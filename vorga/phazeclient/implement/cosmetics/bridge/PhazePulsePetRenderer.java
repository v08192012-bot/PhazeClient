/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
/*    */ 
/*    */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
/*    */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_742;
/*    */ import net.minecraft.class_7833;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsSyncService;
/*    */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.PulseGeoRenderer;
/*    */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*    */ 
/*    */ public final class PhazePulsePetRenderer {
/* 15 */   private static final long START_NANOS = System.nanoTime();
/*    */   private static boolean registered;
/*    */   
/*    */   public static synchronized void register() {
/* 19 */     if (registered)
/* 20 */       return;  registered = true;
/* 21 */     WorldRenderEvents.AFTER_ENTITIES.register(ctx -> {
/*    */           class_310 client = class_310.method_1551();
/*    */           if (client.field_1724 == null || client.field_1687 == null || !(Theme.getInstance()).renderOtherPlayerCosmetics.isValue()) {
/*    */             return;
/*    */           }
/*    */           for (class_742 player : client.field_1687.method_18456()) {
/*    */             if (player != client.field_1724)
/*    */               render(ctx, client, player); 
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   private static void render(WorldRenderContext ctx, class_310 client, class_742 player) {
/* 34 */     CosmeticEntry entry = CosmeticsSyncService.getInstance().pulseEntryFor(player.method_5667(), CosmeticCategory.PET);
/* 35 */     if (entry == null || entry.kind() != CosmeticKind.GEOMETRY)
/* 36 */       return;  ParsedCosmeticModel model = CosmeticModelLoader.get(entry);
/* 37 */     if (model == null)
/* 38 */       return;  float tickDelta = ctx.tickCounter().method_60637(false);
/* 39 */     class_243 pos = player.method_30950(tickDelta);
/* 40 */     class_243 camera = client.field_1773.method_19418().method_19326();
/* 41 */     class_4587 matrices = ctx.matrixStack();
/* 42 */     matrices.method_22903();
/* 43 */     matrices.method_22904(pos.field_1352 - camera.field_1352, pos.field_1351 - camera.field_1351, pos.field_1350 - camera.field_1350);
/* 44 */     matrices.method_22907(class_7833.field_40716.rotationDegrees(-player.method_61415(tickDelta)));
/* 45 */     matrices.method_46416(model.globalOffset.x(), player.method_17682() - model.globalOffset.y(), model.globalOffset.z());
/* 46 */     if (entry.id() == 132) matrices.method_22904(0.0D, -2.0D, 0.0D); 
/* 47 */     PulseGeoRenderer.renderPet(entry, matrices, ctx.consumers(), 15728880, player
/* 48 */         .method_6128(), player.method_5681(), player.method_5715(), 
/* 49 */         PetMovementTracker.isMoving(player), 
/* 50 */         (float)((System.nanoTime() - START_NANOS) / 1.0E9D));
/* 51 */     matrices.method_22909();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\PhazePulsePetRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */