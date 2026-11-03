/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PackPreviewAssetLogic
/*     */ {
/*     */   static String canonicalPath(String path) {
/*  17 */     path = path.replace("textures/blocks/", "textures/block/").replace("textures/items/", "textures/item/");
/*  18 */     switch (path) { case "textures/item/bow_standby.png": case "textures/item/totem.png": case "textures/block/log_oak.png": case "textures/block/grass_top.png":  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  23 */       path;
/*     */   }
/*     */ 
/*     */   
/*     */   static List<String> aliases(String canonical) {
/*  28 */     List<String> paths = new ArrayList<>();
/*  29 */     paths.add(canonical);
/*  30 */     paths.add(canonical.replace("textures/block/", "textures/blocks/")
/*  31 */         .replace("textures/item/", "textures/items/"));
/*  32 */     switch (canonical) { case "textures/item/bow.png": 
/*     */       case "textures/item/totem_of_undying.png": 
/*     */       case "textures/block/oak_log.png": 
/*     */       case "textures/block/grass_block_top.png": 
/*     */       case "textures/particle/critical_hit.png": 
/*     */       case "textures/particle/enchanted_hit.png": 
/*     */       default:
/*  39 */         break; }  String legacy = null;
/*     */     
/*  41 */     if (legacy != null) {
/*  42 */       paths.add(legacy);
/*  43 */       paths.add(legacy.replace("textures/items/", "textures/item/")
/*  44 */           .replace("textures/blocks/", "textures/block/"));
/*     */     } 
/*  46 */     return paths.stream().distinct().toList();
/*     */   }
/*     */   
/*     */   static <T extends Comparable<? super T>> List<T> shuffled(List<T> values, String packId) {
/*  50 */     List<T> result = new ArrayList<>(values);
/*  51 */     Collections.sort(result);
/*  52 */     Collections.shuffle(result, new Random(packId.hashCode()));
/*  53 */     return result;
/*     */   }
/*     */   
/*     */   static int batchIndex(long elapsedMillis, int batchCount) {
/*  57 */     return (int)Math.floorMod(Math.floorDiv(elapsedMillis, 1500L), batchCount);
/*     */   }
/*     */   
/*     */   static <T> List<List<T>> blockBatches(List<T> sprites) {
/*  61 */     if (sprites.size() < 4) {
/*  62 */       return List.of();
/*     */     }
/*  64 */     List<List<T>> batches = new ArrayList<>();
/*     */ 
/*     */     
/*  67 */     for (int i = 0; i < sprites.size(); i++) {
/*  68 */       List<T> batch = new ArrayList<>(4);
/*  69 */       for (int j = 0; j < 4; j++) {
/*  70 */         batch.add(sprites.get((i + j) % sprites.size()));
/*     */       }
/*  72 */       batches.add(List.copyOf(batch));
/*     */     } 
/*  74 */     return List.copyOf(batches);
/*     */   }
/*     */ 
/*     */   
/*     */   static int resolution(Map<Integer, Integer> widths) {
/*  79 */     int result = 0;
/*  80 */     int count = 0;
/*  81 */     for (Map.Entry<Integer, Integer> entry : widths.entrySet()) {
/*  82 */       if (((Integer)entry.getValue()).intValue() > count || (((Integer)entry.getValue()).intValue() == count && ((Integer)entry.getKey()).intValue() < result)) {
/*  83 */         result = ((Integer)entry.getKey()).intValue();
/*  84 */         count = ((Integer)entry.getValue()).intValue();
/*     */       } 
/*     */     } 
/*  87 */     return result;
/*     */   }
/*     */   
/*     */   static boolean samePixels(int width, int height, int[] a, int otherWidth, int otherHeight, int[] b) {
/*  91 */     if (width != otherWidth || height != otherHeight || a.length != b.length) {
/*  92 */       return false;
/*     */     }
/*  94 */     for (int i = 0; i < a.length; i++) {
/*     */       
/*  96 */       if (visible(a[i]) != visible(b[i])) {
/*  97 */         return false;
/*     */       }
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   private static int visible(int argb) {
/* 104 */     return (argb >>> 24 == 0) ? 0 : argb;
/*     */   }
/*     */ 
/*     */   
/*     */   static int over(int background, int foreground) {
/* 109 */     int fa = foreground >>> 24;
/* 110 */     int ba = background >>> 24;
/* 111 */     int alphaNumerator = fa * 255 + ba * (255 - fa);
/* 112 */     if (alphaNumerator == 0) {
/* 113 */       return 0;
/*     */     }
/* 115 */     int result = (alphaNumerator + 127) / 255 << 24;
/* 116 */     for (int shift = 16; shift >= 0; shift -= 8) {
/* 117 */       int f = foreground >>> shift & 0xFF;
/* 118 */       int b = background >>> shift & 0xFF;
/* 119 */       int channel = (f * fa * 255 + b * ba * (255 - fa) + alphaNumerator / 2) / alphaNumerator;
/* 120 */       result |= channel << shift;
/*     */     } 
/* 122 */     return result;
/*     */   }
/*     */   
/*     */   static Map<String, String> itemPaths() {
/* 126 */     Map<String, String> paths = new LinkedHashMap<>();
/* 127 */     for (String item : List.<String>of(new String[] { "bucket", "lava_bucket", "water_bucket", "golden_apple", "ender_pearl", "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2", "iron_sword", "iron_pickaxe", "diamond_sword", "diamond_pickaxe", "totem_of_undying"
/*     */         }))
/*     */     {
/* 130 */       paths.put(item, "textures/item/" + item + ".png");
/*     */     }
/* 132 */     return paths;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewAssetLogic.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */