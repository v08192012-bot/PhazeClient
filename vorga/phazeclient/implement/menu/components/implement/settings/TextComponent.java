/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_4587;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.TextSetting;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.Fonts;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ import vorga.phazeclient.implement.menu.font.MsdfGuiFontRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextComponent
/*     */   extends AbstractSettingComponent
/*     */ {
/*  28 */   static int ACTIVE_TEXT_COMPONENT_ID = 0;
/*  29 */   private int myActivationId = -1; private boolean typing; private final TextSetting setting;
/*     */   private float rectX;
/*     */   private float rectY;
/*     */   private float rectWidth;
/*     */   private float rectHeight;
/*     */   private boolean dragging;
/*  35 */   private int cursorPosition = 0;
/*  36 */   private int selectionStart = -1;
/*  37 */   private int selectionEnd = -1;
/*  38 */   private long lastClickTime = 0L;
/*  39 */   private float xOffset = 0.0F;
/*  40 */   private String text = "";
/*     */   
/*     */   public TextComponent(TextSetting setting) {
/*  43 */     super((Setting)setting);
/*  44 */     this.setting = setting;
/*  45 */     this.text = (setting.getText() != null) ? setting.getText() : "";
/*  46 */     this.cursorPosition = this.text.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  51 */     updateVisibilityAnimation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (this.typing && this.myActivationId != ACTIVE_TEXT_COMPONENT_ID) {
/*  58 */       this.typing = false;
/*  59 */       this.dragging = false;
/*  60 */       clearSelection();
/*     */     } 
/*     */     
/*  63 */     MsdfGuiFontRenderer labelFont = GuiFonts.getSize(14, Fonts.Type.INTER_BOLD);
/*     */     
/*  65 */     boolean isModified = this.setting.isModified();
/*  66 */     float textOffset = animatedTextOffset(isModified);
/*     */     
/*  68 */     class_4587 matrix = context.method_51448();
/*  69 */     MsdfGuiFontRenderer msdfGuiFontRenderer1 = GuiFonts.getSize(12);
/*     */     
/*  71 */     this.height = 23.5F;
/*  72 */     float hoverProgress = animatedCardHover(MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     boolean overInputRect = MathUtil.isHovered(mouseX, mouseY, (this.x + this.width - 61.5F), (this.y + this.height / 2.0F - 6.0F), 53.0D, 12.0D);
/*     */     
/*  80 */     if (overInputRect || this.typing) {
/*  81 */       CursorManager.requestBeam();
/*     */     }
/*     */     
/*  84 */     renderSettingCard(context, this.typing ? 1.0F : 0.0F, hoverProgress);
/*     */     
/*  86 */     this.rectX = this.x + this.width - 61.5F;
/*  87 */     this.rectY = this.y + this.height / 2.0F - 6.0F;
/*  88 */     this.rectWidth = 53.0F;
/*  89 */     this.rectHeight = 12.0F;
/*     */     
/*  91 */     rectangle.render(ShapeProperties.create(matrix, this.rectX, this.rectY, this.rectWidth, this.rectHeight)
/*  92 */         .round(2.0F).thickness(1.1F)
/*  93 */         .outlineColor(MenuStyle.withAlpha(this.typing ? MenuStyle.CHIP_ACTIVE : MenuStyle.BORDER, this.currentAlpha))
/*  94 */         .color(MenuStyle.withAlpha(MenuStyle.PANEL_CHIP, this.currentAlpha))
/*  95 */         .build());
/*     */     
/*  97 */     this.resetIcon.position(this.x, this.y, this.height).alpha(this.currentAlpha).modified(isModified).render(matrix);
/*     */     
/*  99 */     float textX = this.x + 10.0F + textOffset;
/* 100 */     String labelText = this.label.singleLine(this.setting.getLocalizedName());
/* 101 */     this.label.render(context.method_51448(), (FontRenderer)labelFont, labelText, textX, centeredTextY((FontRenderer)labelFont, labelText), this.rectX - 8.0F, this.y, this.height, 
/* 102 */         primaryText());
/* 103 */     updateLabelHover(mouseX, mouseY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     if (this.dragging) {
/* 113 */       this.cursorPosition = getCursorIndexAt(mouseX);
/* 114 */       if (this.selectionStart == -1) this.selectionStart = this.cursorPosition; 
/* 115 */       this.selectionEnd = this.cursorPosition;
/*     */     } 
/*     */     
/* 118 */     updateXOffset((FontRenderer)msdfGuiFontRenderer1, this.cursorPosition);
/*     */     
/* 120 */     String displayText = this.text.isEmpty() ? ((this.setting.getText() == null) ? "" : this.setting.getText()) : this.text;
/* 121 */     float inputTextY = centeredTextY((FontRenderer)msdfGuiFontRenderer1, displayText.isEmpty() ? "I" : displayText, this.rectY, this.rectHeight);
/* 122 */     float cursorTop = inputTextY - 2.5F;
/* 123 */     float cursorHeight = Math.max(7.0F, renderedTextHeight((FontRenderer)msdfGuiFontRenderer1, "I"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     ScissorManager scissorManager = Main.getInstance().getScissorManager();
/* 136 */     scissorManager.push(matrix.method_23760().method_23761(), this.rectX + 1.0F, this.rectY + 1.0F, this.rectWidth - 2.0F, this.rectHeight - 2.0F);
/*     */ 
/*     */     
/* 139 */     if (this.typing && this.selectionStart != -1 && this.selectionEnd != -1 && this.selectionStart != this.selectionEnd) {
/* 140 */       int start = Math.max(0, Math.min(getStartOfSelection(), this.text.length()));
/* 141 */       int end = Math.max(0, Math.min(getEndOfSelection(), this.text.length()));
/* 142 */       if (start < end) {
/* 143 */         float selectionXStart = this.rectX + 3.0F - this.xOffset + msdfGuiFontRenderer1.getStringWidth(this.text.substring(0, start));
/* 144 */         float selectionXEnd = this.rectX + 3.0F - this.xOffset + msdfGuiFontRenderer1.getStringWidth(this.text.substring(0, end));
/* 145 */         float selectionWidth = selectionXEnd - selectionXStart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 151 */         float selSelH = Math.max(8.0F, cursorHeight + 2.0F);
/* 152 */         float selSelY = this.rectY + (this.rectHeight - selSelH) * 0.5F;
/* 153 */         rectangle.render(ShapeProperties.create(matrix, selectionXStart, selSelY, selectionWidth, selSelH)
/* 154 */             .color(MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, this.currentAlpha))
/* 155 */             .build());
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     msdfGuiFontRenderer1.drawString(context.method_51448(), this.text, (this.rectX + 3.0F - this.xOffset), inputTextY, 
/* 160 */         MenuStyle.withAlpha(this.typing ? MenuStyle.TEXT_PRIMARY : MenuStyle.TEXT_MUTED, this.currentAlpha));
/*     */     
/* 162 */     if (!this.typing && this.text.isEmpty()) {
/* 163 */       msdfGuiFontRenderer1.drawString(context.method_51448(), this.text = this.setting.getText(), (this.rectX + 3.0F), inputTextY, mutedText());
/*     */     }
/*     */     
/* 166 */     long currentTime = System.currentTimeMillis();
/* 167 */     boolean focused = (this.typing && currentTime % 1000L < 500L);
/*     */     
/* 169 */     if (focused && (this.selectionStart == -1 || this.selectionStart == this.selectionEnd)) {
/* 170 */       float cursorX = msdfGuiFontRenderer1.getStringWidth(this.text.substring(0, this.cursorPosition));
/* 171 */       rectangle.render(ShapeProperties.create(matrix, (this.rectX + 3.0F - this.xOffset + cursorX), cursorTop, 0.5D, cursorHeight)
/* 172 */           .color(MenuStyle.withAlpha(-1, this.currentAlpha))
/* 173 */           .build());
/*     */     } 
/*     */     
/* 176 */     context.method_51452();
/* 177 */     scissorManager.pop();
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
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 189 */     if (!this.dragging) {
/* 190 */       return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */     }
/* 192 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 198 */     if (button == 0 && this.resetIcon.isHovered(mouseX, mouseY)) {
/* 199 */       playButtonClickSound();
/* 200 */       this.setting.reset();
/* 201 */       this.text = (this.setting.getText() != null) ? this.setting.getText() : "";
/* 202 */       this.cursorPosition = this.text.length();
/* 203 */       return true;
/*     */     } 
/*     */     
/* 206 */     boolean insideRect = MathUtil.isHovered(mouseX, mouseY, this.rectX, this.rectY, this.rectWidth, this.rectHeight);
/* 207 */     if (insideRect && button == 0) {
/* 208 */       playButtonClickSound();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       this.myActivationId = ++ACTIVE_TEXT_COMPONENT_ID;
/* 218 */       long currentTime = System.currentTimeMillis();
/* 219 */       if (currentTime - this.lastClickTime < 250L) {
/* 220 */         this.selectionStart = 0;
/* 221 */         this.selectionEnd = this.text.length();
/*     */       } else {
/* 223 */         this.typing = true;
/* 224 */         this.dragging = true;
/* 225 */         this.lastClickTime = currentTime;
/* 226 */         this.cursorPosition = getCursorIndexAt(mouseX);
/* 227 */         this.selectionStart = this.cursorPosition;
/* 228 */         this.selectionEnd = this.cursorPosition;
/*     */       } 
/* 230 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     this.typing = false;
/* 236 */     this.dragging = false;
/* 237 */     clearSelection();
/*     */     
/* 239 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 244 */     if (!this.setting.isVisible()) {
/* 245 */       return false;
/*     */     }
/* 247 */     if (MathUtil.isHovered(mouseX, mouseY, this.rectX, this.rectY, this.rectWidth, this.rectHeight)) {
/* 248 */       return true;
/*     */     }
/* 250 */     return MathUtil.isHovered(mouseX, mouseY, (this.x + 9.0F), (this.y + 6.0F), (this.width - this.rectWidth - 18.0F), (this.height - 12.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 255 */     this.dragging = false;
/* 256 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 261 */     if (this.typing && this.text.length() < this.setting.getMax()) {
/* 262 */       deleteSelectedText();
/* 263 */       this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(0, this.cursorPosition) + chr;
/* 264 */       this.cursorPosition++;
/* 265 */       clearSelection();
/*     */     } 
/* 267 */     return super.charTyped(chr, modifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 273 */     if (this.typing)
/* 274 */       if (class_437.method_25441()) { switch (keyCode) { case 65:
/* 275 */             selectAllText(); break;
/* 276 */           case 86: pasteFromClipboard(); break;
/* 277 */           case 67: copyToClipboard(); break; }
/*     */          }
/* 279 */       else { switch (keyCode) { case 257: case 259:
/* 280 */             handleTextModification(keyCode); break;
/* 281 */           case 262: case 263: moveCursor(keyCode); break; }
/*     */          }
/*     */        
/* 284 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */   
/*     */   private void pasteFromClipboard() {
/* 289 */     String clipboardText = GLFW.glfwGetClipboardString(window().method_4490());
/* 290 */     if (clipboardText != null) {
/* 291 */       replaceText(this.cursorPosition, this.cursorPosition, clipboardText);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyToClipboard() {
/* 297 */     if (hasSelection()) {
/* 298 */       GLFW.glfwSetClipboardString(window().method_4490(), getSelectedText());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectAllText() {
/* 304 */     this.selectionStart = 0;
/* 305 */     this.selectionEnd = this.text.length();
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleTextModification(int keyCode) {
/* 310 */     if (keyCode == 259) {
/* 311 */       if (hasSelection()) {
/* 312 */         replaceText(getStartOfSelection(), getEndOfSelection(), "");
/* 313 */       } else if (this.cursorPosition > 0) {
/* 314 */         replaceText(this.cursorPosition - 1, this.cursorPosition, "");
/*     */       } 
/* 316 */     } else if (keyCode == 257 && 
/* 317 */       this.text.length() >= this.setting.getMin() && this.text.length() <= this.setting.getMax()) {
/* 318 */       this.setting.setText(this.text);
/* 319 */       this.typing = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveCursor(int keyCode) {
/* 326 */     if (keyCode == 263 && this.cursorPosition > 0) {
/* 327 */       this.cursorPosition--;
/* 328 */     } else if (keyCode == 262 && this.cursorPosition < this.text.length()) {
/* 329 */       this.cursorPosition++;
/*     */     } 
/* 331 */     updateSelectionAfterCursorMove();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSelectionAfterCursorMove() {
/* 336 */     if (class_437.method_25442()) {
/* 337 */       if (this.selectionStart == -1) this.selectionStart = this.cursorPosition; 
/* 338 */       this.selectionEnd = this.cursorPosition;
/*     */     } else {
/* 340 */       clearSelection();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void replaceText(int start, int end, String replacement) {
/* 346 */     if (start < 0) start = 0; 
/* 347 */     if (end > this.text.length()) end = this.text.length(); 
/* 348 */     if (start > end) start = end;
/*     */     
/* 350 */     this.text = this.text.substring(0, start) + this.text.substring(0, start) + replacement;
/* 351 */     this.cursorPosition = start + replacement.length();
/* 352 */     clearSelection();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasSelection() {
/* 357 */     return (this.selectionStart != -1 && this.selectionEnd != -1 && this.selectionStart != this.selectionEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSelectedText() {
/* 362 */     return this.text.substring(getStartOfSelection(), getEndOfSelection());
/*     */   }
/*     */ 
/*     */   
/*     */   private int getStartOfSelection() {
/* 367 */     return Math.min(this.selectionStart, this.selectionEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEndOfSelection() {
/* 372 */     return Math.max(this.selectionStart, this.selectionEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearSelection() {
/* 377 */     this.selectionStart = -1;
/* 378 */     this.selectionEnd = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getCursorIndexAt(double mouseX) {
/* 383 */     MsdfGuiFontRenderer msdfGuiFontRenderer = GuiFonts.getSize(12, Fonts.Type.INTER_BOLD);
/* 384 */     float relativeX = (float)mouseX - this.rectX - 3.0F + this.xOffset;
/* 385 */     int position = 0;
/* 386 */     while (position < this.text.length()) {
/* 387 */       float textWidth = msdfGuiFontRenderer.getStringWidth(this.text.substring(0, position + 1));
/* 388 */       if (textWidth > relativeX) {
/*     */         break;
/*     */       }
/* 391 */       position++;
/*     */     } 
/* 393 */     return position;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateXOffset(FontRenderer font, int cursorPosition) {
/* 398 */     float cursorX = font.getStringWidth(this.text.substring(0, cursorPosition));
/* 399 */     if (cursorX < this.xOffset) {
/* 400 */       this.xOffset = cursorX;
/* 401 */     } else if (cursorX - this.xOffset > this.rectWidth - 7.0F) {
/* 402 */       this.xOffset = cursorX - this.rectWidth - 7.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void deleteSelectedText() {
/* 408 */     if (hasSelection())
/* 409 */       replaceText(getStartOfSelection(), getEndOfSelection(), ""); 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\TextComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */