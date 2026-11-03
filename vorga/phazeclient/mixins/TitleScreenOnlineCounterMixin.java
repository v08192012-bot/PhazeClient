/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_442;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.base.util.RemoteRulesService;
/*    */ import vorga.phazeclient.implement.menu.AnnouncementOverlay;
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
/*    */ @Mixin({class_442.class})
/*    */ public abstract class TitleScreenOnlineCounterMixin
/*    */ {
/*    */   private static final int PADDING = 4;
/*    */   private static final int TEXT_COLOR = -1;
/*    */   
/*    */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*    */   private void phaze$drawOnlineCounter(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 49 */     class_310 client = class_310.method_1551();
/* 50 */     if (client == null || client.field_1772 == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 56 */     AnnouncementOverlay.render(context);
/* 57 */     if (client.field_1755 instanceof vorga.phazeclient.implement.menu.MainMenuScreen) {
/*    */       return;
/*    */     }
/*    */     
/* 61 */     int count = RemoteRulesService.getInstance().getOnlineCount();
/*    */ 
/*    */     
/* 64 */     String text = (count < 0) ? "Phaze: connecting…" : ("Phaze: " + count + " online");
/*    */ 
/*    */     
/* 67 */     context.method_25303(client.field_1772, text, 4, 4, -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\TitleScreenOnlineCounterMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */