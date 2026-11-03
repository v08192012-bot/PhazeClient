/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public final class FpsHud extends RectHudModule {
/*  7 */   private static final FpsHud INSTANCE = new FpsHud();
/*    */   
/*    */   public static FpsHud getInstance() {
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
/*    */ 
/*    */ 
/*    */   
/* 24 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Show value before label, e.g. \"60 FPS\" instead of \"FPS: 60\"")).setValue(false);
/*    */   
/*    */   private FpsHud() {
/* 27 */     super("fps_hud", "FPS");
/* 28 */     this.reverseOrder.setFullWidth(true);
/* 29 */     setup(new Setting[] { (Setting)this.reverseOrder });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 34 */     return "Shows current FPS on HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 39 */     return "fps_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 44 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\FpsHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */