/*    */ package vorga.phazeclient.base.util.animation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Interpolation
/*    */ {
/*    */   public double interpolate(double progress) {
/* 20 */     return progress * progress;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 25 */     return "Smooth";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations$2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */