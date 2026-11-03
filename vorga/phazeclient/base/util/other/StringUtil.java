/*     */ package vorga.phazeclient.base.util.other;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ 
/*     */ public final class StringUtil
/*     */   implements QuickImports
/*     */ {
/*     */   private StringUtil() {
/*  17 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final Map<Integer, String> SPECIAL_KEY_NAMES = buildSpecialKeyNames();
/*     */   
/*     */   private static Map<Integer, String> buildSpecialKeyNames() {
/*  34 */     Map<Integer, String> m = new HashMap<>();
/*     */     
/*  36 */     m.put(Integer.valueOf(32), "SPACE");
/*  37 */     m.put(Integer.valueOf(257), "ENTER");
/*  38 */     m.put(Integer.valueOf(258), "TAB");
/*  39 */     m.put(Integer.valueOf(259), "BACKSPACE");
/*  40 */     m.put(Integer.valueOf(256), "ESC");
/*     */     
/*  42 */     m.put(Integer.valueOf(260), "INSERT");
/*  43 */     m.put(Integer.valueOf(261), "DELETE");
/*  44 */     m.put(Integer.valueOf(268), "HOME");
/*  45 */     m.put(Integer.valueOf(269), "END");
/*  46 */     m.put(Integer.valueOf(266), "PG UP");
/*  47 */     m.put(Integer.valueOf(267), "PG DN");
/*     */     
/*  49 */     m.put(Integer.valueOf(263), "LEFT");
/*  50 */     m.put(Integer.valueOf(262), "RIGHT");
/*  51 */     m.put(Integer.valueOf(265), "UP");
/*  52 */     m.put(Integer.valueOf(264), "DOWN");
/*     */     
/*  54 */     m.put(Integer.valueOf(280), "CAPS");
/*  55 */     m.put(Integer.valueOf(281), "SCROLL");
/*  56 */     m.put(Integer.valueOf(282), "NUM LOCK");
/*  57 */     m.put(Integer.valueOf(283), "PRT SC");
/*  58 */     m.put(Integer.valueOf(284), "PAUSE");
/*  59 */     m.put(Integer.valueOf(348), "MENU");
/*     */ 
/*     */     
/*  62 */     m.put(Integer.valueOf(340), "LSHIFT");
/*  63 */     m.put(Integer.valueOf(341), "LCTRL");
/*  64 */     m.put(Integer.valueOf(342), "LALT");
/*  65 */     m.put(Integer.valueOf(343), "LSUPER");
/*  66 */     m.put(Integer.valueOf(344), "RSHIFT");
/*  67 */     m.put(Integer.valueOf(345), "RCTRL");
/*  68 */     m.put(Integer.valueOf(346), "RALT");
/*  69 */     m.put(Integer.valueOf(347), "RSUPER");
/*     */ 
/*     */ 
/*     */     
/*  73 */     m.put(Integer.valueOf(39), "'");
/*  74 */     m.put(Integer.valueOf(44), ",");
/*  75 */     m.put(Integer.valueOf(45), "-");
/*  76 */     m.put(Integer.valueOf(46), ".");
/*  77 */     m.put(Integer.valueOf(47), "/");
/*  78 */     m.put(Integer.valueOf(59), ";");
/*  79 */     m.put(Integer.valueOf(61), "=");
/*  80 */     m.put(Integer.valueOf(91), "[");
/*  81 */     m.put(Integer.valueOf(92), "\\");
/*  82 */     m.put(Integer.valueOf(93), "]");
/*  83 */     m.put(Integer.valueOf(96), "`");
/*     */     
/*  85 */     m.put(Integer.valueOf(330), "NP .");
/*  86 */     m.put(Integer.valueOf(331), "NP /");
/*  87 */     m.put(Integer.valueOf(332), "NP *");
/*  88 */     m.put(Integer.valueOf(333), "NP -");
/*  89 */     m.put(Integer.valueOf(334), "NP +");
/*  90 */     m.put(Integer.valueOf(335), "NP ENT");
/*  91 */     m.put(Integer.valueOf(336), "NP =");
/*  92 */     return m;
/*     */   }
/*     */   
/*     */   public static String randomString(int length) {
/*  96 */     return IntStream.range(0, length)
/*  97 */       .<CharSequence>mapToObj(operand -> String.valueOf((char)(new Random()).nextInt(97, 123)))
/*  98 */       .collect(Collectors.joining());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBindName(int key) {
/* 124 */     if (key < 0) return "N/A";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (key <= 7) {
/* 130 */       switch (key) { case 0: 
/*     */         case 1:
/*     */         
/*     */         case 2:
/* 134 */          }  return "MB " + key + 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     if (key >= 65 && key <= 90) {
/* 141 */       return String.valueOf((char)key);
/*     */     }
/* 143 */     if (key >= 48 && key <= 57) {
/* 144 */       return String.valueOf((char)key);
/*     */     }
/*     */ 
/*     */     
/* 148 */     if (key >= 290 && key <= 314) {
/* 149 */       return "F" + key - 290 + 1;
/*     */     }
/*     */ 
/*     */     
/* 153 */     if (key >= 320 && key <= 329) {
/* 154 */       return "NP " + key - 320;
/*     */     }
/*     */ 
/*     */     
/* 158 */     String mapped = SPECIAL_KEY_NAMES.get(Integer.valueOf(key));
/* 159 */     if (mapped != null) return mapped;
/*     */ 
/*     */ 
/*     */     
/* 163 */     return "KEY " + key;
/*     */   }
/*     */   
/*     */   public static String wrap(String input, int width, int size) {
/* 167 */     String[] words = input.split(" ");
/* 168 */     StringBuilder output = new StringBuilder();
/* 169 */     float lineWidth = 0.0F;
/* 170 */     for (String word : words) {
/* 171 */       float wordWidth = Fonts.getSize(size).getStringWidth(word);
/* 172 */       if (lineWidth + wordWidth > width) {
/* 173 */         output.append("\n");
/* 174 */         lineWidth = 0.0F;
/* 175 */       } else if (lineWidth > 0.0F) {
/* 176 */         output.append(" ");
/* 177 */         lineWidth += Fonts.getSize(size).getStringWidth(" ");
/*     */       } 
/* 179 */       output.append(word);
/* 180 */       lineWidth += wordWidth;
/*     */     } 
/* 182 */     return output.toString();
/*     */   }
/*     */   
/*     */   public static String getUserRole() {
/* 186 */     return "USER";
/*     */   }
/*     */ 
/*     */   
/*     */   public static void refreshRoles() {}
/*     */   
/*     */   public static Set<String> getDevelopers() {
/* 193 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   public static Set<String> getYoutubers() {
/* 197 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   public static Set<String> getTesters() {
/* 201 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   public static Set<String> getPasters() {
/* 205 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   public static Set<String> getCrow() {
/* 209 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   public static String getDuration(int time) {
/* 213 */     int mins = time / 60;
/* 214 */     String sec = String.format("%02d", new Object[] { Integer.valueOf(time % 60) });
/* 215 */     return "" + mins + ":" + mins;
/*     */   }
/*     */   
/*     */   public static String toRoman(int number) {
/* 219 */     if (number <= 0) return ""; 
/* 220 */     if (number >= 10) return "X";
/*     */     
/* 222 */     String[] romanNumerals = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };
/* 223 */     return (number <= romanNumerals.length) ? romanNumerals[number - 1] : String.valueOf(number);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\other\StringUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */