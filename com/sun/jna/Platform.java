/*     */ package com.sun.jna;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Platform
/*     */ {
/*     */   public static final int UNSPECIFIED = -1;
/*     */   public static final int MAC = 0;
/*     */   public static final int LINUX = 1;
/*     */   public static final int WINDOWS = 2;
/*     */   public static final int SOLARIS = 3;
/*     */   public static final int FREEBSD = 4;
/*     */   public static final int OPENBSD = 5;
/*     */   public static final int WINDOWSCE = 6;
/*     */   public static final int AIX = 7;
/*     */   public static final int ANDROID = 8;
/*     */   public static final int GNU = 9;
/*     */   public static final int KFREEBSD = 10;
/*     */   public static final int NETBSD = 11;
/*     */   public static final int DRAGONFLYBSD = 12;
/*     */   public static final boolean RO_FIELDS;
/*     */   public static final boolean HAS_BUFFERS;
/*     */   
/*     */   static {
/*  73 */     String osName = System.getProperty("os.name");
/*  74 */     if (osName.startsWith("Linux")) {
/*  75 */       if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
/*  76 */         osType = 8;
/*     */         
/*  78 */         System.setProperty("jna.nounpack", "true");
/*     */       } else {
/*     */         
/*  81 */         osType = 1;
/*     */       }
/*     */     
/*  84 */     } else if (osName.startsWith("AIX")) {
/*  85 */       osType = 7;
/*     */     }
/*  87 */     else if (osName.startsWith("Mac") || osName.startsWith("Darwin")) {
/*  88 */       osType = 0;
/*     */     }
/*  90 */     else if (osName.startsWith("Windows CE")) {
/*  91 */       osType = 6;
/*     */     }
/*  93 */     else if (osName.startsWith("Windows")) {
/*  94 */       osType = 2;
/*     */     }
/*  96 */     else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
/*  97 */       osType = 3;
/*     */     }
/*  99 */     else if (osName.startsWith("FreeBSD")) {
/* 100 */       osType = 4;
/*     */     }
/* 102 */     else if (osName.startsWith("OpenBSD")) {
/* 103 */       osType = 5;
/*     */     }
/* 105 */     else if (osName.equalsIgnoreCase("gnu")) {
/* 106 */       osType = 9;
/*     */     }
/* 108 */     else if (osName.equalsIgnoreCase("gnu/kfreebsd")) {
/* 109 */       osType = 10;
/*     */     }
/* 111 */     else if (osName.equalsIgnoreCase("netbsd")) {
/* 112 */       osType = 11;
/*     */     }
/* 114 */     else if (osName.equalsIgnoreCase("dragonflybsd")) {
/* 115 */       osType = 12;
/*     */     } else {
/*     */       
/* 118 */       osType = -1;
/*     */     } 
/* 120 */     boolean hasBuffers = false;
/*     */     try {
/* 122 */       Class.forName("java.nio.Buffer");
/* 123 */       hasBuffers = true;
/*     */     }
/* 125 */     catch (ClassNotFoundException classNotFoundException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public static final boolean HAS_AWT = (osType != 6 && osType != 8 && osType != 7);
/* 131 */   public static final boolean HAS_JAWT = (HAS_AWT && osType != 0); public static final String MATH_LIBRARY_NAME; public static final String C_LIBRARY_NAME; public static final boolean HAS_DLL_CALLBACKS; static {
/* 132 */     HAS_BUFFERS = hasBuffers;
/* 133 */     RO_FIELDS = (osType != 6);
/* 134 */     C_LIBRARY_NAME = (osType == 2) ? "msvcrt" : ((osType == 6) ? "coredll" : "c");
/* 135 */     MATH_LIBRARY_NAME = (osType == 2) ? "msvcrt" : ((osType == 6) ? "coredll" : "m");
/* 136 */     ARCH = getCanonicalArchitecture(System.getProperty("os.arch"), osType);
/*     */     
/* 138 */     HAS_DLL_CALLBACKS = (osType == 2 && !ARCH.startsWith("aarch"));
/* 139 */     RESOURCE_PREFIX = getNativeLibraryResourcePrefix();
/*     */   }
/*     */   public static final String RESOURCE_PREFIX; private static final int osType; public static final String ARCH;
/*     */   public static final int getOSType() {
/* 143 */     return osType;
/*     */   }
/*     */   public static final boolean isMac() {
/* 146 */     return (osType == 0);
/*     */   }
/*     */   public static final boolean isAndroid() {
/* 149 */     return (osType == 8);
/*     */   }
/*     */   public static final boolean isLinux() {
/* 152 */     return (osType == 1);
/*     */   }
/*     */   public static final boolean isAIX() {
/* 155 */     return (osType == 7);
/*     */   }
/*     */   public static final boolean isWindowsCE() {
/* 158 */     return (osType == 6);
/*     */   }
/*     */   
/*     */   public static final boolean isWindows() {
/* 162 */     return (osType == 2 || osType == 6);
/*     */   }
/*     */   public static final boolean isSolaris() {
/* 165 */     return (osType == 3);
/*     */   }
/*     */   public static final boolean isDragonFlyBSD() {
/* 168 */     return (osType == 12);
/*     */   }
/*     */   public static final boolean isFreeBSD() {
/* 171 */     return (osType == 4);
/*     */   }
/*     */   public static final boolean isOpenBSD() {
/* 174 */     return (osType == 5);
/*     */   }
/*     */   public static final boolean isNetBSD() {
/* 177 */     return (osType == 11);
/*     */   }
/*     */   public static final boolean isGNU() {
/* 180 */     return (osType == 9);
/*     */   }
/*     */   public static final boolean iskFreeBSD() {
/* 183 */     return (osType == 10);
/*     */   }
/*     */   
/*     */   public static final boolean isX11() {
/* 187 */     return (!isWindows() && !isMac());
/*     */   }
/*     */   public static final boolean hasRuntimeExec() {
/* 190 */     if (isWindowsCE() && "J9".equals(System.getProperty("java.vm.name")))
/* 191 */       return false; 
/* 192 */     return true;
/*     */   }
/*     */   public static final boolean is64Bit() {
/* 195 */     String model = System.getProperty("sun.arch.data.model", 
/* 196 */         System.getProperty("com.ibm.vm.bitmode"));
/* 197 */     if (model != null) {
/* 198 */       return "64".equals(model);
/*     */     }
/* 200 */     if ("x86-64".equals(ARCH) || "ia64"
/* 201 */       .equals(ARCH) || "ppc64"
/* 202 */       .equals(ARCH) || "ppc64le".equals(ARCH) || "sparcv9"
/* 203 */       .equals(ARCH) || "mips64"
/* 204 */       .equals(ARCH) || "mips64el".equals(ARCH) || "loongarch64"
/* 205 */       .equals(ARCH) || "amd64"
/* 206 */       .equals(ARCH) || "aarch64"
/* 207 */       .equals(ARCH)) {
/* 208 */       return true;
/*     */     }
/* 210 */     return (Native.POINTER_SIZE == 8);
/*     */   }
/*     */   
/*     */   public static final boolean isIntel() {
/* 214 */     if (ARCH.startsWith("x86")) {
/* 215 */       return true;
/*     */     }
/* 217 */     return false;
/*     */   }
/*     */   
/*     */   public static final boolean isPPC() {
/* 221 */     if (ARCH.startsWith("ppc")) {
/* 222 */       return true;
/*     */     }
/* 224 */     return false;
/*     */   }
/*     */   
/*     */   public static final boolean isARM() {
/* 228 */     return (ARCH.startsWith("arm") || ARCH.startsWith("aarch"));
/*     */   }
/*     */   
/*     */   public static final boolean isSPARC() {
/* 232 */     return ARCH.startsWith("sparc");
/*     */   }
/*     */   
/*     */   public static final boolean isMIPS() {
/* 236 */     if (ARCH.equals("mips") || ARCH
/* 237 */       .equals("mips64") || ARCH
/* 238 */       .equals("mipsel") || ARCH
/* 239 */       .equals("mips64el")) {
/* 240 */       return true;
/*     */     }
/* 242 */     return false;
/*     */   }
/*     */   
/*     */   public static final boolean isLoongArch() {
/* 246 */     return ARCH.startsWith("loongarch");
/*     */   }
/*     */   
/*     */   static String getCanonicalArchitecture(String arch, int platform) {
/* 250 */     arch = arch.toLowerCase().trim();
/* 251 */     if ("powerpc".equals(arch)) {
/* 252 */       arch = "ppc";
/*     */     }
/* 254 */     else if ("powerpc64".equals(arch)) {
/* 255 */       arch = "ppc64";
/*     */     }
/* 257 */     else if ("i386".equals(arch) || "i686".equals(arch)) {
/* 258 */       arch = "x86";
/*     */     }
/* 260 */     else if ("x86_64".equals(arch) || "amd64".equals(arch)) {
/* 261 */       arch = "x86-64";
/*     */     }
/* 263 */     else if ("zarch_64".equals(arch)) {
/* 264 */       arch = "s390x";
/*     */     } 
/*     */ 
/*     */     
/* 268 */     if ("ppc64".equals(arch) && "little".equals(System.getProperty("sun.cpu.endian"))) {
/* 269 */       arch = "ppc64le";
/*     */     }
/*     */     
/* 272 */     if ("arm".equals(arch) && platform == 1 && isSoftFloat()) {
/* 273 */       arch = "armel";
/*     */     }
/*     */     
/* 276 */     return arch;
/*     */   }
/*     */   
/*     */   static boolean isSoftFloat() {
/*     */     try {
/* 281 */       File self = new File("/proc/self/exe");
/* 282 */       if (self.exists()) {
/* 283 */         ELFAnalyser ahfd = ELFAnalyser.analyse(self.getCanonicalPath());
/* 284 */         return !ahfd.isArmHardFloat();
/*     */       } 
/* 286 */     } catch (IOException ex) {
/*     */       
/* 288 */       Logger.getLogger(Platform.class.getName()).log(Level.INFO, "Failed to read '/proc/self/exe' or the target binary.", ex);
/* 289 */     } catch (SecurityException ex) {
/*     */       
/* 291 */       Logger.getLogger(Platform.class.getName()).log(Level.INFO, "SecurityException while analysing '/proc/self/exe' or the target binary.", ex);
/*     */     } 
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getNativeLibraryResourcePrefix() {
/* 300 */     String prefix = System.getProperty("jna.prefix");
/* 301 */     if (prefix != null) {
/* 302 */       return prefix;
/*     */     }
/* 304 */     return getNativeLibraryResourcePrefix(getOSType(), System.getProperty("os.arch"), System.getProperty("os.name"));
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
/*     */   static String getNativeLibraryResourcePrefix(int osType, String arch, String name) {
/* 316 */     arch = getCanonicalArchitecture(arch, osType);
/* 317 */     switch (osType)
/*     */     { case 8:
/* 319 */         if (arch.startsWith("arm")) {
/* 320 */           arch = "arm";
/*     */         }
/* 322 */         osPrefix = "android-" + arch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 363 */         return osPrefix;case 2: osPrefix = "win32-" + arch; return osPrefix;case 6: osPrefix = "w32ce-" + arch; return osPrefix;case 0: osPrefix = "darwin-" + arch; return osPrefix;case 1: osPrefix = "linux-" + arch; return osPrefix;case 3: osPrefix = "sunos-" + arch; return osPrefix;case 12: osPrefix = "dragonflybsd-" + arch; return osPrefix;case 4: osPrefix = "freebsd-" + arch; return osPrefix;case 5: osPrefix = "openbsd-" + arch; return osPrefix;case 11: osPrefix = "netbsd-" + arch; return osPrefix;case 10: osPrefix = "kfreebsd-" + arch; return osPrefix; }  String osPrefix = name.toLowerCase(); int space = osPrefix.indexOf(" "); if (space != -1) osPrefix = osPrefix.substring(0, space);  osPrefix = osPrefix + "-" + arch; return osPrefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\Platform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */