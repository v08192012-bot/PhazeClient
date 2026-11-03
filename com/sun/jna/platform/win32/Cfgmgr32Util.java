/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.win32.W32APITypeMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Cfgmgr32Util
/*     */ {
/*     */   public static class Cfgmgr32Exception
/*     */     extends RuntimeException
/*     */   {
/*     */     private final int errorCode;
/*     */     
/*     */     public Cfgmgr32Exception(int errorCode) {
/*  40 */       this.errorCode = errorCode;
/*     */     }
/*     */     
/*     */     public int getErrorCode() {
/*  44 */       return this.errorCode;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  49 */       return super.toString() + String.format(" [errorCode: 0x%08x]", new Object[] { Integer.valueOf(this.errorCode) });
/*     */     }
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
/*     */   public static String CM_Get_Device_ID(int devInst) throws Cfgmgr32Exception {
/*  65 */     int charToBytes = Boolean.getBoolean("w32.ascii") ? 1 : Native.WCHAR_SIZE;
/*     */ 
/*     */     
/*  68 */     IntByReference pulLen = new IntByReference();
/*  69 */     int ret = Cfgmgr32.INSTANCE.CM_Get_Device_ID_Size(pulLen, devInst, 0);
/*  70 */     if (ret != 0) {
/*  71 */       throw new Cfgmgr32Exception(ret);
/*     */     }
/*     */ 
/*     */     
/*  75 */     Memory buffer = new Memory(((pulLen.getValue() + 1) * charToBytes));
/*     */     
/*  77 */     buffer.clear();
/*     */     
/*  79 */     ret = Cfgmgr32.INSTANCE.CM_Get_Device_ID(devInst, (Pointer)buffer, pulLen.getValue(), 0);
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (ret == 26) {
/*  84 */       ret = Cfgmgr32.INSTANCE.CM_Get_Device_ID_Size(pulLen, devInst, 0);
/*  85 */       if (ret != 0) {
/*  86 */         throw new Cfgmgr32Exception(ret);
/*     */       }
/*  88 */       buffer = new Memory(((pulLen.getValue() + 1) * charToBytes));
/*  89 */       buffer.clear();
/*  90 */       ret = Cfgmgr32.INSTANCE.CM_Get_Device_ID(devInst, (Pointer)buffer, pulLen.getValue(), 0);
/*     */     } 
/*     */     
/*  93 */     if (ret != 0) {
/*  94 */       throw new Cfgmgr32Exception(ret);
/*     */     }
/*     */     
/*  97 */     if (charToBytes == 1) {
/*  98 */       return buffer.getString(0L);
/*     */     }
/* 100 */     return buffer.getWideString(0L);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object CM_Get_DevNode_Registry_Property(int devInst, int ulProperty) throws Cfgmgr32Exception {
/* 139 */     IntByReference size = new IntByReference();
/* 140 */     IntByReference type = new IntByReference();
/* 141 */     int ret = Cfgmgr32.INSTANCE.CM_Get_DevNode_Registry_Property(devInst, ulProperty, type, null, size, 0);
/*     */     
/* 143 */     if (ret == 37) {
/* 144 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 148 */     if (ret != 26) {
/* 149 */       throw new Cfgmgr32Exception(ret);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     Memory buffer = null;
/* 155 */     if (size.getValue() > 0) {
/* 156 */       buffer = new Memory(size.getValue());
/* 157 */       ret = Cfgmgr32.INSTANCE.CM_Get_DevNode_Registry_Property(devInst, ulProperty, type, (Pointer)buffer, size, 0);
/* 158 */       if (ret != 0) {
/* 159 */         throw new Cfgmgr32Exception(ret);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 164 */     switch (type.getValue()) {
/*     */       
/*     */       case 1:
/* 167 */         if (buffer == null) {
/* 168 */           return "";
/*     */         }
/* 170 */         return (W32APITypeMapper.DEFAULT == W32APITypeMapper.UNICODE) ? buffer.getWideString(0L) : 
/* 171 */           buffer.getString(0L);
/*     */       
/*     */       case 7:
/* 174 */         if (buffer == null) {
/* 175 */           return new String[0];
/*     */         }
/* 177 */         return Advapi32Util.regMultiSzBufferToStringArray(buffer);
/*     */       
/*     */       case 4:
/* 180 */         if (buffer == null) {
/* 181 */           return Integer.valueOf(0);
/*     */         }
/* 183 */         return Integer.valueOf(buffer.getInt(0L));
/*     */       case 0:
/* 185 */         return null;
/*     */     } 
/*     */     
/* 188 */     if (buffer == null) {
/* 189 */       return new byte[0];
/*     */     }
/*     */     
/* 192 */     return buffer.getByteArray(0L, (int)buffer.size());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\Cfgmgr32Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */