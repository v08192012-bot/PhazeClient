/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.group;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.feature.module.setting.SettingComponentAdder;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.api.system.shape.implement.Blur;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WorldColorGroupWindow
/*     */   extends AbstractWindow
/*     */ {
/*     */   private static final float TITLE_SIZE = 7.0F;
/*     */   private static final float HEADER_HEIGHT = 24.0F;
/*     */   private static final float CONTENT_TOP = 26.0F;
/*     */   private static final float CONTENT_BOTTOM = 4.0F;
/*     */   private static final float MIN_WINDOW_HEIGHT = 48.0F;
/*     */   private final GroupSetting setting;
/*  37 */   private final List<AbstractSettingComponent> components = new ArrayList<>(); public List<AbstractSettingComponent> getComponents() { return this.components; } public GroupSetting getSetting() {
/*  38 */     return this.setting;
/*  39 */   } private float cachedComponentHeight = 0.0F; public float getCachedComponentHeight() { return this.cachedComponentHeight; }
/*     */   
/*     */   public WorldColorGroupWindow(GroupSetting setting) {
/*  42 */     this.setting = setting;
/*  43 */     (new SettingComponentAdder()).addSettingComponent(setting.getSubSettings(), this.components);
/*  44 */     getScaleAnimation().setMs(200);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawWindow(class_332 context, int mouseX, int mouseY, float delta) {
/*  49 */     class_4587 matrices = context.method_51448();
/*  50 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*  51 */     ScissorManager scissorManager = Main.getInstance().getScissorManager();
/*     */     
/*  53 */     renderWindowBlur(matrices);
/*     */     
/*  55 */     this.cachedComponentHeight = calculateComponentHeight();
/*  56 */     float desiredHeight = 26.0F + this.cachedComponentHeight + 4.0F;
/*  57 */     float viewportHeight = Math.max(0.0F, this.height - 26.0F - 4.0F);
/*  58 */     boolean clip = (desiredHeight > this.height);
/*     */     
/*  60 */     int outline = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.BORDER_LIGHT, -1, 0.06F), this.globalAlpha * 0.96F);
/*  61 */     int panelFill = MenuStyle.withAlpha(MenuStyle.PANEL_BG, this.globalAlpha * 0.94F);
/*  62 */     int panelInner = MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_BG_SOFT, MenuStyle.PANEL_CONTENT, 0.42F), this.globalAlpha * 0.98F);
/*     */     
/*  64 */     rectangle.render(ShapeProperties.create(matrices, this.x, this.y, this.width, this.height)
/*  65 */         .round(9.0F)
/*  66 */         .softness(1.1F)
/*  67 */         .thickness(1.15F)
/*  68 */         .outlineColor(outline)
/*  69 */         .color(panelFill)
/*  70 */         .build());
/*     */     
/*  72 */     rectangle.render(ShapeProperties.create(matrices, (this.x + 1.0F), (this.y + 1.0F), (this.width - 2.0F), (this.height - 2.0F))
/*  73 */         .round(8.0F)
/*  74 */         .thickness(0.0F)
/*  75 */         .color(panelInner)
/*  76 */         .build());
/*     */     
/*  78 */     String title = this.setting.getLocalizedName();
/*  79 */     MsdfRenderer.renderText(
/*  80 */         MsdfFonts.bold(), title, 7.0F, 
/*     */ 
/*     */         
/*  83 */         MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.globalAlpha), positionMatrix, 
/*     */         
/*  85 */         MenuStyle.centerMsdfTextX(MsdfFonts.bold(), title, 7.0F, this.x, this.width), 
/*  86 */         MenuStyle.centerMsdfTextY(7.0F, this.y, 24.0F), 0.0F);
/*     */ 
/*     */ 
/*     */     
/*  90 */     float contentY = (float)((this.y + 26.0F) + this.smoothedScroll);
/*  91 */     if (clip) {
/*  92 */       scissorManager.push(matrices.method_23760().method_23761(), this.x + 4.0F, this.y + 26.0F - 2.0F, this.width - 8.0F, viewportHeight + 2.0F);
/*     */     }
/*     */     
/*  95 */     float offset = 0.0F;
/*  96 */     float totalHeight = 0.0F;
/*  97 */     for (int i = this.components.size() - 1; i >= 0; i--) {
/*  98 */       AbstractSettingComponent component = this.components.get(i);
/*  99 */       Supplier<Boolean> visible = component.getSetting().getVisible();
/* 100 */       if (visible == null || ((Boolean)visible.get()).booleanValue()) {
/*     */ 
/*     */ 
/*     */         
/* 104 */         component.x = this.x + 6.0F;
/* 105 */         component.y = contentY + offset;
/* 106 */         component.width = this.width - 12.0F;
/* 107 */         component.globalAlpha = this.globalAlpha;
/* 108 */         component.setExternalAlpha(this.globalAlpha);
/* 109 */         component.render(context, mouseX, mouseY, delta);
/* 110 */         component.setExternalAlpha(1.0F);
/*     */         
/* 112 */         offset += component.height;
/* 113 */         totalHeight += component.height;
/*     */       } 
/*     */     } 
/* 116 */     if (clip) {
/* 117 */       scissorManager.pop();
/*     */     }
/*     */     
/* 120 */     float maxScroll = Math.max(0.0F, totalHeight - viewportHeight);
/* 121 */     this.scroll = class_3532.method_15350(this.scroll, -maxScroll, 0.0D);
/* 122 */     this.smoothedScroll = class_3532.method_16436(0.10000000149011612D, this.smoothedScroll, this.scroll);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 127 */     draggable(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 24.0D));
/* 128 */     if (!isHovered(mouseX, mouseY)) {
/* 129 */       return false;
/*     */     }
/*     */     
/* 132 */     if (super.mouseClicked(mouseX, mouseY, button)) {
/* 133 */       return true;
/*     */     }
/*     */     
/* 136 */     for (AbstractSettingComponent component : this.components) {
/* 137 */       if (component.isHover(mouseX, mouseY) && component.mouseClicked(mouseX, mouseY, button)) {
/* 138 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 147 */     for (AbstractComponent component : this.components) {
/* 148 */       if (component.isHover(mouseX, mouseY)) {
/* 149 */         return true;
/*     */       }
/*     */     } 
/* 152 */     return super.isHover(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(double mouseX, double mouseY) {
/* 157 */     for (AbstractComponent component : this.components) {
/* 158 */       if (component.isHover(mouseX, mouseY)) {
/* 159 */         return true;
/*     */       }
/*     */     } 
/* 162 */     return super.isHovered(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 167 */     this.components.forEach(component -> component.mouseReleased(mouseX, mouseY, button));
/* 168 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 173 */     boolean scrolled = (isHovered(mouseX, mouseY) && this.cachedComponentHeight + 26.0F + 4.0F > this.height);
/* 174 */     if (scrolled) {
/* 175 */       this.scroll += amount * 20.0D;
/*     */     }
/* 177 */     this.components.forEach(component -> component.mouseScrolled(mouseX, mouseY, amount));
/* 178 */     return scrolled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 183 */     this.components.forEach(component -> component.keyPressed(keyCode, scanCode, modifiers));
/* 184 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 189 */     this.components.forEach(component -> component.charTyped(chr, modifiers));
/* 190 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */   
/*     */   private float calculateComponentHeight() {
/* 194 */     float total = 0.0F;
/* 195 */     for (AbstractSettingComponent component : this.components) {
/* 196 */       Supplier<Boolean> visible = component.getSetting().getVisible();
/* 197 */       if (visible != null && !((Boolean)visible.get()).booleanValue()) {
/*     */         continue;
/*     */       }
/* 200 */       total += component.height;
/*     */     } 
/* 202 */     return total;
/*     */   }
/*     */   
/*     */   private void renderWindowBlur(class_4587 matrices) {
/* 206 */     float blurRadius = Theme.getInstance().getMenuBlurRadius();
/* 207 */     if (blurRadius <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     Blur.INSTANCE.renderGaussianOverlay(ShapeProperties.create(matrices, this.x, this.y, this.width, this.height)
/* 212 */         .round(9.0F)
/* 213 */         .softness(1.1F)
/* 214 */         .quality(blurRadius * 2.0F)
/* 215 */         .color(MenuStyle.withAlpha(-1, this.globalAlpha))
/* 216 */         .build());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\group\WorldColorGroupWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */