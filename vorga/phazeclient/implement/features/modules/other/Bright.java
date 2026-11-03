/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
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
/*    */ public final class Bright
/*    */   extends Module
/*    */ {
/* 23 */   private static final Bright INSTANCE = new Bright();
/*    */   
/* 25 */   public final ValueSetting brightness = (new ValueSetting("Brightness", "Lightmap gamma override; 1.0 = full bright, 0.0 = vanilla feel."))
/*    */ 
/*    */     
/* 28 */     .range(0.0F, 1.0F).setValue(1.0F);
/*    */   
/*    */   private Bright() {
/* 31 */     super("full_bright", "Full Bright", ModuleCategory.OTHER);
/* 32 */     this.brightness.setFullWidth(true);
/* 33 */     setup(new Setting[] { (Setting)this.brightness });
/*    */   }
/*    */   
/*    */   public static Bright getInstance() {
/* 37 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 42 */     return "Forces maximum lightmap brightness so caves and night render at near-day levels";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 47 */     return "bright.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 52 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Bright.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */