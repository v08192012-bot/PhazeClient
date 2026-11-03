/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.class_156;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_4011;
/*    */ import net.minecraft.class_425;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.menu.loading.BootstrapLoadingRenderer;
/*    */ 
/*    */ 
/*    */ @Mixin({class_425.class})
/*    */ public abstract class SplashOverlayMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_310 field_18217;
/*    */   @Shadow
/*    */   @Final
/*    */   private class_4011 field_17767;
/*    */   
/*    */   @Inject(method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"}, at = {@At("HEAD")}, cancellable = true, require = 1)
/*    */   private void phaze$renderLoadingScreen(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 32 */     ci.cancel();
/* 33 */     long now = class_156.method_658();
/* 34 */     if (this.field_17771 == -1L && this.field_17767.method_18787()) {
/*    */       
/* 36 */       this.field_17771 = now;
/* 37 */       Optional<Throwable> failure = Optional.empty();
/*    */       try {
/* 39 */         this.field_17767.method_18849();
/* 40 */       } catch (Throwable throwable) {
/* 41 */         failure = Optional.of(throwable);
/*    */       } 
/* 43 */       this.field_18218.accept(failure);
/*    */ 
/*    */       
/* 46 */       if (this.field_18217.method_18506() != this)
/* 47 */         return;  if (this.field_18217.field_1755 != null) {
/* 48 */         this.field_18217.field_1755.method_25423(this.field_18217, context.method_51421(), context.method_51443());
/*    */       }
/*    */     } 
/*    */     
/* 52 */     float alpha = 1.0F;
/* 53 */     if (this.field_17771 != -1L) {
/* 54 */       float elapsed = (float)(now - this.field_17771) / 1000.0F;
/* 55 */       if (this.field_18217.field_1755 != null) {
/* 56 */         this.field_18217.field_1755.method_25394(context, 0, 0, delta);
/*    */       }
/*    */       
/* 59 */       alpha = 1.0F - class_3532.method_15363((elapsed - 0.2F) / 0.4F, 0.0F, 1.0F);
/* 60 */       if (elapsed >= 0.6F) {
/* 61 */         if (this.field_18217.method_18506() == this) this.field_18217.method_18502(null); 
/*    */         return;
/*    */       } 
/* 64 */       this.field_17770 = 1.0F;
/*    */     } else {
/* 66 */       float target = class_3532.method_15363(this.field_17767.method_18229(), 0.0F, 1.0F);
/* 67 */       this.field_17770 = Math.max(this.field_17770, class_3532.method_16439(0.15F, this.field_17770, target));
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 72 */     context.method_51452();
/* 73 */     BootstrapLoadingRenderer.render(this.field_18217.method_22683().method_4489(), this.field_18217
/* 74 */         .method_22683().method_4506(), this.field_17770, alpha);
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   @Final
/*    */   private Consumer<Optional<Throwable>> field_18218;
/*    */   @Shadow
/*    */   private float field_17770;
/*    */   @Shadow
/*    */   private long field_17771;
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\SplashOverlayMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */