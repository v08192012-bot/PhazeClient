/*   */ package vorga.phazeclient.api.system.animation.implement;
/*   */ 
/*   */ import vorga.phazeclient.api.system.animation.Animation;
/*   */ 
/*   */ public class LinearAnimation
/*   */   extends Animation
/*   */ {
/*   */   public double calculation(double value) {
/* 9 */     return value / this.ms;
/*   */   }
/*   */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\animation\implement\LinearAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */