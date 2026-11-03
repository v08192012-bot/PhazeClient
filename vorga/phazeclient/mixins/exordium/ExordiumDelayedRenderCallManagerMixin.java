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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Pseudo
/*    */ @Mixin(targets = {"dev.tr7zw.exordium.util.DelayedRenderCallManager"}, remap = false)
/*    */ public abstract class ExordiumDelayedRenderCallManagerMixin
/*    */ {
/*    */   @Inject(method = {"renderComponents"}, at = {@At(value = "INVOKE", target = "Ldev/tr7zw/exordium/util/rendersystem/MultiStateHolder;apply()V", shift = At.Shift.BEFORE)}, remap = false)
/*    */   private void phaze$renderAnimatedCachedTextures(CallbackInfo ci) {
/* 27 */     ExordiumAnimationBridge.renderDeferredComponents();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\exordium\ExordiumDelayedRenderCallManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */