/*     */ package vorga.phazeclient.implement.menu;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3298;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_5944;
/*     */ import net.minecraft.class_9801;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.font.msdf.ResourceProvider;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UiMsdfIconAtlas
/*     */ {
/*  39 */   private static final class_310 MC = class_310.method_1551();
/*  40 */   private static final class_2960 ATLAS_TEXTURE_ID = class_2960.method_60655("phaze", "msdf/ui_icons.png");
/*  41 */   private static final class_2960 ATLAS_DATA_ID = class_2960.method_60655("phaze", "msdf/ui_icons.json");
/*  42 */   private static final class_2960 BACK_ARROW_ICON_ID = class_2960.method_60656("textures/back_arrow.png");
/*     */   private static final float DEFAULT_DISTANCE_RANGE = 12.0F;
/*     */   private static final float SMOOTHNESS = 0.42F;
/*     */   private static final float THICKNESS = 0.0F;
/*     */   private static final float UV_INSET = 1.0F;
/*  47 */   private static final Set<class_2960> RASTER_FALLBACK_ICONS = Set.of(BACK_ARROW_ICON_ID);
/*     */ 
/*     */ 
/*     */   
/*  51 */   private static final Map<class_2960, AtlasIcon> ATLAS_ICONS = new HashMap<>();
/*  52 */   private static final Set<class_2960> MISSING_ICONS = new HashSet<>();
/*  53 */   private static final Map<class_2960, Float> ASPECT_RATIO_CACHE = new HashMap<>();
/*     */   
/*     */   private static class_1044 atlasTexture;
/*  56 */   private static float distanceRange = 12.0F;
/*     */   
/*     */   private static boolean atlasLoaded;
/*     */   
/*     */   private static boolean filterApplied;
/*     */   
/*     */   private static boolean globalFailed;
/*     */   
/*     */   public static void warmup() {
/*  65 */     synchronized (UiMsdfIconAtlas.class) {
/*  66 */       ensureAtlasLoaded();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized void onResourcesReloaded() {
/*  72 */     filterApplied = false;
/*  73 */     atlasTexture = null;
/*  74 */     atlasLoaded = false;
/*     */   }
/*     */   
/*     */   public static void registerTexture(String texture) {
/*     */     try {
/*  79 */       registerTexture(class_2960.method_60654(texture));
/*  80 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized void registerTexture(class_2960 icon) {
/*  85 */     if (icon == null || globalFailed || RASTER_FALLBACK_ICONS.contains(icon)) {
/*     */       return;
/*     */     }
/*  88 */     ensureAtlasLoaded();
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
/*     */   public static boolean renderIcon(class_332 context, class_2960 icon, float x, float y, float width, float height, int color, boolean precise) {
/* 101 */     return renderIcon(context, icon, x, y, width, height, color, precise, 0.0F, 0.42F);
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
/*     */   public static boolean renderIcon(class_332 context, class_2960 icon, float x, float y, float width, float height, int color, boolean precise, float thickness, float smoothness) {
/* 116 */     AtlasIcon atlasIcon = ensureIconReady(icon);
/* 117 */     if (atlasIcon == null) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     float x1 = precise ? x : Math.round(x);
/* 122 */     float y1 = precise ? y : Math.round(y);
/* 123 */     float drawWidth = precise ? Math.max(1.0F, width) : Math.max(1.0F, Math.round(width));
/* 124 */     float drawHeight = precise ? Math.max(1.0F, height) : Math.max(1.0F, Math.round(height));
/* 125 */     FittedRect fittedRect = fitRect(x1, y1, drawWidth, drawHeight, atlasIcon.aspectRatio);
/* 126 */     return renderQuad(context
/* 127 */         .method_51448().method_23760().method_23761(), atlasIcon, fittedRect.left, fittedRect.top, fittedRect.right, fittedRect.bottom, color, false, thickness, smoothness);
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
/*     */   public static boolean renderIcon(class_4587 matrix, class_2960 icon, float x, float y, float width, float height, int color) {
/* 149 */     AtlasIcon atlasIcon = ensureIconReady(icon);
/* 150 */     if (atlasIcon == null) {
/* 151 */       return false;
/*     */     }
/* 153 */     FittedRect fittedRect = fitRect(x, y, Math.max(1.0F, width), Math.max(1.0F, height), atlasIcon.aspectRatio);
/* 154 */     return renderQuad(matrix.method_23760().method_23761(), atlasIcon, fittedRect.left, fittedRect.top, fittedRect.right, fittedRect.bottom, color, true, 0.0F, 0.42F);
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
/*     */   public static boolean renderIcon(Matrix4f matrix, class_2960 icon, float x, float y, float width, float height, int color, boolean precise) {
/* 167 */     AtlasIcon atlasIcon = ensureIconReady(icon);
/* 168 */     if (atlasIcon == null) {
/* 169 */       return false;
/*     */     }
/*     */     
/* 172 */     float x1 = precise ? x : Math.round(x);
/* 173 */     float y1 = precise ? y : Math.round(y);
/* 174 */     float drawWidth = precise ? Math.max(1.0F, width) : Math.max(1.0F, Math.round(width));
/* 175 */     float drawHeight = precise ? Math.max(1.0F, height) : Math.max(1.0F, Math.round(height));
/* 176 */     FittedRect fittedRect = fitRect(x1, y1, drawWidth, drawHeight, atlasIcon.aspectRatio);
/* 177 */     return renderQuad(matrix, atlasIcon, fittedRect.left, fittedRect.top, fittedRect.right, fittedRect.bottom, color, false, 0.0F, 0.42F);
/*     */   }
/*     */   
/*     */   private static synchronized boolean ensureAtlasLoaded() {
/* 181 */     if (atlasLoaded) {
/* 182 */       return true;
/*     */     }
/* 184 */     if (globalFailed || MC == null || MC.method_1531() == null) {
/* 185 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 189 */       AtlasDefinition definition = (AtlasDefinition)ResourceProvider.fromJsonToInstance(ATLAS_DATA_ID, AtlasDefinition.class);
/* 190 */       if (definition == null || definition.atlas == null) {
/* 191 */         globalFailed = true;
/* 192 */         return false;
/*     */       } 
/*     */       
/* 195 */       atlasTexture = MC.method_1531().method_4619(ATLAS_TEXTURE_ID);
/* 196 */       if (atlasTexture == null) {
/* 197 */         globalFailed = true;
/* 198 */         return false;
/*     */       } 
/*     */       
/* 201 */       float atlasWidth = Math.max(1.0F, definition.atlas.width);
/* 202 */       float atlasHeight = Math.max(1.0F, definition.atlas.height);
/* 203 */       distanceRange = (definition.atlas.distanceRange > 0.0F) ? definition.atlas.distanceRange : 12.0F;
/*     */       
/* 205 */       ATLAS_ICONS.clear();
/* 206 */       MISSING_ICONS.clear();
/* 207 */       if (definition.icons != null) {
/* 208 */         for (IconDefinition iconDefinition : definition.icons) {
/* 209 */           if (iconDefinition == null || iconDefinition.id == null || iconDefinition.atlasBounds == null) {
/*     */             continue;
/*     */           }
/*     */           
/*     */           try {
/* 214 */             class_2960 identifier = class_2960.method_60654(iconDefinition.id);
/* 215 */             AtlasBounds bounds = iconDefinition.atlasBounds;
/* 216 */             float minU = (bounds.left + 1.0F) / atlasWidth;
/* 217 */             float maxU = (bounds.right - 1.0F) / atlasWidth;
/* 218 */             float minV = (bounds.top + 1.0F) / atlasHeight;
/* 219 */             float maxV = (bounds.bottom - 1.0F) / atlasHeight;
/* 220 */             float iconWidth = Math.max(1.0F, bounds.right - bounds.left);
/* 221 */             float iconHeight = Math.max(1.0F, bounds.bottom - bounds.top);
/*     */ 
/*     */             
/* 224 */             float aspectRatio = (iconDefinition.contentAspectRatio > 0.0F) ? iconDefinition.contentAspectRatio : (iconWidth / iconHeight);
/* 225 */             ATLAS_ICONS.put(identifier, new AtlasIcon(minU, minV, maxU, maxV, aspectRatio));
/* 226 */             if (!RASTER_FALLBACK_ICONS.contains(identifier)) {
/* 227 */               ASPECT_RATIO_CACHE.put(identifier, Float.valueOf(aspectRatio));
/*     */             }
/* 229 */           } catch (Throwable throwable) {}
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 234 */       filterApplied = false;
/* 235 */       atlasLoaded = true;
/* 236 */       return true;
/* 237 */     } catch (Throwable ignored) {
/* 238 */       globalFailed = true;
/* 239 */       ATLAS_ICONS.clear();
/* 240 */       MISSING_ICONS.clear();
/* 241 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static synchronized AtlasIcon ensureIconReady(class_2960 icon) {
/* 246 */     if (icon == null || globalFailed || MC == null || MC.method_1531() == null || RASTER_FALLBACK_ICONS.contains(icon)) {
/* 247 */       return null;
/*     */     }
/* 249 */     if (!atlasLoaded && !ensureAtlasLoaded()) {
/* 250 */       return null;
/*     */     }
/* 252 */     if (MISSING_ICONS.contains(icon)) {
/* 253 */       return null;
/*     */     }
/*     */     
/* 256 */     AtlasIcon atlasIcon = ATLAS_ICONS.get(icon);
/* 257 */     if (atlasIcon == null) {
/* 258 */       MISSING_ICONS.add(icon);
/*     */     }
/* 260 */     return atlasIcon;
/*     */   }
/*     */   
/*     */   public static float resolveAspectRatio(class_2960 icon) {
/* 264 */     if (icon == null) {
/* 265 */       return 1.0F;
/*     */     }
/*     */     
/* 268 */     if (RASTER_FALLBACK_ICONS.contains(icon)) {
/* 269 */       return resolveResourceAspectRatio(icon);
/*     */     }
/*     */     
/* 272 */     Float cached = ASPECT_RATIO_CACHE.get(icon);
/* 273 */     if (cached != null && cached.floatValue() > 0.0F) {
/* 274 */       return cached.floatValue();
/*     */     }
/*     */     
/*     */     try {
/* 278 */       if (!atlasLoaded) {
/* 279 */         ensureAtlasLoaded();
/*     */       }
/*     */       
/* 282 */       AtlasIcon atlasIcon = ATLAS_ICONS.get(icon);
/* 283 */       if (atlasIcon != null && atlasIcon.aspectRatio > 0.0F) {
/* 284 */         ASPECT_RATIO_CACHE.put(icon, Float.valueOf(atlasIcon.aspectRatio));
/* 285 */         return atlasIcon.aspectRatio;
/*     */       } 
/*     */       
/* 288 */       return resolveResourceAspectRatio(icon);
/* 289 */     } catch (Throwable throwable) {
/*     */ 
/*     */       
/* 292 */       return 1.0F;
/*     */     } 
/*     */   }
/*     */   private static float resolveResourceAspectRatio(class_2960 icon) {
/*     */     try {
/* 297 */       Optional<class_3298> resource = MC.method_1478().method_14486(icon);
/* 298 */       if (resource.isPresent())
/* 299 */       { InputStream stream = ((class_3298)resource.get()).method_14482(); 
/* 300 */         try { class_1011 image = class_1011.method_4309(stream);
/* 301 */           float aspectRatio = image.method_4307() / Math.max(1, image.method_4323());
/* 302 */           image.close();
/* 303 */           ASPECT_RATIO_CACHE.put(icon, Float.valueOf(aspectRatio));
/* 304 */           float f1 = aspectRatio;
/* 305 */           if (stream != null) stream.close();  return f1; } catch (Throwable throwable) { if (stream != null)
/*     */             try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } 
/* 307 */     } catch (Throwable throwable) {}
/*     */     
/* 309 */     return 1.0F;
/*     */   }
/*     */   
/*     */   private static FittedRect fitRect(float x, float y, float width, float height, float aspectRatio) {
/* 313 */     float safeAspect = (aspectRatio > 0.0F) ? aspectRatio : 1.0F;
/* 314 */     float drawWidth = width;
/* 315 */     float drawHeight = drawWidth / safeAspect;
/*     */     
/* 317 */     if (drawHeight > height) {
/* 318 */       drawHeight = height;
/* 319 */       drawWidth = drawHeight * safeAspect;
/*     */     } 
/*     */     
/* 322 */     float offsetX = (width - drawWidth) * 0.5F;
/* 323 */     float offsetY = (height - drawHeight) * 0.5F;
/* 324 */     return new FittedRect(x + offsetX, y + offsetY, x + offsetX + drawWidth, y + offsetY + drawHeight);
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
/*     */   private static boolean renderQuad(Matrix4f matrix, AtlasIcon atlasIcon, float x1, float y1, float x2, float y2, int color, boolean legacyImageOrientation, float thickness, float smoothness) {
/* 339 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 341 */     if (!filterApplied && atlasTexture != null) {
/* 342 */       atlasTexture.method_4527(true, false);
/* 343 */       filterApplied = true;
/*     */     } 
/*     */     
/* 346 */     RenderSystem.enableBlend();
/* 347 */     RenderSystem.defaultBlendFunc();
/* 348 */     RenderSystem.disableCull();
/* 349 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 350 */     RenderSystem.setShaderTexture(0, ATLAS_TEXTURE_ID);
/*     */     
/* 352 */     class_5944 shader = RenderSystem.setShader(MsdfRenderer.MSDF_FONT_SHADER_KEY);
/* 353 */     if (shader != null) {
/* 354 */       shader.method_34582("Range").method_1251(distanceRange);
/* 355 */       shader.method_34582("Thickness").method_1251(thickness);
/* 356 */       shader.method_34582("Smoothness").method_1251(smoothness);
/* 357 */       shader.method_34582("Outline").method_35649(0);
/* 358 */       shader.method_34582("OutlineThickness").method_1251(0.0F);
/* 359 */       shader.method_34582("OutlineColor").method_35657(0.0F, 0.0F, 0.0F, 0.0F);
/* 360 */       shader.method_34582("EnableFadeout").method_35649(0);
/* 361 */       shader.method_34582("LabelFadeEnabled").method_35649(0);
/* 362 */       shader.method_34582("FadeoutStart").method_1251(0.0F);
/* 363 */       shader.method_34582("FadeoutEnd").method_1251(1.0F);
/* 364 */       shader.method_34582("MaxWidth").method_1251(0.0F);
/* 365 */       shader.method_34582("TextPosX").method_1251(x1);
/* 366 */       shader.method_34582("ColorModulator").method_35657(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 369 */     class_287 builder = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 370 */     if (legacyImageOrientation) {
/* 371 */       builder.method_22918(matrix, x1, y2, 0.0F).method_22913(atlasIcon.minU, atlasIcon.minV).method_39415(color);
/* 372 */       builder.method_22918(matrix, x2, y2, 0.0F).method_22913(atlasIcon.minU, atlasIcon.maxV).method_39415(color);
/* 373 */       builder.method_22918(matrix, x2, y1, 0.0F).method_22913(atlasIcon.maxU, atlasIcon.maxV).method_39415(color);
/* 374 */       builder.method_22918(matrix, x1, y1, 0.0F).method_22913(atlasIcon.maxU, atlasIcon.minV).method_39415(color);
/*     */     } else {
/* 376 */       builder.method_22918(matrix, x1, y1, 0.0F).method_22913(atlasIcon.minU, atlasIcon.minV).method_39415(color);
/* 377 */       builder.method_22918(matrix, x1, y2, 0.0F).method_22913(atlasIcon.minU, atlasIcon.maxV).method_39415(color);
/* 378 */       builder.method_22918(matrix, x2, y2, 0.0F).method_22913(atlasIcon.maxU, atlasIcon.maxV).method_39415(color);
/* 379 */       builder.method_22918(matrix, x2, y1, 0.0F).method_22913(atlasIcon.maxU, atlasIcon.minV).method_39415(color);
/*     */     } 
/*     */     
/* 382 */     class_9801 builtBuffer = builder.method_60794();
/* 383 */     if (builtBuffer != null) {
/* 384 */       class_286.method_43433(builtBuffer);
/*     */     }
/*     */     
/* 387 */     RenderSystem.setShaderTexture(0, 0);
/* 388 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 389 */     RenderSystem.enableCull();
/* 390 */     RenderSystem.disableBlend();
/* 391 */     return true;
/*     */   }
/*     */   private static final class AtlasDefinition {
/*     */     private UiMsdfIconAtlas.AtlasMetadata atlas; private List<UiMsdfIconAtlas.IconDefinition> icons; }
/*     */   
/*     */   private static final class AtlasMetadata {
/*     */     private float distanceRange;
/*     */     private float width;
/*     */     private float height; }
/*     */   
/*     */   private static final class IconDefinition {
/*     */     private String id;
/*     */     private float contentAspectRatio;
/*     */     private UiMsdfIconAtlas.AtlasBounds atlasBounds; }
/*     */   
/*     */   private static final class AtlasBounds {
/*     */     private float left;
/*     */     private float top;
/*     */     private float right;
/*     */     private float bottom; }
/*     */   
/*     */   private static final class AtlasIcon extends Record { private final float minU;
/*     */     private final float minV;
/*     */     private final float maxU;
/*     */     private final float maxV;
/*     */     private final float aspectRatio;
/*     */     
/* 418 */     private AtlasIcon(float minU, float minV, float maxU, float maxV, float aspectRatio) { this.minU = minU; this.minV = minV; this.maxU = maxU; this.maxV = maxV; this.aspectRatio = aspectRatio; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$AtlasIcon;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #418	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 418 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$AtlasIcon; } public float minU() { return this.minU; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$AtlasIcon;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #418	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$AtlasIcon; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$AtlasIcon;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #418	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$AtlasIcon;
/* 418 */       //   0	8	1	o	Ljava/lang/Object; } public float minV() { return this.minV; } public float maxU() { return this.maxU; } public float maxV() { return this.maxV; } public float aspectRatio() { return this.aspectRatio; }
/*     */      }
/*     */   private static final class FittedRect extends Record { private final float left; private final float top; private final float right; private final float bottom;
/* 421 */     private FittedRect(float left, float top, float right, float bottom) { this.left = left; this.top = top; this.right = right; this.bottom = bottom; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$FittedRect;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #421	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$FittedRect; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$FittedRect;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #421	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$FittedRect; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$FittedRect;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #421	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/UiMsdfIconAtlas$FittedRect;
/* 421 */       //   0	8	1	o	Ljava/lang/Object; } public float left() { return this.left; } public float top() { return this.top; } public float right() { return this.right; } public float bottom() { return this.bottom; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\UiMsdfIconAtlas.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */