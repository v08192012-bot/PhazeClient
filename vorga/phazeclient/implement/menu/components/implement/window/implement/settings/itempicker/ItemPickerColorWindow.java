/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.itempicker;
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
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ItemPickerSetting;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ public final class ItemPickerColorWindow extends AbstractWindow {
/*     */   public static final float WINDOW_WIDTH = 126.0F;
/*     */   public static final float WINDOW_HEIGHT = 123.0F;
/*     */   private static final float TITLE_SIZE = 7.0F;
/*     */   private static final float HEADER_HEIGHT = 24.0F;
/*     */   private static final float PICKER_X = 13.0F;
/*     */   private static final float PICKER_Y = 28.0F;
/*     */   private static final float PICKER_WIDTH = 100.0F;
/*     */   private static final float PICKER_HEIGHT = 66.0F;
/*     */   private static final float HUE_BAR_Y = 100.0F;
/*     */   private static final float HUE_BAR_HEIGHT = 7.0F;
/*  31 */   private static final class_2960 HUE_TEXTURE = class_2960.method_60654("textures/color_picker/hue.png");
/*     */   
/*     */   private static final float HUE_BAR_PADDING = 8.0F;
/*     */   
/*     */   private static final float PICKER_INDICATOR_SIZE = 9.0F;
/*     */   
/*     */   private static final float HUE_INDICATOR_WIDTH = 4.0F;
/*     */   
/*     */   private static final float HUE_INDICATOR_EXTRA_HEIGHT = 4.0F;
/*     */   private final ItemPickerSetting setting;
/*     */   private float hue;
/*     */   private float saturation;
/*     */   private float brightness;
/*     */   private boolean pickerDragging;
/*     */   private boolean hueDragging;
/*     */   
/*     */   public ItemPickerSetting getSetting() {
/*  48 */     return this.setting;
/*     */   }
/*  50 */   public float getHue() { return this.hue; }
/*  51 */   public float getSaturation() { return this.saturation; } public float getBrightness() {
/*  52 */     return this.brightness;
/*     */   }
/*  54 */   public boolean isPickerDragging() { return this.pickerDragging; } public boolean isHueDragging() {
/*  55 */     return this.hueDragging;
/*     */   }
/*     */   public ItemPickerColorWindow(ItemPickerSetting setting) {
/*  58 */     this.setting = setting;
/*  59 */     syncFromColor(setting.getHighlightColor());
/*  60 */     getScaleAnimation().setMs(200);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawWindow(class_332 context, int mouseX, int mouseY, float delta) {
/*  65 */     class_4587 matrices = context.method_51448();
/*  66 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */     
/*  68 */     int outline = applyGlobalAlpha(opaque(MenuStyle.mix(MenuStyle.BORDER, -1, 0.08F)));
/*  69 */     int panelFill = applyGlobalAlpha(opaque(MenuStyle.mix(MenuStyle.PANEL_BG, -16777216, 0.18F)));
/*  70 */     int panelTop = panelFill;
/*  71 */     int pickerOutline = applyGlobalAlpha(opaque(MenuStyle.mix(MenuStyle.BORDER_LIGHT, -1, 0.1F)));
/*  72 */     int hueColor = applyGlobalAlpha(0xFF000000 | Color.HSBtoRGB(this.hue, 1.0F, 1.0F) & 0xFFFFFF);
/*  73 */     int selectedColor = applyGlobalAlpha(0xFF000000 | Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 0xFFFFFF);
/*     */     
/*  75 */     rectangle.render(ShapeProperties.create(matrices, this.x, this.y, this.width, this.height)
/*  76 */         .round(9.0F)
/*  77 */         .softness(1.1F)
/*  78 */         .thickness(1.15F)
/*  79 */         .outlineColor(outline)
/*  80 */         .color(panelFill)
/*  81 */         .build());
/*     */     
/*  83 */     rectangle.render(ShapeProperties.create(matrices, this.x, this.y, this.width, 24.0D)
/*  84 */         .round(9.0F)
/*  85 */         .thickness(0.0F)
/*  86 */         .color(panelTop)
/*  87 */         .build());
/*     */     
/*  89 */     String title = this.setting.getRowTitle();
/*  90 */     MsdfRenderer.renderText(
/*  91 */         MsdfFonts.bold(), title, 7.0F, 
/*     */ 
/*     */         
/*  94 */         applyGlobalAlpha(MenuStyle.TEXT_PRIMARY), positionMatrix, 
/*     */         
/*  96 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), title, 7.0F, this.x, this.width), 
/*  97 */         MenuStyle.centerMsdfTextY(7.0F, this.y, 24.0F), 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 101 */     float pickerX = this.x + 13.0F;
/* 102 */     float pickerY = this.y + 28.0F;
/* 103 */     float hueBarX = this.x + 8.0F;
/* 104 */     float hueBarY = this.y + 100.0F;
/* 105 */     float hueBarWidth = this.width - 16.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     int[] pickerColors = { applyGlobalAlpha(-16777216), applyGlobalAlpha(-1), applyGlobalAlpha(-16777216), hueColor };
/*     */ 
/*     */ 
/*     */     
/* 114 */     rectangle.render(ShapeProperties.create(matrices, pickerX, pickerY, 100.0D, 66.0D)
/* 115 */         .round(8.0F)
/* 116 */         .softness(1.0F)
/* 117 */         .thickness(1.1F)
/* 118 */         .outlineColor(pickerOutline)
/* 119 */         .color(pickerColors)
/* 120 */         .build());
/*     */     
/* 122 */     renderHueStrip(matrices, hueBarX, hueBarY, hueBarWidth, 7.0F, pickerOutline);
/*     */     
/* 124 */     float pickerRadius = 4.5F;
/* 125 */     float indicatorX = class_3532.method_15363(pickerX + 100.0F * this.saturation, pickerX + pickerRadius, pickerX + 100.0F - pickerRadius);
/* 126 */     float indicatorY = class_3532.method_15363(pickerY + 66.0F * (1.0F - this.brightness), pickerY + pickerRadius, pickerY + 66.0F - pickerRadius);
/* 127 */     rectangle.render(ShapeProperties.create(matrices, (indicatorX - pickerRadius), (indicatorY - pickerRadius), 9.0D, 9.0D)
/* 128 */         .round(pickerRadius)
/* 129 */         .softness(1.0F)
/* 130 */         .thickness(2.0F)
/* 131 */         .outlineColor(applyGlobalAlpha(-1))
/* 132 */         .color(selectedColor)
/* 133 */         .build());
/*     */     
/* 135 */     float hueHalfWidth = 2.0F;
/* 136 */     float hueIndicatorX = class_3532.method_15363(hueBarX + hueBarWidth * this.hue, hueBarX + hueHalfWidth, hueBarX + hueBarWidth - hueHalfWidth);
/* 137 */     rectangle.render(ShapeProperties.create(matrices, (hueIndicatorX - hueHalfWidth), (hueBarY - 2.0F), 4.0D, 11.0D)
/* 138 */         .round(2.0F)
/* 139 */         .softness(1.0F)
/* 140 */         .thickness(1.4F)
/* 141 */         .outlineColor(applyGlobalAlpha(-1))
/* 142 */         .color(applyGlobalAlpha(-3151873))
/* 143 */         .build());
/*     */     
/* 145 */     if (this.pickerDragging) {
/* 146 */       updatePicker(mouseX, mouseY);
/*     */     }
/* 148 */     if (this.hueDragging) {
/* 149 */       updateHue(mouseX);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 155 */     draggable(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 24.0D));
/* 156 */     if (super.mouseClicked(mouseX, mouseY, button)) {
/* 157 */       return true;
/*     */     }
/*     */     
/* 160 */     if (button == 0) {
/* 161 */       if (MathUtil.isHovered(mouseX, mouseY, (this.x + 13.0F), (this.y + 28.0F), 100.0D, 66.0D)) {
/* 162 */         this.pickerDragging = true;
/* 163 */         updatePicker(mouseX, mouseY);
/* 164 */         return true;
/*     */       } 
/*     */       
/* 167 */       float hueBarX = this.x + 8.0F;
/* 168 */       if (MathUtil.isHovered(mouseX, mouseY, hueBarX, (this.y + 100.0F), (this.width - 16.0F), 7.0D)) {
/* 169 */         this.hueDragging = true;
/* 170 */         updateHue(mouseX);
/* 171 */         return true;
/*     */       } 
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 179 */     if (this.pickerDragging) {
/* 180 */       updatePicker(mouseX, mouseY);
/* 181 */       return true;
/*     */     } 
/* 183 */     if (this.hueDragging) {
/* 184 */       updateHue(mouseX);
/* 185 */       return true;
/*     */     } 
/* 187 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 192 */     this.pickerDragging = false;
/* 193 */     this.hueDragging = false;
/* 194 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */   
/*     */   private void updatePicker(double mouseX, double mouseY) {
/* 198 */     this.saturation = class_3532.method_15363((float)((mouseX - (this.x + 13.0F)) / 100.0D), 0.0F, 1.0F);
/* 199 */     this.brightness = class_3532.method_15363(1.0F - (float)((mouseY - (this.y + 28.0F)) / 66.0D), 0.0F, 1.0F);
/* 200 */     commitColor();
/*     */   }
/*     */   
/*     */   private void updateHue(double mouseX) {
/* 204 */     float hueBarX = this.x + 8.0F;
/* 205 */     float hueBarWidth = this.width - 16.0F;
/* 206 */     this.hue = class_3532.method_15363((float)((mouseX - hueBarX) / hueBarWidth), 0.0F, 1.0F);
/* 207 */     commitColor();
/*     */   }
/*     */   
/*     */   private void commitColor() {
/* 211 */     this.setting.setHighlightColor(0xFF000000 | Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 0xFFFFFF);
/*     */   }
/*     */   
/*     */   private void syncFromColor(int color) {
/* 215 */     float[] hsb = Color.RGBtoHSB(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, null);
/* 216 */     this.hue = hsb[0];
/* 217 */     this.saturation = hsb[1];
/* 218 */     this.brightness = hsb[2];
/*     */   }
/*     */   
/*     */   private void renderHueStrip(class_4587 matrices, float x, float y, float width, float height, int outlineColor) {
/* 222 */     float radius = 2.35F;
/* 223 */     rectangle.render(ShapeProperties.create(matrices, x, y, width, height)
/* 224 */         .round(radius)
/* 225 */         .softness(1.0F)
/* 226 */         .thickness(1.0F)
/* 227 */         .outlineColor(outlineColor)
/* 228 */         .color(applyGlobalAlpha(opaque(MenuStyle.mix(MenuStyle.PANEL_BG, -16777216, 0.24F))))
/* 229 */         .build());
/*     */     
/* 231 */     float innerInset = 0.6F;
/* 232 */     float innerX = x + innerInset;
/* 233 */     float innerY = y + innerInset;
/* 234 */     float innerWidth = Math.max(1.0F, width - innerInset * 2.0F);
/* 235 */     float innerHeight = Math.max(1.0F, height - innerInset * 2.0F);
/* 236 */     float capSize = innerHeight;
/* 237 */     float gradientX = innerX + capSize / 2.0F;
/* 238 */     float gradientWidth = Math.max(1.0F, innerWidth - capSize);
/*     */     
/* 240 */     renderHorizontalHueTexture(matrices, gradientX, innerY, gradientWidth, innerHeight, applyGlobalAlpha(-1));
/*     */     
/* 242 */     int edgeColor = applyGlobalAlpha(-65536);
/* 243 */     rectangle.render(ShapeProperties.create(matrices, innerX, innerY, capSize, innerHeight)
/* 244 */         .round(2.1F)
/* 245 */         .thickness(0.0F)
/* 246 */         .color(edgeColor)
/* 247 */         .build());
/* 248 */     rectangle.render(ShapeProperties.create(matrices, (innerX + innerWidth - capSize), innerY, capSize, innerHeight)
/* 249 */         .round(2.1F)
/* 250 */         .thickness(0.0F)
/* 251 */         .color(edgeColor)
/* 252 */         .build());
/*     */   }
/*     */   
/*     */   private static void renderHorizontalHueTexture(class_4587 matrices, float x, float y, float width, float height, int color) {
/* 256 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 258 */     RenderSystem.enableBlend();
/* 259 */     RenderSystem.defaultBlendFunc();
/* 260 */     RenderSystem.setShaderTexture(0, HUE_TEXTURE);
/* 261 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 263 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 264 */     GL11.glTexParameteri(3553, 10240, 9729);
/*     */     
/* 266 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 267 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 268 */     buffer.method_22918(matrix, x, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 269 */     buffer.method_22918(matrix, x, y + height, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 270 */     buffer.method_22918(matrix, x + width, y + height, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 271 */     buffer.method_22918(matrix, x + width, y, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 272 */     class_286.method_43433(buffer.method_60800());
/* 273 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private static int opaque(int color) {
/* 277 */     return 0xFF000000 | color & 0xFFFFFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\itempicker\ItemPickerColorWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */