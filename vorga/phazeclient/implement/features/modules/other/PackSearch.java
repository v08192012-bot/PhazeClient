/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PackSearch
/*    */ {
/*    */   public static boolean matches(String query, String id, String name, String description) {
/* 11 */     String needle = query.strip().toLowerCase(Locale.ROOT);
/* 12 */     return (needle.isEmpty() || contains(id, needle) || contains(name, needle) || contains(description, needle));
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean contains(String text, String needle) {
/* 17 */     return text.replaceAll("§.", "").toLowerCase(Locale.ROOT).contains(needle);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackSearch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */