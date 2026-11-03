/*    */ package vorga.phazeclient.mixins.iris;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorShaderHelper;
/*    */ 
/*    */ @Mixin(targets = {"net.irisshaders.iris.gl.program.Program"}, remap = false)
/*    */ public abstract class IrisProgramWorldColorMixin {
/*    */   @Unique
/*    */   private boolean phaze$worldColorProgramRegistered;
/*    */   
/*    */   @Inject(method = {"use"}, at = {@At("TAIL")}, require = 0, remap = false)
/*    */   private void phaze$uploadWorldColorUniforms(CallbackInfo ci) {
/* 17 */     if (!this.phaze$worldColorProgramRegistered) {
/* 18 */       WorldColorShaderHelper.registerCurrentProgramInstance();
/* 19 */       this.phaze$worldColorProgramRegistered = true;
/*    */     } 
/* 21 */     WorldColorShaderHelper.uploadTerrainUniformsToCurrentGlProgram();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\iris\IrisProgramWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */