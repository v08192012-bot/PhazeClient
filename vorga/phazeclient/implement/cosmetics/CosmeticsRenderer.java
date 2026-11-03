/*     */ package vorga.phazeclient.implement.cosmetics;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_7833;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.w3c.dom.Node;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*     */ 
/*     */ public final class CosmeticsRenderer {
/*  43 */   public static final class_2960 PHAZE_CAPE_TEXTURE = class_2960.method_60655("phaze", "textures/cosmetics/phaze_cape.png");
/*  44 */   private static final Logger LOG = LoggerFactory.getLogger("PhazeCosmetics");
/*  45 */   private static final Map<ModelKey, CachedModel> MODELS = new HashMap<>();
/*  46 */   private static final Map<ModelKey, Long> FAILED_MODELS = new HashMap<>();
/*  47 */   private static final Map<Path, CachedCape> CAPES = new HashMap<>();
/*  48 */   private static final ThreadLocal<String> PREVIEW_SELECTION = new ThreadLocal<>();
/*  49 */   private static final ThreadLocal<Float> PREVIEW_ALPHA = new ThreadLocal<>();
/*  50 */   private static final ThreadLocal<Float> PREVIEW_BODY_YAW = new ThreadLocal<>();
/*  51 */   static final CosmeticsPhysics.Pose STATIC_POSE = new CosmeticsPhysics.Pose(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */   
/*     */   private static final long FILE_STAMP_REFRESH_NS = 1000000000L;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderLocalPlayer(class_4587 matrices, class_4597 consumers, class_10055 state, int light, class_4587.class_4665 stableLightingEntry) {
/*     */     class_1657 player;
/*  60 */     CosmeticsState settings = CosmeticsState.getInstance();
/*  61 */     String previewSelection = PREVIEW_SELECTION.get();
/*  62 */     boolean preview = (previewSelection != null);
/*  63 */     class_310 client = class_310.method_1551();
/*  64 */     class_746 self = client.field_1724;
/*  65 */     if (self == null || client.field_1687 == null)
/*  66 */       return;  class_1297 renderedEntity = client.field_1687.method_8469(state.field_53528);
/*  67 */     if (renderedEntity instanceof class_1657) { player = (class_1657)renderedEntity; } else { return; }
/*  68 */      boolean localPlayer = (player.method_5628() == self.method_5628());
/*  69 */     if (preview) {
/*  70 */       if (!localPlayer || CosmeticsState.isCape(previewSelection) || 
/*  71 */         CosmeticsState.isHat(previewSelection))
/*  72 */         return;  renderBodySelection(matrices, consumers, state, light, stableLightingEntry, player, previewSelection, true);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  77 */     if (localPlayer) {
/*  78 */       if (client.field_1690.method_31044().method_31034())
/*  79 */         return;  if (settings.isWingEquipped()) {
/*  80 */         renderBodySelection(matrices, consumers, state, light, stableLightingEntry, player, settings
/*     */             
/*  82 */             .getSelectedWing(), false);
/*     */       }
/*     */       
/*  85 */       if (settings.isPetEquipped()) {
/*  86 */         renderBodySelection(matrices, consumers, state, light, stableLightingEntry, player, settings
/*     */             
/*  88 */             .getSelectedPet(), false);
/*     */       }
/*     */     } else {
/*     */       
/*  92 */       if (!(Theme.getInstance()).renderOtherPlayerCosmetics.isValue()) {
/*     */         return;
/*     */       }
/*  95 */       CosmeticsSyncService sync = CosmeticsSyncService.getInstance();
/*  96 */       String wing = sync.wingSelectionFor(player.method_5667());
/*  97 */       if (wing != null) {
/*  98 */         renderBodySelection(matrices, consumers, state, light, stableLightingEntry, player, wing, false);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 103 */       String pet = sync.petSelectionFor(player.method_5667());
/* 104 */       if (pet != null) {
/* 105 */         renderBodySelection(matrices, consumers, state, light, stableLightingEntry, player, pet, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderHeadCosmetic(class_4587 matrices, class_4597 consumers, class_10055 state, int light, class_4587.class_4665 stableLightingEntry) {
/*     */     class_1657 player;
/*     */     String selection;
/* 116 */     CosmeticsState settings = CosmeticsState.getInstance();
/* 117 */     String previewSelection = PREVIEW_SELECTION.get();
/* 118 */     boolean preview = (previewSelection != null);
/* 119 */     class_310 client = class_310.method_1551();
/* 120 */     class_746 self = client.field_1724;
/* 121 */     if (self == null || client.field_1687 == null)
/* 122 */       return;  class_1297 renderedEntity = client.field_1687.method_8469(state.field_53528);
/* 123 */     if (renderedEntity instanceof class_1657) { player = (class_1657)renderedEntity; } else { return; }
/* 124 */      boolean localPlayer = (player.method_5628() == self.method_5628());
/*     */     
/* 126 */     if (preview) {
/* 127 */       if (!localPlayer || !CosmeticsState.isHat(previewSelection))
/* 128 */         return;  selection = previewSelection;
/* 129 */     } else if (localPlayer) {
/* 130 */       if (!settings.isHatEquipped() || client.field_1690
/* 131 */         .method_31044().method_31034())
/* 132 */         return;  selection = settings.getSelectedHat();
/*     */     } else {
/* 134 */       if (!(Theme.getInstance()).renderOtherPlayerCosmetics.isValue()) {
/*     */         return;
/*     */       }
/*     */       
/* 138 */       selection = CosmeticsSyncService.getInstance().hatSelectionFor(player.method_5667());
/* 139 */       if (selection == null)
/*     */         return; 
/* 141 */     }  renderHeadSelection(matrices, consumers, light, stableLightingEntry, selection);
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
/*     */   private static void renderBodySelection(class_4587 matrices, class_4597 consumers, class_10055 state, int light, class_4587.class_4665 stableLightingEntry, class_1657 player, String selection, boolean preview) {
/* 156 */     if ("None".equalsIgnoreCase(selection))
/* 157 */       return;  if (CosmeticsState.isCape(selection))
/*     */       return; 
/* 159 */     BlockbenchWingModel model = model(selection);
/* 160 */     if (model == null)
/*     */       return; 
/* 162 */     matrices.method_22903();
/* 163 */     if (CosmeticsState.isPet(selection)) {
/*     */       
/* 165 */       CosmeticsPhysics.CompanionPose companion = CosmeticsPhysics.getInstance().sampleCompanion(player, state, preview, 
/*     */           
/* 167 */           CosmeticsState.getInstance()
/* 168 */           .getPetFollowIntensity());
/*     */       
/* 170 */       float scale = petScale(selection);
/* 171 */       String lowerSelection = selection.toLowerCase(Locale.ROOT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       float companionYaw = lowerSelection.contains("goldfish") ? 0.0F : 180.0F;
/* 180 */       model.transformCompanionTo(matrices, companion
/*     */           
/* 182 */           .x(), companion.y(), companion.z(), scale, companionYaw);
/*     */ 
/*     */       
/* 185 */       model.render(matrices, consumers, light, STATIC_POSE, false, false, stableLightingEntry);
/*     */ 
/*     */ 
/*     */       
/* 189 */       matrices.method_22909();
/*     */       
/*     */       return;
/*     */     } 
/* 193 */     CosmeticsPhysics.Pose physics = CosmeticsPhysics.getInstance().sample(player, state, preview, 
/*     */         
/* 195 */         CosmeticsState.getInstance().getWingMotion());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     boolean isWimgs = "Wimgs".equalsIgnoreCase(selection);
/* 202 */     boolean isFluffyWings = "Fluffy Wings".equalsIgnoreCase(selection);
/* 203 */     boolean isCodexWings = selection.toLowerCase(Locale.ROOT).startsWith("ayldwt");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     float attachmentY = isFluffyWings ? 0.19F : 0.1F;
/* 214 */     float attachmentZ = (isWimgs || isFluffyWings || isCodexWings) ? 0.128F : -0.132F;
/* 215 */     model.translateAttachmentTo(matrices, 0.0F, attachmentY, attachmentZ);
/* 216 */     if (isWimgs) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       model.rotateAroundRootY(matrices, 180.0F);
/*     */ 
/*     */ 
/*     */       
/* 226 */       model.rotateAroundRootZ(matrices, 180.0F);
/*     */ 
/*     */       
/* 229 */       model.scaleAroundRoot(matrices, 0.9F);
/*     */     } 
/*     */     
/* 232 */     model.rotateAroundRootX(matrices, physics.rootPitch());
/* 233 */     model.rotateAroundRootY(matrices, physics.rootYaw());
/*     */ 
/*     */     
/* 236 */     model.render(matrices, consumers, light, physics, true, false, stableLightingEntry);
/*     */ 
/*     */ 
/*     */     
/* 240 */     matrices.method_22909();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderHeadSelection(class_4587 matrices, class_4597 consumers, int light, class_4587.class_4665 stableLightingEntry, String selection) {
/*     */     float scale;
/* 250 */     if (selection == null || "None".equalsIgnoreCase(selection) || 
/* 251 */       !CosmeticsState.isHat(selection))
/* 252 */       return;  BlockbenchWingModel model = model(selection);
/* 253 */     if (model == null)
/*     */       return; 
/* 255 */     boolean turtle = selection.toLowerCase(Locale.ROOT).contains("turtle");
/* 256 */     String lowerSelection = selection.toLowerCase(Locale.ROOT);
/*     */     
/* 258 */     if (turtle) {
/* 259 */       scale = 0.65F;
/* 260 */     } else if (lowerSelection.contains("maple witch")) {
/* 261 */       scale = 0.82F;
/* 262 */     } else if (lowerSelection.contains("violet witch")) {
/* 263 */       scale = 1.05F;
/*     */     } else {
/* 265 */       scale = 1.0F;
/*     */     } 
/* 267 */     matrices.method_22903();
/* 268 */     if (lowerSelection.contains("ice hat")) {
/*     */ 
/*     */       
/* 271 */       matrices.method_46416(0.0F, 0.1F, -0.015625F);
/* 272 */     } else if (lowerSelection.contains("sprint hat")) {
/* 273 */       matrices.method_46416(0.0F, 0.11F, 0.0F);
/* 274 */     } else if (lowerSelection.contains("brewing hat")) {
/* 275 */       matrices.method_46416(0.0F, 0.06F, 0.0F);
/* 276 */     } else if (lowerSelection.contains("maple witch")) {
/* 277 */       matrices.method_46416(0.0F, 0.18F, 0.0F);
/* 278 */     } else if (lowerSelection.contains("violet witch")) {
/* 279 */       matrices.method_46416(0.0F, 0.15F, 0.0F);
/*     */     } 
/* 281 */     model.transformOntoHead(matrices, scale, 0.0F);
/* 282 */     model.render(matrices, consumers, light, STATIC_POSE, false, false, stableLightingEntry);
/*     */ 
/*     */ 
/*     */     
/* 286 */     matrices.method_22909();
/*     */   }
/*     */   
/*     */   private static float petScale(String selection) {
/* 290 */     String lower = selection.toLowerCase(Locale.ROOT);
/* 291 */     if (lower.contains("goldfish")) return 1.15F; 
/* 292 */     if (lower.contains("ally")) return 0.65F; 
/* 293 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public static void renderPreview(String selection, float alpha, Runnable renderer) {
/* 297 */     renderPreview(selection, alpha, 0.0F, renderer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderPreview(String selection, float alpha, float bodyYaw, Runnable renderer) {
/* 306 */     String previous = PREVIEW_SELECTION.get();
/* 307 */     Float previousAlpha = PREVIEW_ALPHA.get();
/* 308 */     Float previousBodyYaw = PREVIEW_BODY_YAW.get();
/* 309 */     PREVIEW_SELECTION.set(selection);
/* 310 */     PREVIEW_ALPHA.set(Float.valueOf(Math.max(0.0F, Math.min(1.0F, alpha))));
/* 311 */     PREVIEW_BODY_YAW.set(Float.valueOf(bodyYaw));
/*     */     try {
/* 313 */       renderer.run();
/*     */     } finally {
/* 315 */       if (previous == null) {
/* 316 */         PREVIEW_SELECTION.remove();
/*     */       } else {
/* 318 */         PREVIEW_SELECTION.set(previous);
/*     */       } 
/* 320 */       if (previousAlpha == null) {
/* 321 */         PREVIEW_ALPHA.remove();
/*     */       } else {
/* 323 */         PREVIEW_ALPHA.set(previousAlpha);
/*     */       } 
/* 325 */       if (previousBodyYaw == null) {
/* 326 */         PREVIEW_BODY_YAW.remove();
/*     */       } else {
/* 328 */         PREVIEW_BODY_YAW.set(previousBodyYaw);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float previewAlpha() {
/* 334 */     Float alpha = PREVIEW_ALPHA.get();
/* 335 */     return (alpha == null) ? 1.0F : alpha.floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Float previewBodyYaw() {
/* 343 */     return PREVIEW_BODY_YAW.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean renderCatalogModel(class_332 context, String selection, float x, float y, float width, float height, float alpha) {
/* 353 */     if (CosmeticsState.isCape(selection)) {
/*     */ 
/*     */       
/* 356 */       class_2960 texture = capePreviewTexture(selection);
/* 357 */       if (texture == null) return false; 
/* 358 */       int drawHeight = Math.max(1, Math.round(height * 0.88F));
/* 359 */       int drawWidth = Math.max(1, Math.round(drawHeight * 0.625F));
/* 360 */       int drawX = Math.round(x + (width - drawWidth) * 0.5F);
/* 361 */       int drawY = Math.round(y + (height - drawHeight) * 0.5F);
/* 362 */       int color = (int)(Math.max(0.0F, Math.min(1.0F, alpha)) * 255.0F) << 24 | 0xFFFFFF;
/*     */ 
/*     */ 
/*     */       
/* 366 */       context.method_25293(class_1921::method_62277, texture, drawX, drawY, 4.0F, 4.0F, drawWidth, drawHeight, 40, 64, 256, 128, color);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 376 */       return true;
/*     */     } 
/* 378 */     beginCatalogModelBatch(alpha);
/* 379 */     boolean rendered = renderCatalogWingBatched(context, selection, x, y, width, height);
/* 380 */     endCatalogModelBatch();
/* 381 */     return rendered;
/*     */   }
/*     */   
/*     */   public static void beginCatalogModelBatch(float alpha) {
/* 385 */     class_310 client = class_310.method_1551();
/* 386 */     if (client == null)
/* 387 */       return;  client.method_22940().method_23000().method_22993();
/* 388 */     RenderSystem.enableBlend();
/* 389 */     RenderSystem.defaultBlendFunc();
/* 390 */     RenderSystem.enableDepthTest();
/* 391 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 
/*     */         
/* 393 */         Math.max(0.0F, Math.min(1.0F, alpha)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean renderCatalogWingBatched(class_332 context, String selection, float x, float y, float width, float height) {
/* 403 */     if (CosmeticsState.isCape(selection)) return false; 
/* 404 */     BlockbenchWingModel model = model(selection);
/* 405 */     class_310 client = class_310.method_1551();
/* 406 */     if (model == null || client == null || width <= 1.0F || height <= 1.0F) {
/* 407 */       return false;
/*     */     }
/*     */     
/* 410 */     BlockbenchWingModel.Bounds bounds = model.bounds();
/* 411 */     boolean frontIsXAxis = bounds.frontIsXAxis();
/* 412 */     float projectedWidth = frontIsXAxis ? bounds.depth() : bounds.width();
/* 413 */     float scale = Math.min(width * 0.84F / projectedWidth, height * 0.84F / bounds
/*     */         
/* 415 */         .height());
/*     */ 
/*     */     
/* 418 */     class_4587 matrices = context.method_51448();
/*     */     
/* 420 */     class_4597.class_4598 consumers = client.method_22940().method_23000();
/*     */     
/* 422 */     matrices.method_22903();
/* 423 */     matrices.method_46416(x + width * 0.5F, y + height * 0.5F, 260.0F);
/* 424 */     matrices.method_22905(scale, -scale, scale);
/* 425 */     if (frontIsXAxis) {
/*     */       
/* 427 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(90.0F));
/*     */     } else {
/*     */       
/* 430 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(180.0F));
/*     */     } 
/* 432 */     String lowerSelection = selection.toLowerCase(Locale.ROOT);
/*     */ 
/*     */     
/* 435 */     if (lowerSelection.contains("ally") || lowerSelection
/* 436 */       .contains("birb")) {
/*     */       
/* 438 */       matrices.method_22907(class_7833.field_40716
/* 439 */           .rotationDegrees(45.0F));
/*     */     }
/* 441 */     else if (lowerSelection.contains("turtle")) {
/* 442 */       matrices.method_22907(class_7833.field_40716
/* 443 */           .rotationDegrees(180.0F));
/*     */     } 
/*     */     
/* 446 */     if (CosmeticsState.isWing(selection) && 
/* 447 */       !"Wimgs".equalsIgnoreCase(selection) && 
/* 448 */       !"Fluffy Wings".equalsIgnoreCase(selection)) {
/* 449 */       matrices.method_22907(class_7833.field_40718.rotationDegrees(180.0F));
/*     */     }
/* 451 */     matrices.method_46416(-bounds.centerX(), -bounds.centerY(), -bounds.centerZ());
/* 452 */     if (!model.renderCatalogBuffers(matrices)) {
/* 453 */       model.renderCatalog(matrices, (class_4597)consumers, 15728880, matrices.method_23760());
/*     */     }
/* 455 */     matrices.method_22909();
/* 456 */     return true;
/*     */   }
/*     */   
/*     */   public static void endCatalogModelBatch() {
/* 460 */     class_310 client = class_310.method_1551();
/* 461 */     if (client != null) {
/* 462 */       client.method_22940().method_23000().method_22993();
/*     */     }
/* 464 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 465 */     RenderSystem.disableDepthTest();
/* 466 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   public static boolean isRenderingPreview() {
/* 470 */     return (PREVIEW_SELECTION.get() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String capeSelectionFor(class_10055 state) {
/*     */     class_1657 player;
/* 479 */     class_310 client = class_310.method_1551();
/* 480 */     if (client == null || client.field_1724 == null || client.field_1687 == null || state == null) {
/* 481 */       return null;
/*     */     }
/* 483 */     class_1297 renderedEntity = client.field_1687.method_8469(state.field_53528);
/* 484 */     if (renderedEntity instanceof class_1657) { player = (class_1657)renderedEntity; } else { return null; }
/* 485 */      return capeSelectionFor(player);
/*     */   }
/*     */   
/*     */   public static String capeSelectionFor(class_1657 player) {
/* 489 */     class_310 client = class_310.method_1551();
/* 490 */     if (client == null || client.field_1724 == null || player == null) return null; 
/* 491 */     boolean localPlayer = (player.method_5628() == client.field_1724.method_5628());
/* 492 */     String preview = PREVIEW_SELECTION.get();
/* 493 */     if (preview != null) {
/* 494 */       return (localPlayer && CosmeticsState.isCape(preview)) ? preview : null;
/*     */     }
/* 496 */     if (localPlayer) {
/* 497 */       CosmeticsState settings = CosmeticsState.getInstance();
/* 498 */       return settings.isCapeEquipped() ? settings.getSelectedCape() : null;
/*     */     } 
/* 500 */     if (!(Theme.getInstance()).renderOtherPlayerCosmetics.isValue()) {
/* 501 */       return null;
/*     */     }
/* 503 */     return CosmeticsSyncService.getInstance().capeSelectionFor(player.method_5667());
/*     */   }
/*     */   
/*     */   public static synchronized class_2960 capeTexture(String selection) {
/* 507 */     return resolveCapeTexture(selection, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized class_2960 capePreviewTexture(String selection) {
/* 512 */     return resolveCapeTexture(selection, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_2960 resolveCapeTexture(String selection, boolean animated) {
/*     */     try {
/* 523 */       CosmeticsState.CosmeticEntry entry = CosmeticsState.getInstance().resolveEntry(selection);
/* 524 */       if (entry == null || !CosmeticsState.isCape(selection)) return null; 
/* 525 */       Path file = entry.file().toAbsolutePath().normalize();
/* 526 */       CachedCape cached = CAPES.get(file);
/* 527 */       long now = System.nanoTime();
/* 528 */       if (cached != null && now - cached.checkedAtNanos() < 1000000000L) {
/* 529 */         return cached.textureAt(now, animated);
/*     */       }
/* 531 */       long stamp = Files.getLastModifiedTime(file, new java.nio.file.LinkOption[0]).toMillis();
/* 532 */       if (cached != null && cached.stamp() == stamp) {
/* 533 */         cached.checkedAtNanos = now;
/* 534 */         return cached.textureAt(now, animated);
/*     */       } 
/*     */       
/* 537 */       class_310 client = class_310.method_1551();
/* 538 */       if (client == null) return null; 
/* 539 */       if (cached != null) {
/* 540 */         cached.destroy(client);
/*     */       }
/* 542 */       String token = Integer.toUnsignedString(file.toString().hashCode(), 16);
/* 543 */       List<CapeFrame> frames = loadCapeFrames(client, file, token);
/* 544 */       if (frames.isEmpty()) return null; 
/* 545 */       cached = new CachedCape(frames, stamp, now);
/* 546 */       CAPES.put(file, cached);
/* 547 */       return cached.textureAt(now, animated);
/* 548 */     } catch (Throwable error) {
/* 549 */       LOG.error("Failed to load cape texture '{}'", selection, error);
/* 550 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<CapeFrame> loadCapeFrames(class_310 client, Path file, String textureToken) throws Exception {
/* 559 */     List<DecodedCapeFrame> decoded = isGif(file) ? readGifFrames(file) : readStaticCapeFrame(file);
/* 560 */     List<CapeFrame> frames = new ArrayList<>(decoded.size());
/*     */     try {
/* 562 */       for (int index = 0; index < decoded.size(); index++) {
/* 563 */         DecodedCapeFrame frame = decoded.get(index);
/* 564 */         class_2960 id = class_2960.method_60655("phaze", "dynamic/capes/" + textureToken + "/" + index);
/* 565 */         client.method_1531().method_4615(id);
/* 566 */         client.method_1531().method_4616(id, (class_1044)new class_1043(frame.image()));
/* 567 */         frames.add(new CapeFrame(id, frame.durationNanos()));
/*     */       } 
/* 569 */       return frames;
/* 570 */     } catch (Throwable error) {
/* 571 */       for (CapeFrame frame : frames) {
/* 572 */         client.method_1531().method_4615(frame.id());
/*     */       }
/* 574 */       for (int index = frames.size(); index < decoded.size(); index++) {
/* 575 */         ((DecodedCapeFrame)decoded.get(index)).image().close();
/*     */       }
/* 577 */       throw error;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<DecodedCapeFrame> readStaticCapeFrame(Path file) throws Exception {
/* 582 */     InputStream input = Files.newInputStream(file, new java.nio.file.OpenOption[0]); 
/* 583 */     try { List<DecodedCapeFrame> list = List.of(new DecodedCapeFrame(class_1011.method_4309(input), 0L));
/* 584 */       if (input != null) input.close();  return list; } catch (Throwable throwable) { if (input != null)
/*     */         try { input.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 588 */      } private static List<DecodedCapeFrame> readGifFrames(Path file) throws Exception { ImageInputStream stream = ImageIO.createImageInputStream(file.toFile()); 
/* 589 */     try { Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
/* 590 */       if (!readers.hasNext()) throw new IllegalStateException("GIF reader is unavailable"); 
/* 591 */       ImageReader reader = readers.next();
/*     */       try {
/* 593 */         reader.setInput(stream, false, false);
/* 594 */         int frameCount = reader.getNumImages(true);
/* 595 */         int canvasWidth = gifDimension(reader.getStreamMetadata(), "logicalScreenWidth", reader.getWidth(0));
/* 596 */         int canvasHeight = gifDimension(reader.getStreamMetadata(), "logicalScreenHeight", reader.getHeight(0));
/* 597 */         BufferedImage canvas = new BufferedImage(canvasWidth, canvasHeight, 2);
/* 598 */         BufferedImage restorePoint = null;
/* 599 */         GifFrameInfo previous = null;
/* 600 */         List<DecodedCapeFrame> frames = new ArrayList<>(frameCount);
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
/*     */       }
/*     */       finally {
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
/* 631 */         reader.dispose();
/*     */       }  }
/*     */     catch (Throwable throwable) { if (stream != null)
/*     */         try { stream.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 637 */      } private static boolean isGif(Path file) { return file.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".gif"); }
/*     */ 
/*     */   
/*     */   private static int gifDimension(IIOMetadata metadata, String attribute, int fallback) {
/* 641 */     String value = gifMetadataAttribute(metadata, "LogicalScreenDescriptor", attribute);
/*     */     try {
/* 643 */       return (value == null) ? fallback : Math.max(1, Integer.parseInt(value));
/* 644 */     } catch (NumberFormatException ignored) {
/* 645 */       return fallback;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static GifFrameInfo gifFrameInfo(IIOMetadata metadata, BufferedImage raw) {
/* 650 */     int x = gifIntAttribute(metadata, "ImageDescriptor", "imageLeftPosition", 0);
/* 651 */     int y = gifIntAttribute(metadata, "ImageDescriptor", "imageTopPosition", 0);
/* 652 */     int width = gifIntAttribute(metadata, "ImageDescriptor", "imageWidth", raw.getWidth());
/* 653 */     int height = gifIntAttribute(metadata, "ImageDescriptor", "imageHeight", raw.getHeight());
/* 654 */     int delayCentiseconds = gifIntAttribute(metadata, "GraphicControlExtension", "delayTime", 10);
/* 655 */     String disposal = gifMetadataAttribute(metadata, "GraphicControlExtension", "disposalMethod");
/* 656 */     return new GifFrameInfo(x, y, width, height, (disposal == null) ? "none" : disposal, 
/* 657 */         Math.max(50000000L, delayCentiseconds * 10000000L));
/*     */   }
/*     */   
/*     */   private static int gifIntAttribute(IIOMetadata metadata, String nodeName, String attribute, int fallback) {
/* 661 */     String value = gifMetadataAttribute(metadata, nodeName, attribute);
/*     */     try {
/* 663 */       return (value == null) ? fallback : Integer.parseInt(value);
/* 664 */     } catch (NumberFormatException ignored) {
/* 665 */       return fallback;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String gifMetadataAttribute(IIOMetadata metadata, String nodeName, String attribute) {
/* 670 */     if (metadata == null) return null; 
/*     */     try {
/* 672 */       Node node = findMetadataNode(metadata.getAsTree(metadata.getNativeMetadataFormatName()), nodeName);
/* 673 */       return (node == null || node.getAttributes() == null || node.getAttributes().getNamedItem(attribute) == null) ? 
/* 674 */         null : node.getAttributes().getNamedItem(attribute).getNodeValue();
/* 675 */     } catch (Throwable ignored) {
/* 676 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Node findMetadataNode(Node root, String name) {
/* 681 */     if (root == null) return null; 
/* 682 */     if (name.equals(root.getNodeName())) return root; 
/* 683 */     for (Node child = root.getFirstChild(); child != null; child = child.getNextSibling()) {
/* 684 */       Node found = findMetadataNode(child, name);
/* 685 */       if (found != null) return found; 
/*     */     } 
/* 687 */     return null;
/*     */   }
/*     */   
/*     */   private static void applyGifDisposal(BufferedImage canvas, BufferedImage restorePoint, GifFrameInfo previous) {
/* 691 */     if ("restoreToBackgroundColor".equals(previous.disposalMethod())) {
/* 692 */       Graphics2D graphics = canvas.createGraphics();
/*     */       try {
/* 694 */         graphics.setComposite(AlphaComposite.Clear);
/* 695 */         graphics.fillRect(previous.x(), previous.y(), previous.width(), previous.height());
/*     */       } finally {
/* 697 */         graphics.dispose();
/*     */       } 
/* 699 */     } else if ("restoreToPrevious".equals(previous.disposalMethod()) && restorePoint != null) {
/* 700 */       Graphics2D graphics = canvas.createGraphics();
/*     */       try {
/* 702 */         graphics.drawImage(restorePoint, 0, 0, (ImageObserver)null);
/*     */       } finally {
/* 704 */         graphics.dispose();
/* 705 */         restorePoint.flush();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BufferedImage copyImage(BufferedImage source) {
/* 711 */     BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), 2);
/* 712 */     Graphics2D graphics = copy.createGraphics();
/* 713 */     graphics.drawImage(source, 0, 0, (ImageObserver)null);
/* 714 */     graphics.dispose();
/* 715 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_1011 toNativeCapeImage(BufferedImage source) throws Exception {
/* 721 */     BufferedImage cape = new BufferedImage(256, 128, 2);
/* 722 */     Graphics2D graphics = cape.createGraphics();
/*     */     try {
/* 724 */       graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
/* 725 */       graphics.drawImage(source, 0, 0, 256, 128, null);
/*     */     } finally {
/* 727 */       graphics.dispose();
/*     */     }  try {
/* 729 */       ByteArrayOutputStream output = new ByteArrayOutputStream();
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 735 */       cape.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized BlockbenchWingModel model(String selection) {
/*     */     try {
/* 742 */       CosmeticsState.CosmeticEntry entry = CosmeticsState.getInstance().resolveEntry(selection);
/* 743 */       if (entry == null || !Files.isRegularFile(entry.file(), new java.nio.file.LinkOption[0])) return null; 
/* 744 */       Path file = entry.file().toAbsolutePath().normalize();
/* 745 */       String variant = (entry.textureVariant() == null) ? "" : entry.textureVariant();
/* 746 */       CosmeticsState.CosmeticType type = CosmeticsState.typeOf(selection);
/* 747 */       ModelKey key = new ModelKey(file, variant, type);
/* 748 */       CachedModel cached = MODELS.get(key);
/* 749 */       long now = System.nanoTime();
/* 750 */       if (cached != null && now - cached.checkedAtNanos < 1000000000L) {
/* 751 */         return cached.model;
/*     */       }
/* 753 */       long stamp = Files.getLastModifiedTime(file, new java.nio.file.LinkOption[0]).toMillis();
/* 754 */       if (cached == null || stamp != cached.stamp) {
/*     */         try {
/* 756 */           BlockbenchWingModel loaded = BlockbenchWingModel.load(file, variant, type);
/* 757 */           String textureKey = Integer.toUnsignedString(key.hashCode(), 16);
/* 758 */           loaded.registerTextures(textureKey);
/* 759 */           if (cached != null) {
/* 760 */             cached.model.close();
/*     */           }
/* 762 */           cached = new CachedModel(loaded, stamp, now);
/* 763 */           MODELS.put(key, cached);
/* 764 */           FAILED_MODELS.remove(key);
/* 765 */         } catch (Throwable error) {
/* 766 */           Long failedStamp = FAILED_MODELS.put(key, Long.valueOf(stamp));
/* 767 */           if (failedStamp == null || failedStamp.longValue() != stamp) {
/* 768 */             LOG.error("Failed to load cosmetic '{}' from {} (variant '{}')", new Object[] { selection, file, variant, error });
/*     */           }
/*     */           
/* 771 */           return null;
/*     */         } 
/*     */       }
/* 774 */       cached.checkedAtNanos = now;
/* 775 */       return cached.model;
/* 776 */     } catch (Throwable error) {
/*     */       
/* 778 */       LOG.error("Failed to resolve cosmetic '{}'", selection, error);
/* 779 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class CachedModel {
/*     */     private final BlockbenchWingModel model;
/*     */     private final long stamp;
/*     */     private long checkedAtNanos;
/*     */     
/*     */     private CachedModel(BlockbenchWingModel model, long stamp, long checkedAtNanos) {
/* 789 */       this.model = model;
/* 790 */       this.stamp = stamp;
/* 791 */       this.checkedAtNanos = checkedAtNanos;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class CachedCape {
/*     */     private final List<CosmeticsRenderer.CapeFrame> frames;
/*     */     private final long stamp;
/*     */     private long checkedAtNanos;
/*     */     
/*     */     private CachedCape(List<CosmeticsRenderer.CapeFrame> frames, long stamp, long checkedAtNanos) {
/* 801 */       this.frames = List.copyOf(frames);
/* 802 */       this.stamp = stamp;
/* 803 */       this.checkedAtNanos = checkedAtNanos;
/*     */     }
/*     */     
/*     */     private class_2960 textureAt(long nowNanos, boolean animated) {
/* 807 */       if (this.frames.isEmpty()) return null; 
/* 808 */       if (!animated || this.frames.size() == 1) return ((CosmeticsRenderer.CapeFrame)this.frames.get(0)).id(); 
/* 809 */       long duration = 0L;
/* 810 */       for (CosmeticsRenderer.CapeFrame frame : this.frames) duration += frame.durationNanos(); 
/* 811 */       if (duration <= 0L) return ((CosmeticsRenderer.CapeFrame)this.frames.get(0)).id(); 
/* 812 */       long elapsed = Math.floorMod(nowNanos, duration);
/* 813 */       for (CosmeticsRenderer.CapeFrame frame : this.frames) {
/* 814 */         if (elapsed < frame.durationNanos()) return frame.id(); 
/* 815 */         elapsed -= frame.durationNanos();
/*     */       } 
/* 817 */       return ((CosmeticsRenderer.CapeFrame)this.frames.get(this.frames.size() - 1)).id();
/*     */     }
/*     */     
/*     */     private void destroy(class_310 client) {
/* 821 */       for (CosmeticsRenderer.CapeFrame frame : this.frames) {
/* 822 */         client.method_1531().method_4615(frame.id());
/*     */       }
/*     */     }
/*     */     
/*     */     private long stamp() {
/* 827 */       return this.stamp;
/*     */     }
/*     */     
/*     */     private long checkedAtNanos() {
/* 831 */       return this.checkedAtNanos;
/*     */     } }
/*     */   private static final class CapeFrame extends Record { private final class_2960 id; private final long durationNanos;
/*     */     
/* 835 */     private CapeFrame(class_2960 id, long durationNanos) { this.id = id; this.durationNanos = durationNanos; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$CapeFrame;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #835	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 835 */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$CapeFrame; } public class_2960 id() { return this.id; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$CapeFrame;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #835	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$CapeFrame; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$CapeFrame;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #835	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$CapeFrame;
/* 835 */       //   0	8	1	o	Ljava/lang/Object; } public long durationNanos() { return this.durationNanos; }
/*     */      } private static final class DecodedCapeFrame extends Record { private final class_1011 image; private final long durationNanos;
/* 837 */     private DecodedCapeFrame(class_1011 image, long durationNanos) { this.image = image; this.durationNanos = durationNanos; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$DecodedCapeFrame;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #837	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$DecodedCapeFrame; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$DecodedCapeFrame;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #837	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$DecodedCapeFrame; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$DecodedCapeFrame;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #837	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$DecodedCapeFrame;
/* 837 */       //   0	8	1	o	Ljava/lang/Object; } public class_1011 image() { return this.image; } public long durationNanos() { return this.durationNanos; }
/*     */      } private static final class GifFrameInfo extends Record { private final int x; private final int y; private final int width; private final int height; private final String disposalMethod; private final long durationNanos;
/* 839 */     private GifFrameInfo(int x, int y, int width, int height, String disposalMethod, long durationNanos) { this.x = x; this.y = y; this.width = width; this.height = height; this.disposalMethod = disposalMethod; this.durationNanos = durationNanos; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$GifFrameInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #839	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$GifFrameInfo; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$GifFrameInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #839	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$GifFrameInfo; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$GifFrameInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #839	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$GifFrameInfo;
/* 839 */       //   0	8	1	o	Ljava/lang/Object; } public int x() { return this.x; } public int y() { return this.y; } public int width() { return this.width; } public int height() { return this.height; } public String disposalMethod() { return this.disposalMethod; } public long durationNanos() { return this.durationNanos; }
/*     */      }
/*     */   private static final class ModelKey extends Record { private final Path file; private final String textureVariant; private final CosmeticsState.CosmeticType type;
/* 842 */     private ModelKey(Path file, String textureVariant, CosmeticsState.CosmeticType type) { this.file = file; this.textureVariant = textureVariant; this.type = type; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$ModelKey;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #842	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$ModelKey; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$ModelKey;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #842	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$ModelKey; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$ModelKey;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #842	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/CosmeticsRenderer$ModelKey;
/* 842 */       //   0	8	1	o	Ljava/lang/Object; } public Path file() { return this.file; } public String textureVariant() { return this.textureVariant; } public CosmeticsState.CosmeticType type() { return this.type; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\CosmeticsRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */