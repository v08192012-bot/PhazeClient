/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_5498;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ 
/*     */ public final class FreeLook extends Module {
/*  12 */   private static final FreeLook INSTANCE = new FreeLook();
/*     */ 
/*     */   
/*  15 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  16 */   public final BindSetting keybind = new BindSetting("Keybind", "Key to activate freelook");
/*  17 */   public final BooleanSetting hold = (new BooleanSetting("Hold", "Hold the key to keep freelook active. When off, the key toggles freelook on/off."))
/*  18 */     .setValue(false);
/*     */   
/*     */   private boolean active = false;
/*     */   
/*     */   private float anchorYaw;
/*     */   private float anchorPitch;
/*     */   private float lookYaw;
/*     */   private float lookPitch;
/*     */   private float prevLookYaw;
/*     */   private float prevLookPitch;
/*  28 */   private class_5498 originalPerspective = class_5498.field_26664;
/*     */   
/*     */   private FreeLook() {
/*  31 */     super("freelook", "Free Look", ModuleCategory.UTILITIES);
/*     */     
/*  33 */     this.keybind.setFullWidth(true);
/*  34 */     this.hold.setFullWidth(true);
/*  35 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.keybind, (Setting)this.hold });
/*     */   }
/*     */   
/*     */   public static FreeLook getInstance() {
/*  39 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  44 */     return "Look around without moving your player";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  49 */     return "freelook.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  54 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public void tick() {
/*  63 */     class_310 mc = class_310.method_1551();
/*     */     
/*  65 */     if (!this.active) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     if (!isEnabled() || mc.field_1724 == null) {
/*  70 */       deactivate();
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     mc.field_1724.method_36456(this.anchorYaw);
/*  75 */     mc.field_1724.method_36457(this.anchorPitch);
/*     */   }
/*     */   
/*     */   public boolean onMouseLook(double cursorDeltaX, double cursorDeltaY) {
/*  79 */     class_310 mc = class_310.method_1551();
/*     */     
/*  81 */     if (!this.active || mc.field_1724 == null) {
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     double yawDelta = cursorDeltaX * 0.15D;
/*  95 */     double pitchDelta = cursorDeltaY * 0.15D;
/*     */     
/*  97 */     this.prevLookYaw = this.lookYaw;
/*  98 */     this.prevLookPitch = this.lookPitch;
/*  99 */     this.lookYaw += (float)yawDelta;
/* 100 */     this.lookPitch = class_3532.method_15363(this.lookPitch + (float)pitchDelta, -90.0F, 90.0F);
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public void activate() {
/* 105 */     class_310 mc = class_310.method_1551();
/*     */     
/* 107 */     if (!isEnabled() || this.active || mc.field_1724 == null || mc.field_1755 != null) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     this.anchorYaw = mc.field_1724.method_36454();
/* 112 */     this.anchorPitch = mc.field_1724.method_36455();
/* 113 */     this.lookYaw = this.anchorYaw;
/* 114 */     this.lookPitch = this.anchorPitch;
/* 115 */     this.prevLookYaw = this.lookYaw;
/* 116 */     this.prevLookPitch = this.lookPitch;
/* 117 */     this.originalPerspective = mc.field_1690.method_31044();
/* 118 */     mc.field_1690.method_31043(class_5498.field_26665);
/* 119 */     this.active = true;
/*     */   }
/*     */   
/*     */   public void deactivate() {
/* 123 */     class_310 mc = class_310.method_1551();
/*     */     
/* 125 */     if (!this.active) {
/*     */       return;
/*     */     }
/*     */     
/* 129 */     if (mc.field_1724 != null) {
/* 130 */       mc.field_1724.method_36456(this.anchorYaw);
/* 131 */       mc.field_1724.method_36457(this.anchorPitch);
/*     */     } 
/*     */     
/* 134 */     mc.field_1690.method_31043(this.originalPerspective);
/* 135 */     this.active = false;
/*     */   }
/*     */   
/*     */   public float getCameraYaw(float tickDelta) {
/* 139 */     return class_3532.method_17821(tickDelta, this.prevLookYaw, this.lookYaw);
/*     */   }
/*     */   
/*     */   public float getCameraPitch(float tickDelta) {
/* 143 */     return class_3532.method_16439(tickDelta, this.prevLookPitch, this.lookPitch);
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 147 */     return this.active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindStateChanged(int code, int action) {
/* 157 */     if (code == 294 && action == 1 && this.active) {
/* 158 */       deactivate();
/*     */       
/*     */       return;
/*     */     } 
/* 162 */     if (code != this.keybind.getKey()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     class_310 mc = class_310.method_1551();
/* 176 */     if (mc != null && mc.method_22683() != null) {
/* 177 */       long handle = mc.method_22683().method_4490();
/* 178 */       if (GLFW.glfwGetKey(handle, 292) == 1) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if (this.hold.isValue()) {
/*     */       
/* 185 */       if (action == 1) {
/* 186 */         activate();
/* 187 */       } else if (action == 0) {
/* 188 */         deactivate();
/*     */       }
/*     */     
/*     */     }
/* 192 */     else if (action == 1) {
/* 193 */       if (this.active) {
/* 194 */         deactivate();
/*     */       } else {
/* 196 */         activate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDisable() {
/* 203 */     deactivate();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\FreeLook.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */