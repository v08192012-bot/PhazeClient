/*    */ package vorga.phazeclient.implement.menu.components.implement.window.implement.module;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.implement.menu.components.implement.window.implement.AbstractBindWindow;
/*    */ 
/*    */ public class ModuleBindWindow extends AbstractBindWindow {
/*    */   public ModuleBindWindow(Module module) {
/*  7 */     this.module = module;
/*    */   }
/*    */   private final Module module;
/*    */   
/*    */   public Module getModule() {
/* 12 */     return this.module;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getKey() {
/* 17 */     return this.module.getKey();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setKey(int key) {
/* 22 */     this.module.setKey(key);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getType() {
/* 27 */     return this.module.getType();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setType(int type) {
/* 32 */     this.module.setType(type);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\module\ModuleBindWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */