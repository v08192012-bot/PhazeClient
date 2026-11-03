/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public class ThreeDSkins extends Module {
/* 10 */   private static final ThreeDSkins INSTANCE = new ThreeDSkins();
/*    */   
/* 12 */   public final SectionSetting voxelsSection = new SectionSetting("Voxels settings");
/* 13 */   public final SectionSetting visibleSection = new SectionSetting("Visible");
/*    */   
/* 15 */   public final ValueSetting voxelThickness = (new ValueSetting("Voxel Depth", "Depth of extruded outer skin voxels"))
/* 16 */     .range(0.2F, 1.2F).step(0.05F).setValue(0.65F)
/* 17 */     .onChange(v -> ThreeDSkinsVoxelizer.clearCache());
/* 18 */   public final ValueSetting maxDistance = (new ValueSetting("Max Distance", "Maximum distance to render 3D voxels (blocks)"))
/* 19 */     .range(12, 64).step(4.0F).setValue(28.0F);
/* 20 */   public final BooleanSetting showOthers = (new BooleanSetting("Show Others", "Apply 3D skin layer to other players"))
/* 21 */     .setValue(true);
/* 22 */   public final BooleanSetting hat3D = (new BooleanSetting("3D Hat", "Extrude hat and hair layer")).setValue(true);
/* 23 */   public final BooleanSetting jacket3D = (new BooleanSetting("3D Jacket", "Extrude jacket and torso layer")).setValue(true);
/* 24 */   public final BooleanSetting sleeves3D = (new BooleanSetting("3D Sleeves", "Extrude sleeve layers")).setValue(true);
/* 25 */   public final BooleanSetting pants3D = (new BooleanSetting("3D Pants", "Extrude pants layers")).setValue(true);
/*    */   
/*    */   private ThreeDSkins() {
/* 28 */     super("three_d_skins", "3D Skins", ModuleCategory.OTHER);
/* 29 */     this.voxelThickness.setFullWidth(true);
/* 30 */     this.maxDistance.setFullWidth(true);
/* 31 */     this.showOthers.setFullWidth(true);
/* 32 */     setup(new Setting[] { (Setting)this.voxelsSection, (Setting)this.voxelThickness, (Setting)this.maxDistance, (Setting)this.visibleSection, (Setting)this.showOthers, (Setting)this.hat3D, (Setting)this.jacket3D, (Setting)this.sleeves3D, (Setting)this.pants3D });
/*    */   }
/*    */ 
/*    */   
/*    */   public static ThreeDSkins getInstance() {
/* 37 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 42 */     return "Clean-room 3D skin layer voxelization with optimized neighbor face culling";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 47 */     return "player_model_hud.png";
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ThreeDSkins.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */