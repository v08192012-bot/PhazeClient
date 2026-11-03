/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OaIdlUtil;
/*     */ import com.sun.jna.platform.win32.Ole32;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WTypes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Wbemcli
/*     */ {
/*     */   public static final int WBEM_FLAG_RETURN_WBEM_COMPLETE = 0;
/*     */   public static final int WBEM_FLAG_RETURN_IMMEDIATELY = 16;
/*     */   public static final int WBEM_FLAG_FORWARD_ONLY = 32;
/*     */   public static final int WBEM_FLAG_NO_ERROR_OBJECT = 64;
/*     */   public static final int WBEM_FLAG_SEND_STATUS = 128;
/*     */   public static final int WBEM_FLAG_ENSURE_LOCATABLE = 256;
/*     */   public static final int WBEM_FLAG_DIRECT_READ = 512;
/*     */   public static final int WBEM_MASK_RESERVED_FLAGS = 126976;
/*     */   public static final int WBEM_FLAG_USE_AMENDED_QUALIFIERS = 131072;
/*     */   public static final int WBEM_FLAG_STRONG_VALIDATION = 1048576;
/*     */   public static final int WBEM_INFINITE = -1;
/*     */   public static final int WBEM_S_NO_ERROR = 0;
/*     */   public static final int WBEM_S_FALSE = 1;
/*     */   public static final int WBEM_S_TIMEDOUT = 262148;
/*     */   public static final int WBEM_S_NO_MORE_DATA = 262149;
/*     */   public static final int WBEM_E_INVALID_NAMESPACE = -2147217394;
/*     */   public static final int WBEM_E_INVALID_CLASS = -2147217392;
/*     */   public static final int WBEM_E_INVALID_QUERY = -2147217385;
/*     */   public static final int CIM_ILLEGAL = 4095;
/*     */   public static final int CIM_EMPTY = 0;
/*     */   public static final int CIM_SINT8 = 16;
/*     */   public static final int CIM_UINT8 = 17;
/*     */   public static final int CIM_SINT16 = 2;
/*     */   public static final int CIM_UINT16 = 18;
/*     */   public static final int CIM_SINT32 = 3;
/*     */   public static final int CIM_UINT32 = 19;
/*     */   public static final int CIM_SINT64 = 20;
/*     */   public static final int CIM_UINT64 = 21;
/*     */   public static final int CIM_REAL32 = 4;
/*     */   public static final int CIM_REAL64 = 5;
/*     */   public static final int CIM_BOOLEAN = 11;
/*     */   public static final int CIM_STRING = 8;
/*     */   public static final int CIM_DATETIME = 101;
/*     */   public static final int CIM_REFERENCE = 102;
/*     */   public static final int CIM_CHAR16 = 103;
/*     */   public static final int CIM_OBJECT = 13;
/*     */   public static final int CIM_FLAG_ARRAY = 8192;
/*     */   
/*     */   public static interface WBEM_CONDITION_FLAG_TYPE
/*     */   {
/*     */     public static final int WBEM_FLAG_ALWAYS = 0;
/*     */     public static final int WBEM_FLAG_ONLY_IF_TRUE = 1;
/*     */     public static final int WBEM_FLAG_ONLY_IF_FALSE = 2;
/*     */     public static final int WBEM_FLAG_ONLY_IF_IDENTICAL = 3;
/*     */     public static final int WBEM_MASK_PRIMARY_CONDITION = 3;
/*     */     public static final int WBEM_FLAG_KEYS_ONLY = 4;
/*     */     public static final int WBEM_FLAG_REFS_ONLY = 8;
/*     */     public static final int WBEM_FLAG_LOCAL_ONLY = 16;
/*     */     public static final int WBEM_FLAG_PROPAGATED_ONLY = 32;
/*     */     public static final int WBEM_FLAG_SYSTEM_ONLY = 48;
/*     */     public static final int WBEM_FLAG_NONSYSTEM_ONLY = 64;
/*     */     public static final int WBEM_MASK_CONDITION_ORIGIN = 112;
/*     */     public static final int WBEM_FLAG_CLASS_OVERRIDES_ONLY = 256;
/*     */     public static final int WBEM_FLAG_CLASS_LOCAL_AND_OVERRIDES = 512;
/*     */     public static final int WBEM_MASK_CLASS_CONDITION = 768;
/*     */   }
/*     */   
/*     */   public static class IWbemClassObject
/*     */     extends Unknown
/*     */   {
/*     */     public IWbemClassObject() {}
/*     */     
/*     */     public IWbemClassObject(Pointer pvInstance) {
/* 125 */       super(pvInstance);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT Get(WString wszName, int lFlags, Variant.VARIANT.ByReference pVal, IntByReference pType, IntByReference plFlavor) {
/* 131 */       return (WinNT.HRESULT)_invokeNativeObject(4, new Object[] {
/* 132 */             getPointer(), wszName, Integer.valueOf(lFlags), pVal, pType, plFlavor }, WinNT.HRESULT.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT Get(String wszName, int lFlags, Variant.VARIANT.ByReference pVal, IntByReference pType, IntByReference plFlavor) {
/* 137 */       return Get((wszName == null) ? null : new WString(wszName), lFlags, pVal, pType, plFlavor);
/*     */     }
/*     */     
/*     */     public WinNT.HRESULT GetMethod(String wszName, int lFlags, PointerByReference ppInSignature, PointerByReference ppOutSignature) {
/* 141 */       return GetMethod((wszName == null) ? null : new WString(wszName), lFlags, ppInSignature, ppOutSignature);
/*     */     }
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT GetMethod(WString wszName, int lFlags, PointerByReference ppInSignature, PointerByReference ppOutSignature) {
/* 146 */       return (WinNT.HRESULT)_invokeNativeObject(19, new Object[] {
/* 147 */             getPointer(), wszName, Integer.valueOf(lFlags), ppInSignature, ppOutSignature }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public IWbemClassObject GetMethod(String wszName) {
/* 151 */       PointerByReference ppInSignature = new PointerByReference();
/* 152 */       WinNT.HRESULT res = GetMethod(wszName, 0, ppInSignature, (PointerByReference)null);
/* 153 */       COMUtils.checkRC(res);
/* 154 */       return new IWbemClassObject(ppInSignature.getValue());
/*     */     }
/*     */     
/*     */     public WinNT.HRESULT GetNames(String wszQualifierName, int lFlags, Variant.VARIANT.ByReference pQualifierVal, PointerByReference pNames) {
/* 158 */       return GetNames((wszQualifierName == null) ? null : new WString(wszQualifierName), lFlags, pQualifierVal, pNames);
/*     */     }
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT GetNames(WString wszQualifierName, int lFlags, Variant.VARIANT.ByReference pQualifierVal, PointerByReference pNames) {
/* 163 */       return (WinNT.HRESULT)_invokeNativeObject(7, new Object[] {
/* 164 */             getPointer(), wszQualifierName, Integer.valueOf(lFlags), pQualifierVal, pNames }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public String[] GetNames(String wszQualifierName, int lFlags, Variant.VARIANT.ByReference pQualifierVal) {
/* 168 */       PointerByReference pbr = new PointerByReference();
/* 169 */       COMUtils.checkRC(GetNames(wszQualifierName, lFlags, pQualifierVal, pbr));
/* 170 */       Object[] nameObjects = (Object[])OaIdlUtil.toPrimitiveArray(new OaIdl.SAFEARRAY(pbr.getValue()), true);
/* 171 */       String[] names = new String[nameObjects.length];
/* 172 */       for (int i = 0; i < nameObjects.length; i++) {
/* 173 */         names[i] = (String)nameObjects[i];
/*     */       }
/* 175 */       return names;
/*     */     }
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT GetQualifierSet(PointerByReference ppQualSet) {
/* 180 */       return (WinNT.HRESULT)_invokeNativeObject(3, new Object[] {
/* 181 */             getPointer(), ppQualSet }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public Wbemcli.IWbemQualifierSet GetQualifierSet() {
/* 185 */       PointerByReference ppQualSet = new PointerByReference();
/* 186 */       WinNT.HRESULT hr = GetQualifierSet(ppQualSet);
/* 187 */       COMUtils.checkRC(hr);
/* 188 */       Wbemcli.IWbemQualifierSet qualifier = new Wbemcli.IWbemQualifierSet(ppQualSet.getValue());
/*     */       
/* 190 */       return qualifier;
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
/*     */     public WinNT.HRESULT GetPropertyQualifierSet(WString wszProperty, PointerByReference ppQualSet) {
/* 202 */       return (WinNT.HRESULT)_invokeNativeObject(11, new Object[] {
/* 203 */             getPointer(), wszProperty, ppQualSet }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public Wbemcli.IWbemQualifierSet GetPropertyQualifierSet(String strProperty) {
/* 207 */       WString wszProperty = new WString(strProperty);
/* 208 */       PointerByReference ppQualSet = new PointerByReference();
/*     */       
/* 210 */       COMUtils.checkRC(GetPropertyQualifierSet(wszProperty, ppQualSet));
/* 211 */       Wbemcli.IWbemQualifierSet qualifier = new Wbemcli.IWbemQualifierSet(ppQualSet.getValue());
/* 212 */       return qualifier;
/*     */     }
/*     */     
/*     */     public WinNT.HRESULT Put(String wszName, int lFlags, Variant.VARIANT pVal, int Type) {
/* 216 */       return Put((wszName == null) ? null : new WString(wszName), lFlags, pVal, Type);
/*     */     }
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT Put(WString wszName, int lFlags, Variant.VARIANT pVal, int Type) {
/* 221 */       return (WinNT.HRESULT)_invokeNativeObject(5, new Object[] {
/* 222 */             getPointer(), wszName, Integer.valueOf(lFlags), pVal, Integer.valueOf(Type) }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public void Put(String wszName, String pValue) {
/* 226 */       Variant.VARIANT aVariant = new Variant.VARIANT();
/* 227 */       WTypes.BSTR strValue = OleAuto.INSTANCE.SysAllocString(pValue);
/*     */       try {
/* 229 */         aVariant.setValue(8, strValue);
/* 230 */         WinNT.HRESULT res = Put(wszName, 0, aVariant, 0);
/* 231 */         COMUtils.checkRC(res);
/*     */       } finally {
/*     */         
/* 234 */         OleAuto.INSTANCE.VariantClear(aVariant);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT SpawnInstance(int lFlags, PointerByReference ppNewInstance) {
/* 240 */       return (WinNT.HRESULT)_invokeNativeObject(15, new Object[] {
/* 241 */             getPointer(), Integer.valueOf(lFlags), ppNewInstance }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public IWbemClassObject SpawnInstance() {
/* 245 */       PointerByReference ppNewInstance = new PointerByReference();
/* 246 */       WinNT.HRESULT res = SpawnInstance(0, ppNewInstance);
/* 247 */       COMUtils.checkRC(res);
/* 248 */       return new IWbemClassObject(ppNewInstance.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IWbemQualifierSet extends Unknown {
/*     */     public IWbemQualifierSet(Pointer pvInstance) {
/* 254 */       super(pvInstance);
/*     */     }
/*     */     
/*     */     public WinNT.HRESULT Get(WString wszName, int lFlags, Variant.VARIANT.ByReference pVal, IntByReference plFlavor) {
/* 258 */       return (WinNT.HRESULT)_invokeNativeObject(3, new Object[] {
/* 259 */             getPointer(), wszName, Integer.valueOf(lFlags), pVal, plFlavor }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public String Get(String wszName) {
/* 263 */       WString wszNameStr = new WString(wszName);
/* 264 */       Variant.VARIANT.ByReference pQualifierVal = new Variant.VARIANT.ByReference();
/* 265 */       WinNT.HRESULT hres = Get(wszNameStr, 0, pQualifierVal, (IntByReference)null);
/* 266 */       if (hres.intValue() == -2147217406)
/*     */       {
/* 268 */         return null;
/*     */       }
/* 270 */       int qualifierInt = pQualifierVal.getVarType().intValue();
/* 271 */       switch (qualifierInt) {
/*     */         case 11:
/* 273 */           return String.valueOf(pQualifierVal.booleanValue());
/*     */         case 8:
/* 275 */           return pQualifierVal.stringValue();
/*     */       } 
/* 277 */       return null;
/*     */     }
/*     */     
/*     */     public WinNT.HRESULT GetNames(int lFlags, PointerByReference pNames) {
/* 281 */       return (WinNT.HRESULT)_invokeNativeObject(6, new Object[] {
/* 282 */             getPointer(), Integer.valueOf(lFlags), pNames }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public String[] GetNames() {
/* 286 */       PointerByReference pbr = new PointerByReference();
/* 287 */       COMUtils.checkRC(GetNames(0, pbr));
/* 288 */       Object[] nameObjects = (Object[])OaIdlUtil.toPrimitiveArray(new OaIdl.SAFEARRAY(pbr.getValue()), true);
/* 289 */       String[] qualifierNames = new String[nameObjects.length];
/* 290 */       for (int i = 0; i < nameObjects.length; i++) {
/* 291 */         qualifierNames[i] = (String)nameObjects[i];
/*     */       }
/* 293 */       return qualifierNames;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IEnumWbemClassObject
/*     */     extends Unknown
/*     */   {
/*     */     public IEnumWbemClassObject() {}
/*     */ 
/*     */     
/*     */     public IEnumWbemClassObject(Pointer pvInstance) {
/* 306 */       super(pvInstance);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT Next(int lTimeOut, int uCount, Pointer[] ppObjects, IntByReference puReturned) {
/* 312 */       return (WinNT.HRESULT)_invokeNativeObject(4, new Object[] {
/* 313 */             getPointer(), Integer.valueOf(lTimeOut), Integer.valueOf(uCount), ppObjects, puReturned }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public Wbemcli.IWbemClassObject[] Next(int lTimeOut, int uCount) {
/* 317 */       Pointer[] resultArray = new Pointer[uCount];
/* 318 */       IntByReference resultCount = new IntByReference();
/* 319 */       WinNT.HRESULT result = Next(lTimeOut, uCount, resultArray, resultCount);
/* 320 */       COMUtils.checkRC(result);
/* 321 */       Wbemcli.IWbemClassObject[] returnValue = new Wbemcli.IWbemClassObject[resultCount.getValue()];
/* 322 */       for (int i = 0; i < resultCount.getValue(); i++) {
/* 323 */         returnValue[i] = new Wbemcli.IWbemClassObject(resultArray[i]);
/*     */       }
/* 325 */       return returnValue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IWbemLocator
/*     */     extends Unknown
/*     */   {
/* 335 */     public static final Guid.CLSID CLSID_WbemLocator = new Guid.CLSID("4590f811-1d3a-11d0-891f-00aa004b2e24");
/* 336 */     public static final Guid.GUID IID_IWbemLocator = new Guid.GUID("dc12a687-737f-11cf-884d-00aa004b2e24");
/*     */ 
/*     */     
/*     */     public IWbemLocator() {}
/*     */     
/*     */     private IWbemLocator(Pointer pvInstance) {
/* 342 */       super(pvInstance);
/*     */     }
/*     */     
/*     */     public static IWbemLocator create() {
/* 346 */       PointerByReference pbr = new PointerByReference();
/*     */       
/* 348 */       WinNT.HRESULT hres = Ole32.INSTANCE.CoCreateInstance((Guid.GUID)CLSID_WbemLocator, null, 1, IID_IWbemLocator, pbr);
/*     */       
/* 350 */       if (COMUtils.FAILED(hres)) {
/* 351 */         return null;
/*     */       }
/*     */       
/* 354 */       return new IWbemLocator(pbr.getValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT ConnectServer(WTypes.BSTR strNetworkResource, WTypes.BSTR strUser, WTypes.BSTR strPassword, WTypes.BSTR strLocale, int lSecurityFlags, WTypes.BSTR strAuthority, Wbemcli.IWbemContext pCtx, PointerByReference ppNamespace) {
/* 360 */       return (WinNT.HRESULT)_invokeNativeObject(3, new Object[] { getPointer(), strNetworkResource, strUser, strPassword, strLocale, 
/* 361 */             Integer.valueOf(lSecurityFlags), strAuthority, pCtx, ppNamespace }, WinNT.HRESULT.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public Wbemcli.IWbemServices ConnectServer(String strNetworkResource, String strUser, String strPassword, String strLocale, int lSecurityFlags, String strAuthority, Wbemcli.IWbemContext pCtx) {
/* 366 */       WTypes.BSTR strNetworkResourceBSTR = OleAuto.INSTANCE.SysAllocString(strNetworkResource);
/* 367 */       WTypes.BSTR strUserBSTR = OleAuto.INSTANCE.SysAllocString(strUser);
/* 368 */       WTypes.BSTR strPasswordBSTR = OleAuto.INSTANCE.SysAllocString(strPassword);
/* 369 */       WTypes.BSTR strLocaleBSTR = OleAuto.INSTANCE.SysAllocString(strLocale);
/* 370 */       WTypes.BSTR strAuthorityBSTR = OleAuto.INSTANCE.SysAllocString(strAuthority);
/*     */       
/* 372 */       PointerByReference pbr = new PointerByReference();
/*     */       
/*     */       try {
/* 375 */         WinNT.HRESULT result = ConnectServer(strNetworkResourceBSTR, strUserBSTR, strPasswordBSTR, strLocaleBSTR, lSecurityFlags, strAuthorityBSTR, pCtx, pbr);
/*     */ 
/*     */         
/* 378 */         COMUtils.checkRC(result);
/*     */         
/* 380 */         return new Wbemcli.IWbemServices(pbr.getValue());
/*     */       } finally {
/* 382 */         OleAuto.INSTANCE.SysFreeString(strNetworkResourceBSTR);
/* 383 */         OleAuto.INSTANCE.SysFreeString(strUserBSTR);
/* 384 */         OleAuto.INSTANCE.SysFreeString(strPasswordBSTR);
/* 385 */         OleAuto.INSTANCE.SysFreeString(strLocaleBSTR);
/* 386 */         OleAuto.INSTANCE.SysFreeString(strAuthorityBSTR);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IWbemServices
/*     */     extends Unknown
/*     */   {
/*     */     public IWbemServices() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public IWbemServices(Pointer pvInstance) {
/* 401 */       super(pvInstance);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT ExecMethod(WTypes.BSTR strObjectPath, WTypes.BSTR strMethodName, int lFlags, Wbemcli.IWbemContext pCtx, Pointer pInParams, PointerByReference ppOutParams, PointerByReference ppCallResult) {
/* 407 */       return (WinNT.HRESULT)_invokeNativeObject(24, new Object[] {
/* 408 */             getPointer(), strObjectPath, strMethodName, Integer.valueOf(lFlags), pCtx, pInParams, ppOutParams, ppCallResult }, WinNT.HRESULT.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public Wbemcli.IWbemClassObject ExecMethod(String strObjectPath, String strMethodName, int lFlags, Wbemcli.IWbemContext pCtx, Wbemcli.IWbemClassObject inParams) {
/* 413 */       WTypes.BSTR strObjectPathBSTR = OleAuto.INSTANCE.SysAllocString(strObjectPath);
/* 414 */       WTypes.BSTR strMethodNameBSTR = OleAuto.INSTANCE.SysAllocString(strMethodName);
/*     */       try {
/* 416 */         PointerByReference ppOutParams = new PointerByReference();
/*     */         
/* 418 */         WinNT.HRESULT res = ExecMethod(strObjectPathBSTR, strMethodNameBSTR, lFlags, pCtx, inParams.getPointer(), ppOutParams, (PointerByReference)null);
/*     */         
/* 420 */         COMUtils.checkRC(res);
/*     */         
/* 422 */         return new Wbemcli.IWbemClassObject(ppOutParams.getValue());
/*     */       } finally {
/* 424 */         OleAuto.INSTANCE.SysFreeString(strObjectPathBSTR);
/* 425 */         OleAuto.INSTANCE.SysFreeString(strMethodNameBSTR);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT ExecQuery(WTypes.BSTR strQueryLanguage, WTypes.BSTR strQuery, int lFlags, Wbemcli.IWbemContext pCtx, PointerByReference ppEnum) {
/* 432 */       return (WinNT.HRESULT)_invokeNativeObject(20, new Object[] {
/* 433 */             getPointer(), strQueryLanguage, strQuery, Integer.valueOf(lFlags), pCtx, ppEnum }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public Wbemcli.IEnumWbemClassObject ExecQuery(String strQueryLanguage, String strQuery, int lFlags, Wbemcli.IWbemContext pCtx) {
/* 437 */       WTypes.BSTR strQueryLanguageBSTR = OleAuto.INSTANCE.SysAllocString(strQueryLanguage);
/* 438 */       WTypes.BSTR strQueryBSTR = OleAuto.INSTANCE.SysAllocString(strQuery);
/*     */       try {
/* 440 */         PointerByReference pbr = new PointerByReference();
/*     */         
/* 442 */         WinNT.HRESULT res = ExecQuery(strQueryLanguageBSTR, strQueryBSTR, lFlags, pCtx, pbr);
/*     */         
/* 444 */         COMUtils.checkRC(res);
/*     */         
/* 446 */         return new Wbemcli.IEnumWbemClassObject(pbr.getValue());
/*     */       } finally {
/* 448 */         OleAuto.INSTANCE.SysFreeString(strQueryLanguageBSTR);
/* 449 */         OleAuto.INSTANCE.SysFreeString(strQueryBSTR);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WinNT.HRESULT GetObject(WTypes.BSTR strObjectPath, int lFlags, Wbemcli.IWbemContext pCtx, PointerByReference ppObject, PointerByReference ppCallResult) {
/* 456 */       return (WinNT.HRESULT)_invokeNativeObject(6, new Object[] {
/* 457 */             getPointer(), strObjectPath, Integer.valueOf(lFlags), pCtx, ppObject, ppCallResult }, WinNT.HRESULT.class);
/*     */     }
/*     */     
/*     */     public Wbemcli.IWbemClassObject GetObject(String strObjectPath, int lFlags, Wbemcli.IWbemContext pCtx) {
/* 461 */       WTypes.BSTR strObjectPathBSTR = OleAuto.INSTANCE.SysAllocString(strObjectPath);
/*     */       try {
/* 463 */         PointerByReference ppObject = new PointerByReference();
/* 464 */         WinNT.HRESULT res = GetObject(strObjectPathBSTR, lFlags, pCtx, ppObject, (PointerByReference)null);
/* 465 */         COMUtils.checkRC(res);
/* 466 */         return new Wbemcli.IWbemClassObject(ppObject.getValue());
/*     */       } finally {
/* 468 */         OleAuto.INSTANCE.SysFreeString(strObjectPathBSTR);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IWbemContext
/*     */     extends Unknown
/*     */   {
/* 478 */     public static final Guid.CLSID CLSID_WbemContext = new Guid.CLSID("674B6698-EE92-11D0-AD71-00C04FD8FDFF");
/* 479 */     public static final Guid.GUID IID_IWbemContext = new Guid.GUID("44aca674-e8fc-11d0-a07c-00c04fb68820");
/*     */ 
/*     */     
/*     */     public IWbemContext() {}
/*     */     
/*     */     public static IWbemContext create() {
/* 485 */       PointerByReference pbr = new PointerByReference();
/*     */       
/* 487 */       WinNT.HRESULT hres = Ole32.INSTANCE.CoCreateInstance((Guid.GUID)CLSID_WbemContext, null, 1, IID_IWbemContext, pbr);
/*     */       
/* 489 */       if (COMUtils.FAILED(hres)) {
/* 490 */         return null;
/*     */       }
/*     */       
/* 493 */       return new IWbemContext(pbr.getValue());
/*     */     }
/*     */     
/*     */     public IWbemContext(Pointer pvInstance) {
/* 497 */       super(pvInstance);
/*     */     }
/*     */     
/*     */     public void SetValue(String wszName, int lFlag, Variant.VARIANT pValue) {
/* 501 */       WTypes.BSTR wszNameBSTR = OleAuto.INSTANCE.SysAllocString(wszName);
/*     */       
/*     */       try {
/* 504 */         WinNT.HRESULT res = (WinNT.HRESULT)_invokeNativeObject(8, new Object[] {
/* 505 */               getPointer(), wszNameBSTR, Integer.valueOf(lFlag), pValue }, WinNT.HRESULT.class);
/* 506 */         COMUtils.checkRC(res);
/*     */       } finally {
/* 508 */         OleAuto.INSTANCE.SysFreeString(wszNameBSTR);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void SetValue(String wszName, int lFlag, boolean pValue) {
/* 513 */       Variant.VARIANT aVariant = new Variant.VARIANT();
/* 514 */       aVariant.setValue(11, pValue ? Variant.VARIANT_TRUE : Variant.VARIANT_FALSE);
/* 515 */       SetValue(wszName, lFlag, aVariant);
/* 516 */       OleAuto.INSTANCE.VariantClear(aVariant);
/*     */     }
/*     */     
/*     */     public void SetValue(String wszName, int lFlag, String pValue) {
/* 520 */       Variant.VARIANT aVariant = new Variant.VARIANT();
/* 521 */       WTypes.BSTR strValue = OleAuto.INSTANCE.SysAllocString(pValue);
/*     */       try {
/* 523 */         aVariant.setValue(30, strValue);
/* 524 */         SetValue(wszName, lFlag, aVariant);
/*     */       } finally {
/*     */         
/* 527 */         OleAuto.INSTANCE.SysFreeString(strValue);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\COM\Wbemcli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */