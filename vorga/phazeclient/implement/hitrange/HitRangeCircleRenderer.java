/*     */ package vorga.phazeclient.implement.hitrange;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4668;
/*     */ import net.minecraft.class_746;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HitRangeCircleRenderer
/*     */ {
/*     */   private static final float FILLED_OUTLINE_Y_EPSILON = 0.0015F;
/*  66 */   private static final class_1921.class_4687 DEBUG_LINES = makeLayer(class_293.class_5596.field_29344);
/*  67 */   private static final class_1921.class_4687 DEBUG_QUADS = makeLayer(class_293.class_5596.field_27382);
/*  68 */   private static final class_1921.class_4687 TRIANGLES = makeLayer(class_293.class_5596.field_27379);
/*     */   
/*  70 */   private static final List<Angle> angles = new ArrayList<>();
/*     */   
/*     */   static {
/*  73 */     computeAngles();
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
/*     */   
/*     */   public static void drawCircle(class_4587 matrices, class_4597 vertexConsumers, class_10055 state) {
/*  86 */     HitRange config = HitRange.getInstance();
/*  87 */     class_746 player = (class_310.method_1551()).field_1724;
/*  88 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/*  92 */     class_243 entityPos = new class_243(state.field_53325, state.field_53326, state.field_53327);
/*     */     
/*  94 */     int color = config.color.getColor();
/*  95 */     if (config.randomColors.isValue()) {
/*     */ 
/*     */       
/*  98 */       color = state.field_53529.hashCode() | 0xFF000000;
/*  99 */     } else if (config.colorWhenInRange
/* 100 */       .isValue() && state.field_53528 != player
/* 101 */       .method_5628() && entityPos
/* 102 */       .method_24802((class_2374)player.method_19538(), config.radius.getValue())) {
/*     */       
/* 104 */       color = config.inRangeColor.getColor();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     float dy = (state.field_53334 ? 0.125F : 0.0F) + config.height.getValue();
/*     */     
/* 112 */     HitRange.Mode mode = config.mode();
/* 113 */     ResolvedCircleColors resolvedColors = resolveColors(config, mode, color);
/*     */     
/* 115 */     matrices.method_22903();
/* 116 */     switch (mode) { case LINE:
/* 117 */         drawCircleLines(matrices, vertexConsumers.getBuffer((class_1921)DEBUG_LINES), dy, resolvedColors.outlineArgb()); break;
/* 118 */       case THICK: drawCircleQuad(matrices, vertexConsumers.getBuffer((class_1921)DEBUG_QUADS), dy, resolvedColors.outlineArgb()); break;
/*     */       case FILLED:
/* 120 */         if ((resolvedColors.fillArgb() >>> 24 & 0xFF) > 0) {
/* 121 */           drawCircleTriangles(matrices, vertexConsumers.getBuffer((class_1921)TRIANGLES), dy, resolvedColors.fillArgb());
/*     */         }
/* 123 */         if ((resolvedColors.outlineArgb() >>> 24 & 0xFF) > 0) {
/* 124 */           drawCircleLines(matrices, vertexConsumers.getBuffer((class_1921)DEBUG_LINES), dy + 0.0015F, resolvedColors.outlineArgb());
/*     */         }
/*     */         break; }
/*     */     
/* 128 */     matrices.method_22909();
/*     */   }
/*     */   
/*     */   private static ResolvedCircleColors resolveColors(HitRange config, HitRange.Mode mode, int color) {
/* 132 */     if (mode != HitRange.Mode.FILLED) {
/* 133 */       return new ResolvedCircleColors(color, color);
/*     */     }
/*     */     
/* 136 */     int rgb = color & 0xFFFFFF;
/* 137 */     int fillAlpha = color >>> 24 & 0xFF;
/* 138 */     int outlineAlpha = class_3532.method_15340(Math.round(config.outlineOpacity.getValue() * 255.0F), 0, 255);
/* 139 */     return new ResolvedCircleColors(fillAlpha << 24 | rgb, outlineAlpha << 24 | rgb);
/*     */   }
/*     */   
/*     */   private static void drawCircleLines(class_4587 matrices, class_4588 vertices, float dy, int argb) {
/* 143 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     int n = angles.size();
/* 152 */     for (int i = 0; i < n; i++) {
/* 153 */       Angle a = angles.get(i);
/* 154 */       Angle b = angles.get((i + 1) % n);
/* 155 */       vertices.method_22918(positionMatrix, a.dx, dy, a.dz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/* 156 */       vertices.method_22918(positionMatrix, b.dx, dy, b.dz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void drawCircleQuad(class_4587 matrices, class_4588 vertices, float dy, int argb) {
/* 161 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */     
/* 163 */     for (int i = 1; i < angles.size() + 1; i++) {
/* 164 */       Angle angle = angles.get(i % angles.size());
/* 165 */       Angle prevAngle = angles.get(i - 1);
/*     */       
/* 167 */       vertices.method_22918(positionMatrix, prevAngle.dx, dy, prevAngle.dz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/* 168 */       vertices.method_22918(positionMatrix, prevAngle.farDx, dy, prevAngle.farDz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/* 169 */       vertices.method_22918(positionMatrix, angle.farDx, dy, angle.farDz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/* 170 */       vertices.method_22918(positionMatrix, angle.dx, dy, angle.dz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void drawCircleTriangles(class_4587 matrices, class_4588 vertices, float dy, int argb) {
/* 175 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     int n = angles.size();
/* 186 */     for (int i = 0; i < n; i++) {
/* 187 */       Angle a = angles.get(i);
/* 188 */       Angle b = angles.get((i + 1) % n);
/* 189 */       vertices.method_22918(positionMatrix, 0.0F, dy, 0.0F).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/* 190 */       vertices.method_22918(positionMatrix, a.dx, dy, a.dz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/* 191 */       vertices.method_22918(positionMatrix, b.dx, dy, b.dz).method_39415(argb).method_60831(matrices.method_23760(), 0.0F, 0.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeAngles() {
/* 201 */     angles.clear();
/* 202 */     HitRange config = HitRange.getInstance();
/*     */     
/* 204 */     int segments = Math.max(3, config.circleSegments.getInt());
/* 205 */     float radius = config.radius.getValue();
/* 206 */     HitRange.Mode mode = config.mode();
/*     */     
/* 208 */     if (mode == HitRange.Mode.THICK) {
/* 209 */       float thickness = Math.max(0.001F, config.thickness.getValue());
/* 210 */       for (int i = 0; i < segments; i++) {
/* 211 */         float angle = 6.2831855F * i / segments;
/* 212 */         float inner = radius - thickness / 2.0F;
/* 213 */         float outer = inner + thickness;
/*     */         
/* 215 */         float dx = inner * class_3532.method_15374(angle);
/* 216 */         float dz = inner * class_3532.method_15362(angle);
/*     */         
/* 218 */         float farDx = outer * class_3532.method_15374(angle);
/* 219 */         float farDz = outer * class_3532.method_15362(angle);
/*     */         
/* 221 */         angles.add(new Angle(dx, dz, farDx, farDz));
/*     */       } 
/*     */     } else {
/* 224 */       for (int i = 0; i < segments; i++) {
/* 225 */         float angle = 6.2831855F * i / segments;
/* 226 */         float dx = radius * class_3532.method_15374(angle);
/* 227 */         float dz = radius * class_3532.method_15362(angle);
/*     */         
/* 229 */         angles.add(new Angle(dx, dz));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class_1921.class_4687 makeLayer(class_293.class_5596 mode) {
/* 235 */     String name = "phaze_hitrange_" + mode.name().toLowerCase(Locale.ROOT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     return class_1921.method_24049(name, class_290.field_1576, mode, 1536, false, true, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 249 */         class_1921.class_4688.method_23598()
/* 250 */         .method_34578(class_4668.field_29442)
/* 251 */         .method_23615(class_4668.field_21370)
/* 252 */         .method_23603(class_4668.field_21345)
/* 253 */         .method_23608(class_4668.field_21383)
/* 254 */         .method_23611(class_4668.field_21385)
/* 255 */         .method_23616(class_4668.field_21350)
/* 256 */         .method_23604(class_4668.field_21348)
/* 257 */         .method_23607(class_4668.field_22241)
/* 258 */         .method_23617(false));
/*     */   }
/*     */   
/*     */   private static final class Angle extends Record {
/*     */     private final float dx;
/*     */     private final float dz;
/*     */     private final float farDx;
/*     */     private final float farDz;
/*     */     
/* 267 */     public float farDz() { return this.farDz; } public float farDx() { return this.farDx; } public float dz() { return this.dz; } public float dx() { return this.dx; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$Angle;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #267	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$Angle;
/* 267 */       //   0	8	1	o	Ljava/lang/Object; } private Angle(float dx, float dz, float farDx, float farDz) { this.dx = dx; this.dz = dz; this.farDx = farDx; this.farDz = farDz; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$Angle;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #267	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$Angle; }
/*     */     public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$Angle;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #267	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 269 */       //   0	7	0	this	Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$Angle; } Angle(float dx, float dz) { this(dx, dz, 0.0F, 0.0F); }
/*     */      }
/*     */   private static final class ResolvedCircleColors extends Record { private final int fillArgb; private final int outlineArgb;
/*     */     
/* 273 */     private ResolvedCircleColors(int fillArgb, int outlineArgb) { this.fillArgb = fillArgb; this.outlineArgb = outlineArgb; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$ResolvedCircleColors;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #273	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$ResolvedCircleColors; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$ResolvedCircleColors;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #273	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$ResolvedCircleColors; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$ResolvedCircleColors;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #273	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/hitrange/HitRangeCircleRenderer$ResolvedCircleColors;
/* 273 */       //   0	8	1	o	Ljava/lang/Object; } public int fillArgb() { return this.fillArgb; } public int outlineArgb() { return this.outlineArgb; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\hitrange\HitRangeCircleRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */