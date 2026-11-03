/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public final class CpsHud extends RectHudModule {
/*  7 */   private static final CpsHud INSTANCE = new CpsHud();
/*    */   
/*    */   public static CpsHud getInstance() {
/* 10 */     return INSTANCE;
/*    */   }
/*    */   
/* 13 */   public final BooleanSetting showCpsText = (new BooleanSetting("Show CPS Text", "Show or hide the CPS text")).setValue(true);
/* 14 */   public final BooleanSetting rightClickCps = (new BooleanSetting("Right Click CPS", "Show right click CPS alongside left click")).setValue(false);
/* 15 */   public final BooleanSetting reverseText = (new BooleanSetting("Reverse Order", "Show value before label, e.g. \"5 CPS\" instead of \"CPS: 5\"")).setValue(false);
/*    */   
/*    */   private CpsHud() {
/* 18 */     super("cps_hud", "CPS");
/* 19 */     this.reverseText.setFullWidth(true);
/* 20 */     this.showCpsText.setFullWidth(true);
/* 21 */     this.rightClickCps.setFullWidth(true);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     setup(new Setting[] { (Setting)this.showCpsText, (Setting)this.rightClickCps, (Setting)this.reverseText });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 31 */     return "Shows current clicks per second on HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 36 */     return "cps_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 41 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\CpsHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */