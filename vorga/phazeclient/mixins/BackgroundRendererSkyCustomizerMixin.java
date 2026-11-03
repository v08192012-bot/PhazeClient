/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_5636;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_758;
/*     */ import org.joml.Vector4f;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import vorga.phazeclient.api.system.colorcorrection.WorldColorCorrectionController;
/*     */ import vorga.phazeclient.implement.features.modules.other.SkyCustomizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin(value = {class_758.class}, priority = 900)
/*     */ public abstract class BackgroundRendererSkyCustomizerMixin
/*     */ {
/*     */   @ModifyReturnValue(method = {"getFogColor"}, at = {@At("RETURN")})
/*     */   private static Vector4f phaze$tintFogColor(Vector4f vanilla, class_4184 camera, float tickDelta, class_638 world, int viewDistance, float skyDarkness) {
/*  67 */     if (vanilla == null) {
/*  68 */       return vanilla;
/*     */     }
/*  70 */     if (camera != null) {
/*  71 */       switch (camera.method_19334()) {
/*     */         case field_27886:
/*     */         case field_27885:
/*     */         case field_27887:
/*  75 */           return vanilla;
/*     */       } 
/*     */ 
/*     */       
/*  79 */       class_1297 class_1297 = camera.method_19331(); if (class_1297 instanceof class_1309) { class_1309 living = (class_1309)class_1297; if (living
/*  80 */           .method_6059(class_1294.field_5919) || living
/*  81 */           .method_6059(class_1294.field_38092)) {
/*  82 */           return vanilla;
/*     */         } }
/*     */     
/*     */     } 
/*  86 */     int r = clamp255(Math.round(vanilla.x * 255.0F));
/*  87 */     int g = clamp255(Math.round(vanilla.y * 255.0F));
/*  88 */     int b = clamp255(Math.round(vanilla.z * 255.0F));
/*  89 */     int incoming = 0xFF000000 | r << 16 | g << 8 | b;
/*     */     
/*  91 */     int working = incoming;
/*  92 */     SkyCustomizer module = SkyCustomizer.getInstance();
/*  93 */     if (module != null && module.isEnabled()) {
/*  94 */       float brightness = (world != null) ? world.method_23783(tickDelta) : 0.5F;
/*  95 */       working = module.applyToSky(incoming, brightness);
/*     */     } 
/*     */     
/*  98 */     int corrected = WorldColorCorrectionController.applyToArgb(WorldColorCorrectionController.Target.SKY, (
/*     */         
/* 100 */         clamp255(Math.round(vanilla.w * 255.0F)) & 0xFF) << 24 | working & 0xFFFFFF);
/*     */ 
/*     */     
/* 103 */     vanilla.x = (corrected >> 16 & 0xFF) / 255.0F;
/* 104 */     vanilla.y = (corrected >> 8 & 0xFF) / 255.0F;
/* 105 */     vanilla.z = (corrected & 0xFF) / 255.0F;
/* 106 */     vanilla.w = (corrected >> 24 & 0xFF) / 255.0F;
/* 107 */     return vanilla;
/*     */   }
/*     */   
/*     */   private static int clamp255(int v) {
/* 111 */     if (v < 0) return 0; 
/* 112 */     if (v > 255) return 255; 
/* 113 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BackgroundRendererSkyCustomizerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */