/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ 
/*     */ public final class PackPreviewRenderer
/*     */ {
/*     */   private static final int HEIGHT = 44;
/*     */   private static final int ICON_SIZE = 16;
/*     */   private static final int SLOT = 18;
/*  14 */   private static final String[] BUCKETS = new String[] { "bucket", "lava_bucket", "water_bucket" };
/*  15 */   private static final String[] BOWS = new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" };
/*  16 */   private static final String[] TOOLS = new String[] { "iron_sword", "iron_pickaxe", "diamond_sword", "diamond_pickaxe", "totem_of_undying" };
/*  17 */   private static final PackPreviewCache CACHE = new PackPreviewCache();
/*     */   
/*     */   private static String hoveredPack;
/*     */   
/*     */   private static long hoveredAtNanos;
/*     */   
/*     */   private static long lastRenderedNanos;
/*     */   
/*     */   public static void clear(class_310 client) {
/*  26 */     CACHE.clear();
/*  27 */     hoveredPack = null;
/*  28 */     hoveredAtNanos = 0L;
/*  29 */     lastRenderedNanos = 0L;
/*     */   }
/*     */   
/*     */   public static void beginFrame() {
/*  33 */     CACHE.beginFrame();
/*     */   }
/*     */   
/*     */   public static boolean render(class_332 context, String packId, int x, int y, int width, int height) {
/*  37 */     if (packId == null || packId.isEmpty() || width <= 0 || height <= 0) {
/*  38 */       return false;
/*     */     }
/*  40 */     PackPreviewAssets.Data data = CACHE.get(packId);
/*  41 */     if (data == null) return false; 
/*  42 */     class_310 client = class_310.method_1551();
/*  43 */     long now = System.nanoTime();
/*  44 */     if (!packId.equals(hoveredPack) || now - lastRenderedNanos > 500000000L) {
/*  45 */       hoveredPack = packId;
/*  46 */       hoveredAtNanos = now;
/*     */     } 
/*  48 */     lastRenderedNanos = now;
/*  49 */     long elapsed = Math.max(0L, (now - hoveredAtNanos) / 1000000L);
/*  50 */     String resolution = (data.resolution() > 0) ? ("" + data.resolution() + "x") : "—";
/*  51 */     int dividerX = 181;
/*  52 */     int textX = dividerX + 5;
/*  53 */     int infoWidth = Math.max(client.field_1772.method_1727("Res: " + resolution), client.field_1772
/*  54 */         .method_1727("Animations: ✓"));
/*  55 */     infoWidth = Math.max(infoWidth, client.field_1772.method_1727("Lightmap: ✓"));
/*  56 */     infoWidth = Math.max(infoWidth, client.field_1772.method_1727("Sounds: ✓"));
/*  57 */     float scale = Math.min(width / (textX + infoWidth + 1), height / 44.0F);
/*     */     
/*  59 */     context.method_51448().method_22903();
/*     */     try {
/*  61 */       context.method_51448().method_46416(x, y + (height - 44.0F * scale) / 2.0F, 0.0F);
/*  62 */       context.method_51448().method_22905(scale, scale, 1.0F);
/*  63 */       List<PackPreviewAssets.Sprite> blocks = data.blocksAt(elapsed); int i;
/*  64 */       for (i = 0; i < 4; i++) {
/*  65 */         drawIcon(context, blocks.get(i), i * 18, 3);
/*     */       }
/*  67 */       drawIcon(context, data.items().get(BUCKETS[PackPreviewAssetLogic.batchIndex(elapsed, BUCKETS.length)]), 72, 3);
/*  68 */       drawIcon(context, data.items().get("golden_apple"), 90, 3);
/*  69 */       drawIcon(context, data.items().get("ender_pearl"), 108, 3);
/*  70 */       drawIcon(context, data.items().get(BOWS[PackPreviewAssetLogic.batchIndex(elapsed, BOWS.length)]), 126, 3);
/*  71 */       for (i = 0; i < TOOLS.length; i++) {
/*  72 */         drawIcon(context, data.items().get(TOOLS[i]), i * 18, 25);
/*     */       }
/*  74 */       for (i = 0; i < 3; i++) {
/*  75 */         drawIcon(context, data.hearts().get(i), (5 + i) * 18, 25);
/*     */       }
/*  77 */       drawIcon(context, data.particles().get("critical_hit"), 144, 25);
/*  78 */       drawIcon(context, data.particles().get("enchanted_hit"), 162, 25);
/*  79 */       context.method_25294(dividerX, 1, dividerX + 1, 43, -1);
/*  80 */       drawStatus(context, "Res: ", resolution, textX, 1, -9312885);
/*  81 */       drawStatus(context, "Animations: ", data.animations() ? "✓" : "✕", textX, 12, 
/*  82 */           data.animations() ? -11933575 : -45747);
/*  83 */       drawStatus(context, "Lightmap: ", data.lightmap() ? "✓" : "✕", textX, 23, 
/*  84 */           data.lightmap() ? -11933575 : -45747);
/*  85 */       drawStatus(context, "Sounds: ", data.sounds() ? "✓" : "✕", textX, 34, 
/*  86 */           data.sounds() ? -11933575 : -45747);
/*     */     } finally {
/*  88 */       context.method_51448().method_22909();
/*     */     } 
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   private static void drawStatus(class_332 context, String label, String value, int x, int y, int color) {
/*  94 */     class_327 font = (class_310.method_1551()).field_1772;
/*  95 */     context.method_51433(font, label, x, y, -1, false);
/*  96 */     context.method_51433(font, value, x + font.method_1727(label), y, color, false);
/*     */   }
/*     */   
/*     */   private static void drawIcon(class_332 context, PackPreviewAssets.Sprite sprite, int x, int y) {
/* 100 */     context.method_25302(class_1921::method_62277, sprite.id(), x, y, 0.0F, 0.0F, 16, 16, sprite
/* 101 */         .width(), sprite.height(), sprite.width(), sprite.height());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */