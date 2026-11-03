/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.WrapWithCondition;
/*     */ import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.class_1259;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_337;
/*     */ import net.minecraft.class_345;
/*     */ import net.minecraft.class_5348;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.api.system.hud.BossBarColorContext;
/*     */ import vorga.phazeclient.api.system.hud.BossBarHudAccess;
/*     */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_337.class})
/*     */ public abstract class BossBarHudMixin
/*     */   implements BossBarHudAccess
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private class_310 field_2058;
/*     */   @Shadow
/*     */   @Final
/*     */   private Map<UUID, class_345> field_2060;
/*     */   
/*     */   public void phaze$updateLayout() {
/*  46 */     BossBarHud.getInstance().updateLayout(this.field_2058, this.field_2060.values()); } @Shadow
/*     */   @Final
/*     */   private static class_2960[] field_45345; @Unique
/*     */   private class_345 phaze$preview; @Invoker("renderBossBar")
/*     */   protected abstract void phaze$renderBossBar(class_332 paramclass_332, int paramInt1, int paramInt2, class_1259 paramclass_1259); @WrapMethod(method = {"render"})
/*  51 */   private void phaze$renderTransformed(class_332 context, Operation<Void> original) { BossBarHud module = BossBarHud.getInstance();
/*  52 */     if (!module.isEnabled() || this.field_2060.isEmpty()) {
/*  53 */       original.call(new Object[] { context });
/*     */       return;
/*     */     } 
/*  56 */     phaze$updateLayout();
/*  57 */     context.method_51448().method_22903();
/*     */     try {
/*  59 */       module.applyTransform(context);
/*  60 */       original.call(new Object[] { context });
/*     */     } finally {
/*  62 */       context.method_51448().method_22909();
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   @WrapWithCondition(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/BossBarHud;renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;)V")}, require = 1)
/*     */   private boolean phaze$renderHealth(class_337 hud, class_332 context, int x, int y, class_1259 bar) {
/*  69 */     BossBarHud module = BossBarHud.getInstance();
/*  70 */     return (!module.isEnabled() || module.renderHealth.isValue());
/*     */   }
/*     */ 
/*     */   
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I")}, require = 1)
/*     */   private int phaze$renderText(class_332 context, class_327 renderer, class_2561 text, int x, int y, int color, Operation<Integer> original) {
/*  76 */     BossBarHud module = BossBarHud.getInstance();
/*  77 */     return (!module.isEnabled() || module.renderText.isValue()) ? ((Integer)original.call(new Object[] { context, renderer, text, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(color) })).intValue() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I")}, require = 1)
/*     */   private int phaze$measureText(class_327 renderer, class_5348 text, Operation<Integer> original) {
/*  83 */     BossBarHud module = BossBarHud.getInstance();
/*  84 */     if (!module.isEnabled()) return ((Integer)original.call(new Object[] { renderer, text })).intValue(); 
/*  85 */     return module.renderText.isValue() ? module.getNameWidth(renderer, text) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @WrapMethod(method = {"renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;I[Lnet/minecraft/util/Identifier;[Lnet/minecraft/util/Identifier;)V"})
/*     */   private void phaze$tintProgress(class_332 context, int x, int y, class_1259 bar, int width, class_2960[] textures, class_2960[] notchedTextures, Operation<Void> original) {
/*  91 */     BossBarHud module = BossBarHud.getInstance();
/*  92 */     int previous = BossBarColorContext.setTint((textures == field_45345 && module.isEnabled()) ? 
/*  93 */         module.getActiveFillColor() : -1);
/*     */     try {
/*  95 */       original.call(new Object[] { context, Integer.valueOf(x), Integer.valueOf(y), bar, Integer.valueOf(width), textures, notchedTextures });
/*     */     } finally {
/*  97 */       BossBarColorContext.setTint(previous);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyArg(method = {"renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;I[Lnet/minecraft/util/Identifier;[Lnet/minecraft/util/Identifier;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIIIIII)V", ordinal = 0), index = 1, require = 1)
/*     */   private class_2960 phaze$neutralProgressTexture(class_2960 texture, @Local(argsOnly = true, ordinal = 0) class_2960[] textures) {
/* 104 */     if (textures == field_45345 && BossBarHud.getInstance().isEnabled())
/* 105 */       for (class_2960 vanilla : field_45345) {
/* 106 */         if (texture.equals(vanilla)) return field_45345[class_1259.class_1260.field_5786.ordinal()];
/*     */       
/*     */       }  
/* 109 */     return texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public void phaze$renderPreview(class_332 context) {
/* 114 */     BossBarHud module = BossBarHud.getInstance();
/* 115 */     if (!this.field_2060.isEmpty() || !module.isEnabled())
/* 116 */       return;  if (this.phaze$preview == null) {
/* 117 */       this.phaze$preview = new class_345(new UUID(0L, 0L), BossBarHud.PREVIEW_NAME, 0.75F, class_1259.class_1260.field_5783, class_1259.class_1261.field_5795, false, false, false);
/*     */     }
/*     */     
/* 120 */     context.method_51448().method_22903();
/*     */     try {
/* 122 */       module.applyTransform(context);
/* 123 */       int centerX = context.method_51421() / 2;
/* 124 */       if (module.renderHealth.isValue()) phaze$renderBossBar(context, centerX - 91, 12, (class_1259)this.phaze$preview); 
/* 125 */       if (module.renderText.isValue()) {
/* 126 */         class_2561 name = this.phaze$preview.method_5414();
/* 127 */         context.method_27535(this.field_2058.field_1772, name, centerX - module.getNameWidth(this.field_2058.field_1772, (class_5348)name) / 2, 3, -1);
/*     */       } 
/*     */     } finally {
/* 130 */       context.method_51448().method_22909();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"handlePacket", "clear"}, at = {@At("TAIL")})
/*     */   private void phaze$invalidateLayout(CallbackInfo ci) {
/* 136 */     BossBarHud.getInstance().invalidateNameWidths();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\BossBarHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */