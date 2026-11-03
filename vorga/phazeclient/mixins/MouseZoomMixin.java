/*     */ package vorga.phazeclient.mixins;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_312;
/*     */ import net.minecraft.class_746;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.implement.features.modules.other.FreeLook;
/*     */ import vorga.phazeclient.implement.features.modules.other.Zoom;
/*     */ 
/*     */ @Mixin(value = {class_312.class}, priority = 500)
/*     */ public class MouseZoomMixin {
/*     */   @Unique
/*  20 */   private static float cinematic$smoothX = 0.0F;
/*     */   @Unique
/*  22 */   private static float cinematic$smoothY = 0.0F;
/*     */   
/*     */   @Final
/*     */   @Shadow
/*     */   private class_310 field_1779;
/*     */   
/*     */   @Shadow
/*     */   private double field_1789;
/*     */   
/*     */   @Shadow
/*     */   private double field_1787;
/*     */   
/*     */   @Inject(method = {"onMouseButton"}, at = {@At("HEAD")})
/*     */   private void phaze$onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
/*  36 */     if (window == this.field_1779.method_22683().method_4490()) {
/*  37 */       FreeLook freeLook = FreeLook.getInstance();
/*  38 */       if (freeLook != null && freeLook.isEnabled()) {
/*  39 */         freeLook.onBindStateChanged(button, action);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"onMouseScroll"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void scrollZoom(long window, double horizontal, double vertical, CallbackInfo ci) {
/*  46 */     if (!Zoom.getInstance().isEnabled() || !Zoom.isZoomActive()) {
/*     */       return;
/*     */     }
/*     */     
/*  50 */     if ((class_310.method_1551()).field_1755 != null) {
/*     */       return;
/*     */     }
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
/*  63 */     double currentZoom = Zoom.getInstance().getCurrentZoomLevel();
/*  64 */     double multiplier = Zoom.getInstance().getZoomScrollMultiplier();
/*  65 */     double sensitivity = Zoom.getInstance().getZoomScrollSensitivity();
/*     */     
/*  67 */     if (multiplier <= 1.0001D) {
/*     */ 
/*     */ 
/*     */       
/*  71 */       newZoom = currentZoom + vertical * currentZoom / 10.0D * sensitivity;
/*     */     } else {
/*  73 */       double exponent = vertical * sensitivity;
/*  74 */       newZoom = currentZoom * Math.pow(multiplier, exponent);
/*     */     } 
/*     */ 
/*     */     
/*  78 */     if (newZoom < 2.0D) {
/*  79 */       newZoom = 2.0D;
/*     */     }
/*     */     
/*  82 */     if (!Double.isFinite(newZoom)) {
/*  83 */       newZoom = Zoom.getInstance().getSafeMaxZoomLevel();
/*     */     }
/*     */     
/*  86 */     double newZoom = Math.max(2.0D, Math.min(newZoom, Zoom.getInstance().getSafeMaxZoomLevel()));
/*  87 */     Zoom.getInstance().setCurrentZoomLevel((float)newZoom);
/*  88 */     ci.cancel();
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
/*     */   @Redirect(method = {"updateMouse"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"))
/*     */   private void phaze$redirectChangeLookDirection(class_746 player, double cursorDeltaX, double cursorDeltaY) {
/* 109 */     FreeLook freeLook = FreeLook.getInstance();
/* 110 */     if (freeLook != null && freeLook.isEnabled() && freeLook.isActive()) {
/* 111 */       freeLook.onMouseLook(cursorDeltaX, cursorDeltaY);
/*     */       return;
/*     */     } 
/* 114 */     player.method_5872(cursorDeltaX, cursorDeltaY);
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"updateMouse"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"), index = 0)
/*     */   private double zoomSensitivityX(double x) {
/* 119 */     if (!Zoom.getInstance().isEnabled() || !Zoom.isZoomActive()) {
/* 120 */       cinematic$smoothX = 0.0F;
/* 121 */       return x;
/*     */     } 
/*     */     
/* 124 */     if ((class_310.method_1551()).field_1755 != null) {
/* 125 */       cinematic$smoothX = 0.0F;
/* 126 */       return x;
/*     */     } 
/*     */     
/* 129 */     float zoomLevel = Zoom.getInstance().getCurrentZoomLevel();
/*     */     
/* 131 */     if (Zoom.getInstance().isCinematicCamera()) {
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
/* 144 */       float dt = class_310.method_1551().method_61966().method_60636() / 20.0F;
/* 145 */       float alpha = 1.0F - (float)Math.pow(0.8500000238418579D, (60.0F * dt));
/* 146 */       if (alpha < 0.0F) alpha = 0.0F; 
/* 147 */       if (alpha > 1.0F) alpha = 1.0F; 
/* 148 */       cinematic$smoothX = (float)(cinematic$smoothX + (x - cinematic$smoothX) * alpha);
/* 149 */       return (cinematic$smoothX / zoomLevel);
/*     */     } 
/*     */     
/* 152 */     return x / zoomLevel;
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"updateMouse"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"), index = 1)
/*     */   private double zoomSensitivityY(double y) {
/* 157 */     if (!Zoom.getInstance().isEnabled() || !Zoom.isZoomActive()) {
/* 158 */       cinematic$smoothY = 0.0F;
/* 159 */       return y;
/*     */     } 
/*     */     
/* 162 */     if ((class_310.method_1551()).field_1755 != null) {
/* 163 */       cinematic$smoothY = 0.0F;
/* 164 */       return y;
/*     */     } 
/*     */     
/* 167 */     float zoomLevel = Zoom.getInstance().getCurrentZoomLevel();
/*     */     
/* 169 */     if (Zoom.getInstance().isCinematicCamera()) {
/*     */ 
/*     */       
/* 172 */       float dt = class_310.method_1551().method_61966().method_60636() / 20.0F;
/* 173 */       float alpha = 1.0F - (float)Math.pow(0.8500000238418579D, (60.0F * dt));
/* 174 */       if (alpha < 0.0F) alpha = 0.0F; 
/* 175 */       if (alpha > 1.0F) alpha = 1.0F; 
/* 176 */       cinematic$smoothY = (float)(cinematic$smoothY + (y - cinematic$smoothY) * alpha);
/* 177 */       return (cinematic$smoothY / zoomLevel);
/*     */     } 
/*     */     
/* 180 */     return y / zoomLevel;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\MouseZoomMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */