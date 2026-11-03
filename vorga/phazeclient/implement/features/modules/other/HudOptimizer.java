/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ import vorga.phazeclient.api.system.hud.BatchedHudBuffer;
/*    */ import vorga.phazeclient.api.system.shape.implement.Blur;
/*    */ 
/*    */ public final class HudOptimizer extends Module {
/* 11 */   private static final HudOptimizer INSTANCE = new HudOptimizer();
/*    */   
/* 13 */   public final SectionSetting generalSection = new SectionSetting("General");
/*    */ 
/*    */   
/*    */   public final ValueSetting refreshRate;
/*    */ 
/*    */   
/*    */   public final ValueSetting blurBackgroundFps;
/*    */ 
/*    */ 
/*    */   
/*    */   private HudOptimizer() {
/* 24 */     super("hudoptimizer", "HUD Optimizer", ModuleCategory.HUD); this.refreshRate = (new ValueSetting("Refresh Rate", "How many times per second the cached HUD frame is regenerated. Lower = more performance, higher = smoother animations.")).range(10, 120).setValue(30.0F).onChange(value -> BatchedHudBuffer.INSTANCE.setTargetFps(value.intValue())); this.blurBackgroundFps = (new ValueSetting("Blur Background FPS", "How many times per second the world behind blurred HUDs is refreshed.")).range(10, 360).step(1.0F).setValue(120.0F).onChange(value -> Blur.INSTANCE.setHudBackgroundFps(value.intValue()));
/* 25 */     this.refreshRate.setFullWidth(true);
/* 26 */     this.blurBackgroundFps.setFullWidth(true);
/* 27 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.refreshRate, (Setting)this.blurBackgroundFps });
/* 28 */     BatchedHudBuffer.INSTANCE.setTargetFps((int)this.refreshRate.getValue());
/* 29 */     Blur.INSTANCE.setHudBackgroundFps(this.blurBackgroundFps.getInt());
/*    */   }
/*    */   
/*    */   public static HudOptimizer getInstance() {
/* 33 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 38 */     return "Batches all 2D HUD elements into a single FBO and refreshes at a throttled rate";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 43 */     return "hudoptimizer.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 48 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCanBind() {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabledByDefault() {
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 63 */     BatchedHudBuffer.INSTANCE.invalidate();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\HudOptimizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */