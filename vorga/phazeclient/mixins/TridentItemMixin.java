/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1835;
/*    */ import net.minecraft.class_1937;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.WeatherChanger;
/*    */ 
/*    */ @Mixin({class_1835.class})
/*    */ public class TridentItemMixin
/*    */ {
/*    */   @Inject(method = {"onStoppedUsing"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onStoppedUsing(class_1799 stack, class_1937 world, class_1309 user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
/* 18 */     if (user instanceof net.minecraft.class_1657) {
/* 19 */       WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 20 */       if (weatherChanger.isWeatherOverrideActive()) {
/* 21 */         String weatherType = weatherChanger.weatherType.getSelected();
/* 22 */         if (weatherType.equals("Thunder") || weatherType.equals("Rain"))
/* 23 */           cir.setReturnValue(Boolean.valueOf(false)); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\TridentItemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */