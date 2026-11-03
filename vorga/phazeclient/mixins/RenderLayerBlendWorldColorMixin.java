/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4668;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorRenderHelper;
/*    */ 
/*    */ @Mixin({class_4668.class})
/*    */ public abstract class RenderLayerBlendWorldColorMixin {
/*    */   @Inject(method = {"startDrawing"}, at = {@At("TAIL")}, require = 0)
/*    */   private void phaze$enableBlend(CallbackInfo ci) {
/* 16 */     RenderLayerBlendWorldColorMixin renderLayerBlendWorldColorMixin = this; if (renderLayerBlendWorldColorMixin instanceof class_1921) { class_1921 layer = (class_1921)renderLayerBlendWorldColorMixin; if (WorldColorRenderHelper.shouldForceBlendForLayer(layer)) {
/* 17 */         RenderSystem.enableBlend();
/* 18 */         RenderSystem.defaultBlendFunc();
/*    */       }  }
/*    */   
/*    */   }
/*    */   @Inject(method = {"endDrawing"}, at = {@At("HEAD")}, require = 0)
/*    */   private void phaze$disableBlend(CallbackInfo ci) {
/* 24 */     RenderLayerBlendWorldColorMixin renderLayerBlendWorldColorMixin = this; if (renderLayerBlendWorldColorMixin instanceof class_1921) { class_1921 layer = (class_1921)renderLayerBlendWorldColorMixin; if (WorldColorRenderHelper.shouldForceBlendForLayer(layer))
/* 25 */         RenderSystem.disableBlend();  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\RenderLayerBlendWorldColorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */