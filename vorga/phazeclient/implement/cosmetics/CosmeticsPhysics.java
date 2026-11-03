/*     */ package vorga.phazeclient.implement.cosmetics;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CosmeticsPhysics
/*     */ {
/*  16 */   private static final CosmeticsPhysics INSTANCE = new CosmeticsPhysics();
/*  17 */   private static final long PREVIEW_EPOCH_NANOS = System.nanoTime();
/*     */ 
/*     */   
/*  20 */   private final Map<Integer, Simulation> simulations = new HashMap<>();
/*     */   
/*  22 */   private final Map<Integer, CompanionSimulation> companionSimulations = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static CosmeticsPhysics getInstance() {
/*  28 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   Pose sample(class_1657 player, class_10055 state, boolean preview, float idleAmplitude) {
/*  33 */     if (preview) {
/*     */ 
/*     */ 
/*     */       
/*  37 */       double time = (System.nanoTime() - PREVIEW_EPOCH_NANOS) / 1.0E9D;
/*  38 */       return new Pose(0.0F, 0.0F, 
/*     */           
/*  40 */           (float)Math.sin(time * 1.7D) * idleAmplitude * 0.38F, 0.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  45 */     long now = System.nanoTime();
/*  46 */     if (this.simulations.size() > 256) {
/*  47 */       this.simulations.clear();
/*     */     }
/*  49 */     Simulation simulation = this.simulations.computeIfAbsent(
/*  50 */         Integer.valueOf(player.method_5628()), ignored -> new Simulation(state.field_53446, now));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     float dt = (simulation.lastUpdateNanos == 0L) ? 0.016666668F : Math.min(0.05F, Math.max(0.001F, (float)(now - simulation.lastUpdateNanos) / 1.0E9F));
/*     */     
/*  58 */     simulation.lastUpdateNanos = now;
/*     */     
/*  60 */     class_243 velocity = player.method_18798();
/*  61 */     float horizontalSpeed = (float)Math.sqrt(velocity.field_1352 * velocity.field_1352 + velocity.field_1350 * velocity.field_1350);
/*     */ 
/*     */     
/*  64 */     float rawMovement = class_3532.method_15363(horizontalSpeed / 0.28F, 0.0F, 1.65F);
/*  65 */     float sprint = player.method_5624() ? 1.0F : 0.0F;
/*     */ 
/*     */ 
/*     */     
/*  69 */     float yaw = state.field_53446;
/*  70 */     float yawDelta = class_3532.method_15393(yaw - simulation.lastYaw);
/*  71 */     simulation.lastYaw = yaw;
/*  72 */     float rawTurnLag = class_3532.method_15363(-yawDelta * 0.82F, -13.0F, 13.0F);
/*     */ 
/*     */ 
/*     */     
/*  76 */     simulation.movementInput.update(rawMovement, dt, 58.0F, 13.0F);
/*  77 */     simulation.verticalInput.update((float)velocity.field_1351, dt, 62.0F, 14.0F);
/*  78 */     simulation.turnInput.update(rawTurnLag, dt, 74.0F, 15.0F);
/*  79 */     float movement = simulation.movementInput.value;
/*  80 */     float vertical = simulation.verticalInput.value;
/*  81 */     float turnLag = simulation.turnInput.value;
/*     */     
/*  83 */     float airbornePitch = 0.0F;
/*  84 */     float airborneFlex = 0.0F;
/*  85 */     if (!player.method_24828()) {
/*  86 */       if (vertical > 0.0F) {
/*  87 */         airbornePitch = -class_3532.method_15363(vertical * 22.0F, 2.0F, 10.0F);
/*  88 */         airborneFlex = -class_3532.method_15363(vertical * 13.0F, 1.0F, 6.0F);
/*     */       } else {
/*  90 */         airbornePitch = class_3532.method_15363(-vertical * 18.0F, 2.0F, 11.0F);
/*  91 */         airborneFlex = class_3532.method_15363(-vertical * 12.0F, 1.0F, 7.0F);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  96 */     float age = state.field_53328;
/*  97 */     float stepPulse = class_3532.method_15374(age * 0.72F) * movement * 1.8F;
/*  98 */     float idle = class_3532.method_15374(age * 0.12F) * idleAmplitude;
/*     */     
/* 100 */     simulation.rootPitch.update(-movement * 2.2F - sprint * 6.8F + airbornePitch, dt, 82.0F, 13.0F);
/* 101 */     simulation.rootYaw.update(turnLag, dt, 95.0F, 14.0F);
/* 102 */     simulation.segmentPitch.update(idle + movement * 2.4F + sprint * 4.5F + airborneFlex + stepPulse, dt, 72.0F, 11.0F);
/*     */ 
/*     */ 
/*     */     
/* 106 */     simulation.segmentYaw.update(turnLag * 0.82F, dt, 86.0F, 12.0F);
/*     */     
/* 108 */     return new Pose(simulation.rootPitch.value, simulation.rootYaw.value, simulation.segmentPitch.value, simulation.segmentYaw.value);
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
/*     */   CompanionPose sampleCompanion(class_1657 player, class_10055 state, boolean preview, float intensity) {
/* 120 */     double time = (System.nanoTime() - PREVIEW_EPOCH_NANOS) / 1.0E9D;
/*     */     
/* 122 */     if (preview) {
/* 123 */       return new CompanionPose(0.72F, -0.4F + 
/*     */           
/* 125 */           (float)Math.sin(time * 2.1D) * 0.025F, 0.08F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 130 */     long now = System.nanoTime();
/* 131 */     if (this.companionSimulations.size() > 256) {
/* 132 */       this.companionSimulations.clear();
/*     */     }
/* 134 */     CompanionSimulation simulation = this.companionSimulations.computeIfAbsent(
/* 135 */         Integer.valueOf(player.method_5628()), ignored -> new CompanionSimulation(now));
/*     */     
/* 137 */     float dt = Math.min(0.05F, Math.max(0.001F, (float)(now - simulation.lastUpdateNanos) / 1.0E9F));
/*     */ 
/*     */ 
/*     */     
/* 141 */     simulation.lastUpdateNanos = now;
/*     */     
/* 143 */     class_243 velocity = player.method_18798();
/* 144 */     float yawRadians = (float)Math.toRadians(state.field_53446);
/* 145 */     float sin = class_3532.method_15374(yawRadians);
/* 146 */     float cos = class_3532.method_15362(yawRadians);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     float localSide = (float)velocity.field_1352 * cos + (float)velocity.field_1350 * sin;
/*     */     
/* 153 */     float localForward = -((float)velocity.field_1352) * sin + (float)velocity.field_1350 * cos;
/*     */     
/* 155 */     float strength = class_3532.method_15363(intensity, 0.0F, 1.0F);
/* 156 */     float targetX = 0.72F - class_3532.method_15363(localSide * 1.25F * strength, -0.34F, 0.34F);
/*     */ 
/*     */     
/* 159 */     float targetY = -0.4F - class_3532.method_15363((float)velocity.field_1351 * 0.72F * strength, -0.34F, 0.34F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     float targetZ = 0.08F + class_3532.method_15363(localForward * 1.55F * strength, -0.48F, 0.48F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     float stiffness = class_3532.method_16439(strength, 55.0F, 27.0F);
/* 172 */     float damping = class_3532.method_16439(strength, 11.0F, 7.0F);
/* 173 */     simulation.x.update(targetX, dt, stiffness, damping);
/* 174 */     simulation.y.update(targetY, dt, stiffness * 0.88F, damping * 0.94F);
/* 175 */     simulation.z.update(targetZ, dt, stiffness, damping);
/*     */     
/* 177 */     return new CompanionPose(simulation.x.value, simulation.y.value + 
/*     */ 
/*     */         
/* 180 */         (float)Math.sin(time * 2.1D) * 0.025F, simulation.z.value);
/*     */   }
/*     */   static final class Pose extends Record { private final float rootPitch; private final float rootYaw; private final float segmentPitch;
/*     */     private final float segmentYaw;
/*     */     
/* 185 */     Pose(float rootPitch, float rootYaw, float segmentPitch, float segmentYaw) { this.rootPitch = rootPitch; this.rootYaw = rootYaw; this.segmentPitch = segmentPitch; this.segmentYaw = segmentYaw; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$Pose;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #185	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 185 */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$Pose; } public float rootPitch() { return this.rootPitch; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$Pose;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #185	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$Pose; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$Pose;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #185	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$Pose;
/* 185 */       //   0	8	1	o	Ljava/lang/Object; } public float rootYaw() { return this.rootYaw; } public float segmentPitch() { return this.segmentPitch; } public float segmentYaw() { return this.segmentYaw; }
/*     */      }
/*     */   static final class CompanionPose extends Record { private final float x; private final float y; private final float z;
/* 188 */     CompanionPose(float x, float y, float z) { this.x = x; this.y = y; this.z = z; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$CompanionPose;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$CompanionPose; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$CompanionPose;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$CompanionPose; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$CompanionPose;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsPhysics$CompanionPose;
/* 188 */       //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public float z() { return this.z; }
/*     */      }
/*     */   
/*     */   private static final class Simulation {
/* 192 */     private final CosmeticsPhysics.Spring rootPitch = new CosmeticsPhysics.Spring();
/* 193 */     private final CosmeticsPhysics.Spring rootYaw = new CosmeticsPhysics.Spring();
/* 194 */     private final CosmeticsPhysics.Spring segmentPitch = new CosmeticsPhysics.Spring();
/* 195 */     private final CosmeticsPhysics.Spring segmentYaw = new CosmeticsPhysics.Spring();
/* 196 */     private final CosmeticsPhysics.Spring movementInput = new CosmeticsPhysics.Spring();
/* 197 */     private final CosmeticsPhysics.Spring verticalInput = new CosmeticsPhysics.Spring();
/* 198 */     private final CosmeticsPhysics.Spring turnInput = new CosmeticsPhysics.Spring();
/*     */     private long lastUpdateNanos;
/*     */     private float lastYaw;
/*     */     
/*     */     private Simulation(float lastYaw, long lastUpdateNanos) {
/* 203 */       this.lastYaw = lastYaw;
/* 204 */       this.lastUpdateNanos = lastUpdateNanos;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class CompanionSimulation {
/* 209 */     private final CosmeticsPhysics.Spring x = new CosmeticsPhysics.Spring(0.72F);
/* 210 */     private final CosmeticsPhysics.Spring y = new CosmeticsPhysics.Spring(-0.4F);
/* 211 */     private final CosmeticsPhysics.Spring z = new CosmeticsPhysics.Spring(0.08F);
/*     */     private long lastUpdateNanos;
/*     */     
/*     */     private CompanionSimulation(long lastUpdateNanos) {
/* 215 */       this.lastUpdateNanos = lastUpdateNanos;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Spring
/*     */   {
/*     */     private float value;
/*     */     private float velocity;
/*     */     
/*     */     private Spring() {}
/*     */     
/*     */     private Spring(float value) {
/* 227 */       this.value = value;
/*     */     }
/*     */     
/*     */     void update(float target, float dt, float stiffness, float damping) {
/* 231 */       this.velocity += (target - this.value) * stiffness * dt;
/* 232 */       this.velocity *= (float)Math.exp((-damping * dt));
/* 233 */       this.value += this.velocity * dt;
/*     */     }
/*     */     
/*     */     void reset() {
/* 237 */       this.value = 0.0F;
/* 238 */       this.velocity = 0.0F;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\CosmeticsPhysics.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */