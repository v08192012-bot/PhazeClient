/*     */ package vorga.phazeclient.api.feature.module.setting.implement;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ public class ValueSetting extends Setting {
/*     */   private float value;
/*     */   private float min;
/*     */   private float max;
/*     */   private boolean integer;
/*     */   private Float defaultValue;
/*     */   
/*  12 */   public ValueSetting setMin(float min) { this.min = min; return this; } public ValueSetting setMax(float max) { this.max = max; return this; } public ValueSetting setInteger(boolean integer) { this.integer = integer; return this; } public ValueSetting setDefaultValue(Float defaultValue) { this.defaultValue = defaultValue; return this; } public ValueSetting setStep(float step) { this.step = step; return this; } public ValueSetting setSnapTarget(float snapTarget) { this.snapTarget = snapTarget; return this; } public ValueSetting setSnapRadius(float snapRadius) { this.snapRadius = snapRadius; return this; } public ValueSetting setOnChangeCallback(Consumer<Float> onChangeCallback) { this.onChangeCallback = onChangeCallback; return this; }
/*     */ 
/*     */   
/*  15 */   public float getValue() { return this.value; } public float getMin() { return this.min; } public float getMax() { return this.max; }
/*  16 */   public boolean isInteger() { return this.integer; } public Float getDefaultValue() {
/*  17 */     return this.defaultValue;
/*  18 */   } private float step = 0.1F; private float snapTarget; public float getStep() { return this.step; }
/*     */    private float snapRadius; private Consumer<Float> onChangeCallback; public float getSnapTarget() {
/*  20 */     return this.snapTarget; }
/*  21 */   public float getSnapRadius() { return this.snapRadius; } public Consumer<Float> getOnChangeCallback() {
/*  22 */     return this.onChangeCallback;
/*     */   }
/*     */   public ValueSetting(String name, String description) {
/*  25 */     super(name, description);
/*     */   }
/*     */   
/*     */   public ValueSetting range(float min, float max) {
/*  29 */     this.min = min;
/*  30 */     this.max = max;
/*  31 */     return this;
/*     */   }
/*     */   
/*     */   public ValueSetting range(int min, int max) {
/*  35 */     this.min = min;
/*  36 */     this.max = max;
/*  37 */     this.integer = true;
/*  38 */     return this;
/*     */   }
/*     */   
/*     */   public ValueSetting step(float step) {
/*  42 */     this.step = step;
/*  43 */     return this;
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
/*     */   
/*     */   public ValueSetting snapTo(float target, float radius) {
/*  63 */     this.snapTarget = target;
/*  64 */     this.snapRadius = Math.abs(radius);
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueSetting snapToDefault(float radius) {
/*  70 */     return snapTo((this.defaultValue != null) ? this.defaultValue.floatValue() : this.value, radius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float applySnap(float raw) {
/*  79 */     if (this.snapRadius <= 0.0F) {
/*  80 */       return raw;
/*     */     }
/*  82 */     return (Math.abs(raw - this.snapTarget) <= this.snapRadius) ? this.snapTarget : raw;
/*     */   }
/*     */   
/*     */   public int getInt() {
/*  86 */     return (int)this.value;
/*     */   }
/*     */   
/*     */   public ValueSetting visible(Supplier<Boolean> visible) {
/*  90 */     setVisible(visible);
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public ValueSetting onChange(Consumer<Float> callback) {
/*  95 */     this.onChangeCallback = callback;
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public ValueSetting setValue(float value) {
/* 100 */     if (this.defaultValue == null) {
/* 101 */       this.defaultValue = Float.valueOf(value);
/*     */     }
/* 103 */     boolean changed = (this.value != value);
/* 104 */     this.value = value;
/* 105 */     notifyChange();
/* 106 */     if (changed && this.onChangeCallback != null) {
/* 107 */       this.onChangeCallback.accept(Float.valueOf(value));
/*     */     }
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/* 114 */     if (this.defaultValue == null) {
/* 115 */       return false;
/*     */     }
/* 117 */     return (this.value != this.defaultValue.floatValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 122 */     if (this.defaultValue != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       setValue(this.defaultValue.floatValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\ValueSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */