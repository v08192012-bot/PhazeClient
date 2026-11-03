/*    */ package vorga.phazeclient.api.system.shape;
/*    */ public class ShapePropertiesBuilder { private class_4587 matrix; private float x; private float y; private float width;
/*    */   private float height;
/*    */   private float softness;
/*    */   private float thickness;
/*    */   private float start;
/*    */   private float end;
/*    */   private boolean quality$set;
/*    */   
/* 10 */   public ShapePropertiesBuilder matrix(class_4587 matrix) { this.matrix = matrix; return this; } private float quality$value; private boolean rotation$set; private float rotation$value; private Vector4f round; private boolean outlineColor$set; private int outlineColor$value; private Vector4i color; private float quality; private float rotation; private int outlineColor; public ShapePropertiesBuilder x(float x) { this.x = x; return this; } public ShapePropertiesBuilder y(float y) { this.y = y; return this; } public ShapePropertiesBuilder width(float width) { this.width = width; return this; } public ShapePropertiesBuilder height(float height) { this.height = height; return this; } public ShapePropertiesBuilder softness(float softness) { this.softness = softness; return this; } public ShapePropertiesBuilder thickness(float thickness) { this.thickness = thickness; return this; } public ShapePropertiesBuilder start(float start) { this.start = start; return this; } public ShapePropertiesBuilder end(float end) { this.end = end; return this; } public ShapePropertiesBuilder quality(float quality) { this.quality$value = quality; this.quality$set = true; return this; } public ShapePropertiesBuilder rotation(float rotation) { this.rotation$value = rotation; this.rotation$set = true; return this; } public ShapePropertiesBuilder outlineColor(int outlineColor) { this.outlineColor$value = outlineColor; this.outlineColor$set = true; return this; } public ShapeProperties build() { float quality$value = this.quality$value; if (!this.quality$set) quality$value = ShapeProperties.$default$quality();  float rotation$value = this.rotation$value; if (!this.rotation$set) rotation$value = ShapeProperties.$default$rotation();  int outlineColor$value = this.outlineColor$value; if (!this.outlineColor$set) outlineColor$value = ShapeProperties.$default$outlineColor();  return new ShapeProperties(this.matrix, this.x, this.y, this.width, this.height, this.softness, this.thickness, this.start, this.end, quality$value, rotation$value, this.round, outlineColor$value, this.color); } public String toString() { return "ShapeProperties.ShapePropertiesBuilder(matrix=" + String.valueOf(this.matrix) + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", softness=" + this.softness + ", thickness=" + this.thickness + ", start=" + this.start + ", end=" + this.end + ", quality$value=" + this.quality$value + ", rotation$value=" + this.rotation$value + ", round=" + String.valueOf(this.round) + ", outlineColor$value=" + this.outlineColor$value + ", color=" + String.valueOf(this.color) + ")"; }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShapePropertiesBuilder color(int color) {
/* 52 */     this.color = new Vector4i(color);
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   public ShapePropertiesBuilder color(Vector4i color) {
/* 57 */     this.color = color;
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public ShapePropertiesBuilder color(int... color) {
/* 62 */     this.color = new Vector4i(color);
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public ShapePropertiesBuilder round(float round) {
/* 67 */     this.round = new Vector4f(round);
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public ShapePropertiesBuilder round(Vector4f round) {
/* 72 */     this.round = new Vector4f((Vector4fc)round);
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public ShapePropertiesBuilder round(float... round) {
/* 77 */     this.round = new Vector4f(round);
/* 78 */     return this;
/*    */   } }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\ShapeProperties$ShapePropertiesBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */