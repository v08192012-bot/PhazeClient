/*     */ package vorga.phazeclient.api.system.font.msdf;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_2960;
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
/*     */ public final class Builder
/*     */ {
/*     */   private class_2960 dataIdentifier;
/*     */   private class_2960 atlasIdentifier;
/*     */   
/*     */   public Builder data(String fileName) {
/* 158 */     this.dataIdentifier = class_2960.method_60655("minecraft", "msdf/" + fileName + ".json");
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public Builder atlas(String fileName) {
/* 163 */     this.atlasIdentifier = class_2960.method_60655("minecraft", "msdf/" + fileName + ".png");
/* 164 */     return this;
/*     */   }
/*     */   
/*     */   public MsdfFont build() {
/* 168 */     FontData data = ResourceProvider.<FontData>fromJsonToInstance(this.dataIdentifier, FontData.class);
/* 169 */     class_1044 texture = MsdfFont.MC.method_1531().method_4619(this.atlasIdentifier);
/*     */     
/* 171 */     RenderSystem.recordRenderCall(() -> texture.method_4527(true, false));
/*     */     
/* 173 */     float atlasWidth = data.atlas().width();
/* 174 */     float atlasHeight = data.atlas().height();
/* 175 */     Int2ObjectOpenHashMap int2ObjectOpenHashMap1 = new Int2ObjectOpenHashMap();
/* 176 */     for (FontData.GlyphData glyphData : data.glyphs()) {
/* 177 */       int2ObjectOpenHashMap1.put(glyphData.unicode(), new MsdfGlyph(glyphData, atlasWidth, atlasHeight));
/*     */     }
/*     */     
/* 180 */     Int2ObjectOpenHashMap int2ObjectOpenHashMap2 = new Int2ObjectOpenHashMap();
/* 181 */     for (FontData.KerningData kerning : data.kernings()) {
/* 182 */       Int2FloatOpenHashMap int2FloatOpenHashMap; Int2FloatMap map = (Int2FloatMap)int2ObjectOpenHashMap2.get(kerning.leftChar());
/* 183 */       if (map == null) {
/* 184 */         int2FloatOpenHashMap = new Int2FloatOpenHashMap();
/* 185 */         int2ObjectOpenHashMap2.put(kerning.leftChar(), int2FloatOpenHashMap);
/*     */       } 
/* 187 */       int2FloatOpenHashMap.put(kerning.rightChar(), kerning.advance());
/*     */     } 
/*     */     
/* 190 */     return new MsdfFont(texture, data.atlas(), (Int2ObjectMap<MsdfGlyph>)int2ObjectOpenHashMap1, (Int2ObjectMap<Int2FloatMap>)int2ObjectOpenHashMap2);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\MsdfFont$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */