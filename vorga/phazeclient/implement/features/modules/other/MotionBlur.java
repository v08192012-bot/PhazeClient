/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ import vorga.phazeclient.api.system.motionblur.Shader;
/*    */ 
/*    */ public final class MotionBlur extends Module {
/* 12 */   private static final MotionBlur INSTANCE = new MotionBlur();
/*    */   
/* 14 */   public final SectionSetting mainSection = new SectionSetting("General");
/* 15 */   public final ValueSetting strength = (new ValueSetting("Strength", "Motion blur strength")).range(-2.0F, 2.0F).setValue(-0.8F);
/* 16 */   public final BooleanSetting useRRC = (new BooleanSetting("Refresh Rate Scaling", "Scale strength based on refresh rate")).setValue(true);
/* 17 */   public final ValueSetting quality = (new ValueSetting("Quality", "Quality level (0=Low, 1=Medium, 2=High, 3=Ultra)")).range(0, 3).setValue(2.0F);
/* 18 */   public final ValueSetting handDepthThreshold = (new ValueSetting("Hand Depth Threshold", "Hand depth threshold")).range(0.0F, 1.0F).setValue(0.56F);
/*    */   
/*    */   public final Shader shader;
/*    */   
/*    */   private MotionBlur() {
/* 23 */     super("motion_blur", "Motion Blur", ModuleCategory.OTHER, true, false);
/*    */     
/* 25 */     this.strength.setFullWidth(true);
/* 26 */     this.useRRC.setFullWidth(true);
/* 27 */     this.quality.setFullWidth(true);
/* 28 */     this.handDepthThreshold.setFullWidth(true);
/*    */     
/* 30 */     setup(new Setting[] { (Setting)this.mainSection, (Setting)this.strength, (Setting)this.useRRC, (Setting)this.quality, (Setting)this.handDepthThreshold });
/*    */     
/* 32 */     this.shader = new Shader(this);
/*    */   }
/*    */   
/*    */   public static MotionBlur getInstance() {
/* 36 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 41 */     return "Motion blur effect";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 46 */     return "motion_blur.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 51 */     return 21.0F;
/*    */   }
/*    */   
/*    */   public float getStrength() {
/* 55 */     return this.strength.getValue();
/*    */   }
/*    */   
/*    */   public boolean isUseRRC() {
/* 59 */     return this.useRRC.isValue();
/*    */   }
/*    */   
/*    */   public int getQuality() {
/* 63 */     return this.quality.getInt();
/*    */   }
/*    */   
/*    */   public float getHandDepthThreshold() {
/* 67 */     return this.handDepthThreshold.getValue();
/*    */   }
/*    */   
/*    */   public void setStrength(float value) {
/* 71 */     this.strength.setValue(value);
/*    */   }
/*    */   
/*    */   public void updateBlurStrength(float value) {
/* 75 */     this.strength.setValue(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\MotionBlur.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */