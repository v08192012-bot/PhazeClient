/*    */ package vorga.phazeclient.api.system.nametag;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ 
/*    */ public class NametagBlurStorage
/*    */ {
/* 10 */   private static final List<NametagBlurRect> blurRects = new ArrayList<>();
/*    */   private static boolean enabled = false;
/*    */   
/*    */   public static void setEnabled(boolean enabled) {
/* 14 */     NametagBlurStorage.enabled = enabled;
/* 15 */     if (!enabled) {
/* 16 */       blurRects.clear();
/*    */     }
/*    */   }
/*    */   
/*    */   public static void addBlurRect(float x, float y, float width, float height, float quality, Matrix4f matrix) {
/* 21 */     if (enabled) {
/* 22 */       blurRects.add(new NametagBlurRect(x, y, width, height, quality, new Matrix4f((Matrix4fc)matrix)));
/*    */     }
/*    */   }
/*    */   
/*    */   public static List<NametagBlurRect> getBlurRects() {
/* 27 */     return blurRects;
/*    */   }
/*    */   
/*    */   public static void clear() {
/* 31 */     blurRects.clear();
/*    */   }
/*    */   public static final class NametagBlurRect extends Record { private final float x; private final float y; private final float width; private final float height; private final float quality; private final Matrix4f matrix;
/* 34 */     public NametagBlurRect(float x, float y, float width, float height, float quality, Matrix4f matrix) { this.x = x; this.y = y; this.width = width; this.height = height; this.quality = quality; this.matrix = matrix; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/nametag/NametagBlurStorage$NametagBlurRect;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #34	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 34 */       //   0	7	0	this	Lvorga/phazeclient/api/system/nametag/NametagBlurStorage$NametagBlurRect; } public float x() { return this.x; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/nametag/NametagBlurStorage$NametagBlurRect;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #34	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lvorga/phazeclient/api/system/nametag/NametagBlurStorage$NametagBlurRect; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/nametag/NametagBlurStorage$NametagBlurRect;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #34	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lvorga/phazeclient/api/system/nametag/NametagBlurStorage$NametagBlurRect;
/* 34 */       //   0	8	1	o	Ljava/lang/Object; } public float y() { return this.y; } public float width() { return this.width; } public float height() { return this.height; } public float quality() { return this.quality; } public Matrix4f matrix() { return this.matrix; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\nametag\NametagBlurStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */