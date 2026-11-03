/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import net.minecraft.class_765;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import vorga.phazeclient.implement.features.modules.other.Bright;
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
/*    */ @Mixin({class_765.class})
/*    */ public class LightmapTextureManagerBrightMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"update(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;")})
/*    */   private Object phaze$injectFullBright(Object original) {
/*    */     Double vanilla;
/* 38 */     Bright bright = Bright.getInstance();
/* 39 */     if (bright == null || !bright.isState()) {
/* 40 */       return original;
/*    */     }
/* 42 */     if (original instanceof Double) { vanilla = (Double)original; }
/* 43 */     else { return original; }
/*    */     
/* 45 */     return Double.valueOf(Math.max(vanilla.doubleValue(), bright.brightness.getValue() * 10.0D));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\LightmapTextureManagerBrightMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */