/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_310;
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
/*     */ public final class PhazePlayerPresence
/*     */ {
/*  33 */   private static final String[] PUBLIC_PLAYER_ENDPOINTS = new String[] { "/api/online-players", "/api/players", "/api/online-users", "/api/users", "/api/online-clients", "/api/clients", "/api/presence", "/api/online" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Holder
/*     */   {
/*  45 */     static final PhazePlayerPresence INSTANCE = new PhazePlayerPresence();
/*     */   }
/*     */   
/*     */   public static PhazePlayerPresence getInstance() {
/*  49 */     return Holder.INSTANCE;
/*     */   }
/*     */   
/*  52 */   private volatile Set<String> knownUsernames = Collections.emptySet();
/*     */   private volatile String adminToken;
/*     */   
/*     */   private PhazePlayerPresence() {
/*  56 */     this.adminToken = loadAdminToken();
/*  57 */     refreshSelfOnly();
/*     */   }
/*     */   
/*     */   public boolean isKnownUser(String username) {
/*  61 */     if (username == null || username.isBlank()) {
/*  62 */       return false;
/*     */     }
/*  64 */     return this.knownUsernames.contains(username.toLowerCase(Locale.ROOT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findKnownUserInText(String text) {
/*  74 */     if (text == null || text.isBlank()) {
/*  75 */       return null;
/*     */     }
/*  77 */     Set<String> users = this.knownUsernames;
/*  78 */     if (users.isEmpty()) {
/*  79 */       return null;
/*     */     }
/*     */     
/*  82 */     int start = -1;
/*  83 */     for (int i = 0; i <= text.length(); i++) {
/*  84 */       char c = (i < text.length()) ? text.charAt(i) : Character.MIN_VALUE;
/*  85 */       if (i < text.length() && isUsernameChar(c)) {
/*  86 */         if (start < 0) {
/*  87 */           start = i;
/*     */         
/*     */         }
/*     */       }
/*  91 */       else if (start >= 0) {
/*     */ 
/*     */ 
/*     */         
/*  95 */         String token = text.substring(start, i);
/*  96 */         String normalized = token.toLowerCase(Locale.ROOT);
/*  97 */         if (users.contains(normalized)) {
/*  98 */           return normalized;
/*     */         }
/* 100 */         start = -1;
/*     */       } 
/*     */     } 
/* 103 */     String lowered = text.toLowerCase(Locale.ROOT);
/* 104 */     String best = null;
/* 105 */     for (String username : users) {
/* 106 */       if (username == null || username.isBlank()) {
/*     */         continue;
/*     */       }
/* 109 */       int from = 0;
/* 110 */       while (from < lowered.length()) {
/* 111 */         int idx = lowered.indexOf(username, from);
/* 112 */         if (idx < 0) {
/*     */           break;
/*     */         }
/* 115 */         int end = idx + username.length();
/* 116 */         boolean leftBoundary = (idx == 0 || !isUsernameChar(lowered.charAt(idx - 1)));
/* 117 */         boolean rightBoundary = (end >= lowered.length() || !isUsernameChar(lowered.charAt(end)));
/*     */         
/* 119 */         boolean accept = ((leftBoundary && rightBoundary) || (username.length() >= 4 && (leftBoundary || rightBoundary)));
/* 120 */         if (accept && (best == null || username.length() > best.length())) {
/* 121 */           best = username;
/*     */         }
/* 123 */         from = idx + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     return best;
/*     */   }
/*     */   
/*     */   public void refreshFromRulesPayload(JsonObject payload, String apiBase, int connectTimeoutMs, int readTimeoutMs) {
/* 131 */     Set<String> next = new LinkedHashSet<>();
/* 132 */     addCurrentUsername(next);
/* 133 */     collectUsernames(payload, next);
/* 134 */     if (next.size() <= 1) {
/*     */       try {
/* 136 */         fetchPublicPlayers(apiBase, connectTimeoutMs, readTimeoutMs, next);
/* 137 */       } catch (Throwable throwable) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     String token = this.adminToken;
/* 145 */     if (token == null || token.isBlank()) {
/* 146 */       token = loadAdminToken();
/* 147 */       this.adminToken = token;
/*     */     } 
/* 149 */     if (token != null && !token.isBlank()) {
/*     */       try {
/* 151 */         fetchAdminPlayers(apiBase, token, connectTimeoutMs, readTimeoutMs, next);
/* 152 */       } catch (Throwable throwable) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     this.knownUsernames = Collections.unmodifiableSet(next);
/*     */   }
/*     */   
/*     */   public void refreshSelfOnly() {
/* 162 */     Set<String> next = new LinkedHashSet<>();
/* 163 */     addCurrentUsername(next);
/* 164 */     this.knownUsernames = Collections.unmodifiableSet(next);
/*     */   }
/*     */   
/*     */   private void collectUsernames(JsonObject payload, Set<String> sink) {
/* 168 */     if (payload == null) {
/*     */       return;
/*     */     }
/* 171 */     collectArray(payload.get("usernames"), sink);
/* 172 */     collectArray(payload.get("players"), sink);
/* 173 */     collectArray(payload.get("onlinePlayers"), sink);
/*     */   }
/*     */   
/*     */   private void collectArray(JsonElement element, Set<String> sink) {
/* 177 */     if (element == null || !element.isJsonArray()) {
/*     */       return;
/*     */     }
/* 180 */     JsonArray array = element.getAsJsonArray();
/* 181 */     for (JsonElement entry : array) {
/* 182 */       if (entry == null || entry.isJsonNull()) {
/*     */         continue;
/*     */       }
/* 185 */       if (entry.isJsonPrimitive() && entry.getAsJsonPrimitive().isString()) {
/* 186 */         addUsername(sink, entry.getAsString());
/*     */         continue;
/*     */       } 
/* 189 */       if (entry.isJsonObject()) {
/* 190 */         JsonObject object = entry.getAsJsonObject();
/* 191 */         if (object.has("username")) {
/* 192 */           addUsername(sink, object.get("username").getAsString()); continue;
/* 193 */         }  if (object.has("name")) {
/* 194 */           addUsername(sink, object.get("name").getAsString());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fetchAdminPlayers(String apiBase, String token, int connectTimeoutMs, int readTimeoutMs, Set<String> sink) throws IOException {
/* 201 */     URI uri = URI.create(apiBase + "/api/admin/online-players");
/* 202 */     HttpURLConnection conn = (HttpURLConnection)uri.toURL().openConnection();
/*     */     try {
/* 204 */       applyCommonRequestHeaders(conn, connectTimeoutMs, readTimeoutMs);
/* 205 */       conn.setRequestMethod("GET");
/* 206 */       conn.setRequestProperty("Authorization", "Bearer " + token);
/* 207 */       int status = conn.getResponseCode();
/* 208 */       if (status != 200) {
/*     */         return;
/*     */       }
/* 211 */       String body = readBody(conn.getInputStream());
/* 212 */       collectUsernames(JsonParser.parseString(body), sink);
/*     */     } finally {
/* 214 */       conn.disconnect();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fetchPublicPlayers(String apiBase, int connectTimeoutMs, int readTimeoutMs, Set<String> sink) throws IOException {
/* 219 */     for (String endpoint : PUBLIC_PLAYER_ENDPOINTS) {
/* 220 */       URI uri = URI.create(apiBase + apiBase);
/* 221 */       HttpURLConnection conn = (HttpURLConnection)uri.toURL().openConnection();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectUsernames(JsonElement payload, Set<String> sink) {
/* 242 */     if (payload == null || payload.isJsonNull()) {
/*     */       return;
/*     */     }
/* 245 */     if (payload.isJsonArray()) {
/* 246 */       collectArray(payload, sink);
/*     */       return;
/*     */     } 
/* 249 */     if (!payload.isJsonObject()) {
/*     */       return;
/*     */     }
/*     */     
/* 253 */     JsonObject object = payload.getAsJsonObject();
/* 254 */     collectArray(object.get("usernames"), sink);
/* 255 */     collectArray(object.get("players"), sink);
/* 256 */     collectArray(object.get("onlinePlayers"), sink);
/* 257 */     collectArray(object.get("users"), sink);
/* 258 */     collectArray(object.get("onlineUsers"), sink);
/* 259 */     collectArray(object.get("clients"), sink);
/*     */   }
/*     */   
/*     */   private void applyCommonRequestHeaders(HttpURLConnection conn, int connectTimeoutMs, int readTimeoutMs) {
/* 263 */     conn.setConnectTimeout(connectTimeoutMs);
/* 264 */     conn.setReadTimeout(readTimeoutMs);
/* 265 */     conn.setRequestProperty("Accept", "application/json");
/* 266 */     conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String loadAdminToken() {
/* 274 */     String fromProperty = System.getProperty("phaze.rules.adminToken", "").trim();
/* 275 */     if (!fromProperty.isEmpty()) {
/* 276 */       return fromProperty;
/*     */     }
/*     */     
/* 279 */     String fromEnv = System.getenv("PHAZE_ADMIN_TOKEN");
/* 280 */     if (fromEnv != null && !fromEnv.isBlank()) {
/* 281 */       return fromEnv.trim();
/*     */     }
/*     */     
/*     */     try {
/* 285 */       class_310 client = class_310.method_1551();
/* 286 */       Path runDir = (client != null) ? client.field_1697.toPath() : Path.of(".", new String[0]);
/* 287 */       Path file = runDir.resolve("Phaze").resolve("files").resolve("admin_token");
/* 288 */       if (Files.exists(file, new java.nio.file.LinkOption[0])) {
/* 289 */         String token = Files.readString(file, StandardCharsets.UTF_8).trim();
/* 290 */         if (!token.isEmpty()) {
/* 291 */           return token;
/*     */         }
/*     */       } 
/* 294 */     } catch (Throwable throwable) {}
/*     */     
/* 296 */     return "";
/*     */   }
/*     */   
/*     */   private void addCurrentUsername(Set<String> sink) {
/*     */     try {
/* 301 */       class_310 client = class_310.method_1551();
/* 302 */       if (client == null || client.method_1548() == null) {
/*     */         return;
/*     */       }
/* 305 */       addUsername(sink, client.method_1548().method_1676());
/* 306 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void addUsername(Set<String> sink, String username) {
/* 311 */     if (username == null || username.isBlank()) {
/*     */       return;
/*     */     }
/* 314 */     sink.add(username.toLowerCase(Locale.ROOT));
/*     */   }
/*     */   
/*     */   private static boolean isUsernameChar(char c) {
/* 318 */     return (c == '_' || Character.isLetterOrDigit(c));
/*     */   }
/*     */   
/*     */   private static String readBody(InputStream stream) throws IOException {
/* 322 */     StringBuilder out = new StringBuilder();
/* 323 */     BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)); 
/* 324 */     try { char[] buf = new char[2048];
/*     */       int read;
/* 326 */       while ((read = reader.read(buf)) != -1) {
/* 327 */         out.append(buf, 0, read);
/*     */       }
/* 329 */       reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 330 */      return out.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\PhazePlayerPresence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */