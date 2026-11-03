/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1661;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.AutoEat;
/*    */ import vorga.phazeclient.implement.features.modules.other.LockSlot;
/*    */ import vorga.phazeclient.implement.features.modules.other.PotionAuto;
/*    */ 
/*    */ @Mixin({class_1661.class})
/*    */ public class PlayerInventoryPotionMixin
/*    */ {
/*    */   @Shadow
/*    */   public int field_7545;
/*    */   
/*    */   @Inject(method = {"setSelectedSlot"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$onSetSelectedSlot(int slot, CallbackInfo ci) {
/* 22 */     PotionAuto potionAuto = PotionAuto.getInstance();
/* 23 */     if (potionAuto != null && potionAuto.isEnabled() && potionAuto.isDrinking()) {
/* 24 */       int lockedSlot = potionAuto.getLockedHotbarSlot();
/* 25 */       if (lockedSlot >= 0 && slot != lockedSlot) {
/* 26 */         this.field_7545 = lockedSlot;
/* 27 */         ci.cancel();
/*    */ 
/*    */ 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 37 */     AutoEat autoEat = AutoEat.getInstance();
/* 38 */     if (autoEat != null && autoEat.isAutoEating()) {
/* 39 */       ci.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"dropSelectedItem"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$onDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
/* 45 */     LockSlot lockSlot = LockSlot.getInstance();
/* 46 */     if (lockSlot != null && lockSlot.isHotbarSlotLocked(this.field_7545)) {
/* 47 */       cir.setReturnValue(Boolean.valueOf(false));
/* 48 */       cir.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PlayerInventoryPotionMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */