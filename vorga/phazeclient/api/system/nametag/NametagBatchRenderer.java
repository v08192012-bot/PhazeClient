/*    */ package vorga.phazeclient.api.system.nametag;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ 
/*    */ public class NametagBatchRenderer {
/*  9 */   private static final List<NametagBatchEntry> batchEntries = new ArrayList<>();
/*    */   private static boolean batchingEnabled = false;
/*    */   
/*    */   public static class NametagBatchEntry {
/*    */     public final float x;
/*    */     public final float y;
/*    */     public final float width;
/*    */     public final float height;
/*    */     public final int color;
/*    */     public final Matrix4f matrix;
/*    */     public final float quality;
/*    */     
/*    */     public NametagBatchEntry(float x, float y, float width, float height, int color, Matrix4f matrix, float quality) {
/* 22 */       this.x = x;
/* 23 */       this.y = y;
/* 24 */       this.width = width;
/* 25 */       this.height = height;
/* 26 */       this.color = color;
/* 27 */       this.matrix = new Matrix4f((Matrix4fc)matrix);
/* 28 */       this.quality = quality;
/*    */     }
/*    */   }
/*    */   
/*    */   public static void startBatching() {
/* 33 */     batchingEnabled = true;
/* 34 */     batchEntries.clear();
/*    */   }
/*    */   
/*    */   public static void endBatching() {
/* 38 */     batchingEnabled = false;
/*    */   }
/*    */   
/*    */   public static void addBlurRect(float x, float y, float width, float height, int color, Matrix4f matrix, float quality) {
/* 42 */     if (batchingEnabled) {
/* 43 */       batchEntries.add(new NametagBatchEntry(x, y, width, height, color, matrix, quality));
/*    */     }
/*    */   }
/*    */   
/*    */   public static List<NametagBatchEntry> getBatchEntries() {
/* 48 */     return batchEntries;
/*    */   }
/*    */   
/*    */   public static boolean isBatchingEnabled() {
/* 52 */     return batchingEnabled;
/*    */   }
/*    */   
/*    */   public static void clear() {
/* 56 */     batchEntries.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\nametag\NametagBatchRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */