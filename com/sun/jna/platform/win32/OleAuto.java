/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Structure.FieldOrder;
/*     */ import com.sun.jna.ptr.DoubleByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import com.sun.jna.win32.StdCallLibrary;
/*     */ import com.sun.jna.win32.W32APIOptions;
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
/*     */ public interface OleAuto
/*     */   extends StdCallLibrary
/*     */ {
/*  61 */   public static final OleAuto INSTANCE = (OleAuto)Native.load("OleAut32", OleAuto.class, W32APIOptions.DEFAULT_OPTIONS);
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
/*     */   public static final int DISPATCH_METHOD = 1;
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
/*     */   public static final int DISPATCH_PROPERTYGET = 2;
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
/*     */   public static final int DISPATCH_PROPERTYPUT = 4;
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
/*     */   public static final int DISPATCH_PROPERTYPUTREF = 8;
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
/*     */   public static final int FADF_AUTO = 1;
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
/*     */   public static final int FADF_STATIC = 2;
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
/*     */   public static final int FADF_EMBEDDED = 4;
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
/*     */   public static final int FADF_FIXEDSIZE = 16;
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
/*     */   public static final int FADF_RECORD = 32;
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
/*     */   public static final int FADF_HAVEIID = 64;
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
/*     */   public static final int FADF_HAVEVARTYPE = 128;
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
/*     */   public static final int FADF_BSTR = 256;
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
/*     */   public static final int FADF_UNKNOWN = 512;
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
/*     */   public static final int FADF_DISPATCH = 1024;
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
/*     */   public static final int FADF_VARIANT = 2048;
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
/*     */   public static final int FADF_RESERVED = 61448;
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
/*     */   public static final short VARIANT_NOVALUEPROP = 1;
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
/*     */   public static final short VARIANT_ALPHABOOL = 2;
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
/*     */   public static final short VARIANT_NOUSEROVERRIDE = 4;
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
/*     */   public static final short VARIANT_CALENDAR_HIJRI = 8;
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
/*     */   public static final short VARIANT_LOCALBOOL = 16;
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
/*     */   public static final short VARIANT_CALENDAR_THAI = 32;
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
/*     */   public static final short VARIANT_CALENDAR_GREGORIAN = 64;
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
/*     */   public static final short VARIANT_USE_NLS = 128;
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
/*     */   WTypes.BSTR SysAllocString(String paramString);
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
/*     */   void SysFreeString(WTypes.BSTR paramBSTR);
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
/*     */   int SysStringByteLen(WTypes.BSTR paramBSTR);
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
/*     */   int SysStringLen(WTypes.BSTR paramBSTR);
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
/*     */   void VariantInit(Variant.VARIANT.ByReference paramByReference);
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
/*     */   void VariantInit(Variant.VARIANT paramVARIANT);
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
/*     */   WinNT.HRESULT VariantCopy(Pointer paramPointer, Variant.VARIANT paramVARIANT);
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
/*     */   WinNT.HRESULT VariantClear(Variant.VARIANT paramVARIANT);
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
/*     */   WinNT.HRESULT VariantChangeType(Variant.VARIANT paramVARIANT1, Variant.VARIANT paramVARIANT2, short paramShort, WTypes.VARTYPE paramVARTYPE);
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
/*     */   WinNT.HRESULT VariantChangeType(Variant.VARIANT.ByReference paramByReference1, Variant.VARIANT.ByReference paramByReference2, short paramShort, WTypes.VARTYPE paramVARTYPE);
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
/*     */   OaIdl.SAFEARRAY.ByReference SafeArrayCreate(WTypes.VARTYPE paramVARTYPE, WinDef.UINT paramUINT, OaIdl.SAFEARRAYBOUND[] paramArrayOfSAFEARRAYBOUND);
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
/*     */   WinNT.HRESULT SafeArrayPutElement(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.LONG[] paramArrayOfLONG, Pointer paramPointer);
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
/*     */   WinNT.HRESULT SafeArrayGetUBound(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.UINT paramUINT, WinDef.LONGByReference paramLONGByReference);
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
/*     */   WinNT.HRESULT SafeArrayGetLBound(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.UINT paramUINT, WinDef.LONGByReference paramLONGByReference);
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
/*     */   WinNT.HRESULT SafeArrayGetElement(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.LONG[] paramArrayOfLONG, Pointer paramPointer);
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
/*     */   WinNT.HRESULT SafeArrayPtrOfIndex(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.LONG[] paramArrayOfLONG, PointerByReference paramPointerByReference);
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
/*     */   WinNT.HRESULT SafeArrayLock(OaIdl.SAFEARRAY paramSAFEARRAY);
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
/*     */   WinNT.HRESULT SafeArrayUnlock(OaIdl.SAFEARRAY paramSAFEARRAY);
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
/*     */   WinNT.HRESULT SafeArrayDestroy(OaIdl.SAFEARRAY paramSAFEARRAY);
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
/*     */   WinNT.HRESULT SafeArrayRedim(OaIdl.SAFEARRAY paramSAFEARRAY, OaIdl.SAFEARRAYBOUND paramSAFEARRAYBOUND);
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
/*     */   WinNT.HRESULT SafeArrayGetVartype(OaIdl.SAFEARRAY paramSAFEARRAY, WTypes.VARTYPEByReference paramVARTYPEByReference);
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
/*     */   WinDef.UINT SafeArrayGetDim(OaIdl.SAFEARRAY paramSAFEARRAY);
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
/*     */   WinNT.HRESULT SafeArrayAccessData(OaIdl.SAFEARRAY paramSAFEARRAY, PointerByReference paramPointerByReference);
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
/*     */   WinNT.HRESULT SafeArrayUnaccessData(OaIdl.SAFEARRAY paramSAFEARRAY);
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
/*     */   WinDef.UINT SafeArrayGetElemsize(OaIdl.SAFEARRAY paramSAFEARRAY);
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
/*     */   WinNT.HRESULT GetActiveObject(Guid.GUID paramGUID, WinDef.PVOID paramPVOID, PointerByReference paramPointerByReference);
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
/*     */   WinNT.HRESULT LoadRegTypeLib(Guid.GUID paramGUID, int paramInt1, int paramInt2, WinDef.LCID paramLCID, PointerByReference paramPointerByReference);
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
/*     */   WinNT.HRESULT LoadTypeLib(String paramString, PointerByReference paramPointerByReference);
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
/*     */   int SystemTimeToVariantTime(WinBase.SYSTEMTIME paramSYSTEMTIME, DoubleByReference paramDoubleByReference);
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
/*     */   int VariantTimeToSystemTime(double paramDouble, WinBase.SYSTEMTIME paramSYSTEMTIME);
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
/*     */   @FieldOrder({"rgvarg", "rgdispidNamedArgs", "cArgs", "cNamedArgs"})
/*     */   public static class DISPPARAMS
/*     */     extends Structure
/*     */   {
/*     */     public Variant.VariantArg.ByReference rgvarg;
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
/*     */     public static class ByReference
/*     */       extends DISPPARAMS
/*     */       implements Structure.ByReference {}
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
/* 777 */     public Pointer rgdispidNamedArgs = Pointer.NULL;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 782 */     public WinDef.UINT cArgs = new WinDef.UINT(0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 788 */     public WinDef.UINT cNamedArgs = new WinDef.UINT(0L);
/*     */     
/*     */     public OaIdl.DISPID[] getRgdispidNamedArgs() {
/* 791 */       OaIdl.DISPID[] namedArgs = null;
/* 792 */       int count = this.cNamedArgs.intValue();
/* 793 */       if (this.rgdispidNamedArgs != null && count > 0) {
/* 794 */         int[] rawData = this.rgdispidNamedArgs.getIntArray(0L, count);
/* 795 */         namedArgs = new OaIdl.DISPID[count];
/* 796 */         for (int i = 0; i < count; i++) {
/* 797 */           namedArgs[i] = new OaIdl.DISPID(rawData[i]);
/*     */         }
/*     */       } else {
/* 800 */         namedArgs = new OaIdl.DISPID[0];
/*     */       } 
/* 802 */       return namedArgs;
/*     */     }
/*     */     
/*     */     public void setRgdispidNamedArgs(OaIdl.DISPID[] namedArgs) {
/* 806 */       if (namedArgs == null) {
/* 807 */         namedArgs = new OaIdl.DISPID[0];
/*     */       }
/* 809 */       this.cNamedArgs = new WinDef.UINT(namedArgs.length);
/* 810 */       this.rgdispidNamedArgs = (Pointer)new Memory((OaIdl.DISPID.SIZE * namedArgs.length));
/* 811 */       int[] rawData = new int[namedArgs.length];
/* 812 */       for (int i = 0; i < rawData.length; i++) {
/* 813 */         rawData[i] = namedArgs[i].intValue();
/*     */       }
/* 815 */       this.rgdispidNamedArgs.write(0L, rawData, 0, namedArgs.length);
/*     */     }
/*     */     
/*     */     public Variant.VARIANT[] getArgs() {
/* 819 */       if (this.rgvarg != null) {
/* 820 */         this.rgvarg.setArraySize(this.cArgs.intValue());
/* 821 */         return this.rgvarg.variantArg;
/*     */       } 
/* 823 */       return new Variant.VARIANT[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public void setArgs(Variant.VARIANT[] arguments) {
/* 828 */       if (arguments == null) {
/* 829 */         arguments = new Variant.VARIANT[0];
/*     */       }
/*     */       
/* 832 */       this.rgvarg = new Variant.VariantArg.ByReference(arguments);
/* 833 */       this.cArgs = new WinDef.UINT(arguments.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DISPPARAMS() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DISPPARAMS(Pointer memory) {
/* 849 */       super(memory);
/* 850 */       read();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\OleAuto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */