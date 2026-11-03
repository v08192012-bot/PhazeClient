/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.PointerType;
/*     */ import com.sun.jna.Structure;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public interface WTypes
/*     */ {
/*     */   public static final int CLSCTX_INPROC_SERVER = 1;
/*     */   public static final int CLSCTX_INPROC_HANDLER = 2;
/*     */   public static final int CLSCTX_LOCAL_SERVER = 4;
/*     */   public static final int CLSCTX_INPROC_SERVER16 = 8;
/*     */   public static final int CLSCTX_REMOTE_SERVER = 16;
/*     */   public static final int CLSCTX_INPROC_HANDLER16 = 32;
/*     */   public static final int CLSCTX_RESERVED1 = 64;
/*     */   public static final int CLSCTX_RESERVED2 = 128;
/*     */   public static final int CLSCTX_RESERVED3 = 256;
/*     */   public static final int CLSCTX_RESERVED4 = 512;
/*     */   public static final int CLSCTX_NO_CODE_DOWNLOAD = 1024;
/*     */   public static final int CLSCTX_RESERVED5 = 2048;
/*     */   public static final int CLSCTX_NO_CUSTOM_MARSHAL = 4096;
/*     */   public static final int CLSCTX_ENABLE_CODE_DOWNLOAD = 8192;
/*     */   public static final int CLSCTX_NO_FAILURE_LOG = 16384;
/*     */   public static final int CLSCTX_DISABLE_AAA = 32768;
/*     */   public static final int CLSCTX_ENABLE_AAA = 65536;
/*     */   public static final int CLSCTX_FROM_DEFAULT_CONTEXT = 131072;
/*     */   public static final int CLSCTX_ACTIVATE_32_BIT_SERVER = 262144;
/*     */   public static final int CLSCTX_ACTIVATE_64_BIT_SERVER = 524288;
/*     */   public static final int CLSCTX_ENABLE_CLOAKING = 1048576;
/*     */   public static final int CLSCTX_APPCONTAINER = 4194304;
/*     */   public static final int CLSCTX_ACTIVATE_AAA_AS_IU = 8388608;
/*     */   public static final int CLSCTX_PS_DLL = -2147483648;
/*     */   public static final int CLSCTX_SERVER = 21;
/*     */   public static final int CLSCTX_ALL = 7;
/*     */   
/*     */   public static class BSTR
/*     */     extends PointerType
/*     */   {
/*     */     public BSTR() {
/* 104 */       super(Pointer.NULL);
/*     */     }
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
/*     */     public BSTR(Pointer pointer) {
/* 117 */       super(pointer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public BSTR(String value) {
/* 127 */       setValue(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void setValue(String value) {
/* 135 */       if (value == null) {
/* 136 */         value = "";
/*     */       }
/*     */       try {
/* 139 */         byte[] encodedValue = value.getBytes("UTF-16LE");
/*     */ 
/*     */         
/* 142 */         Memory mem = new Memory((4 + encodedValue.length + 2));
/* 143 */         mem.clear();
/* 144 */         mem.setInt(0L, encodedValue.length);
/* 145 */         mem.write(4L, encodedValue, 0, encodedValue.length);
/* 146 */         setPointer(mem.share(4L));
/* 147 */       } catch (UnsupportedEncodingException ex) {
/* 148 */         throw new RuntimeException("UTF-16LE charset is not supported", ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getValue() {
/*     */       try {
/* 154 */         Pointer pointer = getPointer();
/* 155 */         if (pointer == null) {
/* 156 */           return "";
/*     */         }
/* 158 */         int stringLength = pointer.getInt(-4L);
/* 159 */         return new String(pointer.getByteArray(0L, stringLength), "UTF-16LE");
/* 160 */       } catch (UnsupportedEncodingException ex) {
/* 161 */         throw new RuntimeException("UTF-16LE charset is not supported", ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 167 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BSTRByReference extends com.sun.jna.ptr.ByReference {
/*     */     public BSTRByReference() {
/* 173 */       super(Native.POINTER_SIZE);
/*     */     }
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
/*     */     public BSTRByReference(WTypes.BSTR value) {
/* 187 */       this();
/* 188 */       setValue(value);
/*     */     }
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
/*     */     public void setValue(WTypes.BSTR value) {
/* 202 */       getPointer().setPointer(0L, value.getPointer());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WTypes.BSTR getValue() {
/* 214 */       return new WTypes.BSTR(getPointer().getPointer(0L));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/* 224 */       WTypes.BSTR b = getValue();
/* 225 */       return (b == null) ? null : b.getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LPSTR
/*     */     extends PointerType {
/*     */     public static class ByReference
/*     */       extends LPSTR implements Structure.ByReference {}
/*     */     
/*     */     public LPSTR() {
/* 235 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public LPSTR(Pointer pointer) {
/* 239 */       super(pointer);
/*     */     }
/*     */     
/*     */     public LPSTR(String value) {
/* 243 */       this((Pointer)new Memory(value.length() + 1L));
/* 244 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 248 */       getPointer().setString(0L, value);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 252 */       Pointer pointer = getPointer();
/* 253 */       String str = null;
/* 254 */       if (pointer != null) {
/* 255 */         str = pointer.getString(0L);
/*     */       }
/* 257 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 262 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LPWSTR
/*     */     extends PointerType {
/*     */     public static class ByReference
/*     */       extends LPWSTR implements Structure.ByReference {}
/*     */     
/*     */     public LPWSTR() {
/* 272 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public LPWSTR(Pointer pointer) {
/* 276 */       super(pointer);
/*     */     }
/*     */     
/*     */     public LPWSTR(String value) {
/* 280 */       this((Pointer)new Memory((value.length() + 1L) * Native.WCHAR_SIZE));
/* 281 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 285 */       getPointer().setWideString(0L, value);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 289 */       Pointer pointer = getPointer();
/* 290 */       String str = null;
/* 291 */       if (pointer != null) {
/* 292 */         str = pointer.getWideString(0L);
/*     */       }
/* 294 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 299 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LPOLESTR
/*     */     extends PointerType {
/*     */     public static class ByReference
/*     */       extends LPOLESTR implements Structure.ByReference {}
/*     */     
/*     */     public LPOLESTR() {
/* 309 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public LPOLESTR(Pointer pointer) {
/* 313 */       super(pointer);
/*     */     }
/*     */     
/*     */     public LPOLESTR(String value) {
/* 317 */       super((Pointer)new Memory((value.length() + 1L) * Native.WCHAR_SIZE));
/* 318 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 322 */       getPointer().setWideString(0L, value);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 326 */       Pointer pointer = getPointer();
/* 327 */       String str = null;
/* 328 */       if (pointer != null) {
/* 329 */         str = pointer.getWideString(0L);
/*     */       }
/* 331 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 336 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class VARTYPE extends WinDef.USHORT {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public VARTYPE() {
/* 344 */       this(0);
/*     */     }
/*     */     
/*     */     public VARTYPE(int value) {
/* 348 */       super(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class VARTYPEByReference extends com.sun.jna.ptr.ByReference {
/*     */     public VARTYPEByReference() {
/* 354 */       super(2);
/*     */     }
/*     */     
/*     */     public VARTYPEByReference(WTypes.VARTYPE type) {
/* 358 */       super(2);
/* 359 */       setValue(type);
/*     */     }
/*     */     
/*     */     public VARTYPEByReference(short type) {
/* 363 */       super(2);
/* 364 */       getPointer().setShort(0L, type);
/*     */     }
/*     */     
/*     */     public void setValue(WTypes.VARTYPE value) {
/* 368 */       getPointer().setShort(0L, value.shortValue());
/*     */     }
/*     */     
/*     */     public WTypes.VARTYPE getValue() {
/* 372 */       return new WTypes.VARTYPE(getPointer().getShort(0L));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\WTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */