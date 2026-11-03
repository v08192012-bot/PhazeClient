/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_309;
/*     */ import net.minecraft.class_310;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.AucHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.AutoSwap;
/*     */ import vorga.phazeclient.implement.features.modules.other.Binds;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChangeHand;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChunkAnimator;
/*     */ import vorga.phazeclient.implement.features.modules.other.ElytraUtility;
/*     */ import vorga.phazeclient.implement.features.modules.other.FastSwap;
/*     */ import vorga.phazeclient.implement.features.modules.other.FreeLook;
/*     */ import vorga.phazeclient.implement.features.modules.other.Zoom;
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
/*     */ @Mixin({class_309.class})
/*     */ public abstract class KeyboardMixin
/*     */ {
/*     */   @Inject(method = {"onKey"}, at = {@At("HEAD")})
/*     */   private void phaze$onKeyAucHelper(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
/*  67 */     AucHelper.getInstance().onBindPressed(key, action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onKey"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$onKeyAutoSwap(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
/*  75 */     if (!AutoSwap.getInstance().isEnabled() || !AutoSwap.getInstance().canActivateInGame()) {
/*     */       return;
/*     */     }
/*  78 */     int bindKey = (AutoSwap.getInstance()).keybind.getKey();
/*  79 */     if (bindKey == key && bindKey != -1 && action == 1) {
/*  80 */       AutoSwap.getInstance().activateDirectSwap();
/*  81 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onKey"}, at = {@At("HEAD")})
/*     */   private void phaze$onKeyBinds(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
/*  91 */     Binds.getInstance().onKey(key, action);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onKey"}, at = {@At("HEAD")})
/*     */   private void phaze$onKeyFastSwap(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
/*  98 */     FastSwap.getInstance().onKey(key, action);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onKey"}, at = {@At("HEAD")})
/*     */   private void phaze$onKeyElytraUtility(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
/* 105 */     ElytraUtility module = ElytraUtility.getInstance();
/* 106 */     if (module == null) {
/*     */       return;
/*     */     }
/* 109 */     module.onBindStateChanged(key, action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onKey"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$onKeyZoom(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
/* 120 */     FreeLook freeLook = FreeLook.getInstance();
/* 121 */     if (freeLook != null && freeLook.isEnabled()) {
/* 122 */       freeLook.onBindStateChanged(key, action);
/*     */     }
/*     */     
/* 125 */     ChangeHand changeHand = ChangeHand.getInstance();
/* 126 */     if (changeHand != null && changeHand.isEnabled()) {
/* 127 */       changeHand.onBindStateChanged(key, action);
/*     */     }
/*     */     
/* 130 */     int bindKey = (Zoom.getInstance()).keybind.getKey();
/* 131 */     if (bindKey == key && bindKey != -1) {
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
/* 142 */       class_310 mc = class_310.method_1551();
/* 143 */       boolean inGui = (mc != null && mc.field_1755 != null);
/* 144 */       if (action == 1) {
/* 145 */         if (Zoom.getInstance().isEnabled() && !inGui) {
/* 146 */           if (Zoom.getInstance().isHold()) {
/* 147 */             Zoom.setZoomActive(true);
/*     */           } else {
/* 149 */             Zoom.setZoomActive(!Zoom.isZoomActive());
/*     */           } 
/*     */         }
/* 152 */         ci.cancel();
/* 153 */       } else if (action == 0) {
/* 154 */         if (Zoom.getInstance().isEnabled() && Zoom.getInstance().isHold() && !inGui) {
/* 155 */           Zoom.setZoomActive(false);
/*     */         }
/* 157 */         ci.cancel();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"processF3(I)Z"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;reload()V")})
/*     */   private void phaze$chunkAnimatorOnF3A(int key, CallbackInfoReturnable<Boolean> cir) {
/* 178 */     ChunkAnimator.getInstance().onF3AReload();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\KeyboardMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */