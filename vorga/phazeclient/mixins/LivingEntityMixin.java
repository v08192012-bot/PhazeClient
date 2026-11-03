/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1282;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_310;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.hud.ComboCounterHud;
/*    */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1309.class})
/*    */ public class LivingEntityMixin
/*    */ {
/*    */   @Inject(method = {"onDamaged"}, at = {@At("HEAD")})
/*    */   private void phaze$onDamaged(class_1282 source, CallbackInfo ci) {
/* 21 */     class_1309 self = (class_1309)this;
/* 22 */     if (!(self.method_37908()).field_9236)
/* 23 */       return;  if (!(self instanceof net.minecraft.class_1657))
/* 24 */       return;  class_310 mc = class_310.method_1551();
/* 25 */     if (mc.field_1724 == null || !mc.field_1724.equals(self))
/* 26 */       return;  ComboCounterHud.getInstance().onHitByEnemy();
/*    */   }
/*    */   
/*    */   @Inject(method = {"getHandSwingDuration"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void phaze$modifySwingDuration(CallbackInfoReturnable<Integer> cir) {
/* 31 */     OldAnimations mod = OldAnimations.getInstance();
/* 32 */     if (mod == null || !mod.isEnabled())
/* 33 */       return;  class_1309 self = (class_1309)this;
/* 34 */     class_310 mc = class_310.method_1551();
/* 35 */     if (mc.field_1724 != null && mc.field_1724.equals(self)) {
/* 36 */       float speed = mod.swingSpeed.getValue();
/* 37 */       if (Math.abs(speed - 1.0F) > 0.02F) {
/* 38 */         int base = ((Integer)cir.getReturnValue()).intValue();
/* 39 */         int modified = Math.max(1, Math.round(base / speed));
/* 40 */         cir.setReturnValue(Integer.valueOf(modified));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\LivingEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */