/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import vorga.phazeclient.implement.cosmetics.CosmeticsSyncService;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PhazeEventService
/*     */ {
/*  59 */   private static final Logger LOG = LoggerFactory.getLogger("PhazeEvents");
/*     */   
/*     */   private static final int CONNECT_TIMEOUT_MS = 15000;
/*     */   private static final int READ_TIMEOUT_MS = 40000;
/*     */   private static final long RECONNECT_MIN_MS = 5000L;
/*     */   private static final long RECONNECT_MAX_MS = 300000L;
/*     */   private final ExecutorService scheduler;
/*     */   private final AtomicBoolean started;
/*     */   private volatile boolean connected;
/*     */   private volatile long reconnectDelayMs;
/*     */   
/*     */   private static final class Holder
/*     */   {
/*  72 */     static final PhazeEventService INSTANCE = new PhazeEventService();
/*     */   }
/*     */   
/*     */   public static PhazeEventService getInstance() {
/*  76 */     return Holder.INSTANCE;
/*     */   }
/*     */   private PhazeEventService() {
/*  79 */     this
/*  80 */       .scheduler = Executors.newSingleThreadExecutor(r -> {
/*     */           Thread t = new Thread(r, "phaze-events");
/*     */           
/*     */           t.setDaemon(true);
/*     */           return (ThreadFactory)t;
/*     */         });
/*  86 */     this.started = new AtomicBoolean(false);
/*  87 */     this.connected = false;
/*  88 */     this.reconnectDelayMs = 5000L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/*  94 */     return this.connected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 103 */     RemoteRulesService rules = RemoteRulesService.getInstance();
/* 104 */     if (rules.getApiBase() == null || rules.getApiBase().isEmpty()) {
/*     */       return;
/*     */     }
/* 107 */     if (!this.started.compareAndSet(false, true)) {
/*     */       return;
/*     */     }
/* 110 */     this.scheduler.execute(this::runForever);
/*     */   }
/*     */   
/*     */   private void runForever() {
/*     */     while (true) {
/*     */       try {
/* 116 */         listen();
/*     */       
/*     */       }
/* 119 */       catch (Throwable t) {
/* 120 */         LOG.debug("event stream ended: {}", t.toString());
/*     */       } 
/* 122 */       this.connected = false;
/* 123 */       long delay = this.reconnectDelayMs;
/*     */ 
/*     */       
/* 126 */       long jitter = (long)(delay * 0.25D * Math.random());
/*     */       try {
/* 128 */         Thread.sleep(delay + jitter);
/* 129 */       } catch (InterruptedException interrupted) {
/* 130 */         Thread.currentThread().interrupt();
/*     */         return;
/*     */       } 
/* 133 */       this.reconnectDelayMs = Math.min(300000L, delay * 2L);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void listen() throws Exception {
/* 138 */     RemoteRulesService rules = RemoteRulesService.getInstance();
/*     */ 
/*     */     
/* 141 */     String url = rules.getApiBase() + "/api/events?clientId=" + rules.getApiBase();
/*     */     
/* 143 */     HttpURLConnection conn = (HttpURLConnection)URI.create(url).toURL().openConnection();
/*     */     
/* 145 */     try { conn.setRequestMethod("GET");
/* 146 */       conn.setConnectTimeout(15000);
/* 147 */       conn.setReadTimeout(40000);
/* 148 */       conn.setRequestProperty("Accept", "text/event-stream");
/* 149 */       conn.setRequestProperty("Cache-Control", "no-cache");
/* 150 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       conn.setInstanceFollowRedirects(true);
/*     */       
/* 157 */       int status = conn.getResponseCode();
/* 158 */       if (status != 200) {
/* 159 */         LOG.debug("event stream returned {}", Integer.valueOf(status));
/*     */         
/*     */         return;
/*     */       } 
/* 163 */       this.connected = true;
/*     */ 
/*     */ 
/*     */       
/* 167 */       this.reconnectDelayMs = 5000L;
/*     */ 
/*     */       
/* 170 */       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)); 
/* 171 */       try { String event = null;
/* 172 */         String data = null;
/*     */         String line;
/* 174 */         while ((line = reader.readLine()) != null) {
/* 175 */           if (line.isEmpty()) {
/* 176 */             if (event != null) {
/* 177 */               dispatch(event, data);
/*     */             }
/* 179 */             event = null;
/* 180 */             data = null;
/*     */             continue;
/*     */           } 
/* 183 */           if (line.startsWith(":")) {
/*     */             continue;
/*     */           }
/* 186 */           if (line.startsWith("event:")) {
/* 187 */             event = line.substring(6).trim(); continue;
/* 188 */           }  if (line.startsWith("data:")) {
/* 189 */             String chunk = line.substring(5).trim();
/* 190 */             data = (data == null) ? chunk : (data + data);
/*     */           } 
/*     */         } 
/* 193 */         reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*     */        }
/* 195 */     finally { this.connected = false;
/* 196 */       conn.disconnect(); }
/*     */   
/*     */   }
/*     */   private void dispatch(String event, String data) {
/*     */     try {
/*     */       JsonElement parsed;
/* 202 */       switch (event) {
/*     */         case "rules":
/* 204 */           LOG.info("rules changed remotely, refreshing");
/* 205 */           RemoteRulesService.getInstance().requestRefresh(); return;
/*     */         case "kick":
/* 207 */           handleKick(data); return;
/*     */         case "announcement":
/* 209 */           parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 210 */           if (parsed.isJsonObject() && parsed.getAsJsonObject().has("announcement")) {
/* 211 */             JsonElement payload = parsed.getAsJsonObject().get("announcement");
/* 212 */             if (payload.isJsonObject()) {
/* 213 */               PhazeAnnouncements.accept(payload.getAsJsonObject());
/*     */             }
/*     */           } 
/*     */           return;
/*     */         case "announcement_expired":
/* 218 */           parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 219 */           if (parsed.isJsonObject() && parsed.getAsJsonObject().has("id")) {
/* 220 */             PhazeAnnouncements.dismiss(parsed.getAsJsonObject().get("id").getAsInt());
/*     */           }
/*     */           return;
/*     */         case "cosmetic":
/* 224 */           parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 225 */           if (parsed.isJsonObject())
/*     */           {
/* 227 */             CosmeticsSyncService.getInstance()
/* 228 */               .acceptEvent(parsed.getAsJsonObject());
/*     */           }
/*     */           return;
/*     */         case "pulse_cosmetic":
/* 232 */           parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 233 */           if (parsed.isJsonObject())
/*     */           {
/* 235 */             CosmeticsSyncService.getInstance()
/* 236 */               .acceptPulseEvent(parsed.getAsJsonObject());
/*     */           }
/*     */           return;
/*     */         case "pulse_graffiti":
/* 240 */           parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 241 */           if (parsed.isJsonObject())
/*     */           {
/* 243 */             CosmeticsSyncService.getInstance()
/* 244 */               .acceptPulseGraffitiEvent(parsed.getAsJsonObject());
/*     */           }
/*     */           return;
/*     */         case "hello":
/* 248 */           CosmeticsSyncService.getInstance()
/* 249 */             .requestRefresh(); return;
/*     */         case "ping":
/* 251 */           parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 252 */           if (parsed.isJsonObject())
/*     */           {
/* 254 */             CosmeticsSyncService.getInstance()
/* 255 */               .acceptPushCheckpoint(parsed.getAsJsonObject()); } 
/*     */           return;
/*     */       } 
/* 258 */       LOG.debug("unknown event '{}'", event);
/*     */     }
/* 260 */     catch (Throwable t) {
/* 261 */       LOG.warn("failed to handle event '{}': {}", event, t.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleKick(String data) {
/* 266 */     String message = Lang.t("status.kicked_by_admin");
/*     */     try {
/* 268 */       JsonElement parsed = JsonParser.parseString((data == null) ? "{}" : data);
/* 269 */       if (parsed.isJsonObject()) {
/* 270 */         JsonObject obj = parsed.getAsJsonObject();
/* 271 */         if (obj.has("message") && !obj.get("message").isJsonNull()) {
/* 272 */           String supplied = obj.get("message").getAsString().trim();
/* 273 */           if (!supplied.isEmpty()) {
/* 274 */             message = supplied;
/*     */           }
/*     */         } 
/*     */       } 
/* 278 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     LOG.info("kick received: {}", message);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     disconnectNow(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void disconnectNow(String message) {
/* 298 */     class_310 client = class_310.method_1551();
/* 299 */     if (client == null) {
/*     */       return;
/*     */     }
/* 302 */     client.execute(() -> {
/*     */           try {
/*     */             if (client.method_1542()) {
/*     */               return;
/*     */             }
/*     */             if (client.method_1562() == null) {
/*     */               return;
/*     */             }
/*     */             client.method_1562().method_48296().method_10747((class_2561)class_2561.method_43470(message));
/* 311 */           } catch (Throwable t) {
/*     */             LOG.warn("disconnect failed: {}", t.toString());
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\PhazeEventService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */