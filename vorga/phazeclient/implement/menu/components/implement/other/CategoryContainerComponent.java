/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.class_332;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.snapshot.CardSnapshotCache;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.category.CategoryComponent;
/*     */ 
/*     */ public class CategoryContainerComponent extends AbstractComponent {
/*     */   public CategoryContainerComponent setCachedTotalWidth(float cachedTotalWidth) {
/*  15 */     this.cachedTotalWidth = cachedTotalWidth; return this;
/*     */   }
/*     */   
/*     */   private static final float CHIP_TEXT_SIZE = 6.25F;
/*     */   private static final float CHIP_HEIGHT = 14.0F;
/*     */   private static final float CHIP_HORIZONTAL_PADDING = 16.0F;
/*     */   private static final float CHIP_GAP = 4.0F;
/*  22 */   private static final List<ModuleCategory> CHIP_CATEGORIES = List.of(ModuleCategory.ALL, ModuleCategory.HUD, ModuleCategory.UTILITIES, ModuleCategory.OTHER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   private final List<CategoryComponent> categoryComponents = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private float cachedTotalWidth = -1.0F;
/*     */   
/*     */   public void ensureCategoryComponentsInitialized() {
/*  46 */     if (!this.categoryComponents.isEmpty()) {
/*     */       return;
/*     */     }
/*  49 */     initializeCategoryComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeCategoryComponents() {
/*  59 */     CardSnapshotCache.clearAll();
/*  60 */     this.categoryComponents.clear();
/*  61 */     Objects.requireNonNull(this.categoryComponents); CHIP_CATEGORIES.stream().map(CategoryComponent::new).forEach(this.categoryComponents::add);
/*  62 */     this.cachedTotalWidth = -1.0F;
/*     */   }
/*     */   
/*     */   public float getTotalWidth() {
/*  66 */     if (this.cachedTotalWidth >= 0.0F) {
/*  67 */       return this.cachedTotalWidth;
/*     */     }
/*  69 */     float total = 0.0F;
/*  70 */     for (int i = 0; i < this.categoryComponents.size(); i++) {
/*  71 */       String label = ((CategoryComponent)this.categoryComponents.get(i)).getTabLabel();
/*  72 */       total += MsdfFonts.bold().getWidth(label, 6.25F) + 16.0F;
/*  73 */       if (i < this.categoryComponents.size() - 1) {
/*  74 */         total += 4.0F;
/*     */       }
/*     */     } 
/*  77 */     this.cachedTotalWidth = total;
/*  78 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  84 */     float offset = 0.0F;
/*  85 */     for (CategoryComponent component : this.categoryComponents) {
/*  86 */       String label = component.getTabLabel();
/*  87 */       float chipWidth = MsdfFonts.bold().getWidth(label, 6.25F) + 16.0F;
/*     */       
/*  89 */       component.x = this.x + offset - 5.0F;
/*  90 */       component.y = this.y;
/*  91 */       component.width = chipWidth;
/*  92 */       component.height = 14.0F;
/*  93 */       component.globalAlpha = this.globalAlpha;
/*     */       
/*  95 */       component.render(context, mouseX, mouseY, delta);
/*  96 */       offset += chipWidth + 4.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 102 */     this.categoryComponents.forEach(AbstractComponent::tick);
/* 103 */     super.tick();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 109 */     return (this.categoryComponents.stream()
/* 110 */       .anyMatch(categoryComponent -> categoryComponent.mouseClicked(mouseX, mouseY, button)) || super
/* 111 */       .mouseClicked(mouseX, mouseY, button));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 117 */     this.categoryComponents.forEach(categoryComponent -> categoryComponent.mouseReleased(mouseX, mouseY, button));
/* 118 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 124 */     this.categoryComponents.forEach(categoryComponent -> categoryComponent.mouseDragged(mouseX, mouseY, button, deltaX, deltaY));
/* 125 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 131 */     this.categoryComponents.forEach(categoryComponent -> categoryComponent.mouseScrolled(mouseX, mouseY, amount));
/* 132 */     return super.mouseScrolled(mouseX, mouseY, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 138 */     this.categoryComponents.forEach(categoryComponent -> categoryComponent.keyPressed(keyCode, scanCode, modifiers));
/* 139 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 145 */     this.categoryComponents.forEach(categoryComponent -> categoryComponent.charTyped(chr, modifiers));
/* 146 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */   
/*     */   public boolean isInteractiveHover(double mouseX, double mouseY) {
/* 150 */     return this.categoryComponents.stream().anyMatch(categoryComponent -> categoryComponent.isHover(mouseX, mouseY));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\CategoryContainerComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */