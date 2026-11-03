/*    */ package vorga.phazeclient.api.system.shape.implement;
/*    */ import com.mojang.blaze3d.platform.GlStateManager;
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
/*    */ 
/*    */ public class InvertedRectangle implements Shape, QuickImports {
/* 21 */   private final class_10156 SHADER_KEY = new class_10156(class_2960.method_60655("phaze", "core/round_inverted"), class_290.field_1592, class_10149.field_53930);
/* 22 */   private final Vector3f scratchPosition = new Vector3f();
/* 23 */   private final Vector3f scratchSize = new Vector3f();
/* 24 */   private final Vector4f scratchRound = new Vector4f();
/*    */ 
/*    */   
/*    */   public void render(ShapeProperties shape) {
/* 28 */     BatchedRectangle.flushIfBatching();
/*    */     
/* 30 */     RenderSystem.enableBlend();
/* 31 */     RenderSystem.blendFuncSeparate(GlStateManager.class_4535.ONE_MINUS_DST_COLOR, GlStateManager.class_4534.ONE_MINUS_SRC_COLOR, GlStateManager.class_4535.ONE, GlStateManager.class_4534.ZERO);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     RenderSystem.enableDepthTest();
/* 38 */     RenderSystem.enableCull();
/*    */     
/* 40 */     if (window() == null)
/* 41 */       return;  float scale = (float)window().method_4495();
/*    */     
/* 43 */     Matrix4f matrix4f = shape.getMatrix().method_23760().method_23761();
/* 44 */     Vector3f pos = matrix4f.transformPosition(shape.getX(), shape.getY(), 0.0F, this.scratchPosition).mul(scale);
/* 45 */     Vector3f size = matrix4f.getScale(this.scratchSize).mul(scale);
/* 46 */     Vector4f round = this.scratchRound.set((Vector4fc)shape.getRound()).mul(size.y);
/*    */     
/* 48 */     float softness = shape.getSoftness();
/* 49 */     float width = shape.getWidth() * size.x;
/* 50 */     float height = shape.getHeight() * size.y;
/*    */     
/* 52 */     class_287 buffer = tessellator().method_60827(class_293.class_5596.field_27382, class_290.field_1592);
/* 53 */     drawEngine.quad(matrix4f, buffer, shape.getX() - softness / 2.0F, shape.getY() - softness / 2.0F, shape.getWidth() + softness, shape.getHeight() + softness);
/*    */     
/* 55 */     class_5944 shader = RenderSystem.setShader(this.SHADER_KEY);
/*    */     
/* 57 */     if (shader == null)
/* 58 */       return;  shader.method_35785("size").method_1255(width, height);
/* 59 */     shader.method_35785("location").method_1255(pos.x, window().method_4507() - height - pos.y);
/* 60 */     shader.method_35785("radius").method_35652(round);
/* 61 */     shader.method_35785("softness").method_1251(softness);
/*    */     
/* 63 */     class_286.method_43433(buffer.method_60800());
/* 64 */     RenderSystem.disableBlend();
/* 65 */     RenderSystem.defaultBlendFunc();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\shape\implement\InvertedRectangle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */