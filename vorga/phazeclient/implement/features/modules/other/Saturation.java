/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ 
/*    */ public final class Saturation extends Module {
/*  8 */   private static final Saturation INSTANCE = new Saturation();
/*    */   
/*    */   public static Saturation getInstance() {
/* 11 */     return INSTANCE;
/*    */   }
/*    */   
/* 14 */   public final SelectSetting style = (new SelectSetting("Style", "Saturation bar style"))
/* 15 */     .value(new String[] { "Yellow Bar", "Second Hunger Bar"
/* 16 */       }).selected("Yellow Bar");
/*    */   
/*    */   private Saturation() {
/* 19 */     super("saturation", "Saturation", ModuleCategory.HUD);
/* 20 */     setup(new Setting[] { (Setting)this.style });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 25 */     return "Shows saturation bar above hunger bar";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 30 */     return "saturation.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 35 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCanBind() {
/* 40 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Saturation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */