/*    */ package vorga.phazeclient.api.system.cursor;
/*    */ 
/*    */ public final class HudCursorRelay {
/*  4 */   private static int deferredShape = 0;
/*  5 */   private static int deferredPriority = 0;
/*    */ 
/*    */   
/*    */   private static boolean deferredActive = false;
/*    */ 
/*    */   
/*    */   public static void reset() {
/* 12 */     deferredShape = 0;
/* 13 */     deferredPriority = 0;
/* 14 */     deferredActive = false;
/*    */   }
/*    */   
/*    */   public static void defer(int shape, int priority) {
/* 18 */     if (!deferredActive || priority >= deferredPriority) {
/* 19 */       deferredShape = shape;
/* 20 */       deferredPriority = priority;
/* 21 */       deferredActive = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void apply() {
/* 26 */     if (!deferredActive) {
/*    */       return;
/*    */     }
/* 29 */     switch (deferredShape) { case 221188:
/* 30 */         CursorManager.requestHand(); break;
/* 31 */       case 221193: CursorManager.requestMove(); break;
/* 32 */       case 221189: CursorManager.requestHorizontalResize(); break;
/* 33 */       case 221190: CursorManager.requestVerticalResize(); break;
/* 34 */       case 221194: CursorManager.requestNotAllowed();
/*    */         break; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\cursor\HudCursorRelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */