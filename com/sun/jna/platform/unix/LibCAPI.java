/*     */ package com.sun.jna.platform.unix;public interface LibCAPI extends Reboot, Resource { public static final int HOST_NAME_MAX = 255;
/*     */   
/*     */   int getuid();
/*     */   
/*     */   int geteuid();
/*     */   
/*     */   int getgid();
/*     */   
/*     */   int getegid();
/*     */   
/*     */   int setuid(int paramInt);
/*     */   
/*     */   int seteuid(int paramInt);
/*     */   
/*     */   int setgid(int paramInt);
/*     */   
/*     */   int setegid(int paramInt);
/*     */   
/*     */   int gethostname(byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   int sethostname(String paramString, int paramInt);
/*     */   
/*     */   int getdomainname(byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   int setdomainname(String paramString, int paramInt);
/*     */   
/*     */   String getenv(String paramString);
/*     */   
/*     */   int setenv(String paramString1, String paramString2, int paramInt);
/*     */   
/*     */   int unsetenv(String paramString);
/*     */   
/*     */   int getloadavg(double[] paramArrayOfdouble, int paramInt);
/*     */   
/*     */   int close(int paramInt);
/*     */   
/*     */   int msync(Pointer paramPointer, size_t paramsize_t, int paramInt);
/*     */   
/*     */   int munmap(Pointer paramPointer, size_t paramsize_t);
/*     */   
/*     */   public static class size_t extends IntegerType {
/*  42 */     public static final size_t ZERO = new size_t();
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public static class ByReference
/*     */       extends com.sun.jna.ptr.ByReference {
/*     */       public ByReference() {
/*  48 */         this(0L);
/*     */       }
/*     */       
/*     */       public ByReference(long value) {
/*  52 */         this(new LibCAPI.size_t(value));
/*     */       }
/*     */       
/*     */       public ByReference(LibCAPI.size_t value) {
/*  56 */         super(Native.SIZE_T_SIZE);
/*  57 */         setValue(value);
/*     */       }
/*     */       
/*     */       public void setValue(long value) {
/*  61 */         setValue(new LibCAPI.size_t(value));
/*     */       }
/*     */       
/*     */       public void setValue(LibCAPI.size_t value) {
/*  65 */         if (Native.SIZE_T_SIZE > 4) {
/*  66 */           getPointer().setLong(0L, value.longValue());
/*     */         } else {
/*  68 */           getPointer().setInt(0L, value.intValue());
/*     */         } 
/*     */       }
/*     */       
/*     */       public long longValue() {
/*  73 */         return (Native.SIZE_T_SIZE > 4) ? getPointer().getLong(0L) : getPointer().getInt(0L);
/*     */       }
/*     */       
/*     */       public LibCAPI.size_t getValue() {
/*  77 */         return new LibCAPI.size_t(longValue());
/*     */       }
/*     */     }
/*     */     
/*     */     public size_t() {
/*  82 */       this(0L);
/*     */     }
/*     */     
/*     */     public size_t(long value) {
/*  86 */       super(Native.SIZE_T_SIZE, value, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ssize_t
/*     */     extends IntegerType
/*     */   {
/*  95 */     public static final ssize_t ZERO = new ssize_t();
/*     */     
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public ssize_t() {
/* 100 */       this(0L);
/*     */     }
/*     */     
/*     */     public ssize_t(long value) {
/* 104 */       super(Native.SIZE_T_SIZE, value, false);
/*     */     }
/*     */   } }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platfor\\unix\LibCAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */