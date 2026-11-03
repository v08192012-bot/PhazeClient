/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10034;
/*    */ import net.minecraft.class_10055;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_3883;
/*    */ import net.minecraft.class_3887;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_4608;
/*    */ import net.minecraft.class_572;
/*    */ import net.minecraft.class_591;
/*    */ import net.minecraft.class_8685;
/*    */ import net.minecraft.class_972;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsState;
/*    */ import vorga.phazeclient.implement.features.modules.other.WaveCapes;
/*    */ import vorga.phazeclient.implement.features.modules.other.WavyCapeRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_972.class})
/*    */ public abstract class CapeFeatureRendererMixin
/*    */   extends class_3887<class_10055, class_591>
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_572<class_10055> field_53206;
/*    */   
/*    */   private CapeFeatureRendererMixin(class_3883<class_10055, class_591> context) {
/* 41 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$renderCape(class_4587 matrices, class_4597 vertexConsumers, int light, class_10055 state, float limbAngle, float limbDistance, CallbackInfo ci) {
/* 54 */     if (state.field_53333 || !state.field_53532)
/*    */       return; 
/* 56 */     WaveCapes waveCapes = WaveCapes.getInstance();
/* 57 */     boolean wavy = (waveCapes != null && waveCapes.isEnabled());
/*    */ 
/*    */     
/* 60 */     String selection = CosmeticsRenderer.capeSelectionFor(state);
/* 61 */     if (CosmeticsState.isCape(selection)) {
/* 62 */       ci.cancel();
/* 63 */       class_2960 texture = CosmeticsRenderer.capeTexture(selection);
/* 64 */       if (texture == null)
/*    */         return; 
/* 66 */       if (wavy) {
/* 67 */         matrices.method_22903();
/* 68 */         ((class_591)method_17165()).field_3391.method_22703(matrices);
/* 69 */         WavyCapeRenderer.renderWavyCape(matrices, vertexConsumers, texture, light, state, "Phaze Cape"
/*    */             
/* 71 */             .equalsIgnoreCase(selection));
/*    */         
/* 73 */         matrices.method_22909();
/*    */       } else {
/* 75 */         matrices.method_22903();
/* 76 */         class_4588 vertices = vertexConsumers.getBuffer(class_1921.method_23572(texture));
/* 77 */         ((class_591)method_17165()).method_64254(this.field_53206);
/* 78 */         this.field_53206.method_17087((class_10034)state);
/* 79 */         this.field_53206.method_60879(matrices, vertices, light, class_4608.field_21444);
/* 80 */         matrices.method_22909();
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 86 */     if (wavy) {
/* 87 */       class_8685 skinTextures = state.field_53520;
/* 88 */       if (skinTextures != null && skinTextures.comp_1627() != null) {
/* 89 */         ci.cancel();
/* 90 */         matrices.method_22903();
/* 91 */         ((class_591)method_17165()).field_3391.method_22703(matrices);
/* 92 */         WavyCapeRenderer.renderWavyCape(matrices, vertexConsumers, skinTextures
/* 93 */             .comp_1627(), light, state, false);
/*    */         
/* 95 */         matrices.method_22909();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\CapeFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */