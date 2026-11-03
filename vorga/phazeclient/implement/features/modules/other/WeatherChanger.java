/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public final class WeatherChanger extends Module {
/*  9 */   private static final WeatherChanger INSTANCE = new WeatherChanger();
/* 10 */   private float prevRainGradient = 0.0F;
/* 11 */   private float prevThunderGradient = 0.0F;
/*    */   
/*    */   public static WeatherChanger getInstance() {
/* 14 */     return INSTANCE;
/*    */   }
/*    */   
/* 17 */   public final SelectSetting weatherType = (new SelectSetting("Weather Type", "Select the type of weather"))
/* 18 */     .value(new String[] { "Clear", "Rain", "Thunder", "Snow"
/* 19 */       }).selected("Clear");
/* 20 */   public final ValueSetting rainDensity = new ValueSetting("Rain Density", "Set the density of rain (0-100%)");
/* 21 */   public final ValueSetting thunderStrength = new ValueSetting("Thunder Strength", "Set the strength of thunder (0-100%)");
/* 22 */   public final ValueSetting snowDensity = new ValueSetting("Snow Density", "Set the density of snow (0-100%)");
/*    */   
/*    */   private WeatherChanger() {
/* 25 */     super("weather_changer", "Weather Changer", ModuleCategory.OTHER, true, false);
/* 26 */     this.rainDensity.range(0, 100).setValue(100.0F).visible(() -> Boolean.valueOf(this.weatherType.isSelected("Rain")));
/* 27 */     this.thunderStrength.range(0, 100).setValue(100.0F).visible(() -> Boolean.valueOf(this.weatherType.isSelected("Thunder")));
/* 28 */     this.snowDensity.range(0, 100).setValue(100.0F).visible(() -> Boolean.valueOf(this.weatherType.isSelected("Snow")));
/* 29 */     this.weatherType.setFullWidth(true);
/* 30 */     this.rainDensity.setFullWidth(true);
/* 31 */     this.thunderStrength.setFullWidth(true);
/* 32 */     this.snowDensity.setFullWidth(true);
/* 33 */     setup(new Setting[] { (Setting)this.weatherType, (Setting)this.rainDensity, (Setting)this.thunderStrength, (Setting)this.snowDensity });
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 38 */     this.prevRainGradient = 0.0F;
/* 39 */     this.prevThunderGradient = 0.0F;
/*    */   }
/*    */   
/*    */   public boolean isWeatherOverrideActive() {
/* 43 */     return isEnabled();
/*    */   }
/*    */   
/*    */   public float getRainGradient() {
/* 47 */     if (!isEnabled() || this.weatherType.getSelected().equals("Clear")) {
/* 48 */       this.prevRainGradient = 0.0F;
/* 49 */       return 0.0F;
/*    */     } 
/* 51 */     float target = 1.0F;
/* 52 */     if (this.weatherType.getSelected().equals("Rain")) { target = this.rainDensity.getValue() / 100.0F; }
/* 53 */     else if (this.weatherType.getSelected().equals("Snow")) { target = this.snowDensity.getValue() / 100.0F; }
/* 54 */      this.prevRainGradient = target;
/* 55 */     return target;
/*    */   }
/*    */   
/*    */   public float getThunderGradient() {
/* 59 */     if (!isEnabled() || !this.weatherType.getSelected().equals("Thunder")) {
/* 60 */       this.prevThunderGradient = 0.0F;
/* 61 */       return 0.0F;
/*    */     } 
/* 63 */     float target = this.thunderStrength.getValue() / 100.0F;
/* 64 */     this.prevThunderGradient = target;
/* 65 */     return target;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 70 */     return "Change the weather in the world (client-side, works on any server)";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 75 */     return "weather_changer.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 80 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showIconInSettings() {
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\WeatherChanger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */