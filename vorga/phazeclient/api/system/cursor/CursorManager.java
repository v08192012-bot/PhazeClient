/*     */ package vorga.phazeclient.api.system.cursor;
/*     */ 
/*     */ import net.minecraft.class_310;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CursorManager
/*     */ {
/*     */   public static final int SHAPE_ARROW = 221185;
/*     */   public static final int SHAPE_HAND = 221188;
/*     */   public static final int SHAPE_BEAM = 221186;
/*     */   public static final int SHAPE_NOT_ALLOWED = 221194;
/*     */   public static final int SHAPE_MOVE = 221193;
/*     */   public static final int SHAPE_VRESIZE = 221190;
/*     */   public static final int SHAPE_HRESIZE = 221189;
/*     */   private static final long SCROLL_OVERRIDE_NANOS = 150000000L;
/*  81 */   private static long arrowCursor = 0L;
/*  82 */   private static long handCursor = 0L;
/*  83 */   private static long beamCursor = 0L;
/*  84 */   private static long notAllowedCursor = 0L;
/*  85 */   private static long moveCursor = 0L;
/*  86 */   private static long vResizeCursor = 0L;
/*  87 */   private static long hResizeCursor = 0L;
/*     */ 
/*     */   
/*  90 */   private static int requestedShape = 221185;
/*     */   
/*  92 */   private static int requestedPriority = 0;
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static int lastAppliedShape = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean screenActive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static int scrollOverrideShape = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private static long scrollOverrideUntilNanos = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private static int dragOverrideShape = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beginFrame() {
/* 126 */     requestedShape = 221185;
/* 127 */     requestedPriority = 0;
/* 128 */     screenActive = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void requestHand() {
/* 133 */     requestShape(221188, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void requestBeam() {
/* 138 */     requestShape(221186, 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void requestNotAllowed() {
/* 143 */     requestShape(221194, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void requestMove() {
/* 148 */     requestShape(221193, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void requestHorizontalResize() {
/* 153 */     requestShape(221189, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void requestVerticalResize() {
/* 158 */     requestShape(221190, 2);
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
/*     */   public static void beginDrag(int shape) {
/* 177 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*     */       return;
/*     */     }
/* 180 */     if (dragOverrideShape == shape) {
/*     */       return;
/*     */     }
/* 183 */     dragOverrideShape = shape;
/*     */ 
/*     */ 
/*     */     
/* 187 */     applyShape(shape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void endDrag() {
/* 197 */     dragOverrideShape = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifyScroll(double horizontal, double vertical) {
/* 238 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*     */       return;
/*     */     }
/* 241 */     double absH = Math.abs(horizontal);
/* 242 */     double absV = Math.abs(vertical);
/* 243 */     if (absH == 0.0D && absV == 0.0D) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     int shape = (absV >= absH) ? 221190 : 221189;
/* 252 */     scrollOverrideShape = shape;
/* 253 */     scrollOverrideUntilNanos = System.nanoTime() + 150000000L;
/*     */ 
/*     */ 
/*     */     
/* 257 */     applyShape(shape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void endFrame(boolean dynamicEnabled) {
/*     */     int target;
/* 268 */     if (!screenActive) {
/*     */       return;
/*     */     }
/* 271 */     screenActive = false;
/*     */     
/* 273 */     if (!dynamicEnabled) {
/* 274 */       target = 221185;
/*     */ 
/*     */ 
/*     */       
/* 278 */       scrollOverrideShape = 0;
/* 279 */       scrollOverrideUntilNanos = 0L;
/* 280 */       dragOverrideShape = 0;
/* 281 */     } else if (dragOverrideShape != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       target = dragOverrideShape;
/* 288 */     } else if (scrollOverrideShape != 0 && System.nanoTime() < scrollOverrideUntilNanos) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 293 */       target = scrollOverrideShape;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 300 */       if (scrollOverrideShape != 0) {
/* 301 */         scrollOverrideShape = 0;
/* 302 */         scrollOverrideUntilNanos = 0L;
/*     */       } 
/* 304 */       target = requestedShape;
/*     */     } 
/* 306 */     applyShape(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forceArrow() {
/* 316 */     applyShape(221185);
/* 317 */     screenActive = false;
/* 318 */     scrollOverrideShape = 0;
/* 319 */     scrollOverrideUntilNanos = 0L;
/* 320 */     dragOverrideShape = 0;
/*     */   }
/*     */   
/*     */   private static void applyShape(int shape) {
/* 324 */     if (shape == lastAppliedShape) {
/*     */       return;
/*     */     }
/* 327 */     long handle = windowHandle();
/* 328 */     if (handle == 0L) {
/*     */       return;
/*     */     }
/* 331 */     long cursor = cursorFor(shape);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     GLFW.glfwSetCursor(handle, cursor);
/* 337 */     lastAppliedShape = shape;
/*     */   }
/*     */   
/*     */   private static long cursorFor(int shape) {
/* 341 */     switch (shape) {
/*     */       case 221188:
/* 343 */         if (handCursor == 0L) {
/* 344 */           handCursor = GLFW.glfwCreateStandardCursor(221188);
/*     */         }
/*     */ 
/*     */       
/*     */       case 221186:
/* 349 */         if (beamCursor == 0L) {
/* 350 */           beamCursor = GLFW.glfwCreateStandardCursor(221186);
/*     */         }
/*     */ 
/*     */       
/*     */       case 221194:
/* 355 */         if (notAllowedCursor == 0L) {
/* 356 */           notAllowedCursor = GLFW.glfwCreateStandardCursor(221194);
/*     */         }
/*     */ 
/*     */       
/*     */       case 221193:
/* 361 */         if (moveCursor == 0L) {
/* 362 */           moveCursor = GLFW.glfwCreateStandardCursor(221193);
/*     */         }
/*     */ 
/*     */       
/*     */       case 221190:
/* 367 */         if (vResizeCursor == 0L) {
/* 368 */           vResizeCursor = GLFW.glfwCreateStandardCursor(221190);
/*     */         }
/*     */ 
/*     */       
/*     */       case 221189:
/* 373 */         if (hResizeCursor == 0L) {
/* 374 */           hResizeCursor = GLFW.glfwCreateStandardCursor(221189);
/*     */         }
/*     */     } 
/*     */ 
/*     */     
/* 379 */     if (arrowCursor == 0L) {
/* 380 */       arrowCursor = GLFW.glfwCreateStandardCursor(221185);
/*     */     }
/* 382 */     return arrowCursor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void requestShape(int shape, int priority) {
/* 388 */     if (priority < requestedPriority) {
/*     */       return;
/*     */     }
/* 391 */     if (priority == requestedPriority && requestedShape == 221186 && shape != 221186) {
/*     */       return;
/*     */     }
/* 394 */     requestedShape = shape;
/* 395 */     requestedPriority = priority;
/*     */   }
/*     */   
/*     */   private static long windowHandle() {
/* 399 */     class_310 mc = class_310.method_1551();
/* 400 */     if (mc == null || mc.method_22683() == null) {
/* 401 */       return 0L;
/*     */     }
/* 403 */     return mc.method_22683().method_4490();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\cursor\CursorManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */