/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.lang.reflect.Method;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_4608;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitColor;
/*     */ import vorga.phazeclient.implement.hitcolor.OverlayReloadListener;
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
/*     */ @Mixin({class_4608.class})
/*     */ public class OverlayTextureMixin
/*     */   implements OverlayReloadListener
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private class_1043 field_21013;
/*     */   @Unique
/*     */   private static final int phaze$VANILLA_HURT_ARGB = -1291911168;
/*     */   @Unique
/*     */   private static Method phaze$setColorMethod;
/*     */   @Unique
/*     */   private static Method phaze$uploadMethod;
/*     */   @Unique
/*     */   private static boolean phaze$reflectionResolved;
/*     */   @Unique
/*     */   private static boolean phaze$reflectionFailed;
/*     */   @Unique
/*  56 */   private int phaze$appliedArgb = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"<init>"}, at = {@At("TAIL")})
/*     */   private void onInit(CallbackInfo ci) {
/*  64 */     setColor();
/*  65 */     OverlayReloadListener.registerOverlay(this);
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static int getColorInt(int red, int green, int blue, int alpha) {
/*  70 */     alpha = 255 - alpha;
/*  71 */     return alpha << 24 | red << 16 | green << 8 | blue;
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static void phaze$resolveReflection() {
/*  76 */     if (phaze$reflectionResolved) {
/*     */       return;
/*     */     }
/*  79 */     phaze$reflectionResolved = true;
/*     */     
/*     */     try {
/*  82 */       phaze$setColorMethod = class_1011.class.getDeclaredMethod("method_61941", new Class[] { int.class, int.class, int.class });
/*     */       
/*  84 */       phaze$setColorMethod.setAccessible(true);
/*  85 */       phaze$uploadMethod = class_1011.class.getDeclaredMethod("method_22619", new Class[] { int.class, int.class, int.class, int.class, int.class, int.class, int.class, boolean.class });
/*     */ 
/*     */       
/*  88 */       phaze$uploadMethod.setAccessible(true);
/*  89 */     } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       phaze$reflectionFailed = true;
/*  95 */       phaze$setColorMethod = null;
/*  96 */       phaze$uploadMethod = null;
/*  97 */       System.err.println("[Phaze] HitColor overlay unavailable, using vanilla hurt color: " + String.valueOf(t));
/*     */     } 
/*     */   }
/*     */   public void setColor() {
/*     */     int argb;
/* 102 */     phaze$resolveReflection();
/* 103 */     if (phaze$reflectionFailed) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     class_1011 nativeImage = this.field_21013.method_4525();
/* 108 */     if (nativeImage == null) {
/*     */       return;
/*     */     }
/*     */     
/* 112 */     HitColor module = HitColor.getInstance();
/*     */     
/* 114 */     if (module.isEnabled() && module.customHitcolor.isValue()) {
/* 115 */       int hitColor = module.getHitColor();
/* 116 */       int red = hitColor >> 16 & 0xFF;
/* 117 */       int green = hitColor >> 8 & 0xFF;
/* 118 */       int blue = hitColor & 0xFF;
/* 119 */       int alpha = hitColor >> 24 & 0xFF;
/* 120 */       argb = getColorInt(red, green, blue, alpha);
/*     */     } else {
/* 122 */       argb = -1291911168;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (argb == this.phaze$appliedArgb) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 135 */       for (int i = 0; i < 8; i++) {
/* 136 */         for (int j = 0; j < 16; j++) {
/* 137 */           phaze$setColorMethod.invoke(nativeImage, new Object[] { Integer.valueOf(j), Integer.valueOf(i), Integer.valueOf(argb) });
/*     */         } 
/*     */       } 
/*     */       
/* 141 */       RenderSystem.activeTexture(33985);
/* 142 */       this.field_21013.method_23207();
/* 143 */       phaze$uploadMethod.invoke(nativeImage, new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), 
/* 144 */             Integer.valueOf(nativeImage.method_4307()), Integer.valueOf(nativeImage.method_4323()), Boolean.valueOf(false) });
/* 145 */       RenderSystem.activeTexture(33984);
/* 146 */     } catch (Throwable t) {
/* 147 */       phaze$reflectionFailed = true;
/* 148 */       System.err.println("[Phaze] HitColor overlay disabled after upload failure: " + String.valueOf(t));
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     this.phaze$appliedArgb = argb;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\OverlayTextureMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */