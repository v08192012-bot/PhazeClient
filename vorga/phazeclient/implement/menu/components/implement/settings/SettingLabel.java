/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFont;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SettingLabel
/*     */ {
/*  21 */   private final SettingLabelState state = new SettingLabelState();
/*     */   private String source;
/*  23 */   private String line = "";
/*     */   private float animatedTextInset;
/*     */   
/*     */   public void setAnimatedTextInset(float inset) {
/*  27 */     this.animatedTextInset = inset;
/*     */   }
/*     */   
/*     */   public String singleLine(String text) {
/*  31 */     if (text == null) text = ""; 
/*  32 */     if (!text.equals(this.source)) {
/*  33 */       this.source = text;
/*  34 */       this
/*  35 */         .line = text.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').replace(' ', ' ').replace(' ', ' ');
/*     */     } 
/*  37 */     return this.line;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(class_4587 matrices, FontRenderer font, String text, float textX, float textY, float right, float top, float height, int color) {
/*  42 */     text = singleLine(text);
/*  43 */     if (font instanceof MsdfGuiFontRenderer) { MsdfGuiFontRenderer gui = (MsdfGuiFontRenderer)font;
/*  44 */       render(matrices, gui.msdfFont(), text, gui.msdfSize(), font.getStringWidth(text), textX, gui
/*  45 */           .msdfY(textY), right, top, height, color); }
/*     */     else
/*  47 */     { float size = font.getFont().getSize2D() / 2.0F;
/*  48 */       render(matrices, MsdfFonts.bold(), text, size, renderedWidth(text, size), textX, textY, right, top, height, color); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderMsdf(class_4587 matrices, String text, float size, float textX, float textY, float right, float top, float height, int color) {
/*  55 */     text = singleLine(text);
/*     */     
/*  57 */     render(matrices, MsdfFonts.bold(), text, size, renderedWidth(text, size), textX, textY, right, top, height, color);
/*     */   }
/*     */   
/*     */   private static float renderedWidth(String text, float size) {
/*  61 */     return MsdfFonts.bold().getWidth(text, size) + Math.max(0, text.length() - 1) * 0.025F * size;
/*     */   }
/*     */ 
/*     */   
/*     */   private void render(class_4587 matrices, MsdfFont font, String text, float size, float textWidth, float textX, float textY, float right, float top, float height, int color) {
/*  66 */     long now = System.nanoTime();
/*  67 */     this.state.layout(text, textWidth, right - textX, height, now, this.animatedTextInset);
/*     */     
/*  69 */     float width = this.state.getWidth();
/*  70 */     if (width <= 0.0F || height <= 0.0F)
/*  71 */       return;  if (!this.state.isOverflowing()) {
/*  72 */       MsdfRenderer.renderText(font, text, size, color, matrices.method_23760().method_23761(), textX, textY, 0.0F);
/*     */       return;
/*     */     } 
/*  75 */     float offset = this.state.offset(now);
/*  76 */     float fade = this.state.fadeWidth();
/*  77 */     float leftStrength = Math.max(0.0F, Math.min(1.0F, offset / fade));
/*  78 */     float rightStrength = Math.max(0.0F, Math.min(1.0F, (this.state.getOverflow() - offset) / fade));
/*  79 */     ScissorManager scissor = Main.getInstance().getScissorManager();
/*     */ 
/*     */     
/*  82 */     if (MsdfRenderer.isUsingFallback()) flushFallback(); 
/*  83 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  84 */     scissor.push(matrix, textX, top, width, height);
/*     */     try {
/*  86 */       float scale = Math.abs(matrix.m00());
/*  87 */       float left = matrix.m00() * textX + matrix.m30();
/*  88 */       MsdfRenderer.renderTextFaded(font, text, size, color, matrix, textX - offset, textY, 0.0F, left, left + width * scale, fade * scale, leftStrength, rightStrength);
/*     */       
/*  90 */       if (MsdfRenderer.isUsingFallback()) flushFallback(); 
/*     */     } finally {
/*  92 */       scissor.pop();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void flushFallback() {
/*  97 */     class_310.method_1551().method_22940().method_23000().method_22993();
/*     */   }
/*     */   
/*     */   public void updateHover(boolean cardHovered) {
/* 101 */     this.state.updateHover(cardHovered, System.nanoTime());
/*     */   }
/*     */   
/*     */   public SettingLabelState state() {
/* 105 */     return this.state;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\SettingLabel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */