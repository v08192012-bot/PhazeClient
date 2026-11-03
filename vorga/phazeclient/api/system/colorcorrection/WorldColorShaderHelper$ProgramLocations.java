/*     */ package vorga.phazeclient.api.system.colorcorrection;
/*     */ 
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProgramLocations
/*     */   extends Record
/*     */ {
/*     */   private final int blockColor;
/*     */   private final int blockParams;
/*     */   private final int fluidColor;
/*     */   private final int fluidParams;
/*     */   private final int playerColor;
/*     */   private final int playerParams;
/*     */   private final int entityColor;
/*     */   private final int entityParams;
/*     */   
/*     */   public int entityParams() {
/* 112 */     return this.entityParams; } public int entityColor() { return this.entityColor; } public int playerParams() { return this.playerParams; } public int playerColor() { return this.playerColor; } public int fluidParams() { return this.fluidParams; } public int fluidColor() { return this.fluidColor; } public int blockParams() { return this.blockParams; } public int blockColor() { return this.blockColor; } private ProgramLocations(int blockColor, int blockParams, int fluidColor, int fluidParams, int playerColor, int playerParams, int entityColor, int entityParams) { this.blockColor = blockColor; this.blockParams = blockParams; this.fluidColor = fluidColor; this.fluidParams = fluidParams; this.playerColor = playerColor; this.playerParams = playerParams; this.entityColor = entityColor; this.entityParams = entityParams; }
/*     */    public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #112	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #112	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;
/*     */   }
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #112	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lvorga/phazeclient/api/system/colorcorrection/WorldColorShaderHelper$ProgramLocations;
/*     */   }
/*     */   private ProgramLocations(int program) {
/* 123 */     this(
/* 124 */         GL20.glGetUniformLocation(program, "PhazeBlockColor"), 
/* 125 */         GL20.glGetUniformLocation(program, "PhazeBlockParams"), 
/* 126 */         GL20.glGetUniformLocation(program, "PhazeFluidColor"), 
/* 127 */         GL20.glGetUniformLocation(program, "PhazeFluidParams"), 
/* 128 */         GL20.glGetUniformLocation(program, "PhazePlayerColor"), 
/* 129 */         GL20.glGetUniformLocation(program, "PhazePlayerParams"), 
/* 130 */         GL20.glGetUniformLocation(program, "PhazeEntityColor"), 
/* 131 */         GL20.glGetUniformLocation(program, "PhazeEntityParams"));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\WorldColorShaderHelper$ProgramLocations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */