/*    */ package vorga.phazeclient.implement.menu.font;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import vorga.phazeclient.api.system.font.Fonts;
/*    */ 
/*    */ 
/*    */ public final class GuiFonts
/*    */ {
/* 11 */   private static final Map<Key, MsdfGuiFontRenderer> CACHE = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MsdfGuiFontRenderer getSize(int size) {
/* 17 */     return getSize(size, Fonts.Type.INTER_BOLD);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MsdfGuiFontRenderer getSize(int size, Fonts.Type type) {
/* 22 */     Objects.requireNonNull(type, "type");
/* 23 */     if (size <= 0) throw new IllegalArgumentException("GUI font size must be positive"); 
/* 24 */     switch (type) { default: throw new MatchException(null, null);
/*    */       case INTER_BOLD: case SF_BOLD: case JET_BOLD: 
/* 26 */       case INTER_DEFAULT: case SF_DEFAULT: case JET_DEFAULT: case ICO: break; }  boolean bold = false;
/*    */     
/* 28 */     return CACHE.computeIfAbsent(new Key(size, bold), key -> new MsdfGuiFontRenderer(key.size() / 2.0F, key.bold()));
/*    */   }
/*    */   private static final class Key extends Record { private final int size; private final boolean bold;
/* 31 */     private Key(int size, boolean bold) { this.size = size; this.bold = bold; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/font/GuiFonts$Key;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 31 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiFonts$Key; } public int size() { return this.size; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/font/GuiFonts$Key;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/font/GuiFonts$Key; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/font/GuiFonts$Key;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/font/GuiFonts$Key;
/* 31 */       //   0	8	1	o	Ljava/lang/Object; } public boolean bold() { return this.bold; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\font\GuiFonts.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */