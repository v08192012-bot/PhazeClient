/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.Kernel32;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.Ole32;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COMBindingBaseObject
/*     */   extends COMInvoker
/*     */ {
/*  55 */   public static final WinDef.LCID LOCALE_USER_DEFAULT = Kernel32.INSTANCE
/*  56 */     .GetUserDefaultLCID();
/*     */ 
/*     */   
/*  59 */   public static final WinDef.LCID LOCALE_SYSTEM_DEFAULT = Kernel32.INSTANCE
/*  60 */     .GetSystemDefaultLCID();
/*     */ 
/*     */   
/*     */   private IUnknown iUnknown;
/*     */ 
/*     */   
/*     */   private IDispatch iDispatch;
/*     */ 
/*     */   
/*  69 */   private PointerByReference pDispatch = new PointerByReference();
/*     */ 
/*     */   
/*  72 */   private PointerByReference pUnknown = new PointerByReference();
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(IDispatch dispatch) {
/*  76 */     this.iDispatch = dispatch;
/*     */   }
/*     */   
/*     */   public COMBindingBaseObject(Guid.CLSID clsid, boolean useActiveInstance) {
/*  80 */     this(clsid, useActiveInstance, 21);
/*     */   }
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(Guid.CLSID clsid, boolean useActiveInstance, int dwClsContext) {
/*  85 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*  87 */     init(useActiveInstance, (Guid.GUID)clsid, dwClsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(String progId, boolean useActiveInstance, int dwClsContext) throws COMException {
/*  92 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*  94 */     Guid.CLSID.ByReference clsid = new Guid.CLSID.ByReference();
/*  95 */     WinNT.HRESULT hr = Ole32.INSTANCE.CLSIDFromProgID(progId, clsid);
/*     */     
/*  97 */     COMUtils.checkRC(hr);
/*     */     
/*  99 */     init(useActiveInstance, (Guid.GUID)clsid, dwClsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(String progId, boolean useActiveInstance) throws COMException {
/* 104 */     this(progId, useActiveInstance, 21);
/*     */   }
/*     */   
/*     */   private void init(boolean useActiveInstance, Guid.GUID clsid, int dwClsContext) throws COMException {
/*     */     WinNT.HRESULT hr;
/* 109 */     if (useActiveInstance) {
/* 110 */       hr = OleAuto.INSTANCE.GetActiveObject(clsid, null, this.pUnknown);
/*     */       
/* 112 */       if (COMUtils.SUCCEEDED(hr)) {
/* 113 */         this.iUnknown = new Unknown(this.pUnknown.getValue());
/* 114 */         hr = this.iUnknown.QueryInterface(new Guid.REFIID(IDispatch.IID_IDISPATCH), this.pDispatch);
/*     */       } else {
/*     */         
/* 117 */         hr = Ole32.INSTANCE.CoCreateInstance(clsid, null, dwClsContext, (Guid.GUID)IDispatch.IID_IDISPATCH, this.pDispatch);
/*     */       } 
/*     */     } else {
/*     */       
/* 121 */       hr = Ole32.INSTANCE.CoCreateInstance(clsid, null, dwClsContext, (Guid.GUID)IDispatch.IID_IDISPATCH, this.pDispatch);
/*     */     } 
/*     */ 
/*     */     
/* 125 */     COMUtils.checkRC(hr);
/*     */     
/* 127 */     this.iDispatch = new Dispatch(this.pDispatch.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDispatch getIDispatch() {
/* 136 */     return this.iDispatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointerByReference getIDispatchPointer() {
/* 145 */     return this.pDispatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IUnknown getIUnknown() {
/* 154 */     return this.iUnknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointerByReference getIUnknownPointer() {
/* 163 */     return this.pUnknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 170 */     if (this.iDispatch != null) {
/* 171 */       this.iDispatch.Release();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, String name, Variant.VARIANT[] pArgs) throws COMException {
/* 179 */     WString[] ptName = { new WString(name) };
/* 180 */     OaIdl.DISPIDByReference pdispID = new OaIdl.DISPIDByReference();
/*     */ 
/*     */     
/* 183 */     WinNT.HRESULT hr = this.iDispatch.GetIDsOfNames(new Guid.REFIID(Guid.IID_NULL), ptName, 1, LOCALE_USER_DEFAULT, pdispID);
/*     */ 
/*     */     
/* 186 */     COMUtils.checkRC(hr);
/*     */     
/* 188 */     return oleMethod(nType, pvResult, pdispID.getValue(), pArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, OaIdl.DISPID dispId, Variant.VARIANT[] pArgs) throws COMException {
/* 196 */     int finalNType, _argsLen = 0;
/* 197 */     Variant.VARIANT[] _args = null;
/* 198 */     OleAuto.DISPPARAMS.ByReference dp = new OleAuto.DISPPARAMS.ByReference();
/* 199 */     OaIdl.EXCEPINFO.ByReference pExcepInfo = new OaIdl.EXCEPINFO.ByReference();
/* 200 */     IntByReference puArgErr = new IntByReference();
/*     */ 
/*     */     
/* 203 */     if (pArgs != null && pArgs.length > 0) {
/* 204 */       _argsLen = pArgs.length;
/* 205 */       _args = new Variant.VARIANT[_argsLen];
/*     */       
/* 207 */       int revCount = _argsLen;
/* 208 */       for (int i = 0; i < _argsLen; i++) {
/* 209 */         _args[i] = pArgs[--revCount];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 214 */     if (nType == 4) {
/* 215 */       dp.setRgdispidNamedArgs(new OaIdl.DISPID[] { OaIdl.DISPID_PROPERTYPUT });
/*     */     }
/*     */ 
/*     */     
/* 219 */     if (_argsLen > 0) {
/* 220 */       dp.setArgs(_args);
/*     */ 
/*     */       
/* 223 */       dp.write();
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
/* 251 */     if (nType == 1 || nType == 2) {
/* 252 */       finalNType = 3;
/*     */     } else {
/* 254 */       finalNType = nType;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     WinNT.HRESULT hr = this.iDispatch.Invoke(dispId, new Guid.REFIID(Guid.IID_NULL), LOCALE_SYSTEM_DEFAULT, new WinDef.WORD(finalNType), dp, pvResult, pExcepInfo, puArgErr);
/*     */ 
/*     */     
/* 261 */     COMUtils.checkRC(hr, (OaIdl.EXCEPINFO)pExcepInfo, puArgErr);
/* 262 */     return hr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, String name, Variant.VARIANT pArg) throws COMException {
/* 283 */     return oleMethod(nType, pvResult, name, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, OaIdl.DISPID dispId, Variant.VARIANT pArg) throws COMException {
/* 289 */     return oleMethod(nType, pvResult, dispId, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, String name) throws COMException {
/* 308 */     return oleMethod(nType, pvResult, name, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, OaIdl.DISPID dispId) throws COMException {
/* 314 */     return oleMethod(nType, pvResult, dispId, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name, Variant.VARIANT[] pArgs) throws COMException {
/* 324 */     if (pDisp == null) {
/* 325 */       throw new COMException("pDisp (IDispatch) parameter is null!");
/*     */     }
/*     */     
/* 328 */     WString[] ptName = { new WString(name) };
/* 329 */     OaIdl.DISPIDByReference pdispID = new OaIdl.DISPIDByReference();
/*     */ 
/*     */     
/* 332 */     WinNT.HRESULT hr = pDisp.GetIDsOfNames(new Guid.REFIID(Guid.IID_NULL), ptName, 1, LOCALE_USER_DEFAULT, pdispID);
/*     */ 
/*     */     
/* 335 */     COMUtils.checkRC(hr);
/*     */     
/* 337 */     return 
/* 338 */       oleMethod(nType, pvResult, pDisp, pdispID.getValue(), pArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId, Variant.VARIANT[] pArgs) throws COMException {
/*     */     int finalNType;
/* 349 */     if (pDisp == null) {
/* 350 */       throw new COMException("pDisp (IDispatch) parameter is null!");
/*     */     }
/*     */     
/* 353 */     int _argsLen = 0;
/* 354 */     Variant.VARIANT[] _args = null;
/* 355 */     OleAuto.DISPPARAMS.ByReference dp = new OleAuto.DISPPARAMS.ByReference();
/* 356 */     OaIdl.EXCEPINFO.ByReference pExcepInfo = new OaIdl.EXCEPINFO.ByReference();
/* 357 */     IntByReference puArgErr = new IntByReference();
/*     */ 
/*     */     
/* 360 */     if (pArgs != null && pArgs.length > 0) {
/* 361 */       _argsLen = pArgs.length;
/* 362 */       _args = new Variant.VARIANT[_argsLen];
/*     */       
/* 364 */       int revCount = _argsLen;
/* 365 */       for (int i = 0; i < _argsLen; i++) {
/* 366 */         _args[i] = pArgs[--revCount];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 371 */     if (nType == 4) {
/* 372 */       dp.setRgdispidNamedArgs(new OaIdl.DISPID[] { OaIdl.DISPID_PROPERTYPUT });
/*     */     }
/*     */ 
/*     */     
/* 376 */     if (_argsLen > 0) {
/* 377 */       dp.setArgs(_args);
/*     */ 
/*     */       
/* 380 */       dp.write();
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
/* 408 */     if (nType == 1 || nType == 2) {
/* 409 */       finalNType = 3;
/*     */     } else {
/* 411 */       finalNType = nType;
/*     */     } 
/*     */ 
/*     */     
/* 415 */     WinNT.HRESULT hr = pDisp.Invoke(dispId, new Guid.REFIID(Guid.IID_NULL), LOCALE_SYSTEM_DEFAULT, new WinDef.WORD(finalNType), dp, pvResult, pExcepInfo, puArgErr);
/*     */ 
/*     */     
/* 418 */     COMUtils.checkRC(hr, (OaIdl.EXCEPINFO)pExcepInfo, puArgErr);
/* 419 */     return hr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name, Variant.VARIANT pArg) throws COMException {
/* 429 */     return oleMethod(nType, pvResult, pDisp, name, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId, Variant.VARIANT pArg) throws COMException {
/* 440 */     return oleMethod(nType, pvResult, pDisp, dispId, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name) throws COMException {
/* 451 */     return oleMethod(nType, pvResult, pDisp, name, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId) throws COMException {
/* 461 */     return oleMethod(nType, pvResult, pDisp, dispId, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void checkFailed(WinNT.HRESULT hr) {
/* 473 */     COMUtils.checkRC(hr);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\COM\COMBindingBaseObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */