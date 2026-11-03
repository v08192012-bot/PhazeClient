/*    */ package vorga.phazeclient.api.system.font.msdf;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MsdfFonts
/*    */ {
/*    */   private static MsdfFont medium;
/*    */   private static MsdfFont bold;
/*    */   private static MsdfFont hud;
/*    */   
/*    */   public static MsdfFont medium() {
/* 12 */     if (medium == null) {
/* 13 */       medium = MsdfFont.builder().atlas("medium").data("medium").build();
/*    */     }
/* 15 */     return medium;
/*    */   }
/*    */   
/*    */   public static MsdfFont bold() {
/* 19 */     if (bold == null) {
/* 20 */       bold = MsdfFont.builder().atlas("bold").data("bold").build();
/*    */     }
/* 22 */     return bold;
/*    */   }
/*    */   
/*    */   public static MsdfFont hud() {
/* 26 */     if (hud == null) {
/* 27 */       hud = MsdfFont.builder().atlas("minecraft-ascii-ttf-msdf").data("minecraft-ascii-ttf-msdf").build();
/*    */     }
/* 29 */     return hud;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void onResourcesReloaded() {
/* 34 */     if (medium != null) medium.onResourcesReloaded(); 
/* 35 */     if (bold != null) bold.onResourcesReloaded(); 
/* 36 */     if (hud != null) hud.onResourcesReloaded(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\MsdfFonts.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */