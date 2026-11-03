/*    */ package vorga.phazeclient.api.system.nametag;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ public class NametagTextCache
/*    */ {
/*  9 */   private static final Map<String, TextCacheEntry> textCache = new HashMap<>();
/*    */   private static final int CACHE_SIZE = 256;
/*    */   private static final long CACHE_DURATION = 60000L;
/*    */   
/*    */   public static class TextCacheEntry {
/*    */     public final float width;
/*    */     public final long timestamp;
/*    */     
/*    */     public TextCacheEntry(float width) {
/* 18 */       this.width = width;
/* 19 */       this.timestamp = System.currentTimeMillis();
/*    */     }
/*    */   }
/*    */   
/*    */   public static float getCachedTextWidth(class_2561 text) {
/* 24 */     String key = text.getString();
/* 25 */     TextCacheEntry entry = textCache.get(key);
/* 26 */     if (entry != null && System.currentTimeMillis() - entry.timestamp < 60000L) {
/* 27 */       return entry.width;
/*    */     }
/* 29 */     return -1.0F;
/*    */   }
/*    */   
/*    */   public static void cacheTextWidth(class_2561 text, float width) {
/* 33 */     String key = text.getString();
/* 34 */     textCache.put(key, new TextCacheEntry(width));
/*    */ 
/*    */     
/* 37 */     if (textCache.size() > 256) {
/* 38 */       textCache.entrySet().removeIf(e -> (System.currentTimeMillis() - ((TextCacheEntry)e.getValue()).timestamp > 60000L));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void clear() {
/* 44 */     textCache.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\nametag\NametagTextCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */