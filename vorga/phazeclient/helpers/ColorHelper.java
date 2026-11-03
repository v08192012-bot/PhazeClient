/*    */ package vorga.phazeclient.helpers;
/*    */ 
/*    */ import net.minecraft.class_3532;
/*    */ 
/*    */ public class ColorHelper {
/*    */   public static int argbFromRGBA(float r, float g, float b, float a) {
/*  7 */     return class_3532.method_15357(a * 255.0D) << 24 | 
/*  8 */       class_3532.method_15357(r * 255.0D) << 16 | 
/*  9 */       class_3532.method_15357(g * 255.0D) << 8 | 
/* 10 */       class_3532.method_15357(b * 255.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\helpers\ColorHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */