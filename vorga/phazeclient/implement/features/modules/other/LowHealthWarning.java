/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import com.google.gson.JsonObject;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_9848;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public class LowHealthWarning extends Module {
/*  21 */   private static final LowHealthWarning INSTANCE = new LowHealthWarning();
/*  22 */   private static final class_2960 VIGNETTE_MASK = class_2960.method_60655("phaze", "low_health_vignette_mask");
/*     */   
/*     */   private static final int MASK_SIZE = 256;
/*     */   private static boolean vignetteMaskReady;
/*  26 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  27 */   public final SectionSetting soundSection = new SectionSetting("Audio");
/*  28 */   public final SectionSetting colorSection = new SectionSetting("Color");
/*     */   
/*  30 */   public final ValueSetting threshold = (new ValueSetting("Threshold (HP)", "HP threshold to activate warning (6.0 = 3 hearts)"))
/*  31 */     .range(2.0F, 14.0F)
/*  32 */     .step(1.0F)
/*  33 */     .setValue(6.0F);
/*     */   
/*  35 */   public final ColorSetting color = (new ColorSetting("Color", "Warning vignette tint and intensity (alpha)")).value(-637595375);
/*  36 */   public final ValueSetting pulseSpeed = (new ValueSetting("Pulse Speed", "Pulsing animation speed")).range(1.0F, 5.0F).step(0.5F).setValue(1.0F);
/*  37 */   public final BooleanSetting heartbeatSound = (new BooleanSetting("Heartbeat Sound", "Play immersive heartbeat sound while in critical state")).setValue(true);
/*  38 */   public final ValueSetting soundVolume = (new ValueSetting("Sound Volume", "Heartbeat sound volume")).range(10, 100).step(5.0F).setValue(75.0F);
/*     */   
/*  40 */   private class_1109 currentHeartbeatInstance = null;
/*  41 */   private long lastHeartbeatTime = 0L;
/*  42 */   private float activeHeartbeatVolume = -1.0F;
/*     */   
/*     */   private LowHealthWarning() {
/*  45 */     super("low_health_warning", "Low HP Warning", ModuleCategory.OTHER);
/*  46 */     this.threshold.setFullWidth(true);
/*  47 */     this.pulseSpeed.setFullWidth(true);
/*  48 */     this.heartbeatSound.setFullWidth(true);
/*  49 */     this.soundVolume.setFullWidth(true);
/*  50 */     this.color.setFullWidth(true);
/*  51 */     Objects.requireNonNull(this.heartbeatSound); this.soundVolume.visible(this.heartbeatSound::isValue);
/*  52 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.threshold, (Setting)this.pulseSpeed, (Setting)this.soundSection, (Setting)this.heartbeatSound, (Setting)this.soundVolume, (Setting)this.colorSection, (Setting)this.color });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LowHealthWarning getInstance() {
/*  58 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject migrateLegacySettings(JsonObject settings) {
/*  63 */     if (!settings.has("Intensity")) return settings; 
/*  64 */     JsonObject migrated = settings.deepCopy();
/*  65 */     migrated.remove("Intensity");
/*     */     try {
/*  67 */       float intensity = settings.get("Intensity").getAsFloat();
/*  68 */       if (!Float.isFinite(intensity)) return migrated; 
/*  69 */       int oldColor = settings.has("Color") ? settings.get("Color").getAsInt() : -61167;
/*  70 */       int alpha = Math.round((oldColor >>> 24) * class_3532.method_15363(intensity, 0.0F, 1.0F));
/*  71 */       migrated.addProperty("Color", Integer.valueOf(alpha << 24 | oldColor & 0xFFFFFF));
/*  72 */     } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */     
/*  75 */     return migrated;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  80 */     return "Smooth cinematic screen vignette and pulsing heartbeat when health is low";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  85 */     return "health_indicator.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*  90 */     stopHeartbeat();
/*     */   }
/*     */   
/*     */   public void stopHeartbeat() {
/*  94 */     if (this.currentHeartbeatInstance != null) {
/*     */       try {
/*  96 */         class_310.method_1551().method_1483().method_4870((class_1113)this.currentHeartbeatInstance);
/*  97 */       } catch (Throwable throwable) {}
/*  98 */       this.currentHeartbeatInstance = null;
/*     */     } 
/* 100 */     this.lastHeartbeatTime = 0L;
/* 101 */     this.activeHeartbeatVolume = -1.0F;
/*     */   }
/*     */   
/*     */   public boolean render(class_332 context) {
/* 105 */     if (!isEnabled()) {
/* 106 */       stopHeartbeat();
/* 107 */       return false;
/*     */     } 
/* 109 */     class_310 client = class_310.method_1551();
/* 110 */     if (client.field_1724 == null || client.field_1724.method_7337() || client.field_1724.method_7325()) {
/* 111 */       stopHeartbeat();
/* 112 */       return false;
/*     */     } 
/*     */     
/* 115 */     float hp = client.field_1724.method_6032();
/* 116 */     if (hp > this.threshold.getValue() || hp <= 0.0F) {
/* 117 */       stopHeartbeat();
/* 118 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     float hpRatio = class_3532.method_15363(hp / this.threshold.getValue(), 0.0F, 1.0F);
/* 123 */     float speedMultiplier = 1.0F + (1.0F - hpRatio) * 0.8F;
/*     */     
/* 125 */     double time = System.currentTimeMillis() / 1000.0D * (this.pulseSpeed.getValue() * speedMultiplier);
/* 126 */     float pulse = (float)((Math.sin(time * Math.PI * 2.0D) + 1.0D) * 0.5D);
/* 127 */     float baseAlpha = 0.4F + 0.6F * pulse;
/*     */     
/* 129 */     int argb = this.color.getColor();
/* 130 */     drawVignette(context, argb, baseAlpha);
/*     */ 
/*     */     
/* 133 */     if (this.heartbeatSound.isValue()) {
/* 134 */       boolean isPlaying = (this.currentHeartbeatInstance != null && client.method_1483().method_4877((class_1113)this.currentHeartbeatInstance));
/* 135 */       long now = System.currentTimeMillis();
/* 136 */       long intervalMs = (long)(1100.0F - (1.0F - hpRatio) * 550.0F);
/* 137 */       float requestedVolume = this.soundVolume.getValue() / 100.0F * (0.6F + (1.0F - hpRatio) * 0.4F);
/*     */ 
/*     */ 
/*     */       
/* 141 */       if (this.currentHeartbeatInstance != null && 
/* 142 */         Math.abs(requestedVolume - this.activeHeartbeatVolume) > 0.001F) {
/* 143 */         stopHeartbeat();
/* 144 */         this.lastHeartbeatTime = now - intervalMs;
/* 145 */         isPlaying = false;
/*     */       } 
/* 147 */       if (!isPlaying && now - this.lastHeartbeatTime >= intervalMs) {
/*     */ 
/*     */         
/* 150 */         stopHeartbeat();
/* 151 */         this.lastHeartbeatTime = now;
/* 152 */         float vol = requestedVolume;
/* 153 */         float pitch = 0.95F + (1.0F - hpRatio) * 0.25F;
/* 154 */         this.currentHeartbeatInstance = class_1109.method_4757(PhazeSounds.HEARTBEAT, pitch, vol);
/* 155 */         this.activeHeartbeatVolume = vol;
/* 156 */         client.method_1483().method_4873((class_1113)this.currentHeartbeatInstance);
/*     */       } 
/*     */     } else {
/* 159 */       stopHeartbeat();
/*     */     } 
/* 161 */     return (argb >>> 24 > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawVignette(class_332 context, int argb, float opacity) {
/* 166 */     int alpha = Math.round(class_3532.method_15363(opacity, 0.0F, 1.0F) * (argb >>> 24));
/* 167 */     if (alpha == 0)
/* 168 */       return;  ensureVignetteMask(class_310.method_1551());
/* 169 */     int tint = alpha << 24 | argb & 0xFFFFFF;
/* 170 */     context.method_25293(class_1921::method_62275, VIGNETTE_MASK, 0, 0, 0.0F, 0.0F, context
/* 171 */         .method_51421(), context.method_51443(), 256, 256, 256, 256, tint);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void ensureVignetteMask(class_310 client) {
/* 176 */     if (vignetteMaskReady) {
/*     */       return;
/*     */     }
/* 179 */     class_1011 image = new class_1011(256, 256, false);
/* 180 */     float center = 127.5F;
/* 181 */     for (int y = 0; y < 256; y++) {
/* 182 */       for (int x = 0; x < 256; x++) {
/* 183 */         float dx = (x - center) / center;
/* 184 */         float dy = (y - center) / center;
/* 185 */         float distance = class_3532.method_15363((float)Math.sqrt((dx * dx + dy * dy)), 0.0F, 1.0F);
/* 186 */         float alpha = class_3532.method_15363((distance - 0.42F) / 0.58F, 0.0F, 1.0F);
/* 187 */         image.method_61941(x, y, class_9848.method_61318(alpha, 1.0F, 1.0F, 1.0F));
/*     */       } 
/*     */     } 
/* 190 */     class_1043 texture = new class_1043(image);
/* 191 */     texture.method_65924(true);
/* 192 */     texture.method_4527(true, false);
/* 193 */     client.method_1531().method_4616(VIGNETTE_MASK, (class_1044)texture);
/* 194 */     vignetteMaskReady = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\LowHealthWarning.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */