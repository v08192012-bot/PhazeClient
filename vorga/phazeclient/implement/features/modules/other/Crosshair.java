/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Crosshair
/*    */   extends Module
/*    */ {
/* 16 */   private static final Crosshair INSTANCE = new Crosshair();
/*    */   
/* 18 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 19 */   public final BooleanSetting showInThirdPerson = (new BooleanSetting("Show In Third Person", "Keep the vanilla crosshair visible while in F5 / third-person view"))
/*    */ 
/*    */     
/* 22 */     .setValue(true);
/*    */   
/*    */   private Crosshair() {
/* 25 */     super("crosshair", "Crosshair", ModuleCategory.OTHER);
/* 26 */     this.showInThirdPerson.setFullWidth(true);
/* 27 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.showInThirdPerson });
/*    */   }
/*    */   
/*    */   public static Crosshair getInstance() {
/* 31 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 36 */     return "Keep the vanilla crosshair visible in third-person (F5) view";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 41 */     return "crosshair.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 46 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Crosshair.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */