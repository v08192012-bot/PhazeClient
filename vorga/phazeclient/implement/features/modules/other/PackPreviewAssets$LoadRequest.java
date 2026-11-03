/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3262;
/*     */ import net.minecraft.class_3268;
/*     */ import net.minecraft.class_3288;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LoadRequest
/*     */   extends Record
/*     */ {
/*     */   private final class_310 client;
/*     */   private final String packId;
/*     */   private final class_3288 profile;
/*     */   private final class_3268 vanilla;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #79	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #79	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #79	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/PackPreviewAssets$LoadRequest;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   LoadRequest(class_310 client, String packId, class_3288 profile, class_3268 vanilla) {
/*  79 */     this.client = client; this.packId = packId; this.profile = profile; this.vanilla = vanilla; } public class_310 client() { return this.client; } public String packId() { return this.packId; } public class_3288 profile() { return this.profile; } public class_3268 vanilla() { return this.vanilla; }
/*     */    PackPreviewAssets.Prepared decode(BooleanSupplier cancelled) {
/*  81 */     PackPreviewAssets.Loader loader = new PackPreviewAssets.Loader(this.client, this.packId, this.vanilla, cancelled);
/*  82 */     class_3262 pack = null;
/*     */     try {
/*  84 */       loader.checkCancelled();
/*  85 */       if (this.profile != null) {
/*     */         try {
/*  87 */           pack = this.profile.method_14458();
/*  88 */         } catch (CancellationException e) {
/*  89 */           throw e;
/*  90 */         } catch (RuntimeException e) {
/*  91 */           loader.note("Could not open inspected profile: " + e.getClass().getSimpleName());
/*     */         } 
/*     */       } else {
/*  94 */         loader.note("Profile not found; using vanilla assets");
/*     */       } 
/*  96 */       loader.pack = pack;
/*  97 */       return loader.load();
/*  98 */     } catch (RuntimeException|Error e) {
/*  99 */       loader.destroy();
/* 100 */       throw e;
/*     */     } finally {
/* 102 */       if (pack != null && pack != this.vanilla)
/*     */         try {
/* 104 */           pack.close();
/* 105 */         } catch (RuntimeException runtimeException) {} 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewAssets$LoadRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */