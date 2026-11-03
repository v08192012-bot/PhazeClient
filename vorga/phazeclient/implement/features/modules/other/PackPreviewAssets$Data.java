/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_2960;
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
/*     */ public final class Data
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final class_310 client;
/*     */   private final String packId;
/*     */   private final List<List<PackPreviewAssets.Sprite>> blockBatches;
/*     */   private final Map<String, PackPreviewAssets.Sprite> items;
/*     */   private final List<PackPreviewAssets.Sprite> hearts;
/*     */   private final Map<String, PackPreviewAssets.Sprite> particles;
/*     */   private final int resolution;
/*     */   private final int changedBlockCount;
/*     */   private final boolean animations;
/*     */   private final boolean lightmap;
/*     */   private final boolean sounds;
/*     */   private final List<String> diagnostics;
/*     */   private final List<class_2960> owned;
/*     */   private final long textureBytes;
/*     */   private boolean closed;
/*     */   
/*     */   private Data(PackPreviewAssets.Loader loader, List<List<PackPreviewAssets.Sprite>> blocks, Map<String, PackPreviewAssets.Sprite> items, List<PackPreviewAssets.Sprite> hearts, Map<String, PackPreviewAssets.Sprite> particles, int changedBlockCount) {
/* 212 */     this.client = loader.client;
/* 213 */     this.packId = loader.packId;
/* 214 */     this.blockBatches = blocks.stream().map(List::copyOf).toList();
/* 215 */     this.items = Collections.unmodifiableMap(new LinkedHashMap<>(items));
/* 216 */     this.hearts = List.copyOf(hearts);
/* 217 */     this.particles = Collections.unmodifiableMap(new LinkedHashMap<>(particles));
/* 218 */     this.resolution = PackPreviewAssetLogic.resolution(loader.widths);
/* 219 */     this.changedBlockCount = changedBlockCount;
/* 220 */     this.animations = loader.animations;
/* 221 */     this.lightmap = loader.lightmap;
/* 222 */     this.sounds = loader.sounds;
/* 223 */     this.diagnostics = List.copyOf(loader.diagnostics);
/* 224 */     this.owned = List.copyOf(loader.owned);
/* 225 */     this.textureBytes = loader.textureBytes;
/*     */   }
/*     */   
/* 228 */   public String packId() { return this.packId; } public List<List<PackPreviewAssets.Sprite>> blockBatches() {
/* 229 */     return this.blockBatches;
/*     */   }
/*     */   public List<PackPreviewAssets.Sprite> blocksAt(long elapsedMillis) {
/* 232 */     return this.blockBatches.get(PackPreviewAssetLogic.batchIndex(elapsedMillis, this.blockBatches.size()));
/*     */   } public Map<String, PackPreviewAssets.Sprite> items() {
/* 234 */     return this.items;
/*     */   }
/* 236 */   public List<PackPreviewAssets.Sprite> hearts() { return this.hearts; } public Map<String, PackPreviewAssets.Sprite> particles() {
/* 237 */     return this.particles;
/*     */   } public int resolution() {
/* 239 */     return this.resolution;
/*     */   }
/* 241 */   public int changedBlockCount() { return this.changedBlockCount; }
/* 242 */   public boolean animations() { return this.animations; }
/* 243 */   public boolean lightmap() { return this.lightmap; }
/* 244 */   public boolean sounds() { return this.sounds; } public List<String> diagnostics() {
/* 245 */     return this.diagnostics;
/*     */   }
/* 247 */   public long textureBytes() { return this.textureBytes; } public boolean isClosed() {
/* 248 */     return this.closed;
/*     */   }
/*     */   
/*     */   public void close() {
/* 252 */     if (!this.closed) {
/* 253 */       this.closed = true;
/* 254 */       for (class_2960 id : this.owned)
/* 255 */         this.client.method_1531().method_4615(id); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewAssets$Data.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */