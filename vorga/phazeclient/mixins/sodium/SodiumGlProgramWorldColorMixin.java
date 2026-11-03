/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorShaderHelper;
/*    */ 
/*    */ @Mixin(targets = {"net.caffeinemc.mods.sodium.client.gl.shader.GlProgram"}, remap = false)
/*    */ public abstract class SodiumGlProgramWorldColorMixin {
/*    */   @Unique
/*    */   private boolean phaze$worldColorProgramRegistered;
/*    */   
/*    */   @Inject(method = {"bind"}, at = {@At("TAIL")}, remap = false)
/*    */   private void phaze$uploadWorldColorUniforms(CallbackInfo ci) {
/* 17 */     if (!this.phaze$worldColorProgramRegistered) {
/* 18 */       WorldColorShaderHelper.registerCurrentProgramInstance();
/* 19 */       this.phaze$worldColorProgramRegistered = true;
/*    */     } 
/* 21 */     WorldColorShaderHelper.uploadTerrainUniformsToCurrentGlProgram();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\SodiumGlProgramWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */