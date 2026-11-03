/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.api.system.hud.HudBuffer;
/*     */ import vorga.phazeclient.implement.menu.MenuPalette;
/*     */ import vorga.phazeclient.implement.menu.MenuPalettes;
/*     */ 
/*     */ public abstract class RectHudModule extends Module {
/*     */   private static final float DEFAULT_HUD_X = 22.0F;
/*     */   private static final float DEFAULT_HUD_Y = 22.0F;
/*     */   private static final float DEFAULT_HUD_SCALE = 1.0F;
/*     */   private static final float MIN_HUD_SCALE = 0.5F;
/*     */   public static final float MAX_HUD_SCALE = 3.0F;
/*     */   private final float defaultHudX;
/*     */   private final float defaultHudY;
/*     */   private final float defaultHudScale;
/*  25 */   public final SectionSetting mainSection = new SectionSetting("General");
/*  26 */   public final BooleanSetting textShadow = (new BooleanSetting("Text Shadow", "Draw text with vanilla shadow")).setValue(true);
/*  27 */   public final BooleanSetting background = (new BooleanSetting("Background", "Draw scoreboard-style background")).setValue(true);
/*  28 */   public final BooleanSetting showBrackets = (new BooleanSetting("Show Brackets", "Show brackets around text when background is disabled")).setValue(false).visible(() -> Boolean.valueOf(!this.background.isValue()));
/*  29 */   public final SelectSetting backgroundPreset = (new SelectSetting("Background Preset", "Choose preset background style"))
/*  30 */     .value(new String[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         "Vanilla", "Lunar Blue", "Mocha Gold", "Rose Quartz", "Emerald Frost", "Arctic Mint", "Crimson Silk", "Solar Ember", "Midnight Bloom", "Desert Mirage",
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         "Sapphire Steel", "Velvet Plum", "Frosted Peach", "Moss Smoke", "Polar Night", "Snow", "Obsidian", "Nebula", "Coral", "Jade",
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         "Sunset", "Violet", "Ocean", "Custom Color", "Gradient"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  57 */       }).selected("Vanilla")
/*  58 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/*  59 */   public final ValueSetting colorBrightness = (new ValueSetting("Color Brightness", "Adjust main color brightness"))
/*  60 */     .range(0, 200)
/*  61 */     .setValue(100.0F)
/*  62 */     .visible(() -> Boolean.valueOf((this.background.isValue() && !isVanillaPreset() && !isCustomColorPreset() && !isGradientPreset())));
/*  63 */   public final ValueSetting backgroundOpacity = (new ValueSetting("Background Opacity", "Custom background opacity"))
/*  64 */     .range(0, 100)
/*  65 */     .setValue(50.0F)
/*  66 */     .visible(() -> Boolean.valueOf((this.background.isValue() && !isVanillaPreset())));
/*  67 */   public final ColorSetting customBackgroundColor = (new ColorSetting("Custom Background Color", "Color used by the custom HUD background"))
/*  68 */     .value(-14666681)
/*  69 */     .noAlpha()
/*  70 */     .popupRow()
/*  71 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isCustomColorPreset())));
/*  72 */   public final ColorSetting gradientStartColor = (new ColorSetting("Gradient Start Color", "First color of the HUD gradient"))
/*  73 */     .value(-14988955)
/*  74 */     .noAlpha()
/*  75 */     .popupRow()
/*  76 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  77 */   public final ColorSetting gradientEndColor = (new ColorSetting("Gradient End Color", "Second color of the HUD gradient"))
/*  78 */     .value(-10508077)
/*  79 */     .noAlpha()
/*  80 */     .popupRow()
/*  81 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  82 */   public final ValueSetting gradientSpeed = (new ValueSetting("Gradient Speed", "How quickly the gradient flows between its colors"))
/*  83 */     .range(0.0F, 4.0F)
/*  84 */     .step(0.05F)
/*  85 */     .setValue(0.75F)
/*  86 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  87 */   public final SelectSetting gradientDirection = (new SelectSetting("Gradient Direction", "Direction of the animated HUD gradient"))
/*  88 */     .value(new String[] { "Left to Right", "Right to Left", "Top to Bottom", "Bottom to Top", "Diagonal Down", "Diagonal Up", "Pulse"
/*  89 */       }).selected("Left to Right")
/*  90 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  91 */   public final ValueSetting backgroundBlurRadius = (new ValueSetting("Background Blur Radius", "Blur radius for HUD background"))
/*  92 */     .range(0.0F, 32.0F)
/*  93 */     .step(0.25F)
/*  94 */     .setValue(0.0F)
/*  95 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public final SectionSetting colorSection = (new SectionSetting("Color Settings"))
/* 102 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/* 103 */   public final SectionSetting otherSection = new SectionSetting("Other");
/* 104 */   public final ValueSetting cornerRounding = (new ValueSetting("Corner Rounding", "Round this HUD background's corners"))
/* 105 */     .range(0.0F, 10.0F)
/* 106 */     .step(0.5F)
/* 107 */     .setValue(0.0F)
/* 108 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/*     */   
/* 110 */   private final HudBuffer hudBuffer = new HudBuffer();
/* 111 */   private long gradientLastUpdateNanos = -1L;
/* 112 */   private float gradientAnimationOffset = 0.0F;
/*     */   
/* 114 */   private float hudX = 22.0F;
/* 115 */   private float hudY = 22.0F;
/* 116 */   private float hudScale = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   private int hudRefWidth = -1;
/* 124 */   private int hudRefHeight = -1;
/*     */   
/*     */   protected RectHudModule(String name, String visibleName) {
/* 127 */     this(name, visibleName, 22.0F, 22.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected RectHudModule(String name, String visibleName, float defaultHudX, float defaultHudY, float defaultHudScale) {
/* 131 */     this(name, visibleName, ModuleCategory.HUD, defaultHudX, defaultHudY, defaultHudScale);
/*     */   }
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
/*     */   protected RectHudModule(String name, String visibleName, ModuleCategory category, float defaultHudX, float defaultHudY, float defaultHudScale) {
/* 145 */     super(name, visibleName, category, true, false);
/* 146 */     this.defaultHudX = defaultHudX;
/* 147 */     this.defaultHudY = defaultHudY;
/* 148 */     this.defaultHudScale = defaultHudScale;
/* 149 */     this.hudX = defaultHudX;
/* 150 */     this.hudY = defaultHudY;
/* 151 */     this.hudScale = defaultHudScale;
/* 152 */     this.mainSection.setFullWidth(true);
/* 153 */     this.textShadow.setFullWidth(true);
/* 154 */     this.background.setFullWidth(true);
/* 155 */     this.showBrackets.setFullWidth(true);
/* 156 */     this.backgroundPreset.setFullWidth(true);
/* 157 */     this.colorBrightness.setFullWidth(true);
/* 158 */     this.backgroundOpacity.setFullWidth(true);
/* 159 */     this.customBackgroundColor.setFullWidth(true);
/* 160 */     this.gradientStartColor.setFullWidth(true);
/* 161 */     this.gradientEndColor.setFullWidth(true);
/* 162 */     this.gradientSpeed.setFullWidth(true);
/* 163 */     this.gradientDirection.setFullWidth(true);
/* 164 */     this.backgroundBlurRadius.setFullWidth(true);
/* 165 */     this.otherSection.setFullWidth(true);
/* 166 */     this.cornerRounding.setFullWidth(true);
/* 167 */     setup(new Setting[] { (Setting)this.mainSection, (Setting)this.textShadow, (Setting)this.background, (Setting)this.showBrackets, (Setting)this.colorSection, (Setting)this.backgroundPreset, (Setting)this.colorBrightness, (Setting)this.backgroundOpacity, (Setting)this.customBackgroundColor, (Setting)this.gradientStartColor, (Setting)this.gradientEndColor, (Setting)this.gradientSpeed, (Setting)this.gradientDirection, (Setting)this.backgroundBlurRadius, (Setting)this.otherSection, (Setting)this.cornerRounding });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHudX() {
/* 173 */     resolveForCurrentScreen();
/* 174 */     return this.hudX;
/*     */   }
/*     */   
/*     */   public void setHudX(float hudX) {
/* 178 */     resolveForCurrentScreen();
/* 179 */     this.hudX = hudX;
/* 180 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getHudY() {
/* 184 */     resolveForCurrentScreen();
/* 185 */     return this.hudY;
/*     */   }
/*     */   
/*     */   public void setHudY(float hudY) {
/* 189 */     resolveForCurrentScreen();
/* 190 */     this.hudY = hudY;
/* 191 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getHudScale() {
/* 195 */     return this.hudScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRenderHudScale() {
/* 204 */     return this.hudScale * 2.0F;
/*     */   }
/*     */   
/*     */   public void setHudScale(float hudScale) {
/* 208 */     this.hudScale = class_3532.method_15363(hudScale, getMinHudScale(), getMaxHudScale());
/* 209 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getMinHudScale() {
/* 213 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public final float getMaxHudScale() {
/* 217 */     return 3.0F;
/*     */   }
/*     */   
/*     */   public HudBuffer getHudBuffer() {
/* 221 */     return this.hudBuffer;
/*     */   }
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
/*     */   public boolean hasActiveBackgroundBlur() {
/* 238 */     return (this.background.isValue() && this.backgroundBlurRadius.getValue() > 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasActiveAnimatedBackground() {
/* 247 */     return (this.background.isValue() && isGradientPreset() && this.gradientSpeed.getValue() > 0.0F);
/*     */   }
/*     */   
/*     */   public void resetHudTransform() {
/* 251 */     this.hudX = this.defaultHudX;
/* 252 */     this.hudY = this.defaultHudY;
/* 253 */     this.hudScale = this.defaultHudScale;
/* 254 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getHudXRatio() {
/* 258 */     class_310 client = class_310.method_1551();
/* 259 */     if (client == null || client.method_22683() == null) {
/* 260 */       return 0.0F;
/*     */     }
/* 262 */     resolveForCurrentScreen();
/* 263 */     return class_3532.method_15363(this.hudX / Math.max(1.0F, client.method_22683().method_4480()), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getHudYRatio() {
/* 267 */     class_310 client = class_310.method_1551();
/* 268 */     if (client == null || client.method_22683() == null) {
/* 269 */       return 0.0F;
/*     */     }
/* 271 */     resolveForCurrentScreen();
/* 272 */     return class_3532.method_15363(this.hudY / Math.max(1.0F, client.method_22683().method_4507()), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void setHudXRatio(float ratio) {
/* 276 */     class_310 client = class_310.method_1551();
/* 277 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 280 */     this
/* 281 */       .hudX = class_3532.method_15363(ratio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4480());
/* 282 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public void setHudYRatio(float ratio) {
/* 286 */     class_310 client = class_310.method_1551();
/* 287 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 290 */     this
/* 291 */       .hudY = class_3532.method_15363(ratio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4507());
/* 292 */     markResolvedForCurrentScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHudPositionRatio(float xRatio, float yRatio) {
/* 297 */     class_310 client = class_310.method_1551();
/* 298 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 301 */     this
/* 302 */       .hudX = class_3532.method_15363(xRatio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4480());
/* 303 */     this
/* 304 */       .hudY = class_3532.method_15363(yRatio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4507());
/* 305 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public int getResolvedBackgroundColor(class_310 client) {
/* 309 */     if (isVanillaPreset()) {
/* 310 */       return client.field_1690.method_19345(0.5F);
/*     */     }
/* 312 */     if (isCustomColorPreset()) {
/* 313 */       return applyColorOptions(this.customBackgroundColor.getColor());
/*     */     }
/* 315 */     if (isGradientPreset()) {
/* 316 */       return getResolvedGradientStartColor();
/*     */     }
/* 318 */     MenuPalette palette = MenuPalettes.byName(this.backgroundPreset.getSelected());
/* 319 */     int presetColor = adjustBrightness(palette.chipActive(), this.colorBrightness.getValue() / 100.0F);
/* 320 */     int alpha = class_3532.method_15340(Math.round(this.backgroundOpacity.getValue() / 100.0F * 255.0F), 0, 255);
/* 321 */     return alpha << 24 | presetColor & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   protected boolean isVanillaPreset() {
/* 325 */     return "Vanilla".equalsIgnoreCase(this.backgroundPreset.getSelected());
/*     */   }
/*     */   
/*     */   public boolean isCustomColorPreset() {
/* 329 */     return "Custom Color".equalsIgnoreCase(this.backgroundPreset.getSelected());
/*     */   }
/*     */   
/*     */   public boolean isGradientPreset() {
/* 333 */     return "Gradient".equalsIgnoreCase(this.backgroundPreset.getSelected());
/*     */   }
/*     */   
/*     */   public int getResolvedGradientStartColor() {
/* 337 */     return applyColorOptions(this.gradientStartColor.getColor());
/*     */   }
/*     */   
/*     */   public int getResolvedGradientEndColor() {
/* 341 */     return applyColorOptions(this.gradientEndColor.getColor());
/*     */   }
/*     */   
/*     */   public String getGradientDirection() {
/* 345 */     return this.gradientDirection.getSelected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGradientAnimationOffset(long nowMs) {
/* 353 */     long nowNanos = System.nanoTime();
/* 354 */     if (this.gradientLastUpdateNanos < 0L) {
/* 355 */       this.gradientLastUpdateNanos = nowNanos;
/* 356 */       return this.gradientAnimationOffset;
/*     */     } 
/* 358 */     float deltaSeconds = class_3532.method_15363((float)(nowNanos - this.gradientLastUpdateNanos) / 1.0E9F, 0.0F, 0.1F);
/* 359 */     this.gradientLastUpdateNanos = nowNanos;
/* 360 */     float speed = Math.max(0.0F, this.gradientSpeed.getValue());
/* 361 */     this.gradientAnimationOffset = (this.gradientAnimationOffset + deltaSeconds * speed * 0.5F) % 1.0F;
/* 362 */     return this.gradientAnimationOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeGradientColors(int[] colors, long nowMs) {
/* 371 */     if (colors == null || colors.length < 4) {
/*     */       return;
/*     */     }
/* 374 */     if (!isGradientPreset()) {
/* 375 */       int color = getResolvedBackgroundColor(class_310.method_1551());
/* 376 */       colors[0] = color;
/* 377 */       colors[1] = color;
/* 378 */       colors[2] = color;
/* 379 */       colors[3] = color;
/*     */       
/*     */       return;
/*     */     } 
/* 383 */     int first = getResolvedGradientStartColor();
/* 384 */     int second = getResolvedGradientEndColor();
/* 385 */     int midpoint = blend(first, second, 0.5F);
/* 386 */     switch (this.gradientDirection.getSelected()) { case "Right to Left":
/* 387 */         setGradientCorners(colors, second, second, first, first); return;
/* 388 */       case "Top to Bottom": setGradientCorners(colors, first, second, first, second); return;
/* 389 */       case "Bottom to Top": setGradientCorners(colors, second, first, second, first); return;
/* 390 */       case "Diagonal Down": setGradientCorners(colors, first, midpoint, midpoint, second); return;
/* 391 */       case "Diagonal Up": setGradientCorners(colors, midpoint, second, first, midpoint); return;
/* 392 */       case "Pulse": setGradientCorners(colors, first, first, first, first); return; }
/* 393 */      setGradientCorners(colors, first, first, second, second);
/*     */   }
/*     */ 
/*     */   
/*     */   private int applyColorOptions(int color) {
/* 398 */     int alpha = class_3532.method_15340(Math.round((color >>> 24 & 0xFF) * this.backgroundOpacity.getValue() / 100.0F), 0, 255);
/* 399 */     return alpha << 24 | color & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   private static void setGradientCorners(int[] colors, int topLeft, int bottomLeft, int topRight, int bottomRight) {
/* 403 */     colors[0] = topLeft;
/* 404 */     colors[1] = bottomLeft;
/* 405 */     colors[2] = topRight;
/* 406 */     colors[3] = bottomRight;
/*     */   }
/*     */   
/*     */   private static int blend(int first, int second, float progress) {
/* 410 */     float t = class_3532.method_15363(progress, 0.0F, 1.0F);
/* 411 */     int a = Math.round((first >>> 24 & 0xFF) + ((second >>> 24 & 0xFF) - (first >>> 24 & 0xFF)) * t);
/* 412 */     int r = Math.round((first >>> 16 & 0xFF) + ((second >>> 16 & 0xFF) - (first >>> 16 & 0xFF)) * t);
/* 413 */     int g = Math.round((first >>> 8 & 0xFF) + ((second >>> 8 & 0xFF) - (first >>> 8 & 0xFF)) * t);
/* 414 */     int b = Math.round((first & 0xFF) + ((second & 0xFF) - (first & 0xFF)) * t);
/* 415 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   private static int adjustBrightness(int color, float multiplier) {
/* 419 */     int r = class_3532.method_15340(Math.round((color >>> 16 & 0xFF) * multiplier), 0, 255);
/* 420 */     int g = class_3532.method_15340(Math.round((color >>> 8 & 0xFF) * multiplier), 0, 255);
/* 421 */     int b = class_3532.method_15340(Math.round((color & 0xFF) * multiplier), 0, 255);
/* 422 */     return r << 16 | g << 8 | b;
/*     */   }
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
/*     */   private void resolveForCurrentScreen() {
/* 438 */     class_310 client = class_310.method_1551();
/* 439 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 442 */     int screenWidth = client.method_22683().method_4480();
/* 443 */     int screenHeight = client.method_22683().method_4507();
/* 444 */     if (screenWidth <= 0 || screenHeight <= 0) {
/*     */       return;
/*     */     }
/* 447 */     if (this.hudRefWidth <= 0 || this.hudRefHeight <= 0) {
/*     */ 
/*     */       
/* 450 */       this.hudRefWidth = screenWidth;
/* 451 */       this.hudRefHeight = screenHeight;
/*     */       return;
/*     */     } 
/* 454 */     if (screenWidth == this.hudRefWidth && screenHeight == this.hudRefHeight) {
/*     */       return;
/*     */     }
/* 457 */     this.hudX *= screenWidth / this.hudRefWidth;
/* 458 */     this.hudY *= screenHeight / this.hudRefHeight;
/* 459 */     this.hudRefWidth = screenWidth;
/* 460 */     this.hudRefHeight = screenHeight;
/*     */   }
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
/*     */   public void setHudPosition(float x, float y, int refWidth, int refHeight) {
/* 475 */     this.hudX = x;
/* 476 */     this.hudY = y;
/* 477 */     if (refWidth > 0 && refHeight > 0) {
/* 478 */       this.hudRefWidth = refWidth;
/* 479 */       this.hudRefHeight = refHeight;
/*     */     } else {
/* 481 */       markResolvedForCurrentScreen();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void markResolvedForCurrentScreen() {
/* 487 */     class_310 client = class_310.method_1551();
/* 488 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 491 */     this.hudRefWidth = client.method_22683().method_4480();
/* 492 */     this.hudRefHeight = client.method_22683().method_4507();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHudRefWidth() {
/* 497 */     return this.hudRefWidth;
/*     */   }
/*     */   
/*     */   public int getHudRefHeight() {
/* 501 */     return this.hudRefHeight;
/*     */   }
/*     */   
/*     */   public void setHudReference(int width, int height) {
/* 505 */     this.hudRefWidth = width;
/* 506 */     this.hudRefHeight = height;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\RectHudModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */