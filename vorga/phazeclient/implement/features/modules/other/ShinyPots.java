/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_9334;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public class ShinyPots
/*    */   extends Module {
/* 14 */   private static final ShinyPots INSTANCE = new ShinyPots();
/*    */   
/* 16 */   public final BooleanSetting allPotions = (new BooleanSetting("All Potions", "Enchanted glint on all potion bottles")).setValue(true);
/* 17 */   public final BooleanSetting xpBottles = (new BooleanSetting("XP Bottles", "Enchanted glint on bottles o' enchanting")).setValue(true);
/* 18 */   public final BooleanSetting ominousBottles = (new BooleanSetting("Ominous Bottles", "Enchanted glint on ominous bottles")).setValue(true);
/*    */   
/*    */   private ShinyPots() {
/* 21 */     super("shiny_pots", "Shiny Pots", ModuleCategory.OTHER);
/* 22 */     setup(new Setting[] { (Setting)this.allPotions, (Setting)this.xpBottles, (Setting)this.ominousBottles });
/*    */   }
/*    */   
/*    */   public static ShinyPots getInstance() {
/* 26 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 31 */     return "Adds animated enchanted shine overlay to potion bottles and throwables";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 36 */     return "potion_hud.png";
/*    */   }
/*    */   
/*    */   public static boolean isPotion(class_1799 stack) {
/* 40 */     if (stack == null || stack.method_7960()) return false; 
/* 41 */     ShinyPots instance = getInstance();
/* 42 */     if (instance == null || !instance.isEnabled()) return false;
/*    */     
/* 44 */     class_1792 item = stack.method_7909();
/* 45 */     if (instance.allPotions.isValue()) {
/* 46 */       if (item instanceof net.minecraft.class_1812 || item instanceof net.minecraft.class_4537) return true; 
/* 47 */       if (item == class_1802.field_8574 || item == class_1802.field_8436 || item == class_1802.field_8150) return true; 
/* 48 */       if (stack.method_57826(class_9334.field_49651)) return true;
/*    */     
/*    */     } 
/* 51 */     if (instance.xpBottles.isValue() && item == class_1802.field_8287) {
/* 52 */       return true;
/*    */     }
/*    */     
/* 55 */     if (instance.ominousBottles.isValue() && (
/* 56 */       item == class_1802.field_50140 || stack.method_57826(class_9334.field_50238))) {
/* 57 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ShinyPots.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */