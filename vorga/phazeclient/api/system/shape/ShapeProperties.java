/*    */ package vorga.phazeclient.api.system.shape;public class ShapeProperties { private class_4587 matrix; private float x; private float y; private float width; private float height; private float softness; private float thickness;
/*    */   private float start;
/*    */   private float end;
/*    */   private float quality;
/*    */   private float rotation;
/*    */   private Vector4f round;
/*    */   private int outlineColor;
/*    */   private Vector4i color;
/*    */   
/* 10 */   private static float $default$quality() { return 20.0F; } private static float $default$rotation() { return 90.0F; } private static int $default$outlineColor() { return -1; } public static ShapePropertiesBuilder builder() { return new ShapePropertiesBuilder(); }
/*    */   
/* 12 */   public void setMatrix(class_4587 matrix) { this.matrix = matrix; } public void setX(float x) { this.x = x; } public void setY(float y) { this.y = y; } public void setWidth(float width) { this.width = width; } public void setHeight(float height) { this.height = height; } public void setSoftness(float softness) { this.softness = softness; } public void setThickness(float thickness) { this.thickness = thickness; } public void setStart(float start) { this.start = start; } public void setEnd(float end) { this.end = end; } public void setQuality(float quality) { this.quality = quality; } public void setRotation(float rotation) { this.rotation = rotation; } public void setRound(Vector4f round) { this.round = round; } public void setOutlineColor(int outlineColor) { this.outlineColor = outlineColor; } public void setColor(Vector4i color) { this.color = color; }
/*    */   
/* 14 */   public class_4587 getMatrix() { return this.matrix; }
/* 15 */   public float getX() { return this.x; } public float getY() { return this.y; } public float getWidth() { return this.width; } public float getHeight() { return this.height; }
/* 16 */   public float getSoftness() { return this.softness; } public float getThickness() { return this.thickness; }
/* 17 */   public float getStart() { return this.start; } public float getEnd() { return this.end; }
/*    */   
/*    */   public float getQuality() {
/* 20 */     return this.quality;
/*    */   }
/*    */   public float getRotation() {
/* 23 */     return this.rotation;
/*    */   } public Vector4f getRound() {
/* 25 */     return this.round;
/*    */   }
/*    */   
/* 28 */   public int getOutlineColor() { return this.outlineColor; } public Vector4i getColor() {
/* 29 */     return this.color;
/*    */   } public ShapePropertiesBuilder toBuilder() {
/* 31 */     return (new ShapePropertiesBuilder()).matrix(this.matrix).x(this.x).y(this.y).width(this.width).height(this.height).softness(this.softness).thickness(this.thickness).start(this.start).end(this.end).quality(this.quality).rotation(this.rotation).round(this.round).outlineColor(this.outlineColor).color(this.color);
/*    */   }
/* 33 */   private ShapeProperties(class_4587 matrix, float x, float y, float width, float height, float softness, float thickness, float start, float end, float quality, float rotation, Vector4f round, int outlineColor, Vector4i color) { this.matrix = matrix;
/* 34 */     this.x = x;
/* 35 */     this.y = y;
/* 36 */     this.width = width;
/* 37 */     this.height = height;
/* 38 */     this.softness = softness;
/* 39 */     this.thickness = thickness;
/* 40 */     this.round = (round != null) ? round : new Vector4f(0.0F);
/* 41 */     this.outlineColor = outlineColor;
/* 42 */     this.color = (color != null) ? color : new Vector4i(-1);
/* 43 */     this.start = start;
/* 44 */     this.end = end;
/* 45 */     this.quality = quality;
/* 46 */     this.rotation = rotation; }
/*    */   
/*    */   public static class ShapePropertiesBuilder {
/*    */     private class_4587 matrix; private float x; private float y; private float width; private float height; private float softness; private float thickness; private float start; private float end; private boolean quality$set; private float quality$value; private boolean rotation$set; private float rotation$value; private Vector4f round; private boolean outlineColor$set; private int outlineColor$value;
/*    */     private Vector4i color;
/*    */     
/* 52 */     public ShapePropertiesBuilder color(int color) { this.color = new Vector4i(color);
/* 53 */       return this; }
/*    */     private float quality;
/*    */     private float rotation;
/*    */     private int outlineColor;
/* 57 */     public ShapePropertiesBuilder matrix(class_4587 matrix) { this.matrix = matrix; return this; } public ShapePropertiesBuilder x(float x) { this.x = x; return this; } public ShapePropertiesBuilder y(float y) { this.y = y; return this; } public ShapePropertiesBuilder width(float width) { this.width = width; return this; } public ShapePropertiesBuilder height(float height) { this.height = height; return this; } public ShapePropertiesBuilder softness(float softness) { this.softness = softness; return this; } public ShapePropertiesBuilder thickness(float thickness) { this.thickness = thickness; return this; } public ShapePropertiesBuilder start(float start) { this.start = start; return this; } public ShapePropertiesBuilder end(float end) { this.end = end; return this; } public ShapePropertiesBuilder quality(float quality) { this.quality$value = quality; this.quality$set = true; return this; } public ShapePropertiesBuilder rotation(float rotation) { this.rotation$value = rotation; this.rotation$set = true; return this; } public ShapePropertiesBuilder outlineColor(int outlineColor) { this.outlineColor$value = outlineColor; this.outlineColor$set = true; return this; } public ShapeProperties build() { float quality$value = this.quality$value; if (!this.quality$set) quality$value = ShapeProperties.$default$quality();  float rotation$value = this.rotation$value; if (!this.rotation$set) rotation$value = ShapeProperties.$default$rotation();  int outlineColor$value = this.outlineColor$value; if (!this.outlineColor$set) outlineColor$value = ShapeProperties.$default$outlineColor();  return new ShapeProperties(this.matrix, this.x, this.y, this.width, this.height, this.softness, this.thickness, this.start, this.end, quality$value, rotation$value, this.round, outlineColor$value, this.color); } public String toString() { return "ShapeProperties.ShapePropertiesBuilder(matrix=" + String.valueOf(this.matrix) + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", softness=" + this.softness + ", thickness=" + this.thickness + ", start=" + this.start + ", end=" + this.end + ", quality$value=" + this.quality$value + ", rotation$value=" + this.rotation$value + ", round=" + String.valueOf(this.round) + ", outlineColor$value=" + this.outlineColor$value + ", color=" + String.valueOf(this.color) + ")"; } public ShapePropertiesBuilder color(Vector4i color) { this.color = color;
/* 58 */       return this; }
/*    */ 
/*    */     
/*    */     public ShapePropertiesBuilder color(int... color) {
/* 62 */       this.color = new Vector4i(color);
/* 63 */       return this;
/*    */     }
/*    */     
/*    */     public ShapePropertiesBuilder round(float round) {
/* 67 */       this.round = new Vector4f(round);
/* 68 */       return this;
/*    */     }
/*    */     
/*    */     public ShapePropertiesBuilder round(Vector4f round) {
/* 72 */       this.round = new Vector4f((Vector4fc)round);
/* 73 */       return this;
/*    */     }
/*    */     
/*    */     public ShapePropertiesBuilder round(float... round) {
/* 77 */       this.round = new Vector4f(round);
/* 78 */       return this;
/*    */     }
/*    */   }
/*    */   
/*    */   public static ShapePropertiesBuilder create(class_4587 matrix, double x, double y, double width, double height) {
/* 83 */     return builder().matrix(matrix).x((float)x).y((float)y).width((float)width).height((float)height);
/*    */   } }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\ShapeProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */