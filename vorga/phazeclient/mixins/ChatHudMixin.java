/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_303;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_338;
/*     */ import net.minecraft.class_5481;
/*     */ import net.minecraft.class_7469;
/*     */ import net.minecraft.class_7591;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.api.system.hud.ChatAnimationFrameAccess;
/*     */ import vorga.phazeclient.api.system.hud.ExordiumAnimationBridge;
/*     */ import vorga.phazeclient.base.util.PhazeBadgeUtil;
/*     */ import vorga.phazeclient.base.util.animation.Interpolation;
/*     */ import vorga.phazeclient.helpers.ChatScrollState;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChatHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.MentionHighlight;
/*     */ import vorga.phazeclient.implement.features.modules.other.NickHider;
/*     */ import vorga.phazeclient.implement.features.modules.other.Translator;
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
/*     */ @Mixin({class_338.class})
/*     */ public abstract class ChatHudMixin
/*     */   implements ChatAnimationFrameAccess
/*     */ {
/*     */   @Shadow
/*     */   private int field_2066;
/*     */   @Shadow
/*     */   @Final
/*     */   private List<class_303.class_7590> field_2064;
/*     */   @Unique
/*     */   private static final float UP_DISPLACEMENT_SCALE = 0.2F;
/*     */   @Unique
/*     */   private static final float LEFT_DISPLACEMENT_SCALE = 1.0F;
/*     */   @Unique
/*  71 */   private long phaze$lastMessageNanos = 0L; @Unique
/*  72 */   private int phaze$latestAddedTick = Integer.MIN_VALUE;
/*     */   @Unique
/*  74 */   private float phaze$frameDx = 0.0F; @Unique
/*  75 */   private float phaze$frameDy = 0.0F;
/*     */   @Unique
/*     */   private boolean phaze$frameActive = false;
/*     */   @Unique
/*  79 */   private final Set<Integer> phaze$badgedChatTicks = new LinkedHashSet<>(); @Unique private boolean phaze$pendingBadgeForNextLine = false; @Unique private boolean phaze$pendingCodeBadgeForNextLine = false; @Unique
/*  80 */   private final Map<Integer, Boolean> phaze$codeBadgeChatTicks = new LinkedHashMap<>(); @Unique
/*  81 */   private final Set<Integer> phaze$drawnBadgeTicksThisFrame = new HashSet<>();
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
/*     */   @ModifyVariable(method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at = @At("HEAD"), argsOnly = true)
/*     */   private class_2561 phaze$mentionThenHide(class_2561 original) {
/* 105 */     this.phaze$pendingBadgeForNextLine = false;
/* 106 */     this.phaze$pendingCodeBadgeForNextLine = false;
/* 107 */     if (PhazeBadgeUtil.hasBadgePadding(original)) {
/* 108 */       this.phaze$pendingBadgeForNextLine = true;
/* 109 */       String paddedSender = PhazeBadgeUtil.extractChatSender(original.getString());
/* 110 */       this.phaze$pendingCodeBadgeForNextLine = PhazeBadgeUtil.isCodeBadgeUser(paddedSender);
/* 111 */       return original;
/*     */     } 
/*     */     
/* 114 */     class_2561 afterMention = original;
/* 115 */     MentionHighlight mention = MentionHighlight.getInstance();
/* 116 */     if (mention != null && mention.isEnabled()) {
/* 117 */       afterMention = mention.processIncoming(original);
/*     */     }
/* 119 */     String sender = PhazeBadgeUtil.extractChatSender((afterMention != null) ? afterMention.getString() : null);
/* 120 */     NickHider hider = NickHider.getInstance();
/* 121 */     class_2561 result = (hider == null) ? afterMention : hider.rewrite(afterMention);
/*     */     
/* 123 */     if (sender != null && PhazeBadgeUtil.isPhazeUser(sender)) {
/* 124 */       this.phaze$pendingBadgeForNextLine = true;
/* 125 */       this.phaze$pendingCodeBadgeForNextLine = PhazeBadgeUtil.isCodeBadgeUser(sender);
/* 126 */       return PhazeBadgeUtil.withBadgePadding(result);
/*     */     } 
/* 128 */     return result;
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
/*     */   @Inject(method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$collapseRepeats(class_2561 message, class_7469 signature, class_7591 indicator, CallbackInfo ci) {
/* 141 */     ChatHelper helper = ChatHelper.getInstance();
/* 142 */     if (helper == null || !helper.isEnabled() || helper.isBypassActive()) {
/*     */       return;
/*     */     }
/*     */     
/* 146 */     class_338 hud = (class_338)this;
/* 147 */     class_2561 replacement = helper.tryCollapse(hud, message);
/* 148 */     if (replacement == null) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     ci.cancel();
/* 153 */     helper.runWithBypass(() -> hud.method_1812(replacement));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at = {@At("HEAD")})
/*     */   private void phaze$translateIncoming(class_2561 message, class_7469 signature, class_7591 indicator, CallbackInfo ci) {
/* 165 */     Translator translator = Translator.getInstance();
/* 166 */     if (translator == null || !translator.isEnabled() || translator.isBypassActive()) {
/*     */       return;
/*     */     }
/* 169 */     translator.onIncomingChat(message, signature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getMessageOpacityMultiplier"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private static void phaze$applyFadeIn(int messageAge, CallbackInfoReturnable<Double> cir) {
/* 178 */     Animations module = Animations.getInstance();
/* 179 */     if (module == null || !module.isChatFadeEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 184 */     if (ExordiumAnimationBridge.isCapturingChat()) {
/*     */       return;
/*     */     }
/* 187 */     float fadeIn = module.computeChatFadeInMultiplier(messageAge);
/* 188 */     if (fadeIn >= 1.0F) {
/*     */       return;
/*     */     }
/* 191 */     Double original = (Double)cir.getReturnValue();
/* 192 */     if (original == null) {
/*     */       return;
/*     */     }
/* 195 */     cir.setReturnValue(Double.valueOf(original.doubleValue() * fadeIn));
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
/*     */   @ModifyExpressionValue(method = {"addVisibleMessage", "addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V", "addToMessageHistory"}, at = {@At(value = "CONSTANT", args = {"intValue=100"})})
/*     */   private int phaze$expandHistoryLimit(int original) {
/* 211 */     ChatHelper helper = ChatHelper.getInstance();
/* 212 */     if (helper == null) {
/* 213 */       return original;
/*     */     }
/* 215 */     return helper.getChatHistoryLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V"}, at = {@At("TAIL")})
/*     */   private void phaze$stampMessageArrival(class_303 line, CallbackInfo ci) {
/* 227 */     this.phaze$lastMessageNanos = System.nanoTime();
/* 228 */     if (!this.field_2064.isEmpty()) {
/* 229 */       this.phaze$latestAddedTick = ((class_303.class_7590)this.field_2064.get(0)).comp_895();
/*     */     }
/* 231 */     if (this.phaze$pendingBadgeForNextLine && line != null) {
/* 232 */       phaze$rememberBadgedChatTick(line.comp_892(), this.phaze$pendingCodeBadgeForNextLine);
/*     */     }
/* 234 */     this.phaze$pendingBadgeForNextLine = false;
/* 235 */     this.phaze$pendingCodeBadgeForNextLine = false;
/*     */ 
/*     */     
/* 238 */     ExordiumAnimationBridge.requestImmediateCapture("minecraft:chat_panel");
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void phaze$prepareFrame(class_332 context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
/* 244 */     this.phaze$drawnBadgeTicksThisFrame.clear();
/* 245 */     phaze$tickAnimationFrame();
/*     */   }
/*     */ 
/*     */   
/*     */   public void phaze$tickAnimationFrame() {
/* 250 */     this.phaze$frameActive = false;
/* 251 */     this.phaze$frameDx = 0.0F;
/* 252 */     this.phaze$frameDy = 0.0F;
/*     */     
/* 254 */     Animations module = Animations.getInstance();
/* 255 */     if (module == null || this.phaze$lastMessageNanos == 0L) {
/* 256 */       ExordiumAnimationBridge.updateChatAnimation(0.0F, 0.0F, 1.0F, false);
/*     */       
/*     */       return;
/*     */     } 
/* 260 */     float lifetimeMs = (float)(System.nanoTime() - this.phaze$lastMessageNanos) / 1000000.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     float cachedRowAlpha = 1.0F;
/* 266 */     if (module.isChatFadeEnabled() && lifetimeMs < 200.0F) {
/* 267 */       cachedRowAlpha = Math.max(0.0F, Math.min(1.0F, (lifetimeMs + 50.0F) / 200.0F));
/*     */     }
/*     */     
/* 270 */     if (module.isChatSmoothScrollEnabled() && !ChatScrollState.suppressSlide && this.field_2066 == 0) {
/*     */ 
/*     */       
/* 273 */       boolean left = module.isChatMessageSlideLeft();
/* 274 */       float fadeMs = left ? module.chatLeftSlideFadeMs() : module.chatSlideFadeMs();
/* 275 */       if (fadeMs > 0.0F && lifetimeMs < fadeMs) {
/* 276 */         float alpha = lifetimeMs / fadeMs;
/* 277 */         if (alpha < 0.0F) alpha = 0.0F; 
/* 278 */         if (alpha > 1.0F) alpha = 1.0F;
/*     */         
/* 280 */         if (left) {
/* 281 */           float maxLeft = method_1811() * 1.0F;
/* 282 */           Interpolation interp = module.getChatLeftInterpolation();
/* 283 */           float shaped = (float)interp.interpolate(alpha);
/* 284 */           this.phaze$frameDx = -maxLeft * (1.0F - shaped);
/* 285 */           this.phaze$frameDy = 0.0F;
/* 286 */           this.phaze$frameActive = (Math.abs(this.phaze$frameDx) >= 1.0F);
/*     */         } else {
/* 288 */           float maxUp = method_44752() * 0.2F;
/* 289 */           this.phaze$frameDx = 0.0F;
/* 290 */           this.phaze$frameDy = Math.round(maxUp * (1.0F - alpha));
/* 291 */           this.phaze$frameActive = (this.phaze$frameDy >= 1.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     ExordiumAnimationBridge.updateChatAnimation(this.phaze$frameDx, this.phaze$frameDy, cachedRowAlpha, this.phaze$frameActive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$shouldShift(class_303.class_7590 visible) {
/* 306 */     return (this.phaze$frameActive && visible != null && visible
/*     */       
/* 308 */       .comp_895() == this.phaze$latestAddedTick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V")}, require = 0)
/*     */   private void phaze$shiftFill(class_332 ctx, int x1, int y1, int x2, int y2, int color, Operation<Void> op, @Local class_303.class_7590 visible) {
/* 320 */     if (ExordiumAnimationBridge.isCapturingChat()) {
/* 321 */       ExordiumAnimationBridge.recordChatElement(ctx, x1, y1, x2, y2, (visible != null && visible
/*     */           
/* 323 */           .comp_895() == this.phaze$latestAddedTick));
/*     */       
/* 325 */       op.call(new Object[] { ctx, Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(color) });
/*     */       return;
/*     */     } 
/* 328 */     if (phaze$shouldShift(visible)) {
/* 329 */       int dx = Math.round(this.phaze$frameDx);
/* 330 */       int dy = Math.round(this.phaze$frameDy);
/* 331 */       op.call(new Object[] { ctx, Integer.valueOf(x1 + dx), Integer.valueOf(y1 + dy), Integer.valueOf(x2 + dx), Integer.valueOf(y2 + dy), Integer.valueOf(color) });
/*     */     } else {
/* 333 */       op.call(new Object[] { ctx, Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(x2), Integer.valueOf(y2), Integer.valueOf(color) });
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
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I")}, require = 0)
/*     */   private int phaze$shiftText(class_332 ctx, class_327 renderer, class_5481 text, int x, int y, int color, Operation<Integer> op, @Local class_303.class_7590 visible) {
/* 347 */     if (ExordiumAnimationBridge.isCapturingChat()) {
/* 348 */       ExordiumAnimationBridge.recordChatElement(ctx, x - 1.0F, y - 1.0F, (x + renderer
/*     */ 
/*     */ 
/*     */           
/* 352 */           .method_30880(text)) + 2.0F, y + 10.0F, (visible != null && visible
/*     */           
/* 354 */           .comp_895() == this.phaze$latestAddedTick));
/*     */       
/* 356 */       if (phaze$shouldDrawChatBadge(visible)) {
/* 357 */         PhazeBadgeUtil.drawChatBadgeAsText(ctx, renderer, x - 1.0F, y - 1.0F, 
/* 358 */             PhazeBadgeUtil.alphaWhite(color), 
/* 359 */             phaze$isCodeBadge(visible));
/*     */       }
/*     */       
/* 362 */       return ((Integer)op.call(new Object[] { ctx, renderer, text, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(color) })).intValue();
/*     */     } 
/*     */     
/* 365 */     int drawX = x;
/* 366 */     int drawY = y;
/* 367 */     if (phaze$shouldShift(visible)) {
/* 368 */       drawX += Math.round(this.phaze$frameDx);
/* 369 */       drawY += Math.round(this.phaze$frameDy);
/*     */     } 
/*     */     
/* 372 */     if (phaze$shouldDrawChatBadge(visible)) {
/* 373 */       PhazeBadgeUtil.drawChatBadgeAsText(ctx, renderer, drawX - 1.0F, drawY - 1.0F, 
/* 374 */           PhazeBadgeUtil.alphaWhite(color), 
/* 375 */           phaze$isCodeBadge(visible));
/*     */     }
/*     */ 
/*     */     
/* 379 */     return ((Integer)op.call(new Object[] { ctx, renderer, text, Integer.valueOf(drawX), Integer.valueOf(drawY), Integer.valueOf(color) })).intValue();
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private void phaze$rememberBadgedChatTick(int tick, boolean codeBadge) {
/* 384 */     this.phaze$badgedChatTicks.add(Integer.valueOf(tick));
/* 385 */     this.phaze$codeBadgeChatTicks.put(Integer.valueOf(tick), Boolean.valueOf(codeBadge));
/* 386 */     while (this.phaze$badgedChatTicks.size() > 512) {
/* 387 */       Integer oldest = this.phaze$badgedChatTicks.iterator().next();
/* 388 */       this.phaze$badgedChatTicks.remove(oldest);
/* 389 */       this.phaze$codeBadgeChatTicks.remove(oldest);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$isCodeBadge(class_303.class_7590 visible) {
/* 395 */     return (visible != null && Boolean.TRUE.equals(this.phaze$codeBadgeChatTicks.get(Integer.valueOf(visible.comp_895()))));
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$shouldDrawChatBadge(class_303.class_7590 visible) {
/* 400 */     return (visible != null && this.phaze$badgedChatTicks
/* 401 */       .contains(Integer.valueOf(visible.comp_895())) && this.phaze$drawnBadgeTicksThisFrame
/* 402 */       .add(Integer.valueOf(visible.comp_895())));
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract int method_44752();
/*     */   
/*     */   @Shadow
/*     */   public abstract int method_1811();
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ChatHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */