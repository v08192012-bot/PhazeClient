/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_9779;
/*     */ import vorga.phazeclient.api.system.render.Render3DUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FTHelperRenderer
/*     */ {
/*     */   private static final float FILL_ALPHA_SCALE = 0.18F;
/*     */   private static final double TRAPKA_BLOCK_INSET = 0.01D;
/*     */   private static final float REFERENCE_THICKNESS_RADIUS = 10.0F;
/*     */   
/*     */   private static float scaledThickness(FTHelper module, float radius) {
/*  62 */     return module.circleThickness.getValue() * radius / 10.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void render(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter) {
/*  70 */     FTHelper module = FTHelper.getInstance();
/*  71 */     if (module == null || !module.isEnabled()) {
/*     */       return;
/*     */     }
/*  74 */     FTHelper.HighlightType type = module.getHighlightType();
/*  75 */     if (type == FTHelper.HighlightType.NONE) {
/*     */       return;
/*     */     }
/*  78 */     class_310 mc = class_310.method_1551();
/*  79 */     if (mc == null || mc.field_1724 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     switch (type) { case field_11036:
/*  84 */         drawTrapkaBox(matrices, cameraPos, tickCounter, 1); break;
/*  85 */       case field_11033: drawTrapkaBox(matrices, cameraPos, tickCounter, 3); break;
/*  86 */       case field_11043: drawCircleAtFeet(matrices, cameraPos, tickCounter, 10.0F); break;
/*  87 */       case field_11035: drawCircleAtFeet(matrices, cameraPos, tickCounter, 2.0F); break;
/*  88 */       case null: drawSnezhokPrediction(matrices, cameraPos, tickCounter); break;
/*  89 */       case null: drawPlastBox(matrices, cameraPos);
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   private static class_243 lerpedPlayerPos(class_310 mc, class_9779 tickCounter) {
/*  95 */     class_746 player = mc.field_1724;
/*  96 */     float td = tickCounter.method_60637(false);
/*  97 */     return new class_243(
/*  98 */         class_3532.method_16436(td, player.field_6014, player.method_23317()), 
/*  99 */         class_3532.method_16436(td, player.field_6036, player.method_23318()), 
/* 100 */         class_3532.method_16436(td, player.field_5969, player.method_23321()));
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
/*     */   private static void drawTrapkaBox(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter, int halfExtent) {
/* 117 */     class_310 mc = class_310.method_1551();
/* 118 */     class_746 player = mc.field_1724;
/* 119 */     if (player == null)
/* 120 */       return;  FTHelper module = FTHelper.getInstance();
/* 121 */     int color = module.resolveBoxColor();
/*     */ 
/*     */     
/* 124 */     class_2338 block = class_2338.method_49637(player.method_23317(), player.method_23318(), player.method_23321());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     Render3DUtil.drawBox(matrices, 
/* 134 */         (float)((block.method_10263() - halfExtent) + 0.01D - cameraPos.field_1352), 
/* 135 */         (float)(block.method_10264() + 0.01D - cameraPos.field_1351), 
/* 136 */         (float)((block.method_10260() - halfExtent) + 0.01D - cameraPos.field_1350), 
/* 137 */         (float)((block.method_10263() + halfExtent + 1) - 0.01D - cameraPos.field_1352), 
/* 138 */         (float)((block.method_10264() + 3) - 0.01D - cameraPos.field_1351), 
/* 139 */         (float)((block.method_10260() + halfExtent + 1) - 0.01D - cameraPos.field_1350), color, 0.18F, 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void drawCircleAtFeet(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter, float radius) {
/* 144 */     class_310 mc = class_310.method_1551();
/* 145 */     FTHelper module = FTHelper.getInstance();
/* 146 */     int color = module.resolveCircleColor();
/* 147 */     class_243 p = lerpedPlayerPos(mc, tickCounter);
/*     */ 
/*     */ 
/*     */     
/* 151 */     float thickness = scaledThickness(module, radius);
/* 152 */     float ringY = (float)(p.field_1351 + 0.05D);
/* 153 */     matrices.method_22903();
/* 154 */     matrices.method_22904(-cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (module.circleGlow.isValue()) {
/* 161 */       float strength = module.circleGlowStrength.getValue();
/* 162 */       int centerAlpha = Math.max(0, Math.min(255, Math.round(80.0F * Math.min(2.0F, strength))));
/* 163 */       int centerColor = centerAlpha << 24 | color & 0xFFFFFF;
/* 164 */       int rimColor = color & 0xFFFFFF;
/*     */ 
/*     */ 
/*     */       
/* 168 */       Render3DUtil.drawFlatGlowDisc(matrices, (float)p.field_1352, ringY, (float)p.field_1350, radius * (1.0F + 0.2F * 
/* 169 */           Math.min(2.0F, strength)), centerColor, rimColor, 96, true);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     Render3DUtil.drawThickFlatRing(matrices, (float)p.field_1352, ringY, (float)p.field_1350, radius, thickness, color, 96, true);
/*     */     
/* 180 */     matrices.method_22909();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawSnezhokPrediction(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter) {
/* 191 */     class_310 mc = class_310.method_1551();
/* 192 */     if (mc.field_1687 == null)
/* 193 */       return;  FTHelper module = FTHelper.getInstance();
/* 194 */     class_746 class_746 = mc.field_1724;
/* 195 */     float tickDelta = tickCounter.method_60637(false);
/*     */ 
/*     */ 
/*     */     
/* 199 */     class_243 eye = class_746.method_5836(tickDelta);
/* 200 */     class_243 look = class_746.method_5828(tickDelta);
/* 201 */     class_243 hit = resolveSnezhokHit(mc, (class_1657)class_746, eye, look);
/* 202 */     if (hit == null)
/*     */       return; 
/* 204 */     int color = module.resolveCircleColor();
/*     */ 
/*     */ 
/*     */     
/* 208 */     float radius = 3.5F;
/* 209 */     float thickness = scaledThickness(module, radius);
/* 210 */     float ringX = (float)(hit.field_1352 - cameraPos.field_1352);
/* 211 */     float ringY = (float)(hit.field_1351 - cameraPos.field_1351 + 0.05000000074505806D);
/* 212 */     float ringZ = (float)(hit.field_1350 - cameraPos.field_1350);
/* 213 */     if (module.circleGlow.isValue()) {
/* 214 */       float strength = module.circleGlowStrength.getValue();
/* 215 */       int centerAlpha = Math.max(0, Math.min(255, Math.round(80.0F * Math.min(2.0F, strength))));
/* 216 */       int centerColor = centerAlpha << 24 | color & 0xFFFFFF;
/* 217 */       int rimColor = color & 0xFFFFFF;
/* 218 */       Render3DUtil.drawFlatGlowDisc(matrices, ringX, ringY, ringZ, radius * (1.0F + 0.2F * 
/* 219 */           Math.min(2.0F, strength)), centerColor, rimColor, 64, true);
/*     */     } 
/*     */     
/* 222 */     Render3DUtil.drawThickFlatRing(matrices, ringX, ringY, ringZ, radius, thickness, color, 64, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class_243 resolveSnezhokHit(class_310 mc, class_1657 player, class_243 eye, class_243 look) {
/* 227 */     class_243 motion = look.method_1021(1.5D);
/* 228 */     class_243 pos = eye;
/* 229 */     class_243 hit = null;
/* 230 */     for (int i = 0; i < 200; i++) {
/* 231 */       class_243 next = pos.method_1019(motion);
/* 232 */       class_3965 class_3965 = mc.field_1687.method_17742(new class_3959(pos, next, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)player));
/*     */ 
/*     */       
/* 235 */       if (class_3965.method_17783() != class_239.class_240.field_1333) {
/* 236 */         hit = class_3965.method_17784();
/*     */         break;
/*     */       } 
/* 239 */       motion = motion.method_1021(0.99D).method_1031(0.0D, -0.03D, 0.0D);
/* 240 */       pos = next;
/* 241 */       if (next.field_1351 < -64.0D || next.method_1022(eye) > 100.0D)
/*     */         break; 
/* 243 */     }  return hit;
/*     */   }
/*     */   private static void drawPlastBox(class_4587 matrices, class_243 cameraPos) {
/*     */     double x1, y1, z1, x2, y2, z2;
/* 247 */     FTHelper module = FTHelper.getInstance();
/* 248 */     class_2338 target = module.getTargetBlockPos();
/* 249 */     class_2350 side = module.getTargetBlockSide();
/* 250 */     if (target == null || side == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     switch (side) {
/*     */       case field_11036:
/* 259 */         x1 = (target.method_10263() - 2);
/* 260 */         y1 = (target.method_10264() + 1);
/* 261 */         z1 = (target.method_10260() - 2);
/* 262 */         x2 = (target.method_10263() + 3);
/* 263 */         y2 = (target.method_10264() + 3);
/* 264 */         z2 = (target.method_10260() + 3);
/*     */         break;
/*     */       case field_11033:
/* 267 */         x1 = (target.method_10263() - 2);
/* 268 */         y1 = (target.method_10264() - 1);
/* 269 */         z1 = (target.method_10260() - 2);
/* 270 */         x2 = (target.method_10263() + 3);
/* 271 */         y2 = (target.method_10264() + 1);
/* 272 */         z2 = (target.method_10260() + 3); break;
/*     */       case field_11043:
/*     */       case field_11035:
/* 275 */         x1 = (target.method_10263() - 2);
/* 276 */         y1 = (target.method_10264() - 2);
/* 277 */         z1 = (side == class_2350.field_11035) ? (target.method_10260() + 1) : (target.method_10260() - 1);
/* 278 */         x2 = (target.method_10263() + 3);
/* 279 */         y2 = (target.method_10264() + 3);
/* 280 */         z2 = (side == class_2350.field_11035) ? (target.method_10260() + 3) : (target.method_10260() + 1);
/*     */         break;
/*     */       default:
/* 283 */         x1 = (side == class_2350.field_11034) ? (target.method_10263() + 1) : (target.method_10263() - 1);
/* 284 */         y1 = (target.method_10264() - 2);
/* 285 */         z1 = (target.method_10260() - 2);
/* 286 */         x2 = (side == class_2350.field_11034) ? (target.method_10263() + 3) : (target.method_10263() + 1);
/* 287 */         y2 = (target.method_10264() + 3);
/* 288 */         z2 = (target.method_10260() + 3);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 293 */     Render3DUtil.drawBox(matrices, (float)(x1 - cameraPos.field_1352), (float)(y1 - cameraPos.field_1351), (float)(z1 - cameraPos.field_1350), (float)(x2 - cameraPos.field_1352), (float)(y2 - cameraPos.field_1351), (float)(z2 - cameraPos.field_1350), module
/*     */ 
/*     */         
/* 296 */         .resolveBoxColor(), 0.18F, 2.0F);
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
/*     */   public static void renderSnowballs(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter) {
/* 308 */     FTHelper module = FTHelper.getInstance();
/* 309 */     if (module == null || !module.isEnabled() || !module.snezhokZamorozkaEnabled.isValue())
/* 310 */       return;  List<FTHelper.TrackedSnowball> list = module.getTrackedSnowballs();
/* 311 */     if (list.isEmpty())
/* 312 */       return;  int color = module.resolveCircleColor();
/*     */ 
/*     */     
/* 315 */     float thickness = scaledThickness(module, 3.5F);
/* 316 */     float tickDelta = tickCounter.method_60637(false);
/* 317 */     for (FTHelper.TrackedSnowball t : list) {
/* 318 */       class_243 p = t.getRenderPosition(tickDelta);
/* 319 */       float ringX = (float)(p.field_1352 - cameraPos.field_1352);
/* 320 */       float ringY = (float)(p.field_1351 - cameraPos.field_1351 + 0.05000000074505806D);
/* 321 */       float ringZ = (float)(p.field_1350 - cameraPos.field_1350);
/* 322 */       if (module.circleGlow.isValue()) {
/* 323 */         float strength = module.circleGlowStrength.getValue();
/* 324 */         int centerAlpha = Math.max(0, Math.min(255, Math.round(80.0F * Math.min(2.0F, strength))));
/* 325 */         int centerColor = centerAlpha << 24 | color & 0xFFFFFF;
/* 326 */         int rimColor = color & 0xFFFFFF;
/* 327 */         Render3DUtil.drawFlatGlowDisc(matrices, ringX, ringY, ringZ, 3.5F * (1.0F + 0.2F * 
/*     */             
/* 329 */             Math.min(2.0F, strength)), centerColor, rimColor, 64, true);
/*     */       } 
/*     */       
/* 332 */       Render3DUtil.drawThickFlatRing(matrices, ringX, ringY, ringZ, 3.5F, thickness, color, 64, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 338 */       List<class_243> trail = t.getTrail();
/* 339 */       if (trail != null && trail.size() >= 2) {
/* 340 */         Render3DUtil.drawPolylineOffset(matrices, trail, -cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350, color, 2.0F, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderHighlight(class_4587 matrices, class_243 cameraPos, class_9779 tickCounter) {
/* 349 */     render(matrices, cameraPos, tickCounter);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\FTHelperRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */