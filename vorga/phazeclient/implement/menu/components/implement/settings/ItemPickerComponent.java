/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ItemPickerSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.ItemPickerScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.CheckComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.itempicker.ItemPickerColorWindow;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ 
/*     */ public final class ItemPickerComponent
/*     */   extends AbstractSettingComponent
/*     */ {
/*     */   private static final float ICON_SIZE = 16.0F;
/*     */   private static final float EMPTY_PLUS_SIZE = 12.4F;
/*     */   private static final float LEFT_PADDING = 10.0F;
/*  36 */   private final CheckComponent checkComponent = new CheckComponent(); private static final float TEXT_GAP = 7.0F; private static final float COLOR_SIZE = 12.0F; private static final float COLOR_RIGHT = 10.0F; private static final float TOGGLE_GAP = 9.0F;
/*  37 */   private final Animation enabledAnimation = (new DecelerateAnimation()).setMs(190).setValue(1.0D);
/*     */   private final ItemPickerSetting setting;
/*     */   
/*     */   public ItemPickerComponent(ItemPickerSetting setting) {
/*  41 */     super((Setting)setting);
/*  42 */     this.setting = setting;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  47 */     updateVisibilityAnimation();
/*     */     
/*  49 */     MsdfGuiFontRenderer msdfGuiFontRenderer = GuiFonts.getSize(13, Fonts.Type.INTER_BOLD);
/*  50 */     boolean isModified = this.setting.isModified();
/*  51 */     float textOffset = animatedTextOffset(isModified);
/*  52 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 23.5D);
/*  53 */     float hoverProgress = animatedCardHover(hovered);
/*  54 */     this.enabledAnimation.setDirection(this.setting.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
/*  55 */     float enabledProgress = this.enabledAnimation.getOutputFloat();
/*  56 */     float activeProgress = 0.08F + enabledProgress * 0.76F;
/*     */     
/*  58 */     this.height = 23.5F;
/*  59 */     renderSettingCard(context, activeProgress, hoverProgress);
/*     */     
/*  61 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(context.method_51448());
/*     */     
/*  63 */     float colorX = this.x + this.width - 10.0F - 12.0F;
/*  64 */     float colorY = this.y + 11.75F - 6.0F;
/*  65 */     float toggleX = colorX - 9.0F - CheckComponent.getToggleWidth();
/*  66 */     float toggleY = this.y + 11.75F - CheckComponent.getToggleHeight() / 2.0F;
/*     */     
/*  68 */     float iconX = this.x + 10.0F + textOffset;
/*  69 */     float iconY = this.y + 11.75F - 8.0F;
/*  70 */     float textX = iconX + 16.0F + 7.0F;
/*     */     
/*  72 */     class_1799 previewStack = this.setting.createPreviewStack();
/*  73 */     if (!previewStack.method_7960()) {
/*  74 */       renderPreviewItem(context, previewStack, iconX, iconY);
/*     */     } else {
/*  76 */       String plus = "+";
/*  77 */       MsdfRenderer.renderText(
/*  78 */           MsdfFonts.bold(), plus, 12.4F, 
/*     */ 
/*     */           
/*  81 */           MenuStyle.withAlpha(-1, this.currentAlpha), context
/*  82 */           .method_51448().method_23760().method_23761(), 
/*  83 */           MenuStyle.centerMsdfTextX(MsdfFonts.bold(), plus, 12.4F, iconX, 16.0F) + 3.0F, 
/*  84 */           MenuStyle.centerMsdfTextY(12.4F, iconY, 16.0F) + 0.15F, 0.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     String title = this.label.singleLine(this.setting.getRowTitle());
/*  90 */     int baseTitleColor = MenuStyle.mix(mutedText(), primaryText(), 0.24F + enabledProgress * 0.52F);
/*  91 */     int titleColor = MenuStyle.mix(baseTitleColor, 
/*     */         
/*  93 */         MenuStyle.withAlpha(-1, this.currentAlpha), enabledProgress * 0.44F + hoverProgress * 0.08F);
/*     */ 
/*     */     
/*  96 */     this.label.render(context.method_51448(), (FontRenderer)msdfGuiFontRenderer, title, textX, centeredTextY((FontRenderer)msdfGuiFontRenderer, title, this.y, 23.5F), toggleX - 8.0F, this.y, 23.5F, titleColor);
/*     */     
/*  98 */     updateLabelHover(mouseX, mouseY);
/*     */     
/* 100 */     ((CheckComponent)this.checkComponent.position(toggleX, toggleY))
/* 101 */       .setRunnable(() -> this.setting.setEnabled(!this.setting.isEnabled()))
/* 102 */       .setState(this.setting.isEnabled())
/* 103 */       .setAlpha(this.currentAlpha)
/* 104 */       .render(context, mouseX, mouseY, delta);
/*     */     
/* 106 */     int colorFill = MenuStyle.withAlpha(0xFF000000 | this.setting.getHighlightColor() & 0xFFFFFF, this.currentAlpha);
/* 107 */     int colorOutline = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.settingOutline(true), -1, hovered ? 0.12F : 0.0F), this.currentAlpha);
/* 108 */     rectangle.render(ShapeProperties.create(context.method_51448(), colorX, colorY, 12.0D, 12.0D)
/* 109 */         .round(3.2F)
/* 110 */         .thickness(1.05F)
/* 111 */         .outlineColor(colorOutline)
/* 112 */         .color(colorFill)
/* 113 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 118 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 119 */       playButtonClickSound();
/* 120 */       this.setting.reset();
/* 121 */       return true;
/*     */     } 
/*     */     
/* 124 */     if (this.checkComponent.mouseClicked(mouseX, mouseY, button)) {
/* 125 */       return true;
/*     */     }
/*     */     
/* 128 */     float colorX = this.x + this.width - 10.0F - 12.0F;
/* 129 */     float colorY = this.y + 11.75F - 6.0F;
/* 130 */     if (button == 0 && MathUtil.isHovered(mouseX, mouseY, colorX, colorY, 12.0D, 12.0D)) {
/* 131 */       playButtonClickSound();
/* 132 */       toggleColorWindow(colorX, colorY);
/* 133 */       return true;
/*     */     } 
/*     */     
/* 136 */     if (button == 0 && MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 23.5D)) {
/* 137 */       playButtonClickSound();
/* 138 */       if (this.setting.isCustomPicker()) {
/* 139 */         class_310 client = class_310.method_1551();
/* 140 */         if (client != null) {
/* 141 */           client.method_1507((class_437)new ItemPickerScreen(client.field_1755, this.setting));
/*     */         }
/*     */       } else {
/* 144 */         this.setting.setEnabled(!this.setting.isEnabled());
/*     */       } 
/* 146 */       return true;
/*     */     } 
/*     */     
/* 149 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 154 */     if (!this.setting.isVisible()) {
/* 155 */       return false;
/*     */     }
/* 157 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 23.5D);
/*     */   }
/*     */   
/*     */   private void toggleColorWindow(float colorX, float colorY) {
/* 161 */     AbstractWindow existingWindow = null;
/*     */     
/* 163 */     for (AbstractWindow window : windowManager.getWindows()) {
/* 164 */       if (window instanceof ItemPickerColorWindow) { ItemPickerColorWindow pickerWindow = (ItemPickerColorWindow)window; if (pickerWindow.getSetting() == this.setting) {
/* 165 */           existingWindow = window;
/*     */           break;
/*     */         }  }
/*     */     
/*     */     } 
/* 170 */     if (existingWindow != null) {
/* 171 */       windowManager.delete(existingWindow);
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     for (AbstractWindow window : windowManager.getWindows()) {
/* 176 */       if (window instanceof ItemPickerColorWindow) {
/* 177 */         windowManager.delete(window);
/*     */       }
/*     */     } 
/*     */     
/* 181 */     int windowWidth = Math.round(126.0F);
/* 182 */     int windowHeight = Math.round(123.0F);
/* 183 */     int windowX = MenuScreen.INSTANCE.clampOverlayX((colorX + 12.0F + 8.0F), windowWidth);
/* 184 */     int windowY = MenuScreen.INSTANCE.clampOverlayY((this.y + 11.75F - windowHeight / 2.0F), windowHeight);
/*     */     
/* 186 */     windowManager.add((new ItemPickerColorWindow(this.setting))
/* 187 */         .position(windowX, windowY)
/* 188 */         .size(126.0F, 123.0F)
/* 189 */         .draggable(false));
/*     */   }
/*     */   
/*     */   private void renderPreviewItem(class_332 context, class_1799 stack, float iconX, float iconY) {
/* 193 */     class_4587 matrices = context.method_51448();
/* 194 */     float iconScale = 0.86F + this.currentAlpha * 0.14F;
/* 195 */     float center = 8.0F;
/*     */     
/* 197 */     matrices.method_22903();
/* 198 */     matrices.method_46416(iconX + center, iconY + center, 0.0F);
/* 199 */     matrices.method_22905(iconScale, iconScale, 1.0F);
/* 200 */     matrices.method_46416(-center, -center, 0.0F);
/*     */     
/* 202 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.currentAlpha);
/* 203 */     context.method_51427(stack, 0, 0);
/* 204 */     context.method_51452();
/* 205 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 207 */     matrices.method_22909();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\ItemPickerComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */