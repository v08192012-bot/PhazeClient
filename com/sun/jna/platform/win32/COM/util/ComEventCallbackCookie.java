/*    */ package com.sun.jna.platform.win32.COM.util;
/*    */ 
/*    */ import com.sun.jna.platform.win32.WinDef;
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
/*    */ public class ComEventCallbackCookie
/*    */   implements IComEventCallbackCookie
/*    */ {
/*    */   WinDef.DWORD value;
/*    */   
/*    */   public ComEventCallbackCookie(WinDef.DWORD value) {
/* 31 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WinDef.DWORD getValue() {
/* 37 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\CO\\util\ComEventCallbackCookie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */