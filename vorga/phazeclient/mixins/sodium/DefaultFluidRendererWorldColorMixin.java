/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin(value = {DefaultFluidRenderer.class}, remap = false)
/*    */ public abstract class DefaultFluidRendererWorldColorMixin
/*    */ {
/*    */   @Inject(method = {"writeQuad"}, at = {@At("HEAD")}, require = 0, remap = false)
/*    */   private void phaze$markFluidQuadForLiveColorCorrection(CallbackInfo ci) {
/* 17 */     if (this.quadColors == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 24 */     for (int i = 0; i < Math.min(this.quadColors.length, 4); i++)
/* 25 */       this.quadColors[i] = this.quadColors[i] & 0xFFFFFF | 0xFE000000; 
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   @Final
/*    */   private int[] quadColors;
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\DefaultFluidRendererWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */