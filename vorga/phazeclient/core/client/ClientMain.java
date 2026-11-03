/*    */ package vorga.phazeclient.core.client;
/*    */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
/*    */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*    */ import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
/*    */ import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_304;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_3264;
/*    */ import net.minecraft.class_3300;
/*    */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*    */ import vorga.phazeclient.core.Main;
/*    */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*    */ import vorga.phazeclient.implement.features.modules.hud.PackDisplayHud;
/*    */ import vorga.phazeclient.implement.features.modules.other.ColorCorrection;
/*    */ import vorga.phazeclient.implement.features.modules.other.MotionBlur;
/*    */ import vorga.phazeclient.implement.features.modules.other.PackPreviewRenderer;
/*    */ import vorga.phazeclient.implement.menu.MenuPanoramaRegistry;
/*    */ import vorga.phazeclient.implement.menu.MenuPanoramaRenderer;
/*    */ import vorga.phazeclient.implement.menu.MenuScreen;
/*    */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
/*    */ import vorga.phazeclient.implement.menu.loading.BootstrapLoadingRenderer;
/*    */ 
/*    */ public class ClientMain implements ClientModInitializer {
/* 25 */   private static final class_304 OPEN_MENU_KEY = KeyBindingHelper.registerKeyBinding(new class_304("key.phaze.open_menu", class_3675.class_307.field_1668, 344, "category.phaze"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   private int saveTimer = 0;
/* 35 */   private int preloadTimer = 0;
/*    */   
/*    */   private boolean guiPreloaded = false;
/*    */   private static final int SAVE_INTERVAL_TICKS = 100;
/*    */   private static final int PRELOAD_DELAY_TICKS = 40;
/*    */   
/*    */   public void onInitializeClient() {
/* 42 */     MenuPanoramaRegistry.ensureDirectoryExists();
/* 43 */     ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
/* 44 */     ClientLifecycleEvents.CLIENT_STOPPING.register(this::onClientStopping);
/*    */     
/* 46 */     ResourceManagerHelper.get(class_3264.field_14188).registerReloadListener((IdentifiableResourceReloadListener)new SimpleSynchronousResourceReloadListener(this)
/*    */         {
/*    */           public void method_14491(class_3300 manager) {
/* 49 */             (MotionBlur.getInstance()).shader.reload();
/* 50 */             (ColorCorrection.getInstance()).shader.reload();
/* 51 */             MsdfFonts.onResourcesReloaded();
/* 52 */             UiMsdfIconAtlas.onResourcesReloaded();
/* 53 */             MenuPanoramaRenderer.onResourcesReloaded();
/* 54 */             MenuPanoramaRegistry.onResourcesReloaded();
/* 55 */             PackPreviewRenderer.clear(class_310.method_1551());
/* 56 */             PackDisplayHud.getInstance().onResourcesReloaded(class_310.method_1551());
/* 57 */             BossBarHud.getInstance().invalidateNameWidths();
/*    */           }
/*    */ 
/*    */           
/*    */           public class_2960 getFabricId() {
/* 62 */             return class_2960.method_60655("phazeclient", "shader_reloader");
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   private void onClientTick(class_310 client) {
/* 69 */     while (OPEN_MENU_KEY.method_1436()) {
/* 70 */       if (client.field_1755 instanceof MenuScreen) {
/* 71 */         client.method_1507(null); continue;
/*    */       } 
/* 73 */       MenuScreen.INSTANCE.openGui();
/*    */     } 
/*    */ 
/*    */     
/* 77 */     if (!this.guiPreloaded) {
/* 78 */       this.preloadTimer++;
/* 79 */       if (this.preloadTimer >= 40) {
/* 80 */         MenuScreen.preload();
/* 81 */         this.guiPreloaded = true;
/*    */       } 
/*    */     } 
/*    */     
/* 85 */     this.saveTimer++;
/* 86 */     if (this.saveTimer >= 100) {
/* 87 */       this.saveTimer = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   private void onClientStopping(class_310 client) {
/* 92 */     BootstrapLoadingRenderer.close();
/* 93 */     Main.getInstance().getConfigManager().saveCurrentConfig();
/* 94 */     System.out.println("[Phaze Client] Client stopping, config saved!");
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\core\client\ClientMain.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */