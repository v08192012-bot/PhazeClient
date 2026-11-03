/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
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
/*     */ public final class AutoGG
/*     */   extends Module
/*     */ {
/*  48 */   private static final AutoGG INSTANCE = new AutoGG();
/*     */ 
/*     */   
/*     */   private static final long ATTACK_TIMEOUT_MS = 5000L;
/*     */   
/*  53 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */   
/*  55 */   public final SelectSetting message = (new SelectSetting("Message", "Phrase to send in chat after a kill"))
/*     */ 
/*     */     
/*  58 */     .value(new String[] { "gg", "GG" }).selected("GG");
/*     */   
/*  60 */   public final ValueSetting delayMs = (new ValueSetting("Delay (ms)", "Delay before the message is sent after a kill is detected"))
/*     */ 
/*     */     
/*  63 */     .range(0, 3000).step(50.0F).setValue(500.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private final Map<class_1657, Long> recentlyAttacked = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final Map<class_1657, EntityHealthData> trackedEntities = new ConcurrentHashMap<>();
/*     */   
/*  80 */   private long pendingSendAt = 0L;
/*     */   private boolean hasPending = false;
/*     */   
/*     */   private AutoGG() {
/*  84 */     super("auto_gg", "Auto GG", ModuleCategory.OTHER);
/*  85 */     this.message.setFullWidth(true);
/*  86 */     this.delayMs.setFullWidth(true);
/*  87 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.message, (Setting)this.delayMs });
/*     */     
/*  89 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*     */   }
/*     */   
/*     */   public static AutoGG getInstance() {
/*  93 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  98 */     return "Sends 'gg' or 'GG' in chat automatically after killing another player";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 103 */     return "auto_gg.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 108 */     return 28.35F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconOffsetY() {
/* 113 */     return -2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 118 */     this.recentlyAttacked.clear();
/* 119 */     this.trackedEntities.clear();
/* 120 */     this.hasPending = false;
/* 121 */     this.pendingSendAt = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordAttack(class_1657 target) {
/* 132 */     if (!isEnabled() || target == null) {
/*     */       return;
/*     */     }
/* 135 */     this.recentlyAttacked.put(target, Long.valueOf(System.currentTimeMillis()));
/*     */   }
/*     */   
/*     */   private void tick(class_310 mc) {
/* 139 */     long now = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (this.hasPending) {
/* 145 */       if (!isEnabled()) {
/* 146 */         this.hasPending = false;
/* 147 */         this.pendingSendAt = 0L;
/* 148 */       } else if (mc != null && mc.field_1724 != null && mc.method_1562() != null && now >= this.pendingSendAt) {
/*     */         
/* 150 */         String phrase = this.message.getSelected();
/* 151 */         if (phrase == null || phrase.isEmpty()) {
/* 152 */           phrase = "GG";
/*     */         }
/* 154 */         mc.method_1562().method_45729(phrase);
/* 155 */         this.hasPending = false;
/* 156 */         this.pendingSendAt = 0L;
/*     */       } 
/*     */     }
/*     */     
/* 160 */     if (!isEnabled() || mc == null || mc.field_1724 == null || mc.field_1687 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 166 */     this.recentlyAttacked.entrySet().removeIf(entry -> (now - ((Long)entry.getValue()).longValue() > 5000L));
/*     */ 
/*     */ 
/*     */     
/* 170 */     for (class_1657 player : mc.field_1687.method_18456()) {
/* 171 */       if (player == null || mc.field_1724.equals(player)) {
/*     */         continue;
/*     */       }
/*     */       
/* 175 */       float currentHealth = player.method_6032();
/* 176 */       EntityHealthData data = this.trackedEntities.computeIfAbsent(player, k -> new EntityHealthData(currentHealth));
/*     */ 
/*     */       
/* 179 */       boolean weAttacked = this.recentlyAttacked.containsKey(player);
/* 180 */       boolean killTransition = ((data.wasAlive && !player.method_5805()) || (data.previousHealth > 0.0F && currentHealth <= 0.0F));
/*     */ 
/*     */       
/* 183 */       if (killTransition && weAttacked) {
/* 184 */         onKillDetected(player);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 191 */       data.previousHealth = currentHealth;
/* 192 */       data.wasAlive = player.method_5805();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     Iterator<Map.Entry<class_1657, EntityHealthData>> iter = this.trackedEntities.entrySet().iterator();
/* 204 */     while (iter.hasNext()) {
/* 205 */       Map.Entry<class_1657, EntityHealthData> entry = iter.next();
/* 206 */       class_1657 ent = entry.getKey();
/* 207 */       if (ent.method_31481() || !ent.method_5805()) {
/* 208 */         if (this.recentlyAttacked.containsKey(ent)) {
/* 209 */           onKillDetected(ent);
/*     */         }
/* 211 */         iter.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onKillDetected(class_1657 victim) {
/* 217 */     this.recentlyAttacked.remove(victim);
/* 218 */     this.trackedEntities.remove(victim);
/* 219 */     if (!this.hasPending) {
/* 220 */       this.pendingSendAt = System.currentTimeMillis() + Math.max(0L, (long)this.delayMs.getValue());
/* 221 */       this.hasPending = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EntityHealthData
/*     */   {
/*     */     float previousHealth;
/*     */     
/*     */     boolean wasAlive;
/*     */ 
/*     */     
/*     */     EntityHealthData(float currentHealth) {
/* 234 */       this.previousHealth = currentHealth;
/* 235 */       this.wasAlive = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoGG.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */