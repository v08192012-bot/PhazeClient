/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1269;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2868;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_9334;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public final class AutoEat extends Module {
/*  19 */   private static final AutoEat INSTANCE = new AutoEat();
/*     */   
/*     */   private static final long FEED_COMMAND_COOLDOWN_MS = 2000L;
/*  22 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  23 */   public final BooleanSetting useCommand = (new BooleanSetting("Use /feed Command", "Send the /feed chat command instead of eating physical food (server-side support required)"))
/*     */ 
/*     */     
/*  26 */     .setValue(false);
/*  27 */   public final ValueSetting hungerThreshold = (new ValueSetting("Hunger Threshold", "Auto-eat when the food bar drops to this value (out of 20)"))
/*     */ 
/*     */     
/*  30 */     .range(1, 19).setValue(17.0F);
/*     */   
/*     */   private boolean eating = false;
/*  33 */   private int previousSlot = -1;
/*  34 */   private long lastCommandMs = 0L;
/*     */   
/*     */   private AutoEat() {
/*  37 */     super("auto_eat", "Auto Eat", ModuleCategory.UTILITIES);
/*  38 */     this.useCommand.setFullWidth(true);
/*  39 */     this.hungerThreshold.setFullWidth(true);
/*  40 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.useCommand, (Setting)this.hungerThreshold });
/*     */     
/*  42 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*     */   }
/*     */   
/*     */   public static AutoEat getInstance() {
/*  46 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  51 */     return "Automatically eats food (or runs /feed) when hunger drops below the threshold";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  56 */     return "auto_eat.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  61 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoEating() {
/*  71 */     return (isEnabled() && this.eating);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*  76 */     class_310 mc = class_310.method_1551();
/*  77 */     if (mc != null) {
/*  78 */       finishEating(mc);
/*     */     }
/*     */   }
/*     */   
/*     */   private void tick(class_310 mc) {
/*  83 */     if (mc == null || mc.field_1724 == null || mc.field_1761 == null) {
/*  84 */       if (this.eating && mc != null) {
/*  85 */         finishEating(mc);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     if (!isEnabled()) {
/*  91 */       if (this.eating) {
/*  92 */         finishEating(mc);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     boolean screenOpen = (mc.field_1755 != null);
/*     */     
/* 106 */     int hunger = mc.field_1724.method_7344().method_7586();
/* 107 */     int threshold = this.hungerThreshold.getInt();
/*     */     
/* 109 */     if (this.useCommand.isValue()) {
/* 110 */       if (this.eating) {
/* 111 */         finishEating(mc);
/*     */       }
/* 113 */       if (screenOpen) {
/*     */         return;
/*     */       }
/* 116 */       if (hunger <= threshold && mc.method_1562() != null) {
/* 117 */         long now = System.currentTimeMillis();
/* 118 */         if (now - this.lastCommandMs > 2000L) {
/* 119 */           mc.method_1562().method_45730("feed");
/* 120 */           this.lastCommandMs = now;
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 127 */     if (this.eating) {
/* 128 */       if (mc.field_1724.method_6115()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       finishEating(mc);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 143 */     if (screenOpen) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     if (hunger > threshold) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     int foodSlot = findFoodInHotbar((class_1657)mc.field_1724);
/* 152 */     if (foodSlot < 0) {
/*     */       return;
/*     */     }
/*     */     
/* 156 */     class_1661 inventory = mc.field_1724.method_31548();
/* 157 */     this.previousSlot = inventory.field_7545;
/* 158 */     selectHotbarSlot(mc, foodSlot);
/* 159 */     class_1269 result = mc.field_1761.method_2919((class_1657)mc.field_1724, class_1268.field_5808);
/* 160 */     if (result.method_23665()) {
/* 161 */       this.eating = true;
/*     */     } else {
/*     */       
/* 164 */       selectHotbarSlot(mc, this.previousSlot);
/* 165 */       this.previousSlot = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void selectHotbarSlot(class_310 mc, int slot) {
/* 175 */     if (mc == null || mc.field_1724 == null) {
/*     */       return;
/*     */     }
/* 178 */     (mc.field_1724.method_31548()).field_7545 = slot;
/* 179 */     if (mc.method_1562() != null) {
/* 180 */       mc.method_1562().method_52787((class_2596)new class_2868(slot));
/*     */     }
/*     */   }
/*     */   
/*     */   private int findFoodInHotbar(class_1657 player) {
/* 185 */     class_1661 inventory = player.method_31548();
/* 186 */     for (int i = 0; i < 9; i++) {
/* 187 */       class_1799 stack = inventory.method_5438(i);
/* 188 */       if (isFood(stack)) {
/* 189 */         return i;
/*     */       }
/*     */     } 
/* 192 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean isFood(class_1799 stack) {
/* 196 */     if (stack == null || stack.method_7960()) {
/* 197 */       return false;
/*     */     }
/* 199 */     return stack.method_57826(class_9334.field_50075);
/*     */   }
/*     */   
/*     */   private void finishEating(class_310 mc) {
/* 203 */     boolean wasEating = this.eating;
/* 204 */     this.eating = false;
/* 205 */     if (wasEating && this.previousSlot >= 0 && mc != null && mc.field_1724 != null) {
/* 206 */       selectHotbarSlot(mc, this.previousSlot);
/*     */     }
/* 208 */     this.previousSlot = -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoEat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */