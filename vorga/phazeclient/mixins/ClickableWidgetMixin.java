/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_339;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
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
/*     */ @Mixin({class_339.class})
/*     */ public abstract class ClickableWidgetMixin
/*     */ {
/*     */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*     */   private void phaze$cursorRequestHand(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/*  42 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*     */       return;
/*     */     }
/*  45 */     class_339 self = (class_339)this;
/*  46 */     if (!self.field_22764) {
/*     */       return;
/*     */     }
/*  49 */     int x = self.method_46426();
/*  50 */     int y = self.method_46427();
/*  51 */     int w = self.method_25368();
/*  52 */     int h = self.method_25364();
/*  53 */     boolean over = (mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h);
/*  54 */     if (!over) {
/*     */       return;
/*     */     }
/*  57 */     if (!self.field_22763) {
/*  58 */       CursorManager.requestNotAllowed();
/*     */       return;
/*     */     } 
/*  61 */     if (self instanceof net.minecraft.class_342) {
/*  62 */       CursorManager.requestBeam();
/*     */       return;
/*     */     } 
/*  65 */     CursorManager.requestHand();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseDragged"}, at = {@At("HEAD")})
/*     */   private void phaze$cursorOnDrag(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
/*  72 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*     */       return;
/*     */     }
/*  75 */     class_339 self = (class_339)this;
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (self instanceof net.minecraft.class_357) {
/*     */       return;
/*     */     }
/*  82 */     if (!phaze$looksLikeSlider(self)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  88 */     CursorManager.notifyScroll(1.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean phaze$looksLikeSlider(class_339 w) {
/*  98 */     String n = w.getClass().getSimpleName();
/*  99 */     if (n == null || n.isEmpty()) {
/* 100 */       return false;
/*     */     }
/* 102 */     String lc = n.toLowerCase(Locale.ROOT);
/* 103 */     return (lc.contains("slider") || lc
/* 104 */       .contains("range") || lc
/* 105 */       .contains("bar"));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClickableWidgetMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */