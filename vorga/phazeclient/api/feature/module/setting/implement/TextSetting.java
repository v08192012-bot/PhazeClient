/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ public class TextSetting extends Setting {
/*    */   private String text;
/*    */   private String defaultText;
/*    */   
/*    */   public TextSetting setDefaultText(String defaultText) {
/* 12 */     this.defaultText = defaultText; return this; } public TextSetting setMin(int min) { this.min = min; return this; } public TextSetting setMax(int max) { this.max = max; return this; } public TextSetting setOnChangeCallback(Consumer<String> onChangeCallback) { this.onChangeCallback = onChangeCallback; return this; }
/*    */ 
/*    */   
/* 15 */   public String getText() { return this.text; } public String getDefaultText() {
/* 16 */     return this.defaultText;
/* 17 */   } private int min = 0; public int getMin() { return this.min; }
/* 18 */    private int max = Integer.MAX_VALUE; private Consumer<String> onChangeCallback; public int getMax() { return this.max; } public Consumer<String> getOnChangeCallback() {
/* 19 */     return this.onChangeCallback;
/*    */   }
/*    */   public TextSetting(String name, String description) {
/* 22 */     super(name, description);
/*    */   }
/*    */   
/*    */   public TextSetting visible(Supplier<Boolean> visible) {
/* 26 */     setVisible(visible);
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   public TextSetting onChange(Consumer<String> callback) {
/* 31 */     this.onChangeCallback = callback;
/* 32 */     return this;
/*    */   }
/*    */   
/*    */   public TextSetting setText(String text) {
/* 36 */     if (this.defaultText == null) {
/* 37 */       this.defaultText = text;
/*    */     }
/* 39 */     boolean changed = (this.text == null) ? ((text != null)) : (!this.text.equals(text));
/* 40 */     this.text = text;
/* 41 */     notifyChange();
/* 42 */     if (changed && this.onChangeCallback != null) {
/* 43 */       this.onChangeCallback.accept(text);
/*    */     }
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 50 */     if (this.defaultText == null) {
/* 51 */       return false;
/*    */     }
/* 53 */     if (this.text == null) {
/* 54 */       return (this.defaultText != null);
/*    */     }
/* 56 */     return !this.text.equals(this.defaultText);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 61 */     if (this.defaultText != null)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 67 */       setText(this.defaultText);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\TextSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */