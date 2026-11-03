/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1935;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
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
/*     */ public final class Consumable
/*     */   extends RectHudModule
/*     */ {
/*  44 */   private static final Consumable INSTANCE = new Consumable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final Map<String, class_1792> ITEM_MAP = new LinkedHashMap<>();
/*     */   static {
/*  56 */     ITEM_MAP.put("Snowball", class_1802.field_8543);
/*  57 */     ITEM_MAP.put("Egg", class_1802.field_8803);
/*  58 */     ITEM_MAP.put("Wind Charge", class_1802.field_49098);
/*  59 */     ITEM_MAP.put("Golden Apple", class_1802.field_8463);
/*  60 */     ITEM_MAP.put("Enchanted Golden Apple", class_1802.field_8367);
/*  61 */     ITEM_MAP.put("Arrow", class_1802.field_8107);
/*  62 */     ITEM_MAP.put("Spectral Arrow", class_1802.field_8236);
/*  63 */     ITEM_MAP.put("Tipped Arrow", class_1802.field_8087);
/*  64 */     ITEM_MAP.put("Totem", class_1802.field_8288);
/*  65 */     ITEM_MAP.put("Chorus Fruit", class_1802.field_8233);
/*  66 */     ITEM_MAP.put("Ender Pearl", class_1802.field_8634);
/*  67 */     ITEM_MAP.put("Firework Rocket", class_1802.field_8639);
/*  68 */     ITEM_MAP.put("Experience Bottle", class_1802.field_8287);
/*     */   }
/*     */   
/*  71 */   public final SectionSetting itemsSection = new SectionSetting("Items");
/*     */   
/*  73 */   public final MultiSelectSetting itemTypes = (new MultiSelectSetting("Item Types", "Item types tracked by the HUD - icons appear only for those whose count is greater than zero"))
/*     */ 
/*     */     
/*  76 */     .value(new String[] { 
/*     */         "Snowball", "Egg", "Wind Charge", "Golden Apple", "Enchanted Golden Apple", "Arrow", "Spectral Arrow", "Tipped Arrow", "Totem", "Chorus Fruit",
/*     */         
/*     */         "Ender Pearl", "Firework Rocket", "Experience Bottle"
/*  80 */       }).selected(new String[] { "Golden Apple", "Enchanted Golden Apple", "Totem", "Ender Pearl" });
/*     */   
/*  82 */   public final SectionSetting displaySection = new SectionSetting("Display");
/*     */   
/*  84 */   public final SelectSetting layout = (new SelectSetting("Layout", "Whether icons stack horizontally, vertically, or in a square grid"))
/*     */ 
/*     */     
/*  87 */     .value(new String[] { "Line", "Column", "Table" }).selected("Line");
/*     */   
/*  89 */   public final BooleanSetting showCount = (new BooleanSetting("Show Count", "Draw the small stack-count number in the bottom-right corner of each icon"))
/*     */ 
/*     */     
/*  92 */     .setValue(true);
/*     */   
/*     */   public static Consumable getInstance() {
/*  95 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private Consumable() {
/*  99 */     super("consumable", "Consumable", ModuleCategory.HUD, 320.0F, 220.0F, 1.0F);
/* 100 */     this.itemsSection.setFullWidth(true);
/* 101 */     this.itemTypes.setFullWidth(true);
/* 102 */     this.displaySection.setFullWidth(true);
/* 103 */     this.layout.setFullWidth(true);
/* 104 */     this.showCount.setFullWidth(true);
/* 105 */     setup(new Setting[] { (Setting)this.itemsSection, (Setting)this.itemTypes, (Setting)this.displaySection, (Setting)this.layout, (Setting)this.showCount });
/*     */   } public static final class IconEntry extends Record {
/*     */     private final class_1799 stack; private final int row; private final float col;
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/hud/Consumable$IconEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #114	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/hud/Consumable$IconEntry;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/hud/Consumable$IconEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #114	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/hud/Consumable$IconEntry;
/*     */     }
/* 114 */     public IconEntry(class_1799 stack, int row, float col) { this.stack = stack; this.row = row; this.col = col; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/hud/Consumable$IconEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #114	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/hud/Consumable$IconEntry;
/* 114 */       //   0	8	1	o	Ljava/lang/Object; } public class_1799 stack() { return this.stack; } public int row() { return this.row; } public float col() { return this.col; }
/*     */      }
/*     */   public static final class Layout extends Record { private final List<Consumable.IconEntry> entries;
/*     */     private final int cols;
/*     */     private final int rows;
/*     */     private final float baseWidth;
/*     */     private final float baseHeight;
/*     */     
/* 122 */     public Layout(List<Consumable.IconEntry> entries, int cols, int rows, float baseWidth, float baseHeight) { this.entries = entries; this.cols = cols; this.rows = rows; this.baseWidth = baseWidth; this.baseHeight = baseHeight; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/hud/Consumable$Layout;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #122	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/hud/Consumable$Layout; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/hud/Consumable$Layout;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #122	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/hud/Consumable$Layout; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/hud/Consumable$Layout;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #122	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/hud/Consumable$Layout;
/* 122 */       //   0	8	1	o	Ljava/lang/Object; } public List<Consumable.IconEntry> entries() { return this.entries; } public int cols() { return this.cols; } public int rows() { return this.rows; } public float baseWidth() { return this.baseWidth; } public float baseHeight() { return this.baseHeight; }
/*     */      }
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
/*     */   public Layout computeLayout(boolean chatEditing) {
/*     */     int cols, rows;
/* 137 */     class_310 client = class_310.method_1551();
/* 138 */     List<IconEntry> entries = new ArrayList<>();
/*     */ 
/*     */     
/* 141 */     float padding = 3.0F;
/* 142 */     float baseItemSize = 16.0F;
/* 143 */     float itemSize = baseItemSize + 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     List<String> selected = this.itemTypes.getSelected();
/* 150 */     List<class_1799> presentStacks = new ArrayList<>();
/* 151 */     List<class_1799> previewStacks = new ArrayList<>();
/* 152 */     for (String type : selected) {
/* 153 */       class_1792 item = ITEM_MAP.get(type);
/* 154 */       if (item == null)
/* 155 */         continue;  int count = countItem(client, item);
/* 156 */       if (count > 0) {
/* 157 */         presentStacks.add(new class_1799((class_1935)item, count));
/*     */       }
/* 159 */       previewStacks.add(new class_1799((class_1935)item, 1));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     List<class_1799> visibleStacks = !presentStacks.isEmpty() ? presentStacks : (chatEditing ? previewStacks : new ArrayList<>());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (visibleStacks.isEmpty() && chatEditing) {
/* 170 */       visibleStacks.add(new class_1799((class_1935)class_1802.field_8463, 1));
/*     */     }
/*     */     
/* 173 */     int n = visibleStacks.size();
/* 174 */     if (n == 0) {
/* 175 */       return new Layout(entries, 0, 0, 0.0F, 0.0F);
/*     */     }
/*     */     
/* 178 */     switch (this.layout.getSelected()) { case "Line":
/* 179 */         cols = n; rows = 1; break;
/* 180 */       case "Column": cols = 1; rows = n; break;
/*     */       case "Table":
/* 182 */         cols = (int)Math.ceil(Math.sqrt(n));
/* 183 */         rows = (int)Math.ceil(n / cols); break;
/*     */       default:
/* 185 */         cols = n; rows = 1;
/*     */         break; }
/*     */     
/* 188 */     for (int i = 0; i < n; i++) {
/*     */       int r; int c; int itemsInRow;
/* 190 */       switch (this.layout.getSelected()) { case "Column":
/* 191 */           r = i; c = 0;
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
/* 207 */           entries.add(new IconEntry(visibleStacks.get(i), r, c)); break;case "Table": r = i / cols; itemsInRow = Math.min(cols, n - r * cols); c = i % cols; entries.add(new IconEntry(visibleStacks.get(i), r, c + (cols - itemsInRow) / 2.0F)); break;default: r = 0; c = i; entries.add(new IconEntry(visibleStacks.get(i), r, c)); break; }
/*     */     
/*     */     } 
/* 210 */     float width = cols * itemSize + padding * 2.0F;
/* 211 */     float height = rows * itemSize + padding * 2.0F;
/* 212 */     return new Layout(entries, cols, rows, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int countItem(class_310 client, class_1792 item) {
/* 222 */     if (client == null || client.field_1724 == null) {
/* 223 */       return 0;
/*     */     }
/* 225 */     int sum = 0;
/* 226 */     class_1661 inv = client.field_1724.method_31548();
/* 227 */     int size = inv.method_5439();
/* 228 */     for (int i = 0; i < size; i++) {
/* 229 */       class_1799 stack = inv.method_5438(i);
/* 230 */       if (!stack.method_7960() && stack.method_31574(item)) {
/* 231 */         sum += stack.method_7947();
/*     */       }
/*     */     } 
/* 234 */     return sum;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 239 */     return "Counts consumable items in your inventory and shows them as a draggable icon grid";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 244 */     return "consumable.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 249 */     return 21.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\Consumable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */