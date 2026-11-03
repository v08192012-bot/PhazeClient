/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_342;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
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
/*    */ @Mixin({class_342.class})
/*    */ public abstract class TextFieldWidgetMixin
/*    */ {
/*    */   @Shadow
/*    */   private String field_2092;
/*    */   @Unique
/* 58 */   private String phaze$savedText = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderWidget"}, at = {@At("HEAD")})
/*    */   private void phaze$maskPasswordHead(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderWidget"}, at = {@At("TAIL")})
/*    */   private void phaze$maskPasswordTail(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderWidget"}, at = {@At("TAIL")})
/*    */   private void phaze$cursorRequestBeam(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 83 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*    */       return;
/*    */     }
/* 86 */     class_342 self = (class_342)this;
/* 87 */     if (!self.field_22764 || !self.field_22763) {
/*    */       return;
/*    */     }
/* 90 */     int x = self.method_46426();
/* 91 */     int y = self.method_46427();
/* 92 */     int w = self.method_25368();
/* 93 */     int h = self.method_25364();
/* 94 */     if (mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h)
/* 95 */       CursorManager.requestBeam(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\TextFieldWidgetMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */