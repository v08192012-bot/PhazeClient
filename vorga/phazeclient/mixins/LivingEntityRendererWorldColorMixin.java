/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10042;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_922;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_922.class})
/*    */ public abstract class LivingEntityRendererWorldColorMixin
/*    */ {
/*    */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("HEAD")})
/*    */   private void phaze$pushLivingEntityWorldColor(class_10042 state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 28 */     WorldColorCorrectionController.push(
/* 29 */         (state instanceof net.minecraft.class_10055) ? 
/* 30 */         WorldColorCorrectionController.Target.PLAYERS : 
/* 31 */         WorldColorCorrectionController.Target.ENTITIES);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("RETURN")})
/*    */   private void phaze$popLivingEntityWorldColor(class_10042 state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 46 */     WorldColorCorrectionController.pop();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\LivingEntityRendererWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */