/*    */ package com.sun.jna;
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
/*    */ public class FromNativeContext
/*    */ {
/*    */   private Class<?> type;
/*    */   
/*    */   FromNativeContext(Class<?> javaType) {
/* 30 */     this.type = javaType;
/*    */   }
/*    */   
/*    */   public Class<?> getTargetType() {
/* 34 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\FromNativeContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */