/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
/*    */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_4608;
/*    */ import org.joml.Matrix4f;
/*    */ import vorga.phazeclient.implement.cosmetics.CosmeticsSyncService;
/*    */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*    */ 
/*    */ public final class PhazePulseGraffitiRenderer {
/*    */   private static final double MAX_DISTANCE_SQ = 9216.0D;
/*    */   private static final int MAX_PER_FRAME = 512;
/*    */   private static boolean registered;
/*    */   
/*    */   public static synchronized void register() {
/* 25 */     if (registered)
/* 26 */       return;  registered = true;
/* 27 */     WorldRenderEvents.AFTER_ENTITIES.register(ctx -> {
/*    */           class_310 client = class_310.method_1551();
/*    */           if (client.field_1724 == null || client.field_1687 == null || !(Theme.getInstance()).renderOtherPlayerCosmetics.isValue())
/*    */             return; 
/*    */           String serverKey = SharedWorldKey.get(client);
/*    */           String dimension = SharedWorldKey.dimension(client);
/*    */           if (serverKey == null || dimension == null)
/*    */             return; 
/*    */           class_243 cameraPos = client.field_1773.method_19418().method_19326();
/*    */           Camera camera = new Camera(cameraPos.field_1352, cameraPos.field_1351, cameraPos.field_1350);
/*    */           int rendered = 0;
/*    */           for (CosmeticsSyncService.PulseGraffiti graffiti : CosmeticsSyncService.getInstance().pulseGraffiti()) {
/*    */             if (!serverKey.equals(graffiti.serverKey()) || !dimension.equals(graffiti.dimension()))
/*    */               continue; 
/*    */             class_2338 pos = graffiti.pos();
/*    */             if (pos.method_46558().method_1025(client.field_1724.method_19538()) > 9216.0D)
/*    */               continue; 
/*    */             CosmeticEntry entry = CosmeticCatalog.byId(graffiti.graffitiId());
/*    */             if (entry == null || entry.category() != CosmeticCategory.GRAFFITI)
/*    */               continue; 
/*    */             render(entry, pos, graffiti.face(), ctx.matrixStack(), ctx.consumers(), camera);
/*    */             if (++rendered >= 512)
/*    */               break; 
/*    */           } 
/*    */         });
/*    */   }
/*    */   private static void render(CosmeticEntry entry, class_2338 pos, class_2350 face, class_4587 matrices, class_4597 providers, Camera camera) {
/* 54 */     class_2960 texture = entry.textureId();
/* 55 */     if (texture == null)
/*    */       return; 
/* 57 */     float aspect = (entry.textureWidth() > 0 && entry.textureHeight() > 0) ? (entry.textureWidth() / entry.textureHeight()) : 1.0F;
/* 58 */     float width = (aspect >= 1.0F) ? 0.9F : (0.9F * aspect);
/* 59 */     float height = (aspect >= 1.0F) ? (0.9F / aspect) : 0.9F;
/* 60 */     float cx = pos.method_10263() + 0.5F, cy = pos.method_10264() + 0.5F, cz = pos.method_10260() + 0.5F;
/* 61 */     float offset = 0.01F;
/* 62 */     float[] u = new float[3], v = { 0.0F, 1.0F, 0.0F };
/* 63 */     switch (face) { case field_11043:
/* 64 */         cz -= 0.5F + offset; u[0] = 1.0F; break;
/* 65 */       case field_11035: cz += 0.5F + offset; u[0] = -1.0F; break;
/* 66 */       case field_11034: cx += 0.5F + offset; u[2] = -1.0F; break;
/* 67 */       case field_11039: cx -= 0.5F + offset; u[2] = 1.0F; break;
/* 68 */       case field_11036: cy += 0.5F + offset; u[0] = 1.0F; v = new float[] { 0.0F, 0.0F, -1.0F }; break;
/* 69 */       case field_11033: cy -= 0.5F + offset; u[0] = 1.0F; v = new float[] { 0.0F, 0.0F, 1.0F }; break; }
/*    */     
/* 71 */     float hu = width / 2.0F, hv = height / 2.0F;
/* 72 */     matrices.method_22903();
/* 73 */     matrices.method_22904(-camera.x, -camera.y, -camera.z);
/* 74 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 75 */     class_4588 consumer = providers.getBuffer(class_1921.method_23580(texture));
/* 76 */     RenderSystem.disableCull();
/* 77 */     vertex(consumer, matrix, cx - u[0] * hu - v[0] * hv, cy - u[1] * hu - v[1] * hv, cz - u[2] * hu - v[2] * hv, 0.0F, 1.0F);
/* 78 */     vertex(consumer, matrix, cx + u[0] * hu - v[0] * hv, cy + u[1] * hu - v[1] * hv, cz + u[2] * hu - v[2] * hv, 1.0F, 1.0F);
/* 79 */     vertex(consumer, matrix, cx + u[0] * hu + v[0] * hv, cy + u[1] * hu + v[1] * hv, cz + u[2] * hu + v[2] * hv, 1.0F, 0.0F);
/* 80 */     vertex(consumer, matrix, cx - u[0] * hu + v[0] * hv, cy - u[1] * hu + v[1] * hv, cz - u[2] * hu + v[2] * hv, 0.0F, 0.0F);
/* 81 */     RenderSystem.enableCull();
/* 82 */     matrices.method_22909();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void vertex(class_4588 consumer, Matrix4f matrix, float x, float y, float z, float u, float v) {
/* 87 */     consumer.method_22918(matrix, x, y, z).method_1336(255, 255, 255, 255).method_22913(u, v)
/* 88 */       .method_22922(class_4608.field_21444).method_60803(15728880).method_22914(0.0F, 1.0F, 0.0F);
/*    */   }
/*    */   private static final class Camera extends Record { private final double x; private final double y; private final double z;
/* 91 */     private Camera(double x, double y, double z) { this.x = x; this.y = y; this.z = z; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/cosmetics/bridge/PhazePulseGraffitiRenderer$Camera;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #91	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 91 */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/PhazePulseGraffitiRenderer$Camera; } public double x() { return this.x; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/cosmetics/bridge/PhazePulseGraffitiRenderer$Camera;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #91	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/PhazePulseGraffitiRenderer$Camera; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/cosmetics/bridge/PhazePulseGraffitiRenderer$Camera;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #91	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lvorga/phazeclient/implement/cosmetics/bridge/PhazePulseGraffitiRenderer$Camera;
/* 91 */       //   0	8	1	o	Ljava/lang/Object; } public double y() { return this.y; } public double z() { return this.z; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\PhazePulseGraffitiRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */