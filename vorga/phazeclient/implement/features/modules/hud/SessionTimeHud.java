/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ 
/*    */ public final class SessionTimeHud extends RectHudModule {
/*  7 */   private static final SessionTimeHud INSTANCE = new SessionTimeHud();
/*    */   
/*  9 */   public final SectionSetting sessionSection = new SectionSetting("Session");
/* 10 */   public final SelectSetting displayOption = (new SelectSetting("Display Option", "Session time format"))
/* 11 */     .value(new String[] { "12h 34m 56s", "123.456s", "12:34:56", "12:34", "12:34:56.789"
/* 12 */       }).selected("12:34:56");
/*    */   
/*    */   public static SessionTimeHud getInstance() {
/* 15 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private SessionTimeHud() {
/* 19 */     super("session_time_hud", "Session Time", 22.0F, 476.0F, 1.0F);
/* 20 */     this.displayOption.setFullWidth(true);
/* 21 */     setup(new Setting[] { (Setting)this.sessionSection, (Setting)this.displayOption });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 26 */     return "session_time.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 31 */     return "Shows current session duration";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 36 */     return 24.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\SessionTimeHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */