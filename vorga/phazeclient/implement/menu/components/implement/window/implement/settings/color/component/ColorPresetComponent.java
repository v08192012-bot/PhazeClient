/*    */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*    */ 
/*    */ public class ColorPresetComponent extends AbstractComponent {
/*    */   private final ColorSetting setting;
/*    */   private float windowHeight;
/*    */   
/* 10 */   public ColorSetting getSetting() { return this.setting; } public float getWindowHeight() {
/* 11 */     return this.windowHeight;
/*    */   }
/*    */   public ColorPresetComponent(ColorSetting setting) {
/* 14 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 20 */     this.windowHeight = 132.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 25 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\component\ColorPresetComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */