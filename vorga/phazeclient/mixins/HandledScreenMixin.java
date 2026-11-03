/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1703;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1735;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_465;
/*     */ import net.minecraft.class_9288;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ import vorga.phazeclient.implement.features.modules.other.HealingHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.ItemHighlighter;
/*     */ import vorga.phazeclient.implement.features.modules.other.ItemScroller;
/*     */ import vorga.phazeclient.implement.features.modules.other.MaceIndicator;
/*     */ import vorga.phazeclient.implement.features.modules.other.ShulkerPreview;
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
/*     */ @Mixin({class_465.class})
/*     */ public abstract class HandledScreenMixin
/*     */ {
/*     */   @Shadow
/*     */   protected int field_2776;
/*     */   @Shadow
/*     */   protected int field_2800;
/*     */   @Shadow
/*     */   @Nullable
/*     */   protected class_1735 field_2787;
/*     */   
/*     */   @Shadow
/*     */   @Nullable
/*     */   protected abstract class_1735 method_64240(double paramDouble1, double paramDouble2);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_2383(@Nullable class_1735 paramclass_1735, int paramInt1, int paramInt2, class_1713 paramclass_1713);
/*     */   
/*     */   @Unique
/*     */   private static void phaze$resetGuiRenderState() {
/*  63 */     RenderSystem.depthMask(true);
/*  64 */     RenderSystem.enableDepthTest();
/*  65 */     RenderSystem.defaultBlendFunc();
/*  66 */     RenderSystem.disableBlend();
/*  67 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*  74 */   private final Set<Integer> phaze$transferredSlots = new HashSet<>();
/*     */   @Unique
/*  76 */   private long phaze$lastTransferAt = 0L;
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$shiftDragActive = false;
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseClicked"}, at = {@At("HEAD")})
/*     */   private void phaze$onMouseClickedHead(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
/*  84 */     ItemScroller mod = ItemScroller.getInstance();
/*  85 */     if (mod == null || !mod.isEnabled() || !ServerUtil.isItemScrollerSupported()) {
/*  86 */       this.phaze$shiftDragActive = false;
/*     */       return;
/*     */     } 
/*  89 */     if (this instanceof net.minecraft.class_481) {
/*  90 */       this.phaze$shiftDragActive = false;
/*  91 */       this.phaze$transferredSlots.clear();
/*     */       return;
/*     */     } 
/*  94 */     if (button != 0 || !class_437.method_25442()) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     this.phaze$shiftDragActive = true;
/*  99 */     this.phaze$transferredSlots.clear();
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.phaze$lastTransferAt = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseReleased"}, at = {@At("HEAD")})
/*     */   private void phaze$onMouseReleasedHead(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
/* 109 */     if (button == 0) {
/* 110 */       this.phaze$shiftDragActive = false;
/* 111 */       this.phaze$transferredSlots.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"mouseDragged"}, at = {@At("HEAD")})
/*     */   private void phaze$onMouseDraggedHead(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
/* 119 */     if (!this.phaze$shiftDragActive) {
/* 120 */       this.phaze$shiftDragActive = true;
/* 121 */       this.phaze$transferredSlots.clear();
/* 122 */       this.phaze$lastTransferAt = 0L;
/*     */     } 
/*     */     
/* 125 */     double startX = mouseX - deltaX;
/* 126 */     double startY = mouseY - deltaY;
/* 127 */     double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
/* 128 */     int steps = Math.max(1, (int)Math.ceil(dist / 4.0D));
/*     */     
/* 130 */     for (int i = 0; i <= steps; i++) {
/* 131 */       double t = i / steps;
/* 132 */       double sampleX = startX + deltaX * t;
/* 133 */       double sampleY = startY + deltaY * t;
/* 134 */       if (phaze$tryShiftDragTransfer(sampleX, sampleY)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void phaze$processShiftDragWhileHovering(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 142 */     phaze$tryShiftDragTransfer(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"drawMouseoverTooltip"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void phaze$suppressShulkerVanillaTooltip(class_332 context, int mouseX, int mouseY, CallbackInfo ci) {
/* 151 */     ShulkerPreview module = ShulkerPreview.getInstance();
/* 152 */     if (module == null || !module.isEnabled() || !module.shouldShow()) {
/*     */       return;
/*     */     }
/* 155 */     if (this.field_2787 == null) {
/*     */       return;
/*     */     }
/* 158 */     class_1799 stack = this.field_2787.method_7677();
/* 159 */     class_9288 container = module.extractContainer(stack);
/* 160 */     if (container == null) {
/*     */       return;
/*     */     }
/* 163 */     ci.cancel();
/*     */   }
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*     */   private void phaze$drawShulkerPreview(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 168 */     ShulkerPreview module = ShulkerPreview.getInstance();
/* 169 */     if (module == null || !module.isEnabled() || !module.shouldShow()) {
/*     */       return;
/*     */     }
/* 172 */     if (this.field_2787 == null) {
/*     */       return;
/*     */     }
/* 175 */     class_1799 stack = this.field_2787.method_7677();
/* 176 */     class_9288 container = module.extractContainer(stack);
/* 177 */     if (container == null) {
/*     */       return;
/*     */     }
/* 180 */     module.renderPreview(context, mouseX, mouseY, container, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("HEAD")})
/*     */   private void phaze$resetStateAtRenderHead(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 189 */     phaze$resetGuiRenderState();
/*     */   }
/*     */   
/*     */   @Inject(method = {"drawSlot"}, at = {@At("HEAD")})
/*     */   private void phaze$resetStateBeforeSlot(class_332 context, class_1735 slot, CallbackInfo ci) {
/* 194 */     phaze$resetGuiRenderState();
/*     */   }
/*     */   
/*     */   @Inject(method = {"render"}, at = {@At("RETURN")})
/*     */   private void phaze$drawUtilityHighlights(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 199 */     HealingHelper healing = HealingHelper.getInstance();
/* 200 */     ItemHighlighter highlighter = ItemHighlighter.getInstance();
/* 201 */     MaceIndicator mace = MaceIndicator.getInstance();
/* 202 */     boolean healingEnabled = (healing != null && healing.isEnabled());
/* 203 */     boolean highlighterEnabled = (highlighter != null && highlighter.isEnabled());
/* 204 */     boolean maceEnabled = (mace != null && mace.isEnabled());
/* 205 */     if (!healingEnabled && !highlighterEnabled && !maceEnabled) {
/* 206 */       phaze$resetGuiRenderState();
/*     */       return;
/*     */     } 
/* 209 */     if (healingEnabled) healing.beginRenderPass(); 
/* 210 */     if (highlighterEnabled) highlighter.beginRenderPass(); 
/* 211 */     phaze$paintInventoryUtilityFills(context, 
/*     */         
/* 213 */         healingEnabled ? healing : null, 
/* 214 */         highlighterEnabled ? highlighter : null, 
/* 215 */         maceEnabled ? mace : null);
/*     */     
/* 217 */     phaze$resetGuiRenderState();
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
/*     */   @Unique
/*     */   private void phaze$paintInventoryUtilityFills(class_332 context, HealingHelper healing, ItemHighlighter highlighter, MaceIndicator mace) {
/* 233 */     class_465<?> self = (class_465)this;
/* 234 */     class_1703 handler = self.method_17577();
/* 235 */     if (handler == null) {
/*     */       return;
/*     */     }
/*     */     
/* 239 */     context.method_51452();
/* 240 */     RenderSystem.enableBlend();
/* 241 */     RenderSystem.defaultBlendFunc();
/* 242 */     RenderSystem.disableDepthTest();
/* 243 */     RenderSystem.depthMask(false);
/* 244 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 246 */     class_310 client = class_310.method_1551();
/* 247 */     class_1661 playerInventory = (client != null && client.field_1724 != null) ? client.field_1724.method_31548() : null;
/* 248 */     boolean paintedPlayerSlots = false;
/* 249 */     if (playerInventory != null) {
/* 250 */       for (class_1735 slot : handler.field_7761) {
/* 251 */         if (!phaze$isMainPlayerSlot(slot, playerInventory)) {
/*     */           continue;
/*     */         }
/* 254 */         phaze$paintUtilitySlot(context, slot, healing, highlighter, mace);
/* 255 */         paintedPlayerSlots = true;
/*     */       } 
/*     */     }
/*     */     
/* 259 */     if (!paintedPlayerSlots) {
/* 260 */       int totalSlots = handler.field_7761.size();
/* 261 */       int playerInvStart = totalSlots - 36;
/* 262 */       if (playerInvStart >= 0) {
/* 263 */         for (int i = playerInvStart; i < totalSlots; i++) {
/* 264 */           phaze$paintUtilitySlot(context, (class_1735)handler.field_7761.get(i), healing, highlighter, mace);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 269 */     context.method_51452();
/* 270 */     RenderSystem.depthMask(true);
/* 271 */     phaze$resetGuiRenderState();
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$isMainPlayerSlot(class_1735 slot, class_1661 playerInventory) {
/* 276 */     return (slot != null && slot.field_7871 == playerInventory && slot
/*     */       
/* 278 */       .method_34266() >= 0 && slot
/* 279 */       .method_34266() < 36);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private void phaze$paintUtilitySlot(class_332 context, class_1735 slot, HealingHelper healing, ItemHighlighter highlighter, MaceIndicator mace) {
/* 290 */     class_1799 stack = slot.method_7677();
/* 291 */     int sx = this.field_2776 + slot.field_7873;
/* 292 */     int sy = this.field_2800 + slot.field_7872;
/* 293 */     if (healing != null) {
/* 294 */       phaze$fillUtilitySlot(context, sx, sy, healing.colorForPreparedStack(stack));
/*     */     }
/* 296 */     if (highlighter != null) {
/* 297 */       phaze$fillUtilitySlot(context, sx, sy, highlighter.colorForPreparedStack(stack));
/*     */     }
/* 299 */     if (mace != null) {
/* 300 */       phaze$fillUtilitySlot(context, sx, sy, mace.colorForStack(stack));
/*     */     }
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private static void phaze$fillUtilitySlot(class_332 context, int x, int y, int color) {
/* 306 */     if ((color & 0xFF000000) != 0) {
/* 307 */       context.method_25294(x, y, x + 16, y + 16, color);
/*     */     }
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$tryShiftDragTransfer(double mouseX, double mouseY) {
/* 313 */     ItemScroller mod = ItemScroller.getInstance();
/* 314 */     class_310 client = class_310.method_1551();
/* 315 */     if (mod == null || !mod.isEnabled() || !ServerUtil.isItemScrollerSupported() || client == null || client.method_22683() == null) {
/* 316 */       this.phaze$shiftDragActive = false;
/* 317 */       return false;
/*     */     } 
/* 319 */     if (this instanceof net.minecraft.class_481) {
/* 320 */       this.phaze$shiftDragActive = false;
/* 321 */       this.phaze$transferredSlots.clear();
/* 322 */       return false;
/*     */     } 
/*     */     
/* 325 */     boolean leftPressed = (GLFW.glfwGetMouseButton(client.method_22683().method_4490(), 0) == 1);
/* 326 */     if (!this.phaze$shiftDragActive || !leftPressed || !class_437.method_25442()) {
/* 327 */       if (!leftPressed) {
/* 328 */         this.phaze$shiftDragActive = false;
/* 329 */         this.phaze$transferredSlots.clear();
/*     */       } 
/* 331 */       return false;
/*     */     } 
/*     */     
/* 334 */     long now = System.currentTimeMillis();
/* 335 */     if (now - this.phaze$lastTransferAt < mod.getDelayMs()) {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     class_1735 slot = method_64240(mouseX, mouseY);
/* 340 */     if (slot == null || !slot.method_7681()) {
/* 341 */       return false;
/*     */     }
/*     */     
/* 344 */     int transferSlotId = slot.field_7874;
/* 345 */     if (this.phaze$transferredSlots.contains(Integer.valueOf(transferSlotId))) {
/* 346 */       return false;
/*     */     }
/*     */     
/* 349 */     this.phaze$transferredSlots.add(Integer.valueOf(transferSlotId));
/* 350 */     if (!phaze$quickMoveSlot(slot)) {
/* 351 */       this.phaze$transferredSlots.remove(Integer.valueOf(transferSlotId));
/* 352 */       return false;
/*     */     } 
/*     */     
/* 355 */     this.phaze$lastTransferAt = now;
/* 356 */     return true;
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean phaze$quickMoveSlot(class_1735 slot) {
/* 361 */     if (slot == null) {
/* 362 */       return false;
/*     */     }
/*     */     
/* 365 */     class_310 client = class_310.method_1551();
/* 366 */     if (client == null || client.field_1724 == null) {
/* 367 */       return false;
/*     */     }
/*     */     
/* 370 */     method_2383(slot, slot.field_7874, 0, class_1713.field_7794);
/* 371 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\HandledScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */