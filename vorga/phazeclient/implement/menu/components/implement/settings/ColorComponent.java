/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.SettingColorPickerWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.AlphaComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.ColorEditorComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.ColorPresetComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.HueComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.SaturationComponent;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorComponent
/*     */   extends AbstractSettingComponent
/*     */ {
/*     */   private static final float LEFT_PADDING = 10.0F;
/*     */   private static final float COLOR_SIZE = 12.0F;
/*     */   private static final float COLOR_RIGHT = 10.0F;
/*     */   private static final float HEADER_TEXT_SIZE = 7.0F;
/*     */   private static final float HEADER_TOP_PAD = 3.0F;
/*     */   private final ColorSetting setting;
/*  45 */   private final List<AbstractComponent> components = new ArrayList<>();
/*     */   
/*     */   private final HueComponent hueComponent;
/*     */   private final SaturationComponent saturationComponent;
/*     */   private final AlphaComponent alphaComponent;
/*     */   private final ColorEditorComponent colorEditorComponent;
/*     */   private final ColorPresetComponent colorPresetComponent;
/*     */   
/*     */   public ColorComponent(ColorSetting setting) {
/*  54 */     super((Setting)setting);
/*  55 */     this.setting = setting;
/*     */     
/*  57 */     this.components.addAll(
/*  58 */         Arrays.asList(new AbstractComponent[] { (AbstractComponent)(this.hueComponent = new HueComponent(setting)), (AbstractComponent)(this.saturationComponent = new SaturationComponent(setting)), (AbstractComponent)(this.alphaComponent = new AlphaComponent(setting)), (AbstractComponent)(this.colorEditorComponent = new ColorEditorComponent(setting)), (AbstractComponent)(this.colorPresetComponent = new ColorPresetComponent(setting)) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  70 */     updateVisibilityAnimation();
/*     */     
/*  72 */     if (!this.setting.isVisible()) {
/*     */       return;
/*     */     }
/*  75 */     if (this.setting.isPopupRow()) {
/*  76 */       renderPopupRow(context, mouseX, mouseY);
/*     */       
/*     */       return;
/*     */     } 
/*  80 */     class_4587 matrix = context.method_51448();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     ((ColorPresetComponent)this.colorPresetComponent.position(this.x, this.y)).getWindowHeight();
/*     */     
/*  90 */     this.alphaComponent.position(this.x + 5.0F, this.y + 5.0F);
/*  91 */     this.hueComponent.position(this.x + 5.0F, this.y + 5.0F);
/*  92 */     this.saturationComponent.position(this.x + 5.0F, this.y + 5.0F);
/*  93 */     this.colorEditorComponent.position(this.x + 5.0F, this.y + 5.0F);
/*     */     
/*  95 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*     */           component.globalAlpha = this.currentAlpha;
/*     */ 
/*     */           
/*     */           component.render(context, mouseX, mouseY, delta);
/*     */         });
/*     */ 
/*     */     
/* 108 */     renderHeader(matrix);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     this.height = 73.0F;
/* 116 */     updateLabelHover(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   private void renderPopupRow(class_332 context, int mouseX, int mouseY) {
/* 120 */     MsdfGuiFontRenderer msdfGuiFontRenderer = GuiFonts.getSize(13, Fonts.Type.INTER_BOLD);
/* 121 */     boolean isModified = this.setting.isModified();
/* 122 */     float textOffset = animatedTextOffset(isModified);
/* 123 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 23.5D);
/* 124 */     if (hovered) {
/* 125 */       CursorManager.requestHand();
/*     */     }
/* 127 */     float hoverProgress = animatedCardHover(hovered);
/*     */     
/* 129 */     this.height = 23.5F;
/* 130 */     renderSettingCard(context, 0.0F, hoverProgress);
/* 131 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(context.method_51448());
/*     */     
/* 133 */     float colorX = this.x + this.width - 10.0F - 12.0F;
/* 134 */     float colorY = this.y + 11.75F - 6.0F;
/* 135 */     float textX = this.x + 10.0F + textOffset;
/* 136 */     String title = this.label.singleLine(this.setting.getLocalizedName());
/* 137 */     this.label.render(context.method_51448(), (FontRenderer)msdfGuiFontRenderer, title, textX, centeredTextY((FontRenderer)msdfGuiFontRenderer, title, this.y, 23.5F), colorX - 8.0F, this.y, 23.5F, 
/* 138 */         primaryText());
/* 139 */     updateLabelHover(mouseX, mouseY);
/*     */     
/* 141 */     int colorFill = MenuStyle.withAlpha(0xFF000000 | this.setting.getColor() & 0xFFFFFF, this.currentAlpha);
/* 142 */     int colorOutline = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.settingOutline(false), -1, hovered ? 0.12F : 0.0F), this.currentAlpha);
/* 143 */     rectangle.render(ShapeProperties.create(context.method_51448(), colorX, colorY, 12.0D, 12.0D)
/* 144 */         .round(3.2F)
/* 145 */         .thickness(1.05F)
/* 146 */         .outlineColor(colorOutline)
/* 147 */         .color(colorFill)
/* 148 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderHeader(class_4587 matrix) {
/* 168 */     String header, settingName = this.setting.getLocalizedName();
/* 169 */     if (settingName == null) settingName = "";
/*     */     
/* 171 */     String moduleName = resolveModuleVisibleName();
/*     */ 
/*     */     
/* 174 */     if (!moduleName.isEmpty() && !settingName.isEmpty()) {
/* 175 */       header = moduleName + " " + moduleName;
/* 176 */     } else if (!moduleName.isEmpty()) {
/* 177 */       header = moduleName;
/*     */     } else {
/* 179 */       header = settingName;
/*     */     } 
/* 181 */     if (header.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     this.label.renderMsdf(matrix, header, 7.0F, this.x + 10.0F, this.y + 3.0F, this.x + this.width - 10.0F, this.y, 13.0F, 
/* 186 */         MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.currentAlpha));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String resolveModuleVisibleName() {
/* 198 */     String moduleId = this.setting.getModuleContext();
/* 199 */     if (moduleId == null || moduleId.isEmpty()) {
/* 200 */       return "";
/*     */     }
/* 202 */     Main main = Main.getInstance();
/* 203 */     if (main == null || main.getModuleProvider() == null) {
/* 204 */       return "";
/*     */     }
/* 206 */     Module module = main.getModuleProvider().get(moduleId);
/* 207 */     if (module == null) {
/* 208 */       return "";
/*     */     }
/* 210 */     String visible = module.getVisibleName();
/* 211 */     return (visible == null) ? "" : visible;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 216 */     if (!this.setting.isVisible()) {
/* 217 */       return false;
/*     */     }
/* 219 */     if (this.setting.isPopupRow()) {
/* 220 */       return handlePopupRowClick(mouseX, mouseY, button);
/*     */     }
/*     */     
/* 223 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent)
/*     */             return;  component.mouseClicked(mouseX, mouseY, button);
/*     */         });
/* 227 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 232 */     if (this.setting.isPopupRow()) {
/* 233 */       return super.mouseScrolled(mouseX, mouseY, amount);
/*     */     }
/* 235 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent)
/*     */             return;  component.mouseScrolled(mouseX, mouseY, amount);
/*     */         });
/* 239 */     return super.mouseScrolled(mouseX, mouseY, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 244 */     if (this.setting.isPopupRow()) {
/* 245 */       return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */     }
/* 247 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent)
/*     */             return;  component.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */         });
/* 251 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 256 */     if (this.setting.isPopupRow()) {
/* 257 */       return super.mouseReleased(mouseX, mouseY, button);
/*     */     }
/* 259 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent)
/*     */             return;  component.mouseReleased(mouseX, mouseY, button);
/*     */         });
/* 263 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 268 */     if (!this.setting.isVisible()) {
/* 269 */       return false;
/*     */     }
/* 271 */     if (this.setting.isPopupRow()) {
/* 272 */       return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 23.5D);
/*     */     }
/* 274 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */   
/*     */   private boolean handlePopupRowClick(double mouseX, double mouseY, int button) {
/* 278 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 279 */       playButtonClickSound();
/* 280 */       this.setting.reset();
/* 281 */       return true;
/*     */     } 
/*     */     
/* 284 */     if (button == 0 && MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 23.5D)) {
/* 285 */       playButtonClickSound();
/* 286 */       toggleColorWindow();
/* 287 */       return true;
/*     */     } 
/*     */     
/* 290 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */   
/*     */   private void toggleColorWindow() {
/* 294 */     AbstractWindow existingWindow = null;
/* 295 */     for (AbstractWindow window : windowManager.getWindows()) {
/* 296 */       if (window instanceof SettingColorPickerWindow) { SettingColorPickerWindow pickerWindow = (SettingColorPickerWindow)window; if (pickerWindow.getSetting() == this.setting) {
/* 297 */           existingWindow = window;
/*     */           break;
/*     */         }  }
/*     */     
/*     */     } 
/* 302 */     if (existingWindow != null) {
/* 303 */       windowManager.delete(existingWindow);
/*     */       
/*     */       return;
/*     */     } 
/* 307 */     for (AbstractWindow window : windowManager.getWindows()) {
/* 308 */       if (window instanceof SettingColorPickerWindow) {
/* 309 */         windowManager.delete(window);
/*     */       }
/*     */     } 
/*     */     
/* 313 */     int windowWidth = Math.round(126.0F);
/* 314 */     int windowHeight = Math.round(SettingColorPickerWindow.getWindowHeight(this.setting));
/* 315 */     float colorX = this.x + this.width - 10.0F - 12.0F;
/* 316 */     int windowX = MenuScreen.INSTANCE.clampOverlayX((colorX + 12.0F + 8.0F), windowWidth);
/* 317 */     int windowY = MenuScreen.INSTANCE.clampOverlayY((this.y + 11.75F - windowHeight / 2.0F), windowHeight);
/*     */     
/* 319 */     windowManager.add((new SettingColorPickerWindow(this.setting))
/* 320 */         .position(windowX, windowY)
/* 321 */         .size(windowWidth, windowHeight)
/* 322 */         .draggable(false));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\ColorComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */