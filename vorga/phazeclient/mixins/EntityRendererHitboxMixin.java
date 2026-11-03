/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_761;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.HitboxCustomizer;
/*    */ import vorga.phazeclient.util.HitboxRenderUtil;
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
/*    */ @Mixin({class_761.class})
/*    */ public class EntityRendererHitboxMixin
/*    */ {
/*    */   @Inject(method = {"method_3956"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void phaze$renderCustomHitbox(class_4587 matrices, class_4588 vertices, class_1297 entity, float tickDelta, float red, float green, float blue, CallbackInfo ci) {
/* 38 */     HitboxCustomizer module = HitboxCustomizer.getInstance();
/*    */ 
/*    */     
/* 41 */     if (module.isEnabled()) {
/* 42 */       HitboxRenderUtil.drawBox(matrices, vertices, entity, tickDelta);
/* 43 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EntityRendererHitboxMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */