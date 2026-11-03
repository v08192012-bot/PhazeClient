/*    */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_4587;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*    */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*    */ 
/*    */ public class HueComponent extends AbstractComponent {
/*    */   public HueComponent(ColorSetting setting) {
/* 16 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   private final ColorSetting setting;
/*    */   
/*    */   private boolean hueDragging;
/*    */   private float X;
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 26 */     class_4587 matrix = context.method_51448();
/*    */     
/* 28 */     this.X = this.x + 6.0F;
/* 29 */     this.Y = this.y + 10.5F;
/* 30 */     this.W = 138.0F;
/* 31 */     this.H = 50.0F;
/* 32 */     boolean hoveredPicker = MathUtil.isHovered(mouseX, mouseY, this.X, this.Y, this.W, this.H);
/* 33 */     if (this.hueDragging) {
/* 34 */       CursorManager.requestMove();
/* 35 */     } else if (hoveredPicker) {
/* 36 */       CursorManager.requestHand();
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     int[] color = { applyGlobalAlpha(-16777216), applyGlobalAlpha(-1), applyGlobalAlpha(-16777216), applyGlobalAlpha(0xFF000000 | Color.HSBtoRGB(this.setting.getHue(), 1.0F, 1.0F) & 0xFFFFFF) };
/*    */ 
/*    */     
/* 46 */     rectangle.render(ShapeProperties.create(matrix, this.X, this.Y, this.W, this.H)
/* 47 */         .round(2.0F)
/* 48 */         .thickness(2.0F)
/* 49 */         .outlineColor(applyGlobalAlpha(MenuStyle.BORDER_LIGHT))
/* 50 */         .color(color)
/* 51 */         .build());
/*    */ 
/*    */     
/* 54 */     float clampedX = class_3532.method_15363(this.X + this.W * this.setting.getSaturation(), this.X, this.X + this.W - 5.0F);
/* 55 */     float clampedY = class_3532.method_15363(this.Y + this.H * (1.0F - this.setting.getBrightness()), this.Y, this.Y + this.H - 5.0F);
/*    */     
/* 57 */     rectangle.render(ShapeProperties.create(matrix, clampedX, clampedY, 5.0D, 5.0D)
/* 58 */         .round(2.5F)
/* 59 */         .softness(1.0F)
/* 60 */         .thickness(3.0F)
/* 61 */         .color(16777215)
/* 62 */         .outlineColor(applyGlobalAlpha(-1))
/* 63 */         .build());
/*    */ 
/*    */     
/* 66 */     float min = class_3532.method_15363((mouseX - this.X) / this.W, 0.0F, 1.0F);
/*    */     
/* 68 */     if (this.hueDragging) {
/* 69 */       this.setting.setBrightness(class_3532.method_15363(1.0F - (mouseY - this.Y) / this.H, 0.0F, 1.0F));
/* 70 */       this.setting.setSaturation(min);
/*    */     } 
/*    */   }
/*    */   private float Y; private float W;
/*    */   private float H;
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 77 */     this.hueDragging = (button == 0 && MathUtil.isHovered(mouseX, mouseY, this.X, this.Y, this.W, this.H));
/* 78 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 84 */     this.hueDragging = false;
/* 85 */     return super.mouseReleased(mouseX, mouseY, button);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\component\HueComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */