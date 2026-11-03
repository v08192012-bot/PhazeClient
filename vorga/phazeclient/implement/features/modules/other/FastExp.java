/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_746;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public final class FastExp extends Module {
/* 13 */   private static final FastExp INSTANCE = new FastExp();
/*    */   
/* 15 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 16 */   public final ValueSetting delay = (new ValueSetting("Delay", "Minimum gap (ms) between bottle throws. 50 reproduces the unrestricted Fast-Exp behavior (one throw per game tick); raising it throttles the rate, with 200 matching vanilla's natural 4-tick cooldown."))
/*    */ 
/*    */     
/* 19 */     .range(50, 200).step(10.0F).setValue(50.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   private long lastBypassMs = 0L;
/*    */   
/*    */   private FastExp() {
/* 29 */     super("fast_exp", "Fast Exp", ModuleCategory.UTILITIES);
/* 30 */     this.delay.setFullWidth(true);
/* 31 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.delay });
/*    */   }
/*    */   
/*    */   public static FastExp getInstance() {
/* 35 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 40 */     return "Removes the right-click delay when throwing experience bottles";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 45 */     return "fast_exp.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 50 */     return 21.0F;
/*    */   }
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
/*    */   public static boolean shouldFastThrow() {
/* 69 */     FastExp module = INSTANCE;
/* 70 */     if (module == null || !module.isEnabled()) {
/* 71 */       return false;
/*    */     }
/*    */     
/* 74 */     class_310 client = class_310.method_1551();
/* 75 */     if (client == null) {
/* 76 */       return false;
/*    */     }
/* 78 */     class_746 class_746 = client.field_1724;
/* 79 */     if (class_746 == null) {
/* 80 */       return false;
/*    */     }
/*    */     
/* 83 */     class_1799 mainHand = class_746.method_6047();
/* 84 */     class_1799 offHand = class_746.method_6079();
/*    */     
/* 86 */     boolean holding = ((mainHand != null && mainHand.method_31574(class_1802.field_8287)) || (offHand != null && offHand.method_31574(class_1802.field_8287)));
/* 87 */     if (!holding) {
/* 88 */       return false;
/*    */     }
/*    */     
/* 91 */     long now = System.currentTimeMillis();
/* 92 */     long delayMs = (long)module.delay.getValue();
/* 93 */     if (now - module.lastBypassMs < delayMs) {
/* 94 */       return false;
/*    */     }
/* 96 */     module.lastBypassMs = now;
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\FastExp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */