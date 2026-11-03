/*    */ package vorga.phazeclient.api.system.draw;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_10142;
/*    */ import net.minecraft.class_286;
/*    */ import net.minecraft.class_287;
/*    */ import net.minecraft.class_290;
/*    */ import net.minecraft.class_293;
/*    */ import org.joml.Matrix4f;
/*    */ import vorga.phazeclient.base.QuickImports;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DrawEngineImpl
/*    */   implements DrawEngine, QuickImports
/*    */ {
/*    */   public void quad(Matrix4f matrix4f, class_287 buffer, float x, float y, float width, float height) {
/* 19 */     buffer.method_22918(matrix4f, x, y, 0.0F);
/* 20 */     buffer.method_22918(matrix4f, x, y + height, 0.0F);
/* 21 */     buffer.method_22918(matrix4f, x + width, y + height, 0.0F);
/* 22 */     buffer.method_22918(matrix4f, x + width, y, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void quad(Matrix4f matrix4f, class_287 buffer, float x, float y, float width, float height, int color) {
/* 27 */     buffer.method_22918(matrix4f, x, y, 0.0F).method_39415(color);
/* 28 */     buffer.method_22918(matrix4f, x, y + height, 0.0F).method_39415(color);
/* 29 */     buffer.method_22918(matrix4f, x + width, y + height, 0.0F).method_39415(color);
/* 30 */     buffer.method_22918(matrix4f, x + width, y, 0.0F).method_39415(color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void quad(Matrix4f matrix4f, float x, float y, float width, float height, int color) {
/* 35 */     RenderSystem.setShader(class_10142.field_53880);
/* 36 */     class_287 buffer = tessellator().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 37 */     buffer.method_22918(matrix4f, x, y + height, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 38 */     buffer.method_22918(matrix4f, x + width, y + height, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 39 */     buffer.method_22918(matrix4f, x + width, y, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 40 */     buffer.method_22918(matrix4f, x, y, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 41 */     class_286.method_43433(buffer.method_60800());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\draw\DrawEngineImpl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */