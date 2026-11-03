/*    */ package com.sun.jna;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class Klass
/*    */ {
/*    */   public static <T> T newInstance(Class<T> klass) {
/*    */     try {
/* 48 */       return klass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 49 */     } catch (IllegalAccessException|IllegalArgumentException|InstantiationException|NoSuchMethodException|SecurityException e) {
/* 50 */       String msg = "Can't create an instance of " + klass + ", requires a public no-arg constructor: " + e;
/*    */       
/* 52 */       throw new IllegalArgumentException(msg, e);
/* 53 */     } catch (InvocationTargetException e) {
/* 54 */       if (e.getCause() instanceof RuntimeException) {
/* 55 */         throw (RuntimeException)e.getCause();
/*    */       }
/* 57 */       String msg = "Can't create an instance of " + klass + ", requires a public no-arg constructor: " + e;
/*    */       
/* 59 */       throw new IllegalArgumentException(msg, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\Klass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */