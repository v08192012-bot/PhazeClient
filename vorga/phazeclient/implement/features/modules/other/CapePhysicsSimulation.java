/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CapePhysicsSimulation
/*     */ {
/*     */   public static final int PART_COUNT = 16;
/*  12 */   private final float[] x = new float[16];
/*  13 */   private final float[] y = new float[16];
/*  14 */   private final float[] z = new float[16];
/*     */   
/*  16 */   private final float[] prevX = new float[16];
/*  17 */   private final float[] prevY = new float[16];
/*  18 */   private final float[] prevZ = new float[16];
/*     */   
/*     */   private boolean initialized = false;
/*  21 */   private long lastUpdateTick = Long.MIN_VALUE;
/*     */   private boolean sneaking = false;
/*     */   private float dirX;
/*     */   
/*     */   public CapePhysicsSimulation() {
/*  26 */     reset();
/*     */   }
/*     */   private float dirY; private float dirZ;
/*     */   public void reset() {
/*  30 */     for (int i = 0; i < 16; i++) {
/*     */ 
/*     */ 
/*     */       
/*  34 */       this.x[i] = 0.0F;
/*  35 */       this.y[i] = -i;
/*  36 */       this.z[i] = 0.0F;
/*  37 */       this.prevX[i] = this.x[i];
/*  38 */       this.prevY[i] = this.y[i];
/*  39 */       this.prevZ[i] = this.z[i];
/*     */     } 
/*  41 */     this.initialized = false;
/*  42 */     this.lastUpdateTick = Long.MIN_VALUE;
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
/*     */   public void updateForTick(long tick, float forwardDelta, float verticalDelta, float strafeDelta, float gravityStrength, float heightMultiplier, float strafeMultiplier, boolean isSneaking, boolean isUnderwater) {
/*  62 */     if (tick == this.lastUpdateTick)
/*  63 */       return;  update(forwardDelta, verticalDelta, strafeDelta, gravityStrength, heightMultiplier, strafeMultiplier, isSneaking, isUnderwater);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.lastUpdateTick = tick;
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
/*     */   public void update(float forwardDelta, float verticalDelta, float strafeDelta, float gravityStrength, float heightMultiplier, float strafeMultiplier, boolean isSneaking, boolean isUnderwater) {
/*  86 */     if (!this.initialized) {
/*  87 */       reset();
/*  88 */       this.initialized = true;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  93 */     System.arraycopy(this.x, 0, this.prevX, 0, 16);
/*  94 */     System.arraycopy(this.y, 0, this.prevY, 0, 16);
/*  95 */     System.arraycopy(this.z, 0, this.prevZ, 0, 16);
/*     */ 
/*     */     
/*  98 */     double changeX = forwardDelta + ((isSneaking && !this.sneaking) ? 3.0D : 0.0D);
/*  99 */     double changeY = (verticalDelta * heightMultiplier) + ((isSneaking && !this.sneaking) ? 1.0D : 0.0D);
/* 100 */     double changeZ = (-strafeDelta * strafeMultiplier);
/* 101 */     this.sneaking = isSneaking;
/*     */     
/* 103 */     this.x[0] = this.x[0] + (float)changeX;
/* 104 */     this.y[0] = this.y[0] + (float)changeY;
/* 105 */     this.z[0] = this.z[0] + (float)changeZ;
/*     */ 
/*     */     
/* 108 */     float fall = (isUnderwater ? (gravityStrength / 10.0F) : gravityStrength) * 0.05F; int i;
/* 109 */     for (i = 1; i < 16; i++) {
/* 110 */       this.y[i] = this.y[i] - fall;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     preventHardBends();
/* 117 */     preventSelfClipping(32);
/*     */ 
/*     */     
/* 120 */     for (i = 1; i < 16; i++) {
/* 121 */       if (this.x[i] - this.x[0] > 0.0F) {
/* 122 */         this.x[i] = this.x[0];
/*     */       }
/* 124 */       float fraction = i / 16.0F;
/* 125 */       float maxZ = fraction * fraction * 5.0F;
/* 126 */       float offset = this.z[0] - this.z[i];
/* 127 */       if (offset > maxZ) {
/* 128 */         this.z[i] = this.z[0] - maxZ;
/*     */       }
/* 130 */       if (offset < -maxZ) {
/* 131 */         this.z[i] = this.z[0] + maxZ;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 136 */     for (int iter = 0; iter < 3; iter++) {
/* 137 */       for (int j = 14; j >= 0; j--) {
/* 138 */         direction(j, j + 1);
/* 139 */         separate(j, j + 1);
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     preventSelfClipping(32);
/*     */ 
/*     */     
/* 146 */     for (int a = 0; a < 15; a++) {
/* 147 */       direction(a, a + 1);
/* 148 */       this.x[a + 1] = this.x[a] - this.dirX;
/* 149 */       this.y[a + 1] = this.y[a] - this.dirY;
/* 150 */       this.z[a + 1] = this.z[a] - this.dirZ;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void preventHardBends() {
/* 155 */     double maxBend = 20.0D;
/* 156 */     for (int i = 14; i >= 1; i--) {
/* 157 */       double first = Math.atan2((this.y[i + 1] - this.y[i]), (this.x[i + 1] - this.x[i]));
/* 158 */       double second = Math.atan2((this.y[i - 1] - this.y[i]), (this.x[i - 1] - this.x[i]));
/* 159 */       double angle = Math.toDegrees(first - second);
/* 160 */       for (; angle > 360.0D; angle -= 360.0D);
/* 161 */       for (; angle < -360.0D; angle += 360.0D);
/* 162 */       double abs = Math.abs(angle);
/* 163 */       if (abs < 160.0D) {
/* 164 */         replaceBend(i, angle, 161.0D);
/* 165 */       } else if (abs > 200.0D) {
/* 166 */         replaceBend(i, angle, 199.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void preventSelfClipping(int maxRuns) {
/*     */     boolean clipped;
/* 172 */     int runs = 0;
/*     */     
/*     */     do {
/* 175 */       clipped = false;
/* 176 */       for (int a = 0; a < 16; a++) {
/* 177 */         for (int b = a + 1; b < 16; b++) {
/* 178 */           float dx = this.x[a] - this.x[b];
/* 179 */           float dy = this.y[a] - this.y[b];
/* 180 */           float dz = this.z[a] - this.z[b];
/* 181 */           if (dx * dx + dy * dy + dz * dz < 0.99F)
/* 182 */           { clipped = true;
/* 183 */             direction(a, b);
/* 184 */             separate(a, b);
/* 185 */             if (++runs >= maxRuns)
/*     */               return;  } 
/*     */         } 
/*     */       } 
/* 189 */     } while (clipped);
/*     */   }
/*     */   
/*     */   private void replaceBend(int i, double angle, double target) {
/* 193 */     double theta = Math.toRadians(target) * ((angle < 0.0D) ? -1.0D : 1.0D);
/* 194 */     float ox = this.x[i - 1] - this.x[i];
/* 195 */     float oy = this.y[i - 1] - this.y[i];
/* 196 */     float cos = (float)Math.cos(theta);
/* 197 */     float sin = (float)Math.sin(theta);
/* 198 */     this.x[i + 1] = cos * ox - sin * oy + this.x[i];
/* 199 */     this.y[i + 1] = sin * ox + cos * oy + this.y[i];
/*     */   }
/*     */   
/*     */   private void direction(int a, int b) {
/* 203 */     this.dirX = this.x[a] - this.x[b];
/* 204 */     this.dirY = this.y[a] - this.y[b];
/* 205 */     this.dirZ = this.z[a] - this.z[b];
/* 206 */     float len = (float)Math.sqrt((this.dirX * this.dirX + this.dirY * this.dirY + this.dirZ * this.dirZ));
/* 207 */     if (len < 1.0E-4F) {
/* 208 */       this.dirX = this.dirY = this.dirZ = 0.0F;
/*     */     } else {
/* 210 */       this.dirX /= len;
/* 211 */       this.dirY /= len;
/* 212 */       this.dirZ /= len;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void separate(int a, int b) {
/* 217 */     float cx = (this.x[a] + this.x[b]) / 2.0F;
/* 218 */     float cy = (this.y[a] + this.y[b]) / 2.0F;
/* 219 */     float cz = (this.z[a] + this.z[b]) / 2.0F;
/* 220 */     if (a != 0) {
/* 221 */       this.x[a] = cx + this.dirX / 2.0F;
/* 222 */       this.y[a] = cy + this.dirY / 2.0F;
/* 223 */       this.z[a] = cz + this.dirZ / 2.0F;
/*     */     } 
/* 225 */     this.x[b] = cx - this.dirX / 2.0F;
/* 226 */     this.y[b] = cy - this.dirY / 2.0F;
/* 227 */     this.z[b] = cz - this.dirZ / 2.0F;
/*     */   }
/*     */   
/*     */   public float getLerpX(int part, float delta) {
/* 231 */     if (part < 0 || part >= 16) return 0.0F; 
/* 232 */     return class_3532.method_16439(delta, this.prevX[part], this.x[part]);
/*     */   }
/*     */   
/*     */   public float getLerpY(int part, float delta) {
/* 236 */     if (part < 0 || part >= 16) return 0.0F; 
/* 237 */     return class_3532.method_16439(delta, this.prevY[part], this.y[part]);
/*     */   }
/*     */   
/*     */   public float getLerpZ(int part, float delta) {
/* 241 */     if (part < 0 || part >= 16) return 0.0F; 
/* 242 */     return class_3532.method_16439(delta, this.prevZ[part], this.z[part]);
/*     */   }
/*     */   
/*     */   public float getSegmentRotation(int part, float delta) {
/* 246 */     if (part >= 15) {
/* 247 */       return getSegmentRotation(14, delta);
/*     */     }
/* 249 */     float dx = getLerpX(part + 1, delta) - getLerpX(part, delta);
/* 250 */     float dy = getLerpY(part + 1, delta) - getLerpY(part, delta);
/* 251 */     return (float)Math.toDegrees(Math.atan2(dx, dy)) + 180.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\CapePhysicsSimulation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */