/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_10017;
/*     */ import net.minecraft.class_10142;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4618;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5348;
/*     */ import net.minecraft.class_5481;
/*     */ import net.minecraft.class_7833;
/*     */ import net.minecraft.class_897;
/*     */ import org.joml.Matrix4f;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.api.system.shape.implement.Blur;
/*     */ import vorga.phazeclient.base.util.PhazeBadgeUtil;
/*     */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.hud.NametagHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.TotemTracker;
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
/*     */ @Mixin({class_897.class})
/*     */ public abstract class EntityRendererMixin
/*     */ {
/*     */   private static boolean phaze$backgroundDrawnThisLabel = false;
/*     */   private static boolean phaze$drawBadgeThisLabel = false;
/*     */   private static boolean phaze$drawCodeBadgeThisLabel = false;
/*     */   private static boolean phaze$depthPreparedThisLabel = false;
/*     */   private static boolean phaze$fallbackQueuedThisLabel = false;
/*     */   private static final int NAMETAG_CACHE_MAX = 256;
/*  61 */   private static float phaze$currentLabelDistance = 0.0F;
/*     */   
/*  63 */   private static final Map<String, Integer> TEXT_WIDTH_CACHE = new LinkedHashMap<String, Integer>(256, 0.75F, true)
/*     */     {
/*     */       protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
/*  66 */         return (size() > 256);
/*     */       }
/*     */     };
/*  69 */   private static final Map<Integer, Integer> TEXT_COLOR_CACHE = new LinkedHashMap<Integer, Integer>(256, 0.75F, true)
/*     */     {
/*     */       protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
/*  72 */         return (size() > 256);
/*     */       }
/*     */     };
/*  75 */   private static long lastBackgroundSettingsSignature = Long.MIN_VALUE;
/*  76 */   private static int lastBackgroundInputColor = Integer.MIN_VALUE;
/*  77 */   private static int lastBackgroundResolvedColor = 0;
/*     */   
/*     */   @Inject(method = {"hasLabel(Lnet/minecraft/entity/Entity;D)Z"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$allowOwnNametagInFirstPerson(class_1297 entity, double squaredDistanceToCamera, CallbackInfoReturnable<Boolean> cir) {
/*  81 */     NametagHud module = NametagHud.getInstance();
/*  82 */     if (!module.isEnabled())
/*     */       return; 
/*  84 */     class_310 client = class_310.method_1551();
/*  85 */     if (client == null || client.field_1690 == null || client.field_1724 == null)
/*     */       return; 
/*  87 */     if (module.hideInF1.isValue() && client.field_1690.field_1842) {
/*  88 */       cir.setReturnValue(Boolean.valueOf(false));
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     boolean isSelf = (entity == client.method_1560() || entity == client.field_1724);
/*  93 */     if (!isSelf) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     boolean firstPerson = client.field_1690.method_31044().method_31034();
/* 101 */     cir.setReturnValue(Boolean.valueOf((!firstPerson && module.thirdPersonNametag.isValue())));
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
/*     */   @Inject(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$controlNametagVisibility(class_10017 state, class_2561 text, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 114 */     Float previewBodyYaw = CosmeticsRenderer.previewBodyYaw();
/* 115 */     if (previewBodyYaw != null) {
/* 116 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(-previewBodyYaw.floatValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     phaze$backgroundDrawnThisLabel = false;
/* 124 */     phaze$drawBadgeThisLabel = false;
/* 125 */     phaze$drawCodeBadgeThisLabel = false;
/* 126 */     phaze$depthPreparedThisLabel = false;
/* 127 */     phaze$fallbackQueuedThisLabel = false;
/*     */     
/* 129 */     NametagHud module = NametagHud.getInstance();
/* 130 */     if (!module.isEnabled())
/*     */       return; 
/* 132 */     phaze$currentLabelDistance = (float)Math.sqrt(Math.max(0.0D, state.field_53332));
/* 133 */     class_310 client = class_310.method_1551();
/* 134 */     if (client == null || client.field_1690 == null)
/*     */       return; 
/* 136 */     if (module.hideInF1.isValue() && client.field_1690.field_1842) {
/* 137 */       ci.cancel();
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     if (state.field_53332 > 4096.0D) {
/* 142 */       ci.cancel();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Redirect(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"))
/*     */   private int phaze$drawNametagWithSettings(class_327 textRenderer, class_2561 text, float x, float y, int color, boolean shadow, Matrix4f matrix, class_4597 vertexConsumers, class_327.class_6415 layerType, int backgroundColor, int light) {
/* 152 */     NametagHud module = NametagHud.getInstance();
/* 153 */     int resolvedBackground = phaze$drawBlurBackgroundIfNeeded(
/* 154 */         phaze$getCachedTextWidth(textRenderer, text), matrix, x, y, vertexConsumers, layerType, backgroundColor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     int result = textRenderer.method_27522(text, x, y, 
/*     */ 
/*     */ 
/*     */         
/* 166 */         phaze$resolvedTextColor(color), 
/* 167 */         module.isEnabled() ? module.nametagTextShadow.isValue() : shadow, matrix, vertexConsumers, layerType, resolvedBackground, light);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     phaze$drawNametagBadgeIfNeeded(matrix, vertexConsumers, x, y, layerType, light, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 181 */         PhazeBadgeUtil.alphaWhite(color));
/*     */     
/* 183 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Redirect(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"))
/*     */   private int phaze$drawOrderedNametagWithSettings(class_327 textRenderer, class_5481 text, float x, float y, int color, boolean shadow, Matrix4f matrix, class_4597 vertexConsumers, class_327.class_6415 layerType, int backgroundColor, int light) {
/* 191 */     NametagHud module = NametagHud.getInstance();
/* 192 */     int resolvedBackground = phaze$drawBlurBackgroundIfNeeded(
/* 193 */         phaze$getCachedTextWidth(textRenderer, text), matrix, x, y, vertexConsumers, layerType, backgroundColor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     int result = textRenderer.method_22942(text, x, y, 
/*     */ 
/*     */ 
/*     */         
/* 205 */         phaze$resolvedTextColor(color), 
/* 206 */         module.isEnabled() ? module.nametagTextShadow.isValue() : shadow, matrix, vertexConsumers, layerType, resolvedBackground, light);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     phaze$drawNametagBadgeIfNeeded(matrix, vertexConsumers, x, y, layerType, light, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 220 */         PhazeBadgeUtil.alphaWhite(color));
/*     */     
/* 222 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
/*     */   private class_2561 phaze$modifyNametagText(class_2561 original) {
/* 232 */     NametagHud module = NametagHud.getInstance();
/* 233 */     if (!module.isEnabled() || original == null) return original;
/*     */     
/* 235 */     class_310 client = class_310.method_1551();
/* 236 */     if (client == null || client.field_1724 == null) return original;
/*     */     
/* 238 */     String selfName = client.field_1724.method_5477().getString();
/* 239 */     int textColor = 16777215;
/* 240 */     if (module.replaceOwnNameColor.isValue() && selfName.equals(original.getString())) {
/* 241 */       textColor = 5636095;
/*     */     }
/*     */     
/* 244 */     class_5250 styled = original.method_27661();
/* 245 */     styled.method_10862(styled.method_10866().method_36139(textColor));
/* 246 */     return (class_2561)styled;
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
/*     */   @ModifyVariable(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
/*     */   private class_2561 phaze$appendTotemSuffix(class_2561 original) {
/* 264 */     TotemTracker tracker = TotemTracker.getInstance();
/* 265 */     if (tracker == null || !tracker.isEnabled() || !tracker.nametagSuffix.isValue()) {
/* 266 */       return original;
/*     */     }
/* 268 */     if (original == null) {
/* 269 */       return original;
/*     */     }
/* 271 */     String displayed = original.getString();
/* 272 */     int count = tracker.getLossCountFromText(displayed);
/* 273 */     if (count <= 0) {
/* 274 */       return original;
/*     */     }
/* 276 */     class_5250 decorated = original.method_27661();
/* 277 */     decorated.method_10852((class_2561)class_2561.method_43470(" | ").method_27692(class_124.field_1080));
/* 278 */     decorated.method_10852((class_2561)class_2561.method_43470("-" + count).method_27692(class_124.field_1061));
/* 279 */     return (class_2561)decorated;
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
/*     */   @ModifyVariable(method = {"renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
/*     */   private class_2561 phaze$prependBadgePadding(class_2561 original) {
/* 292 */     if (original == null || !(this instanceof net.minecraft.class_1007)) {
/* 293 */       return original;
/*     */     }
/* 295 */     String identity = PhazeBadgeUtil.extractNametagIdentity(original.getString());
/* 296 */     if (identity == null || !PhazeBadgeUtil.isPhazeUser(identity)) {
/* 297 */       return original;
/*     */     }
/* 299 */     phaze$drawBadgeThisLabel = true;
/* 300 */     phaze$drawCodeBadgeThisLabel = PhazeBadgeUtil.isCodeBadgeUser(identity);
/* 301 */     return PhazeBadgeUtil.withBadgePadding(original);
/*     */   }
/*     */   
/*     */   private static int phaze$resolvedTextColor(int originalColor) {
/* 305 */     Integer cached = TEXT_COLOR_CACHE.get(Integer.valueOf(originalColor));
/* 306 */     if (cached != null) {
/* 307 */       return cached.intValue();
/*     */     }
/* 309 */     int resolved = originalColor;
/* 310 */     TEXT_COLOR_CACHE.put(Integer.valueOf(originalColor), Integer.valueOf(resolved));
/* 311 */     return resolved;
/*     */   }
/*     */   
/*     */   private static int phaze$resolvedBackgroundColor(int vanillaBackgroundColor) {
/* 315 */     NametagHud module = NametagHud.getInstance();
/* 316 */     if (!module.isEnabled()) {
/* 317 */       return vanillaBackgroundColor;
/*     */     }
/* 319 */     if (!module.background.isValue()) {
/* 320 */       return 0;
/*     */     }
/*     */     
/* 323 */     long settingsSignature = phaze$backgroundSettingsSignature(module);
/* 324 */     if (settingsSignature == lastBackgroundSettingsSignature && vanillaBackgroundColor == lastBackgroundInputColor) {
/* 325 */       return lastBackgroundResolvedColor;
/*     */     }
/*     */     
/* 328 */     class_310 client = class_310.method_1551();
/* 329 */     int out = (client != null) ? module.getResolvedBackgroundColor(client) : vanillaBackgroundColor;
/*     */     
/* 331 */     lastBackgroundSettingsSignature = settingsSignature;
/* 332 */     lastBackgroundInputColor = vanillaBackgroundColor;
/* 333 */     lastBackgroundResolvedColor = out;
/* 334 */     return out;
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
/*     */   private static void phaze$drawNametagBadgeIfNeeded(Matrix4f matrix, class_4597 vertexConsumers, float x, float y, class_327.class_6415 layerType, int light, int color) {
/* 346 */     if (!phaze$drawBadgeThisLabel) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 352 */     float badgeSize = phaze$drawCodeBadgeThisLabel ? 8.0F : 10.0F;
/* 353 */     float inset = (10.0F - badgeSize) / 2.0F;
/* 354 */     PhazeBadgeUtil.drawWorldBadge(matrix, vertexConsumers, layerType, x - 2.0F + inset, y - 1.0F + inset, badgeSize, light, color, phaze$drawCodeBadgeThisLabel);
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
/*     */   private static int phaze$drawBlurBackgroundIfNeeded(float textWidth, Matrix4f matrix, float x, float y, class_4597 vertexConsumers, class_327.class_6415 layerType, int vanillaBackgroundColor) {
/* 370 */     NametagHud module = NametagHud.getInstance();
/* 371 */     if (!module.isEnabled() || !module.background.isValue()) {
/* 372 */       return phaze$resolvedBackgroundColor(vanillaBackgroundColor);
/*     */     }
/* 374 */     float blurRadius = module.backgroundBlurRadius.getValue();
/* 375 */     int background = phaze$resolvedBackgroundColor(vanillaBackgroundColor);
/*     */ 
/*     */ 
/*     */     
/* 379 */     if (layerType != class_327.class_6415.field_33993) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 384 */       if (phaze$shouldDrawDepthAwareBlur(textWidth, blurRadius))
/*     */       {
/*     */ 
/*     */         
/* 388 */         phaze$prepareDepthAwareBlur(vertexConsumers);
/*     */       }
/* 390 */       if (blurRadius > 0.0F) {
/* 391 */         phaze$fallbackQueuedThisLabel = true;
/* 392 */         return background;
/*     */       } 
/* 394 */       return 0;
/*     */     } 
/*     */     
/* 397 */     if (blurRadius <= 0.0F || phaze$backgroundDrawnThisLabel) {
/* 398 */       return background;
/*     */     }
/*     */     
/* 401 */     float left = x - 1.0F;
/* 402 */     float top = y - 1.0F;
/* 403 */     float rightExtend = -1.0F;
/* 404 */     float width = textWidth + 2.0F + rightExtend;
/* 405 */     float height = 10.0F;
/*     */ 
/*     */ 
/*     */     
/* 409 */     left = phaze$snapHalfPixel(left);
/* 410 */     top = phaze$snapHalfPixel(top);
/* 411 */     width = phaze$snapHalfPixel(Math.max(0.0F, width));
/* 412 */     height = phaze$snapHalfPixel(Math.max(0.0F, height));
/*     */     
/* 414 */     if (width * height < 50.0F) {
/* 415 */       if (!phaze$fallbackQueuedThisLabel) {
/* 416 */         drawSolidRect3D(matrix, left, top, width, height, background);
/*     */       }
/* 418 */       phaze$backgroundDrawnThisLabel = true;
/* 419 */       return 0;
/*     */     } 
/*     */     
/* 422 */     float quality = class_3532.method_15363(0.35F + blurRadius * 0.1F, 0.35F, 4.2F);
/* 423 */     class_310 client = class_310.method_1551();
/* 424 */     float distance = phaze$currentLabelDistance;
/* 425 */     float distanceFactor = phaze$blurDistanceFactor(distance);
/* 426 */     float playerSpeed = Blur.INSTANCE.getPlayerSpeed(client);
/*     */     
/* 428 */     if (distanceFactor <= 0.001F) {
/* 429 */       if (!phaze$fallbackQueuedThisLabel) {
/* 430 */         drawSolidRect3D(matrix, left, top, width, height, background);
/*     */       }
/* 432 */       phaze$backgroundDrawnThisLabel = true;
/* 433 */       return 0;
/*     */     } 
/* 435 */     quality *= distanceFactor;
/*     */     
/* 437 */     if (playerSpeed > 20.0F) {
/* 438 */       float speedFactor = class_3532.method_15363(20.0F / playerSpeed, 0.3F, 1.0F);
/* 439 */       quality *= speedFactor;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (phaze$fallbackQueuedThisLabel)
/*     */     {
/*     */ 
/*     */       
/* 449 */       phaze$flushCurrentNametagLayer(vertexConsumers);
/*     */     }
/* 451 */     phaze$prepareDepthAwareBlur(vertexConsumers);
/* 452 */     Blur.INSTANCE.renderWorldRect(matrix, left, top, width, height, quality, background, !phaze$fallbackQueuedThisLabel, distanceFactor);
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
/* 463 */     phaze$backgroundDrawnThisLabel = true;
/* 464 */     return 0;
/*     */   }
/*     */   
/*     */   private static boolean phaze$shouldDrawDepthAwareBlur(float textWidth, float blurRadius) {
/* 468 */     float width = phaze$snapHalfPixel(Math.max(0.0F, textWidth + 1.0F));
/* 469 */     return (blurRadius > 0.0F && width * 10.0F >= 50.0F && 
/*     */       
/* 471 */       phaze$blurDistanceFactor(phaze$currentLabelDistance) > 0.001F);
/*     */   }
/*     */   
/*     */   private static float phaze$blurDistanceFactor(float distance) {
/* 475 */     if (distance <= 24.0F) {
/* 476 */       return 1.0F;
/*     */     }
/* 478 */     if (distance >= 30.0F) {
/* 479 */       return 0.0F;
/*     */     }
/* 481 */     float t = class_3532.method_15363((distance - 24.0F) / 6.0F, 0.0F, 1.0F);
/* 482 */     float smoothstep = t * t * (3.0F - 2.0F * t);
/* 483 */     return 1.0F - smoothstep;
/*     */   }
/*     */   
/*     */   private static void phaze$prepareDepthAwareBlur(class_4597 vertexConsumers) {
/* 487 */     if (phaze$depthPreparedThisLabel) {
/*     */       return;
/*     */     }
/*     */     
/* 491 */     phaze$flushCurrentNametagLayer(vertexConsumers);
/* 492 */     Blur.INSTANCE.prepareWorldRectInput();
/* 493 */     phaze$depthPreparedThisLabel = true;
/*     */   }
/*     */   
/*     */   private static void phaze$flushCurrentNametagLayer(class_4597 vertexConsumers) {
/* 497 */     class_4597.class_4598 immediate = null;
/* 498 */     if (vertexConsumers instanceof class_4597.class_4598) { class_4597.class_4598 direct = (class_4597.class_4598)vertexConsumers;
/* 499 */       immediate = direct; }
/* 500 */     else if (vertexConsumers instanceof class_4618) { class_4618 outline = (class_4618)vertexConsumers;
/* 501 */       immediate = ((OutlineVertexConsumerProviderAccessor)outline).phaze$getParent(); }
/*     */     
/* 503 */     if (immediate != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 508 */       immediate.method_37104();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int phaze$getCachedTextWidth(class_327 textRenderer, class_2561 text) {
/* 513 */     String key = text.getString();
/* 514 */     Integer cached = TEXT_WIDTH_CACHE.get(key);
/* 515 */     if (cached != null) {
/* 516 */       return cached.intValue();
/*     */     }
/* 518 */     int width = textRenderer.method_27525((class_5348)text);
/* 519 */     TEXT_WIDTH_CACHE.put(key, Integer.valueOf(width));
/* 520 */     return width;
/*     */   }
/*     */   
/*     */   private static int phaze$getCachedTextWidth(class_327 textRenderer, class_5481 text) {
/* 524 */     String key = text.toString();
/* 525 */     Integer cached = TEXT_WIDTH_CACHE.get(key);
/* 526 */     if (cached != null) {
/* 527 */       return cached.intValue();
/*     */     }
/* 529 */     int width = textRenderer.method_30880(text);
/* 530 */     TEXT_WIDTH_CACHE.put(key, Integer.valueOf(width));
/* 531 */     return width;
/*     */   }
/*     */   
/*     */   private static long phaze$backgroundSettingsSignature(NametagHud module) {
/* 535 */     long h = -7046029254386353131L;
/* 536 */     h = h * 1099511628211L ^ (module.background.isValue() ? 1L : 0L);
/* 537 */     String preset = module.backgroundPreset.getSelected();
/* 538 */     h = h * 1099511628211L ^ ((preset == null) ? 0L : preset.hashCode());
/* 539 */     h = h * 1099511628211L ^ Math.round(module.colorBrightness.getValue() * 100.0F);
/* 540 */     h = h * 1099511628211L ^ Math.round(module.backgroundOpacity.getValue() * 100.0F);
/* 541 */     return h;
/*     */   }
/*     */   
/*     */   private static void drawSolidRect3D(Matrix4f matrix, float x, float y, float width, float height, int argb) {
/* 545 */     float a = (argb >>> 24 & 0xFF) / 255.0F;
/* 546 */     if (a <= 0.0F || width <= 0.0F || height <= 0.0F)
/* 547 */       return;  float r = (argb >>> 16 & 0xFF) / 255.0F;
/* 548 */     float g = (argb >>> 8 & 0xFF) / 255.0F;
/* 549 */     float b = (argb & 0xFF) / 255.0F;
/*     */     
/* 551 */     RenderSystem.enableBlend();
/* 552 */     RenderSystem.defaultBlendFunc();
/* 553 */     RenderSystem.disableDepthTest();
/* 554 */     RenderSystem.depthMask(false);
/* 555 */     RenderSystem.setShader(class_10142.field_53876);
/*     */     
/* 557 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/* 558 */     buffer.method_22918(matrix, x, y, 0.0F).method_22915(r, g, b, a);
/* 559 */     buffer.method_22918(matrix, x, y + height, 0.0F).method_22915(r, g, b, a);
/* 560 */     buffer.method_22918(matrix, x + width, y + height, 0.0F).method_22915(r, g, b, a);
/* 561 */     buffer.method_22918(matrix, x + width, y, 0.0F).method_22915(r, g, b, a);
/* 562 */     class_286.method_43433(buffer.method_60800());
/* 563 */     RenderSystem.depthMask(true);
/* 564 */     RenderSystem.enableDepthTest();
/* 565 */     RenderSystem.defaultBlendFunc();
/* 566 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private static float phaze$snapHalfPixel(float value) {
/* 570 */     return Math.round(value * 2.0F) * 0.5F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */