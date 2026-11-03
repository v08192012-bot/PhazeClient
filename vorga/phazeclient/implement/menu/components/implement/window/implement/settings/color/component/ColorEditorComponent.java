/*    */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*    */ 
/*    */ public class ColorEditorComponent extends AbstractComponent {
/*    */   public ColorEditorComponent(ColorSetting setting) {
/*  9 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   private final ColorSetting setting;
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {}
/*    */ 
/*    */   
/*    */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 20 */     return super.mouseScrolled(mouseX, mouseY, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 25 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 30 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 35 */     return super.mouseReleased(mouseX, mouseY, button);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean charTyped(char chr, int modifiers) {
/* 40 */     return super.charTyped(chr, modifiers);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 45 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\component\ColorEditorComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */