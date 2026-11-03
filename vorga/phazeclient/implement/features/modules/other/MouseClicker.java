/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_636;
/*     */ import net.minecraft.class_746;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ import vorga.phazeclient.mixins.MinecraftClientMouseInvoker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MouseClicker
/*     */   extends Module
/*     */ {
/*  23 */   private static final MouseClicker INSTANCE = new MouseClicker();
/*     */   
/*     */   private static final String HAND_LEFT = "Left";
/*     */   
/*     */   private static final String HAND_RIGHT = "Right";
/*  28 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  29 */   public final ValueSetting clickDelay = (new ValueSetting("Click Delay", "Ticks between clicks (20 = 1s)"))
/*  30 */     .range(1, 100)
/*  31 */     .setValue(10.0F);
/*  32 */   public final SelectSetting hand = (new SelectSetting("Hand", "Which hand action to perform"))
/*  33 */     .value(new String[] { "Left", "Right"
/*  34 */       }).selected("Left");
/*     */   
/*     */   private int tickCounter;
/*     */   private boolean breakingBlock;
/*     */   
/*     */   private MouseClicker() {
/*  40 */     super("mouseclicker", "Tape Mouse", ModuleCategory.UTILITIES);
/*  41 */     this.clickDelay.setFullWidth(true);
/*  42 */     this.hand.setFullWidth(true);
/*  43 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.clickDelay, (Setting)this.hand });
/*     */   }
/*     */   
/*     */   public static MouseClicker getInstance() {
/*  47 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  52 */     return "Periodically attacks or uses item based on selected hand";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  57 */     return "mouseclicker.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  62 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServerAllowed() {
/*  67 */     return ServerUtil.isMouseClickerSupported();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*  72 */     this.tickCounter = 0;
/*  73 */     stopBlockBreaking(class_310.method_1551());
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  77 */     if (!isEnabled()) {
/*  78 */       this.tickCounter = 0;
/*  79 */       stopBlockBreaking(class_310.method_1551());
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     class_310 client = class_310.method_1551();
/*  84 */     if (client == null || client.field_1724 == null || client.field_1687 == null || client.field_1761 == null || client.field_1755 != null) {
/*     */       
/*  86 */       stopBlockBreaking(client);
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     if (!ServerUtil.isMouseClickerSupported()) {
/*  91 */       stopBlockBreaking(client);
/*     */       
/*     */       return;
/*     */     } 
/*  95 */     if ("Left".equalsIgnoreCase(this.hand.getSelected()) && client.field_1765 != null && client.field_1765
/*     */       
/*  97 */       .method_17783() == class_239.class_240.field_1332) {
/*  98 */       performHeldLeftClick(client);
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     stopBlockBreaking(client);
/*     */     
/* 104 */     this.tickCounter++;
/* 105 */     int delay = Math.max(1, this.clickDelay.getInt());
/* 106 */     if (this.tickCounter < delay) {
/*     */       return;
/*     */     }
/* 109 */     this.tickCounter = 0;
/*     */     
/* 111 */     performClick(client);
/*     */   }
/*     */   
/*     */   private void performClick(class_310 client) {
/* 115 */     class_746 player = client.field_1724;
/* 116 */     class_636 interaction = client.field_1761;
/* 117 */     if (player == null || interaction == null) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     if ("Right".equalsIgnoreCase(this.hand.getSelected())) {
/* 122 */       ((MinecraftClientMouseInvoker)client).phaze$invokeDoItemUse();
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     ((MinecraftClientMouseInvoker)client).phaze$invokeDoAttack();
/*     */   }
/*     */   
/*     */   private void performHeldLeftClick(class_310 client) {
/* 130 */     MinecraftClientMouseInvoker invoker = (MinecraftClientMouseInvoker)client;
/* 131 */     invoker.phaze$invokeHandleBlockBreaking(true);
/* 132 */     if (!this.breakingBlock) {
/* 133 */       this.breakingBlock = true;
/* 134 */       this.tickCounter = 0;
/* 135 */       invoker.phaze$invokeDoAttack();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void stopBlockBreaking(class_310 client) {
/* 140 */     if (!this.breakingBlock || client == null) {
/* 141 */       this.breakingBlock = false;
/*     */       
/*     */       return;
/*     */     } 
/* 145 */     ((MinecraftClientMouseInvoker)client).phaze$invokeHandleBlockBreaking(false);
/* 146 */     this.breakingBlock = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\MouseClicker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */