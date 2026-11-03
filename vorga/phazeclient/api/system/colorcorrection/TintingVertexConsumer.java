/*     */ package vorga.phazeclient.api.system.colorcorrection;
/*     */ 
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_777;
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
/*     */ public class TintingVertexConsumer
/*     */   implements class_4588
/*     */ {
/*     */   private static final int PHAZE$PLAYER_MARKER_ALPHA = 254;
/*     */   private static final int PHAZE$ENTITY_MARKER_ALPHA = 253;
/*     */   private final class_4588 parent;
/*     */   private final WorldColorCorrectionController.Target target;
/*     */   private final boolean colorTransform;
/*     */   private final boolean alphaTransform;
/*     */   private final boolean entityTextureCorrection;
/*     */   private final boolean alphaBlend;
/*     */   private final float alphaMultiplier;
/*     */   private final float redMultiplier;
/*     */   private final float greenMultiplier;
/*     */   private final float blueMultiplier;
/*     */   private final float saturation;
/*     */   private final float brightness;
/*     */   
/*     */   public TintingVertexConsumer(class_4588 parent, WorldColorCorrectionController.Target target) {
/*  36 */     this.parent = parent;
/*  37 */     this.target = target;
/*  38 */     this.colorTransform = WorldColorCorrectionController.needsColorTransform(target);
/*  39 */     this.alphaTransform = WorldColorCorrectionController.needsAlphaTransform(target);
/*  40 */     this.entityTextureCorrection = (this.colorTransform && (target == WorldColorCorrectionController.Target.PLAYERS || target == WorldColorCorrectionController.Target.ENTITIES));
/*     */ 
/*     */ 
/*     */     
/*  44 */     if (this.colorTransform) {
/*  45 */       this.redMultiplier = WorldColorCorrectionController.getRed(target);
/*  46 */       this.greenMultiplier = WorldColorCorrectionController.getGreen(target);
/*  47 */       this.blueMultiplier = WorldColorCorrectionController.getBlue(target);
/*  48 */       this.saturation = WorldColorCorrectionController.getSaturation(target);
/*  49 */       this.brightness = WorldColorCorrectionController.getBrightness(target);
/*     */     } else {
/*  51 */       this.redMultiplier = 1.0F;
/*  52 */       this.greenMultiplier = 1.0F;
/*  53 */       this.blueMultiplier = 1.0F;
/*  54 */       this.saturation = 1.0F;
/*  55 */       this.brightness = 0.0F;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     this.alphaBlend = (this.alphaTransform && WorldColorCorrectionController.needsBlend(target));
/*  62 */     this
/*     */       
/*  64 */       .alphaMultiplier = this.alphaTransform ? WorldColorCorrectionController.getAlpha(target) : 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22912(float x, float y, float z) {
/*  69 */     this.parent.method_22912(x, y, z);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_1336(int red, int green, int blue, int alpha) {
/*  75 */     if (!this.colorTransform && !this.alphaTransform) {
/*  76 */       this.parent.method_1336(red, green, blue, alpha);
/*  77 */       return this;
/*     */     } 
/*  79 */     this.parent.method_39415(phaze$transformArgb(alpha << 24 | red << 16 | green << 8 | blue));
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22915(float red, float green, float blue, float alpha) {
/*  85 */     if (!this.colorTransform && !this.alphaTransform) {
/*  86 */       this.parent.method_22915(red, green, blue, alpha);
/*  87 */       return this;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     int argb = Math.round(alpha * 255.0F) << 24 | Math.round(red * 255.0F) << 16 | Math.round(green * 255.0F) << 8 | Math.round(blue * 255.0F);
/*  94 */     this.parent.method_39415(phaze$transformArgb(argb));
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_39415(int argb) {
/* 100 */     if (!this.colorTransform && !this.alphaTransform) {
/* 101 */       this.parent.method_39415(argb);
/* 102 */       return this;
/*     */     } 
/* 104 */     this.parent.method_39415(phaze$transformArgb(argb));
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_60832(int rgb) {
/* 110 */     if (!this.colorTransform && !this.alphaTransform) {
/* 111 */       this.parent.method_60832(rgb);
/* 112 */       return this;
/*     */     } 
/* 114 */     this.parent.method_39415(phaze$transformArgb(0xFF000000 | rgb & 0xFFFFFF));
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22913(float u, float v) {
/* 120 */     this.parent.method_22913(u, v);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_60796(int u, int v) {
/* 126 */     this.parent.method_60796(u, v);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22922(int uv) {
/* 132 */     this.parent.method_22922(uv);
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22921(int u, int v) {
/* 138 */     this.parent.method_22921(u, v);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_60803(int uv) {
/* 144 */     this.parent.method_60803(uv);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22914(float x, float y, float z) {
/* 150 */     this.parent.method_22914(x, y, z);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_23919(float x, float y, float z, int color, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {
/* 156 */     this.parent.method_23919(x, y, z, phaze$transformArgb(color), u, v, overlay, light, normalX, normalY, normalZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_22919(class_4587.class_4665 matrixEntry, class_777 quad, float red, float green, float blue, float alpha, int light, int overlay) {
/* 161 */     if (!this.colorTransform && !this.alphaTransform) {
/* 162 */       this.parent.method_22919(matrixEntry, quad, red, green, blue, alpha, light, overlay);
/*     */       
/*     */       return;
/*     */     } 
/* 166 */     int corrected = phaze$transformArgb(
/* 167 */         Math.round(alpha * 255.0F) << 24 | 
/* 168 */         Math.round(red * 255.0F) << 16 | 
/* 169 */         Math.round(green * 255.0F) << 8 | 
/* 170 */         Math.round(blue * 255.0F));
/*     */     
/* 172 */     this.parent.method_22919(matrixEntry, quad, (corrected >>> 16 & 0xFF) / 255.0F, (corrected >>> 8 & 0xFF) / 255.0F, (corrected & 0xFF) / 255.0F, (corrected >>> 24 & 0xFF) / 255.0F, light, overlay);
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
/*     */   public void method_22920(class_4587.class_4665 matrixEntry, class_777 quad, float[] brightnesses, float red, float green, float blue, float alpha, int[] lights, int overlay, boolean useQuadColorData) {
/* 186 */     if (!this.colorTransform && !this.alphaTransform) {
/* 187 */       this.parent.method_22920(matrixEntry, quad, brightnesses, red, green, blue, alpha, lights, overlay, useQuadColorData);
/*     */       
/*     */       return;
/*     */     } 
/* 191 */     int corrected = phaze$transformArgb(
/* 192 */         Math.round(alpha * 255.0F) << 24 | 
/* 193 */         Math.round(red * 255.0F) << 16 | 
/* 194 */         Math.round(green * 255.0F) << 8 | 
/* 195 */         Math.round(blue * 255.0F));
/*     */     
/* 197 */     this.parent.method_22920(matrixEntry, quad, brightnesses, (corrected >>> 16 & 0xFF) / 255.0F, (corrected >>> 8 & 0xFF) / 255.0F, (corrected & 0xFF) / 255.0F, (corrected >>> 24 & 0xFF) / 255.0F, lights, overlay, useQuadColorData);
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
/*     */   final int phaze$transformArgb(int argb) {
/* 212 */     int originalAlpha = argb >>> 24 & 0xFF;
/*     */ 
/*     */     
/* 215 */     int correctedAlpha = this.alphaTransform ? WorldColorCorrectionController.applyAlphaIntWith(originalAlpha, this.alphaBlend, this.alphaMultiplier) : originalAlpha;
/* 216 */     int encodedAlpha = phaze$encodeEntityMarkerAlpha(correctedAlpha);
/* 217 */     boolean shaderHandlesRgb = (this.entityTextureCorrection && encodedAlpha != correctedAlpha);
/* 218 */     int rgb = argb & 0xFFFFFF;
/* 219 */     if (this.colorTransform && !shaderHandlesRgb) {
/* 220 */       rgb = WorldColorCorrectionController.applyRgbWith(argb >>> 16 & 0xFF, argb >>> 8 & 0xFF, argb & 0xFF, this.redMultiplier, this.greenMultiplier, this.blueMultiplier, this.saturation, this.brightness);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     return encodedAlpha << 24 | rgb;
/*     */   }
/*     */   
/*     */   final int phaze$encodeEntityMarkerAlpha(int alpha) {
/* 235 */     if (!this.entityTextureCorrection || alpha < 250) {
/* 236 */       return alpha;
/*     */     }
/* 238 */     return (this.target == WorldColorCorrectionController.Target.PLAYERS) ? 
/* 239 */       254 : 
/* 240 */       253;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\TintingVertexConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */