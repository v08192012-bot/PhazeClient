/*     */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Objects;
/*     */ import java.util.TreeMap;
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
/*     */ final class Channel
/*     */ {
/* 196 */   final NavigableMap<Float, float[]> keys = (NavigableMap)new TreeMap<>();
/*     */   float[] constant;
/*     */   
/*     */   boolean isEmpty() {
/* 200 */     return (this.constant == null && this.keys.isEmpty());
/*     */   }
/*     */   
/*     */   float[] sample(float time, float[] fallback) {
/* 204 */     if (this.constant != null) return this.constant; 
/* 205 */     if (this.keys.isEmpty()) return fallback; 
/* 206 */     if (this.keys.size() == 1) return (float[])this.keys.firstEntry().getValue();
/*     */     
/* 208 */     Map.Entry<Float, float[]> first = (Map.Entry)this.keys.firstEntry();
/* 209 */     Map.Entry<Float, float[]> last = (Map.Entry)this.keys.lastEntry();
/* 210 */     if (time <= ((Float)first.getKey()).floatValue()) return first.getValue(); 
/* 211 */     if (time >= ((Float)last.getKey()).floatValue()) return last.getValue();
/*     */     
/* 213 */     Map.Entry<Float, float[]> a = (Map.Entry)this.keys.floorEntry(Float.valueOf(time));
/* 214 */     Map.Entry<Float, float[]> b = (Map.Entry)this.keys.ceilingEntry(Float.valueOf(time));
/* 215 */     if (a == null) return first.getValue(); 
/* 216 */     if (b == null) return last.getValue(); 
/* 217 */     if (Objects.equals(a.getKey(), b.getKey())) return a.getValue();
/*     */     
/* 219 */     float q = (time - ((Float)a.getKey()).floatValue()) / (((Float)b.getKey()).floatValue() - ((Float)a.getKey()).floatValue());
/* 220 */     q = Math.max(0.0F, Math.min(1.0F, q));
/* 221 */     float[] av = a.getValue();
/* 222 */     float[] bv = b.getValue();
/* 223 */     return new float[] { av[0] + q * (bv[0] - av[0]), av[1] + q * (bv[1] - av[1]), av[2] + q * (bv[2] - av[2]) };
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\PulseSourceModel$Channel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */