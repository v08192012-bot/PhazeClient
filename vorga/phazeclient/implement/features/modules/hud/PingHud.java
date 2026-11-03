/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public final class PingHud
/*    */   extends RectHudModule {
/*  8 */   private static final PingHud INSTANCE = new PingHud();
/*    */   
/*    */   public static PingHud getInstance() {
/* 11 */     return INSTANCE;
/*    */   }
/*    */   
/* 14 */   public final BooleanSetting dynamicPingColor = (new BooleanSetting("Dynamic Ping Color", "Color ping by thresholds")).setValue(true);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 19 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Show value before label, e.g. \"50 ms Ping\" instead of \"Ping: 50 ms\"")).setValue(false);
/*    */   
/* 21 */   private int cachedPing = -1;
/*    */   
/*    */   private PingHud() {
/* 24 */     super("ping_hud", "Ping", 22.0F, 190.0F, 1.0F);
/* 25 */     this.dynamicPingColor.setFullWidth(true);
/* 26 */     this.reverseOrder.setFullWidth(true);
/* 27 */     setup(new Setting[] { (Setting)this.dynamicPingColor, (Setting)this.reverseOrder });
/*    */   }
/*    */   
/*    */   public int getCachedPing() {
/* 31 */     return this.cachedPing;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updatePing(int latency) {
/* 36 */     this.cachedPing = Math.max(0, latency);
/*    */   }
/*    */   
/*    */   public void resetPingCache() {
/* 40 */     this.cachedPing = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 45 */     return "Shows server ping on HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 50 */     return "ping_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 55 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\PingHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */