/*     */ package vorga.phazeclient.implement.menu.components.implement.settings.select;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.ResetIconComponent;
/*     */ 
/*     */ public class SelectComponent
/*     */   extends AbstractSettingComponent
/*     */ {
/*     */   private static final float LABEL_TEXT_SIZE = 7.0F;
/*     */   private static final float VALUE_TEXT_SIZE = 6.1F;
/*     */   private static final float CARD_MIN_HEIGHT = 23.5F;
/*     */   private static final float SELECT_BOX_HEIGHT = 16.0F;
/*     */   private static final float SELECT_BOX_MIN_WIDTH = 92.0F;
/*     */   private static final float SELECT_BOX_MAX_WIDTH = 118.0F;
/*     */   private static final float SELECT_BOX_RIGHT_MARGIN = 8.0F;
/*     */   private static final float ARROW_HIT_WIDTH = 14.0F;
/*     */   private static final float ARROW_TEXT_SIZE = 7.0F;
/*     */   private static final float SLIDE_OFFSET = 6.0F;
/*     */   private static final int TRANSITION_MS = 150;
/*     */   private static final int HOVER_MS = 120;
/*     */   private static final int ICON_ANIMATION_MS = 200;
/*     */   private final SelectSetting setting;
/*  39 */   private final Animation transitionAnimation = (new DecelerateAnimation())
/*  40 */     .setMs(150).setValue(1.0D);
/*  41 */   private final Animation leftHoverAnimation = (new DecelerateAnimation())
/*  42 */     .setMs(120).setValue(1.0D);
/*  43 */   private final Animation rightHoverAnimation = (new DecelerateAnimation())
/*  44 */     .setMs(120).setValue(1.0D);
/*  45 */   private final Animation textOffsetAnimation = (new DecelerateAnimation())
/*  46 */     .setMs(200).setValue(1.0D);
/*  47 */   private final Animation resetIconAnimation = (new DecelerateAnimation())
/*  48 */     .setMs(200).setValue(1.0D);
/*  49 */   private String previousValue = null;
/*  50 */   private int cycleDirection = 1;
/*     */   
/*     */   public SelectComponent(SelectSetting setting) {
/*  53 */     super((Setting)setting);
/*  54 */     this.setting = setting;
/*  55 */     this.transitionAnimation.setDirection(Direction.FORWARDS);
/*  56 */     boolean isModified = setting.isModified();
/*  57 */     this.textOffsetAnimation.setDirectionAndFinish(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*  58 */     this.resetIconAnimation.setDirectionAndFinish(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeAllDropdowns() {}
/*     */ 
/*     */   
/*     */   public static void handleGlobalClick(double mouseX, double mouseY) {}
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  69 */     updateVisibilityAnimation();
/*     */     
/*  71 */     boolean isModified = this.setting.isModified();
/*  72 */     this.textOffsetAnimation.setDirection(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*  73 */     this.resetIconAnimation.setDirection(isModified ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/*  75 */     float textOffsetProgress = this.textOffsetAnimation.getOutputFloat();
/*  76 */     float animatedTextOffset = ResetIconComponent.getTextOffset() * textOffsetProgress;
/*  77 */     this.label.setAnimatedTextInset(animatedTextOffset);
/*  78 */     float resetIconAlpha = this.resetIconAnimation.getOutputFloat();
/*     */     
/*  80 */     class_4587 matrices = context.method_51448();
/*     */     
/*  82 */     float selectedBoxX = selectedBoxX();
/*  83 */     this.height = 23.5F;
/*  84 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*     */     
/*  86 */     renderSettingCard(context, 0.0F, hoverProgress);
/*  87 */     renderArrowSelector(matrices, mouseX, mouseY);
/*     */     
/*  89 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha * resetIconAlpha).modified(isModified).render(matrices);
/*     */     
/*  91 */     float textX = this.x + 10.0F + animatedTextOffset;
/*  92 */     this.label.renderMsdf(matrices, this.setting.getLocalizedName(), 7.0F, textX, this.y + (this.height - 7.0F) / 2.0F + 1.1F, selectedBoxX - 8.0F, this.y, this.height, 
/*     */         
/*  94 */         primaryText());
/*  95 */     updateLabelHover(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 100 */     if (button == 0) {
/* 101 */       if (this.resetIcon.isHovered(mouseX, mouseY)) {
/* 102 */         playButtonClickSound();
/* 103 */         this.setting.reset();
/* 104 */         return true;
/*     */       } 
/*     */       
/* 107 */       float boxX = selectedBoxX();
/* 108 */       float boxY = selectedBoxY();
/* 109 */       float boxWidth = selectedBoxWidth();
/*     */       
/* 111 */       if (MathUtil.isHovered(mouseX, mouseY, boxX, boxY, 14.0D, 16.0D)) {
/* 112 */         playButtonClickSound();
/* 113 */         cyclePrevious();
/* 114 */         return true;
/*     */       } 
/*     */       
/* 117 */       if (MathUtil.isHovered(mouseX, mouseY, (boxX + boxWidth - 14.0F), boxY, 14.0D, 16.0D)) {
/* 118 */         playButtonClickSound();
/* 119 */         cycleNext();
/* 120 */         return true;
/*     */       } 
/*     */     } 
/* 123 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 128 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */   
/*     */   private void renderArrowSelector(class_4587 matrices, int mouseX, int mouseY) {
/* 132 */     float boxX = selectedBoxX();
/* 133 */     float boxY = selectedBoxY();
/* 134 */     float boxWidth = selectedBoxWidth();
/* 135 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */     
/* 137 */     rectangle.render(ShapeProperties.create(matrices, boxX, boxY, boxWidth, 16.0D)
/* 138 */         .round(2.0F).softness(1.1F).thickness(1.1F)
/* 139 */         .outlineColor(MenuStyle.withAlpha(MenuStyle.BORDER, this.currentAlpha))
/* 140 */         .color(MenuStyle.withAlpha(MenuStyle.PANEL_CHIP, this.currentAlpha))
/* 141 */         .build());
/*     */     
/* 143 */     boolean leftHovered = MathUtil.isHovered(mouseX, mouseY, boxX, boxY, 14.0D, 16.0D);
/* 144 */     if (leftHovered) {
/* 145 */       CursorManager.requestHand();
/*     */     }
/* 147 */     this.leftHoverAnimation.setDirection(leftHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 148 */     float leftProgress = this.leftHoverAnimation.getOutputFloat();
/* 149 */     float leftAlpha = (0.7F + 0.3F * leftProgress) * this.currentAlpha;
/* 150 */     int leftColor = MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, leftAlpha);
/* 151 */     MsdfRenderer.renderText(
/* 152 */         MsdfFonts.bold(), "<", 7.0F, leftColor, positionMatrix, boxX + 4.0F, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 158 */         MenuStyle.centerMsdfTextY(7.0F, boxY, 16.0F), 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 162 */     boolean rightHovered = MathUtil.isHovered(mouseX, mouseY, (boxX + boxWidth - 14.0F), boxY, 14.0D, 16.0D);
/* 163 */     if (rightHovered) {
/* 164 */       CursorManager.requestHand();
/*     */     }
/* 166 */     this.rightHoverAnimation.setDirection(rightHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 167 */     float rightProgress = this.rightHoverAnimation.getOutputFloat();
/* 168 */     float rightAlpha = (0.7F + 0.3F * rightProgress) * this.currentAlpha;
/* 169 */     int rightColor = MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, rightAlpha);
/* 170 */     float rightArrowWidth = MsdfFonts.bold().getWidth(">", 7.0F);
/* 171 */     MsdfRenderer.renderText(
/* 172 */         MsdfFonts.bold(), ">", 7.0F, rightColor, positionMatrix, boxX + boxWidth - 4.0F - rightArrowWidth, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 178 */         MenuStyle.centerMsdfTextY(7.0F, boxY, 16.0F), 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 182 */     float progress = this.transitionAnimation.getOutputFloat();
/* 183 */     float textAreaX = boxX + 14.0F;
/* 184 */     float textAreaWidth = boxWidth - 28.0F;
/* 185 */     float textY = MenuStyle.centerMsdfTextY(6.1F, boxY, 16.0F);
/*     */     
/* 187 */     String selectedName = this.setting.getSelected();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     String selectedDisplay = Lang.translate(selectedName);
/* 196 */     String previousDisplay = (this.previousValue == null) ? null : Lang.translate(this.previousValue);
/*     */     
/* 198 */     if (this.previousValue != null && progress < 1.0F) {
/* 199 */       float oldAlpha = (1.0F - progress) * this.currentAlpha;
/* 200 */       float oldSlide = progress * 6.0F * this.cycleDirection;
/* 201 */       float oldWidth = MsdfFonts.bold().getWidth(previousDisplay, 6.1F);
/* 202 */       float oldCenterX = textAreaX + (textAreaWidth - oldWidth) / 2.0F + oldSlide;
/* 203 */       MsdfRenderer.renderText(
/* 204 */           MsdfFonts.bold(), previousDisplay, 6.1F, 
/*     */ 
/*     */           
/* 207 */           MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, oldAlpha), positionMatrix, oldCenterX, textY, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       float newAlpha = progress * this.currentAlpha;
/* 215 */       float newSlide = (1.0F - progress) * 6.0F * -this.cycleDirection;
/* 216 */       float newWidth = MsdfFonts.bold().getWidth(selectedDisplay, 6.1F);
/* 217 */       float newCenterX = textAreaX + (textAreaWidth - newWidth) / 2.0F + newSlide;
/* 218 */       MsdfRenderer.renderText(
/* 219 */           MsdfFonts.bold(), selectedDisplay, 6.1F, 
/*     */ 
/*     */           
/* 222 */           MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, newAlpha), positionMatrix, newCenterX, textY, 0.0F);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 229 */       this.previousValue = null;
/* 230 */       float valueWidth = MsdfFonts.bold().getWidth(selectedDisplay, 6.1F);
/* 231 */       float centerX = textAreaX + (textAreaWidth - valueWidth) / 2.0F;
/* 232 */       MsdfRenderer.renderText(
/* 233 */           MsdfFonts.bold(), selectedDisplay, 6.1F, 
/*     */ 
/*     */           
/* 236 */           MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.currentAlpha), positionMatrix, centerX, textY, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startTransition(int direction) {
/* 246 */     this.previousValue = this.setting.getSelected();
/* 247 */     this.cycleDirection = direction;
/* 248 */     this.transitionAnimation.reset();
/* 249 */     this.transitionAnimation.setDirection(Direction.BACKWARDS);
/* 250 */     this.transitionAnimation.setDirection(Direction.FORWARDS);
/*     */   }
/*     */   
/*     */   private void cyclePrevious() {
/* 254 */     startTransition(-1);
/* 255 */     List<String> list = this.setting.getList();
/* 256 */     int currentIndex = list.indexOf(this.setting.getSelected());
/* 257 */     int newIndex = (currentIndex - 1 + list.size()) % list.size();
/* 258 */     this.setting.setSelected(list.get(newIndex));
/*     */   }
/*     */   
/*     */   private void cycleNext() {
/* 262 */     startTransition(1);
/* 263 */     List<String> list = this.setting.getList();
/* 264 */     int currentIndex = list.indexOf(this.setting.getSelected());
/* 265 */     int newIndex = (currentIndex + 1) % list.size();
/* 266 */     this.setting.setSelected(list.get(newIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTooltips(class_332 context) {}
/*     */   
/*     */   public float getExpandedHeight() {
/* 273 */     return this.height;
/*     */   }
/*     */   
/*     */   private float selectedBoxWidth() {
/* 277 */     return Math.min(118.0F, Math.max(92.0F, this.width * 0.42F));
/*     */   }
/*     */   
/*     */   private float selectedBoxX() {
/* 281 */     return this.x + this.width - selectedBoxWidth() - 8.0F;
/*     */   }
/*     */   
/*     */   private float selectedBoxY() {
/* 285 */     return this.y + this.height / 2.0F - 8.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\select\SelectComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */