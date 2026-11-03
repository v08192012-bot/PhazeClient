/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
/*    */ import net.minecraft.class_332;
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
/*    */ @Mixin({ControlElement.class})
/*    */ public abstract class SodiumControlElementCursorMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At("TAIL")}, require = 0)
/*    */   private void phaze$cursorRequestHand(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 37 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 40 */     ControlElement<?> self = (ControlElement)this;
/* 41 */     if (self.method_25405(mouseX, mouseY))
/* 42 */       CursorManager.requestHand(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\SodiumControlElementCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */