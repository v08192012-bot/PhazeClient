/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1937;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.WeatherChanger;
/*    */ 
/*    */ @Mixin({class_1937.class})
/*    */ public abstract class WorldMixin {
/*    */   @Shadow
/*    */   @Final
/*    */   public boolean field_9236;
/*    */   
/*    */   @Inject(method = {"getRainGradient"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void getRainGradient(float delta, CallbackInfoReturnable<Float> cir) {
/* 20 */     if (!this.field_9236) {
/*    */       return;
/*    */     }
/* 23 */     WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 24 */     if (weatherChanger.isWeatherOverrideActive()) {
/* 25 */       String weatherType = weatherChanger.weatherType.getSelected();
/* 26 */       if (weatherType.equals("Clear")) {
/* 27 */         cir.setReturnValue(Float.valueOf(0.0F));
/* 28 */       } else if (weatherType.equals("Rain") || weatherType.equals("Thunder")) {
/* 29 */         cir.setReturnValue(Float.valueOf(weatherChanger.getRainGradient()));
/* 30 */       } else if (weatherType.equals("Snow")) {
/* 31 */         cir.setReturnValue(Float.valueOf(weatherChanger.getRainGradient()));
/*    */       } 
/*    */     } 
/*    */   } @Shadow
/*    */   protected float field_9235; @Shadow
/*    */   protected float field_9234; @Inject(method = {"getThunderGradient"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void getThunderGradient(float delta, CallbackInfoReturnable<Float> cir) {
/* 38 */     if (!this.field_9236) {
/*    */       return;
/*    */     }
/* 41 */     WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 42 */     if (weatherChanger.isWeatherOverrideActive()) {
/* 43 */       if (weatherChanger.weatherType.getSelected().equals("Thunder")) {
/* 44 */         cir.setReturnValue(Float.valueOf(weatherChanger.getThunderGradient()));
/*    */       } else {
/* 46 */         cir.setReturnValue(Float.valueOf(0.0F));
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"isRaining"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void isRaining(CallbackInfoReturnable<Boolean> cir) {
/* 53 */     if (!this.field_9236) {
/*    */       return;
/*    */     }
/* 56 */     WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 57 */     if (weatherChanger.isWeatherOverrideActive()) {
/* 58 */       String weatherType = weatherChanger.weatherType.getSelected();
/* 59 */       cir.setReturnValue(Boolean.valueOf((weatherType.equals("Rain") || weatherType.equals("Thunder") || weatherType.equals("Snow"))));
/* 60 */       cir.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"isThundering"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void isThundering(CallbackInfoReturnable<Boolean> cir) {
/* 66 */     if (!this.field_9236) {
/*    */       return;
/*    */     }
/* 69 */     WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 70 */     if (weatherChanger.isWeatherOverrideActive()) {
/* 71 */       cir.setReturnValue(Boolean.valueOf(weatherChanger.weatherType.getSelected().equals("Thunder")));
/* 72 */       cir.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\WorldMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */