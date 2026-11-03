/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_897;
/*    */ import net.minecraft.class_898;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorRenderHelper;
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
/*    */ @Mixin({class_898.class})
/*    */ public abstract class EntityRenderDispatcherWorldColorMixin
/*    */ {
/*    */   @Inject(method = {"render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V"}, at = {@At("HEAD")})
/*    */   private <E extends net.minecraft.class_1297> void phaze$pushEntityWorldColor(E entity, double x, double y, double z, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light, class_897<? super E, ?> renderer, CallbackInfo ci) {
/* 38 */     WorldColorCorrectionController.Target target = (entity instanceof net.minecraft.class_1657) ? WorldColorCorrectionController.Target.PLAYERS : WorldColorCorrectionController.Target.ENTITIES;
/* 39 */     WorldColorCorrectionController.push(target);
/* 40 */     WorldColorRenderHelper.beginEntityShaderAlpha(target);
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
/*    */   @Inject(method = {"render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V"}, at = {@At("RETURN")})
/*    */   private <E extends net.minecraft.class_1297> void phaze$popEntityWorldColor(E entity, double x, double y, double z, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light, class_897<? super E, ?> renderer, CallbackInfo ci) {
/* 59 */     WorldColorRenderHelper.endEntityShaderAlpha();
/* 60 */     WorldColorCorrectionController.pop();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EntityRenderDispatcherWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */