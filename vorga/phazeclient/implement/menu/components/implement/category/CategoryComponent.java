/*     */ package vorga.phazeclient.implement.menu.components.implement.category;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.module.ModuleComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.ModuleDescriptionComponent;
/*     */ 
/*     */ public class CategoryComponent extends AbstractComponent {
/*     */   private static final float CHIP_TEXT_SIZE = 6.25F;
/*     */   private static final int COLUMN_COUNT = 3;
/*     */   private static final int COLUMN_GAP = 6;
/*     */   private static final int ROW_GAP = 8;
/*     */   private static final float SIDEBAR_WIDTH = 100.0F;
/*     */   private static final float GRID_TOP = 55.0F;
/*     */   private static final float GRID_BOTTOM_PADDING = 6.0F;
/*  41 */   private final List<ModuleComponent> moduleComponents = new ArrayList<>();
/*     */   public ModuleCategory getCategory() {
/*  43 */     return this.category;
/*     */   }
/*     */   private final ModuleCategory category;
/*  46 */   private final Animation hoverAnimation = (new DecelerateAnimation()).setMs(180).setValue(1.0D);
/*  47 */   private final Animation selectionAnimation = (new DecelerateAnimation()).setMs(220).setValue(1.0D);
/*     */   
/*  49 */   private final Map<ModuleComponent, Integer> assignedColumns = new HashMap<>();
/*  50 */   private final List<ModuleComponent> visibleComponents = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private final int[] cachedColumnHeights = new int[3];
/*  61 */   private int cachedMaxColumnHeight = 0;
/*  62 */   private ModuleCategory lastCategory = null;
/*  63 */   private String lastSearchText = "";
/*     */   
/*     */   private boolean visibleCacheDirty = true;
/*     */   private boolean isInitialized = false;
/*  67 */   private int mouseX = 0;
/*  68 */   private int mouseY = 0;
/*     */   
/*     */   public CategoryComponent(ModuleCategory category) {
/*  71 */     this.category = category;
/*  72 */     initialize();
/*     */   }
/*     */   
/*     */   public String getTabLabel() {
/*  76 */     return this.category.getReadableName();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  80 */     List<Module> modules = new ArrayList<>(Main.getInstance().getModuleProvider().getModules());
/*     */ 
/*     */ 
/*     */     
/*  84 */     modules.sort((a, b) -> a.getVisibleName().compareToIgnoreCase(b.getVisibleName()));
/*     */     
/*  86 */     for (Module module : modules) {
/*  87 */       this.moduleComponents.add(new ModuleComponent(module));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  93 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/*     */     
/*  95 */     this.mouseX = mouseX;
/*  96 */     this.mouseY = mouseY;
/*     */     
/*  98 */     drawCategoryTab(context, context.method_51448(), mouseX, mouseY);
/*     */     
/* 100 */     ModuleCategory currentCategory = menuScreen.getCategory();
/* 101 */     if (!shouldRenderContentForCurrentCategory(currentCategory)) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     String currentSearchText = menuScreen.getSearchComponent().getText().toLowerCase();
/* 106 */     refreshVisibleCache(currentCategory, currentSearchText);
/*     */     
/* 108 */     if (!this.isInitialized && currentCategory == this.category) {
/* 109 */       this.isInitialized = true;
/* 110 */       this.lastCategory = currentCategory;
/* 111 */       this.lastSearchText = currentSearchText;
/* 112 */       assignColumns();
/* 113 */     } else if (!currentCategory.equals(this.lastCategory) || !currentSearchText.equals(this.lastSearchText) || this.visibleCacheDirty) {
/* 114 */       this.lastCategory = currentCategory;
/* 115 */       this.lastSearchText = currentSearchText;
/* 116 */       assignColumns();
/*     */     } 
/* 118 */     this.visibleCacheDirty = false;
/*     */     
/* 120 */     float gridX = menuScreen.x + 100.0F + 6.0F;
/* 121 */     float gridY = menuScreen.y + 55.0F;
/* 122 */     float gridWidth = menuScreen.width - 100.0F - 12.0F;
/* 123 */     float gridHeight = menuScreen.height - 55.0F - 6.0F;
/* 124 */     float columnWidth = (gridWidth - 12.0F) / 3.0F;
/*     */     
/* 126 */     Matrix4f positionMatrix = context.method_51448().method_23760().method_23761();
/* 127 */     ScissorManager scissorManager = Main.getInstance().getScissorManager();
/* 128 */     scissorManager.push(positionMatrix, gridX, gridY, gridWidth, gridHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     int[] offsets = new int[3];
/* 134 */     System.arraycopy(this.cachedColumnHeights, 0, offsets, 0, 3);
/* 135 */     float gridBottom = gridY + gridHeight;
/* 136 */     for (int i = this.visibleComponents.size() - 1; i >= 0; i--) {
/* 137 */       ModuleComponent component = this.visibleComponents.get(i);
/* 138 */       int componentHeight = component.getComponentHeight() + 8;
/* 139 */       int column = ((Integer)this.assignedColumns.getOrDefault(component, Integer.valueOf(0))).intValue();
/*     */       
/* 141 */       component.x = gridX + column * (columnWidth + 6.0F);
/* 142 */       component.y = (float)((gridY + 8.0F + offsets[column] - componentHeight) + this.smoothedScroll);
/* 143 */       component.width = columnWidth;
/*     */ 
/*     */ 
/*     */       
/* 147 */       component.height = component.getComponentHeight();
/*     */       
/* 149 */       float cardTop = component.y;
/* 150 */       float cardBottom = cardTop + component.height;
/* 151 */       boolean offscreen = (cardBottom < gridY || cardTop > gridBottom);
/* 152 */       if (!offscreen) {
/* 153 */         component.globalAlpha = this.globalAlpha;
/* 154 */         component.render(context, mouseX, mouseY, delta);
/*     */       } 
/* 156 */       offsets[column] = offsets[column] - componentHeight;
/*     */     } 
/*     */     
/* 159 */     scissorManager.pop();
/* 160 */     renderTooltips(context);
/*     */     
/* 162 */     int maxScrollHeight = calculateMaxScrollHeight();
/* 163 */     this.scroll = class_3532.method_15350(this.scroll, -maxScrollHeight, 0.0D);
/* 164 */     this.smoothedScroll = MathUtil.interpolateSmooth(2.0D, this.smoothedScroll, this.scroll);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 169 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/*     */     
/* 171 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height) && button == 0) {
/* 172 */       playButtonClickSound();
/* 173 */       menuScreen.closeModuleDetail();
/* 174 */       MenuScreen.INSTANCE.setCategory(this.category);
/* 175 */       return true;
/*     */     } 
/*     */     
/* 178 */     ModuleCategory currentCategory = menuScreen.getCategory();
/* 179 */     if (!shouldRenderContentForCurrentCategory(currentCategory)) {
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     float gridX = menuScreen.x + 100.0F + 6.0F;
/* 184 */     float gridY = menuScreen.y + 55.0F;
/* 185 */     float gridWidth = menuScreen.width - 100.0F - 12.0F;
/* 186 */     float gridHeight = menuScreen.height - 55.0F - 6.0F;
/* 187 */     if (MathUtil.isHovered(mouseX, mouseY, gridX, gridY, gridWidth, gridHeight)) {
/*     */ 
/*     */       
/* 190 */       boolean anyHovered = false;
/* 191 */       for (ModuleComponent moduleComponent : this.visibleComponents) {
/* 192 */         if (moduleComponent.isHover(mouseX, mouseY)) {
/* 193 */           anyHovered = true;
/* 194 */           if (moduleComponent.mouseClicked(mouseX, mouseY, button)) {
/* 195 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 199 */       if (anyHovered) {
/* 200 */         return super.mouseClicked(mouseX, mouseY, button);
/*     */       }
/*     */     } 
/*     */     
/* 204 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 210 */     for (ModuleComponent moduleComponent : this.visibleComponents) {
/* 211 */       if (moduleComponent.isHover(mouseX, mouseY)) {
/* 212 */         return true;
/*     */       }
/*     */     } 
/* 215 */     return super.isHover(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 220 */     this.visibleComponents.forEach(moduleComponent -> moduleComponent.mouseReleased(mouseX, mouseY, button));
/* 221 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 226 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 227 */     float gridX = menuScreen.x + 100.0F + 6.0F;
/* 228 */     float gridY = menuScreen.y + 55.0F;
/* 229 */     float gridWidth = menuScreen.width - 100.0F - 12.0F;
/* 230 */     float gridHeight = menuScreen.height - 55.0F - 6.0F;
/*     */     
/* 232 */     if (MathUtil.isHovered(mouseX, mouseY, gridX, gridY, gridWidth, gridHeight)) {
/* 233 */       this.scroll += amount * 20.0D;
/*     */     }
/*     */     
/* 236 */     this.visibleComponents.forEach(moduleComponent -> moduleComponent.mouseScrolled(mouseX, mouseY, amount));
/* 237 */     return super.mouseScrolled(mouseX, mouseY, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 242 */     this.visibleComponents.forEach(moduleComponent -> moduleComponent.keyPressed(keyCode, scanCode, modifiers));
/* 243 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 248 */     this.visibleComponents.forEach(moduleComponent -> moduleComponent.charTyped(chr, modifiers));
/* 249 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */   
/*     */   private void drawCategoryTab(class_332 context, class_4587 matrix, int mouseX, int mouseY) {
/* 253 */     boolean isSelected = (MenuScreen.INSTANCE.getCategory() == this.category);
/* 254 */     this.selectionAnimation.setDirection(isSelected ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/* 256 */     boolean isHovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/* 257 */     this.hoverAnimation.setDirection(isHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (isHovered) {
/* 263 */       CursorManager.requestHand();
/*     */     }
/*     */     
/* 266 */     float hoverProgress = this.hoverAnimation.getOutputFloat();
/* 267 */     float selectionProgress = this.selectionAnimation.getOutputFloat();
/*     */     
/* 269 */     String label = getTabLabel();
/* 270 */     int accentColor = label.equals("NEW") ? MenuStyle.CHIP_NEW : MenuStyle.CHIP_ACTIVE;
/* 271 */     int baseChipColor = MenuStyle.mix(MenuStyle.PANEL_CHIP, accentColor, 0.24F);
/* 272 */     int hoverChipColor = MenuStyle.mix(baseChipColor, accentColor, 0.2F);
/* 273 */     int selectedChipColor = MenuStyle.mix(baseChipColor, accentColor, 0.44F);
/* 274 */     int chipColor = MenuStyle.mix(MenuStyle.mix(baseChipColor, hoverChipColor, hoverProgress), selectedChipColor, selectionProgress);
/*     */     
/* 276 */     float textEmphasis = Math.min(1.0F, 0.16F + hoverProgress * 0.18F + selectionProgress * 0.76F);
/* 277 */     int textColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, textEmphasis);
/*     */     
/* 279 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/* 280 */         .round(2.0F).thickness(1.2F).outlineColor(applyGlobalAlpha(MenuStyle.BORDER)).color(applyGlobalAlpha(chipColor))
/* 281 */         .build());
/*     */     
/* 283 */     MsdfRenderer.renderText(
/* 284 */         MsdfFonts.bold(), label, 6.25F, 
/*     */ 
/*     */         
/* 287 */         applyGlobalAlpha(textColor), matrix
/* 288 */         .method_23760().method_23761(), 
/* 289 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), label, 6.25F, this.x, this.width), 
/* 290 */         MenuStyle.centerMsdfTextY(6.25F, this.y, this.height), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldRenderContentForCurrentCategory(ModuleCategory currentCategory) {
/* 296 */     if (currentCategory == ModuleCategory.SEARCH)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       return (this.category == ModuleCategory.ALL);
/*     */     }
/* 308 */     return (this.category == currentCategory);
/*     */   }
/*     */   
/*     */   private void assignColumns() {
/* 312 */     this.assignedColumns.clear();
/* 313 */     int[] heights = new int[3];
/*     */     
/* 315 */     for (int i = this.visibleComponents.size() - 1; i >= 0; i--) {
/* 316 */       ModuleComponent component = this.visibleComponents.get(i);
/* 317 */       int shortestColumn = findShortestColumn(heights);
/* 318 */       this.assignedColumns.put(component, Integer.valueOf(shortestColumn));
/* 319 */       heights[shortestColumn] = heights[shortestColumn] + component.getComponentHeight() + 8;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 324 */     System.arraycopy(heights, 0, this.cachedColumnHeights, 0, 3);
/* 325 */     int max = 0;
/* 326 */     for (int h : heights) {
/* 327 */       if (h > max) max = h; 
/*     */     } 
/* 329 */     this.cachedMaxColumnHeight = max;
/*     */   }
/*     */   
/*     */   private int findShortestColumn(int[] heights) {
/* 333 */     int shortest = 0;
/* 334 */     for (int i = 1; i < heights.length; i++) {
/* 335 */       if (heights[i] < heights[shortest]) {
/* 336 */         shortest = i;
/*     */       }
/*     */     } 
/* 339 */     return shortest;
/*     */   }
/*     */   
/*     */   private int calculateMaxScrollHeight() {
/* 343 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 344 */     int visibleHeight = Math.round(menuScreen.height - 55.0F - 6.0F);
/* 345 */     int maxScroll = this.cachedMaxColumnHeight - visibleHeight;
/* 346 */     return Math.max(0, maxScroll);
/*     */   }
/*     */   
/*     */   private boolean shouldRenderComponent(ModuleComponent component) {
/* 350 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 351 */     ModuleCategory moduleCategory = component.getModule().getCategory();
/* 352 */     ModuleCategory secondaryCategory = component.getModule().getSecondaryCategory();
/* 353 */     ModuleCategory currentCategory = menuScreen.getCategory();
/* 354 */     String searchText = menuScreen.getSearchComponent().getText().toLowerCase();
/*     */     
/* 356 */     if (!component.getModule().isVisible()) {
/* 357 */       return false;
/*     */     }
/*     */     
/* 360 */     if (currentCategory == ModuleCategory.SEARCH) {
/* 361 */       if (searchText.equalsIgnoreCase("")) {
/* 362 */         return false;
/*     */       }
/*     */       
/* 365 */       String moduleName = component.getModule().getLocalizedName().toLowerCase();
/* 366 */       if (moduleName.contains(searchText)) {
/* 367 */         return true;
/*     */       }
/*     */       
/* 370 */       return component.getModule().settings().stream().anyMatch(setting -> searchInSetting(setting, searchText));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (currentCategory == ModuleCategory.ALL) {
/* 376 */       return true;
/*     */     }
/*     */     
/* 379 */     return (moduleCategory.equals(currentCategory) || (secondaryCategory != null && secondaryCategory
/* 380 */       .equals(currentCategory)));
/*     */   }
/*     */   
/*     */   private void refreshVisibleCache(ModuleCategory currentCategory, String currentSearchText) {
/* 384 */     List<ModuleComponent> nextVisible = new ArrayList<>();
/* 385 */     for (ModuleComponent moduleComponent : this.moduleComponents) {
/* 386 */       if (shouldRenderComponent(moduleComponent)) {
/* 387 */         nextVisible.add(moduleComponent);
/*     */       }
/*     */     } 
/*     */     
/* 391 */     if (Objects.equals(nextVisible, this.visibleComponents)) {
/* 392 */       this.visibleCacheDirty = false;
/*     */       
/*     */       return;
/*     */     } 
/* 396 */     this.visibleComponents.clear();
/* 397 */     this.visibleComponents.addAll(nextVisible);
/* 398 */     this.visibleCacheDirty = true;
/*     */   }
/*     */   
/*     */   private boolean searchInSetting(Setting setting, String searchText) {
/* 402 */     if (setting.getLocalizedName().toLowerCase().contains(searchText)) {
/* 403 */       return true;
/*     */     }
/*     */     
/* 406 */     if (setting.getLocalizedDescription() != null && setting.getLocalizedDescription().toLowerCase().contains(searchText)) {
/* 407 */       return true;
/*     */     }
/*     */     
/* 410 */     if (setting instanceof SelectSetting) { SelectSetting selectSetting = (SelectSetting)setting; if (selectSetting.getList() != null) {
/* 411 */         return selectSetting.getList().stream().anyMatch(option -> option.toLowerCase().contains(searchText));
/*     */       } }
/*     */     
/* 414 */     if (setting instanceof MultiSelectSetting) { MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting; if (multiSelectSetting.getList() != null) {
/* 415 */         return multiSelectSetting.getList().stream().anyMatch(option -> option.toLowerCase().contains(searchText));
/*     */       } }
/*     */     
/* 418 */     if (setting instanceof GroupSetting) { GroupSetting groupSetting = (GroupSetting)setting;
/* 419 */       return groupSetting.getSubSettings().stream().anyMatch(subSetting -> searchInSetting(subSetting, searchText)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     return false;
/*     */   }
/*     */   
/*     */   private void renderTooltips(class_332 context) {
/* 428 */     ModuleDescriptionComponent descriptionComponent = MenuScreen.INSTANCE.getModuleDescriptionComponent();
/* 429 */     if (descriptionComponent != null) {
/* 430 */       descriptionComponent.hide();
/*     */     }
/*     */   }
/*     */   
/*     */   private int countAllSettings(Module module) {
/* 435 */     int count = 0;
/* 436 */     for (Setting setting : module.settings()) {
/* 437 */       count++;
/*     */       
/* 439 */       if (setting instanceof GroupSetting) { GroupSetting groupSetting = (GroupSetting)setting;
/* 440 */         count += countSettingsInGroup(groupSetting); }
/*     */     
/*     */     } 
/* 443 */     return count;
/*     */   }
/*     */   
/*     */   private int countSettingsInGroup(GroupSetting group) {
/* 447 */     int count = 0;
/* 448 */     for (Setting setting : group.getSubSettings()) {
/* 449 */       count++;
/*     */       
/* 451 */       if (setting instanceof GroupSetting) { GroupSetting nestedGroup = (GroupSetting)setting;
/* 452 */         count += countSettingsInGroup(nestedGroup); }
/*     */     
/*     */     } 
/* 455 */     return count;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\category\CategoryComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */