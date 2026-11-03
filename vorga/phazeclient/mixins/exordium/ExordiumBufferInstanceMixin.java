/*    */ package vorga.phazeclient.mixins.exordium;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Pseudo;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.api.system.hud.ExordiumAnimationBridge;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Pseudo
/*    */ @Mixin(targets = {"dev.tr7zw.exordium.components.BufferInstance"}, remap = false)
/*    */ public abstract class ExordiumBufferInstanceMixin
/*    */ {
/*    */   @Inject(method = {"renderBuffer"}, at = {@At("HEAD")}, remap = false)
/*    */   private void phaze$beforeRenderBuffer(Object state, class_332 context, CallbackInfoReturnable<Boolean> cir) {
/* 24 */     ExordiumAnimationBridge.beforeBufferRender(this, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderBuffer"}, at = {@At("RETURN")}, remap = false)
/*    */   private void phaze$afterRenderBuffer(Object state, class_332 context, CallbackInfoReturnable<Boolean> cir) {
/* 33 */     ExordiumAnimationBridge.afterBufferRender(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"postRender"}, at = {@At("RETURN")}, remap = false)
/*    */   private void phaze$afterPostRender(Object state, class_332 context, CallbackInfo ci) {
/* 42 */     ExordiumAnimationBridge.afterBufferPostRender(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\exordium\ExordiumBufferInstanceMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */