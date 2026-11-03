/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
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
/*    */ 
/*    */ 
/*    */ public final class PlayerModelHud
/*    */   extends RectHudModule
/*    */ {
/* 35 */   private static final PlayerModelHud INSTANCE = new PlayerModelHud();
/*    */   
/*    */   private static final float BASE_MODEL_SIZE = 30.0F;
/* 38 */   public final SectionSetting otherSection = new SectionSetting("General");
/* 39 */   public final SelectSetting mode = (new SelectSetting("Mode", "How the model should orient itself"))
/*    */ 
/*    */     
/* 42 */     .value(new String[] { "Follow Mouse", "Auto Rotate", "Static" }).selected("Follow Mouse");
/* 43 */   public final ValueSetting modelSize = (new ValueSetting("Model Size", "Legacy slider kept hidden; size is now changed via HUD edit / chat like every other HUD"))
/*    */ 
/*    */     
/* 46 */     .range(20, 80).step(1.0F).setValue(30.0F).visible(() -> Boolean.valueOf(false));
/* 47 */   public final ValueSetting rotationSpeed = (new ValueSetting("Rotation Speed", "Degrees per second when Auto Rotate is selected"))
/*    */ 
/*    */     
/* 50 */     .range(10, 360).step(5.0F).setValue(60.0F)
/* 51 */     .visible(() -> Boolean.valueOf("Auto Rotate".equalsIgnoreCase(this.mode.getSelected())));
/*    */ 
/*    */   
/*    */   private PlayerModelHud() {
/* 55 */     super("player_model_hud", "Player Model", 22.0F, 22.0F, 1.5F);
/* 56 */     this.background.setVisible(() -> Boolean.valueOf(false));
/* 57 */     this.backgroundPreset.setVisible(() -> Boolean.valueOf(false));
/* 58 */     this.colorBrightness.setVisible(() -> Boolean.valueOf(false));
/* 59 */     this.backgroundOpacity.setVisible(() -> Boolean.valueOf(false));
/* 60 */     this.backgroundBlurRadius.setVisible(() -> Boolean.valueOf(false));
/* 61 */     this.textShadow.setVisible(() -> Boolean.valueOf(false));
/* 62 */     this.showBrackets.setVisible(() -> Boolean.valueOf(false));
/* 63 */     this.mainSection.setVisible(() -> Boolean.valueOf(false));
/* 64 */     this.colorSection.setVisible(() -> Boolean.valueOf(false));
/* 65 */     this.mode.setFullWidth(true);
/* 66 */     this.rotationSpeed.setFullWidth(true);
/* 67 */     setup(new Setting[] { (Setting)this.otherSection, (Setting)this.mode, (Setting)this.modelSize, (Setting)this.rotationSpeed });
/*    */   }
/*    */   
/*    */   public static PlayerModelHud getInstance() {
/* 71 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 76 */     return "Renders a 3D mini-version of your skin in the corner";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 81 */     return "player_model_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 86 */     return 21.0F;
/*    */   }
/*    */   
/*    */   public float getBaseModelSize() {
/* 90 */     return 30.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\PlayerModelHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */