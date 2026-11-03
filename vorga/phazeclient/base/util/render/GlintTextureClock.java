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
/*    */ public final class GlintTextureClock
/*    */ {
/*    */   private boolean active;
/*    */   private long previousRealMs;
/*    */   private long textureMs;
/*    */   private double fractionalMs;
/*    */   private double previousSpeed;
/*    */   
/*    */   public long sample(long realMs, boolean enabled, double speed) {
/* 23 */     if (!enabled) {
/* 24 */       this.active = false;
/* 25 */       return realMs;
/*    */     } 
/*    */     
/* 28 */     double boundedSpeed = Double.isFinite(speed) ? Math.max(0.2D, Math.min(4.0D, speed)) : 1.0D;
/* 29 */     if (!this.active) {
/* 30 */       this.active = true;
/* 31 */       this.previousRealMs = realMs;
/* 32 */       this.textureMs = realMs;
/* 33 */       this.fractionalMs = 0.0D;
/* 34 */     } else if (realMs > this.previousRealMs) {
/* 35 */       double elapsed = (realMs - this.previousRealMs) * this.previousSpeed + this.fractionalMs;
/* 36 */       long wholeMs = (long)elapsed;
/* 37 */       this.textureMs += wholeMs;
/* 38 */       this.fractionalMs = elapsed - wholeMs;
/* 39 */       this.previousRealMs = realMs;
/*    */     } 
/* 41 */     this.previousSpeed = boundedSpeed;
/* 42 */     return this.textureMs;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\GlintTextureClock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */