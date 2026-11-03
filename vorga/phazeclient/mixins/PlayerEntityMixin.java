/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_1657;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.hud.ComboCounterHud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1657.class})
/*    */ public abstract class PlayerEntityMixin
/*    */ {
/*    */   @Inject(method = {"attack"}, at = {@At("HEAD")})
/*    */   private void phaze$onAttack(class_1297 target, CallbackInfo ci) {
/*    */     class_1657 player;
/* 26 */     if (target instanceof class_1657) { player = (class_1657)target; } else { return; }
/* 27 */      if (target == this)
/* 28 */       return;  ComboCounterHud.getInstance().onAttack((class_1309)player);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */