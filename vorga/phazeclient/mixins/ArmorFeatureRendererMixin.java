/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10197;
/*    */ import net.minecraft.class_970;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import vorga.phazeclient.implement.hitcolor.OverlayRendered;
/*    */ 
/*    */ @Mixin({class_970.class})
/*    */ public class ArmorFeatureRendererMixin
/*    */   implements OverlayRendered {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_10197 field_54183;
/*    */   
/*    */   public void setOverlay(int overlay) {
/* 18 */     class_10197 class_101971 = this.field_54183; if (class_101971 instanceof OverlayRendered) { OverlayRendered rendered = (OverlayRendered)class_101971;
/* 19 */       rendered.setOverlay(overlay); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ArmorFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */