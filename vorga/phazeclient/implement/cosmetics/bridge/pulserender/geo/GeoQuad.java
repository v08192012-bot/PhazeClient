/*    */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*    */ 
/*    */ public class GeoQuad {
/*    */   public GeoVertex[] vertices;
/*    */   public Vec3F normal;
/*    */   
/*    */   public GeoQuad(GeoVertex[] var1, Vec3F var2) {
/*  8 */     this.vertices = var1;
/*  9 */     this.normal = var2;
/*    */   }
/*    */   
/*    */   public GeoQuad(GeoVertex[] var1, float var2, float var3, float var4) {
/* 13 */     this.vertices = var1;
/* 14 */     this.normal = new Vec3F(var2, var3, var4);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\GeoQuad.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */