/*     */ package vorga.phazeclient.implement.menu.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFont;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ 
/*     */ public final class MsdfGuiFontRenderer extends FontRenderer {
/*     */   private static final int LAYOUT_CACHE_LIMIT = 512;
/*     */   private final float size;
/*     */   private final boolean bold;
/*  21 */   private final Map<String, GuiTextLayout> layouts = new HashMap<>();
/*     */ 
/*     */   
/*     */   MsdfGuiFontRenderer(float size, boolean bold) {
/*  25 */     super(new Font("SansSerif", bold ? 1 : 0, 1), size);
/*  26 */     this.size = size;
/*  27 */     this.bold = bold;
/*     */   }
/*     */   
/*     */   public MsdfFont msdfFont() {
/*  31 */     return atlas(this.bold);
/*     */   }
/*     */   
/*     */   public float msdfSize() {
/*  35 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public float msdfY(double y) {
/*  40 */     return (float)y - 1.85F;
/*     */   }
/*     */   
/*     */   private static MsdfFont atlas(boolean bold) {
/*  44 */     return bold ? MsdfFonts.bold() : MsdfFonts.medium();
/*     */   }
/*     */   
/*     */   private GuiTextLayout layout(String text) {
/*  48 */     String source = (text == null) ? "" : text;
/*  49 */     GuiTextLayout cached = this.layouts.get(source);
/*  50 */     if (cached != null) return cached; 
/*  51 */     GuiTextLayout result = GuiTextLayout.create(source, this.size, this.bold);
/*  52 */     if (this.layouts.size() >= 512) this.layouts.clear(); 
/*  53 */     this.layouts.put(source, result);
/*  54 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStringWidth(String text) {
/*  59 */     return (layout(text)).width;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStringWidth(class_2561 text) {
/*  64 */     return getStringWidth(styled(text));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStringHeight(String text) {
/*  70 */     return (layout(text)).height * 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStringHeight(class_2561 text) {
/*  75 */     return getStringHeight(styled(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(class_4587 matrix, String text, double x, double y, int color) {
/*  80 */     if (color >>> 24 == 0)
/*  81 */       return;  for (GuiTextLayout.Run run : (layout(text)).runs) {
/*  82 */       MsdfRenderer.renderText(atlas(run.bold()), run.text(), this.size, GuiTextLayout.color(color, run.rgb()), matrix
/*  83 */           .method_23760().method_23761(), (float)x + run.x(), msdfY(y) + run.y(), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawText(class_4587 matrix, class_2561 text, double x, double y) {
/*  89 */     drawString(matrix, styled(text), x, y, ColorUtil.getText());
/*     */   }
/*     */ 
/*     */   
/*     */   public void findStyle(StringBuilder builder, class_2561 text) {
/*  94 */     if (text == null)
/*  95 */       return;  text.method_27658((style, content) -> { builder.append("§r"); if (style.method_10973() != null) builder.append('⏏').append(style.method_10973().method_27716()).append('⏏');  if (style.method_10984()) builder.append("§l");  builder.append(content); return Optional.empty(); }class_2583.field_24360);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String styled(class_2561 text) {
/* 105 */     if (text == null) return ""; 
/* 106 */     StringBuilder builder = new StringBuilder();
/* 107 */     findStyle(builder, text);
/* 108 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(class_4587 matrix, String text, double x, double y, int color) {
/* 113 */     drawString(matrix, text, x - (getStringWidth(text) / 2.0F), y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStringWithScroll(class_4587 matrix, String text, double x, double y, float width, int color) {
/* 118 */     String source = (text == null) ? "" : text;
/* 119 */     String separated = source + "  |  ";
/* 120 */     float textWidth = getStringWidth(separated);
/* 121 */     if (textWidth - width < 10.0F) { drawString(matrix, source, x, y, color); }
/* 122 */     else { drawString(matrix, separated + separated, x - MathUtil.textScrolling(textWidth), y, color); }
/*     */   
/*     */   }
/*     */   
/*     */   public void drawGradientString(class_4587 matrix, String text, double x, double y, int start, int end) {
/* 127 */     gradient(matrix, text, x, y, start, end, false, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawAnimatedGradientString(class_4587 matrix, String text, double x, double y, int start, int end) {
/* 132 */     drawAnimatedGradientString(matrix, text, x, y, start, end, Math.PI, 5.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawAnimatedGradientString(class_4587 matrix, String text, double x, double y, int start, int end, double waveLength, double speed) {
/* 138 */     gradient(matrix, text, x, y, start, end, true, waveLength, speed);
/*     */   }
/*     */ 
/*     */   
/*     */   private void gradient(class_4587 matrix, String text, double x, double y, int start, int end, boolean animated, double waveLength, double speed) {
/* 143 */     GuiTextLayout layout = layout(text);
/* 144 */     double time = System.currentTimeMillis() / 1000.0D;
/* 145 */     for (int i = 0; i < layout.glyphs.size(); i++) {
/* 146 */       GuiTextLayout.Glyph glyph = layout.glyphs.get(i);
/* 147 */       float t = i / Math.max(1, layout.glyphs.size() - 1);
/* 148 */       if (animated) t = (float)((Math.sin(t * waveLength - time * speed) + 1.0D) * 0.5D); 
/* 149 */       int color = GuiTextLayout.color(GuiTextLayout.mix(start, end, t), glyph.rgb());
/* 150 */       if (color >>> 24 != 0)
/* 151 */         MsdfRenderer.renderText(atlas(glyph.bold()), glyph.text(), this.size, color, matrix.method_23760().method_23761(), (float)x + glyph
/* 152 */             .x(), msdfY(y) + glyph.y(), 0.0F); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\font\MsdfGuiFontRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */