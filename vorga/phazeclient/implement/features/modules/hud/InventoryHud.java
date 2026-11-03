/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_746;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
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
/*     */ public final class InventoryHud
/*     */   extends RectHudModule
/*     */ {
/*  23 */   private static final InventoryHud INSTANCE = new InventoryHud();
/*     */   
/*  25 */   public final BooleanSetting drawCounts = (new BooleanSetting("Item Counts", "Draw the stack count badge on each item"))
/*     */ 
/*     */     
/*  28 */     .setValue(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InventoryHud() {
/*  36 */     super("inventory_hud", "Inventory", 22.0F, 22.0F, 1.5F);
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
/*  47 */     this.background.setVisible(() -> Boolean.valueOf(false));
/*  48 */     this.backgroundPreset.setVisible(() -> Boolean.valueOf(false));
/*  49 */     this.colorBrightness.setVisible(() -> Boolean.valueOf(false));
/*  50 */     this.backgroundOpacity.setVisible(() -> Boolean.valueOf(false));
/*  51 */     this.backgroundBlurRadius.setVisible(() -> Boolean.valueOf(false));
/*  52 */     this.textShadow.setVisible(() -> Boolean.valueOf(false));
/*  53 */     this.showBrackets.setVisible(() -> Boolean.valueOf(false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     this.mainSection.setVisible(() -> Boolean.valueOf(false));
/*  63 */     this.colorSection.setVisible(() -> Boolean.valueOf(false));
/*     */     
/*  65 */     this.drawCounts.setFullWidth(true);
/*  66 */     setup(new Setting[] { (Setting)this.drawCounts });
/*     */   }
/*     */   
/*     */   public static InventoryHud getInstance() {
/*  70 */     return INSTANCE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final class_1799[] SNAPSHOT = new class_1799[27];
/* 102 */   private static final boolean[] OVERLAY_FLAGS = new boolean[27];
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static int lastSnapshotTick = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void refreshSnapshotIfStale(class_310 mc) {
/* 115 */     if (mc == null || mc.field_1724 == null) {
/*     */       return;
/*     */     }
/* 118 */     int tick = (mc.field_1705 != null) ? mc.field_1705.method_1738() : 0;
/* 119 */     if (tick == lastSnapshotTick) {
/*     */       return;
/*     */     }
/* 122 */     lastSnapshotTick = tick;
/* 123 */     class_746 player = mc.field_1724;
/* 124 */     class_1661 inv = player.method_31548();
/* 125 */     for (int i = 0; i < 27; i++) {
/* 126 */       class_1799 stack = (class_1799)inv.field_7547.get(9 + i);
/* 127 */       SNAPSHOT[i] = stack;
/* 128 */       OVERLAY_FLAGS[i] = computeOverlayFlag(stack, mc, player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1799[] getSnapshotStacks() {
/* 135 */     return SNAPSHOT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] getSnapshotOverlayFlags() {
/* 141 */     return OVERLAY_FLAGS;
/*     */   }
/*     */   
/*     */   private static boolean computeOverlayFlag(class_1799 stack, class_310 mc, class_746 player) {
/* 145 */     if (stack == null || stack.method_7960()) return false; 
/* 146 */     if (stack.method_7947() != 1) return true; 
/* 147 */     if (stack.method_31578()) return true; 
/* 148 */     return (player.method_7357().method_7905(stack, 0.0F) > 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 153 */     return "Renders your 27 storage slots or last-seen ender chest on the HUD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 158 */     return "inventory_hud.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 163 */     return 21.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\InventoryHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */