/*     */ package com.sun.jna.internal;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionUtils
/*     */ {
/*  45 */   private static final Logger LOG = Logger.getLogger(ReflectionUtils.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Constructor getConstructorLookupClass() {
/*  59 */     if (CONSTRUCTOR_LOOKUP_CLASS == null) {
/*  60 */       Class lookup = lookupClass("java.lang.invoke.MethodHandles$Lookup");
/*  61 */       CONSTRUCTOR_LOOKUP_CLASS = lookupDeclaredConstructor(lookup, new Class[] { Class.class });
/*     */     } 
/*  63 */     return CONSTRUCTOR_LOOKUP_CLASS;
/*     */   }
/*     */   
/*     */   static {
/*  67 */     Class methodHandles = lookupClass("java.lang.invoke.MethodHandles");
/*  68 */     Class methodHandle = lookupClass("java.lang.invoke.MethodHandle");
/*  69 */     Class lookup = lookupClass("java.lang.invoke.MethodHandles$Lookup");
/*  70 */     Class methodType = lookupClass("java.lang.invoke.MethodType");
/*     */   }
/*  72 */   private static final Method METHOD_IS_DEFAULT = lookupMethod(Method.class, "isDefault", new Class[0]); private static final Method METHOD_HANDLES_LOOKUP; private static final Method METHOD_HANDLES_LOOKUP_IN; private static final Method METHOD_HANDLES_PRIVATE_LOOKUP_IN; private static final Method METHOD_HANDLES_LOOKUP_UNREFLECT_SPECIAL; static {
/*  73 */     METHOD_HANDLES_LOOKUP = lookupMethod(methodHandles, "lookup", new Class[0]);
/*  74 */     METHOD_HANDLES_LOOKUP_IN = lookupMethod(lookup, "in", new Class[] { Class.class });
/*  75 */     METHOD_HANDLES_LOOKUP_UNREFLECT_SPECIAL = lookupMethod(lookup, "unreflectSpecial", new Class[] { Method.class, Class.class });
/*  76 */     METHOD_HANDLES_LOOKUP_FIND_SPECIAL = lookupMethod(lookup, "findSpecial", new Class[] { Class.class, String.class, methodType, Class.class });
/*  77 */     METHOD_HANDLES_BIND_TO = lookupMethod(methodHandle, "bindTo", new Class[] { Object.class });
/*  78 */     METHOD_HANDLES_INVOKE_WITH_ARGUMENTS = lookupMethod(methodHandle, "invokeWithArguments", new Class[] { Object[].class });
/*  79 */     METHOD_HANDLES_PRIVATE_LOOKUP_IN = lookupMethod(methodHandles, "privateLookupIn", new Class[] { Class.class, lookup });
/*  80 */     METHOD_TYPE = lookupMethod(methodType, "methodType", new Class[] { Class.class, Class[].class });
/*     */   }
/*     */   private static final Method METHOD_HANDLES_LOOKUP_FIND_SPECIAL; private static final Method METHOD_HANDLES_BIND_TO; private static final Method METHOD_HANDLES_INVOKE_WITH_ARGUMENTS; private static final Method METHOD_TYPE; private static Constructor CONSTRUCTOR_LOOKUP_CLASS;
/*     */   private static Constructor lookupDeclaredConstructor(Class clazz, Class... arguments) {
/*  84 */     if (clazz == null) {
/*  85 */       LOG.log(Level.FINE, "Failed to lookup method: <init>#{1}({2})", new Object[] { clazz, 
/*  86 */             Arrays.toString((Object[])arguments) });
/*  87 */       return null;
/*     */     } 
/*     */     try {
/*  90 */       Constructor init = clazz.getDeclaredConstructor(arguments);
/*  91 */       init.setAccessible(true);
/*  92 */       return init;
/*  93 */     } catch (Exception ex) {
/*  94 */       LOG.log(Level.FINE, "Failed to lookup method: <init>#{1}({2})", new Object[] { clazz, 
/*  95 */             Arrays.toString((Object[])arguments) });
/*  96 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Method lookupMethod(Class clazz, String methodName, Class... arguments) {
/* 101 */     if (clazz == null) {
/* 102 */       LOG.log(Level.FINE, "Failed to lookup method: {0}#{1}({2})", new Object[] { clazz, methodName, 
/* 103 */             Arrays.toString((Object[])arguments) });
/* 104 */       return null;
/*     */     } 
/*     */     try {
/* 107 */       return clazz.getMethod(methodName, arguments);
/* 108 */     } catch (Exception ex) {
/* 109 */       LOG.log(Level.FINE, "Failed to lookup method: {0}#{1}({2})", new Object[] { clazz, methodName, 
/* 110 */             Arrays.toString((Object[])arguments) });
/* 111 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Class lookupClass(String name) {
/*     */     try {
/* 117 */       return Class.forName(name);
/* 118 */     } catch (ClassNotFoundException ex) {
/* 119 */       LOG.log(Level.FINE, "Failed to lookup class: " + name, ex);
/* 120 */       return null;
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
/*     */   public static boolean isDefault(Method method) {
/* 134 */     if (METHOD_IS_DEFAULT == null) {
/* 135 */       return false;
/*     */     }
/*     */     try {
/* 138 */       return ((Boolean)METHOD_IS_DEFAULT.invoke(method, new Object[0])).booleanValue();
/* 139 */     } catch (IllegalAccessException|IllegalArgumentException ex) {
/* 140 */       throw new RuntimeException(ex);
/* 141 */     } catch (InvocationTargetException ex) {
/* 142 */       Throwable cause = ex.getCause();
/* 143 */       if (cause instanceof RuntimeException)
/* 144 */         throw (RuntimeException)cause; 
/* 145 */       if (cause instanceof Error) {
/* 146 */         throw (Error)cause;
/*     */       }
/* 148 */       throw new RuntimeException(cause);
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
/*     */   public static Object getMethodHandle(Method method) throws Exception {
/* 163 */     assert isDefault(method);
/* 164 */     Object baseLookup = createLookup();
/*     */     try {
/* 166 */       Object lookup = createPrivateLookupIn(method.getDeclaringClass(), baseLookup);
/* 167 */       Object mh = mhViaFindSpecial(lookup, method);
/* 168 */       return mh;
/* 169 */     } catch (Exception ex) {
/* 170 */       Object lookup = getConstructorLookupClass().newInstance(new Object[] { method.getDeclaringClass() });
/* 171 */       Object mh = mhViaUnreflectSpecial(lookup, method);
/* 172 */       return mh;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object mhViaFindSpecial(Object lookup, Method method) throws Exception {
/* 177 */     return METHOD_HANDLES_LOOKUP_FIND_SPECIAL.invoke(lookup, new Object[] { method
/*     */           
/* 179 */           .getDeclaringClass(), method
/* 180 */           .getName(), METHOD_TYPE
/* 181 */           .invoke(null, new Object[] { method.getReturnType(), method.getParameterTypes() }), method
/* 182 */           .getDeclaringClass() });
/*     */   }
/*     */   
/*     */   private static Object mhViaUnreflectSpecial(Object lookup, Method method) throws Exception {
/* 186 */     Object l2 = METHOD_HANDLES_LOOKUP_IN.invoke(lookup, new Object[] { method.getDeclaringClass() });
/* 187 */     return METHOD_HANDLES_LOOKUP_UNREFLECT_SPECIAL.invoke(l2, new Object[] { method, method.getDeclaringClass() });
/*     */   }
/*     */   
/*     */   private static Object createPrivateLookupIn(Class type, Object lookup) throws Exception {
/* 191 */     return METHOD_HANDLES_PRIVATE_LOOKUP_IN.invoke(null, new Object[] { type, lookup });
/*     */   }
/*     */   
/*     */   private static Object createLookup() throws Exception {
/* 195 */     return METHOD_HANDLES_LOOKUP.invoke(null, new Object[0]);
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
/*     */   public static Object invokeDefaultMethod(Object target, Object methodHandle, Object... args) throws Throwable {
/* 209 */     Object boundMethodHandle = METHOD_HANDLES_BIND_TO.invoke(methodHandle, new Object[] { target });
/* 210 */     return METHOD_HANDLES_INVOKE_WITH_ARGUMENTS.invoke(boundMethodHandle, new Object[] { args });
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\internal\ReflectionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */