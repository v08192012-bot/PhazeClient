/*    */ package vorga.phazeclient.api.system.animation.implement;
/*    */ 
/*    */ import vorga.phazeclient.api.system.animation.Animation;
/*    */ 
/*    */ public class DecelerateAnimation
/*    */   extends Animation
/*    */ {
/*    */   public double calculation(double value) {
/*  9 */     double x = value / this.ms;
/* 10 */     return 1.0D - (x - 1.0D) * (x - 1.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\animation\implement\DecelerateAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */