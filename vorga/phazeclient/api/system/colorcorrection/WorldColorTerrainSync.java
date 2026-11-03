/*    */ package vorga.phazeclient.api.system.colorcorrection;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WorldColorTerrainSync
/*    */ {
/*    */   public static boolean isShaderPackActive() {
/*    */     try {
/* 11 */       Class<?> irisApiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
/* 12 */       Object api = irisApiClass.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/* 13 */       if (api == null) {
/* 14 */         return false;
/*    */       }
/* 16 */       Method isShaderPackInUse = irisApiClass.getMethod("isShaderPackInUse", new Class[0]);
/* 17 */       Object value = isShaderPackInUse.invoke(api, new Object[0]);
/* 18 */       if (value instanceof Boolean) { Boolean active = (Boolean)value; if (active.booleanValue()); }  return false;
/* 19 */     } catch (Throwable ignored) {
/* 20 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean shouldUseBakedFluidColors() {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public static void requestTerrainRefreshThrottled(long minDelayMs) {}
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\WorldColorTerrainSync.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */