/*     */ package vorga.phazeclient.api.feature.module.setting.implement;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ 
/*     */ public class GroupSetting extends Setting {
/*     */   private boolean value;
/*     */   private Boolean defaultValue;
/*     */   
/*     */   public GroupSetting setDefaultValue(Boolean defaultValue) {
/*  14 */     this.defaultValue = defaultValue; return this; } public GroupSetting setCheckbox(boolean checkbox) { this.checkbox = checkbox; return this; } public GroupSetting setSubSettings(List<Setting> subSettings) { this.subSettings = subSettings; return this; } public GroupSetting setColorPickerStyleWindow(boolean colorPickerStyleWindow) { this.colorPickerStyleWindow = colorPickerStyleWindow; return this; } public GroupSetting setPopupWidth(float popupWidth) { this.popupWidth = popupWidth; return this; } public GroupSetting setPopupMaxHeight(float popupMaxHeight) { this.popupMaxHeight = popupMaxHeight; return this; }
/*     */ 
/*     */   
/*  17 */   public boolean isValue() { return this.value; }
/*  18 */   public Boolean getDefaultValue() { return this.defaultValue; } public boolean isCheckbox() {
/*  19 */     return this.checkbox;
/*  20 */   } private boolean checkbox = true; private List<Setting> subSettings = new ArrayList<>(); public List<Setting> getSubSettings() { return this.subSettings; } public boolean isColorPickerStyleWindow() {
/*  21 */     return this.colorPickerStyleWindow;
/*  22 */   } private boolean colorPickerStyleWindow = false; private float popupWidth = 148.0F; public float getPopupWidth() { return this.popupWidth; }
/*  23 */    private float popupMaxHeight = 190.0F; public float getPopupMaxHeight() { return this.popupMaxHeight; }
/*     */   
/*     */   public GroupSetting(String name, String description) {
/*  26 */     super(name, description);
/*     */   }
/*     */   
/*     */   public GroupSetting(String name, String description, boolean checkbox) {
/*  30 */     super(name, description);
/*  31 */     this.checkbox = checkbox;
/*     */   }
/*     */   
/*     */   public GroupSetting settings(Setting... setting) {
/*  35 */     this.subSettings.addAll(Arrays.asList(setting));
/*  36 */     return this;
/*     */   }
/*     */   
/*     */   public GroupSetting visible(Supplier<Boolean> visible) {
/*  40 */     setVisible(visible);
/*  41 */     return this;
/*     */   }
/*     */   
/*     */   public GroupSetting colorPickerStyleWindow(boolean colorPickerStyleWindow) {
/*  45 */     this.colorPickerStyleWindow = colorPickerStyleWindow;
/*  46 */     return this;
/*     */   }
/*     */   
/*     */   public GroupSetting popupWidth(float popupWidth) {
/*  50 */     this.popupWidth = popupWidth;
/*  51 */     return this;
/*     */   }
/*     */   
/*     */   public GroupSetting popupMaxHeight(float popupMaxHeight) {
/*  55 */     this.popupMaxHeight = popupMaxHeight;
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  61 */     return getNameKey();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  66 */     String descriptionKey = getDescriptionKey();
/*  67 */     if (descriptionKey == null || descriptionKey.isEmpty()) {
/*  68 */       return "";
/*     */     }
/*  70 */     return descriptionKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  75 */     return super.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalizedDescription() {
/*  80 */     return super.getDescription();
/*     */   }
/*     */   
/*     */   public GroupSetting setValue(boolean value) {
/*  84 */     if (this.defaultValue == null) {
/*  85 */       this.defaultValue = Boolean.valueOf(value);
/*     */     }
/*  87 */     this.value = value;
/*  88 */     notifyChange();
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/*  94 */     if (this.defaultValue != null && this.value != this.defaultValue.booleanValue()) {
/*  95 */       return true;
/*     */     }
/*     */     
/*  98 */     for (Setting subSetting : this.subSettings) {
/*  99 */       if (subSetting.isModified()) {
/* 100 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 109 */     if (this.defaultValue != null)
/*     */     {
/* 111 */       setValue(this.defaultValue.booleanValue());
/*     */     }
/*     */     
/* 114 */     for (Setting subSetting : this.subSettings)
/* 115 */       subSetting.reset(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\GroupSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */