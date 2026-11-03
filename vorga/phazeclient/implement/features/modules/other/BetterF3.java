/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
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
/*     */ public final class BetterF3
/*     */   extends Module
/*     */ {
/*  48 */   private static final BetterF3 INSTANCE = new BetterF3();
/*     */   
/*  50 */   public final SectionSetting sectionsSection = new SectionSetting("Sections");
/*  51 */   public final MultiSelectSetting sectionToggles = (new MultiSelectSetting("Sections", "Pick which lines the F3 overlay shows. Click a label to toggle that line on / off."))
/*     */ 
/*     */     
/*  54 */     .value(new String[] {
/*     */         
/*     */         "FPS", "Coordinates", "Facing", "Biome", "Light", "Dimension", "Server", "Memory", "Time", "System"
/*     */       
/*  58 */       }).selected(new String[] { "FPS", "Coordinates", "Facing", "Biome", "Light", "Dimension", "Server", "Memory" });
/*     */ 
/*     */   
/*     */   public final BooleanLike showFps = () -> this.sectionToggles.getSelected().contains("FPS");
/*     */ 
/*     */   
/*     */   public final BooleanLike showFpsBar = () -> false;
/*     */ 
/*     */   
/*     */   public final BooleanLike showCoords = () -> this.sectionToggles.getSelected().contains("Coordinates");
/*     */ 
/*     */   
/*     */   public final BooleanLike showFacing = () -> this.sectionToggles.getSelected().contains("Facing");
/*     */ 
/*     */   
/*     */   public final BooleanLike showBiome = () -> this.sectionToggles.getSelected().contains("Biome");
/*     */ 
/*     */   
/*     */   public final BooleanLike showLight = () -> this.sectionToggles.getSelected().contains("Light");
/*     */ 
/*     */   
/*     */   public final BooleanLike showDimension = () -> this.sectionToggles.getSelected().contains("Dimension");
/*     */ 
/*     */   
/*     */   public final BooleanLike showServer = () -> this.sectionToggles.getSelected().contains("Server");
/*     */ 
/*     */   
/*     */   public final BooleanLike showMemory = () -> this.sectionToggles.getSelected().contains("Memory");
/*     */ 
/*     */   
/*     */   public final BooleanLike showTime = () -> this.sectionToggles.getSelected().contains("Time");
/*     */ 
/*     */   
/*     */   public final BooleanLike showSystem = () -> this.sectionToggles.getSelected().contains("System");
/*     */   
/*     */   public final BooleanLike showTargeted = () -> false;
/*     */   
/*  95 */   public final SectionSetting styleSection = new SectionSetting("Style");
/*  96 */   public final BooleanSetting colorCoding = (new BooleanSetting("Color Coding", "Tint values dynamically (FPS tiers, X/Y/Z axis colors, memory pressure). Off = uniform white"))
/*     */ 
/*     */     
/*  99 */     .setValue(true);
/* 100 */   public final BooleanSetting compactMode = (new BooleanSetting("Compact Mode", "Tighter line spacing for smaller footprint"))
/*     */ 
/*     */     
/* 103 */     .setValue(false);
/* 104 */   public final BooleanSetting textShadow = (new BooleanSetting("Text Shadow", "Render text with vanilla drop-shadow"))
/*     */ 
/*     */     
/* 107 */     .setValue(true);
/*     */   
/*     */   private BetterF3() {
/* 110 */     super("better_f3", "Better F3", ModuleCategory.OTHER);
/* 111 */     this.sectionToggles.setFullWidth(true);
/* 112 */     this.colorCoding.setFullWidth(true);
/* 113 */     this.compactMode.setFullWidth(true);
/* 114 */     this.textShadow.setFullWidth(true);
/* 115 */     setup(new Setting[] { (Setting)this.sectionsSection, (Setting)this.sectionToggles, (Setting)this.styleSection, (Setting)this.colorCoding, (Setting)this.compactMode, (Setting)this.textShadow });
/*     */   }
/*     */ 
/*     */   
/*     */   public static BetterF3 getInstance() {
/* 120 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 125 */     return "Compact custom F3 debug overlay with color-coded values and FPS bar";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 130 */     return "better_f3.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 135 */     return 21.0F;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BooleanLike {
/*     */     boolean isValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\BetterF3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */