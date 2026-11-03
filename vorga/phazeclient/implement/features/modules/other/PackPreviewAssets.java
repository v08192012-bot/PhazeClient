/*      */ package vorga.phazeclient.implement.features.modules.other;
/*      */ 
/*      */ import com.google.gson.JsonArray;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.google.gson.JsonObject;
/*      */ import com.google.gson.JsonParser;
/*      */ import com.google.gson.stream.JsonReader;
/*      */ import com.google.gson.stream.JsonToken;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.StringReader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.CancellationException;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import net.minecraft.class_1011;
/*      */ import net.minecraft.class_1043;
/*      */ import net.minecraft.class_1044;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_3262;
/*      */ import net.minecraft.class_3264;
/*      */ import net.minecraft.class_3268;
/*      */ import net.minecraft.class_3288;
/*      */ import net.minecraft.class_7367;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class PackPreviewAssets
/*      */ {
/*      */   public static final long BLOCK_CYCLE_MILLIS = 1500L;
/*      */   public static final int MAX_BLOCK_SPRITES = 64;
/*      */   public static final int MAX_SPRITE_SIZE = 256;
/*      */   private static final int MAX_RESOURCES = 16384;
/*      */   private static final int MAX_NAMESPACES = 256;
/*      */   private static final int MAX_CANDIDATES = 2048;
/*      */   private static final int MAX_PNG_BYTES = 8388608;
/*      */   private static final int MAX_JSON_BYTES = 262144;
/*      */   private static final long MAX_IMAGE_PIXELS = 4194304L;
/*      */   private static final long MAX_CUSTOM_PIXELS = 67108864L;
/*      */   private static final long MAX_CUSTOM_BYTES = 134217728L;
/*   56 */   private static final AtomicLong SERIAL = new AtomicLong();
/*   57 */   private static final class_3264 TYPE = class_3264.field_14188;
/*   58 */   private static final List<String> FIXED_BLOCKS = List.of("dirt", "oak_log", "grass_block_top", "sand");
/*      */   public static final class Sprite extends Record {
/*      */     private final class_2960 id; private final int width; private final int height; private final int originalWidth;
/*      */     private final int originalHeight;
/*      */     private final boolean custom;
/*      */     
/*   64 */     public Sprite(class_2960 id, int width, int height, int originalWidth, int originalHeight, boolean custom) { this.id = id; this.width = width; this.height = height; this.originalWidth = originalWidth; this.originalHeight = originalHeight; this.custom = custom; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Sprite;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #64	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Sprite; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Sprite;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #64	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Sprite; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Sprite;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #64	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Sprite;
/*   64 */       //   0	8	1	o	Ljava/lang/Object; } public class_2960 id() { return this.id; } public int width() { return this.width; } public int height() { return this.height; } public int originalWidth() { return this.originalWidth; } public int originalHeight() { return this.originalHeight; } public boolean custom() { return this.custom; }
/*      */   
/*      */   }
/*      */   public static final class Features extends Record { private final boolean animations; private final boolean lightmap; private final boolean sounds;
/*   68 */     public Features(boolean animations, boolean lightmap, boolean sounds) { this.animations = animations; this.lightmap = lightmap; this.sounds = sounds; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Features;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #68	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Features; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Features;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #68	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Features; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Features;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #68	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Features;
/*   68 */       //   0	8	1	o	Ljava/lang/Object; } public boolean animations() { return this.animations; } public boolean lightmap() { return this.lightmap; } public boolean sounds() { return this.sounds; }
/*      */      }
/*      */   
/*      */   static LoadRequest request(class_310 client, String packId) {
/*   72 */     if (client == null || packId == null) {
/*   73 */       throw new IllegalArgumentException("client and packId are required");
/*      */     }
/*   75 */     return new LoadRequest(client, packId, client.method_1520().method_14449(packId), client
/*   76 */         .method_45573());
/*      */   } static final class LoadRequest extends Record {
/*      */     private final class_310 client; private final String packId; private final class_3288 profile; private final class_3268 vanilla;
/*   79 */     LoadRequest(class_310 client, String packId, class_3288 profile, class_3268 vanilla) { this.client = client; this.packId = packId; this.profile = profile; this.vanilla = vanilla; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #79	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #79	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #79	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;
/*   79 */       //   0	8	1	o	Ljava/lang/Object; } public class_310 client() { return this.client; } public String packId() { return this.packId; } public class_3288 profile() { return this.profile; } public class_3268 vanilla() { return this.vanilla; }
/*      */      PackPreviewAssets.Prepared decode(BooleanSupplier cancelled) {
/*   81 */       PackPreviewAssets.Loader loader = new PackPreviewAssets.Loader(this.client, this.packId, this.vanilla, cancelled);
/*   82 */       class_3262 pack = null;
/*      */       try {
/*   84 */         loader.checkCancelled();
/*   85 */         if (this.profile != null) {
/*      */           try {
/*   87 */             pack = this.profile.method_14458();
/*   88 */           } catch (CancellationException e) {
/*   89 */             throw e;
/*   90 */           } catch (RuntimeException e) {
/*   91 */             loader.note("Could not open inspected profile: " + e.getClass().getSimpleName());
/*      */           } 
/*      */         } else {
/*   94 */           loader.note("Profile not found; using vanilla assets");
/*      */         } 
/*   96 */         loader.pack = pack;
/*   97 */         return loader.load();
/*   98 */       } catch (RuntimeException|Error e) {
/*   99 */         loader.destroy();
/*  100 */         throw e;
/*      */       } finally {
/*  102 */         if (pack != null && pack != this.vanilla) {
/*      */           try {
/*  104 */             pack.close();
/*  105 */           } catch (RuntimeException runtimeException) {}
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Prepared
/*      */     implements AutoCloseable
/*      */   {
/*      */     private final PackPreviewAssets.Data data;
/*      */     private final List<class_1011> images;
/*      */     private int uploaded;
/*      */     private boolean transferred;
/*      */     private boolean closed;
/*      */     
/*      */     private Prepared(PackPreviewAssets.Data data, List<class_1011> images) {
/*  121 */       this.data = data;
/*  122 */       this.images = images;
/*      */     }
/*      */     
/*      */     long textureBytes() {
/*  126 */       return this.data.textureBytes();
/*      */     }
/*      */ 
/*      */     
/*      */     boolean upload(int maxTextures, long maxBytes) {
/*  131 */       if (this.closed || this.transferred) throw new IllegalStateException("Prepared preview is no longer owned"); 
/*  132 */       int textures = 0;
/*  133 */       long bytes = 0L;
/*  134 */       while (this.uploaded < this.images.size() && textures < maxTextures) {
/*  135 */         class_1011 image = this.images.get(this.uploaded);
/*  136 */         long size = image.method_4307() * image.method_4323() * 4L;
/*  137 */         if (bytes + size > maxBytes)
/*  138 */           break;  class_1043 texture = null;
/*      */         try {
/*  140 */           texture = new class_1043(image);
/*  141 */           this.data.client.method_1531().method_4616(this.data.owned.get(this.uploaded), (class_1044)texture);
/*  142 */         } catch (RuntimeException|Error e) {
/*  143 */           if (texture != null) {
/*  144 */             this.data.client.method_1531().method_4615(this.data.owned.get(this.uploaded));
/*  145 */             texture.close();
/*      */           } else {
/*  147 */             image.close();
/*      */           } 
/*  149 */           this.images.set(this.uploaded, (class_1011)null);
/*  150 */           close();
/*  151 */           throw e;
/*      */         } 
/*  153 */         this.images.set(this.uploaded++, (class_1011)null);
/*  154 */         textures++;
/*  155 */         bytes += size;
/*      */       } 
/*  157 */       return (this.uploaded == this.images.size());
/*      */     }
/*      */     
/*      */     PackPreviewAssets.Data takeData() {
/*  161 */       if (this.closed || this.transferred || this.uploaded != this.images.size()) {
/*  162 */         throw new IllegalStateException("Preview textures are not ready");
/*      */       }
/*  164 */       this.transferred = true;
/*  165 */       this.images.clear();
/*  166 */       return this.data;
/*      */     }
/*      */ 
/*      */     
/*      */     public void close() {
/*  171 */       if (this.closed || this.transferred)
/*  172 */         return;  this.closed = true;
/*  173 */       this.data.closed = true;
/*  174 */       for (int i = 0; i < this.uploaded; i++) {
/*  175 */         this.data.client.method_1531().method_4615(this.data.owned.get(i));
/*      */       }
/*  177 */       for (class_1011 image : this.images) {
/*  178 */         if (image != null) image.close(); 
/*      */       } 
/*  180 */       this.images.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static Features inspectFeatures(class_3262 pack) {
/*  186 */     Loader loader = new Loader(pack);
/*  187 */     loader.scan();
/*  188 */     loader.detectFeatures();
/*  189 */     return new Features(loader.animations, loader.lightmap, loader.sounds);
/*      */   }
/*      */   
/*      */   public static final class Data
/*      */     implements AutoCloseable
/*      */   {
/*      */     private final class_310 client;
/*      */     private final String packId;
/*      */     private final List<List<PackPreviewAssets.Sprite>> blockBatches;
/*      */     private final Map<String, PackPreviewAssets.Sprite> items;
/*      */     private final List<PackPreviewAssets.Sprite> hearts;
/*      */     private final Map<String, PackPreviewAssets.Sprite> particles;
/*      */     private final int resolution;
/*      */     private final int changedBlockCount;
/*      */     private final boolean animations;
/*      */     private final boolean lightmap;
/*      */     private final boolean sounds;
/*      */     private final List<String> diagnostics;
/*      */     private final List<class_2960> owned;
/*      */     private final long textureBytes;
/*      */     private boolean closed;
/*      */     
/*      */     private Data(PackPreviewAssets.Loader loader, List<List<PackPreviewAssets.Sprite>> blocks, Map<String, PackPreviewAssets.Sprite> items, List<PackPreviewAssets.Sprite> hearts, Map<String, PackPreviewAssets.Sprite> particles, int changedBlockCount) {
/*  212 */       this.client = loader.client;
/*  213 */       this.packId = loader.packId;
/*  214 */       this.blockBatches = blocks.stream().map(List::copyOf).toList();
/*  215 */       this.items = Collections.unmodifiableMap(new LinkedHashMap<>(items));
/*  216 */       this.hearts = List.copyOf(hearts);
/*  217 */       this.particles = Collections.unmodifiableMap(new LinkedHashMap<>(particles));
/*  218 */       this.resolution = PackPreviewAssetLogic.resolution(loader.widths);
/*  219 */       this.changedBlockCount = changedBlockCount;
/*  220 */       this.animations = loader.animations;
/*  221 */       this.lightmap = loader.lightmap;
/*  222 */       this.sounds = loader.sounds;
/*  223 */       this.diagnostics = List.copyOf(loader.diagnostics);
/*  224 */       this.owned = List.copyOf(loader.owned);
/*  225 */       this.textureBytes = loader.textureBytes;
/*      */     }
/*      */     
/*  228 */     public String packId() { return this.packId; } public List<List<PackPreviewAssets.Sprite>> blockBatches() {
/*  229 */       return this.blockBatches;
/*      */     }
/*      */     public List<PackPreviewAssets.Sprite> blocksAt(long elapsedMillis) {
/*  232 */       return this.blockBatches.get(PackPreviewAssetLogic.batchIndex(elapsedMillis, this.blockBatches.size()));
/*      */     } public Map<String, PackPreviewAssets.Sprite> items() {
/*  234 */       return this.items;
/*      */     }
/*  236 */     public List<PackPreviewAssets.Sprite> hearts() { return this.hearts; } public Map<String, PackPreviewAssets.Sprite> particles() {
/*  237 */       return this.particles;
/*      */     } public int resolution() {
/*  239 */       return this.resolution;
/*      */     }
/*  241 */     public int changedBlockCount() { return this.changedBlockCount; }
/*  242 */     public boolean animations() { return this.animations; }
/*  243 */     public boolean lightmap() { return this.lightmap; }
/*  244 */     public boolean sounds() { return this.sounds; } public List<String> diagnostics() {
/*  245 */       return this.diagnostics;
/*      */     }
/*  247 */     public long textureBytes() { return this.textureBytes; } public boolean isClosed() {
/*  248 */       return this.closed;
/*      */     }
/*      */     
/*      */     public void close() {
/*  252 */       if (!this.closed) {
/*  253 */         this.closed = true;
/*  254 */         for (class_2960 id : this.owned) {
/*  255 */           this.client.method_1531().method_4615(id);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static Data load(class_310 client, String packId) {
/*  263 */     Prepared prepared = request(client, packId).decode(() -> false); try {
/*  264 */       prepared.upload(2147483647, Long.MAX_VALUE);
/*  265 */       Data data = prepared.takeData();
/*  266 */       if (prepared != null) prepared.close();  return data;
/*      */     } catch (Throwable throwable) {
/*      */       if (prepared != null)
/*      */         try {
/*      */           prepared.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }   throw throwable;
/*  274 */     }  } private static final class Loader { private final class_310 client; private final String packId; private final class_3268 vanilla; private final BooleanSupplier cancelled; private final String texturePrefix = "pack_preview_assets/" + PackPreviewAssets.SERIAL.incrementAndGet() + "/";
/*  275 */     private final TreeMap<class_2960, class_7367<InputStream>> resources = new TreeMap<>(Comparator.comparing(class_2960::toString));
/*  276 */     private final Map<Integer, Integer> widths = new TreeMap<>();
/*  277 */     private final Set<String> measured = new HashSet<>();
/*  278 */     private final List<String> diagnostics = new ArrayList<>();
/*  279 */     private final List<class_2960> owned = new ArrayList<>();
/*  280 */     private final List<class_1011> images = new ArrayList<>();
/*      */     private class_3262 pack;
/*      */     private long customPixels;
/*      */     private long customBytesRead;
/*      */     private long textureBytes;
/*      */     private boolean animations;
/*      */     private boolean lightmap;
/*      */     private boolean sounds;
/*      */     
/*      */     private Loader(class_310 client, String packId, class_3268 vanilla, BooleanSupplier cancelled) {
/*  290 */       this.client = client;
/*  291 */       this.packId = packId;
/*  292 */       this.vanilla = vanilla;
/*  293 */       this.cancelled = cancelled;
/*      */     }
/*      */     
/*      */     private Loader(class_3262 pack) {
/*  297 */       this.client = null;
/*  298 */       this.packId = "feature-inspection";
/*  299 */       this.vanilla = null;
/*  300 */       this.cancelled = (() -> false);
/*  301 */       this.pack = pack;
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Prepared load() {
/*  305 */       scan();
/*      */       
/*  307 */       Map<String, PackPreviewAssets.Sprite> items = new LinkedHashMap<>();
/*  308 */       for (Map.Entry<String, String> entry : PackPreviewAssetLogic.itemPaths().entrySet()) {
/*  309 */         checkCancelled();
/*  310 */         PackPreviewAssets.Image image = preferred(class_2960.method_60656(entry.getValue()), true); 
/*  311 */         try { items.put(entry.getKey(), register(image));
/*  312 */           if (image != null) image.close();  } catch (Throwable throwable) { if (image != null)
/*      */             try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  314 */       }  List<PackPreviewAssets.Sprite> hearts = loadHearts();
/*  315 */       Map<String, PackPreviewAssets.Sprite> particles = new LinkedHashMap<>();
/*  316 */       for (String name : List.<String>of("critical_hit", "enchanted_hit")) {
/*  317 */         PackPreviewAssets.Image image = preferred(class_2960.method_60656("textures/particle/" + name + ".png"), false); 
/*  318 */         try { particles.put(name, register(image));
/*  319 */           if (image != null) image.close();  } catch (Throwable throwable) { if (image != null)
/*      */             try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  321 */       }  detectFeatures();
/*  322 */       List<List<PackPreviewAssets.Sprite>> blocks = new ArrayList<>();
/*  323 */       int changed = loadBlocks(blocks);
/*  324 */       measureOtherItems();
/*  325 */       checkCancelled();
/*  326 */       return new PackPreviewAssets.Prepared(new PackPreviewAssets.Data(this, blocks, items, hearts, particles, changed), this.images);
/*      */     }
/*      */     
/*      */     private void checkCancelled() {
/*  330 */       if (this.cancelled.getAsBoolean()) throw new CancellationException("Pack preview was invalidated"); 
/*      */     }
/*      */     
/*      */     private void note(String message) {
/*  334 */       if (this.diagnostics.contains(message)) {
/*      */         return;
/*      */       }
/*  337 */       if (message.contains("limited to") || message.contains("budget reached") || message.startsWith("Cycling retains")) {
/*  338 */         this.diagnostics.add(0, message);
/*  339 */         if (this.diagnostics.size() > 32) {
/*  340 */           this.diagnostics.removeLast();
/*      */         }
/*  342 */       } else if (this.diagnostics.size() < 32) {
/*  343 */         this.diagnostics.add(message);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void scan() {
/*  348 */       checkCancelled();
/*  349 */       if (this.pack == null) {
/*      */         return;
/*      */       }
/*      */       try {
/*  353 */         List<String> namespaces = this.pack.method_14406(PackPreviewAssets.TYPE).stream().sorted().toList();
/*  354 */         if (namespaces.size() > 256) {
/*  355 */           note("Namespace scan limited to 256");
/*      */         }
/*  357 */         for (Iterator<String> iterator = namespaces.subList(0, Math.min(namespaces.size(), 256)).iterator(); iterator.hasNext(); ) { String namespace = iterator.next();
/*  358 */           checkCancelled();
/*      */           
/*      */           try {
/*  361 */             class_3262.class_7664 collect = (id, supplier) -> {
/*      */                 checkCancelled();
/*      */                 
/*      */                 if (!relevant(id.method_12832())) {
/*      */                   return;
/*      */                 }
/*      */                 
/*      */                 if (this.resources.size() < 16384 || this.resources.containsKey(id)) {
/*      */                   this.resources.put(id, supplier);
/*      */                 } else {
/*      */                   note("Resource index limited to 16384; feature detection may be partial");
/*      */                   
/*      */                   if (this.resources.comparator().compare(id, this.resources.lastKey()) < 0) {
/*      */                     this.resources.pollLastEntry();
/*      */                     this.resources.put(id, supplier);
/*      */                   } 
/*      */                 } 
/*      */               };
/*  379 */             for (String prefix : List.<String>of("textures", "sounds", "optifine/lightmap", "mcpatcher/lightmap")) {
/*  380 */               checkCancelled();
/*  381 */               this.pack.method_14408(PackPreviewAssets.TYPE, namespace, prefix, collect);
/*      */             } 
/*  383 */             class_2960 soundsJson = class_2960.method_60655(namespace, "sounds.json");
/*  384 */             class_7367<InputStream> definitions = this.pack.method_14405(PackPreviewAssets.TYPE, soundsJson);
/*  385 */             if (definitions != null) {
/*  386 */               collect.accept(soundsJson, definitions);
/*      */             }
/*  388 */           } catch (CancellationException e) {
/*  389 */             throw e;
/*  390 */           } catch (RuntimeException e) {
/*  391 */             note("Partial namespace scan (" + namespace + "): " + e.getClass().getSimpleName());
/*      */           }  }
/*      */       
/*  394 */       } catch (CancellationException e) {
/*  395 */         throw e;
/*  396 */       } catch (RuntimeException e) {
/*  397 */         note("Partial resource scan: " + e.getClass().getSimpleName());
/*      */       } 
/*      */     }
/*      */     
/*      */     private boolean relevant(String path) {
/*  402 */       return (path.startsWith("textures/") || path.startsWith("sounds/") || path.equals("sounds.json") || path
/*  403 */         .startsWith("optifine/lightmap/") || path.startsWith("mcpatcher/lightmap/"));
/*      */     }
/*      */     
/*      */     private void destroy() {
/*  407 */       this.images.forEach(class_1011::close);
/*  408 */       this.images.clear();
/*  409 */       this.owned.clear();
/*      */     }
/*      */     
/*      */     private int loadBlocks(List<List<PackPreviewAssets.Sprite>> batches) {
/*  413 */       Map<String, class_2960> candidates = new TreeMap<>();
/*  414 */       for (class_2960 id : this.resources.keySet()) {
/*  415 */         checkCancelled();
/*  416 */         String path = PackPreviewAssetLogic.canonicalPath(id.method_12832());
/*  417 */         if (!path.startsWith("textures/block/") || !path.endsWith(".png")) {
/*      */           continue;
/*      */         }
/*  420 */         String key = id.method_12836() + ":" + id.method_12836();
/*  421 */         candidates.putIfAbsent(key, class_2960.method_60654(key));
/*      */       } 
/*  423 */       List<PackPreviewAssets.Image> selected = new ArrayList<>();
/*  424 */       int changed = 0;
/*  425 */       int inspected = 0;
/*      */       try {
/*  427 */         for (String key : PackPreviewAssetLogic.<Comparable>shuffled(new ArrayList<>(candidates.keySet()), this.packId)) {
/*  428 */           checkCancelled();
/*  429 */           if (inspected++ == 2048) {
/*  430 */             note("Block comparison limited to 2048 candidates");
/*      */             break;
/*      */           } 
/*  433 */           class_2960 id = candidates.get(key);
/*  434 */           PackPreviewAssets.Raw custom = customRaw(id); 
/*  435 */           try { boolean different; if (custom == null)
/*      */             
/*      */             { 
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
/*  453 */               if (custom != null) custom.close();  continue; }  rememberWidth(custom); class_2960 canonical = class_2960.method_60654(key); PackPreviewAssets.Raw base = raw(canonical, false); try { different = (base == null || (!Arrays.equals(custom.encoded, base.encoded) && !PackPreviewAssetLogic.samePixels(custom.pixels.method_4307(), custom.pixels.method_4323(), custom.pixels.method_61942(), base.pixels.method_4307(), base.pixels.method_4323(), base.pixels.method_61942()))); if (base != null) base.close();  } catch (Throwable throwable) { if (base != null) try { base.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (different) { changed++; if (selected.size() < 64) selected.add(frame(custom, true));  }  if (custom != null) custom.close();  } catch (Throwable different) { Throwable throwable; if (custom != null)
/*      */               try { custom.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  455 */         }  if (changed < 4) {
/*      */           
/*  457 */           List<PackPreviewAssets.Sprite> fixed = new ArrayList<>(4);
/*  458 */           for (String name : PackPreviewAssets.FIXED_BLOCKS) {
/*  459 */             PackPreviewAssets.Image image = fallback(class_2960.method_60656("textures/block/" + name + ".png")); 
/*  460 */             try { fixed.add(register(image));
/*  461 */               if (image != null) image.close();  } catch (Throwable throwable) { if (image != null)
/*      */                 try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  463 */           }  batches.add(fixed);
/*      */         } else {
/*  465 */           List<PackPreviewAssets.Sprite> sprites = new ArrayList<>();
/*  466 */           for (PackPreviewAssets.Image image : selected) {
/*  467 */             sprites.add(register(image));
/*      */           }
/*  469 */           batches.addAll(PackPreviewAssetLogic.blockBatches(sprites));
/*  470 */           if (changed > 64) {
/*  471 */             note("Cycling retains 64 of " + changed + " verified changed blocks");
/*      */           }
/*      */         } 
/*      */       } finally {
/*  475 */         selected.forEach(PackPreviewAssets.Image::close);
/*      */       } 
/*  477 */       return changed;
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Image preferred(class_2960 canonical, boolean item) {
/*  481 */       PackPreviewAssets.Raw raw = customRaw(canonical); 
/*  482 */       try { if (raw != null)
/*  483 */         { if (item) {
/*  484 */             rememberWidth(raw);
/*      */           }
/*  486 */           PackPreviewAssets.Image image = frame(raw, true);
/*      */           
/*  488 */           if (raw != null) raw.close();  return image; }  if (raw != null) raw.close();  } catch (Throwable throwable) { if (raw != null)
/*  489 */           try { raw.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return fallback(canonical);
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Raw customRaw(class_2960 canonical) {
/*  493 */       for (String path : PackPreviewAssetLogic.aliases(canonical.method_12832())) {
/*  494 */         PackPreviewAssets.Raw raw = raw(class_2960.method_60655(canonical.method_12836(), path), true);
/*  495 */         if (raw != null) {
/*  496 */           return raw;
/*      */         }
/*      */       } 
/*  499 */       return null;
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Image fallback(class_2960 id) {
/*  503 */       PackPreviewAssets.Raw raw = raw(id, false); 
/*  504 */       try { if (raw != null)
/*  505 */         { PackPreviewAssets.Image image1 = frame(raw, false);
/*      */           
/*  507 */           if (raw != null) raw.close();  return image1; }  if (raw != null) raw.close();  } catch (Throwable throwable) { if (raw != null)
/*  508 */           try { raw.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  note("Vanilla asset unavailable: " + String.valueOf(id) + "; generated placeholder");
/*  509 */       class_1011 image = new class_1011(16, 16, true);
/*  510 */       for (int y = 0; y < 16; y++) {
/*  511 */         for (int x = 0; x < 16; x++) {
/*  512 */           image.method_61941(x, y, ((x / 4 + y / 4 & 0x1) == 0) ? -65281 : -15395563);
/*      */         }
/*      */       } 
/*  515 */       return new PackPreviewAssets.Image(image, 16, 16, false);
/*      */     }
/*      */     
/*      */     private void rememberWidth(PackPreviewAssets.Raw raw) {
/*  519 */       String canonical = raw.id.method_12836() + ":" + raw.id.method_12836();
/*  520 */       if (this.measured.add(canonical)) {
/*  521 */         this.widths.merge(Integer.valueOf(raw.frame.width), Integer.valueOf(1), Integer::sum);
/*      */       }
/*      */     }
/*      */     
/*      */     private void measureOtherItems() {
/*  526 */       int count = 0;
/*  527 */       for (class_2960 id : this.resources.keySet()) {
/*  528 */         checkCancelled();
/*  529 */         String path = PackPreviewAssetLogic.canonicalPath(id.method_12832());
/*  530 */         if (path.startsWith("textures/item/") && path.endsWith(".png") && 
/*  531 */           !this.measured.contains(id.method_12836() + ":" + id.method_12836())) {
/*  532 */           if (count++ == 2048) {
/*  533 */             note("Item resolution scan limited to 2048 candidates");
/*      */             
/*      */             break;
/*      */           } 
/*  537 */           for (String alias : PackPreviewAssetLogic.aliases(path)) {
/*  538 */             PackPreviewAssets.Raw raw = raw(class_2960.method_60655(id.method_12836(), alias), true); 
/*  539 */             try { if (raw != null)
/*  540 */               { rememberWidth(raw);
/*      */ 
/*      */                 
/*  543 */                 if (raw != null) raw.close();  break; }  if (raw != null) raw.close();  } catch (Throwable throwable) { if (raw != null)
/*      */                 try { raw.close(); }
/*      */                 catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */                   throw throwable; }
/*      */           
/*      */           } 
/*      */         } 
/*  550 */       }  } private PackPreviewAssets.Raw raw(class_2960 id, boolean custom) { checkCancelled();
/*  551 */       if (custom && this.customPixels >= 67108864L) {
/*  552 */         note("Custom PNG decode budget reached (64 million pixels); detection/resolution may be partial");
/*  553 */         return null;
/*      */       } 
/*  555 */       byte[] bytes = bytes(id, custom, 8388608);
/*  556 */       if (bytes == null) {
/*  557 */         return null;
/*      */       }
/*  559 */       int[] size = PackPreviewAssets.pngSize(bytes);
/*  560 */       if (size == null) {
/*  561 */         note("Invalid or oversized PNG: " + String.valueOf(id));
/*  562 */         return null;
/*      */       } 
/*  564 */       long pixels = size[0] * size[1];
/*  565 */       if (custom && this.customPixels + pixels > 67108864L) {
/*  566 */         note("Custom PNG decode budget reached (64 million pixels); detection/resolution may be partial");
/*  567 */         return null;
/*      */       } 
/*  569 */       if (custom) {
/*  570 */         this.customPixels += pixels;
/*      */       }
/*      */       try {
/*  573 */         class_1011 image = class_1011.method_49277(bytes);
/*      */         try {
/*  575 */           checkCancelled();
/*  576 */           return new PackPreviewAssets.Raw(id, image, bytes, frameInfo(id, custom, image.method_4307(), image.method_4323()));
/*  577 */         } catch (RuntimeException|Error e) {
/*  578 */           image.close();
/*  579 */           throw e;
/*      */         } 
/*  581 */       } catch (CancellationException e) {
/*  582 */         throw e;
/*  583 */       } catch (IOException|RuntimeException e) {
/*  584 */         note("Unreadable PNG: " + String.valueOf(id));
/*  585 */         return null;
/*      */       }  }
/*      */ 
/*      */     
/*      */     private byte[] bytes(class_2960 id, boolean custom, int limit) {
/*  590 */       checkCancelled();
/*  591 */       class_3262 source = custom ? this.pack : (class_3262)this.vanilla;
/*  592 */       if (source == null) {
/*  593 */         return null;
/*      */       }
/*  595 */       long remaining = 134217728L - this.customBytesRead;
/*  596 */       if (custom && remaining <= 0L) {
/*  597 */         note("Custom resource read budget reached (128MiB); detection/resolution may be partial");
/*  598 */         return null;
/*      */       } 
/*      */       
/*  601 */       try { class_7367<InputStream> supplier = custom ? this.resources.get(id) : null;
/*  602 */         if (supplier == null) {
/*  603 */           supplier = source.method_14405(PackPreviewAssets.TYPE, id);
/*      */         }
/*  605 */         if (supplier == null) {
/*  606 */           return null;
/*      */         }
/*  608 */         InputStream input = (InputStream)supplier.get(); 
/*  609 */         try { int readLimit = custom ? (int)Math.min(limit, remaining) : limit;
/*  610 */           byte[] bytes = input.readNBytes(readLimit + 1);
/*  611 */           checkCancelled();
/*  612 */           if (custom) {
/*  613 */             this.customBytesRead += bytes.length;
/*      */           }
/*  615 */           if (bytes.length > readLimit)
/*  616 */           { if (readLimit < limit) {
/*  617 */               note("Custom resource read budget reached (128MiB); detection/resolution may be partial");
/*      */             } else {
/*  619 */               note("Resource exceeds byte limit: " + String.valueOf(id));
/*      */             } 
/*  621 */             byte[] arrayOfByte = null;
/*      */ 
/*      */             
/*  624 */             if (input != null) input.close();  return arrayOfByte; }  byte[] arrayOfByte1 = bytes; if (input != null) input.close();  return arrayOfByte1; } catch (Throwable throwable) { if (input != null)
/*  625 */             try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (CancellationException e)
/*  626 */       { throw e; }
/*  627 */       catch (IOException|RuntimeException e)
/*  628 */       { note("Unreadable resource: " + String.valueOf(id));
/*  629 */         return null; }
/*      */     
/*      */     }
/*      */     
/*      */     private JsonObject json(class_2960 id, boolean custom) {
/*  634 */       byte[] bytes = bytes(id, custom, 262144);
/*  635 */       if (bytes == null) {
/*  636 */         return null;
/*      */       }
/*      */       try {
/*  639 */         String text = new String(bytes, StandardCharsets.UTF_8);
/*      */ 
/*      */         
/*  642 */         JsonReader validation = new JsonReader(new StringReader(text)); 
/*  643 */         try { validation.setLenient(false);
/*  644 */           validation.skipValue();
/*  645 */           if (validation.peek() != JsonToken.END_DOCUMENT)
/*  646 */           { note("Invalid JSON: " + String.valueOf(id));
/*  647 */             JsonObject jsonObject = null;
/*      */             
/*  649 */             validation.close(); return jsonObject; }  validation.close(); } catch (Throwable throwable) { try { validation.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*  650 */          JsonElement json = JsonParser.parseString(text);
/*  651 */         return json.isJsonObject() ? json.getAsJsonObject() : null;
/*  652 */       } catch (IOException|RuntimeException|StackOverflowError e) {
/*  653 */         note("Invalid JSON: " + String.valueOf(id));
/*  654 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Frame frameInfo(class_2960 id, boolean custom, int width, int height) {
/*  659 */       JsonObject metadata = json(class_2960.method_60655(id.method_12836(), id.method_12832() + ".mcmeta"), custom);
/*  660 */       JsonObject animation = PackPreviewAssets.animation(metadata);
/*  661 */       int frameWidth = width;
/*  662 */       int frameHeight = height;
/*  663 */       if (animation != null) {
/*  664 */         if (!PackPreviewAssets.validFrameGeometry(animation, width, height)) {
/*  665 */           note("Invalid animation frame geometry/index: " + String.valueOf(id));
/*  666 */           return new PackPreviewAssets.Frame(0, 0, width, height);
/*      */         } 
/*  668 */         if (animation.has("width")) {
/*  669 */           frameWidth = animation.get("width").getAsInt();
/*  670 */           frameHeight = animation.has("height") ? animation.get("height").getAsInt() : height;
/*  671 */         } else if (animation.has("height")) {
/*  672 */           frameHeight = animation.get("height").getAsInt();
/*      */         } else {
/*  674 */           frameWidth = frameHeight = Math.min(width, height);
/*      */         } 
/*  676 */         if (frameWidth > width || frameHeight > height || width % frameWidth != 0 || height % frameHeight != 0) {
/*  677 */           note("Invalid animation frame geometry: " + String.valueOf(id));
/*  678 */           return new PackPreviewAssets.Frame(0, 0, width, height);
/*      */         } 
/*  680 */         int first = 0;
/*  681 */         if (animation.has("frames") && !animation.getAsJsonArray("frames").isEmpty()) {
/*  682 */           JsonElement entry = animation.getAsJsonArray("frames").get(0);
/*  683 */           first = entry.isJsonObject() ? entry.getAsJsonObject().get("index").getAsInt() : entry.getAsInt();
/*      */         } 
/*  685 */         int columns = width / frameWidth;
/*  686 */         int frameCount = columns * height / frameHeight;
/*  687 */         if (first >= frameCount) {
/*  688 */           note("Animation frame index out of range: " + String.valueOf(id));
/*  689 */           first = 0;
/*      */         } 
/*  691 */         return new PackPreviewAssets.Frame(first % columns * frameWidth, first / columns * frameHeight, frameWidth, frameHeight);
/*      */       } 
/*  693 */       return new PackPreviewAssets.Frame(0, 0, frameWidth, frameHeight);
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Image frame(PackPreviewAssets.Raw raw, boolean custom) {
/*  697 */       PackPreviewAssets.Frame f = raw.frame;
/*  698 */       double scale = Math.min(1.0D, 256.0D / Math.max(f.width, f.height));
/*  699 */       int width = Math.max(1, (int)Math.round(f.width * scale));
/*  700 */       int height = Math.max(1, (int)Math.round(f.height * scale));
/*  701 */       class_1011 result = new class_1011(width, height, true);
/*  702 */       for (int y = 0; y < height; y++) {
/*  703 */         for (int x = 0; x < width; x++) {
/*  704 */           result.method_61941(x, y, raw.pixels.method_61940(f.x + x * f.width / width, f.y + y * f.height / height));
/*      */         }
/*      */       } 
/*  707 */       return new PackPreviewAssets.Image(result, f.width, f.height, custom);
/*      */     }
/*      */ 
/*      */     
/*      */     private PackPreviewAssets.Sprite register(PackPreviewAssets.Image image) {
/*  712 */       class_1011 copy = new class_1011(image.pixels.method_4307(), image.pixels.method_4323(), true);
/*  713 */       class_2960 id = class_2960.method_60655("phaze", this.texturePrefix + this.texturePrefix);
/*      */       try {
/*  715 */         copy.method_4317(image.pixels);
/*  716 */         this.images.add(copy);
/*  717 */         this.owned.add(id);
/*  718 */         this.textureBytes += copy.method_4307() * copy.method_4323() * 4L;
/*  719 */         return new PackPreviewAssets.Sprite(id, copy.method_4307(), copy.method_4323(), image.originalWidth, image.originalHeight, image.custom);
/*  720 */       } catch (RuntimeException|Error e) {
/*  721 */         copy.close();
/*  722 */         throw e;
/*      */       } 
/*      */     }
/*      */     
/*      */     private List<PackPreviewAssets.Sprite> loadHearts() {
/*  727 */       List<PackPreviewAssets.Sprite> result = new ArrayList<>(3);
/*  728 */       PackPreviewAssets.Image container = heartLayer("container", 16); 
/*  729 */       try { PackPreviewAssets.Image full = heartLayer("full", 52); 
/*  730 */         try { PackPreviewAssets.Image half = heartLayer("half", 61); 
/*  731 */           try { PackPreviewAssets.Image composed = compose(container, full); 
/*  732 */             try { result.add(register(composed));
/*  733 */               if (composed != null) composed.close();  } catch (Throwable throwable) { if (composed != null)
/*  734 */                 try { composed.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  composed = compose(container, half); 
/*  735 */             try { result.add(register(composed));
/*  736 */               if (composed != null) composed.close();  } catch (Throwable throwable) { if (composed != null)
/*  737 */                 try { composed.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  result.add(register(container));
/*  738 */             if (half != null) half.close();  } catch (Throwable throwable) { if (half != null) try { half.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (full != null) full.close();  } catch (Throwable throwable) { if (full != null) try { full.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (container != null) container.close();  } catch (Throwable throwable) { if (container != null)
/*  739 */           try { container.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return result;
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Image heartLayer(String name, int legacyX) {
/*  743 */       class_2960 modern = class_2960.method_60656("textures/gui/sprites/hud/heart/" + name + ".png");
/*  744 */       PackPreviewAssets.Raw custom = raw(modern, true); 
/*  745 */       try { if (custom != null)
/*  746 */         { PackPreviewAssets.Image image = frame(custom, true);
/*      */           
/*  748 */           if (custom != null) custom.close();  return image; }  if (custom != null) custom.close();  } catch (Throwable throwable) { if (custom != null)
/*      */           try { custom.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  750 */        PackPreviewAssets.Raw atlas = raw(class_2960.method_60656("textures/gui/icons.png"), true); 
/*  751 */       try { if (atlas != null && atlas.pixels.method_4307() % 256 == 0 && atlas.pixels.method_4323() % 256 == 0)
/*  752 */         { int sx = atlas.pixels.method_4307() / 256;
/*  753 */           int sy = atlas.pixels.method_4323() / 256;
/*  754 */           PackPreviewAssets.Raw crop = new PackPreviewAssets.Raw(atlas.id, atlas.pixels, atlas.encoded, new PackPreviewAssets.Frame(legacyX * sx, 0, 9 * sx, 9 * sy));
/*  755 */           PackPreviewAssets.Image image = frame(crop, true);
/*      */           
/*  757 */           if (atlas != null) atlas.close();  return image; }  if (atlas != null) atlas.close();  } catch (Throwable throwable) { if (atlas != null)
/*  758 */           try { atlas.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return fallback(modern);
/*      */     }
/*      */     
/*      */     private PackPreviewAssets.Image compose(PackPreviewAssets.Image container, PackPreviewAssets.Image fill) {
/*  762 */       int width = Math.max(container.pixels.method_4307(), fill.pixels.method_4307());
/*  763 */       int height = Math.max(container.pixels.method_4323(), fill.pixels.method_4323());
/*  764 */       class_1011 result = new class_1011(width, height, true);
/*  765 */       for (int y = 0; y < height; y++) {
/*  766 */         for (int x = 0; x < width; x++) {
/*  767 */           int background = container.pixels.method_61940(x * container.pixels.method_4307() / width, y * container.pixels
/*  768 */               .method_4323() / height);
/*  769 */           int foreground = fill.pixels.method_61940(x * fill.pixels.method_4307() / width, y * fill.pixels
/*  770 */               .method_4323() / height);
/*  771 */           result.method_61941(x, y, PackPreviewAssetLogic.over(background, foreground));
/*      */         } 
/*      */       } 
/*  774 */       return new PackPreviewAssets.Image(result, Math.max(container.originalWidth, fill.originalWidth), 
/*  775 */           Math.max(container.originalHeight, fill.originalHeight), (container.custom || fill.custom));
/*      */     }
/*      */     
/*      */     private void detectFeatures() {
/*  779 */       int metadataCount = 0;
/*  780 */       for (class_2960 id : this.resources.keySet()) {
/*  781 */         checkCancelled();
/*  782 */         String path = id.method_12832();
/*  783 */         if (!this.sounds && path.startsWith("sounds/") && path.endsWith(".ogg")) {
/*  784 */           byte[] header = header(id, 4);
/*  785 */           this.sounds = (header != null && Arrays.equals(header, new byte[] { 79, 103, 103, 83 }));
/*      */         } 
/*  787 */         if (!this.sounds && path.equals("sounds.json")) {
/*  788 */           this.sounds = PackPreviewAssets.soundDefinitions(json(id, true));
/*      */         }
/*  790 */         if (!this.lightmap && path.endsWith(".png") && (path.startsWith("optifine/lightmap/") || path
/*  791 */           .startsWith("mcpatcher/lightmap/") || path.equals("textures/misc/lightmap.png"))) {
/*  792 */           this.lightmap = (PackPreviewAssets.pngSize(header(id, 24)) != null);
/*      */         }
/*  794 */         if (!this.animations && path.startsWith("textures/") && path.endsWith(".png.mcmeta")) {
/*  795 */           if (metadataCount++ >= 2048) {
/*  796 */             note("Animation metadata scan limited to 2048");
/*      */             continue;
/*      */           } 
/*  799 */           class_2960 png = class_2960.method_60655(id.method_12836(), path.substring(0, path.length() - 7));
/*  800 */           int[] size = PackPreviewAssets.pngSize(header(png, 24));
/*  801 */           if (size != null) {
/*  802 */             JsonObject anim = PackPreviewAssets.animation(json(id, true));
/*  803 */             this.animations = (anim != null && PackPreviewAssets.validFrameGeometry(anim, size[0], size[1]));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private byte[] header(class_2960 id, int size) {
/*  810 */       checkCancelled();
/*  811 */       if (this.pack == null) {
/*  812 */         return null;
/*      */       }
/*      */       try {
/*  815 */         class_7367<InputStream> supplier = this.resources.get(id);
/*  816 */         if (supplier == null) {
/*  817 */           supplier = this.pack.method_14405(PackPreviewAssets.TYPE, id);
/*      */         }
/*  819 */         if (supplier != null)
/*  820 */         { InputStream stream = (InputStream)supplier.get(); 
/*  821 */           try { byte[] bytes = stream.readNBytes(size);
/*  822 */             checkCancelled();
/*  823 */             byte[] arrayOfByte1 = bytes;
/*  824 */             if (stream != null) stream.close();  return arrayOfByte1; } catch (Throwable throwable) { if (stream != null)
/*      */               try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } 
/*  826 */       } catch (CancellationException e) {
/*  827 */         throw e;
/*  828 */       } catch (IOException|RuntimeException e) {
/*  829 */         note("Unreadable resource header: " + String.valueOf(id));
/*      */       } 
/*  831 */       return null;
/*      */     } }
/*      */ 
/*      */   
/*      */   private static int[] pngSize(byte[] bytes) {
/*  836 */     if (bytes == null || bytes.length < 24 || bytes[0] != -119 || bytes[1] != 80 || bytes[2] != 78 || bytes[3] != 71 || bytes[4] != 13 || bytes[5] != 10 || bytes[6] != 26 || bytes[7] != 10 || bytes[12] != 73 || bytes[13] != 72 || bytes[14] != 68 || bytes[15] != 82)
/*      */     {
/*      */ 
/*      */       
/*  840 */       return null;
/*      */     }
/*  842 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
/*  843 */     if (buffer.getInt(8) != 13) {
/*  844 */       return null;
/*      */     }
/*  846 */     int width = buffer.getInt(16);
/*  847 */     int height = buffer.getInt(20);
/*      */     
/*  849 */     (new int[2])[0] = width; (new int[2])[1] = height; return (width > 0 && height > 0 && width <= 8192 && height <= 8192 && width * height <= 4194304L) ? new int[2] : null;
/*      */   }
/*      */   
/*      */   private static boolean integer(JsonElement element, int minimum) {
/*      */     try {
/*  854 */       return (element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber() && element
/*  855 */         .getAsBigDecimal().intValueExact() >= minimum);
/*  856 */     } catch (RuntimeException e) {
/*  857 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static JsonObject animation(JsonObject metadata) {
/*  863 */     if (metadata == null || !metadata.has("animation") || !metadata.get("animation").isJsonObject()) {
/*  864 */       return null;
/*      */     }
/*  866 */     JsonObject animation = metadata.getAsJsonObject("animation");
/*  867 */     for (String key : List.<String>of("width", "height", "frametime")) {
/*  868 */       if (animation.has(key) && !integer(animation.get(key), 1)) {
/*  869 */         return null;
/*      */       }
/*      */     } 
/*  872 */     if (animation.has("interpolate") && (!animation.get("interpolate").isJsonPrimitive() || 
/*  873 */       !animation.getAsJsonPrimitive("interpolate").isBoolean())) {
/*  874 */       return null;
/*      */     }
/*  876 */     if (animation.has("frames")) {
/*  877 */       if (!animation.get("frames").isJsonArray()) {
/*  878 */         return null;
/*      */       }
/*  880 */       for (JsonElement entry : animation.getAsJsonArray("frames")) {
/*  881 */         if (entry.isJsonObject()) {
/*  882 */           JsonObject frame = entry.getAsJsonObject();
/*  883 */           if (!integer(frame.get("index"), 0) || (frame.has("time") && !integer(frame.get("time"), 1)))
/*  884 */             return null;  continue;
/*      */         } 
/*  886 */         if (!integer(entry, 0)) {
/*  887 */           return null;
/*      */         }
/*      */       } 
/*      */     } 
/*  891 */     return animation;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean validFrameGeometry(JsonObject animation, int width, int height) {
/*  896 */     int fw = animation.has("width") ? animation.get("width").getAsInt() : (animation.has("height") ? width : Math.min(width, height));
/*      */     
/*  898 */     int fh = animation.has("height") ? animation.get("height").getAsInt() : (animation.has("width") ? height : Math.min(width, height));
/*  899 */     if (fw > width || fh > height || width % fw != 0 || height % fh != 0) {
/*  900 */       return false;
/*      */     }
/*  902 */     int count = width / fw * height / fh;
/*  903 */     if (animation.has("frames")) {
/*  904 */       for (JsonElement entry : animation.getAsJsonArray("frames")) {
/*  905 */         int index = entry.isJsonObject() ? entry.getAsJsonObject().get("index").getAsInt() : entry.getAsInt();
/*  906 */         if (index >= count) {
/*  907 */           return false;
/*      */         }
/*      */       } 
/*      */     }
/*  911 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean soundDefinitions(JsonObject definitions) {
/*  916 */     if (definitions == null) {
/*  917 */       return false;
/*      */     }
/*  919 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)definitions.entrySet()) {
/*  920 */       if (((String)entry.getKey()).isBlank() || class_2960.method_12829(entry.getKey()) == null || !((JsonElement)entry.getValue()).isJsonObject()) {
/*      */         continue;
/*      */       }
/*  923 */       JsonObject event = ((JsonElement)entry.getValue()).getAsJsonObject();
/*  924 */       if (!event.has("sounds") || !event.get("sounds").isJsonArray()) {
/*      */         continue;
/*      */       }
/*  927 */       if (event.has("replace") && (!event.get("replace").isJsonPrimitive() || 
/*  928 */         !event.getAsJsonPrimitive("replace").isBoolean())) {
/*      */         continue;
/*      */       }
/*  931 */       if (event.has("subtitle") && (!event.get("subtitle").isJsonPrimitive() || 
/*  932 */         !event.getAsJsonPrimitive("subtitle").isString())) {
/*      */         continue;
/*      */       }
/*  935 */       JsonArray sounds = event.getAsJsonArray("sounds");
/*  936 */       boolean valid = !sounds.isEmpty();
/*  937 */       for (JsonElement sound : sounds) {
/*  938 */         if (!validSound(sound)) {
/*  939 */           valid = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  943 */       if (valid) {
/*  944 */         return true;
/*      */       }
/*      */     } 
/*  947 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean validSound(JsonElement sound) {
/*  951 */     JsonElement name = sound;
/*  952 */     if (sound.isJsonObject()) {
/*  953 */       JsonObject object = sound.getAsJsonObject();
/*  954 */       name = object.get("name");
/*  955 */       if (object.has("type")) {
/*  956 */         JsonElement type = object.get("type");
/*  957 */         if (!type.isJsonPrimitive() || !type.getAsJsonPrimitive().isString() || 
/*  958 */           !List.<String>of("file", "event").contains(type.getAsString())) {
/*  959 */           return false;
/*      */         }
/*      */       } 
/*  962 */       if (object.has("weight") && !integer(object.get("weight"), 1)) {
/*  963 */         return false;
/*      */       }
/*  965 */       if (object.has("attenuation_distance") && !integer(object.get("attenuation_distance"), 0)) {
/*  966 */         return false;
/*      */       }
/*  968 */       for (String key : List.<String>of("stream", "preload")) {
/*  969 */         if (object.has(key) && (!object.get(key).isJsonPrimitive() || 
/*  970 */           !object.getAsJsonPrimitive(key).isBoolean())) {
/*  971 */           return false;
/*      */         }
/*      */       } 
/*  974 */       for (String key : List.<String>of("volume", "pitch")) {
/*  975 */         if (object.has(key)) {
/*  976 */           JsonElement value = object.get(key);
/*  977 */           if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber() || 
/*  978 */             !Double.isFinite(value.getAsDouble()) || value.getAsDouble() <= 0.0D) {
/*  979 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  984 */     return (name != null && name.isJsonPrimitive() && name.getAsJsonPrimitive().isString() && 
/*  985 */       !name.getAsString().isBlank() && class_2960.method_12829(name.getAsString()) != null);
/*      */   } private static final class Frame extends Record {
/*      */     private final int x; private final int y; private final int width; private final int height;
/*  988 */     private Frame(int x, int y, int width, int height) { this.x = x; this.y = y; this.width = width; this.height = height; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Frame;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #988	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Frame; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Frame;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #988	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Frame; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Frame;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #988	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Frame;
/*  988 */       //   0	8	1	o	Ljava/lang/Object; } public int x() { return this.x; } public int y() { return this.y; } public int width() { return this.width; } public int height() { return this.height; }
/*      */      } private static final class Raw extends Record implements AutoCloseable {
/*      */     private final class_2960 id; private final class_1011 pixels; private final byte[] encoded; private final PackPreviewAssets.Frame frame;
/*  991 */     private Raw(class_2960 id, class_1011 pixels, byte[] encoded, PackPreviewAssets.Frame frame) { this.id = id; this.pixels = pixels; this.encoded = encoded; this.frame = frame; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Raw;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #991	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Raw; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Raw;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #991	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Raw; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Raw;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #991	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Raw;
/*  991 */       //   0	8	1	o	Ljava/lang/Object; } public class_2960 id() { return this.id; } public class_1011 pixels() { return this.pixels; } public byte[] encoded() { return this.encoded; } public PackPreviewAssets.Frame frame() { return this.frame; }
/*      */     
/*      */     public void close() {
/*  994 */       this.pixels.close();
/*      */     }
/*      */   }
/*      */   private static final class Image extends Record implements AutoCloseable { private final class_1011 pixels; private final int originalWidth; private final int originalHeight; private final boolean custom;
/*  998 */     private Image(class_1011 pixels, int originalWidth, int originalHeight, boolean custom) { this.pixels = pixels; this.originalWidth = originalWidth; this.originalHeight = originalHeight; this.custom = custom; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Image;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #998	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Image; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Image;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #998	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Image; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Image;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #998	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$Image;
/*  998 */       //   0	8	1	o	Ljava/lang/Object; } public class_1011 pixels() { return this.pixels; } public int originalWidth() { return this.originalWidth; } public int originalHeight() { return this.originalHeight; } public boolean custom() { return this.custom; }
/*      */ 
/*      */     
/*      */     public void close() {
/* 1002 */       this.pixels.close();
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewAssets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */