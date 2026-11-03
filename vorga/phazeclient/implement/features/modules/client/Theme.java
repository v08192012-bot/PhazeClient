/*     */ package vorga.phazeclient.implement.features.modules.client;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.color.ColorPalette;
/*     */ import vorga.phazeclient.base.util.color.ThemeColorPalette;
/*     */ import vorga.phazeclient.implement.menu.MenuPalette;
/*     */ import vorga.phazeclient.implement.menu.MenuPalettes;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.MenuUiSettings;
/*     */ 
/*     */ public final class Theme extends Module {
/*  18 */   private static final Theme INSTANCE = new Theme();
/*     */   
/*     */   public static Theme getInstance() {
/*  21 */     return INSTANCE;
/*     */   }
/*     */   
/*  24 */   private ColorPalette currentPalette = (ColorPalette)new ThemeColorPalette(MenuPalettes.LUNAR_BLUE);
/*     */   private boolean guiScaleAdjustmentActive;
/*  26 */   private float pendingGuiScale = 1.0F;
/*     */   
/*  28 */   public final SelectSetting menuTheme = (new SelectSetting("Theme", "Menu & HUD theme preset"))
/*  29 */     .value(new String[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         "Lunar Blue", "Mocha Gold", "Rose Quartz", "Emerald Frost", "Arctic Mint", "Crimson Silk", "Solar Ember", "Midnight Bloom", "Desert Mirage", "Sapphire Steel",
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         "Velvet Plum", "Frosted Peach", "Moss Smoke", "Polar Night", "Snow", "Obsidian", "Nebula", "Coral", "Jade", "Sunset",
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         "Violet", "Ocean"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  53 */       }).selected("Lunar Blue");
/*     */   
/*  55 */   public final ValueSetting blurRadius = (new ValueSetting("Blur Radius", "Adjust background blur strength"))
/*  56 */     .range(0, 32)
/*  57 */     .setValue(16.0F);
/*     */   
/*  59 */   public final ValueSetting guiScale = (new ValueSetting("GUI Scale", "Scale the entire Phaze GUI"))
/*  60 */     .range(0.9F, 2.5F)
/*  61 */     .step(0.1F)
/*  62 */     .setValue(1.0F)
/*  63 */     .onChange(this::applyGuiScale);
/*     */   
/*  65 */   public final ColorSetting hudTextColor = (new ColorSetting("Hud Text Color", "Default color for HUD text that does not use its own dynamic tint"))
/*     */ 
/*     */ 
/*     */     
/*  69 */     .value(-1)
/*  70 */     .noAlpha()
/*  71 */     .popupRow();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public final SelectSetting language = (new SelectSetting("Language", "UI language for menu strings"))
/*  82 */     .value(new String[] { "English", "Русский"
/*  83 */       }).selected("English");
/*     */   
/*  85 */   public final BooleanSetting renderOtherPlayerCosmetics = (new BooleanSetting("Render Other Player Cosmetics", "Render Phaze cosmetics equipped by other players"))
/*     */ 
/*     */     
/*  88 */     .setValue(true);
/*     */   
/*     */   private Theme() {
/*  91 */     super("themes", "Themes", ModuleCategory.OTHER, false, false);
/*  92 */     this.hudTextColor.setFullWidth(true);
/*  93 */     this.language.setFullWidth(true);
/*  94 */     this.guiScale.setFullWidth(true);
/*  95 */     this.renderOtherPlayerCosmetics.setFullWidth(true);
/*  96 */     setup(new Setting[] { (Setting)this.menuTheme, (Setting)this.blurRadius, (Setting)this.language, (Setting)this.hudTextColor, (Setting)this.guiScale, (Setting)this.renderOtherPlayerCosmetics });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     Lang.setActive(this.language.getSelected());
/*     */     
/* 104 */     applyTheme();
/* 105 */     applyMenuTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void syncLanguage() {
/* 115 */     Lang.setActive(this.language.getSelected());
/*     */   }
/*     */   
/*     */   public float getFadeSpeed() {
/* 119 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public int[] getClientColors() {
/* 123 */     MenuPalette palette = getCurrentMenuPalette();
/* 124 */     return new int[] { palette.chipActive(), palette.chipNew(), palette.accentGreen() };
/*     */   }
/*     */   
/*     */   public ColorPalette getCurrentPalette() {
/* 128 */     applyTheme();
/* 129 */     return this.currentPalette;
/*     */   }
/*     */   
/*     */   public MenuPalette getCurrentMenuPalette() {
/* 133 */     return MenuPalettes.byName(this.menuTheme.getSelected());
/*     */   }
/*     */   
/*     */   public float getMenuBlurRadius() {
/* 137 */     float value = this.blurRadius.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     return (float)(Math.log1p(value) * 1.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHudBlurQualityMultiplier() {
/* 149 */     return 0.5053F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHudBlurMode() {
/* 154 */     return 2;
/*     */   }
/*     */   
/*     */   public float getHudBlurRadiusMultiplier() {
/* 158 */     return 2.5F;
/*     */   }
/*     */   
/*     */   public int getHudTextColor() {
/* 162 */     return 0xFF000000 | this.hudTextColor.getColor() & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   private void applyGuiScale(float value) {
/* 166 */     float clamped = Math.max(0.9F, Math.min(2.5F, value));
/* 167 */     if (Float.compare(value, clamped) != 0) {
/*     */ 
/*     */ 
/*     */       
/* 171 */       this.guiScale.setValue(clamped);
/*     */       return;
/*     */     } 
/* 174 */     this.pendingGuiScale = clamped;
/* 175 */     if (this.guiScaleAdjustmentActive) {
/*     */       return;
/*     */     }
/* 178 */     MenuUiSettings.getInstance().setGuiScale(clamped);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginGuiScaleAdjustment() {
/* 187 */     this.guiScaleAdjustmentActive = true;
/* 188 */     this.pendingGuiScale = this.guiScale.getValue();
/*     */   }
/*     */   
/*     */   public void endGuiScaleAdjustment() {
/* 192 */     if (!this.guiScaleAdjustmentActive)
/* 193 */       return;  this.guiScaleAdjustmentActive = false;
/* 194 */     applyGuiScale(this.pendingGuiScale);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 199 */     return "Custom themes for GUI";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showIconInSettings() {
/* 209 */     return false;
/*     */   }
/*     */   
/*     */   public void applyMenuTheme() {
/* 213 */     MenuStyle.apply(getCurrentMenuPalette());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 221 */     this.currentPalette = (ColorPalette)new ThemeColorPalette(getCurrentMenuPalette());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\client\Theme.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */