/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4071;
/*     */ import net.minecraft.class_437;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.implement.features.modules.other.Zoom;
/*     */ import vorga.phazeclient.implement.menu.MainMenuScreen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_310.class})
/*     */ public class ScreenOpenMixin
/*     */ {
/*     */   @Unique
/*     */   private boolean phaze$redirectingTitleScreen;
/*     */   
/*     */   @ModifyVariable(method = {"setScreen"}, at = @At("HEAD"), argsOnly = true)
/*     */   private class_437 phaze$replaceTitleScreenEarly(class_437 screen) {
/*  30 */     if (!MainMenuScreen.isCustomMainMenuEnabled()) {
/*  31 */       return screen;
/*     */     }
/*  33 */     if (screen instanceof net.minecraft.class_442 && !(screen instanceof MainMenuScreen))
/*     */     {
/*  35 */       return (class_437)new MainMenuScreen();
/*     */     }
/*  37 */     return screen;
/*     */   }
/*     */   
/*     */   @ModifyVariable(method = {"setScreen"}, at = @At("STORE"), ordinal = 0)
/*     */   private class_437 phaze$replaceGeneratedTitleScreen(class_437 screen) {
/*  42 */     if (!MainMenuScreen.isCustomMainMenuEnabled()) {
/*  43 */       return screen;
/*     */     }
/*  45 */     if (screen instanceof net.minecraft.class_442 && !(screen instanceof MainMenuScreen))
/*     */     {
/*  47 */       return (class_437)new MainMenuScreen();
/*     */     }
/*  49 */     return screen;
/*     */   }
/*     */   
/*     */   @Inject(method = {"setScreen"}, at = {@At("TAIL")})
/*     */   private void phaze$replaceCurrentTitleScreenAfterSet(class_437 screen, CallbackInfo ci) {
/*  54 */     class_310 client = (class_310)this;
/*  55 */     if (!MainMenuScreen.isCustomMainMenuEnabled()) {
/*     */       return;
/*     */     }
/*  58 */     if (this.phaze$redirectingTitleScreen || !(client.field_1755 instanceof net.minecraft.class_442) || client.field_1755 instanceof MainMenuScreen) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.phaze$redirectingTitleScreen = true;
/*     */     try {
/*  66 */       client.method_1507((class_437)new MainMenuScreen());
/*     */     } finally {
/*  68 */       this.phaze$redirectingTitleScreen = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"setOverlay"}, at = {@At("TAIL")})
/*     */   private void phaze$replaceLingeringTitleScreenAfterOverlayClose(class_4071 overlay, CallbackInfo ci) {
/*  74 */     class_310 client = (class_310)this;
/*  75 */     if (!MainMenuScreen.isCustomMainMenuEnabled()) {
/*     */       return;
/*     */     }
/*  78 */     if (overlay != null) {
/*     */       return;
/*     */     }
/*     */     
/*  82 */     if (client.field_1755 == null && client.field_1687 == null) {
/*  83 */       client.method_1507((class_437)new MainMenuScreen());
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     if (client.field_1755 instanceof net.minecraft.class_442 && !(client.field_1755 instanceof MainMenuScreen))
/*     */     {
/*  89 */       client.method_1507((class_437)new MainMenuScreen());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"setScreen"}, at = {@At("HEAD")})
/*     */   private void onScreenOpen(class_437 screen, CallbackInfo ci) {
/* 102 */     if (screen != null && Zoom.isZoomActive())
/* 103 */       Zoom.setZoomActive(false); 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ScreenOpenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */