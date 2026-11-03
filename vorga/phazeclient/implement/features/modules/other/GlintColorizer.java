/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ 
/*    */ public class GlintColorizer extends Module {
/* 13 */   private static final GlintColorizer INSTANCE = new GlintColorizer();
/*    */   
/* 15 */   public final SectionSetting generalSection = new SectionSetting("General");
/*    */   
/* 17 */   public final SelectSetting mode = (new SelectSetting("Mode", "Glint color mode"))
/* 18 */     .value(new String[] { "Custom", "Rainbow", "Turquoise", "Golden", "Ruby"
/* 19 */       }).selected("Custom");
/* 20 */   public final ColorSetting customColor = (new ColorSetting("Color", "Custom glint color")).value(-16711732)
/* 21 */     .visible(() -> Boolean.valueOf(this.mode.isSelected("Custom")));
/* 22 */   public final ValueSetting speed = (new ValueSetting("Speed", "Glint movement and color animation speed")
/*    */     {
/*    */       public boolean isFullWidth() {
/* 25 */         return GlintColorizer.this.mode.isSelected("Custom");
/*    */       }
/* 28 */     }).range(0.2F, 4.0F).step(0.1F).setValue(1.0F);
/*    */   
/*    */   private GlintColorizer() {
/* 31 */     super("glint_colorizer", "Custom Glint", ModuleCategory.OTHER);
/* 32 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.mode, (Setting)this.customColor, (Setting)this.speed });
/*    */   }
/*    */   
/*    */   public static GlintColorizer getInstance() {
/* 36 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 41 */     return "Custom color and animation speed for enchanted item and armor glint";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 46 */     return "color_correction.png";
/*    */   } public int getActiveGlintColor() {
/*    */     float cycle;
/*    */     float hue;
/* 50 */     if (!isEnabled()) return -6736897; 
/* 51 */     switch (this.mode.getSelected()) {
/*    */       case "Rainbow":
/* 53 */         cycle = Math.max(0.2F, this.speed.getValue());
/* 54 */         hue = (float)(System.currentTimeMillis() % (long)(5000.0F / cycle)) / 5000.0F / cycle;
/* 55 */         return Color.HSBtoRGB(hue, 0.85F, 1.0F);
/*    */       case "Turquoise":
/* 57 */         return -16711732;
/*    */       case "Golden":
/* 59 */         return -10496;
/*    */       case "Ruby":
/* 61 */         return -52378;
/*    */     } 
/* 63 */     return this.customColor.getColor();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\GlintColorizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */