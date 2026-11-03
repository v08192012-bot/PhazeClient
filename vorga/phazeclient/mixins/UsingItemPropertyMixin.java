/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10468;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_638;
/*    */ import net.minecraft.class_811;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
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
/*    */ @Mixin({class_10468.class})
/*    */ public abstract class UsingItemPropertyMixin
/*    */ {
/*    */   @Inject(method = {"getValue"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$suppressShieldBlockModel(class_1799 stack, class_638 world, class_1309 entity, int seed, class_811 mode, CallbackInfoReturnable<Boolean> cir) {
/* 32 */     if (stack.method_7909() instanceof net.minecraft.class_1819 && entity instanceof class_1657) { class_1657 player = (class_1657)entity;
/* 33 */       if (OldAnimations.isSwordBlockingActive(player))
/* 34 */         cir.setReturnValue(Boolean.valueOf(false));  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\UsingItemPropertyMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */