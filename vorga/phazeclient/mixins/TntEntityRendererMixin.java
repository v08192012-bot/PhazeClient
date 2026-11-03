/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10075;
/*    */ import net.minecraft.class_1541;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_5617;
/*    */ import net.minecraft.class_897;
/*    */ import net.minecraft.class_956;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.TntCountdown;
/*    */ 
/*    */ @Mixin({class_956.class})
/*    */ public abstract class TntEntityRendererMixin
/*    */   extends class_897<class_1541, class_10075> {
/*    */   protected TntEntityRendererMixin(class_5617.class_5618 context) {
/* 21 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"render(Lnet/minecraft/client/render/entity/state/TntEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, at = {@At("TAIL")})
/*    */   private void phaze$renderTntCountdown(class_10075 state, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/* 29 */     TntCountdown module = TntCountdown.getInstance();
/* 30 */     if (module == null || !module.showText.isValue()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 36 */     state.field_53338 = new class_243(0.0D, 1.0D, 0.0D);
/* 37 */     TntCountdown.renderTimer(state, matrices, vertexConsumers, this.field_4676
/* 38 */         .method_24197(), method_3932());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\TntEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */