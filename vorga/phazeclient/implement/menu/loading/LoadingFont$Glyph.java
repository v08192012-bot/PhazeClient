/*    */ package vorga.phazeclient.implement.menu.loading;
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
/*    */ final class Glyph
/*    */   extends Record
/*    */ {
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float width;
/*    */   private final float height;
/*    */   private final float u;
/*    */   
/*    */   Glyph(float x, float y, float width, float height, float u, float v, float uw, float vh) {
/* 22 */     this.x = x; this.y = y; this.width = width; this.height = height; this.u = u; this.v = v; this.uw = uw; this.vh = vh; } private final float v; private final float uw; private final float vh; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #22	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;
/* 22 */     //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public float width() { return this.width; } public float height() { return this.height; } public float u() { return this.u; } public float v() { return this.v; } public float uw() { return this.uw; } public float vh() { return this.vh; }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\loading\LoadingFont$Glyph.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */