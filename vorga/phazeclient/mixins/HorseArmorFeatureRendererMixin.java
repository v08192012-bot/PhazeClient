/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import net.minecraft.class_10197;
/*    */ import net.minecraft.class_4073;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import vorga.phazeclient.implement.hitcolor.OverlayRendered;
/*    */ 
/*    */ @Mixin({class_4073.class})
/*    */ public class HorseArmorFeatureRendererMixin
/*    */   implements OverlayRendered {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_10197 field_54182;
/*    */   
/*    */   public void setOverlay(int coords) {
/* 18 */     class_10197 class_101971 = this.field_54182; if (class_101971 instanceof OverlayRendered) { OverlayRendered rendered = (OverlayRendered)class_101971;
/* 19 */       rendered.setOverlay(coords); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\HorseArmorFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */