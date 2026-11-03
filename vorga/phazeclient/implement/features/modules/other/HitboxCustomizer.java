/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ 
/*    */ public final class HitboxCustomizer
/*    */   extends Module
/*    */ {
/* 16 */   private static final HitboxCustomizer INSTANCE = new HitboxCustomizer();
/*    */   
/* 18 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 19 */   public final ColorSetting hitboxColor = (new ColorSetting("Hitbox Color", "Color of all entity hitboxes"))
/* 20 */     .setColor(-1)
/* 21 */     .popupRow();
/* 22 */   public final BooleanSetting fill = (new BooleanSetting("Fill", "Fill the hitbox faces in addition to the outline"))
/* 23 */     .setValue(false);
/* 24 */   public final ValueSetting fillOpacity = (new ValueSetting("Opacity", "Opacity of the hitbox fill"))
/* 25 */     .range(0.0F, 1.0F)
/* 26 */     .step(0.01F)
/* 27 */     .setValue(0.3F);
/* 28 */   public final ColorSetting reachColor = (new ColorSetting("Reach Color", "Color used when looking at an entity in attack range"))
/* 29 */     .setColor(-53200)
/* 30 */     .popupRow();
/* 31 */   public final ValueSetting outlineThickness = (new ValueSetting("Outline Thickness", "Thickness of the hitbox outline"))
/* 32 */     .range(1.0F, 6.0F)
/* 33 */     .step(0.5F)
/* 34 */     .setValue(1.0F);
/* 35 */   public final BooleanSetting showLookLine = (new BooleanSetting("Show Eye Line", "Draws a line in the entity look direction"))
/* 36 */     .setValue(true);
/* 37 */   public final BooleanSetting redInReach = (new BooleanSetting("Change Color When Looking on Entity", "Switches to reach color when crosshair targets an entity within attack range"))
/* 38 */     .setValue(false);
/*    */   
/*    */   private HitboxCustomizer() {
/* 41 */     super("hitboxcustomizer", "Hitbox Customizer", ModuleCategory.OTHER);
/*    */     
/* 43 */     this.hitboxColor.setFullWidth(true);
/* 44 */     this.fill.setFullWidth(true);
/* 45 */     this.fillOpacity.setFullWidth(true);
/* 46 */     this.reachColor.setFullWidth(true);
/* 47 */     this.outlineThickness.setFullWidth(true);
/* 48 */     this.showLookLine.setFullWidth(true);
/* 49 */     this.redInReach.setFullWidth(true);
/* 50 */     Objects.requireNonNull(this.fill); this.fillOpacity.visible(this.fill::isValue);
/* 51 */     Objects.requireNonNull(this.redInReach); this.reachColor.setVisible(this.redInReach::isValue);
/*    */     
/* 53 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.hitboxColor, (Setting)this.fill, (Setting)this.fillOpacity, (Setting)this.showLookLine, (Setting)this.redInReach, (Setting)this.outlineThickness, (Setting)this.reachColor });
/*    */   }
/*    */   
/*    */   public static HitboxCustomizer getInstance() {
/* 57 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 62 */     return "Customize entity hitbox color";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 67 */     return "hitboxcustomizer.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 72 */     return 21.0F;
/*    */   }
/*    */   
/*    */   public int getHitboxColor() {
/* 76 */     return this.hitboxColor.getColor();
/*    */   }
/*    */   
/*    */   public boolean isFillEnabled() {
/* 80 */     return this.fill.isValue();
/*    */   }
/*    */   
/*    */   public float getFillOpacity() {
/* 84 */     return this.fillOpacity.getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\HitboxCustomizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */