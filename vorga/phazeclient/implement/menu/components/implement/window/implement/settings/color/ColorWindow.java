/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_332;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.AlphaComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.ColorEditorComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.ColorPresetComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.HueComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component.SaturationComponent;
/*     */ 
/*     */ public class ColorWindow extends AbstractWindow {
/*     */   private final ColorSetting setting;
/*     */   
/*     */   public ColorSetting getSetting() {
/*  20 */     return this.setting;
/*  21 */   } private final List<AbstractComponent> components = new ArrayList<>(); private final HueComponent hueComponent; private final SaturationComponent saturationComponent; public List<AbstractComponent> getComponents() { return this.components; }
/*     */    private final AlphaComponent alphaComponent; private final ColorEditorComponent colorEditorComponent; private final ColorPresetComponent colorPresetComponent; public HueComponent getHueComponent() {
/*  23 */     return this.hueComponent; }
/*  24 */   public SaturationComponent getSaturationComponent() { return this.saturationComponent; }
/*  25 */   public AlphaComponent getAlphaComponent() { return this.alphaComponent; }
/*  26 */   public ColorEditorComponent getColorEditorComponent() { return this.colorEditorComponent; } public ColorPresetComponent getColorPresetComponent() {
/*  27 */     return this.colorPresetComponent;
/*     */   }
/*     */   public ColorWindow(ColorSetting setting) {
/*  30 */     this.setting = setting;
/*     */     
/*  32 */     this.components.addAll(
/*  33 */         Arrays.asList(new AbstractComponent[] { (AbstractComponent)(this.hueComponent = new HueComponent(setting)), (AbstractComponent)(this.saturationComponent = new SaturationComponent(setting)), (AbstractComponent)(this.alphaComponent = new AlphaComponent(setting)), (AbstractComponent)(this.colorEditorComponent = new ColorEditorComponent(setting)), (AbstractComponent)(this.colorPresetComponent = new ColorPresetComponent(setting)) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawWindow(class_332 context, int mouseX, int mouseY, float delta) {
/*  45 */     rectangle.render(ShapeProperties.create(context.method_51448(), this.x, this.y, this.width, this.height)
/*  46 */         .round(6.0F).thickness(2.0F).softness(1.0F).outlineColor(applyGlobalAlpha(ColorUtil.getOutline())).color(applyGlobalAlpha(ColorUtil.getMainGuiColor())).build());
/*     */     
/*  48 */     this.alphaComponent.position(this.x, this.y);
/*  49 */     this.hueComponent.position(this.x, this.y);
/*  50 */     this.saturationComponent.position(this.x, this.y);
/*  51 */     this.colorEditorComponent.position(this.x, this.y);
/*     */     
/*  53 */     this
/*  54 */       .height = ((ColorPresetComponent)this.colorPresetComponent.position(this.x, this.y)).getWindowHeight() - 20.0F;
/*     */     
/*  56 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.globalAlpha = this.globalAlpha;
/*     */           component.render(context, mouseX, mouseY, delta);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  67 */     draggable(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 17.0D));
/*  68 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.mouseClicked(mouseX, mouseY, button);
/*     */         });
/*  74 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/*  79 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.mouseScrolled(mouseX, mouseY, amount);
/*     */         });
/*  85 */     return super.mouseScrolled(mouseX, mouseY, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/*  90 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */         });
/*  96 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 101 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.mouseReleased(mouseX, mouseY, button);
/*     */         });
/* 107 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 112 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.charTyped(chr, modifiers);
/*     */         });
/* 118 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 123 */     this.components.forEach(component -> {
/*     */           if (this.setting.isNoAlpha() && component == this.alphaComponent) {
/*     */             return;
/*     */           }
/*     */           component.keyPressed(keyCode, scanCode, modifiers);
/*     */         });
/* 129 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\ColorWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */