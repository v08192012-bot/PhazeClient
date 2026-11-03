/*    */ package vorga.phazeclient.implement.features.modules.hud;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_3532;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ 
/*    */ public final class NametagHud extends RectHudModule {
/*  9 */   private static final NametagHud INSTANCE = new NametagHud();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final float CUSTOM_PRESET_MAX_ALPHA = 0.3F;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public final SectionSetting nametagSection = new SectionSetting("Nametag");
/* 24 */   public final BooleanSetting nametagTextShadow = (new BooleanSetting("Nametag Text Shadow", "Shadow for nametag text")).setValue(true);
/* 25 */   public final BooleanSetting thirdPersonNametag = (new BooleanSetting("Third Person Nametag", "Render nametag in third person")).setValue(true);
/* 26 */   public final BooleanSetting toggleMessage = (new BooleanSetting("Toggle Message", "Display toggle message for nametags")).setValue(true);
/* 27 */   public final BooleanSetting hideInF1 = (new BooleanSetting("Hide in F1", "Hide nametags in F1 mode")).setValue(false);
/* 28 */   public final BooleanSetting replaceOwnNameColor = (new BooleanSetting("Replace Own Name Color", "Replace own nametag text color")).setValue(true);
/*    */   
/*    */   public static NametagHud getInstance() {
/* 31 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private NametagHud() {
/* 35 */     super("nametag_hud", "Nametag", 22.0F, 408.0F, 1.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     this.backgroundOpacity.setValue(100.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     this.textShadow.visible(() -> Boolean.valueOf(false));
/* 47 */     this.nametagTextShadow.setFullWidth(true);
/* 48 */     this.thirdPersonNametag.setFullWidth(true);
/* 49 */     this.toggleMessage.setFullWidth(true);
/* 50 */     this.hideInF1.setFullWidth(true);
/* 51 */     this.replaceOwnNameColor.setFullWidth(true);
/* 52 */     setup(new Setting[] { (Setting)this.nametagSection, (Setting)this.nametagTextShadow, (Setting)this.thirdPersonNametag, (Setting)this.toggleMessage, (Setting)this.hideInF1, (Setting)this.replaceOwnNameColor });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getResolvedBackgroundColor(class_310 client) {
/* 67 */     if (isVanillaPreset()) {
/*    */ 
/*    */ 
/*    */       
/* 71 */       int alpha = class_3532.method_15340(Math.round(76.5F), 0, 255);
/* 72 */       return alpha << 24;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     int parentColor = super.getResolvedBackgroundColor(client);
/* 80 */     int parentAlpha = parentColor >>> 24 & 0xFF;
/* 81 */     int scaledAlpha = class_3532.method_15340(Math.round(parentAlpha * 0.3F), 0, 255);
/* 82 */     return scaledAlpha << 24 | parentColor & 0xFFFFFF;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 87 */     return "Nametag options HUD";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 92 */     return "nametag_hud.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 97 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\NametagHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */