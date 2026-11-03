/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2338;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.WeatherChanger;
/*    */ 
/*    */ @Mixin({class_1959.class})
/*    */ public class BiomeMixin {
/*    */   @Inject(method = {"getPrecipitation"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void getPrecipitation(class_2338 pos, int seaLevel, CallbackInfoReturnable<class_1959.class_1963> cir) {
/* 15 */     WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 16 */     if (!weatherChanger.isWeatherOverrideActive()) {
/*    */       return;
/*    */     }
/*    */     
/* 20 */     String weatherType = weatherChanger.weatherType.getSelected();
/* 21 */     if (weatherType.equals("Clear")) {
/* 22 */       cir.setReturnValue(class_1959.class_1963.field_9384);
/* 23 */     } else if (weatherType.equals("Snow")) {
/* 24 */       cir.setReturnValue(class_1959.class_1963.field_9383);
/* 25 */     } else if (weatherType.equals("Rain") || weatherType.equals("Thunder")) {
/* 26 */       cir.setReturnValue(class_1959.class_1963.field_9382);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BiomeMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */