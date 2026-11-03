/*     */ package vorga.phazeclient.api.system.font;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_4587;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.system.font.entry.DrawEntry;
/*     */ import vorga.phazeclient.api.system.font.glyph.Glyph;
/*     */ import vorga.phazeclient.api.system.font.glyph.GlyphMap;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ 
/*     */ public class FontRenderer implements QuickImports {
/*     */   public FontRenderer setFont(Font font) {
/*  36 */     this.font = font; return this;
/*     */   }
/*     */   
/*     */   private static final int STRING_BUILDER_POOL_SIZE = 16;
/*  40 */   private static final Queue<StringBuilder> STRING_BUILDER_POOL = new ArrayDeque<>(16);
/*     */   
/*     */   static {
/*  43 */     for (int i = 0; i < 16; i++) {
/*  44 */       STRING_BUILDER_POOL.offer(new StringBuilder(256));
/*     */     }
/*     */   }
/*     */   
/*  48 */   public static double ANIMATION_TIME = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final Pattern DECIMAL_COLOR_PATTERN = Pattern.compile("\\d+");
/*     */   
/*  54 */   private final Object2ObjectMap<class_2960, ObjectList<DrawEntry>> GLYPH_PAGE_CACHE = (Object2ObjectMap<class_2960, ObjectList<DrawEntry>>)new Object2ObjectOpenHashMap();
/*  55 */   private final ObjectList<GlyphMap> maps = (ObjectList<GlyphMap>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private final Char2ObjectOpenHashMap<Glyph> glyphCache = new Char2ObjectOpenHashMap(); private Font font; public Font getFont() {
/*  66 */     return this.font;
/*     */   }
/*     */   
/*     */   public FontRenderer(Font font, float sizePx) {
/*  70 */     init(font, sizePx);
/*     */   }
/*     */   
/*     */   private void init(Font font, float sizePx) {
/*  74 */     this.font = font.deriveFont(sizePx * 2.0F);
/*     */   }
/*     */   
/*     */   private GlyphMap generateMap(char from, char to) {
/*  78 */     GlyphMap glyphMap = new GlyphMap(from, to, this.font, randomIdentifier(), 5);
/*  79 */     this.maps.add(glyphMap);
/*  80 */     return glyphMap;
/*     */   }
/*     */   
/*     */   private Glyph locateGlyph(char glyph) {
/*  84 */     Glyph cached = (Glyph)this.glyphCache.get(glyph);
/*  85 */     if (cached != null) {
/*  86 */       return cached;
/*     */     }
/*     */     
/*  89 */     for (ObjectListIterator<GlyphMap> objectListIterator = this.maps.iterator(); objectListIterator.hasNext(); ) { GlyphMap map = objectListIterator.next();
/*  90 */       if (map.contains(glyph)) {
/*  91 */         Glyph found = map.getGlyph(glyph);
/*  92 */         this.glyphCache.put(glyph, found);
/*  93 */         return found;
/*     */       }  }
/*     */ 
/*     */     
/*  97 */     char base = (char)MathUtil.floorNearestMulN(glyph, 128);
/*  98 */     Glyph result = generateMap(base, (char)(base + 128)).getGlyph(glyph);
/*  99 */     this.glyphCache.put(glyph, result);
/* 100 */     return result;
/*     */   }
/*     */   
/*     */   private static StringBuilder acquireStringBuilder() {
/* 104 */     StringBuilder sb = STRING_BUILDER_POOL.poll();
/* 105 */     if (sb == null) {
/* 106 */       sb = new StringBuilder(256);
/*     */     } else {
/* 108 */       sb.setLength(0);
/*     */     } 
/* 110 */     return sb;
/*     */   }
/*     */   
/*     */   private static void releaseStringBuilder(StringBuilder sb) {
/* 114 */     if (STRING_BUILDER_POOL.size() < 16) {
/* 115 */       sb.setLength(0);
/* 116 */       STRING_BUILDER_POOL.offer(sb);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawText(class_4587 matrix, class_2561 text, double x, double y) {
/* 121 */     StringBuilder sb = acquireStringBuilder();
/*     */     try {
/* 123 */       findStyle(sb, text);
/* 124 */       drawString(matrix, sb.toString(), x, y, ColorUtil.getText());
/*     */     } finally {
/* 126 */       releaseStringBuilder(sb);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void findStyle(StringBuilder sb, class_2561 component) {
/* 131 */     class_2583 style = component.method_10866();
/* 132 */     if (component.method_10855().isEmpty())
/* 133 */     { if (style.method_10973() != null) sb.append(ColorUtil.formatting(style.method_10973().method_27716())); 
/* 134 */       sb.append(component.getString()).append(class_124.field_1070); }
/* 135 */     else { component.method_36136(style).forEach(text -> findStyle(sb, text)); }
/*     */   
/*     */   }
/*     */   public void drawStringWithScroll(class_4587 matrix, String text, double x, double y, float width, int color) {
/* 139 */     String separation = "  |  ";
/* 140 */     float textWidth = getStringWidth(text + text);
/*     */     
/* 142 */     if (textWidth - width < 10.0F) {
/* 143 */       drawString(matrix, text, x, y, color);
/*     */       
/*     */       return;
/*     */     } 
/* 147 */     drawString(matrix, text + text + separation, x - MathUtil.textScrolling(textWidth), y, color);
/*     */   }
/*     */   
/*     */   public void drawString(class_4587 matrix, String text, double x, double y, int color) {
/* 151 */     int length = text.length();
/* 152 */     float xOffset = 0.0F;
/* 153 */     float yOffset = 0.0F;
/* 154 */     int lineStart = 0;
/*     */ 
/*     */     
/* 157 */     StringBuilder stringColor = null;
/* 158 */     boolean colorFormat = false;
/* 159 */     boolean textColor = false;
/* 160 */     int clr = color;
/*     */     
/* 162 */     for (int i = 0; i < length; i++) {
/* 163 */       char c = text.charAt(i);
/*     */       
/* 165 */       if (c == '§') {
/* 166 */         colorFormat = true;
/*     */       }
/* 168 */       else if (colorFormat) {
/* 169 */         colorFormat = false;
/* 170 */         char c1 = Character.toUpperCase(c);
/* 171 */         if (ColorUtil.colorCodes.containsKey(c1)) {
/*     */           
/* 173 */           clr = ColorUtil.colorCodes.get(c1) | 0xFF000000;
/* 174 */         } else if (c1 == 'R') {
/* 175 */           clr = color;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 180 */       else if (c == '⏏') {
/* 181 */         if (textColor && stringColor != null) {
/*     */           try {
/* 183 */             String colorString = stringColor.toString();
/*     */ 
/*     */             
/* 186 */             if (DECIMAL_COLOR_PATTERN.matcher(colorString).matches()) {
/* 187 */               clr = Integer.parseInt(colorString) | 0xFF000000;
/*     */             }
/* 189 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*     */           
/* 191 */           stringColor.setLength(0);
/*     */         } 
/* 193 */         textColor = !textColor;
/*     */       }
/* 195 */       else if (textColor) {
/* 196 */         if (stringColor == null) {
/* 197 */           stringColor = new StringBuilder(16);
/*     */         }
/* 199 */         stringColor.append(c);
/*     */ 
/*     */       
/*     */       }
/* 203 */       else if (c == '\n') {
/* 204 */         yOffset += getStringHeight(text.substring(lineStart, i)) - 2.0F;
/* 205 */         xOffset = 0.0F;
/* 206 */         lineStart = i + 1;
/*     */       }
/*     */       else {
/*     */         
/* 210 */         Glyph glyph = locateGlyph(c);
/* 211 */         if (glyph != null) {
/* 212 */           if (glyph.value() != ' ') {
/* 213 */             class_2960 i1 = (glyph.owner()).bindToTexture;
/* 214 */             DrawEntry entry = new DrawEntry(xOffset, yOffset, clr, glyph);
/* 215 */             ((ObjectList)this.GLYPH_PAGE_CACHE.computeIfAbsent(i1, integer -> new ObjectArrayList())).add(entry);
/*     */           } 
/* 217 */           xOffset += glyph.width();
/*     */         } 
/*     */       } 
/*     */     } 
/* 221 */     if (!this.GLYPH_PAGE_CACHE.isEmpty()) drawGlyphs(matrix, x, y); 
/* 222 */     clearGlyphCache();
/*     */   }
/*     */   
/*     */   public void drawGradientString(class_4587 matrix, String text, double x, double y, int colorStart, int colorEnd) {
/* 226 */     float xOffset = 0.0F;
/* 227 */     float yOffset = 0.0F;
/* 228 */     int lineStart = 0;
/* 229 */     int textLength = text.length();
/*     */     
/* 231 */     for (int i = 0; i < textLength; i++) {
/* 232 */       char c = text.charAt(i);
/* 233 */       if (c == '\n') {
/* 234 */         yOffset += getStringHeight(text.substring(lineStart, i)) - 2.0F;
/* 235 */         xOffset = 0.0F;
/* 236 */         lineStart = i + 1;
/*     */       } else {
/*     */         
/* 239 */         Glyph glyph = locateGlyph(c);
/* 240 */         if (glyph != null) {
/* 241 */           if (glyph.value() != ' ') {
/* 242 */             float t = i / (textLength - 1);
/* 243 */             int color = interpolateColor(colorStart, colorEnd, t);
/* 244 */             class_2960 i1 = (glyph.owner()).bindToTexture;
/* 245 */             DrawEntry entry = new DrawEntry(xOffset, yOffset, color, glyph);
/* 246 */             ((ObjectList)this.GLYPH_PAGE_CACHE.computeIfAbsent(i1, integer -> new ObjectArrayList())).add(entry);
/*     */           } 
/* 248 */           xOffset += glyph.width();
/*     */         } 
/*     */       } 
/*     */     } 
/* 252 */     if (!this.GLYPH_PAGE_CACHE.isEmpty()) drawGlyphs(matrix, x, y); 
/* 253 */     clearGlyphCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawAnimatedGradientString(class_4587 matrix, String text, double x, double y, int colorStart, int colorEnd, double waveLength, double speed) {
/* 258 */     ANIMATION_TIME = System.currentTimeMillis() / 1000.0D;
/*     */     
/* 260 */     float xOffset = 0.0F;
/* 261 */     float yOffset = 0.0F;
/* 262 */     int lineStart = 0;
/* 263 */     int textLength = text.length();
/*     */     
/* 265 */     for (int i = 0; i < textLength; i++) {
/* 266 */       char c = text.charAt(i);
/* 267 */       if (c == '\n') {
/* 268 */         yOffset += getStringHeight(text.substring(lineStart, i)) - 2.0F;
/* 269 */         xOffset = 0.0F;
/* 270 */         lineStart = i + 1;
/*     */       } else {
/*     */         
/* 273 */         Glyph glyph = locateGlyph(c);
/* 274 */         if (glyph != null) {
/* 275 */           if (glyph.value() != ' ') {
/* 276 */             float t = i / Math.max(1, textLength - 1);
/*     */             
/* 278 */             double wave = Math.sin(t * waveLength - ANIMATION_TIME * speed);
/* 279 */             float normalizedWave = (float)((wave + 1.0D) / 2.0D);
/*     */             
/* 281 */             int color = interpolateColor(colorStart, colorEnd, normalizedWave);
/* 282 */             class_2960 i1 = (glyph.owner()).bindToTexture;
/* 283 */             DrawEntry entry = new DrawEntry(xOffset, yOffset, color, glyph);
/* 284 */             ((ObjectList)this.GLYPH_PAGE_CACHE.computeIfAbsent(i1, integer -> new ObjectArrayList())).add(entry);
/*     */           } 
/* 286 */           xOffset += glyph.width();
/*     */         } 
/*     */       } 
/*     */     } 
/* 290 */     if (!this.GLYPH_PAGE_CACHE.isEmpty()) drawGlyphs(matrix, x, y); 
/* 291 */     clearGlyphCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawAnimatedGradientString(class_4587 matrix, String text, double x, double y, int colorStart, int colorEnd) {
/* 296 */     drawAnimatedGradientString(matrix, text, x, y, colorStart, colorEnd, Math.PI, 5.0D);
/*     */   }
/*     */   
/*     */   private void clearGlyphCache() {
/* 300 */     for (ObjectIterator<ObjectList<DrawEntry>> objectIterator = this.GLYPH_PAGE_CACHE.values().iterator(); objectIterator.hasNext(); ) { ObjectList<DrawEntry> list = objectIterator.next();
/* 301 */       list.clear(); }
/*     */     
/* 303 */     this.GLYPH_PAGE_CACHE.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawGlyphs(class_4587 matrix, double x, double y) {
/* 312 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 314 */     matrix.method_22903();
/* 315 */     matrix.method_22904(x, y - 3.0D, 0.0D);
/* 316 */     matrix.method_22905(0.5F, 0.5F, 1.0F);
/* 317 */     Matrix4f matrix4f = matrix.method_23760().method_23761();
/*     */     
/* 319 */     RenderSystem.enableBlend();
/* 320 */     RenderSystem.defaultBlendFunc();
/* 321 */     RenderSystem.setShader(class_10142.field_53880);
/* 322 */     for (ObjectIterator<class_2960> objectIterator = this.GLYPH_PAGE_CACHE.keySet().iterator(); objectIterator.hasNext(); ) { class_2960 identifier = objectIterator.next();
/* 323 */       RenderSystem.setShaderTexture(0, identifier);
/* 324 */       class_287 buffer = tessellator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 325 */       for (ObjectListIterator<DrawEntry> objectListIterator = ((ObjectList)this.GLYPH_PAGE_CACHE.get(identifier)).iterator(); objectListIterator.hasNext(); ) { DrawEntry drawEntry = objectListIterator.next();
/* 326 */         float x1 = drawEntry.atX();
/* 327 */         float y1 = drawEntry.atY();
/*     */         
/* 329 */         Glyph glyph = drawEntry.toDraw();
/* 330 */         GlyphMap glyphMap = glyph.owner();
/*     */         
/* 332 */         float width = glyph.width();
/* 333 */         float height = glyph.height();
/*     */         
/* 335 */         float u1 = glyph.u() / glyphMap.width;
/* 336 */         float v1 = glyph.v() / glyphMap.height;
/* 337 */         float u2 = (glyph.u() + glyph.width()) / glyphMap.width;
/* 338 */         float v2 = (glyph.v() + glyph.height()) / glyphMap.height;
/*     */         
/* 340 */         int color = drawEntry.color();
/*     */         
/* 342 */         buffer.method_22918(matrix4f, x1 + 0.0F, y1 + height, 0.0F).method_22913(u1, v2).method_39415(color);
/* 343 */         buffer.method_22918(matrix4f, x1 + width, y1 + height, 0.0F).method_22913(u2, v2).method_39415(color);
/* 344 */         buffer.method_22918(matrix4f, x1 + width, y1 + 0.0F, 0.0F).method_22913(u2, v1).method_39415(color);
/* 345 */         buffer.method_22918(matrix4f, x1 + 0.0F, y1 + 0.0F, 0.0F).method_22913(u1, v1).method_39415(color); }
/*     */       
/* 347 */       class_286.method_43433(buffer.method_60800()); }
/*     */     
/* 349 */     RenderSystem.disableBlend();
/* 350 */     matrix.method_22909();
/*     */   }
/*     */   
/*     */   public void drawCenteredString(class_4587 stack, String s, double x, double y, int color) {
/* 354 */     drawString(stack, s, (int)(x - (getStringWidth(s) / 2.0F)), (float)y, color);
/*     */   }
/*     */   
/*     */   public float getStringWidth(class_2561 text) {
/* 358 */     return (text != null) ? getStringWidth(text.getString()) : 0.0F;
/*     */   }
/*     */   
/*     */   public float getStringWidth(String text) {
/* 362 */     float currentLine = 0.0F;
/* 363 */     float maxPreviousLines = 0.0F;
/* 364 */     boolean ignore = false;
/*     */     
/* 366 */     for (int i = 0, n = text.length(); i < n; i++) {
/* 367 */       char c = text.charAt(i);
/* 368 */       if (ignore) {
/* 369 */         ignore = false;
/*     */       }
/* 371 */       else if (c == '§') {
/* 372 */         ignore = true;
/*     */       }
/* 374 */       else if (c == '\n') {
/* 375 */         maxPreviousLines = Math.max(currentLine, maxPreviousLines);
/* 376 */         currentLine = 0.0F;
/*     */       }
/*     */       else {
/*     */         
/* 380 */         Glyph glyph = locateGlyph(c);
/* 381 */         currentLine += (glyph == null) ? 0.0F : glyph.width();
/*     */       } 
/*     */     } 
/* 384 */     return Math.max(currentLine, maxPreviousLines) / 2.0F;
/*     */   }
/*     */   
/*     */   public float getStringHeight(class_2561 text) {
/* 388 */     return getStringHeight(text.getString());
/*     */   }
/*     */   
/*     */   public float getStringHeight(String text) {
/* 392 */     float currentLine = 0.0F;
/* 393 */     float previous = 0.0F;
/*     */     
/* 395 */     String source = text.isEmpty() ? " " : text;
/* 396 */     for (int i = 0, n = source.length(); i < n; i++) {
/* 397 */       char c = source.charAt(i);
/* 398 */       if (c == '\n') {
/* 399 */         currentLine = (currentLine == 0.0F) ? locateGlyph(' ').height() : currentLine;
/* 400 */         previous += currentLine;
/* 401 */         currentLine = 0.0F;
/*     */       } else {
/*     */         
/* 404 */         Glyph glyph = locateGlyph(c);
/* 405 */         currentLine = Math.max((glyph == null) ? 0.0F : glyph.height(), currentLine);
/*     */       } 
/*     */     } 
/* 408 */     return currentLine + previous;
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
/*     */   private int interpolateColor(int colorStart, int colorEnd, float t) {
/* 427 */     float startAlpha = (colorStart >> 24 & 0xFF) / 255.0F;
/* 428 */     float startRed = (colorStart >> 16 & 0xFF) / 255.0F;
/* 429 */     float startGreen = (colorStart >> 8 & 0xFF) / 255.0F;
/* 430 */     float startBlue = (colorStart & 0xFF) / 255.0F;
/*     */     
/* 432 */     float endAlpha = (colorEnd >> 24 & 0xFF) / 255.0F;
/* 433 */     float endRed = (colorEnd >> 16 & 0xFF) / 255.0F;
/* 434 */     float endGreen = (colorEnd >> 8 & 0xFF) / 255.0F;
/* 435 */     float endBlue = (colorEnd & 0xFF) / 255.0F;
/*     */     
/* 437 */     float alpha = startAlpha + t * (endAlpha - startAlpha);
/* 438 */     float red = startRed + t * (endRed - startRed);
/* 439 */     float green = startGreen + t * (endGreen - startGreen);
/* 440 */     float blue = startBlue + t * (endBlue - startBlue);
/*     */     
/* 442 */     return (int)(alpha * 255.0F) << 24 | (int)(red * 255.0F) << 16 | (int)(green * 255.0F) << 8 | (int)(blue * 255.0F);
/*     */   }
/*     */   @Contract(value = "-> new", pure = true)
/*     */   @NotNull
/*     */   public static class_2960 randomIdentifier() {
/* 447 */     return class_2960.method_60655("phaze", "temp/" + StringUtil.randomString(32));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\FontRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */