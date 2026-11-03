/*    */ package vorga.phazeclient.implement.menu.font;final class Glyph extends Record { private final String text;
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final int rgb;
/*    */   private final boolean bold;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;
/*    */   }
/*    */   
/* 15 */   Glyph(String text, float x, float y, int rgb, boolean bold) { this.text = text; this.x = x; this.y = y; this.rgb = rgb; this.bold = bold; } public String text() { return this.text; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;
/* 15 */     //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public int rgb() { return this.rgb; } public boolean bold() { return this.bold; }
/*    */    }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\font\GuiTextLayout$Glyph.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */