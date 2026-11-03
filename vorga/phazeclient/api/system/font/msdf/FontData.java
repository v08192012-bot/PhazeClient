/*     */ package vorga.phazeclient.api.system.font.msdf;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class FontData
/*     */ {
/*     */   private AtlasData atlas;
/*     */   private MetricsData metrics;
/*     */   private List<GlyphData> glyphs;
/*     */   @SerializedName("kerning")
/*     */   private List<KerningData> kernings;
/*     */   
/*     */   public AtlasData atlas() {
/*  15 */     return this.atlas;
/*     */   }
/*     */   
/*     */   public MetricsData metrics() {
/*  19 */     return this.metrics;
/*     */   }
/*     */   
/*     */   public List<GlyphData> glyphs() {
/*  23 */     return this.glyphs;
/*     */   }
/*     */   
/*     */   public List<KerningData> kernings() {
/*  27 */     return this.kernings;
/*     */   }
/*     */   
/*     */   public static final class AtlasData {
/*     */     @SerializedName("distanceRange")
/*     */     private float range;
/*     */     private float width;
/*     */     private float height;
/*     */     
/*     */     public float range() {
/*  37 */       return this.range;
/*     */     }
/*     */     
/*     */     public float width() {
/*  41 */       return this.width;
/*     */     }
/*     */     
/*     */     public float height() {
/*  45 */       return this.height;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class MetricsData {
/*     */     private float lineHeight;
/*     */     private float ascender;
/*     */     private float descender;
/*     */     
/*     */     public float lineHeight() {
/*  55 */       return this.lineHeight;
/*     */     }
/*     */     
/*     */     public float ascender() {
/*  59 */       return this.ascender;
/*     */     }
/*     */     
/*     */     public float descender() {
/*  63 */       return this.descender;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class GlyphData {
/*     */     private int unicode;
/*     */     private float advance;
/*     */     private FontData.BoundsData planeBounds;
/*     */     private FontData.BoundsData atlasBounds;
/*     */     
/*     */     public int unicode() {
/*  74 */       return this.unicode;
/*     */     }
/*     */     
/*     */     public float advance() {
/*  78 */       return this.advance;
/*     */     }
/*     */     
/*     */     public FontData.BoundsData planeBounds() {
/*  82 */       return this.planeBounds;
/*     */     }
/*     */     
/*     */     public FontData.BoundsData atlasBounds() {
/*  86 */       return this.atlasBounds;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class BoundsData {
/*     */     private float left;
/*     */     private float top;
/*     */     private float right;
/*     */     private float bottom;
/*     */     
/*     */     public float left() {
/*  97 */       return this.left;
/*     */     }
/*     */     
/*     */     public float top() {
/* 101 */       return this.top;
/*     */     }
/*     */     
/*     */     public float right() {
/* 105 */       return this.right;
/*     */     }
/*     */     
/*     */     public float bottom() {
/* 109 */       return this.bottom;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class KerningData {
/*     */     @SerializedName("unicode1")
/*     */     private int leftChar;
/*     */     @SerializedName("unicode2")
/*     */     private int rightChar;
/*     */     private float advance;
/*     */     
/*     */     public int leftChar() {
/* 121 */       return this.leftChar;
/*     */     }
/*     */     
/*     */     public int rightChar() {
/* 125 */       return this.rightChar;
/*     */     }
/*     */     
/*     */     public float advance() {
/* 129 */       return this.advance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\FontData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */