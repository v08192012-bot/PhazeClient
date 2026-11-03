/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import vorga.phazeclient.api.system.hud.BossBarColorContext;
/*    */ 
/*    */ @Mixin({class_332.class})
/*    */ public abstract class DrawContextBossBarMixin
/*    */ {
/*    */   @ModifyVariable(method = {"drawTexturedQuad(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIIFFFFI)V"}, at = @At("HEAD"), argsOnly = true, ordinal = 4, require = 1)
/*    */   private int phaze$bossBarVertexTint(int color) {
/* 14 */     return BossBarColorContext.applyTint(color);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\DrawContextBossBarMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */