/*      */ package vorga.phazeclient.implement.menu.components.implement.other;
/*      */ 
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.class_1309;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_332;
/*      */ import net.minecraft.class_3532;
/*      */ import net.minecraft.class_4050;
/*      */ import net.minecraft.class_4587;
/*      */ import net.minecraft.class_490;
/*      */ import net.minecraft.class_746;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Vector3f;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import vorga.phazeclient.api.system.animation.Animation;
/*      */ import vorga.phazeclient.api.system.animation.Direction;
/*      */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*      */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*      */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*      */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*      */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*      */ import vorga.phazeclient.api.system.snapshot.CardSnapshotCache;
/*      */ import vorga.phazeclient.base.util.Lang;
/*      */ import vorga.phazeclient.base.util.math.MathUtil;
/*      */ import vorga.phazeclient.core.Main;
/*      */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*      */ import vorga.phazeclient.implement.cosmetics.CosmeticsState;
/*      */ import vorga.phazeclient.implement.menu.MenuStyle;
/*      */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
/*      */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class CosmeticsViewComponent
/*      */   extends AbstractComponent
/*      */ {
/*      */   private static final float HEADER_OFFSET = 41.0F;
/*      */   private static final float SIDE_MARGIN = 9.0F;
/*      */   private static final float GAP = 7.0F;
/*      */   private static final float CATEGORY_WIDTH = 61.0F;
/*      */   private static final float PREVIEW_WIDTH = 142.0F;
/*      */   private static final float TITLE_SIZE = 8.4F;
/*      */   private static final float SUBTITLE_SIZE = 5.3F;
/*      */   private static final float LABEL_SIZE = 5.8F;
/*      */   private static final float CARD_LABEL_SIZE = 5.9F;
/*   54 */   private static final class_2960 ICON_ALL = class_2960.method_60655("phaze", "textures/cosmetics/category_all.png");
/*   55 */   private static final class_2960 ICON_WINGS = class_2960.method_60655("phaze", "textures/cosmetics/category_wings.png");
/*   56 */   private static final class_2960 ICON_CAPES = class_2960.method_60655("phaze", "textures/cosmetics/category_capes.png");
/*   57 */   private static final class_2960 ICON_HATS = class_2960.method_60655("phaze", "textures/cosmetics/category_hats.png");
/*   58 */   private static final class_2960 ICON_PETS = class_2960.method_60655("phaze", "textures/cosmetics/category_pets.png");
/*      */   
/*   60 */   private static final List<CatalogItem> LOCKED_ITEMS = List.of(new CatalogItem("Dragon Wings", "Dragon Wings", true, -10919062, -13551555), new CatalogItem("Nebula Wings", "Nebula Wings", true, -10333554, -13752240), new CatalogItem("Frost Wings", "Frost Wings", true, -8668208, -13079675), new CatalogItem("Infernal Wings", "Infernal Wings", true, -3577795, -8966113));
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean open;
/*      */ 
/*      */ 
/*      */   
/*   68 */   private String category = "ALL";
/*   69 */   private String previewSelection = "Wimgs";
/*   70 */   private float previewYaw = 18.0F;
/*      */   private double catalogScroll;
/*      */   private double smoothedCatalogScroll;
/*      */   private boolean draggingPreview;
/*      */   private boolean draggingMotion;
/*      */   private boolean draggingPetFollow;
/*   76 */   private double sliderAnimation = -1.0D;
/*   77 */   private double petSliderAnimation = -1.0D;
/*   78 */   private final Animation pageAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/*   79 */   private final CheckComponent hideElytraToggle = new CheckComponent();
/*   80 */   private final Map<String, CardAnimations> cardAnimations = new HashMap<>();
/*   81 */   private final Map<String, Object> catalogPreviewCacheKeys = new HashMap<>();
/*   82 */   private final Set<String> livePreviewFallback = new HashSet<>();
/*   83 */   private final Object playerPreviewCacheKey = new Object();
/*      */   private long lastPlayerPreviewCaptureNs;
/*      */   
/*      */   public CosmeticsViewComponent() {
/*   87 */     this.pageAnimation.setDirectionAndFinish(Direction.BACKWARDS);
/*   88 */     this.hideElytraToggle.setRunnable(() -> {
/*      */           CosmeticsState state = CosmeticsState.getInstance();
/*      */           state.setHideElytra(!state.isHideElytra());
/*      */         });
/*      */   }
/*      */   
/*      */   public void open() {
/*   95 */     this.open = true;
/*   96 */     this.pageAnimation.setDirection(Direction.FORWARDS);
/*   97 */     CosmeticsState.getInstance().refreshCatalog();
/*   98 */     this.catalogScroll = 0.0D;
/*   99 */     this.smoothedCatalogScroll = 0.0D;
/*  100 */     this.livePreviewFallback.clear();
/*  101 */     this.previewSelection = CosmeticsState.getInstance().getSelectedWing();
/*  102 */     if ("None".equalsIgnoreCase(this.previewSelection) || 
/*  103 */       !CosmeticsState.getInstance().isAvailable(this.previewSelection)) {
/*  104 */       List<CosmeticsState.CosmeticEntry> catalog = CosmeticsState.getInstance().getCatalog();
/*  105 */       this.previewSelection = catalog.isEmpty() ? "Wimgs" : ((CosmeticsState.CosmeticEntry)catalog.getFirst()).name();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void close() {
/*  110 */     this.open = false;
/*  111 */     this.pageAnimation.setDirection(Direction.BACKWARDS);
/*  112 */     this.draggingPreview = false;
/*  113 */     this.draggingMotion = false;
/*  114 */     this.draggingPetFollow = false;
/*      */   }
/*      */   
/*      */   public boolean isOpen() {
/*  118 */     return this.open;
/*      */   }
/*      */   
/*      */   public boolean shouldRender() {
/*  122 */     return (this.open || this.pageAnimation.getOutputFloat() > 0.002F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  127 */     if (!shouldRender())
/*      */       return; 
/*  129 */     float inheritedAlpha = this.globalAlpha;
/*  130 */     this.globalAlpha *= this.pageAnimation.getOutputFloat();
/*  131 */     Layout layout = layout();
/*      */     try {
/*  133 */       renderTitle(context, layout);
/*  134 */       renderCategories(context, layout, mouseX, mouseY);
/*  135 */       renderCatalog(context, layout, mouseX, mouseY);
/*  136 */       renderPreview(context, layout, mouseX, mouseY);
/*      */     } finally {
/*  138 */       this.globalAlpha = inheritedAlpha;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderTitle(class_332 context, Layout layout) {
/*  143 */     class_4587 matrices = context.method_51448();
/*  144 */     MsdfRenderer.renderText(
/*  145 */         MsdfFonts.bold(), Lang.t("cosmetics.title"), 8.4F, applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), matrices
/*  146 */         .method_23760().method_23761(), layout.catalogX, layout.top, 0.0F);
/*      */     
/*  148 */     MsdfRenderer.renderText(
/*  149 */         MsdfFonts.medium(), Lang.t("cosmetics.subtitle"), 5.3F, 
/*  150 */         applyGlobalAlpha(MenuStyle.TEXT_MUTED), matrices.method_23760().method_23761(), layout.catalogX, layout.top + 10.5F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderCategories(class_332 context, Layout layout, int mouseX, int mouseY) {
/*  156 */     String[] labels = { "ALL", "WINGS", "CAPES", "HATS", "PETS" };
/*  157 */     float rowY = layout.cardsTop;
/*  158 */     for (String label : labels) {
/*  159 */       boolean selected = label.equals(this.category);
/*  160 */       boolean hovered = MathUtil.isHovered(mouseX, mouseY, layout.categoryX, rowY, 61.0D, 24.0D);
/*      */ 
/*      */       
/*  163 */       int fill = selected ? MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CHIP_ACTIVE, 0.28F) : MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CARD_INNER, 0.34F);
/*  164 */       int border = selected ? MenuStyle.mix(MenuStyle.BORDER_LIGHT, MenuStyle.CHIP_ACTIVE, 0.55F) : MenuStyle.BORDER;
/*  165 */       if (hovered) fill = MenuStyle.mix(fill, MenuStyle.TEXT_PRIMARY, 0.06F);
/*      */       
/*  167 */       rectangle.render(ShapeProperties.create(context.method_51448(), layout.categoryX, rowY, 61.0D, 24.0D)
/*  168 */           .round(3.0F).thickness(2.0F)
/*  169 */           .outlineColor(applyGlobalAlpha(border)).color(applyGlobalAlpha(fill)).build());
/*  170 */       drawCategoryGlyph(context, label, layout.categoryX + 8.0F, rowY + 7.0F, 
/*  171 */           selected ? MenuStyle.CHIP_ACTIVE : MenuStyle.TEXT_MUTED);
/*  172 */       MsdfRenderer.renderText(
/*  173 */           MsdfFonts.bold(), categoryLabel(label), 5.8F, 
/*  174 */           applyGlobalAlpha(selected ? MenuStyle.TEXT_PRIMARY : MenuStyle.TEXT_MUTED), context
/*  175 */           .method_51448().method_23760().method_23761(), layout.categoryX + 21.0F, 
/*      */           
/*  177 */           MenuStyle.centerMsdfTextY(5.8F, rowY, 24.0F), 0.0F);
/*      */ 
/*      */       
/*  180 */       rowY += 28.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void drawCategoryGlyph(class_332 context, String label, float x, float y, int color) {
/*  185 */     switch (label) { case "WINGS": 
/*      */       case "CAPES": 
/*      */       case "HATS": 
/*      */       case "PETS": 
/*      */       default:
/*  190 */         break; }  class_2960 icon = ICON_ALL;
/*      */     
/*  192 */     UiMsdfIconAtlas.renderIcon(context, icon, x - 0.5F, y - 0.5F, 11.0F, 11.0F, 
/*  193 */         applyGlobalAlpha(color), true);
/*      */   }
/*      */   
/*      */   private static String categoryLabel(String category) {
/*  197 */     switch (category) { case "WINGS": case "CAPES": case "HATS": case "PETS":  }  return 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  202 */       Lang.t("cosmetics.category.all");
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCatalog(class_332 context, Layout layout, int mouseX, int mouseY) {
/*  207 */     List<CatalogItem> items = allCatalogItems();
/*  208 */     List<VisibleCard> visibleCards = new ArrayList<>(8);
/*  209 */     float cardGap = 6.0F;
/*  210 */     float cardW = (layout.catalogWidth - cardGap) / 2.0F;
/*  211 */     float cardH = 61.0F;
/*  212 */     float viewportHeight = catalogViewportHeight(layout);
/*  213 */     double maxScroll = catalogMaxScroll(layout, items.size(), cardH, cardGap);
/*  214 */     this.catalogScroll = Math.max(-maxScroll, Math.min(0.0D, this.catalogScroll));
/*  215 */     this.smoothedCatalogScroll = MathUtil.interpolateSmooth(3.2D, this.smoothedCatalogScroll, this.catalogScroll);
/*      */ 
/*      */     
/*  218 */     if (Math.abs(this.smoothedCatalogScroll - this.catalogScroll) < 0.01D) {
/*  219 */       this.smoothedCatalogScroll = this.catalogScroll;
/*      */     }
/*      */     
/*  222 */     Main.getInstance().getScissorManager().push(context
/*  223 */         .method_51448().method_23760().method_23761(), layout.catalogX, layout.cardsTop, layout.catalogWidth, viewportHeight);
/*      */ 
/*      */     
/*  226 */     for (int i = 0; i < items.size(); i++) {
/*  227 */       CatalogItem item = items.get(i);
/*  228 */       int column = i % 2;
/*  229 */       int row = i / 2;
/*  230 */       float cardX = layout.catalogX + column * (cardW + cardGap);
/*  231 */       float cardY = layout.cardsTop + row * (cardH + cardGap) + (float)this.smoothedCatalogScroll;
/*      */       
/*  233 */       if (cardY + cardH >= layout.cardsTop - 1.0F && cardY <= layout.cardsTop + viewportHeight + 1.0F) {
/*      */ 
/*      */ 
/*      */         
/*  237 */         boolean available = (!item.locked && CosmeticsState.getInstance().isAvailable(item.id));
/*  238 */         boolean selected = item.id.equalsIgnoreCase(this.previewSelection);
/*  239 */         boolean equipped = (!item.locked && CosmeticsState.getInstance().isEquipped(item.id));
/*      */ 
/*      */ 
/*      */         
/*  243 */         boolean hovered = (MathUtil.isHovered(mouseX, mouseY, layout.catalogX, layout.cardsTop, layout.catalogWidth, viewportHeight) && MathUtil.isHovered(mouseX, mouseY, cardX, cardY, cardW, cardH));
/*  244 */         CardAnimations animations = this.cardAnimations.computeIfAbsent(item.id
/*  245 */             .toLowerCase(Locale.ROOT), ignored -> new CardAnimations(hovered, selected));
/*      */         
/*  247 */         animations.hover.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*  248 */         animations.selection.setDirection(selected ? Direction.FORWARDS : Direction.BACKWARDS);
/*  249 */         float hoverProgress = animations.hover.getOutputFloat();
/*  250 */         float selectionProgress = animations.selection.getOutputFloat();
/*      */         
/*  252 */         int fill = (item.locked || !available) ? MenuStyle.CARD_DISABLED : MenuStyle.CARD_BG;
/*  253 */         if (!item.locked) fill = MenuStyle.mix(fill, MenuStyle.TEXT_PRIMARY, hoverProgress * 0.055F); 
/*  254 */         int selectedBorder = MenuStyle.mix(MenuStyle.BORDER_LIGHT, MenuStyle.CHIP_ACTIVE, 0.72F);
/*  255 */         int border = MenuStyle.mix(MenuStyle.BORDER, selectedBorder, selectionProgress);
/*      */         
/*  257 */         rectangle.render(ShapeProperties.create(context.method_51448(), cardX, cardY, cardW, cardH)
/*  258 */             .round(4.0F).thickness(1.5F + 0.7F * selectionProgress)
/*  259 */             .outlineColor(applyGlobalAlpha(border)).color(applyGlobalAlpha(fill)).build());
/*      */         
/*  261 */         if (!available) {
/*  262 */           drawWingThumbnail(context, cardX + cardW / 2.0F, cardY + 24.0F, item.primary, item.accent, true);
/*      */         }
/*      */ 
/*      */         
/*  266 */         String baseLabel = catalogItemLabel(item);
/*      */ 
/*      */         
/*  269 */         String label = (available || item.locked) ? baseLabel : (baseLabel + " · " + baseLabel);
/*      */         
/*  271 */         int textColor = (item.locked || !available) ? MenuStyle.TEXT_MUTED : (selected ? MenuStyle.CHIP_ACTIVE : MenuStyle.TEXT_PRIMARY);
/*  272 */         visibleCards.add(new VisibleCard(item, cardX, cardY, cardW, cardH, available, selected, equipped, selectionProgress, label, textColor));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     BatchedRectangle.flushIfBatching();
/*  283 */     for (VisibleCard card : visibleCards) {
/*  284 */       if (!card.available)
/*  285 */         continue;  renderCatalogModelPreview(context, card.item.id, card.x, card.y, card.width, card.height);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  290 */     context.method_51452();
/*      */ 
/*      */     
/*  293 */     for (VisibleCard card : visibleCards) {
/*  294 */       MsdfRenderer.renderText(
/*  295 */           MsdfFonts.medium(), card.label, 5.9F, 
/*  296 */           applyGlobalAlpha(card.textColor), context
/*  297 */           .method_51448().method_23760().method_23761(), 
/*  298 */           MenuStyle.centerMsdfTextX(
/*  299 */             MsdfFonts.medium(), card.label, 5.9F, card.x, card.width), card.y + card.height - 14.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  304 */       if (!card.equipped && card.item.locked) {
/*  305 */         String locked = Lang.t("cosmetics.locked");
/*  306 */         MsdfRenderer.renderText(
/*  307 */             MsdfFonts.bold(), locked, 4.2F, applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  308 */             .method_51448().method_23760().method_23761(), card.x + card.width - 
/*  309 */             MsdfFonts.bold().getWidth(locked, 4.2F) - 4.0F, card.y + 4.0F, 0.0F);
/*      */         continue;
/*      */       } 
/*  312 */       if (card.selected) {
/*  313 */         rectangle.render(ShapeProperties.create(context
/*  314 */               .method_51448(), (card.x + card.width - 10.0F), (card.y + 4.0F), 6.0D, 6.0D)
/*      */ 
/*      */             
/*  317 */             .round(3.0F).color(applyGlobalAlpha(MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, card.selectionProgress)))
/*  318 */             .build());
/*      */       }
/*      */     } 
/*      */     
/*  322 */     BatchedRectangle.flushIfBatching();
/*  323 */     RenderSystem.disableDepthTest();
/*  324 */     context.method_51448().method_22903();
/*  325 */     context.method_51448().method_46416(0.0F, 0.0F, 600.0F);
/*  326 */     for (VisibleCard card : visibleCards) {
/*  327 */       if (!card.item.locked && card.equipped) {
/*  328 */         renderEquippedBadge(context, card.x, card.y, card.width);
/*      */       }
/*      */     } 
/*  331 */     BatchedRectangle.flushIfBatching();
/*  332 */     context.method_51448().method_22909();
/*  333 */     Main.getInstance().getScissorManager().pop();
/*      */   }
/*      */   
/*      */   private void renderEquippedBadge(class_332 context, float cardX, float cardY, float cardW) {
/*  337 */     String worn = Lang.t("cosmetics.equipped");
/*  338 */     float badgeW = MsdfFonts.bold().getWidth(worn, 3.9F) + 8.0F;
/*  339 */     float badgeX = cardX + cardW - badgeW - 4.0F;
/*  340 */     rectangle.render(ShapeProperties.create(context
/*  341 */           .method_51448(), badgeX, (cardY + 4.0F), badgeW, 8.0D)
/*  342 */         .round(4.0F)
/*  343 */         .color(applyGlobalAlpha(MenuStyle.mix(MenuStyle.CHIP_ACTIVE, MenuStyle.ACCENT_GREEN, 0.28F)))
/*      */         
/*  345 */         .build());
/*  346 */     MsdfRenderer.renderText(
/*  347 */         MsdfFonts.bold(), worn, 3.9F, 
/*  348 */         applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), context
/*  349 */         .method_51448().method_23760().method_23761(), 
/*  350 */         MenuStyle.centerMsdfTextX(
/*  351 */           MsdfFonts.bold(), worn, 3.9F, badgeX, badgeW), 
/*  352 */         MenuStyle.centerMsdfTextY(3.9F, cardY + 4.0F, 8.0F) - 0.25F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String catalogItemLabel(CatalogItem item) {
/*  358 */     if (!item.locked) return item.label; 
/*  359 */     boolean russian = "Русский".equals(Lang.getActive());
/*  360 */     switch (item.id) { case "Dragon Wings": return 
/*  361 */           russian ? "Драконьи крылья" : "Dragon Wings";
/*  362 */       case "Nebula Wings": return russian ? "Крылья туманности" : "Nebula Wings";
/*  363 */       case "Frost Wings": return russian ? "Морозные крылья" : "Frost Wings";
/*  364 */       case "Infernal Wings": return russian ? "Адские крылья" : "Infernal Wings"; }
/*  365 */      return item.label;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderCatalogModelPreview(class_332 context, String selection, float cardX, float cardY, float cardW, float cardH) {
/*  371 */     if (this.globalAlpha < 0.02F)
/*  372 */       return;  float previewX = cardX + 4.0F;
/*  373 */     float previewY = cardY + 3.0F;
/*  374 */     float previewW = cardW - 8.0F;
/*  375 */     float previewH = cardH - 21.0F;
/*  376 */     String normalizedSelection = selection.toLowerCase(Locale.ROOT);
/*      */     
/*  378 */     class_310 client = class_310.method_1551();
/*  379 */     if (client == null || client.method_22683() == null) {
/*  380 */       CosmeticsRenderer.renderCatalogModel(context, selection, previewX, previewY, previewW, previewH, this.globalAlpha);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  387 */     if (this.livePreviewFallback.contains(normalizedSelection)) {
/*  388 */       CosmeticsRenderer.renderCatalogModel(context, selection, previewX, previewY, previewW, previewH, this.globalAlpha);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  396 */     float framebufferScale = (float)client.method_22683().method_4495();
/*  397 */     Object cacheKey = this.catalogPreviewCacheKeys.computeIfAbsent(normalizedSelection, ignored -> new Object());
/*      */ 
/*      */     
/*  400 */     CardSnapshotCache.Snapshot snapshot = CardSnapshotCache.getOrCreate(cacheKey, 
/*      */         
/*  402 */         Math.max(1, Math.round(previewW * framebufferScale)), 
/*  403 */         Math.max(1, Math.round(previewH * framebufferScale)), true);
/*      */ 
/*      */ 
/*      */     
/*  407 */     int visualHash = 31 * normalizedSelection.hashCode() + CosmeticsState.typeOf(selection).ordinal();
/*  408 */     if (!snapshot.populated || snapshot.hash != visualHash) {
/*  409 */       boolean rendered = false;
/*  410 */       CardSnapshotCache.beginCapture(snapshot, previewW, previewH);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  418 */         class_332 captureContext = new class_332(client, client.method_22940().method_23000());
/*      */         
/*  420 */         rendered = CosmeticsRenderer.renderCatalogModel(captureContext, selection, 0.0F, 0.0F, previewW, previewH, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  425 */         captureContext.method_51452();
/*      */       } finally {
/*  427 */         CardSnapshotCache.endCapture();
/*      */       } 
/*      */ 
/*      */       
/*  431 */       if (rendered && CardSnapshotCache.hasVisiblePixels(snapshot)) {
/*  432 */         snapshot.hash = visualHash;
/*      */       } else {
/*  434 */         snapshot.populated = false;
/*  435 */         this.livePreviewFallback.add(normalizedSelection);
/*      */       } 
/*      */     } 
/*  438 */     CardSnapshotCache.blit(context, snapshot, previewX, previewY, previewW, previewH, this.globalAlpha);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void captureCatalogSnapshots(class_332 context, List<PendingCatalogSnapshot> pending) {
/*  449 */     if (pending.isEmpty())
/*  450 */       return;  class_310 client = class_310.method_1551();
/*  451 */     if (client == null || client.method_22683() == null || client
/*  452 */       .method_1522() == null)
/*  453 */       return;  float framebufferScale = (float)client.method_22683().method_4495();
/*  454 */     int framebufferHeight = client.method_22683().method_4506();
/*  455 */     for (PendingCatalogSnapshot capture : pending) {
/*      */       
/*  457 */       Vector3f topLeft = context.method_51448().method_23760().method_23761().transformPosition(new Vector3f(capture.box.x, capture.box.y, 0.0F));
/*      */       
/*  459 */       Vector3f bottomRight = context.method_51448().method_23760().method_23761().transformPosition(new Vector3f(capture.box.x + capture.box.width, capture.box.y + capture.box.height, 0.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  464 */       int srcX = Math.max(0, Math.round(
/*  465 */             Math.min(topLeft.x, bottomRight.x) * framebufferScale));
/*      */       
/*  467 */       int srcTop = Math.max(0, Math.round(
/*  468 */             Math.min(topLeft.y, bottomRight.y) * framebufferScale));
/*      */       
/*  470 */       int srcWidth = Math.max(1, Math.round(
/*  471 */             Math.abs(bottomRight.x - topLeft.x) * framebufferScale));
/*      */       
/*  473 */       int srcHeight = Math.max(1, Math.round(
/*  474 */             Math.abs(bottomRight.y - topLeft.y) * framebufferScale));
/*      */       
/*  476 */       int srcY = Math.max(0, framebufferHeight - srcTop - srcHeight);
/*  477 */       CardSnapshotCache.copyRegionFromFramebuffer(capture.snapshot, client
/*      */           
/*  479 */           .method_1522(), srcX, srcY, srcWidth, srcHeight);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawWingThumbnail(class_332 context, float centerX, float centerY, int primary, int accent, boolean muted) {
/*  487 */     int feather = applyGlobalAlpha(muted ? MenuStyle.mix(primary, MenuStyle.CARD_DISABLED, 0.65F) : primary);
/*  488 */     int frame = applyGlobalAlpha(muted ? MenuStyle.mix(accent, MenuStyle.CARD_DISABLED, 0.65F) : accent);
/*  489 */     for (int side : new int[] { -1, 1 }) {
/*  490 */       for (int i = 0; i < 5; i++) {
/*  491 */         float distance = 4.0F + i * 4.0F;
/*  492 */         float featherX = centerX + side * distance - ((side < 0) ? 5.0F : 0.0F);
/*  493 */         float featherY = centerY - 8.0F + i * 2.4F;
/*  494 */         rectangle.render(ShapeProperties.create(context.method_51448(), featherX, featherY, 5.0D, (15.0F - i))
/*  495 */             .round(2.2F).color(feather).build());
/*      */       } 
/*  497 */       float frameX = centerX + side * 4.0F - ((side < 0) ? 17.0F : 0.0F);
/*  498 */       rectangle.render(ShapeProperties.create(context.method_51448(), frameX, (centerY - 10.0F), 17.0D, 2.200000047683716D)
/*  499 */           .round(1.0F).color(frame).build());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPreview(class_332 context, Layout layout, int mouseX, int mouseY) {
/*  504 */     float panelX = layout.previewX;
/*  505 */     float panelY = layout.top;
/*  506 */     float panelH = layout.contentHeight;
/*  507 */     rectangle.render(ShapeProperties.create(context.method_51448(), panelX, panelY, 142.0D, panelH)
/*  508 */         .round(5.0F).thickness(1.6F)
/*  509 */         .outlineColor(applyGlobalAlpha(MenuStyle.BORDER))
/*  510 */         .color(applyGlobalAlpha(MenuStyle.mix(MenuStyle.PANEL_BG, MenuStyle.CARD_BG, 0.42F))).build());
/*      */     
/*  512 */     String title = Lang.t("cosmetics.preview");
/*  513 */     MsdfRenderer.renderText(
/*  514 */         MsdfFonts.bold(), title, 5.8F, applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  515 */         .method_51448().method_23760().method_23761(), panelX + 9.0F, panelY + 8.0F, 0.0F);
/*      */ 
/*      */     
/*  518 */     float previewBottom = panelY + 133.0F;
/*  519 */     renderPlayerPreview(context, panelX + 71.0F, previewBottom);
/*      */ 
/*      */ 
/*      */     
/*  523 */     String rotateHint = this.draggingPreview ? Lang.t("cosmetics.rotate") : Lang.t("cosmetics.drag_to_rotate");
/*  524 */     MsdfRenderer.renderText(
/*  525 */         MsdfFonts.medium(), rotateHint, 4.7F, applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  526 */         .method_51448().method_23760().method_23761(), 
/*  527 */         MenuStyle.centerMsdfTextX(MsdfFonts.medium(), rotateHint, 4.7F, panelX, 142.0F), panelY + 136.0F, 0.0F);
/*      */ 
/*      */ 
/*      */     
/*  531 */     float infoY = panelY + 150.0F;
/*      */     
/*  533 */     String cosmeticName = CosmeticsState.displayNameFor(this.previewSelection).toUpperCase(Locale.ROOT);
/*  534 */     List<String> nameLines = wrapPreviewName(cosmeticName, 7.4F, 124.0F);
/*  535 */     for (int i = 0; i < nameLines.size(); i++) {
/*  536 */       MsdfRenderer.renderText(
/*  537 */           MsdfFonts.bold(), nameLines.get(i), 7.4F, 
/*  538 */           applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), context
/*  539 */           .method_51448().method_23760().method_23761(), panelX + 9.0F, infoY + i * 9.0F, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  544 */     CosmeticsState state = CosmeticsState.getInstance();
/*  545 */     if (CosmeticsState.isWing(this.previewSelection)) {
/*  546 */       float hideY = panelY + 174.0F;
/*  547 */       MsdfRenderer.renderText(
/*  548 */           MsdfFonts.medium(), Lang.t("cosmetics.hide_elytra"), 5.1F, 
/*  549 */           applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  550 */           .method_51448().method_23760().method_23761(), panelX + 9.0F, 
/*  551 */           MenuStyle.centerMsdfTextY(5.1F, hideY, 13.0F), 0.0F);
/*      */       
/*  553 */       this.hideElytraToggle.position(layout.toggleX, hideY + 1.5F);
/*  554 */       this.hideElytraToggle.setAlpha(this.globalAlpha);
/*  555 */       this.hideElytraToggle.setState(state.isHideElytra());
/*  556 */       this.hideElytraToggle.render(context, mouseX, mouseY, 0.0F);
/*      */       
/*  558 */       float motionY = panelY + 193.0F;
/*  559 */       String motion = Lang.t("cosmetics.wing_motion");
/*  560 */       MsdfRenderer.renderText(
/*  561 */           MsdfFonts.medium(), motion, 4.8F, applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  562 */           .method_51448().method_23760().method_23761(), panelX + 9.0F, motionY, 0.0F);
/*      */       
/*  564 */       drawMotionSlider(context, layout, motionY + 9.0F, mouseX);
/*  565 */     } else if (CosmeticsState.isPet(this.previewSelection)) {
/*  566 */       float followY = panelY + 174.0F;
/*  567 */       MsdfRenderer.renderText(
/*  568 */           MsdfFonts.medium(), Lang.t("cosmetics.pet_follow"), 4.8F, 
/*  569 */           applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  570 */           .method_51448().method_23760().method_23761(), panelX + 9.0F, followY, 0.0F);
/*      */ 
/*      */       
/*  573 */       drawPetFollowSlider(context, layout, followY + 9.0F, mouseX);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  578 */     boolean equipped = state.isEquipped(this.previewSelection);
/*  579 */     float buttonY = panelY + panelH - 29.0F;
/*  580 */     float buttonX = panelX + 9.0F;
/*  581 */     float buttonW = 124.0F;
/*  582 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, buttonX, buttonY, buttonW, 20.0D);
/*      */ 
/*      */     
/*  585 */     int buttonColor = equipped ? MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CHIP_ACTIVE, 0.18F) : MenuStyle.mix(MenuStyle.CHIP_ACTIVE, MenuStyle.ACCENT_GREEN, 0.28F);
/*  586 */     if (hovered) buttonColor = MenuStyle.mix(buttonColor, MenuStyle.TEXT_PRIMARY, 0.08F); 
/*  587 */     rectangle.render(ShapeProperties.create(context.method_51448(), buttonX, buttonY, buttonW, 20.0D)
/*  588 */         .round(3.0F).thickness(1.6F)
/*  589 */         .outlineColor(applyGlobalAlpha(MenuStyle.mix(MenuStyle.BORDER_LIGHT, MenuStyle.CHIP_ACTIVE, 0.5F)))
/*  590 */         .color(applyGlobalAlpha(buttonColor)).build());
/*  591 */     String action = equipped ? Lang.t("cosmetics.remove") : Lang.t("cosmetics.equip");
/*  592 */     MsdfRenderer.renderText(
/*  593 */         MsdfFonts.bold(), action, 6.2F, applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), context
/*  594 */         .method_51448().method_23760().method_23761(), 
/*  595 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), action, 6.2F, buttonX, buttonW), 
/*  596 */         MenuStyle.centerMsdfTextY(6.2F, buttonY, 20.0F), 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderPlayerPreview(class_332 context, float centerX, float bottomY) {
/*  601 */     class_310 client = class_310.method_1551();
/*  602 */     class_746 player = client.field_1724;
/*  603 */     if (player == null || !CosmeticsState.getInstance().isAvailable(this.previewSelection)) {
/*      */ 
/*      */       
/*  606 */       String unavailable = (player == null) ? Lang.t("cosmetics.enter_world") : Lang.t("cosmetics.model_missing");
/*  607 */       MsdfRenderer.renderText(
/*  608 */           MsdfFonts.medium(), unavailable, 4.8F, applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  609 */           .method_51448().method_23760().method_23761(), 
/*  610 */           MenuStyle.centerMsdfTextX(MsdfFonts.medium(), unavailable, 4.8F, centerX - 71.0F, 142.0F), bottomY - 65.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  622 */     BatchedRectangle.flushIfBatching();
/*  623 */     renderPlayerPreviewEntity(context, player, centerX, bottomY, this.globalAlpha);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderPlayerPreviewEntity(class_332 context, class_746 player, float centerX, float bottomY, float alpha) {
/*  637 */     Quaternionf bodyRotation = (new Quaternionf()).rotateZ(3.1415927F).rotateY((float)Math.toRadians(this.previewYaw));
/*      */ 
/*      */ 
/*      */     
/*  641 */     float previewScale = 43.0F;
/*  642 */     class_4050 previousPose = player.method_18376();
/*  643 */     boolean scissorEnabled = GL11.glIsEnabled(3089);
/*  644 */     int[] scissor = scissorEnabled ? new int[4] : null;
/*  645 */     if (scissorEnabled) {
/*  646 */       GL11.glGetIntegerv(3088, scissor);
/*      */ 
/*      */ 
/*      */       
/*  650 */       RenderSystem.disableScissor();
/*      */     } 
/*      */     try {
/*  653 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
/*  654 */       player.method_18380(class_4050.field_18076);
/*  655 */       CosmeticsRenderer.renderPreview(this.previewSelection, alpha, this.previewYaw, () -> class_490.method_48472(context, centerX, bottomY, previewScale, new Vector3f(0.0F, 0.0F, 0.0F), bodyRotation, new Quaternionf(), (class_1309)player));
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */       
/*  663 */       player.method_18380(previousPose);
/*  664 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*  665 */       if (scissorEnabled) {
/*  666 */         RenderSystem.enableScissor(scissor[0], scissor[1], scissor[2], scissor[3]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<String> wrapPreviewName(String text, float size, float maxWidth) {
/*  674 */     if (MsdfFonts.bold().getWidth(text, size) <= maxWidth) {
/*  675 */       return List.of(text);
/*      */     }
/*  677 */     String[] words = text.split("\\s+");
/*  678 */     StringBuilder first = new StringBuilder();
/*  679 */     StringBuilder second = new StringBuilder();
/*  680 */     for (String word : words) {
/*  681 */       String candidate = first.isEmpty() ? word : (String.valueOf(first) + " " + String.valueOf(first));
/*  682 */       if (second.isEmpty() && MsdfFonts.bold().getWidth(candidate, size) <= maxWidth) {
/*  683 */         first.setLength(0);
/*  684 */         first.append(candidate);
/*      */       } else {
/*  686 */         if (!second.isEmpty()) second.append(' '); 
/*  687 */         second.append(word);
/*      */       } 
/*      */     } 
/*  690 */     if (first.isEmpty()) first.append(text); 
/*  691 */     if (second.isEmpty()) return List.of(first.toString()); 
/*  692 */     return List.of(first.toString(), second.toString());
/*      */   }
/*      */   
/*      */   private void drawMotionSlider(class_332 context, Layout layout, float sliderY, int mouseX) {
/*  696 */     CosmeticsState state = CosmeticsState.getInstance();
/*  697 */     float percentValue = layout.sliderWidth * state.getWingMotion() / 16.0F;
/*  698 */     float difference = class_3532.method_15363(mouseX - layout.sliderX, 0.0F, layout.sliderWidth);
/*  699 */     if (this.sliderAnimation < 0.0D) this.sliderAnimation = percentValue; 
/*  700 */     this.sliderAnimation = MathUtil.interpolateSmooth(2.5D, this.sliderAnimation, 
/*      */ 
/*      */         
/*  703 */         this.draggingMotion ? difference : percentValue);
/*      */ 
/*      */     
/*  706 */     int background = applyGlobalAlpha(
/*  707 */         MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CARD_OPTIONS, 0.35F));
/*      */     
/*  709 */     int sliderColor = applyGlobalAlpha(MenuStyle.CHIP_ACTIVE);
/*  710 */     int thumbBackground = applyGlobalAlpha(MenuStyle.PANEL_BG);
/*  711 */     rectangle.render(ShapeProperties.create(context
/*  712 */           .method_51448(), layout.sliderX, (sliderY - 1.0F), layout.sliderWidth, 2.0D)
/*      */         
/*  714 */         .round(1.0F).color(background).build());
/*      */     
/*  716 */     float fillWidth = class_3532.method_15363((float)this.sliderAnimation, 0.0F, layout.sliderWidth);
/*      */ 
/*      */     
/*  719 */     if (fillWidth > 0.0F) {
/*  720 */       rectangle.render(ShapeProperties.create(context
/*  721 */             .method_51448(), layout.sliderX, (sliderY - 1.0F), fillWidth, 2.0D)
/*      */           
/*  723 */           .round(1.0F).color(sliderColor).build());
/*      */     }
/*      */     
/*  726 */     float thumbX = class_3532.method_15363(layout.sliderX + (float)this.sliderAnimation, layout.sliderX, layout.sliderX + layout.sliderWidth);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     rectangle.render(ShapeProperties.create(context.method_51448(), (thumbX - 4.0F), (sliderY - 4.0F), 8.0D, 8.0D)
/*  732 */         .round(4.0F).thickness(1.0F)
/*  733 */         .outlineColor(thumbBackground)
/*  734 */         .color(sliderColor).build());
/*      */     
/*  736 */     if (this.draggingMotion) {
/*  737 */       updateMotion(mouseX, layout);
/*      */     }
/*  739 */     String value = String.format(Locale.ROOT, "%.1f°", new Object[] { Float.valueOf(state.getWingMotion()) });
/*  740 */     MsdfRenderer.renderText(
/*  741 */         MsdfFonts.medium(), value, 4.5F, applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  742 */         .method_51448().method_23760().method_23761(), layout.sliderX + layout.sliderWidth - 
/*  743 */         MsdfFonts.medium().getWidth(value, 4.5F), sliderY + 6.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawPetFollowSlider(class_332 context, Layout layout, float sliderY, int mouseX) {
/*  754 */     CosmeticsState state = CosmeticsState.getInstance();
/*      */     
/*  756 */     float percentValue = layout.sliderWidth * state.getPetFollowIntensity();
/*  757 */     float difference = class_3532.method_15363(mouseX - layout.sliderX, 0.0F, layout.sliderWidth);
/*      */ 
/*      */     
/*  760 */     if (this.petSliderAnimation < 0.0D) {
/*  761 */       this.petSliderAnimation = percentValue;
/*      */     }
/*  763 */     this.petSliderAnimation = MathUtil.interpolateSmooth(2.5D, this.petSliderAnimation, 
/*      */ 
/*      */         
/*  766 */         this.draggingPetFollow ? difference : percentValue);
/*      */ 
/*      */     
/*  769 */     int background = applyGlobalAlpha(
/*  770 */         MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CARD_OPTIONS, 0.35F));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  776 */     int sliderColor = applyGlobalAlpha(MenuStyle.CHIP_ACTIVE);
/*  777 */     int thumbBackground = applyGlobalAlpha(MenuStyle.PANEL_BG);
/*  778 */     rectangle.render(ShapeProperties.create(context
/*  779 */           .method_51448(), layout.sliderX, (sliderY - 1.0F), layout.sliderWidth, 2.0D)
/*      */         
/*  781 */         .round(1.0F).color(background).build());
/*      */     
/*  783 */     float fillWidth = class_3532.method_15363((float)this.petSliderAnimation, 0.0F, layout.sliderWidth);
/*      */ 
/*      */     
/*  786 */     if (fillWidth > 0.0F) {
/*  787 */       rectangle.render(ShapeProperties.create(context
/*  788 */             .method_51448(), layout.sliderX, (sliderY - 1.0F), fillWidth, 2.0D)
/*      */           
/*  790 */           .round(1.0F).color(sliderColor).build());
/*      */     }
/*  792 */     float thumbX = class_3532.method_15363(layout.sliderX + (float)this.petSliderAnimation, layout.sliderX, layout.sliderX + layout.sliderWidth);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  797 */     rectangle.render(ShapeProperties.create(context
/*  798 */           .method_51448(), (thumbX - 4.0F), (sliderY - 4.0F), 8.0D, 8.0D)
/*      */         
/*  800 */         .round(4.0F).thickness(1.0F)
/*  801 */         .outlineColor(thumbBackground)
/*  802 */         .color(sliderColor).build());
/*      */     
/*  804 */     if (this.draggingPetFollow) {
/*  805 */       updatePetFollow(mouseX, layout);
/*      */     }
/*  807 */     String value = "" + Math.round(state
/*  808 */         .getPetFollowIntensity() * 100.0F) + "%";
/*      */     
/*  810 */     MsdfRenderer.renderText(
/*  811 */         MsdfFonts.medium(), value, 4.5F, 
/*  812 */         applyGlobalAlpha(MenuStyle.TEXT_MUTED), context
/*  813 */         .method_51448().method_23760().method_23761(), layout.sliderX + layout.sliderWidth - 
/*      */         
/*  815 */         MsdfFonts.medium().getWidth(value, 4.5F), sliderY + 6.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  822 */     if (!this.open || button != 0) return false; 
/*  823 */     Layout layout = layout();
/*      */     
/*  825 */     String[] categories = { "ALL", "WINGS", "CAPES", "HATS", "PETS" };
/*  826 */     float categoryY = layout.cardsTop;
/*  827 */     for (String candidate : categories) {
/*  828 */       if (MathUtil.isHovered(mouseX, mouseY, layout.categoryX, categoryY, 61.0D, 24.0D)) {
/*  829 */         this.category = candidate;
/*  830 */         this.catalogScroll = 0.0D;
/*  831 */         this.smoothedCatalogScroll = 0.0D;
/*  832 */         playButtonClickSound();
/*  833 */         return true;
/*      */       } 
/*  835 */       categoryY += 28.0F;
/*      */     } 
/*      */     
/*  838 */     float cardGap = 6.0F;
/*  839 */     float cardW = (layout.catalogWidth - cardGap) / 2.0F;
/*  840 */     float cardH = 61.0F;
/*  841 */     List<CatalogItem> items = allCatalogItems();
/*  842 */     float viewportHeight = catalogViewportHeight(layout);
/*  843 */     if (!MathUtil.isHovered(mouseX, mouseY, layout.catalogX, layout.cardsTop, layout.catalogWidth, viewportHeight))
/*      */     {
/*  845 */       items = List.of();
/*      */     }
/*  847 */     for (int i = 0; i < items.size(); i++) {
/*  848 */       CatalogItem item = items.get(i);
/*  849 */       float cardX = layout.catalogX + (i % 2) * (cardW + cardGap);
/*  850 */       float cardY = layout.cardsTop + (i / 2) * (cardH + cardGap) + (float)this.smoothedCatalogScroll;
/*      */       
/*  852 */       if (MathUtil.isHovered(mouseX, mouseY, cardX, cardY, cardW, cardH)) {
/*  853 */         if (!item.locked && CosmeticsState.getInstance().isAvailable(item.id)) {
/*  854 */           this.previewSelection = item.id;
/*  855 */           playButtonClickSound();
/*      */         } 
/*  857 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  861 */     if (MathUtil.isHovered(mouseX, mouseY, (layout.previewX + 5.0F), (layout.top + 20.0F), 132.0D, 140.0D)) {
/*      */       
/*  863 */       this.draggingPreview = true;
/*  864 */       return true;
/*      */     } 
/*      */     
/*  867 */     if (CosmeticsState.isWing(this.previewSelection)) {
/*  868 */       if (this.hideElytraToggle.mouseClicked(mouseX, mouseY, button)) {
/*  869 */         return true;
/*      */       }
/*  871 */       float sliderY = layout.top + 202.0F;
/*  872 */       if (MathUtil.isHovered(mouseX, mouseY, (layout.sliderX - 3.0F), (sliderY - 5.0F), (layout.sliderWidth + 6.0F), 13.0D)) {
/*      */         
/*  874 */         this.draggingMotion = true;
/*  875 */         updateMotion(mouseX, layout);
/*  876 */         return true;
/*      */       } 
/*  878 */     } else if (CosmeticsState.isPet(this.previewSelection)) {
/*  879 */       float sliderY = layout.top + 183.0F;
/*  880 */       if (MathUtil.isHovered(mouseX, mouseY, (layout.sliderX - 3.0F), (sliderY - 5.0F), (layout.sliderWidth + 6.0F), 13.0D)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  885 */         this.draggingPetFollow = true;
/*  886 */         updatePetFollow(mouseX, layout);
/*  887 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  891 */     float buttonY = layout.top + layout.contentHeight - 29.0F;
/*  892 */     if (MathUtil.isHovered(mouseX, mouseY, (layout.previewX + 9.0F), buttonY, 124.0D, 20.0D)) {
/*      */       
/*  894 */       CosmeticsState state = CosmeticsState.getInstance();
/*  895 */       boolean alreadyEquipped = state.isEquipped(this.previewSelection);
/*  896 */       if (alreadyEquipped) {
/*  897 */         state.setEquipped(this.previewSelection, false);
/*      */       } else {
/*  899 */         state.setSelected(this.previewSelection);
/*  900 */         state.setEquipped(this.previewSelection, true);
/*      */       } 
/*  902 */       playButtonClickSound();
/*  903 */       return true;
/*      */     } 
/*      */     
/*  906 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/*  911 */     if (!this.open || button != 0) return false; 
/*  912 */     if (this.draggingPreview) {
/*  913 */       this.previewYaw += (float)deltaX * 2.2F;
/*  914 */       return true;
/*      */     } 
/*  916 */     if (this.draggingMotion) {
/*  917 */       updateMotion(mouseX, layout());
/*  918 */       return true;
/*      */     } 
/*  920 */     if (this.draggingPetFollow) {
/*  921 */       updatePetFollow(mouseX, layout());
/*  922 */       return true;
/*      */     } 
/*  924 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/*  929 */     if (button == 0 && (this.draggingPreview || this.draggingMotion || this.draggingPetFollow)) {
/*      */       
/*  931 */       this.draggingPreview = false;
/*  932 */       this.draggingMotion = false;
/*  933 */       this.draggingPetFollow = false;
/*  934 */       return true;
/*      */     } 
/*  936 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/*  941 */     if (!this.open) return false; 
/*  942 */     Layout layout = layout();
/*  943 */     if (!MathUtil.isHovered(mouseX, mouseY, layout.catalogX, layout.cardsTop, layout.catalogWidth, 
/*  944 */         catalogViewportHeight(layout))) {
/*  945 */       return false;
/*      */     }
/*  947 */     double maxScroll = catalogMaxScroll(layout, allCatalogItems().size(), 61.0F, 6.0F);
/*  948 */     if (maxScroll <= 0.0D) return false; 
/*  949 */     this.catalogScroll = Math.max(-maxScroll, 
/*      */         
/*  951 */         Math.min(0.0D, this.catalogScroll + amount * 34.0D));
/*      */     
/*  953 */     return true;
/*      */   }
/*      */   
/*      */   private void updateMotion(double mouseX, Layout layout) {
/*  957 */     float progress = (float)((mouseX - layout.sliderX) / layout.sliderWidth);
/*  958 */     progress = Math.max(0.0F, Math.min(1.0F, progress));
/*  959 */     float stepped = Math.round(progress * 32.0F) / 2.0F;
/*  960 */     CosmeticsState.getInstance().setWingMotion(stepped);
/*      */   }
/*      */   
/*      */   private void updatePetFollow(double mouseX, Layout layout) {
/*  964 */     float progress = (float)((mouseX - layout.sliderX) / layout.sliderWidth);
/*      */ 
/*      */     
/*  967 */     progress = class_3532.method_15363(progress, 0.0F, 1.0F);
/*  968 */     float stepped = Math.round(progress * 20.0F) / 20.0F;
/*  969 */     CosmeticsState.getInstance().setPetFollowIntensity(stepped);
/*      */   }
/*      */   
/*      */   private static float catalogViewportHeight(Layout layout) {
/*  973 */     return Math.max(1.0F, layout.top + layout.contentHeight - layout.cardsTop);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static double catalogMaxScroll(Layout layout, int itemCount, float cardHeight, float cardGap) {
/*  979 */     int rows = (itemCount + 1) / 2;
/*      */ 
/*      */     
/*  982 */     float contentHeight = (rows <= 0) ? 0.0F : (rows * cardHeight + (rows - 1) * cardGap);
/*  983 */     return Math.max(0.0F, contentHeight - catalogViewportHeight(layout));
/*      */   }
/*      */   
/*      */   private List<CatalogItem> allCatalogItems() {
/*  987 */     List<CatalogItem> items = new ArrayList<>();
/*  988 */     int index = 0;
/*  989 */     for (CosmeticsState.CosmeticEntry entry : CosmeticsState.getInstance().getCatalog()) {
/*  990 */       CosmeticsState.CosmeticType type = CosmeticsState.typeOf(entry.name());
/*  991 */       if (!"ALL".equals(this.category)) switch (this.category) { case "WINGS":
/*  992 */             if (type == CosmeticsState.CosmeticType.WING)
/*  993 */               break;  continue;case "CAPES": if (type == CosmeticsState.CosmeticType.CAPE)
/*  994 */               break;  continue;case "HATS": if (type == CosmeticsState.CosmeticType.HAT)
/*  995 */               break;  continue;case "PETS": if (type == CosmeticsState.CosmeticType.PET)
/*      */               break;  continue;
/*      */           default:
/*  998 */             continue; }   int[] colors = cosmeticColors(entry.name(), index++);
/*  999 */       items.add(new CatalogItem(entry.name(), CosmeticsState.displayNameFor(entry.name()), false, colors[0], colors[1]));
/*      */     } 
/* 1001 */     if ("ALL".equals(this.category) || "WINGS".equals(this.category))
/* 1002 */       for (CatalogItem locked : LOCKED_ITEMS) {
/* 1003 */         if (items.size() >= 6)
/* 1004 */           break;  boolean duplicate = items.stream().anyMatch(item -> item.id.equalsIgnoreCase(locked.id));
/* 1005 */         if (!duplicate) items.add(locked);
/*      */       
/*      */       }  
/* 1008 */     return items;
/*      */   }
/*      */   
/*      */   private int[] cosmeticColors(String name, int index) {
/* 1012 */     if ("Wimgs".equalsIgnoreCase(name)) {
/* 1013 */       return new int[] { -2234648, -2053574 };
/*      */     }
/* 1015 */     if ("Fluffy Wings".equalsIgnoreCase(name)) {
/* 1016 */       return new int[] { -1857351, -2586206 };
/*      */     }
/* 1018 */     int[][] palette = { { -8668208, -13079675 }, { -7373880, -11714941 }, { -9258586, -13866400 }, { -2975132, -8367310 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     return palette[Math.floorMod(index, palette.length)];
/*      */   }
/*      */   
/*      */   private Layout layout() {
/* 1028 */     float contentX = this.x + 9.0F;
/* 1029 */     float top = this.y + 41.0F;
/* 1030 */     float contentWidth = this.width - 18.0F;
/* 1031 */     float contentHeight = this.height - 41.0F - 9.0F;
/* 1032 */     float categoryX = contentX;
/* 1033 */     float catalogX = categoryX + 61.0F + 7.0F;
/* 1034 */     float previewX = this.x + this.width - 9.0F - 142.0F;
/* 1035 */     float catalogWidth = previewX - 7.0F - catalogX;
/* 1036 */     float cardsTop = top + 25.0F;
/* 1037 */     float toggleX = previewX + 142.0F - 27.0F;
/* 1038 */     float sliderX = previewX + 9.0F;
/* 1039 */     float sliderWidth = 124.0F;
/* 1040 */     return new Layout(top, contentHeight, categoryX, catalogX, catalogWidth, cardsTop, previewX, toggleX, sliderX, sliderWidth);
/*      */   }
/*      */   private static final class CatalogItem extends Record { private final String id; private final String label; private final boolean locked;
/*      */     private final int primary;
/*      */     private final int accent;
/*      */     
/* 1046 */     private CatalogItem(String id, String label, boolean locked, int primary, int accent) { this.id = id; this.label = label; this.locked = locked; this.primary = primary; this.accent = accent; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogItem;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1046	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 1046 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogItem; } public String id() { return this.id; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogItem;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1046	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogItem; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogItem;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1046	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogItem;
/* 1046 */       //   0	8	1	o	Ljava/lang/Object; } public String label() { return this.label; } public boolean locked() { return this.locked; } public int primary() { return this.primary; } public int accent() { return this.accent; }
/*      */      }
/*      */   private static final class VisibleCard extends Record { private final CosmeticsViewComponent.CatalogItem item; private final float x; private final float y; private final float width; private final float height; private final boolean available; private final boolean selected; private final boolean equipped; private final float selectionProgress; private final String label; private final int textColor;
/* 1049 */     private VisibleCard(CosmeticsViewComponent.CatalogItem item, float x, float y, float width, float height, boolean available, boolean selected, boolean equipped, float selectionProgress, String label, int textColor) { this.item = item; this.x = x; this.y = y; this.width = width; this.height = height; this.available = available; this.selected = selected; this.equipped = equipped; this.selectionProgress = selectionProgress; this.label = label; this.textColor = textColor; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$VisibleCard;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1049	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$VisibleCard; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$VisibleCard;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1049	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$VisibleCard; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$VisibleCard;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1049	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$VisibleCard;
/* 1049 */       //   0	8	1	o	Ljava/lang/Object; } public CosmeticsViewComponent.CatalogItem item() { return this.item; } public float x() { return this.x; } public float y() { return this.y; } public float width() { return this.width; } public float height() { return this.height; } public boolean available() { return this.available; } public boolean selected() { return this.selected; } public boolean equipped() { return this.equipped; } public float selectionProgress() { return this.selectionProgress; } public String label() { return this.label; } public int textColor() { return this.textColor; }
/*      */      }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class CatalogPreviewBox
/*      */     extends Record
/*      */   {
/*      */     private final float x;
/*      */     
/*      */     private final float y;
/*      */     private final float width;
/*      */     private final float height;
/*      */     
/*      */     private CatalogPreviewBox(float x, float y, float width, float height) {
/* 1064 */       this.x = x; this.y = y; this.width = width; this.height = height; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogPreviewBox;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1064	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogPreviewBox; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogPreviewBox;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1064	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogPreviewBox; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogPreviewBox;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1064	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$CatalogPreviewBox;
/* 1064 */       //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public float width() { return this.width; } public float height() { return this.height; }
/*      */      }
/*      */   private static final class PendingCatalogSnapshot extends Record { private final CardSnapshotCache.Snapshot snapshot; private final CosmeticsViewComponent.CatalogPreviewBox box;
/* 1067 */     private PendingCatalogSnapshot(CardSnapshotCache.Snapshot snapshot, CosmeticsViewComponent.CatalogPreviewBox box) { this.snapshot = snapshot; this.box = box; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$PendingCatalogSnapshot;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1067	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$PendingCatalogSnapshot; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$PendingCatalogSnapshot;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1067	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$PendingCatalogSnapshot; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$PendingCatalogSnapshot;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1067	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$PendingCatalogSnapshot;
/* 1067 */       //   0	8	1	o	Ljava/lang/Object; } public CardSnapshotCache.Snapshot snapshot() { return this.snapshot; } public CosmeticsViewComponent.CatalogPreviewBox box() { return this.box; }
/*      */      }
/*      */   private static final class Layout extends Record { private final float top; private final float contentHeight; private final float categoryX; private final float catalogX; private final float catalogWidth; private final float cardsTop; private final float previewX; private final float toggleX;
/*      */     private final float sliderX;
/*      */     private final float sliderWidth;
/*      */     
/* 1073 */     private Layout(float top, float contentHeight, float categoryX, float catalogX, float catalogWidth, float cardsTop, float previewX, float toggleX, float sliderX, float sliderWidth) { this.top = top; this.contentHeight = contentHeight; this.categoryX = categoryX; this.catalogX = catalogX; this.catalogWidth = catalogWidth; this.cardsTop = cardsTop; this.previewX = previewX; this.toggleX = toggleX; this.sliderX = sliderX; this.sliderWidth = sliderWidth; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$Layout;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1073	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$Layout; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$Layout;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1073	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$Layout; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$Layout;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1073	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/other/CosmeticsViewComponent$Layout;
/* 1073 */       //   0	8	1	o	Ljava/lang/Object; } public float top() { return this.top; } public float contentHeight() { return this.contentHeight; } public float categoryX() { return this.categoryX; } public float catalogX() { return this.catalogX; } public float catalogWidth() { return this.catalogWidth; } public float cardsTop() { return this.cardsTop; } public float previewX() { return this.previewX; } public float toggleX() { return this.toggleX; } public float sliderX() { return this.sliderX; } public float sliderWidth() { return this.sliderWidth; }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class CardAnimations
/*      */   {
/* 1088 */     private final Animation hover = (new DecelerateAnimation()).setMs(150).setValue(1.0D);
/* 1089 */     private final Animation selection = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/*      */     
/*      */     private CardAnimations(boolean hovered, boolean selected) {
/* 1092 */       this.hover.setDirectionAndFinish(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 1093 */       this.selection.setDirectionAndFinish(selected ? Direction.FORWARDS : Direction.BACKWARDS);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\CosmeticsViewComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */