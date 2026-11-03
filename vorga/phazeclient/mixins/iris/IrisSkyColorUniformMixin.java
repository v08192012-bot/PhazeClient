/*    */ package vorga.phazeclient.mixins.iris;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_638;
/*    */ import org.joml.Vector3d;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
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
/*    */ @Mixin(targets = {"net.irisshaders.iris.uniforms.CommonUniforms"}, remap = false)
/*    */ public abstract class IrisSkyColorUniformMixin
/*    */ {
/*    */   @ModifyReturnValue(method = {"getSkyColor"}, at = {@At("RETURN")}, remap = false)
/*    */   private static Vector3d phaze$tintIrisSkyColor(Vector3d vanilla) {
/* 58 */     if (vanilla == null) {
/* 59 */       return vanilla;
/*    */     }
/* 61 */     class_310 mc = class_310.method_1551();
/* 62 */     if (mc == null) return vanilla; 
/* 63 */     class_638 world = mc.field_1687;
/* 64 */     if (world == null) return vanilla;
/*    */     
/* 66 */     int r = clamp255((int)Math.round(vanilla.x * 255.0D));
/* 67 */     int g = clamp255((int)Math.round(vanilla.y * 255.0D));
/* 68 */     int b = clamp255((int)Math.round(vanilla.z * 255.0D));
/* 69 */     int incoming = 0xFF000000 | r << 16 | g << 8 | b;
/*    */     
/* 71 */     int working = incoming;
/* 72 */     SkyCustomizer module = SkyCustomizer.getInstance();
/* 73 */     if (module != null && module.isEnabled()) {
/* 74 */       float brightness = world.method_23783(1.0F);
/* 75 */       working = module.applyToSky(incoming, brightness);
/*    */     } 
/*    */     
/* 78 */     int corrected = WorldColorCorrectionController.applyToArgb(WorldColorCorrectionController.Target.SKY, 0xFF000000 | working & 0xFFFFFF);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 83 */     return new Vector3d((corrected >> 16 & 0xFF) / 255.0D, (corrected >> 8 & 0xFF) / 255.0D, (corrected & 0xFF) / 255.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int clamp255(int v) {
/* 91 */     if (v < 0) return 0; 
/* 92 */     if (v > 255) return 255; 
/* 93 */     return v;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\iris\IrisSkyColorUniformMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */