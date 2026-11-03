/*    */ package vorga.phazeclient.api.system.nametag;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.lwjgl.opengl.GL30;
/*    */ import org.lwjgl.system.MemoryStack;
/*    */ 
/*    */ public class NametagVBOManager
/*    */ {
/* 11 */   private static final Map<String, VBOEntry> vboCache = new HashMap<>();
/*    */   private static final int CACHE_SIZE = 64;
/*    */   
/*    */   public static class VBOEntry {
/*    */     public final int vboId;
/*    */     public final int vertexCount;
/*    */     public final long timestamp;
/*    */     
/*    */     public VBOEntry(int vboId, int vertexCount) {
/* 20 */       this.vboId = vboId;
/* 21 */       this.vertexCount = vertexCount;
/* 22 */       this.timestamp = System.currentTimeMillis();
/*    */     }
/*    */   }
/*    */   
/*    */   public static int createVBO(float[] vertices) {
/* 27 */     String key = generateKey(vertices);
/* 28 */     VBOEntry entry = vboCache.get(key);
/* 29 */     if (entry != null) {
/* 30 */       return entry.vboId;
/*    */     }
/*    */     
/* 33 */     int vboId = GL30.glGenBuffers();
/* 34 */     GL30.glBindBuffer(34962, vboId);
/*    */     
/* 36 */     MemoryStack stack = MemoryStack.stackPush(); 
/* 37 */     try { FloatBuffer buffer = stack.mallocFloat(vertices.length);
/* 38 */       buffer.put(vertices);
/* 39 */       buffer.flip();
/* 40 */       GL30.glBufferData(34962, buffer, 35044);
/* 41 */       if (stack != null) stack.close();  } catch (Throwable throwable) { if (stack != null)
/*    */         try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 43 */      GL30.glBindBuffer(34962, 0);
/*    */     
/* 45 */     if (vboCache.size() > 64)
/*    */     {
/* 47 */       vboCache.entrySet().removeIf(e -> (System.currentTimeMillis() - ((VBOEntry)e.getValue()).timestamp > 60000L));
/*    */     }
/*    */ 
/*    */     
/* 51 */     vboCache.put(key, new VBOEntry(vboId, vertices.length / 4));
/* 52 */     return vboId;
/*    */   }
/*    */   
/*    */   private static String generateKey(float[] vertices) {
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     for (float v : vertices) {
/* 58 */       sb.append(v).append(",");
/*    */     }
/* 60 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public static void deleteVBO(int vboId) {
/* 64 */     GL30.glDeleteBuffers(vboId);
/* 65 */     vboCache.entrySet().removeIf(e -> (((VBOEntry)e.getValue()).vboId == vboId));
/*    */   }
/*    */   
/*    */   public static void clear() {
/* 69 */     for (VBOEntry entry : vboCache.values()) {
/* 70 */       GL30.glDeleteBuffers(entry.vboId);
/*    */     }
/* 72 */     vboCache.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\nametag\NametagVBOManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */