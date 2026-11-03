/*      */ package vorga.phazeclient.implement.features.modules.other;
/*      */ import java.util.Objects;
/*      */ import net.minecraft.class_5498;
/*      */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*      */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*      */ import vorga.phazeclient.base.util.animation.Interpolation;
/*      */ import vorga.phazeclient.base.util.animation.Interpolations;
/*      */ 
/*      */ public final class Animations extends Module {
/*   14 */   private static final Animations INSTANCE = new Animations();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float TAB_SLIDE_TRAVEL = 18.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float TAB_SMOOTH_BASE = 0.0015F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float TAB_SETTLE_EPSILON = 0.15F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int CHAT_FADE_IN_TICKS = 4;
/*      */ 
/*      */ 
/*      */   
/*   38 */   public final SectionSetting tabSection = new SectionSetting("Tab List");
/*   39 */   public final BooleanSetting tabSlide = (new BooleanSetting("Tab Slide", "Animate the player tab list when opening or closing it"))
/*      */ 
/*      */     
/*   42 */     .setValue(true);
/*   43 */   public final SelectSetting tabAnimationType = (new SelectSetting("Tab Animation", "Which style of animation to use when showing or hiding the tab list"))
/*      */ 
/*      */     
/*   46 */     .value(new String[] { "Slide", "Scale", "Slide+Scale" }).selected("Slide");
/*   47 */   public final BooleanSetting tabFade = (new BooleanSetting("Tab Fade", "Fade the tab list's opacity in and out together with the animation"))
/*      */ 
/*      */     
/*   50 */     .setValue(true);
/*   51 */   public final ValueSetting tabSlideSpeed = (new ValueSetting("Tab Animation Speed", "How quickly the tab list animates in/out. Higher = snappier."))
/*      */ 
/*      */     
/*   54 */     .range(1, 30).step(0.5F).setValue(5.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public final SelectSetting tabInterpolationOpen = (new SelectSetting("Tab Open Interpolation", "Easing curve when the tab list opens (Scale / Slide+Scale styles)"))
/*      */ 
/*      */     
/*   67 */     .value(Interpolations.getAllNames()).selected("Default");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   public final SelectSetting tabInterpolationClose = (new SelectSetting("Tab Close Interpolation", "Easing curve when the tab list closes (Scale / Slide+Scale styles)"))
/*      */ 
/*      */     
/*   76 */     .value(Interpolations.getAllNames()).selected("Default");
/*      */   
/*   78 */   public final SectionSetting chatSection = new SectionSetting("Chat");
/*   79 */   public final BooleanSetting chatFade = (new BooleanSetting("Chat Fade", "Fade in newly received chat messages over a few ticks"))
/*      */ 
/*      */     
/*   82 */     .setValue(true);
/*   83 */   public final BooleanSetting chatSmoothScroll = (new BooleanSetting("Message Animation", "Slide newly received chat messages into place instead of popping in"))
/*      */ 
/*      */     
/*   86 */     .setValue(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   public final SelectSetting chatMessageAnimationType = (new SelectSetting("Message Animation Type", "Direction the new chat message slides in from"))
/*      */ 
/*      */     
/*  102 */     .value(new String[] { "Up", "Left" }).selected("Up");
/*  103 */   public final ValueSetting chatSmoothSpeed = (new ValueSetting("Message Animation Speed", "Speed of the new-message slide. Higher = snappier (shorter slide duration)."))
/*      */ 
/*      */     
/*  106 */     .range(1, 30).step(0.5F).setValue(5.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  117 */   public final SelectSetting chatLeftInterpolation = (new SelectSetting("Left Slide Interpolation", "Easing curve for the Left-direction chat message slide"))
/*      */ 
/*      */     
/*  120 */     .value(Interpolations.getAllNames()).selected("Default");
/*  121 */   public final BooleanSetting smoothInputField = (new BooleanSetting("Smooth Input Field", "Slide the chat input box up from below when the chat screen opens (fixed speed)"))
/*      */ 
/*      */     
/*  124 */     .setValue(true);
/*      */   
/*  126 */   public final SectionSetting hotbarSection = new SectionSetting("Hotbar");
/*  127 */   public final BooleanSetting hotbarSlide = (new BooleanSetting("Hotbar Slide", "Smoothly slide the hotbar selection highlight when changing slots"))
/*      */ 
/*      */     
/*  130 */     .setValue(true);
/*  131 */   public final BooleanSetting hotbarRollover = (new BooleanSetting("Hotbar Rollover", "When wrapping past slot 8 to 0 (or vice versa), slide across the wrap instead of teleporting"))
/*      */ 
/*      */     
/*  134 */     .setValue(true);
/*  135 */   public final ValueSetting hotbarSpeed = (new ValueSetting("Hotbar Slide Speed", "Smoothness of the hotbar selection slide. Higher = snappier."))
/*      */ 
/*      */     
/*  138 */     .range(1, 30).step(0.5F).setValue(5.0F);
/*      */   
/*  140 */   public final SectionSetting cameraSection = new SectionSetting("Camera");
/*  141 */   public final BooleanSetting smoothF5 = (new BooleanSetting("Smooth F5", "Smoothly slide the camera out behind the player when toggling third person, and back in when returning to first person"))
/*      */ 
/*      */     
/*  144 */     .setValue(true);
/*  145 */   public final ValueSetting smoothF5Speed = (new ValueSetting("F5 Animation Speed", "How quickly the camera zooms in / out on perspective toggle. Higher = snappier."))
/*      */ 
/*      */     
/*  148 */     .range(1, 30).step(0.5F).setValue(5.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   public final SelectSetting smoothF5Interpolation = (new SelectSetting("F5 Interpolation", "Easing curve for the camera zoom-out animation"))
/*      */ 
/*      */     
/*  160 */     .value(Interpolations.getAllNames()).selected("Default");
/*      */   
/*  162 */   public final SectionSetting cursorSection = new SectionSetting("Cursor");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  177 */   public final BooleanSetting dynamicCursor = (new BooleanSetting("Dynamic Cursor", "Switch the mouse pointer to a hand on buttons and an I-beam on text inputs (1.21.11-style)"))
/*      */ 
/*      */     
/*  180 */     .setValue(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  198 */   public final BooleanSetting comfortableTextSelection = (new BooleanSetting("Comfortable Text Selection", "Drag-select text in any input field with the mouse - chat, search bars, Sodium options, etc."))
/*      */ 
/*      */     
/*  201 */     .setValue(true);
/*      */   
/*  203 */   public final SectionSetting listsSection = new SectionSetting("Lists");
/*  204 */   public final BooleanSetting listSmoothScroll = (new BooleanSetting("List Smooth Scroll", "Smooth scrolling for option lists, server lists, multiplayer lists, etc."))
/*      */ 
/*      */     
/*  207 */     .setValue(true);
/*  208 */   public final ValueSetting listSpeed = (new ValueSetting("List Scroll Speed", "Smoothness of widget-list scrolling. Higher = snappier."))
/*      */ 
/*      */     
/*  211 */     .range(1, 30).step(0.5F).setValue(5.0F);
/*  212 */   public final ValueSetting listLinesPerScroll = (new ValueSetting("List Lines Per Scroll", "Number of entries advanced per mouse-wheel tick in option lists."))
/*      */ 
/*      */     
/*  215 */     .range(1, 10).step(1.0F).setValue(1.0F);
/*      */ 
/*      */   
/*  218 */   private float tabCurrentOffset = -18.0F;
/*      */   
/*  220 */   private float tabTargetOffset = -18.0F;
/*  221 */   private long tabLastFrameNanos = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  232 */   private float tabLinearProgress = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   private float tabAnimationStartProgress = 0.0F;
/*      */ 
/*      */ 
/*      */   
/*  241 */   private long tabAnimationStartNanos = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tabAnimationTargetOpen = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float F5_FULL_DISTANCE = 4.0F;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float F5_SETTLE_EPSILON = 0.02F;
/*      */ 
/*      */ 
/*      */   
/*  258 */   private class_5498 f5LastPerspective = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  263 */   private float f5CurrentDistance = 0.0F;
/*      */ 
/*      */ 
/*      */   
/*  267 */   private float f5TargetDistance = 0.0F;
/*  268 */   private long f5LastFrameNanos = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  273 */   private float f5AnimationStartDistance = 0.0F;
/*      */ 
/*      */   
/*  276 */   private long f5AnimationStartNanos = 0L; private static final float TAB_TAIL_FADE_THRESHOLD = 0.15F;
/*      */   
/*      */   private Animations() {
/*  279 */     super("animations", "Animations", ModuleCategory.HUD);
/*      */     
/*  281 */     this.tabSlide.setFullWidth(true);
/*  282 */     this.tabAnimationType.setFullWidth(true);
/*  283 */     Objects.requireNonNull(this.tabSlide); this.tabAnimationType.visible(this.tabSlide::isValue);
/*  284 */     this.tabFade.setFullWidth(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     this.tabFade.visible(() -> Boolean.valueOf((this.tabSlide.isValue() && !isTabSlideStyle())));
/*  294 */     this.tabSlideSpeed.setFullWidth(true);
/*  295 */     Objects.requireNonNull(this.tabSlide); this.tabSlideSpeed.visible(this.tabSlide::isValue);
/*  296 */     this.tabInterpolationOpen.setFullWidth(true);
/*  297 */     this.tabInterpolationClose.setFullWidth(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  303 */     this.tabInterpolationOpen.visible(() -> Boolean.valueOf((this.tabSlide.isValue() && !isTabSlideStyle())));
/*  304 */     this.tabInterpolationClose.visible(() -> Boolean.valueOf((this.tabSlide.isValue() && !isTabSlideStyle())));
/*      */     
/*  306 */     this.chatFade.setFullWidth(true);
/*  307 */     this.chatSmoothScroll.setFullWidth(true);
/*  308 */     this.chatMessageAnimationType.setFullWidth(true);
/*  309 */     Objects.requireNonNull(this.chatSmoothScroll); this.chatMessageAnimationType.visible(this.chatSmoothScroll::isValue);
/*  310 */     this.chatSmoothSpeed.setFullWidth(true);
/*  311 */     Objects.requireNonNull(this.chatSmoothScroll); this.chatSmoothSpeed.visible(this.chatSmoothScroll::isValue);
/*  312 */     this.chatLeftInterpolation.setFullWidth(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  317 */     this.chatLeftInterpolation.visible(() -> Boolean.valueOf((this.chatSmoothScroll.isValue() && "Left".equalsIgnoreCase(this.chatMessageAnimationType.getSelected()))));
/*      */     
/*  319 */     this.smoothInputField.setFullWidth(true);
/*      */     
/*  321 */     this.hotbarSlide.setFullWidth(true);
/*  322 */     this.hotbarRollover.setFullWidth(true);
/*  323 */     Objects.requireNonNull(this.hotbarSlide); this.hotbarRollover.visible(this.hotbarSlide::isValue);
/*  324 */     this.hotbarSpeed.setFullWidth(true);
/*  325 */     Objects.requireNonNull(this.hotbarSlide); this.hotbarSpeed.visible(this.hotbarSlide::isValue);
/*      */     
/*  327 */     this.listSmoothScroll.setFullWidth(true);
/*  328 */     this.listSpeed.setFullWidth(true);
/*  329 */     Objects.requireNonNull(this.listSmoothScroll); this.listSpeed.visible(this.listSmoothScroll::isValue);
/*  330 */     this.listLinesPerScroll.setFullWidth(true);
/*      */     
/*  332 */     this.smoothF5.setFullWidth(true);
/*  333 */     this.smoothF5Speed.setFullWidth(true);
/*  334 */     Objects.requireNonNull(this.smoothF5); this.smoothF5Speed.visible(this.smoothF5::isValue);
/*  335 */     this.smoothF5Interpolation.setFullWidth(true);
/*  336 */     Objects.requireNonNull(this.smoothF5); this.smoothF5Interpolation.visible(this.smoothF5::isValue);
/*      */     
/*  338 */     this.dynamicCursor.setFullWidth(true);
/*  339 */     this.comfortableTextSelection.setFullWidth(true);
/*      */     
/*  341 */     setup(new Setting[] { (Setting)this.tabSection, (Setting)this.tabSlide, (Setting)this.tabAnimationType, (Setting)this.tabFade, (Setting)this.tabSlideSpeed, (Setting)this.tabInterpolationOpen, (Setting)this.tabInterpolationClose, (Setting)this.chatSection, (Setting)this.chatFade, (Setting)this.chatSmoothScroll, (Setting)this.chatMessageAnimationType, (Setting)this.chatSmoothSpeed, (Setting)this.chatLeftInterpolation, (Setting)this.smoothInputField, (Setting)this.hotbarSection, (Setting)this.hotbarSlide, (Setting)this.hotbarRollover, (Setting)this.hotbarSpeed, (Setting)this.cameraSection, (Setting)this.smoothF5, (Setting)this.smoothF5Speed, (Setting)this.smoothF5Interpolation, (Setting)this.cursorSection, (Setting)this.dynamicCursor, (Setting)this.comfortableTextSelection, (Setting)this.listsSection, (Setting)this.listSmoothScroll, (Setting)this.listSpeed, (Setting)this.listLinesPerScroll });
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
/*      */   public boolean isDynamicCursorEnabled() {
/*  357 */     return (isEnabled() && this.dynamicCursor.isValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isComfortableTextSelectionEnabled() {
/*  367 */     return (isEnabled() && this.comfortableTextSelection.isValue());
/*      */   }
/*      */   
/*      */   public static Animations getInstance() {
/*  371 */     return INSTANCE;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  376 */     return "Adds smooth UI animations: chat message fade-in, tab list slide-in/out";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getIcon() {
/*  381 */     return "animations.png";
/*      */   }
/*      */ 
/*      */   
/*      */   public float getIconSize() {
/*  386 */     return 21.0F;
/*      */   }
/*      */   
/*      */   public boolean isChatFadeEnabled() {
/*  390 */     return (isEnabled() && this.chatFade.isValue());
/*      */   }
/*      */   
/*      */   public boolean isTabSlideEnabled() {
/*  394 */     return (isEnabled() && this.tabSlide.isValue());
/*      */   }
/*      */   
/*      */   public boolean isTabFadeEnabled() {
/*  398 */     if (!isTabSlideEnabled()) {
/*  399 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  405 */     if (isTabSlideStyle()) {
/*  406 */       return true;
/*      */     }
/*  408 */     return this.tabFade.isValue();
/*      */   }
/*      */   
/*      */   public boolean isHotbarSlideEnabled() {
/*  412 */     return (isEnabled() && this.hotbarSlide.isValue());
/*      */   }
/*      */   
/*      */   public boolean isHotbarRolloverEnabled() {
/*  416 */     return (isHotbarSlideEnabled() && this.hotbarRollover.isValue());
/*      */   }
/*      */   
/*      */   public boolean isChatSmoothScrollEnabled() {
/*  420 */     return (isEnabled() && this.chatSmoothScroll.isValue());
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
/*      */   public boolean isChatMessageSlideLeft() {
/*  433 */     if (!isChatSmoothScrollEnabled()) {
/*  434 */       return false;
/*      */     }
/*  436 */     return "Left".equalsIgnoreCase(this.chatMessageAnimationType.getSelected());
/*      */   }
/*      */   
/*      */   public boolean isSmoothInputFieldEnabled() {
/*  440 */     return (isEnabled() && this.smoothInputField.isValue());
/*      */   }
/*      */   
/*      */   public boolean isListSmoothScrollEnabled() {
/*  444 */     return (isEnabled() && this.listSmoothScroll.isValue());
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
/*      */   public int linesPerScroll() {
/*  457 */     if (!isEnabled()) {
/*  458 */       return 1;
/*      */     }
/*  460 */     int v = (int)this.listLinesPerScroll.getValue();
/*  461 */     if (v < 1) v = 1; 
/*  462 */     if (v > 10) v = 10; 
/*  463 */     return v;
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
/*      */   public float chatSlideFadeMs() {
/*  478 */     float v = this.chatSmoothSpeed.getValue();
/*  479 */     if (v < 1.0F) v = 1.0F; 
/*  480 */     return 750.0F / v;
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
/*      */   public float chatLeftSlideFadeMs() {
/*  508 */     return chatSlideFadeMs() * 4.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float smoothnessForSpeed(float speed) {
/*  518 */     return (float)Math.pow(0.001500000013038516D, (speed / 5.0F));
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
/*      */   public float tabAnimationDurationMs() {
/*  532 */     float v = this.tabSlideSpeed.getValue();
/*  533 */     if (v < 1.0F) v = 1.0F; 
/*  534 */     return 3500.0F / v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float f5AnimationDurationMs() {
/*  545 */     float v = this.smoothF5Speed.getValue();
/*  546 */     if (v < 1.0F) v = 1.0F; 
/*  547 */     return 3500.0F / v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Interpolation getTabInterpolationOpen() {
/*  553 */     return Interpolations.getByName(this.tabInterpolationOpen.getSelected());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Interpolation getTabInterpolationClose() {
/*  559 */     return Interpolations.getByName(this.tabInterpolationClose.getSelected());
/*      */   }
/*      */ 
/*      */   
/*      */   public Interpolation getChatLeftInterpolation() {
/*  564 */     return Interpolations.getByName(this.chatLeftInterpolation.getSelected());
/*      */   }
/*      */ 
/*      */   
/*      */   public Interpolation getSmoothF5Interpolation() {
/*  569 */     return Interpolations.getByName(this.smoothF5Interpolation.getSelected());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeChatFadeInMultiplier(int messageAgeTicks) {
/*  578 */     if (!isChatFadeEnabled()) {
/*  579 */       return 1.0F;
/*      */     }
/*  581 */     if (messageAgeTicks < 0 || messageAgeTicks >= 4) {
/*  582 */       return 1.0F;
/*      */     }
/*  584 */     return (messageAgeTicks + 1) / 4.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float tickTabSlide(boolean keyPressed) {
/*      */     float dt;
/*  594 */     if (!isTabSlideEnabled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  603 */       this.tabCurrentOffset = -18.0F;
/*  604 */       this.tabTargetOffset = -18.0F;
/*  605 */       this.tabLastFrameNanos = 0L;
/*  606 */       this.tabLinearProgress = 0.0F;
/*  607 */       this.tabAnimationStartProgress = 0.0F;
/*  608 */       this.tabAnimationStartNanos = 0L;
/*  609 */       this.tabAnimationTargetOpen = false;
/*  610 */       return -18.0F;
/*      */     } 
/*      */     
/*  613 */     this.tabTargetOffset = keyPressed ? 0.0F : -18.0F;
/*      */     
/*  615 */     long now = System.nanoTime();
/*      */     
/*  617 */     if (this.tabLastFrameNanos == 0L) {
/*  618 */       dt = 0.016666668F;
/*      */     } else {
/*  620 */       dt = (float)(now - this.tabLastFrameNanos) / 1.0E9F;
/*  621 */       if (dt > 0.25F) dt = 0.25F; 
/*      */     } 
/*  623 */     this.tabLastFrameNanos = now;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  629 */     float smoothness = smoothnessForSpeed(this.tabSlideSpeed.getValue());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  634 */     float decay = (float)Math.pow(smoothness, dt);
/*  635 */     this.tabCurrentOffset = (this.tabCurrentOffset - this.tabTargetOffset) * decay + this.tabTargetOffset;
/*      */ 
/*      */ 
/*      */     
/*  639 */     if (Math.abs(this.tabCurrentOffset - this.tabTargetOffset) < 0.15F) {
/*  640 */       this.tabCurrentOffset = this.tabTargetOffset;
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
/*  652 */     if (keyPressed != this.tabAnimationTargetOpen || this.tabAnimationStartNanos == 0L) {
/*  653 */       this.tabAnimationStartProgress = this.tabLinearProgress;
/*  654 */       this.tabAnimationStartNanos = now;
/*  655 */       this.tabAnimationTargetOpen = keyPressed;
/*      */     } 
/*  657 */     float endProgress = keyPressed ? 1.0F : 0.0F;
/*  658 */     float duration = tabAnimationDurationMs();
/*  659 */     if (duration < 1.0F) duration = 1.0F; 
/*  660 */     float linearT = (float)(now - this.tabAnimationStartNanos) / 1000000.0F / duration;
/*  661 */     if (linearT < 0.0F) linearT = 0.0F; 
/*  662 */     if (linearT > 1.0F) linearT = 1.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  669 */     String interpName = keyPressed ? this.tabInterpolationOpen.getSelected() : this.tabInterpolationClose.getSelected();
/*  670 */     if ("Default".equals(interpName)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  678 */       float decayProgress = 1.0F + this.tabCurrentOffset / 18.0F;
/*  679 */       if (decayProgress < 0.0F) decayProgress = 0.0F; 
/*  680 */       if (decayProgress > 1.0F) decayProgress = 1.0F; 
/*  681 */       this.tabLinearProgress = decayProgress;
/*      */     } else {
/*  683 */       Interpolation interp = Interpolations.getByName(interpName);
/*  684 */       float shapedT = (float)interp.interpolate(linearT);
/*  685 */       this.tabLinearProgress = this.tabAnimationStartProgress + (endProgress - this.tabAnimationStartProgress) * shapedT;
/*  686 */       if (this.tabLinearProgress < 0.0F) this.tabLinearProgress = 0.0F; 
/*  687 */       if (this.tabLinearProgress > 1.0F) this.tabLinearProgress = 1.0F;
/*      */     
/*      */     } 
/*  690 */     return this.tabCurrentOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTabSlideRendering(boolean keyPressed) {
/*  700 */     if (!isTabSlideEnabled()) {
/*  701 */       return keyPressed;
/*      */     }
/*  703 */     if (keyPressed) return true;
/*      */     
/*  705 */     return (this.tabCurrentOffset > -17.85F);
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
/*      */   public void snapTabClosed() {
/*  719 */     this.tabCurrentOffset = -18.0F;
/*  720 */     this.tabTargetOffset = -18.0F;
/*  721 */     this.tabLastFrameNanos = 0L;
/*  722 */     this.tabLinearProgress = 0.0F;
/*  723 */     this.tabAnimationStartProgress = 0.0F;
/*  724 */     this.tabAnimationStartNanos = 0L;
/*  725 */     this.tabAnimationTargetOpen = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float currentTabSlideOffset() {
/*  736 */     return this.tabCurrentOffset;
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
/*      */   public float currentTabProgress() {
/*  750 */     float p = this.tabLinearProgress;
/*  751 */     if (p < 0.0F) return 0.0F; 
/*  752 */     if (p > 1.0F) return 1.0F; 
/*  753 */     return p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTabSlideStyle() {
/*  761 */     return "Slide".equals(this.tabAnimationType.getSelected());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTabScaleStyle() {
/*  771 */     return "Scale".equals(this.tabAnimationType.getSelected());
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
/*      */   public boolean isTabSlideScaleStyle() {
/*  784 */     return "Slide+Scale".equals(this.tabAnimationType.getSelected());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float currentTabAlpha() {
/*  837 */     return 1.0F;
/*      */   }
/*      */   
/*      */   private static float smoothTabFade(float alpha) {
/*  841 */     float clamped = alpha;
/*  842 */     if (clamped < 0.0F) clamped = 0.0F; 
/*  843 */     if (clamped > 1.0F) clamped = 1.0F; 
/*  844 */     return clamped * clamped * (3.0F - 2.0F * clamped);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSmoothF5Enabled() {
/*  854 */     return (isEnabled() && this.smoothF5.isValue());
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
/*      */   public float tickSmoothF5(class_5498 currentPerspective) {
/*  881 */     if (!isSmoothF5Enabled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  888 */       float vanillaDistance = (currentPerspective != null && currentPerspective.method_31034()) ? 0.0F : 4.0F;
/*  889 */       this.f5CurrentDistance = vanillaDistance;
/*  890 */       this.f5TargetDistance = vanillaDistance;
/*  891 */       this.f5LastPerspective = currentPerspective;
/*  892 */       this.f5LastFrameNanos = 0L;
/*  893 */       this.f5AnimationStartDistance = vanillaDistance;
/*  894 */       this.f5AnimationStartNanos = 0L;
/*  895 */       return this.f5CurrentDistance;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  901 */     if (this.f5LastPerspective == null) {
/*      */       
/*  903 */       float seed = (currentPerspective != null && currentPerspective.method_31034()) ? 0.0F : 4.0F;
/*  904 */       this.f5CurrentDistance = seed;
/*  905 */       this.f5TargetDistance = seed;
/*  906 */       this.f5LastPerspective = currentPerspective;
/*  907 */       this.f5LastFrameNanos = 0L;
/*  908 */       this.f5AnimationStartDistance = seed;
/*  909 */       this.f5AnimationStartNanos = 0L;
/*  910 */       return this.f5CurrentDistance;
/*      */     } 
/*      */     
/*  913 */     if (this.f5LastPerspective != currentPerspective) {
/*  914 */       boolean curFirst = (currentPerspective != null && currentPerspective.method_31034());
/*  915 */       if (curFirst) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  922 */         this.f5CurrentDistance = 0.0F;
/*  923 */         this.f5TargetDistance = 0.0F;
/*  924 */         this.f5AnimationStartDistance = 0.0F;
/*  925 */         this.f5AnimationStartNanos = 0L;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  935 */         this.f5CurrentDistance = 0.0F;
/*  936 */         this.f5TargetDistance = 4.0F;
/*  937 */         this.f5AnimationStartDistance = 0.0F;
/*  938 */         this.f5AnimationStartNanos = System.nanoTime();
/*      */       } 
/*  940 */       this.f5LastPerspective = currentPerspective;
/*      */     } 
/*      */     
/*  943 */     String interpName = this.smoothF5Interpolation.getSelected();
/*  944 */     long now = System.nanoTime();
/*      */     
/*  946 */     if ("Default".equals(interpName)) {
/*      */       float dt;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  953 */       if (this.f5LastFrameNanos == 0L) {
/*  954 */         dt = 0.016666668F;
/*      */       } else {
/*  956 */         dt = (float)(now - this.f5LastFrameNanos) / 1.0E9F;
/*  957 */         if (dt > 0.25F) dt = 0.25F; 
/*      */       } 
/*  959 */       this.f5LastFrameNanos = now;
/*  960 */       float smoothness = smoothnessForSpeed(this.smoothF5Speed.getValue());
/*  961 */       float decay = (float)Math.pow(smoothness, dt);
/*  962 */       this.f5CurrentDistance = (this.f5CurrentDistance - this.f5TargetDistance) * decay + this.f5TargetDistance;
/*  963 */       if (Math.abs(this.f5CurrentDistance - this.f5TargetDistance) < 0.02F) {
/*  964 */         this.f5CurrentDistance = this.f5TargetDistance;
/*  965 */         this.f5AnimationStartNanos = 0L;
/*      */       } 
/*  967 */       return this.f5CurrentDistance;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  972 */     if (this.f5AnimationStartNanos == 0L) {
/*  973 */       this.f5LastFrameNanos = 0L;
/*  974 */       return this.f5CurrentDistance;
/*      */     } 
/*      */     
/*  977 */     this.f5LastFrameNanos = now;
/*  978 */     float duration = f5AnimationDurationMs();
/*  979 */     if (duration < 1.0F) duration = 1.0F; 
/*  980 */     float linearT = (float)(now - this.f5AnimationStartNanos) / 1000000.0F / duration;
/*  981 */     if (linearT < 0.0F) linearT = 0.0F; 
/*  982 */     if (linearT > 1.0F) linearT = 1.0F;
/*      */     
/*  984 */     Interpolation interp = Interpolations.getByName(interpName);
/*  985 */     float shapedT = (float)interp.interpolate(linearT);
/*  986 */     this.f5CurrentDistance = this.f5AnimationStartDistance + (this.f5TargetDistance - this.f5AnimationStartDistance) * shapedT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  993 */     if (linearT >= 1.0F) {
/*  994 */       this.f5CurrentDistance = this.f5TargetDistance;
/*  995 */       this.f5AnimationStartNanos = 0L;
/*  996 */     } else if (Math.abs(this.f5CurrentDistance - this.f5TargetDistance) < 0.02F && shapedT >= 1.0F) {
/*      */       
/*  998 */       this.f5CurrentDistance = this.f5TargetDistance;
/*  999 */       this.f5AnimationStartNanos = 0L;
/*      */     } 
/* 1001 */     return this.f5CurrentDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float currentF5Distance() {
/* 1010 */     return this.f5CurrentDistance;
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
/*      */   public boolean isF5AnimationActive() {
/* 1025 */     return (isSmoothF5Enabled() && this.f5CurrentDistance > 0.02F);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Animations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */