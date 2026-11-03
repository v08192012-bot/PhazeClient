/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_340;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.implement.features.modules.other.BetterF3;
/*     */ import vorga.phazeclient.implement.features.modules.other.BetterF3Renderer;
/*     */ import vorga.phazeclient.implement.features.modules.other.FakeFps;
/*     */ import vorga.phazeclient.implement.features.modules.other.StreamerMode;
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
/*     */ @Mixin({class_340.class})
/*     */ public abstract class DebugHudMixin
/*     */ {
/*  33 */   private static final String[] PHAZE_COORD_PREFIXES = new String[] { "XYZ:", "Block:", "Chunk:" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private static final Pattern PHAZE_FPS_PREFIX = Pattern.compile("^\\d+\\s*fps");
/*     */ 
/*     */   
/*     */   @Inject(method = {"drawLeftText"}, at = {@At("HEAD")}, cancellable = true, require = 1)
/*     */   private void phaze$replaceDebugText(class_332 context, CallbackInfo ci) {
/*  46 */     BetterF3 module = BetterF3.getInstance();
/*  47 */     if (module == null || !module.isEnabled()) {
/*     */       return;
/*     */     }
/*  50 */     BetterF3Renderer.render(context);
/*  51 */     ci.cancel();
/*     */   }
/*     */   
/*     */   @Inject(method = {"drawRightText"}, at = {@At("HEAD")}, cancellable = true, require = 1)
/*     */   private void phaze$hideVanillaRightText(class_332 context, CallbackInfo ci) {
/*  56 */     BetterF3 module = BetterF3.getInstance();
/*  57 */     if (module != null && module.isEnabled()) {
/*  58 */       ci.cancel();
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
/*     */ 
/*     */   
/*     */   @Inject(method = {"getLeftText"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private void phaze$maskCoordinates(CallbackInfoReturnable<List<String>> cir) {
/*  73 */     StreamerMode streamer = StreamerMode.getInstance();
/*  74 */     if (streamer == null || !streamer.isHideCoordinatesEnabled()) {
/*     */       return;
/*     */     }
/*  77 */     List<String> original = (List<String>)cir.getReturnValue();
/*  78 */     if (original == null || original.isEmpty()) {
/*     */       return;
/*     */     }
/*  81 */     List<String> masked = new ArrayList<>(original.size());
/*  82 */     for (String line : original) {
/*  83 */       masked.add(phaze$maskLine(line));
/*     */     }
/*  85 */     cir.setReturnValue(masked);
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"getLeftText"}, at = {@At("RETURN")})
/*     */   private List<String> phaze$rewriteFpsLine(List<String> original) {
/*  90 */     FakeFps module = FakeFps.getInstance();
/*  91 */     if (module == null || !module.isEnabled() || original == null || original.isEmpty()) {
/*  92 */       return original;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     List<String> patched = new ArrayList<>(original);
/*  99 */     for (int i = 0; i < patched.size(); i++) {
/* 100 */       String line = patched.get(i);
/* 101 */       if (line != null) {
/* 102 */         Matcher m = PHAZE_FPS_PREFIX.matcher(line);
/* 103 */         if (m.find()) {
/* 104 */           int fake = module.getFakeFps();
/* 105 */           patched.set(i, "" + fake + " fps" + fake); break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 109 */     return patched;
/*     */   }
/*     */   
/*     */   private static String phaze$maskLine(String line) {
/* 113 */     if (line == null || line.isEmpty()) {
/* 114 */       return line;
/*     */     }
/* 116 */     String probe = line.startsWith(" ") ? line.stripLeading() : line;
/* 117 */     for (String prefix : PHAZE_COORD_PREFIXES) {
/* 118 */       if (probe.startsWith(prefix)) {
/* 119 */         int colon = line.indexOf(':');
/* 120 */         return (colon >= 0) ? (line.substring(0, colon + 1) + " hidden") : "hidden";
/*     */       } 
/*     */     } 
/* 123 */     return line;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\DebugHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */