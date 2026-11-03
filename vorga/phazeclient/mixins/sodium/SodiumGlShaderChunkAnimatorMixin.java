/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import vorga.phazeclient.base.util.shader.ChunkAnimatorShaderPatcher;
/*    */ import vorga.phazeclient.base.util.shader.WorldColorChunkShaderPatcher;
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
/*    */ @Mixin(targets = {"net.caffeinemc.mods.sodium.client.gl.shader.GlShader"}, remap = false)
/*    */ public abstract class SodiumGlShaderChunkAnimatorMixin
/*    */ {
/*    */   @ModifyArg(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/gl/shader/ShaderWorkarounds;safeShaderSource(ILjava/lang/CharSequence;)V", remap = false), index = 1, remap = false)
/*    */   private CharSequence phaze$injectSodiumChunkAnim(CharSequence source) {
/* 46 */     if (source == null) {
/* 47 */       return null;
/*    */     }
/* 49 */     return WorldColorChunkShaderPatcher.patch(ChunkAnimatorShaderPatcher.patch(source.toString()));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\SodiumGlShaderChunkAnimatorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */