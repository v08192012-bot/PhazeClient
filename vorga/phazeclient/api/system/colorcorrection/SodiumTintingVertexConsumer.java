/*    */ package vorga.phazeclient.api.system.colorcorrection;
/*    */ 
/*    */ import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
/*    */ import net.minecraft.class_293;
/*    */ import net.minecraft.class_296;
/*    */ import net.minecraft.class_4588;
/*    */ import org.lwjgl.system.MemoryStack;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ 
/*    */ final class SodiumTintingVertexConsumer
/*    */   extends TintingVertexConsumer
/*    */   implements VertexBufferWriter
/*    */ {
/*    */   private final VertexBufferWriter writer;
/*    */   private final boolean colorTransform;
/*    */   private final boolean alphaTransform;
/*    */   
/*    */   private SodiumTintingVertexConsumer(class_4588 parent, VertexBufferWriter writer, WorldColorCorrectionController.Target target) {
/* 20 */     super(parent, target);
/* 21 */     this.writer = writer;
/* 22 */     this.colorTransform = WorldColorCorrectionController.needsColorTransform(target);
/* 23 */     this.alphaTransform = WorldColorCorrectionController.needsAlphaTransform(target);
/*    */   }
/*    */   
/*    */   static class_4588 create(class_4588 parent, WorldColorCorrectionController.Target target) {
/* 27 */     VertexBufferWriter writer = VertexBufferWriter.tryOf(parent);
/* 28 */     return (writer == null) ? null : new SodiumTintingVertexConsumer(parent, writer, target);
/*    */   }
/*    */ 
/*    */   
/*    */   public void push(MemoryStack stack, long pointer, int count, class_293 format) {
/* 33 */     int colorOffset = format.method_60835(class_296.field_52108);
/* 34 */     if ((!this.colorTransform && !this.alphaTransform) || colorOffset < 0) {
/* 35 */       this.writer.push(stack, pointer, count, format);
/*    */       
/*    */       return;
/*    */     } 
/* 39 */     int stride = format.method_1362();
/* 40 */     int bytes = Math.multiplyExact(stride, count);
/* 41 */     long transformed = stack.nmalloc(4, bytes);
/* 42 */     MemoryUtil.memCopy(pointer, transformed, bytes);
/*    */     
/* 44 */     for (int vertex = 0; vertex < count; vertex++) {
/* 45 */       long colorAddress = transformed + vertex * stride + colorOffset;
/* 46 */       int red = MemoryUtil.memGetByte(colorAddress) & 0xFF;
/* 47 */       int green = MemoryUtil.memGetByte(colorAddress + 1L) & 0xFF;
/* 48 */       int blue = MemoryUtil.memGetByte(colorAddress + 2L) & 0xFF;
/* 49 */       int alpha = MemoryUtil.memGetByte(colorAddress + 3L) & 0xFF;
/*    */       
/* 51 */       int corrected = phaze$transformArgb(alpha << 24 | red << 16 | green << 8 | blue);
/*    */ 
/*    */       
/* 54 */       red = corrected >>> 16 & 0xFF;
/* 55 */       green = corrected >>> 8 & 0xFF;
/* 56 */       blue = corrected & 0xFF;
/* 57 */       alpha = corrected >>> 24 & 0xFF;
/*    */       
/* 59 */       MemoryUtil.memPutByte(colorAddress, (byte)red);
/* 60 */       MemoryUtil.memPutByte(colorAddress + 1L, (byte)green);
/* 61 */       MemoryUtil.memPutByte(colorAddress + 2L, (byte)blue);
/* 62 */       MemoryUtil.memPutByte(colorAddress + 3L, (byte)alpha);
/*    */     } 
/*    */     
/* 65 */     this.writer.push(stack, transformed, count, format);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\SodiumTintingVertexConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */