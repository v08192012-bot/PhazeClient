/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import vorga.phazeclient.base.util.HardwareId;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.RemoteRulesService;
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
/*     */ 
/*     */ 
/*     */ public final class ConfigShareApi
/*     */ {
/*     */   private static final int CONNECT_TIMEOUT_MS = 15000;
/*     */   private static final int READ_TIMEOUT_MS = 30000;
/*     */   private static final int MAX_RESPONSE_BYTES = 524288;
/*     */   private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
/*  53 */   private static volatile String lastError = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLastError() {
/*  58 */     return lastError;
/*     */   }
/*     */   
/*     */   public static String upload(String share, String author, Integer maxUses, String name) {
/*  62 */     lastError = "";
/*     */     
/*  64 */     if (share == null || !share.startsWith("PHAZE1:")) {
/*  65 */       lastError = Lang.t("status.cloud_bad_payload");
/*  66 */       return null;
/*     */     } 
/*  68 */     String base = apiBase();
/*  69 */     if (base == null) {
/*  70 */       lastError = Lang.t("status.cloud_disabled_detail");
/*  71 */       return null;
/*     */     } 
/*     */     
/*  74 */     JsonObject body = new JsonObject();
/*  75 */     body.addProperty("payload", share);
/*  76 */     if (author != null && !author.isBlank()) {
/*  77 */       body.addProperty("author", author.trim());
/*     */     }
/*  79 */     if (name != null && !name.isBlank()) {
/*  80 */       body.addProperty("name", name.trim());
/*     */     }
/*  82 */     if (maxUses != null && maxUses.intValue() > 0) {
/*  83 */       body.addProperty("maxUses", maxUses);
/*     */     }
/*  85 */     body.addProperty("clientId", RemoteRulesService.getInstance().getClientId());
/*  86 */     String hwid = HardwareId.get();
/*  87 */     if (hwid != null) {
/*  88 */       body.addProperty("hwid", hwid);
/*     */     }
/*     */     
/*     */     try {
/*  92 */       Response response = send(base + "/api/configs", "POST", body.toString());
/*  93 */       if (response.status == 201) {
/*  94 */         JsonObject json = parseObject(response.body);
/*  95 */         String id = (json != null && json.has("id")) ? json.get("id").getAsString() : null;
/*  96 */         if (id == null || id.isEmpty()) {
/*  97 */           lastError = Lang.t("status.cloud_bad_response");
/*  98 */           return null;
/*     */         } 
/* 100 */         return id;
/*     */       } 
/* 102 */       lastError = describeUploadFailure(response);
/* 103 */       return null;
/* 104 */     } catch (IOException e) {
/* 105 */       lastError = Lang.t("status.cloud_network_error");
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String upload(String share, String author, Integer maxUses) {
/* 111 */     return upload(share, author, maxUses, null);
/*     */   }
/*     */   
/*     */   public static String upload(String share) {
/* 115 */     return upload(share, null, null, null);
/*     */   }
/*     */   
/*     */   public static DownloadResult downloadFull(String code) {
/* 119 */     lastError = "";
/*     */     
/* 121 */     if (code == null || code.isBlank()) {
/* 122 */       lastError = Lang.t("status.cloud_bad_code");
/* 123 */       return null;
/*     */     } 
/* 125 */     String base = apiBase();
/* 126 */     if (base == null) {
/* 127 */       lastError = Lang.t("status.cloud_disabled_detail");
/* 128 */       return null;
/*     */     } 
/*     */     
/* 131 */     String id = code.trim().toLowerCase();
/*     */     try {
/* 133 */       Response response = send(base + "/api/configs/" + base, 
/* 134 */           "GET", null);
/*     */ 
/*     */       
/* 137 */       if (response.status == 200) {
/* 138 */         JsonObject json = parseObject(response.body);
/* 139 */         if (json == null || !json.has("payload")) {
/* 140 */           lastError = Lang.t("status.cloud_bad_response");
/* 141 */           return null;
/*     */         } 
/* 143 */         String payload = json.get("payload").getAsString();
/*     */ 
/*     */         
/* 146 */         String name = (json.has("name") && !json.get("name").isJsonNull()) ? json.get("name").getAsString() : null;
/* 147 */         return new DownloadResult(payload, name);
/*     */       } 
/* 149 */       lastError = describeDownloadFailure(response);
/* 150 */       return null;
/* 151 */     } catch (IOException e) {
/* 152 */       lastError = Lang.t("status.cloud_network_error");
/* 153 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String download(String code) {
/* 158 */     DownloadResult result = downloadFull(code);
/* 159 */     return (result == null) ? null : result.payload;
/*     */   }
/*     */   
/*     */   private static String describeUploadFailure(Response response) {
/* 163 */     if (response.status == 429) {
/* 164 */       JsonObject json = parseObject(response.body);
/*     */ 
/*     */       
/* 167 */       String error = (json != null && json.has("error")) ? json.get("error").getAsString() : "";
/* 168 */       if ("quota_exceeded".equals(error)) {
/* 169 */         int limit = json.has("limit") ? json.get("limit").getAsInt() : 5;
/* 170 */         return Lang.t("status.cloud_quota_exceeded").replace("%d", String.valueOf(limit));
/*     */       } 
/* 172 */       return Lang.t("status.cloud_rate_limited");
/*     */     } 
/* 174 */     if (response.status == 400) {
/* 175 */       return Lang.t("status.cloud_bad_payload");
/*     */     }
/* 177 */     return Lang.t("status.cloud_server_error").replace("%d", String.valueOf(response.status));
/*     */   }
/*     */   
/*     */   private static String describeDownloadFailure(Response response) {
/* 181 */     switch (response.status) {
/*     */       case 400:
/* 183 */         return Lang.t("status.cloud_bad_code");
/*     */       case 404:
/* 185 */         return Lang.t("status.cloud_not_found");
/*     */ 
/*     */       
/*     */       case 410:
/* 189 */         return Lang.t("status.cloud_exhausted");
/*     */       case 429:
/* 191 */         return Lang.t("status.cloud_rate_limited");
/*     */     } 
/* 193 */     return Lang.t("status.cloud_server_error")
/* 194 */       .replace("%d", String.valueOf(response.status));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String apiBase() {
/* 199 */     String base = RemoteRulesService.getInstance().getApiBase();
/* 200 */     return (base == null || base.isEmpty()) ? null : base;
/*     */   }
/*     */   
/*     */   private static JsonObject parseObject(String raw) {
/*     */     try {
/* 205 */       JsonElement parsed = JsonParser.parseString(raw);
/* 206 */       return parsed.isJsonObject() ? parsed.getAsJsonObject() : null;
/* 207 */     } catch (Throwable t) {
/* 208 */       return null;
/*     */     } 
/*     */   }
/*     */   private static final class Response extends Record { private final int status; private final String body;
/* 212 */     private Response(int status, String body) { this.status = status; this.body = body; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/other/ConfigShareApi$Response;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #212	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 212 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/ConfigShareApi$Response; } public int status() { return this.status; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/other/ConfigShareApi$Response;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #212	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 212 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/ConfigShareApi$Response; } public String body() { return this.body; } public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/other/ConfigShareApi$Response;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #212	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/ConfigShareApi$Response;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     } } private static Response send(String url, String method, String jsonBody) throws IOException {
/* 215 */     HttpURLConnection conn = (HttpURLConnection)URI.create(url).toURL().openConnection();
/*     */     try {
/* 217 */       conn.setRequestMethod(method);
/* 218 */       conn.setConnectTimeout(15000);
/* 219 */       conn.setReadTimeout(30000);
/* 220 */       conn.setRequestProperty("Accept", "application/json");
/* 221 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/* 222 */       conn.setInstanceFollowRedirects(true);
/*     */       
/* 224 */       if (jsonBody != null) {
/* 225 */         conn.setRequestProperty("Content-Type", "application/json");
/* 226 */         conn.setDoOutput(true);
/* 227 */         OutputStream out = conn.getOutputStream(); 
/* 228 */         try { out.write(jsonBody.getBytes(StandardCharsets.UTF_8));
/* 229 */           if (out != null) out.close();  } catch (Throwable throwable) { if (out != null)
/*     */             try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */       
/* 232 */       }  int status = conn.getResponseCode();
/*     */ 
/*     */       
/* 235 */       InputStream stream = (status >= 400) ? conn.getErrorStream() : conn.getInputStream();
/* 236 */       return new Response(status, (stream == null) ? "" : readBody(stream));
/*     */     } finally {
/* 238 */       conn.disconnect();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String readBody(InputStream stream) throws IOException {
/* 243 */     StringBuilder out = new StringBuilder();
/* 244 */     BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
/*     */     
/* 246 */     try { char[] buf = new char[4096];
/*     */       int read;
/* 248 */       while ((read = reader.read(buf)) != -1) {
/* 249 */         out.append(buf, 0, read);
/* 250 */         if (out.length() > 524288) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 257 */       reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 258 */      return out.toString();
/*     */   }
/*     */   
/*     */   public static final class DownloadResult {
/*     */     public final String payload;
/*     */     public final String name;
/*     */     
/*     */     public DownloadResult(String payload, String name) {
/* 266 */       this.payload = payload;
/* 267 */       this.name = name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\ConfigShareApi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */