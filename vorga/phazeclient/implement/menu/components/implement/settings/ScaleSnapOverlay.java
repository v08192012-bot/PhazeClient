/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
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
/*     */ public final class ScaleSnapOverlay
/*     */ {
/*     */   private static final float VALUE_SIZE = 34.0F;
/*     */   private static final float HINT_SIZE = 9.0F;
/*     */   private static final float GAP = 6.0F;
/*     */   private static final long STALE_AFTER_MS = 250L;
/*     */   private static volatile String valueText;
/*     */   private static volatile String hintText;
/*     */   private static volatile boolean snapped;
/*     */   private static volatile long updatedAt;
/*     */   
/*     */   public static void show(float value, boolean snapped, String hint) {
/*  58 */     valueText = "x" + String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf(value) });
/*  59 */     hintText = hint;
/*  60 */     ScaleSnapOverlay.snapped = snapped;
/*  61 */     updatedAt = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public static void hide() {
/*  65 */     valueText = null;
/*     */   }
/*     */   
/*     */   public static void render(class_332 context) {
/*  69 */     String value = valueText;
/*  70 */     if (value == null) {
/*     */       return;
/*     */     }
/*  73 */     if (System.currentTimeMillis() - updatedAt > 250L) {
/*  74 */       valueText = null;
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     class_310 client = class_310.method_1551();
/*  79 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     double guiScale = client.method_22683().method_4495();
/*  84 */     if (guiScale <= 0.0D) {
/*  85 */       guiScale = 1.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     float centerX = client.method_22683().method_4486() / 2.0F;
/*  91 */     float centerY = client.method_22683().method_4502() / 2.0F;
/*     */     
/*  93 */     class_4587 matrices = context.method_51448();
/*  94 */     matrices.method_22903();
/*  95 */     matrices.method_46416(centerX, centerY, 0.0F);
/*  96 */     float inverse = (float)(1.0D / guiScale);
/*  97 */     matrices.method_22905(inverse, inverse, 1.0F);
/*     */     
/*  99 */     int valueColor = snapped ? MenuStyle.CHIP_ACTIVE : MenuStyle.TEXT_PRIMARY;
/*     */     
/* 101 */     float valueX = -MsdfFonts.bold().getWidth(value, 34.0F) / 2.0F;
/* 102 */     MsdfRenderer.renderText(
/* 103 */         MsdfFonts.bold(), value, 34.0F, valueColor, matrices
/*     */         
/* 105 */         .method_23760().method_23761(), valueX, -17.0F, 0.0F);
/*     */ 
/*     */     
/* 108 */     String hint = hintText;
/* 109 */     if (hint != null && !hint.isEmpty()) {
/* 110 */       float hintX = -MsdfFonts.medium().getWidth(hint, 9.0F) / 2.0F;
/* 111 */       MsdfRenderer.renderText(
/* 112 */           MsdfFonts.medium(), hint, 9.0F, MenuStyle.TEXT_MUTED, matrices
/*     */           
/* 114 */           .method_23760().method_23761(), hintX, 23.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     matrices.method_22909();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\ScaleSnapOverlay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */