/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_824;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorRenderHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_824.class})
/*    */ public abstract class BlockEntityRenderDispatcherWorldColorMixin
/*    */ {
/*    */   @ModifyVariable(method = {"render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at = @At("HEAD"), argsOnly = true, index = 4, require = 0)
/*    */   private class_4597 phaze$wrapBlockEntityProvider(class_4597 original) {
/* 21 */     return WorldColorRenderHelper.tintProvider(original, WorldColorCorrectionController.Target.BLOCKS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyVariable(method = {"render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at = @At("HEAD"), argsOnly = true, index = 4, require = 0)
/*    */   private static class_4597 phaze$wrapStaticBlockEntityProvider(class_4597 original) {
/* 32 */     return WorldColorRenderHelper.tintProvider(original, WorldColorCorrectionController.Target.BLOCKS);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BlockEntityRenderDispatcherWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */