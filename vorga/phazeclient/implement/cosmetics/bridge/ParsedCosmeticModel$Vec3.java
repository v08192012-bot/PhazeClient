/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
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
/*    */ final class Vec3
/*    */   extends Record
/*    */ {
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float z;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$Vec3;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$Vec3;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$Vec3;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$Vec3;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$Vec3;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$Vec3;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   Vec3(float x, float y, float z) {
/* 64 */     this.x = x; this.y = y; this.z = z; } public float x() { return this.x; } public float y() { return this.y; } public float z() { return this.z; }
/* 65 */    static final Vec3 ZERO = new Vec3(0.0F, 0.0F, 0.0F);
/* 66 */   static final Vec3 ONE = new Vec3(1.0F, 1.0F, 1.0F);
/*    */   
/*    */   Vec3 add(Vec3 other) {
/* 69 */     return new Vec3(this.x + other.x, this.y + other.y, this.z + other.z);
/*    */   }
/*    */   
/*    */   Vec3 mul(Vec3 other) {
/* 73 */     return new Vec3(this.x * other.x, this.y * other.y, this.z * other.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\ParsedCosmeticModel$Vec3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */