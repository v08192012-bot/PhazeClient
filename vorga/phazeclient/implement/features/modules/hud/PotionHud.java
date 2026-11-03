/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
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
/*    */ public final class PotionHud
/*    */   extends RectHudModule
/*    */ {
/* 24 */   private static final PotionHud INSTANCE = new PotionHud();
/*    */   
/* 26 */   public final SectionSetting colorsSection = new SectionSetting("Colors");
/* 27 */   public final BooleanSetting colorByType = (new BooleanSetting("Color By Type", "Tint the effect name by category: green = beneficial, red = harmful, white = neutral"))
/*    */ 
/*    */     
/* 30 */     .setValue(true);
/* 31 */   public final BooleanSetting flashOnExpiry = (new BooleanSetting("Flash On Expiry", "Pulse the effect line when it has less than 10 seconds left"))
/*    */ 
/*    */     
/* 34 */     .setValue(true);
/*    */   
/*    */   public static PotionHud getInstance() {
/* 37 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private PotionHud() {
/* 41 */     super("potion_hud", "Potions", 22.0F, 286.0F, 1.0F);
/* 42 */     this.colorByType.setFullWidth(true);
/* 43 */     this.flashOnExpiry.setFullWidth(true);
/* 44 */     setup(new Setting[] { (Setting)this.colorsSection, (Setting)this.colorByType, (Setting)this.flashOnExpiry });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 49 */     return "Shows active potion effects on HUD with optional buff/debuff color coding";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 54 */     return "potion_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 59 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\PotionHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */