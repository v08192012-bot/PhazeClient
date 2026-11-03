/*    */ package vorga.phazeclient.implement.menu.font;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import vorga.phazeclient.api.system.font.msdf.FontData;
/*    */ 
/*    */ final class GuiFontAtlas
/*    */ {
/* 14 */   private final Map<Integer, Float> advances = new HashMap<>();
/* 15 */   private final Map<Long, Float> kernings = new HashMap<>();
/*    */   final float lineHeight;
/*    */   
/*    */   private GuiFontAtlas(String name) {
/* 19 */     String path = "/assets/minecraft/msdf/" + name + ".json"; 
/* 20 */     try { InputStream stream = GuiFontAtlas.class.getResourceAsStream(path); 
/* 21 */       try { if (stream == null) throw new IllegalStateException("Missing GUI atlas: " + path); 
/* 22 */         FontData data = (FontData)(new Gson()).fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), FontData.class);
/* 23 */         this.lineHeight = data.metrics().lineHeight();
/* 24 */         for (FontData.GlyphData glyph : data.glyphs()) this.advances.put(Integer.valueOf(glyph.unicode()), Float.valueOf(glyph.advance())); 
/* 25 */         if (data.kernings() != null) {
/* 26 */           for (FontData.KerningData kerning : data.kernings()) {
/* 27 */             this.kernings.put(Long.valueOf(pair(kerning.leftChar(), kerning.rightChar())), Float.valueOf(kerning.advance()));
/*    */           }
/*    */         }
/* 30 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException exception)
/* 31 */     { throw new IllegalStateException("Cannot read GUI atlas: " + path, exception); }
/*    */   
/*    */   }
/*    */   
/*    */   static GuiFontAtlas get(boolean bold) {
/* 36 */     return bold ? Bold.INSTANCE : Medium.INSTANCE;
/*    */   }
/*    */   
/*    */   boolean contains(int codepoint) {
/* 40 */     return this.advances.containsKey(Integer.valueOf(codepoint));
/*    */   }
/*    */   
/*    */   int supported(int codepoint) {
/* 44 */     return contains(codepoint) ? codepoint : 63;
/*    */   }
/*    */   
/*    */   float advance(int codepoint) {
/* 48 */     return ((Float)this.advances.getOrDefault(Integer.valueOf(codepoint), Float.valueOf(0.0F))).floatValue();
/*    */   }
/*    */   
/*    */   float kerning(int left, int right) {
/* 52 */     return ((Float)this.kernings.getOrDefault(Long.valueOf(pair(left, right)), Float.valueOf(0.0F))).floatValue();
/*    */   }
/*    */   
/*    */   private static long pair(int left, int right) {
/* 56 */     return left << 32L | right & 0xFFFFFFFFL;
/*    */   }
/*    */   
/*    */   private static final class Bold {
/* 60 */     private static final GuiFontAtlas INSTANCE = new GuiFontAtlas("bold");
/*    */   }
/*    */   
/*    */   private static final class Medium {
/* 64 */     private static final GuiFontAtlas INSTANCE = new GuiFontAtlas("medium");
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\font\GuiFontAtlas.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */