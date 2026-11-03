/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import net.minecraft.class_310;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ import vorga.phazeclient.base.util.ServerUtil;
/*    */ 
/*    */ public final class AutoNear extends Module {
/* 10 */   private static final AutoNear INSTANCE = new AutoNear();
/*    */ 
/*    */   
/* 13 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 14 */   public final ValueSetting delaySeconds = (new ValueSetting("Delay", "Delay between /near commands in seconds"))
/* 15 */     .range(10, 300)
/* 16 */     .setValue(60.0F);
/*    */ 
/*    */   
/* 19 */   private long lastCommandMs = 0L;
/*    */   
/*    */   private AutoNear() {
/* 22 */     super("autonear", "Auto Near", ModuleCategory.UTILITIES);
/*    */     
/* 24 */     this.delaySeconds.setFullWidth(true);
/* 25 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.delaySeconds });
/*    */   }
/*    */   
/*    */   public static AutoNear getInstance() {
/* 29 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 34 */     return "Automatically executes /near max command";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 39 */     return "autonear.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 44 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isServerAllowed() {
/* 49 */     class_310 mc = class_310.method_1551();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     if (mc != null && mc.method_1542()) {
/* 55 */       return true;
/*    */     }
/* 57 */     return ServerUtil.isFunTimeServer();
/*    */   }
/*    */   
/*    */   public void tick() {
/* 61 */     class_310 mc = class_310.method_1551();
/*    */     
/* 63 */     if (!isEnabled() || mc.field_1724 == null || mc.method_1562() == null || !isServerAllowed()) {
/*    */       return;
/*    */     }
/*    */     
/* 67 */     long now = System.currentTimeMillis();
/* 68 */     long delayMs = Math.max(10L, this.delaySeconds.getInt()) * 1000L;
/*    */     
/* 70 */     if (now - this.lastCommandMs < delayMs) {
/*    */       return;
/*    */     }
/*    */     
/* 74 */     mc.method_1562().method_45730("near max");
/* 75 */     this.lastCommandMs = now;
/*    */   }
/*    */   
/*    */   protected void onDisable() {
/* 79 */     this.lastCommandMs = 0L;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoNear.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */