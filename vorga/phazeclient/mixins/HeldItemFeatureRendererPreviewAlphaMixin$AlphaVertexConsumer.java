/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_4588;
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
/*     */ final class AlphaVertexConsumer
/*     */   implements class_4588
/*     */ {
/*     */   private final class_4588 delegate;
/*     */   private final float alpha;
/*     */   
/*     */   private AlphaVertexConsumer(class_4588 delegate, float alpha) {
/*  79 */     this.delegate = delegate;
/*  80 */     this.alpha = alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22912(float x, float y, float z) {
/*  85 */     this.delegate.method_22912(x, y, z);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_1336(int red, int green, int blue, int alpha) {
/*  91 */     this.delegate.method_1336(red, green, blue, Math.round(alpha * this.alpha));
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_39415(int argb) {
/*  97 */     int sourceAlpha = argb >>> 24;
/*  98 */     int fadedAlpha = Math.round(sourceAlpha * this.alpha);
/*  99 */     this.delegate.method_39415(argb & 0xFFFFFF | fadedAlpha << 24);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22913(float u, float v) {
/* 105 */     this.delegate.method_22913(u, v);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_60796(int u, int v) {
/* 111 */     this.delegate.method_60796(u, v);
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22921(int u, int v) {
/* 117 */     this.delegate.method_22921(u, v);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22914(float x, float y, float z) {
/* 123 */     this.delegate.method_22914(x, y, z);
/* 124 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\HeldItemFeatureRendererPreviewAlphaMixin$AlphaVertexConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */