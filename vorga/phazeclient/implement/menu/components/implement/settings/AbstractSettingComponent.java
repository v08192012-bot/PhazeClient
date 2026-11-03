/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ public abstract class AbstractSettingComponent extends AbstractComponent {
/*     */   public static final float ROW_HEIGHT = 23.5F;
/*     */   private final Setting setting;
/*     */   private final Animation visibilityAnimation;
/*     */   private final Animation textOffsetAnimation;
/*     */   private final Animation cardHoverAnimation;
/*     */   protected final ResetIconComponent resetIcon;
/*     */   protected final SettingLabel label;
/*     */   private boolean textOffsetInitialized;
/*     */   public float currentAlpha;
/*     */   private float externalAlpha;
/*     */   
/*  19 */   public AbstractSettingComponent(Setting setting) { this.height = 23.5F;
/*     */ 
/*     */ 
/*     */     
/*  23 */     this.visibilityAnimation = (new DecelerateAnimation()).setMs(1).setValue(1.0D);
/*  24 */     this.textOffsetAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D);
/*  25 */     this.cardHoverAnimation = (new DecelerateAnimation()).setMs(170).setValue(1.0D);
/*  26 */     this.resetIcon = new ResetIconComponent();
/*  27 */     this.label = new SettingLabel();
/*  28 */     this.textOffsetInitialized = false;
/*     */     
/*  30 */     this.currentAlpha = 1.0F;
/*  31 */     this.externalAlpha = 1.0F; this.setting = setting; } public Setting getSetting() { return this.setting; } public Animation getVisibilityAnimation() { return this.visibilityAnimation; } public Animation getTextOffsetAnimation() { return this.textOffsetAnimation; } public Animation getCardHoverAnimation() { return this.cardHoverAnimation; } public float getExternalAlpha() { return this.externalAlpha; }
/*     */   public ResetIconComponent getResetIcon() { return this.resetIcon; }
/*     */   public SettingLabel getLabel() { return this.label; }
/*  34 */   public boolean isTextOffsetInitialized() { return this.textOffsetInitialized; } public float getCurrentAlpha() { return this.currentAlpha; } public void updateVisibilityAnimation() { boolean wasVisible = this.visibilityAnimation.isDirection(Direction.FORWARDS);
/*  35 */     boolean isVisible = this.setting.isVisible();
/*     */     
/*  37 */     if (!wasVisible && isVisible) {
/*  38 */       this.visibilityAnimation.setValue(1.0D);
/*  39 */       this.visibilityAnimation.setDirection(Direction.FORWARDS);
/*     */     } else {
/*  41 */       this.visibilityAnimation.setDirection(isVisible ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     } 
/*  43 */     this.currentAlpha = this.visibilityAnimation.getOutputFloat() * this.externalAlpha; }
/*     */ 
/*     */   
/*     */   public void setExternalAlpha(float externalAlpha) {
/*  47 */     this.externalAlpha = Math.max(0.0F, Math.min(1.0F, externalAlpha));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVisibilityProgress() {
/*  55 */     return this.visibilityAnimation.getOutputDouble();
/*     */   }
/*     */   
/*     */   public boolean shouldSkipRender() {
/*  59 */     return (getVisibilityProgress() <= 0.01D);
/*     */   }
/*     */   
/*     */   public float getExpandedHeight() {
/*  63 */     return this.height;
/*     */   }
/*     */   
/*     */   protected void renderSettingCard(class_332 context, boolean active) {
/*  67 */     renderSettingCard(context, active ? 1.0F : 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   protected void renderSettingCard(class_332 context, float activeProgress, float hoverProgress) {
/*  71 */     float clampedActive = Math.max(0.0F, Math.min(1.0F, activeProgress));
/*  72 */     float clampedHover = Math.max(0.0F, Math.min(1.0F, hoverProgress));
/*     */     
/*  74 */     int baseBackground = MenuStyle.mix(
/*  75 */         MenuStyle.settingSurface(false), 
/*  76 */         MenuStyle.settingSurface(true), clampedActive);
/*     */ 
/*     */     
/*  79 */     int baseOutline = MenuStyle.mix(
/*  80 */         MenuStyle.settingOutline(false), 
/*  81 */         MenuStyle.settingOutline(true), clampedActive);
/*     */ 
/*     */ 
/*     */     
/*  85 */     int hoveredBackground = MenuStyle.mix(baseBackground, -1, clampedHover * 0.08F);
/*  86 */     int hoveredOutline = MenuStyle.mix(baseOutline, -1, clampedHover * 0.12F);
/*     */     
/*  88 */     int background = MenuStyle.withAlpha(hoveredBackground, this.currentAlpha * 0.78F);
/*  89 */     int outline = MenuStyle.withAlpha(hoveredOutline, this.currentAlpha * 0.74F);
/*     */     
/*  91 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.x, this.y, this.width, this.height)
/*  92 */         .round(4.0F)
/*  93 */         .softness(1.12F)
/*  94 */         .thickness(1.2F)
/*  95 */         .outlineColor(outline)
/*  96 */         .color(background)
/*  97 */         .build());
/*     */   }
/*     */   
/*     */   protected int primaryText() {
/* 101 */     return MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.currentAlpha);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateLabelHover(int mouseX, int mouseY) {
/* 106 */     this.label.updateHover((this.setting.isVisible() && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int mutedText() {
/* 111 */     return MenuStyle.withAlpha(MenuStyle.TEXT_MUTED, this.currentAlpha);
/*     */   }
/*     */   
/*     */   protected float animatedTextOffset(boolean modified) {
/* 115 */     if (!this.textOffsetInitialized) {
/* 116 */       this.textOffsetAnimation.setDirectionAndFinish(modified ? Direction.FORWARDS : Direction.BACKWARDS);
/* 117 */       this.textOffsetInitialized = true;
/*     */     } 
/* 119 */     this.textOffsetAnimation.setDirection(modified ? Direction.FORWARDS : Direction.BACKWARDS);
/* 120 */     float offset = ResetIconComponent.getTextOffset() * this.textOffsetAnimation.getOutputFloat();
/* 121 */     this.label.setAnimatedTextInset(offset);
/* 122 */     return offset;
/*     */   }
/*     */   
/*     */   protected float animatedCardHover(boolean hovered) {
/* 126 */     this.cardHoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 127 */     return this.cardHoverAnimation.getOutputFloat();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\AbstractSettingComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */