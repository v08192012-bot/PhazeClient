/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*    */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*    */ import java.io.File;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.class_1011;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_276;
/*    */ import net.minecraft.class_318;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import vorga.phazeclient.implement.features.modules.other.ChatHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_318.class})
/*    */ public abstract class ScreenshotRecorderScreencopyMixin
/*    */ {
/*    */   @WrapOperation(method = {"saveScreenshotInner"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/ScreenshotRecorder;takeScreenshot(Lnet/minecraft/client/gl/Framebuffer;)Lnet/minecraft/client/texture/NativeImage;")})
/*    */   private static class_1011 phaze$screencopyWrapToImage(class_276 framebuffer, Operation<class_1011> original, File file, String fileName, class_276 fb2, Consumer<class_2561> messageReceiver) {
/* 56 */     class_1011 image = (class_1011)original.call(new Object[] { framebuffer });
/* 57 */     ChatHelper helper = ChatHelper.getInstance();
/* 58 */     if (image != null && helper != null && helper.shouldCopyScreenshot()) {
/*    */       try {
/* 60 */         helper.copyImageToClipboardAsync(image, messageReceiver);
/* 61 */       } catch (Throwable t) {
/* 62 */         if (messageReceiver != null) {
/* 63 */           messageReceiver.accept(class_2561.method_43470("Screencopy failed: " + t.getClass().getSimpleName()));
/*    */         }
/*    */       } 
/*    */     }
/* 67 */     return image;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ScreenshotRecorderScreencopyMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */