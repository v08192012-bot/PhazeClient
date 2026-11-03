/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
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
/*     */ public final class TpsHud
/*     */   extends RectHudModule
/*     */ {
/*  26 */   private static final TpsHud INSTANCE = new TpsHud();
/*     */ 
/*     */   
/*     */   public static final int SAMPLE_WINDOW = 60;
/*     */   
/*  31 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  32 */   public final BooleanSetting colorByTps = (new BooleanSetting("Color By TPS", "Tint the TPS reading green / yellow / red based on tier"))
/*     */ 
/*     */     
/*  35 */     .setValue(true);
/*     */ 
/*     */ 
/*     */   
/*  39 */   private final ArrayDeque<Double> samples = new ArrayDeque<>(60);
/*  40 */   private long lastSampleNanos = 0L;
/*  41 */   private long lastWorldTime = 0L;
/*     */   
/*     */   private TpsHud() {
/*  44 */     super("tps_hud", "TPS", 22.0F, 22.0F, 1.0F);
/*  45 */     this.colorByTps.setFullWidth(true);
/*  46 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.colorByTps });
/*     */   }
/*     */   
/*     */   public static TpsHud getInstance() {
/*  50 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  55 */     return "Estimates server TPS based on world-time advancement";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  60 */     return "tps_hud.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  65 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordSample(long currentWorldTime) {
/*  76 */     long now = System.nanoTime();
/*  77 */     if (this.lastSampleNanos == 0L) {
/*  78 */       this.lastSampleNanos = now;
/*  79 */       this.lastWorldTime = currentWorldTime;
/*     */       return;
/*     */     } 
/*  82 */     double deltaSeconds = (now - this.lastSampleNanos) / 1.0E9D;
/*  83 */     if (deltaSeconds < 0.2D)
/*     */       return; 
/*  85 */     long deltaTicks = currentWorldTime - this.lastWorldTime;
/*  86 */     if (deltaTicks < 0L) {
/*  87 */       this.lastSampleNanos = now;
/*  88 */       this.lastWorldTime = currentWorldTime;
/*     */       return;
/*     */     } 
/*  91 */     double tps = deltaTicks / deltaSeconds;
/*  92 */     if (tps > 20.5D) tps = 20.0D; 
/*  93 */     this.samples.addFirst(Double.valueOf(tps));
/*  94 */     while (this.samples.size() > 60) {
/*  95 */       this.samples.removeLast();
/*     */     }
/*  97 */     this.lastSampleNanos = now;
/*  98 */     this.lastWorldTime = currentWorldTime;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSmoothedTps() {
/* 104 */     if (this.samples.isEmpty()) return 20.0D; 
/* 105 */     double sum = 0.0D;
/* 106 */     for (Double s : this.samples) sum += s.doubleValue(); 
/* 107 */     return sum / this.samples.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedText() {
/* 112 */     return String.format("TPS: %.1f", new Object[] { Double.valueOf(getSmoothedTps()) });
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 117 */     if (!this.colorByTps.isValue()) return -1; 
/* 118 */     double tps = getSmoothedTps();
/* 119 */     if (tps >= 18.0D) return -11141291; 
/* 120 */     if (tps >= 15.0D) return -171; 
/* 121 */     return -43691;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\TpsHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */