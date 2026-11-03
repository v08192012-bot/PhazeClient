/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*    */ 
/*    */ public final class CoordinatesHud extends RectHudModule {
/*  7 */   private static final CoordinatesHud INSTANCE = new CoordinatesHud();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   public final MultiSelectSetting displayItems = (new MultiSelectSetting("Display Items", "Pick which coordinate rows the HUD should show"))
/*    */ 
/*    */     
/* 17 */     .value(new String[] {
/*    */ 
/*    */         
/*    */         "Show X", "Show Y", "Show Z", "Show Chunk", "Show Biome", "Show Direction", "Show Axis Signs"
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 25 */       }).selected(new String[] { "Show X", "Show Y", "Show Z", "Show Chunk", "Show Biome", "Show Direction", "Show Axis Signs" });
/*    */   
/*    */   public final BooleanLike showX = () -> this.displayItems.isSelected("Show X");
/*    */   
/*    */   public final BooleanLike showY = () -> this.displayItems.isSelected("Show Y");
/*    */   
/*    */   public final BooleanLike showZ = () -> this.displayItems.isSelected("Show Z");
/*    */   
/*    */   public final BooleanLike showChunk = () -> this.displayItems.isSelected("Show Chunk");
/*    */   
/*    */   public final BooleanLike showBiome = () -> this.displayItems.isSelected("Show Biome");
/*    */   
/*    */   public final BooleanLike showDirection = () -> this.displayItems.isSelected("Show Direction");
/*    */   
/*    */   public final BooleanLike showAxisSigns = () -> this.displayItems.isSelected("Show Axis Signs");
/*    */ 
/*    */   
/*    */   public static CoordinatesHud getInstance() {
/* 43 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private CoordinatesHud() {
/* 47 */     super("coordinates_hud", "Coordinates", 22.0F, 124.0F, 1.0F);
/* 48 */     this.displayItems.setFullWidth(true);
/* 49 */     setup(new Setting[] { (Setting)this.displayItems });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     this.showBrackets.visible(() -> Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 59 */     return "Shows position, chunk, biome, and direction";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 64 */     return "coordinates_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 69 */     return 21.0F;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface BooleanLike {
/*    */     boolean isValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\CoordinatesHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */