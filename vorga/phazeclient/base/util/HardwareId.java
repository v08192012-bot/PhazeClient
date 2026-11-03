/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HardwareId
/*     */ {
/*     */   private static volatile String cached;
/*     */   
/*     */   public static String get() {
/*  53 */     String local = cached;
/*  54 */     if (local != null) {
/*  55 */       return local.isEmpty() ? null : local;
/*     */     }
/*  57 */     synchronized (HardwareId.class) {
/*  58 */       if (cached == null) {
/*  59 */         cached = compute();
/*     */       }
/*     */     } 
/*  62 */     return cached.isEmpty() ? null : cached;
/*     */   }
/*     */   
/*     */   private static String compute() {
/*     */     try {
/*  67 */       List<String> parts = new ArrayList<>();
/*  68 */       parts.addAll(collectMacAddresses());
/*  69 */       parts.add("host:" + safeHostName());
/*  70 */       parts.add("user:" + normalize(System.getProperty("user.name")));
/*  71 */       parts.add("os:" + normalize(System.getProperty("os.name")) + "/" + 
/*  72 */           normalize(System.getProperty("os.arch")));
/*     */ 
/*     */ 
/*     */       
/*  76 */       Collections.sort(parts);
/*     */       
/*  78 */       MessageDigest digest = MessageDigest.getInstance("SHA-256");
/*  79 */       digest.update("phaze-hwid-v1".getBytes(StandardCharsets.UTF_8));
/*  80 */       for (String part : parts) {
/*  81 */         digest.update((byte)0);
/*  82 */         digest.update(part.getBytes(StandardCharsets.UTF_8));
/*     */       } 
/*  84 */       return toHex(digest.digest());
/*  85 */     } catch (Throwable t) {
/*     */ 
/*     */       
/*  88 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<String> collectMacAddresses() {
/*  93 */     List<String> macs = new ArrayList<>();
/*     */     try {
/*  95 */       Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
/*  96 */       while (interfaces != null && interfaces.hasMoreElements()) {
/*  97 */         NetworkInterface iface = interfaces.nextElement();
/*     */         try {
/*  99 */           if (iface.isLoopback() || iface.isVirtual() || iface.isPointToPoint()) {
/*     */             continue;
/*     */           }
/* 102 */           byte[] mac = iface.getHardwareAddress();
/* 103 */           if (mac == null || mac.length == 0) {
/*     */             continue;
/*     */           }
/* 106 */           macs.add("mac:" + toHex(mac));
/* 107 */         } catch (Throwable throwable) {}
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 112 */     catch (Throwable throwable) {}
/*     */     
/* 114 */     return macs;
/*     */   }
/*     */   
/*     */   private static String safeHostName() {
/*     */     try {
/* 119 */       return normalize(InetAddress.getLocalHost().getHostName());
/* 120 */     } catch (Throwable t) {
/* 121 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String normalize(String value) {
/* 126 */     return (value == null) ? "" : value.trim().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   private static String toHex(byte[] bytes) {
/* 130 */     StringBuilder out = new StringBuilder(bytes.length * 2);
/* 131 */     for (byte b : bytes) {
/* 132 */       out.append(Character.forDigit(b >> 4 & 0xF, 16));
/* 133 */       out.append(Character.forDigit(b & 0xF, 16));
/*     */     } 
/* 135 */     return out.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\HardwareId.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */