/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
/*    */ import net.minecraft.class_1269;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public class KillStreakHud extends RectHudModule {
/* 14 */   private static final KillStreakHud INSTANCE = new KillStreakHud();
/*    */   
/*    */   private static boolean registered = false;
/* 17 */   public final BooleanSetting playersOnly = (new BooleanSetting("Players Only", "Count only player eliminations")).setValue(true);
/* 18 */   public final ValueSetting posX = (new ValueSetting("X Offset", "X screen coordinate")).range(0, 1920).step(5.0F).setValue(20.0F);
/* 19 */   public final ValueSetting posY = (new ValueSetting("Y Offset", "Y screen coordinate")).range(0, 1080).step(5.0F).setValue(120.0F);
/*    */   
/* 21 */   private int streak = 0;
/* 22 */   private class_1309 pendingTarget = null;
/* 23 */   private long lastHitTime = 0L;
/*    */   
/*    */   private KillStreakHud() {
/* 26 */     super("killstreak_hud", "Killstreak", 20.0F, 120.0F, 1.0F);
/* 27 */     setup(new Setting[] { (Setting)this.playersOnly, (Setting)this.posX, (Setting)this.posY });
/* 28 */     registerEvents();
/*    */   }
/*    */   
/*    */   public static KillStreakHud getInstance() {
/* 32 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 37 */     return "HUD counter tracking current and highest consecutive kill streaks";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 42 */     return "combo_counter_hud.png";
/*    */   }
/*    */   
/*    */   private synchronized void registerEvents() {
/* 46 */     if (registered)
/* 47 */       return;  registered = true;
/*    */     
/* 49 */     AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
/*    */           if (world.method_8608() && isEnabled() && entity instanceof class_1309) {
/*    */             class_1309 living = (class_1309)entity;
/*    */             this.pendingTarget = living;
/*    */             this.lastHitTime = System.currentTimeMillis();
/*    */           } 
/*    */           return (class_1269)class_1269.field_5811;
/*    */         });
/* 57 */     ClientTickEvents.END_CLIENT_TICK.register(client -> {
/*    */           if (!isEnabled() || client.field_1724 == null) {
/*    */             return;
/*    */           }
/*    */           if (client.field_1724.method_29504() || client.field_1724.method_6032() <= 0.0F) {
/*    */             this.streak = 0;
/*    */             this.pendingTarget = null;
/*    */             return;
/*    */           } 
/*    */           if (this.pendingTarget != null) {
/*    */             if (System.currentTimeMillis() - this.lastHitTime > 2500L) {
/*    */               this.pendingTarget = null;
/*    */             } else if (this.pendingTarget.method_29504() || this.pendingTarget.method_6032() <= 0.0F) {
/*    */               if (!this.playersOnly.isValue() || this.pendingTarget instanceof class_1657) {
/*    */                 this.streak++;
/*    */               }
/*    */               this.pendingTarget = null;
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void render(class_332 context) {
/* 80 */     if (!isEnabled())
/* 81 */       return;  class_310 client = class_310.method_1551();
/* 82 */     if (client.field_1724 == null || (client.field_1690 != null && client.field_1690.field_1842))
/*    */       return; 
/* 84 */     String textStr = "Streak: " + this.streak;
/* 85 */     int textWidth = client.field_1772.method_1727(textStr);
/* 86 */     int pad = 4;
/* 87 */     int width = textWidth + pad * 2;
/* 88 */     int height = 14;
/*    */     
/* 90 */     int x = (int)this.posX.getValue();
/* 91 */     int y = (int)this.posY.getValue();
/*    */     
/* 93 */     if (this.background.isValue()) {
/* 94 */       int bg = getResolvedBackgroundColor(client);
/* 95 */       context.method_25294(x, y, x + width, y + height, bg);
/*    */     } 
/*    */     
/* 98 */     context.method_51433(client.field_1772, textStr, x + pad, y + 3, -1, this.textShadow.isValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\KillStreakHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */