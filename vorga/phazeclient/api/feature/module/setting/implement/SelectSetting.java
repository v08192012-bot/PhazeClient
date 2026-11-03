/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class SelectSetting extends Setting {
/*    */   private String selected;
/*    */   private List<String> list;
/*    */   private Consumer<String> onChangeCallback;
/*    */   private String defaultSelected;
/*    */   
/* 14 */   public String getSelected() { return this.selected; }
/* 15 */   public List<String> getList() { return this.list; }
/* 16 */   public Consumer<String> getOnChangeCallback() { return this.onChangeCallback; } public String getDefaultSelected() {
/* 17 */     return this.defaultSelected;
/*    */   }
/*    */   public SelectSetting(String name, String description) {
/* 20 */     super(name, description);
/*    */   }
/*    */   
/*    */   public SelectSetting value(String... values) {
/* 24 */     List<String> list = Arrays.asList(values);
/*    */     
/* 26 */     this.selected = list.getFirst();
/* 27 */     this.list = list;
/*    */     
/* 29 */     return this;
/*    */   }
/*    */   
/*    */   public SelectSetting visible(Supplier<Boolean> visible) {
/* 33 */     setVisible(visible);
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   public SelectSetting selected(String string) {
/* 38 */     if (this.defaultSelected == null) {
/* 39 */       this.defaultSelected = string;
/*    */     }
/* 41 */     this.selected = string;
/* 42 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isSelected(String name) {
/* 46 */     return this.selected.equalsIgnoreCase(name);
/*    */   }
/*    */   
/*    */   public SelectSetting onChange(Consumer<String> callback) {
/* 50 */     this.onChangeCallback = callback;
/* 51 */     return this;
/*    */   }
/*    */   
/*    */   public void setSelected(String newSelected) {
/* 55 */     boolean changed = (this.selected == null || !this.selected.equals(newSelected));
/*    */     
/* 57 */     this.selected = newSelected;
/*    */     
/* 59 */     if (changed) {
/* 60 */       notifyChange();
/* 61 */       if (this.onChangeCallback != null) {
/* 62 */         this.onChangeCallback.accept(newSelected);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getSelectedLocalized() {
/* 68 */     return this.selected;
/*    */   }
/*    */   
/*    */   public List<String> getListLocalized() {
/* 72 */     return this.list;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 77 */     if (this.defaultSelected == null) {
/* 78 */       return false;
/*    */     }
/* 80 */     if (this.selected == null) {
/* 81 */       return (this.defaultSelected != null);
/*    */     }
/* 83 */     return !this.selected.equals(this.defaultSelected);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 88 */     if (this.defaultSelected != null)
/* 89 */       setSelected(this.defaultSelected); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\SelectSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */