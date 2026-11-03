/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_638;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.TimeChanger;
/*    */ 
/*    */ @Mixin({class_638.class_5271.class})
/*    */ public class ClientWorldPropertiesMixin
/*    */ {
/*    */   @Inject(at = {@At("TAIL")}, method = {"getTimeOfDay"}, cancellable = true)
/*    */   private void getTimeOfDay(CallbackInfoReturnable<Long> ci) {
/* 15 */     if (TimeChanger.getInstance().isTimeOverrideActive())
/* 16 */       ci.setReturnValue(Long.valueOf((long)(TimeChanger.getInstance()).timeValue.getValue())); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClientWorldPropertiesMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */