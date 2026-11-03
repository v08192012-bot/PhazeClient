/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1799;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.ShinyPots;
/*    */ 
/*    */ @Mixin({class_1799.class})
/*    */ public abstract class ItemStackMixin {
/*    */   @Inject(method = {"hasGlint"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$shinyPots(CallbackInfoReturnable<Boolean> cir) {
/* 14 */     class_1799 stack = (class_1799)this;
/* 15 */     if (ShinyPots.isPotion(stack))
/* 16 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ItemStackMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */