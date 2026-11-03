/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Prepared
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final PackPreviewAssets.Data data;
/*     */   private final List<class_1011> images;
/*     */   private int uploaded;
/*     */   private boolean transferred;
/*     */   private boolean closed;
/*     */   
/*     */   private Prepared(PackPreviewAssets.Data data, List<class_1011> images) {
/* 121 */     this.data = data;
/* 122 */     this.images = images;
/*     */   }
/*     */   
/*     */   long textureBytes() {
/* 126 */     return this.data.textureBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean upload(int maxTextures, long maxBytes) {
/* 131 */     if (this.closed || this.transferred) throw new IllegalStateException("Prepared preview is no longer owned"); 
/* 132 */     int textures = 0;
/* 133 */     long bytes = 0L;
/* 134 */     while (this.uploaded < this.images.size() && textures < maxTextures) {
/* 135 */       class_1011 image = this.images.get(this.uploaded);
/* 136 */       long size = image.method_4307() * image.method_4323() * 4L;
/* 137 */       if (bytes + size > maxBytes)
/* 138 */         break;  class_1043 texture = null;
/*     */       try {
/* 140 */         texture = new class_1043(image);
/* 141 */         this.data.client.method_1531().method_4616(this.data.owned.get(this.uploaded), (class_1044)texture);
/* 142 */       } catch (RuntimeException|Error e) {
/* 143 */         if (texture != null) {
/* 144 */           this.data.client.method_1531().method_4615(this.data.owned.get(this.uploaded));
/* 145 */           texture.close();
/*     */         } else {
/* 147 */           image.close();
/*     */         } 
/* 149 */         this.images.set(this.uploaded, null);
/* 150 */         close();
/* 151 */         throw e;
/*     */       } 
/* 153 */       this.images.set(this.uploaded++, null);
/* 154 */       textures++;
/* 155 */       bytes += size;
/*     */     } 
/* 157 */     return (this.uploaded == this.images.size());
/*     */   }
/*     */   
/*     */   PackPreviewAssets.Data takeData() {
/* 161 */     if (this.closed || this.transferred || this.uploaded != this.images.size()) {
/* 162 */       throw new IllegalStateException("Preview textures are not ready");
/*     */     }
/* 164 */     this.transferred = true;
/* 165 */     this.images.clear();
/* 166 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 171 */     if (this.closed || this.transferred)
/* 172 */       return;  this.closed = true;
/* 173 */     this.data.closed = true;
/* 174 */     for (int i = 0; i < this.uploaded; i++) {
/* 175 */       this.data.client.method_1531().method_4615(this.data.owned.get(i));
/*     */     }
/* 177 */     for (class_1011 image : this.images) {
/* 178 */       if (image != null) image.close(); 
/*     */     } 
/* 180 */     this.images.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewAssets$Prepared.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */