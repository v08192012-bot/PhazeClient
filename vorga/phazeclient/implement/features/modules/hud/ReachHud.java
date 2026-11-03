/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3532;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReachHud
/*     */   extends RectHudModule
/*     */ {
/*     */   private static final long RESET_TIMEOUT_MS = 5000L;
/*  22 */   private static final ReachHud INSTANCE = new ReachHud();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private float lastReach = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private long lastHitTimeMillis = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Add \"Reach:\" prefix instead of just \"X blocks\"")).setValue(false);
/*     */   
/*     */   public static ReachHud getInstance() {
/*  51 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private ReachHud() {
/*  55 */     super("reach_hud", "Reach HUD");
/*  56 */     this.reverseOrder.setFullWidth(true);
/*  57 */     setup(new Setting[] { (Setting)this.reverseOrder });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  62 */     return "Shows hit distance on HUD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  67 */     return "reach_hud.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  72 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public void recordHitDistance(class_1657 player, class_1297 target) {
/*  76 */     if (player == null || target == null) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     class_243 eyePos = player.method_33571();
/*  81 */     class_238 box = target.method_5829();
/*  82 */     double closestX = class_3532.method_15350(eyePos.field_1352, box.field_1323, box.field_1320);
/*  83 */     double closestY = class_3532.method_15350(eyePos.field_1351, box.field_1322, box.field_1325);
/*  84 */     double closestZ = class_3532.method_15350(eyePos.field_1350, box.field_1321, box.field_1324);
/*     */     
/*  86 */     double dx = eyePos.field_1352 - closestX;
/*  87 */     double dy = eyePos.field_1351 - closestY;
/*  88 */     double dz = eyePos.field_1350 - closestZ;
/*  89 */     this.lastReach = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
/*  90 */     this.lastHitTimeMillis = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyAirSwing() {
/* 100 */     this.lastReach = 0.0F;
/* 101 */     this.lastHitTimeMillis = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedReach() {
/*     */     String value;
/* 112 */     if (this.lastHitTimeMillis > 0L && 
/* 113 */       System.currentTimeMillis() - this.lastHitTimeMillis > 5000L) {
/* 114 */       this.lastReach = 0.0F;
/* 115 */       this.lastHitTimeMillis = 0L;
/*     */     } 
/*     */     
/* 118 */     float rounded = Math.round(this.lastReach);
/*     */     
/* 120 */     if (Math.abs(this.lastReach - rounded) < 0.005F) {
/* 121 */       value = "" + (int)rounded + " blocks";
/*     */     } else {
/* 123 */       value = String.format(Locale.US, "%.2f blocks", new Object[] { Float.valueOf(this.lastReach) });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 128 */     return this.reverseOrder.isValue() ? ("Reach: " + value) : value;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\ReachHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */