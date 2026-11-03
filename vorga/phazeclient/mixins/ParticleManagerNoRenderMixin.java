/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2394;
/*     */ import net.minecraft.class_2396;
/*     */ import net.minecraft.class_2398;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_702;
/*     */ import net.minecraft.class_703;
/*     */ import net.minecraft.class_7923;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoRender;
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
/*     */ @Mixin({class_702.class})
/*     */ public abstract class ParticleManagerNoRenderMixin
/*     */ {
/*     */   @Inject(method = {"addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*     */   private void phaze$cancelAdd7(class_2394 parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<class_703> cir) {
/*  63 */     if (phaze$shouldCancel(parameters)) {
/*  64 */       cir.setReturnValue(null);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean phaze$shouldCancel(class_2394 parameters) {
/*  69 */     NoRender mod = NoRender.getInstance();
/*  70 */     if (mod == null || !mod.isEnabled() || parameters == null) {
/*  71 */       return false;
/*     */     }
/*  73 */     if (mod.particles.isValue()) {
/*  74 */       return true;
/*     */     }
/*  76 */     class_2396<?> type = parameters.method_10295();
/*  77 */     if (mod.hitParticles.isValue() && (
/*  78 */       type == class_2398.field_11209 || type == class_2398.field_11205 || type == class_2398.field_11208 || type == class_2398.field_11227))
/*     */     {
/*     */ 
/*     */       
/*  82 */       return true;
/*     */     }
/*     */     
/*  85 */     if (mod.potionParticles.isValue())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       if (type == class_2398.field_11226) {
/*  95 */         return true;
/*     */       }
/*     */     }
/*  98 */     if (mod.splashPotionParticles.isValue())
/*     */     {
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
/* 110 */       if (type == class_2398.field_11245 || type == class_2398.field_11213)
/*     */       {
/* 112 */         return true;
/*     */       }
/*     */     }
/* 115 */     if (mod.foodParticles.isValue())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       if (type == class_2398.field_11218) {
/* 125 */         return true;
/*     */       }
/*     */     }
/* 128 */     if (mod.maceParticles.isValue()) {
/*     */ 
/*     */       
/* 131 */       class_2960 id = class_7923.field_41180.method_10221(type);
/* 132 */       if (id != null) {
/* 133 */         String path = id.method_12832();
/* 134 */         if (path.contains("gust") || path.contains("wind")) {
/* 135 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 139 */     return false;
/*     */   }
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
/*     */   @Inject(method = {"addBlockBreakParticles(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*     */   private void phaze$cancelBreakParticles(class_2338 pos, class_2680 state, CallbackInfo ci) {
/* 154 */     NoRender mod = NoRender.getInstance();
/* 155 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (mod.breakBlockParticles.isValue()) {
/* 163 */       ci.cancel();
/*     */     }
/*     */   }
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
/*     */   @Inject(method = {"addBlockBreakingParticles(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)V"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*     */   private void phaze$cancelBreakingParticles(class_2338 pos, class_2350 direction, CallbackInfo ci) {
/* 180 */     NoRender mod = NoRender.getInstance();
/* 181 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/* 184 */     if (mod.breakBlockParticles.isValue())
/* 185 */       ci.cancel(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ParticleManagerNoRenderMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */