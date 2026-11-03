/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({FlatButtonWidget.class})
/*    */ public abstract class SodiumFlatButtonCursorMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At("TAIL")}, require = 0)
/*    */   private void phaze$cursorRequestHand(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 40 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 43 */     FlatButtonWidget self = (FlatButtonWidget)this;
/* 44 */     if (self.method_25405(mouseX, mouseY))
/* 45 */       CursorManager.requestHand(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\SodiumFlatButtonCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */