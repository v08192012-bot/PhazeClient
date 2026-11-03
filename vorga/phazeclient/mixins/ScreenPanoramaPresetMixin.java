/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_437;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.menu.MainMenuScreen;
/*    */ import vorga.phazeclient.implement.menu.MenuUiSettings;
/*    */ 
/*    */ @Mixin({class_437.class})
/*    */ public abstract class ScreenPanoramaPresetMixin {
/*    */   @Shadow
/*    */   protected int field_22789;
/*    */   
/*    */   @Inject(method = {"renderPanoramaBackground"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void phaze$renderCustomPanorama(class_332 context, float delta, CallbackInfo ci) {
/* 21 */     class_310 client = class_310.method_1551();
/* 22 */     if (client == null || !MainMenuScreen.isCustomMainMenuEnabled()) {
/*    */       return;
/*    */     }
/* 25 */     MenuUiSettings.getInstance().getSelectedPanoramaPreset().getRenderer().render(context, this.field_22789, this.field_22790, 1.0F);
/* 26 */     ci.cancel();
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   protected int field_22790;
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ScreenPanoramaPresetMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */