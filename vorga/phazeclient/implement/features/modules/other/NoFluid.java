/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
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
/*     */ public final class NoFluid
/*     */   extends Module
/*     */ {
/*  40 */   private static final NoFluid INSTANCE = new NoFluid();
/*     */   
/*     */   public static final String MODE_WATER = "Water";
/*     */   
/*     */   public static final String MODE_LAVA = "Lava";
/*     */   public static final String MODE_BOTH = "Both";
/*  46 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public final SelectSetting mode = (new SelectSetting("Mode", "Which fluid's fog and overlay to remove"))
/*     */ 
/*     */     
/*  59 */     .value(new String[] { "Water", "Lava", "Both" }).selected("Both");
/*     */   
/*     */   public static NoFluid getInstance() {
/*  62 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private NoFluid() {
/*  66 */     super("no_fluid", "No Fluid", ModuleCategory.UTILITIES);
/*  67 */     this.mode.setFullWidth(true);
/*  68 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.mode });
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
/*     */   public boolean shouldHideWater() {
/*  81 */     if (!isEnabled()) return false; 
/*  82 */     String selected = this.mode.getSelected();
/*  83 */     return ("Water".equals(selected) || "Both".equals(selected));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldHideLava() {
/*  92 */     if (!isEnabled()) return false; 
/*  93 */     String selected = this.mode.getSelected();
/*  94 */     return ("Lava".equals(selected) || "Both".equals(selected));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  99 */     return "Removes the fog and screen overlay applied while the camera is submerged in water or lava";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 104 */     return "no_fluid.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 109 */     return 21.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\NoFluid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */