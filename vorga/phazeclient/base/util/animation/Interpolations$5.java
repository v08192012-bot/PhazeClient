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
/* 60 */     return 1.0D + 2.70158D * Math.pow(progress - 1.0D, 3.0D) + 1.70158D * Math.pow(progress - 1.0D, 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 65 */     return "Back";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations$5.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */