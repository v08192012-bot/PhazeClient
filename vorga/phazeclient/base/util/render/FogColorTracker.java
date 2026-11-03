/*    */ package vorga.phazeclient.base.util.render;
/*    */ 
/*    */ import org.joml.Vector4f;
/*    */ 
/*    */ public final class FogColorTracker {
/*  6 */   private static volatile float r = 0.7F;
/*  7 */   private static volatile float g = 0.81F;
/*  8 */   private static volatile float b = 0.99F;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void update(Vector4f color) {
/* 14 */     if (color == null)
/* 15 */       return;  r = clamp01(color.x);
/* 16 */     g = clamp01(color.y);
/* 17 */     b = clamp01(color.z);
/*    */   }
/*    */   
/*    */   public static float red() {
/* 21 */     return r;
/*    */   }
/*    */   
/*    */   public static float green() {
/* 25 */     return g;
/*    */   }
/*    */   
/*    */   public static float blue() {
/* 29 */     return b;
/*    */   }
/*    */   
/*    */   private static float clamp01(float v) {
/* 33 */     if (v < 0.0F) return 0.0F; 
/* 34 */     if (v > 1.0F) return 1.0F; 
/* 35 */     return v;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\FogColorTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */