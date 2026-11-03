/*    */ package com.sun.jna.platform.unix.solaris;
/*    */ 
/*    */ import com.sun.jna.Native;
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
/*    */ public class Kstat2StatusException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final int kstat2Status;
/*    */   
/*    */   public Kstat2StatusException(int ks) {
/* 44 */     this(ks, formatMessage(ks));
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
/*    */   protected Kstat2StatusException(int ks, String msg) {
/* 56 */     super(msg);
/* 57 */     this.kstat2Status = ks;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getKstat2Status() {
/* 64 */     return this.kstat2Status;
/*    */   }
/*    */   
/*    */   private static String formatMessage(int ks) {
/* 68 */     String status = Kstat2.INSTANCE.kstat2_status_string(ks);
/* 69 */     if (ks == 10) {
/* 70 */       status = status + " (errno=" + Native.getLastError() + ")";
/*    */     }
/* 72 */     return "Kstat2Status error code " + ks + ": " + status;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platfor\\unix\solaris\Kstat2StatusException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */