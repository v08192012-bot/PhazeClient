/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_2586;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_824;
/*    */ import net.minecraft.class_827;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.ChunkAnimator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_824.class})
/*    */ public abstract class BlockEntityRenderDispatcherChunkAnimatorMixin
/*    */ {
/*    */   @Inject(method = {"render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*    */   private <E extends class_2586> void phaze$hideContainersDuringChunkAnimation(E blockEntity, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
/* 38 */     if (phaze$shouldHideContainer((class_2586)blockEntity)) {
/* 39 */       ci.cancel();
/*    */     }
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*    */   private static <E extends class_2586> void phaze$hideContainersDuringDirectRender(class_827<E> renderer, E blockEntity, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
/* 59 */     if (phaze$shouldHideContainer((class_2586)blockEntity)) {
/* 60 */       ci.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   private static boolean phaze$shouldHideContainer(class_2586 blockEntity) {
/* 65 */     if (!(blockEntity instanceof net.minecraft.class_2595) && !(blockEntity instanceof net.minecraft.class_2627) && !(blockEntity instanceof net.minecraft.class_3719))
/*    */     {
/*    */       
/* 68 */       return false;
/*    */     }
/* 70 */     return ChunkAnimator.getInstance().isColumnAnimating(blockEntity.method_11016());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BlockEntityRenderDispatcherChunkAnimatorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */