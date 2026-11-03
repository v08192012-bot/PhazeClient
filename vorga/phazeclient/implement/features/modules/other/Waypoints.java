/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
/*    */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
/*    */ import net.minecraft.class_238;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_5250;
/*    */ import net.minecraft.class_5348;
/*    */ import net.minecraft.class_9974;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*    */ import vorga.phazeclient.util.render.PhazeRenderLayers;
/*    */ 
/*    */ public class Waypoints extends Module {
/* 23 */   private static final Waypoints INSTANCE = new Waypoints();
/*    */   
/*    */   private static boolean registered = false;
/* 26 */   public final BooleanSetting deathWaypoint = (new BooleanSetting("Death Waypoint", "Automatically place a 3D marker upon death")).setValue(true);
/* 27 */   public final BooleanSetting showBeam = (new BooleanSetting("Show Beam", "Draw vertical light beam to the sky")).setValue(true);
/* 28 */   public final ColorSetting deathColor = (new ColorSetting("Death Color", "Color of death waypoint")).value(-52429);
/* 29 */   public final ValueSetting maxDistance = (new ValueSetting("Max Distance", "Maximum render distance in blocks")).range(200, 5000).step(100.0F).setValue(2000.0F);
/*    */   
/*    */   private boolean hasDeathPos = false;
/* 32 */   private double deathX = 0.0D; private double deathY = 0.0D; private double deathZ = 0.0D;
/*    */   private boolean wasDead = false;
/*    */   
/*    */   private Waypoints() {
/* 36 */     super("waypoints", "Waypoints", ModuleCategory.OTHER);
/* 37 */     setup(new Setting[] { (Setting)this.deathWaypoint, (Setting)this.showBeam, (Setting)this.deathColor, (Setting)this.maxDistance });
/* 38 */     registerEvents();
/*    */   }
/*    */   
/*    */   public static Waypoints getInstance() {
/* 42 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 47 */     return "3D in-world beacons and markers with distance tags and death waypoints";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 52 */     return "death_coords.png";
/*    */   }
/*    */   
/*    */   private synchronized void registerEvents() {
/* 56 */     if (registered)
/* 57 */       return;  registered = true;
/*    */     
/* 59 */     ClientTickEvents.END_CLIENT_TICK.register(client -> {
/*    */           if (!isEnabled() || client.field_1724 == null)
/*    */             return; 
/* 62 */           boolean dead = (client.field_1724.method_29504() || client.field_1724.method_6032() <= 0.0F);
/*    */           
/*    */           if (dead && !this.wasDead && this.deathWaypoint.isValue()) {
/*    */             this.deathX = client.field_1724.method_23317();
/*    */             this.deathY = client.field_1724.method_23318();
/*    */             this.deathZ = client.field_1724.method_23321();
/*    */             this.hasDeathPos = true;
/*    */           } 
/*    */           this.wasDead = dead;
/*    */         });
/* 72 */     WorldRenderEvents.AFTER_ENTITIES.register(context -> {
/*    */           if (!isEnabled() || !this.hasDeathPos)
/*    */             return; 
/*    */           class_310 client = class_310.method_1551();
/*    */           if (client.field_1687 == null || client.field_1724 == null)
/*    */             return; 
/*    */           class_4597 consumers = context.consumers();
/*    */           if (consumers == null)
/*    */             return; 
/*    */           class_4587 matrices = context.matrixStack();
/*    */           class_243 cameraPos = client.field_1773.method_19418().method_19326();
/*    */           double dx = this.deathX - client.field_1724.method_23317();
/*    */           double dy = this.deathY - client.field_1724.method_23318();
/*    */           double dz = this.deathZ - client.field_1724.method_23321();
/*    */           double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*    */           if (dist > this.maxDistance.getValue())
/*    */             return; 
/*    */           int col = this.deathColor.getColor();
/*    */           float a = (col >> 24 & 0xFF) / 255.0F;
/*    */           float r = (col >> 16 & 0xFF) / 255.0F;
/*    */           float g = (col >> 8 & 0xFF) / 255.0F;
/*    */           float b = (col & 0xFF) / 255.0F;
/*    */           double rx = this.deathX - cameraPos.field_1352;
/*    */           double ry = this.deathY - cameraPos.field_1351;
/*    */           double rz = this.deathZ - cameraPos.field_1350;
/*    */           if (this.showBeam.isValue()) {
/*    */             class_4588 consumer = consumers.getBuffer(PhazeRenderLayers.getThickLines(2.0F));
/*    */             class_238 beamBox = new class_238(rx - 0.2D, ry, rz - 0.2D, rx + 0.2D, ry + 128.0D, rz + 0.2D);
/*    */             class_9974.method_62295(matrices, consumer, beamBox, r, g, b, a * 0.7F);
/*    */           } 
/*    */           String textStr = "Death (" + (int)dist + "m)";
/*    */           class_5250 class_5250 = class_2561.method_43470(textStr);
/*    */           class_327 textRenderer = client.field_1772;
/*    */           int textWidth = textRenderer.method_27525((class_5348)class_5250);
/*    */           matrices.method_22903();
/*    */           matrices.method_22904(rx, ry + 2.0D, rz);
/*    */           matrices.method_22907(client.method_1561().method_24197());
/*    */           float s = 0.035F * Math.max(1.0F, (float)(dist / 16.0D));
/*    */           matrices.method_22905(-s, -s, s);
/*    */           textRenderer.method_27522((class_2561)class_5250, -textWidth / 2.0F, 0.0F, col, false, matrices.method_23760().method_23761(), consumers, class_327.class_6415.field_33994, -2147483648, 15728880);
/*    */           matrices.method_22909();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Waypoints.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */