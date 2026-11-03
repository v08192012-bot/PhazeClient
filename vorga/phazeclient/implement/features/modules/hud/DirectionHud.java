/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public final class DirectionHud extends RectHudModule {
/*  8 */   private static final DirectionHud INSTANCE = new DirectionHud();
/*    */   
/* 10 */   public final SectionSetting directionSection = new SectionSetting("Direction");
/* 11 */   public final ValueSetting hudLength = (new ValueSetting("HUD Length", "Length of direction strip"))
/* 12 */     .range(180, 420)
/* 13 */     .setValue(320.0F);
/* 14 */   public final BooleanSetting showIntermediate = (new BooleanSetting("Show Intermediate", "Show NE/SE/SW/NW marks")).setValue(true);
/* 15 */   public final BooleanSetting showDegreeNumber = (new BooleanSetting("Show Degree Number", "Show center degree value")).setValue(true);
/* 16 */   public final ValueSetting smoothness = (new ValueSetting("Smoothness", "Compass movement smoothness"))
/* 17 */     .range(1, 20)
/* 18 */     .setValue(10.0F);
/*    */   
/*    */   public static DirectionHud getInstance() {
/* 21 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private DirectionHud() {
/* 25 */     super("direction_hud", "Direction", 22.0F, 340.0F, 1.0F);
/* 26 */     this.hudLength.setFullWidth(true);
/* 27 */     this.showIntermediate.setFullWidth(true);
/* 28 */     this.showDegreeNumber.setFullWidth(true);
/* 29 */     this.smoothness.setFullWidth(true);
/* 30 */     setup(new Setting[] { (Setting)this.directionSection, (Setting)this.hudLength, (Setting)this.showIntermediate, (Setting)this.showDegreeNumber, (Setting)this.smoothness });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 35 */     return "Shows animated cardinal direction strip";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\DirectionHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */