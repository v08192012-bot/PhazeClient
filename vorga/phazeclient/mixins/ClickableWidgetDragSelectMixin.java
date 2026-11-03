/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_339;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_3532;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_339.class})
/*     */ public abstract class ClickableWidgetDragSelectMixin
/*     */ {
/*     */   @Inject(method = {"mouseDragged"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$dragSelect(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
/*     */     class_342 tf;
/*  45 */     if (button != 0)
/*  46 */       return;  if (!Animations.getInstance().isComfortableTextSelectionEnabled())
/*     */       return; 
/*  48 */     Object self = this;
/*  49 */     if (self instanceof class_342) { tf = (class_342)self; } else { return; }
/*  50 */      if (!tf.field_22763 || !tf.field_22764) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (!tf.method_25370()) tf.method_25365(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     int x = tf.method_46426();
/*  68 */     int relX = class_3532.method_15357(mouseX) - x;
/*  69 */     int outerW = tf.method_25368();
/*  70 */     int innerW = tf.method_1859();
/*  71 */     int inset = (outerW - innerW) / 2;
/*  72 */     if (inset > 0) {
/*  73 */       relX -= inset;
/*     */     }
/*  75 */     if (relX < 0) relX = 0;
/*     */     
/*  77 */     class_327 tr = (class_310.method_1551()).field_1772;
/*  78 */     if (tr == null)
/*     */       return; 
/*  80 */     String text = tf.method_1882();
/*  81 */     if (text == null) text = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     int idx = 0;
/*  91 */     int best = 0;
/*  92 */     int bestDist = Integer.MAX_VALUE;
/*  93 */     for (int i = 0; i <= text.length(); i++) {
/*  94 */       int w = tr.method_1727(text.substring(0, i));
/*  95 */       int dist = Math.abs(w - relX);
/*  96 */       if (dist < bestDist) {
/*  97 */         bestDist = dist;
/*  98 */         best = i;
/*     */       } 
/* 100 */       if (w > relX + 4)
/* 101 */         break;  idx = i;
/*     */     } 
/*     */     
/* 104 */     int chosen = (bestDist < Integer.MAX_VALUE) ? best : idx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     tf.method_1875(chosen);
/* 118 */     cir.setReturnValue(Boolean.valueOf(true));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClickableWidgetDragSelectMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */