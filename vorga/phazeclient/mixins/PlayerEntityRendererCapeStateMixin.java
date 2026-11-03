/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10055;
/*    */ import net.minecraft.class_1007;
/*    */ import net.minecraft.class_1306;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_572;
/*    */ import net.minecraft.class_742;
/*    */ import net.minecraft.class_8685;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsState;
/*    */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1007.class})
/*    */ public abstract class PlayerEntityRendererCapeStateMixin
/*    */ {
/*    */   @Inject(method = {"updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V"}, at = {@At("RETURN")})
/*    */   private void phaze$installCapeState(class_742 player, class_10055 state, float tickDelta, CallbackInfo ci) {
/* 32 */     String selection = CosmeticsRenderer.capeSelectionFor((class_1657)player);
/* 33 */     if (!state.field_53532 || 
/* 34 */       !CosmeticsState.isCape(selection) || state.field_53520 == null)
/*    */       return; 
/* 36 */     class_2960 texture = CosmeticsRenderer.capeTexture(selection);
/* 37 */     if (texture == null)
/*    */       return; 
/* 39 */     class_8685 original = state.field_53520;
/* 40 */     state
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 46 */       .field_53520 = new class_8685(original.comp_1626(), original.comp_1911(), texture, original.comp_1628(), original.comp_1629(), original.comp_1630());
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
/*    */   
/*    */   @Inject(method = {"updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V"}, at = {@At("RETURN")})
/*    */   private void phaze$applyOldAnimationsThirdPerson(class_742 player, class_10055 state, float tickDelta, CallbackInfo ci) {
/* 60 */     if (OldAnimations.isSwordBlockingActive((class_1657)player))
/* 61 */       if (state.field_55303 == class_1306.field_6183) {
/* 62 */         state.field_55304 = class_572.class_573.field_3406;
/* 63 */         if (state.field_55306 == class_572.class_573.field_3406) {
/* 64 */           state.field_55306 = class_572.class_573.field_3410;
/*    */         }
/*    */       } else {
/* 67 */         state.field_55306 = class_572.class_573.field_3406;
/* 68 */         if (state.field_55304 == class_572.class_573.field_3406)
/* 69 */           state.field_55304 = class_572.class_573.field_3410; 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PlayerEntityRendererCapeStateMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */