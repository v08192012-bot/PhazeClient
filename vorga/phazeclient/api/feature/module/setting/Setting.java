/*    */ package vorga.phazeclient.api.feature.module.setting;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class Setting {
/*    */   private final String nameKey;
/*    */   private final String descriptionKey;
/*    */   private String moduleContext;
/*    */   private Supplier<Boolean> visible;
/*    */   private static Consumer<Setting> globalChangeListener;
/*    */   
/* 12 */   public String getNameKey() { return this.nameKey; }
/* 13 */   public String getDescriptionKey() { return this.descriptionKey; }
/* 14 */   public void setModuleContext(String moduleContext) { this.moduleContext = moduleContext; }
/* 15 */   public String getModuleContext() { return this.moduleContext; }
/* 16 */   public void setVisible(Supplier<Boolean> visible) { this.visible = visible; }
/* 17 */   public Supplier<Boolean> getVisible() { return this.visible; } public static void setGlobalChangeListener(Consumer<Setting> globalChangeListener) {
/* 18 */     Setting.globalChangeListener = globalChangeListener;
/*    */   } private boolean saveToConfig = true;
/* 20 */   public void setSaveToConfig(boolean saveToConfig) { this.saveToConfig = saveToConfig; }
/* 21 */   public boolean isSaveToConfig() { return this.saveToConfig; }
/* 22 */   public void setFullWidth(boolean fullWidth) { this.fullWidth = fullWidth; } private boolean fullWidth = false; public boolean isFullWidth() {
/* 23 */     return this.fullWidth;
/*    */   }
/*    */   public Setting(String nameKey) {
/* 26 */     this.nameKey = nameKey;
/* 27 */     this.descriptionKey = null;
/*    */   }
/*    */   
/*    */   public Setting(String nameKey, String descriptionKey) {
/* 31 */     this.nameKey = nameKey;
/* 32 */     this.descriptionKey = descriptionKey;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 36 */     return (this.visible == null || ((Boolean)this.visible.get()).booleanValue());
/*    */   }
/*    */   
/*    */   public String getName() {
/* 40 */     return Lang.translate(this.nameKey);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRawName() {
/* 47 */     return this.nameKey;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 51 */     if (this.descriptionKey == null || this.descriptionKey.isEmpty()) {
/* 52 */       return "";
/*    */     }
/* 54 */     return Lang.translate(this.descriptionKey);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRawDescription() {
/* 59 */     return (this.descriptionKey == null) ? "" : this.descriptionKey;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public String getLocalizedName() {
/* 64 */     return getName();
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public String getLocalizedDescription() {
/* 69 */     return getDescription();
/*    */   }
/*    */   
/*    */   public boolean isModified() {
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {}
/*    */   
/*    */   public Setting fullWidth(boolean fullWidth) {
/* 80 */     this.fullWidth = fullWidth;
/* 81 */     return this;
/*    */   }
/*    */   
/*    */   protected void notifyChange() {
/* 85 */     if (globalChangeListener != null)
/* 86 */       globalChangeListener.accept(this); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\Setting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */