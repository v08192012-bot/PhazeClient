/*     */ package vorga.phazeclient.api.system.motionblur;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3f;
/*     */ import vorga.phazeclient.implement.features.modules.other.MotionBlur;
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
/*     */ public class Shader
/*     */ {
/*     */   private final MotionBlur config;
/*     */   private final PostEffectShader motionBlurShader;
/*     */   private static final float FPS_SMOOTHING = 0.1F;
/*     */   private static final float TIER_GUARD = 0.06F;
/*  28 */   private long lastNano = System.nanoTime();
/*  29 */   private float currentBlur = 0.0F;
/*  30 */   private float currentFPS = 0.0F;
/*  31 */   private int sampleTier = -1;
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
/*  45 */   private int staticFrames = 0;
/*     */ 
/*     */   
/*     */   private static final int STATIC_FRAMES_TO_SKIP = 8;
/*     */ 
/*     */   
/*  51 */   private int lastSampleAmount = -1;
/*  52 */   private float lastViewWidth = -1.0F;
/*  53 */   private float lastViewHeight = -1.0F;
/*  54 */   private float lastHandDepthThreshold = Float.NaN;
/*     */   
/*     */   private boolean blurAlgorithmSent = false;
/*  57 */   private final Matrix4f tempPrevModelView = new Matrix4f();
/*  58 */   private final Matrix4f tempPrevProjection = new Matrix4f();
/*  59 */   private final Matrix4f tempProjInverse = new Matrix4f();
/*  60 */   private final Matrix4f tempMvInverse = new Matrix4f();
/*     */   
/*     */   public Shader(MotionBlur config) {
/*  63 */     this.config = config;
/*  64 */     this
/*  65 */       .motionBlurShader = new PostEffectShader(class_2960.method_60655("phazeclient", "motion_blur"), shader -> shader.setUniformValue("BlendFactor", config.getStrength()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyMotionBlurBeforeHands() {
/*  71 */     long now = System.nanoTime();
/*  72 */     float deltaTime = (float)(now - this.lastNano) / 1.0E9F;
/*  73 */     this.lastNano = now;
/*     */     
/*  75 */     if (deltaTime > 0.0F && deltaTime < 1.0F) {
/*  76 */       float instantFps = 1.0F / deltaTime;
/*  77 */       this
/*     */         
/*  79 */         .currentFPS = (this.currentFPS <= 0.0F) ? instantFps : (this.currentFPS + (instantFps - this.currentFPS) * 0.1F);
/*     */     } else {
/*  81 */       this.currentFPS = 0.0F;
/*     */     } 
/*     */     
/*  84 */     if (this.config.getStrength() == 0.0F || !this.config.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     if (this.staticFrames >= 8) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       leaveRenderStateAsPassWould();
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     applyMotionBlur();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void leaveRenderStateAsPassWould() {
/* 107 */     class_310 client = class_310.method_1551();
/* 108 */     if (client == null || client.method_1522() == null) {
/*     */       return;
/*     */     }
/* 111 */     client.method_1522().method_1235(true);
/* 112 */     RenderSystem.enableDepthTest();
/* 113 */     RenderSystem.depthFunc(515);
/* 114 */     RenderSystem.depthMask(true);
/* 115 */     RenderSystem.enableBlend();
/* 116 */     RenderSystem.defaultBlendFunc();
/*     */   }
/*     */   
/*     */   private void applyMotionBlur() {
/* 120 */     class_310 client = class_310.method_1551();
/*     */     
/* 122 */     MonitorInfoProvider.updateDisplayInfo();
/* 123 */     int displayRefreshRate = MonitorInfoProvider.getRefreshRate();
/*     */     
/* 125 */     float baseStrength = this.config.getStrength();
/* 126 */     float scaledStrength = baseStrength;
/* 127 */     if (this.config.isUseRRC()) {
/* 128 */       float fpsOverRefresh = (displayRefreshRate > 0) ? (this.currentFPS / displayRefreshRate) : 1.0F;
/* 129 */       if (fpsOverRefresh < 1.0F) fpsOverRefresh = 1.0F; 
/* 130 */       scaledStrength = baseStrength * fpsOverRefresh;
/*     */     } 
/*     */     
/* 133 */     if (this.currentBlur != scaledStrength) {
/* 134 */       this.motionBlurShader.setUniformValue("BlendFactor", scaledStrength);
/* 135 */       this.currentBlur = scaledStrength;
/*     */     } 
/*     */     
/* 138 */     int sampleAmount = getSampleAmountForFPS(this.currentFPS);
/* 139 */     if (sampleAmount != this.lastSampleAmount) {
/* 140 */       this.motionBlurShader.setUniformValue("motionBlurSamples", sampleAmount);
/* 141 */       this.motionBlurShader.setUniformValue("halfSamples", sampleAmount / 2);
/* 142 */       this.motionBlurShader.setUniformValue("inverseSamples", 1.0F / sampleAmount);
/* 143 */       this.lastSampleAmount = sampleAmount;
/*     */     } 
/*     */     
/* 146 */     float viewWidth = (client.method_1522()).field_1480;
/* 147 */     float viewHeight = (client.method_1522()).field_1477;
/* 148 */     if (viewWidth != this.lastViewWidth || viewHeight != this.lastViewHeight) {
/* 149 */       this.motionBlurShader.setUniformValue("view_res", viewWidth, viewHeight);
/* 150 */       this.lastViewWidth = viewWidth;
/* 151 */       this.lastViewHeight = viewHeight;
/*     */     } 
/*     */     
/* 154 */     if (!this.blurAlgorithmSent) {
/* 155 */       this.motionBlurShader.setUniformValue("blurAlgorithm", 1);
/* 156 */       this.blurAlgorithmSent = true;
/*     */     } 
/*     */     
/* 159 */     float handDepthThreshold = this.config.getHandDepthThreshold();
/* 160 */     if (handDepthThreshold != this.lastHandDepthThreshold) {
/* 161 */       this.motionBlurShader.setUniformValue("handDepthThreshold", handDepthThreshold);
/* 162 */       this.lastHandDepthThreshold = handDepthThreshold;
/*     */     } 
/*     */     
/* 165 */     this.motionBlurShader.render(0.0F);
/*     */     
/* 167 */     RenderSystem.enableDepthTest();
/* 168 */     RenderSystem.depthFunc(515);
/* 169 */     RenderSystem.depthMask(true);
/* 170 */     RenderSystem.enableBlend();
/* 171 */     RenderSystem.defaultBlendFunc();
/*     */   }
/*     */   
/*     */   private int getSampleAmountForFPS(float fps) {
/* 175 */     int quality = this.config.getQuality();
/*     */     
/* 177 */     switch (quality) { case 0: 
/*     */       case 1: 
/*     */       case 2: 
/*     */       case 3: 
/*     */       default:
/* 182 */         break; }  int baseSamples = 12;
/*     */ 
/*     */     
/* 185 */     this.sampleTier = resolveSampleTier(fps);
/*     */     
/* 187 */     switch (this.sampleTier) { case 0: case 1: case 3:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 191 */       baseSamples;
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
/*     */   private int resolveSampleTier(float fps) {
/* 207 */     if (this.sampleTier < 0) {
/* 208 */       return (fps < 30.0F) ? 0 : ((fps < 60.0F) ? 1 : ((fps > 144.0F) ? 3 : 2));
/*     */     }
/* 210 */     if (this.sampleTier > 0 && fps < tierLowerBound(this.sampleTier) * 0.94F) {
/* 211 */       return this.sampleTier - 1;
/*     */     }
/* 213 */     if (this.sampleTier < 3 && fps > tierUpperBound(this.sampleTier) * 1.06F) {
/* 214 */       return this.sampleTier + 1;
/*     */     }
/* 216 */     return this.sampleTier;
/*     */   }
/*     */   
/*     */   private static float tierLowerBound(int tier) {
/* 220 */     switch (tier) { case 1: case 2: case 3:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 224 */       0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float tierUpperBound(int tier) {
/* 229 */     switch (tier) { case 0: case 1: case 2:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 233 */       Float.MAX_VALUE;
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
/*     */   public void setFrameMotionBlur(Matrix4f modelView, Matrix4f prevModelView, Matrix4f projection, Matrix4f prevProjection, Vector3f cameraPos, Vector3f prevCameraPos) {
/* 248 */     boolean unchanged = (modelView.equals(prevModelView) && projection.equals(prevProjection) && cameraPos.equals(prevCameraPos));
/* 249 */     this.staticFrames = unchanged ? Math.min(this.staticFrames + 1, 8) : 0;
/*     */     
/* 251 */     this.motionBlurShader.setUniformValue("mvInverse", this.tempMvInverse.set((Matrix4fc)modelView).invert());
/* 252 */     this.motionBlurShader.setUniformValue("projInverse", this.tempProjInverse.set((Matrix4fc)projection).invert());
/* 253 */     this.motionBlurShader.setUniformValue("prevModelView", this.tempPrevModelView.set((Matrix4fc)prevModelView));
/* 254 */     this.motionBlurShader.setUniformValue("prevProjection", this.tempPrevProjection.set((Matrix4fc)prevProjection));
/* 255 */     this.motionBlurShader.setUniformValue("cameraPos", cameraPos.x, cameraPos.y, cameraPos.z);
/* 256 */     this.motionBlurShader.setUniformValue("prevCameraPos", prevCameraPos.x, prevCameraPos.y, prevCameraPos.z);
/*     */   }
/*     */   
/*     */   public void updateBlurStrength(float strength) {
/* 260 */     this.motionBlurShader.setUniformValue("BlendFactor", strength);
/* 261 */     this.currentBlur = strength;
/*     */   }
/*     */   
/*     */   public void reload() {
/* 265 */     this.motionBlurShader.reload();
/*     */ 
/*     */ 
/*     */     
/* 269 */     this.lastSampleAmount = -1;
/* 270 */     this.lastViewWidth = -1.0F;
/* 271 */     this.lastViewHeight = -1.0F;
/* 272 */     this.lastHandDepthThreshold = Float.NaN;
/* 273 */     this.blurAlgorithmSent = false;
/* 274 */     this.currentBlur = Float.NaN;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\motionblur\Shader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */