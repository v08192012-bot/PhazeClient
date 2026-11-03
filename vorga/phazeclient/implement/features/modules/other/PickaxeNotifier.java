/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2868;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3414;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_5250;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PickaxeNotifier
/*     */   extends Module
/*     */ {
/*  56 */   private static final PickaxeNotifier INSTANCE = new PickaxeNotifier();
/*     */   
/*     */   private static final int MIN_HOTBAR_SLOT = 1;
/*     */   private static final int MAX_HOTBAR_SLOT = 9;
/*  60 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */   
/*  62 */   public final ValueSetting durabilityThreshold = (new ValueSetting("Durability Threshold", "Warn when remaining durability drops below this many uses"))
/*     */ 
/*     */     
/*  65 */     .range(1, 2031).step(1.0F).setValue(50.0F);
/*     */   
/*  67 */   public final BooleanSetting autoSwitch = (new BooleanSetting("Auto Switch", "Automatically switch to the configured hotbar slot when the pickaxe falls under the threshold"))
/*     */ 
/*     */     
/*  70 */     .setValue(false);
/*  71 */   public final BooleanSetting playSound = (new BooleanSetting("Play Sound", "Play a warning sound together with the low durability message"))
/*     */ 
/*     */     
/*  74 */     .setValue(true);
/*     */   
/*  76 */   public final ValueSetting swapSlot = (new ValueSetting("Swap Slot", "Hotbar slot (1-9) to switch to when Auto Switch fires"))
/*     */ 
/*     */     
/*  79 */     .range(1, 9).step(1.0F).setValue(2.0F)
/*  80 */     .visible(() -> Boolean.valueOf(this.autoSwitch.isValue()));
/*     */   
/*     */   private String warnedSignature;
/*     */   
/*     */   private PickaxeNotifier() {
/*  85 */     super("pickaxe_notifier", "Pickaxe Notifier", ModuleCategory.UTILITIES);
/*  86 */     this.durabilityThreshold.setFullWidth(true);
/*  87 */     this.autoSwitch.setFullWidth(true);
/*  88 */     this.playSound.setFullWidth(true);
/*  89 */     this.swapSlot.setFullWidth(true);
/*  90 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.durabilityThreshold, (Setting)this.autoSwitch, (Setting)this.playSound, (Setting)this.swapSlot });
/*     */   }
/*     */   
/*     */   public static PickaxeNotifier getInstance() {
/*  94 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  99 */     return "Warns when the held pickaxe is low on durability and can auto-swap to a safe hotbar slot";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 104 */     return "pickaxe_notifier.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 109 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAttackBlock() {
/* 118 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/* 121 */     class_310 mc = class_310.method_1551();
/* 122 */     if (mc == null || mc.field_1724 == null || mc.field_1761 == null) {
/*     */       return;
/*     */     }
/* 125 */     class_1799 stack = mc.field_1724.method_6047();
/* 126 */     if (!isTrackedPickaxe(stack)) {
/* 127 */       this.warnedSignature = null;
/*     */       return;
/*     */     } 
/* 130 */     int remaining = stack.method_7936() - stack.method_7919();
/* 131 */     if (remaining >= this.durabilityThreshold.getInt()) {
/* 132 */       this.warnedSignature = null;
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     String currentSignature = "" + (mc.field_1724.method_31548()).field_7545 + ":" + (mc.field_1724.method_31548()).field_7545;
/* 137 */     if (currentSignature.equals(this.warnedSignature)) {
/*     */       return;
/*     */     }
/* 140 */     this.warnedSignature = currentSignature;
/*     */     
/* 142 */     boolean switched = (this.autoSwitch.isValue() && switchToConfiguredSlot(mc));
/* 143 */     sendLowDurabilityMessage(mc, remaining, switched);
/*     */   }
/*     */   
/*     */   private boolean isTrackedPickaxe(class_1799 stack) {
/* 147 */     return (stack != null && !stack.method_7960() && stack.method_7963() && stack.method_7909() instanceof net.minecraft.class_1810);
/*     */   }
/*     */   
/*     */   private boolean switchToConfiguredSlot(class_310 mc) {
/* 151 */     int targetSlot = clampHotbarSlot(this.swapSlot.getInt()) - 1;
/* 152 */     if ((mc.field_1724.method_31548()).field_7545 == targetSlot) {
/* 153 */       return false;
/*     */     }
/* 155 */     (mc.field_1724.method_31548()).field_7545 = targetSlot;
/* 156 */     if (mc.method_1562() != null) {
/* 157 */       mc.method_1562().method_52787((class_2596)new class_2868(targetSlot));
/*     */     }
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   private void sendLowDurabilityMessage(class_310 mc, int remaining, boolean switched) {
/* 163 */     if (mc.field_1705 == null || mc.field_1705.method_1743() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 168 */     class_5250 line = class_2561.method_43470("[Phaze] ").method_27692(class_124.field_1065).method_10852((class_2561)class_2561.method_43470("Pickaxe durability low ").method_27692(class_124.field_1061)).method_10852((class_2561)class_2561.method_43470("(" + remaining + " left)").method_27692(class_124.field_1065));
/* 169 */     if (switched) {
/* 170 */       line.method_10852((class_2561)class_2561.method_43470(" -> slot " + clampHotbarSlot(this.swapSlot.getInt())).method_27692(class_124.field_1080));
/*     */     }
/* 172 */     mc.field_1705.method_1743().method_1812((class_2561)line);
/* 173 */     if (this.playSound.isValue() && mc.method_1483() != null) {
/* 174 */       mc.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)class_3417.field_14622
/* 175 */             .comp_349(), 1.25F, 1.0F));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int clampHotbarSlot(int slot) {
/* 181 */     return Math.max(1, Math.min(9, slot));
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 186 */     super.deactivate();
/* 187 */     this.warnedSignature = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PickaxeNotifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */