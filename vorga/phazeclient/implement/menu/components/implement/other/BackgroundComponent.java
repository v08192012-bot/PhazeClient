/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.config.ConfigManager;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ 
/*     */ public class BackgroundComponent extends AbstractComponent {
/*     */   private static final float TAB_TEXT_SIZE = 6.9F;
/*     */   private static final float FOOTER_TEXT_SIZE = 5.5F;
/*     */   private static final float CONFIG_TEXT_SIZE = 6.0F;
/*     */   private static final float HEADER_HEIGHT = 34.0F;
/*     */   private static final float TOP_TAB_HEIGHT = 17.0F;
/*     */   private static final float TOP_TAB_GAP = 8.0F;
/*     */   private static final float TOP_TAB_HORIZONTAL_PADDING = 19.0F;
/*     */   private static final float SIDEBAR_WIDTH = 100.0F;
/*     */   
/*  41 */   public BackgroundComponent setSearchComponent(SearchComponent searchComponent) { this.searchComponent = searchComponent; return this; } private static final float CONFIG_ROW_HEIGHT = 17.5F; private static final float CONFIG_ROW_GAP = 1.0F; private static final float CONFIG_DELETE_WIDTH = 19.5F; private static final float CONFIG_DELETE_ICON_SIZE = 5.4F; private static final float CONFIG_TEXT_PADDING = 6.0F; private static final float CONFIG_SCROLL_STEP = 22.0F; private static final float FOOTER_BUTTON_HEIGHT = 15.0F; private static final float FOOTER_BUTTON_GAP = 14.0F; public BackgroundComponent setEditingConfigName(String editingConfigName) { this.editingConfigName = editingConfigName; return this; } public BackgroundComponent setEditingText(String editingText) { this.editingText = editingText; return this; } public BackgroundComponent setLastClickTime(long lastClickTime) { this.lastClickTime = lastClickTime; return this; } public BackgroundComponent setLastClickedConfig(String lastClickedConfig) { this.lastClickedConfig = lastClickedConfig; return this; } public BackgroundComponent setConfigScroll(double configScroll) { this.configScroll = configScroll; return this; } public BackgroundComponent setSmoothedConfigScroll(double smoothedConfigScroll) { this.smoothedConfigScroll = smoothedConfigScroll; return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private final ConfigManager configManager = Main.getInstance().getConfigManager();
/*  62 */   private final Animation saveConfigHoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/*  63 */   private final Animation editHudHoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/*  64 */   private final Map<String, Animation> configHoverAnimations = new HashMap<>();
/*  65 */   private final Map<String, Animation> configDeleteHoverAnimations = new HashMap<>();
/*  66 */   private final Map<String, Animation> topTabHoverAnimations = new HashMap<>();
/*     */   
/*     */   private SearchComponent searchComponent;
/*  69 */   private String editingConfigName = null;
/*  70 */   private String editingText = "";
/*  71 */   private long lastClickTime = 0L;
/*  72 */   private String lastClickedConfig = null;
/*     */ 
/*     */   
/*     */   private double configScroll;
/*     */   
/*     */   private double smoothedConfigScroll;
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  81 */     Theme.getInstance().syncLanguage();
/*  82 */     class_4587 matrix = context.method_51448();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     boolean configsOpen = MenuScreen.INSTANCE.isConfigsViewOpen();
/*  88 */     boolean cosmeticsOpen = MenuScreen.INSTANCE.isCosmeticsViewOpen();
/*  89 */     boolean fullWidthPage = (configsOpen || cosmeticsOpen || MenuScreen.INSTANCE.isMessengerViewOpen());
/*     */     
/*  91 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/*  92 */         .round(8.0F).softness(1.0F).thickness(2.0F)
/*  93 */         .outlineColor(applyGlobalAlpha(MenuStyle.BORDER_LIGHT))
/*  94 */         .color(applyGlobalAlpha(MenuStyle.PANEL_BG))
/*  95 */         .build());
/*     */     
/*  97 */     rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), (this.y + 1.0F), (this.width - 2.0F), (this.height - 2.0F))
/*  98 */         .round(7.0F).color(applyGlobalAlpha(MenuStyle.PANEL_BG_SOFT)).build());
/*     */     
/* 100 */     rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), (this.y + 1.0F), (this.width - 2.0F), 34.0D)
/* 101 */         .round(new float[] { 7.0F, 0.0F, 7.0F, 0.0F }).color(applyGlobalAlpha(MenuStyle.PANEL_HEADER)).build());
/*     */     
/* 103 */     if (!fullWidthPage) {
/* 104 */       rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), (this.y + 34.0F + 1.0F), 99.0D, (this.height - 34.0F - 2.0F))
/* 105 */           .round(new float[] { 0.0F, 0.0F, 0.0F, 7.0F }).color(applyGlobalAlpha(MenuStyle.PANEL_SIDEBAR)).build());
/*     */       
/* 107 */       rectangle.render(ShapeProperties.create(matrix, (this.x + 100.0F), (this.y + 34.0F + 1.0F), (this.width - 100.0F - 1.0F), (this.height - 34.0F - 2.0F))
/* 108 */           .round(new float[] { 0.0F, 7.0F, 0.0F, 0.0F }).color(applyGlobalAlpha(MenuStyle.PANEL_CONTENT)).build());
/*     */       
/* 110 */       rectangle.render(ShapeProperties.create(matrix, (this.x + 100.0F), (this.y + 34.0F + 1.0F), 1.0D, (this.height - 34.0F - 2.0F))
/* 111 */           .color(applyGlobalAlpha(MenuStyle.BORDER)).build());
/*     */     } else {
/*     */       
/* 114 */       rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), (this.y + 34.0F + 1.0F), (this.width - 2.0F), (this.height - 34.0F - 2.0F))
/* 115 */           .round(new float[] { 0.0F, 0.0F, 7.0F, 7.0F }).color(applyGlobalAlpha(MenuStyle.PANEL_CONTENT)).build());
/*     */     } 
/* 117 */     rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), (this.y + 34.0F), (this.width - 2.0F), 1.0D)
/* 118 */         .color(applyGlobalAlpha(MenuStyle.BORDER)).build());
/*     */     
/* 120 */     renderHeader(context, mouseX, mouseY);
/* 121 */     if (!fullWidthPage) {
/* 122 */       renderConfigs(context, mouseX, mouseY);
/* 123 */       renderSidebarFooter(context, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderHeader(class_332 context, int mouseX, int mouseY) {
/* 128 */     String[] labels = { "MODS", "SETTINGS", "COSMETICS", "CONFIGS", "CHAT" };
/* 129 */     boolean settingsActive = isSettingsTabActive();
/* 130 */     boolean cosmeticsActive = isCosmeticsTabActive();
/* 131 */     boolean configsActive = isConfigsTabActive();
/* 132 */     boolean chatActive = isChatTabActive();
/* 133 */     boolean modsActive = (!settingsActive && !cosmeticsActive && !configsActive && !chatActive);
/* 134 */     boolean[] active = { modsActive, settingsActive, cosmeticsActive, configsActive, chatActive };
/* 135 */     float totalTabsWidth = 0.0F;
/* 136 */     for (int i = 0; i < labels.length; i++) {
/* 137 */       totalTabsWidth += getTopTabWidth(labels[i]);
/* 138 */       if (i < labels.length - 1) {
/* 139 */         totalTabsWidth += 8.0F;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (configsActive) {
/* 149 */       totalTabsWidth += 25.0F;
/*     */     }
/*     */     
/* 152 */     float tabsX = this.x + this.width / 2.0F - totalTabsWidth / 2.0F;
/* 153 */     for (int j = 0; j < labels.length; j++) {
/* 154 */       tabsX += drawTopTab(context, mouseX, mouseY, tabsX, labels[j], active[j]) + 8.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (configsActive) {
/* 161 */       drawImportPlus(context, mouseX, mouseY, tabsX);
/*     */     }
/*     */   }
/*     */   
/*     */   private float getTopTabWidth(String label) {
/* 166 */     return MsdfFonts.bold().getWidth(topTabText(label), 6.9F) + 19.0F;
/*     */   }
/*     */   
/*     */   public void renderPageTransitionMask(class_332 context, float alpha) {
/* 170 */     if (alpha <= 0.001F) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     class_4587 matrix = context.method_51448();
/* 175 */     float contentY = this.y + 34.0F + 1.0F;
/* 176 */     float contentHeight = this.height - 34.0F - 2.0F;
/*     */ 
/*     */     
/* 179 */     boolean fullWidthPage = (MenuScreen.INSTANCE.isConfigsViewOpen() || MenuScreen.INSTANCE.isCosmeticsViewOpen() || MenuScreen.INSTANCE.isMessengerViewOpen());
/*     */     
/* 181 */     if (fullWidthPage) {
/* 182 */       rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), contentY, (this.width - 2.0F), contentHeight)
/* 183 */           .round(new float[] { 0.0F, 0.0F, 7.0F, 7.0F
/* 184 */             }).color(MenuStyle.withAlpha(MenuStyle.PANEL_CONTENT, alpha))
/* 185 */           .build());
/*     */       
/*     */       return;
/*     */     } 
/* 189 */     rectangle.render(ShapeProperties.create(matrix, (this.x + 1.0F), contentY, 99.0D, contentHeight)
/* 190 */         .round(new float[] { 0.0F, 0.0F, 0.0F, 7.0F
/* 191 */           }).color(MenuStyle.withAlpha(MenuStyle.PANEL_SIDEBAR, alpha))
/* 192 */         .build());
/* 193 */     rectangle.render(ShapeProperties.create(matrix, (this.x + 100.0F), contentY, (this.width - 100.0F - 1.0F), contentHeight)
/*     */         
/* 195 */         .round(new float[] { 0.0F, 7.0F, 0.0F, 0.0F
/* 196 */           }).color(MenuStyle.withAlpha(MenuStyle.PANEL_CONTENT, alpha))
/* 197 */         .build());
/* 198 */     rectangle.render(ShapeProperties.create(matrix, (this.x + 100.0F), contentY, 1.0D, contentHeight)
/* 199 */         .color(MenuStyle.withAlpha(MenuStyle.BORDER, alpha))
/* 200 */         .build());
/*     */   }
/*     */   
/*     */   private float drawTopTab(class_332 context, int mouseX, int mouseY, float tabX, String label, boolean active) {
/* 204 */     class_4587 matrix = context.method_51448();
/* 205 */     String displayLabel = topTabText(label);
/* 206 */     float tabWidth = getTopTabWidth(label);
/* 207 */     float tabY = this.y + 8.5F;
/* 208 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, tabX, tabY, tabWidth, 17.0D);
/* 209 */     if (hovered) {
/* 210 */       CursorManager.requestHand();
/*     */     }
/* 212 */     Animation hoverAnimation = this.topTabHoverAnimations.computeIfAbsent(label, ignored -> (new DecelerateAnimation()).setMs(180).setValue(1.0D));
/* 213 */     hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/* 215 */     float hoverProgress = hoverAnimation.getOutputFloat();
/* 216 */     float activeProgress = active ? 1.0F : 0.0F;
/*     */     
/* 218 */     int baseTabColor = MenuStyle.PANEL_CHIP;
/* 219 */     int hoverTabColor = MenuStyle.mix(baseTabColor, -1, 0.08F);
/* 220 */     int activeTabColor = MenuStyle.mix(baseTabColor, -1, 0.16F);
/* 221 */     int tabColor = MenuStyle.mix(MenuStyle.mix(baseTabColor, hoverTabColor, hoverProgress), activeTabColor, activeProgress);
/*     */     
/* 223 */     int baseBorderColor = MenuStyle.BORDER;
/* 224 */     int hoverBorderColor = MenuStyle.mix(MenuStyle.BORDER, MenuStyle.BORDER_LIGHT, 0.26F);
/* 225 */     int activeBorderColor = MenuStyle.mix(MenuStyle.BORDER, MenuStyle.BORDER_LIGHT, 0.38F);
/* 226 */     int borderColor = MenuStyle.mix(MenuStyle.mix(baseBorderColor, hoverBorderColor, hoverProgress), activeBorderColor, activeProgress);
/*     */     
/* 228 */     int baseTextColor = MenuStyle.TEXT_MUTED;
/* 229 */     int hoverTextColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, 0.45F);
/* 230 */     int activeTextColor = MenuStyle.TEXT_PRIMARY;
/* 231 */     int tabText = MenuStyle.mix(MenuStyle.mix(baseTextColor, hoverTextColor, hoverProgress), activeTextColor, activeProgress);
/*     */     
/* 233 */     rectangle.render(ShapeProperties.create(matrix, tabX, tabY, tabWidth, 17.0D)
/* 234 */         .round(2.0F).thickness(3.0F).outlineColor(applyGlobalAlpha(borderColor)).color(MenuStyle.withAlpha(tabColor, 0.0F)).build());
/* 235 */     MsdfRenderer.renderText(
/* 236 */         MsdfFonts.bold(), displayLabel, 6.9F, 
/*     */ 
/*     */         
/* 239 */         applyGlobalAlpha(tabText), matrix
/* 240 */         .method_23760().method_23761(), 
/* 241 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), displayLabel, 6.9F, tabX, tabWidth), 
/* 242 */         MenuStyle.centerMsdfTextY(6.9F, tabY, 17.0F), 0.0F);
/*     */ 
/*     */     
/* 245 */     return tabWidth;
/*     */   }
/*     */   
/*     */   private static String topTabText(String id) {
/* 249 */     return id;
/*     */   }
/*     */   
/*     */   private void renderNewConfigButton(class_332 context, int mouseX, int mouseY) {
/* 253 */     class_4587 matrix = context.method_51448();
/* 254 */     float buttonX = this.x + 4.0F;
/* 255 */     float buttonY = this.y + 34.0F + 7.0F;
/* 256 */     float buttonWidth = 92.0F;
/* 257 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, buttonX, buttonY, buttonWidth, 15.0D);
/* 258 */     this.saveConfigHoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/* 260 */     float hoverProgress = this.saveConfigHoverAnimation.getOutputFloat();
/*     */     
/* 262 */     rectangle.render(ShapeProperties.create(matrix, buttonX, buttonY, buttonWidth, 15.0D)
/* 263 */         .round(2.0F).thickness(2.0F)
/* 264 */         .outlineColor(MenuStyle.BORDER)
/* 265 */         .color(MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.TEXT_PRIMARY, 0.06F * hoverProgress)).build());
/* 266 */     String label = Lang.t("sidebar.new_config");
/* 267 */     MsdfRenderer.renderText(
/* 268 */         MsdfFonts.bold(), label, 5.5F, 
/*     */ 
/*     */         
/* 271 */         MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, 0.55F + hoverProgress * 0.45F), matrix
/* 272 */         .method_23760().method_23761(), 
/* 273 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), label, 5.5F, buttonX, buttonWidth), 
/* 274 */         MenuStyle.centerMsdfTextY(5.5F, buttonY, 15.0F), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderConfigs(class_332 context, int mouseX, int mouseY) {
/* 280 */     class_4587 matrix = context.method_51448();
/* 281 */     String[] configs = this.configManager.getConfigList();
/* 282 */     float rowX = configRowX();
/* 283 */     float rowWidth = configRowWidth();
/* 284 */     float viewportY = configViewportY();
/* 285 */     float viewportHeight = configViewportHeight();
/* 286 */     float contentHeight = configContentHeight(configs.length);
/* 287 */     double maxScroll = Math.max(0.0F, contentHeight - viewportHeight);
/* 288 */     this.configScroll = Math.max(-maxScroll, Math.min(0.0D, this.configScroll));
/* 289 */     if (maxScroll <= 0.01D) {
/* 290 */       this.configScroll = 0.0D;
/* 291 */       this.smoothedConfigScroll = 0.0D;
/*     */     } else {
/* 293 */       this.smoothedConfigScroll = MathUtil.interpolateSmooth(3.5D, this.smoothedConfigScroll, this.configScroll);
/*     */ 
/*     */       
/* 296 */       if (Math.abs(this.smoothedConfigScroll - this.configScroll) < 0.01D) {
/* 297 */         this.smoothedConfigScroll = this.configScroll;
/*     */       }
/*     */     } 
/*     */     
/* 301 */     String currentConfig = this.configManager.getCurrentConfigName();
/* 302 */     Main.getInstance().getScissorManager().push(matrix
/* 303 */         .method_23760().method_23761(), rowX, viewportY, rowWidth, viewportHeight);
/*     */     
/* 305 */     for (int i = 0; i < configs.length; i++) {
/* 306 */       String config = configs[i];
/* 307 */       float rowY = viewportY + i * 18.5F + (float)this.smoothedConfigScroll;
/*     */       
/* 309 */       if (rowY + 17.5F >= viewportY - 1.0F && rowY <= viewportY + viewportHeight + 1.0F) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 314 */         boolean rowHovered = MathUtil.isHovered(mouseX, mouseY, rowX, rowY, rowWidth, 17.5D);
/* 315 */         boolean deleteHovered = isDeleteHovered(mouseX, mouseY, rowY);
/* 316 */         if (rowHovered && mouseY >= viewportY && mouseY < viewportY + viewportHeight) {
/* 317 */           CursorManager.requestHand();
/*     */         }
/* 319 */         boolean active = config.equalsIgnoreCase(currentConfig);
/* 320 */         Animation rowHoverAnimation = configHoverAnimation(config);
/* 321 */         Animation deleteHoverAnimation = configDeleteHoverAnimation(config);
/*     */         
/* 323 */         rowHoverAnimation.setDirection(rowHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 324 */         deleteHoverAnimation.setDirection(deleteHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */         
/* 326 */         float rowHoverProgress = rowHoverAnimation.getOutputFloat();
/* 327 */         float deleteHoverProgress = deleteHoverAnimation.getOutputFloat();
/*     */         
/* 329 */         int baseRowColor = active ? MenuStyle.PANEL_ROW_ACTIVE : MenuStyle.PANEL_ROW;
/* 330 */         int rowColor = MenuStyle.mix(baseRowColor, MenuStyle.TEXT_PRIMARY, 0.07F * rowHoverProgress);
/*     */ 
/*     */         
/* 333 */         int outlineColor = active ? MenuStyle.mix(MenuStyle.BORDER_LIGHT, MenuStyle.CHIP_ACTIVE, 0.35F + rowHoverProgress * 0.08F) : MenuStyle.mix(MenuStyle.BORDER, MenuStyle.BORDER_LIGHT, rowHoverProgress);
/*     */         
/* 335 */         rectangle.render(ShapeProperties.create(matrix, rowX, rowY, rowWidth, 17.5D)
/* 336 */             .round(1.5F).thickness(2.0F).outlineColor(applyGlobalAlpha(outlineColor)).color(applyGlobalAlpha(rowColor)).build());
/*     */         
/* 338 */         MsdfRenderer.renderText(
/* 339 */             MsdfFonts.bold(), config, 6.0F, 
/*     */ 
/*     */             
/* 342 */             applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), context
/* 343 */             .method_51448().method_23760().method_23761(), rowX + 6.0F, 
/*     */             
/* 345 */             MenuStyle.centerMsdfTextY(6.0F, rowY, 17.5F), 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 355 */         float iconSize = 5.4F;
/* 356 */         float deleteX = deleteSectionX(rowX, rowWidth) + (19.5F - iconSize) / 2.0F;
/* 357 */         float iconY = rowY + (17.5F - iconSize) / 2.0F;
/* 358 */         int deleteColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, -1, deleteHoverProgress);
/*     */         
/* 360 */         renderConfigCross(matrix, deleteX, iconY, iconSize, applyGlobalAlpha(deleteColor));
/*     */ 
/*     */ 
/*     */         
/* 364 */         if (this.editingConfigName != null && this.editingConfigName.equals(config)) {
/* 365 */           float textWidth = MsdfFonts.bold().getWidth(this.editingText + "_", 6.0F);
/* 366 */           rectangle.render(ShapeProperties.create(matrix, rowX, rowY, Math.max(rowWidth, textWidth + 12.0F), 17.5D)
/* 367 */               .round(1.5F).thickness(2.0F).outlineColor(applyGlobalAlpha(MenuStyle.CHIP_ACTIVE)).color(applyGlobalAlpha(MenuStyle.PANEL_BG)).build());
/* 368 */           MsdfRenderer.renderText(
/* 369 */               MsdfFonts.bold(), this.editingText + "_", 6.0F, 
/*     */ 
/*     */               
/* 372 */               applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), context
/* 373 */               .method_51448().method_23760().method_23761(), rowX + 6.0F, 
/*     */               
/* 375 */               MenuStyle.centerMsdfTextY(6.0F, rowY, 17.5F), 0.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     Main.getInstance().getScissorManager().pop();
/*     */   }
/*     */   
/*     */   private void renderSidebarFooter(class_332 context, int mouseX, int mouseY) {
/* 384 */     class_4587 matrix = context.method_51448();
/* 385 */     float footerX = this.x + 4.0F;
/* 386 */     float footerWidth = 92.0F;
/* 387 */     float buttonGap = 4.0F;
/*     */ 
/*     */     
/* 390 */     float newConfigY = footerStartY() - 5.0F - 15.0F - buttonGap;
/* 391 */     boolean newConfigHovered = MathUtil.isHovered(mouseX, mouseY, footerX, newConfigY, footerWidth, 15.0D);
/* 392 */     if (newConfigHovered) {
/* 393 */       CursorManager.requestHand();
/*     */     }
/* 395 */     this.saveConfigHoverAnimation.setDirection(newConfigHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 396 */     float newConfigHoverProgress = this.saveConfigHoverAnimation.getOutputFloat();
/*     */     
/* 398 */     rectangle.render(ShapeProperties.create(matrix, footerX, newConfigY, footerWidth, 15.0D)
/* 399 */         .round(2.0F).thickness(2.0F)
/* 400 */         .outlineColor(applyGlobalAlpha(MenuStyle.BORDER))
/* 401 */         .color(applyGlobalAlpha(MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.TEXT_PRIMARY, 0.06F * newConfigHoverProgress))).build());
/* 402 */     String newConfigLabel = Lang.t("sidebar.new_config");
/* 403 */     MsdfRenderer.renderText(
/* 404 */         MsdfFonts.bold(), newConfigLabel, 5.5F, 
/*     */ 
/*     */         
/* 407 */         applyGlobalAlpha(MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, 0.55F + newConfigHoverProgress * 0.45F)), matrix
/* 408 */         .method_23760().method_23761(), 
/* 409 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), newConfigLabel, 5.5F, footerX, footerWidth), 
/* 410 */         MenuStyle.centerMsdfTextY(5.5F, newConfigY, 15.0F), 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     float editHudY = footerStartY() - 5.0F;
/* 416 */     boolean editHovered = MathUtil.isHovered(mouseX, mouseY, footerX, editHudY, footerWidth, 15.0D);
/* 417 */     if (editHovered) {
/* 418 */       CursorManager.requestHand();
/*     */     }
/* 420 */     this.editHudHoverAnimation.setDirection(editHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 421 */     float editHoverProgress = this.editHudHoverAnimation.getOutputFloat();
/*     */     
/* 423 */     rectangle.render(ShapeProperties.create(matrix, footerX, editHudY, footerWidth, 15.0D)
/* 424 */         .round(2.0F).thickness(2.0F)
/* 425 */         .outlineColor(applyGlobalAlpha(MenuStyle.BORDER))
/* 426 */         .color(applyGlobalAlpha(MenuStyle.mix(MenuStyle.CHIP_ACTIVE, MenuStyle.TEXT_PRIMARY, 0.08F * editHoverProgress))).build());
/* 427 */     String editLabel = Lang.t("sidebar.edit_hud");
/* 428 */     MsdfRenderer.renderText(
/* 429 */         MsdfFonts.bold(), editLabel, 5.5F, 
/*     */ 
/*     */         
/* 432 */         applyGlobalAlpha(MenuStyle.mix(MenuStyle.TEXT_PRIMARY, MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, 1.0F), editHoverProgress)), matrix
/* 433 */         .method_23760().method_23761(), 
/* 434 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), editLabel, 5.5F, footerX, footerWidth), 
/* 435 */         MenuStyle.centerMsdfTextY(5.5F, editHudY, 15.0F), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 442 */     if (button != 0) {
/* 443 */       return super.mouseClicked(mouseX, mouseY, button);
/*     */     }
/*     */     
/* 446 */     if (handleTopTabClick(mouseX, mouseY)) {
/* 447 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     if (MenuScreen.INSTANCE.isConfigsViewOpen() || MenuScreen.INSTANCE.isCosmeticsViewOpen()) {
/* 455 */       return false;
/*     */     }
/*     */     
/* 458 */     float footerX = this.x + 4.0F;
/* 459 */     float footerWidth = 92.0F;
/* 460 */     float buttonGap = 4.0F;
/*     */ 
/*     */     
/* 463 */     float newConfigY = footerStartY() - 5.0F - 15.0F - buttonGap;
/* 464 */     if (MathUtil.isHovered(mouseX, mouseY, footerX, newConfigY, footerWidth, 15.0D)) {
/* 465 */       playButtonClickSound();
/* 466 */       this.configManager.createNewConfig();
/* 467 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 471 */     float editHudY = footerStartY() - 5.0F;
/* 472 */     if (MathUtil.isHovered(mouseX, mouseY, footerX, editHudY, footerWidth, 15.0D)) {
/* 473 */       playButtonClickSound();
/* 474 */       class_310 client = class_310.method_1551();
/* 475 */       if (client != null && client.field_1724 != null && client.field_1687 != null) {
/* 476 */         client.method_1507((class_437)new class_408(""));
/*     */       }
/* 478 */       return true;
/*     */     } 
/*     */     
/* 481 */     float rowX = configRowX();
/* 482 */     float rowWidth = configRowWidth();
/* 483 */     float viewportY = configViewportY();
/* 484 */     float viewportBottom = viewportY + configViewportHeight();
/* 485 */     String[] configs = this.configManager.getConfigList();
/* 486 */     for (int i = 0; i < configs.length; i++) {
/* 487 */       String config = configs[i];
/* 488 */       float rowY = viewportY + i * 18.5F + (float)this.smoothedConfigScroll;
/*     */       
/* 490 */       if (rowY + 17.5F >= viewportY && rowY <= viewportBottom) {
/*     */ 
/*     */ 
/*     */         
/* 494 */         if (isDeleteHovered(mouseX, mouseY, rowY)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 500 */           playButtonClickSound();
/* 501 */           this.configManager.deleteConfig(config);
/* 502 */           return true;
/*     */         } 
/*     */         
/* 505 */         if (MathUtil.isHovered(mouseX, mouseY, rowX, rowY, rowWidth, 17.5D)) {
/* 506 */           long currentTime = System.currentTimeMillis();
/* 507 */           if (config.equals(this.lastClickedConfig) && currentTime - this.lastClickTime < 500L) {
/*     */             
/* 509 */             if (!config.equalsIgnoreCase("default")) {
/* 510 */               this.editingConfigName = config;
/* 511 */               this.editingText = config;
/* 512 */               this.lastClickedConfig = null;
/* 513 */               this.lastClickTime = 0L;
/*     */             } 
/*     */           } else {
/*     */             
/* 517 */             if (this.editingConfigName != null) {
/*     */               
/* 519 */               if (!this.editingText.isEmpty() && !this.editingText.equals(this.editingConfigName)) {
/* 520 */                 this.configManager.renameConfig(this.editingConfigName, this.editingText);
/*     */               }
/* 522 */               this.editingConfigName = null;
/* 523 */               this.editingText = "";
/*     */             } 
/* 525 */             playButtonClickSound();
/* 526 */             this.configManager.loadConfig(config);
/* 527 */             this.lastClickedConfig = config;
/* 528 */             this.lastClickTime = currentTime;
/*     */           } 
/* 530 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 535 */     if (this.editingConfigName != null) {
/* 536 */       this.editingConfigName = null;
/* 537 */       this.editingText = "";
/*     */     } 
/*     */     
/* 540 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 545 */     if (this.editingConfigName != null) {
/* 546 */       if (keyCode == 257) {
/*     */         
/* 548 */         if (!this.editingText.isEmpty() && !this.editingText.equals(this.editingConfigName)) {
/* 549 */           this.configManager.renameConfig(this.editingConfigName, this.editingText);
/*     */         }
/* 551 */         this.editingConfigName = null;
/* 552 */         this.editingText = "";
/* 553 */         return true;
/* 554 */       }  if (keyCode == 256) {
/*     */         
/* 556 */         this.editingConfigName = null;
/* 557 */         this.editingText = "";
/* 558 */         return true;
/* 559 */       }  if (keyCode == 259) {
/*     */         
/* 561 */         if (!this.editingText.isEmpty()) {
/* 562 */           this.editingText = this.editingText.substring(0, this.editingText.length() - 1);
/*     */         }
/* 564 */         return true;
/*     */       } 
/*     */     } 
/* 567 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 572 */     if (this.editingConfigName != null) {
/*     */       
/* 574 */       if (Character.isLetterOrDigit(chr) || chr == '_' || chr == '-') {
/* 575 */         this.editingText += this.editingText;
/*     */       }
/* 577 */       return true;
/*     */     } 
/* 579 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */   
/*     */   public boolean isInteractiveHover(double mouseX, double mouseY) {
/* 583 */     if (isTopTabHovered(mouseX, mouseY)) {
/* 584 */       return true;
/*     */     }
/*     */     
/* 587 */     float footerX = this.x + 4.0F;
/* 588 */     float footerWidth = 92.0F;
/* 589 */     float buttonGap = 4.0F;
/* 590 */     float newConfigY = footerStartY() - 5.0F - 15.0F - buttonGap;
/* 591 */     float editHudY = footerStartY() - 5.0F;
/*     */     
/* 593 */     if (MathUtil.isHovered(mouseX, mouseY, footerX, newConfigY, footerWidth, 15.0D)) {
/* 594 */       return true;
/*     */     }
/*     */     
/* 597 */     if (MathUtil.isHovered(mouseX, mouseY, footerX, editHudY, footerWidth, 15.0D)) {
/* 598 */       return true;
/*     */     }
/*     */     
/* 601 */     float rowX = configRowX();
/* 602 */     float rowWidth = configRowWidth();
/* 603 */     float viewportY = configViewportY();
/* 604 */     float viewportBottom = viewportY + configViewportHeight();
/* 605 */     String[] configs = this.configManager.getConfigList();
/* 606 */     for (int i = 0; i < configs.length; i++) {
/* 607 */       float rowY = viewportY + i * 18.5F + (float)this.smoothedConfigScroll;
/*     */       
/* 609 */       if (rowY + 17.5F >= viewportY && rowY <= viewportBottom)
/*     */       {
/*     */ 
/*     */         
/* 613 */         if (MathUtil.isHovered(mouseX, mouseY, rowX, rowY, rowWidth, 17.5D) || isDeleteHovered(mouseX, mouseY, rowY)) {
/* 614 */           return true;
/*     */         }
/*     */       }
/*     */     } 
/* 618 */     return false;
/*     */   }
/*     */   
/*     */   private float footerStartY() {
/* 622 */     return this.y + this.height - 18.0F;
/*     */   }
/*     */   
/*     */   private float configRowX() {
/* 626 */     return this.x + 5.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private float configRowStartY() {
/* 631 */     return this.y + 34.0F + 8.5F;
/*     */   }
/*     */   
/*     */   private float configViewportY() {
/* 635 */     return configRowStartY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float configViewportHeight() {
/* 642 */     float newConfigY = footerStartY() - 5.0F - 15.0F - 4.0F;
/*     */     
/* 644 */     return Math.max(0.0F, newConfigY - 4.0F - configViewportY());
/*     */   }
/*     */   
/*     */   private static float configContentHeight(int configCount) {
/* 648 */     if (configCount <= 0) {
/* 649 */       return 0.0F;
/*     */     }
/* 651 */     return configCount * 17.5F + (configCount - 1) * 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 657 */     if (MenuScreen.INSTANCE.isConfigsViewOpen() || MenuScreen.INSTANCE.isCosmeticsViewOpen() || 
/* 658 */       !MathUtil.isHovered(mouseX, mouseY, this.x, configViewportY(), 100.0D, configViewportHeight())) {
/* 659 */       return false;
/*     */     }
/*     */     
/* 662 */     double maxScroll = Math.max(0.0F, 
/* 663 */         configContentHeight((this.configManager.getConfigList()).length) - configViewportHeight());
/* 664 */     if (maxScroll <= 0.01D) {
/* 665 */       return false;
/*     */     }
/*     */     
/* 668 */     this.configScroll = Math.max(-maxScroll, Math.min(0.0D, this.configScroll + amount * 22.0D));
/*     */     
/* 670 */     return true;
/*     */   }
/*     */   
/*     */   private float configRowWidth() {
/* 674 */     return 92.0F;
/*     */   }
/*     */   
/*     */   private float deleteSectionX(float rowX, float rowWidth) {
/* 678 */     return rowX + rowWidth - 19.5F;
/*     */   }
/*     */   
/*     */   private boolean isDeleteHovered(double mouseX, double mouseY, float rowY) {
/* 682 */     float iconSize = 5.4F;
/* 683 */     float deleteX = deleteSectionX(configRowX(), configRowWidth()) + (19.5F - iconSize) / 2.0F;
/* 684 */     float deleteY = rowY + (17.5F - iconSize) / 2.0F;
/* 685 */     return MathUtil.isHovered(mouseX, mouseY, deleteX, deleteY, iconSize, iconSize);
/*     */   }
/*     */   
/*     */   private Animation configHoverAnimation(String configName) {
/* 689 */     return this.configHoverAnimations.computeIfAbsent(configName, ignored -> (new DecelerateAnimation()).setMs(180).setValue(1.0D));
/*     */   }
/*     */   
/*     */   private Animation configDeleteHoverAnimation(String configName) {
/* 693 */     return this.configDeleteHoverAnimations.computeIfAbsent(configName, ignored -> (new DecelerateAnimation()).setMs(160).setValue(1.0D));
/*     */   }
/*     */   
/*     */   private boolean handleTopTabClick(double mouseX, double mouseY) {
/* 697 */     String[] labels = { "MODS", "SETTINGS", "COSMETICS", "CONFIGS", "CHAT" };
/* 698 */     boolean configsActive = isConfigsTabActive();
/* 699 */     float totalTabsWidth = 0.0F;
/* 700 */     for (int i = 0; i < labels.length; i++) {
/* 701 */       totalTabsWidth += getTopTabWidth(labels[i]);
/* 702 */       if (i < labels.length - 1) {
/* 703 */         totalTabsWidth += 8.0F;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 710 */     if (configsActive) {
/* 711 */       totalTabsWidth += 25.0F;
/*     */     }
/*     */     
/* 714 */     float tabsX = this.x + this.width / 2.0F - totalTabsWidth / 2.0F;
/* 715 */     float tabY = this.y + 8.5F;
/* 716 */     for (String label : labels) {
/* 717 */       float tabWidth = getTopTabWidth(label);
/* 718 */       if (MathUtil.isHovered(mouseX, mouseY, tabsX, tabY, tabWidth, 17.0D)) {
/* 719 */         playButtonClickSound();
/* 720 */         MenuScreen.INSTANCE.requestTopTab(label);
/* 721 */         return true;
/*     */       } 
/* 723 */       tabsX += tabWidth + 8.0F;
/*     */     } 
/*     */     
/* 726 */     if (configsActive && 
/* 727 */       MathUtil.isHovered(mouseX, mouseY, tabsX, tabY, 17.0D, 17.0D)) {
/* 728 */       playButtonClickSound();
/* 729 */       MenuScreen.INSTANCE.openConfigImportModal();
/* 730 */       return true;
/*     */     } 
/* 732 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isTopTabHovered(double mouseX, double mouseY) {
/* 736 */     String[] labels = { "MODS", "SETTINGS", "COSMETICS", "CONFIGS", "CHAT" };
/* 737 */     boolean configsActive = isConfigsTabActive();
/* 738 */     float totalTabsWidth = 0.0F;
/* 739 */     for (int i = 0; i < labels.length; i++) {
/* 740 */       totalTabsWidth += getTopTabWidth(labels[i]);
/* 741 */       if (i < labels.length - 1) {
/* 742 */         totalTabsWidth += 8.0F;
/*     */       }
/*     */     } 
/* 745 */     if (configsActive) {
/* 746 */       totalTabsWidth += 25.0F;
/*     */     }
/*     */     
/* 749 */     float tabsX = this.x + this.width / 2.0F - totalTabsWidth / 2.0F;
/* 750 */     float tabY = this.y + 8.5F;
/* 751 */     for (String label : labels) {
/* 752 */       float tabWidth = getTopTabWidth(label);
/* 753 */       if (MathUtil.isHovered(mouseX, mouseY, tabsX, tabY, tabWidth, 17.0D)) {
/* 754 */         return true;
/*     */       }
/* 756 */       tabsX += tabWidth + 8.0F;
/*     */     } 
/* 758 */     if (configsActive && 
/* 759 */       MathUtil.isHovered(mouseX, mouseY, tabsX, tabY, 17.0D, 17.0D)) {
/* 760 */       return true;
/*     */     }
/* 762 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawImportPlus(class_332 context, int mouseX, int mouseY, float tabX) {
/* 772 */     class_4587 matrix = context.method_51448();
/* 773 */     float tabY = this.y + 8.5F;
/* 774 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, tabX, tabY, 17.0D, 17.0D);
/* 775 */     if (hovered) {
/* 776 */       CursorManager.requestHand();
/*     */     }
/* 778 */     Animation hoverAnimation = this.topTabHoverAnimations.computeIfAbsent("__plus__", ignored -> (new DecelerateAnimation()).setMs(180).setValue(1.0D));
/*     */     
/* 780 */     hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 781 */     float hoverProgress = hoverAnimation.getOutputFloat();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 788 */     int baseTabColor = MenuStyle.PANEL_CHIP;
/* 789 */     int hoverTabColor = MenuStyle.mix(baseTabColor, -1, 0.08F);
/* 790 */     int tabColor = MenuStyle.mix(baseTabColor, hoverTabColor, hoverProgress);
/*     */     
/* 792 */     int baseBorderColor = MenuStyle.BORDER;
/* 793 */     int hoverBorderColor = MenuStyle.mix(MenuStyle.BORDER, MenuStyle.BORDER_LIGHT, 0.45F);
/* 794 */     int borderColor = MenuStyle.mix(baseBorderColor, hoverBorderColor, hoverProgress);
/*     */     
/* 796 */     int baseIconColor = MenuStyle.TEXT_MUTED;
/* 797 */     int hoverIconColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, 0.55F);
/* 798 */     int iconColor = MenuStyle.mix(baseIconColor, hoverIconColor, hoverProgress);
/*     */ 
/*     */     
/* 801 */     rectangle.render(ShapeProperties.create(matrix, tabX, tabY, 17.0D, 17.0D)
/* 802 */         .round(2.0F).thickness(3.0F)
/* 803 */         .outlineColor(applyGlobalAlpha(borderColor))
/* 804 */         .color(MenuStyle.withAlpha(tabColor, 0.0F))
/* 805 */         .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 814 */     float iconSize = 8.5F;
/* 815 */     float chipCx = tabX + 8.5F;
/* 816 */     float chipCy = tabY + 8.5F;
/* 817 */     matrix.method_22903();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 827 */     matrix.method_46416(chipCx, chipCy, 0.0F);
/* 828 */     matrix.method_22907(class_7833.field_40718.rotationDegrees(45.0F));
/* 829 */     matrix.method_46416(-chipCx, -chipCy, 0.0F);
/* 830 */     image.setTexture("phaze:textures/menu/cross.png")
/* 831 */       .render(ShapeProperties.create(matrix, (chipCx - iconSize * 0.5F), (chipCy - iconSize * 0.5F), iconSize, iconSize)
/*     */ 
/*     */         
/* 834 */         .color(applyGlobalAlpha(iconColor))
/* 835 */         .build());
/* 836 */     matrix.method_22909();
/*     */   }
/*     */   
/*     */   private void renderConfigCross(class_4587 matrix, float x, float y, float size, int color) {
/* 840 */     RenderSystem.enableBlend();
/* 841 */     RenderSystem.defaultBlendFunc();
/* 842 */     RenderSystem.setShaderTexture(0, class_2960.method_60655("phaze", "textures/menu/cross.png"));
/* 843 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 845 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 846 */     GL11.glTexParameteri(3553, 10240, 9729);
/*     */     
/* 848 */     Matrix4f positionMatrix = matrix.method_23760().method_23761();
/* 849 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 850 */     buffer.method_22918(positionMatrix, x, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 851 */     buffer.method_22918(positionMatrix, x, y + size, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 852 */     buffer.method_22918(positionMatrix, x + size, y + size, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 853 */     buffer.method_22918(positionMatrix, x + size, y, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 854 */     class_286.method_43433(buffer.method_60800());
/* 855 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private boolean isSettingsTabActive() {
/* 859 */     return (MenuScreen.INSTANCE.getModuleDetailComponent().isOpen() && MenuScreen.INSTANCE
/* 860 */       .getModuleDetailComponent().getModule() == Theme.getInstance());
/*     */   }
/*     */   
/*     */   private boolean isConfigsTabActive() {
/* 864 */     return MenuScreen.INSTANCE.isConfigsViewOpen();
/*     */   }
/*     */   
/*     */   private boolean isCosmeticsTabActive() {
/* 868 */     return MenuScreen.INSTANCE.isCosmeticsViewOpen();
/*     */   }
/*     */   
/*     */   private boolean isChatTabActive() {
/* 872 */     return MenuScreen.INSTANCE.isMessengerViewOpen();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\BackgroundComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */