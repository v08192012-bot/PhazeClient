/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AucHelper
/*     */   extends Module
/*     */ {
/*  32 */   private static final AucHelper INSTANCE = new AucHelper();
/*     */   
/*  34 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  35 */   public final BindSetting keybind = new BindSetting("Bind", "Key that triggers /ah search for the item in your main hand");
/*     */   
/*     */   private AucHelper() {
/*  38 */     super("auc_helper", "Auc Helper", ModuleCategory.UTILITIES);
/*  39 */     this.keybind.setFullWidth(true);
/*  40 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.keybind });
/*     */   }
/*     */   
/*     */   public static AucHelper getInstance() {
/*  44 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  49 */     return "Sends /ah search <item in main hand> when the bind is pressed";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  54 */     return "auc_helper.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  59 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindPressed(int key, int action) {
/*  80 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/*  83 */     if (action != 1) {
/*     */       return;
/*     */     }
/*  86 */     int bound = this.keybind.getKey();
/*  87 */     if (bound == -1 || bound != key) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     class_310 mc = class_310.method_1551();
/*  92 */     if (mc == null || mc.field_1724 == null || mc.method_1562() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (mc.field_1755 != null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     class_1799 stack = mc.field_1724.method_6047();
/* 103 */     if (stack == null || stack.method_7960()) {
/* 104 */       mc.field_1724.method_7353((class_2561)class_2561.method_43470("§c[Auc Helper] hand is empty"), true);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     String rawName = stack.method_7964().getString();
/* 114 */     String query = rawName.replaceAll("§.", "").trim();
/* 115 */     if (query.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     mc.method_1562().method_45730("ah search " + query);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AucHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */