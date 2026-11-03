/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1542;
/*     */ import net.minecraft.class_1676;
/*     */ import net.minecraft.class_1680;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2604;
/*     */ import net.minecraft.class_2663;
/*     */ import net.minecraft.class_2678;
/*     */ import net.minecraft.class_2724;
/*     */ import net.minecraft.class_2775;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_634;
/*     */ import net.minecraft.class_638;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChatHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.ChunkAnimator;
/*     */ import vorga.phazeclient.implement.features.modules.other.FTHelper;
/*     */ import vorga.phazeclient.implement.features.modules.other.ItemPickupLogger;
/*     */ import vorga.phazeclient.implement.features.modules.other.MentionHighlight;
/*     */ import vorga.phazeclient.implement.features.modules.other.Predictions;
/*     */ import vorga.phazeclient.implement.features.modules.other.TotemTracker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_634.class})
/*     */ public abstract class ClientPlayNetworkHandlerMixin
/*     */ {
/*     */   @Shadow
/*     */   private class_638 field_3699;
/*     */   @Unique
/*     */   private static final long PHAZE_DUP_WINDOW_MS = 2000L;
/*     */   @Unique
/*  51 */   private final Int2LongOpenHashMap phaze$recentEntityIds = new Int2LongOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onGameJoin"}, at = {@At("HEAD")})
/*     */   private void phaze$chunkAnimatorOnGameJoin(class_2678 packet, CallbackInfo ci) {
/*  59 */     ChunkAnimator.getInstance().resetTracker();
/*     */   }
/*     */   
/*     */   @Inject(method = {"onPlayerRespawn"}, at = {@At("HEAD")})
/*     */   private void phaze$chunkAnimatorOnPlayerRespawn(class_2724 packet, CallbackInfo ci) {
/*  64 */     ChunkAnimator.getInstance().resetTracker();
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
/*     */   @ModifyVariable(method = {"sendChatMessage(Ljava/lang/String;)V"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
/*     */   private String phaze$lowercaseOutgoing(String content) {
/*  78 */     ChatHelper helper = ChatHelper.getInstance();
/*  79 */     if (helper == null) {
/*  80 */       return content;
/*     */     }
/*  82 */     return helper.maybeAntiCaps(content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"sendChatMessage(Ljava/lang/String;)V"}, at = {@At("HEAD")})
/*     */   private void phaze$markOutgoingChat(String content, CallbackInfo ci) {
/*  94 */     MentionHighlight.getInstance().markOutgoing(content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"sendChatCommand(Ljava/lang/String;)V"}, at = {@At("HEAD")}, require = 0)
/*     */   private void phaze$markOutgoingCommand(String command, CallbackInfo ci) {
/* 103 */     MentionHighlight.getInstance().markOutgoing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onEntityStatus"}, at = {@At("HEAD")})
/*     */   private void phaze$captureTotemPop(class_2663 packet, CallbackInfo ci) {
/* 112 */     if (packet.method_11470() != 35) {
/*     */       return;
/*     */     }
/* 115 */     TotemTracker tracker = TotemTracker.getInstance();
/* 116 */     if (tracker == null) {
/*     */       return;
/*     */     }
/* 119 */     class_310 mc = class_310.method_1551();
/* 120 */     if (mc == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 125 */     if (!mc.method_18854()) {
/*     */       return;
/*     */     }
/* 128 */     class_638 w = this.field_3699;
/* 129 */     if (w == null) {
/*     */       return;
/*     */     }
/* 132 */     class_1297 entity = packet.method_11469((class_1937)w);
/* 133 */     if (entity instanceof class_1309) { class_1309 living = (class_1309)entity;
/* 134 */       tracker.recordTotemUse(living); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onEntitySpawn"}, at = {@At("TAIL")})
/*     */   private void phaze$captureSnowballSpawn(class_2604 packet, CallbackInfo ci) {
/* 144 */     FTHelper module = FTHelper.getInstance();
/* 145 */     if (module == null || !module.isEnabled()) {
/*     */       return;
/*     */     }
/* 148 */     class_310 mc = class_310.method_1551();
/* 149 */     if (mc == null || mc.field_1687 == null || !mc.method_18854()) {
/*     */       return;
/*     */     }
/* 152 */     class_1297 entity = mc.field_1687.method_8469(packet.method_11167());
/* 153 */     if (entity instanceof class_1680) { class_1680 snowball = (class_1680)entity;
/* 154 */       module.trackSnowball(snowball); }
/*     */   
/*     */   }
/*     */   
/*     */   @Inject(method = {"onEntitySpawn"}, at = {@At("TAIL")})
/*     */   private void phaze$captureProjectileSpawn(class_2604 packet, CallbackInfo ci) {
/* 160 */     Predictions module = Predictions.getInstance();
/* 161 */     if (module == null || !module.isEnabled()) {
/*     */       return;
/*     */     }
/* 164 */     class_310 mc = class_310.method_1551();
/* 165 */     if (mc == null || mc.field_1687 == null || !mc.method_18854()) {
/*     */       return;
/*     */     }
/* 168 */     class_1297 entity = mc.field_1687.method_8469(packet.method_11167());
/* 169 */     if (entity instanceof class_1676) { class_1676 projectile = (class_1676)entity;
/* 170 */       module.trackProjectile(projectile); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onItemPickupAnimation"}, at = {@At("HEAD")})
/*     */   private void phaze$logPickup(class_2775 packet, CallbackInfo ci) {
/* 180 */     ItemPickupLogger logger = ItemPickupLogger.getInstance();
/* 181 */     if (logger == null || !logger.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     class_310 mc = class_310.method_1551();
/* 186 */     if (mc == null || mc.field_1724 == null || mc.field_1687 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 190 */     if (packet.method_11912() != mc.field_1724.method_5628()) {
/*     */       return;
/*     */     }
/*     */     
/* 194 */     int entityId = packet.method_11915();
/* 195 */     long now = System.currentTimeMillis();
/* 196 */     long lastSeen = this.phaze$recentEntityIds.getOrDefault(entityId, 0L);
/* 197 */     if (lastSeen != 0L && now - lastSeen < 2000L) {
/*     */       return;
/*     */     }
/* 200 */     this.phaze$recentEntityIds.put(entityId, now);
/*     */     
/* 202 */     if (this.phaze$recentEntityIds.size() > 64) {
/* 203 */       ObjectIterator<Int2LongMap.Entry> objectIterator = this.phaze$recentEntityIds.int2LongEntrySet().iterator();
/* 204 */       while (objectIterator.hasNext()) {
/* 205 */         if (now - ((Int2LongMap.Entry)objectIterator.next()).getLongValue() > 2000L) {
/* 206 */           objectIterator.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     class_1297 entity = mc.field_1687.method_8469(entityId);
/* 212 */     if (entity instanceof class_1542) { class_1542 itemEntity = (class_1542)entity;
/* 213 */       logger.onPickup(itemEntity.method_6983(), packet.method_11913()); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ClientPlayNetworkHandlerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */