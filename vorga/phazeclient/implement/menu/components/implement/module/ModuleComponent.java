/*     */ package vorga.phazeclient.implement.menu.components.implement.module;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.SettingComponentAdder;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.api.system.snapshot.CardSnapshotCache;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.other.StringUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
/*     */ 
/*     */ 
/*     */ public class ModuleComponent
/*     */   extends AbstractComponent
/*     */ {
/*  38 */   private static final Gson GSON_COMPACT = new Gson();
/*     */   
/*     */   private static final long COPY_PASTE_COOLDOWN_MS = 150L;
/*     */   private static final float TITLE_TEXT_SIZE = 8.4F;
/*     */   private static final float ROW_TEXT_SIZE = 6.45F;
/*     */   private static final float BIND_TEXT_SIZE = 5.8F;
/*     */   private static final float CARD_INSET = 1.3F;
/*     */   private static final float ROW_INSET = 0.29999995F;
/*     */   private static final float STANDALONE_OPTIONS_RADIUS = 5.2F;
/*     */   private static final float STATE_ROW_RADIUS = 7.0F;
/*     */   private static final float OPTIONS_ROW_HEIGHT = 18.0F;
/*     */   private static final float ENABLED_ROW_HEIGHT = 18.0F;
/*     */   private static final float BASE_HEIGHT_ENABLED = 86.0F;
/*     */   private static final float BASE_HEIGHT_SIMPLE = 62.0F;
/*     */   private static final float BASE_HEIGHT_OPTIONS_ONLY = 68.0F;
/*  53 */   private final List<AbstractSettingComponent> components = new ArrayList<>(); private final Module module; private boolean binding; public List<AbstractSettingComponent> getComponents() { return this.components; } public Module getModule() {
/*  54 */     return this.module;
/*     */   }
/*  56 */   public boolean isBinding() { return this.binding; } private boolean isHovered = false; public boolean isHovered() {
/*  57 */     return this.isHovered;
/*     */   }
/*  59 */   private long lastCopyTime = 0L; public long getLastCopyTime() { return this.lastCopyTime; }
/*  60 */    private long lastPasteTime = 0L; public long getLastPasteTime() { return this.lastPasteTime; }
/*     */   
/*  62 */   private final Animation hoverAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D); public Animation getHoverAnimation() { return this.hoverAnimation; }
/*  63 */    private final Animation optionsHoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D); public Animation getOptionsHoverAnimation() { return this.optionsHoverAnimation; }
/*  64 */    private final Animation outlineColorAnimation = (new DecelerateAnimation()).setMs(300).setValue(1.0D); public Animation getOutlineColorAnimation() { return this.outlineColorAnimation; }
/*  65 */    private final Animation stateRowHoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D); private float visualX; private float visualY; private float prevX; public Animation getStateRowHoverAnimation() { return this.stateRowHoverAnimation; }
/*     */    private float prevY; private boolean lastModuleState; private static final boolean CARD_SNAPSHOT_CACHE_ENABLED = false; public float getVisualX() {
/*  67 */     return this.visualX; } public float getVisualY() { return this.visualY; }
/*  68 */   public float getPrevX() { return this.prevX; } public float getPrevY() { return this.prevY; } public boolean isLastModuleState() {
/*  69 */     return this.lastModuleState;
/*     */   }
/*     */   public ModuleComponent(Module module) {
/*  72 */     this.module = module;
/*  73 */     (new SettingComponentAdder()).addSettingComponent(module.settings(), this.components);
/*  74 */     this.visualX = this.prevX = 0.0F;
/*  75 */     this.visualY = this.prevY = 0.0F;
/*  76 */     this.lastModuleState = module.isState();
/*  77 */     this.hoverAnimation.setDirectionAndFinish(Direction.BACKWARDS);
/*  78 */     this.optionsHoverAnimation.setDirectionAndFinish(Direction.BACKWARDS);
/*  79 */     this.stateRowHoverAnimation.setDirectionAndFinish(Direction.BACKWARDS);
/*  80 */     this.outlineColorAnimation.setDirectionAndFinish(module.isState() ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  88 */     this.visualX = this.x;
/*  89 */     this.visualY = this.y;
/*  90 */     this.height = getComponentHeight();
/*     */     
/*  92 */     boolean inSearchMode = isInSearchMode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     preUpdateAnimations(mouseX, mouseY, inSearchMode);
/*     */     
/* 105 */     if (canUseCache()) {
/* 106 */       renderViaCache(context, mouseX, mouseY, delta, inSearchMode);
/*     */     } else {
/* 108 */       renderImmediate(context, mouseX, mouseY, delta, inSearchMode);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void preUpdateAnimations(int mouseX, int mouseY, boolean inSearchMode) {
/* 132 */     this.isHovered = isHover(mouseX, mouseY);
/* 133 */     this.hoverAnimation.setDirection(this.isHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     if (this.isHovered) {
/* 139 */       CursorManager.requestHand();
/*     */     }
/*     */     
/* 142 */     boolean currentModuleState = this.module.isState();
/* 143 */     if (currentModuleState != this.lastModuleState) {
/* 144 */       this.outlineColorAnimation.setDirection(currentModuleState ? Direction.FORWARDS : Direction.BACKWARDS);
/* 145 */       this.lastModuleState = currentModuleState;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     boolean showStateRow = this.module.isShowEnable();
/*     */     
/* 154 */     if (hasSettings()) {
/* 155 */       boolean rowHovered = isOptionsRowHovered(mouseX, mouseY);
/* 156 */       this.optionsHoverAnimation.setDirection(rowHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     } 
/*     */     
/* 159 */     if (showStateRow) {
/* 160 */       boolean stateRowHovered = isStateRowHovered(mouseX, mouseY);
/* 161 */       this.stateRowHoverAnimation.setDirection(stateRowHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 162 */       if (stateRowHovered && this.module.isServerLocked()) {
/* 163 */         CursorManager.requestNotAllowed();
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canUseCache() {
/* 208 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderViaCache(class_332 context, int mouseX, int mouseY, float delta, boolean inSearchMode) {
/* 237 */     class_310 mc = class_310.method_1551();
/* 238 */     if (mc == null || mc.method_22683() == null) {
/* 239 */       renderImmediate(context, mouseX, mouseY, delta, inSearchMode);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 248 */     int scale = Math.max(1, (int)Math.ceil(mc.method_22683().method_4495()));
/* 249 */     int fbW = Math.max(1, Math.round(this.width * scale));
/* 250 */     int fbH = Math.max(1, Math.round(this.height * scale));
/*     */     
/* 252 */     int hash = computeStateHash();
/* 253 */     CardSnapshotCache.Snapshot snapshot = CardSnapshotCache.getOrCreate(this, fbW, fbH);
/*     */     
/* 255 */     if (!snapshot.populated || snapshot.hash != hash) {
/* 256 */       CardSnapshotCache.beginCapture(snapshot, this.width, this.height);
/*     */       try {
/* 258 */         context.method_51448().method_22903();
/* 259 */         context.method_51448().method_46416(-this.x, -this.y, 0.0F);
/* 260 */         renderImmediate(context, mouseX, mouseY, delta, inSearchMode);
/* 261 */         context.method_51448().method_22909();
/* 262 */         snapshot.hash = hash;
/*     */       } finally {
/* 264 */         CardSnapshotCache.endCapture();
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     CardSnapshotCache.blit(context, snapshot, this.x, this.y, this.width, this.height, this.globalAlpha);
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
/*     */   private int computeStateHash() {
/* 283 */     int h = 1;
/* 284 */     h = h * 31 + Boolean.hashCode(this.module.isState());
/* 285 */     h = h * 31 + Boolean.hashCode(this.module.isCanBind());
/* 286 */     h = h * 31 + Boolean.hashCode(this.module.isShowEnable());
/* 287 */     h = h * 31 + Boolean.hashCode(this.module.isServerLocked());
/* 288 */     h = h * 31 + Integer.hashCode(this.module.getKey());
/* 289 */     h = h * 31 + Boolean.hashCode(hasSettings());
/* 290 */     h = h * 31 + Boolean.hashCode(this.isHovered);
/* 291 */     h = h * 31 + Boolean.hashCode(this.binding);
/*     */     
/* 293 */     h = h * 31 + Float.floatToIntBits(this.width);
/* 294 */     h = h * 31 + Float.floatToIntBits(this.height);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     h = h * 31 + Integer.hashCode(MenuStyle.BORDER);
/* 301 */     h = h * 31 + Integer.hashCode(MenuStyle.BORDER_LIGHT);
/* 302 */     h = h * 31 + Integer.hashCode(MenuStyle.CARD_OPTIONS);
/* 303 */     h = h * 31 + Integer.hashCode(MenuStyle.PANEL_CHIP);
/* 304 */     h = h * 31 + Integer.hashCode(MenuStyle.CHIP_ACTIVE);
/* 305 */     h = h * 31 + Integer.hashCode(MenuStyle.TEXT_PRIMARY);
/* 306 */     h = h * 31 + Integer.hashCode(MenuStyle.TEXT_MUTED);
/* 307 */     h = h * 31 + Integer.hashCode(MenuStyle.ACCENT_GREEN);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     h = h * 31 + (int)(this.hoverAnimation.getOutputFloat() * 8.0F);
/* 315 */     h = h * 31 + (int)(this.optionsHoverAnimation.getOutputFloat() * 8.0F);
/* 316 */     h = h * 31 + (int)(this.outlineColorAnimation.getOutputFloat() * 8.0F);
/* 317 */     h = h * 31 + (int)(this.stateRowHoverAnimation.getOutputFloat() * 8.0F);
/*     */     
/* 319 */     return h;
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
/*     */   private void renderImmediate(class_332 context, int mouseX, int mouseY, float delta, boolean inSearchMode) {
/* 331 */     float offsetX = this.visualX - this.x;
/* 332 */     float offsetY = this.visualY - this.y;
/*     */     
/* 334 */     float hoverProgress = this.hoverAnimation.getOutputFloat();
/* 335 */     float baseHeight = getBaseHeightFloat();
/* 336 */     boolean showOptionsRow = hasSettings();
/* 337 */     boolean showStateRow = this.module.isShowEnable();
/* 338 */     float optionsY = getOptionsRowY();
/*     */     
/* 340 */     context.method_51448().method_22903();
/* 341 */     context.method_51448().method_46416(offsetX, offsetY, 0.0F);
/*     */     
/* 343 */     int outlineColor = MenuStyle.mix(MenuStyle.BORDER, MenuStyle.CHIP_ACTIVE, this.outlineColorAnimation.getOutputFloat() * 0.75F);
/* 344 */     outlineColor = MenuStyle.withAlpha(outlineColor, applyGlobalAlpha(0.96F));
/* 345 */     int hoverFillColor = MenuStyle.withAlpha(-1, applyGlobalAlpha(hoverProgress * 0.06F));
/* 346 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.x, this.y, this.width, this.height)
/* 347 */         .round(8.0F).softness(1.0F).thickness(0.0F).outlineColor(0).color(hoverFillColor).build());
/*     */     
/* 349 */     float iconSize = this.module.getIconSize();
/* 350 */     float iconX = this.x + (this.width - iconSize) / 2.0F;
/* 351 */     float iconY = this.y + 12.0F + this.module.getIconOffsetY();
/* 352 */     String iconTexture = (this.module.getIcon() != null) ? ("phaze:textures/modules/" + this.module.getIcon()) : "phaze:textures/modules/hand.png";
/* 353 */     int iconColor = applyGlobalAlpha(MenuStyle.TEXT_PRIMARY);
/* 354 */     if (!UiMsdfIconAtlas.renderIcon(context, class_2960.method_60654(iconTexture), iconX, iconY, iconSize, iconSize, iconColor, true)) {
/* 355 */       image.setTexture(iconTexture)
/* 356 */         .render(ShapeProperties.create(context.method_51448(), iconX, iconY, iconSize, iconSize)
/* 357 */           .color(iconColor).build());
/*     */     }
/*     */     
/* 360 */     String moduleName = this.module.getLocalizedName();
/* 361 */     float titleAreaY = this.y + 31.0F;
/* 362 */     float titleAreaBottom = showOptionsRow ? (optionsY - 1.0F) : (this.y + baseHeight - 7.0F);
/* 363 */     float titleAreaHeight = Math.max(10.0F, titleAreaBottom - titleAreaY);
/* 364 */     MsdfRenderer.renderText(
/* 365 */         MsdfFonts.medium(), moduleName, 8.4F, 
/*     */ 
/*     */         
/* 368 */         applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/* 369 */         .method_51448().method_23760().method_23761(), 
/* 370 */         MenuStyle.centerMsdfTextX(MsdfFonts.medium(), moduleName, 8.4F, this.x, this.width), 
/* 371 */         MenuStyle.centerMsdfTextY(8.4F, titleAreaY, titleAreaHeight), 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (showOptionsRow) {
/* 376 */       renderOptionsRow(context, mouseX, mouseY, optionsY, !showStateRow, inSearchMode);
/*     */     }
/*     */     
/* 379 */     if (showStateRow) {
/* 380 */       float enabledY = getStateRowY();
/* 381 */       boolean stateRowHovered = isStateRowHovered(mouseX, mouseY);
/* 382 */       this.stateRowHoverAnimation.setDirection(stateRowHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 383 */       renderStateRow(context, enabledY, this.stateRowHoverAnimation.getOutputFloat());
/*     */     } 
/*     */     
/* 386 */     if (showStateRow && this.module.isCanBind()) {
/* 387 */       drawBind(context, this.x, this.y);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 392 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.x, this.y, this.width, this.height)
/* 393 */         .round(8.0F).softness(1.0F).thickness(3.6F).outlineColor(outlineColor)
/* 394 */         .color(0).build());
/*     */     
/* 396 */     context.method_51448().method_22909();
/*     */   }
/*     */   
/*     */   private void renderOptionsRow(class_332 context, int mouseX, int mouseY, float optionsY, boolean standaloneRow, boolean inSearchMode) {
/* 400 */     boolean hasSettings = hasSettings();
/*     */ 
/*     */     
/* 403 */     int baseRowColor = hasSettings ? MenuStyle.CARD_OPTIONS : MenuStyle.mix(MenuStyle.CARD_OPTIONS, MenuStyle.PANEL_CHIP, 0.45F);
/* 404 */     float iconSectionWidth = 26.0F;
/* 405 */     float rowX = getRowX(standaloneRow);
/* 406 */     float rowWidth = getRowWidth(standaloneRow);
/* 407 */     float dividerX = rowX + rowWidth - iconSectionWidth;
/* 408 */     boolean rowHovered = isOptionsRowHovered(mouseX, mouseY);
/* 409 */     this.optionsHoverAnimation.setDirection(rowHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 410 */     float rowHoverProgress = this.optionsHoverAnimation.getOutputFloat();
/* 411 */     int rowColor = MenuStyle.mix(baseRowColor, MenuStyle.TEXT_PRIMARY, rowHoverProgress * 0.055F);
/* 412 */     int borderColor = MenuStyle.withAlpha(MenuStyle.BORDER_LIGHT, applyGlobalAlpha(standaloneRow ? (0.45F + rowHoverProgress * 0.17F) : (0.35F + rowHoverProgress * 0.13F)));
/*     */     
/* 414 */     rectangle.render(ShapeProperties.create(context.method_51448(), rowX, optionsY, rowWidth, 18.0D)
/* 415 */         .round(new float[] { 0.0F, standaloneRow ? 5.2F : 0.0F, 0.0F, standaloneRow ? 5.2F : 0.0F }).color(applyGlobalAlpha(rowColor)).build());
/*     */     
/* 417 */     rectangle.render(ShapeProperties.create(context.method_51448(), rowX, optionsY, rowWidth, 1.0D)
/* 418 */         .color(borderColor).build());
/*     */     
/* 420 */     rectangle.render(ShapeProperties.create(context.method_51448(), dividerX, optionsY, 1.0D, (18.0F - (standaloneRow ? 0.35F : 0.0F)))
/* 421 */         .color(MenuStyle.withAlpha(MenuStyle.BORDER_LIGHT, applyGlobalAlpha(0.55F + rowHoverProgress * 0.15F))).build());
/*     */     
/* 423 */     String optionsLabel = "OPTIONS";
/* 424 */     MsdfRenderer.renderText(
/* 425 */         MsdfFonts.bold(), optionsLabel, 6.45F, 
/*     */ 
/*     */         
/* 428 */         applyGlobalAlpha(hasSettings ? MenuStyle.mix(MenuStyle.TEXT_PRIMARY, -1, rowHoverProgress * 0.1F) : MenuStyle.mix(MenuStyle.TEXT_MUTED, -1, rowHoverProgress * 0.08F)), context
/* 429 */         .method_51448().method_23760().method_23761(), rowX + 11.0F, 
/*     */         
/* 431 */         MenuStyle.centerMsdfTextY(6.45F, optionsY, 18.0F), 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 435 */     if (hasSettings) {
/* 436 */       float iconX = dividerX + (iconSectionWidth - 9.0F) / 2.0F;
/* 437 */       float iconY = optionsY + 4.5F;
/* 438 */       int iconColor = applyGlobalAlpha(MenuStyle.mix(MenuStyle.TEXT_PRIMARY, -1, rowHoverProgress * 0.1F));
/* 439 */       if (!UiMsdfIconAtlas.renderIcon(context, 
/* 440 */           class_2960.method_60655("phaze", "textures/menu/options_settings.png"), iconX, iconY, 9.0F, 9.0F, iconColor, true))
/*     */       {
/* 442 */         image.setTexture("textures/settings.png")
/* 443 */           .render(ShapeProperties.create(context.method_51448(), iconX, iconY, 9.0D, 9.0D)
/* 444 */             .color(iconColor).build()); } 
/*     */     } 
/*     */   }
/*     */   private void renderStateRow(class_332 context, float enabledY, float hoverProgress) {
/*     */     int stateColor;
/*     */     String stateText;
/* 450 */     boolean locked = this.module.isServerLocked();
/*     */ 
/*     */ 
/*     */     
/* 454 */     if (locked) {
/* 455 */       stateColor = applyGlobalAlpha(-7829114);
/* 456 */       stateText = "LOCKED";
/*     */     } else {
/* 458 */       float stateProgress = this.outlineColorAnimation.getOutputFloat();
/* 459 */       int base = MenuStyle.mix(-6283712, MenuStyle.ACCENT_GREEN, stateProgress);
/* 460 */       stateColor = applyGlobalAlpha(MenuStyle.mix(base, MenuStyle.TEXT_PRIMARY, hoverProgress * 0.15F));
/* 461 */       stateText = this.module.isState() ? "ENABLED" : "DISABLED";
/*     */     } 
/*     */ 
/*     */     
/* 465 */     rectangle.render(ShapeProperties.create(context.method_51448(), getRowX(false), enabledY, getRowWidth(false), 18.0D)
/* 466 */         .round(new float[] { 0.0F, 7.0F, 0.0F, 7.0F }).color(stateColor).build());
/*     */     
/* 468 */     MsdfRenderer.renderText(
/* 469 */         MsdfFonts.bold(), stateText, 6.45F, 
/*     */ 
/*     */         
/* 472 */         applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), context
/* 473 */         .method_51448().method_23760().method_23761(), 
/* 474 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), stateText, 6.45F, this.x, this.width), 
/* 475 */         MenuStyle.centerMsdfTextY(6.45F, enabledY, 18.0F), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 482 */     boolean showOptionsRow = hasSettings();
/* 483 */     boolean showStateRow = this.module.isShowEnable();
/* 484 */     boolean optionsHovered = isOptionsRowHovered(mouseX, mouseY);
/* 485 */     boolean settingsClick = (button == 0 || button == 1);
/*     */     
/* 487 */     if (showOptionsRow && settingsClick && optionsHovered) {
/* 488 */       if (this.module.isServerLocked()) {
/* 489 */         return true;
/*     */       }
/* 491 */       playButtonClickSound();
/* 492 */       MenuScreen.INSTANCE.openModuleDetail(this.module);
/* 493 */       return true;
/*     */     } 
/*     */     
/* 496 */     if (showStateRow && button == 0 && isStateRowHovered(mouseX, mouseY)) {
/* 497 */       if (this.module.isServerLocked()) {
/* 498 */         return true;
/*     */       }
/* 500 */       playButtonClickSound();
/* 501 */       this.module.switchState();
/* 502 */       return true;
/*     */     } 
/*     */     
/* 505 */     if (showStateRow && this.module.isCanBind()) {
/* 506 */       String bindName = StringUtil.getBindName(this.module.getKey());
/* 507 */       float stringWidth = MsdfFonts.bold().getWidth(bindName, 5.8F);
/* 508 */       if (MathUtil.isHovered(mouseX, mouseY, (this.x + this.width - 15.0F - stringWidth), (this.y + 8.0F), (stringWidth + 6.0F), 9.0D) && button == 0) {
/* 509 */         playButtonClickSound();
/* 510 */         this.binding = !this.binding;
/* 511 */         return true;
/* 512 */       }  if (this.binding) {
/* 513 */         this.module.setKey(button);
/* 514 */         this.binding = false;
/* 515 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 519 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 524 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 525 */     float menuTop = menuScreen.y;
/* 526 */     float menuBottom = (menuScreen.y + menuScreen.height);
/* 527 */     float menuLeft = menuScreen.x;
/* 528 */     float menuRight = (menuScreen.x + menuScreen.width);
/*     */     
/* 530 */     if (mouseX < menuLeft || mouseX > menuRight || mouseY < menuTop || mouseY > menuBottom) {
/* 531 */       return false;
/*     */     }
/*     */     
/* 534 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/* 535 */     if (!hovered) {
/* 536 */       return false;
/*     */     }
/*     */     
/* 539 */     float visibleTop = Math.max(this.y, menuTop);
/* 540 */     float visibleBottom = Math.min(this.y + this.height, menuBottom);
/* 541 */     return (mouseY >= visibleTop && mouseY <= visibleBottom);
/*     */   }
/*     */   
/*     */   private boolean hasSettings() {
/* 545 */     return !this.components.isEmpty();
/*     */   }
/*     */   
/*     */   private float getRowX(boolean standaloneRow) {
/* 549 */     return this.x + (standaloneRow ? 0.0F : 0.29999995F);
/*     */   }
/*     */   
/*     */   private float getRowWidth(boolean standaloneRow) {
/* 553 */     return this.width - (standaloneRow ? 0.0F : 0.5999999F);
/*     */   }
/*     */   
/*     */   private float getOptionsRowY() {
/* 557 */     return this.y + getOptionsRowOffsetY();
/*     */   }
/*     */   
/*     */   private float getOptionsRowOffsetY() {
/* 561 */     return getBaseHeightFloat() - 18.0F - (
/* 562 */       this.module.isShowEnable() ? 18.0F : 0.0F);
/*     */   }
/*     */   
/*     */   private float getStateRowY() {
/* 566 */     return getOptionsRowY() + 18.0F;
/*     */   }
/*     */   
/*     */   private boolean isOptionsRowHovered(double mouseX, double mouseY) {
/* 570 */     boolean standaloneRow = !this.module.isShowEnable();
/* 571 */     return (hasSettings() && isRowHovered(mouseX, mouseY, standaloneRow, getOptionsRowY(), 18.0F, 
/* 572 */         standaloneRow ? 5.2F : 0.0F));
/*     */   }
/*     */   
/*     */   private boolean isStateRowHovered(double mouseX, double mouseY) {
/* 576 */     return (this.module.isShowEnable() && isRowHovered(mouseX, mouseY, false, getStateRowY(), 18.0F, 7.0F));
/*     */   }
/*     */   
/*     */   private boolean isRowHovered(double mouseX, double mouseY, boolean standaloneRow, float rowY, float rowHeight, float bottomRadius) {
/* 580 */     float rowX = getRowX(standaloneRow);
/* 581 */     float rowWidth = getRowWidth(standaloneRow);
/* 582 */     float bottom = rowY + rowHeight;
/*     */     
/* 584 */     if (!MathUtil.isHovered(mouseX, mouseY, rowX, rowY, rowWidth, rowHeight) || mouseY >= bottom) {
/* 585 */       return false;
/*     */     }
/* 587 */     if (bottomRadius > 0.0F && mouseY > (bottom - bottomRadius)) {
/* 588 */       double cornerX = Math.max((rowX + bottomRadius), Math.min(mouseX, (rowX + rowWidth - bottomRadius)));
/* 589 */       double dx = mouseX - cornerX;
/* 590 */       double dy = mouseY - (bottom - bottomRadius);
/* 591 */       return (dx * dx + dy * dy <= (bottomRadius * bottomRadius));
/*     */     } 
/* 593 */     return true;
/*     */   }
/*     */   
/*     */   private float getBaseHeightFloat() {
/* 597 */     if (this.module.isShowEnable()) {
/* 598 */       return 86.0F;
/*     */     }
/* 600 */     return hasSettings() ? 68.0F : 62.0F;
/*     */   }
/*     */   
/*     */   public int getComponentHeight() {
/* 604 */     float bottom = getBaseHeightFloat();
/* 605 */     if (this.module.isShowEnable()) {
/* 606 */       bottom = Math.max(bottom, getOptionsRowOffsetY() + 18.0F + 18.0F);
/*     */     }
/* 608 */     return (int)Math.ceil(bottom);
/*     */   }
/*     */   
/*     */   private boolean isInSearchMode() {
/* 612 */     return (MenuScreen.INSTANCE != null && MenuScreen.INSTANCE.getCategory() == ModuleCategory.SEARCH);
/*     */   }
/*     */   
/*     */   private void drawBind(class_332 context, float renderX, float renderY) {
/* 616 */     String bindName = StringUtil.getBindName(this.module.getKey());
/* 617 */     String name = this.binding ? ("(" + bindName + ") ...") : bindName;
/* 618 */     float stringWidth = MsdfFonts.bold().getWidth(name, 5.8F);
/* 619 */     float bindX = renderX + this.width - stringWidth - 18.0F;
/* 620 */     float bindY = renderY + 6.0F;
/*     */     
/* 622 */     rectangle.render(ShapeProperties.create(context.method_51448(), bindX, bindY, (stringWidth + 8.0F), 10.0D)
/* 623 */         .round(2.0F).thickness(1.2F).outlineColor(applyGlobalAlpha(MenuStyle.BORDER)).color(applyGlobalAlpha(MenuStyle.PANEL_CHIP)).build());
/*     */     
/* 625 */     MsdfRenderer.renderText(
/* 626 */         MsdfFonts.bold(), name, 5.8F, 
/*     */ 
/*     */         
/* 629 */         applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/* 630 */         .method_51448().method_23760().method_23761(), bindX + 4.0F, 
/*     */         
/* 632 */         MenuStyle.centerMsdfTextY(5.8F, bindY, 10.0F), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 639 */     int key = (keyCode == 261) ? -1 : keyCode;
/* 640 */     if (this.binding && this.module.isCanBind()) {
/* 641 */       this.module.setKey(key);
/* 642 */       this.binding = false;
/* 643 */       return true;
/*     */     } 
/*     */     
/* 646 */     if (keyCode == 67 && (modifiers & 0x2) != 0 && 
/* 647 */       this.isHovered) {
/* 648 */       copyModuleConfig();
/* 649 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 653 */     if (keyCode == 86 && (modifiers & 0x2) != 0) {
/* 654 */       pasteModuleConfig();
/*     */     }
/*     */     
/* 657 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */   
/*     */   private void copyModuleConfig() {
/* 661 */     long currentTime = System.currentTimeMillis();
/* 662 */     if (currentTime - this.lastCopyTime < 150L) {
/*     */       return;
/*     */     }
/* 665 */     this.lastCopyTime = currentTime;
/*     */     
/*     */     try {
/* 668 */       JsonObject rootObject = new JsonObject();
/* 669 */       JsonObject moduleData = new JsonObject();
/*     */       
/* 671 */       rootObject.addProperty("moduleId", this.module.getIdentifier());
/* 672 */       rootObject.addProperty("moduleName", this.module.getName());
/*     */       
/* 674 */       moduleData.addProperty("enabled", Boolean.valueOf(this.module.isState()));
/* 675 */       moduleData.addProperty("bind", Integer.valueOf(this.module.getKey()));
/*     */       
/* 677 */       for (Setting setting : this.module.settings()) {
/* 678 */         saveSetting(setting, moduleData);
/*     */       }
/*     */       
/* 681 */       rootObject.add("config", (JsonElement)moduleData);
/*     */       
/* 683 */       String jsonString = GSON_COMPACT.toJson((JsonElement)rootObject);
/*     */       
/* 685 */       class_310 client = class_310.method_1551();
/* 686 */       if (client == null || client.method_22683() == null) {
/*     */         return;
/*     */       }
/*     */       
/* 690 */       long windowHandle = client.method_22683().method_4490();
/* 691 */       GLFW.glfwSetClipboardString(windowHandle, jsonString);
/* 692 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void pasteModuleConfig() {
/* 697 */     long currentTime = System.currentTimeMillis();
/* 698 */     if (currentTime - this.lastPasteTime < 150L) {
/*     */       return;
/*     */     }
/* 701 */     this.lastPasteTime = currentTime;
/*     */     
/*     */     try {
/* 704 */       class_310 client = class_310.method_1551();
/* 705 */       if (client == null || client.method_22683() == null) {
/*     */         return;
/*     */       }
/*     */       
/* 709 */       long windowHandle = client.method_22683().method_4490();
/* 710 */       String jsonString = GLFW.glfwGetClipboardString(windowHandle);
/*     */       
/* 712 */       if (jsonString == null || jsonString.trim().isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 716 */       JsonObject rootObject = (JsonObject)GSON_COMPACT.fromJson(jsonString.trim(), JsonObject.class);
/* 717 */       if (!rootObject.has("moduleId") || !rootObject.has("config")) {
/*     */         return;
/*     */       }
/*     */       
/* 721 */       String clipboardModuleId = rootObject.get("moduleId").getAsString();
/* 722 */       if (!clipboardModuleId.equals(this.module.getIdentifier())) {
/*     */         return;
/*     */       }
/*     */       
/* 726 */       JsonObject moduleData = rootObject.getAsJsonObject("config");
/*     */       
/* 728 */       if (moduleData.has("enabled")) {
/* 729 */         this.module.setStateSilent(moduleData.get("enabled").getAsBoolean());
/*     */       }
/*     */       
/* 732 */       if (moduleData.has("bind")) {
/* 733 */         this.module.setKey(moduleData.get("bind").getAsInt());
/*     */       }
/*     */       
/* 736 */       for (Setting setting : this.module.settings()) {
/*     */         try {
/* 738 */           loadSetting(setting, moduleData);
/* 739 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/* 743 */       if (this.module.isEnabled()) {
/* 744 */         this.module.setStateSilent(true);
/*     */       }
/* 746 */     } catch (Exception exception) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveSetting(Setting setting, JsonObject moduleData) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual isSaveToConfig : ()Z
/*     */     //   4: ifne -> 8
/*     */     //   7: return
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual getNameKey : ()Ljava/lang/String;
/*     */     //   12: astore_3
/*     */     //   13: aload_1
/*     */     //   14: dup
/*     */     //   15: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   18: pop
/*     */     //   19: astore #4
/*     */     //   21: iconst_0
/*     */     //   22: istore #5
/*     */     //   24: aload #4
/*     */     //   26: iload #5
/*     */     //   28: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   33: tableswitch default -> 609, 0 -> 88, 1 -> 111, 2 -> 134, 3 -> 180, 4 -> 203, 5 -> 226, 6 -> 246, 7 -> 272, 8 -> 411, 9 -> 522
/*     */     //   88: aload #4
/*     */     //   90: checkcast vorga/phazeclient/api/feature/module/setting/implement/BooleanSetting
/*     */     //   93: astore #6
/*     */     //   95: aload_2
/*     */     //   96: aload_3
/*     */     //   97: aload #6
/*     */     //   99: invokevirtual isValue : ()Z
/*     */     //   102: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   105: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   108: goto -> 609
/*     */     //   111: aload #4
/*     */     //   113: checkcast vorga/phazeclient/api/feature/module/setting/implement/ValueSetting
/*     */     //   116: astore #7
/*     */     //   118: aload_2
/*     */     //   119: aload_3
/*     */     //   120: aload #7
/*     */     //   122: invokevirtual getValue : ()F
/*     */     //   125: invokestatic valueOf : (F)Ljava/lang/Float;
/*     */     //   128: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   131: goto -> 609
/*     */     //   134: aload #4
/*     */     //   136: checkcast vorga/phazeclient/api/feature/module/setting/implement/TextSetting
/*     */     //   139: astore #8
/*     */     //   141: aload #8
/*     */     //   143: invokevirtual getText : ()Ljava/lang/String;
/*     */     //   146: astore #9
/*     */     //   148: aload #9
/*     */     //   150: ldc_w ' '
/*     */     //   153: ldc_w '%%'
/*     */     //   156: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   159: ldc_w '/'
/*     */     //   162: ldc_w '++'
/*     */     //   165: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   168: astore #9
/*     */     //   170: aload_2
/*     */     //   171: aload_3
/*     */     //   172: aload #9
/*     */     //   174: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   177: goto -> 609
/*     */     //   180: aload #4
/*     */     //   182: checkcast vorga/phazeclient/api/feature/module/setting/implement/BindSetting
/*     */     //   185: astore #9
/*     */     //   187: aload_2
/*     */     //   188: aload_3
/*     */     //   189: aload #9
/*     */     //   191: invokevirtual getKey : ()I
/*     */     //   194: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   197: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   200: goto -> 609
/*     */     //   203: aload #4
/*     */     //   205: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   208: astore #10
/*     */     //   210: aload_2
/*     */     //   211: aload_3
/*     */     //   212: aload #10
/*     */     //   214: invokevirtual getColor : ()I
/*     */     //   217: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   220: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   223: goto -> 609
/*     */     //   226: aload #4
/*     */     //   228: checkcast vorga/phazeclient/api/feature/module/setting/implement/SelectSetting
/*     */     //   231: astore #11
/*     */     //   233: aload_2
/*     */     //   234: aload_3
/*     */     //   235: aload #11
/*     */     //   237: invokevirtual getSelected : ()Ljava/lang/String;
/*     */     //   240: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   243: goto -> 609
/*     */     //   246: aload #4
/*     */     //   248: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting
/*     */     //   251: astore #12
/*     */     //   253: aload_2
/*     */     //   254: aload_3
/*     */     //   255: ldc_w ','
/*     */     //   258: aload #12
/*     */     //   260: invokevirtual getSelected : ()Ljava/util/List;
/*     */     //   263: invokestatic join : (Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
/*     */     //   266: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   269: goto -> 609
/*     */     //   272: aload #4
/*     */     //   274: checkcast vorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting
/*     */     //   277: astore #13
/*     */     //   279: aload #13
/*     */     //   281: invokevirtual isCustomPicker : ()Z
/*     */     //   284: ifeq -> 305
/*     */     //   287: aload #13
/*     */     //   289: invokevirtual isActive : ()Z
/*     */     //   292: ifne -> 305
/*     */     //   295: aload_2
/*     */     //   296: aload_3
/*     */     //   297: iconst_0
/*     */     //   298: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   301: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   304: return
/*     */     //   305: new com/google/gson/JsonObject
/*     */     //   308: dup
/*     */     //   309: invokespecial <init> : ()V
/*     */     //   312: astore #14
/*     */     //   314: aload #14
/*     */     //   316: ldc_w 'active'
/*     */     //   319: aload #13
/*     */     //   321: invokevirtual isActive : ()Z
/*     */     //   324: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   327: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   330: aload #14
/*     */     //   332: ldc_w 'enabled'
/*     */     //   335: aload #13
/*     */     //   337: invokevirtual isEnabled : ()Z
/*     */     //   340: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   343: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   346: aload #14
/*     */     //   348: ldc_w 'highlightColor'
/*     */     //   351: aload #13
/*     */     //   353: invokevirtual getHighlightColor : ()I
/*     */     //   356: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   359: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   362: aload #14
/*     */     //   364: ldc_w 'itemId'
/*     */     //   367: aload #13
/*     */     //   369: invokevirtual getItemId : ()Ljava/lang/String;
/*     */     //   372: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   375: aload #14
/*     */     //   377: ldc_w 'displayName'
/*     */     //   380: aload #13
/*     */     //   382: invokevirtual getDisplayName : ()Ljava/lang/String;
/*     */     //   385: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   388: aload #14
/*     */     //   390: ldc_w 'matchName'
/*     */     //   393: aload #13
/*     */     //   395: invokevirtual getMatchName : ()Ljava/lang/String;
/*     */     //   398: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   401: aload_2
/*     */     //   402: aload_3
/*     */     //   403: aload #14
/*     */     //   405: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   408: goto -> 609
/*     */     //   411: aload #4
/*     */     //   413: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting
/*     */     //   416: astore #14
/*     */     //   418: new com/google/gson/JsonObject
/*     */     //   421: dup
/*     */     //   422: invokespecial <init> : ()V
/*     */     //   425: astore #15
/*     */     //   427: aload #15
/*     */     //   429: ldc_w 'selectedColorIndex'
/*     */     //   432: aload #14
/*     */     //   434: invokevirtual getSelectedColorIndex : ()I
/*     */     //   437: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   440: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   443: new com/google/gson/JsonArray
/*     */     //   446: dup
/*     */     //   447: invokespecial <init> : ()V
/*     */     //   450: astore #16
/*     */     //   452: aload #14
/*     */     //   454: invokevirtual getAllColors : ()Ljava/util/List;
/*     */     //   457: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   462: astore #17
/*     */     //   464: aload #17
/*     */     //   466: invokeinterface hasNext : ()Z
/*     */     //   471: ifeq -> 502
/*     */     //   474: aload #17
/*     */     //   476: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   481: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   484: astore #18
/*     */     //   486: aload #16
/*     */     //   488: aload #18
/*     */     //   490: invokevirtual getColor : ()I
/*     */     //   493: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   496: invokevirtual add : (Ljava/lang/Number;)V
/*     */     //   499: goto -> 464
/*     */     //   502: aload #15
/*     */     //   504: ldc_w 'colors'
/*     */     //   507: aload #16
/*     */     //   509: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   512: aload_2
/*     */     //   513: aload_3
/*     */     //   514: aload #15
/*     */     //   516: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   519: goto -> 609
/*     */     //   522: aload #4
/*     */     //   524: checkcast vorga/phazeclient/api/feature/module/setting/implement/GroupSetting
/*     */     //   527: astore #15
/*     */     //   529: new com/google/gson/JsonObject
/*     */     //   532: dup
/*     */     //   533: invokespecial <init> : ()V
/*     */     //   536: astore #16
/*     */     //   538: aload #16
/*     */     //   540: ldc_w 'state'
/*     */     //   543: aload #15
/*     */     //   545: invokevirtual isValue : ()Z
/*     */     //   548: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   551: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   554: aload #15
/*     */     //   556: invokevirtual getSubSettings : ()Ljava/util/List;
/*     */     //   559: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   564: astore #17
/*     */     //   566: aload #17
/*     */     //   568: invokeinterface hasNext : ()Z
/*     */     //   573: ifeq -> 599
/*     */     //   576: aload #17
/*     */     //   578: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   583: checkcast vorga/phazeclient/api/feature/module/setting/Setting
/*     */     //   586: astore #18
/*     */     //   588: aload_0
/*     */     //   589: aload #18
/*     */     //   591: aload #16
/*     */     //   593: invokevirtual saveSetting : (Lvorga/phazeclient/api/feature/module/setting/Setting;Lcom/google/gson/JsonObject;)V
/*     */     //   596: goto -> 566
/*     */     //   599: aload_2
/*     */     //   600: aload_3
/*     */     //   601: aload #16
/*     */     //   603: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   606: goto -> 609
/*     */     //   609: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #751	-> 0
/*     */     //   #752	-> 7
/*     */     //   #755	-> 8
/*     */     //   #757	-> 13
/*     */     //   #758	-> 88
/*     */     //   #759	-> 111
/*     */     //   #760	-> 134
/*     */     //   #761	-> 141
/*     */     //   #762	-> 148
/*     */     //   #763	-> 170
/*     */     //   #764	-> 177
/*     */     //   #765	-> 180
/*     */     //   #766	-> 203
/*     */     //   #767	-> 226
/*     */     //   #768	-> 246
/*     */     //   #769	-> 272
/*     */     //   #770	-> 279
/*     */     //   #771	-> 295
/*     */     //   #772	-> 304
/*     */     //   #774	-> 305
/*     */     //   #775	-> 314
/*     */     //   #776	-> 330
/*     */     //   #777	-> 346
/*     */     //   #778	-> 362
/*     */     //   #779	-> 375
/*     */     //   #780	-> 388
/*     */     //   #781	-> 401
/*     */     //   #782	-> 408
/*     */     //   #783	-> 411
/*     */     //   #784	-> 418
/*     */     //   #785	-> 427
/*     */     //   #787	-> 443
/*     */     //   #788	-> 452
/*     */     //   #789	-> 486
/*     */     //   #790	-> 499
/*     */     //   #791	-> 502
/*     */     //   #792	-> 512
/*     */     //   #793	-> 519
/*     */     //   #794	-> 522
/*     */     //   #795	-> 529
/*     */     //   #796	-> 538
/*     */     //   #798	-> 554
/*     */     //   #799	-> 588
/*     */     //   #800	-> 596
/*     */     //   #802	-> 599
/*     */     //   #803	-> 606
/*     */     //   #807	-> 609
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   95	16	6	booleanSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   118	16	7	valueSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*     */     //   148	29	9	value	Ljava/lang/String;
/*     */     //   141	39	8	textSetting	Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*     */     //   187	16	9	bindSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*     */     //   210	16	10	colorSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   233	13	11	selectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/SelectSetting;
/*     */     //   253	19	12	multiSelectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;
/*     */     //   314	94	14	itemObject	Lcom/google/gson/JsonObject;
/*     */     //   279	132	13	itemPickerSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting;
/*     */     //   486	13	18	color	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   427	92	15	colorObject	Lcom/google/gson/JsonObject;
/*     */     //   452	67	16	colorsArray	Lcom/google/gson/JsonArray;
/*     */     //   418	104	14	multiColor	Lvorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting;
/*     */     //   588	8	18	subSetting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   538	68	16	groupObject	Lcom/google/gson/JsonObject;
/*     */     //   529	80	15	group	Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*     */     //   0	610	0	this	Lvorga/phazeclient/implement/menu/components/implement/module/ModuleComponent;
/*     */     //   0	610	1	setting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   0	610	2	moduleData	Lcom/google/gson/JsonObject;
/*     */     //   13	597	3	key	Ljava/lang/String;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadSetting(Setting setting, JsonObject moduleData) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual isSaveToConfig : ()Z
/*     */     //   4: ifne -> 8
/*     */     //   7: return
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual getNameKey : ()Ljava/lang/String;
/*     */     //   12: astore_3
/*     */     //   13: aload_2
/*     */     //   14: aload_3
/*     */     //   15: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   18: ifne -> 22
/*     */     //   21: return
/*     */     //   22: aload_2
/*     */     //   23: aload_3
/*     */     //   24: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   27: astore #4
/*     */     //   29: aload_1
/*     */     //   30: dup
/*     */     //   31: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   34: pop
/*     */     //   35: astore #5
/*     */     //   37: iconst_0
/*     */     //   38: istore #6
/*     */     //   40: aload #5
/*     */     //   42: iload #6
/*     */     //   44: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   49: tableswitch default -> 844, 0 -> 104, 1 -> 125, 2 -> 146, 3 -> 193, 4 -> 214, 5 -> 235, 6 -> 255, 7 -> 314, 8 -> 581, 9 -> 754
/*     */     //   104: aload #5
/*     */     //   106: checkcast vorga/phazeclient/api/feature/module/setting/implement/BooleanSetting
/*     */     //   109: astore #7
/*     */     //   111: aload #7
/*     */     //   113: aload #4
/*     */     //   115: invokevirtual getAsBoolean : ()Z
/*     */     //   118: invokevirtual setValue : (Z)Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   121: pop
/*     */     //   122: goto -> 844
/*     */     //   125: aload #5
/*     */     //   127: checkcast vorga/phazeclient/api/feature/module/setting/implement/ValueSetting
/*     */     //   130: astore #8
/*     */     //   132: aload #8
/*     */     //   134: aload #4
/*     */     //   136: invokevirtual getAsFloat : ()F
/*     */     //   139: invokevirtual setValue : (F)Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*     */     //   142: pop
/*     */     //   143: goto -> 844
/*     */     //   146: aload #5
/*     */     //   148: checkcast vorga/phazeclient/api/feature/module/setting/implement/TextSetting
/*     */     //   151: astore #9
/*     */     //   153: aload #4
/*     */     //   155: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   158: astore #10
/*     */     //   160: aload #10
/*     */     //   162: ldc_w '%%'
/*     */     //   165: ldc_w ' '
/*     */     //   168: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   171: ldc_w '++'
/*     */     //   174: ldc_w '/'
/*     */     //   177: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   180: astore #10
/*     */     //   182: aload #9
/*     */     //   184: aload #10
/*     */     //   186: invokevirtual setText : (Ljava/lang/String;)Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*     */     //   189: pop
/*     */     //   190: goto -> 844
/*     */     //   193: aload #5
/*     */     //   195: checkcast vorga/phazeclient/api/feature/module/setting/implement/BindSetting
/*     */     //   198: astore #10
/*     */     //   200: aload #10
/*     */     //   202: aload #4
/*     */     //   204: invokevirtual getAsInt : ()I
/*     */     //   207: invokevirtual setKey : (I)Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*     */     //   210: pop
/*     */     //   211: goto -> 844
/*     */     //   214: aload #5
/*     */     //   216: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   219: astore #11
/*     */     //   221: aload #11
/*     */     //   223: aload #4
/*     */     //   225: invokevirtual getAsInt : ()I
/*     */     //   228: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   231: pop
/*     */     //   232: goto -> 844
/*     */     //   235: aload #5
/*     */     //   237: checkcast vorga/phazeclient/api/feature/module/setting/implement/SelectSetting
/*     */     //   240: astore #12
/*     */     //   242: aload #12
/*     */     //   244: aload #4
/*     */     //   246: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   249: invokevirtual setSelected : (Ljava/lang/String;)V
/*     */     //   252: goto -> 844
/*     */     //   255: aload #5
/*     */     //   257: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting
/*     */     //   260: astore #13
/*     */     //   262: aload #4
/*     */     //   264: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   267: astore #14
/*     */     //   269: new java/util/ArrayList
/*     */     //   272: dup
/*     */     //   273: aload #14
/*     */     //   275: ldc_w ','
/*     */     //   278: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
/*     */     //   281: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
/*     */     //   284: invokespecial <init> : (Ljava/util/Collection;)V
/*     */     //   287: astore #15
/*     */     //   289: aload #15
/*     */     //   291: aload #13
/*     */     //   293: <illegal opcode> test : (Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;)Ljava/util/function/Predicate;
/*     */     //   298: invokeinterface removeIf : (Ljava/util/function/Predicate;)Z
/*     */     //   303: pop
/*     */     //   304: aload #13
/*     */     //   306: aload #15
/*     */     //   308: invokevirtual setSelected : (Ljava/util/List;)V
/*     */     //   311: goto -> 844
/*     */     //   314: aload #5
/*     */     //   316: checkcast vorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting
/*     */     //   319: astore #14
/*     */     //   321: aload #4
/*     */     //   323: invokevirtual isJsonPrimitive : ()Z
/*     */     //   326: ifeq -> 351
/*     */     //   329: aload #4
/*     */     //   331: invokevirtual getAsJsonPrimitive : ()Lcom/google/gson/JsonPrimitive;
/*     */     //   334: invokevirtual isBoolean : ()Z
/*     */     //   337: ifeq -> 351
/*     */     //   340: aload #14
/*     */     //   342: aload #4
/*     */     //   344: invokevirtual getAsBoolean : ()Z
/*     */     //   347: invokevirtual setEnabled : (Z)V
/*     */     //   350: return
/*     */     //   351: aload #4
/*     */     //   353: invokevirtual isJsonObject : ()Z
/*     */     //   356: ifne -> 360
/*     */     //   359: return
/*     */     //   360: aload #4
/*     */     //   362: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*     */     //   365: astore #15
/*     */     //   367: aload #15
/*     */     //   369: ldc_w 'itemId'
/*     */     //   372: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   375: ifeq -> 392
/*     */     //   378: aload #15
/*     */     //   380: ldc_w 'itemId'
/*     */     //   383: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   386: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   389: goto -> 395
/*     */     //   392: ldc_w ''
/*     */     //   395: astore #16
/*     */     //   397: aload #15
/*     */     //   399: ldc_w 'displayName'
/*     */     //   402: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   405: ifeq -> 422
/*     */     //   408: aload #15
/*     */     //   410: ldc_w 'displayName'
/*     */     //   413: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   416: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   419: goto -> 425
/*     */     //   422: ldc_w ''
/*     */     //   425: astore #17
/*     */     //   427: aload #15
/*     */     //   429: ldc_w 'matchName'
/*     */     //   432: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   435: ifeq -> 452
/*     */     //   438: aload #15
/*     */     //   440: ldc_w 'matchName'
/*     */     //   443: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   446: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   449: goto -> 455
/*     */     //   452: ldc_w ''
/*     */     //   455: astore #18
/*     */     //   457: aload #15
/*     */     //   459: ldc_w 'enabled'
/*     */     //   462: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   465: ifeq -> 482
/*     */     //   468: aload #15
/*     */     //   470: ldc_w 'enabled'
/*     */     //   473: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   476: invokevirtual getAsBoolean : ()Z
/*     */     //   479: goto -> 487
/*     */     //   482: aload #14
/*     */     //   484: invokevirtual isEnabled : ()Z
/*     */     //   487: istore #19
/*     */     //   489: aload #15
/*     */     //   491: ldc_w 'highlightColor'
/*     */     //   494: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   497: ifeq -> 514
/*     */     //   500: aload #15
/*     */     //   502: ldc_w 'highlightColor'
/*     */     //   505: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   508: invokevirtual getAsInt : ()I
/*     */     //   511: goto -> 519
/*     */     //   514: aload #14
/*     */     //   516: invokevirtual getHighlightColor : ()I
/*     */     //   519: istore #20
/*     */     //   521: aload #15
/*     */     //   523: ldc_w 'active'
/*     */     //   526: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   529: ifeq -> 546
/*     */     //   532: aload #15
/*     */     //   534: ldc_w 'active'
/*     */     //   537: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   540: invokevirtual getAsBoolean : ()Z
/*     */     //   543: goto -> 559
/*     */     //   546: aload #16
/*     */     //   548: invokevirtual isBlank : ()Z
/*     */     //   551: ifne -> 558
/*     */     //   554: iconst_1
/*     */     //   555: goto -> 559
/*     */     //   558: iconst_0
/*     */     //   559: istore #21
/*     */     //   561: aload #14
/*     */     //   563: iload #21
/*     */     //   565: aload #16
/*     */     //   567: aload #17
/*     */     //   569: aload #18
/*     */     //   571: iload #19
/*     */     //   573: iload #20
/*     */     //   575: invokevirtual setSerializedState : (ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZI)V
/*     */     //   578: goto -> 844
/*     */     //   581: aload #5
/*     */     //   583: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting
/*     */     //   586: astore #15
/*     */     //   588: aload #4
/*     */     //   590: invokevirtual isJsonPrimitive : ()Z
/*     */     //   593: ifeq -> 634
/*     */     //   596: aload #4
/*     */     //   598: invokevirtual getAsJsonPrimitive : ()Lcom/google/gson/JsonPrimitive;
/*     */     //   601: invokevirtual isNumber : ()Z
/*     */     //   604: ifeq -> 634
/*     */     //   607: aload #4
/*     */     //   609: invokevirtual getAsInt : ()I
/*     */     //   612: istore #16
/*     */     //   614: aload #15
/*     */     //   616: invokevirtual getColor1 : ()Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   619: ifnull -> 633
/*     */     //   622: aload #15
/*     */     //   624: invokevirtual getColor1 : ()Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   627: iload #16
/*     */     //   629: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   632: pop
/*     */     //   633: return
/*     */     //   634: aload #4
/*     */     //   636: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*     */     //   639: astore #16
/*     */     //   641: aload #16
/*     */     //   643: ldc_w 'selectedColorIndex'
/*     */     //   646: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   649: ifeq -> 668
/*     */     //   652: aload #15
/*     */     //   654: aload #16
/*     */     //   656: ldc_w 'selectedColorIndex'
/*     */     //   659: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   662: invokevirtual getAsInt : ()I
/*     */     //   665: invokevirtual setSelectedColorIndex : (I)V
/*     */     //   668: aload #16
/*     */     //   670: ldc_w 'colors'
/*     */     //   673: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   676: ifeq -> 751
/*     */     //   679: aload #16
/*     */     //   681: ldc_w 'colors'
/*     */     //   684: invokevirtual getAsJsonArray : (Ljava/lang/String;)Lcom/google/gson/JsonArray;
/*     */     //   687: astore #17
/*     */     //   689: aload #15
/*     */     //   691: invokevirtual getAllColors : ()Ljava/util/List;
/*     */     //   694: astore #18
/*     */     //   696: iconst_0
/*     */     //   697: istore #19
/*     */     //   699: iload #19
/*     */     //   701: aload #17
/*     */     //   703: invokevirtual size : ()I
/*     */     //   706: aload #18
/*     */     //   708: invokeinterface size : ()I
/*     */     //   713: invokestatic min : (II)I
/*     */     //   716: if_icmpge -> 751
/*     */     //   719: aload #18
/*     */     //   721: iload #19
/*     */     //   723: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   728: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   731: aload #17
/*     */     //   733: iload #19
/*     */     //   735: invokevirtual get : (I)Lcom/google/gson/JsonElement;
/*     */     //   738: invokevirtual getAsInt : ()I
/*     */     //   741: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   744: pop
/*     */     //   745: iinc #19, 1
/*     */     //   748: goto -> 699
/*     */     //   751: goto -> 844
/*     */     //   754: aload #5
/*     */     //   756: checkcast vorga/phazeclient/api/feature/module/setting/implement/GroupSetting
/*     */     //   759: astore #16
/*     */     //   761: aload #4
/*     */     //   763: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*     */     //   766: astore #17
/*     */     //   768: aload #17
/*     */     //   770: ldc_w 'state'
/*     */     //   773: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   776: ifeq -> 796
/*     */     //   779: aload #16
/*     */     //   781: aload #17
/*     */     //   783: ldc_w 'state'
/*     */     //   786: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   789: invokevirtual getAsBoolean : ()Z
/*     */     //   792: invokevirtual setValue : (Z)Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*     */     //   795: pop
/*     */     //   796: aload #16
/*     */     //   798: invokevirtual getSubSettings : ()Ljava/util/List;
/*     */     //   801: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   806: astore #18
/*     */     //   808: aload #18
/*     */     //   810: invokeinterface hasNext : ()Z
/*     */     //   815: ifeq -> 841
/*     */     //   818: aload #18
/*     */     //   820: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   825: checkcast vorga/phazeclient/api/feature/module/setting/Setting
/*     */     //   828: astore #19
/*     */     //   830: aload_0
/*     */     //   831: aload #19
/*     */     //   833: aload #17
/*     */     //   835: invokevirtual loadSetting : (Lvorga/phazeclient/api/feature/module/setting/Setting;Lcom/google/gson/JsonObject;)V
/*     */     //   838: goto -> 808
/*     */     //   841: goto -> 844
/*     */     //   844: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #810	-> 0
/*     */     //   #811	-> 7
/*     */     //   #814	-> 8
/*     */     //   #815	-> 13
/*     */     //   #816	-> 21
/*     */     //   #819	-> 22
/*     */     //   #821	-> 29
/*     */     //   #822	-> 104
/*     */     //   #823	-> 125
/*     */     //   #824	-> 146
/*     */     //   #825	-> 153
/*     */     //   #826	-> 160
/*     */     //   #827	-> 182
/*     */     //   #828	-> 190
/*     */     //   #829	-> 193
/*     */     //   #830	-> 214
/*     */     //   #831	-> 235
/*     */     //   #832	-> 255
/*     */     //   #833	-> 262
/*     */     //   #834	-> 269
/*     */     //   #835	-> 289
/*     */     //   #836	-> 304
/*     */     //   #837	-> 311
/*     */     //   #838	-> 314
/*     */     //   #839	-> 321
/*     */     //   #840	-> 340
/*     */     //   #841	-> 350
/*     */     //   #843	-> 351
/*     */     //   #844	-> 359
/*     */     //   #846	-> 360
/*     */     //   #847	-> 367
/*     */     //   #848	-> 397
/*     */     //   #849	-> 427
/*     */     //   #850	-> 457
/*     */     //   #851	-> 468
/*     */     //   #852	-> 482
/*     */     //   #853	-> 489
/*     */     //   #854	-> 500
/*     */     //   #855	-> 514
/*     */     //   #856	-> 521
/*     */     //   #857	-> 532
/*     */     //   #858	-> 546
/*     */     //   #859	-> 561
/*     */     //   #860	-> 578
/*     */     //   #861	-> 581
/*     */     //   #862	-> 588
/*     */     //   #863	-> 607
/*     */     //   #864	-> 614
/*     */     //   #865	-> 622
/*     */     //   #867	-> 633
/*     */     //   #870	-> 634
/*     */     //   #871	-> 641
/*     */     //   #872	-> 652
/*     */     //   #875	-> 668
/*     */     //   #876	-> 679
/*     */     //   #877	-> 689
/*     */     //   #879	-> 696
/*     */     //   #880	-> 719
/*     */     //   #879	-> 745
/*     */     //   #883	-> 751
/*     */     //   #884	-> 754
/*     */     //   #885	-> 761
/*     */     //   #886	-> 768
/*     */     //   #887	-> 779
/*     */     //   #890	-> 796
/*     */     //   #891	-> 830
/*     */     //   #892	-> 838
/*     */     //   #893	-> 841
/*     */     //   #897	-> 844
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   111	14	7	booleanSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   132	14	8	valueSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*     */     //   160	30	10	value	Ljava/lang/String;
/*     */     //   153	40	9	textSetting	Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*     */     //   200	14	10	bindSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*     */     //   221	14	11	colorSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   242	13	12	selectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/SelectSetting;
/*     */     //   269	42	14	value	Ljava/lang/String;
/*     */     //   289	22	15	selected	Ljava/util/List;
/*     */     //   262	52	13	multiSelectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;
/*     */     //   367	211	15	itemObject	Lcom/google/gson/JsonObject;
/*     */     //   397	181	16	itemId	Ljava/lang/String;
/*     */     //   427	151	17	displayName	Ljava/lang/String;
/*     */     //   457	121	18	matchName	Ljava/lang/String;
/*     */     //   489	89	19	enabled	Z
/*     */     //   521	57	20	highlightColor	I
/*     */     //   561	17	21	active	Z
/*     */     //   321	260	14	itemPickerSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting;
/*     */     //   614	20	16	oldColor	I
/*     */     //   699	52	19	i	I
/*     */     //   689	62	17	colorsArray	Lcom/google/gson/JsonArray;
/*     */     //   696	55	18	colorSettings	Ljava/util/List;
/*     */     //   641	110	16	colorObject	Lcom/google/gson/JsonObject;
/*     */     //   588	166	15	multiColor	Lvorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting;
/*     */     //   830	8	19	subSetting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   768	73	17	groupObject	Lcom/google/gson/JsonObject;
/*     */     //   761	83	16	group	Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*     */     //   0	845	0	this	Lvorga/phazeclient/implement/menu/components/implement/module/ModuleComponent;
/*     */     //   0	845	1	setting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   0	845	2	moduleData	Lcom/google/gson/JsonObject;
/*     */     //   13	832	3	key	Ljava/lang/String;
/*     */     //   29	816	4	element	Lcom/google/gson/JsonElement;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   289	22	15	selected	Ljava/util/List<Ljava/lang/String;>;
/*     */     //   696	55	18	colorSettings	Ljava/util/List<Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 901 */     if (this == o) return true; 
/* 902 */     if (o == null || getClass() != o.getClass()) return false; 
/* 903 */     ModuleComponent that = (ModuleComponent)o;
/* 904 */     return this.module.equals(that.module);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 909 */     return Objects.hash(new Object[] { this.module });
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\module\ModuleComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */