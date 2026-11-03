/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10034;
/*    */ import net.minecraft.class_10055;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_979;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsRenderer;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsState;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsSyncService;
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
/*    */ @Mixin({class_979.class})
/*    */ public abstract class ElytraFeatureRendererMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$hideElytraBehindCosmetic(class_4587 matrices, class_4597 vertexConsumers, int light, class_10034 state, float limbAngle, float limbDistance, CallbackInfo ci) {
/* 35 */     CosmeticsState cosmetics = CosmeticsState.getInstance();
/* 36 */     class_310 client = class_310.method_1551();
/* 37 */     if (state instanceof class_10055) { class_10055 playerState = (class_10055)state; if (client.field_1724 != null && client.field_1687 != null && cosmetics
/*    */         
/* 39 */         .isHideElytra()) {
/* 40 */         if (playerState.field_53528 == client.field_1724.method_5628()) {
/* 41 */           if (cosmetics.isEquipped() || CosmeticsRenderer.isRenderingPreview()) {
/* 42 */             ci.cancel();
/*    */           }
/*    */           return;
/*    */         } 
/* 46 */         class_1297 entity = client.field_1687.method_8469(playerState.field_53528);
/* 47 */         if (entity instanceof class_1657) { class_1657 player = (class_1657)entity;
/* 48 */           if (CosmeticsSyncService.getInstance()
/* 49 */             .selectionFor(player.method_5667()) != null)
/* 50 */             ci.cancel();  }
/*    */         
/*    */         return;
/*    */       }  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ElytraFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */