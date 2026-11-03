/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_1293;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1844;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_9334;
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
/*     */ public final class HealingHelper
/*     */   extends Module
/*     */ {
/*  55 */   private static final HealingHelper INSTANCE = new HealingHelper();
/*     */   
/*     */   private static final int CONSUMABLE_FINISH_TICKS = 32;
/*     */   
/*     */   private static final int FINISH_TOLERANCE = 2;
/*     */   
/*     */   private static final long PULSE_PERIOD_MS = 500L;
/*     */   
/*     */   private static final float MAX_ALPHA = 0.55F;
/*     */   
/*     */   private static final int RGB_GREEN = 3407701;
/*     */   
/*     */   private static final int RGB_YELLOW = 16767040;
/*     */   
/*     */   private static final int RGB_RED = 16724016;
/*     */   private static final int RGB_ORANGE = 16747008;
/*  71 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  72 */   public final ValueSetting hpThreshold = (new ValueSetting("Healing Potion HP", "Highlight healing potions green while your HP is at or below this value (in half-hearts pairs, 1 = half a heart). Turns yellow if the saturation rule below is also flashing."))
/*     */ 
/*     */     
/*  75 */     .range(1, 20).step(1.0F).setValue(14.0F);
/*  76 */   public final ValueSetting gappleCooldownSec = (new ValueSetting("Enchanted Gapple Cooldown", "Number of seconds to suppress the enchanted-gapple flash after you eat one; once this much time has passed it starts flashing again as a re-eat reminder."))
/*     */ 
/*     */     
/*  79 */     .range(1, 120).step(1.0F).setValue(60.0F);
/*  80 */   public final ValueSetting saturationThreshold = (new ValueSetting("Gapple Saturation", "Highlight regular golden apples orange while your saturation is at or below this value (saturation max is 20)."))
/*     */ 
/*     */     
/*  83 */     .range(1, 20).step(1.0F).setValue(6.0F);
/*     */ 
/*     */   
/*  86 */   private int prevEgappleUseTime = -1;
/*     */   
/*  88 */   private long lastEgappleEatenMs = 0L;
/*  89 */   private final Map<class_1799, Integer> preparedColorCache = new IdentityHashMap<>();
/*     */   private boolean snapshotPrepared;
/*     */   private int healingPotionColor;
/*     */   private int enchantedGappleColor;
/*     */   private int regularGappleColor;
/*     */   
/*     */   private HealingHelper() {
/*  96 */     super("healing_helper", "Healing Helper", ModuleCategory.UTILITIES);
/*  97 */     this.hpThreshold.setFullWidth(true);
/*  98 */     this.gappleCooldownSec.setFullWidth(true);
/*  99 */     this.saturationThreshold.setFullWidth(true);
/* 100 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.hpThreshold, (Setting)this.gappleCooldownSec, (Setting)this.saturationThreshold });
/*     */     
/* 102 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*     */   }
/*     */   
/*     */   public static HealingHelper getInstance() {
/* 106 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 111 */     return "Pulses healing potions, golden apples, and enchanted gapples in your inventory based on HP, saturation, and a re-eat timer";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 116 */     return "healing_helper.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 121 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tick(class_310 mc) {
/* 132 */     if (mc == null) {
/*     */       return;
/*     */     }
/* 135 */     class_746 class_746 = mc.field_1724;
/* 136 */     if (class_746 == null) {
/* 137 */       this.prevEgappleUseTime = -1;
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     class_1799 activeItem = class_746.method_6030();
/* 142 */     boolean usingEgappleNow = (class_746.method_6115() && activeItem.method_31574(class_1802.field_8367));
/*     */     
/* 144 */     if (this.prevEgappleUseTime >= 30 && !usingEgappleNow)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 149 */       this.lastEgappleEatenMs = System.currentTimeMillis();
/*     */     }
/*     */     
/* 152 */     this.prevEgappleUseTime = usingEgappleNow ? class_746.method_6048() : -1;
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
/*     */   public int colorForStack(class_1799 stack) {
/* 167 */     prepareSnapshot();
/* 168 */     return resolvePreparedColor(stack);
/*     */   }
/*     */   
/*     */   public void beginRenderPass() {
/* 172 */     prepareSnapshot();
/* 173 */     this.preparedColorCache.clear();
/*     */   }
/*     */   
/*     */   public int colorForPreparedStack(class_1799 stack) {
/* 177 */     if (!this.snapshotPrepared) {
/* 178 */       beginRenderPass();
/*     */     }
/* 180 */     if (!isEnabled() || stack == null || stack.method_7960()) {
/* 181 */       return 0;
/*     */     }
/* 183 */     Integer cached = this.preparedColorCache.get(stack);
/* 184 */     if (cached != null) {
/* 185 */       return cached.intValue();
/*     */     }
/* 187 */     int color = resolvePreparedColor(stack);
/* 188 */     this.preparedColorCache.put(stack, Integer.valueOf(color));
/* 189 */     return color;
/*     */   }
/*     */   
/*     */   private void prepareSnapshot() {
/* 193 */     this.snapshotPrepared = true;
/* 194 */     this.healingPotionColor = 0;
/* 195 */     this.enchantedGappleColor = 0;
/* 196 */     this.regularGappleColor = 0;
/* 197 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/* 200 */     class_310 mc = class_310.method_1551();
/* 201 */     if (mc == null) {
/*     */       return;
/*     */     }
/* 204 */     class_746 class_746 = mc.field_1724;
/* 205 */     if (class_746 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 209 */     float health = class_746.method_6032();
/* 210 */     float saturation = class_746.method_7344().method_7589();
/* 211 */     int hpThr = this.hpThreshold.getInt();
/* 212 */     int satThr = this.saturationThreshold.getInt();
/* 213 */     boolean lowHp = (health <= hpThr);
/* 214 */     boolean lowSat = (saturation <= satThr);
/* 215 */     long now = System.currentTimeMillis();
/* 216 */     float alpha = 0.55F * pulseAlpha(now);
/*     */     
/* 218 */     if (lowHp) {
/* 219 */       this.healingPotionColor = packArgb(lowSat ? 3407701 : 16767040, alpha);
/*     */     }
/* 221 */     long cooldownMs = (long)this.gappleCooldownSec.getValue() * 1000L;
/* 222 */     if (this.lastEgappleEatenMs == 0L || now - this.lastEgappleEatenMs >= cooldownMs) {
/* 223 */       this.enchantedGappleColor = packArgb(16724016, alpha);
/*     */     }
/* 225 */     if (lowSat) {
/* 226 */       this.regularGappleColor = packArgb(16747008, alpha);
/*     */     }
/*     */   }
/*     */   
/*     */   private int resolvePreparedColor(class_1799 stack) {
/* 231 */     if (!isEnabled() || stack == null || stack.method_7960()) {
/* 232 */       return 0;
/*     */     }
/* 234 */     if (isHealingPotion(stack))
/* 235 */       return this.healingPotionColor; 
/* 236 */     if (stack.method_31574(class_1802.field_8367))
/* 237 */       return this.enchantedGappleColor; 
/* 238 */     if (stack.method_31574(class_1802.field_8463)) {
/* 239 */       return this.regularGappleColor;
/*     */     }
/* 241 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float pulseAlpha(long nowMs) {
/* 251 */     long t = nowMs % 500L;
/* 252 */     return (float)Math.sin(Math.PI * t / 500.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int packArgb(int rgb, float alpha) {
/* 257 */     int a = Math.max(0, Math.min(255, Math.round(alpha * 255.0F)));
/* 258 */     return a << 24 | rgb & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isHealingPotion(class_1799 stack) {
/* 268 */     if (!stack.method_31574(class_1802.field_8574) && !stack.method_31574(class_1802.field_8436) && !stack.method_31574(class_1802.field_8150)) {
/* 269 */       return false;
/*     */     }
/* 271 */     class_1844 contents = (class_1844)stack.method_57824(class_9334.field_49651);
/* 272 */     if (contents == null) {
/* 273 */       return false;
/*     */     }
/* 275 */     for (class_1293 effect : contents.method_57397()) {
/* 276 */       if (effect.method_5579().equals(class_1294.field_5915)) {
/* 277 */         return true;
/*     */       }
/*     */     } 
/* 280 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\HealingHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */