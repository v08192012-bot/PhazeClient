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
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Base64;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import net.minecraft.class_310;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public final class RemoteRulesService
/*     */ {
/*  70 */   private static final Logger LOG = LoggerFactory.getLogger("PhazeRules");
/*  71 */   private static final Set<String> OFFLINE_FALLBACK_HIDDEN_MODULES = Set.of(new String[] { "auc_helper", "auto_respawn", "auto_eat", "autonear", "autopotion", "autoreissue", "autoswap", "binds", "elytrautility", "fast_swap", "shifttap" });
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_API_BASE = "https://phazeclient.ru";
/*     */ 
/*     */   
/*     */   private static final long HEARTBEAT_SECONDS = 600L;
/*     */ 
/*     */   
/*     */   private static final long REFRESH_INTERVAL_SECONDS = 600L;
/*     */ 
/*     */   
/*     */   private static final long LOCKDOWN_AFTER_MS = 1200000L;
/*     */ 
/*     */   
/*     */   private static final long STARTUP_GRACE_MS = 45000L;
/*     */ 
/*     */   
/*     */   private static final int CONNECT_TIMEOUT_MS = 15000;
/*     */ 
/*     */   
/*     */   private static final int READ_TIMEOUT_MS = 30000;
/*     */ 
/*     */   
/*     */   private final ScheduledExecutorService scheduler;
/*     */ 
/*     */   
/*     */   private final AtomicBoolean started;
/*     */ 
/*     */   
/*     */   private final AtomicBoolean refreshInFlight;
/*     */ 
/*     */   
/*     */   private final AtomicBoolean manifestUploaded;
/*     */ 
/*     */   
/*     */   private final String apiBase;
/*     */ 
/*     */   
/*     */   private final boolean enabled;
/*     */ 
/*     */   
/*     */   private final String clientId;
/*     */ 
/*     */   
/*     */   private volatile Set<String> blocked;
/*     */ 
/*     */   
/*     */   private volatile Set<String> allowed;
/*     */ 
/*     */   
/*     */   private volatile String lastHost;
/*     */ 
/*     */   
/*     */   private volatile long lastRefreshMs;
/*     */ 
/*     */   
/*     */   private volatile long lastSuccessfulFetchMs;
/*     */ 
/*     */   
/*     */   private final long serviceStartMs;
/*     */ 
/*     */   
/*     */   private volatile int onlineCount;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 139 */     System.setProperty("java.net.preferIPv4Stack", "true");
/* 140 */     System.setProperty("java.net.preferIPv6Addresses", "false");
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
/*     */   private static final class Holder
/*     */   {
/* 154 */     static final RemoteRulesService INSTANCE = new RemoteRulesService();
/*     */   }
/*     */   
/*     */   public static RemoteRulesService getInstance() {
/* 158 */     return Holder.INSTANCE;
/*     */   }
/*     */   private RemoteRulesService() {
/* 161 */     this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
/*     */           Thread t = new Thread(r, "phaze-rules-poll");
/*     */           
/*     */           t.setDaemon(true);
/*     */           return (ThreadFactory)t;
/*     */         });
/* 167 */     this.started = new AtomicBoolean(false);
/* 168 */     this.refreshInFlight = new AtomicBoolean(false);
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
/* 179 */     this.manifestUploaded = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     this.blocked = Collections.emptySet();
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
/* 198 */     this.allowed = Collections.emptySet();
/* 199 */     this.lastHost = null;
/* 200 */     this.lastRefreshMs = 0L;
/* 201 */     this.lastSuccessfulFetchMs = 0L;
/* 202 */     this.serviceStartMs = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     this.onlineCount = -1;
/*     */ 
/*     */     
/* 213 */     String configured = System.getProperty("phaze.rules.api", "https://phazeclient.ru");
/*     */     
/* 215 */     while (configured.endsWith("/")) {
/* 216 */       configured = configured.substring(0, configured.length() - 1);
/*     */     }
/* 218 */     this.apiBase = configured;
/* 219 */     this.enabled = !configured.isEmpty();
/* 220 */     this.clientId = loadOrCreateClientId();
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
/*     */   public int getOnlineCount() {
/* 233 */     return this.onlineCount;
/*     */   }
/*     */   
/*     */   public boolean isKnownClientUser(String username) {
/* 237 */     return PhazePlayerPresence.getInstance().isKnownUser(username);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClientId() {
/* 242 */     return this.clientId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getApiBase() {
/* 252 */     return this.apiBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModuleExplicitlyAllowed(String moduleId) {
/* 262 */     if (moduleId == null || moduleId.isEmpty()) {
/* 263 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 267 */     if (isRulesApiUnavailableNow()) {
/* 268 */       return false;
/*     */     }
/* 270 */     return this.allowed.contains(moduleId.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCurrentUsername() {
/*     */     try {
/* 280 */       class_310 client = class_310.method_1551();
/* 281 */       if (client == null || client.method_1548() == null) return null; 
/* 282 */       String username = client.method_1548().method_1676();
/* 283 */       return (username == null || username.isBlank()) ? null : username;
/* 284 */     } catch (Throwable t) {
/* 285 */       return null;
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
/*     */   private String loadOrCreateClientId() {
/*     */     try {
/* 305 */       class_310 client = class_310.method_1551();
/*     */ 
/*     */       
/* 308 */       Path runDir = (client != null) ? client.field_1697.toPath() : Path.of(".", new String[0]);
/* 309 */       Path file = runDir.resolve("Phaze").resolve("files").resolve("client_id");
/*     */       
/* 311 */       if (Files.exists(file, new java.nio.file.LinkOption[0])) {
/* 312 */         String existing = Files.readString(file, StandardCharsets.UTF_8).trim();
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 317 */           UUID parsed = UUID.fromString(existing);
/* 318 */           return parsed.toString();
/* 319 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 324 */       String fresh = UUID.randomUUID().toString();
/*     */       try {
/* 326 */         Files.createDirectories(file.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 327 */         Files.writeString(file, fresh, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
/* 328 */       } catch (IOException writeFailed) {
/*     */ 
/*     */ 
/*     */         
/* 332 */         LOG.warn("could not persist client id: {}", writeFailed.toString());
/*     */       } 
/* 334 */       return fresh;
/* 335 */     } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */       
/* 339 */       LOG.warn("client id init failed, using ephemeral id: {}", t.toString());
/* 340 */       return UUID.randomUUID().toString();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 349 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 352 */     if (!this.started.compareAndSet(false, true)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     this.scheduler.scheduleWithFixedDelay(this::heartbeat, 0L, 600L, TimeUnit.SECONDS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     PhazeEventService.getInstance().start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModuleBlocked(String moduleId) {
/* 381 */     if (moduleId == null || moduleId.isEmpty()) {
/* 382 */       return false;
/*     */     }
/* 384 */     if (shouldUseOfflineModuleFallback()) {
/* 385 */       String normalized = moduleId.toLowerCase();
/* 386 */       if (ServerUtil.hasMirroredModuleRule(normalized)) {
/* 387 */         return !ServerUtil.isModuleAllowedByMirroredRules(normalized);
/*     */       }
/* 389 */       return OFFLINE_FALLBACK_HIDDEN_MODULES.contains(normalized);
/*     */     } 
/* 391 */     if (!this.enabled) {
/* 392 */       return false;
/*     */     }
/* 394 */     return this.blocked.contains(moduleId.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseOfflineModuleFallback() {
/* 404 */     class_310 mc = class_310.method_1551();
/* 405 */     if (mc != null && mc.method_1542()) {
/* 406 */       return false;
/*     */     }
/* 408 */     return isRulesApiUnavailableNow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldHideModuleWhenOffline(String moduleId) {
/* 417 */     if (moduleId == null || moduleId.isEmpty()) {
/* 418 */       return false;
/*     */     }
/* 420 */     if (!shouldUseOfflineModuleFallback()) {
/* 421 */       return false;
/*     */     }
/* 423 */     String normalized = moduleId.toLowerCase();
/* 424 */     if (ServerUtil.hasMirroredModuleRule(normalized)) {
/* 425 */       return false;
/*     */     }
/* 427 */     return OFFLINE_FALLBACK_HIDDEN_MODULES.contains(normalized);
/*     */   }
/*     */   
/*     */   private boolean isStrictLockdownNow() {
/* 431 */     String host = ServerUtil.getCurrentServerHost();
/* 432 */     if (host == null || host.isEmpty()) return false;
/*     */     
/* 434 */     return isRulesApiUnavailableNow();
/*     */   }
/*     */   
/*     */   private boolean isRulesApiUnavailableNow() {
/* 438 */     if (!this.enabled) {
/* 439 */       return true;
/*     */     }
/* 441 */     long now = System.currentTimeMillis();
/* 442 */     if (now - this.serviceStartMs < 45000L) return false; 
/* 443 */     if (this.lastSuccessfulFetchMs <= 0L) return true; 
/* 444 */     return (now - this.lastSuccessfulFetchMs > 1200000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getBlockedModuleIds() {
/* 452 */     return this.blocked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestRefresh() {
/* 462 */     if (!this.enabled || !this.started.get()) {
/*     */       return;
/*     */     }
/* 465 */     this.scheduler.execute(this::heartbeat);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<String> readModuleIds(JsonObject obj, String field) {
/* 470 */     Set<String> out = new HashSet<>();
/* 471 */     if (obj.has(field) && obj.get(field).isJsonArray()) {
/* 472 */       JsonArray arr = obj.getAsJsonArray(field);
/* 473 */       for (JsonElement el : arr) {
/* 474 */         if (el.isJsonPrimitive()) {
/* 475 */           out.add(el.getAsString().toLowerCase());
/*     */         }
/*     */       } 
/*     */     } 
/* 479 */     return out;
/*     */   }
/*     */   
/*     */   private void heartbeat() {
/*     */     try {
/* 484 */       String host = ServerUtil.getCurrentServerHost();
/*     */       
/* 486 */       if (host == null) host = "";
/*     */       
/* 488 */       boolean hostChanged = !host.equals(this.lastHost);
/* 489 */       long now = System.currentTimeMillis();
/* 490 */       boolean stale = (now - this.lastRefreshMs >= 600000L);
/*     */       
/* 492 */       if (hostChanged) {
/*     */ 
/*     */ 
/*     */         
/* 496 */         this.lastHost = host;
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
/* 507 */         fetchAsync(host, true);
/* 508 */       } else if (stale) {
/*     */ 
/*     */ 
/*     */         
/* 512 */         fetchAsync(host, false);
/*     */       } 
/* 514 */     } catch (Throwable t) {
/* 515 */       LOG.warn("heartbeat failed", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void fetchAsync(String host, boolean bypassCache) {
/* 522 */     if (!this.refreshInFlight.compareAndSet(false, true)) {
/*     */       return;
/*     */     }
/* 525 */     this.scheduler.execute(() -> {
/*     */           try {
/*     */             fetch(host, bypassCache);
/* 528 */           } catch (Throwable t) {
/*     */             LOG.warn("fetch failed for host='{}': {}", host, t.toString());
/*     */             this.lastRefreshMs = 0L;
/*     */           } finally {
/*     */             this.refreshInFlight.set(false);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fetch(String host, boolean bypassCache) throws Exception {
/* 542 */     String body, encodedHost = URLEncoder.encode((host == null) ? "" : host, StandardCharsets.UTF_8);
/* 543 */     String encodedClient = URLEncoder.encode(this.clientId, StandardCharsets.UTF_8);
/* 544 */     String encodedUsername = URLEncoder.encode(
/* 545 */         (getCurrentUsername() == null) ? "" : getCurrentUsername(), StandardCharsets.UTF_8);
/*     */     
/* 547 */     String encodedFresh = bypassCache ? "&fresh=1" : "";
/*     */ 
/*     */ 
/*     */     
/* 551 */     URI uri = URI.create(this.apiBase + "/api/module-rules?host=" + this.apiBase + "&clientId=" + encodedHost + "&username=" + encodedClient + encodedUsername);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 561 */     URL url = uri.toURL();
/* 562 */     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*     */ 
/*     */     
/*     */     try {
/* 566 */       conn.setRequestMethod("GET");
/* 567 */       conn.setConnectTimeout(15000);
/* 568 */       conn.setReadTimeout(30000);
/* 569 */       conn.setRequestProperty("Accept", "application/json");
/* 570 */       if (bypassCache) {
/* 571 */         conn.setRequestProperty("Cache-Control", "no-cache, no-store, max-age=0");
/* 572 */         conn.setRequestProperty("Pragma", "no-cache");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 577 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 582 */       conn.setInstanceFollowRedirects(true);
/*     */       
/* 584 */       int status = conn.getResponseCode();
/* 585 */       if (status != 200) {
/* 586 */         LOG.warn("fetch '{}' returned status {}", uri, Integer.valueOf(status));
/*     */         
/*     */         return;
/*     */       } 
/* 590 */       InputStream in = conn.getInputStream();
/* 591 */       StringBuilder sb = new StringBuilder();
/* 592 */       BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); 
/* 593 */       try { char[] buf = new char[2048];
/*     */         int n;
/* 595 */         for (; (n = r.read(buf)) != -1; sb.append(buf, 0, n));
/* 596 */         r.close(); } catch (Throwable throwable) { try { r.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 597 */        body = sb.toString();
/*     */     } finally {
/* 599 */       conn.disconnect();
/*     */     } 
/*     */     
/* 602 */     JsonElement parsed = JsonParser.parseString(body);
/* 603 */     if (!parsed.isJsonObject())
/* 604 */       return;  JsonObject obj = parsed.getAsJsonObject();
/*     */     
/* 606 */     Set<String> next = readModuleIds(obj, "blocked");
/* 607 */     Set<String> nextAllowed = readModuleIds(obj, "allowed");
/*     */     
/* 609 */     if (obj.has("online") && obj.get("online").isJsonPrimitive()) {
/*     */       try {
/* 611 */         this.onlineCount = obj.get("online").getAsInt();
/* 612 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 620 */     PhazeAnnouncements.acceptAll(obj.get("announcements"));
/*     */     
/* 622 */     PhazePlayerPresence.getInstance().refreshFromRulesPayload(obj, this.apiBase, 15000, 30000);
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
/* 636 */     if ((host == null) ? (this.lastHost == null) : host.equals(this.lastHost)) {
/* 637 */       this.blocked = Collections.unmodifiableSet(next);
/* 638 */       this.allowed = Collections.unmodifiableSet(nextAllowed);
/* 639 */       this.lastRefreshMs = System.currentTimeMillis();
/* 640 */       this.lastSuccessfulFetchMs = this.lastRefreshMs;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 651 */     if (this.manifestUploaded.compareAndSet(false, true)) {
/*     */       try {
/* 653 */         pushManifest();
/* 654 */       } catch (Throwable t) {
/*     */         
/* 656 */         this.manifestUploaded.set(false);
/* 657 */         LOG.warn("manifest upload failed: {}", t.toString());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pushManifest() throws IOException {
/*     */     byte[] manifestBytes;
/*     */     String signature;
/* 670 */     InputStream manifest = RemoteRulesService.class.getResourceAsStream("/phaze/module-manifest.json");
/*     */     
/* 672 */     try { InputStream signatureStream = RemoteRulesService.class.getResourceAsStream("/phaze/module-manifest.sig");
/*     */       
/* 674 */       try { if (manifest == null || signatureStream == null) {
/* 675 */           throw new IOException("signed module manifest is missing from JAR");
/*     */         }
/* 677 */         manifestBytes = manifest.readAllBytes();
/* 678 */         signature = (new String(signatureStream.readAllBytes(), StandardCharsets.US_ASCII)).trim();
/* 679 */         if (signatureStream != null) signatureStream.close();  } catch (Throwable throwable) { if (signatureStream != null) try { signatureStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (manifest != null) manifest.close();  } catch (Throwable throwable) { if (manifest != null)
/* 680 */         try { manifest.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (manifestBytes.length == 0 || signature.isEmpty()) {
/* 681 */       throw new IOException("signed module manifest is empty");
/*     */     }
/*     */     
/* 684 */     JsonObject body = new JsonObject();
/* 685 */     body.addProperty("clientId", this.clientId);
/* 686 */     body.addProperty("payload", Base64.getEncoder().encodeToString(manifestBytes));
/* 687 */     body.addProperty("signature", signature);
/*     */     
/* 689 */     URI uri = URI.create(this.apiBase + "/api/manifest");
/* 690 */     HttpURLConnection conn = (HttpURLConnection)uri.toURL().openConnection();
/*     */     try {
/* 692 */       conn.setRequestMethod("POST");
/* 693 */       conn.setConnectTimeout(15000);
/* 694 */       conn.setReadTimeout(30000);
/* 695 */       conn.setRequestProperty("Content-Type", "application/json");
/* 696 */       conn.setRequestProperty("Accept", "application/json");
/*     */ 
/*     */       
/* 699 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 704 */       conn.setDoOutput(true);
/* 705 */       conn.setInstanceFollowRedirects(true);
/*     */       
/* 707 */       byte[] payload = body.toString().getBytes(StandardCharsets.UTF_8);
/* 708 */       OutputStream out = conn.getOutputStream(); 
/* 709 */       try { out.write(payload);
/* 710 */         if (out != null) out.close();  } catch (Throwable throwable) { if (out != null)
/*     */           try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 712 */        int status = conn.getResponseCode();
/* 713 */       if (status != 204 && status != 200)
/*     */       {
/*     */ 
/*     */         
/* 717 */         throw new IOException("manifest POST returned " + status);
/*     */       }
/*     */     } finally {
/* 720 */       conn.disconnect();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\RemoteRulesService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */