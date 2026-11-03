/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ public class BooleanSetting
/*    */   extends Setting {
/*    */   private boolean value;
/*    */   private Boolean defaultValue;
/*    */   
/*    */   public BooleanSetting setDefaultValue(Boolean defaultValue) {
/* 13 */     this.defaultValue = defaultValue; return this; } public BooleanSetting setKey(int key) { this.key = key; return this; } public BooleanSetting setType(int type) { this.type = type; return this; } public BooleanSetting setOnChangeCallback(Consumer<Boolean> onChangeCallback) { this.onChangeCallback = onChangeCallback; return this; }
/*    */ 
/*    */   
/* 16 */   public boolean isValue() { return this.value; } public Boolean getDefaultValue() {
/* 17 */     return this.defaultValue;
/* 18 */   } private int key = -1; public int getKey() { return this.key; }
/* 19 */    private int type = 1; private Consumer<Boolean> onChangeCallback; public int getType() { return this.type; } public Consumer<Boolean> getOnChangeCallback() {
/* 20 */     return this.onChangeCallback;
/*    */   }
/*    */   public BooleanSetting(String name, String description) {
/* 23 */     super(name, description);
/*    */   }
/*    */   
/*    */   public BooleanSetting visible(Supplier<Boolean> visible) {
/* 27 */     setVisible(visible);
/* 28 */     return this;
/*    */   }
/*    */   
/*    */   public BooleanSetting onChange(Consumer<Boolean> callback) {
/* 32 */     this.onChangeCallback = callback;
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public BooleanSetting setValue(boolean value) {
/* 37 */     if (this.defaultValue == null) {
/* 38 */       this.defaultValue = Boolean.valueOf(value);
/*    */     }
/* 40 */     boolean changed = (this.value != value);
/* 41 */     this.value = value;
/* 42 */     if (changed) {
/* 43 */       notifyChange();
/* 44 */       if (this.onChangeCallback != null) {
/* 45 */         this.onChangeCallback.accept(Boolean.valueOf(value));
/*    */       }
/*    */     } 
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 53 */     if (this.defaultValue == null) {
/* 54 */       return false;
/*    */     }
/* 56 */     return (this.value != this.defaultValue.booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 61 */     if (this.defaultValue != null)
/* 62 */       setValue(this.defaultValue.booleanValue()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\BooleanSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */