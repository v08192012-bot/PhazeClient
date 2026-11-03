/*     */ package vorga.phazeclient.api.system.colorcorrection;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_2960;
/*     */ import vorga.phazeclient.api.system.motionblur.PostEffectShader;
/*     */ import vorga.phazeclient.implement.features.modules.other.ColorCorrection;
/*     */ 
/*     */ public class ColorCorrectionShader {
/*     */   private final ColorCorrection config;
/*     */   private final PostEffectShader shader;
/*     */   private float lastBrightness;
/*     */   private float lastContrast;
/*     */   private float lastSaturation;
/*     */   private float lastHue;
/*     */   private float lastGamma;
/*     */   private float lastTemperature;
/*     */   private float lastVibrance;
/*     */   
/*     */   public ColorCorrectionShader(ColorCorrection config) {
/*  20 */     this.lastBrightness = Float.NaN;
/*  21 */     this.lastContrast = Float.NaN;
/*  22 */     this.lastSaturation = Float.NaN;
/*  23 */     this.lastHue = Float.NaN;
/*  24 */     this.lastGamma = Float.NaN;
/*  25 */     this.lastTemperature = Float.NaN;
/*  26 */     this.lastVibrance = Float.NaN;
/*     */     this.config = config;
/*     */     this.shader = new PostEffectShader(class_2960.method_60655("phazeclient", "color_correction"));
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
/*     */   private boolean isIdentity() {
/*  42 */     return (this.config.getBrightness() == 0.0F && this.config
/*  43 */       .getContrast() == 1.0F && this.config
/*  44 */       .getSaturation() == 1.0F && this.config
/*  45 */       .getHue() == 0.0F && this.config
/*  46 */       .getGamma() == 1.0F && this.config
/*  47 */       .getTemperature() == 0.0F && this.config
/*  48 */       .getVibrance() == 0.0F);
/*     */   }
/*     */   
/*     */   public void apply() {
/*  52 */     if (!this.config.isEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (isIdentity())
/*     */       return; 
/*  61 */     float brightness = this.config.getBrightness();
/*  62 */     float contrast = this.config.getContrast();
/*  63 */     float saturation = this.config.getSaturation();
/*  64 */     float hue = this.config.getHue();
/*  65 */     float gamma = this.config.getGamma();
/*  66 */     float temperature = this.config.getTemperature();
/*  67 */     float vibrance = this.config.getVibrance();
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (brightness != this.lastBrightness) {
/*  72 */       this.shader.setUniformValue("Brightness", brightness);
/*  73 */       this.lastBrightness = brightness;
/*     */     } 
/*  75 */     if (contrast != this.lastContrast) {
/*  76 */       this.shader.setUniformValue("Contrast", contrast);
/*  77 */       this.lastContrast = contrast;
/*     */     } 
/*  79 */     if (saturation != this.lastSaturation) {
/*  80 */       this.shader.setUniformValue("Saturation", saturation);
/*  81 */       this.lastSaturation = saturation;
/*     */     } 
/*  83 */     if (hue != this.lastHue) {
/*  84 */       this.shader.setUniformValue("Hue", hue);
/*  85 */       this.lastHue = hue;
/*     */     } 
/*  87 */     if (gamma != this.lastGamma) {
/*  88 */       this.shader.setUniformValue("Gamma", gamma);
/*  89 */       this.lastGamma = gamma;
/*     */     } 
/*  91 */     if (temperature != this.lastTemperature) {
/*  92 */       this.shader.setUniformValue("Temperature", temperature);
/*  93 */       this.lastTemperature = temperature;
/*     */     } 
/*  95 */     if (vibrance != this.lastVibrance) {
/*  96 */       this.shader.setUniformValue("Vibrance", vibrance);
/*  97 */       this.lastVibrance = vibrance;
/*     */     } 
/*     */     
/* 100 */     this.shader.render(0.0F);
/*     */     
/* 102 */     RenderSystem.enableDepthTest();
/* 103 */     RenderSystem.depthFunc(515);
/* 104 */     RenderSystem.depthMask(true);
/* 105 */     RenderSystem.enableBlend();
/* 106 */     RenderSystem.defaultBlendFunc();
/*     */   }
/*     */   
/*     */   public void reload() {
/* 110 */     this.shader.reload();
/*     */ 
/*     */     
/* 113 */     this.lastBrightness = Float.NaN;
/* 114 */     this.lastContrast = Float.NaN;
/* 115 */     this.lastSaturation = Float.NaN;
/* 116 */     this.lastHue = Float.NaN;
/* 117 */     this.lastGamma = Float.NaN;
/* 118 */     this.lastTemperature = Float.NaN;
/* 119 */     this.lastVibrance = Float.NaN;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\ColorCorrectionShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */