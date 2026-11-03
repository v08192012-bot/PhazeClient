/*   */ package vorga.phazeclient.api.system.font.glyph;public final class Glyph extends Record { private final int u; private final int v; private final int width; private final int height; private final char value; private final GlyphMap owner;
/*   */   
/* 3 */   public Glyph(int u, int v, int width, int height, char value, GlyphMap owner) { this.u = u; this.v = v; this.width = width; this.height = height; this.value = value; this.owner = owner; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/font/glyph/Glyph;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 3 */     //   0	7	0	this	Lvorga/phazeclient/api/system/font/glyph/Glyph; } public int u() { return this.u; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/font/glyph/Glyph;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lvorga/phazeclient/api/system/font/glyph/Glyph; } public final boolean equals(Object o) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/font/glyph/Glyph;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lvorga/phazeclient/api/system/font/glyph/Glyph;
/* 3 */     //   0	8	1	o	Ljava/lang/Object; } public int v() { return this.v; } public int width() { return this.width; } public int height() { return this.height; } public char value() { return this.value; } public GlyphMap owner() { return this.owner; }
/*   */    }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\glyph\Glyph.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */