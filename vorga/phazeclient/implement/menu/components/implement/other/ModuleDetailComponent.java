/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.SettingComponentAdder;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFont;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleDetailComponent
/*     */   extends AbstractComponent
/*     */ {
/*     */   private static final float PANEL_PADDING = 12.0F;
/*     */   private static final float SETTINGS_TOP = 41.0F;
/*     */   private static final float SETTINGS_BOTTOM = 0.0F;
/*     */   private static final float COLUMN_GAP = 8.0F;
/*     */   private static final float ROW_GAP = 8.0F;
/*     */   private static final float BACK_BUTTON_SIZE = 13.0F;
/*     */   private static final float DESCRIPTION_TEXT_SIZE = 4.9F;
/*     */   private static final float DESCRIPTION_TOP_OFFSET = 11.2F;
/*  42 */   private final List<AbstractSettingComponent> settingComponents = new ArrayList<>(); public List<AbstractSettingComponent> getSettingComponents() { return this.settingComponents; }
/*  43 */    private final Animation openAnimation = (new DecelerateAnimation()).setMs(1).setValue(1.0D); public Animation getOpenAnimation() { return this.openAnimation; }
/*  44 */    private final Animation backHoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D); public Animation getBackHoverAnimation() { return this.backHoverAnimation; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Module module;
/*     */ 
/*     */   
/*     */   public Module getModule() {
/*  53 */     return this.module;
/*  54 */   } private double lastMeasuredHeight = 0.0D; public double getLastMeasuredHeight() { return this.lastMeasuredHeight; }
/*     */   
/*     */   public void open(Module module) {
/*  57 */     if (module == null || module.isServerLocked()) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     if (this.module != module) {
/*  62 */       this.settingComponents.clear();
/*  63 */       (new SettingComponentAdder()).addSettingComponent(module.settings(), this.settingComponents);
/*     */     } 
/*     */     
/*  66 */     this.module = module;
/*  67 */     this.scroll = 0.0D;
/*  68 */     this.smoothedScroll = 0.0D;
/*  69 */     this.lastMeasuredHeight = 0.0D;
/*  70 */     this.openAnimation.setDirectionAndFinish(Direction.FORWARDS);
/*  71 */     this.backHoverAnimation.setDirectionAndFinish(Direction.BACKWARDS);
/*  72 */     MenuScreen.INSTANCE.getModuleDescriptionComponent().hide();
/*     */   }
/*     */   
/*     */   public void closeDetail() {
/*  76 */     this.module = null;
/*  77 */     this.scroll = 0.0D;
/*  78 */     this.smoothedScroll = 0.0D;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/*  82 */     if (this.module != null && this.module.isServerLocked()) {
/*  83 */       MenuScreen.INSTANCE.closeModuleDetail();
/*  84 */       return false;
/*     */     } 
/*  86 */     return (this.module != null);
/*     */   }
/*     */   
/*     */   public float contentX() {
/*  90 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/*  91 */     return menuScreen.x + 101.0F;
/*     */   }
/*     */   
/*     */   public float contentY() {
/*  95 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/*  96 */     return menuScreen.y + 25.5F;
/*     */   }
/*     */   
/*     */   public float contentWidth() {
/* 100 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 101 */     return menuScreen.width - 102.5F;
/*     */   }
/*     */   
/*     */   public float contentHeight() {
/* 105 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 106 */     return menuScreen.height - 27.0F;
/*     */   }
/*     */   
/*     */   private float backButtonX() {
/* 110 */     return contentX() + 12.0F;
/*     */   }
/*     */   
/*     */   private float backButtonY() {
/* 114 */     return contentY() + 12.0F;
/*     */   }
/*     */   
/*     */   private float settingsPanelX() {
/* 118 */     return contentX() + 12.0F;
/*     */   }
/*     */   
/*     */   private float settingsPanelY() {
/* 122 */     return contentY() + 41.0F;
/*     */   }
/*     */   
/*     */   private float settingsPanelWidth() {
/* 126 */     return contentWidth() - 24.0F;
/*     */   }
/*     */   
/*     */   private float settingsPanelHeight() {
/* 130 */     return contentHeight() - 41.0F - 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 135 */     if (!isOpen()) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     float anim = this.openAnimation.getOutputFloat() * this.globalAlpha;
/* 140 */     renderHeader(context, mouseX, mouseY, anim);
/* 141 */     renderDescription(context, anim);
/* 142 */     renderSettingsPanel(context, mouseX, mouseY, delta, anim);
/*     */   }
/*     */   
/*     */   private void renderHeader(class_332 context, int mouseX, int mouseY, float anim) {
/* 146 */     class_4587 matrices = context.method_51448();
/*     */     
/* 148 */     boolean backHovered = MathUtil.isHovered(mouseX, mouseY, backButtonX(), backButtonY(), 13.0D, 13.0D);
/* 149 */     this.backHoverAnimation.setDirection(backHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 150 */     float backHover = this.backHoverAnimation.getOutputFloat();
/* 151 */     int backBg = backHovered ? MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CARD_OPTIONS, 0.45F) : MenuStyle.PANEL_CHIP;
/* 152 */     int backBorder = backHovered ? MenuStyle.BORDER_LIGHT : MenuStyle.BORDER;
/*     */     
/* 154 */     rectangle.render(ShapeProperties.create(matrices, backButtonX(), backButtonY(), 13.0D, 13.0D)
/* 155 */         .round(3.0F)
/* 156 */         .thickness(1.2F)
/* 157 */         .outlineColor(MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_BG, backBorder, anim), 0.96F * anim))
/* 158 */         .color(MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_BG, backBg, anim), 0.92F * anim))
/* 159 */         .build());
/* 160 */     float backIconSize = 7.5F;
/* 161 */     float backIconX = backButtonX() + (13.0F - backIconSize) / 2.0F;
/* 162 */     float backIconY = backButtonY() + (13.0F - backIconSize) / 2.0F;
/* 163 */     image.setTexture("textures/back_arrow.png")
/* 164 */       .render(ShapeProperties.create(matrices, (backIconX - backIconSize), backIconY, backIconSize, backIconSize)
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 169 */         .rotation(0.0F)
/* 170 */         .color(MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.TEXT_PRIMARY, -1, backHover * 0.18F), anim))
/* 171 */         .build());
/*     */     
/* 173 */     String title = this.module.getLocalizedName().toUpperCase(Locale.ROOT);
/* 174 */     float titleX = backButtonX() + 13.0F + 8.0F;
/*     */     
/* 176 */     float titleY = MenuStyle.centerMsdfTextY(8.2F, backButtonY(), 13.0F);
/* 177 */     MsdfRenderer.renderText(MsdfFonts.bold(), title, 8.2F, MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, anim), matrices.method_23760().method_23761(), titleX, titleY, 0.0F);
/*     */   }
/*     */   
/*     */   private void renderDescription(class_332 context, float anim) {
/* 181 */     class_4587 matrices = context.method_51448();
/* 182 */     String description = this.module.getDisplayDescription();
/* 183 */     if (description == null || description.isEmpty()) {
/* 184 */       description = "Custom themes for GUI";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     description = Lang.translate(description);
/*     */     
/* 194 */     float descX = backButtonX() + 13.0F + 8.0F;
/* 195 */     float descY = backButtonY() + 11.2F;
/* 196 */     float lineHeight = 6.5F;
/* 197 */     MsdfFont descFont = MsdfFonts.medium();
/*     */     
/* 199 */     for (String line : wrap(description, descFont, 4.9F, contentWidth() - 24.0F - 32.0F)) {
/* 200 */       MsdfRenderer.renderText(descFont, line, 4.9F, 
/*     */ 
/*     */ 
/*     */           
/* 204 */           MenuStyle.withAlpha(MenuStyle.TEXT_MUTED, anim), matrices
/* 205 */           .method_23760().method_23761(), descX, descY, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 210 */       descY += lineHeight;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderSettingsPanel(class_332 context, int mouseX, int mouseY, float delta, float anim) {
/* 215 */     class_4587 matrices = context.method_51448();
/* 216 */     float panelX = settingsPanelX();
/* 217 */     float panelY = settingsPanelY();
/* 218 */     float panelWidth = settingsPanelWidth();
/* 219 */     float panelHeight = settingsPanelHeight();
/*     */     
/* 221 */     float fadeProgress = 1.0F;
/* 222 */     float panelAlpha = Math.max(0.0F, Math.min(1.0F, fadeProgress * anim));
/*     */     
/* 224 */     float innerX = panelX + 2.0F;
/* 225 */     float innerY = panelY + 2.0F;
/* 226 */     float innerWidth = panelWidth - 4.0F;
/* 227 */     float innerHeight = panelHeight - 4.0F;
/*     */ 
/*     */     
/* 230 */     boolean singleColumnLayout = (this.settingComponents.size() <= 2);
/* 231 */     float columnWidth = singleColumnLayout ? innerWidth : ((innerWidth - 8.0F) / 2.0F);
/* 232 */     float scissorTopLift = 6.0F;
/* 233 */     float scissorX = innerX;
/* 234 */     float scissorY = innerY - scissorTopLift;
/* 235 */     float scissorWidth = innerWidth;
/* 236 */     float scissorHeight = innerHeight + scissorTopLift;
/*     */     
/* 238 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/* 239 */     ScissorManager scissorManager = Main.getInstance().getScissorManager();
/* 240 */     scissorManager.push(positionMatrix, scissorX, scissorY, scissorWidth, scissorHeight);
/*     */     
/* 242 */     float[] columnOffsets = { 0.0F, 0.0F };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     float visibleTop = scissorY;
/* 255 */     float visibleBottom = scissorY + scissorHeight;
/* 256 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 257 */       if (!shouldRenderSetting(component.getSetting())) {
/*     */         continue;
/*     */       }
/*     */       
/* 261 */       boolean fullWidthSetting = (singleColumnLayout || component.getSetting().isFullWidth());
/* 262 */       if (fullWidthSetting) {
/* 263 */         float rowOffset = Math.max(columnOffsets[0], columnOffsets[1]);
/* 264 */         float f1 = (float)((innerY + rowOffset) - this.smoothedScroll);
/* 265 */         float f2 = component.getExpandedHeight();
/*     */         
/* 267 */         component.x = innerX;
/* 268 */         component.y = f1;
/* 269 */         component.width = innerWidth;
/* 270 */         if (f1 + f2 >= visibleTop && f1 <= visibleBottom) {
/* 271 */           component.setExternalAlpha(panelAlpha);
/* 272 */           component.render(context, mouseX, mouseY, delta);
/* 273 */           component.setExternalAlpha(1.0F);
/*     */         } 
/*     */         
/* 276 */         float nextOffset = rowOffset + f2 + 8.0F;
/* 277 */         columnOffsets[0] = nextOffset;
/* 278 */         columnOffsets[1] = nextOffset; continue;
/*     */       } 
/* 280 */       int column = (columnOffsets[0] <= columnOffsets[1]) ? 0 : 1;
/* 281 */       float componentX = innerX + column * (columnWidth + 8.0F);
/* 282 */       float componentY = (float)((innerY + columnOffsets[column]) - this.smoothedScroll);
/* 283 */       float rowHeight = component.getExpandedHeight();
/*     */       
/* 285 */       component.x = componentX;
/* 286 */       component.y = componentY;
/* 287 */       component.width = columnWidth;
/* 288 */       if (componentY + rowHeight >= visibleTop && componentY <= visibleBottom) {
/* 289 */         component.setExternalAlpha(panelAlpha);
/* 290 */         component.render(context, mouseX, mouseY, delta);
/* 291 */         component.setExternalAlpha(1.0F);
/*     */       } 
/*     */       
/* 294 */       columnOffsets[column] = columnOffsets[column] + rowHeight + 8.0F;
/*     */     } 
/*     */ 
/*     */     
/* 298 */     scissorManager.pop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     this.lastMeasuredHeight = Math.max(columnOffsets[0], columnOffsets[1]);
/* 306 */     double maxScroll = Math.max(0.0D, this.lastMeasuredHeight - innerHeight);
/* 307 */     this.scroll = Math.max(0.0D, Math.min(this.scroll, maxScroll));
/* 308 */     this.smoothedScroll = MathUtil.interpolateSmooth(2.0D, this.smoothedScroll, this.scroll);
/*     */   }
/*     */   
/*     */   private boolean shouldRenderSetting(Setting setting) {
/* 312 */     return (setting != null && setting.isVisible());
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
/*     */   private boolean isComponentVisible(AbstractSettingComponent component) {
/* 329 */     float visibleTop = settingsPanelY() - 4.0F;
/* 330 */     float visibleBottom = visibleTop + settingsPanelHeight() + 4.0F;
/* 331 */     float rowTop = component.y;
/* 332 */     float rowBottom = component.y + component.getExpandedHeight();
/* 333 */     return (rowBottom >= visibleTop && rowTop <= visibleBottom);
/*     */   }
/*     */   
/*     */   private List<String> wrap(String text, MsdfFont font, float size, float maxWidth) {
/* 337 */     List<String> lines = new ArrayList<>();
/* 338 */     if (text == null || text.isEmpty()) {
/* 339 */       return lines;
/*     */     }
/*     */     
/* 342 */     StringBuilder currentLine = new StringBuilder();
/* 343 */     for (String word : text.split(" ")) {
/* 344 */       String candidate = currentLine.isEmpty() ? word : (String.valueOf(currentLine) + " " + String.valueOf(currentLine));
/* 345 */       if (font.getWidth(candidate, size) <= maxWidth) {
/* 346 */         currentLine = new StringBuilder(candidate);
/*     */       } else {
/* 348 */         if (!currentLine.isEmpty()) {
/* 349 */           lines.add(currentLine.toString());
/*     */         }
/* 351 */         currentLine = new StringBuilder(word);
/*     */       } 
/*     */     } 
/*     */     
/* 355 */     if (!currentLine.isEmpty()) {
/* 356 */       lines.add(currentLine.toString());
/*     */     }
/*     */     
/* 359 */     return lines;
/*     */   }
/*     */   
/*     */   public boolean blocksInteraction(double mouseX, double mouseY) {
/* 363 */     return (isOpen() && MathUtil.isHovered(mouseX, mouseY, contentX(), contentY(), contentWidth(), contentHeight()));
/*     */   }
/*     */   
/*     */   public boolean isInteractiveHover(double mouseX, double mouseY) {
/* 367 */     if (!isOpen()) {
/* 368 */       return false;
/*     */     }
/*     */     
/* 371 */     if (MathUtil.isHovered(mouseX, mouseY, backButtonX(), backButtonY(), 13.0D, 13.0D)) {
/* 372 */       return true;
/*     */     }
/*     */     
/* 375 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 376 */       if (shouldRenderSetting(component.getSetting()) && isComponentVisible(component) && component.isHover(mouseX, mouseY)) {
/* 377 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 381 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 386 */     if (!isOpen()) {
/* 387 */       return false;
/*     */     }
/*     */     
/* 390 */     if (button == 0 && MathUtil.isHovered(mouseX, mouseY, backButtonX(), backButtonY(), 13.0D, 13.0D)) {
/* 391 */       playButtonClickSound();
/* 392 */       closeDetail();
/* 393 */       return true;
/*     */     } 
/*     */     
/* 396 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 397 */       if (shouldRenderSetting(component.getSetting()) && isComponentVisible(component) && component.isHover(mouseX, mouseY) && component.mouseClicked(mouseX, mouseY, button)) {
/* 398 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 402 */     return blocksInteraction(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 407 */     if (!isOpen()) {
/* 408 */       return false;
/*     */     }
/*     */     
/* 411 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 412 */       if (shouldRenderSetting(component.getSetting()) && isComponentVisible(component)) {
/* 413 */         component.mouseReleased(mouseX, mouseY, button);
/*     */       }
/*     */     } 
/* 416 */     return blocksInteraction(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 421 */     if (!isOpen()) {
/* 422 */       return false;
/*     */     }
/*     */     
/* 425 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 426 */       if (shouldRenderSetting(component.getSetting()) && isComponentVisible(component)) {
/* 427 */         component.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */       }
/*     */     } 
/* 430 */     return blocksInteraction(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 435 */     if (!isOpen()) {
/* 436 */       return false;
/*     */     }
/*     */     
/* 439 */     if (MathUtil.isHovered(mouseX, mouseY, settingsPanelX(), settingsPanelY(), settingsPanelWidth(), settingsPanelHeight())) {
/* 440 */       this.scroll += amount * -20.0D;
/*     */     }
/*     */     
/* 443 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 444 */       if (shouldRenderSetting(component.getSetting()) && isComponentVisible(component) && component.isHover(mouseX, mouseY)) {
/* 445 */         component.mouseScrolled(mouseX, mouseY, amount);
/*     */       }
/*     */     } 
/*     */     
/* 449 */     return blocksInteraction(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 454 */     if (!isOpen()) {
/* 455 */       return false;
/*     */     }
/*     */     
/* 458 */     if (keyCode == 256) {
/* 459 */       return false;
/*     */     }
/*     */     
/* 462 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 463 */       if (shouldRenderSetting(component.getSetting()) && component.keyPressed(keyCode, scanCode, modifiers)) {
/* 464 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 468 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
/* 473 */     if (!isOpen()) {
/* 474 */       return false;
/*     */     }
/*     */     
/* 477 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 478 */       if (shouldRenderSetting(component.getSetting())) {
/* 479 */         component.keyReleased(keyCode, scanCode, modifiers);
/*     */       }
/*     */     } 
/* 482 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 487 */     if (!isOpen()) {
/* 488 */       return false;
/*     */     }
/*     */     
/* 491 */     for (AbstractSettingComponent component : this.settingComponents) {
/* 492 */       if (shouldRenderSetting(component.getSetting()) && component.charTyped(chr, modifiers)) {
/* 493 */         return true;
/*     */       }
/*     */     } 
/* 496 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\ModuleDetailComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */