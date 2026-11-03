/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_638;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import vorga.phazeclient.implement.features.modules.other.SkyCustomizer;
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
/*    */ @Mixin(value = {class_638.class}, priority = 1300)
/*    */ public abstract class ClientWorldSkyCustomizerMixin
/*    */ {
/*    */   @ModifyReturnValue(method = {"getSkyColor"}, at = {@At("RETURN")})
/*    */   private int phaze$tintSkyColor(int original, class_243 cameraPos, float tickDelta) {
/* 54 */     SkyCustomizer module = SkyCustomizer.getInstance();
/* 55 */     if (module == null || !module.isEnabled()) {
/* 56 */       return original;
/*    */     }
/* 58 */     class_638 self = (class_638)this;
/* 59 */     return module.applyToSky(original, self.method_23783(tickDelta));
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClientWorldSkyCustomizerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */