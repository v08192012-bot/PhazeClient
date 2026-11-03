/*    */ package vorga.phazeclient.mixins.sodium;
/*    */ 
/*    */ import net.caffeinemc.mods.sodium.client.gui.widgets.AbstractWidget;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"me.flashyreese.mods.reeses_sodium_options.client.gui.frame.components.SearchTextFieldComponent"}, remap = false)
/*    */ public abstract class ReesesSearchFieldCursorMixin
/*    */ {
/*    */   @Inject(method = {"method_25394"}, at = {@At("TAIL")}, require = 0, remap = false)
/*    */   private void phaze$cursorRequestBeam(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 49 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 55 */     if (((AbstractWidget)this).method_25405(mouseX, mouseY))
/* 56 */       CursorManager.requestBeam(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\sodium\ReesesSearchFieldCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */