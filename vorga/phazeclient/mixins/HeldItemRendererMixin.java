/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1306;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_742;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_759;
/*     */ import net.minecraft.class_7833;
/*     */ import net.minecraft.class_9334;
/*     */ import org.joml.Quaternionf;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChangeHand;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoRender;
/*     */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
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
/*     */ @Mixin({class_759.class})
/*     */ public abstract class HeldItemRendererMixin
/*     */ {
/*     */   @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", ordinal = 0, shift = At.Shift.AFTER)}, require = 1)
/*     */   private void phaze$applyHandOffset(class_742 player, float tickDelta, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/*  67 */     ChangeHand mod = ChangeHand.getInstance();
/*  68 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     if (item.method_7960() || item.method_57826(class_9334.field_49646)) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     mod.applyHandOffset(matrices, (hand == class_1268.field_5808), player.method_6068());
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
/*     */   @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
/*     */   private void phaze$applyHandTweaks(class_742 player, float tickDelta, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/*  99 */     float xSign = (player.method_6068() == class_1306.field_6182) ? -1.0F : 1.0F;
/* 100 */     boolean isMain = (hand == class_1268.field_5808);
/*     */ 
/*     */     
/* 103 */     NoRender noRender = NoRender.getInstance();
/* 104 */     if (noRender != null && noRender.isEnabled() && !isMain && item.method_7909() instanceof net.minecraft.class_1819 && noRender.lowerShield.isValue()) {
/* 105 */       matrices.method_46416(0.0F, -0.2F, 0.0F);
/* 106 */       matrices.method_22905(0.85F, 0.85F, 0.85F);
/*     */     } 
/*     */ 
/*     */     
/* 110 */     OldAnimations oldAnim = OldAnimations.getInstance();
/* 111 */     if (oldAnim != null && oldAnim.isEnabled()) {
/* 112 */       if (isMain && item.method_7909() instanceof net.minecraft.class_1787 && oldAnim.oldRod.isValue()) {
/* 113 */         matrices.method_46416(xSign * -0.05F, 0.05F, -0.05F);
/* 114 */         matrices.method_22907(class_7833.field_40716.rotationDegrees(xSign * 8.0F));
/* 115 */         matrices.method_22907(class_7833.field_40714.rotationDegrees(8.0F));
/*     */       } 
/* 117 */       if (oldAnim.oldBow.isValue() && item.method_31574(class_1802.field_8102) && player.method_6115()) {
/*     */         
/* 119 */         matrices.method_22907(class_7833.field_40716.rotationDegrees(xSign * 25.0F));
/* 120 */         matrices.method_46416(xSign * 0.02F, 0.0F, 0.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 125 */     ChangeHand mod = ChangeHand.getInstance();
/* 126 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/* 129 */     mod.applyHandScale(matrices, isMain);
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
/*     */   @WrapOperation(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z")})
/*     */   private boolean phaze$suppressShieldUsing(class_742 player, Operation<Boolean> original, @Local(argsOnly = true) class_1268 hand, @Local(argsOnly = true) class_1799 item) {
/* 145 */     if (hand == class_1268.field_5810 && item.method_7909() instanceof net.minecraft.class_1819 && 
/* 146 */       OldAnimations.isSwordBlockingActive((class_1657)player)) {
/* 147 */       return false;
/*     */     }
/*     */     
/* 150 */     return ((Boolean)original.call(new Object[] { player })).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WrapWithCondition(method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V")})
/*     */   private boolean phaze$skipHandSway(class_4587 instance, Quaternionf rotation) {
/* 161 */     ChangeHand mod = ChangeHand.getInstance();
/* 162 */     if (mod == null || !mod.isEnabled() || !mod.noHandSway.isValue()) {
/* 163 */       return true;
/*     */     }
/* 165 */     return false;
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
/*     */   @Inject(method = {"swingArm"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$handleOldSwingAndBlockHit(float swingProgress, float equipProgress, class_4587 matrices, int armSign, class_1306 arm, CallbackInfo ci) {
/* 181 */     OldAnimations mod = OldAnimations.getInstance();
/* 182 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     class_310 mc = class_310.method_1551();
/* 187 */     if (mc.field_1724 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     boolean isMainHand = (arm == mc.field_1724.method_6068());
/* 192 */     boolean isBlocking = (isMainHand && OldAnimations.isSwordBlockingActive((class_1657)mc.field_1724));
/*     */     
/* 194 */     if (isBlocking && 
/* 195 */       mod.swordBlocking.isValue()) {
/* 196 */       ci.cancel();
/* 197 */       OldAnimations.renderSwordBlock(matrices, arm, armSign, equipProgress, swingProgress);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 202 */     if (mod.oldSwing.isValue() && swingProgress > 0.0F) {
/* 203 */       ci.cancel();
/* 204 */       OldAnimations.renderOldSwing(matrices, arm, armSign, swingProgress, equipProgress);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"shouldSkipHandAnimationOnSwap"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$noReEquipOnSwap(class_1799 from, class_1799 to, CallbackInfoReturnable<Boolean> cir) {
/* 214 */     OldAnimations mod = OldAnimations.getInstance();
/* 215 */     if (mod != null && mod.isEnabled() && mod.noReEquip.isValue() && 
/* 216 */       from.method_31574(to.method_7909())) {
/* 217 */       cir.setReturnValue(Boolean.valueOf(true));
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
/*     */   @Redirect(method = {"updateHeldItems"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
/*     */   private float phaze$noReEquipOnAttackCooldown(class_746 player, float baseTime) {
/* 230 */     OldAnimations mod = OldAnimations.getInstance();
/* 231 */     if (mod != null && mod.isEnabled() && mod.noReEquip.isValue()) {
/* 232 */       return 1.0F;
/*     */     }
/* 234 */     return player.method_7261(baseTime);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\HeldItemRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */