/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4668;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.base.util.render.GlintTextureClock;
/*    */ import vorga.phazeclient.implement.features.modules.other.GlintColorizer;
/*    */ 
/*    */ @Mixin({class_4668.class})
/*    */ public abstract class GlintColorizerMixin {
/*    */   @Unique
/* 19 */   private static final GlintTextureClock phaze$glintTextureClock = new GlintTextureClock();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyExpressionValue(method = {"setupGlintTexturing(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getMeasuringTimeMs()J")}, require = 1, allow = 1)
/*    */   private static long phaze$scaleGlintTextureTime(long original) {
/* 31 */     RenderSystem.assertOnRenderThread();
/* 32 */     GlintColorizer module = GlintColorizer.getInstance();
/* 33 */     boolean enabled = (module != null && module.isEnabled());
/* 34 */     return phaze$glintTextureClock.sample(original, enabled, enabled ? module.speed.getValue() : 1.0D);
/*    */   }
/*    */   
/*    */   @Inject(method = {"startDrawing"}, at = {@At("TAIL")})
/*    */   private void phaze$applyCustomGlint(CallbackInfo ci) {
/* 39 */     GlintColorizerMixin glintColorizerMixin = this; if (glintColorizerMixin instanceof class_1921) { class_1921 layer = (class_1921)glintColorizerMixin; if (layer
/* 40 */         .toString().toLowerCase(Locale.ROOT).contains("glint")) {
/*    */ 
/*    */ 
/*    */         
/* 44 */         GlintColorizer module = GlintColorizer.getInstance();
/* 45 */         if (module == null || !module.isEnabled())
/*    */           return; 
/* 47 */         int color = module.getActiveGlintColor();
/* 48 */         RenderSystem.setShaderColor((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >>> 24 & 0xFF) / 255.0F);
/*    */         return;
/*    */       }  }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"endDrawing"}, at = {@At("HEAD")})
/*    */   private void phaze$resetCustomGlint(CallbackInfo ci) {
/* 58 */     GlintColorizerMixin glintColorizerMixin = this; if (glintColorizerMixin instanceof class_1921) { class_1921 layer = (class_1921)glintColorizerMixin; if (layer
/* 59 */         .toString().toLowerCase(Locale.ROOT).contains("glint")) {
/*    */ 
/*    */         
/* 62 */         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*    */         return;
/*    */       }  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\GlintColorizerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */