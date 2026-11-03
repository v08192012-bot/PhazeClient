/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_266;
/*     */ import net.minecraft.class_269;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_355;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5348;
/*     */ import net.minecraft.class_5481;
/*     */ import net.minecraft.class_640;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*     */ import vorga.phazeclient.api.system.hud.ExordiumAnimationBridge;
/*     */ import vorga.phazeclient.base.util.PhazeBadgeUtil;
/*     */ import vorga.phazeclient.implement.features.modules.hud.TabHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ import vorga.phazeclient.implement.features.modules.other.NickHider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_355.class})
/*     */ public class PlayerListHudMixin
/*     */ {
/*     */   @Unique
/*     */   private boolean phaze$tabTransformPushed = false;
/*     */   
/*     */   @ModifyVariable(method = {"render"}, at = @At("STORE"), ordinal = 0)
/*     */   private List<class_640> phaze$moveSelfToTop(List<class_640> original) {
/*  49 */     TabHud tabHud = TabHud.getInstance();
/*  50 */     if (!tabHud.isEnabled() || !tabHud.showSelfOnTop.isValue()) {
/*  51 */       return original;
/*     */     }
/*     */     
/*  54 */     class_310 client = class_310.method_1551();
/*  55 */     if (client == null || client.field_1724 == null || original == null || original.size() < 2) {
/*  56 */       return original;
/*     */     }
/*     */     
/*  59 */     UUID selfUuid = client.field_1724.method_5667();
/*  60 */     int selfIndex = -1;
/*  61 */     for (int i = 0; i < original.size(); i++) {
/*  62 */       if (((class_640)original.get(i)).method_2966().getId().equals(selfUuid)) {
/*  63 */         selfIndex = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  68 */     if (selfIndex <= 0) {
/*  69 */       return original;
/*     */     }
/*     */     
/*  72 */     List<class_640> reordered = new ArrayList<>(original);
/*  73 */     class_640 self = reordered.remove(selfIndex);
/*  74 */     reordered.add(0, self);
/*  75 */     return reordered;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"renderLatencyIcon"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$renderPingAsNumber(class_332 context, int width, int x, int y, class_640 entry, CallbackInfo ci) {
/*  81 */     TabHud tabHud = TabHud.getInstance();
/*  82 */     if (!tabHud.isEnabled() || !tabHud.displayPingAsNumber.isValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     class_310 client = class_310.method_1551();
/*  87 */     if (client == null || client.field_1772 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     int ping = Math.max(0, entry.method_2959());
/*  92 */     int color = -1;
/*  93 */     if (tabHud.dynamicPingColor.isValue()) {
/*  94 */       if (ping < 70) { color = -11141291; }
/*  95 */       else if (ping < 150) { color = -171; }
/*  96 */       else if (ping < 300) { color = -22016; }
/*  97 */       else { color = -43691; }
/*     */     
/*     */     }
/* 100 */     String text = String.valueOf(ping);
/* 101 */     int textWidth = client.field_1772.method_1727(text);
/* 102 */     int textX = x + width - textWidth - 2;
/* 103 */     ExordiumAnimationBridge.recordTabElement(context, textX - 1.0F, y - 1.0F, (textX + textWidth) + 1.0F, y + 10.0F);
/*     */ 
/*     */     
/* 106 */     context.method_51433(client.field_1772, text, textX, y, phaze$applyTabAlpha(color), tabHud.pingNumberShadow.isValue());
/* 107 */     ci.cancel();
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
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void phaze$resetTabAnimationState(class_332 context, int scaledWindowWidth, class_269 scoreboard, class_266 objective, CallbackInfo ci) {
/* 128 */     this.phaze$tabTransformPushed = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0)}, locals = LocalCapture.CAPTURE_FAILHARD)
/*     */   private void phaze$pushTabSlide(class_332 context, int scaledWindowWidth, class_269 scoreboard, class_266 objective, CallbackInfo ci, List<class_640> entries, List<?> scoreEntries, int emptyWidth, int maxNameWidth, int maxScoreWidth, int totalPlayers, int rowsPerColumn, int columns, boolean showSkins, int scoreWidth, int columnWidth, int left, int top, int totalWidth, List<?> headerLines) {
/* 159 */     Animations module = Animations.getInstance();
/* 160 */     if (module == null || !module.isTabSlideEnabled() || this.phaze$tabTransformPushed) {
/*     */       return;
/*     */     }
/*     */     
/* 164 */     if (ExordiumAnimationBridge.isCapturingPlayerList()) {
/* 165 */       float f1 = scaledWindowWidth * 0.5F;
/*     */ 
/*     */       
/* 168 */       float f2 = module.isTabSlideScaleStyle() ? top : (top + rowsPerColumn * 9.0F * 0.5F);
/* 169 */       ExordiumAnimationBridge.recordTabGeometry(f1, f2);
/*     */       
/*     */       return;
/*     */     } 
/* 173 */     context.method_51452();
/* 174 */     context.method_51448().method_22903();
/* 175 */     this.phaze$tabTransformPushed = true;
/*     */     
/* 177 */     if (module.isTabSlideStyle()) {
/* 178 */       float offsetY = module.currentTabSlideOffset();
/* 179 */       if (offsetY != 0.0F) {
/* 180 */         context.method_51448().method_46416(0.0F, offsetY, 0.0F);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 185 */     float progress = module.currentTabProgress();
/* 186 */     float scale = Math.max(0.01F, progress);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     float pivotX = scaledWindowWidth * 0.5F;
/*     */ 
/*     */     
/* 194 */     float pivotY = module.isTabSlideScaleStyle() ? top : (top + rowsPerColumn * 9.0F * 0.5F);
/* 195 */     context.method_51448().method_46416(pivotX, pivotY, 0.0F);
/* 196 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 197 */     context.method_51448().method_46416(-pivotX, -pivotY, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("RETURN")})
/*     */   private void phaze$popTabSlide(class_332 context, int scaledWindowWidth, class_269 scoreboard, class_266 objective, CallbackInfo ci) {
/* 205 */     Animations module = Animations.getInstance();
/* 206 */     if (module == null || !module.isTabSlideEnabled() || !this.phaze$tabTransformPushed) {
/*     */       return;
/*     */     }
/* 209 */     context.method_51452();
/* 210 */     context.method_51448().method_22909();
/* 211 */     this.phaze$tabTransformPushed = false;
/*     */   }
/*     */   
/*     */   @Inject(method = {"getPlayerName"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private void phaze$styleOwnName(class_640 entry, CallbackInfoReturnable<class_2561> cir) {
/* 216 */     TabHud tabHud = TabHud.getInstance();
/* 217 */     if (!tabHud.isEnabled() || !tabHud.highlightOwn.isValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 221 */     class_310 client = class_310.method_1551();
/* 222 */     if (client == null || client.field_1724 == null) {
/*     */       return;
/*     */     }
/* 225 */     if (!entry.method_2966().getId().equals(client.field_1724.method_5667())) {
/*     */       return;
/*     */     }
/*     */     
/* 229 */     class_2561 original = (class_2561)cir.getReturnValue();
/* 230 */     if (original == null) {
/*     */       return;
/*     */     }
/* 233 */     class_5250 styled = original.method_27661().method_27695(new class_124[] { class_124.field_1075, class_124.field_1067 });
/* 234 */     cir.setReturnValue(styled);
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
/*     */   @Inject(method = {"getPlayerName"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private void phaze$nickHideTabName(class_640 entry, CallbackInfoReturnable<class_2561> cir) {
/* 253 */     NickHider hider = NickHider.getInstance();
/* 254 */     if (hider == null || !hider.isEnabled()) {
/*     */       return;
/*     */     }
/* 257 */     class_2561 current = (class_2561)cir.getReturnValue();
/* 258 */     class_2561 rewritten = hider.rewrite(current);
/* 259 */     if (rewritten != current) {
/* 260 */       cir.setReturnValue(rewritten);
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"getPlayerName"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private void phaze$badgeTabName(class_640 entry, CallbackInfoReturnable<class_2561> cir) {
/* 266 */     if (entry == null || !PhazeBadgeUtil.isPhazeUser(entry.method_2966().getName())) {
/*     */       return;
/*     */     }
/*     */     
/* 270 */     class_2561 current = (class_2561)cir.getReturnValue();
/* 271 */     if (current != null) {
/* 272 */       int paddingSpaces = phaze$needsExtraNickHiderPadding(entry) ? 3 : 2;
/* 273 */       cir.setReturnValue(PhazeBadgeUtil.withBadgePadding(current, paddingSpaces));
/*     */     } 
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
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V")}, require = 0)
/*     */   private void phaze$fadeTabFill(class_332 context, int x1, int y1, int x2, int y2, int color, Operation<Void> operation) {
/* 294 */     if (ExordiumAnimationBridge.isCapturingPlayerList()) {
/* 295 */       ExordiumAnimationBridge.recordTabElement(context, x1, y1, x2, y2);
/* 296 */       operation.call(new Object[] { context, Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(color) });
/*     */       return;
/*     */     } 
/* 299 */     operation.call(new Object[] { context, Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(phaze$applyTabFillAlpha(color)) });
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
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/PlayerSkinDrawer;draw(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;IIIZZI)V")}, require = 0)
/*     */   private void phaze$fadeTabPlayerHead(class_332 context, class_2960 texture, int x, int y, int size, boolean drawHat, boolean upsideDown, int color, Operation<Void> operation) {
/* 321 */     if (ExordiumAnimationBridge.isCapturingPlayerList()) {
/* 322 */       ExordiumAnimationBridge.recordTabElement(context, x, y, (x + size), (y + size));
/*     */     }
/* 324 */     phaze$withTabTextureAlpha(context, () -> operation.call(new Object[] { context, texture, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(size), Boolean.valueOf(drawHat), Boolean.valueOf(upsideDown), Integer.valueOf(phaze$applyTabAlpha(color)) }));
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
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I")}, require = 0)
/*     */   private int phaze$drawTabBadge(class_332 context, class_327 renderer, class_2561 text, int x, int y, int color, Operation<Integer> operation, @Local class_640 entry) {
/* 347 */     ExordiumAnimationBridge.recordTabElement(context, x - 3.0F, y - 3.0F, (x + renderer
/* 348 */         .method_27525((class_5348)text)) + 1.0F, y + 10.0F);
/*     */     
/* 350 */     int fadedColor = phaze$applyTabAlpha(color);
/* 351 */     if (entry != null && PhazeBadgeUtil.isPhazeUser(entry.method_2966().getName())) {
/* 352 */       context.method_51452();
/* 353 */       boolean codeBadge = PhazeBadgeUtil.isCodeBadgeUser(entry.method_2966().getName());
/* 354 */       float regularSize = PhazeBadgeUtil.guiBadgeSize(renderer);
/*     */ 
/*     */       
/* 357 */       float size = codeBadge ? (regularSize / 1.3F) : regularSize;
/* 358 */       float inset = (regularSize - size) / 2.0F;
/* 359 */       PhazeBadgeUtil.drawGuiBadge(context, x - 2.5F + inset, y - 2.5F + inset, size, 
/*     */           
/* 361 */           PhazeBadgeUtil.alphaWhite(fadedColor), codeBadge);
/*     */     } 
/*     */     
/* 364 */     return ((Integer)operation.call(new Object[] { context, renderer, text, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(fadedColor) })).intValue();
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
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I")}, require = 0)
/*     */   private int phaze$fadeTabOrderedText(class_332 context, class_327 renderer, class_5481 text, int x, int y, int color, Operation<Integer> operation) {
/* 384 */     ExordiumAnimationBridge.recordTabElement(context, x - 1.0F, y - 1.0F, (x + renderer
/* 385 */         .method_30880(text)) + 1.0F, y + 10.0F);
/*     */     
/* 387 */     return ((Integer)operation.call(new Object[] { context, renderer, text, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(phaze$applyTabAlpha(color)) })).intValue();
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
/*     */   @WrapOperation(method = {"renderScoreboardObjective", "renderLatencyIcon"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V")}, require = 0)
/*     */   private void phaze$fadeTabGuiTexture(class_332 context, Function<?, ?> layerFactory, class_2960 texture, int x, int y, int width, int height, Operation<Void> operation) {
/* 408 */     ExordiumAnimationBridge.recordTabElement(context, x, y, (x + width), (y + height));
/* 409 */     phaze$withTabTextureAlpha(context, () -> operation.call(new Object[] { context, layerFactory, texture, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(width), Integer.valueOf(height) }));
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
/*     */   @WrapOperation(method = {"renderScoreboardObjective"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I")}, require = 0)
/*     */   private int phaze$fadeTabScoreText(class_332 context, class_327 renderer, class_2561 text, int x, int y, int color, Operation<Integer> operation) {
/* 431 */     ExordiumAnimationBridge.recordTabElement(context, x - 1.0F, y - 1.0F, (x + renderer
/* 432 */         .method_27525((class_5348)text)) + 1.0F, y + 10.0F);
/*     */     
/* 434 */     return ((Integer)operation.call(new Object[] { context, renderer, text, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(phaze$applyTabAlpha(color)) })).intValue();
/*     */   }
/*     */   
/*     */   private static boolean phaze$needsExtraNickHiderPadding(class_640 entry) {
/* 438 */     NickHider hider = NickHider.getInstance();
/* 439 */     if (hider == null || !hider.isEnabled()) {
/* 440 */       return false;
/*     */     }
/*     */     
/* 443 */     class_310 client = class_310.method_1551();
/* 444 */     return (client != null && client.field_1724 != null && entry
/*     */       
/* 446 */       .method_2966().getId().equals(client.field_1724.method_5667()));
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static int phaze$applyTabAlpha(int color) {
/* 451 */     if (ExordiumAnimationBridge.isCapturingPlayerList()) {
/* 452 */       return color;
/*     */     }
/* 454 */     Animations module = Animations.getInstance();
/* 455 */     if (module == null || !module.isTabSlideEnabled()) {
/* 456 */       return color;
/*     */     }
/* 458 */     float alphaMultiplier = module.currentTabAlpha();
/* 459 */     if (alphaMultiplier >= 0.999F) {
/* 460 */       return color;
/*     */     }
/*     */     
/* 463 */     int baseAlpha = color >>> 24;
/* 464 */     if (baseAlpha == 0 && (color & 0xFFFFFF) != 0) {
/* 465 */       baseAlpha = 255;
/*     */     }
/* 467 */     int scaledAlpha = Math.max(0, Math.min(255, Math.round(baseAlpha * alphaMultiplier)));
/* 468 */     return color & 0xFFFFFF | scaledAlpha << 24;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private static int phaze$applyTabFillAlpha(int color) {
/* 477 */     return color;
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static void phaze$withTabTextureAlpha(class_332 context, Runnable draw) {
/* 482 */     if (ExordiumAnimationBridge.isCapturingPlayerList()) {
/* 483 */       draw.run();
/*     */       return;
/*     */     } 
/* 486 */     Animations module = Animations.getInstance();
/* 487 */     if (module == null || !module.isTabSlideEnabled()) {
/* 488 */       draw.run();
/*     */       
/*     */       return;
/*     */     } 
/* 492 */     float alphaMultiplier = module.currentTabAlpha();
/* 493 */     if (alphaMultiplier >= 0.999F) {
/* 494 */       draw.run();
/*     */       
/*     */       return;
/*     */     } 
/* 498 */     context.method_51452();
/* 499 */     RenderSystem.enableBlend();
/* 500 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alphaMultiplier);
/*     */     try {
/* 502 */       draw.run();
/* 503 */       context.method_51452();
/*     */     } finally {
/* 505 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PlayerListHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */