/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_521;
/*    */ import net.minecraft.class_5369;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.implement.features.modules.other.PackOrganizer;
/*    */ import vorga.phazeclient.implement.features.modules.other.PackPreviewRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_521.class_4271.class})
/*    */ public abstract class ResourcePackEntryPreviewMixin
/*    */ {
/*    */   @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I")}, cancellable = true, require = 1)
/*    */   private void phaze$renderPackPreview(class_332 context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
/* 23 */     PackOrganizer module = PackOrganizer.getInstance();
/* 24 */     if (!hovered || module == null || !module.isEnabled() || !module.packPreviews.isValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     class_5369.class_5371 pack = ((ResourcePackEntryAccessor)this).phaze$getPack();
/* 29 */     if (pack != null && PackPreviewRenderer.render(context, pack.method_48276(), x + 34, y, entryWidth - 44, 
/* 30 */         Math.min(32, entryHeight)))
/* 31 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ResourcePackEntryPreviewMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */