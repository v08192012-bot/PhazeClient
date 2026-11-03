/*      */ package vorga.phazeclient.mixins;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import java.time.LocalTime;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Vector;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import net.minecraft.class_1058;
/*      */ import net.minecraft.class_1291;
/*      */ import net.minecraft.class_1293;
/*      */ import net.minecraft.class_1297;
/*      */ import net.minecraft.class_1299;
/*      */ import net.minecraft.class_1533;
/*      */ import net.minecraft.class_1657;
/*      */ import net.minecraft.class_1661;
/*      */ import net.minecraft.class_1702;
/*      */ import net.minecraft.class_1792;
/*      */ import net.minecraft.class_1796;
/*      */ import net.minecraft.class_1799;
/*      */ import net.minecraft.class_1802;
/*      */ import net.minecraft.class_1921;
/*      */ import net.minecraft.class_1935;
/*      */ import net.minecraft.class_2248;
/*      */ import net.minecraft.class_2338;
/*      */ import net.minecraft.class_2350;
/*      */ import net.minecraft.class_239;
/*      */ import net.minecraft.class_243;
/*      */ import net.minecraft.class_2561;
/*      */ import net.minecraft.class_266;
/*      */ import net.minecraft.class_268;
/*      */ import net.minecraft.class_2680;
/*      */ import net.minecraft.class_269;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_327;
/*      */ import net.minecraft.class_332;
/*      */ import net.minecraft.class_338;
/*      */ import net.minecraft.class_3481;
/*      */ import net.minecraft.class_3532;
/*      */ import net.minecraft.class_3965;
/*      */ import net.minecraft.class_3966;
/*      */ import net.minecraft.class_4050;
/*      */ import net.minecraft.class_4076;
/*      */ import net.minecraft.class_4081;
/*      */ import net.minecraft.class_4587;
/*      */ import net.minecraft.class_490;
/*      */ import net.minecraft.class_5250;
/*      */ import net.minecraft.class_5321;
/*      */ import net.minecraft.class_5348;
/*      */ import net.minecraft.class_5498;
/*      */ import net.minecraft.class_640;
/*      */ import net.minecraft.class_746;
/*      */ import net.minecraft.class_7923;
/*      */ import net.minecraft.class_8646;
/*      */ import net.minecraft.class_9011;
/*      */ import net.minecraft.class_9779;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Vector3f;
/*      */ import org.lwjgl.glfw.GLFW;
/*      */ import org.spongepowered.asm.mixin.Final;
/*      */ import org.spongepowered.asm.mixin.Mixin;
/*      */ import org.spongepowered.asm.mixin.Shadow;
/*      */ import org.spongepowered.asm.mixin.Unique;
/*      */ import org.spongepowered.asm.mixin.injection.At;
/*      */ import org.spongepowered.asm.mixin.injection.Inject;
/*      */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*      */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*      */ import vorga.phazeclient.api.feature.module.Module;
/*      */ import vorga.phazeclient.api.feature.module.ModuleProvider;
/*      */ import vorga.phazeclient.api.system.cursor.HudCursorRelay;
/*      */ import vorga.phazeclient.api.system.hud.BatchedHudBuffer;
/*      */ import vorga.phazeclient.api.system.hud.BossBarHudAccess;
/*      */ import vorga.phazeclient.api.system.hud.ChatAnimationFrameAccess;
/*      */ import vorga.phazeclient.api.system.hud.ExordiumAnimationBridge;
/*      */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*      */ import vorga.phazeclient.api.system.shape.implement.Blur;
/*      */ import vorga.phazeclient.api.system.shape.implement.Rectangle;
/*      */ import vorga.phazeclient.core.Main;
/*      */ import vorga.phazeclient.helpers.IntPoint;
/*      */ import vorga.phazeclient.helpers.TextureHelper;
/*      */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ArmorHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ComboCounterHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.Consumable;
/*      */ import vorga.phazeclient.implement.features.modules.hud.Cooldowns;
/*      */ import vorga.phazeclient.implement.features.modules.hud.CoordinatesHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.CpsHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.DayCounterHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.DirectionHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.FpsHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.InventoryHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.KeystrokesHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.MemoryHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.MovementSpeedHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.PackDisplayHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.PingHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.PlayerModelHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.PotionHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ReachHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.RectHudModule;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ScoreboardHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ScoreboardSidebarEntry;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ServerAddressHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.SessionTimeHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.SprintHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.TimeHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.TpsHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.WailaHud;
/*      */ import vorga.phazeclient.implement.features.modules.other.Animations;
/*      */ import vorga.phazeclient.implement.features.modules.other.AutoSprint;
/*      */ import vorga.phazeclient.implement.features.modules.other.Crosshair;
/*      */ import vorga.phazeclient.implement.features.modules.other.HealingHelper;
/*      */ import vorga.phazeclient.implement.features.modules.other.HealthIndicator;
/*      */ import vorga.phazeclient.implement.features.modules.other.HudOptimizer;
/*      */ import vorga.phazeclient.implement.features.modules.other.ItemHighlighter;
/*      */ import vorga.phazeclient.implement.features.modules.other.MaceIndicator;
/*      */ import vorga.phazeclient.implement.features.modules.other.NickHider;
/*      */ import vorga.phazeclient.implement.features.modules.other.NoRender;
/*      */ import vorga.phazeclient.implement.features.modules.other.Saturation;
/*      */ import vorga.phazeclient.implement.features.modules.other.StreamerMode;
/*      */ import vorga.phazeclient.implement.features.modules.other.TrapTimer;
/*      */ import vorga.phazeclient.implement.features.modules.other.Zoom;
/*      */ import vorga.phazeclient.implement.menu.components.implement.settings.ScaleSnapOverlay;
/*      */ 
/*      */ @Mixin({class_329.class})
/*      */ public class InGameHudMixin {
/*  138 */   private static final Rectangle HUD_BACKGROUND_RECTANGLE = new Rectangle();
/*      */   private static final float BASE_WIDTH = 60.0F;
/*      */   private static final float BASE_HEIGHT = 20.0F;
/*      */   private static final int HUD_TEXT_COLOR = -1;
/*      */   private static final float HUD_TEXT_SIZE = 8.0F;
/*      */   private static final float HUD_TEXT_RENDER_Z = 1000.0F;
/*      */   private static final int HANDLE_COLOR = -9242668;
/*      */   private static final float BASE_HOVER_OUTLINE_THICKNESS = 1.0F;
/*      */   private static final float HUD_RENDER_Z = 400.0F;
/*      */   private static final float HANDLE_RENDER_Z = 450.0F;
/*      */   private static final float GUIDE_SNAP_RADIUS = 3.0F;
/*      */   private static final float HUD_TO_HUD_SNAP_RADIUS = 4.0F;
/*      */   private static final float GUIDE_FADE_SPEED = 14.0F;
/*      */   private static final int GUIDE_MAX_ALPHA = 140;
/*      */   private static final long HUD_TEXT_THROTTLE_MS = 50L;
/*  153 */   private static final class_2960 DIRECTION_TRIANGLE_TEXTURE = class_2960.method_60655("phaze", "textures/down_triangle.png");
/*      */   
/*      */   private static final int HUD_FPS = 0;
/*      */   private static final int HUD_CPS = 1;
/*      */   private static final int HUD_REACH = 2;
/*      */   private static final int HUD_SPRINT = 3;
/*      */   private static final int HUD_COORDINATES = 4;
/*      */   private static final int HUD_PING = 5;
/*      */   private static final int HUD_KEYSTROKES = 6;
/*      */   private static final int HUD_POTION = 7;
/*      */   private static final int HUD_DAY_COUNTER = 8;
/*      */   private static final int HUD_DIRECTION = 9;
/*      */   private static final int HUD_TAB = 10;
/*      */   private static final int HUD_NAMETAG = 11;
/*      */   private static final int HUD_TIME = 12;
/*      */   private static final int HUD_SESSION = 13;
/*      */   private static final int HUD_SCOREBOARD = 14;
/*      */   private static final int HUD_MEMORY = 15;
/*      */   private static final int HUD_COMBO = 16;
/*      */   private static final int HUD_SERVER_ADDRESS = 17;
/*      */   private static final int HUD_MOVEMENT_SPEED = 18;
/*      */   private static final int HUD_WAILA = 19;
/*      */   private static final int HUD_HEALTH_INDICATOR = 20;
/*      */   private static final int HUD_BATTLE_INFO = 21;
/*      */   private static final int HUD_CONSUMABLE = 22;
/*      */   private static final int HUD_TPS = 23;
/*      */   private static final int HUD_PLAYER_MODEL = 24;
/*      */   private static final int HUD_TRAP_TIMER = 25;
/*      */   private static final int HUD_INVENTORY = 26;
/*      */   private static final int HUD_PACK_DISPLAY = 27;
/*      */   private static final int HUD_BOSS_BAR = 28;
/*      */   private static final int RECT_HUD_COUNT = 29;
/*      */   private static final int HUD_ARMOR_BLUR_SLOT = 27;
/*      */   private static final int HUD_SNAP_ARMOR = 29;
/*      */   private static final int HUD_SNAP_COUNT = 30;
/*      */   private static final int KEYSTROKE_W = 0;
/*      */   private static final int KEYSTROKE_A = 1;
/*      */   private static final int KEYSTROKE_S = 2;
/*      */   private static final int KEYSTROKE_D = 3;
/*      */   private static final int KEYSTROKE_LMB = 4;
/*      */   private static final int KEYSTROKE_RMB = 5;
/*      */   private static final int KEYSTROKE_SPACE = 6;
/*  195 */   private static final List<ShapeProperties> KEYSTROKE_BLUR_RECTS = List.of(
/*  196 */       createKeystrokeBlurRect(20.0F, 0.0F, 16.0F, 18.0F), 
/*  197 */       createKeystrokeBlurRect(0.0F, 19.0F, 18.0F, 18.0F), 
/*  198 */       createKeystrokeBlurRect(19.0F, 19.0F, 16.0F, 18.0F), 
/*  199 */       createKeystrokeBlurRect(36.0F, 19.0F, 18.0F, 18.0F), 
/*  200 */       createKeystrokeBlurRect(0.0F, 38.0F, 54.0F, 8.0F), 
/*  201 */       createKeystrokeBlurRect(0.0F, 47.0F, 26.0F, 16.0F), 
/*  202 */       createKeystrokeBlurRect(28.0F, 47.0F, 26.0F, 16.0F));
/*      */ 
/*      */   
/*  205 */   private static final boolean[] RECT_DRAGGING = new boolean[29];
/*  206 */   private static final boolean[] RECT_RESIZING = new boolean[29];
/*  207 */   private static final float[] RECT_DRAG_OFFSET_X = new float[29];
/*  208 */   private static final float[] RECT_DRAG_OFFSET_Y = new float[29];
/*  209 */   private static final float[] RECT_RESIZE_START_WIDTH = new float[29];
/*  210 */   private static final float[] RECT_RESIZE_START_MOUSE_X = new float[29];
/*  211 */   private static final float[] RECT_RESIZE_START_MOUSE_Y = new float[29];
/*  212 */   private static final float[] RECT_HOVER_PROGRESS = new float[29];
/*  213 */   private static final float[] RECT_POSITION_RATIO_X = new float[29];
/*  214 */   private static final float[] RECT_POSITION_RATIO_Y = new float[29];
/*  215 */   private static final int[] RECT_LAST_SCREEN_WIDTH = new int[29];
/*  216 */   private static final int[] RECT_LAST_SCREEN_HEIGHT = new int[29];
/*  217 */   private static final boolean[] RECT_LAYOUT_INITIALIZED = new boolean[29];
/*  218 */   private static final int[] RECT_BG_ANIMATED_COLOR = new int[29];
/*  219 */   private static final boolean[] RECT_BG_COLOR_INITIALIZED = new boolean[29];
/*  220 */   private static final float[] HUD_SNAP_X = new float[30];
/*  221 */   private static final float[] HUD_SNAP_Y = new float[30];
/*  222 */   private static final float[] HUD_SNAP_WIDTH = new float[30];
/*  223 */   private static final float[] HUD_SNAP_HEIGHT = new float[30];
/*  224 */   private static final Module[] HUD_SNAP_OWNER = new Module[30];
/*  225 */   private static final boolean[] HUD_SNAP_VALID = new boolean[30];
/*  226 */   private static final float[] KEYSTROKE_PROGRESS = new float[7];
/*  227 */   private static final String[] HUD_TEXT_CACHE = new String[29];
/*  228 */   private static final long[] HUD_TEXT_CACHE_TIME_MS = new long[29]; private static final int HUD_TEXT_WIDTH_CACHE_MAX = 512;
/*      */   
/*  230 */   private static final Map<String, Float> HUD_TEXT_WIDTH_CACHE = new LinkedHashMap<String, Float>(512, 0.75F, true)
/*      */     {
/*      */       protected boolean removeEldestEntry(Map.Entry<String, Float> eldest) {
/*  233 */         return (size() > 512);
/*      */       }
/*      */     };
/*  236 */   private static List<String> coordinatesLinesCache = new ArrayList<>();
/*  237 */   private static String coordinatesBiomeNameCache = "";
/*  238 */   private static int coordinatesBiomeColorCache = 16777045;
/*  239 */   private static String coordinatesFacingCache = "";
/*  240 */   private static String coordinatesTopSignCache = "";
/*  241 */   private static String coordinatesBottomSignCache = "";
/*  242 */   private static List<class_1799> armorStacksCache = new ArrayList<>();
/*  243 */   private static List<String> armorDurabilityTextsCache = new ArrayList<>();
/*  244 */   private static List<class_1293> potionEffectsCache = new ArrayList<>();
/*  245 */   private static List<String> potionNamesCache = new ArrayList<>();
/*  246 */   private static List<String> potionDurationsCache = new ArrayList<>();
/*      */   private static boolean potionSampleCache = false;
/*      */   private static boolean potionCacheInitialized = false;
/*  249 */   private static long potionSampleCycle = Long.MIN_VALUE;
/*      */   private static int potionSampleIndex;
/*  251 */   private static final Random POTION_PREVIEW_RANDOM = new Random();
/*  252 */   private static final String[][] POTION_PREVIEW_SAMPLES = new String[][] { { "Speed II", "01:48" }, { "Strength", "02:31" }, { "Regeneration II", "00:45" }, { "Invisibility", "02:14" }, { "Fire Resistance", "04:12" }, { "Night Vision", "03:06" } };
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean armorDragging = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean armorResizing = false;
/*      */ 
/*      */   
/*  263 */   private static float armorDragOffsetX = 0.0F;
/*  264 */   private static float armorDragOffsetY = 0.0F;
/*  265 */   private static float armorResizeStartScale = 1.0F;
/*  266 */   private static float armorResizeStartMouseX = 0.0F;
/*  267 */   private static float armorResizeStartMouseY = 0.0F;
/*  268 */   private static float armorHoverProgress = 0.0F;
/*  269 */   private static float armorPositionRatioX = 0.0F;
/*  270 */   private static float armorPositionRatioY = 0.0F;
/*  271 */   private static int armorLastScreenWidth = -1;
/*  272 */   private static int armorLastScreenHeight = -1;
/*      */   private static boolean armorLayoutInitialized = false;
/*  274 */   private static int armorAnimatedBackgroundColor = 0;
/*      */   private static boolean armorBackgroundColorInitialized = false;
/*  276 */   private static int scoreboardAnimatedTitleColor = 0;
/*  277 */   private static int scoreboardAnimatedRowColor = 0;
/*      */   
/*      */   private static boolean scoreboardBackgroundColorsInitialized = false;
/*  280 */   private static final Deque<Long> LEFT_CLICKS = new ArrayDeque<>();
/*  281 */   private static final Deque<Long> RIGHT_CLICKS = new ArrayDeque<>();
/*      */   private static boolean renderedThisFrame = false;
/*      */   private static boolean wasMouseDown = false;
/*      */   private static boolean wasLeftMouseDown = false;
/*      */   private static boolean wasRightMouseDown = false;
/*  286 */   private static long lastFrameNanos = -1L;
/*  287 */   private static float cachedFrameDeltaSeconds = 0.0F;
/*  288 */   private static float verticalGuideProgress = 0.0F;
/*  289 */   private static float horizontalGuideProgress = 0.0F;
/*      */   private static boolean showVerticalGuideThisFrame = false;
/*      */   private static boolean showHorizontalGuideThisFrame = false;
/*  292 */   private static float hudVerticalGuideProgress = 0.0F;
/*  293 */   private static float hudHorizontalGuideProgress = 0.0F;
/*      */   private static boolean showHudVerticalGuideThisFrame = false;
/*      */   private static boolean showHudHorizontalGuideThisFrame = false;
/*  296 */   private static float hudVerticalGuideX = 0.0F;
/*  297 */   private static float hudVerticalGuideTop = 0.0F;
/*  298 */   private static float hudVerticalGuideBottom = 0.0F;
/*  299 */   private static float hudHorizontalGuideY = 0.0F;
/*  300 */   private static float hudHorizontalGuideLeft = 0.0F;
/*  301 */   private static float hudHorizontalGuideRight = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inBatchPass = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean batchIncludesBlur = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inLogicOnlyPass = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inGradientPass = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean phaze$hasActiveHudBlurThisFrame = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  339 */   private static final Map<Object, Boolean> PHAZE_LAST_BLUR_STATE = new IdentityHashMap<>();
/*  340 */   private static final Map<Object, Boolean> PHAZE_LAST_GRADIENT_STATE = new IdentityHashMap<>();
/*  341 */   private static float directionDisplayYaw = Float.NaN;
/*  342 */   private static final long SESSION_START_MS = System.currentTimeMillis(); @Shadow @Final
/*      */   private class_310 field_2035; @Shadow
/*      */   @Final
/*      */   private class_355 field_2015; @Unique
/*  346 */   private static void phaze$resetGuiRenderState() { RenderSystem.depthMask(true);
/*  347 */     RenderSystem.enableDepthTest();
/*  348 */     RenderSystem.defaultBlendFunc();
/*  349 */     RenderSystem.disableBlend();
/*  350 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); }
/*      */ 
/*      */   
/*      */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*      */   private void beginHudFrame(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/*  355 */     renderedThisFrame = false;
/*  356 */     inBatchPass = false;
/*  357 */     batchIncludesBlur = false;
/*  358 */     inLogicOnlyPass = false;
/*  359 */     inGradientPass = false;
/*  360 */     HudCursorRelay.reset();
/*  361 */     Blur.INSTANCE.beginCachedFrame();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     ExordiumAnimationBridge.beginHudFrame(context);
/*  367 */     if (this.field_2035 != null && this.field_2035.field_1705 != null) {
/*  368 */       if (BossBarHud.getInstance().isEnabled()) {
/*  369 */         ((BossBarHudAccess)this.field_2035.field_1705.method_1740()).phaze$updateLayout();
/*      */       }
/*  371 */       BossBarHud.getInstance().beginFrame(System.nanoTime());
/*  372 */       if (!BossBarHud.getInstance().isEnabled() || !(this.field_2035.field_1755 instanceof net.minecraft.class_408)) {
/*  373 */         RECT_DRAGGING[28] = false;
/*  374 */         RECT_RESIZING[28] = false;
/*  375 */         RECT_HOVER_PROGRESS[28] = 0.0F;
/*      */       } 
/*      */     } 
/*  378 */     phaze$advanceHotbarSlide();
/*  379 */     if (this.field_2035 != null && this.field_2035.field_1705 != null) {
/*  380 */       class_338 class_338 = this.field_2035.field_1705.method_1743(); if (class_338 instanceof ChatAnimationFrameAccess) { ChatAnimationFrameAccess access = (ChatAnimationFrameAccess)class_338;
/*  381 */         access.phaze$tickAnimationFrame(); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderStatusEffectOverlay"}, at = {@At("HEAD")}, cancellable = true)
/*      */   private void phaze$suppressVanillaStatusEffectOverlay(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/*  397 */     if (PotionHud.getInstance().isEnabled()) {
/*  398 */       ci.cancel();
/*      */     }
/*      */   }
/*      */   
/*      */   @Inject(method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"}, at = {@At("HEAD")}, cancellable = true)
/*      */   private void phaze$suppressVanillaScoreboardSidebar(class_332 context, class_266 objective, CallbackInfo ci) {
/*  404 */     NoRender noRender = NoRender.getInstance();
/*  405 */     boolean hideByNoRender = (noRender != null && noRender.isEnabled() && noRender.scoreboard.isValue());
/*  406 */     if (ScoreboardHud.getInstance().isEnabled() || hideByNoRender) {
/*  407 */       ci.cancel();
/*      */     }
/*      */   }
/*      */   
/*      */   @Inject(method = {"method_55808"}, at = {@At("HEAD")}, cancellable = true, require = 1)
/*      */   private void phaze$suppressVanillaBossBar(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/*  413 */     NoRender noRender = NoRender.getInstance();
/*  414 */     if (noRender != null && noRender.isEnabled() && noRender.bossBar.isValue()) {
/*  415 */       ci.cancel();
/*      */     }
/*      */   }
/*      */   
/*      */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*      */   private void renderCustomHudFallback(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/*  421 */     class_310 client = class_310.method_1551();
/*  422 */     boolean hudHidden = (client == null || client.field_1690 == null || client.field_1690.field_1842);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     AnnouncementOverlay.render(context);
/*  428 */     KillStreakHud.getInstance().render(context);
/*      */     
/*  430 */     if (!renderedThisFrame) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  435 */       boolean hasLiveHudAnimation = (!hudHidden && phaze$prescanBlurStateFlips());
/*  436 */       if (!hudHidden && phaze$hasActiveHudBlurThisFrame) {
/*  437 */         context.method_51452();
/*  438 */         Blur.INSTANCE.captureBaseFrameForBlur();
/*      */       } 
/*      */       
/*  441 */       if (hudHidden || !hasAnyHudEnabled()) {
/*  442 */         BatchedHudBuffer.INSTANCE.invalidate();
/*  443 */       } else if (!HudOptimizer.getInstance().isEnabled()) {
/*  444 */         BatchedHudBuffer.INSTANCE.invalidate();
/*  445 */         renderHudInternal(context);
/*  446 */       } else if (phaze$shouldBypassHudBatchingForCurrentScreen(client)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  452 */         BatchedHudBuffer.INSTANCE.invalidate();
/*  453 */         renderHudInternal(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  470 */         int configuredRefreshRate = (HudOptimizer.getInstance()).refreshRate.getInt();
/*  471 */         boolean smoothGuiBackdrop = client.field_1755 instanceof vorga.phazeclient.implement.menu.MenuScreen;
/*      */ 
/*      */         
/*  474 */         int effectiveRefreshRate = (hasLiveHudAnimation || smoothGuiBackdrop) ? Math.max(60, configuredRefreshRate) : configuredRefreshRate;
/*  475 */         BatchedHudBuffer.INSTANCE.setTargetFps(effectiveRefreshRate);
/*  476 */         boolean chatEditing = client.field_1755 instanceof net.minecraft.class_408;
/*      */         
/*  478 */         boolean shouldRefresh = (chatEditing || BatchedHudBuffer.INSTANCE.shouldRefresh(false));
/*      */ 
/*      */ 
/*      */         
/*  482 */         if (shouldRefresh) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  504 */           context.method_51452();
/*  505 */           inBatchPass = true;
/*  506 */           batchIncludesBlur = true;
/*  507 */           BatchedHudBuffer.INSTANCE.beginCapture();
/*  508 */           renderHudInternal(context);
/*      */ 
/*      */           
/*  511 */           context.method_51452();
/*  512 */           BatchedHudBuffer.INSTANCE.endCapture();
/*  513 */           batchIncludesBlur = false;
/*  514 */           inBatchPass = false;
/*      */         } 
/*  516 */         BatchedHudBuffer.INSTANCE.blit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  522 */         if (hasLiveHudAnimation) {
/*  523 */           inBatchPass = true;
/*  524 */           inGradientPass = true;
/*  525 */           renderHudInternal(context);
/*  526 */           context.method_51452();
/*  527 */           inGradientPass = false;
/*  528 */           inBatchPass = false;
/*      */         } 
/*      */ 
/*      */         
/*  532 */         inLogicOnlyPass = true;
/*  533 */         renderHudInternal(context);
/*  534 */         inLogicOnlyPass = false;
/*      */       } 
/*      */     } 
/*  537 */     Blur.INSTANCE.endCachedFrame();
/*      */ 
/*      */     
/*  540 */     if (client != null && client.field_1690 != null && !client.field_1690.field_1842) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  551 */       float screenWidth = client.method_22683().method_4486();
/*  552 */       float screenHeight = client.method_22683().method_4502();
/*  553 */       renderZoomLevel(context, client, screenWidth, screenHeight);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  559 */     if (client != null && client.field_1755 instanceof net.minecraft.class_408) {
/*  560 */       ScaleSnapOverlay.render(context);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  567 */     context.method_51452();
/*  568 */     phaze$resetGuiRenderState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHudInternal(class_332 context) {
/*      */     float deltaSeconds;
/*  585 */     boolean firstCallThisFrame = !renderedThisFrame;
/*  586 */     boolean lastCallThisFrame = !inBatchPass;
/*  587 */     renderedThisFrame = true;
/*      */     
/*  589 */     class_310 client = class_310.method_1551();
/*  590 */     if (client == null || client.field_1690 == null || client.field_1690.field_1842) {
/*      */       return;
/*      */     }
/*      */     
/*  594 */     if (!hasAnyHudEnabled()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  599 */     if (firstCallThisFrame) {
/*  600 */       long now = System.nanoTime();
/*  601 */       if (lastFrameNanos < 0L) {
/*  602 */         lastFrameNanos = now;
/*      */       }
/*  604 */       deltaSeconds = class_3532.method_15363((float)(now - lastFrameNanos) / 1.0E9F, 0.0F, 0.1F);
/*  605 */       lastFrameNanos = now;
/*  606 */       cachedFrameDeltaSeconds = deltaSeconds;
/*      */     } else {
/*  608 */       deltaSeconds = cachedFrameDeltaSeconds;
/*      */     } 
/*      */     
/*  611 */     float guiScale = (float)client.method_22683().method_4495();
/*  612 */     if (guiScale <= 0.0F) {
/*  613 */       guiScale = 1.0F;
/*      */     }
/*  615 */     float inverseGuiScale = 1.0F / guiScale;
/*  616 */     float screenWidth = client.method_22683().method_4480();
/*  617 */     float screenHeight = client.method_22683().method_4507();
/*  618 */     if (screenWidth <= 2.0F || screenHeight <= 2.0F) {
/*      */       return;
/*      */     }
/*  621 */     float screenCenterX = screenWidth * 0.5F;
/*  622 */     float screenCenterY = screenHeight * 0.5F;
/*      */     
/*  624 */     boolean chatEditing = client.field_1755 instanceof net.minecraft.class_408;
/*  625 */     double mouseX = client.field_1729.method_1603();
/*  626 */     double mouseY = client.field_1729.method_1604();
/*  627 */     boolean mouseDown = (GLFW.glfwGetMouseButton(client.method_22683().method_4490(), 0) == 1);
/*  628 */     boolean rightMouseDown = (GLFW.glfwGetMouseButton(client.method_22683().method_4490(), 1) == 1);
/*  629 */     boolean gameplayInput = (client.field_1755 == null && client.field_1724 != null && client.field_1687 != null);
/*  630 */     if (firstCallThisFrame) {
/*  631 */       updateClicksPerSecond(mouseDown, rightMouseDown, gameplayInput);
/*  632 */       updateKeystrokeAnimations(client, mouseDown, rightMouseDown, deltaSeconds);
/*  633 */       showVerticalGuideThisFrame = false;
/*  634 */       showHorizontalGuideThisFrame = false;
/*  635 */       showHudVerticalGuideThisFrame = false;
/*  636 */       showHudHorizontalGuideThisFrame = false;
/*      */     } 
/*  638 */     if (inLogicOnlyPass) {
/*  639 */       renderLiveKeystrokeAnimation(context, client, inverseGuiScale);
/*  640 */       if (chatEditing) {
/*  641 */         verticalGuideProgress = approachExp(verticalGuideProgress, showVerticalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  642 */         horizontalGuideProgress = approachExp(horizontalGuideProgress, showHorizontalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  643 */         hudVerticalGuideProgress = approachExp(hudVerticalGuideProgress, showHudVerticalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  644 */         hudHorizontalGuideProgress = approachExp(hudHorizontalGuideProgress, showHudHorizontalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  645 */         renderHudGuides(context, screenWidth, screenHeight, inverseGuiScale);
/*      */       } else {
/*  647 */         verticalGuideProgress = 0.0F;
/*  648 */         horizontalGuideProgress = 0.0F;
/*  649 */         hudVerticalGuideProgress = 0.0F;
/*  650 */         hudHorizontalGuideProgress = 0.0F;
/*      */       } 
/*  652 */       wasMouseDown = mouseDown;
/*      */       
/*      */       return;
/*      */     } 
/*  656 */     String fpsText = getCachedHudText((RectHudModule)FpsHud.getInstance(), 0, chatEditing, () -> {
/*      */           int fps = client.method_47599();
/*      */           return (FpsHud.getInstance()).reverseOrder.isValue() ? ("" + fps + " FPS") : ("FPS: " + fps);
/*      */         });
/*  660 */     String fpsTextWrapped = wrapTextWithBrackets(fpsText, (RectHudModule)FpsHud.getInstance());
/*  661 */     renderBufferedHud(context, (RectHudModule)FpsHud.getInstance(), chatEditing, () -> renderRectHud(context, client, (RectHudModule)FpsHud.getInstance(), fpsTextWrapped, 0, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)FpsHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */     
/*  664 */     String cpsText = getCachedHudText((RectHudModule)CpsHud.getInstance(), 1, chatEditing, () -> {
/*      */           CpsHud cpsHud = CpsHud.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           int leftCps = LEFT_CLICKS.size();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           int rightCps = RIGHT_CLICKS.size();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return cpsHud.rightClickCps.isValue() ? (cpsHud.showCpsText.isValue() ? (cpsHud.reverseText.isValue() ? ("" + leftCps + " | " + leftCps + " CPS") : ("CPS: " + leftCps + " | " + rightCps)) : ("" + leftCps + " | " + leftCps)) : (cpsHud.showCpsText.isValue() ? (cpsHud.reverseText.isValue() ? ("" + leftCps + " CPS") : ("CPS: " + leftCps)) : String.valueOf(leftCps));
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  693 */     String cpsTextWrapped = wrapTextWithBrackets(cpsText, (RectHudModule)CpsHud.getInstance());
/*  694 */     renderBufferedHud(context, (RectHudModule)CpsHud.getInstance(), chatEditing, () -> renderRectHud(context, client, (RectHudModule)CpsHud.getInstance(), cpsTextWrapped, 1, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)CpsHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */     
/*  697 */     String reachText = getCachedHudText((RectHudModule)ReachHud.getInstance(), 2, chatEditing, () -> ReachHud.getInstance().getFormattedReach());
/*  698 */     String reachTextWrapped = wrapTextWithBrackets(reachText, (RectHudModule)ReachHud.getInstance());
/*  699 */     renderBufferedHud(context, (RectHudModule)ReachHud.getInstance(), chatEditing, () -> renderRectHud(context, client, (RectHudModule)ReachHud.getInstance(), reachTextWrapped, 2, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)ReachHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, 66.0F, 20.0F));
/*      */ 
/*      */ 
/*      */     
/*  703 */     renderBufferedHud(context, ArmorHud.getInstance(), chatEditing, () -> renderArmorHud(context, client, ArmorHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, getHudDelta(ArmorHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  711 */     renderBufferedHud(context, (RectHudModule)InventoryHud.getInstance(), chatEditing, () -> renderInventoryHud(context, client, InventoryHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)InventoryHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  715 */     String sprintText = getCachedHudText((RectHudModule)SprintHud.getInstance(), 3, chatEditing, () -> getSprintHudText(client));
/*  716 */     String sprintTextWrapped = wrapTextWithBrackets(sprintText, (RectHudModule)SprintHud.getInstance());
/*  717 */     renderBufferedHud(context, (RectHudModule)SprintHud.getInstance(), chatEditing, () -> renderRectHud(context, client, (RectHudModule)SprintHud.getInstance(), sprintTextWrapped, 3, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)SprintHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, getTextHudBaseWidth(client, sprintTextWrapped), 20.0F));
/*      */ 
/*      */ 
/*      */     
/*  721 */     renderBufferedHud(context, (RectHudModule)CoordinatesHud.getInstance(), chatEditing, () -> renderCoordinatesHud(context, client, CoordinatesHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)CoordinatesHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */     
/*  723 */     renderBufferedHud(context, (RectHudModule)PingHud.getInstance(), chatEditing, () -> renderPingHud(context, client, PingHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)PingHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */     
/*  725 */     renderBufferedHud(context, (RectHudModule)KeystrokesHud.getInstance(), chatEditing, () -> renderKeystrokesHud(context, client, KeystrokesHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)KeystrokesHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */     
/*  727 */     renderBufferedHud(context, (RectHudModule)PotionHud.getInstance(), chatEditing, () -> renderPotionHud(context, client, PotionHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)PotionHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */     
/*  729 */     String dayText = getCachedHudText((RectHudModule)DayCounterHud.getInstance(), 8, chatEditing, () -> getDayCounterText(client));
/*  730 */     String dayTextWrapped = wrapTextWithBrackets(dayText, (RectHudModule)DayCounterHud.getInstance());
/*  731 */     renderBufferedHud(context, (RectHudModule)DayCounterHud.getInstance(), chatEditing, () -> renderRectHud(context, client, (RectHudModule)DayCounterHud.getInstance(), dayTextWrapped, 8, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)DayCounterHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, getTextHudBaseWidth(client, dayTextWrapped), 20.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  737 */     String timeText = getTimeHudText(TimeHud.getInstance(), client);
/*  738 */     String timeTextWrapped = wrapTextWithBrackets(timeText, (RectHudModule)TimeHud.getInstance());
/*  739 */     renderBufferedHud(context, (RectHudModule)TimeHud.getInstance(), chatEditing, () -> renderSimpleTextHud(context, client, (RectHudModule)TimeHud.getInstance(), timeTextWrapped, 12, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)TimeHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */     
/*  742 */     String sessionText = getSessionText(SessionTimeHud.getInstance());
/*  743 */     String sessionTextWrapped = wrapTextWithBrackets(sessionText, (RectHudModule)SessionTimeHud.getInstance());
/*  744 */     renderBufferedHud(context, (RectHudModule)SessionTimeHud.getInstance(), chatEditing, () -> renderSessionTimeHud(context, client, (RectHudModule)SessionTimeHud.getInstance(), sessionTextWrapped, 13, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)SessionTimeHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  748 */     String memoryText = MemoryHud.getInstance().getMemoryText();
/*  749 */     String memoryTextWrapped = wrapTextWithBrackets(memoryText, (RectHudModule)MemoryHud.getInstance());
/*  750 */     renderBufferedHud(context, (RectHudModule)MemoryHud.getInstance(), chatEditing, () -> renderMemoryHud(context, client, MemoryHud.getInstance(), memoryTextWrapped, 15, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)MemoryHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  754 */     String tpsText = TpsHud.getInstance().getFormattedText();
/*  755 */     String tpsTextWrapped = wrapTextWithBrackets(tpsText, (RectHudModule)TpsHud.getInstance());
/*  756 */     renderBufferedHud(context, (RectHudModule)TpsHud.getInstance(), chatEditing, () -> renderTpsHud(context, client, TpsHud.getInstance(), tpsTextWrapped, 23, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)TpsHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  760 */     String comboText = ComboCounterHud.getInstance().getComboText();
/*  761 */     String comboTextWrapped = wrapTextWithBrackets(comboText, (RectHudModule)ComboCounterHud.getInstance());
/*  762 */     renderBufferedHud(context, (RectHudModule)ComboCounterHud.getInstance(), chatEditing, () -> renderComboHud(context, client, ComboCounterHud.getInstance(), comboTextWrapped, 16, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)ComboCounterHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  766 */     String serverAddressText = ServerAddressHud.getInstance().getServerAddress();
/*  767 */     String serverAddressTextWrapped = wrapTextWithBrackets(serverAddressText, (RectHudModule)ServerAddressHud.getInstance());
/*  768 */     renderBufferedHud(context, (RectHudModule)ServerAddressHud.getInstance(), chatEditing, () -> renderServerAddressHud(context, client, ServerAddressHud.getInstance(), serverAddressTextWrapped, 17, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)ServerAddressHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  772 */     TrapTimer trapTimer = TrapTimer.getInstance();
/*  773 */     renderBufferedHud(context, (RectHudModule)trapTimer, chatEditing, () -> {
/*      */           class_2561 trapText = trapTimer.getDisplayText(chatEditing);
/*      */           
/*      */           if (trapText == null || trapText.getString().isEmpty()) {
/*      */             return;
/*      */           }
/*      */           
/*      */           renderTrapTimerHud(context, client, trapTimer, trapText, 25, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)trapTimer, chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY);
/*      */         });
/*      */     
/*  783 */     String speedText = getCachedHudText((RectHudModule)MovementSpeedHud.getInstance(), 18, chatEditing, () -> {
/*      */           if (client.field_1724 == null) {
/*      */             return "0.00 m/s";
/*      */           }
/*      */           
/*      */           MovementSpeedHud module = MovementSpeedHud.getInstance();
/*      */           
/*      */           double speed = Math.sqrt(client.field_1724.method_5707(new class_243(client.field_1724.field_6014, client.field_1724.field_6036, client.field_1724.field_5969))) * 20.0D;
/*      */           
/*      */           if (module.onlyUseGroundSpeed.isValue()) {
/*      */             double dx = client.field_1724.method_23317() - client.field_1724.field_6014;
/*      */             
/*      */             double dz = client.field_1724.method_23321() - client.field_1724.field_5969;
/*      */             
/*      */             speed = Math.sqrt(dx * dx + dz * dz) * 20.0D;
/*      */           } 
/*      */           
/*      */           if (speed < 0.01D) {
/*      */             speed = 0.0D;
/*      */           }
/*      */           
/*      */           String value = module.getSpeedText(speed) + " m/s";
/*      */           return module.reverseOrder.isValue() ? ("Speed: " + value) : value;
/*      */         });
/*  807 */     String speedTextWrapped = wrapTextWithBrackets(speedText, (RectHudModule)MovementSpeedHud.getInstance());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  812 */     renderBufferedHud(context, (RectHudModule)MovementSpeedHud.getInstance(), chatEditing, () -> renderRectHud(context, client, (RectHudModule)MovementSpeedHud.getInstance(), speedTextWrapped, 18, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)MovementSpeedHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, getTextHudBaseWidth(client, speedTextWrapped), 20.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  817 */     class_1799 wailaIcon = getWailaIcon(client);
/*  818 */     String wailaText = getCachedHudText((RectHudModule)WailaHud.getInstance(), 19, chatEditing, () -> (client.field_1724 == null) ? "" : getWailaText(client));
/*      */ 
/*      */ 
/*      */     
/*  822 */     String wailaTextWrapped = wrapTextWithBrackets(wailaText, (RectHudModule)WailaHud.getInstance());
/*  823 */     renderBufferedHud(context, (RectHudModule)WailaHud.getInstance(), chatEditing, () -> renderWailaHud(context, client, WailaHud.getInstance(), wailaTextWrapped, wailaIcon, 19, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)WailaHud.getInstance(), chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  835 */     HealthIndicator healthIndicator = HealthIndicator.getInstance();
/*  836 */     renderBufferedHud(context, (RectHudModule)healthIndicator, chatEditing, () -> {
/*      */           String hpText;
/*      */ 
/*      */           
/*      */           if (chatEditing) {
/*      */             hpText = healthIndicator.getPlaceholderText();
/*      */           } else {
/*      */             hpText = healthIndicator.getDisplayText();
/*      */ 
/*      */             
/*      */             if (hpText.isEmpty()) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */           
/*      */           String wrappedHpText = wrapTextWithBrackets(hpText, (RectHudModule)healthIndicator);
/*      */           
/*      */           renderRectHud(context, client, (RectHudModule)healthIndicator, wrappedHpText, 20, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)healthIndicator, chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, getTextHudBaseWidth(client, wrappedHpText), 20.0F);
/*      */         });
/*      */     
/*  856 */     PackDisplayHud packDisplay = PackDisplayHud.getInstance();
/*  857 */     renderBufferedHud(context, (RectHudModule)packDisplay, chatEditing, () -> {
/*      */           class_2960 packIcon = packDisplay.getPackIcon(client);
/*      */ 
/*      */           
/*      */           String packText = packDisplay.getDisplayText(client);
/*      */ 
/*      */           
/*      */           if (packText.isEmpty()) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*      */           renderRectHud(context, client, (RectHudModule)packDisplay, packText, 27, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)packDisplay, chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, Math.max(60.0F, packDisplay.getPanelBaseWidth(client)), 28.0F);
/*      */ 
/*      */           
/*      */           if (packIcon != null) {
/*      */             context.method_51448().method_22903();
/*      */ 
/*      */             
/*      */             context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/*      */ 
/*      */             
/*      */             context.method_51448().method_46416(packDisplay.getHudX(), packDisplay.getHudY(), 1001.0F);
/*      */ 
/*      */             
/*      */             context.method_51448().method_22905(packDisplay.getRenderHudScale(), packDisplay.getRenderHudScale(), 1.0F);
/*      */ 
/*      */             
/*      */             packDisplay.drawPackIcon(context);
/*      */ 
/*      */             
/*      */             context.method_51448().method_22909();
/*      */           } 
/*      */         });
/*      */ 
/*      */     
/*  893 */     Consumable consumable = Consumable.getInstance();
/*  894 */     renderBufferedHud(context, (RectHudModule)consumable, chatEditing, () -> renderConsumableHud(context, client, consumable, 22, chatEditing, mouseX, mouseY, mouseDown, getHudDelta((RectHudModule)consumable, chatEditing, deltaSeconds), inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     renderBufferedHud(context, (RectHudModule)ScoreboardHud.getInstance(), chatEditing, () -> renderScoreboardHud(context, client, ScoreboardHud.getInstance(), chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY));
/*      */ 
/*      */ 
/*      */     
/*  903 */     if (chatEditing) {
/*  904 */       BossBarHud bossBar = BossBarHud.getInstance();
/*  905 */       renderBufferedHud(context, (RectHudModule)bossBar, true, () -> renderRectHud(context, client, (RectHudModule)bossBar, "", 28, true, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, bossBar.getBaseWidth(), bossBar.getBaseHeight()));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  911 */     if (lastCallThisFrame) {
/*  912 */       if (chatEditing) {
/*  913 */         verticalGuideProgress = approachExp(verticalGuideProgress, showVerticalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  914 */         horizontalGuideProgress = approachExp(horizontalGuideProgress, showHorizontalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  915 */         hudVerticalGuideProgress = approachExp(hudVerticalGuideProgress, showHudVerticalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  916 */         hudHorizontalGuideProgress = approachExp(hudHorizontalGuideProgress, showHudHorizontalGuideThisFrame ? 1.0F : 0.0F, 14.0F, deltaSeconds);
/*  917 */         renderHudGuides(context, screenWidth, screenHeight, inverseGuiScale);
/*      */       } else {
/*  919 */         verticalGuideProgress = 0.0F;
/*  920 */         horizontalGuideProgress = 0.0F;
/*  921 */         hudVerticalGuideProgress = 0.0F;
/*  922 */         hudHorizontalGuideProgress = 0.0F;
/*      */       } 
/*  924 */       wasMouseDown = mouseDown;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderBufferedHud(class_332 context, RectHudModule module, boolean chatEditing, Runnable renderLogic) {
/*  929 */     boolean liveBackground = (module.hasActiveBackgroundBlur() || module.hasActiveAnimatedBackground());
/*  930 */     phaze$trackBlurStateChange(module, liveBackground);
/*  931 */     phaze$trackGradientStateChange(module, module.hasActiveAnimatedBackground());
/*  932 */     if (inGradientPass) {
/*  933 */       if (module.hasActiveAnimatedBackground()) {
/*  934 */         renderBufferedHudInternal(context, module.isEnabled(), false, module.getHudBuffer(), 60, chatEditing, renderLogic);
/*      */       }
/*      */       return;
/*      */     } 
/*  938 */     if (inBatchPass && module.hasActiveAnimatedBackground()) {
/*      */       return;
/*      */     }
/*  941 */     if (shouldSkipForCurrentPass(liveBackground))
/*  942 */       return;  renderBufferedHudInternal(context, module.isEnabled(), false, module.getHudBuffer(), 60, chatEditing, renderLogic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void phaze$trackBlurStateChange(Object module, boolean current) {
/*  959 */     Boolean prev = PHAZE_LAST_BLUR_STATE.put(module, Boolean.valueOf(current));
/*  960 */     if (prev != null && prev.booleanValue() != current) {
/*  961 */       BatchedHudBuffer.INSTANCE.invalidate();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void phaze$trackGradientStateChange(Object module, boolean current) {
/*  966 */     Boolean previous = PHAZE_LAST_GRADIENT_STATE.put(module, Boolean.valueOf(current));
/*  967 */     if (previous != null && previous.booleanValue() != current) {
/*  968 */       BatchedHudBuffer.INSTANCE.invalidate();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean phaze$prescanBlurStateFlips() {
/*      */     int i, j;
/*  999 */     Main main = Main.getInstance();
/* 1000 */     if (main == null) {
/* 1001 */       phaze$hasActiveHudBlurThisFrame = false;
/* 1002 */       return false;
/*      */     } 
/* 1004 */     ModuleProvider provider = main.getModuleProvider();
/* 1005 */     if (provider == null) {
/* 1006 */       phaze$hasActiveHudBlurThisFrame = false;
/* 1007 */       return false;
/*      */     } 
/* 1009 */     boolean anyLiveBackground = false;
/* 1010 */     boolean anyActualBlur = false;
/* 1011 */     for (Module module : provider.getModules()) {
/*      */       boolean current, animatedGradient;
/*      */       
/* 1014 */       if (module instanceof RectHudModule) { RectHudModule rectModule = (RectHudModule)module;
/* 1015 */         animatedGradient = rectModule.hasActiveAnimatedBackground();
/* 1016 */         current = (rectModule.hasActiveBackgroundBlur() || animatedGradient);
/* 1017 */         j = anyActualBlur | ((module.isEnabled() && rectModule.hasActiveBackgroundBlur()) ? 1 : 0); }
/* 1018 */       else if (module instanceof ArmorHud) { ArmorHud armorModule = (ArmorHud)module;
/* 1019 */         animatedGradient = armorModule.hasActiveAnimatedBackground();
/* 1020 */         current = (armorModule.hasActiveBackgroundBlur() || animatedGradient);
/* 1021 */         j |= (module.isEnabled() && armorModule.hasActiveBackgroundBlur()) ? 1 : 0; }
/*      */       else
/*      */       { continue; }
/*      */       
/* 1025 */       i = anyLiveBackground | ((module.isEnabled() && current) ? 1 : 0);
/* 1026 */       Boolean prev = PHAZE_LAST_BLUR_STATE.put(module, Boolean.valueOf(current));
/* 1027 */       if (prev != null && prev.booleanValue() != current) {
/* 1028 */         BatchedHudBuffer.INSTANCE.invalidate();
/*      */       }
/* 1030 */       Boolean previousGradient = PHAZE_LAST_GRADIENT_STATE.put(module, Boolean.valueOf(animatedGradient));
/* 1031 */       if (previousGradient != null && previousGradient.booleanValue() != animatedGradient) {
/* 1032 */         BatchedHudBuffer.INSTANCE.invalidate();
/*      */       }
/*      */     } 
/* 1035 */     phaze$hasActiveHudBlurThisFrame = j;
/* 1036 */     return i;
/*      */   }
/*      */   
/*      */   private void renderBufferedHud(class_332 context, ArmorHud module, boolean chatEditing, Runnable renderLogic) {
/* 1040 */     boolean liveBackground = (module.hasActiveBackgroundBlur() || module.hasActiveAnimatedBackground());
/* 1041 */     phaze$trackBlurStateChange(module, liveBackground);
/* 1042 */     phaze$trackGradientStateChange(module, module.hasActiveAnimatedBackground());
/* 1043 */     if (inGradientPass) {
/* 1044 */       if (module.hasActiveAnimatedBackground()) {
/* 1045 */         renderBufferedHudInternal(context, module.isEnabled(), false, module.getHudBuffer(), 60, chatEditing, renderLogic);
/*      */       }
/*      */       return;
/*      */     } 
/* 1049 */     if (inBatchPass && module.hasActiveAnimatedBackground()) {
/*      */       return;
/*      */     }
/* 1052 */     if (shouldSkipForCurrentPass(liveBackground))
/* 1053 */       return;  renderBufferedHudInternal(context, module.isEnabled(), false, module.getHudBuffer(), 60, chatEditing, renderLogic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean shouldSkipForCurrentPass(boolean hasBlur) {
/* 1062 */     if (inLogicOnlyPass) return true; 
/* 1063 */     if (inBatchPass && hasBlur && !batchIncludesBlur) return true; 
/* 1064 */     return false;
/*      */   }
/*      */   
/*      */   private float getHudDelta(RectHudModule module, boolean chatEditing, float deltaSeconds) {
/* 1068 */     return deltaSeconds;
/*      */   }
/*      */   
/*      */   private float getHudDelta(ArmorHud module, boolean chatEditing, float deltaSeconds) {
/* 1072 */     return deltaSeconds;
/*      */   }
/*      */   
/*      */   private void renderBufferedHudInternal(class_332 context, boolean enabled, boolean batching, HudBuffer buffer, int targetFps, boolean chatEditing, Runnable renderLogic) {
/* 1076 */     if (!enabled) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1082 */     renderLogic.run();
/*      */   }
/*      */   
/*      */   private String getCachedHudText(RectHudModule module, int hudIndex, boolean chatEditing, Supplier<String> supplier) {
/* 1086 */     long now = System.currentTimeMillis();
/* 1087 */     boolean throttledDue = (now - HUD_TEXT_CACHE_TIME_MS[hudIndex] >= 50L);
/* 1088 */     if (HUD_TEXT_CACHE[hudIndex] == null || throttledDue) {
/* 1089 */       HUD_TEXT_CACHE[hudIndex] = supplier.get();
/* 1090 */       HUD_TEXT_CACHE_TIME_MS[hudIndex] = now;
/*      */     } 
/* 1092 */     return HUD_TEXT_CACHE[hudIndex];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderRectHud(class_332 context, class_310 client, RectHudModule module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 1112 */     renderRectHud(context, client, module, text, hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, 60.0F, 20.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderRectHud(class_332 context, class_310 client, RectHudModule module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY, float baseWidth, float baseHeight) {
/* 1135 */     if (!module.isEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 1139 */     module.setHudScale(class_3532.method_15363(module.getHudScale(), module.getMinHudScale(), module.getMaxHudScale()));
/* 1140 */     float scale = module.getRenderHudScale();
/*      */     
/* 1142 */     float hudWidth = baseWidth * scale;
/* 1143 */     float hudHeight = baseHeight * scale;
/* 1144 */     float maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 1145 */     float maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 1146 */     int currentScreenWidth = Math.round(screenWidth);
/* 1147 */     int currentScreenHeight = Math.round(screenHeight);
/* 1148 */     float x = module.getHudX();
/* 1149 */     float y = module.getHudY();
/* 1150 */     boolean resized = (RECT_LAYOUT_INITIALIZED[hudIndex] && (RECT_LAST_SCREEN_WIDTH[hudIndex] != currentScreenWidth || RECT_LAST_SCREEN_HEIGHT[hudIndex] != currentScreenHeight));
/*      */ 
/*      */     
/* 1153 */     if (resized && !RECT_DRAGGING[hudIndex] && !RECT_RESIZING[hudIndex]) {
/* 1154 */       x = (maxX <= 0.0F) ? 0.0F : (RECT_POSITION_RATIO_X[hudIndex] * maxX);
/* 1155 */       y = (maxY <= 0.0F) ? 0.0F : (RECT_POSITION_RATIO_Y[hudIndex] * maxY);
/*      */     } 
/* 1157 */     x = class_3532.method_15363(x, 0.0F, maxX);
/* 1158 */     y = class_3532.method_15363(y, 0.0F, maxY);
/* 1159 */     module.setHudX(x);
/* 1160 */     module.setHudY(y);
/* 1161 */     RECT_POSITION_RATIO_X[hudIndex] = (maxX <= 0.0F) ? 0.0F : (x / maxX);
/* 1162 */     RECT_POSITION_RATIO_Y[hudIndex] = (maxY <= 0.0F) ? 0.0F : (y / maxY);
/* 1163 */     RECT_LAST_SCREEN_WIDTH[hudIndex] = currentScreenWidth;
/* 1164 */     RECT_LAST_SCREEN_HEIGHT[hudIndex] = currentScreenHeight;
/* 1165 */     RECT_LAYOUT_INITIALIZED[hudIndex] = true;
/*      */     
/* 1167 */     int handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/*      */ 
/*      */     
/* 1170 */     float handleX = x + hudWidth - handleSize / 2.0F;
/* 1171 */     float handleY = y + hudHeight - handleSize / 2.0F;
/*      */     
/* 1173 */     boolean hoveredHud = false;
/* 1174 */     boolean hoveredHandle = false;
/* 1175 */     boolean nearHud = false;
/*      */     
/* 1177 */     if (!chatEditing) {
/* 1178 */       RECT_DRAGGING[hudIndex] = false;
/* 1179 */       RECT_RESIZING[hudIndex] = false;
/* 1180 */       RECT_HOVER_PROGRESS[hudIndex] = approachExp(RECT_HOVER_PROGRESS[hudIndex], 0.0F, 10.0F, deltaSeconds);
/*      */     } else {
/* 1182 */       hoveredHud = isHovered(mouseX, mouseY, x, y, hudWidth, hudHeight);
/* 1183 */       hoveredHandle = isHovered(mouseX, mouseY, handleX, handleY, handleSize, handleSize);
/* 1184 */       nearHud = isNearRect(mouseX, mouseY, x, y, hudWidth, hudHeight, Math.max(14.0F, 12.0F * scale));
/* 1185 */       if (!mouseDown) {
/* 1186 */         RECT_DRAGGING[hudIndex] = false;
/* 1187 */         RECT_RESIZING[hudIndex] = false;
/* 1188 */       } else if (!wasMouseDown && !isAnyHudInteractionActive()) {
/* 1189 */         if (hoveredHandle) {
/* 1190 */           RECT_RESIZING[hudIndex] = true;
/* 1191 */           RECT_RESIZE_START_WIDTH[hudIndex] = hudWidth;
/* 1192 */           RECT_RESIZE_START_MOUSE_X[hudIndex] = (float)mouseX;
/* 1193 */           RECT_RESIZE_START_MOUSE_Y[hudIndex] = (float)mouseY;
/* 1194 */         } else if (hoveredHud) {
/* 1195 */           RECT_DRAGGING[hudIndex] = true;
/* 1196 */           RECT_DRAG_OFFSET_X[hudIndex] = (float)mouseX - x;
/* 1197 */           RECT_DRAG_OFFSET_Y[hudIndex] = (float)mouseY - y;
/*      */         } 
/*      */       } 
/*      */       
/* 1201 */       if (mouseDown) {
/* 1202 */         if (RECT_DRAGGING[hudIndex]) {
/* 1203 */           float newX = class_3532.method_15363((float)mouseX - RECT_DRAG_OFFSET_X[hudIndex], 0.0F, maxX);
/* 1204 */           float newY = class_3532.method_15363((float)mouseY - RECT_DRAG_OFFSET_Y[hudIndex], 0.0F, maxY);
/* 1205 */           float centerX = newX + hudWidth * 0.5F;
/* 1206 */           float centerY = newY + hudHeight * 0.5F;
/* 1207 */           boolean snappedToScreenX = false;
/* 1208 */           boolean snappedToScreenY = false;
/*      */           
/* 1210 */           if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 1211 */             newX = class_3532.method_15363(screenCenterX - hudWidth * 0.5F, 0.0F, maxX);
/* 1212 */             showVerticalGuideThisFrame = true;
/* 1213 */             snappedToScreenX = true;
/*      */           } 
/* 1215 */           if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 1216 */             newY = class_3532.method_15363(screenCenterY - hudHeight * 0.5F, 0.0F, maxY);
/* 1217 */             showHorizontalGuideThisFrame = true;
/* 1218 */             snappedToScreenY = true;
/*      */           } 
/* 1220 */           if (!snappedToScreenX) {
/* 1221 */             newX = snapHudAlignmentX(hudIndex, newX, newY, hudWidth, hudHeight, maxX, 1.0F);
/*      */           }
/* 1223 */           if (!snappedToScreenY) {
/* 1224 */             newY = snapHudAlignmentY(hudIndex, newX, newY, hudWidth, hudHeight, maxY, 1.0F);
/*      */           }
/*      */           
/* 1227 */           module.setHudX(newX);
/* 1228 */           module.setHudY(newY);
/* 1229 */           x = newX;
/* 1230 */           y = newY;
/* 1231 */           RECT_POSITION_RATIO_X[hudIndex] = (maxX <= 0.0F) ? 0.0F : (x / maxX);
/* 1232 */           RECT_POSITION_RATIO_Y[hudIndex] = (maxY <= 0.0F) ? 0.0F : (y / maxY);
/* 1233 */           handleX = x + hudWidth - handleSize / 2.0F;
/* 1234 */           handleY = y + hudHeight - handleSize / 2.0F;
/* 1235 */         } else if (RECT_RESIZING[hudIndex]) {
/* 1236 */           float deltaX = (float)mouseX - RECT_RESIZE_START_MOUSE_X[hudIndex];
/* 1237 */           float deltaY = (float)mouseY - RECT_RESIZE_START_MOUSE_Y[hudIndex];
/* 1238 */           float delta = (deltaX + deltaY) * 0.5F;
/*      */           
/* 1240 */           float scaleBaseline = scale / module.getHudScale();
/* 1241 */           float minWidth = baseWidth * module.getMinHudScale() * scaleBaseline;
/* 1242 */           float maxWidth = baseWidth * module.getMaxHudScale() * scaleBaseline;
/* 1243 */           float newWidth = class_3532.method_15363(RECT_RESIZE_START_WIDTH[hudIndex] + delta * 0.9F, minWidth, maxWidth);
/* 1244 */           float newScale = snapAndAnnounceHudScale((Module)module, newWidth / baseWidth / scaleBaseline, module
/*      */               
/* 1246 */               .getMinHudScale(), module.getMaxHudScale());
/*      */           
/* 1248 */           module.setHudScale(newScale);
/* 1249 */           scale = module.getRenderHudScale();
/* 1250 */           hudWidth = baseWidth * scale;
/* 1251 */           hudHeight = baseHeight * scale;
/* 1252 */           maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 1253 */           maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 1254 */           x = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 1255 */           y = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 1256 */           module.setHudX(x);
/* 1257 */           module.setHudY(y);
/* 1258 */           RECT_POSITION_RATIO_X[hudIndex] = (maxX <= 0.0F) ? 0.0F : (x / maxX);
/* 1259 */           RECT_POSITION_RATIO_Y[hudIndex] = (maxY <= 0.0F) ? 0.0F : (y / maxY);
/* 1260 */           handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 1261 */           handleX = x + hudWidth - handleSize / 2.0F;
/* 1262 */           handleY = y + hudHeight - handleSize / 2.0F;
/*      */         } 
/*      */       }
/*      */       
/* 1266 */       if (RECT_DRAGGING[hudIndex]) {
/* 1267 */         float centerX = x + hudWidth * 0.5F;
/* 1268 */         float centerY = y + hudHeight * 0.5F;
/* 1269 */         if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 1270 */           showVerticalGuideThisFrame = true;
/*      */         }
/* 1272 */         if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 1273 */           showHorizontalGuideThisFrame = true;
/*      */         }
/*      */       } 
/*      */       
/* 1277 */       RECT_HOVER_PROGRESS[hudIndex] = approachExp(RECT_HOVER_PROGRESS[hudIndex], hoveredHud ? 1.0F : 0.0F, 10.0F, deltaSeconds);
/*      */     } 
/*      */     
/* 1280 */     rememberHudSnapBounds(hudIndex, (Module)module, x, y, hudWidth, hudHeight);
/*      */     
/* 1282 */     if (chatEditing) {
/* 1283 */       if (RECT_RESIZING[hudIndex]) {
/* 1284 */         HudCursorRelay.defer(221189, 4);
/* 1285 */       } else if (RECT_DRAGGING[hudIndex]) {
/* 1286 */         HudCursorRelay.defer(221193, 3);
/*      */       } 
/*      */     }
/*      */     
/* 1290 */     if (hudIndex == 28 && chatEditing && !BossBarHud.getInstance().hasLiveBars()) {
/* 1291 */       ((BossBarHudAccess)client.field_1705.method_1740()).phaze$renderPreview(context);
/*      */     }
/*      */     
/* 1294 */     float textY = (baseHeight - 8.0F) / 2.0F;
/* 1295 */     int hoverOutlineThickness = Math.max(1, Math.round(1.0F / Math.max(1.0F, scale)));
/*      */     
/* 1297 */     context.method_51448().method_22903();
/* 1298 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/*      */     
/* 1300 */     context.method_51448().method_22903();
/* 1301 */     context.method_51448().method_46416(x, y, 400.0F);
/* 1302 */     context.method_51448().method_22905(scale, scale, 1.0F);
/*      */     
/* 1304 */     if (module.background.isValue() && hudIndex != 6) {
/* 1305 */       float blurRadius = Math.max(0.0F, module.backgroundBlurRadius.getValue());
/* 1306 */       if (blurRadius > 0.0F) {
/* 1307 */         float safeScale = Math.max(scale, 1.0F);
/* 1308 */         float normalizedBlurRadius = blurRadius / safeScale;
/* 1309 */         float blurX = 0.0F;
/* 1310 */         float blurY = 0.0F;
/* 1311 */         float blurWidth = baseWidth;
/* 1312 */         float blurHeight = baseHeight;
/* 1313 */         float blurQuality = getOptimizedHudBlurQuality(normalizedBlurRadius);
/* 1314 */         long blurStateKey = makeHudBlurStateKey(normalizedBlurRadius, safeScale, blurX, blurY, blurWidth, blurHeight);
/* 1315 */         Blur.INSTANCE.registerHudBlurState(hudIndex, blurStateKey);
/*      */         
/* 1317 */         if (blurQuality > 0.0F && blurWidth > 1.5F && blurHeight > 1.5F) {
/* 1318 */           Blur.INSTANCE.renderCached(ShapeProperties.create(context.method_51448(), blurX, blurY, blurWidth, blurHeight)
/* 1319 */               .round(getClampedHudCornerRadius(module.cornerRounding.getValue(), blurWidth, blurHeight))
/* 1320 */               .softness(0.0F)
/* 1321 */               .quality(blurQuality)
/* 1322 */               .color(-1)
/* 1323 */               .build());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1328 */     if (module.background.isValue()) {
/* 1329 */       if (module.isGradientPreset()) {
/* 1330 */         int hoverFill = chatEditing ? withAlpha(16777215, (int)(30.0F * RECT_HOVER_PROGRESS[hudIndex])) : 0;
/* 1331 */         if (hudIndex != 6) {
/* 1332 */           renderGradientRect(context, 0.0F, 0.0F, baseWidth, baseHeight, hoverFill, module
/* 1333 */               .getResolvedGradientStartColor(), module.getResolvedGradientEndColor(), module
/* 1334 */               .getGradientDirection(), module.getGradientAnimationOffset(System.currentTimeMillis()), module
/* 1335 */               .hasActiveAnimatedBackground(), module.cornerRounding.getValue());
/* 1336 */           context.method_51452();
/*      */         } 
/*      */       } else {
/* 1339 */         int targetBgColor = module.getResolvedBackgroundColor(client);
/* 1340 */         if (!RECT_BG_COLOR_INITIALIZED[hudIndex]) {
/* 1341 */           RECT_BG_ANIMATED_COLOR[hudIndex] = targetBgColor;
/* 1342 */           RECT_BG_COLOR_INITIALIZED[hudIndex] = true;
/*      */         } else {
/* 1344 */           RECT_BG_ANIMATED_COLOR[hudIndex] = approachColorExp(RECT_BG_ANIMATED_COLOR[hudIndex], targetBgColor, 12.0F, deltaSeconds);
/*      */         } 
/*      */         
/* 1347 */         int bgColor = RECT_BG_ANIMATED_COLOR[hudIndex];
/* 1348 */         if (chatEditing) {
/* 1349 */           int hoverFill = withAlpha(16777215, (int)(30.0F * RECT_HOVER_PROGRESS[hudIndex]));
/* 1350 */           bgColor = blendARGB(bgColor, hoverFill);
/*      */         } 
/* 1352 */         if (hudIndex != 6) {
/* 1353 */           renderSolidHudBackground(context, 0.0F, 0.0F, baseWidth, baseHeight, bgColor, module.cornerRounding
/* 1354 */               .getValue(), (module.backgroundBlurRadius.getValue() <= 0.0F));
/* 1355 */           context.method_51452();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1360 */     context.method_51448().method_22909();
/*      */ 
/*      */     
/* 1363 */     int textColor = resolveHudTextColor();
/* 1364 */     if (module instanceof MemoryHud) {
/* 1365 */       MemoryHud memoryHud = (MemoryHud)module;
/* 1366 */       int memoryColor = memoryHud.getMemoryColor();
/* 1367 */       if (memoryColor != -1) {
/* 1368 */         textColor = memoryColor;
/*      */       }
/* 1370 */     } else if (module instanceof HealthIndicator) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1377 */       int healthColor = ((HealthIndicator)module).getCurrentHpColor();
/* 1378 */       if (healthColor != -1) {
/* 1379 */         textColor = healthColor;
/*      */       }
/* 1381 */     } else if (module instanceof TpsHud) {
/*      */ 
/*      */       
/* 1384 */       int tpsColor = ((TpsHud)module).getColor();
/* 1385 */       if (tpsColor != -1) {
/* 1386 */         textColor = tpsColor;
/*      */       }
/*      */     } 
/*      */     
/* 1390 */     if (module instanceof PackDisplayHud) { PackDisplayHud packDisplay = (PackDisplayHud)module;
/* 1391 */       renderPackDisplayText(context, client, packDisplay, x, y, baseHeight, scale, textColor); }
/* 1392 */     else if (hudIndex != 28)
/* 1393 */     { float textX = (baseWidth - getHudTextWidth(client, text, 8.0F)) / 2.0F;
/* 1394 */       renderScaledHudTextColored(context, client, text, x, y, textX, textY, 8.0F, scale, module.textShadow.isValue(), textColor); }
/*      */ 
/*      */     
/* 1397 */     if (chatEditing && RECT_HOVER_PROGRESS[hudIndex] > 0.05F) {
/* 1398 */       int borderColor = withAlpha(16777215, (int)(175.0F * RECT_HOVER_PROGRESS[hudIndex]));
/* 1399 */       drawOuterOutline(context, x, y, hudWidth, hudHeight, hoverOutlineThickness, borderColor);
/*      */     } 
/*      */     
/* 1402 */     boolean showResizeHandle = (chatEditing && (RECT_RESIZING[hudIndex] || hoveredHandle || hoveredHud || nearHud));
/* 1403 */     if (showResizeHandle) {
/* 1404 */       context.method_51448().method_22903();
/* 1405 */       context.method_51448().method_46416(0.0F, 0.0F, 450.0F);
/* 1406 */       int hX = Math.round(handleX);
/* 1407 */       int hY = Math.round(handleY);
/* 1408 */       int handleColor = RECT_RESIZING[hudIndex] ? withAlpha(16777215, 255) : -9242668;
/* 1409 */       context.method_25294(hX, hY, hX + handleSize, hY + handleSize, handleColor);
/*      */       
/* 1411 */       if (hoveredHandle || RECT_RESIZING[hudIndex]) {
/* 1412 */         int borderColor = withAlpha(16777215, 220);
/* 1413 */         drawOutlineNoOverlap(context, hX - 1, hY - 1, handleSize + 2, handleSize + 2, borderColor);
/*      */       } 
/* 1415 */       context.method_51448().method_22909();
/*      */     } 
/*      */     
/* 1418 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderPackDisplayText(class_332 context, class_310 client, PackDisplayHud module, float x, float y, float baseHeight, float scale, int textColor) {
/* 1431 */     class_2561 title = module.getNameText(client);
/* 1432 */     int textX = Math.round(module.getTextStartX());
/* 1433 */     boolean hasDescription = module.hasDescription(client);
/* 1434 */     float titleY = hasDescription ? 3.0F : ((baseHeight - 8.0F) * 0.5F);
/*      */     
/* 1436 */     context.method_51448().method_22903();
/* 1437 */     context.method_51448().method_46416(x, y, 1000.0F);
/* 1438 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 1439 */     context.method_51439(client.field_1772, title, textX, Math.round(titleY), textColor, module.textShadow.isValue());
/*      */     
/* 1441 */     if (hasDescription) {
/* 1442 */       float lineY = 14.0F;
/* 1443 */       for (class_2561 line : module.getDescriptionLines(client)) {
/* 1444 */         context.method_51439(client.field_1772, line, textX, Math.round(lineY), textColor, module.textShadow.isValue());
/* 1445 */         lineY += 8.0F;
/*      */       } 
/*      */     } 
/* 1448 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderArmorHud(class_332 context, class_310 client, ArmorHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 1466 */     if (!module.isEnabled() || client.field_1724 == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1471 */     armorStacksCache.clear();
/* 1472 */     armorDurabilityTextsCache.clear();
/*      */     
/* 1474 */     class_1661 inventory = client.field_1724.method_31548();
/* 1475 */     for (int slot = 3; slot >= 0; slot--) {
/* 1476 */       class_1799 stack = inventory.method_7372(slot);
/* 1477 */       if (!stack.method_7960()) {
/*      */ 
/*      */         
/* 1480 */         armorStacksCache.add(stack);
/* 1481 */         armorDurabilityTextsCache.add(formatDurabilityText(module, stack));
/*      */       } 
/*      */     } 
/* 1484 */     class_1799 offHand = client.field_1724.method_6079();
/* 1485 */     if (!offHand.method_7960() && offHand.method_31574(class_1802.field_8255)) {
/* 1486 */       armorStacksCache.add(offHand);
/* 1487 */       armorDurabilityTextsCache.add(formatDurabilityText(module, offHand));
/*      */     } 
/*      */     
/* 1490 */     if (armorStacksCache.isEmpty() && chatEditing) {
/* 1491 */       armorStacksCache.add(new class_1799((class_1935)class_1802.field_8805));
/* 1492 */       armorStacksCache.add(new class_1799((class_1935)class_1802.field_8058));
/* 1493 */       armorStacksCache.add(new class_1799((class_1935)class_1802.field_8348));
/* 1494 */       armorStacksCache.add(new class_1799((class_1935)class_1802.field_8285));
/* 1495 */       for (class_1799 stack : armorStacksCache) {
/* 1496 */         armorDurabilityTextsCache.add(module.formatDurability(stack.method_7936(), stack.method_7936()));
/*      */       }
/*      */     } 
/*      */     
/* 1500 */     List<class_1799> stacks = armorStacksCache;
/* 1501 */     List<String> durabilityTexts = armorDurabilityTextsCache;
/* 1502 */     if (stacks.isEmpty() && !chatEditing) {
/*      */       return;
/*      */     }
/*      */     
/* 1506 */     int iconSize = 16;
/* 1507 */     int rowHeight = 18;
/* 1508 */     int textGap = 3;
/* 1509 */     int numberSidePadding = 2;
/* 1510 */     float maxTextWidth = 0.0F;
/* 1511 */     for (String text : durabilityTexts) {
/* 1512 */       maxTextWidth = Math.max(maxTextWidth, getHudTextWidth(client, text, 8.0F));
/*      */     }
/*      */     
/* 1515 */     module.setHudScale(class_3532.method_15363(module.getHudScale(), module.getMinHudScale(), module.getMaxHudScale()));
/* 1516 */     float scale = module.getRenderHudScale();
/* 1517 */     float baseWidth = (iconSize + textGap) + maxTextWidth + numberSidePadding;
/* 1518 */     float baseHeight = (stacks.size() * rowHeight);
/* 1519 */     float hudWidth = baseWidth * scale;
/* 1520 */     float hudHeight = baseHeight * scale;
/*      */     
/* 1522 */     float maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 1523 */     float maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 1524 */     float x = module.getHudX();
/* 1525 */     float y = module.getHudY();
/* 1526 */     int currentScreenWidth = Math.round(screenWidth);
/* 1527 */     int currentScreenHeight = Math.round(screenHeight);
/* 1528 */     boolean resized = (armorLayoutInitialized && (armorLastScreenWidth != currentScreenWidth || armorLastScreenHeight != currentScreenHeight));
/*      */     
/* 1530 */     if (resized && !armorDragging && !armorResizing) {
/* 1531 */       x = (maxX <= 0.0F) ? 0.0F : (armorPositionRatioX * maxX);
/* 1532 */       y = (maxY <= 0.0F) ? 0.0F : (armorPositionRatioY * maxY);
/*      */     } 
/* 1534 */     x = class_3532.method_15363(x, 0.0F, maxX);
/* 1535 */     y = class_3532.method_15363(y, 0.0F, maxY);
/* 1536 */     module.setHudX(x);
/* 1537 */     module.setHudY(y);
/* 1538 */     armorPositionRatioX = (maxX <= 0.0F) ? 0.0F : (x / maxX);
/* 1539 */     armorPositionRatioY = (maxY <= 0.0F) ? 0.0F : (y / maxY);
/* 1540 */     armorLastScreenWidth = currentScreenWidth;
/* 1541 */     armorLastScreenHeight = currentScreenHeight;
/* 1542 */     armorLayoutInitialized = true;
/*      */     
/* 1544 */     int handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 1545 */     float handleX = x + hudWidth - handleSize / 2.0F;
/* 1546 */     float handleY = y + hudHeight - handleSize / 2.0F;
/* 1547 */     boolean hovered = false;
/* 1548 */     boolean hoveredHandle = false;
/* 1549 */     boolean nearHud = false;
/*      */     
/* 1551 */     if (!chatEditing) {
/* 1552 */       armorDragging = false;
/* 1553 */       armorResizing = false;
/* 1554 */       armorHoverProgress = approachExp(armorHoverProgress, 0.0F, 10.0F, deltaSeconds);
/*      */     } else {
/* 1556 */       hovered = isHovered(mouseX, mouseY, x, y, hudWidth, hudHeight);
/* 1557 */       hoveredHandle = isHovered(mouseX, mouseY, handleX, handleY, handleSize, handleSize);
/* 1558 */       nearHud = isNearRect(mouseX, mouseY, x, y, hudWidth, hudHeight, Math.max(14.0F, 12.0F * scale));
/* 1559 */       if (!mouseDown) {
/* 1560 */         armorDragging = false;
/* 1561 */         armorResizing = false;
/* 1562 */       } else if (!wasMouseDown && !isAnyHudInteractionActive()) {
/* 1563 */         if (hoveredHandle) {
/* 1564 */           armorResizing = true;
/* 1565 */           armorResizeStartScale = scale;
/* 1566 */           armorResizeStartMouseX = (float)mouseX;
/* 1567 */           armorResizeStartMouseY = (float)mouseY;
/* 1568 */         } else if (hovered) {
/* 1569 */           armorDragging = true;
/* 1570 */           armorDragOffsetX = (float)mouseX - x;
/* 1571 */           armorDragOffsetY = (float)mouseY - y;
/*      */         } 
/*      */       } 
/*      */       
/* 1575 */       if (mouseDown) {
/* 1576 */         if (armorDragging) {
/* 1577 */           float newX = class_3532.method_15363((float)mouseX - armorDragOffsetX, 0.0F, maxX);
/* 1578 */           float newY = class_3532.method_15363((float)mouseY - armorDragOffsetY, 0.0F, maxY);
/* 1579 */           float centerX = newX + hudWidth * 0.5F;
/* 1580 */           float centerY = newY + hudHeight * 0.5F;
/* 1581 */           boolean snappedToScreenX = false;
/* 1582 */           boolean snappedToScreenY = false;
/*      */           
/* 1584 */           if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 1585 */             newX = class_3532.method_15363(screenCenterX - hudWidth * 0.5F, 0.0F, maxX);
/* 1586 */             showVerticalGuideThisFrame = true;
/* 1587 */             snappedToScreenX = true;
/*      */           } 
/* 1589 */           if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 1590 */             newY = class_3532.method_15363(screenCenterY - hudHeight * 0.5F, 0.0F, maxY);
/* 1591 */             showHorizontalGuideThisFrame = true;
/* 1592 */             snappedToScreenY = true;
/*      */           } 
/* 1594 */           if (!snappedToScreenX) {
/* 1595 */             newX = snapHudAlignmentX(29, newX, newY, hudWidth, hudHeight, maxX, 1.0F);
/*      */           }
/* 1597 */           if (!snappedToScreenY) {
/* 1598 */             newY = snapHudAlignmentY(29, newX, newY, hudWidth, hudHeight, maxY, 1.0F);
/*      */           }
/*      */           
/* 1601 */           module.setHudX(newX);
/* 1602 */           module.setHudY(newY);
/* 1603 */           x = newX;
/* 1604 */           y = newY;
/* 1605 */           armorPositionRatioX = (maxX <= 0.0F) ? 0.0F : (x / maxX);
/* 1606 */           armorPositionRatioY = (maxY <= 0.0F) ? 0.0F : (y / maxY);
/* 1607 */           handleX = x + hudWidth - handleSize / 2.0F;
/* 1608 */           handleY = y + hudHeight - handleSize / 2.0F;
/* 1609 */         } else if (armorResizing) {
/* 1610 */           float deltaX = (float)mouseX - armorResizeStartMouseX;
/* 1611 */           float deltaY = (float)mouseY - armorResizeStartMouseY;
/* 1612 */           float delta = (deltaX + deltaY) * 0.5F;
/*      */           
/* 1614 */           float newScale = (armorResizeStartScale + delta * 0.9F / 60.0F) / 2.0F;
/* 1615 */           newScale = snapAndAnnounceHudScale((Module)module, newScale, module
/* 1616 */               .getMinHudScale(), module.getMaxHudScale());
/* 1617 */           module.setHudScale(newScale);
/* 1618 */           scale = module.getRenderHudScale();
/* 1619 */           hudWidth = baseWidth * scale;
/* 1620 */           hudHeight = baseHeight * scale;
/* 1621 */           maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 1622 */           maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 1623 */           x = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 1624 */           y = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 1625 */           module.setHudX(x);
/* 1626 */           module.setHudY(y);
/* 1627 */           armorPositionRatioX = (maxX <= 0.0F) ? 0.0F : (x / maxX);
/* 1628 */           armorPositionRatioY = (maxY <= 0.0F) ? 0.0F : (y / maxY);
/* 1629 */           handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 1630 */           handleX = x + hudWidth - handleSize / 2.0F;
/* 1631 */           handleY = y + hudHeight - handleSize / 2.0F;
/*      */         } 
/*      */       }
/*      */       
/* 1635 */       if (armorDragging) {
/* 1636 */         float centerX = x + hudWidth * 0.5F;
/* 1637 */         float centerY = y + hudHeight * 0.5F;
/* 1638 */         if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 1639 */           showVerticalGuideThisFrame = true;
/*      */         }
/* 1641 */         if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 1642 */           showHorizontalGuideThisFrame = true;
/*      */         }
/*      */       } 
/*      */       
/* 1646 */       armorHoverProgress = approachExp(armorHoverProgress, hovered ? 1.0F : 0.0F, 10.0F, deltaSeconds);
/*      */     } 
/*      */     
/* 1649 */     rememberHudSnapBounds(29, (Module)module, x, y, hudWidth, hudHeight);
/*      */     
/* 1651 */     if (chatEditing) {
/* 1652 */       if (armorResizing) {
/* 1653 */         HudCursorRelay.defer(221189, 4);
/* 1654 */       } else if (armorDragging) {
/* 1655 */         HudCursorRelay.defer(221193, 3);
/*      */       } 
/*      */     }
/*      */     
/* 1659 */     boolean textOnLeft = (x > screenWidth * 0.5F);
/* 1660 */     int hoverOutlineThickness = Math.max(1, Math.round(2.0F / Math.max(1.0F, scale)));
/*      */     
/* 1662 */     context.method_51448().method_22903();
/* 1663 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 1664 */     context.method_51448().method_22903();
/* 1665 */     context.method_51448().method_46416(x, y, 400.0F);
/* 1666 */     context.method_51448().method_22905(scale, scale, 1.0F);
/*      */     
/* 1668 */     if (module.background.isValue()) {
/* 1669 */       float blurRadius = Math.max(0.0F, module.backgroundBlurRadius.getValue());
/* 1670 */       if (blurRadius > 0.0F) {
/* 1671 */         float safeScale = Math.max(scale, 1.0F);
/* 1672 */         float normalizedBlurRadius = blurRadius / safeScale;
/* 1673 */         float blurX = 0.0F;
/* 1674 */         float blurY = 0.0F;
/* 1675 */         float blurWidth = baseWidth;
/* 1676 */         float blurHeight = baseHeight;
/* 1677 */         float blurQuality = getOptimizedHudBlurQuality(normalizedBlurRadius);
/* 1678 */         long blurStateKey = makeHudBlurStateKey(normalizedBlurRadius, safeScale, blurX, blurY, blurWidth, blurHeight);
/* 1679 */         Blur.INSTANCE.registerHudBlurState(27, blurStateKey);
/*      */         
/* 1681 */         if (blurQuality > 0.0F && blurWidth > 1.5F && blurHeight > 1.5F) {
/* 1682 */           Blur.INSTANCE.renderCached(ShapeProperties.create(context.method_51448(), blurX, blurY, blurWidth, blurHeight)
/* 1683 */               .round(getClampedHudCornerRadius(module.cornerRounding.getValue(), blurWidth, blurHeight))
/* 1684 */               .softness(0.0F)
/* 1685 */               .quality(blurQuality)
/* 1686 */               .color(-1)
/* 1687 */               .build());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1692 */     if (module.background.isValue()) {
/* 1693 */       if (module.isGradientPreset()) {
/* 1694 */         int hoverFill = chatEditing ? withAlpha(16777215, (int)(30.0F * armorHoverProgress)) : 0;
/* 1695 */         renderGradientRect(context, 0.0F, 0.0F, baseWidth, baseHeight, hoverFill, module
/* 1696 */             .getResolvedGradientStartColor(), module.getResolvedGradientEndColor(), module
/* 1697 */             .getGradientDirection(), module.getGradientAnimationOffset(System.currentTimeMillis()), module
/* 1698 */             .hasActiveAnimatedBackground(), module.cornerRounding.getValue());
/* 1699 */         context.method_51452();
/*      */       } else {
/* 1701 */         int targetBgColor = module.getResolvedBackgroundColor(client);
/* 1702 */         if (!armorBackgroundColorInitialized) {
/* 1703 */           armorAnimatedBackgroundColor = targetBgColor;
/* 1704 */           armorBackgroundColorInitialized = true;
/*      */         } else {
/* 1706 */           armorAnimatedBackgroundColor = approachColorExp(armorAnimatedBackgroundColor, targetBgColor, 12.0F, deltaSeconds);
/*      */         } 
/*      */         
/* 1709 */         int bgColor = armorAnimatedBackgroundColor;
/* 1710 */         if (chatEditing) {
/* 1711 */           int hoverFill = withAlpha(16777215, (int)(30.0F * armorHoverProgress));
/* 1712 */           bgColor = blendARGB(bgColor, hoverFill);
/*      */         } 
/* 1714 */         renderSolidHudBackground(context, 0.0F, 0.0F, baseWidth, baseHeight, bgColor, module.cornerRounding
/* 1715 */             .getValue(), (module.backgroundBlurRadius.getValue() <= 0.0F));
/* 1716 */         context.method_51452();
/*      */       } 
/*      */     }
/*      */     int i;
/* 1720 */     for (i = 0; i < stacks.size(); i++) {
/* 1721 */       int iconX; class_1799 stack = stacks.get(i);
/* 1722 */       String durabilityText = durabilityTexts.get(i);
/* 1723 */       float rowY = (i * rowHeight);
/* 1724 */       float textWidth = getHudTextWidth(client, durabilityText, 8.0F);
/*      */ 
/*      */ 
/*      */       
/* 1728 */       if (textOnLeft) {
/* 1729 */         iconX = Math.round(numberSidePadding + maxTextWidth + textGap);
/* 1730 */         float textX = numberSidePadding + maxTextWidth - textWidth;
/*      */       } else {
/* 1732 */         iconX = 0;
/* 1733 */         float textX = (iconSize + textGap);
/*      */       } 
/*      */       
/* 1736 */       int iconY = Math.round(rowY + 1.0F);
/* 1737 */       context.method_51427(stack, iconX, iconY);
/*      */     } 
/* 1739 */     context.method_51448().method_22909();
/*      */     
/* 1741 */     for (i = 0; i < stacks.size(); i++) {
/* 1742 */       float textX; String durabilityText = durabilityTexts.get(i);
/* 1743 */       class_1799 stackForColor = stacks.get(i);
/* 1744 */       float rowY = (i * rowHeight);
/* 1745 */       float textWidth = getHudTextWidth(client, durabilityText, 8.0F);
/*      */ 
/*      */       
/* 1748 */       if (textOnLeft) {
/* 1749 */         textX = numberSidePadding + maxTextWidth - textWidth;
/*      */       } else {
/* 1751 */         textX = (iconSize + textGap);
/*      */       } 
/*      */       
/* 1754 */       float textY = rowY + 4.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1761 */       int stackMax = stackForColor.method_7936();
/*      */ 
/*      */       
/* 1764 */       int stackRemaining = stackForColor.method_7963() ? Math.max(0, stackMax - stackForColor.method_7919()) : stackMax;
/* 1765 */       int textColor = module.colorForDurability(stackRemaining, stackMax);
/* 1766 */       if (textColor == -1) {
/* 1767 */         textColor = resolveHudTextColor();
/*      */       }
/*      */       
/* 1770 */       renderScaledHudTextColored(context, client, durabilityText, x, y, textX, textY, 8.0F, scale, module.textShadow.isValue(), textColor);
/*      */     } 
/*      */     
/* 1773 */     if (chatEditing && armorHoverProgress > 0.05F) {
/* 1774 */       int borderColor = withAlpha(16777215, (int)(160.0F * armorHoverProgress));
/* 1775 */       drawOuterOutline(context, x, y, hudWidth, hudHeight, hoverOutlineThickness, borderColor);
/*      */     } 
/*      */     
/* 1778 */     boolean showResizeHandle = (chatEditing && (armorResizing || hoveredHandle || hovered || nearHud));
/* 1779 */     if (showResizeHandle) {
/* 1780 */       context.method_51448().method_22903();
/* 1781 */       context.method_51448().method_46416(0.0F, 0.0F, 450.0F);
/* 1782 */       int hX = Math.round(handleX);
/* 1783 */       int hY = Math.round(handleY);
/* 1784 */       int handleColor = armorResizing ? withAlpha(16777215, 255) : -9242668;
/* 1785 */       context.method_25294(hX, hY, hX + handleSize, hY + handleSize, handleColor);
/*      */       
/* 1787 */       if (hoveredHandle || armorResizing) {
/* 1788 */         int borderColor = withAlpha(16777215, 220);
/* 1789 */         drawOutlineNoOverlap(context, hX - 1, hY - 1, handleSize + 2, handleSize + 2, borderColor);
/*      */       } 
/* 1791 */       context.method_51448().method_22909();
/*      */     } 
/*      */     
/* 1794 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderConsumableHud(class_332 context, class_310 client, Consumable module, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 1824 */     if (!module.isEnabled() || client.field_1724 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1828 */     Consumable.Layout layout = module.computeLayout(chatEditing);
/* 1829 */     if (layout.entries().isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1833 */     float baseWidth = layout.baseWidth();
/* 1834 */     float baseHeight = layout.baseHeight();
/*      */     
/* 1836 */     module.setHudScale(class_3532.method_15363(module.getHudScale(), module.getMinHudScale(), module.getMaxHudScale()));
/* 1837 */     float scale = module.getRenderHudScale();
/* 1838 */     float hudWidth = baseWidth * scale;
/* 1839 */     float hudHeight = baseHeight * scale;
/*      */     
/* 1841 */     float maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 1842 */     float maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 1843 */     float x = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 1844 */     float y = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 1845 */     module.setHudX(x);
/* 1846 */     module.setHudY(y);
/*      */     
/* 1848 */     int handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 1849 */     float handleX = x + hudWidth - handleSize;
/* 1850 */     float handleY = y + hudHeight - handleSize;
/* 1851 */     boolean hovered = false;
/* 1852 */     boolean hoveredHandle = false;
/* 1853 */     boolean nearHud = false;
/*      */     
/* 1855 */     if (!chatEditing) {
/* 1856 */       RECT_DRAGGING[hudIndex] = false;
/* 1857 */       RECT_RESIZING[hudIndex] = false;
/* 1858 */       RECT_HOVER_PROGRESS[hudIndex] = approachExp(RECT_HOVER_PROGRESS[hudIndex], 0.0F, 10.0F, deltaSeconds);
/*      */     } else {
/* 1860 */       hovered = isHovered(mouseX, mouseY, x, y, hudWidth, hudHeight);
/* 1861 */       hoveredHandle = isHovered(mouseX, mouseY, handleX, handleY, handleSize, handleSize);
/* 1862 */       nearHud = isNearRect(mouseX, mouseY, x, y, hudWidth, hudHeight, Math.max(14.0F, 12.0F * scale));
/* 1863 */       if (!mouseDown) {
/* 1864 */         RECT_DRAGGING[hudIndex] = false;
/* 1865 */         RECT_RESIZING[hudIndex] = false;
/* 1866 */       } else if (!wasMouseDown && !isAnyHudInteractionActive()) {
/* 1867 */         if (hoveredHandle) {
/* 1868 */           RECT_RESIZING[hudIndex] = true;
/* 1869 */           RECT_RESIZE_START_WIDTH[hudIndex] = scale;
/* 1870 */           RECT_RESIZE_START_MOUSE_X[hudIndex] = (float)mouseX;
/* 1871 */           RECT_RESIZE_START_MOUSE_Y[hudIndex] = (float)mouseY;
/* 1872 */         } else if (hovered) {
/* 1873 */           RECT_DRAGGING[hudIndex] = true;
/* 1874 */           RECT_DRAG_OFFSET_X[hudIndex] = (float)mouseX - x;
/* 1875 */           RECT_DRAG_OFFSET_Y[hudIndex] = (float)mouseY - y;
/*      */         } 
/*      */       } 
/*      */       
/* 1879 */       if (mouseDown) {
/* 1880 */         if (RECT_DRAGGING[hudIndex]) {
/* 1881 */           float newX = class_3532.method_15363((float)mouseX - RECT_DRAG_OFFSET_X[hudIndex], 0.0F, maxX);
/* 1882 */           float newY = class_3532.method_15363((float)mouseY - RECT_DRAG_OFFSET_Y[hudIndex], 0.0F, maxY);
/* 1883 */           float centerX = newX + hudWidth * 0.5F;
/* 1884 */           float centerY = newY + hudHeight * 0.5F;
/* 1885 */           boolean snappedToScreenX = false;
/* 1886 */           boolean snappedToScreenY = false;
/* 1887 */           if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 1888 */             newX = class_3532.method_15363(screenCenterX - hudWidth * 0.5F, 0.0F, maxX);
/* 1889 */             showVerticalGuideThisFrame = true;
/* 1890 */             snappedToScreenX = true;
/*      */           } 
/* 1892 */           if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 1893 */             newY = class_3532.method_15363(screenCenterY - hudHeight * 0.5F, 0.0F, maxY);
/* 1894 */             showHorizontalGuideThisFrame = true;
/* 1895 */             snappedToScreenY = true;
/*      */           } 
/* 1897 */           if (!snappedToScreenX) {
/* 1898 */             newX = snapHudAlignmentX(hudIndex, newX, newY, hudWidth, hudHeight, maxX, 1.0F);
/*      */           }
/* 1900 */           if (!snappedToScreenY) {
/* 1901 */             newY = snapHudAlignmentY(hudIndex, newX, newY, hudWidth, hudHeight, maxY, 1.0F);
/*      */           }
/* 1903 */           module.setHudX(newX);
/* 1904 */           module.setHudY(newY);
/* 1905 */           x = newX;
/* 1906 */           y = newY;
/* 1907 */           handleX = x + hudWidth - handleSize;
/* 1908 */           handleY = y + hudHeight - handleSize;
/* 1909 */         } else if (RECT_RESIZING[hudIndex]) {
/* 1910 */           float deltaX = (float)mouseX - RECT_RESIZE_START_MOUSE_X[hudIndex];
/* 1911 */           float deltaY = (float)mouseY - RECT_RESIZE_START_MOUSE_Y[hudIndex];
/* 1912 */           float delta = (deltaX + deltaY) * 0.5F;
/* 1913 */           float newScale = (RECT_RESIZE_START_WIDTH[hudIndex] + delta * 0.9F / 60.0F) / 2.0F;
/*      */           
/* 1915 */           newScale = snapAndAnnounceHudScale((Module)module, newScale, module
/* 1916 */               .getMinHudScale(), module.getMaxHudScale());
/* 1917 */           module.setHudScale(newScale);
/* 1918 */           scale = module.getRenderHudScale();
/* 1919 */           hudWidth = baseWidth * scale;
/* 1920 */           hudHeight = baseHeight * scale;
/* 1921 */           maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 1922 */           maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 1923 */           x = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 1924 */           y = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 1925 */           module.setHudX(x);
/* 1926 */           module.setHudY(y);
/* 1927 */           handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 1928 */           handleX = x + hudWidth - handleSize;
/* 1929 */           handleY = y + hudHeight - handleSize;
/*      */         } 
/*      */       }
/*      */       
/* 1933 */       if (RECT_DRAGGING[hudIndex]) {
/* 1934 */         float centerX = x + hudWidth * 0.5F;
/* 1935 */         float centerY = y + hudHeight * 0.5F;
/* 1936 */         if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 1937 */           showVerticalGuideThisFrame = true;
/*      */         }
/* 1939 */         if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 1940 */           showHorizontalGuideThisFrame = true;
/*      */         }
/*      */       } 
/*      */       
/* 1944 */       RECT_HOVER_PROGRESS[hudIndex] = approachExp(RECT_HOVER_PROGRESS[hudIndex], hovered ? 1.0F : 0.0F, 10.0F, deltaSeconds);
/*      */     } 
/*      */     
/* 1947 */     rememberHudSnapBounds(hudIndex, (Module)module, x, y, hudWidth, hudHeight);
/*      */     
/* 1949 */     if (chatEditing) {
/* 1950 */       if (RECT_RESIZING[hudIndex]) {
/* 1951 */         HudCursorRelay.defer(221189, 4);
/* 1952 */       } else if (RECT_DRAGGING[hudIndex]) {
/* 1953 */         HudCursorRelay.defer(221193, 3);
/*      */       } 
/*      */     }
/*      */     
/* 1957 */     int hoverOutlineThickness = Math.max(1, Math.round(2.0F / Math.max(1.0F, scale)));
/*      */     
/* 1959 */     context.method_51448().method_22903();
/* 1960 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 1961 */     context.method_51448().method_22903();
/* 1962 */     context.method_51448().method_46416(x, y, 400.0F);
/* 1963 */     context.method_51448().method_22905(scale, scale, 1.0F);
/*      */     
/* 1965 */     if (module.background.isValue()) {
/* 1966 */       float blurRadius = Math.max(0.0F, module.backgroundBlurRadius.getValue());
/* 1967 */       if (blurRadius > 0.0F) {
/* 1968 */         float safeScale = Math.max(scale, 1.0F);
/* 1969 */         float normalizedBlurRadius = blurRadius / safeScale;
/* 1970 */         float blurX = 0.0F;
/* 1971 */         float blurY = 0.0F;
/* 1972 */         float blurWidth = baseWidth;
/* 1973 */         float blurHeight = baseHeight;
/* 1974 */         float blurQuality = getOptimizedHudBlurQuality(normalizedBlurRadius);
/* 1975 */         long blurStateKey = makeHudBlurStateKey(normalizedBlurRadius, safeScale, blurX, blurY, blurWidth, blurHeight);
/* 1976 */         Blur.INSTANCE.registerHudBlurState(hudIndex, blurStateKey);
/*      */         
/* 1978 */         if (blurQuality > 0.0F && blurWidth > 1.5F && blurHeight > 1.5F) {
/* 1979 */           Blur.INSTANCE.renderCached(ShapeProperties.create(context.method_51448(), blurX, blurY, blurWidth, blurHeight)
/* 1980 */               .round(getClampedHudCornerRadius(module.cornerRounding.getValue(), blurWidth, blurHeight))
/* 1981 */               .softness(0.0F)
/* 1982 */               .quality(blurQuality)
/* 1983 */               .color(-1)
/* 1984 */               .build());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1989 */     if (module.background.isValue()) {
/* 1990 */       if (module.isGradientPreset()) {
/* 1991 */         int hoverFill = chatEditing ? withAlpha(16777215, (int)(30.0F * RECT_HOVER_PROGRESS[hudIndex])) : 0;
/* 1992 */         renderGradientRect(context, 0.0F, 0.0F, baseWidth, baseHeight, hoverFill, module
/* 1993 */             .getResolvedGradientStartColor(), module.getResolvedGradientEndColor(), module
/* 1994 */             .getGradientDirection(), module.getGradientAnimationOffset(System.currentTimeMillis()), module
/* 1995 */             .hasActiveAnimatedBackground(), module.cornerRounding.getValue());
/* 1996 */         context.method_51452();
/*      */       } else {
/* 1998 */         int targetBgColor = module.getResolvedBackgroundColor(client);
/* 1999 */         if (!RECT_BG_COLOR_INITIALIZED[hudIndex]) {
/* 2000 */           RECT_BG_ANIMATED_COLOR[hudIndex] = targetBgColor;
/* 2001 */           RECT_BG_COLOR_INITIALIZED[hudIndex] = true;
/*      */         } else {
/* 2003 */           RECT_BG_ANIMATED_COLOR[hudIndex] = approachColorExp(RECT_BG_ANIMATED_COLOR[hudIndex], targetBgColor, 12.0F, deltaSeconds);
/*      */         } 
/* 2005 */         int bgColor = RECT_BG_ANIMATED_COLOR[hudIndex];
/* 2006 */         if (chatEditing) {
/* 2007 */           int hoverFill = withAlpha(16777215, (int)(30.0F * RECT_HOVER_PROGRESS[hudIndex]));
/* 2008 */           bgColor = blendARGB(bgColor, hoverFill);
/*      */         } 
/* 2010 */         renderSolidHudBackground(context, 0.0F, 0.0F, baseWidth, baseHeight, bgColor, module.cornerRounding
/* 2011 */             .getValue(), (module.backgroundBlurRadius.getValue() <= 0.0F));
/* 2012 */         context.method_51452();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2019 */     float padding = 3.0F;
/* 2020 */     float itemSize = 18.0F;
/* 2021 */     for (Consumable.IconEntry entry : layout.entries()) {
/*      */ 
/*      */ 
/*      */       
/* 2025 */       int iconX = Math.round(padding + 1.0F + entry.col() * itemSize);
/* 2026 */       int iconY = Math.round(padding + entry.row() * itemSize);
/* 2027 */       context.method_51427(entry.stack(), iconX, iconY);
/* 2028 */       if (module.showCount.isValue())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2035 */         context.method_51431(client.field_1772, entry.stack(), iconX, iconY);
/*      */       }
/*      */     } 
/* 2038 */     context.method_51448().method_22909();
/*      */     
/* 2040 */     if (chatEditing && RECT_HOVER_PROGRESS[hudIndex] > 0.05F) {
/* 2041 */       int borderColor = withAlpha(16777215, (int)(160.0F * RECT_HOVER_PROGRESS[hudIndex]));
/* 2042 */       drawOuterOutline(context, x, y, hudWidth, hudHeight, hoverOutlineThickness, borderColor);
/*      */     } 
/*      */     
/* 2045 */     boolean showResizeHandle = (chatEditing && (RECT_RESIZING[hudIndex] || hoveredHandle || hovered || nearHud));
/* 2046 */     if (showResizeHandle) {
/* 2047 */       context.method_51448().method_22903();
/* 2048 */       context.method_51448().method_46416(0.0F, 0.0F, 450.0F);
/* 2049 */       int hX = Math.round(handleX);
/* 2050 */       int hY = Math.round(handleY);
/* 2051 */       int handleColor = RECT_RESIZING[hudIndex] ? withAlpha(16777215, 255) : -9242668;
/* 2052 */       context.method_25294(hX, hY, hX + handleSize, hY + handleSize, handleColor);
/* 2053 */       if (hoveredHandle || RECT_RESIZING[hudIndex]) {
/* 2054 */         int borderColor = withAlpha(16777215, 220);
/* 2055 */         drawOutlineNoOverlap(context, hX - 1, hY - 1, handleSize + 2, handleSize + 2, borderColor);
/*      */       } 
/* 2057 */       context.method_51448().method_22909();
/*      */     } 
/*      */     
/* 2060 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderCoordinatesHud(class_332 context, class_310 client, CoordinatesHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2078 */     if (!module.isEnabled() || client.field_1724 == null || client.field_1687 == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2090 */     List<String> updatedLines = new ArrayList<>();
/* 2091 */     class_2338 pos = client.field_1724.method_24515();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2099 */     boolean hideCoords = StreamerMode.getInstance().isHideCoordinatesEnabled();
/* 2100 */     if (module.showX.isValue() && !hideCoords) {
/* 2101 */       updatedLines.add("X: " + pos.method_10263());
/*      */     }
/* 2103 */     if (module.showY.isValue() && !hideCoords) {
/* 2104 */       updatedLines.add("Y: " + pos.method_10264());
/*      */     }
/* 2106 */     if (module.showZ.isValue() && !hideCoords) {
/* 2107 */       updatedLines.add("Z: " + pos.method_10260());
/*      */     }
/* 2109 */     if (module.showChunk.isValue() && !hideCoords) {
/* 2110 */       updatedLines.add("C: " + class_4076.method_18684(pos.method_10263()) + "/" + class_4076.method_18684(pos.method_10260()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2116 */     coordinatesBiomeNameCache = "";
/* 2117 */     coordinatesBiomeColorCache = 16777045;
/* 2118 */     if (module.showBiome.isValue()) {
/* 2119 */       coordinatesBiomeNameCache = getBiomeName(client, pos);
/* 2120 */       coordinatesBiomeColorCache = getBiomeColor(client, pos);
/* 2121 */       updatedLines.add("Biome: " + coordinatesBiomeNameCache);
/*      */     } 
/* 2123 */     coordinatesFacingCache = getFacing8Point(client.field_1724.method_5705(0.0F));
/* 2124 */     coordinatesTopSignCache = getDirectionXSign(coordinatesFacingCache);
/* 2125 */     coordinatesBottomSignCache = getDirectionZSign(coordinatesFacingCache);
/* 2126 */     coordinatesLinesCache = updatedLines;
/*      */     
/* 2128 */     List<String> lines = coordinatesLinesCache;
/* 2129 */     String biomeName = coordinatesBiomeNameCache;
/* 2130 */     if (lines.isEmpty() && !chatEditing) {
/*      */       return;
/*      */     }
/* 2133 */     if (lines.isEmpty()) {
/* 2134 */       lines.add("Coordinates");
/*      */     }
/*      */     
/* 2137 */     float paddingX = 5.0F;
/* 2138 */     float paddingY = 4.0F;
/* 2139 */     float lineHeight = 10.0F;
/* 2140 */     float maxLineWidth = 0.0F;
/* 2141 */     for (String line : lines) {
/* 2142 */       maxLineWidth = Math.max(maxLineWidth, getHudTextWidth(client, line, 8.0F));
/*      */     }
/* 2144 */     boolean showDirection = module.showDirection.isValue();
/* 2145 */     float directionColumn = showDirection ? 26.0F : 0.0F;
/* 2146 */     float baseWidth = Math.max(74.0F, paddingX * 2.0F + maxLineWidth + directionColumn);
/* 2147 */     float baseHeight = Math.max(20.0F, paddingY * 2.0F + lines.size() * lineHeight);
/*      */     
/* 2149 */     renderRectHud(context, client, (RectHudModule)module, "", 4, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight);
/*      */ 
/*      */     
/* 2152 */     float x = module.getHudX();
/* 2153 */     float y = module.getHudY();
/* 2154 */     float scale = module.getRenderHudScale();
/* 2155 */     context.method_51448().method_22903();
/* 2156 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2165 */     float textBlockHeight = lines.size() * lineHeight;
/* 2166 */     float textOffsetY = (baseHeight - textBlockHeight) * 0.5F;
/* 2167 */     if (textOffsetY < paddingY) {
/* 2168 */       textOffsetY = paddingY;
/*      */     }
/* 2170 */     for (int i = 0; i < lines.size(); i++) {
/* 2171 */       String line = lines.get(i);
/* 2172 */       float textY = textOffsetY + i * lineHeight;
/* 2173 */       if (module.showBiome.isValue() && line.startsWith("Biome: ")) {
/* 2174 */         String label = "Biome: ";
/* 2175 */         renderScaledHudTextColored(context, client, label, x, y, paddingX, textY, 8.0F, scale, module.textShadow.isValue(), resolveHudTextColor());
/* 2176 */         float labelWidth = getHudTextWidth(client, label, 8.0F);
/* 2177 */         renderScaledHudTextColored(context, client, biomeName, x, y, paddingX + labelWidth, textY, 8.0F, scale, module.textShadow.isValue(), coordinatesBiomeColorCache);
/*      */       } else {
/* 2179 */         renderScaledHudText(context, client, line, x, y, paddingX, textY, 8.0F, scale, module.textShadow.isValue());
/*      */       } 
/*      */     } 
/*      */     
/* 2183 */     if (showDirection) {
/* 2184 */       String facing = coordinatesFacingCache;
/* 2185 */       float facingWidth = getHudTextWidth(client, facing, 8.0F);
/* 2186 */       float facingCenterX = baseWidth - directionColumn * 0.5F;
/* 2187 */       float facingX = facingCenterX - facingWidth * 0.5F;
/* 2188 */       float facingY = baseHeight * 0.5F - 4.0F;
/* 2189 */       if (module.showAxisSigns.isValue()) {
/* 2190 */         String topSign = coordinatesTopSignCache;
/* 2191 */         String bottomSign = coordinatesBottomSignCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2202 */         float maxStackHalf = baseHeight * 0.5F - 5.0F;
/* 2203 */         if (maxStackHalf < 0.0F) {
/* 2204 */           maxStackHalf = 0.0F;
/*      */         }
/* 2206 */         float signOffset = Math.min(10.0F, maxStackHalf);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2217 */         float signTextSize = hideCoords ? 5.0F : 8.0F;
/* 2218 */         if (!topSign.isEmpty()) {
/* 2219 */           float signWidth = getHudTextWidth(client, topSign, signTextSize);
/* 2220 */           renderScaledHudText(context, client, topSign, x, y, facingCenterX - signWidth * 0.5F, facingY - signOffset, signTextSize, scale, module.textShadow.isValue());
/*      */         } 
/* 2222 */         renderScaledHudText(context, client, facing, x, y, facingX, facingY, 8.0F, scale, module.textShadow.isValue());
/* 2223 */         if (!bottomSign.isEmpty()) {
/* 2224 */           float signWidth = getHudTextWidth(client, bottomSign, signTextSize);
/* 2225 */           renderScaledHudText(context, client, bottomSign, x, y, facingCenterX - signWidth * 0.5F, facingY + signOffset, signTextSize, scale, module.textShadow.isValue());
/*      */         } 
/*      */       } else {
/* 2228 */         renderScaledHudText(context, client, facing, x, y, facingX, facingY, 8.0F, scale, module.textShadow.isValue());
/*      */       } 
/*      */     } 
/*      */     
/* 2232 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderPingHud(class_332 context, class_310 client, PingHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/*      */     int pingColor;
/* 2250 */     if (!module.isEnabled() || client.field_1724 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2254 */     boolean local = (client.method_1496() || client.method_1558() == null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2260 */     boolean reversed = module.reverseOrder.isValue();
/* 2261 */     String label = reversed ? " Ping" : "Ping: ";
/* 2262 */     String value = getCachedHudText((RectHudModule)module, 5, chatEditing, () -> {
/*      */           if (local) {
/*      */             module.resetPingCache();
/*      */             return "... ms";
/*      */           } 
/*      */           int rawPing = getServerPing(client);
/*      */           module.updatePing(rawPing);
/*      */           int displayPing = module.getCachedPing();
/*      */           return (displayPing >= 0) ? ("" + displayPing + " ms") : "... ms";
/*      */         });
/* 2272 */     String fullText = reversed ? (value + value) : (label + label);
/* 2273 */     fullText = wrapTextWithBrackets(fullText, (RectHudModule)module);
/* 2274 */     float baseWidth = Math.max(48.0F, getHudTextWidth(client, fullText, 8.0F) + 12.0F);
/*      */     
/* 2276 */     renderRectHud(context, client, (RectHudModule)module, "", 5, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, 20.0F);
/*      */ 
/*      */     
/* 2279 */     float x = module.getHudX();
/* 2280 */     float y = module.getHudY();
/* 2281 */     float scale = module.getRenderHudScale();
/* 2282 */     float totalWidth = getHudTextWidth(client, fullText, 8.0F);
/* 2283 */     float textX = (baseWidth - totalWidth) * 0.5F;
/* 2284 */     float textY = 6.0F;
/*      */     
/* 2286 */     context.method_51448().method_22903();
/* 2287 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 2288 */     renderScaledHudText(context, client, fullText, x, y, textX, textY, 8.0F, scale, module.textShadow.isValue());
/* 2289 */     float labelWidth = getHudTextWidth(client, label, 8.0F);
/*      */     
/* 2291 */     if (local) {
/* 2292 */       pingColor = 16777045;
/*      */     } else {
/* 2294 */       int ping = module.getCachedPing();
/* 2295 */       if (module.dynamicPingColor.isValue() && ping > 0)
/* 2296 */       { if (ping < 60) { pingColor = -11141291; }
/* 2297 */         else if (ping < 150) { pingColor = -171; }
/* 2298 */         else if (ping < 300) { pingColor = -22016; }
/* 2299 */         else { pingColor = -43691; }
/*      */          }
/* 2301 */       else { pingColor = resolveHudTextColor(); }
/*      */     
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2308 */     float valueX = reversed ? textX : (textX + labelWidth);
/* 2309 */     renderScaledHudTextColored(context, client, value, x, y, valueX, textY, 8.0F, scale, module.textShadow.isValue(), pingColor);
/* 2310 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSimpleTextHud(class_332 context, class_310 client, RectHudModule module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2330 */     float baseWidth = Math.max(48.0F, getHudTextWidth(client, text, 8.0F) + 14.0F);
/* 2331 */     renderRectHud(context, client, module, text, hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, 20.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderTrapTimerHud(class_332 context, class_310 client, TrapTimer module, class_2561 text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2352 */     float baseWidth = Math.max(48.0F, client.field_1772.method_27525((class_5348)text) + 14.0F);
/* 2353 */     module.ensureDefaultHudPosition(screenWidth, screenHeight, baseWidth, 20.0F);
/* 2354 */     float x = module.getHudX();
/* 2355 */     float y = module.getHudY();
/* 2356 */     float scale = module.getRenderHudScale();
/*      */     
/* 2358 */     renderRectHud(context, client, (RectHudModule)module, "", hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, 20.0F);
/*      */ 
/*      */     
/* 2361 */     context.method_51448().method_22903();
/* 2362 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 2363 */     context.method_51448().method_22903();
/* 2364 */     context.method_51448().method_46416(x, y, 425.0F);
/* 2365 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 2366 */     float textX = 7.0F;
/* 2367 */     float textY = 5.5F;
/* 2368 */     context.method_51439(client.field_1772, text, Math.round(textX), Math.round(textY), resolveHudTextColor(), module.textShadow.isValue());
/* 2369 */     context.method_51452();
/* 2370 */     context.method_51448().method_22909();
/* 2371 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSessionTimeHud(class_332 context, class_310 client, RectHudModule module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2391 */     float x = module.getHudX();
/* 2392 */     float y = module.getHudY();
/* 2393 */     float scale = module.getRenderHudScale();
/* 2394 */     float textWidth = client.field_1772.method_1727(text);
/* 2395 */     float baseWidth = Math.max(48.0F, textWidth + 16.0F);
/* 2396 */     float baseHeight = 20.0F;
/*      */     
/* 2398 */     renderRectHud(context, client, module, "", hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight);
/*      */ 
/*      */     
/* 2401 */     context.method_51448().method_22903();
/* 2402 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 2403 */     context.method_51448().method_22903();
/* 2404 */     context.method_51448().method_46416(x, y, 425.0F);
/* 2405 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 2406 */     float textX = (baseWidth - textWidth) * 0.5F;
/* 2407 */     float textY = (baseHeight - 9.0F) * 0.5F;
/* 2408 */     context.method_51433(client.field_1772, text, Math.round(textX), Math.round(textY), resolveHudTextColor(), module.textShadow.isValue());
/* 2409 */     context.method_51452();
/* 2410 */     context.method_51448().method_22909();
/* 2411 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderScoreboardHud(class_332 context, class_310 client, ScoreboardHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/*      */     int targetTitleBgColor, targetRowBgColor;
/* 2429 */     if (!module.isEnabled() || client.field_1687 == null || client.field_1724 == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2434 */     class_269 scoreboard = client.field_1687.method_8428();
/* 2435 */     class_266 objective = scoreboard.method_1189(class_8646.field_45157);
/* 2436 */     if (objective == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2440 */     Collection<class_9011> entries = scoreboard.method_1184(objective);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2449 */     List<class_9011> filteredEntries = entries.stream().filter(entry -> !entry.method_55385()).sorted((a, b) -> { int byValue = Integer.compare(b.comp_2128(), a.comp_2128()); return (byValue != 0) ? byValue : String.CASE_INSENSITIVE_ORDER.compare(a.comp_2127(), b.comp_2127()); }).limit(15L).toList();
/* 2450 */     if (filteredEntries.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 2454 */     NickHider nickHider = NickHider.getInstance();
/* 2455 */     boolean shouldHideNick = (nickHider != null && nickHider.isEnabled());
/*      */     
/* 2457 */     ArrayList<ScoreboardSidebarEntry> sidebarEntries = new ArrayList<>();
/* 2458 */     for (class_9011 entry : filteredEntries) {
/* 2459 */       class_2561 class_25611; class_268 team = scoreboard.method_1164(entry.comp_2127());
/* 2460 */       class_2561 name = entry.method_55387();
/* 2461 */       class_5250 class_52501 = class_268.method_1142((class_270)team, name);
/* 2462 */       if (shouldHideNick) {
/* 2463 */         class_25611 = nickHider.rewrite((class_2561)class_52501);
/*      */       }
/*      */ 
/*      */       
/* 2467 */       class_5250 class_52502 = class_2561.method_43473();
/* 2468 */       boolean hasScore = false;
/*      */       try {
/* 2470 */         class_52502 = entry.method_55386(objective.method_55384());
/* 2471 */         String rawScore = (class_52502 == null) ? "" : class_52502.getString();
/* 2472 */         hasScore = (rawScore != null && !rawScore.trim().isEmpty());
/* 2473 */       } catch (Throwable ignored) {
/* 2474 */         hasScore = false;
/*      */       } 
/* 2476 */       int scoreWidth = hasScore ? (int)getHudTextWidth(client, class_52502.getString(), 8.0F) : 0;
/*      */       
/* 2478 */       sidebarEntries.add(new ScoreboardSidebarEntry(class_25611, (class_2561)class_52502, hasScore, scoreWidth));
/*      */     } 
/*      */ 
/*      */     
/* 2482 */     class_2561 title = objective.method_1114();
/* 2483 */     if (shouldHideNick) {
/* 2484 */       title = nickHider.rewrite(title);
/*      */     }
/* 2486 */     int titleWidth = (int)getHudTextWidth(client, title.getString(), 8.0F);
/* 2487 */     int maxContentWidth = titleWidth;
/* 2488 */     int entryCount = sidebarEntries.size();
/*      */     
/* 2490 */     for (ScoreboardSidebarEntry sidebarEntry : sidebarEntries) {
/* 2491 */       int textWidth = (int)getHudTextWidth(client, sidebarEntry.name().getString(), 8.0F);
/*      */ 
/*      */ 
/*      */       
/* 2495 */       boolean hideZeroScore = (sidebarEntry.hasScore() && module.showNumbers.isValue() && !module.showZeros.isValue() && phaze$isZeroScoreText(sidebarEntry.score()));
/* 2496 */       int scoreWidth = (sidebarEntry.hasScore() && !hideZeroScore) ? sidebarEntry.scoreWidth() : 0;
/* 2497 */       if (scoreWidth > 0) {
/*      */ 
/*      */         
/* 2500 */         maxContentWidth = Math.max(maxContentWidth, textWidth + scoreWidth + 2); continue;
/*      */       } 
/* 2502 */       maxContentWidth = Math.max(maxContentWidth, textWidth);
/*      */     } 
/*      */ 
/*      */     
/* 2506 */     int vanillaHorizontalPadding = 3;
/* 2507 */     float baseWidth = (maxContentWidth + vanillaHorizontalPadding + 2);
/* 2508 */     int topInset = module.showTitle.isValue() ? 10 : 1;
/* 2509 */     float baseHeight = entryCount * 9.0F + topInset;
/*      */ 
/*      */ 
/*      */     
/* 2513 */     if (!module.isPositionInitialized()) {
/* 2514 */       float scale = module.getRenderHudScale();
/* 2515 */       float hudWidth = baseWidth * scale;
/* 2516 */       float hudHeight = baseHeight * scale;
/* 2517 */       float vanillaX = Math.max(0.0F, screenWidth - hudWidth - 2.0F);
/* 2518 */       float vanillaY = Math.max(0.0F, (screenHeight - hudHeight) * 0.5F);
/* 2519 */       module.setHudX(vanillaX);
/* 2520 */       module.setHudY(vanillaY);
/* 2521 */       module.setPositionInitialized(true);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2526 */     boolean prevBackground = module.background.isValue();
/* 2527 */     module.background.setValue(false);
/*      */     try {
/* 2529 */       renderRectHud(context, client, (RectHudModule)module, "", 14, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight);
/*      */     } finally {
/*      */       
/* 2532 */       module.background.setValue(prevBackground);
/*      */     } 
/*      */ 
/*      */     
/* 2536 */     float hudX = module.getHudX();
/* 2537 */     float hudY = module.getHudY();
/* 2538 */     float hudScale = module.getRenderHudScale();
/*      */ 
/*      */     
/* 2541 */     int rightEdgeLocal = Math.round(baseWidth);
/* 2542 */     int textLeftLocal = 2;
/* 2543 */     int verticalPosLocal = entryCount * 9;
/* 2544 */     boolean gradientBackground = module.isGradientPreset();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2549 */     if (module.shouldUseVanillaColors()) {
/* 2550 */       targetTitleBgColor = client.field_1690.method_19345(0.4F);
/* 2551 */       targetRowBgColor = client.field_1690.method_19345(0.3F);
/*      */     } else {
/* 2553 */       targetTitleBgColor = module.getResolvedBackgroundColor(client);
/* 2554 */       targetRowBgColor = targetTitleBgColor;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2559 */     if (!scoreboardBackgroundColorsInitialized) {
/* 2560 */       scoreboardAnimatedTitleColor = targetTitleBgColor;
/* 2561 */       scoreboardAnimatedRowColor = targetRowBgColor;
/* 2562 */       scoreboardBackgroundColorsInitialized = true;
/*      */     } else {
/* 2564 */       scoreboardAnimatedTitleColor = approachColorExp(scoreboardAnimatedTitleColor, targetTitleBgColor, 12.0F, deltaSeconds);
/* 2565 */       scoreboardAnimatedRowColor = approachColorExp(scoreboardAnimatedRowColor, targetRowBgColor, 12.0F, deltaSeconds);
/*      */     } 
/* 2567 */     int titleBgColor = scoreboardAnimatedTitleColor;
/* 2568 */     int rowBgColor = scoreboardAnimatedRowColor;
/*      */ 
/*      */     
/* 2571 */     context.method_51448().method_22903();
/* 2572 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 2573 */     context.method_51448().method_22903();
/* 2574 */     context.method_51448().method_46416(hudX, hudY, 400.0F);
/* 2575 */     context.method_51448().method_22905(hudScale, hudScale, 1.0F);
/*      */ 
/*      */     
/* 2578 */     context.method_51448().method_46416(0.0F, topInset, 0.0F);
/*      */ 
/*      */     
/* 2581 */     if (module.background.isValue()) {
/* 2582 */       int backgroundTopLocal = -topInset;
/* 2583 */       int backgroundBottomLocal = verticalPosLocal;
/*      */       
/* 2585 */       float blurRadius = Math.max(0.0F, module.backgroundBlurRadius.getValue());
/* 2586 */       if (blurRadius > 0.0F) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2591 */         float safeScale = Math.max(hudScale, 1.0F);
/* 2592 */         float normalizedBlurRadius = blurRadius / safeScale;
/* 2593 */         float blurQuality = getOptimizedHudBlurQuality(normalizedBlurRadius);
/* 2594 */         float blurWidth = rightEdgeLocal;
/* 2595 */         float blurHeight = (backgroundBottomLocal - backgroundTopLocal);
/* 2596 */         long blurStateKey = makeHudBlurStateKey(normalizedBlurRadius, safeScale, 0.0F, backgroundTopLocal, blurWidth, blurHeight);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2604 */         Blur.INSTANCE.registerHudBlurState(14, blurStateKey);
/* 2605 */         context.method_51452();
/* 2606 */         if (blurQuality > 0.0F && blurWidth > 1.5F && blurHeight > 1.5F) {
/* 2607 */           Blur.INSTANCE.renderCached(ShapeProperties.create(context.method_51448(), 0.0D, backgroundTopLocal, blurWidth, blurHeight)
/*      */               
/* 2609 */               .round(getClampedHudCornerRadius(module.cornerRounding.getValue(), blurWidth, blurHeight))
/* 2610 */               .softness(0.0F)
/* 2611 */               .quality(blurQuality)
/* 2612 */               .color(-1)
/* 2613 */               .build());
/*      */         }
/* 2615 */         renderScoreboardBackground(module, context, gradientBackground, topInset, rightEdgeLocal, verticalPosLocal, titleBgColor, rowBgColor);
/*      */         
/* 2617 */         context.method_51452();
/*      */       } else {
/* 2619 */         renderScoreboardBackground(module, context, gradientBackground, topInset, rightEdgeLocal, verticalPosLocal, titleBgColor, rowBgColor);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2625 */     if (module.showTitle.isValue()) {
/* 2626 */       float titleX = (rightEdgeLocal - titleWidth) / 2.0F;
/* 2627 */       class_327 textRenderer = client.field_1772;
/* 2628 */       context.method_51439(textRenderer, title, (int)titleX, -topInset + 1, -1, false);
/*      */     } 
/*      */ 
/*      */     
/* 2632 */     for (int i = 0; i < entryCount; i++) {
/* 2633 */       ScoreboardSidebarEntry sidebarEntry = sidebarEntries.get(i);
/* 2634 */       int rowY = verticalPosLocal - (entryCount - i) * 9;
/*      */       
/* 2636 */       class_327 textRenderer = client.field_1772;
/* 2637 */       context.method_51439(textRenderer, sidebarEntry.name(), textLeftLocal, rowY, -1, false);
/*      */       
/* 2639 */       if (module.showNumbers.isValue() && sidebarEntry.hasScore() && (
/* 2640 */         module.showZeros.isValue() || !phaze$isZeroScoreText(sidebarEntry.score()))) {
/*      */ 
/*      */         
/* 2643 */         float scoreWidth = sidebarEntry.scoreWidth();
/* 2644 */         float scoreX = rightEdgeLocal - 2.0F - scoreWidth;
/* 2645 */         context.method_51439(textRenderer, sidebarEntry.score(), (int)scoreX, rowY, -1, false);
/*      */       } 
/*      */     } 
/*      */     
/* 2649 */     context.method_51448().method_22909();
/* 2650 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderScoreboardBackground(ScoreboardHud module, class_332 context, boolean gradientBackground, int topInset, int rightEdgeLocal, int verticalPosLocal, int titleBgColor, int rowBgColor) {
/* 2664 */     if (gradientBackground) {
/* 2665 */       if (module.showTitle.isValue()) {
/* 2666 */         renderGradientRect(context, 0.0F, -topInset, rightEdgeLocal, topInset - 1.0F, 0, module
/* 2667 */             .getResolvedGradientStartColor(), module.getResolvedGradientEndColor(), module
/* 2668 */             .getGradientDirection(), module.getGradientAnimationOffset(System.currentTimeMillis()), module
/* 2669 */             .hasActiveAnimatedBackground(), module.cornerRounding.getValue());
/*      */       }
/* 2671 */       renderGradientRect(context, 0.0F, -1.0F, rightEdgeLocal, verticalPosLocal + 1.0F, 0, module
/* 2672 */           .getResolvedGradientStartColor(), module.getResolvedGradientEndColor(), module
/* 2673 */           .getGradientDirection(), module.getGradientAnimationOffset(System.currentTimeMillis()), module
/* 2674 */           .hasActiveAnimatedBackground(), module.cornerRounding.getValue());
/* 2675 */       context.method_51452();
/*      */       return;
/*      */     } 
/* 2678 */     if (module.showTitle.isValue()) {
/* 2679 */       renderSolidHudBackground(context, 0.0F, -topInset, rightEdgeLocal, topInset - 1.0F, titleBgColor, module.cornerRounding
/* 2680 */           .getValue(), (module.backgroundBlurRadius.getValue() <= 0.0F));
/*      */     }
/* 2682 */     renderSolidHudBackground(context, 0.0F, -1.0F, rightEdgeLocal, verticalPosLocal + 1.0F, rowBgColor, module.cornerRounding
/* 2683 */         .getValue(), (module.backgroundBlurRadius.getValue() <= 0.0F));
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private static boolean phaze$shouldBypassHudBatchingForCurrentScreen(class_310 client) {
/* 2688 */     return (client != null && client.field_1687 != null && client.field_1755 != null && !(client.field_1755 instanceof net.minecraft.class_408) && !(client.field_1755 instanceof vorga.phazeclient.implement.menu.MenuScreen));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Unique
/*      */   private static boolean phaze$isZeroScoreText(class_2561 scoreText) {
/* 2697 */     if (scoreText == null) {
/* 2698 */       return false;
/*      */     }
/* 2700 */     String raw = scoreText.getString();
/* 2701 */     if (raw == null || raw.isEmpty()) {
/* 2702 */       return false;
/*      */     }
/*      */     
/* 2705 */     boolean sawDigit = false;
/* 2706 */     for (int i = 0; i < raw.length(); i++) {
/* 2707 */       char c = raw.charAt(i);
/* 2708 */       if (!Character.isWhitespace(c))
/*      */       {
/*      */         
/* 2711 */         if (Character.isDigit(c)) {
/* 2712 */           sawDigit = true;
/* 2713 */           if (c != '0') {
/* 2714 */             return false;
/*      */           }
/*      */         } else {
/*      */           
/* 2718 */           return false;
/*      */         }  } 
/* 2720 */     }  return sawDigit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderDirectionHud(class_332 context, class_310 client, DirectionHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2738 */     if (!module.isEnabled() || client.field_1724 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2742 */     float baseWidth = module.hudLength.getValue();
/* 2743 */     float baseHeight = 52.0F;
/* 2744 */     renderRectHud(context, client, (RectHudModule)module, "", 9, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight);
/*      */ 
/*      */     
/* 2747 */     float targetYaw = wrap360(client.field_1724.method_5705(0.0F));
/* 2748 */     if (Float.isNaN(directionDisplayYaw)) {
/* 2749 */       directionDisplayYaw = targetYaw;
/*      */     }
/* 2751 */     float speed = class_3532.method_15363(module.smoothness.getValue(), 1.0F, 20.0F);
/* 2752 */     directionDisplayYaw = approachAngle(directionDisplayYaw, targetYaw, deltaSeconds * speed * 6.0F);
/*      */     
/* 2754 */     float x = module.getHudX();
/* 2755 */     float y = module.getHudY();
/* 2756 */     float scale = module.getRenderHudScale();
/* 2757 */     float centerX = baseWidth * 0.5F;
/* 2758 */     float leftPadding = 8.0F;
/* 2759 */     float rightPadding = 8.0F;
/* 2760 */     float visibleWidth = Math.max(64.0F, baseWidth - leftPadding - rightPadding);
/* 2761 */     float degreesPerPixel = 90.0F / visibleWidth;
/*      */ 
/*      */     
/* 2764 */     float yawNumberY = 1.0F;
/* 2765 */     float triangleY = 11.0F;
/* 2766 */     float majorTickTop = 14.0F;
/* 2767 */     float majorTickBottom = 28.0F;
/* 2768 */     float minorTickTop = 17.0F;
/* 2769 */     float minorTickBottom = 26.0F;
/* 2770 */     float labelY = 30.0F;
/* 2771 */     float baselineY = 28.0F;
/*      */     
/* 2773 */     context.method_51448().method_22903();
/* 2774 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 2775 */     context.method_25294(Math.round(x + leftPadding), Math.round(y + baselineY), Math.round(x + baseWidth - rightPadding), Math.round(y + baselineY + 1.0F), withAlpha(16777215, 30));
/*      */     
/*      */     int deg;
/* 2778 */     for (deg = 0; deg < 360; deg += 15) {
/* 2779 */       float markYaw = deg;
/* 2780 */       float delta = shortestAngleDeg(markYaw, directionDisplayYaw);
/* 2781 */       float px = centerX + delta / degreesPerPixel;
/* 2782 */       if (px >= leftPadding && px <= baseWidth - rightPadding) {
/*      */         
/* 2784 */         int alphaCenter = class_3532.method_15340(Math.round(255.0F * directionEdgeFade(px, baseWidth)), 0, 255);
/* 2785 */         if (alphaCenter > 4) {
/*      */ 
/*      */ 
/*      */           
/* 2789 */           String label = directionLabel(deg, module.showIntermediate.isValue());
/* 2790 */           if (label.isEmpty()) {
/* 2791 */             int minorHalf = 0;
/* 2792 */             context.method_25294(
/* 2793 */                 Math.round(x + px - minorHalf), 
/* 2794 */                 Math.round(y + minorTickTop), 
/* 2795 */                 Math.round(x + px + minorHalf + 1.0F), 
/* 2796 */                 Math.round(y + minorTickBottom), 
/* 2797 */                 withAlpha(16777215, Math.round(130.0F * alphaCenter / 255.0F)));
/*      */           } else {
/*      */             
/* 2800 */             int majorHalf = 0;
/* 2801 */             context.method_25294(
/* 2802 */                 Math.round(x + px - majorHalf), 
/* 2803 */                 Math.round(y + majorTickTop), 
/* 2804 */                 Math.round(x + px + majorHalf + 1.0F), 
/* 2805 */                 Math.round(y + majorTickBottom), 
/* 2806 */                 withAlpha(16777215, Math.round(210.0F * alphaCenter / 255.0F)));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2812 */     for (deg = 0; deg < 360; deg += 15) {
/* 2813 */       float markYaw = deg;
/* 2814 */       float delta = shortestAngleDeg(markYaw, directionDisplayYaw);
/* 2815 */       float px = centerX + delta / degreesPerPixel;
/* 2816 */       if (px >= leftPadding && px <= baseWidth - rightPadding) {
/*      */         
/* 2818 */         String label = directionLabel(deg, module.showIntermediate.isValue());
/* 2819 */         if (!label.isEmpty()) {
/*      */ 
/*      */           
/* 2822 */           int alphaCenter = class_3532.method_15340(Math.round(255.0F * directionEdgeFade(px, baseWidth)), 0, 255);
/* 2823 */           if (alphaCenter > 4)
/*      */           
/*      */           { 
/*      */             
/* 2827 */             float tw = getHudTextWidth(client, label, 8.0F);
/* 2828 */             float textLeft = px - tw * 0.5F;
/* 2829 */             float textRight = textLeft + tw;
/* 2830 */             int alphaLeft = class_3532.method_15340(Math.round(255.0F * directionEdgeFade(textLeft + 1.0F, baseWidth)), 0, 255);
/* 2831 */             int alphaRight = class_3532.method_15340(Math.round(255.0F * directionEdgeFade(textRight - 1.0F, baseWidth)), 0, 255);
/* 2832 */             int alpha = Math.min(alphaCenter, Math.min(alphaLeft, alphaRight));
/* 2833 */             if (alpha > 4)
/*      */             {
/*      */ 
/*      */               
/* 2837 */               if (textRight >= leftPadding && textLeft <= baseWidth - rightPadding)
/*      */               {
/*      */                 
/* 2840 */                 renderScaledHudTextWithAlpha(context, client, label, x, y, textLeft, labelY, 8.0F, scale, module.textShadow.isValue(), alpha); }  }  } 
/*      */         } 
/*      */       } 
/* 2843 */     }  if (module.showDegreeNumber.isValue()) {
/* 2844 */       String degree = String.valueOf(Math.round(directionDisplayYaw));
/* 2845 */       float tw = getHudTextWidth(client, degree, 8.0F);
/* 2846 */       renderScaledHudText(context, client, degree, x, y, centerX - tw * 0.5F, yawNumberY, 8.0F, scale, module.textShadow.isValue());
/* 2847 */       int triSize = 18;
/* 2848 */       renderScaledHudTexture(context, x, y, centerX - triSize * 0.5F, triangleY, triSize, triSize, scale);
/*      */     } 
/* 2850 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderScaledHudTexture(class_332 context, float hudX, float hudY, float localX, float localY, int width, int height, float scale) {
/* 2863 */     context.method_51448().method_22903();
/* 2864 */     context.method_51448().method_46416(hudX, hudY, 425.0F);
/* 2865 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 2866 */     context.method_25290(class_1921::method_62277, DIRECTION_TRIANGLE_TEXTURE, Math.round(localX), Math.round(localY), 0.0F, 0.0F, width, height, width, height);
/* 2867 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderKeystrokesHud(class_332 context, class_310 client, KeystrokesHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2885 */     if (!module.isEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 2889 */     float baseWidth = 54.0F;
/* 2890 */     float baseHeight = 63.0F;
/* 2891 */     renderRectHud(context, client, (RectHudModule)module, "", 6, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight);
/*      */ 
/*      */     
/* 2894 */     float x = module.getHudX();
/* 2895 */     float y = module.getHudY();
/* 2896 */     float scale = module.getRenderHudScale();
/*      */ 
/*      */     
/* 2899 */     int idleColor = module.background.isValue() ? RECT_BG_ANIMATED_COLOR[6] : 0;
/*      */     
/* 2901 */     context.method_51448().method_22903();
/* 2902 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 2903 */     context.method_51448().method_22903();
/* 2904 */     context.method_51448().method_46416(x, y, 420.0F);
/* 2905 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 2906 */     renderKeystrokeButtonBlur(context, module, scale);
/* 2907 */     float cachedProgressScale = inBatchPass ? 0.0F : 1.0F;
/* 2908 */     if (module.background.isValue() && module.isGradientPreset()) {
/* 2909 */       renderGradientKeyButton(context, module, 20, 0, 16, 18, KEYSTROKE_PROGRESS[0] * cachedProgressScale);
/* 2910 */       renderGradientKeyButton(context, module, 0, 19, 18, 18, KEYSTROKE_PROGRESS[1] * cachedProgressScale);
/* 2911 */       renderGradientKeyButton(context, module, 19, 19, 16, 18, KEYSTROKE_PROGRESS[2] * cachedProgressScale);
/* 2912 */       renderGradientKeyButton(context, module, 36, 19, 18, 18, KEYSTROKE_PROGRESS[3] * cachedProgressScale);
/* 2913 */       renderGradientKeyButton(context, module, 0, 38, 54, 8, KEYSTROKE_PROGRESS[6] * cachedProgressScale);
/* 2914 */       renderGradientKeyButton(context, module, 0, 47, 26, 16, KEYSTROKE_PROGRESS[4] * cachedProgressScale);
/* 2915 */       renderGradientKeyButton(context, module, 28, 47, 26, 16, KEYSTROKE_PROGRESS[5] * cachedProgressScale);
/*      */     } else {
/* 2917 */       renderKeyButton(context, module, 20, 0, 16, 18, idleColor, KEYSTROKE_PROGRESS[0] * cachedProgressScale);
/* 2918 */       renderKeyButton(context, module, 0, 19, 18, 18, idleColor, KEYSTROKE_PROGRESS[1] * cachedProgressScale);
/* 2919 */       renderKeyButton(context, module, 19, 19, 16, 18, idleColor, KEYSTROKE_PROGRESS[2] * cachedProgressScale);
/* 2920 */       renderKeyButton(context, module, 36, 19, 18, 18, idleColor, KEYSTROKE_PROGRESS[3] * cachedProgressScale);
/* 2921 */       renderKeyButton(context, module, 0, 38, 54, 8, idleColor, KEYSTROKE_PROGRESS[6] * cachedProgressScale);
/* 2922 */       renderKeyButton(context, module, 0, 47, 26, 16, idleColor, KEYSTROKE_PROGRESS[4] * cachedProgressScale);
/* 2923 */       renderKeyButton(context, module, 28, 47, 26, 16, idleColor, KEYSTROKE_PROGRESS[5] * cachedProgressScale);
/*      */     } 
/* 2925 */     context.method_51452();
/* 2926 */     context.method_51448().method_22909();
/*      */     
/* 2928 */     renderKeyLabel(context, client, "W", x, y, 20.0F, 4.0F, 16.0F, KEYSTROKE_PROGRESS[0] * cachedProgressScale, scale, module.textShadow.isValue());
/* 2929 */     renderKeyLabel(context, client, "A", x, y, 0.0F, 23.0F, 18.0F, KEYSTROKE_PROGRESS[1] * cachedProgressScale, scale, module.textShadow.isValue());
/* 2930 */     renderKeyLabel(context, client, "S", x, y, 19.0F, 23.0F, 16.0F, KEYSTROKE_PROGRESS[2] * cachedProgressScale, scale, module.textShadow.isValue());
/* 2931 */     renderKeyLabel(context, client, "D", x, y, 36.0F, 23.0F, 18.0F, KEYSTROKE_PROGRESS[3] * cachedProgressScale, scale, module.textShadow.isValue());
/* 2932 */     renderSpacebarLabel(context, x, y, 0.0F, 38.0F, 54.0F, 8.0F, KEYSTROKE_PROGRESS[6] * cachedProgressScale, scale);
/* 2933 */     renderKeyLabel(context, client, "LMB", x, y, 0.0F, 50.0F, 26.0F, KEYSTROKE_PROGRESS[4] * cachedProgressScale, scale, module.textShadow.isValue());
/* 2934 */     renderKeyLabel(context, client, "RMB", x, y, 28.0F, 50.0F, 26.0F, KEYSTROKE_PROGRESS[5] * cachedProgressScale, scale, module.textShadow.isValue());
/* 2935 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderPotionHud(class_332 context, class_310 client, PotionHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 2953 */     if (!module.isEnabled() || client.field_1724 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2957 */     List<class_1293> updatedEffects = new ArrayList<>(client.field_1724.method_6026());
/* 2958 */     boolean updatedSample = (chatEditing && updatedEffects.isEmpty());
/* 2959 */     long previewCycle = updatedSample ? (System.currentTimeMillis() / 3000L) : Long.MIN_VALUE;
/* 2960 */     if (updatedSample && potionSampleCycle != previewCycle) {
/* 2961 */       potionSampleIndex = POTION_PREVIEW_RANDOM.nextInt(POTION_PREVIEW_SAMPLES.length);
/*      */     }
/* 2963 */     if (!potionCacheInitialized || !chatEditing || !updatedSample || !potionSampleCache || potionSampleCycle != previewCycle) {
/*      */       
/* 2965 */       updatedEffects.sort(Comparator.comparing(effect -> ((class_1291)effect.method_5579().comp_349()).method_5560().getString()));
/* 2966 */       List<String> updatedNames = new ArrayList<>();
/* 2967 */       List<String> updatedDurations = new ArrayList<>();
/* 2968 */       if (updatedSample) {
/* 2969 */         String[] preview = POTION_PREVIEW_SAMPLES[potionSampleIndex];
/* 2970 */         updatedNames.add(preview[0]);
/* 2971 */         updatedDurations.add(preview[1]);
/*      */       } else {
/* 2973 */         for (class_1293 effect : updatedEffects) {
/* 2974 */           updatedNames.add(getEffectName(effect));
/* 2975 */           updatedDurations.add(formatEffectDuration(effect));
/*      */         } 
/*      */       } 
/* 2978 */       potionEffectsCache = updatedEffects;
/* 2979 */       potionNamesCache = updatedNames;
/* 2980 */       potionDurationsCache = updatedDurations;
/* 2981 */       potionSampleCache = updatedSample;
/* 2982 */       potionSampleCycle = previewCycle;
/* 2983 */       potionCacheInitialized = true;
/*      */     } 
/*      */     
/* 2986 */     List<class_1293> effects = potionEffectsCache;
/* 2987 */     boolean sample = potionSampleCache;
/* 2988 */     if (effects.isEmpty() && !sample) {
/*      */       return;
/*      */     }
/*      */     
/* 2992 */     float rowHeight = 24.0F;
/* 2993 */     float iconSize = 18.0F;
/* 2994 */     float paddingX = 6.0F;
/* 2995 */     float paddingY = 4.0F;
/*      */ 
/*      */ 
/*      */     
/* 2999 */     float textX = paddingX + iconSize + 3.0F;
/* 3000 */     int rows = sample ? 1 : effects.size();
/* 3001 */     float maxTextWidth = 0.0F;
/* 3002 */     for (int i = 0; i < rows; i++) {
/* 3003 */       maxTextWidth = Math.max(maxTextWidth, getHudTextWidth(client, potionNamesCache.get(i), 8.0F));
/* 3004 */       maxTextWidth = Math.max(maxTextWidth, getHudTextWidth(client, potionDurationsCache.get(i), 8.0F));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3010 */     float baseWidth = Math.max(91.0F, textX + maxTextWidth + paddingX - 3.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3017 */     float baseHeight = paddingY * 2.0F + rows * rowHeight;
/* 3018 */     context.method_51448().method_22903();
/* 3019 */     context.method_51448().method_46416(0.0F, 1.0F, 0.0F);
/* 3020 */     renderRectHud(context, client, (RectHudModule)module, "", 7, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight - 4.0F);
/*      */     
/* 3022 */     context.method_51448().method_22909();
/*      */     
/* 3024 */     float x = module.getHudX();
/* 3025 */     float y = module.getHudY();
/* 3026 */     float scale = module.getRenderHudScale();
/* 3027 */     context.method_51448().method_22903();
/* 3028 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/*      */     int j;
/* 3030 */     for (j = 0; j < rows; j++) {
/* 3031 */       float rowY = paddingY + j * rowHeight;
/* 3032 */       context.method_51448().method_22903();
/* 3033 */       context.method_51448().method_46416(x, y, 425.0F);
/* 3034 */       context.method_51448().method_22905(scale, scale, 1.0F);
/* 3035 */       if (sample) {
/* 3036 */         context.method_51427(new class_1799((class_1935)class_1802.field_8574), Math.round(paddingX), Math.round(rowY + 2.0F));
/*      */       } else {
/* 3038 */         class_1058 sprite = client.method_18505().method_18663(((class_1293)effects.get(j)).method_5579());
/* 3039 */         context.method_52709(class_1921::method_62277, sprite, Math.round(paddingX), Math.round(rowY + 2.0F), Math.round(iconSize), Math.round(iconSize));
/*      */       } 
/* 3041 */       context.method_51452();
/* 3042 */       context.method_51448().method_22909();
/*      */     } 
/* 3044 */     for (j = 0; j < rows; j++) {
/* 3045 */       float rowY = paddingY + j * rowHeight;
/* 3046 */       String name = potionNamesCache.get(j);
/* 3047 */       String duration = potionDurationsCache.get(j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3056 */       int nameColor = resolveHudTextColor();
/* 3057 */       if (module.colorByType.isValue() && !sample && j < potionEffectsCache.size()) {
/* 3058 */         class_1293 effect = potionEffectsCache.get(j);
/* 3059 */         class_4081 cat = ((class_1291)effect.method_5579().comp_349()).method_18792();
/* 3060 */         if (cat == class_4081.field_18271) { nameColor = -11141291; }
/* 3061 */         else if (cat == class_4081.field_18272) { nameColor = -43691; }
/*      */       
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3067 */       if (module.flashOnExpiry.isValue() && !sample && j < potionEffectsCache.size()) {
/* 3068 */         class_1293 effect = potionEffectsCache.get(j);
/* 3069 */         if (!effect.method_48559() && effect.method_5584() < 200) {
/* 3070 */           float pulse = (float)(0.5D + 0.5D * Math.sin(System.nanoTime() / 2.0E8D));
/* 3071 */           int origAlpha = nameColor >>> 24 & 0xFF;
/* 3072 */           int newAlpha = (int)(origAlpha * (0.45F + 0.55F * pulse));
/* 3073 */           nameColor = newAlpha << 24 | nameColor & 0xFFFFFF;
/*      */         } 
/*      */       } 
/*      */       
/* 3077 */       renderScaledHudTextColored(context, client, name, x, y, textX, rowY + 2.0F, 8.0F, scale, module.textShadow.isValue(), nameColor);
/* 3078 */       renderScaledHudText(context, client, duration, x, y, textX, rowY + 12.0F, 8.0F, scale, module.textShadow.isValue());
/*      */     } 
/*      */     
/* 3081 */     context.method_51448().method_22909();
/*      */   }
/*      */   
/*      */   private static String formatDurabilityText(ArmorHud module, class_1799 stack) {
/* 3085 */     if (!stack.method_7963()) {
/* 3086 */       return "-";
/*      */     }
/* 3088 */     int remaining = Math.max(0, stack.method_7936() - stack.method_7919());
/* 3089 */     return module.formatDurability(remaining, stack.method_7936());
/*      */   }
/*      */   
/*      */   private static float getTextHudBaseWidth(class_310 client, String text) {
/* 3093 */     float width = Math.max(44.0F, getHudTextWidth(client, text, 8.0F) + 14.0F);
/*      */     
/* 3095 */     if (text.contains("A") || text.contains("a")) {
/* 3096 */       width += 2.0F;
/*      */     }
/* 3098 */     return width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getSprintHudText(class_310 client) {
/* 3105 */     if (client.field_1724 == null || client.field_1690 == null) {
/* 3106 */       return "Not Sprinting";
/*      */     }
/* 3108 */     if ((client.field_1724.method_31549()).field_7479 && client.field_1690.field_1832.method_1434()) {
/* 3109 */       return "Flying Descending";
/*      */     }
/* 3111 */     if ((client.field_1724.method_31549()).field_7479) {
/* 3112 */       return "Flying";
/*      */     }
/* 3114 */     if (client.field_1690.field_1832.method_1434() || client.field_1724.method_5715()) {
/* 3115 */       return "Sneaking (Key Held)";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3124 */     AutoSprint autoSprint = AutoSprint.getInstance();
/* 3125 */     boolean autoSprintActive = (autoSprint.isEnabled() && autoSprint.showInSprintHud.isValue());
/* 3126 */     if (client.field_1690.field_1867.method_1434()) {
/* 3127 */       return autoSprintActive ? "Sprinting (AutoSprint)" : "Sprinting (Key Held)";
/*      */     }
/* 3129 */     if (client.field_1724.method_5624()) {
/* 3130 */       return autoSprintActive ? "Sprinting (AutoSprint)" : "Sprinting (Vanilla)";
/*      */     }
/* 3132 */     return "Not Sprinting";
/*      */   }
/*      */   
/*      */   private static String getDayCounterText(class_310 client) {
/* 3136 */     DayCounterHud module = DayCounterHud.getInstance();
/* 3137 */     if (client.field_1687 == null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 3142 */       return module.reverseOrder.isValue() ? "Day: 0" : "0 Days";
/*      */     }
/* 3144 */     long days = Math.max(0L, client.field_1687.method_8510() / 24000L) + 1L;
/* 3145 */     if (module.reverseOrder.isValue())
/*      */     {
/*      */ 
/*      */       
/* 3149 */       return "Day: " + days;
/*      */     }
/* 3151 */     return "" + days + days;
/*      */   }
/*      */   private static String getTimeHudText(TimeHud module, class_310 client) {
/*      */     String pattern;
/* 3155 */     LocalTime now = LocalTime.now();
/* 3156 */     boolean is24 = module.hour24.isValue();
/* 3157 */     boolean wantSeconds = module.showSeconds.isValue();
/*      */     
/* 3159 */     if (is24) {
/* 3160 */       pattern = wantSeconds ? "H:mm:ss" : "H:mm";
/* 3161 */     } else if (module.showAmPm.isValue()) {
/* 3162 */       pattern = wantSeconds ? "h:mm:ss a" : "h:mm a";
/*      */     } else {
/* 3164 */       pattern = wantSeconds ? "h:mm:ss" : "h:mm";
/*      */     } 
/* 3166 */     String time = now.format(DateTimeFormatter.ofPattern(pattern, Locale.US));
/* 3167 */     if (module.showPhase.isValue() && client != null && client.field_1687 != null) {
/* 3168 */       TimeHud.Phase phase = module.phaseForTime(client.field_1687.method_8532());
/* 3169 */       String label = module.colorPhase.isValue() ? (phase.colorCode + phase.colorCode + "§r") : phase.label;
/* 3170 */       return time + " " + time;
/*      */     } 
/* 3172 */     return time;
/*      */   }
/*      */   
/*      */   private static String getSessionText(SessionTimeHud module) {
/* 3176 */     long elapsed = Math.max(0L, System.currentTimeMillis() - SESSION_START_MS);
/* 3177 */     long ms = elapsed % 1000L;
/* 3178 */     long totalSec = elapsed / 1000L;
/* 3179 */     long sec = totalSec % 60L;
/* 3180 */     long totalMin = totalSec / 60L;
/* 3181 */     long min = totalMin % 60L;
/* 3182 */     long hours = totalMin / 60L;
/* 3183 */     String mode = module.displayOption.getSelected();
/* 3184 */     switch (mode) { case "12h 34m 56s": case "123.456s": case "12:34": case "12:34:56.789":  }  return 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3189 */       String.format(Locale.US, "%02d:%02d:%02d", new Object[] { Long.valueOf(hours), Long.valueOf(min), Long.valueOf(sec) });
/*      */   }
/*      */ 
/*      */   
/*      */   private static float wrap360(float angle) {
/* 3194 */     float a = angle % 360.0F;
/* 3195 */     if (a < 0.0F) a += 360.0F; 
/* 3196 */     return a;
/*      */   }
/*      */   
/*      */   private static String wrapTextWithBrackets(String text, RectHudModule module) {
/* 3200 */     if (module != null && !module.background.isValue() && module.showBrackets.isValue()) {
/* 3201 */       return "[" + text + "]";
/*      */     }
/* 3203 */     return text;
/*      */   }
/*      */   
/*      */   private static float shortestAngleDeg(float target, float current) {
/* 3207 */     float diff = (target - current) % 360.0F;
/* 3208 */     if (diff > 180.0F) diff -= 360.0F; 
/* 3209 */     if (diff < -180.0F) diff += 360.0F; 
/* 3210 */     return diff;
/*      */   }
/*      */   
/*      */   private static float approachAngle(float current, float target, float step) {
/* 3214 */     float diff = shortestAngleDeg(target, current);
/* 3215 */     return wrap360(current + diff * class_3532.method_15363(step, 0.0F, 1.0F));
/*      */   }
/*      */   
/*      */   private static String directionLabel(int deg, boolean showIntermediate) {
/* 3219 */     switch (deg) { case 0: case 45: return 
/*      */           
/* 3221 */           showIntermediate ? "SE" : "";
/*      */       case 90: 
/* 3223 */       case 135: return showIntermediate ? "SW" : "";
/*      */       case 180: 
/* 3225 */       case 225: return showIntermediate ? "NW" : "";
/*      */       case 270: 
/* 3227 */       case 315: return showIntermediate ? "NE" : ""; }
/* 3228 */      return String.valueOf(deg);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getBiomeName(class_310 client, class_2338 pos) {
/* 3233 */     if (client.field_1687 == null) {
/* 3234 */       return "Unknown";
/*      */     }
/* 3236 */     return client.field_1687.method_23753(pos).method_40230()
/* 3237 */       .map(key -> toTitleCase(key.method_29177().method_12832()))
/* 3238 */       .orElse("Unknown");
/*      */   }
/*      */   
/*      */   private static int getBiomeColor(class_310 client, class_2338 pos) {
/* 3242 */     if (client.field_1687 == null) {
/* 3243 */       return 16777045;
/*      */     }
/*      */ 
/*      */     
/* 3247 */     String biomeId = client.field_1687.method_23753(pos).method_40230().map(key -> key.method_29177().method_12832()).orElse("");
/* 3248 */     switch (biomeId) { case "river": case "frozen_river": case "ocean": case "deep_ocean": case "cold_ocean": case "deep_cold_ocean": case "lukewarm_ocean": case "deep_lukewarm_ocean": case "warm_ocean": case "frozen_ocean": case "deep_frozen_ocean": case "desert": case "beach": case "snowy_beach": case "plains": case "sunflower_plains": case "meadow": case "forest": case "flower_forest": case "birch_forest": case "old_growth_birch_forest": case "dark_forest": case "taiga": case "old_growth_pine_taiga": case "old_growth_spruce_taiga": case "snowy_taiga": case "snowy_plains": case "ice_spikes": case "snowy_slopes": case "frozen_peaks": case "jungle": case "bamboo_jungle": case "sparse_jungle": case "savanna": case "savanna_plateau": case "windswept_savanna": case "badlands": case "eroded_badlands": case "wooded_badlands": case "swamp": case "mangrove_swamp": case "mushroom_fields": case "stony_shore": case "windswept_hills": case "windswept_gravelly_hills": case "windswept_forest": case "stony_peaks": case "grove": case "jagged_peaks": case "cherry_grove": case "the_nether": case "nether_wastes": case "soul_sand_valley": case "crimson_forest": case "warped_forest": case "basalt_deltas": case "the_end": case "end_highlands": case "end_midlands": case "end_barrens": case "small_end_islands": case "the_void": case "deep_dark": case "lush_caves": case "dripstone_caves":  }  return 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3282 */       16777045;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String toTitleCase(String value) {
/* 3287 */     String[] parts = value.replace('_', ' ').split(" ");
/* 3288 */     StringBuilder builder = new StringBuilder();
/* 3289 */     for (String part : parts) {
/* 3290 */       if (!part.isEmpty()) {
/*      */ 
/*      */         
/* 3293 */         if (!builder.isEmpty()) {
/* 3294 */           builder.append(' ');
/*      */         }
/* 3296 */         builder.append(Character.toUpperCase(part.charAt(0)));
/* 3297 */         if (part.length() > 1)
/* 3298 */           builder.append(part.substring(1)); 
/*      */       } 
/*      */     } 
/* 3301 */     return builder.isEmpty() ? value : builder.toString();
/*      */   }
/*      */   
/*      */   private static String getFacingShort(class_2350 direction) {
/* 3305 */     switch (direction) { case field_11043: case field_11035: case field_11034: case field_11039:  }  return 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3310 */       "";
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getFacing8Point(float yaw) {
/* 3315 */     float normalized = (yaw % 360.0F + 360.0F) % 360.0F;
/* 3316 */     if (normalized >= 337.5F || normalized < 22.5F) return "S"; 
/* 3317 */     if (normalized < 67.5F) return "SW"; 
/* 3318 */     if (normalized < 112.5F) return "W"; 
/* 3319 */     if (normalized < 157.5F) return "NW"; 
/* 3320 */     if (normalized < 202.5F) return "N"; 
/* 3321 */     if (normalized < 247.5F) return "NE"; 
/* 3322 */     if (normalized < 292.5F) return "E"; 
/* 3323 */     return "SE";
/*      */   }
/*      */   
/*      */   private static String getDirectionXSign(String facing) {
/* 3327 */     switch (facing) { case "E": case "NE": case "SE": case "W": case "NW": case "SW":  }  return 
/*      */ 
/*      */       
/* 3330 */       "";
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getDirectionZSign(String facing) {
/* 3335 */     switch (facing) { case "S": case "SE": case "SW": case "N": case "NE": case "NW":  }  return 
/*      */ 
/*      */       
/* 3338 */       "";
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getServerPing(class_310 client) {
/* 3343 */     if (client.method_1562() == null || client.field_1724 == null) {
/* 3344 */       return 0;
/*      */     }
/* 3346 */     class_640 entry = client.method_1562().method_2871(client.field_1724.method_5667());
/* 3347 */     return (entry == null) ? 0 : Math.max(0, entry.method_2959());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateKeystrokeAnimations(class_310 client, boolean leftMouseDown, boolean rightMouseDown, float deltaSeconds) {
/* 3356 */     if (client == null || client.field_1690 == null) {
/*      */       return;
/*      */     }
/* 3359 */     updateKeystroke(0, client.field_1690.field_1894.method_1434(), deltaSeconds);
/* 3360 */     updateKeystroke(1, client.field_1690.field_1913.method_1434(), deltaSeconds);
/* 3361 */     updateKeystroke(2, client.field_1690.field_1881.method_1434(), deltaSeconds);
/* 3362 */     updateKeystroke(3, client.field_1690.field_1849.method_1434(), deltaSeconds);
/* 3363 */     updateKeystroke(4, (leftMouseDown && client.field_1755 == null), deltaSeconds);
/* 3364 */     updateKeystroke(5, (rightMouseDown && client.field_1755 == null), deltaSeconds);
/* 3365 */     updateKeystroke(6, client.field_1690.field_1903.method_1434(), deltaSeconds);
/*      */   }
/*      */   
/*      */   private static void updateKeystroke(int index, boolean pressed, float deltaSeconds) {
/* 3369 */     KEYSTROKE_PROGRESS[index] = approachExp(KEYSTROKE_PROGRESS[index], pressed ? 1.0F : 0.0F, 16.0F, deltaSeconds);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderLiveKeystrokeAnimation(class_332 context, class_310 client, float inverseGuiScale) {
/* 3383 */     KeystrokesHud module = KeystrokesHud.getInstance();
/* 3384 */     if (context == null || client == null || module == null || !module.isEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 3388 */     boolean hasVisibleProgress = false;
/* 3389 */     for (float progress : KEYSTROKE_PROGRESS) {
/* 3390 */       if (progress > 0.001F) {
/* 3391 */         hasVisibleProgress = true;
/*      */         break;
/*      */       } 
/*      */     } 
/* 3395 */     if (!hasVisibleProgress) {
/*      */       return;
/*      */     }
/*      */     
/* 3399 */     float x = module.getHudX();
/* 3400 */     float y = module.getHudY();
/* 3401 */     float scale = module.getRenderHudScale();
/*      */     
/* 3403 */     context.method_51448().method_22903();
/* 3404 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 3405 */     context.method_51448().method_22903();
/* 3406 */     context.method_51448().method_46416(x, y, 420.0F);
/* 3407 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 3408 */     renderLiveKeystrokeButton(context, module, 20, 0, 16, 18, KEYSTROKE_PROGRESS[0]);
/* 3409 */     renderLiveKeystrokeButton(context, module, 0, 19, 18, 18, KEYSTROKE_PROGRESS[1]);
/* 3410 */     renderLiveKeystrokeButton(context, module, 19, 19, 16, 18, KEYSTROKE_PROGRESS[2]);
/* 3411 */     renderLiveKeystrokeButton(context, module, 36, 19, 18, 18, KEYSTROKE_PROGRESS[3]);
/* 3412 */     renderLiveKeystrokeButton(context, module, 0, 38, 54, 8, KEYSTROKE_PROGRESS[6]);
/* 3413 */     renderLiveKeystrokeButton(context, module, 0, 47, 26, 16, KEYSTROKE_PROGRESS[4]);
/* 3414 */     renderLiveKeystrokeButton(context, module, 28, 47, 26, 16, KEYSTROKE_PROGRESS[5]);
/* 3415 */     context.method_51452();
/* 3416 */     context.method_51448().method_22909();
/*      */     
/* 3418 */     boolean shadow = module.textShadow.isValue();
/* 3419 */     renderLiveKeystrokeLabel(context, client, "W", x, y, 20.0F, 4.0F, 16.0F, KEYSTROKE_PROGRESS[0], scale, shadow);
/* 3420 */     renderLiveKeystrokeLabel(context, client, "A", x, y, 0.0F, 23.0F, 18.0F, KEYSTROKE_PROGRESS[1], scale, shadow);
/* 3421 */     renderLiveKeystrokeLabel(context, client, "S", x, y, 19.0F, 23.0F, 16.0F, KEYSTROKE_PROGRESS[2], scale, shadow);
/* 3422 */     renderLiveKeystrokeLabel(context, client, "D", x, y, 36.0F, 23.0F, 18.0F, KEYSTROKE_PROGRESS[3], scale, shadow);
/* 3423 */     if (KEYSTROKE_PROGRESS[6] > 0.45F) {
/* 3424 */       renderSpacebarLabel(context, x, y, 0.0F, 38.0F, 54.0F, 8.0F, KEYSTROKE_PROGRESS[6], scale);
/*      */     }
/* 3426 */     renderLiveKeystrokeLabel(context, client, "LMB", x, y, 0.0F, 50.0F, 26.0F, KEYSTROKE_PROGRESS[4], scale, shadow);
/* 3427 */     renderLiveKeystrokeLabel(context, client, "RMB", x, y, 28.0F, 50.0F, 26.0F, KEYSTROKE_PROGRESS[5], scale, shadow);
/* 3428 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderLiveKeystrokeButton(class_332 context, KeystrokesHud module, int x, int y, int width, int height, float progress) {
/* 3440 */     int alpha = class_3532.method_15340(Math.round(185.0F * progress), 0, 185);
/* 3441 */     if (alpha > 0) {
/* 3442 */       renderSolidHudBackground(context, x, y, width, height, withAlpha(16777215, alpha), module.cornerRounding
/* 3443 */           .getValue(), (module.backgroundBlurRadius.getValue() <= 0.0F));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderLiveKeystrokeLabel(class_332 context, class_310 client, String label, float hudX, float hudY, float keyX, float keyY, float keyWidth, float progress, float scale, boolean shadow) {
/* 3460 */     if (progress > 0.45F) {
/* 3461 */       renderKeyLabel(context, client, label, hudX, hudY, keyX, keyY, keyWidth, progress, scale, shadow);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void renderKeystrokeButtonBlur(class_332 context, KeystrokesHud module, float scale) {
/* 3466 */     if (!module.background.isValue()) {
/*      */       return;
/*      */     }
/* 3469 */     float blurRadius = Math.max(0.0F, module.backgroundBlurRadius.getValue());
/* 3470 */     if (blurRadius <= 0.0F) {
/*      */       return;
/*      */     }
/* 3473 */     float safeScale = Math.max(scale, 1.0F);
/* 3474 */     float normalizedBlurRadius = blurRadius / safeScale;
/* 3475 */     float blurQuality = getOptimizedHudBlurQuality(normalizedBlurRadius);
/* 3476 */     if (blurQuality <= 0.0F) {
/*      */       return;
/*      */     }
/* 3479 */     long blurStateKey = makeHudBlurStateKey(normalizedBlurRadius, safeScale, 0.0F, 0.0F, 54.0F, 63.0F);
/* 3480 */     Blur.INSTANCE.registerHudBlurState(6, blurStateKey);
/* 3481 */     for (ShapeProperties shape : KEYSTROKE_BLUR_RECTS) {
/* 3482 */       shape.setMatrix(context.method_51448());
/* 3483 */       shape.setQuality(blurQuality);
/* 3484 */       shape.getRound().set(getClampedHudCornerRadius(module.cornerRounding
/* 3485 */             .getValue(), shape.getWidth(), shape.getHeight()));
/*      */     } 
/* 3487 */     Blur.INSTANCE.renderCachedBatch(KEYSTROKE_BLUR_RECTS);
/*      */   }
/*      */   
/*      */   private static ShapeProperties createKeystrokeBlurRect(float x, float y, float width, float height) {
/* 3491 */     return ShapeProperties.create(null, x, y, width, height)
/* 3492 */       .round(0.0F)
/* 3493 */       .softness(0.0F)
/* 3494 */       .color(-1)
/* 3495 */       .build();
/*      */   }
/*      */   private static void renderKeyButton(class_332 context, KeystrokesHud module, int x, int y, int width, int height, int idleColor, float progress) {
/* 3498 */     int activeOverlay = withAlpha(16777215, Math.round(185.0F * progress));
/* 3499 */     int color = blendARGB(idleColor, activeOverlay);
/* 3500 */     renderSolidHudBackground(context, x, y, width, height, color, module.cornerRounding.getValue(), 
/* 3501 */         (module.backgroundBlurRadius.getValue() <= 0.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderGradientKeyButton(class_332 context, KeystrokesHud module, int x, int y, int width, int height, float progress) {
/* 3513 */     renderGradientRect(context, x, y, width, height, withAlpha(16777215, Math.round(185.0F * progress)), module
/* 3514 */         .getResolvedGradientStartColor(), module.getResolvedGradientEndColor(), module
/* 3515 */         .getGradientDirection(), module.getGradientAnimationOffset(System.currentTimeMillis()), module
/* 3516 */         .hasActiveAnimatedBackground(), module.cornerRounding.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderKeyLabel(class_332 context, class_310 client, String label, float hudX, float hudY, float keyX, float keyY, float keyWidth, float progress, float scale, boolean shadow) {
/* 3532 */     float textWidth = getHudTextWidth(client, label, 8.0F);
/* 3533 */     int color = (progress > 0.45F) ? 1118481 : resolveHudTextColor();
/* 3534 */     renderScaledHudTextColored(context, client, label, hudX, hudY, keyX + (keyWidth - textWidth) * 0.5F, keyY, 8.0F, scale, shadow, color);
/*      */   }
/*      */   
/*      */   private static void renderSpacebarLabel(class_332 context, float hudX, float hudY, float keyX, float keyY, float keyWidth, float keyHeight, float progress, float scale) {
/* 3538 */     int color = (progress > 0.45F) ? 1118481 : resolveHudTextColor();
/* 3539 */     int lineColor = withAlpha(color, 200);
/* 3540 */     float lineWidth = 14.0F;
/* 3541 */     float lineThickness = 1.0F;
/* 3542 */     float lx = keyX + (keyWidth - lineWidth) * 0.5F;
/* 3543 */     float ly = keyY + 2.0F;
/* 3544 */     context.method_51448().method_22903();
/* 3545 */     context.method_51448().method_46416(hudX, hudY, 425.0F);
/* 3546 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 3547 */     context.method_25294(Math.round(lx), Math.round(ly), Math.round(lx + lineWidth), Math.round(ly + lineThickness), lineColor);
/* 3548 */     context.method_51452();
/* 3549 */     context.method_51448().method_22909();
/*      */   }
/*      */   
/*      */   private static String getKeyLabel(class_2561 keyText) {
/* 3553 */     String value = (keyText == null) ? "" : keyText.getString();
/* 3554 */     if (value == null || value.isBlank()) {
/* 3555 */       return "?";
/*      */     }
/* 3557 */     if (value.length() > 3) {
/* 3558 */       return value.substring(0, 3).toUpperCase(Locale.ROOT);
/*      */     }
/* 3560 */     return value.toUpperCase(Locale.ROOT);
/*      */   }
/*      */   
/*      */   private static String getEffectName(class_1293 effect) {
/* 3564 */     String name = ((class_1291)effect.method_5579().comp_349()).method_5560().getString();
/* 3565 */     int amplifier = effect.method_5578();
/* 3566 */     if (amplifier >= 1) {
/* 3567 */       name = name + " " + name;
/*      */     }
/* 3569 */     return name;
/*      */   }
/*      */   
/*      */   private static String toRoman(int number) {
/* 3573 */     switch (number) { case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10:  }  return 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3583 */       String.valueOf(number);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String formatEffectDuration(class_1293 effect) {
/* 3588 */     if (effect.method_48559())
/*      */     {
/*      */       
/* 3591 */       return "∞";
/*      */     }
/* 3593 */     int totalSeconds = Math.max(0, effect.method_5584() / 20);
/* 3594 */     int minutes = totalSeconds / 60;
/* 3595 */     int seconds = totalSeconds % 60;
/* 3596 */     return String.format(Locale.US, "%02d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
/*      */   }
/*      */   
/*      */   private static float getHudTextWidth(class_310 client, String text, float textSize) {
/* 3600 */     String cacheKey = "V|" + textSize + "|" + text;
/* 3601 */     Float cached = HUD_TEXT_WIDTH_CACHE.get(cacheKey);
/* 3602 */     if (cached != null) {
/* 3603 */       return cached.floatValue();
/*      */     }
/*      */     
/* 3606 */     float width = client.field_1772.method_1727(text);
/* 3607 */     HUD_TEXT_WIDTH_CACHE.put(cacheKey, Float.valueOf(width));
/* 3608 */     return width;
/*      */   }
/*      */   
/*      */   private static void renderHudTextWithAlpha(class_332 context, class_310 client, String text, float x, float y, float textSize, boolean shadow, int alpha) {
/* 3612 */     renderHudTextColoredWithAlpha(context, client, text, x, y, textSize, shadow, resolveHudTextColor() & 0xFFFFFF, alpha);
/*      */   }
/*      */   
/*      */   private static int resolveHudTextColor() {
/* 3616 */     Theme theme = Theme.getInstance();
/* 3617 */     if (theme == null) {
/* 3618 */       return -1;
/*      */     }
/* 3620 */     return theme.getHudTextColor();
/*      */   }
/*      */   
/*      */   private static void renderHudTextColoredWithAlpha(class_332 context, class_310 client, String text, float x, float y, float textSize, boolean shadow, int rgbColor, int alpha) {
/* 3624 */     int textColor = class_3532.method_15340(alpha, 0, 255) << 24 | rgbColor & 0xFFFFFF;
/* 3625 */     context.method_51433(client.field_1772, text, Math.round(x), Math.round(y), textColor, shadow);
/*      */   }
/*      */   
/*      */   private static float getOptimizedHudBlurQuality(float normalizedBlurRadius) {
/* 3629 */     float radius = Math.max(0.0F, normalizedBlurRadius);
/* 3630 */     if (radius <= 0.001F) {
/* 3631 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3638 */     float t = class_3532.method_15363(radius / 32.0F, 0.0F, 1.0F);
/* 3639 */     float strength = class_3532.method_16439(t, 0.14F, 0.34F);
/* 3640 */     return radius * strength;
/*      */   }
/*      */   
/*      */   private static long makeHudBlurStateKey(float normalizedRadius, float safeScale, float x, float y, float width, float height) {
/* 3644 */     long h = -7046029254386353131L;
/* 3645 */     h = h * 1099511628211L ^ quantizeBlurKeyPart(normalizedRadius, 100.0F);
/* 3646 */     h = h * 1099511628211L ^ quantizeBlurKeyPart(safeScale, 100.0F);
/* 3647 */     h = h * 1099511628211L ^ quantizeBlurKeyPart(x, 1000.0F);
/* 3648 */     h = h * 1099511628211L ^ quantizeBlurKeyPart(y, 1000.0F);
/* 3649 */     h = h * 1099511628211L ^ quantizeBlurKeyPart(width, 1000.0F);
/* 3650 */     h = h * 1099511628211L ^ quantizeBlurKeyPart(height, 1000.0F);
/* 3651 */     return h;
/*      */   }
/*      */   
/*      */   private static long quantizeBlurKeyPart(float value, float precision) {
/* 3655 */     return Math.round(value * precision);
/*      */   }
/*      */   
/*      */   private static void renderHudText(class_332 context, class_310 client, String text, float x, float y, float msdfSize, boolean shadow) {
/* 3659 */     renderHudTextWithAlpha(context, client, text, x, y, msdfSize, shadow, 255);
/*      */   }
/*      */   
/*      */   private static void renderHudTextColored(class_332 context, class_310 client, String text, float x, float y, float msdfSize, boolean shadow, int rgbColor) {
/* 3663 */     renderHudTextColoredWithAlpha(context, client, text, x, y, msdfSize, shadow, rgbColor, 255);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderScaledHudText(class_332 context, class_310 client, String text, float hudX, float hudY, float textX, float textY, float textSize, float scale, boolean shadow) {
/* 3678 */     context.method_51448().method_22903();
/* 3679 */     context.method_51448().method_46416(hudX, hudY, 425.0F);
/* 3680 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 3681 */     renderHudText(context, client, text, textX, textY, textSize, shadow);
/* 3682 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderScaledHudTextWithAlpha(class_332 context, class_310 client, String text, float hudX, float hudY, float textX, float textY, float textSize, float scale, boolean shadow, int alpha) {
/* 3698 */     context.method_51448().method_22903();
/* 3699 */     context.method_51448().method_46416(hudX, hudY, 425.0F);
/* 3700 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 3701 */     renderHudTextWithAlpha(context, client, text, textX, textY, textSize, shadow, alpha);
/* 3702 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderScaledHudTextColored(class_332 context, class_310 client, String text, float hudX, float hudY, float textX, float textY, float textSize, float scale, boolean shadow, int rgbColor) {
/* 3718 */     context.method_51448().method_22903();
/* 3719 */     context.method_51448().method_46416(hudX, hudY, 425.0F);
/* 3720 */     context.method_51448().method_22905(scale, scale, 1.0F);
/* 3721 */     renderHudTextColored(context, client, text, textX, textY, textSize, shadow, rgbColor);
/* 3722 */     context.method_51448().method_22909();
/*      */   }
/*      */   
/*      */   private static void updateClicksPerSecond(boolean mouseDown, boolean rightMouseDown, boolean gameplayInput) {
/* 3726 */     if (!gameplayInput) {
/* 3727 */       LEFT_CLICKS.clear();
/* 3728 */       RIGHT_CLICKS.clear();
/* 3729 */       wasLeftMouseDown = false;
/* 3730 */       wasRightMouseDown = false;
/*      */       
/*      */       return;
/*      */     } 
/* 3734 */     long nowMs = System.currentTimeMillis();
/*      */     
/* 3736 */     if (mouseDown && !wasLeftMouseDown) {
/* 3737 */       LEFT_CLICKS.addLast(Long.valueOf(nowMs));
/*      */     }
/* 3739 */     wasLeftMouseDown = mouseDown;
/*      */     
/* 3741 */     while (!LEFT_CLICKS.isEmpty() && nowMs - ((Long)LEFT_CLICKS.peekFirst()).longValue() > 1000L) {
/* 3742 */       LEFT_CLICKS.removeFirst();
/*      */     }
/*      */ 
/*      */     
/* 3746 */     if (rightMouseDown && !wasRightMouseDown) {
/* 3747 */       RIGHT_CLICKS.addLast(Long.valueOf(nowMs));
/*      */     }
/* 3749 */     wasRightMouseDown = rightMouseDown;
/*      */     
/* 3751 */     while (!RIGHT_CLICKS.isEmpty() && nowMs - ((Long)RIGHT_CLICKS.peekFirst()).longValue() > 1000L) {
/* 3752 */       RIGHT_CLICKS.removeFirst();
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean hasAnyHudEnabled() {
/* 3757 */     if (!FpsHud.getInstance().isEnabled() && 
/* 3758 */       !CpsHud.getInstance().isEnabled() && 
/* 3759 */       !ReachHud.getInstance().isEnabled() && 
/* 3760 */       !ArmorHud.getInstance().isEnabled() && 
/* 3761 */       !SprintHud.getInstance().isEnabled() && 
/* 3762 */       !CoordinatesHud.getInstance().isEnabled() && 
/* 3763 */       !PingHud.getInstance().isEnabled() && 
/* 3764 */       !KeystrokesHud.getInstance().isEnabled() && 
/* 3765 */       !PotionHud.getInstance().isEnabled() && 
/* 3766 */       !DayCounterHud.getInstance().isEnabled() && 
/* 3767 */       !NametagHud.getInstance().isEnabled() && 
/* 3768 */       !TimeHud.getInstance().isEnabled() && 
/* 3769 */       !SessionTimeHud.getInstance().isEnabled() && 
/* 3770 */       !MemoryHud.getInstance().isEnabled() && 
/* 3771 */       !TpsHud.getInstance().isEnabled() && 
/* 3772 */       !ComboCounterHud.getInstance().isEnabled() && 
/* 3773 */       !ServerAddressHud.getInstance().isEnabled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3779 */       if (MovementSpeedHud.getInstance().isEnabled() || 
/* 3780 */         WailaHud.getInstance().isEnabled() || 
/* 3781 */         HealthIndicator.getInstance().isEnabled() || 
/* 3782 */         TrapTimer.getInstance().isEnabled() || 
/* 3783 */         Consumable.getInstance().isEnabled() || 
/* 3784 */         PlayerModelHud.getInstance().isEnabled() || 
/* 3785 */         InventoryHud.getInstance().isEnabled() || 
/* 3786 */         ScoreboardHud.getInstance().isEnabled() || (
/* 3787 */         BossBarHud.getInstance().isEnabled() && (class_310.method_1551()).field_1755 instanceof net.minecraft.class_408));
/*      */       return false;
/*      */     } 
/*      */   } private static boolean isAnyHudInteractionActive() {
/* 3791 */     for (int index = 0; index < 29; index++) {
/* 3792 */       if (RECT_DRAGGING[index] || RECT_RESIZING[index]) return true; 
/*      */     } 
/* 3794 */     return (armorDragging || armorResizing);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float snapAndAnnounceHudScale(Module module, float rawScale, float minScale, float maxScale) {
/* 3803 */     float clamped = class_3532.method_15363(rawScale, minScale, maxScale);
/* 3804 */     boolean canReachDefault = (minScale <= 1.0F && maxScale >= 1.0F);
/* 3805 */     boolean snapped = (canReachDefault && clamped >= 0.92F && clamped <= 1.08F);
/* 3806 */     float value = snapped ? 1.0F : clamped;
/* 3807 */     ScaleSnapOverlay.show(value, snapped, module.getVisibleName() + " Scale");
/* 3808 */     return value;
/*      */   }
/*      */   
/*      */   private static void renderHudGuides(class_332 context, float screenWidth, float screenHeight, float inverseGuiScale) {
/* 3812 */     context.method_51448().method_22903();
/* 3813 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/*      */     
/* 3815 */     if (verticalGuideProgress > 0.01F) {
/* 3816 */       int alpha = class_3532.method_15340(Math.round(140.0F * verticalGuideProgress), 0, 255);
/* 3817 */       int x = Math.round(screenWidth * 0.5F);
/* 3818 */       context.method_25294(x, 0, x + 1, Math.round(screenHeight), withAlpha(16777215, alpha));
/*      */     } 
/*      */     
/* 3821 */     if (horizontalGuideProgress > 0.01F) {
/* 3822 */       int alpha = class_3532.method_15340(Math.round(140.0F * horizontalGuideProgress), 0, 255);
/* 3823 */       int y = Math.round(screenHeight * 0.5F);
/* 3824 */       context.method_25294(0, y, Math.round(screenWidth), y + 1, withAlpha(16777215, alpha));
/*      */     } 
/*      */     
/* 3827 */     if (hudVerticalGuideProgress > 0.01F && hudVerticalGuideBottom > hudVerticalGuideTop) {
/* 3828 */       int alpha = class_3532.method_15340(Math.round(140.0F * hudVerticalGuideProgress), 0, 255);
/* 3829 */       int x = Math.round(hudVerticalGuideX);
/* 3830 */       int top = class_3532.method_15375(hudVerticalGuideTop);
/* 3831 */       int bottom = class_3532.method_15386(hudVerticalGuideBottom);
/* 3832 */       context.method_25294(x, top, x + 1, bottom, withAlpha(16777215, alpha));
/*      */     } 
/*      */     
/* 3835 */     if (hudHorizontalGuideProgress > 0.01F && hudHorizontalGuideRight > hudHorizontalGuideLeft) {
/* 3836 */       int alpha = class_3532.method_15340(Math.round(140.0F * hudHorizontalGuideProgress), 0, 255);
/* 3837 */       int y = Math.round(hudHorizontalGuideY);
/* 3838 */       int left = class_3532.method_15375(hudHorizontalGuideLeft);
/* 3839 */       int right = class_3532.method_15386(hudHorizontalGuideRight);
/* 3840 */       context.method_25294(left, y, right, y + 1, withAlpha(16777215, alpha));
/*      */     } 
/*      */     
/* 3843 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rememberHudSnapBounds(int hudIndex, Module owner, float x, float y, float width, float height) {
/* 3854 */     if (hudIndex < 0 || hudIndex >= 30 || owner == null || width <= 0.0F || height <= 0.0F) {
/*      */       return;
/*      */     }
/* 3857 */     HUD_SNAP_X[hudIndex] = x;
/* 3858 */     HUD_SNAP_Y[hudIndex] = y;
/* 3859 */     HUD_SNAP_WIDTH[hudIndex] = width;
/* 3860 */     HUD_SNAP_HEIGHT[hudIndex] = height;
/* 3861 */     HUD_SNAP_OWNER[hudIndex] = owner;
/* 3862 */     HUD_SNAP_VALID[hudIndex] = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float snapHudAlignmentX(int movingIndex, float x, float y, float width, float height, float maxX, float coordinateScale) {
/* 3874 */     float safeCoordinateScale = Math.max(0.001F, coordinateScale);
/* 3875 */     float bestDistance = Float.MAX_VALUE;
/* 3876 */     int bestIndex = -1;
/* 3877 */     float bestTargetAnchor = 0.0F;
/* 3878 */     float bestMovingAnchorOffset = 0.0F;
/*      */     
/* 3880 */     for (int index = 0; index < 30; index++) {
/* 3881 */       Module owner = HUD_SNAP_OWNER[index];
/* 3882 */       if (index != movingIndex && HUD_SNAP_VALID[index] && owner != null && owner.isEnabled()) {
/*      */ 
/*      */         
/* 3885 */         float targetLeft = HUD_SNAP_X[index] / safeCoordinateScale;
/* 3886 */         float targetCenterX = (HUD_SNAP_X[index] + HUD_SNAP_WIDTH[index] * 0.5F) / safeCoordinateScale;
/* 3887 */         float targetRight = (HUD_SNAP_X[index] + HUD_SNAP_WIDTH[index]) / safeCoordinateScale;
/*      */ 
/*      */         
/* 3890 */         float distance = Math.abs(x + width * 0.5F - targetCenterX);
/* 3891 */         if (distance <= 4.0F && distance < bestDistance) {
/* 3892 */           bestDistance = distance;
/* 3893 */           bestIndex = index;
/* 3894 */           bestTargetAnchor = targetCenterX;
/* 3895 */           bestMovingAnchorOffset = width * 0.5F;
/*      */         } 
/*      */         
/* 3898 */         distance = Math.abs(x - targetLeft);
/* 3899 */         if (distance <= 4.0F && distance < bestDistance) {
/* 3900 */           bestDistance = distance;
/* 3901 */           bestIndex = index;
/* 3902 */           bestTargetAnchor = targetLeft;
/* 3903 */           bestMovingAnchorOffset = 0.0F;
/*      */         } 
/*      */         
/* 3906 */         distance = Math.abs(x + width - targetRight);
/* 3907 */         if (distance <= 4.0F && distance < bestDistance) {
/* 3908 */           bestDistance = distance;
/* 3909 */           bestIndex = index;
/* 3910 */           bestTargetAnchor = targetRight;
/* 3911 */           bestMovingAnchorOffset = width;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3915 */     if (bestIndex < 0) {
/* 3916 */       return x;
/*      */     }
/*      */     
/* 3919 */     float snappedX = class_3532.method_15363(bestTargetAnchor - bestMovingAnchorOffset, 0.0F, maxX);
/* 3920 */     if (Math.abs(snappedX + bestMovingAnchorOffset - bestTargetAnchor) > 0.01F) {
/* 3921 */       return x;
/*      */     }
/* 3923 */     showHudVerticalGuideThisFrame = true;
/* 3924 */     hudVerticalGuideX = bestTargetAnchor * safeCoordinateScale;
/* 3925 */     hudVerticalGuideTop = Math.min(y * safeCoordinateScale, HUD_SNAP_Y[bestIndex]);
/* 3926 */     hudVerticalGuideBottom = Math.max((y + height) * safeCoordinateScale, HUD_SNAP_Y[bestIndex] + HUD_SNAP_HEIGHT[bestIndex]);
/*      */ 
/*      */ 
/*      */     
/* 3930 */     return snappedX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float snapHudAlignmentY(int movingIndex, float x, float y, float width, float height, float maxY, float coordinateScale) {
/* 3942 */     float safeCoordinateScale = Math.max(0.001F, coordinateScale);
/* 3943 */     float bestDistance = Float.MAX_VALUE;
/* 3944 */     int bestIndex = -1;
/* 3945 */     float bestTargetAnchor = 0.0F;
/* 3946 */     float bestMovingAnchorOffset = 0.0F;
/*      */     
/* 3948 */     for (int index = 0; index < 30; index++) {
/* 3949 */       Module owner = HUD_SNAP_OWNER[index];
/* 3950 */       if (index != movingIndex && HUD_SNAP_VALID[index] && owner != null && owner.isEnabled()) {
/*      */ 
/*      */         
/* 3953 */         float targetTop = HUD_SNAP_Y[index] / safeCoordinateScale;
/* 3954 */         float targetCenterY = (HUD_SNAP_Y[index] + HUD_SNAP_HEIGHT[index] * 0.5F) / safeCoordinateScale;
/* 3955 */         float targetBottom = (HUD_SNAP_Y[index] + HUD_SNAP_HEIGHT[index]) / safeCoordinateScale;
/*      */         
/* 3957 */         float distance = Math.abs(y + height * 0.5F - targetCenterY);
/* 3958 */         if (distance <= 4.0F && distance < bestDistance) {
/* 3959 */           bestDistance = distance;
/* 3960 */           bestIndex = index;
/* 3961 */           bestTargetAnchor = targetCenterY;
/* 3962 */           bestMovingAnchorOffset = height * 0.5F;
/*      */         } 
/*      */         
/* 3965 */         distance = Math.abs(y - targetTop);
/* 3966 */         if (distance <= 4.0F && distance < bestDistance) {
/* 3967 */           bestDistance = distance;
/* 3968 */           bestIndex = index;
/* 3969 */           bestTargetAnchor = targetTop;
/* 3970 */           bestMovingAnchorOffset = 0.0F;
/*      */         } 
/*      */         
/* 3973 */         distance = Math.abs(y + height - targetBottom);
/* 3974 */         if (distance <= 4.0F && distance < bestDistance) {
/* 3975 */           bestDistance = distance;
/* 3976 */           bestIndex = index;
/* 3977 */           bestTargetAnchor = targetBottom;
/* 3978 */           bestMovingAnchorOffset = height;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3982 */     if (bestIndex < 0) {
/* 3983 */       return y;
/*      */     }
/*      */     
/* 3986 */     float snappedY = class_3532.method_15363(bestTargetAnchor - bestMovingAnchorOffset, 0.0F, maxY);
/* 3987 */     if (Math.abs(snappedY + bestMovingAnchorOffset - bestTargetAnchor) > 0.01F) {
/* 3988 */       return y;
/*      */     }
/* 3990 */     showHudHorizontalGuideThisFrame = true;
/* 3991 */     hudHorizontalGuideY = bestTargetAnchor * safeCoordinateScale;
/* 3992 */     hudHorizontalGuideLeft = Math.min(x * safeCoordinateScale, HUD_SNAP_X[bestIndex]);
/* 3993 */     hudHorizontalGuideRight = Math.max((x + width) * safeCoordinateScale, HUD_SNAP_X[bestIndex] + HUD_SNAP_WIDTH[bestIndex]);
/*      */ 
/*      */ 
/*      */     
/* 3997 */     return snappedY;
/*      */   }
/*      */   
/*      */   private static boolean isHovered(double mouseX, double mouseY, float x, float y, float width, float height) {
/* 4001 */     return (mouseX >= x && mouseX <= (x + width) && mouseY >= y && mouseY <= (y + height));
/*      */   }
/*      */   
/*      */   private static boolean isNearRect(double mouseX, double mouseY, float x, float y, float width, float height, float distance) {
/* 4005 */     return (mouseX >= (x - distance) && mouseX <= (x + width + distance) && mouseY >= (y - distance) && mouseY <= (y + height + distance));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int withAlpha(int rgb, int alpha) {
/* 4012 */     int a = class_3532.method_15340(alpha, 0, 255);
/* 4013 */     return a << 24 | rgb & 0xFFFFFF;
/*      */   }
/*      */   
/*      */   private static float getClampedHudCornerRadius(float requestedRadius, float width, float height) {
/* 4017 */     return class_3532.method_15363(requestedRadius, 0.0F, Math.max(0.0F, Math.min(width, height) * 0.5F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderSolidHudBackground(class_332 context, float x, float y, float width, float height, int color, float requestedRadius, boolean useSdfRenderer) {
/* 4030 */     float radius = getClampedHudCornerRadius(requestedRadius, width, height);
/* 4031 */     if (radius <= 0.01F) {
/* 4032 */       context.method_25294(Math.round(x), Math.round(y), Math.round(x + width), Math.round(y + height), color);
/*      */       
/*      */       return;
/*      */     } 
/* 4036 */     if (useSdfRenderer) {
/* 4037 */       context.method_51452();
/* 4038 */       HUD_BACKGROUND_RECTANGLE.render(ShapeProperties.create(context.method_51448(), x, y, width, height)
/* 4039 */           .round(radius)
/* 4040 */           .softness(1.0F)
/* 4041 */           .color(color)
/* 4042 */           .build());
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4051 */     float centerLeft = x + radius;
/* 4052 */     float centerRight = x + width - radius;
/* 4053 */     drawVerticalGradient(context, centerLeft, y, Math.max(0.0F, centerRight - centerLeft), height, color, color);
/*      */     
/* 4055 */     class_310 client = class_310.method_1551();
/*      */ 
/*      */     
/* 4058 */     float windowScale = (client == null || client.method_22683() == null) ? 1.0F : Math.max(1.0F, (float)client.method_22683().method_4495());
/* 4059 */     float matrixScaleX = Math.abs(context.method_51448().method_23760().method_23761().m00());
/* 4060 */     float matrixScaleY = Math.abs(context.method_51448().method_23760().method_23761().m11());
/* 4061 */     float physicalScaleX = Math.max(0.01F, matrixScaleX * windowScale);
/* 4062 */     float physicalScaleY = Math.max(0.01F, matrixScaleY * windowScale);
/* 4063 */     int capStrips = class_3532.method_15340((int)Math.ceil((radius * physicalScaleX * 1.5F)), 8, 128);
/* 4064 */     float antialiasSize = Math.min(radius, 1.0F / physicalScaleY);
/* 4065 */     for (int strip = 0; strip < capStrips; strip++) {
/* 4066 */       float p0 = strip / capStrips;
/* 4067 */       float p1 = (strip + 1.0F) / capStrips;
/* 4068 */       float centerProgress = (p0 + p1) * 0.5F;
/* 4069 */       float edgeDistance = centerProgress * radius;
/* 4070 */       float dx = radius - edgeDistance;
/* 4071 */       float inset = radius - (float)Math.sqrt(Math.max(0.0F, radius * radius - dx * dx));
/* 4072 */       float stripWidth = (p1 - p0) * radius;
/* 4073 */       float exactTop = y + inset;
/* 4074 */       float exactBottom = y + height - inset;
/* 4075 */       if (exactBottom > exactTop) {
/*      */ 
/*      */         
/* 4078 */         renderAntialiasedSolidStrip(context, x + p0 * radius, exactTop, stripWidth, exactBottom, color, antialiasSize);
/*      */         
/* 4080 */         renderAntialiasedSolidStrip(context, x + width - p1 * radius, exactTop, stripWidth, exactBottom, color, antialiasSize);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderAntialiasedSolidStrip(class_332 context, float x, float exactTop, float width, float exactBottom, int color, float antialiasSize) {
/* 4094 */     float halfAntialias = antialiasSize * 0.5F;
/* 4095 */     float solidTop = Math.min(exactBottom, exactTop + halfAntialias);
/* 4096 */     float solidBottom = Math.max(exactTop, exactBottom - halfAntialias);
/* 4097 */     if (solidBottom > solidTop) {
/* 4098 */       drawVerticalGradient(context, x, solidTop, width, solidBottom - solidTop, color, color);
/*      */     }
/* 4100 */     if (antialiasSize > 0.001F) {
/* 4101 */       int transparent = multiplyColorAlpha(color, 0.0F);
/* 4102 */       drawVerticalGradient(context, x, exactTop - halfAntialias, width, antialiasSize, transparent, color);
/* 4103 */       drawVerticalGradient(context, x, exactBottom - halfAntialias, width, antialiasSize, color, transparent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderGradientRect(class_332 context, float x, float y, float width, float height, int hoverFill, int startColor, int endColor, String direction, float animationOffset, boolean animated, float requestedRadius) {
/* 4128 */     if (hoverFill != 0) {
/* 4129 */       startColor = blendARGB(startColor, hoverFill);
/* 4130 */       endColor = blendARGB(endColor, hoverFill);
/*      */     } 
/* 4132 */     int left = Math.round(x);
/* 4133 */     int top = Math.round(y);
/* 4134 */     int right = Math.max(left + 1, Math.round(x + width));
/* 4135 */     int bottom = Math.max(top + 1, Math.round(y + height));
/* 4136 */     String selectedDirection = (direction == null) ? "Left to Right" : direction;
/* 4137 */     float radius = getClampedHudCornerRadius(requestedRadius, (right - left), (bottom - top));
/*      */     
/* 4139 */     if (radius > 0.01F) {
/* 4140 */       renderRoundedGradient(context, left, top, right, bottom, startColor, endColor, selectedDirection, animationOffset, animated, radius);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 4145 */     if (!animated) {
/* 4146 */       renderStaticGradient(context, left, top, right, bottom, startColor, endColor, selectedDirection);
/*      */       
/*      */       return;
/*      */     } 
/* 4150 */     if ("Pulse".equals(selectedDirection)) {
/* 4151 */       context.method_25294(left, top, right, bottom, movingGradientColor(startColor, endColor, animationOffset));
/*      */       
/*      */       return;
/*      */     } 
/* 4155 */     if ("Top to Bottom".equals(selectedDirection) || "Bottom to Top".equals(selectedDirection)) {
/* 4156 */       float directionSign = "Top to Bottom".equals(selectedDirection) ? -1.0F : 1.0F;
/* 4157 */       renderMovingAxisGradient(context, left, top, right, bottom, startColor, endColor, directionSign * animationOffset, false);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 4162 */     if ("Left to Right".equals(selectedDirection) || "Right to Left".equals(selectedDirection)) {
/* 4163 */       float directionSign = "Right to Left".equals(selectedDirection) ? 1.0F : -1.0F;
/* 4164 */       renderMovingAxisGradient(context, left, top, right, bottom, startColor, endColor, directionSign * animationOffset, true);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 4169 */     int stripCount = 96;
/* 4170 */     for (int strip = 0; strip < stripCount; strip++) {
/* 4171 */       float progress = (strip + 0.5F) / stripCount;
/* 4172 */       float stripLeft = class_3532.method_48781(strip / stripCount, left, right);
/* 4173 */       float stripRight = class_3532.method_48781((strip + 1.0F) / stripCount, left, right);
/*      */       
/* 4175 */       if ("Diagonal Down".equals(selectedDirection) || "Diagonal Up".equals(selectedDirection)) {
/* 4176 */         boolean down = "Diagonal Down".equals(selectedDirection);
/* 4177 */         float topCoordinate = down ? (progress * 0.5F) : (0.5F + progress * 0.5F);
/* 4178 */         float bottomCoordinate = down ? (0.5F + progress * 0.5F) : (progress * 0.5F);
/* 4179 */         int topColor = movingGradientColor(startColor, endColor, topCoordinate - animationOffset);
/* 4180 */         int bottomColor = movingGradientColor(startColor, endColor, bottomCoordinate - animationOffset);
/* 4181 */         drawVerticalGradient(context, stripLeft, top, stripRight - stripLeft, (bottom - top), topColor, bottomColor);
/*      */       } else {
/* 4183 */         float directionSign = "Right to Left".equals(selectedDirection) ? 1.0F : -1.0F;
/* 4184 */         int color = movingGradientColor(startColor, endColor, progress + directionSign * animationOffset);
/* 4185 */         drawVerticalGradient(context, stripLeft, top, stripRight - stripLeft, (bottom - top), color, color);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderRoundedGradient(class_332 context, float left, float top, float right, float bottom, int startColor, int endColor, String direction, float animationOffset, boolean animated, float radius) {
/* 4203 */     float width = right - left;
/* 4204 */     float height = bottom - top;
/* 4205 */     class_310 client = class_310.method_1551();
/*      */ 
/*      */     
/* 4208 */     float windowScale = (client == null || client.method_22683() == null) ? 1.0F : Math.max(1.0F, (float)client.method_22683().method_4495());
/* 4209 */     float matrixScaleX = Math.abs(context.method_51448().method_23760().method_23761().m00());
/* 4210 */     float matrixScaleY = Math.abs(context.method_51448().method_23760().method_23761().m11());
/* 4211 */     float physicalScaleX = Math.max(0.01F, matrixScaleX * windowScale);
/* 4212 */     float physicalScaleY = Math.max(0.01F, matrixScaleY * windowScale);
/*      */ 
/*      */ 
/*      */     
/* 4216 */     int stripCount = class_3532.method_15340((int)Math.ceil((width * physicalScaleX * 1.5F)), 48, 512);
/* 4217 */     float antialiasSize = Math.min(radius, 1.0F / physicalScaleY);
/*      */     
/* 4219 */     for (int strip = 0; strip < stripCount; strip++) {
/* 4220 */       float x0Progress = strip / stripCount;
/* 4221 */       float x1Progress = (strip + 1.0F) / stripCount;
/* 4222 */       float xProgress = (x0Progress + x1Progress) * 0.5F;
/* 4223 */       float stripLeft = class_3532.method_16439(x0Progress, left, right);
/* 4224 */       float stripRight = class_3532.method_16439(x1Progress, left, right);
/* 4225 */       float edgeDistance = Math.min(xProgress * width, (1.0F - xProgress) * width);
/* 4226 */       float inset = 0.0F;
/* 4227 */       boolean roundedCap = (edgeDistance < radius);
/* 4228 */       if (roundedCap) {
/* 4229 */         float dx = radius - edgeDistance;
/* 4230 */         inset = radius - (float)Math.sqrt(Math.max(0.0F, radius * radius - dx * dx));
/*      */       } 
/*      */       
/* 4233 */       float exactTop = top + inset;
/* 4234 */       float exactBottom = bottom - inset;
/* 4235 */       float solidTop = Math.min(exactBottom, exactTop + antialiasSize * 0.5F);
/* 4236 */       float solidBottom = Math.max(exactTop, exactBottom - antialiasSize * 0.5F);
/* 4237 */       if (exactBottom > exactTop) {
/*      */ 
/*      */         
/* 4240 */         float topProgress = (solidTop - top) / height;
/* 4241 */         float bottomProgress = (solidBottom - top) / height;
/* 4242 */         int topColor = sampleHudGradientColor(startColor, endColor, direction, xProgress, topProgress, animationOffset, animated);
/*      */         
/* 4244 */         int bottomColor = sampleHudGradientColor(startColor, endColor, direction, xProgress, bottomProgress, animationOffset, animated);
/*      */         
/* 4246 */         drawVerticalGradient(context, stripLeft, solidTop, stripRight - stripLeft, solidBottom - solidTop, topColor, bottomColor);
/*      */ 
/*      */         
/* 4249 */         if (roundedCap && antialiasSize > 0.001F) {
/* 4250 */           int exactTopColor = sampleHudGradientColor(startColor, endColor, direction, xProgress, inset / height, animationOffset, animated);
/*      */           
/* 4252 */           int exactBottomColor = sampleHudGradientColor(startColor, endColor, direction, xProgress, 1.0F - inset / height, animationOffset, animated);
/*      */           
/* 4254 */           drawVerticalGradient(context, stripLeft, exactTop - antialiasSize * 0.5F, stripRight - stripLeft, antialiasSize, 
/*      */               
/* 4256 */               multiplyColorAlpha(exactTopColor, 0.0F), exactTopColor);
/* 4257 */           drawVerticalGradient(context, stripLeft, exactBottom - antialiasSize * 0.5F, stripRight - stripLeft, antialiasSize, exactBottomColor, 
/*      */               
/* 4259 */               multiplyColorAlpha(exactBottomColor, 0.0F));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static int multiplyColorAlpha(int color, float multiplier) {
/* 4265 */     int alpha = class_3532.method_15340(Math.round((color >>> 24 & 0xFF) * multiplier), 0, 255);
/* 4266 */     return alpha << 24 | color & 0xFFFFFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int sampleHudGradientColor(int startColor, int endColor, String direction, float xProgress, float yProgress, float animationOffset, boolean animated) {
/*      */     float coordinate;
/*      */     float phase;
/* 4278 */     if ("Pulse".equals(direction)) {
/* 4279 */       return animated ? 
/* 4280 */         movingGradientColor(startColor, endColor, animationOffset) : 
/* 4281 */         startColor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4286 */     switch (direction) {
/*      */       case "Right to Left":
/* 4288 */         coordinate = 1.0F - xProgress;
/* 4289 */         phase = animationOffset;
/*      */         break;
/*      */       case "Top to Bottom":
/* 4292 */         coordinate = yProgress;
/* 4293 */         phase = -animationOffset;
/*      */         break;
/*      */       case "Bottom to Top":
/* 4296 */         coordinate = 1.0F - yProgress;
/* 4297 */         phase = animationOffset;
/*      */         break;
/*      */       case "Diagonal Down":
/* 4300 */         coordinate = (xProgress + yProgress) * 0.5F;
/* 4301 */         phase = -animationOffset;
/*      */         break;
/*      */       case "Diagonal Up":
/* 4304 */         coordinate = (xProgress + 1.0F - yProgress) * 0.5F;
/* 4305 */         phase = -animationOffset;
/*      */         break;
/*      */       default:
/* 4308 */         coordinate = xProgress;
/* 4309 */         phase = -animationOffset;
/*      */         break;
/*      */     } 
/* 4312 */     return animated ? 
/* 4313 */       movingGradientColor(startColor, endColor, coordinate + phase) : 
/* 4314 */       lerpGradientColor(startColor, endColor, class_3532.method_15363(coordinate, 0.0F, 1.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderStaticGradient(class_332 context, int left, int top, int right, int bottom, int startColor, int endColor, String direction) {
/* 4327 */     if ("Top to Bottom".equals(direction)) {
/* 4328 */       drawVerticalGradient(context, left, top, (right - left), (bottom - top), startColor, endColor);
/*      */       return;
/*      */     } 
/* 4331 */     if ("Bottom to Top".equals(direction)) {
/* 4332 */       drawVerticalGradient(context, left, top, (right - left), (bottom - top), endColor, startColor);
/*      */       return;
/*      */     } 
/* 4335 */     if ("Pulse".equals(direction)) {
/* 4336 */       context.method_25294(left, top, right, bottom, startColor);
/*      */       
/*      */       return;
/*      */     } 
/* 4340 */     if ("Left to Right".equals(direction) || "Right to Left".equals(direction)) {
/* 4341 */       boolean reversed = "Right to Left".equals(direction);
/* 4342 */       drawHorizontalGradient(context, left, top, (right - left), (bottom - top), 
/* 4343 */           reversed ? endColor : startColor, reversed ? startColor : endColor);
/*      */       
/*      */       return;
/*      */     } 
/* 4347 */     int stripCount = 96;
/* 4348 */     for (int strip = 0; strip < stripCount; strip++) {
/* 4349 */       float progress = (strip + 0.5F) / stripCount;
/* 4350 */       float stripLeft = class_3532.method_48781(strip / stripCount, left, right);
/* 4351 */       float stripRight = class_3532.method_48781((strip + 1.0F) / stripCount, left, right);
/* 4352 */       if ("Diagonal Down".equals(direction) || "Diagonal Up".equals(direction)) {
/* 4353 */         boolean down = "Diagonal Down".equals(direction);
/* 4354 */         float topProgress = down ? (progress * 0.5F) : (0.5F + progress * 0.5F);
/* 4355 */         float bottomProgress = down ? (0.5F + progress * 0.5F) : (progress * 0.5F);
/* 4356 */         drawVerticalGradient(context, stripLeft, top, stripRight - stripLeft, (bottom - top), 
/* 4357 */             lerpGradientColor(startColor, endColor, topProgress), 
/* 4358 */             lerpGradientColor(startColor, endColor, bottomProgress));
/*      */       } else {
/* 4360 */         float colorProgress = "Right to Left".equals(direction) ? (1.0F - progress) : progress;
/* 4361 */         int color = lerpGradientColor(startColor, endColor, colorProgress);
/* 4362 */         drawVerticalGradient(context, stripLeft, top, stripRight - stripLeft, (bottom - top), color, color);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void renderMovingAxisGradient(class_332 context, float left, float top, float right, float bottom, int startColor, int endColor, float phase, boolean horizontal) {
/* 4383 */     float position = 0.0F;
/* 4384 */     for (int segment = 0; segment < 4 && position < 0.99999F; segment++) {
/* 4385 */       float coordinate = position + phase;
/* 4386 */       float nextStop = ((float)Math.floor((coordinate * 2.0F + 1.0E-5F)) + 1.0F) * 0.5F - phase;
/* 4387 */       float endPosition = class_3532.method_15363(nextStop, position + 1.0E-4F, 1.0F);
/* 4388 */       int fromColor = movingLinearGradientColor(startColor, endColor, coordinate);
/* 4389 */       int toColor = movingLinearGradientColor(startColor, endColor, endPosition + phase);
/*      */       
/* 4391 */       if (horizontal) {
/* 4392 */         float x = class_3532.method_16439(position, left, right);
/* 4393 */         float width = class_3532.method_16439(endPosition, left, right) - x;
/* 4394 */         drawHorizontalGradient(context, x, top, width, bottom - top, fromColor, toColor);
/*      */       } else {
/* 4396 */         float y = class_3532.method_16439(position, top, bottom);
/* 4397 */         float height = class_3532.method_16439(endPosition, top, bottom) - y;
/* 4398 */         drawVerticalGradient(context, left, y, right - left, height, fromColor, toColor);
/*      */       } 
/* 4400 */       position = endPosition;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void drawVerticalGradient(class_332 context, float x, float y, float width, float height, int startColor, int endColor) {
/* 4413 */     if (width <= 0.0F || height <= 0.0F) {
/*      */       return;
/*      */     }
/* 4416 */     context.method_51448().method_22903();
/* 4417 */     context.method_51448().method_46416(x, y, 0.0F);
/* 4418 */     context.method_51448().method_22905(width, height, 1.0F);
/* 4419 */     context.method_25296(0, 0, 1, 1, startColor, endColor);
/* 4420 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void drawHorizontalGradient(class_332 context, float x, float y, float width, float height, int startColor, int endColor) {
/* 4432 */     if (width <= 0.0F || height <= 0.0F) {
/*      */       return;
/*      */     }
/* 4435 */     context.method_51448().method_22903();
/* 4436 */     context.method_51448().method_46416(x, y, 0.0F);
/* 4437 */     context.method_51448().method_22905(width, height, 1.0F);
/* 4438 */     context.method_51448().method_22907(class_7833.field_40718.rotationDegrees(-90.0F));
/* 4439 */     context.method_25296(-1, 0, 0, 1, startColor, endColor);
/* 4440 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */   
/*      */   private static int movingGradientColor(int startColor, int endColor, float coordinate) {
/* 4445 */     float wrapped = coordinate - (float)Math.floor(coordinate);
/* 4446 */     float blend = 0.5F - 0.5F * (float)Math.cos(wrapped * Math.PI * 2.0D);
/* 4447 */     return lerpGradientColor(startColor, endColor, blend);
/*      */   }
/*      */   
/*      */   private static int movingLinearGradientColor(int startColor, int endColor, float coordinate) {
/* 4451 */     float wrapped = coordinate - (float)Math.floor(coordinate);
/* 4452 */     float blend = (wrapped < 0.5F) ? (wrapped * 2.0F) : ((1.0F - wrapped) * 2.0F);
/* 4453 */     return lerpGradientColor(startColor, endColor, blend);
/*      */   }
/*      */   
/*      */   private static int lerpGradientColor(int first, int second, float progress) {
/* 4457 */     float t = class_3532.method_15363(progress, 0.0F, 1.0F);
/* 4458 */     int a = Math.round((first >>> 24 & 0xFF) + ((second >>> 24 & 0xFF) - (first >>> 24 & 0xFF)) * t);
/* 4459 */     int r = Math.round((first >>> 16 & 0xFF) + ((second >>> 16 & 0xFF) - (first >>> 16 & 0xFF)) * t);
/* 4460 */     int g = Math.round((first >>> 8 & 0xFF) + ((second >>> 8 & 0xFF) - (first >>> 8 & 0xFF)) * t);
/* 4461 */     int b = Math.round((first & 0xFF) + ((second & 0xFF) - (first & 0xFF)) * t);
/* 4462 */     return a << 24 | r << 16 | g << 8 | b;
/*      */   }
/*      */   
/*      */   private static float directionEdgeFade(float x, float width) {
/* 4466 */     float fadeWidth = Math.max(18.0F, width * 0.16F);
/* 4467 */     float left = class_3532.method_15363((x - 4.0F) / fadeWidth, 0.0F, 1.0F);
/* 4468 */     float right = class_3532.method_15363((width - 4.0F - x) / fadeWidth, 0.0F, 1.0F);
/* 4469 */     return smoothStep(left) * smoothStep(right);
/*      */   }
/*      */   
/*      */   private static float smoothStep(float value) {
/* 4473 */     float t = class_3532.method_15363(value, 0.0F, 1.0F);
/* 4474 */     return t * t * (3.0F - 2.0F * t);
/*      */   }
/*      */   
/*      */   private static float approachExp(float current, float target, float speed, float dt) {
/* 4478 */     float t = 1.0F - (float)Math.exp((-speed * dt));
/* 4479 */     return current + (target - current) * t;
/*      */   }
/*      */   
/*      */   private static void drawOutlineNoOverlap(class_332 context, int x, int y, int width, int height, int color) {
/* 4483 */     if (width <= 2 || height <= 2) {
/*      */       return;
/*      */     }
/* 4486 */     context.method_25294(x, y, x + width, y + 1, color);
/* 4487 */     context.method_25294(x, y + height - 1, x + width, y + height, color);
/* 4488 */     if (height > 2) {
/* 4489 */       context.method_25294(x, y + 1, x + 1, y + height - 1, color);
/* 4490 */       context.method_25294(x + width - 1, y + 1, x + width, y + height - 1, color);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void drawOuterOutline(class_332 context, float x, float y, float width, float height, int thickness, int color) {
/* 4495 */     if (thickness <= 0) {
/*      */       return;
/*      */     }
/* 4498 */     int innerLeft = class_3532.method_15375(x);
/* 4499 */     int innerTop = class_3532.method_15375(y);
/* 4500 */     int innerRight = class_3532.method_15386(x + width);
/* 4501 */     int innerBottom = class_3532.method_15386(y + height);
/*      */     
/* 4503 */     int outerLeft = innerLeft - thickness;
/* 4504 */     int outerTop = innerTop - thickness;
/* 4505 */     int outerRight = innerRight + thickness;
/* 4506 */     int outerBottom = innerBottom + thickness;
/*      */     
/* 4508 */     context.method_25294(outerLeft, outerTop, outerRight, innerTop, color);
/* 4509 */     context.method_25294(outerLeft, innerBottom, outerRight, outerBottom, color);
/* 4510 */     context.method_25294(outerLeft, innerTop, innerLeft, innerBottom, color);
/* 4511 */     context.method_25294(innerRight, innerTop, outerRight, innerBottom, color);
/*      */   }
/*      */   
/*      */   private static int blendARGB(int base, int over) {
/* 4515 */     float oa = (over >>> 24 & 0xFF) / 255.0F;
/* 4516 */     if (oa <= 0.0F) {
/* 4517 */       return base;
/*      */     }
/* 4519 */     float ba = (base >>> 24 & 0xFF) / 255.0F;
/* 4520 */     float outA = oa + ba * (1.0F - oa);
/* 4521 */     if (outA <= 0.0F) {
/* 4522 */       return 0;
/*      */     }
/*      */     
/* 4525 */     int br = base >>> 16 & 0xFF;
/* 4526 */     int bg = base >>> 8 & 0xFF;
/* 4527 */     int bb = base & 0xFF;
/* 4528 */     int or = over >>> 16 & 0xFF;
/* 4529 */     int og = over >>> 8 & 0xFF;
/* 4530 */     int ob = over & 0xFF;
/*      */     
/* 4532 */     int outR = class_3532.method_15340(Math.round((or * oa + br * ba * (1.0F - oa)) / outA), 0, 255);
/* 4533 */     int outG = class_3532.method_15340(Math.round((og * oa + bg * ba * (1.0F - oa)) / outA), 0, 255);
/* 4534 */     int outB = class_3532.method_15340(Math.round((ob * oa + bb * ba * (1.0F - oa)) / outA), 0, 255);
/* 4535 */     int outAlpha = class_3532.method_15340(Math.round(outA * 255.0F), 0, 255);
/* 4536 */     return outAlpha << 24 | outR << 16 | outG << 8 | outB;
/*      */   }
/*      */   
/*      */   private static int approachColorExp(int current, int target, float speed, float dt) {
/* 4540 */     float t = 1.0F - (float)Math.exp((-speed * dt));
/*      */     
/* 4542 */     int ca = current >>> 24 & 0xFF;
/* 4543 */     int cr = current >>> 16 & 0xFF;
/* 4544 */     int cg = current >>> 8 & 0xFF;
/* 4545 */     int cb = current & 0xFF;
/*      */     
/* 4547 */     int ta = target >>> 24 & 0xFF;
/* 4548 */     int tr = target >>> 16 & 0xFF;
/* 4549 */     int tg = target >>> 8 & 0xFF;
/* 4550 */     int tb = target & 0xFF;
/*      */     
/* 4552 */     int a = class_3532.method_15340(Math.round(ca + (ta - ca) * t), 0, 255);
/* 4553 */     int r = class_3532.method_15340(Math.round(cr + (tr - cr) * t), 0, 255);
/* 4554 */     int g = class_3532.method_15340(Math.round(cg + (tg - cg) * t), 0, 255);
/* 4555 */     int b = class_3532.method_15340(Math.round(cb + (tb - cb) * t), 0, 255);
/*      */     
/* 4557 */     return a << 24 | r << 16 | g << 8 | b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderMemoryHud(class_332 context, class_310 client, MemoryHud module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 4577 */     renderRectHud(context, client, (RectHudModule)module, text, hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, 
/*      */         
/* 4579 */         getTextHudBaseWidth(client, text), 20.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderTpsHud(class_332 context, class_310 client, TpsHud module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 4599 */     renderRectHud(context, client, (RectHudModule)module, text, hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, 
/*      */         
/* 4601 */         getTextHudBaseWidth(client, text), 20.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderComboHud(class_332 context, class_310 client, ComboCounterHud module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 4621 */     renderRectHud(context, client, (RectHudModule)module, text, hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, 
/*      */         
/* 4623 */         getTextHudBaseWidth(client, text), 20.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderServerAddressHud(class_332 context, class_310 client, ServerAddressHud module, String text, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 4643 */     renderRectHud(context, client, (RectHudModule)module, text, hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, 
/*      */         
/* 4645 */         getTextHudBaseWidth(client, text), 20.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4654 */     if (module.displayServerIcon.isValue()) {
/* 4655 */       float scale = module.getRenderHudScale();
/* 4656 */       float hudHeight = 20.0F * scale;
/* 4657 */       module.renderServerIcon(context, module.getHudX(), module.getHudY(), hudHeight, inverseGuiScale);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getWailaText(class_310 client) {
/* 4662 */     WailaHud waila = WailaHud.getInstance();
/* 4663 */     if (client.field_1724 == null || client.field_1687 == null) {
/* 4664 */       return waila.alwaysShow.isValue() ? "No target" : "";
/*      */     }
/*      */     
/* 4667 */     class_239 hit = client.field_1724.method_5745(4.5D, 0.0F, false);
/*      */     
/* 4669 */     if (hit.method_17783() == class_239.class_240.field_1333) {
/* 4670 */       return waila.alwaysShow.isValue() ? "No target" : "";
/*      */     }
/*      */     
/* 4673 */     StringBuilder sb = new StringBuilder();
/*      */     
/* 4675 */     if (hit.method_17783() == class_239.class_240.field_1332) {
/* 4676 */       class_2338 pos = ((class_3965)hit).method_17777();
/* 4677 */       class_2680 state = client.field_1687.method_8320(pos);
/* 4678 */       class_2248 block = state.method_26204();
/*      */ 
/*      */       
/* 4681 */       String blockName = block.method_9518().getString();
/* 4682 */       sb.append(blockName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4692 */       if (waila.showCoordinates.isValue() && 
/* 4693 */         !StreamerMode.getInstance().isHideCoordinatesEnabled()) {
/* 4694 */         sb.append("\n(").append(pos.method_10263())
/* 4695 */           .append(", ").append(pos.method_10264())
/* 4696 */           .append(", ").append(pos.method_10260())
/* 4697 */           .append(')');
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4707 */       if (waila.showCorrectTool.isValue()) {
/* 4708 */         sb.append("\nCorrect Tool: ").append(phaze$resolveCorrectTool(state));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4715 */       if (waila.showBreakTime.isValue()) {
/* 4716 */         if (client.field_1724.method_7337()) {
/* 4717 */           sb.append("\nBreak Time: Instant");
/*      */         } else {
/* 4719 */           float hardness = state.method_26214((class_1922)client.field_1687, pos);
/* 4720 */           if (hardness <= 0.0F) {
/* 4721 */             sb.append("\nBreak Time: Instant");
/*      */           } else {
/* 4723 */             float breakSpeed = client.field_1724.method_7351(state);
/* 4724 */             if (breakSpeed <= 0.0F) {
/* 4725 */               sb.append("\nBreak Time: Never");
/*      */             } else {
/* 4727 */               boolean canHarvest = client.field_1724.method_7305(state);
/* 4728 */               int divider = canHarvest ? 30 : 100;
/* 4729 */               float breakTime = hardness / breakSpeed * divider / 20.0F;
/* 4730 */               if (breakTime < 0.05F) {
/* 4731 */                 sb.append("\nBreak Time: Instant");
/*      */               } else {
/* 4733 */                 sb.append(String.format("\nBreak Time: %.2fs", new Object[] { Float.valueOf(breakTime) }));
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 4740 */     } else if (hit.method_17783() == class_239.class_240.field_1331 && waila.showEntities.isValue()) {
/* 4741 */       class_1297 entity = ((class_3966)hit).method_17782();
/* 4742 */       String entityName = entity.method_5477().getString();
/* 4743 */       sb.append(entityName);
/*      */ 
/*      */       
/* 4746 */       class_1299<?> entityType = entity.method_5864();
/* 4747 */       sb.append("\nType: ").append(entityType.method_5897().getString());
/*      */     } 
/*      */     
/* 4750 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String phaze$resolveCorrectTool(class_2680 state) {
/* 4777 */     String tier = "";
/* 4778 */     if (state.method_26164(class_3481.field_33717)) {
/* 4779 */       tier = "Diamond ";
/* 4780 */     } else if (state.method_26164(class_3481.field_33718)) {
/* 4781 */       tier = "Iron ";
/* 4782 */     } else if (state.method_26164(class_3481.field_33719)) {
/* 4783 */       tier = "Stone ";
/*      */     } 
/* 4785 */     if (state.method_26164(class_3481.field_33715)) return tier + "Pickaxe"; 
/* 4786 */     if (state.method_26164(class_3481.field_33713)) return tier + "Axe"; 
/* 4787 */     if (state.method_26164(class_3481.field_33716)) return tier + "Shovel"; 
/* 4788 */     if (state.method_26164(class_3481.field_33714)) return tier + "Hoe"; 
/* 4789 */     if (state.method_26164(class_3481.field_44469)) return "Sword"; 
/* 4790 */     return "Hand";
/*      */   }
/*      */   
/*      */   private class_1799 getWailaIcon(class_310 client) {
/* 4794 */     WailaHud waila = WailaHud.getInstance();
/* 4795 */     if (!waila.showIcon.isValue()) {
/* 4796 */       return null;
/*      */     }
/*      */     
/* 4799 */     if (client.field_1724 == null || client.field_1687 == null) {
/* 4800 */       return null;
/*      */     }
/*      */     
/* 4803 */     class_239 hit = client.field_1724.method_5745(4.5D, 0.0F, false);
/*      */     
/* 4805 */     if (hit.method_17783() == class_239.class_240.field_1333)
/*      */     {
/* 4807 */       return new class_1799((class_1935)class_1802.field_8077);
/*      */     }
/*      */     
/* 4810 */     if (hit.method_17783() == class_239.class_240.field_1332) {
/* 4811 */       class_2338 pos = ((class_3965)hit).method_17777();
/* 4812 */       class_2680 state = client.field_1687.method_8320(pos);
/* 4813 */       class_2248 block = state.method_26204();
/*      */ 
/*      */       
/* 4816 */       class_1792 item = block.method_8389();
/* 4817 */       if (item != class_1802.field_8162) {
/* 4818 */         return new class_1799((class_1935)item);
/*      */       }
/* 4820 */     } else if (hit.method_17783() == class_239.class_240.field_1331 && waila.showEntities.isValue()) {
/* 4821 */       class_1297 entity = ((class_3966)hit).method_17782();
/*      */ 
/*      */       
/* 4824 */       if (entity.method_5864() == class_1299.field_6043 || entity.method_5864() == class_1299.field_28401) {
/* 4825 */         class_1533 itemFrame = (class_1533)entity;
/* 4826 */         class_1799 heldItem = itemFrame.method_6940();
/* 4827 */         if (!heldItem.method_7960()) {
/* 4828 */           return heldItem;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 4834 */         String entityName = entity.method_5864().method_5897().getString().toLowerCase().replace(" ", "_");
/* 4835 */         class_2960 spawnEggId = class_2960.method_60655("minecraft", entityName + "_spawn_egg");
/* 4836 */         class_1792 spawnEgg = (class_1792)class_7923.field_41178.method_63535(spawnEggId);
/* 4837 */         if (spawnEgg != null && spawnEgg != class_1802.field_8162) {
/* 4838 */           return new class_1799((class_1935)spawnEgg);
/*      */         }
/* 4840 */       } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4845 */       return new class_1799((class_1935)class_1802.field_8077);
/*      */     } 
/*      */     
/* 4848 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderWailaHud(class_332 context, class_310 client, WailaHud module, String text, class_1799 icon, int hudIndex, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 4869 */     float lineHeight = 10.0F;
/* 4870 */     float scale = module.getRenderHudScale();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4875 */     float nominalIconSize = lineHeight * 2.0F / 1.1F;
/* 4876 */     boolean noTarget = (text.isEmpty() || text.contains("No target"));
/*      */ 
/*      */     
/* 4879 */     String[] lines = text.split("\n");
/* 4880 */     float maxTextWidth = 0.0F;
/* 4881 */     for (String line : lines) {
/* 4882 */       if (!line.isEmpty()) {
/* 4883 */         maxTextWidth = Math.max(maxTextWidth, getHudTextWidth(client, line, 8.0F));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4893 */     float baseHeight = noTarget ? 20.0F : (lines.length * lineHeight + 2.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4903 */     float effectiveIconSize = (icon != null) ? Math.max(8.0F, Math.min(nominalIconSize, baseHeight - 4.0F)) : 0.0F;
/* 4904 */     float iconOffset = (icon != null) ? (effectiveIconSize + 3.0F) : 0.0F;
/*      */     
/* 4906 */     float baseWidth = iconOffset + maxTextWidth;
/*      */ 
/*      */     
/* 4909 */     baseWidth += 4.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4915 */     baseWidth += 4.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4920 */     if (noTarget) {
/* 4921 */       baseWidth += 2.0F;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 4929 */       baseWidth += 2.0F;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4941 */     boolean liftTop = !noTarget;
/* 4942 */     if (liftTop) {
/* 4943 */       context.method_51448().method_22903();
/* 4944 */       context.method_51448().method_46416(0.0F, -4.0F, 0.0F);
/*      */     } 
/*      */ 
/*      */     
/* 4948 */     renderRectHud(context, client, (RectHudModule)module, "", hudIndex, chatEditing, mouseX, mouseY, mouseDown, deltaSeconds, inverseGuiScale, screenWidth, screenHeight, screenCenterX, screenCenterY, baseWidth, baseHeight);
/*      */ 
/*      */ 
/*      */     
/* 4952 */     float x = module.getHudX();
/* 4953 */     float y = module.getHudY();
/*      */     
/* 4955 */     context.method_51448().method_22903();
/* 4956 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4963 */     float iconLocalX = 5.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4970 */     if (icon != null) {
/* 4971 */       float iconLocalY = (baseHeight - effectiveIconSize) * 0.5F;
/* 4972 */       context.method_51448().method_22903();
/* 4973 */       context.method_51448().method_46416(x, y, 450.0F);
/* 4974 */       context.method_51448().method_22905(scale, scale, 1.0F);
/* 4975 */       context.method_51448().method_46416(iconLocalX, iconLocalY, 0.0F);
/* 4976 */       float iconDrawScale = effectiveIconSize / 16.0F;
/* 4977 */       context.method_51448().method_22905(iconDrawScale, iconDrawScale, 1.0F);
/*      */       
/* 4979 */       RenderSystem.disableBlend();
/* 4980 */       context.method_51427(icon, 0, 0);
/* 4981 */       context.method_51452();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4987 */       RenderSystem.enableBlend();
/* 4988 */       context.method_51448().method_22909();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5005 */     float totalTextHeight = lines.length * lineHeight;
/* 5006 */     float verticalOffset = (baseHeight - totalTextHeight) * 0.5F;
/* 5007 */     if (noTarget) {
/* 5008 */       verticalOffset++;
/*      */     } else {
/* 5010 */       verticalOffset++;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5017 */     float textXOffset = iconOffset + 4.0F + 4.0F;
/* 5018 */     for (int i = 0; i < lines.length; i++) {
/* 5019 */       String line = lines[i];
/* 5020 */       renderScaledHudText(context, client, line, x, y, textXOffset, verticalOffset + i * lineHeight, 8.0F, scale, true);
/*      */     } 
/*      */     
/* 5023 */     context.method_51448().method_22909();
/*      */     
/* 5025 */     if (liftTop) {
/* 5026 */       context.method_51448().method_22909();
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderZoomLevel(class_332 context, class_310 client, float screenWidth, float screenHeight) {
/* 5031 */     if (!Zoom.getInstance().isShowCurrentZoom() || !Zoom.isZoomActive()) {
/*      */       return;
/*      */     }
/*      */     
/* 5035 */     String displayText = Zoom.getInstance().getFormattedZoomLevel();
/*      */     
/* 5037 */     float textWidth = client.field_1772.method_1727(displayText);
/* 5038 */     float x = (screenWidth - textWidth) / 2.0F;
/* 5039 */     float y = screenHeight - 50.0F;
/*      */     
/* 5041 */     context.method_51439(client.field_1772, (class_2561)class_2561.method_43470(displayText), (int)x, (int)y, -1, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Unique
/* 5073 */   private float phaze$satUnclampedFlashAlpha = 0.0F; @Unique
/* 5074 */   private float phaze$satFlashAlpha = 0.0F; @Unique
/* 5075 */   private byte phaze$satAlphaDir = 1; @Unique
/* 5076 */   private final SaturationOffsetsCache phaze$satBarOffsets = new SaturationOffsetsCache(); @Unique
/* 5077 */   private final SaturationHeldFoodCache phaze$satHeldFood = new SaturationHeldFoodCache(); @Unique
/*      */   private boolean phaze$bubblesLifted = false; @Unique
/*      */   private static final int PHAZE_BUBBLE_LIFT_PX = 10; @Unique
/*      */   private static final int PHAZE_HOTBAR_SLOT_PX = 20; @Unique
/*      */   private static final int PHAZE_HOTBAR_SLOTS = 9; @Unique
/*      */   private static final int PHAZE_HOTBAR_PIXEL_W = 180; @Unique
/*      */   private static final int PHAZE_HOTBAR_BG_W = 182;
/*      */   @Unique
/*      */   private static final int PHAZE_HOTBAR_BG_H = 22;
/*      */   @Unique
/* 5087 */   private float phaze$hotbarCurrentSlotX = 0.0F; @Unique
/* 5088 */   private int phaze$hotbarLastSelected = -1; @Unique
/* 5089 */   private long phaze$hotbarLastFrameNanos = 0L; @Unique
/*      */   private boolean phaze$hotbarShouldDrawMirror = false; @Unique
/* 5091 */   private int phaze$hotbarMirrorOffsetX = 0; @Unique
/*      */   private int phaze$hotbarLastDrawX; @Unique
/*      */   private int phaze$hotbarLastDrawY; @Unique
/*      */   private int phaze$hotbarLastWidth; @Unique
/*      */   private int phaze$hotbarLastHeight; @Unique
/*      */   private class_2960 phaze$hotbarLastTexture; @Unique
/*      */   private Function<class_2960, ?> phaze$hotbarLastSpriteFn; @Unique
/*      */   private boolean phaze$hotbarScissorOn = false; @Unique
/*      */   private boolean phaze$tabWasOpenedThisCycle = false;
/*      */   @Unique
/*      */   private static boolean phaze$pmWasMouseDown = false;
/*      */   @Unique
/*      */   private static final int PHAZE_INV_SLOT = 18;
/*      */   @Unique
/*      */   private static final int PHAZE_INV_ICON = 16;
/*      */   @Unique
/*      */   private static final int PHAZE_INV_BORDER = 7;
/*      */   @Unique
/*      */   private static final int PHAZE_INV_SLOT_OFFSET = 8;
/*      */   @Unique
/* 5111 */   private static final class_2960 PHAZE_INV_PANEL_SPRITE = class_2960.method_60655("phaze", "shulker_box_tooltip");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderStatusBars"}, at = {@At("TAIL")})
/*      */   private void phaze$onSaturationRenderStatusBars(class_332 context, CallbackInfo ci) {
/* 5119 */     if (!Saturation.getInstance().isEnabled())
/* 5120 */       return;  class_310 mc = class_310.method_1551();
/* 5121 */     if (mc.field_1724 == null)
/*      */       return; 
/* 5123 */     class_746 class_746 = mc.field_1724;
/* 5124 */     class_1702 stats = class_746.method_7344();
/* 5125 */     float saturationLevel = stats.method_7589();
/* 5126 */     if (saturationLevel <= 0.0F) {
/* 5127 */       phaze$resetSaturationFlash();
/*      */       
/*      */       return;
/*      */     } 
/* 5131 */     int right = context.method_51421() / 2 + 91;
/* 5132 */     int top = context.method_51443() - 39;
/* 5133 */     phaze$drawSaturationOverlay(context, saturationLevel, 0.0F, 1.0F, mc.field_1705.method_1738(), right, top);
/*      */     
/* 5135 */     class_1799 heldItem = class_746.method_6047();
/* 5136 */     if (heldItem.method_7960()) heldItem = class_746.method_6079(); 
/* 5137 */     if (heldItem.method_7960() || !heldItem.method_57826(class_9334.field_50075)) {
/* 5138 */       phaze$resetSaturationFlash();
/*      */       return;
/*      */     } 
/* 5141 */     phaze$satOnClientTick();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderAirBubbles"}, at = {@At("HEAD")})
/*      */   private void phaze$liftAirBubblesHead(class_332 context, class_1657 player, int heartCount, int maxAirBubbles, int top, CallbackInfo ci) {
/* 5148 */     if (phaze$shouldLiftBubbles()) {
/* 5149 */       context.method_51448().method_22903();
/* 5150 */       context.method_51448().method_46416(0.0F, -10.0F, 0.0F);
/* 5151 */       this.phaze$bubblesLifted = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderAirBubbles"}, at = {@At("RETURN")})
/*      */   private void phaze$liftAirBubblesReturn(class_332 context, class_1657 player, int heartCount, int maxAirBubbles, int top, CallbackInfo ci) {
/* 5159 */     if (this.phaze$bubblesLifted) {
/* 5160 */       context.method_51448().method_22909();
/* 5161 */       this.phaze$bubblesLifted = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private static boolean phaze$shouldLiftBubbles() {
/* 5167 */     Saturation sat = Saturation.getInstance();
/* 5168 */     if (sat == null || !sat.isEnabled()) return false; 
/* 5169 */     return "Second Hunger Bar".equals(sat.style.getSelected());
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private void phaze$drawSaturationOverlay(class_332 context, float saturationLevel, float saturationGained, float alpha, int guiTicks, int right, int top) {
/* 5174 */     if (saturationLevel + saturationGained < 0.0F)
/* 5175 */       return;  int alphaColor = ColorHelper.argbFromRGBA(1.0F, 1.0F, 1.0F, alpha);
/* 5176 */     float modifiedSaturation = Math.max(0.0F, Math.min(saturationLevel + saturationGained, 20.0F));
/* 5177 */     int startSaturationBar = 0;
/* 5178 */     int endSaturationBar = (int)Math.ceil((modifiedSaturation / 2.0F));
/* 5179 */     if (saturationGained != 0.0F) startSaturationBar = (int)Math.max(saturationLevel / 2.0F, 0.0F);
/*      */     
/* 5181 */     int iconSize = 9;
/* 5182 */     int saturationTop = top - 10;
/* 5183 */     String styleValue = (Saturation.getInstance()).style.getSelected();
/* 5184 */     boolean isYellowBar = styleValue.equals("Yellow Bar");
/*      */     
/* 5186 */     for (int i = startSaturationBar; i < endSaturationBar; i++) {
/* 5187 */       int x = right - i * 8 - 9;
/* 5188 */       int y = saturationTop;
/* 5189 */       float effectiveSaturationOfBar = modifiedSaturation / 2.0F - i;
/*      */       
/* 5191 */       if (!isYellowBar)
/* 5192 */       { if (effectiveSaturationOfBar > 0.0F) {
/* 5193 */           class_2960 foodTexture = null;
/* 5194 */           if (effectiveSaturationOfBar >= 1.0F) {
/* 5195 */             foodTexture = TextureHelper.FOOD_FULL_TEXTURE;
/* 5196 */           } else if (effectiveSaturationOfBar >= 0.5D) {
/* 5197 */             foodTexture = TextureHelper.FOOD_HALF_TEXTURE;
/*      */           } 
/* 5199 */           if (foodTexture != null)
/* 5200 */           { context.method_52706(class_1921::method_62277, TextureHelper.FOOD_EMPTY_TEXTURE, x, y, iconSize, iconSize);
/* 5201 */             context.method_52706(class_1921::method_62277, foodTexture, x, y, iconSize, iconSize); } 
/*      */         }  }
/* 5203 */       else { int hungerY = top;
/* 5204 */         int v = 0;
/* 5205 */         int u = 0;
/* 5206 */         if (effectiveSaturationOfBar >= 1.0F) { u = 3 * iconSize; }
/* 5207 */         else if (effectiveSaturationOfBar > 0.5D) { u = 2 * iconSize; }
/* 5208 */         else if (effectiveSaturationOfBar > 0.25D) { u = 1 * iconSize; }
/* 5209 */          context.method_25291(class_1921::method_62277, TextureHelper.MOD_ICONS, x, hungerY, u, v, iconSize, iconSize, 256, 256, alphaColor); }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private void phaze$satOnClientTick() {
/* 5216 */     this.phaze$satUnclampedFlashAlpha += this.phaze$satAlphaDir * 0.125F;
/* 5217 */     if (this.phaze$satUnclampedFlashAlpha >= 1.5F) { this.phaze$satAlphaDir = -1; }
/* 5218 */     else if (this.phaze$satUnclampedFlashAlpha <= -0.5F) { this.phaze$satAlphaDir = 1; }
/* 5219 */      this.phaze$satFlashAlpha = Math.max(0.0F, Math.min(1.0F, this.phaze$satUnclampedFlashAlpha));
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private void phaze$resetSaturationFlash() {
/* 5224 */     this.phaze$satUnclampedFlashAlpha = this.phaze$satFlashAlpha = 0.0F;
/* 5225 */     this.phaze$satAlphaDir = 1;
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private static class SaturationOffsetsCache {
/* 5230 */     private final Vector<IntPoint> foodBarOffsets = new Vector<>();
/* 5231 */     private int lastGuiTick = 0;
/* 5232 */     private final Random random = new Random();
/*      */     
/*      */     private void generate(int guiTicks, class_1657 player) {
/* 5235 */       int preferFoodBars = 10;
/* 5236 */       if (this.foodBarOffsets.size() != 10) this.foodBarOffsets.setSize(10); 
/* 5237 */       this.random.setSeed((guiTicks * 312871));
/* 5238 */       for (int i = 0; i < 10; i++) {
/* 5239 */         int x = -(i * 8) - 9;
/* 5240 */         int y = 0;
/* 5241 */         IntPoint point = this.foodBarOffsets.get(i);
/* 5242 */         if (point == null) {
/* 5243 */           point = new IntPoint();
/* 5244 */           this.foodBarOffsets.set(i, point);
/*      */         } 
/* 5246 */         point.x = x;
/* 5247 */         point.y = y;
/*      */       } 
/* 5249 */       this.lastGuiTick = guiTicks;
/*      */     }
/*      */     
/*      */     public Vector<IntPoint> foodBarOffsets(int guiTicks, class_1657 player) {
/* 5253 */       if (guiTicks != this.lastGuiTick) generate(guiTicks, player); 
/* 5254 */       return this.foodBarOffsets;
/*      */     } }
/*      */   @Unique
/*      */   private static class SaturationHeldFoodCache { private class_1799 lastHeldItem;
/*      */     private int lastGuiTick;
/*      */     
/*      */     private SaturationHeldFoodCache() {
/* 5261 */       this.lastGuiTick = 0;
/*      */     }
/*      */     public class_1799 result(int guiTick, class_1657 player) {
/* 5264 */       if (guiTick != this.lastGuiTick) {
/* 5265 */         class_1799 heldItem = player.method_6047();
/* 5266 */         if (heldItem.method_7960()) heldItem = player.method_6079(); 
/* 5267 */         this.lastHeldItem = heldItem;
/* 5268 */         this.lastGuiTick = guiTick;
/*      */       } 
/* 5270 */       return this.lastHeldItem;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V"}, at = {@At("TAIL")})
/*      */   private void phaze$drawCooldownNumber(class_332 context, int x, int y, class_9779 tickCounter, class_1657 player, class_1799 stack, int seed, CallbackInfo ci) {
/* 5286 */     Cooldowns module = Cooldowns.getInstance();
/* 5287 */     if (module == null || !module.isEnabled())
/* 5288 */       return;  if (stack == null || stack.method_7960() || player == null)
/* 5289 */       return;  class_1796 manager = player.method_7357();
/* 5290 */     if (manager == null)
/* 5291 */       return;  float tickDelta = tickCounter.method_60637(false);
/* 5292 */     float progress = manager.method_7905(stack, tickDelta);
/* 5293 */     if (progress <= 0.0F)
/*      */       return; 
/* 5295 */     class_2960 groupId = manager.method_62836(stack);
/* 5296 */     class_1796.class_1797 entry = (class_1796.class_1797)manager.field_8024.get(groupId);
/* 5297 */     if (entry == null)
/* 5298 */       return;  float remainingTicks = entry.comp_3084 - manager.field_8025 + tickDelta;
/* 5299 */     if (remainingTicks <= 0.0F)
/* 5300 */       return;  float remainingSeconds = remainingTicks / 20.0F;
/*      */     
/* 5302 */     String text = module.formatSeconds(remainingSeconds);
/* 5303 */     class_310 mc = class_310.method_1551();
/* 5304 */     if (mc == null || mc.field_1772 == null)
/* 5305 */       return;  int color = module.colorForProgress(progress);
/* 5306 */     int textWidth = mc.field_1772.method_1727(text);
/* 5307 */     int drawX = x + 8 - textWidth / 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5314 */     int drawY = y + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5324 */     class_4587 matrices = context.method_51448();
/* 5325 */     matrices.method_22903();
/* 5326 */     matrices.method_46416(0.0F, 0.0F, 200.0F);
/* 5327 */     context.method_51433(mc.field_1772, text, drawX, drawY, color, module.textShadow.isValue());
/*      */ 
/*      */ 
/*      */     
/* 5331 */     context.method_51452();
/* 5332 */     matrices.method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderHotbar"}, at = {@At("HEAD")})
/*      */   private void phaze$prepareHotbarSlideDraw(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5341 */     this.phaze$hotbarScissorOn = false;
/*      */   }
/*      */   @Unique
/*      */   private void phaze$advanceHotbarSlide() {
/*      */     float dt;
/* 5346 */     this.phaze$hotbarShouldDrawMirror = false;
/* 5347 */     this.phaze$hotbarMirrorOffsetX = 0;
/* 5348 */     Animations module = Animations.getInstance();
/* 5349 */     if (module == null || !module.isHotbarSlideEnabled()) {
/* 5350 */       this.phaze$hotbarLastSelected = -1;
/* 5351 */       this.phaze$hotbarLastFrameNanos = 0L;
/* 5352 */       ExordiumAnimationBridge.updateHotbarAnimation(0.0F, 0.0F, false, 0);
/*      */       return;
/*      */     } 
/* 5355 */     class_310 mc = class_310.method_1551();
/* 5356 */     if (mc == null || mc.field_1724 == null) {
/* 5357 */       ExordiumAnimationBridge.updateHotbarAnimation(0.0F, 0.0F, false, 0);
/*      */       
/*      */       return;
/*      */     } 
/* 5361 */     int selected = (mc.field_1724.method_31548()).field_7545;
/* 5362 */     float target = (selected * 20);
/*      */     
/* 5364 */     if (module.isHotbarRolloverEnabled() && this.phaze$hotbarLastSelected >= 0) {
/* 5365 */       int slotDelta = selected - this.phaze$hotbarLastSelected;
/* 5366 */       if (slotDelta >= 5) { this.phaze$hotbarCurrentSlotX += 180.0F; }
/* 5367 */       else if (slotDelta <= -5) { this.phaze$hotbarCurrentSlotX -= 180.0F; }
/*      */     
/* 5369 */     }  this.phaze$hotbarLastSelected = selected;
/*      */     
/* 5371 */     long now = System.nanoTime();
/*      */     
/* 5373 */     if (this.phaze$hotbarLastFrameNanos == 0L) { dt = 0.016666668F; }
/*      */     else
/* 5375 */     { dt = (float)(now - this.phaze$hotbarLastFrameNanos) / 1.0E9F;
/* 5376 */       if (dt > 0.25F) dt = 0.25F;  }
/*      */     
/* 5378 */     this.phaze$hotbarLastFrameNanos = now;
/*      */     
/* 5380 */     float smoothness = module.smoothnessForSpeed(module.hotbarSpeed.getValue());
/* 5381 */     float decay = (float)Math.pow(smoothness, dt);
/* 5382 */     this.phaze$hotbarCurrentSlotX = (this.phaze$hotbarCurrentSlotX - target) * decay + target;
/* 5383 */     if (Math.abs(this.phaze$hotbarCurrentSlotX - target) < 0.4F) this.phaze$hotbarCurrentSlotX = target;
/*      */     
/* 5385 */     if (module.isHotbarRolloverEnabled()) {
/* 5386 */       float maxNormalX = 160.0F;
/* 5387 */       if (this.phaze$hotbarCurrentSlotX < 0.0F) {
/* 5388 */         this.phaze$hotbarShouldDrawMirror = true;
/* 5389 */         this.phaze$hotbarMirrorOffsetX = 180;
/* 5390 */       } else if (this.phaze$hotbarCurrentSlotX > maxNormalX) {
/* 5391 */         this.phaze$hotbarShouldDrawMirror = true;
/* 5392 */         this.phaze$hotbarMirrorOffsetX = -180;
/*      */       } 
/*      */     } 
/* 5395 */     ExordiumAnimationBridge.updateHotbarAnimation(this.phaze$hotbarCurrentSlotX, target, this.phaze$hotbarShouldDrawMirror, this.phaze$hotbarMirrorOffsetX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderHotbar"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1, shift = At.Shift.BEFORE)})
/*      */   private void phaze$openMirrorScissor(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5410 */     if (ExordiumAnimationBridge.isCapturingHotbar())
/* 5411 */       return;  if (!this.phaze$hotbarShouldDrawMirror)
/* 5412 */       return;  context.method_51452();
/* 5413 */     int hotbarLeft = context.method_51421() / 2 - 91;
/* 5414 */     int hotbarTop = context.method_51443() - 22;
/* 5415 */     context.method_44379(hotbarLeft, hotbarTop, hotbarLeft + 182, hotbarTop + 22);
/* 5416 */     this.phaze$hotbarScissorOn = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ModifyArgs(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
/*      */   private void phaze$slideSelectionX(Args args) {
/* 5426 */     Animations module = Animations.getInstance();
/* 5427 */     if (module == null || !module.isHotbarSlideEnabled())
/* 5428 */       return;  class_310 mc = class_310.method_1551();
/* 5429 */     if (mc == null || mc.field_1724 == null)
/*      */       return; 
/* 5431 */     if (ExordiumAnimationBridge.isCapturingHotbar()) {
/*      */ 
/*      */       
/* 5434 */       Function<class_2960, class_1921> layerFactory = (Function<class_2960, class_1921>)args.get(0);
/* 5435 */       ExordiumAnimationBridge.recordHotbarSelection(layerFactory, (class_2960)args
/*      */           
/* 5437 */           .get(1), ((Integer)args
/* 5438 */           .get(2)).intValue(), ((Integer)args
/* 5439 */           .get(3)).intValue(), ((Integer)args
/* 5440 */           .get(4)).intValue(), ((Integer)args
/* 5441 */           .get(5)).intValue());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5446 */       args.set(2, Integer.valueOf(-10000));
/* 5447 */       this.phaze$hotbarShouldDrawMirror = false;
/*      */       
/*      */       return;
/*      */     } 
/* 5451 */     int selectedSlot = (mc.field_1724.method_31548()).field_7545;
/* 5452 */     int origX = ((Integer)args.get(2)).intValue();
/* 5453 */     int baseX = origX - selectedSlot * 20;
/* 5454 */     int newX = baseX + Math.round(this.phaze$hotbarCurrentSlotX);
/* 5455 */     args.set(2, Integer.valueOf(newX));
/*      */     
/* 5457 */     if (this.phaze$hotbarShouldDrawMirror) {
/* 5458 */       this.phaze$hotbarLastDrawX = newX;
/* 5459 */       this.phaze$hotbarLastDrawY = ((Integer)args.get(3)).intValue();
/* 5460 */       this.phaze$hotbarLastWidth = ((Integer)args.get(4)).intValue();
/* 5461 */       this.phaze$hotbarLastHeight = ((Integer)args.get(5)).intValue();
/* 5462 */       this.phaze$hotbarLastTexture = (class_2960)args.get(1);
/* 5463 */       this.phaze$hotbarLastSpriteFn = (Function<class_2960, ?>)args.get(0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderHotbar"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1, shift = At.Shift.AFTER)})
/*      */   private void phaze$drawMirrorAndCloseScissor(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5475 */     if (!this.phaze$hotbarShouldDrawMirror || this.phaze$hotbarLastTexture == null || this.phaze$hotbarLastSpriteFn == null) {
/* 5476 */       if (this.phaze$hotbarScissorOn) {
/* 5477 */         context.method_51452();
/* 5478 */         context.method_44380();
/* 5479 */         this.phaze$hotbarScissorOn = false;
/*      */       } 
/*      */       return;
/*      */     } 
/* 5483 */     int mirrorX = this.phaze$hotbarLastDrawX + this.phaze$hotbarMirrorOffsetX;
/* 5484 */     context.method_52706(this.phaze$hotbarLastSpriteFn, this.phaze$hotbarLastTexture, mirrorX, this.phaze$hotbarLastDrawY, this.phaze$hotbarLastWidth, this.phaze$hotbarLastHeight);
/*      */ 
/*      */ 
/*      */     
/* 5488 */     context.method_51452();
/* 5489 */     if (this.phaze$hotbarScissorOn) {
/* 5490 */       context.method_44380();
/* 5491 */       this.phaze$hotbarScissorOn = false;
/*      */     } 
/* 5493 */     this.phaze$hotbarShouldDrawMirror = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderHotbar"}, at = {@At("TAIL")})
/*      */   private void phaze$drawHotbarUtilityHighlights(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5502 */     MaceIndicator mace = MaceIndicator.getInstance();
/* 5503 */     ItemHighlighter highlighter = ItemHighlighter.getInstance();
/* 5504 */     HealingHelper healing = HealingHelper.getInstance();
/* 5505 */     boolean maceEnabled = (mace != null && mace.isEnabled());
/* 5506 */     boolean highlighterEnabled = (highlighter != null && highlighter.isEnabled());
/* 5507 */     boolean healingEnabled = (healing != null && healing.isEnabled());
/* 5508 */     if (!maceEnabled && !highlighterEnabled && !healingEnabled)
/* 5509 */       return;  if (highlighterEnabled) highlighter.beginRenderPass(); 
/* 5510 */     if (healingEnabled) healing.beginRenderPass(); 
/* 5511 */     phaze$paintHotbarUtilityFills(context, 
/*      */         
/* 5513 */         maceEnabled ? mace : null, 
/* 5514 */         highlighterEnabled ? highlighter : null, 
/* 5515 */         healingEnabled ? healing : null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ModifyArg(method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"))
/*      */   private class_2561 phaze$hideOwnNickInSidebar(class_2561 original) {
/* 5529 */     NickHider hider = NickHider.getInstance();
/* 5530 */     if (hider == null || !hider.isEnabled()) return original; 
/* 5531 */     return hider.rewrite(original);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*      */   private void phaze$tabSlideTick(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5540 */     Animations module = Animations.getInstance();
/* 5541 */     if (module == null || !module.isTabSlideEnabled()) {
/* 5542 */       this.phaze$tabWasOpenedThisCycle = false;
/*      */       return;
/*      */     } 
/* 5545 */     if (this.field_2035 == null || this.field_2035.field_1690 == null) {
/* 5546 */       this.phaze$tabWasOpenedThisCycle = false;
/*      */       return;
/*      */     } 
/* 5549 */     if (this.field_2035.field_1690.field_1842) {
/* 5550 */       module.snapTabClosed();
/* 5551 */       this.phaze$tabWasOpenedThisCycle = false;
/*      */       
/*      */       return;
/*      */     } 
/* 5555 */     boolean keyPressed = this.field_2035.field_1690.field_1907.method_1434();
/* 5556 */     module.tickTabSlide(keyPressed);
/*      */     
/* 5558 */     if (keyPressed && 
/* 5559 */       phaze$vanillaTabListWouldRender()) this.phaze$tabWasOpenedThisCycle = true;
/*      */   
/*      */   }
/*      */   
/*      */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*      */   private void phaze$tabSlideRenderTail(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5565 */     Animations module = Animations.getInstance();
/* 5566 */     if (module == null || !module.isTabSlideEnabled()) {
/* 5567 */       this.phaze$tabWasOpenedThisCycle = false;
/*      */       return;
/*      */     } 
/* 5570 */     if (this.field_2035 == null || this.field_2035.field_1690 == null || this.field_2035.field_1690.field_1842) {
/* 5571 */       this.phaze$tabWasOpenedThisCycle = false;
/*      */       
/*      */       return;
/*      */     } 
/* 5575 */     boolean keyPressed = this.field_2035.field_1690.field_1907.method_1434();
/* 5576 */     if (keyPressed) {
/*      */       return;
/*      */     }
/* 5579 */     if (!module.isTabSlideRendering(false)) {
/* 5580 */       this.phaze$tabWasOpenedThisCycle = false;
/*      */       return;
/*      */     } 
/* 5583 */     if (!this.phaze$tabWasOpenedThisCycle)
/*      */       return; 
/* 5585 */     if (ExordiumAnimationBridge.renderClosingPlayerListFromCache()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5592 */     this.field_2015.method_1921(true);
/* 5593 */     class_269 scoreboard = (this.field_2035.field_1687 == null) ? null : this.field_2035.field_1687.method_8428();
/*      */     
/* 5595 */     class_266 objective = (scoreboard == null) ? null : scoreboard.method_1189(class_8646.field_45156);
/* 5596 */     this.field_2015.method_1919(context, context.method_51421(), scoreboard, objective);
/* 5597 */     this.field_2015.method_1921(false);
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private boolean phaze$vanillaTabListWouldRender() {
/* 5602 */     if (this.field_2035 == null || this.field_2035.field_1724 == null) return false; 
/* 5603 */     if (!this.field_2035.method_1542()) return true; 
/* 5604 */     if (this.field_2035.field_1724.field_3944 != null && this.field_2035.field_1724.field_3944
/* 5605 */       .method_45732().size() > 1) return true; 
/* 5606 */     if (this.field_2035.field_1687 != null) {
/* 5607 */       class_269 scoreboard = this.field_2035.field_1687.method_8428();
/* 5608 */       if (scoreboard != null && scoreboard
/* 5609 */         .method_1189(class_8646.field_45156) != null) return true; 
/*      */     } 
/* 5611 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Unique
/*      */   private void phaze$paintHotbarUtilityFills(class_332 context, MaceIndicator mace, ItemHighlighter highlighter, HealingHelper healing) {
/* 5626 */     class_310 mc = class_310.method_1551();
/* 5627 */     if (mc == null || mc.field_1724 == null)
/* 5628 */       return;  class_746 class_746 = mc.field_1724;
/*      */     
/* 5630 */     int screenW = context.method_51421();
/* 5631 */     int screenH = context.method_51443();
/* 5632 */     int centerX = screenW / 2;
/* 5633 */     int itemY = screenH - 16 - 3;
/*      */     
/* 5635 */     context.method_51452();
/* 5636 */     RenderSystem.enableBlend();
/* 5637 */     RenderSystem.defaultBlendFunc();
/* 5638 */     RenderSystem.disableDepthTest();
/* 5639 */     RenderSystem.depthMask(false);
/*      */     
/* 5641 */     for (int n = 0; n < 9; n++) {
/* 5642 */       class_1799 stack = (class_1799)(class_746.method_31548()).field_7547.get(n);
/* 5643 */       int itemX = centerX - 90 + n * 20 + 2;
/* 5644 */       if (mace != null) {
/* 5645 */         phaze$fillHotbarOverlay(context, itemX, itemY, mace.colorForStack(stack));
/*      */       }
/* 5647 */       if (highlighter != null) {
/* 5648 */         phaze$fillHotbarOverlay(context, itemX, itemY, highlighter.colorForPreparedStack(stack));
/*      */       }
/* 5650 */       if (healing != null) {
/* 5651 */         phaze$fillHotbarOverlay(context, itemX, itemY, healing.colorForPreparedStack(stack));
/*      */       }
/*      */     } 
/* 5654 */     context.method_51452();
/* 5655 */     RenderSystem.depthMask(true);
/* 5656 */     phaze$resetGuiRenderState();
/*      */   }
/*      */   
/*      */   @Unique
/*      */   private static void phaze$fillHotbarOverlay(class_332 context, int x, int y, int color) {
/* 5661 */     if ((color & 0xFF000000) != 0) {
/* 5662 */       context.method_25294(x, y, x + 16, y + 16, color);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"renderHotbar"}, at = {@At("TAIL")})
/*      */   private void phaze$flushHotbarBatch(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/* 5672 */     context.method_51452();
/* 5673 */     phaze$resetGuiRenderState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*      */   private void phaze$drawPlayerModel(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
/*      */     Quaternionf bodyRotation;
/* 5682 */     PlayerModelHud module = PlayerModelHud.getInstance();
/* 5683 */     if (module == null || !module.isEnabled())
/* 5684 */       return;  class_310 mc = class_310.method_1551();
/* 5685 */     if (mc == null || mc.field_1724 == null || mc.field_1690 == null)
/* 5686 */       return;  if (mc.field_1690.field_1842)
/*      */       return; 
/* 5688 */     int hudIndex = 24;
/* 5689 */     class_746 player = mc.field_1724;
/* 5690 */     float baseSize = module.getBaseModelSize();
/* 5691 */     module.setHudScale(class_3532.method_15363(module.getHudScale(), module.getMinHudScale(), module.getMaxHudScale()));
/* 5692 */     float scale = module.getRenderHudScale();
/* 5693 */     float panelW = 2.0F * baseSize * scale;
/* 5694 */     float panelH = 2.0F * baseSize * scale;
/*      */     
/* 5696 */     boolean chatEditing = mc.field_1755 instanceof net.minecraft.class_408;
/* 5697 */     boolean mouseDown = (chatEditing && GLFW.glfwGetMouseButton(mc
/* 5698 */         .method_22683().method_4490(), 0) == 1);
/* 5699 */     double scaleFactor = mc.method_22683().method_4495();
/* 5700 */     if (scaleFactor <= 0.0D) scaleFactor = 1.0D; 
/* 5701 */     float mouseX = (float)(mc.field_1729.method_1603() / scaleFactor);
/* 5702 */     float mouseY = (float)(mc.field_1729.method_1604() / scaleFactor);
/*      */     
/* 5704 */     int scaledScreenW = mc.method_22683().method_4486();
/* 5705 */     int scaledScreenH = mc.method_22683().method_4502();
/* 5706 */     float maxX = Math.max(0.0F, scaledScreenW - panelW);
/* 5707 */     float maxY = Math.max(0.0F, scaledScreenH - panelH);
/*      */     
/* 5709 */     float panelX = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 5710 */     float panelY = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 5711 */     module.setHudX(panelX);
/* 5712 */     module.setHudY(panelY);
/*      */     
/* 5714 */     float hoverInsetX = 7.0F;
/* 5715 */     float hoverX = panelX + 7.0F;
/* 5716 */     float hoverW = Math.max(12.0F, panelW - 14.0F);
/* 5717 */     int handleSize = Math.max(4, Math.min(8, Math.round(4.0F * scale)));
/* 5718 */     float handleX = hoverX + hoverW - handleSize / 2.0F;
/* 5719 */     float handleY = panelY + panelH - handleSize / 2.0F;
/* 5720 */     boolean hoveredHud = false;
/* 5721 */     boolean hoveredHandle = false;
/* 5722 */     boolean nearHud = false;
/*      */     
/* 5724 */     if (chatEditing) {
/* 5725 */       hoveredHud = isHovered(mouseX, mouseY, hoverX, panelY, hoverW, panelH);
/* 5726 */       hoveredHandle = isHovered(mouseX, mouseY, handleX, handleY, handleSize, handleSize);
/* 5727 */       nearHud = isNearRect(mouseX, mouseY, hoverX, panelY, hoverW, panelH, Math.max(12.0F, 10.0F * scale));
/*      */       
/* 5729 */       if (!mouseDown) {
/* 5730 */         RECT_DRAGGING[24] = false;
/* 5731 */         RECT_RESIZING[24] = false;
/* 5732 */       } else if (!phaze$pmWasMouseDown && !isAnyHudInteractionActive()) {
/* 5733 */         if (hoveredHandle) {
/* 5734 */           RECT_RESIZING[24] = true;
/* 5735 */           RECT_RESIZE_START_WIDTH[24] = scale;
/* 5736 */           RECT_RESIZE_START_MOUSE_X[24] = mouseX;
/* 5737 */           RECT_RESIZE_START_MOUSE_Y[24] = mouseY;
/* 5738 */         } else if (hoveredHud) {
/* 5739 */           RECT_DRAGGING[24] = true;
/* 5740 */           RECT_DRAG_OFFSET_X[24] = mouseX - panelX;
/* 5741 */           RECT_DRAG_OFFSET_Y[24] = mouseY - panelY;
/*      */         } 
/*      */       } 
/*      */       
/* 5745 */       if (mouseDown) {
/* 5746 */         if (RECT_DRAGGING[24]) {
/* 5747 */           float newX = class_3532.method_15363(mouseX - RECT_DRAG_OFFSET_X[24], 0.0F, maxX);
/* 5748 */           float newY = class_3532.method_15363(mouseY - RECT_DRAG_OFFSET_Y[24], 0.0F, maxY);
/* 5749 */           float dragCenterX = newX + panelW * 0.5F;
/* 5750 */           float dragCenterY = newY + panelH * 0.5F;
/* 5751 */           boolean snappedToScreenX = false;
/* 5752 */           boolean snappedToScreenY = false;
/*      */           
/* 5754 */           if (Math.abs(dragCenterX - scaledScreenW * 0.5F) <= 3.0F) {
/* 5755 */             newX = class_3532.method_15363(scaledScreenW * 0.5F - panelW * 0.5F, 0.0F, maxX);
/* 5756 */             showVerticalGuideThisFrame = true;
/* 5757 */             snappedToScreenX = true;
/*      */           } 
/* 5759 */           if (Math.abs(dragCenterY - scaledScreenH * 0.5F) <= 3.0F) {
/* 5760 */             newY = class_3532.method_15363(scaledScreenH * 0.5F - panelH * 0.5F, 0.0F, maxY);
/* 5761 */             showHorizontalGuideThisFrame = true;
/* 5762 */             snappedToScreenY = true;
/*      */           } 
/* 5764 */           if (!snappedToScreenX) {
/* 5765 */             newX = snapHudAlignmentX(24, newX, newY, panelW, panelH, maxX, (float)scaleFactor);
/*      */           }
/*      */ 
/*      */           
/* 5769 */           if (!snappedToScreenY) {
/* 5770 */             newY = snapHudAlignmentY(24, newX, newY, panelW, panelH, maxY, (float)scaleFactor);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 5775 */           panelX = newX;
/* 5776 */           panelY = newY;
/* 5777 */           module.setHudX(panelX);
/* 5778 */           module.setHudY(panelY);
/* 5779 */           hoverX = panelX + 7.0F;
/* 5780 */           handleX = hoverX + hoverW - handleSize / 2.0F;
/* 5781 */           handleY = panelY + panelH - handleSize / 2.0F;
/* 5782 */         } else if (RECT_RESIZING[24]) {
/* 5783 */           float deltaX = mouseX - RECT_RESIZE_START_MOUSE_X[24];
/* 5784 */           float deltaY = mouseY - RECT_RESIZE_START_MOUSE_Y[24];
/* 5785 */           float delta = (deltaX + deltaY) * 0.5F;
/* 5786 */           float newScale = (RECT_RESIZE_START_WIDTH[24] + delta * 0.9F / baseSize * 2.0F) / 2.0F;
/*      */ 
/*      */ 
/*      */           
/* 5790 */           newScale = snapAndAnnounceHudScale((Module)module, newScale, module
/* 5791 */               .getMinHudScale(), module.getMaxHudScale());
/* 5792 */           module.setHudScale(newScale);
/* 5793 */           scale = module.getRenderHudScale();
/* 5794 */           panelW = 2.0F * baseSize * scale;
/* 5795 */           panelH = 2.0F * baseSize * scale;
/* 5796 */           maxX = Math.max(0.0F, scaledScreenW - panelW);
/* 5797 */           maxY = Math.max(0.0F, scaledScreenH - panelH);
/* 5798 */           panelX = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 5799 */           panelY = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 5800 */           module.setHudX(panelX);
/* 5801 */           module.setHudY(panelY);
/* 5802 */           hoverX = panelX + 7.0F;
/* 5803 */           hoverW = Math.max(12.0F, panelW - 14.0F);
/* 5804 */           handleSize = Math.max(4, Math.min(8, Math.round(4.0F * scale)));
/* 5805 */           handleX = hoverX + hoverW - handleSize / 2.0F;
/* 5806 */           handleY = panelY + panelH - handleSize / 2.0F;
/*      */         } 
/*      */       }
/*      */     } else {
/* 5810 */       RECT_DRAGGING[24] = false;
/* 5811 */       RECT_RESIZING[24] = false;
/*      */     } 
/*      */     
/* 5814 */     if (RECT_DRAGGING[24]) {
/* 5815 */       float dragCenterX = panelX + panelW * 0.5F;
/* 5816 */       float dragCenterY = panelY + panelH * 0.5F;
/* 5817 */       if (Math.abs(dragCenterX - scaledScreenW * 0.5F) <= 3.0F) {
/* 5818 */         showVerticalGuideThisFrame = true;
/*      */       }
/* 5820 */       if (Math.abs(dragCenterY - scaledScreenH * 0.5F) <= 3.0F) {
/* 5821 */         showHorizontalGuideThisFrame = true;
/*      */       }
/*      */     } 
/*      */     
/* 5825 */     float hoverTarget = (chatEditing && hoveredHud) ? 1.0F : 0.0F;
/* 5826 */     RECT_HOVER_PROGRESS[24] = approachExp(RECT_HOVER_PROGRESS[24], hoverTarget, 10.0F, 
/*      */ 
/*      */ 
/*      */         
/* 5830 */         getHudDelta((RectHudModule)module, chatEditing, tickCounter.method_60637(false)));
/*      */     
/* 5832 */     phaze$pmWasMouseDown = mouseDown;
/*      */     
/* 5834 */     rememberHudSnapBounds(24, (Module)module, panelX * (float)scaleFactor, panelY * (float)scaleFactor, panelW * (float)scaleFactor, panelH * (float)scaleFactor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5843 */     if (chatEditing) {
/* 5844 */       if (RECT_RESIZING[24]) {
/* 5845 */         HudCursorRelay.defer(221189, 4);
/* 5846 */       } else if (RECT_DRAGGING[24]) {
/* 5847 */         HudCursorRelay.defer(221193, 3);
/*      */       } 
/*      */     }
/*      */     
/* 5851 */     float centerX = panelX + panelW * 0.5F;
/* 5852 */     float centerY = panelY + panelH;
/*      */     
/* 5854 */     Vector3f translation = new Vector3f();
/*      */     
/* 5856 */     Quaternionf headRotation = null;
/*      */     
/* 5858 */     String mode = module.mode.getSelected();
/* 5859 */     if ("Follow Mouse".equalsIgnoreCase(mode) && !chatEditing) {
/* 5860 */       float mx = (float)(mc.field_1729.method_1603() * mc.method_22683().method_4486() / mc.method_22683().method_4480());
/* 5861 */       float my = (float)(mc.field_1729.method_1604() * mc.method_22683().method_4502() / mc.method_22683().method_4507());
/* 5862 */       float dx = (centerX - mx) / 45.0F;
/* 5863 */       float dy = (panelY + panelH * 0.58F - my) / 45.0F;
/* 5864 */       bodyRotation = (new Quaternionf()).rotateZ(3.1415927F);
/* 5865 */       headRotation = (new Quaternionf()).rotateX(dy * 20.0F * 0.017453292F);
/* 5866 */       bodyRotation.mul((Quaternionfc)(new Quaternionf()).rotateY(dx * 20.0F * 0.017453292F));
/* 5867 */     } else if ("Auto Rotate".equalsIgnoreCase(mode)) {
/* 5868 */       float speed = module.rotationSpeed.getValue();
/* 5869 */       float angleDeg = (float)System.nanoTime() / 1.0E9F * speed;
/* 5870 */       bodyRotation = (new Quaternionf()).rotateZ(3.1415927F).rotateY(angleDeg * 0.017453292F);
/* 5871 */       headRotation = new Quaternionf();
/*      */     } else {
/* 5873 */       bodyRotation = (new Quaternionf()).rotateZ(3.1415927F);
/* 5874 */       headRotation = new Quaternionf();
/*      */     } 
/*      */     
/* 5877 */     class_4050 previousPose = player.method_18376();
/*      */     try {
/* 5879 */       player.method_18380(class_4050.field_18076);
/* 5880 */       class_490.method_48472(context, centerX, centerY, baseSize * scale, translation, bodyRotation, headRotation, (class_1309)player);
/*      */     } finally {
/*      */       
/* 5883 */       player.method_18380(previousPose);
/*      */     } 
/*      */     
/* 5886 */     if (chatEditing && RECT_HOVER_PROGRESS[24] > 0.05F) {
/* 5887 */       int outlineColor = withAlpha(16777215, (int)(165.0F * RECT_HOVER_PROGRESS[24]));
/* 5888 */       drawOuterOutline(context, hoverX, panelY, hoverW, panelH, 1, outlineColor);
/*      */     } 
/*      */     
/* 5891 */     boolean showResizeHandle = (chatEditing && (RECT_RESIZING[24] || hoveredHandle || hoveredHud || nearHud));
/* 5892 */     if (showResizeHandle) {
/* 5893 */       context.method_51448().method_22903();
/* 5894 */       context.method_51448().method_46416(0.0F, 0.0F, 450.0F);
/* 5895 */       int hX = Math.round(handleX);
/* 5896 */       int hY = Math.round(handleY);
/* 5897 */       int handleColor = RECT_RESIZING[24] ? withAlpha(16777215, 255) : -9242668;
/* 5898 */       context.method_25294(hX, hY, hX + handleSize, hY + handleSize, handleColor);
/* 5899 */       if (hoveredHandle || RECT_RESIZING[24]) {
/* 5900 */         drawOutlineNoOverlap(context, hX - 1, hY - 1, handleSize + 2, handleSize + 2, withAlpha(16777215, 220));
/*      */       }
/* 5902 */       context.method_51448().method_22909();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderInventoryHud(class_332 context, class_310 client, InventoryHud module, boolean chatEditing, double mouseX, double mouseY, boolean mouseDown, float deltaSeconds, float inverseGuiScale, float screenWidth, float screenHeight, float screenCenterX, float screenCenterY) {
/* 5939 */     if (module == null || !module.isEnabled() || client == null || client.field_1724 == null || client.field_1690 == null) {
/*      */       return;
/*      */     }
/* 5942 */     if (client.field_1690.field_1842) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5953 */     InventoryHud.refreshSnapshotIfStale(client);
/* 5954 */     class_1799[] snapshot = InventoryHud.getSnapshotStacks();
/* 5955 */     boolean[] overlayFlags = InventoryHud.getSnapshotOverlayFlags();
/*      */     
/* 5957 */     int hudIndex = 26;
/* 5958 */     module.setHudScale(class_3532.method_15363(module.getHudScale(), module.getMinHudScale(), module.getMaxHudScale()));
/* 5959 */     float scale = module.getRenderHudScale();
/*      */     
/* 5961 */     float baseWidth = 176.0F;
/* 5962 */     float baseHeight = 68.0F;
/* 5963 */     float hudWidth = baseWidth * scale;
/* 5964 */     float hudHeight = baseHeight * scale;
/* 5965 */     float maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 5966 */     float maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 5967 */     int currentScreenWidth = Math.round(screenWidth);
/* 5968 */     int currentScreenHeight = Math.round(screenHeight);
/* 5969 */     float panelX = module.getHudX();
/* 5970 */     float panelY = module.getHudY();
/* 5971 */     boolean resized = (RECT_LAYOUT_INITIALIZED[26] && (RECT_LAST_SCREEN_WIDTH[26] != currentScreenWidth || RECT_LAST_SCREEN_HEIGHT[26] != currentScreenHeight));
/*      */ 
/*      */     
/* 5974 */     if (resized && !RECT_DRAGGING[26] && !RECT_RESIZING[26]) {
/* 5975 */       panelX = (maxX <= 0.0F) ? 0.0F : (RECT_POSITION_RATIO_X[26] * maxX);
/* 5976 */       panelY = (maxY <= 0.0F) ? 0.0F : (RECT_POSITION_RATIO_Y[26] * maxY);
/*      */     } 
/*      */     
/* 5979 */     panelX = class_3532.method_15363(panelX, 0.0F, maxX);
/* 5980 */     panelY = class_3532.method_15363(panelY, 0.0F, maxY);
/* 5981 */     module.setHudX(panelX);
/* 5982 */     module.setHudY(panelY);
/* 5983 */     RECT_POSITION_RATIO_X[26] = (maxX <= 0.0F) ? 0.0F : (panelX / maxX);
/* 5984 */     RECT_POSITION_RATIO_Y[26] = (maxY <= 0.0F) ? 0.0F : (panelY / maxY);
/* 5985 */     RECT_LAST_SCREEN_WIDTH[26] = currentScreenWidth;
/* 5986 */     RECT_LAST_SCREEN_HEIGHT[26] = currentScreenHeight;
/* 5987 */     RECT_LAYOUT_INITIALIZED[26] = true;
/*      */     
/* 5989 */     int handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 5990 */     float handleX = panelX + hudWidth - handleSize / 2.0F;
/* 5991 */     float handleY = panelY + hudHeight - handleSize / 2.0F;
/* 5992 */     boolean hoveredHud = false;
/* 5993 */     boolean hoveredHandle = false;
/* 5994 */     boolean nearHud = false;
/*      */     
/* 5996 */     if (!chatEditing) {
/* 5997 */       RECT_DRAGGING[26] = false;
/* 5998 */       RECT_RESIZING[26] = false;
/* 5999 */       RECT_HOVER_PROGRESS[26] = approachExp(RECT_HOVER_PROGRESS[26], 0.0F, 10.0F, deltaSeconds);
/*      */     } else {
/* 6001 */       hoveredHud = isHovered(mouseX, mouseY, panelX, panelY, hudWidth, hudHeight);
/* 6002 */       hoveredHandle = isHovered(mouseX, mouseY, handleX, handleY, handleSize, handleSize);
/* 6003 */       nearHud = isNearRect(mouseX, mouseY, panelX, panelY, hudWidth, hudHeight, Math.max(14.0F, 12.0F * scale));
/*      */       
/* 6005 */       if (!mouseDown) {
/* 6006 */         RECT_DRAGGING[26] = false;
/* 6007 */         RECT_RESIZING[26] = false;
/* 6008 */       } else if (!wasMouseDown && !isAnyHudInteractionActive()) {
/* 6009 */         if (hoveredHandle) {
/* 6010 */           RECT_RESIZING[26] = true;
/* 6011 */           RECT_RESIZE_START_WIDTH[26] = hudWidth;
/* 6012 */           RECT_RESIZE_START_MOUSE_X[26] = (float)mouseX;
/* 6013 */           RECT_RESIZE_START_MOUSE_Y[26] = (float)mouseY;
/* 6014 */         } else if (hoveredHud) {
/* 6015 */           RECT_DRAGGING[26] = true;
/* 6016 */           RECT_DRAG_OFFSET_X[26] = (float)mouseX - panelX;
/* 6017 */           RECT_DRAG_OFFSET_Y[26] = (float)mouseY - panelY;
/*      */         } 
/*      */       } 
/*      */       
/* 6021 */       if (mouseDown) {
/* 6022 */         if (RECT_DRAGGING[26]) {
/* 6023 */           float newX = class_3532.method_15363((float)mouseX - RECT_DRAG_OFFSET_X[26], 0.0F, maxX);
/* 6024 */           float newY = class_3532.method_15363((float)mouseY - RECT_DRAG_OFFSET_Y[26], 0.0F, maxY);
/* 6025 */           float centerX = newX + hudWidth * 0.5F;
/* 6026 */           float centerY = newY + hudHeight * 0.5F;
/* 6027 */           boolean snappedToScreenX = false;
/* 6028 */           boolean snappedToScreenY = false;
/*      */           
/* 6030 */           if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 6031 */             newX = class_3532.method_15363(screenCenterX - hudWidth * 0.5F, 0.0F, maxX);
/* 6032 */             showVerticalGuideThisFrame = true;
/* 6033 */             snappedToScreenX = true;
/*      */           } 
/* 6035 */           if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 6036 */             newY = class_3532.method_15363(screenCenterY - hudHeight * 0.5F, 0.0F, maxY);
/* 6037 */             showHorizontalGuideThisFrame = true;
/* 6038 */             snappedToScreenY = true;
/*      */           } 
/* 6040 */           if (!snappedToScreenX) {
/* 6041 */             newX = snapHudAlignmentX(26, newX, newY, hudWidth, hudHeight, maxX, 1.0F);
/*      */           }
/* 6043 */           if (!snappedToScreenY) {
/* 6044 */             newY = snapHudAlignmentY(26, newX, newY, hudWidth, hudHeight, maxY, 1.0F);
/*      */           }
/*      */           
/* 6047 */           panelX = newX;
/* 6048 */           panelY = newY;
/* 6049 */           module.setHudX(panelX);
/* 6050 */           module.setHudY(panelY);
/* 6051 */           RECT_POSITION_RATIO_X[26] = (maxX <= 0.0F) ? 0.0F : (panelX / maxX);
/* 6052 */           RECT_POSITION_RATIO_Y[26] = (maxY <= 0.0F) ? 0.0F : (panelY / maxY);
/* 6053 */           handleX = panelX + hudWidth - handleSize / 2.0F;
/* 6054 */           handleY = panelY + hudHeight - handleSize / 2.0F;
/* 6055 */         } else if (RECT_RESIZING[26]) {
/* 6056 */           float deltaX = (float)mouseX - RECT_RESIZE_START_MOUSE_X[26];
/* 6057 */           float deltaY = (float)mouseY - RECT_RESIZE_START_MOUSE_Y[26];
/* 6058 */           float delta = (deltaX + deltaY) * 0.5F;
/*      */           
/* 6060 */           float minWidth = baseWidth * module.getMinHudScale() * 2.0F;
/* 6061 */           float maxWidth = baseWidth * module.getMaxHudScale() * 2.0F;
/* 6062 */           float newWidth = class_3532.method_15363(RECT_RESIZE_START_WIDTH[26] + delta * 0.9F, minWidth, maxWidth);
/* 6063 */           float newScale = snapAndAnnounceHudScale((Module)module, newWidth / baseWidth / 2.0F, module
/*      */               
/* 6065 */               .getMinHudScale(), module.getMaxHudScale());
/*      */           
/* 6067 */           module.setHudScale(newScale);
/* 6068 */           scale = module.getRenderHudScale();
/* 6069 */           hudWidth = baseWidth * scale;
/* 6070 */           hudHeight = baseHeight * scale;
/* 6071 */           maxX = Math.max(0.0F, screenWidth - hudWidth);
/* 6072 */           maxY = Math.max(0.0F, screenHeight - hudHeight);
/* 6073 */           panelX = class_3532.method_15363(module.getHudX(), 0.0F, maxX);
/* 6074 */           panelY = class_3532.method_15363(module.getHudY(), 0.0F, maxY);
/* 6075 */           module.setHudX(panelX);
/* 6076 */           module.setHudY(panelY);
/* 6077 */           RECT_POSITION_RATIO_X[26] = (maxX <= 0.0F) ? 0.0F : (panelX / maxX);
/* 6078 */           RECT_POSITION_RATIO_Y[26] = (maxY <= 0.0F) ? 0.0F : (panelY / maxY);
/* 6079 */           handleSize = Math.max(4, Math.min(10, Math.round(5.0F * scale)));
/* 6080 */           handleX = panelX + hudWidth - handleSize / 2.0F;
/* 6081 */           handleY = panelY + hudHeight - handleSize / 2.0F;
/*      */         } 
/*      */       }
/*      */       
/* 6085 */       if (RECT_DRAGGING[26]) {
/* 6086 */         float centerX = panelX + hudWidth * 0.5F;
/* 6087 */         float centerY = panelY + hudHeight * 0.5F;
/* 6088 */         if (Math.abs(centerX - screenCenterX) <= 3.0F) {
/* 6089 */           showVerticalGuideThisFrame = true;
/*      */         }
/* 6091 */         if (Math.abs(centerY - screenCenterY) <= 3.0F) {
/* 6092 */           showHorizontalGuideThisFrame = true;
/*      */         }
/*      */       } 
/*      */       
/* 6096 */       RECT_HOVER_PROGRESS[26] = approachExp(RECT_HOVER_PROGRESS[26], hoveredHud ? 1.0F : 0.0F, 10.0F, deltaSeconds);
/*      */     } 
/*      */     
/* 6099 */     rememberHudSnapBounds(26, (Module)module, panelX, panelY, hudWidth, hudHeight);
/*      */     
/* 6101 */     if (chatEditing) {
/* 6102 */       if (RECT_RESIZING[26]) {
/* 6103 */         HudCursorRelay.defer(221189, 4);
/* 6104 */       } else if (RECT_DRAGGING[26]) {
/* 6105 */         HudCursorRelay.defer(221193, 3);
/*      */       } 
/*      */     }
/*      */     
/* 6109 */     int hoverOutlineThickness = Math.max(1, Math.round(1.0F / Math.max(1.0F, scale)));
/*      */     
/* 6111 */     context.method_51448().method_22903();
/* 6112 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 6113 */     context.method_51448().method_22903();
/* 6114 */     context.method_51448().method_46416(panelX, panelY, 400.0F);
/* 6115 */     context.method_51448().method_22905(scale, scale, 1.0F);
/*      */     
/* 6117 */     context.method_52707(class_1921::method_62277, PHAZE_INV_PANEL_SPRITE, 0, 0, (int)baseWidth, (int)baseHeight, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6126 */     boolean drawCounts = module.drawCounts.isValue();
/* 6127 */     for (int row = 0; row < 3; row++) {
/* 6128 */       for (int col = 0; col < 9; col++) {
/* 6129 */         int sx = 8 + col * 18;
/* 6130 */         int sy = 8 + row * 18;
/* 6131 */         int idx = row * 9 + col;
/* 6132 */         class_1799 stack = snapshot[idx];
/* 6133 */         if (stack != null && !stack.method_7960()) {
/*      */ 
/*      */           
/* 6136 */           context.method_51427(stack, sx, sy);
/* 6137 */           if (drawCounts && overlayFlags[idx])
/* 6138 */             context.method_51431(client.field_1772, stack, sx, sy); 
/*      */         } 
/*      */       } 
/*      */     } 
/* 6142 */     context.method_51452();
/* 6143 */     context.method_51448().method_22909();
/*      */     
/* 6145 */     if (chatEditing && RECT_HOVER_PROGRESS[26] > 0.05F) {
/* 6146 */       int outlineColor = withAlpha(16777215, (int)(175.0F * RECT_HOVER_PROGRESS[26]));
/* 6147 */       drawOuterOutline(context, panelX, panelY, hudWidth, hudHeight, hoverOutlineThickness, outlineColor);
/*      */     } 
/*      */     
/* 6150 */     boolean showResizeHandle = (chatEditing && (RECT_RESIZING[26] || hoveredHandle || hoveredHud || nearHud));
/* 6151 */     if (showResizeHandle) {
/* 6152 */       context.method_51448().method_22903();
/* 6153 */       context.method_51448().method_46416(0.0F, 0.0F, 450.0F);
/* 6154 */       int hX = Math.round(handleX);
/* 6155 */       int hY = Math.round(handleY);
/* 6156 */       int handleColor = RECT_RESIZING[26] ? withAlpha(16777215, 255) : -9242668;
/* 6157 */       context.method_25294(hX, hY, hX + handleSize, hY + handleSize, handleColor);
/* 6158 */       if (hoveredHandle || RECT_RESIZING[26]) {
/* 6159 */         drawOutlineNoOverlap(context, hX - 1, hY - 1, handleSize + 2, handleSize + 2, withAlpha(16777215, 220));
/*      */       }
/* 6161 */       context.method_51448().method_22909();
/*      */     } 
/*      */     
/* 6164 */     context.method_51448().method_22909();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Unique
/*      */   private static boolean phaze$shouldDrawSlotOverlay(class_1799 stack) {
/* 6178 */     if (stack.method_7947() != 1) return true; 
/* 6179 */     if (stack.method_31578()) return true; 
/* 6180 */     class_310 mc = class_310.method_1551();
/* 6181 */     if (mc != null && mc.field_1724 != null && mc.field_1724
/* 6182 */       .method_7357().method_7905(stack, 0.0F) > 0.0F) {
/* 6183 */       return true;
/*      */     }
/* 6185 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Redirect(method = {"renderCrosshair"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
/*      */   private boolean phaze$forceFirstPersonForCrosshair(class_5498 perspective) {
/* 6198 */     Crosshair module = Crosshair.getInstance();
/* 6199 */     if (module != null && module.isEnabled() && module.showInThirdPerson.isValue()) {
/* 6200 */       class_310 mc = class_310.method_1551();
/* 6201 */       if (mc != null && mc.field_1690 != null && mc.field_1690.field_1842) {
/* 6202 */         return perspective.method_31034();
/*      */       }
/* 6204 */       return true;
/*      */     } 
/* 6206 */     return perspective.method_31034();
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\InGameHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */