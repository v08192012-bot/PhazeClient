/*      */ package vorga.phazeclient.implement.features.modules.other;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
/*      */ import net.minecraft.class_1923;
/*      */ import net.minecraft.class_2338;
/*      */ import net.minecraft.class_2806;
/*      */ import net.minecraft.class_2818;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_4076;
/*      */ import net.minecraft.class_638;
/*      */ import vorga.phazeclient.api.feature.module.Module;
/*      */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*      */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*      */ import vorga.phazeclient.base.util.animation.Interpolation;
/*      */ import vorga.phazeclient.base.util.animation.Interpolations;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ChunkAnimator
/*      */   extends Module
/*      */ {
/*   47 */   private static final ChunkAnimator INSTANCE = new ChunkAnimator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_TRACKED = 100000;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   private final Map<Long, Long> firstSeenMs = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   90 */   private final Set<Long> pendingAnimationKeys = ConcurrentHashMap.newKeySet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private final Map<Long, Float> playerYAtRegister = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   private volatile long lastAnimRegisterMs = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasActiveAnimations() {
/*  152 */     if (!isEnabled()) {
/*  153 */       return false;
/*      */     }
/*  155 */     long last = this.lastAnimRegisterMs;
/*  156 */     if (last == 0L) {
/*  157 */       return false;
/*      */     }
/*  159 */     return (System.currentTimeMillis() - last <= this.duration.getInt());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isColumnAnimating(class_2338 position) {
/*  170 */     if (!isEnabled() || position == null) {
/*  171 */       return false;
/*      */     }
/*  173 */     long key = class_1923.method_8331(position.method_10263() >> 4, position.method_10260() >> 4);
/*      */ 
/*      */     
/*  176 */     if (this.pendingAnimationKeys.contains(Long.valueOf(key))) {
/*  177 */       return true;
/*      */     }
/*  179 */     Long startedAt = this.firstSeenMs.get(Long.valueOf(key));
/*  180 */     if (startedAt != null && 
/*  181 */       System.currentTimeMillis() - startedAt.longValue() < Math.max(1L, this.duration.getInt())) {
/*  182 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     int chunkX = position.method_10263() >> 4;
/*  189 */     int sectionY = position.method_10264() >> 4;
/*  190 */     int chunkZ = position.method_10260() >> 4;
/*  191 */     long regionKey = class_4076.method_18685(
/*  192 */         Math.floorDiv(chunkX, 8) * 8, 
/*  193 */         Math.floorDiv(sectionY, 4) * 4, 
/*  194 */         Math.floorDiv(chunkZ, 8) * 8);
/*      */     
/*  196 */     Long regionStartedAt = this.regionFirstSeenMs.get(Long.valueOf(regionKey));
/*  197 */     if (regionStartedAt != null && 
/*  198 */       System.currentTimeMillis() - regionStartedAt.longValue() < Math.max(1L, this.duration.getInt())) {
/*  199 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  208 */     return hasActiveAnimations();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   private final Map<Long, Long> regionFirstSeenMs = new ConcurrentHashMap<>();
/*      */   
/*  239 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  240 */   public final ValueSetting duration = (new ValueSetting("Duration", "Animation length in milliseconds. Lower = snappier."))
/*      */ 
/*      */     
/*  243 */     .range(100, 5000).step(50.0F).setValue(600.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  263 */   public final SelectSetting animationType = (new SelectSetting("Animation Type", "How chunks enter view. Top/Bottom/Side slide chunks in from a direction. Fade dissolves chunks via alpha (Sodium only). Scale grows each section out from its centre (Sodium only - shader patch required)."))
/*      */ 
/*      */     
/*  266 */     .value(new String[] { "Top", "Bottom", "Side", "Fade", "Scale" }).selected("Bottom");
/*  267 */   public final ValueSetting distance = (new ValueSetting("Distance", "How many blocks chunks travel before reaching their final position."))
/*      */ 
/*      */     
/*  270 */     .range(8, 256).step(1.0F).setValue(64.0F)
/*      */ 
/*      */     
/*  273 */     .visible(() -> Boolean.valueOf((!this.animationType.isSelected("Fade") && !this.animationType.isSelected("Scale"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  284 */   public final SelectSetting directionSide = (new SelectSetting("Side", "Cardinal direction chunks slide in from when Animation Type is Side."))
/*      */ 
/*      */     
/*  287 */     .value(new String[] { "North", "South", "East", "West" }).selected("South")
/*  288 */     .visible(() -> Boolean.valueOf(this.animationType.isSelected("Side")));
/*  289 */   public final SelectSetting easing = (new SelectSetting("Easing", "Easing curve for the slide. Decelerate gives a soft landing; Bounce / Elastic add an overshoot."))
/*      */ 
/*      */     
/*  292 */     .value(Interpolations.getAllNames()).selected("Decelerate")
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  297 */     .visible(() -> Boolean.valueOf((!this.animationType.isSelected("Fade") && !this.animationType.isSelected("Scale"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  327 */   public final SelectSetting fadeStyle = (new SelectSetting("Fade Style", "Fog Mix: smooth blend toward fog-like distance colour. Applies only when Animation Type is Fade."))
/*      */ 
/*      */     
/*  330 */     .value(new String[] { "Fog Mix" }).selected("Fog Mix")
/*  331 */     .visible(() -> Boolean.valueOf(this.animationType.isSelected("Fade")));
/*      */   
/*      */   private ChunkAnimator() {
/*  334 */     super("chunk_animator", "World Animator", ModuleCategory.OTHER);
/*  335 */     this.duration.setFullWidth(true);
/*  336 */     this.distance.setFullWidth(true);
/*  337 */     this.animationType.setFullWidth(true);
/*  338 */     this.directionSide.setFullWidth(true);
/*  339 */     this.easing.setFullWidth(true);
/*  340 */     this.fadeStyle.setFullWidth(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  347 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.duration, (Setting)this.distance, (Setting)this.animationType, (Setting)this.directionSide, (Setting)this.easing, (Setting)this.fadeStyle });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  359 */     ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
/*      */           if (!isEnabled()) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*      */           long key = class_1923.method_8331((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180);
/*      */           
/*      */           this.firstSeenMs.remove(Long.valueOf(key));
/*      */           
/*      */           this.playerYAtRegister.remove(Long.valueOf(key));
/*      */           
/*      */           this.pendingAnimationKeys.add(Long.valueOf(key));
/*      */         });
/*      */     
/*  374 */     ClientChunkEvents.CHUNK_UNLOAD.register((world, chunk) -> {
/*      */           long key = class_1923.method_8331((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180);
/*      */           this.firstSeenMs.remove(Long.valueOf(key));
/*      */           this.playerYAtRegister.remove(Long.valueOf(key));
/*      */           this.pendingAnimationKeys.remove(Long.valueOf(key));
/*      */         });
/*      */   }
/*      */   
/*      */   public static ChunkAnimator getInstance() {
/*  383 */     return INSTANCE;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  388 */     return "Animates loaded chunks sliding smoothly into place from below or from a chosen direction";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeAnimationDirection(float[] out) {
/*  419 */     if (out == null || out.length < 3) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  429 */     if (this.animationType.isSelected("Fade") || this.animationType.isSelected("Scale")) {
/*  430 */       out[0] = 0.0F; out[1] = 0.0F; out[2] = 0.0F;
/*      */       return;
/*      */     } 
/*  433 */     if (this.animationType.isSelected("Top")) {
/*      */ 
/*      */       
/*  436 */       out[0] = 0.0F; out[1] = 1.0F; out[2] = 0.0F;
/*      */       return;
/*      */     } 
/*  439 */     if (this.animationType.isSelected("Side")) {
/*  440 */       String side = this.directionSide.getSelected();
/*  441 */       if ("North".equalsIgnoreCase(side)) {
/*  442 */         out[0] = 0.0F; out[1] = 0.0F; out[2] = -1.0F;
/*      */         return;
/*      */       } 
/*  445 */       if ("South".equalsIgnoreCase(side)) {
/*  446 */         out[0] = 0.0F; out[1] = 0.0F; out[2] = 1.0F;
/*      */         return;
/*      */       } 
/*  449 */       if ("East".equalsIgnoreCase(side)) {
/*  450 */         out[0] = 1.0F; out[1] = 0.0F; out[2] = 0.0F;
/*      */         return;
/*      */       } 
/*  453 */       if ("West".equalsIgnoreCase(side)) {
/*  454 */         out[0] = -1.0F; out[1] = 0.0F; out[2] = 0.0F;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  462 */     out[0] = 0.0F; out[1] = -1.0F; out[2] = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeAnimationDirectionPerSection(float[] out) {
/*  485 */     if (out == null || out.length < 3) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  490 */     if (this.animationType.isSelected("Fade") || this.animationType.isSelected("Scale")) {
/*  491 */       out[0] = 0.0F; out[1] = 0.0F; out[2] = 0.0F;
/*      */       return;
/*      */     } 
/*  494 */     if (this.animationType.isSelected("Top") || this.animationType.isSelected("Bottom")) {
/*  495 */       out[0] = 0.0F; out[1] = 1.0F; out[2] = 0.0F;
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  502 */     writeAnimationDirection(out);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getIcon() {
/*  507 */     return "chunk_animator.png";
/*      */   }
/*      */ 
/*      */   
/*      */   public float getIconSize() {
/*  512 */     return 21.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getYOffset(class_2338 origin) {
/*  527 */     if (!isEnabled() || origin == null) {
/*  528 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  538 */     if (this.animationType.isSelected("Fade") || this.animationType.isSelected("Scale")) {
/*  539 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  552 */     long key = class_1923.method_8331(origin.method_10263() >> 4, origin.method_10260() >> 4);
/*  553 */     long now = System.currentTimeMillis();
/*  554 */     long total = Math.max(1L, this.duration.getInt());
/*  555 */     int dist = this.distance.getInt();
/*      */     
/*  557 */     Long start = this.firstSeenMs.get(Long.valueOf(key));
/*  558 */     if (start == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  567 */       if (!this.pendingAnimationKeys.contains(Long.valueOf(key))) {
/*  568 */         return 0.0F;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  573 */       if (this.firstSeenMs.size() >= 100000) {
/*  574 */         evictExpired(now, total);
/*      */       }
/*  576 */       this.pendingAnimationKeys.remove(Long.valueOf(key));
/*  577 */       this.firstSeenMs.put(Long.valueOf(key), Long.valueOf(now));
/*  578 */       this.lastAnimRegisterMs = now;
/*  579 */       return dist;
/*      */     } 
/*      */     
/*  582 */     long elapsed = now - start.longValue();
/*  583 */     if (elapsed >= total) {
/*  584 */       return 0.0F;
/*      */     }
/*      */     
/*  587 */     double progress = elapsed / total;
/*  588 */     Interpolation curve = Interpolations.getByName(this.easing.getSelected());
/*  589 */     double eased = curve.interpolate(progress);
/*      */ 
/*      */     
/*  592 */     double offset = dist * (1.0D - eased);
/*      */ 
/*      */ 
/*      */     
/*  596 */     if (offset < 0.0D) {
/*  597 */       offset = 0.0D;
/*      */     }
/*  599 */     return (float)offset;
/*      */   }
/*      */ 
/*      */   
/*      */   private void evictExpired(long now, long total) {
/*  604 */     evictExpiredFrom(this.firstSeenMs, now, total);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  613 */     this.playerYAtRegister.keySet().removeIf(k -> !this.firstSeenMs.containsKey(k));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeRegionSectionYOffsets(int regionOriginBlockX, int regionOriginBlockY, int regionOriginBlockZ, int[] drawableSlots, int drawableCount, float[] outOffsets) {
/*      */     float verticalSign;
/*  660 */     Arrays.fill(outOffsets, 0, 256, 0.0F);
/*  661 */     if (!isEnabled() || drawableSlots == null || drawableCount <= 0) {
/*  662 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  671 */     if (this.animationType.isSelected("Fade") || this.animationType.isSelected("Scale")) {
/*  672 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     int regionChunkX = regionOriginBlockX >> 4;
/*  680 */     int regionChunkY = regionOriginBlockY >> 4;
/*  681 */     int regionChunkZ = regionOriginBlockZ >> 4;
/*      */     
/*  683 */     long now = System.currentTimeMillis();
/*  684 */     long total = Math.max(1L, this.duration.getInt());
/*  685 */     int dist = this.distance.getInt();
/*  686 */     Interpolation curve = Interpolations.getByName(this.easing.getSelected());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  696 */     class_310 mc = class_310.method_1551();
/*      */ 
/*      */     
/*  699 */     float currentPlayerY = (mc != null && mc.field_1724 != null) ? (float)mc.field_1724.method_23318() : 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  705 */     if (this.animationType.isSelected("Top")) {
/*  706 */       verticalSign = 1.0F;
/*  707 */     } else if (this.animationType.isSelected("Bottom")) {
/*  708 */       verticalSign = -1.0F;
/*      */     } else {
/*  710 */       verticalSign = 0.0F;
/*      */     } 
/*  712 */     boolean verticalMode = (verticalSign != 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  717 */     if (this.firstSeenMs.size() >= 100000) {
/*  718 */       evictExpired(now, total);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     boolean hasNonZero = false;
/*  732 */     for (int i = 0; i < drawableCount; i++) {
/*  733 */       float offset; int slot = drawableSlots[i] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  741 */       int relX = slot >> 5 & 0x7;
/*  742 */       int relY = slot & 0x3;
/*  743 */       int relZ = slot >> 2 & 0x7;
/*      */       
/*  745 */       int sx = regionChunkX + relX;
/*  746 */       int sz = regionChunkZ + relZ;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  758 */       long key = class_1923.method_8331(sx, sz);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  766 */       int sectionWorldY = regionOriginBlockY + relY * 16;
/*      */       
/*  768 */       Long start = this.firstSeenMs.get(Long.valueOf(key));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  792 */       if (start == null && !this.pendingAnimationKeys.contains(Long.valueOf(key))) {
/*      */         continue;
/*      */       }
/*  795 */       if (start == null) {
/*  796 */         this.pendingAnimationKeys.remove(Long.valueOf(key));
/*  797 */         this.firstSeenMs.put(Long.valueOf(key), Long.valueOf(now));
/*  798 */         this.lastAnimRegisterMs = now;
/*  799 */         if (verticalMode) {
/*  800 */           this.playerYAtRegister.put(Long.valueOf(key), Float.valueOf(currentPlayerY));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  811 */           float startWorldY = currentPlayerY + verticalSign * dist;
/*  812 */           offset = startWorldY - sectionWorldY;
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  818 */           offset = dist;
/*      */         } 
/*      */       } else {
/*  821 */         long elapsed = now - start.longValue();
/*  822 */         if (elapsed >= total) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  829 */         double progress = elapsed / total;
/*  830 */         double eased = curve.interpolate(progress);
/*  831 */         if (verticalMode) {
/*  832 */           Float storedPy = this.playerYAtRegister.get(Long.valueOf(key));
/*  833 */           float py = (storedPy != null) ? storedPy.floatValue() : currentPlayerY;
/*  834 */           float startWorldY = py + verticalSign * dist;
/*  835 */           float initial = startWorldY - sectionWorldY;
/*  836 */           offset = initial * (float)(1.0D - eased);
/*      */ 
/*      */ 
/*      */           
/*  840 */           if (offset == 0.0F) {
/*      */             continue;
/*      */           }
/*      */         } else {
/*  844 */           double off = dist * (1.0D - eased);
/*  845 */           offset = (off < 0.0D) ? 0.0F : (float)off;
/*  846 */           if (offset == 0.0F) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  856 */       outOffsets[slot] = offset;
/*  857 */       hasNonZero = true; continue;
/*      */     } 
/*  859 */     return hasNonZero;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFadeMode() {
/*  869 */     return this.animationType.isSelected("Fade");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isScaleMode() {
/*  886 */     return this.animationType.isSelected("Scale");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAnimationModeIndex() {
/*  911 */     if (!isEnabled()) {
/*  912 */       return 0;
/*      */     }
/*  914 */     if (isFadeMode()) {
/*  915 */       return 2;
/*      */     }
/*  917 */     if (isScaleMode()) {
/*  918 */       return 3;
/*      */     }
/*      */ 
/*      */     
/*  922 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFadeStyleIndex() {
/*  942 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeRegionSectionFadeValues(int regionOriginBlockX, int regionOriginBlockY, int regionOriginBlockZ, int[] drawableSlots, int drawableCount, float[] outFade) {
/*  982 */     Arrays.fill(outFade, 0, 256, 1.0F);
/*  983 */     return writeRegionSectionProgressInternal(regionOriginBlockX, regionOriginBlockZ, drawableSlots, drawableCount, outFade, 
/*      */ 
/*      */ 
/*      */         
/*  987 */         isFadeMode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeRegionSectionScaleValues(int regionOriginBlockX, int regionOriginBlockY, int regionOriginBlockZ, int[] drawableSlots, int drawableCount, float[] outScale) {
/* 1022 */     Arrays.fill(outScale, 0, 256, 1.0F);
/* 1023 */     return writeRegionSectionProgressInternal(regionOriginBlockX, regionOriginBlockZ, drawableSlots, drawableCount, outScale, 
/*      */ 
/*      */ 
/*      */         
/* 1027 */         isScaleMode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean writeRegionSectionProgressInternal(int regionOriginBlockX, int regionOriginBlockZ, int[] drawableSlots, int drawableCount, float[] out, boolean active) {
/* 1066 */     if (!isEnabled() || !active || drawableSlots == null || drawableCount <= 0) {
/* 1067 */       return false;
/*      */     }
/*      */     
/* 1070 */     int regionChunkX = regionOriginBlockX >> 4;
/* 1071 */     int regionChunkZ = regionOriginBlockZ >> 4;
/*      */     
/* 1073 */     long now = System.currentTimeMillis();
/* 1074 */     long total = Math.max(1L, this.duration.getInt());
/*      */     
/* 1076 */     if (this.firstSeenMs.size() >= 100000) {
/* 1077 */       evictExpired(now, total);
/*      */     }
/*      */     
/* 1080 */     boolean hasNonOne = false;
/* 1081 */     for (int i = 0; i < drawableCount; i++) {
/* 1082 */       int slot = drawableSlots[i] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1090 */       int relX = slot >> 5 & 0x7;
/* 1091 */       int relZ = slot >> 2 & 0x7;
/* 1092 */       int sx = regionChunkX + relX;
/* 1093 */       int sz = regionChunkZ + relZ;
/* 1094 */       long key = class_1923.method_8331(sx, sz);
/*      */       
/* 1096 */       Long start = this.firstSeenMs.get(Long.valueOf(key));
/* 1097 */       if (start != null || this.pendingAnimationKeys.contains(Long.valueOf(key)))
/*      */       {
/*      */         
/* 1100 */         if (start == null) {
/* 1101 */           this.pendingAnimationKeys.remove(Long.valueOf(key));
/* 1102 */           this.firstSeenMs.put(Long.valueOf(key), Long.valueOf(now));
/* 1103 */           this.lastAnimRegisterMs = now;
/*      */ 
/*      */ 
/*      */           
/* 1107 */           out[slot] = 0.0F;
/* 1108 */           hasNonOne = true;
/*      */         } else {
/*      */           
/* 1111 */           long elapsed = now - start.longValue();
/* 1112 */           if (elapsed < total)
/*      */           
/*      */           { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1123 */             float progress = (float)elapsed / (float)total;
/* 1124 */             out[slot] = progress;
/* 1125 */             hasNonOne = true; } 
/*      */         }  } 
/* 1127 */     }  return hasNonOne;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRegionMagnitude(int regionOriginBlockX, int regionOriginBlockY, int regionOriginBlockZ, int[] drawableSlots, int drawableCount) {
/* 1162 */     if (!isEnabled() || drawableSlots == null || drawableCount <= 0) {
/* 1163 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     int regionChunkX = regionOriginBlockX >> 4;
/* 1177 */     int regionChunkY = regionOriginBlockY >> 4;
/* 1178 */     int regionChunkZ = regionOriginBlockZ >> 4;
/* 1179 */     long regionKey = class_4076.method_18685(regionChunkX, regionChunkY, regionChunkZ);
/*      */     
/* 1181 */     long now = System.currentTimeMillis();
/* 1182 */     long total = Math.max(1L, this.duration.getInt());
/* 1183 */     int dist = this.distance.getInt();
/*      */     
/* 1185 */     if (this.regionFirstSeenMs.size() >= 100000) {
/* 1186 */       evictExpiredFrom(this.regionFirstSeenMs, now, total);
/*      */     }
/*      */     
/* 1189 */     Long start = this.regionFirstSeenMs.get(Long.valueOf(regionKey));
/* 1190 */     if (start == null) {
/* 1191 */       this.regionFirstSeenMs.put(Long.valueOf(regionKey), Long.valueOf(now));
/* 1192 */       this.lastAnimRegisterMs = now;
/* 1193 */       return dist;
/*      */     } 
/*      */     
/* 1196 */     long elapsed = now - start.longValue();
/* 1197 */     if (elapsed >= total) {
/* 1198 */       return 0.0F;
/*      */     }
/*      */     
/* 1201 */     Interpolation curve = Interpolations.getByName(this.easing.getSelected());
/* 1202 */     double progress = elapsed / total;
/* 1203 */     double eased = curve.interpolate(progress);
/* 1204 */     return (float)(dist * (1.0D - eased));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void evictExpiredFrom(Map<Long, Long> map, long now, long total) {
/* 1209 */     long cutoff = now - 2L * total;
/* 1210 */     Iterator<Map.Entry<Long, Long>> it = map.entrySet().iterator();
/* 1211 */     while (it.hasNext()) {
/* 1212 */       if (((Long)((Map.Entry)it.next()).getValue()).longValue() < cutoff) {
/* 1213 */         it.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetTracker() {
/* 1238 */     this.firstSeenMs.clear();
/* 1239 */     this.regionFirstSeenMs.clear();
/* 1240 */     this.playerYAtRegister.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1245 */     this.pendingAnimationKeys.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onF3AReload() {
/* 1271 */     this.firstSeenMs.clear();
/* 1272 */     this.regionFirstSeenMs.clear();
/* 1273 */     this.playerYAtRegister.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1281 */     class_310 mc = class_310.method_1551();
/* 1282 */     if (mc == null || mc.field_1687 == null || mc.field_1724 == null) {
/*      */       return;
/*      */     }
/* 1285 */     int playerChunkX = (mc.field_1724.method_31476()).field_9181;
/* 1286 */     int playerChunkZ = (mc.field_1724.method_31476()).field_9180;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1294 */     int viewDistance = ((Integer)mc.field_1690.method_42503().method_41753()).intValue() + 1;
/* 1295 */     for (int dx = -viewDistance; dx <= viewDistance; dx++) {
/* 1296 */       for (int dz = -viewDistance; dz <= viewDistance; dz++) {
/* 1297 */         int cx = playerChunkX + dx;
/* 1298 */         int cz = playerChunkZ + dz;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1303 */         if (mc.field_1687.method_8402(cx, cz, class_2806.field_12803, false) != null)
/* 1304 */           this.pendingAnimationKeys.add(Long.valueOf(class_1923.method_8331(cx, cz))); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ChunkAnimator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */