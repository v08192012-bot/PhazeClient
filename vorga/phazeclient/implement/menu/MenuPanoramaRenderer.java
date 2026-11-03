/*    */ package vorga.phazeclient.implement.menu;
/*    */ 
/*    */ import java.util.stream.IntStream;
/*    */ import net.minecraft.class_1060;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_751;
/*    */ import net.minecraft.class_9848;
/*    */ 
/*    */ public final class MenuPanoramaRenderer
/*    */ {
/* 14 */   private static final class_2960 OVERLAY_TEXTURE = class_2960.method_60656("textures/gui/title/background/panorama_overlay.png");
/* 15 */   private static volatile int resourceGeneration = 0;
/*    */   
/*    */   private final class_751 cubeMap;
/*    */   private final class_2960[] faceTextures;
/* 19 */   private long lastFrameTimeNs = -1L;
/* 20 */   private int preparedResourceGeneration = Integer.MIN_VALUE;
/*    */   private float pitch;
/*    */   
/*    */   public MenuPanoramaRenderer(class_2960 cubeMapBase) {
/* 24 */     this.cubeMap = new class_751(cubeMapBase);
/* 25 */     this
/*    */       
/* 27 */       .faceTextures = (class_2960[])IntStream.range(0, 6).mapToObj(face -> cubeMapBase.method_45136(cubeMapBase.method_12832() + "_" + cubeMapBase.method_12832() + ".png")).toArray(x$0 -> new class_2960[x$0]);
/*    */   }
/*    */   
/*    */   public void render(class_332 context, int width, int height, float alpha) {
/* 31 */     class_310 client = class_310.method_1551();
/* 32 */     if (client == null) {
/*    */       return;
/*    */     }
/* 35 */     prepareTextures(client);
/*    */     
/* 37 */     long nowNs = System.nanoTime();
/*    */ 
/*    */     
/* 40 */     float dtSeconds = (this.lastFrameTimeNs > 0L) ? Math.min(0.05F, (float)(nowNs - this.lastFrameTimeNs) / 1.0E9F) : 0.016666668F;
/* 41 */     this.lastFrameTimeNs = nowNs;
/*    */     
/* 43 */     float speedMultiplier = (float)(MenuUiSettings.getInstance().getPanoramaSpeed() / 1000.0D);
/* 44 */     this.pitch = wrapOnce(this.pitch + dtSeconds * 72.0F * speedMultiplier, 360.0F);
/*    */     
/* 46 */     context.method_51452();
/* 47 */     this.cubeMap.method_3156(client, 10.0F, -this.pitch, alpha);
/* 48 */     context.method_51452();
/* 49 */     context.method_25293(class_1921::method_62277, OVERLAY_TEXTURE, 0, 0, 0.0F, 0.0F, width, height, 16, 128, 16, 128, 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 62 */         class_9848.method_61317(alpha));
/*    */   }
/*    */ 
/*    */   
/*    */   private static float wrapOnce(float value, float max) {
/* 67 */     return (value > max) ? (value - max) : value;
/*    */   }
/*    */   
/*    */   public static void onResourcesReloaded() {
/* 71 */     resourceGeneration++;
/*    */   }
/*    */   
/*    */   public void prepareTextures(class_310 client) {
/* 75 */     if (client == null) {
/*    */       return;
/*    */     }
/* 78 */     class_1060 textureManager = client.method_1531();
/* 79 */     if (textureManager == null) {
/*    */       return;
/*    */     }
/* 82 */     if (this.preparedResourceGeneration == resourceGeneration) {
/*    */       return;
/*    */     }
/* 85 */     textureManager.method_4619(OVERLAY_TEXTURE);
/* 86 */     for (class_2960 faceTexture : this.faceTextures) {
/* 87 */       textureManager.method_4619(faceTexture);
/*    */     }
/* 89 */     this.preparedResourceGeneration = resourceGeneration;
/* 90 */     this.lastFrameTimeNs = -1L;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\MenuPanoramaRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */