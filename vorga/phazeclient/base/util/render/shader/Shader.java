/*     */ package vorga.phazeclient.base.util.render.shader;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import org.joml.Vector2f;
/*     */ import org.joml.Vector3f;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public class Shader
/*     */ {
/*     */   private final int programId;
/*     */   private final String vertexPath;
/*     */   private final String fragmentPath;
/*     */   
/*     */   public Shader(String folder, String name) {
/*  18 */     this.vertexPath = "/assets/Phaze/shaders/" + folder + "/" + name + ".vsh";
/*  19 */     this.fragmentPath = "/assets/Phaze/shaders/" + folder + "/" + name + ".fsh";
/*     */     
/*  21 */     int vertexId = createShader(this.vertexPath, 35633);
/*  22 */     int fragmentId = createShader(this.fragmentPath, 35632);
/*     */     
/*  24 */     this.programId = GL20.glCreateProgram();
/*  25 */     GL20.glAttachShader(this.programId, vertexId);
/*  26 */     GL20.glAttachShader(this.programId, fragmentId);
/*  27 */     GL20.glLinkProgram(this.programId);
/*     */     
/*  29 */     if (GL20.glGetProgrami(this.programId, 35714) == 0) {
/*  30 */       throw new RuntimeException("Failed to link shader program: " + GL20.glGetProgramInfoLog(this.programId));
/*     */     }
/*     */     
/*  33 */     GL20.glDeleteShader(vertexId);
/*  34 */     GL20.glDeleteShader(fragmentId);
/*     */   }
/*     */   
/*     */   private int createShader(String path, int type) {
/*     */     try {
/*  39 */       String source = readShaderFile(path);
/*  40 */       int shaderId = GL20.glCreateShader(type);
/*  41 */       GL20.glShaderSource(shaderId, source);
/*  42 */       GL20.glCompileShader(shaderId);
/*     */       
/*  44 */       if (GL20.glGetShaderi(shaderId, 35713) == 0) {
/*  45 */         throw new RuntimeException("Failed to compile shader: " + path + "\n" + GL20.glGetShaderInfoLog(shaderId));
/*     */       }
/*     */       
/*  48 */       return shaderId;
/*  49 */     } catch (IOException e) {
/*  50 */       throw new RuntimeException("Failed to load shader: " + path, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String readShaderFile(String path) throws IOException {
/*  55 */     InputStream stream = Shader.class.getResourceAsStream(path);
/*  56 */     if (stream == null) {
/*  57 */       throw new IOException("Shader file not found: " + path);
/*     */     }
/*     */     
/*  60 */     StringBuilder source = new StringBuilder();
/*  61 */     BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); 
/*     */     try { String line;
/*  63 */       while ((line = reader.readLine()) != null) {
/*  64 */         source.append(line).append("\n");
/*     */       }
/*  66 */       reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*  67 */      return source.toString();
/*     */   }
/*     */   
/*     */   public void bind() {
/*  71 */     GL20.glUseProgram(this.programId);
/*     */   }
/*     */   
/*     */   public void unbind() {
/*  75 */     GL20.glUseProgram(0);
/*     */   }
/*     */   
/*     */   public void setUniform1i(String name, int value) {
/*  79 */     int location = GL20.glGetUniformLocation(this.programId, name);
/*  80 */     if (location != -1) {
/*  81 */       GL20.glUniform1i(location, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUniform1f(String name, float value) {
/*  86 */     int location = GL20.glGetUniformLocation(this.programId, name);
/*  87 */     if (location != -1) {
/*  88 */       GL20.glUniform1f(location, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUniform2f(String name, float x, float y) {
/*  93 */     int location = GL20.glGetUniformLocation(this.programId, name);
/*  94 */     if (location != -1) {
/*  95 */       GL20.glUniform2f(location, x, y);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUniform2f(String name, Vector2f vec) {
/* 100 */     setUniform2f(name, vec.x, vec.y);
/*     */   }
/*     */   
/*     */   public void setUniform3f(String name, float x, float y, float z) {
/* 104 */     int location = GL20.glGetUniformLocation(this.programId, name);
/* 105 */     if (location != -1) {
/* 106 */       GL20.glUniform3f(location, x, y, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setUniform3f(String name, Vector3f vec) {
/* 111 */     setUniform3f(name, vec.x, vec.y, vec.z);
/*     */   }
/*     */   
/*     */   public void setUniformBool(String name, boolean value) {
/* 115 */     setUniform1i(name, value ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void delete() {
/* 119 */     GL20.glDeleteProgram(this.programId);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\shader\Shader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */