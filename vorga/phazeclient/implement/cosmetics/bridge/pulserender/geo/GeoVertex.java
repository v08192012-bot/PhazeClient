/*    */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*    */ 
/*    */ public class GeoVertex {
/*    */   public Vec3F position;
/*    */   public float textureU;
/*    */   public float textureV;
/*    */   
/*    */   public GeoVertex(float var1, float var2, float var3, float var4, float var5) {
/*  9 */     this.position = new Vec3F(var1, var2, var3);
/* 10 */     this.textureU = var4;
/* 11 */     this.textureV = var5;
/*    */   }
/*    */   
/*    */   public GeoVertex(Vec3F var1, float var2, float var3) {
/* 15 */     this.position = var1;
/* 16 */     this.textureU = var2;
/* 17 */     this.textureV = var3;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\GeoVertex.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */