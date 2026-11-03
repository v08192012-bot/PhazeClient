/*     */ package vorga.phazeclient.api.system.hud;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_10142;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5944;
/*     */ import net.minecraft.class_6367;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BatchedHudBuffer
/*     */ {
/*  26 */   public static final BatchedHudBuffer INSTANCE = new BatchedHudBuffer();
/*     */   
/*     */   private class_6367 fbo;
/*  29 */   private int lastWidth = -1;
/*  30 */   private int lastHeight = -1;
/*  31 */   private long lastRefreshMs = 0L;
/*     */   private boolean dirty = true;
/*     */   private boolean hasContent = false;
/*  34 */   private int targetFps = 30;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_276 realMainFramebuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetFps(int fps) {
/*  47 */     this.targetFps = Math.max(1, Math.min(240, fps));
/*     */   }
/*     */   
/*     */   public int getTargetFps() {
/*  51 */     return this.targetFps;
/*     */   }
/*     */   
/*     */   public void invalidate() {
/*  55 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public boolean hasContent() {
/*  59 */     return this.hasContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_6367 getActiveCaptureFramebuffer() {
/*  69 */     return (HudBuffer.activeCaptureTarget >= 0) ? this.fbo : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_276 getRealMainFramebuffer() {
/*  80 */     return this.realMainFramebuffer;
/*     */   }
/*     */   
/*     */   public boolean shouldRefresh(boolean force) {
/*  84 */     if (force || this.dirty || !this.hasContent) {
/*  85 */       return true;
/*     */     }
/*  87 */     long elapsed = System.currentTimeMillis() - this.lastRefreshMs;
/*  88 */     return (elapsed >= 1000L / this.targetFps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ensureFramebuffer() {
/*  97 */     class_310 mc = class_310.method_1551();
/*  98 */     if (mc == null || mc.method_22683() == null) {
/*  99 */       return false;
/*     */     }
/* 101 */     int w = mc.method_22683().method_4489();
/* 102 */     int h = mc.method_22683().method_4506();
/* 103 */     if (w <= 0 || h <= 0) {
/* 104 */       return false;
/*     */     }
/*     */     
/* 107 */     if (this.fbo == null || w != this.lastWidth || h != this.lastHeight) {
/* 108 */       if (this.fbo != null) {
/* 109 */         this.fbo.method_1238();
/* 110 */         this.fbo = null;
/*     */       } 
/* 112 */       this.fbo = new class_6367(w, h, true);
/*     */ 
/*     */ 
/*     */       
/* 116 */       this.fbo.method_58226(9728);
/* 117 */       this.lastWidth = w;
/* 118 */       this.lastHeight = h;
/* 119 */       this.hasContent = false;
/* 120 */       this.dirty = true;
/*     */     } 
/* 122 */     return true;
/*     */   }
/*     */   
/*     */   public void beginCapture() {
/* 126 */     if (!ensureFramebuffer()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     class_310 mc = class_310.method_1551();
/* 134 */     this.realMainFramebuffer = (mc != null) ? mc.method_1522() : null;
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
/* 177 */     GlStateManager._disableScissorTest();
/* 178 */     GlStateManager._colorMask(true, true, true, true);
/* 179 */     GlStateManager._depthMask(true);
/*     */     
/* 181 */     this.fbo.method_1236(0.0F, 0.0F, 0.0F, 0.0F);
/* 182 */     this.fbo.method_1230();
/* 183 */     this.fbo.method_1235(false);
/* 184 */     HudBuffer.activeCaptureTarget = this.fbo.field_1476;
/*     */   }
/*     */   
/*     */   public void endCapture() {
/* 188 */     if (this.fbo == null) {
/* 189 */       HudBuffer.activeCaptureTarget = -1;
/*     */       return;
/*     */     } 
/* 192 */     HudBuffer.activeCaptureTarget = -1;
/* 193 */     this.fbo.method_1240();
/* 194 */     class_310.method_1551().method_1522().method_1235(false);
/* 195 */     this.hasContent = true;
/* 196 */     this.dirty = false;
/* 197 */     this.lastRefreshMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void blit() {
/* 205 */     if (this.fbo == null || !this.hasContent) {
/*     */       return;
/*     */     }
/*     */     
/* 209 */     class_310 mc = class_310.method_1551();
/* 210 */     if (mc == null || mc.method_1522() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 214 */     int width = mc.method_22683().method_4489();
/* 215 */     int height = mc.method_22683().method_4506();
/*     */     
/* 217 */     mc.method_1522().method_1235(false);
/*     */     
/* 219 */     RenderSystem.enableBlend();
/* 220 */     RenderSystem.blendFunc(GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */     
/* 224 */     RenderSystem.disableDepthTest();
/* 225 */     RenderSystem.depthMask(false);
/* 226 */     RenderSystem.disableCull();
/* 227 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 228 */     GlStateManager._viewport(0, 0, width, height);
/*     */     
/* 230 */     class_5944 shader = RenderSystem.setShader(class_10142.field_53868);
/* 231 */     shader.method_62899("InSampler", this.fbo.method_30277());
/*     */     
/* 233 */     class_287 bb = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_29336);
/*     */ 
/*     */ 
/*     */     
/* 237 */     bb.method_22912(0.0F, 0.0F, 0.0F);
/* 238 */     bb.method_22912(1.0F, 0.0F, 0.0F);
/* 239 */     bb.method_22912(1.0F, 1.0F, 0.0F);
/* 240 */     bb.method_22912(0.0F, 1.0F, 0.0F);
/* 241 */     class_286.method_43433(bb.method_60800());
/*     */     
/* 243 */     RenderSystem.setShaderTexture(0, 0);
/* 244 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 245 */     RenderSystem.depthMask(true);
/* 246 */     RenderSystem.enableDepthTest();
/* 247 */     RenderSystem.enableCull();
/* 248 */     RenderSystem.defaultBlendFunc();
/*     */     
/* 250 */     mc.method_1522().method_1235(true);
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 254 */     if (this.fbo != null) {
/* 255 */       this.fbo.method_1238();
/* 256 */       this.fbo = null;
/*     */     } 
/* 258 */     this.hasContent = false;
/* 259 */     this.dirty = true;
/* 260 */     this.lastWidth = -1;
/* 261 */     this.lastHeight = -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\hud\BatchedHudBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */