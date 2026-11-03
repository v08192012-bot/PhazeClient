/*    */ package com.sun.jna.platform.unix.aix;
/*    */ 
/*    */ import com.sun.jna.Native;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ final class SharedObjectLoader
/*    */ {
/*    */   static Perfstat getPerfstatInstance() {
/* 45 */     Map<String, Object> options = getOptions();
/*    */     try {
/* 47 */       return (Perfstat)Native.load("/usr/lib/libperfstat.a(shr_64.o)", Perfstat.class, options);
/* 48 */     } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/*    */ 
/*    */       
/* 51 */       return (Perfstat)Native.load("/usr/lib/libperfstat.a(shr.o)", Perfstat.class, options);
/*    */     } 
/*    */   }
/*    */   private static Map<String, Object> getOptions() {
/* 55 */     int RTLD_MEMBER = 262144;
/* 56 */     int RTLD_GLOBAL = 65536;
/* 57 */     int RTLD_LAZY = 4;
/* 58 */     Map<String, Object> options = new HashMap<>();
/* 59 */     options.put("open-flags", Integer.valueOf(RTLD_MEMBER | RTLD_GLOBAL | RTLD_LAZY));
/* 60 */     return Collections.unmodifiableMap(options);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platfor\\unix\aix\SharedObjectLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */