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
/*    */ final class BoneStyle
/*    */   extends Record
/*    */ {
/*    */   private final boolean visible;
/*    */   private final float scale;
/*    */   private final ParsedCosmeticModel.Vec3 offset;
/*    */   private final ParsedCosmeticModel.Vec3 rotation;
/*    */   private final int color;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$BoneStyle;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #95	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$BoneStyle;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$BoneStyle;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #95	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$BoneStyle;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$BoneStyle;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #95	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/ParsedCosmeticModel$BoneStyle;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   BoneStyle(boolean visible, float scale, ParsedCosmeticModel.Vec3 offset, ParsedCosmeticModel.Vec3 rotation, int color) {
/* 95 */     this.visible = visible; this.scale = scale; this.offset = offset; this.rotation = rotation; this.color = color; } public boolean visible() { return this.visible; } public float scale() { return this.scale; } public ParsedCosmeticModel.Vec3 offset() { return this.offset; } public ParsedCosmeticModel.Vec3 rotation() { return this.rotation; } public int color() { return this.color; }
/* 96 */    static final BoneStyle DEFAULT = new BoneStyle(true, 1.0F, ParsedCosmeticModel.Vec3.ZERO, ParsedCosmeticModel.Vec3.ZERO, 16777215);
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\ParsedCosmeticModel$BoneStyle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */