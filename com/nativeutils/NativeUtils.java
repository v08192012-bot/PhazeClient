/*     */ package com.nativeutils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.FileSystemNotFoundException;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NativeUtils
/*     */ {
/*  42 */   private static final Logger LOGGER = Logger.getLogger(NativeUtils.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_PREFIX_LENGTH = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NATIVE_FOLDER_PATH_PREFIX = "nativeutils";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File temporaryDir;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadLibraryFromJar(String path) throws IOException {
/*  78 */     if (path == null || !path.startsWith("/")) {
/*  79 */       throw new IllegalArgumentException("The path has to be absolute (start with '/').");
/*     */     }
/*     */ 
/*     */     
/*  83 */     String[] parts = path.split("/");
/*  84 */     String filename = (parts.length > 1) ? parts[parts.length - 1] : null;
/*     */ 
/*     */     
/*  87 */     if (filename == null || filename.length() < 3) {
/*  88 */       throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
/*     */     }
/*     */ 
/*     */     
/*  92 */     if (temporaryDir == null) {
/*  93 */       temporaryDir = createTempDirectory();
/*  94 */       temporaryDir.deleteOnExit();
/*     */     } 
/*  96 */     File temp = new File(temporaryDir, filename);
/*  97 */     try (InputStream is = NativeUtils.class.getResourceAsStream(path)) {
/*  98 */       Files.copy(is, temp.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*  99 */     } catch (IOException e) {
/* 100 */       boolean status = temp.delete();
/* 101 */       if (status) {
/* 102 */         LOGGER.info("Deleted file : " + temp.getAbsolutePath());
/*     */       }
/* 104 */       throw e;
/* 105 */     } catch (NullPointerException e) {
/* 106 */       boolean status = temp.delete();
/* 107 */       if (status) {
/* 108 */         LOGGER.info("Deleted file : " + temp.getAbsolutePath());
/*     */       }
/* 110 */       throw new FileNotFoundException("File " + path + " was not found inside JAR.");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       System.load(temp.getAbsolutePath());
/*     */     } finally {
/* 117 */       if (isPosixCompliant()) {
/*     */         
/* 119 */         boolean status = temp.delete();
/* 120 */         if (status) {
/* 121 */           LOGGER.info("Deleted file : " + temp.getAbsolutePath());
/*     */         }
/*     */       } else {
/*     */         
/* 125 */         temp.deleteOnExit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isPosixCompliant() {
/*     */     try {
/* 132 */       return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
/* 133 */     } catch (FileSystemNotFoundException|java.nio.file.ProviderNotFoundException|SecurityException e) {
/*     */ 
/*     */       
/* 136 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static File createTempDirectory() throws IOException {
/* 141 */     String tempDir = System.getProperty("java.io.tmpdir");
/* 142 */     File generatedDir = new File(tempDir, "nativeutils" + System.nanoTime());
/* 143 */     if (!generatedDir.mkdir()) {
/* 144 */       throw new IOException("Failed to create temp directory " + generatedDir.getName());
/*     */     }
/* 146 */     return generatedDir;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\nativeutils\NativeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */