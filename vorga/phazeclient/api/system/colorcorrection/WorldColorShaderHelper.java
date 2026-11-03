/*     */ package vorga.phazeclient.api.system.colorcorrection;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public final class WorldColorShaderHelper
/*     */ {
/*     */   public static final String BLOCK_COLOR_UNIFORM = "PhazeBlockColor";
/*     */   public static final String BLOCK_PARAMS_UNIFORM = "PhazeBlockParams";
/*     */   public static final String FLUID_COLOR_UNIFORM = "PhazeFluidColor";
/*     */   public static final String FLUID_PARAMS_UNIFORM = "PhazeFluidParams";
/*     */   public static final String PLAYER_COLOR_UNIFORM = "PhazePlayerColor";
/*     */   public static final String PLAYER_PARAMS_UNIFORM = "PhazePlayerParams";
/*     */   public static final String ENTITY_COLOR_UNIFORM = "PhazeEntityColor";
/*     */   public static final String ENTITY_PARAMS_UNIFORM = "PhazeEntityParams";
/*  18 */   private static final WorldColorCorrectionController.Target[] RAW_TARGETS = new WorldColorCorrectionController.Target[] { WorldColorCorrectionController.Target.BLOCKS, WorldColorCorrectionController.Target.FLUIDS, WorldColorCorrectionController.Target.PLAYERS, WorldColorCorrectionController.Target.ENTITIES };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   private static final Map<Integer, ProgramState> PROGRAM_STATES = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void uploadTerrainUniformsToCurrentGlProgram() {
/*     */     try {
/*  31 */       int program = GL20.glGetInteger(35725);
/*  32 */       if (program <= 0) {
/*     */         return;
/*     */       }
/*     */       
/*  36 */       ProgramState state = PROGRAM_STATES.computeIfAbsent(Integer.valueOf(program), ProgramState::new);
/*  37 */       long signature = computeStateSignature();
/*  38 */       if (state.lastSignature == signature) {
/*     */         return;
/*     */       }
/*     */       
/*  42 */       ProgramLocations locations = state.locations;
/*  43 */       uploadRawTarget(locations.blockColor, locations.blockParams, WorldColorCorrectionController.Target.BLOCKS);
/*  44 */       uploadRawTarget(locations.fluidColor, locations.fluidParams, WorldColorCorrectionController.Target.FLUIDS);
/*  45 */       uploadRawTarget(locations.playerColor, locations.playerParams, WorldColorCorrectionController.Target.PLAYERS);
/*  46 */       uploadRawTarget(locations.entityColor, locations.entityParams, WorldColorCorrectionController.Target.ENTITIES);
/*  47 */       state.lastSignature = signature;
/*  48 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCurrentProgramInstance() {
/*  53 */     int program = GL20.glGetInteger(35725);
/*  54 */     if (program > 0) {
/*  55 */       PROGRAM_STATES.remove(Integer.valueOf(program));
/*     */     }
/*     */   }
/*     */   
/*     */   private static long computeStateSignature() {
/*  60 */     long signature = -7046029254386353131L;
/*  61 */     for (WorldColorCorrectionController.Target target : RAW_TARGETS) {
/*  62 */       boolean active = WorldColorCorrectionController.isTargetActive(target);
/*  63 */       signature = signature * 31L + (active ? 1L : 0L);
/*  64 */       if (active) {
/*     */ 
/*     */         
/*  67 */         signature = signature * 31L + Float.floatToRawIntBits(WorldColorCorrectionController.getRed(target));
/*  68 */         signature = signature * 31L + Float.floatToRawIntBits(WorldColorCorrectionController.getGreen(target));
/*  69 */         signature = signature * 31L + Float.floatToRawIntBits(WorldColorCorrectionController.getBlue(target));
/*  70 */         signature = signature * 31L + Float.floatToRawIntBits(WorldColorCorrectionController.getAlpha(target));
/*  71 */         signature = signature * 31L + Float.floatToRawIntBits(WorldColorCorrectionController.getSaturation(target));
/*  72 */         signature = signature * 31L + Float.floatToRawIntBits(WorldColorCorrectionController.getBrightness(target));
/*     */       } 
/*  74 */     }  return signature;
/*     */   }
/*     */   
/*     */   private static void uploadRawTarget(int colorLocation, int paramsLocation, WorldColorCorrectionController.Target target) {
/*  78 */     boolean active = WorldColorCorrectionController.isTargetActive(target);
/*     */ 
/*     */     
/*  81 */     float alpha = WorldColorCorrectionController.supportsAlpha(target) ? WorldColorCorrectionController.getAlpha(target) : 1.0F;
/*     */     
/*  83 */     if (colorLocation >= 0) {
/*  84 */       if (active) {
/*  85 */         GL20.glUniform4f(colorLocation, 
/*     */             
/*  87 */             WorldColorCorrectionController.getRed(target), 
/*  88 */             WorldColorCorrectionController.getGreen(target), 
/*  89 */             WorldColorCorrectionController.getBlue(target), alpha);
/*     */       }
/*     */       else {
/*     */         
/*  93 */         GL20.glUniform4f(colorLocation, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */     
/*  97 */     if (paramsLocation >= 0)
/*  98 */       if (active) {
/*  99 */         GL20.glUniform4f(paramsLocation, 
/*     */             
/* 101 */             WorldColorCorrectionController.getSaturation(target), 
/* 102 */             WorldColorCorrectionController.getBrightness(target), 1.0F, 0.0F);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 107 */         GL20.glUniform4f(paramsLocation, 1.0F, 0.0F, 0.0F, 0.0F);
/*     */       }  
/*     */   }
/*     */   private static final class ProgramLocations extends Record {
/*     */     private final int blockColor; private final int blockParams; private final int fluidColor; private final int fluidParams;
/* 112 */     public int entityParams() { return this.entityParams; } private final int playerColor; private final int playerParams; private final int entityColor; private final int entityParams; public int entityColor() { return this.entityColor; } public int playerParams() { return this.playerParams; } public int playerColor() { return this.playerColor; } public int fluidParams() { return this.fluidParams; } public int fluidColor() { return this.fluidColor; } public int blockParams() { return this.blockParams; } public int blockColor() { return this.blockColor; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #112	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;
/* 112 */       //   0	8	1	o	Ljava/lang/Object; } private ProgramLocations(int blockColor, int blockParams, int fluidColor, int fluidParams, int playerColor, int playerParams, int entityColor, int entityParams) { this.blockColor = blockColor; this.blockParams = blockParams; this.fluidColor = fluidColor; this.fluidParams = fluidParams; this.playerColor = playerColor; this.playerParams = playerParams; this.entityColor = entityColor; this.entityParams = entityParams; }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #112	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;
/*     */     }
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #112	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;
/*     */     }
/*     */     
/*     */     private ProgramLocations(int program) {
/* 123 */       this(
/* 124 */           GL20.glGetUniformLocation(program, "PhazeBlockColor"), 
/* 125 */           GL20.glGetUniformLocation(program, "PhazeBlockParams"), 
/* 126 */           GL20.glGetUniformLocation(program, "PhazeFluidColor"), 
/* 127 */           GL20.glGetUniformLocation(program, "PhazeFluidParams"), 
/* 128 */           GL20.glGetUniformLocation(program, "PhazePlayerColor"), 
/* 129 */           GL20.glGetUniformLocation(program, "PhazePlayerParams"), 
/* 130 */           GL20.glGetUniformLocation(program, "PhazeEntityColor"), 
/* 131 */           GL20.glGetUniformLocation(program, "PhazeEntityParams"));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ProgramState
/*     */   {
/*     */     private final WorldColorShaderHelper.ProgramLocations locations;
/* 138 */     private long lastSignature = Long.MIN_VALUE;
/*     */     
/*     */     private ProgramState(int program) {
/* 141 */       this.locations = new WorldColorShaderHelper.ProgramLocations(program);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\WorldColorShaderHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */