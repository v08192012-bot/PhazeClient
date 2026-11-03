/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*    */ import net.minecraft.class_310;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ public final class AutoSprint extends Module {
/* 10 */   private static final AutoSprint INSTANCE = new AutoSprint();
/*    */   
/* 12 */   public final SectionSetting generalSection = new SectionSetting("General");
/*    */   
/* 14 */   public final BooleanSetting showInSprintHud = (new BooleanSetting("Show in sprint hud", "Show AutoSprint in Sprint HUD instead of Vanilla")).setValue(true);
/*    */   
/*    */   public static AutoSprint getInstance() {
/* 17 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private AutoSprint() {
/* 21 */     super("auto_sprint", "AutoSprint", ModuleCategory.UTILITIES, true, false);
/* 22 */     this.showInSprintHud.setFullWidth(true);
/* 23 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.showInSprintHud });
/*    */     
/* 25 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*    */   }
/*    */   
/*    */   private void tick(class_310 client) {
/* 29 */     if (client == null || client.field_1724 == null || client.field_1690 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 33 */     if (!isEnabled()) {
/*    */ 
/*    */       
/* 36 */       client.field_1690.field_1867.method_23481(false);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 41 */     client.field_1690.field_1867.method_23481(true);
/*    */ 
/*    */ 
/*    */     
/* 45 */     if (client.field_1724.field_6250 > 0.0F && 
/* 46 */       !client.field_1724.method_5624() && 
/* 47 */       !client.field_1724.method_5715() && 
/* 48 */       !client.field_1724.method_6115() && client.field_1724
/* 49 */       .method_7344().method_7586() > 6) {
/* 50 */       client.field_1724.method_5728(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 56 */     return "Automatically sprints while holding forward";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 61 */     return "auto_sprint.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 66 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoSprint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */