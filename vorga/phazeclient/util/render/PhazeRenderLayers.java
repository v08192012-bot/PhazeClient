/*    */ package vorga.phazeclient.util.render;
/*    */ 
/*    */ import java.util.OptionalDouble;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.class_156;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_290;
/*    */ import net.minecraft.class_293;
/*    */ import net.minecraft.class_4668;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PhazeRenderLayers
/*    */ {
/* 19 */   private static final class_1921 HITBOX_FILL = (class_1921)class_1921.method_24048("phaze_hitbox_fill", class_290.field_1576, class_293.class_5596.field_27382, 1536, 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 24 */       class_1921.class_4688.method_23598()
/* 25 */       .method_34578(class_4668.field_29442)
/* 26 */       .method_23607(class_4668.field_22241)
/* 27 */       .method_23615(class_4668.field_21370)
/* 28 */       .method_23610(class_4668.field_25643)
/* 29 */       .method_23616(class_4668.field_21350)
/* 30 */       .method_23604(class_4668.field_21348)
/* 31 */       .method_23603(class_4668.field_21345)
/* 32 */       .method_23617(false));
/*    */   
/*    */   static {
/* 35 */     THICK_LINES = class_156.method_34866(width -> class_1921.method_24048("phaze_thick_lines_" + width, class_290.field_29337, class_293.class_5596.field_27377, 1536, class_1921.class_4688.method_23598().method_34578(class_4668.field_29433).method_23609(new class_4668.class_4677(OptionalDouble.of(width.floatValue()))).method_23607(class_4668.field_22241).method_23615(class_4668.field_21370).method_23610(class_4668.field_25643).method_23616(class_4668.field_21349).method_23603(class_4668.field_21345).method_23617(false)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final Function<Float, class_1921> THICK_LINES;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class_1921 getHitboxFill() {
/* 56 */     return HITBOX_FILL;
/*    */   }
/*    */   
/*    */   public static class_1921 getThickLines(float width) {
/* 60 */     return THICK_LINES.apply(Float.valueOf(width));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclien\\util\render\PhazeRenderLayers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */