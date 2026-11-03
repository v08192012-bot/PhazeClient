/*    */ package vorga.phazeclient.api.system.nametag;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VBOEntry
/*    */ {
/*    */   public final int vboId;
/*    */   public final int vertexCount;
/*    */   public final long timestamp;
/*    */   
/*    */   public VBOEntry(int vboId, int vertexCount) {
/* 20 */     this.vboId = vboId;
/* 21 */     this.vertexCount = vertexCount;
/* 22 */     this.timestamp = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\nametag\NametagVBOManager$VBOEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */