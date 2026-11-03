/*   */ package vorga.phazeclient.api.system.font.entry;public final class DrawEntry extends Record { private final float atX; private final float atY;
/*   */   private final int color;
/*   */   private final Glyph toDraw;
/*   */   
/* 5 */   public DrawEntry(float atX, float atY, int color, Glyph toDraw) { this.atX = atX; this.atY = atY; this.color = color; this.toDraw = toDraw; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/font/entry/DrawEntry;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 5 */     //   0	7	0	this	Lvorga/phazeclient/api/system/font/entry/DrawEntry; } public float atX() { return this.atX; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/font/entry/DrawEntry;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lvorga/phazeclient/api/system/font/entry/DrawEntry; } public final boolean equals(Object o) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/font/entry/DrawEntry;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lvorga/phazeclient/api/system/font/entry/DrawEntry;
/* 5 */     //   0	8	1	o	Ljava/lang/Object; } public float atY() { return this.atY; } public int color() { return this.color; } public Glyph toDraw() { return this.toDraw; }
/*   */    }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\entry\DrawEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */