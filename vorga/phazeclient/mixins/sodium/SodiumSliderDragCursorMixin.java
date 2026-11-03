/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*    */ import vorga.phazeclient.implement.features.modules.other.Animations;
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
/*    */ @Mixin(targets = {"net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl$Button"}, remap = false)
/*    */ public abstract class SodiumSliderDragCursorMixin
/*    */ {
/*    */   @Inject(method = {"mouseDragged"}, at = {@At("HEAD")}, require = 0)
/*    */   private void phaze$cursorOnDrag(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
/* 41 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 47 */     CursorManager.notifyScroll(1.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\SodiumSliderDragCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */