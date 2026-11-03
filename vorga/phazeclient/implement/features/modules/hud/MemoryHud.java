/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ 
/*    */ public final class MemoryHud extends RectHudModule {
/*  8 */   private static final MemoryHud INSTANCE = new MemoryHud();
/*    */   
/*    */   public static MemoryHud getInstance() {
/* 11 */     return INSTANCE;
/*    */   }
/*    */   
/* 14 */   public final SelectSetting displayMode = (new SelectSetting("Display Mode", "Memory display format"))
/* 15 */     .value(new String[] { "Percentage", "Megabytes", "Gigabytes" });
/* 16 */   public final BooleanSetting colorBasedOnUsage = (new BooleanSetting("Color Based On Usage", "Change color based on memory usage")).setValue(true);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Show value before label, e.g. \"50% Mem\" instead of \"Mem: 50%\"")).setValue(false);
/*    */   
/*    */   private MemoryHud() {
/* 24 */     super("memory_hud", "Memory HUD", 100.0F, 50.0F, 1.0F);
/* 25 */     this.reverseOrder.setFullWidth(true);
/* 26 */     setup(new Setting[] { (Setting)this.displayMode, (Setting)this.colorBasedOnUsage, (Setting)this.reverseOrder });
/*    */   }
/*    */   public String getMemoryText() {
/*    */     String value;
/* 30 */     Runtime runtime = Runtime.getRuntime();
/* 31 */     long totalMemory = runtime.totalMemory();
/* 32 */     long freeMemory = runtime.freeMemory();
/* 33 */     long usedMemory = totalMemory - freeMemory;
/* 34 */     long maxMemory = runtime.maxMemory();
/*    */     
/* 36 */     String mode = this.displayMode.getSelected();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     if (mode.equals("Percentage")) {
/* 43 */       double percentage = usedMemory * 100.0D / maxMemory;
/* 44 */       value = String.format("%.1f%%", new Object[] { Double.valueOf(percentage) });
/* 45 */     } else if (mode.equals("Megabytes")) {
/* 46 */       value = String.format("%d/%d MB", new Object[] { Long.valueOf(usedMemory / 1048576L), Long.valueOf(maxMemory / 1048576L) });
/*    */     } else {
/* 48 */       value = String.format("%.2f/%.2f GB", new Object[] { Double.valueOf(usedMemory / 1.073741824E9D), Double.valueOf(maxMemory / 1.073741824E9D) });
/*    */     } 
/* 50 */     return this.reverseOrder.isValue() ? (value + " Mem") : ("Mem: " + value);
/*    */   }
/*    */   
/*    */   public int getMemoryColor() {
/* 54 */     if (!this.colorBasedOnUsage.isValue()) {
/* 55 */       return -1;
/*    */     }
/*    */     
/* 58 */     Runtime runtime = Runtime.getRuntime();
/* 59 */     long totalMemory = runtime.totalMemory();
/* 60 */     long freeMemory = runtime.freeMemory();
/* 61 */     long usedMemory = totalMemory - freeMemory;
/* 62 */     long maxMemory = runtime.maxMemory();
/* 63 */     double percentage = usedMemory * 100.0D / maxMemory;
/*    */     
/* 65 */     if (percentage < 50.0D)
/* 66 */       return -16711936; 
/* 67 */     if (percentage < 75.0D) {
/* 68 */       return -256;
/*    */     }
/* 70 */     return -65536;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void activate() {
/* 76 */     super.activate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 81 */     super.deactivate();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 86 */     return "Shows RAM usage in HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 91 */     return "memory_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 96 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\MemoryHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */