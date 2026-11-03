/*    */ package vorga.phazeclient.base.util.color;
/*    */ 
/*    */ public class CustomPalette
/*    */   extends ColorPalette {
/*    */   private final int mainGui;
/*    */   private final int guiRect;
/*    */   private final int guiRect2;
/*    */   private final int rect;
/*    */   private final int rectDarker;
/*    */   private final int text;
/*    */   private final int description;
/*    */   
/*    */   public CustomPalette(int mainGui, int guiRect, int guiRect2, int rect, int rectDarker, int text, int description) {
/* 14 */     this.mainGui = mainGui;
/* 15 */     this.guiRect = guiRect;
/* 16 */     this.guiRect2 = guiRect2;
/* 17 */     this.rect = rect;
/* 18 */     this.rectDarker = rectDarker;
/* 19 */     this.text = text;
/* 20 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public int mainGuiColor() {
/* 25 */     return this.mainGui;
/*    */   }
/*    */ 
/*    */   
/*    */   public int guiRectColor() {
/* 30 */     return this.guiRect;
/*    */   }
/*    */ 
/*    */   
/*    */   public int guiRectColor2() {
/* 35 */     return this.guiRect2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rectColor() {
/* 40 */     return this.rect;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rectDarkerColor() {
/* 45 */     return this.rectDarker;
/*    */   }
/*    */ 
/*    */   
/*    */   public int textColor() {
/* 50 */     return this.text;
/*    */   }
/*    */ 
/*    */   
/*    */   public int descriptionColor() {
/* 55 */     return this.description;
/*    */   }
/*    */ 
/*    */   
/*    */   public int outlineColor() {
/* 60 */     return isDark() ? -12961214 : -3355444;
/*    */   }
/*    */ 
/*    */   
/*    */   public int friendColor() {
/* 65 */     return isDark() ? -11870592 : -14498466;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 70 */     return "Custom";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDark() {
/* 75 */     int r = this.mainGui >> 16 & 0xFF;
/* 76 */     int g = this.mainGui >> 8 & 0xFF;
/* 77 */     int b = this.mainGui & 0xFF;
/* 78 */     int brightness = (r + g + b) / 3;
/* 79 */     return (brightness < 128);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\color\CustomPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */