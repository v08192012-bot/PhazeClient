/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_1144;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3414;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_5250;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public final class ArmorNotifier extends Module {
/*  26 */   private static final ArmorNotifier INSTANCE = new ArmorNotifier();
/*     */   
/*     */   private static final long ALERT_COOLDOWN_MS = 4000L;
/*  29 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  30 */   public final ValueSetting threshold = (new ValueSetting("Threshold (%)", "Play an alert sound when any armor piece drops below this durability percentage"))
/*     */ 
/*     */     
/*  33 */     .range(1, 50).setValue(5.0F);
/*  34 */   public final BooleanSetting chatNotify = (new BooleanSetting("Show in Chat", "Also print '[Phaze] <piece> almost broke!' to chat (English) when an armor piece crosses the threshold"))
/*     */ 
/*     */     
/*  37 */     .setValue(false);
/*  38 */   public final BooleanSetting repeat = (new BooleanSetting("Repeat", "Replay the alert (sound and chat line if enabled) several more times after the initial trigger"))
/*     */ 
/*     */     
/*  41 */     .setValue(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ValueSetting repetitions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ValueSetting repeatDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<class_1304, Boolean> wasBelow;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<class_1304, Integer> lastRemaining;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<class_1304, String> lastName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastAlertMs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int pendingRepeats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long nextRepeatMs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<class_1304> pendingSlots;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArmorNotifier() {
/* 109 */     super("armor_notifier", "Armor Notifier", ModuleCategory.UTILITIES); Objects.requireNonNull(this.repeat); this.repetitions = (new ValueSetting("Number of Repetitions", "How many extra times the alert is replayed after the initial trigger")).range(2, 10).setValue(3.0F).visible(this.repeat::isValue); Objects.requireNonNull(this.repeat); this.repeatDelay = (new ValueSetting("Repeat Delay", "Seconds between successive repeated alerts")).range(3, 30).setValue(5.0F).visible(this.repeat::isValue); this.wasBelow = new EnumMap<>(class_1304.class); this.lastRemaining = new EnumMap<>(class_1304.class); this.lastName = new EnumMap<>(class_1304.class); this.lastAlertMs = 0L; this.pendingRepeats = 0; this.nextRepeatMs = 0L; this.pendingSlots = new ArrayList<>(4);
/* 110 */     this.threshold.setFullWidth(true);
/* 111 */     this.chatNotify.setFullWidth(true);
/* 112 */     this.repeat.setFullWidth(true);
/* 113 */     this.repetitions.setFullWidth(true);
/* 114 */     this.repeatDelay.setFullWidth(true);
/* 115 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.threshold, (Setting)this.chatNotify, (Setting)this.repeat, (Setting)this.repetitions, (Setting)this.repeatDelay });
/*     */     
/* 117 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*     */   }
/*     */   
/*     */   public static ArmorNotifier getInstance() {
/* 121 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 126 */     return "Plays a sound when any armor piece drops below a durability threshold";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 131 */     return "armor_notifier.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 136 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 141 */     this.wasBelow.clear();
/* 142 */     this.lastRemaining.clear();
/* 143 */     this.lastName.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     this.pendingRepeats = 0;
/* 149 */     this.pendingSlots.clear();
/*     */   }
/*     */   
/*     */   private void tick(class_310 mc) {
/* 153 */     if (!isEnabled() || mc == null || mc.field_1724 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (mc.method_1493()) {
/*     */       return;
/*     */     }
/*     */     
/* 167 */     long now = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     float pct = this.threshold.getValue() / 100.0F;
/* 175 */     List<class_1304> newSlots = null;
/* 176 */     List<class_1304> brokenSlots = null;
/*     */     
/* 178 */     for (class_1304 slot : class_1304.values()) {
/*     */       
/* 180 */       if (slot.method_5925() == class_1304.class_1305.field_6178) {
/*     */ 
/*     */         
/* 183 */         class_1799 stack = mc.field_1724.method_6118(slot);
/* 184 */         int prevRem = ((Integer)this.lastRemaining.getOrDefault(slot, Integer.valueOf(-1))).intValue();
/* 185 */         boolean wasBelowFlag = ((Boolean)this.wasBelow.getOrDefault(slot, Boolean.valueOf(false))).booleanValue();
/* 186 */         boolean present = (stack != null && !stack.method_7960() && stack.method_7936() > 0);
/*     */         
/* 188 */         if (present) {
/* 189 */           int max = stack.method_7936();
/* 190 */           int rem = max - stack.method_7919();
/* 191 */           float fraction = rem / max;
/* 192 */           boolean below = (fraction <= pct);
/* 193 */           if (below && !wasBelowFlag) {
/* 194 */             if (newSlots == null) {
/* 195 */               newSlots = new ArrayList<>(4);
/*     */             }
/* 197 */             newSlots.add(slot);
/*     */           } 
/* 199 */           this.wasBelow.put(slot, Boolean.valueOf(below));
/* 200 */           this.lastRemaining.put(slot, Integer.valueOf(rem));
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 205 */           this.lastName.put(slot, stack.method_7964().getString());
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 211 */           if (prevRem > 0 && wasBelowFlag) {
/* 212 */             if (brokenSlots == null) {
/* 213 */               brokenSlots = new ArrayList<>(4);
/*     */             }
/* 215 */             brokenSlots.add(slot);
/*     */           } 
/* 217 */           this.wasBelow.put(slot, Boolean.valueOf(false));
/* 218 */           this.lastRemaining.put(slot, Integer.valueOf(-1));
/*     */         } 
/*     */       } 
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
/* 231 */     if (brokenSlots != null) {
/* 232 */       playBreakSound(mc);
/* 233 */       if (this.chatNotify.isValue()) {
/* 234 */         for (class_1304 slot : brokenSlots) {
/* 235 */           String name = this.lastName.getOrDefault(slot, fallbackName(slot));
/* 236 */           sendBrokeMessage(mc, name);
/*     */         } 
/*     */       }
/* 239 */       this.pendingSlots.removeAll(brokenSlots);
/* 240 */       if (this.pendingSlots.isEmpty()) {
/* 241 */         this.pendingRepeats = 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     if (this.pendingRepeats > 0) {
/* 251 */       this.pendingSlots.removeIf(s -> {
/*     */             class_1799 st = mc.field_1724.method_6118(s);
/* 253 */             return (st == null || st.method_7960() || st.method_7936() <= 0);
/*     */           });
/* 255 */       if (this.pendingSlots.isEmpty()) {
/* 256 */         this.pendingRepeats = 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     if (this.pendingRepeats > 0 && now >= this.nextRepeatMs) {
/* 266 */       playPing(mc);
/* 267 */       if (this.chatNotify.isValue()) {
/* 268 */         for (class_1304 slot : this.pendingSlots) {
/* 269 */           class_1799 st = mc.field_1724.method_6118(slot);
/*     */ 
/*     */           
/* 272 */           String name = (st != null && !st.method_7960()) ? st.method_7964().getString() : this.lastName.getOrDefault(slot, fallbackName(slot));
/* 273 */           sendAlmostBrokeMessage(mc, name);
/*     */         } 
/*     */       }
/* 276 */       this.pendingRepeats--;
/*     */ 
/*     */       
/* 279 */       this.nextRepeatMs = now + (long)(this.repeatDelay.getValue() * 1000.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     if (newSlots != null && 
/* 286 */       now - this.lastAlertMs >= 4000L) {
/* 287 */       playPing(mc);
/* 288 */       if (this.chatNotify.isValue()) {
/* 289 */         for (class_1304 slot : newSlots) {
/* 290 */           class_1799 st = mc.field_1724.method_6118(slot);
/*     */ 
/*     */           
/* 293 */           String name = (st != null && !st.method_7960()) ? st.method_7964().getString() : fallbackName(slot);
/* 294 */           sendAlmostBrokeMessage(mc, name);
/*     */         } 
/*     */       }
/* 297 */       this.lastAlertMs = now;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (this.repeat.isValue()) {
/* 307 */         this.pendingRepeats = this.repetitions.getInt();
/* 308 */         this.nextRepeatMs = now + (long)(this.repeatDelay.getValue() * 1000.0F);
/* 309 */         this.pendingSlots.clear();
/* 310 */         this.pendingSlots.addAll(newSlots);
/*     */       } else {
/* 312 */         this.pendingRepeats = 0;
/* 313 */         this.pendingSlots.clear();
/*     */       } 
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
/*     */   
/*     */   private static String fallbackName(class_1304 slot) {
/* 329 */     switch (slot) { case field_6169: case field_6174: case field_6172: case field_6166:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       "Armor";
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
/*     */   private static void sendAlmostBrokeMessage(class_310 mc, String pieceName) {
/* 348 */     if (mc.field_1705 == null) {
/*     */       return;
/*     */     }
/* 351 */     class_5250 prefix = class_2561.method_43470("[Phaze] ").method_27695(new class_124[] { class_124.field_1075, class_124.field_1067 });
/* 352 */     class_5250 body = class_2561.method_43470(pieceName + " almost broke!").method_27692(class_124.field_1068);
/* 353 */     mc.field_1705.method_1743().method_1812((class_2561)prefix.method_10852((class_2561)body));
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
/*     */   private static void sendBrokeMessage(class_310 mc, String pieceName) {
/* 365 */     if (mc.field_1705 == null) {
/*     */       return;
/*     */     }
/* 368 */     class_5250 prefix = class_2561.method_43470("[Phaze] ").method_27695(new class_124[] { class_124.field_1075, class_124.field_1067 });
/* 369 */     class_5250 body = class_2561.method_43470(pieceName + " broke!").method_27692(class_124.field_1061);
/* 370 */     mc.field_1705.method_1743().method_1812((class_2561)prefix.method_10852((class_2561)body));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBelowThreshold(class_1799 stack, float thresholdFraction) {
/* 379 */     if (stack == null || stack.method_7960()) {
/* 380 */       return false;
/*     */     }
/* 382 */     int max = stack.method_7936();
/* 383 */     if (max <= 0) {
/* 384 */       return false;
/*     */     }
/* 386 */     int remaining = max - stack.method_7919();
/* 387 */     float fraction = remaining / max;
/* 388 */     return (fraction <= thresholdFraction);
/*     */   }
/*     */   
/*     */   private void playPing(class_310 mc) {
/* 392 */     class_1144 soundManager = mc.method_1483();
/* 393 */     if (soundManager == null) {
/*     */       return;
/*     */     }
/* 396 */     class_3414 sound = (class_3414)class_3417.field_14622.comp_349();
/* 397 */     soundManager.method_4873((class_1113)class_1109.method_4757(sound, 1.6F, 0.9F));
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
/*     */   private void playBreakSound(class_310 mc) {
/* 409 */     class_1144 soundManager = mc.method_1483();
/* 410 */     if (soundManager == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 417 */     class_3414 sound = class_3417.field_15075;
/* 418 */     soundManager.method_4873((class_1113)class_1109.method_4757(sound, 1.0F, 1.0F));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ArmorNotifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */