/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
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
/*     */ public final class ArmorHud extends Module {
/*  16 */   private static final ArmorHud INSTANCE = new ArmorHud();
/*     */   
/*     */   private static final float DEFAULT_HUD_X = 22.0F;
/*     */   private static final float DEFAULT_HUD_Y = 60.0F;
/*     */   private static final float DEFAULT_HUD_SCALE = 1.0F;
/*     */   private static final float MIN_HUD_SCALE = 0.5F;
/*  22 */   public final SectionSetting mainSection = new SectionSetting("General");
/*  23 */   public final BooleanSetting textShadow = (new BooleanSetting("Text Shadow", "Draw text with vanilla shadow")).setValue(true);
/*  24 */   public final BooleanSetting background = (new BooleanSetting("Background", "Draw scoreboard-style background")).setValue(false);
/*  25 */   public final SelectSetting backgroundPreset = (new SelectSetting("Background Preset", "Choose preset background style"))
/*  26 */     .value(new String[] {
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
/*  53 */       }).selected("Vanilla")
/*  54 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/*  55 */   public final ValueSetting colorBrightness = (new ValueSetting("Color Brightness", "Adjust main color brightness"))
/*  56 */     .range(0, 200)
/*  57 */     .setValue(100.0F)
/*  58 */     .visible(() -> Boolean.valueOf((this.background.isValue() && !isVanillaPreset() && !isCustomColorPreset() && !isGradientPreset())));
/*  59 */   public final ValueSetting backgroundOpacity = (new ValueSetting("Background Opacity", "Custom background opacity"))
/*  60 */     .range(0, 100)
/*  61 */     .setValue(30.0F)
/*  62 */     .visible(() -> Boolean.valueOf((this.background.isValue() && !isVanillaPreset())));
/*  63 */   public final ColorSetting customBackgroundColor = (new ColorSetting("Custom Background Color", "Color used by the custom HUD background"))
/*  64 */     .value(-14666681)
/*  65 */     .noAlpha()
/*  66 */     .popupRow()
/*  67 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isCustomColorPreset())));
/*  68 */   public final ColorSetting gradientStartColor = (new ColorSetting("Gradient Start Color", "First color of the HUD gradient"))
/*  69 */     .value(-14988955)
/*  70 */     .noAlpha()
/*  71 */     .popupRow()
/*  72 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  73 */   public final ColorSetting gradientEndColor = (new ColorSetting("Gradient End Color", "Second color of the HUD gradient"))
/*  74 */     .value(-10508077)
/*  75 */     .noAlpha()
/*  76 */     .popupRow()
/*  77 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  78 */   public final ValueSetting gradientSpeed = (new ValueSetting("Gradient Speed", "How quickly the gradient flows between its colors"))
/*  79 */     .range(0.0F, 4.0F)
/*  80 */     .step(0.05F)
/*  81 */     .setValue(0.75F)
/*  82 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  83 */   public final SelectSetting gradientDirection = (new SelectSetting("Gradient Direction", "Direction of the animated HUD gradient"))
/*  84 */     .value(new String[] { "Left to Right", "Right to Left", "Top to Bottom", "Bottom to Top", "Diagonal Down", "Diagonal Up", "Pulse"
/*  85 */       }).selected("Left to Right")
/*  86 */     .visible(() -> Boolean.valueOf((this.background.isValue() && isGradientPreset())));
/*  87 */   public final ValueSetting backgroundBlurRadius = (new ValueSetting("Background Blur Radius", "Blur radius for HUD background"))
/*  88 */     .range(0.0F, 32.0F)
/*  89 */     .step(0.25F)
/*  90 */     .setValue(0.0F)
/*  91 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/*  92 */   public final SectionSetting colorSection = (new SectionSetting("Color Settings"))
/*  93 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/*  94 */   public final SectionSetting otherSection = new SectionSetting("Other");
/*  95 */   public final ValueSetting cornerRounding = (new ValueSetting("Corner Rounding", "Round this HUD background's corners"))
/*  96 */     .range(0.0F, 10.0F)
/*  97 */     .step(0.5F)
/*  98 */     .setValue(0.0F)
/*  99 */     .visible(() -> Boolean.valueOf(this.background.isValue()));
/* 100 */   public final SelectSetting durabilityMode = (new SelectSetting("Durability Mode", "How to display armor durability"))
/* 101 */     .value(new String[] { "Units", "Percent"
/* 102 */       }).selected("Units");
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
/* 117 */   public final BooleanSetting colorByDurability = (new BooleanSetting("Color By Durability", "Color the durability number red/yellow/green based on remaining armor percentage"))
/*     */ 
/*     */     
/* 120 */     .setValue(false);
/*     */   
/* 122 */   private final HudBuffer hudBuffer = new HudBuffer();
/* 123 */   private long gradientLastUpdateNanos = -1L;
/* 124 */   private float gradientAnimationOffset = 0.0F;
/*     */   
/* 126 */   private float hudX = 22.0F;
/* 127 */   private float hudY = 60.0F;
/* 128 */   private float hudScale = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private int hudRefWidth = -1;
/* 136 */   private int hudRefHeight = -1;
/*     */   
/*     */   public static ArmorHud getInstance() {
/* 139 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private ArmorHud() {
/* 143 */     super("armor_hud", "Armor", ModuleCategory.HUD, true, false);
/* 144 */     this.mainSection.setFullWidth(true);
/* 145 */     this.textShadow.setFullWidth(true);
/* 146 */     this.background.setFullWidth(true);
/* 147 */     this.backgroundPreset.setFullWidth(true);
/* 148 */     this.colorBrightness.setFullWidth(true);
/* 149 */     this.backgroundOpacity.setFullWidth(true);
/* 150 */     this.customBackgroundColor.setFullWidth(true);
/* 151 */     this.gradientStartColor.setFullWidth(true);
/* 152 */     this.gradientEndColor.setFullWidth(true);
/* 153 */     this.gradientSpeed.setFullWidth(true);
/* 154 */     this.gradientDirection.setFullWidth(true);
/* 155 */     this.backgroundBlurRadius.setFullWidth(true);
/* 156 */     this.cornerRounding.setFullWidth(true);
/* 157 */     this.durabilityMode.setFullWidth(true);
/* 158 */     this.colorByDurability.setFullWidth(true);
/* 159 */     setup(new Setting[] { (Setting)this.mainSection, (Setting)this.textShadow, (Setting)this.background, (Setting)this.colorSection, (Setting)this.backgroundPreset, (Setting)this.colorBrightness, (Setting)this.backgroundOpacity, (Setting)this.customBackgroundColor, (Setting)this.gradientStartColor, (Setting)this.gradientEndColor, (Setting)this.gradientSpeed, (Setting)this.gradientDirection, (Setting)this.backgroundBlurRadius, (Setting)this.otherSection, (Setting)this.cornerRounding, (Setting)this.durabilityMode, (Setting)this.colorByDurability });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 166 */     return "Shows armor and durability on HUD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 171 */     return "armor_hud.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 176 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public HudBuffer getHudBuffer() {
/* 180 */     return this.hudBuffer;
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
/*     */   public boolean hasActiveBackgroundBlur() {
/* 193 */     return (this.background.isValue() && this.backgroundBlurRadius.getValue() > 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasActiveAnimatedBackground() {
/* 198 */     return (this.background.isValue() && isGradientPreset() && this.gradientSpeed.getValue() > 0.0F);
/*     */   }
/*     */   
/*     */   public float getHudX() {
/* 202 */     resolveForCurrentScreen();
/* 203 */     return this.hudX;
/*     */   }
/*     */   
/*     */   public void setHudX(float hudX) {
/* 207 */     resolveForCurrentScreen();
/* 208 */     this.hudX = hudX;
/* 209 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getHudY() {
/* 213 */     resolveForCurrentScreen();
/* 214 */     return this.hudY;
/*     */   }
/*     */   
/*     */   public void setHudY(float hudY) {
/* 218 */     resolveForCurrentScreen();
/* 219 */     this.hudY = hudY;
/* 220 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public void resetHudTransform() {
/* 224 */     this.hudX = 22.0F;
/* 225 */     this.hudY = 60.0F;
/* 226 */     this.hudScale = 1.0F;
/* 227 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getHudScale() {
/* 231 */     return this.hudScale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRenderHudScale() {
/* 239 */     return this.hudScale * 2.0F;
/*     */   }
/*     */   
/*     */   public void setHudScale(float hudScale) {
/* 243 */     this.hudScale = class_3532.method_15363(hudScale, 0.5F, 3.0F);
/* 244 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public float getMinHudScale() {
/* 248 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public float getMaxHudScale() {
/* 252 */     return 3.0F;
/*     */   }
/*     */   
/*     */   public String formatDurability(int remaining, int max) {
/* 256 */     if ("Percent".equalsIgnoreCase(this.durabilityMode.getSelected())) {
/* 257 */       int percent = (max <= 0) ? 0 : class_3532.method_15340(Math.round(remaining * 100.0F / max), 0, 100);
/* 258 */       return "" + percent + "%";
/*     */     } 
/* 260 */     return String.valueOf(remaining);
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
/*     */   public int colorForDurability(int remaining, int max) {
/* 272 */     if (!this.colorByDurability.isValue()) {
/* 273 */       return -1;
/*     */     }
/* 275 */     if (max <= 0) {
/* 276 */       return -1;
/*     */     }
/* 278 */     float ratio = remaining / max;
/* 279 */     if (ratio > 0.75F) {
/* 280 */       return -11141291;
/*     */     }
/* 282 */     if (ratio > 0.25F) {
/* 283 */       return -22016;
/*     */     }
/* 285 */     return -43691;
/*     */   }
/*     */   
/*     */   public int getResolvedBackgroundColor(class_310 client) {
/* 289 */     if (isVanillaPreset()) {
/* 290 */       return client.field_1690.method_19345(0.3F);
/*     */     }
/* 292 */     if (isCustomColorPreset()) {
/* 293 */       return applyColorOptions(this.customBackgroundColor.getColor());
/*     */     }
/* 295 */     if (isGradientPreset()) {
/* 296 */       return getResolvedGradientStartColor();
/*     */     }
/* 298 */     MenuPalette palette = MenuPalettes.byName(this.backgroundPreset.getSelected());
/* 299 */     int presetColor = adjustBrightness(palette.chipActive(), this.colorBrightness.getValue() / 100.0F);
/* 300 */     int alpha = class_3532.method_15340(Math.round(this.backgroundOpacity.getValue() / 100.0F * 255.0F), 0, 255);
/* 301 */     return alpha << 24 | presetColor & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   private boolean isVanillaPreset() {
/* 305 */     return "Vanilla".equalsIgnoreCase(this.backgroundPreset.getSelected());
/*     */   }
/*     */   
/*     */   public boolean isGradientPreset() {
/* 309 */     return "Gradient".equalsIgnoreCase(this.backgroundPreset.getSelected());
/*     */   }
/*     */   
/*     */   private boolean isCustomColorPreset() {
/* 313 */     return "Custom Color".equalsIgnoreCase(this.backgroundPreset.getSelected());
/*     */   }
/*     */   
/*     */   public int getResolvedGradientStartColor() {
/* 317 */     return applyColorOptions(this.gradientStartColor.getColor());
/*     */   }
/*     */   
/*     */   public int getResolvedGradientEndColor() {
/* 321 */     return applyColorOptions(this.gradientEndColor.getColor());
/*     */   }
/*     */   
/*     */   public String getGradientDirection() {
/* 325 */     return this.gradientDirection.getSelected();
/*     */   }
/*     */   
/*     */   public float getGradientAnimationOffset(long nowMs) {
/* 329 */     long nowNanos = System.nanoTime();
/* 330 */     if (this.gradientLastUpdateNanos < 0L) {
/* 331 */       this.gradientLastUpdateNanos = nowNanos;
/* 332 */       return this.gradientAnimationOffset;
/*     */     } 
/* 334 */     float deltaSeconds = class_3532.method_15363((float)(nowNanos - this.gradientLastUpdateNanos) / 1.0E9F, 0.0F, 0.1F);
/* 335 */     this.gradientLastUpdateNanos = nowNanos;
/* 336 */     float speed = Math.max(0.0F, this.gradientSpeed.getValue());
/* 337 */     this.gradientAnimationOffset = (this.gradientAnimationOffset + deltaSeconds * speed * 0.5F) % 1.0F;
/* 338 */     return this.gradientAnimationOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeGradientColors(int[] colors, long nowMs) {
/* 343 */     if (colors == null || colors.length < 4) {
/*     */       return;
/*     */     }
/* 346 */     if (!isGradientPreset()) {
/* 347 */       int color = getResolvedBackgroundColor(class_310.method_1551());
/* 348 */       colors[0] = color;
/* 349 */       colors[1] = color;
/* 350 */       colors[2] = color;
/* 351 */       colors[3] = color;
/*     */       return;
/*     */     } 
/* 354 */     int first = getResolvedGradientStartColor();
/* 355 */     int second = getResolvedGradientEndColor();
/* 356 */     int midpoint = blend(first, second, 0.5F);
/* 357 */     switch (this.gradientDirection.getSelected()) { case "Right to Left":
/* 358 */         setGradientCorners(colors, second, second, first, first); return;
/* 359 */       case "Top to Bottom": setGradientCorners(colors, first, second, first, second); return;
/* 360 */       case "Bottom to Top": setGradientCorners(colors, second, first, second, first); return;
/* 361 */       case "Diagonal Down": setGradientCorners(colors, first, midpoint, midpoint, second); return;
/* 362 */       case "Diagonal Up": setGradientCorners(colors, midpoint, second, first, midpoint); return;
/* 363 */       case "Pulse": setGradientCorners(colors, first, first, first, first); return; }
/* 364 */      setGradientCorners(colors, first, first, second, second);
/*     */   }
/*     */ 
/*     */   
/*     */   private int applyColorOptions(int color) {
/* 369 */     int alpha = class_3532.method_15340(Math.round((color >>> 24 & 0xFF) * this.backgroundOpacity.getValue() / 100.0F), 0, 255);
/* 370 */     return alpha << 24 | color & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   private static void setGradientCorners(int[] colors, int topLeft, int bottomLeft, int topRight, int bottomRight) {
/* 374 */     colors[0] = topLeft;
/* 375 */     colors[1] = bottomLeft;
/* 376 */     colors[2] = topRight;
/* 377 */     colors[3] = bottomRight;
/*     */   }
/*     */   
/*     */   private static int blend(int first, int second, float progress) {
/* 381 */     float t = class_3532.method_15363(progress, 0.0F, 1.0F);
/* 382 */     int a = Math.round((first >>> 24 & 0xFF) + ((second >>> 24 & 0xFF) - (first >>> 24 & 0xFF)) * t);
/* 383 */     int r = Math.round((first >>> 16 & 0xFF) + ((second >>> 16 & 0xFF) - (first >>> 16 & 0xFF)) * t);
/* 384 */     int g = Math.round((first >>> 8 & 0xFF) + ((second >>> 8 & 0xFF) - (first >>> 8 & 0xFF)) * t);
/* 385 */     int b = Math.round((first & 0xFF) + ((second & 0xFF) - (first & 0xFF)) * t);
/* 386 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   private static int adjustBrightness(int color, float multiplier) {
/* 390 */     int r = class_3532.method_15340(Math.round((color >>> 16 & 0xFF) * multiplier), 0, 255);
/* 391 */     int g = class_3532.method_15340(Math.round((color >>> 8 & 0xFF) * multiplier), 0, 255);
/* 392 */     int b = class_3532.method_15340(Math.round((color & 0xFF) * multiplier), 0, 255);
/* 393 */     return r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   public float getHudXRatio() {
/* 397 */     class_310 client = class_310.method_1551();
/* 398 */     if (client == null || client.method_22683() == null) {
/* 399 */       return 0.0F;
/*     */     }
/* 401 */     resolveForCurrentScreen();
/* 402 */     return class_3532.method_15363(this.hudX / Math.max(1.0F, client.method_22683().method_4486()), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getHudYRatio() {
/* 406 */     class_310 client = class_310.method_1551();
/* 407 */     if (client == null || client.method_22683() == null) {
/* 408 */       return 0.0F;
/*     */     }
/* 410 */     resolveForCurrentScreen();
/* 411 */     return class_3532.method_15363(this.hudY / Math.max(1.0F, client.method_22683().method_4502()), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void setHudXRatio(float ratio) {
/* 415 */     class_310 client = class_310.method_1551();
/* 416 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 419 */     this
/* 420 */       .hudX = class_3532.method_15363(ratio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4486());
/* 421 */     markResolvedForCurrentScreen();
/*     */   }
/*     */   
/*     */   public void setHudYRatio(float ratio) {
/* 425 */     class_310 client = class_310.method_1551();
/* 426 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 429 */     this
/* 430 */       .hudY = class_3532.method_15363(ratio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4502());
/* 431 */     markResolvedForCurrentScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHudPositionRatio(float xRatio, float yRatio) {
/* 436 */     class_310 client = class_310.method_1551();
/* 437 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 440 */     this
/* 441 */       .hudX = class_3532.method_15363(xRatio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4486());
/* 442 */     this
/* 443 */       .hudY = class_3532.method_15363(yRatio, 0.0F, 1.0F) * Math.max(1, client.method_22683().method_4502());
/* 444 */     markResolvedForCurrentScreen();
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
/* 460 */     class_310 client = class_310.method_1551();
/* 461 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 464 */     int screenWidth = client.method_22683().method_4486();
/* 465 */     int screenHeight = client.method_22683().method_4502();
/* 466 */     if (screenWidth <= 0 || screenHeight <= 0) {
/*     */       return;
/*     */     }
/* 469 */     if (this.hudRefWidth <= 0 || this.hudRefHeight <= 0) {
/*     */ 
/*     */       
/* 472 */       this.hudRefWidth = screenWidth;
/* 473 */       this.hudRefHeight = screenHeight;
/*     */       return;
/*     */     } 
/* 476 */     if (screenWidth == this.hudRefWidth && screenHeight == this.hudRefHeight) {
/*     */       return;
/*     */     }
/* 479 */     this.hudX *= screenWidth / this.hudRefWidth;
/* 480 */     this.hudY *= screenHeight / this.hudRefHeight;
/* 481 */     this.hudRefWidth = screenWidth;
/* 482 */     this.hudRefHeight = screenHeight;
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
/* 497 */     this.hudX = x;
/* 498 */     this.hudY = y;
/* 499 */     if (refWidth > 0 && refHeight > 0) {
/* 500 */       this.hudRefWidth = refWidth;
/* 501 */       this.hudRefHeight = refHeight;
/*     */     } else {
/* 503 */       markResolvedForCurrentScreen();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void markResolvedForCurrentScreen() {
/* 509 */     class_310 client = class_310.method_1551();
/* 510 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 513 */     this.hudRefWidth = client.method_22683().method_4486();
/* 514 */     this.hudRefHeight = client.method_22683().method_4502();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHudRefWidth() {
/* 519 */     return this.hudRefWidth;
/*     */   }
/*     */   
/*     */   public int getHudRefHeight() {
/* 523 */     return this.hudRefHeight;
/*     */   }
/*     */   
/*     */   public void setHudReference(int width, int height) {
/* 527 */     this.hudRefWidth = width;
/* 528 */     this.hudRefHeight = height;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\ArmorHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */