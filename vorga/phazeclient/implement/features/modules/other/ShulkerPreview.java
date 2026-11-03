/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.class_1747;
/*     */ import net.minecraft.class_1767;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2480;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3675;
/*     */ import net.minecraft.class_9288;
/*     */ import net.minecraft.class_9334;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
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
/*     */ public final class ShulkerPreview
/*     */   extends Module
/*     */ {
/*  86 */   private static final ShulkerPreview INSTANCE = new ShulkerPreview();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final class_2960 PANEL_SPRITE = class_2960.method_60655("phaze", "shulker_box_tooltip");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int UNCOLORED_SHULKER_RGB = 9922455;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static final class_2960 SLOT_HIGHLIGHT_BACK = class_2960.method_60656("container/slot_highlight_back");
/*     */   
/* 110 */   private static final class_2960 SLOT_HIGHLIGHT_FRONT = class_2960.method_60656("container/slot_highlight_front");
/*     */ 
/*     */   
/*     */   private static final int SLOT_SIZE = 18;
/*     */ 
/*     */   
/*     */   private static final int GRID_COLS = 9;
/*     */ 
/*     */   
/*     */   private static final int GRID_ROWS = 3;
/*     */ 
/*     */   
/*     */   private static final int SLOT_OFFSET_X = 8;
/*     */ 
/*     */   
/*     */   private static final int SLOT_OFFSET_Y = 8;
/*     */   
/*     */   private static final int PREVIEW_W = 176;
/*     */   
/*     */   private static final int PREVIEW_H = 68;
/*     */   
/*     */   private static final int HIGHLIGHT_SIZE = 24;
/*     */   
/*     */   private static final int HIGHLIGHT_OFFSET = 4;
/*     */   
/*     */   private static final int CURSOR_OFFSET = 14;
/*     */   
/* 137 */   private static final class_1799[] CACHED_STACKS = new class_1799[27];
/* 138 */   private static final boolean[] CACHED_OVERLAYS = new boolean[27];
/* 139 */   private static int cachedSlotsToDraw = 0;
/* 140 */   private static class_1799 lastHoveredStack = null;
/* 141 */   private static class_9288 lastContainer = null;
/* 142 */   private static int lastTick = Integer.MIN_VALUE;
/*     */   
/* 144 */   public final SectionSetting generalSection = new SectionSetting("General");
/* 145 */   public final BooleanSetting alwaysShow = (new BooleanSetting("Always Show", "Always show shulker contents while hovering, without holding a key"))
/*     */ 
/*     */     
/* 148 */     .setValue(false);
/* 149 */   public final BindSetting showBind = new BindSetting("Show Bind", "Hold this key to show the preview while Always Show is off");
/*     */ 
/*     */ 
/*     */   
/* 153 */   public final BooleanSetting colorByShulker = (new BooleanSetting("Color By Shulker", "Tint the chest preview background with the shulker box's dye colour"))
/*     */ 
/*     */     
/* 156 */     .setValue(true);
/*     */   
/*     */   private ShulkerPreview() {
/* 159 */     super("shulker_preview", "Shulker Preview", ModuleCategory.UTILITIES);
/* 160 */     this.alwaysShow.setFullWidth(true);
/* 161 */     this.showBind.setFullWidth(true);
/* 162 */     this.showBind.visible(() -> Boolean.valueOf(!this.alwaysShow.isValue()));
/* 163 */     this.colorByShulker.setFullWidth(true);
/* 164 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.alwaysShow, (Setting)this.showBind, (Setting)this.colorByShulker });
/*     */   }
/*     */   
/*     */   public static ShulkerPreview getInstance() {
/* 168 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 173 */     return "Shows shulker box contents in a tooltip while hovering it in any inventory";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 178 */     return "shulker_preview.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 183 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public boolean shouldShow() {
/* 187 */     if (this.alwaysShow.isValue()) {
/* 188 */       return true;
/*     */     }
/* 190 */     int key = this.showBind.getKey();
/* 191 */     if (key == -1) {
/* 192 */       return false;
/*     */     }
/* 194 */     class_310 mc = class_310.method_1551();
/* 195 */     if (mc == null || mc.method_22683() == null) {
/* 196 */       return false;
/*     */     }
/* 198 */     return class_3675.method_15987(mc.method_22683().method_4490(), key);
/*     */   }
/*     */   public class_9288 extractContainer(class_1799 stack) {
/*     */     class_1747 blockItem;
/* 202 */     if (stack == null || stack.method_7960()) {
/* 203 */       return null;
/*     */     }
/* 205 */     class_1792 class_1792 = stack.method_7909(); if (class_1792 instanceof class_1747) { blockItem = (class_1747)class_1792; }
/* 206 */     else { return null; }
/*     */     
/* 208 */     class_2248 block = blockItem.method_7711();
/* 209 */     if (!(block instanceof class_2480)) {
/* 210 */       return null;
/*     */     }
/* 212 */     return (class_9288)stack.method_57824(class_9334.field_49622);
/*     */   }
/*     */   private static class_1767 resolveShulkerColor(class_1799 stack) {
/*     */     class_1747 blockItem;
/* 216 */     if (stack != null) { class_1792 class_1792 = stack.method_7909(); if (class_1792 instanceof class_1747) { blockItem = (class_1747)class_1792; }
/* 217 */       else { return null; }  } else { return null; }
/*     */     
/* 219 */     class_2248 class_2248 = blockItem.method_7711(); if (class_2248 instanceof class_2480) { class_2480 shulker = (class_2480)class_2248;
/* 220 */       return shulker.method_10528(); }
/*     */     
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isShulkerBox(class_1799 stack) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ifnull -> 35
/*     */     //   4: aload_0
/*     */     //   5: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
/*     */     //   8: astore_2
/*     */     //   9: aload_2
/*     */     //   10: instanceof net/minecraft/class_1747
/*     */     //   13: ifeq -> 35
/*     */     //   16: aload_2
/*     */     //   17: checkcast net/minecraft/class_1747
/*     */     //   20: astore_1
/*     */     //   21: aload_1
/*     */     //   22: invokevirtual method_7711 : ()Lnet/minecraft/class_2248;
/*     */     //   25: instanceof net/minecraft/class_2480
/*     */     //   28: ifeq -> 35
/*     */     //   31: iconst_1
/*     */     //   32: goto -> 36
/*     */     //   35: iconst_0
/*     */     //   36: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #228	-> 0
/*     */     //   #227	-> 4
/*     */     //   #228	-> 22
/*     */     //   #226	-> 36
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   21	14	1	blockItem	Lnet/minecraft/class_1747;
/*     */     //   0	37	0	stack	Lnet/minecraft/class_1799;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int clampDyeChannel(int channel) {
/* 235 */     return (channel < 38) ? 38 : channel;
/*     */   }
/*     */   
/*     */   public void renderPreview(class_332 context, int mouseX, int mouseY, class_9288 container) {
/* 239 */     renderPreview(context, mouseX, mouseY, container, (class_1799)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPreview(class_332 context, int mouseX, int mouseY, class_9288 container, class_1799 hoveredStack) {
/* 244 */     class_310 mc = class_310.method_1551();
/* 245 */     if (mc == null || mc.field_1755 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 249 */     int screenW = mc.field_1755.field_22789;
/* 250 */     int screenH = mc.field_1755.field_22790;
/*     */ 
/*     */     
/* 253 */     int x = mouseX + 14;
/* 254 */     if (x + 176 > screenW) {
/* 255 */       x = mouseX - 14 - 176;
/*     */     }
/* 257 */     int y = mouseY - 34;
/* 258 */     if (y < 4) y = 4; 
/* 259 */     if (y + 68 > screenH - 4) y = screenH - 4 - 68;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     context.method_51452();
/* 266 */     context.method_51448().method_22903();
/* 267 */     context.method_51448().method_46416(0.0F, 0.0F, 500.0F);
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
/* 280 */     int tintArgb = -1;
/* 281 */     if (this.colorByShulker.isValue()) {
/* 282 */       class_1767 dye = resolveShulkerColor(hoveredStack);
/* 283 */       if (dye != null) {
/* 284 */         int rgb = dye.method_7787();
/* 285 */         int rr = clampDyeChannel(rgb >> 16 & 0xFF);
/* 286 */         int gg = clampDyeChannel(rgb >> 8 & 0xFF);
/* 287 */         int bb = clampDyeChannel(rgb & 0xFF);
/* 288 */         tintArgb = 0xFF000000 | rr << 16 | gg << 8 | bb;
/* 289 */       } else if (isShulkerBox(hoveredStack)) {
/* 290 */         tintArgb = -6854761;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     context.method_52707(class_1921::method_62277, PANEL_SPRITE, x, y, 176, 68, tintArgb);
/*     */     
/* 302 */     context.method_51452();
/*     */ 
/*     */     
/* 305 */     int tick = (mc.field_1705 != null) ? mc.field_1705.method_1738() : 0;
/* 306 */     if (hoveredStack != lastHoveredStack || container != lastContainer || tick != lastTick) {
/* 307 */       lastHoveredStack = hoveredStack;
/* 308 */       lastContainer = container;
/* 309 */       lastTick = tick;
/* 310 */       int idx = 0;
/* 311 */       int cap = 27;
/*     */ 
/*     */ 
/*     */       
/* 315 */       Iterator<class_1799> iter = container.method_59712().iterator();
/* 316 */       while (iter.hasNext() && idx < cap) {
/* 317 */         class_1799 stack = iter.next();
/* 318 */         CACHED_STACKS[idx] = stack;
/* 319 */         CACHED_OVERLAYS[idx] = computeOverlayFlag(stack, mc);
/* 320 */         idx++;
/*     */       } 
/* 322 */       cachedSlotsToDraw = idx;
/* 323 */       for (int j = idx; j < cap; j++) {
/* 324 */         CACHED_STACKS[j] = null;
/* 325 */         CACHED_OVERLAYS[j] = false;
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     int gridOriginX = x + 8;
/* 330 */     int gridOriginY = y + 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     int hoveredSlot = -1;
/* 339 */     int relX = mouseX + 1 - x - 8;
/* 340 */     int relY = mouseY + 1 - y - 8;
/* 341 */     if (relX >= 0 && relY >= 0) {
/* 342 */       int sx = relX / 18;
/* 343 */       int sy = relY / 18;
/* 344 */       if (sx >= 0 && sx < 9 && sy >= 0 && sy < 3) {
/* 345 */         hoveredSlot = sy * 9 + sx;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     for (int i = 0; i < cachedSlotsToDraw; i++) {
/* 353 */       class_1799 stack = CACHED_STACKS[i];
/* 354 */       if (stack != null && !stack.method_7960()) {
/* 355 */         int row = i / 9;
/* 356 */         int col = i % 9;
/* 357 */         int slotX = gridOriginX + col * 18;
/* 358 */         int slotY = gridOriginY + row * 18;
/* 359 */         boolean highlighted = (i == hoveredSlot);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 367 */         if (highlighted) {
/* 368 */           context.method_52706(class_1921::method_62275, SLOT_HIGHLIGHT_BACK, slotX - 4, slotY - 4, 24, 24);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 374 */         context.method_51427(stack, slotX, slotY);
/* 375 */         if (CACHED_OVERLAYS[i]) {
/* 376 */           context.method_51431(mc.field_1772, stack, slotX, slotY);
/*     */         }
/*     */         
/* 379 */         if (highlighted) {
/* 380 */           context.method_52706(class_1921::method_62277, SLOT_HIGHLIGHT_FRONT, slotX - 4, slotY - 4, 24, 24);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 387 */     context.method_51452();
/* 388 */     context.method_51448().method_22909();
/*     */   }
/*     */   
/*     */   private static boolean computeOverlayFlag(class_1799 stack, class_310 mc) {
/* 392 */     if (stack == null || stack.method_7960()) return false; 
/* 393 */     if (stack.method_7947() != 1) return true; 
/* 394 */     if (stack.method_31578()) return true; 
/* 395 */     if (mc != null && mc.field_1724 != null && mc.field_1724
/* 396 */       .method_7357().method_7905(stack, 0.0F) > 0.0F) {
/* 397 */       return true;
/*     */     }
/* 399 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ShulkerPreview.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */