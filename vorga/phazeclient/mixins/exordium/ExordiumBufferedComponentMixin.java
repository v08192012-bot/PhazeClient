/*    */ package vorga.phazeclient.mixins.exordium;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Pseudo;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.hud.ExordiumAnimationBridge;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Pseudo
/*    */ @Mixin(targets = {"dev.tr7zw.exordium.render.BufferedComponent"}, remap = false)
/*    */ public abstract class ExordiumBufferedComponentMixin
/*    */ {
/*    */   @Inject(method = {"renderBuffer"}, at = {@At("HEAD")}, cancellable = true, remap = false)
/*    */   private void phaze$deferAnimatedTexture(CallbackInfo ci) {
/* 19 */     if (ExordiumAnimationBridge.deferBufferedComponent(this))
/* 20 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\exordium\ExordiumBufferedComponentMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */