/*     */ package vorga.phazeclient.api.system.hud;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_284;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_5944;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector4f;
/*     */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ScoreboardHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoRender;
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
/*     */ public final class ExordiumAnimationBridge
/*     */ {
/*     */   public static final String HOTBAR = "minecraft:hotbar";
/*     */   public static final String PLAYER_LIST = "minecraft:player_list";
/*     */   public static final String CHAT = "minecraft:chat_panel";
/*     */   public static final String SCOREBOARD = "minecraft:scoreboard";
/*     */   public static final String BOSS_BAR = "minecraft:boss_bar";
/*  54 */   private static final Map<Object, String> COMPONENT_BY_BUFFER = new IdentityHashMap<>();
/*  55 */   private static final Map<Object, String> ID_BY_INSTANCE = new IdentityHashMap<>();
/*     */   
/*  57 */   private static final Set<Object> LISTENER_REGISTERED = Collections.newSetFromMap(new IdentityHashMap<>());
/*     */   
/*  59 */   private static final Set<String> FORCE_CAPTURE = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*  60 */   private static final LinkedHashMap<Object, String> DEFERRED = new LinkedHashMap<>();
/*  61 */   private static final ThreadLocal<String> CAPTURING = new ThreadLocal<>();
/*     */   
/*     */   private static Field instanceIdField;
/*     */   
/*     */   private static Field instanceBufferField;
/*     */   
/*     */   private static Field instancePacingField;
/*     */   
/*     */   private static Field instanceCapturingField;
/*     */   
/*     */   private static Method registerUpdateListenerMethod;
/*     */   private static Method pacingSetCooldownMethod;
/*     */   private static Method bufferTextureMethod;
/*     */   private static Field exordiumInstanceField;
/*     */   private static Method exordiumGetShaderManagerMethod;
/*     */   private static Method exordiumGetMultiTextureShaderMethod;
/*     */   private static Method exordiumGetTextureCountUniformMethod;
/*     */   private static Method exordiumGetModelMethod;
/*     */   private static Method exordiumModelDrawMethod;
/*     */   private static Object exordiumShaderManager;
/*     */   private static boolean reflectionFailed;
/*     */   private static class_332 frameContext;
/*     */   private static boolean lastHotbarEnabled;
/*     */   private static boolean lastTabEnabled;
/*     */   private static boolean lastChatAnimationEnabled;
/*     */   private static boolean hotbarPrepared;
/*     */   private static boolean tabPrepared;
/*     */   private static boolean chatPrepared;
/*     */   private static Object playerListBuffer;
/*     */   private static HotbarSelector hotbarSelector;
/*     */   private static Rect tabBounds;
/*     */   private static float tabPivotX;
/*     */   private static float tabPivotY;
/*     */   private static boolean tabGeometryKnown;
/*     */   private static Rect chatBounds;
/*     */   private static Rect latestChatBounds;
/*     */   private static float hotbarCurrentSlotX;
/*     */   private static float hotbarTargetSlotX;
/*     */   private static boolean hotbarMirror;
/*     */   private static int hotbarMirrorOffset;
/*     */   private static float chatDx;
/*     */   private static float chatDy;
/* 103 */   private static float chatAlpha = 1.0F;
/*     */ 
/*     */   
/*     */   private static boolean chatAnimationActive;
/*     */ 
/*     */   
/*     */   public static void beginHudFrame(class_332 context) {
/* 110 */     frameContext = context;
/* 111 */     DEFERRED.clear();
/*     */     
/* 113 */     Animations animations = Animations.getInstance();
/* 114 */     boolean hotbarEnabled = (animations != null && animations.isHotbarSlideEnabled());
/* 115 */     boolean tabEnabled = (animations != null && animations.isTabSlideEnabled());
/*     */     
/* 117 */     boolean chatEnabled = (animations != null && (animations.isChatSmoothScrollEnabled() || animations.isChatFadeEnabled()));
/*     */     
/* 119 */     if (hotbarEnabled && !lastHotbarEnabled) {
/* 120 */       hotbarPrepared = false;
/* 121 */       hotbarSelector = null;
/* 122 */       requestImmediateCapture("minecraft:hotbar");
/*     */     } 
/* 124 */     if (tabEnabled && !lastTabEnabled) {
/* 125 */       tabPrepared = false;
/* 126 */       requestImmediateCapture("minecraft:player_list");
/*     */     } 
/* 128 */     if (chatEnabled && !lastChatAnimationEnabled) {
/* 129 */       chatPrepared = false;
/* 130 */       requestImmediateCapture("minecraft:chat_panel");
/*     */     } 
/*     */     
/* 133 */     lastHotbarEnabled = hotbarEnabled;
/* 134 */     lastTabEnabled = tabEnabled;
/* 135 */     lastChatAnimationEnabled = chatEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateHotbarAnimation(float currentSlotX, float targetSlotX, boolean mirror, int mirrorOffset) {
/* 144 */     hotbarCurrentSlotX = currentSlotX;
/* 145 */     hotbarTargetSlotX = targetSlotX;
/* 146 */     hotbarMirror = mirror;
/* 147 */     hotbarMirrorOffset = mirrorOffset;
/*     */   }
/*     */   
/*     */   public static void updateChatAnimation(float dx, float dy, float alpha, boolean active) {
/* 151 */     chatDx = dx;
/* 152 */     chatDy = dy;
/* 153 */     chatAlpha = clamp01(alpha);
/* 154 */     chatAnimationActive = (active || chatAlpha < 0.999F);
/*     */   }
/*     */   
/*     */   public static void requestImmediateCapture(String id) {
/* 158 */     if (id != null) {
/* 159 */       FORCE_CAPTURE.add(id);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beforeBufferRender(Object instance, class_332 context) {
/* 169 */     if (instance == null || reflectionFailed) {
/*     */       return;
/*     */     }
/* 172 */     frameContext = context;
/*     */     try {
/* 174 */       resolveInstanceReflection(instance.getClass());
/* 175 */       String id = readId(instance);
/* 176 */       Object buffer = instanceBufferField.get(instance);
/* 177 */       if (id == null || buffer == null) {
/*     */         return;
/*     */       }
/* 180 */       ID_BY_INSTANCE.put(instance, id);
/* 181 */       COMPONENT_BY_BUFFER.put(buffer, id);
/* 182 */       if ("minecraft:player_list".equals(id)) {
/* 183 */         playerListBuffer = buffer;
/*     */       }
/*     */       
/* 186 */       if (LISTENER_REGISTERED.add(instance)) {
/* 187 */         Supplier<Boolean> listener = () -> Boolean.valueOf(FORCE_CAPTURE.remove(id));
/* 188 */         registerUpdateListenerMethod.invoke(instance, new Object[] { listener });
/*     */       } 
/*     */       
/* 191 */       if ("minecraft:boss_bar".equals(id) && BossBarHud.getInstance().shouldRefreshExordium(System.nanoTime())) {
/* 192 */         FORCE_CAPTURE.add(id);
/*     */       }
/*     */       
/* 195 */       if ("minecraft:hotbar".equals(id) && lastHotbarEnabled && !hotbarPrepared) {
/* 196 */         FORCE_CAPTURE.add(id);
/* 197 */       } else if ("minecraft:player_list".equals(id) && lastTabEnabled && !tabPrepared) {
/* 198 */         FORCE_CAPTURE.add(id);
/* 199 */       } else if ("minecraft:chat_panel".equals(id) && lastChatAnimationEnabled && !chatPrepared) {
/* 200 */         FORCE_CAPTURE.add(id);
/*     */       } 
/*     */       
/* 203 */       if (FORCE_CAPTURE.contains(id)) {
/* 204 */         Object pacing = instancePacingField.get(instance);
/* 205 */         if (pacing != null) {
/* 206 */           if (pacingSetCooldownMethod == null) {
/* 207 */             pacingSetCooldownMethod = pacing.getClass().getMethod("setCooldown", new Class[] { long.class });
/*     */           }
/* 209 */           pacingSetCooldownMethod.invoke(pacing, new Object[] { Long.valueOf(0L) });
/*     */         } 
/*     */       } 
/* 212 */     } catch (Throwable ignored) {
/* 213 */       reflectionFailed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void afterBufferRender(Object instance) {
/* 223 */     if (instance == null || reflectionFailed) {
/*     */       return;
/*     */     }
/*     */     try {
/* 227 */       resolveInstanceReflection(instance.getClass());
/* 228 */       if (!instanceCapturingField.getBoolean(instance)) {
/*     */         return;
/*     */       }
/* 231 */       String id = ID_BY_INSTANCE.get(instance);
/* 232 */       if (id == null) {
/* 233 */         id = readId(instance);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 238 */       FORCE_CAPTURE.remove(id);
/* 239 */       CAPTURING.set(id);
/* 240 */       if ("minecraft:hotbar".equals(id)) {
/* 241 */         hotbarSelector = null;
/* 242 */       } else if ("minecraft:player_list".equals(id)) {
/* 243 */         tabBounds = null;
/* 244 */         tabGeometryKnown = false;
/* 245 */       } else if ("minecraft:chat_panel".equals(id)) {
/* 246 */         chatBounds = null;
/* 247 */         latestChatBounds = null;
/*     */       } 
/* 249 */     } catch (Throwable ignored) {
/* 250 */       reflectionFailed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void afterBufferPostRender(Object instance) {
/* 255 */     String id = CAPTURING.get();
/* 256 */     if ("minecraft:hotbar".equals(id)) {
/* 257 */       hotbarPrepared = (hotbarSelector != null);
/* 258 */     } else if ("minecraft:player_list".equals(id)) {
/* 259 */       tabPrepared = tabGeometryKnown;
/* 260 */     } else if ("minecraft:chat_panel".equals(id)) {
/* 261 */       chatPrepared = (chatBounds != null);
/*     */     } 
/* 263 */     CAPTURING.remove();
/*     */   }
/*     */   
/*     */   public static boolean isCapturing(String id) {
/* 267 */     return (id != null && id.equals(CAPTURING.get()));
/*     */   }
/*     */   
/*     */   public static boolean isCapturingHotbar() {
/* 271 */     return (isCapturing("minecraft:hotbar") && lastHotbarEnabled);
/*     */   }
/*     */   
/*     */   public static boolean isCapturingPlayerList() {
/* 275 */     return (isCapturing("minecraft:player_list") && lastTabEnabled);
/*     */   }
/*     */   
/*     */   public static boolean isCapturingChat() {
/* 279 */     return (isCapturing("minecraft:chat_panel") && lastChatAnimationEnabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean deferBufferedComponent(Object buffer) {
/*     */     boolean defer;
/* 288 */     String id = COMPONENT_BY_BUFFER.get(buffer);
/* 289 */     if (id == null) {
/* 290 */       return false;
/*     */     }
/* 292 */     if ("minecraft:scoreboard".equals(id) && shouldSuppressVanillaScoreboard())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       return true;
/*     */     }
/* 301 */     if ("minecraft:boss_bar".equals(id)) {
/* 302 */       NoRender noRender = NoRender.getInstance();
/* 303 */       if (noRender != null && noRender.isEnabled() && noRender.bossBar.isValue()) return true;
/*     */     
/*     */     } 
/*     */     
/* 307 */     if ("minecraft:hotbar".equals(id)) {
/* 308 */       defer = (lastHotbarEnabled && hotbarSelector != null);
/* 309 */     } else if ("minecraft:player_list".equals(id)) {
/* 310 */       defer = (lastTabEnabled && tabPrepared && isTabVisuallyAnimated());
/* 311 */     } else if ("minecraft:chat_panel".equals(id)) {
/* 312 */       defer = (lastChatAnimationEnabled && chatPrepared && chatAnimationActive && latestChatBounds != null);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 317 */       defer = false;
/*     */     } 
/*     */     
/* 320 */     if (defer) {
/* 321 */       DEFERRED.put(buffer, id);
/*     */     }
/* 323 */     return defer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderDeferredComponents() {
/* 331 */     class_332 context = frameContext;
/* 332 */     if (context == null || DEFERRED.isEmpty() || reflectionFailed) {
/* 333 */       DEFERRED.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 337 */     context.method_51452();
/* 338 */     RenderSystem.enableBlend();
/* 339 */     RenderSystem.blendFunc(GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */     
/* 343 */     RenderSystem.disableDepthTest();
/* 344 */     RenderSystem.depthMask(false);
/* 345 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*     */     try {
/* 348 */       for (Map.Entry<Object, String> entry : DEFERRED.entrySet()) {
/*     */         int texture;
/*     */         try {
/* 351 */           texture = getTextureId(entry.getKey());
/* 352 */         } catch (Throwable ignored) {
/*     */           continue;
/*     */         } 
/* 355 */         if (texture <= 0) {
/*     */           continue;
/*     */         }
/* 358 */         switch ((String)entry.getValue()) { case "minecraft:hotbar":
/* 359 */             renderHotbar(context, texture);
/* 360 */           case "minecraft:player_list": renderPlayerList(context, texture);
/* 361 */           case "minecraft:chat_panel": renderChat(context, texture); }
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 366 */       context.method_51452();
/*     */     } finally {
/* 368 */       DEFERRED.clear();
/* 369 */       RenderSystem.setShaderTexture(0, 0);
/* 370 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void recordHotbarSelection(Function<class_2960, class_1921> layerFactory, class_2960 texture, int x, int y, int width, int height) {
/* 388 */     if (!isCapturingHotbar() || layerFactory == null || texture == null) {
/*     */       return;
/*     */     }
/* 391 */     class_310 client = class_310.method_1551();
/* 392 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 395 */     float screenWidth = client.method_22683().method_4486();
/* 396 */     float screenHeight = client.method_22683().method_4502();
/* 397 */     float hotbarLeft = screenWidth * 0.5F - 91.0F;
/*     */ 
/*     */ 
/*     */     
/* 401 */     Rect component = new Rect(Math.max(0.0F, hotbarLeft - 36.0F), Math.max(0.0F, screenHeight - 42.0F), Math.min(screenWidth, hotbarLeft + 182.0F + 36.0F), screenHeight);
/*     */ 
/*     */     
/* 404 */     Rect clip = new Rect(hotbarLeft, screenHeight - 22.0F, hotbarLeft + 182.0F, screenHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 411 */     int baseX = x - Math.round(hotbarTargetSlotX);
/* 412 */     hotbarSelector = new HotbarSelector(layerFactory, texture, baseX, y, width, height, component, clip);
/*     */   }
/*     */   
/*     */   public static void recordTabGeometry(float pivotX, float pivotY) {
/* 416 */     if (!isCapturingPlayerList()) {
/*     */       return;
/*     */     }
/* 419 */     tabPivotX = pivotX;
/* 420 */     tabPivotY = pivotY;
/* 421 */     tabGeometryKnown = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void recordTabElement(class_332 context, float x1, float y1, float x2, float y2) {
/* 431 */     if (!isCapturingPlayerList()) {
/*     */       return;
/*     */     }
/* 434 */     tabBounds = union(tabBounds, transformRect(context, x1, y1, x2, y2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void recordChatElement(class_332 context, float x1, float y1, float x2, float y2, boolean latest) {
/* 445 */     if (!isCapturingChat()) {
/*     */       return;
/*     */     }
/* 448 */     Rect rect = transformRect(context, x1, y1, x2, y2);
/* 449 */     chatBounds = union(chatBounds, rect);
/* 450 */     if (latest) {
/* 451 */       latestChatBounds = union(latestChatBounds, rect);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void renderHotbar(class_332 context, int texture) {
/* 456 */     HotbarSelector selector = hotbarSelector;
/* 457 */     if (selector == null) {
/*     */       return;
/*     */     }
/* 460 */     Matrix4f matrix = baseModelViewMatrix(context);
/* 461 */     drawExordiumTexture(texture, matrix, 1.0F);
/*     */     
/* 463 */     int drawX = selector.baseX + Math.round(hotbarCurrentSlotX);
/* 464 */     context.method_44379(
/* 465 */         Math.round(selector.clipBounds.left), 
/* 466 */         Math.round(selector.clipBounds.top), 
/* 467 */         Math.round(selector.clipBounds.right), 
/* 468 */         Math.round(selector.clipBounds.bottom));
/*     */     
/* 470 */     context.method_52706(selector.layerFactory, selector.texture, drawX, selector.y, selector.width, selector.height);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     if (hotbarMirror) {
/* 479 */       context.method_52706(selector.layerFactory, selector.texture, drawX + hotbarMirrorOffset, selector.y, selector.width, selector.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     context.method_51452();
/* 489 */     context.method_44380();
/*     */   }
/*     */   
/*     */   private static void renderPlayerList(class_332 context, int texture) {
/* 493 */     class_310 client = class_310.method_1551();
/* 494 */     if (client == null || client.method_22683() == null) {
/*     */       return;
/*     */     }
/* 497 */     Animations animations = Animations.getInstance();
/* 498 */     if (animations == null) {
/*     */       return;
/*     */     }
/*     */     
/* 502 */     Matrix4f matrix = baseModelViewMatrix(context);
/* 503 */     if (animations.isTabSlideStyle()) {
/* 504 */       matrix.translate(0.0F, animations.currentTabSlideOffset(), 0.0F);
/*     */     } else {
/* 506 */       float scale = Math.max(0.01F, animations.currentTabProgress());
/* 507 */       matrix.translate(tabPivotX, tabPivotY, 0.0F);
/* 508 */       matrix.scale(scale, scale, 1.0F);
/* 509 */       matrix.translate(-tabPivotX, -tabPivotY, 0.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     drawExordiumTexture(texture, matrix, 1.0F);
/*     */   }
/*     */   
/*     */   private static void renderChat(class_332 context, int texture) {
/* 519 */     Rect component = chatBounds;
/* 520 */     Rect latest = latestChatBounds;
/* 521 */     if (component == null || latest == null) {
/*     */       return;
/*     */     }
/*     */     
/* 525 */     Matrix4f baseMatrix = baseModelViewMatrix(context);
/* 526 */     drawTextureMinusHole(context, texture, component, latest, baseMatrix);
/*     */     
/* 528 */     Rect destination = latest.offset(chatDx, chatDy);
/* 529 */     Rect clippedDestination = intersection(destination, component);
/* 530 */     if (clippedDestination != null) {
/* 531 */       Matrix4f movedMatrix = (new Matrix4f((Matrix4fc)baseMatrix)).translate(chatDx, chatDy, 0.0F);
/* 532 */       drawExordiumTextureClipped(context, texture, movedMatrix, chatAlpha, clippedDestination);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isTabVisuallyAnimated() {
/* 537 */     Animations animations = Animations.getInstance();
/* 538 */     if (animations == null || !animations.isTabSlideEnabled()) {
/* 539 */       return false;
/*     */     }
/* 541 */     if (animations.isTabSlideStyle()) {
/* 542 */       return (Math.abs(animations.currentTabSlideOffset()) > 0.01F || animations
/* 543 */         .currentTabAlpha() < 0.999F);
/*     */     }
/* 545 */     return (animations.currentTabProgress() < 0.999F || animations
/* 546 */       .currentTabAlpha() < 0.999F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean renderClosingPlayerListFromCache() {
/*     */     int texture;
/* 556 */     class_332 context = frameContext;
/* 557 */     Object buffer = playerListBuffer;
/* 558 */     if (context == null || buffer == null || reflectionFailed || !lastTabEnabled || !tabPrepared)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 563 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 568 */       texture = getTextureId(buffer);
/* 569 */     } catch (Throwable ignored) {
/* 570 */       return false;
/*     */     } 
/* 572 */     if (texture <= 0) {
/* 573 */       return false;
/*     */     }
/*     */     
/* 576 */     context.method_51452();
/* 577 */     RenderSystem.enableBlend();
/* 578 */     RenderSystem.blendFunc(GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */     
/* 582 */     RenderSystem.disableDepthTest();
/* 583 */     RenderSystem.depthMask(false);
/* 584 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     try {
/* 586 */       renderPlayerList(context, texture);
/* 587 */       context.method_51452();
/* 588 */       return true;
/*     */     } finally {
/* 590 */       RenderSystem.setShaderTexture(0, 0);
/* 591 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 592 */       RenderSystem.depthMask(true);
/* 593 */       RenderSystem.enableDepthTest();
/* 594 */       RenderSystem.defaultBlendFunc();
/*     */ 
/*     */ 
/*     */       
/* 598 */       RenderSystem.enableBlend();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean shouldSuppressVanillaScoreboard() {
/* 603 */     ScoreboardHud scoreboard = ScoreboardHud.getInstance();
/* 604 */     if (scoreboard != null && scoreboard.isEnabled()) {
/* 605 */       return true;
/*     */     }
/* 607 */     NoRender noRender = NoRender.getInstance();
/* 608 */     return (noRender != null && noRender.isEnabled() && noRender.scoreboard.isValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawTextureMinusHole(class_332 context, int texture, Rect component, Rect hole, Matrix4f matrix) {
/* 618 */     Rect clippedHole = intersection(component, hole);
/* 619 */     if (clippedHole == null) {
/* 620 */       drawExordiumTextureClipped(context, texture, matrix, 1.0F, component);
/*     */       
/*     */       return;
/*     */     } 
/* 624 */     drawExordiumTextureClipped(context, texture, matrix, 1.0F, new Rect(component.left, component.top, component.right, clippedHole.top));
/*     */     
/* 626 */     drawExordiumTextureClipped(context, texture, matrix, 1.0F, new Rect(component.left, clippedHole.bottom, component.right, component.bottom));
/*     */     
/* 628 */     drawExordiumTextureClipped(context, texture, matrix, 1.0F, new Rect(component.left, clippedHole.top, clippedHole.left, clippedHole.bottom));
/*     */     
/* 630 */     drawExordiumTextureClipped(context, texture, matrix, 1.0F, new Rect(clippedHole.right, clippedHole.top, component.right, clippedHole.bottom));
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
/*     */   private static void drawExordiumTextureClipped(class_332 context, int texture, Matrix4f matrix, float alpha, Rect clip) {
/* 650 */     if (clip == null || clip.isEmpty() || alpha <= 0.001F) {
/*     */       return;
/*     */     }
/* 653 */     context.method_44379(
/* 654 */         (int)Math.floor(clip.left), 
/* 655 */         (int)Math.floor(clip.top), 
/* 656 */         (int)Math.ceil(clip.right), 
/* 657 */         (int)Math.ceil(clip.bottom));
/*     */     
/*     */     try {
/* 660 */       drawExordiumTexture(texture, matrix, alpha);
/*     */     } finally {
/* 662 */       context.method_44380();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void drawExordiumTexture(int texture, Matrix4f matrix, float alpha) {
/* 667 */     if (texture <= 0 || matrix == null || alpha <= 0.001F || reflectionFailed) {
/*     */       return;
/*     */     }
/*     */     try {
/* 671 */       resolveExordiumRendererReflection();
/* 672 */       Object model = exordiumGetModelMethod.invoke(null, new Object[0]);
/* 673 */       Object shaderObject = exordiumGetMultiTextureShaderMethod.invoke(exordiumShaderManager, new Object[0]);
/* 674 */       Object uniformObject = exordiumGetTextureCountUniformMethod.invoke(exordiumShaderManager, new Object[0]);
/* 675 */       if (shaderObject instanceof class_5944) { class_5944 shader = (class_5944)shaderObject; if (uniformObject instanceof class_284)
/* 676 */         { class_284 textureCount = (class_284)uniformObject; if (model == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 681 */           RenderSystem.setShader(shader);
/* 682 */           textureCount.method_35649(1);
/* 683 */           RenderSystem.setShaderTexture(0, texture);
/* 684 */           RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, clamp01(alpha));
/* 685 */           RenderSystem.enableBlend();
/* 686 */           RenderSystem.blendFunc(GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */           
/* 690 */           exordiumModelDrawMethod.invoke(model, new Object[] { matrix }); return; }  }  return;
/* 691 */     } catch (Throwable ignored) {
/* 692 */       reflectionFailed = true;
/*     */     } finally {
/* 694 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Matrix4f baseModelViewMatrix(class_332 context) {
/* 699 */     Matrix4f matrix = new Matrix4f((Matrix4fc)RenderSystem.getModelViewMatrix());
/* 700 */     if (context != null) {
/* 701 */       matrix.mul((Matrix4fc)context.method_51448().method_23760().method_23761());
/*     */     }
/* 703 */     return matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Rect transformRect(class_332 context, float x1, float y1, float x2, float y2) {
/* 713 */     Matrix4f matrix = context.method_51448().method_23760().method_23761();
/* 714 */     Vector4f p1 = matrix.transform(new Vector4f(x1, y1, 0.0F, 1.0F));
/* 715 */     Vector4f p2 = matrix.transform(new Vector4f(x2, y1, 0.0F, 1.0F));
/* 716 */     Vector4f p3 = matrix.transform(new Vector4f(x2, y2, 0.0F, 1.0F));
/* 717 */     Vector4f p4 = matrix.transform(new Vector4f(x1, y2, 0.0F, 1.0F));
/* 718 */     float left = (float)Math.floor(Math.min(Math.min(p1.x, p2.x), Math.min(p3.x, p4.x)));
/* 719 */     float top = (float)Math.floor(Math.min(Math.min(p1.y, p2.y), Math.min(p3.y, p4.y)));
/* 720 */     float right = (float)Math.ceil(Math.max(Math.max(p1.x, p2.x), Math.max(p3.x, p4.x)));
/* 721 */     float bottom = (float)Math.ceil(Math.max(Math.max(p1.y, p2.y), Math.max(p3.y, p4.y)));
/* 722 */     return new Rect(left, top, right, bottom);
/*     */   }
/*     */   
/*     */   private static Rect union(Rect first, Rect second) {
/* 726 */     if (second == null || second.isEmpty()) {
/* 727 */       return first;
/*     */     }
/* 729 */     if (first == null || first.isEmpty()) {
/* 730 */       return second;
/*     */     }
/* 732 */     return new Rect(
/* 733 */         Math.min(first.left, second.left), 
/* 734 */         Math.min(first.top, second.top), 
/* 735 */         Math.max(first.right, second.right), 
/* 736 */         Math.max(first.bottom, second.bottom));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Rect intersection(Rect first, Rect second) {
/* 741 */     float left = Math.max(first.left, second.left);
/* 742 */     float top = Math.max(first.top, second.top);
/* 743 */     float right = Math.min(first.right, second.right);
/* 744 */     float bottom = Math.min(first.bottom, second.bottom);
/* 745 */     if (right <= left || bottom <= top) {
/* 746 */       return null;
/*     */     }
/* 748 */     return new Rect(left, top, right, bottom);
/*     */   }
/*     */   
/*     */   private static float clamp01(float value) {
/* 752 */     return Math.max(0.0F, Math.min(1.0F, value));
/*     */   }
/*     */   
/*     */   private static int getTextureId(Object buffer) throws Exception {
/* 756 */     if (bufferTextureMethod == null) {
/* 757 */       bufferTextureMethod = buffer.getClass().getMethod("getTextureId", new Class[0]);
/*     */     }
/* 759 */     return ((Number)bufferTextureMethod.invoke(buffer, new Object[0])).intValue();
/*     */   }
/*     */   
/*     */   private static String readId(Object instance) throws Exception {
/* 763 */     Object value = instanceIdField.get(instance);
/* 764 */     return (value == null) ? null : value.toString();
/*     */   }
/*     */   
/*     */   private static void resolveInstanceReflection(Class<?> type) throws Exception {
/* 768 */     if (instanceIdField != null) {
/*     */       return;
/*     */     }
/* 771 */     instanceIdField = accessible(type.getDeclaredField("id"));
/* 772 */     instanceBufferField = accessible(type.getDeclaredField("buffer"));
/* 773 */     instancePacingField = accessible(type.getDeclaredField("pacing"));
/* 774 */     instanceCapturingField = accessible(type.getDeclaredField("isCapturing"));
/* 775 */     registerUpdateListenerMethod = type.getMethod("registerUpdateListener", new Class[] { Supplier.class });
/*     */   }
/*     */   
/*     */   private static void resolveExordiumRendererReflection() throws Exception {
/* 779 */     if (exordiumModelDrawMethod != null && exordiumShaderManager != null) {
/*     */       return;
/*     */     }
/*     */     
/* 783 */     Class<?> baseType = Class.forName("dev.tr7zw.exordium.ExordiumModBase");
/* 784 */     exordiumInstanceField = baseType.getField("instance");
/* 785 */     Object exordium = exordiumInstanceField.get(null);
/* 786 */     exordiumGetShaderManagerMethod = baseType.getMethod("getCustomShaderManager", new Class[0]);
/* 787 */     exordiumShaderManager = exordiumGetShaderManagerMethod.invoke(exordium, new Object[0]);
/*     */     
/* 789 */     Class<?> shaderManagerType = exordiumShaderManager.getClass();
/*     */     
/* 791 */     exordiumGetMultiTextureShaderMethod = shaderManagerType.getMethod("getPositionMultiTexShader", new Class[0]);
/*     */     
/* 793 */     exordiumGetTextureCountUniformMethod = shaderManagerType.getMethod("getPositionMultiTexTextureCountUniform", new Class[0]);
/*     */ 
/*     */     
/* 796 */     Class<?> bufferedComponentType = Class.forName("dev.tr7zw.exordium.render.BufferedComponent");
/* 797 */     exordiumGetModelMethod = bufferedComponentType.getMethod("getModel", new Class[0]);
/* 798 */     Class<?> modelType = Class.forName("dev.tr7zw.exordium.render.Model");
/* 799 */     exordiumModelDrawMethod = modelType.getMethod("draw", new Class[] { Matrix4f.class });
/*     */   }
/*     */   
/*     */   private static <T extends java.lang.reflect.AccessibleObject> T accessible(T object) {
/* 803 */     object.setAccessible(true);
/* 804 */     return object;
/*     */   }
/*     */   private static final class Rect extends Record { private final float left; private final float top; private final float right; private final float bottom;
/* 807 */     private Rect(float left, float top, float right, float bottom) { this.left = left; this.top = top; this.right = right; this.bottom = bottom; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$Rect;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #807	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 807 */       //   0	7	0	this	Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$Rect; } public float left() { return this.left; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$Rect;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #807	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$Rect; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$Rect;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #807	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$Rect;
/* 807 */       //   0	8	1	o	Ljava/lang/Object; } public float top() { return this.top; } public float right() { return this.right; } public float bottom() { return this.bottom; }
/*     */      float width() {
/* 809 */       return this.right - this.left;
/*     */     }
/*     */     
/*     */     float height() {
/* 813 */       return this.bottom - this.top;
/*     */     }
/*     */     
/*     */     boolean isEmpty() {
/* 817 */       return (width() <= 0.0F || height() <= 0.0F);
/*     */     }
/*     */     
/*     */     Rect offset(float x, float y) {
/* 821 */       return new Rect(this.left + x, this.top + y, this.right + x, this.bottom + y);
/*     */     } }
/*     */   private static final class HotbarSelector extends Record { private final Function<class_2960, class_1921> layerFactory; private final class_2960 texture; private final int baseX; private final int y; private final int width; private final int height; private final ExordiumAnimationBridge.Rect componentBounds; private final ExordiumAnimationBridge.Rect clipBounds;
/*     */     
/* 825 */     private HotbarSelector(Function<class_2960, class_1921> layerFactory, class_2960 texture, int baseX, int y, int width, int height, ExordiumAnimationBridge.Rect componentBounds, ExordiumAnimationBridge.Rect clipBounds) { this.layerFactory = layerFactory; this.texture = texture; this.baseX = baseX; this.y = y; this.width = width; this.height = height; this.componentBounds = componentBounds; this.clipBounds = clipBounds; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$HotbarSelector;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #825	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$HotbarSelector; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$HotbarSelector;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #825	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$HotbarSelector; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$HotbarSelector;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #825	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/api/system/hud/ExordiumAnimationBridge$HotbarSelector;
/* 825 */       //   0	8	1	o	Ljava/lang/Object; } public Function<class_2960, class_1921> layerFactory() { return this.layerFactory; } public class_2960 texture() { return this.texture; } public int baseX() { return this.baseX; } public int y() { return this.y; } public int width() { return this.width; } public int height() { return this.height; } public ExordiumAnimationBridge.Rect componentBounds() { return this.componentBounds; } public ExordiumAnimationBridge.Rect clipBounds() { return this.clipBounds; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\hud\ExordiumAnimationBridge.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */