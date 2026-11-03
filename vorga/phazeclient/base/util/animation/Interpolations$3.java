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
/*    */ class null
/*    */   implements Interpolation
/*    */ {
/*    */   public double interpolate(double progress) {
/* 32 */     return 1.0D - Math.pow(1.0D - progress, 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return "Fast";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations$3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */