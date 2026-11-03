/*    */ package io.github.imurx.arboard;
/*    */ 
/*    */ import java.lang.ref.Cleaner;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageData
/*    */   implements AutoCloseable
/*    */ {
/*    */   private final Cleaner.Cleanable cleanable;
/*    */   protected final long ptr;
/*    */   
/*    */   public ImageData(int width, int height, byte[] image) {
/* 14 */     this(imageDataNew(width, height, image));
/*    */   }
/*    */   
/*    */   protected ImageData(long ptr) {
/* 18 */     this.ptr = ptr;
/* 19 */     this.cleanable = Clipboard.cleaner.register(this, () -> imageDataDrop(ptr));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getImage() {
/* 25 */     return imageDataGetBytes(this.ptr);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 30 */     this.cleanable.clean();
/*    */   }
/*    */   
/*    */   private static native long imageDataNew(int paramInt1, int paramInt2, byte[] paramArrayOfbyte);
/*    */   
/*    */   private static native byte[] imageDataGetBytes(long paramLong);
/*    */   
/*    */   private static native void imageDataDrop(long paramLong);
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\io\github\imurx\arboard\ImageData.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */