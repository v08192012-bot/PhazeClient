/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_757;
/*     */ import net.minecraft.class_9779;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.base.util.animation.Interpolation;
/*     */ import vorga.phazeclient.base.util.animation.Interpolations;
/*     */ import vorga.phazeclient.implement.features.modules.other.Zoom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_757.class})
/*     */ public abstract class GameRendererZoomMixin
/*     */ {
/*     */   @Unique
/*  27 */   private static double zoom$lastZoomDivisor = 1.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private static boolean zoom$wasActive = false;
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private static boolean zoom$isZoomingIn = false;
/*     */ 
/*     */   
/*     */   @Unique
/*  40 */   private static double zoom$animStart = 1.0D;
/*     */   @Unique
/*  42 */   private static long zoom$animStartedAtMs = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*  51 */   private static double zoom$lastTarget = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"getFov"}, at = @At(value = "RETURN", shift = At.Shift.BEFORE), ordinal = 1)
/*     */   private float injectZoom(float fov) {
/*     */     float targetZoom;
/*  64 */     if (Zoom.getInstance().isEnabled() && Zoom.isZoomActive()) {
/*  65 */       targetZoom = Zoom.getInstance().getCurrentZoomLevel();
/*     */     } else {
/*  67 */       targetZoom = 1.0F;
/*     */     } 
/*  69 */     if (!Float.isFinite(targetZoom) || targetZoom < 1.0F) {
/*  70 */       targetZoom = 1.0F;
/*     */     }
/*  72 */     if (!Double.isFinite(zoom$lastZoomDivisor) || zoom$lastZoomDivisor < 1.0D) {
/*  73 */       zoom$lastZoomDivisor = 1.0D;
/*     */     }
/*  75 */     if (!Double.isFinite(zoom$animStart) || zoom$animStart < 1.0D) {
/*  76 */       zoom$animStart = zoom$lastZoomDivisor;
/*     */     }
/*  78 */     if (!Double.isFinite(zoom$lastTarget) || zoom$lastTarget < 1.0D) {
/*  79 */       zoom$lastTarget = 1.0D;
/*     */     }
/*     */     
/*  82 */     boolean active = Zoom.isZoomActive();
/*  83 */     boolean directionFlipped = false;
/*  84 */     if (active && !zoom$wasActive) {
/*  85 */       zoom$isZoomingIn = true;
/*  86 */       directionFlipped = true;
/*  87 */     } else if (!active && zoom$wasActive) {
/*  88 */       zoom$isZoomingIn = false;
/*  89 */       directionFlipped = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     boolean targetChanged = (active && Math.abs(targetZoom - zoom$lastTarget) > 0.001D);
/*     */     
/*  99 */     if (directionFlipped || targetChanged) {
/* 100 */       zoom$animStart = zoom$lastZoomDivisor;
/* 101 */       zoom$animStartedAtMs = System.currentTimeMillis();
/*     */     } 
/* 103 */     zoom$lastTarget = targetZoom;
/*     */ 
/*     */ 
/*     */     
/* 107 */     String curve = zoom$isZoomingIn ? Zoom.getInstance().getZoomInInterpolation() : Zoom.getInstance().getZoomOutInterpolation();
/*     */     
/* 109 */     if ("Default".equals(curve)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 115 */       float animationSpeed, duration = zoom$isZoomingIn ? Zoom.getInstance().getZoomInDuration() : Zoom.getInstance().getZoomOutDuration();
/*     */       
/* 117 */       if (zoom$isZoomingIn) {
/* 118 */         animationSpeed = 1.0F / duration;
/*     */       } else {
/* 120 */         float currentZoom = (float)zoom$lastZoomDivisor;
/* 121 */         float baseSpeed = 1.0F / duration;
/* 122 */         if (currentZoom > 50.0F) {
/* 123 */           float multiplier = Math.min(currentZoom / 50.0F, 5.0F);
/* 124 */           animationSpeed = baseSpeed * multiplier;
/*     */         } else {
/* 126 */           animationSpeed = baseSpeed;
/*     */         } 
/*     */       } 
/* 129 */       zoom$lastZoomDivisor += 0.45D * (targetZoom - zoom$lastZoomDivisor) * zoom$frameStep(animationSpeed);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 149 */       float duration = zoom$isZoomingIn ? Zoom.getInstance().getZoomInDuration() : Zoom.getInstance().getZoomOutDuration();
/* 150 */       duration *= 0.35F;
/* 151 */       if (duration < 0.01F) duration = 0.01F; 
/* 152 */       long elapsed = System.currentTimeMillis() - zoom$animStartedAtMs;
/* 153 */       float t = (float)elapsed / duration * 1000.0F;
/* 154 */       if (t < 0.0F) t = 0.0F; 
/* 155 */       if (t > 1.0F) t = 1.0F; 
/* 156 */       Interpolation interp = Interpolations.getByName(curve);
/* 157 */       float eased = (float)interp.interpolate(t);
/* 158 */       zoom$lastZoomDivisor = zoom$animStart + (targetZoom - zoom$animStart) * eased;
/*     */     } 
/* 160 */     if (!Double.isFinite(zoom$lastZoomDivisor) || zoom$lastZoomDivisor < 1.0D) {
/* 161 */       zoom$lastZoomDivisor = 1.0D;
/*     */     }
/*     */     
/* 164 */     zoom$wasActive = active;
/*     */     
/* 166 */     return (float)(fov / zoom$lastZoomDivisor);
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("RETURN")})
/*     */   private void onRender(class_9779 tickCounter, boolean tick, CallbackInfo ci) {}
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private static float zoom$frameStep(float animationSpeed) {
/* 176 */     return class_310.method_1551().method_61966().method_60636() * animationSpeed;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\GameRendererZoomMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */