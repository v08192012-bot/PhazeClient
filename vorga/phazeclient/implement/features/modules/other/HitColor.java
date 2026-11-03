/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.implement.hitcolor.OverlayReloadListener;
/*    */ 
/*    */ public final class HitColor extends Module {
/* 10 */   private static final HitColor INSTANCE = new HitColor();
/*    */   
/*    */   public static HitColor getInstance() {
/* 13 */     return INSTANCE;
/*    */   }
/*    */   
/* 16 */   public final SectionSetting otherSection = new SectionSetting("Other");
/* 17 */   public final BooleanSetting showDamageInArmor = (new BooleanSetting("Show Damage In Armor", "Show hit color on armor")).setValue(true);
/* 18 */   public final BooleanSetting customHitcolor = (new BooleanSetting("Custom Hit Color", "Enable custom hit color"))
/* 19 */     .setValue(true)
/* 20 */     .onChange(value -> OverlayReloadListener.event());
/* 21 */   public final ColorSetting hitcolor = (new ColorSetting("Hit Color", "Color when entity is hit"))
/* 22 */     .value(-65536)
/* 23 */     .popupRow()
/* 24 */     .onChange(value -> OverlayReloadListener.event());
/*    */   
/*    */   private HitColor() {
/* 27 */     super("hitcolor", "HitColor", ModuleCategory.OTHER);
/* 28 */     this.showDamageInArmor.setFullWidth(true);
/* 29 */     this.customHitcolor.setFullWidth(true);
/* 30 */     this.hitcolor.setFullWidth(true);
/* 31 */     setup(new Setting[] { (Setting)this.otherSection, (Setting)this.showDamageInArmor, (Setting)this.customHitcolor, (Setting)this.hitcolor });
/*    */   }
/*    */   
/*    */   public int getHitColor() {
/* 35 */     return this.hitcolor.getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate() {
/* 40 */     OverlayReloadListener.event();
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 45 */     OverlayReloadListener.event();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 50 */     return "Custom hit color for entities";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 55 */     return "hitcolor.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 60 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCanBind() {
/* 65 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\HitColor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */