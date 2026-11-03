/*    */ package vorga.phazeclient.api.system.font.msdf;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_3298;
/*    */ import net.minecraft.class_3300;
/*    */ 
/*    */ public final class ResourceProvider
/*    */ {
/* 15 */   private static final Gson GSON = new Gson();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class_3300 resourceManager() {
/* 21 */     return class_310.method_1551().method_1478();
/*    */   }
/*    */   
/*    */   public static <T> T fromJsonToInstance(class_2960 identifier, Class<T> clazz) {
/* 25 */     return (T)GSON.fromJson(toString(identifier), clazz);
/*    */   }
/*    */   
/*    */   public static String toString(class_2960 identifier) {
/*    */     try {
/* 30 */       Optional<class_3298> resource = resourceManager().method_14486(identifier);
/* 31 */       if (resource.isPresent()) {
/* 32 */         BufferedReader reader = new BufferedReader(new InputStreamReader(((class_3298)resource.get()).method_14482(), StandardCharsets.UTF_8)); 
/* 33 */         try { StringBuilder builder = new StringBuilder();
/*    */           String line;
/* 35 */           while ((line = reader.readLine()) != null) {
/* 36 */             builder.append(line).append('\n');
/*    */           }
/* 38 */           String str1 = builder.toString();
/* 39 */           reader.close(); return str1; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; } 
/*    */       } 
/* 41 */     } catch (Exception e) {
/* 42 */       throw new RuntimeException("Failed to read resource: " + String.valueOf(identifier), e);
/*    */     } 
/*    */     
/* 45 */     throw new RuntimeException("Resource not found: " + String.valueOf(identifier));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\ResourceProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */