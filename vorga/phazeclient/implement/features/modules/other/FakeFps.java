/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Random;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
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
/*     */ public final class FakeFps
/*     */   extends Module
/*     */ {
/*  30 */   private static final FakeFps INSTANCE = new FakeFps();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long UPDATE_INTERVAL_MS = 1000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  43 */   public final ValueSetting minFps = (new ValueSetting("Min FPS", "Lower bound of the fake FPS range (inclusive)"))
/*     */ 
/*     */     
/*  46 */     .range(1, 9999).step(1.0F).setValue(120.0F);
/*  47 */   public final ValueSetting maxFps = (new ValueSetting("Max FPS", "Upper bound of the fake FPS range (inclusive)"))
/*     */ 
/*     */     
/*  50 */     .range(1, 9999).step(1.0F).setValue(240.0F);
/*     */ 
/*     */   
/*  53 */   private int lastFps = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private long lastUpdateMs = 0L;
/*  59 */   private final Random random = new Random();
/*     */   
/*     */   private FakeFps() {
/*  62 */     super("fake_fps", "Fake FPS", ModuleCategory.OTHER);
/*  63 */     this.minFps.setFullWidth(true);
/*  64 */     this.maxFps.setFullWidth(true);
/*  65 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.minFps, (Setting)this.maxFps });
/*     */   }
/*     */   
/*     */   public static FakeFps getInstance() {
/*  69 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  74 */     return "Replaces the visible FPS counter with a randomised value drawn from your configured range";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  79 */     return "fake_fps.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  84 */     return 21.0F;
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
/*     */   public int getFakeFps() {
/*  99 */     long now = System.currentTimeMillis();
/* 100 */     if (this.lastUpdateMs == 0L || now - this.lastUpdateMs >= 1000L) {
/* 101 */       int lo = Math.round(this.minFps.getValue());
/* 102 */       int hi = Math.round(this.maxFps.getValue());
/*     */ 
/*     */       
/* 105 */       if (lo > hi) {
/* 106 */         int tmp = lo;
/* 107 */         lo = hi;
/* 108 */         hi = tmp;
/*     */       } 
/* 110 */       int span = hi - lo + 1;
/* 111 */       if (span <= 0) span = 1; 
/* 112 */       this.lastFps = lo + this.random.nextInt(span);
/* 113 */       this.lastUpdateMs = now;
/*     */     } 
/* 115 */     return this.lastFps;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\FakeFps.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */