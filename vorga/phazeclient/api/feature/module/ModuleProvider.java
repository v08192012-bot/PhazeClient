/*    */ package vorga.phazeclient.api.feature.module;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class ModuleProvider
/*    */ {
/*    */   private final List<Module> modules;
/*    */   
/*    */   public ModuleProvider(List<Module> modules) {
/* 11 */     this.modules = modules;
/*    */   }
/*    */   public List<Module> getModules() {
/* 14 */     return this.modules;
/*    */   }
/*    */   public <T extends Module> T get(String name) {
/* 17 */     return (T)this.modules.stream()
/* 18 */       .filter(module -> module.getIdentifier().equalsIgnoreCase(name))
/* 19 */       .map(module -> module)
/* 20 */       .findFirst()
/* 21 */       .orElse(null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends Module> T get(Class<T> clazz) {
/* 27 */     Objects.requireNonNull(clazz); return (T)this.modules.stream().filter(module -> clazz.isAssignableFrom(module.getClass())).map(clazz::cast)
/* 28 */       .findFirst()
/* 29 */       .orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\ModuleProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */