/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_3966;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_9779;
/*     */ import net.minecraft.class_9801;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.render.Render3DUtil;
/*     */ 
/*     */ public final class PredictionsRenderer {
/*  32 */   private static final class_2960 GLOW_TEXTURE = class_2960.method_60655("phaze", "textures/particles/bloom/bloom_soft.png");
/*  33 */   private static final float[] MULTISHOT_YAW_OFFSETS = new float[] { -10.0F, 0.0F, 10.0F };
/*  34 */   private static final ArrayList<Predictions.TrajectoryResult> TRAJECTORIES = new ArrayList<>(3);
/*  35 */   private static final ArrayList<ImpactMark> IMPACT_MARKS = new ArrayList<>(3);
/*  36 */   private static final SnapCache[] SNAP_CACHE = new SnapCache[4];
/*     */   private static Object snapCacheWorld;
/*  38 */   private static long snapCacheTick = Long.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int snapCacheCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int snapCacheWriteIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final Animation entityGrow = (new DecelerateAnimation()).setMs(180).setValue(1.0D); static {
/*  58 */     entityGrow.setDirection(Direction.BACKWARDS);
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
/*     */   private static class_243 lerpedEyePos(class_310 mc, class_9779 tickCounter) {
/*  81 */     class_746 class_746 = mc.field_1724;
/*  82 */     float td = tickCounter.method_60637(false);
/*  83 */     return class_746.method_5836(td);
/*     */   }
/*     */   
/*     */   public static void render(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter) {
/*  87 */     Predictions module = Predictions.getInstance();
/*  88 */     if (module == null || !module.isEnabled())
/*  89 */       return;  class_310 mc = class_310.method_1551();
/*  90 */     if (mc == null || mc.field_1724 == null || mc.field_1687 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  96 */     int accent = module.resolveAccentColor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     int entityAccent = module.resolveEntityHitColor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     Collection<Predictions.ProjectileTrail> trails = module.getTrails();
/* 113 */     if (!trails.isEmpty()) {
/* 114 */       float trailWidth = Math.max(1.0F, module.lineWidth.getInt());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       float tickDelta = tickCounter.method_60637(false);
/*     */       
/* 123 */       for (Predictions.ProjectileTrail t : trails) {
/* 124 */         if (t.entity == null)
/* 125 */           continue;  double lx = class_3532.method_16436(tickDelta, t.entity.field_6038, t.entity.method_23317());
/* 126 */         double ly = class_3532.method_16436(tickDelta, t.entity.field_5971, t.entity.method_23318());
/* 127 */         double lz = class_3532.method_16436(tickDelta, t.entity.field_5989, t.entity.method_23321());
/* 128 */         class_243 lerpedPos = new class_243(lx, ly, lz);
/* 129 */         List<class_243> pts = t.getRemainingPath(lerpedPos);
/* 130 */         if (pts != null && pts.size() >= 2) {
/*     */ 
/*     */ 
/*     */           
/* 134 */           if (t.smoothedImpact != null && t.result != null && t.result
/*     */             
/* 136 */             .type() != class_239.class_240.field_1333) {
/* 137 */             pts.set(pts.size() - 1, t.smoothedImpact);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 144 */           int lineColor = (t.result != null && t.result.type() == class_239.class_240.field_1331) ? entityAccent : accent;
/* 145 */           if (module.fadeTrail.isValue()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 153 */             float fadeDist = module.fadeDistance.getValue();
/* 154 */             Render3DUtil.drawPolylineFadedOffset(matrices, pts, -cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350, lineColor, trailWidth, fadeDist, true);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 160 */           Render3DUtil.drawPolylineOffset(matrices, pts, -cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350, lineColor, trailWidth, true);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       if (module.showImpactSphere.isValue()) {
/* 175 */         float baseRadius = module.impactSphereRadius.getValue();
/* 176 */         String style = module.impactMarkerStyle.getSelected();
/* 177 */         boolean sphere = !"Circle".equalsIgnoreCase(style);
/* 178 */         int sphereAlpha = Math.max(0, Math.min(255, 
/* 179 */               Math.round(module.sphereOpacity.getValue() * 2.55F)));
/*     */         
/* 181 */         long nowNanos = System.nanoTime();
/* 182 */         for (Predictions.ProjectileTrail t : trails) {
/* 183 */           Predictions.TrajectoryResult result = t.result;
/* 184 */           if (result == null || result.type() == class_239.class_240.field_1333)
/* 185 */             continue;  class_243 impactRaw = result.impact();
/* 186 */           if (impactRaw == null) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 192 */           t.smoothedImpact = impactRaw;
/* 193 */           t.lastSmoothNanos = nowNanos;
/*     */           
/* 195 */           class_243 impact = impactRaw;
/* 196 */           boolean isEntity = (result.type() == class_239.class_240.field_1331);
/* 197 */           if (isEntity && !phaze$shouldTargetEntityForPrediction(result.entity())) {
/*     */             continue;
/*     */           }
/* 200 */           float radius = baseRadius;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 205 */           int markerAccent = isEntity ? entityAccent : accent;
/* 206 */           int sphereColor = sphereAlpha << 24 | markerAccent & 0xFFFFFF;
/*     */           
/* 208 */           class_243 toward = cameraPos.method_1020(impact).method_1029();
/* 209 */           double frontShift = 0.0D;
/* 210 */           double cx = impact.field_1352 + toward.field_1352 * frontShift;
/* 211 */           double cy = impact.field_1351 + toward.field_1351 * frontShift;
/* 212 */           double cz = impact.field_1350 + toward.field_1350 * frontShift;
/*     */           
/* 214 */           if (sphere) {
/* 215 */             double sphereCy = isEntity ? cy : (cy + module.sphereYOffset.getValue());
/* 216 */             if (module.showGlow.isValue()) {
/* 217 */               float strength = module.glowStrength.getValue();
/* 218 */               float haloRadius = radius * (1.6F + strength * 1.4F);
/* 219 */               float pulse = phaze$pulseFactor(module);
/* 220 */               int haloAlpha = Math.max(0, Math.min(255, Math.round(110.0F * Math.min(2.0F, strength) * pulse)));
/* 221 */               int haloColor = haloAlpha << 24 | markerAccent & 0xFFFFFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 227 */               Render3DUtil.drawBillboard(matrices, GLOW_TEXTURE, (float)(cx - cameraPos.field_1352), (float)(sphereCy - cameraPos.field_1351), (float)(cz - cameraPos.field_1350), haloRadius, haloColor, true);
/*     */             } 
/*     */ 
/*     */             
/* 231 */             Render3DUtil.drawSphereSolid(matrices, (float)(cx - cameraPos.field_1352), (float)(sphereCy - cameraPos.field_1351), (float)(cz - cameraPos.field_1350), radius, sphereColor, 12, 18, true);
/*     */             continue;
/*     */           } 
/* 234 */           class_2350 face = result.face();
/* 235 */           float nx = face.method_10148();
/* 236 */           float ny = face.method_10164();
/* 237 */           float nz = face.method_10165();
/* 238 */           float surfaceLift = 0.02F;
/* 239 */           double markerCx = isEntity ? cx : (impact.field_1352 + (nx * surfaceLift));
/* 240 */           double markerCy = isEntity ? cy : (impact.field_1351 + (ny * surfaceLift));
/* 241 */           double markerCz = isEntity ? cz : (impact.field_1350 + (nz * surfaceLift));
/* 242 */           if (module.showGlow.isValue()) {
/* 243 */             float strength = module.glowStrength.getValue();
/* 244 */             float haloRadius = radius * (1.6F + strength * 1.4F);
/* 245 */             float pulse = phaze$pulseFactor(module);
/* 246 */             int haloAlpha = Math.max(0, Math.min(255, Math.round(110.0F * Math.min(2.0F, strength) * pulse)));
/* 247 */             int haloColor = haloAlpha << 24 | markerAccent & 0xFFFFFF;
/* 248 */             Render3DUtil.drawBillboard(matrices, GLOW_TEXTURE, (float)(markerCx - cameraPos.field_1352), (float)(markerCy - cameraPos.field_1351), (float)(markerCz - cameraPos.field_1350), haloRadius, haloColor, true);
/*     */           } 
/*     */ 
/*     */           
/* 252 */           float ringThickness = module.circleThickness.getValue();
/* 253 */           if (isEntity) {
/* 254 */             Render3DUtil.drawThickRingOnFace(matrices, (float)(cx - cameraPos.field_1352), (float)(cy - cameraPos.field_1351), (float)(cz - cameraPos.field_1350), 0.0F, 1.0F, 0.0F, radius, ringThickness, markerAccent, 64, true);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 259 */           Render3DUtil.drawThickRingOnFace(matrices, (float)(markerCx - cameraPos.field_1352), (float)(markerCy - cameraPos.field_1351), (float)(markerCz - cameraPos.field_1350), nx, ny, nz, radius * 1.35F, ringThickness, markerAccent, 64, true);
/*     */         } 
/*     */       } 
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
/* 273 */     TRAJECTORIES.clear();
/* 274 */     IMPACT_MARKS.clear();
/* 275 */     List<Predictions.TrajectoryResult> trajectories = TRAJECTORIES;
/* 276 */     List<ImpactMark> marks = IMPACT_MARKS;
/*     */     
/* 278 */     if (module.shouldPredictHeld()) {
/* 279 */       collectHeldHand(mc, module, tickCounter, trajectories, marks);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 284 */     boolean hasLines = false;
/* 285 */     for (Predictions.TrajectoryResult r : trajectories) {
/* 286 */       if (r != null && r.path() != null && r.path().size() >= 2) {
/* 287 */         hasLines = true;
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 296 */     if (hasLines) {
/* 297 */       RenderSystem.enableBlend();
/* 298 */       RenderSystem.defaultBlendFunc();
/* 299 */       RenderSystem.disableCull();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 305 */       RenderSystem.enableDepthTest();
/* 306 */       RenderSystem.depthMask(false);
/* 307 */       RenderSystem.setShader(class_10142.field_53864);
/* 308 */       RenderSystem.lineWidth(Math.max(1.0F, module.lineWidth.getInt()));
/*     */       
/* 310 */       class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/* 311 */       for (Predictions.TrajectoryResult r : trajectories) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 318 */         int lineColor = (r != null && r.type() == class_239.class_240.field_1331) ? entityAccent : accent;
/* 319 */         emitPath(matrices, buffer, r, lineColor, cameraPos);
/*     */       } 
/*     */       
/* 322 */       class_9801 built = buffer.method_60794();
/* 323 */       if (built != null) {
/* 324 */         class_286.method_43433(built);
/*     */       }
/*     */       
/* 327 */       RenderSystem.depthMask(true);
/* 328 */       RenderSystem.enableDepthTest();
/* 329 */       RenderSystem.enableCull();
/* 330 */       RenderSystem.disableBlend();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     if (!marks.isEmpty() && module.showImpactSphere.isValue()) {
/* 337 */       float baseRadius = module.impactSphereRadius.getValue();
/* 338 */       String style = module.impactMarkerStyle.getSelected();
/* 339 */       boolean sphere = !"Circle".equalsIgnoreCase(style);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       int sphereAlpha = Math.max(0, Math.min(255, 
/* 348 */             Math.round(module.sphereOpacity.getValue() * 2.55F)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 356 */       boolean anyEntity = false;
/* 357 */       for (ImpactMark m : marks) {
/* 358 */         if (m.entity) { anyEntity = true; break; }
/*     */       
/* 360 */       }  entityGrow.setDirection(anyEntity ? Direction.FORWARDS : Direction.BACKWARDS);
/* 361 */       float growProgress = entityGrow.getOutputFloat();
/* 362 */       float entityScale = 1.0F + 0.3F * growProgress;
/*     */       
/* 364 */       for (ImpactMark m : marks) {
/* 365 */         double markerCx, markerCy, markerCz; if (m.entity && !phaze$shouldTargetEntityForPrediction(m.entityRef)) {
/*     */           continue;
/*     */         }
/* 368 */         float radius = m.entity ? (baseRadius * entityScale) : baseRadius;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 373 */         int markerAccent = m.entity ? entityAccent : accent;
/* 374 */         int sphereColor = sphereAlpha << 24 | markerAccent & 0xFFFFFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 380 */         class_243 toward = cameraPos.method_1020(m.pos).method_1029();
/* 381 */         double frontShift = m.entity ? (radius * 1.05D) : 0.0D;
/* 382 */         double cx = m.pos.field_1352 + toward.field_1352 * frontShift;
/* 383 */         double cy = m.pos.field_1351 + toward.field_1351 * frontShift;
/* 384 */         double cz = m.pos.field_1350 + toward.field_1350 * frontShift;
/*     */         
/* 386 */         if (sphere) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 393 */           double sphereCy = m.entity ? cy : (cy + module.sphereYOffset.getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 403 */           if (module.showGlow.isValue()) {
/* 404 */             float strength = module.glowStrength.getValue();
/* 405 */             float haloRadius = radius * (1.6F + strength * 1.4F);
/* 406 */             float pulse = phaze$pulseFactor(module);
/* 407 */             int haloAlpha = Math.max(0, Math.min(255, Math.round(110.0F * Math.min(2.0F, strength) * pulse)));
/* 408 */             int haloColor = haloAlpha << 24 | markerAccent & 0xFFFFFF;
/* 409 */             Render3DUtil.drawBillboard(matrices, GLOW_TEXTURE, (float)(cx - cameraPos.field_1352), (float)(sphereCy - cameraPos.field_1351), (float)(cz - cameraPos.field_1350), haloRadius, haloColor, true);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 414 */           Render3DUtil.drawSphereSolid(matrices, (float)(cx - cameraPos.field_1352), (float)(sphereCy - cameraPos.field_1351), (float)(cz - cameraPos.field_1350), radius, sphereColor, 12, 18, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 432 */         class_2350 face = m.face;
/* 433 */         float nx = face.method_10148();
/* 434 */         float ny = face.method_10164();
/* 435 */         float nz = face.method_10165();
/*     */ 
/*     */         
/* 438 */         float surfaceLift = 0.02F;
/*     */         
/* 440 */         if (m.entity) {
/* 441 */           markerCx = cx;
/* 442 */           markerCy = cy;
/* 443 */           markerCz = cz;
/*     */         } else {
/* 445 */           markerCx = m.pos.field_1352 + (nx * surfaceLift);
/* 446 */           markerCy = m.pos.field_1351 + (ny * surfaceLift);
/* 447 */           markerCz = m.pos.field_1350 + (nz * surfaceLift);
/*     */         } 
/* 449 */         if (module.showGlow.isValue()) {
/* 450 */           float strength = module.glowStrength.getValue();
/* 451 */           float haloRadius = radius * (1.6F + strength * 1.4F);
/* 452 */           float pulse = phaze$pulseFactor(module);
/* 453 */           int haloAlpha = Math.max(0, Math.min(255, Math.round(110.0F * Math.min(2.0F, strength) * pulse)));
/* 454 */           int haloColor = haloAlpha << 24 | markerAccent & 0xFFFFFF;
/* 455 */           Render3DUtil.drawBillboard(matrices, GLOW_TEXTURE, (float)(markerCx - cameraPos.field_1352), (float)(markerCy - cameraPos.field_1351), (float)(markerCz - cameraPos.field_1350), haloRadius, haloColor, true);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 463 */         float ringThickness = module.circleThickness.getValue();
/* 464 */         if (m.entity) {
/*     */ 
/*     */ 
/*     */           
/* 468 */           Render3DUtil.drawThickRingOnFace(matrices, (float)(cx - cameraPos.field_1352), (float)(cy - cameraPos.field_1351), (float)(cz - cameraPos.field_1350), 0.0F, 1.0F, 0.0F, radius, ringThickness, markerAccent, 64, true);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 473 */         Render3DUtil.drawThickRingOnFace(matrices, (float)(markerCx - cameraPos.field_1352), (float)(markerCy - cameraPos.field_1351), (float)(markerCz - cameraPos.field_1350), nx, ny, nz, radius * 1.35F, ringThickness, markerAccent, 64, true);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 484 */       entityGrow.setDirection(Direction.BACKWARDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void collectHeldHand(class_310 mc, Predictions module, class_9779 tickCounter, List<Predictions.TrajectoryResult> out, List<ImpactMark> marks) {
/* 492 */     class_746 class_746 = mc.field_1724;
/* 493 */     class_1799 main = class_746.method_6047();
/* 494 */     class_1799 off = class_746.method_6079();
/* 495 */     class_1799 chosen = pickThrowable(module, main, off);
/* 496 */     if (chosen == null)
/*     */       return; 
/* 498 */     Predictions.HeldType type = module.classifyHeldStack(chosen);
/* 499 */     if (type == Predictions.HeldType.BOW) {
/*     */ 
/*     */ 
/*     */       
/* 503 */       if (!class_746.method_6115())
/* 504 */         return;  class_1799 active = class_746.method_6030();
/* 505 */       if (active == null || active.method_7960())
/* 506 */         return;  if (!class_1799.method_31577(active, chosen))
/*     */         return; 
/* 508 */     }  double speed = module.initialVelocityFor(type, chosen, mc);
/* 509 */     if (speed <= 0.0D)
/*     */       return; 
/* 511 */     class_243 look = class_746.method_5828(1.0F);
/* 512 */     class_243 motion = look.method_1021(speed);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 517 */     class_243 eye = lerpedEyePos(mc, tickCounter);
/* 518 */     switch (type) { case SNOWBALL: case EGG: case ENDER_PEARL: case EXPERIENCE_BOTTLE: case SPLASH_POTION: 
/*     */       case BOW: case CROSSBOW: case TRIDENT: 
/*     */       default:
/* 521 */         break; }  double gravity = 0.03D;
/*     */     
/* 523 */     boolean trident = (type == Predictions.HeldType.TRIDENT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 530 */     boolean multishot = (type == Predictions.HeldType.CROSSBOW && module.hasMultishot(chosen));
/* 531 */     if (multishot) {
/* 532 */       for (float yawOffset : MULTISHOT_YAW_OFFSETS) {
/* 533 */         class_243 fanLook = phaze$rotateLookYaw(look, yawOffset);
/* 534 */         class_243 fanMotion = fanLook.method_1021(speed);
/* 535 */         Predictions.TrajectoryResult r = module.predictCached(eye, fanMotion, gravity, trident, (class_1297)class_746);
/* 536 */         if (r != null && r.path() != null && r.path().size() >= 2) {
/* 537 */           Predictions.TrajectoryResult s = phaze$snapToTargetedEntity(mc, (class_1657)class_746, eye, fanLook, r, speed);
/* 538 */           if (s != null) r = s; 
/* 539 */           out.add(r);
/* 540 */           if (r.type() != class_239.class_240.field_1333) {
/* 541 */             marks.add(new ImpactMark(r.impact(), (r.type() == class_239.class_240.field_1331), r.face(), r.entity()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       return;
/*     */     } 
/* 547 */     Predictions.TrajectoryResult result = module.predictCached(eye, motion, gravity, trident, (class_1297)class_746);
/* 548 */     if (result == null || result.path() == null || result.path().size() < 2) {
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
/* 559 */     Predictions.TrajectoryResult snapped = phaze$snapToTargetedEntity(mc, (class_1657)class_746, eye, look, result, speed);
/* 560 */     if (snapped != null) {
/* 561 */       result = snapped;
/*     */     }
/*     */     
/* 564 */     out.add(result);
/* 565 */     if (result.type() != class_239.class_240.field_1333) {
/* 566 */       marks.add(new ImpactMark(result.impact(), (result.type() == class_239.class_240.field_1331), result.face(), result.entity()));
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
/*     */   private static class_243 phaze$rotateLookYaw(class_243 look, float yawDegrees) {
/* 578 */     double rad = Math.toRadians(yawDegrees);
/* 579 */     double cos = Math.cos(rad);
/* 580 */     double sin = Math.sin(rad);
/*     */ 
/*     */ 
/*     */     
/* 584 */     double x = look.field_1352 * cos + look.field_1350 * sin;
/* 585 */     double z = -look.field_1352 * sin + look.field_1350 * cos;
/* 586 */     return (new class_243(x, look.field_1351, z)).method_1029();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Predictions.TrajectoryResult phaze$snapToTargetedEntity(class_310 mc, class_1657 self, class_243 eye, class_243 look, Predictions.TrajectoryResult ballistic, double speed) {
/* 592 */     if (mc.field_1687 == null) return null; 
/* 593 */     long worldTick = mc.field_1687.method_8510();
/* 594 */     if (snapCacheWorld != mc.field_1687 || snapCacheTick != worldTick) {
/* 595 */       snapCacheWorld = mc.field_1687;
/* 596 */       snapCacheTick = worldTick;
/* 597 */       snapCacheCount = 0;
/* 598 */       snapCacheWriteIndex = 0;
/*     */     } 
/* 600 */     for (int i = 0; i < snapCacheCount; i++) {
/* 601 */       SnapCache cached = SNAP_CACHE[i];
/* 602 */       if (cached != null && cached.matches(self, eye, look, ballistic, speed)) {
/* 603 */         return cached.result;
/*     */       }
/*     */     } 
/*     */     
/* 607 */     Predictions.TrajectoryResult result = phaze$computeSnapToTargetedEntity(mc, self, eye, look, ballistic, speed);
/* 608 */     SNAP_CACHE[snapCacheWriteIndex] = new SnapCache(self, eye, look, ballistic, speed, result);
/* 609 */     snapCacheWriteIndex = (snapCacheWriteIndex + 1) % SNAP_CACHE.length;
/* 610 */     snapCacheCount = Math.min(SNAP_CACHE.length, snapCacheCount + 1);
/* 611 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Predictions.TrajectoryResult phaze$computeSnapToTargetedEntity(class_310 mc, class_1657 self, class_243 eye, class_243 look, Predictions.TrajectoryResult ballistic, double speed) {
/*     */     double maxRange;
/* 617 */     if (mc.field_1687 == null) return null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 624 */     if (ballistic != null && ballistic.impact() != null) {
/* 625 */       maxRange = Math.max(8.0D, eye.method_1022(ballistic.impact()) * 1.5D);
/*     */     } else {
/* 627 */       maxRange = 64.0D;
/*     */     } 
/*     */     
/* 630 */     class_243 end = eye.method_1019(look.method_1021(maxRange));
/*     */ 
/*     */     
/* 633 */     class_238 searchBox = self.method_5829().method_18804(look.method_1021(maxRange)).method_1009(1.0D, 1.0D, 1.0D);
/*     */     
/* 635 */     class_3966 hit = class_1675.method_18075((class_1297)self, eye, end, searchBox, e -> 
/*     */         
/* 637 */         (!e.method_7325() && e instanceof class_1309 && e != self && phaze$shouldTargetEntityForPrediction(e)), maxRange * maxRange);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     if (hit == null || hit.method_17782() == null) {
/* 643 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 652 */     class_3959 blockCtx = new class_3959(eye, end, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)self);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 657 */     class_3965 class_3965 = mc.field_1687.method_17742(blockCtx);
/* 658 */     if (class_3965 != null && class_3965.method_17783() == class_239.class_240.field_1332) {
/* 659 */       double blockDistSq = eye.method_1025(class_3965.method_17784());
/* 660 */       double entityDistSq = eye.method_1025(hit.method_17784());
/* 661 */       if (blockDistSq < entityDistSq) {
/* 662 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 666 */     class_1297 target = hit.method_17782();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 672 */     class_243 impactPos = hit.method_17784();
/*     */     
/* 674 */     List<class_243> path = new ArrayList<>();
/* 675 */     path.add(eye);
/* 676 */     path.add(impactPos);
/* 677 */     return new Predictions.TrajectoryResult(path, impactPos, class_239.class_240.field_1331, class_2350.field_11036, target);
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
/*     */   private static float phaze$pulseFactor(Predictions module) {
/* 694 */     if (!module.glowPulsate.isValue()) return 1.0F; 
/* 695 */     float speed = module.glowPulsateSpeed.getValue();
/* 696 */     if (speed <= 0.0F) speed = 1.0E-4F; 
/* 697 */     double t = System.nanoTime() / 1.0E9D * speed;
/*     */ 
/*     */ 
/*     */     
/* 701 */     float s = (float)(0.5D + 0.5D * Math.sin(t * Math.PI * 2.0D));
/* 702 */     return 0.1F + s * 0.9F;
/*     */   }
/*     */   private static boolean phaze$shouldTargetEntityForPrediction(class_1297 entity) {
/*     */     class_1309 living;
/* 706 */     if (entity == null || entity.method_7325()) return false; 
/* 707 */     if (entity.method_5851()) return true; 
/* 708 */     if (!entity.method_5767()) return true; 
/* 709 */     if (entity instanceof class_1309) { living = (class_1309)entity; } else { return false; }
/* 710 */      for (class_1799 armor : living.method_5661()) {
/* 711 */       if (armor != null && !armor.method_7960()) {
/* 712 */         return true;
/*     */       }
/*     */     } 
/* 715 */     return false;
/*     */   }
/*     */   
/*     */   private static void emitPath(class_4587 matrices, class_287 buffer, Predictions.TrajectoryResult result, int color, class_243 cameraPos) {
/* 719 */     List<class_243> path = result.path();
/* 720 */     if (path.size() < 2)
/* 721 */       return;  for (int i = 0; i < path.size() - 1; i++) {
/* 722 */       class_243 start = path.get(i);
/* 723 */       class_243 end = path.get(i + 1);
/* 724 */       Render3DUtil.vertexLine(matrices, (class_4588)buffer, start.field_1352 - cameraPos.field_1352, start.field_1351 - cameraPos.field_1351, start.field_1350 - cameraPos.field_1350, end.field_1352 - cameraPos.field_1352, end.field_1351 - cameraPos.field_1351, end.field_1350 - cameraPos.field_1350, color, color);
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
/*     */   private static class_1799 pickThrowable(Predictions module, class_1799 main, class_1799 off) {
/* 740 */     if (module.classifyHeldStack(main) != Predictions.HeldType.NONE) return main; 
/* 741 */     if (module.classifyHeldStack(off) != Predictions.HeldType.NONE) return off; 
/* 742 */     return null;
/*     */   }
/*     */   private static final class ImpactMark extends Record { private final class_243 pos; private final boolean entity; private final class_2350 face; private final class_1297 entityRef;
/*     */     
/* 746 */     private ImpactMark(class_243 pos, boolean entity, class_2350 face, class_1297 entityRef) { this.pos = pos; this.entity = entity; this.face = face; this.entityRef = entityRef; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$ImpactMark;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #746	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 746 */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$ImpactMark; } public class_243 pos() { return this.pos; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$ImpactMark;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #746	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$ImpactMark; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$ImpactMark;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #746	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$ImpactMark;
/* 746 */       //   0	8	1	o	Ljava/lang/Object; } public boolean entity() { return this.entity; } public class_2350 face() { return this.face; } public class_1297 entityRef() { return this.entityRef; }
/*     */      }
/*     */   private static final class SnapCache extends Record { private final class_1657 player; private final class_243 eye; private final class_243 look; private final Predictions.TrajectoryResult ballistic; private final double speed; private final Predictions.TrajectoryResult result;
/* 749 */     private SnapCache(class_1657 player, class_243 eye, class_243 look, Predictions.TrajectoryResult ballistic, double speed, Predictions.TrajectoryResult result) { this.player = player; this.eye = eye; this.look = look; this.ballistic = ballistic; this.speed = speed; this.result = result; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$SnapCache;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #749	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$SnapCache; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$SnapCache;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #749	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$SnapCache; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$SnapCache;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #749	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PredictionsRenderer$SnapCache;
/* 749 */       //   0	8	1	o	Ljava/lang/Object; } public class_1657 player() { return this.player; } public class_243 eye() { return this.eye; } public class_243 look() { return this.look; } public Predictions.TrajectoryResult ballistic() { return this.ballistic; } public double speed() { return this.speed; } public Predictions.TrajectoryResult result() { return this.result; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean matches(class_1657 player, class_243 eye, class_243 look, Predictions.TrajectoryResult ballistic, double speed) {
/* 764 */       return (this.player == player && this.ballistic == ballistic && 
/*     */         
/* 766 */         same(this.eye, eye) && 
/* 767 */         same(this.look, look) && 
/* 768 */         Double.doubleToLongBits(this.speed) == Double.doubleToLongBits(speed));
/*     */     }
/*     */     
/*     */     private static boolean same(class_243 first, class_243 second) {
/* 772 */       return (first != null && second != null && 
/* 773 */         Double.doubleToLongBits(first.field_1352) == Double.doubleToLongBits(second.field_1352) && 
/* 774 */         Double.doubleToLongBits(first.field_1351) == Double.doubleToLongBits(second.field_1351) && 
/* 775 */         Double.doubleToLongBits(first.field_1350) == Double.doubleToLongBits(second.field_1350));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PredictionsRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */