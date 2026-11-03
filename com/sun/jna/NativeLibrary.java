/*      */ package com.sun.jna;
/*      */ 
/*      */ import com.sun.jna.internal.Cleaner;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.concurrent.ConcurrentHashMap;
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
/*      */ public class NativeLibrary
/*      */   implements Closeable
/*      */ {
/*   89 */   private static final Logger LOG = Logger.getLogger(NativeLibrary.class.getName());
/*   90 */   private static final Level DEBUG_LOAD_LEVEL = Native.DEBUG_LOAD ? Level.INFO : Level.FINE;
/*   91 */   private static final SymbolProvider NATIVE_SYMBOL_PROVIDER = new SymbolProvider()
/*      */     {
/*      */       public long getSymbolAddress(long handle, String name, SymbolProvider parent) {
/*   94 */         return Native.findSymbol(handle, name);
/*      */       }
/*      */     };
/*      */   
/*      */   private final Cleaner.Cleanable cleanable;
/*      */   private volatile long handle;
/*      */   private final String libraryName;
/*      */   private final String libraryPath;
/*  102 */   private final Map<String, Function> functions = new HashMap<>();
/*      */   
/*      */   private final SymbolProvider symbolProvider;
/*      */   private final int callFlags;
/*      */   private final String encoding;
/*      */   private final Map<String, ?> options;
/*  108 */   private static final Map<String, Reference<NativeLibrary>> libraries = new HashMap<>();
/*      */   
/*  110 */   private static final Map<String, List<String>> searchPaths = new ConcurrentHashMap<>();
/*  111 */   private static final LinkedHashSet<String> librarySearchPath = new LinkedHashSet<>();
/*      */   private static final int DEFAULT_OPEN_OPTIONS = -1;
/*      */   
/*      */   static {
/*  115 */     if (Native.POINTER_SIZE == 0)
/*  116 */       throw new Error("Native library not initialized"); 
/*      */   }
/*      */   
/*      */   private static String functionKey(String name, int flags, String encoding) {
/*  120 */     return name + "|" + flags + "|" + encoding;
/*      */   }
/*      */ 
/*      */   
/*      */   private NativeLibrary(String libraryName, String libraryPath, long handle, Map<String, ?> options) {
/*  125 */     this.libraryName = getLibraryName(libraryName);
/*  126 */     this.libraryPath = libraryPath;
/*  127 */     this.handle = handle;
/*  128 */     this.cleanable = Cleaner.getCleaner().register(this, new NativeLibraryDisposer(handle));
/*  129 */     Object option = options.get("calling-convention");
/*  130 */     int callingConvention = (option instanceof Number) ? ((Number)option).intValue() : 0;
/*  131 */     this.callFlags = callingConvention;
/*  132 */     this.options = options;
/*  133 */     SymbolProvider optionSymbolProvider = (SymbolProvider)options.get("symbol-provider");
/*  134 */     if (optionSymbolProvider == null) {
/*  135 */       this.symbolProvider = NATIVE_SYMBOL_PROVIDER;
/*      */     } else {
/*  137 */       this.symbolProvider = optionSymbolProvider;
/*      */     } 
/*      */     
/*  140 */     String encodingValue = (String)options.get("string-encoding");
/*  141 */     if (encodingValue == null) {
/*  142 */       encodingValue = Native.getDefaultStringEncoding();
/*      */     }
/*  144 */     this.encoding = encodingValue;
/*      */ 
/*      */ 
/*      */     
/*  148 */     if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) {
/*  149 */       synchronized (this.functions) {
/*  150 */         Function f = new Function(this, "GetLastError", 63, this.encoding)
/*      */           {
/*      */             Object invoke(Object[] args, Class<?> returnType, boolean b, int fixedArgs) {
/*  153 */               return Integer.valueOf(Native.getLastError());
/*      */             }
/*      */ 
/*      */             
/*      */             Object invoke(Method invokingMethod, Class<?>[] paramTypes, Class<?> returnType, Object[] inArgs, Map<String, ?> options) {
/*  158 */               return Integer.valueOf(Native.getLastError());
/*      */             }
/*      */           };
/*  161 */         this.functions.put(functionKey("GetLastError", this.callFlags, this.encoding), f);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static int openFlags(Map<String, ?> options) {
/*  168 */     Object opt = options.get("open-flags");
/*  169 */     if (opt instanceof Number) {
/*  170 */       return ((Number)opt).intValue();
/*      */     }
/*  172 */     return -1;
/*      */   }
/*      */   
/*      */   private static NativeLibrary loadLibrary(String libraryName, Map<String, ?> options) {
/*  176 */     LOG.log(DEBUG_LOAD_LEVEL, "Looking for library '" + libraryName + "'");
/*      */     
/*  178 */     List<Throwable> exceptions = new ArrayList<>();
/*  179 */     boolean isAbsolutePath = (new File(libraryName)).isAbsolute();
/*  180 */     LinkedHashSet<String> searchPath = new LinkedHashSet<>();
/*  181 */     int openFlags = openFlags(options);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  186 */     List<String> customPaths = searchPaths.get(libraryName);
/*  187 */     if (customPaths != null) {
/*  188 */       synchronized (customPaths) {
/*  189 */         searchPath.addAll(customPaths);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  195 */     String webstartPath = Native.getWebStartLibraryPath(libraryName);
/*  196 */     if (webstartPath != null) {
/*  197 */       LOG.log(DEBUG_LOAD_LEVEL, "Adding web start path " + webstartPath);
/*  198 */       searchPath.add(webstartPath);
/*      */     } 
/*      */     
/*  201 */     LOG.log(DEBUG_LOAD_LEVEL, "Adding paths from jna.library.path: " + System.getProperty("jna.library.path"));
/*      */     
/*  203 */     searchPath.addAll(initPaths("jna.library.path"));
/*  204 */     String libraryPath = findLibraryPath(libraryName, searchPath);
/*  205 */     long handle = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  212 */       LOG.log(DEBUG_LOAD_LEVEL, "Trying " + libraryPath);
/*  213 */       handle = Native.open(libraryPath, openFlags);
/*  214 */     } catch (UnsatisfiedLinkError e) {
/*      */       
/*  216 */       LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + e.getMessage());
/*  217 */       LOG.log(DEBUG_LOAD_LEVEL, "Adding system paths: " + librarySearchPath);
/*  218 */       exceptions.add(e);
/*  219 */       searchPath.addAll(librarySearchPath);
/*      */     } 
/*      */     
/*      */     try {
/*  223 */       if (handle == 0L) {
/*  224 */         libraryPath = findLibraryPath(libraryName, searchPath);
/*  225 */         LOG.log(DEBUG_LOAD_LEVEL, "Trying " + libraryPath);
/*  226 */         handle = Native.open(libraryPath, openFlags);
/*  227 */         if (handle == 0L) {
/*  228 */           throw new UnsatisfiedLinkError("Failed to load library '" + libraryName + "'");
/*      */         }
/*      */       } 
/*  231 */     } catch (UnsatisfiedLinkError ule) {
/*  232 */       LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + ule.getMessage());
/*  233 */       exceptions.add(ule);
/*      */ 
/*      */ 
/*      */       
/*  237 */       if (Platform.isAndroid()) {
/*      */         try {
/*  239 */           LOG.log(DEBUG_LOAD_LEVEL, "Preload (via System.loadLibrary) " + libraryName);
/*  240 */           System.loadLibrary(libraryName);
/*  241 */           handle = Native.open(libraryPath, openFlags);
/*      */         }
/*  243 */         catch (UnsatisfiedLinkError e2) {
/*  244 */           LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + e2.getMessage());
/*  245 */           exceptions.add(e2);
/*      */         }
/*      */       
/*  248 */       } else if (Platform.isLinux() || Platform.isFreeBSD()) {
/*      */ 
/*      */ 
/*      */         
/*  252 */         LOG.log(DEBUG_LOAD_LEVEL, "Looking for version variants");
/*  253 */         libraryPath = matchLibrary(libraryName, searchPath);
/*  254 */         if (libraryPath != null) {
/*  255 */           LOG.log(DEBUG_LOAD_LEVEL, "Trying " + libraryPath);
/*      */           try {
/*  257 */             handle = Native.open(libraryPath, openFlags);
/*      */           }
/*  259 */           catch (UnsatisfiedLinkError e2) {
/*  260 */             LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + e2.getMessage());
/*  261 */             exceptions.add(e2);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*  266 */       } else if (Platform.isMac() && !libraryName.endsWith(".dylib")) {
/*  267 */         for (String frameworkName : matchFramework(libraryName)) {
/*      */           try {
/*  269 */             LOG.log(DEBUG_LOAD_LEVEL, "Trying " + frameworkName);
/*  270 */             handle = Native.open(frameworkName, openFlags);
/*      */             
/*      */             break;
/*  273 */           } catch (UnsatisfiedLinkError e2) {
/*  274 */             LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + e2.getMessage());
/*  275 */             exceptions.add(e2);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*  280 */       } else if (Platform.isWindows() && !isAbsolutePath) {
/*  281 */         LOG.log(DEBUG_LOAD_LEVEL, "Looking for lib- prefix");
/*  282 */         libraryPath = findLibraryPath("lib" + libraryName, searchPath);
/*  283 */         if (libraryPath != null) {
/*  284 */           LOG.log(DEBUG_LOAD_LEVEL, "Trying " + libraryPath);
/*      */           try {
/*  286 */             handle = Native.open(libraryPath, openFlags);
/*  287 */           } catch (UnsatisfiedLinkError e2) {
/*  288 */             LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + e2.getMessage());
/*  289 */             exceptions.add(e2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  295 */       if (handle == 0L) {
/*      */         try {
/*  297 */           File embedded = Native.extractFromResourcePath(libraryName, (ClassLoader)options.get("classloader"));
/*  298 */           if (embedded != null) {
/*      */             try {
/*  300 */               handle = Native.open(embedded.getAbsolutePath(), openFlags);
/*  301 */               libraryPath = embedded.getAbsolutePath();
/*      */             } finally {
/*      */               
/*  304 */               if (Native.isUnpacked(embedded)) {
/*  305 */                 Native.deleteLibrary(embedded);
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*  310 */         } catch (IOException e2) {
/*  311 */           LOG.log(DEBUG_LOAD_LEVEL, "Loading failed with message: " + e2.getMessage());
/*  312 */           exceptions.add(e2);
/*      */         } 
/*      */       }
/*      */       
/*  316 */       if (handle == 0L) {
/*  317 */         StringBuilder sb = new StringBuilder();
/*  318 */         sb.append("Unable to load library '");
/*  319 */         sb.append(libraryName);
/*  320 */         sb.append("':");
/*  321 */         for (Throwable t : exceptions) {
/*  322 */           sb.append("\n");
/*  323 */           sb.append(t.getMessage());
/*      */         } 
/*  325 */         UnsatisfiedLinkError res = new UnsatisfiedLinkError(sb.toString());
/*  326 */         for (Throwable t : exceptions) {
/*  327 */           addSuppressedReflected(res, t);
/*      */         }
/*  329 */         throw res;
/*      */       } 
/*      */     } 
/*      */     
/*  333 */     LOG.log(DEBUG_LOAD_LEVEL, "Found library '" + libraryName + "' at " + libraryPath);
/*  334 */     return new NativeLibrary(libraryName, libraryPath, handle, options);
/*      */   }
/*      */   
/*  337 */   private static Method addSuppressedMethod = null;
/*      */   
/*      */   static { try {
/*  340 */       addSuppressedMethod = Throwable.class.getMethod("addSuppressed", new Class[] { Throwable.class });
/*  341 */     } catch (NoSuchMethodException noSuchMethodException) {
/*      */     
/*  343 */     } catch (SecurityException ex) {
/*  344 */       Logger.getLogger(NativeLibrary.class.getName()).log(Level.SEVERE, "Failed to initialize 'addSuppressed' method", ex);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  906 */     String webstartPath = Native.getWebStartLibraryPath("jnidispatch");
/*  907 */     if (webstartPath != null) {
/*  908 */       librarySearchPath.add(webstartPath);
/*      */     }
/*  910 */     if (System.getProperty("jna.platform.library.path") == null && 
/*  911 */       !Platform.isWindows()) {
/*      */       
/*  913 */       String platformPath = "";
/*  914 */       String sep = "";
/*  915 */       String archPath = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  928 */       if (Platform.isLinux() || Platform.isSolaris() || 
/*  929 */         Platform.isFreeBSD() || Platform.iskFreeBSD())
/*      */       {
/*  931 */         archPath = (Platform.isSolaris() ? "/" : "") + (Native.POINTER_SIZE * 8);
/*      */       }
/*  933 */       String[] paths = { "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  944 */       if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
/*  945 */         String multiArchPath = getMultiArchPath();
/*      */ 
/*      */         
/*  948 */         paths = new String[] { "/usr/lib/" + multiArchPath, "/lib/" + multiArchPath, "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib" };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  964 */       if (Platform.isLinux()) {
/*  965 */         ArrayList<String> ldPaths = getLinuxLdPaths();
/*      */         
/*  967 */         for (int j = paths.length - 1; 0 <= j; j--) {
/*  968 */           int found = ldPaths.indexOf(paths[j]);
/*  969 */           if (found != -1) {
/*  970 */             ldPaths.remove(found);
/*      */           }
/*  972 */           ldPaths.add(0, paths[j]);
/*      */         } 
/*  974 */         paths = ldPaths.<String>toArray(new String[0]);
/*      */       } 
/*      */       
/*  977 */       for (int i = 0; i < paths.length; i++) {
/*  978 */         File dir = new File(paths[i]);
/*  979 */         if (dir.exists() && dir.isDirectory()) {
/*  980 */           platformPath = platformPath + sep + paths[i];
/*  981 */           sep = File.pathSeparator;
/*      */         } 
/*      */       } 
/*  984 */       if (!"".equals(platformPath)) {
/*  985 */         System.setProperty("jna.platform.library.path", platformPath);
/*      */       }
/*      */     } 
/*  988 */     librarySearchPath.addAll(initPaths("jna.platform.library.path")); } private static void addSuppressedReflected(Throwable target, Throwable suppressed) { if (addSuppressedMethod == null) return;  try { addSuppressedMethod.invoke(target, new Object[] { suppressed }); } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException ex) { throw new RuntimeException("Failed to call addSuppressedMethod", ex); }  } static String[] matchFramework(String libraryName) { Set<String> paths = new LinkedHashSet<>(); File framework = new File(libraryName); if (framework.isAbsolute()) { if (libraryName.contains(".framework")) { if (framework.exists()) return new String[] { framework.getAbsolutePath() };  paths.add(framework.getAbsolutePath()); } else { framework = new File(new File(framework.getParentFile(), framework.getName() + ".framework"), framework.getName()); if (framework.exists()) return new String[] { framework.getAbsolutePath() };  paths.add(framework.getAbsolutePath()); }  } else { String[] PREFIXES = { System.getProperty("user.home"), "", "/System" }; String suffix = !libraryName.contains(".framework") ? (libraryName + ".framework/" + libraryName) : libraryName; for (String prefix : PREFIXES) { framework = new File(prefix + "/Library/Frameworks/" + suffix); if (framework.exists()) return new String[] { framework.getAbsolutePath() };  paths.add(framework.getAbsolutePath()); }  }  return paths.<String>toArray(new String[0]); } private String getLibraryName(String libraryName) { String simplified = libraryName; String BASE = "---"; String template = mapSharedLibraryName("---"); int prefixEnd = template.indexOf("---"); if (prefixEnd > 0 && simplified.startsWith(template.substring(0, prefixEnd))) simplified = simplified.substring(prefixEnd);  String suffix = template.substring(prefixEnd + "---".length()); int suffixStart = simplified.indexOf(suffix); if (suffixStart != -1) simplified = simplified.substring(0, suffixStart);  return simplified; } public static final NativeLibrary getInstance(String libraryName) { return getInstance(libraryName, Collections.emptyMap()); } public static final NativeLibrary getInstance(String libraryName, ClassLoader classLoader) { return getInstance(libraryName, Collections.singletonMap("classloader", classLoader)); } public static final NativeLibrary getInstance(String libraryName, Map<String, ?> libraryOptions) { Map<String, Object> options = new HashMap<>(libraryOptions); if (options.get("calling-convention") == null) options.put("calling-convention", Integer.valueOf(0));  if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(libraryName)) libraryName = null;  synchronized (libraries) { Reference<NativeLibrary> ref = libraries.get(libraryName + options); NativeLibrary library = (ref != null) ? ref.get() : null; if (library == null) { if (libraryName == null) { library = new NativeLibrary("<process>", null, Native.open(null, openFlags(options)), options); } else { library = loadLibrary(libraryName, options); }  ref = new WeakReference<>(library); libraries.put(library.getName() + options, ref); File file = library.getFile(); if (file != null) { libraries.put(file.getAbsolutePath() + options, ref); libraries.put(file.getName() + options, ref); }  }  return library; }  } public static final synchronized NativeLibrary getProcess() { return getInstance(null); } public static final synchronized NativeLibrary getProcess(Map<String, ?> options) { return getInstance((String)null, options); } public static final void addSearchPath(String libraryName, String path) { List<String> customPaths = searchPaths.get(libraryName); if (customPaths == null) { customPaths = Collections.synchronizedList(new ArrayList<>()); searchPaths.put(libraryName, customPaths); }  customPaths.add(path); } public Function getFunction(String functionName) { return getFunction(functionName, this.callFlags); } Function getFunction(String name, Method method) { FunctionMapper mapper = (FunctionMapper)this.options.get("function-mapper"); if (mapper != null) name = mapper.getFunctionName(this, method);  String prefix = System.getProperty("jna.profiler.prefix", "$$YJP$$"); if (name.startsWith(prefix)) name = name.substring(prefix.length());  int flags = this.callFlags; Class<?>[] etypes = method.getExceptionTypes(); for (int i = 0; i < etypes.length; i++) { if (LastErrorException.class.isAssignableFrom(etypes[i])) flags |= 0x40;  }  return getFunction(name, flags); }
/*      */   public Function getFunction(String functionName, int callFlags) { return getFunction(functionName, callFlags, this.encoding); }
/*      */   public Function getFunction(String functionName, int callFlags, String encoding) { if (functionName == null) throw new NullPointerException("Function name may not be null");  synchronized (this.functions) { String key = functionKey(functionName, callFlags, encoding); Function function = this.functions.get(key); if (function == null) { function = new Function(this, functionName, callFlags, encoding); this.functions.put(key, function); }  return function; }  }
/*      */   public Map<String, ?> getOptions() { return this.options; }
/*  992 */   private static String getMultiArchPath() { String cpu = Platform.ARCH;
/*      */ 
/*      */     
/*  995 */     String kernel = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
/*  996 */     String libc = "-gnu";
/*      */     
/*  998 */     if (Platform.isIntel()) {
/*  999 */       cpu = Platform.is64Bit() ? "x86_64" : "i386";
/*      */     }
/* 1001 */     else if (Platform.isPPC()) {
/* 1002 */       cpu = Platform.is64Bit() ? "powerpc64" : "powerpc";
/*      */     }
/* 1004 */     else if (Platform.isARM()) {
/* 1005 */       cpu = "arm";
/* 1006 */       libc = "-gnueabi";
/*      */     }
/* 1008 */     else if (Platform.ARCH.equals("mips64el")) {
/* 1009 */       libc = "-gnuabi64";
/*      */     } 
/*      */     
/* 1012 */     return cpu + kernel + libc; }
/*      */   public Pointer getGlobalVariableAddress(String symbolName) { try { return new Pointer(getSymbolAddress(symbolName)); } catch (UnsatisfiedLinkError e) { throw new UnsatisfiedLinkError("Error looking up '" + symbolName + "': " + e.getMessage()); }  }
/*      */   long getSymbolAddress(String name) { if (this.handle == 0L) throw new UnsatisfiedLinkError("Library has been unloaded");  return this.symbolProvider.getSymbolAddress(this.handle, name, NATIVE_SYMBOL_PROVIDER); }
/*      */   public String toString() { return "Native Library <" + this.libraryPath + "@" + this.handle + ">"; }
/*      */   public String getName() { return this.libraryName; }
/*      */   public File getFile() { if (this.libraryPath == null) return null;  return new File(this.libraryPath); }
/*      */   static void disposeAll() { Set<Reference<NativeLibrary>> values; synchronized (libraries) { values = new LinkedHashSet<>(libraries.values()); }  for (Reference<NativeLibrary> ref : values) { NativeLibrary lib = ref.get(); if (lib != null) lib.close();  }  }
/* 1019 */   public void close() { Set<String> keys = new HashSet<>(); synchronized (libraries) { for (Map.Entry<String, Reference<NativeLibrary>> e : libraries.entrySet()) { Reference<NativeLibrary> ref = e.getValue(); if (ref.get() == this) keys.add(e.getKey());  }  for (String k : keys) libraries.remove(k);  }  synchronized (this) { if (this.handle != 0L) { this.handle = 0L; this.cleanable.clean(); }  }  } private static ArrayList<String> getLinuxLdPaths() { ArrayList<String> ldPaths = new ArrayList<>();
/* 1020 */     Process process = null;
/* 1021 */     BufferedReader reader = null;
/*      */     
/* 1023 */     try { process = Runtime.getRuntime().exec("/sbin/ldconfig -p");
/* 1024 */       reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*      */       String buffer;
/* 1026 */       while ((buffer = reader.readLine()) != null) {
/* 1027 */         int startPath = buffer.indexOf(" => ");
/* 1028 */         int endPath = buffer.lastIndexOf('/');
/* 1029 */         if (startPath != -1 && endPath != -1 && startPath < endPath) {
/* 1030 */           String path = buffer.substring(startPath + 4, endPath);
/* 1031 */           if (!ldPaths.contains(path)) {
/* 1032 */             ldPaths.add(path);
/*      */           }
/*      */         } 
/*      */       }  }
/* 1036 */     catch (Exception exception) {  }
/*      */     finally
/* 1038 */     { if (reader != null) {
/*      */         try {
/* 1040 */           reader.close();
/* 1041 */         } catch (IOException iOException) {}
/*      */       }
/*      */       
/* 1044 */       if (process != null) {
/*      */         try {
/* 1046 */           process.waitFor();
/* 1047 */         } catch (InterruptedException interruptedException) {}
/*      */       } }
/*      */ 
/*      */     
/* 1051 */     return ldPaths; }
/*      */   @Deprecated public void dispose() { close(); }
/*      */   private static List<String> initPaths(String key) { String value = System.getProperty(key, ""); if ("".equals(value)) return Collections.emptyList();  StringTokenizer st = new StringTokenizer(value, File.pathSeparator); List<String> list = new ArrayList<>(); while (st.hasMoreTokens()) { String path = st.nextToken(); if (!"".equals(path)) list.add(path);  }  return list; }
/*      */   private static String findLibraryPath(String libName, Collection<String> searchPath) { if ((new File(libName)).isAbsolute()) return libName;  String name = mapSharedLibraryName(libName); for (String path : searchPath) { File file = new File(path, name); if (file.exists()) return file.getAbsolutePath();  if (Platform.isMac()) if (name.endsWith(".dylib")) { file = new File(path, name.substring(0, name.lastIndexOf(".dylib")) + ".jnilib"); if (file.exists()) return file.getAbsolutePath();  }   }  return name; }
/*      */   static String mapSharedLibraryName(String libName) { if (Platform.isMac()) { if (libName.startsWith("lib") && (libName.endsWith(".dylib") || libName.endsWith(".jnilib"))) return libName;  String name = System.mapLibraryName(libName); if (name.endsWith(".jnilib")) return name.substring(0, name.lastIndexOf(".jnilib")) + ".dylib";  return name; }  if (Platform.isLinux() || Platform.isFreeBSD()) { if (isVersionedName(libName) || libName.endsWith(".so")) return libName;  } else if (Platform.isAIX()) { if (isVersionedName(libName) || libName.endsWith(".so") || libName.startsWith("lib") || libName.endsWith(".a")) return libName;  } else if (Platform.isWindows() && (libName.endsWith(".drv") || libName.endsWith(".dll") || libName.endsWith(".ocx"))) { return libName; }  String mappedName = System.mapLibraryName(libName); if (Platform.isAIX() && mappedName.endsWith(".so")) return mappedName.replaceAll(".so$", ".a");  return mappedName; } private static boolean isVersionedName(String name) { if (name.startsWith("lib")) { int so = name.lastIndexOf(".so."); if (so != -1 && so + 4 < name.length()) { for (int i = so + 4; i < name.length(); i++) { char ch = name.charAt(i); if (!Character.isDigit(ch) && ch != '.') return false;  }  return true; }  }  return false; } static String matchLibrary(final String libName, Collection<String> searchPath) { File lib = new File(libName); if (lib.isAbsolute()) searchPath = Arrays.asList(new String[] { lib.getParent() });  FilenameFilter filter = new FilenameFilter() {
/*      */         public boolean accept(File dir, String filename) { return ((filename.startsWith("lib" + libName + ".so") || (filename.startsWith(libName + ".so") && libName.startsWith("lib"))) && NativeLibrary.isVersionedName(filename)); }
/*      */       }; Collection<File> matches = new LinkedList<>(); for (String path : searchPath) { File[] files = (new File(path)).listFiles(filter); if (files != null && files.length > 0) matches.addAll(Arrays.asList(files));  }  double bestVersion = -1.0D; String bestMatch = null; for (File f : matches) { String path = f.getAbsolutePath(); String ver = path.substring(path.lastIndexOf(".so.") + 4); double version = parseVersion(ver); if (version > bestVersion) { bestVersion = version; bestMatch = path; }  }  return bestMatch; } static double parseVersion(String ver) { double v = 0.0D; double divisor = 1.0D; int dot = ver.indexOf("."); while (ver != null) { String num; if (dot != -1) { num = ver.substring(0, dot); ver = ver.substring(dot + 1); dot = ver.indexOf("."); } else { num = ver; ver = null; }  try { v += Integer.parseInt(num) / divisor; } catch (NumberFormatException e) { return 0.0D; }  divisor *= 100.0D; }  return v; } private static final class NativeLibraryDisposer implements Runnable
/*      */   {
/* 1059 */     private long handle; public NativeLibraryDisposer(long handle) { this.handle = handle; }
/*      */ 
/*      */     
/*      */     public synchronized void run() {
/* 1063 */       if (this.handle != 0L)
/*      */         try {
/* 1065 */           Native.close(this.handle);
/*      */         } finally {
/* 1067 */           this.handle = 0L;
/*      */         }  
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\NativeLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */