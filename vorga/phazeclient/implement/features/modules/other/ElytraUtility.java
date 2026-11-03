/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import net.minecraft.class_10192;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1761;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_481;
/*     */ import net.minecraft.class_490;
/*     */ import net.minecraft.class_7706;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_9334;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ 
/*     */ 
/*     */ public final class ElytraUtility
/*     */   extends Module
/*     */ {
/*  29 */   private static final ElytraUtility INSTANCE = new ElytraUtility();
/*     */   
/*     */   private static final int CHEST_SLOT_ID = 6;
/*     */   private static final long SWAP_DELAY_MS = 120L;
/*     */   private static final long DEBOUNCE_MS = 200L;
/*  34 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  35 */   public final BindSetting keybind = (new BindSetting("Keybind", "Key to swap elytra/chestplate"))
/*  36 */     .setKey(82);
/*     */   
/*     */   private boolean swapping;
/*     */   
/*     */   private long lastActionMs;
/*     */ 
/*     */   
/*     */   private ElytraUtility() {
/*  44 */     super("elytrautility", "Elytra Swap", ModuleCategory.UTILITIES);
/*  45 */     this.keybind.setFullWidth(true);
/*  46 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.keybind });
/*     */   }
/*     */   
/*     */   public static ElytraUtility getInstance() {
/*  50 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  55 */     return "Swap chestplate and elytra with a single keybind";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  60 */     return "elytrautility.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  65 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServerAllowed() {
/*  75 */     return ServerUtil.isElytraUtilitySupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindStateChanged(int code, int action) {
/*  82 */     if (!isEnabled() || action != 1) {
/*     */       return;
/*     */     }
/*  85 */     class_310 mc = class_310.method_1551();
/*     */ 
/*     */     
/*  88 */     if (mc.field_1755 != null && !(mc.field_1755 instanceof class_481)) {
/*     */       return;
/*     */     }
/*  91 */     if (!ServerUtil.isElytraUtilitySupported()) {
/*     */       return;
/*     */     }
/*  94 */     if (this.keybind.getKey() == -1 || code != this.keybind.getKey()) {
/*     */       return;
/*     */     }
/*  97 */     beginInventorySwap();
/*     */   }
/*     */   
/*     */   private void beginInventorySwap() {
/* 101 */     class_310 mc = class_310.method_1551();
/* 102 */     if (mc.field_1724 == null || mc.field_1761 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     long now = System.currentTimeMillis();
/* 107 */     if (this.swapping || now - this.lastActionMs < 200L) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     class_1799 chestStack = mc.field_1724.field_7498.method_7611(6).method_7677();
/* 112 */     boolean elytraEquipped = chestStack.method_31574(class_1802.field_8833);
/* 113 */     int sourceSlotId = elytraEquipped ? findChestplateSlotId() : findElytraSlotId();
/* 114 */     if (sourceSlotId == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 118 */     this.swapping = true;
/* 119 */     this.lastActionMs = now;
/* 120 */     mc.method_1507((class_437)new class_490((class_1657)mc.field_1724));
/*     */     
/* 122 */     Thread worker = new Thread(() -> {
/*     */           try {
/*     */             Thread.sleep(120L);
/*     */             
/*     */             mc.execute(());
/*     */             
/*     */             Thread.sleep(180L);
/*     */             
/*     */             mc.execute(this::finishSwap);
/* 131 */           } catch (InterruptedException ignored) {
/*     */             Thread.currentThread().interrupt();
/*     */             mc.execute(this::finishSwap);
/*     */           } 
/*     */         }"PhazeClient-ElytraSwap");
/* 136 */     worker.setDaemon(true);
/* 137 */     worker.start();
/*     */   }
/*     */   
/*     */   private void phaze$ensureCreativeInventoryTab() {
/* 141 */     class_310 mc = class_310.method_1551();
/* 142 */     if (mc == null || !mc.field_1724.method_7337()) {
/*     */       return;
/*     */     }
/* 145 */     class_437 class_437 = mc.field_1755; if (class_437 instanceof class_481) { class_481 creativeScreen = (class_481)class_437;
/*     */       try {
/* 147 */         class_1761 inventoryGroup = (class_1761)class_7923.field_44687.method_29107(class_7706.field_40206);
/* 148 */         if (inventoryGroup == null) {
/*     */           return;
/*     */         }
/* 151 */         for (Method method : creativeScreen.getClass().getDeclaredMethods()) {
/* 152 */           if (method.getParameterCount() == 1 && method.getReturnType() == void.class) {
/* 153 */             Class<?> param = method.getParameterTypes()[0];
/* 154 */             if (param.isInstance(inventoryGroup)) {
/* 155 */               method.setAccessible(true);
/* 156 */               method.invoke(creativeScreen, new Object[] { inventoryGroup });
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 161 */       } catch (Throwable throwable) {} }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performSwap(int sourceSlotId) {
/* 169 */     class_310 mc = class_310.method_1551();
/* 170 */     if (mc.field_1724 == null || mc.field_1761 == null) {
/*     */       return;
/*     */     }
/* 173 */     if (sourceSlotId < 0 || sourceSlotId >= mc.field_1724.field_7498.field_7761.size()) {
/*     */       return;
/*     */     }
/* 176 */     int syncId = mc.field_1724.field_7498.field_7763;
/* 177 */     mc.field_1761.method_2906(syncId, sourceSlotId, 0, class_1713.field_7790, (class_1657)mc.field_1724);
/* 178 */     mc.field_1761.method_2906(syncId, 6, 0, class_1713.field_7790, (class_1657)mc.field_1724);
/* 179 */     mc.field_1761.method_2906(syncId, sourceSlotId, 0, class_1713.field_7790, (class_1657)mc.field_1724);
/*     */   }
/*     */   
/*     */   private void finishSwap() {
/* 183 */     class_310 mc = class_310.method_1551();
/* 184 */     if (mc.field_1724 != null) {
/* 185 */       mc.field_1724.method_7346();
/*     */     }
/* 187 */     mc.method_1507(null);
/* 188 */     this.swapping = false;
/*     */   }
/*     */   
/*     */   private int findElytraSlotId() {
/* 192 */     class_310 mc = class_310.method_1551();
/* 193 */     if (mc.field_1724 == null) return -1; 
/* 194 */     for (int slotId = 9; slotId <= 44; slotId++) {
/* 195 */       if (mc.field_1724.field_7498.method_7611(slotId).method_7677().method_31574(class_1802.field_8833)) {
/* 196 */         return slotId;
/*     */       }
/*     */     } 
/* 199 */     return -1;
/*     */   }
/*     */   
/*     */   private int findChestplateSlotId() {
/* 203 */     class_310 mc = class_310.method_1551();
/* 204 */     if (mc.field_1724 == null) return -1; 
/* 205 */     for (int slotId = 9; slotId <= 44; slotId++) {
/* 206 */       class_1799 stack = mc.field_1724.field_7498.method_7611(slotId).method_7677();
/* 207 */       class_10192 equippable = (class_10192)stack.method_57824(class_9334.field_54196);
/* 208 */       if (equippable != null && equippable.comp_3174() == class_1304.field_6174 && !stack.method_31574(class_1802.field_8833)) {
/* 209 */         return slotId;
/*     */       }
/*     */     } 
/* 212 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ElytraUtility.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */