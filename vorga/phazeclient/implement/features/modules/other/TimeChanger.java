/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*    */ import net.minecraft.class_1928;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_3218;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public final class TimeChanger extends Module {
/* 12 */   private static final TimeChanger INSTANCE = new TimeChanger();
/*    */   private boolean originalDaylightCycle = true;
/* 14 */   private long originalTime = -1L;
/* 15 */   private int cooldownTicks = 0;
/*    */   
/*    */   public static TimeChanger getInstance() {
/* 18 */     return INSTANCE;
/*    */   }
/*    */   
/* 21 */   public final ValueSetting timeValue = new ValueSetting("Time", "Set world time (0-24000 ticks)");
/*    */   
/*    */   private TimeChanger() {
/* 24 */     super("time_changer", "Time Changer", ModuleCategory.OTHER, true, false);
/* 25 */     this.timeValue.range(0, 24000).setValue(12000.0F);
/* 26 */     setup(new Setting[] { (Setting)this.timeValue });
/*    */     
/* 28 */     ClientTickEvents.END_CLIENT_TICK.register(client -> {
/*    */           if (this.cooldownTicks > 0) {
/*    */             this.cooldownTicks--;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public boolean isTimeOverrideActive() {
/* 36 */     return (isEnabled() && this.cooldownTicks == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 41 */     return "Change the time of day in the world (client-side, works on any server)";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 46 */     return "time_changer.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 51 */     return 21.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showIconInSettings() {
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate() {
/* 61 */     this.cooldownTicks = 0;
/*    */     
/* 63 */     class_310 client = class_310.method_1551();
/* 64 */     if (client.method_1576() != null && client.field_1687 != null) {
/* 65 */       class_3218 world = client.method_1576().method_3847(client.field_1687.method_27983());
/* 66 */       if (world != null) {
/* 67 */         this.originalTime = world.method_8532();
/* 68 */         this.originalDaylightCycle = ((class_1928.class_4310)world.method_64395().method_20746(class_1928.field_19396)).method_20753();
/* 69 */         ((class_1928.class_4310)world.method_64395().method_20746(class_1928.field_19396)).method_20758(false, (MinecraftServer)client.method_1576());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 76 */     this.cooldownTicks = 5;
/*    */     
/* 78 */     class_310 client = class_310.method_1551();
/* 79 */     if (client.method_1576() != null && client.field_1687 != null) {
/* 80 */       class_3218 world = client.method_1576().method_3847(client.field_1687.method_27983());
/* 81 */       if (world != null) {
/* 82 */         if (this.originalTime != -1L) {
/* 83 */           world.method_29199(this.originalTime);
/* 84 */           this.originalTime = -1L;
/*    */         } 
/* 86 */         ((class_1928.class_4310)world.method_64395().method_20746(class_1928.field_19396)).method_20758(this.originalDaylightCycle, (MinecraftServer)client.method_1576());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\TimeChanger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */