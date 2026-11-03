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
/* 86 */     if (progress == 0.0D) return 0.0D; 
/* 87 */     if (progress == 1.0D) return 1.0D;
/*    */     
/* 89 */     double c4 = 2.0943951023931953D;
/* 90 */     return Math.pow(2.0D, -10.0D * progress) * Math.sin((progress * 10.0D - 0.75D) * c4) + 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 95 */     return "Elastic";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations$7.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */