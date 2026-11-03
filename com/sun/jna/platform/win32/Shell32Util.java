/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Shell32Util
/*     */ {
/*     */   public static String getFolderPath(WinDef.HWND hwnd, int nFolder, WinDef.DWORD dwFlags) {
/*  54 */     char[] pszPath = new char[260];
/*  55 */     WinNT.HRESULT hr = Shell32.INSTANCE.SHGetFolderPath(hwnd, nFolder, null, dwFlags, pszPath);
/*     */     
/*  57 */     if (!hr.equals(W32Errors.S_OK)) {
/*  58 */       throw new Win32Exception(hr);
/*     */     }
/*  60 */     return Native.toString(pszPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFolderPath(int nFolder) {
/*  71 */     return getFolderPath(null, nFolder, ShlObj.SHGFP_TYPE_CURRENT);
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
/*     */   public static String getKnownFolderPath(Guid.GUID guid) throws Win32Exception {
/*  86 */     int flags = ShlObj.KNOWN_FOLDER_FLAG.NONE.getFlag();
/*  87 */     PointerByReference outPath = new PointerByReference();
/*  88 */     WinNT.HANDLE token = null;
/*  89 */     WinNT.HRESULT hr = Shell32.INSTANCE.SHGetKnownFolderPath(guid, flags, token, outPath);
/*     */     
/*  91 */     if (!W32Errors.SUCCEEDED(hr.intValue()))
/*     */     {
/*  93 */       throw new Win32Exception(hr);
/*     */     }
/*     */     
/*  96 */     String result = outPath.getValue().getWideString(0L);
/*  97 */     Ole32.INSTANCE.CoTaskMemFree(outPath.getValue());
/*     */     
/*  99 */     return result;
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
/*     */   public static final String getSpecialFolderPath(int csidl, boolean create) {
/* 113 */     char[] pszPath = new char[260];
/* 114 */     if (!Shell32.INSTANCE.SHGetSpecialFolderPath(null, pszPath, csidl, create))
/* 115 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError()); 
/* 116 */     return Native.toString(pszPath);
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
/*     */   public static final String[] CommandLineToArgv(String cmdLine) {
/* 130 */     WString cl = new WString(cmdLine);
/* 131 */     IntByReference nargs = new IntByReference();
/* 132 */     Pointer strArr = Shell32.INSTANCE.CommandLineToArgvW(cl, nargs);
/* 133 */     if (strArr != null) {
/*     */       try {
/* 135 */         return strArr.getWideStringArray(0L, nargs.getValue());
/*     */       } finally {
/* 137 */         Kernel32.INSTANCE.LocalFree(strArr);
/*     */       } 
/*     */     }
/* 140 */     throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\Shell32Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */