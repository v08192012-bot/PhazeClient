/*     */ package vorga.phazeclient.core;
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.ModuleProvider;
/*     */ import vorga.phazeclient.api.system.discord.DiscordManager;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.implement.config.ConfigManager;
/*     */ import vorga.phazeclient.implement.cosmetics.CosmeticsSyncService;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ArmorHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.Cooldowns;
/*     */ import vorga.phazeclient.implement.features.modules.hud.DayCounterHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.InventoryHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.KeystrokesHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.MovementSpeedHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.PingHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ReachHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.TabHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.TpsHud;
/*     */ import vorga.phazeclient.implement.features.modules.other.ArmorNotifier;
/*     */ import vorga.phazeclient.implement.features.modules.other.AspectRatio;
/*     */ import vorga.phazeclient.implement.features.modules.other.AutoNear;
/*     */ import vorga.phazeclient.implement.features.modules.other.Bright;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChunkAnimator;
/*     */ import vorga.phazeclient.implement.features.modules.other.Crosshair;
/*     */ import vorga.phazeclient.implement.features.modules.other.DiscordRpc;
/*     */ import vorga.phazeclient.implement.features.modules.other.ElytraUtility;
/*     */ import vorga.phazeclient.implement.features.modules.other.FakeFps;
/*     */ import vorga.phazeclient.implement.features.modules.other.HealingHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.HitColor;
/*     */ import vorga.phazeclient.implement.features.modules.other.HudOptimizer;
/*     */ import vorga.phazeclient.implement.features.modules.other.ItemPhysics;
/*     */ import vorga.phazeclient.implement.features.modules.other.ItemScroller;
/*     */ import vorga.phazeclient.implement.features.modules.other.KillSounds;
/*     */ import vorga.phazeclient.implement.features.modules.other.LowHealthWarning;
/*     */ import vorga.phazeclient.implement.features.modules.other.MotionBlur;
/*     */ import vorga.phazeclient.implement.features.modules.other.NoFluid;
/*     */ import vorga.phazeclient.implement.features.modules.other.OldAnimations;
/*     */ import vorga.phazeclient.implement.features.modules.other.PotionAuto;
/*     */ import vorga.phazeclient.implement.features.modules.other.ShulkerPreview;
/*     */ import vorga.phazeclient.implement.features.modules.other.ThreeDSkins;
/*     */ import vorga.phazeclient.implement.features.modules.other.WaveCapes;
/*     */ import vorga.phazeclient.implement.features.modules.other.Zoom;
/*     */ 
/*     */ public class Main implements ModInitializer {
/*  45 */   private final ScissorManager scissorManager = new ScissorManager(); private static Main instance; public ScissorManager getScissorManager() { return this.scissorManager; }
/*  46 */    private final ModuleProvider moduleProvider = new ModuleProvider(new ArrayList()); public ModuleProvider getModuleProvider() { return this.moduleProvider; }
/*  47 */    private final ConfigManager configManager = ConfigManager.getInstance(); public ConfigManager getConfigManager() { return this.configManager; }
/*  48 */    private final DiscordManager discordManager = new DiscordManager(); public DiscordManager getDiscordManager() { return this.discordManager; }
/*     */   
/*     */   public Main() {
/*  51 */     instance = this;
/*     */   }
/*     */   
/*     */   public static Main getInstance() {
/*  55 */     if (instance == null) {
/*  56 */       instance = new Main();
/*     */     }
/*  58 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInitialize() {
/*  63 */     HolyWorldFeatureControlService.getInstance().init();
/*     */     
/*  65 */     if (this.moduleProvider.get(Theme.class) == null) {
/*  66 */       this.moduleProvider.getModules().add(Theme.getInstance());
/*     */     }
/*  68 */     if (this.moduleProvider.get(TimeChanger.class) == null) {
/*  69 */       this.moduleProvider.getModules().add(TimeChanger.getInstance());
/*     */     }
/*  71 */     if (this.moduleProvider.get(WeatherChanger.class) == null) {
/*  72 */       this.moduleProvider.getModules().add(WeatherChanger.getInstance());
/*     */     }
/*  74 */     if (this.moduleProvider.get(AutoSprint.class) == null) {
/*  75 */       this.moduleProvider.getModules().add(AutoSprint.getInstance());
/*     */     }
/*  77 */     if (this.moduleProvider.get(FpsHud.class) == null) {
/*  78 */       this.moduleProvider.getModules().add(FpsHud.getInstance());
/*     */     }
/*  80 */     if (this.moduleProvider.get(CpsHud.class) == null) {
/*  81 */       this.moduleProvider.getModules().add(CpsHud.getInstance());
/*     */     }
/*  83 */     if (this.moduleProvider.get(ReachHud.class) == null) {
/*  84 */       this.moduleProvider.getModules().add(ReachHud.getInstance());
/*     */     }
/*  86 */     if (this.moduleProvider.get(ArmorHud.class) == null) {
/*  87 */       this.moduleProvider.getModules().add(ArmorHud.getInstance());
/*  88 */       ArmorHud.getInstance().setState(true);
/*     */     } 
/*  90 */     if (this.moduleProvider.get(SprintHud.class) == null) {
/*  91 */       this.moduleProvider.getModules().add(SprintHud.getInstance());
/*     */     }
/*  93 */     if (this.moduleProvider.get(CoordinatesHud.class) == null) {
/*  94 */       this.moduleProvider.getModules().add(CoordinatesHud.getInstance());
/*     */     }
/*  96 */     if (this.moduleProvider.get(PingHud.class) == null) {
/*  97 */       this.moduleProvider.getModules().add(PingHud.getInstance());
/*     */     }
/*  99 */     if (this.moduleProvider.get(KeystrokesHud.class) == null) {
/* 100 */       this.moduleProvider.getModules().add(KeystrokesHud.getInstance());
/*     */     }
/* 102 */     if (this.moduleProvider.get(PotionHud.class) == null) {
/* 103 */       this.moduleProvider.getModules().add(PotionHud.getInstance());
/*     */     }
/* 105 */     if (this.moduleProvider.get(DayCounterHud.class) == null) {
/* 106 */       this.moduleProvider.getModules().add(DayCounterHud.getInstance());
/*     */     }
/* 108 */     if (this.moduleProvider.get(TabHud.class) == null) {
/* 109 */       this.moduleProvider.getModules().add(TabHud.getInstance());
/*     */     }
/* 111 */     if (this.moduleProvider.get(NametagHud.class) == null) {
/* 112 */       this.moduleProvider.getModules().add(NametagHud.getInstance());
/*     */     }
/* 114 */     if (this.moduleProvider.get(TimeHud.class) == null) {
/* 115 */       this.moduleProvider.getModules().add(TimeHud.getInstance());
/*     */     }
/* 117 */     if (this.moduleProvider.get(SessionTimeHud.class) == null) {
/* 118 */       this.moduleProvider.getModules().add(SessionTimeHud.getInstance());
/*     */     }
/* 120 */     if (this.moduleProvider.get(MemoryHud.class) == null) {
/* 121 */       this.moduleProvider.getModules().add(MemoryHud.getInstance());
/*     */     }
/* 123 */     if (this.moduleProvider.get(ComboCounterHud.class) == null) {
/* 124 */       this.moduleProvider.getModules().add(ComboCounterHud.getInstance());
/*     */     }
/* 126 */     if (this.moduleProvider.get(Cooldowns.class) == null) {
/* 127 */       this.moduleProvider.getModules().add(Cooldowns.getInstance());
/*     */     }
/* 129 */     if (this.moduleProvider.get(ServerAddressHud.class) == null) {
/* 130 */       this.moduleProvider.getModules().add(ServerAddressHud.getInstance());
/*     */     }
/* 132 */     if (this.moduleProvider.get(MovementSpeedHud.class) == null) {
/* 133 */       this.moduleProvider.getModules().add(MovementSpeedHud.getInstance());
/*     */     }
/* 135 */     if (this.moduleProvider.get(WailaHud.class) == null) {
/* 136 */       this.moduleProvider.getModules().add(WailaHud.getInstance());
/*     */     }
/* 138 */     if (this.moduleProvider.get(Saturation.class) == null) {
/* 139 */       this.moduleProvider.getModules().add(Saturation.getInstance());
/*     */     }
/* 141 */     if (this.moduleProvider.get(HitColor.class) == null) {
/* 142 */       this.moduleProvider.getModules().add(HitColor.getInstance());
/*     */     }
/* 144 */     if (this.moduleProvider.get(FakeFps.class) == null) {
/* 145 */       this.moduleProvider.getModules().add(FakeFps.getInstance());
/*     */     }
/* 147 */     if (this.moduleProvider.get(SmoothCamera.class) == null) {
/* 148 */       this.moduleProvider.getModules().add(SmoothCamera.getInstance());
/*     */     }
/* 150 */     if (this.moduleProvider.get(BetterF3.class) == null) {
/* 151 */       this.moduleProvider.getModules().add(BetterF3.getInstance());
/*     */     }
/* 153 */     if (this.moduleProvider.get(InventoryHud.class) == null) {
/* 154 */       this.moduleProvider.getModules().add(InventoryHud.getInstance());
/*     */     }
/* 156 */     if (this.moduleProvider.get(PlayerModelHud.class) == null) {
/* 157 */       this.moduleProvider.getModules().add(PlayerModelHud.getInstance());
/*     */     }
/* 159 */     if (this.moduleProvider.get(TpsHud.class) == null) {
/* 160 */       this.moduleProvider.getModules().add(TpsHud.getInstance());
/*     */     }
/* 162 */     if (this.moduleProvider.get(BlockOverlay.class) == null) {
/* 163 */       this.moduleProvider.getModules().add(BlockOverlay.getInstance());
/*     */     }
/* 165 */     if (this.moduleProvider.get(Crosshair.class) == null) {
/* 166 */       this.moduleProvider.getModules().add(Crosshair.getInstance());
/*     */     }
/* 168 */     if (this.moduleProvider.get(DeathCoords.class) == null) {
/* 169 */       this.moduleProvider.getModules().add(DeathCoords.getInstance());
/*     */     }
/* 171 */     if (this.moduleProvider.get(DiscordRpc.class) == null) {
/* 172 */       this.moduleProvider.getModules().add(DiscordRpc.getInstance());
/*     */     }
/* 174 */     if (this.moduleProvider.get(Zoom.class) == null) {
/* 175 */       this.moduleProvider.getModules().add(Zoom.getInstance());
/*     */     }
/* 177 */     if (this.moduleProvider.get(AutoSwap.class) == null) {
/* 178 */       this.moduleProvider.getModules().add(AutoSwap.getInstance());
/*     */     }
/* 180 */     if (this.moduleProvider.get(FastSwap.class) == null) {
/* 181 */       this.moduleProvider.getModules().add(FastSwap.getInstance());
/*     */     }
/* 183 */     if (this.moduleProvider.get(ShiftTap.class) == null) {
/* 184 */       this.moduleProvider.getModules().add(ShiftTap.getInstance());
/*     */     }
/* 186 */     if (this.moduleProvider.get(AutoNear.class) == null) {
/* 187 */       this.moduleProvider.getModules().add(AutoNear.getInstance());
/*     */     }
/* 189 */     if (this.moduleProvider.get(AutoReissue.class) == null) {
/* 190 */       this.moduleProvider.getModules().add(AutoReissue.getInstance());
/*     */     }
/* 192 */     if (this.moduleProvider.get(HudOptimizer.class) == null) {
/* 193 */       this.moduleProvider.getModules().add(HudOptimizer.getInstance());
/* 194 */       HudOptimizer.getInstance().setState(true);
/*     */     } 
/* 196 */     if (this.moduleProvider.get(FreeLook.class) == null) {
/* 197 */       this.moduleProvider.getModules().add(FreeLook.getInstance());
/*     */     }
/* 199 */     if (this.moduleProvider.get(PotionAuto.class) == null) {
/* 200 */       this.moduleProvider.getModules().add(PotionAuto.getInstance());
/*     */     }
/* 202 */     if (this.moduleProvider.get(HitboxCustomizer.class) == null) {
/* 203 */       this.moduleProvider.getModules().add(HitboxCustomizer.getInstance());
/*     */     }
/* 205 */     if (this.moduleProvider.get(LockSlot.class) == null) {
/* 206 */       this.moduleProvider.getModules().add(LockSlot.getInstance());
/*     */     }
/* 208 */     if (this.moduleProvider.get(MouseClicker.class) == null) {
/* 209 */       this.moduleProvider.getModules().add(MouseClicker.getInstance());
/*     */     }
/* 211 */     if (this.moduleProvider.get(ElytraUtility.class) == null) {
/* 212 */       this.moduleProvider.getModules().add(ElytraUtility.getInstance());
/*     */     }
/* 214 */     if (this.moduleProvider.get(MotionBlur.class) == null) {
/* 215 */       this.moduleProvider.getModules().add(MotionBlur.getInstance());
/*     */     }
/* 217 */     if (this.moduleProvider.get(HealthIndicator.class) == null) {
/* 218 */       this.moduleProvider.getModules().add(HealthIndicator.getInstance());
/*     */     }
/* 220 */     if (this.moduleProvider.get(Translator.class) == null) {
/* 221 */       this.moduleProvider.getModules().add(Translator.getInstance());
/*     */     }
/* 223 */     if (this.moduleProvider.get(NoFluid.class) == null) {
/* 224 */       this.moduleProvider.getModules().add(NoFluid.getInstance());
/*     */     }
/* 226 */     if (this.moduleProvider.get(TotemTracker.class) == null) {
/* 227 */       this.moduleProvider.getModules().add(TotemTracker.getInstance());
/*     */     }
/* 229 */     if (this.moduleProvider.get(Consumable.class) == null) {
/* 230 */       this.moduleProvider.getModules().add(Consumable.getInstance());
/*     */     }
/* 232 */     if (this.moduleProvider.get(ItemPhysics.class) == null) {
/* 233 */       this.moduleProvider.getModules().add(ItemPhysics.getInstance());
/*     */     }
/* 235 */     if (this.moduleProvider.get(ColorCorrection.class) == null) {
/* 236 */       this.moduleProvider.getModules().add(ColorCorrection.getInstance());
/*     */     }
/* 238 */     if (this.moduleProvider.get(FastExp.class) == null) {
/* 239 */       this.moduleProvider.getModules().add(FastExp.getInstance());
/*     */     }
/* 241 */     if (this.moduleProvider.get(ItemPickupLogger.class) == null) {
/* 242 */       this.moduleProvider.getModules().add(ItemPickupLogger.getInstance());
/*     */     }
/* 244 */     if (this.moduleProvider.get(AutoEat.class) == null) {
/* 245 */       this.moduleProvider.getModules().add(AutoEat.getInstance());
/*     */     }
/* 247 */     if (this.moduleProvider.get(ArmorNotifier.class) == null) {
/* 248 */       this.moduleProvider.getModules().add(ArmorNotifier.getInstance());
/*     */     }
/* 250 */     if (this.moduleProvider.get(ChatHelper.class) == null) {
/* 251 */       this.moduleProvider.getModules().add(ChatHelper.getInstance());
/*     */     }
/* 253 */     if (this.moduleProvider.get(FTHelper.class) == null) {
/* 254 */       this.moduleProvider.getModules().add(FTHelper.getInstance());
/*     */     }
/* 256 */     if (this.moduleProvider.get(HolyWorldHelper.class) == null) {
/* 257 */       this.moduleProvider.getModules().add(HolyWorldHelper.getInstance());
/*     */     }
/* 259 */     if (this.moduleProvider.get(TrapTimer.class) == null) {
/* 260 */       this.moduleProvider.getModules().add(TrapTimer.getInstance());
/*     */     }
/* 262 */     if (this.moduleProvider.get(PickaxeNotifier.class) == null) {
/* 263 */       this.moduleProvider.getModules().add(PickaxeNotifier.getInstance());
/*     */     }
/* 265 */     if (this.moduleProvider.get(ItemHighlighter.class) == null) {
/* 266 */       this.moduleProvider.getModules().add(ItemHighlighter.getInstance());
/*     */     }
/* 268 */     if (this.moduleProvider.get(Predictions.class) == null) {
/* 269 */       this.moduleProvider.getModules().add(Predictions.getInstance());
/*     */     }
/* 271 */     if (this.moduleProvider.get(NickHider.class) == null) {
/* 272 */       this.moduleProvider.getModules().add(NickHider.getInstance());
/*     */     }
/* 274 */     if (this.moduleProvider.get(StreamerMode.class) == null) {
/* 275 */       this.moduleProvider.getModules().add(StreamerMode.getInstance());
/*     */     }
/* 277 */     if (this.moduleProvider.get(HealingHelper.class) == null) {
/* 278 */       this.moduleProvider.getModules().add(HealingHelper.getInstance());
/*     */     }
/* 280 */     if (this.moduleProvider.get(ShulkerPreview.class) == null) {
/* 281 */       this.moduleProvider.getModules().add(ShulkerPreview.getInstance());
/*     */     }
/* 283 */     if (this.moduleProvider.get(Animations.class) == null) {
/* 284 */       this.moduleProvider.getModules().add(Animations.getInstance());
/*     */     }
/* 286 */     if (this.moduleProvider.get(Bright.class) == null) {
/* 287 */       this.moduleProvider.getModules().add(Bright.getInstance());
/*     */     }
/* 289 */     if (this.moduleProvider.get(AspectRatio.class) == null) {
/* 290 */       this.moduleProvider.getModules().add(AspectRatio.getInstance());
/*     */     }
/* 292 */     if (this.moduleProvider.get(AutoGG.class) == null) {
/* 293 */       this.moduleProvider.getModules().add(AutoGG.getInstance());
/*     */     }
/* 295 */     if (this.moduleProvider.get(ItemScroller.class) == null) {
/* 296 */       this.moduleProvider.getModules().add(ItemScroller.getInstance());
/*     */     }
/* 298 */     if (this.moduleProvider.get(NoRender.class) == null) {
/* 299 */       this.moduleProvider.getModules().add(NoRender.getInstance());
/*     */     }
/* 301 */     if (this.moduleProvider.get(ChangeHand.class) == null) {
/* 302 */       this.moduleProvider.getModules().add(ChangeHand.getInstance());
/*     */     }
/* 304 */     if (this.moduleProvider.get(OldAnimations.class) == null) {
/* 305 */       this.moduleProvider.getModules().add(OldAnimations.getInstance());
/*     */     }
/* 307 */     if (this.moduleProvider.get(HitRange.class) == null) {
/* 308 */       this.moduleProvider.getModules().add(HitRange.getInstance());
/*     */     }
/* 310 */     if (this.moduleProvider.get(AucHelper.class) == null) {
/* 311 */       this.moduleProvider.getModules().add(AucHelper.getInstance());
/*     */     }
/* 313 */     if (this.moduleProvider.get(Binds.class) == null) {
/* 314 */       this.moduleProvider.getModules().add(Binds.getInstance());
/*     */     }
/* 316 */     if (this.moduleProvider.get(MentionHighlight.class) == null) {
/* 317 */       this.moduleProvider.getModules().add(MentionHighlight.getInstance());
/*     */     }
/* 319 */     if (this.moduleProvider.get(SkyCustomizer.class) == null) {
/* 320 */       this.moduleProvider.getModules().add(SkyCustomizer.getInstance());
/*     */     }
/* 322 */     if (this.moduleProvider.get(CustomFog.class) == null) {
/* 323 */       this.moduleProvider.getModules().add(CustomFog.getInstance());
/*     */     }
/* 325 */     if (this.moduleProvider.get(AutoRespawn.class) == null) {
/* 326 */       this.moduleProvider.getModules().add(AutoRespawn.getInstance());
/*     */     }
/* 328 */     if (this.moduleProvider.get(MaceIndicator.class) == null) {
/* 329 */       this.moduleProvider.getModules().add(MaceIndicator.getInstance());
/*     */     }
/* 331 */     if (this.moduleProvider.get(ChunkAnimator.class) == null) {
/* 332 */       this.moduleProvider.getModules().add(ChunkAnimator.getInstance());
/*     */     }
/* 334 */     if (this.moduleProvider.get(ScoreboardHud.class) == null) {
/* 335 */       this.moduleProvider.getModules().add(ScoreboardHud.getInstance());
/*     */     }
/* 337 */     if (this.moduleProvider.get(TntCountdown.class) == null) {
/* 338 */       this.moduleProvider.getModules().add(TntCountdown.getInstance());
/*     */     }
/* 340 */     if (this.moduleProvider.get(ShinyPots.class) == null) {
/* 341 */       this.moduleProvider.getModules().add(ShinyPots.getInstance());
/*     */     }
/* 343 */     if (this.moduleProvider.get(KillSounds.class) == null) {
/* 344 */       this.moduleProvider.getModules().add(KillSounds.getInstance());
/*     */     }
/* 346 */     if (this.moduleProvider.get(LowHealthWarning.class) == null) {
/* 347 */       this.moduleProvider.getModules().add(LowHealthWarning.getInstance());
/*     */     }
/* 349 */     if (this.moduleProvider.get(KillStreakHud.class) == null) {
/* 350 */       this.moduleProvider.getModules().add(KillStreakHud.getInstance());
/*     */     }
/* 352 */     if (this.moduleProvider.get(PackDisplayHud.class) == null) {
/* 353 */       this.moduleProvider.getModules().add(PackDisplayHud.getInstance());
/*     */     }
/* 355 */     if (this.moduleProvider.get(BossBarHud.class) == null) {
/* 356 */       this.moduleProvider.getModules().add(BossBarHud.getInstance());
/*     */     }
/* 358 */     if (this.moduleProvider.get(ChunkBorders.class) == null) {
/* 359 */       this.moduleProvider.getModules().add(ChunkBorders.getInstance());
/*     */     }
/* 361 */     if (this.moduleProvider.get(Waypoints.class) == null) {
/* 362 */       this.moduleProvider.getModules().add(Waypoints.getInstance());
/*     */     }
/* 364 */     if (this.moduleProvider.get(PackOrganizer.class) == null) {
/* 365 */       this.moduleProvider.getModules().add(PackOrganizer.getInstance());
/*     */     }
/* 367 */     if (this.moduleProvider.get(GlintColorizer.class) == null) {
/* 368 */       this.moduleProvider.getModules().add(GlintColorizer.getInstance());
/*     */     }
/* 370 */     if (this.moduleProvider.get(ThreeDSkins.class) == null) {
/* 371 */       this.moduleProvider.getModules().add(ThreeDSkins.getInstance());
/*     */     }
/* 373 */     if (this.moduleProvider.get(WaveCapes.class) == null) {
/* 374 */       this.moduleProvider.getModules().add(WaveCapes.getInstance());
/*     */     }
/*     */     
/* 377 */     this.configManager.loadCurrentConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     Setting.setGlobalChangeListener(setting -> this.configManager.markDirty());
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
/* 400 */     Module.setGlobalStateChangeListener(module -> this.configManager.flushNow());
/*     */ 
/*     */     
/* 403 */     this.configManager.enableAutoSave();
/* 404 */     ClientTickEvents.END_CLIENT_TICK.register(client -> this.configManager.flushIfDirty());
/*     */ 
/*     */     
/* 407 */     PhazePulsePetRenderer.register();
/* 408 */     PhazePulseGraffitiRenderer.register();
/* 409 */     ClientTickEvents.END_CLIENT_TICK.register(client -> CosmeticsSyncService.getInstance().tick(client));
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
/* 422 */     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
/*     */             try {
/*     */               this.configManager.saveCurrentConfig();
/* 425 */             } catch (Throwable throwable) {}
/*     */           }"phaze-config-shutdown"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     ClientLifecycleEvents.CLIENT_STOPPING.register(client -> this.configManager.saveCurrentConfig());
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
/* 450 */     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
/*     */             try {
/*     */               this.configManager.saveCurrentConfig();
/* 453 */             } catch (Throwable throwable) {}
/*     */           }"Phaze-ShutdownSave"));
/*     */ 
/*     */     
/* 457 */     this.discordManager.init();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 465 */     RemoteRulesService.getInstance().start();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\core\Main.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */