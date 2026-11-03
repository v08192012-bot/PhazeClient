/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ 
/*    */ public class PackOrganizer extends Module {
/*  8 */   private static final PackOrganizer INSTANCE = new PackOrganizer();
/*    */   
/* 10 */   public final BooleanSetting searchBar = (new BooleanSetting("Search Bar", "Show quick search bar on resource pack screen")).setValue(true);
/* 11 */   public final BooleanSetting packPreviews = (new BooleanSetting("Pack Previews", "Show real pack textures and resource details when hovering a pack")).setValue(true);
/*    */   
/*    */   private PackOrganizer() {
/* 14 */     super("pack_organizer", "Pack Organizer", ModuleCategory.OTHER);
/* 15 */     setup(new Setting[] { (Setting)this.searchBar, (Setting)this.packPreviews });
/*    */   }
/*    */   
/*    */   public static PackOrganizer getInstance() {
/* 19 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 24 */     return "Adds a quick search bar and a real texture preview to the resource pack screen";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabledByDefault() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 34 */     return "shulker_preview.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackOrganizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */