/*     */ package vorga.phazeclient.base.util.animation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements Interpolation
/*     */ {
/*     */   public double interpolate(double progress) {
/* 102 */     double n1 = 7.5625D;
/* 103 */     double d1 = 2.75D;
/*     */     
/* 105 */     if (progress < 1.0D / d1)
/* 106 */       return n1 * progress * progress; 
/* 107 */     if (progress < 2.0D / d1)
/* 108 */       return n1 * (progress -= 1.5D / d1) * progress + 0.75D; 
/* 109 */     if (progress < 2.5D / d1) {
/* 110 */       return n1 * (progress -= 2.25D / d1) * progress + 0.9375D;
/*     */     }
/* 112 */     return n1 * (progress -= 2.625D / d1) * progress + 0.984375D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return "Bounce";
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations$8.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */