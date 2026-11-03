/*     */ package vorga.phazeclient.mixins;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_636;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ReachHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.AutoEat;
/*     */ import vorga.phazeclient.implement.features.modules.other.AutoGG;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChangeHand;
/*     */ import vorga.phazeclient.implement.features.modules.other.HealthIndicator;
/*     */ import vorga.phazeclient.implement.features.modules.other.LockSlot;
/*     */ import vorga.phazeclient.implement.features.modules.other.PickaxeNotifier;
/*     */ import vorga.phazeclient.implement.features.modules.other.ShiftTap;
/*     */ import vorga.phazeclient.implement.features.modules.other.TrapTimer;
/*     */ 
/*     */ @Mixin({class_636.class})
/*     */ public class ClientPlayerInteractionManagerMixin {
/*     */   @Inject(method = {"attackEntity"}, at = {@At("HEAD")})
/*     */   private void phaze$recordReach(class_1657 player, class_1297 target, CallbackInfo ci) {
/*  27 */     ReachHud reachHud = ReachHud.getInstance();
/*  28 */     if (reachHud.isEnabled()) {
/*  29 */       reachHud.recordHitDistance(player, target);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     ShiftTap shiftTap = ShiftTap.getInstance();
/*  40 */     if (shiftTap.isEnabled() && target instanceof class_1657) {
/*  41 */       class_1657 victim = (class_1657)target;
/*  42 */       if (!victim.equals(player)) {
/*  43 */         shiftTap.triggerShiftTap();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     if (target instanceof class_1657) { class_1657 victim = (class_1657)target; if (!victim.equals(player)) {
/*  51 */         AutoGG autoGG = AutoGG.getInstance();
/*  52 */         if (autoGG != null) {
/*  53 */           autoGG.recordAttack(victim);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  62 */         HealthIndicator healthIndicator = HealthIndicator.getInstance();
/*  63 */         if (healthIndicator != null) {
/*  64 */           healthIndicator.recordAttack(victim);
/*     */         }
/*     */       }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     ChangeHand changeHand = ChangeHand.getInstance();
/*  74 */     if (changeHand != null) {
/*  75 */       changeHand.onAttackEntity();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"interactItem"}, at = {@At("HEAD")})
/*     */   private void phaze$onInteractItem(class_1657 player, class_1268 hand, CallbackInfoReturnable<class_1269> cir) {
/*  96 */     TrapTimer trapTimer = TrapTimer.getInstance();
/*  97 */     if (trapTimer != null) {
/*  98 */       trapTimer.onItemUse(player, hand);
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
/*     */   @Inject(method = {"attackBlock"}, at = {@At("HEAD")})
/*     */   private void phaze$onAttackBlock(class_2338 pos, class_2350 direction, CallbackInfoReturnable<Boolean> cir) {
/* 113 */     PickaxeNotifier notifications = PickaxeNotifier.getInstance();
/* 114 */     if (notifications != null) {
/* 115 */       notifications.onAttackBlock();
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
/*     */   @Inject(method = {"stopUsingItem"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$preventStopWhileAutoEating(class_1657 player, CallbackInfo ci) {
/* 128 */     AutoEat autoEat = AutoEat.getInstance();
/* 129 */     if (autoEat != null && autoEat.isAutoEating()) {
/* 130 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"clickSlot"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$onClickSlot(int syncId, int slotId, int button, class_1713 actionType, class_1657 player, CallbackInfo ci) {
/* 136 */     if (actionType != class_1713.field_7795) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     LockSlot lockSlot = LockSlot.getInstance();
/* 141 */     if (lockSlot == null || !lockSlot.isEnabled()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 147 */     if (slotId >= 36 && slotId <= 44) {
/* 148 */       int hotbarIndex = slotId - 36;
/* 149 */       if (lockSlot.isHotbarSlotLocked(hotbarIndex)) {
/* 150 */         ci.cancel();
/*     */       }
/* 152 */     } else if (slotId == 45 && 
/* 153 */       lockSlot.isOffhandLocked()) {
/* 154 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClientPlayerInteractionManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */