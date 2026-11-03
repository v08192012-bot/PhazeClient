/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_408;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.api.system.cursor.HudCursorRelay;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ import vorga.phazeclient.implement.features.modules.other.StreamerMode;
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
/*     */ @Mixin({class_408.class})
/*     */ public abstract class ChatScreenInputFieldMixin
/*     */ {
/*     */   @Unique
/*     */   private static final float FADE_TIME = 170.0F;
/*     */   @Unique
/*     */   private static final float FADE_OFFSET = 8.0F;
/*     */   @Unique
/*     */   private static final float C1 = 1.70158F;
/*     */   @Unique
/*     */   private static final float C3 = 2.70158F;
/*     */   @Unique
/*     */   private boolean phaze$wasOpenedLastFrame = false;
/*     */   @Unique
/*  46 */   private long phaze$lastOpenNanos = 0L; @Unique
/*  47 */   private float phaze$displacement = 0.0F;
/*     */   
/*     */   @Unique
/*     */   private float phaze$calculateDisplacement() {
/*  51 */     Animations module = Animations.getInstance();
/*  52 */     if (module == null || !module.isSmoothInputFieldEnabled()) {
/*  53 */       return 0.0F;
/*     */     }
/*  55 */     class_310 client = class_310.method_1551();
/*  56 */     if (client == null) {
/*  57 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (!this.phaze$wasOpenedLastFrame && client.field_1724 != null && 
/*     */       
/*  63 */       !client.field_1724.method_6113()) {
/*  64 */       this.phaze$wasOpenedLastFrame = true;
/*  65 */       this.phaze$lastOpenNanos = System.nanoTime();
/*     */     } 
/*     */     
/*  68 */     float screenFactor = client.method_22683().method_4507() / 1080.0F;
/*  69 */     float elapsedMs = (float)(System.nanoTime() - this.phaze$lastOpenNanos) / 1000000.0F;
/*  70 */     if (elapsedMs > 170.0F) elapsedMs = 170.0F; 
/*  71 */     float alpha = 1.0F - elapsedMs / 170.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     float modifiedAlpha = 2.70158F * alpha * alpha * alpha - 1.70158F * alpha * alpha;
/*  78 */     return modifiedAlpha * 8.0F * screenFactor;
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
/*     */   @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", shift = At.Shift.BEFORE)})
/*     */   private void phaze$pushInputSlide(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/*  91 */     this.phaze$displacement = phaze$calculateDisplacement();
/*  92 */     if (this.phaze$displacement == 0.0F) {
/*     */       return;
/*     */     }
/*  95 */     context.method_51452();
/*  96 */     context.method_51448().method_22903();
/*  97 */     context.method_51448().method_46416(0.0F, this.phaze$displacement, 0.0F);
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
/*     */   @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER)})
/*     */   private void phaze$popInputSlide(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 110 */     if (this.phaze$displacement == 0.0F) {
/*     */       return;
/*     */     }
/* 113 */     context.method_51452();
/* 114 */     context.method_51448().method_22909();
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"removed"}, at = {@At("HEAD")})
/*     */   private void phaze$onRemoved(CallbackInfo ci) {
/* 120 */     this.phaze$wasOpenedLastFrame = false;
/*     */   }
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*     */   private void phaze$applyHudEditorCursor(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 125 */     if (Animations.getInstance().isDynamicCursorEnabled()) {
/* 126 */       HudCursorRelay.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/* 135 */   private String phaze$savedChatText = null;
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
/*     */   @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
/*     */   private void phaze$maskedChatRender(class_342 chatField, class_332 context, int mouseX, int mouseY, float delta) {
/* 164 */     StreamerMode streamer = StreamerMode.getInstance();
/* 165 */     String original = chatField.method_1882();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     boolean swap = (streamer != null && streamer.isHidePasswordsEnabled() && original != null && !original.isEmpty() && original.charAt(0) == '/');
/* 171 */     String masked = swap ? StreamerMode.maskPasswordIfMatching(original) : null;
/* 172 */     boolean didSwap = (masked != null && !masked.equals(original));
/* 173 */     Field textField = null;
/* 174 */     if (didSwap) {
/*     */       try {
/* 176 */         textField = phaze$resolveTextField();
/* 177 */         textField.set(chatField, masked);
/* 178 */       } catch (Throwable ignored) {
/* 179 */         didSwap = false;
/*     */       } 
/*     */     }
/* 182 */     chatField.method_25394(context, mouseX, mouseY, delta);
/* 183 */     if (didSwap) {
/*     */       try {
/* 185 */         textField.set(chatField, original);
/* 186 */       } catch (Throwable ignored) {
/*     */ 
/*     */ 
/*     */         
/* 190 */         chatField.method_1852(original);
/*     */       } 
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
/*     */   @Unique
/* 204 */   private static Field phaze$cachedTextField = null;
/*     */   
/*     */   @Unique
/*     */   private static Field phaze$resolveTextField() throws NoSuchFieldException {
/* 208 */     Field f = phaze$cachedTextField;
/* 209 */     if (f != null) return f; 
/* 210 */     Class<?> c = class_342.class;
/* 211 */     NoSuchFieldException last = null;
/* 212 */     for (String name : new String[] { "text", "field_2092" }) {
/*     */       try {
/* 214 */         Field candidate = c.getDeclaredField(name);
/* 215 */         candidate.setAccessible(true);
/* 216 */         phaze$cachedTextField = candidate;
/* 217 */         return candidate;
/* 218 */       } catch (NoSuchFieldException e) {
/* 219 */         last = e;
/*     */       } 
/*     */     } 
/* 222 */     throw (last == null) ? new NoSuchFieldException("text") : last;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ChatScreenInputFieldMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */