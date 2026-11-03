/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_746;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.base.util.HolyWorldFeatureControlService;
/*     */ import vorga.phazeclient.base.util.RemoteRulesService;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ComboCounterHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.TpsHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.AutoNear;
/*     */ import vorga.phazeclient.implement.features.modules.other.AutoReissue;
/*     */ import vorga.phazeclient.implement.features.modules.other.ColorCorrection;
/*     */ import vorga.phazeclient.implement.features.modules.other.FTHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.FreeLook;
/*     */ import vorga.phazeclient.implement.features.modules.other.LockSlot;
/*     */ import vorga.phazeclient.implement.features.modules.other.MouseClicker;
/*     */ import vorga.phazeclient.implement.features.modules.other.PotionAuto;
/*     */ import vorga.phazeclient.implement.features.modules.other.Predictions;
/*     */ import vorga.phazeclient.implement.features.modules.other.ShiftTap;
/*     */ import vorga.phazeclient.implement.features.modules.other.TotemTracker;
/*     */ import vorga.phazeclient.implement.features.modules.other.TrapTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_746.class})
/*     */ public class ClientPlayerEntityMixin
/*     */ {
/*  38 */   private static String phaze$lastObservedHost = null;
/*     */   
/*     */   @Inject(method = {"requestRespawn"}, at = {@At("HEAD")})
/*     */   private void onRequestRespawn(CallbackInfo ci) {
/*  42 */     ComboCounterHud.getInstance().onWorldJoin();
/*     */   }
/*     */   
/*     */   @Inject(method = {"tick"}, at = {@At("HEAD")})
/*     */   private void onTick(CallbackInfo ci) {
/*  47 */     phaze$detectHostChange();
/*  48 */     phaze$enforceServerLocks();
/*  49 */     ShiftTap.getInstance().onTick();
/*  50 */     AutoNear.getInstance().tick();
/*  51 */     PotionAuto.getInstance().tick();
/*  52 */     FreeLook.getInstance().tick();
/*  53 */     AutoReissue.getInstance().tick();
/*  54 */     ColorCorrection.getInstance().tick();
/*  55 */     MouseClicker.getInstance().onTick();
/*     */ 
/*     */ 
/*     */     
/*  59 */     TotemTracker.getInstance().pruneStaleEntries();
/*     */ 
/*     */ 
/*     */     
/*  63 */     TrapTimer.getInstance().tick();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     FTHelper.getInstance().tickTrackedSnowballs();
/*     */     
/*  70 */     Predictions.getInstance().tickTrails();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     class_310 mc = class_310.method_1551();
/*  77 */     if (mc != null && mc.field_1687 != null) {
/*  78 */       TpsHud.getInstance()
/*  79 */         .recordSample(mc.field_1687.method_8510());
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void phaze$detectHostChange() {
/* 102 */     String current = ServerUtil.getCurrentServerHost();
/* 103 */     if (current == null) {
/* 104 */       current = "";
/*     */     }
/* 106 */     if (!current.equals(phaze$lastObservedHost)) {
/* 107 */       phaze$lastObservedHost = current;
/* 108 */       RemoteRulesService.getInstance().requestRefresh();
/* 109 */       HolyWorldFeatureControlService.getInstance().onHostChange();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"dropSelectedItem"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$onDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
/* 115 */     LockSlot lockSlot = LockSlot.getInstance();
/* 116 */     if (lockSlot == null || !lockSlot.isEnabled()) {
/*     */       return;
/*     */     }
/* 119 */     class_746 self = (class_746)this;
/* 120 */     class_1661 inventory = self.method_31548();
/* 121 */     if (lockSlot.isHotbarSlotLocked(inventory.field_7545)) {
/* 122 */       cir.setReturnValue(Boolean.valueOf(false));
/* 123 */       cir.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void phaze$enforceServerLocks() {
/* 128 */     HolyWorldFeatureControlService.enforceServerLocks();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClientPlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */