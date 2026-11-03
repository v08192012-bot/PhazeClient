/*     */ package vorga.phazeclient.implement.menu.components.implement.window;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_332;
/*     */ import vorga.phazeclient.api.system.shape.implement.Blur;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.other.ModuleDescriptionComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.ColorWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.settings.group.GroupWindow;
/*     */ 
/*     */ public class WindowManager
/*     */   extends AbstractComponent
/*     */ {
/*  17 */   public static final WindowManager INSTANCE = new WindowManager();
/*  18 */   private final List<AbstractWindow> windows = new ArrayList<>(); public List<AbstractWindow> getWindows() { return this.windows; }
/*     */   
/*     */   public void add(AbstractWindow window) {
/*  21 */     this.windows.add(window);
/*     */   }
/*     */   
/*     */   public void delete(AbstractWindow window) {
/*  25 */     window.startCloseAnimation();
/*     */   }
/*     */   
/*     */   public void closeAll() {
/*  29 */     for (AbstractWindow window : this.windows) {
/*  30 */       window.startCloseAnimation();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/*  35 */     this.windows.clear();
/*     */   }
/*     */   
/*     */   public boolean isMouseOverAnyWindow(double mouseX, double mouseY) {
/*  39 */     for (AbstractWindow window : this.windows) {
/*  40 */       if (window.isHovered(mouseX, mouseY)) {
/*  41 */         return true;
/*     */       }
/*     */     } 
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  50 */     List<AbstractWindow> toRemove = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (!this.windows.isEmpty()) {
/*  58 */       Blur.INSTANCE.captureMenuOverlayFrame();
/*     */     }
/*     */     
/*  61 */     this.windows.forEach(window -> {
/*     */           window.render(context, mouseX, mouseY, delta);
/*     */           
/*     */           if (window.isCloseAnimationFinished()) {
/*     */             toRemove.add(window);
/*     */           }
/*     */         });
/*  68 */     this.windows.removeAll(toRemove);
/*     */     
/*  70 */     renderWindowHoverDescriptions(context, mouseX, mouseY, delta);
/*     */   }
/*     */   
/*     */   private void renderWindowHoverDescriptions(class_332 context, int mouseX, int mouseY, float delta) {
/*  74 */     MenuScreen menuScreen = MenuScreen.INSTANCE;
/*  75 */     ModuleDescriptionComponent descriptionComponent = menuScreen.getModuleDescriptionComponent();
/*     */     
/*  77 */     if (descriptionComponent != null) {
/*  78 */       boolean mouseOverWindow = false;
/*  79 */       for (int i = this.windows.size() - 1; i >= 0; i--) {
/*  80 */         AbstractWindow window = this.windows.get(i);
/*  81 */         if (window.isHovered(mouseX, mouseY)) {
/*  82 */           mouseOverWindow = true;
/*  83 */           handleWindowHoverDescriptions(window, descriptionComponent, mouseX, mouseY, context, delta);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  88 */       if (!mouseOverWindow) {
/*  89 */         descriptionComponent.hide();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleWindowHoverDescriptions(AbstractWindow window, ModuleDescriptionComponent descriptionComponent, int mouseX, int mouseY, class_332 context, float delta) {
/*  96 */     if (window instanceof GroupWindow) {
/*  97 */       GroupWindow groupWindow = (GroupWindow)window;
/*     */ 
/*     */       
/* 100 */       for (AbstractSettingComponent settingComponent : groupWindow.getComponents()) {
/* 101 */         if (settingComponent.isHover(mouseX, mouseY)) {
/* 102 */           descriptionComponent.setHoveredSetting(settingComponent.getSetting());
/* 103 */           descriptionComponent.render(context, mouseX, mouseY, delta);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 109 */     if (window instanceof ColorWindow) {
/* 110 */       ColorWindow colorWindow = (ColorWindow)window;
/*     */ 
/*     */       
/* 113 */       for (AbstractComponent component : colorWindow.getComponents()) {
/* 114 */         if (component.isHover(mouseX, mouseY)) {
/* 115 */           String componentDescription = getColorComponentDescription(component);
/* 116 */           if (componentDescription != null && !componentDescription.isEmpty()) {
/* 117 */             descriptionComponent.setHoveredSettingDescription(componentDescription);
/* 118 */             descriptionComponent.render(context, mouseX, mouseY, delta);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     descriptionComponent.hide();
/*     */   }
/*     */   
/*     */   private String getColorComponentDescription(AbstractComponent component) {
/* 129 */     String className = component.getClass().getSimpleName();
/* 130 */     switch (className) {
/*     */       case "HueComponent":
/* 132 */         return "setting.color.hue.name";
/*     */       case "SaturationComponent":
/* 134 */         return "setting.color.saturation.name";
/*     */       case "AlphaComponent":
/* 136 */         return "setting.color.alpha.name";
/*     */       case "ColorEditorComponent":
/* 138 */         return "setting.color.editor.name";
/*     */       case "ColorPresetComponent":
/* 140 */         return "setting.color.preset.name";
/*     */     } 
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 148 */     boolean clickedInsideWindow = false;
/*     */     
/* 150 */     List<AbstractWindow> windowsCopy = new ArrayList<>(this.windows);
/*     */     
/* 152 */     for (int i = windowsCopy.size() - 1; i >= 0; i--) {
/* 153 */       AbstractWindow window = windowsCopy.get(i);
/* 154 */       if (window.isHovered(mouseX, mouseY)) {
/* 155 */         clickedInsideWindow = true;
/*     */         
/* 157 */         this.windows.remove(window);
/* 158 */         this.windows.add(window);
/*     */         
/* 160 */         window.mouseClicked(mouseX, mouseY, button);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 165 */     if (!clickedInsideWindow) {
/* 166 */       for (AbstractWindow window : this.windows) {
/* 167 */         window.startCloseAnimation();
/*     */       }
/*     */       
/* 170 */       return false;
/*     */     } 
/*     */     
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 179 */     this.windows.forEach(window -> window.isHovered(mouseX, mouseY));
/*     */     
/* 181 */     for (AbstractWindow window : this.windows) {
/* 182 */       if (window.isHover(mouseX, mouseY)) {
/* 183 */         return true;
/*     */       }
/*     */     } 
/* 186 */     return super.isHover(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 192 */     this.windows.forEach(window -> window.charTyped(chr, modifiers));
/* 193 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 198 */     for (AbstractWindow window : this.windows) {
/* 199 */       if (window.mouseScrolled(mouseX, mouseY, amount)) {
/* 200 */         return true;
/*     */       }
/*     */     } 
/* 203 */     return super.mouseScrolled(mouseX, mouseY, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 208 */     this.windows.forEach(window -> window.keyPressed(keyCode, scanCode, modifiers));
/* 209 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
/* 214 */     this.windows.forEach(window -> window.keyReleased(keyCode, scanCode, modifiers));
/* 215 */     return super.keyReleased(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 220 */     this.windows.forEach(window -> window.mouseReleased(mouseX, mouseY, button));
/* 221 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\WindowManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */