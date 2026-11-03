/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_357;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
/*    */ @Mixin({class_357.class})
/*    */ public abstract class SliderWidgetCursorMixin
/*    */ {
/*    */   @Inject(method = {"onClick(DD)V"}, at = {@At("HEAD")}, require = 0)
/*    */   private void phaze$cursorOnClick(double mouseX, double mouseY, CallbackInfo ci) {
/* 35 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 38 */     CursorManager.beginDrag(221189);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"onRelease(DD)V"}, at = {@At("HEAD")}, require = 0)
/*    */   private void phaze$cursorOnRelease(double mouseX, double mouseY, CallbackInfo ci) {
/* 46 */     CursorManager.endDrag();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"mouseScrolled(DDDD)Z"}, at = {@At("HEAD")}, require = 0)
/*    */   private void phaze$cursorOnScroll(double mouseX, double mouseY, double horizontal, double vertical, CallbackInfoReturnable<Boolean> cir) {
/* 53 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     CursorManager.notifyScroll((horizontal != 0.0D) ? horizontal : vertical, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\SliderWidgetCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */