/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ public class ButtonSetting extends Setting {
/*    */   private Runnable runnable;
/*    */   private String buttonName;
/*    */   
/*    */   public ButtonSetting setRunnable(Runnable runnable) {
/* 11 */     this.runnable = runnable; return this; } public ButtonSetting setButtonName(String buttonName) { this.buttonName = buttonName; return this; }
/*    */ 
/*    */   
/* 14 */   public Runnable getRunnable() { return this.runnable; } public String getButtonName() {
/* 15 */     return this.buttonName;
/*    */   }
/*    */   public ButtonSetting(String name, String description) {
/* 18 */     super(name, description);
/*    */   }
/*    */   
/*    */   public ButtonSetting visible(Supplier<Boolean> visible) {
/* 22 */     setVisible(visible);
/* 23 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public void reset() {}
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\ButtonSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */