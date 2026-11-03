/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PdhUtil
/*     */ {
/*  40 */   private static final int CHAR_TO_BYTES = Boolean.getBoolean("w32.ascii") ? 1 : Native.WCHAR_SIZE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String ENGLISH_COUNTER_KEY = "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Perflib\\009";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String ENGLISH_COUNTER_VALUE = "Counter";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String PdhLookupPerfNameByIndex(String szMachineName, int dwNameIndex) {
/*  63 */     WinDef.DWORDByReference pcchNameBufferSize = new WinDef.DWORDByReference(new WinDef.DWORD(0L));
/*  64 */     int result = Pdh.INSTANCE.PdhLookupPerfNameByIndex(szMachineName, dwNameIndex, null, pcchNameBufferSize);
/*  65 */     Memory mem = null;
/*     */ 
/*     */     
/*  68 */     if (result != -1073738819) {
/*     */       
/*  70 */       if (result != 0 && result != -2147481646) {
/*  71 */         throw new PdhException(result);
/*     */       }
/*     */       
/*  74 */       if (pcchNameBufferSize.getValue().intValue() < 1) {
/*  75 */         return "";
/*     */       }
/*     */       
/*  78 */       mem = new Memory((pcchNameBufferSize.getValue().intValue() * CHAR_TO_BYTES));
/*  79 */       result = Pdh.INSTANCE.PdhLookupPerfNameByIndex(szMachineName, dwNameIndex, (Pointer)mem, pcchNameBufferSize);
/*     */     } else {
/*     */       int bufferSize;
/*  82 */       for (bufferSize = 32; bufferSize <= 1024; bufferSize *= 2) {
/*  83 */         pcchNameBufferSize = new WinDef.DWORDByReference(new WinDef.DWORD(bufferSize));
/*  84 */         mem = new Memory((bufferSize * CHAR_TO_BYTES));
/*  85 */         result = Pdh.INSTANCE.PdhLookupPerfNameByIndex(szMachineName, dwNameIndex, (Pointer)mem, pcchNameBufferSize);
/*  86 */         if (result != -1073738819 && result != -1073738814) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*  91 */     if (result != 0) {
/*  92 */       throw new PdhException(result);
/*     */     }
/*     */ 
/*     */     
/*  96 */     if (CHAR_TO_BYTES == 1) {
/*  97 */       return mem.getString(0L);
/*     */     }
/*  99 */     return mem.getWideString(0L);
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
/*     */   public static int PdhLookupPerfIndexByEnglishName(String szNameBuffer) {
/* 115 */     String[] counters = Advapi32Util.registryGetStringArray(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Perflib\\009", "Counter");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     for (int i = 1; i < counters.length; i += 2) {
/* 122 */       if (counters[i].equals(szNameBuffer)) {
/*     */         try {
/* 124 */           return Integer.parseInt(counters[i - 1]);
/* 125 */         } catch (NumberFormatException e) {
/*     */           
/* 127 */           return 0;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 132 */     return 0;
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
/*     */   public static PdhEnumObjectItems PdhEnumObjectItems(String szDataSource, String szMachineName, String szObjectName, int dwDetailLevel) {
/* 162 */     List<String> counters = new ArrayList<>();
/* 163 */     List<String> instances = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     WinDef.DWORDByReference pcchCounterListLength = new WinDef.DWORDByReference(new WinDef.DWORD(0L));
/* 169 */     WinDef.DWORDByReference pcchInstanceListLength = new WinDef.DWORDByReference(new WinDef.DWORD(0L));
/* 170 */     int result = Pdh.INSTANCE.PdhEnumObjectItems(szDataSource, szMachineName, szObjectName, null, pcchCounterListLength, null, pcchInstanceListLength, dwDetailLevel, 0);
/*     */     
/* 172 */     if (result != 0 && result != -2147481646) {
/* 173 */       throw new PdhException(result);
/*     */     }
/*     */     
/* 176 */     Memory mszCounterList = null;
/* 177 */     Memory mszInstanceList = null;
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 182 */       if (pcchCounterListLength.getValue().intValue() > 0) {
/* 183 */         mszCounterList = new Memory((pcchCounterListLength.getValue().intValue() * CHAR_TO_BYTES));
/*     */       }
/*     */       
/* 186 */       if (pcchInstanceListLength.getValue().intValue() > 0) {
/* 187 */         mszInstanceList = new Memory((pcchInstanceListLength.getValue().intValue() * CHAR_TO_BYTES));
/*     */       }
/*     */       
/* 190 */       result = Pdh.INSTANCE.PdhEnumObjectItems(szDataSource, szMachineName, szObjectName, (Pointer)mszCounterList, pcchCounterListLength, (Pointer)mszInstanceList, pcchInstanceListLength, dwDetailLevel, 0);
/*     */       
/* 192 */       if (result != -2147481646) {
/*     */         continue;
/*     */       }
/*     */       
/* 196 */       if (mszCounterList != null) {
/* 197 */         long l = mszCounterList.size() / CHAR_TO_BYTES;
/* 198 */         pcchCounterListLength.setValue(new WinDef.DWORD(l + 1024L));
/* 199 */         mszCounterList.close();
/*     */       } 
/* 201 */       if (mszInstanceList == null)
/* 202 */         continue;  long tooSmallSize = mszInstanceList.size() / CHAR_TO_BYTES;
/* 203 */       pcchInstanceListLength.setValue(new WinDef.DWORD(tooSmallSize + 1024L));
/* 204 */       mszInstanceList.close();
/*     */     
/*     */     }
/* 207 */     while (result == -2147481646);
/*     */     
/* 209 */     if (result != 0) {
/* 210 */       throw new PdhException(result);
/*     */     }
/*     */ 
/*     */     
/* 214 */     if (mszCounterList != null) {
/* 215 */       int offset = 0;
/* 216 */       while (offset < mszCounterList.size()) {
/* 217 */         String s = null;
/* 218 */         if (CHAR_TO_BYTES == 1) {
/* 219 */           s = mszCounterList.getString(offset);
/*     */         } else {
/* 221 */           s = mszCounterList.getWideString(offset);
/*     */         } 
/*     */         
/* 224 */         if (s.isEmpty()) {
/*     */           break;
/*     */         }
/* 227 */         counters.add(s);
/*     */         
/* 229 */         offset += (s.length() + 1) * CHAR_TO_BYTES;
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     if (mszInstanceList != null) {
/* 234 */       int offset = 0;
/* 235 */       while (offset < mszInstanceList.size()) {
/* 236 */         String s = null;
/* 237 */         if (CHAR_TO_BYTES == 1) {
/* 238 */           s = mszInstanceList.getString(offset);
/*     */         } else {
/* 240 */           s = mszInstanceList.getWideString(offset);
/*     */         } 
/*     */         
/* 243 */         if (s.isEmpty()) {
/*     */           break;
/*     */         }
/* 246 */         instances.add(s);
/*     */         
/* 248 */         offset += (s.length() + 1) * CHAR_TO_BYTES;
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     return new PdhEnumObjectItems(counters, instances);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PdhEnumObjectItems
/*     */   {
/*     */     private final List<String> counters;
/*     */ 
/*     */     
/*     */     private final List<String> instances;
/*     */ 
/*     */     
/*     */     public PdhEnumObjectItems(List<String> counters, List<String> instances) {
/* 266 */       this.counters = copyAndEmptyListForNullList(counters);
/* 267 */       this.instances = copyAndEmptyListForNullList(instances);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<String> getCounters() {
/* 275 */       return this.counters;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<String> getInstances() {
/* 283 */       return this.instances;
/*     */     }
/*     */     
/*     */     private List<String> copyAndEmptyListForNullList(List<String> inputList) {
/* 287 */       if (inputList == null) {
/* 288 */         return new ArrayList<>();
/*     */       }
/* 290 */       return new ArrayList<>(inputList);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 296 */       return "PdhEnumObjectItems{counters=" + this.counters + ", instances=" + this.instances + '}';
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class PdhException extends RuntimeException {
/*     */     private final int errorCode;
/*     */     
/*     */     public PdhException(int errorCode) {
/* 304 */       super(String.format("Pdh call failed with error code 0x%08X", new Object[] { Integer.valueOf(errorCode) }));
/* 305 */       this.errorCode = errorCode;
/*     */     }
/*     */     
/*     */     public int getErrorCode() {
/* 309 */       return this.errorCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\PdhUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */