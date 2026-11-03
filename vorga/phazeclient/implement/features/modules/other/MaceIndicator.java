/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_746;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
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
/*     */ public final class MaceIndicator
/*     */   extends Module
/*     */ {
/*  32 */   private static final MaceIndicator INSTANCE = new MaceIndicator();
/*     */   
/*     */   private static final int RGB_RED = 16724016;
/*     */   
/*     */   private static final int RGB_YELLOW = 16767040;
/*     */   private static final int RGB_GREEN = 3407701;
/*  38 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  39 */   public final ValueSetting opacity = (new ValueSetting("Opacity", "Fill opacity in percent"))
/*     */ 
/*     */     
/*  42 */     .range(0, 100).step(1.0F).setValue(55.0F);
/*     */   
/*     */   private MaceIndicator() {
/*  45 */     super("mace_indicator", "Mace Indicator", ModuleCategory.UTILITIES);
/*  46 */     this.opacity.setFullWidth(true);
/*  47 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.opacity });
/*     */   }
/*     */   
/*     */   public static MaceIndicator getInstance() {
/*  51 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  56 */     return "Tints maces in your inventory red/yellow/green based on attack cooldown progress";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  61 */     return "mace_indicator.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  66 */     return 21.0F;
/*     */   }
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
/*     */   public int colorForStack(class_1799 stack) {
/*     */     int rgb;
/*  81 */     if (!isEnabled() || stack == null || stack.method_7960()) {
/*  82 */       return 0;
/*     */     }
/*  84 */     if (!stack.method_31574(class_1802.field_49814)) {
/*  85 */       return 0;
/*     */     }
/*     */     
/*  88 */     class_310 mc = class_310.method_1551();
/*  89 */     if (mc == null) {
/*  90 */       return 0;
/*     */     }
/*  92 */     class_746 class_746 = mc.field_1724;
/*  93 */     if (class_746 == null) {
/*  94 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     class_1799 held = class_746.method_6047();
/* 103 */     if (held == null || !held.method_31574(class_1802.field_49814)) {
/* 104 */       return 0;
/*     */     }
/*     */     
/* 107 */     float charge = chargeOf((class_1657)class_746);
/*     */     
/* 109 */     if (charge <= 0.3F) {
/* 110 */       rgb = 16724016;
/* 111 */     } else if (charge <= 0.6F) {
/* 112 */       rgb = 16767040;
/*     */     } else {
/* 114 */       rgb = 3407701;
/*     */     } 
/* 116 */     return packArgb(rgb, this.opacity.getInt() / 100.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float chargeOf(class_1657 p) {
/* 127 */     if (p == null) {
/* 128 */       return 0.0F;
/*     */     }
/* 130 */     return Math.min(1.0F, Math.max(0.0F, p.method_7261(0.0F)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int packArgb(int rgb, float alpha) {
/* 135 */     int a = Math.max(0, Math.min(255, Math.round(alpha * 255.0F)));
/* 136 */     return a << 24 | rgb & 0xFFFFFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\MaceIndicator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */