/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
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
/*     */ 
/*     */ public final class NoRender
/*     */   extends Module
/*     */ {
/*  48 */   private static final NoRender INSTANCE = new NoRender();
/*     */   
/*  50 */   public final BooleanSetting glowing = (new BooleanSetting("Glowing", "Hide the outline drawn around entities with the minecraft:glowing effect"))
/*     */ 
/*     */     
/*  53 */     .setValue(true);
/*     */   
/*  55 */   public final BooleanSetting fire = (new BooleanSetting("Fire", "Hide the burning fire overlay drawn over the screen while on fire"))
/*     */ 
/*     */     
/*  58 */     .setValue(true);
/*     */   
/*  60 */   public final BooleanSetting lowerShield = (new BooleanSetting("Lower Shield", "Lowers the offhand shield so it does not obstruct your crosshair or view"))
/*     */ 
/*     */     
/*  63 */     .setValue(true);
/*     */   
/*  65 */   public final BooleanSetting particles = (new BooleanSetting("Particles", "Skip every particle the client would spawn (ambient, weather, hit, etc.)"))
/*     */ 
/*     */     
/*  68 */     .setValue(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public final MultiSelectSetting particleTypes = (new MultiSelectSetting("Particle Types", "Pick which particle categories to skip. Click a label to toggle that category on / off."))
/*     */ 
/*     */     
/*  80 */     .value(new String[] {
/*     */         
/*     */         "Hit Particles", "Potion Particles", "Break Block Particles", "Splash Potion Particles", "Food Particles", "Mace Particles", "Scoreboard", "Boss Bar", "Rain"
/*     */       
/*  84 */       }).selected(new String[] { "Hit Particles" });
/*     */ 
/*     */   
/*     */   public final BooleanLike hitParticles = () -> this.particleTypes.getSelected().contains("Hit Particles");
/*     */ 
/*     */   
/*     */   public final BooleanLike potionParticles = () -> this.particleTypes.getSelected().contains("Potion Particles");
/*     */ 
/*     */   
/*     */   public final BooleanLike breakBlockParticles = () -> this.particleTypes.getSelected().contains("Break Block Particles");
/*     */ 
/*     */   
/*     */   public final BooleanLike splashPotionParticles = () -> this.particleTypes.getSelected().contains("Splash Potion Particles");
/*     */ 
/*     */   
/*     */   public final BooleanLike foodParticles = () -> this.particleTypes.getSelected().contains("Food Particles");
/*     */ 
/*     */   
/*     */   public final BooleanLike maceParticles = () -> this.particleTypes.getSelected().contains("Mace Particles");
/*     */ 
/*     */   
/*     */   public final BooleanLike scoreboard = () -> this.particleTypes.getSelected().contains("Scoreboard");
/*     */ 
/*     */   
/*     */   public final BooleanLike bossBar = () -> this.particleTypes.getSelected().contains("Boss Bar");
/*     */ 
/*     */   
/*     */   public final BooleanLike rain = () -> this.particleTypes.getSelected().contains("Rain");
/*     */ 
/*     */ 
/*     */   
/*     */   private NoRender() {
/* 116 */     super("no_render", "Render Tweaks", ModuleCategory.OTHER);
/* 117 */     this.glowing.setFullWidth(true);
/* 118 */     this.fire.setFullWidth(true);
/* 119 */     this.lowerShield.setFullWidth(true);
/* 120 */     this.particles.setFullWidth(true);
/*     */ 
/*     */ 
/*     */     
/* 124 */     this.particleTypes.setFullWidth(true);
/* 125 */     this.particleTypes.visible(() -> Boolean.valueOf(!this.particles.isValue()));
/* 126 */     setup(new Setting[] { (Setting)this.glowing, (Setting)this.fire, (Setting)this.lowerShield, (Setting)this.particles, (Setting)this.particleTypes });
/*     */   }
/*     */   
/*     */   public static NoRender getInstance() {
/* 130 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 135 */     return "Render-pipeline tweaks: hide glowing, fire, particles, scoreboard, boss bar and rain";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 140 */     return "no_render.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 145 */     return 21.0F;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BooleanLike {
/*     */     boolean isValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\NoRender.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */