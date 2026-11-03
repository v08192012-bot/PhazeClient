/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_521;
/*     */ import net.minecraft.class_5369;
/*     */ import net.minecraft.class_5375;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.implement.features.modules.other.PackOrganizer;
/*     */ import vorga.phazeclient.implement.features.modules.other.PackPreviewRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.other.PackSearch;
/*     */ 
/*     */ 
/*     */ @Mixin({class_5375.class})
/*     */ public abstract class PackScreenMixin
/*     */   extends class_437
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private class_5369 field_25468;
/*     */   @Shadow
/*     */   private class_4185 field_25475;
/*     */   @Unique
/*  38 */   private String phaze$packSearchQuery = ""; @Shadow
/*     */   private class_521 field_25472; @Shadow
/*     */   private class_521 field_25473;
/*     */   @Unique
/*     */   private class_342 phaze$packSearchField;
/*     */   
/*     */   @Shadow
/*     */   private void method_29678() {
/*  46 */     throw new AssertionError("Mixin shadow");
/*     */   }
/*     */   
/*     */   protected PackScreenMixin(class_2561 title) {
/*  50 */     super(title);
/*     */   }
/*     */   
/*     */   @Inject(method = {"init"}, at = {@At("TAIL")})
/*     */   private void phaze$addPackSearchBar(CallbackInfo ci) {
/*  55 */     PackPreviewRenderer.clear(this.field_22787);
/*  56 */     this.phaze$packSearchField = null;
/*  57 */     if (phaze$isPackSearchEnabled()) {
/*  58 */       int w = 180;
/*  59 */       int h = 18;
/*  60 */       int x = this.field_22789 / 2 - w / 2;
/*  61 */       int y = 14;
/*  62 */       class_342 searchField = new class_342(this.field_22793, x, y, w, h, (class_2561)class_2561.method_43470(Lang.translate("Search")));
/*  63 */       searchField.method_47404((class_2561)class_2561.method_43470(Lang.translate("Search packs...")));
/*  64 */       searchField.method_1880(256);
/*     */       
/*  66 */       searchField.method_1852(this.phaze$packSearchQuery);
/*  67 */       searchField.method_1863(query -> {
/*     */             if (!this.phaze$packSearchQuery.equals(query)) {
/*     */               this.phaze$packSearchQuery = query;
/*     */               
/*     */               method_29678();
/*     */               this.field_25472.method_44382(0.0D);
/*     */               this.field_25473.method_44382(0.0D);
/*     */             } 
/*     */           });
/*  76 */       this.phaze$packSearchField = searchField;
/*  77 */       method_37063((class_364)searchField);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"refreshWidgetPositions"}, at = {@At("TAIL")})
/*     */   private void phaze$positionPackSearchBar(CallbackInfo ci) {
/*  83 */     if (this.phaze$packSearchField != null) {
/*  84 */       this.phaze$packSearchField.method_46421(this.field_22789 / 2 - this.phaze$packSearchField.method_25368() / 2);
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"refresh"}, at = {@At("HEAD")})
/*     */   private void phaze$invalidatePackPreviews(CallbackInfo ci) {
/*  90 */     if (this.field_22787 != null) {
/*  91 */       PackPreviewRenderer.clear(this.field_22787);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
/*  97 */     PackOrganizer module = PackOrganizer.getInstance();
/*  98 */     if (module != null && module.isEnabled() && module.packPreviews.isValue()) {
/*  99 */       PackPreviewRenderer.beginFrame();
/*     */     } else {
/* 101 */       PackPreviewRenderer.clear(this.field_22787);
/*     */     } 
/* 103 */     super.method_25394(context, mouseX, mouseY, delta);
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25432() {
/* 108 */     super.method_25432();
/* 109 */     PackPreviewRenderer.clear(this.field_22787);
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"updatePackList"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
/*     */   private Stream<class_5369.class_5371> phaze$filterPacks(Stream<class_5369.class_5371> packs) {
/* 115 */     if (!phaze$isPackSearchEnabled() || this.phaze$packSearchQuery.isEmpty()) {
/* 116 */       return packs;
/*     */     }
/* 118 */     return packs.filter(pack -> PackSearch.matches(this.phaze$packSearchQuery, pack.method_48276(), pack.method_29650().getString(), pack.method_29651().getString()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"updatePackLists"}, at = {@At("TAIL")})
/*     */   private void phaze$keepDoneButtonUsable(CallbackInfo ci) {
/* 124 */     if (phaze$isPackSearchEnabled() && !this.phaze$packSearchQuery.isEmpty())
/*     */     {
/* 126 */       this.field_25475.field_22763 = this.field_25468.method_29643().findAny().isPresent();
/*     */     }
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$isPackSearchEnabled() {
/* 132 */     PackOrganizer module = PackOrganizer.getInstance();
/* 133 */     return (module != null && module.isEnabled() && module.searchBar.isValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyArg(method = {"init"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextWidget;<init>(Lnet/minecraft/text/Text;Lnet/minecraft/client/font/TextRenderer;)V", ordinal = 1), index = 0)
/*     */   private class_2561 phaze$hideDropHint(class_2561 original) {
/* 141 */     return (class_2561)class_2561.method_43473();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PackScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */