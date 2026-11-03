/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_6367;
/*     */ import net.minecraft.class_746;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Mutable;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.api.system.hud.BatchedHudBuffer;
/*     */ import vorga.phazeclient.api.system.hud.HudBuffer;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ReachHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.FakeFps;
/*     */ import vorga.phazeclient.implement.features.modules.other.FastExp;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitRange;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoRender;
/*     */ import vorga.phazeclient.implement.menu.MainMenuScreen;
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
/*     */ @Mixin({class_310.class})
/*     */ public abstract class MinecraftClientMixin
/*     */ {
/*     */   @Unique
/*     */   private boolean phaze$redirectingMainMenuInRender;
/*     */   @Shadow
/*     */   private int field_1752;
/*     */   @Shadow
/*     */   public class_239 field_1765;
/*     */   @Shadow
/*     */   @Mutable
/*     */   private static int field_1738;
/*     */   @Shadow
/*     */   public class_437 field_1755;
/*     */   
/*     */   @Inject(method = {"hasOutline(Lnet/minecraft/entity/Entity;)Z"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$skipGlowOutline(class_1297 entity, CallbackInfoReturnable<Boolean> cir) {
/*  65 */     NoRender mod = NoRender.getInstance();
/*  66 */     if (mod == null || !mod.isEnabled()) {
/*     */       return;
/*     */     }
/*  69 */     if (mod.glowing.isValue()) {
/*  70 */       cir.setReturnValue(Boolean.valueOf(false));
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
/*     */   @Inject(method = {"tick"}, at = {@At("TAIL")})
/*     */   private void phaze$updateHitRangeNearest(CallbackInfo ci) {
/*  83 */     HitRange config = HitRange.getInstance();
/*  84 */     if (!config.isEnabled() || !config.nearestOnly.isValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     class_746 player = (class_310.method_1551()).field_1724;
/*  89 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     class_1657 nearest = player.method_37908().method_8604(player
/*  94 */         .method_23317(), player
/*  95 */         .method_23318(), player
/*  96 */         .method_23321(), config.maxSearchDistance
/*  97 */         .getInt(), e -> !e.equals(player));
/*     */ 
/*     */     
/* 100 */     config.setNearest(nearest);
/*     */   }
/*     */   
/*     */   @Inject(method = {"tick"}, at = {@At("HEAD")})
/*     */   private void phaze$ensureCustomMainMenuBeforeFrame(CallbackInfo ci) {
/* 105 */     class_310 client = (class_310)this;
/* 106 */     if (!MainMenuScreen.isCustomMainMenuEnabled()) {
/*     */       return;
/*     */     }
/* 109 */     if (client.method_18506() != null) {
/*     */       return;
/*     */     }
/*     */     
/* 113 */     if (this.field_1755 == null && client.field_1687 == null) {
/* 114 */       client.method_1507((class_437)new MainMenuScreen());
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     if (this.field_1755 instanceof net.minecraft.class_442 && !(this.field_1755 instanceof MainMenuScreen))
/*     */     {
/* 120 */       client.method_1507((class_437)new MainMenuScreen());
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void phaze$ensureCustomMainMenuDuringRender(boolean tick, CallbackInfo ci) {
/* 126 */     if (this.phaze$redirectingMainMenuInRender) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     class_310 client = (class_310)this;
/* 131 */     if (!MainMenuScreen.isCustomMainMenuEnabled()) {
/*     */       return;
/*     */     }
/* 134 */     if (client.method_18506() != null) {
/*     */       return;
/*     */     }
/*     */     
/* 138 */     boolean shouldOpenMainMenu = ((this.field_1755 == null && client.field_1687 == null) || (this.field_1755 instanceof net.minecraft.class_442 && !(this.field_1755 instanceof MainMenuScreen)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (!shouldOpenMainMenu) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     this.phaze$redirectingMainMenuInRender = true;
/*     */     try {
/* 149 */       client.method_1507((class_437)new MainMenuScreen());
/*     */     } finally {
/* 151 */       this.phaze$redirectingMainMenuInRender = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"handleInputEvents"}, at = {@At("HEAD")})
/*     */   private void phaze$bypassExperienceBottleCooldown(CallbackInfo ci) {
/* 161 */     if (FastExp.shouldFastThrow()) {
/* 162 */       this.field_1752 = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getFramebuffer"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$redirectFramebufferToBatchCapture(CallbackInfoReturnable<class_276> cir) {
/* 172 */     if (HudBuffer.activeCaptureTarget < 0) {
/*     */       return;
/*     */     }
/* 175 */     class_6367 fbo = BatchedHudBuffer.INSTANCE.getActiveCaptureFramebuffer();
/* 176 */     if (fbo != null) {
/* 177 */       cir.setReturnValue(fbo);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"doAttack"}, at = {@At("HEAD")})
/*     */   private void phaze$reachHudAirSwingReset(CallbackInfoReturnable<Boolean> cir) {
/* 187 */     ReachHud reachHud = ReachHud.getInstance();
/* 188 */     if (!reachHud.isEnabled()) {
/*     */       return;
/*     */     }
/* 191 */     if (this.field_1765 == null || this.field_1765.method_17783() != class_239.class_240.field_1331) {
/* 192 */       reachHud.notifyAirSwing();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getCurrentFps"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$fakeFps(CallbackInfoReturnable<Integer> cir) {
/* 202 */     FakeFps module = FakeFps.getInstance();
/* 203 */     if (module == null || !module.isEnabled()) {
/*     */       return;
/*     */     }
/* 206 */     int fake = module.getFakeFps();
/* 207 */     field_1738 = fake;
/* 208 */     cir.setReturnValue(Integer.valueOf(fake));
/*     */   }
/*     */   
/*     */   @Inject(method = {"getWindowTitle"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$windowTitle(CallbackInfoReturnable<String> cir) {
/* 213 */     cir.setReturnValue("Phaze Client* 1.21.4");
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\MinecraftClientMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */