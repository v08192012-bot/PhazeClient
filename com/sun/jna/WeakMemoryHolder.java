/*    */ package com.sun.jna;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.IdentityHashMap;
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
/*    */ 
/*    */ 
/*    */ public class WeakMemoryHolder
/*    */ {
/* 41 */   ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
/* 42 */   IdentityHashMap<Reference<Object>, Memory> backingMap = new IdentityHashMap<>();
/*    */   
/*    */   public synchronized void put(Object o, Memory m) {
/* 45 */     clean();
/* 46 */     Reference<Object> reference = new WeakReference(o, this.referenceQueue);
/* 47 */     this.backingMap.put(reference, m);
/*    */   }
/*    */   
/*    */   public synchronized void clean() {
/* 51 */     for (Reference<?> ref = this.referenceQueue.poll(); ref != null; ref = this.referenceQueue.poll())
/* 52 */       this.backingMap.remove(ref); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\WeakMemoryHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */