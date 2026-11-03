/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_742;
/*    */ 
/*    */ 
/*    */ public final class PetMovementTracker
/*    */ {
/*    */   private static final double MOVE_PER_TICK_SQ = 4.0E-4D;
/*    */   private static final int STOP_AFTER_TICKS = 2;
/* 13 */   private static final Map<UUID, State> STATES = new HashMap<>();
/*    */   
/*    */   public static boolean isMoving(class_742 player) {
/* 16 */     State state = STATES.computeIfAbsent(player.method_5667(), ignored -> new State(player.method_23317(), player.method_23321(), player.field_6012));
/*    */     
/* 18 */     if (state.age == player.field_6012) return state.moving;
/*    */     
/* 20 */     if (player.field_6012 < state.age || player.field_6012 - state.age > 20) {
/* 21 */       state.x = player.method_23317();
/* 22 */       state.z = player.method_23321();
/* 23 */       state.age = player.field_6012;
/* 24 */       state.stillTicks = 0;
/* 25 */       state.moving = false;
/* 26 */       return false;
/*    */     } 
/*    */     
/* 29 */     double dx = player.method_23317() - state.x;
/* 30 */     double dz = player.method_23321() - state.z;
/* 31 */     boolean moved = (dx * dx + dz * dz >= 4.0E-4D);
/* 32 */     state.x = player.method_23317();
/* 33 */     state.z = player.method_23321();
/* 34 */     state.age = player.field_6012;
/*    */     
/* 36 */     if (moved) {
/* 37 */       state.stillTicks = 0;
/* 38 */       state.moving = true;
/* 39 */     } else if (++state.stillTicks >= 2) {
/* 40 */       state.moving = false;
/*    */     } 
/*    */     
/* 43 */     if (STATES.size() > 512) {
/* 44 */       STATES.entrySet().removeIf(entry -> (((State)entry.getValue()).age + 200 < player.field_6012));
/*    */     }
/* 46 */     return state.moving;
/*    */   }
/*    */   
/*    */   private static final class State {
/*    */     double x;
/*    */     double z;
/*    */     int age;
/*    */     int stillTicks;
/*    */     boolean moving;
/*    */     
/*    */     State(double x, double z, int age) {
/* 57 */       this.x = x;
/* 58 */       this.z = z;
/* 59 */       this.age = age;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\PetMovementTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */