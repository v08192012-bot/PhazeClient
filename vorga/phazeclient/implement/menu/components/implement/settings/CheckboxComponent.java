/*    */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*    */ import net.minecraft.class_332;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.system.animation.Animation;
/*    */ import vorga.phazeclient.api.system.animation.Direction;
/*    */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*    */ import vorga.phazeclient.api.system.font.FontRenderer;
/*    */ import vorga.phazeclient.api.system.font.Fonts;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ import vorga.phazeclient.implement.menu.components.implement.other.CheckComponent;
/*    */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*    */ 
/*    */ public class CheckboxComponent extends AbstractSettingComponent {
/* 16 */   private final CheckComponent checkComponent = new CheckComponent();
/*    */   private final BooleanSetting setting;
/* 18 */   private final Animation hoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/* 19 */   private final Animation activeAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/*    */   
/*    */   public CheckboxComponent(BooleanSetting setting) {
/* 22 */     super((Setting)setting);
/* 23 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 28 */     updateVisibilityAnimation();
/* 29 */     MsdfGuiFontRenderer labelFont = GuiFonts.getSize(14, Fonts.Type.INTER_BOLD);
/*    */     
/* 31 */     boolean isModified = this.setting.isModified();
/* 32 */     float textOffset = animatedTextOffset(isModified);
/*    */     
/* 34 */     this.height = 23.5F;
/*    */     
/* 36 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/* 37 */     this.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 38 */     float hoverProgress = this.hoverAnimation.getOutputFloat();
/* 39 */     this.activeAnimation.setDirection(this.setting.isValue() ? Direction.FORWARDS : Direction.BACKWARDS);
/* 40 */     float activeProgress = this.activeAnimation.getOutputFloat();
/*    */     
/* 42 */     renderSettingCard(context, activeProgress, hoverProgress);
/*    */     
/* 44 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(context.method_51448());
/*    */     
/* 46 */     float textX = this.x + 10.0F + textOffset;
/* 47 */     String text = this.label.singleLine(this.setting.getLocalizedName());
/* 48 */     float textY = centeredTextY((FontRenderer)labelFont, text);
/* 49 */     int textColor = MenuStyle.mix(primaryText(), MenuStyle.withAlpha(-1, this.currentAlpha), hoverProgress * 0.16F);
/* 50 */     this.label.render(context.method_51448(), (FontRenderer)labelFont, text, textX, textY, this.x + this.width - 37.0F, this.y, this.height, textColor);
/*    */     
/* 52 */     updateLabelHover(mouseX, mouseY);
/*    */     
/* 54 */     ((CheckComponent)this.checkComponent.position(this.x + this.width - 29.0F, this.y + (this.height - CheckComponent.getToggleHeight()) / 2.0F))
/* 55 */       .setRunnable(() -> this.setting.setValue(!this.setting.isValue()))
/* 56 */       .setState(this.setting.isValue())
/* 57 */       .setAlpha(this.currentAlpha)
/* 58 */       .render(context, mouseX, mouseY, delta);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 63 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 64 */       this.setting.reset();
/* 65 */       return true;
/*    */     } 
/*    */     
/* 68 */     if (this.checkComponent.mouseClicked(mouseX, mouseY, button)) {
/* 69 */       return true;
/*    */     }
/* 71 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHover(double mouseX, double mouseY) {
/* 76 */     if (!this.setting.isVisible()) {
/* 77 */       return false;
/*    */     }
/* 79 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\CheckboxComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */