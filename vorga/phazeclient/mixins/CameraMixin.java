/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_5498;
/*     */ import net.minecraft.class_5636;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ import vorga.phazeclient.implement.features.modules.other.FreeLook;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoFluid;
/*     */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
/*     */ import vorga.phazeclient.implement.features.modules.other.SmoothCamera;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_4184.class})
/*     */ public abstract class CameraMixin
/*     */ {
/*     */   @Shadow
/*     */   private float field_18721;
/*     */   @Shadow
/*     */   private float field_18722;
/*     */   @Unique
/*  44 */   private static float phaze$smoothCamYaw = Float.NaN; @Unique
/*  45 */   private static float phaze$smoothCamPitch = Float.NaN; @Unique
/*  46 */   private static long phaze$smoothCamLastNanos = 0L; @Unique
/*  47 */   private static class_5498 phaze$smoothCamLastPerspective = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyArgs(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
/*     */   private void phaze$smoothCameraRotation(Args args) {
/*     */     float dtSeconds, factor;
/*  54 */     SmoothCamera module = SmoothCamera.getInstance();
/*  55 */     if (module == null || !module.isEnabled()) {
/*  56 */       phaze$smoothCamYaw = Float.NaN;
/*  57 */       phaze$smoothCamPitch = Float.NaN;
/*  58 */       phaze$smoothCamLastNanos = 0L;
/*  59 */       phaze$smoothCamLastPerspective = null;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  65 */     FreeLook freeLook = FreeLook.getInstance();
/*  66 */     if (freeLook != null && freeLook.isEnabled() && freeLook.isActive()) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     float targetYaw = ((Float)args.get(0)).floatValue();
/*  71 */     float targetPitch = ((Float)args.get(1)).floatValue();
/*     */ 
/*     */ 
/*     */     
/*  75 */     float smoothness = module.getSmoothness();
/*  76 */     if (smoothness < 0.0F) smoothness = 0.0F; 
/*  77 */     if (smoothness > 0.99F) smoothness = 0.99F; 
/*  78 */     float halfLife = smoothness * 0.15F;
/*     */     
/*  80 */     long now = System.nanoTime();
/*     */     
/*  82 */     if (phaze$smoothCamLastNanos == 0L) {
/*  83 */       dtSeconds = 0.016666668F;
/*     */     } else {
/*  85 */       dtSeconds = (float)(now - phaze$smoothCamLastNanos) / 1.0E9F;
/*  86 */       if (dtSeconds > 0.25F) dtSeconds = 0.25F; 
/*  87 */       if (dtSeconds < 0.0F) dtSeconds = 0.0F; 
/*     */     } 
/*  89 */     phaze$smoothCamLastNanos = now;
/*     */ 
/*     */     
/*  92 */     if (halfLife <= 1.0E-4F) {
/*  93 */       factor = 1.0F;
/*     */     } else {
/*  95 */       factor = 1.0F - (float)Math.pow(0.5D, (dtSeconds / halfLife));
/*     */     } 
/*  97 */     if (factor < 0.0F) factor = 0.0F; 
/*  98 */     if (factor > 1.0F) factor = 1.0F;
/*     */     
/* 100 */     class_5498 currentPerspective = phaze$currentPerspective();
/*     */ 
/*     */     
/* 103 */     if (Float.isNaN(phaze$smoothCamYaw) || Float.isNaN(phaze$smoothCamPitch)) {
/* 104 */       phaze$smoothCamYaw = targetYaw;
/* 105 */       phaze$smoothCamPitch = targetPitch;
/* 106 */       phaze$smoothCamLastPerspective = currentPerspective;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (currentPerspective != phaze$smoothCamLastPerspective) {
/* 122 */       phaze$smoothCamYaw = targetYaw;
/* 123 */       phaze$smoothCamPitch = targetPitch;
/* 124 */       phaze$smoothCamLastPerspective = currentPerspective;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 131 */     float deltaYaw = class_3532.method_15393(targetYaw - phaze$smoothCamYaw);
/* 132 */     float deltaPitch = targetPitch - phaze$smoothCamPitch;
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
/* 146 */     if (Math.abs(deltaYaw) > 170.0F || Math.abs(deltaPitch) > 80.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     phaze$smoothCamYaw += deltaYaw * factor;
/* 151 */     phaze$smoothCamPitch += deltaPitch * factor;
/*     */     
/* 153 */     if (phaze$smoothCamPitch > 90.0F) phaze$smoothCamPitch = 90.0F; 
/* 154 */     if (phaze$smoothCamPitch < -90.0F) phaze$smoothCamPitch = -90.0F;
/*     */     
/* 156 */     args.set(0, Float.valueOf(phaze$smoothCamYaw));
/* 157 */     args.set(1, Float.valueOf(phaze$smoothCamPitch));
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static class_5498 phaze$currentPerspective() {
/* 162 */     class_310 mc = class_310.method_1551();
/* 163 */     if (mc == null || mc.field_1690 == null) return null; 
/* 164 */     return mc.field_1690.method_31044();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyArgs(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
/*     */   private void phaze$freeLookRotation(Args args) {
/* 173 */     FreeLook freeLook = FreeLook.getInstance();
/* 174 */     if (freeLook == null || !freeLook.isEnabled() || !freeLook.isActive()) {
/*     */       return;
/*     */     }
/* 177 */     args.set(0, Float.valueOf(freeLook.getCameraYaw(1.0F)));
/* 178 */     args.set(1, Float.valueOf(freeLook.getCameraPitch(1.0F)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"update"}, at = {@At("HEAD")})
/*     */   private void phaze$tickSmoothF5(class_1922 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
/* 186 */     class_310 mc = class_310.method_1551();
/* 187 */     Animations animations = Animations.getInstance();
/* 188 */     if (mc == null || mc.field_1690 == null || animations == null) {
/*     */       return;
/*     */     }
/* 191 */     animations.tickSmoothF5(mc.field_1690.method_31044());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"update"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
/*     */   private boolean phaze$forceThirdPersonDuringSlide(boolean thirdPerson) {
/* 200 */     Animations animations = Animations.getInstance();
/* 201 */     if (animations == null || !animations.isSmoothF5Enabled()) {
/* 202 */       return thirdPerson;
/*     */     }
/* 204 */     return (thirdPerson || animations.isF5AnimationActive());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyArg(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(F)F"))
/*     */   private float phaze$smoothCameraDistance(float distance) {
/* 213 */     Animations animations = Animations.getInstance();
/* 214 */     if (animations == null || !animations.isSmoothF5Enabled()) {
/* 215 */       return distance;
/*     */     }
/* 217 */     return animations.currentF5Distance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getSubmersionType"}, at = {@At("RETURN")}, cancellable = true, require = 0)
/*     */   private void phaze$rewriteSubmersionType(CallbackInfoReturnable<class_5636> cir) {
/* 226 */     NoFluid mod = NoFluid.getInstance();
/* 227 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/* 230 */     class_5636 type = (class_5636)cir.getReturnValue();
/* 231 */     if (type == class_5636.field_27886 && mod.shouldHideWater()) {
/* 232 */       cir.setReturnValue(class_5636.field_27888);
/* 233 */     } else if (type == class_5636.field_27885 && mod.shouldHideLava()) {
/* 234 */       cir.setReturnValue(class_5636.field_27888);
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
/*     */   @Redirect(method = {"updateEyeHeight"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getStandingEyeHeight()F"))
/*     */   private float phaze$oldSneakEyeHeight(class_1297 entity) {
/* 253 */     OldAnimations oldAnim = OldAnimations.getInstance();
/* 254 */     if (oldAnim != null && oldAnim.isEnabled() && oldAnim.oldSneak.isValue() && (
/* 255 */       entity.method_18276() || entity.method_5715())) {
/* 256 */       return 1.54F;
/*     */     }
/*     */     
/* 259 */     return entity.method_5751();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\CameraMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */