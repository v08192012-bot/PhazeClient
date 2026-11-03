/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
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
/*     */ public final class Cooldowns
/*     */   extends Module
/*     */ {
/*  42 */   private static final Cooldowns INSTANCE = new Cooldowns();
/*     */   
/*  44 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */   
/*  46 */   public final BooleanSetting textShadow = (new BooleanSetting("Text Shadow", "Draw the cooldown number with a vanilla-style drop shadow"))
/*     */ 
/*     */     
/*  49 */     .setValue(true);
/*     */   
/*  51 */   public final BooleanSetting showDecimals = (new BooleanSetting("Show Decimals", "Display the remaining cooldown with one decimal place (e.g. \"1.4\") instead of rounding to whole seconds"))
/*     */ 
/*     */     
/*  54 */     .setValue(true);
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
/*  68 */   public final BooleanSetting colorByCooldown = (new BooleanSetting("Color By Cooldown", "Color the displayed number red/yellow/green based on remaining cooldown progress"))
/*     */ 
/*     */     
/*  71 */     .setValue(false);
/*     */   
/*     */   private Cooldowns() {
/*  74 */     super("cooldowns", "Cooldowns", ModuleCategory.HUD);
/*  75 */     this.textShadow.setFullWidth(true);
/*  76 */     this.showDecimals.setFullWidth(true);
/*  77 */     this.colorByCooldown.setFullWidth(true);
/*  78 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.textShadow, (Setting)this.showDecimals, (Setting)this.colorByCooldown });
/*     */   }
/*     */   
/*     */   public static Cooldowns getInstance() {
/*  82 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  87 */     return "Shows the remaining cooldown time as a number above each hotbar slot";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  92 */     return "cooldowns.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  97 */     return 21.0F;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int colorForProgress(float progress) {
/* 114 */     if (!this.colorByCooldown.isValue()) {
/* 115 */       return -1;
/*     */     }
/* 117 */     if (progress > 0.75F) {
/* 118 */       return -43691;
/*     */     }
/* 120 */     if (progress > 0.25F) {
/* 121 */       return -22016;
/*     */     }
/* 123 */     return -11141291;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String formatSeconds(float seconds) {
/* 134 */     if (this.showDecimals.isValue()) {
/* 135 */       return String.format(Locale.ROOT, "%.1f", new Object[] { Float.valueOf(seconds) });
/*     */     }
/* 137 */     return String.valueOf(Math.max(1, Math.round(seconds)));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\Cooldowns.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */