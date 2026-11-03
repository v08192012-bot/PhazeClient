/*    */ package vorga.phazeclient.implement.hitcolor;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface OverlayReloadListener
/*    */ {
/* 22 */   public static final List<OverlayReloadListener> listeners = new CopyOnWriteArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   void setColor();
/*    */ 
/*    */ 
/*    */   
/*    */   static void registerOverlay(OverlayReloadListener listener) {
/* 31 */     if (listener == null || listeners.contains(listener)) {
/*    */       return;
/*    */     }
/* 34 */     listeners.add(listener);
/*    */   }
/*    */   
/*    */   static void unregisterOverlay(OverlayReloadListener listener) {
/* 38 */     if (listener != null) {
/* 39 */       listeners.remove(listener);
/*    */     }
/*    */   }
/*    */   
/*    */   static void event() {
/* 44 */     for (int i = 0, n = listeners.size(); i < n; i++)
/* 45 */       ((OverlayReloadListener)listeners.get(i)).setColor(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\hitcolor\OverlayReloadListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */