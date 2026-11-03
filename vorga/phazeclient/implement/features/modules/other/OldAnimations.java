/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_1306;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1839;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_7833;
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
/*     */ public final class OldAnimations
/*     */   extends Module
/*     */ {
/*  37 */   private static final OldAnimations INSTANCE = new OldAnimations();
/*     */ 
/*     */   
/*  40 */   public final SectionSetting swordBlockSection = new SectionSetting("Sword Blocking");
/*  41 */   public final BooleanSetting swordBlocking = (new BooleanSetting("Sword Blocking", "Render sword in the classic 1.7 blocking pose when right-clicking or using a shield"))
/*     */ 
/*     */     
/*  44 */     .setValue(true);
/*     */ 
/*     */   
/*  47 */   public final SectionSetting swingSection = new SectionSetting("Swing & Equip");
/*  48 */   public final BooleanSetting oldSwing = (new BooleanSetting("1.7 Swing", "Classic 1.7/1.8 snappy swing curve and motion"))
/*     */ 
/*     */     
/*  51 */     .setValue(true);
/*  52 */   public final BooleanSetting noReEquip = (new BooleanSetting("No Re-Equip", "Prevent item from lowering down when taking durability damage, consuming items, or attacking"))
/*     */ 
/*     */     
/*  55 */     .setValue(true);
/*  56 */   public final ValueSetting swingSpeed = (new ValueSetting("Swing Speed", "Animation speed multiplier for hand swinging"))
/*     */ 
/*     */     
/*  59 */     .range(0.5F, 2.0F).step(0.05F).setValue(1.0F).snapTo(1.0F, 0.05F);
/*     */ 
/*     */   
/*  62 */   public final SectionSetting actionsSection = new SectionSetting("Actions");
/*  63 */   public final BooleanSetting oldBow = (new BooleanSetting("1.7 Bow", "Classic 1.7 straight bow pullback positioning"))
/*     */ 
/*     */     
/*  66 */     .setValue(true);
/*  67 */   public final BooleanSetting oldRod = (new BooleanSetting("1.7 Rod", "Classic fishing rod held angle and position"))
/*     */ 
/*     */     
/*  70 */     .setValue(false);
/*     */ 
/*     */   
/*  73 */   public final SectionSetting sneakSection = new SectionSetting("Sneak");
/*  74 */   public final BooleanSetting oldSneak = (new BooleanSetting("1.7 Sneak", "Instant 1.7 camera eye height transition when crouching"))
/*     */ 
/*     */     
/*  77 */     .setValue(false);
/*     */   
/*     */   private OldAnimations() {
/*  80 */     super("oldanimations", "Old Animations", ModuleCategory.OTHER);
/*     */     
/*  82 */     this.swordBlocking.setFullWidth(true);
/*     */     
/*  84 */     this.oldSwing.setFullWidth(true);
/*  85 */     this.noReEquip.setFullWidth(true);
/*  86 */     this.swingSpeed.setFullWidth(true);
/*     */     
/*  88 */     this.oldBow.setFullWidth(true);
/*  89 */     this.oldRod.setFullWidth(true);
/*     */     
/*  91 */     this.oldSneak.setFullWidth(true);
/*     */     
/*  93 */     setup(new Setting[] { (Setting)this.swordBlockSection, (Setting)this.swordBlocking, (Setting)this.swingSection, (Setting)this.oldSwing, (Setting)this.noReEquip, (Setting)this.swingSpeed, (Setting)this.actionsSection, (Setting)this.oldBow, (Setting)this.oldRod, (Setting)this.sneakSection, (Setting)this.oldSneak });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OldAnimations getInstance() {
/* 102 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 107 */     return "Classic 1.7 / 1.8 combat animations, sword blocking, smooth re-equip and bow";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 112 */     return "old_animations.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 117 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/* 122 */     return false;
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
/*     */   public static boolean isPlayerSwordBlocking(class_1657 player) {
/* 134 */     if (player == null) return false; 
/* 135 */     class_1799 main = player.method_6047();
/* 136 */     if (!(main.method_7909() instanceof net.minecraft.class_1829)) return false;
/*     */ 
/*     */     
/* 139 */     if (player.method_6115()) {
/* 140 */       class_1799 active = player.method_6030();
/* 141 */       if (active.method_7909() instanceof net.minecraft.class_1819 || active.method_7909() instanceof net.minecraft.class_1829) {
/* 142 */         return true;
/*     */       }
/* 144 */       if (active.method_7976() == class_1839.field_8950 || active.method_7976() == class_1839.field_8946) {
/* 145 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     class_310 mc = class_310.method_1551();
/* 150 */     if (player == mc.field_1724 && mc.field_1755 == null && mc.field_1690 != null && mc.field_1690.field_1904 != null && mc.field_1690.field_1904.method_1434()) {
/* 151 */       class_1799 off = player.method_6079();
/* 152 */       if (player.method_6115() && (off.method_7976() == class_1839.field_8950 || off.method_7976() == class_1839.field_8946)) {
/* 153 */         return false;
/*     */       }
/* 155 */       return true;
/*     */     } 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSwordBlockingActive(class_1657 player) {
/* 165 */     OldAnimations mod = getInstance();
/* 166 */     if (mod == null || !mod.isEnabled() || !mod.swordBlocking.isValue()) {
/* 167 */       return false;
/*     */     }
/* 169 */     return isPlayerSwordBlocking(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderSwordBlock(class_4587 matrices, class_1306 arm, int armSign, float equipProgress) {
/* 177 */     renderSwordBlock(matrices, arm, armSign, equipProgress, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderSwordBlock(class_4587 matrices, class_1306 arm, int armSign, float equipProgress, float swingProgress) {
/* 182 */     matrices.method_46416(armSign * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
/*     */ 
/*     */     
/* 185 */     if (swingProgress > 0.0F) {
/* 186 */       float g = class_3532.method_15374(class_3532.method_15355(swingProgress) * 3.1415927F);
/* 187 */       matrices.method_46416(armSign * -g * 0.2F, class_3532.method_15374(class_3532.method_15355(swingProgress) * 6.2831855F) * 0.1F, -g * 0.1F);
/*     */     } 
/*     */ 
/*     */     
/* 191 */     matrices.method_46416(armSign * -0.14142136F, 0.08F, 0.14142136F);
/* 192 */     matrices.method_22907(class_7833.field_40714.rotationDegrees(-102.25F));
/* 193 */     matrices.method_22907(class_7833.field_40716.rotationDegrees(armSign * 13.365F));
/* 194 */     matrices.method_22907(class_7833.field_40718.rotationDegrees(armSign * 78.05F));
/*     */ 
/*     */     
/* 197 */     if (swingProgress > 0.0F) {
/* 198 */       float g = class_3532.method_15374(class_3532.method_15355(swingProgress) * 3.1415927F);
/* 199 */       matrices.method_22907(class_7833.field_40714.rotationDegrees(g * -25.0F));
/* 200 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(armSign * g * 15.0F));
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
/*     */   public static void renderOldSwing(class_4587 matrices, class_1306 arm, int armSign, float swingProgress, float equipProgress) {
/* 214 */     float f = -0.4F * class_3532.method_15374(class_3532.method_15355(swingProgress) * 3.1415927F);
/* 215 */     float g = 0.2F * class_3532.method_15374(class_3532.method_15355(swingProgress) * 6.2831855F);
/* 216 */     float h = -0.2F * class_3532.method_15374(swingProgress * 3.1415927F);
/* 217 */     matrices.method_46416(armSign * f, g, h);
/*     */ 
/*     */     
/* 220 */     matrices.method_46416(armSign * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
/*     */ 
/*     */     
/* 223 */     float sin1 = class_3532.method_15374(swingProgress * swingProgress * 3.1415927F);
/* 224 */     float sinSqrt = class_3532.method_15374(class_3532.method_15355(swingProgress) * 3.1415927F);
/* 225 */     matrices.method_22907(class_7833.field_40716.rotationDegrees(armSign * (45.0F + sin1 * -20.0F)));
/* 226 */     matrices.method_22907(class_7833.field_40718.rotationDegrees(armSign * sinSqrt * -20.0F));
/* 227 */     matrices.method_22907(class_7833.field_40714.rotationDegrees(sinSqrt * -80.0F));
/*     */ 
/*     */ 
/*     */     
/* 231 */     matrices.method_22907(class_7833.field_40716.rotationDegrees(armSign * -45.0F));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\OldAnimations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */