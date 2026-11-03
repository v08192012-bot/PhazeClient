/*    */ package vorga.phazeclient.api.system.shape.implement;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
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
/*    */ public class Arc implements Shape, QuickImports {
/* 21 */   private final class_10156 SHADER_KEY = new class_10156(class_2960.method_60655("phaze", "core/arc"), class_290.field_1592, class_10149.field_53930);
/* 22 */   private final Vector3f scratchPosition = new Vector3f();
/* 23 */   private final Vector3f scratchSize = new Vector3f();
/* 24 */   private final Vector4f scratchRound = new Vector4f();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(ShapeProperties shape) {
/* 32 */     BatchedRectangle.flushIfBatching();
/*    */     
/* 34 */     RenderSystem.enableBlend();
/* 35 */     RenderSystem.defaultBlendFunc();
/* 36 */     RenderSystem.enableDepthTest();
/* 37 */     RenderSystem.enableCull();
/*    */     
/* 39 */     if (window() == null)
/* 40 */       return;  float scale = (float)window().method_4495();
/* 41 */     float alpha = RenderSystem.getShaderColor()[3];
/*    */     
/* 43 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/* 44 */     Vector3f pos = matrix4f.transformPosition(shape.getX(), shape.getY(), 0.0F, this.scratchPosition).mul(scale);
/* 45 */     Vector3f size = matrix4f.getScale(this.scratchSize).mul(scale);
/* 46 */     Vector4f round = this.scratchRound.set((Vector4fc)shape.getRound()).mul(size.y);
/*    */     
/* 48 */     float width = shape.getWidth() * size.x;
/* 49 */     float height = shape.getHeight() * size.y;
/*    */     
/* 51 */     class_287 buffer = tessellator().method_60827(class_293.class_5596.field_27382, class_290.field_1592);
/* 52 */     drawEngine.quad(matrix4f, buffer, shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
/*    */     
/* 54 */     class_5944 shader = RenderSystem.setShader(this.SHADER_KEY);
/* 55 */     if (shader == null)
/* 56 */       return;  shader.method_35785("size").method_1255(width, height);
/* 57 */     shader.method_35785("location").method_1255(pos.x, window().method_4507() - height - pos.y);
/* 58 */     shader.method_35785("radius").method_1251(round.x);
/* 59 */     shader.method_35785("thickness").method_1251(shape.getThickness());
/* 60 */     shader.method_35785("start").method_1251(shape.getStart());
/* 61 */     shader.method_35785("end").method_1251(shape.getEnd());
/* 62 */     shader.method_35785("color1").method_35657(ColorUtil.redf((shape.getColor()).x), ColorUtil.greenf((shape.getColor()).x), ColorUtil.bluef((shape.getColor()).x), ColorUtil.alphaf(ColorUtil.multAlpha((shape.getColor()).x, alpha)));
/* 63 */     shader.method_35785("color2").method_35657(ColorUtil.redf((shape.getColor()).y), ColorUtil.greenf((shape.getColor()).y), ColorUtil.bluef((shape.getColor()).y), ColorUtil.alphaf(ColorUtil.multAlpha((shape.getColor()).y, alpha)));
/*    */     
/* 65 */     class_286.method_43433(buffer.method_60800());
/* 66 */     RenderSystem.disableBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\implement\Arc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */