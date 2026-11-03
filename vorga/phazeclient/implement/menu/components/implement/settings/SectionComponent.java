/*    */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.class_332;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ 
/*    */ public class SectionComponent extends AbstractSettingComponent {
/*    */   public SectionComponent(SectionSetting setting) {
/* 12 */     super((Setting)setting);
/* 13 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 18 */     updateVisibilityAnimation();
/* 19 */     this.height = 12.0F;
/*    */     
/* 21 */     float fontSize = 5.5F;
/* 22 */     String text = this.label.singleLine(this.setting.getLocalizedName().toUpperCase(Locale.ROOT));
/* 23 */     float textWidth = MsdfFonts.bold().getWidth(text, fontSize);
/* 24 */     float centerX = this.x + Math.max(0.0F, (this.width - textWidth) / 2.0F);
/* 25 */     int color = MenuStyle.withAlpha(MenuStyle.TEXT_MUTED, this.currentAlpha * 0.7F);
/* 26 */     this.label.renderMsdf(context.method_51448(), text, fontSize, centerX, this.y + 3.25F, this.x + this.width, this.y, this.height, color);
/*    */     
/* 28 */     updateLabelHover(mouseX, mouseY);
/*    */   }
/*    */   private final SectionSetting setting;
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHover(double mouseX, double mouseY) {
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\SectionComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */