/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ 
/*     */ public final class SettingLabelState
/*     */ {
/*     */   public static final int WIDTH_TRANSITION_MS = 200;
/*     */   public static final long WIDTH_TRANSITION_NANOS = 200000000L;
/*     */   public static final long TRAVEL_NANOS = 900000000L;
/*     */   public static final long HOLD_NANOS = 650000000L;
/*     */   public static final long RETURN_NANOS = 900000000L;
/*     */   public static final float FADE_WIDTH = 12.0F;
/*  12 */   private String text = "";
/*     */   
/*     */   private float width;
/*     */   
/*     */   private float height;
/*     */   
/*     */   private float overflow;
/*     */   private float widthFrom;
/*     */   private float widthTarget;
/*     */   
/*     */   public void layout(String text, float textWidth, float width, float height) {
/*  23 */     layout(text, textWidth, width, height, System.nanoTime());
/*     */   }
/*     */   private long widthStarted; private boolean widthInitialized; private long started; private boolean running; private boolean hovered; private float startFraction;
/*     */   public void layout(String text, float textWidth, float width, float height, long now) {
/*  27 */     layout(text, textWidth, width, height, now, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void layout(String text, float textWidth, float width, float height, long now, float animatedInset) {
/*  33 */     float target = Math.max(0.0F, width + animatedInset);
/*  34 */     if (!this.widthInitialized) {
/*     */       
/*  36 */       this.widthFrom = this.widthTarget = target;
/*  37 */       this.widthStarted = now;
/*  38 */       this.widthInitialized = true;
/*     */     } 
/*  40 */     float currentWidth = sampleWidth(now);
/*  41 */     if (Float.compare(target, this.widthTarget) != 0) {
/*     */ 
/*     */       
/*  44 */       this.widthFrom = currentWidth;
/*  45 */       this.widthTarget = target;
/*  46 */       this.widthStarted = now;
/*     */     } 
/*     */ 
/*     */     
/*  50 */     if (!this.text.equals(text)) {
/*  51 */       this.running = false;
/*     */     }
/*  53 */     this.text = text;
/*  54 */     this.width = Math.max(0.0F, currentWidth - animatedInset);
/*  55 */     this.height = Math.max(0.0F, height);
/*  56 */     this.overflow = Math.max(0.0F, textWidth - this.width);
/*  57 */     if (!isOverflowing()) this.running = false; 
/*     */   }
/*     */   
/*     */   private float sampleWidth(long now) {
/*  61 */     float t = clamp((float)Math.max(0L, now - this.widthStarted) / 2.0E8F);
/*  62 */     float progress = 1.0F - (1.0F - t) * (1.0F - t);
/*  63 */     return this.widthFrom + (this.widthTarget - this.widthFrom) * progress;
/*     */   }
/*     */   
/*     */   public float getWidth() {
/*  67 */     return this.width;
/*     */   }
/*     */   
/*     */   public boolean isOverflowing() {
/*  71 */     return (this.overflow > 0.0F && this.width > 0.0F && this.height > 0.0F);
/*     */   }
/*     */   
/*     */   public boolean isHovered() {
/*  75 */     return this.hovered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateHover(boolean hovered, long now) {
/*  85 */     float currentOffset = offset(now);
/*  86 */     boolean entered = (hovered && !this.hovered);
/*  87 */     this.hovered = hovered;
/*  88 */     if (entered && isOverflowing()) {
/*  89 */       this.startFraction = currentOffset / this.overflow;
/*  90 */       this.started = now;
/*  91 */       this.running = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float offset(long now) {
/*  96 */     if (!this.running || !isOverflowing()) return 0.0F; 
/*  97 */     long elapsed = Math.max(0L, now - this.started);
/*  98 */     if (elapsed < 900000000L) {
/*  99 */       return this.overflow * (this.startFraction + (1.0F - this.startFraction) * ease((float)elapsed / 9.0E8F));
/*     */     }
/* 101 */     elapsed -= 900000000L;
/* 102 */     if (elapsed < 650000000L) return this.overflow; 
/* 103 */     elapsed -= 650000000L;
/* 104 */     if (elapsed < 900000000L) return this.overflow * (1.0F - ease((float)elapsed / 9.0E8F)); 
/* 105 */     this.running = false;
/* 106 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public float fadeWidth() {
/* 110 */     return Math.min(12.0F, this.width * 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float alphaAt(float localX, float offset) {
/* 115 */     if (!isOverflowing()) return 1.0F; 
/* 116 */     if (localX < 0.0F || localX > this.width) return 0.0F; 
/* 117 */     float fade = fadeWidth();
/* 118 */     float left = clamp(offset / fade);
/* 119 */     float right = clamp((this.overflow - offset) / fade);
/* 120 */     return clamp((1.0F - left * (1.0F - ease(localX / fade))) * (1.0F - right * (1.0F - 
/* 121 */         ease((this.width - localX) / fade))));
/*     */   }
/*     */   
/*     */   public float getOverflow() {
/* 125 */     return this.overflow;
/*     */   }
/*     */   
/*     */   public static float snapClipEdge(float localX, float scale, float translation) {
/* 129 */     return (scale > 0.0F) ? ((Math.round(localX * scale + translation) - translation) / scale) : localX;
/*     */   }
/*     */   
/*     */   private static float ease(float value) {
/* 133 */     float t = clamp(value);
/* 134 */     return t * t * (3.0F - 2.0F * t);
/*     */   }
/*     */   
/*     */   private static float clamp(float value) {
/* 138 */     return Math.max(0.0F, Math.min(1.0F, value));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\SettingLabelState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */