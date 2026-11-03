/*     */ package vorga.phazeclient.api.system.font.msdf;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_10156;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_5944;
/*     */ import net.minecraft.class_9801;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ 
/*     */ public final class MsdfRenderer {
/*  20 */   private static final class_310 MC = class_310.method_1551();
/*     */   
/*     */   private static boolean msdfFailed = false;
/*  23 */   public static final class_10156 MSDF_FONT_SHADER_KEY = new class_10156(
/*  24 */       class_2960.method_60655("phaze", "core/msdf_font"), class_290.field_1575, class_10149.field_53930);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderText(MsdfFont font, String text, float size, int color, Matrix4f matrix, float x, float y, float z) {
/*  33 */     renderText(font, text, size, color, matrix, x, y, z, 0.05F, 0.5F);
/*     */   }
/*     */   
/*     */   public static void renderText(MsdfFont font, String text, float size, int color, Matrix4f matrix, float x, float y, float z, float thickness, float smoothness) {
/*  37 */     render(font, text, size, color, matrix, x, y, z, thickness, smoothness, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderTextFaded(MsdfFont font, String text, float size, int color, Matrix4f matrix, float x, float y, float z, float left, float right, float fadeWidth, float leftStrength, float rightStrength) {
/*  44 */     render(font, text, size, color, matrix, x, y, z, 0.05F, 0.5F, left, right, fadeWidth, leftStrength, rightStrength);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isUsingFallback() {
/*  49 */     return msdfFailed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void render(MsdfFont font, String text, float size, int color, Matrix4f matrix, float x, float y, float z, float thickness, float smoothness, float left, float right, float fadeWidth, float leftStrength, float rightStrength) {
/*  55 */     if (text == null || text.isEmpty() || color >>> 24 == 0)
/*  56 */       return;  if (msdfFailed || font == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  64 */       BatchedRectangle.flushIfBatching();
/*  65 */       fallback(text, color, matrix, x, y);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  75 */     BatchedRectangle.flushIfBatching();
/*     */     
/*     */     try {
/*  78 */       RenderSystem.enableBlend();
/*  79 */       RenderSystem.defaultBlendFunc();
/*  80 */       RenderSystem.disableCull();
/*  81 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*  82 */       RenderSystem.setShaderTexture(0, font.getTextureId());
/*     */       
/*  84 */       class_5944 shader = RenderSystem.setShader(MSDF_FONT_SHADER_KEY);
/*  85 */       if (shader != null) {
/*  86 */         shader.method_34582("Range").method_1251(font.getAtlas().range());
/*  87 */         shader.method_34582("Thickness").method_1251(thickness);
/*  88 */         shader.method_34582("Smoothness").method_1251(smoothness);
/*  89 */         shader.method_34582("Outline").method_35649(0);
/*  90 */         shader.method_34582("OutlineThickness").method_1251(0.0F);
/*  91 */         shader.method_34582("OutlineColor").method_35657(0.0F, 0.0F, 0.0F, 0.0F);
/*  92 */         shader.method_34582("EnableFadeout").method_35649(0);
/*  93 */         shader.method_34582("FadeoutStart").method_1251(0.0F);
/*  94 */         shader.method_34582("FadeoutEnd").method_1251(1.0F);
/*  95 */         shader.method_34582("MaxWidth").method_1251(0.0F);
/*  96 */         shader.method_34582("TextPosX").method_1251(x);
/*  97 */         shader.method_34582("LabelFadeEnabled").method_35649((fadeWidth > 0.0F) ? 1 : 0);
/*  98 */         shader.method_34582("LabelFadeBounds").method_35657(left, right, fadeWidth, 0.0F);
/*  99 */         shader.method_34582("LabelFadeStrength").method_1255(leftStrength, rightStrength);
/* 100 */         shader.method_34582("ColorModulator").method_35657(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 103 */       class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 104 */       font.applyGlyphs(matrix, (class_4588)builder, text, size, thickness * 0.5F * size, 0.0F, x - 0.75F, y + size * 0.7F, z, color);
/*     */       
/* 106 */       class_9801 builtBuffer = builder.method_60794();
/* 107 */       if (builtBuffer != null) {
/* 108 */         class_286.method_43433(builtBuffer);
/*     */       }
/*     */       
/* 111 */       RenderSystem.setShaderTexture(0, 0);
/* 112 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 113 */       RenderSystem.enableCull();
/* 114 */       RenderSystem.disableBlend();
/* 115 */     } catch (Exception ignored) {
/* 116 */       msdfFailed = true;
/* 117 */       fallback(text, color, matrix, x, y);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void fallback(String text, int color, Matrix4f matrix, float x, float y) {
/* 122 */     class_327 textRenderer = MC.field_1772;
/* 123 */     if (textRenderer != null)
/* 124 */       textRenderer.method_27521(text, x, y, color, false, matrix, (class_4597)MC.method_22940().method_23000(), class_327.class_6415.field_33993, 0, 15728880); 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\MsdfRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */