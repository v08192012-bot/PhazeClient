/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_342;
/*    */ import net.minecraft.class_362;
/*    */ import net.minecraft.class_364;
/*    */ import net.minecraft.class_408;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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
/*    */ @Mixin({class_408.class})
/*    */ public abstract class ChatScreenDragSelectMixin
/*    */ {
/*    */   @Shadow
/*    */   protected class_342 field_2382;
/*    */   
/*    */   @Inject(method = {"mouseClicked"}, at = {@At("RETURN")})
/*    */   private void phaze$armChatDrag(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
/* 38 */     if (button != 0)
/* 39 */       return;  if (!Animations.getInstance().isComfortableTextSelectionEnabled())
/* 40 */       return;  if (this.field_2382 == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     int x = this.field_2382.method_46426();
/* 48 */     int y = this.field_2382.method_46427();
/* 49 */     int w = this.field_2382.method_25368();
/* 50 */     int h = this.field_2382.method_25364();
/* 51 */     if (mouseX < x || mouseX >= (x + w) || mouseY < y || mouseY >= (y + h)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     class_362 self = (class_362)this;
/*    */     
/* 63 */     self.method_25395((class_364)this.field_2382);
/* 64 */     self.method_25398(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ChatScreenDragSelectMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */