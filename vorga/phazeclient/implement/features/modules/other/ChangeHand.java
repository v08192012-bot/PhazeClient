/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_1306;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChangeHand
/*     */   extends Module
/*     */ {
/*  50 */   private static final ChangeHand INSTANCE = new ChangeHand();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public final SectionSetting mainHandSection = new SectionSetting("Main Hand");
/*  61 */   public final ValueSetting mainHandX = (new ValueSetting("Main X", "Horizontal offset of the main hand item"))
/*  62 */     .range(-1.0F, 1.0F).step(0.01F).setValue(0.0F);
/*  63 */   public final ValueSetting mainHandY = (new ValueSetting("Main Y", "Vertical offset of the main hand item"))
/*  64 */     .range(-1.0F, 1.0F).step(0.01F).setValue(0.0F);
/*  65 */   public final ValueSetting mainHandZ = (new ValueSetting("Main Z", "Depth (forward/back) offset of the main hand item"))
/*  66 */     .range(-2.5F, 2.5F).step(0.01F).setValue(0.0F);
/*  67 */   public final ValueSetting mainHandScale = (new ValueSetting("Main Scale", "Size multiplier of the main hand item"))
/*  68 */     .range(0.1F, 2.0F).step(0.01F).setValue(1.0F)
/*     */ 
/*     */     
/*  71 */     .snapTo(1.0F, 0.08F);
/*     */ 
/*     */   
/*  74 */   public final SectionSetting offHandSection = new SectionSetting("Off Hand");
/*  75 */   public final ValueSetting offHandX = (new ValueSetting("Off X", "Horizontal offset of the off hand item"))
/*  76 */     .range(-1.0F, 1.0F).step(0.01F).setValue(0.0F);
/*  77 */   public final ValueSetting offHandY = (new ValueSetting("Off Y", "Vertical offset of the off hand item"))
/*  78 */     .range(-1.0F, 1.0F).step(0.01F).setValue(0.0F);
/*  79 */   public final ValueSetting offHandZ = (new ValueSetting("Off Z", "Depth (forward/back) offset of the off hand item"))
/*  80 */     .range(-2.5F, 2.5F).step(0.01F).setValue(0.0F);
/*  81 */   public final ValueSetting offHandScale = (new ValueSetting("Off Scale", "Size multiplier of the off hand item"))
/*  82 */     .range(0.1F, 2.0F).step(0.01F).setValue(1.0F)
/*  83 */     .snapTo(1.0F, 0.08F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public final SectionSetting switchSection = new SectionSetting("Switch Side");
/*  91 */   public final BooleanSetting uponImpact = (new BooleanSetting("Upon Impact", "Flip the main arm every time you successfully hit an entity"))
/*     */ 
/*     */     
/*  94 */     .setValue(false);
/*  95 */   public final BindSetting keybind = new BindSetting("Bind", "Key to manually flip the main arm. Only used when Upon Impact is OFF.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public final SectionSetting swaySection = new SectionSetting("Hand Sway");
/* 116 */   public final BooleanSetting noHandSway = (new BooleanSetting("No Hand Sway", "Stop the camera-following yaw/pitch sway on your held item, keeping it rotationally fixed relative to the camera"))
/*     */ 
/*     */     
/* 119 */     .setValue(false);
/*     */   
/*     */   private ChangeHand() {
/* 122 */     super("changehand", "Change Hand", ModuleCategory.OTHER);
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.mainHandX.setFullWidth(true);
/* 127 */     this.mainHandY.setFullWidth(true);
/* 128 */     this.mainHandZ.setFullWidth(true);
/* 129 */     this.mainHandScale.setFullWidth(true);
/* 130 */     this.offHandX.setFullWidth(true);
/* 131 */     this.offHandY.setFullWidth(true);
/* 132 */     this.offHandZ.setFullWidth(true);
/* 133 */     this.offHandScale.setFullWidth(true);
/* 134 */     this.uponImpact.setFullWidth(true);
/* 135 */     this.keybind.setFullWidth(true);
/* 136 */     this.noHandSway.setFullWidth(true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     this.keybind.visible(() -> Boolean.valueOf(!this.uponImpact.isValue()));
/* 142 */     setup(new Setting[] { (Setting)this.mainHandSection, (Setting)this.mainHandX, (Setting)this.mainHandY, (Setting)this.mainHandZ, (Setting)this.mainHandScale, (Setting)this.offHandSection, (Setting)this.offHandX, (Setting)this.offHandY, (Setting)this.offHandZ, (Setting)this.offHandScale, (Setting)this.switchSection, (Setting)this.uponImpact, (Setting)this.keybind, (Setting)this.swaySection, (Setting)this.noHandSway });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChangeHand getInstance() {
/* 151 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 156 */     return "Changes the visual properties of your hands - position, scale, and active side";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 161 */     return "hand.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 166 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMainHandTransform() {
/* 175 */     return (this.mainHandX.getValue() != 0.0F || this.mainHandY
/* 176 */       .getValue() != 0.0F || this.mainHandZ
/* 177 */       .getValue() != 0.0F || this.mainHandScale
/* 178 */       .getValue() != 1.0F);
/*     */   }
/*     */   
/*     */   public boolean hasOffHandTransform() {
/* 182 */     return (this.offHandX.getValue() != 0.0F || this.offHandY
/* 183 */       .getValue() != 0.0F || this.offHandZ
/* 184 */       .getValue() != 0.0F || this.offHandScale
/* 185 */       .getValue() != 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyHandOffset(class_4587 matrices, boolean mainHand, class_1306 mainArm) {
/* 190 */     float x = mainHand ? this.mainHandX.getValue() : this.offHandX.getValue();
/* 191 */     float y = mainHand ? this.mainHandY.getValue() : this.offHandY.getValue();
/* 192 */     float z = mainHand ? this.mainHandZ.getValue() : this.offHandZ.getValue();
/* 193 */     if (x != 0.0F || y != 0.0F || z != 0.0F) {
/* 194 */       float xSign = (mainArm == class_1306.field_6182) ? -1.0F : 1.0F;
/* 195 */       matrices.method_46416(x * xSign, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyHandScale(class_4587 matrices, boolean mainHand) {
/* 201 */     float scale = mainHand ? this.mainHandScale.getValue() : this.offHandScale.getValue();
/* 202 */     if (scale != 1.0F) {
/* 203 */       matrices.method_22905(scale, scale, scale);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/* 209 */     return false;
/*     */   }
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
/*     */   public void flipMainArm() {
/* 232 */     class_310 client = class_310.method_1551();
/* 233 */     if (client == null || client.field_1724 == null) {
/*     */       return;
/*     */     }
/* 236 */     class_1306 current = client.field_1724.method_6068();
/* 237 */     class_1306 flipped = (current == class_1306.field_6182) ? class_1306.field_6183 : class_1306.field_6182;
/* 238 */     client.field_1724.method_7283(flipped);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAttackEntity() {
/* 249 */     if (!isEnabled() || !this.uponImpact.isValue()) {
/*     */       return;
/*     */     }
/* 252 */     flipMainArm();
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindStateChanged(int code, int action) {
/* 268 */     if (!isEnabled() || this.uponImpact.isValue()) {
/*     */       return;
/*     */     }
/* 271 */     int bound = this.keybind.getKey();
/* 272 */     if (bound == -1 || code != bound) {
/*     */       return;
/*     */     }
/* 275 */     if (action != 1) {
/*     */       return;
/*     */     }
/* 278 */     class_310 client = class_310.method_1551();
/* 279 */     if (client != null && client.field_1755 != null) {
/*     */       return;
/*     */     }
/* 282 */     flipMainArm();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ChangeHand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */