/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class VoxelVertex
/*    */   extends Record
/*    */ {
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float z;
/*    */   private final float u;
/*    */   
/*    */   public VoxelVertex(float x, float y, float z, float u, float v) {
/* 19 */     this.x = x; this.y = y; this.z = z; this.u = u; this.v = v; } private final float v; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelVertex;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelVertex; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelVertex;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelVertex; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelVertex;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelVertex;
/* 19 */     //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public float z() { return this.z; } public float u() { return this.u; } public float v() { return this.v; }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ThreeDSkinsVoxelizer$VoxelVertex.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */