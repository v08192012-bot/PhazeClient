/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.platform.win32.COM.Dispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatch;
/*     */ import com.sun.jna.platform.win32.COM.IUnknown;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WTypes;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Convert
/*     */ {
/*     */   public static Variant.VARIANT toVariant(Object value) {
/*  92 */     if (value instanceof Variant.VARIANT)
/*  93 */       return (Variant.VARIANT)value; 
/*  94 */     if (value instanceof Byte)
/*  95 */       return new Variant.VARIANT(((Byte)value).byteValue()); 
/*  96 */     if (value instanceof Character)
/*  97 */       return new Variant.VARIANT(((Character)value).charValue()); 
/*  98 */     if (value instanceof Short)
/*  99 */       return new Variant.VARIANT(((Short)value).shortValue()); 
/* 100 */     if (value instanceof Integer)
/* 101 */       return new Variant.VARIANT(((Integer)value).intValue()); 
/* 102 */     if (value instanceof Long)
/* 103 */       return new Variant.VARIANT(((Long)value).longValue()); 
/* 104 */     if (value instanceof Float)
/* 105 */       return new Variant.VARIANT(((Float)value).floatValue()); 
/* 106 */     if (value instanceof Double)
/* 107 */       return new Variant.VARIANT(((Double)value).doubleValue()); 
/* 108 */     if (value instanceof String)
/* 109 */       return new Variant.VARIANT((String)value); 
/* 110 */     if (value instanceof Boolean)
/* 111 */       return new Variant.VARIANT(((Boolean)value).booleanValue()); 
/* 112 */     if (value instanceof Dispatch)
/* 113 */       return new Variant.VARIANT((Dispatch)value); 
/* 114 */     if (value instanceof Date)
/* 115 */       return new Variant.VARIANT((Date)value); 
/* 116 */     if (value instanceof Proxy) {
/* 117 */       InvocationHandler ih = Proxy.getInvocationHandler(value);
/* 118 */       ProxyObject pobj = (ProxyObject)ih;
/* 119 */       return new Variant.VARIANT(pobj.getRawDispatch());
/* 120 */     }  if (value instanceof IComEnum) {
/* 121 */       IComEnum enm = (IComEnum)value;
/* 122 */       return new Variant.VARIANT(new WinDef.LONG(enm.getValue()));
/*     */     } 
/* 124 */     Constructor<Variant.VARIANT> constructor = null;
/* 125 */     if (value != null) {
/* 126 */       for (Constructor<Variant.VARIANT> m : Variant.VARIANT.class.getConstructors()) {
/* 127 */         Class<?>[] parameters = m.getParameterTypes();
/* 128 */         if (parameters.length == 1 && parameters[0]
/* 129 */           .isAssignableFrom(value.getClass())) {
/* 130 */           constructor = m;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 135 */     if (constructor != null) {
/*     */       try {
/* 137 */         return constructor.newInstance(new Object[] { value });
/* 138 */       } catch (Exception ex) {
/* 139 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */   
/*     */   public static Object toJavaObject(Variant.VARIANT value, Class<?> targetClass, ObjectFactory factory, boolean addReference, boolean freeValue) {
/*     */     Object result;
/* 147 */     int varType = (value != null) ? value.getVarType().intValue() : 1;
/*     */     
/* 149 */     if (varType == 0 || varType == 1) {
/* 150 */       return null;
/*     */     }
/*     */     
/* 153 */     if (targetClass != null && !targetClass.isAssignableFrom(Object.class)) {
/* 154 */       if (targetClass.isAssignableFrom(value.getClass())) {
/* 155 */         return value;
/*     */       }
/*     */       
/* 158 */       Object vobj = value.getValue();
/* 159 */       if (vobj != null && targetClass.isAssignableFrom(vobj.getClass())) {
/* 160 */         return vobj;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     Variant.VARIANT inputValue = value;
/*     */     
/* 166 */     if (varType == 16396) {
/* 167 */       value = (Variant.VARIANT)value.getValue();
/* 168 */       varType = value.getVarType().intValue();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (targetClass == null || targetClass.isAssignableFrom(Object.class)) {
/*     */       
/* 175 */       targetClass = null;
/*     */       
/* 177 */       switch (varType) {
/*     */         case 16:
/*     */         case 17:
/* 180 */           targetClass = Byte.class;
/*     */           break;
/*     */         case 2:
/* 183 */           targetClass = Short.class;
/*     */           break;
/*     */         case 18:
/* 186 */           targetClass = Character.class;
/*     */           break;
/*     */         case 3:
/*     */         case 19:
/*     */         case 22:
/*     */         case 23:
/* 192 */           targetClass = Integer.class;
/*     */           break;
/*     */         case 20:
/*     */         case 21:
/* 196 */           targetClass = Long.class;
/*     */           break;
/*     */         case 4:
/* 199 */           targetClass = Float.class;
/*     */           break;
/*     */         case 5:
/* 202 */           targetClass = Double.class;
/*     */           break;
/*     */         case 11:
/* 205 */           targetClass = Boolean.class;
/*     */           break;
/*     */         case 10:
/* 208 */           targetClass = WinDef.SCODE.class;
/*     */           break;
/*     */         case 6:
/* 211 */           targetClass = OaIdl.CURRENCY.class;
/*     */           break;
/*     */         case 7:
/* 214 */           targetClass = Date.class;
/*     */           break;
/*     */         case 8:
/* 217 */           targetClass = String.class;
/*     */           break;
/*     */         case 13:
/* 220 */           targetClass = IUnknown.class;
/*     */           break;
/*     */         case 9:
/* 223 */           targetClass = IDispatch.class;
/*     */           break;
/*     */         case 16396:
/* 226 */           targetClass = Variant.class;
/*     */           break;
/*     */         case 16384:
/* 229 */           targetClass = WinDef.PVOID.class;
/*     */           break;
/*     */         case 16398:
/* 232 */           targetClass = OaIdl.DECIMAL.class;
/*     */           break;
/*     */         
/*     */         default:
/* 236 */           if ((varType & 0x2000) > 0) {
/* 237 */             targetClass = OaIdl.SAFEARRAY.class;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 243 */     if (Byte.class.equals(targetClass) || byte.class.equals(targetClass)) {
/* 244 */       result = Byte.valueOf(value.byteValue());
/* 245 */     } else if (Short.class.equals(targetClass) || short.class.equals(targetClass)) {
/* 246 */       result = Short.valueOf(value.shortValue());
/* 247 */     } else if (Character.class.equals(targetClass) || char.class.equals(targetClass)) {
/* 248 */       result = Character.valueOf((char)value.intValue());
/* 249 */     } else if (Integer.class.equals(targetClass) || int.class.equals(targetClass)) {
/* 250 */       result = Integer.valueOf(value.intValue());
/* 251 */     } else if (Long.class.equals(targetClass) || long.class.equals(targetClass) || IComEnum.class.isAssignableFrom(targetClass)) {
/* 252 */       result = Long.valueOf(value.longValue());
/* 253 */     } else if (Float.class.equals(targetClass) || float.class.equals(targetClass)) {
/* 254 */       result = Float.valueOf(value.floatValue());
/* 255 */     } else if (Double.class.equals(targetClass) || double.class.equals(targetClass)) {
/* 256 */       result = Double.valueOf(value.doubleValue());
/* 257 */     } else if (Boolean.class.equals(targetClass) || boolean.class.equals(targetClass)) {
/* 258 */       result = Boolean.valueOf(value.booleanValue());
/* 259 */     } else if (Date.class.equals(targetClass)) {
/* 260 */       result = value.dateValue();
/* 261 */     } else if (String.class.equals(targetClass)) {
/* 262 */       result = value.stringValue();
/*     */     } else {
/* 264 */       result = value.getValue();
/* 265 */       if (result instanceof Dispatch) {
/* 266 */         Dispatch d = (Dispatch)result;
/* 267 */         if (targetClass != null && targetClass.isInterface()) {
/* 268 */           Object proxy = factory.createProxy(targetClass, (IDispatch)d);
/*     */ 
/*     */           
/* 271 */           if (!addReference) {
/* 272 */             int i = d.Release();
/*     */           }
/* 274 */           result = proxy;
/*     */         } else {
/* 276 */           result = d;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     if (IComEnum.class.isAssignableFrom(targetClass)) {
/* 282 */       result = targetClass.cast(toComEnum(targetClass, result));
/*     */     }
/*     */     
/* 285 */     if (freeValue) {
/* 286 */       free(inputValue, result);
/*     */     }
/*     */     
/* 289 */     return result;
/*     */   }
/*     */   
/*     */   public static <T extends IComEnum> T toComEnum(Class<T> enumType, Object value) {
/*     */     try {
/* 294 */       Method m = enumType.getMethod("values", new Class[0]);
/* 295 */       IComEnum[] arrayOfIComEnum = (IComEnum[])m.invoke(null, new Object[0]);
/* 296 */       for (IComEnum iComEnum : arrayOfIComEnum) {
/* 297 */         if (value.equals(Long.valueOf(iComEnum.getValue()))) {
/* 298 */           return (T)iComEnum;
/*     */         }
/*     */       } 
/* 301 */     } catch (NoSuchMethodException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException noSuchMethodException) {}
/*     */     
/* 303 */     return null;
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
/*     */   public static void free(Variant.VARIANT variant, Class<?> javaType) {
/* 317 */     if ((javaType == null || !WTypes.BSTR.class.isAssignableFrom(javaType)) && variant != null && variant
/*     */       
/* 319 */       .getVarType().intValue() == 8) {
/* 320 */       Object value = variant.getValue();
/* 321 */       if (value instanceof WTypes.BSTR) {
/* 322 */         OleAuto.INSTANCE.SysFreeString((WTypes.BSTR)value);
/*     */       }
/*     */     } 
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
/*     */   public static void free(Variant.VARIANT variant, Object value) {
/* 338 */     free(variant, (value == null) ? null : value.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\CO\\util\Convert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */