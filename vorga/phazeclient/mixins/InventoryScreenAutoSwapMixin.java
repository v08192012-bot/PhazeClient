/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_490;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.base.util.ServerUtil;
/*    */ import vorga.phazeclient.implement.features.modules.other.AutoSwap;
/*    */ 
/*    */ 
/*    */ @Mixin({class_490.class})
/*    */ public class InventoryScreenAutoSwapMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRenderInventory(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 18 */     if (AutoSwap.getInstance().isSwapping() && (
/* 19 */       ServerUtil.isFunTimeServer() || ServerUtil.isFunTrainerServer()))
/* 20 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\InventoryScreenAutoSwapMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */