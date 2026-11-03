/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_862;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.ChunkBorders;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_862.class})
/*    */ public abstract class ChunkBorderDebugRendererMixin
/*    */ {
/*    */   @Inject(method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;DDD)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$overrideChunkBorderRenderer(class_4587 matrices, class_4597 vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
/* 21 */     ChunkBorders module = ChunkBorders.getInstance();
/* 22 */     if (module != null && module.isEnabled()) {
/* 23 */       ci.cancel();
/* 24 */       module.renderCustomChunkBorders(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ChunkBorderDebugRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */