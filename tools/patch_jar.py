#!/usr/bin/env python3
"""
Patches PhazeClient-1.2.jar with the MSDF screen-pixel-range fix.

The jar is a zip; this rewrites it entry by entry, replacing
assets/phaze/shaders/core/msdf_font.fsh with the corrected shader and
copying everything else byte for byte. The result is the real client jar
with the fix, ready to drop into .minecraft/mods.

Usage:  python3 patch_jar.py PhazeClient-1.2.jar
        (or just drop the jar onto this script)
"""

import os
import sys
import zipfile

TARGET = "assets/phaze/shaders/core/msdf_font.fsh"

SHADER = r"""#version 150

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

"""


def pick_jar():
    if len(sys.argv) > 1:
        return sys.argv[1]
    here = os.path.dirname(os.path.abspath(__file__))
    jars = [f for f in os.listdir(here) if f.lower().endswith(".jar")
            and "-msdf-fix" not in f.lower()]
    if len(jars) == 1:
        return os.path.join(here, jars[0])
    if not jars:
        sys.exit("No .jar found. Run: python3 patch_jar.py PhazeClient-1.2.jar")
    sys.exit("Several jars found, pick one:\n  python3 patch_jar.py " + jars[0])


def main():
    src = pick_jar()
    if not os.path.isfile(src):
        sys.exit("Not a file: " + src)
    base, ext = os.path.splitext(src)
    dst = base + "-msdf-fix" + ext

    with zipfile.ZipFile(src) as zin:
        names = zin.namelist()
        if "fabric.mod.json" not in names:
            print("WARNING: no fabric.mod.json inside - is this really the mod jar?")
        replaced = TARGET in names
        with zipfile.ZipFile(dst, "w", zipfile.ZIP_DEFLATED) as zout:
            for name in names:
                if name == TARGET:
                    continue
                info = zin.getinfo(name)
                zout.writestr(info, zin.read(name))
            zout.writestr(TARGET, SHADER)
        total = len(names)

    print()
    print("  source : %s (%d entries)" % (src, total))
    print("  output : %s" % dst)
    print("  shader : %s (%d bytes)" % (TARGET, len(SHADER)))
    print("  action : %s" % ("replaced" if replaced else "added (entry was missing!)"))
    print()
    print("Done. Drop it into .minecraft/mods (delete/rename the old jar first).")


if __name__ == "__main__":
    main()
