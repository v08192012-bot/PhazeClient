/*    */ package vorga.phazeclient.mixins.iris;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ import vorga.phazeclient.base.util.shader.ChunkAnimatorShaderPatcher;
/*    */ import vorga.phazeclient.base.util.shader.IrisEntityShaderPatcher;
/*    */ import vorga.phazeclient.base.util.shader.IrisFluidShaderPatcher;
/*    */ import vorga.phazeclient.base.util.shader.IrisSkyShaderPatcher;
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
/*    */ @Mixin(targets = {"net.irisshaders.iris.gl.shader.GlShader"}, remap = false)
/*    */ public abstract class IrisGlShaderChunkAnimatorMixin
/*    */ {
/*    */   @ModifyArgs(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/irisshaders/iris/gl/shader/GlShader;createShader(Lnet/irisshaders/iris/gl/shader/ShaderType;Ljava/lang/String;Ljava/lang/String;)I", remap = false), remap = false)
/*    */   private static void phaze$injectIrisShaderPatches(Args args) {
/* 71 */     String name = (String)args.get(1);
/* 72 */     String source = (String)args.get(2);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     String patched = WorldColorChunkShaderPatcher.patch(ChunkAnimatorShaderPatcher.patch(source));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     patched = IrisSkyShaderPatcher.patch(name, patched);
/* 83 */     patched = IrisEntityShaderPatcher.patch(name, patched);
/* 84 */     patched = IrisFluidShaderPatcher.patch(name, patched);
/*    */     
/* 86 */     args.set(2, patched);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\iris\IrisGlShaderChunkAnimatorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */