/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Zoom
/*     */   extends Module
/*     */ {
/*  20 */   private static final Zoom INSTANCE = new Zoom();
/*     */   
/*     */   private static final float MIN_ZOOM_LEVEL = 2.0F;
/*     */   private static final float SAFE_MAX_ZOOM_LEVEL = 1.0E30F;
/*     */   private static final int SCIENTIFIC_ZOOM_ZERO_THRESHOLD = 15;
/*     */   private static boolean zoomActive = false;
/*  26 */   private static float currentZoomLevel = 7.5F;
/*  27 */   private static float savedZoomLevel = 7.5F;
/*     */   
/*  29 */   public final SectionSetting zoomSection = new SectionSetting("Zoom");
/*  30 */   public final BindSetting keybind = new BindSetting("Keybind", "Key to toggle zoom");
/*  31 */   public final BooleanSetting hold = (new BooleanSetting("Hold", "Hold key to zoom (release to disable)")).setValue(false);
/*  32 */   public final BooleanSetting resumeZoom = (new BooleanSetting("Resume Zoom", "Resume zoom level after disabling")).setValue(false);
/*  33 */   public final ValueSetting defaultZoom = (new ValueSetting("Default Zoom", "Default zoom level")).range(2.0F, 15.0F).step(1.0F).setValue(4.0F);
/*  34 */   public final BooleanSetting cinematicCamera = (new BooleanSetting("Cinematic Camera", "Use cinematic camera when zooming")).setValue(false);
/*  35 */   public final ValueSetting zoomInDuration = (new ValueSetting("Zoom In Duration", "Duration of zoom in animation")).range(0.1F, 2.0F).step(0.1F).setValue(1.5F);
/*  36 */   public final SelectSetting zoomInInterpolation = (new SelectSetting("Zoom In Interpolation", "Easing curve used while zooming in. Default keeps the legacy exponential-approach behaviour."))
/*     */ 
/*     */     
/*  39 */     .value(new String[] { "Default", "Linear", "Smooth", "Fast", "Balanced", "Ease Out"
/*  40 */       }).selected("Default");
/*  41 */   public final ValueSetting zoomOutDuration = (new ValueSetting("Zoom Out Duration", "Duration of zoom out animation")).range(0.1F, 2.0F).step(0.1F).setValue(1.0F);
/*  42 */   public final SelectSetting zoomOutInterpolation = (new SelectSetting("Zoom Out Interpolation", "Easing curve used while zooming out. Default keeps the legacy exponential-approach behaviour."))
/*     */ 
/*     */     
/*  45 */     .value(new String[] { "Default", "Linear", "Smooth", "Fast", "Balanced", "Ease Out"
/*  46 */       }).selected("Default");
/*  47 */   public final ValueSetting zoomScrollMultiplier = (new ValueSetting("Scroll Multiplier", "Zoom scroll multiplier")).range(1.0F, 5.0F).setValue(2.0F);
/*  48 */   public final ValueSetting zoomScrollSensitivity = (new ValueSetting("Scroll Sensitivity", "Zoom scroll sensitivity")).range(0.1F, 5.0F).setValue(1.0F);
/*  49 */   public final BooleanSetting showCurrentZoom = (new BooleanSetting("Show Current Zoom", "Show current zoom level above hotbar")).setValue(false);
/*     */   
/*  51 */   public final SectionSetting limitsSection = new SectionSetting("Limits");
/*  52 */   public final BooleanSetting enableLimits = (new BooleanSetting("Enable Limits", "Enable zoom limits")).setValue(true);
/*  53 */   public final ValueSetting maxZoom = (new ValueSetting("Max Zoom", "Maximum zoom level")).range(10, 5000).setValue(100.0F).visible(() -> Boolean.valueOf(this.enableLimits.isValue()));
/*     */   
/*     */   private Zoom() {
/*  56 */     super("zoom", "Zoom", ModuleCategory.OTHER);
/*     */ 
/*     */     
/*  59 */     this.keybind.setFullWidth(true);
/*  60 */     this.hold.setFullWidth(true);
/*  61 */     this.cinematicCamera.setFullWidth(true);
/*  62 */     this.defaultZoom.setFullWidth(true);
/*  63 */     this.resumeZoom.setFullWidth(true);
/*  64 */     this.zoomInDuration.setFullWidth(true);
/*  65 */     this.zoomInInterpolation.setFullWidth(true);
/*  66 */     this.zoomOutDuration.setFullWidth(true);
/*  67 */     this.zoomOutInterpolation.setFullWidth(true);
/*  68 */     this.zoomScrollMultiplier.setFullWidth(true);
/*  69 */     this.zoomScrollSensitivity.setFullWidth(true);
/*  70 */     this.showCurrentZoom.setFullWidth(true);
/*  71 */     this.enableLimits.setFullWidth(true);
/*  72 */     this.maxZoom.setFullWidth(true);
/*     */     
/*  74 */     setup(new Setting[] { (Setting)this.zoomSection, (Setting)this.keybind, (Setting)this.hold, (Setting)this.cinematicCamera, (Setting)this.defaultZoom, (Setting)this.resumeZoom, (Setting)this.zoomInDuration, (Setting)this.zoomInInterpolation, (Setting)this.zoomOutDuration, (Setting)this.zoomOutInterpolation, (Setting)this.zoomScrollMultiplier, (Setting)this.zoomScrollSensitivity, (Setting)this.showCurrentZoom, (Setting)this.limitsSection, (Setting)this.enableLimits, (Setting)this.maxZoom });
/*     */   }
/*     */   
/*     */   public static Zoom getInstance() {
/*  78 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  83 */     return "Zoom functionality";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  88 */     return "zoom.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  93 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCurrentZoomLevel() {
/* 103 */     currentZoomLevel = sanitizeZoomLevel(currentZoomLevel);
/* 104 */     return currentZoomLevel;
/*     */   }
/*     */   
/*     */   public void setCurrentZoomLevel(float level) {
/* 108 */     currentZoomLevel = sanitizeZoomLevel(level);
/*     */   }
/*     */   
/*     */   public boolean isHold() {
/* 112 */     return this.hold.isValue();
/*     */   }
/*     */   
/*     */   public boolean isCinematicCamera() {
/* 116 */     return this.cinematicCamera.isValue();
/*     */   }
/*     */   
/*     */   public boolean isShowCurrentZoom() {
/* 120 */     return this.showCurrentZoom.isValue();
/*     */   }
/*     */   
/*     */   public float getDefaultZoom() {
/* 124 */     return this.defaultZoom.getValue();
/*     */   }
/*     */   
/*     */   public boolean isResumeZoom() {
/* 128 */     return this.resumeZoom.isValue();
/*     */   }
/*     */   
/*     */   public float getZoomInDuration() {
/* 132 */     return this.zoomInDuration.getValue();
/*     */   }
/*     */   
/*     */   public float getZoomOutDuration() {
/* 136 */     return this.zoomOutDuration.getValue();
/*     */   }
/*     */   
/*     */   public String getZoomInInterpolation() {
/* 140 */     return this.zoomInInterpolation.getSelected();
/*     */   }
/*     */   
/*     */   public String getZoomOutInterpolation() {
/* 144 */     return this.zoomOutInterpolation.getSelected();
/*     */   }
/*     */   
/*     */   public float getZoomScrollMultiplier() {
/* 148 */     return this.zoomScrollMultiplier.getValue();
/*     */   }
/*     */   
/*     */   public float getZoomScrollSensitivity() {
/* 152 */     return this.zoomScrollSensitivity.getValue();
/*     */   }
/*     */   
/*     */   public boolean isEnableLimits() {
/* 156 */     return this.enableLimits.isValue();
/*     */   }
/*     */   
/*     */   public int getMaxZoom() {
/* 160 */     return this.maxZoom.getInt();
/*     */   }
/*     */   
/*     */   public float getSafeMaxZoomLevel() {
/* 164 */     if (!isEnableLimits()) {
/* 165 */       return 1.0E30F;
/*     */     }
/* 167 */     return Math.min(getMaxZoom(), 1.0E30F);
/*     */   }
/*     */   
/*     */   public String getFormattedZoomLevel() {
/* 171 */     float zoom = getCurrentZoomLevel();
/* 172 */     if (!Float.isFinite(zoom)) {
/* 173 */       return "0x";
/*     */     }
/*     */     
/* 176 */     int zeros = (int)Math.max(0.0D, Math.floor(Math.log10(zoom)));
/* 177 */     if (zeros >= 15) {
/* 178 */       return "1e+" + zeros + "x";
/*     */     }
/*     */     
/* 181 */     if (Math.abs(zoom - Math.round(zoom)) < 0.05F) {
/* 182 */       return "" + Math.round(zoom) + "x";
/*     */     }
/*     */     
/* 185 */     return String.format(Locale.ROOT, "%.1fx", new Object[] { Float.valueOf(zoom) });
/*     */   }
/*     */   
/*     */   public static float sanitizeZoomLevel(float level) {
/* 189 */     if (!Float.isFinite(level) || level < 2.0F) {
/* 190 */       return 2.0F;
/*     */     }
/* 192 */     return Math.min(level, 1.0E30F);
/*     */   }
/*     */   
/*     */   public static boolean isZoomActive() {
/* 196 */     return zoomActive;
/*     */   }
/*     */   
/*     */   public static void setZoomActive(boolean active) {
/* 200 */     zoomActive = active;
/*     */ 
/*     */     
/* 203 */     if (!active && getInstance().isResumeZoom()) {
/*     */       
/* 205 */       savedZoomLevel = sanitizeZoomLevel(currentZoomLevel);
/* 206 */     } else if (active && getInstance().isResumeZoom()) {
/*     */       
/* 208 */       currentZoomLevel = sanitizeZoomLevel(savedZoomLevel);
/* 209 */     } else if (active && !getInstance().isResumeZoom()) {
/*     */       
/* 211 */       currentZoomLevel = sanitizeZoomLevel(getInstance().getDefaultZoom());
/* 212 */     } else if (!active) {
/* 213 */       currentZoomLevel = sanitizeZoomLevel(currentZoomLevel);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Zoom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */