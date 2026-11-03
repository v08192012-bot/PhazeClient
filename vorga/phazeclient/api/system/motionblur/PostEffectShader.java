/*     */ package vorga.phazeclient.api.system.motionblur;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.class_10151;
/*     */ import net.minecraft.class_279;
/*     */ import net.minecraft.class_283;
/*     */ import net.minecraft.class_284;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_9922;
/*     */ import net.minecraft.class_9960;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.mixins.GameRendererAccessor;
/*     */ import vorga.phazeclient.mixins.PostEffectProcessorAccessor;
/*     */ import vorga.phazeclient.mixins.ShaderLoaderAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PostEffectShader
/*     */ {
/*     */   private final class_2960 location;
/*     */   private final Consumer<PostEffectShader> initCallback;
/*     */   private class_279 processor;
/*     */   private boolean initialized = false;
/*     */   private boolean errored = false;
/*     */   
/*     */   public PostEffectShader(class_2960 location, Consumer<PostEffectShader> initCallback) {
/*  30 */     this.location = location;
/*  31 */     this.initCallback = initCallback;
/*     */   }
/*     */   
/*     */   public PostEffectShader(class_2960 location) {
/*  35 */     this(location, s -> {
/*     */         
/*     */         });
/*     */   } private void ensureInitialized() {
/*  39 */     if (this.initialized || this.errored)
/*     */       return;  try {
/*  41 */       class_310 client = class_310.method_1551();
/*  42 */       class_10151 shaderLoader = client.method_62887();
/*  43 */       class_10151.class_10170 cache = ((ShaderLoaderAccessor)shaderLoader).getCache();
/*  44 */       this.processor = cache.method_63523(this.location, class_9960.field_53902);
/*  45 */       this.initialized = true;
/*  46 */       this.initCallback.accept(this);
/*  47 */     } catch (Exception e) {
/*  48 */       this.errored = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void render(float tickDelta) {
/*  53 */     ensureInitialized();
/*  54 */     if (this.processor == null)
/*     */       return; 
/*  56 */     RenderSystem.disableBlend();
/*  57 */     RenderSystem.disableDepthTest();
/*  58 */     RenderSystem.resetTextureMatrix();
/*     */     
/*  60 */     class_310 client = class_310.method_1551();
/*  61 */     this.processor.method_1258(client.method_1522(), (class_9922)((GameRendererAccessor)client.field_1773).getPool());
/*  62 */     client.method_1522().method_1235(true);
/*     */     
/*  64 */     RenderSystem.disableBlend();
/*  65 */     RenderSystem.blendFunc(770, 771);
/*  66 */     RenderSystem.enableDepthTest();
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, float value) {
/*  70 */     forEachUniform(name, u -> u.method_1251(value));
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, float v0, float v1) {
/*  74 */     forEachUniform(name, u -> u.method_1255(v0, v1));
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, float v0, float v1, float v2) {
/*  78 */     forEachUniform(name, u -> u.method_1249(v0, v1, v2));
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, float v0, float v1, float v2, float v3) {
/*  82 */     forEachUniform(name, u -> u.method_35657(v0, v1, v2, v3));
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, int value) {
/*  86 */     forEachUniform(name, u -> u.method_35649(value));
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, int v0, int v1) {
/*  90 */     forEachUniform(name, u -> u.method_35650(v0, v1));
/*     */   }
/*     */   
/*     */   public void setUniformValue(String name, Matrix4f value) {
/*  94 */     forEachUniform(name, u -> u.method_1250(value));
/*     */   }
/*     */   
/*     */   public void reload() {
/*  98 */     this.processor = null;
/*  99 */     this.initialized = false;
/* 100 */     this.errored = false;
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 104 */     return this.initialized;
/*     */   }
/*     */   
/*     */   private void forEachUniform(String name, Consumer<class_284> action) {
/* 108 */     ensureInitialized();
/* 109 */     if (this.processor == null)
/*     */       return; 
/* 111 */     List<class_283> passes = ((PostEffectProcessorAccessor)this.processor).getPasses();
/* 112 */     for (class_283 pass : passes) {
/* 113 */       class_284 uniform = pass.method_62922().method_34582(name);
/* 114 */       if (uniform != null)
/* 115 */         action.accept(uniform); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\motionblur\PostEffectShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */