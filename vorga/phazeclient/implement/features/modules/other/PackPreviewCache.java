/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.class_310;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ final class PackPreviewCache
/*     */ {
/*     */   static final int MAX_CACHED_PACKS = 8;
/*     */   static final long MAX_TEXTURE_BYTES = 33554432L;
/*     */   static final int TEXTURES_PER_FRAME = 8;
/*     */   static final long BYTES_PER_FRAME = 1048576L;
/*  24 */   private static final Logger LOG = LoggerFactory.getLogger("PhazePackPreview");
/*     */   
/*     */   private final Executor worker;
/*     */   private final Function<String, PackPreviewAssets.LoadRequest> requests;
/*  28 */   private final Map<String, PackPreviewAssets.Data> cached = new LinkedHashMap<>(8, 0.75F, true);
/*  29 */   private final Set<String> failed = new HashSet<>();
/*     */   private volatile long generation;
/*     */   private long cachedBytes;
/*     */   private Loading loading;
/*     */   private PackPreviewAssets.LoadRequest pending;
/*     */   private PackPreviewAssets.Prepared uploading;
/*     */   private String uploadingPack;
/*     */   
/*     */   PackPreviewCache() {
/*  38 */     this(Executors.newSingleThreadExecutor(task -> {
/*     */             Thread thread = new Thread(task, "Phaze-PackPreview");
/*     */             thread.setDaemon(true);
/*     */             thread.setPriority(4);
/*     */             return thread;
/*     */           }), packId -> PackPreviewAssets.request(class_310.method_1551(), packId));
/*     */   }
/*     */   
/*     */   PackPreviewCache(Executor worker, Function<String, PackPreviewAssets.LoadRequest> requests) {
/*  47 */     this.worker = worker;
/*  48 */     this.requests = requests;
/*     */   }
/*     */   
/*     */   PackPreviewAssets.Data get(String packId) {
/*  52 */     PackPreviewAssets.Data data = this.cached.get(packId);
/*  53 */     if (data != null || this.failed.contains(packId) || packId.equals(this.uploadingPack) || (this.loading != null && this.loading.generation == this.generation && packId
/*  54 */       .equals(this.loading.packId))) {
/*  55 */       this.pending = null;
/*  56 */       return data;
/*     */     } 
/*  58 */     if (this.pending == null || !packId.equals(this.pending.packId())) {
/*  59 */       this.pending = this.requests.apply(packId);
/*     */     }
/*  61 */     startPending();
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   void beginFrame() {
/*  67 */     if (this.uploading == null && this.loading != null && this.loading.result.isDone()) {
/*  68 */       Loading completed = this.loading;
/*  69 */       this.loading = null;
/*  70 */       if (completed.generation == this.generation) {
/*     */         try {
/*  72 */           this.uploading = completed.result.getNow(null);
/*  73 */           this.uploadingPack = completed.packId;
/*  74 */           reserve(this.uploading.textureBytes());
/*  75 */         } catch (CancellationException cancellationException) {
/*     */         
/*  77 */         } catch (CompletionException e) {
/*  78 */           fail(completed.packId, e.getCause());
/*     */         } 
/*     */       }
/*     */     } 
/*  82 */     if (this.uploading != null) {
/*     */       try {
/*  84 */         if (this.uploading.upload(8, 1048576L)) {
/*  85 */           PackPreviewAssets.Data data = this.uploading.takeData();
/*  86 */           this.cached.put(data.packId(), data);
/*  87 */           this.cachedBytes += data.textureBytes();
/*  88 */           this.uploading = null;
/*  89 */           this.uploadingPack = null;
/*     */         } 
/*  91 */       } catch (RuntimeException e) {
/*  92 */         fail(this.uploadingPack, e);
/*  93 */         this.uploading.close();
/*  94 */         this.uploading = null;
/*  95 */         this.uploadingPack = null;
/*     */       } 
/*     */     }
/*  98 */     startPending();
/*     */   }
/*     */   
/*     */   void clear() {
/* 102 */     long invalidated = this.generation++;
/* 103 */     this.pending = null;
/* 104 */     if (this.loading != null && this.loading.generation == invalidated)
/*     */     {
/* 106 */       this.loading.result.thenAccept(PackPreviewAssets.Prepared::close);
/*     */     }
/* 108 */     if (this.uploading != null) {
/* 109 */       this.uploading.close();
/* 110 */       this.uploading = null;
/* 111 */       this.uploadingPack = null;
/*     */     } 
/* 113 */     this.cached.values().forEach(PackPreviewAssets.Data::close);
/* 114 */     this.cached.clear();
/* 115 */     this.cachedBytes = 0L;
/* 116 */     this.failed.clear();
/*     */   }
/*     */   
/*     */   private void startPending() {
/* 120 */     if (this.loading != null || this.pending == null)
/* 121 */       return;  PackPreviewAssets.LoadRequest request = this.pending;
/* 122 */     this.pending = null;
/* 123 */     if (this.cached.containsKey(request.packId()) || this.failed.contains(request.packId()) || request
/* 124 */       .packId().equals(this.uploadingPack))
/* 125 */       return;  long currentGeneration = this.generation;
/* 126 */     this.loading = new Loading(request.packId(), currentGeneration, CompletableFuture.supplyAsync(() -> request.decode(()), this.worker));
/*     */   }
/*     */ 
/*     */   
/*     */   private void reserve(long incomingBytes) {
/* 131 */     Iterator<PackPreviewAssets.Data> iterator = this.cached.values().iterator();
/* 132 */     while (iterator.hasNext() && (this.cached.size() >= 8 || this.cachedBytes + incomingBytes > 33554432L)) {
/*     */       
/* 134 */       PackPreviewAssets.Data oldest = iterator.next();
/* 135 */       this.cachedBytes -= oldest.textureBytes();
/* 136 */       oldest.close();
/* 137 */       iterator.remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fail(String packId, Throwable failure) {
/* 142 */     this.failed.add(packId);
/* 143 */     LOG.warn("Could not prepare preview for {}", packId, failure);
/*     */   }
/*     */   private static final class Loading extends Record { private final String packId; private final long generation; private final CompletableFuture<PackPreviewAssets.Prepared> result;
/* 146 */     private Loading(String packId, long generation, CompletableFuture<PackPreviewAssets.Prepared> result) { this.packId = packId; this.generation = generation; this.result = result; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewCache$Loading;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #146	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 146 */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewCache$Loading; } public String packId() { return this.packId; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewCache$Loading;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #146	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewCache$Loading; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewCache$Loading;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #146	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewCache$Loading;
/* 146 */       //   0	8	1	o	Ljava/lang/Object; } public long generation() { return this.generation; } public CompletableFuture<PackPreviewAssets.Prepared> result() { return this.result; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */