/*    */ package vorga.phazeclient.implement.hitcolor;
/*    */ 
/*    */ import net.minecraft.class_4608;
/*    */ 
/*    */ public final class HitColorArmorContext
/*    */ {
/*  7 */   private static final ThreadLocal<Integer> OVERLAY = ThreadLocal.withInitial(() -> Integer.valueOf(class_4608.field_21444));
/*  8 */   private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> Boolean.valueOf(false));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void begin(int overlay) {
/* 14 */     OVERLAY.set(Integer.valueOf(overlay));
/* 15 */     ACTIVE.set(Boolean.valueOf(true));
/*    */   }
/*    */   
/*    */   public static boolean isActive() {
/* 19 */     return ((Boolean)ACTIVE.get()).booleanValue();
/*    */   }
/*    */   
/*    */   public static int getOverlay() {
/* 23 */     return ((Integer)OVERLAY.get()).intValue();
/*    */   }
/*    */   
/*    */   public static void end() {
/* 27 */     ACTIVE.set(Boolean.valueOf(false));
/* 28 */     OVERLAY.set(Integer.valueOf(class_4608.field_21444));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\hitcolor\HitColorArmorContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */