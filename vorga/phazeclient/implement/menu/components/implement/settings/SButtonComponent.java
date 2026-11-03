/*    */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*    */ import net.minecraft.class_332;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ButtonSetting;
/*    */ import vorga.phazeclient.api.system.font.FontRenderer;
/*    */ import vorga.phazeclient.api.system.font.Fonts;
/*    */ import vorga.phazeclient.base.util.Lang;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.components.implement.other.ButtonComponent;
/*    */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*    */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*    */ 
/*    */ public class SButtonComponent extends AbstractSettingComponent {
/* 14 */   private final ButtonComponent buttonComponent = new ButtonComponent();
/*    */   private final ButtonSetting setting;
/*    */   
/*    */   public SButtonComponent(ButtonSetting setting) {
/* 18 */     super((Setting)setting);
/* 19 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 24 */     updateVisibilityAnimation();
/* 25 */     MsdfGuiFontRenderer labelFont = GuiFonts.getSize(14, Fonts.Type.INTER_BOLD);
/*    */     
/* 27 */     boolean isModified = this.setting.isModified();
/* 28 */     float textOffset = animatedTextOffset(isModified);
/*    */     
/* 30 */     this.height = 23.5F;
/* 31 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*    */     
/* 33 */     renderSettingCard(context, 0.0F, hoverProgress);
/*    */     
/* 35 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(context.method_51448());
/*    */     
/* 37 */     String buttonText = this.setting.getButtonName();
/* 38 */     if (buttonText == null || buttonText.isBlank()) {
/* 39 */       buttonText = LocalizationManager.getInstance().get("module.setting.button_click");
/*    */     } else {
/* 41 */       buttonText = Lang.translate(buttonText);
/*    */     } 
/*    */ 
/*    */     
/* 45 */     ButtonComponent lunarButton = this.buttonComponent.setText(buttonText).setRunnable(this.setting.getRunnable());
/* 46 */     float buttonWidth = lunarButton.measureWidth();
/*    */     
/* 48 */     String text = this.label.singleLine(this.setting.getLocalizedName());
/* 49 */     this.label.render(context.method_51448(), (FontRenderer)labelFont, text, this.x + 10.0F + textOffset, centeredTextY((FontRenderer)labelFont, text), this.x + this.width - 17.0F - buttonWidth, this.y, this.height, 
/* 50 */         primaryText());
/* 51 */     updateLabelHover(mouseX, mouseY);
/*    */     
/* 53 */     lunarButton.position(this.x + this.width - 9.0F - buttonWidth, this.y + this.height / 2.0F - 7.0F);
/* 54 */     lunarButton.globalAlpha = this.currentAlpha;
/* 55 */     lunarButton.render(context, mouseX, mouseY, delta);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 61 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 62 */       playButtonClickSound();
/* 63 */       this.setting.reset();
/* 64 */       return true;
/*    */     } 
/*    */     
/* 67 */     if (this.buttonComponent.mouseClicked(mouseX, mouseY, button)) {
/* 68 */       return true;
/*    */     }
/* 70 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHover(double mouseX, double mouseY) {
/* 75 */     if (!this.setting.isVisible()) {
/* 76 */       return false;
/*    */     }
/* 78 */     float buttonWidth = this.buttonComponent.measureWidth();
/* 79 */     if (MathUtil.isHovered(mouseX, mouseY, (this.x + this.width - 9.0F - buttonWidth), (this.y + this.height / 2.0F - 7.0F), buttonWidth, this.buttonComponent.height)) {
/* 80 */       return true;
/*    */     }
/* 82 */     return MathUtil.isHovered(mouseX, mouseY, (this.x + 10.0F), (this.y + 6.0F), (this.width - buttonWidth - 20.0F), (this.height - 12.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\SButtonComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */