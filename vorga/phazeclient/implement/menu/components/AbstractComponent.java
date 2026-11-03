/*     */ package vorga.phazeclient.implement.menu.components;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_6880;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.base.trait.ResizableMovable;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ public abstract class AbstractComponent implements Component, QuickImports, ResizableMovable {
/*     */   public float x;
/*  13 */   public float globalAlpha = 1.0F; public float y; public float width;
/*     */   public float height;
/*  15 */   public double scroll = 0.0D;
/*  16 */   public double smoothedScroll = 0.0D;
/*     */ 
/*     */   
/*     */   public ResizableMovable position(float x, float y) {
/*  20 */     this.x = x;
/*  21 */     this.y = y;
/*  22 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResizableMovable size(float width, float height) {
/*  27 */     this.width = width;
/*  28 */     this.height = height;
/*  29 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {}
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  38 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/*  73 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */   
/*     */   protected float centeredTextY(FontRenderer font, String text) {
/*  77 */     return MenuStyle.centerTextY(font, text, this.y, this.height);
/*     */   }
/*     */   
/*     */   protected float centeredTextY(FontRenderer font, String text, float boxY, float boxHeight) {
/*  81 */     return MenuStyle.centerTextY(font, text, boxY, boxHeight);
/*     */   }
/*     */   
/*     */   protected float centeredTextX(FontRenderer font, String text, float boxX, float boxWidth) {
/*  85 */     return MenuStyle.centerTextX(font, text, boxX, boxWidth);
/*     */   }
/*     */   
/*     */   protected float renderedTextHeight(FontRenderer font, String text) {
/*  89 */     return MenuStyle.renderedTextHeight(font, text);
/*     */   }
/*     */   
/*     */   protected void playButtonClickSound() {
/*  93 */     class_310 client = class_310.method_1551();
/*  94 */     if (client == null || client.method_1483() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     client.method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, 1.0F));
/*     */   }
/*     */   
/*     */   protected int applyGlobalAlpha(int color) {
/* 102 */     return MenuStyle.withAlpha(color, this.globalAlpha);
/*     */   }
/*     */   
/*     */   protected float applyGlobalAlpha(float alpha) {
/* 106 */     return alpha * this.globalAlpha;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\AbstractComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */