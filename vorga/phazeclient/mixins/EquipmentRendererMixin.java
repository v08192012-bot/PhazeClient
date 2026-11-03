/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10197;
/*    */ import net.minecraft.class_4608;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import vorga.phazeclient.implement.features.modules.other.HitColor;
/*    */ import vorga.phazeclient.implement.hitcolor.OverlayRendered;
/*    */ 
/*    */ @Mixin({class_10197.class})
/*    */ public abstract class EquipmentRendererMixin implements OverlayRendered {
/*    */   @Unique
/* 15 */   private int phaze$overlay = class_4608.field_21444;
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
/*    */   @ModifyArg(method = {"render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"), index = 3, require = 1)
/*    */   private int phaze$applyOverlayToTintedArmor(int overlay) {
/* 28 */     return phaze$shouldRenderHurtOverlay() ? this.phaze$overlay : overlay;
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
/*    */   @ModifyArg(method = {"render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"), index = 3, require = 1)
/*    */   private int phaze$applyOverlayToArmorTrim(int overlay) {
/* 41 */     return phaze$shouldRenderHurtOverlay() ? this.phaze$overlay : overlay;
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private boolean phaze$shouldRenderHurtOverlay() {
/* 46 */     HitColor module = HitColor.getInstance();
/* 47 */     return (module.isEnabled() && module.showDamageInArmor
/* 48 */       .isValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOverlay(int overlay) {
/* 53 */     this.phaze$overlay = overlay;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EquipmentRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */