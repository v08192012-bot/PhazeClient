/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_7528;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
/*     */ @Mixin({class_7528.class})
/*     */ public abstract class ScrollableWidgetSmoothScrollMixin
/*     */ {
/*     */   @Shadow
/*     */   private double field_39497;
/*     */   @Unique
/*     */   private double phaze$targetScroll;
/*     */   @Unique
/*     */   private double phaze$displayScroll;
/*     */   @Unique
/*  48 */   private long phaze$lastFrameNanos = 0L;
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$initialized = false;
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$insideMouseDragged = false;
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$shouldApply() {
/*  62 */     if (!(this instanceof net.minecraft.class_350)) {
/*  63 */       return false;
/*     */     }
/*  65 */     Animations module = Animations.getInstance();
/*  66 */     return (module != null && module.isListSmoothScrollEnabled());
/*     */   }
/*     */   
/*     */   @Inject(method = {"setScrollY"}, at = {@At("TAIL")})
/*     */   private void phaze$captureTargetAndRestore(double y, CallbackInfo ci) {
/*  71 */     if (!phaze$shouldApply()) {
/*     */       
/*  73 */       this.phaze$displayScroll = this.field_39497;
/*  74 */       this.phaze$targetScroll = this.field_39497;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (this.phaze$insideMouseDragged) {
/*  85 */       this.phaze$displayScroll = this.field_39497;
/*  86 */       this.phaze$targetScroll = this.field_39497;
/*  87 */       this.phaze$initialized = true;
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     this.phaze$targetScroll = this.field_39497;
/*     */     
/*  93 */     if (!this.phaze$initialized) {
/*  94 */       this.phaze$displayScroll = this.field_39497;
/*  95 */       this.phaze$initialized = true;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     this.field_39497 = this.phaze$displayScroll;
/*     */   }
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
/*     */   @Inject(method = {"mouseDragged"}, at = {@At("HEAD")}, require = 0)
/*     */   private void phaze$dragHead(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
/* 119 */     this.phaze$insideMouseDragged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseDragged"}, at = {@At("RETURN")}, require = 0)
/*     */   private void phaze$dragTail(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
/* 126 */     this.phaze$insideMouseDragged = false;
/*     */   }
/*     */   @Inject(method = {"drawScrollbar"}, at = {@At("HEAD")})
/*     */   private void phaze$tickDecay(class_332 context, CallbackInfo ci) {
/*     */     float dt;
/* 131 */     if (!phaze$shouldApply()) {
/*     */       return;
/*     */     }
/* 134 */     if (!this.phaze$initialized) {
/* 135 */       this.phaze$displayScroll = this.field_39497;
/* 136 */       this.phaze$targetScroll = this.field_39497;
/* 137 */       this.phaze$initialized = true;
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     long now = System.nanoTime();
/*     */     
/* 143 */     if (this.phaze$lastFrameNanos == 0L) {
/* 144 */       dt = 0.016666668F;
/*     */     } else {
/* 146 */       dt = (float)(now - this.phaze$lastFrameNanos) / 1.0E9F;
/* 147 */       if (dt > 0.25F) dt = 0.25F; 
/*     */     } 
/* 149 */     this.phaze$lastFrameNanos = now;
/*     */     
/* 151 */     Animations module = Animations.getInstance();
/* 152 */     float smoothness = module.smoothnessForSpeed(module.listSpeed.getValue());
/* 153 */     double decay = Math.pow(smoothness, dt);
/* 154 */     this.phaze$displayScroll = (this.phaze$displayScroll - this.phaze$targetScroll) * decay + this.phaze$targetScroll;
/* 155 */     if (Math.abs(this.phaze$displayScroll - this.phaze$targetScroll) < 0.5D) {
/* 156 */       this.phaze$displayScroll = this.phaze$targetScroll;
/*     */     }
/*     */     
/* 159 */     this.field_39497 = this.phaze$displayScroll;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseScrolled"}, at = {@At("HEAD")}, require = 0)
/*     */   private void phaze$wheelChain(double mouseX, double mouseY, double horizontal, double vertical, CallbackInfoReturnable<Boolean> cir) {
/* 165 */     if (!phaze$shouldApply() || !this.phaze$initialized) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     this.field_39497 = this.phaze$targetScroll;
/*     */   }
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
/*     */   @ModifyArg(method = {"mouseScrolled"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ScrollableWidget;setScrollY(D)V"), require = 0)
/*     */   private double phaze$multiplyWheelDelta(double newScrollY) {
/* 194 */     if (!(this instanceof net.minecraft.class_350)) {
/* 195 */       return newScrollY;
/*     */     }
/* 197 */     Animations module = Animations.getInstance();
/* 198 */     if (module == null) {
/* 199 */       return newScrollY;
/*     */     }
/* 201 */     int lines = module.linesPerScroll();
/* 202 */     if (lines <= 1) {
/* 203 */       return newScrollY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     double anchor = this.phaze$initialized ? this.phaze$targetScroll : this.field_39497;
/* 215 */     return anchor + (newScrollY - anchor) * lines;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ScrollableWidgetSmoothScrollMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */