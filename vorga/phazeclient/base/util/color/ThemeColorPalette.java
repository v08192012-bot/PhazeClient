/*    */ package vorga.phazeclient.base.util.color;
/*    */ 
/*    */ import vorga.phazeclient.implement.menu.MenuPalette;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ThemeColorPalette
/*    */   extends ColorPalette
/*    */ {
/*    */   private final MenuPalette menu;
/*    */   
/*    */   public ThemeColorPalette(MenuPalette menu) {
/* 28 */     this.menu = menu;
/*    */   }
/*    */   
/*    */   private static int opaque(int argb) {
/* 32 */     return argb | 0xFF000000;
/*    */   }
/*    */ 
/*    */   
/*    */   public int mainGuiColor() {
/* 37 */     return opaque(this.menu.panelBg());
/*    */   }
/*    */ 
/*    */   
/*    */   public int guiRectColor() {
/* 42 */     return opaque(this.menu.panelChip());
/*    */   }
/*    */ 
/*    */   
/*    */   public int guiRectColor2() {
/* 47 */     return opaque(this.menu.panelContent());
/*    */   }
/*    */ 
/*    */   
/*    */   public int rectColor() {
/* 52 */     return opaque(this.menu.panelRow());
/*    */   }
/*    */ 
/*    */   
/*    */   public int rectDarkerColor() {
/* 57 */     return opaque(this.menu.panelBgSoft());
/*    */   }
/*    */ 
/*    */   
/*    */   public int textColor() {
/* 62 */     return this.menu.textPrimary();
/*    */   }
/*    */ 
/*    */   
/*    */   public int descriptionColor() {
/* 67 */     return this.menu.textMuted();
/*    */   }
/*    */ 
/*    */   
/*    */   public int outlineColor() {
/* 72 */     return opaque(this.menu.borderLight());
/*    */   }
/*    */ 
/*    */   
/*    */   public int friendColor() {
/* 77 */     return this.menu.accentGreen();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 82 */     return this.menu.name();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDark() {
/* 87 */     int rgb = this.menu.textPrimary();
/* 88 */     int r = rgb >> 16 & 0xFF;
/* 89 */     int g = rgb >> 8 & 0xFF;
/* 90 */     int b = rgb & 0xFF;
/* 91 */     int brightness = (r + g + b) / 3;
/* 92 */     return (brightness > 128);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\color\ThemeColorPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */