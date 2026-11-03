/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import net.caffeinemc.mods.sodium.client.gl.shader.ShaderParser;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
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
/*    */ @Mixin(value = {ShaderParser.class}, remap = false)
/*    */ public abstract class ShaderParserChunkAnimatorMixin
/*    */ {
/*    */   @ModifyReturnValue(method = {"parseShader(Ljava/lang/String;Lnet/caffeinemc/mods/sodium/client/gl/shader/ShaderConstants;)Ljava/lang/String;"}, at = {@At("RETURN")}, remap = false)
/*    */   private static String phaze$injectChunkAnim(String original) {
/* 70 */     return WorldColorChunkShaderPatcher.patch(ChunkAnimatorShaderPatcher.patch(original));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\ShaderParserChunkAnimatorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */