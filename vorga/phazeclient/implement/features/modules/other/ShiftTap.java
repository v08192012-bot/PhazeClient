/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ 
/*     */ public final class ShiftTap extends Module {
/*  10 */   private static final ShiftTap INSTANCE = new ShiftTap();
/*     */   
/*     */   private static final int MIN_DURATION = 10;
/*     */   
/*     */   private static final int MAX_DURATION = 200;
/*  15 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  16 */   public final ValueSetting sneakDuration = (new ValueSetting("Sneak Duration", "Duration of sneak in milliseconds"))
/*  17 */     .range(10, 200)
/*  18 */     .setValue(50.0F);
/*     */ 
/*     */   
/*  21 */   private long shiftTapEndTime = 0L;
/*     */   private boolean moduleControllingSneak = false;
/*     */   
/*     */   private ShiftTap() {
/*  25 */     super("shifttap", "Shift Tap", ModuleCategory.UTILITIES);
/*     */     
/*  27 */     this.sneakDuration.setFullWidth(true);
/*  28 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.sneakDuration });
/*     */   }
/*     */   
/*     */   public static ShiftTap getInstance() {
/*  32 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  37 */     return "Automatically taps shift for short duration";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  42 */     return "shifttap.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  47 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServerAllowed() {
/*  52 */     return ServerUtil.isShiftTapSupported();
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  56 */     class_310 mc = class_310.method_1551();
/*     */     
/*  58 */     if (!isEnabled()) {
/*  59 */       stopShiftTap();
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     if (mc.field_1724 == null || mc.field_1724.method_7325() || !isWorldSupported()) {
/*  64 */       stopShiftTap();
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     if (this.moduleControllingSneak && System.currentTimeMillis() >= this.shiftTapEndTime) {
/*  69 */       stopShiftTap();
/*     */     }
/*     */   }
/*     */   
/*     */   public void triggerShiftTap() {
/*  74 */     class_310 mc = class_310.method_1551();
/*     */     
/*  76 */     if (!isEnabled() || mc.field_1724 == null || !isWorldSupported()) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     this.shiftTapEndTime = System.currentTimeMillis() + this.sneakDuration.getInt();
/*  81 */     if (!this.moduleControllingSneak) {
/*  82 */       mc.field_1690.field_1832.method_23481(true);
/*  83 */       this.moduleControllingSneak = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopShiftTap() {
/*  88 */     class_310 mc = class_310.method_1551();
/*     */     
/*  90 */     if (!this.moduleControllingSneak) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     mc.field_1690.field_1832.method_23481(false);
/*  95 */     this.moduleControllingSneak = false;
/*  96 */     this.shiftTapEndTime = 0L;
/*     */   }
/*     */   
/*     */   private boolean isWorldSupported() {
/* 100 */     return ServerUtil.isShiftTapSupported();
/*     */   }
/*     */   
/*     */   protected void onDisable() {
/* 104 */     stopShiftTap();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ShiftTap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */