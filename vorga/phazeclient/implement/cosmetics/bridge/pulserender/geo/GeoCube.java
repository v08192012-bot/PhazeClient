/*    */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*    */ 
/*    */ public class GeoCube {
/*  4 */   public GeoQuad[] quads = new GeoQuad[6];
/*    */   public Vec3F size;
/*    */   public Vec3F pivot;
/*    */   public Vec3F rotation;
/*    */   public float inflate;
/*    */   public boolean mirror;
/*    */   
/*    */   public GeoCube(float var1, float var2, float var3) {
/* 12 */     this.size = new Vec3F(var1, var2, var3);
/* 13 */     this.pivot = new Vec3F(0.0F, 0.0F, 0.0F);
/* 14 */     this.rotation = new Vec3F(0.0F, 0.0F, 0.0F);
/* 15 */     this.inflate = 0.0F;
/* 16 */     this.mirror = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\GeoCube.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */