/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_9919;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.menu.MenuUiSettings;
/*    */ 
/*    */ 
/*    */ @Mixin({class_9919.class})
/*    */ public class InactivityFpsLimiterMixin
/*    */ {
/*    */   @Inject(method = {"update"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void phaze$applyCustomGuiFpsLimit(CallbackInfoReturnable<Integer> cir) {
/* 19 */     if (this.field_52731 == null || this.field_52731.method_22683() == null || this.field_52731.method_22683().method_61946()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     if (this.field_52731.field_1687 != null || this.field_52731.field_1755 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 30 */     cir.setReturnValue(Integer.valueOf(MenuUiSettings.getInstance().getGuiFpsLimit()));
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   @Final
/*    */   private class_310 field_52731;
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\InactivityFpsLimiterMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */