/*    */ package vorga.phazeclient.api.system.nametag;
/*    */ 
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NametagBatchEntry
/*    */ {
/*    */   public final float x;
/*    */   public final float y;
/*    */   public final float width;
/*    */   public final float height;
/*    */   public final int color;
/*    */   public final Matrix4f matrix;
/*    */   public final float quality;
/*    */   
/*    */   public NametagBatchEntry(float x, float y, float width, float height, int color, Matrix4f matrix, float quality) {
/* 22 */     this.x = x;
/* 23 */     this.y = y;
/* 24 */     this.width = width;
/* 25 */     this.height = height;
/* 26 */     this.color = color;
/* 27 */     this.matrix = new Matrix4f((Matrix4fc)matrix);
/* 28 */     this.quality = quality;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\nametag\NametagBatchRenderer$NametagBatchEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */