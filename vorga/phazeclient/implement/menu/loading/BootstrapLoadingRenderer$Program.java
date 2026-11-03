/*     */ package vorga.phazeclient.implement.menu.loading;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.lwjgl.opengl.GL32C;
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
/*     */ final class Program
/*     */ {
/*     */   final int id;
/*     */   final int rect;
/*     */   final int viewport;
/* 187 */   private final Map<String, Integer> uniforms = new HashMap<>();
/*     */   
/*     */   private Program(int id, String[] names) throws IOException {
/* 190 */     this.id = id;
/* 191 */     this.rect = location("Rect");
/* 192 */     this.viewport = location("Viewport");
/* 193 */     for (String name : names) this.uniforms.put(name, Integer.valueOf(location(name))); 
/*     */   }
/*     */   
/*     */   int uniform(String name) {
/* 197 */     return ((Integer)this.uniforms.get(name)).intValue();
/*     */   }
/*     */   
/*     */   private int location(String name) throws IOException {
/* 201 */     int location = GL32C.glGetUniformLocation(this.id, name);
/* 202 */     if (location < 0) throw new IOException("Missing loading shader uniform '" + name + "' in program " + this.id); 
/* 203 */     return location;
/*     */   }
/*     */   
/*     */   void use(int width, int height) {
/* 207 */     GL32C.glUseProgram(this.id);
/* 208 */     GL32C.glUniform2f(this.viewport, width, height);
/*     */   }
/*     */   
/*     */   static Program create(String fragment, String... uniforms) throws IOException {
/* 212 */     int vertex = 0, pixel = 0, program = 0;
/* 213 */     boolean linked = false;
/*     */     try {
/* 215 */       vertex = compile(35633, "quad.vsh");
/* 216 */       pixel = compile(35632, fragment);
/* 217 */       program = GL32C.glCreateProgram();
/* 218 */       if (program == 0) throw new IOException("Cannot allocate loading program " + fragment); 
/* 219 */       GL32C.glAttachShader(program, vertex);
/* 220 */       GL32C.glAttachShader(program, pixel);
/* 221 */       GL32C.glBindAttribLocation(program, 0, "Position");
/* 222 */       GL32C.glBindAttribLocation(program, 1, "UV");
/* 223 */       GL32C.glBindFragDataLocation(program, 0, "FragColor");
/* 224 */       GL32C.glLinkProgram(program);
/* 225 */       if (GL32C.glGetProgrami(program, 35714) == 0) {
/* 226 */         throw new IOException("Loading shader link failed (" + fragment + "): " + GL32C.glGetProgramInfoLog(program));
/*     */       }
/* 228 */       Program result = new Program(program, uniforms);
/* 229 */       linked = true;
/* 230 */       return result;
/*     */     } finally {
/* 232 */       if (program != 0 && vertex != 0) GL32C.glDetachShader(program, vertex); 
/* 233 */       if (program != 0 && pixel != 0) GL32C.glDetachShader(program, pixel); 
/* 234 */       if (vertex != 0) GL32C.glDeleteShader(vertex); 
/* 235 */       if (pixel != 0) GL32C.glDeleteShader(pixel); 
/* 236 */       if (!linked && program != 0) GL32C.glDeleteProgram(program); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int compile(int type, String file) throws IOException {
/* 241 */     String source = BootstrapLoadingRenderer.source(file);
/* 242 */     int shader = GL32C.glCreateShader(type);
/* 243 */     if (shader == 0) throw new IOException("Cannot allocate loading shader " + file); 
/* 244 */     boolean compiled = false;
/*     */     try {
/* 246 */       GL32C.glShaderSource(shader, source);
/* 247 */       GL32C.glCompileShader(shader);
/* 248 */       if (GL32C.glGetShaderi(shader, 35713) == 0) {
/* 249 */         throw new IOException("Loading shader compilation failed (/assets/phaze/shaders/loading/" + file + "): " + GL32C.glGetShaderInfoLog(shader));
/*     */       }
/* 251 */       compiled = true;
/* 252 */       return shader;
/*     */     } finally {
/* 254 */       if (!compiled) GL32C.glDeleteShader(shader); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\loading\BootstrapLoadingRenderer$Program.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */