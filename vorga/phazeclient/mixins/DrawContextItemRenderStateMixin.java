/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_332;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_332.class})
/*    */ public abstract class DrawContextItemRenderStateMixin
/*    */ {
/*    */   @Inject(method = {"drawItem(Lnet/minecraft/item/ItemStack;II)V"}, at = {@At("HEAD")})
/*    */   private void phaze$resetStateBeforeDrawItem(class_1799 stack, int x, int y, CallbackInfo ci) {
/* 21 */     float alpha = RenderSystem.getShaderColor()[3];
/* 22 */     RenderSystem.depthMask(true);
/* 23 */     RenderSystem.enableDepthTest();
/* 24 */     RenderSystem.defaultBlendFunc();
/* 25 */     RenderSystem.disableBlend();
/* 26 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\DrawContextItemRenderStateMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */