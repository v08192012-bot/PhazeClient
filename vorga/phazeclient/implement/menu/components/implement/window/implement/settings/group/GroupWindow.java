/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.group;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.SettingComponentAdder;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ 
/*     */ 
/*     */ public class GroupWindow
/*     */   extends AbstractWindow
/*     */ {
/*     */   private final GroupSetting setting;
/*  28 */   private final List<AbstractSettingComponent> components = new ArrayList<>(); public List<AbstractSettingComponent> getComponents() { return this.components; } public GroupSetting getSetting() {
/*  29 */     return this.setting;
/*  30 */   } private float cachedComponentHeight = 0.0F; public float getCachedComponentHeight() { return this.cachedComponentHeight; }
/*     */   
/*     */   public GroupWindow(GroupSetting setting) {
/*  33 */     this.setting = setting;
/*     */     
/*  35 */     (new SettingComponentAdder()).addSettingComponent(setting
/*  36 */         .getSubSettings(), this.components);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawWindow(class_332 context, int mouseX, int mouseY, float delta) {
/*  44 */     class_4587 matrix = context.method_51448();
/*     */ 
/*     */     
/*  47 */     ScissorManager scissorManager = Main.getInstance().getScissorManager();
/*     */     
/*  49 */     this.cachedComponentHeight = calculateComponentHeight();
/*  50 */     this.height = class_3532.method_15363(this.cachedComponentHeight, 0.0F, 200.0F);
/*     */     
/*  52 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/*  53 */         .round(4.0F).thickness(2.0F).softness(1.0F).outlineColor(applyGlobalAlpha(ColorUtil.getOutline())).color(applyGlobalAlpha(ColorUtil.getGuiRectColor(1.0F))).build());
/*     */     
/*  55 */     GuiFonts.getSize(15, Fonts.Type.INTER_BOLD).drawString(context.method_51448(), this.setting.getLocalizedName(), (this.x + 9.0F), (this.y + 10.0F), applyGlobalAlpha(ColorUtil.getText()));
/*     */     
/*  57 */     boolean isLimitedHeight = (class_3532.method_15363(this.height, 0.0F, 200.0F) == 200.0F);
/*  58 */     if (isLimitedHeight) scissorManager.push(matrix.method_23760().method_23761(), this.x, this.y + 23.0F, this.width, this.height - 28.0F);
/*     */     
/*  60 */     float offset = 0.0F;
/*  61 */     float totalHeight = 0.0F;
/*  62 */     for (int i = this.components.size() - 1; i >= 0; i--) {
/*  63 */       AbstractSettingComponent component = this.components.get(i);
/*  64 */       Supplier<Boolean> visible = component.getSetting().getVisible();
/*     */       
/*  66 */       if (visible == null || ((Boolean)visible.get()).booleanValue()) {
/*     */ 
/*     */ 
/*     */         
/*  70 */         component.x = this.x;
/*  71 */         component.y = (float)((this.y + 19.0F + offset + this.height - 25.0F - component.height) + this.smoothedScroll);
/*  72 */         component.width = this.width;
/*  73 */         component.globalAlpha = this.globalAlpha;
/*     */         
/*  75 */         component.render(context, mouseX, mouseY, delta);
/*     */         
/*  77 */         offset -= component.height;
/*  78 */         totalHeight += component.height;
/*     */       } 
/*     */     } 
/*  81 */     if (isLimitedHeight) scissorManager.pop();
/*     */     
/*  83 */     float maxScroll = Math.max(0.0F, totalHeight - this.height - 23.0F);
/*  84 */     this.scroll = class_3532.method_15350(this.scroll, -maxScroll, 0.0D);
/*  85 */     this.smoothedScroll = class_3532.method_16436(0.10000000149011612D, this.smoothedScroll, this.scroll);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  91 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 19.0D) && button == 1) {
/*  92 */       closeChildGroupWindows(this.setting);
/*  93 */       windowManager.delete(this);
/*  94 */       return true;
/*     */     } 
/*     */     
/*  97 */     draggable((MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, 19.0D) && button == 0));
/*     */ 
/*     */ 
/*     */     
/* 101 */     boolean isAnyComponentHovered = this.components.stream().anyMatch(abstractComponent -> abstractComponent.isHover(mouseX, mouseY));
/*     */     
/* 103 */     if (isAnyComponentHovered) {
/* 104 */       closeChildGroupWindows(this.setting);
/*     */       
/* 106 */       for (AbstractSettingComponent component : this.components) {
/* 107 */         if (component.isHover(mouseX, mouseY) && 
/* 108 */           component.mouseClicked(mouseX, mouseY, button)) {
/* 109 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 113 */       return super.mouseClicked(mouseX, mouseY, button);
/*     */     } 
/*     */     
/* 116 */     this.components.forEach(abstractComponent -> abstractComponent.mouseClicked(mouseX, mouseY, button));
/* 117 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */   
/*     */   private void closeChildGroupWindows(GroupSetting parentSetting) {
/* 121 */     List<AbstractWindow> windowsCopy = new ArrayList<>(windowManager.getWindows());
/*     */     
/* 123 */     for (Setting childSetting : parentSetting.getSubSettings()) {
/* 124 */       if (childSetting instanceof GroupSetting) { GroupSetting groupSetting = (GroupSetting)childSetting;
/* 125 */         closeChildGroupWindows(groupSetting);
/*     */         
/* 127 */         for (AbstractWindow window : windowsCopy) {
/* 128 */           if (window instanceof GroupWindow) { GroupWindow groupWindow = (GroupWindow)window;
/* 129 */             if (groupWindow.getSetting() == groupSetting) {
/* 130 */               windowManager.delete(window);
/*     */             } }
/*     */         
/*     */         }  }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 142 */     this.components.forEach(abstractComponent -> abstractComponent.isHover(mouseX, mouseY));
/*     */     
/* 144 */     for (AbstractComponent abstractComponent : this.components) {
/* 145 */       if (abstractComponent.isHover(mouseX, mouseY)) {
/* 146 */         return true;
/*     */       }
/*     */     } 
/* 149 */     return super.isHover(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(double mouseX, double mouseY) {
/* 154 */     for (AbstractComponent abstractComponent : this.components) {
/* 155 */       if (abstractComponent.isHover(mouseX, mouseY)) {
/* 156 */         return true;
/*     */       }
/*     */     } 
/* 159 */     return super.isHovered(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 165 */     this.components.forEach(abstractComponent -> abstractComponent.mouseReleased(mouseX, mouseY, button));
/* 166 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 172 */     boolean scrolled = (class_3532.method_15363(this.height, 0.0F, 200.0F) == 200.0F && MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/* 173 */     if (scrolled) {
/* 174 */       this.scroll += amount * 20.0D;
/*     */     }
/* 176 */     this.components.forEach(abstractComponent -> abstractComponent.mouseScrolled(mouseX, mouseY, amount));
/* 177 */     return scrolled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 182 */     this.components.forEach(abstractComponent -> abstractComponent.keyPressed(keyCode, scanCode, modifiers));
/* 183 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 188 */     this.components.forEach(abstractComponent -> abstractComponent.charTyped(chr, modifiers));
/* 189 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */   
/*     */   private float calculateComponentHeight() {
/* 193 */     float offsetY = 0.0F;
/* 194 */     for (AbstractSettingComponent component : this.components) {
/* 195 */       Supplier<Boolean> visible = component.getSetting().getVisible();
/*     */       
/* 197 */       if (visible != null && !((Boolean)visible.get()).booleanValue()) {
/*     */         continue;
/*     */       }
/*     */       
/* 201 */       offsetY += component.height;
/*     */     } 
/* 203 */     return offsetY + 25.0F;
/*     */   }
/*     */   
/*     */   public float getComponentHeight() {
/* 207 */     return this.cachedComponentHeight;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\group\GroupWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */