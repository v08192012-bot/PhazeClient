/*    */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*    */ 
/*    */ public class Vec3F {
/*  4 */   public static final Vec3F NULL_VECTOR = new Vec3F(0.0F, 0.0F, 0.0F);
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public Vec3F(float var1, float var2, float var3) {
/* 10 */     this.x = var1;
/* 11 */     this.y = var2;
/* 12 */     this.z = var3;
/*    */   }
/*    */   
/*    */   public float getX() {
/* 16 */     return this.x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 20 */     return this.y;
/*    */   }
/*    */   
/*    */   public float getZ() {
/* 24 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setX(float var1) {
/* 28 */     this.x = var1;
/*    */   }
/*    */   
/*    */   public void setY(float var1) {
/* 32 */     this.y = var1;
/*    */   }
/*    */   
/*    */   public void setZ(float var1) {
/* 36 */     this.z = var1;
/*    */   }
/*    */   
/*    */   public void set(float var1, float var2, float var3) {
/* 40 */     this.x = var1;
/* 41 */     this.y = var2;
/* 42 */     this.z = var3;
/*    */   }
/*    */   
/*    */   public Vec3F crossProduct(Vec3F var1) {
/* 46 */     return new Vec3F(this.y * var1.z - this.z * var1.y, this.z * var1.x - this.x * var1.z, this.x * var1.y - this.y * var1.x);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "" + this.x + "," + this.x + "," + this.y;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\Vec3F.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */