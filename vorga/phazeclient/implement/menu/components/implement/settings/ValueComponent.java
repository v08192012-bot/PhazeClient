/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueComponent
/*     */   extends AbstractSettingComponent
/*     */ {
/*     */   private static final float LABEL_TEXT_SIZE = 7.0F;
/*     */   private static final float VALUE_TEXT_SIZE = 6.1F;
/*     */   private static final float SLIDER_MARGIN = 9.0F;
/*     */   private static final float TRACK_HEIGHT = 2.0F;
/*     */   private static final float THUMB_OUTER = 8.0F;
/*     */   private static final float THUMB_INNER = 6.0F;
/*     */   private static final double SMOOTH_SPEED = 4.0D;
/*     */   private static final int ANIMATION_MS = 200;
/*     */   private final ValueSetting setting;
/*     */   private boolean dragging;
/*     */   
/*     */   public boolean isSliderHovered() {
/*  39 */     return this.isSliderHovered;
/*     */   }
/*     */   private double animation; private float previousValue; private boolean wasVisible = true; private boolean isSliderHovered = false; private static final float RESET_AREA = 16.0F;
/*     */   private static final float SLIDER_FIXED_WIDTH = 70.0F;
/*     */   private float cachedSliderStartX;
/*     */   private float cachedSliderEndX;
/*     */   private float cachedSliderWidth;
/*     */   private float cachedSliderY;
/*  47 */   private final Animation textOffsetAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D);
/*  48 */   private final Animation resetIconAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D);
/*     */   
/*     */   public ValueComponent(ValueSetting setting) {
/*  51 */     super((Setting)setting);
/*  52 */     this.setting = setting;
/*  53 */     if (isGuiScaleSetting()) {
/*     */ 
/*     */       
/*  56 */       setting.range(0.9F, 2.5F).step(0.1F);
/*  57 */       if (setting.getValue() < 0.9F) {
/*  58 */         setting.setValue(0.9F);
/*     */       }
/*     */     } 
/*  61 */     this.previousValue = setting.getValue();
/*  62 */     this.animation = ((setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()) * 70.0F);
/*  63 */     boolean isModified = setting.isModified();
/*  64 */     this.textOffsetAnimation.setDirectionAndFinish(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*  65 */     this.resetIconAnimation.setDirectionAndFinish(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  70 */     super.tick();
/*     */   }
/*     */   
/*     */   private void computeSliderGeometry() {
/*  74 */     this.cachedSliderWidth = Math.min(70.0F, this.width * 0.35F);
/*  75 */     this.cachedSliderEndX = this.x + this.width - 9.0F - 16.0F;
/*  76 */     this.cachedSliderStartX = this.cachedSliderEndX - this.cachedSliderWidth;
/*  77 */     this.cachedSliderY = this.y + this.height / 2.0F;
/*     */   }
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*     */     String value;
/*  82 */     updateVisibilityAnimation();
/*     */     
/*  84 */     boolean isModified = this.setting.isModified();
/*  85 */     this.textOffsetAnimation.setDirection(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*  86 */     this.resetIconAnimation.setDirection(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/*  88 */     float textOffsetProgress = this.textOffsetAnimation.getOutputFloat();
/*  89 */     float animatedTextOffset = ResetIconComponent.getTextOffset() * textOffsetProgress;
/*  90 */     this.label.setAnimatedTextInset(animatedTextOffset);
/*  91 */     float resetIconAlpha = this.resetIconAnimation.getOutputFloat();
/*     */     
/*  93 */     class_4587 matrices = context.method_51448();
/*     */     
/*  95 */     this.height = 23.5F;
/*  96 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*     */     
/*  98 */     computeSliderGeometry();
/*     */     
/* 100 */     float centerY = this.y + this.height / 2.0F;
/*     */     
/* 102 */     this.isSliderHovered = MathUtil.isHovered(mouseX, mouseY, (this.cachedSliderStartX - 4.0F), (centerY - 6.0F), (this.cachedSliderWidth + 8.0F), 12.0D);
/* 103 */     if (this.dragging) {
/* 104 */       CursorManager.beginDrag(221189);
/* 105 */     } else if (this.isSliderHovered) {
/* 106 */       CursorManager.requestHand();
/*     */     } 
/*     */     
/* 109 */     renderSettingCard(context, 0.0F, hoverProgress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     float rawValue = this.setting.getValue();
/* 119 */     float stepSize = this.setting.getStep();
/*     */     
/* 121 */     if (stepSize >= 1.0F && Math.abs(rawValue - Math.round(rawValue)) < 1.0E-4F) {
/* 122 */       value = String.valueOf(Math.round(rawValue));
/*     */     } else {
/* 124 */       value = String.valueOf(rawValue);
/*     */     } 
/* 126 */     float valueWidth = MsdfFonts.bold().getWidth(value, 6.1F);
/* 127 */     float valueX = this.cachedSliderStartX - valueWidth - 6.0F;
/* 128 */     this.label.renderMsdf(matrices, this.setting.getLocalizedName(), 7.0F, this.x + 10.0F + animatedTextOffset, centerY - 3.5F + 1.1F, valueX - 8.0F, this.y, this.height, 
/*     */         
/* 130 */         primaryText());
/* 131 */     updateLabelHover(mouseX, mouseY);
/* 132 */     MsdfRenderer.renderText(
/* 133 */         MsdfFonts.bold(), value, 6.1F, 
/*     */ 
/*     */         
/* 136 */         MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.currentAlpha), matrices
/* 137 */         .method_23760().method_23761(), valueX, centerY - 3.05F + 0.5F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     changeValue(renderSlider(mouseX, matrices, this.cachedSliderStartX, centerY, this.cachedSliderWidth));
/*     */     
/* 145 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha * resetIconAlpha).modified(isModified).render(matrices);
/* 146 */     if (isModified && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 147 */       CursorManager.requestHand();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 153 */     if (!this.setting.isVisible()) {
/* 154 */       return false;
/*     */     }
/* 156 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 161 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 162 */       playButtonClickSound();
/* 163 */       this.setting.reset();
/* 164 */       return true;
/*     */     } 
/*     */     
/* 167 */     computeSliderGeometry();
/* 168 */     float centerY = this.y + this.height / 2.0F;
/* 169 */     boolean wasClicked = (MathUtil.isHovered(mouseX, mouseY, (this.cachedSliderStartX - 4.0F), (centerY - 6.0F), (this.cachedSliderWidth + 8.0F), 12.0D) && button == 0);
/* 170 */     if (wasClicked) {
/* 171 */       playButtonClickSound();
/* 172 */       this.dragging = true;
/* 173 */       CursorManager.beginDrag(221189);
/* 174 */       if (isGuiScaleSetting()) {
/* 175 */         Theme.getInstance().beginGuiScaleAdjustment();
/*     */       }
/* 177 */       return true;
/*     */     } 
/* 179 */     this.dragging = false;
/* 180 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 186 */     boolean wasDragging = this.dragging;
/* 187 */     if (wasDragging) {
/* 188 */       CursorManager.endDrag();
/*     */     }
/* 190 */     this.dragging = false;
/* 191 */     if (wasDragging && isGuiScaleSetting()) {
/* 192 */       Theme.getInstance().endGuiScaleAdjustment();
/*     */     }
/* 194 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */   
/*     */   private float renderSlider(int mouseX, class_4587 matrix, float sliderStartX, float sliderY, float sliderWidth) {
/* 198 */     float percentValue = sliderWidth * (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
/* 199 */     float difference = class_3532.method_15363(mouseX - sliderStartX, 0.0F, sliderWidth);
/*     */     
/* 201 */     if (!this.wasVisible && this.setting.isVisible()) {
/* 202 */       this.animation = percentValue;
/*     */     }
/* 204 */     this.wasVisible = this.setting.isVisible();
/*     */     
/* 206 */     this.animation = MathUtil.interpolateSmooth(2.5D, this.animation, this.dragging ? difference : percentValue);
/*     */     
/* 208 */     int bgColor = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CARD_OPTIONS, 0.35F), this.currentAlpha);
/* 209 */     int sliderColor = MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, this.currentAlpha);
/* 210 */     int thumbBgColor = MenuStyle.withAlpha(MenuStyle.PANEL_BG, this.currentAlpha);
/*     */     
/* 212 */     rectangle.render(ShapeProperties.create(matrix, sliderStartX, (sliderY - 1.0F), sliderWidth, 2.0D)
/* 213 */         .round(1.0F).color(bgColor).build());
/*     */     
/* 215 */     float fillWidth = class_3532.method_15363((float)this.animation, 0.0F, sliderWidth);
/* 216 */     if (fillWidth > 0.0F) {
/* 217 */       rectangle.render(ShapeProperties.create(matrix, sliderStartX, (sliderY - 1.0F), fillWidth, 2.0D)
/* 218 */           .round(1.0F).color(sliderColor).build());
/*     */     }
/*     */     
/* 221 */     float thumbX = class_3532.method_15363((float)(sliderStartX + this.animation), sliderStartX, sliderStartX + sliderWidth);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     float thumbBorder = 1.0F;
/* 228 */     rectangle.render(ShapeProperties.create(matrix, (thumbX - 4.0F), (sliderY - 4.0F), 8.0D, 8.0D)
/* 229 */         .round(4.0F)
/* 230 */         .thickness(thumbBorder)
/* 231 */         .outlineColor(thumbBgColor)
/* 232 */         .color(sliderColor)
/* 233 */         .build());
/*     */     
/* 235 */     return difference;
/*     */   }
/*     */ 
/*     */   
/*     */   private void changeValue(float difference) {
/* 240 */     if (!this.dragging) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     float rangeMin = this.setting.getMin();
/* 251 */     float rangeMax = this.setting.getMax();
/*     */ 
/*     */     
/* 254 */     float rawValue = (difference == 0.0F) ? rangeMin : (difference / this.cachedSliderWidth * (rangeMax - rangeMin) + rangeMin);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     float snapped2 = this.setting.applySnap(rawValue);
/* 262 */     boolean magnetHolding = (snapped2 != rawValue);
/* 263 */     if (magnetHolding) {
/* 264 */       rawValue = snapped2;
/* 265 */       this.setting.setValue(this.setting.isInteger() ? (int)rawValue : rawValue);
/* 266 */       this.previousValue = rawValue;
/*     */       
/*     */       return;
/*     */     } 
/* 270 */     float step = this.setting.getStep();
/* 271 */     if (step <= 0.0F) step = 0.01F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     float steps = Math.round((rawValue - rangeMin) / step + 1.0E-6F);
/* 279 */     float snapped = rangeMin + steps * step;
/* 280 */     if (snapped < rangeMin) snapped = rangeMin; 
/* 281 */     if (snapped > rangeMax) snapped = rangeMax;
/*     */ 
/*     */ 
/*     */     
/* 285 */     BigDecimal bd = BigDecimal.valueOf(snapped).setScale(2, RoundingMode.HALF_UP);
/* 286 */     float newValue = bd.floatValue();
/* 287 */     if (this.setting.isInteger()) newValue = (int)newValue;
/*     */     
/* 289 */     if (newValue != this.previousValue) {
/* 290 */       this.previousValue = newValue;
/*     */     }
/*     */     
/* 293 */     this.setting.setValue(newValue);
/*     */   }
/*     */   
/*     */   private boolean isGuiScaleSetting() {
/* 297 */     return (this.setting == (Theme.getInstance()).guiScale);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\ValueComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */