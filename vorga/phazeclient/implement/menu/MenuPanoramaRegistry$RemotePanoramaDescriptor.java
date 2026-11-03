/*      */ package vorga.phazeclient.implement.menu;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URI;
/*      */ import java.net.http.HttpRequest;
/*      */ import java.net.http.HttpResponse;
/*      */ import java.nio.file.CopyOption;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.StandardCopyOption;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.zip.ZipFile;
/*      */ import net.minecraft.class_1011;
/*      */ import net.minecraft.class_1043;
/*      */ import net.minecraft.class_1044;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ final class RemotePanoramaDescriptor
/*      */   implements MenuUiSettings.PanoramaDescriptor
/*      */ {
/*      */   private final String id;
/*      */   private final String displayName;
/*      */   private final String archiveName;
/*      */   private final URI downloadUri;
/*      */   private final URI previewUri;
/*      */   private final class_2960 previewTextureId;
/*  881 */   private final AtomicBoolean downloading = new AtomicBoolean(false);
/*  882 */   private final AtomicBoolean previewLoading = new AtomicBoolean(false);
/*  883 */   private final AtomicLong downloadedBytes = new AtomicLong();
/*  884 */   private volatile long totalBytes = -1L;
/*      */   private class_1043 previewTexture;
/*      */   
/*      */   private RemotePanoramaDescriptor(String id, String displayName, String archiveName, String downloadUrl, String previewUrl) {
/*  888 */     this.id = id;
/*  889 */     this.displayName = displayName;
/*  890 */     this.archiveName = archiveName;
/*  891 */     this.downloadUri = URI.create(downloadUrl);
/*  892 */     this.previewUri = URI.create(previewUrl);
/*  893 */     this.previewTextureId = class_2960.method_60655("phaze", "dynamic/remote-panoramas/" + MenuPanoramaRegistry.sanitizeTextureToken(id) + "/preview");
/*      */   }
/*      */   
/*  896 */   public String getId() { return this.id; }
/*  897 */   public String displayName() { return this.displayName; }
/*  898 */   public class_2960 previewTexture() { ensurePreview(); return this.previewTextureId; }
/*  899 */   public int previewTextureSize() { return 1; }
/*  900 */   public int previewCropInset() { return 0; }
/*  901 */   public int previewCropSize() { return 1; }
/*  902 */   public MenuPanoramaRenderer getRenderer() { return MenuUiSettings.PanoramaPreset.VANILLA.getRenderer(); }
/*  903 */   public boolean isRemote() { return true; }
/*  904 */   public boolean isDownloading() { return this.downloading.get(); } public boolean hasPreviewTexture() {
/*  905 */     return (this.previewTexture != null);
/*      */   } public float downloadProgress() {
/*  907 */     long total = this.totalBytes;
/*  908 */     return (total > 0L) ? Math.min(1.0F, (float)this.downloadedBytes.get() / (float)total) : 0.0F;
/*      */   }
/*      */   
/*      */   private boolean isDownloaded() {
/*  912 */     return Files.isRegularFile(MenuPanoramaRegistry.getPanoramasDirectory().resolve(this.archiveName), new java.nio.file.LinkOption[0]);
/*      */   }
/*      */   
/*      */   private void ensurePreview() {
/*  916 */     class_310 client = class_310.method_1551();
/*  917 */     if (this.previewTexture != null || client == null || !this.previewLoading.compareAndSet(false, true))
/*      */       return; 
/*  919 */     Path cacheFile = MenuPanoramaRegistry.previewCacheFile(MenuPanoramaRegistry.sanitizeTextureToken(this.id), 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  926 */     CompletableFuture.runAsync(() -> {
/*      */           class_1011 cached = MenuPanoramaRegistry.readCachedPreview(cacheFile);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           if (cached != null) {
/*      */             installPreview(client, cached);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           HttpRequest request = HttpRequest.newBuilder(this.previewUri).GET().build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           MenuPanoramaRegistry.HTTP_CLIENT.<byte[]>sendAsync(request, (HttpResponse.BodyHandler)HttpResponse.BodyHandlers.ofByteArray()).thenAcceptAsync(()).exceptionally(());
/*  955 */         }).exceptionally(error -> {
/*      */           this.previewLoading.set(false);
/*      */           return null;
/*      */         });
/*      */   }
/*      */   
/*      */   private void installPreview(class_310 client, class_1011 image) {
/*  962 */     client.execute(() -> {
/*      */           try {
/*      */             if (this.previewTexture == null) {
/*      */               this.previewTexture = new class_1043(image);
/*      */               client.method_1531().method_4616(this.previewTextureId, (class_1044)this.previewTexture);
/*      */             } else {
/*      */               image.close();
/*      */             } 
/*  970 */           } catch (Throwable error) {
/*      */             image.close();
/*      */             System.err.println("[Phaze] remote panorama preview registration failed: " + String.valueOf(error));
/*      */           } finally {
/*      */             this.previewLoading.set(false);
/*      */           } 
/*      */         });
/*      */   }
/*      */   
/*      */   private void activate() {
/*  980 */     Path target = MenuPanoramaRegistry.getPanoramasDirectory().resolve(this.archiveName);
/*  981 */     if (Files.isRegularFile(target, new java.nio.file.LinkOption[0])) {
/*  982 */       selectDownloaded();
/*      */       return;
/*      */     } 
/*  985 */     if (!this.downloading.compareAndSet(false, true))
/*  986 */       return;  MenuPanoramaRegistry.ensureDirectoryExists();
/*  987 */     this.downloadedBytes.set(0L);
/*  988 */     this.totalBytes = -1L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  993 */     Path temporary = target.resolveSibling(this.archiveName + ".part");
/*  994 */     HttpRequest request = HttpRequest.newBuilder(this.downloadUri).GET().build();
/*  995 */     MenuPanoramaRegistry.HTTP_CLIENT.<InputStream>sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
/*  996 */       .thenAcceptAsync(response -> { try { if (response.statusCode() < 200 || response.statusCode() >= 300)
/*      */               throw new IOException("HTTP " + response.statusCode());  this.totalBytes = response.headers().firstValueAsLong("Content-Length").orElse(-1L); InputStream input = response.body(); try { OutputStream output = Files.newOutputStream(temporary, new java.nio.file.OpenOption[0]); 
/*      */               try { byte[] buffer = new byte[65536]; int read; while ((read = input.read(buffer)) >= 0) {
/*      */                   output.write(buffer, 0, read); this.downloadedBytes.addAndGet(read);
/*      */                 }  if (output != null)
/*      */                   output.close();  }
/* 1002 */               catch (Throwable throwable) { if (output != null) try { output.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (input != null) input.close();  } catch (Throwable throwable) { if (input != null) try { input.close(); } catch (Throwable throwable1)
/*      */                 { throwable.addSuppressed(throwable1); }
/*      */               
/*      */ 
/*      */               
/*      */               throw throwable; }
/*      */ 
/*      */             
/*      */             ZipFile ignored = new ZipFile(temporary.toFile());
/*      */             
/*      */             ignored.close();
/*      */             Files.move(temporary, target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE });
/*      */             selectDownloaded(); }
/* 1015 */           catch (Throwable error) { try { Files.deleteIfExists(temporary); }
/* 1016 */             catch (IOException iOException) {}
/*      */             
/*      */             System.err.println("[Phaze] remote panorama download failed: " + String.valueOf(error)); }
/*      */           finally
/*      */           { this.downloading.set(false); }
/*      */         
/* 1022 */         }).exceptionally(error -> { try { Files.deleteIfExists(temporary); }
/* 1023 */           catch (IOException iOException) {}
/*      */           this.downloading.set(false);
/*      */           System.err.println("[Phaze] remote panorama download failed: " + String.valueOf(error));
/*      */           return null;
/*      */         });
/*      */   }
/*      */   
/*      */   private void selectDownloaded() {
/* 1031 */     class_310 client = class_310.method_1551();
/* 1032 */     if (client == null)
/* 1033 */       return;  client.execute(() -> {
/*      */           MenuPanoramaRegistry.reload();
/*      */           MenuUiSettings.getInstance().setSelectedPanoramaPreset(MenuPanoramaRegistry.customIdForArchiveName(this.archiveName));
/*      */         });
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\MenuPanoramaRegistry$RemotePanoramaDescriptor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */