/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5250;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TotemTracker
/*     */   extends Module
/*     */ {
/*  64 */   private static final TotemTracker INSTANCE = new TotemTracker();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte STATUS_USE_TOTEM = 35;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public final ValueSetting radius = (new ValueSetting("Radius", "Maximum distance from you in blocks within which a totem pop is counted"))
/*     */ 
/*     */     
/*  89 */     .range(8, 256).setValue(32.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public final BooleanSetting chatNotify = (new BooleanSetting("Chat Notify", "Print a chat message when a tracked player pops a totem"))
/*     */ 
/*     */     
/* 101 */     .setValue(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public final BooleanSetting nametagSuffix = (new BooleanSetting("Nametag Suffix", "Append \" | -N\" to tracked players' nametags showing how many totems they've lost"))
/*     */ 
/*     */     
/* 111 */     .setValue(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public final ValueSetting resetCooldownSeconds = (new ValueSetting("Reset Cooldown", "Seconds of inactivity after which a player's totem-loss counter resets to 0 (0 = never)"))
/*     */ 
/*     */     
/* 121 */     .range(0, 600).setValue(0.0F);
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
/* 139 */   private final Map<String, Integer> losses = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   private final Map<String, Long> lastUseMs = new ConcurrentHashMap<>();
/*     */   
/*     */   public static TotemTracker getInstance() {
/* 150 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private TotemTracker() {
/* 154 */     super("totem_tracker", "Totem Tracker", ModuleCategory.UTILITIES);
/* 155 */     this.radius.setFullWidth(true);
/* 156 */     this.chatNotify.setFullWidth(true);
/* 157 */     this.nametagSuffix.setFullWidth(true);
/* 158 */     this.resetCooldownSeconds.setFullWidth(true);
/* 159 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.radius, (Setting)this.chatNotify, (Setting)this.nametagSuffix, (Setting)this.resetCooldownSeconds });
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
/*     */   public void recordTotemUse(class_1309 entity) {
/*     */     class_1657 player;
/* 177 */     if (!isEnabled() || entity == null) {
/*     */       return;
/*     */     }
/* 180 */     if (entity instanceof class_1657) { player = (class_1657)entity; }
/*     */     else
/*     */     { return; }
/* 183 */      class_310 client = class_310.method_1551();
/* 184 */     if (client == null || client.field_1724 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 189 */     if (player == client.field_1724) {
/*     */       return;
/*     */     }
/* 192 */     double maxDist = this.radius.getValue();
/* 193 */     double distSq = player.method_5858((class_1297)client.field_1724);
/* 194 */     if (distSq > maxDist * maxDist) {
/*     */       return;
/*     */     }
/*     */     
/* 198 */     String name = player.method_5477().getString();
/* 199 */     if (name == null || name.isEmpty()) {
/*     */       return;
/*     */     }
/* 202 */     int newCount = ((Integer)this.losses.merge(name, Integer.valueOf(1), Integer::sum)).intValue();
/* 203 */     this.lastUseMs.put(name, Long.valueOf(System.currentTimeMillis()));
/*     */     
/* 205 */     if (this.chatNotify.isValue()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       class_5250 line = class_2561.method_43470("[Phaze] ").method_27692(class_124.field_1065).method_10852((class_2561)class_2561.method_43470(name).method_27692(class_124.field_1068)).method_10852((class_2561)class_2561.method_43470(" lost a totem ").method_27692(class_124.field_1080)).method_10852((class_2561)class_2561.method_43470("(-" + newCount + ")").method_27692(class_124.field_1061));
/* 213 */       client.execute(() -> {
/*     */             if (client.field_1705 != null) {
/*     */               client.field_1705.method_1743().method_1812((class_2561)line);
/*     */             }
/*     */           });
/*     */     } 
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
/*     */   public int getLossCount(String name) {
/* 231 */     if (name == null) {
/* 232 */       return 0;
/*     */     }
/* 234 */     return ((Integer)this.losses.getOrDefault(name, Integer.valueOf(0))).intValue();
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
/*     */   
/*     */   public int getLossCountFromText(String displayed) {
/* 253 */     if (displayed == null || displayed.isEmpty() || this.losses.isEmpty()) {
/* 254 */       return 0;
/*     */     }
/* 256 */     for (Map.Entry<String, Integer> e : this.losses.entrySet()) {
/* 257 */       String key = e.getKey();
/* 258 */       if (!key.isEmpty() && 
/* 259 */         displayed.contains(key)) {
/* 260 */         return ((Integer)e.getValue()).intValue();
/*     */       }
/*     */     } 
/* 263 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldDecorateNametag(String name) {
/* 274 */     return (isEnabled() && this.nametagSuffix.isValue() && getLossCount(name) > 0);
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
/*     */   public void pruneStaleEntries() {
/* 290 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/* 293 */     int cooldown = this.resetCooldownSeconds.getInt();
/* 294 */     if (cooldown <= 0) {
/*     */       return;
/*     */     }
/* 297 */     long now = System.currentTimeMillis();
/* 298 */     long windowMs = cooldown * 1000L;
/* 299 */     Iterator<Map.Entry<String, Long>> it = this.lastUseMs.entrySet().iterator();
/* 300 */     while (it.hasNext()) {
/* 301 */       Map.Entry<String, Long> e = it.next();
/* 302 */       if (now - ((Long)e.getValue()).longValue() >= windowMs) {
/* 303 */         this.losses.remove(e.getKey());
/* 304 */         it.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Integer> snapshot() {
/* 315 */     return new HashMap<>(this.losses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetAll() {
/* 323 */     this.losses.clear();
/* 324 */     this.lastUseMs.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 329 */     return "Tracks who pops totems near you - prints to chat and adds \" | -N\" to their nametag";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 334 */     return "totem_tracker.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 339 */     return 21.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\TotemTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */