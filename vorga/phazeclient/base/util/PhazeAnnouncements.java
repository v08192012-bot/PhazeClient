/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3414;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5251;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PhazeAnnouncements
/*     */ {
/*     */   private static final long BANNER_MS = 10000L;
/*  53 */   private static final Pattern URL_PATTERN = Pattern.compile("https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+[\\w\\-_~/#\\[\\]@$&*+=]");
/*     */   
/*     */   private static final int BRAND_FROM = 7272357;
/*     */   
/*     */   private static final int BRAND_TO = 1026408;
/*     */   
/*  59 */   private static final Set<String> seenIds = new LinkedHashSet<>();
/*  60 */   private static final List<Banner> banners = new ArrayList<>();
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
/*     */   private static int resolveColor(String key) {
/*  74 */     if (key == null) {
/*  75 */       return -1;
/*     */     }
/*  77 */     switch (key) { case "gray": case "red": case "orange": case "yellow": case "green": case "aqua": case "blue": case "purple": case "pink":  }  return 
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
/*  88 */       -1;
/*     */   }
/*     */   public static final class Banner extends Record {
/*     */     private final int id; private final String text; private final int color; private final long shownUntilMs;
/*     */     
/*  93 */     public Banner(int id, String text, int color, long shownUntilMs) { this.id = id; this.text = text; this.color = color; this.shownUntilMs = shownUntilMs; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/PhazeAnnouncements$Banner;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/PhazeAnnouncements$Banner; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/PhazeAnnouncements$Banner;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/PhazeAnnouncements$Banner; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/PhazeAnnouncements$Banner;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/base/util/PhazeAnnouncements$Banner;
/*  93 */       //   0	8	1	o	Ljava/lang/Object; } public int id() { return this.id; } public String text() { return this.text; } public int color() { return this.color; } public long shownUntilMs() { return this.shownUntilMs; }
/*     */      public boolean isExpired(long now) {
/*  95 */       return (now >= this.shownUntilMs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized List<Banner> activeBanners() {
/* 105 */     long now = System.currentTimeMillis();
/* 106 */     banners.removeIf(banner -> banner.isExpired(now));
/* 107 */     return List.copyOf(banners);
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized void dismiss(int id) {
/* 112 */     banners.removeIf(banner -> (banner.id() == id));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void acceptAll(JsonElement element) {
/* 117 */     if (element == null || !element.isJsonArray()) {
/*     */       return;
/*     */     }
/* 120 */     JsonArray array = element.getAsJsonArray();
/* 121 */     for (JsonElement entry : array) {
/* 122 */       if (entry != null && entry.isJsonObject()) {
/* 123 */         accept(entry.getAsJsonObject());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void accept(JsonObject json) {
/*     */     int id, repeatCount;
/* 134 */     if (json == null || !json.has("id")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 141 */       id = json.get("id").getAsInt();
/*     */ 
/*     */       
/* 144 */       repeatCount = (json.has("repeatCount") && !json.get("repeatCount").isJsonNull()) ? json.get("repeatCount").getAsInt() : 0;
/* 145 */     } catch (Throwable malformed) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     String key = "" + id + ":" + id;
/* 155 */     synchronized (PhazeAnnouncements.class) {
/* 156 */       if (!seenIds.add(key)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     String text = optionalString(json, "text");
/* 162 */     if (text == null) {
/*     */       return;
/*     */     }
/* 165 */     String sound = optionalString(json, "sound");
/* 166 */     int color = resolveColor(optionalString(json, "color"));
/*     */ 
/*     */     
/* 169 */     boolean chatEnabled = (json.has("chatEnabled") && !json.get("chatEnabled").isJsonNull() && json.get("chatEnabled").getAsBoolean());
/* 170 */     String chatText = chatEnabled ? optionalString(json, "chatText") : null;
/*     */     
/* 172 */     class_310 client = class_310.method_1551();
/* 173 */     if (client == null) {
/*     */       return;
/*     */     }
/* 176 */     client.execute(() -> show(client, id, text, color, sound, chatText));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void show(class_310 client, int id, String text, int color, String sound, String chatText) {
/* 181 */     synchronized (PhazeAnnouncements.class) {
/* 182 */       banners.add(new Banner(id, text, color, System.currentTimeMillis() + 10000L));
/*     */     } 
/*     */     
/* 185 */     playSound(client, sound);
/*     */     
/* 187 */     if (chatText != null && !chatText.isBlank() && client.field_1724 != null) {
/* 188 */       client.field_1724.method_7353(buildChatMessage(chatText), false);
/*     */     }
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
/*     */   private static void playSound(class_310 client, String sound) {
/* 201 */     if (sound == null || sound.isBlank() || "none".equals(sound)) {
/*     */       return;
/*     */     }
/* 204 */     if (client.method_1483() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 208 */     switch (sound) { case "ping": 
/*     */       case "levelup": 
/*     */       case "bell": 
/*     */       case "chime": 
/*     */       case "xp": 
/*     */       case "challenge": 
/*     */       case "click": 
/*     */       default:
/* 216 */         break; }  class_3414 event = null;
/*     */     
/* 218 */     if (event == null) {
/*     */       return;
/*     */     }
/* 221 */     client.method_1483().method_4873((class_1113)class_1109.method_4757(event, 1.0F, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class_2561 buildChatMessage(String raw) {
/* 232 */     class_5250 result = brandPrefix();
/* 233 */     Matcher matcher = URL_PATTERN.matcher(raw);
/* 234 */     int cursor = 0;
/*     */     
/* 236 */     while (matcher.find()) {
/* 237 */       if (matcher.start() > cursor) {
/* 238 */         result.method_10852((class_2561)class_2561.method_43470(raw.substring(cursor, matcher.start())));
/*     */       }
/* 240 */       String url = matcher.group();
/* 241 */       result.method_10852((class_2561)class_2561.method_43470(url).method_10862(class_2583.field_24360
/* 242 */             .method_10977(class_124.field_1075)
/* 243 */             .method_30938(Boolean.valueOf(true))
/* 244 */             .method_10958(new class_2558(class_2558.class_2559.field_11749, url))));
/* 245 */       cursor = matcher.end();
/*     */     } 
/* 247 */     if (cursor < raw.length()) {
/* 248 */       result.method_10852((class_2561)class_2561.method_43470(raw.substring(cursor)));
/*     */     }
/* 250 */     return (class_2561)result;
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
/*     */   private static class_5250 brandPrefix() {
/* 263 */     String brand = "Phaze Client";
/* 264 */     class_5250 prefix = class_2561.method_43470("");
/*     */     
/* 266 */     for (int i = 0; i < "Phaze Client".length(); i++) {
/* 267 */       float t = ("Phaze Client".length() == 1) ? 0.0F : (i / ("Phaze Client".length() - 1));
/* 268 */       prefix.method_10852((class_2561)class_2561.method_43470(String.valueOf("Phaze Client".charAt(i)))
/* 269 */           .method_10862(class_2583.field_24360
/* 270 */             .method_27703(class_5251.method_27717(lerpColor(7272357, 1026408, t)))
/* 271 */             .method_10982(Boolean.valueOf(true))));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 276 */     prefix.method_10852((class_2561)class_2561.method_43470(" · ")
/* 277 */         .method_10862(class_2583.field_24360.method_10977(class_124.field_1063)));
/* 278 */     return prefix;
/*     */   }
/*     */   
/*     */   private static int lerpColor(int from, int to, float t) {
/* 282 */     int r = (int)((from >> 16 & 0xFF) + ((to >> 16 & 0xFF) - (from >> 16 & 0xFF)) * t);
/* 283 */     int g = (int)((from >> 8 & 0xFF) + ((to >> 8 & 0xFF) - (from >> 8 & 0xFF)) * t);
/* 284 */     int b = (int)((from & 0xFF) + ((to & 0xFF) - (from & 0xFF)) * t);
/* 285 */     return r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   private static String optionalString(JsonObject json, String field) {
/* 289 */     if (!json.has(field) || json.get(field).isJsonNull()) {
/* 290 */       return null;
/*     */     }
/* 292 */     String value = json.get(field).getAsString().trim();
/* 293 */     return value.isEmpty() ? null : value;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\PhazeAnnouncements.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */