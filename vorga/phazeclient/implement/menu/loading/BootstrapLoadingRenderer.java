/*     */ package vorga.phazeclient.implement.menu.loading;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.opengl.GL32C;
/*     */ import org.lwjgl.opengl.GLCapabilities;
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
/*     */ public final class BootstrapLoadingRenderer
/*     */ {
/*     */   private static final String SHADERS = "/assets/phaze/shaders/loading/";
/*     */   public static final int ATLAS_TEXTURE_INDEX = 7;
/*     */   private static BootstrapLoadingRenderer instance;
/*  27 */   private final Thread thread = Thread.currentThread();
/*  28 */   private final GLCapabilities context = GL.getCapabilities();
/*     */   
/*     */   private Program shapes;
/*     */   
/*     */   private Program text;
/*     */   
/*     */   private LoadingFont bold;
/*     */   private LoadingFont medium;
/*     */   private int vao;
/*     */   private int vbo;
/*     */   
/*     */   public static void render(int framebufferWidth, int framebufferHeight, float progress, float alpha) {
/*  40 */     if (!Float.isFinite(progress) || !Float.isFinite(alpha)) {
/*  41 */       throw new IllegalArgumentException("Bootstrap loading progress and alpha must be finite");
/*     */     }
/*  43 */     if (framebufferWidth <= 0 || framebufferHeight <= 0)
/*  44 */       return;  alpha = clamp(alpha, 0.0F, 1.0F);
/*  45 */     if (alpha == 0.0F)
/*  46 */       return;  if (!(GL.getCapabilities()).OpenGL32) {
/*  47 */       throw new IllegalStateException("Bootstrap loading renderer requires an OpenGL 3.2 context");
/*     */     }
/*  49 */     if (instance != null) instance.checkContext(); 
/*  50 */     LoadingGlState state = new LoadingGlState(); 
/*  51 */     try { state.prepare(framebufferWidth, framebufferHeight);
/*  52 */       if (instance == null) {
/*  53 */         BootstrapLoadingRenderer renderer = new BootstrapLoadingRenderer();
/*     */         try {
/*  55 */           renderer.initialize();
/*  56 */           instance = renderer;
/*  57 */         } catch (IOException|RuntimeException|Error exception) {
/*  58 */           renderer.release();
/*  59 */           throw new IllegalStateException("Failed to initialize independent Phaze loading renderer", exception);
/*     */         } 
/*     */       } 
/*  62 */       instance.draw(framebufferWidth, framebufferHeight, clamp(progress, 0.0F, 1.0F), alpha);
/*  63 */       state.close(); }
/*     */     catch (Throwable throwable) { try { state.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/*  68 */      } public static void close() { if (instance == null)
/*  69 */       return;  instance.checkContext();
/*  70 */     instance.release();
/*  71 */     instance = null; }
/*     */ 
/*     */   
/*     */   private void checkContext() {
/*  75 */     if (this.thread != Thread.currentThread() || this.context != GL.getCapabilities()) {
/*  76 */       throw new IllegalStateException("Bootstrap loading renderer must use its owning render thread and GL context");
/*     */     }
/*     */   }
/*     */   
/*     */   private void initialize() throws IOException {
/*  81 */     this.shapes = Program.create("rounded.fsh", new String[] { "Color", "Radius", "BorderColor", "BorderWidth" });
/*  82 */     this.text = Program.create("msdf.fsh", new String[] { "Atlas", "UvRect", "DistanceRange", "AtlasSize", "Alpha" });
/*  83 */     this.vao = GL32C.glGenVertexArrays();
/*  84 */     this.vbo = GL32C.glGenBuffers();
/*  85 */     if (this.vao == 0 || this.vbo == 0) throw new IOException("Cannot allocate loading VAO/VBO"); 
/*  86 */     GL32C.glBindVertexArray(this.vao);
/*  87 */     GL32C.glBindBuffer(34962, this.vbo);
/*  88 */     GL32C.glBufferData(34962, new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F }, 35044);
/*     */ 
/*     */ 
/*     */     
/*  92 */     GL32C.glVertexAttribPointer(0, 2, 5126, false, 16, 0L);
/*  93 */     GL32C.glVertexAttribPointer(1, 2, 5126, false, 16, 8L);
/*  94 */     GL32C.glEnableVertexAttribArray(0);
/*  95 */     GL32C.glEnableVertexAttribArray(1);
/*  96 */     this.bold = LoadingFont.load("bold", "PHAZE");
/*  97 */     this.medium = LoadingFont.load("medium", "CLIENT");
/*     */   }
/*     */   
/*     */   private void draw(int width, int height, float progress, float alpha) {
/* 101 */     GL32C.glBindVertexArray(this.vao);
/* 102 */     this.shapes.use(width, height);
/* 103 */     rectangle(0.0F, 0.0F, width, height, 0.0F, 0.30980393F, 0.6156863F, 0.4627451F, alpha, 0.0F, 0.0F);
/*     */ 
/*     */     
/* 106 */     float titleSize = Math.min(clamp(height * 0.16F, 32.0F, 180.0F), width * 0.7F / this.bold.width);
/* 107 */     float subtitleSize = titleSize * 0.36F;
/* 108 */     float gap = Math.max(2.0F, titleSize * 0.035F);
/* 109 */     float logoHeight = titleSize + gap + subtitleSize;
/* 110 */     float logoTop = height * 0.46F - logoHeight / 2.0F;
/* 111 */     this.text.use(width, height);
/* 112 */     GL32C.glUniform1i(this.text.uniform("Atlas"), 7);
/* 113 */     GL32C.glUniform1f(this.text.uniform("Alpha"), alpha);
/* 114 */     word(this.bold, titleSize, width, logoTop, titleSize);
/* 115 */     word(this.medium, subtitleSize, width, logoTop + titleSize + gap, subtitleSize);
/*     */     
/* 117 */     float barWidth = Math.min(width, Math.max(32.0F, Math.min((width - 48), Math.max(220.0F, width * 0.52F))));
/* 118 */     float barHeight = Math.min(height, clamp(height * 0.009F, 6.0F, 12.0F) + 4.0F);
/* 119 */     float barX = (width - barWidth) / 2.0F;
/* 120 */     float barY = Math.min(height - barHeight, height * 0.82F);
/* 121 */     this.shapes.use(width, height);
/* 122 */     rectangle(barX, barY, barWidth, barHeight, barHeight / 2.0F, 1.0F, 1.0F, 1.0F, alpha * 0.2F, alpha * 0.75F, 
/* 123 */         Math.min(1.0F, barHeight / 4.0F));
/* 124 */     if (progress > 0.0F) {
/* 125 */       float fillWidth = barWidth * progress;
/* 126 */       rectangle(barX, barY, fillWidth, barHeight, Math.min(fillWidth, barHeight) / 2.0F, 1.0F, 1.0F, 1.0F, alpha, 0.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void word(LoadingFont font, float size, int screenWidth, float boxTop, float boxHeight) {
/* 132 */     float x = (screenWidth - font.width * size) / 2.0F - font.left * size;
/* 133 */     float baseline = boxTop + (boxHeight - font.height * size) / 2.0F - font.top * size;
/*     */ 
/*     */     
/* 136 */     RenderSystem.activeTexture(33991);
/* 137 */     RenderSystem.bindTexture(font.texture());
/* 138 */     GL32C.glUniform1f(this.text.uniform("DistanceRange"), font.range);
/* 139 */     GL32C.glUniform2f(this.text.uniform("AtlasSize"), font.atlasWidth, font.atlasHeight);
/* 140 */     for (LoadingFont.Glyph glyph : font.glyphs) {
/* 141 */       GL32C.glUniform4f(this.text.rect, x + glyph.x() * size, baseline + glyph.y() * size, glyph
/* 142 */           .width() * size, glyph.height() * size);
/* 143 */       GL32C.glUniform4f(this.text.uniform("UvRect"), glyph.u(), glyph.v(), glyph.uw(), glyph.vh());
/* 144 */       GL32C.glDrawArrays(4, 0, 6);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void rectangle(float x, float y, float width, float height, float radius, float red, float green, float blue, float alpha, float borderAlpha, float borderWidth) {
/* 150 */     GL32C.glUniform4f(this.shapes.rect, x, y, width, height);
/* 151 */     GL32C.glUniform1f(this.shapes.uniform("Radius"), radius);
/* 152 */     GL32C.glUniform4f(this.shapes.uniform("Color"), red, green, blue, alpha);
/* 153 */     GL32C.glUniform4f(this.shapes.uniform("BorderColor"), 1.0F, 1.0F, 1.0F, borderAlpha);
/* 154 */     GL32C.glUniform1f(this.shapes.uniform("BorderWidth"), borderWidth);
/* 155 */     GL32C.glDrawArrays(4, 0, 6);
/*     */   }
/*     */   
/*     */   private void release() {
/* 159 */     if (this.bold != null) this.bold.close(); 
/* 160 */     if (this.medium != null) this.medium.close(); 
/* 161 */     if (this.shapes != null) GL32C.glDeleteProgram(this.shapes.id); 
/* 162 */     if (this.text != null) GL32C.glDeleteProgram(this.text.id); 
/* 163 */     if (this.vbo != 0) GL32C.glDeleteBuffers(this.vbo); 
/* 164 */     if (this.vao != 0) GL32C.glDeleteVertexArrays(this.vao); 
/*     */   }
/*     */   
/*     */   static InputStream resource(String path) throws IOException {
/* 168 */     InputStream stream = BootstrapLoadingRenderer.class.getResourceAsStream(path);
/* 169 */     if (stream == null) throw new IOException("Missing bundled loading resource: " + path); 
/* 170 */     return stream;
/*     */   }
/*     */   
/*     */   private static String source(String file) throws IOException {
/* 174 */     InputStream stream = resource("/assets/phaze/shaders/loading/" + file); 
/* 175 */     try { String str = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
/* 176 */       if (stream != null) stream.close();  return str; } catch (Throwable throwable) { if (stream != null)
/*     */         try { stream.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 180 */      } private static float clamp(float value, float min, float max) { return Math.max(min, Math.min(max, value)); }
/*     */ 
/*     */   
/*     */   private static final class Program {
/*     */     final int id;
/*     */     final int rect;
/*     */     final int viewport;
/* 187 */     private final Map<String, Integer> uniforms = new HashMap<>();
/*     */     
/*     */     private Program(int id, String[] names) throws IOException {
/* 190 */       this.id = id;
/* 191 */       this.rect = location("Rect");
/* 192 */       this.viewport = location("Viewport");
/* 193 */       for (String name : names) this.uniforms.put(name, Integer.valueOf(location(name))); 
/*     */     }
/*     */     
/*     */     int uniform(String name) {
/* 197 */       return ((Integer)this.uniforms.get(name)).intValue();
/*     */     }
/*     */     
/*     */     private int location(String name) throws IOException {
/* 201 */       int location = GL32C.glGetUniformLocation(this.id, name);
/* 202 */       if (location < 0) throw new IOException("Missing loading shader uniform '" + name + "' in program " + this.id); 
/* 203 */       return location;
/*     */     }
/*     */     
/*     */     void use(int width, int height) {
/* 207 */       GL32C.glUseProgram(this.id);
/* 208 */       GL32C.glUniform2f(this.viewport, width, height);
/*     */     }
/*     */     
/*     */     static Program create(String fragment, String... uniforms) throws IOException {
/* 212 */       int vertex = 0, pixel = 0, program = 0;
/* 213 */       boolean linked = false;
/*     */       try {
/* 215 */         vertex = compile(35633, "quad.vsh");
/* 216 */         pixel = compile(35632, fragment);
/* 217 */         program = GL32C.glCreateProgram();
/* 218 */         if (program == 0) throw new IOException("Cannot allocate loading program " + fragment); 
/* 219 */         GL32C.glAttachShader(program, vertex);
/* 220 */         GL32C.glAttachShader(program, pixel);
/* 221 */         GL32C.glBindAttribLocation(program, 0, "Position");
/* 222 */         GL32C.glBindAttribLocation(program, 1, "UV");
/* 223 */         GL32C.glBindFragDataLocation(program, 0, "FragColor");
/* 224 */         GL32C.glLinkProgram(program);
/* 225 */         if (GL32C.glGetProgrami(program, 35714) == 0) {
/* 226 */           throw new IOException("Loading shader link failed (" + fragment + "): " + GL32C.glGetProgramInfoLog(program));
/*     */         }
/* 228 */         Program result = new Program(program, uniforms);
/* 229 */         linked = true;
/* 230 */         return result;
/*     */       } finally {
/* 232 */         if (program != 0 && vertex != 0) GL32C.glDetachShader(program, vertex); 
/* 233 */         if (program != 0 && pixel != 0) GL32C.glDetachShader(program, pixel); 
/* 234 */         if (vertex != 0) GL32C.glDeleteShader(vertex); 
/* 235 */         if (pixel != 0) GL32C.glDeleteShader(pixel); 
/* 236 */         if (!linked && program != 0) GL32C.glDeleteProgram(program); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private static int compile(int type, String file) throws IOException {
/* 241 */       String source = BootstrapLoadingRenderer.source(file);
/* 242 */       int shader = GL32C.glCreateShader(type);
/* 243 */       if (shader == 0) throw new IOException("Cannot allocate loading shader " + file); 
/* 244 */       boolean compiled = false;
/*     */       try {
/* 246 */         GL32C.glShaderSource(shader, source);
/* 247 */         GL32C.glCompileShader(shader);
/* 248 */         if (GL32C.glGetShaderi(shader, 35713) == 0) {
/* 249 */           throw new IOException("Loading shader compilation failed (/assets/phaze/shaders/loading/" + file + "): " + GL32C.glGetShaderInfoLog(shader));
/*     */         }
/* 251 */         compiled = true;
/* 252 */         return shader;
/*     */       } finally {
/* 254 */         if (!compiled) GL32C.glDeleteShader(shader); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\loading\BootstrapLoadingRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */