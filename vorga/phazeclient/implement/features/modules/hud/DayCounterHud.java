/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public final class DayCounterHud extends RectHudModule {
/*  7 */   private static final DayCounterHud INSTANCE = new DayCounterHud();
/*    */   
/*    */   public static DayCounterHud getInstance() {
/* 10 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Show \"Day: 5\" instead of \"5 Days\"")).setValue(false);
/*    */   
/*    */   private DayCounterHud() {
/* 24 */     super("day_counter_hud", "Day Counter", 22.0F, 338.0F, 1.0F);
/* 25 */     this.reverseOrder.setFullWidth(true);
/* 26 */     setup(new Setting[] { (Setting)this.reverseOrder });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 31 */     return "Shows current world day";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 36 */     return "day_counter_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 41 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\DayCounterHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */