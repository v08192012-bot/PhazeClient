/*    */ package vorga.phazeclient.api.system.colorcorrection;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ 
/*    */ public final class TintingVertexConsumerProvider
/*    */   implements class_4597 {
/* 12 */   private static final boolean SODIUM_LOADED = FabricLoader.getInstance().isModLoaded("sodium");
/*    */   private final class_4597 parent;
/*    */   private final WorldColorCorrectionController.Target target;
/* 15 */   private final Map<class_1921, CachedConsumer> cache = new IdentityHashMap<>();
/*    */   
/*    */   public TintingVertexConsumerProvider(class_4597 parent, WorldColorCorrectionController.Target target) {
/* 18 */     this.parent = parent;
/* 19 */     this.target = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_4588 getBuffer(class_1921 layer) {
/* 24 */     if (layer == null) {
/* 25 */       return this.parent.getBuffer(null);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 30 */     class_4588 parentConsumer = this.parent.getBuffer(layer);
/* 31 */     CachedConsumer cached = this.cache.get(layer);
/* 32 */     if (cached != null && cached.parent == parentConsumer) {
/* 33 */       return cached.tinted;
/*    */     }
/*    */     
/* 36 */     class_4588 tinted = createConsumer(parentConsumer);
/* 37 */     this.cache.put(layer, new CachedConsumer(parentConsumer, tinted));
/* 38 */     return tinted;
/*    */   }
/*    */   
/*    */   private class_4588 createConsumer(class_4588 parentConsumer) {
/* 42 */     if (SODIUM_LOADED) {
/* 43 */       class_4588 optimized = SodiumTintingVertexConsumer.create(parentConsumer, this.target);
/* 44 */       if (optimized != null) {
/* 45 */         return optimized;
/*    */       }
/*    */     } 
/* 48 */     return new TintingVertexConsumer(parentConsumer, this.target);
/*    */   }
/*    */   private static final class CachedConsumer extends Record { private final class_4588 parent; private final class_4588 tinted;
/* 51 */     private CachedConsumer(class_4588 parent, class_4588 tinted) { this.parent = parent; this.tinted = tinted; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/colorcorrection/TintingVertexConsumerProvider$CachedConsumer;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 51 */       //   0	7	0	this	Lvorga/phazeclient/api/system/colorcorrection/TintingVertexConsumerProvider$CachedConsumer; } public class_4588 parent() { return this.parent; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/colorcorrection/TintingVertexConsumerProvider$CachedConsumer;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lvorga/phazeclient/api/system/colorcorrection/TintingVertexConsumerProvider$CachedConsumer; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/colorcorrection/TintingVertexConsumerProvider$CachedConsumer;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lvorga/phazeclient/api/system/colorcorrection/TintingVertexConsumerProvider$CachedConsumer;
/* 51 */       //   0	8	1	o	Ljava/lang/Object; } public class_4588 tinted() { return this.tinted; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\TintingVertexConsumerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */