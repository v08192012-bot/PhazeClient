/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.other.StringUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ public class BindComponent
/*     */   extends AbstractSettingComponent {
/*     */   public BindComponent(BindSetting setting) {
/*  20 */     super((Setting)setting);
/*  21 */     this.setting = setting;
/*     */   }
/*     */   private final BindSetting setting;
/*     */   private boolean binding;
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  27 */     updateVisibilityAnimation();
/*  28 */     MsdfGuiFontRenderer bindFont = GuiFonts.getSize(13, Fonts.Type.INTER_BOLD);
/*  29 */     MsdfGuiFontRenderer labelFont = GuiFonts.getSize(14, Fonts.Type.INTER_BOLD);
/*     */     
/*  31 */     boolean isModified = this.setting.isModified();
/*  32 */     float textOffset = animatedTextOffset(isModified);
/*     */     
/*  34 */     class_4587 matrix = context.method_51448();
/*  35 */     this.height = 23.5F;
/*     */     
/*  37 */     String bindName = StringUtil.getBindName(this.setting.getKey());
/*  38 */     String name = this.binding ? ("(" + bindName + ") ...") : bindName;
/*  39 */     float stringWidth = bindFont.getStringWidth(name) - 2.0F;
/*  40 */     float badgeWidth = stringWidth + 10.0F;
/*  41 */     float badgeX = this.x + this.width - stringWidth - 17.0F;
/*  42 */     float badgeY = this.y + this.height / 2.0F - 5.75F;
/*     */     
/*  44 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*     */     
/*  46 */     if (MathUtil.isHovered(mouseX, mouseY, badgeX, badgeY, badgeWidth, 11.5D)) {
/*  47 */       CursorManager.requestHand();
/*     */     }
/*     */     
/*  50 */     renderSettingCard(context, this.binding ? 1.0F : 0.0F, hoverProgress);
/*     */     
/*  52 */     rectangle.render(ShapeProperties.create(matrix, badgeX, badgeY, badgeWidth, 11.5D)
/*  53 */         .round(2.0F).thickness(1.1F)
/*  54 */         .outlineColor(MenuStyle.withAlpha(MenuStyle.settingOutline(this.binding), this.currentAlpha))
/*  55 */         .color(MenuStyle.withAlpha(MenuStyle.settingSurface(this.binding), this.currentAlpha))
/*  56 */         .build());
/*     */     
/*  58 */     bindFont.drawString(matrix, name, (this.x + this.width - 12.0F - stringWidth - 1.0F), centeredTextY((FontRenderer)bindFont, name, badgeY, 11.5F), mutedText());
/*     */     
/*  60 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(matrix);
/*     */     
/*  62 */     float textX = this.x + 10.0F + textOffset;
/*  63 */     String text = this.label.singleLine(this.setting.getLocalizedName());
/*  64 */     this.label.render(matrix, (FontRenderer)labelFont, text, textX, centeredTextY((FontRenderer)labelFont, text), badgeX - 8.0F, this.y, this.height, 
/*  65 */         primaryText());
/*  66 */     updateLabelHover(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  72 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/*  73 */       playButtonClickSound();
/*  74 */       this.setting.reset();
/*  75 */       return true;
/*     */     } 
/*     */     
/*  78 */     if (button == 0 && MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
/*  79 */       playButtonClickSound();
/*  80 */       this.binding = !this.binding;
/*  81 */       return true;
/*     */     } 
/*     */     
/*  84 */     if (this.binding && button > 1) {
/*  85 */       this.setting.setKey(button);
/*  86 */       this.binding = false;
/*  87 */       return true;
/*     */     } 
/*     */     
/*  90 */     if (this.binding && button == 0) {
/*  91 */       this.binding = false;
/*     */     }
/*     */     
/*  94 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/*  99 */     if (!this.setting.isVisible()) {
/* 100 */       return false;
/*     */     }
/* 102 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 107 */     if (this.binding) {
/* 108 */       int key = (keyCode == 261) ? -1 : keyCode;
/* 109 */       this.setting.setKey(key);
/* 110 */       this.binding = false;
/* 111 */       return true;
/*     */     } 
/*     */     
/* 114 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\BindComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */