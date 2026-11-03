/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_1657;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.implement.features.modules.hud.RectHudModule;
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
/*     */ public final class HealthIndicator
/*     */   extends RectHudModule
/*     */ {
/*  76 */   private static final HealthIndicator INSTANCE = new HealthIndicator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int COLOR_RED = -43691;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int COLOR_GOLD = -22016;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int COLOR_YELLOW = -171;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int COLOR_GREEN = -11141291;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int COLOR_DARK_GREEN = -16733696;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public final ValueSetting targetDelay = (new ValueSetting("Target Delay", "Seconds to keep showing the target's HP after the last hit"))
/*     */ 
/*     */     
/* 112 */     .range(1, 30).setValue(10.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public final BooleanSetting colorByHp = (new BooleanSetting("Color By HP", "Tint the HP value by remaining hearts (red <= 5, gold <= 10, yellow <= 15, green <= 20, dark-green > 20)"))
/*     */ 
/*     */     
/* 124 */     .setValue(true);
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
/* 137 */   public final BooleanSetting hpPrefix = (new BooleanSetting("HP Prefix", "Prefix the value with \"HP\" (e.g. \"HP 10\" instead of just \"10\")"))
/*     */ 
/*     */     
/* 140 */     .setValue(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   private volatile class_1657 target = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   private volatile long lastAttackMs = 0L;
/*     */   
/*     */   public static HealthIndicator getInstance() {
/* 160 */     return INSTANCE;
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
/*     */   private HealthIndicator() {
/* 174 */     super("health_indicator", "Health Indicator", ModuleCategory.OTHER, 320.0F, 200.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     this.background.setValue(false);
/* 180 */     this.targetDelay.setFullWidth(true);
/* 181 */     this.colorByHp.setFullWidth(true);
/* 182 */     this.hpPrefix.setFullWidth(true);
/* 183 */     setup(new Setting[] { (Setting)this.targetDelay, (Setting)this.colorByHp, (Setting)this.hpPrefix });
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
/*     */   
/*     */   public void recordAttack(class_1657 victim) {
/* 201 */     if (victim == null) {
/*     */       return;
/*     */     }
/* 204 */     this.target = victim;
/* 205 */     this.lastAttackMs = System.currentTimeMillis();
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
/*     */   public boolean hasActiveTarget() {
/* 219 */     class_1657 t = this.target;
/* 220 */     if (t == null) {
/* 221 */       return false;
/*     */     }
/* 223 */     if (!t.method_5805()) {
/* 224 */       return false;
/*     */     }
/* 226 */     long windowMs = (long)(this.targetDelay.getValue() * 1000.0F);
/* 227 */     return (System.currentTimeMillis() - this.lastAttackMs < windowMs);
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
/*     */   
/*     */   public String getDisplayText() {
/* 245 */     if (!hasActiveTarget()) {
/* 246 */       return "";
/*     */     }
/* 248 */     class_1657 t = this.target;
/* 249 */     if (t == null) {
/* 250 */       return "";
/*     */     }
/* 252 */     int hp = (int)t.method_6032();
/* 253 */     return this.hpPrefix.isValue() ? ("HP " + hp) : String.valueOf(hp);
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
/*     */   public String getPlaceholderText() {
/* 265 */     return this.hpPrefix.isValue() ? "HP 20" : "20";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHpColor() {
/* 276 */     if (!this.colorByHp.isValue()) {
/* 277 */       return -1;
/*     */     }
/* 279 */     class_1657 t = this.target;
/* 280 */     if (t == null) {
/* 281 */       return -1;
/*     */     }
/* 283 */     float hp = t.method_6032();
/* 284 */     if (hp <= 5.0F) {
/* 285 */       return -43691;
/*     */     }
/* 287 */     if (hp <= 10.0F) {
/* 288 */       return -22016;
/*     */     }
/* 290 */     if (hp <= 15.0F) {
/* 291 */       return -171;
/*     */     }
/* 293 */     if (hp <= 20.0F) {
/* 294 */       return -11141291;
/*     */     }
/* 296 */     return -16733696;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 301 */     return "Shows the HP of the player you most recently hit (port of ZakoHealthIndicator)";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 306 */     return "health_indicator.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 311 */     return 21.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\HealthIndicator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */