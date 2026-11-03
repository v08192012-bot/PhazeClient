/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.class_1011;
/*    */ import net.minecraft.class_10538;
/*    */ import net.minecraft.class_2960;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import vorga.phazeclient.implement.features.modules.other.ThreeDSkinsVoxelizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_10538.class})
/*    */ public abstract class PlayerSkinTextureDownloaderMixin
/*    */ {
/*    */   @Inject(method = {"registerTexture(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/texture/NativeImage;)Ljava/util/concurrent/CompletableFuture;"}, at = {@At("HEAD")})
/*    */   private static void phaze$onRegisterSkinTexture(class_2960 id, class_1011 image, CallbackInfoReturnable<CompletableFuture<class_2960>> cir) {
/* 24 */     ThreeDSkinsVoxelizer.capture(id, image);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PlayerSkinTextureDownloaderMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */