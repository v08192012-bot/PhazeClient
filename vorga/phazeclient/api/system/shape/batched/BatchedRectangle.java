/*     */ package vorga.phazeclient.api.system.shape.batched;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_10149;
/*     */ import net.minecraft.class_10156;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_296;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5944;
/*     */ import net.minecraft.class_9801;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ import org.joml.Vector4fc;
/*     */ import org.joml.Vector4i;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
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
/*     */ public final class BatchedRectangle
/*     */ {
/*     */   public static final class_296 RECT_BASE;
/*     */   public static final class_296 RECT_SIZE;
/*     */   public static final class_296 RECT_RADIUS;
/*     */   public static final class_296 RECT_PARAMS;
/*     */   public static final class_296 RECT_OUTLINE;
/*     */   public static final class_293 BATCHED_FORMAT;
/*     */   public static final class_10156 SHADER_KEY;
/*     */   private static final boolean DISABLED;
/*     */   
/*     */   static {
/* 116 */     class_296 base = null, size = null, radius = null, params = null, outline = null;
/* 117 */     class_293 format = null;
/* 118 */     class_10156 shaderKey = null;
/* 119 */     boolean disabled = true;
/*     */     try {
/* 121 */       int id = findFreeSlot(7);
/* 122 */       base = class_296.method_60845(id, 0, class_296.class_297.field_1623, class_296.class_298.field_20782, 2);
/* 123 */       id = findFreeSlot(id + 1);
/* 124 */       size = class_296.method_60845(id, 0, class_296.class_297.field_1623, class_296.class_298.field_20782, 2);
/* 125 */       id = findFreeSlot(id + 1);
/* 126 */       radius = class_296.method_60845(id, 0, class_296.class_297.field_1623, class_296.class_298.field_20782, 4);
/* 127 */       id = findFreeSlot(id + 1);
/* 128 */       params = class_296.method_60845(id, 0, class_296.class_297.field_1623, class_296.class_298.field_20782, 2);
/* 129 */       id = findFreeSlot(id + 1);
/* 130 */       outline = class_296.method_60845(id, 0, class_296.class_297.field_1623, class_296.class_298.field_20782, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       format = class_293.method_60833().method_60842("Position", class_296.field_52107).method_60842("Color", class_296.field_52108).method_60842("RectBase", base).method_60842("RectSize", size).method_60842("Radius", radius).method_60842("Params", params).method_60842("OutlineColor", outline).method_60840();
/*     */       
/* 142 */       shaderKey = new class_10156(class_2960.method_60655("phaze", "core/round_batched"), format, class_10149.field_53930);
/*     */ 
/*     */       
/* 145 */       disabled = false;
/* 146 */     } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */       
/* 150 */       System.err.println("[Phaze/BatchedRectangle] Disabled (VertexFormatElement allocation failed): " + String.valueOf(t));
/*     */     } 
/* 152 */     RECT_BASE = base;
/* 153 */     RECT_SIZE = size;
/* 154 */     RECT_RADIUS = radius;
/* 155 */     RECT_PARAMS = params;
/* 156 */     RECT_OUTLINE = outline;
/* 157 */     BATCHED_FORMAT = format;
/* 158 */     SHADER_KEY = shaderKey;
/* 159 */     DISABLED = disabled;
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
/*     */   private static int findFreeSlot(int startFrom) {
/* 171 */     for (int id = startFrom; id < 32; id++) {
/* 172 */       if (class_296.method_60844(id) == null) {
/* 173 */         return id;
/*     */       }
/*     */     } 
/* 176 */     throw new IllegalStateException("No free VertexFormatElement slot >= " + startFrom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   private static int scopeDepth = 0;
/* 185 */   private static class_287 activeBuilder = null;
/* 186 */   private static int pendingRects = 0;
/* 187 */   private static final Vector3f SCRATCH_BASE_POS = new Vector3f();
/* 188 */   private static final Vector3f SCRATCH_SIZE = new Vector3f();
/* 189 */   private static final Vector4f SCRATCH_RADIUS = new Vector4f();
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
/* 212 */   private static float renderTargetFbHeightOverride = -1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beginScope() {
/* 222 */     if (DISABLED) {
/*     */       return;
/*     */     }
/* 225 */     scopeDepth++;
/*     */   }
/*     */   
/*     */   public static void endScope() {
/* 229 */     if (DISABLED || scopeDepth <= 0) {
/*     */       return;
/*     */     }
/* 232 */     if (--scopeDepth == 0) {
/* 233 */       flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isBatching() {
/* 238 */     return (!DISABLED && scopeDepth > 0);
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
/*     */   public static void flushIfBatching() {
/* 250 */     if (isBatching()) {
/* 251 */       flush();
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
/*     */   public static void setRenderTargetFbHeight(float fbHeight) {
/* 269 */     flushIfBatching();
/* 270 */     renderTargetFbHeightOverride = fbHeight;
/*     */   }
/*     */   
/*     */   public static void clearRenderTargetFbHeight() {
/* 274 */     flushIfBatching();
/* 275 */     renderTargetFbHeightOverride = -1.0F;
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
/*     */   public static float getActiveFbHeight() {
/* 291 */     if (renderTargetFbHeightOverride > 0.0F) {
/* 292 */       return renderTargetFbHeightOverride;
/*     */     }
/* 294 */     class_310 mc = class_310.method_1551();
/* 295 */     if (mc == null || mc.method_22683() == null) {
/* 296 */       return 0.0F;
/*     */     }
/* 298 */     return mc.method_22683().method_4506();
/*     */   }
/*     */   
/*     */   public static void flush() {
/* 302 */     class_287 builder = activeBuilder;
/* 303 */     int rects = pendingRects;
/* 304 */     activeBuilder = null;
/* 305 */     pendingRects = 0;
/* 306 */     if (builder == null || rects == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 310 */     class_9801 built = builder.method_60794();
/* 311 */     if (built == null) {
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
/* 322 */     RenderSystem.enableBlend();
/* 323 */     RenderSystem.defaultBlendFunc();
/* 324 */     RenderSystem.enableDepthTest();
/* 325 */     RenderSystem.enableCull();
/*     */     
/*     */     try {
/* 328 */       class_5944 shader = RenderSystem.setShader(SHADER_KEY);
/* 329 */       if (shader == null) {
/* 330 */         built.close();
/*     */         return;
/*     */       } 
/* 333 */       class_286.method_43433(built);
/*     */     } finally {
/* 335 */       RenderSystem.disableBlend();
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
/*     */   public static void submit(ShapeProperties shape) {
/* 365 */     class_310 mc = class_310.method_1551();
/* 366 */     if (mc == null || mc.method_22683() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 370 */     float scale = (float)mc.method_22683().method_4495();
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
/* 383 */     float windowHeight = (renderTargetFbHeightOverride > 0.0F) ? renderTargetFbHeightOverride : mc.method_22683().method_4506();
/* 384 */     float globalAlpha = RenderSystem.getShaderColor()[3];
/*     */     
/* 386 */     Matrix4f matrix = shape.getMatrix().method_23760().method_23761();
/* 387 */     Vector3f basePos = matrix.transformPosition(shape.getX(), shape.getY(), 0.0F, SCRATCH_BASE_POS).mul(scale);
/* 388 */     Vector3f sizeVec = matrix.getScale(SCRATCH_SIZE).mul(scale);
/*     */     
/* 390 */     float scaledWidth = shape.getWidth() * sizeVec.x;
/* 391 */     float scaledHeight = shape.getHeight() * sizeVec.y;
/*     */     
/* 393 */     float baseX = basePos.x;
/* 394 */     float baseY = windowHeight - scaledHeight - basePos.y;
/*     */     
/* 396 */     Vector4f scaledRadius = SCRATCH_RADIUS.set((Vector4fc)shape.getRound()).mul(sizeVec.y);
/* 397 */     float softness = shape.getSoftness();
/* 398 */     float thickness = shape.getThickness();
/*     */     
/* 400 */     Vector4i colors = shape.getColor();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 405 */     int color1 = ColorUtil.multAlpha(colors.x, globalAlpha);
/* 406 */     int color2 = ColorUtil.multAlpha(colors.y, globalAlpha);
/* 407 */     int color3 = ColorUtil.multAlpha(colors.z, globalAlpha);
/* 408 */     int color4 = ColorUtil.multAlpha(colors.w, globalAlpha);
/* 409 */     int outlineColor = ColorUtil.multAlpha(shape.getOutlineColor(), globalAlpha);
/*     */     
/* 411 */     if (activeBuilder == null) {
/* 412 */       activeBuilder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, BATCHED_FORMAT);
/*     */     }
/*     */     
/* 415 */     float x0 = shape.getX() - softness / 2.0F;
/* 416 */     float y0 = shape.getY() - softness / 2.0F;
/* 417 */     float w = shape.getWidth() + softness;
/* 418 */     float h = shape.getHeight() + softness;
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
/* 433 */     emit(activeBuilder, matrix, x0, y0, color2, baseX, baseY, scaledWidth, scaledHeight, scaledRadius, thickness, softness, outlineColor);
/*     */     
/* 435 */     emit(activeBuilder, matrix, x0, y0 + h, color1, baseX, baseY, scaledWidth, scaledHeight, scaledRadius, thickness, softness, outlineColor);
/*     */     
/* 437 */     emit(activeBuilder, matrix, x0 + w, y0 + h, color3, baseX, baseY, scaledWidth, scaledHeight, scaledRadius, thickness, softness, outlineColor);
/*     */     
/* 439 */     emit(activeBuilder, matrix, x0 + w, y0, color4, baseX, baseY, scaledWidth, scaledHeight, scaledRadius, thickness, softness, outlineColor);
/*     */ 
/*     */     
/* 442 */     pendingRects++;
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
/*     */   private static void emit(class_287 buffer, Matrix4f matrix, float x, float y, int color, float baseX, float baseY, float sizeX, float sizeY, Vector4f radius, float thickness, float softness, int outlineColor) {
/* 457 */     buffer.method_22918(matrix, x, y, 0.0F).method_39415(color);
/*     */     
/* 459 */     long ptr = buffer.method_60798(RECT_BASE);
/* 460 */     MemoryUtil.memPutFloat(ptr, baseX);
/* 461 */     MemoryUtil.memPutFloat(ptr + 4L, baseY);
/*     */     
/* 463 */     ptr = buffer.method_60798(RECT_SIZE);
/* 464 */     MemoryUtil.memPutFloat(ptr, sizeX);
/* 465 */     MemoryUtil.memPutFloat(ptr + 4L, sizeY);
/*     */     
/* 467 */     ptr = buffer.method_60798(RECT_RADIUS);
/* 468 */     MemoryUtil.memPutFloat(ptr, radius.x);
/* 469 */     MemoryUtil.memPutFloat(ptr + 4L, radius.y);
/* 470 */     MemoryUtil.memPutFloat(ptr + 8L, radius.z);
/* 471 */     MemoryUtil.memPutFloat(ptr + 12L, radius.w);
/*     */     
/* 473 */     ptr = buffer.method_60798(RECT_PARAMS);
/* 474 */     MemoryUtil.memPutFloat(ptr, thickness);
/* 475 */     MemoryUtil.memPutFloat(ptr + 4L, softness);
/*     */     
/* 477 */     ptr = buffer.method_60798(RECT_OUTLINE);
/* 478 */     MemoryUtil.memPutFloat(ptr, ColorUtil.redf(outlineColor));
/* 479 */     MemoryUtil.memPutFloat(ptr + 4L, ColorUtil.greenf(outlineColor));
/* 480 */     MemoryUtil.memPutFloat(ptr + 8L, ColorUtil.bluef(outlineColor));
/* 481 */     MemoryUtil.memPutFloat(ptr + 12L, ColorUtil.alphaf(outlineColor));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\batched\BatchedRectangle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */