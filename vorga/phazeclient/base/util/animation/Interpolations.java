/*     */ package vorga.phazeclient.base.util.animation;
/*     */ 
/*     */ public class Interpolations
/*     */ {
/*   5 */   public static final Interpolation LINEAR = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*   8 */         return progress;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  13 */         return "Linear";
/*     */       }
/*     */     };
/*     */   
/*  17 */   public static final Interpolation EASE_IN = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*  20 */         return progress * progress;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  25 */         return "Smooth";
/*     */       }
/*     */     };
/*     */   
/*  29 */   public static final Interpolation EASE_OUT = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*  32 */         return 1.0D - Math.pow(1.0D - progress, 2.0D);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  37 */         return "Fast";
/*     */       }
/*     */     };
/*     */   
/*  41 */   public static final Interpolation EASE_IN_OUT = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*  44 */         if (progress < 0.5D) {
/*  45 */           return 2.0D * progress * progress;
/*     */         }
/*  47 */         return 1.0D - Math.pow(-2.0D * progress + 2.0D, 2.0D) / 2.0D;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String getName() {
/*  53 */         return "Balanced";
/*     */       }
/*     */     };
/*     */   
/*  57 */   public static final Interpolation EASE_OUT_BACK = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*  60 */         return 1.0D + 2.70158D * Math.pow(progress - 1.0D, 3.0D) + 1.70158D * Math.pow(progress - 1.0D, 2.0D);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  65 */         return "Back";
/*     */       }
/*     */     };
/*     */   
/*  69 */   public static final Interpolation EASE_IN_BACK = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*  72 */         double c1 = 1.70158D;
/*  73 */         double c3 = c1 + 1.0D;
/*  74 */         return c3 * progress * progress * progress - c1 * progress * progress;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  79 */         return "Overshoot";
/*     */       }
/*     */     };
/*     */   
/*  83 */   public static final Interpolation ELASTIC = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/*  86 */         if (progress == 0.0D) return 0.0D; 
/*  87 */         if (progress == 1.0D) return 1.0D;
/*     */         
/*  89 */         double c4 = 2.0943951023931953D;
/*  90 */         return Math.pow(2.0D, -10.0D * progress) * Math.sin((progress * 10.0D - 0.75D) * c4) + 1.0D;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/*  95 */         return "Elastic";
/*     */       }
/*     */     };
/*     */   
/*  99 */   public static final Interpolation BOUNCE = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/* 102 */         double n1 = 7.5625D;
/* 103 */         double d1 = 2.75D;
/*     */         
/* 105 */         if (progress < 1.0D / d1)
/* 106 */           return n1 * progress * progress; 
/* 107 */         if (progress < 2.0D / d1)
/* 108 */           return n1 * (progress -= 1.5D / d1) * progress + 0.75D; 
/* 109 */         if (progress < 2.5D / d1) {
/* 110 */           return n1 * (progress -= 2.25D / d1) * progress + 0.9375D;
/*     */         }
/* 112 */         return n1 * (progress -= 2.625D / d1) * progress + 0.984375D;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String getName() {
/* 118 */         return "Bounce";
/*     */       }
/*     */     };
/*     */   
/* 122 */   public static final Interpolation IOS_EASE_OUT = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/* 125 */         return 1.0D - Math.pow(1.0D - progress, 3.0D);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/* 130 */         return "Ease Out";
/*     */       }
/*     */     };
/*     */   
/* 134 */   public static final Interpolation IOS_SPRING = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/* 137 */         double damping = 0.7D;
/* 138 */         double frequency = 1.5D;
/*     */         
/* 140 */         double decay = Math.exp(-damping * frequency * progress);
/* 141 */         double oscillation = Math.cos(frequency * progress * Math.PI * 2.0D);
/*     */         
/* 143 */         return 1.0D - decay * oscillation * 0.1D + decay * (1.0D - progress);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/* 148 */         return "Spring";
/*     */       }
/*     */     };
/*     */   
/* 152 */   public static final Interpolation IOS_DECELERATE = new Interpolation()
/*     */     {
/*     */       public double interpolate(double progress) {
/* 155 */         return progress * (2.0D - progress);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getName() {
/* 160 */         return "Decelerate";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_NAME = "Default";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Interpolation getByName(String name) {
/* 178 */     switch (name) { case "Default": case "Linear": case "Smooth": case "Fast": case "Balanced": case "Back": case "Overshoot": case "Elastic": case "Bounce": case "Ease Out": case "Spring": case "Decelerate":  }  return 
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
/* 191 */       LINEAR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getAllNames() {
/* 199 */     return new String[] { "Default", "Linear", "Smooth", "Fast", "Balanced", "Back", "Overshoot", "Elastic", "Bounce", "Ease Out", "Spring", "Decelerate" };
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\animation\Interpolations.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */