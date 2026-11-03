/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.IntegerType;
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.NativeLong;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Structure.FieldOrder;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.platform.win32.COM.COMUtils;
/*      */ import com.sun.jna.platform.win32.COM.Dispatch;
/*      */ import com.sun.jna.platform.win32.COM.TypeComp;
/*      */ import com.sun.jna.platform.win32.COM.Unknown;
/*      */ import com.sun.jna.ptr.DoubleByReference;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import java.io.Closeable;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface OaIdl
/*      */ {
/*   93 */   public static final long DATE_OFFSET = (new Date(-1, 11, 30, 0, 0, 0)).getTime();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"wCode", "wReserved", "bstrSource", "bstrDescription", "bstrHelpFile", "dwHelpContext", "pvReserved", "pfnDeferredFillIn", "scode"})
/*      */   public static class EXCEPINFO
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.WORD wCode;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wReserved;
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.BSTR bstrSource;
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.BSTR bstrDescription;
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.BSTR bstrHelpFile;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwHelpContext;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.PVOID pvReserved;
/*      */ 
/*      */ 
/*      */     
/*      */     public ByReference pfnDeferredFillIn;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.SCODE scode;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends EXCEPINFO
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EXCEPINFO() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public EXCEPINFO(Pointer p) {
/*  151 */       super(p);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class VARIANT_BOOL extends IntegerType {
/*      */     private static final long serialVersionUID = 1L;
/*      */     public static final int SIZE = 2;
/*      */     
/*      */     public VARIANT_BOOL() {
/*  160 */       this(0L);
/*      */     }
/*      */     
/*      */     public VARIANT_BOOL(long value) {
/*  164 */       super(2, value);
/*      */     }
/*      */     
/*      */     public VARIANT_BOOL(boolean value) {
/*  168 */       this(value ? 65535L : 0L);
/*      */     }
/*      */     
/*      */     public boolean booleanValue() {
/*  172 */       return (shortValue() != 0);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class _VARIANT_BOOL extends VARIANT_BOOL {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public _VARIANT_BOOL() {
/*  180 */       this(0L);
/*      */     }
/*      */     
/*      */     public _VARIANT_BOOL(long value) {
/*  184 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class VARIANT_BOOLByReference extends com.sun.jna.ptr.ByReference {
/*      */     public VARIANT_BOOLByReference() {
/*  190 */       this(new OaIdl.VARIANT_BOOL(0L));
/*      */     }
/*      */     
/*      */     public VARIANT_BOOLByReference(OaIdl.VARIANT_BOOL value) {
/*  194 */       super(2);
/*  195 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.VARIANT_BOOL value) {
/*  199 */       getPointer().setShort(0L, value.shortValue());
/*      */     }
/*      */     
/*      */     public OaIdl.VARIANT_BOOL getValue() {
/*  203 */       return new OaIdl.VARIANT_BOOL(getPointer().getShort(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   public static class _VARIANT_BOOLByReference extends com.sun.jna.ptr.ByReference {
/*      */     public _VARIANT_BOOLByReference() {
/*  209 */       this(new OaIdl.VARIANT_BOOL(0L));
/*      */     }
/*      */     
/*      */     public _VARIANT_BOOLByReference(OaIdl.VARIANT_BOOL value) {
/*  213 */       super(2);
/*  214 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.VARIANT_BOOL value) {
/*  218 */       getPointer().setShort(0L, value.shortValue());
/*      */     }
/*      */     
/*      */     public OaIdl.VARIANT_BOOL getValue() {
/*  222 */       return new OaIdl.VARIANT_BOOL(getPointer().getShort(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"date"})
/*      */   public static class DATE
/*      */     extends Structure
/*      */   {
/*      */     private static final double MILLISECONDS_PER_DAY = 8.64E7D;
/*      */     public double date;
/*      */     
/*      */     public static class ByReference
/*      */       extends DATE
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public DATE() {}
/*      */     
/*      */     public DATE(double date) {
/*  241 */       this.date = date;
/*      */     }
/*      */     
/*      */     public DATE(Date javaDate) {
/*  245 */       setFromJavaDate(javaDate);
/*      */     }
/*      */     
/*      */     public Date getAsJavaDate() {
/*  249 */       WinBase.SYSTEMTIME systemtime = new WinBase.SYSTEMTIME();
/*  250 */       OleAuto.INSTANCE.VariantTimeToSystemTime(this.date, systemtime);
/*  251 */       Calendar calendar = systemtime.toCalendar();
/*      */ 
/*      */       
/*  254 */       int millis = (int)((long)(Math.abs(this.date) * 8.64E7D + 0.5D) % 1000L);
/*  255 */       if ((this.date > 0.0D && millis > 500) || (this.date < 0.0D && millis > 499)) {
/*  256 */         millis -= 1000;
/*      */       }
/*  258 */       calendar.set(14, millis);
/*  259 */       return calendar.getTime();
/*      */     }
/*      */     
/*      */     public void setFromJavaDate(Date javaDate) {
/*  263 */       Calendar calendar = Calendar.getInstance();
/*  264 */       calendar.setTime(javaDate);
/*  265 */       DoubleByReference pvtime = new DoubleByReference();
/*  266 */       OleAuto.INSTANCE.SystemTimeToVariantTime(new WinBase.SYSTEMTIME(calendar), pvtime);
/*  267 */       double value = pvtime.getValue();
/*      */ 
/*      */       
/*  270 */       this.date = value + Math.signum(value) * calendar.get(14) / 8.64E7D;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DISPID
/*      */     extends WinDef.LONG
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public DISPID() {
/*  281 */       this(0);
/*      */     }
/*      */     
/*      */     public DISPID(int value) {
/*  285 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DISPIDByReference extends com.sun.jna.ptr.ByReference {
/*      */     public DISPIDByReference() {
/*  291 */       this(new OaIdl.DISPID(0));
/*      */     }
/*      */     
/*      */     public DISPIDByReference(OaIdl.DISPID value) {
/*  295 */       super(OaIdl.DISPID.SIZE);
/*  296 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.DISPID value) {
/*  300 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */     
/*      */     public OaIdl.DISPID getValue() {
/*  304 */       return new OaIdl.DISPID(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MEMBERID extends DISPID {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public MEMBERID() {
/*  312 */       this(0);
/*      */     }
/*      */     
/*      */     public MEMBERID(int value) {
/*  316 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MEMBERIDByReference extends com.sun.jna.ptr.ByReference {
/*      */     public MEMBERIDByReference() {
/*  322 */       this(new OaIdl.MEMBERID(0));
/*      */     }
/*      */     
/*      */     public MEMBERIDByReference(OaIdl.MEMBERID value) {
/*  326 */       super(OaIdl.MEMBERID.SIZE);
/*  327 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.MEMBERID value) {
/*  331 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */     
/*      */     public OaIdl.MEMBERID getValue() {
/*  335 */       return new OaIdl.MEMBERID(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  342 */   public static final DISPID DISPID_COLLECT = new DISPID(-8);
/*      */ 
/*      */ 
/*      */   
/*  346 */   public static final DISPID DISPID_CONSTRUCTOR = new DISPID(-6);
/*      */ 
/*      */ 
/*      */   
/*  350 */   public static final DISPID DISPID_DESTRUCTOR = new DISPID(-7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  356 */   public static final DISPID DISPID_EVALUATE = new DISPID(-5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  362 */   public static final DISPID DISPID_NEWENUM = new DISPID(-4);
/*      */ 
/*      */ 
/*      */   
/*  366 */   public static final DISPID DISPID_PROPERTYPUT = new DISPID(-3);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  371 */   public static final DISPID DISPID_UNKNOWN = new DISPID(-1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  377 */   public static final DISPID DISPID_VALUE = new DISPID(0);
/*      */   
/*  379 */   public static final MEMBERID MEMBERID_NIL = new MEMBERID(DISPID_UNKNOWN
/*  380 */       .intValue());
/*      */   
/*      */   public static final int FADF_AUTO = 1;
/*      */   
/*      */   public static final int FADF_STATIC = 2;
/*      */   
/*      */   public static final int FADF_EMBEDDED = 4;
/*      */   
/*      */   public static final int FADF_FIXEDSIZE = 16;
/*      */   
/*      */   public static final int FADF_RECORD = 32;
/*      */   
/*      */   public static final int FADF_HAVEIID = 64;
/*      */   
/*      */   public static final int FADF_HAVEVARTYPE = 128;
/*      */   
/*      */   public static final int FADF_BSTR = 256;
/*      */   
/*      */   public static final int FADF_UNKNOWN = 512;
/*      */   
/*      */   public static final int FADF_DISPATCH = 1024;
/*      */   
/*      */   public static final int FADF_VARIANT = 2048;
/*      */   
/*      */   public static final int FADF_RESERVED = 61448;
/*      */ 
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class TYPEKIND
/*      */     extends Structure
/*      */   {
/*      */     public int value;
/*      */     
/*      */     public static final int TKIND_ENUM = 0;
/*      */     
/*      */     public static final int TKIND_RECORD = 1;
/*      */     
/*      */     public static final int TKIND_MODULE = 2;
/*      */     
/*      */     public static final int TKIND_INTERFACE = 3;
/*      */     
/*      */     public static final int TKIND_DISPATCH = 4;
/*      */     
/*      */     public static final int TKIND_COCLASS = 5;
/*      */     
/*      */     public static final int TKIND_ALIAS = 6;
/*      */     
/*      */     public static final int TKIND_UNION = 7;
/*      */     public static final int TKIND_MAX = 8;
/*      */     
/*      */     public static class ByReference
/*      */       extends TYPEKIND
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(int value) {
/*  437 */         super(value);
/*      */       }
/*      */       
/*      */       public ByReference(OaIdl.TYPEKIND typekind) {
/*  441 */         super(typekind.getPointer());
/*  442 */         this.value = typekind.value;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public TYPEKIND() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public TYPEKIND(int value) {
/*  453 */       this.value = value;
/*      */     }
/*      */     
/*      */     public TYPEKIND(Pointer pointer) {
/*  457 */       super(pointer);
/*  458 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class DESCKIND
/*      */     extends Structure
/*      */   {
/*      */     public int value;
/*      */ 
/*      */     
/*      */     public static final int DESCKIND_NONE = 0;
/*      */ 
/*      */     
/*      */     public static final int DESCKIND_FUNCDESC = 1;
/*      */     
/*      */     public static final int DESCKIND_VARDESC = 2;
/*      */     
/*      */     public static final int DESCKIND_TYPECOMP = 3;
/*      */     
/*      */     public static final int DESCKIND_IMPLICITAPPOBJ = 4;
/*      */     
/*      */     public static final int DESCKIND_MAX = 5;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends DESCKIND
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public DESCKIND() {}
/*      */ 
/*      */     
/*      */     public DESCKIND(int value) {
/*  494 */       this.value = value;
/*      */     }
/*      */     
/*      */     public DESCKIND(Pointer pointer) {
/*  498 */       super(pointer);
/*  499 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"cDims", "fFeatures", "cbElements", "cLocks", "pvData", "rgsabound"})
/*      */   public static class SAFEARRAY
/*      */     extends Structure
/*      */     implements Closeable
/*      */   {
/*      */     public WinDef.USHORT cDims;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.USHORT fFeatures;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.ULONG cbElements;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.ULONG cLocks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.PVOID pvData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends SAFEARRAY
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  565 */     public OaIdl.SAFEARRAYBOUND[] rgsabound = new OaIdl.SAFEARRAYBOUND[] { new OaIdl.SAFEARRAYBOUND() };
/*      */ 
/*      */     
/*      */     public SAFEARRAY() {}
/*      */ 
/*      */     
/*      */     public SAFEARRAY(Pointer pointer) {
/*  572 */       super(pointer);
/*  573 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public void read() {
/*  578 */       super.read();
/*  579 */       if (this.cDims.intValue() > 0) {
/*  580 */         this.rgsabound = (OaIdl.SAFEARRAYBOUND[])this.rgsabound[0].toArray(this.cDims.intValue());
/*      */       } else {
/*  582 */         this.rgsabound = new OaIdl.SAFEARRAYBOUND[] { new OaIdl.SAFEARRAYBOUND() };
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static ByReference createSafeArray(int... size) {
/*  599 */       return createSafeArray(new WTypes.VARTYPE(12), size);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static ByReference createSafeArray(WTypes.VARTYPE vartype, int... size) {
/*  615 */       OaIdl.SAFEARRAYBOUND[] rgsabound = (OaIdl.SAFEARRAYBOUND[])(new OaIdl.SAFEARRAYBOUND()).toArray(size.length);
/*  616 */       for (int i = 0; i < size.length; i++) {
/*  617 */         (rgsabound[i]).lLbound = new WinDef.LONG(0L);
/*  618 */         (rgsabound[i]).cElements = new WinDef.ULONG(size[size.length - i - 1]);
/*      */       } 
/*  620 */       ByReference data = OleAuto.INSTANCE.SafeArrayCreate(vartype, new WinDef.UINT(size.length), rgsabound);
/*  621 */       return data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void putElement(Object arg, int... indices) {
/*      */       WinNT.HRESULT hr;
/*      */       Memory mem;
/*  635 */       WinDef.LONG[] paramIndices = new WinDef.LONG[indices.length];
/*  636 */       for (int i = 0; i < indices.length; i++) {
/*  637 */         paramIndices[i] = new WinDef.LONG(indices[indices.length - i - 1]);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  642 */       switch (getVarType().intValue()) {
/*      */         case 11:
/*  644 */           mem = new Memory(2L);
/*  645 */           if (arg instanceof Boolean) {
/*  646 */             mem.setShort(0L, (short)(((Boolean)arg).booleanValue() ? 65535 : 0));
/*      */           } else {
/*  648 */             mem.setShort(0L, (short)((((Number)arg).intValue() > 0) ? 65535 : 0));
/*      */           } 
/*  650 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  651 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 16:
/*      */         case 17:
/*  655 */           mem = new Memory(1L);
/*  656 */           mem.setByte(0L, ((Number)arg).byteValue());
/*  657 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  658 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 2:
/*      */         case 18:
/*  662 */           mem = new Memory(2L);
/*  663 */           mem.setShort(0L, ((Number)arg).shortValue());
/*  664 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  665 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 3:
/*      */         case 19:
/*      */         case 22:
/*      */         case 23:
/*  671 */           mem = new Memory(4L);
/*  672 */           mem.setInt(0L, ((Number)arg).intValue());
/*  673 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  674 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 10:
/*  677 */           mem = new Memory(4L);
/*  678 */           mem.setInt(0L, ((Number)arg).intValue());
/*  679 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  680 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 4:
/*  683 */           mem = new Memory(4L);
/*  684 */           mem.setFloat(0L, ((Number)arg).floatValue());
/*  685 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  686 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 5:
/*  689 */           mem = new Memory(8L);
/*  690 */           mem.setDouble(0L, ((Number)arg).doubleValue());
/*  691 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  692 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 7:
/*  695 */           mem = new Memory(8L);
/*  696 */           mem.setDouble(0L, ((OaIdl.DATE)arg).date);
/*  697 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  698 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 8:
/*  701 */           if (arg instanceof String) {
/*  702 */             WTypes.BSTR bstr = OleAuto.INSTANCE.SysAllocString((String)arg);
/*  703 */             hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, bstr.getPointer());
/*  704 */             OleAuto.INSTANCE.SysFreeString(bstr);
/*  705 */             COMUtils.checkRC(hr);
/*      */           } else {
/*  707 */             hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((WTypes.BSTR)arg).getPointer());
/*  708 */             COMUtils.checkRC(hr);
/*      */           } 
/*      */           return;
/*      */         case 12:
/*  712 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((Variant.VARIANT)arg).getPointer());
/*  713 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 13:
/*  716 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((Unknown)arg).getPointer());
/*  717 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 9:
/*  720 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((Dispatch)arg).getPointer());
/*  721 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 6:
/*  724 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((OaIdl.CURRENCY)arg).getPointer());
/*  725 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 14:
/*  728 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((OaIdl.DECIMAL)arg).getPointer());
/*  729 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */       } 
/*      */       
/*  733 */       throw new IllegalStateException("Can't parse array content - type not supported: " + getVarType().intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getElement(int... indices) {
/*      */       Object result;
/*      */       WinNT.HRESULT hr;
/*      */       Memory mem;
/*      */       PointerByReference pbr;
/*      */       WTypes.BSTR bstr;
/*      */       Variant.VARIANT holder;
/*      */       OaIdl.CURRENCY currency;
/*      */       OaIdl.DECIMAL decimal;
/*  747 */       WinDef.LONG[] paramIndices = new WinDef.LONG[indices.length];
/*  748 */       for (int i = 0; i < indices.length; i++) {
/*  749 */         paramIndices[i] = new WinDef.LONG(indices[indices.length - i - 1]);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  756 */       switch (getVarType().intValue()) {
/*      */         case 11:
/*  758 */           mem = new Memory(2L);
/*  759 */           hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem);
/*  760 */           COMUtils.checkRC(hr);
/*  761 */           result = Boolean.valueOf((mem.getShort(0L) != 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  853 */           return result;case 16: case 17: mem = new Memory(1L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Byte.valueOf(mem.getByte(0L)); return result;case 2: case 18: mem = new Memory(2L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Short.valueOf(mem.getShort(0L)); return result;case 3: case 19: case 22: case 23: mem = new Memory(4L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Integer.valueOf(mem.getInt(0L)); return result;case 10: mem = new Memory(4L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = new WinDef.SCODE(mem.getInt(0L)); return result;case 4: mem = new Memory(4L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Float.valueOf(mem.getFloat(0L)); return result;case 5: mem = new Memory(8L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Double.valueOf(mem.getDouble(0L)); return result;case 7: mem = new Memory(8L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = new OaIdl.DATE(mem.getDouble(0L)); return result;case 8: pbr = new PointerByReference(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, pbr.getPointer()); COMUtils.checkRC(hr); bstr = new WTypes.BSTR(pbr.getValue()); result = bstr.getValue(); OleAuto.INSTANCE.SysFreeString(bstr); return result;case 12: holder = new Variant.VARIANT(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, holder.getPointer()); COMUtils.checkRC(hr); result = holder; return result;case 13: pbr = new PointerByReference(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, pbr.getPointer()); COMUtils.checkRC(hr); result = new Unknown(pbr.getValue()); return result;case 9: pbr = new PointerByReference(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, pbr.getPointer()); COMUtils.checkRC(hr); result = new Dispatch(pbr.getValue()); return result;case 6: currency = new OaIdl.CURRENCY(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, currency.getPointer()); COMUtils.checkRC(hr); result = currency; return result;case 14: decimal = new OaIdl.DECIMAL(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, decimal.getPointer()); COMUtils.checkRC(hr); result = decimal; return result;
/*      */       } 
/*      */       throw new IllegalStateException("Can't parse array content - type not supported: " + getVarType().intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer ptrOfIndex(int... indices) {
/*  869 */       WinDef.LONG[] paramIndices = new WinDef.LONG[indices.length];
/*  870 */       for (int i = 0; i < indices.length; i++) {
/*  871 */         paramIndices[i] = new WinDef.LONG(indices[indices.length - i - 1]);
/*      */       }
/*  873 */       PointerByReference pbr = new PointerByReference();
/*  874 */       WinNT.HRESULT hr = OleAuto.INSTANCE.SafeArrayPtrOfIndex(this, paramIndices, pbr);
/*  875 */       COMUtils.checkRC(hr);
/*  876 */       return pbr.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void destroy() {
/*  883 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayDestroy(this);
/*  884 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {
/*  892 */       destroy();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLBound(int dimension) {
/*  905 */       int targetDimension = getDimensionCount() - dimension;
/*  906 */       WinDef.LONGByReference bound = new WinDef.LONGByReference();
/*  907 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayGetLBound(this, new WinDef.UINT(targetDimension), bound);
/*  908 */       COMUtils.checkRC(res);
/*  909 */       return bound.getValue().intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getUBound(int dimension) {
/*  922 */       int targetDimension = getDimensionCount() - dimension;
/*  923 */       WinDef.LONGByReference bound = new WinDef.LONGByReference();
/*  924 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayGetUBound(this, new WinDef.UINT(targetDimension), bound);
/*  925 */       COMUtils.checkRC(res);
/*  926 */       return bound.getValue().intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getDimensionCount() {
/*  935 */       return OleAuto.INSTANCE.SafeArrayGetDim(this).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer accessData() {
/*  944 */       PointerByReference pbr = new PointerByReference();
/*  945 */       WinNT.HRESULT hr = OleAuto.INSTANCE.SafeArrayAccessData(this, pbr);
/*  946 */       COMUtils.checkRC(hr);
/*  947 */       return pbr.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unaccessData() {
/*  955 */       WinNT.HRESULT hr = OleAuto.INSTANCE.SafeArrayUnaccessData(this);
/*  956 */       COMUtils.checkRC(hr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void lock() {
/*  964 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayLock(this);
/*  965 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unlock() {
/*  972 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayUnlock(this);
/*  973 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void redim(int cElements, int lLbound) {
/*  984 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayRedim(this, new OaIdl.SAFEARRAYBOUND(cElements, lLbound));
/*  985 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.VARTYPE getVarType() {
/*  994 */       WTypes.VARTYPEByReference resultHolder = new WTypes.VARTYPEByReference();
/*  995 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayGetVartype(this, resultHolder);
/*  996 */       COMUtils.checkRC(res);
/*  997 */       return resultHolder.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getElemsize() {
/* 1006 */       return OleAuto.INSTANCE.SafeArrayGetElemsize(this).longValue();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"pSAFEARRAY"})
/*      */   public static class SAFEARRAYByReference extends Structure implements Structure.ByReference {
/*      */     public OaIdl.SAFEARRAY.ByReference pSAFEARRAY;
/*      */     
/*      */     public SAFEARRAYByReference() {}
/*      */     
/*      */     public SAFEARRAYByReference(Pointer p) {
/* 1017 */       super(p);
/* 1018 */       read();
/*      */     }
/*      */     
/*      */     public SAFEARRAYByReference(OaIdl.SAFEARRAY.ByReference safeArray) {
/* 1022 */       this.pSAFEARRAY = safeArray;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cElements", "lLbound"})
/*      */   public static class SAFEARRAYBOUND
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.ULONG cElements;
/*      */     
/*      */     public WinDef.LONG lLbound;
/*      */     
/*      */     public static class ByReference
/*      */       extends SAFEARRAYBOUND
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public SAFEARRAYBOUND() {}
/*      */     
/*      */     public SAFEARRAYBOUND(Pointer pointer) {
/* 1042 */       super(pointer);
/* 1043 */       read();
/*      */     }
/*      */     
/*      */     public SAFEARRAYBOUND(int cElements, int lLbound) {
/* 1047 */       this.cElements = new WinDef.ULONG(cElements);
/* 1048 */       this.lLbound = new WinDef.LONG(lLbound);
/* 1049 */       write();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class CURRENCY
/*      */     extends Union
/*      */   {
/*      */     public _CURRENCY currency;
/*      */     public WinDef.LONGLONG int64;
/*      */     
/*      */     public static class ByReference
/*      */       extends CURRENCY
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CURRENCY() {}
/*      */     
/*      */     public CURRENCY(Pointer pointer) {
/* 1067 */       super(pointer);
/* 1068 */       read();
/*      */     }
/*      */     
/*      */     @FieldOrder({"Lo", "Hi"})
/*      */     public static class _CURRENCY
/*      */       extends Structure
/*      */     {
/*      */       public WinDef.ULONG Lo;
/*      */       public WinDef.LONG Hi;
/*      */       
/*      */       public _CURRENCY() {}
/*      */       
/*      */       public _CURRENCY(Pointer pointer) {
/* 1081 */         super(pointer);
/* 1082 */         read();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"wReserved", "decimal1", "Hi32", "decimal2"})
/*      */   public static class DECIMAL extends Structure { public short wReserved;
/*      */     public _DECIMAL1 decimal1;
/*      */     public NativeLong Hi32;
/*      */     public _DECIMAL2 decimal2;
/*      */     
/*      */     public static class ByReference extends DECIMAL implements Structure.ByReference {}
/*      */     
/*      */     public static class _DECIMAL1 extends Union {
/*      */       public WinDef.USHORT signscale;
/*      */       public _DECIMAL1_DECIMAL decimal1_DECIMAL;
/*      */       
/*      */       public _DECIMAL1() {
/* 1100 */         setType("signscale");
/*      */       }
/*      */       
/*      */       public _DECIMAL1(Pointer pointer) {
/* 1104 */         super(pointer);
/* 1105 */         setType("signscale");
/* 1106 */         read();
/*      */       }
/*      */       
/*      */       @FieldOrder({"scale", "sign"})
/*      */       public static class _DECIMAL1_DECIMAL
/*      */         extends Structure
/*      */       {
/*      */         public WinDef.BYTE scale;
/*      */         public WinDef.BYTE sign;
/*      */         
/*      */         public _DECIMAL1_DECIMAL() {}
/*      */         
/*      */         public _DECIMAL1_DECIMAL(Pointer pointer) {
/* 1119 */           super(pointer);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public static class _DECIMAL2 extends Union {
/*      */       public WinDef.ULONGLONG Lo64;
/*      */       public _DECIMAL2_DECIMAL decimal2_DECIMAL;
/*      */       
/*      */       public _DECIMAL2() {
/* 1129 */         setType("Lo64");
/*      */       }
/*      */       
/*      */       public _DECIMAL2(Pointer pointer) {
/* 1133 */         super(pointer);
/* 1134 */         setType("Lo64");
/* 1135 */         read();
/*      */       }
/*      */ 
/*      */       
/*      */       @FieldOrder({"Lo32", "Mid32"})
/*      */       public static class _DECIMAL2_DECIMAL
/*      */         extends Structure
/*      */       {
/*      */         public WinDef.BYTE Lo32;
/*      */         public WinDef.BYTE Mid32;
/*      */         
/*      */         public _DECIMAL2_DECIMAL() {}
/*      */         
/*      */         public _DECIMAL2_DECIMAL(Pointer pointer) {
/* 1149 */           super(pointer);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DECIMAL() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DECIMAL(Pointer pointer) {
/* 1164 */       super(pointer);
/*      */     } }
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class SYSKIND extends Structure {
/*      */     public int value;
/*      */     public static final int SYS_WIN16 = 0;
/*      */     public static final int SYS_WIN32 = 1;
/*      */     public static final int SYS_MAC = 2;
/*      */     public static final int SYS_WIN64 = 3;
/*      */     
/*      */     public static class ByReference extends SYSKIND implements Structure.ByReference {}
/*      */     
/*      */     public SYSKIND() {}
/*      */     
/*      */     public SYSKIND(int value) {
/* 1180 */       this.value = value;
/*      */     }
/*      */     
/*      */     public SYSKIND(Pointer pointer) {
/* 1184 */       super(pointer);
/* 1185 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class LIBFLAGS
/*      */     extends Structure
/*      */   {
/*      */     public int value;
/*      */     public static final int LIBFLAG_FRESTRICTED = 1;
/*      */     public static final int LIBFLAG_FCONTROL = 2;
/*      */     public static final int LIBFLAG_FHIDDEN = 4;
/*      */     public static final int LIBFLAG_FHASDISKIMAGE = 8;
/*      */     
/*      */     public static class ByReference
/*      */       extends LIBFLAGS
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public LIBFLAGS() {}
/*      */     
/*      */     public LIBFLAGS(int value) {
/* 1207 */       this.value = value;
/*      */     }
/*      */     
/*      */     public LIBFLAGS(Pointer pointer) {
/* 1211 */       super(pointer);
/* 1212 */       read();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"guid", "lcid", "syskind", "wMajorVerNum", "wMinorVerNum", "wLibFlags"})
/*      */   public static class TLIBATTR
/*      */     extends Structure {
/*      */     public Guid.GUID guid;
/*      */     public WinDef.LCID lcid;
/*      */     public OaIdl.SYSKIND syskind;
/*      */     public WinDef.WORD wMajorVerNum;
/*      */     public WinDef.WORD wMinorVerNum;
/*      */     public WinDef.WORD wLibFlags;
/*      */     
/*      */     public static class ByReference
/*      */       extends TLIBATTR implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer pointer) {
/* 1231 */         super(pointer);
/* 1232 */         read();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TLIBATTR() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TLIBATTR(Pointer pointer) {
/* 1248 */       super(pointer);
/* 1249 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class BINDPTR
/*      */     extends Union
/*      */   {
/*      */     public OaIdl.FUNCDESC lpfuncdesc;
/*      */     
/*      */     public OaIdl.VARDESC lpvardesc;
/*      */     
/*      */     public TypeComp lptcomp;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends BINDPTR
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public BINDPTR() {}
/*      */     
/*      */     public BINDPTR(OaIdl.VARDESC lpvardesc) {
/* 1272 */       this.lpvardesc = lpvardesc;
/* 1273 */       setType(OaIdl.VARDESC.class);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BINDPTR(TypeComp lptcomp) {
/* 1279 */       this.lptcomp = lptcomp;
/* 1280 */       setType(TypeComp.class);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BINDPTR(OaIdl.FUNCDESC lpfuncdesc) {
/* 1286 */       this.lpfuncdesc = lpfuncdesc;
/* 1287 */       setType(OaIdl.FUNCDESC.class);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"memid", "lprgscode", "lprgelemdescParam", "funckind", "invkind", "callconv", "cParams", "cParamsOpt", "oVft", "cScodes", "elemdescFunc", "wFuncFlags"})
/*      */   public static class FUNCDESC
/*      */     extends Structure
/*      */   {
/*      */     public OaIdl.MEMBERID memid;
/*      */     
/*      */     public OaIdl.ScodeArg.ByReference lprgscode;
/*      */     public OaIdl.ElemDescArg.ByReference lprgelemdescParam;
/*      */     public OaIdl.FUNCKIND funckind;
/*      */     public OaIdl.INVOKEKIND invkind;
/*      */     public OaIdl.CALLCONV callconv;
/*      */     public WinDef.SHORT cParams;
/*      */     public WinDef.SHORT cParamsOpt;
/*      */     public WinDef.SHORT oVft;
/*      */     public WinDef.SHORT cScodes;
/*      */     public OaIdl.ELEMDESC elemdescFunc;
/*      */     public WinDef.WORD wFuncFlags;
/*      */     
/*      */     public static class ByReference
/*      */       extends FUNCDESC
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public FUNCDESC() {}
/*      */     
/*      */     public FUNCDESC(Pointer pointer) {
/* 1317 */       super(pointer);
/* 1318 */       read();
/*      */       
/* 1320 */       if (this.cParams.shortValue() > 1) {
/* 1321 */         this.lprgelemdescParam
/* 1322 */           .elemDescArg = new OaIdl.ELEMDESC[this.cParams.shortValue()];
/* 1323 */         this.lprgelemdescParam.read();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"elemDescArg"})
/*      */   public static class ElemDescArg
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends ElemDescArg implements Structure.ByReference {}
/*      */     
/* 1334 */     public OaIdl.ELEMDESC[] elemDescArg = new OaIdl.ELEMDESC[] { new OaIdl.ELEMDESC() };
/*      */ 
/*      */     
/*      */     public ElemDescArg() {}
/*      */ 
/*      */     
/*      */     public ElemDescArg(Pointer pointer) {
/* 1341 */       super(pointer);
/* 1342 */       read();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"scodeArg"})
/*      */   public static class ScodeArg
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends ScodeArg implements Structure.ByReference {}
/*      */     
/* 1352 */     public WinDef.SCODE[] scodeArg = new WinDef.SCODE[] { new WinDef.SCODE() };
/*      */ 
/*      */     
/*      */     public ScodeArg() {}
/*      */ 
/*      */     
/*      */     public ScodeArg(Pointer pointer) {
/* 1359 */       super(pointer);
/* 1360 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"memid", "lpstrSchema", "_vardesc", "elemdescVar", "wVarFlags", "varkind"})
/*      */   public static class VARDESC
/*      */     extends Structure
/*      */   {
/*      */     public OaIdl.MEMBERID memid;
/*      */     
/*      */     public WTypes.LPOLESTR lpstrSchema;
/*      */     
/*      */     public _VARDESC _vardesc;
/*      */     
/*      */     public OaIdl.ELEMDESC elemdescVar;
/*      */     
/*      */     public WinDef.WORD wVarFlags;
/*      */     
/*      */     public OaIdl.VARKIND varkind;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends VARDESC
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public static class _VARDESC
/*      */       extends Union
/*      */     {
/*      */       public NativeLong oInst;
/*      */       
/*      */       public Variant.VARIANT.ByReference lpvarValue;
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends _VARDESC
/*      */         implements Structure.ByReference {}
/*      */ 
/*      */       
/*      */       public _VARDESC() {
/* 1401 */         setType("lpvarValue");
/* 1402 */         read();
/*      */       }
/*      */       
/*      */       public _VARDESC(Pointer pointer) {
/* 1406 */         super(pointer);
/* 1407 */         setType("lpvarValue");
/* 1408 */         read();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public _VARDESC(Variant.VARIANT.ByReference lpvarValue) {
/* 1417 */         this.lpvarValue = lpvarValue;
/* 1418 */         setType("lpvarValue");
/*      */       }
/*      */ 
/*      */       
/*      */       public _VARDESC(NativeLong oInst) {
/* 1423 */         this.oInst = oInst;
/* 1424 */         setType("oInst");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public VARDESC() {}
/*      */ 
/*      */     
/*      */     public VARDESC(Pointer pointer) {
/* 1433 */       super(pointer);
/* 1434 */       this._vardesc.setType("lpvarValue");
/* 1435 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"tdesc", "_elemdesc"})
/*      */   public static class ELEMDESC
/*      */     extends Structure
/*      */   {
/*      */     public OaIdl.TYPEDESC tdesc;
/*      */ 
/*      */     
/*      */     public _ELEMDESC _elemdesc;
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends ELEMDESC
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */     
/*      */     public static class _ELEMDESC
/*      */       extends Union
/*      */     {
/*      */       public OaIdl.IDLDESC idldesc;
/*      */       
/*      */       public OaIdl.PARAMDESC paramdesc;
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends _ELEMDESC
/*      */         implements Structure.ByReference {}
/*      */ 
/*      */       
/*      */       public _ELEMDESC() {}
/*      */ 
/*      */       
/*      */       public _ELEMDESC(Pointer pointer) {
/* 1475 */         super(pointer);
/* 1476 */         setType("paramdesc");
/* 1477 */         read();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public _ELEMDESC(OaIdl.PARAMDESC paramdesc) {
/* 1486 */         this.paramdesc = paramdesc;
/* 1487 */         setType("paramdesc");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public _ELEMDESC(OaIdl.IDLDESC idldesc) {
/* 1496 */         this.idldesc = idldesc;
/* 1497 */         setType("idldesc");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ELEMDESC() {}
/*      */ 
/*      */     
/*      */     public ELEMDESC(Pointer pointer) {
/* 1506 */       super(pointer);
/* 1507 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class FUNCKIND
/*      */     extends Structure
/*      */   {
/*      */     public static final int FUNC_VIRTUAL = 0;
/*      */     
/*      */     public static final int FUNC_PUREVIRTUAL = 1;
/*      */     
/*      */     public static final int FUNC_NONVIRTUAL = 2;
/*      */     
/*      */     public static final int FUNC_STATIC = 3;
/*      */     
/*      */     public static final int FUNC_DISPATCH = 4;
/*      */     
/*      */     public int value;
/*      */     
/*      */     public static class ByReference
/*      */       extends FUNCKIND
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public FUNCKIND() {}
/*      */     
/*      */     public FUNCKIND(int value) {
/* 1535 */       this.value = value;
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class INVOKEKIND
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends INVOKEKIND
/*      */       implements Structure.ByReference {}
/*      */     
/* 1547 */     public static final INVOKEKIND INVOKE_FUNC = new INVOKEKIND(1);
/*      */     
/* 1549 */     public static final INVOKEKIND INVOKE_PROPERTYGET = new INVOKEKIND(2);
/*      */     
/* 1551 */     public static final INVOKEKIND INVOKE_PROPERTYPUT = new INVOKEKIND(4);
/*      */     
/* 1553 */     public static final INVOKEKIND INVOKE_PROPERTYPUTREF = new INVOKEKIND(8);
/*      */     
/*      */     public int value;
/*      */ 
/*      */     
/*      */     public INVOKEKIND() {}
/*      */ 
/*      */     
/*      */     public INVOKEKIND(int value) {
/* 1562 */       this.value = value;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class CALLCONV
/*      */     extends Structure
/*      */   {
/*      */     public static final int CC_FASTCALL = 0;
/*      */     
/*      */     public static final int CC_CDECL = 1;
/*      */     
/*      */     public static final int CC_MSCPASCAL = 2;
/*      */     
/*      */     public static final int CC_PASCAL = 2;
/*      */     
/*      */     public static final int CC_MACPASCAL = 3;
/*      */     
/*      */     public static final int CC_STDCALL = 4;
/*      */     
/*      */     public static final int CC_FPFASTCALL = 5;
/*      */     
/*      */     public static final int CC_SYSCALL = 6;
/*      */     
/*      */     public static final int CC_MPWCDECL = 7;
/*      */     
/*      */     public static final int CC_MPWPASCAL = 8;
/*      */     
/*      */     public static final int CC_MAX = 9;
/*      */     
/*      */     public int value;
/*      */     
/*      */     public static class ByReference
/*      */       extends CALLCONV
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CALLCONV() {}
/*      */     
/*      */     public CALLCONV(int value) {
/* 1602 */       this.value = value;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"value"})
/*      */   public static class VARKIND
/*      */     extends Structure
/*      */   {
/*      */     public static final int VAR_PERINSTANCE = 0;
/*      */     
/*      */     public static final int VAR_STATIC = 1;
/*      */     
/*      */     public static final int VAR_CONST = 2;
/*      */     
/*      */     public static final int VAR_DISPATCH = 3;
/*      */     
/*      */     public int value;
/*      */     
/*      */     public static class ByReference
/*      */       extends VARKIND
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public VARKIND() {}
/*      */     
/*      */     public VARKIND(int value) {
/* 1628 */       this.value = value;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"_typedesc", "vt"})
/*      */   public static class TYPEDESC
/*      */     extends Structure
/*      */   {
/*      */     public _TYPEDESC _typedesc;
/*      */     
/*      */     public WTypes.VARTYPE vt;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends TYPEDESC
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public static class _TYPEDESC
/*      */       extends Union
/*      */     {
/*      */       public OaIdl.TYPEDESC.ByReference lptdesc;
/*      */       
/*      */       public OaIdl.ARRAYDESC.ByReference lpadesc;
/*      */       
/*      */       public OaIdl.HREFTYPE hreftype;
/*      */       
/*      */       public _TYPEDESC() {
/* 1657 */         setType("hreftype");
/* 1658 */         read();
/*      */       }
/*      */       
/*      */       public _TYPEDESC(Pointer pointer) {
/* 1662 */         super(pointer);
/* 1663 */         setType("hreftype");
/* 1664 */         read();
/*      */       }
/*      */       
/*      */       public OaIdl.TYPEDESC.ByReference getLptdesc() {
/* 1668 */         setType("lptdesc");
/* 1669 */         read();
/* 1670 */         return this.lptdesc;
/*      */       }
/*      */       
/*      */       public OaIdl.ARRAYDESC.ByReference getLpadesc() {
/* 1674 */         setType("lpadesc");
/* 1675 */         read();
/* 1676 */         return this.lpadesc;
/*      */       }
/*      */       
/*      */       public OaIdl.HREFTYPE getHreftype() {
/* 1680 */         setType("hreftype");
/* 1681 */         read();
/* 1682 */         return this.hreftype;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TYPEDESC() {
/* 1690 */       read();
/*      */     }
/*      */     
/*      */     public TYPEDESC(Pointer pointer) {
/* 1694 */       super(pointer);
/* 1695 */       read();
/*      */     }
/*      */     
/*      */     public TYPEDESC(_TYPEDESC _typedesc, WTypes.VARTYPE vt) {
/* 1699 */       this._typedesc = _typedesc;
/* 1700 */       this.vt = vt;
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"dwReserved", "wIDLFlags"})
/*      */   public static class IDLDESC
/*      */     extends Structure {
/*      */     public BaseTSD.ULONG_PTR dwReserved;
/*      */     public WinDef.USHORT wIDLFlags;
/*      */     
/*      */     public static class ByReference extends IDLDESC implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(OaIdl.IDLDESC idldesc) {
/* 1714 */         super(idldesc.dwReserved, idldesc.wIDLFlags);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IDLDESC() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IDLDESC(Pointer pointer) {
/* 1727 */       super(pointer);
/* 1728 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public IDLDESC(BaseTSD.ULONG_PTR dwReserved, WinDef.USHORT wIDLFlags) {
/* 1733 */       this.dwReserved = dwReserved;
/* 1734 */       this.wIDLFlags = wIDLFlags;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"tdescElem", "cDims", "rgbounds"})
/*      */   public static class ARRAYDESC
/*      */     extends Structure
/*      */   {
/*      */     public OaIdl.TYPEDESC tdescElem;
/*      */     
/*      */     public short cDims;
/*      */     
/* 1747 */     public OaIdl.SAFEARRAYBOUND[] rgbounds = new OaIdl.SAFEARRAYBOUND[] { new OaIdl.SAFEARRAYBOUND() };
/*      */ 
/*      */     
/*      */     public ARRAYDESC() {}
/*      */ 
/*      */     
/*      */     public ARRAYDESC(Pointer pointer) {
/* 1754 */       super(pointer);
/* 1755 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ARRAYDESC(OaIdl.TYPEDESC tdescElem, short cDims, OaIdl.SAFEARRAYBOUND[] rgbounds) {
/* 1767 */       this.tdescElem = tdescElem;
/* 1768 */       this.cDims = cDims;
/* 1769 */       if (rgbounds.length != this.rgbounds.length)
/* 1770 */         throw new IllegalArgumentException("Wrong array size !"); 
/* 1771 */       this.rgbounds = rgbounds;
/*      */     }
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends ARRAYDESC
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"pparamdescex", "wParamFlags"})
/*      */   public static class PARAMDESC
/*      */     extends Structure
/*      */   {
/*      */     public Pointer pparamdescex;
/*      */     
/*      */     public WinDef.USHORT wParamFlags;
/*      */     
/*      */     public static class ByReference
/*      */       extends PARAMDESC
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public PARAMDESC() {}
/*      */     
/*      */     public PARAMDESC(Pointer pointer) {
/* 1796 */       super(pointer);
/* 1797 */       read();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"cBytes", "varDefaultValue"})
/*      */   public static class PARAMDESCEX
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.ULONG cBytes;
/*      */     public Variant.VariantArg varDefaultValue;
/*      */     
/*      */     public static class ByReference
/*      */       extends PARAMDESCEX
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public PARAMDESCEX() {}
/*      */     
/*      */     public PARAMDESCEX(Pointer pointer) {
/* 1815 */       super(pointer);
/* 1816 */       read();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class HREFTYPE
/*      */     extends WinDef.DWORD
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public HREFTYPE() {}
/*      */     
/*      */     public HREFTYPE(long value) {
/* 1828 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class HREFTYPEByReference extends WinDef.DWORDByReference {
/*      */     public HREFTYPEByReference() {
/* 1834 */       this(new OaIdl.HREFTYPE(0L));
/*      */     }
/*      */     
/*      */     public HREFTYPEByReference(WinDef.DWORD value) {
/* 1838 */       super(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.HREFTYPE value) {
/* 1842 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public OaIdl.HREFTYPE getValue() {
/* 1847 */       return new OaIdl.HREFTYPE(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"guid", "lcid", "dwReserved", "memidConstructor", "memidDestructor", "lpstrSchema", "cbSizeInstance", "typekind", "cFuncs", "cVars", "cImplTypes", "cbSizeVft", "cbAlignment", "wTypeFlags", "wMajorVerNum", "wMinorVerNum", "tdescAlias", "idldescType"})
/*      */   public static class TYPEATTR
/*      */     extends Structure {
/*      */     public Guid.GUID guid;
/*      */     public WinDef.LCID lcid;
/*      */     public WinDef.DWORD dwReserved;
/*      */     public OaIdl.MEMBERID memidConstructor;
/*      */     public OaIdl.MEMBERID memidDestructor;
/*      */     public WTypes.LPOLESTR lpstrSchema;
/*      */     public WinDef.ULONG cbSizeInstance;
/*      */     public OaIdl.TYPEKIND typekind;
/*      */     public WinDef.WORD cFuncs;
/*      */     public WinDef.WORD cVars;
/*      */     public WinDef.WORD cImplTypes;
/*      */     public WinDef.WORD cbSizeVft;
/*      */     public WinDef.WORD cbAlignment;
/*      */     public WinDef.WORD wTypeFlags;
/*      */     public WinDef.WORD wMajorVerNum;
/*      */     public WinDef.WORD wMinorVerNum;
/*      */     public OaIdl.TYPEDESC tdescAlias;
/*      */     public OaIdl.IDLDESC idldescType;
/*      */     public static final int TYPEFLAGS_FAPPOBJECT = 1;
/*      */     public static final int TYPEFLAGS_FCANCREATE = 2;
/*      */     public static final int TYPEFLAGS_FLICENSED = 4;
/*      */     public static final int TYPEFLAGS_FPREDECLID = 8;
/*      */     public static final int TYPEFLAGS_FHIDDEN = 16;
/*      */     public static final int TYPEFLAGS_FCONTROL = 32;
/*      */     public static final int TYPEFLAGS_FDUAL = 64;
/*      */     public static final int TYPEFLAGS_FNONEXTENSIBLE = 128;
/*      */     public static final int TYPEFLAGS_FOLEAUTOMATION = 256;
/*      */     public static final int TYPEFLAGS_FRESTRICTED = 512;
/*      */     public static final int TYPEFLAGS_FAGGREGATABLE = 1024;
/*      */     public static final int TYPEFLAGS_FREPLACEABLE = 2048;
/*      */     public static final int TYPEFLAGS_FDISPATCHABLE = 4096;
/*      */     public static final int TYPEFLAGS_FREVERSEBIND = 8192;
/*      */     public static final int TYPEFLAGS_FPROXY = 16384;
/*      */     
/*      */     public static class ByReference
/*      */       extends TYPEATTR implements Structure.ByReference {}
/*      */     
/*      */     public TYPEATTR() {}
/*      */     
/*      */     public TYPEATTR(Pointer pointer) {
/* 1894 */       super(pointer);
/* 1895 */       read();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\OaIdl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */