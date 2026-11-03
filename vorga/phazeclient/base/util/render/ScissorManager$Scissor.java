/*    */ package vorga.phazeclient.base.util.render;
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
/*    */ class Scissor
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public int width;
/*    */   public int height;
/*    */   
/*    */   public void set(double x, double y, double width, double height) {
/* 87 */     this.x = Math.max(0, (int)Math.round(x));
/* 88 */     this.y = Math.max(0, (int)Math.round(y));
/* 89 */     this.width = Math.max(0, (int)Math.round(width));
/* 90 */     this.height = Math.max(0, (int)Math.round(height));
/*    */   }
/*    */   
/*    */   Scissor copy() {
/* 94 */     Scissor newScissor = new Scissor();
/* 95 */     newScissor.set(this.x, this.y, this.width, this.height);
/* 96 */     return newScissor;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\ScissorManager$Scissor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */