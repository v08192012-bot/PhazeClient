/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_4588;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
/*    */ 
/*    */ @Mixin({class_4588.class})
/*    */ public interface VertexConsumerWorldColorMixin
/*    */ {
/*    */   @Inject(method = {"color(I)Lnet/minecraft/client/render/VertexConsumer;"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$applyWorldColorToArgb(int argb, CallbackInfoReturnable<class_4588> cir) {
/* 15 */     int transformed = WorldColorCorrectionController.applyToArgb(argb);
/* 16 */     if (transformed == argb) {
/*    */       return;
/*    */     }
/*    */     
/* 20 */     class_4588 self = (class_4588)this;
/* 21 */     cir.setReturnValue(self.method_1336(transformed >>> 16 & 0xFF, transformed >>> 8 & 0xFF, transformed & 0xFF, transformed >>> 24 & 0xFF));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"color(FFFF)Lnet/minecraft/client/render/VertexConsumer;"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$applyWorldColorToFloats(float red, float green, float blue, float alpha, CallbackInfoReturnable<class_4588> cir) {
/* 31 */     int argb = ((int)(alpha * 255.0F) & 0xFF) << 24 | ((int)(red * 255.0F) & 0xFF) << 16 | ((int)(green * 255.0F) & 0xFF) << 8 | (int)(blue * 255.0F) & 0xFF;
/*    */ 
/*    */ 
/*    */     
/* 35 */     int transformed = WorldColorCorrectionController.applyToArgb(argb);
/* 36 */     if (transformed == argb) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     class_4588 self = (class_4588)this;
/* 41 */     cir.setReturnValue(self.method_1336(transformed >>> 16 & 0xFF, transformed >>> 8 & 0xFF, transformed & 0xFF, transformed >>> 24 & 0xFF));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\VertexConsumerWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */