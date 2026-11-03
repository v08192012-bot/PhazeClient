/*     */ package vorga.phazeclient.api.system.hud;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5944;
/*     */ import net.minecraft.class_6367;
/*     */ 
/*     */ public class HudBuffer {
/*  12 */   public static volatile int activeCaptureTarget = -1;
/*     */   
/*     */   private class_6367 framebuffer;
/*  15 */   private long lastRenderTimeMs = 0L;
/*  16 */   private long lastLogicUpdateTimeMs = 0L;
/*  17 */   private long lastDataUpdateTimeMs = 0L;
/*  18 */   private float accumulatedLogicDeltaSeconds = 0.0F;
/*     */   private boolean hasContent = false;
/*  20 */   private int lastScreenWidth = 0;
/*  21 */   private int lastScreenHeight = 0;
/*     */   
/*     */   public boolean shouldUpdate(int targetFps) {
/*  24 */     if (targetFps <= 0) {
/*  25 */       return true;
/*     */     }
/*  27 */     long now = System.currentTimeMillis();
/*  28 */     long intervalMs = 1000L / targetFps;
/*  29 */     return (!this.hasContent || now - this.lastRenderTimeMs >= intervalMs);
/*     */   }
/*     */   
/*     */   public float getThrottledDelta(int targetFps, float deltaSeconds) {
/*  33 */     if (targetFps <= 0) {
/*  34 */       return deltaSeconds;
/*     */     }
/*     */     
/*  37 */     long now = System.currentTimeMillis();
/*  38 */     long intervalMs = 1000L / targetFps;
/*  39 */     this.accumulatedLogicDeltaSeconds += deltaSeconds;
/*  40 */     if (this.lastLogicUpdateTimeMs == 0L || now - this.lastLogicUpdateTimeMs >= intervalMs) {
/*  41 */       float result = this.accumulatedLogicDeltaSeconds;
/*  42 */       this.accumulatedLogicDeltaSeconds = 0.0F;
/*  43 */       this.lastLogicUpdateTimeMs = now;
/*  44 */       return result;
/*     */     } 
/*  46 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean shouldUpdateData(int targetFps) {
/*  50 */     if (targetFps <= 0) {
/*  51 */       return true;
/*     */     }
/*     */     
/*  54 */     long now = System.currentTimeMillis();
/*  55 */     long intervalMs = 1000L / targetFps;
/*  56 */     if (this.lastDataUpdateTimeMs == 0L || now - this.lastDataUpdateTimeMs >= intervalMs) {
/*  57 */       this.lastDataUpdateTimeMs = now;
/*  58 */       return true;
/*     */     } 
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   public void beginCapture() {
/*  64 */     class_310 mc = class_310.method_1551();
/*  65 */     int width = mc.method_22683().method_4489();
/*  66 */     int height = mc.method_22683().method_4506();
/*     */     
/*  68 */     if (this.framebuffer == null || this.lastScreenWidth != width || this.lastScreenHeight != height) {
/*  69 */       if (this.framebuffer != null) {
/*  70 */         this.framebuffer.method_1238();
/*     */       }
/*  72 */       this.framebuffer = new class_6367(width, height, true);
/*     */ 
/*     */       
/*  75 */       this.framebuffer.method_58226(9728);
/*  76 */       this.lastScreenWidth = width;
/*  77 */       this.lastScreenHeight = height;
/*  78 */       this.hasContent = false;
/*     */     } 
/*     */     
/*  81 */     this.framebuffer.method_1236(0.0F, 0.0F, 0.0F, 0.0F);
/*  82 */     this.framebuffer.method_1230();
/*  83 */     this.framebuffer.method_1235(false);
/*  84 */     activeCaptureTarget = this.framebuffer.field_1476;
/*     */   }
/*     */   
/*     */   public void endCapture() {
/*  88 */     activeCaptureTarget = -1;
/*  89 */     this.framebuffer.method_1240();
/*  90 */     class_310.method_1551().method_1522().method_1235(false);
/*  91 */     this.hasContent = true;
/*  92 */     this.lastRenderTimeMs = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void bindCaptureTarget() {
/*  96 */     if (this.framebuffer != null) {
/*  97 */       this.framebuffer.method_1235(false);
/*  98 */       activeCaptureTarget = this.framebuffer.field_1476;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasContent() {
/* 103 */     return this.hasContent;
/*     */   }
/*     */   
/*     */   public boolean drawCached() {
/* 107 */     if (this.framebuffer == null || !this.hasContent) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     class_310 mc = class_310.method_1551();
/* 112 */     int width = mc.method_22683().method_4489();
/* 113 */     int height = mc.method_22683().method_4506();
/*     */     
/* 115 */     mc.method_1522().method_1235(false);
/*     */     
/* 117 */     RenderSystem.enableBlend();
/* 118 */     RenderSystem.blendFunc(GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */     
/* 122 */     RenderSystem.disableDepthTest();
/* 123 */     RenderSystem.depthMask(false);
/* 124 */     RenderSystem.disableCull();
/* 125 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 126 */     GlStateManager._viewport(0, 0, width, height);
/*     */     
/* 128 */     class_5944 shader = RenderSystem.setShader(class_10142.field_53868);
/* 129 */     shader.method_62899("InSampler", this.framebuffer.method_30277());
/*     */     
/* 131 */     class_287 bufferBuilder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_29336);
/*     */ 
/*     */ 
/*     */     
/* 135 */     bufferBuilder.method_22912(0.0F, 0.0F, 0.0F);
/* 136 */     bufferBuilder.method_22912(1.0F, 0.0F, 0.0F);
/* 137 */     bufferBuilder.method_22912(1.0F, 1.0F, 0.0F);
/* 138 */     bufferBuilder.method_22912(0.0F, 1.0F, 0.0F);
/* 139 */     class_286.method_43433(bufferBuilder.method_60800());
/*     */     
/* 141 */     RenderSystem.setShaderTexture(0, 0);
/* 142 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 143 */     RenderSystem.depthMask(true);
/* 144 */     RenderSystem.enableDepthTest();
/* 145 */     RenderSystem.enableCull();
/* 146 */     RenderSystem.defaultBlendFunc();
/*     */     
/* 148 */     mc.method_1522().method_1235(true);
/* 149 */     return true;
/*     */   }
/*     */   
/*     */   public void invalidate() {
/* 153 */     this.hasContent = false;
/* 154 */     this.lastLogicUpdateTimeMs = 0L;
/* 155 */     this.lastDataUpdateTimeMs = 0L;
/* 156 */     this.accumulatedLogicDeltaSeconds = 0.0F;
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 160 */     if (this.framebuffer != null) {
/* 161 */       this.framebuffer.method_1238();
/* 162 */       this.framebuffer = null;
/*     */     } 
/* 164 */     this.hasContent = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\hud\HudBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */