/*     */ package vorga.phazeclient.implement.menu;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Path;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1060;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CustomPanoramaDescriptor
/*     */   implements MenuUiSettings.PanoramaDescriptor
/*     */ {
/*     */   private final String id;
/*     */   private final String displayName;
/*     */   private final Path archivePath;
/*     */   private final class_2960 cubeMapBase;
/*     */   private final class_2960 previewTextureId;
/*     */   private final class_2960[] faceTextureIds;
/*     */   private MenuPanoramaRenderer renderer;
/*     */   private class_1043 previewTexture;
/*     */   private class_1043[] faceTextures;
/*     */   private int previewTextureSize;
/*     */   private boolean facesLoaded;
/* 651 */   private long archiveStamp = Long.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CustomPanoramaDescriptor(String id, String displayName, Path archivePath, class_2960 cubeMapBase, class_2960 previewTextureId, class_2960[] faceTextureIds) {
/* 659 */     this.id = id;
/* 660 */     this.displayName = displayName;
/* 661 */     this.archivePath = archivePath;
/* 662 */     this.cubeMapBase = cubeMapBase;
/* 663 */     this.previewTextureId = previewTextureId;
/* 664 */     this.faceTextureIds = faceTextureIds;
/*     */   }
/*     */   
/*     */   static CustomPanoramaDescriptor load(Path archivePath, class_310 client) throws IOException {
/* 668 */     String archiveName = archivePath.getFileName().toString();
/* 669 */     String displayName = MenuPanoramaRegistry.stripZipExtension(archiveName);
/* 670 */     String textureToken = MenuPanoramaRegistry.sanitizeTextureToken(displayName) + "_" + MenuPanoramaRegistry.sanitizeTextureToken(displayName);
/* 671 */     class_2960 cubeMapBase = class_2960.method_60655("phaze", "dynamic/panoramas/" + textureToken + "/panorama");
/* 672 */     class_2960 previewTextureId = class_2960.method_60655("phaze", "dynamic/panoramas/" + textureToken + "/preview");
/* 673 */     class_2960[] faceTextureIds = new class_2960[6];
/* 674 */     for (int i = 0; i < 6; i++) {
/* 675 */       faceTextureIds[i] = cubeMapBase.method_45136(cubeMapBase.method_12832() + "_" + cubeMapBase.method_12832() + ".png");
/*     */     }
/*     */ 
/*     */     
/* 679 */     CustomPanoramaDescriptor descriptor = new CustomPanoramaDescriptor(MenuPanoramaRegistry.customIdForArchiveName(archiveName), displayName, archivePath, cubeMapBase, previewTextureId, faceTextureIds);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 686 */     descriptor.validateArchive();
/* 687 */     descriptor.reloadClientTextures(client);
/* 688 */     return descriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 693 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String displayName() {
/* 698 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2960 previewTexture() {
/* 703 */     return this.previewTextureId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int previewTextureSize() {
/* 708 */     return this.previewTextureSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int previewCropInset() {
/* 713 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int previewCropSize() {
/* 718 */     return this.previewTextureSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuPanoramaRenderer getRenderer() {
/* 723 */     if (this.renderer == null) {
/* 724 */       ensureFaceTexturesLoaded(class_310.method_1551());
/* 725 */       this.renderer = new MenuPanoramaRenderer(this.cubeMapBase);
/*     */     } 
/* 727 */     return this.renderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCustom() {
/* 732 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPreviewTexture() {
/* 737 */     return (this.previewTexture != null);
/*     */   }
/*     */   
/*     */   void reloadClientTextures(class_310 client) {
/* 741 */     boolean reloadFaces = (this.facesLoaded || this.renderer != null);
/* 742 */     close(client);
/* 743 */     loadPreviewTexture(client);
/* 744 */     if (reloadFaces) {
/* 745 */       ensureFaceTexturesLoaded(client);
/*     */     }
/*     */   }
/*     */   
/*     */   void close(class_310 client) {
/* 750 */     class_1060 textureManager = (client != null) ? client.method_1531() : null;
/* 751 */     if (textureManager != null) {
/* 752 */       textureManager.method_4615(this.previewTextureId);
/* 753 */       for (class_2960 faceTextureId : this.faceTextureIds) {
/* 754 */         textureManager.method_4615(faceTextureId);
/*     */       }
/*     */     } 
/* 757 */     if (this.previewTexture != null) {
/* 758 */       this.previewTexture.close();
/* 759 */       this.previewTexture = null;
/*     */     } 
/* 761 */     if (this.faceTextures != null) {
/* 762 */       for (class_1043 faceTexture : this.faceTextures) {
/* 763 */         if (faceTexture != null) {
/* 764 */           faceTexture.close();
/*     */         }
/*     */       } 
/* 767 */       this.faceTextures = null;
/*     */     } 
/* 769 */     this.renderer = null;
/* 770 */     this.facesLoaded = false;
/*     */   }
/*     */   
/*     */   private void validateArchive() throws IOException {
/* 774 */     ZipFile zip = new ZipFile(this.archivePath.toFile()); 
/* 775 */     try { for (int i = 0; i < 6; i++) {
/* 776 */         if (MenuPanoramaRegistry.findEntryIgnoreCase(zip, "panorama_" + i + ".png") == null) {
/* 777 */           throw new IOException("missing panorama_" + i + ".png");
/*     */         }
/*     */       } 
/* 780 */       zip.close(); }
/*     */     catch (Throwable throwable) { try { zip.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 784 */      } private void loadPreviewTexture(class_310 client) { if (client == null || client.method_1531() == null) {
/* 785 */       this.previewTextureSize = 256;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 793 */     String token = MenuPanoramaRegistry.sanitizeTextureToken(MenuPanoramaRegistry.stripZipExtension(this.archivePath.getFileName().toString()));
/* 794 */     Path cacheFile = MenuPanoramaRegistry.previewCacheFile(token, this.archiveStamp);
/* 795 */     class_1011 cached = MenuPanoramaRegistry.readCachedPreview(cacheFile);
/* 796 */     if (cached != null) {
/* 797 */       this.previewTextureSize = Math.max(1, cached.method_4307());
/* 798 */       this.previewTexture = new class_1043(cached);
/* 799 */       client.method_1531().method_4616(this.previewTextureId, (class_1044)this.previewTexture);
/*     */       return;
/*     */     } 
/*     */     
/* 803 */     try { ZipFile zip = new ZipFile(this.archivePath.toFile()); 
/* 804 */       try { ZipEntry previewEntry = MenuPanoramaRegistry.findEntryIgnoreCase(zip, "icon.png");
/* 805 */         boolean usePanoramaFace = (previewEntry == null);
/* 806 */         if (!usePanoramaFace) {
/* 807 */           InputStream inputStream = zip.getInputStream(previewEntry); 
/* 808 */           try { class_1011 icon = class_1011.method_4309(inputStream);
/* 809 */             usePanoramaFace = (icon.method_4307() <= 1 || icon.method_4323() <= 1);
/* 810 */             icon.close();
/* 811 */             if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null)
/*     */               try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 813 */         }  if (usePanoramaFace) previewEntry = MenuPanoramaRegistry.findEntryIgnoreCase(zip, "panorama_0.png"); 
/* 814 */         if (previewEntry == null) {
/* 815 */           throw new IOException("missing icon.png and panorama_0.png");
/*     */         }
/*     */         
/* 818 */         InputStream input = zip.getInputStream(previewEntry); 
/* 819 */         try { class_1011 preview = MenuPanoramaRegistry.downscaleSquare(
/* 820 */               MenuPanoramaRegistry.cropToSquare(class_1011.method_4309(input)), 256);
/*     */ 
/*     */           
/* 823 */           MenuPanoramaRegistry.writeCachedPreview(cacheFile, preview, token);
/* 824 */           this.previewTextureSize = Math.max(1, preview.method_4307());
/* 825 */           this.previewTexture = new class_1043(preview);
/* 826 */           client.method_1531().method_4616(this.previewTextureId, (class_1044)this.previewTexture);
/* 827 */           if (input != null) input.close();  } catch (Throwable throwable) { if (input != null)
/* 828 */             try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  zip.close(); } catch (Throwable throwable) { try { zip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable t)
/* 829 */     { this.previewTextureSize = 256;
/* 830 */       System.err.println("[Phaze] failed to load panorama preview " + String.valueOf(this.archivePath.getFileName()) + ": " + String.valueOf(t)); }
/*     */      }
/*     */ 
/*     */   
/*     */   private void ensureFaceTexturesLoaded(class_310 client) {
/* 835 */     if (this.facesLoaded || client == null || client.method_1531() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 839 */     try { ZipFile zip = new ZipFile(this.archivePath.toFile()); 
/* 840 */       try { class_1043[] loadedTextures = new class_1043[6];
/* 841 */         for (int i = 0; i < 6; i++) {
/* 842 */           ZipEntry faceEntry = MenuPanoramaRegistry.findEntryIgnoreCase(zip, "panorama_" + i + ".png");
/* 843 */           if (faceEntry == null) {
/* 844 */             throw new IOException("missing panorama_" + i + ".png");
/*     */           }
/* 846 */           InputStream input = zip.getInputStream(faceEntry); 
/* 847 */           try { class_1011 face = class_1011.method_4309(input);
/* 848 */             loadedTextures[i] = new class_1043(face);
/* 849 */             client.method_1531().method_4616(this.faceTextureIds[i], (class_1044)loadedTextures[i]);
/* 850 */             if (input != null) input.close();  } catch (Throwable throwable) { if (input != null)
/*     */               try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 852 */         }  this.faceTextures = loadedTextures;
/* 853 */         this.facesLoaded = true;
/* 854 */         zip.close(); } catch (Throwable throwable) { try { zip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable t)
/* 855 */     { if (this.faceTextures != null) {
/* 856 */         for (class_1043 faceTexture : this.faceTextures) {
/* 857 */           if (faceTexture != null) {
/* 858 */             faceTexture.close();
/*     */           }
/*     */         } 
/* 861 */         this.faceTextures = null;
/*     */       } 
/* 863 */       class_1060 textureManager = client.method_1531();
/* 864 */       if (textureManager != null) {
/* 865 */         for (class_2960 faceTextureId : this.faceTextureIds) {
/* 866 */           textureManager.method_4615(faceTextureId);
/*     */         }
/*     */       }
/* 869 */       System.err.println("[Phaze] failed to load panorama faces " + String.valueOf(this.archivePath.getFileName()) + ": " + String.valueOf(t)); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\MenuPanoramaRegistry$CustomPanoramaDescriptor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */