/*    */ package vorga.phazeclient.implement.menu.components.implement.other;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_7833;
/*    */ import vorga.phazeclient.api.system.animation.Animation;
/*    */ import vorga.phazeclient.api.system.animation.Direction;
/*    */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*    */ 
/*    */ public class SettingComponent extends AbstractComponent {
/*    */   private Runnable runnable;
/*    */   
/* 15 */   public SettingComponent setRunnable(Runnable runnable) { this.runnable = runnable; return this; } public SettingComponent setWindowOpen(boolean windowOpen) { this.windowOpen = windowOpen; return this; }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean windowOpen = false;
/*    */   
/* 21 */   private final Animation rotationAnimation = (new EaseInOutAnimation()).setMs(600).setValue(1.0D);
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 25 */     float buttonSize = 11.0F;
/* 26 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, buttonSize, buttonSize);
/* 27 */     if (hovered) {
/* 28 */       CursorManager.requestHand();
/*    */     }
/* 30 */     if (this.windowOpen) {
/* 31 */       this.rotationAnimation.setDirection(Direction.FORWARDS);
/*    */     } else {
/* 33 */       this.rotationAnimation.setDirection(Direction.BACKWARDS);
/*    */     } 
/*    */     
/* 36 */     float rotationProgress = this.rotationAnimation.getOutputFloat();
/* 37 */     float rotationAngle = 90.0F + rotationProgress * 360.0F;
/*    */     
/* 39 */     float centerX = this.x + buttonSize / 2.0F;
/* 40 */     float centerY = this.y + buttonSize / 2.0F;
/*    */     
/* 42 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.x, this.y, buttonSize, buttonSize)
/* 43 */         .round(2.0F)
/* 44 */         .thickness(1.0F)
/* 45 */         .outlineColor(MenuStyle.BORDER)
/* 46 */         .color(MenuStyle.PANEL_CHIP)
/* 47 */         .build());
/*    */     
/* 49 */     context.method_51448().method_22903();
/* 50 */     context.method_51448().method_46416(centerX, centerY, 0.0F);
/* 51 */     context.method_51448().method_22907(class_7833.field_40718.rotationDegrees(rotationAngle));
/* 52 */     context.method_51448().method_46416(-centerX, -centerY, 0.0F);
/*    */     
/* 54 */     image.setTexture("textures/settings.png").render(
/* 55 */         ShapeProperties.create(context.method_51448(), (this.x + 2.0F), (this.y + 2.0F), 7.0D, 7.0D)
/* 56 */         .color(MenuStyle.TEXT_MUTED)
/* 57 */         .build());
/*    */ 
/*    */     
/* 60 */     context.method_51448().method_22909();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 66 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 11.0D, 11.0D) && button == 0) {
/* 67 */       playButtonClickSound();
/* 68 */       this.runnable.run();
/* 69 */       return true;
/*    */     } 
/* 71 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\SettingComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */