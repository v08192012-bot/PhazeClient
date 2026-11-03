/*     */ package vorga.phazeclient.base.util.render.shader;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_6367;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderHelper
/*     */ {
/*     */   private static Shader chromaShader;
/*     */   private static Shader solidShader;
/*     */   private static Shader balatroShader;
/*     */   private static Shader smokeShader;
/*     */   private static Shader stripesShader;
/*     */   private static Shader glowShader;
/*     */   private static Shader glassShader;
/*     */   private static Shader snowShader;
/*     */   
/*     */   public static void initShadersIfNeeded() {
/*  30 */     if (initialized)
/*     */       return;  try {
/*  32 */       chromaShader = new Shader("hand", "chroma");
/*  33 */       solidShader = new Shader("hand", "solid");
/*  34 */       balatroShader = new Shader("hand", "balatro");
/*  35 */       smokeShader = new Shader("hand", "smoke");
/*  36 */       stripesShader = new Shader("hand", "stripes");
/*  37 */       glowShader = new Shader("hand", "glow");
/*  38 */       glassShader = new Shader("hand", "glass");
/*  39 */       snowShader = new Shader("hand", "snow");
/*  40 */       blendShader = new Shader("hand", "blend");
/*  41 */       initialized = true;
/*  42 */     } catch (Exception e) {
/*  43 */       System.err.println("Failed to initialize hand shaders!");
/*  44 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   private static Shader blendShader; private static class_6367 copyFbo; private static class_6367 fbo1; private static class_6367 fbo2; private static class_6367 effectFbo; private static class_6367 solidFbo; private static class_6367 shader1Fbo; private static class_6367 shader2Fbo; private static boolean initialized = false;
/*     */   public static void checkFramebuffers() {
/*  49 */     class_310 mc = class_310.method_1551();
/*  50 */     if (mc == null || mc.method_22683() == null)
/*     */       return; 
/*  52 */     int width = mc.method_22683().method_4489();
/*  53 */     int height = mc.method_22683().method_4506();
/*     */     
/*  55 */     if (copyFbo == null || copyFbo.field_1482 != width || copyFbo.field_1481 != height) {
/*  56 */       if (copyFbo != null) {
/*  57 */         copyFbo.method_1238();
/*  58 */         fbo1.method_1238();
/*  59 */         fbo2.method_1238();
/*  60 */         effectFbo.method_1238();
/*  61 */         solidFbo.method_1238();
/*  62 */         if (shader1Fbo != null) shader1Fbo.method_1238(); 
/*  63 */         if (shader2Fbo != null) shader2Fbo.method_1238(); 
/*     */       } 
/*  65 */       copyFbo = new class_6367(width, height, true);
/*  66 */       fbo1 = new class_6367(width, height, true);
/*  67 */       fbo2 = new class_6367(width, height, true);
/*  68 */       effectFbo = new class_6367(width, height, true);
/*  69 */       solidFbo = new class_6367(width, height, true);
/*  70 */       shader1Fbo = new class_6367(width, height, true);
/*  71 */       shader2Fbo = new class_6367(width, height, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawFullScreenQuad() {
/*  76 */     RenderSystem.assertOnRenderThread();
/*  77 */     class_287 bufferBuilder = RenderSystem.renderThreadTesselator().method_60827(class_293.class_5596.field_27382, class_290.field_1592);
/*     */ 
/*     */ 
/*     */     
/*  81 */     bufferBuilder.method_22912(-1.0F, -1.0F, 0.0F);
/*  82 */     bufferBuilder.method_22912(1.0F, -1.0F, 0.0F);
/*  83 */     bufferBuilder.method_22912(1.0F, 1.0F, 0.0F);
/*  84 */     bufferBuilder.method_22912(-1.0F, 1.0F, 0.0F);
/*  85 */     class_286.method_43433(bufferBuilder.method_60800());
/*     */   }
/*     */   
/*     */   public static boolean isInitialized() {
/*  89 */     return initialized;
/*     */   }
/*     */   
/*     */   public static Shader getChromaShader() {
/*  93 */     return chromaShader;
/*     */   }
/*     */   
/*     */   public static Shader getSolidShader() {
/*  97 */     return solidShader;
/*     */   }
/*     */   
/*     */   public static Shader getBalatroShader() {
/* 101 */     return balatroShader;
/*     */   }
/*     */   
/*     */   public static Shader getSmokeShader() {
/* 105 */     return smokeShader;
/*     */   }
/*     */   
/*     */   public static class_6367 getCopyFbo() {
/* 109 */     return copyFbo;
/*     */   }
/*     */   
/*     */   public static class_6367 getFbo1() {
/* 113 */     return fbo1;
/*     */   }
/*     */   
/*     */   public static class_6367 getFbo2() {
/* 117 */     return fbo2;
/*     */   }
/*     */   
/*     */   public static class_6367 getEffectFbo() {
/* 121 */     return effectFbo;
/*     */   }
/*     */   
/*     */   public static class_6367 getSolidFbo() {
/* 125 */     return solidFbo;
/*     */   }
/*     */   
/*     */   public static Shader getStripesShader() {
/* 129 */     return stripesShader;
/*     */   }
/*     */   
/*     */   public static Shader getGlowShader() {
/* 133 */     return glowShader;
/*     */   }
/*     */   
/*     */   public static Shader getGlassShader() {
/* 137 */     return glassShader;
/*     */   }
/*     */   
/*     */   public static Shader getSnowShader() {
/* 141 */     return snowShader;
/*     */   }
/*     */   
/*     */   public static Shader getBlendShader() {
/* 145 */     return blendShader;
/*     */   }
/*     */   
/*     */   public static class_6367 getShader1Fbo() {
/* 149 */     return shader1Fbo;
/*     */   }
/*     */   
/*     */   public static class_6367 getShader2Fbo() {
/* 153 */     return shader2Fbo;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\shader\ShaderHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */