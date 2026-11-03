/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_5944;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorShaderHelper;
/*    */ 
/*    */ @Mixin({class_5944.class})
/*    */ public abstract class ShaderProgramWorldColorMixin {
/*    */   @Unique
/*    */   private boolean phaze$worldColorProgramRegistered;
/*    */   
/*    */   @Inject(method = {"bind"}, at = {@At("TAIL")}, require = 0)
/*    */   private void phaze$uploadTerrainUniformsOnBind(CallbackInfo ci) {
/* 18 */     if (!this.phaze$worldColorProgramRegistered) {
/* 19 */       WorldColorShaderHelper.registerCurrentProgramInstance();
/* 20 */       this.phaze$worldColorProgramRegistered = true;
/*    */     } 
/* 22 */     WorldColorShaderHelper.uploadTerrainUniformsToCurrentGlProgram();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ShaderProgramWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */