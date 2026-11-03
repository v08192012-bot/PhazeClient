/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ public final class KeystrokesHud extends RectHudModule {
/*  4 */   private static final KeystrokesHud INSTANCE = new KeystrokesHud();
/*    */   
/*    */   public static KeystrokesHud getInstance() {
/*  7 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private KeystrokesHud() {
/* 11 */     super("keystrokes_hud", "Keystrokes", 22.0F, 218.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 16 */     return "Shows movement and mouse buttons on HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 21 */     return "keystrokes_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 26 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\KeystrokesHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */