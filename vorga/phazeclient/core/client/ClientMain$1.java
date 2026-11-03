/*    */ package vorga.phazeclient.core.client;
/*    */ 
/*    */ import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_3300;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*    */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*    */ import vorga.phazeclient.implement.features.modules.hud.PackDisplayHud;
/*    */ import vorga.phazeclient.implement.features.modules.other.ColorCorrection;
/*    */ import vorga.phazeclient.implement.features.modules.other.MotionBlur;
/*    */ import vorga.phazeclient.implement.features.modules.other.PackPreviewRenderer;
/*    */ import vorga.phazeclient.implement.menu.MenuPanoramaRegistry;
/*    */ import vorga.phazeclient.implement.menu.MenuPanoramaRenderer;
/*    */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements SimpleSynchronousResourceReloadListener
/*    */ {
/*    */   null(ClientMain this$0) {}
/*    */   
/*    */   public void method_14491(class_3300 manager) {
/* 49 */     (MotionBlur.getInstance()).shader.reload();
/* 50 */     (ColorCorrection.getInstance()).shader.reload();
/* 51 */     MsdfFonts.onResourcesReloaded();
/* 52 */     UiMsdfIconAtlas.onResourcesReloaded();
/* 53 */     MenuPanoramaRenderer.onResourcesReloaded();
/* 54 */     MenuPanoramaRegistry.onResourcesReloaded();
/* 55 */     PackPreviewRenderer.clear(class_310.method_1551());
/* 56 */     PackDisplayHud.getInstance().onResourcesReloaded(class_310.method_1551());
/* 57 */     BossBarHud.getInstance().invalidateNameWidths();
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2960 getFabricId() {
/* 62 */     return class_2960.method_60655("phazeclient", "shader_reloader");
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\core\client\ClientMain$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */