/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import net.minecraft.class_1309;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public final class ComboCounterHud extends RectHudModule {
/*  8 */   private static final ComboCounterHud INSTANCE = new ComboCounterHud();
/*    */   
/*    */   public static ComboCounterHud getInstance() {
/* 11 */     return INSTANCE;
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
/* 22 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Show value before label, e.g. \"3 Combo\" instead of \"Combo 3\"")).setValue(false);
/*    */   
/* 24 */   private int combo = 0;
/* 25 */   private class_1309 lastTarget = null;
/*    */   private boolean wasHit = false;
/*    */   
/*    */   private ComboCounterHud() {
/* 29 */     super("combo_counter_hud", "Combo Counter", 100.0F, 50.0F, 1.0F);
/* 30 */     this.reverseOrder.setFullWidth(true);
/* 31 */     setup(new Setting[] { (Setting)this.reverseOrder });
/*    */   }
/*    */   
/*    */   public void onAttack(class_1309 target) {
/* 35 */     if (this.lastTarget == null || !this.lastTarget.equals(target)) {
/* 36 */       this.combo = 0;
/* 37 */       this.lastTarget = target;
/* 38 */       this.wasHit = false;
/*    */     } 
/* 40 */     this.combo++;
/*    */   }
/*    */   
/*    */   public void onHitByEnemy() {
/* 44 */     this.combo = 0;
/* 45 */     this.lastTarget = null;
/* 46 */     this.wasHit = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onWorldJoin() {
/* 51 */     this.combo = 0;
/* 52 */     this.lastTarget = null;
/* 53 */     this.wasHit = false;
/*    */   }
/*    */   
/*    */   public String getComboText() {
/* 57 */     if (this.combo == 0 && !this.wasHit) {
/* 58 */       return "No Combo";
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 63 */     return this.reverseOrder.isValue() ? ("" + this.combo + " Combo") : ("Combo " + this.combo);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCombo() {
/* 73 */     return this.combo;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate() {
/* 78 */     super.activate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 83 */     super.deactivate();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 88 */     return "Counts combo hits until enemy hits back";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 93 */     return "combo_counter_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 98 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\ComboCounterHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */