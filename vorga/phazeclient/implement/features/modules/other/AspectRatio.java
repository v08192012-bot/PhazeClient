/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AspectRatio
/*     */   extends Module
/*     */ {
/*  33 */   private static final AspectRatio INSTANCE = new AspectRatio();
/*     */   
/*  35 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */ 
/*     */   
/*  38 */   public final BooleanSetting usePreset = (new BooleanSetting("Use Preset", "Pick from a list of common aspect ratios instead of a free-form factor"))
/*     */ 
/*     */     
/*  41 */     .setValue(false);
/*     */   
/*  43 */   public final SelectSetting preset = (new SelectSetting("Aspect Preset", "Common cinematic / vanilla aspect ratios"))
/*     */ 
/*     */     
/*  46 */     .value(new String[] { "16:9", "5:4", "4:3", "21:9" }).selected("16:9");
/*     */   
/*  48 */   public final ValueSetting factor = (new ValueSetting("Aspect Factor", "Manual aspect ratio; 1.0 = square, >1 stretches horizontally, <1 squeezes."))
/*     */ 
/*     */     
/*  51 */     .range(0.5F, 4.0F).step(0.01F).setValue(1.0F);
/*     */   
/*     */   private AspectRatio() {
/*  54 */     super("aspect_ratio", "Aspect Ratio", ModuleCategory.OTHER);
/*     */     
/*  56 */     this.usePreset.setFullWidth(true);
/*  57 */     this.preset.setFullWidth(true);
/*  58 */     Objects.requireNonNull(this.usePreset); this.preset.setVisible(this.usePreset::isValue);
/*  59 */     this.factor.setFullWidth(true);
/*  60 */     this.factor.setVisible(() -> Boolean.valueOf(!this.usePreset.isValue()));
/*     */     
/*  62 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.usePreset, (Setting)this.preset, (Setting)this.factor });
/*     */   }
/*     */   
/*     */   public static AspectRatio getInstance() {
/*  66 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRatio() {
/*  77 */     if (this.usePreset.isValue()) {
/*  78 */       switch (this.preset.getSelected()) { case "16:9": case "5:4": case "4:3": case "21:9":  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  83 */         1.7777778F;
/*     */     } 
/*     */     
/*  86 */     return this.factor.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  91 */     return "Override the world projection's aspect ratio with a preset or manual factor";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  96 */     return "aspect_ratio.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 101 */     return 27.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconOffsetY() {
/* 106 */     return -2.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AspectRatio.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */