/*    */ package vorga.phazeclient.api.system.shape.implement;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_10142;
/*    */ import net.minecraft.class_286;
/*    */ import net.minecraft.class_287;
/*    */ import net.minecraft.class_289;
/*    */ import net.minecraft.class_290;
/*    */ import net.minecraft.class_293;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_7833;
/*    */ import org.joml.Matrix4f;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import vorga.phazeclient.api.system.shape.Shape;
/*    */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*    */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*    */ import vorga.phazeclient.base.QuickImports;
/*    */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
/*     */ import vorga.phazeclient.api.system.render.TextureFilter;
/*    */ 
/*    */ public class Image implements Shape, QuickImports {
/*    */   public Image setTexture(String texture) {
/* 23 */     this.texture = texture; return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String texture;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(ShapeProperties shape) {
/* 35 */     BatchedRectangle.flushIfBatching();
/*    */     
/* 37 */     class_4587 matrix = shape.getMatrix();
/*    */     
/* 39 */     RenderSystem.enableBlend();
/* 40 */     RenderSystem.defaultBlendFunc();
/*    */     
/* 42 */     class_2960 textureId = class_2960.method_60654(this.texture);
/* 43 */     RenderSystem.setShaderTexture(0, textureId);
/*    */     
/* 45 */     TextureFilter.setMinMag(textureId, 9729);
/*    */     
/* 48 */     float width = shape.getWidth();
/* 49 */     float x = shape.getX() + width;
/* 50 */     float y = shape.getY();
/*    */     
/* 52 */     matrix.method_22903();
/* 53 */     matrix.method_46416(x, y, 0.0F);
/* 54 */     matrix.method_22907(class_7833.field_40718.rotationDegrees(shape.getRotation()));
/* 55 */     matrix.method_46416(-x, -y, 0.0F);
/*    */     
/* 57 */     float rawWidth = shape.getHeight();
/* 58 */     float rawHeight = width;
/* 59 */     float aspectRatio = UiMsdfIconAtlas.resolveAspectRatio(textureId);
/* 60 */     float drawWidth = rawWidth;
/* 61 */     float drawHeight = drawWidth / Math.max(1.0E-4F, aspectRatio);
/* 62 */     if (drawHeight > rawHeight) {
/* 63 */       drawHeight = rawHeight;
/* 64 */       drawWidth = drawHeight * Math.max(1.0E-4F, aspectRatio);
/*    */     } 
/* 66 */     float drawX = x + (rawWidth - drawWidth) * 0.5F;
/* 67 */     float drawY = y + (rawHeight - drawHeight) * 0.5F;
/*    */     
/* 69 */     if (!UiMsdfIconAtlas.renderIcon(matrix, textureId, drawX, drawY, drawWidth, drawHeight, (shape.getColor()).x)) {
/* 70 */       renderRawTexture(matrix, textureId, drawX, drawY, drawWidth, drawHeight, (shape.getColor()).x);
/*    */     }
/*    */     
/* 73 */     matrix.method_22909();
/*    */     
/* 75 */     RenderSystem.disableBlend();
/*    */   }
/*    */   
/*    */   private static void renderRawTexture(class_4587 matrix, class_2960 textureId, float x, float y, float width, float height, int color) {
/* 79 */     RenderSystem.setShaderTexture(0, textureId);
/* 80 */     RenderSystem.setShader(class_10142.field_53880);
/*    */     
/* 82 */     Matrix4f positionMatrix = matrix.method_23760().method_23761();
/* 83 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 84 */     buffer.method_22918(positionMatrix, x, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 85 */     buffer.method_22918(positionMatrix, x, y + height, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 86 */     buffer.method_22918(positionMatrix, x + width, y + height, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 87 */     buffer.method_22918(positionMatrix, x + width, y, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 88 */     class_286.method_43433(buffer.method_60800());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\implement\Image.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */