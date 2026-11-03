/*     */ package vorga.phazeclient.mixins.sodium;
/*     */ 
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import net.caffeinemc.mods.sodium.client.render.chunk.DefaultChunkRenderer;
/*     */ import net.caffeinemc.mods.sodium.client.render.chunk.lists.ChunkRenderList;
/*     */ import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion;
/*     */ import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
/*     */ import net.caffeinemc.mods.sodium.client.render.viewport.CameraTransform;
/*     */ import net.caffeinemc.mods.sodium.client.util.iterator.ByteIterator;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL31;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*     */ import vorga.phazeclient.base.util.render.FogColorTracker;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChunkAnimator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin(value = {DefaultChunkRenderer.class}, remap = false)
/*     */ public abstract class DefaultChunkRendererChunkAnimatorMixin
/*     */ {
/*     */   private static ChunkShaderInterface phaze$cachedShader;
/*  99 */   private static int phaze$cachedBlockIdx = -1;
/* 100 */   private static int phaze$cachedModeLoc = -1;
/* 101 */   private static int phaze$cachedFadeStyleLoc = -1;
/* 102 */   private static int phaze$cachedFogMixColorLoc = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static int phaze$uboBufferId = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static int phaze$lastUploadedMode = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private static int phaze$lastUploadedFadeStyle = -1;
/* 135 */   private static float phaze$lastFogMixR = Float.NaN;
/* 136 */   private static float phaze$lastFogMixG = Float.NaN;
/* 137 */   private static float phaze$lastFogMixB = Float.NaN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean phaze$lastWasIdentity = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   private static final float[] PHAZE$VEC4 = new float[1024];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   private static final float[] PHAZE$SCALAR_SCRATCH = new float[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   private static final float[] PHAZE$DIR = new float[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   private static final int[] PHAZE$SLOT_BUFFER = new int[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyArgs(method = {"setModelMatrixUniforms"}, at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/shader/ChunkShaderInterface;setRegionOffset(FFF)V", remap = false), remap = false)
/*     */   private static void phaze$fallbackRegionOffset(Args args, @Local(argsOnly = true) ChunkShaderInterface shader, @Local(argsOnly = true) RenderRegion region) {
/* 225 */     ChunkAnimator animator = ChunkAnimator.getInstance();
/* 226 */     if (animator == null || region == null || shader == null) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     if (shader != phaze$cachedShader) {
/* 249 */       int programId = GL20.glGetInteger(35725);
/* 250 */       if (programId == 0) {
/*     */         return;
/*     */       }
/* 253 */       phaze$cachedShader = shader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       phaze$cachedBlockIdx = GL31.glGetUniformBlockIndex(programId, "PhazeChunkAnimBlock");
/*     */       
/* 263 */       if (phaze$cachedBlockIdx != -1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 271 */         GL31.glUniformBlockBinding(programId, phaze$cachedBlockIdx, 0);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 276 */       phaze$cachedModeLoc = GL20.glGetUniformLocation(programId, "u_PhazeChunkAnimMode");
/*     */       
/* 278 */       phaze$cachedFadeStyleLoc = GL20.glGetUniformLocation(programId, "u_PhazeFadeStyle");
/*     */       
/* 280 */       phaze$cachedFogMixColorLoc = GL20.glGetUniformLocation(programId, "u_PhazeFogMixColor");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       phaze$lastUploadedFadeStyle = -1;
/* 291 */       phaze$lastUploadedMode = -1;
/* 292 */       phaze$lastFogMixR = Float.NaN;
/* 293 */       phaze$lastFogMixG = Float.NaN;
/* 294 */       phaze$lastFogMixB = Float.NaN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       phaze$lastWasIdentity = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 306 */     if (!animator.isEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     if (phaze$cachedBlockIdx != -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 319 */     ChunkRenderList renderList = region.getRenderList();
/* 320 */     if (renderList == null) {
/*     */       return;
/*     */     }
/* 323 */     ByteIterator iter = renderList.sectionsWithGeometryIterator(false);
/* 324 */     if (iter == null) {
/*     */       return;
/*     */     }
/* 327 */     int slotCount = 0;
/* 328 */     while (iter.hasNext() && slotCount < PHAZE$SLOT_BUFFER.length) {
/* 329 */       PHAZE$SLOT_BUFFER[slotCount++] = iter.nextByteAsInt();
/*     */     }
/* 331 */     if (slotCount == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 335 */     float magnitude = animator.getRegionMagnitude(region
/* 336 */         .getOriginX(), region.getOriginY(), region.getOriginZ(), PHAZE$SLOT_BUFFER, slotCount);
/*     */     
/* 338 */     if (magnitude == 0.0F) {
/*     */       return;
/*     */     }
/* 341 */     animator.writeAnimationDirection(PHAZE$DIR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     if (PHAZE$DIR[0] != 0.0F) args.set(0, Float.valueOf(((Float)args.get(0)).floatValue() + magnitude * PHAZE$DIR[0])); 
/* 350 */     if (PHAZE$DIR[1] != 0.0F) args.set(1, Float.valueOf(((Float)args.get(1)).floatValue() + magnitude * PHAZE$DIR[1])); 
/* 351 */     if (PHAZE$DIR[2] != 0.0F) args.set(2, Float.valueOf(((Float)args.get(2)).floatValue() + magnitude * PHAZE$DIR[2]));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"setModelMatrixUniforms"}, at = {@At("TAIL")}, remap = false)
/*     */   private static void phaze$uploadSectionAnimOffsets(ChunkShaderInterface shader, RenderRegion region, CameraTransform cameraTransform, CallbackInfo ci) {
/* 364 */     ChunkAnimator animator = ChunkAnimator.getInstance();
/* 365 */     if (animator == null || region == null) {
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
/* 376 */     if (phaze$cachedBlockIdx == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 380 */     int mode = animator.getAnimationModeIndex();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     if (mode == 0) {
/* 389 */       if (!phaze$lastWasIdentity) {
/* 390 */         phaze$fillIdentity(PHAZE$VEC4);
/* 391 */         phaze$uploadUbo(PHAZE$VEC4);
/* 392 */         phaze$lastWasIdentity = true;
/*     */       } 
/* 394 */       if (phaze$lastUploadedMode != 0 && phaze$cachedModeLoc >= 0) {
/* 395 */         GL20.glUniform1i(phaze$cachedModeLoc, 0);
/* 396 */         phaze$lastUploadedMode = 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     ChunkRenderList renderList = region.getRenderList();
/* 409 */     if (renderList == null) {
/*     */       return;
/*     */     }
/* 412 */     ByteIterator iter = renderList.sectionsWithGeometryIterator(false);
/* 413 */     if (iter == null) {
/*     */       return;
/*     */     }
/* 416 */     int slotCount = 0;
/* 417 */     while (iter.hasNext() && slotCount < PHAZE$SLOT_BUFFER.length) {
/* 418 */       PHAZE$SLOT_BUFFER[slotCount++] = iter.nextByteAsInt();
/*     */     }
/* 420 */     if (slotCount == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     boolean hasNonIdentity = phaze$packAnimData(animator, region, slotCount, mode, PHAZE$VEC4);
/*     */ 
/*     */     
/* 432 */     if (hasNonIdentity || !phaze$lastWasIdentity) {
/* 433 */       phaze$uploadUbo(PHAZE$VEC4);
/* 434 */       phaze$lastWasIdentity = !hasNonIdentity;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     if (phaze$cachedModeLoc >= 0 && mode != phaze$lastUploadedMode) {
/* 442 */       GL20.glUniform1i(phaze$cachedModeLoc, mode);
/* 443 */       phaze$lastUploadedMode = mode;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     if (phaze$cachedFadeStyleLoc >= 0 && (animator.isFadeMode() || animator.isScaleMode())) {
/* 454 */       int currentStyle = animator.getFadeStyleIndex();
/* 455 */       if (currentStyle != phaze$lastUploadedFadeStyle) {
/* 456 */         GL20.glUniform1i(phaze$cachedFadeStyleLoc, currentStyle);
/* 457 */         phaze$lastUploadedFadeStyle = currentStyle;
/*     */       } 
/*     */     } 
/* 460 */     if (phaze$cachedFogMixColorLoc >= 0 && (animator.isFadeMode() || animator.isScaleMode())) {
/* 461 */       float r = FogColorTracker.red();
/* 462 */       float g = FogColorTracker.green();
/* 463 */       float b = FogColorTracker.blue();
/* 464 */       if (Float.compare(r, phaze$lastFogMixR) != 0 || 
/* 465 */         Float.compare(g, phaze$lastFogMixG) != 0 || 
/* 466 */         Float.compare(b, phaze$lastFogMixB) != 0) {
/* 467 */         GL20.glUniform3f(phaze$cachedFogMixColorLoc, r, g, b);
/* 468 */         phaze$lastFogMixR = r;
/* 469 */         phaze$lastFogMixG = g;
/* 470 */         phaze$lastFogMixB = b;
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
/*     */   private static void phaze$fillIdentity(float[] vec4) {
/* 484 */     for (int i = 0; i < 256; i++) {
/* 485 */       int o = i * 4;
/* 486 */       vec4[o] = 0.0F;
/* 487 */       vec4[o + 1] = 0.0F;
/* 488 */       vec4[o + 2] = 0.0F;
/* 489 */       vec4[o + 3] = 1.0F;
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
/*     */   private static boolean phaze$packAnimData(ChunkAnimator animator, RenderRegion region, int slotCount, int mode, float[] vec4) {
/* 520 */     phaze$fillIdentity(vec4);
/*     */     
/* 522 */     if (mode == 1) {
/* 523 */       boolean hasNonZero = animator.writeRegionSectionYOffsets(region
/* 524 */           .getOriginX(), region.getOriginY(), region.getOriginZ(), PHAZE$SLOT_BUFFER, slotCount, PHAZE$SCALAR_SCRATCH);
/*     */       
/* 526 */       if (!hasNonZero) {
/* 527 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 534 */       animator.writeAnimationDirectionPerSection(PHAZE$DIR);
/* 535 */       for (int i = 0; i < 256; i++) {
/* 536 */         float mag = PHAZE$SCALAR_SCRATCH[i];
/* 537 */         if (mag != 0.0F) {
/* 538 */           int o = i * 4;
/* 539 */           vec4[o] = mag * PHAZE$DIR[0];
/* 540 */           vec4[o + 1] = mag * PHAZE$DIR[1];
/* 541 */           vec4[o + 2] = mag * PHAZE$DIR[2];
/*     */         } 
/*     */       } 
/*     */       
/* 545 */       return true;
/*     */     } 
/*     */     
/* 548 */     if (mode == 2) {
/* 549 */       boolean hasNonOne = animator.writeRegionSectionFadeValues(region
/* 550 */           .getOriginX(), region.getOriginY(), region.getOriginZ(), PHAZE$SLOT_BUFFER, slotCount, PHAZE$SCALAR_SCRATCH);
/*     */       
/* 552 */       if (!hasNonOne) {
/* 553 */         return false;
/*     */       }
/* 555 */       for (int i = 0; i < 256; i++) {
/* 556 */         vec4[i * 4 + 3] = PHAZE$SCALAR_SCRATCH[i];
/*     */       }
/* 558 */       return true;
/*     */     } 
/*     */     
/* 561 */     if (mode == 3) {
/* 562 */       boolean hasNonOne = animator.writeRegionSectionScaleValues(region
/* 563 */           .getOriginX(), region.getOriginY(), region.getOriginZ(), PHAZE$SLOT_BUFFER, slotCount, PHAZE$SCALAR_SCRATCH);
/*     */       
/* 565 */       if (!hasNonOne) {
/* 566 */         return false;
/*     */       }
/* 568 */       for (int i = 0; i < 256; i++) {
/* 569 */         vec4[i * 4 + 3] = PHAZE$SCALAR_SCRATCH[i];
/*     */       }
/* 571 */       return true;
/*     */     } 
/*     */     
/* 574 */     return false;
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
/*     */   private static void phaze$uploadUbo(float[] vec4) {
/* 590 */     if (phaze$uboBufferId == 0) {
/* 591 */       phaze$uboBufferId = GL15.glGenBuffers();
/*     */     }
/* 593 */     GL15.glBindBuffer(35345, phaze$uboBufferId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 600 */     GL15.glBufferData(35345, vec4, 35040);
/* 601 */     GL30.glBindBufferBase(35345, 0, phaze$uboBufferId);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\DefaultChunkRendererChunkAnimatorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */