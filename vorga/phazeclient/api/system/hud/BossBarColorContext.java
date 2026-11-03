/*    */ package vorga.phazeclient.api.system.hud;
/*    */ 
/*    */ public final class BossBarColorContext
/*    */ {
/*  5 */   private static int tint = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int setTint(int color) {
/* 11 */     int previous = tint;
/* 12 */     tint = color;
/* 13 */     return previous;
/*    */   }
/*    */   
/*    */   public static int applyTint(int color) {
/* 17 */     if (tint == -1) return color; 
/* 18 */     int a = (color >>> 24) * (tint >>> 24) / 255;
/* 19 */     int r = (color >>> 16 & 0xFF) * (tint >>> 16 & 0xFF) / 255;
/* 20 */     int g = (color >>> 8 & 0xFF) * (tint >>> 8 & 0xFF) / 255;
/* 21 */     int b = (color & 0xFF) * (tint & 0xFF) / 255;
/* 22 */     return a << 24 | r << 16 | g << 8 | b;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\hud\BossBarColorContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */