/*    */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import org.joml.Quaternionf;
/*    */ import org.joml.Vector3f;
/*    */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoBone;
/*    */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoCube;
/*    */ 
/*    */ public class GeckoRenderHelper {
/* 10 */   private static final Vector3f POSITIVE_X = new Vector3f(1.0F, 0.0F, 0.0F);
/* 11 */   private static final Vector3f POSITIVE_Y = new Vector3f(0.0F, 1.0F, 0.0F);
/* 12 */   private static final Vector3f POSITIVE_Z = new Vector3f(0.0F, 0.0F, 1.0F);
/*    */   
/*    */   public static void translate(GeoBone var0, class_4587 var1) {
/* 15 */     var1.method_46416(-var0.getPositionX() / 16.0F, var0.getPositionY() / 16.0F, var0.getPositionZ() / 16.0F);
/*    */   }
/*    */   
/*    */   public static void moveToPivot(GeoBone var0, class_4587 var1) {
/* 19 */     var1.method_46416(var0.getPivotX() / 16.0F, var0.getPivotY() / 16.0F, var0.getPivotZ() / 16.0F);
/*    */   }
/*    */   
/*    */   public static void moveBackFromPivot(GeoBone var0, class_4587 var1) {
/* 23 */     var1.method_46416(-var0.getPivotX() / 16.0F, -var0.getPivotY() / 16.0F, -var0.getPivotZ() / 16.0F);
/*    */   }
/*    */   
/*    */   public static void rotate(GeoBone var0, class_4587 var1) {
/* 27 */     if (var0.getRotationZ() != 0.0F) {
/* 28 */       var1.method_22907(getRadialQuaternion(POSITIVE_Z, var0.getRotationZ()));
/*    */     }
/*    */     
/* 31 */     if (var0.getRotationY() != 0.0F) {
/* 32 */       var1.method_22907(getRadialQuaternion(POSITIVE_Y, var0.getRotationY()));
/*    */     }
/*    */     
/* 35 */     if (var0.getRotationX() != 0.0F) {
/* 36 */       var1.method_22907(getRadialQuaternion(POSITIVE_X, var0.getRotationX()));
/*    */     }
/*    */   }
/*    */   
/*    */   public static void scale(GeoBone var0, class_4587 var1) {
/* 41 */     var1.method_22905(var0.getScaleX(), var0.getScaleY(), var0.getScaleZ());
/*    */   }
/*    */   
/*    */   public static void moveToPivot(GeoCube var0, class_4587 var1) {
/* 45 */     var1.method_46416(var0.pivot.getX() / 16.0F, var0.pivot.getY() / 16.0F, var0.pivot.getZ() / 16.0F);
/*    */   }
/*    */   
/*    */   public static void moveBackFromPivot(GeoCube var0, class_4587 var1) {
/* 49 */     var1.method_46416(-var0.pivot.getX() / 16.0F, -var0.pivot.getY() / 16.0F, -var0.pivot.getZ() / 16.0F);
/*    */   }
/*    */   
/*    */   public static void rotate(GeoCube var0, class_4587 var1) {
/* 53 */     if (var0.rotation.getZ() != 0.0F) {
/* 54 */       var1.method_22907(getRadialQuaternion(POSITIVE_Z, var0.rotation.getZ()));
/*    */     }
/*    */     
/* 57 */     if (var0.rotation.getY() != 0.0F) {
/* 58 */       var1.method_22907(getRadialQuaternion(POSITIVE_Y, var0.rotation.getY()));
/*    */     }
/*    */     
/* 61 */     if (var0.rotation.getX() != 0.0F) {
/* 62 */       var1.method_22907(getRadialQuaternion(POSITIVE_X, var0.rotation.getX()));
/*    */     }
/*    */   }
/*    */   
/*    */   public static Quaternionf getRadialQuaternion(Vector3f var0, float var1) {
/* 67 */     float var2 = (float)Math.sin((var1 / 2.0F));
/* 68 */     float var3 = var0.x() * var2;
/* 69 */     float var4 = var0.y() * var2;
/* 70 */     float var5 = var0.z() * var2;
/* 71 */     float var6 = (float)Math.cos((var1 / 2.0F));
/* 72 */     return new Quaternionf(var3, var4, var5, var6);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\GeckoRenderHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */