/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_10039;
/*     */ import net.minecraft.class_1542;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4608;
/*     */ import net.minecraft.class_5819;
/*     */ import net.minecraft.class_7833;
/*     */ import net.minecraft.class_916;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.api.system.itemphysics.ItemPhysicsManager;
/*     */ import vorga.phazeclient.implement.features.modules.other.ItemPhysics;
/*     */ 
/*     */ 
/*     */ @Mixin({class_916.class})
/*     */ public class ItemEntityRendererPhysicsMixin
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private class_5819 field_4725;
/*     */   @Unique
/*  29 */   private int phaze$currentEntityId = -1;
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$currentIsBlock = false;
/*     */ 
/*     */   
/*     */   @Inject(method = {"updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V"}, at = {@At("TAIL")})
/*     */   private void captureEntityId(class_1542 entity, class_10039 state, float tickDelta, CallbackInfo ci) {
/*  37 */     if (ItemPhysics.getInstance().isEnabled()) {
/*  38 */       this.phaze$currentEntityId = entity.method_5628();
/*  39 */       this.phaze$currentIsBlock = (!state.field_55310.method_65606() && state.field_55310.method_65607());
/*  40 */       ItemPhysicsManager.getInstance().updateRotation(entity, this.phaze$currentIsBlock);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void applyPhysics(class_10039 state, class_4587 matrixStack, class_4597 vcp, int light, CallbackInfo ci) {
/*  46 */     if (!ItemPhysics.getInstance().isEnabled())
/*  47 */       return;  if (this.phaze$currentEntityId < 0)
/*  48 */       return;  if (state.field_55310.method_65606())
/*     */       return; 
/*  50 */     ItemPhysicsManager.ItemPhysicsData data = ItemPhysicsManager.getInstance().getItemData(this.phaze$currentEntityId);
/*  51 */     if (data == null)
/*     */       return; 
/*  53 */     matrixStack.method_22903();
/*     */     
/*  55 */     this.field_4725.method_43052(state.field_55312);
/*  56 */     boolean isBlock = state.field_55310.method_65607();
/*     */ 
/*     */     
/*  59 */     matrixStack.method_22907(class_7833.field_40714.rotationDegrees(90.0F));
/*     */     
/*  61 */     matrixStack.method_22907(class_7833.field_40718.rotationDegrees(data.yRot));
/*     */     
/*  63 */     float scaleY = (state.field_55310.method_65609()).field_4285.y();
/*     */     
/*  65 */     if (isBlock) {
/*     */       
/*  67 */       matrixStack.method_46416(0.0F, -0.2F, -0.08F);
/*  68 */       matrixStack.method_46416(0.0F, scaleY, 0.0F);
/*  69 */       matrixStack.method_22907(class_7833.field_40716.rotationDegrees(data.xRot));
/*  70 */       matrixStack.method_46416(0.0F, -scaleY, 0.0F);
/*     */     } else {
/*     */       
/*  73 */       matrixStack.method_46416(0.0F, 0.0F, -0.04F);
/*  74 */       matrixStack.method_22907(class_7833.field_40716.rotationDegrees(data.xRot));
/*     */     } 
/*     */     
/*  77 */     int renderedAmount = state.field_55311;
/*     */     
/*  79 */     if (!isBlock) {
/*  80 */       float f7 = -0.0F * (renderedAmount - 1) * 0.5F;
/*  81 */       float f8 = -0.0F * (renderedAmount - 1) * 0.5F;
/*  82 */       float f9 = -0.09375F * (renderedAmount - 1) * 0.5F;
/*  83 */       matrixStack.method_46416(f7, f8, f9);
/*     */     } 
/*     */     
/*  86 */     float sx = (state.field_55310.method_65609()).field_4285.x();
/*  87 */     float sy = (state.field_55310.method_65609()).field_4285.y();
/*  88 */     float sz = (state.field_55310.method_65609()).field_4285.z();
/*     */     
/*  90 */     for (int k = 0; k < renderedAmount; k++) {
/*  91 */       matrixStack.method_22903();
/*  92 */       if (k > 0) {
/*  93 */         if (isBlock) {
/*  94 */           float rx = (this.field_4725.method_43057() * 2.0F - 1.0F) * 0.15F;
/*  95 */           float ry = (this.field_4725.method_43057() * 2.0F - 1.0F) * 0.15F;
/*  96 */           float rz = (this.field_4725.method_43057() * 2.0F - 1.0F) * 0.15F;
/*  97 */           matrixStack.method_46416(rx, ry, rz);
/*     */         } else {
/*  99 */           float rx = (this.field_4725.method_43057() * 2.0F - 1.0F) * 0.15F * 0.5F;
/* 100 */           float ry = (this.field_4725.method_43057() * 2.0F - 1.0F) * 0.15F * 0.5F;
/* 101 */           matrixStack.method_46416(rx, ry, 0.0F);
/*     */         } 
/*     */       }
/* 104 */       state.field_55310.method_65604(matrixStack, vcp, light, class_4608.field_21444);
/* 105 */       matrixStack.method_22909();
/* 106 */       if (!isBlock) {
/* 107 */         matrixStack.method_46416(0.0F * sx, 0.0F * sy, 0.09375F * sz);
/*     */       }
/*     */     } 
/*     */     
/* 111 */     matrixStack.method_22909();
/*     */     
/* 113 */     ci.cancel();
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static int phaze$getRenderedAmount(int count) {
/* 118 */     if (count > 48) return 5; 
/* 119 */     if (count > 32) return 4; 
/* 120 */     if (count > 16) return 3; 
/* 121 */     if (count > 1) return 2; 
/* 122 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ItemEntityRendererPhysicsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */