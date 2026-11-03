/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement;
/*     */ 
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.system.localization.LocalizationManager;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.other.StringUtil;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.AbstractWindow;
/*     */ import vorga.phazeclient.implement.menu.components.implement.window.implement.module.ModuleBindWindow;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractBindWindow
/*     */   extends AbstractWindow
/*     */ {
/*     */   private boolean binding;
/*     */   
/*     */   protected abstract int getKey();
/*     */   
/*     */   protected abstract void setKey(int paramInt);
/*     */   
/*     */   protected abstract int getType();
/*     */   
/*     */   protected abstract void setType(int paramInt);
/*     */   
/*     */   public void drawWindow(class_332 context, int mouseX, int mouseY, float delta) {
/*  30 */     class_4587 matrix = context.method_51448();
/*     */     
/*  32 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/*  33 */         .round(4.0F).softness(25.0F).color(838860800).build());
/*     */     
/*  35 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/*  36 */         .round(4.0F).thickness(2.0F).outlineColor(ColorUtil.getOutline(0.8F, 1.0F)).color(ColorUtil.getRect(1.0F)).build());
/*     */     
/*  38 */     GuiFonts.getSize(14).drawString(matrix, LocalizationManager.getInstance().get("ui.binding_module"), (this.x + 5.0F), (this.y + 8.0F), -1);
/*     */     
/*  40 */     image.setTexture("textures/trash.png").render(ShapeProperties.create(matrix, (this.x + this.width - 13.0F), (this.y + 5.3F), 8.0D, 8.0D).build());
/*     */     
/*  42 */     drawKeyButton(matrix);
/*  43 */     drawTypeButton(matrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  48 */     if (button == 0) {
/*  49 */       if (MathUtil.isHovered(mouseX, mouseY, (this.x + this.width - 57.0F), (this.y + 37.0F), 52.0D, 13.0D)) {
/*  50 */         setType((getType() != 1) ? 1 : 0);
/*     */       }
/*     */       
/*  53 */       float stringWidth = GuiFonts.getSize(14).getStringWidth(StringUtil.getBindName(getKey()));
/*     */       
/*  55 */       if (MathUtil.isHovered(mouseX, mouseY, (this.x + this.width - stringWidth - 15.0F), (this.y + 18.8F), (stringWidth + 10.0F), 13.0D)) {
/*  56 */         this.binding = !this.binding;
/*     */       }
/*     */       
/*  59 */       if (MathUtil.isHovered(mouseX, mouseY, (this.x + this.width - 13.0F), (this.y + 5.3F), 8.0D, 8.0D)) {
/*  60 */         setKey(-1);
/*  61 */         if (this instanceof ModuleBindWindow) {
/*  62 */           ((ModuleBindWindow)this).getModule().setState(false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     if (this.binding && button > 1) {
/*  68 */       setKey(button);
/*  69 */       this.binding = false;
/*     */     } 
/*  71 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/*  76 */     int key = (keyCode == 261) ? -1 : keyCode;
/*  77 */     if (this.binding) {
/*  78 */       setKey(key);
/*  79 */       this.binding = false;
/*  80 */       if (key == -1 && this instanceof ModuleBindWindow) {
/*  81 */         ((ModuleBindWindow)this).getModule().setState(false);
/*     */       }
/*     */     } 
/*  84 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawKeyButton(class_4587 matrix) {
/*  89 */     float stringWidth = GuiFonts.getSize(14).getStringWidth(StringUtil.getBindName(getKey()));
/*     */     
/*  91 */     rectangle.render(ShapeProperties.create(matrix, (this.x + this.width - stringWidth - 15.0F), (this.y + 18.8F), (stringWidth + 10.0F), 13.0D)
/*  92 */         .round(2.0F).thickness(2.0F).softness(1.0F).outlineColor(ColorUtil.getOutline(0.8F, 1.0F)).color(ColorUtil.getOutline(0.1F, 1.0F)).build());
/*     */     
/*  94 */     int bindingColor = this.binding ? -8288257 : ColorUtil.getText();
/*     */     
/*  96 */     GuiFonts.getSize(14).drawString(matrix, StringUtil.getBindName(getKey()), (this.x + this.width - 10.0F - stringWidth), (this.y + 23.6F), bindingColor);
/*  97 */     GuiFonts.getSize(14).drawString(matrix, LocalizationManager.getInstance().get("ui.key"), (int)(this.x + 5.0F), (int)(this.y + 24.3D), ColorUtil.getText());
/*     */   }
/*     */   
/*     */   private void drawTypeButton(class_4587 matrix) {
/* 101 */     rectangle.render(ShapeProperties.create(matrix, (this.x + this.width - 57.0F), (this.y + 37.0F), 52.0D, 13.0D)
/* 102 */         .round(2.0F).thickness(2.0F).softness(1.0F).outlineColor(ColorUtil.getOutline(0.8F, 1.0F)).color(ColorUtil.getOutline(0.1F, 1.0F)).build());
/*     */     
/* 104 */     if (getType() == 1) {
/* 105 */       rectangle.render(ShapeProperties.create(matrix, (this.x + this.width - 34.0F), (this.y + 37.0F), 29.0D, 13.0D)
/* 106 */           .round(new float[] { 2.0F, 2.0F, 0.0F, 0.0F }).color(-8288257).build());
/*     */     } else {
/* 108 */       rectangle.render(ShapeProperties.create(matrix, (this.x + this.width - 57.0F), (this.y + 37.0F), 23.0D, 13.0D)
/* 109 */           .round(new float[] { 0.0F, 0.0F, 2.0F, 2.0F }).color(-8288257).build());
/*     */     } 
/*     */     
/* 112 */     GuiFonts.getSize(12).drawString(matrix, LocalizationManager.getInstance().get("ui.hold"), (this.x + 52.0F), this.y + 42.3D, ColorUtil.getText());
/* 113 */     GuiFonts.getSize(12).drawString(matrix, LocalizationManager.getInstance().get("ui.toggle"), (this.x + 73.0F), this.y + 42.3D, ColorUtil.getText());
/*     */     
/* 115 */     GuiFonts.getSize(14).drawString(matrix, LocalizationManager.getInstance().get("ui.bind_mode"), (int)(this.x + 5.0F), (int)(this.y + 42.3F), ColorUtil.getText());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\AbstractBindWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */