/*     */ package vorga.phazeclient.api.feature.module.setting.implement;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1935;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_7923;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ 
/*     */ public class ItemPickerSetting
/*     */   extends Setting {
/*  18 */   private static final int[] DEFAULT_COLOR_PRESETS = new int[] { 6542079, 16757575, 9103770, 16748459, 11901951, 16767083, 7528913, 16740193 };
/*     */ 
/*     */   
/*     */   private boolean active;
/*     */ 
/*     */   
/*     */   private boolean defaultActive;
/*     */ 
/*     */   
/*     */   public boolean isActive()
/*     */   {
/*  29 */     return this.active; } public boolean isDefaultActive() {
/*  30 */     return this.defaultActive;
/*     */   } private boolean enabled = true;
/*  32 */   public boolean isEnabled() { return this.enabled; } private boolean defaultEnabled = true; public boolean isDefaultEnabled() {
/*  33 */     return this.defaultEnabled;
/*     */   } private int highlightColor; private int defaultHighlightColor; public int getHighlightColor() {
/*  35 */     return this.highlightColor; } public int getDefaultHighlightColor() {
/*  36 */     return this.defaultHighlightColor;
/*     */   }
/*  38 */   private String itemId = ""; public String getItemId() { return this.itemId; }
/*  39 */    private String defaultItemId = ""; public String getDefaultItemId() { return this.defaultItemId; }
/*     */   
/*  41 */   private String displayName = "";
/*  42 */   private String defaultDisplayName = ""; public String getDefaultDisplayName() { return this.defaultDisplayName; }
/*     */   
/*  44 */   private String matchName = ""; public String getMatchName() { return this.matchName; }
/*  45 */    private String defaultMatchName = ""; private boolean partialMatch; private boolean defaultPartialMatch; public String getDefaultMatchName() { return this.defaultMatchName; }
/*     */   
/*  47 */   public boolean isPartialMatch() { return this.partialMatch; } public boolean isDefaultPartialMatch() {
/*  48 */     return this.defaultPartialMatch;
/*     */   } private boolean pickerEnabled = true;
/*  50 */   public boolean isPickerEnabled() { return this.pickerEnabled; } private boolean defaultPickerEnabled = true; public boolean isDefaultPickerEnabled() {
/*  51 */     return this.defaultPickerEnabled;
/*     */   } private boolean clearable = true;
/*  53 */   public boolean isClearable() { return this.clearable; } private boolean defaultClearable = true; public boolean isDefaultClearable() {
/*  54 */     return this.defaultClearable;
/*     */   }
/*  56 */   private String rowLabel = ""; public String getRowLabel() { return this.rowLabel; }
/*  57 */    private int[] colorPresets = (int[])DEFAULT_COLOR_PRESETS.clone(); public int[] getColorPresets() { return this.colorPresets; }
/*     */   
/*     */   public ItemPickerSetting(String name, String description, int defaultColor) {
/*  60 */     super(name, description);
/*  61 */     this.highlightColor = defaultColor;
/*  62 */     this.defaultHighlightColor = defaultColor;
/*     */   }
/*     */   
/*     */   public ItemPickerSetting visible(Supplier<Boolean> visible) {
/*  66 */     setVisible(visible);
/*  67 */     return this;
/*     */   }
/*     */   
/*     */   public ItemPickerSetting rowLabel(String rowLabel) {
/*  71 */     this.rowLabel = (rowLabel == null) ? "" : rowLabel;
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public ItemPickerSetting colorPresets(int... presets) {
/*  76 */     if (presets != null && presets.length > 0) {
/*  77 */       this.colorPresets = Arrays.copyOf(presets, presets.length);
/*     */     }
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public ItemPickerSetting fixedSelection(class_1792 item, String matchName) {
/*  83 */     String resolvedItemId = class_7923.field_41178.method_10221(item).toString();
/*  84 */     String safeMatch = (matchName == null) ? "" : matchName;
/*     */     
/*  86 */     this.active = true;
/*  87 */     this.defaultActive = true;
/*     */     
/*  89 */     this.itemId = resolvedItemId;
/*  90 */     this.defaultItemId = resolvedItemId;
/*  91 */     this.displayName = "";
/*  92 */     this.defaultDisplayName = "";
/*  93 */     this.matchName = safeMatch;
/*  94 */     this.defaultMatchName = safeMatch;
/*  95 */     this.partialMatch = !safeMatch.isBlank();
/*  96 */     this.defaultPartialMatch = this.partialMatch;
/*     */     
/*  98 */     this.pickerEnabled = false;
/*  99 */     this.defaultPickerEnabled = false;
/* 100 */     this.clearable = false;
/* 101 */     this.defaultClearable = false;
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasSelection() {
/* 106 */     return (this.itemId != null && !this.itemId.isBlank());
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 110 */     if (this.displayName != null && !this.displayName.isBlank()) {
/* 111 */       return this.displayName;
/*     */     }
/* 113 */     return (this.itemId == null) ? "" : this.itemId;
/*     */   }
/*     */   
/*     */   public String getRowTitle() {
/* 117 */     if (this.pickerEnabled && hasSelection() && this.displayName != null && !this.displayName.isBlank()) {
/* 118 */       return this.displayName;
/*     */     }
/* 120 */     if (this.rowLabel != null && !this.rowLabel.isBlank()) {
/* 121 */       return Lang.translate(this.rowLabel);
/*     */     }
/* 123 */     if (hasSelection()) {
/* 124 */       return getDisplayName();
/*     */     }
/* 126 */     return getName();
/*     */   }
/*     */   
/*     */   public boolean isCustomPicker() {
/* 130 */     return this.pickerEnabled;
/*     */   }
/*     */   
/*     */   public int getAccentColor() {
/* 134 */     return this.highlightColor;
/*     */   }
/*     */   
/*     */   public void setActive(boolean active) {
/* 138 */     if (this.active != active) {
/* 139 */       this.active = active;
/* 140 */       notifyChange();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 145 */     if (this.enabled != enabled) {
/* 146 */       this.enabled = enabled;
/* 147 */       notifyChange();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHighlightColor(int highlightColor) {
/* 152 */     if (this.highlightColor != highlightColor) {
/* 153 */       this.highlightColor = highlightColor;
/* 154 */       notifyChange();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cycleHighlightColor() {
/* 159 */     if (this.colorPresets.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     int current = this.highlightColor & 0xFFFFFF;
/* 164 */     int nextIndex = 0;
/* 165 */     for (int i = 0; i < this.colorPresets.length; i++) {
/* 166 */       if ((this.colorPresets[i] & 0xFFFFFF) == current) {
/* 167 */         nextIndex = (i + 1) % this.colorPresets.length;
/*     */         break;
/*     */       } 
/*     */     } 
/* 171 */     setHighlightColor(0xFF000000 | this.colorPresets[nextIndex] & 0xFFFFFF);
/*     */   }
/*     */   
/*     */   public void selectFromStack(class_1799 stack) {
/* 175 */     if (stack == null || stack.method_7960()) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     String selectedItemId = class_7923.field_41178.method_10221(stack.method_7909()).toString();
/* 180 */     String selectedDisplayName = stack.method_7964().getString();
/* 181 */     String vanillaName = (new class_1799((class_1935)stack.method_7909())).method_7964().getString();
/* 182 */     String selectedMatchName = selectedDisplayName.equalsIgnoreCase(vanillaName) ? "" : selectedDisplayName;
/*     */     
/* 184 */     applySelection(true, selectedItemId, selectedDisplayName, selectedMatchName, false, true);
/*     */   }
/*     */   
/*     */   public void setSerializedState(boolean active, String itemId, String displayName, String matchName) {
/* 188 */     applySelection(active, itemId, displayName, matchName, this.partialMatch, true);
/*     */   }
/*     */   
/*     */   public void setSerializedState(boolean active, String itemId, String displayName, String matchName, boolean enabled, int highlightColor) {
/* 192 */     boolean changed = (this.enabled != enabled || this.highlightColor != highlightColor);
/* 193 */     this.enabled = enabled;
/* 194 */     this.highlightColor = highlightColor;
/* 195 */     applySelection(active, itemId, displayName, matchName, this.partialMatch, !changed);
/* 196 */     if (changed) {
/* 197 */       notifyChange();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearSelection() {
/* 202 */     applySelection(false, "", "", "", false, true);
/*     */   }
/*     */   
/*     */   public boolean matches(class_1799 stack) {
/* 206 */     return (this.enabled && matchesSelectedItem(stack));
/*     */   }
/*     */   
/*     */   public boolean matchesSelectedItem(class_1799 stack) {
/* 210 */     if (!this.active || !hasSelection() || stack == null || stack.method_7960()) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     String stackItemId = class_7923.field_41178.method_10221(stack.method_7909()).toString();
/* 215 */     if (!stackItemId.equals(this.itemId)) {
/* 216 */       return false;
/*     */     }
/*     */     
/* 219 */     if (this.matchName == null || this.matchName.isBlank()) {
/* 220 */       return true;
/*     */     }
/*     */     
/* 223 */     String display = normalize(stack.method_7964().getString());
/* 224 */     if (this.partialMatch) {
/* 225 */       for (String token : this.matchName.split("\\|")) {
/* 226 */         String normalizedToken = normalize(token);
/* 227 */         if (!normalizedToken.isBlank() && display.contains(normalizedToken)) {
/* 228 */           return true;
/*     */         }
/*     */       } 
/* 231 */       return false;
/*     */     } 
/*     */     
/* 234 */     return display.equals(normalize(this.matchName));
/*     */   }
/*     */   
/*     */   public class_1799 createPreviewStack() {
/* 238 */     if (!hasSelection()) {
/* 239 */       return class_1799.field_8037;
/*     */     }
/*     */     
/*     */     try {
/* 243 */       class_1792 item = (class_1792)class_7923.field_41178.method_63535(class_2960.method_60654(this.itemId));
/* 244 */       if (item == null || item == class_1802.field_8162) {
/* 245 */         return class_1799.field_8037;
/*     */       }
/* 247 */       return new class_1799((class_1935)item);
/* 248 */     } catch (Exception ignored) {
/* 249 */       return class_1799.field_8037;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/* 255 */     return (this.active != this.defaultActive || this.enabled != this.defaultEnabled || this.highlightColor != this.defaultHighlightColor || 
/*     */ 
/*     */       
/* 258 */       !Objects.equals(this.itemId, this.defaultItemId) || 
/* 259 */       !Objects.equals(this.displayName, this.defaultDisplayName) || 
/* 260 */       !Objects.equals(this.matchName, this.defaultMatchName) || this.partialMatch != this.defaultPartialMatch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 271 */     boolean changed = (this.active != this.defaultActive || this.enabled != this.defaultEnabled || this.highlightColor != this.defaultHighlightColor || !Objects.equals(this.itemId, this.defaultItemId) || !Objects.equals(this.displayName, this.defaultDisplayName) || !Objects.equals(this.matchName, this.defaultMatchName) || this.partialMatch != this.defaultPartialMatch || this.pickerEnabled != this.defaultPickerEnabled || this.clearable != this.defaultClearable);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     this.active = this.defaultActive;
/* 277 */     this.enabled = this.defaultEnabled;
/* 278 */     this.highlightColor = this.defaultHighlightColor;
/* 279 */     this.itemId = this.defaultItemId;
/* 280 */     this.displayName = this.defaultDisplayName;
/* 281 */     this.matchName = this.defaultMatchName;
/* 282 */     this.partialMatch = this.defaultPartialMatch;
/* 283 */     this.pickerEnabled = this.defaultPickerEnabled;
/* 284 */     this.clearable = this.defaultClearable;
/*     */     
/* 286 */     if (changed) {
/* 287 */       notifyChange();
/*     */     }
/*     */   }
/*     */   
/*     */   private void applySelection(boolean active, String itemId, String displayName, String matchName, boolean partialMatch, boolean notify) {
/* 292 */     String safeItemId = (itemId == null) ? "" : itemId;
/* 293 */     String safeDisplayName = (displayName == null) ? "" : displayName;
/* 294 */     String safeMatchName = (matchName == null) ? "" : matchName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     boolean changed = (this.active != active || !Objects.equals(this.itemId, safeItemId) || !Objects.equals(this.displayName, safeDisplayName) || !Objects.equals(this.matchName, safeMatchName) || this.partialMatch != partialMatch);
/*     */ 
/*     */     
/* 302 */     this.active = active;
/* 303 */     this.itemId = safeItemId;
/* 304 */     this.displayName = safeDisplayName;
/* 305 */     this.matchName = safeMatchName;
/* 306 */     this.partialMatch = partialMatch;
/*     */     
/* 308 */     if (changed && notify) {
/* 309 */       notifyChange();
/*     */     }
/*     */   }
/*     */   
/*     */   private static String normalize(String value) {
/* 314 */     return (value == null) ? "" : value.trim().toLowerCase(Locale.ROOT);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\ItemPickerSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */