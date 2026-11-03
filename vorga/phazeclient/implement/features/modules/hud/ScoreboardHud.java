/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public final class ScoreboardHud
/*    */   extends RectHudModule {
/*  8 */   private static final ScoreboardHud INSTANCE = new ScoreboardHud();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ScoreboardHud getInstance() {
/* 16 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean positionInitialized = false;
/*    */   public final BooleanLike showNumbers = () -> true;
/*    */   public final BooleanLike showZeros = () -> true;
/* 23 */   public final BooleanSetting showTitle = (new BooleanSetting("Show Title", "Show scoreboard title")).setValue(true);
/*    */ 
/*    */   
/*    */   private ScoreboardHud() {
/* 27 */     super("scoreboard_hud", "Scoreboard", 0.0F, 0.0F, 1.0F);
/* 28 */     this.showTitle.setFullWidth(true);
/* 29 */     setup(new Setting[] { (Setting)this.showTitle });
/*    */   }
/*    */   
/*    */   public boolean isPositionInitialized() {
/* 33 */     return this.positionInitialized;
/*    */   }
/*    */   
/*    */   public void setPositionInitialized(boolean positionInitialized) {
/* 37 */     this.positionInitialized = positionInitialized;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHudPosition(float x, float y, int refWidth, int refHeight) {
/* 42 */     super.setHudPosition(x, y, refWidth, refHeight);
/* 43 */     this.positionInitialized = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHudX(float hudX) {
/* 48 */     super.setHudX(hudX);
/* 49 */     this.positionInitialized = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHudY(float hudY) {
/* 54 */     super.setHudY(hudY);
/* 55 */     this.positionInitialized = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHudXRatio(float ratio) {
/* 60 */     super.setHudXRatio(ratio);
/* 61 */     this.positionInitialized = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHudYRatio(float ratio) {
/* 66 */     super.setHudYRatio(ratio);
/* 67 */     this.positionInitialized = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void resetHudTransform() {
/* 72 */     super.resetHudTransform();
/* 73 */     this.positionInitialized = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 78 */     return "Modify vanilla scoreboard with blur, custom colors, position, and scale";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 83 */     return "scoreboard_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 88 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMinHudScale() {
/* 93 */     return 0.5F;
/*    */   }
/*    */   
/*    */   public boolean shouldUseVanillaColors() {
/* 97 */     return (this.background.isValue() && isVanillaPreset());
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface BooleanLike {
/*    */     boolean isValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\ScoreboardHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */