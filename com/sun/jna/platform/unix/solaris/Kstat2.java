/*     */ package com.sun.jna.platform.unix.solaris;
/*     */ 
/*     */ import com.sun.jna.Library;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.PointerType;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Structure.FieldOrder;
/*     */ import com.sun.jna.Union;
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
/*     */ public interface Kstat2
/*     */   extends Library
/*     */ {
/*  48 */   public static final Kstat2 INSTANCE = (Kstat2)Native.load("kstat2", Kstat2.class); public static final int KSTAT2_S_OK = 0;
/*     */   public static final int KSTAT2_S_NO_PERM = 1;
/*     */   public static final int KSTAT2_S_NO_MEM = 2;
/*     */   public static final int KSTAT2_S_NO_SPACE = 3;
/*     */   public static final int KSTAT2_S_INVAL_ARG = 4;
/*     */   public static final int KSTAT2_S_INVAL_STATE = 5;
/*     */   public static final int KSTAT2_S_INVAL_TYPE = 6;
/*     */   public static final int KSTAT2_S_NOT_FOUND = 7;
/*     */   public static final int KSTAT2_S_CONC_MOD = 8;
/*     */   public static final int KSTAT2_S_DEL_MAP = 9;
/*     */   public static final int KSTAT2_S_SYS_FAIL = 10;
/*     */   public static final int KSTAT2_M_STRING = 0;
/*     */   public static final int KSTAT2_M_GLOB = 1;
/*     */   public static final int KSTAT2_M_RE = 2;
/*     */   public static final byte KSTAT2_NVVT_MAP = 0;
/*     */   public static final byte KSTAT2_NVVT_INT = 1;
/*     */   public static final byte KSTAT2_NVVT_INTS = 2;
/*     */   public static final byte KSTAT2_NVVT_STR = 3;
/*     */   public static final byte KSTAT2_NVVT_STRS = 4;
/*     */   public static final byte KSTAT2_NVK_SYS = 1;
/*     */   public static final byte KSTAT2_NVK_USR = 2;
/*     */   public static final byte KSTAT2_NVK_MAP = 4;
/*     */   public static final byte KSTAT2_NVK_ALL = 7;
/*     */   public static final short KSTAT2_NVF_NONE = 0;
/*     */   public static final short KSTAT2_NVF_INVAL = 1;
/*     */   
/*     */   int kstat2_open(PointerByReference paramPointerByReference, Kstat2MatcherList paramKstat2MatcherList);
/*     */   
/*     */   int kstat2_update(Kstat2Handle paramKstat2Handle);
/*     */   
/*     */   int kstat2_close(PointerByReference paramPointerByReference);
/*     */   
/*     */   int kstat2_alloc_matcher_list(PointerByReference paramPointerByReference);
/*     */   
/*     */   int kstat2_free_matcher_list(PointerByReference paramPointerByReference);
/*     */   
/*     */   int kstat2_add_matcher(int paramInt, String paramString, Kstat2MatcherList paramKstat2MatcherList);
/*     */   
/*     */   int kstat2_lookup_map(Kstat2Handle paramKstat2Handle, String paramString, PointerByReference paramPointerByReference);
/*     */   
/*     */   int kstat2_map_get(Kstat2Map paramKstat2Map, String paramString, PointerByReference paramPointerByReference);
/*     */   
/*     */   String kstat2_status_string(int paramInt);
/*     */   
/*  92 */   public static class Kstat2Handle extends PointerType { private PointerByReference ref = new PointerByReference();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Kstat2Handle() {
/* 101 */       this(null);
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
/*     */ 
/*     */ 
/*     */     
/*     */     public Kstat2Handle(Kstat2.Kstat2MatcherList matchers) {
/* 118 */       int ks = Kstat2.INSTANCE.kstat2_open(this.ref, matchers);
/* 119 */       if (ks != 0) {
/* 120 */         throw new Kstat2StatusException(ks);
/*     */       }
/* 122 */       setPointer(this.ref.getValue());
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
/*     */     public int update() {
/* 136 */       return Kstat2.INSTANCE.kstat2_update(this);
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
/*     */     public Kstat2.Kstat2Map lookupMap(String uri) {
/* 149 */       PointerByReference pMap = new PointerByReference();
/* 150 */       int ks = Kstat2.INSTANCE.kstat2_lookup_map(this, uri, pMap);
/* 151 */       if (ks != 0) {
/* 152 */         throw new Kstat2StatusException(ks);
/*     */       }
/* 154 */       return new Kstat2.Kstat2Map(pMap.getValue());
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
/*     */     public int close() {
/* 167 */       return Kstat2.INSTANCE.kstat2_close(this.ref);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Kstat2MatcherList
/*     */     extends PointerType
/*     */   {
/* 175 */     private PointerByReference ref = new PointerByReference();
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
/*     */     public Kstat2MatcherList() {
/* 187 */       int ks = Kstat2.INSTANCE.kstat2_alloc_matcher_list(this.ref);
/* 188 */       if (ks != 0) {
/* 189 */         throw new Kstat2StatusException(ks);
/*     */       }
/* 191 */       setPointer(this.ref.getValue());
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
/*     */     public int addMatcher(int type, String match) {
/* 215 */       return Kstat2.INSTANCE.kstat2_add_matcher(type, match, this);
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
/*     */     public int free() {
/* 228 */       return Kstat2.INSTANCE.kstat2_free_matcher_list(this.ref);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Kstat2Map
/*     */     extends PointerType
/*     */   {
/*     */     public Kstat2Map() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Kstat2Map(Pointer p) {
/* 242 */       super(p);
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
/*     */     public Kstat2.Kstat2NV mapGet(String name) {
/* 255 */       PointerByReference pbr = new PointerByReference();
/* 256 */       int ks = Kstat2.INSTANCE.kstat2_map_get(this, name, pbr);
/* 257 */       if (ks != 0) {
/* 258 */         throw new Kstat2StatusException(ks);
/*     */       }
/* 260 */       return new Kstat2.Kstat2NV(pbr.getValue());
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
/*     */     public Object getValue(String name) {
/*     */       try {
/* 291 */         Kstat2.Kstat2NV nv = mapGet(name);
/* 292 */         if (nv.flags == 1) {
/* 293 */           return null;
/*     */         }
/* 295 */         switch (nv.type) {
/*     */           case 0:
/* 297 */             return nv.data.map;
/*     */           case 1:
/* 299 */             return Long.valueOf(nv.data.integerVal);
/*     */           case 2:
/* 301 */             return nv.data.integers.addr.getLongArray(0L, nv.data.integers.len);
/*     */           case 3:
/* 303 */             return nv.data.strings.addr.getString(0L);
/*     */           case 4:
/* 305 */             return nv.data.strings.addr.getStringArray(0L, nv.data.strings.len);
/*     */         } 
/* 307 */         return null;
/*     */       }
/* 309 */       catch (Kstat2StatusException e) {
/* 310 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FieldOrder({"name", "type", "kind", "flags", "data"})
/*     */   public static class Kstat2NV
/*     */     extends Structure
/*     */   {
/*     */     public String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte kind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short flags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Kstat2NV(Pointer p) {
/* 350 */       super(p);
/* 351 */       read();
/*     */     } public UNION data; public static class UNION extends Union {
/*     */       public Kstat2.Kstat2Map map; public long integerVal; public IntegersArr integers; public StringsArr strings; @FieldOrder({"addr", "len"}) public static class IntegersArr extends Structure {
/*     */         public Pointer addr; public int len; } @FieldOrder({"addr", "len"}) public static class StringsArr extends Structure {
/*     */         public Pointer addr; public int len; } } @FieldOrder({"addr", "len"}) public static class IntegersArr extends Structure {
/* 356 */       public Pointer addr; public int len; } @FieldOrder({"addr", "len"}) public static class StringsArr extends Structure { public Pointer addr; public int len; } public Kstat2NV() {} public void read() { super.read();
/* 357 */       switch (this.type) {
/*     */         case 0:
/* 359 */           this.data.setType(Kstat2.Kstat2Map.class);
/*     */           break;
/*     */         case 1:
/* 362 */           this.data.setType(long.class);
/*     */           break;
/*     */         case 2:
/* 365 */           this.data.setType(UNION.IntegersArr.class);
/*     */           break;
/*     */         case 3:
/*     */         case 4:
/* 369 */           this.data.setType(UNION.StringsArr.class);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 374 */       this.data.read(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platfor\\unix\solaris\Kstat2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */