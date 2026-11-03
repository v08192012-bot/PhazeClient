/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ public class MultiSelectSetting
/*    */   extends Setting {
/*    */   private List<String> list;
/*    */   
/*    */   public void setList(List<String> list) {
/* 14 */     this.list = list; } public void setSelected(List<String> selected) { this.selected = selected; } public void setDefaultSelected(List<String> defaultSelected) { this.defaultSelected = defaultSelected; }
/*    */   
/* 16 */   private List<String> selected = new ArrayList<>(); private List<String> defaultSelected; public List<String> getList() { return this.list; } public List<String> getSelected() { return this.selected; } public List<String> getDefaultSelected() {
/* 17 */     return this.defaultSelected;
/*    */   }
/*    */   public MultiSelectSetting(String name, String description) {
/* 20 */     super(name, description);
/*    */   }
/*    */   
/*    */   public MultiSelectSetting value(String... settings) {
/* 24 */     this.list = Arrays.asList(settings);
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public MultiSelectSetting selected(String... settings) {
/* 29 */     if (this.defaultSelected == null) {
/* 30 */       this.defaultSelected = new ArrayList<>(Arrays.asList(settings));
/*    */     }
/* 32 */     this.selected = new ArrayList<>(Arrays.asList(settings));
/* 33 */     notifyChange();
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   public MultiSelectSetting visible(Supplier<Boolean> visible) {
/* 38 */     setVisible(visible);
/* 39 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isSelected(String name) {
/* 43 */     return this.selected.contains(name);
/*    */   }
/*    */   
/*    */   public List<String> getListLocalized() {
/* 47 */     return this.list;
/*    */   }
/*    */   
/*    */   public List<String> getSelectedLocalized() {
/* 51 */     return this.selected;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 56 */     if (this.defaultSelected == null) {
/* 57 */       return false;
/*    */     }
/* 59 */     if (this.selected == null) {
/* 60 */       return (this.defaultSelected != null);
/*    */     }
/* 62 */     if (this.selected.size() != this.defaultSelected.size()) {
/* 63 */       return true;
/*    */     }
/* 65 */     return (!this.selected.containsAll(this.defaultSelected) || !this.defaultSelected.containsAll(this.selected));
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 70 */     if (this.defaultSelected != null) {
/*    */ 
/*    */ 
/*    */       
/* 74 */       this.selected = new ArrayList<>(this.defaultSelected);
/* 75 */       notifyChange();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\MultiSelectSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */