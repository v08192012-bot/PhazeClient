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
/*    */ class null
/*    */   implements Interpolation
/*    */ {
/*    */   public double interpolate(double progress) {
/* 44 */     if (progress < 0.5D) {
/* 45 */       return 2.0D * progress * progress;
/*    */     }
/* 47 */     return 1.0D - Math.pow(-2.0D * progress + 2.0D, 2.0D) / 2.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return "Balanced";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations$4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */