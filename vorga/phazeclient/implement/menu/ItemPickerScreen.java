/*     */ package vorga.phazeclient.implement.menu;
/*     */ 
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_6880;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ItemPickerSetting;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ public final class ItemPickerScreen extends class_437 implements QuickImports {
/*     */   private static final int SLOT_SIZE = 20;
/*     */   private static final int SLOT_ICON_SIZE = 16;
/*     */   private static final int GRID_COLUMNS = 9;
/*     */   private static final int STORAGE_ROWS = 3;
/*     */   private static final int HOTBAR_ROWS = 1;
/*     */   private static final int PANEL_PADDING = 14;
/*     */   private static final int HEADER_HEIGHT = 42;
/*     */   private static final int FOOTER_HEIGHT = 28;
/*     */   private static final int ROW_GAP = 4;
/*     */   private static final int SECTION_GAP = 7;
/*     */   private final class_437 parent;
/*     */   private final ItemPickerSetting targetSetting;
/*     */   private SlotHit hoveredSlot;
/*     */   private float panelX;
/*     */   private float panelY;
/*     */   private float panelWidth;
/*     */   private float panelHeight;
/*     */   
/*     */   public ItemPickerScreen(class_437 parent, ItemPickerSetting targetSetting) {
/*  45 */     super(class_2561.method_30163(Lang.translate("Choose Highlight Item")));
/*  46 */     this.parent = parent;
/*  47 */     this.targetSetting = targetSetting;
/*     */   }
/*     */   
/*     */   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
/*     */     String footerTitle, footerSubtitle;
/*  52 */     Theme.getInstance().applyMenuTheme();
/*  53 */     method_25420(context, mouseX, mouseY, delta);
/*     */     
/*  55 */     this.hoveredSlot = null;
/*     */     
/*  57 */     this.panelWidth = 208.0F;
/*  58 */     this.panelHeight = 193.0F;
/*     */     
/*  60 */     this.panelX = this.field_22789 / 2.0F - this.panelWidth / 2.0F;
/*  61 */     this.panelY = this.field_22790 / 2.0F - this.panelHeight / 2.0F;
/*     */     
/*  63 */     context.method_25294(0, 0, this.field_22789, this.field_22790, MenuStyle.detailScrim(0.92F));
/*     */     
/*  65 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.panelX, this.panelY, this.panelWidth, this.panelHeight)
/*  66 */         .round(7.0F)
/*  67 */         .thickness(1.2F)
/*  68 */         .outlineColor(MenuStyle.withAlpha(MenuStyle.BORDER_LIGHT, 0.92F))
/*  69 */         .color(MenuStyle.withAlpha(MenuStyle.PANEL_BG, 0.97F))
/*  70 */         .build());
/*     */     
/*  72 */     MsdfGuiFontRenderer msdfGuiFontRenderer1 = GuiFonts.getSize(15, Fonts.Type.INTER_BOLD);
/*  73 */     MsdfGuiFontRenderer msdfGuiFontRenderer2 = GuiFonts.getSize(11);
/*     */     
/*  75 */     float textX = this.panelX + 14.0F - 7.0F;
/*  76 */     int headerTitleColor = MenuStyle.mix(MenuStyle.TEXT_PRIMARY, -1, 0.55F);
/*  77 */     int headerSubtitleColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, -1, 0.4F);
/*  78 */     msdfGuiFontRenderer1.drawString(context.method_51448(), Lang.translate("Choose Highlight Item"), textX, (this.panelY + 11.0F), headerTitleColor);
/*  79 */     msdfGuiFontRenderer2.drawString(context
/*  80 */         .method_51448(), 
/*  81 */         Lang.translate("Click any inventory item to add it to Item Highlighter"), textX, (this.panelY + 25.0F), headerSubtitleColor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     class_1661 inventory = ((class_310.method_1551()).field_1724 != null) ? (class_310.method_1551()).field_1724.method_31548() : null;
/*     */     
/*  91 */     float gridX = this.panelX + 14.0F;
/*  92 */     float storageY = this.panelY + 42.0F + 14.0F - 2.0F;
/*  93 */     float hotbarY = storageY + 60.0F + 7.0F;
/*     */     
/*  95 */     if (inventory == null) {
/*  96 */       MsdfGuiFontRenderer msdfGuiFontRenderer = GuiFonts.getSize(12, Fonts.Type.INTER_BOLD);
/*  97 */       String line1 = Lang.translate("Join a world to choose an item");
/*  98 */       String line2 = Lang.translate("Click outside or press Esc to go back");
/*  99 */       msdfGuiFontRenderer.drawString(context.method_51448(), line1, (this.panelX + (this.panelWidth - msdfGuiFontRenderer.getStringWidth(line1)) / 2.0F), (this.panelY + this.panelHeight / 2.0F - 10.0F), MenuStyle.TEXT_PRIMARY);
/* 100 */       msdfGuiFontRenderer2.drawString(context.method_51448(), line2, (this.panelX + (this.panelWidth - msdfGuiFontRenderer2.getStringWidth(line2)) / 2.0F), (this.panelY + this.panelHeight / 2.0F + 4.0F), MenuStyle.TEXT_MUTED);
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     renderInventoryRows(context, mouseX, mouseY, inventory, gridX, storageY, 9, 27, true);
/* 105 */     renderInventoryRows(context, mouseX, mouseY, inventory, gridX, hotbarY, 0, 9, false);
/* 106 */     context.method_51452();
/*     */     
/* 108 */     float footerY = this.panelY + this.panelHeight - 28.0F;
/* 109 */     rectangle.render(ShapeProperties.create(context.method_51448(), (this.panelX + 1.0F), footerY, (this.panelWidth - 2.0F), 27.0D)
/* 110 */         .round(6.0F)
/* 111 */         .thickness(1.0F)
/* 112 */         .outlineColor(MenuStyle.withAlpha(MenuStyle.BORDER, 0.55F))
/* 113 */         .color(MenuStyle.withAlpha(MenuStyle.PANEL_CHIP, 0.78F))
/* 114 */         .build());
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (this.hoveredSlot != null && !this.hoveredSlot.stack.method_7960()) {
/* 119 */       footerTitle = this.hoveredSlot.stack.method_7964().getString();
/*     */ 
/*     */       
/* 122 */       footerSubtitle = (this.hoveredSlot.stack.method_7947() > 1) ? ("" + this.hoveredSlot.stack.method_7947() + "x") : Lang.translate("Click to choose item");
/* 123 */     } else if (this.targetSetting.hasSelection()) {
/* 124 */       footerTitle = Lang.translate("Current selection");
/* 125 */       footerSubtitle = this.targetSetting.getRowTitle();
/*     */     } else {
/* 127 */       footerTitle = Lang.translate("Open inventory picker");
/* 128 */       footerSubtitle = Lang.translate("Selected items are highlighted immediately");
/*     */     } 
/*     */     
/* 131 */     msdfGuiFontRenderer1 = GuiFonts.getSize(12, Fonts.Type.INTER_BOLD);
/* 132 */     msdfGuiFontRenderer2 = GuiFonts.getSize(10);
/* 133 */     msdfGuiFontRenderer1.drawString(context.method_51448(), trimToWidth((FontRenderer)msdfGuiFontRenderer1, footerTitle, this.panelWidth - 28.0F), textX, (footerY + 6.0F), MenuStyle.TEXT_PRIMARY);
/* 134 */     msdfGuiFontRenderer2.drawString(context.method_51448(), trimToWidth((FontRenderer)msdfGuiFontRenderer2, footerSubtitle, this.panelWidth - 28.0F), textX, (footerY + 16.0F), MenuStyle.TEXT_MUTED);
/*     */   }
/*     */   
/*     */   private void renderInventoryRows(class_332 context, int mouseX, int mouseY, class_1661 inventory, float startX, float startY, int startIndex, int slotCount, boolean storage) {
/* 138 */     int rows = storage ? 3 : 1;
/*     */     
/* 140 */     for (int row = 0; row < rows; row++) {
/* 141 */       for (int column = 0; column < 9; column++) {
/* 142 */         int slotIndex = startIndex + row * 9 + column;
/* 143 */         class_1799 stack = (class_1799)inventory.field_7547.get(slotIndex);
/* 144 */         float slotX = startX + (column * 20);
/* 145 */         float slotY = startY + (row * 20);
/* 146 */         boolean hovered = MathUtil.isHovered(mouseX, mouseY, slotX, slotY, 19.0D, 19.0D);
/* 147 */         boolean selectable = (stack != null && !stack.method_7960());
/* 148 */         boolean selected = (selectable && this.targetSetting.matchesSelectedItem(stack));
/*     */         
/* 150 */         float hoverAmount = hovered ? 1.0F : 0.0F;
/* 151 */         int slotBackground = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.settingSurface(false), MenuStyle.settingSurface(true), selected ? 0.42F : (hoverAmount * 0.14F)), 0.96F);
/* 152 */         int slotOutline = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.BORDER, this.targetSetting.getAccentColor() | 0xFF000000, selected ? 0.85F : (hoverAmount * 0.18F)), 0.92F);
/*     */         
/* 154 */         rectangle.render(ShapeProperties.create(context.method_51448(), slotX, slotY, 19.0D, 19.0D)
/* 155 */             .round(4.0F)
/* 156 */             .thickness(1.05F)
/* 157 */             .outlineColor(slotOutline)
/* 158 */             .color(slotBackground)
/* 159 */             .build());
/*     */         
/* 161 */         if (selectable) {
/* 162 */           context.method_51427(stack, Math.round(slotX + 1.0F), Math.round(slotY + 1.0F));
/*     */           
/* 164 */           context.method_51432((class_310.method_1551()).field_1772, stack, 
/* 165 */               Math.round(slotX + 1.0F), Math.round(slotY + 1.0F), "");
/* 166 */           if (stack.method_7947() > 1) {
/* 167 */             context.method_51452();
/* 168 */             MsdfGuiFontRenderer countFont = GuiFonts.getSize(16);
/* 169 */             String count = Integer.toString(stack.method_7947());
/* 170 */             float countX = slotX + 18.0F - countFont.getStringWidth(count);
/* 171 */             countFont.drawString(context.method_51448(), count, (countX + 0.6F), (slotY + 11.6F), -14671840);
/* 172 */             countFont.drawString(context.method_51448(), count, countX, (slotY + 11.0F), -1);
/*     */           } 
/*     */         } 
/*     */         
/* 176 */         if (hovered && selectable) {
/* 177 */           this.hoveredSlot = new SlotHit(slotX, slotY, slotIndex, stack);
/* 178 */           CursorManager.requestHand();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25402(double mouseX, double mouseY, int button) {
/* 186 */     if (button != 0) {
/* 187 */       return super.method_25402(mouseX, mouseY, button);
/*     */     }
/*     */     
/* 190 */     if (!MathUtil.isHovered(mouseX, mouseY, this.panelX, this.panelY, this.panelWidth, this.panelHeight)) {
/* 191 */       playClick();
/* 192 */       method_25419();
/* 193 */       return true;
/*     */     } 
/*     */     
/* 196 */     if (this.hoveredSlot != null && this.hoveredSlot.stack != null && !this.hoveredSlot.stack.method_7960()) {
/* 197 */       this.targetSetting.selectFromStack(this.hoveredSlot.stack);
/* 198 */       playClick();
/* 199 */       method_25419();
/* 200 */       return true;
/*     */     } 
/*     */     
/* 203 */     return super.method_25402(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25419() {
/* 208 */     if (this.field_22787 != null) {
/* 209 */       this.field_22787.method_1507(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25421() {
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {}
/*     */ 
/*     */   
/*     */   private void playClick() {
/* 223 */     class_310 client = class_310.method_1551();
/* 224 */     if (client != null && client.method_1483() != null) {
/* 225 */       client.method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, 1.0F));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String trimToWidth(FontRenderer font, String text, float maxWidth) {
/* 230 */     if (text == null || text.isEmpty()) {
/* 231 */       return "";
/*     */     }
/* 233 */     if (font.getStringWidth(text) <= maxWidth) {
/* 234 */       return text;
/*     */     }
/*     */     
/* 237 */     String ellipsis = "...";
/* 238 */     float ellipsisWidth = font.getStringWidth(ellipsis);
/* 239 */     StringBuilder builder = new StringBuilder();
/* 240 */     for (int i = 0; i < text.length(); i++) {
/* 241 */       char ch = text.charAt(i);
/* 242 */       if (font.getStringWidth(builder.toString() + builder.toString()) + ellipsisWidth > maxWidth) {
/*     */         break;
/*     */       }
/* 245 */       builder.append(ch);
/*     */     } 
/* 247 */     return builder.isEmpty() ? ellipsis : (String.valueOf(builder) + String.valueOf(builder));
/*     */   }
/*     */   private static final class SlotHit extends Record { private final float x; private final float y; private final int inventoryIndex; private final class_1799 stack;
/* 250 */     private SlotHit(float x, float y, int inventoryIndex, class_1799 stack) { this.x = x; this.y = y; this.inventoryIndex = inventoryIndex; this.stack = stack; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/ItemPickerScreen$SlotHit;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #250	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 250 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/ItemPickerScreen$SlotHit; } public float x() { return this.x; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/ItemPickerScreen$SlotHit;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #250	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/ItemPickerScreen$SlotHit; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/ItemPickerScreen$SlotHit;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #250	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/ItemPickerScreen$SlotHit;
/* 250 */       //   0	8	1	o	Ljava/lang/Object; } public float y() { return this.y; } public int inventoryIndex() { return this.inventoryIndex; } public class_1799 stack() { return this.stack; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\ItemPickerScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */