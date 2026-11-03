/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ 
/*    */ public final class LockSlot
/*    */   extends Module
/*    */ {
/* 13 */   private static final LockSlot INSTANCE = new LockSlot();
/*    */   
/* 15 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 16 */   public final MultiSelectSetting lockedSlots = (new MultiSelectSetting("Locked Slots", "Pick which hotbar or offhand slots should be protected from dropping"))
/*    */ 
/*    */     
/* 19 */     .value(new String[] {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         "Slot 1", "Slot 2", "Slot 3", "Slot 4", "Slot 5", "Slot 6", "Slot 7", "Slot 8", "Slot 9", "Offhand"
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 30 */       }).selected(new String[0]);
/*    */   
/*    */   private LockSlot() {
/* 33 */     super("lockslot", "Lock Slot", ModuleCategory.UTILITIES);
/* 34 */     this.lockedSlots.setFullWidth(true);
/* 35 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.lockedSlots });
/*    */   }
/*    */   
/*    */   public static LockSlot getInstance() {
/* 39 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 44 */     return "Prevent dropping items from chosen hotbar/offhand slots";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 49 */     return "lockslot.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 54 */     return 21.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSlotLocked(int hotbarIndex) {
/* 62 */     if (!isEnabled()) {
/* 63 */       return false;
/*    */     }
/* 65 */     if (hotbarIndex == 40) {
/* 66 */       return this.lockedSlots.isSelected("Offhand");
/*    */     }
/* 68 */     if (hotbarIndex < 0 || hotbarIndex > 8) {
/* 69 */       return false;
/*    */     }
/* 71 */     return this.lockedSlots.isSelected("Slot " + hotbarIndex + 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isHotbarSlotLocked(int hotbarIndex) {
/* 78 */     return isSlotLocked(hotbarIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOffhandLocked() {
/* 85 */     return isSlotLocked(40);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\LockSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */