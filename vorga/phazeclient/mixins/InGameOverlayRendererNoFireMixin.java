/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_4603;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.NoRender;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_4603.class})
/*    */ public abstract class InGameOverlayRendererNoFireMixin
/*    */ {
/*    */   @Inject(method = {"renderFireOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*    */   private static void phaze$skipFireOverlay(class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
/* 45 */     NoRender mod = NoRender.getInstance();
/* 46 */     if (mod != null && mod.isEnabled() && mod.fire.isValue())
/* 47 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\InGameOverlayRendererNoFireMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */