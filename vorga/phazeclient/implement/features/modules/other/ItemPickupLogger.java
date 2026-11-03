/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1814;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_5250;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ public final class ItemPickupLogger extends Module {
/* 15 */   private static final ItemPickupLogger INSTANCE = new ItemPickupLogger();
/*    */   
/* 17 */   public final SectionSetting filterSection = new SectionSetting("Filter");
/* 18 */   public final BooleanSetting onlyRare = (new BooleanSetting("Only Rare", "Log only items that are not COMMON rarity (uncommon/rare/epic)"))
/*    */ 
/*    */     
/* 21 */     .setValue(false);
/*    */   
/*    */   private ItemPickupLogger() {
/* 24 */     super("item_pickup_logger", "Item Pickup Logger", ModuleCategory.UTILITIES);
/* 25 */     this.onlyRare.setFullWidth(true);
/* 26 */     setup(new Setting[] { (Setting)this.filterSection, (Setting)this.onlyRare });
/*    */   }
/*    */   
/*    */   public static ItemPickupLogger getInstance() {
/* 30 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 35 */     return "Logs every picked-up item to the chat";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 40 */     return "item_pickup_logger.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 45 */     return 21.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPickup(class_1799 stack, int amount) {
/* 54 */     if (!isEnabled() || stack == null || stack.method_7960()) {
/*    */       return;
/*    */     }
/*    */     
/* 58 */     if (this.onlyRare.isValue()) {
/* 59 */       class_1814 rarity = stack.method_7932();
/* 60 */       if (rarity == null || rarity == class_1814.field_8906) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 65 */     class_310 mc = class_310.method_1551();
/* 66 */     if (mc == null || mc.field_1705 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 70 */     class_5250 itemName = stack.method_7964().method_27661();
/*    */ 
/*    */ 
/*    */     
/* 74 */     class_5250 class_52501 = class_2561.method_43470("[Pickup] ").method_27692(class_124.field_1065).method_10852((class_2561)itemName).method_10852((class_2561)class_2561.method_43470(" x" + amount).method_27692(class_124.field_1080));
/* 75 */     mc.field_1705.method_1743().method_1812((class_2561)class_52501);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ItemPickupLogger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */