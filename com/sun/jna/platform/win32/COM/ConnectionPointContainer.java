/*    */ package com.sun.jna.platform.win32.COM;
/*    */ 
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.platform.win32.Guid;
/*    */ import com.sun.jna.platform.win32.WinNT;
/*    */ import com.sun.jna.ptr.PointerByReference;
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
/*    */ public class ConnectionPointContainer
/*    */   extends Unknown
/*    */   implements IConnectionPointContainer
/*    */ {
/*    */   public ConnectionPointContainer(Pointer pointer) {
/* 35 */     super(pointer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WinNT.HRESULT EnumConnectionPoints() {
/* 42 */     int vTableId = 3;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WinNT.HRESULT FindConnectionPoint(Guid.REFIID riid, PointerByReference ppCP) {
/* 56 */     int vTableId = 4;
/* 57 */     return (WinNT.HRESULT)_invokeNativeObject(4, new Object[] {
/* 58 */           getPointer(), riid, ppCP }, WinNT.HRESULT.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\COM\ConnectionPointContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */