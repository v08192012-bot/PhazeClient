/*    */ package vorga.phazeclient.api.system.animation;
/*    */ 
/*    */ import vorga.phazeclient.base.util.math.Counter;
/*    */ 
/*    */ public abstract class Animation
/*    */   implements AnimationCalculation
/*    */ {
/*    */   public Animation setMs(int ms) {
/*  9 */     this.ms = ms; return this; } public Animation setValue(double value) { this.value = value; return this; }
/*    */ 
/*    */   
/* 12 */   private final Counter counter = new Counter();
/*    */   
/*    */   protected int ms;
/*    */   protected double value;
/* 16 */   protected Direction direction = Direction.FORWARDS;
/*    */   
/*    */   public int getMs() {
/* 19 */     return this.ms;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 23 */     this.counter.resetCounter();
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 27 */     return this.counter.isReached(this.ms);
/*    */   }
/*    */   
/*    */   public boolean isFinished(Direction direction) {
/* 31 */     return (this.direction == direction && isDone());
/*    */   }
/*    */   
/*    */   public void setDirection(Direction direction) {
/* 35 */     if (this.direction != direction) {
/* 36 */       this.direction = direction;
/* 37 */       adjustTimer();
/*    */     } 
/*    */   }
/*    */   
/*    */   public Animation setDirectionAndFinish(Direction direction) {
/* 42 */     this.direction = direction;
/* 43 */     this.counter.setTime(System.currentTimeMillis() - this.ms);
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isDirection(Direction direction) {
/* 48 */     return (this.direction == direction);
/*    */   }
/*    */   
/*    */   private void adjustTimer() {
/* 52 */     this.counter.setTime(
/* 53 */         System.currentTimeMillis() - this.ms - Math.min(this.ms, this.counter.getTime()));
/*    */   }
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
/*    */   public Double getOutput() {
/* 67 */     return Double.valueOf(getOutputDouble());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getOutputDouble() {
/* 77 */     double time = (1.0D - calculation(this.counter.getTime())) * this.value;
/* 78 */     return (this.direction == Direction.FORWARDS) ? endValue() : (isDone() ? 0.0D : time);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getOutputFloat() {
/* 89 */     return (float)getOutputDouble();
/*    */   }
/*    */   
/*    */   private double endValue() {
/* 93 */     return isDone() ? this.value : (calculation(this.counter.getTime()) * this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\animation\Animation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */