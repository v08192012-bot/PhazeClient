/*     */ package vorga.phazeclient.base.util;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_5250;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PhazeBadgeUtil
/*     */ {
/*  23 */   public static final class_2960 BADGE_ICON = class_2960.method_60655("phaze", "textures/gui/phaze_user_badge.png");
/*     */   
/*  25 */   public static final class_2960 CODE_BADGE_ICON = class_2960.method_60655("phaze", "textures/gui/phaze_code_badge.png");
/*     */   private static final int CODE_BADGE_RGB = 16711680;
/*     */   private static final String BADGE_PADDING = "  ";
/*  28 */   private static final Pattern LEADING_TAGS = Pattern.compile("^(?:\\[[^\\]]*]\\s*)+");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPhazeUser(String username) {
/*  34 */     return RemoteRulesService.getInstance().isKnownClientUser(username);
/*     */   }
/*     */   
/*     */   public static boolean isCodeBadgeUser(String username) {
/*  38 */     return (username != null && ("nomadvorgayt"
/*  39 */       .equalsIgnoreCase(username) || "nomadvorga".equalsIgnoreCase(username)));
/*     */   }
/*     */   
/*     */   public static boolean hasBadgePadding(class_2561 text) {
/*  43 */     return (text != null && hasBadgePadding(text.getString()));
/*     */   }
/*     */   
/*     */   public static boolean hasBadgePadding(String text) {
/*  47 */     return (text != null && text.startsWith("  "));
/*     */   }
/*     */   
/*     */   public static class_2561 withBadgePadding(class_2561 text) {
/*  51 */     return withBadgePadding(text, "  ".length());
/*     */   }
/*     */   
/*     */   public static class_2561 withBadgePadding(class_2561 text, int spaces) {
/*  55 */     if (text == null || hasBadgePadding(text)) {
/*  56 */       return text;
/*     */     }
/*  58 */     class_5250 prefixed = class_2561.method_43470(" ".repeat(Math.max(1, spaces)));
/*  59 */     prefixed.method_10852((class_2561)text.method_27661());
/*  60 */     return (class_2561)prefixed;
/*     */   }
/*     */   
/*     */   public static String extractChatSender(String flat) {
/*  64 */     if (flat == null || flat.isBlank()) {
/*  65 */       return null;
/*     */     }
/*     */     
/*  68 */     String trimmed = stripBadgePadding(flat).stripLeading();
/*  69 */     if (trimmed.startsWith("<")) {
/*  70 */       int close = trimmed.indexOf('>');
/*  71 */       if (close > 1) {
/*  72 */         return sanitizeUsername(trimmed.substring(1, close));
/*     */       }
/*     */     } 
/*     */     
/*  76 */     int separator = findHeaderSeparator(trimmed, 80);
/*  77 */     if (separator < 0) {
/*  78 */       return findKnownUserNearChatStart(trimmed);
/*     */     }
/*     */     
/*  81 */     String header = trimmed.substring(0, separator).trim();
/*  82 */     String detected = findKnownUserNearChatStart(header);
/*  83 */     if (detected != null) {
/*  84 */       return detected;
/*     */     }
/*     */     
/*  87 */     header = LEADING_TAGS.matcher(header).replaceFirst("").trim();
/*  88 */     int trailingTag = header.lastIndexOf(']');
/*  89 */     if (trailingTag >= 0 && trailingTag + 1 < header.length()) {
/*  90 */       header = header.substring(trailingTag + 1).trim();
/*     */     }
/*     */     
/*  93 */     if (header.isEmpty()) {
/*  94 */       return null;
/*     */     }
/*     */     
/*  97 */     String[] tokens = header.split("\\s+");
/*  98 */     return (tokens.length == 0) ? null : sanitizeUsername(tokens[tokens.length - 1]);
/*     */   }
/*     */   
/*     */   public static String extractNametagIdentity(String flat) {
/* 102 */     if (flat == null || flat.isBlank()) {
/* 103 */       return null;
/*     */     }
/* 105 */     String trimmed = stripBadgePadding(flat).trim();
/* 106 */     int suffix = trimmed.indexOf(" | ");
/* 107 */     if (suffix > 0) {
/* 108 */       trimmed = trimmed.substring(0, suffix);
/*     */     }
/* 110 */     String direct = sanitizeUsername(trimmed);
/* 111 */     if (direct != null && isPhazeUser(direct)) {
/* 112 */       return direct;
/*     */     }
/*     */     
/* 115 */     String embedded = PhazePlayerPresence.getInstance().findKnownUserInText(trimmed);
/* 116 */     if (embedded != null) {
/* 117 */       return embedded;
/*     */     }
/*     */     
/* 120 */     return direct;
/*     */   }
/*     */   
/*     */   public static float guiBadgeSize(class_327 renderer) {
/* 124 */     if (renderer == null) {
/* 125 */       return 12.0F;
/*     */     }
/* 127 */     Objects.requireNonNull(renderer); return Math.max(8.0F, 9.0F - 1.0F) * 1.5F;
/*     */   }
/*     */   
/*     */   public static int alphaWhite(int color) {
/* 131 */     return color & 0xFF000000 | 0xFFFFFF;
/*     */   }
/*     */   
/*     */   public static void drawGuiBadge(class_332 context, float x, float y, float size, int color) {
/* 135 */     drawGuiBadge(context, x, y, size, color, false);
/*     */   }
/*     */   
/*     */   public static void drawGuiBadge(class_332 context, float x, float y, float size, int color, boolean codeBadge) {
/* 139 */     int drawSize = Math.max(1, Math.round(size));
/* 140 */     context.method_25293(class_1921::method_62277, 
/*     */         
/* 142 */         badgeIcon(codeBadge), 
/* 143 */         Math.round(x), 
/* 144 */         Math.round(y), 0.0F, 0.0F, drawSize, drawSize, 64, 64, 64, 64, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         badgeColor(color, codeBadge));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawChatBadgeAsText(class_332 context, class_327 renderer, float x, float y, int color) {
/* 158 */     drawChatBadgeAsText(context, renderer, x, y, color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawChatBadgeAsText(class_332 context, class_327 renderer, float x, float y, int color, boolean codeBadge) {
/* 164 */     Objects.requireNonNull(renderer); float size = (renderer == null) ? 8.0F : Math.max(8.0F, 9.0F - 1.0F);
/* 165 */     drawGuiBadge(context, Math.round(x), Math.round(y + 1.0F), size, color, codeBadge);
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
/*     */   public static void drawWorldBadge(Matrix4f matrix, class_4597 vertexConsumers, class_327.class_6415 layerType, float x, float y, float size, int light, int color, boolean codeBadge) {
/* 179 */     class_4588 vertexConsumer = vertexConsumers.getBuffer(resolveTextRenderLayer(layerType, codeBadge));
/* 180 */     int badgeColor = badgeColor(color, codeBadge);
/* 181 */     vertexConsumer.method_22918(matrix, x, y, 0.0F).method_39415(badgeColor).method_22913(0.0F, 0.0F).method_60803(light);
/* 182 */     vertexConsumer.method_22918(matrix, x, y + size, 0.0F).method_39415(badgeColor).method_22913(0.0F, 1.0F).method_60803(light);
/* 183 */     vertexConsumer.method_22918(matrix, x + size, y + size, 0.0F).method_39415(badgeColor).method_22913(1.0F, 1.0F).method_60803(light);
/* 184 */     vertexConsumer.method_22918(matrix, x + size, y, 0.0F).method_39415(badgeColor).method_22913(1.0F, 0.0F).method_60803(light);
/*     */   }
/*     */   
/*     */   private static String stripBadgePadding(String text) {
/* 188 */     if (text == null) {
/* 189 */       return null;
/*     */     }
/* 191 */     return text.startsWith("  ") ? text.substring("  ".length()) : text;
/*     */   }
/*     */   
/*     */   private static int findHeaderSeparator(String text, int maxChars) {
/* 195 */     int limit = Math.min(text.length(), maxChars);
/* 196 */     for (int i = 0; i < limit; i++) {
/* 197 */       char c = text.charAt(i);
/* 198 */       if (c == ':' || c == '»' || c == '>' || c == '›' || c == '→' || c == '➜' || c == '➤' || c == '➡') {
/* 199 */         return i;
/*     */       }
/*     */     } 
/* 202 */     return -1;
/*     */   }
/*     */   
/*     */   private static String findKnownUserNearChatStart(String text) {
/* 206 */     if (text == null || text.isBlank()) {
/* 207 */       return null;
/*     */     }
/* 209 */     String head = text.substring(0, Math.min(text.length(), 96)).trim();
/* 210 */     if (head.isEmpty()) {
/* 211 */       return null;
/*     */     }
/* 213 */     return PhazePlayerPresence.getInstance().findKnownUserInText(head);
/*     */   }
/*     */   
/*     */   private static String sanitizeUsername(String raw) {
/* 217 */     if (raw == null) {
/* 218 */       return null;
/*     */     }
/*     */     
/* 221 */     int start = 0;
/* 222 */     int end = raw.length();
/* 223 */     while (start < end && !isUsernameChar(raw.charAt(start))) {
/* 224 */       start++;
/*     */     }
/* 226 */     while (end > start && !isUsernameChar(raw.charAt(end - 1))) {
/* 227 */       end--;
/*     */     }
/* 229 */     if (start >= end) {
/* 230 */       return null;
/*     */     }
/*     */     
/* 233 */     String candidate = raw.substring(start, end).trim();
/* 234 */     return candidate.isEmpty() ? null : candidate.toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   private static boolean isUsernameChar(char c) {
/* 238 */     return (c == '_' || Character.isLetterOrDigit(c));
/*     */   }
/*     */   
/*     */   private static class_2960 badgeIcon(boolean codeBadge) {
/* 242 */     return codeBadge ? CODE_BADGE_ICON : BADGE_ICON;
/*     */   }
/*     */   
/*     */   private static int badgeColor(int color, boolean codeBadge) {
/* 246 */     return codeBadge ? (color & 0xFF000000 | 0xFF0000) : color;
/*     */   }
/*     */   
/*     */   private static class_1921 resolveTextRenderLayer(class_327.class_6415 layerType, boolean codeBadge) {
/* 250 */     class_2960 icon = badgeIcon(codeBadge);
/* 251 */     switch (layerType) { default: throw new MatchException(null, null);case field_33994: case field_33995: case field_33993: break; }  return 
/*     */ 
/*     */       
/* 254 */       class_1921.method_23028(icon);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\PhazeBadgeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */