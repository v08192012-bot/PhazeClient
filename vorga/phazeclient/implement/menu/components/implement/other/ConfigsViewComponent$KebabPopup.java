/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.config.ConfigManager;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class KebabPopup
/*     */ {
/*     */   private static final float ITEM_H = 16.0F;
/*     */   private static final float WIDTH = 100.0F;
/*     */   private static final float TEXT_SIZE = 6.5F;
/* 756 */   private static final String[] ITEMS = new String[] { "kebab.share", "kebab.rename", "kebab.delete" };
/*     */   
/*     */   private final String configName;
/*     */   private final float anchorX;
/*     */   private final float anchorY;
/*     */   private final ConfigsViewComponent owner;
/*     */   private final Animation fade;
/*     */   private final Animation[] itemHover;
/*     */   private boolean closing = false;
/*     */   
/*     */   KebabPopup(String configName, float anchorX, float anchorY, ConfigsViewComponent owner) {
/* 767 */     this.configName = configName;
/* 768 */     this.anchorX = anchorX;
/* 769 */     this.anchorY = anchorY;
/* 770 */     this.owner = owner;
/* 771 */     this.fade = (new DecelerateAnimation()).setMs(140).setValue(1.0D);
/* 772 */     this.fade.setDirectionAndFinish(Direction.BACKWARDS);
/* 773 */     this.fade.setDirection(Direction.FORWARDS);
/* 774 */     this.itemHover = new Animation[ITEMS.length];
/* 775 */     for (int i = 0; i < ITEMS.length; i++) {
/* 776 */       this.itemHover[i] = (new DecelerateAnimation()).setMs(140).setValue(1.0D);
/* 777 */       this.itemHover[i].setDirectionAndFinish(Direction.BACKWARDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void close() {
/* 785 */     this.closing = true;
/* 786 */     this.fade.setDirection(Direction.BACKWARDS);
/* 787 */     for (Animation anim : this.itemHover) {
/* 788 */       anim.setDirection(Direction.BACKWARDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInteractive() {
/* 798 */     return !this.closing;
/*     */   }
/*     */   
/*     */   boolean isFullyClosed() {
/* 802 */     return (this.closing && this.fade.getOutputFloat() <= 0.001F);
/*     */   }
/*     */   
/*     */   void render(class_332 context, int mouseX, int mouseY, float menuFadeAlpha) {
/* 806 */     class_4587 matrix = context.method_51448();
/* 807 */     float a = this.fade.getOutputFloat() * menuFadeAlpha;
/* 808 */     if (a <= 0.001F) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 814 */     float popupX = this.anchorX - 100.0F;
/* 815 */     float popupY = this.anchorY + 4.0F;
/* 816 */     float popupH = ITEMS.length * 16.0F;
/* 817 */     float menuBottom = this.owner.y + this.owner.height - 8.0F;
/* 818 */     if (popupY + popupH > menuBottom) {
/* 819 */       popupY = this.anchorY - popupH - 12.0F;
/*     */     }
/* 821 */     float menuLeft = this.owner.x + 6.0F;
/* 822 */     if (popupX < menuLeft) popupX = menuLeft;
/*     */     
/* 824 */     int fill = MenuStyle.mix(MenuStyle.PANEL_CONTENT, 255, 0.1F);
/* 825 */     QuickImports.rectangle.render(ShapeProperties.create(matrix, popupX, popupY, 100.0D, popupH)
/* 826 */         .round(5.0F).softness(1.0F).thickness(1.2F)
/* 827 */         .outlineColor(MenuStyle.withAlpha(MenuStyle.BORDER, a))
/* 828 */         .color(MenuStyle.withAlpha(fill, a * 0.97F))
/* 829 */         .build());
/*     */     
/* 831 */     for (int i = 0; i < ITEMS.length; i++) {
/* 832 */       float iy = popupY + i * 16.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 838 */       boolean hover = (!this.closing && MathUtil.isHovered(mouseX, mouseY, popupX, iy, 100.0D, 16.0D));
/* 839 */       this.itemHover[i].setDirection(hover ? Direction.FORWARDS : Direction.BACKWARDS);
/* 840 */       float hoverAlpha = this.itemHover[i].getOutputFloat();
/*     */       
/* 842 */       if (hoverAlpha > 0.001F) {
/* 843 */         int hoverFill = MenuStyle.mix(MenuStyle.CARD_OPTIONS, MenuStyle.PANEL_CHIP, 0.35F);
/* 844 */         QuickImports.rectangle.render(ShapeProperties.create(matrix, popupX, iy, 100.0D, 16.0D)
/* 845 */             .round(3.0F)
/* 846 */             .color(MenuStyle.withAlpha(hoverFill, a * 0.85F * hoverAlpha))
/* 847 */             .build());
/*     */       } 
/* 849 */       int baseTextColor = MenuStyle.TEXT_PRIMARY;
/*     */ 
/*     */       
/* 852 */       int textColor = (i == 2) ? MenuStyle.mix(baseTextColor, -2076592, hoverAlpha) : baseTextColor;
/* 853 */       MsdfRenderer.renderText(
/* 854 */           MsdfFonts.bold(), Lang.t(ITEMS[i]), 6.5F, 
/* 855 */           MenuStyle.withAlpha(textColor, a), matrix
/* 856 */           .method_23760().method_23761(), popupX + 10.0F, 
/*     */           
/* 858 */           MenuStyle.centerMsdfTextY(6.5F, iy, 16.0F), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean handleClick(double mouseX, double mouseY) {
/*     */     ConfigManager mgr;
/* 865 */     float popupX = this.anchorX - 100.0F;
/* 866 */     float popupY = this.anchorY + 4.0F;
/* 867 */     float popupH = ITEMS.length * 16.0F;
/* 868 */     float menuBottom = this.owner.y + this.owner.height - 8.0F;
/* 869 */     if (popupY + popupH > menuBottom) popupY = this.anchorY - popupH - 12.0F; 
/* 870 */     float menuLeft = this.owner.x + 6.0F;
/* 871 */     if (popupX < menuLeft) popupX = menuLeft;
/*     */     
/* 873 */     if (!MathUtil.isHovered(mouseX, mouseY, popupX, popupY, 100.0D, popupH)) return false; 
/* 874 */     int idx = (int)((mouseY - popupY) / 16.0D);
/* 875 */     if (idx < 0 || idx >= ITEMS.length) return false;
/*     */     
/* 877 */     switch (idx) { case 0:
/* 878 */         MenuScreen.INSTANCE.openConfigShareModal(this.configName); break;
/* 879 */       case 1: Objects.requireNonNull(this.owner); MenuScreen.INSTANCE.openConfigRenameModal(this.configName, this.owner::refreshMetadataCaches); break;
/*     */       case 2:
/* 881 */         mgr = ConfigManager.getInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 887 */         mgr.deleteConfig(this.configName);
/* 888 */         this.owner.refreshMetadataCaches();
/*     */         break; }
/*     */     
/* 891 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\ConfigsViewComponent$KebabPopup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */