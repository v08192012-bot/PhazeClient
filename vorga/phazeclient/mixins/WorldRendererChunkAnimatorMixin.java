/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import net.minecraft.class_761;
/*    */ import net.minecraft.class_846;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ import vorga.phazeclient.implement.features.modules.other.ChunkAnimator;
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
/*    */ @Mixin({class_761.class})
/*    */ public abstract class WorldRendererChunkAnimatorMixin
/*    */ {
/* 62 */   private static final float[] PHAZE$DIR = new float[3];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyArgs(method = {"renderLayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/GlUniform;set(FFF)V", ordinal = 0))
/*    */   private void phaze$chunkAnimatorModelOffset(Args args, @Local class_846.class_851 builtChunk) {
/* 73 */     ChunkAnimator animator = ChunkAnimator.getInstance();
/* 74 */     if (animator == null || !animator.isEnabled() || builtChunk == null) {
/*    */       return;
/*    */     }
/* 77 */     float magnitude = animator.getYOffset(builtChunk.method_3670());
/* 78 */     if (magnitude == 0.0F) {
/*    */       return;
/*    */     }
/* 81 */     animator.writeAnimationDirection(PHAZE$DIR);
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
/* 92 */     if (PHAZE$DIR[0] != 0.0F) args.set(0, Float.valueOf(((Float)args.get(0)).floatValue() + magnitude * PHAZE$DIR[0])); 
/* 93 */     if (PHAZE$DIR[1] != 0.0F) args.set(1, Float.valueOf(((Float)args.get(1)).floatValue() + magnitude * PHAZE$DIR[1])); 
/* 94 */     if (PHAZE$DIR[2] != 0.0F) args.set(2, Float.valueOf(((Float)args.get(2)).floatValue() + magnitude * PHAZE$DIR[2])); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\WorldRendererChunkAnimatorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */