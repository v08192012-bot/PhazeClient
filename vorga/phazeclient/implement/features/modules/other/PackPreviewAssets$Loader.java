/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3262;
/*     */ import net.minecraft.class_3268;
/*     */ import net.minecraft.class_7367;
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
/*     */ final class Loader
/*     */ {
/*     */   private final class_310 client;
/*     */   private final String packId;
/*     */   private final class_3268 vanilla;
/*     */   private final BooleanSupplier cancelled;
/* 274 */   private final String texturePrefix = "pack_preview_assets/" + PackPreviewAssets.SERIAL.incrementAndGet() + "/";
/* 275 */   private final TreeMap<class_2960, class_7367<InputStream>> resources = new TreeMap<>(Comparator.comparing(class_2960::toString));
/* 276 */   private final Map<Integer, Integer> widths = new TreeMap<>();
/* 277 */   private final Set<String> measured = new HashSet<>();
/* 278 */   private final List<String> diagnostics = new ArrayList<>();
/* 279 */   private final List<class_2960> owned = new ArrayList<>();
/* 280 */   private final List<class_1011> images = new ArrayList<>();
/*     */   private class_3262 pack;
/*     */   private long customPixels;
/*     */   private long customBytesRead;
/*     */   private long textureBytes;
/*     */   private boolean animations;
/*     */   private boolean lightmap;
/*     */   private boolean sounds;
/*     */   
/*     */   private Loader(class_310 client, String packId, class_3268 vanilla, BooleanSupplier cancelled) {
/* 290 */     this.client = client;
/* 291 */     this.packId = packId;
/* 292 */     this.vanilla = vanilla;
/* 293 */     this.cancelled = cancelled;
/*     */   }
/*     */   
/*     */   private Loader(class_3262 pack) {
/* 297 */     this.client = null;
/* 298 */     this.packId = "feature-inspection";
/* 299 */     this.vanilla = null;
/* 300 */     this.cancelled = (() -> false);
/* 301 */     this.pack = pack;
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Prepared load() {
/* 305 */     scan();
/*     */     
/* 307 */     Map<String, PackPreviewAssets.Sprite> items = new LinkedHashMap<>();
/* 308 */     for (Map.Entry<String, String> entry : PackPreviewAssetLogic.itemPaths().entrySet()) {
/* 309 */       checkCancelled();
/* 310 */       PackPreviewAssets.Image image = preferred(class_2960.method_60656(entry.getValue()), true); 
/* 311 */       try { items.put(entry.getKey(), register(image));
/* 312 */         if (image != null) image.close();  } catch (Throwable throwable) { if (image != null)
/*     */           try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 314 */     }  List<PackPreviewAssets.Sprite> hearts = loadHearts();
/* 315 */     Map<String, PackPreviewAssets.Sprite> particles = new LinkedHashMap<>();
/* 316 */     for (String name : List.<String>of("critical_hit", "enchanted_hit")) {
/* 317 */       PackPreviewAssets.Image image = preferred(class_2960.method_60656("textures/particle/" + name + ".png"), false); 
/* 318 */       try { particles.put(name, register(image));
/* 319 */         if (image != null) image.close();  } catch (Throwable throwable) { if (image != null)
/*     */           try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 321 */     }  detectFeatures();
/* 322 */     List<List<PackPreviewAssets.Sprite>> blocks = new ArrayList<>();
/* 323 */     int changed = loadBlocks(blocks);
/* 324 */     measureOtherItems();
/* 325 */     checkCancelled();
/* 326 */     return new PackPreviewAssets.Prepared(new PackPreviewAssets.Data(this, blocks, items, hearts, particles, changed), this.images);
/*     */   }
/*     */   
/*     */   private void checkCancelled() {
/* 330 */     if (this.cancelled.getAsBoolean()) throw new CancellationException("Pack preview was invalidated"); 
/*     */   }
/*     */   
/*     */   private void note(String message) {
/* 334 */     if (this.diagnostics.contains(message)) {
/*     */       return;
/*     */     }
/* 337 */     if (message.contains("limited to") || message.contains("budget reached") || message.startsWith("Cycling retains")) {
/* 338 */       this.diagnostics.add(0, message);
/* 339 */       if (this.diagnostics.size() > 32) {
/* 340 */         this.diagnostics.removeLast();
/*     */       }
/* 342 */     } else if (this.diagnostics.size() < 32) {
/* 343 */       this.diagnostics.add(message);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void scan() {
/* 348 */     checkCancelled();
/* 349 */     if (this.pack == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 353 */       List<String> namespaces = this.pack.method_14406(PackPreviewAssets.TYPE).stream().sorted().toList();
/* 354 */       if (namespaces.size() > 256) {
/* 355 */         note("Namespace scan limited to 256");
/*     */       }
/* 357 */       for (Iterator<String> iterator = namespaces.subList(0, Math.min(namespaces.size(), 256)).iterator(); iterator.hasNext(); ) { String namespace = iterator.next();
/* 358 */         checkCancelled();
/*     */         
/*     */         try {
/* 361 */           class_3262.class_7664 collect = (id, supplier) -> {
/*     */               checkCancelled();
/*     */               
/*     */               if (!relevant(id.method_12832())) {
/*     */                 return;
/*     */               }
/*     */               
/*     */               if (this.resources.size() < 16384 || this.resources.containsKey(id)) {
/*     */                 this.resources.put(id, supplier);
/*     */               } else {
/*     */                 note("Resource index limited to 16384; feature detection may be partial");
/*     */                 
/*     */                 if (this.resources.comparator().compare(id, this.resources.lastKey()) < 0) {
/*     */                   this.resources.pollLastEntry();
/*     */                   this.resources.put(id, supplier);
/*     */                 } 
/*     */               } 
/*     */             };
/* 379 */           for (String prefix : List.<String>of("textures", "sounds", "optifine/lightmap", "mcpatcher/lightmap")) {
/* 380 */             checkCancelled();
/* 381 */             this.pack.method_14408(PackPreviewAssets.TYPE, namespace, prefix, collect);
/*     */           } 
/* 383 */           class_2960 soundsJson = class_2960.method_60655(namespace, "sounds.json");
/* 384 */           class_7367<InputStream> definitions = this.pack.method_14405(PackPreviewAssets.TYPE, soundsJson);
/* 385 */           if (definitions != null) {
/* 386 */             collect.accept(soundsJson, definitions);
/*     */           }
/* 388 */         } catch (CancellationException e) {
/* 389 */           throw e;
/* 390 */         } catch (RuntimeException e) {
/* 391 */           note("Partial namespace scan (" + namespace + "): " + e.getClass().getSimpleName());
/*     */         }  }
/*     */     
/* 394 */     } catch (CancellationException e) {
/* 395 */       throw e;
/* 396 */     } catch (RuntimeException e) {
/* 397 */       note("Partial resource scan: " + e.getClass().getSimpleName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean relevant(String path) {
/* 402 */     return (path.startsWith("textures/") || path.startsWith("sounds/") || path.equals("sounds.json") || path
/* 403 */       .startsWith("optifine/lightmap/") || path.startsWith("mcpatcher/lightmap/"));
/*     */   }
/*     */   
/*     */   private void destroy() {
/* 407 */     this.images.forEach(class_1011::close);
/* 408 */     this.images.clear();
/* 409 */     this.owned.clear();
/*     */   }
/*     */   
/*     */   private int loadBlocks(List<List<PackPreviewAssets.Sprite>> batches) {
/* 413 */     Map<String, class_2960> candidates = new TreeMap<>();
/* 414 */     for (class_2960 id : this.resources.keySet()) {
/* 415 */       checkCancelled();
/* 416 */       String path = PackPreviewAssetLogic.canonicalPath(id.method_12832());
/* 417 */       if (!path.startsWith("textures/block/") || !path.endsWith(".png")) {
/*     */         continue;
/*     */       }
/* 420 */       String key = id.method_12836() + ":" + id.method_12836();
/* 421 */       candidates.putIfAbsent(key, class_2960.method_60654(key));
/*     */     } 
/* 423 */     List<PackPreviewAssets.Image> selected = new ArrayList<>();
/* 424 */     int changed = 0;
/* 425 */     int inspected = 0;
/*     */     try {
/* 427 */       for (String key : PackPreviewAssetLogic.<Comparable>shuffled(new ArrayList<>(candidates.keySet()), this.packId)) {
/* 428 */         checkCancelled();
/* 429 */         if (inspected++ == 2048) {
/* 430 */           note("Block comparison limited to 2048 candidates");
/*     */           break;
/*     */         } 
/* 433 */         class_2960 id = candidates.get(key);
/* 434 */         PackPreviewAssets.Raw custom = customRaw(id); 
/* 435 */         try { boolean different; if (custom == null)
/*     */           
/*     */           { 
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
/* 453 */             if (custom != null) custom.close();  continue; }  rememberWidth(custom); class_2960 canonical = class_2960.method_60654(key); PackPreviewAssets.Raw base = raw(canonical, false); try { different = (base == null || (!Arrays.equals(custom.encoded, base.encoded) && !PackPreviewAssetLogic.samePixels(custom.pixels.method_4307(), custom.pixels.method_4323(), custom.pixels.method_61942(), base.pixels.method_4307(), base.pixels.method_4323(), base.pixels.method_61942()))); if (base != null) base.close();  } catch (Throwable throwable) { if (base != null) try { base.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (different) { changed++; if (selected.size() < 64) selected.add(frame(custom, true));  }  if (custom != null) custom.close();  } catch (Throwable different) { Throwable throwable; if (custom != null)
/*     */             try { custom.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 455 */       }  if (changed < 4) {
/*     */         
/* 457 */         List<PackPreviewAssets.Sprite> fixed = new ArrayList<>(4);
/* 458 */         for (String name : PackPreviewAssets.FIXED_BLOCKS) {
/* 459 */           PackPreviewAssets.Image image = fallback(class_2960.method_60656("textures/block/" + name + ".png")); 
/* 460 */           try { fixed.add(register(image));
/* 461 */             if (image != null) image.close();  } catch (Throwable throwable) { if (image != null)
/*     */               try { image.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 463 */         }  batches.add(fixed);
/*     */       } else {
/* 465 */         List<PackPreviewAssets.Sprite> sprites = new ArrayList<>();
/* 466 */         for (PackPreviewAssets.Image image : selected) {
/* 467 */           sprites.add(register(image));
/*     */         }
/* 469 */         batches.addAll(PackPreviewAssetLogic.blockBatches(sprites));
/* 470 */         if (changed > 64) {
/* 471 */           note("Cycling retains 64 of " + changed + " verified changed blocks");
/*     */         }
/*     */       } 
/*     */     } finally {
/* 475 */       selected.forEach(PackPreviewAssets.Image::close);
/*     */     } 
/* 477 */     return changed;
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Image preferred(class_2960 canonical, boolean item) {
/* 481 */     PackPreviewAssets.Raw raw = customRaw(canonical); 
/* 482 */     try { if (raw != null)
/* 483 */       { if (item) {
/* 484 */           rememberWidth(raw);
/*     */         }
/* 486 */         PackPreviewAssets.Image image = frame(raw, true);
/*     */         
/* 488 */         if (raw != null) raw.close();  return image; }  if (raw != null) raw.close();  } catch (Throwable throwable) { if (raw != null)
/* 489 */         try { raw.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return fallback(canonical);
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Raw customRaw(class_2960 canonical) {
/* 493 */     for (String path : PackPreviewAssetLogic.aliases(canonical.method_12832())) {
/* 494 */       PackPreviewAssets.Raw raw = raw(class_2960.method_60655(canonical.method_12836(), path), true);
/* 495 */       if (raw != null) {
/* 496 */         return raw;
/*     */       }
/*     */     } 
/* 499 */     return null;
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Image fallback(class_2960 id) {
/* 503 */     PackPreviewAssets.Raw raw = raw(id, false); 
/* 504 */     try { if (raw != null)
/* 505 */       { PackPreviewAssets.Image image1 = frame(raw, false);
/*     */         
/* 507 */         if (raw != null) raw.close();  return image1; }  if (raw != null) raw.close();  } catch (Throwable throwable) { if (raw != null)
/* 508 */         try { raw.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  note("Vanilla asset unavailable: " + String.valueOf(id) + "; generated placeholder");
/* 509 */     class_1011 image = new class_1011(16, 16, true);
/* 510 */     for (int y = 0; y < 16; y++) {
/* 511 */       for (int x = 0; x < 16; x++) {
/* 512 */         image.method_61941(x, y, ((x / 4 + y / 4 & 0x1) == 0) ? -65281 : -15395563);
/*     */       }
/*     */     } 
/* 515 */     return new PackPreviewAssets.Image(image, 16, 16, false);
/*     */   }
/*     */   
/*     */   private void rememberWidth(PackPreviewAssets.Raw raw) {
/* 519 */     String canonical = raw.id.method_12836() + ":" + raw.id.method_12836();
/* 520 */     if (this.measured.add(canonical)) {
/* 521 */       this.widths.merge(Integer.valueOf(raw.frame.width), Integer.valueOf(1), Integer::sum);
/*     */     }
/*     */   }
/*     */   
/*     */   private void measureOtherItems() {
/* 526 */     int count = 0;
/* 527 */     for (class_2960 id : this.resources.keySet()) {
/* 528 */       checkCancelled();
/* 529 */       String path = PackPreviewAssetLogic.canonicalPath(id.method_12832());
/* 530 */       if (path.startsWith("textures/item/") && path.endsWith(".png") && 
/* 531 */         !this.measured.contains(id.method_12836() + ":" + id.method_12836())) {
/* 532 */         if (count++ == 2048) {
/* 533 */           note("Item resolution scan limited to 2048 candidates");
/*     */           
/*     */           break;
/*     */         } 
/* 537 */         for (String alias : PackPreviewAssetLogic.aliases(path)) {
/* 538 */           PackPreviewAssets.Raw raw = raw(class_2960.method_60655(id.method_12836(), alias), true); 
/* 539 */           try { if (raw != null)
/* 540 */             { rememberWidth(raw);
/*     */ 
/*     */               
/* 543 */               if (raw != null) raw.close();  break; }  if (raw != null) raw.close();  } catch (Throwable throwable) { if (raw != null)
/*     */               try { raw.close(); }
/*     */               catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */                 throw throwable; }
/*     */         
/*     */         } 
/*     */       } 
/* 550 */     }  } private PackPreviewAssets.Raw raw(class_2960 id, boolean custom) { checkCancelled();
/* 551 */     if (custom && this.customPixels >= 67108864L) {
/* 552 */       note("Custom PNG decode budget reached (64 million pixels); detection/resolution may be partial");
/* 553 */       return null;
/*     */     } 
/* 555 */     byte[] bytes = bytes(id, custom, 8388608);
/* 556 */     if (bytes == null) {
/* 557 */       return null;
/*     */     }
/* 559 */     int[] size = PackPreviewAssets.pngSize(bytes);
/* 560 */     if (size == null) {
/* 561 */       note("Invalid or oversized PNG: " + String.valueOf(id));
/* 562 */       return null;
/*     */     } 
/* 564 */     long pixels = size[0] * size[1];
/* 565 */     if (custom && this.customPixels + pixels > 67108864L) {
/* 566 */       note("Custom PNG decode budget reached (64 million pixels); detection/resolution may be partial");
/* 567 */       return null;
/*     */     } 
/* 569 */     if (custom) {
/* 570 */       this.customPixels += pixels;
/*     */     }
/*     */     try {
/* 573 */       class_1011 image = class_1011.method_49277(bytes);
/*     */       try {
/* 575 */         checkCancelled();
/* 576 */         return new PackPreviewAssets.Raw(id, image, bytes, frameInfo(id, custom, image.method_4307(), image.method_4323()));
/* 577 */       } catch (RuntimeException|Error e) {
/* 578 */         image.close();
/* 579 */         throw e;
/*     */       } 
/* 581 */     } catch (CancellationException e) {
/* 582 */       throw e;
/* 583 */     } catch (IOException|RuntimeException e) {
/* 584 */       note("Unreadable PNG: " + String.valueOf(id));
/* 585 */       return null;
/*     */     }  }
/*     */ 
/*     */   
/*     */   private byte[] bytes(class_2960 id, boolean custom, int limit) {
/* 590 */     checkCancelled();
/* 591 */     class_3262 source = custom ? this.pack : (class_3262)this.vanilla;
/* 592 */     if (source == null) {
/* 593 */       return null;
/*     */     }
/* 595 */     long remaining = 134217728L - this.customBytesRead;
/* 596 */     if (custom && remaining <= 0L) {
/* 597 */       note("Custom resource read budget reached (128MiB); detection/resolution may be partial");
/* 598 */       return null;
/*     */     } 
/*     */     
/* 601 */     try { class_7367<InputStream> supplier = custom ? this.resources.get(id) : null;
/* 602 */       if (supplier == null) {
/* 603 */         supplier = source.method_14405(PackPreviewAssets.TYPE, id);
/*     */       }
/* 605 */       if (supplier == null) {
/* 606 */         return null;
/*     */       }
/* 608 */       InputStream input = (InputStream)supplier.get(); 
/* 609 */       try { int readLimit = custom ? (int)Math.min(limit, remaining) : limit;
/* 610 */         byte[] bytes = input.readNBytes(readLimit + 1);
/* 611 */         checkCancelled();
/* 612 */         if (custom) {
/* 613 */           this.customBytesRead += bytes.length;
/*     */         }
/* 615 */         if (bytes.length > readLimit)
/* 616 */         { if (readLimit < limit) {
/* 617 */             note("Custom resource read budget reached (128MiB); detection/resolution may be partial");
/*     */           } else {
/* 619 */             note("Resource exceeds byte limit: " + String.valueOf(id));
/*     */           } 
/* 621 */           byte[] arrayOfByte = null;
/*     */ 
/*     */           
/* 624 */           if (input != null) input.close();  return arrayOfByte; }  byte[] arrayOfByte1 = bytes; if (input != null) input.close();  return arrayOfByte1; } catch (Throwable throwable) { if (input != null)
/* 625 */           try { input.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (CancellationException e)
/* 626 */     { throw e; }
/* 627 */     catch (IOException|RuntimeException e)
/* 628 */     { note("Unreadable resource: " + String.valueOf(id));
/* 629 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   private JsonObject json(class_2960 id, boolean custom) {
/* 634 */     byte[] bytes = bytes(id, custom, 262144);
/* 635 */     if (bytes == null) {
/* 636 */       return null;
/*     */     }
/*     */     try {
/* 639 */       String text = new String(bytes, StandardCharsets.UTF_8);
/*     */ 
/*     */       
/* 642 */       JsonReader validation = new JsonReader(new StringReader(text)); 
/* 643 */       try { validation.setLenient(false);
/* 644 */         validation.skipValue();
/* 645 */         if (validation.peek() != JsonToken.END_DOCUMENT)
/* 646 */         { note("Invalid JSON: " + String.valueOf(id));
/* 647 */           JsonObject jsonObject = null;
/*     */           
/* 649 */           validation.close(); return jsonObject; }  validation.close(); } catch (Throwable throwable) { try { validation.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 650 */        JsonElement json = JsonParser.parseString(text);
/* 651 */       return json.isJsonObject() ? json.getAsJsonObject() : null;
/* 652 */     } catch (IOException|RuntimeException|StackOverflowError e) {
/* 653 */       note("Invalid JSON: " + String.valueOf(id));
/* 654 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Frame frameInfo(class_2960 id, boolean custom, int width, int height) {
/* 659 */     JsonObject metadata = json(class_2960.method_60655(id.method_12836(), id.method_12832() + ".mcmeta"), custom);
/* 660 */     JsonObject animation = PackPreviewAssets.animation(metadata);
/* 661 */     int frameWidth = width;
/* 662 */     int frameHeight = height;
/* 663 */     if (animation != null) {
/* 664 */       if (!PackPreviewAssets.validFrameGeometry(animation, width, height)) {
/* 665 */         note("Invalid animation frame geometry/index: " + String.valueOf(id));
/* 666 */         return new PackPreviewAssets.Frame(0, 0, width, height);
/*     */       } 
/* 668 */       if (animation.has("width")) {
/* 669 */         frameWidth = animation.get("width").getAsInt();
/* 670 */         frameHeight = animation.has("height") ? animation.get("height").getAsInt() : height;
/* 671 */       } else if (animation.has("height")) {
/* 672 */         frameHeight = animation.get("height").getAsInt();
/*     */       } else {
/* 674 */         frameWidth = frameHeight = Math.min(width, height);
/*     */       } 
/* 676 */       if (frameWidth > width || frameHeight > height || width % frameWidth != 0 || height % frameHeight != 0) {
/* 677 */         note("Invalid animation frame geometry: " + String.valueOf(id));
/* 678 */         return new PackPreviewAssets.Frame(0, 0, width, height);
/*     */       } 
/* 680 */       int first = 0;
/* 681 */       if (animation.has("frames") && !animation.getAsJsonArray("frames").isEmpty()) {
/* 682 */         JsonElement entry = animation.getAsJsonArray("frames").get(0);
/* 683 */         first = entry.isJsonObject() ? entry.getAsJsonObject().get("index").getAsInt() : entry.getAsInt();
/*     */       } 
/* 685 */       int columns = width / frameWidth;
/* 686 */       int frameCount = columns * height / frameHeight;
/* 687 */       if (first >= frameCount) {
/* 688 */         note("Animation frame index out of range: " + String.valueOf(id));
/* 689 */         first = 0;
/*     */       } 
/* 691 */       return new PackPreviewAssets.Frame(first % columns * frameWidth, first / columns * frameHeight, frameWidth, frameHeight);
/*     */     } 
/* 693 */     return new PackPreviewAssets.Frame(0, 0, frameWidth, frameHeight);
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Image frame(PackPreviewAssets.Raw raw, boolean custom) {
/* 697 */     PackPreviewAssets.Frame f = raw.frame;
/* 698 */     double scale = Math.min(1.0D, 256.0D / Math.max(f.width, f.height));
/* 699 */     int width = Math.max(1, (int)Math.round(f.width * scale));
/* 700 */     int height = Math.max(1, (int)Math.round(f.height * scale));
/* 701 */     class_1011 result = new class_1011(width, height, true);
/* 702 */     for (int y = 0; y < height; y++) {
/* 703 */       for (int x = 0; x < width; x++) {
/* 704 */         result.method_61941(x, y, raw.pixels.method_61940(f.x + x * f.width / width, f.y + y * f.height / height));
/*     */       }
/*     */     } 
/* 707 */     return new PackPreviewAssets.Image(result, f.width, f.height, custom);
/*     */   }
/*     */ 
/*     */   
/*     */   private PackPreviewAssets.Sprite register(PackPreviewAssets.Image image) {
/* 712 */     class_1011 copy = new class_1011(image.pixels.method_4307(), image.pixels.method_4323(), true);
/* 713 */     class_2960 id = class_2960.method_60655("phaze", this.texturePrefix + this.texturePrefix);
/*     */     try {
/* 715 */       copy.method_4317(image.pixels);
/* 716 */       this.images.add(copy);
/* 717 */       this.owned.add(id);
/* 718 */       this.textureBytes += copy.method_4307() * copy.method_4323() * 4L;
/* 719 */       return new PackPreviewAssets.Sprite(id, copy.method_4307(), copy.method_4323(), image.originalWidth, image.originalHeight, image.custom);
/* 720 */     } catch (RuntimeException|Error e) {
/* 721 */       copy.close();
/* 722 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<PackPreviewAssets.Sprite> loadHearts() {
/* 727 */     List<PackPreviewAssets.Sprite> result = new ArrayList<>(3);
/* 728 */     PackPreviewAssets.Image container = heartLayer("container", 16); 
/* 729 */     try { PackPreviewAssets.Image full = heartLayer("full", 52); 
/* 730 */       try { PackPreviewAssets.Image half = heartLayer("half", 61); 
/* 731 */         try { PackPreviewAssets.Image composed = compose(container, full); 
/* 732 */           try { result.add(register(composed));
/* 733 */             if (composed != null) composed.close();  } catch (Throwable throwable) { if (composed != null)
/* 734 */               try { composed.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  composed = compose(container, half); 
/* 735 */           try { result.add(register(composed));
/* 736 */             if (composed != null) composed.close();  } catch (Throwable throwable) { if (composed != null)
/* 737 */               try { composed.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  result.add(register(container));
/* 738 */           if (half != null) half.close();  } catch (Throwable throwable) { if (half != null) try { half.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (full != null) full.close();  } catch (Throwable throwable) { if (full != null) try { full.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (container != null) container.close();  } catch (Throwable throwable) { if (container != null)
/* 739 */         try { container.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return result;
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Image heartLayer(String name, int legacyX) {
/* 743 */     class_2960 modern = class_2960.method_60656("textures/gui/sprites/hud/heart/" + name + ".png");
/* 744 */     PackPreviewAssets.Raw custom = raw(modern, true); 
/* 745 */     try { if (custom != null)
/* 746 */       { PackPreviewAssets.Image image = frame(custom, true);
/*     */         
/* 748 */         if (custom != null) custom.close();  return image; }  if (custom != null) custom.close();  } catch (Throwable throwable) { if (custom != null)
/*     */         try { custom.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 750 */      PackPreviewAssets.Raw atlas = raw(class_2960.method_60656("textures/gui/icons.png"), true); 
/* 751 */     try { if (atlas != null && atlas.pixels.method_4307() % 256 == 0 && atlas.pixels.method_4323() % 256 == 0)
/* 752 */       { int sx = atlas.pixels.method_4307() / 256;
/* 753 */         int sy = atlas.pixels.method_4323() / 256;
/* 754 */         PackPreviewAssets.Raw crop = new PackPreviewAssets.Raw(atlas.id, atlas.pixels, atlas.encoded, new PackPreviewAssets.Frame(legacyX * sx, 0, 9 * sx, 9 * sy));
/* 755 */         PackPreviewAssets.Image image = frame(crop, true);
/*     */         
/* 757 */         if (atlas != null) atlas.close();  return image; }  if (atlas != null) atlas.close();  } catch (Throwable throwable) { if (atlas != null)
/* 758 */         try { atlas.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return fallback(modern);
/*     */   }
/*     */   
/*     */   private PackPreviewAssets.Image compose(PackPreviewAssets.Image container, PackPreviewAssets.Image fill) {
/* 762 */     int width = Math.max(container.pixels.method_4307(), fill.pixels.method_4307());
/* 763 */     int height = Math.max(container.pixels.method_4323(), fill.pixels.method_4323());
/* 764 */     class_1011 result = new class_1011(width, height, true);
/* 765 */     for (int y = 0; y < height; y++) {
/* 766 */       for (int x = 0; x < width; x++) {
/* 767 */         int background = container.pixels.method_61940(x * container.pixels.method_4307() / width, y * container.pixels
/* 768 */             .method_4323() / height);
/* 769 */         int foreground = fill.pixels.method_61940(x * fill.pixels.method_4307() / width, y * fill.pixels
/* 770 */             .method_4323() / height);
/* 771 */         result.method_61941(x, y, PackPreviewAssetLogic.over(background, foreground));
/*     */       } 
/*     */     } 
/* 774 */     return new PackPreviewAssets.Image(result, Math.max(container.originalWidth, fill.originalWidth), 
/* 775 */         Math.max(container.originalHeight, fill.originalHeight), (container.custom || fill.custom));
/*     */   }
/*     */   
/*     */   private void detectFeatures() {
/* 779 */     int metadataCount = 0;
/* 780 */     for (class_2960 id : this.resources.keySet()) {
/* 781 */       checkCancelled();
/* 782 */       String path = id.method_12832();
/* 783 */       if (!this.sounds && path.startsWith("sounds/") && path.endsWith(".ogg")) {
/* 784 */         byte[] header = header(id, 4);
/* 785 */         this.sounds = (header != null && Arrays.equals(header, new byte[] { 79, 103, 103, 83 }));
/*     */       } 
/* 787 */       if (!this.sounds && path.equals("sounds.json")) {
/* 788 */         this.sounds = PackPreviewAssets.soundDefinitions(json(id, true));
/*     */       }
/* 790 */       if (!this.lightmap && path.endsWith(".png") && (path.startsWith("optifine/lightmap/") || path
/* 791 */         .startsWith("mcpatcher/lightmap/") || path.equals("textures/misc/lightmap.png"))) {
/* 792 */         this.lightmap = (PackPreviewAssets.pngSize(header(id, 24)) != null);
/*     */       }
/* 794 */       if (!this.animations && path.startsWith("textures/") && path.endsWith(".png.mcmeta")) {
/* 795 */         if (metadataCount++ >= 2048) {
/* 796 */           note("Animation metadata scan limited to 2048");
/*     */           continue;
/*     */         } 
/* 799 */         class_2960 png = class_2960.method_60655(id.method_12836(), path.substring(0, path.length() - 7));
/* 800 */         int[] size = PackPreviewAssets.pngSize(header(png, 24));
/* 801 */         if (size != null) {
/* 802 */           JsonObject anim = PackPreviewAssets.animation(json(id, true));
/* 803 */           this.animations = (anim != null && PackPreviewAssets.validFrameGeometry(anim, size[0], size[1]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] header(class_2960 id, int size) {
/* 810 */     checkCancelled();
/* 811 */     if (this.pack == null) {
/* 812 */       return null;
/*     */     }
/*     */     try {
/* 815 */       class_7367<InputStream> supplier = this.resources.get(id);
/* 816 */       if (supplier == null) {
/* 817 */         supplier = this.pack.method_14405(PackPreviewAssets.TYPE, id);
/*     */       }
/* 819 */       if (supplier != null)
/* 820 */       { InputStream stream = (InputStream)supplier.get(); 
/* 821 */         try { byte[] bytes = stream.readNBytes(size);
/* 822 */           checkCancelled();
/* 823 */           byte[] arrayOfByte1 = bytes;
/* 824 */           if (stream != null) stream.close();  return arrayOfByte1; } catch (Throwable throwable) { if (stream != null)
/*     */             try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } 
/* 826 */     } catch (CancellationException e) {
/* 827 */       throw e;
/* 828 */     } catch (IOException|RuntimeException e) {
/* 829 */       note("Unreadable resource header: " + String.valueOf(id));
/*     */     } 
/* 831 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\PackPreviewAssets$Loader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */