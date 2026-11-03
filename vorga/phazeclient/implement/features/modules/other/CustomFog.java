/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
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
/*     */ public final class CustomFog
/*     */   extends Module
/*     */ {
/*  29 */   private static final CustomFog INSTANCE = new CustomFog();
/*     */   
/*  31 */   public final SectionSetting distanceSection = new SectionSetting("Distance");
/*  32 */   public final ValueSetting distance = (new ValueSetting("Distance", "Distance in blocks at which the fog reaches full opacity"))
/*     */ 
/*     */     
/*  35 */     .range(2.0F, 512.0F).step(1.0F).setValue(96.0F);
/*  36 */   public final ValueSetting density = (new ValueSetting("Density", "How much of the distance range is fogged. 0 = thin band at the far plane, 1 = solid wall starting at the camera"))
/*     */ 
/*     */     
/*  39 */     .range(0.0F, 1.0F).step(0.01F).setValue(0.5F);
/*     */   
/*  41 */   public final SectionSetting colorSection = new SectionSetting("Color");
/*  42 */   public final SelectSetting colorMode = (new SelectSetting("Color Mode", "Custom uses the picker below, Theme follows the active GUI palette"))
/*     */ 
/*     */     
/*  45 */     .value(new String[] { "Custom", "Theme" }).selected("Custom");
/*  46 */   public final ColorSetting color = (new ColorSetting("Fog Color", "Custom fog colour. Ignored when Color Mode is set to Theme"))
/*     */ 
/*     */     
/*  49 */     .setColor(-9534521).noAlpha().popupRow();
/*     */   
/*  51 */   public final SectionSetting scopeSection = new SectionSetting("Scope");
/*  52 */   public final BooleanSetting affectSky = (new BooleanSetting("Affect Sky Fog", "Apply the override to the sky-dome fog pass too. Disable to keep the horizon clear and only fog terrain."))
/*     */ 
/*     */     
/*  55 */     .setValue(true);
/*     */   
/*     */   private CustomFog() {
/*  58 */     super("custom_fog", "Custom Fog", ModuleCategory.OTHER);
/*     */     
/*  60 */     this.distance.setFullWidth(true);
/*  61 */     this.density.setFullWidth(true);
/*  62 */     this.colorMode.setFullWidth(true);
/*  63 */     this.color.setFullWidth(true);
/*  64 */     this.affectSky.setFullWidth(true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     this.color.visible(() -> Boolean.valueOf("Custom".equalsIgnoreCase(this.colorMode.getSelected())));
/*     */     
/*  71 */     setup(new Setting[] { (Setting)this.distanceSection, (Setting)this.distance, (Setting)this.density, (Setting)this.colorSection, (Setting)this.colorMode, (Setting)this.color, (Setting)this.scopeSection, (Setting)this.affectSky });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomFog getInstance() {
/*  79 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  84 */     return "Custom fog with adjustable distance, density and color";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  89 */     return "custom_fog.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  94 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public float getDistance() {
/*  98 */     return this.distance.getValue();
/*     */   }
/*     */   
/*     */   public float getDensity() {
/* 102 */     return this.density.getValue();
/*     */   }
/*     */   
/*     */   public boolean isAffectSky() {
/* 106 */     return this.affectSky.isValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getResolvedRgb() {
/* 111 */     if ("Theme".equalsIgnoreCase(this.colorMode.getSelected())) {
/* 112 */       return Theme.getInstance().getCurrentMenuPalette().chipActive() & 0xFFFFFF;
/*     */     }
/* 114 */     return this.color.getColor() & 0xFFFFFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\CustomFog.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */