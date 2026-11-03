/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_364;
/*    */ import net.minecraft.class_4185;
/*    */ import net.minecraft.class_433;
/*    */ import net.minecraft.class_437;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.ColorCorrection;
/*    */ 
/*    */ @Mixin({class_433.class})
/*    */ public abstract class GameMenuScreenWorldColorButtonMixin extends class_437 {
/*    */   protected GameMenuScreenWorldColorButtonMixin(class_2561 title) {
/* 17 */     super(title);
/*    */   }
/*    */   
/*    */   @Inject(method = {"init"}, at = {@At("TAIL")})
/*    */   private void phaze$addWorldColorButton(CallbackInfo ci) {
/* 22 */     class_433 self = (class_433)this;
/* 23 */     if (!self.method_53558()) {
/*    */       return;
/*    */     }
/*    */     
/* 27 */     method_37063(
/* 28 */         (class_364)class_4185.method_46430(
/* 29 */           (class_2561)class_2561.method_43470("Цвет мира"), button -> ColorCorrection.openWorldColorMenu())
/*    */ 
/*    */         
/* 32 */         .method_46434(this.field_22789 - 86, 10, 76, 20)
/* 33 */         .method_46431());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\GameMenuScreenWorldColorButtonMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */