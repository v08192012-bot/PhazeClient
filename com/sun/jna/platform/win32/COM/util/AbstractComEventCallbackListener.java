/*    */ package com.sun.jna.platform.win32.COM.util;
/*    */ 
/*    */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
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
/*    */ public abstract class AbstractComEventCallbackListener
/*    */   implements IComEventCallbackListener
/*    */ {
/* 31 */   IDispatchCallback dispatchCallback = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDispatchCallbackListener(IDispatchCallback dispatchCallback) {
/* 37 */     this.dispatchCallback = dispatchCallback;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\CO\\util\AbstractComEventCallbackListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */