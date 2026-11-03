/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_10426;
/*     */ import net.minecraft.class_10444;
/*     */ import net.minecraft.class_1306;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_572;
/*     */ import net.minecraft.class_7833;
/*     */ import net.minecraft.class_989;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_989.class})
/*     */ public abstract class HeldItemFeatureRendererPreviewAlphaMixin
/*     */ {
/*     */   @ModifyVariable(method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/ArmedEntityRenderState;FF)V"}, at = @At("HEAD"), argsOnly = true)
/*     */   private class_4597 phaze$fadePreviewHeldItem(class_4597 original) {
/*  35 */     if (!CosmeticsRenderer.isRenderingPreview()) return original; 
/*  36 */     float alpha = CosmeticsRenderer.previewAlpha();
/*  37 */     if (alpha >= 0.999F) return original; 
/*  38 */     return layer -> new AlphaVertexConsumer(original.getBuffer(layer), alpha);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"renderItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderState;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V")})
/*     */   private void phaze$alignSwordBlockingThirdPerson(class_10426 entityState, class_10444 itemState, class_1306 arm, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
/*  57 */     if (entityState instanceof class_10055) { class_10055 playerState = (class_10055)entityState;
/*  58 */       OldAnimations mod = OldAnimations.getInstance();
/*  59 */       if (mod != null && mod.isEnabled() && mod.swordBlocking.isValue()) {
/*  60 */         boolean isMainArm = (arm == playerState.field_55303);
/*  61 */         boolean isBlockingArm = (isMainArm && (playerState.field_55304 == class_572.class_573.field_3406 || playerState.field_55306 == class_572.class_573.field_3406));
/*     */         
/*  63 */         if (isBlockingArm) {
/*  64 */           float sign = (arm == class_1306.field_6182) ? -1.0F : 1.0F;
/*     */           
/*  66 */           matrices.method_22907(class_7833.field_40714.rotationDegrees(sign * 5.0F));
/*  67 */           matrices.method_22907(class_7833.field_40716.rotationDegrees(sign * 30.0F));
/*  68 */           matrices.method_22907(class_7833.field_40718.rotationDegrees(sign * 25.0F));
/*     */         } 
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   private static final class AlphaVertexConsumer implements class_4588 {
/*     */     private final class_4588 delegate;
/*     */     private final float alpha;
/*     */     
/*     */     private AlphaVertexConsumer(class_4588 delegate, float alpha) {
/*  79 */       this.delegate = delegate;
/*  80 */       this.alpha = alpha;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22912(float x, float y, float z) {
/*  85 */       this.delegate.method_22912(x, y, z);
/*  86 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_1336(int red, int green, int blue, int alpha) {
/*  91 */       this.delegate.method_1336(red, green, blue, Math.round(alpha * this.alpha));
/*  92 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_39415(int argb) {
/*  97 */       int sourceAlpha = argb >>> 24;
/*  98 */       int fadedAlpha = Math.round(sourceAlpha * this.alpha);
/*  99 */       this.delegate.method_39415(argb & 0xFFFFFF | fadedAlpha << 24);
/* 100 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22913(float u, float v) {
/* 105 */       this.delegate.method_22913(u, v);
/* 106 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_60796(int u, int v) {
/* 111 */       this.delegate.method_60796(u, v);
/* 112 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22921(int u, int v) {
/* 117 */       this.delegate.method_22921(u, v);
/* 118 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22914(float x, float y, float z) {
/* 123 */       this.delegate.method_22914(x, y, z);
/* 124 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\HeldItemFeatureRendererPreviewAlphaMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */