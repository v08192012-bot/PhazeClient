/*     */ package vorga.phazeclient.api.feature.module.setting.implement;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorSetting
/*     */   extends Setting
/*     */ {
/*     */   public void setPresets(int[] presets) {
/*  15 */     this.presets = presets; } public void setDefaultColor(Integer defaultColor) { this.defaultColor = defaultColor; } public void setOnChangeCallback(Consumer<Integer> onChangeCallback) { this.onChangeCallback = onChangeCallback; } public void setNoAlpha(boolean noAlpha) { this.noAlpha = noAlpha; } public void setPopupRow(boolean popupRow) { this.popupRow = popupRow; }
/*     */   
/*  17 */   private static final int[] DEFAULT_PRESETS = new int[] { -6969946, -1499549, -1618884, -1671646, -932849, -13710223, -15024996, -13273872, -13330213, -6596170, -5930259, -8943479, -4581296, -4571856, -4561637, -4088564, -14310566, -15362435, -13934912, -13993296, -8566895, -8034114, -10853018, -7597508, -7590364, -7582700, -7244278, -14910908, -15699618, -14661744, -14721915, -10602899, -10202993 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   private float hue = 0.0F; private float saturation = 1.0F; private float brightness = 1.0F; private int[] presets; private Integer defaultColor; private float alpha = 1.0F; private Consumer<Integer> onChangeCallback; public float getHue() { return this.hue; }
/*  27 */   public float getSaturation() { return this.saturation; }
/*  28 */   public float getBrightness() { return this.brightness; } public float getAlpha() {
/*  29 */     return this.alpha;
/*     */   }
/*  31 */   public int[] getPresets() { return this.presets; }
/*  32 */   public Integer getDefaultColor() { return this.defaultColor; } public Consumer<Integer> getOnChangeCallback() {
/*  33 */     return this.onChangeCallback;
/*     */   }
/*     */   
/*     */   private boolean noAlpha = false;
/*     */   private boolean popupRow = true;
/*     */   
/*     */   public boolean isPopupRow() {
/*  40 */     return this.popupRow;
/*     */   }
/*     */   public ColorSetting(String name, String description) {
/*  43 */     super(name, description);
/*  44 */     this.presets = DEFAULT_PRESETS;
/*     */   }
/*     */   
/*     */   public ColorSetting value(int value) {
/*  48 */     if (this.defaultColor == null) {
/*  49 */       this.defaultColor = Integer.valueOf(value);
/*     */     }
/*  51 */     setColorInternal(value);
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public ColorSetting presets(int... presets) {
/*  56 */     this.presets = presets;
/*  57 */     return this;
/*     */   }
/*     */   
/*     */   public ColorSetting visible(Supplier<Boolean> visible) {
/*  61 */     setVisible(visible);
/*  62 */     return this;
/*     */   }
/*     */   
/*     */   public ColorSetting onChange(Consumer<Integer> callback) {
/*  66 */     this.onChangeCallback = callback;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ColorSetting noAlpha() {
/*  72 */     this.noAlpha = true;
/*  73 */     this.alpha = 1.0F;
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public ColorSetting popupRow() {
/*  78 */     this.popupRow = true;
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isNoAlpha() {
/*  83 */     return this.noAlpha;
/*     */   }
/*     */   
/*     */   public int getColor() {
/*  87 */     return getColorWithAlpha() & 0xFFFFFF | Math.round(this.alpha * 255.0F) << 24;
/*     */   }
/*     */   
/*     */   public int getColorWithAlpha() {
/*  91 */     return HSBtoRGB(this.hue, this.saturation, this.brightness);
/*     */   }
/*     */   
/*     */   public ColorSetting setColor(int color) {
/*  95 */     if (this.defaultColor == null) {
/*  96 */       this.defaultColor = Integer.valueOf(color);
/*     */     }
/*  98 */     setColorInternal(color);
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   public void setHue(float hue) {
/* 103 */     this.hue = hue;
/* 104 */     notifyColorChange();
/*     */   }
/*     */   
/*     */   public void setSaturation(float saturation) {
/* 108 */     this.saturation = saturation;
/* 109 */     notifyColorChange();
/*     */   }
/*     */   
/*     */   public void setBrightness(float brightness) {
/* 113 */     this.brightness = brightness;
/* 114 */     notifyColorChange();
/*     */   }
/*     */   
/*     */   public void setAlpha(float alpha) {
/* 118 */     this.alpha = alpha;
/* 119 */     notifyColorChange();
/*     */   }
/*     */   
/*     */   private void notifyColorChange() {
/* 123 */     notifyChange();
/* 124 */     if (this.onChangeCallback != null) {
/* 125 */       this.onChangeCallback.accept(Integer.valueOf(getColor()));
/*     */     }
/*     */   }
/*     */   
/*     */   private void setColorInternal(int color) {
/* 130 */     float[] hsb = RGBtoHSB(
/* 131 */         getRed(color), 
/* 132 */         getGreen(color), 
/* 133 */         getBlue(color));
/*     */ 
/*     */     
/* 136 */     this.hue = hsb[0];
/* 137 */     this.saturation = hsb[1];
/* 138 */     this.brightness = hsb[2];
/* 139 */     this.alpha = MathUtil.getAlpha(color) / 255.0F;
/*     */     
/* 141 */     notifyColorChange();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/* 146 */     if (this.defaultColor == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     return (getColor() != this.defaultColor.intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 154 */     if (this.defaultColor != null) {
/* 155 */       setColorInternal(this.defaultColor.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getRed(int color) {
/* 160 */     return color >> 16 & 0xFF;
/*     */   }
/*     */   
/*     */   private static int getGreen(int color) {
/* 164 */     return color >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   private static int getBlue(int color) {
/* 168 */     return color & 0xFF;
/*     */   }
/*     */   
/*     */   private static float[] RGBtoHSB(int r, int g, int b) {
/* 172 */     float[] hsb = new float[3];
/*     */     
/* 174 */     float rf = r / 255.0F;
/* 175 */     float gf = g / 255.0F;
/* 176 */     float bf = b / 255.0F;
/*     */     
/* 178 */     float max = Math.max(rf, Math.max(gf, bf));
/* 179 */     float min = Math.min(rf, Math.min(gf, bf));
/* 180 */     float delta = max - min;
/*     */     
/* 182 */     hsb[2] = max;
/*     */     
/* 184 */     if (max != 0.0F) {
/* 185 */       hsb[1] = delta / max;
/*     */     } else {
/* 187 */       hsb[1] = 0.0F;
/*     */     } 
/*     */     
/* 190 */     if (delta == 0.0F) {
/* 191 */       hsb[0] = 0.0F;
/*     */     } else {
/* 193 */       if (rf == max) {
/* 194 */         hsb[0] = (gf - bf) / delta;
/* 195 */       } else if (gf == max) {
/* 196 */         hsb[0] = 2.0F + (bf - rf) / delta;
/*     */       } else {
/* 198 */         hsb[0] = 4.0F + (rf - gf) / delta;
/*     */       } 
/* 200 */       hsb[0] = hsb[0] / 6.0F;
/* 201 */       if (hsb[0] < 0.0F) {
/* 202 */         hsb[0] = hsb[0] + 1.0F;
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return hsb;
/*     */   }
/*     */   
/*     */   private static int HSBtoRGB(float hue, float saturation, float brightness) {
/* 210 */     int r = 0, g = 0, b = 0;
/*     */     
/* 212 */     if (saturation == 0.0F) {
/* 213 */       r = g = b = (int)(brightness * 255.0F + 0.5F);
/*     */     } else {
/* 215 */       float h = (hue - (float)Math.floor(hue)) * 6.0F;
/* 216 */       float f = h - (float)Math.floor(h);
/* 217 */       float p = brightness * (1.0F - saturation);
/* 218 */       float q = brightness * (1.0F - saturation * f);
/* 219 */       float t = brightness * (1.0F - saturation * (1.0F - f));
/*     */       
/* 221 */       switch ((int)h) {
/*     */         case 0:
/* 223 */           r = (int)(brightness * 255.0F + 0.5F);
/* 224 */           g = (int)(t * 255.0F + 0.5F);
/* 225 */           b = (int)(p * 255.0F + 0.5F);
/*     */           break;
/*     */         case 1:
/* 228 */           r = (int)(q * 255.0F + 0.5F);
/* 229 */           g = (int)(brightness * 255.0F + 0.5F);
/* 230 */           b = (int)(p * 255.0F + 0.5F);
/*     */           break;
/*     */         case 2:
/* 233 */           r = (int)(p * 255.0F + 0.5F);
/* 234 */           g = (int)(brightness * 255.0F + 0.5F);
/* 235 */           b = (int)(t * 255.0F + 0.5F);
/*     */           break;
/*     */         case 3:
/* 238 */           r = (int)(p * 255.0F + 0.5F);
/* 239 */           g = (int)(q * 255.0F + 0.5F);
/* 240 */           b = (int)(brightness * 255.0F + 0.5F);
/*     */           break;
/*     */         case 4:
/* 243 */           r = (int)(t * 255.0F + 0.5F);
/* 244 */           g = (int)(p * 255.0F + 0.5F);
/* 245 */           b = (int)(brightness * 255.0F + 0.5F);
/*     */           break;
/*     */         case 5:
/* 248 */           r = (int)(brightness * 255.0F + 0.5F);
/* 249 */           g = (int)(p * 255.0F + 0.5F);
/* 250 */           b = (int)(q * 255.0F + 0.5F);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 255 */     return 0xFF000000 | r << 16 | g << 8 | b;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\ColorSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */