/*     */ package vorga.phazeclient.implement.cosmetics.bridge;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import java.util.HexFormat;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_642;
/*     */ 
/*     */ 
/*     */ public final class SharedWorldKey
/*     */ {
/*     */   public static String get(class_310 client) {
/*  19 */     if (client == null || client.field_1687 == null) return null; 
/*  20 */     String connectedAddress = actualAddress(client, true);
/*  21 */     if (connectedAddress != null && connectedAddress.startsWith("local:")) {
/*  22 */       return hash(connectedAddress);
/*     */     }
/*  24 */     class_642 info = client.method_1558();
/*     */ 
/*     */     
/*  27 */     String address = (info != null && info.field_3761 != null && !info.field_3761.isBlank()) ? canonicalConfiguredAddress(info.field_3761) : actualAddress(client);
/*  28 */     return hash(address);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String legacy(class_310 client) {
/*  33 */     if (client == null || client.field_1687 == null) return null; 
/*  34 */     String address = null;
/*  35 */     class_642 info = client.method_1558();
/*  36 */     if (info != null && info.field_3761 != null && !info.field_3761.isBlank()) {
/*  37 */       address = normalize(info.field_3761);
/*  38 */     } else if (client.method_1576() != null && client.method_1576().method_3756() > 0) {
/*  39 */       address = "local:" + client.method_1576().method_3756();
/*     */     } 
/*  41 */     return hash(address);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String actual(class_310 client) {
/*  46 */     return (client == null || client.field_1687 == null) ? null : hash(actualAddress(client, false));
/*     */   }
/*     */   
/*     */   private static String actualAddress(class_310 client) {
/*  50 */     return actualAddress(client, true);
/*     */   }
/*     */   
/*     */   private static String actualAddress(class_310 client, boolean includeLocalInterfaces) {
/*  54 */     if (client.method_1562() != null) {
/*  55 */       SocketAddress remote = client.method_1562().method_48296().method_10755();
/*  56 */       if (remote instanceof InetSocketAddress) { InetSocketAddress inet = (InetSocketAddress)remote;
/*     */ 
/*     */         
/*  59 */         String host = (inet.getAddress() == null) ? inet.getHostString() : inet.getAddress().getHostAddress();
/*  60 */         if (inet.getAddress() != null && (inet.getAddress().isLoopbackAddress() || (includeLocalInterfaces && 
/*  61 */           isThisComputer(inet.getAddress())))) {
/*  62 */           return "local:" + inet.getPort();
/*     */         }
/*  64 */         if (host.contains(":")) host = "[" + host + "]"; 
/*  65 */         return normalize(host + ":" + host); }
/*     */     
/*     */     } 
/*  68 */     if (client.method_1576() != null && client.method_1576().method_3756() > 0) {
/*  69 */       return "local:" + client.method_1576().method_3756();
/*     */     }
/*  71 */     class_642 info = client.method_1558();
/*  72 */     return (info == null) ? null : normalize(info.field_3761);
/*     */   }
/*     */   
/*     */   private static boolean isThisComputer(InetAddress address) {
/*     */     try {
/*  77 */       return (NetworkInterface.getByInetAddress(address) != null);
/*  78 */     } catch (Exception ignored) {
/*  79 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String hash(String address) {
/*  84 */     if (address == null || address.isBlank()) return null;
/*     */     
/*     */     try {
/*  87 */       byte[] digest = MessageDigest.getInstance("SHA-256").digest(address.getBytes(StandardCharsets.UTF_8));
/*  88 */       return HexFormat.of().formatHex(digest);
/*  89 */     } catch (Exception ignored) {
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String canonicalConfiguredAddress(String value) {
/*  95 */     String address = value.trim().toLowerCase(Locale.ROOT);
/*  96 */     if (address.startsWith("localhost:") || address.startsWith("127.0.0.1:") || address
/*  97 */       .startsWith("[::1]:")) return normalize(address);
/*     */     
/*  99 */     String host = address;
/* 100 */     int port = 25565;
/* 101 */     if (address.startsWith("[")) {
/* 102 */       int end = address.indexOf(']');
/* 103 */       if (end > 0) {
/* 104 */         host = address.substring(1, end);
/* 105 */         if (end + 2 < address.length() && address.charAt(end + 1) == ':') {
/* 106 */           try { port = Integer.parseInt(address.substring(end + 2)); } catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */       } 
/*     */     } else {
/* 110 */       int colon = address.lastIndexOf(':');
/* 111 */       if (colon > 0 && address.indexOf(':') == colon) {
/* 112 */         host = address.substring(0, colon); 
/* 113 */         try { port = Integer.parseInt(address.substring(colon + 1)); } catch (NumberFormatException numberFormatException) {}
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     if (!host.matches("[0-9.]+") && !isSharedHosting(host)) {
/* 118 */       String[] labels = host.split("\\.");
/* 119 */       int keep = 2;
/* 120 */       if (labels.length >= 3 && isCountrySecondLevel(labels[labels.length - 2])) keep = 3; 
/* 121 */       if (labels.length > keep) host = String.join(".", Arrays.<CharSequence>copyOfRange((CharSequence[])labels, labels.length - keep, labels.length)); 
/*     */     } 
/* 123 */     return host + ":" + host;
/*     */   }
/*     */   
/*     */   private static boolean isCountrySecondLevel(String label) {
/* 127 */     return (label.equals("co") || label.equals("com") || label.equals("net") || label
/* 128 */       .equals("org") || label.equals("gov") || label.equals("ac"));
/*     */   }
/*     */   
/*     */   private static boolean isSharedHosting(String host) {
/* 132 */     return (host.endsWith(".aternos.me") || host.endsWith(".minehut.gg") || host
/* 133 */       .endsWith(".falixsrv.me") || host.endsWith(".server.pro"));
/*     */   }
/*     */   
/*     */   public static String dimension(class_310 client) {
/* 137 */     return (client == null || client.field_1687 == null) ? null : 
/* 138 */       client.field_1687.method_27983().method_29177().toString();
/*     */   }
/*     */   
/*     */   private static String normalize(String value) {
/* 142 */     String address = value.trim().toLowerCase(Locale.ROOT);
/* 143 */     if (address.startsWith("127.0.0.1:")) return "local:" + address.substring(10); 
/* 144 */     if (address.startsWith("localhost:")) return "local:" + address.substring(10); 
/* 145 */     if (address.startsWith("[::1]:")) return "local:" + address.substring(6); 
/* 146 */     return address;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\SharedWorldKey.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */