/*    */ package vorga.phazeclient.implement.menu.components.implement.other;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_4587;
/*    */ import org.joml.Matrix4f;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ 
/*    */ public class ButtonComponent extends AbstractComponent {
/*    */   private static final float TEXT_SIZE = 6.7F;
/*    */   private static final float BUTTON_HEIGHT = 14.0F;
/*    */   
/* 15 */   public ButtonComponent setText(String text) { this.text = text; return this; } private String text; private Runnable runnable; public ButtonComponent setRunnable(Runnable runnable) { this.runnable = runnable; return this; } public ButtonComponent setColor(int color) { this.color = color; return this; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   private int color = MenuStyle.CHIP_ACTIVE;
/*    */   
/*    */   public float measureWidth() {
/* 26 */     String resolved = (this.text == null) ? "" : this.text;
/* 27 */     return MsdfFonts.bold().getWidth(resolved, 6.7F) + 18.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 32 */     class_4587 matrix = context.method_51448();
/* 33 */     String resolved = (this.text == null) ? "" : this.text;
/* 34 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/* 35 */     if (hovered) {
/* 36 */       CursorManager.requestHand();
/*    */     }
/*    */     
/* 39 */     this.width = measureWidth();
/* 40 */     this.height = 14.0F;
/*    */     
/* 42 */     int outline = applyGlobalAlpha(MenuStyle.mix(MenuStyle.BORDER, -1, hovered ? 0.12F : 0.04F));
/* 43 */     int fill = applyGlobalAlpha(MenuStyle.mix(this.color, -1, hovered ? 0.08F : 0.0F));
/* 44 */     int textColor = applyGlobalAlpha(MenuStyle.TEXT_PRIMARY);
/* 45 */     float scale = 0.9F + this.globalAlpha * 0.1F;
/* 46 */     float centerX = this.x + this.width / 2.0F;
/* 47 */     float centerY = this.y + this.height / 2.0F;
/*    */     
/* 49 */     matrix.method_22903();
/* 50 */     matrix.method_46416(centerX, centerY, 0.0F);
/* 51 */     matrix.method_22905(scale, scale, 1.0F);
/* 52 */     matrix.method_46416(-centerX, -centerY, 0.0F);
/* 53 */     Matrix4f positionMatrix = matrix.method_23760().method_23761();
/*    */     
/* 55 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/* 56 */         .round(3.4F)
/* 57 */         .thickness(1.1F)
/* 58 */         .softness(1.0F)
/* 59 */         .outlineColor(outline)
/* 60 */         .color(fill)
/* 61 */         .build());
/*    */     
/* 63 */     MsdfRenderer.renderText(
/* 64 */         MsdfFonts.bold(), resolved, 6.7F, textColor, positionMatrix, 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 69 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), resolved, 6.7F, this.x, this.width), 
/* 70 */         MenuStyle.centerMsdfTextY(6.7F, this.y, this.height), 0.0F);
/*    */ 
/*    */     
/* 73 */     matrix.method_22909();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 78 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height) && button == 0) {
/* 79 */       playButtonClickSound();
/* 80 */       this.runnable.run();
/* 81 */       return true;
/*    */     } 
/* 83 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\ButtonComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */