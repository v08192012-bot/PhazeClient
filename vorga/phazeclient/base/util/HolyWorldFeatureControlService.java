/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
/*     */ import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
/*     */ import net.fabricmc.fabric.api.networking.v1.PacketSender;
/*     */ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_634;
/*     */ import net.minecraft.class_8710;
/*     */ import net.minecraft.class_9129;
/*     */ import net.minecraft.class_9139;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.ModuleDetailComponent;
/*     */ 
/*     */ public final class HolyWorldFeatureControlService {
/*     */   private static final String REQUEST_METHOD_NAME = "checkFeatures";
/*     */   private static final String HOLYWORLD_SEGMENT = "holyworld";
/*     */   private static final long REQUEST_COOLDOWN_MS = 10000L;
/*     */   private static final String FIXED_CLIENT_ID = "phaze-client";
/*     */   private static final long RETRY_INTERVAL_MS = 10000L;
/*     */   private static final int MAX_RETRIES = 5;
/*  45 */   private final AtomicBoolean initialized = new AtomicBoolean(false);
/*  46 */   private final Set<String> disabledFeatures = ConcurrentHashMap.newKeySet();
/*  47 */   private final AtomicLong lastRequestAt = new AtomicLong(0L);
/*     */   
/*  49 */   private final AtomicBoolean receivedInitialResponse = new AtomicBoolean(false);
/*  50 */   private final AtomicReference<String> pendingRequestId = new AtomicReference<>(null);
/*  51 */   private final AtomicInteger retryCount = new AtomicInteger(0); private final ScheduledExecutorService scheduler; private ScheduledFuture<?> retryFuture;
/*     */   private HolyWorldFeatureControlService() {
/*  53 */     this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
/*     */           Thread thread = new Thread(r, "HolyWorld-FeatureControl");
/*     */           thread.setDaemon(true);
/*     */           return thread;
/*     */         });
/*  58 */     this.retryFuture = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Holder
/*     */   {
/*  64 */     private static final HolyWorldFeatureControlService INSTANCE = new HolyWorldFeatureControlService();
/*     */   }
/*     */   
/*     */   public static HolyWorldFeatureControlService getInstance() {
/*  68 */     return Holder.INSTANCE;
/*     */   }
/*     */   
/*     */   public void init() {
/*  72 */     if (!this.initialized.compareAndSet(false, true)) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     PayloadTypeRegistry.playC2S().register(HolyWorldPayload.ID, HolyWorldPayload.CODEC);
/*  77 */     PayloadTypeRegistry.playS2C().register(HolyWorldPayload.ID, HolyWorldPayload.CODEC);
/*  78 */     ClientPlayNetworking.registerGlobalReceiver(HolyWorldPayload.ID, (payload, context) -> {
/*     */           String json = payload.json();
/*     */           
/*     */           context.client().execute(());
/*     */         });
/*  83 */     ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
/*     */           resetCache();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           client.execute(());
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  94 */     ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> resetCache());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void resetCache() {
/* 100 */     stopRetryTask();
/* 101 */     this.disabledFeatures.clear();
/* 102 */     this.receivedInitialResponse.set(false);
/* 103 */     this.pendingRequestId.set(null);
/* 104 */     this.retryCount.set(0);
/*     */   }
/*     */   
/*     */   public void onHostChange() {
/* 108 */     if (isHolyWorldServer()) {
/* 109 */       if (!this.receivedInitialResponse.get() && this.pendingRequestId.get() == null) {
/* 110 */         requestServerRules("");
/* 111 */         startRetryTask();
/* 112 */         enforceServerLocks();
/*     */       } 
/*     */     } else {
/* 115 */       resetCache();
/*     */     } 
/*     */   }
/*     */   private synchronized void startRetryTask() {
/*     */     long initialDelay;
/* 120 */     stopRetryTask();
/* 121 */     this.retryCount.set(0);
/*     */     
/* 123 */     long now = System.currentTimeMillis();
/* 124 */     long elapsed = now - this.lastRequestAt.get();
/*     */     
/* 126 */     if (elapsed < 10000L) {
/* 127 */       initialDelay = 10000L - elapsed + 100L;
/*     */     } else {
/* 129 */       initialDelay = 10000L;
/*     */     } 
/*     */     
/* 132 */     this.retryFuture = this.scheduler.scheduleWithFixedDelay(() -> {
/*     */           try {
/*     */             if (!isHolyWorldServer() || this.receivedInitialResponse.get()) {
/*     */               stopRetryTask();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             if (this.retryCount.incrementAndGet() > 5) {
/*     */               stopRetryTask();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             class_310 mc = class_310.method_1551();
/*     */             if (mc != null) {
/*     */               mc.execute(());
/*     */             }
/* 150 */           } catch (Throwable throwable) {}
/*     */         }initialDelay, 10000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void stopRetryTask() {
/* 156 */     if (this.retryFuture != null) {
/* 157 */       this.retryFuture.cancel(false);
/* 158 */       this.retryFuture = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFeatureDisabled(String featureName) {
/* 163 */     if (!isHolyWorldServer()) {
/* 164 */       return false;
/*     */     }
/* 166 */     if (!this.receivedInitialResponse.get()) {
/* 167 */       return true;
/*     */     }
/* 169 */     return this.disabledFeatures.contains(normalize(featureName));
/*     */   }
/*     */   
/*     */   public Set<String> getDisabledFeatures() {
/* 173 */     return Collections.unmodifiableSet(this.disabledFeatures);
/*     */   }
/*     */   
/*     */   public void requestFeatureStatus(String featureName) {
/* 177 */     requestServerRules(featureName);
/*     */   }
/*     */   
/*     */   private boolean requestServerRules(String featureName) {
/* 181 */     if (!isHolyWorldServer()) {
/* 182 */       return false;
/*     */     }
/*     */     
/* 185 */     long now = System.currentTimeMillis();
/* 186 */     long previous = this.lastRequestAt.get();
/* 187 */     if (now - previous < 10000L) {
/* 188 */       return false;
/*     */     }
/* 190 */     if (!this.lastRequestAt.compareAndSet(previous, now)) {
/* 191 */       return false;
/*     */     }
/*     */     
/* 194 */     String requestId = UUID.randomUUID().toString();
/* 195 */     this.pendingRequestId.set(requestId);
/*     */     
/* 197 */     JsonObject request = new JsonObject();
/* 198 */     request.addProperty("method", "checkFeatures");
/* 199 */     request.addProperty("id", requestId);
/*     */     
/* 201 */     JsonObject payload = new JsonObject();
/* 202 */     payload.addProperty("client", "phaze-client");
/* 203 */     payload.add("features", (JsonElement)buildFeatureArray(featureName));
/* 204 */     request.add("payload", (JsonElement)payload);
/*     */     
/*     */     try {
/* 207 */       ClientPlayNetworking.send(new HolyWorldPayload(request.toString()));
/* 208 */       return true;
/* 209 */     } catch (Throwable ignored) {
/* 210 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handlePayload(String json) {
/* 215 */     if (!isHolyWorldServer()) {
/* 216 */       resetCache();
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 221 */       JsonElement parsed = JsonParser.parseString(json);
/* 222 */       if (!parsed.isJsonObject()) {
/*     */         return;
/*     */       }
/*     */       
/* 226 */       JsonObject object = parsed.getAsJsonObject();
/* 227 */       boolean isRpcResponse = object.has("ok");
/*     */       
/* 229 */       boolean isPushUpdate = (object.has("event") || object.has("type") || object.has("action") || (!isRpcResponse && object.has("payload")));
/*     */       
/* 231 */       Set<String> nextDisabled = null;
/*     */       
/* 233 */       if (isRpcResponse) {
/* 234 */         if (!object.get("ok").getAsBoolean()) {
/*     */           return;
/*     */         }
/* 237 */         String respId = getString(object, "id");
/* 238 */         String expectedId = this.pendingRequestId.get();
/* 239 */         if (expectedId == null || !expectedId.equals(respId)) {
/*     */           return;
/*     */         }
/* 242 */         this.pendingRequestId.set(null);
/* 243 */         nextDisabled = parseBlocklist(object.get("payload"));
/* 244 */       } else if (isPushUpdate) {
/* 245 */         nextDisabled = parseBlocklist(object.get("payload"));
/*     */       } 
/*     */       
/* 248 */       if (nextDisabled != null) {
/* 249 */         this.disabledFeatures.clear();
/* 250 */         this.disabledFeatures.addAll(nextDisabled);
/* 251 */         this.receivedInitialResponse.set(true);
/* 252 */         stopRetryTask();
/* 253 */         enforceServerLocks();
/*     */       } 
/* 255 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<String> parseBlocklist(JsonElement payloadElement) {
/* 260 */     if (payloadElement == null || payloadElement.isJsonNull()) {
/* 261 */       return null;
/*     */     }
/* 263 */     Set<String> nextDisabled = ConcurrentHashMap.newKeySet();
/* 264 */     JsonArray array = null;
/*     */     
/* 266 */     if (payloadElement.isJsonArray()) {
/* 267 */       array = payloadElement.getAsJsonArray();
/* 268 */     } else if (payloadElement.isJsonObject()) {
/* 269 */       JsonObject obj = payloadElement.getAsJsonObject();
/* 270 */       if (obj.has("blocklist") && obj.get("blocklist").isJsonArray()) {
/* 271 */         array = obj.getAsJsonArray("blocklist");
/* 272 */       } else if (obj.has("features") && obj.get("features").isJsonArray()) {
/* 273 */         array = obj.getAsJsonArray("features");
/* 274 */       } else if (obj.has("disabled") && obj.get("disabled").isJsonArray()) {
/* 275 */         array = obj.getAsJsonArray("disabled");
/*     */       } 
/*     */     } 
/*     */     
/* 279 */     if (array != null) {
/* 280 */       for (JsonElement entry : array) {
/* 281 */         if (entry != null && !entry.isJsonNull() && entry.isJsonPrimitive()) {
/* 282 */           String normalized = normalize(entry.getAsString());
/* 283 */           if (!normalized.isEmpty()) {
/* 284 */             nextDisabled.add(normalized);
/*     */           }
/*     */         } 
/*     */       } 
/* 288 */       return nextDisabled;
/*     */     } 
/* 290 */     return null;
/*     */   }
/*     */   
/*     */   public static void enforceServerLocks() {
/* 294 */     Main main = Main.getInstance();
/* 295 */     if (main == null || main.getModuleProvider() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 299 */     for (Module module : main.getModuleProvider().getModules()) {
/* 300 */       if (module != null && module.isServerLocked()) {
/* 301 */         if (module.state) {
/* 302 */           module.setState(false);
/*     */         }
/* 304 */         if (module.isEnabled()) {
/* 305 */           module.deactivate();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 311 */       MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 312 */       if (menuScreen != null && menuScreen.getModuleDetailComponent() != null) {
/* 313 */         ModuleDetailComponent detail = menuScreen.getModuleDetailComponent();
/* 314 */         if (detail.isOpen() && detail.getModule() != null && detail.getModule().isServerLocked()) {
/* 315 */           menuScreen.closeModuleDetail();
/*     */         }
/*     */       } 
/* 318 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonArray buildFeatureArray(String featureName) {
/* 323 */     List<String> features = new ArrayList<>();
/* 324 */     Main main = Main.getInstance();
/* 325 */     if (main != null && main.getModuleProvider() != null) {
/* 326 */       for (Module module : main.getModuleProvider().getModules()) {
/* 327 */         if (module == null) {
/*     */           continue;
/*     */         }
/* 330 */         String id = normalize(module.getIdentifier());
/* 331 */         if (!id.isEmpty() && !features.contains(id)) {
/* 332 */           features.add(id);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 337 */     String requestedFeature = normalize(featureName);
/* 338 */     if (!requestedFeature.isEmpty() && !features.contains(requestedFeature)) {
/* 339 */       features.add(requestedFeature);
/*     */     }
/*     */     
/* 342 */     JsonArray array = new JsonArray();
/* 343 */     for (String feature : features) {
/* 344 */       array.add(feature);
/*     */     }
/* 346 */     return array;
/*     */   }
/*     */   
/*     */   private static String getString(JsonObject object, String key) {
/* 350 */     JsonElement element = object.get(key);
/* 351 */     return (element == null || element.isJsonNull()) ? "" : element.getAsString();
/*     */   }
/*     */   
/*     */   private static String normalize(String value) {
/* 355 */     return (value == null) ? "" : value.trim().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   private static boolean isHolyWorldServer() {
/* 359 */     String host = ServerUtil.getCurrentServerHost();
/* 360 */     if (host.isEmpty()) {
/* 361 */       return false;
/*     */     }
/*     */     
/* 364 */     String[] parts = host.split("\\.");
/* 365 */     for (String part : parts) {
/* 366 */       if ("holyworld".equals(part)) {
/* 367 */         return true;
/*     */       }
/*     */     } 
/* 370 */     return false;
/*     */   }
/*     */   public static final class HolyWorldPayload extends Record implements class_8710 { private final String json;
/* 373 */     public HolyWorldPayload(String json) { this.json = json; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/HolyWorldFeatureControlService$HolyWorldPayload;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #373	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 373 */       //   0	7	0	this	Lvorga/phazeclient/base/util/HolyWorldFeatureControlService$HolyWorldPayload; } public String json() { return this.json; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/HolyWorldFeatureControlService$HolyWorldPayload;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #373	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/HolyWorldFeatureControlService$HolyWorldPayload; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/HolyWorldFeatureControlService$HolyWorldPayload;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #373	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/base/util/HolyWorldFeatureControlService$HolyWorldPayload;
/* 374 */       //   0	8	1	o	Ljava/lang/Object; } public static final class_8710.class_9154<HolyWorldPayload> ID = new class_8710.class_9154(
/* 375 */         class_2960.method_60655("liteapi", "feature-control")); public static final class_9139<class_9129, HolyWorldPayload> CODEC;
/*     */     static {
/* 377 */       CODEC = class_9139.method_56438((value, buf) -> buf.method_52983(value.json().getBytes(StandardCharsets.UTF_8)), buf -> {
/*     */             byte[] bytes = new byte[buf.readableBytes()];
/*     */             buf.method_52979(bytes);
/*     */             return new HolyWorldPayload(new String(bytes, StandardCharsets.UTF_8));
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public class_8710.class_9154<? extends class_8710> method_56479() {
/* 388 */       return (class_8710.class_9154)ID;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\HolyWorldFeatureControlService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */