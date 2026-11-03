/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1087;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_5819;
/*    */ import net.minecraft.class_778;
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
/*    */ 
/*    */ 
/*    */ @Mixin({class_778.class})
/*    */ public abstract class BlockModelRendererWorldColorMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*    */   private void phaze$pushBlockWorldColor(class_1920 world, class_1087 model, class_2680 state, class_2338 pos, class_4587 matrices, class_4588 vertexConsumer, boolean cull, class_5819 random, long seed, int overlay, CallbackInfo ci) {
/* 34 */     WorldColorCorrectionController.push(WorldColorCorrectionController.Target.BLOCKS);
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
/*    */   @Inject(method = {"render"}, at = {@At("RETURN")})
/*    */   private void phaze$popBlockWorldColor(class_1920 world, class_1087 model, class_2680 state, class_2338 pos, class_4587 matrices, class_4588 vertexConsumer, boolean cull, class_5819 random, long seed, int overlay, CallbackInfo ci) {
/* 51 */     WorldColorCorrectionController.pop();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BlockModelRendererWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */