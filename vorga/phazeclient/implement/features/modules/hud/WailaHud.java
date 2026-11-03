/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ public final class WailaHud extends RectHudModule {
/*  7 */   private static final WailaHud INSTANCE = new WailaHud();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static WailaHud getInstance() {
/* 15 */     return INSTANCE;
/*    */   }
/*    */   
/* 18 */   public final SectionSetting infoSection = new SectionSetting("Info Display");
/* 19 */   public final MultiSelectSetting infoItems = (new MultiSelectSetting("Info Items", "Pick which WAILA info blocks should appear"))
/*    */ 
/*    */     
/* 22 */     .value(new String[] {
/*    */ 
/*    */         
/*    */         "Show Icon", "Show Correct Tool", "Show Coordinates", "Show Break Time", "Always Show", "Show Entities"
/*    */ 
/*    */ 
/*    */       
/* 29 */       }).selected(new String[] { "Show Icon", "Show Correct Tool", "Show Coordinates", "Show Break Time", "Always Show", "Show Entities" });
/*    */   
/*    */   public final BooleanLike showIcon = () -> this.infoItems.isSelected("Show Icon");
/*    */   
/*    */   public final BooleanLike showCorrectTool = () -> this.infoItems.isSelected("Show Correct Tool");
/*    */   
/*    */   public final BooleanLike showCoordinates = () -> this.infoItems.isSelected("Show Coordinates");
/*    */   
/*    */   public final BooleanLike showBreakTime = () -> this.infoItems.isSelected("Show Break Time");
/*    */   
/*    */   public final BooleanLike alwaysShow = () -> this.infoItems.isSelected("Always Show");
/*    */   
/*    */   public final BooleanLike showEntities = () -> this.infoItems.isSelected("Show Entities");
/*    */ 
/*    */   
/*    */   private WailaHud() {
/* 45 */     super("waila_hud", "Waila", 22.0F, 544.0F, 1.0F);
/* 46 */     this.infoItems.setFullWidth(true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 53 */     setup(new Setting[] { (Setting)this.infoSection, (Setting)this.infoItems });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 58 */     return "Shows block/entity info when targeting";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 63 */     return "waila.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 68 */     return 21.0F;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface BooleanLike {
/*    */     boolean isValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\WailaHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */