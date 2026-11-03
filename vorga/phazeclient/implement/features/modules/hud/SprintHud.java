/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ public final class SprintHud extends RectHudModule {
/*  4 */   private static final SprintHud INSTANCE = new SprintHud();
/*    */   
/*    */   public static SprintHud getInstance() {
/*  7 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private SprintHud() {
/* 11 */     super("sprint_hud", "Sprint HUD", 22.0F, 96.0F, 1.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 17 */     this.showBrackets.visible(() -> Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 22 */     return "Shows current sprint, sneak, and flying state";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 27 */     return "sprint_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 32 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\SprintHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */