/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
/*    */ 
/*    */ import net.minecraft.class_10055;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_742;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsSyncService;
/*    */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.PulseGeoRenderer;
/*    */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*    */ 
/*    */ public final class PhazePulseRenderer
/*    */ {
/* 15 */   private static final long START_NANOS = System.nanoTime();
/*    */ 
/*    */   
/*    */   public static void renderBody(class_4587 matrices, class_4597 providers, class_10055 state, int light) {
/* 19 */     class_742 player = remotePlayer(state.field_53528);
/* 20 */     if (player == null || state.field_53333 || state.field_53542)
/* 21 */       return;  float time = time();
/* 22 */     boolean moving = (player.method_18798().method_37268() > 0.0015D);
/*    */     
/* 24 */     renderGeometry(entry(player, CosmeticCategory.BODYWEAR), CosmeticCategory.BODYWEAR, matrices, providers, light, state, moving, time);
/*    */     
/* 26 */     renderGeometry(entry(player, CosmeticCategory.WINGS), CosmeticCategory.WINGS, matrices, providers, light, state, moving, time);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void renderHead(class_4587 matrices, class_4597 providers, class_10055 state, int light) {
/* 32 */     class_742 player = remotePlayer(state.field_53528);
/* 33 */     if (player == null || state.field_53333 || state.field_53542)
/* 34 */       return;  CosmeticEntry entry = entry(player, CosmeticCategory.HAT);
/* 35 */     if (entry == null || entry.kind() != CosmeticKind.GEOMETRY)
/* 36 */       return;  matrices.method_22903();
/* 37 */     switch (entry.id()) { case 85: case 88: case 92: case 96: break;
/*    */       case 48: case 49: case 50: case 53: case 57:
/* 39 */         matrices.method_22904(0.0D, -0.24D, 0.0D); break;
/* 40 */       case 80: matrices.method_22904(0.0D, 0.06D, 0.0D); break;
/* 41 */       default: matrices.method_22904(0.0D, -0.15D, 0.0D); break; }
/*    */     
/* 43 */     PulseGeoRenderer.render(entry, matrices, providers, light, state.field_53411, state.field_53412, state.field_53410, 
/*    */         
/* 45 */         (player.method_18798().method_37268() > 0.0015D), time());
/* 46 */     matrices.method_22909();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void renderGeometry(CosmeticEntry entry, CosmeticCategory category, class_4587 matrices, class_4597 providers, int light, class_10055 state, boolean moving, float time) {
/* 53 */     if (entry == null || entry.kind() != CosmeticKind.GEOMETRY)
/* 54 */       return;  matrices.method_22903();
/* 55 */     if (category == CosmeticCategory.BODYWEAR) { matrices.method_22904(0.0D, 0.25D, 0.0D); }
/* 56 */     else if (category == CosmeticCategory.WINGS) { matrices.method_22904(0.0D, 0.27D, 0.0D); }
/* 57 */      PulseGeoRenderer.render(entry, matrices, providers, light, state.field_53411, state.field_53412, state.field_53410, moving, time);
/*    */     
/* 59 */     matrices.method_22909();
/*    */   }
/*    */   
/*    */   private static CosmeticEntry entry(class_742 player, CosmeticCategory category) {
/* 63 */     return CosmeticsSyncService.getInstance().pulseEntryFor(player.method_5667(), category);
/*    */   }
/*    */   
/*    */   private static class_742 remotePlayer(int entityId) {
/* 67 */     class_310 client = class_310.method_1551();
/* 68 */     if (client == null || client.field_1687 == null || client.field_1724 == null || 
/* 69 */       !(Theme.getInstance()).renderOtherPlayerCosmetics.isValue()) return null; 
/* 70 */     class_1297 entity = client.field_1687.method_8469(entityId);
/* 71 */     if (entity instanceof class_742) { class_742 player = (class_742)entity; if (player != client.field_1724)
/* 72 */         return player;  }
/*    */     
/*    */     return null;
/*    */   } private static float time() {
/* 76 */     return (float)((System.nanoTime() - START_NANOS) / 1.0E9D);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\PhazePulseRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */