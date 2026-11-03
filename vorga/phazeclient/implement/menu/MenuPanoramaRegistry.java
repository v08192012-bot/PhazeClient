/*      */ package vorga.phazeclient.implement.menu;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URI;
/*      */ import java.net.http.HttpClient;
/*      */ import java.net.http.HttpRequest;
/*      */ import java.net.http.HttpResponse;
/*      */ import java.nio.file.CopyOption;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.StandardCopyOption;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.nio.file.attribute.FileTime;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipFile;
/*      */ import net.fabricmc.loader.api.FabricLoader;
/*      */ import net.minecraft.class_1011;
/*      */ import net.minecraft.class_1043;
/*      */ import net.minecraft.class_1044;
/*      */ import net.minecraft.class_1060;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import vorga.phazeclient.mixins.NativeImageGetColorInvoker;
/*      */ import vorga.phazeclient.mixins.NativeImageSetColorInvoker;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class MenuPanoramaRegistry
/*      */ {
/*      */   private static final String ROOT_DIR = "Phaze";
/*      */   private static final String PANORAMAS_DIR = "Panoramas";
/*      */   private static final long RESCAN_INTERVAL_MS = 1000L;
/*   51 */   private static final Map<String, CustomPanoramaDescriptor> CUSTOM_PANORAMAS = new LinkedHashMap<>();
/*   52 */   private static final Map<String, RemotePanoramaDescriptor> REMOTE_PANORAMAS = new LinkedHashMap<>();
/*   53 */   private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
/*      */   
/*      */   private static volatile boolean loaded = false;
/*   56 */   private static volatile long lastFolderSignature = Long.MIN_VALUE;
/*   57 */   private static volatile long lastRefreshCheckMs = 0L;
/*      */   
/*      */   private static final int PREVIEW_CACHE_SIZE = 256;
/*      */   private static final String PREVIEW_CACHE_DIR = ".previews";
/*      */   
/*      */   static {
/*   63 */     registerRemote(new RemotePanoramaDescriptor("remote:chateau", "Chateau", "chateau.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v1/chateau.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v1/chateau-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   70 */     registerRemote(new RemotePanoramaDescriptor("remote:castle", "Castle", "castle.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v1/castle.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v1/castle-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   77 */     registerRemote(new RemotePanoramaDescriptor("remote:post_soviet_night", "Post-Soviet Night", "post-soviet-night.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v1/post-soviet-night.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v1/post-soviet-night-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   89 */     registerRemote(new RemotePanoramaDescriptor("remote:legacy", "Legacy", "legacy-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/legacy-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/legacy-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   96 */     registerRemote(new RemotePanoramaDescriptor("remote:classic", "Classic", "classic-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/classic-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/classic-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_13", "1.13", "1-13-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-13-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-13-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  110 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_15", "1.15", "1-15-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-15-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-15-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  117 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_16", "1.16", "1-16-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-16-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-16-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  124 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_17", "1.17", "1-17-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-17-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-17-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  131 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_18", "1.18", "1-18-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-18-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-18-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  138 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_19", "1.19", "1-19-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-19-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-19-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  145 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_19_night", "1.19 Night", "1-19-night-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-19-night-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-19-night-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  152 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_20", "1.20 Trails & Tales", "1-20-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-20-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-20-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  159 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_20_night", "1.20 Night", "1-20-night-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-20-night-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-20-night-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  166 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_21", "1.21", "1-21-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  173 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_21_5", "1.21.5 Spring to Life", "1-21-5-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-5-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-5-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  180 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_21_6", "1.21.6 Chase the Skies", "1-21-6-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-6-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-6-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  187 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_21_9", "1.21.9", "1-21-9-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-9-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-9-panorama-preview.png"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  194 */     registerRemote(new RemotePanoramaDescriptor("remote:mc_1_21_11", "1.21.11 Mounts of Mayhem", "1-21-11-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-11-panorama.zip", "https://github.com/Nomadvorga/phaze-client/releases/download/panorama-assets-v2/1-21-11-panorama-preview.png"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void ensureDirectoryExists() {
/*      */     try {
/*  205 */       Files.createDirectories(getPanoramasDirectory(), (FileAttribute<?>[])new FileAttribute[0]);
/*  206 */     } catch (IOException e) {
/*  207 */       System.err.println("[Phaze] failed to create panoramas directory: " + String.valueOf(e));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Path getPanoramasDirectory() {
/*  212 */     return FabricLoader.getInstance().getGameDir().resolve("Phaze").resolve("Panoramas");
/*      */   }
/*      */   
/*      */   public static List<MenuUiSettings.PanoramaDescriptor> getAllPresets() {
/*  216 */     maybeRefresh();
/*  217 */     List<MenuUiSettings.PanoramaDescriptor> presets = new ArrayList<>();
/*  218 */     presets.addAll(Arrays.asList((MenuUiSettings.PanoramaDescriptor[])MenuUiSettings.PanoramaPreset.values()));
/*  219 */     for (RemotePanoramaDescriptor remote : REMOTE_PANORAMAS.values()) {
/*  220 */       if (!remote.isDownloaded()) {
/*  221 */         presets.add(remote);
/*      */       }
/*      */     } 
/*  224 */     presets.addAll(CUSTOM_PANORAMAS.values());
/*  225 */     return presets;
/*      */   }
/*      */   
/*      */   public static synchronized MenuUiSettings.PanoramaDescriptor findById(String id) {
/*  229 */     maybeRefresh();
/*  230 */     if (id != null) {
/*  231 */       for (MenuUiSettings.PanoramaPreset preset : MenuUiSettings.PanoramaPreset.values()) {
/*  232 */         if (preset.getId().equalsIgnoreCase(id)) {
/*  233 */           return preset;
/*      */         }
/*      */       } 
/*  236 */       CustomPanoramaDescriptor custom = CUSTOM_PANORAMAS.get(id.toLowerCase(Locale.ROOT));
/*  237 */       if (custom != null) {
/*  238 */         return custom;
/*      */       }
/*  240 */       RemotePanoramaDescriptor remote = REMOTE_PANORAMAS.get(id.toLowerCase(Locale.ROOT));
/*  241 */       if (remote != null) {
/*  242 */         return remote;
/*      */       }
/*      */     } 
/*  245 */     return MenuUiSettings.PanoramaPreset.VANILLA;
/*      */   }
/*      */   
/*      */   public static boolean isRemotePanorama(String id) {
/*  249 */     return (id != null && REMOTE_PANORAMAS.containsKey(id.toLowerCase(Locale.ROOT)));
/*      */   }
/*      */   
/*      */   public static void activateRemotePanorama(String id) {
/*  253 */     if (id == null)
/*  254 */       return;  RemotePanoramaDescriptor remote = REMOTE_PANORAMAS.get(id.toLowerCase(Locale.ROOT));
/*  255 */     if (remote != null) remote.activate(); 
/*      */   }
/*      */   
/*      */   private static void registerRemote(RemotePanoramaDescriptor panorama) {
/*  259 */     REMOTE_PANORAMAS.put(panorama.getId().toLowerCase(Locale.ROOT), panorama);
/*      */   }
/*      */   
/*      */   public static synchronized List<MenuUiSettings.PanoramaDescriptor> importArchives(List<Path> paths) {
/*  263 */     ensureDirectoryExists();
/*  264 */     List<String> importedIds = new ArrayList<>();
/*  265 */     for (Path path : paths) {
/*  266 */       if (path == null || !Files.isRegularFile(path, new java.nio.file.LinkOption[0]))
/*  267 */         continue;  String fileName = path.getFileName().toString();
/*  268 */       if (!fileName.toLowerCase(Locale.ROOT).endsWith(".zip"))
/*      */         continue; 
/*      */       try {
/*  271 */         Path target = resolveImportTarget(path);
/*  272 */         Path sourceAbsolute = path.toAbsolutePath().normalize();
/*  273 */         Path targetAbsolute = target.toAbsolutePath().normalize();
/*  274 */         if (!sourceAbsolute.equals(targetAbsolute)) {
/*  275 */           Files.copy(path, target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*      */         }
/*  277 */         importedIds.add(customIdForArchiveName(target.getFileName().toString()));
/*  278 */       } catch (Throwable t) {
/*  279 */         System.err.println("[Phaze] failed to import panorama archive " + String.valueOf(path) + ": " + String.valueOf(t));
/*      */       } 
/*      */     } 
/*      */     
/*  283 */     if (importedIds.isEmpty()) {
/*  284 */       return List.of();
/*      */     }
/*      */     
/*  287 */     reload();
/*  288 */     List<MenuUiSettings.PanoramaDescriptor> imported = new ArrayList<>();
/*  289 */     for (String id : importedIds) {
/*  290 */       imported.add(findById(id));
/*      */     }
/*  292 */     return imported;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void reload() {
/*  313 */     ensureDirectoryExists();
/*  314 */     class_310 client = class_310.method_1551();
/*      */     
/*  316 */     List<Path> archives = listArchives();
/*  317 */     Map<String, CustomPanoramaDescriptor> next = new LinkedHashMap<>();
/*      */     
/*  319 */     for (Path archive : archives) {
/*  320 */       String key = archive.getFileName().toString().toLowerCase(Locale.ROOT);
/*  321 */       String id = customIdForArchiveName(archive.getFileName().toString()).toLowerCase(Locale.ROOT);
/*  322 */       CustomPanoramaDescriptor existing = CUSTOM_PANORAMAS.get(id);
/*  323 */       long stamp = archiveStamp(archive);
/*      */       
/*  325 */       if (existing != null && existing.archiveStamp == stamp) {
/*      */ 
/*      */         
/*  328 */         next.put(id, existing);
/*      */         
/*      */         continue;
/*      */       } 
/*  332 */       if (existing != null) {
/*  333 */         existing.close(client);
/*      */       }
/*      */       try {
/*  336 */         CustomPanoramaDescriptor panorama = CustomPanoramaDescriptor.load(archive, client);
/*  337 */         panorama.archiveStamp = stamp;
/*  338 */         next.put(panorama.getId().toLowerCase(Locale.ROOT), panorama);
/*  339 */       } catch (Throwable t) {
/*  340 */         System.err.println("[Phaze] skipped invalid panorama archive " + key + ": " + t.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  346 */     for (Map.Entry<String, CustomPanoramaDescriptor> entry : CUSTOM_PANORAMAS.entrySet()) {
/*  347 */       if (!next.containsKey(entry.getKey())) {
/*  348 */         ((CustomPanoramaDescriptor)entry.getValue()).close(client);
/*      */       }
/*      */     } 
/*      */     
/*  352 */     CUSTOM_PANORAMAS.clear();
/*  353 */     CUSTOM_PANORAMAS.putAll(next);
/*      */     
/*  355 */     loaded = true;
/*  356 */     lastFolderSignature = computeFolderSignature(archives);
/*  357 */     lastRefreshCheckMs = System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   
/*      */   private static long archiveStamp(Path archive) {
/*      */     try {
/*  363 */       return mix(Files.size(archive), Files.getLastModifiedTime(archive, new java.nio.file.LinkOption[0]).toMillis());
/*  364 */     } catch (IOException e) {
/*  365 */       return Long.MIN_VALUE;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static synchronized boolean deleteCustomPanorama(String id) {
/*  370 */     maybeRefresh();
/*  371 */     if (id == null) {
/*  372 */       return false;
/*      */     }
/*      */     
/*  375 */     String normalizedId = id.toLowerCase(Locale.ROOT);
/*  376 */     CustomPanoramaDescriptor panorama = CUSTOM_PANORAMAS.remove(normalizedId);
/*  377 */     if (panorama == null) {
/*  378 */       return false;
/*      */     }
/*      */     
/*  381 */     class_310 client = class_310.method_1551();
/*  382 */     panorama.close(client);
/*      */     try {
/*  384 */       boolean deleted = Files.deleteIfExists(panorama.archivePath);
/*  385 */       if (!deleted) {
/*  386 */         CUSTOM_PANORAMAS.put(normalizedId, panorama);
/*  387 */         panorama.reloadClientTextures(client);
/*  388 */         return false;
/*      */       } 
/*  390 */       List<Path> archives = listArchives();
/*  391 */       lastFolderSignature = computeFolderSignature(archives);
/*  392 */       lastRefreshCheckMs = System.currentTimeMillis();
/*  393 */       loaded = true;
/*  394 */       return true;
/*  395 */     } catch (IOException e) {
/*  396 */       CUSTOM_PANORAMAS.put(normalizedId, panorama);
/*  397 */       panorama.reloadClientTextures(client);
/*  398 */       System.err.println("[Phaze] failed to delete panorama archive " + String.valueOf(panorama.archivePath.getFileName()) + ": " + String.valueOf(e));
/*  399 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static synchronized void onResourcesReloaded() {
/*  404 */     if (!loaded)
/*  405 */       return;  class_310 client = class_310.method_1551();
/*  406 */     for (CustomPanoramaDescriptor panorama : CUSTOM_PANORAMAS.values()) {
/*  407 */       panorama.reloadClientTextures(client);
/*      */     }
/*      */   }
/*      */   
/*      */   private static synchronized void maybeRefresh() {
/*  412 */     long now = System.currentTimeMillis();
/*  413 */     if (!loaded) {
/*  414 */       reload();
/*      */       return;
/*      */     } 
/*  417 */     if (now - lastRefreshCheckMs < 1000L) {
/*      */       return;
/*      */     }
/*      */     
/*  421 */     List<Path> archives = listArchives();
/*  422 */     long signature = computeFolderSignature(archives);
/*  423 */     lastRefreshCheckMs = now;
/*  424 */     if (signature != lastFolderSignature) {
/*  425 */       reload();
/*      */     }
/*      */   }
/*      */   
/*      */   private static Path resolveImportTarget(Path source) throws IOException {
/*  430 */     Path directory = getPanoramasDirectory();
/*  431 */     Path sourceAbsolute = source.toAbsolutePath().normalize();
/*  432 */     if (sourceAbsolute.getParent() != null && sourceAbsolute.getParent().equals(directory.toAbsolutePath().normalize())) {
/*  433 */       return sourceAbsolute;
/*      */     }
/*      */     
/*  436 */     String originalName = sanitizeArchiveFileName(source.getFileName().toString());
/*  437 */     String baseName = originalName;
/*  438 */     String extension = ".zip";
/*  439 */     int dot = originalName.toLowerCase(Locale.ROOT).lastIndexOf(".zip");
/*  440 */     if (dot >= 0) {
/*  441 */       baseName = originalName.substring(0, dot);
/*      */     }
/*      */     
/*  444 */     Path candidate = directory.resolve(originalName);
/*  445 */     int index = 2;
/*  446 */     while (Files.exists(candidate, new java.nio.file.LinkOption[0])) {
/*  447 */       candidate = directory.resolve(baseName + " (" + baseName + ")" + index++);
/*      */     }
/*  449 */     return candidate;
/*      */   }
/*      */   
/*      */   private static String sanitizeArchiveFileName(String fileName) {
/*  453 */     String safe = (fileName == null) ? "panorama.zip" : fileName.trim();
/*  454 */     if (safe.isEmpty()) safe = "panorama.zip"; 
/*  455 */     safe = safe.replaceAll("[\\\\/:*?\"<>|]", "_");
/*  456 */     if (!safe.toLowerCase(Locale.ROOT).endsWith(".zip")) {
/*  457 */       safe = safe + ".zip";
/*      */     }
/*  459 */     return safe;
/*      */   }
/*      */   
/*      */   private static List<Path> listArchives() {
/*  463 */     ensureDirectoryExists();
/*  464 */     List<Path> archives = new ArrayList<>(); 
/*  465 */     try { Stream<Path> stream = Files.list(getPanoramasDirectory());
/*      */ 
/*      */ 
/*      */       
/*  469 */       try { Objects.requireNonNull(archives); stream.filter(x$0 -> Files.isRegularFile(x$0, new java.nio.file.LinkOption[0])).filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".zip")).sorted(Comparator.comparing(path -> path.getFileName().toString().toLowerCase(Locale.ROOT))).forEach(archives::add);
/*  470 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  471 */     { System.err.println("[Phaze] failed to list panoramas: " + String.valueOf(e)); }
/*      */     
/*  473 */     return archives;
/*      */   }
/*      */   
/*      */   private static long computeFolderSignature(List<Path> archives) {
/*  477 */     long signature = 1469598103934665603L;
/*  478 */     for (Path archive : archives) {
/*      */       try {
/*  480 */         FileTime modified = Files.getLastModifiedTime(archive, new java.nio.file.LinkOption[0]);
/*  481 */         signature = mix(signature, archive.getFileName().toString().toLowerCase(Locale.ROOT).hashCode());
/*  482 */         signature = mix(signature, Files.size(archive));
/*  483 */         signature = mix(signature, modified.toMillis());
/*  484 */       } catch (IOException e) {
/*  485 */         signature = mix(signature, archive.getFileName().toString().hashCode());
/*      */       } 
/*      */     } 
/*  488 */     return signature;
/*      */   }
/*      */   
/*      */   private static long mix(long current, long value) {
/*  492 */     return (current ^ value) * 1099511628211L;
/*      */   }
/*      */   
/*      */   private static String customIdForArchiveName(String archiveName) {
/*  496 */     return "custom:" + archiveName.toLowerCase(Locale.ROOT);
/*      */   }
/*      */   
/*      */   private static String sanitizeTextureToken(String text) {
/*  500 */     String safe = (text == null) ? "panorama" : text.toLowerCase(Locale.ROOT);
/*  501 */     safe = safe.replaceAll("[^a-z0-9._-]", "_");
/*  502 */     while (safe.contains("__")) {
/*  503 */       safe = safe.replace("__", "_");
/*      */     }
/*  505 */     if (safe.isEmpty()) {
/*  506 */       safe = "panorama";
/*      */     }
/*  508 */     return safe;
/*      */   }
/*      */   
/*      */   private static String stripZipExtension(String name) {
/*  512 */     if (name == null) return ""; 
/*  513 */     return name.toLowerCase(Locale.ROOT).endsWith(".zip") ? name.substring(0, name.length() - 4) : name;
/*      */   }
/*      */   
/*      */   private static ZipEntry findEntryIgnoreCase(ZipFile zip, String entryName) {
/*  517 */     String target = entryName.toLowerCase(Locale.ROOT);
/*  518 */     return zip.stream()
/*  519 */       .filter(entry -> !entry.isDirectory())
/*  520 */       .filter(entry -> entry.getName().toLowerCase(Locale.ROOT).equals(target))
/*  521 */       .findFirst()
/*  522 */       .orElse(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Path previewCacheDirectory() {
/*  534 */     return getPanoramasDirectory().resolve(".previews");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Path previewCacheFile(String token, long stamp) {
/*  543 */     return previewCacheDirectory().resolve(token + "_" + token + ".png");
/*      */   }
/*      */   
/*      */   private static class_1011 readCachedPreview(Path cacheFile) {
/*  547 */     if (!Files.isRegularFile(cacheFile, new java.nio.file.LinkOption[0]))
/*  548 */       return null; 
/*      */     
/*  550 */     try { InputStream input = Files.newInputStream(cacheFile, new java.nio.file.OpenOption[0]); 
/*  551 */       try { class_1011 class_1011 = class_1011.method_4309(input);
/*  552 */         if (input != null) input.close();  return class_1011; } catch (Throwable throwable) { if (input != null) try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable t) { 
/*  553 */       try { Files.deleteIfExists(cacheFile); } catch (IOException iOException) {}
/*  554 */       return null; }
/*      */   
/*      */   }
/*      */   
/*      */   private static void writeCachedPreview(Path cacheFile, class_1011 image, String token) {
/*      */     try {
/*  560 */       Files.createDirectories(cacheFile.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/*      */ 
/*      */ 
/*      */       
/*  564 */       Stream<Path> stream = Files.list(cacheFile.getParent()); 
/*  565 */       try { stream.filter(x$0 -> Files.isRegularFile(x$0, new java.nio.file.LinkOption[0]))
/*  566 */           .filter(p -> p.getFileName().toString().startsWith(token + "_"))
/*  567 */           .filter(p -> !p.equals(cacheFile))
/*  568 */           .forEach(p -> { try { Files.deleteIfExists(p); }
/*  569 */               catch (IOException iOException) {}
/*      */             });
/*  571 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*  572 */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  image.method_4314(cacheFile);
/*  573 */     } catch (Throwable t) {
/*      */ 
/*      */ 
/*      */       
/*  577 */       System.err.println("[Phaze] could not cache panorama preview " + String.valueOf(cacheFile.getFileName()) + ": " + String.valueOf(t));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class_1011 downscaleSquare(class_1011 source, int target) {
/*  588 */     int size = Math.min(source.method_4307(), source.method_4323());
/*  589 */     if (size <= target) {
/*  590 */       return source;
/*      */     }
/*  592 */     class_1011 out = new class_1011(target, target, false);
/*  593 */     NativeImageGetColorInvoker reader = (NativeImageGetColorInvoker)source;
/*  594 */     NativeImageSetColorInvoker writer = (NativeImageSetColorInvoker)out;
/*  595 */     int block = size / target;
/*  596 */     int blockPixels = block * block;
/*      */     
/*  598 */     for (int y = 0; y < target; y++) {
/*  599 */       for (int x = 0; x < target; x++) {
/*  600 */         long a = 0L, b = 0L, g = 0L, r = 0L;
/*  601 */         int baseX = x * block;
/*  602 */         int baseY = y * block;
/*  603 */         for (int dy = 0; dy < block; dy++) {
/*  604 */           for (int dx = 0; dx < block; dx++) {
/*  605 */             int color = reader.phaze$invokeGetColor(baseX + dx, baseY + dy);
/*  606 */             a += (color >>> 24 & 0xFF);
/*  607 */             b += (color >>> 16 & 0xFF);
/*  608 */             g += (color >>> 8 & 0xFF);
/*  609 */             r += (color & 0xFF);
/*      */           } 
/*      */         } 
/*  612 */         writer.phaze$invokeSetColor(x, y, (int)(a / blockPixels << 24L | b / blockPixels << 16L | g / blockPixels << 8L | r / blockPixels));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  618 */     source.close();
/*  619 */     return out;
/*      */   }
/*      */   
/*      */   private static class_1011 cropToSquare(class_1011 image) {
/*  623 */     int width = image.method_4307();
/*  624 */     int height = image.method_4323();
/*  625 */     int size = Math.max(1, Math.min(width, height));
/*  626 */     if (width == size && height == size) {
/*  627 */       return image;
/*      */     }
/*  629 */     int offsetX = Math.max(0, (width - size) / 2);
/*  630 */     int offsetY = Math.max(0, (height - size) / 2);
/*  631 */     class_1011 square = new class_1011(size, size, false);
/*  632 */     image.method_47594(square, offsetX, offsetY, 0, 0, size, size, false, false);
/*  633 */     image.close();
/*  634 */     return square;
/*      */   }
/*      */   
/*      */   private static final class CustomPanoramaDescriptor
/*      */     implements MenuUiSettings.PanoramaDescriptor
/*      */   {
/*      */     private final String id;
/*      */     private final String displayName;
/*      */     private final Path archivePath;
/*      */     private final class_2960 cubeMapBase;
/*      */     private final class_2960 previewTextureId;
/*      */     private final class_2960[] faceTextureIds;
/*      */     private MenuPanoramaRenderer renderer;
/*      */     private class_1043 previewTexture;
/*      */     private class_1043[] faceTextures;
/*      */     private int previewTextureSize;
/*      */     private boolean facesLoaded;
/*  651 */     private long archiveStamp = Long.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private CustomPanoramaDescriptor(String id, String displayName, Path archivePath, class_2960 cubeMapBase, class_2960 previewTextureId, class_2960[] faceTextureIds) {
/*  659 */       this.id = id;
/*  660 */       this.displayName = displayName;
/*  661 */       this.archivePath = archivePath;
/*  662 */       this.cubeMapBase = cubeMapBase;
/*  663 */       this.previewTextureId = previewTextureId;
/*  664 */       this.faceTextureIds = faceTextureIds;
/*      */     }
/*      */     
/*      */     static CustomPanoramaDescriptor load(Path archivePath, class_310 client) throws IOException {
/*  668 */       String archiveName = archivePath.getFileName().toString();
/*  669 */       String displayName = MenuPanoramaRegistry.stripZipExtension(archiveName);
/*  670 */       String textureToken = MenuPanoramaRegistry.sanitizeTextureToken(displayName) + "_" + MenuPanoramaRegistry.sanitizeTextureToken(displayName);
/*  671 */       class_2960 cubeMapBase = class_2960.method_60655("phaze", "dynamic/panoramas/" + textureToken + "/panorama");
/*  672 */       class_2960 previewTextureId = class_2960.method_60655("phaze", "dynamic/panoramas/" + textureToken + "/preview");
/*  673 */       class_2960[] faceTextureIds = new class_2960[6];
/*  674 */       for (int i = 0; i < 6; i++) {
/*  675 */         faceTextureIds[i] = cubeMapBase.method_45136(cubeMapBase.method_12832() + "_" + cubeMapBase.method_12832() + ".png");
/*      */       }
/*      */ 
/*      */       
/*  679 */       CustomPanoramaDescriptor descriptor = new CustomPanoramaDescriptor(MenuPanoramaRegistry.customIdForArchiveName(archiveName), displayName, archivePath, cubeMapBase, previewTextureId, faceTextureIds);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  686 */       descriptor.validateArchive();
/*  687 */       descriptor.reloadClientTextures(client);
/*  688 */       return descriptor;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getId() {
/*  693 */       return this.id;
/*      */     }
/*      */ 
/*      */     
/*      */     public String displayName() {
/*  698 */       return this.displayName;
/*      */     }
/*      */ 
/*      */     
/*      */     public class_2960 previewTexture() {
/*  703 */       return this.previewTextureId;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previewTextureSize() {
/*  708 */       return this.previewTextureSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previewCropInset() {
/*  713 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previewCropSize() {
/*  718 */       return this.previewTextureSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public MenuPanoramaRenderer getRenderer() {
/*  723 */       if (this.renderer == null) {
/*  724 */         ensureFaceTexturesLoaded(class_310.method_1551());
/*  725 */         this.renderer = new MenuPanoramaRenderer(this.cubeMapBase);
/*      */       } 
/*  727 */       return this.renderer;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isCustom() {
/*  732 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPreviewTexture() {
/*  737 */       return (this.previewTexture != null);
/*      */     }
/*      */     
/*      */     void reloadClientTextures(class_310 client) {
/*  741 */       boolean reloadFaces = (this.facesLoaded || this.renderer != null);
/*  742 */       close(client);
/*  743 */       loadPreviewTexture(client);
/*  744 */       if (reloadFaces) {
/*  745 */         ensureFaceTexturesLoaded(client);
/*      */       }
/*      */     }
/*      */     
/*      */     void close(class_310 client) {
/*  750 */       class_1060 textureManager = (client != null) ? client.method_1531() : null;
/*  751 */       if (textureManager != null) {
/*  752 */         textureManager.method_4615(this.previewTextureId);
/*  753 */         for (class_2960 faceTextureId : this.faceTextureIds) {
/*  754 */           textureManager.method_4615(faceTextureId);
/*      */         }
/*      */       } 
/*  757 */       if (this.previewTexture != null) {
/*  758 */         this.previewTexture.close();
/*  759 */         this.previewTexture = null;
/*      */       } 
/*  761 */       if (this.faceTextures != null) {
/*  762 */         for (class_1043 faceTexture : this.faceTextures) {
/*  763 */           if (faceTexture != null) {
/*  764 */             faceTexture.close();
/*      */           }
/*      */         } 
/*  767 */         this.faceTextures = null;
/*      */       } 
/*  769 */       this.renderer = null;
/*  770 */       this.facesLoaded = false;
/*      */     }
/*      */     
/*      */     private void validateArchive() throws IOException {
/*  774 */       ZipFile zip = new ZipFile(this.archivePath.toFile()); 
/*  775 */       try { for (int i = 0; i < 6; i++) {
/*  776 */           if (MenuPanoramaRegistry.findEntryIgnoreCase(zip, "panorama_" + i + ".png") == null) {
/*  777 */             throw new IOException("missing panorama_" + i + ".png");
/*      */           }
/*      */         } 
/*  780 */         zip.close(); }
/*      */       catch (Throwable throwable) { try { zip.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */          throw throwable; }
/*  784 */        } private void loadPreviewTexture(class_310 client) { if (client == null || client.method_1531() == null) {
/*  785 */         this.previewTextureSize = 256;
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  793 */       String token = MenuPanoramaRegistry.sanitizeTextureToken(MenuPanoramaRegistry.stripZipExtension(this.archivePath.getFileName().toString()));
/*  794 */       Path cacheFile = MenuPanoramaRegistry.previewCacheFile(token, this.archiveStamp);
/*  795 */       class_1011 cached = MenuPanoramaRegistry.readCachedPreview(cacheFile);
/*  796 */       if (cached != null) {
/*  797 */         this.previewTextureSize = Math.max(1, cached.method_4307());
/*  798 */         this.previewTexture = new class_1043(cached);
/*  799 */         client.method_1531().method_4616(this.previewTextureId, (class_1044)this.previewTexture);
/*      */         return;
/*      */       } 
/*      */       
/*  803 */       try { ZipFile zip = new ZipFile(this.archivePath.toFile()); 
/*  804 */         try { ZipEntry previewEntry = MenuPanoramaRegistry.findEntryIgnoreCase(zip, "icon.png");
/*  805 */           boolean usePanoramaFace = (previewEntry == null);
/*  806 */           if (!usePanoramaFace) {
/*  807 */             InputStream inputStream = zip.getInputStream(previewEntry); 
/*  808 */             try { class_1011 icon = class_1011.method_4309(inputStream);
/*  809 */               usePanoramaFace = (icon.method_4307() <= 1 || icon.method_4323() <= 1);
/*  810 */               icon.close();
/*  811 */               if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null)
/*      */                 try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  813 */           }  if (usePanoramaFace) previewEntry = MenuPanoramaRegistry.findEntryIgnoreCase(zip, "panorama_0.png"); 
/*  814 */           if (previewEntry == null) {
/*  815 */             throw new IOException("missing icon.png and panorama_0.png");
/*      */           }
/*      */           
/*  818 */           InputStream input = zip.getInputStream(previewEntry); 
/*  819 */           try { class_1011 preview = MenuPanoramaRegistry.downscaleSquare(
/*  820 */                 MenuPanoramaRegistry.cropToSquare(class_1011.method_4309(input)), 256);
/*      */ 
/*      */             
/*  823 */             MenuPanoramaRegistry.writeCachedPreview(cacheFile, preview, token);
/*  824 */             this.previewTextureSize = Math.max(1, preview.method_4307());
/*  825 */             this.previewTexture = new class_1043(preview);
/*  826 */             client.method_1531().method_4616(this.previewTextureId, (class_1044)this.previewTexture);
/*  827 */             if (input != null) input.close();  } catch (Throwable throwable) { if (input != null)
/*  828 */               try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  zip.close(); } catch (Throwable throwable) { try { zip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable t)
/*  829 */       { this.previewTextureSize = 256;
/*  830 */         System.err.println("[Phaze] failed to load panorama preview " + String.valueOf(this.archivePath.getFileName()) + ": " + String.valueOf(t)); }
/*      */        }
/*      */ 
/*      */     
/*      */     private void ensureFaceTexturesLoaded(class_310 client) {
/*  835 */       if (this.facesLoaded || client == null || client.method_1531() == null) {
/*      */         return;
/*      */       }
/*      */       
/*  839 */       try { ZipFile zip = new ZipFile(this.archivePath.toFile()); 
/*  840 */         try { class_1043[] loadedTextures = new class_1043[6];
/*  841 */           for (int i = 0; i < 6; i++) {
/*  842 */             ZipEntry faceEntry = MenuPanoramaRegistry.findEntryIgnoreCase(zip, "panorama_" + i + ".png");
/*  843 */             if (faceEntry == null) {
/*  844 */               throw new IOException("missing panorama_" + i + ".png");
/*      */             }
/*  846 */             InputStream input = zip.getInputStream(faceEntry); 
/*  847 */             try { class_1011 face = class_1011.method_4309(input);
/*  848 */               loadedTextures[i] = new class_1043(face);
/*  849 */               client.method_1531().method_4616(this.faceTextureIds[i], (class_1044)loadedTextures[i]);
/*  850 */               if (input != null) input.close();  } catch (Throwable throwable) { if (input != null)
/*      */                 try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  852 */           }  this.faceTextures = loadedTextures;
/*  853 */           this.facesLoaded = true;
/*  854 */           zip.close(); } catch (Throwable throwable) { try { zip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable t)
/*  855 */       { if (this.faceTextures != null) {
/*  856 */           for (class_1043 faceTexture : this.faceTextures) {
/*  857 */             if (faceTexture != null) {
/*  858 */               faceTexture.close();
/*      */             }
/*      */           } 
/*  861 */           this.faceTextures = null;
/*      */         } 
/*  863 */         class_1060 textureManager = client.method_1531();
/*  864 */         if (textureManager != null) {
/*  865 */           for (class_2960 faceTextureId : this.faceTextureIds) {
/*  866 */             textureManager.method_4615(faceTextureId);
/*      */           }
/*      */         }
/*  869 */         System.err.println("[Phaze] failed to load panorama faces " + String.valueOf(this.archivePath.getFileName()) + ": " + String.valueOf(t)); }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class RemotePanoramaDescriptor implements MenuUiSettings.PanoramaDescriptor {
/*      */     private final String id;
/*      */     private final String displayName;
/*      */     private final String archiveName;
/*      */     private final URI downloadUri;
/*      */     private final URI previewUri;
/*      */     private final class_2960 previewTextureId;
/*  881 */     private final AtomicBoolean downloading = new AtomicBoolean(false);
/*  882 */     private final AtomicBoolean previewLoading = new AtomicBoolean(false);
/*  883 */     private final AtomicLong downloadedBytes = new AtomicLong();
/*  884 */     private volatile long totalBytes = -1L;
/*      */     private class_1043 previewTexture;
/*      */     
/*      */     private RemotePanoramaDescriptor(String id, String displayName, String archiveName, String downloadUrl, String previewUrl) {
/*  888 */       this.id = id;
/*  889 */       this.displayName = displayName;
/*  890 */       this.archiveName = archiveName;
/*  891 */       this.downloadUri = URI.create(downloadUrl);
/*  892 */       this.previewUri = URI.create(previewUrl);
/*  893 */       this.previewTextureId = class_2960.method_60655("phaze", "dynamic/remote-panoramas/" + MenuPanoramaRegistry.sanitizeTextureToken(id) + "/preview");
/*      */     }
/*      */     
/*  896 */     public String getId() { return this.id; }
/*  897 */     public String displayName() { return this.displayName; }
/*  898 */     public class_2960 previewTexture() { ensurePreview(); return this.previewTextureId; }
/*  899 */     public int previewTextureSize() { return 1; }
/*  900 */     public int previewCropInset() { return 0; }
/*  901 */     public int previewCropSize() { return 1; }
/*  902 */     public MenuPanoramaRenderer getRenderer() { return MenuUiSettings.PanoramaPreset.VANILLA.getRenderer(); }
/*  903 */     public boolean isRemote() { return true; }
/*  904 */     public boolean isDownloading() { return this.downloading.get(); } public boolean hasPreviewTexture() {
/*  905 */       return (this.previewTexture != null);
/*      */     } public float downloadProgress() {
/*  907 */       long total = this.totalBytes;
/*  908 */       return (total > 0L) ? Math.min(1.0F, (float)this.downloadedBytes.get() / (float)total) : 0.0F;
/*      */     }
/*      */     
/*      */     private boolean isDownloaded() {
/*  912 */       return Files.isRegularFile(MenuPanoramaRegistry.getPanoramasDirectory().resolve(this.archiveName), new java.nio.file.LinkOption[0]);
/*      */     }
/*      */     
/*      */     private void ensurePreview() {
/*  916 */       class_310 client = class_310.method_1551();
/*  917 */       if (this.previewTexture != null || client == null || !this.previewLoading.compareAndSet(false, true))
/*      */         return; 
/*  919 */       Path cacheFile = MenuPanoramaRegistry.previewCacheFile(MenuPanoramaRegistry.sanitizeTextureToken(this.id), 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  926 */       CompletableFuture.runAsync(() -> {
/*      */             class_1011 cached = MenuPanoramaRegistry.readCachedPreview(cacheFile);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if (cached != null) {
/*      */               installPreview(client, cached);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               return;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             HttpRequest request = HttpRequest.newBuilder(this.previewUri).GET().build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             MenuPanoramaRegistry.HTTP_CLIENT.<byte[]>sendAsync(request, (HttpResponse.BodyHandler)HttpResponse.BodyHandlers.ofByteArray()).thenAcceptAsync(()).exceptionally(());
/*  955 */           }).exceptionally(error -> {
/*      */             this.previewLoading.set(false);
/*      */             return null;
/*      */           });
/*      */     }
/*      */     
/*      */     private void installPreview(class_310 client, class_1011 image) {
/*  962 */       client.execute(() -> {
/*      */             try {
/*      */               if (this.previewTexture == null) {
/*      */                 this.previewTexture = new class_1043(image);
/*      */                 client.method_1531().method_4616(this.previewTextureId, (class_1044)this.previewTexture);
/*      */               } else {
/*      */                 image.close();
/*      */               } 
/*  970 */             } catch (Throwable error) {
/*      */               image.close();
/*      */               System.err.println("[Phaze] remote panorama preview registration failed: " + String.valueOf(error));
/*      */             } finally {
/*      */               this.previewLoading.set(false);
/*      */             } 
/*      */           });
/*      */     }
/*      */     
/*      */     private void activate() {
/*  980 */       Path target = MenuPanoramaRegistry.getPanoramasDirectory().resolve(this.archiveName);
/*  981 */       if (Files.isRegularFile(target, new java.nio.file.LinkOption[0])) {
/*  982 */         selectDownloaded();
/*      */         return;
/*      */       } 
/*  985 */       if (!this.downloading.compareAndSet(false, true))
/*  986 */         return;  MenuPanoramaRegistry.ensureDirectoryExists();
/*  987 */       this.downloadedBytes.set(0L);
/*  988 */       this.totalBytes = -1L;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  993 */       Path temporary = target.resolveSibling(this.archiveName + ".part");
/*  994 */       HttpRequest request = HttpRequest.newBuilder(this.downloadUri).GET().build();
/*  995 */       MenuPanoramaRegistry.HTTP_CLIENT.<InputStream>sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
/*  996 */         .thenAcceptAsync(response -> { try { if (response.statusCode() < 200 || response.statusCode() >= 300)
/*      */                 throw new IOException("HTTP " + response.statusCode());  this.totalBytes = response.headers().firstValueAsLong("Content-Length").orElse(-1L); InputStream input = response.body(); try { OutputStream output = Files.newOutputStream(temporary, new java.nio.file.OpenOption[0]); 
/*      */                 try { byte[] buffer = new byte[65536]; int read; while ((read = input.read(buffer)) >= 0) {
/*      */                     output.write(buffer, 0, read); this.downloadedBytes.addAndGet(read);
/*      */                   }  if (output != null)
/*      */                     output.close();  }
/* 1002 */                 catch (Throwable throwable) { if (output != null) try { output.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (input != null) input.close();  } catch (Throwable throwable) { if (input != null) try { input.close(); } catch (Throwable throwable1)
/*      */                   { throwable.addSuppressed(throwable1); }
/*      */                 
/*      */ 
/*      */                 
/*      */                 throw throwable; }
/*      */ 
/*      */               
/*      */               ZipFile ignored = new ZipFile(temporary.toFile());
/*      */               
/*      */               ignored.close();
/*      */               Files.move(temporary, target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE });
/*      */               selectDownloaded(); }
/* 1015 */             catch (Throwable error) { try { Files.deleteIfExists(temporary); }
/* 1016 */               catch (IOException iOException) {}
/*      */               
/*      */               System.err.println("[Phaze] remote panorama download failed: " + String.valueOf(error)); }
/*      */             finally
/*      */             { this.downloading.set(false); }
/*      */           
/* 1022 */           }).exceptionally(error -> { try { Files.deleteIfExists(temporary); }
/* 1023 */             catch (IOException iOException) {}
/*      */             this.downloading.set(false);
/*      */             System.err.println("[Phaze] remote panorama download failed: " + String.valueOf(error));
/*      */             return null;
/*      */           });
/*      */     }
/*      */     
/*      */     private void selectDownloaded() {
/* 1031 */       class_310 client = class_310.method_1551();
/* 1032 */       if (client == null)
/* 1033 */         return;  client.execute(() -> {
/*      */             MenuPanoramaRegistry.reload();
/*      */             MenuUiSettings.getInstance().setSelectedPanoramaPreset(MenuPanoramaRegistry.customIdForArchiveName(this.archiveName));
/*      */           });
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\MenuPanoramaRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */