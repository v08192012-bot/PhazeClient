/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_757;
/*     */ import net.minecraft.class_9779;
/*     */ import org.joml.Matrix4f;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.AspectRatio;
/*     */ import vorga.phazeclient.implement.features.modules.other.ColorCorrection;
/*     */ import vorga.phazeclient.implement.features.modules.other.LowHealthWarning;
/*     */ import vorga.phazeclient.implement.features.modules.other.MotionBlur;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin(value = {class_757.class}, priority = 1100)
/*     */ public abstract class GameRendererMixin
/*     */ {
/*     */   @Shadow
/*     */   private float field_4005;
/*     */   @Shadow
/*     */   private float field_3988;
/*     */   @Shadow
/*     */   private float field_4004;
/*     */   @Shadow
/*     */   private float field_4025;
/*     */   
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;draw()V", ordinal = 1)}, require = 1)
/*     */   private void phaze$renderLowHealthAboveGui(class_332 context, Operation<Void> original) {
/*  40 */     original.call(new Object[] { context });
/*  41 */     if (LowHealthWarning.getInstance().render(context)) context.method_51452();
/*     */   
/*     */   }
/*     */   
/*     */   @Inject(method = {"getBasicProjectionMatrix"}, at = {@At("TAIL")}, cancellable = true)
/*     */   private void phaze$overrideAspect(float fovDegrees, CallbackInfoReturnable<Matrix4f> cir) {
/*  47 */     AspectRatio module = AspectRatio.getInstance();
/*  48 */     if (module == null || !module.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     Matrix4f projection = new Matrix4f();
/*     */     
/*  54 */     float ratio = module.getRatio();
/*     */     
/*  56 */     if (this.field_4005 != 1.0F) {
/*  57 */       projection.translate(this.field_3988, -this.field_4004, 0.0F);
/*  58 */       projection.scale(this.field_4005, this.field_4005, 1.0F);
/*     */     } 
/*     */     
/*  61 */     projection.perspective((float)(fovDegrees * 0.017453292519943295D), ratio, 0.05F, this.field_4025 * 4.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     cir.setReturnValue(projection);
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
/*     */   @Inject(method = {"renderWorld"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;renderHand(Lnet/minecraft/client/render/Camera;FLorg/joml/Matrix4f;)V", shift = At.Shift.BEFORE)})
/*     */   private void phaze$applyMotionBlur(class_9779 tickCounter, CallbackInfo ci) {
/*  82 */     MotionBlur module = MotionBlur.getInstance();
/*  83 */     if (module == null || !module.isEnabled())
/*  84 */       return;  if (module.shader == null)
/*  85 */       return;  module.shader.applyMotionBlurBeforeHands();
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
/*     */   @Inject(method = {"renderWorld"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;renderHand(Lnet/minecraft/client/render/Camera;FLorg/joml/Matrix4f;)V", shift = At.Shift.AFTER)})
/*     */   private void phaze$applyColorCorrection(class_9779 tickCounter, CallbackInfo ci) {
/*  99 */     ColorCorrection module = ColorCorrection.getInstance();
/* 100 */     if (module == null || !module.isEnabled())
/* 101 */       return;  if (module.shader == null)
/* 102 */       return;  module.shader.apply();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\GameRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */