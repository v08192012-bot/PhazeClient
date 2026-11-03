/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SkyCustomizer
/*     */   extends Module
/*     */ {
/*  59 */   private static final SkyCustomizer INSTANCE = new SkyCustomizer();
/*     */   
/*  61 */   public final SectionSetting modeSection = new SectionSetting("Mode");
/*  62 */   public final SelectSetting mode = (new SelectSetting("Mode", "How the configured color is applied: Tint blends with vanilla, Replace overrides it, Gradient smooths between day/night colors."))
/*     */ 
/*     */     
/*  65 */     .value(new String[] { "Tint", "Replace", "Gradient" }).selected("Tint");
/*     */   
/*  67 */   public final SectionSetting tintSection = new SectionSetting("Color");
/*  68 */   public final ColorSetting baseColor = (new ColorSetting("Base Color", "Used by Tint and Replace modes; also the day color in Gradient mode"))
/*     */ 
/*     */     
/*  71 */     .setColor(-8734977).noAlpha().popupRow();
/*  72 */   public final ColorSetting nightColor = (new ColorSetting("Night Color", "Gradient mode only - the colour the sky leans toward at night"))
/*     */ 
/*     */     
/*  75 */     .setColor(-15068621).noAlpha().popupRow();
/*  76 */   public final ValueSetting intensity = (new ValueSetting("Intensity", "Tint strength. 0 = vanilla, 1 = fully tinted"))
/*     */ 
/*     */     
/*  79 */     .range(0.0F, 1.0F).step(0.05F).setValue(0.5F);
/*     */   
/*  81 */   public final SectionSetting twilightSection = new SectionSetting("Twilight");
/*  82 */   public final ValueSetting sunsetBoost = (new ValueSetting("Sunrise/Sunset Boost", "Amplifies the warm channels during dawn / dusk. 1 = neutral, higher = more orange/red"))
/*     */ 
/*     */     
/*  85 */     .range(1.0F, 4.0F).step(0.05F).setValue(1.0F);
/*     */   
/*     */   private SkyCustomizer() {
/*  88 */     super("sky_customizer", "Sky Customizer", ModuleCategory.OTHER);
/*     */     
/*  90 */     this.mode.setFullWidth(true);
/*  91 */     this.baseColor.setFullWidth(true);
/*  92 */     this.nightColor.setFullWidth(true);
/*  93 */     this.intensity.setFullWidth(true);
/*  94 */     this.sunsetBoost.setFullWidth(true);
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.nightColor.visible(() -> Boolean.valueOf("Gradient".equalsIgnoreCase(this.mode.getSelected())));
/*  99 */     this.intensity.visible(() -> Boolean.valueOf(!"Replace".equalsIgnoreCase(this.mode.getSelected())));
/*     */     
/* 101 */     setup(new Setting[] { (Setting)this.modeSection, (Setting)this.mode, (Setting)this.tintSection, (Setting)this.baseColor, (Setting)this.nightColor, (Setting)this.intensity, (Setting)this.twilightSection, (Setting)this.sunsetBoost });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivate() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static SkyCustomizer getInstance() {
/* 117 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 122 */     return "Custom sky tint with day/night gradient and sunset boost";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 127 */     return "sky_customizer.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 132 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int applyToSky(int vanillaArgb, float skyBrightness) {
/* 141 */     if (!isEnabled()) {
/* 142 */       return vanillaArgb;
/*     */     }
/* 144 */     int target = pickTargetColor(skyBrightness);
/* 145 */     int blended = blendForCurrentMode(vanillaArgb, target);
/* 146 */     return applySunsetBoost(blended, skyBrightness);
/*     */   }
/*     */   
/*     */   private int pickTargetColor(float skyBrightness) {
/* 150 */     if ("Gradient".equalsIgnoreCase(this.mode.getSelected()))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 155 */       return blend(this.nightColor.getColor(), this.baseColor.getColor(), clamp01(skyBrightness));
/*     */     }
/* 157 */     return this.baseColor.getColor();
/*     */   }
/*     */   
/*     */   private int blendForCurrentMode(int vanilla, int target) {
/* 161 */     String m = this.mode.getSelected();
/* 162 */     if ("Replace".equalsIgnoreCase(m)) {
/* 163 */       return target;
/*     */     }
/* 165 */     return blend(vanilla, target, clamp01(this.intensity.getValue()));
/*     */   }
/*     */   
/*     */   private int applySunsetBoost(int color, float skyBrightness) {
/* 169 */     float boost = this.sunsetBoost.getValue();
/* 170 */     if (boost <= 1.001F) {
/* 171 */       return color;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     float window = 1.0F - Math.abs(skyBrightness - 0.5F) * 2.0F;
/* 179 */     if (window <= 0.0F) {
/* 180 */       return color;
/*     */     }
/* 182 */     float effective = 1.0F + (boost - 1.0F) * clamp01(window);
/* 183 */     int a = color >> 24 & 0xFF;
/* 184 */     int r = clamp255(Math.round((color >> 16 & 0xFF) * effective));
/* 185 */     int g = clamp255(Math.round((color >> 8 & 0xFF) * (1.0F + (effective - 1.0F) * 0.6F)));
/* 186 */     int b = color & 0xFF;
/* 187 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   private static int blend(int a, int b, float t) {
/* 191 */     if (t <= 0.0F) return a; 
/* 192 */     if (t >= 1.0F) return b; 
/* 193 */     int aa = a >> 24 & 0xFF;
/* 194 */     int ar = a >> 16 & 0xFF;
/* 195 */     int ag = a >> 8 & 0xFF;
/* 196 */     int ab = a & 0xFF;
/* 197 */     int ba = b >> 24 & 0xFF;
/* 198 */     int br = b >> 16 & 0xFF;
/* 199 */     int bg = b >> 8 & 0xFF;
/* 200 */     int bb = b & 0xFF;
/* 201 */     int oa = Math.round(aa + (ba - aa) * t);
/* 202 */     int or = Math.round(ar + (br - ar) * t);
/* 203 */     int og = Math.round(ag + (bg - ag) * t);
/* 204 */     int ob = Math.round(ab + (bb - ab) * t);
/* 205 */     return oa << 24 | or << 16 | og << 8 | ob;
/*     */   }
/*     */   
/*     */   private static float clamp01(float v) {
/* 209 */     if (v < 0.0F) return 0.0F; 
/* 210 */     if (v > 1.0F) return 1.0F; 
/* 211 */     return v;
/*     */   }
/*     */   
/*     */   private static int clamp255(int v) {
/* 215 */     if (v < 0) return 0; 
/* 216 */     if (v > 255) return 255; 
/* 217 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\SkyCustomizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */