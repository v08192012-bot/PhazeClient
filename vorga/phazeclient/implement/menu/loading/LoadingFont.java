/*     */ package vorga.phazeclient.implement.menu.loading;
/*     */ final class LoadingFont { final Glyph[] glyphs;
/*     */   final float left;
/*     */   final float top;
/*     */   final float width;
/*     */   final float height;
/*     */   final float range;
/*     */   final int atlasWidth;
/*     */   final int atlasHeight;
/*     */   private int texture;
/*     */   
/*     */   static final class Glyph extends Record {
/*     */     private final float x;
/*     */     private final float y;
/*     */     private final float width;
/*     */     private final float height;
/*     */     private final float u;
/*     */     private final float v;
/*     */     private final float uw;
/*     */     private final float vh;
/*     */     
/*  22 */     Glyph(float x, float y, float width, float height, float u, float v, float uw, float vh) { this.x = x; this.y = y; this.width = width; this.height = height; this.u = u; this.v = v; this.uw = uw; this.vh = vh; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #22	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #22	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #22	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/loading/LoadingFont$Glyph;
/*  22 */       //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public float width() { return this.width; } public float height() { return this.height; } public float u() { return this.u; } public float v() { return this.v; } public float uw() { return this.uw; } public float vh() { return this.vh; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LoadingFont(JsonObject data, String word) {
/*  35 */     JsonObject atlas = data.getAsJsonObject("atlas");
/*  36 */     this.atlasWidth = atlas.get("width").getAsInt();
/*  37 */     this.atlasHeight = atlas.get("height").getAsInt();
/*  38 */     this.range = atlas.get("distanceRange").getAsFloat();
/*  39 */     String type = atlas.get("type").getAsString();
/*  40 */     if ((!type.equals("mtsdf") && !type.equals("msdf")) || this.atlasWidth <= 0 || this.atlasHeight <= 0 || 
/*  41 */       !Float.isFinite(this.range) || this.range <= 0.0F || 
/*  42 */       !atlas.get("yOrigin").getAsString().equals("bottom")) {
/*  43 */       throw new IllegalArgumentException("Expected a bottom-origin MSDF/MTSDF atlas with a positive range");
/*     */     }
/*  45 */     Map<Integer, JsonObject> byCodepoint = new HashMap<>();
/*  46 */     for (JsonElement entry : data.getAsJsonArray("glyphs")) {
/*  47 */       JsonObject glyph = entry.getAsJsonObject();
/*  48 */       byCodepoint.put(Integer.valueOf(glyph.get("unicode").getAsInt()), glyph);
/*     */     } 
/*  50 */     Map<Long, Float> kerning = new HashMap<>();
/*  51 */     JsonArray pairs = data.getAsJsonArray("kerning");
/*  52 */     if (pairs != null) {
/*  53 */       for (JsonElement entry : pairs) {
/*  54 */         JsonObject pair = entry.getAsJsonObject();
/*  55 */         kerning.put(Long.valueOf(pairKey(pair.get("unicode1").getAsInt(), pair.get("unicode2").getAsInt())), 
/*  56 */             Float.valueOf(pair.get("advance").getAsFloat()));
/*     */       } 
/*     */     }
/*  59 */     this.glyphs = new Glyph[word.length()];
/*  60 */     float pen = 0.0F, minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY;
/*  61 */     float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
/*  62 */     for (int i = 0; i < word.length(); i++) {
/*  63 */       int codepoint = word.charAt(i);
/*  64 */       JsonObject glyph = byCodepoint.get(Integer.valueOf(codepoint));
/*  65 */       if (glyph == null || !glyph.has("planeBounds") || !glyph.has("atlasBounds")) {
/*  66 */         throw new IllegalArgumentException("Missing drawable glyph '" + word.charAt(i) + "'");
/*     */       }
/*  68 */       if (i > 0) pen += ((Float)kerning.getOrDefault(Long.valueOf(pairKey(word.charAt(i - 1), codepoint)), Float.valueOf(0.0F))).floatValue(); 
/*  69 */       JsonObject plane = glyph.getAsJsonObject("planeBounds");
/*  70 */       JsonObject uv = glyph.getAsJsonObject("atlasBounds");
/*  71 */       float x = pen + value(plane, "left");
/*  72 */       float y = -value(plane, "top");
/*  73 */       float w = value(plane, "right") - value(plane, "left");
/*  74 */       float h = value(plane, "top") - value(plane, "bottom");
/*     */       
/*  76 */       this.glyphs[i] = new Glyph(x, y, w, h, value(uv, "left") / this.atlasWidth, 1.0F - 
/*  77 */           value(uv, "top") / this.atlasHeight, (
/*  78 */           value(uv, "right") - value(uv, "left")) / this.atlasWidth, (
/*  79 */           value(uv, "top") - value(uv, "bottom")) / this.atlasHeight);
/*  80 */       minX = Math.min(minX, x);
/*  81 */       minY = Math.min(minY, y);
/*  82 */       maxX = Math.max(maxX, x + w);
/*  83 */       maxY = Math.max(maxY, y + h);
/*  84 */       pen += value(glyph, "advance");
/*     */     } 
/*  86 */     this.left = minX;
/*  87 */     this.top = minY;
/*  88 */     this.width = maxX - minX;
/*  89 */     this.height = maxY - minY;
/*     */   }
/*     */   static LoadingFont load(String name, String word) throws IOException {
/*     */     LoadingFont font;
/*  93 */     String base = "/assets/minecraft/msdf/" + name;
/*     */     
/*  95 */     try { InputStream stream = BootstrapLoadingRenderer.resource(base + ".json"); 
/*  96 */       try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  97 */         try { font = new LoadingFont(JsonParser.parseReader(reader).getAsJsonObject(), word);
/*  98 */           reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (RuntimeException|IOException exception)
/*  99 */     { throw new IOException("Cannot read bootstrap MSDF metadata " + base + ".json", exception); }
/*     */      
/* 101 */     try { InputStream stream = BootstrapLoadingRenderer.resource(base + ".png"); 
/* 102 */       try { class_1011 image = class_1011.method_4310(class_1011.class_1012.field_4997, stream); 
/* 103 */         try { if (image.method_4307() != font.atlasWidth || image.method_4323() != font.atlasHeight) {
/* 104 */             throw new IOException("Atlas dimensions disagree with JSON: " + base);
/*     */           }
/* 106 */           ByteBuffer pixels = MemoryUtil.memAlloc(Math.multiplyExact(Math.multiplyExact(font.atlasWidth, font.atlasHeight), 4));
/*     */           
/*     */           try {
/* 109 */             for (int y = 0; y < font.atlasHeight; y++) {
/* 110 */               for (int x = 0; x < font.atlasWidth; x++) {
/* 111 */                 int argb = image.method_61940(x, y);
/* 112 */                 pixels.put((byte)(argb >>> 16)).put((byte)(argb >>> 8))
/* 113 */                   .put((byte)argb).put((byte)(argb >>> 24));
/*     */               } 
/*     */             } 
/* 116 */             pixels.flip();
/* 117 */             font.texture = GL32C.glGenTextures();
/* 118 */             if (font.texture == 0) throw new IOException("Cannot allocate bootstrap atlas " + base);
/*     */ 
/*     */ 
/*     */             
/* 122 */             RenderSystem.activeTexture(33991);
/* 123 */             RenderSystem.bindTexture(font.texture);
/* 124 */             GL32C.glTexParameteri(3553, 10241, 9729);
/* 125 */             GL32C.glTexParameteri(3553, 10240, 9729);
/* 126 */             GL32C.glTexParameteri(3553, 10242, 33071);
/* 127 */             GL32C.glTexParameteri(3553, 10243, 33071);
/* 128 */             GL32C.glTexParameteri(3553, 33085, 0);
/* 129 */             GL32C.glBindBuffer(35052, 0);
/* 130 */             GL32C.glPixelStorei(3317, 1);
/* 131 */             GL32C.glPixelStorei(3314, 0);
/* 132 */             GL32C.glPixelStorei(3316, 0);
/* 133 */             GL32C.glPixelStorei(3315, 0);
/*     */             
/* 135 */             GL32C.glTexImage2D(3553, 0, 32856, font.atlasWidth, font.atlasHeight, 0, 6408, 5121, pixels);
/*     */             
/* 137 */             if (GL32C.glGetTexLevelParameteri(3553, 0, 4096) != font.atlasWidth) {
/* 138 */               throw new IOException("OpenGL did not allocate bootstrap atlas " + base);
/*     */             }
/*     */           } finally {
/* 141 */             MemoryUtil.memFree(pixels);
/*     */           } 
/* 143 */           LoadingFont loadingFont = font;
/* 144 */           if (image != null) image.close();  if (stream != null) stream.close();  return loadingFont; } catch (Throwable throwable) { if (image != null) try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (RuntimeException|IOException|Error exception)
/* 145 */     { font.close();
/* 146 */       throw new IOException("Cannot upload bootstrap MSDF atlas " + base + ".png", exception); }
/*     */   
/*     */   }
/*     */   
/*     */   int texture() {
/* 151 */     return this.texture;
/*     */   }
/*     */   
/*     */   void close() {
/* 155 */     if (this.texture != 0) GL32C.glDeleteTextures(this.texture); 
/* 156 */     this.texture = 0;
/*     */   }
/*     */   
/*     */   private static float value(JsonObject object, String name) {
/* 160 */     return object.get(name).getAsFloat();
/*     */   }
/*     */   
/*     */   private static long pairKey(int first, int second) {
/* 164 */     return first << 32L | second & 0xFFFFFFFFL;
/*     */   } }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\loading\LoadingFont.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */