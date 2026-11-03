/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
/*    */ public final class CosmeticEntry extends Record { private final int id; private final CosmeticCategory category; private final String name; private final CosmeticKind kind; private final String modelResource; private final String textureResource;
/*    */   private final String previewResource;
/*    */   
/*  5 */   public CosmeticEntry(int id, CosmeticCategory category, String name, CosmeticKind kind, String modelResource, String textureResource, String previewResource, int textureWidth, int textureHeight, int frameCount, int frameWidth, int frameHeight, int frameTime, int price) { this.id = id; this.category = category; this.name = name; this.kind = kind; this.modelResource = modelResource; this.textureResource = textureResource; this.previewResource = previewResource; this.textureWidth = textureWidth; this.textureHeight = textureHeight; this.frameCount = frameCount; this.frameWidth = frameWidth; this.frameHeight = frameHeight; this.frameTime = frameTime; this.price = price; } private final int textureWidth; private final int textureHeight; private final int frameCount; private final int frameWidth; private final int frameHeight; private final int frameTime; private final int price; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticEntry; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticEntry; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticEntry;
/*  5 */     //   0	8	1	o	Ljava/lang/Object; } public int id() { return this.id; } public CosmeticCategory category() { return this.category; } public String name() { return this.name; } public CosmeticKind kind() { return this.kind; } public String modelResource() { return this.modelResource; } public String textureResource() { return this.textureResource; } public String previewResource() { return this.previewResource; } public int textureWidth() { return this.textureWidth; } public int textureHeight() { return this.textureHeight; } public int frameCount() { return this.frameCount; } public int frameWidth() { return this.frameWidth; } public int frameHeight() { return this.frameHeight; } public int frameTime() { return this.frameTime; } public int price() { return this.price; }
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
/*    */   public class_2960 textureId() {
/* 22 */     return (this.textureResource == null) ? null : class_2960.method_60654(this.textureResource);
/*    */   } }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\CosmeticEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */