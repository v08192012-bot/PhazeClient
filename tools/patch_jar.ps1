# Patches PhazeClient-1.2.jar with the MSDF screen-pixel-range fix.
# Run from the .bat, or: powershell -ExecutionPolicy Bypass -File patch_jar.ps1 PhazeClient-1.2.jar

param([string]$Jar)

$ErrorActionPreference = "Stop"
$Target = "assets/phaze/shaders/core/msdf_font.fsh"
$Here   = Split-Path -Parent $MyInvocation.MyCommand.Path

$Shader = @'
#version 150

in vec2 TexCoord;
in vec4 FragColor;
in vec2 GlobalPos;

uniform sampler2D Sampler0;
uniform float Range;
uniform float Thickness;
uniform float Smoothness;
uniform bool Outline;
uniform float OutlineThickness;
uniform vec4 OutlineColor;
uniform vec4 ColorModulator;
uniform bool EnableFadeout;
uniform float FadeoutStart;
uniform float FadeoutEnd;
uniform float MaxWidth;
uniform float TextPosX;
uniform bool LabelFadeEnabled;
uniform vec4 LabelFadeBounds;
uniform vec2 LabelFadeStrength;

out vec4 OutColor;

float median(vec3 color) {
    return max(min(color.r, color.g), min(max(color.r, color.g), color.b));
}

void main() {
    float dist = median(texture(Sampler0, TexCoord).rgb) - 0.5 + Thickness;

    // How many screen pixels one unit of the distance field covers.
    // `Range` is expressed in atlas texels, so it must be divided by the atlas
    // size and multiplied by how much UV a single screen pixel spans (fwidth)
    // to get back to pixels. This is the canonical MSDF formulation - the same
    // one used by phaze:loading/msdf - and it keeps the glyph/icon edge exactly
    // one pixel wide at every scale. Measuring dFdx(uv.x)/dFdy(uv.y) separately
    // instead loses a factor of sqrt(2) (edges render ~40% too soft, i.e. as if
    // extra antialiasing were applied) and collapses entirely on rotated quads,
    // where both derivatives can hit zero and leave the glyph unantialiased.
    vec2 unitRange = vec2(Range) / vec2(textureSize(Sampler0, 0));
    vec2 screenTexSize = vec2(1.0) / max(fwidth(TexCoord), vec2(1.0e-6));
    // Below one screen pixel the nominally empty part of a tiny quad becomes
    // partially opaque and shows up as a pale square behind small UI glyphs
    // (notably the panorama-card cross), so one pixel is the lower bound.
    float pixels = max(0.5 * dot(unitRange, screenTexSize), 1.0);
    float alpha = smoothstep(-Smoothness, Smoothness, dist * pixels);
    vec4 color = vec4(FragColor.rgb, FragColor.a * alpha);

    if (Outline) {
        color = mix(OutlineColor, FragColor, alpha);
        color.a *= smoothstep(-Smoothness, Smoothness, (dist + OutlineThickness) * pixels);
    }

    if (EnableFadeout) {
        float fadeAlpha = 1.0;
        float relativeX = GlobalPos.x - TextPosX;
        float normalizedX = relativeX / MaxWidth;
        if (normalizedX > FadeoutStart) {
            fadeAlpha = 1.0 - smoothstep(FadeoutStart, FadeoutEnd, normalizedX);
        }
        color.a *= fadeAlpha;
    }

    if (LabelFadeEnabled) {
        float leftRamp = smoothstep(0.0, LabelFadeBounds.z, GlobalPos.x - LabelFadeBounds.x);
        float rightRamp = smoothstep(0.0, LabelFadeBounds.z, LabelFadeBounds.y - GlobalPos.x);
        color.a *= mix(1.0, leftRamp, LabelFadeStrength.x) * mix(1.0, rightRamp, LabelFadeStrength.y);
    }

    OutColor = color * ColorModulator;
}

'@

if (-not $Jar) {
    $found = @(Get-ChildItem -Path $Here -Filter *.jar -File |
               Where-Object { $_.Name -notlike "*-msdf-fix*" })
    if ($found.Count -eq 1) { $Jar = $found[0].FullName }
    elseif ($found.Count -eq 0) {
        Write-Host "No .jar found next to this script." -ForegroundColor Red
        Write-Host "Put PhazeClient-1.2.jar here and run again (or drag the jar onto patch_jar.bat)."
        exit 1
    } else {
        Write-Host "Several jars found, drag the right one onto patch_jar.bat." -ForegroundColor Yellow
        exit 1
    }
}

if (-not (Test-Path -LiteralPath $Jar -PathType Leaf)) {
    Write-Host "Not a file: $Jar" -ForegroundColor Red
    exit 1
}

$Dst = [System.IO.Path]::Combine(
    [System.IO.Path]::GetDirectoryName($Jar),
    [System.IO.Path]::GetFileNameWithoutExtension($Jar) + "-msdf-fix" + [System.IO.Path]::GetExtension($Jar))

Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$Src = [System.IO.Compression.ZipFile]::OpenRead($Jar)
try {
    $Names = $Src.Entries | ForEach-Object { $_.FullName }
    if ($Names -notcontains "fabric.mod.json") {
        Write-Host "WARNING: no fabric.mod.json inside - is this really the mod jar?" -ForegroundColor Yellow
    }
    $Replaced = $Names -contains $Target

    if (Test-Path -LiteralPath $Dst) { Remove-Item -LiteralPath $Dst -Force }
    $Zip = [System.IO.Compression.ZipFile]::Open($Dst, [System.IO.Compression.ZipArchiveMode]::Create)
    try {
        foreach ($e in $Src.Entries) {
            if ($e.FullName -eq $Target) { continue }
            $New = $Zip.CreateEntry($e.FullName, [System.IO.Compression.CompressionLevel]::Optimal)
            $S = $e.Open(); $D = $New.Open()
            try { $S.CopyTo($D) } finally { $S.Close(); $D.Close() }
        }
        $Entry = $Zip.CreateEntry($Target, [System.IO.Compression.CompressionLevel]::Optimal)
        $W = New-Object System.IO.StreamWriter($Entry.Open(), (New-Object System.Text.UTF8Encoding($false)))
        try { $W.Write($Shader) } finally { $W.Close() }
    } finally { $Zip.Dispose() }
} finally { $Src.Dispose() }

Write-Host ""
Write-Host ("  source : " + $Jar)
Write-Host ("  output : " + $Dst)
Write-Host ("  shader : " + $Target + " (" + $Shader.Length + " bytes)")
if ($Replaced) { Write-Host "  action : replaced" } else { Write-Host "  action : added (entry was missing!)" -ForegroundColor Yellow }
Write-Host ""
Write-Host "Done. Drop it into .minecraft/mods (delete or rename the old jar first)."
