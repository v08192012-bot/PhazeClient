/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import net.minecraft.class_10017;
/*     */ import net.minecraft.class_10042;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3887;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_583;
/*     */ import net.minecraft.class_591;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_922;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.PhazePulseRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.hud.NametagHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitColor;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitRange;
/*     */ import vorga.phazeclient.implement.features.modules.other.ThreeDSkinsHelper;
/*     */ import vorga.phazeclient.implement.hitcolor.OverlayReloadListener;
/*     */ import vorga.phazeclient.implement.hitcolor.OverlayRendered;
/*     */ import vorga.phazeclient.implement.hitrange.HitRangeCircleRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_922.class})
/*     */ public abstract class LivingEntityRendererMixin<T extends class_1309, S extends class_10042, M extends class_583<? super S>>
/*     */ {
/*     */   @Shadow
/*     */   protected M field_4737;
/*     */   
/*     */   @Shadow
/*     */   protected abstract float method_23185(S paramS);
/*     */   
/*     */   @WrapOperation(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V")}, require = 1)
/*     */   private void phaze$renderFeatureWithHitColorOverlay(class_3887<?, ?> featureRenderer, class_4587 matrices, class_4597 vertexConsumers, int light, class_10017 entityRenderState, float limbAngle, float limbDistance, Operation<Void> original) {
/*  84 */     if (HitColor.getInstance().isEnabled() && featureRenderer instanceof OverlayRendered) { OverlayRendered rendered = (OverlayRendered)featureRenderer;
/*     */       
/*  86 */       class_10042 class_10042 = (class_10042)entityRenderState;
/*  87 */       int overlay = class_922.method_23622(class_10042, method_23185((S)class_10042));
/*  88 */       rendered.setOverlay(overlay);
/*  89 */       OverlayReloadListener.event(); }
/*     */ 
/*     */     
/*  92 */     original.call(new Object[] { featureRenderer, matrices, vertexConsumers, Integer.valueOf(light), entityRenderState, Float.valueOf(limbAngle), Float.valueOf(limbDistance) });
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
/*     */   @Inject(method = {"hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$forceOwnNametagVisibility(class_1309 entity, double squaredDistanceToCamera, CallbackInfoReturnable<Boolean> cir) {
/* 106 */     NametagHud module = NametagHud.getInstance();
/* 107 */     if (!module.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     class_310 client = class_310.method_1551();
/* 112 */     if (client == null || client.field_1724 == null || client.field_1690 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     boolean isSelf = (entity == client.field_1724 || entity == client.method_1560());
/* 117 */     if (!isSelf) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     if (module.hideInF1.isValue() && client.field_1690.field_1842) {
/* 122 */       cir.setReturnValue(Boolean.valueOf(false));
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     if (!module.thirdPersonNametag.isValue() && !client.field_1690.method_31044().method_31034()) {
/* 127 */       cir.setReturnValue(Boolean.valueOf(false));
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     cir.setReturnValue(Boolean.valueOf(true));
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
/*     */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("TAIL")})
/*     */   private void phaze$drawHitRange(S state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/*     */     class_10055 playerState;
/* 149 */     HitRange config = HitRange.getInstance();
/* 150 */     if (!config.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 154 */     if (state instanceof class_10055) { playerState = (class_10055)state; }
/*     */     else
/*     */     { return; }
/*     */     
/* 158 */     class_746 player = (class_310.method_1551()).field_1724;
/* 159 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (player.method_5628() == playerState.field_53528) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     class_243 pos = new class_243(((class_10042)state).field_53325, ((class_10042)state).field_53326, ((class_10042)state).field_53327);
/* 171 */     if (!pos.method_24802((class_2374)player.method_19538(), config.maxDistance.getInt())) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     if (config.nearestOnly.isValue() && (
/* 176 */       config.getNearest() == null || config.getNearest().method_5628() != playerState.field_53528)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 181 */     if (playerState.field_53449 > 0.0F || playerState.field_53461 || playerState.field_53463 != null) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     HitRangeCircleRenderer.drawCircle(matrices, vertexConsumers, playerState);
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
/*     */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE)})
/*     */   private void phaze$renderCosmeticInBodySpace(S state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 208 */     if (state instanceof class_10055) { class_10055 playerState = (class_10055)state;
/*     */ 
/*     */ 
/*     */       
/* 212 */       M m = this.field_4737; if (m instanceof class_591) { class_591 playerModel = (class_591)m;
/* 213 */         class_4587.class_4665 stableLightingEntry = matrices.method_23760();
/* 214 */         matrices.method_22903();
/* 215 */         playerModel.field_3391.method_22703(matrices);
/* 216 */         CosmeticsRenderer.renderLocalPlayer(matrices, vertexConsumers, playerState, light, stableLightingEntry);
/*     */ 
/*     */ 
/*     */         
/* 220 */         PhazePulseRenderer.renderBody(matrices, vertexConsumers, playerState, light);
/*     */ 
/*     */         
/* 223 */         matrices.method_22909();
/*     */         
/* 225 */         matrices.method_22903();
/* 226 */         playerModel.field_3398.method_22703(matrices);
/* 227 */         CosmeticsRenderer.renderHeadCosmetic(matrices, vertexConsumers, playerState, light, stableLightingEntry);
/*     */ 
/*     */ 
/*     */         
/* 231 */         PhazePulseRenderer.renderHead(matrices, vertexConsumers, playerState, light);
/*     */ 
/*     */         
/* 234 */         matrices.method_22909(); }
/*     */       else
/* 236 */       { CosmeticsRenderer.renderLocalPlayer(matrices, vertexConsumers, playerState, light, matrices
/*     */             
/* 238 */             .method_23760()); }
/*     */        }
/*     */   
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
/*     */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/client/render/entity/state/EntityRenderState;)V", shift = At.Shift.AFTER)}, require = 1)
/*     */   private void phaze$applyThreeDSkins(S state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 253 */     if (state instanceof class_10055) { class_10055 playerState = (class_10055)state; M m = this.field_4737; if (m instanceof class_591) { class_591 playerModel = (class_591)m;
/* 254 */         ThreeDSkinsHelper.preRender(playerModel, playerState); }
/*     */        }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE)}, require = 1)
/*     */   private void phaze$restoreThreeDSkins(S state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 267 */     M m = this.field_4737; if (m instanceof class_591) { class_591 playerModel = (class_591)m;
/* 268 */       if (state instanceof class_10055) { class_10055 playerState = (class_10055)state;
/* 269 */         ThreeDSkinsHelper.postRender(playerModel, playerState, matrices, vertexConsumers, light, 
/*     */             
/* 271 */             class_922.method_23622((class_10042)playerState, method_23185((S)playerState))); }
/*     */        }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\LivingEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */