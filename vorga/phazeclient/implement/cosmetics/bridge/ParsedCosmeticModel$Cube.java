/*     */ package vorga.phazeclient.implement.cosmetics.bridge;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Cube
/*     */ {
/*     */   final ParsedCosmeticModel.Vec3 origin;
/*     */   final ParsedCosmeticModel.Vec3 size;
/*     */   final ParsedCosmeticModel.Vec3 pivot;
/*     */   final ParsedCosmeticModel.Vec3 rotation;
/*     */   final float inflate;
/*     */   final EnumMap<ParsedCosmeticModel.Side, ParsedCosmeticModel.Face> faces;
/*     */   
/*     */   Cube(ParsedCosmeticModel.Vec3 origin, ParsedCosmeticModel.Vec3 size, ParsedCosmeticModel.Vec3 pivot, ParsedCosmeticModel.Vec3 rotation, float inflate, EnumMap<ParsedCosmeticModel.Side, ParsedCosmeticModel.Face> faces) {
/* 110 */     this.origin = origin;
/* 111 */     this.size = size;
/* 112 */     this.pivot = pivot;
/* 113 */     this.rotation = rotation;
/* 114 */     this.inflate = inflate;
/* 115 */     this.faces = faces;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\ParsedCosmeticModel$Cube.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */