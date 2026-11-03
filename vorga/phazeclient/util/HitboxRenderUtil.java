/*     */ package vorga.phazeclient.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitboxCustomizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HitboxRenderUtil
/*     */ {
/*     */   public static void drawBox(class_4587 matrices, class_4588 vertices, class_1297 entity, float yaw) {
/*  24 */     HitboxCustomizer module = HitboxCustomizer.getInstance();
/*  25 */     if (!module.isEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  30 */     class_238 box = entity.method_5829().method_989(-entity.method_23317(), -entity.method_23318(), -entity.method_23321());
/*     */ 
/*     */     
/*  33 */     Color hitboxColor = getCustomColor();
/*     */ 
/*     */     
/*  36 */     float red = hitboxColor.getRed() / 255.0F;
/*  37 */     float green = hitboxColor.getGreen() / 255.0F;
/*  38 */     float blue = hitboxColor.getBlue() / 255.0F;
/*  39 */     float alpha = 1.0F;
/*     */ 
/*     */     
/*  42 */     renderThickBox(matrices, vertices, box, red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCustomBox(class_4587 matrices, class_4588 vertices, class_238 box, float red, float green, float blue, float alpha) {
/*  49 */     HitboxCustomizer module = HitboxCustomizer.getInstance();
/*  50 */     if (!module.isEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  55 */     Color hitboxColor = getCustomColor();
/*     */ 
/*     */     
/*  58 */     float customRed = hitboxColor.getRed() / 255.0F;
/*  59 */     float customGreen = hitboxColor.getGreen() / 255.0F;
/*  60 */     float customBlue = hitboxColor.getBlue() / 255.0F;
/*  61 */     float customAlpha = (hitboxColor.getAlpha() & 0xFF) / 255.0F;
/*     */ 
/*     */     
/*  64 */     renderThickBox(matrices, vertices, box, customRed, customGreen, customBlue, customAlpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Color getCustomColor() {
/*  71 */     HitboxCustomizer module = HitboxCustomizer.getInstance();
/*  72 */     int colorInt = module.getHitboxColor();
/*     */ 
/*     */     
/*  75 */     int alpha = colorInt >> 24 & 0xFF;
/*  76 */     int red = colorInt >> 16 & 0xFF;
/*  77 */     int green = colorInt >> 8 & 0xFF;
/*  78 */     int blue = colorInt & 0xFF;
/*     */     
/*  80 */     return new Color(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderThickBox(class_4587 matrices, class_4588 vertices, class_238 box, float red, float green, float blue, float alpha) {
/*  87 */     Matrix4f matrix = matrices.method_23760().method_23761();
/*     */ 
/*     */     
/*  90 */     renderSingleBox(vertices, matrix, box, red, green, blue, alpha);
/*     */ 
/*     */     
/*  93 */     double offset = 0.001D;
/*  94 */     class_238 thickBox = box.method_1014(offset);
/*  95 */     renderSingleBox(vertices, matrix, thickBox, red, green, blue, alpha * 0.8F);
/*     */ 
/*     */     
/*  98 */     class_238 thickerBox = box.method_1014(offset * 2.0D);
/*  99 */     renderSingleBox(vertices, matrix, thickerBox, red, green, blue, alpha * 0.6F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderSingleBox(class_4588 vertices, Matrix4f matrix, class_238 box, float red, float green, float blue, float alpha) {
/* 108 */     line(vertices, matrix, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1322, (float)box.field_1321, red, green, blue, alpha);
/* 109 */     line(vertices, matrix, (float)box.field_1320, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1322, (float)box.field_1324, red, green, blue, alpha);
/* 110 */     line(vertices, matrix, (float)box.field_1320, (float)box.field_1322, (float)box.field_1324, (float)box.field_1323, (float)box.field_1322, (float)box.field_1324, red, green, blue, alpha);
/* 111 */     line(vertices, matrix, (float)box.field_1323, (float)box.field_1322, (float)box.field_1324, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, red, green, blue, alpha);
/*     */ 
/*     */     
/* 114 */     line(vertices, matrix, (float)box.field_1323, (float)box.field_1325, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1321, red, green, blue, alpha);
/* 115 */     line(vertices, matrix, (float)box.field_1320, (float)box.field_1325, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, red, green, blue, alpha);
/* 116 */     line(vertices, matrix, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, (float)box.field_1323, (float)box.field_1325, (float)box.field_1324, red, green, blue, alpha);
/* 117 */     line(vertices, matrix, (float)box.field_1323, (float)box.field_1325, (float)box.field_1324, (float)box.field_1323, (float)box.field_1325, (float)box.field_1321, red, green, blue, alpha);
/*     */ 
/*     */     
/* 120 */     line(vertices, matrix, (float)box.field_1323, (float)box.field_1322, (float)box.field_1321, (float)box.field_1323, (float)box.field_1325, (float)box.field_1321, red, green, blue, alpha);
/* 121 */     line(vertices, matrix, (float)box.field_1320, (float)box.field_1322, (float)box.field_1321, (float)box.field_1320, (float)box.field_1325, (float)box.field_1321, red, green, blue, alpha);
/* 122 */     line(vertices, matrix, (float)box.field_1320, (float)box.field_1322, (float)box.field_1324, (float)box.field_1320, (float)box.field_1325, (float)box.field_1324, red, green, blue, alpha);
/* 123 */     line(vertices, matrix, (float)box.field_1323, (float)box.field_1322, (float)box.field_1324, (float)box.field_1323, (float)box.field_1325, (float)box.field_1324, red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void line(class_4588 vertexConsumer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float red, float green, float blue, float alpha) {
/* 130 */     vertexConsumer.method_22918(matrix, x1, y1, z1).method_22915(red, green, blue, alpha).method_22914(0.0F, 1.0F, 0.0F);
/* 131 */     vertexConsumer.method_22918(matrix, x2, y2, z2).method_22915(red, green, blue, alpha).method_22914(0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclien\\util\HitboxRenderUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */