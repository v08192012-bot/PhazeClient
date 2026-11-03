/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ 
/*    */ public final class MovementSpeedHud extends RectHudModule {
/*  8 */   private static final MovementSpeedHud INSTANCE = new MovementSpeedHud();
/*    */   
/*    */   public static MovementSpeedHud getInstance() {
/* 11 */     return INSTANCE;
/*    */   }
/*    */   
/* 14 */   public final BooleanSetting onlyUseGroundSpeed = (new BooleanSetting("Only Use Ground Speed", "Only count horizontal speed")).setValue(false);
/* 15 */   public final SelectSetting roundTo = (new SelectSetting("Round To", "Decimal places for speed display"))
/* 16 */     .value(new String[] { "Nearest", "1 Decimal", "2 Decimals", "3 Decimals"
/* 17 */       }).selected("2 Decimals");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public final BooleanSetting reverseOrder = (new BooleanSetting("Reverse Order", "Add \"Speed:\" prefix instead of just \"X m/s\"")).setValue(false);
/*    */   
/*    */   private MovementSpeedHud() {
/* 26 */     super("movement_speed_hud", "Movement Speed", 22.0F, 510.0F, 1.0F);
/* 27 */     this.onlyUseGroundSpeed.setFullWidth(true);
/* 28 */     this.roundTo.setFullWidth(true);
/* 29 */     this.reverseOrder.setFullWidth(true);
/* 30 */     setup(new Setting[] { (Setting)this.onlyUseGroundSpeed, (Setting)this.roundTo, (Setting)this.reverseOrder });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 35 */     return "Shows current movement speed";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 40 */     return "movement_speed.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 45 */     return 21.0F;
/*    */   }
/*    */   
/*    */   public String getSpeedText(double speed) {
/* 49 */     double displaySpeed = this.onlyUseGroundSpeed.isValue() ? Math.sqrt(speed * speed - getVerticalSpeed() * getVerticalSpeed()) : speed;
/* 50 */     String rounding = this.roundTo.getSelected();
/*    */     
/* 52 */     if (rounding.equals("Nearest"))
/* 53 */       return String.format("%.0f", new Object[] { Double.valueOf(displaySpeed) }); 
/* 54 */     if (rounding.equals("1 Decimal"))
/* 55 */       return String.format("%.1f", new Object[] { Double.valueOf(displaySpeed) }); 
/* 56 */     if (rounding.equals("2 Decimals")) {
/* 57 */       return String.format("%.2f", new Object[] { Double.valueOf(displaySpeed) });
/*    */     }
/* 59 */     return String.format("%.3f", new Object[] { Double.valueOf(displaySpeed) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private double getVerticalSpeed() {
/* 65 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\MovementSpeedHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */