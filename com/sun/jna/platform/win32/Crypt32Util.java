/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Crypt32Util
/*     */ {
/*     */   public static byte[] cryptProtectData(byte[] data) {
/*  49 */     return cryptProtectData(data, 0);
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
/*     */   public static byte[] cryptProtectData(byte[] data, int flags) {
/*  62 */     return cryptProtectData(data, null, flags, "", null);
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
/*     */ 
/*     */   
/*     */   public static byte[] cryptProtectData(byte[] data, byte[] entropy, int flags, String description, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT prompt) {
/*  82 */     WinCrypt.DATA_BLOB pDataIn = new WinCrypt.DATA_BLOB(data);
/*  83 */     WinCrypt.DATA_BLOB pDataProtected = new WinCrypt.DATA_BLOB();
/*  84 */     WinCrypt.DATA_BLOB pEntropy = (entropy == null) ? null : new WinCrypt.DATA_BLOB(entropy);
/*  85 */     Win32Exception err = null;
/*  86 */     byte[] protectedData = null;
/*     */     try {
/*  88 */       if (!Crypt32.INSTANCE.CryptProtectData(pDataIn, description, pEntropy, null, prompt, flags, pDataProtected)) {
/*     */         
/*  90 */         err = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       } else {
/*  92 */         protectedData = pDataProtected.getData();
/*     */       } 
/*     */     } finally {
/*  95 */       if (pDataIn.pbData != null) {
/*  96 */         pDataIn.pbData.clear(pDataIn.cbData);
/*     */       }
/*  98 */       if (pEntropy != null && pEntropy.pbData != null) {
/*  99 */         pEntropy.pbData.clear(pEntropy.cbData);
/*     */       }
/* 101 */       if (pDataProtected.pbData != null) {
/* 102 */         pDataProtected.pbData.clear(pDataProtected.cbData);
/*     */         try {
/* 104 */           Kernel32Util.freeLocalMemory(pDataProtected.pbData);
/* 105 */         } catch (Win32Exception e) {
/* 106 */           if (err == null) {
/* 107 */             err = e;
/*     */           } else {
/* 109 */             err.addSuppressedReflected((Throwable)e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     if (err != null) {
/* 116 */       if (protectedData != null) {
/* 117 */         Arrays.fill(protectedData, (byte)0);
/*     */       }
/* 119 */       throw err;
/*     */     } 
/*     */     
/* 122 */     return protectedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] cryptUnprotectData(byte[] data) {
/* 133 */     return cryptUnprotectData(data, 0);
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
/*     */   public static byte[] cryptUnprotectData(byte[] data, int flags) {
/* 146 */     return cryptUnprotectData(data, null, flags, null);
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
/*     */   public static byte[] cryptUnprotectData(byte[] data, byte[] entropy, int flags, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT prompt) {
/* 164 */     WinCrypt.DATA_BLOB pDataIn = new WinCrypt.DATA_BLOB(data);
/* 165 */     WinCrypt.DATA_BLOB pDataUnprotected = new WinCrypt.DATA_BLOB();
/* 166 */     WinCrypt.DATA_BLOB pEntropy = (entropy == null) ? null : new WinCrypt.DATA_BLOB(entropy);
/* 167 */     Win32Exception err = null;
/* 168 */     byte[] unProtectedData = null;
/*     */     try {
/* 170 */       if (!Crypt32.INSTANCE.CryptUnprotectData(pDataIn, null, pEntropy, null, prompt, flags, pDataUnprotected)) {
/*     */         
/* 172 */         err = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       } else {
/* 174 */         unProtectedData = pDataUnprotected.getData();
/*     */       } 
/*     */     } finally {
/* 177 */       if (pDataIn.pbData != null) {
/* 178 */         pDataIn.pbData.clear(pDataIn.cbData);
/*     */       }
/* 180 */       if (pEntropy != null && pEntropy.pbData != null) {
/* 181 */         pEntropy.pbData.clear(pEntropy.cbData);
/*     */       }
/* 183 */       if (pDataUnprotected.pbData != null) {
/* 184 */         pDataUnprotected.pbData.clear(pDataUnprotected.cbData);
/*     */         try {
/* 186 */           Kernel32Util.freeLocalMemory(pDataUnprotected.pbData);
/* 187 */         } catch (Win32Exception e) {
/* 188 */           if (err == null) {
/* 189 */             err = e;
/*     */           } else {
/* 191 */             err.addSuppressedReflected((Throwable)e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     if (err != null) {
/* 198 */       if (unProtectedData != null) {
/* 199 */         Arrays.fill(unProtectedData, (byte)0);
/*     */       }
/* 201 */       throw err;
/*     */     } 
/*     */     
/* 204 */     return unProtectedData;
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
/*     */   public static String CertNameToStr(int dwCertEncodingType, int dwStrType, WinCrypt.DATA_BLOB pName) {
/* 222 */     int charToBytes = Boolean.getBoolean("w32.ascii") ? 1 : Native.WCHAR_SIZE;
/*     */ 
/*     */     
/* 225 */     int requiredSize = Crypt32.INSTANCE.CertNameToStr(dwCertEncodingType, pName, dwStrType, Pointer.NULL, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     Memory mem = new Memory((requiredSize * charToBytes));
/*     */ 
/*     */     
/* 235 */     int resultBytes = Crypt32.INSTANCE.CertNameToStr(dwCertEncodingType, pName, dwStrType, (Pointer)mem, requiredSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     assert resultBytes == requiredSize;
/*     */     
/* 244 */     if (Boolean.getBoolean("w32.ascii")) {
/* 245 */       return mem.getString(0L);
/*     */     }
/* 247 */     return mem.getWideString(0L);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\Crypt32Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */