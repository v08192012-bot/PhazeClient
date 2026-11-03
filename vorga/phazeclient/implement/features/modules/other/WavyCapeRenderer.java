/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4608;
/*     */ import net.minecraft.class_7833;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WavyCapeRenderer
/*     */ {
/*     */   public static final int PART_COUNT = 16;
/*     */   private static final float CAPE_WIDTH = 0.625F;
/*     */   private static final float CAPE_HEIGHT = 1.0F;
/*     */   private static final float CAPE_DEPTH = 0.0625F;
/*     */   private static final float PHAZE_CAPE_DEPTH = 0.1875F;
/*  31 */   private static final Matrix4f[] POSITION_MATRICES = new Matrix4f[16];
/*  32 */   private static final Vector4f V_A = new Vector4f();
/*  33 */   private static final Vector4f V_B = new Vector4f();
/*  34 */   private static final Vector4f V_C = new Vector4f();
/*     */   
/*  36 */   private static final Int2ObjectOpenHashMap<CapePhysicsSimulation> SIMULATIONS = new Int2ObjectOpenHashMap();
/*  37 */   private static long lastPurge = 0L;
/*     */   
/*     */   static {
/*  40 */     for (int i = 0; i < 16; i++) {
/*  41 */       POSITION_MATRICES[i] = new Matrix4f();
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
/*     */   public static void renderWavyCape(class_4587 matrices, class_4597 vertexConsumers, class_2960 texture, int light, class_10055 state) {
/*  54 */     renderWavyCape(matrices, vertexConsumers, texture, light, state, false);
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
/*     */   public static void renderWavyCape(class_4587 matrices, class_4597 vertexConsumers, class_2960 texture, int light, class_10055 state, boolean phazeCape) {
/*  71 */     WaveCapes module = WaveCapes.getInstance();
/*  72 */     if (module == null || !module.isEnabled())
/*  73 */       return;  if (state.field_53333 || !state.field_53532)
/*     */       return; 
/*  75 */     class_310 client = class_310.method_1551();
/*  76 */     if (client.field_1687 == null)
/*     */       return; 
/*  78 */     long now = System.currentTimeMillis();
/*  79 */     if (now - lastPurge > 10000L) {
/*  80 */       lastPurge = now;
/*  81 */       if (SIMULATIONS.size() > 64) {
/*  82 */         SIMULATIONS.clear();
/*     */       }
/*     */     } 
/*     */     
/*  86 */     class_1297 entity = client.field_1687.method_8469(state.field_53528);
/*  87 */     boolean underwater = (entity != null && entity.method_5869());
/*  88 */     float delta = client.method_61966().method_60637(false);
/*     */ 
/*     */ 
/*     */     
/*  92 */     CapePhysicsSimulation sim = (CapePhysicsSimulation)SIMULATIONS.computeIfAbsent(state.field_53528, id -> new CapePhysicsSimulation());
/*     */     
/*  94 */     float forwardDelta = 0.0F;
/*  95 */     float verticalDelta = 0.0F;
/*  96 */     float strafeDelta = 0.0F;
/*     */     
/*  98 */     if (entity != null) {
/*  99 */       float yawRad = (float)Math.toRadians(-entity.method_36454());
/* 100 */       float dx = (float)(entity.method_23317() - entity.field_6038);
/* 101 */       float dy = (float)(entity.method_23318() - entity.field_5971);
/* 102 */       float dz = (float)(entity.method_23321() - entity.field_5989);
/* 103 */       strafeDelta = class_3532.method_15362(yawRad) * dx - class_3532.method_15374(yawRad) * dz;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       verticalDelta = (dy < 0.0F) ? (dy * 0.2F) : dy;
/* 109 */       forwardDelta = (float)Math.sqrt((dx * dx + dz * dz)) * 10.0F;
/*     */     } 
/*     */     
/* 112 */     long simulationTick = (entity != null) ? entity.field_6012 : client.field_1687.method_8510();
/* 113 */     sim.updateForTick(simulationTick, forwardDelta, verticalDelta, strafeDelta, module.gravity
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         .getValue(), module.heightMultiplier
/* 119 */         .getValue(), module.strafeMultiplier
/* 120 */         .getValue(), state.field_53334, underwater);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     for (int part = 0; part < 16; part++) {
/* 127 */       matrices.method_22903();
/* 128 */       matrices.method_22904(0.0D, 0.0D, 0.125D);
/*     */       
/* 130 */       float firstX = sim.getLerpX(0, delta);
/* 131 */       float currX = sim.getLerpX(part, delta);
/* 132 */       float x = currX - firstX;
/* 133 */       if (x > 0.0F) x = 0.0F; 
/* 134 */       float y = sim.getLerpY(0, delta) - part - sim.getLerpY(part, delta);
/* 135 */       float z = sim.getLerpZ(0, delta) - sim.getLerpZ(part, delta);
/* 136 */       float partRotation = sim.getSegmentRotation(part, delta);
/*     */       
/* 138 */       float naturalWind = calculateNaturalWind(part, underwater, module);
/*     */       
/* 140 */       matrices.method_22907(class_7833.field_40714.rotationDegrees(6.0F + naturalWind));
/* 141 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(180.0F));
/*     */       
/* 143 */       matrices.method_46416(-z / 16.0F, y / 16.0F, x / 16.0F);
/* 144 */       matrices.method_46416(0.0F, 0.03F, -0.03F);
/* 145 */       matrices.method_46416(0.0F, part * 1.0F / 16.0F, 0.0F);
/* 146 */       matrices.method_22907(class_7833.field_40714.rotationDegrees(-partRotation));
/* 147 */       matrices.method_46416(0.0F, -part * 1.0F / 16.0F, 0.0F);
/* 148 */       matrices.method_46416(0.0F, -0.03F, 0.03F);
/*     */       
/* 150 */       POSITION_MATRICES[part].set((Matrix4fc)matrices.method_23760().method_23761());
/* 151 */       matrices.method_22909();
/*     */     } 
/*     */     
/* 154 */     class_4588 consumer = vertexConsumers.getBuffer(class_1921.method_23578(texture));
/* 155 */     drawSmoothMesh(consumer, light, phazeCape ? 0.1875F : 0.0625F, phazeCape);
/*     */   }
/*     */   
/*     */   private static void drawSmoothMesh(class_4588 consumer, int light, float capeDepth, boolean phazeCape) {
/* 159 */     float texelU = 0.015625F;
/* 160 */     float texelV = 0.03125F;
/* 161 */     float halfW = 0.3125F;
/*     */     
/* 163 */     for (int part = 0; part < 16; part++) {
/* 164 */       int prev = Math.max(part - 1, 0);
/* 165 */       Matrix4f matPrev = POSITION_MATRICES[prev];
/* 166 */       Matrix4f matCurr = POSITION_MATRICES[part];
/*     */       
/* 168 */       float yTop = part * 0.0625F;
/* 169 */       float yBot = (part + 1) * 0.0625F;
/*     */ 
/*     */       
/* 172 */       if (part == 0) {
/* 173 */         float f1 = 1.0F * texelU;
/* 174 */         float f2 = 11.0F * texelU;
/* 175 */         float v0 = 0.0F;
/* 176 */         float v1 = texelV;
/* 177 */         drawQuad(consumer, light, matPrev, halfW, 0.0F, 0.0F, f2, v1, matPrev, -halfW, 0.0F, 0.0F, f1, v1, matPrev, -halfW, 0.0F, -capeDepth, f1, v0, matPrev, halfW, 0.0F, -capeDepth, f2, v0, false);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       if (part == 15) {
/* 187 */         float f1 = 11.0F * texelU;
/* 188 */         float f2 = 21.0F * texelU;
/* 189 */         float v0 = 0.0F;
/* 190 */         float v1 = texelV;
/* 191 */         drawQuad(consumer, light, matCurr, halfW, 1.0F, -capeDepth, f2, v0, matCurr, -halfW, 1.0F, -capeDepth, f1, v0, matCurr, -halfW, 1.0F, 0.0F, f1, v1, matCurr, halfW, 1.0F, 0.0F, f2, v1, false);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 200 */       float minU = 0.0F;
/* 201 */       float maxU = 1.0F * texelU;
/* 202 */       float minV = texelV * (part + 1);
/* 203 */       float maxV = minV + texelV;
/* 204 */       drawQuad(consumer, light, matCurr, -halfW, yBot, 0.0F, minU, maxV, matCurr, -halfW, yBot, -capeDepth, maxU, maxV, matPrev, -halfW, yTop, -capeDepth, maxU, minV, matPrev, -halfW, yTop, 0.0F, minU, minV, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       minU = 11.0F * texelU;
/* 213 */       maxU = 12.0F * texelU;
/* 214 */       drawQuad(consumer, light, matCurr, halfW, yBot, -capeDepth, minU, maxV, matCurr, halfW, yBot, 0.0F, maxU, maxV, matPrev, halfW, yTop, 0.0F, maxU, minV, matPrev, halfW, yTop, -capeDepth, minU, minV, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       minU = 1.0F * texelU;
/* 225 */       maxU = 11.0F * texelU;
/* 226 */       drawQuad(consumer, light, matPrev, halfW, yTop, -capeDepth, maxU, minV, matPrev, -halfW, yTop, -capeDepth, minU, minV, matCurr, -halfW, yBot, -capeDepth, minU, maxV, matCurr, halfW, yBot, -capeDepth, maxU, maxV, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 234 */       minU = 12.0F * texelU;
/* 235 */       maxU = 22.0F * texelU;
/* 236 */       drawQuad(consumer, light, matPrev, halfW, yTop, 0.0F, minU, minV, matPrev, -halfW, yTop, 0.0F, maxU, minV, matCurr, -halfW, yBot, 0.0F, maxU, maxV, matCurr, halfW, yBot, 0.0F, minU, maxV, true);
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
/*     */   private static void drawQuad(class_4588 consumer, int light, Matrix4f matA, float ax, float ay, float az, float au, float av, Matrix4f matB, float bx, float by, float bz, float bu, float bv, Matrix4f matC, float cx, float cy, float cz, float cu, float cv, Matrix4f matD, float dx, float dy, float dz, float du, float dv, boolean invertNormal) {
/* 254 */     matA.transform(ax, ay, az, 1.0F, V_A);
/* 255 */     matB.transform(bx, by, bz, 1.0F, V_B);
/* 256 */     matC.transform(cx, cy, cz, 1.0F, V_C);
/*     */     
/* 258 */     float uX = V_B.x - V_A.x;
/* 259 */     float uY = V_B.y - V_A.y;
/* 260 */     float uZ = V_B.z - V_A.z;
/*     */     
/* 262 */     float wX = V_C.x - V_A.x;
/* 263 */     float wY = V_C.y - V_A.y;
/* 264 */     float wZ = V_C.z - V_A.z;
/*     */     
/* 266 */     float nx = uY * wZ - uZ * wY;
/* 267 */     float ny = uZ * wX - uX * wZ;
/* 268 */     float nz = uX * wY - uY * wX;
/* 269 */     float len = (float)Math.sqrt((nx * nx + ny * ny + nz * nz));
/* 270 */     if (len > 1.0E-4F) {
/* 271 */       nx /= len;
/* 272 */       ny /= len;
/* 273 */       nz /= len;
/*     */     } else {
/* 275 */       nx = 0.0F;
/* 276 */       ny = 0.0F;
/* 277 */       nz = 1.0F;
/*     */     } 
/*     */     
/* 280 */     if (invertNormal) {
/* 281 */       nx = -nx;
/* 282 */       ny = -ny;
/* 283 */       nz = -nz;
/*     */     } 
/*     */     
/* 286 */     consumer.method_22918(matA, ax, ay, az).method_39415(-1).method_22913(au, av).method_22922(class_4608.field_21444).method_60803(light).method_22914(nx, ny, nz);
/* 287 */     consumer.method_22918(matB, bx, by, bz).method_39415(-1).method_22913(bu, bv).method_22922(class_4608.field_21444).method_60803(light).method_22914(nx, ny, nz);
/* 288 */     consumer.method_22918(matC, cx, cy, cz).method_39415(-1).method_22913(cu, cv).method_22922(class_4608.field_21444).method_60803(light).method_22914(nx, ny, nz);
/* 289 */     consumer.method_22918(matD, dx, dy, dz).method_39415(-1).method_22913(du, dv).method_22922(class_4608.field_21444).method_60803(light).method_22914(nx, ny, nz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float calculateNaturalWind(int part, boolean underwater, WaveCapes module) {
/* 297 */     if (!module.windEffect.isValue()) {
/* 298 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 302 */     double speed = 1.0D;
/* 303 */     double baseCycleMs = underwater ? 3240.0D : 1080.0D;
/* 304 */     double cycleMs = baseCycleMs / speed;
/*     */     
/* 306 */     long now = System.currentTimeMillis();
/*     */     
/* 308 */     double progress = now % cycleMs / cycleMs;
/*     */     
/* 310 */     float partFraction = (part + 1) / 16.0F;
/*     */ 
/*     */     
/* 313 */     double phase = partFraction * 2.0D * Math.PI - progress * 2.0D * Math.PI;
/*     */ 
/*     */     
/* 316 */     double wave = Math.sin(phase) + 0.15D * Math.sin(2.0D * phase + 0.4D);
/*     */ 
/*     */     
/* 319 */     float windEase = 0.2F + 0.8F * (float)Math.sin(partFraction * Math.PI / 2.0D);
/*     */     
/* 321 */     return (float)(wave * 3.0D * windEase);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\WavyCapeRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */