/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiColorSetting;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.SettingColorPickerWindow;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ public class MultiColorComponent extends AbstractSettingComponent {
/*     */   public MultiColorComponent(MultiColorSetting setting) {
/*  21 */     super((Setting)setting);
/*  22 */     this.setting = setting;
/*     */   }
/*     */   private final MultiColorSetting setting;
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  27 */     updateVisibilityAnimation();
/*  28 */     MsdfGuiFontRenderer labelFont = GuiFonts.getSize(14, Fonts.Type.INTER_BOLD);
/*     */     
/*  30 */     boolean isModified = this.setting.isModified();
/*  31 */     float textOffset = animatedTextOffset(isModified);
/*     */     
/*  33 */     if (!this.setting.isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/*  37 */     class_4587 matrix = context.method_51448();
/*     */     
/*  39 */     int colorCount = this.setting.getColorCount();
/*  40 */     this.height = 23.5F;
/*  41 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*     */     
/*  43 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(matrix);
/*  44 */     renderSettingCard(context, 0.0F, hoverProgress);
/*     */     
/*  46 */     float textX = this.x + 10.0F + textOffset;
/*  47 */     String text = this.label.singleLine(this.setting.getLocalizedName());
/*  48 */     float firstColorX = this.x + this.width - 15.5F - Math.max(0, colorCount - 1) * 9.0F;
/*  49 */     this.label.render(matrix, (FontRenderer)labelFont, text, textX, centeredTextY((FontRenderer)labelFont, text), firstColorX - 8.0F, this.y, this.height, 
/*  50 */         primaryText());
/*  51 */     updateLabelHover(mouseX, mouseY);
/*     */     
/*  53 */     for (int i = 0; i < colorCount; i++) {
/*  54 */       ColorSetting colorSetting = this.setting.getColor(i);
/*  55 */       if (colorSetting != null) {
/*  56 */         float colorX = this.x + this.width - 15.5F - ((colorCount - 1 - i) * 9);
/*  57 */         float colorY = this.y + this.height / 2.0F - 3.5F;
/*  58 */         if (MathUtil.isHovered(mouseX, mouseY, colorX, colorY, 7.0D, 7.0D)) {
/*  59 */           CursorManager.requestHand();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  68 */         rectangle.render(ShapeProperties.create(matrix, colorX, colorY, 7.0D, 7.0D)
/*  69 */             .round(3.5F)
/*  70 */             .thickness(1.2F)
/*  71 */             .softness(1.0F)
/*  72 */             .outlineColor(MenuStyle.BORDER_LIGHT)
/*  73 */             .color(colorSetting.getColor())
/*  74 */             .build());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  81 */     if (!this.setting.isVisible()) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/*  86 */       playButtonClickSound();
/*  87 */       this.setting.reset();
/*  88 */       return true;
/*     */     } 
/*     */     
/*  91 */     int colorCount = this.setting.getColorCount();
/*     */     
/*  93 */     for (int i = 0; i < colorCount; i++) {
/*  94 */       float colorX = this.x + this.width - 15.5F - ((colorCount - 1 - i) * 9);
/*  95 */       float colorY = this.y + this.height / 2.0F - 3.5F;
/*     */       
/*  97 */       if (MathUtil.isHovered(mouseX, mouseY, colorX, colorY, 7.0D, 7.0D)) {
/*  98 */         if (button == 0) {
/*  99 */           playButtonClickSound();
/* 100 */           this.setting.setSelectedColorIndex(i);
/* 101 */           ColorSetting selectedColor = this.setting.getColor(i);
/*     */           
/* 103 */           if (selectedColor != null) {
/* 104 */             AbstractWindow existingWindow = null;
/*     */             
/* 106 */             for (AbstractWindow window : windowManager.getWindows()) {
/* 107 */               if (window instanceof SettingColorPickerWindow) {
/* 108 */                 existingWindow = window;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 113 */             if (existingWindow != null) {
/* 114 */               windowManager.delete(existingWindow);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 134 */               int windowWidth = Math.round(126.0F);
/* 135 */               int windowHeight = Math.round(SettingColorPickerWindow.getWindowHeight(selectedColor));
/*     */               
/* 137 */               int windowX = (int)(mouseX + 10.0D);
/* 138 */               int windowY = (int)(mouseY + 10.0D);
/*     */               
/* 140 */               windowX = MenuScreen.INSTANCE.clampOverlayX(windowX, windowWidth);
/* 141 */               windowY = MenuScreen.INSTANCE.clampOverlayY(windowY, windowHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 146 */               AbstractWindow colorWindow = (new SettingColorPickerWindow(selectedColor)).position(windowX, windowY).size(windowWidth, windowHeight).draggable(false);
/*     */               
/* 148 */               windowManager.add(colorWindow);
/*     */             } 
/*     */           } 
/* 151 */           return true;
/* 152 */         }  if (button == 1) {
/* 153 */           playButtonClickSound();
/* 154 */           this.setting.setSelectedColorIndex(i);
/* 155 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 165 */     if (!this.setting.isVisible()) {
/* 166 */       return false;
/*     */     }
/*     */     
/* 169 */     int colorCount = this.setting.getColorCount();
/* 170 */     for (int i = 0; i < colorCount; i++) {
/* 171 */       float colorX = this.x + this.width - 15.5F - ((colorCount - 1 - i) * 9);
/* 172 */       float colorY = this.y + this.height / 2.0F - 3.5F;
/* 173 */       if (MathUtil.isHovered(mouseX, mouseY, colorX, colorY, 7.0D, 7.0D)) {
/* 174 */         return true;
/*     */       }
/*     */     } 
/* 177 */     if (MathUtil.isHovered(mouseX, mouseY, (this.x + 9.0F), (this.y + 6.0F), (this.width - (colorCount * 9) - 18.0F), (this.height - 12.0F))) {
/* 178 */       return true;
/*     */     }
/* 180 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\MultiColorComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */