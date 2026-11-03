/*     */ package vorga.phazeclient.base.util.color;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
/*     */ import java.awt.Color;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_3532;
/*     */ import org.joml.Vector4i;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ColorUtil
/*     */ {
/*     */   private ColorUtil() {
/*  17 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*  18 */   } public static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)§[0-9a-f-or]");
/*  19 */   public static Char2IntArrayMap colorCodes = new Char2IntArrayMap()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final int RED = getColor(255, 0, 0);
/*  39 */   public static final int GREEN = getColor(0, 255, 0);
/*  40 */   public static final int BLUE = getColor(0, 0, 255);
/*  41 */   public static final int YELLOW = getColor(255, 255, 0);
/*  42 */   public static final int WHITE = getColor(255);
/*  43 */   public static final int BLACK = getColor(0);
/*  44 */   public static final int HALF_BLACK = getColor(0, 0.5F);
/*  45 */   public static final int LIGHT_RED = getColor(255, 85, 85);
/*     */   
/*     */   public static int red(int c) {
/*  48 */     return c >> 16 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int green(int c) {
/*  52 */     return c >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int blue(int c) {
/*  56 */     return c & 0xFF;
/*     */   }
/*     */   
/*     */   public static int alpha(int c) {
/*  60 */     return c >> 24 & 0xFF;
/*     */   }
/*     */   
/*     */   public static float redf(int c) {
/*  64 */     return red(c) / 255.0F;
/*     */   }
/*     */   
/*     */   public static float greenf(int c) {
/*  68 */     return green(c) / 255.0F;
/*     */   }
/*     */   
/*     */   public static float bluef(int c) {
/*  72 */     return blue(c) / 255.0F;
/*     */   }
/*     */   
/*     */   public static float alphaf(int c) {
/*  76 */     return alpha(c) / 255.0F;
/*     */   }
/*     */   
/*     */   public static int[] getRGBA(int c) {
/*  80 */     return new int[] { red(c), green(c), blue(c), alpha(c) };
/*     */   }
/*     */   
/*     */   public static int[] getRGB(int c) {
/*  84 */     return new int[] { red(c), green(c), blue(c) };
/*     */   }
/*     */   
/*     */   public static float[] getRGBAf(int c) {
/*  88 */     return new float[] { redf(c), greenf(c), bluef(c), alphaf(c) };
/*     */   }
/*     */   
/*     */   public static float[] getRGBf(int c) {
/*  92 */     return new float[] { redf(c), greenf(c), bluef(c) };
/*     */   }
/*     */   
/*     */   public static int getColor(float red, float green, float blue, float alpha) {
/*  96 */     return getColor(Math.round(red * 255.0F), Math.round(green * 255.0F), Math.round(blue * 255.0F), Math.round(alpha * 255.0F));
/*     */   }
/*     */   
/*     */   public static int getColor(int red, int green, int blue, float alpha) {
/* 100 */     return getColor(red, green, blue, Math.round(alpha * 255.0F));
/*     */   }
/*     */   
/*     */   public static int getColor(float red, float green, float blue) {
/* 104 */     return getColor(red, green, blue, 1.0F);
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness, int alpha) {
/* 108 */     return getColor(brightness, brightness, brightness, alpha);
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness, float alpha) {
/* 112 */     return getColor(brightness, Math.round(alpha * 255.0F));
/*     */   }
/*     */   
/*     */   public static int getColor(int brightness) {
/* 116 */     return getColor(brightness, brightness, brightness);
/*     */   }
/*     */   
/*     */   public static int replAlpha(int color, int alpha) {
/* 120 */     return getColor(red(color), green(color), blue(color), alpha);
/*     */   }
/*     */   
/*     */   public static int replAlpha(int color, float alpha) {
/* 124 */     return getColor(red(color), green(color), blue(color), alpha);
/*     */   }
/*     */   
/*     */   public static int multAlpha(int color, float percent01) {
/* 128 */     return getColor(red(color), green(color), blue(color), Math.round(alpha(color) * percent01));
/*     */   }
/*     */   
/*     */   public static int multColor(int colorStart, int colorEnd, float progress) {
/* 132 */     return getColor(Math.round(red(colorStart) * redf(colorEnd) * progress), Math.round(green(colorStart) * greenf(colorEnd) * progress), 
/* 133 */         Math.round(blue(colorStart) * bluef(colorEnd) * progress), Math.round(alpha(colorStart) * alphaf(colorEnd) * progress));
/*     */   }
/*     */   
/*     */   public static int multRed(int colorStart, int colorEnd, float progress) {
/* 137 */     return getColor(Math.round(red(colorStart) * redf(colorEnd) * progress), Math.round(green(colorStart) * greenf(colorEnd) * progress), 
/* 138 */         Math.round(blue(colorStart) * bluef(colorEnd) * progress), Math.round(alpha(colorStart) * alphaf(colorEnd) * progress));
/*     */   }
/*     */   
/*     */   public static int multDark(int color, float percent01) {
/* 142 */     return getColor(
/* 143 */         Math.round(red(color) * percent01), 
/* 144 */         Math.round(green(color) * percent01), 
/* 145 */         Math.round(blue(color) * percent01), 
/* 146 */         alpha(color));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int multBright(int color, float percent01) {
/* 151 */     return getColor(
/* 152 */         Math.min(255, Math.round(red(color) / percent01)), 
/* 153 */         Math.min(255, Math.round(green(color) / percent01)), 
/* 154 */         Math.min(255, Math.round(blue(color) / percent01)), 
/* 155 */         alpha(color));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int overCol(int color1, int color2, float percent01) {
/* 160 */     float percent = class_3532.method_15363(percent01, 0.0F, 1.0F);
/* 161 */     return getColor(
/* 162 */         class_3532.method_48781(percent, red(color1), red(color2)), 
/* 163 */         class_3532.method_48781(percent, green(color1), green(color2)), 
/* 164 */         class_3532.method_48781(percent, blue(color1), blue(color2)), 
/* 165 */         class_3532.method_48781(percent, alpha(color1), alpha(color2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vector4i multRedAndAlpha(Vector4i color, float red, float alpha) {
/* 170 */     return new Vector4i(multRedAndAlpha(color.x, red, alpha), multRedAndAlpha(color.y, red, alpha), multRedAndAlpha(color.w, red, alpha), multRedAndAlpha(color.z, red, alpha));
/*     */   }
/*     */   
/*     */   public static int multRedAndAlpha(int color, float red, float alpha) {
/* 174 */     return getColor(red(color), Math.min(255, Math.round(green(color) / red)), Math.min(255, Math.round(blue(color) / red)), Math.round(alpha(color) * alpha));
/*     */   }
/*     */   
/*     */   public static int multRed(int color, float percent01) {
/* 178 */     return getColor(red(color), Math.min(255, Math.round(green(color) / percent01)), Math.min(255, Math.round(blue(color) / percent01)), alpha(color));
/*     */   }
/*     */   
/*     */   public static int multGreen(int color, float percent01) {
/* 182 */     return getColor(Math.min(255, Math.round(green(color) / percent01)), green(color), Math.min(255, Math.round(blue(color) / percent01)), alpha(color));
/*     */   }
/*     */   
/*     */   public static int gradientToRed(int originalColor, float damageIntensity) {
/* 186 */     damageIntensity = class_3532.method_15363(damageIntensity, 0.0F, 1.0F);
/*     */     
/* 188 */     int originalRed = red(originalColor);
/* 189 */     int originalGreen = green(originalColor);
/* 190 */     int originalBlue = blue(originalColor);
/* 191 */     int originalAlpha = alpha(originalColor);
/*     */     
/* 193 */     int targetRed = 255;
/* 194 */     int targetGreen = 0;
/* 195 */     int targetBlue = 0;
/*     */     
/* 197 */     int newRed = Math.round(class_3532.method_48781(damageIntensity, originalRed, targetRed));
/* 198 */     int newGreen = Math.round(class_3532.method_48781(damageIntensity, originalGreen, targetGreen));
/* 199 */     int newBlue = Math.round(class_3532.method_48781(damageIntensity, originalBlue, targetBlue));
/*     */     
/* 201 */     return getColor(newRed, newGreen, newBlue, originalAlpha);
/*     */   }
/*     */   
/*     */   public static int[] genGradientForText(int color1, int color2, int length) {
/* 205 */     int[] gradient = new int[length];
/* 206 */     for (int i = 0; i < length; i++) {
/* 207 */       float pc = i / (length - 1);
/* 208 */       gradient[i] = overCol(color1, color2, pc);
/*     */     } 
/* 210 */     return gradient;
/*     */   }
/*     */   
/*     */   public static int rainbow(int speed, int index, float saturation, float brightness, float opacity) {
/* 214 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 215 */     float hue = angle / 360.0F;
/* 216 */     int color = Color.HSBtoRGB(hue, saturation, brightness);
/* 217 */     return getColor(red(color), green(color), blue(color), Math.round(opacity * 255.0F));
/*     */   }
/*     */   
/*     */   public static int fade(int speed, int index, int first, int second) {
/* 221 */     int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
/* 222 */     angle = (angle >= 180) ? (360 - angle) : angle;
/* 223 */     return overCol(first, second, angle / 180.0F);
/*     */   }
/*     */   
/*     */   public static int fade(int index) {
/* 227 */     Color clientColor = new Color(getClientColor());
/* 228 */     return fade(8, index, clientColor.brighter().getRGB(), clientColor.darker().getRGB());
/*     */   }
/*     */   
/*     */   public static int fade(int index, int indexMultiplier) {
/* 232 */     Color clientColor = new Color(getClientColor());
/* 233 */     return fade(8, index * indexMultiplier, clientColor.brighter().getRGB(), clientColor.darker().getRGB());
/*     */   }
/*     */   
/*     */   public static int invertFade(int index) {
/* 237 */     Color clientColor = new Color(getClientColor());
/* 238 */     return fade(8, index, clientColor.darker().getRGB(), clientColor.brighter().getRGB());
/*     */   }
/*     */   
/*     */   public static int multiColorFade(int index) {
/* 242 */     int[] colors = getClientColors();
/* 243 */     if (colors.length == 0) return getColor(255, 255, 255); 
/* 244 */     if (colors.length == 1) return colors[0];
/*     */     
/* 246 */     float speedMultiplier = Theme.getInstance().getFadeSpeed();
/* 247 */     long currentTime = System.currentTimeMillis();
/*     */     
/* 249 */     if (colors.length == 2) {
/* 250 */       double d = 8.0D / speedMultiplier;
/* 251 */       int angle = (int)((currentTime / d + index) % 360.0D);
/* 252 */       angle = (angle >= 180) ? (360 - angle) : angle;
/* 253 */       return overCol(colors[0], colors[1], angle / 180.0F);
/*     */     } 
/* 255 */     double adjustedSpeed = 10.0D / speedMultiplier;
/* 256 */     float timeProgress = (float)((currentTime / adjustedSpeed + (index * 100)) % (colors.length * 360L)) / 360.0F;
/*     */     
/* 258 */     int index1 = (int)Math.floor(timeProgress) % colors.length;
/* 259 */     int index2 = (index1 + 1) % colors.length;
/* 260 */     float lerp = timeProgress - (int)Math.floor(timeProgress);
/*     */     
/* 262 */     return overCol(colors[index1], colors[index2], lerp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vector4i roundClientColor(float alpha) {
/* 267 */     return new Vector4i(multAlpha(multiColorFade(270), alpha), multAlpha(multiColorFade(0), alpha), 
/* 268 */         multAlpha(multiColorFade(180), alpha), multAlpha(multiColorFade(90), alpha));
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
/*     */   public static int getColor(int red, int green, int blue, int alpha) {
/* 287 */     return computeColor(red, green, blue, alpha);
/*     */   }
/*     */   
/*     */   public static int getColor(int red, int green, int blue) {
/* 291 */     return getColor(red, green, blue, 255);
/*     */   }
/*     */   
/*     */   private static int computeColor(int red, int green, int blue, int alpha) {
/* 295 */     return class_3532.method_15340(alpha, 0, 255) << 24 | 
/* 296 */       class_3532.method_15340(red, 0, 255) << 16 | 
/* 297 */       class_3532.method_15340(green, 0, 255) << 8 | 
/* 298 */       class_3532.method_15340(blue, 0, 255);
/*     */   }
/*     */   
/*     */   public static String formatting(int color) {
/* 302 */     return "⏏" + color + "⏏";
/*     */   }
/*     */   
/*     */   public static int lighter(int hex) {
/* 306 */     return lighter(hex, 1);
/*     */   }
/*     */   
/*     */   public static int lighter(int hex, int steps) {
/* 310 */     int a = hex >> 24 & 0xFF;
/* 311 */     int r = hex >> 16 & 0xFF;
/* 312 */     int g = hex >> 8 & 0xFF;
/* 313 */     int b = hex & 0xFF;
/*     */     
/* 315 */     for (int i = 0; i < steps; i++) {
/* 316 */       r += (255 - r) / 10;
/* 317 */       g += (255 - g) / 10;
/* 318 */       b += (255 - b) / 10;
/*     */     } 
/*     */     
/* 321 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   public static int darker(int hex) {
/* 325 */     return darker(hex, 1);
/*     */   }
/*     */   
/*     */   public static int darker(int hex, int steps) {
/* 329 */     int a = hex >> 24 & 0xFF;
/* 330 */     int r = hex >> 16 & 0xFF;
/* 331 */     int g = hex >> 8 & 0xFF;
/* 332 */     int b = hex & 0xFF;
/*     */     
/* 334 */     for (int i = 0; i < steps; i++) {
/* 335 */       r -= r / 10;
/* 336 */       g -= g / 10;
/* 337 */       b -= b / 10;
/*     */     } 
/*     */     
/* 340 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   public static String removeFormatting(String text) {
/* 344 */     return (text == null || text.isEmpty()) ? null : FORMATTING_CODE_PATTERN.matcher(text).replaceAll("");
/*     */   }
/*     */   
/*     */   private static ColorPalette getPalette() {
/* 348 */     return Theme.getInstance().getCurrentPalette();
/*     */   }
/*     */   
/*     */   public static int getMainGuiColor() {
/* 352 */     return getPalette().mainGuiColor();
/*     */   }
/*     */   
/*     */   public static int getGuiRectColor(float alpha) {
/* 356 */     return multAlpha(getPalette().guiRectColor(), alpha);
/*     */   }
/*     */   
/*     */   public static int getGuiRectColor2(float alpha) {
/* 360 */     return multAlpha(getPalette().guiRectColor2(), alpha);
/*     */   }
/*     */   
/*     */   public static int getRect(float alpha) {
/* 364 */     return multAlpha(getPalette().rectColor(), alpha);
/*     */   }
/*     */   
/*     */   public static int getRectDarker(float alpha) {
/* 368 */     return multAlpha(getPalette().rectDarkerColor(), alpha);
/*     */   }
/*     */   
/*     */   public static int getText(float alpha) {
/* 372 */     return multAlpha(getText(), alpha);
/*     */   }
/*     */   
/*     */   public static int getText() {
/* 376 */     return getPalette().textColor();
/*     */   }
/*     */   
/*     */   public static int getDescription() {
/* 380 */     return getPalette().descriptionColor();
/*     */   }
/*     */   
/*     */   public static int getDescription(float alpha) {
/* 384 */     return multAlpha(getDescription(), alpha);
/*     */   }
/*     */   
/*     */   public static int getClientColorByIndex(int index) {
/* 388 */     int[] colors = getClientColors();
/* 389 */     if (colors.length == 0) {
/* 390 */       return getColor(255, 255, 255);
/*     */     }
/* 392 */     return colors[Math.max(0, Math.min(index, colors.length - 1))];
/*     */   }
/*     */   
/*     */   public static int getClientColor() {
/* 396 */     int[] colors = getClientColors();
/* 397 */     if (colors.length == 0) return getColor(255, 255, 255); 
/* 398 */     if (colors.length == 1) return colors[0];
/*     */     
/* 400 */     return multiColorFade(0);
/*     */   }
/*     */   
/*     */   public static int getClientColorFade(int offset) {
/* 404 */     int[] colors = getClientColors();
/* 405 */     if (colors.length == 0) return getColor(255, 255, 255); 
/* 406 */     if (colors.length == 1) return colors[0];
/*     */     
/* 408 */     return multiColorFade(offset);
/*     */   }
/*     */   
/*     */   public static int getClientColor(float alpha) {
/* 412 */     return multAlpha(getClientColor(), alpha);
/*     */   }
/*     */   
/*     */   public static int getClientColorFade(int offset, float alpha) {
/* 416 */     return multAlpha(getClientColorFade(offset), alpha);
/*     */   }
/*     */   
/*     */   public static int[] getClientColors() {
/* 420 */     return Theme.getInstance().getClientColors();
/*     */   }
/*     */   
/*     */   public static int getFriendColor() {
/* 424 */     return getPalette().friendColor();
/*     */   }
/*     */   
/*     */   public static int getOutline(float alpha, float bright) {
/* 428 */     return multBright(multAlpha(getOutline(), alpha), bright);
/*     */   }
/*     */   
/*     */   public static int getOutline(float alpha) {
/* 432 */     return multAlpha(getOutline(), alpha);
/*     */   }
/*     */   
/*     */   public static int getOutline() {
/* 436 */     return getPalette().outlineColor();
/*     */   }
/*     */   
/*     */   public static boolean isDarkBackground() {
/* 440 */     int backgroundColor = getRect(1.0F);
/* 441 */     int r = red(backgroundColor);
/* 442 */     int g = green(backgroundColor);
/* 443 */     int b = blue(backgroundColor);
/* 444 */     double luminance = (0.2126D * r + 0.7152D * g + 0.0722D * b) / 255.0D;
/* 445 */     return (luminance < 0.5D);
/*     */   }
/*     */   
/*     */   public static int[] getBadgeColors(String role) {
/* 449 */     switch (role.toUpperCase()) { case "YOUTUBE":
/* 450 */         (new int[2])[0] = 
/* 451 */           parseColor("#de0000"); (new int[2])[1] = 
/* 452 */           parseColor("#ffffff");
/*     */       case "DEVELOPER":
/* 454 */         (new int[2])[0] = 
/* 455 */           parseColor("#ff0000"); (new int[2])[1] = 
/* 456 */           parseColor("#7a0000");
/*     */       case "TESTER":
/* 458 */         (new int[2])[0] = 
/* 459 */           parseColor("#2ecc71"); (new int[2])[1] = 
/* 460 */           parseColor("#1abc9c");
/*     */       case "PASTER":
/* 462 */         (new int[2])[0] = 
/* 463 */           parseColor("#fcff5e"); (new int[2])[1] = 
/* 464 */           parseColor("#fbff00");
/*     */       case "CROW":
/* 466 */         (new int[2])[0] = 
/* 467 */           parseColor("#72eeff"); (new int[2])[1] = 
/* 468 */           parseColor("#27b3ff"); }
/*     */     
/* 470 */     return new int[] {
/* 471 */         parseColor("#525050"), 
/* 472 */         parseColor("#a39e9e")
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseColor(String hex) {
/* 478 */     if (hex.startsWith("#")) {
/* 479 */       hex = hex.substring(1);
/*     */     }
/* 481 */     return getColor(
/* 482 */         Integer.parseInt(hex.substring(0, 2), 16), 
/* 483 */         Integer.parseInt(hex.substring(2, 4), 16), 
/* 484 */         Integer.parseInt(hex.substring(4, 6), 16), 255);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\color\ColorUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */