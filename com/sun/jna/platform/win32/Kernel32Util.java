/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.LastErrorException;
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.ptr.IntByReference;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import com.sun.jna.win32.W32APITypeMapper;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Kernel32Util
/*      */   implements WinDef
/*      */ {
/*      */   public static String getComputerName() {
/*   63 */     char[] buffer = new char[WinBase.MAX_COMPUTERNAME_LENGTH + 1];
/*   64 */     IntByReference lpnSize = new IntByReference(buffer.length);
/*   65 */     if (!Kernel32.INSTANCE.GetComputerName(buffer, lpnSize)) {
/*   66 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*   68 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void freeLocalMemory(Pointer ptr) {
/*   78 */     Pointer res = Kernel32.INSTANCE.LocalFree(ptr);
/*   79 */     if (res != null) {
/*   80 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void freeGlobalMemory(Pointer ptr) {
/*   91 */     Pointer res = Kernel32.INSTANCE.GlobalFree(ptr);
/*   92 */     if (res != null) {
/*   93 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandleRefs(WinNT.HANDLEByReference... refs) {
/*  108 */     Win32Exception err = null;
/*  109 */     for (WinNT.HANDLEByReference r : refs) {
/*      */       try {
/*  111 */         closeHandleRef(r);
/*  112 */       } catch (Win32Exception e) {
/*  113 */         if (err == null) {
/*  114 */           err = e;
/*      */         } else {
/*  116 */           err.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  121 */     if (err != null) {
/*  122 */       throw err;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandleRef(WinNT.HANDLEByReference ref) {
/*  132 */     closeHandle((ref == null) ? null : ref.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandles(WinNT.HANDLE... handles) {
/*  146 */     Win32Exception err = null;
/*  147 */     for (WinNT.HANDLE h : handles) {
/*      */       try {
/*  149 */         closeHandle(h);
/*  150 */       } catch (Win32Exception e) {
/*  151 */         if (err == null) {
/*  152 */           err = e;
/*      */         } else {
/*  154 */           err.addSuppressedReflected((Throwable)e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  159 */     if (err != null) {
/*  160 */       throw err;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void closeHandle(WinNT.HANDLE h) {
/*  172 */     if (h == null) {
/*      */       return;
/*      */     }
/*      */     
/*  176 */     if (!Kernel32.INSTANCE.CloseHandle(h)) {
/*  177 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessage(int code) {
/*  188 */     return formatMessage(code, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessage(int code, int primaryLangId, int sublangId) {
/*  210 */     PointerByReference buffer = new PointerByReference();
/*  211 */     int nLen = Kernel32.INSTANCE.FormatMessage(4864, null, code, 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  217 */         WinNT.LocaleMacros.MAKELANGID(primaryLangId, sublangId), buffer, 0, null);
/*      */     
/*  219 */     if (nLen == 0) {
/*  220 */       throw new LastErrorException(Native.getLastError());
/*      */     }
/*      */     
/*  223 */     Pointer ptr = buffer.getValue();
/*      */     try {
/*  225 */       String s = ptr.getWideString(0L);
/*  226 */       return s.trim();
/*      */     } finally {
/*  228 */       freeLocalMemory(ptr);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessage(WinNT.HRESULT code) {
/*  240 */     return formatMessage(code.intValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessage(WinNT.HRESULT code, int primaryLangId, int sublangId) {
/*  255 */     return formatMessage(code.intValue(), primaryLangId, sublangId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessageFromLastErrorCode(int code) {
/*  266 */     return formatMessage(W32Errors.HRESULT_FROM_WIN32(code));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatMessageFromLastErrorCode(int code, int primaryLangId, int sublangId) {
/*  281 */     return formatMessage(W32Errors.HRESULT_FROM_WIN32(code), primaryLangId, sublangId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLastErrorMessage() {
/*  289 */     return formatMessageFromLastErrorCode(Kernel32.INSTANCE
/*  290 */         .GetLastError());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLastErrorMessage(int primaryLangId, int sublangId) {
/*  298 */     return formatMessageFromLastErrorCode(Kernel32.INSTANCE
/*  299 */         .GetLastError(), primaryLangId, sublangId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getTempPath() {
/*  308 */     WinDef.DWORD nBufferLength = new WinDef.DWORD(260L);
/*  309 */     char[] buffer = new char[nBufferLength.intValue()];
/*  310 */     if (Kernel32.INSTANCE.GetTempPath(nBufferLength, buffer).intValue() == 0) {
/*  311 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  313 */     return Native.toString(buffer);
/*      */   }
/*      */   
/*      */   public static void deleteFile(String filename) {
/*  317 */     if (!Kernel32.INSTANCE.DeleteFile(filename)) {
/*  318 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> getLogicalDriveStrings() {
/*  328 */     WinDef.DWORD dwSize = Kernel32.INSTANCE.GetLogicalDriveStrings(new WinDef.DWORD(0L), null);
/*  329 */     if (dwSize.intValue() <= 0) {
/*  330 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  333 */     char[] buf = new char[dwSize.intValue()];
/*  334 */     dwSize = Kernel32.INSTANCE.GetLogicalDriveStrings(dwSize, buf);
/*  335 */     int bufSize = dwSize.intValue();
/*  336 */     if (bufSize <= 0) {
/*  337 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  340 */     return Native.toStringList(buf, 0, bufSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getFileAttributes(String fileName) {
/*  351 */     int fileAttributes = Kernel32.INSTANCE.GetFileAttributes(fileName);
/*  352 */     if (fileAttributes == -1) {
/*  353 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  355 */     return fileAttributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getFileType(String fileName) throws FileNotFoundException {
/*  365 */     File f = new File(fileName);
/*  366 */     if (!f.exists()) {
/*  367 */       throw new FileNotFoundException(fileName);
/*      */     }
/*      */     
/*  370 */     WinNT.HANDLE hFile = null;
/*  371 */     Win32Exception err = null; try {
/*      */       int rc;
/*  373 */       hFile = Kernel32.INSTANCE.CreateFile(fileName, -2147483648, 1, new WinBase.SECURITY_ATTRIBUTES(), 3, 128, (new WinNT.HANDLEByReference())
/*      */ 
/*      */           
/*  376 */           .getValue());
/*      */       
/*  378 */       if (WinBase.INVALID_HANDLE_VALUE.equals(hFile)) {
/*  379 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/*  382 */       int type = Kernel32.INSTANCE.GetFileType(hFile);
/*  383 */       switch (type) {
/*      */         case 0:
/*  385 */           rc = Kernel32.INSTANCE.GetLastError();
/*  386 */           switch (rc) {
/*      */             case 0:
/*      */               break;
/*      */           } 
/*  390 */           throw new Win32Exception(rc);
/*      */       } 
/*      */       
/*  393 */       return type;
/*      */     }
/*  395 */     catch (Win32Exception e) {
/*  396 */       throw err = e;
/*      */     } finally {
/*  398 */       cleanUp(hFile, err);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getDriveType(String rootName) {
/*  407 */     return Kernel32.INSTANCE.GetDriveType(rootName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getEnvironmentVariable(String name) {
/*  419 */     int size = Kernel32.INSTANCE.GetEnvironmentVariable(name, null, 0);
/*  420 */     if (size == 0)
/*  421 */       return null; 
/*  422 */     if (size < 0) {
/*  423 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  426 */     char[] buffer = new char[size];
/*  427 */     size = Kernel32.INSTANCE.GetEnvironmentVariable(name, buffer, buffer.length);
/*      */     
/*  429 */     if (size <= 0) {
/*  430 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  432 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> getEnvironmentVariables() {
/*  444 */     Pointer lpszEnvironmentBlock = Kernel32.INSTANCE.GetEnvironmentStrings();
/*  445 */     if (lpszEnvironmentBlock == null) {
/*  446 */       throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*      */     try {
/*  450 */       return getEnvironmentVariables(lpszEnvironmentBlock, 0L);
/*      */     } finally {
/*  452 */       if (!Kernel32.INSTANCE.FreeEnvironmentStrings(lpszEnvironmentBlock)) {
/*  453 */         throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, String> getEnvironmentVariables(Pointer lpszEnvironmentBlock, long offset) {
/*  470 */     if (lpszEnvironmentBlock == null) {
/*  471 */       return null;
/*      */     }
/*      */     
/*  474 */     Map<String, String> vars = new TreeMap<>();
/*  475 */     boolean asWideChars = isWideCharEnvironmentStringBlock(lpszEnvironmentBlock, offset);
/*  476 */     long stepFactor = asWideChars ? 2L : 1L;
/*  477 */     long curOffset = offset; while (true) {
/*  478 */       String nvp = readEnvironmentStringBlockEntry(lpszEnvironmentBlock, curOffset, asWideChars);
/*  479 */       int len = nvp.length();
/*  480 */       if (len == 0) {
/*      */         break;
/*      */       }
/*      */       
/*  484 */       int pos = nvp.indexOf('=');
/*  485 */       if (pos < 0) {
/*  486 */         throw new IllegalArgumentException("Missing variable value separator in " + nvp);
/*      */       }
/*      */       
/*  489 */       String name = nvp.substring(0, pos), value = nvp.substring(pos + 1);
/*  490 */       vars.put(name, value);
/*      */       
/*  492 */       curOffset += (len + 1) * stepFactor;
/*      */     } 
/*      */     
/*  495 */     return vars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String readEnvironmentStringBlockEntry(Pointer lpszEnvironmentBlock, long offset, boolean asWideChars) {
/*  511 */     long endOffset = findEnvironmentStringBlockEntryEnd(lpszEnvironmentBlock, offset, asWideChars);
/*  512 */     int dataLen = (int)(endOffset - offset);
/*  513 */     if (dataLen == 0) {
/*  514 */       return "";
/*      */     }
/*      */     
/*  517 */     int charsLen = asWideChars ? (dataLen / 2) : dataLen;
/*  518 */     char[] chars = new char[charsLen];
/*  519 */     long curOffset = offset, stepSize = asWideChars ? 2L : 1L;
/*  520 */     ByteOrder byteOrder = ByteOrder.nativeOrder();
/*  521 */     for (int index = 0; index < chars.length; index++, curOffset += stepSize) {
/*  522 */       byte b = lpszEnvironmentBlock.getByte(curOffset);
/*  523 */       if (asWideChars) {
/*  524 */         byte x = lpszEnvironmentBlock.getByte(curOffset + 1L);
/*  525 */         if (ByteOrder.LITTLE_ENDIAN.equals(byteOrder)) {
/*  526 */           chars[index] = (char)(x << 8 & 0xFF00 | b & 0xFF);
/*      */         } else {
/*  528 */           chars[index] = (char)(b << 8 & 0xFF00 | x & 0xFF);
/*      */         } 
/*      */       } else {
/*  531 */         chars[index] = (char)(b & 0xFF);
/*      */       } 
/*      */     } 
/*      */     
/*  535 */     return new String(chars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long findEnvironmentStringBlockEntryEnd(Pointer lpszEnvironmentBlock, long offset, boolean asWideChars) {
/*      */     long curOffset;
/*      */     long stepSize;
/*  551 */     for (curOffset = offset, stepSize = asWideChars ? 2L : 1L;; curOffset += stepSize) {
/*  552 */       byte b = lpszEnvironmentBlock.getByte(curOffset);
/*  553 */       if (b == 0) {
/*  554 */         return curOffset;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWideCharEnvironmentStringBlock(Pointer lpszEnvironmentBlock, long offset) {
/*  587 */     byte b0 = lpszEnvironmentBlock.getByte(offset);
/*  588 */     byte b1 = lpszEnvironmentBlock.getByte(offset + 1L);
/*  589 */     ByteOrder byteOrder = ByteOrder.nativeOrder();
/*  590 */     if (ByteOrder.LITTLE_ENDIAN.equals(byteOrder)) {
/*  591 */       return isWideCharEnvironmentStringBlock(b1);
/*      */     }
/*  593 */     return isWideCharEnvironmentStringBlock(b0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isWideCharEnvironmentStringBlock(byte charsetIndicator) {
/*  599 */     if (charsetIndicator != 0) {
/*  600 */       return false;
/*      */     }
/*  602 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int getPrivateProfileInt(String appName, String keyName, int defaultValue, String fileName) {
/*  628 */     return Kernel32.INSTANCE.GetPrivateProfileInt(appName, keyName, defaultValue, fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String getPrivateProfileString(String lpAppName, String lpKeyName, String lpDefault, String lpFileName) {
/*  683 */     char[] buffer = new char[1024];
/*  684 */     Kernel32.INSTANCE.GetPrivateProfileString(lpAppName, lpKeyName, lpDefault, buffer, new WinDef.DWORD(buffer.length), lpFileName);
/*      */     
/*  686 */     return Native.toString(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final void writePrivateProfileString(String appName, String keyName, String string, String fileName) {
/*  691 */     if (!Kernel32.INSTANCE.WritePrivateProfileString(appName, keyName, string, fileName))
/*      */     {
/*  693 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[] getLogicalProcessorInformation() {
/*      */     Memory memory;
/*  704 */     int sizePerStruct = (new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION()).size();
/*  705 */     WinDef.DWORDByReference bufferSize = new WinDef.DWORDByReference(new WinDef.DWORD(sizePerStruct));
/*      */ 
/*      */     
/*      */     while (true) {
/*  709 */       memory = new Memory(bufferSize.getValue().intValue());
/*  710 */       if (!Kernel32.INSTANCE.GetLogicalProcessorInformation((Pointer)memory, bufferSize)) {
/*      */         
/*  712 */         int err = Kernel32.INSTANCE.GetLastError();
/*  713 */         if (err != 122)
/*  714 */           throw new Win32Exception(err); 
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  719 */     WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION firstInformation = new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION((Pointer)memory);
/*      */     
/*  721 */     int returnedStructCount = bufferSize.getValue().intValue() / sizePerStruct;
/*      */     
/*  723 */     return (WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[])firstInformation
/*  724 */       .toArray((Structure[])new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION[returnedStructCount]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX[] getLogicalProcessorInformationEx(int relationshipType) {
/*      */     Memory memory;
/*  745 */     WinDef.DWORDByReference bufferSize = new WinDef.DWORDByReference(new WinDef.DWORD(1L));
/*      */     
/*      */     while (true) {
/*  748 */       memory = new Memory(bufferSize.getValue().intValue());
/*  749 */       if (!Kernel32.INSTANCE.GetLogicalProcessorInformationEx(relationshipType, (Pointer)memory, bufferSize)) {
/*  750 */         int err = Kernel32.INSTANCE.GetLastError();
/*  751 */         if (err != 122) {
/*  752 */           throw new Win32Exception(err);
/*      */         }
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  758 */     List<WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX> procInfoList = new ArrayList<>();
/*  759 */     int offset = 0;
/*  760 */     while (offset < bufferSize.getValue().intValue()) {
/*      */       
/*  762 */       WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX information = WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX.fromPointer(memory.share(offset));
/*  763 */       procInfoList.add(information);
/*  764 */       offset += information.size;
/*      */     } 
/*  766 */     return procInfoList.<WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX>toArray(new WinNT.SYSTEM_LOGICAL_PROCESSOR_INFORMATION_EX[0]);
/*      */   }
/*      */ 
/*      */   
/*  770 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String VOLUME_GUID_PATH_PREFIX = "\\\\?\\Volume{";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String VOLUME_GUID_PATH_SUFFIX = "}\\";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String[] getPrivateProfileSection(String appName, String fileName) {
/*  790 */     char[] buffer = new char[32768];
/*  791 */     if (Kernel32.INSTANCE.GetPrivateProfileSection(appName, buffer, new WinDef.DWORD(buffer.length), fileName).intValue() == 0) {
/*  792 */       int lastError = Kernel32.INSTANCE.GetLastError();
/*  793 */       if (lastError == 0) {
/*  794 */         return EMPTY_STRING_ARRAY;
/*      */       }
/*  796 */       throw new Win32Exception(lastError);
/*      */     } 
/*      */     
/*  799 */     return (new String(buffer)).split("\000");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String[] getPrivateProfileSectionNames(String fileName) {
/*  814 */     char[] buffer = new char[65536];
/*  815 */     if (Kernel32.INSTANCE.GetPrivateProfileSectionNames(buffer, new WinDef.DWORD(buffer.length), fileName).intValue() == 0) {
/*  816 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*  818 */     return (new String(buffer)).split("\000");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void writePrivateProfileSection(String appName, String[] strings, String fileName) {
/*  831 */     StringBuilder buffer = new StringBuilder();
/*  832 */     for (String string : strings)
/*  833 */       buffer.append(string).append(false); 
/*  834 */     buffer.append(false);
/*  835 */     if (!Kernel32.INSTANCE.WritePrivateProfileSection(appName, buffer.toString(), fileName)) {
/*  836 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final List<String> queryDosDevice(String lpszDeviceName, int maxTargetSize) {
/*  848 */     char[] lpTargetPath = new char[maxTargetSize];
/*  849 */     int dwSize = Kernel32.INSTANCE.QueryDosDevice(lpszDeviceName, lpTargetPath, lpTargetPath.length);
/*  850 */     if (dwSize == 0) {
/*  851 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  854 */     return Native.toStringList(lpTargetPath, 0, dwSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final List<String> getVolumePathNamesForVolumeName(String lpszVolumeName) {
/*  864 */     char[] lpszVolumePathNames = new char[261];
/*  865 */     IntByReference lpcchReturnLength = new IntByReference();
/*      */     
/*  867 */     if (!Kernel32.INSTANCE.GetVolumePathNamesForVolumeName(lpszVolumeName, lpszVolumePathNames, lpszVolumePathNames.length, lpcchReturnLength)) {
/*  868 */       int hr = Kernel32.INSTANCE.GetLastError();
/*  869 */       if (hr != 234) {
/*  870 */         throw new Win32Exception(hr);
/*      */       }
/*      */       
/*  873 */       int required = lpcchReturnLength.getValue();
/*  874 */       lpszVolumePathNames = new char[required];
/*      */       
/*  876 */       if (!Kernel32.INSTANCE.GetVolumePathNamesForVolumeName(lpszVolumeName, lpszVolumePathNames, lpszVolumePathNames.length, lpcchReturnLength)) {
/*  877 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */     } 
/*      */     
/*  881 */     int bufSize = lpcchReturnLength.getValue();
/*  882 */     return Native.toStringList(lpszVolumePathNames, 0, bufSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String extractVolumeGUID(String volumeGUIDPath) {
/*  902 */     if (volumeGUIDPath == null || volumeGUIDPath
/*  903 */       .length() <= "\\\\?\\Volume{".length() + "}\\".length() || 
/*  904 */       !volumeGUIDPath.startsWith("\\\\?\\Volume{") || 
/*  905 */       !volumeGUIDPath.endsWith("}\\")) {
/*  906 */       throw new IllegalArgumentException("Bad volume GUID path format: " + volumeGUIDPath);
/*      */     }
/*      */     
/*  909 */     return volumeGUIDPath.substring("\\\\?\\Volume{".length(), volumeGUIDPath.length() - "}\\".length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String QueryFullProcessImageName(int pid, int dwFlags) {
/*  925 */     WinNT.HANDLE hProcess = null;
/*  926 */     Win32Exception we = null;
/*      */     
/*      */     try {
/*  929 */       hProcess = Kernel32.INSTANCE.OpenProcess(1040, false, pid);
/*  930 */       if (hProcess == null) {
/*  931 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*  933 */       return QueryFullProcessImageName(hProcess, dwFlags);
/*  934 */     } catch (Win32Exception e) {
/*  935 */       throw we = e;
/*      */     } finally {
/*  937 */       cleanUp(hProcess, we);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String QueryFullProcessImageName(WinNT.HANDLE hProcess, int dwFlags) {
/*  955 */     int size = 260;
/*  956 */     IntByReference lpdwSize = new IntByReference();
/*      */     while (true) {
/*  958 */       char[] lpExeName = new char[size];
/*  959 */       lpdwSize.setValue(size);
/*  960 */       if (Kernel32.INSTANCE.QueryFullProcessImageName(hProcess, dwFlags, lpExeName, lpdwSize)) {
/*  961 */         return new String(lpExeName, 0, lpdwSize.getValue());
/*      */       }
/*  963 */       size += 1024;
/*  964 */       if (Kernel32.INSTANCE.GetLastError() != 122) {
/*  965 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] getResource(String path, String type, String name) {
/*  982 */     WinDef.HMODULE target = Kernel32.INSTANCE.LoadLibraryEx(path, null, 2);
/*      */     
/*  984 */     if (target == null) {
/*  985 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/*  988 */     Win32Exception err = null;
/*  989 */     Pointer start = null;
/*  990 */     int length = 0;
/*  991 */     byte[] results = null; try {
/*      */       Memory memory1, memory2;
/*  993 */       Pointer t = null;
/*      */       try {
/*  995 */         t = new Pointer(Long.parseLong(type));
/*  996 */       } catch (NumberFormatException e) {
/*  997 */         memory1 = new Memory((Native.WCHAR_SIZE * (type.length() + 1)));
/*  998 */         memory1.setWideString(0L, type);
/*      */       } 
/*      */       
/* 1001 */       Pointer n = null;
/*      */       try {
/* 1003 */         n = new Pointer(Long.parseLong(name));
/* 1004 */       } catch (NumberFormatException e) {
/* 1005 */         memory2 = new Memory((Native.WCHAR_SIZE * (name.length() + 1)));
/* 1006 */         memory2.setWideString(0L, name);
/*      */       } 
/*      */       
/* 1009 */       WinDef.HRSRC hrsrc = Kernel32.INSTANCE.FindResource(target, (Pointer)memory2, (Pointer)memory1);
/* 1010 */       if (hrsrc == null) {
/* 1011 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */ 
/*      */       
/* 1015 */       WinNT.HANDLE loaded = Kernel32.INSTANCE.LoadResource(target, hrsrc);
/* 1016 */       if (loaded == null) {
/* 1017 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 1020 */       length = Kernel32.INSTANCE.SizeofResource(target, hrsrc);
/* 1021 */       if (length == 0) {
/* 1022 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1027 */       start = Kernel32.INSTANCE.LockResource(loaded);
/* 1028 */       if (start == null) {
/* 1029 */         throw new IllegalStateException("LockResource returned null.");
/*      */       }
/*      */       
/* 1032 */       results = start.getByteArray(0L, length);
/* 1033 */     } catch (Win32Exception we) {
/* 1034 */       err = we;
/*      */     } finally {
/*      */       
/* 1037 */       if (target != null && 
/* 1038 */         !Kernel32.INSTANCE.FreeLibrary(target)) {
/* 1039 */         Win32Exception we = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/* 1040 */         if (err != null) {
/* 1041 */           we.addSuppressedReflected((Throwable)err);
/*      */         }
/* 1043 */         throw we;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1048 */     if (err != null) {
/* 1049 */       throw err;
/*      */     }
/*      */     
/* 1052 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, List<String>> getResourceNames(String path) {
/* 1065 */     WinDef.HMODULE target = Kernel32.INSTANCE.LoadLibraryEx(path, null, 2);
/*      */     
/* 1067 */     if (target == null) {
/* 1068 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1071 */     final List<String> types = new ArrayList<>();
/* 1072 */     final Map<String, List<String>> result = new LinkedHashMap<>();
/*      */     
/* 1074 */     WinBase.EnumResTypeProc ertp = new WinBase.EnumResTypeProc()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean invoke(WinDef.HMODULE module, Pointer type, Pointer lParam)
/*      */         {
/* 1082 */           if (Pointer.nativeValue(type) <= 65535L) {
/* 1083 */             types.add(Pointer.nativeValue(type) + "");
/*      */           } else {
/* 1085 */             types.add(type.getWideString(0L));
/*      */           } 
/* 1087 */           return true;
/*      */         }
/*      */       };
/*      */     
/* 1091 */     WinBase.EnumResNameProc ernp = new WinBase.EnumResNameProc()
/*      */       {
/*      */         public boolean invoke(WinDef.HMODULE module, Pointer type, Pointer name, Pointer lParam)
/*      */         {
/* 1095 */           String typeName = "";
/*      */           
/* 1097 */           if (Pointer.nativeValue(type) <= 65535L) {
/* 1098 */             typeName = Pointer.nativeValue(type) + "";
/*      */           } else {
/* 1100 */             typeName = type.getWideString(0L);
/*      */           } 
/*      */           
/* 1103 */           if (Pointer.nativeValue(name) < 65535L) {
/* 1104 */             ((List<String>)result.get(typeName)).add(Pointer.nativeValue(name) + "");
/*      */           } else {
/* 1106 */             ((List<String>)result.get(typeName)).add(name.getWideString(0L));
/*      */           } 
/*      */           
/* 1109 */           return true;
/*      */         }
/*      */       };
/*      */ 
/*      */     
/* 1114 */     Win32Exception err = null;
/*      */     try {
/* 1116 */       if (!Kernel32.INSTANCE.EnumResourceTypes(target, ertp, null)) {
/* 1117 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 1120 */       for (String typeName : types) {
/* 1121 */         Memory memory; result.put(typeName, new ArrayList<>());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1126 */         Pointer pointer = null;
/*      */         try {
/* 1128 */           pointer = new Pointer(Long.parseLong(typeName));
/* 1129 */         } catch (NumberFormatException e) {
/* 1130 */           memory = new Memory((Native.WCHAR_SIZE * (typeName.length() + 1)));
/* 1131 */           memory.setWideString(0L, typeName);
/*      */         } 
/*      */         
/* 1134 */         boolean callResult = Kernel32.INSTANCE.EnumResourceNames(target, (Pointer)memory, ernp, null);
/*      */         
/* 1136 */         if (!callResult) {
/* 1137 */           throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */         }
/*      */       } 
/* 1140 */     } catch (Win32Exception e) {
/* 1141 */       err = e;
/*      */     }
/*      */     finally {
/*      */       
/* 1145 */       if (target != null && 
/* 1146 */         !Kernel32.INSTANCE.FreeLibrary(target)) {
/* 1147 */         Win32Exception we = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/* 1148 */         if (err != null) {
/* 1149 */           we.addSuppressedReflected((Throwable)err);
/*      */         }
/* 1151 */         throw we;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1156 */     if (err != null) {
/* 1157 */       throw err;
/*      */     }
/* 1159 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Tlhelp32.MODULEENTRY32W> getModules(int processID) {
/* 1170 */     WinNT.HANDLE snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPMODULE, new WinDef.DWORD(processID));
/* 1171 */     if (snapshot == null) {
/* 1172 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1175 */     Win32Exception we = null;
/*      */     try {
/* 1177 */       Tlhelp32.MODULEENTRY32W first = new Tlhelp32.MODULEENTRY32W();
/*      */       
/* 1179 */       if (!Kernel32.INSTANCE.Module32FirstW(snapshot, first)) {
/* 1180 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/*      */       
/* 1183 */       List<Tlhelp32.MODULEENTRY32W> modules = new ArrayList<>();
/* 1184 */       modules.add(first);
/*      */       
/* 1186 */       Tlhelp32.MODULEENTRY32W next = new Tlhelp32.MODULEENTRY32W();
/* 1187 */       while (Kernel32.INSTANCE.Module32NextW(snapshot, next)) {
/* 1188 */         modules.add(next);
/* 1189 */         next = new Tlhelp32.MODULEENTRY32W();
/*      */       } 
/*      */       
/* 1192 */       int lastError = Kernel32.INSTANCE.GetLastError();
/*      */ 
/*      */ 
/*      */       
/* 1196 */       if (lastError != 0 && lastError != 18) {
/* 1197 */         throw new Win32Exception(lastError);
/*      */       }
/*      */       
/* 1200 */       return modules;
/* 1201 */     } catch (Win32Exception e) {
/* 1202 */       throw we = e;
/*      */     } finally {
/* 1204 */       cleanUp(snapshot, we);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String expandEnvironmentStrings(String input) {
/*      */     Memory resultMemory;
/* 1229 */     if (input == null) {
/* 1230 */       return "";
/*      */     }
/*      */     
/* 1233 */     int resultChars = Kernel32.INSTANCE.ExpandEnvironmentStrings(input, null, 0);
/*      */     
/* 1235 */     if (resultChars == 0) {
/* 1236 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */ 
/*      */     
/* 1240 */     if (W32APITypeMapper.DEFAULT == W32APITypeMapper.UNICODE) {
/* 1241 */       resultMemory = new Memory((resultChars * Native.WCHAR_SIZE));
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1246 */       resultMemory = new Memory((resultChars + 1));
/*      */     } 
/* 1248 */     resultChars = Kernel32.INSTANCE.ExpandEnvironmentStrings(input, (Pointer)resultMemory, resultChars);
/*      */     
/* 1250 */     if (resultChars == 0) {
/* 1251 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1254 */     if (W32APITypeMapper.DEFAULT == W32APITypeMapper.UNICODE) {
/* 1255 */       return resultMemory.getWideString(0L);
/*      */     }
/* 1257 */     return resultMemory.getString(0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WinDef.DWORD getCurrentProcessPriority() {
/* 1268 */     WinDef.DWORD dwPriorityClass = Kernel32.INSTANCE.GetPriorityClass(Kernel32.INSTANCE.GetCurrentProcess());
/* 1269 */     if (!isValidPriorityClass(dwPriorityClass)) {
/* 1270 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/* 1272 */     return dwPriorityClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCurrentProcessPriority(WinDef.DWORD dwPriorityClass) {
/* 1282 */     if (!isValidPriorityClass(dwPriorityClass)) {
/* 1283 */       throw new IllegalArgumentException("The given priority value is invalid!");
/*      */     }
/* 1285 */     if (!Kernel32.INSTANCE.SetPriorityClass(Kernel32.INSTANCE.GetCurrentProcess(), dwPriorityClass)) {
/* 1286 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCurrentProcessBackgroundMode(boolean enable) {
/* 1298 */     WinDef.DWORD dwPriorityClass = enable ? Kernel32.PROCESS_MODE_BACKGROUND_BEGIN : Kernel32.PROCESS_MODE_BACKGROUND_END;
/* 1299 */     if (!Kernel32.INSTANCE.SetPriorityClass(Kernel32.INSTANCE.GetCurrentProcess(), dwPriorityClass)) {
/* 1300 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getCurrentThreadPriority() {
/* 1311 */     int nPriority = Kernel32.INSTANCE.GetThreadPriority(Kernel32.INSTANCE.GetCurrentThread());
/* 1312 */     if (!isValidThreadPriority(nPriority)) {
/* 1313 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/* 1315 */     return nPriority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCurrentThreadPriority(int nPriority) {
/* 1325 */     if (!isValidThreadPriority(nPriority)) {
/* 1326 */       throw new IllegalArgumentException("The given priority value is invalid!");
/*      */     }
/* 1328 */     if (!Kernel32.INSTANCE.SetThreadPriority(Kernel32.INSTANCE.GetCurrentThread(), nPriority)) {
/* 1329 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCurrentThreadBackgroundMode(boolean enable) {
/* 1341 */     int nPriority = enable ? 65536 : 131072;
/* 1342 */     if (!Kernel32.INSTANCE.SetThreadPriority(Kernel32.INSTANCE.GetCurrentThread(), nPriority)) {
/* 1343 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WinDef.DWORD getProcessPriority(int pid) {
/* 1354 */     WinNT.HANDLE hProcess = Kernel32.INSTANCE.OpenProcess(1024, false, pid);
/* 1355 */     if (hProcess == null) {
/* 1356 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1359 */     Win32Exception we = null;
/*      */     try {
/* 1361 */       WinDef.DWORD dwPriorityClass = Kernel32.INSTANCE.GetPriorityClass(hProcess);
/* 1362 */       if (!isValidPriorityClass(dwPriorityClass)) {
/* 1363 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1365 */       return dwPriorityClass;
/* 1366 */     } catch (Win32Exception e) {
/* 1367 */       throw we = e;
/*      */     } finally {
/* 1369 */       cleanUp(hProcess, we);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setProcessPriority(int pid, WinDef.DWORD dwPriorityClass) {
/* 1381 */     if (!isValidPriorityClass(dwPriorityClass)) {
/* 1382 */       throw new IllegalArgumentException("The given priority value is invalid!");
/*      */     }
/*      */     
/* 1385 */     WinNT.HANDLE hProcess = Kernel32.INSTANCE.OpenProcess(512, false, pid);
/* 1386 */     if (hProcess == null) {
/* 1387 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1390 */     Win32Exception we = null;
/*      */     try {
/* 1392 */       if (!Kernel32.INSTANCE.SetPriorityClass(hProcess, dwPriorityClass)) {
/* 1393 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1395 */     } catch (Win32Exception e) {
/* 1396 */       we = e;
/*      */     } finally {
/* 1398 */       cleanUp(hProcess, we);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getThreadPriority(int tid) {
/* 1409 */     WinNT.HANDLE hThread = Kernel32.INSTANCE.OpenThread(64, false, tid);
/* 1410 */     if (hThread == null) {
/* 1411 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1414 */     Win32Exception we = null;
/*      */     try {
/* 1416 */       int nPriority = Kernel32.INSTANCE.GetThreadPriority(hThread);
/* 1417 */       if (!isValidThreadPriority(nPriority)) {
/* 1418 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1420 */       return nPriority;
/* 1421 */     } catch (Win32Exception e) {
/* 1422 */       throw we = e;
/*      */     } finally {
/* 1424 */       cleanUp(hThread, we);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setThreadPriority(int tid, int nPriority) {
/* 1436 */     if (!isValidThreadPriority(nPriority)) {
/* 1437 */       throw new IllegalArgumentException("The given priority value is invalid!");
/*      */     }
/*      */     
/* 1440 */     WinNT.HANDLE hThread = Kernel32.INSTANCE.OpenThread(32, false, tid);
/* 1441 */     if (hThread == null) {
/* 1442 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */     }
/*      */     
/* 1445 */     Win32Exception we = null;
/*      */     try {
/* 1447 */       if (!Kernel32.INSTANCE.SetThreadPriority(hThread, nPriority)) {
/* 1448 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*      */       }
/* 1450 */     } catch (Win32Exception e) {
/* 1451 */       we = e;
/*      */     } finally {
/* 1453 */       cleanUp(hThread, we);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isValidPriorityClass(WinDef.DWORD dwPriorityClass) {
/* 1465 */     return (Kernel32.NORMAL_PRIORITY_CLASS.equals(dwPriorityClass) || Kernel32.IDLE_PRIORITY_CLASS
/* 1466 */       .equals(dwPriorityClass) || Kernel32.HIGH_PRIORITY_CLASS
/* 1467 */       .equals(dwPriorityClass) || Kernel32.REALTIME_PRIORITY_CLASS
/* 1468 */       .equals(dwPriorityClass) || Kernel32.BELOW_NORMAL_PRIORITY_CLASS
/* 1469 */       .equals(dwPriorityClass) || Kernel32.ABOVE_NORMAL_PRIORITY_CLASS
/* 1470 */       .equals(dwPriorityClass));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isValidThreadPriority(int nPriority) {
/* 1481 */     switch (nPriority) {
/*      */       case -15:
/*      */       case -2:
/*      */       case -1:
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 15:
/* 1489 */         return true;
/*      */     } 
/* 1491 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void cleanUp(WinNT.HANDLE h, Win32Exception we) {
/*      */     try {
/* 1497 */       closeHandle(h);
/* 1498 */     } catch (Win32Exception e) {
/* 1499 */       if (we == null) {
/* 1500 */         we = e;
/*      */       } else {
/* 1502 */         we.addSuppressedReflected((Throwable)e);
/*      */       } 
/*      */     } 
/* 1505 */     if (we != null)
/* 1506 */       throw we; 
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\Kernel32Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */