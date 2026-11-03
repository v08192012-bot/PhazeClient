/*    */ package vorga.phazeclient.api.system.animation.implement;
/*    */ 
/*    */ import vorga.phazeclient.api.system.animation.Animation;
/*    */ 
/*    */ public class EaseInOutAnimation
/*    */   extends Animation
/*    */ {
/*    */   public double calculation(double value) {
/*  9 */     double x = value / this.ms;
/*    */     
/* 11 */     if (x < 0.5D) {
/* 12 */       return 4.0D * x * x * x;
/*    */     }
/* 14 */     double f = 2.0D * x - 2.0D;
/* 15 */     return 1.0D - f * f * f / 2.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\animation\implement\EaseInOutAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */