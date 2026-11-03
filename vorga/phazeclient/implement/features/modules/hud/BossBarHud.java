/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_345;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_5348;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public final class BossBarHud
/*     */   extends RectHudModule
/*     */ {
/*  24 */   private static final BossBarHud INSTANCE = new BossBarHud();
/*  25 */   public static final class_2561 PREVIEW_NAME = (class_2561)class_2561.method_43470("Boss Bar");
/*     */   
/*     */   private static final long RAINBOW_CACHE_INTERVAL = 16666666L;
/*  28 */   public final BooleanSetting renderHealth = (new BooleanSetting("Render Health", "Show the boss health bar")).setValue(true);
/*  29 */   public final BooleanSetting renderText = (new BooleanSetting("Render Text", "Show the boss bar name")).setValue(false);
/*  30 */   public final SectionSetting colorOptions = new SectionSetting("Color");
/*  31 */   public final SelectSetting colorMode = (new SelectSetting(this, "Mode", "Boss bar color mode")
/*     */     {
/*     */       public boolean isFullWidth() {
/*  34 */         return isSelected("Rainbow");
/*     */       }
/*  36 */     }).value(new String[] { "Custom", "Rainbow" }).selected("Custom");
/*  37 */   public final ColorSetting fillColor = (new ColorSetting("Fill Color", "Boss bar fill color"))
/*  38 */     .value(-5614081).noAlpha().popupRow().visible(() -> Boolean.valueOf(this.colorMode.isSelected("Custom")));
/*  39 */   public final ValueSetting rainbowSpeed = (new ValueSetting("Rainbow Speed", "Speed of the boss bar rainbow cycle"))
/*  40 */     .range(0.1F, 1.0F).step(0.05F).setValue(0.5F).visible(() -> Boolean.valueOf(this.colorMode.isSelected("Rainbow")));
/*     */   
/*  42 */   private class_2561[] measuredNames = new class_2561[8];
/*  43 */   private int[] measuredWidths = new int[8];
/*  44 */   private int baseWidth = 182;
/*  45 */   private int baseHeight = 5;
/*     */   private int nativeX;
/*  47 */   private int nativeY = 12;
/*     */   private boolean liveBars;
/*     */   private boolean positionInitialized;
/*     */   private boolean layoutDirty = true;
/*  51 */   private int layoutScreenWidth = -1; private int layoutScreenHeight = -1; private int layoutRows; private boolean layoutHealth;
/*     */   private boolean layoutText;
/*  53 */   private long lastHueNanos = -1L;
/*     */   private float rainbowHue;
/*  55 */   private int activeFillColor = -5614081;
/*     */   
/*     */   private boolean cacheInitialized;
/*     */   private boolean lastEnabled;
/*     */   private boolean lastHealth;
/*     */   private boolean lastText;
/*     */   private boolean lastRainbow;
/*     */   private boolean lastChat;
/*     */   
/*     */   private BossBarHud() {
/*  65 */     super("bossbar_tweaker", "Boss Bar", 0.0F, 0.0F, 1.0F);
/*  66 */     settings().clear();
/*  67 */     this.background.setValue(false);
/*  68 */     this.rainbowSpeed.setFullWidth(true);
/*  69 */     setup(new Setting[] { (Setting)this.mainSection, (Setting)this.renderHealth, (Setting)this.renderText, (Setting)this.colorOptions, (Setting)this.colorMode, (Setting)this.fillColor, (Setting)this.rainbowSpeed });
/*     */   }
/*     */   private float lastX; private float lastY; private float lastScale; private float lastGuiScale; private float lastRainbowSpeed; private int lastCustomColor; private long lastCacheNanos;
/*     */   public static BossBarHud getInstance() {
/*  73 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  78 */     return "Customizable boss bar";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  83 */     return "scoreboard_hud.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRenderHudScale() {
/*  88 */     return getHudScale() * guiScale();
/*     */   }
/*     */   
/*     */   public int getBaseWidth() {
/*  92 */     return this.baseWidth;
/*     */   }
/*     */   
/*     */   public int getBaseHeight() {
/*  96 */     return this.baseHeight;
/*     */   }
/*     */   
/*     */   public boolean hasLiveBars() {
/* 100 */     return this.liveBars;
/*     */   }
/*     */   
/*     */   public int getActiveFillColor() {
/* 104 */     return this.activeFillColor;
/*     */   }
/*     */   
/*     */   public void updateLayout(class_310 client, Collection<class_345> bars) {
/* 108 */     int scaledWidth = client.method_22683().method_4486();
/* 109 */     int scaledHeight = client.method_22683().method_4502();
/* 110 */     int maxRows = Math.max(1, (scaledHeight / 3 - 12 + 18) / 19);
/* 111 */     int rows = Math.min(Math.max(1, bars.size()), maxRows);
/* 112 */     this.liveBars = !bars.isEmpty();
/* 113 */     boolean health = this.renderHealth.isValue(), text = this.renderText.isValue();
/* 114 */     if (!this.layoutDirty && scaledWidth == this.layoutScreenWidth && scaledHeight == this.layoutScreenHeight && rows == this.layoutRows && health == this.layoutHealth && text == this.layoutText) {
/*     */       
/* 116 */       updateDefaultPosition(scaledWidth);
/*     */       return;
/*     */     } 
/* 119 */     this.layoutDirty = false;
/* 120 */     this.layoutScreenWidth = scaledWidth;
/* 121 */     this.layoutScreenHeight = scaledHeight;
/* 122 */     this.layoutRows = rows;
/* 123 */     this.layoutHealth = health;
/* 124 */     this.layoutText = text;
/* 125 */     this.baseWidth = 182;
/* 126 */     if (text) {
/* 127 */       if (rows > this.measuredNames.length) {
/* 128 */         this.measuredNames = Arrays.<class_2561>copyOf(this.measuredNames, rows);
/* 129 */         this.measuredWidths = Arrays.copyOf(this.measuredWidths, rows);
/*     */       } 
/* 131 */       int index = 0;
/* 132 */       if (this.liveBars)
/* 133 */       { for (class_345 bar : bars) {
/* 134 */           measureName(client, index++, bar.method_5414());
/* 135 */           if (index == rows)
/*     */             break; 
/*     */         }  }
/* 138 */       else { measureName(client, 0, PREVIEW_NAME); }
/*     */     
/*     */     } 
/* 141 */     this.nativeX = scaledWidth / 2 - this.baseWidth / 2;
/* 142 */     this.nativeY = text ? 3 : 12;
/* 143 */     int rowHeight = text ? (health ? 14 : 9) : 5;
/* 144 */     this.baseHeight = (rows - 1) * 19 + rowHeight;
/* 145 */     updateDefaultPosition(scaledWidth);
/*     */   }
/*     */   
/*     */   private void updateDefaultPosition(int scaledWidth) {
/* 149 */     if (!this.positionInitialized) {
/* 150 */       float scale = getRenderHudScale();
/* 151 */       super.setHudX(Math.max(0.0F, (scaledWidth / 2) * guiScale() - this.baseWidth * scale / 2.0F));
/* 152 */       super.setHudY(this.nativeY * guiScale());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void measureName(class_310 client, int index, class_2561 name) {
/* 157 */     if (this.measuredNames[index] != name) {
/* 158 */       this.measuredNames[index] = name;
/* 159 */       this.measuredWidths[index] = client.field_1772.method_27525((class_5348)name);
/*     */     } 
/* 161 */     this.baseWidth = Math.max(this.baseWidth, this.measuredWidths[index] + 3 & 0xFFFFFFFE);
/*     */   }
/*     */   
/*     */   public int getNameWidth(class_327 renderer, class_5348 name) {
/* 165 */     for (int index = 0; index < this.layoutRows; index++) {
/* 166 */       if (this.measuredNames[index] == name) return this.measuredWidths[index]; 
/*     */     } 
/* 168 */     return renderer.method_27525(name);
/*     */   }
/*     */   
/*     */   public void invalidateNameWidths() {
/* 172 */     Arrays.fill((Object[])this.measuredNames, (Object)null);
/* 173 */     this.layoutDirty = true;
/*     */   }
/*     */   
/*     */   public void applyTransform(class_332 context) {
/* 177 */     float guiScale = guiScale();
/* 178 */     context.method_51448().method_46416(getHudX() / guiScale, getHudY() / guiScale, 0.0F);
/* 179 */     context.method_51448().method_22905(getHudScale(), getHudScale(), 1.0F);
/* 180 */     context.method_51448().method_46416(-this.nativeX, -this.nativeY, 0.0F);
/*     */   }
/*     */   
/*     */   public void beginFrame(long nowNanos) {
/* 184 */     class_310 client = class_310.method_1551();
/* 185 */     boolean rainbow = (isEnabled() && this.renderHealth.isValue() && this.colorMode.isSelected("Rainbow") && (this.liveBars || (client != null && client.field_1755 instanceof net.minecraft.class_408)));
/*     */     
/* 187 */     if (rainbow) {
/* 188 */       if (this.lastHueNanos >= 0L) {
/* 189 */         this.rainbowHue = advanceRainbowHue(this.rainbowHue, nowNanos - this.lastHueNanos, this.rainbowSpeed.getValue());
/*     */       }
/* 191 */       this.lastHueNanos = nowNanos;
/* 192 */       this.activeFillColor = 0xFF000000 | class_3532.method_15369(this.rainbowHue, 1.0F, 1.0F);
/*     */     } else {
/* 194 */       this.lastHueNanos = -1L;
/* 195 */       this.activeFillColor = this.fillColor.getColor() | 0xFF000000;
/*     */     } 
/*     */   }
/*     */   
/*     */   static float rainbowCycleSeconds(float speed) {
/* 200 */     float value = class_3532.method_15363(speed, 0.1F, 1.0F);
/* 201 */     return (value <= 0.5F) ? class_3532.method_16439((value - 0.1F) / 0.4F, 20.0F, 5.0F) : 
/* 202 */       class_3532.method_16439((value - 0.5F) / 0.5F, 5.0F, 1.0F);
/*     */   }
/*     */   
/*     */   static float advanceRainbowHue(float hue, long elapsedNanos, float speed) {
/* 206 */     return (float)((hue + Math.max(0L, elapsedNanos) / 1.0E9D * rainbowCycleSeconds(speed)) % 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRefreshExordium(long nowNanos) {
/* 211 */     class_310 client = class_310.method_1551();
/* 212 */     boolean enabled = isEnabled();
/* 213 */     boolean health = this.renderHealth.isValue();
/* 214 */     boolean text = this.renderText.isValue();
/* 215 */     boolean rainbow = this.colorMode.isSelected("Rainbow");
/* 216 */     boolean chat = (client != null && client.field_1755 instanceof net.minecraft.class_408);
/* 217 */     float x = getHudX(), y = getHudY(), scale = getHudScale(), guiScale = guiScale();
/* 218 */     int customColor = this.fillColor.getColor();
/* 219 */     float speed = this.rainbowSpeed.getValue();
/* 220 */     boolean changed = (!this.cacheInitialized || enabled != this.lastEnabled || health != this.lastHealth || text != this.lastText || rainbow != this.lastRainbow || chat != this.lastChat || x != this.lastX || y != this.lastY || scale != this.lastScale || guiScale != this.lastGuiScale || customColor != this.lastCustomColor || speed != this.lastRainbowSpeed);
/*     */ 
/*     */     
/* 223 */     boolean animate = (enabled && health && this.liveBars && rainbow && nowNanos - this.lastCacheNanos >= 16666666L);
/* 224 */     this.cacheInitialized = true;
/* 225 */     this.lastEnabled = enabled;
/* 226 */     this.lastHealth = health;
/* 227 */     this.lastText = text;
/* 228 */     this.lastRainbow = rainbow;
/* 229 */     this.lastChat = chat;
/* 230 */     this.lastX = x;
/* 231 */     this.lastY = y;
/* 232 */     this.lastScale = scale;
/* 233 */     this.lastGuiScale = guiScale;
/* 234 */     this.lastCustomColor = customColor;
/* 235 */     this.lastRainbowSpeed = speed;
/* 236 */     if (changed || animate) this.lastCacheNanos = nowNanos; 
/* 237 */     return (changed || animate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHudX(float x) {
/* 242 */     if (x != getHudX()) this.positionInitialized = true; 
/* 243 */     super.setHudX(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHudY(float y) {
/* 248 */     if (y != getHudY()) this.positionInitialized = true; 
/* 249 */     super.setHudY(y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHudPosition(float x, float y, int width, int height) {
/* 254 */     this.positionInitialized = true;
/* 255 */     super.setHudPosition(x, y, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHudPositionRatio(float x, float y) {
/* 260 */     this.positionInitialized = true;
/* 261 */     super.setHudPositionRatio(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetHudTransform() {
/* 266 */     super.resetHudTransform();
/* 267 */     this.positionInitialized = false;
/*     */   }
/*     */   
/*     */   public boolean isHudPositionAutomatic() {
/* 271 */     return !this.positionInitialized;
/*     */   }
/*     */   
/*     */   public void resetAutomaticPosition() {
/* 275 */     this.positionInitialized = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject migrateLegacyData(JsonObject source) {
/* 280 */     class_310 client = class_310.method_1551();
/* 281 */     int width = (client == null || client.method_22683() == null) ? 0 : client.method_22683().method_4480();
/* 282 */     int height = (client == null || client.method_22683() == null) ? 0 : client.method_22683().method_4507();
/* 283 */     return migrateLegacyData(source, width, height, guiScale());
/*     */   }
/*     */   
/*     */   static JsonObject migrateLegacyData(JsonObject source, int width, int height, float guiScale) {
/* 287 */     boolean nested = (source.has("settings") && source.get("settings").isJsonObject());
/* 288 */     JsonObject oldSettings = nested ? source.getAsJsonObject("settings") : source;
/* 289 */     if (!oldSettings.has("Scale") && !oldSettings.has("Y Offset") && !oldSettings.has("Hide Bossbar")) return source; 
/* 290 */     JsonObject data = source.deepCopy();
/* 291 */     JsonObject settings = nested ? data.getAsJsonObject("settings") : data;
/* 292 */     float scale = class_3532.method_15363(legacyNumber(settings.get("Scale"), 1.0F), 0.5F, 3.0F);
/* 293 */     if (!data.has("hud_scale")) { data.addProperty("hud_scale", Float.valueOf(scale)); }
/* 294 */     else { scale = class_3532.method_15363(legacyNumber(data.get("hud_scale"), scale), 0.5F, 3.0F); }
/*     */     
/* 296 */     boolean hasPosition = (data.has("hud_x_ratio") || data.has("hud_y_ratio") || data.has("hud_x") || data.has("hud_y") || data.has("hud_ref_w") || data.has("hud_ref_h") || data.has("hud_auto_position"));
/* 297 */     if (!hasPosition && width > 0 && height > 0) {
/* 298 */       float offset = legacyNumber(settings.get("Y Offset"), 0.0F);
/* 299 */       data.addProperty("hud_x", Float.valueOf(width * 0.5F - 91.0F * scale * guiScale));
/* 300 */       data.addProperty("hud_y", Float.valueOf((12.0F * scale + offset) * guiScale));
/* 301 */       data.addProperty("hud_ref_w", Integer.valueOf(width));
/* 302 */       data.addProperty("hud_ref_h", Integer.valueOf(height));
/* 303 */       data.addProperty("hud_auto_position", Boolean.valueOf(false));
/*     */     } 
/* 305 */     JsonElement hide = settings.get("Hide Bossbar");
/* 306 */     if (hide != null && hide.isJsonPrimitive() && hide.getAsJsonPrimitive().isBoolean()) {
/* 307 */       boolean hidden = hide.getAsBoolean();
/* 308 */       if (!settings.has("Render Health")) settings.addProperty("Render Health", Boolean.valueOf(!hidden)); 
/* 309 */       if (hidden && !settings.has("Render Text")) settings.addProperty("Render Text", Boolean.valueOf(false)); 
/*     */     } 
/* 311 */     settings.remove("Scale");
/* 312 */     settings.remove("Y Offset");
/* 313 */     settings.remove("Hide Bossbar");
/* 314 */     return data;
/*     */   }
/*     */   
/*     */   private static float legacyNumber(JsonElement value, float fallback) {
/* 318 */     if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) return fallback; 
/* 319 */     float number = value.getAsFloat();
/* 320 */     return Float.isFinite(number) ? number : fallback;
/*     */   }
/*     */   
/*     */   private static float guiScale() {
/* 324 */     class_310 client = class_310.method_1551();
/* 325 */     return (client == null || client.method_22683() == null) ? 1.0F : (float)client.method_22683().method_4495();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\BossBarHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */