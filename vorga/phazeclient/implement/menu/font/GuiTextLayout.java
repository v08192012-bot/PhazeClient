/*     */ package vorga.phazeclient.implement.menu.font;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ final class GuiTextLayout
/*     */ {
/*     */   static final float GLYPH_SPACING = 0.025F;
/*  10 */   private static final int[] COLORS = new int[] { 0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215 }; final List<Glyph> glyphs; final List<Run> runs; final float width; final float height;
/*     */   static final class Glyph extends Record { private final String text; private final float x; private final float y;
/*     */     private final int rgb;
/*     */     private final boolean bold;
/*     */     
/*  15 */     Glyph(String text, float x, float y, int rgb, boolean bold) { this.text = text; this.x = x; this.y = y; this.rgb = rgb; this.bold = bold; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #15	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  15 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph; } public String text() { return this.text; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #15	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #15	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Glyph;
/*  15 */       //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public int rgb() { return this.rgb; } public boolean bold() { return this.bold; }
/*     */      }
/*     */   static final class Run extends Record { private final String text; private final float x; private final float y; private final int rgb; private final boolean bold;
/*  18 */     Run(String text, float x, float y, int rgb, boolean bold) { this.text = text; this.x = x; this.y = y; this.rgb = rgb; this.bold = bold; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Run;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #18	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Run; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Run;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #18	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Run; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Run;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #18	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/font/GuiTextLayout$Run;
/*  18 */       //   0	8	1	o	Ljava/lang/Object; } public String text() { return this.text; } public float x() { return this.x; } public float y() { return this.y; } public int rgb() { return this.rgb; } public boolean bold() { return this.bold; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiTextLayout(List<Glyph> glyphs, List<Run> runs, float width, float height) {
/*  27 */     this.glyphs = List.copyOf(glyphs);
/*  28 */     this.runs = List.copyOf(runs);
/*  29 */     this.width = width;
/*  30 */     this.height = height;
/*     */   }
/*     */   
/*     */   static GuiTextLayout create(String text, float size, boolean defaultBold) {
/*  34 */     List<Glyph> glyphs = new ArrayList<>();
/*  35 */     float x = 0.0F, y = 0.0F, width = 0.0F;
/*  36 */     float lineHeight = Math.max((GuiFontAtlas.get(false)).lineHeight, (GuiFontAtlas.get(true)).lineHeight) * size;
/*  37 */     int previous = -1, rgb = -1;
/*  38 */     boolean bold = defaultBold, previousBold = bold;
/*  39 */     for (int i = 0; i < text.length(); ) {
/*  40 */       int cp = text.codePointAt(i);
/*  41 */       i += Character.charCount(cp);
/*  42 */       if (cp == 167) {
/*  43 */         if (i >= text.length())
/*  44 */           break;  char code = Character.toLowerCase(text.charAt(i++));
/*  45 */         int colorIndex = "0123456789abcdef".indexOf(code);
/*  46 */         if (colorIndex >= 0) {
/*  47 */           rgb = COLORS[colorIndex];
/*  48 */           bold = defaultBold; continue;
/*  49 */         }  if (code == 'r') {
/*  50 */           rgb = -1;
/*  51 */           bold = defaultBold; continue;
/*  52 */         }  if (code == 'l') {
/*  53 */           bold = true; continue;
/*  54 */         }  if (code == 'x' && i + 12 <= text.length()) {
/*  55 */           int hex = 0;
/*  56 */           boolean valid = true;
/*  57 */           for (int digit = 0; digit < 6; digit++) {
/*  58 */             int offset = i + digit * 2;
/*  59 */             int value = Character.digit(text.charAt(offset + 1), 16);
/*  60 */             if (text.charAt(offset) != '§' || value < 0) { valid = false; break; }
/*  61 */              hex = hex << 4 | value;
/*     */           } 
/*  63 */           if (valid) { rgb = hex; bold = defaultBold; i += 12; }
/*     */         
/*     */         } 
/*     */         continue;
/*     */       } 
/*  68 */       if (cp == 9167) {
/*  69 */         int end = text.indexOf('⏏', i);
/*  70 */         if (end < 0)
/*  71 */           break;  try { rgb = Integer.parseInt(text.substring(i, end)) & 0xFFFFFF; }
/*  72 */         catch (NumberFormatException numberFormatException) {}
/*  73 */         i = end + 1;
/*     */         continue;
/*     */       } 
/*  76 */       if (cp == 13)
/*  77 */         continue;  if (cp == 10 || cp == 8232 || cp == 8233) {
/*  78 */         width = Math.max(width, (previous < 0) ? 0.0F : (x - 0.025F * size));
/*  79 */         x = 0.0F;
/*  80 */         y += lineHeight;
/*  81 */         previous = -1;
/*     */         continue;
/*     */       } 
/*  84 */       int repetitions = (cp == 9) ? 4 : 1;
/*  85 */       GuiFontAtlas atlas = GuiFontAtlas.get(bold);
/*  86 */       cp = atlas.supported((cp == 9) ? 32 : cp);
/*  87 */       for (int repeat = 0; repeat < repetitions; repeat++) {
/*  88 */         if (previous >= 0 && previousBold == bold) x += atlas.kerning(previous, cp) * size; 
/*  89 */         glyphs.add(new Glyph(Character.toString(cp), x, y, rgb, bold));
/*  90 */         x += (atlas.advance(cp) + 0.025F) * size;
/*  91 */         previous = cp;
/*  92 */         previousBold = bold;
/*     */       } 
/*     */     } 
/*  95 */     width = Math.max(width, (previous < 0) ? 0.0F : (x - 0.025F * size));
/*  96 */     List<Run> runs = new ArrayList<>();
/*  97 */     StringBuilder run = new StringBuilder();
/*  98 */     Glyph start = null;
/*  99 */     for (Glyph glyph : glyphs) {
/* 100 */       if (start != null && (glyph.rgb != start.rgb || glyph.bold != start.bold || glyph.y != start.y)) {
/* 101 */         runs.add(new Run(run.toString(), start.x, start.y, start.rgb, start.bold));
/* 102 */         run.setLength(0);
/* 103 */         start = null;
/*     */       } 
/* 105 */       if (start == null) start = glyph; 
/* 106 */       run.append(glyph.text);
/*     */     } 
/* 108 */     if (start != null) runs.add(new Run(run.toString(), start.x, start.y, start.rgb, start.bold)); 
/* 109 */     return new GuiTextLayout(glyphs, runs, width, y + size);
/*     */   }
/*     */   
/*     */   static int color(int base, int rgb) {
/* 113 */     return (rgb < 0) ? base : (base & 0xFF000000 | rgb);
/*     */   }
/*     */   
/*     */   static int mix(int start, int end, float t) {
/* 117 */     t = Math.max(0.0F, Math.min(1.0F, t));
/* 118 */     int result = 0;
/* 119 */     for (int shift = 0; shift <= 24; shift += 8) {
/* 120 */       int a = start >>> shift & 0xFF;
/* 121 */       int b = end >>> shift & 0xFF;
/* 122 */       result |= Math.round(a + (b - a) * t) << shift;
/*     */     } 
/* 124 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\font\GuiTextLayout.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */