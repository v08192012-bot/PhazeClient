/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_634;
/*     */ import net.minecraft.class_642;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerUtil
/*     */ {
/*  16 */   private static final Set<String> FUN_TIME_SEGMENTS = Set.of("funtime", "funsky");
/*     */   private static volatile String cachedAddressInput;
/*  18 */   private static volatile String cachedNormalizedHost = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final Set<String> SHIFT_TAP_SEGMENTS = Set.of("funtime", "funmoon", "skytime");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final Set<String> AUTO_SWAP_SEGMENTS = Set.of("funtime", "funsky", "holytime", "spookytime", "funtrainer", "skytime");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final Set<String> AUTO_POTION_SEGMENTS = Set.of("funtime", "funsky", "holytime", "space-times", "spookytime", "fillcube", "skytime");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private static final Set<String> ELYTRA_UTILITY_SEGMENTS = Set.of("funtime", "funsky", "holytime", "spookytime", "funmoon", "skytime", "funtrainer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final Set<String> ITEM_SCROLLER_SEGMENTS = Set.of("funtime", "holyworld", "skytime", "holytime", "funsky", "space-times", "spookytime", "funmoon", "stray");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final Set<String> MOUSE_CLICKER_SEGMENTS = Set.of("funtime", "funsky", "holytime", "space-times", "spookytime", "funmoon", "fillcube", "skytime", "funtrainer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final Set<String> AUTO_REISSUE_SEGMENTS = Set.of("funtime", "skytime");
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final Set<String> AUC_HELPER_SEGMENTS = Set.of("funtime");
/*     */ 
/*     */   
/*  86 */   private static final Set<String> AUTO_EAT_SEGMENTS = Set.of("fillcube", "funsky", "funtime", "holytime", "skytime", "space-times", "spookytime");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final Set<String> AUTO_RESPAWN_SEGMENTS = Set.of("funsky", "funtime", "holytime", "skytime", "space-times", "spookytime");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final Set<String> FAST_SWAP_SEGMENTS = Set.of("fillcube", "funtime", "space-times");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static final Set<String> FT_HELPER_SEGMENTS = Set.of("funmoon", "funsky", "funtime", "funtrainer", "holytime", "skytime", "space-times", "spookytime");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final Set<String> TRAP_TIMER_SEGMENTS = Set.of("funsky", "funtime", "funtrainer", "holytime", "skytime", "spookytime");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private static final Map<String, Set<String>> MIRRORED_MODULE_SEGMENTS = Map.ofEntries((Map.Entry<? extends String, ? extends Set<String>>[])new Map.Entry[] { 
/* 127 */         Map.entry("auc_helper", AUC_HELPER_SEGMENTS), 
/* 128 */         Map.entry("auto_eat", AUTO_EAT_SEGMENTS), 
/* 129 */         Map.entry("auto_respawn", AUTO_RESPAWN_SEGMENTS), 
/* 130 */         Map.entry("autonear", Set.of("funtime")), 
/* 131 */         Map.entry("autopotion", AUTO_POTION_SEGMENTS), 
/* 132 */         Map.entry("autoreissue", AUTO_REISSUE_SEGMENTS), 
/* 133 */         Map.entry("autoswap", AUTO_SWAP_SEGMENTS), 
/* 134 */         Map.entry("elytrautility", ELYTRA_UTILITY_SEGMENTS), 
/* 135 */         Map.entry("fast_swap", FAST_SWAP_SEGMENTS), 
/* 136 */         Map.entry("ft_helper", FT_HELPER_SEGMENTS), 
/* 137 */         Map.entry("item_scroller", ITEM_SCROLLER_SEGMENTS), 
/* 138 */         Map.entry("mouseclicker", MOUSE_CLICKER_SEGMENTS), 
/* 139 */         Map.entry("shifttap", SHIFT_TAP_SEGMENTS), 
/* 140 */         Map.entry("trap_timer", TRAP_TIMER_SEGMENTS) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCurrentServerHost() {
/* 164 */     class_310 mc = class_310.method_1551();
/* 165 */     if (mc == null) return "";
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (mc.method_1542()) return "";
/*     */     
/* 171 */     class_642 entry = mc.method_1558();
/* 172 */     if (entry != null && entry.field_3761 != null) {
/* 173 */       return stripPort(entry.field_3761);
/*     */     }
/*     */     
/* 176 */     class_634 handler = mc.method_1562();
/* 177 */     if (handler == null) return "";
/*     */     
/* 179 */     class_642 handlerInfo = handler.method_45734();
/* 180 */     if (handlerInfo != null && handlerInfo.field_3761 != null) {
/* 181 */       return stripPort(handlerInfo.field_3761);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 186 */     SocketAddress peer = handler.method_48296().method_10755();
/* 187 */     if (peer instanceof InetSocketAddress) { InetSocketAddress isa = (InetSocketAddress)peer; if (isa.getHostString() != null)
/* 188 */         return stripPort(isa.getHostString());  }
/*     */     
/* 190 */     return "";
/*     */   }
/*     */   
/*     */   private static String stripPort(String addr) {
/* 194 */     if (addr.equals(cachedAddressInput)) {
/* 195 */       return cachedNormalizedHost;
/*     */     }
/* 197 */     String s = addr.toLowerCase(Locale.ROOT).trim();
/* 198 */     int sep = s.indexOf(':');
/* 199 */     String normalized = (sep >= 0) ? s.substring(0, sep) : s;
/* 200 */     cachedNormalizedHost = normalized;
/* 201 */     cachedAddressInput = addr;
/* 202 */     return normalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasServerSegment(String expectedSegment) {
/* 209 */     String host = getCurrentServerHost();
/* 210 */     if (host.isEmpty()) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     return containsHostSegment(host, expectedSegment);
/*     */   }
/*     */   
/*     */   private static boolean hasAnyServerSegment(Set<String> expectedSegments) {
/* 218 */     String host = getCurrentServerHost();
/* 219 */     if (host.isEmpty()) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     for (String expectedSegment : expectedSegments) {
/* 224 */       if (containsHostSegment(host, expectedSegment)) {
/* 225 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 229 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean containsHostSegment(String host, String expectedSegment) {
/* 233 */     int start = 0;
/* 234 */     while (start <= host.length()) {
/* 235 */       int end = host.indexOf('.', start);
/* 236 */       if (end < 0) {
/* 237 */         end = host.length();
/*     */       }
/* 239 */       int length = end - start;
/* 240 */       if (length == expectedSegment.length() && host
/* 241 */         .regionMatches(start, expectedSegment, 0, length)) {
/* 242 */         return true;
/*     */       }
/* 244 */       if (end == host.length()) {
/* 245 */         return false;
/*     */       }
/* 247 */       start = end + 1;
/*     */     } 
/* 249 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isSingleplayerOrHasAnyServerSegment(Set<String> expectedSegments) {
/* 253 */     class_310 mc = class_310.method_1551();
/* 254 */     if (mc == null) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (mc.method_1542()) {
/* 258 */       return true;
/*     */     }
/* 260 */     return hasAnyServerSegment(expectedSegments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFunTimeServer() {
/* 267 */     return hasAnyServerSegment(FUN_TIME_SEGMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFunTrainerServer() {
/* 274 */     return hasServerSegment("funtrainer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFillCubeServer() {
/* 281 */     return hasServerSegment("fillcube");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHolyWorldServer() {
/* 288 */     return hasServerSegment("holyworld");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isShiftTapSupported() {
/* 295 */     return isSupported("shifttap", SHIFT_TAP_SEGMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAutoSwapSupported() {
/* 304 */     return isSupported("autoswap", AUTO_SWAP_SEGMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAutoPotionSupported() {
/* 313 */     return isSupported("autopotion", AUTO_POTION_SEGMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isElytraUtilitySupported() {
/* 322 */     return isSupported("elytrautility", ELYTRA_UTILITY_SEGMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isItemScrollerSupported() {
/* 331 */     return isSupported("item_scroller", ITEM_SCROLLER_SEGMENTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMouseClickerSupported() {
/* 341 */     return isSupported("mouseclicker", MOUSE_CLICKER_SEGMENTS);
/*     */   }
/*     */   
/*     */   public static boolean isAutoReissueSupported() {
/* 345 */     return isSupported("autoreissue", AUTO_REISSUE_SEGMENTS);
/*     */   }
/*     */   
/*     */   public static boolean hasMirroredModuleRule(String moduleId) {
/* 349 */     if (moduleId == null || moduleId.isEmpty()) {
/* 350 */       return false;
/*     */     }
/* 352 */     return MIRRORED_MODULE_SEGMENTS.containsKey(moduleId.toLowerCase());
/*     */   }
/*     */   
/*     */   public static boolean isModuleAllowedByMirroredRules(String moduleId) {
/* 356 */     if (moduleId == null || moduleId.isEmpty()) {
/* 357 */       return true;
/*     */     }
/*     */     
/* 360 */     Set<String> allowedSegments = MIRRORED_MODULE_SEGMENTS.get(moduleId.toLowerCase());
/* 361 */     if (allowedSegments == null) {
/* 362 */       return true;
/*     */     }
/*     */     
/* 365 */     return isSupported(moduleId, allowedSegments);
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
/*     */   private static boolean isSupported(String moduleId, Set<String> allowedSegments) {
/* 386 */     if (RemoteRulesService.getInstance().isModuleExplicitlyAllowed(moduleId)) {
/* 387 */       return true;
/*     */     }
/* 389 */     return isSingleplayerOrHasAnyServerSegment(allowedSegments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getServerAddress() {
/* 396 */     return getCurrentServerHost();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\ServerUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */