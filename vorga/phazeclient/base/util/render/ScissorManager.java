/*    */ package vorga.phazeclient.base.util.render;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Vector3f;
/*    */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*    */ import vorga.phazeclient.base.QuickImports;
/*    */ 
/*    */ 
/*    */ public class ScissorManager
/*    */   implements QuickImports
/*    */ {
/* 15 */   private final Deque<Scissor> scissorStack = new ArrayDeque<>();
/* 16 */   private final Deque<Scissor> freeScissors = new ArrayDeque<>();
/* 17 */   private final Vector3f scratchPosition = new Vector3f();
/* 18 */   private final Vector3f scratchSize = new Vector3f();
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
/*    */   public void push(Matrix4f matrix4f, float x, float y, float width, float height) {
/* 30 */     BatchedRectangle.flushIfBatching();
/*    */     
/* 32 */     Vector3f pos = matrix4f.transformPosition(x, y, 0.0F, this.scratchPosition);
/* 33 */     Vector3f size = matrix4f.getScale(this.scratchSize).mul(width, height, 0.0F);
/*    */     
/* 35 */     Scissor newScissor = this.freeScissors.pollFirst();
/* 36 */     if (newScissor == null) {
/* 37 */       newScissor = new Scissor();
/*    */     }
/*    */     
/* 40 */     if (!this.scissorStack.isEmpty()) {
/* 41 */       Scissor prevScissor = this.scissorStack.peek();
/*    */       
/* 43 */       double intersectX = Math.max(prevScissor.x, pos.x);
/* 44 */       double intersectY = Math.max(prevScissor.y, pos.y);
/* 45 */       double intersectWidth = Math.min((prevScissor.x + prevScissor.width), pos.x + size.x) - intersectX;
/* 46 */       double intersectHeight = Math.min((prevScissor.y + prevScissor.height), pos.y + size.y) - intersectY;
/*    */       
/* 48 */       newScissor.set(intersectX, intersectY, intersectWidth, intersectHeight);
/*    */     } else {
/* 50 */       newScissor.set(pos.x, pos.y, size.x, size.y);
/*    */     } 
/*    */     
/* 53 */     this.scissorStack.push(newScissor);
/* 54 */     setScissor(newScissor);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void pop() {
/* 60 */     BatchedRectangle.flushIfBatching();
/*    */     
/* 62 */     if (!this.scissorStack.isEmpty()) {
/* 63 */       this.freeScissors.addFirst(this.scissorStack.pop());
/* 64 */       if (this.scissorStack.isEmpty()) {
/* 65 */         RenderSystem.disableScissor();
/*    */       } else {
/* 67 */         setScissor(this.scissorStack.peek());
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void setScissor(Scissor scissor) {
/* 73 */     int scaleFactor = (int)window().method_4495();
/* 74 */     int x = scissor.x * scaleFactor;
/* 75 */     int y = window().method_4507() - scissor.y * scaleFactor + scissor.height * scaleFactor;
/* 76 */     int width = scissor.width * scaleFactor;
/* 77 */     int height = scissor.height * scaleFactor;
/*    */     
/* 79 */     RenderSystem.enableScissor(x, y, width, height);
/*    */   }
/*    */   
/*    */   private static class Scissor {
/*    */     public int x;
/*    */     public int y;
/*    */     
/*    */     public void set(double x, double y, double width, double height) {
/* 87 */       this.x = Math.max(0, (int)Math.round(x));
/* 88 */       this.y = Math.max(0, (int)Math.round(y));
/* 89 */       this.width = Math.max(0, (int)Math.round(width));
/* 90 */       this.height = Math.max(0, (int)Math.round(height));
/*    */     }
/*    */     public int width; public int height;
/*    */     Scissor copy() {
/* 94 */       Scissor newScissor = new Scissor();
/* 95 */       newScissor.set(this.x, this.y, this.width, this.height);
/* 96 */       return newScissor;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\ScissorManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */