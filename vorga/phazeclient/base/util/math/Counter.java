/*    */ package vorga.phazeclient.base.util.math;
/*    */ 
/*    */ public class Counter {
/*  4 */   private long time = System.currentTimeMillis();
/*    */   
/*    */   public void resetCounter() {
/*  7 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean isReached(long ms) {
/* 11 */     return (getTime() >= ms);
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 15 */     return System.currentTimeMillis() - this.time;
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 19 */     this.time = time;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\math\Counter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */