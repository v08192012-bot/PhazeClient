/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.caffeinemc.mods.sodium.client.render.immediate.CloudRenderer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
/*    */ 
/*    */ @Mixin(value = {CloudRenderer.class}, remap = false)
/*    */ public abstract class SodiumCloudRendererWorldColorMixin {
/*    */   @Inject(method = {"render"}, at = {@At("HEAD")}, remap = false)
/*    */   private void phaze$pushSodiumCloudWorldColor(CallbackInfo ci) {
/* 16 */     WorldColorCorrectionController.push(WorldColorCorrectionController.Target.CLOUDS);
/*    */   }
/*    */   
/*    */   @Inject(method = {"render"}, at = {@At("RETURN")}, remap = false)
/*    */   private void phaze$popSodiumCloudWorldColor(CallbackInfo ci) {
/* 21 */     WorldColorCorrectionController.pop();
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
/*    */   @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0), remap = false)
/*    */   private void phaze$applySodiumCloudWorldColor(float red, float green, float blue, float alpha) {
/* 34 */     int argb = ((int)(alpha * 255.0F) & 0xFF) << 24 | ((int)(red * 255.0F) & 0xFF) << 16 | ((int)(green * 255.0F) & 0xFF) << 8 | (int)(blue * 255.0F) & 0xFF;
/*    */ 
/*    */ 
/*    */     
/* 38 */     int transformed = WorldColorCorrectionController.applyToArgb(WorldColorCorrectionController.Target.CLOUDS, argb);
/* 39 */     RenderSystem.setShaderColor((transformed >>> 16 & 0xFF) / 255.0F, (transformed >>> 8 & 0xFF) / 255.0F, (transformed & 0xFF) / 255.0F, (transformed >>> 24 & 0xFF) / 255.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\SodiumCloudRendererWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */