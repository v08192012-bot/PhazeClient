/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ public final class SettingColorPickerWindow extends AbstractWindow {
/*     */   public static final float WINDOW_WIDTH = 126.0F;
/*     */   public static final float WINDOW_HEIGHT = 123.0F;
/*     */   public static final float WINDOW_HEIGHT_WITH_ALPHA = 138.0F;
/*     */   private static final float TITLE_SIZE = 7.0F;
/*     */   private static final float HEADER_HEIGHT = 24.0F;
/*     */   private static final float PICKER_X = 13.0F;
/*     */   private static final float PICKER_Y = 28.0F;
/*     */   private static final float PICKER_WIDTH = 100.0F;
/*     */   private static final float PICKER_HEIGHT = 66.0F;
/*     */   private static final float HUE_BAR_Y = 100.0F;
/*     */   private static final float HUE_BAR_HEIGHT = 7.0F;
/*     */   private static final float ALPHA_BAR_Y = 112.0F;
/*     */   private static final float ALPHA_BAR_HEIGHT = 7.0F;
/*  33 */   private static final class_2960 HUE_TEXTURE = class_2960.method_60654("textures/color_picker/hue.png");
/*     */   
/*     */   private static final float HUE_BAR_PADDING = 8.0F;
/*     */   
/*     */   private static final float PICKER_INDICATOR_SIZE = 9.0F;
/*     */   
/*     */   private static final float HUE_INDICATOR_WIDTH = 4.0F;
/*     */   
/*     */   private static final float HUE_INDICATOR_EXTRA_HEIGHT = 4.0F;
/*     */   
/*     */   private final ColorSetting setting;
/*     */   
/*     */   private float hue;
/*     */   private float saturation;
/*     */   private float brightness;
/*     */   private float alpha;
/*     */   private boolean pickerDragging;
/*     */   private boolean hueDragging;
/*     */   private boolean alphaDragging;
/*     */   
/*     */   public ColorSetting getSetting() {
/*  54 */     return this.setting;
/*     */   }
/*  56 */   public float getHue() { return this.hue; }
/*  57 */   public float getSaturation() { return this.saturation; }
/*  58 */   public float getBrightness() { return this.brightness; } public float getAlpha() {
/*  59 */     return this.alpha;
/*     */   }
/*  61 */   public boolean isPickerDragging() { return this.pickerDragging; }
/*  62 */   public boolean isHueDragging() { return this.hueDragging; } public boolean isAlphaDragging() {
/*  63 */     return this.alphaDragging;
/*     */   }
/*     */   public SettingColorPickerWindow(ColorSetting setting) {
/*  66 */     this.setting = setting;
/*  67 */     syncFromColor(setting.getColor());
/*  68 */     getScaleAnimation().setMs(200);
/*     */   }
/*     */   
/*     */   public static float getWindowHeight(ColorSetting setting) {
/*  72 */     return (setting != null && !setting.isNoAlpha()) ? 138.0F : 123.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawWindow(class_332 context, int mouseX, int mouseY, float delta) {
/*  77 */     class_4587 matrices = context.method_51448();
/*  78 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */     
/*  80 */     renderWindowBlur(matrices);
/*     */     
/*  82 */     int outline = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.BORDER_LIGHT, -1, 0.06F), this.globalAlpha * 0.96F);
/*  83 */     int panelFill = MenuStyle.withAlpha(MenuStyle.PANEL_BG, this.globalAlpha * 0.94F);
/*  84 */     int panelInner = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_BG_SOFT, MenuStyle.PANEL_CONTENT, 0.42F), this.globalAlpha * 0.98F);
/*  85 */     int pickerOutline = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.BORDER_LIGHT, -1, 0.1F), this.globalAlpha * 0.96F);
/*  86 */     int hueColor = MenuStyle.withAlpha(0xFF000000 | Color.HSBtoRGB(this.hue, 1.0F, 1.0F) & 0xFFFFFF, this.globalAlpha);
/*  87 */     int selectedOpaqueColor = MenuStyle.withAlpha(0xFF000000 | Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 0xFFFFFF, this.globalAlpha);
/*  88 */     int selectedAlphaColor = MenuStyle.withAlpha(Math.round(this.alpha * 255.0F) << 24 | Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 0xFFFFFF, this.globalAlpha);
/*     */     
/*  90 */     rectangle.render(ShapeProperties.create(matrices, this.x, this.y, this.width, this.height)
/*  91 */         .round(9.0F)
/*  92 */         .softness(1.1F)
/*  93 */         .thickness(1.15F)
/*  94 */         .outlineColor(outline)
/*  95 */         .color(panelFill)
/*  96 */         .build());
/*     */     
/*  98 */     rectangle.render(ShapeProperties.create(matrices, (this.x + 1.0F), (this.y + 1.0F), (this.width - 2.0F), (this.height - 2.0F))
/*  99 */         .round(8.0F)
/* 100 */         .thickness(0.0F)
/* 101 */         .color(panelInner)
/* 102 */         .build());
/*     */     
/* 104 */     String title = this.setting.getName();
/* 105 */     MsdfRenderer.renderText(
/* 106 */         MsdfFonts.bold(), title, 7.0F, 
/*     */ 
/*     */         
/* 109 */         MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.globalAlpha), positionMatrix, 
/*     */         
/* 111 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), title, 7.0F, this.x, this.width), 
/* 112 */         MenuStyle.centerMsdfTextY(7.0F, this.y, 24.0F), 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 116 */     float pickerX = this.x + 13.0F;
/* 117 */     float pickerY = this.y + 28.0F;
/* 118 */     float hueBarX = this.x + 8.0F;
/* 119 */     float hueBarY = this.y + 100.0F;
/* 120 */     float hueBarWidth = this.width - 16.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     int[] pickerColors = { MenuStyle.withAlpha(-16777216, this.globalAlpha), MenuStyle.withAlpha(-1, this.globalAlpha), MenuStyle.withAlpha(-16777216, this.globalAlpha), hueColor };
/*     */ 
/*     */ 
/*     */     
/* 129 */     rectangle.render(ShapeProperties.create(matrices, pickerX, pickerY, 100.0D, 66.0D)
/* 130 */         .round(8.0F)
/* 131 */         .softness(1.0F)
/* 132 */         .thickness(1.1F)
/* 133 */         .outlineColor(pickerOutline)
/* 134 */         .color(pickerColors)
/* 135 */         .build());
/*     */     
/* 137 */     renderHueStrip(matrices, hueBarX, hueBarY, hueBarWidth, 7.0F, pickerOutline);
/*     */     
/* 139 */     float pickerRadius = 4.5F;
/* 140 */     float indicatorX = class_3532.method_15363(pickerX + 100.0F * this.saturation, pickerX + pickerRadius, pickerX + 100.0F - pickerRadius);
/* 141 */     float indicatorY = class_3532.method_15363(pickerY + 66.0F * (1.0F - this.brightness), pickerY + pickerRadius, pickerY + 66.0F - pickerRadius);
/* 142 */     rectangle.render(ShapeProperties.create(matrices, (indicatorX - pickerRadius), (indicatorY - pickerRadius), 9.0D, 9.0D)
/* 143 */         .round(pickerRadius)
/* 144 */         .softness(1.0F)
/* 145 */         .thickness(2.0F)
/* 146 */         .outlineColor(MenuStyle.withAlpha(-1, this.globalAlpha))
/* 147 */         .color(selectedOpaqueColor)
/* 148 */         .build());
/*     */     
/* 150 */     float hueHalfWidth = 2.0F;
/* 151 */     float hueIndicatorX = class_3532.method_15363(hueBarX + hueBarWidth * this.hue, hueBarX + hueHalfWidth, hueBarX + hueBarWidth - hueHalfWidth);
/* 152 */     rectangle.render(ShapeProperties.create(matrices, (hueIndicatorX - hueHalfWidth), (hueBarY - 2.0F), 4.0D, 11.0D)
/* 153 */         .round(2.2F)
/* 154 */         .softness(1.0F)
/* 155 */         .thickness(1.4F)
/* 156 */         .outlineColor(MenuStyle.withAlpha(-1, this.globalAlpha))
/* 157 */         .color(MenuStyle.withAlpha(-3151873, this.globalAlpha))
/* 158 */         .build());
/*     */     
/* 160 */     if (!this.setting.isNoAlpha()) {
/* 161 */       float alphaBarX = this.x + 8.0F;
/* 162 */       float alphaBarY = this.y + 112.0F;
/* 163 */       float alphaBarWidth = this.width - 16.0F;
/* 164 */       renderAlphaStrip(matrices, alphaBarX, alphaBarY, alphaBarWidth, 7.0F, pickerOutline, selectedOpaqueColor);
/*     */       
/* 166 */       float alphaHalfWidth = 2.0F;
/* 167 */       float alphaIndicatorX = class_3532.method_15363(alphaBarX + alphaBarWidth * this.alpha, alphaBarX + alphaHalfWidth, alphaBarX + alphaBarWidth - alphaHalfWidth);
/* 168 */       rectangle.render(ShapeProperties.create(matrices, (alphaIndicatorX - alphaHalfWidth), (alphaBarY - 2.0F), 4.0D, 11.0D)
/* 169 */           .round(2.2F)
/* 170 */           .softness(1.0F)
/* 171 */           .thickness(1.4F)
/* 172 */           .outlineColor(MenuStyle.withAlpha(-1, this.globalAlpha))
/* 173 */           .color(selectedAlphaColor)
/* 174 */           .build());
/*     */     } 
/*     */     
/* 177 */     if (this.pickerDragging) {
/* 178 */       updatePicker(mouseX, mouseY);
/*     */     }
/* 180 */     if (this.hueDragging) {
/* 181 */       updateHue(mouseX);
/*     */     }
/* 183 */     if (this.alphaDragging) {
/* 184 */       updateAlpha(mouseX);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 190 */     draggable(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 24.0D));
/* 191 */     if (super.mouseClicked(mouseX, mouseY, button)) {
/* 192 */       return true;
/*     */     }
/*     */     
/* 195 */     if (button == 0) {
/* 196 */       if (MathUtil.isHovered(mouseX, mouseY, (this.x + 13.0F), (this.y + 28.0F), 100.0D, 66.0D)) {
/* 197 */         this.pickerDragging = true;
/* 198 */         updatePicker(mouseX, mouseY);
/* 199 */         return true;
/*     */       } 
/*     */       
/* 202 */       float hueBarX = this.x + 8.0F;
/* 203 */       if (MathUtil.isHovered(mouseX, mouseY, hueBarX, (this.y + 100.0F), (this.width - 16.0F), 7.0D)) {
/* 204 */         this.hueDragging = true;
/* 205 */         updateHue(mouseX);
/* 206 */         return true;
/*     */       } 
/*     */       
/* 209 */       if (!this.setting.isNoAlpha() && MathUtil.isHovered(mouseX, mouseY, hueBarX, (this.y + 112.0F), (this.width - 16.0F), 7.0D)) {
/* 210 */         this.alphaDragging = true;
/* 211 */         updateAlpha(mouseX);
/* 212 */         return true;
/*     */       } 
/*     */     } 
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 220 */     if (this.pickerDragging) {
/* 221 */       updatePicker(mouseX, mouseY);
/* 222 */       return true;
/*     */     } 
/* 224 */     if (this.hueDragging) {
/* 225 */       updateHue(mouseX);
/* 226 */       return true;
/*     */     } 
/* 228 */     if (this.alphaDragging) {
/* 229 */       updateAlpha(mouseX);
/* 230 */       return true;
/*     */     } 
/* 232 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 237 */     this.pickerDragging = false;
/* 238 */     this.hueDragging = false;
/* 239 */     this.alphaDragging = false;
/* 240 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */   
/*     */   private void updatePicker(double mouseX, double mouseY) {
/* 244 */     this.saturation = class_3532.method_15363((float)((mouseX - (this.x + 13.0F)) / 100.0D), 0.0F, 1.0F);
/* 245 */     this.brightness = class_3532.method_15363(1.0F - (float)((mouseY - (this.y + 28.0F)) / 66.0D), 0.0F, 1.0F);
/* 246 */     commitColor();
/*     */   }
/*     */   
/*     */   private void renderWindowBlur(class_4587 matrices) {
/* 250 */     float blurRadius = Theme.getInstance().getMenuBlurRadius();
/* 251 */     if (blurRadius <= 0.0F) {
/*     */       return;
/*     */     }
/* 254 */     Blur.INSTANCE.renderGaussianOverlay(ShapeProperties.create(matrices, this.x, this.y, this.width, this.height)
/* 255 */         .round(9.0F)
/* 256 */         .softness(1.1F)
/* 257 */         .quality(blurRadius * 2.0F)
/* 258 */         .color(MenuStyle.withAlpha(-1, this.globalAlpha))
/* 259 */         .build());
/*     */   }
/*     */   
/*     */   private void updateHue(double mouseX) {
/* 263 */     float hueBarX = this.x + 8.0F;
/* 264 */     float hueBarWidth = this.width - 16.0F;
/* 265 */     this.hue = class_3532.method_15363((float)((mouseX - hueBarX) / hueBarWidth), 0.0F, 1.0F);
/* 266 */     commitColor();
/*     */   }
/*     */   
/*     */   private void updateAlpha(double mouseX) {
/* 270 */     float alphaBarX = this.x + 8.0F;
/* 271 */     float alphaBarWidth = this.width - 16.0F;
/* 272 */     this.alpha = class_3532.method_15363((float)((mouseX - alphaBarX) / alphaBarWidth), 0.0F, 1.0F);
/* 273 */     commitColor();
/*     */   }
/*     */   
/*     */   private void commitColor() {
/* 277 */     int rgb = Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 0xFFFFFF;
/* 278 */     int packedAlpha = this.setting.isNoAlpha() ? -16777216 : (Math.round(this.alpha * 255.0F) << 24);
/* 279 */     this.setting.setColor(packedAlpha | rgb);
/*     */   }
/*     */   
/*     */   private void syncFromColor(int color) {
/* 283 */     float[] hsb = Color.RGBtoHSB(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, null);
/* 284 */     this.hue = hsb[0];
/* 285 */     this.saturation = hsb[1];
/* 286 */     this.brightness = hsb[2];
/* 287 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/*     */   }
/*     */   
/*     */   private void renderHueStrip(class_4587 matrices, float x, float y, float width, float height, int outlineColor) {
/* 291 */     float radius = 2.35F;
/* 292 */     rectangle.render(ShapeProperties.create(matrices, x, y, width, height)
/* 293 */         .round(radius)
/* 294 */         .softness(1.0F)
/* 295 */         .thickness(1.0F)
/* 296 */         .outlineColor(outlineColor)
/* 297 */         .color(MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_BG, -16777216, 0.24F), this.globalAlpha * 0.9F))
/* 298 */         .build());
/*     */     
/* 300 */     float innerInset = 0.6F;
/* 301 */     float innerX = x + innerInset;
/* 302 */     float innerY = y + innerInset;
/* 303 */     float innerWidth = Math.max(1.0F, width - innerInset * 2.0F);
/* 304 */     float innerHeight = Math.max(1.0F, height - innerInset * 2.0F);
/* 305 */     float capSize = innerHeight;
/* 306 */     float gradientX = innerX + capSize / 2.0F;
/* 307 */     float gradientWidth = Math.max(1.0F, innerWidth - capSize);
/*     */     
/* 309 */     renderHorizontalHueTexture(matrices, gradientX, innerY, gradientWidth, innerHeight, MenuStyle.withAlpha(-1, this.globalAlpha));
/*     */     
/* 311 */     int edgeColor = MenuStyle.withAlpha(-65536, this.globalAlpha);
/* 312 */     rectangle.render(ShapeProperties.create(matrices, innerX, innerY, capSize, innerHeight)
/* 313 */         .round(2.1F)
/* 314 */         .thickness(0.0F)
/* 315 */         .color(edgeColor)
/* 316 */         .build());
/* 317 */     rectangle.render(ShapeProperties.create(matrices, (innerX + innerWidth - capSize), innerY, capSize, innerHeight)
/* 318 */         .round(2.1F)
/* 319 */         .thickness(0.0F)
/* 320 */         .color(edgeColor)
/* 321 */         .build());
/*     */   }
/*     */   
/*     */   private void renderAlphaStrip(class_4587 matrices, float x, float y, float width, float height, int outlineColor, int opaqueColor) {
/* 325 */     float radius = 2.35F;
/* 326 */     int whiteBase = MenuStyle.withAlpha(-1, this.globalAlpha);
/* 327 */     rectangle.render(ShapeProperties.create(matrices, x, y, width, height)
/* 328 */         .round(radius)
/* 329 */         .softness(1.0F)
/* 330 */         .thickness(1.0F)
/* 331 */         .outlineColor(outlineColor)
/* 332 */         .color(whiteBase)
/* 333 */         .build());
/*     */     
/* 335 */     float innerInset = 0.6F;
/* 336 */     float innerX = x + innerInset;
/* 337 */     float innerY = y + innerInset;
/* 338 */     float innerWidth = Math.max(1.0F, width - innerInset * 2.0F);
/* 339 */     float innerHeight = Math.max(1.0F, height - innerInset * 2.0F);
/* 340 */     int transparentColor = opaqueColor & 0xFFFFFF;
/* 341 */     rectangle.render(ShapeProperties.create(matrices, innerX, innerY, innerWidth, innerHeight)
/* 342 */         .round(1.9F)
/* 343 */         .thickness(0.0F)
/* 344 */         .color(whiteBase)
/* 345 */         .build());
/* 346 */     rectangle.render(ShapeProperties.create(matrices, innerX, innerY, innerWidth, innerHeight)
/* 347 */         .round(1.9F)
/* 348 */         .thickness(0.0F)
/* 349 */         .color(new int[] { transparentColor, transparentColor, opaqueColor, opaqueColor
/* 350 */           }).build());
/*     */   }
/*     */   
/*     */   private static void renderHorizontalHueTexture(class_4587 matrices, float x, float y, float width, float height, int color) {
/* 354 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 356 */     RenderSystem.enableBlend();
/* 357 */     RenderSystem.defaultBlendFunc();
/* 358 */     RenderSystem.setShaderTexture(0, HUE_TEXTURE);
/* 359 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 361 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 362 */     GL11.glTexParameteri(3553, 10240, 9729);
/*     */     
/* 364 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 365 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 366 */     buffer.method_22918(matrix, x, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 367 */     buffer.method_22918(matrix, x, y + height, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 368 */     buffer.method_22918(matrix, x + width, y + height, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 369 */     buffer.method_22918(matrix, x + width, y, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 370 */     class_286.method_43433(buffer.method_60800());
/* 371 */     RenderSystem.disableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\SettingColorPickerWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */