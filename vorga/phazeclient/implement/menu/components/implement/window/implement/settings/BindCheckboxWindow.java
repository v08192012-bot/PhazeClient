/*    */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.implement.menu.components.implement.window.implement.AbstractBindWindow;
/*    */ 
/*    */ public class BindCheckboxWindow extends AbstractBindWindow {
/*    */   public BindCheckboxWindow(BooleanSetting setting) {
/*  7 */     this.setting = setting;
/*    */   }
/*    */   
/*    */   private final BooleanSetting setting;
/*    */   
/*    */   protected int getKey() {
/* 13 */     return this.setting.getKey();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setKey(int key) {
/* 18 */     this.setting.setKey(key);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getType() {
/* 23 */     return this.setting.getType();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setType(int type) {
/* 28 */     this.setting.setType(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\BindCheckboxWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */