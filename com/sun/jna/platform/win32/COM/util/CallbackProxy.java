/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.COM.COMException;
/*     */ import com.sun.jna.platform.win32.COM.COMUtils;
/*     */ import com.sun.jna.platform.win32.COM.Dispatch;
/*     */ import com.sun.jna.platform.win32.COM.DispatchListener;
/*     */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
/*     */ import com.sun.jna.platform.win32.COM.Unknown;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComEventCallback;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinError;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CallbackProxy
/*     */   implements IDispatchCallback
/*     */ {
/*     */   private static boolean DEFAULT_BOOLEAN;
/*     */   private static byte DEFAULT_BYTE;
/*     */   private static short DEFAULT_SHORT;
/*     */   private static int DEFAULT_INT;
/*     */   private static long DEFAULT_LONG;
/*     */   private static float DEFAULT_FLOAT;
/*     */   private static double DEFAULT_DOUBLE;
/*     */   ObjectFactory factory;
/*     */   Class<?> comEventCallbackInterface;
/*     */   IComEventCallbackListener comEventCallbackListener;
/*     */   Guid.REFIID listenedToRiid;
/*     */   public DispatchListener dispatchListener;
/*     */   Map<OaIdl.DISPID, Method> dsipIdMap;
/*     */   
/*     */   public CallbackProxy(ObjectFactory factory, Class<?> comEventCallbackInterface, IComEventCallbackListener comEventCallbackListener) {
/*  74 */     this.factory = factory;
/*  75 */     this.comEventCallbackInterface = comEventCallbackInterface;
/*  76 */     this.comEventCallbackListener = comEventCallbackListener;
/*  77 */     this.listenedToRiid = createRIID(comEventCallbackInterface);
/*  78 */     this.dsipIdMap = createDispIdMap(comEventCallbackInterface);
/*  79 */     this.dispatchListener = new DispatchListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Guid.REFIID createRIID(Class<?> comEventCallbackInterface) {
/*  90 */     ComInterface comInterfaceAnnotation = comEventCallbackInterface.<ComInterface>getAnnotation(ComInterface.class);
/*  91 */     if (null == comInterfaceAnnotation) {
/*  92 */       throw new COMException("advise: Interface must define a value for either iid via the ComInterface annotation");
/*     */     }
/*     */     
/*  95 */     String iidStr = comInterfaceAnnotation.iid();
/*  96 */     if (null == iidStr || iidStr.isEmpty()) {
/*  97 */       throw new COMException("ComInterface must define a value for iid");
/*     */     }
/*  99 */     return new Guid.REFIID((new Guid.IID(iidStr)).getPointer());
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<OaIdl.DISPID, Method> createDispIdMap(Class<?> comEventCallbackInterface) {
/* 104 */     Map<OaIdl.DISPID, Method> map = new HashMap<>();
/*     */     
/* 106 */     for (Method meth : comEventCallbackInterface.getMethods()) {
/* 107 */       ComEventCallback callbackAnnotation = meth.<ComEventCallback>getAnnotation(ComEventCallback.class);
/* 108 */       ComMethod methodAnnotation = meth.<ComMethod>getAnnotation(ComMethod.class);
/* 109 */       if (methodAnnotation != null) {
/* 110 */         int dispId = methodAnnotation.dispId();
/* 111 */         if (-1 == dispId) {
/* 112 */           dispId = fetchDispIdFromName(callbackAnnotation);
/*     */         }
/* 114 */         if (dispId == -1) {
/* 115 */           this.comEventCallbackListener.errorReceivingCallbackEvent("DISPID for " + meth
/* 116 */               .getName() + " not found", null);
/*     */         }
/*     */         
/* 119 */         map.put(new OaIdl.DISPID(dispId), meth);
/* 120 */       } else if (null != callbackAnnotation) {
/* 121 */         int dispId = callbackAnnotation.dispid();
/* 122 */         if (-1 == dispId) {
/* 123 */           dispId = fetchDispIdFromName(callbackAnnotation);
/*     */         }
/* 125 */         if (dispId == -1) {
/* 126 */           this.comEventCallbackListener.errorReceivingCallbackEvent("DISPID for " + meth
/* 127 */               .getName() + " not found", null);
/*     */         }
/*     */         
/* 130 */         map.put(new OaIdl.DISPID(dispId), meth);
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private int fetchDispIdFromName(ComEventCallback annotation) {
/* 139 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void invokeOnThread(OaIdl.DISPID dispIdMember, Guid.REFIID riid, WinDef.LCID lcid, WinDef.WORD wFlags, OleAuto.DISPPARAMS.ByReference pDispParams) {
/* 145 */     Variant.VARIANT[] arguments = pDispParams.getArgs();
/*     */     
/* 147 */     Method eventMethod = this.dsipIdMap.get(dispIdMember);
/* 148 */     if (eventMethod == null) {
/* 149 */       this.comEventCallbackListener.errorReceivingCallbackEvent("No method found with dispId = " + dispIdMember, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
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
/* 187 */     OaIdl.DISPID[] positionMap = pDispParams.getRgdispidNamedArgs();
/*     */     
/* 189 */     Class<?>[] paramTypes = eventMethod.getParameterTypes();
/* 190 */     Object[] params = new Object[paramTypes.length];
/*     */     
/*     */     int i;
/* 193 */     for (i = 0; i < params.length && arguments.length - positionMap.length - i > 0; i++) {
/* 194 */       Class<?> targetClass = paramTypes[i];
/* 195 */       Variant.VARIANT varg = arguments[arguments.length - i - 1];
/* 196 */       params[i] = Convert.toJavaObject(varg, targetClass, this.factory, true, false);
/*     */     } 
/*     */     
/* 199 */     for (i = 0; i < positionMap.length; i++) {
/* 200 */       int targetPosition = positionMap[i].intValue();
/* 201 */       if (targetPosition < params.length) {
/*     */ 
/*     */ 
/*     */         
/* 205 */         Class<?> targetClass = paramTypes[targetPosition];
/* 206 */         Variant.VARIANT varg = arguments[i];
/* 207 */         params[targetPosition] = Convert.toJavaObject(varg, targetClass, this.factory, true, false);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 213 */     for (i = 0; i < params.length; i++) {
/* 214 */       if (params[i] == null && paramTypes[i].isPrimitive()) {
/* 215 */         if (paramTypes[i].equals(boolean.class)) {
/* 216 */           params[i] = Boolean.valueOf(DEFAULT_BOOLEAN);
/* 217 */         } else if (paramTypes[i].equals(byte.class)) {
/* 218 */           params[i] = Byte.valueOf(DEFAULT_BYTE);
/* 219 */         } else if (paramTypes[i].equals(short.class)) {
/* 220 */           params[i] = Short.valueOf(DEFAULT_SHORT);
/* 221 */         } else if (paramTypes[i].equals(int.class)) {
/* 222 */           params[i] = Integer.valueOf(DEFAULT_INT);
/* 223 */         } else if (paramTypes[i].equals(long.class)) {
/* 224 */           params[i] = Long.valueOf(DEFAULT_LONG);
/* 225 */         } else if (paramTypes[i].equals(float.class)) {
/* 226 */           params[i] = Float.valueOf(DEFAULT_FLOAT);
/* 227 */         } else if (paramTypes[i].equals(double.class)) {
/* 228 */           params[i] = Double.valueOf(DEFAULT_DOUBLE);
/*     */         } else {
/* 230 */           throw new IllegalArgumentException("Class type " + paramTypes[i].getName() + " not mapped to primitive default value.");
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 236 */       eventMethod.invoke(this.comEventCallbackListener, params);
/* 237 */     } catch (Exception e) {
/* 238 */       List<String> decodedClassNames = new ArrayList<>(params.length);
/* 239 */       for (Object o : params) {
/* 240 */         if (o == null) {
/* 241 */           decodedClassNames.add("NULL");
/*     */         } else {
/* 243 */           decodedClassNames.add(o.getClass().getName());
/*     */         } 
/*     */       } 
/* 246 */       this.comEventCallbackListener.errorReceivingCallbackEvent("Exception invoking method " + eventMethod + " supplied: " + decodedClassNames
/* 247 */           .toString(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Pointer getPointer() {
/* 253 */     return this.dispatchListener.getPointer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT GetTypeInfoCount(WinDef.UINTByReference pctinfo) {
/* 259 */     return new WinNT.HRESULT(-2147467263);
/*     */   }
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT GetTypeInfo(WinDef.UINT iTInfo, WinDef.LCID lcid, PointerByReference ppTInfo) {
/* 264 */     return new WinNT.HRESULT(-2147467263);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT GetIDsOfNames(Guid.REFIID riid, WString[] rgszNames, int cNames, WinDef.LCID lcid, OaIdl.DISPIDByReference rgDispId) {
/* 270 */     return new WinNT.HRESULT(-2147467263);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT Invoke(OaIdl.DISPID dispIdMember, Guid.REFIID riid, WinDef.LCID lcid, WinDef.WORD wFlags, OleAuto.DISPPARAMS.ByReference pDispParams, Variant.VARIANT.ByReference pVarResult, OaIdl.EXCEPINFO.ByReference pExcepInfo, IntByReference puArgErr) {
/* 278 */     assert COMUtils.comIsInitialized() : "Assumption about COM threading broken.";
/*     */     
/* 280 */     invokeOnThread(dispIdMember, riid, lcid, wFlags, pDispParams);
/*     */     
/* 282 */     return WinError.S_OK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT QueryInterface(Guid.REFIID refid, PointerByReference ppvObject) {
/* 288 */     if (null == ppvObject)
/* 289 */       return new WinNT.HRESULT(-2147467261); 
/* 290 */     if (refid.equals(this.listenedToRiid)) {
/* 291 */       ppvObject.setValue(getPointer());
/* 292 */       return WinError.S_OK;
/* 293 */     }  if (refid.getValue().equals(Unknown.IID_IUNKNOWN)) {
/* 294 */       ppvObject.setValue(getPointer());
/* 295 */       return WinError.S_OK;
/* 296 */     }  if (refid.getValue().equals(Dispatch.IID_IDISPATCH)) {
/* 297 */       ppvObject.setValue(getPointer());
/* 298 */       return WinError.S_OK;
/*     */     } 
/*     */     
/* 301 */     return new WinNT.HRESULT(-2147467262);
/*     */   }
/*     */ 
/*     */   
/*     */   public int AddRef() {
/* 306 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int Release() {
/* 311 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\CO\\util\CallbackProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */