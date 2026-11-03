/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Edge
/*     */   extends Record
/*     */ {
/*     */   private final float startX;
/*     */   private final float startY;
/*     */   private final float startZ;
/*     */   private final float endX;
/*     */   private final float endY;
/*     */   private final float endZ;
/*     */   private final float normalX;
/*     */   private final float normalY;
/*     */   private final float normalZ;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/HolyWorldHelperRenderer$Edge;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #351	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/HolyWorldHelperRenderer$Edge;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/HolyWorldHelperRenderer$Edge;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #351	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/HolyWorldHelperRenderer$Edge;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/HolyWorldHelperRenderer$Edge;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #351	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/HolyWorldHelperRenderer$Edge;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private Edge(float startX, float startY, float startZ, float endX, float endY, float endZ, float normalX, float normalY, float normalZ) {
/* 351 */     this.startX = startX; this.startY = startY; this.startZ = startZ; this.endX = endX; this.endY = endY; this.endZ = endZ; this.normalX = normalX; this.normalY = normalY; this.normalZ = normalZ; } public float startX() { return this.startX; } public float startY() { return this.startY; } public float startZ() { return this.startZ; } public float endX() { return this.endX; } public float endY() { return this.endY; } public float endZ() { return this.endZ; } public float normalX() { return this.normalX; } public float normalY() { return this.normalY; } public float normalZ() { return this.normalZ; }
/*     */   
/*     */   private static Edge of(float firstX, float firstY, float firstZ, float secondX, float secondY, float secondZ) {
/* 354 */     if (compare(firstX, firstY, firstZ, secondX, secondY, secondZ) > 0) {
/* 355 */       float swapX = firstX;
/* 356 */       float swapY = firstY;
/* 357 */       float swapZ = firstZ;
/* 358 */       firstX = secondX;
/* 359 */       firstY = secondY;
/* 360 */       firstZ = secondZ;
/* 361 */       secondX = swapX;
/* 362 */       secondY = swapY;
/* 363 */       secondZ = swapZ;
/*     */     } 
/* 365 */     float dx = secondX - firstX;
/* 366 */     float dy = secondY - firstY;
/* 367 */     float dz = secondZ - firstZ;
/* 368 */     float length = (float)Math.sqrt((dx * dx + dy * dy + dz * dz));
/* 369 */     float normalX = (length == 0.0F) ? 0.0F : (dx / length);
/* 370 */     float normalY = (length == 0.0F) ? 1.0F : (dy / length);
/* 371 */     float normalZ = (length == 0.0F) ? 0.0F : (dz / length);
/* 372 */     return new Edge(firstX, firstY, firstZ, secondX, secondY, secondZ, normalX, normalY, normalZ);
/*     */   }
/*     */   
/*     */   private static int compare(float firstX, float firstY, float firstZ, float secondX, float secondY, float secondZ) {
/* 376 */     int x = Float.compare(firstX, secondX);
/* 377 */     if (x != 0) return x; 
/* 378 */     int y = Float.compare(firstY, secondY);
/* 379 */     return (y != 0) ? y : Float.compare(firstZ, secondZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\HolyWorldHelperRenderer$Edge.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */