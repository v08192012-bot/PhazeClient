/*     */ package vorga.phazeclient.implement.cosmetics.bridge;
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
/*     */ final class Channel
/*     */ {
/* 138 */   final NavigableMap<Float, ParsedCosmeticModel.Vec3> keys = new TreeMap<>();
/*     */   ParsedCosmeticModel.Vec3 constant;
/*     */   
/*     */   boolean isEmpty() {
/* 142 */     return (this.constant == null && this.keys.isEmpty());
/*     */   }
/*     */   
/*     */   ParsedCosmeticModel.Vec3 sample(float t, ParsedCosmeticModel.Vec3 fallback) {
/* 146 */     if (this.constant != null) return this.constant; 
/* 147 */     if (this.keys.isEmpty()) return fallback; 
/* 148 */     if (this.keys.size() == 1) return (ParsedCosmeticModel.Vec3)this.keys.firstEntry().getValue();
/*     */     
/* 150 */     Map.Entry<Float, ParsedCosmeticModel.Vec3> first = this.keys.firstEntry();
/* 151 */     Map.Entry<Float, ParsedCosmeticModel.Vec3> last = this.keys.lastEntry();
/* 152 */     if (t <= ((Float)first.getKey()).floatValue()) return first.getValue(); 
/* 153 */     if (t >= ((Float)last.getKey()).floatValue()) return last.getValue();
/*     */     
/* 155 */     Map.Entry<Float, ParsedCosmeticModel.Vec3> a = this.keys.floorEntry(Float.valueOf(t));
/* 156 */     Map.Entry<Float, ParsedCosmeticModel.Vec3> b = this.keys.ceilingEntry(Float.valueOf(t));
/* 157 */     if (a == null) return first.getValue(); 
/* 158 */     if (b == null) return last.getValue(); 
/* 159 */     if (Objects.equals(a.getKey(), b.getKey())) return a.getValue();
/*     */     
/* 161 */     float q = (t - ((Float)a.getKey()).floatValue()) / (((Float)b.getKey()).floatValue() - ((Float)a.getKey()).floatValue());
/* 162 */     q = Math.max(0.0F, Math.min(1.0F, q));
/*     */     
/* 164 */     q = q * q * (3.0F - 2.0F * q);
/* 165 */     ParsedCosmeticModel.Vec3 av = a.getValue();
/* 166 */     ParsedCosmeticModel.Vec3 bv = b.getValue();
/* 167 */     return new ParsedCosmeticModel.Vec3(av
/* 168 */         .x() + (bv.x() - av.x()) * q, av
/* 169 */         .y() + (bv.y() - av.y()) * q, av
/* 170 */         .z() + (bv.z() - av.z()) * q);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\ParsedCosmeticModel$Channel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */