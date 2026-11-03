/*     */ package vorga.phazeclient.api.system.snapshot;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_10142;
/*     */ import net.minecraft.class_10366;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_6367;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL30C;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ import vorga.phazeclient.api.system.shape.implement.Rectangle;
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
/*     */ public final class CardSnapshotCache
/*     */ {
/* 113 */   private static final Rectangle RECTANGLE = new Rectangle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private static final Map<Object, Snapshot> CACHE = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   private static CaptureState active = null;
/*     */ 
/*     */   
/*     */   public static final class Snapshot
/*     */   {
/*     */     class_276 fbo;
/*     */     
/*     */     int fbWidth;
/*     */     
/*     */     int fbHeight;
/*     */     
/*     */     boolean useDepth;
/*     */     
/*     */     public int hash;
/*     */     
/*     */     public boolean populated;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class CaptureState
/*     */   {
/*     */     CardSnapshotCache.Snapshot snapshot;
/*     */     
/*     */     class_276 mainFb;
/*     */     
/*     */     Matrix4f savedProj;
/*     */     
/*     */     class_10366 savedProjType;
/*     */     
/*     */     int savedViewportX;
/*     */     
/*     */     int savedViewportY;
/*     */     
/*     */     int savedViewportW;
/*     */     
/*     */     int savedViewportH;
/*     */     
/*     */     boolean savedScissorEnabled;
/*     */     
/*     */     int savedScissorX;
/*     */     
/*     */     int savedScissorY;
/*     */     
/*     */     int savedScissorW;
/*     */     
/*     */     int savedScissorH;
/*     */     
/*     */     float[] savedShaderColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Snapshot getOrCreate(Object key, int fbWidth, int fbHeight) {
/* 182 */     return getOrCreate(key, fbWidth, fbHeight, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Snapshot getOrCreate(Object key, int fbWidth, int fbHeight, boolean useDepth) {
/* 191 */     Snapshot snap = CACHE.computeIfAbsent(key, k -> new Snapshot());
/* 192 */     if (snap.fbo == null || snap.fbWidth != fbWidth || snap.fbHeight != fbHeight || snap.useDepth != useDepth) {
/*     */ 
/*     */ 
/*     */       
/* 196 */       if (snap.fbo != null) {
/* 197 */         snap.fbo.method_1238();
/*     */       }
/* 199 */       snap.fbo = (class_276)new class_6367(fbWidth, fbHeight, useDepth);
/* 200 */       snap.fbo.method_58226(9729);
/* 201 */       snap.fbWidth = fbWidth;
/* 202 */       snap.fbHeight = fbHeight;
/* 203 */       snap.useDepth = useDepth;
/* 204 */       snap.populated = false;
/*     */     } 
/* 206 */     return snap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void invalidate(Object key) {
/* 216 */     Snapshot snap = CACHE.remove(key);
/* 217 */     if (snap != null && snap.fbo != null) {
/* 218 */       snap.fbo.method_1238();
/*     */     }
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
/*     */   public static void clearAll() {
/* 233 */     List<Snapshot> snapshots = new ArrayList<>(CACHE.values());
/* 234 */     CACHE.clear();
/* 235 */     for (Snapshot s : snapshots) {
/* 236 */       if (s.fbo != null) {
/* 237 */         s.fbo.method_1238();
/*     */       }
/*     */     } 
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
/*     */   public static void beginCapture(Snapshot snapshot, float widthGui, float heightGui) {
/* 269 */     class_310 mc = class_310.method_1551();
/* 270 */     if (mc == null || mc.method_1522() == null || snapshot == null || snapshot.fbo == null) {
/*     */       return;
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
/* 282 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 284 */     CaptureState s = new CaptureState();
/* 285 */     s.snapshot = snapshot;
/* 286 */     s.mainFb = mc.method_1522();
/* 287 */     s.savedProj = new Matrix4f((Matrix4fc)RenderSystem.getProjectionMatrix());
/* 288 */     s.savedProjType = RenderSystem.getProjectionType();
/*     */ 
/*     */     
/* 291 */     int[] viewport = new int[4];
/* 292 */     GL11.glGetIntegerv(2978, viewport);
/* 293 */     s.savedViewportX = viewport[0];
/* 294 */     s.savedViewportY = viewport[1];
/* 295 */     s.savedViewportW = viewport[2];
/* 296 */     s.savedViewportH = viewport[3];
/* 297 */     s.savedScissorEnabled = GL11.glIsEnabled(3089);
/* 298 */     if (s.savedScissorEnabled) {
/* 299 */       int[] scissor = new int[4];
/* 300 */       GL11.glGetIntegerv(3088, scissor);
/* 301 */       s.savedScissorX = scissor[0];
/* 302 */       s.savedScissorY = scissor[1];
/* 303 */       s.savedScissorW = scissor[2];
/* 304 */       s.savedScissorH = scissor[3];
/*     */ 
/*     */ 
/*     */       
/* 308 */       RenderSystem.disableScissor();
/*     */     } 
/* 310 */     s.savedShaderColor = (float[])RenderSystem.getShaderColor().clone();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 321 */     snapshot.fbo.method_1236(0.0F, 0.0F, 0.0F, 0.0F);
/* 322 */     snapshot.fbo.method_1230();
/* 323 */     snapshot.fbo.method_1235(true);
/*     */     
/* 325 */     RenderSystem.viewport(0, 0, snapshot.fbWidth, snapshot.fbHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     Matrix4f cardProj = (new Matrix4f()).setOrtho(0.0F, widthGui, heightGui, 0.0F, 1000.0F, 21000.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     cardProj.translate(0.0F, 0.0F, -11000.0F);
/* 337 */     RenderSystem.setProjectionMatrix(cardProj, class_10366.field_54954);
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
/* 349 */     BatchedRectangle.setRenderTargetFbHeight(snapshot.fbHeight);
/*     */     
/* 351 */     active = s;
/*     */   }
/*     */   
/*     */   public static void endCapture() {
/* 355 */     if (active == null) {
/*     */       return;
/*     */     }
/* 358 */     CaptureState s = active;
/* 359 */     active = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     BatchedRectangle.flushIfBatching();
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
/* 380 */     BatchedRectangle.clearRenderTargetFbHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     s.mainFb.method_1235(false);
/*     */     
/* 388 */     RenderSystem.viewport(s.savedViewportX, s.savedViewportY, s.savedViewportW, s.savedViewportH);
/* 389 */     RenderSystem.setProjectionMatrix(s.savedProj, s.savedProjType);
/* 390 */     if (s.savedScissorEnabled)
/*     */     {
/*     */ 
/*     */       
/* 394 */       RenderSystem.enableScissor(s.savedScissorX, s.savedScissorY, s.savedScissorW, s.savedScissorH);
/*     */     }
/* 396 */     RenderSystem.setShaderColor(s.savedShaderColor[0], s.savedShaderColor[1], s.savedShaderColor[2], s.savedShaderColor[3]);
/*     */     
/* 398 */     s.snapshot.populated = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rebindActiveCaptureTarget() {
/* 409 */     if (active == null || active.snapshot == null || active.snapshot.fbo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 413 */     active.snapshot.fbo.method_1235(false);
/* 414 */     RenderSystem.viewport(0, 0, active.snapshot.fbWidth, active.snapshot.fbHeight);
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
/*     */   public static boolean hasVisiblePixels(Snapshot snapshot) {
/* 427 */     if (snapshot == null || snapshot.fbo == null || snapshot.fbWidth <= 0 || snapshot.fbHeight <= 0)
/*     */     {
/* 429 */       return false;
/*     */     }
/* 431 */     int previousRead = GL11.glGetInteger(36010);
/*     */ 
/*     */     
/* 434 */     ByteBuffer pixels = BufferUtils.createByteBuffer(snapshot.fbWidth * snapshot.fbHeight * 4);
/*     */ 
/*     */     
/*     */     try {
/* 438 */       GlStateManager._glBindFramebuffer(36008, snapshot.fbo.field_1476);
/*     */ 
/*     */       
/* 441 */       GL11.glReadPixels(0, 0, snapshot.fbWidth, snapshot.fbHeight, 6408, 5121, pixels);
/*     */       
/*     */       int i;
/*     */       
/* 445 */       for (i = 3; i < pixels.capacity(); i += 4) {
/* 446 */         if ((pixels.get(i) & 0xFF) > 2) {
/* 447 */           return true;
/*     */         }
/*     */       } 
/* 450 */       i = 0; return i;
/*     */     } finally {
/* 452 */       GlStateManager._glBindFramebuffer(36008, previousRead);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void blit(class_332 context, Snapshot snapshot, float x, float y, float widthGui, float heightGui, float alpha) {
/* 472 */     if (snapshot == null || snapshot.fbo == null || !snapshot.populated) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 478 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 480 */     Matrix4f matrix = context.method_51448().method_23760().method_23761();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 487 */     int alphaByte = Math.max(0, Math.min(255, Math.round(alpha * 255.0F)));
/* 488 */     int color = alphaByte << 24 | 0xFFFFFF;
/*     */     
/* 490 */     RenderSystem.enableBlend();
/* 491 */     RenderSystem.defaultBlendFunc();
/* 492 */     RenderSystem.setShaderTexture(0, snapshot.fbo.method_30277());
/* 493 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 495 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/*     */ 
/*     */     
/* 498 */     buffer.method_22918(matrix, x, y, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 499 */     buffer.method_22918(matrix, x, y + heightGui, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 500 */     buffer.method_22918(matrix, x + widthGui, y + heightGui, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 501 */     buffer.method_22918(matrix, x + widthGui, y, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/*     */     
/* 503 */     class_286.method_43433(buffer.method_60800());
/* 504 */     RenderSystem.disableBlend();
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
/*     */   public static void blitRounded(class_332 context, Snapshot snapshot, float x, float y, float widthGui, float heightGui, float alpha, float round, int backgroundColor) {
/* 518 */     if (snapshot == null || snapshot.fbo == null || !snapshot.populated) {
/*     */       return;
/*     */     }
/*     */     
/* 522 */     BatchedRectangle.flushIfBatching();
/* 523 */     RECTANGLE.render(ShapeProperties.create(context.method_51448(), x, y, widthGui, heightGui)
/* 524 */         .round(round)
/* 525 */         .color(backgroundColor)
/* 526 */         .build());
/*     */     
/* 528 */     Matrix4f matrix = context.method_51448().method_23760().method_23761();
/* 529 */     int alphaByte = Math.max(0, Math.min(255, Math.round(alpha * 255.0F)));
/* 530 */     int color = alphaByte << 24 | 0xFFFFFF;
/*     */     
/* 532 */     RenderSystem.enableBlend();
/* 533 */     RenderSystem.blendFunc(772, 773);
/* 534 */     RenderSystem.setShaderTexture(0, snapshot.fbo.method_30277());
/* 535 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 537 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 538 */     buffer.method_22918(matrix, x, y, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 539 */     buffer.method_22918(matrix, x, y + heightGui, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 540 */     buffer.method_22918(matrix, x + widthGui, y + heightGui, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 541 */     buffer.method_22918(matrix, x + widthGui, y, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/*     */     
/* 543 */     class_286.method_43433(buffer.method_60800());
/* 544 */     RenderSystem.defaultBlendFunc();
/* 545 */     RenderSystem.disableBlend();
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
/*     */   public static void copyRegionFromFramebuffer(Snapshot snapshot, class_276 source, int srcX, int srcY, int srcWidth, int srcHeight) {
/* 559 */     if (snapshot == null || snapshot.fbo == null || source == null || srcWidth <= 0 || srcHeight <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 563 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 565 */     int previousRead = GL11.glGetInteger(36010);
/* 566 */     int previousDraw = GL11.glGetInteger(36006);
/*     */     
/* 568 */     GlStateManager._glBindFramebuffer(36008, source.field_1476);
/* 569 */     GlStateManager._glBindFramebuffer(36009, snapshot.fbo.field_1476);
/* 570 */     GL30C.glBlitFramebuffer(srcX, srcY, srcX + srcWidth, srcY + srcHeight, 0, 0, snapshot.fbWidth, snapshot.fbHeight, 16384, 9729);
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
/* 582 */     GlStateManager._glBindFramebuffer(36008, previousRead);
/* 583 */     GlStateManager._glBindFramebuffer(36009, previousDraw);
/* 584 */     snapshot.populated = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\snapshot\CardSnapshotCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */