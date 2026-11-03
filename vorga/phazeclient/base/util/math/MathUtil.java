/*     */ package vorga.phazeclient.base.util.math;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_9779;
/*     */ import net.minecraft.class_9848;
/*     */ import org.joml.Vector3d;
/*     */ 
/*     */ 
/*     */ public final class MathUtil
/*     */ {
/*     */   private MathUtil() {
/*  18 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*  19 */   } public static double PI2 = 6.283185307179586D;
/*     */   
/*     */   public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
/*  22 */     return (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height);
/*     */   }
/*     */   
/*     */   public static class_243 closestPointToBox(class_238 box) {
/*  26 */     class_310 client = class_310.method_1551();
/*  27 */     if (client == null || client.field_1724 == null || client.method_61966() == null) {
/*  28 */       return box.method_1005();
/*     */     }
/*     */     
/*  31 */     class_243 eye = client.field_1724.method_5836(client.method_61966().method_60637(true));
/*  32 */     return new class_243(Math.min(Math.max(eye.field_1352, box.field_1323), box.field_1320), Math.min(Math.max(eye.field_1351, box.field_1322), box.field_1325), Math.min(Math.max(eye.field_1350, box.field_1321), box.field_1324));
/*     */   }
/*     */   
/*     */   public static void scale(class_4587 stack, float x, float y, float scale, Runnable data) {
/*  36 */     if (scale != 1.0F) {
/*  37 */       float scale2 = 0.5F + scale / 2.0F;
/*  38 */       stack.method_22903();
/*  39 */       stack.method_46416(x, y, 0.0F);
/*  40 */       stack.method_22905(scale2, scale2, 1.0F);
/*  41 */       stack.method_46416(-x, -y, 0.0F);
/*  42 */       setAlpha(scale, data);
/*  43 */       stack.method_22909();
/*     */     } else {
/*  45 */       data.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void scale(class_4587 stack, float x, float y, float scaleX, float scaleY, Runnable data) {
/*  50 */     float sumScale = scaleX * scaleY;
/*  51 */     if (sumScale != 1.0F) {
/*  52 */       stack.method_22903();
/*  53 */       stack.method_46416(x, y, 0.0F);
/*  54 */       stack.method_22905(scaleX, scaleY, 1.0F);
/*  55 */       stack.method_46416(-x, -y, 0.0F);
/*  56 */       setAlpha(sumScale, data);
/*  57 */       stack.method_22909();
/*     */     } else {
/*  59 */       data.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float blinking(double speed, float f) {
/*  64 */     float red = (float)(System.currentTimeMillis() % speed / speed / f);
/*  65 */     if (red > f / 2.0F) red = f - red; 
/*  66 */     return red;
/*     */   }
/*     */   
/*     */   public static float textScrolling(float textWidth) {
/*  70 */     int speed = (int)(textWidth * 75.0F);
/*  71 */     return (float)class_3532.method_15350((System.currentTimeMillis() % speed) * Math.PI / speed, 0.0D, 1.0D) * textWidth;
/*     */   }
/*     */   
/*     */   public static void setAlpha(float alpha, Runnable data) {
/*  75 */     setColor(1.0F, 1.0F, 1.0F, alpha, data);
/*     */   }
/*     */   
/*     */   public static void setColor(float red, float green, float blue, float alpha, Runnable data) {
/*  79 */     RenderSystem.setShaderColor(class_3532.method_15363(red, 0.0F, 1.0F), class_3532.method_15363(green, 0.0F, 1.0F), class_3532.method_15363(blue, 0.0F, 1.0F), class_3532.method_15363(alpha, 0.0F, 1.0F));
/*  80 */     data.run();
/*  81 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static double round(double num, double increment) {
/*  85 */     double rounded = Math.round(num / increment) * increment;
/*  86 */     return Math.round(rounded * 100.0D) / 100.0D;
/*     */   }
/*     */   
/*     */   public static int floorNearestMulN(int x, int n) {
/*  90 */     return n * (int)Math.floor(x / n);
/*     */   }
/*     */   
/*     */   public static int getRed(int hex) {
/*  94 */     return hex >> 16 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getGreen(int hex) {
/*  98 */     return hex >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getBlue(int hex) {
/* 102 */     return hex & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getAlpha(int hex) {
/* 106 */     return hex >> 24 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int applyOpacity(int color, float opacity) {
/* 110 */     return class_9848.method_61324((int)(getAlpha(color) * opacity / 255.0F), getRed(color), getGreen(color), getBlue(color));
/*     */   }
/*     */   
/*     */   public static class_243 cosSin(int i, int size, double width) {
/* 114 */     int index = Math.min(i, size);
/* 115 */     float cos = (float)(Math.cos(index * PI2 / size) * width);
/* 116 */     float sin = (float)(-Math.sin(index * PI2 / size) * width);
/* 117 */     return new class_243(cos, 0.0D, sin);
/*     */   }
/*     */   
/*     */   public static double absSinAnimation(double input) {
/* 121 */     return Math.abs(1.0D + Math.sin(input)) / 2.0D;
/*     */   }
/*     */   
/*     */   public static Vector3d interpolate(Vector3d prevPos, Vector3d pos) {
/* 125 */     return new Vector3d(interpolate(prevPos.x, pos.x), interpolate(prevPos.y, pos.y), interpolate(prevPos.z, pos.z));
/*     */   }
/*     */   
/*     */   public static class_243 interpolate(class_243 prevPos, class_243 pos) {
/* 129 */     return new class_243(interpolate(prevPos.field_1352, pos.field_1352), interpolate(prevPos.field_1351, pos.field_1351), interpolate(prevPos.field_1350, pos.field_1350));
/*     */   }
/*     */   
/*     */   public static class_243 interpolate(class_1297 entity) {
/* 133 */     if (entity == null) return class_243.field_1353; 
/* 134 */     return new class_243(interpolate(entity.field_6014, entity.method_23317()), interpolate(entity.field_6036, entity.method_23318()), interpolate(entity.field_5969, entity.method_23321()));
/*     */   }
/*     */   
/*     */   public static float interpolate(float prev, float orig) {
/* 138 */     class_9779 tickCounter = currentTickCounter();
/* 139 */     return (tickCounter != null) ? class_3532.method_16439(tickCounter.method_60637(false), prev, orig) : orig;
/*     */   }
/*     */   
/*     */   public static double interpolate(double prev, double orig) {
/* 143 */     class_9779 tickCounter = currentTickCounter();
/* 144 */     return (tickCounter != null) ? class_3532.method_16436(tickCounter.method_60637(false), prev, orig) : orig;
/*     */   }
/*     */   
/*     */   public static float interpolateSmooth(double smooth, float prev, float orig) {
/* 148 */     class_9779 tickCounter = currentTickCounter();
/* 149 */     return (tickCounter != null) ? (float)class_3532.method_16436(tickCounter.method_60638() / smooth, prev, orig) : orig;
/*     */   }
/*     */   
/*     */   public static double interpolateSmooth(double smooth, double prev, double orig) {
/* 153 */     class_9779 tickCounter = currentTickCounter();
/* 154 */     return (tickCounter != null) ? class_3532.method_16436(tickCounter.method_60638() / smooth, prev, orig) : orig;
/*     */   }
/*     */   
/*     */   private static class_9779 currentTickCounter() {
/* 158 */     class_310 client = class_310.method_1551();
/* 159 */     return (client != null) ? client.method_61966() : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\math\MathUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */