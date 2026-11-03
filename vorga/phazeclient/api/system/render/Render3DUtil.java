/*      */ package vorga.phazeclient.api.system.render;
/*      */ 
/*      */ import com.mojang.blaze3d.platform.GlStateManager;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import java.util.List;
/*      */ import net.minecraft.class_10142;
/*      */ import net.minecraft.class_238;
/*      */ import net.minecraft.class_243;
/*      */ import net.minecraft.class_286;
/*      */ import net.minecraft.class_287;
/*      */ import net.minecraft.class_289;
/*      */ import net.minecraft.class_290;
/*      */ import net.minecraft.class_293;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_4184;
/*      */ import net.minecraft.class_4587;
/*      */ import net.minecraft.class_4588;
/*      */ import net.minecraft.class_9801;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Vector3f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Render3DUtil
/*      */ {
/*      */   private static final int MAX_CACHED_CIRCLE_SEGMENTS = 256;
/*      */   private static final int MAX_CACHED_SPHERE_STACKS = 64;
/*   52 */   private static final CircleLut[] CIRCLE_LUT_CACHE = new CircleLut[257];
/*   53 */   private static final SphereLatitudeLut[] SPHERE_LATITUDE_CACHE = new SphereLatitudeLut[65];
/*   54 */   private static final Vector3f BILLBOARD_RIGHT = new Vector3f();
/*   55 */   private static final Vector3f BILLBOARD_UP = new Vector3f();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBoxFill(class_4587 matrices, float x1, float y1, float z1, float x2, float y2, float z2, int color, float fillAlphaScale) {
/*   75 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*   76 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*   77 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*   78 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*   79 */     float b = (color & 0xFF) / 255.0F;
/*   80 */     float fillA = a * Math.max(0.0F, Math.min(1.0F, fillAlphaScale));
/*      */     
/*   82 */     RenderSystem.enableBlend();
/*   83 */     RenderSystem.defaultBlendFunc();
/*   84 */     RenderSystem.disableCull();
/*   85 */     RenderSystem.enableDepthTest();
/*   86 */     RenderSystem.depthMask(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   92 */     RenderSystem.polygonOffset(-1.0F, -1.0F);
/*   93 */     RenderSystem.enablePolygonOffset();
/*   94 */     RenderSystem.setShader(class_10142.field_53876);
/*      */     
/*   96 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*      */     
/*   98 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*   99 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  100 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  101 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*      */     
/*  103 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  104 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  105 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  106 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  108 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  109 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  110 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  111 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*      */     
/*  113 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  114 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  115 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  116 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*      */     
/*  118 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  119 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  120 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  121 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  123 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  124 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  125 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  126 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  127 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  129 */     RenderSystem.disablePolygonOffset();
/*  130 */     RenderSystem.depthMask(true);
/*  131 */     RenderSystem.enableCull();
/*  132 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBox(class_4587 matrices, float x1, float y1, float z1, float x2, float y2, float z2, int color, float fillAlphaScale, float lineWidth) {
/*  147 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  148 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  149 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  150 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  151 */     float b = (color & 0xFF) / 255.0F;
/*  152 */     float fillA = a * Math.max(0.0F, Math.min(1.0F, fillAlphaScale));
/*      */     
/*  154 */     RenderSystem.enableBlend();
/*  155 */     RenderSystem.defaultBlendFunc();
/*  156 */     RenderSystem.disableCull();
/*  157 */     RenderSystem.enableDepthTest();
/*  158 */     RenderSystem.depthMask(false);
/*      */ 
/*      */     
/*  161 */     RenderSystem.setShader(class_10142.field_53876);
/*  162 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*      */     
/*  164 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  165 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  166 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  167 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*      */     
/*  169 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  170 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  171 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  172 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  174 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  175 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  176 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  177 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*      */     
/*  179 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  180 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  181 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  182 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*      */     
/*  184 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  185 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  186 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  187 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  189 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  190 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  191 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  192 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  193 */     class_286.method_43433(buffer.method_60800());
/*      */ 
/*      */     
/*  196 */     RenderSystem.setShader(class_10142.field_53864);
/*  197 */     RenderSystem.lineWidth(Math.max(1.0F, lineWidth));
/*  198 */     buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/*      */     
/*  200 */     line(matrix, buffer, x1, y1, z1, x2, y1, z1, r, g, b, a);
/*  201 */     line(matrix, buffer, x2, y1, z1, x2, y1, z2, r, g, b, a);
/*  202 */     line(matrix, buffer, x2, y1, z2, x1, y1, z2, r, g, b, a);
/*  203 */     line(matrix, buffer, x1, y1, z2, x1, y1, z1, r, g, b, a);
/*      */     
/*  205 */     line(matrix, buffer, x1, y2, z1, x2, y2, z1, r, g, b, a);
/*  206 */     line(matrix, buffer, x2, y2, z1, x2, y2, z2, r, g, b, a);
/*  207 */     line(matrix, buffer, x2, y2, z2, x1, y2, z2, r, g, b, a);
/*  208 */     line(matrix, buffer, x1, y2, z2, x1, y2, z1, r, g, b, a);
/*      */     
/*  210 */     line(matrix, buffer, x1, y1, z1, x1, y2, z1, r, g, b, a);
/*  211 */     line(matrix, buffer, x2, y1, z1, x2, y2, z1, r, g, b, a);
/*  212 */     line(matrix, buffer, x2, y1, z2, x2, y2, z2, r, g, b, a);
/*  213 */     line(matrix, buffer, x1, y1, z2, x1, y2, z2, r, g, b, a);
/*  214 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  216 */     RenderSystem.depthMask(true);
/*  217 */     RenderSystem.enableCull();
/*  218 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void vertexBoxFill(class_4587 matrices, class_4588 consumer, class_238 box, int color, float fillAlphaScale) {
/*  226 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  227 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  228 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  229 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  230 */     float b = (color & 0xFF) / 255.0F;
/*  231 */     float fillA = a * Math.max(0.0F, Math.min(1.0F, fillAlphaScale));
/*  232 */     float x1 = (float)box.field_1323;
/*  233 */     float y1 = (float)box.field_1322;
/*  234 */     float z1 = (float)box.field_1321;
/*  235 */     float x2 = (float)box.field_1320;
/*  236 */     float y2 = (float)box.field_1325;
/*  237 */     float z2 = (float)box.field_1324;
/*      */     
/*  239 */     consumer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  240 */     consumer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  241 */     consumer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  242 */     consumer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*      */     
/*  244 */     consumer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  245 */     consumer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  246 */     consumer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  247 */     consumer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  249 */     consumer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  250 */     consumer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  251 */     consumer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  252 */     consumer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*      */     
/*  254 */     consumer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  255 */     consumer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  256 */     consumer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  257 */     consumer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*      */     
/*  259 */     consumer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  260 */     consumer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  261 */     consumer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  262 */     consumer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  264 */     consumer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  265 */     consumer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  266 */     consumer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  267 */     consumer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawSolidBox(class_4587 matrices, float x1, float y1, float z1, float x2, float y2, float z2, int color, float fillAlphaScale, boolean depthTest) {
/*  281 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  282 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  283 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  284 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  285 */     float b = (color & 0xFF) / 255.0F;
/*  286 */     float fillA = a * Math.max(0.0F, Math.min(1.0F, fillAlphaScale));
/*      */     
/*  288 */     RenderSystem.enableBlend();
/*  289 */     RenderSystem.defaultBlendFunc();
/*  290 */     RenderSystem.disableCull();
/*  291 */     if (depthTest) {
/*  292 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  294 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  296 */     RenderSystem.depthMask(false);
/*      */     
/*  298 */     RenderSystem.setShader(class_10142.field_53876);
/*  299 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*      */     
/*  301 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  302 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  303 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  304 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*      */     
/*  306 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  307 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  308 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  309 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  311 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  312 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*  313 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  314 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*      */     
/*  316 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  317 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  318 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  319 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*      */     
/*  321 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, fillA);
/*  322 */     buffer.method_22918(matrix, x1, y1, z2).method_22915(r, g, b, fillA);
/*  323 */     buffer.method_22918(matrix, x1, y2, z2).method_22915(r, g, b, fillA);
/*  324 */     buffer.method_22918(matrix, x1, y2, z1).method_22915(r, g, b, fillA);
/*      */     
/*  326 */     buffer.method_22918(matrix, x2, y1, z1).method_22915(r, g, b, fillA);
/*  327 */     buffer.method_22918(matrix, x2, y2, z1).method_22915(r, g, b, fillA);
/*  328 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, fillA);
/*  329 */     buffer.method_22918(matrix, x2, y1, z2).method_22915(r, g, b, fillA);
/*  330 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  332 */     RenderSystem.depthMask(true);
/*  333 */     RenderSystem.enableDepthTest();
/*  334 */     RenderSystem.enableCull();
/*  335 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawSphereSolid(class_4587 matrices, float cx, float cy, float cz, float radius, int color, int stacks, int segments) {
/*  355 */     drawSphereSolid(matrices, cx, cy, cz, radius, color, stacks, segments, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawSphereSolid(class_4587 matrices, float cx, float cy, float cz, float radius, int color, int stacks, int segments, boolean depthTest) {
/*  383 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  384 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  385 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  386 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  387 */     float b = (color & 0xFF) / 255.0F;
/*      */     
/*  389 */     RenderSystem.enableBlend();
/*  390 */     RenderSystem.defaultBlendFunc();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  396 */     RenderSystem.enableCull();
/*  397 */     if (depthTest) {
/*  398 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  400 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  402 */     RenderSystem.depthMask(false);
/*  403 */     RenderSystem.setShader(class_10142.field_53876);
/*      */     
/*  405 */     CircleLut circle = circleLut(segments);
/*  406 */     SphereLatitudeLut latitudes = sphereLatitudeLut(stacks);
/*  407 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  408 */     for (int i = 0; i < latitudes.stacks; i++) {
/*  409 */       float y1 = latitudes.sin[i] * radius;
/*  410 */       float y2 = latitudes.sin[i + 1] * radius;
/*  411 */       float r1 = latitudes.cos[i] * radius;
/*  412 */       float r2 = latitudes.cos[i + 1] * radius;
/*  413 */       for (int j = 0; j < circle.segments; j++) {
/*  414 */         float c1 = circle.cos[j];
/*  415 */         float s1 = circle.sin[j];
/*  416 */         float c2 = circle.cos[j + 1];
/*  417 */         float s2 = circle.sin[j + 1];
/*      */         
/*  419 */         float x11 = cx + r1 * c1, z11 = cz + r1 * s1;
/*  420 */         float x12 = cx + r1 * c2, z12 = cz + r1 * s2;
/*  421 */         float x21 = cx + r2 * c1, z21 = cz + r2 * s1;
/*  422 */         float x22 = cx + r2 * c2, z22 = cz + r2 * s2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  431 */         buffer.method_22918(matrix, x11, cy + y1, z11).method_22915(r, g, b, a);
/*  432 */         buffer.method_22918(matrix, x21, cy + y2, z21).method_22915(r, g, b, a);
/*  433 */         buffer.method_22918(matrix, x22, cy + y2, z22).method_22915(r, g, b, a);
/*  434 */         buffer.method_22918(matrix, x12, cy + y1, z12).method_22915(r, g, b, a);
/*      */       } 
/*      */     } 
/*  437 */     class_9801 built = buffer.method_60794();
/*  438 */     if (built != null) {
/*  439 */       class_286.method_43433(built);
/*      */     }
/*      */     
/*  442 */     RenderSystem.depthMask(true);
/*  443 */     RenderSystem.enableDepthTest();
/*  444 */     RenderSystem.enableCull();
/*  445 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBillboard(class_4587 matrices, class_2960 texture, float cx, float cy, float cz, float radius, int color) {
/*  465 */     drawBillboard(matrices, texture, cx, cy, cz, radius, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBillboard(class_4587 matrices, class_2960 texture, float cx, float cy, float cz, float radius, int color, boolean depthTest) {
/*  480 */     class_310 client = class_310.method_1551();
/*  481 */     if (client == null || client.field_1773 == null || client.field_1773.method_19418() == null) {
/*      */       return;
/*      */     }
/*  484 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  485 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  486 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  487 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  488 */     float b = (color & 0xFF) / 255.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  493 */     class_4184 camera = client.field_1773.method_19418();
/*  494 */     Vector3f rightV = BILLBOARD_RIGHT;
/*  495 */     Vector3f upV = BILLBOARD_UP;
/*  496 */     Quaternionf rotation = camera.method_23767();
/*  497 */     rotation.transform(1.0F, 0.0F, 0.0F, rightV);
/*  498 */     rotation.transform(0.0F, 1.0F, 0.0F, upV);
/*      */     
/*  500 */     float rx = rightV.x * radius, ry = rightV.y * radius, rz = rightV.z * radius;
/*  501 */     float ux = upV.x * radius, uy = upV.y * radius, uz = upV.z * radius;
/*      */     
/*  503 */     RenderSystem.enableBlend();
/*  504 */     RenderSystem.blendFunc(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE);
/*      */ 
/*      */ 
/*      */     
/*  508 */     RenderSystem.disableCull();
/*  509 */     if (depthTest) {
/*  510 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  512 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  514 */     RenderSystem.depthMask(false);
/*  515 */     RenderSystem.setShaderTexture(0, texture);
/*  516 */     RenderSystem.setShader(class_10142.field_53880);
/*      */     
/*  518 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  523 */     buffer.method_22918(matrix, cx - rx - ux, cy - ry - uy, cz - rz - uz).method_22913(0.0F, 1.0F).method_22915(r, g, b, a);
/*  524 */     buffer.method_22918(matrix, cx + rx - ux, cy + ry - uy, cz + rz - uz).method_22913(1.0F, 1.0F).method_22915(r, g, b, a);
/*  525 */     buffer.method_22918(matrix, cx + rx + ux, cy + ry + uy, cz + rz + uz).method_22913(1.0F, 0.0F).method_22915(r, g, b, a);
/*  526 */     buffer.method_22918(matrix, cx - rx + ux, cy - ry + uy, cz - rz + uz).method_22913(0.0F, 0.0F).method_22915(r, g, b, a);
/*      */     
/*  528 */     class_9801 built = buffer.method_60794();
/*  529 */     if (built != null) {
/*  530 */       class_286.method_43433(built);
/*      */     }
/*      */     
/*  533 */     RenderSystem.depthMask(true);
/*  534 */     RenderSystem.enableDepthTest();
/*  535 */     RenderSystem.enableCull();
/*  536 */     RenderSystem.defaultBlendFunc();
/*  537 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void line(Matrix4f matrix, class_287 buffer, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
/*  543 */     buffer.method_22918(matrix, x1, y1, z1).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  544 */     buffer.method_22918(matrix, x2, y2, z2).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawThickFlatRing(class_4587 matrices, float centerX, float centerY, float centerZ, float radius, float thickness, int color, int segments, boolean depthTest) {
/*  569 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  570 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  571 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  572 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  573 */     float b = (color & 0xFF) / 255.0F;
/*  574 */     float inner = Math.max(0.0F, radius - thickness * 0.5F);
/*  575 */     float outer = radius + thickness * 0.5F;
/*      */     
/*  577 */     RenderSystem.enableBlend();
/*  578 */     RenderSystem.defaultBlendFunc();
/*  579 */     RenderSystem.disableCull();
/*  580 */     if (depthTest) {
/*  581 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  583 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  585 */     RenderSystem.depthMask(false);
/*  586 */     RenderSystem.setShader(class_10142.field_53876);
/*      */     
/*  588 */     CircleLut circle = circleLut(segments);
/*  589 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  590 */     for (int i = 0; i < circle.segments; i++) {
/*  591 */       float c1 = circle.cos[i], s1 = circle.sin[i];
/*  592 */       float c2 = circle.cos[i + 1], s2 = circle.sin[i + 1];
/*      */       
/*  594 */       float ix1 = centerX + c1 * inner, iz1 = centerZ + s1 * inner;
/*  595 */       float ix2 = centerX + c2 * inner, iz2 = centerZ + s2 * inner;
/*  596 */       float ox1 = centerX + c1 * outer, oz1 = centerZ + s1 * outer;
/*  597 */       float ox2 = centerX + c2 * outer, oz2 = centerZ + s2 * outer;
/*      */ 
/*      */       
/*  600 */       buffer.method_22918(matrix, ix1, centerY, iz1).method_22915(r, g, b, a);
/*  601 */       buffer.method_22918(matrix, ix2, centerY, iz2).method_22915(r, g, b, a);
/*  602 */       buffer.method_22918(matrix, ox2, centerY, oz2).method_22915(r, g, b, a);
/*  603 */       buffer.method_22918(matrix, ox1, centerY, oz1).method_22915(r, g, b, a);
/*      */     } 
/*  605 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  607 */     RenderSystem.depthMask(true);
/*  608 */     RenderSystem.enableDepthTest();
/*  609 */     RenderSystem.enableCull();
/*  610 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawFlatGlowDisc(class_4587 matrices, float centerX, float centerY, float centerZ, float radius, int centerColor, int rimColor, int segments, boolean depthTest) {
/*  631 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  632 */     float ca = (centerColor >>> 24 & 0xFF) / 255.0F;
/*  633 */     float cr = (centerColor >>> 16 & 0xFF) / 255.0F;
/*  634 */     float cg = (centerColor >>> 8 & 0xFF) / 255.0F;
/*  635 */     float cb = (centerColor & 0xFF) / 255.0F;
/*  636 */     float ra = (rimColor >>> 24 & 0xFF) / 255.0F;
/*  637 */     float rr = (rimColor >>> 16 & 0xFF) / 255.0F;
/*  638 */     float rg = (rimColor >>> 8 & 0xFF) / 255.0F;
/*  639 */     float rb = (rimColor & 0xFF) / 255.0F;
/*      */     
/*  641 */     RenderSystem.enableBlend();
/*      */ 
/*      */     
/*  644 */     RenderSystem.blendFunc(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE);
/*      */ 
/*      */ 
/*      */     
/*  648 */     RenderSystem.disableCull();
/*  649 */     if (depthTest) {
/*  650 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  652 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  654 */     RenderSystem.depthMask(false);
/*  655 */     RenderSystem.setShader(class_10142.field_53876);
/*      */     
/*  657 */     CircleLut circle = circleLut(segments);
/*  658 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27379, class_290.field_1576);
/*  659 */     for (int i = 0; i < circle.segments; i++) {
/*  660 */       float x1 = centerX + circle.cos[i] * radius;
/*  661 */       float z1 = centerZ + circle.sin[i] * radius;
/*  662 */       float x2 = centerX + circle.cos[i + 1] * radius;
/*  663 */       float z2 = centerZ + circle.sin[i + 1] * radius;
/*      */       
/*  665 */       buffer.method_22918(matrix, centerX, centerY, centerZ).method_22915(cr, cg, cb, ca);
/*  666 */       buffer.method_22918(matrix, x1, centerY, z1).method_22915(rr, rg, rb, ra);
/*  667 */       buffer.method_22918(matrix, x2, centerY, z2).method_22915(rr, rg, rb, ra);
/*      */     } 
/*  669 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  671 */     RenderSystem.depthMask(true);
/*  672 */     RenderSystem.enableDepthTest();
/*  673 */     RenderSystem.enableCull();
/*  674 */     RenderSystem.defaultBlendFunc();
/*  675 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawCylinderOutline(class_4587 matrices, float centerX, float centerY, float centerZ, float radius, float height, int color, float lineWidth, int segments) {
/*  688 */     drawCylinderOutline(matrices, centerX, centerY, centerZ, radius, height, color, lineWidth, segments, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawCylinderOutline(class_4587 matrices, float centerX, float centerY, float centerZ, float radius, float height, int color, float lineWidth, int segments, boolean depthTest) {
/*  703 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  704 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  705 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  706 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  707 */     float b = (color & 0xFF) / 255.0F;
/*      */     
/*  709 */     RenderSystem.enableBlend();
/*  710 */     RenderSystem.defaultBlendFunc();
/*  711 */     RenderSystem.disableCull();
/*  712 */     if (depthTest) {
/*  713 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  715 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  717 */     RenderSystem.depthMask(false);
/*  718 */     RenderSystem.setShader(class_10142.field_53864);
/*  719 */     RenderSystem.lineWidth(Math.max(1.0F, lineWidth));
/*      */     
/*  721 */     CircleLut circle = circleLut(segments);
/*  722 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/*  723 */     float topY = centerY + height;
/*  724 */     for (int i = 0; i < circle.segments; i++) {
/*  725 */       float x1 = centerX + circle.cos[i] * radius;
/*  726 */       float z1 = centerZ + circle.sin[i] * radius;
/*  727 */       float x2 = centerX + circle.cos[i + 1] * radius;
/*  728 */       float z2 = centerZ + circle.sin[i + 1] * radius;
/*  729 */       buffer.method_22918(matrix, x1, centerY, z1).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  730 */       buffer.method_22918(matrix, x2, centerY, z2).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  731 */       buffer.method_22918(matrix, x1, topY, z1).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  732 */       buffer.method_22918(matrix, x2, topY, z2).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*      */     } 
/*  734 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  736 */     RenderSystem.depthMask(true);
/*  737 */     RenderSystem.enableDepthTest();
/*  738 */     RenderSystem.enableCull();
/*  739 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawCircleOnFace(class_4587 matrices, float centerX, float centerY, float centerZ, float nx, float ny, float nz, float radius, int color, float lineWidth, int segments, boolean depthTest) {
/*      */     float hx, hy, hz;
/*  762 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  763 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  764 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  765 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  766 */     float b = (color & 0xFF) / 255.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  772 */     float ax = Math.abs(nx), ay = Math.abs(ny), az = Math.abs(nz);
/*      */     
/*  774 */     if (ax <= ay && ax <= az) { hx = 1.0F; hy = 0.0F; hz = 0.0F; }
/*  775 */     else if (ay <= ax && ay <= az) { hx = 0.0F; hy = 1.0F; hz = 0.0F; }
/*  776 */     else { hx = 0.0F; hy = 0.0F; hz = 1.0F; }
/*      */     
/*  778 */     float uxRaw = hy * nz - hz * ny;
/*  779 */     float uyRaw = hz * nx - hx * nz;
/*  780 */     float uzRaw = hx * ny - hy * nx;
/*  781 */     float uLen = (float)Math.sqrt((uxRaw * uxRaw + uyRaw * uyRaw + uzRaw * uzRaw));
/*  782 */     if (uLen < 1.0E-6F)
/*  783 */       return;  float ux = uxRaw / uLen, uy = uyRaw / uLen, uz = uzRaw / uLen;
/*      */     
/*  785 */     float vx = ny * uz - nz * uy;
/*  786 */     float vy = nz * ux - nx * uz;
/*  787 */     float vz = nx * uy - ny * ux;
/*      */     
/*  789 */     RenderSystem.enableBlend();
/*  790 */     RenderSystem.defaultBlendFunc();
/*  791 */     RenderSystem.disableCull();
/*  792 */     if (depthTest) {
/*  793 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  795 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  797 */     RenderSystem.depthMask(false);
/*  798 */     RenderSystem.setShader(class_10142.field_53864);
/*  799 */     RenderSystem.lineWidth(Math.max(1.0F, lineWidth));
/*      */     
/*  801 */     CircleLut circle = circleLut(segments);
/*  802 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/*  803 */     for (int i = 0; i < circle.segments; i++) {
/*  804 */       float c1 = circle.cos[i], s1 = circle.sin[i];
/*  805 */       float c2 = circle.cos[i + 1], s2 = circle.sin[i + 1];
/*      */       
/*  807 */       float p1x = centerX + (ux * c1 + vx * s1) * radius;
/*  808 */       float p1y = centerY + (uy * c1 + vy * s1) * radius;
/*  809 */       float p1z = centerZ + (uz * c1 + vz * s1) * radius;
/*  810 */       float p2x = centerX + (ux * c2 + vx * s2) * radius;
/*  811 */       float p2y = centerY + (uy * c2 + vy * s2) * radius;
/*  812 */       float p2z = centerZ + (uz * c2 + vz * s2) * radius;
/*  813 */       buffer.method_22918(matrix, p1x, p1y, p1z).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  814 */       buffer.method_22918(matrix, p2x, p2y, p2z).method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*      */     } 
/*  816 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  818 */     RenderSystem.depthMask(true);
/*  819 */     RenderSystem.enableDepthTest();
/*  820 */     RenderSystem.enableCull();
/*  821 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawThickRingOnFace(class_4587 matrices, float centerX, float centerY, float centerZ, float nx, float ny, float nz, float radius, float thickness, int color, int segments, boolean depthTest) {
/*      */     float hx, hy, hz;
/*  843 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  844 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  845 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  846 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/*  847 */     float b = (color & 0xFF) / 255.0F;
/*  848 */     float inner = Math.max(0.0F, radius - thickness * 0.5F);
/*  849 */     float outer = radius + thickness * 0.5F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  855 */     float ax = Math.abs(nx), ay = Math.abs(ny), az = Math.abs(nz);
/*      */     
/*  857 */     if (ax <= ay && ax <= az) { hx = 1.0F; hy = 0.0F; hz = 0.0F; }
/*  858 */     else if (ay <= ax && ay <= az) { hx = 0.0F; hy = 1.0F; hz = 0.0F; }
/*  859 */     else { hx = 0.0F; hy = 0.0F; hz = 1.0F; }
/*  860 */      float uxRaw = hy * nz - hz * ny;
/*  861 */     float uyRaw = hz * nx - hx * nz;
/*  862 */     float uzRaw = hx * ny - hy * nx;
/*  863 */     float uLen = (float)Math.sqrt((uxRaw * uxRaw + uyRaw * uyRaw + uzRaw * uzRaw));
/*  864 */     if (uLen < 1.0E-6F)
/*  865 */       return;  float ux = uxRaw / uLen, uy = uyRaw / uLen, uz = uzRaw / uLen;
/*  866 */     float vx = ny * uz - nz * uy;
/*  867 */     float vy = nz * ux - nx * uz;
/*  868 */     float vz = nx * uy - ny * ux;
/*      */     
/*  870 */     RenderSystem.enableBlend();
/*  871 */     RenderSystem.defaultBlendFunc();
/*  872 */     RenderSystem.disableCull();
/*  873 */     if (depthTest) {
/*  874 */       RenderSystem.enableDepthTest();
/*      */     } else {
/*  876 */       RenderSystem.disableDepthTest();
/*      */     } 
/*  878 */     RenderSystem.depthMask(false);
/*  879 */     RenderSystem.setShader(class_10142.field_53876);
/*      */     
/*  881 */     CircleLut circle = circleLut(segments);
/*  882 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  883 */     for (int i = 0; i < circle.segments; i++) {
/*  884 */       float c1 = circle.cos[i], s1 = circle.sin[i];
/*  885 */       float c2 = circle.cos[i + 1], s2 = circle.sin[i + 1];
/*      */       
/*  887 */       float ix1 = centerX + (ux * c1 + vx * s1) * inner;
/*  888 */       float iy1 = centerY + (uy * c1 + vy * s1) * inner;
/*  889 */       float iz1 = centerZ + (uz * c1 + vz * s1) * inner;
/*  890 */       float ix2 = centerX + (ux * c2 + vx * s2) * inner;
/*  891 */       float iy2 = centerY + (uy * c2 + vy * s2) * inner;
/*  892 */       float iz2 = centerZ + (uz * c2 + vz * s2) * inner;
/*  893 */       float ox1 = centerX + (ux * c1 + vx * s1) * outer;
/*  894 */       float oy1 = centerY + (uy * c1 + vy * s1) * outer;
/*  895 */       float oz1 = centerZ + (uz * c1 + vz * s1) * outer;
/*  896 */       float ox2 = centerX + (ux * c2 + vx * s2) * outer;
/*  897 */       float oy2 = centerY + (uy * c2 + vy * s2) * outer;
/*  898 */       float oz2 = centerZ + (uz * c2 + vz * s2) * outer;
/*      */       
/*  900 */       buffer.method_22918(matrix, ix1, iy1, iz1).method_22915(r, g, b, a);
/*  901 */       buffer.method_22918(matrix, ix2, iy2, iz2).method_22915(r, g, b, a);
/*  902 */       buffer.method_22918(matrix, ox2, oy2, oz2).method_22915(r, g, b, a);
/*  903 */       buffer.method_22918(matrix, ox1, oy1, oz1).method_22915(r, g, b, a);
/*      */     } 
/*  905 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  907 */     RenderSystem.depthMask(true);
/*  908 */     RenderSystem.enableDepthTest();
/*  909 */     RenderSystem.enableCull();
/*  910 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void vertexLine(class_4587 matrices, class_4588 buffer, class_243 start, class_243 end, int startColor, int endColor) {
/*  921 */     vertexLine(matrices, buffer, start.field_1352, start.field_1351, start.field_1350, end.field_1352, end.field_1351, end.field_1350, startColor, endColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void vertexLine(class_4587 matrices, class_4588 buffer, double startX, double startY, double startZ, double endX, double endY, double endZ, int startColor, int endColor) {
/*  931 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  932 */     float a1 = (startColor >>> 24 & 0xFF) / 255.0F;
/*  933 */     float r1 = (startColor >>> 16 & 0xFF) / 255.0F;
/*  934 */     float g1 = (startColor >>> 8 & 0xFF) / 255.0F;
/*  935 */     float b1 = (startColor & 0xFF) / 255.0F;
/*  936 */     float a2 = (endColor >>> 24 & 0xFF) / 255.0F;
/*  937 */     float r2 = (endColor >>> 16 & 0xFF) / 255.0F;
/*  938 */     float g2 = (endColor >>> 8 & 0xFF) / 255.0F;
/*  939 */     float b2 = (endColor & 0xFF) / 255.0F;
/*  940 */     buffer.method_22918(matrix, (float)startX, (float)startY, (float)startZ)
/*  941 */       .method_22915(r1, g1, b1, a1).method_22914(0.0F, 1.0F, 0.0F);
/*  942 */     buffer.method_22918(matrix, (float)endX, (float)endY, (float)endZ)
/*  943 */       .method_22915(r2, g2, b2, a2).method_22914(0.0F, 1.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void vertexLine(class_4587 matrices, class_4588 buffer, class_243 start, class_243 end, int color) {
/*  949 */     vertexLine(matrices, buffer, start, end, color, color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawLineWorld(class_4587 matrices, class_243 start, class_243 end, int color, float lineWidth) {
/*  958 */     class_310 client = class_310.method_1551();
/*  959 */     if (client == null || client.field_1773 == null || client.field_1773.method_19418() == null) {
/*      */       return;
/*      */     }
/*  962 */     class_243 camera = client.field_1773.method_19418().method_19326();
/*  963 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*  964 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/*  965 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/*  966 */     float gC = (color >>> 8 & 0xFF) / 255.0F;
/*  967 */     float b = (color & 0xFF) / 255.0F;
/*      */     
/*  969 */     RenderSystem.enableBlend();
/*  970 */     RenderSystem.defaultBlendFunc();
/*  971 */     RenderSystem.disableCull();
/*  972 */     RenderSystem.enableDepthTest();
/*  973 */     RenderSystem.depthMask(false);
/*  974 */     RenderSystem.setShader(class_10142.field_53864);
/*  975 */     RenderSystem.lineWidth(Math.max(1.0F, lineWidth));
/*      */     
/*  977 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/*  978 */     buffer.method_22918(matrix, (float)(start.field_1352 - camera.field_1352), (float)(start.field_1351 - camera.field_1351), (float)(start.field_1350 - camera.field_1350))
/*      */ 
/*      */       
/*  981 */       .method_22915(r, gC, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  982 */     buffer.method_22918(matrix, (float)(end.field_1352 - camera.field_1352), (float)(end.field_1351 - camera.field_1351), (float)(end.field_1350 - camera.field_1350))
/*      */ 
/*      */       
/*  985 */       .method_22915(r, gC, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*  986 */     class_286.method_43433(buffer.method_60800());
/*      */     
/*  988 */     RenderSystem.depthMask(true);
/*  989 */     RenderSystem.enableCull();
/*  990 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawPolyline(class_4587 matrices, List<class_243> points, int color, float lineWidth, boolean depthTest) {
/* 1009 */     drawPolylineOffset(matrices, points, 0.0D, 0.0D, 0.0D, color, lineWidth, depthTest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawPolylineOffset(class_4587 matrices, List<class_243> points, double offsetX, double offsetY, double offsetZ, int color, float lineWidth, boolean depthTest) {
/* 1016 */     if (!hasRenderablePolylineSegment(points)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1021 */     class_243 origin = points.get(0);
/* 1022 */     matrices.method_22903();
/* 1023 */     matrices.method_22904(origin.field_1352 + offsetX, origin.field_1351 + offsetY, origin.field_1350 + offsetZ);
/* 1024 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 1025 */     float a = (color >>> 24 & 0xFF) / 255.0F;
/* 1026 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/* 1027 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/* 1028 */     float b = (color & 0xFF) / 255.0F;
/*      */     
/* 1030 */     RenderSystem.enableBlend();
/* 1031 */     RenderSystem.defaultBlendFunc();
/* 1032 */     RenderSystem.disableCull();
/* 1033 */     if (depthTest) {
/* 1034 */       RenderSystem.enableDepthTest();
/*      */     } else {
/* 1036 */       RenderSystem.disableDepthTest();
/*      */     } 
/* 1038 */     RenderSystem.depthMask(false);
/* 1039 */     RenderSystem.setShader(class_10142.field_53864);
/* 1040 */     RenderSystem.lineWidth(Math.max(1.0F, lineWidth));
/*      */     
/* 1042 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/* 1043 */     for (int i = 0; i < points.size() - 1; i++) {
/* 1044 */       class_243 start = points.get(i);
/* 1045 */       class_243 end = points.get(i + 1);
/* 1046 */       if (isRenderablePolylineSegment(start, end)) {
/* 1047 */         buffer.method_22918(matrix, (float)(start.field_1352 - origin.field_1352), (float)(start.field_1351 - origin.field_1351), (float)(start.field_1350 - origin.field_1350))
/*      */ 
/*      */           
/* 1050 */           .method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/* 1051 */         buffer.method_22918(matrix, (float)(end.field_1352 - origin.field_1352), (float)(end.field_1351 - origin.field_1351), (float)(end.field_1350 - origin.field_1350))
/*      */ 
/*      */           
/* 1054 */           .method_22915(r, g, b, a).method_22914(0.0F, 1.0F, 0.0F);
/*      */       } 
/* 1056 */     }  class_286.method_43433(buffer.method_60800());
/*      */     
/* 1058 */     RenderSystem.depthMask(true);
/* 1059 */     RenderSystem.enableCull();
/* 1060 */     RenderSystem.enableDepthTest();
/* 1061 */     RenderSystem.disableBlend();
/* 1062 */     matrices.method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawPolylineFaded(class_4587 matrices, List<class_243> points, int color, float lineWidth, float fadeDistance, boolean depthTest) {
/* 1096 */     drawPolylineFadedOffset(matrices, points, 0.0D, 0.0D, 0.0D, color, lineWidth, fadeDistance, depthTest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawPolylineFadedOffset(class_4587 matrices, List<class_243> points, double offsetX, double offsetY, double offsetZ, int color, float lineWidth, float fadeDistance, boolean depthTest) {
/* 1105 */     if (!hasRenderablePolylineSegment(points))
/* 1106 */       return;  if (fadeDistance <= 0.0F) {
/* 1107 */       drawPolylineOffset(matrices, points, offsetX, offsetY, offsetZ, color, lineWidth, depthTest);
/*      */       return;
/*      */     } 
/* 1110 */     class_243 origin = points.get(0);
/* 1111 */     matrices.method_22903();
/* 1112 */     matrices.method_22904(origin.field_1352 + offsetX, origin.field_1351 + offsetY, origin.field_1350 + offsetZ);
/* 1113 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 1114 */     float maxA = (color >>> 24 & 0xFF) / 255.0F;
/* 1115 */     float r = (color >>> 16 & 0xFF) / 255.0F;
/* 1116 */     float g = (color >>> 8 & 0xFF) / 255.0F;
/* 1117 */     float b = (color & 0xFF) / 255.0F;
/*      */     
/* 1119 */     RenderSystem.enableBlend();
/* 1120 */     RenderSystem.defaultBlendFunc();
/* 1121 */     RenderSystem.disableCull();
/* 1122 */     if (depthTest) {
/* 1123 */       RenderSystem.enableDepthTest();
/*      */     } else {
/* 1125 */       RenderSystem.disableDepthTest();
/*      */     } 
/* 1127 */     RenderSystem.depthMask(false);
/* 1128 */     RenderSystem.setShader(class_10142.field_53864);
/* 1129 */     RenderSystem.lineWidth(Math.max(1.0F, lineWidth));
/*      */     
/* 1131 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27377, class_290.field_29337);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1138 */     double accLen = 0.0D;
/* 1139 */     for (int i = 0; i < points.size() - 1; i++) {
/* 1140 */       class_243 start = points.get(i);
/* 1141 */       class_243 end = points.get(i + 1);
/* 1142 */       if (isRenderablePolylineSegment(start, end)) {
/* 1143 */         double sx = start.field_1352 - origin.field_1352;
/* 1144 */         double sy = start.field_1351 - origin.field_1351;
/* 1145 */         double sz = start.field_1350 - origin.field_1350;
/* 1146 */         double ex = end.field_1352 - origin.field_1352;
/* 1147 */         double ey = end.field_1351 - origin.field_1351;
/* 1148 */         double ez = end.field_1350 - origin.field_1350;
/* 1149 */         double dx = ex - sx;
/* 1150 */         double dy = ey - sy;
/* 1151 */         double dz = ez - sz;
/* 1152 */         double segLen = Math.sqrt(dx * dx + dy * dy + dz * dz);
/* 1153 */         if (segLen >= 1.0E-7D)
/*      */         
/* 1155 */         { double startLen = accLen;
/* 1156 */           double endLen = accLen + segLen;
/*      */           
/* 1158 */           float startAlpha = (float)Math.min(1.0D, Math.max(0.0D, startLen / fadeDistance)) * maxA;
/* 1159 */           float endAlpha = (float)Math.min(1.0D, Math.max(0.0D, endLen / fadeDistance)) * maxA;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1164 */           if (startLen < fadeDistance && endLen > fadeDistance) {
/* 1165 */             double t = (fadeDistance - startLen) / segLen;
/* 1166 */             double mx = sx + dx * t;
/* 1167 */             double my = sy + dy * t;
/* 1168 */             double mz = sz + dz * t;
/*      */             
/* 1170 */             buffer.method_22918(matrix, (float)sx, (float)sy, (float)sz).method_22915(r, g, b, startAlpha).method_22914(0.0F, 1.0F, 0.0F);
/* 1171 */             buffer.method_22918(matrix, (float)mx, (float)my, (float)mz).method_22915(r, g, b, maxA).method_22914(0.0F, 1.0F, 0.0F);
/*      */             
/* 1173 */             buffer.method_22918(matrix, (float)mx, (float)my, (float)mz).method_22915(r, g, b, maxA).method_22914(0.0F, 1.0F, 0.0F);
/* 1174 */             buffer.method_22918(matrix, (float)ex, (float)ey, (float)ez).method_22915(r, g, b, maxA).method_22914(0.0F, 1.0F, 0.0F);
/*      */           } else {
/* 1176 */             buffer.method_22918(matrix, (float)sx, (float)sy, (float)sz).method_22915(r, g, b, startAlpha).method_22914(0.0F, 1.0F, 0.0F);
/* 1177 */             buffer.method_22918(matrix, (float)ex, (float)ey, (float)ez).method_22915(r, g, b, endAlpha).method_22914(0.0F, 1.0F, 0.0F);
/*      */           } 
/*      */           
/* 1180 */           accLen = endLen; } 
/*      */       } 
/* 1182 */     }  class_286.method_43433(buffer.method_60800());
/*      */     
/* 1184 */     RenderSystem.depthMask(true);
/* 1185 */     RenderSystem.enableCull();
/* 1186 */     RenderSystem.enableDepthTest();
/* 1187 */     RenderSystem.disableBlend();
/* 1188 */     matrices.method_22909();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean hasRenderablePolylineSegment(List<class_243> points) {
/* 1193 */     if (points == null || points.size() < 2) return false; 
/* 1194 */     for (int i = 0; i < points.size() - 1; i++) {
/* 1195 */       if (isRenderablePolylineSegment(points.get(i), points.get(i + 1))) {
/* 1196 */         return true;
/*      */       }
/*      */     } 
/* 1199 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isRenderablePolylineSegment(class_243 start, class_243 end) {
/* 1203 */     if (start == null || end == null) return false; 
/* 1204 */     double dx = end.field_1352 - start.field_1352;
/* 1205 */     double dy = end.field_1351 - start.field_1351;
/* 1206 */     double dz = end.field_1350 - start.field_1350;
/* 1207 */     return (Double.isFinite(dx) && Double.isFinite(dy) && Double.isFinite(dz) && dx * dx + dy * dy + dz * dz >= 1.0E-14D);
/*      */   }
/*      */ 
/*      */   
/*      */   private static CircleLut circleLut(int requestedSegments) {
/* 1212 */     int segments = Math.max(3, requestedSegments);
/* 1213 */     if (segments > 256) {
/* 1214 */       return new CircleLut(segments);
/*      */     }
/* 1216 */     CircleLut cached = CIRCLE_LUT_CACHE[segments];
/* 1217 */     if (cached == null) {
/* 1218 */       cached = new CircleLut(segments);
/* 1219 */       CIRCLE_LUT_CACHE[segments] = cached;
/*      */     } 
/* 1221 */     return cached;
/*      */   }
/*      */   
/*      */   private static SphereLatitudeLut sphereLatitudeLut(int requestedStacks) {
/* 1225 */     int stacks = Math.max(2, requestedStacks);
/* 1226 */     if (stacks > 64) {
/* 1227 */       return new SphereLatitudeLut(stacks);
/*      */     }
/* 1229 */     SphereLatitudeLut cached = SPHERE_LATITUDE_CACHE[stacks];
/* 1230 */     if (cached == null) {
/* 1231 */       cached = new SphereLatitudeLut(stacks);
/* 1232 */       SPHERE_LATITUDE_CACHE[stacks] = cached;
/*      */     } 
/* 1234 */     return cached;
/*      */   }
/*      */   
/*      */   private static final class CircleLut {
/*      */     private final int segments;
/*      */     private final float[] cos;
/*      */     private final float[] sin;
/*      */     
/*      */     private CircleLut(int segments) {
/* 1243 */       this.segments = segments;
/* 1244 */       this.cos = new float[segments + 1];
/* 1245 */       this.sin = new float[segments + 1];
/* 1246 */       for (int i = 0; i <= segments; i++) {
/* 1247 */         double angle = 6.283185307179586D * i / segments;
/* 1248 */         this.cos[i] = (float)Math.cos(angle);
/* 1249 */         this.sin[i] = (float)Math.sin(angle);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class SphereLatitudeLut {
/*      */     private final int stacks;
/*      */     private final float[] cos;
/*      */     private final float[] sin;
/*      */     
/*      */     private SphereLatitudeLut(int stacks) {
/* 1260 */       this.stacks = stacks;
/* 1261 */       this.cos = new float[stacks + 1];
/* 1262 */       this.sin = new float[stacks + 1];
/* 1263 */       for (int i = 0; i <= stacks; i++) {
/* 1264 */         double angle = Math.PI * i / stacks - 1.5707963267948966D;
/* 1265 */         this.cos[i] = (float)Math.cos(angle);
/* 1266 */         this.sin[i] = (float)Math.sin(angle);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\render\Render3DUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */