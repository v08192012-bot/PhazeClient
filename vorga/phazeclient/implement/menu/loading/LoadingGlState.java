/*     */ package vorga.phazeclient.implement.menu.loading;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.lwjgl.opengl.ARBSamplerObjects;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.opengl.GL32C;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LoadingGlState
/*     */   implements AutoCloseable
/*     */ {
/*     */   static final int ATLAS_TEXTURE_UNIT = 33991;
/*     */   static final int ATLAS_TEXTURE_INDEX = 7;
/*  31 */   private static final int[] ENABLES = new int[] { 3042, 2929, 2884, 3089, 2960, 3058, 35977, 32926, 32928, 36281 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private static final int[] UNPACK = new int[] { 3317, 3314, 3316, 3315 };
/*     */ 
/*     */ 
/*     */   
/*  40 */   private final int program = GL32C.glGetInteger(35725);
/*  41 */   private final int vao = GL32C.glGetInteger(34229);
/*  42 */   private final int arrayBuffer = GL32C.glGetInteger(34964);
/*  43 */   private final int unpackBuffer = GL32C.glGetInteger(35055);
/*  44 */   private final int activeTexture = GL32C.glGetInteger(34016);
/*     */   private final int atlasTexture;
/*  46 */   private final boolean samplerSupported = ((GL.getCapabilities()).OpenGL33 || 
/*  47 */     (GL.getCapabilities()).GL_ARB_sampler_objects);
/*     */   private final int sampler;
/*  49 */   private final int srcRgb = GL32C.glGetInteger(32969);
/*  50 */   private final int dstRgb = GL32C.glGetInteger(32968);
/*  51 */   private final int srcAlpha = GL32C.glGetInteger(32971);
/*  52 */   private final int dstAlpha = GL32C.glGetInteger(32970);
/*  53 */   private final int equationRgb = GL32C.glGetInteger(32777);
/*  54 */   private final int equationAlpha = GL32C.glGetInteger(34877);
/*  55 */   private final boolean depthMask = GL32C.glGetBoolean(2930);
/*     */   private final int polygonMode;
/*  57 */   private final int[] viewport = new int[4];
/*  58 */   private final boolean[] colorMask = new boolean[4];
/*  59 */   private final boolean[] enabled = new boolean[ENABLES.length];
/*  60 */   private final int[] unpack = new int[UNPACK.length];
/*     */   LoadingGlState() {
/*     */     int i;
/*  63 */     for (i = 0; i < ENABLES.length; ) { this.enabled[i] = GL32C.glIsEnabled(ENABLES[i]); i++; }
/*  64 */      for (i = 0; i < UNPACK.length; ) { this.unpack[i] = GL32C.glGetInteger(UNPACK[i]); i++; }
/*  65 */      MemoryStack stack = MemoryStack.stackPush(); 
/*  66 */     try { IntBuffer values = stack.mallocInt(4);
/*  67 */       GL32C.glGetIntegerv(2978, values);
/*  68 */       for (int j = 0; j < 4; ) { this.viewport[j] = values.get(j); j++; }
/*  69 */        GL32C.glGetIntegerv(2880, values);
/*  70 */       this.polygonMode = values.get(0);
/*  71 */       ByteBuffer mask = stack.malloc(4);
/*  72 */       GL32C.glGetBooleanv(3107, mask);
/*  73 */       for (int k = 0; k < 4; ) { this.colorMask[k] = (mask.get(k) != 0); k++; }
/*  74 */        if (stack != null) stack.close();  } catch (Throwable throwable) { if (stack != null)
/*  75 */         try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  RenderSystem.activeTexture(33991);
/*  76 */     this.atlasTexture = GL32C.glGetInteger(32873);
/*  77 */     this.sampler = this.samplerSupported ? GL32C.glGetInteger(35097) : 0;
/*  78 */     RenderSystem.activeTexture(this.activeTexture);
/*     */   }
/*     */   
/*     */   void prepare(int width, int height) {
/*  82 */     for (int capability : ENABLES) GL32C.glDisable(capability); 
/*  83 */     GL32C.glEnable(3042);
/*  84 */     GL32C.glBlendEquationSeparate(32774, 32774);
/*  85 */     GL32C.glBlendFuncSeparate(770, 771, 1, 771);
/*  86 */     GL32C.glDepthMask(false);
/*  87 */     GL32C.glColorMask(true, true, true, true);
/*  88 */     GL32C.glPolygonMode(1032, 6914);
/*  89 */     GL32C.glViewport(0, 0, width, height);
/*  90 */     RenderSystem.activeTexture(33991);
/*  91 */     if (this.samplerSupported) ARBSamplerObjects.glBindSampler(7, 0);
/*     */   
/*     */   }
/*     */   
/*     */   public void close() {
/*  96 */     GL32C.glUseProgram(this.program);
/*  97 */     GL32C.glBindVertexArray(this.vao);
/*  98 */     GL32C.glBindBuffer(34962, this.arrayBuffer);
/*  99 */     GL32C.glBindBuffer(35052, this.unpackBuffer); int i;
/* 100 */     for (i = 0; i < UNPACK.length; ) { GL32C.glPixelStorei(UNPACK[i], this.unpack[i]); i++; }
/* 101 */      RenderSystem.activeTexture(33991);
/* 102 */     RenderSystem.bindTexture(this.atlasTexture);
/* 103 */     if (this.samplerSupported) ARBSamplerObjects.glBindSampler(7, this.sampler); 
/* 104 */     RenderSystem.activeTexture(this.activeTexture);
/* 105 */     GL32C.glBlendEquationSeparate(this.equationRgb, this.equationAlpha);
/* 106 */     GL32C.glBlendFuncSeparate(this.srcRgb, this.dstRgb, this.srcAlpha, this.dstAlpha);
/* 107 */     GL32C.glDepthMask(this.depthMask);
/* 108 */     GL32C.glColorMask(this.colorMask[0], this.colorMask[1], this.colorMask[2], this.colorMask[3]);
/* 109 */     GL32C.glPolygonMode(1032, this.polygonMode);
/* 110 */     GL32C.glViewport(this.viewport[0], this.viewport[1], this.viewport[2], this.viewport[3]);
/* 111 */     for (i = 0; i < ENABLES.length; i++) {
/* 112 */       if (this.enabled[i]) { GL32C.glEnable(ENABLES[i]); }
/* 113 */       else { GL32C.glDisable(ENABLES[i]); }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\loading\LoadingGlState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */