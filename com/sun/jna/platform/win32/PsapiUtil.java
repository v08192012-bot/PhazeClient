/*    */ package com.sun.jna.platform.win32;
/*    */ 
/*    */ import com.sun.jna.Native;
/*    */ import com.sun.jna.ptr.IntByReference;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PsapiUtil
/*    */ {
/*    */   public static int[] enumProcesses() {
/* 46 */     int size = 0;
/* 47 */     int[] lpidProcess = null;
/* 48 */     IntByReference lpcbNeeded = new IntByReference();
/*    */     do {
/* 50 */       size += 1024;
/* 51 */       lpidProcess = new int[size];
/* 52 */       if (!Psapi.INSTANCE.EnumProcesses(lpidProcess, size * 4, lpcbNeeded)) {
/* 53 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*    */       }
/* 55 */     } while (size == lpcbNeeded.getValue() / 4);
/*    */     
/* 57 */     return Arrays.copyOf(lpidProcess, lpcbNeeded.getValue() / 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String GetProcessImageFileName(WinNT.HANDLE hProcess) {
/*    */     char[] filePath;
/* 75 */     int size = 2048;
/*    */     while (true) {
/* 77 */       filePath = new char[size];
/* 78 */       int length = Psapi.INSTANCE.GetProcessImageFileName(hProcess, filePath, filePath.length);
/*    */       
/* 80 */       if (length == 0) {
/* 81 */         if (Native.getLastError() != 122) {
/* 82 */           throw new Win32Exception(Native.getLastError());
/*    */         }
/* 84 */         size += 2048; continue;
/*    */       }  break;
/* 86 */     }  return Native.toString(filePath);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\PsapiUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */