/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_357;
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
/*    */ 
/*    */ @Mixin({class_357.class})
/*    */ public abstract class ScreenScrollCursorMixin
/*    */ {
/*    */   @Inject(method = {"mouseScrolled(DDDD)Z"}, at = {@At("HEAD")})
/*    */   private void phaze$cursorOnScroll(double mouseX, double mouseY, double horizontal, double vertical, CallbackInfoReturnable<Boolean> cir) {
/* 43 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 46 */     CursorManager.notifyScroll(horizontal, vertical);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ScreenScrollCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */