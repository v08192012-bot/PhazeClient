/*     */ package com.sun.jna.platform.bsd;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.platform.unix.LibCAPI;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtAttrUtil
/*     */ {
/*     */   public static List<String> list(String path) throws IOException {
/*  41 */     long bufferLength = ExtAttr.INSTANCE.extattr_list_file(path, 1, null, new LibCAPI.size_t(0L)).longValue();
/*     */     
/*  43 */     if (bufferLength < 0L) {
/*  44 */       throw new IOException("errno: " + Native.getLastError());
/*     */     }
/*     */     
/*  47 */     if (bufferLength == 0L) {
/*  48 */       return Collections.emptyList();
/*     */     }
/*     */     
/*  51 */     ByteBuffer buffer = ByteBuffer.allocate((int)bufferLength);
/*  52 */     long valueLength = ExtAttr.INSTANCE.extattr_list_file(path, 1, buffer, new LibCAPI.size_t(bufferLength)).longValue();
/*     */     
/*  54 */     if (valueLength < 0L) {
/*  55 */       throw new IOException("errno: " + Native.getLastError());
/*     */     }
/*     */     
/*  58 */     return decodeStringList(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuffer get(String path, String name) throws IOException {
/*  63 */     long bufferLength = ExtAttr.INSTANCE.extattr_get_file(path, 1, name, null, new LibCAPI.size_t(0L)).longValue();
/*     */     
/*  65 */     if (bufferLength < 0L) {
/*  66 */       throw new IOException("errno: " + Native.getLastError());
/*     */     }
/*     */     
/*  69 */     if (bufferLength == 0L) {
/*  70 */       return ByteBuffer.allocate(0);
/*     */     }
/*     */     
/*  73 */     ByteBuffer buffer = ByteBuffer.allocate((int)bufferLength);
/*  74 */     long valueLength = ExtAttr.INSTANCE.extattr_get_file(path, 1, name, buffer, new LibCAPI.size_t(bufferLength)).longValue();
/*     */     
/*  76 */     if (valueLength < 0L) {
/*  77 */       throw new IOException("errno: " + Native.getLastError());
/*     */     }
/*     */     
/*  80 */     return buffer;
/*     */   }
/*     */   
/*     */   public static void set(String path, String name, ByteBuffer value) throws IOException {
/*  84 */     long r = ExtAttr.INSTANCE.extattr_set_file(path, 1, name, value, new LibCAPI.size_t(value.remaining())).longValue();
/*  85 */     if (r < 0L) {
/*  86 */       throw new IOException("errno: " + Native.getLastError());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void delete(String path, String name) throws IOException {
/*  91 */     int r = ExtAttr.INSTANCE.extattr_delete_file(path, 1, name);
/*  92 */     if (r < 0) {
/*  93 */       throw new IOException("errno: " + Native.getLastError());
/*     */     }
/*     */   }
/*     */   
/*     */   private static List<String> decodeStringList(ByteBuffer buffer) {
/*  98 */     List<String> list = new ArrayList<>();
/*     */     
/* 100 */     while (buffer.hasRemaining()) {
/* 101 */       int length = buffer.get() & 0xFF;
/* 102 */       byte[] value = new byte[length];
/* 103 */       buffer.get(value);
/*     */       
/*     */       try {
/* 106 */         list.add(new String(value, "UTF-8"));
/* 107 */       } catch (UnsupportedEncodingException e) {
/* 108 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\bsd\ExtAttrUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */