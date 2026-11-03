/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5250;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ 
/*     */ public final class AutoSwap extends Module {
/*  17 */   private static final AutoSwap INSTANCE = new AutoSwap();
/*     */   
/*     */   private static final int FIXED_SLOT_COUNT = 3;
/*     */   
/*     */   private static final String SPHERE_KEY = "сфера";
/*     */   private static final String TALISMAN_KEY = "талисман";
/*     */   private static final String TOTEM_KEY = "тотем";
/*     */   private static final int SWAP_DELAY_MS = 50;
/*  25 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  26 */   public final BindSetting keybind = new BindSetting("Keybind", "Key to activate swap");
/*  27 */   public final SelectSetting swapType = (new SelectSetting("Swap Type", "Automatic swap type"))
/*  28 */     .value(new String[] { "Sphere -> Totem", "Sphere -> Talisman", "Talisman -> Totem", "Talisman -> Talisman", "Sphere -> Sphere"
/*  29 */       }).selected("Sphere -> Totem")
/*  30 */     .onChange(this::onSwapTypeChanged);
/*     */   
/*     */   private boolean isSwapping = false;
/*     */   
/*  34 */   private int currentSlotIndex = 0;
/*     */   
/*     */   private AutoSwap() {
/*  37 */     super("autoswap", "Auto Swap", ModuleCategory.UTILITIES);
/*     */ 
/*     */     
/*  40 */     this.keybind.setFullWidth(true);
/*  41 */     this.swapType.setFullWidth(true);
/*     */     
/*  43 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.keybind, (Setting)this.swapType });
/*     */   }
/*     */   
/*     */   public static AutoSwap getInstance() {
/*  47 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  52 */     return "Swaps items on button press";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  57 */     return "autoswap.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  62 */     return 21.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCanBind() {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServerAllowed() {
/*  72 */     return ServerUtil.isAutoSwapSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activateDirectSwap() {
/*  79 */     if (!canActivateInGame()) {
/*     */       return;
/*     */     }
/*  82 */     if (!ServerUtil.isAutoSwapSupported()) {
/*  83 */       class_310 client = class_310.method_1551();
/*  84 */       if (client.field_1724 != null) {
/*  85 */         client.field_1724.method_7353((class_2561)class_2561.method_43470("§cAutoSwap is not supported on this server!"), true);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     SwapTarget target = findNextAvailableTargetWithRules();
/*  91 */     if (target != null) {
/*  92 */       startSwapSequence(target);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canActivateInGame() {
/*  98 */     class_310 client = class_310.method_1551();
/*  99 */     return (client.field_1724 != null && client.field_1687 != null && client.field_1755 == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startSwapSequence(SwapTarget target) {
/* 106 */     class_310 client = class_310.method_1551();
/* 107 */     if (client.field_1724 == null || client.field_1761 == null || this.isSwapping) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     if (target.slotId < 0 || target.slotId >= client.field_1724.field_7498.field_7761.size()) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     this.isSwapping = true;
/* 116 */     client.method_1507((class_437)new class_490((class_1657)client.field_1724));
/*     */     
/* 118 */     Thread worker = new Thread(() -> { sleepSwapDelay(); client.execute(()); }"PhazeClient-AutoSwapStart");
/*     */ 
/*     */ 
/*     */     
/* 122 */     worker.setDaemon(true);
/* 123 */     worker.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performSwap(int targetSlot, String itemName) {
/* 130 */     class_310 client = class_310.method_1551();
/* 131 */     if (client.field_1724 == null || client.field_1761 == null) {
/* 132 */       finishSwap();
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     int syncId = client.field_1724.field_7498.field_7763;
/* 137 */     int offhandSlot = 45;
/* 138 */     boolean hasOffhandItem = !((class_1799)(client.field_1724.method_31548()).field_7544.get(0)).method_7960();
/*     */     
/* 140 */     Thread worker = new Thread(() -> { runSwapClick(client, syncId, targetSlot); sleepSwapDelay(); runSwapClick(client, syncId, offhandSlot); if (hasOffhandItem) { sleepSwapDelay(); runSwapClick(client, syncId, targetSlot); }  sleepSwapDelay(); client.execute(()); }"PhazeClient-AutoSwapSteps");
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
/* 156 */     worker.setDaemon(true);
/* 157 */     worker.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishSwap() {
/* 164 */     class_310 client = class_310.method_1551();
/* 165 */     if (client.field_1724 != null) {
/* 166 */       client.field_1724.method_7346();
/*     */     }
/* 168 */     client.method_1507(null);
/* 169 */     this.isSwapping = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showSwapMessage(String itemName) {
/* 176 */     class_310 client = class_310.method_1551();
/* 177 */     if (client.field_1724 == null || itemName == null || itemName.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 182 */     class_1799 foundStack = findItemStackByName(itemName);
/* 183 */     if (foundStack != null && !foundStack.method_7960()) {
/*     */       
/* 185 */       class_5250 class_5250 = class_2561.method_43470("Swap to ").method_10852(foundStack.method_7964());
/* 186 */       client.field_1724.method_7353((class_2561)class_5250, true);
/*     */     } else {
/*     */       
/* 189 */       client.field_1724.method_7353((class_2561)class_2561.method_43470("Swap to " + itemName), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_1799 findItemStackByName(String itemName) {
/* 197 */     class_310 client = class_310.method_1551();
/* 198 */     if (client.field_1724 == null) return null;
/*     */     
/* 200 */     class_1661 inventory = client.field_1724.method_31548();
/* 201 */     for (int i = 0; i < inventory.field_7547.size(); i++) {
/* 202 */       class_1799 stack = (class_1799)inventory.field_7547.get(i);
/* 203 */       if (!stack.method_7960() && stack.method_7964().getString().equals(itemName)) {
/* 204 */         return stack;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 209 */     class_1799 offhand = (class_1799)inventory.field_7544.get(0);
/* 210 */     if (!offhand.method_7960() && offhand.method_7964().getString().equals(itemName)) {
/* 211 */       return offhand;
/*     */     }
/*     */     
/* 214 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SwapTarget findNextAvailableTargetWithRules() {
/* 221 */     class_310 client = class_310.method_1551();
/* 222 */     if (client.field_1724 == null) return null;
/*     */     
/* 224 */     class_1799 offhandStack = (class_1799)(client.field_1724.method_31548()).field_7544.get(0);
/* 225 */     String currentOffhandItem = offhandStack.method_7960() ? null : offhandStack.method_7964().getString();
/*     */     
/* 227 */     return applySwapRules(currentOffhandItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SwapTarget applySwapRules(String currentItem) {
/* 234 */     String rule = this.swapType.getSelected();
/* 235 */     String lowerItem = (currentItem == null) ? "" : currentItem.toLowerCase();
/*     */ 
/*     */     
/* 238 */     if (currentItem == null || currentItem.isEmpty()) {
/* 239 */       if ("Talisman -> Talisman".equals(rule)) {
/* 240 */         SwapTarget swapTarget = findItemContaining("талисман");
/* 241 */         if (swapTarget != null) return swapTarget;
/*     */       
/*     */       } 
/* 244 */       SwapTarget totem = findTotemTarget();
/* 245 */       if (totem != null) return totem;
/*     */       
/* 247 */       SwapTarget talisman = findItemContaining("талисман");
/* 248 */       if (talisman != null) return talisman;
/*     */       
/* 250 */       return findItemContaining("сфера");
/*     */     } 
/*     */ 
/*     */     
/* 254 */     if (isTotemName(currentItem)) {
/* 255 */       if ("Sphere -> Totem".equals(rule) || "Sphere -> Talisman".equals(rule)) {
/* 256 */         SwapTarget sphere = findItemContaining("сфера");
/* 257 */         if (sphere != null) return sphere;
/*     */       
/*     */       } 
/* 260 */       if ("Talisman -> Totem".equals(rule)) {
/* 261 */         SwapTarget talisman = findItemContaining("талисман");
/* 262 */         if (talisman != null) return talisman; 
/*     */       } 
/* 264 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 268 */     if (lowerItem.contains("талисман")) {
/* 269 */       if ("Talisman -> Talisman".equals(rule)) {
/* 270 */         SwapTarget talisman = findItemContaining("талисман");
/* 271 */         if (talisman != null) return talisman;
/*     */       
/*     */       } 
/* 274 */       if ("Talisman -> Totem".equals(rule)) {
/* 275 */         SwapTarget totem = findTotemTarget();
/* 276 */         if (totem != null) return totem;
/*     */         
/* 278 */         if ("Sphere -> Totem".equals(rule) || "Sphere -> Talisman".equals(rule)) {
/* 279 */           SwapTarget sphere = findItemContaining("сфера");
/* 280 */           if (sphere != null) return sphere;
/*     */         
/*     */         } 
/*     */       } 
/* 284 */       if ("Sphere -> Talisman".equals(rule)) {
/* 285 */         return findItemContaining("сфера");
/*     */       }
/* 287 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 291 */     if (lowerItem.contains("сфера")) {
/* 292 */       if ("Sphere -> Sphere".equals(rule)) {
/* 293 */         SwapTarget sphere = findItemContaining("сфера");
/* 294 */         if (sphere != null) return sphere;
/*     */       
/*     */       } 
/* 297 */       if ("Sphere -> Totem".equals(rule)) {
/* 298 */         SwapTarget totem = findTotemTarget();
/* 299 */         if (totem != null) return totem;
/*     */         
/* 301 */         SwapTarget talisman = findItemContaining("талисман");
/* 302 */         if (talisman != null) return talisman; 
/* 303 */       } else if ("Sphere -> Talisman".equals(rule)) {
/* 304 */         SwapTarget talisman = findItemContaining("талисман");
/* 305 */         if (talisman != null) return talisman;
/*     */         
/* 307 */         SwapTarget totem = findTotemTarget();
/* 308 */         if (totem != null) return totem;
/*     */       
/*     */       } 
/*     */     } 
/* 312 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SwapTarget findTotemTarget() {
/* 319 */     class_310 client = class_310.method_1551();
/* 320 */     if (client.field_1724 == null) return null;
/*     */     
/* 322 */     class_1661 inventory = client.field_1724.method_31548();
/* 323 */     for (int i = 0; i < inventory.field_7547.size(); i++) {
/* 324 */       class_1799 stack = (class_1799)inventory.field_7547.get(i);
/* 325 */       if (!stack.method_7960() && stack.method_31574(class_1802.field_8288)) {
/* 326 */         return new SwapTarget(stack.method_7964().getString(), (i < 9) ? (i + 36) : i);
/*     */       }
/*     */     } 
/* 329 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SwapTarget findItemContaining(String searchText) {
/* 336 */     class_310 client = class_310.method_1551();
/* 337 */     if (client.field_1724 == null) return null;
/*     */     
/* 339 */     class_1661 inventory = client.field_1724.method_31548();
/* 340 */     for (int i = 0; i < inventory.field_7547.size(); i++) {
/* 341 */       class_1799 stack = (class_1799)inventory.field_7547.get(i);
/* 342 */       if (!stack.method_7960()) {
/* 343 */         String stackName = stack.method_7964().getString();
/* 344 */         if (stackName.toLowerCase().contains(searchText)) {
/* 345 */           return new SwapTarget(stackName, (i < 9) ? (i + 36) : i);
/*     */         }
/*     */       } 
/*     */     } 
/* 349 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTotemName(String itemName) {
/* 356 */     return (itemName != null && (itemName
/* 357 */       .equalsIgnoreCase("Totem of Undying") || itemName
/* 358 */       .toLowerCase().contains("тотем")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findItemSlotByName(String itemName) {
/* 365 */     class_310 client = class_310.method_1551();
/* 366 */     if (client.field_1724 == null) return -1;
/*     */     
/* 368 */     class_1661 inventory = client.field_1724.method_31548();
/* 369 */     for (int i = 0; i < inventory.field_7547.size(); i++) {
/* 370 */       class_1799 stack = (class_1799)inventory.field_7547.get(i);
/* 371 */       if (!stack.method_7960() && stack.method_7964().getString().equals(itemName)) {
/* 372 */         return (i < 9) ? (i + 36) : i;
/*     */       }
/*     */     } 
/* 375 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runSwapClick(class_310 client, int syncId, int slotId) {
/* 382 */     CountDownLatch latch = new CountDownLatch(1);
/* 383 */     client.execute(() -> {
/*     */           try {
/*     */             if (client.field_1724 == null || client.field_1761 == null) {
/*     */               return;
/*     */             }
/*     */             
/*     */             client.field_1761.method_2906(syncId, slotId, 0, class_1713.field_7790, (class_1657)client.field_1724);
/*     */           } finally {
/*     */             latch.countDown();
/*     */           } 
/*     */         });
/*     */     try {
/* 395 */       latch.await();
/* 396 */     } catch (InterruptedException ignored) {
/* 397 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sleepSwapDelay() {
/*     */     try {
/* 406 */       Thread.sleep(50L);
/* 407 */     } catch (InterruptedException ignored) {
/* 408 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSwapping() {
/* 413 */     return this.isSwapping;
/*     */   }
/*     */   
/*     */   private void onSwapTypeChanged(String newType) {}
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoSwap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */