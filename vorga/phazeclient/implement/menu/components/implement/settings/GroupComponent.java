/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_332;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.ButtonComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.CheckComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.SettingComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.group.GroupWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.group.WorldColorGroupWindow;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ public class GroupComponent extends AbstractSettingComponent {
/*  20 */   private final CheckComponent checkComponent = new CheckComponent();
/*  21 */   private final SettingComponent settingComponent = new SettingComponent();
/*  22 */   private final ButtonComponent buttonComponent = new ButtonComponent();
/*     */   
/*     */   private final GroupSetting setting;
/*     */   
/*     */   public GroupComponent(GroupSetting setting) {
/*  27 */     super((Setting)setting);
/*  28 */     this.setting = setting;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  33 */     updateVisibilityAnimation();
/*  34 */     MsdfGuiFontRenderer labelFont = GuiFonts.getSize(14, Fonts.Type.INTER_BOLD);
/*     */     
/*  36 */     boolean isModified = this.setting.isModified();
/*  37 */     boolean worldColorStyle = this.setting.isColorPickerStyleWindow();
/*  38 */     float textOffset = worldColorStyle ? 0.0F : animatedTextOffset(isModified);
/*     */     
/*  40 */     ButtonComponent openButton = null;
/*  41 */     float openButtonWidth = 0.0F;
/*  42 */     if (worldColorStyle) {
/*     */ 
/*     */       
/*  45 */       openButton = this.buttonComponent.setText(Lang.translate("Open")).setRunnable(() -> spawnWindow((int)this.x, (int)this.y));
/*  46 */       openButton.globalAlpha = this.currentAlpha;
/*  47 */       openButtonWidth = openButton.measureWidth();
/*     */     } 
/*     */     
/*  50 */     this.height = 23.5F;
/*  51 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*  52 */     float activeProgress = (isGroupWindowOpen() || (this.setting.isCheckbox() && this.setting.isValue())) ? 1.0F : 0.0F;
/*     */     
/*  54 */     if (!worldColorStyle) {
/*  55 */       this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(context.method_51448());
/*     */     }
/*  57 */     renderSettingCard(context, activeProgress, hoverProgress);
/*     */     
/*  59 */     float textX = this.x + 10.0F + textOffset;
/*  60 */     String text = this.label.singleLine(this.setting.getLocalizedName());
/*     */     
/*  62 */     float labelRight = worldColorStyle ? (this.x + this.width - openButtonWidth - 17.0F) : (this.x + this.width - (this.setting.isCheckbox() ? 46.0F : 25.0F));
/*  63 */     this.label.render(context.method_51448(), (FontRenderer)labelFont, text, textX, centeredTextY((FontRenderer)labelFont, text), labelRight, this.y, this.height, 
/*  64 */         primaryText());
/*  65 */     updateLabelHover(mouseX, mouseY);
/*     */     
/*  67 */     boolean isWindowOpen = isGroupWindowOpen();
/*     */     
/*  69 */     if (worldColorStyle) {
/*  70 */       openButton.position(this.x + this.width - 9.0F - openButtonWidth, this.y + this.height / 2.0F - 7.0F);
/*  71 */       openButton.render(context, mouseX, mouseY, delta);
/*     */       
/*     */       return;
/*     */     } 
/*  75 */     if (this.setting.isCheckbox()) {
/*  76 */       ((CheckComponent)this.checkComponent.position(this.x + this.width - 38.0F, this.y + (this.height - CheckComponent.getToggleHeight()) / 2.0F))
/*  77 */         .setRunnable(() -> this.setting.setValue(!this.setting.isValue()))
/*  78 */         .setState(this.setting.isValue())
/*  79 */         .render(context, mouseX, mouseY, delta);
/*     */       
/*  81 */       ((SettingComponent)this.settingComponent.position(this.x + this.width - 17.0F, this.y + this.height / 2.0F - 5.5F))
/*  82 */         .setRunnable(() -> spawnWindow(mouseX, mouseY))
/*  83 */         .setWindowOpen(isWindowOpen)
/*  84 */         .render(context, mouseX, mouseY, delta);
/*     */     } else {
/*  86 */       ((SettingComponent)this.settingComponent.position(this.x + this.width - 17.0F, this.y + this.height / 2.0F - 5.5F))
/*  87 */         .setRunnable(() -> spawnWindow(mouseX, mouseY))
/*  88 */         .setWindowOpen(isWindowOpen)
/*  89 */         .render(context, mouseX, mouseY, delta);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  95 */     if (!this.setting.isColorPickerStyleWindow() && button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/*  96 */       this.setting.reset();
/*  97 */       return true;
/*     */     } 
/*     */     
/* 100 */     if (this.setting.isColorPickerStyleWindow() && this.buttonComponent.mouseClicked(mouseX, mouseY, button)) {
/* 101 */       return true;
/*     */     }
/*     */     
/* 104 */     if (this.setting.isCheckbox() && this.checkComponent.mouseClicked(mouseX, mouseY, button)) {
/* 105 */       return true;
/*     */     }
/* 107 */     if (this.settingComponent.mouseClicked(mouseX, mouseY, button)) {
/* 108 */       return true;
/*     */     }
/* 110 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 115 */     if (!this.setting.isVisible()) {
/* 116 */       return false;
/*     */     }
/* 118 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */   
/*     */   private void spawnWindow(int mouseX, int mouseY) {
/* 122 */     AbstractWindow existingWindow = findWindow(this.setting);
/*     */     
/* 124 */     if (existingWindow != null) {
/* 125 */       closeChildGroupWindows(this.setting);
/* 126 */       windowManager.delete(existingWindow);
/*     */     } else {
/* 128 */       int windowWidth = Math.round(this.setting.isColorPickerStyleWindow() ? this.setting.getPopupWidth() : 137.0F);
/*     */ 
/*     */       
/* 131 */       int windowHeight = this.setting.isColorPickerStyleWindow() ? estimateWorldColorWindowHeight() : 200;
/*     */       
/* 133 */       int windowX = mouseX + 5;
/* 134 */       int windowY = mouseY + 5;
/*     */       
/* 136 */       windowX = MenuScreen.INSTANCE.clampOverlayX(windowX, windowWidth);
/* 137 */       windowY = MenuScreen.INSTANCE.clampOverlayY(windowY, windowHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       AbstractWindow groupWindow = this.setting.isColorPickerStyleWindow() ? (new WorldColorGroupWindow(this.setting)).position(windowX, windowY).size(windowWidth, windowHeight).draggable(false) : (new GroupWindow(this.setting)).position(windowX, windowY).size(windowWidth, 23.0F).draggable(false);
/*     */       
/* 149 */       windowManager.add(groupWindow);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int estimateWorldColorWindowHeight() {
/* 154 */     int visibleSettings = 0;
/* 155 */     for (Setting subSetting : this.setting.getSubSettings()) {
/* 156 */       Supplier<Boolean> visible = subSetting.getVisible();
/* 157 */       if (visible != null && !((Boolean)visible.get()).booleanValue()) {
/*     */         continue;
/*     */       }
/* 160 */       visibleSettings++;
/*     */     } 
/*     */     
/* 163 */     int estimated = 30 + visibleSettings * 26 + 4;
/* 164 */     return Math.min(Math.round(this.setting.getPopupMaxHeight()), Math.max(52, estimated));
/*     */   }
/*     */   
/*     */   private void closeChildGroupWindows(GroupSetting parentSetting) {
/* 168 */     List<AbstractWindow> windowsCopy = new ArrayList<>(windowManager.getWindows());
/*     */     
/* 170 */     for (Setting childSetting : parentSetting.getSubSettings()) {
/* 171 */       if (childSetting instanceof GroupSetting) { GroupSetting groupSetting = (GroupSetting)childSetting;
/* 172 */         closeChildGroupWindows(groupSetting);
/*     */         
/* 174 */         AbstractWindow childWindow = findWindow(groupSetting, windowsCopy);
/* 175 */         if (childWindow != null) {
/* 176 */           windowManager.delete(childWindow);
/*     */         } }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isGroupWindowOpen() {
/* 183 */     return (findWindow(this.setting) != null);
/*     */   }
/*     */   
/*     */   private AbstractWindow findWindow(GroupSetting targetSetting) {
/* 187 */     return findWindow(targetSetting, windowManager.getWindows());
/*     */   }
/*     */   
/*     */   private AbstractWindow findWindow(GroupSetting targetSetting, List<AbstractWindow> windows) {
/* 191 */     for (AbstractWindow window : windows) {
/* 192 */       if (window instanceof GroupWindow) { GroupWindow groupWindow = (GroupWindow)window; if (groupWindow.getSetting() == targetSetting)
/* 193 */           return window;  }
/*     */       
/* 195 */       if (window instanceof WorldColorGroupWindow) { WorldColorGroupWindow worldColorGroupWindow = (WorldColorGroupWindow)window; if (worldColorGroupWindow.getSetting() == targetSetting)
/* 196 */           return window;  }
/*     */     
/*     */     } 
/* 199 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\GroupComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */