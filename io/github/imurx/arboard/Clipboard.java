/*    */ package io.github.imurx.arboard;
/*    */ 
/*    */ import com.nativeutils.NativeUtils;
/*    */ import java.io.IOException;
/*    */ import java.lang.ref.Cleaner;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Clipboard
/*    */   implements AutoCloseable {
/* 11 */   protected static final Cleaner cleaner = Cleaner.create();
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
/* 22 */   private static final Map<String, String> ARCHS = new HashMap<>(); static {
/* 23 */     ARCHS.put("aarch64", "aarch64");
/* 24 */     ARCHS.put("amd64", "x86_64");
/* 25 */     ARCHS.put("x86_64", "x86_64");
/*    */     
/*    */     try {
/* 28 */       NativeUtils.loadLibraryFromJar("/natives/" + (String)ARCHS.get(System.getProperty("os.arch").toLowerCase()) + "_" + System.mapLibraryName("arboard_java"));
/* 29 */     } catch (IOException e1) {
/* 30 */       e1.printStackTrace();
/*    */       try {
/* 32 */         NativeUtils.loadLibraryFromJar("/natives/" + System.mapLibraryName("arboard_java"));
/* 33 */       } catch (IOException e2) {
/* 34 */         throw new RuntimeException(e1);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private final Cleaner.Cleanable cleanable;
/*    */   
/* 43 */   private final long ptr = clipboardNew(); public Clipboard() {
/* 44 */     long ptr = this.ptr;
/* 45 */     this.cleanable = cleaner.register(this, () -> clipboardDrop(ptr));
/*    */   }
/*    */   
/*    */   public String getText() {
/* 49 */     return clipboardGetText(this.ptr);
/*    */   }
/*    */   
/*    */   public void setText(String input) {
/* 53 */     clipboardSetText(this.ptr, input);
/*    */   }
/*    */   
/*    */   public ImageData getImage() {
/* 57 */     return new ImageData(clipboardGetImage(this.ptr));
/*    */   }
/*    */   
/*    */   public void setImage(ImageData image) {
/* 61 */     clipboardSetImage(this.ptr, image.ptr);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 66 */     this.cleanable.clean();
/*    */   }
/*    */   
/*    */   private static native long clipboardNew();
/*    */   
/*    */   private static native String clipboardGetText(long paramLong);
/*    */   
/*    */   private static native void clipboardSetText(long paramLong, String paramString);
/*    */   
/*    */   private static native long clipboardGetImage(long paramLong);
/*    */   
/*    */   private static native void clipboardSetImage(long paramLong1, long paramLong2);
/*    */   
/*    */   private static native void clipboardDrop(long paramLong);
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\io\github\imurx\arboard\Clipboard.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */