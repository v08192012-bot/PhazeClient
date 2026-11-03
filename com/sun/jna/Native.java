/*      */ package com.sun.jna;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Window;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationHandler;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.IllegalCharsetNameException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
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
/*      */ public final class Native
/*      */   implements Version
/*      */ {
/*  114 */   private static final Logger LOG = Logger.getLogger(Native.class.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Charset DEFAULT_CHARSET;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DEFAULT_ENCODING;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  129 */     String nativeEncoding = System.getProperty("native.encoding");
/*  130 */     Charset nativeCharset = null;
/*  131 */     if (nativeEncoding != null) {
/*      */       try {
/*  133 */         nativeCharset = Charset.forName(nativeEncoding);
/*  134 */       } catch (Exception ex) {
/*  135 */         LOG.log(Level.WARNING, "Failed to get charset for native.encoding value : '" + nativeEncoding + "'", ex);
/*      */       } 
/*      */     }
/*  138 */     if (nativeCharset == null) {
/*  139 */       nativeCharset = Charset.defaultCharset();
/*      */     }
/*  141 */     DEFAULT_CHARSET = nativeCharset;
/*  142 */     DEFAULT_ENCODING = nativeCharset.name();
/*      */   }
/*  144 */   public static final boolean DEBUG_LOAD = Boolean.getBoolean("jna.debug_load");
/*  145 */   public static final boolean DEBUG_JNA_LOAD = Boolean.getBoolean("jna.debug_load.jna");
/*  146 */   private static final Level DEBUG_JNA_LOAD_LEVEL = DEBUG_JNA_LOAD ? Level.INFO : Level.FINE;
/*      */ 
/*      */   
/*  149 */   static String jnidispatchPath = null;
/*  150 */   private static final Map<Class<?>, Map<String, Object>> typeOptions = Collections.synchronizedMap(new WeakHashMap<>());
/*  151 */   private static final Map<Class<?>, Reference<?>> libraries = Collections.synchronizedMap(new WeakHashMap<>()); private static final String _OPTION_ENCLOSING_LIBRARY = "enclosing-library";
/*      */   
/*  153 */   private static final Callback.UncaughtExceptionHandler DEFAULT_HANDLER = new Callback.UncaughtExceptionHandler()
/*      */     {
/*      */       public void uncaughtException(Callback c, Throwable e)
/*      */       {
/*  157 */         Native.LOG.log(Level.WARNING, "JNA: Callback " + c + " threw the following exception", e);
/*      */       }
/*      */     };
/*  160 */   private static Callback.UncaughtExceptionHandler callbackExceptionHandler = DEFAULT_HANDLER;
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
/*      */   static boolean isCompatibleVersion(String expectedVersion, String nativeVersion) {
/*  198 */     String[] expectedVersionParts = expectedVersion.split("\\.");
/*  199 */     String[] nativeVersionParts = nativeVersion.split("\\.");
/*  200 */     if (expectedVersionParts.length < 3 || nativeVersionParts.length < 3) {
/*  201 */       return false;
/*      */     }
/*      */     
/*  204 */     int expectedMajor = Integer.parseInt(expectedVersionParts[0]);
/*  205 */     int nativeMajor = Integer.parseInt(nativeVersionParts[0]);
/*  206 */     int expectedMinor = Integer.parseInt(expectedVersionParts[1]);
/*  207 */     int nativeMinor = Integer.parseInt(nativeVersionParts[1]);
/*      */     
/*  209 */     if (expectedMajor != nativeMajor) {
/*  210 */       return false;
/*      */     }
/*      */     
/*  213 */     if (expectedMinor > nativeMinor) {
/*  214 */       return false;
/*      */     }
/*      */     
/*  217 */     return true;
/*      */   }
/*      */   
/*      */   static {
/*  221 */     loadNativeDispatchLibrary();
/*      */     
/*  223 */     if (!isCompatibleVersion("7.0.2", getNativeVersion())) {
/*  224 */       String LS = System.lineSeparator();
/*  225 */       throw new Error(LS + LS + "There is an incompatible JNA native library installed on this system" + LS + "Expected: " + "7.0.2" + LS + "Found:    " + 
/*      */ 
/*      */           
/*  228 */           getNativeVersion() + LS + (
/*  229 */           (jnidispatchPath != null) ? (
/*  230 */           "(at " + jnidispatchPath + ")") : System.getProperty("java.library.path")) + "." + LS + "To resolve this issue you may do one of the following:" + LS + " - remove or uninstall the offending library" + LS + " - set the system property jna.nosys=true" + LS + " - set jna.boot.library.path to include the path to the version of the " + LS + "   jnidispatch library included with the JNA jar file you are using" + LS);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  239 */   public static final int POINTER_SIZE = sizeof(0);
/*  240 */   public static final int LONG_SIZE = sizeof(1);
/*  241 */   public static final int WCHAR_SIZE = sizeof(2);
/*  242 */   public static final int SIZE_T_SIZE = sizeof(3);
/*  243 */   public static final int BOOL_SIZE = sizeof(4);
/*  244 */   public static final int LONG_DOUBLE_SIZE = sizeof(5); private static final int TYPE_VOIDP = 0; private static final int TYPE_LONG = 1; private static final int TYPE_WCHAR_T = 2; private static final int TYPE_SIZE_T = 3; private static final int TYPE_BOOL = 4;
/*      */   private static final int TYPE_LONG_DOUBLE = 5;
/*      */   
/*      */   static {
/*  248 */     initIDs();
/*  249 */     if (Boolean.getBoolean("jna.protected"))
/*  250 */       setProtected(true); 
/*      */   }
/*  252 */   static final int MAX_ALIGNMENT = (Platform.isSPARC() || Platform.isWindows() || (
/*  253 */     Platform.isLinux() && (Platform.isARM() || Platform.isPPC() || Platform.isMIPS() || Platform.isLoongArch())) || 
/*  254 */     Platform.isAIX() || (
/*  255 */     Platform.isAndroid() && !Platform.isIntel())) ? 
/*  256 */     8 : LONG_SIZE;
/*  257 */   static final int MAX_PADDING = (Platform.isMac() && Platform.isPPC()) ? 8 : MAX_ALIGNMENT; static {
/*  258 */     System.setProperty("jna.loaded", "true");
/*      */   }
/*      */ 
/*      */   
/*  262 */   private static final Object finalizer = new Object()
/*      */     {
/*      */       protected void finalize() throws Throwable {
/*  265 */         Native.dispose();
/*  266 */         super.finalize();
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   static final String JNA_TMPLIB_PREFIX = "jna";
/*      */ 
/*      */   
/*      */   private static void dispose() {
/*  275 */     CallbackReference.disposeAll();
/*  276 */     Memory.disposeAll();
/*  277 */     NativeLibrary.disposeAll();
/*  278 */     unregisterAll();
/*  279 */     jnidispatchPath = null;
/*  280 */     System.setProperty("jna.loaded", "false");
/*      */   }
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
/*      */   static boolean deleteLibrary(File lib) {
/*  295 */     if (lib.delete()) {
/*  296 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  300 */     markTemporaryFile(lib);
/*      */     
/*  302 */     return false;
/*      */   }
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
/*      */   public static long getWindowID(Window w) throws HeadlessException {
/*  342 */     return AWT.getWindowID(w);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getComponentID(Component c) throws HeadlessException {
/*  352 */     return AWT.getComponentID(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Pointer getWindowPointer(Window w) throws HeadlessException {
/*  362 */     return new Pointer(AWT.getWindowID(w));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Pointer getComponentPointer(Component c) throws HeadlessException {
/*  372 */     return new Pointer(AWT.getComponentID(c));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Pointer getDirectBufferPointer(Buffer b) {
/*  381 */     long peer = _getDirectBufferPointer(b);
/*  382 */     return (peer == 0L) ? null : new Pointer(peer);
/*      */   }
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
/*      */   private static Charset getCharset(String encoding) {
/*  395 */     Charset charset = null;
/*  396 */     if (encoding != null) {
/*      */       try {
/*  398 */         charset = Charset.forName(encoding);
/*      */       }
/*  400 */       catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException e) {
/*  401 */         LOG.log(Level.WARNING, "JNA Warning: Encoding ''{0}'' is unsupported ({1})", new Object[] { encoding, e
/*  402 */               .getMessage() });
/*      */       } 
/*      */     }
/*  405 */     if (charset == null) {
/*  406 */       LOG.log(Level.WARNING, "JNA Warning: Using fallback encoding {0}", DEFAULT_CHARSET);
/*  407 */       charset = DEFAULT_CHARSET;
/*      */     } 
/*  409 */     return charset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(byte[] buf) {
/*  421 */     return toString(buf, getDefaultStringEncoding());
/*      */   }
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
/*      */   public static String toString(byte[] buf, String encoding) {
/*  438 */     return toString(buf, getCharset(encoding));
/*      */   }
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
/*      */   public static String toString(byte[] buf, Charset charset) {
/*  454 */     int len = buf.length;
/*      */     
/*  456 */     for (int index = 0; index < len; index++) {
/*  457 */       if (buf[index] == 0) {
/*  458 */         len = index;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  463 */     if (len == 0) {
/*  464 */       return "";
/*      */     }
/*      */     
/*  467 */     return new String(buf, 0, len, charset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(char[] buf) {
/*  477 */     int len = buf.length;
/*  478 */     for (int index = 0; index < len; index++) {
/*  479 */       if (buf[index] == '\000') {
/*  480 */         len = index;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  485 */     if (len == 0) {
/*  486 */       return "";
/*      */     }
/*  488 */     return new String(buf, 0, len);
/*      */   }
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
/*      */   public static List<String> toStringList(char[] buf) {
/*  502 */     return toStringList(buf, 0, buf.length);
/*      */   }
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
/*      */   public static List<String> toStringList(char[] buf, int offset, int len) {
/*  516 */     List<String> list = new ArrayList<>();
/*  517 */     int lastPos = offset;
/*  518 */     int maxPos = offset + len;
/*  519 */     for (int curPos = offset; curPos < maxPos; curPos++) {
/*  520 */       if (buf[curPos] == '\000') {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  525 */         if (lastPos == curPos) {
/*  526 */           return list;
/*      */         }
/*      */         
/*  529 */         String value = new String(buf, lastPos, curPos - lastPos);
/*  530 */         list.add(value);
/*  531 */         lastPos = curPos + 1;
/*      */       } 
/*      */     } 
/*      */     
/*  535 */     if (lastPos < maxPos) {
/*  536 */       String value = new String(buf, lastPos, maxPos - lastPos);
/*  537 */       list.add(value);
/*      */     } 
/*      */     
/*  540 */     return list;
/*      */   }
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
/*      */   public static <T extends Library> T load(Class<T> interfaceClass) {
/*  555 */     return load((String)null, interfaceClass);
/*      */   }
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
/*      */   public static <T extends Library> T load(Class<T> interfaceClass, Map<String, ?> options) {
/*  574 */     return load(null, interfaceClass, options);
/*      */   }
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
/*      */   public static <T extends Library> T load(String name, Class<T> interfaceClass) {
/*  592 */     return load(name, interfaceClass, Collections.emptyMap());
/*      */   }
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
/*      */   public static <T extends Library> T load(String name, Class<T> interfaceClass, Map<String, ?> options) {
/*  612 */     if (!Library.class.isAssignableFrom(interfaceClass))
/*      */     {
/*  614 */       throw new IllegalArgumentException("Interface (" + interfaceClass.getSimpleName() + ") of library=" + name + " does not extend " + Library.class
/*  615 */           .getSimpleName());
/*      */     }
/*      */     
/*  618 */     Library.Handler handler = new Library.Handler(name, interfaceClass, options);
/*  619 */     ClassLoader loader = interfaceClass.getClassLoader();
/*  620 */     Object proxy = Proxy.newProxyInstance(loader, new Class[] { interfaceClass }, handler);
/*  621 */     cacheOptions(interfaceClass, options, proxy);
/*  622 */     return interfaceClass.cast(proxy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <T> T loadLibrary(Class<T> interfaceClass) {
/*  632 */     return loadLibrary((String)null, interfaceClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <T> T loadLibrary(Class<T> interfaceClass, Map<String, ?> options) {
/*  642 */     return loadLibrary(null, interfaceClass, options);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <T> T loadLibrary(String name, Class<T> interfaceClass) {
/*  652 */     return loadLibrary(name, interfaceClass, Collections.emptyMap());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <T> T loadLibrary(String name, Class<T> interfaceClass, Map<String, ?> options) {
/*  662 */     if (!Library.class.isAssignableFrom(interfaceClass))
/*      */     {
/*  664 */       throw new IllegalArgumentException("Interface (" + interfaceClass.getSimpleName() + ") of library=" + name + " does not extend " + Library.class
/*  665 */           .getSimpleName());
/*      */     }
/*      */     
/*  668 */     Library.Handler handler = new Library.Handler(name, interfaceClass, options);
/*  669 */     ClassLoader loader = interfaceClass.getClassLoader();
/*  670 */     Object proxy = Proxy.newProxyInstance(loader, new Class[] { interfaceClass }, handler);
/*  671 */     cacheOptions(interfaceClass, options, proxy);
/*  672 */     return interfaceClass.cast(proxy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadLibraryInstance(Class<?> cls) {
/*  681 */     if (cls != null && !libraries.containsKey(cls)) {
/*      */       try {
/*  683 */         Field[] fields = cls.getFields();
/*  684 */         for (int i = 0; i < fields.length; i++) {
/*  685 */           Field field = fields[i];
/*  686 */           if (field.getType() == cls && 
/*  687 */             Modifier.isStatic(field.getModifiers())) {
/*      */             
/*  689 */             field.setAccessible(true);
/*  690 */             libraries.put(cls, new WeakReference(field.get(null)));
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  695 */       } catch (Exception e) {
/*  696 */         throw new IllegalArgumentException("Could not access instance of " + cls + " (" + e + ")");
/*      */       } 
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
/*      */   
/*      */   static Class<?> findEnclosingLibraryClass(Class<?> cls) {
/*  710 */     if (cls == null) {
/*  711 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  715 */     Map<String, ?> libOptions = typeOptions.get(cls);
/*  716 */     if (libOptions != null) {
/*  717 */       Class<?> enclosingClass = (Class)libOptions.get("enclosing-library");
/*  718 */       if (enclosingClass != null) {
/*  719 */         return enclosingClass;
/*      */       }
/*  721 */       return cls;
/*      */     } 
/*  723 */     if (Library.class.isAssignableFrom(cls)) {
/*  724 */       return cls;
/*      */     }
/*  726 */     if (Callback.class.isAssignableFrom(cls)) {
/*  727 */       cls = CallbackReference.findCallbackClass(cls);
/*      */     }
/*  729 */     Class<?> declaring = cls.getDeclaringClass();
/*  730 */     Class<?> fromDeclaring = findEnclosingLibraryClass(declaring);
/*  731 */     if (fromDeclaring != null) {
/*  732 */       return fromDeclaring;
/*      */     }
/*  734 */     return findEnclosingLibraryClass(cls.getSuperclass());
/*      */   }
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
/*      */   public static Map<String, Object> getLibraryOptions(Class<?> type) {
/*  753 */     Map<String, Object> libraryOptions = typeOptions.get(type);
/*  754 */     if (libraryOptions != null) {
/*  755 */       return libraryOptions;
/*      */     }
/*      */     
/*  758 */     Class<?> mappingClass = findEnclosingLibraryClass(type);
/*  759 */     if (mappingClass != null) {
/*  760 */       loadLibraryInstance(mappingClass);
/*      */     } else {
/*  762 */       mappingClass = type;
/*      */     } 
/*      */     
/*  765 */     libraryOptions = typeOptions.get(mappingClass);
/*  766 */     if (libraryOptions != null) {
/*  767 */       typeOptions.put(type, libraryOptions);
/*  768 */       return libraryOptions;
/*      */     } 
/*      */     
/*      */     try {
/*  772 */       Field field = mappingClass.getField("OPTIONS");
/*  773 */       field.setAccessible(true);
/*  774 */       libraryOptions = (Map<String, Object>)field.get(null);
/*  775 */       if (libraryOptions == null) {
/*  776 */         throw new IllegalStateException("Null options field");
/*      */       }
/*  778 */     } catch (NoSuchFieldException e) {
/*  779 */       libraryOptions = Collections.emptyMap();
/*  780 */     } catch (Exception e) {
/*  781 */       throw new IllegalArgumentException("OPTIONS must be a public field of type java.util.Map (" + e + "): " + mappingClass);
/*      */     } 
/*      */     
/*  784 */     libraryOptions = new HashMap<>(libraryOptions);
/*  785 */     if (!libraryOptions.containsKey("type-mapper")) {
/*  786 */       libraryOptions.put("type-mapper", lookupField(mappingClass, "TYPE_MAPPER", TypeMapper.class));
/*      */     }
/*  788 */     if (!libraryOptions.containsKey("structure-alignment")) {
/*  789 */       libraryOptions.put("structure-alignment", lookupField(mappingClass, "STRUCTURE_ALIGNMENT", Integer.class));
/*      */     }
/*  791 */     if (!libraryOptions.containsKey("string-encoding")) {
/*  792 */       libraryOptions.put("string-encoding", lookupField(mappingClass, "STRING_ENCODING", String.class));
/*      */     }
/*  794 */     libraryOptions = cacheOptions(mappingClass, libraryOptions, null);
/*      */     
/*  796 */     if (type != mappingClass) {
/*  797 */       typeOptions.put(type, libraryOptions);
/*      */     }
/*  799 */     return libraryOptions;
/*      */   }
/*      */   
/*      */   private static Object lookupField(Class<?> mappingClass, String fieldName, Class<?> resultClass) {
/*      */     try {
/*  804 */       Field field = mappingClass.getField(fieldName);
/*  805 */       field.setAccessible(true);
/*  806 */       return field.get(null);
/*      */     }
/*  808 */     catch (NoSuchFieldException e) {
/*  809 */       return null;
/*      */     }
/*  811 */     catch (Exception e) {
/*  812 */       throw new IllegalArgumentException(fieldName + " must be a public field of type " + resultClass
/*  813 */           .getName() + " (" + e + "): " + mappingClass);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TypeMapper getTypeMapper(Class<?> cls) {
/*  822 */     Map<String, ?> options = getLibraryOptions(cls);
/*  823 */     return (TypeMapper)options.get("type-mapper");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStringEncoding(Class<?> cls) {
/*  833 */     Map<String, ?> options = getLibraryOptions(cls);
/*  834 */     String encoding = (String)options.get("string-encoding");
/*  835 */     return (encoding != null) ? encoding : getDefaultStringEncoding();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDefaultStringEncoding() {
/*  843 */     return System.getProperty("jna.encoding", DEFAULT_ENCODING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStructureAlignment(Class<?> cls) {
/*  852 */     Integer alignment = (Integer)getLibraryOptions(cls).get("structure-alignment");
/*  853 */     return (alignment == null) ? 0 : alignment.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getBytes(String s) {
/*  862 */     return getBytes(s, getDefaultStringEncoding());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getBytes(String s, String encoding) {
/*  874 */     return getBytes(s, getCharset(encoding));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getBytes(String s, Charset charset) {
/*  884 */     return s.getBytes(charset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] toByteArray(String s) {
/*  894 */     return toByteArray(s, getDefaultStringEncoding());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] toByteArray(String s, String encoding) {
/*  906 */     return toByteArray(s, getCharset(encoding));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] toByteArray(String s, Charset charset) {
/*  917 */     byte[] bytes = getBytes(s, charset);
/*  918 */     byte[] buf = new byte[bytes.length + 1];
/*  919 */     System.arraycopy(bytes, 0, buf, 0, bytes.length);
/*  920 */     return buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] toCharArray(String s) {
/*  928 */     char[] chars = s.toCharArray();
/*  929 */     char[] buf = new char[chars.length + 1];
/*  930 */     System.arraycopy(chars, 0, buf, 0, chars.length);
/*  931 */     return buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadNativeDispatchLibrary() {
/*  940 */     if (!Boolean.getBoolean("jna.nounpack")) {
/*      */       try {
/*  942 */         removeTemporaryFiles();
/*      */       }
/*  944 */       catch (IOException e) {
/*  945 */         LOG.log(Level.WARNING, "JNA Warning: IOException removing temporary files", e);
/*      */       } 
/*      */     }
/*      */     
/*  949 */     String libName = System.getProperty("jna.boot.library.name", "jnidispatch");
/*  950 */     String bootPath = System.getProperty("jna.boot.library.path");
/*  951 */     if (bootPath != null) {
/*      */       
/*  953 */       StringTokenizer dirs = new StringTokenizer(bootPath, File.pathSeparator);
/*  954 */       while (dirs.hasMoreTokens()) {
/*  955 */         String dir = dirs.nextToken();
/*  956 */         File file = new File(new File(dir), System.mapLibraryName(libName).replace(".dylib", ".jnilib"));
/*  957 */         String path = file.getAbsolutePath();
/*  958 */         LOG.log(DEBUG_JNA_LOAD_LEVEL, "Looking in {0}", path);
/*  959 */         if (file.exists()) {
/*      */           try {
/*  961 */             LOG.log(DEBUG_JNA_LOAD_LEVEL, "Trying {0}", path);
/*  962 */             System.setProperty("jnidispatch.path", path);
/*  963 */             System.load(path);
/*  964 */             jnidispatchPath = path;
/*  965 */             LOG.log(DEBUG_JNA_LOAD_LEVEL, "Found jnidispatch at {0}", path);
/*      */             return;
/*  967 */           } catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  973 */         if (Platform.isMac()) {
/*      */           String orig, ext;
/*  975 */           if (path.endsWith("dylib")) {
/*  976 */             orig = "dylib";
/*  977 */             ext = "jnilib";
/*      */           } else {
/*  979 */             orig = "jnilib";
/*  980 */             ext = "dylib";
/*      */           } 
/*  982 */           path = path.substring(0, path.lastIndexOf(orig)) + ext;
/*  983 */           LOG.log(DEBUG_JNA_LOAD_LEVEL, "Looking in {0}", path);
/*  984 */           if ((new File(path)).exists()) {
/*      */             try {
/*  986 */               LOG.log(DEBUG_JNA_LOAD_LEVEL, "Trying {0}", path);
/*  987 */               System.setProperty("jnidispatch.path", path);
/*  988 */               System.load(path);
/*  989 */               jnidispatchPath = path;
/*  990 */               LOG.log(DEBUG_JNA_LOAD_LEVEL, "Found jnidispatch at {0}", path);
/*      */               return;
/*  992 */             } catch (UnsatisfiedLinkError ex) {
/*  993 */               LOG.log(Level.WARNING, "File found at " + path + " but not loadable: " + ex.getMessage(), ex);
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  999 */     String jnaNosys = System.getProperty("jna.nosys", "true");
/* 1000 */     if (!Boolean.parseBoolean(jnaNosys) || Platform.isAndroid()) {
/*      */       try {
/* 1002 */         LOG.log(DEBUG_JNA_LOAD_LEVEL, "Trying (via loadLibrary) {0}", libName);
/* 1003 */         System.loadLibrary(libName);
/* 1004 */         LOG.log(DEBUG_JNA_LOAD_LEVEL, "Found jnidispatch on system path");
/*      */         
/*      */         return;
/* 1007 */       } catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
/*      */     }
/*      */     
/* 1010 */     if (!Boolean.getBoolean("jna.noclasspath")) {
/* 1011 */       loadNativeDispatchLibraryFromClasspath();
/*      */     } else {
/*      */       
/* 1014 */       throw new UnsatisfiedLinkError("Unable to locate JNA native support library");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadNativeDispatchLibraryFromClasspath() {
/*      */     try {
/* 1025 */       String mappedName = System.mapLibraryName("jnidispatch").replace(".dylib", ".jnilib");
/* 1026 */       if (Platform.isAIX())
/*      */       {
/*      */         
/* 1029 */         mappedName = "libjnidispatch.a";
/*      */       }
/* 1031 */       String libName = "/com/sun/jna/" + Platform.RESOURCE_PREFIX + "/" + mappedName;
/* 1032 */       File lib = extractFromResourcePath(libName, Native.class.getClassLoader());
/* 1033 */       if (lib == null && 
/* 1034 */         lib == null) {
/* 1035 */         throw new UnsatisfiedLinkError("Could not find JNA native support");
/*      */       }
/*      */ 
/*      */       
/* 1039 */       LOG.log(DEBUG_JNA_LOAD_LEVEL, "Trying {0}", lib.getAbsolutePath());
/* 1040 */       System.setProperty("jnidispatch.path", lib.getAbsolutePath());
/* 1041 */       System.load(lib.getAbsolutePath());
/* 1042 */       jnidispatchPath = lib.getAbsolutePath();
/* 1043 */       LOG.log(DEBUG_JNA_LOAD_LEVEL, "Found jnidispatch at {0}", jnidispatchPath);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1049 */       if (isUnpacked(lib) && 
/* 1050 */         !Boolean.getBoolean("jnidispatch.preserve")) {
/* 1051 */         deleteLibrary(lib);
/*      */       }
/*      */     }
/* 1054 */     catch (IOException e) {
/* 1055 */       throw new UnsatisfiedLinkError(e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean isUnpacked(File file) {
/* 1061 */     return file.getName().startsWith("jna");
/*      */   }
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
/*      */   public static File extractFromResourcePath(String name) throws IOException {
/* 1076 */     return extractFromResourcePath(name, null);
/*      */   }
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
/*      */   public static File extractFromResourcePath(String name, ClassLoader loader) throws IOException {
/* 1094 */     Level DEBUG = (DEBUG_LOAD || (DEBUG_JNA_LOAD && name.contains("jnidispatch"))) ? Level.INFO : Level.FINE;
/* 1095 */     if (loader == null) {
/* 1096 */       loader = Thread.currentThread().getContextClassLoader();
/*      */       
/* 1098 */       if (loader == null) {
/* 1099 */         loader = Native.class.getClassLoader();
/*      */       }
/*      */     } 
/* 1102 */     LOG.log(DEBUG, "Looking in classpath from {0} for {1}", new Object[] { loader, name });
/* 1103 */     String libname = name.startsWith("/") ? name : NativeLibrary.mapSharedLibraryName(name);
/* 1104 */     String resourcePath = name.startsWith("/") ? name : (Platform.RESOURCE_PREFIX + "/" + libname);
/* 1105 */     if (resourcePath.startsWith("/")) {
/* 1106 */       resourcePath = resourcePath.substring(1);
/*      */     }
/* 1108 */     URL url = loader.getResource(resourcePath);
/* 1109 */     if (url == null) {
/* 1110 */       if (resourcePath.startsWith(Platform.RESOURCE_PREFIX)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1115 */         if (Platform.RESOURCE_PREFIX.startsWith("darwin")) {
/* 1116 */           url = loader.getResource("darwin/" + resourcePath.substring(Platform.RESOURCE_PREFIX.length() + 1));
/*      */         }
/* 1118 */         if (url == null)
/*      */         {
/* 1120 */           url = loader.getResource(libname);
/*      */         }
/* 1122 */       } else if (resourcePath.startsWith("com/sun/jna/" + Platform.RESOURCE_PREFIX + "/")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1127 */         if (Platform.RESOURCE_PREFIX.startsWith("com/sun/jna/darwin")) {
/* 1128 */           url = loader.getResource("com/sun/jna/darwin" + resourcePath.substring(("com/sun/jna/" + Platform.RESOURCE_PREFIX).length() + 1));
/*      */         }
/* 1130 */         if (url == null)
/*      */         {
/* 1132 */           url = loader.getResource(libname);
/*      */         }
/*      */       } 
/*      */     }
/* 1136 */     if (url == null) {
/* 1137 */       String path = System.getProperty("java.class.path");
/* 1138 */       if (loader instanceof URLClassLoader) {
/* 1139 */         path = Arrays.<URL>asList(((URLClassLoader)loader).getURLs()).toString();
/*      */       }
/* 1141 */       throw new IOException("Native library (" + resourcePath + ") not found in resource path (" + path + ")");
/*      */     } 
/* 1143 */     LOG.log(DEBUG, "Found library resource at {0}", url);
/*      */     
/* 1145 */     File lib = null;
/* 1146 */     if (url.getProtocol().toLowerCase().equals("file")) {
/*      */       try {
/* 1148 */         lib = new File(new URI(url.toString()));
/*      */       }
/* 1150 */       catch (URISyntaxException e) {
/* 1151 */         lib = new File(url.getPath());
/*      */       } 
/* 1153 */       LOG.log(DEBUG, "Looking in {0}", lib.getAbsolutePath());
/* 1154 */       if (!lib.exists()) {
/* 1155 */         throw new IOException("File URL " + url + " could not be properly decoded");
/*      */       }
/*      */     }
/* 1158 */     else if (!Boolean.getBoolean("jna.nounpack")) {
/* 1159 */       InputStream is = url.openStream();
/* 1160 */       if (is == null) {
/* 1161 */         throw new IOException("Can't obtain InputStream for " + resourcePath);
/*      */       }
/*      */       
/* 1164 */       FileOutputStream fos = null;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1169 */         File dir = getTempDir();
/* 1170 */         lib = File.createTempFile("jna", Platform.isWindows() ? ".dll" : null, dir);
/* 1171 */         if (!Boolean.getBoolean("jnidispatch.preserve")) {
/* 1172 */           lib.deleteOnExit();
/*      */         }
/* 1174 */         LOG.log(DEBUG, "Extracting library to {0}", lib.getAbsolutePath());
/* 1175 */         fos = new FileOutputStream(lib);
/*      */         
/* 1177 */         byte[] buf = new byte[1024]; int count;
/* 1178 */         while ((count = is.read(buf, 0, buf.length)) > 0) {
/* 1179 */           fos.write(buf, 0, count);
/*      */         }
/*      */       }
/* 1182 */       catch (IOException e) {
/* 1183 */         throw new IOException("Failed to create temporary file for " + name + " library: " + e.getMessage());
/*      */       } finally {
/*      */         
/* 1186 */         try { is.close(); } catch (IOException iOException) {}
/* 1187 */         if (fos != null) {
/* 1188 */           try { fos.close(); } catch (IOException iOException) {}
/*      */         }
/*      */       } 
/*      */     } 
/* 1192 */     return lib;
/*      */   }
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
/*      */   public static Library synchronizedLibrary(final Library library) {
/* 1235 */     Class<?> cls = library.getClass();
/* 1236 */     if (!Proxy.isProxyClass(cls)) {
/* 1237 */       throw new IllegalArgumentException("Library must be a proxy class");
/*      */     }
/* 1239 */     InvocationHandler ih = Proxy.getInvocationHandler(library);
/* 1240 */     if (!(ih instanceof Library.Handler)) {
/* 1241 */       throw new IllegalArgumentException("Unrecognized proxy handler: " + ih);
/*      */     }
/* 1243 */     final Library.Handler handler = (Library.Handler)ih;
/* 1244 */     InvocationHandler newHandler = new InvocationHandler()
/*      */       {
/*      */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 1247 */           synchronized (handler.getNativeLibrary()) {
/* 1248 */             return handler.invoke(library, method, args);
/*      */           } 
/*      */         }
/*      */       };
/* 1252 */     return (Library)Proxy.newProxyInstance(cls.getClassLoader(), cls
/* 1253 */         .getInterfaces(), newHandler);
/*      */   }
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
/*      */   public static String getWebStartLibraryPath(String libName) {
/* 1273 */     if (System.getProperty("javawebstart.version") == null) {
/* 1274 */       return null;
/*      */     }
/*      */     try {
/* 1277 */       ClassLoader cl = Native.class.getClassLoader();
/* 1278 */       Method m = AccessController.<Method>doPrivileged(new PrivilegedAction<Method>()
/*      */           {
/*      */             public Method run() {
/*      */               try {
/* 1282 */                 Method m = ClassLoader.class.getDeclaredMethod("findLibrary", new Class[] { String.class });
/* 1283 */                 m.setAccessible(true);
/* 1284 */                 return m;
/*      */               }
/* 1286 */               catch (Exception e) {
/* 1287 */                 return null;
/*      */               } 
/*      */             }
/*      */           });
/* 1291 */       String libpath = (String)m.invoke(cl, new Object[] { libName });
/* 1292 */       if (libpath != null) {
/* 1293 */         return (new File(libpath)).getParent();
/*      */       }
/* 1295 */       return null;
/*      */     }
/* 1297 */     catch (Exception e) {
/* 1298 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void markTemporaryFile(File file) {
/*      */     try {
/* 1308 */       File marker = new File(file.getParentFile(), file.getName() + ".x");
/* 1309 */       marker.createNewFile();
/*      */     } catch (IOException e) {
/* 1311 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static File getTempDir() throws IOException {
/*      */     File jnatmp;
/* 1319 */     String prop = System.getProperty("jna.tmpdir");
/* 1320 */     if (prop != null) {
/* 1321 */       jnatmp = new File(prop);
/* 1322 */       jnatmp.mkdirs();
/*      */     } else {
/*      */       
/* 1325 */       File tmp = new File(System.getProperty("java.io.tmpdir"));
/* 1326 */       if (Platform.isMac()) {
/*      */         
/* 1328 */         jnatmp = new File(System.getProperty("user.home"), "Library/Caches/JNA/temp");
/* 1329 */       } else if (Platform.isLinux() || Platform.isSolaris() || Platform.isAIX() || Platform.isDragonFlyBSD() || Platform.isFreeBSD() || Platform.isNetBSD() || Platform.isOpenBSD() || Platform.iskFreeBSD()) {
/*      */         File xdgCacheFile;
/*      */         
/* 1332 */         String xdgCacheEnvironment = System.getenv("XDG_CACHE_HOME");
/*      */         
/* 1334 */         if (xdgCacheEnvironment == null || xdgCacheEnvironment.trim().isEmpty()) {
/* 1335 */           xdgCacheFile = new File(System.getProperty("user.home"), ".cache");
/*      */         } else {
/* 1337 */           xdgCacheFile = new File(xdgCacheEnvironment);
/*      */         } 
/* 1339 */         jnatmp = new File(xdgCacheFile, "JNA/temp");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1344 */         jnatmp = new File(tmp, "jna-" + System.getProperty("user.name").hashCode());
/*      */       } 
/*      */       
/* 1347 */       jnatmp.mkdirs();
/* 1348 */       if (!jnatmp.exists() || !jnatmp.canWrite()) {
/* 1349 */         jnatmp = tmp;
/*      */       }
/*      */     } 
/* 1352 */     if (!jnatmp.exists()) {
/* 1353 */       throw new IOException("JNA temporary directory '" + jnatmp + "' does not exist");
/*      */     }
/* 1355 */     if (!jnatmp.canWrite()) {
/* 1356 */       throw new IOException("JNA temporary directory '" + jnatmp + "' is not writable");
/*      */     }
/* 1358 */     return jnatmp;
/*      */   }
/*      */ 
/*      */   
/*      */   static void removeTemporaryFiles() throws IOException {
/* 1363 */     File dir = getTempDir();
/* 1364 */     FilenameFilter filter = new FilenameFilter()
/*      */       {
/*      */         public boolean accept(File dir, String name) {
/* 1367 */           return (name.endsWith(".x") && name.startsWith("jna"));
/*      */         }
/*      */       };
/* 1370 */     File[] files = dir.listFiles(filter);
/* 1371 */     for (int i = 0; files != null && i < files.length; i++) {
/* 1372 */       File marker = files[i];
/* 1373 */       String name = marker.getName();
/* 1374 */       name = name.substring(0, name.length() - 2);
/* 1375 */       File target = new File(marker.getParentFile(), name);
/* 1376 */       if (!target.exists() || target.delete()) {
/* 1377 */         marker.delete();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getNativeSize(Class<?> type, Object value) {
/* 1389 */     if (type.isArray()) {
/* 1390 */       int len = Array.getLength(value);
/* 1391 */       if (len > 0) {
/* 1392 */         Object o = Array.get(value, 0);
/* 1393 */         return len * getNativeSize(type.getComponentType(), o);
/*      */       } 
/*      */       
/* 1396 */       throw new IllegalArgumentException("Arrays of length zero not allowed: " + type);
/*      */     } 
/* 1398 */     if (Structure.class.isAssignableFrom(type) && 
/* 1399 */       !Structure.ByReference.class.isAssignableFrom(type)) {
/* 1400 */       return Structure.size(type, value);
/*      */     }
/*      */     try {
/* 1403 */       return getNativeSize(type);
/*      */     }
/* 1405 */     catch (IllegalArgumentException e) {
/* 1406 */       throw new IllegalArgumentException("The type \"" + type.getName() + "\" is not supported: " + e
/*      */           
/* 1408 */           .getMessage());
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
/*      */   
/*      */   public static int getNativeSize(Class<?> cls) {
/* 1421 */     if (NativeMapped.class.isAssignableFrom(cls)) {
/* 1422 */       cls = NativeMappedConverter.getInstance(cls).nativeType();
/*      */     }
/*      */     
/* 1425 */     if (cls == boolean.class || cls == Boolean.class) return 4; 
/* 1426 */     if (cls == byte.class || cls == Byte.class) return 1; 
/* 1427 */     if (cls == short.class || cls == Short.class) return 2; 
/* 1428 */     if (cls == char.class || cls == Character.class) return WCHAR_SIZE; 
/* 1429 */     if (cls == int.class || cls == Integer.class) return 4; 
/* 1430 */     if (cls == long.class || cls == Long.class) return 8; 
/* 1431 */     if (cls == float.class || cls == Float.class) return 4; 
/* 1432 */     if (cls == double.class || cls == Double.class) return 8; 
/* 1433 */     if (Structure.class.isAssignableFrom(cls)) {
/* 1434 */       if (Structure.ByValue.class.isAssignableFrom(cls)) {
/* 1435 */         return Structure.size((Class)cls);
/*      */       }
/* 1437 */       return POINTER_SIZE;
/*      */     } 
/* 1439 */     if (Pointer.class.isAssignableFrom(cls) || (Platform.HAS_BUFFERS && 
/* 1440 */       Buffers.isBuffer(cls)) || Callback.class
/* 1441 */       .isAssignableFrom(cls) || String.class == cls || WString.class == cls)
/*      */     {
/*      */       
/* 1444 */       return POINTER_SIZE;
/*      */     }
/* 1446 */     throw new IllegalArgumentException("Native size for type \"" + cls.getName() + "\" is unknown");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSupportedNativeType(Class<?> cls) {
/* 1455 */     if (Structure.class.isAssignableFrom(cls)) {
/* 1456 */       return true;
/*      */     }
/*      */     try {
/* 1459 */       return (getNativeSize(cls) != 0);
/*      */     }
/* 1461 */     catch (IllegalArgumentException e) {
/* 1462 */       return false;
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
/*      */   public static void setCallbackExceptionHandler(Callback.UncaughtExceptionHandler eh) {
/* 1474 */     callbackExceptionHandler = (eh == null) ? DEFAULT_HANDLER : eh;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Callback.UncaughtExceptionHandler getCallbackExceptionHandler() {
/* 1479 */     return callbackExceptionHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void register(String libName) {
/* 1489 */     register(findDirectMappedClass(getCallingClass()), libName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void register(NativeLibrary lib) {
/* 1499 */     register(findDirectMappedClass(getCallingClass()), lib);
/*      */   }
/*      */ 
/*      */   
/*      */   static Class<?> findDirectMappedClass(Class<?> cls) {
/* 1504 */     Method[] methods = cls.getDeclaredMethods();
/* 1505 */     for (Method m : methods) {
/* 1506 */       if ((m.getModifiers() & 0x100) != 0) {
/* 1507 */         return cls;
/*      */       }
/*      */     } 
/* 1510 */     int idx = cls.getName().lastIndexOf("$");
/* 1511 */     if (idx != -1) {
/* 1512 */       String name = cls.getName().substring(0, idx);
/*      */       try {
/* 1514 */         return findDirectMappedClass(Class.forName(name, true, cls.getClassLoader()));
/* 1515 */       } catch (ClassNotFoundException classNotFoundException) {}
/*      */     } 
/*      */ 
/*      */     
/* 1519 */     throw new IllegalArgumentException("Can't determine class with native methods from the current context (" + cls + ")");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Class<?> getCallingClass() {
/* 1531 */     Class<?>[] context = (new SecurityManager() { public Class<?>[] getClassContext() { return super.getClassContext(); } }).getClassContext();
/* 1532 */     if (context == null) {
/* 1533 */       throw new IllegalStateException("The SecurityManager implementation on this platform is broken; you must explicitly provide the class to register");
/*      */     }
/* 1535 */     if (context.length < 4) {
/* 1536 */       throw new IllegalStateException("This method must be called from the static initializer of a class");
/*      */     }
/* 1538 */     return context[3];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCallbackThreadInitializer(Callback cb, CallbackThreadInitializer initializer) {
/* 1548 */     CallbackReference.setCallbackThreadInitializer(cb, initializer);
/*      */   }
/*      */   
/* 1551 */   private static final Map<Class<?>, long[]> registeredClasses = (Map)new WeakHashMap<>();
/* 1552 */   private static final Map<Class<?>, NativeLibrary> registeredLibraries = new WeakHashMap<>(); static final int CB_HAS_INITIALIZER = 1; private static final int CVT_UNSUPPORTED = -1; private static final int CVT_DEFAULT = 0; private static final int CVT_POINTER = 1; private static final int CVT_STRING = 2; private static final int CVT_STRUCTURE = 3; private static final int CVT_STRUCTURE_BYVAL = 4; private static final int CVT_BUFFER = 5; private static final int CVT_ARRAY_BYTE = 6; private static final int CVT_ARRAY_SHORT = 7; private static final int CVT_ARRAY_CHAR = 8; private static final int CVT_ARRAY_INT = 9; private static final int CVT_ARRAY_LONG = 10; private static final int CVT_ARRAY_FLOAT = 11; private static final int CVT_ARRAY_DOUBLE = 12; private static final int CVT_ARRAY_BOOLEAN = 13; private static final int CVT_BOOLEAN = 14; private static final int CVT_CALLBACK = 15; private static final int CVT_FLOAT = 16; private static final int CVT_NATIVE_MAPPED = 17; private static final int CVT_NATIVE_MAPPED_STRING = 18; private static final int CVT_NATIVE_MAPPED_WSTRING = 19; private static final int CVT_WSTRING = 20; private static final int CVT_INTEGER_TYPE = 21; private static final int CVT_POINTER_TYPE = 22; private static final int CVT_TYPE_MAPPER = 23; private static final int CVT_TYPE_MAPPER_STRING = 24; private static final int CVT_TYPE_MAPPER_WSTRING = 25; private static final int CVT_OBJECT = 26; private static final int CVT_JNIENV = 27; private static final int CVT_SHORT = 28; private static final int CVT_BYTE = 29; static final int CB_OPTION_DIRECT = 1; static final int CB_OPTION_IN_DLL = 2;
/*      */   
/*      */   private static void unregisterAll() {
/* 1555 */     synchronized (registeredClasses) {
/* 1556 */       for (Map.Entry<Class<?>, long[]> e : registeredClasses.entrySet()) {
/* 1557 */         unregister(e.getKey(), e.getValue());
/*      */       }
/*      */       
/* 1560 */       registeredClasses.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unregister() {
/* 1569 */     unregister(findDirectMappedClass(getCallingClass()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unregister(Class<?> cls) {
/* 1577 */     synchronized (registeredClasses) {
/* 1578 */       long[] handles = registeredClasses.get(cls);
/* 1579 */       if (handles != null) {
/* 1580 */         unregister(cls, handles);
/* 1581 */         registeredClasses.remove(cls);
/* 1582 */         registeredLibraries.remove(cls);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean registered(Class<?> cls) {
/* 1592 */     synchronized (registeredClasses) {
/* 1593 */       return registeredClasses.containsKey(cls);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getSignature(Class<?> cls) {
/* 1601 */     if (cls.isArray()) {
/* 1602 */       return "[" + getSignature(cls.getComponentType());
/*      */     }
/* 1604 */     if (cls.isPrimitive()) {
/* 1605 */       if (cls == void.class) return "V"; 
/* 1606 */       if (cls == boolean.class) return "Z"; 
/* 1607 */       if (cls == byte.class) return "B"; 
/* 1608 */       if (cls == short.class) return "S"; 
/* 1609 */       if (cls == char.class) return "C"; 
/* 1610 */       if (cls == int.class) return "I"; 
/* 1611 */       if (cls == long.class) return "J"; 
/* 1612 */       if (cls == float.class) return "F"; 
/* 1613 */       if (cls == double.class) return "D"; 
/*      */     } 
/* 1615 */     return "L" + replace(".", "/", cls.getName()) + ";";
/*      */   }
/*      */ 
/*      */   
/*      */   static String replace(String s1, String s2, String str) {
/* 1620 */     StringBuilder buf = new StringBuilder();
/*      */     while (true) {
/* 1622 */       int idx = str.indexOf(s1);
/* 1623 */       if (idx == -1) {
/* 1624 */         buf.append(str);
/*      */         
/*      */         break;
/*      */       } 
/* 1628 */       buf.append(str.substring(0, idx));
/* 1629 */       buf.append(s2);
/* 1630 */       str = str.substring(idx + s1.length());
/*      */     } 
/*      */     
/* 1633 */     return buf.toString();
/*      */   }
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
/*      */   private static int getConversion(Class<?> type, TypeMapper mapper, boolean allowObjects) {
/* 1672 */     if (type == Void.class) type = void.class;
/*      */     
/* 1674 */     if (mapper != null) {
/* 1675 */       FromNativeConverter fromNative = mapper.getFromNativeConverter(type);
/* 1676 */       ToNativeConverter toNative = mapper.getToNativeConverter(type);
/* 1677 */       if (fromNative != null) {
/* 1678 */         Class<?> nativeType = fromNative.nativeType();
/* 1679 */         if (nativeType == String.class) {
/* 1680 */           return 24;
/*      */         }
/* 1682 */         if (nativeType == WString.class) {
/* 1683 */           return 25;
/*      */         }
/* 1685 */         return 23;
/*      */       } 
/* 1687 */       if (toNative != null) {
/* 1688 */         Class<?> nativeType = toNative.nativeType();
/* 1689 */         if (nativeType == String.class) {
/* 1690 */           return 24;
/*      */         }
/* 1692 */         if (nativeType == WString.class) {
/* 1693 */           return 25;
/*      */         }
/* 1695 */         return 23;
/*      */       } 
/*      */     } 
/*      */     
/* 1699 */     if (Pointer.class.isAssignableFrom(type)) {
/* 1700 */       return 1;
/*      */     }
/* 1702 */     if (String.class == type) {
/* 1703 */       return 2;
/*      */     }
/* 1705 */     if (WString.class.isAssignableFrom(type)) {
/* 1706 */       return 20;
/*      */     }
/* 1708 */     if (Platform.HAS_BUFFERS && Buffers.isBuffer(type)) {
/* 1709 */       return 5;
/*      */     }
/* 1711 */     if (Structure.class.isAssignableFrom(type)) {
/* 1712 */       if (Structure.ByValue.class.isAssignableFrom(type)) {
/* 1713 */         return 4;
/*      */       }
/* 1715 */       return 3;
/*      */     } 
/* 1717 */     if (type.isArray()) {
/* 1718 */       switch (type.getName().charAt(1)) { case 'Z':
/* 1719 */           return 13;
/* 1720 */         case 'B': return 6;
/* 1721 */         case 'S': return 7;
/* 1722 */         case 'C': return 8;
/* 1723 */         case 'I': return 9;
/* 1724 */         case 'J': return 10;
/* 1725 */         case 'F': return 11;
/* 1726 */         case 'D': return 12; }
/*      */ 
/*      */     
/*      */     }
/* 1730 */     if (type.isPrimitive()) {
/* 1731 */       return (type == boolean.class) ? 14 : 0;
/*      */     }
/* 1733 */     if (Callback.class.isAssignableFrom(type)) {
/* 1734 */       return 15;
/*      */     }
/* 1736 */     if (IntegerType.class.isAssignableFrom(type)) {
/* 1737 */       return 21;
/*      */     }
/* 1739 */     if (PointerType.class.isAssignableFrom(type)) {
/* 1740 */       return 22;
/*      */     }
/* 1742 */     if (NativeMapped.class.isAssignableFrom(type)) {
/* 1743 */       Class<?> nativeType = NativeMappedConverter.getInstance(type).nativeType();
/* 1744 */       if (nativeType == String.class) {
/* 1745 */         return 18;
/*      */       }
/* 1747 */       if (nativeType == WString.class) {
/* 1748 */         return 19;
/*      */       }
/* 1750 */       return 17;
/*      */     } 
/* 1752 */     if (JNIEnv.class == type) {
/* 1753 */       return 27;
/*      */     }
/* 1755 */     return allowObjects ? 26 : -1;
/*      */   }
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
/*      */   public static void register(Class<?> cls, String libName) {
/* 1770 */     NativeLibrary library = NativeLibrary.getInstance(libName, Collections.singletonMap("classloader", cls.getClassLoader()));
/* 1771 */     register(cls, library);
/*      */   }
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
/*      */   public static void register(Class<?> cls, NativeLibrary lib) {
/* 1784 */     Method[] methods = cls.getDeclaredMethods();
/* 1785 */     List<Method> mlist = new ArrayList<>();
/* 1786 */     Map<String, ?> options = lib.getOptions();
/* 1787 */     TypeMapper mapper = (TypeMapper)options.get("type-mapper");
/* 1788 */     boolean allowObjects = Boolean.TRUE.equals(options.get("allow-objects"));
/* 1789 */     options = cacheOptions(cls, options, null);
/*      */     
/* 1791 */     for (Method m : methods) {
/* 1792 */       if ((m.getModifiers() & 0x100) != 0) {
/* 1793 */         mlist.add(m);
/*      */       }
/*      */     } 
/*      */     
/* 1797 */     long[] handles = new long[mlist.size()];
/* 1798 */     for (int i = 0; i < handles.length; i++) {
/* 1799 */       long rtype, closure_rtype; Method method = mlist.get(i);
/* 1800 */       String sig = "(";
/* 1801 */       Class<?> rclass = method.getReturnType();
/*      */       
/* 1803 */       Class<?>[] ptypes = method.getParameterTypes();
/* 1804 */       long[] atypes = new long[ptypes.length];
/* 1805 */       long[] closure_atypes = new long[ptypes.length];
/* 1806 */       int[] cvt = new int[ptypes.length];
/* 1807 */       ToNativeConverter[] toNative = new ToNativeConverter[ptypes.length];
/* 1808 */       FromNativeConverter fromNative = null;
/* 1809 */       int rcvt = getConversion(rclass, mapper, allowObjects);
/* 1810 */       boolean throwLastError = false;
/* 1811 */       switch (rcvt) {
/*      */         case -1:
/* 1813 */           throw new IllegalArgumentException(rclass + " is not a supported return type (in method " + method.getName() + " in " + cls + ")");
/*      */         case 23:
/*      */         case 24:
/*      */         case 25:
/* 1817 */           fromNative = mapper.getFromNativeConverter(rclass);
/*      */ 
/*      */ 
/*      */           
/* 1821 */           closure_rtype = (Structure.FFIType.get(rclass.isPrimitive() ? rclass : Pointer.class).getPointer()).peer;
/* 1822 */           rtype = (Structure.FFIType.get(fromNative.nativeType()).getPointer()).peer;
/*      */           break;
/*      */         case 17:
/*      */         case 18:
/*      */         case 19:
/*      */         case 21:
/*      */         case 22:
/* 1829 */           closure_rtype = (Structure.FFIType.get(Pointer.class).getPointer()).peer;
/* 1830 */           rtype = (Structure.FFIType.get(NativeMappedConverter.getInstance(rclass).nativeType()).getPointer()).peer;
/*      */           break;
/*      */         case 3:
/*      */         case 26:
/* 1834 */           closure_rtype = rtype = (Structure.FFIType.get(Pointer.class).getPointer()).peer;
/*      */         
/*      */         case 4:
/* 1837 */           closure_rtype = (Structure.FFIType.get(Pointer.class).getPointer()).peer;
/* 1838 */           rtype = (Structure.FFIType.get(rclass).getPointer()).peer;
/*      */           break;
/*      */         default:
/* 1841 */           closure_rtype = rtype = (Structure.FFIType.get(rclass).getPointer()).peer;
/*      */           break;
/*      */       } 
/* 1844 */       for (int t = 0; t < ptypes.length; t++) {
/* 1845 */         Class<?> type = ptypes[t];
/* 1846 */         sig = sig + getSignature(type);
/* 1847 */         int conversionType = getConversion(type, mapper, allowObjects);
/* 1848 */         cvt[t] = conversionType;
/* 1849 */         if (conversionType == -1) {
/* 1850 */           throw new IllegalArgumentException(type + " is not a supported argument type (in method " + method.getName() + " in " + cls + ")");
/*      */         }
/* 1852 */         if (conversionType == 17 || conversionType == 18 || conversionType == 19 || conversionType == 21) {
/*      */ 
/*      */ 
/*      */           
/* 1856 */           type = NativeMappedConverter.getInstance(type).nativeType();
/* 1857 */         } else if (conversionType == 23 || conversionType == 24 || conversionType == 25) {
/*      */ 
/*      */           
/* 1860 */           toNative[t] = mapper.getToNativeConverter(type);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1866 */         switch (conversionType) {
/*      */           case 4:
/*      */           case 17:
/*      */           case 18:
/*      */           case 19:
/*      */           case 21:
/*      */           case 22:
/* 1873 */             atypes[t] = (Structure.FFIType.get(type).getPointer()).peer;
/* 1874 */             closure_atypes[t] = (Structure.FFIType.get(Pointer.class).getPointer()).peer;
/*      */             break;
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/* 1879 */             closure_atypes[t] = (Structure.FFIType.get(type.isPrimitive() ? type : Pointer.class).getPointer()).peer;
/* 1880 */             atypes[t] = (Structure.FFIType.get(toNative[t].nativeType()).getPointer()).peer;
/*      */             break;
/*      */           case 0:
/* 1883 */             atypes[t] = (Structure.FFIType.get(type).getPointer()).peer; closure_atypes[t] = (Structure.FFIType.get(type).getPointer()).peer;
/*      */           
/*      */           default:
/* 1886 */             atypes[t] = (Structure.FFIType.get(Pointer.class).getPointer()).peer; closure_atypes[t] = (Structure.FFIType.get(Pointer.class).getPointer()).peer; break;
/*      */         } 
/*      */       } 
/* 1889 */       sig = sig + ")";
/* 1890 */       sig = sig + getSignature(rclass);
/*      */       
/* 1892 */       Class<?>[] etypes = method.getExceptionTypes();
/* 1893 */       for (int e = 0; e < etypes.length; e++) {
/* 1894 */         if (LastErrorException.class.isAssignableFrom(etypes[e])) {
/* 1895 */           throwLastError = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1900 */       Function f = lib.getFunction(method.getName(), method);
/*      */       try {
/* 1902 */         handles[i] = registerMethod(cls, method.getName(), sig, cvt, closure_atypes, atypes, rcvt, closure_rtype, rtype, method, f.peer, f
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1907 */             .getCallingConvention(), throwLastError, toNative, fromNative, f.encoding);
/*      */ 
/*      */       
/*      */       }
/* 1911 */       catch (NoSuchMethodError noSuchMethodError) {
/* 1912 */         throw new UnsatisfiedLinkError("No method " + method.getName() + " with signature " + sig + " in " + cls);
/*      */       } 
/*      */     } 
/* 1915 */     synchronized (registeredClasses) {
/* 1916 */       registeredClasses.put(cls, handles);
/* 1917 */       registeredLibraries.put(cls, lib);
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
/*      */   
/*      */   public static NativeLibrary getNativeLibrary(Library library) {
/* 1930 */     if (library == null) {
/* 1931 */       throw new IllegalArgumentException("null passed to getNativeLibrary");
/*      */     }
/* 1933 */     if (!Proxy.isProxyClass(library.getClass())) {
/* 1934 */       throw new IllegalArgumentException("library object passed to getNativeLibrary in not a proxy");
/*      */     }
/* 1936 */     InvocationHandler handler = Proxy.getInvocationHandler(library);
/* 1937 */     if (!(handler instanceof Library.Handler)) {
/* 1938 */       throw new IllegalArgumentException("Object is not a properly initialized Library interface instance");
/*      */     }
/* 1940 */     return ((Library.Handler)handler).getNativeLibrary();
/*      */   }
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
/*      */   public static NativeLibrary getNativeLibrary(Class<?> cls) {
/* 1953 */     if (cls == null) {
/* 1954 */       throw new IllegalArgumentException("null passed to getNativeLibrary");
/*      */     }
/* 1956 */     Class<?> mappedClass = findDirectMappedClass(cls);
/* 1957 */     synchronized (registeredClasses) {
/* 1958 */       NativeLibrary nativeLibrary = registeredLibraries.get(mappedClass);
/* 1959 */       if (nativeLibrary == null) {
/* 1960 */         throw new IllegalArgumentException("Class " + cls.getName() + " is not currently registered");
/*      */       }
/* 1962 */       return nativeLibrary;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<String, Object> cacheOptions(Class<?> cls, Map<String, ?> options, Object proxy) {
/* 1971 */     Map<String, Object> libOptions = new HashMap<>(options);
/* 1972 */     libOptions.put("enclosing-library", cls);
/* 1973 */     typeOptions.put(cls, libOptions);
/* 1974 */     if (proxy != null) {
/* 1975 */       libraries.put(cls, new WeakReference(proxy));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1981 */     if (!cls.isInterface() && Library.class
/* 1982 */       .isAssignableFrom(cls)) {
/* 1983 */       Class<?>[] ifaces = cls.getInterfaces();
/* 1984 */       for (Class<?> ifc : ifaces) {
/* 1985 */         if (Library.class.isAssignableFrom(ifc)) {
/* 1986 */           cacheOptions(ifc, libOptions, proxy);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1991 */     return libOptions;
/*      */   }
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
/*      */   private static NativeMapped fromNative(Class<?> cls, Object value) {
/* 2015 */     return (NativeMapped)NativeMappedConverter.getInstance(cls).fromNative(value, new FromNativeContext(cls));
/*      */   }
/*      */   
/*      */   private static NativeMapped fromNative(Method m, Object value) {
/* 2019 */     Class<?> cls = m.getReturnType();
/* 2020 */     return (NativeMapped)NativeMappedConverter.getInstance(cls).fromNative(value, new MethodResultContext(cls, null, null, m));
/*      */   }
/*      */   
/*      */   private static Class<?> nativeType(Class<?> cls) {
/* 2024 */     return NativeMappedConverter.getInstance(cls).nativeType();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Object toNative(ToNativeConverter cvt, Object o) {
/* 2030 */     return cvt.toNative(o, new ToNativeContext());
/*      */   }
/*      */   
/*      */   private static Object fromNative(FromNativeConverter cvt, Object o, Method m) {
/* 2034 */     return cvt.fromNative(o, new MethodResultContext(m.getReturnType(), null, null, m));
/*      */   }
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
/*      */   public static void main(String[] args) {
/* 2053 */     String DEFAULT_TITLE = "Java Native Access (JNA)";
/* 2054 */     String DEFAULT_VERSION = "5.15.0";
/* 2055 */     String DEFAULT_BUILD = "5.15.0 (package information missing)";
/* 2056 */     Package pkg = Native.class.getPackage();
/*      */     
/* 2058 */     String title = (pkg != null) ? pkg.getSpecificationTitle() : "Java Native Access (JNA)";
/* 2059 */     if (title == null) title = "Java Native Access (JNA)";
/*      */     
/* 2061 */     String version = (pkg != null) ? pkg.getSpecificationVersion() : "5.15.0";
/* 2062 */     if (version == null) version = "5.15.0"; 
/* 2063 */     title = title + " API Version " + version;
/* 2064 */     System.out.println(title);
/*      */     
/* 2066 */     version = (pkg != null) ? pkg.getImplementationVersion() : "5.15.0 (package information missing)";
/* 2067 */     if (version == null) version = "5.15.0 (package information missing)"; 
/* 2068 */     System.out.println("Version: " + version);
/* 2069 */     System.out.println(" Native: " + getNativeVersion() + " (" + 
/* 2070 */         getAPIChecksum() + ")");
/* 2071 */     System.out.println(" Prefix: " + Platform.RESOURCE_PREFIX);
/*      */   }
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
/*      */   static Structure invokeStructure(Function function, long fp, int callFlags, Object[] args, Structure s) {
/* 2196 */     invokeStructure(function, fp, callFlags, args, (s.getPointer()).peer, 
/* 2197 */         (s.getTypeInfo()).peer);
/* 2198 */     return s;
/*      */   }
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
/*      */   static long open(String name) {
/* 2216 */     return open(name, -1);
/*      */   }
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
/*      */   static Pointer getPointer(long addr) {
/* 2305 */     long peer = _getPointer(addr);
/* 2306 */     return (peer == 0L) ? null : new Pointer(peer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getString(Pointer pointer, long offset) {
/* 2314 */     return getString(pointer, offset, getDefaultStringEncoding());
/*      */   }
/*      */   
/*      */   static String getString(Pointer pointer, long offset, String encoding) {
/* 2318 */     byte[] data = getStringBytes(pointer, pointer.peer, offset);
/* 2319 */     if (encoding != null) {
/*      */       try {
/* 2321 */         return new String(data, encoding);
/*      */       }
/* 2323 */       catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*      */     }
/*      */     
/* 2326 */     return new String(data);
/*      */   }
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
/* 2368 */   private static final ThreadLocal<Memory> nativeThreadTerminationFlag = new ThreadLocal<Memory>()
/*      */     {
/*      */       protected Memory initialValue()
/*      */       {
/* 2372 */         Memory m = new Memory(4L);
/* 2373 */         m.clear();
/* 2374 */         return m;
/*      */       }
/*      */     };
/* 2377 */   private static final Map<Thread, Pointer> nativeThreads = Collections.synchronizedMap(new WeakHashMap<>()); private static native void initIDs(); public static synchronized native void setProtected(boolean paramBoolean); public static synchronized native boolean isProtected(); static native long getWindowHandle0(Component paramComponent); private static native long _getDirectBufferPointer(Buffer paramBuffer); private static native int sizeof(int paramInt);
/*      */   private static native String getNativeVersion();
/*      */   private static native String getAPIChecksum();
/*      */   public static native int getLastError();
/*      */   public static native void setLastError(int paramInt);
/*      */   private static native void unregister(Class<?> paramClass, long[] paramArrayOflong);
/*      */   private static native long registerMethod(Class<?> paramClass, String paramString1, String paramString2, int[] paramArrayOfint, long[] paramArrayOflong1, long[] paramArrayOflong2, int paramInt1, long paramLong1, long paramLong2, Method paramMethod, long paramLong3, int paramInt2, boolean paramBoolean, ToNativeConverter[] paramArrayOfToNativeConverter, FromNativeConverter paramFromNativeConverter, String paramString3);
/*      */   public static native long ffi_prep_cif(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
/*      */   public static native void ffi_call(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*      */   public static native long ffi_prep_closure(long paramLong, ffi_callback paramffi_callback);
/*      */   public static native void ffi_free_closure(long paramLong);
/*      */   static native int initialize_ffi_type(long paramLong);
/*      */   static synchronized native void freeNativeCallback(long paramLong);
/*      */   public static void detach(boolean detach) {
/* 2391 */     Thread thread = Thread.currentThread();
/* 2392 */     if (detach) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2399 */       nativeThreads.remove(thread);
/* 2400 */       Pointer p = nativeThreadTerminationFlag.get();
/* 2401 */       setDetachState(true, 0L);
/*      */     
/*      */     }
/* 2404 */     else if (!nativeThreads.containsKey(thread)) {
/* 2405 */       Pointer p = nativeThreadTerminationFlag.get();
/* 2406 */       nativeThreads.put(thread, p);
/* 2407 */       setDetachState(false, p.peer);
/*      */     } 
/*      */   } static synchronized native long createNativeCallback(Callback paramCallback, Method paramMethod, Class<?>[] paramArrayOfClass, Class<?> paramClass, int paramInt1, int paramInt2, String paramString); static native int invokeInt(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); static native long invokeLong(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); static native void invokeVoid(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); static native float invokeFloat(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); static native double invokeDouble(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); static native long invokePointer(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); private static native void invokeStructure(Function paramFunction, long paramLong1, int paramInt, Object[] paramArrayOfObject, long paramLong2, long paramLong3); static native Object invokeObject(Function paramFunction, long paramLong, int paramInt, Object[] paramArrayOfObject); static native long open(String paramString, int paramInt); static native void close(long paramLong); static native long findSymbol(long paramLong, String paramString); static native long indexOf(Pointer paramPointer, long paramLong1, long paramLong2, byte paramByte); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, byte[] paramArrayOfbyte, int paramInt1, int paramInt2); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, short[] paramArrayOfshort, int paramInt1, int paramInt2); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, char[] paramArrayOfchar, int paramInt1, int paramInt2); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, int[] paramArrayOfint, int paramInt1, int paramInt2); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, long[] paramArrayOflong, int paramInt1, int paramInt2); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, float[] paramArrayOffloat, int paramInt1, int paramInt2); static native void read(Pointer paramPointer, long paramLong1, long paramLong2, double[] paramArrayOfdouble, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, byte[] paramArrayOfbyte, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, short[] paramArrayOfshort, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, char[] paramArrayOfchar, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, int[] paramArrayOfint, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, long[] paramArrayOflong, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, float[] paramArrayOffloat, int paramInt1, int paramInt2); static native void write(Pointer paramPointer, long paramLong1, long paramLong2, double[] paramArrayOfdouble, int paramInt1, int paramInt2); static native byte getByte(Pointer paramPointer, long paramLong1, long paramLong2); static native char getChar(Pointer paramPointer, long paramLong1, long paramLong2); static native short getShort(Pointer paramPointer, long paramLong1, long paramLong2); static native int getInt(Pointer paramPointer, long paramLong1, long paramLong2); static native long getLong(Pointer paramPointer, long paramLong1, long paramLong2);
/*      */   static native float getFloat(Pointer paramPointer, long paramLong1, long paramLong2);
/*      */   static native double getDouble(Pointer paramPointer, long paramLong1, long paramLong2);
/*      */   static Pointer getTerminationFlag(Thread t) {
/* 2413 */     return nativeThreads.get(t);
/*      */   } private static native long _getPointer(long paramLong); static native String getWideString(Pointer paramPointer, long paramLong1, long paramLong2); static native byte[] getStringBytes(Pointer paramPointer, long paramLong1, long paramLong2); static native void setMemory(Pointer paramPointer, long paramLong1, long paramLong2, long paramLong3, byte paramByte); static native void setByte(Pointer paramPointer, long paramLong1, long paramLong2, byte paramByte); static native void setShort(Pointer paramPointer, long paramLong1, long paramLong2, short paramShort); static native void setChar(Pointer paramPointer, long paramLong1, long paramLong2, char paramChar); static native void setInt(Pointer paramPointer, long paramLong1, long paramLong2, int paramInt); static native void setLong(Pointer paramPointer, long paramLong1, long paramLong2, long paramLong3); static native void setFloat(Pointer paramPointer, long paramLong1, long paramLong2, float paramFloat); static native void setDouble(Pointer paramPointer, long paramLong1, long paramLong2, double paramDouble); static native void setPointer(Pointer paramPointer, long paramLong1, long paramLong2, long paramLong3); static native void setWideString(Pointer paramPointer, long paramLong1, long paramLong2, String paramString);
/*      */   static native ByteBuffer getDirectByteBuffer(Pointer paramPointer, long paramLong1, long paramLong2, long paramLong3);
/*      */   public static native long malloc(long paramLong);
/*      */   public static native void free(long paramLong);
/*      */   private static native void setDetachState(boolean paramBoolean, long paramLong);
/*      */   private static class Buffers { static boolean isBuffer(Class<?> cls) {
/* 2420 */       return Buffer.class.isAssignableFrom(cls);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class AWT
/*      */   {
/*      */     static long getWindowID(Window w) throws HeadlessException {
/* 2429 */       return getComponentID(w);
/*      */     }
/*      */ 
/*      */     
/*      */     static long getComponentID(Object o) throws HeadlessException {
/* 2434 */       if (GraphicsEnvironment.isHeadless()) {
/* 2435 */         throw new HeadlessException("No native windows when headless");
/*      */       }
/* 2437 */       Component c = (Component)o;
/* 2438 */       if (c.isLightweight()) {
/* 2439 */         throw new IllegalArgumentException("Component must be heavyweight");
/*      */       }
/* 2441 */       if (!c.isDisplayable()) {
/* 2442 */         throw new IllegalStateException("Component must be displayable");
/*      */       }
/* 2444 */       if (Platform.isX11() && 
/* 2445 */         System.getProperty("java.version").startsWith("1.4") && 
/* 2446 */         !c.isVisible()) {
/* 2447 */         throw new IllegalStateException("Component must be visible");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2453 */       return Native.getWindowHandle0(c);
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface ffi_callback {
/*      */     void invoke(long param1Long1, long param1Long2, long param1Long3);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\Native.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */