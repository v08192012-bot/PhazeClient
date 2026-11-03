/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1799;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.ShinyPots;
/*    */ 
/*    */ @Mixin({class_1792.class})
/*    */ public abstract class ItemMixin {
/*    */   @Inject(method = {"hasGlint"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$shinyPotsItem(class_1799 stack, CallbackInfoReturnable<Boolean> cir) {
/* 15 */     if (ShinyPots.isPotion(stack))
/* 16 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ItemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */