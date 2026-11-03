/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_312;
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
/*    */ @Mixin({class_312.class})
/*    */ public abstract class MouseCursorMixin
/*    */ {
/*    */   @Inject(method = {"onMouseScroll"}, at = {@At("HEAD")}, require = 1)
/*    */   private void phaze$cursorOnScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
/* 32 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 35 */     class_310 mc = class_310.method_1551();
/* 36 */     if (mc == null || mc.field_1755 == null) {
/*    */       return;
/*    */     }
/* 39 */     CursorManager.notifyScroll(horizontal, vertical);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"onMouseButton"}, at = {@At("HEAD")})
/*    */   private void phaze$cursorOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
/* 48 */     if (action == 0 && button == 0)
/* 49 */       CursorManager.endDrag(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\MouseCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */