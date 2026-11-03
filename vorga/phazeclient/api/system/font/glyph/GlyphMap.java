/*     */ package vorga.phazeclient.api.system.font.glyph;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_9848;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ 
/*     */ public class GlyphMap {
/*  30 */   private final Char2ObjectArrayMap<Glyph> glyphs = new Char2ObjectArrayMap(); private final char fromIncl; private final char toExcl;
/*     */   private final Font font;
/*     */   public final class_2960 bindToTexture;
/*     */   private final int pixelPadding;
/*     */   public int width;
/*     */   public int height;
/*     */   private boolean generated = false;
/*     */   
/*     */   public GlyphMap(char from, char to, Font font, class_2960 identifier, int padding) {
/*  39 */     this.fromIncl = from;
/*  40 */     this.toExcl = to;
/*  41 */     this.font = font;
/*  42 */     this.bindToTexture = identifier;
/*  43 */     this.pixelPadding = padding;
/*     */   }
/*     */   
/*     */   public Glyph getGlyph(char c) {
/*  47 */     if (!this.generated) generate(); 
/*  48 */     return (Glyph)this.glyphs.get(c);
/*     */   }
/*     */   
/*     */   public boolean contains(char c) {
/*  52 */     return (c >= this.fromIncl && c < this.toExcl);
/*     */   }
/*     */   
/*     */   private Font getFontForGlyph(char c) {
/*  56 */     if (this.font.canDisplay(c)) return this.font; 
/*  57 */     return Arrays.<Font>stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()).filter(f -> f.canDisplay(c))
/*  58 */       .map(f -> new Font(f.getFontName(), 1, this.font.getSize()))
/*  59 */       .findFirst().orElse(new Font("SansSerif", 1, 0));
/*     */   }
/*     */   
/*     */   public void generate() {
/*  63 */     if (this.generated)
/*     */       return; 
/*  65 */     int range = this.toExcl - this.fromIncl - 1;
/*  66 */     int charsVert = (int)(Math.ceil(Math.sqrt(range)) * 1.5D);
/*  67 */     this.glyphs.clear();
/*     */     
/*  69 */     int generatedChars = 0;
/*  70 */     int charNX = 0;
/*  71 */     int maxX = 0, maxY = 0;
/*  72 */     int currentX = 0, currentY = 0;
/*  73 */     int currentRowMaxY = 0;
/*     */     
/*  75 */     List<Glyph> glyphs1 = new ArrayList<>();
/*  76 */     AffineTransform affineTransform = new AffineTransform();
/*  77 */     FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, false);
/*     */     
/*  79 */     while (generatedChars <= range) {
/*  80 */       char currentChar = (char)(this.fromIncl + generatedChars);
/*  81 */       Font font = getFontForGlyph(currentChar);
/*     */       
/*  83 */       Rectangle2D stringBounds = font.getStringBounds(String.valueOf(currentChar), fontRenderContext);
/*     */       
/*  85 */       int width = (int)Math.ceil(stringBounds.getWidth());
/*  86 */       int height = (int)Math.ceil(stringBounds.getHeight());
/*  87 */       generatedChars++;
/*     */       
/*  89 */       maxX = Math.max(maxX, currentX + width);
/*  90 */       maxY = Math.max(maxY, currentY + height);
/*     */       
/*  92 */       if (charNX >= charsVert) {
/*  93 */         currentX = 0;
/*  94 */         currentY += currentRowMaxY + this.pixelPadding;
/*  95 */         charNX = 0;
/*  96 */         currentRowMaxY = 0;
/*     */       } 
/*     */       
/*  99 */       currentRowMaxY = Math.max(currentRowMaxY, height);
/* 100 */       glyphs1.add(new Glyph(currentX, currentY, width, height, currentChar, this));
/*     */       
/* 102 */       currentX += width + this.pixelPadding;
/* 103 */       charNX++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     BufferedImage bufferedImage = new BufferedImage(Math.max(maxX + this.pixelPadding, 1), Math.max(maxY + this.pixelPadding, 1), 2);
/*     */ 
/*     */ 
/*     */     
/* 112 */     this.width = bufferedImage.getWidth();
/* 113 */     this.height = bufferedImage.getHeight();
/*     */     
/* 115 */     Graphics2D g2d = bufferedImage.createGraphics();
/* 116 */     g2d.setColor(new Color(255, 255, 255, 0));
/* 117 */     g2d.fillRect(0, 0, this.width, this.height);
/* 118 */     g2d.setColor(Color.WHITE);
/*     */     
/* 120 */     g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/* 121 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/* 122 */     g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*     */     
/* 124 */     for (Glyph glyph : glyphs1) {
/* 125 */       g2d.setFont(getFontForGlyph(glyph.value()));
/* 126 */       FontMetrics fontMetrics = g2d.getFontMetrics();
/* 127 */       g2d.drawString(String.valueOf(glyph.value()), glyph.u(), glyph.v() + fontMetrics.getAscent());
/* 128 */       this.glyphs.put(glyph.value(), glyph);
/*     */     } 
/*     */     
/* 131 */     registerBufferedImageTexture(this.bindToTexture, bufferedImage);
/* 132 */     this.generated = true;
/*     */   }
/*     */   
/*     */   public static void registerBufferedImageTexture(class_2960 textureIdentifier, BufferedImage inputImage) {
/*     */     try {
/* 137 */       int imageWidth = inputImage.getWidth();
/* 138 */       int imageHeight = inputImage.getHeight();
/*     */       
/* 140 */       class_1011 nativeImage = new class_1011(class_1011.class_1012.field_4997, imageWidth, imageHeight, false);
/*     */ 
/*     */       
/* 143 */       IntBuffer buffer = MemoryUtil.memIntBuffer(nativeImage.field_4988, nativeImage.method_4307() * nativeImage.method_4323());
/*     */       
/* 145 */       WritableRaster raster = inputImage.getRaster();
/* 146 */       ColorModel colorModel = inputImage.getColorModel();
/*     */       
/* 148 */       Object data = createDataArrayBasedOnRaster(raster);
/*     */       
/* 150 */       for (int y = 0; y < imageHeight; y++) {
/* 151 */         for (int x = 0; x < imageWidth; x++) {
/* 152 */           raster.getDataElements(x, y, data);
/* 153 */           int alpha = colorModel.getAlpha(data);
/* 154 */           int red = colorModel.getRed(data);
/* 155 */           int green = colorModel.getGreen(data);
/* 156 */           int blue = colorModel.getBlue(data);
/* 157 */           buffer.put(class_9848.method_61324(alpha, blue, green, red));
/*     */         } 
/*     */       } 
/*     */       
/* 161 */       class_1043 texture = new class_1043(nativeImage);
/* 162 */       texture.method_4524();
/*     */       
/* 164 */       if (RenderSystem.isOnRenderThread()) {
/* 165 */         class_310.method_1551().method_1531().method_4616(textureIdentifier, (class_1044)texture);
/*     */       } else {
/* 167 */         RenderSystem.recordRenderCall(() -> class_310.method_1551().method_1531().method_4616(textureIdentifier, (class_1044)texture));
/*     */       } 
/*     */     } catch (Throwable $ex) {
/*     */       throw $ex;
/*     */     }  } private static Object createDataArrayBasedOnRaster(WritableRaster raster) {
/* 172 */     switch (raster.getDataBuffer().getDataType()) { case 0: 
/*     */       case 1: 
/*     */       case 3: 
/*     */       case 4:
/*     */       
/*     */       case 5:
/* 178 */        }  throw new IllegalArgumentException("Unsupported data buffer type: " + raster.getDataBuffer().getDataType());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\glyph\GlyphMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */