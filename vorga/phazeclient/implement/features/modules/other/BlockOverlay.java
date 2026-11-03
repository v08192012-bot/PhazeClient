/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BlockOverlay
/*    */   extends Module
/*    */ {
/* 32 */   private static final BlockOverlay INSTANCE = new BlockOverlay();
/*    */   
/* 34 */   public final SectionSetting outlineSection = new SectionSetting("Outline");
/* 35 */   public final ColorSetting outlineColor = (new ColorSetting("Outline Color", "Color of the outline (alpha controls opacity)"))
/*    */ 
/*    */     
/* 38 */     .value(-16777216)
/* 39 */     .popupRow();
/* 40 */   public final SelectSetting style = (new SelectSetting("Style", "Outline only or with a translucent face fill"))
/*    */ 
/*    */     
/* 43 */     .value(new String[] { "Outline", "Filled" }).selected("Outline");
/* 44 */   public final ColorSetting fillColor = (new ColorSetting("Fill Color", "Color of the translucent face fill (Filled style only)"))
/*    */ 
/*    */     
/* 47 */     .value(1090519039)
/* 48 */     .popupRow()
/* 49 */     .visible(() -> Boolean.valueOf("Filled".equalsIgnoreCase(this.style.getSelected())));
/*    */   
/*    */   private BlockOverlay() {
/* 52 */     super("block_overlay", "Block Overlay", ModuleCategory.OTHER);
/* 53 */     this.outlineColor.setFullWidth(true);
/* 54 */     this.style.setFullWidth(true);
/* 55 */     this.fillColor.setFullWidth(true);
/* 56 */     setup(new Setting[] { (Setting)this.outlineSection, (Setting)this.outlineColor, (Setting)this.style, (Setting)this.fillColor });
/*    */   }
/*    */   
/*    */   public static BlockOverlay getInstance() {
/* 60 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 65 */     return "Recolor the vanilla block outline at your crosshair, optionally fill the faces";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 70 */     return "block_overlay.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 75 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\BlockOverlay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */