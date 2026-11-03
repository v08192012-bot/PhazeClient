/*    */ package vorga.phazeclient.implement.menu.components.implement.settings.select;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_4587;
/*    */ import org.joml.Vector4f;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ import vorga.phazeclient.api.system.animation.Animation;
/*    */ import vorga.phazeclient.api.system.animation.Direction;
/*    */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*    */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*    */ 
/*    */ public class SelectedButton extends AbstractComponent {
/*    */   private static final float ITEM_TEXT_SIZE = 5.7F;
/*    */   private final SelectSetting setting;
/*    */   private final String text;
/*    */   private float alpha;
/*    */   
/*    */   public String getText() {
/* 25 */     return this.text;
/*    */   }
/*    */   public SelectedButton setAlpha(float alpha) {
/* 28 */     this.alpha = alpha; return this;
/*    */   }
/*    */   
/*    */   public boolean isHovered() {
/* 32 */     return this.isHovered;
/*    */   }
/*    */   private boolean isHovered = false;
/* 35 */   private final Animation alphaAnimation = (new DecelerateAnimation())
/* 36 */     .setMs(300).setValue(0.5D);
/*    */   
/*    */   public SelectedButton(SelectSetting setting, String text) {
/* 39 */     this.setting = setting;
/* 40 */     this.text = text;
/*    */     
/* 42 */     this.alphaAnimation.setDirection(Direction.BACKWARDS);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 48 */     class_4587 matrices = context.method_51448();
/* 49 */     this.isHovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*    */     
/* 51 */     this.alphaAnimation.setDirection(this.setting.getSelected().contains(this.text) ? Direction.FORWARDS : Direction.BACKWARDS);
/*    */     
/* 53 */     float opacity = this.alphaAnimation.getOutputFloat();
/* 54 */     int selectedOpacity = MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, opacity * this.alpha);
/*    */     
/* 56 */     if (!this.alphaAnimation.isFinished(Direction.BACKWARDS)) {
/* 57 */       rectangle.render(ShapeProperties.create(matrices, this.x, this.y, this.width, (this.height + 0.15F))
/* 58 */           .round(getRound(this.setting.getList(), this.text))
/* 59 */           .softness(1.1F)
/* 60 */           .color(selectedOpacity)
/* 61 */           .build());
/*    */     }
/* 63 */     MsdfRenderer.renderText(
/* 64 */         MsdfFonts.bold(), this.text, 5.7F, 
/*    */ 
/*    */         
/* 67 */         MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.alpha), matrices
/* 68 */         .method_23760().method_23761(), this.x + 4.0F, 
/*    */         
/* 70 */         MenuStyle.centerMsdfTextY(5.7F, this.y, this.height), 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 78 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height) && button == 0) {
/* 79 */       playButtonClickSound();
/* 80 */       this.setting.setSelected(this.text);
/* 81 */       return true;
/*    */     } 
/* 83 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderTargetHudTooltip(class_332 context) {}
/*    */ 
/*    */   
/*    */   private boolean isTargetHudStyleSetting() {
/* 91 */     return false;
/*    */   }
/*    */   
/*    */   public static Vector4f getRound(List<String> list, String text) {
/* 95 */     if (list.size() == 1) return new Vector4f(4.0F); 
/* 96 */     if (((String)list.getLast()).contains(text)) return new Vector4f(0.0F, 4.0F, 0.0F, 4.0F); 
/* 97 */     if (((String)list.getFirst()).contains(text)) return new Vector4f(4.0F, 0.0F, 4.0F, 0.0F); 
/* 98 */     return new Vector4f(0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\select\SelectedButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */