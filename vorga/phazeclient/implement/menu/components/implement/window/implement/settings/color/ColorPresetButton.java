/*    */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color;
/*    */ import net.minecraft.class_332;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*    */ 
/*    */ public class ColorPresetButton extends AbstractComponent {
/*    */   public ColorPresetButton(ColorSetting setting, int color) {
/* 10 */     this.setting = setting; this.color = color;
/*    */   }
/*    */   
/*    */   private final ColorSetting setting;
/*    */   private final int color;
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 17 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.x, this.y, 8.0D, 8.0D).round(2.0F).color(this.color).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 22 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 8.0D, 8.0D) && button == 0) {
/* 23 */       this.setting.setColor(this.color);
/*    */     }
/* 25 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\ColorPresetButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */