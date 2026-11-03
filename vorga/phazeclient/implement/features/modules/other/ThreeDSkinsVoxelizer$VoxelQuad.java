/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ 
/*    */ public final class VoxelQuad extends Record {
/*    */   private final ThreeDSkinsVoxelizer.VoxelVertex[] vertices;
/*    */   private final Vector3f normal;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelQuad;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelQuad;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelQuad;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelQuad;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelQuad;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/ThreeDSkinsVoxelizer$VoxelQuad;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/* 20 */   public VoxelQuad(ThreeDSkinsVoxelizer.VoxelVertex[] vertices, Vector3f normal) { this.vertices = vertices; this.normal = normal; } public ThreeDSkinsVoxelizer.VoxelVertex[] vertices() { return this.vertices; } public Vector3f normal() { return this.normal; }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ThreeDSkinsVoxelizer$VoxelQuad.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */