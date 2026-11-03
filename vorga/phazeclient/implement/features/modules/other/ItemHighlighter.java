/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_7923;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ButtonSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ItemPickerSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ItemHighlighter
/*     */   extends Module
/*     */ {
/*     */   private static final int RESULT_CACHE_MAX = 512;
/*  34 */   private static final int[] CUSTOM_ITEM_COLORS = new int[] { 6542079, 16757575, 9103770, 16748459, 11901951, 16767083, 7528913, 16740193 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final ItemHighlighter INSTANCE = new ItemHighlighter();
/*     */   
/*  47 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  48 */   public final ValueSetting opacity = (new ValueSetting("Opacity", "Highlight overlay opacity in percent"))
/*     */ 
/*     */     
/*  51 */     .range(0, 100).step(1.0F).setValue(40.0F);
/*     */   
/*  53 */   public final SectionSetting basicSection = new SectionSetting("Basic");
/*  54 */   public final SectionSetting funTimeSection = (new SectionSetting("FunTime")).visible(this::shouldShowFunTimeEntries);
/*  55 */   public final SectionSetting holyWorldSection = (new SectionSetting("HolyWorld")).visible(this::shouldShowHolyWorldEntries);
/*  56 */   public final SectionSetting customSection = new SectionSetting("Custom");
/*  57 */   public final ButtonSetting addItem = (new ButtonSetting("Add Item", "Reveal another custom highlight row and pick an inventory item"))
/*     */ 
/*     */     
/*  60 */     .setButtonName("Add").visible(this::hasInactiveCustomItem);
/*     */   
/*  62 */   private final List<ItemPickerSetting> entries = new ArrayList<>();
/*  63 */   private final List<ItemPickerSetting> customItems = new ArrayList<>();
/*  64 */   private final Map<class_1792, List<CompiledEntry>> compiledEntriesByItem = new IdentityHashMap<>();
/*  65 */   private final Map<class_1799, Integer> preparedColorCache = new IdentityHashMap<>();
/*  66 */   private final Map<ResultCacheKey, Integer> resolvedColorCache = new LinkedHashMap<>(128, 0.75F, true);
/*  67 */   private int compiledStateMask = Integer.MIN_VALUE;
/*  68 */   private int compiledEntriesFingerprint = Integer.MIN_VALUE;
/*  69 */   private int compiledAlpha = 0;
/*     */   
/*     */   private ItemHighlighter() {
/*  72 */     super("item_highlighter", "Item Highlighter", ModuleCategory.UTILITIES);
/*  73 */     this.opacity.setFullWidth(true);
/*  74 */     this.addItem.setFullWidth(true);
/*  75 */     this.addItem.setRunnable(this::activateNextCustomItem);
/*     */     
/*  77 */     List<Setting> all = new ArrayList<>();
/*  78 */     all.add(this.generalSection);
/*  79 */     all.add(this.opacity);
/*     */     
/*  81 */     all.add(this.basicSection);
/*  82 */     addBuiltIn(all, "Totem of Undying", class_1802.field_8288, "", 16763904, () -> Boolean.valueOf(true));
/*  83 */     addBuiltIn(all, "Experience Bottle", class_1802.field_8287, "", 6750156, () -> Boolean.valueOf(true));
/*  84 */     addBuiltIn(all, "Golden Apple", class_1802.field_8463, "", 16766720, () -> Boolean.valueOf(true));
/*  85 */     addBuiltIn(all, "Enchanted Golden Apple", class_1802.field_8367, "", 16737996, () -> Boolean.valueOf(true));
/*  86 */     addBuiltIn(all, "Chorus Fruit", class_1802.field_8233, "", 9699539, () -> Boolean.valueOf(true));
/*  87 */     addBuiltIn(all, "Ender Pearl", class_1802.field_8634, "", 6737049, () -> Boolean.valueOf(true));
/*     */     
/*  89 */     all.add(this.funTimeSection);
/*  90 */     addBuiltIn(all, "Disorientation", class_1802.field_8449, "дезориентация", 65280, this::shouldShowFunTimeEntries);
/*  91 */     addBuiltIn(all, "Revealing Dust", class_1802.field_8479, "явная пыль", 16777215, this::shouldShowFunTimeEntries);
/*  92 */     addBuiltIn(all, "Trapka", class_1802.field_22021, "трапка", 12759692, this::shouldShowFunTimeEntries);
/*  93 */     addBuiltIn(all, "Plate", class_1802.field_8551, "пласт", 8421504, this::shouldShowFunTimeEntries);
/*  94 */     addBuiltIn(all, "Freeze Snowball", class_1802.field_8543, "снежок заморозки", 11393253, this::shouldShowFunTimeEntries);
/*     */     
/*  96 */     all.add(this.holyWorldSection);
/*  97 */     addBuiltIn(all, "Trapka 2", class_1802.field_8882, "трапка 2|прожаренный плод хоруса", 16747610, this::shouldShowHolyWorldEntries);
/*  98 */     addBuiltIn(all, "Stun", class_1802.field_8137, "стан|звезда визера", 8104191, this::shouldShowHolyWorldEntries);
/*  99 */     addBuiltIn(all, "Jack O'Lantern", class_1802.field_8693, "светильник джека|jack o'lantern", 16757018, this::shouldShowHolyWorldEntries);
/* 100 */     addBuiltIn(all, "Explosive Trapka", class_1802.field_8662, "взрывная трапка|осколок призмарина", 6480327, this::shouldShowHolyWorldEntries);
/*     */     
/* 102 */     all.add(this.customSection);
/* 103 */     all.add(this.addItem);
/* 104 */     for (int i = 0; i < CUSTOM_ITEM_COLORS.length; i++) {
/* 105 */       int slotIndex = i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       ItemPickerSetting customItem = (new ItemPickerSetting("Custom Item " + i + 1, "Highlight a custom inventory item selected from the picker", CUSTOM_ITEM_COLORS[i])).rowLabel("Custom Item Slot " + i + 1).colorPresets(CUSTOM_ITEM_COLORS).visible(() -> Boolean.valueOf(customItemVisible(slotIndex)));
/* 113 */       customItem.setFullWidth(true);
/* 114 */       this.customItems.add(customItem);
/* 115 */       this.entries.add(customItem);
/* 116 */       all.add(customItem);
/*     */     } 
/*     */     
/* 119 */     setup(all.<Setting>toArray(new Setting[0]));
/*     */   }
/*     */   
/*     */   public static ItemHighlighter getInstance() {
/* 123 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 128 */     return "Tints inventory slots holding signature items so they're visible at a glance";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 133 */     return "item_highlighter.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 138 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public int colorForStack(class_1799 stack) {
/* 142 */     if (!isEnabled() || stack == null || stack.method_7960()) {
/* 143 */       return 0;
/*     */     }
/* 145 */     refreshCompiledEntries();
/* 146 */     return resolveCompiledColor(stack);
/*     */   }
/*     */   
/*     */   public void beginRenderPass() {
/* 150 */     refreshCompiledEntries();
/* 151 */     this.preparedColorCache.clear();
/*     */   }
/*     */   
/*     */   public int colorForPreparedStack(class_1799 stack) {
/* 155 */     if (!isEnabled() || stack == null || stack.method_7960()) {
/* 156 */       return 0;
/*     */     }
/* 158 */     Integer cached = this.preparedColorCache.get(stack);
/* 159 */     if (cached != null) {
/* 160 */       return cached.intValue();
/*     */     }
/*     */     
/* 163 */     int color = resolveCompiledColor(stack);
/* 164 */     this.preparedColorCache.put(stack, Integer.valueOf(color));
/* 165 */     return color;
/*     */   }
/*     */   
/*     */   private int resolveCompiledColor(class_1799 stack) {
/* 169 */     if (this.compiledAlpha <= 0) {
/* 170 */       return 0;
/*     */     }
/*     */     
/* 173 */     List<CompiledEntry> candidates = this.compiledEntriesByItem.get(stack.method_7909());
/* 174 */     if (candidates == null || candidates.isEmpty()) {
/* 175 */       return 0;
/*     */     }
/*     */     
/* 178 */     boolean needsDisplayName = false;
/* 179 */     for (CompiledEntry entry : candidates) {
/* 180 */       if (entry.requiresDisplayName()) {
/* 181 */         needsDisplayName = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 186 */     String normalizedDisplay = needsDisplayName ? normalize(stack.method_7964().getString()) : "";
/* 187 */     ResultCacheKey cacheKey = new ResultCacheKey(stack.method_7909(), normalizedDisplay);
/* 188 */     Integer cached = this.resolvedColorCache.get(cacheKey);
/* 189 */     if (cached != null) {
/* 190 */       return cached.intValue();
/*     */     }
/*     */     
/* 193 */     int resolvedColor = 0;
/* 194 */     for (CompiledEntry entry : candidates) {
/* 195 */       if (!entry.requiresDisplayName()) {
/* 196 */         resolvedColor = this.compiledAlpha << 24 | entry.rgb();
/*     */         break;
/*     */       } 
/* 199 */       if (entry.matches(normalizedDisplay)) {
/* 200 */         resolvedColor = this.compiledAlpha << 24 | entry.rgb();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 205 */     cacheResolvedColor(cacheKey, resolvedColor);
/* 206 */     return resolvedColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addBuiltIn(List<Setting> all, String label, class_1792 item, String matchName, int color, Supplier<Boolean> visible) {
/* 216 */     ItemPickerSetting setting = (new ItemPickerSetting(label, "Tint slots holding " + label.toLowerCase(Locale.ROOT), color)).fixedSelection(item, matchName).rowLabel(label).visible(visible);
/* 217 */     setting.setFullWidth(true);
/* 218 */     this.entries.add(setting);
/* 219 */     all.add(setting);
/*     */   }
/*     */   
/*     */   private boolean hasActiveCustomItem() {
/* 223 */     for (ItemPickerSetting customItem : this.customItems) {
/* 224 */       if (customItem.isActive()) {
/* 225 */         return true;
/*     */       }
/*     */     } 
/* 228 */     return false;
/*     */   }
/*     */   
/*     */   private boolean hasInactiveCustomItem() {
/* 232 */     for (ItemPickerSetting customItem : this.customItems) {
/* 233 */       if (!customItem.isActive()) {
/* 234 */         return true;
/*     */       }
/*     */     } 
/* 237 */     return false;
/*     */   }
/*     */   
/*     */   private boolean customItemVisible(int index) {
/* 241 */     return (index >= 0 && index < this.customItems.size() && ((ItemPickerSetting)this.customItems.get(index)).isActive());
/*     */   }
/*     */   
/*     */   private boolean shouldShowFunTimeEntries() {
/* 245 */     return (isSingleplayerContext() || ServerUtil.isFunTimeServer());
/*     */   }
/*     */   
/*     */   private boolean shouldShowHolyWorldEntries() {
/* 249 */     return (isSingleplayerContext() || ServerUtil.isHolyWorldServer());
/*     */   }
/*     */   
/*     */   private static boolean isSingleplayerContext() {
/* 253 */     class_310 client = class_310.method_1551();
/* 254 */     return (client != null && client.method_1542());
/*     */   }
/*     */   
/*     */   private void activateNextCustomItem() {
/* 258 */     for (ItemPickerSetting customItem : this.customItems) {
/* 259 */       if (!customItem.isActive()) {
/* 260 */         customItem.setActive(true);
/* 261 */         this.compiledEntriesFingerprint = Integer.MIN_VALUE;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshCompiledEntries() {
/* 272 */     int contextMask = (isSingleplayerContext() ? 1 : 0) | (ServerUtil.isFunTimeServer() ? 2 : 0) | (ServerUtil.isHolyWorldServer() ? 4 : 0) | Math.max(0, this.opacity.getInt()) << 8 | (isEnabled() ? 1073741824 : 0);
/* 273 */     int entriesFingerprint = computeEntriesFingerprint();
/* 274 */     boolean cacheStateChanged = (contextMask != this.compiledStateMask || entriesFingerprint != this.compiledEntriesFingerprint);
/*     */ 
/*     */     
/* 277 */     if (!cacheStateChanged) {
/*     */       return;
/*     */     }
/*     */     
/* 281 */     this.compiledStateMask = contextMask;
/* 282 */     this.compiledEntriesFingerprint = entriesFingerprint;
/* 283 */     this.resolvedColorCache.clear();
/* 284 */     this.compiledEntriesByItem.clear();
/* 285 */     this.compiledAlpha = 0;
/*     */     
/* 287 */     int op = this.opacity.getInt();
/* 288 */     if (!isEnabled() || op <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 292 */     this.compiledAlpha = Math.max(0, Math.min(255, Math.round(op / 100.0F * 255.0F)));
/* 293 */     for (ItemPickerSetting entry : this.entries) {
/* 294 */       if (!entry.isVisible() || !entry.isActive() || !entry.isEnabled() || !entry.hasSelection()) {
/*     */         continue;
/*     */       }
/*     */       
/* 298 */       class_1792 item = resolveItem(entry.getItemId());
/* 299 */       if (item == null || item == class_1802.field_8162) {
/*     */         continue;
/*     */       }
/*     */       
/* 303 */       ((List<CompiledEntry>)this.compiledEntriesByItem
/* 304 */         .computeIfAbsent(item, ignored -> new ArrayList(2)))
/* 305 */         .add(CompiledEntry.of(entry));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cacheResolvedColor(ResultCacheKey key, int color) {
/* 310 */     if (this.resolvedColorCache.size() >= 512 && !this.resolvedColorCache.containsKey(key)) {
/* 311 */       Iterator<Map.Entry<ResultCacheKey, Integer>> iterator = this.resolvedColorCache.entrySet().iterator();
/* 312 */       if (iterator.hasNext()) {
/* 313 */         iterator.next();
/* 314 */         iterator.remove();
/*     */       } 
/*     */     } 
/* 317 */     this.resolvedColorCache.put(key, Integer.valueOf(color));
/*     */   }
/*     */   
/*     */   private int computeEntriesFingerprint() {
/* 321 */     int hash = 1;
/* 322 */     for (ItemPickerSetting entry : this.entries) {
/* 323 */       hash = 31 * hash + (entry.isVisible() ? 1 : 0);
/* 324 */       hash = 31 * hash + (entry.isActive() ? 1 : 0);
/* 325 */       hash = 31 * hash + (entry.isEnabled() ? 1 : 0);
/* 326 */       hash = 31 * hash + entry.getHighlightColor();
/* 327 */       hash = 31 * hash + Objects.hashCode(entry.getItemId());
/* 328 */       hash = 31 * hash + Objects.hashCode(entry.getMatchName());
/*     */     } 
/* 330 */     return hash;
/*     */   }
/*     */   
/*     */   private static class_1792 resolveItem(String itemId) {
/* 334 */     if (itemId == null || itemId.isBlank()) {
/* 335 */       return class_1802.field_8162;
/*     */     }
/*     */     try {
/* 338 */       return (class_1792)class_7923.field_41178.method_63535(class_2960.method_60654(itemId));
/* 339 */     } catch (Exception ignored) {
/* 340 */       return class_1802.field_8162;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String normalize(String value) {
/* 345 */     return (value == null) ? "" : value.trim().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   private static final class CompiledEntry extends Record { private final int rgb; private final String exactName; private final String[] partialTokens;
/* 348 */     private CompiledEntry(int rgb, String exactName, String[] partialTokens) { this.rgb = rgb; this.exactName = exactName; this.partialTokens = partialTokens; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$CompiledEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #348	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 348 */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$CompiledEntry; } public int rgb() { return this.rgb; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$CompiledEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #348	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$CompiledEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$CompiledEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #348	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$CompiledEntry;
/* 348 */       //   0	8	1	o	Ljava/lang/Object; } public String exactName() { return this.exactName; } public String[] partialTokens() { return this.partialTokens; }
/*     */      private static CompiledEntry of(ItemPickerSetting setting) {
/* 350 */       String matchName = setting.getMatchName();
/* 351 */       if (matchName == null || matchName.isBlank()) {
/* 352 */         return new CompiledEntry(setting.getHighlightColor() & 0xFFFFFF, null, new String[0]);
/*     */       }
/*     */       
/* 355 */       if (!setting.isPartialMatch()) {
/* 356 */         return new CompiledEntry(setting
/* 357 */             .getHighlightColor() & 0xFFFFFF, 
/* 358 */             ItemHighlighter.normalize(matchName), new String[0]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 363 */       List<String> tokens = new ArrayList<>();
/* 364 */       for (String token : matchName.split("\\|")) {
/* 365 */         String normalized = ItemHighlighter.normalize(token);
/* 366 */         if (!normalized.isBlank()) {
/* 367 */           tokens.add(normalized);
/*     */         }
/*     */       } 
/*     */       
/* 371 */       return new CompiledEntry(setting
/* 372 */           .getHighlightColor() & 0xFFFFFF, null, tokens
/*     */           
/* 374 */           .<String>toArray(new String[0]));
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean requiresDisplayName() {
/* 379 */       return (this.exactName != null || this.partialTokens.length > 0);
/*     */     }
/*     */     
/*     */     private boolean matches(String normalizedDisplay) {
/* 383 */       if (this.exactName != null) {
/* 384 */         return this.exactName.equals(normalizedDisplay);
/*     */       }
/*     */       
/* 387 */       for (String token : this.partialTokens) {
/* 388 */         if (normalizedDisplay.contains(token)) {
/* 389 */           return true;
/*     */         }
/*     */       } 
/* 392 */       return false;
/*     */     } }
/*     */   private static final class ResultCacheKey extends Record { private final class_1792 item; private final String normalizedDisplay;
/*     */     
/* 396 */     private ResultCacheKey(class_1792 item, String normalizedDisplay) { this.item = item; this.normalizedDisplay = normalizedDisplay; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$ResultCacheKey;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #396	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$ResultCacheKey; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$ResultCacheKey;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #396	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$ResultCacheKey; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$ResultCacheKey;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #396	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/ItemHighlighter$ResultCacheKey;
/* 396 */       //   0	8	1	o	Ljava/lang/Object; } public class_1792 item() { return this.item; } public String normalizedDisplay() { return this.normalizedDisplay; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ItemHighlighter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */