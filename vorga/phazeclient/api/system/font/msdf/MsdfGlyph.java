/*    */ package vorga.phazeclient.api.system.font.msdf;
/*    */ 
/*    */ import net.minecraft.class_4588;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public final class MsdfGlyph {
/*    */   private final float minU;
/*    */   private final float maxU;
/*    */   private final float minV;
/*    */   private final float maxV;
/*    */   private final float advance;
/*    */   private final float topPosition;
/*    */   private final float width;
/*    */   private final float height;
/*    */   
/*    */   public MsdfGlyph(FontData.GlyphData data, float atlasWidth, float atlasHeight) {
/* 17 */     this.advance = data.advance();
/*    */     
/* 19 */     FontData.BoundsData atlasBounds = data.atlasBounds();
/* 20 */     if (atlasBounds != null) {
/* 21 */       this.minU = atlasBounds.left() / atlasWidth;
/* 22 */       this.maxU = atlasBounds.right() / atlasWidth;
/* 23 */       this.minV = 1.0F - atlasBounds.top() / atlasHeight;
/* 24 */       this.maxV = 1.0F - atlasBounds.bottom() / atlasHeight;
/*    */     } else {
/* 26 */       this.minU = this.maxU = this.minV = this.maxV = 0.0F;
/*    */     } 
/*    */     
/* 29 */     FontData.BoundsData planeBounds = data.planeBounds();
/* 30 */     if (planeBounds != null) {
/* 31 */       this.width = planeBounds.right() - planeBounds.left();
/* 32 */       this.height = planeBounds.top() - planeBounds.bottom();
/* 33 */       this.topPosition = planeBounds.top();
/*    */     } else {
/* 35 */       this.width = this.height = this.topPosition = 0.0F;
/*    */     } 
/*    */   }
/*    */   
/*    */   public float apply(Matrix4f matrix, class_4588 consumer, float size, float x, float y, float z, int color) {
/* 40 */     y -= this.topPosition * size;
/* 41 */     float scaledWidth = this.width * size;
/* 42 */     float scaledHeight = this.height * size;
/*    */     
/* 44 */     consumer.method_22918(matrix, x, y, z).method_22913(this.minU, this.minV).method_39415(color);
/* 45 */     consumer.method_22918(matrix, x, y + scaledHeight, z).method_22913(this.minU, this.maxV).method_39415(color);
/* 46 */     consumer.method_22918(matrix, x + scaledWidth, y + scaledHeight, z).method_22913(this.maxU, this.maxV).method_39415(color);
/* 47 */     consumer.method_22918(matrix, x + scaledWidth, y, z).method_22913(this.maxU, this.minV).method_39415(color);
/*    */     
/* 49 */     return this.advance * size;
/*    */   }
/*    */   
/*    */   public float getWidth(float size) {
/* 53 */     return this.advance * size;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\font\msdf\MsdfGlyph.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */