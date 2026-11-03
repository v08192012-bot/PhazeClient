/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3966;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4618;
/*     */ import net.minecraft.class_897;
/*     */ import net.minecraft.class_898;
/*     */ import net.minecraft.class_9974;
/*     */ import org.joml.Vector3f;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.api.system.nametag.NametagBlurStorage;
/*     */ import vorga.phazeclient.api.system.render.Render3DUtil;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitboxCustomizer;
/*     */ import vorga.phazeclient.util.render.PhazeRenderLayers;
/*     */ 
/*     */ @Mixin({class_898.class})
/*     */ public class EntityRenderDispatcherMixin
/*     */ {
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void startNametagBatching(CallbackInfo ci) {
/*  33 */     NametagBlurStorage.setEnabled(true);
/*  34 */     NametagBlurStorage.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Redirect(method = {"render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;renderHitbox(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;FFFF)V"))
/*     */   private <E extends class_1297> void phaze$redirectHitboxRender(class_4587 matrices, class_4588 vertices, class_1297 entity, float tickDelta, float red, float green, float blue, E renderedEntity, double x, double y, double z, float renderTickDelta, class_4587 renderMatrices, class_4597 vertexConsumers, int light, class_897<? super E, ?> renderer) {
/*  62 */     HitboxCustomizer module = HitboxCustomizer.getInstance();
/*  63 */     if (!module.isEnabled()) {
/*  64 */       phaze$invokeVanillaRenderHitbox(matrices, vertices, entity, tickDelta, red, green, blue);
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     class_4597.class_4598 immediate = phaze$getImmediate(vertexConsumers);
/*     */     
/*  70 */     int colorInt = module.getHitboxColor();
/*  71 */     if (module.redInReach.isValue() && phaze$isCrosshairTarget(entity)) {
/*  72 */       colorInt = module.reachColor.getColor();
/*     */     }
/*     */     
/*  75 */     float a = (colorInt >>> 24 & 0xFF) / 255.0F;
/*  76 */     float r = (colorInt >>> 16 & 0xFF) / 255.0F;
/*  77 */     float g = (colorInt >>> 8 & 0xFF) / 255.0F;
/*  78 */     float b = (colorInt & 0xFF) / 255.0F;
/*     */     
/*  80 */     class_238 box = entity.method_5829().method_989(-entity.method_23317(), -entity.method_23318(), -entity.method_23321());
/*     */     
/*  82 */     if (immediate != null) {
/*  83 */       immediate.method_22993();
/*     */     }
/*     */     
/*  86 */     float thickness = Math.max(1.0F, module.outlineThickness.getValue());
/*  87 */     if (module.isFillEnabled()) {
/*  88 */       class_1921 fillLayer = PhazeRenderLayers.getHitboxFill();
/*  89 */       class_4588 fillConsumer = vertexConsumers.getBuffer(fillLayer);
/*  90 */       Render3DUtil.vertexBoxFill(matrices, fillConsumer, box, colorInt, module.getFillOpacity());
/*  91 */       if (immediate != null) {
/*  92 */         immediate.method_22994(fillLayer);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     class_1921 lineLayer = (thickness > 1.0F) ? PhazeRenderLayers.getThickLines(thickness) : class_1921.method_23594();
/*  99 */     class_4588 lineConsumer = vertexConsumers.getBuffer(lineLayer);
/* 100 */     class_9974.method_62295(matrices, lineConsumer, box, r, g, b, a);
/*     */     
/* 102 */     if (module.showLookLine.isValue()) {
/* 103 */       class_9974.method_62298(matrices, lineConsumer, new Vector3f(0.0F, entity
/*     */ 
/*     */             
/* 106 */             .method_5751(), 0.0F), entity
/* 107 */           .method_5828(tickDelta).method_1021(2.0D), -16776961);
/*     */     }
/*     */ 
/*     */     
/* 111 */     if (immediate != null) {
/* 112 */       immediate.method_22994(lineLayer);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class_4597.class_4598 phaze$getImmediate(class_4597 provider) {
/* 117 */     if (provider instanceof class_4597.class_4598) { class_4597.class_4598 immediate = (class_4597.class_4598)provider;
/* 118 */       return immediate; }
/*     */     
/* 120 */     if (provider instanceof class_4618) { class_4618 outline = (class_4618)provider;
/* 121 */       return ((OutlineVertexConsumerProviderAccessor)outline).phaze$getParent(); }
/*     */     
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean phaze$isCrosshairTarget(class_1297 entity) {
/* 127 */     class_310 mc = class_310.method_1551();
/* 128 */     if (mc == null) return false; 
/* 129 */     class_239 class_239 = mc.field_1765; if (class_239 instanceof class_3966) { class_3966 ehr = (class_3966)class_239;
/* 130 */       return (ehr.method_17782() == entity); }
/*     */     
/* 132 */     return false;
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
/*     */   @Invoker("renderHitbox")
/*     */   private static void phaze$invokeVanillaRenderHitbox(class_4587 matrices, class_4588 vertices, class_1297 entity, float tickDelta, float red, float green, float blue) {
/* 145 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EntityRenderDispatcherMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */