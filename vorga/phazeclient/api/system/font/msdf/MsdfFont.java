/*     */ package vorga.phazeclient.api.system.font.msdf;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4588;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ public final class MsdfFont
/*     */ {
/*  19 */   private static final class_310 MC = class_310.method_1551();
/*     */   
/*     */   private final class_1044 texture;
/*     */   
/*     */   private final FontData.AtlasData atlas;
/*     */   
/*     */   private final Int2ObjectMap<MsdfGlyph> glyphs;
/*     */   private final Int2ObjectMap<Int2FloatMap> kernings;
/*     */   
/*     */   private static final class WidthKey
/*     */     extends Record
/*     */   {
/*     */     private final String text;
/*     */     private final float size;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/font/msdf/MsdfFont$WidthKey;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/font/msdf/MsdfFont$WidthKey;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/font/msdf/MsdfFont$WidthKey;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/font/msdf/MsdfFont$WidthKey;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/font/msdf/MsdfFont$WidthKey;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/api/system/font/msdf/MsdfFont$WidthKey;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     private WidthKey(String text, float size) {
/*  47 */       this.text = text; this.size = size; } public String text() { return this.text; } public float size() { return this.size; }
/*  48 */      } private final Map<WidthKey, Float> widthCache = new HashMap<>(64);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean filterApplied = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MsdfFont(class_1044 texture, FontData.AtlasData atlas, Int2ObjectMap<MsdfGlyph> glyphs, Int2ObjectMap<Int2FloatMap> kernings) {
/*  62 */     this.texture = texture;
/*  63 */     this.atlas = atlas;
/*  64 */     this.glyphs = glyphs;
/*  65 */     this.kernings = kernings;
/*     */   }
/*     */   
/*     */   public FontData.AtlasData getAtlas() {
/*  69 */     return this.atlas;
/*     */   }
/*     */   
/*     */   public int getTextureId() {
/*  73 */     return this.texture.method_4624();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourcesReloaded() {
/*  78 */     this.filterApplied = false;
/*     */   }
/*     */   
/*     */   public void applyGlyphs(Matrix4f matrix, class_4588 consumer, String text, float size, float thickness, float spacing, float x, float y, float z, int color) {
/*  82 */     if (!this.filterApplied) {
/*  83 */       this.texture.method_4527(true, false);
/*  84 */       this.filterApplied = true;
/*     */     } 
/*     */     
/*  87 */     int previousChar = -1;
/*  88 */     for (int i = 0; i < text.length(); i++) {
/*  89 */       char c = text.charAt(i);
/*  90 */       MsdfGlyph glyph = (MsdfGlyph)this.glyphs.get(c);
/*  91 */       if (glyph != null) {
/*     */ 
/*     */ 
/*     */         
/*  95 */         Int2FloatMap kerning = (Int2FloatMap)this.kernings.get(previousChar);
/*  96 */         if (kerning != null) {
/*  97 */           x += kerning.getOrDefault(c, 0.0F) * size;
/*     */         }
/*     */         
/* 100 */         x += glyph.apply(matrix, consumer, size, x, y, z, color) + thickness + spacing;
/* 101 */         previousChar = c;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWidth(String text, float size) {
/* 108 */     if (text == null || text.isEmpty()) {
/* 109 */       return 0.0F;
/*     */     }
/*     */     
/* 112 */     WidthKey key = new WidthKey(text, size);
/* 113 */     Float cached = this.widthCache.get(key);
/* 114 */     if (cached != null) {
/* 115 */       return cached.floatValue();
/*     */     }
/*     */     
/* 118 */     int previousChar = -1;
/* 119 */     float width = 0.0F;
/*     */     
/* 121 */     for (int i = 0; i < text.length(); i++) {
/* 122 */       char c = text.charAt(i);
/* 123 */       MsdfGlyph glyph = (MsdfGlyph)this.glyphs.get(c);
/* 124 */       if (glyph != null) {
/*     */ 
/*     */ 
/*     */         
/* 128 */         Int2FloatMap kerning = (Int2FloatMap)this.kernings.get(previousChar);
/* 129 */         if (kerning != null) {
/* 130 */           width += kerning.getOrDefault(c, 0.0F) * size;
/*     */         }
/*     */         
/* 133 */         width += glyph.getWidth(size);
/* 134 */         previousChar = c;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (this.widthCache.size() >= 1024) {
/* 143 */       this.widthCache.clear();
/*     */     }
/* 145 */     this.widthCache.put(key, Float.valueOf(width));
/* 146 */     return width;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 150 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     private class_2960 dataIdentifier;
/*     */     private class_2960 atlasIdentifier;
/*     */     
/*     */     public Builder data(String fileName) {
/* 158 */       this.dataIdentifier = class_2960.method_60655("minecraft", "msdf/" + fileName + ".json");
/* 159 */       return this;
/*     */     }
/*     */     
/*     */     public Builder atlas(String fileName) {
/* 163 */       this.atlasIdentifier = class_2960.method_60655("minecraft", "msdf/" + fileName + ".png");
/* 164 */       return this;
/*     */     }
/*     */     
/*     */     public MsdfFont build() {
/* 168 */       FontData data = ResourceProvider.<FontData>fromJsonToInstance(this.dataIdentifier, FontData.class);
/* 169 */       class_1044 texture = MsdfFont.MC.method_1531().method_4619(this.atlasIdentifier);
/*     */       
/* 171 */       RenderSystem.recordRenderCall(() -> texture.method_4527(true, false));
/*     */       
/* 173 */       float atlasWidth = data.atlas().width();
/* 174 */       float atlasHeight = data.atlas().height();
/* 175 */       Int2ObjectOpenHashMap int2ObjectOpenHashMap1 = new Int2ObjectOpenHashMap();
/* 176 */       for (FontData.GlyphData glyphData : data.glyphs()) {
/* 177 */         int2ObjectOpenHashMap1.put(glyphData.unicode(), new MsdfGlyph(glyphData, atlasWidth, atlasHeight));
/*     */       }
/*     */       
/* 180 */       Int2ObjectOpenHashMap int2ObjectOpenHashMap2 = new Int2ObjectOpenHashMap();
/* 181 */       for (FontData.KerningData kerning : data.kernings()) {
/* 182 */         Int2FloatOpenHashMap int2FloatOpenHashMap; Int2FloatMap map = (Int2FloatMap)int2ObjectOpenHashMap2.get(kerning.leftChar());
/* 183 */         if (map == null) {
/* 184 */           int2FloatOpenHashMap = new Int2FloatOpenHashMap();
/* 185 */           int2ObjectOpenHashMap2.put(kerning.leftChar(), int2FloatOpenHashMap);
/*     */         } 
/* 187 */         int2FloatOpenHashMap.put(kerning.rightChar(), kerning.advance());
/*     */       } 
/*     */       
/* 190 */       return new MsdfFont(texture, data.atlas(), (Int2ObjectMap<MsdfGlyph>)int2ObjectOpenHashMap1, (Int2ObjectMap<Int2FloatMap>)int2ObjectOpenHashMap2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\MsdfFont.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */