/*     */ package vorga.phazeclient.implement.menu;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.api.system.shape.implement.Rectangle;
/*     */ import vorga.phazeclient.base.util.PhazeAnnouncements;
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
/*     */ public final class AnnouncementOverlay
/*     */ {
/*     */   private static final float MAX_WIDTH = 320.0F;
/*     */   private static final float PAD_X = 12.0F;
/*     */   private static final float PAD_Y = 9.0F;
/*     */   private static final float TEXT_SIZE = 8.0F;
/*     */   private static final float TOP_FRACTION = 0.17F;
/*     */   private static final float MIN_TOP_MARGIN = 26.0F;
/*     */   private static final int BACKGROUND = -16777216;
/*     */   private static final float BACKGROUND_ALPHA = 0.5F;
/*     */   private static final float CARD_GAP = 5.0F;
/*     */   private static final float CORNER = 6.0F;
/*     */   private static final float RENDER_THICKNESS = 0.05F;
/*     */   private static final float PER_GLYPH_EXTRA = 0.2F;
/*     */   private static final float BASELINE_NUDGE = 1.15F;
/*     */   private static final long FADE_MS = 600L;
/*  70 */   private static final Rectangle RECTANGLE = new Rectangle();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void render(class_332 context) {
/*  76 */     List<PhazeAnnouncements.Banner> banners = PhazeAnnouncements.activeBanners();
/*  77 */     if (banners.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     class_310 client = class_310.method_1551();
/*  82 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     float screenWidth = client.method_22683().method_4486();
/*  87 */     float screenHeight = client.method_22683().method_4502();
/*  88 */     float maxCardWidth = Math.min(320.0F, screenWidth - 24.0F);
/*  89 */     float maxTextWidth = maxCardWidth - 24.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     float y = Math.max(26.0F, screenHeight * 0.17F);
/*     */     
/*  96 */     class_4587 matrices = context.method_51448();
/*  97 */     long now = System.currentTimeMillis();
/*     */     
/*  99 */     for (PhazeAnnouncements.Banner banner : banners) {
/* 100 */       String[] lines = wrap(banner.text(), maxTextWidth);
/*     */ 
/*     */ 
/*     */       
/* 104 */       float widest = 0.0F;
/* 105 */       for (String line : lines) {
/* 106 */         widest = Math.max(widest, measure(line));
/*     */       }
/* 108 */       float cardWidth = Math.min(maxCardWidth, widest + 24.0F);
/*     */       
/* 110 */       float lineHeight = 11.0F;
/* 111 */       float textBlockHeight = lines.length * lineHeight - 3.0F;
/* 112 */       float cardHeight = 18.0F + textBlockHeight;
/* 113 */       float x = (screenWidth - cardWidth) / 2.0F;
/*     */       
/* 115 */       long remaining = banner.shownUntilMs() - now;
/*     */ 
/*     */       
/* 118 */       float alpha = (remaining >= 600L) ? 1.0F : Math.max(0.0F, (float)remaining / 600.0F);
/*     */       
/* 120 */       RECTANGLE.render(ShapeProperties.create(matrices, x, y, cardWidth, cardHeight)
/* 121 */           .round(6.0F)
/* 122 */           .color(MenuStyle.withAlpha(-16777216, alpha * 0.5F))
/* 123 */           .build());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       float textY = y + (cardHeight - textBlockHeight) / 2.0F + 1.15F;
/* 129 */       for (String line : lines) {
/* 130 */         float lineX = x + (cardWidth - measure(line)) / 2.0F;
/* 131 */         MsdfRenderer.renderText(
/* 132 */             MsdfFonts.bold(), line, 8.0F, 
/* 133 */             MenuStyle.withAlpha(banner.color(), alpha), matrices
/* 134 */             .method_23760().method_23761(), lineX, textY, 0.0F);
/*     */         
/* 136 */         textY += lineHeight;
/*     */       } 
/*     */       
/* 139 */       y += cardHeight + 5.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float measure(String text) {
/* 148 */     if (text == null || text.isEmpty()) {
/* 149 */       return 0.0F;
/*     */     }
/* 151 */     return MsdfFonts.bold().getWidth(text, 8.0F) + text.length() * 0.2F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] wrap(String text, float maxWidth) {
/* 160 */     List<String> lines = new ArrayList<>();
/* 161 */     StringBuilder current = new StringBuilder();
/*     */     
/* 163 */     for (String word : text.split(" ")) {
/* 164 */       String candidate = current.isEmpty() ? word : (String.valueOf(current) + " " + String.valueOf(current));
/* 165 */       if (measure(candidate) <= maxWidth) {
/* 166 */         current.setLength(0);
/* 167 */         current.append(candidate);
/*     */       } else {
/*     */         
/* 170 */         if (!current.isEmpty()) {
/* 171 */           lines.add(current.toString());
/* 172 */           current.setLength(0);
/*     */         } 
/* 174 */         while (measure(word) > maxWidth && word.length() > 1) {
/* 175 */           int cut = word.length();
/* 176 */           while (cut > 1 && measure(word.substring(0, cut)) > maxWidth) {
/* 177 */             cut--;
/*     */           }
/* 179 */           lines.add(word.substring(0, cut));
/* 180 */           word = word.substring(cut);
/*     */         } 
/* 182 */         current.append(word);
/*     */       } 
/* 184 */     }  if (!current.isEmpty()) {
/* 185 */       lines.add(current.toString());
/*     */     }
/* 187 */     (new String[1])[0] = text; return lines.isEmpty() ? new String[1] : lines.<String>toArray(new String[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\AnnouncementOverlay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */