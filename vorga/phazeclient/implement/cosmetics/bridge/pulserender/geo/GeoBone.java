/*     */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GeoBone {
/*     */   public GeoBone parent;
/*   8 */   public List<GeoBone> childBones = new ArrayList<>();
/*   9 */   public List<GeoCube> childCubes = new ArrayList<>();
/*     */   public String name;
/*     */   public boolean isHidden = false;
/*     */   public float rotationPointX;
/*     */   public float rotationPointY;
/*     */   public float rotationPointZ;
/*     */   private float rotateX;
/*     */   private float rotateY;
/*     */   private float rotateZ;
/*     */   private float positionX;
/*     */   private float positionY;
/*     */   private float positionZ;
/*  21 */   private float scaleX = 1.0F;
/*  22 */   private float scaleY = 1.0F;
/*  23 */   private float scaleZ = 1.0F;
/*     */   
/*     */   public GeoBone(String var1) {
/*  26 */     this.name = var1;
/*     */   }
/*     */   
/*     */   public float getRotationX() {
/*  30 */     return this.rotateX;
/*     */   }
/*     */   
/*     */   public float getRotationY() {
/*  34 */     return this.rotateY;
/*     */   }
/*     */   
/*     */   public float getRotationZ() {
/*  38 */     return this.rotateZ;
/*     */   }
/*     */   
/*     */   public void setRotationX(float var1) {
/*  42 */     this.rotateX = var1;
/*     */   }
/*     */   
/*     */   public void setRotationY(float var1) {
/*  46 */     this.rotateY = var1;
/*     */   }
/*     */   
/*     */   public void setRotationZ(float var1) {
/*  50 */     this.rotateZ = var1;
/*     */   }
/*     */   
/*     */   public float getPositionX() {
/*  54 */     return this.positionX;
/*     */   }
/*     */   
/*     */   public float getPositionY() {
/*  58 */     return this.positionY;
/*     */   }
/*     */   
/*     */   public float getPositionZ() {
/*  62 */     return this.positionZ;
/*     */   }
/*     */   
/*     */   public void setPositionX(float var1) {
/*  66 */     this.positionX = var1;
/*     */   }
/*     */   
/*     */   public void setPositionY(float var1) {
/*  70 */     this.positionY = var1;
/*     */   }
/*     */   
/*     */   public void setPositionZ(float var1) {
/*  74 */     this.positionZ = var1;
/*     */   }
/*     */   
/*     */   public float getScaleX() {
/*  78 */     return this.scaleX;
/*     */   }
/*     */   
/*     */   public float getScaleY() {
/*  82 */     return this.scaleY;
/*     */   }
/*     */   
/*     */   public float getScaleZ() {
/*  86 */     return this.scaleZ;
/*     */   }
/*     */   
/*     */   public void setScaleX(float var1) {
/*  90 */     this.scaleX = var1;
/*     */   }
/*     */   
/*     */   public void setScaleY(float var1) {
/*  94 */     this.scaleY = var1;
/*     */   }
/*     */   
/*     */   public void setScaleZ(float var1) {
/*  98 */     this.scaleZ = var1;
/*     */   }
/*     */   
/*     */   public float getPivotX() {
/* 102 */     return this.rotationPointX;
/*     */   }
/*     */   
/*     */   public float getPivotY() {
/* 106 */     return this.rotationPointY;
/*     */   }
/*     */   
/*     */   public float getPivotZ() {
/* 110 */     return this.rotationPointZ;
/*     */   }
/*     */   
/*     */   public void setPivotX(float var1) {
/* 114 */     this.rotationPointX = var1;
/*     */   }
/*     */   
/*     */   public void setPivotY(float var1) {
/* 118 */     this.rotationPointY = var1;
/*     */   }
/*     */   
/*     */   public void setPivotZ(float var1) {
/* 122 */     this.rotationPointZ = var1;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 126 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isHidden() {
/* 130 */     return this.isHidden;
/*     */   }
/*     */   
/*     */   public void setHidden(boolean var1) {
/* 134 */     this.isHidden = var1;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\GeoBone.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */