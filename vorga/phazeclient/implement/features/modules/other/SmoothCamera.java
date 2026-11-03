/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
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
/*    */ public final class SmoothCamera
/*    */   extends Module
/*    */ {
/* 34 */   private static final SmoothCamera INSTANCE = new SmoothCamera();
/*    */   
/* 36 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 37 */   public final ValueSetting smoothness = (new ValueSetting("Smoothness", "Higher = more cinematic glide, lower = closer to raw mouse input"))
/*    */ 
/*    */     
/* 40 */     .range(0.05F, 0.95F).step(0.01F).setValue(0.5F);
/*    */   
/*    */   private SmoothCamera() {
/* 43 */     super("smooth_camera", "Smooth Camera", ModuleCategory.OTHER);
/* 44 */     this.smoothness.setFullWidth(true);
/* 45 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.smoothness });
/*    */   }
/*    */   
/*    */   public static SmoothCamera getInstance() {
/* 49 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 54 */     return "Cinematic-style camera smoothing for mouse movement";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 59 */     return "smooth_camera.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 64 */     return 21.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getSmoothness() {
/* 75 */     return this.smoothness.getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\SmoothCamera.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */