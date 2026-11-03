/*    */ package vorga.phazeclient.base.util.other;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.core.Main;
/*    */ 
/*    */ public final class Instance
/*    */ {
/*    */   private Instance() {
/* 11 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/* 12 */   } private static final ConcurrentMap<Class<? extends Module>, Module> instanceModules = new ConcurrentHashMap<>();
/*    */   
/*    */   public static <T extends Module> T get(Class<T> clazz) {
/* 15 */     return clazz.cast(instanceModules.computeIfAbsent(clazz, instance -> Main.getInstance().getModuleProvider().get(instance)));
/*    */   }
/*    */   
/*    */   public static <T extends Module> T get(String module) {
/* 19 */     return (T)Main.getInstance().getModuleProvider().get(module);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\other\Instance.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */