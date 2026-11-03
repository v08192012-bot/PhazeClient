/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public final class ItemPhysics extends Module {
/* 10 */   private static final ItemPhysics INSTANCE = new ItemPhysics();
/*    */   
/* 12 */   public final SectionSetting mainSection = new SectionSetting("General");
/* 13 */   public final ValueSetting rotationSpeed = (new ValueSetting("Rotation Speed", "Rotation speed in air")).range(0.1F, 5.0F).setValue(1.0F);
/*    */   
/*    */   private ItemPhysics() {
/* 16 */     super("item_physics", "Item Physics", ModuleCategory.OTHER, true, false);
/*    */     
/* 18 */     this.rotationSpeed.setFullWidth(true);
/*    */     
/* 20 */     setup(new Setting[] { (Setting)this.mainSection, (Setting)this.rotationSpeed });
/*    */   }
/*    */   
/*    */   public static ItemPhysics getInstance() {
/* 24 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 29 */     return "Realistic item physics";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 34 */     return "item_physics.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 39 */     return 21.0F;
/*    */   }
/*    */   
/*    */   public float getRotationSpeed() {
/* 43 */     return this.rotationSpeed.getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ItemPhysics.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */