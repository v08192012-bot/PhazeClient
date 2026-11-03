/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import java.util.ArrayList;
/*     */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
/*     */ import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
/*     */ import net.minecraft.class_10075;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5348;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionf;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public class TntCountdown extends Module {
/*  25 */   private static final TntCountdown INSTANCE = new TntCountdown();
/*  26 */   private static final List<PendingTimer> PENDING_TIMERS = new ArrayList<>();
/*     */   
/*  28 */   public final BooleanSetting showText = (new BooleanSetting("Show Text", "Show the countdown above primed TNT")).setValue(true);
/*  29 */   public final BooleanSetting showBackground = (new BooleanSetting("Text Background", "Show a dark background behind the countdown")).setValue(true);
/*  30 */   public final SelectSetting timeFormat = (new SelectSetting("Time Format", "Countdown format"))
/*  31 */     .value(new String[] { "0.0s", "0.00s", "Ticks" }).selected("0.00s");
/*  32 */   public final BooleanSetting dynamicColor = (new BooleanSetting("Dynamic Color", "Shift green -> yellow -> red as fuse runs down")).setValue(true);
/*  33 */   public final ColorSetting staticColor = (new ColorSetting("Color", "Custom countdown text color")).value(-52429);
/*  34 */   public final ValueSetting scale = (new ValueSetting("Scale", "Size of the countdown tag")).range(0.5F, 2.5F).step(0.1F).setValue(1.0F);
/*     */   
/*     */   private TntCountdown() {
/*  37 */     super("tnt_countdown", "TNT Countdown", ModuleCategory.OTHER);
/*  38 */     setup(new Setting[] { (Setting)this.showText, (Setting)this.showBackground, (Setting)this.timeFormat, (Setting)this.dynamicColor, (Setting)this.staticColor, (Setting)this.scale });
/*     */ 
/*     */ 
/*     */     
/*  42 */     WorldRenderEvents.AFTER_TRANSLUCENT.register(TntCountdown::renderPendingTimers);
/*     */   }
/*     */   
/*     */   public static TntCountdown getInstance() {
/*  46 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public String getDisplayText(float fuse) {
/*  50 */     float seconds = Math.max(0.0F, fuse / 20.0F);
/*  51 */     switch (this.timeFormat.getSelected()) { case "0.0s": case "Ticks":  }  return 
/*     */ 
/*     */       
/*  54 */       String.format(Locale.ROOT, "%.2fs", new Object[] { Float.valueOf(seconds) });
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayColor(float fuse) {
/*  59 */     if (!this.dynamicColor.isValue()) return this.staticColor.getColor(); 
/*  60 */     if (fuse > 40.0F) return -16711800; 
/*  61 */     if (fuse > 20.0F) return -22016; 
/*  62 */     return -56798;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabledByDefault() {
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  72 */     return "Display billboard detonation timer above primed TNT entities";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  77 */     return "trap_timer.png";
/*     */   }
/*     */   
/*     */   public static void renderTimer(class_10075 state, class_4587 matrices, class_4597 vertexConsumers, Quaternionf cameraRotation, class_327 textRenderer) {
/*  81 */     float fuse = state.field_53596;
/*  82 */     TntCountdown module = getInstance();
/*  83 */     if (module == null || !module.showText.isValue())
/*     */       return; 
/*  85 */     float seconds = Math.max(0.0F, fuse / 20.0F);
/*  86 */     int color = module.getDisplayColor(fuse);
/*  87 */     String formatted = module.getDisplayText(fuse);
/*  88 */     class_5250 class_5250 = class_2561.method_43470(formatted);
/*  89 */     int textWidth = textRenderer.method_27525((class_5348)class_5250);
/*     */     
/*  91 */     matrices.method_22903();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     class_243 labelPos = (state.field_53338 != null) ? state.field_53338 : new class_243(0.0D, 1.0D, 0.0D);
/*  97 */     matrices.method_22904(labelPos.field_1352, labelPos.field_1351 + 0.5D, labelPos.field_1350);
/*  98 */     matrices.method_22907(cameraRotation);
/*  99 */     float s = 0.025F * module.scale.getValue();
/* 100 */     matrices.method_22905(s, -s, s);
/*     */     
/* 102 */     Matrix4f posMatrix = matrices.method_23760().method_23761();
/* 103 */     float x = -textWidth / 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     int background = module.showBackground.isValue() ? -1879048192 : 0;
/* 109 */     PENDING_TIMERS.add(new PendingTimer(new Matrix4f((Matrix4fc)posMatrix), textRenderer, (class_2561)class_5250, x, color, background));
/*     */ 
/*     */ 
/*     */     
/* 113 */     matrices.method_22909();
/*     */   }
/*     */   
/*     */   private static void renderPendingTimers(WorldRenderContext context) {
/* 117 */     if (PENDING_TIMERS.isEmpty())
/*     */       return; 
/* 119 */     class_4597 consumers = context.consumers();
/* 120 */     if (consumers == null) {
/* 121 */       PENDING_TIMERS.clear();
/*     */       
/*     */       return;
/*     */     } 
/* 125 */     for (PendingTimer timer : PENDING_TIMERS) {
/*     */ 
/*     */ 
/*     */       
/* 129 */       timer.textRenderer.method_27522(timer.text, timer.x, 0.0F, timer.color, false, timer.matrix, consumers, class_327.class_6415.field_33994, timer.backgroundColor, 15728880);
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
/* 141 */       timer.textRenderer.method_27522(timer.text, timer.x, 0.0F, timer.color, false, timer.matrix, consumers, class_327.class_6415.field_33993, 0, 15728880);
/*     */     } 
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
/* 155 */     PENDING_TIMERS.clear();
/*     */   }
/*     */   private static final class PendingTimer extends Record { private final Matrix4f matrix; private final class_327 textRenderer; private final class_2561 text; private final float x; private final int color; private final int backgroundColor;
/* 158 */     private PendingTimer(Matrix4f matrix, class_327 textRenderer, class_2561 text, float x, int color, int backgroundColor) { this.matrix = matrix; this.textRenderer = textRenderer; this.text = text; this.x = x; this.color = color; this.backgroundColor = backgroundColor; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/TntCountdown$PendingTimer;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 158 */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/TntCountdown$PendingTimer; } public Matrix4f matrix() { return this.matrix; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/TntCountdown$PendingTimer;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/TntCountdown$PendingTimer; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/TntCountdown$PendingTimer;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/TntCountdown$PendingTimer;
/* 158 */       //   0	8	1	o	Ljava/lang/Object; } public class_327 textRenderer() { return this.textRenderer; } public class_2561 text() { return this.text; } public float x() { return this.x; } public int color() { return this.color; } public int backgroundColor() { return this.backgroundColor; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\TntCountdown.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */