/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public class WaveCapes extends Module {
/*  9 */   private static final WaveCapes INSTANCE = new WaveCapes();
/*    */   
/* 11 */   public final ValueSetting gravity = (new ValueSetting("Gravity", "Simulation gravity pull"))
/* 12 */     .range(5.0F, 50.0F).step(1.0F).setValue(25.0F);
/* 13 */   public final ValueSetting heightMultiplier = (new ValueSetting("Height Drag", "Vertical movement drag multiplier"))
/* 14 */     .range(1.0F, 10.0F).step(0.5F).setValue(6.0F);
/* 15 */   public final ValueSetting strafeMultiplier = (new ValueSetting("Strafe Drag", "Horizontal strafing swing multiplier"))
/* 16 */     .range(0.5F, 5.0F).step(0.5F).setValue(2.0F);
/* 17 */   public final BooleanSetting windEffect = (new BooleanSetting("Wind Ripple", "Travelling wave ripples along the cloth"))
/* 18 */     .setValue(false);
/*    */   
/*    */   private WaveCapes() {
/* 21 */     super("wave_capes", "Wave Capes", ModuleCategory.OTHER);
/* 22 */     setup(new Setting[] { (Setting)this.gravity, (Setting)this.heightMultiplier, (Setting)this.strafeMultiplier, (Setting)this.windEffect });
/*    */   }
/*    */   
/*    */   public static WaveCapes getInstance() {
/* 26 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 31 */     return "1:1 Smooth continuous 16-segment cloth physics for capes (WaveyCapes model)";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 36 */     return "elytrautility.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\WaveCapes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */