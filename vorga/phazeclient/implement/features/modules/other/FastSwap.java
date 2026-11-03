/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.class_1293;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1844;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_9334;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FastSwap
/*     */   extends Module
/*     */ {
/*  27 */   private static final FastSwap INSTANCE = new FastSwap();
/*     */   
/*     */   private static final String KEY_DESCRIPTION = "Select the first matching item in your hotbar";
/*     */   
/*     */   private static final String TRAPKA_NAME = "трапка";
/*     */   
/*     */   private static final String PLATE_NAME = "пласт";
/*     */   private static final String DISORIENTATION_NAME = "дезориентация";
/*     */   private static final String REVEALING_DUST_NAME = "явная пыль";
/*     */   private static final String BALL_LIGHTNING_NAME = "шаровая молния";
/*     */   private static final String SLIME_LUMP_NAME = "ком слизи";
/*     */   private static final String TURTLE_GRIP_NAME = "черепаший захват";
/*     */   private static final String SPIDER_FATE_NAME = "паучья судьба";
/*     */   private static final String STUN_NAME = "стан";
/*     */   private static final String MAGNETIC_SPHERE_NAME = "магнитный шар";
/*     */   private static final String EXPLOSIVE_TRAPKA_NAME = "взрывная трапка";
/*     */   private static final String EXPLOSIVE_THING_NAME = "взрывная штучка";
/*     */   private static final String STAR_STUN_NAME = "стан звезды";
/*  45 */   public final SectionSetting basicSection = new SectionSetting("Basic");
/*  46 */   public final SectionSetting funtimeSection = new SectionSetting("FunTime");
/*  47 */   public final SectionSetting fillCubeSection = new SectionSetting("FillCube");
/*     */   
/*  49 */   public final BindSetting chorusKey = bindSetting("Chorus Key");
/*  50 */   public final BindSetting enderpearlKey = bindSetting("Enderpearl Key");
/*  51 */   public final BindSetting healingPotionKey = bindSetting("Healing Potion Key");
/*     */   
/*  53 */   public final BindSetting trapkaKey = bindSetting("Trapka Key");
/*  54 */   public final BindSetting disorientationKey = bindSetting("Disorientation Key");
/*  55 */   public final BindSetting plateKey = bindSetting("Plate Key");
/*  56 */   public final BindSetting revealingDustKey = bindSetting("Revealing Dust Key");
/*     */   
/*  58 */   public final BindSetting ballLightningKey = bindSetting("Ball Lightning Key");
/*  59 */   public final BindSetting slimeLumpKey = bindSetting("Slime Lump Key");
/*  60 */   public final BindSetting turtleGripKey = bindSetting("Turtle Grip Key");
/*  61 */   public final BindSetting spiderFateKey = bindSetting("Spider Fate Key");
/*  62 */   public final BindSetting stunKey = bindSetting("Stun Key");
/*  63 */   public final BindSetting magneticSphereKey = bindSetting("Magnetic Sphere Key");
/*  64 */   public final BindSetting explosiveTrapkaKey = bindSetting("Explosive Trapka Key");
/*  65 */   public final BindSetting explosiveThingKey = bindSetting("Explosive Thing Key");
/*  66 */   public final BindSetting starStunKey = bindSetting("Star Stun Key");
/*  67 */   public final BindSetting snowLumpKey = bindSetting("Snow Lump Key");
/*     */   
/*     */   private final List<ItemHotkey> hotkeys;
/*     */   
/*     */   private FastSwap() {
/*  72 */     super("fast_swap", "Fast Swap", ModuleCategory.UTILITIES);
/*     */     
/*  74 */     this.funtimeSection.visible(this::shouldShowFunTimeSection);
/*  75 */     this.fillCubeSection.visible(this::shouldShowFillCubeSection);
/*     */     
/*  77 */     this.trapkaKey.visible(this::shouldShowFunTimeSection);
/*  78 */     this.disorientationKey.visible(this::shouldShowFunTimeSection);
/*  79 */     this.plateKey.visible(this::shouldShowFunTimeSection);
/*  80 */     this.revealingDustKey.visible(this::shouldShowFunTimeSection);
/*     */     
/*  82 */     this.ballLightningKey.visible(this::shouldShowFillCubeSection);
/*  83 */     this.slimeLumpKey.visible(this::shouldShowFillCubeSection);
/*  84 */     this.turtleGripKey.visible(this::shouldShowFillCubeSection);
/*  85 */     this.spiderFateKey.visible(this::shouldShowFillCubeSection);
/*  86 */     this.stunKey.visible(this::shouldShowFillCubeSection);
/*  87 */     this.magneticSphereKey.visible(this::shouldShowFillCubeSection);
/*  88 */     this.explosiveTrapkaKey.visible(this::shouldShowFillCubeSection);
/*  89 */     this.explosiveThingKey.visible(this::shouldShowFillCubeSection);
/*  90 */     this.starStunKey.visible(this::shouldShowFillCubeSection);
/*  91 */     this.snowLumpKey.visible(this::shouldShowFillCubeSection);
/*     */     
/*  93 */     setup(new Setting[] { (Setting)this.basicSection, (Setting)this.chorusKey, (Setting)this.enderpearlKey, (Setting)this.healingPotionKey, (Setting)this.funtimeSection, (Setting)this.trapkaKey, (Setting)this.disorientationKey, (Setting)this.plateKey, (Setting)this.revealingDustKey, (Setting)this.fillCubeSection, (Setting)this.ballLightningKey, (Setting)this.slimeLumpKey, (Setting)this.turtleGripKey, (Setting)this.spiderFateKey, (Setting)this.stunKey, (Setting)this.magneticSphereKey, (Setting)this.explosiveTrapkaKey, (Setting)this.explosiveThingKey, (Setting)this.starStunKey, (Setting)this.snowLumpKey });
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
/* 116 */     this.hotkeys = List.of(new ItemHotkey[] { new ItemHotkey(this.chorusKey, stack -> stack.method_31574(class_1802.field_8233)), new ItemHotkey(this.enderpearlKey, stack -> stack.method_31574(class_1802.field_8634)), new ItemHotkey(this.healingPotionKey, FastSwap::isHealingPotion), new ItemHotkey(this.trapkaKey, 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 121 */             namedItem(class_1802.field_22021, "трапка")), new ItemHotkey(this.disorientationKey, 
/* 122 */             namedItem(class_1802.field_8449, "дезориентация")), new ItemHotkey(this.plateKey, 
/* 123 */             namedItem(class_1802.field_8551, "пласт")), new ItemHotkey(this.revealingDustKey, 
/* 124 */             namedItem(class_1802.field_8479, "явная пыль")), new ItemHotkey(this.ballLightningKey, 
/*     */             
/* 126 */             namedItem(class_1802.field_8137, "шаровая молния")), new ItemHotkey(this.slimeLumpKey, 
/* 127 */             namedItem(class_1802.field_8777, "ком слизи")), new ItemHotkey(this.turtleGripKey, 
/* 128 */             namedItem(class_1802.field_8161, "черепаший захват")), new ItemHotkey(this.spiderFateKey, 
/* 129 */             namedItem(class_1802.field_8786, "паучья судьба")), new ItemHotkey(this.stunKey, 
/* 130 */             namedItem(class_1802.field_8449, "стан")), new ItemHotkey(this.magneticSphereKey, 
/* 131 */             namedItem(class_1802.field_8450, "магнитный шар")), new ItemHotkey(this.explosiveTrapkaKey, 
/* 132 */             namedItem(class_1802.field_8662, "взрывная трапка")), new ItemHotkey(this.explosiveThingKey, 
/* 133 */             namedItem(class_1802.field_8814, "взрывная штучка")), new ItemHotkey(this.starStunKey, 
/* 134 */             namedItem(class_1802.field_8137, "стан звезды")), new ItemHotkey(this.snowLumpKey, stack -> stack.method_31574(class_1802.field_8543)) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FastSwap getInstance() {
/* 140 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 145 */     return Lang.translate("Quickly selects matching hotbar items on hotkey press");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 150 */     return "autoswap.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 155 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public void onKey(int key, int action) {
/* 159 */     if (!isEnabled() || action != 1 || key == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     class_310 mc = class_310.method_1551();
/* 164 */     if (mc == null || mc.field_1724 == null || mc.field_1687 == null || mc.field_1755 != null) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     for (ItemHotkey hotkey : this.hotkeys) {
/* 169 */       if (!hotkey.bind.isVisible() || hotkey.bind.getKey() != key) {
/*     */         continue;
/*     */       }
/*     */       
/* 173 */       int slot = findMatchingHotbarSlot(mc, hotkey.matcher);
/* 174 */       if (slot != -1) {
/* 175 */         (mc.field_1724.method_31548()).field_7545 = slot;
/*     */       }
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldShowFunTimeSection() {
/* 182 */     return !ServerUtil.isFillCubeServer();
/*     */   }
/*     */   
/*     */   private boolean shouldShowFillCubeSection() {
/* 186 */     return !ServerUtil.isFunTimeServer();
/*     */   }
/*     */   
/*     */   private static BindSetting bindSetting(String name) {
/* 190 */     BindSetting setting = new BindSetting(name, "Select the first matching item in your hotbar");
/* 191 */     setting.setKey(-1);
/* 192 */     setting.setFullWidth(true);
/* 193 */     return setting;
/*     */   }
/*     */   
/*     */   private static int findMatchingHotbarSlot(class_310 mc, Predicate<class_1799> matcher) {
/* 197 */     for (int slot = 0; slot < 9; slot++) {
/* 198 */       class_1799 stack = mc.field_1724.method_31548().method_5438(slot);
/* 199 */       if (!stack.method_7960() && matcher.test(stack)) {
/* 200 */         return slot;
/*     */       }
/*     */     } 
/* 203 */     return -1;
/*     */   }
/*     */   
/*     */   private static Predicate<class_1799> namedItem(class_1792 item, String needle) {
/* 207 */     return stack -> (stack.method_31574(item) && matchesDisplayName(stack, needle));
/*     */   }
/*     */   
/*     */   private static boolean matchesDisplayName(class_1799 stack, String needle) {
/* 211 */     if (needle == null || needle.isBlank()) {
/* 212 */       return true;
/*     */     }
/* 214 */     String displayName = stack.method_7964().getString().toLowerCase(Locale.ROOT);
/* 215 */     return displayName.contains(needle.toLowerCase(Locale.ROOT));
/*     */   }
/*     */   
/*     */   private static boolean isHealingPotion(class_1799 stack) {
/* 219 */     if (!stack.method_31574(class_1802.field_8574) && !stack.method_31574(class_1802.field_8436) && !stack.method_31574(class_1802.field_8150)) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     class_1844 contents = (class_1844)stack.method_57824(class_9334.field_49651);
/* 224 */     if (contents == null) {
/* 225 */       return false;
/*     */     }
/*     */     
/* 228 */     for (class_1293 effect : contents.method_57397()) {
/* 229 */       if (effect.method_5579().equals(class_1294.field_5915)) {
/* 230 */         return true;
/*     */       }
/*     */     } 
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   private static final class ItemHotkey {
/*     */     private final BindSetting bind;
/*     */     private final Predicate<class_1799> matcher;
/*     */     
/*     */     private ItemHotkey(BindSetting bind, Predicate<class_1799> matcher) {
/* 241 */       this.bind = bind;
/* 242 */       this.matcher = matcher;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\FastSwap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */