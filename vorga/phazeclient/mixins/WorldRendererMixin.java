/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3726;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4599;
/*     */ import net.minecraft.class_5365;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_757;
/*     */ import net.minecraft.class_761;
/*     */ import net.minecraft.class_897;
/*     */ import net.minecraft.class_898;
/*     */ import net.minecraft.class_9779;
/*     */ import net.minecraft.class_9909;
/*     */ import net.minecraft.class_9922;
/*     */ import net.minecraft.class_9925;
/*     */ import net.minecraft.class_9958;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.api.system.render.Render3DUtil;
/*     */ import vorga.phazeclient.api.system.shape.implement.Blur;
/*     */ import vorga.phazeclient.implement.features.modules.hud.NametagHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.BlockOverlay;
/*     */ import vorga.phazeclient.implement.features.modules.other.FTHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.FTHelperRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitRange;
/*     */ import vorga.phazeclient.implement.features.modules.other.HolyWorldHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.HolyWorldHelperRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.other.MotionBlur;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoRender;
/*     */ import vorga.phazeclient.implement.features.modules.other.Predictions;
/*     */ import vorga.phazeclient.implement.features.modules.other.PredictionsRenderer;
/*     */ import vorga.phazeclient.implement.features.modules.other.WeatherChanger;
/*     */ import vorga.phazeclient.implement.hitrange.HitRangeCircleRenderer;
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
/*     */ @Mixin({class_761.class})
/*     */ public abstract class WorldRendererMixin
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private class_4599 field_20951;
/*     */   @Shadow
/*     */   @Final
/*     */   private class_898 field_4109;
/*     */   @Unique
/*  93 */   private final Matrix4f phaze$prevModelView = new Matrix4f(); @Unique
/*  94 */   private final Matrix4f phaze$prevProjection = new Matrix4f(); @Unique
/*  95 */   private final Matrix4f phaze$currentProjection = new Matrix4f(); @Unique
/*  96 */   private final Vector3f phaze$prevCameraPos = new Vector3f(); @Unique
/*  97 */   private final Vector3f phaze$currentCameraPos = new Vector3f(); @Unique
/*     */   private boolean phaze$motionHistoryValid;
/*     */   @Unique
/* 100 */   private static final ArrayList<class_1921> PHAZE_PENDING_LAYER_SCRATCH = new ArrayList<>(64);
/*     */   
/*     */   @Unique
/*     */   private Object phaze$lastScreen;
/*     */ 
/*     */   
/*     */   @Inject(method = {"renderWeather"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$cancelWeatherIfClear(class_9909 builder, class_243 cameraPos, float tickDelta, class_9958 fog, CallbackInfo ci) {
/* 108 */     NoRender noRender = NoRender.getInstance();
/* 109 */     if (noRender != null && noRender.isEnabled() && noRender.rain.isValue()) {
/* 110 */       ci.cancel();
/*     */       return;
/*     */     } 
/* 113 */     WeatherChanger weatherChanger = WeatherChanger.getInstance();
/* 114 */     if (weatherChanger.isWeatherOverrideActive() && weatherChanger.weatherType.getSelected().equals("Clear")) {
/* 115 */       ci.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void phaze$motionBlurSetMatrices(class_9922 allocator, class_9779 tickCounter, boolean renderBlockOutline, class_4184 camera, class_757 gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
/* 125 */     class_310 client = class_310.method_1551();
/* 126 */     Object currentScreen = (client == null) ? null : client.field_1755;
/* 127 */     if (currentScreen != this.phaze$lastScreen) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       this.phaze$lastScreen = currentScreen;
/* 135 */       this.phaze$motionHistoryValid = false;
/*     */     } 
/* 137 */     if (!MotionBlur.getInstance().isEnabled()) {
/* 138 */       this.phaze$motionHistoryValid = false;
/*     */       return;
/*     */     } 
/* 141 */     float tickDelta = tickCounter.method_60637(true);
/* 142 */     float fov = ((GameRendererAccessor)gameRenderer).invokeGetFov(camera, tickDelta, true);
/* 143 */     this.phaze$currentProjection.set((Matrix4fc)gameRenderer.method_22973(fov));
/* 144 */     class_243 cameraPosition = camera.method_19326();
/* 145 */     this.phaze$currentCameraPos.set((float)(cameraPosition.field_1352 % 30000.0D), (float)(cameraPosition.field_1351 % 30000.0D), (float)(cameraPosition.field_1350 % 30000.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (!this.phaze$motionHistoryValid) {
/* 151 */       this.phaze$prevModelView.set((Matrix4fc)positionMatrix);
/* 152 */       this.phaze$prevProjection.set((Matrix4fc)this.phaze$currentProjection);
/* 153 */       this.phaze$prevCameraPos.set((Vector3fc)this.phaze$currentCameraPos);
/* 154 */       this.phaze$motionHistoryValid = true;
/*     */     } 
/* 156 */     (MotionBlur.getInstance()).shader.setFrameMotionBlur(positionMatrix, this.phaze$prevModelView, this.phaze$currentProjection, this.phaze$prevProjection, this.phaze$currentCameraPos, this.phaze$prevCameraPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("RETURN")})
/*     */   private void phaze$motionBlurSetOldMatrices(class_9922 allocator, class_9779 tickCounter, boolean renderBlockOutline, class_4184 camera, class_757 gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
/* 166 */     if (!MotionBlur.getInstance().isEnabled())
/* 167 */       return;  this.phaze$prevModelView.set((Matrix4fc)positionMatrix);
/* 168 */     this.phaze$prevProjection.set((Matrix4fc)this.phaze$currentProjection);
/* 169 */     this.phaze$prevCameraPos.set((Vector3fc)this.phaze$currentCameraPos);
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
/*     */   @Inject(method = {"renderEntities"}, at = {@At("HEAD")})
/*     */   private void phaze$prepareNametagBlurFrame(class_4587 matrices, class_4597.class_4598 immediate, class_4184 camera, class_9779 tickCounter, List<class_1297> entities, CallbackInfo ci) {
/* 185 */     NametagHud module = NametagHud.getInstance();
/* 186 */     class_310 client = class_310.method_1551();
/*     */ 
/*     */     
/* 189 */     boolean hidden = (client != null && client.field_1690 != null && module.hideInF1.isValue() && client.field_1690.field_1842);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     boolean capture = (!entities.isEmpty() && module.isEnabled() && module.background.isValue() && module.backgroundBlurRadius.getValue() > 0.0F && !hidden);
/*     */     
/* 196 */     Blur.INSTANCE.beginWorldSpaceFrame(capture);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"renderEntities"}, at = {@At("TAIL")})
/*     */   private void phaze$drawHitRangeSelf(class_4587 matrices, class_4597.class_4598 immediate, class_4184 camera, class_9779 tickCounter, List<class_1297> entities, CallbackInfo ci) {
/* 208 */     HitRange config = HitRange.getInstance();
/* 209 */     if (!config.isEnabled() || !config.showSelf.isValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     class_746 player = (class_310.method_1551()).field_1724;
/* 214 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     float tickDelta = tickCounter.method_60637(false);
/* 219 */     double px = class_3532.method_16436(tickDelta, player.field_6038, player.method_23317());
/* 220 */     double py = class_3532.method_16436(tickDelta, player.field_5971, player.method_23318());
/* 221 */     double pz = class_3532.method_16436(tickDelta, player.field_5989, player.method_23321());
/*     */     
/* 223 */     class_243 playerPos = (new class_243(px, py, pz)).method_1020(camera.method_19326());
/*     */     
/* 225 */     class_897<? super class_746, ?> renderer = this.field_4109.method_3953((class_1297)player);
/* 226 */     class_10055 state = (class_10055)renderer.method_62425((class_1297)player, tickDelta);
/*     */     
/* 228 */     matrices.method_22903();
/* 229 */     matrices.method_22904(playerPos.field_1352, playerPos.field_1351, playerPos.field_1350);
/* 230 */     class_4597.class_4598 class_45981 = this.field_20951.method_23000();
/* 231 */     HitRangeCircleRenderer.drawCircle(matrices, (class_4597)class_45981, state);
/* 232 */     matrices.method_22909();
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
/*     */   @Inject(method = {"renderEntities"}, at = {@At("TAIL")})
/*     */   private void phaze$drawFTLayers(class_4587 matrices, class_4597.class_4598 immediate, class_4184 camera, class_9779 tickCounter, List<class_1297> entities, CallbackInfo ci) {
/* 248 */     FTHelper ftHelper = FTHelper.getInstance();
/* 249 */     HolyWorldHelper holyWorldHelper = HolyWorldHelper.getInstance();
/* 250 */     Predictions predictions = Predictions.getInstance();
/* 251 */     if (!ftHelper.isEnabled() && !holyWorldHelper.isEnabled() && !predictions.isEnabled()) {
/*     */       return;
/*     */     }
/* 254 */     phaze$flushEntityLayersBeforeOverlays(immediate);
/*     */     
/* 256 */     class_243 cameraPos = camera.method_19326();
/* 257 */     FTHelperRenderer.renderHighlight(matrices, cameraPos, tickCounter);
/* 258 */     FTHelperRenderer.renderSnowballs(matrices, cameraPos, tickCounter);
/* 259 */     HolyWorldHelperRenderer.render(matrices, cameraPos, tickCounter);
/* 260 */     PredictionsRenderer.render(matrices, cameraPos, tickCounter);
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static void phaze$flushEntityLayersBeforeOverlays(class_4597.class_4598 immediate) {
/* 265 */     class_310 client = class_310.method_1551();
/* 266 */     if (client.field_1690.method_42534().method_41753() != class_5365.field_25429) {
/* 267 */       immediate.method_22993();
/*     */       
/*     */       return;
/*     */     } 
/* 271 */     VertexConsumerProviderImmediateAccessor accessor = (VertexConsumerProviderImmediateAccessor)immediate;
/* 272 */     PHAZE_PENDING_LAYER_SCRATCH.clear();
/* 273 */     PHAZE_PENDING_LAYER_SCRATCH.addAll(accessor.phaze$getPendingLayers().keySet());
/* 274 */     for (class_1921 layer : PHAZE_PENDING_LAYER_SCRATCH) {
/* 275 */       String name = layer.toString();
/* 276 */       if (phaze$containsIgnoreCase(name, "particle") || 
/* 277 */         phaze$containsIgnoreCase(name, "weather") || 
/* 278 */         phaze$containsIgnoreCase(name, "cloud")) {
/*     */         continue;
/*     */       }
/* 281 */       immediate.method_22994(layer);
/*     */     } 
/* 283 */     PHAZE_PENDING_LAYER_SCRATCH.clear();
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static boolean phaze$containsIgnoreCase(String value, String needle) {
/* 288 */     int limit = value.length() - needle.length();
/* 289 */     for (int i = 0; i <= limit; i++) {
/* 290 */       if (value.regionMatches(true, i, needle, 0, needle.length())) {
/* 291 */         return true;
/*     */       }
/*     */     } 
/* 294 */     return false;
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
/*     */   @Redirect(method = {"getEntityOutlinesFramebuffer", "getTranslucentFramebuffer", "getEntityFramebuffer", "getParticlesFramebuffer", "getWeatherFramebuffer", "getCloudsFramebuffer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Handle;get()Ljava/lang/Object;"), require = 0)
/*     */   private Object phaze$safeFabulousFramebufferHandle(class_9925<?> handle) {
/*     */     try {
/* 314 */       return handle.get();
/* 315 */     } catch (NullPointerException ignored) {
/*     */ 
/*     */ 
/*     */       
/* 319 */       return null;
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
/*     */   @ModifyArg(method = {"renderTargetBlockOutline"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)V"), index = 8)
/*     */   private int phaze$tintOutline(int original) {
/* 332 */     BlockOverlay module = BlockOverlay.getInstance();
/* 333 */     if (module == null || !module.isEnabled()) {
/* 334 */       return original;
/*     */     }
/* 336 */     return module.outlineColor.getColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"renderTargetBlockOutline"}, at = {@At("HEAD")})
/*     */   private void phaze$drawFill(class_4184 camera, class_4597.class_4598 vertexConsumers, class_4587 matrices, boolean translucent, CallbackInfo ci) {
/* 345 */     BlockOverlay module = BlockOverlay.getInstance();
/* 346 */     if (module == null || !module.isEnabled())
/* 347 */       return;  if (!"Filled".equalsIgnoreCase(module.style.getSelected()))
/*     */       return; 
/* 349 */     if (!translucent)
/*     */       return; 
/* 351 */     class_310 mc = class_310.method_1551();
/* 352 */     if (mc == null || mc.field_1687 == null)
/* 353 */       return;  class_239 class_239 = mc.field_1765; if (class_239 instanceof class_3965) { class_3965 bhr = (class_3965)class_239; if (bhr
/* 354 */         .method_17783() == class_239.class_240.field_1332) {
/*     */ 
/*     */         
/* 357 */         class_2338 pos = bhr.method_17777();
/* 358 */         class_2680 state = mc.field_1687.method_8320(pos);
/* 359 */         if (state.method_26215())
/*     */           return; 
/* 361 */         class_265 shape = state.method_26172((class_1922)mc.field_1687, pos, 
/* 362 */             class_3726.method_16195(camera.method_19331()));
/* 363 */         if (shape.method_1110())
/*     */           return; 
/* 365 */         class_243 cameraPos = camera.method_19326();
/* 366 */         int fillColor = module.fillColor.getColor();
/* 367 */         float fillAlphaScale = (fillColor >>> 24 & 0xFF) / 255.0F;
/* 368 */         int opaqueRgb = 0xFF000000 | fillColor & 0xFFFFFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 374 */         shape.method_1089((minX, minY, minZ, maxX, maxY, maxZ) -> Render3DUtil.drawBoxFill(matrices, (float)(pos.method_10263() + minX - cameraPos.field_1352), (float)(pos.method_10264() + minY - cameraPos.field_1351), (float)(pos.method_10260() + minZ - cameraPos.field_1350), (float)(pos.method_10263() + maxX - cameraPos.field_1352), (float)(pos.method_10264() + maxY - cameraPos.field_1351), (float)(pos.method_10260() + maxZ - cameraPos.field_1350), opaqueRgb, fillAlphaScale));
/*     */         return;
/*     */       }  }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\WorldRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */