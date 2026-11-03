/*    */ package vorga.phazeclient.api.system.shape.implement;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_10149;
/*    */ import net.minecraft.class_10156;
/*    */ import net.minecraft.class_286;
/*    */ import net.minecraft.class_287;
/*    */ import net.minecraft.class_290;
/*    */ import net.minecraft.class_293;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_5944;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Vector3f;
/*    */ import org.joml.Vector4f;
/*    */ import org.joml.Vector4fc;
/*    */ import vorga.phazeclient.api.system.shape.Shape;
/*    */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*    */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*    */ import vorga.phazeclient.base.QuickImports;
/*    */ import vorga.phazeclient.base.util.color.ColorUtil;
/*    */ 
/*    */ public class Rectangle implements Shape, QuickImports {
/* 22 */   private final class_10156 SHADER_KEY = new class_10156(class_2960.method_60655("phaze", "core/round"), class_290.field_1592, class_10149.field_53930);
/* 23 */   private final Vector3f scratchPosition = new Vector3f();
/* 24 */   private final Vector3f scratchSize = new Vector3f();
/* 25 */   private final Vector4f scratchRound = new Vector4f();
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
/*    */   public void render(ShapeProperties shape) {
/* 41 */     if (BatchedRectangle.isBatching()) {
/* 42 */       BatchedRectangle.submit(shape);
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     RenderSystem.enableBlend();
/* 47 */     RenderSystem.defaultBlendFunc();
/* 48 */     RenderSystem.enableDepthTest();
/* 49 */     RenderSystem.enableCull();
/*    */     
/* 51 */     if (window() == null)
/* 52 */       return;  float scale = (float)window().method_4495();
/* 53 */     float alpha = RenderSystem.getShaderColor()[3];
/*    */     
/* 55 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/* 56 */     Vector3f pos = matrix4f.transformPosition(shape.getX(), shape.getY(), 0.0F, this.scratchPosition).mul(scale);
/* 57 */     Vector3f size = matrix4f.getScale(this.scratchSize).mul(scale);
/* 58 */     Vector4f round = this.scratchRound.set((Vector4fc)shape.getRound()).mul(size.y);
/*    */     
/* 60 */     float softness = shape.getSoftness();
/* 61 */     float thickness = shape.getThickness();
/* 62 */     float width = shape.getWidth() * size.x;
/* 63 */     float height = shape.getHeight() * size.y;
/*    */     
/* 65 */     class_287 buffer = tessellator().method_60827(class_293.class_5596.field_27382, class_290.field_1592);
/* 66 */     drawEngine.quad(matrix4f, buffer, shape.getX() - softness / 2.0F, shape.getY() - softness / 2.0F, shape.getWidth() + softness, shape.getHeight() + softness);
/*    */     
/* 68 */     class_5944 shader = RenderSystem.setShader(this.SHADER_KEY);
/*    */     
/* 70 */     if (shader == null)
/* 71 */       return;  shader.method_35785("size").method_1255(width, height);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 80 */     shader.method_35785("location").method_1255(pos.x, BatchedRectangle.getActiveFbHeight() - height - pos.y);
/* 81 */     shader.method_35785("radius").method_35652(round);
/* 82 */     shader.method_35785("softness").method_1251(softness);
/* 83 */     shader.method_35785("thickness").method_1251(thickness);
/* 84 */     shader.method_35785("color1").method_35657(ColorUtil.redf((shape.getColor()).x), ColorUtil.greenf((shape.getColor()).x), ColorUtil.bluef((shape.getColor()).x), ColorUtil.alphaf(ColorUtil.multAlpha((shape.getColor()).x, alpha)));
/* 85 */     shader.method_35785("color2").method_35657(ColorUtil.redf((shape.getColor()).y), ColorUtil.greenf((shape.getColor()).y), ColorUtil.bluef((shape.getColor()).y), ColorUtil.alphaf(ColorUtil.multAlpha((shape.getColor()).y, alpha)));
/* 86 */     shader.method_35785("color3").method_35657(ColorUtil.redf((shape.getColor()).z), ColorUtil.greenf((shape.getColor()).z), ColorUtil.bluef((shape.getColor()).z), ColorUtil.alphaf(ColorUtil.multAlpha((shape.getColor()).z, alpha)));
/* 87 */     shader.method_35785("color4").method_35657(ColorUtil.redf((shape.getColor()).w), ColorUtil.greenf((shape.getColor()).w), ColorUtil.bluef((shape.getColor()).w), ColorUtil.alphaf(ColorUtil.multAlpha((shape.getColor()).w, alpha)));
/* 88 */     shader.method_35785("outlineColor").method_35657(ColorUtil.redf(shape.getOutlineColor()), ColorUtil.greenf(shape.getOutlineColor()), ColorUtil.bluef(shape.getOutlineColor()), ColorUtil.alphaf(ColorUtil.multAlpha(shape.getOutlineColor(), alpha)));
/*    */     
/* 90 */     class_286.method_43433(buffer.method_60800());
/* 91 */     RenderSystem.disableBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\implement\Rectangle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */