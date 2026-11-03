/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_437;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
/*    */ @Mixin({class_437.class})
/*    */ public abstract class ScreenCursorMixin
/*    */ {
/*    */   @Inject(method = {"renderWithTooltip"}, at = {@At("HEAD")})
/*    */   private void phaze$cursorBeginFrame(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 79 */     CursorManager.beginFrame();
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderWithTooltip"}, at = {@At("TAIL")})
/*    */   private void phaze$cursorEndFrame(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 84 */     CursorManager.endFrame(Animations.getInstance().isDynamicCursorEnabled());
/*    */   }
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
/*    */   @Inject(method = {"removed"}, at = {@At("HEAD")})
/*    */   private void phaze$cursorOnRemoved(CallbackInfo ci) {
/* 98 */     CursorManager.forceArrow();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ScreenCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */