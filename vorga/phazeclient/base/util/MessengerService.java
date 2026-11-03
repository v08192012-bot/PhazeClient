/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URLEncoder;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.class_310;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public final class MessengerService {
/*  32 */   private static final Logger LOG = LoggerFactory.getLogger("PhazeMessenger"); private static final int CONNECT_TIMEOUT_MS = 8000; private static final int READ_TIMEOUT_MS = 15000; private static final int MAX_LOCAL_MESSAGES = 100; private static final String USER_AGENT = "PhazeClient-Messenger/1"; private final ExecutorService io; private final ExecutorService streamIo; private final AtomicBoolean started; private final AtomicBoolean sessionRequestInFlight; private final AtomicBoolean streamStarted; private final CopyOnWriteArrayList<Conversation> conversations; private final Map<String, CopyOnWriteArrayList<Message>> messages;
/*     */   private volatile String token;
/*     */   private volatile String status;
/*     */   private volatile String selectedConversation;
/*     */   private final CopyOnWriteArrayList<Message> globalMessages;
/*     */   public static final String GLOBAL_CONVERSATION_ID = "00000000000000000000000000000001";
/*     */   
/*  39 */   private static final class Holder { private static final MessengerService INSTANCE = new MessengerService(); }
/*     */ 
/*     */   
/*     */   public static MessengerService getInstance() {
/*  43 */     return Holder.INSTANCE;
/*     */   }
/*     */   public static final class Conversation extends Record { private final String id; private final String participantId; private final String username; private final String lastText; private final long lastCreatedAt;
/*  46 */     public Conversation(String id, String participantId, String username, String lastText, long lastCreatedAt) { this.id = id; this.participantId = participantId; this.username = username; this.lastText = lastText; this.lastCreatedAt = lastCreatedAt; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/MessengerService$Conversation;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  46 */       //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$Conversation; } public String id() { return this.id; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/MessengerService$Conversation;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$Conversation; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/MessengerService$Conversation;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/base/util/MessengerService$Conversation;
/*  46 */       //   0	8	1	o	Ljava/lang/Object; } public String participantId() { return this.participantId; } public String username() { return this.username; } public String lastText() { return this.lastText; } public long lastCreatedAt() { return this.lastCreatedAt; }
/*     */      }
/*     */   public static final class Message extends Record { private final long id; private final String senderId; private final String senderUsername; private final String text; private final long createdAt;
/*  49 */     public Message(long id, String senderId, String senderUsername, String text, long createdAt) { this.id = id; this.senderId = senderId; this.senderUsername = senderUsername; this.text = text; this.createdAt = createdAt; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/MessengerService$Message;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$Message; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/MessengerService$Message;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$Message; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/MessengerService$Message;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/base/util/MessengerService$Message;
/*  49 */       //   0	8	1	o	Ljava/lang/Object; } public long id() { return this.id; } public String senderId() { return this.senderId; } public String senderUsername() { return this.senderUsername; } public String text() { return this.text; } public long createdAt() { return this.createdAt; }
/*     */      }
/*     */   private static final class HttpResult extends Record { private final int status; private final String body;
/*  52 */     private HttpResult(int status, String body) { this.status = status; this.body = body; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/MessengerService$HttpResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #52	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$HttpResult; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/MessengerService$HttpResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #52	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$HttpResult; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/MessengerService$HttpResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #52	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/base/util/MessengerService$HttpResult;
/*  52 */       //   0	8	1	o	Ljava/lang/Object; } public int status() { return this.status; } public String body() { return this.body; }
/*     */      } private MessengerService() {
/*  54 */     this.io = Executors.newSingleThreadExecutor(r -> {
/*     */           Thread thread = new Thread(r, "phaze-messenger-io");
/*     */           thread.setDaemon(true);
/*     */           return (ThreadFactory)thread;
/*     */         });
/*  59 */     this.streamIo = Executors.newSingleThreadExecutor(r -> {
/*     */           Thread thread = new Thread(r, "phaze-messenger-sse");
/*     */           thread.setDaemon(true);
/*     */           return (ThreadFactory)thread;
/*     */         });
/*  64 */     this.started = new AtomicBoolean(false);
/*  65 */     this.sessionRequestInFlight = new AtomicBoolean(false);
/*  66 */     this.streamStarted = new AtomicBoolean(false);
/*  67 */     this.conversations = new CopyOnWriteArrayList<>();
/*  68 */     this.messages = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*  71 */     this.status = "Offline";
/*     */     
/*  73 */     this.globalMessages = new CopyOnWriteArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  81 */     if (!this.started.compareAndSet(false, true))
/*  82 */       return;  this.io.execute(this::ensureSessionAndRefresh);
/*     */   }
/*     */   
/*     */   public String getStatus() {
/*  86 */     return this.status;
/*     */   }
/*     */   
/*     */   public List<Conversation> getConversations() {
/*  90 */     return Collections.unmodifiableList(new ArrayList<>(this.conversations));
/*     */   }
/*     */   
/*     */   public List<Message> getMessages(String conversationId) {
/*  94 */     return Collections.unmodifiableList(new ArrayList<>(this.messages.getOrDefault(conversationId, new CopyOnWriteArrayList<>())));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Message> getGlobalMessages() {
/*  99 */     return Collections.unmodifiableList(new ArrayList<>(this.globalMessages));
/*     */   }
/*     */   
/*     */   public void sendGlobal(String text) {
/* 103 */     sendGlobal(text, null);
/*     */   }
/*     */   
/*     */   public void sendGlobal(String text, Consumer<Boolean> completion) {
/* 107 */     if (text == null || text.isBlank())
/* 108 */       return;  MessengerTextFilter.Result filter = MessengerTextFilter.check(text);
/* 109 */     if (filter != MessengerTextFilter.Result.ALLOW) {
/* 110 */       this
/* 111 */         .status = (filter == MessengerTextFilter.Result.REPEATED_FLOOD) ? "Message blocked: flood" : "Message blocked: abusive content";
/* 112 */       if (completion != null) completion.accept(Boolean.valueOf(false)); 
/*     */       return;
/*     */     } 
/* 115 */     this.io.execute(() -> {
/*     */           if (!ensureSessionSync()) {
/*     */             if (completion != null)
/*     */               completion.accept(Boolean.valueOf(false)); 
/*     */             return;
/*     */           } 
/*     */           JsonObject body = new JsonObject();
/*     */           body.addProperty("clientMessageId", UUID.randomUUID().toString());
/*     */           body.addProperty("text", text.trim());
/*     */           HttpResult result = request("POST", "/api/chat/global/messages", body.toString(), true);
/*     */           if (result.status == 201 || result.status == 200) {
/*     */             JsonObject root = JsonParser.parseString(result.body).getAsJsonObject();
/*     */             acceptGlobalMessage(root.getAsJsonObject("message"));
/*     */             this.status = "Online";
/*     */             if (completion != null)
/*     */               completion.accept(Boolean.valueOf(true)); 
/*     */           } else {
/*     */             this.status = parseError(result.body, "Send failed");
/*     */             if (completion != null)
/*     */               completion.accept(Boolean.valueOf(false)); 
/*     */           } 
/*     */         }); } public String getSelectedConversation() {
/* 137 */     return this.selectedConversation;
/*     */   }
/*     */   
/*     */   public void selectConversation(String conversationId) {
/* 141 */     this.selectedConversation = conversationId;
/* 142 */     if (conversationId != null && !this.messages.containsKey(conversationId)) {
/* 143 */       this.io.execute(() -> loadMessages(conversationId));
/*     */     }
/*     */   }
/*     */   
/*     */   public void send(String conversationId, String text) {
/* 148 */     if (conversationId == null || text == null || text.isBlank())
/* 149 */       return;  MessengerTextFilter.Result filter = MessengerTextFilter.check(text);
/* 150 */     if (filter != MessengerTextFilter.Result.ALLOW) {
/* 151 */       this
/*     */         
/* 153 */         .status = (filter == MessengerTextFilter.Result.REPEATED_FLOOD) ? "Message blocked: flood" : "Message blocked: abusive content";
/*     */       return;
/*     */     } 
/* 156 */     String trimmed = text.trim();
/* 157 */     this.io.execute(() -> {
/*     */           if (!ensureSessionSync())
/*     */             return; 
/*     */           JsonObject body = new JsonObject();
/*     */           body.addProperty("clientMessageId", UUID.randomUUID().toString());
/*     */           body.addProperty("text", trimmed);
/*     */           HttpResult result = request("POST", "/api/chat/conversations/" + conversationId + "/messages", body.toString(), true);
/*     */           if (result.status == 201 || result.status == 200) {
/*     */             acceptMessage(conversationId, JsonParser.parseString(result.body).getAsJsonObject().getAsJsonObject("message"));
/*     */             this.status = "Online";
/*     */           } else if (result.status == 401) {
/*     */             this.token = null;
/*     */             this.status = "Session expired";
/*     */           } else {
/*     */             this.status = parseError(result.body, "Send failed");
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void addFriendByUsername(String username) {
/* 177 */     if (username == null || username.isBlank())
/* 178 */       return;  this.io.execute(() -> {
/*     */           if (!ensureSessionSync())
/*     */             return; 
/*     */           HttpResult lookup = request("GET", "/api/chat/users/lookup?username=" + URLEncoder.encode(username.trim(), StandardCharsets.UTF_8), null, true);
/*     */           if (lookup.status != 200) {
/*     */             this.status = "Player not found";
/*     */             return;
/*     */           } 
/*     */           JsonObject user = JsonParser.parseString(lookup.body).getAsJsonObject().getAsJsonObject("user");
/*     */           JsonObject body = new JsonObject();
/*     */           body.addProperty("targetUserId", user.get("id").getAsString());
/*     */           HttpResult result = request("POST", "/api/chat/friends/requests", body.toString(), true);
/* 190 */           this.status = (result.status == 201 || result.status == 200) ? "Friend request sent" : parseError(result.body, "Friend request failed");
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 196 */     this.io.shutdownNow();
/* 197 */     this.streamIo.shutdownNow();
/*     */   }
/*     */   
/*     */   private void ensureSessionAndRefresh() {
/* 201 */     if (!ensureSessionSync())
/* 202 */       return;  refreshConversations();
/* 203 */     loadGlobalMessages();
/* 204 */     startStream();
/*     */   }
/*     */   
/*     */   private void loadGlobalMessages() {
/* 208 */     HttpResult result = request("GET", "/api/chat/global/messages?limit=100", null, true);
/* 209 */     if (result.status != 200)
/* 210 */       return;  JsonArray array = JsonParser.parseString(result.body).getAsJsonObject().getAsJsonArray("messages");
/* 211 */     this.globalMessages.clear();
/* 212 */     for (JsonElement element : array) acceptGlobalMessage(element.getAsJsonObject()); 
/*     */   }
/*     */   
/*     */   private boolean ensureSessionSync() {
/* 216 */     if (this.token != null && !this.token.isBlank()) return true; 
/* 217 */     if (!this.sessionRequestInFlight.compareAndSet(false, true)) {
/* 218 */       for (int i = 0; i < 50 && (this.token == null || this.token.isBlank()); i++) {
/*     */         try {
/* 220 */           Thread.sleep(100L);
/* 221 */         } catch (InterruptedException interrupted) {
/* 222 */           Thread.currentThread().interrupt();
/* 223 */           return false;
/*     */         } 
/*     */       } 
/* 226 */       return (this.token != null && !this.token.isBlank());
/*     */     } 
/*     */     try {
/* 229 */       String base = RemoteRulesService.getInstance().getApiBase();
/* 230 */       if (base == null || base.isBlank()) {
/* 231 */         this.status = "Messenger disabled";
/* 232 */         return false;
/*     */       } 
/* 234 */       JsonObject body = new JsonObject();
/* 235 */       body.addProperty("clientId", RemoteRulesService.getInstance().getClientId());
/* 236 */       body.addProperty("username", currentUsername());
/* 237 */       HttpResult result = request("POST", "/api/chat/session", body.toString(), false);
/* 238 */       if (result.status != 201) {
/* 239 */         this.status = parseError(result.body, "Session unavailable");
/* 240 */         return false;
/*     */       } 
/* 242 */       JsonObject root = JsonParser.parseString(result.body).getAsJsonObject();
/* 243 */       this.token = root.get("token").getAsString();
/* 244 */       this.status = "Online";
/* 245 */       return true;
/* 246 */     } catch (Throwable throwable) {
/* 247 */       this.status = "Network unavailable";
/* 248 */       LOG.debug("Messenger session failed", throwable);
/* 249 */       return false;
/*     */     } finally {
/* 251 */       this.sessionRequestInFlight.set(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void refreshConversations() {
/* 256 */     HttpResult result = request("GET", "/api/chat/conversations", null, true);
/* 257 */     if (result.status != 200) {
/* 258 */       if (result.status == 401) this.token = null; 
/* 259 */       this.status = parseError(result.body, "Conversations unavailable");
/*     */       return;
/*     */     } 
/* 262 */     JsonArray array = JsonParser.parseString(result.body).getAsJsonObject().getAsJsonArray("conversations");
/* 263 */     this.conversations.clear();
/* 264 */     for (JsonElement element : array) {
/* 265 */       JsonObject conversation = element.getAsJsonObject();
/* 266 */       JsonObject participant = conversation.getAsJsonObject("participant");
/*     */       
/* 268 */       JsonObject last = conversation.get("lastMessage").isJsonNull() ? null : conversation.getAsJsonObject("lastMessage");
/* 269 */       this.conversations.add(new Conversation(conversation
/* 270 */             .get("id").getAsString(), participant
/* 271 */             .get("id").getAsString(), 
/* 272 */             participant.get("username").isJsonNull() ? "Unknown" : participant.get("username").getAsString(), 
/* 273 */             (last == null) ? "No messages yet" : last.get("text").getAsString(), 
/* 274 */             (last == null) ? 0L : last.get("createdAt").getAsLong()));
/*     */     } 
/*     */     
/* 277 */     if (this.selectedConversation == null && !this.conversations.isEmpty()) {
/* 278 */       selectConversation(((Conversation)this.conversations.get(0)).id());
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadMessages(String conversationId) {
/* 283 */     HttpResult result = request("GET", "/api/chat/conversations/" + conversationId + "/messages?limit=100", null, true);
/* 284 */     if (result.status != 200)
/* 285 */       return;  JsonArray array = JsonParser.parseString(result.body).getAsJsonObject().getAsJsonArray("messages");
/* 286 */     CopyOnWriteArrayList<Message> target = this.messages.computeIfAbsent(conversationId, ignored -> new CopyOnWriteArrayList());
/* 287 */     target.clear();
/* 288 */     for (JsonElement element : array) acceptMessage(conversationId, element.getAsJsonObject()); 
/*     */   }
/*     */   
/*     */   private void startStream() {
/* 292 */     if (!this.streamStarted.compareAndSet(false, true))
/* 293 */       return;  this.streamIo.execute(() -> {
/*     */           for (long backoff = 5000L; !Thread.currentThread().isInterrupted(); backoff = Math.min(300000L, backoff * 2L)) {
/*     */             try {
/*     */               listenStream();
/*     */               
/*     */               backoff = 5000L;
/* 299 */             } catch (Throwable throwable) {
/*     */               this.status = "Messenger reconnecting";
/*     */               LOG.debug("Messenger SSE ended", throwable);
/*     */             } 
/*     */             try {
/*     */               Thread.sleep(backoff + (long)(Math.random() * 1000.0D));
/* 305 */             } catch (InterruptedException interrupted) {
/*     */               Thread.currentThread().interrupt();
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void listenStream() throws Exception {
/* 315 */     if (!ensureSessionSync()) throw new IllegalStateException("no chat session");
/*     */ 
/*     */     
/* 318 */     HttpURLConnection connection = (HttpURLConnection)URI.create(RemoteRulesService.getInstance().getApiBase() + "/api/chat/events").toURL().openConnection();
/*     */     
/* 320 */     try { connection.setRequestMethod("GET");
/* 321 */       connection.setConnectTimeout(8000);
/* 322 */       connection.setReadTimeout(40000);
/* 323 */       connection.setRequestProperty("Authorization", "Bearer " + this.token);
/* 324 */       connection.setRequestProperty("Accept", "text/event-stream");
/* 325 */       connection.setRequestProperty("Cache-Control", "no-cache");
/* 326 */       connection.setRequestProperty("User-Agent", "PhazeClient-Messenger/1");
/* 327 */       if (connection.getResponseCode() != 200) throw new IllegalStateException("SSE " + connection.getResponseCode()); 
/* 328 */       this.status = "Online";
/*     */       
/* 330 */       BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)); 
/* 331 */       try { String event = null;
/* 332 */         String data = null;
/*     */         String line;
/* 334 */         while ((line = reader.readLine()) != null) {
/* 335 */           if (line.isEmpty()) {
/* 336 */             if ("message".equals(event) && data != null) {
/* 337 */               JsonObject root = JsonParser.parseString(data).getAsJsonObject();
/* 338 */               String conversationId = root.get("conversationId").getAsString();
/* 339 */               if ("00000000000000000000000000000001".equals(conversationId)) {
/* 340 */                 acceptGlobalMessage(root.getAsJsonObject("message"));
/*     */               } else {
/* 342 */                 acceptMessage(conversationId, root.getAsJsonObject("message"));
/*     */               } 
/*     */             } 
/* 345 */             event = null;
/* 346 */             data = null; continue;
/* 347 */           }  if (line.startsWith("event:")) {
/* 348 */             event = line.substring(6).trim(); continue;
/* 349 */           }  if (line.startsWith("data:")) {
/* 350 */             String chunk = line.substring(5).trim();
/* 351 */             data = (data == null) ? chunk : (data + data);
/*     */           } 
/*     */         } 
/* 354 */         reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*     */        }
/* 356 */     finally { connection.disconnect(); }
/*     */   
/*     */   }
/*     */   
/*     */   private void acceptMessage(String conversationId, JsonObject message) {
/* 361 */     if (message == null || !message.has("id"))
/* 362 */       return;  CopyOnWriteArrayList<Message> target = this.messages.computeIfAbsent(conversationId, ignored -> new CopyOnWriteArrayList());
/* 363 */     long id = message.get("id").getAsLong();
/* 364 */     target.removeIf(existing -> (existing.id() == id));
/* 365 */     target.add(new Message(id, message
/*     */           
/* 367 */           .get("senderId").getAsString(), 
/* 368 */           message.get("senderUsername").isJsonNull() ? "Unknown" : message.get("senderUsername").getAsString(), message
/* 369 */           .get("text").getAsString(), message
/* 370 */           .get("createdAt").getAsLong()));
/*     */     
/* 372 */     for (; target.size() > 100; target.remove(0));
/*     */   }
/*     */   
/*     */   private void acceptGlobalMessage(JsonObject message) {
/* 376 */     if (message == null || !message.has("id"))
/* 377 */       return;  long id = message.get("id").getAsLong();
/* 378 */     this.globalMessages.removeIf(existing -> (existing.id() == id));
/* 379 */     this.globalMessages.add(new Message(id, message
/*     */           
/* 381 */           .get("senderId").getAsString(), 
/* 382 */           message.get("senderUsername").isJsonNull() ? "Unknown" : message.get("senderUsername").getAsString(), message
/* 383 */           .get("text").getAsString(), message
/* 384 */           .get("createdAt").getAsLong()));
/*     */     
/* 386 */     for (; this.globalMessages.size() > 100; this.globalMessages.remove(0));
/*     */   }
/*     */   
/*     */   private HttpResult request(String method, String path, String body, boolean authenticated) {
/*     */     try {
/* 391 */       String base = RemoteRulesService.getInstance().getApiBase();
/* 392 */       HttpURLConnection connection = (HttpURLConnection)URI.create(base + base).toURL().openConnection();
/*     */       try {
/* 394 */         connection.setRequestMethod(method);
/* 395 */         connection.setConnectTimeout(8000);
/* 396 */         connection.setReadTimeout(15000);
/* 397 */         connection.setRequestProperty("Accept", "application/json");
/* 398 */         connection.setRequestProperty("User-Agent", "PhazeClient-Messenger/1");
/* 399 */         if (authenticated && this.token != null) connection.setRequestProperty("Authorization", "Bearer " + this.token); 
/* 400 */         if (body != null) {
/* 401 */           connection.setDoOutput(true);
/* 402 */           connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 403 */           byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
/* 404 */           OutputStream output = connection.getOutputStream(); 
/* 405 */           try { output.write(bytes);
/* 406 */             if (output != null) output.close();  } catch (Throwable throwable) { if (output != null)
/*     */               try { output.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 408 */         }  int statusCode = connection.getResponseCode();
/* 409 */         InputStream input = (statusCode >= 400) ? connection.getErrorStream() : connection.getInputStream();
/* 410 */         String response = (input == null) ? "{}" : new String(input.readAllBytes(), StandardCharsets.UTF_8);
/* 411 */         return new HttpResult(statusCode, response);
/*     */       } finally {
/* 413 */         connection.disconnect();
/*     */       } 
/* 415 */     } catch (Throwable throwable) {
/* 416 */       LOG.debug("Messenger request failed: {} {}", new Object[] { method, path, throwable });
/* 417 */       return new HttpResult(599, "{}");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String currentUsername() {
/*     */     try {
/* 423 */       class_310 client = class_310.method_1551();
/* 424 */       return (client.method_1548() == null) ? "Player" : client.method_1548().method_1676();
/* 425 */     } catch (Throwable ignored) {
/* 426 */       return "Player";
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String parseError(String body, String fallback) {
/*     */     try {
/* 432 */       JsonObject object = JsonParser.parseString(body).getAsJsonObject();
/* 433 */       if (object.has("reason")) return "Message blocked: " + object.get("reason").getAsString(); 
/* 434 */       if (object.has("error")) {
/* 435 */         String error = object.get("error").getAsString();
/* 436 */         return "not_found".equals(error) ? "Chat server is outdated" : error;
/*     */       } 
/* 438 */       return fallback;
/* 439 */     } catch (Throwable ignored) {
/* 440 */       return fallback;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\MessengerService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */