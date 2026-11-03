/*      */ package vorga.phazeclient.api.system.shape.implement;
/*      */ import com.mojang.blaze3d.platform.GlStateManager;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import java.util.List;
/*      */ import net.minecraft.class_10149;
/*      */ import net.minecraft.class_10156;
/*      */ import net.minecraft.class_243;
/*      */ import net.minecraft.class_276;
/*      */ import net.minecraft.class_286;
/*      */ import net.minecraft.class_287;
/*      */ import net.minecraft.class_289;
/*      */ import net.minecraft.class_290;
/*      */ import net.minecraft.class_293;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_3532;
/*      */ import net.minecraft.class_4184;
/*      */ import net.minecraft.class_5944;
/*      */ import net.minecraft.class_6367;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Vector3f;
/*      */ import org.joml.Vector4f;
/*      */ import org.joml.Vector4fc;
/*      */ import org.lwjgl.opengl.GL30C;
/*      */ import vorga.phazeclient.api.system.draw.DrawEngineImpl;
/*      */ import vorga.phazeclient.api.system.hud.BatchedHudBuffer;
/*      */ import vorga.phazeclient.api.system.hud.HudBuffer;
/*      */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*      */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*      */ import vorga.phazeclient.base.util.color.ColorUtil;
/*      */ import vorga.phazeclient.base.util.render.shader.ShaderHelper;
/*      */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*      */ 
/*      */ public class Blur implements Shape {
/*   36 */   public static final Blur INSTANCE = new Blur();
/*      */   private static final float HUD_GAUSSIAN_STRENGTH_MULTIPLIER = 2.5F;
/*      */   private static final int MAX_PREPARED_HUD_KAWASE_REGIONS = 32;
/*      */   private static final float HUD_FINE_KAWASE_THRESHOLD = 8.0F;
/*      */   private static final long MENU_BLUR_REFRESH_INTERVAL_NS = 16666667L;
/*      */   private static final long NAMETAG_BLUR_REFRESH_INTERVAL_NS = 8333333L;
/*      */   private static final int MENU_BLUR_CACHE_SLOTS = 4;
/*      */   private static final int MAX_HUD_BLUR_STATES = 32;
/*   44 */   private static final class_10156 MASK_SHADER_KEY = new class_10156(
/*   45 */       class_2960.method_60655("phaze", "core/blur"), class_290.field_1576, class_10149.field_53930);
/*      */ 
/*      */ 
/*      */   
/*   49 */   private static final class_10156 GAUSSIAN_SHADER_KEY = new class_10156(
/*   50 */       class_2960.method_60655("phaze", "core/blur_gaussian"), class_290.field_1592, class_10149.field_53930);
/*      */ 
/*      */ 
/*      */   
/*   54 */   private static final class_10156 DUAL_KAWASE_SHADER_KEY = new class_10156(
/*   55 */       class_2960.method_60655("phaze", "core/blur_dual_kawase"), class_290.field_1592, class_10149.field_53930);
/*      */ 
/*      */   
/*      */   private static final int MAX_PREPARED_HUD_GAUSSIAN_REGIONS = 32;
/*      */ 
/*      */   
/*   61 */   private final DrawEngineImpl drawEngine = new DrawEngineImpl();
/*      */   private class_276 input;
/*      */   private class_276 menuInput;
/*      */   private class_276 hudHalfInput;
/*      */   private class_276 nametagInput;
/*      */   private class_276 ping;
/*      */   private class_276 pong;
/*      */   private class_276 halfA;
/*      */   private class_276 halfB;
/*      */   private class_276 quarterA;
/*      */   private class_276 quarterB;
/*   72 */   private final MenuBlurSlot[] menuBlurSlots = new MenuBlurSlot[4];
/*   73 */   private final MenuBlurSlot[] hudBlurSlots = new MenuBlurSlot[4];
/*      */   private boolean cachedFramePrepared = false;
/*      */   private boolean hudBatchMode = false;
/*      */   private boolean hudBatchStateApplied = false;
/*   77 */   private class_5944 hudBatchMaskShader = null;
/*      */   private boolean forceHudRefresh = true;
/*   79 */   private double lastPlayerX = Double.NaN;
/*   80 */   private double lastPlayerY = Double.NaN;
/*   81 */   private double lastPlayerZ = Double.NaN;
/*   82 */   private long lastSpeedCheckTime = 0L;
/*   83 */   private float cachedPlayerSpeed = 0.0F;
/*      */   private boolean worldSpaceSpeedPrepared = false;
/*   85 */   private final long[] hudStateKeys = new long[32];
/*   86 */   private final boolean[] hudStateInitialized = new boolean[32];
/*   87 */   private int lastWorldCaptureWidth = -1;
/*   88 */   private int lastWorldCaptureHeight = -1;
/*      */   private boolean worldSpaceFramePrepared = false;
/*      */   private boolean nametagBlurWasActive = false;
/*   91 */   private long lastNametagBlurRefreshNs = 0L;
/*   92 */   private float lastDualKawaseRadius = -1.0F;
/*   93 */   private int lastDualKawaseWidth = -1;
/*   94 */   private int lastDualKawaseHeight = -1;
/*      */   private boolean dualKawasePrepared = false;
/*      */   private boolean menuBlurCurrentValid = false;
/*      */   private boolean menuBlurPreviousValid = false;
/*   98 */   private long menuBlurRegionKey = Long.MIN_VALUE;
/*   99 */   private long menuBlurLastRefreshNs = 0L;
/*  100 */   private long menuBlurTransitionStartNs = 0L;
/*  101 */   private float menuBlurRadius = -1.0F;
/*  102 */   private BlurRegion menuBlurRegion = null;
/*  103 */   private long menuInputLastCaptureNs = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   private long menuInputRevision = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class_276 menuOverlayInput;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   private long menuOverlayRevision = 0L;
/*      */   private boolean menuOverlayValid = false;
/*      */   private boolean hudInputValid = false;
/*  123 */   private long hudInputRevision = 0L;
/*  124 */   private long lastHudInputRefreshNs = 0L;
/*  125 */   private int hudBackgroundFps = 120;
/*  126 */   private long lastHudBackgroundStateKey = Long.MIN_VALUE;
/*      */   private boolean stableHudCapturePoint = false;
/*  128 */   private Object lastObservedScreen = null;
/*  129 */   private final long[] preparedHudGaussianRegionKeys = new long[32];
/*  130 */   private int preparedHudGaussianRegionCount = 0;
/*  131 */   private final Vector3f scratchScale = new Vector3f();
/*  132 */   private final Vector3f scratchPosition = new Vector3f();
/*  133 */   private final Vector4f scratchRound = new Vector4f();
/*  134 */   private static final Vector4f ZERO_ROUND = new Vector4f();
/*      */   
/*      */   public void setHudBackgroundFps(int fps) {
/*  137 */     int clamped = class_3532.method_15340(fps, 10, 360);
/*  138 */     if (this.hudBackgroundFps == clamped) {
/*      */       return;
/*      */     }
/*  141 */     this.hudBackgroundFps = clamped;
/*  142 */     this.lastHudInputRefreshNs = 0L;
/*  143 */     this.forceHudRefresh = true;
/*      */   }
/*      */   
/*      */   public int getHudBackgroundFps() {
/*  147 */     return this.hudBackgroundFps;
/*      */   }
/*      */   
/*      */   public void beginCachedFrame() {
/*  151 */     class_310 client = class_310.method_1551();
/*  152 */     Object currentScreen = (client == null) ? null : client.field_1755;
/*  153 */     if (currentScreen != this.lastObservedScreen) {
/*  154 */       this.lastObservedScreen = currentScreen;
/*  155 */       this.hudInputValid = false;
/*  156 */       this.lastHudBackgroundStateKey = Long.MIN_VALUE;
/*  157 */       this.forceHudRefresh = true;
/*  158 */       this.menuInputLastCaptureNs = 0L;
/*  159 */       this.menuOverlayValid = false;
/*  160 */       BatchedHudBuffer.INSTANCE.invalidate();
/*  161 */       invalidateHudKawaseCache();
/*  162 */       invalidateMenuBlurCache();
/*      */     } 
/*  164 */     this.cachedFramePrepared = false;
/*  165 */     this.hudBatchMode = true;
/*  166 */     this.hudBatchStateApplied = false;
/*  167 */     this.hudBatchMaskShader = null;
/*  168 */     this.preparedHudGaussianRegionCount = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void captureBaseFrameForBlur() {
/*  195 */     class_310 client = class_310.method_1551();
/*  196 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  204 */     this.stableHudCapturePoint = true;
/*      */     try {
/*  206 */       prepareFramebuffers(client, true, false);
/*      */     } finally {
/*  208 */       this.stableHudCapturePoint = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void endCachedFrame() {
/*  213 */     if (this.hudBatchStateApplied) {
/*  214 */       restoreRenderState(true);
/*      */     }
/*  216 */     this.hudBatchMode = false;
/*  217 */     this.hudBatchStateApplied = false;
/*  218 */     this.hudBatchMaskShader = null;
/*      */   }
/*      */   
/*      */   public void registerHudBlurState(int slot, long stateKey) {
/*  222 */     if (slot < 0 || slot >= this.hudStateKeys.length) {
/*  223 */       this.forceHudRefresh = true;
/*      */       return;
/*      */     } 
/*  226 */     if (!this.hudStateInitialized[slot] || this.hudStateKeys[slot] != stateKey) {
/*  227 */       this.hudStateInitialized[slot] = true;
/*  228 */       this.hudStateKeys[slot] = stateKey;
/*  229 */       this.forceHudRefresh = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void render(ShapeProperties shape) {
/*  235 */     render(shape, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderGaussian(ShapeProperties shape) {
/*  240 */     renderGaussian(shape, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderGaussianOverlay(ShapeProperties shape) {
/*  252 */     renderGaussian(shape, true);
/*      */   }
/*      */   
/*      */   private void renderGaussian(ShapeProperties shape, boolean overlaySource) {
/*  256 */     class_310 client = class_310.method_1551();
/*  257 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/*  260 */     BatchedRectangle.flushIfBatching();
/*  261 */     if (!prepareFramebuffers(client, false, false)) {
/*      */       return;
/*      */     }
/*  264 */     Theme theme = Theme.getInstance();
/*  265 */     float blurRadius = Math.max(0.0F, shape.getQuality()) * theme.getHudBlurRadiusMultiplier();
/*  266 */     BlurRegion blurRegion = computeHudGaussianRegion(client, shape, blurRadius);
/*  267 */     long now = System.nanoTime();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  281 */     boolean useOverlay = (overlaySource && this.menuOverlayValid && this.menuOverlayInput != null);
/*  282 */     if (!useOverlay && now - this.menuInputLastCaptureNs >= 16666667L) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  287 */       captureMenuInput(client, this.menuInput.field_1482, this.menuInput.field_1481);
/*  288 */       this.menuInputLastCaptureNs = now;
/*  289 */       this.menuInputRevision++;
/*      */     } 
/*      */     
/*  292 */     class_276 source = useOverlay ? this.menuOverlayInput : this.menuInput;
/*  293 */     long sourceRevision = useOverlay ? this.menuOverlayRevision : this.menuInputRevision;
/*      */ 
/*      */ 
/*      */     
/*  297 */     long regionKey = (blurRegion == null) ? 7640891576956012809L : computeMenuBlurRegionKey(blurRegion);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  303 */     if (useOverlay) {
/*  304 */       regionKey ^= 0x9E3779B97F4A7C15L;
/*      */     }
/*  306 */     MenuBlurSlot slot = acquireMenuBlurSlot(regionKey, now);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  311 */     boolean refresh = (!slot.valid || Math.abs(slot.blurRadius - blurRadius) >= 0.05F || slot.sourceRevision != sourceRevision);
/*      */     
/*  313 */     if (refresh && 
/*  314 */       applyDualKawaseBlur(client, source, blurRadius, blurRegion, slot.framebuffer)) {
/*  315 */       slot.valid = true;
/*  316 */       slot.blurRadius = blurRadius;
/*  317 */       slot.lastRefreshNs = now;
/*  318 */       slot.sourceRevision = sourceRevision;
/*      */     } 
/*      */     
/*  321 */     if (!slot.valid) {
/*  322 */       restoreRenderState(true);
/*      */       return;
/*      */     } 
/*  325 */     float scale = (float)client.method_22683().method_4495();
/*  326 */     float alpha = RenderSystem.getShaderColor()[3];
/*  327 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/*  328 */     Vector3f size = matrix4f.getScale(this.scratchScale).mul(scale);
/*  329 */     Vector4f round = this.scratchRound.set((Vector4fc)shape.getRound()).mul(size.y);
/*  330 */     float softness = Math.max(0.001F, shape.getSoftness());
/*  331 */     float width = shape.getWidth() * size.x;
/*  332 */     float height = shape.getHeight() * size.y;
/*  333 */     int color = ColorUtil.multAlpha((shape.getColor()).x, alpha);
/*  334 */     RenderSystem.enableBlend();
/*  335 */     RenderSystem.defaultBlendFunc();
/*  336 */     RenderSystem.disableCull();
/*  337 */     RenderSystem.disableDepthTest();
/*  338 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  339 */     this.drawEngine.quad(matrix4f, buffer, shape
/*      */ 
/*      */         
/*  342 */         .getX() - softness / 2.0F, shape
/*  343 */         .getY() - softness / 2.0F, shape
/*  344 */         .getWidth() + softness, shape
/*  345 */         .getHeight() + softness, color);
/*      */ 
/*      */     
/*  348 */     RenderSystem.setShaderTexture(0, slot.framebuffer.method_30277());
/*  349 */     RenderSystem.setShaderTexture(1, slot.framebuffer.method_30277());
/*  350 */     class_5944 shader = RenderSystem.setShader(MASK_SHADER_KEY);
/*  351 */     if (shader != null) {
/*  352 */       shader.method_35785("Size").method_1255(width, height);
/*  353 */       shader.method_35785("Radius").method_35652(round);
/*  354 */       shader.method_35785("RectMask").method_35649((maxCornerRadius(round) <= 0.001F) ? 1 : 0);
/*  355 */       shader.method_35785("Smoothness").method_1251(softness);
/*  356 */       shader.method_35785("BlurRadius").method_1251(0.0F);
/*  357 */       shader.method_35785("BlurMode").method_35649(0);
/*  358 */       shader.method_35785("TintColor").method_35657(0.0F, 0.0F, 0.0F, 0.0F);
/*  359 */       shader.method_35785("FrameMix").method_1251(1.0F);
/*  360 */       class_286.method_43433(buffer.method_60800());
/*      */     } else {
/*  362 */       buffer.method_60800();
/*      */     } 
/*  364 */     RenderSystem.enableDepthTest();
/*  365 */     restoreRenderState(true);
/*      */   }
/*      */   
/*      */   public void renderCached(ShapeProperties shape) {
/*  369 */     render(shape, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareWorldRectInput() {
/*  379 */     class_310 client = class_310.method_1551();
/*  380 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/*  383 */     if (!prepareFramebuffers(client, false, false)) {
/*      */       return;
/*      */     }
/*  386 */     prepareNametagInput(client);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderWorldRect(Matrix4f matrix, float x, float y, float width, float height, float quality, int tintColor, boolean drawFallback, float opacity) {
/*  400 */     float clampedOpacity = class_3532.method_15363(opacity, 0.0F, 1.0F);
/*  401 */     class_310 client = class_310.method_1551();
/*  402 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*  403 */       if (drawFallback) {
/*  404 */         drawWorldFallbackRect(matrix, x, y, width, height, tintColor);
/*      */       }
/*      */       return;
/*      */     } 
/*  408 */     if (!prepareFramebuffers(client, false, false)) {
/*  409 */       if (drawFallback) {
/*  410 */         drawWorldFallbackRect(matrix, x, y, width, height, tintColor);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  415 */     prepareNametagInput(client);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  420 */     RenderSystem.enableBlend();
/*  421 */     RenderSystem.defaultBlendFunc();
/*  422 */     RenderSystem.disableCull();
/*  423 */     RenderSystem.disableDepthTest();
/*  424 */     RenderSystem.depthMask(false);
/*  425 */     if (drawFallback) {
/*  426 */       drawWorldFallbackRectContents(matrix, x, y, width, height, tintColor);
/*      */     }
/*      */     
/*  429 */     if (clampedOpacity <= 0.001F) {
/*  430 */       restoreRenderState(true);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  437 */     RenderSystem.enableDepthTest();
/*  438 */     RenderSystem.depthFunc(515);
/*  439 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  440 */     int blurColor = class_3532.method_15340(Math.round(clampedOpacity * 255.0F), 0, 255) << 24 | 0xFFFFFF;
/*  441 */     this.drawEngine.quad(matrix, buffer, x, y, width, height, blurColor);
/*      */     
/*  443 */     RenderSystem.setShaderTexture(0, this.nametagInput.method_30277());
/*  444 */     RenderSystem.setShaderTexture(1, this.nametagInput.method_30277());
/*  445 */     class_5944 shader = RenderSystem.setShader(MASK_SHADER_KEY);
/*  446 */     if (shader != null) {
/*  447 */       Theme theme = Theme.getInstance();
/*  448 */       int blurMode = theme.getHudBlurMode();
/*  449 */       shader.method_35785("Size").method_1255(Math.max(1.0F, width), Math.max(1.0F, height));
/*  450 */       shader.method_35785("Radius").method_35652(ZERO_ROUND);
/*  451 */       shader.method_35785("RectMask").method_35649(1);
/*  452 */       shader.method_35785("Smoothness").method_1251(0.001F);
/*  453 */       shader.method_35785("BlurRadius").method_1251(Math.max(0.0F, quality) * theme.getHudBlurRadiusMultiplier());
/*  454 */       shader.method_35785("BlurMode").method_35649(blurMode);
/*  455 */       shader.method_35785("FrameMix").method_1251(1.0F);
/*  456 */       setTintUniform(shader, tintColor);
/*  457 */       class_286.method_43433(buffer.method_60800());
/*      */     } else {
/*  459 */       buffer.method_60800();
/*      */     } 
/*      */     
/*  462 */     restoreRenderState(true);
/*      */   }
/*      */   
/*      */   private void prepareNametagInput(class_310 client) {
/*  466 */     int framebufferWidth = Math.max(1, client.method_22683().method_4489());
/*  467 */     int framebufferHeight = Math.max(1, client.method_22683().method_4506());
/*      */ 
/*      */ 
/*      */     
/*  471 */     if (!this.worldSpaceFramePrepared || framebufferWidth != this.lastWorldCaptureWidth || framebufferHeight != this.lastWorldCaptureHeight) {
/*      */ 
/*      */       
/*  474 */       long now = System.nanoTime();
/*  475 */       boolean refresh = (!this.nametagBlurWasActive || framebufferWidth != this.lastWorldCaptureWidth || framebufferHeight != this.lastWorldCaptureHeight || now - this.lastNametagBlurRefreshNs >= 8333333L);
/*      */ 
/*      */ 
/*      */       
/*  479 */       this.nametagBlurWasActive = true;
/*  480 */       if (refresh) {
/*  481 */         captureNametagInput(client, framebufferWidth, framebufferHeight);
/*  482 */         this.lastNametagBlurRefreshNs = now;
/*  483 */         this.lastWorldCaptureWidth = framebufferWidth;
/*  484 */         this.lastWorldCaptureHeight = framebufferHeight;
/*      */       } 
/*  486 */       this.worldSpaceFramePrepared = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void drawWorldFallbackRect(Matrix4f matrix, float x, float y, float width, float height, int tintColor) {
/*  491 */     RenderSystem.enableBlend();
/*  492 */     RenderSystem.defaultBlendFunc();
/*  493 */     RenderSystem.disableCull();
/*  494 */     RenderSystem.disableDepthTest();
/*  495 */     RenderSystem.depthMask(false);
/*  496 */     drawWorldFallbackRectContents(matrix, x, y, width, height, tintColor);
/*  497 */     restoreRenderState(true);
/*      */   }
/*      */   
/*      */   private void drawWorldFallbackRectContents(Matrix4f matrix, float x, float y, float width, float height, int tintColor) {
/*  501 */     if ((tintColor >>> 24 & 0xFF) == 0 || width <= 0.0F || height <= 0.0F) {
/*      */       return;
/*      */     }
/*  504 */     class_287 fallback = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  505 */     this.drawEngine.quad(matrix, fallback, x, y, width, height, tintColor);
/*  506 */     RenderSystem.setShader(class_10142.field_53876);
/*  507 */     class_286.method_43433(fallback.method_60800());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginWorldSpaceFrame(boolean enabled) {
/*  516 */     this.worldSpaceFramePrepared = false;
/*  517 */     this.worldSpaceSpeedPrepared = false;
/*  518 */     if (!enabled) {
/*  519 */       this.nametagBlurWasActive = false;
/*      */     }
/*      */   }
/*      */   
/*      */   public void renderCachedBatch(List<ShapeProperties> shapes) {
/*  524 */     if (shapes == null || shapes.isEmpty()) {
/*      */       return;
/*      */     }
/*  527 */     class_310 client = class_310.method_1551();
/*  528 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/*  531 */     BatchedRectangle.flushIfBatching();
/*  532 */     if (!prepareFramebuffers(client, true, true)) {
/*      */       return;
/*      */     }
/*      */     
/*  536 */     boolean useHudBatch = this.hudBatchMode;
/*  537 */     if (useHudBatch) {
/*  538 */       if (!this.hudBatchStateApplied) {
/*  539 */         RenderSystem.enableBlend();
/*  540 */         RenderSystem.defaultBlendFunc();
/*  541 */         RenderSystem.disableCull();
/*  542 */         RenderSystem.disableDepthTest();
/*  543 */         this.hudBatchStateApplied = true;
/*      */       } 
/*      */     } else {
/*  546 */       RenderSystem.enableBlend();
/*  547 */       RenderSystem.defaultBlendFunc();
/*  548 */       RenderSystem.disableCull();
/*  549 */       RenderSystem.disableDepthTest();
/*      */     } 
/*      */     
/*  552 */     PreparedBlurState batchState = resolvePreparedBatchBlurState(client, shapes);
/*  553 */     class_5944 shader = null;
/*  554 */     PreparedBlurState activeState = null;
/*  555 */     for (ShapeProperties shape : shapes) {
/*  556 */       if (shape == null) {
/*      */         continue;
/*      */       }
/*  559 */       if (computeHudGaussianRegion(client, shape, 0.0F) == null) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  564 */       PreparedBlurState preparedState = (batchState != null) ? batchState : resolvePreparedBlurState(client, shape, true);
/*  565 */       if (preparedState == null) {
/*      */         continue;
/*      */       }
/*  568 */       if (!preparedState.matches(activeState)) {
/*  569 */         RenderSystem.setShaderTexture(0, preparedState.sourceTexture());
/*  570 */         RenderSystem.setShaderTexture(1, preparedState.sourceTexture());
/*  571 */         shader = RenderSystem.setShader(MASK_SHADER_KEY);
/*  572 */         this.hudBatchMaskShader = shader;
/*  573 */         if (shader == null) {
/*  574 */           if (!useHudBatch) {
/*  575 */             restoreRenderState(true);
/*      */           }
/*      */           return;
/*      */         } 
/*  579 */         activeState = preparedState;
/*      */       } 
/*  581 */       renderPreparedShapeWithBoundShader(shape, shader, preparedState);
/*      */     } 
/*      */     
/*  584 */     if (useHudBatch) {
/*  585 */       int target = (HudBuffer.activeCaptureTarget >= 0) ? HudBuffer.activeCaptureTarget : (client.method_1522()).field_1476;
/*  586 */       GlStateManager._glBindFramebuffer(36009, target);
/*      */     } else {
/*  588 */       restoreRenderState(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPreparedShapeWithBoundShader(ShapeProperties shape, class_5944 shader, PreparedBlurState preparedState) {
/*  593 */     class_310 client = class_310.method_1551();
/*  594 */     if (client == null || this.input == null || shader == null || preparedState == null) {
/*      */       return;
/*      */     }
/*      */     
/*  598 */     float scale = (float)client.method_22683().method_4495();
/*  599 */     float alpha = RenderSystem.getShaderColor()[3];
/*  600 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/*  601 */     Vector3f size = matrix4f.getScale(this.scratchScale).mul(scale);
/*  602 */     Vector4f round = this.scratchRound.set((Vector4fc)shape.getRound()).mul(size.y);
/*  603 */     float softness = Math.max(0.001F, shape.getSoftness());
/*  604 */     float width = shape.getWidth() * size.x;
/*  605 */     float height = shape.getHeight() * size.y;
/*  606 */     int color = ColorUtil.multAlpha((shape.getColor()).x, alpha);
/*      */     
/*  608 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  609 */     this.drawEngine.quad(matrix4f, buffer, shape
/*      */ 
/*      */         
/*  612 */         .getX() - softness / 2.0F, shape
/*  613 */         .getY() - softness / 2.0F, shape
/*  614 */         .getWidth() + softness, shape
/*  615 */         .getHeight() + softness, color);
/*      */ 
/*      */ 
/*      */     
/*  619 */     shader.method_35785("Size").method_1255(width, height);
/*  620 */     shader.method_35785("Radius").method_35652(round);
/*  621 */     shader.method_35785("RectMask").method_35649((maxCornerRadius(round) <= 0.001F) ? 1 : 0);
/*  622 */     shader.method_35785("Smoothness").method_1251(softness);
/*  623 */     shader.method_35785("BlurRadius").method_1251(preparedState.blurRadius());
/*  624 */     shader.method_35785("BlurMode").method_35649(preparedState.blurMode());
/*  625 */     shader.method_35785("TintColor").method_35657(0.0F, 0.0F, 0.0F, 0.0F);
/*  626 */     shader.method_35785("FrameMix").method_1251(1.0F);
/*  627 */     class_286.method_43433(buffer.method_60800());
/*      */   }
/*      */   
/*      */   private void render(ShapeProperties shape, boolean cacheFrame) {
/*  631 */     class_310 client = class_310.method_1551();
/*  632 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  644 */     BatchedRectangle.flushIfBatching();
/*      */     
/*  646 */     if (!prepareFramebuffers(client, cacheFrame, true)) {
/*      */       return;
/*      */     }
/*      */     
/*  650 */     Theme theme = Theme.getInstance();
/*  651 */     int blurMode = theme.getHudBlurMode();
/*  652 */     float blurRadius = Math.max(0.0F, shape.getQuality()) * theme.getHudBlurRadiusMultiplier();
/*      */     
/*  654 */     boolean useHudBatch = (cacheFrame && this.hudBatchMode);
/*  655 */     if (useHudBatch) {
/*  656 */       if (!this.hudBatchStateApplied) {
/*  657 */         RenderSystem.enableBlend();
/*  658 */         RenderSystem.defaultBlendFunc();
/*  659 */         RenderSystem.disableCull();
/*  660 */         RenderSystem.disableDepthTest();
/*  661 */         this.hudBatchStateApplied = true;
/*      */       } 
/*      */     } else {
/*  664 */       RenderSystem.enableBlend();
/*  665 */       RenderSystem.defaultBlendFunc();
/*  666 */       RenderSystem.disableCull();
/*  667 */       RenderSystem.disableDepthTest();
/*      */     } 
/*      */     
/*  670 */     if (!renderPreparedShape(shape, cacheFrame)) {
/*  671 */       if (!useHudBatch) {
/*  672 */         restoreRenderState(true);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  677 */     if (useHudBatch) {
/*  678 */       int target = (HudBuffer.activeCaptureTarget >= 0) ? HudBuffer.activeCaptureTarget : (client.method_1522()).field_1476;
/*  679 */       GlStateManager._glBindFramebuffer(36009, target);
/*      */     } 
/*      */     
/*  682 */     if (!useHudBatch) {
/*  683 */       restoreRenderState(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean renderPreparedShape(ShapeProperties shape, boolean useHudCache) {
/*  688 */     class_310 client = class_310.method_1551();
/*  689 */     if (client == null || this.input == null) {
/*  690 */       return false;
/*      */     }
/*      */     
/*  693 */     PreparedBlurState preparedState = resolvePreparedBlurState(client, shape, useHudCache);
/*  694 */     if (preparedState == null) {
/*  695 */       return false;
/*      */     }
/*      */     
/*  698 */     float scale = (float)client.method_22683().method_4495();
/*  699 */     float alpha = RenderSystem.getShaderColor()[3];
/*  700 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/*  701 */     Vector3f size = matrix4f.getScale(this.scratchScale).mul(scale);
/*  702 */     Vector4f round = this.scratchRound.set((Vector4fc)shape.getRound()).mul(size.y);
/*  703 */     float softness = Math.max(0.001F, shape.getSoftness());
/*  704 */     float width = shape.getWidth() * size.x;
/*  705 */     float height = shape.getHeight() * size.y;
/*  706 */     int color = ColorUtil.multAlpha((shape.getColor()).x, alpha);
/*      */     
/*  708 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  709 */     this.drawEngine.quad(matrix4f, buffer, shape
/*      */ 
/*      */         
/*  712 */         .getX() - softness / 2.0F, shape
/*  713 */         .getY() - softness / 2.0F, shape
/*  714 */         .getWidth() + softness, shape
/*  715 */         .getHeight() + softness, color);
/*      */ 
/*      */ 
/*      */     
/*  719 */     RenderSystem.setShaderTexture(0, preparedState.sourceTexture());
/*  720 */     RenderSystem.setShaderTexture(1, preparedState.sourceTexture());
/*  721 */     class_5944 shader = RenderSystem.setShader(MASK_SHADER_KEY);
/*  722 */     this.hudBatchMaskShader = shader;
/*  723 */     if (shader == null) {
/*  724 */       return false;
/*      */     }
/*  726 */     shader.method_35785("Size").method_1255(width, height);
/*  727 */     shader.method_35785("Radius").method_35652(round);
/*  728 */     shader.method_35785("RectMask").method_35649((maxCornerRadius(round) <= 0.001F) ? 1 : 0);
/*  729 */     shader.method_35785("Smoothness").method_1251(softness);
/*  730 */     shader.method_35785("BlurRadius").method_1251(preparedState.blurRadius());
/*  731 */     shader.method_35785("BlurMode").method_35649(preparedState.blurMode());
/*  732 */     shader.method_35785("TintColor").method_35657(0.0F, 0.0F, 0.0F, 0.0F);
/*  733 */     shader.method_35785("FrameMix").method_1251(1.0F);
/*  734 */     class_286.method_43433(buffer.method_60800());
/*  735 */     return true;
/*      */   }
/*      */   
/*      */   private PreparedBlurState resolvePreparedBlurState(class_310 client, ShapeProperties shape, boolean useHudCache) {
/*  739 */     if (client == null || shape == null || this.input == null || this.hudHalfInput == null) {
/*  740 */       return null;
/*      */     }
/*      */     
/*  743 */     Theme theme = Theme.getInstance();
/*  744 */     int blurMode = theme.getHudBlurMode();
/*  745 */     float blurRadius = Math.max(0.0F, shape.getQuality()) * theme.getHudBlurRadiusMultiplier();
/*  746 */     float hudGaussianRadius = blurRadius * 2.5F;
/*  747 */     int effectiveBlurMode = blurMode;
/*  748 */     float effectiveBlurRadius = blurRadius;
/*  749 */     int sourceTexture = this.input.method_30277();
/*      */     
/*  751 */     BlurRegion visibleRegion = computeHudGaussianRegion(client, shape, 0.0F);
/*  752 */     if (visibleRegion == null) {
/*  753 */       return null;
/*      */     }
/*      */     
/*  756 */     if (useHudCache && blurMode == 2 && blurRadius > 0.0F && cacheFrameReadyForHud()) {
/*  757 */       BlurRegion blurRegion = computeHudGaussianRegion(client, shape, hudGaussianRadius);
/*      */ 
/*      */       
/*  760 */       class_276 prepared = (hudGaussianRadius <= 8.0F) ? applyOptimizedHudFineKawaseBlur(client, hudGaussianRadius, blurRegion) : applyOptimizedHudKawaseBlur(client, hudGaussianRadius, blurRegion);
/*  761 */       if (prepared != null) {
/*  762 */         sourceTexture = prepared.method_30277();
/*  763 */         effectiveBlurMode = 0;
/*  764 */         effectiveBlurRadius = 0.0F;
/*      */       } else {
/*  766 */         effectiveBlurMode = 2;
/*  767 */         effectiveBlurRadius = blurRadius;
/*      */       } 
/*      */     } 
/*      */     
/*  771 */     return new PreparedBlurState(sourceTexture, effectiveBlurMode, effectiveBlurRadius);
/*      */   }
/*      */   
/*      */   private PreparedBlurState resolvePreparedBatchBlurState(class_310 client, List<ShapeProperties> shapes) {
/*  775 */     if (client == null || shapes == null || shapes.isEmpty() || !cacheFrameReadyForHud()) {
/*  776 */       return null;
/*      */     }
/*  778 */     Theme theme = Theme.getInstance();
/*  779 */     if (theme == null || theme.getHudBlurMode() != 2) {
/*  780 */       return null;
/*      */     }
/*      */     
/*  783 */     float sharedRadius = -1.0F;
/*  784 */     BlurRegion union = null;
/*  785 */     for (ShapeProperties shape : shapes) {
/*  786 */       if (shape == null)
/*  787 */         continue;  float radius = Math.max(0.0F, shape.getQuality()) * theme.getHudBlurRadiusMultiplier();
/*  788 */       if (radius <= 0.0F)
/*  789 */         continue;  if (sharedRadius < 0.0F) {
/*  790 */         sharedRadius = radius;
/*  791 */       } else if (Math.abs(sharedRadius - radius) >= 0.01F) {
/*  792 */         return null;
/*      */       } 
/*  794 */       BlurRegion region = computeHudGaussianRegion(client, shape, radius * 2.5F);
/*  795 */       if (region != null) {
/*  796 */         union = (union == null) ? region : unionBlurRegions(union, region);
/*      */       }
/*      */     } 
/*  799 */     if (sharedRadius <= 0.0F || union == null) {
/*  800 */       return null;
/*      */     }
/*      */     
/*  803 */     float hudRadius = sharedRadius * 2.5F;
/*      */ 
/*      */     
/*  806 */     class_276 prepared = (hudRadius <= 8.0F) ? applyOptimizedHudFineKawaseBlur(client, hudRadius, union) : applyOptimizedHudKawaseBlur(client, hudRadius, union);
/*  807 */     if (prepared == null) {
/*  808 */       return null;
/*      */     }
/*  810 */     return new PreparedBlurState(prepared.method_30277(), 0, 0.0F);
/*      */   }
/*      */   
/*      */   private boolean cacheFrameReadyForHud() {
/*  814 */     return (this.hudInputValid && this.hudHalfInput != null && this.pong != null);
/*      */   }
/*      */   
/*      */   private boolean prepareFramebuffers(class_310 client, boolean cacheFrame, boolean refreshNonCachedInput) {
/*  818 */     class_276 framebuffer = client.method_1522();
/*  819 */     int framebufferWidth = Math.max(1, client.method_22683().method_4489());
/*  820 */     int framebufferHeight = Math.max(1, client.method_22683().method_4506());
/*  821 */     boolean resized = false;
/*      */     
/*  823 */     if (this.input == null) {
/*  824 */       this.input = (class_276)new class_6367(framebufferWidth, framebufferHeight, false);
/*  825 */       this.input.method_58226(9729);
/*  826 */       this.menuInput = (class_276)new class_6367(framebufferWidth, framebufferHeight, false);
/*  827 */       this.menuInput.method_58226(9729);
/*  828 */       this.hudHalfInput = (class_276)new class_6367(Math.max(1, framebufferWidth / 2), Math.max(1, framebufferHeight / 2), false);
/*  829 */       this.hudHalfInput.method_58226(9729);
/*  830 */       this.nametagInput = (class_276)new class_6367(framebufferWidth, framebufferHeight, false);
/*  831 */       this.nametagInput.method_58226(9729);
/*  832 */       this.ping = (class_276)new class_6367(framebufferWidth, framebufferHeight, false);
/*  833 */       this.ping.method_58226(9729);
/*  834 */       this.pong = (class_276)new class_6367(framebufferWidth, framebufferHeight, false);
/*  835 */       this.pong.method_58226(9729);
/*  836 */       this.halfA = (class_276)new class_6367(Math.max(1, framebufferWidth / 2), Math.max(1, framebufferHeight / 2), false);
/*  837 */       this.halfA.method_58226(9729);
/*  838 */       this.halfB = (class_276)new class_6367(Math.max(1, framebufferWidth / 2), Math.max(1, framebufferHeight / 2), false);
/*  839 */       this.halfB.method_58226(9729);
/*  840 */       this.quarterA = (class_276)new class_6367(Math.max(1, framebufferWidth / 4), Math.max(1, framebufferHeight / 4), false);
/*  841 */       this.quarterA.method_58226(9729);
/*  842 */       this.quarterB = (class_276)new class_6367(Math.max(1, framebufferWidth / 4), Math.max(1, framebufferHeight / 4), false);
/*  843 */       this.quarterB.method_58226(9729);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  850 */       resized = true;
/*  851 */     } else if (this.input.field_1482 != framebufferWidth || this.input.field_1481 != framebufferHeight) {
/*  852 */       this.input.method_1234(framebufferWidth, framebufferHeight);
/*  853 */       this.input.method_58226(9729);
/*  854 */       this.menuInput.method_1234(framebufferWidth, framebufferHeight);
/*  855 */       this.menuInput.method_58226(9729);
/*  856 */       this.hudHalfInput.method_1234(Math.max(1, framebufferWidth / 2), Math.max(1, framebufferHeight / 2));
/*  857 */       this.hudHalfInput.method_58226(9729);
/*  858 */       this.nametagInput.method_1234(framebufferWidth, framebufferHeight);
/*  859 */       this.nametagInput.method_58226(9729);
/*  860 */       this.ping.method_1234(framebufferWidth, framebufferHeight);
/*  861 */       this.ping.method_58226(9729);
/*  862 */       this.pong.method_1234(framebufferWidth, framebufferHeight);
/*  863 */       this.pong.method_58226(9729);
/*  864 */       this.halfA.method_1234(Math.max(1, framebufferWidth / 2), Math.max(1, framebufferHeight / 2));
/*  865 */       this.halfA.method_58226(9729);
/*  866 */       this.halfB.method_1234(Math.max(1, framebufferWidth / 2), Math.max(1, framebufferHeight / 2));
/*  867 */       this.halfB.method_58226(9729);
/*  868 */       this.quarterA.method_1234(Math.max(1, framebufferWidth / 4), Math.max(1, framebufferHeight / 4));
/*  869 */       this.quarterA.method_58226(9729);
/*  870 */       this.quarterB.method_1234(Math.max(1, framebufferWidth / 4), Math.max(1, framebufferHeight / 4));
/*  871 */       this.quarterB.method_58226(9729);
/*      */ 
/*      */       
/*  874 */       for (MenuBlurSlot slot : this.menuBlurSlots) {
/*  875 */         if (slot != null) {
/*      */ 
/*      */           
/*  878 */           slot.framebuffer.method_1234(framebufferWidth, framebufferHeight);
/*  879 */           slot.framebuffer.method_58226(9729);
/*  880 */           slot.valid = false;
/*      */         } 
/*  882 */       }  for (MenuBlurSlot slot : this.hudBlurSlots) {
/*  883 */         if (slot != null) {
/*      */ 
/*      */           
/*  886 */           slot.framebuffer.method_1234(framebufferWidth, framebufferHeight);
/*  887 */           slot.framebuffer.method_58226(9729);
/*  888 */           slot.valid = false;
/*  889 */           slot.hudInputRevision = -1L;
/*  890 */           slot.hudRegionCount = 0;
/*      */         } 
/*  892 */       }  this.menuBlurCurrentValid = false;
/*  893 */       this.menuBlurPreviousValid = false;
/*  894 */       this.menuBlurRegion = null;
/*      */ 
/*      */       
/*  897 */       this.menuOverlayValid = false;
/*  898 */       this.hudInputValid = false;
/*  899 */       invalidateHudKawaseCache();
/*  900 */       resized = true;
/*      */     } 
/*      */     
/*  903 */     if (this.input == null || this.menuInput == null || this.hudHalfInput == null || this.nametagInput == null || this.ping == null || this.pong == null) {
/*  904 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  908 */     if (this.halfA == null || this.halfB == null || this.quarterA == null || this.quarterB == null) {
/*  909 */       return false;
/*      */     }
/*      */     
/*  912 */     if (cacheFrame) {
/*  913 */       if (this.cachedFramePrepared && !resized && !this.forceHudRefresh) {
/*  914 */         return true;
/*      */       }
/*  916 */       long now = System.nanoTime();
/*  917 */       long backgroundStateKey = computeHudBackgroundStateKey(client);
/*  918 */       long refreshIntervalNs = 1000000000L / class_3532.method_15340(this.hudBackgroundFps, 10, 360);
/*  919 */       boolean backgroundChanged = (backgroundStateKey != this.lastHudBackgroundStateKey);
/*  920 */       boolean refreshDue = (now - this.lastHudInputRefreshNs >= refreshIntervalNs);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  925 */       boolean guiOpen = (client.field_1755 != null);
/*  926 */       boolean needsCapture = (!this.hudInputValid || resized || ((!guiOpen || this.stableHudCapturePoint) && backgroundChanged && refreshDue));
/*      */ 
/*      */       
/*  929 */       if (needsCapture) {
/*  930 */         captureHudInput(client, framebufferWidth, framebufferHeight);
/*  931 */         this.lastHudInputRefreshNs = now;
/*  932 */         this.lastHudBackgroundStateKey = backgroundStateKey;
/*      */       } 
/*  934 */       if (this.forceHudRefresh) {
/*  935 */         invalidateHudKawaseCache();
/*  936 */         this.forceHudRefresh = false;
/*      */       } 
/*  938 */       this.cachedFramePrepared = true;
/*  939 */       return true;
/*      */     } 
/*      */     
/*  942 */     if (refreshNonCachedInput) {
/*  943 */       captureWorldInput(client, framebufferWidth, framebufferHeight);
/*      */     }
/*  945 */     return true;
/*      */   }
/*      */   
/*      */   private void captureWorldInput(class_310 client, int framebufferWidth, int framebufferHeight) {
/*  949 */     captureFramebufferInput(client, this.input, framebufferWidth, framebufferHeight, 9728);
/*  950 */     this.dualKawasePrepared = false;
/*  951 */     this.preparedHudGaussianRegionCount = 0;
/*      */   }
/*      */   
/*      */   private void captureMenuInput(class_310 client, int framebufferWidth, int framebufferHeight) {
/*  955 */     captureFramebufferInput(client, this.menuInput, framebufferWidth, framebufferHeight, 9728);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void captureMenuOverlayFrame() {
/*  972 */     class_310 client = class_310.method_1551();
/*  973 */     if (client == null || client.method_22683() == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  978 */     BatchedRectangle.flushIfBatching();
/*  979 */     if (!prepareFramebuffers(client, false, false)) {
/*      */       return;
/*      */     }
/*      */     
/*  983 */     int width = Math.max(1, client.method_22683().method_4489());
/*  984 */     int height = Math.max(1, client.method_22683().method_4506());
/*      */ 
/*      */ 
/*      */     
/*  988 */     if (this.menuOverlayInput == null) {
/*  989 */       this.menuOverlayInput = (class_276)new class_6367(width, height, false);
/*  990 */       this.menuOverlayInput.method_58226(9729);
/*  991 */     } else if (this.menuOverlayInput.field_1482 != width || this.menuOverlayInput.field_1481 != height) {
/*  992 */       this.menuOverlayInput.method_1234(width, height);
/*  993 */       this.menuOverlayInput.method_58226(9729);
/*      */     } 
/*      */     
/*  996 */     captureFramebufferInput(client, this.menuOverlayInput, width, height, 9728);
/*  997 */     this.menuOverlayRevision++;
/*  998 */     this.menuOverlayValid = true;
/*      */   }
/*      */   
/*      */   private void captureHudInput(class_310 client, int framebufferWidth, int framebufferHeight) {
/* 1002 */     captureFramebufferInput(client, this.input, framebufferWidth, framebufferHeight, 9728);
/* 1003 */     captureFramebufferInput(client, this.hudHalfInput, framebufferWidth, framebufferHeight, 9729);
/* 1004 */     this.hudInputValid = true;
/* 1005 */     this.hudInputRevision++;
/* 1006 */     this.dualKawasePrepared = false;
/* 1007 */     this.preparedHudGaussianRegionCount = 0;
/* 1008 */     invalidateHudKawaseCache();
/*      */   }
/*      */   
/*      */   private void captureNametagInput(class_310 client, int framebufferWidth, int framebufferHeight) {
/* 1012 */     captureFramebufferInput(client, this.nametagInput, framebufferWidth, framebufferHeight, 9728);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void captureFramebufferInput(class_310 client, class_276 target, int framebufferWidth, int framebufferHeight, int filter) {
/* 1027 */     class_276 framebuffer = (HudBuffer.activeCaptureTarget >= 0) ? BatchedHudBuffer.INSTANCE.getRealMainFramebuffer() : client.method_1522();
/* 1028 */     if (framebuffer == null) {
/* 1029 */       framebuffer = client.method_1522();
/*      */     }
/*      */ 
/*      */     
/* 1033 */     int restoreFramebuffer = (HudBuffer.activeCaptureTarget >= 0) ? HudBuffer.activeCaptureTarget : (client.method_1522()).field_1476;
/* 1034 */     GlStateManager._glBindFramebuffer(36008, framebuffer.field_1476);
/* 1035 */     GlStateManager._glBindFramebuffer(36009, target.field_1476);
/* 1036 */     GL30C.glBlitFramebuffer(0, 0, framebufferWidth, framebufferHeight, 0, 0, target.field_1482, target.field_1481, 16384, filter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     GlStateManager._glBindFramebuffer(36008, restoreFramebuffer);
/* 1049 */     GlStateManager._glBindFramebuffer(36009, restoreFramebuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean applyDualKawaseBlur(class_310 client, class_276 sourceInput, float blurRadius, BlurRegion region, class_276 output) {
/* 1059 */     if (sourceInput == null || output == null || this.halfA == null || this.halfB == null || this.quarterA == null || this.quarterB == null) {
/* 1060 */       return false;
/*      */     }
/* 1062 */     int w = sourceInput.field_1482;
/* 1063 */     int h = sourceInput.field_1481;
/*      */     
/* 1065 */     float quantizedRadius = class_3532.method_15363(blurRadius, 0.0F, 24.0F);
/* 1066 */     class_5944 shader = RenderSystem.setShader(DUAL_KAWASE_SHADER_KEY);
/* 1067 */     if (shader == null) {
/* 1068 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1073 */     float normalized = class_3532.method_15363(quantizedRadius / 8.0F, 0.0F, 1.0F);
/* 1074 */     float downOffset1 = quantizedRadius * 0.08F;
/* 1075 */     float downOffset2 = quantizedRadius * 0.1F;
/* 1076 */     runDualKawasePass(shader, sourceInput, this.halfA, downOffset1, true, region);
/* 1077 */     runDualKawasePass(shader, this.halfA, this.quarterA, downOffset2, true, region);
/*      */ 
/*      */ 
/*      */     
/* 1081 */     int passes = 2;
/* 1082 */     class_276 src = this.quarterA;
/* 1083 */     class_276 dst = this.quarterB;
/* 1084 */     for (int i = 0; i < passes; i++) {
/* 1085 */       float offset = quantizedRadius * (0.18F + i * (0.02F + normalized * 0.015F));
/* 1086 */       runDualKawasePass(shader, src, dst, offset, true, region);
/* 1087 */       class_276 tmp = src;
/* 1088 */       src = dst;
/* 1089 */       dst = tmp;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     float upOffset1 = quantizedRadius * 0.09F;
/* 1097 */     runDualKawasePass(shader, src, this.halfB, upOffset1, false, region);
/* 1098 */     blitColorRegion(this.halfB, output, region, 9729);
/*      */     
/* 1100 */     bindMainDrawTarget(client);
/* 1101 */     RenderSystem.viewport(0, 0, sourceInput.field_1482, sourceInput.field_1481);
/* 1102 */     this.dualKawasePrepared = true;
/* 1103 */     this.lastDualKawaseRadius = quantizedRadius;
/* 1104 */     this.lastDualKawaseWidth = w;
/* 1105 */     this.lastDualKawaseHeight = h;
/* 1106 */     return true;
/*      */   }
/*      */   
/*      */   private void blitColorRegion(class_276 source, class_276 target, BlurRegion region, int filter) {
/* 1110 */     BlurRegion sourceRegion = scaleBlurRegion(region, source.field_1482, source.field_1481);
/* 1111 */     BlurRegion targetRegion = scaleBlurRegion(region, target.field_1482, target.field_1481);
/* 1112 */     GlStateManager._glBindFramebuffer(36008, source.field_1476);
/* 1113 */     GlStateManager._glBindFramebuffer(36009, target.field_1476);
/* 1114 */     GL30C.glBlitFramebuffer(sourceRegion.x, sourceRegion.y, sourceRegion.x + sourceRegion.width, sourceRegion.y + sourceRegion.height, targetRegion.x, targetRegion.y, targetRegion.x + targetRegion.width, targetRegion.y + targetRegion.height, 16384, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BlurRegion scaleBlurRegion(BlurRegion region, int targetWidth, int targetHeight) {
/* 1129 */     if (region == null || this.input == null) {
/* 1130 */       return new BlurRegion(0, 0, targetWidth, targetHeight);
/*      */     }
/*      */     
/* 1133 */     float scaleX = targetWidth / this.input.field_1482;
/* 1134 */     float scaleY = targetHeight / this.input.field_1481;
/* 1135 */     int left = class_3532.method_15340(class_3532.method_15375(region.x * scaleX) - 2, 0, targetWidth);
/* 1136 */     int bottom = class_3532.method_15340(class_3532.method_15375(region.y * scaleY) - 2, 0, targetHeight);
/* 1137 */     int right = class_3532.method_15340(class_3532.method_15386((region.x + region.width) * scaleX) + 2, 0, targetWidth);
/* 1138 */     int top = class_3532.method_15340(class_3532.method_15386((region.y + region.height) * scaleY) + 2, 0, targetHeight);
/* 1139 */     return new BlurRegion(left, bottom, Math.max(1, right - left), Math.max(1, top - bottom));
/*      */   }
/*      */   
/*      */   private class_276 applyOptimizedHudKawaseBlur(class_310 client, float blurRadius, BlurRegion region) {
/* 1143 */     if (this.hudHalfInput == null || this.quarterA == null || this.quarterB == null || this.halfB == null || region == null) {
/* 1144 */       return null;
/*      */     }
/*      */     
/* 1147 */     int w = client.method_22683().method_4489();
/* 1148 */     int h = client.method_22683().method_4506();
/* 1149 */     float radius = class_3532.method_15363(blurRadius, 0.0F, 24.0F);
/* 1150 */     MenuBlurSlot slot = acquireHudBlurSlot(2, radius, System.nanoTime());
/* 1151 */     if (isHudBlurRegionPrepared(slot, region)) {
/* 1152 */       return slot.framebuffer;
/*      */     }
/*      */     
/* 1155 */     class_5944 shader = RenderSystem.setShader(DUAL_KAWASE_SHADER_KEY);
/* 1156 */     if (shader == null) {
/* 1157 */       return null;
/*      */     }
/*      */     
/* 1160 */     float normalized = class_3532.method_15363(radius / 24.0F, 0.0F, 1.0F);
/* 1161 */     runDualKawasePass(shader, this.hudHalfInput, this.quarterA, radius * 0.14F, true, region);
/*      */     
/* 1163 */     int quarterPasses = (radius > 16.0F) ? 2 : ((radius > 8.0F) ? 1 : 0);
/* 1164 */     class_276 src = this.quarterA;
/* 1165 */     class_276 dst = this.quarterB;
/* 1166 */     for (int i = 0; i < quarterPasses; i++) {
/* 1167 */       float offset = radius * (0.18F + i * (0.025F + normalized * 0.015F));
/* 1168 */       runDualKawasePass(shader, src, dst, offset, true, region);
/* 1169 */       class_276 swap = src;
/* 1170 */       src = dst;
/* 1171 */       dst = swap;
/*      */     } 
/*      */     
/* 1174 */     runDualKawasePass(shader, src, this.halfB, radius * 0.1F, false, region);
/* 1175 */     blitColorRegion(this.halfB, slot.framebuffer, region, 9729);
/* 1176 */     bindMainDrawTarget(client);
/* 1177 */     RenderSystem.viewport(0, 0, w, h);
/*      */     
/* 1179 */     slot.valid = false;
/* 1180 */     rememberHudBlurRegion(slot, region);
/* 1181 */     return slot.framebuffer;
/*      */   }
/*      */   
/*      */   private boolean isHudBlurRegionPrepared(MenuBlurSlot slot, BlurRegion region) {
/* 1185 */     for (int i = 0; i < slot.hudRegionCount; i++) {
/* 1186 */       BlurRegion prepared = slot.hudRegions[i];
/* 1187 */       if (prepared != null && region.x >= prepared.x && region.y >= prepared.y && region.x + region.width <= prepared.x + prepared.width && region.y + region.height <= prepared.y + prepared.height)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1192 */         return true;
/*      */       }
/*      */     } 
/* 1195 */     return false;
/*      */   }
/*      */   
/*      */   private void rememberHudBlurRegion(MenuBlurSlot slot, BlurRegion region) {
/* 1199 */     if (slot.hudRegionCount >= slot.hudRegions.length) {
/* 1200 */       slot.hudRegionCount = 0;
/*      */     }
/* 1202 */     slot.hudRegions[slot.hudRegionCount++] = region;
/*      */   }
/*      */   
/*      */   private void invalidateHudKawaseCache() {
/* 1206 */     for (MenuBlurSlot slot : this.hudBlurSlots) {
/* 1207 */       if (slot != null) {
/* 1208 */         slot.hudInputRevision = -1L;
/* 1209 */         slot.hudRegionCount = 0;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void invalidateMenuBlurCache() {
/* 1215 */     for (MenuBlurSlot slot : this.menuBlurSlots) {
/* 1216 */       if (slot != null) {
/* 1217 */         slot.valid = false;
/* 1218 */         slot.regionKey = Long.MIN_VALUE;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureBlurSlots(MenuBlurSlot[] slots) {
/*      */     int width;
/*      */     int height;
/* 1238 */     if (slots[0] != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1244 */     if (this.input != null) {
/* 1245 */       width = Math.max(1, this.input.field_1482);
/* 1246 */       height = Math.max(1, this.input.field_1481);
/*      */     } else {
/* 1248 */       class_310 client = class_310.method_1551();
/* 1249 */       width = Math.max(1, client.method_22683().method_4489());
/* 1250 */       height = Math.max(1, client.method_22683().method_4506());
/*      */     } 
/*      */     
/* 1253 */     for (int i = 0; i < slots.length; i++) {
/* 1254 */       class_6367 framebufferCache = new class_6367(width, height, false);
/* 1255 */       framebufferCache.method_58226(9729);
/* 1256 */       slots[i] = new MenuBlurSlot((class_276)framebufferCache);
/*      */     } 
/*      */   }
/*      */   
/*      */   private MenuBlurSlot acquireHudBlurSlot(int mode, float radius, long now) {
/* 1261 */     ensureBlurSlots(this.hudBlurSlots);
/* 1262 */     MenuBlurSlot oldest = this.hudBlurSlots[0];
/* 1263 */     for (MenuBlurSlot slot : this.hudBlurSlots) {
/* 1264 */       if (slot.hudInputRevision == this.hudInputRevision && slot.hudMode == mode && 
/*      */         
/* 1266 */         Math.abs(slot.hudRadius - radius) < 0.05F) {
/* 1267 */         slot.hudLastUseNs = now;
/* 1268 */         return slot;
/*      */       } 
/* 1270 */       if (slot.hudLastUseNs < oldest.hudLastUseNs) {
/* 1271 */         oldest = slot;
/*      */       }
/*      */     } 
/*      */     
/* 1275 */     oldest.hudInputRevision = this.hudInputRevision;
/* 1276 */     oldest.hudMode = mode;
/* 1277 */     oldest.hudRadius = radius;
/* 1278 */     oldest.hudLastUseNs = now;
/* 1279 */     oldest.hudRegionCount = 0;
/* 1280 */     oldest.valid = false;
/* 1281 */     return oldest;
/*      */   }
/*      */   
/*      */   private static BlurRegion unionBlurRegions(BlurRegion a, BlurRegion b) {
/* 1285 */     int left = Math.min(a.x, b.x);
/* 1286 */     int bottom = Math.min(a.y, b.y);
/* 1287 */     int right = Math.max(a.x + a.width, b.x + b.width);
/* 1288 */     int top = Math.max(a.y + a.height, b.y + b.height);
/* 1289 */     return new BlurRegion(left, bottom, right - left, top - bottom);
/*      */   }
/*      */   
/*      */   private long computeHudBackgroundStateKey(class_310 client) {
/* 1293 */     long key = (client.field_1687 == null) ? 0L : client.field_1687.method_8510();
/* 1294 */     if (client.field_1773 != null && client.field_1773.method_19418() != null) {
/* 1295 */       class_4184 camera = client.field_1773.method_19418();
/* 1296 */       class_243 pos = camera.method_19326();
/* 1297 */       Quaternionf rotation = camera.method_23767();
/* 1298 */       key = mixStateKey(key, Double.doubleToRawLongBits(pos.field_1352));
/* 1299 */       key = mixStateKey(key, Double.doubleToRawLongBits(pos.field_1351));
/* 1300 */       key = mixStateKey(key, Double.doubleToRawLongBits(pos.field_1350));
/* 1301 */       key = mixStateKey(key, Float.floatToRawIntBits(rotation.x));
/* 1302 */       key = mixStateKey(key, Float.floatToRawIntBits(rotation.y));
/* 1303 */       key = mixStateKey(key, Float.floatToRawIntBits(rotation.z));
/* 1304 */       key = mixStateKey(key, Float.floatToRawIntBits(rotation.w));
/*      */     } 
/* 1306 */     key = mixStateKey(key, (client.field_1755 == null) ? 0L : client.field_1755.getClass().hashCode());
/* 1307 */     return key;
/*      */   }
/*      */   
/*      */   private static long mixStateKey(long current, long value) {
/* 1311 */     return (current ^ value) * -7046029254386353131L;
/*      */   }
/*      */   
/*      */   private class_276 applyOptimizedHudFineKawaseBlur(class_310 client, float blurRadius, BlurRegion region) {
/* 1315 */     if (this.input == null || region == null) {
/* 1316 */       return null;
/*      */     }
/*      */     
/* 1319 */     float radius = class_3532.method_15363(blurRadius, 0.0F, 8.0F);
/* 1320 */     MenuBlurSlot slot = acquireHudBlurSlot(1, radius, System.nanoTime());
/* 1321 */     if (isHudBlurRegionPrepared(slot, region)) {
/* 1322 */       return slot.framebuffer;
/*      */     }
/*      */     
/* 1325 */     class_5944 shader = RenderSystem.setShader(DUAL_KAWASE_SHADER_KEY);
/* 1326 */     if (shader == null) {
/* 1327 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1333 */     runDualKawasePass(shader, this.input, slot.framebuffer, radius * 0.35F, true, region);
/*      */     
/* 1335 */     bindMainDrawTarget(client);
/* 1336 */     RenderSystem.viewport(0, 0, this.input.field_1482, this.input.field_1481);
/* 1337 */     slot.valid = false;
/* 1338 */     rememberHudBlurRegion(slot, region);
/* 1339 */     return slot.framebuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void runDualKawasePass(class_5944 shader, class_276 source, class_276 target, float offset, boolean downsample, BlurRegion region) {
/* 1350 */     target.method_1235(false);
/* 1351 */     RenderSystem.viewport(0, 0, target.field_1482, target.field_1481);
/* 1352 */     RenderSystem.disableBlend();
/* 1353 */     RenderSystem.disableDepthTest();
/* 1354 */     RenderSystem.setShaderTexture(0, source.method_30277());
/* 1355 */     shader.method_35785("TexelSize").method_1255(1.0F / source.field_1482, 1.0F / source.field_1481);
/* 1356 */     shader.method_35785("Offset").method_1251(offset);
/* 1357 */     shader.method_35785("Downsample").method_35649(downsample ? 1 : 0);
/* 1358 */     BlurRegion targetRegion = scaleBlurRegion(region, target.field_1482, target.field_1481);
/* 1359 */     RenderSystem.enableScissor(targetRegion.x, targetRegion.y, targetRegion.width, targetRegion.height);
/* 1360 */     ShaderHelper.drawFullScreenQuad();
/* 1361 */     RenderSystem.disableScissor();
/* 1362 */     target.method_1240();
/*      */   }
/*      */   
/*      */   public float getPlayerSpeed(class_310 client) {
/* 1366 */     if (client == null || client.field_1724 == null) {
/* 1367 */       return 0.0F;
/*      */     }
/* 1369 */     if (this.worldSpaceSpeedPrepared) {
/* 1370 */       return this.cachedPlayerSpeed;
/*      */     }
/* 1372 */     this.worldSpaceSpeedPrepared = true;
/*      */     
/* 1374 */     class_243 playerPos = client.field_1724.method_19538();
/* 1375 */     long currentTime = System.currentTimeMillis();
/*      */     
/* 1377 */     if (Double.isNaN(this.lastPlayerX)) {
/* 1378 */       this.lastPlayerX = playerPos.field_1352;
/* 1379 */       this.lastPlayerY = playerPos.field_1351;
/* 1380 */       this.lastPlayerZ = playerPos.field_1350;
/* 1381 */       this.lastSpeedCheckTime = currentTime;
/* 1382 */       return 0.0F;
/*      */     } 
/*      */     
/* 1385 */     double deltaTime = (currentTime - this.lastSpeedCheckTime) / 1000.0D;
/* 1386 */     if (deltaTime < 0.05D) {
/* 1387 */       return this.cachedPlayerSpeed;
/*      */     }
/*      */     
/* 1390 */     double dx = playerPos.field_1352 - this.lastPlayerX;
/* 1391 */     double dy = playerPos.field_1351 - this.lastPlayerY;
/* 1392 */     double dz = playerPos.field_1350 - this.lastPlayerZ;
/* 1393 */     double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
/* 1394 */     this.cachedPlayerSpeed = (float)(distance / deltaTime);
/*      */     
/* 1396 */     this.lastPlayerX = playerPos.field_1352;
/* 1397 */     this.lastPlayerY = playerPos.field_1351;
/* 1398 */     this.lastPlayerZ = playerPos.field_1350;
/* 1399 */     this.lastSpeedCheckTime = currentTime;
/*      */     
/* 1401 */     return this.cachedPlayerSpeed;
/*      */   }
/*      */   
/*      */   private boolean applyGaussianBlur(class_310 client, float blurRadius) {
/* 1405 */     if (blurRadius <= 0.0F) {
/* 1406 */       GlStateManager._glBindFramebuffer(36008, this.input.field_1476);
/* 1407 */       GlStateManager._glBindFramebuffer(36009, this.pong.field_1476);
/* 1408 */       GL30C.glBlitFramebuffer(0, 0, this.input.field_1482, this.input.field_1481, 0, 0, this.pong.field_1482, this.pong.field_1481, 16384, 9729);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1420 */       bindMainDrawTarget(client);
/* 1421 */       return true;
/*      */     } 
/*      */     
/* 1424 */     class_5944 shader = RenderSystem.setShader(GAUSSIAN_SHADER_KEY);
/* 1425 */     if (shader == null) {
/* 1426 */       return false;
/*      */     }
/*      */     
/* 1429 */     runGaussianPass(shader, this.input, this.ping, 1.0F, 0.0F, blurRadius, null);
/* 1430 */     runGaussianPass(shader, this.ping, this.pong, 0.0F, 1.0F, blurRadius, null);
/* 1431 */     bindMainDrawTarget(client);
/* 1432 */     return true;
/*      */   }
/*      */   
/*      */   private void runGaussianPass(class_5944 shader, class_276 source, class_276 target, float directionX, float directionY, float blurRadius, BlurRegion region) {
/* 1436 */     target.method_1235(false);
/* 1437 */     RenderSystem.viewport(0, 0, target.field_1482, target.field_1481);
/* 1438 */     RenderSystem.disableBlend();
/* 1439 */     RenderSystem.disableDepthTest();
/* 1440 */     RenderSystem.setShaderTexture(0, source.method_30277());
/*      */     
/* 1442 */     int support = class_3532.method_15340(Math.round(blurRadius), 1, 64);
/* 1443 */     float sigma = Math.max(1.0F, blurRadius * 0.55F);
/*      */     
/* 1445 */     shader.method_35785("Direction").method_1255(directionX, directionY);
/* 1446 */     shader.method_35785("TexelSize").method_1255(1.0F / source.field_1482, 1.0F / source.field_1481);
/* 1447 */     shader.method_35785("Support").method_35649(support);
/* 1448 */     shader.method_35785("Sigma").method_1251(sigma);
/* 1449 */     shader.method_35785("Brightness").method_1251(1.0F);
/*      */     
/* 1451 */     if (region != null) {
/* 1452 */       RenderSystem.enableScissor(region.x, region.y, region.width, region.height);
/*      */     }
/* 1454 */     ShaderHelper.drawFullScreenQuad();
/* 1455 */     if (region != null) {
/* 1456 */       RenderSystem.disableScissor();
/*      */     }
/*      */     
/* 1459 */     target.method_1240();
/*      */   }
/*      */   
/*      */   private BlurRegion computeHudGaussianRegion(class_310 client, ShapeProperties shape, float blurRadius) {
/* 1463 */     if (client == null || client.method_22683() == null || shape == null || shape.getMatrix() == null) {
/* 1464 */       return null;
/*      */     }
/*      */     
/* 1467 */     float scale = (float)client.method_22683().method_4495();
/* 1468 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/* 1469 */     float softness = Math.max(0.001F, shape.getSoftness());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1475 */     Vector3f pos = matrix4f.transformPosition(shape.getX() - softness / 2.0F, shape.getY() - softness / 2.0F, 0.0F, this.scratchPosition).mul(scale);
/* 1476 */     Vector3f size = matrix4f.getScale(this.scratchScale).mul(scale);
/* 1477 */     float width = (shape.getWidth() + softness) * size.x;
/* 1478 */     float height = (shape.getHeight() + softness) * size.y;
/* 1479 */     int margin = Math.max(2, class_3532.method_15386(blurRadius) + 2);
/*      */     
/* 1481 */     int framebufferWidth = Math.max(1, client.method_22683().method_4489());
/* 1482 */     int framebufferHeight = Math.max(1, client.method_22683().method_4506());
/* 1483 */     int left = class_3532.method_15340(class_3532.method_15375(pos.x) - margin, 0, framebufferWidth);
/* 1484 */     int top = class_3532.method_15340(class_3532.method_15375(pos.y) - margin, 0, framebufferHeight);
/* 1485 */     int right = class_3532.method_15340(class_3532.method_15386(pos.x + width) + margin, 0, framebufferWidth);
/* 1486 */     int bottom = class_3532.method_15340(class_3532.method_15386(pos.y + height) + margin, 0, framebufferHeight);
/*      */     
/* 1488 */     if (right <= left || bottom <= top) {
/* 1489 */       return null;
/*      */     }
/*      */     
/* 1492 */     return new BlurRegion(left, framebufferHeight - bottom, right - left, bottom - top);
/*      */   }
/*      */   
/*      */   private boolean hasPreparedHudGaussianRegion(long key) {
/* 1496 */     for (int i = 0; i < this.preparedHudGaussianRegionCount; i++) {
/* 1497 */       if (this.preparedHudGaussianRegionKeys[i] == key) {
/* 1498 */         return true;
/*      */       }
/*      */     } 
/* 1501 */     return false;
/*      */   }
/*      */   
/*      */   private void rememberPreparedHudGaussianRegion(long key) {
/* 1505 */     if (this.preparedHudGaussianRegionCount >= this.preparedHudGaussianRegionKeys.length) {
/*      */       return;
/*      */     }
/* 1508 */     this.preparedHudGaussianRegionKeys[this.preparedHudGaussianRegionCount++] = key;
/*      */   }
/*      */   
/*      */   private long computeHudGaussianRegionKey(BlurRegion region, float blurRadius) {
/* 1512 */     long key = Float.floatToRawIntBits(blurRadius);
/* 1513 */     key = key * 31L + region.x;
/* 1514 */     key = key * 31L + region.y;
/* 1515 */     key = key * 31L + region.width;
/* 1516 */     key = key * 31L + region.height;
/* 1517 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long computeMenuBlurRegionKey(BlurRegion region) {
/* 1524 */     long key = (region.x >> 1);
/* 1525 */     key = key * 31L + (region.y >> 1);
/* 1526 */     key = key * 31L + (region.width >> 1);
/* 1527 */     key = key * 31L + (region.height >> 1);
/* 1528 */     return key;
/*      */   }
/*      */   
/*      */   private MenuBlurSlot acquireMenuBlurSlot(long regionKey, long now) {
/* 1532 */     ensureBlurSlots(this.menuBlurSlots);
/* 1533 */     MenuBlurSlot oldest = this.menuBlurSlots[0];
/* 1534 */     for (MenuBlurSlot slot : this.menuBlurSlots) {
/* 1535 */       if (slot.regionKey == regionKey) {
/* 1536 */         slot.lastUseNs = now;
/* 1537 */         return slot;
/*      */       } 
/* 1539 */       if (!slot.valid || slot.lastUseNs < oldest.lastUseNs) {
/* 1540 */         oldest = slot;
/*      */       }
/*      */     } 
/*      */     
/* 1544 */     oldest.regionKey = regionKey;
/* 1545 */     oldest.lastUseNs = now;
/* 1546 */     oldest.valid = false;
/* 1547 */     return oldest;
/*      */   } private static final class BlurRegion extends Record { private final int x; private final int y; private final int width; private final int height;
/* 1549 */     private BlurRegion(int x, int y, int width, int height) { this.x = x; this.y = y; this.width = width; this.height = height; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/shape/implement/Blur$BlurRegion;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1549	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 1549 */       //   0	7	0	this	Lvorga/phazeclient/api/system/shape/implement/Blur$BlurRegion; } public int x() { return this.x; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/shape/implement/Blur$BlurRegion;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1549	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/api/system/shape/implement/Blur$BlurRegion; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/shape/implement/Blur$BlurRegion;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1549	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/api/system/shape/implement/Blur$BlurRegion;
/* 1549 */       //   0	8	1	o	Ljava/lang/Object; } public int y() { return this.y; } public int width() { return this.width; } public int height() { return this.height; }
/*      */      }
/*      */ 
/*      */   
/*      */   private static final class MenuBlurSlot { private final class_276 framebuffer;
/* 1554 */     private final Blur.BlurRegion[] hudRegions = new Blur.BlurRegion[32];
/* 1555 */     private long regionKey = Long.MIN_VALUE;
/*      */     
/* 1557 */     private long sourceRevision = Long.MIN_VALUE;
/* 1558 */     private long lastRefreshNs = 0L;
/* 1559 */     private long lastUseNs = 0L;
/* 1560 */     private float blurRadius = -1.0F;
/*      */     private boolean valid = false;
/* 1562 */     private long hudInputRevision = -1L;
/* 1563 */     private long hudLastUseNs = 0L;
/* 1564 */     private int hudMode = -1;
/* 1565 */     private float hudRadius = -1.0F;
/* 1566 */     private int hudRegionCount = 0;
/*      */     
/*      */     private MenuBlurSlot(class_276 framebuffer) {
/* 1569 */       this.framebuffer = framebuffer;
/*      */     } }
/*      */   private static final class PreparedBlurState extends Record { private final int sourceTexture; private final int blurMode; private final float blurRadius;
/*      */     
/* 1573 */     private PreparedBlurState(int sourceTexture, int blurMode, float blurRadius) { this.sourceTexture = sourceTexture; this.blurMode = blurMode; this.blurRadius = blurRadius; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/shape/implement/Blur$PreparedBlurState;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1573	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/api/system/shape/implement/Blur$PreparedBlurState; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/shape/implement/Blur$PreparedBlurState;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1573	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/api/system/shape/implement/Blur$PreparedBlurState; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/shape/implement/Blur$PreparedBlurState;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1573	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/api/system/shape/implement/Blur$PreparedBlurState;
/* 1573 */       //   0	8	1	o	Ljava/lang/Object; } public int sourceTexture() { return this.sourceTexture; } public int blurMode() { return this.blurMode; } public float blurRadius() { return this.blurRadius; }
/*      */      private boolean matches(PreparedBlurState other) {
/* 1575 */       return (other != null && this.sourceTexture == other.sourceTexture && this.blurMode == other.blurMode && 
/*      */ 
/*      */         
/* 1578 */         Float.floatToRawIntBits(this.blurRadius) == Float.floatToRawIntBits(other.blurRadius));
/*      */     } }
/*      */ 
/*      */   
/*      */   private void bindMainDrawTarget(class_310 client) {
/* 1583 */     if (client == null || client.method_1522() == null) {
/*      */       return;
/*      */     }
/* 1586 */     if (HudBuffer.activeCaptureTarget >= 0) {
/* 1587 */       GlStateManager._glBindFramebuffer(36009, HudBuffer.activeCaptureTarget);
/*      */     } else {
/* 1589 */       client.method_1522().method_1235(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void setTintUniform(class_5944 shader, int argb) {
/* 1594 */     float alpha = (argb >>> 24 & 0xFF) / 255.0F;
/* 1595 */     float red = (argb >>> 16 & 0xFF) / 255.0F;
/* 1596 */     float green = (argb >>> 8 & 0xFF) / 255.0F;
/* 1597 */     float blue = (argb & 0xFF) / 255.0F;
/* 1598 */     shader.method_35785("TintColor").method_35657(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   private static float maxCornerRadius(Vector4f radius) {
/* 1602 */     return Math.max(Math.max(radius.x, radius.y), Math.max(radius.z, radius.w));
/*      */   }
/*      */   
/*      */   private static void restoreRenderState(boolean enableDepthTest) {
/* 1606 */     RenderSystem.depthMask(true);
/* 1607 */     if (enableDepthTest) {
/* 1608 */       RenderSystem.enableDepthTest();
/*      */     } else {
/* 1610 */       RenderSystem.disableDepthTest();
/*      */     } 
/* 1612 */     RenderSystem.defaultBlendFunc();
/* 1613 */     RenderSystem.disableBlend();
/* 1614 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\implement\Blur.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */