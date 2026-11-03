/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_761;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_761.class})
/*    */ public abstract class WorldRendererEntityWorldColorMixin
/*    */ {
/*    */   @ModifyVariable(method = {"renderEntity"}, at = @At("HEAD"), argsOnly = true, ordinal = 0, require = 0)
/*    */   private class_4597 phaze$wrapEntityProvider(class_4597 original, class_1297 entity, double cameraX, double cameraY, double cameraZ, float tickDelta, class_4587 matrices) {
/* 26 */     if (entity == null) {
/* 27 */       return original;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     WorldColorCorrectionController.Target target = (entity instanceof net.minecraft.class_1657) ? WorldColorCorrectionController.Target.PLAYERS : WorldColorCorrectionController.Target.ENTITIES;
/* 34 */     return WorldColorRenderHelper.shouldWrapEntityProvider(target) ? 
/* 35 */       WorldColorRenderHelper.tintProvider(original, target) : 
/* 36 */       original;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\WorldRendererEntityWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */