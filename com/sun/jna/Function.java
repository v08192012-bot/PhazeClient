/*     */ package com.sun.jna;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Function
/*     */   extends Pointer
/*     */ {
/*     */   public static final int MAX_NARGS = 256;
/*     */   public static final int C_CONVENTION = 0;
/*     */   public static final int ALT_CONVENTION = 63;
/*     */   private static final int MASK_CC = 63;
/*     */   public static final int THROW_LAST_ERROR = 64;
/*     */   public static final int USE_VARARGS = 255;
/*     */   private static final int USE_VARARGS_SHIFT = 7;
/*  86 */   static final Integer INTEGER_TRUE = Integer.valueOf(-1);
/*  87 */   static final Integer INTEGER_FALSE = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   private NativeLibrary library;
/*     */ 
/*     */   
/*     */   private final String functionName;
/*     */   
/*     */   final String encoding;
/*     */   
/*     */   final int callFlags;
/*     */   
/*     */   final Map<String, ?> options;
/*     */   
/*     */   static final String OPTION_INVOKING_METHOD = "invoking-method";
/*     */ 
/*     */   
/*     */   public static Function getFunction(String libraryName, String functionName) {
/* 105 */     return NativeLibrary.getInstance(libraryName).getFunction(functionName);
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
/*     */   public static Function getFunction(String libraryName, String functionName, int callFlags) {
/* 126 */     return NativeLibrary.getInstance(libraryName).getFunction(functionName, callFlags, null);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Function getFunction(String libraryName, String functionName, int callFlags, String encoding) {
/* 150 */     return NativeLibrary.getInstance(libraryName).getFunction(functionName, callFlags, encoding);
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
/*     */   public static Function getFunction(Pointer p) {
/* 165 */     return getFunction(p, 0, (String)null);
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
/*     */   public static Function getFunction(Pointer p, int callFlags) {
/* 183 */     return getFunction(p, callFlags, (String)null);
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
/*     */   public static Function getFunction(Pointer p, int callFlags, String encoding) {
/* 204 */     return new Function(p, callFlags, encoding);
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
/* 219 */   private static final VarArgsChecker IS_VARARGS = VarArgsChecker.create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Function(NativeLibrary library, String functionName, int callFlags, String encoding) {
/* 241 */     checkCallingConvention(callFlags & 0x3F);
/* 242 */     if (functionName == null) {
/* 243 */       throw new NullPointerException("Function name must not be null");
/*     */     }
/* 245 */     this.library = library;
/* 246 */     this.functionName = functionName;
/* 247 */     this.callFlags = callFlags;
/* 248 */     this.options = library.getOptions();
/* 249 */     this.encoding = (encoding != null) ? encoding : Native.getDefaultStringEncoding();
/*     */     try {
/* 251 */       this.peer = library.getSymbolAddress(functionName);
/* 252 */     } catch (UnsatisfiedLinkError e) {
/* 253 */       throw new UnsatisfiedLinkError("Error looking up function '" + functionName + "': " + e
/*     */           
/* 255 */           .getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Function(Pointer functionAddress, int callFlags, String encoding) {
/* 275 */     checkCallingConvention(callFlags & 0x3F);
/* 276 */     if (functionAddress == null || functionAddress.peer == 0L)
/*     */     {
/* 278 */       throw new NullPointerException("Function address may not be null");
/*     */     }
/* 280 */     this.functionName = functionAddress.toString();
/* 281 */     this.callFlags = callFlags;
/* 282 */     this.peer = functionAddress.peer;
/* 283 */     this.options = Collections.EMPTY_MAP;
/* 284 */     this
/* 285 */       .encoding = (encoding != null) ? encoding : Native.getDefaultStringEncoding();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkCallingConvention(int convention) throws IllegalArgumentException {
/* 291 */     if ((convention & 0x3F) != convention) {
/* 292 */       throw new IllegalArgumentException("Unrecognized calling convention: " + convention);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 298 */     return this.functionName;
/*     */   }
/*     */   
/*     */   public int getCallingConvention() {
/* 302 */     return this.callFlags & 0x3F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Class<?> returnType, Object[] inArgs) {
/* 309 */     return invoke(returnType, inArgs, this.options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Class<?> returnType, Object[] inArgs, Map<String, ?> options) {
/* 316 */     Method invokingMethod = (Method)options.get("invoking-method");
/* 317 */     Class<?>[] paramTypes = (invokingMethod != null) ? invokingMethod.getParameterTypes() : null;
/* 318 */     return invoke(invokingMethod, paramTypes, returnType, inArgs, options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object invoke(Method invokingMethod, Class<?>[] paramTypes, Class<?> returnType, Object[] inArgs, Map<String, ?> options) {
/* 329 */     Object[] args = new Object[0];
/* 330 */     if (inArgs != null) {
/* 331 */       if (inArgs.length > 256) {
/* 332 */         throw new UnsupportedOperationException("Maximum argument count is 256");
/*     */       }
/* 334 */       args = new Object[inArgs.length];
/* 335 */       System.arraycopy(inArgs, 0, args, 0, args.length);
/*     */     } 
/*     */     
/* 338 */     TypeMapper mapper = (TypeMapper)options.get("type-mapper");
/* 339 */     boolean allowObjects = Boolean.TRUE.equals(options.get("allow-objects"));
/* 340 */     boolean isVarArgs = (args.length > 0 && invokingMethod != null) ? isVarArgs(invokingMethod) : false;
/* 341 */     int fixedArgs = (args.length > 0 && invokingMethod != null) ? fixedArgs(invokingMethod) : 0;
/* 342 */     for (int i = 0; i < args.length; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       Class<?> paramType = (invokingMethod != null) ? ((isVarArgs && i >= paramTypes.length - 1) ? paramTypes[paramTypes.length - 1].getComponentType() : paramTypes[i]) : null;
/* 348 */       args[i] = convertArgument(args, i, invokingMethod, mapper, allowObjects, paramType);
/*     */     } 
/*     */     
/* 351 */     Class<?> nativeReturnType = returnType;
/* 352 */     FromNativeConverter resultConverter = null;
/* 353 */     if (NativeMapped.class.isAssignableFrom(returnType)) {
/* 354 */       NativeMappedConverter tc = NativeMappedConverter.getInstance(returnType);
/* 355 */       resultConverter = tc;
/* 356 */       nativeReturnType = tc.nativeType();
/* 357 */     } else if (mapper != null) {
/* 358 */       resultConverter = mapper.getFromNativeConverter(returnType);
/* 359 */       if (resultConverter != null) {
/* 360 */         nativeReturnType = resultConverter.nativeType();
/*     */       }
/*     */     } 
/*     */     
/* 364 */     Object result = invoke(args, nativeReturnType, allowObjects, fixedArgs);
/*     */     
/* 366 */     if (resultConverter != null) {
/*     */       FromNativeContext context;
/* 368 */       if (invokingMethod != null) {
/* 369 */         context = new MethodResultContext(returnType, this, inArgs, invokingMethod);
/*     */       } else {
/* 371 */         context = new FunctionResultContext(returnType, this, inArgs);
/*     */       } 
/* 373 */       result = resultConverter.fromNative(result, context);
/*     */     } 
/*     */ 
/*     */     
/* 377 */     if (inArgs != null) {
/* 378 */       for (int j = 0; j < inArgs.length; j++) {
/* 379 */         Object inArg = inArgs[j];
/* 380 */         if (inArg != null)
/*     */         {
/* 382 */           if (inArg instanceof Structure) {
/* 383 */             if (!(inArg instanceof Structure.ByValue)) {
/* 384 */               ((Structure)inArg).autoRead();
/*     */             }
/* 386 */           } else if (args[j] instanceof PostCallRead) {
/* 387 */             ((PostCallRead)args[j]).read();
/* 388 */             if (args[j] instanceof PointerArray) {
/* 389 */               PointerArray array = (PointerArray)args[j];
/* 390 */               if (Structure.ByReference[].class.isAssignableFrom(inArg.getClass())) {
/* 391 */                 Class<? extends Structure> type = (Class)inArg.getClass().getComponentType();
/* 392 */                 Structure[] ss = (Structure[])inArg;
/* 393 */                 for (int si = 0; si < ss.length; si++) {
/* 394 */                   Pointer p = array.getPointer((Native.POINTER_SIZE * si));
/* 395 */                   ss[si] = Structure.updateStructureByReference((Class)type, ss[si], p);
/*     */                 } 
/*     */               } 
/*     */             } 
/* 399 */           } else if (Structure[].class.isAssignableFrom(inArg.getClass())) {
/* 400 */             Structure.autoRead((Structure[])inArg);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 405 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   Object invoke(Object[] args, Class<?> returnType, boolean allowObjects) {
/* 410 */     return invoke(args, returnType, allowObjects, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   Object invoke(Object[] args, Class<?> returnType, boolean allowObjects, int fixedArgs) {
/* 415 */     Object result = null;
/* 416 */     int callFlags = this.callFlags | (fixedArgs & 0xFF) << 7;
/* 417 */     if (returnType == null || returnType == void.class || returnType == Void.class) {
/* 418 */       Native.invokeVoid(this, this.peer, callFlags, args);
/* 419 */       result = null;
/* 420 */     } else if (returnType == boolean.class || returnType == Boolean.class) {
/* 421 */       result = valueOf((Native.invokeInt(this, this.peer, callFlags, args) != 0));
/* 422 */     } else if (returnType == byte.class || returnType == Byte.class) {
/* 423 */       result = Byte.valueOf((byte)Native.invokeInt(this, this.peer, callFlags, args));
/* 424 */     } else if (returnType == short.class || returnType == Short.class) {
/* 425 */       result = Short.valueOf((short)Native.invokeInt(this, this.peer, callFlags, args));
/* 426 */     } else if (returnType == char.class || returnType == Character.class) {
/* 427 */       result = Character.valueOf((char)Native.invokeInt(this, this.peer, callFlags, args));
/* 428 */     } else if (returnType == int.class || returnType == Integer.class) {
/* 429 */       result = Integer.valueOf(Native.invokeInt(this, this.peer, callFlags, args));
/* 430 */     } else if (returnType == long.class || returnType == Long.class) {
/* 431 */       result = Long.valueOf(Native.invokeLong(this, this.peer, callFlags, args));
/* 432 */     } else if (returnType == float.class || returnType == Float.class) {
/* 433 */       result = Float.valueOf(Native.invokeFloat(this, this.peer, callFlags, args));
/* 434 */     } else if (returnType == double.class || returnType == Double.class) {
/* 435 */       result = Double.valueOf(Native.invokeDouble(this, this.peer, callFlags, args));
/* 436 */     } else if (returnType == String.class) {
/* 437 */       result = invokeString(callFlags, args, false);
/* 438 */     } else if (returnType == WString.class) {
/* 439 */       String s = invokeString(callFlags, args, true);
/* 440 */       if (s != null)
/* 441 */         result = new WString(s); 
/*     */     } else {
/* 443 */       if (Pointer.class.isAssignableFrom(returnType))
/* 444 */         return invokePointer(callFlags, args); 
/* 445 */       if (Structure.class.isAssignableFrom(returnType)) {
/* 446 */         if (Structure.ByValue.class.isAssignableFrom(returnType)) {
/*     */           
/* 448 */           Structure s = Native.invokeStructure(this, this.peer, callFlags, args, 
/* 449 */               (Structure)Structure.newInstance(returnType));
/* 450 */           s.autoRead();
/* 451 */           result = s;
/*     */         } else {
/* 453 */           result = invokePointer(callFlags, args);
/* 454 */           if (result != null) {
/* 455 */             Structure s = (Structure)Structure.newInstance(returnType, (Pointer)result);
/* 456 */             s.conditionalAutoRead();
/* 457 */             result = s;
/*     */           } 
/*     */         } 
/* 460 */       } else if (Callback.class.isAssignableFrom(returnType)) {
/* 461 */         result = invokePointer(callFlags, args);
/* 462 */         if (result != null) {
/* 463 */           result = CallbackReference.getCallback(returnType, (Pointer)result);
/*     */         }
/* 465 */       } else if (returnType == String[].class) {
/* 466 */         Pointer p = invokePointer(callFlags, args);
/* 467 */         if (p != null) {
/* 468 */           result = p.getStringArray(0L, this.encoding);
/*     */         }
/* 470 */       } else if (returnType == WString[].class) {
/* 471 */         Pointer p = invokePointer(callFlags, args);
/* 472 */         if (p != null) {
/* 473 */           String[] arr = p.getWideStringArray(0L);
/* 474 */           WString[] warr = new WString[arr.length];
/* 475 */           for (int i = 0; i < arr.length; i++) {
/* 476 */             warr[i] = new WString(arr[i]);
/*     */           }
/* 478 */           result = warr;
/*     */         } 
/* 480 */       } else if (returnType == Pointer[].class) {
/* 481 */         Pointer p = invokePointer(callFlags, args);
/* 482 */         if (p != null) {
/* 483 */           result = p.getPointerArray(0L);
/*     */         }
/* 485 */       } else if (allowObjects) {
/* 486 */         result = Native.invokeObject(this, this.peer, callFlags, args);
/* 487 */         if (result != null && 
/* 488 */           !returnType.isAssignableFrom(result.getClass())) {
/* 489 */           throw new ClassCastException("Return type " + returnType + " does not match result " + result
/*     */               
/* 491 */               .getClass());
/*     */         }
/*     */       } else {
/* 494 */         throw new IllegalArgumentException("Unsupported return type " + returnType + " in function " + getName());
/*     */       } 
/* 496 */     }  return result;
/*     */   }
/*     */   
/*     */   private Pointer invokePointer(int callFlags, Object[] args) {
/* 500 */     long ptr = Native.invokePointer(this, this.peer, callFlags, args);
/* 501 */     return (ptr == 0L) ? null : new Pointer(ptr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object convertArgument(Object[] args, int index, Method invokingMethod, TypeMapper mapper, boolean allowObjects, Class<?> expectedType) {
/* 507 */     Object arg = args[index];
/* 508 */     if (arg != null) {
/* 509 */       Class<?> type = arg.getClass();
/* 510 */       ToNativeConverter converter = null;
/* 511 */       if (NativeMapped.class.isAssignableFrom(type)) {
/* 512 */         converter = NativeMappedConverter.getInstance(type);
/* 513 */       } else if (mapper != null) {
/* 514 */         converter = mapper.getToNativeConverter(type);
/*     */       } 
/* 516 */       if (converter != null) {
/*     */         ToNativeContext context;
/* 518 */         if (invokingMethod != null) {
/* 519 */           context = new MethodParameterContext(this, args, index, invokingMethod);
/*     */         } else {
/*     */           
/* 522 */           context = new FunctionParameterContext(this, args, index);
/*     */         } 
/* 524 */         arg = converter.toNative(arg, context);
/*     */       } 
/*     */     } 
/* 527 */     if (arg == null || isPrimitiveArray(arg.getClass())) {
/* 528 */       return arg;
/*     */     }
/*     */     
/* 531 */     Class<?> argClass = arg.getClass();
/*     */     
/* 533 */     if (arg instanceof Structure) {
/* 534 */       Structure struct = (Structure)arg;
/* 535 */       struct.autoWrite();
/* 536 */       if (struct instanceof Structure.ByValue) {
/*     */         
/* 538 */         Class<?> ptype = struct.getClass();
/* 539 */         if (invokingMethod != null) {
/* 540 */           Class<?>[] ptypes = invokingMethod.getParameterTypes();
/* 541 */           if (IS_VARARGS.isVarArgs(invokingMethod)) {
/* 542 */             if (index < ptypes.length - 1) {
/* 543 */               ptype = ptypes[index];
/*     */             } else {
/* 545 */               Class<?> etype = ptypes[ptypes.length - 1].getComponentType();
/* 546 */               if (etype != Object.class) {
/* 547 */                 ptype = etype;
/*     */               }
/*     */             } 
/*     */           } else {
/* 551 */             ptype = ptypes[index];
/*     */           } 
/*     */         } 
/* 554 */         if (Structure.ByValue.class.isAssignableFrom(ptype)) {
/* 555 */           return struct;
/*     */         }
/*     */       } 
/* 558 */       return struct.getPointer();
/* 559 */     }  if (arg instanceof Callback)
/*     */     {
/* 561 */       return CallbackReference.getFunctionPointer((Callback)arg); } 
/* 562 */     if (arg instanceof String)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 567 */       return (new NativeString((String)arg, this.encoding)).getPointer(); } 
/* 568 */     if (arg instanceof WString)
/*     */     {
/* 570 */       return (new NativeString(arg.toString(), true)).getPointer(); } 
/* 571 */     if (arg instanceof Boolean)
/*     */     {
/*     */       
/* 574 */       return Boolean.TRUE.equals(arg) ? INTEGER_TRUE : INTEGER_FALSE; } 
/* 575 */     if (String[].class == argClass)
/* 576 */       return new StringArray((String[])arg, this.encoding); 
/* 577 */     if (WString[].class == argClass)
/* 578 */       return new StringArray((WString[])arg); 
/* 579 */     if (Pointer[].class == argClass)
/* 580 */       return new PointerArray((Pointer[])arg); 
/* 581 */     if (NativeMapped[].class.isAssignableFrom(argClass))
/* 582 */       return new NativeMappedArray((NativeMapped[])arg); 
/* 583 */     if (Structure[].class.isAssignableFrom(argClass)) {
/*     */ 
/*     */       
/* 586 */       Structure[] ss = (Structure[])arg;
/* 587 */       Class<?> type = argClass.getComponentType();
/* 588 */       boolean byRef = Structure.ByReference.class.isAssignableFrom(type);
/* 589 */       if (expectedType != null && 
/* 590 */         !Structure.ByReference[].class.isAssignableFrom(expectedType)) {
/* 591 */         if (byRef) {
/* 592 */           throw new IllegalArgumentException("Function " + getName() + " declared Structure[] at parameter " + index + " but array of " + type + " was passed");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 597 */         for (int i = 0; i < ss.length; i++) {
/* 598 */           if (ss[i] instanceof Structure.ByReference) {
/* 599 */             throw new IllegalArgumentException("Function " + getName() + " declared Structure[] at parameter " + index + " but element " + i + " is of Structure.ByReference type");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 607 */       if (byRef) {
/* 608 */         Structure.autoWrite(ss);
/* 609 */         Pointer[] pointers = new Pointer[ss.length + 1];
/* 610 */         for (int i = 0; i < ss.length; i++) {
/* 611 */           pointers[i] = (ss[i] != null) ? ss[i].getPointer() : null;
/*     */         }
/* 613 */         return new PointerArray(pointers);
/* 614 */       }  if (ss.length == 0)
/* 615 */         throw new IllegalArgumentException("Structure array must have non-zero length"); 
/* 616 */       if (ss[0] == null) {
/* 617 */         Structure.newInstance(type).toArray(ss);
/* 618 */         return ss[0].getPointer();
/*     */       } 
/* 620 */       Structure.autoWrite(ss);
/* 621 */       return ss[0].getPointer();
/*     */     } 
/* 623 */     if (argClass.isArray())
/* 624 */       throw new IllegalArgumentException("Unsupported array argument type: " + argClass
/* 625 */           .getComponentType()); 
/* 626 */     if (allowObjects)
/* 627 */       return arg; 
/* 628 */     if (!Native.isSupportedNativeType(arg.getClass())) {
/* 629 */       throw new IllegalArgumentException("Unsupported argument type " + arg
/* 630 */           .getClass().getName() + " at parameter " + index + " of function " + 
/*     */           
/* 632 */           getName());
/*     */     }
/* 634 */     return arg;
/*     */   }
/*     */   
/*     */   private boolean isPrimitiveArray(Class<?> argClass) {
/* 638 */     return (argClass.isArray() && argClass
/* 639 */       .getComponentType().isPrimitive());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invoke(Object[] args) {
/* 648 */     invoke(Void.class, args);
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
/*     */   private String invokeString(int callFlags, Object[] args, boolean wide) {
/* 663 */     Pointer ptr = invokePointer(callFlags, args);
/* 664 */     String s = null;
/* 665 */     if (ptr != null) {
/* 666 */       if (wide) {
/* 667 */         s = ptr.getWideString(0L);
/*     */       } else {
/*     */         
/* 670 */         s = ptr.getString(0L, this.encoding);
/*     */       } 
/*     */     }
/* 673 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 679 */     if (this.library != null) {
/* 680 */       return "native function " + this.functionName + "(" + this.library.getName() + ")@0x" + 
/* 681 */         Long.toHexString(this.peer);
/*     */     }
/* 683 */     return "native function@0x" + Long.toHexString(this.peer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invokeObject(Object[] args) {
/* 690 */     return invoke(Object.class, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pointer invokePointer(Object[] args) {
/* 697 */     return (Pointer)invoke(Pointer.class, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String invokeString(Object[] args, boolean wide) {
/* 708 */     Object o = invoke(wide ? WString.class : String.class, args);
/* 709 */     return (o != null) ? o.toString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int invokeInt(Object[] args) {
/* 716 */     return ((Integer)invoke(Integer.class, args)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long invokeLong(Object[] args) {
/* 722 */     return ((Long)invoke(Long.class, args)).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float invokeFloat(Object[] args) {
/* 728 */     return ((Float)invoke(Float.class, args)).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double invokeDouble(Object[] args) {
/* 734 */     return ((Double)invoke(Double.class, args)).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeVoid(Object[] args) {
/* 740 */     invoke(Void.class, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 748 */     if (o == this) return true; 
/* 749 */     if (o == null) return false; 
/* 750 */     if (o.getClass() == getClass()) {
/* 751 */       Function other = (Function)o;
/* 752 */       return (other.callFlags == this.callFlags && other.options
/* 753 */         .equals(this.options) && other.peer == this.peer);
/*     */     } 
/*     */     
/* 756 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 764 */     return this.callFlags + this.options.hashCode() + super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object[] concatenateVarArgs(Object[] inArgs) {
/* 774 */     if (inArgs != null && inArgs.length > 0) {
/* 775 */       Object lastArg = inArgs[inArgs.length - 1];
/* 776 */       Class<?> argType = (lastArg != null) ? lastArg.getClass() : null;
/* 777 */       if (argType != null && argType.isArray()) {
/* 778 */         Object[] varArgs = (Object[])lastArg;
/*     */         
/* 780 */         for (int i = 0; i < varArgs.length; i++) {
/* 781 */           if (varArgs[i] instanceof Float) {
/* 782 */             varArgs[i] = Double.valueOf(((Float)varArgs[i]).floatValue());
/*     */           }
/*     */         } 
/* 785 */         Object[] fullArgs = new Object[inArgs.length + varArgs.length];
/* 786 */         System.arraycopy(inArgs, 0, fullArgs, 0, inArgs.length - 1);
/* 787 */         System.arraycopy(varArgs, 0, fullArgs, inArgs.length - 1, varArgs.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 793 */         fullArgs[fullArgs.length - 1] = null;
/* 794 */         inArgs = fullArgs;
/*     */       } 
/*     */     } 
/* 797 */     return inArgs;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isVarArgs(Method m) {
/* 802 */     return IS_VARARGS.isVarArgs(m);
/*     */   }
/*     */ 
/*     */   
/*     */   static int fixedArgs(Method m) {
/* 807 */     return IS_VARARGS.fixedArgs(m);
/*     */   }
/*     */   
/*     */   private static class NativeMappedArray extends Memory implements PostCallRead { private final NativeMapped[] original;
/*     */     
/*     */     public NativeMappedArray(NativeMapped[] arg) {
/* 813 */       super(Native.getNativeSize(arg.getClass(), arg));
/* 814 */       this.original = arg;
/* 815 */       setValue(0L, this.original, this.original.getClass());
/*     */     }
/*     */     
/*     */     public void read() {
/* 819 */       getValue(0L, this.original.getClass(), this.original);
/*     */     } }
/*     */   
/*     */   private static class PointerArray extends Memory implements PostCallRead {
/*     */     private final Pointer[] original;
/*     */     
/*     */     public PointerArray(Pointer[] arg) {
/* 826 */       super((Native.POINTER_SIZE * (arg.length + 1)));
/* 827 */       this.original = arg;
/* 828 */       for (int i = 0; i < arg.length; i++) {
/* 829 */         setPointer((i * Native.POINTER_SIZE), arg[i]);
/*     */       }
/* 831 */       setPointer((Native.POINTER_SIZE * arg.length), (Pointer)null);
/*     */     }
/*     */     
/*     */     public void read() {
/* 835 */       read(0L, this.original, 0, this.original.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static Boolean valueOf(boolean b) {
/* 841 */     return b ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */   
/*     */   public static interface PostCallRead {
/*     */     void read();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\Function.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */