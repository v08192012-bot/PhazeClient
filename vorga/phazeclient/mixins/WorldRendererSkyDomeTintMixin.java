/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_9975;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {class_9975.class}, priority = 1500)
/*    */ public abstract class WorldRendererSkyDomeTintMixin
/*    */ {
/*    */   @Redirect(method = {"renderSky(FFF)V"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0))
/*    */   private void phaze$tintSkyDome(float red, float green, float blue, float alpha) {
/* 63 */     int rr = clamp255(Math.round(red * 255.0F));
/* 64 */     int gg = clamp255(Math.round(green * 255.0F));
/* 65 */     int bb = clamp255(Math.round(blue * 255.0F));
/* 66 */     int working = 0xFF000000 | rr << 16 | gg << 8 | bb;
/*    */     
/* 68 */     SkyCustomizer module = SkyCustomizer.getInstance();
/* 69 */     if (module != null && module.isEnabled()) {
/* 70 */       class_310 mc = class_310.method_1551();
/* 71 */       float skyBrightness = 0.5F;
/* 72 */       if (mc != null && mc.field_1687 != null) {
/* 73 */         skyBrightness = mc.field_1687.method_23783(1.0F);
/*    */       }
/* 75 */       working = module.applyToSky(working, skyBrightness);
/*    */     } 
/*    */     
/* 78 */     int corrected = WorldColorCorrectionController.applyToArgb(WorldColorCorrectionController.Target.SKY, (
/*    */         
/* 80 */         clamp255(Math.round(alpha * 255.0F)) & 0xFF) << 24 | working & 0xFFFFFF);
/*    */ 
/*    */     
/* 83 */     float tr = (corrected >> 16 & 0xFF) / 255.0F;
/* 84 */     float tg = (corrected >> 8 & 0xFF) / 255.0F;
/* 85 */     float tb = (corrected & 0xFF) / 255.0F;
/* 86 */     float ta = (corrected >> 24 & 0xFF) / 255.0F;
/* 87 */     RenderSystem.setShaderColor(tr, tg, tb, ta);
/*    */   }
/*    */   
/*    */   private static int clamp255(int v) {
/* 91 */     if (v < 0) return 0; 
/* 92 */     if (v > 255) return 255; 
/* 93 */     return v;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\WorldRendererSkyDomeTintMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */