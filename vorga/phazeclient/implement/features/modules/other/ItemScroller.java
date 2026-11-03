/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ import vorga.phazeclient.base.util.ServerUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ItemScroller
/*    */   extends Module
/*    */ {
/* 26 */   private static final ItemScroller INSTANCE = new ItemScroller();
/*    */   
/* 28 */   public final SectionSetting generalSection = new SectionSetting("General");
/*    */   
/* 30 */   public final ValueSetting delayMs = (new ValueSetting("Delay (ms)", "Minimum time between two consecutive shift-click transfers while dragging. Lower = snappier, higher = gentler on the server."))
/*    */ 
/*    */     
/* 33 */     .range(5, 50).step(1.0F).setValue(20.0F);
/*    */   
/*    */   private ItemScroller() {
/* 36 */     super("item_scroller", "Item Scroller", ModuleCategory.UTILITIES);
/* 37 */     this.delayMs.setFullWidth(true);
/* 38 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.delayMs });
/*    */   }
/*    */   
/*    */   public static ItemScroller getInstance() {
/* 42 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getDelayMs() {
/* 47 */     float v = this.delayMs.getValue();
/* 48 */     if (v < 5.0F) v = 5.0F; 
/* 49 */     if (v > 50.0F) v = 50.0F; 
/* 50 */     return (long)v;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isServerAllowed() {
/* 64 */     return ServerUtil.isItemScrollerSupported();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 69 */     return "Hold Shift + drag the mouse over slots to shift-click them all without extra clicks";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 74 */     return "item_scroller.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 79 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ItemScroller.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */