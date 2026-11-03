/*     */ package vorga.phazeclient.implement.cosmetics;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_742;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import vorga.phazeclient.base.util.RemoteRulesService;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.CosmeticCatalog;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.CosmeticCategory;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.CosmeticEntry;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.SharedWorldKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CosmeticsSyncService
/*     */ {
/*  44 */   private static final Logger LOG = LoggerFactory.getLogger("PhazeCosmeticsSync");
/*  45 */   private static final CosmeticsSyncService INSTANCE = new CosmeticsSyncService(); private static final int CONNECT_TIMEOUT_MS = 10000; private static final int READ_TIMEOUT_MS = 15000; private static final int MAX_QUERY_PLAYERS = 200; private static final long FALLBACK_REFRESH_MS = 600000L; private static final String SLOT_SEPARATOR = "|"; private final ScheduledExecutorService io; private final Map<UUID, RemoteCosmetic> states;
/*     */   private final Map<UUID, PulseRemoteCosmetic> pulseStates;
/*     */   private final Map<String, PulseGraffiti> pulseGraffiti;
/*     */   private final AtomicBoolean queryInFlight;
/*     */   private final AtomicBoolean graffitiQueryInFlight;
/*     */   
/*     */   private CosmeticsSyncService() {
/*  52 */     this
/*  53 */       .io = Executors.newSingleThreadScheduledExecutor(r -> {
/*     */           Thread thread = new Thread(r, "phaze-cosmetics-sync");
/*     */           thread.setDaemon(true);
/*     */           return (ThreadFactory)thread;
/*     */         });
/*  58 */     this.states = new ConcurrentHashMap<>();
/*  59 */     this.pulseStates = new ConcurrentHashMap<>();
/*  60 */     this.pulseGraffiti = new ConcurrentHashMap<>();
/*  61 */     this.queryInFlight = new AtomicBoolean(false);
/*  62 */     this.graffitiQueryInFlight = new AtomicBoolean(false);
/*  63 */     this.pendingGraffitiQuery = new AtomicReference<>();
/*     */ 
/*     */ 
/*     */     
/*  67 */     this.rosterFingerprint = "";
/*     */ 
/*     */     
/*  70 */     this.publishOnJoin = true;
/*     */     
/*  72 */     this.graffitiContext = "";
/*     */   }
/*     */   private final AtomicReference<GraffitiQuery> pendingGraffitiQuery; private ScheduledFuture<?> pendingPublish; private UUID localPlayerUuid; private String rosterFingerprint; private long lastRosterCheckMs; private volatile long lastQueryMs; private volatile boolean publishOnJoin; private volatile boolean forceRefresh;
/*     */   private volatile String graffitiContext;
/*     */   private volatile long lastGraffitiQueryMs;
/*     */   private volatile long lastPulseCosmeticRevision;
/*     */   private volatile long lastPulseGraffitiRevision;
/*     */   
/*     */   public static CosmeticsSyncService getInstance() {
/*  81 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(class_310 client) {
/*  86 */     if (client == null || client.field_1724 == null || client.field_1687 == null || client
/*  87 */       .method_1562() == null) {
/*  88 */       this.localPlayerUuid = null;
/*  89 */       this.rosterFingerprint = "";
/*  90 */       this.states.clear();
/*  91 */       this.pulseStates.clear();
/*  92 */       this.pulseGraffiti.clear();
/*  93 */       this.graffitiContext = "";
/*  94 */       this.publishOnJoin = true;
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     UUID currentUuid = client.field_1724.method_5667();
/*  99 */     if (!currentUuid.equals(this.localPlayerUuid)) {
/* 100 */       this.localPlayerUuid = currentUuid;
/* 101 */       this.rosterFingerprint = "";
/* 102 */       this.publishOnJoin = true;
/* 103 */       this.forceRefresh = true;
/*     */     } 
/* 105 */     if (this.publishOnJoin) {
/* 106 */       this.publishOnJoin = false;
/* 107 */       publishLocalState();
/*     */     } 
/*     */     
/* 110 */     long now = System.currentTimeMillis();
/* 111 */     if (!this.forceRefresh && now - this.lastRosterCheckMs < 2000L)
/* 112 */       return;  this.lastRosterCheckMs = now;
/*     */     
/* 114 */     List<UUID> players = loadedPlayerUuids(client);
/* 115 */     String fingerprint = players.toString();
/* 116 */     boolean rosterChanged = !fingerprint.equals(this.rosterFingerprint);
/* 117 */     boolean fallbackDue = (now - this.lastQueryMs >= 600000L);
/* 118 */     if (this.forceRefresh || rosterChanged || fallbackDue) {
/* 119 */       this.forceRefresh = false;
/* 120 */       this.rosterFingerprint = fingerprint;
/* 121 */       query(players);
/* 122 */       if (rosterChanged) {
/* 123 */         this.io.schedule(() -> Boolean.valueOf(this.forceRefresh = true), 2L, TimeUnit.SECONDS);
/*     */       }
/*     */     } 
/*     */     
/* 127 */     String serverKey = SharedWorldKey.get(client);
/* 128 */     String dimension = SharedWorldKey.dimension(client);
/* 129 */     String context = String.valueOf(serverKey) + "|" + String.valueOf(serverKey);
/* 130 */     if (serverKey != null && dimension != null && (
/* 131 */       !context.equals(this.graffitiContext) || now - this.lastGraffitiQueryMs >= 600000L)) {
/*     */       
/* 133 */       this.graffitiContext = context;
/* 134 */       queryGraffiti(serverKey, dimension);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void publishLocalState() {
/* 143 */     if (this.pendingPublish != null) this.pendingPublish.cancel(false); 
/* 144 */     this.pendingPublish = this.io.schedule(this::postLocalState, 120L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestRefresh() {
/* 149 */     this.forceRefresh = true;
/* 150 */     this.lastGraffitiQueryMs = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void acceptEvent(JsonObject event) {
/*     */     try {
/* 156 */       UUID uuid = UUID.fromString(event.get("playerUuid").getAsString());
/* 157 */       String cosmetic = event.get("cosmetic").getAsString();
/* 158 */       boolean equipped = event.get("equipped").getAsBoolean();
/* 159 */       long updatedAt = event.get("updatedAt").getAsLong();
/* 160 */       RemoteCosmetic decoded = decode(cosmetic, equipped, updatedAt);
/* 161 */       class_310 client = class_310.method_1551();
/* 162 */       if (client == null)
/* 163 */         return;  client.execute(() -> {
/*     */             if (!isLoadedPlayer(uuid))
/*     */               return;  putIfNewer(uuid, decoded);
/*     */           });
/* 167 */     } catch (Throwable error) {
/* 168 */       LOG.debug("invalid cosmetic event: {}", error.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void acceptPulseEvent(JsonObject event) {
/*     */     try {
/* 175 */       notePulseRevision(event, false);
/* 176 */       UUID uuid = UUID.fromString(event.get("playerUuid").getAsString());
/* 177 */       PulseRemoteCosmetic decoded = decodePulse(event
/* 178 */           .get("cosmetic").getAsString(), event
/* 179 */           .get("equipped").getAsBoolean(), event
/* 180 */           .get("updatedAt").getAsLong());
/*     */       
/* 182 */       class_310 client = class_310.method_1551();
/* 183 */       if (client == null)
/* 184 */         return;  client.execute(() -> {
/*     */             if (!isLoadedPlayer(uuid))
/*     */               return;  putPulseIfNewer(uuid, decoded);
/*     */           });
/* 188 */     } catch (Throwable error) {
/* 189 */       LOG.debug("invalid pulse cosmetic event: {}", error.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void acceptPulseGraffitiEvent(JsonObject event) {
/* 194 */     notePulseRevision(event, true);
/* 195 */     class_310 client = class_310.method_1551();
/* 196 */     if (client == null)
/* 197 */       return;  client.execute(() -> {
/*     */           try {
/*     */             String serverKey = event.get("serverKey").getAsString(); String dimension = event.get("dimension").getAsString(); if (!(serverKey + "|" + serverKey).equals(this.graffitiContext))
/*     */               return; 
/*     */             class_2338 pos = new class_2338(event.get("x").getAsInt(), event.get("y").getAsInt(), event.get("z").getAsInt());
/*     */             class_2350 face = class_2350.method_10168(event.get("face").getAsString());
/*     */             if (face == null)
/*     */               return; 
/*     */             String key = graffitiKey(serverKey, dimension, pos, face);
/*     */             if ("delete".equals(event.get("action").getAsString())) {
/*     */               this.pulseGraffiti.remove(key);
/*     */             } else {
/*     */               int id = event.get("graffitiId").getAsInt();
/*     */               CosmeticEntry entry = CosmeticCatalog.byId(id);
/*     */               if (entry != null && entry.category() == CosmeticCategory.GRAFFITI)
/*     */                 this.pulseGraffiti.put(key, new PulseGraffiti(serverKey, dimension, pos, face, id)); 
/*     */             } 
/*     */             if (this.graffitiQueryInFlight.get())
/*     */               queryGraffiti(serverKey, dimension); 
/* 216 */           } catch (Throwable error) {
/*     */             LOG.debug("invalid shared graffiti event: {}", error.toString());
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public List<PulseGraffiti> pulseGraffiti() {
/* 223 */     return List.copyOf(this.pulseGraffiti.values());
/*     */   }
/*     */   
/*     */   public void acceptPushCheckpoint(JsonObject payload) {
/*     */     try {
/* 228 */       JsonObject revisions = payload.getAsJsonObject("pulseRevisions");
/* 229 */       if (revisions == null)
/* 230 */         return;  long cosmetic = revisions.get("cosmetic").getAsLong();
/* 231 */       long graffiti = revisions.get("graffiti").getAsLong();
/* 232 */       if (cosmetic > this.lastPulseCosmeticRevision) {
/* 233 */         this.lastPulseCosmeticRevision = cosmetic;
/* 234 */         this.forceRefresh = true;
/*     */       } 
/* 236 */       if (graffiti > this.lastPulseGraffitiRevision) {
/* 237 */         this.lastPulseGraffitiRevision = graffiti;
/* 238 */         this.lastGraffitiQueryMs = 0L;
/*     */       } 
/* 240 */     } catch (Throwable error) {
/* 241 */       LOG.debug("invalid push checkpoint: {}", error.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notePulseRevision(JsonObject event, boolean graffiti) {
/* 246 */     if (!event.has("revision"))
/* 247 */       return;  long revision = event.get("revision").getAsLong();
/* 248 */     if (graffiti) { this.lastPulseGraffitiRevision = Math.max(this.lastPulseGraffitiRevision, revision); }
/* 249 */     else { this.lastPulseCosmeticRevision = Math.max(this.lastPulseCosmeticRevision, revision); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CosmeticEntry pulseEntryFor(UUID playerUuid, CosmeticCategory category) {
/* 256 */     if (playerUuid == null) return null; 
/* 257 */     PulseRemoteCosmetic state = this.pulseStates.get(playerUuid);
/* 258 */     if (state == null || !state.equipped) return null; 
/* 259 */     int id = state.id(category);
/* 260 */     CosmeticEntry entry = CosmeticCatalog.byId(id);
/* 261 */     return (entry != null && entry.category() == category) ? entry : null;
/*     */   }
/*     */   
/*     */   public String selectionFor(UUID playerUuid) {
/* 265 */     String wing = wingSelectionFor(playerUuid);
/* 266 */     return (wing != null) ? wing : capeSelectionFor(playerUuid);
/*     */   }
/*     */   
/*     */   public String wingSelectionFor(UUID playerUuid) {
/* 270 */     if (playerUuid == null) return null; 
/* 271 */     RemoteCosmetic state = this.states.get(playerUuid);
/* 272 */     if (state == null || !state.equipped || "None"
/* 273 */       .equalsIgnoreCase(state.wing)) {
/* 274 */       return null;
/*     */     }
/* 276 */     return CosmeticsState.getInstance().isAvailable(state.wing) ? 
/* 277 */       state.wing : 
/* 278 */       null;
/*     */   }
/*     */   
/*     */   public String capeSelectionFor(UUID playerUuid) {
/* 282 */     if (playerUuid == null) return null; 
/* 283 */     RemoteCosmetic state = this.states.get(playerUuid);
/* 284 */     if (state == null || !state.equipped || "None"
/* 285 */       .equalsIgnoreCase(state.cape)) {
/* 286 */       return null;
/*     */     }
/* 288 */     return CosmeticsState.getInstance().isAvailable(state.cape) ? 
/* 289 */       state.cape : 
/* 290 */       null;
/*     */   }
/*     */   
/*     */   public String hatSelectionFor(UUID playerUuid) {
/* 294 */     if (playerUuid == null) return null; 
/* 295 */     RemoteCosmetic state = this.states.get(playerUuid);
/* 296 */     if (state == null || !state.equipped || "None"
/* 297 */       .equalsIgnoreCase(state.hat)) {
/* 298 */       return null;
/*     */     }
/* 300 */     return CosmeticsState.getInstance().isAvailable(state.hat) ? 
/* 301 */       state.hat : 
/* 302 */       null;
/*     */   }
/*     */   
/*     */   public String petSelectionFor(UUID playerUuid) {
/* 306 */     if (playerUuid == null) return null; 
/* 307 */     RemoteCosmetic state = this.states.get(playerUuid);
/* 308 */     if (state == null || !state.equipped || "None"
/* 309 */       .equalsIgnoreCase(state.pet)) {
/* 310 */       return null;
/*     */     }
/* 312 */     return CosmeticsState.getInstance().isAvailable(state.pet) ? 
/* 313 */       state.pet : 
/* 314 */       null;
/*     */   }
/*     */   
/*     */   private void postLocalState() {
/* 318 */     class_310 client = class_310.method_1551();
/* 319 */     if (client == null || client.field_1724 == null) {
/* 320 */       this.publishOnJoin = true;
/*     */       return;
/*     */     } 
/* 323 */     RemoteRulesService rules = RemoteRulesService.getInstance();
/* 324 */     if (rules.getApiBase() == null || rules.getApiBase().isBlank())
/*     */       return; 
/* 326 */     CosmeticsState local = CosmeticsState.getInstance();
/* 327 */     JsonObject body = new JsonObject();
/* 328 */     body.addProperty("clientId", rules.getClientId());
/* 329 */     body.addProperty("playerUuid", client.field_1724.method_5667().toString());
/* 330 */     String wing = local.isWingEquipped() ? local.getSelectedWing() : "None";
/* 331 */     String cape = local.isCapeEquipped() ? local.getSelectedCape() : "None";
/* 332 */     String hat = local.isHatEquipped() ? local.getSelectedHat() : "None";
/* 333 */     String pet = local.isPetEquipped() ? local.getSelectedPet() : "None";
/* 334 */     body.addProperty("cosmetic", String.join("|", new CharSequence[] { wing, cape, hat, pet }));
/* 335 */     body.addProperty("equipped", Boolean.valueOf(local.isEquipped()));
/*     */     try {
/* 337 */       request("/api/cosmetics/state", body);
/* 338 */     } catch (Throwable error) {
/* 339 */       LOG.debug("state publish failed: {}", error.toString());
/* 340 */       this.publishOnJoin = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void query(List<UUID> playerUuids) {
/* 345 */     if (playerUuids.isEmpty() || !this.queryInFlight.compareAndSet(false, true))
/* 346 */       return;  String apiBase = RemoteRulesService.getInstance().getApiBase();
/* 347 */     if (apiBase == null || apiBase.isBlank()) {
/* 348 */       this.queryInFlight.set(false);
/*     */       return;
/*     */     } 
/* 351 */     List<UUID> requested = List.copyOf(playerUuids);
/* 352 */     this.io.execute(() -> {
/*     */           try {
/*     */             JsonObject body = new JsonObject();
/*     */             
/*     */             JsonArray ids = new JsonArray();
/*     */             
/*     */             requested.forEach(());
/*     */             body.add("playerUuids", (JsonElement)ids);
/*     */             JsonArray sources = new JsonArray();
/*     */             sources.add("phaze");
/*     */             sources.add("pulse");
/*     */             body.add("sources", (JsonElement)sources);
/*     */             JsonObject response = request("/api/cosmetics/query", body);
/*     */             JsonArray array = response.getAsJsonArray("states");
/*     */             Map<UUID, Boolean> returned = new ConcurrentHashMap<>();
/*     */             Map<UUID, Boolean> pulseReturned = new ConcurrentHashMap<>();
/*     */             if (array != null) {
/*     */               for (JsonElement element : array) {
/*     */                 if (!element.isJsonObject()) {
/*     */                   continue;
/*     */                 }
/*     */                 JsonObject value = element.getAsJsonObject();
/*     */                 UUID uuid = UUID.fromString(value.get("playerUuid").getAsString());
/*     */                 String source = value.has("source") ? value.get("source").getAsString() : "phaze";
/*     */                 if ("pulse".equals(source)) {
/*     */                   pulseReturned.put(uuid, Boolean.TRUE);
/*     */                   putPulseIfNewer(uuid, decodePulse(value.get("cosmetic").getAsString(), value.get("equipped").getAsBoolean(), value.get("updatedAt").getAsLong()));
/*     */                   continue;
/*     */                 } 
/*     */                 returned.put(uuid, Boolean.TRUE);
/*     */                 putIfNewer(uuid, decode(value.get("cosmetic").getAsString(), value.get("equipped").getAsBoolean(), value.get("updatedAt").getAsLong()));
/*     */               } 
/*     */             }
/*     */             for (UUID uuid : requested) {
/*     */               if (!returned.containsKey(uuid)) {
/*     */                 this.states.remove(uuid);
/*     */               }
/*     */               if (!pulseReturned.containsKey(uuid)) {
/*     */                 this.pulseStates.remove(uuid);
/*     */               }
/*     */             } 
/*     */             this.lastQueryMs = System.currentTimeMillis();
/* 394 */           } catch (Throwable error) {
/*     */             LOG.debug("roster query failed: {}", error.toString());
/*     */           } finally {
/*     */             this.queryInFlight.set(false);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void queryGraffiti(String serverKey, String dimension) {
/* 403 */     this.pendingGraffitiQuery.set(new GraffitiQuery(serverKey, dimension));
/* 404 */     startGraffitiQueryDrain();
/*     */   }
/*     */   
/*     */   private void startGraffitiQueryDrain() {
/* 408 */     if (!this.graffitiQueryInFlight.compareAndSet(false, true))
/* 409 */       return;  this.io.execute(() -> {
/*     */           try {
/*     */             GraffitiQuery query;
/*     */             while ((query = this.pendingGraffitiQuery.getAndSet(null)) != null) {
/*     */               try {
/*     */                 GraffitiQuery currentQuery = query;
/*     */                 JsonObject body = new JsonObject();
/*     */                 body.addProperty("serverKey", currentQuery.serverKey());
/*     */                 body.addProperty("dimension", currentQuery.dimension());
/*     */                 JsonArray array = request("/api/graffiti/query", body).getAsJsonArray("graffiti");
/*     */                 Map<String, PulseGraffiti> next = new ConcurrentHashMap<>();
/*     */                 if (array != null) {
/*     */                   for (JsonElement element : array) {
/*     */                     JsonObject value = element.getAsJsonObject();
/*     */                     int id = value.get("graffitiId").getAsInt();
/*     */                     CosmeticEntry entry = CosmeticCatalog.byId(id);
/*     */                     class_2350 face = class_2350.method_10168(value.get("face").getAsString());
/*     */                     if (entry == null || entry.category() != CosmeticCategory.GRAFFITI || face == null) {
/*     */                       continue;
/*     */                     }
/*     */                     class_2338 pos = new class_2338(value.get("x").getAsInt(), value.get("y").getAsInt(), value.get("z").getAsInt());
/*     */                     PulseGraffiti graffiti = new PulseGraffiti(currentQuery.serverKey(), currentQuery.dimension(), pos, face, id);
/*     */                     next.put(graffitiKey(currentQuery.serverKey(), currentQuery.dimension(), pos, face), graffiti);
/*     */                   } 
/*     */                 }
/*     */                 class_310 client = class_310.method_1551();
/*     */                 if (client != null)
/*     */                   client.execute(()); 
/*     */                 this.lastGraffitiQueryMs = System.currentTimeMillis();
/* 438 */               } catch (Throwable error) {
/*     */                 LOG.debug("shared graffiti query failed: {}", error.toString());
/*     */               } 
/*     */             } 
/*     */           } finally {
/*     */             this.graffitiQueryInFlight.set(false);
/*     */             if (this.pendingGraffitiQuery.get() != null)
/*     */               startGraffitiQueryDrain(); 
/*     */           } 
/*     */         });
/*     */   }
/*     */   private static String graffitiKey(String serverKey, String dimension, class_2338 pos, class_2350 face) {
/* 450 */     return serverKey + "|" + serverKey + "|" + dimension + "|" + pos.method_10263() + "|" + pos.method_10264() + "|" + pos
/* 451 */       .method_10260();
/*     */   }
/*     */   
/*     */   private JsonObject request(String path, JsonObject body) throws Exception {
/* 455 */     String base = RemoteRulesService.getInstance().getApiBase();
/*     */     
/* 457 */     HttpURLConnection connection = (HttpURLConnection)URI.create(base + base).toURL().openConnection();
/*     */     try {
/* 459 */       connection.setRequestMethod("POST");
/* 460 */       connection.setConnectTimeout(10000);
/* 461 */       connection.setReadTimeout(15000);
/* 462 */       connection.setRequestProperty("Content-Type", "application/json");
/* 463 */       connection.setRequestProperty("Accept", "application/json");
/* 464 */       connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/*     */ 
/*     */ 
/*     */       
/* 468 */       connection.setDoOutput(true);
/* 469 */       byte[] payload = body.toString().getBytes(StandardCharsets.UTF_8);
/* 470 */       OutputStream output = connection.getOutputStream(); 
/* 471 */       try { output.write(payload);
/* 472 */         if (output != null) output.close();  } catch (Throwable throwable) { if (output != null)
/* 473 */           try { output.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  int status = connection.getResponseCode();
/* 474 */       if (status < 200 || status >= 300) {
/* 475 */         throw new IllegalStateException("HTTP " + status);
/*     */       }
/*     */       
/* 478 */       BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 489 */       connection.disconnect();
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<UUID> loadedPlayerUuids(class_310 client) {
/* 494 */     List<UUID> result = new ArrayList<>();
/* 495 */     for (class_742 player : client.field_1687.method_18456()) {
/* 496 */       if (player == client.field_1724)
/* 497 */         continue;  result.add(player.method_5667());
/* 498 */       if (result.size() >= 200)
/*     */         break; 
/* 500 */     }  result.sort(Comparator.comparing(UUID::toString));
/* 501 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isLoadedPlayer(UUID uuid) {
/* 505 */     class_310 client = class_310.method_1551();
/* 506 */     if (client == null || client.field_1687 == null) return false; 
/* 507 */     for (class_1657 player : client.field_1687.method_18456()) {
/* 508 */       if (uuid.equals(player.method_5667())) return true; 
/*     */     } 
/* 510 */     return false;
/*     */   }
/*     */   
/*     */   private void putIfNewer(UUID uuid, RemoteCosmetic next) {
/* 514 */     if (this.states.size() > 512) this.states.clear(); 
/* 515 */     this.states.compute(uuid, (ignored, current) -> 
/* 516 */         (current == null || next.updatedAt >= current.updatedAt) ? next : current);
/*     */   }
/*     */   
/*     */   private void putPulseIfNewer(UUID uuid, PulseRemoteCosmetic next) {
/* 520 */     if (this.pulseStates.size() > 512) this.pulseStates.clear(); 
/* 521 */     this.pulseStates.compute(uuid, (ignored, current) -> 
/* 522 */         (current == null || next.updatedAt >= current.updatedAt) ? next : current);
/*     */   }
/*     */   
/*     */   private static PulseRemoteCosmetic decodePulse(String value, boolean equipped, long updatedAt) {
/* 526 */     String[] slots = (value == null) ? new String[0] : value.split("\\|", -1);
/* 527 */     int[] ids = { -1, -1, -1, -1, -1 };
/* 528 */     for (int i = 0; i < Math.min(ids.length, slots.length); i++) { try {
/* 529 */         ids[i] = Integer.parseInt(slots[i]);
/* 530 */       } catch (NumberFormatException numberFormatException) {} }
/*     */     
/* 532 */     return new PulseRemoteCosmetic(ids, equipped, updatedAt);
/*     */   }
/*     */   
/*     */   private static RemoteCosmetic decode(String value, boolean equipped, long updatedAt) {
/* 536 */     String raw = (value == null) ? "None" : value;
/* 537 */     String[] slots = raw.split("\\Q|\\E", -1);
/* 538 */     if (slots.length >= 2) {
/* 539 */       String wing = slots[0];
/* 540 */       String str1 = slots[1];
/* 541 */       String hat = (slots.length >= 3) ? slots[2] : "None";
/* 542 */       String pet = (slots.length >= 4) ? slots[3] : "None";
/* 543 */       return new RemoteCosmetic(
/* 544 */           wing.isBlank() ? "None" : wing, 
/* 545 */           str1.isBlank() ? "None" : str1, 
/* 546 */           hat.isBlank() ? "None" : hat, 
/* 547 */           pet.isBlank() ? "None" : pet, equipped, updatedAt);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 553 */     boolean cape = CosmeticsState.isCape(raw);
/* 554 */     return new RemoteCosmetic(
/* 555 */         cape ? "None" : raw, 
/* 556 */         cape ? raw : "None", "None", "None", equipped, updatedAt);
/*     */   }
/*     */   private static final class RemoteCosmetic extends Record { private final String wing; private final String cape;
/*     */     private final String hat;
/*     */     private final String pet;
/*     */     private final boolean equipped;
/*     */     private final long updatedAt;
/*     */     
/* 564 */     private RemoteCosmetic(String wing, String cape, String hat, String pet, boolean equipped, long updatedAt) { this.wing = wing; this.cape = cape; this.hat = hat; this.pet = pet; this.equipped = equipped; this.updatedAt = updatedAt; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$RemoteCosmetic;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #564	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 564 */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$RemoteCosmetic; } public String wing() { return this.wing; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$RemoteCosmetic;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #564	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$RemoteCosmetic; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$RemoteCosmetic;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #564	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$RemoteCosmetic;
/* 564 */       //   0	8	1	o	Ljava/lang/Object; } public String cape() { return this.cape; } public String hat() { return this.hat; } public String pet() { return this.pet; } public boolean equipped() { return this.equipped; } public long updatedAt() { return this.updatedAt; }
/*     */      }
/*     */ 
/*     */   
/*     */   private static final class PulseRemoteCosmetic extends Record {
/*     */     private final int[] ids;
/*     */     private final boolean equipped;
/*     */     private final long updatedAt;
/*     */     
/*     */     private PulseRemoteCosmetic(int[] ids, boolean equipped, long updatedAt) {
/* 574 */       this.ids = ids; this.equipped = equipped; this.updatedAt = updatedAt; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #574	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #574	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #574	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic;
/* 574 */       //   0	8	1	o	Ljava/lang/Object; } public int[] ids() { return this.ids; } public boolean equipped() { return this.equipped; } public long updatedAt() { return this.updatedAt; }
/*     */ 
/*     */     
/*     */     int id(CosmeticCategory category) {
/*     */       // Byte code:
/*     */       //   0: getstatic vorga/phazeclient/implement/cosmetics/CosmeticsSyncService$1.$SwitchMap$vorga$phazeclient$implement$cosmetics$bridge$CosmeticCategory : [I
/*     */       //   3: aload_1
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: tableswitch default -> 64, 1 -> 44, 2 -> 48, 3 -> 52, 4 -> 56, 5 -> 60
/*     */       //   44: iconst_0
/*     */       //   45: goto -> 65
/*     */       //   48: iconst_1
/*     */       //   49: goto -> 65
/*     */       //   52: iconst_2
/*     */       //   53: goto -> 65
/*     */       //   56: iconst_3
/*     */       //   57: goto -> 65
/*     */       //   60: iconst_4
/*     */       //   61: goto -> 65
/*     */       //   64: iconst_m1
/*     */       //   65: istore_2
/*     */       //   66: iload_2
/*     */       //   67: ifge -> 74
/*     */       //   70: iconst_m1
/*     */       //   71: goto -> 80
/*     */       //   74: aload_0
/*     */       //   75: getfield ids : [I
/*     */       //   78: iload_2
/*     */       //   79: iaload
/*     */       //   80: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #576	-> 0
/*     */       //   #577	-> 44
/*     */       //   #578	-> 48
/*     */       //   #579	-> 52
/*     */       //   #580	-> 56
/*     */       //   #581	-> 60
/*     */       //   #582	-> 64
/*     */       //   #584	-> 66
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	81	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseRemoteCosmetic;
/*     */       //   0	81	1	category	Lvorga/phazeclient/implement/cosmetics/bridge/CosmeticCategory;
/*     */       //   66	15	2	index	I
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class GraffitiQuery
/*     */     extends Record
/*     */   {
/*     */     private final String serverKey;
/*     */     private final String dimension;
/*     */     
/* 588 */     private GraffitiQuery(String serverKey, String dimension) { this.serverKey = serverKey; this.dimension = dimension; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$GraffitiQuery;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #588	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$GraffitiQuery; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$GraffitiQuery;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #588	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$GraffitiQuery; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$GraffitiQuery;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #588	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$GraffitiQuery;
/* 588 */       //   0	8	1	o	Ljava/lang/Object; } public String serverKey() { return this.serverKey; } public String dimension() { return this.dimension; }
/*     */      } public static final class PulseGraffiti extends Record { private final String serverKey; private final String dimension; private final class_2338 pos; private final class_2350 face; private final int graffitiId;
/* 590 */     public PulseGraffiti(String serverKey, String dimension, class_2338 pos, class_2350 face, int graffitiId) { this.serverKey = serverKey; this.dimension = dimension; this.pos = pos; this.face = face; this.graffitiId = graffitiId; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseGraffiti;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #590	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseGraffiti; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseGraffiti;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #590	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseGraffiti; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseGraffiti;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #590	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsSyncService$PulseGraffiti;
/* 590 */       //   0	8	1	o	Ljava/lang/Object; } public String serverKey() { return this.serverKey; } public String dimension() { return this.dimension; } public class_2338 pos() { return this.pos; } public class_2350 face() { return this.face; } public int graffitiId() { return this.graffitiId; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\CosmeticsSyncService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */