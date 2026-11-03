/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_5636;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_6854;
/*     */ import net.minecraft.class_758;
/*     */ import net.minecraft.class_9958;
/*     */ import org.joml.Vector4f;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.CustomFog;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_758.class})
/*     */ public class BackgroundRendererCustomFogMixin
/*     */ {
/*     */   @Unique
/*     */   private static boolean phaze$shouldntApplyCustomFog(class_4184 camera, class_758.class_4596 fogType) {
/*  33 */     CustomFog module = CustomFog.getInstance();
/*  34 */     if (module == null || !module.isEnabled()) {
/*  35 */       return true;
/*     */     }
/*  37 */     if (fogType == class_758.class_4596.field_20945 && !module.isAffectSky()) {
/*  38 */       return true;
/*     */     }
/*  40 */     class_5636 submersion = camera.method_19334();
/*  41 */     if (submersion != class_5636.field_27888) {
/*  42 */       return true;
/*     */     }
/*  44 */     class_1297 entity = camera.method_19331();
/*  45 */     if (entity instanceof class_1309) { class_1309 living = (class_1309)entity;
/*  46 */       if (living.method_6059(class_1294.field_5919) || living
/*  47 */         .method_6059(class_1294.field_38092)) {
/*  48 */         return true;
/*     */       } }
/*     */     
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getFogColor"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private static void phaze$getFogColor(class_4184 camera, float tickDelta, class_638 world, int viewDistance, float skyDarkness, CallbackInfoReturnable<Vector4f> cir) {
/*  61 */     Vector4f vanilla = (Vector4f)cir.getReturnValue();
/*  62 */     if (phaze$shouldntApplyCustomFog(camera, class_758.class_4596.field_20946)) {
/*     */       return;
/*     */     }
/*  65 */     CustomFog module = CustomFog.getInstance();
/*  66 */     int rgb = module.getResolvedRgb();
/*  67 */     float r = (rgb >> 16 & 0xFF) / 255.0F;
/*  68 */     float g = (rgb >> 8 & 0xFF) / 255.0F;
/*  69 */     float b = (rgb & 0xFF) / 255.0F;
/*  70 */     cir.setReturnValue(new Vector4f(r, g, b, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"applyFog"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private static void phaze$applyFog(class_4184 camera, class_758.class_4596 fogType, Vector4f color, float viewDistance, boolean thickFog, float tickProgress, CallbackInfoReturnable<class_9958> cir) {
/*  81 */     if (phaze$shouldntApplyCustomFog(camera, fogType)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     cir.setReturnValue(phaze$buildCustomFog());
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static float clamp01(float v) {
/*  93 */     if (v < 0.0F) return 0.0F; 
/*  94 */     if (v > 1.0F) return 1.0F; 
/*  95 */     return v;
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static class_9958 phaze$buildCustomFog() {
/* 100 */     CustomFog module = CustomFog.getInstance();
/* 101 */     float distance = Math.max(2.0F, module.getDistance());
/* 102 */     float density = clamp01(module.getDensity());
/* 103 */     float start = distance * (1.0F - density);
/* 104 */     float end = distance;
/*     */     
/* 106 */     int rgb = module.getResolvedRgb();
/* 107 */     float r = (rgb >> 16 & 0xFF) / 255.0F;
/* 108 */     float g = (rgb >> 8 & 0xFF) / 255.0F;
/* 109 */     float b = (rgb & 0xFF) / 255.0F;
/*     */ 
/*     */ 
/*     */     
/* 113 */     return new class_9958(start, end, class_6854.field_36351, r, g, b, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BackgroundRendererCustomFogMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */