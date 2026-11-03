/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import net.minecraft.class_4184;
/*    */ import net.minecraft.class_638;
/*    */ import net.minecraft.class_758;
/*    */ import org.joml.Vector4f;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import vorga.phazeclient.base.util.render.FogColorTracker;
/*    */ 
/*    */ @Mixin(value = {class_758.class}, priority = 1)
/*    */ public abstract class BackgroundRendererFogColorTrackerMixin
/*    */ {
/*    */   @ModifyReturnValue(method = {"getFogColor"}, at = {@At("RETURN")})
/*    */   private static Vector4f phaze$trackFogColor(Vector4f color, class_4184 camera, float tickDelta, class_638 world, int viewDistance, float skyDarkness) {
/* 17 */     FogColorTracker.update(color);
/* 18 */     return color;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BackgroundRendererFogColorTrackerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */