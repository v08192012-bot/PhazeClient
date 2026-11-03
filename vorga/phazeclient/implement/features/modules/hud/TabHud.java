/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ public final class TabHud extends RectHudModule {
/*  7 */   private static final TabHud INSTANCE = new TabHud();
/*    */   
/*  9 */   public final SectionSetting colorSection2 = new SectionSetting("Tab Colors");
/* 10 */   public final BooleanSetting highlightOwn = (new BooleanSetting("Highlight Own Name", "Highlight own name on tab")).setValue(true);
/* 11 */   public final BooleanSetting showSelfOnTop = (new BooleanSetting("Show Self On Top", "Move your own row to the top of tab list")).setValue(false);
/* 12 */   public final BooleanSetting displayPingAsNumber = (new BooleanSetting("Display Ping Number", "Show ping as number")).setValue(true);
/* 13 */   public final BooleanSetting pingNumberShadow = (new BooleanSetting("Ping Number Shadow", "Shadow for ping number")).setValue(true)
/* 14 */     .visible(() -> Boolean.valueOf(this.displayPingAsNumber.isValue()));
/* 15 */   public final BooleanSetting dynamicPingColor = (new BooleanSetting("Dynamic Ping Color", "Color ping by thresholds")).setValue(true)
/* 16 */     .visible(() -> Boolean.valueOf(this.displayPingAsNumber.isValue()));
/*    */   
/*    */   public static TabHud getInstance() {
/* 19 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private TabHud() {
/* 23 */     super("tab_hud", "Tab", 22.0F, 374.0F, 1.0F);
/*    */     
/* 25 */     settings().removeIf(s -> {
/*    */           String name = s.getNameKey();
/* 27 */           return (name.equals("Background") || name.equals("Background Preset") || name.equals("Color Brightness") || name.equals("Background Opacity") || name.equals("Background Blur Radius") || name.equals("Color Settings") || name.equals("Batching") || name.equals("HUD Batching") || name.equals("Force HUD Update") || name.equals("HUD FPS"));
/*    */         });
/*    */ 
/*    */ 
/*    */     
/* 32 */     this.highlightOwn.setFullWidth(true);
/* 33 */     this.showSelfOnTop.setFullWidth(true);
/* 34 */     this.displayPingAsNumber.setFullWidth(true);
/* 35 */     this.pingNumberShadow.setFullWidth(true);
/* 36 */     this.dynamicPingColor.setFullWidth(true);
/* 37 */     setup(new Setting[] { (Setting)this.colorSection2, (Setting)this.highlightOwn, (Setting)this.showSelfOnTop, (Setting)this.displayPingAsNumber, (Setting)this.pingNumberShadow, (Setting)this.dynamicPingColor });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 42 */     return "Tab overlay settings HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 47 */     return "tab_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 52 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\TabHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */