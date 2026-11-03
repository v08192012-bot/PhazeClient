/*    */ package vorga.phazeclient.mixins.iris;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"net.irisshaders.iris.gui.element.widget.BooleanElementWidget", "net.irisshaders.iris.gui.element.widget.SliderElementWidget", "net.irisshaders.iris.gui.element.widget.StringElementWidget", "net.irisshaders.iris.gui.element.widget.ProfileElementWidget", "net.irisshaders.iris.gui.element.widget.LinkElementWidget", "net.irisshaders.iris.gui.element.widget.CommentedElementWidget", "net.irisshaders.iris.gui.element.widget.BaseOptionElementWidget"}, remap = false)
/*    */ public abstract class IrisElementWidgetCursorMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At("TAIL")}, require = 0)
/*    */   private void phaze$cursorRequestHand(class_332 context, int mouseX, int mouseY, float delta, boolean hovered, CallbackInfo ci) {
/* 51 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 54 */     if (hovered)
/* 55 */       CursorManager.requestHand(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\iris\IrisElementWidgetCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */