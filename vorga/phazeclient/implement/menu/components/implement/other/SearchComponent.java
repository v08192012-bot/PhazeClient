/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFont;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.base.util.render.ScissorManager;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ 
/*     */ public class SearchComponent extends AbstractComponent {
/*     */   public static boolean typing = false;
/*  32 */   private int cursorPosition = 0;
/*  33 */   private int selectionStart = -1;
/*  34 */   private int selectionEnd = -1;
/*     */   private boolean dragging = false;
/*  36 */   private long lastClickTime = 0L;
/*  37 */   private float xOffset = 0.0F;
/*  38 */   private String text = ""; public String getText() { return this.text; }
/*     */   
/*  40 */   private ModuleCategory previousCategory = ModuleCategory.ALL; public void setPreviousCategory(ModuleCategory previousCategory) { this.previousCategory = previousCategory; } public ModuleCategory getPreviousCategory() {
/*  41 */     return this.previousCategory;
/*     */   }
/*     */   
/*  44 */   private float animatedWidth = 82.0F;
/*     */   private static final float COLLAPSED_WIDTH = 82.0F;
/*     */   private static final float EXPANDED_WIDTH = 82.0F;
/*     */   private static final float ICON_SIZE = 7.0F;
/*     */   private static final float SEARCH_HEIGHT = 14.0F;
/*     */   private static final float SEARCH_TEXT_SIZE = 6.0F;
/*     */   private static final float MSDF_GLYPH_TRACKING = 0.15F;
/*  51 */   private static final class_2960 SEARCH_ICON = class_2960.method_60656("textures/search_lunar.png");
/*     */   
/*     */   private static final float INPUT_TEXT_X_OFFSET = 12.0F;
/*     */   
/*     */   private static final float INPUT_SCISSOR_X_OFFSET = 7.0F;
/*     */   private static final float INPUT_SCISSOR_RIGHT_INSET = 2.0F;
/*     */   private static final float SELECTION_X_OFFSET = 0.0F;
/*     */   private static final float SELECTION_EXPANSION = 1.5F;
/*  59 */   private final Animation hoverAnimation = (new DecelerateAnimation()).setMs(200).setValue(0.15000000596046448D);
/*  60 */   private final Animation selectAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D);
/*     */   
/*     */   public float getAnimationProgress() {
/*  63 */     float denominator = 0.0F;
/*  64 */     if (Math.abs(denominator) < 0.001F) {
/*  65 */       return 1.0F;
/*     */     }
/*  67 */     return (this.animatedWidth - 82.0F) / denominator;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/*  71 */     this.text = text;
/*  72 */     this.cursorPosition = text.length();
/*  73 */     clearSelection();
/*     */   }
/*     */   
/*     */   public void setCursorPosition(int position) {
/*  77 */     this.cursorPosition = Math.max(0, Math.min(position, this.text.length()));
/*     */   }
/*     */   
/*     */   private boolean hasSelection() {
/*  81 */     return (this.selectionStart != -1 && this.selectionEnd != -1 && this.selectionStart != this.selectionEnd);
/*     */   }
/*     */   
/*  84 */   private int selStart() { return Math.min(this.selectionStart, this.selectionEnd); } private int selEnd() {
/*  85 */     return Math.max(this.selectionStart, this.selectionEnd);
/*     */   }
/*     */   private void clearSelection() {
/*  88 */     this.selectionStart = -1;
/*  89 */     this.selectionEnd = -1;
/*     */   }
/*     */   
/*     */   private void deleteSelectedText() {
/*  93 */     if (hasSelection()) {
/*  94 */       int s = selStart();
/*  95 */       int e = selEnd();
/*  96 */       this.text = this.text.substring(0, s) + this.text.substring(0, s);
/*  97 */       this.cursorPosition = s;
/*  98 */       clearSelection();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*     */     float iconX;
/* 104 */     class_4587 matrix = context.method_51448();
/* 105 */     MsdfFont font = MsdfFonts.medium();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (this.dragging && typing) {
/* 112 */       this.cursorPosition = getCursorIndexAt(mouseX);
/* 113 */       if (this.selectionStart == -1) this.selectionStart = this.cursorPosition; 
/* 114 */       this.selectionEnd = this.cursorPosition;
/*     */     } 
/*     */     
/* 117 */     updateXOffset(font, this.cursorPosition);
/*     */     
/* 119 */     float targetWidth = typing ? 82.0F : 82.0F;
/* 120 */     this.animatedWidth = class_3532.method_16439(delta * 0.5F, this.animatedWidth, targetWidth);
/*     */     
/* 122 */     this.width = this.animatedWidth;
/* 123 */     this.height = 14.0F;
/*     */     
/* 125 */     boolean isSearchSelected = (MenuScreen.INSTANCE.getCategory() == ModuleCategory.SEARCH);
/*     */     
/* 127 */     boolean isHovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/* 128 */     this.hoverAnimation.setDirection(isHovered ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (isHovered || typing) {
/* 135 */       CursorManager.requestBeam();
/*     */     }
/*     */     
/* 138 */     this.selectAnimation.setDirection(isSearchSelected ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     
/* 140 */     float hoverProgress = this.hoverAnimation.getOutputFloat();
/* 141 */     float selectProgress = this.selectAnimation.getOutputFloat();
/*     */     
/* 143 */     int outlineColor = MenuStyle.mix(MenuStyle.BORDER, MenuStyle.CHIP_ACTIVE, selectProgress);
/* 144 */     outlineColor = MenuStyle.mix(outlineColor, MenuStyle.CHIP_ACTIVE, hoverProgress * 0.5F);
/* 145 */     int iconColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, selectProgress);
/*     */     
/* 147 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, this.width, this.height)
/* 148 */         .round(2.0F).thickness(3.0F).softness(1.0F).outlineColor(applyGlobalAlpha(outlineColor)).color(0).build());
/*     */ 
/*     */     
/* 151 */     float iconY = this.y + (this.height - 7.0F) / 2.0F;
/*     */     
/* 153 */     if (this.animatedWidth < 81.0F) {
/* 154 */       float progress = (this.animatedWidth - 82.0F) / 0.0F;
/* 155 */       float collapsedIconX = this.x + 4.0F;
/* 156 */       float expandedIconX = this.x + 4.0F;
/* 157 */       iconX = class_3532.method_16439(progress, collapsedIconX, expandedIconX);
/*     */     } else {
/* 159 */       iconX = this.x + 4.0F;
/*     */     } 
/*     */     
/* 162 */     UiMsdfIconAtlas.renderIcon(context, SEARCH_ICON, iconX, iconY, 7.0F, 7.0F, 
/* 163 */         applyGlobalAlpha(iconColor), true);
/*     */     
/* 165 */     float animationProgress = getAnimationProgress();
/* 166 */     float textAlpha = Math.max(0.0F, Math.min(1.0F, (animationProgress - 0.2F) / 0.6F));
/* 167 */     float textX = this.x + 12.0F - this.xOffset;
/*     */     
/* 169 */     if (textAlpha > 0.01F) {
/* 170 */       String displayText = this.text;
/* 171 */       float inputTextY = MenuStyle.centerMsdfTextY(6.0F, this.y, this.height);
/*     */       
/* 173 */       ScissorManager scissor = Main.getInstance().getScissorManager();
/* 174 */       scissor.push(matrix.method_23760().method_23761(), this.x + 7.0F, this.y, this.width - 7.0F - 2.0F, this.height);
/*     */ 
/*     */       
/* 177 */       if (typing && hasSelection()) {
/* 178 */         int s = Math.max(0, Math.min(selStart(), this.text.length()));
/* 179 */         int e = Math.max(0, Math.min(selEnd(), this.text.length()));
/* 180 */         if (s < e) {
/* 181 */           float selX0 = textX + font.getWidth(this.text.substring(0, s), 6.0F) + 0.0F - 1.5F;
/*     */           
/* 183 */           float selX1 = textX + font.getWidth(this.text.substring(0, e), 6.0F) + 0.0F + 1.5F;
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
/* 195 */           float selH = 8.0F;
/* 196 */           float selY = this.y + (this.height - selH) * 0.5F;
/* 197 */           rectangle.render(ShapeProperties.create(matrix, selX0, selY, (selX1 - selX0), selH)
/* 198 */               .color(MenuStyle.withAlpha(MenuStyle.CHIP_ACTIVE, applyGlobalAlpha(textAlpha)))
/* 199 */               .build());
/*     */         } 
/*     */       } 
/*     */       
/* 203 */       if (!this.text.isEmpty() && typing) {
/* 204 */         String searchText = this.text.toLowerCase();
/*     */         
/* 206 */         int autocompleteAlpha = (int)(applyGlobalAlpha(textAlpha) * 119.0F) << 24;
/* 207 */         int autocompleteColor = autocompleteAlpha | 0x777777;
/*     */         
/* 209 */         Main.getInstance().getModuleProvider().getModules().stream()
/* 210 */           .filter(Module::isVisible)
/* 211 */           .filter(mod -> mod.getLocalizedName().toLowerCase().startsWith(searchText))
/* 212 */           .findFirst()
/* 213 */           .ifPresentOrElse(module -> {
/*     */               String completion = module.getLocalizedName();
/*     */ 
/*     */               
/*     */               String remainingText = completion.substring(this.text.length());
/*     */ 
/*     */               
/*     */               float textWidth = getRenderedTextWidth(font, this.text);
/*     */               
/*     */               drawMsdfText(context, remainingText, textX + textWidth, inputTextY, autocompleteColor);
/*     */             }() -> findFirstMatchingSetting(searchText).ifPresent(()));
/*     */       } 
/*     */       
/* 226 */       if (displayText.isEmpty() && !typing) {
/* 227 */         drawMsdfText(context, Lang.translate("Search"), this.x + 12.0F, inputTextY, 
/* 228 */             MenuStyle.withAlpha(MenuStyle.TEXT_MUTED, applyGlobalAlpha(textAlpha)));
/*     */       } else {
/* 230 */         drawMsdfText(context, displayText, textX, inputTextY, 
/* 231 */             MenuStyle.withAlpha(-1, applyGlobalAlpha(textAlpha)));
/*     */       } 
/*     */       
/* 234 */       scissor.pop();
/*     */       
/* 236 */       long currentTime = System.currentTimeMillis();
/* 237 */       boolean focused = (typing && currentTime % 1000L < 500L);
/*     */       
/* 239 */       if (focused && textAlpha > 0.5F && !hasSelection()) {
/* 240 */         float cursorX = font.getWidth(this.text.substring(0, this.cursorPosition), 6.0F);
/* 241 */         int cursorColor = (int)(applyGlobalAlpha(textAlpha) * 255.0F) << 24 | 0xFFFFFF;
/* 242 */         float cursorHeight = 7.0F;
/* 243 */         rectangle.render(ShapeProperties.create(matrix, (this.x + 12.0F - this.xOffset + cursorX), (this.y + (this.height - cursorHeight) * 0.5F), 0.5D, cursorHeight)
/* 244 */             .color(cursorColor).build());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getRenderedTextWidth(MsdfFont font, String value) {
/* 254 */     return font.getWidth(value, 6.0F) + value.length() * 0.15F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 260 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/* 261 */     if (hovered && button == 0) {
/* 262 */       long currentTime = System.currentTimeMillis();
/* 263 */       if (currentTime - this.lastClickTime < 250L && typing) {
/*     */         
/* 265 */         this.selectionStart = 0;
/* 266 */         this.selectionEnd = this.text.length();
/* 267 */         this.cursorPosition = this.text.length();
/*     */       } else {
/*     */         
/* 270 */         boolean wasTyping = typing;
/* 271 */         typing = true;
/* 272 */         this.lastClickTime = currentTime;
/*     */         
/* 274 */         if (wasTyping && this.animatedWidth > 77.0F) {
/* 275 */           this.cursorPosition = getCursorIndexAt(mouseX);
/*     */         } else {
/* 277 */           this.text = "";
/* 278 */           this.cursorPosition = 0;
/* 279 */           this.xOffset = 0.0F;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 285 */         this.dragging = true;
/* 286 */         this.selectionStart = this.cursorPosition;
/* 287 */         this.selectionEnd = this.cursorPosition;
/*     */         
/* 289 */         MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 290 */         if (menuScreen.getCategory() != ModuleCategory.SEARCH) {
/* 291 */           this.previousCategory = menuScreen.getCategory();
/*     */         }
/* 293 */         if (!this.text.isEmpty()) {
/* 294 */           menuScreen.setCategory(ModuleCategory.SEARCH);
/*     */         }
/*     */       }
/*     */     
/* 298 */     } else if (typing) {
/* 299 */       typing = false;
/* 300 */       clearSelection();
/* 301 */       if (this.text.isEmpty()) {
/* 302 */         MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 303 */         if (menuScreen.getCategory() == ModuleCategory.SEARCH) {
/* 304 */           menuScreen.setCategory(this.previousCategory);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 309 */     return (hovered && button == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 314 */     if (button == 0) {
/* 315 */       this.dragging = false;
/*     */     }
/* 317 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
/* 322 */     if (button == 0 && this.dragging && typing) {
/* 323 */       this.cursorPosition = getCursorIndexAt(mouseX);
/* 324 */       if (this.selectionStart == -1) this.selectionStart = this.cursorPosition; 
/* 325 */       this.selectionEnd = this.cursorPosition;
/*     */     } 
/* 327 */     return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 335 */     float maxTextWidth = 58.0F;
/* 336 */     if (typing && MsdfFonts.medium().getWidth(this.text, 6.0F) < maxTextWidth) {
/* 337 */       if ((chr == '/' || chr == '.') && this.text.isEmpty()) {
/* 338 */         return true;
/*     */       }
/*     */       
/* 341 */       if (this.text.isEmpty()) {
/* 342 */         MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 343 */         if (menuScreen.getCategory() != ModuleCategory.SEARCH) {
/* 344 */           this.previousCategory = menuScreen.getCategory();
/* 345 */           menuScreen.setCategory(ModuleCategory.SEARCH);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 352 */       deleteSelectedText();
/* 353 */       this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(0, this.cursorPosition) + chr;
/* 354 */       this.cursorPosition++;
/* 355 */       clearSelection();
/* 356 */       return true;
/*     */     } 
/* 358 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 364 */     if (typing)
/* 365 */       if ((modifiers & 0x2) != 0) {
/* 366 */         String clip; switch (keyCode) { case 65:
/* 367 */             this.selectionStart = 0; this.selectionEnd = this.text.length(); this.cursorPosition = this.text.length(); break;
/*     */           case 67:
/* 369 */             if (hasSelection()) {
/* 370 */               GLFW.glfwSetClipboardString(window().method_4490(), this.text.substring(selStart(), selEnd()));
/*     */             }
/*     */             break;
/*     */           case 88:
/* 374 */             if (hasSelection()) {
/* 375 */               GLFW.glfwSetClipboardString(window().method_4490(), this.text.substring(selStart(), selEnd()));
/* 376 */               deleteSelectedText();
/*     */             } 
/*     */             break;
/*     */           case 86:
/* 380 */             clip = GLFW.glfwGetClipboardString(window().method_4490());
/* 381 */             if (clip != null && !clip.isEmpty()) {
/* 382 */               deleteSelectedText();
/* 383 */               float maxTextWidth = 58.0F;
/* 384 */               for (char c : clip.toCharArray()) {
/* 385 */                 if (MsdfFonts.medium().getWidth(this.text, 6.0F) >= maxTextWidth)
/* 386 */                   break;  this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(0, this.cursorPosition) + c;
/* 387 */                 this.cursorPosition++;
/*     */               } 
/* 389 */               clearSelection();
/* 390 */               MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 391 */               if (!this.text.isEmpty() && menuScreen.getCategory() != ModuleCategory.SEARCH) {
/* 392 */                 this.previousCategory = menuScreen.getCategory();
/* 393 */                 menuScreen.setCategory(ModuleCategory.SEARCH);
/*     */               } 
/*     */             } 
/*     */             break; }
/*     */       
/*     */       } else {
/* 399 */         switch (keyCode) { case 257: case 259:
/* 400 */             handleTextModification(keyCode); break;
/* 401 */           case 262: case 263: moveCursor(keyCode, modifiers); break;
/* 402 */           case 258: handleTabCompletion();
/*     */             break; }
/*     */       
/*     */       }  
/* 406 */     return super.keyPressed(keyCode, scanCode, modifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleTextModification(int keyCode) {
/* 413 */     if (keyCode == 259) {
/* 414 */       if (hasSelection()) {
/* 415 */         deleteSelectedText();
/* 416 */       } else if (this.cursorPosition > 0) {
/* 417 */         this.text = this.text.substring(0, this.cursorPosition - 1) + this.text.substring(0, this.cursorPosition - 1);
/* 418 */         this.cursorPosition--;
/*     */       } 
/*     */       
/* 421 */       if (this.text.isEmpty()) {
/* 422 */         MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 423 */         if (menuScreen.getCategory() == ModuleCategory.SEARCH) {
/* 424 */           menuScreen.setCategory(this.previousCategory);
/*     */         }
/*     */       } 
/* 427 */     } else if (keyCode == 257) {
/* 428 */       typing = false;
/* 429 */       clearSelection();
/* 430 */       if (this.text.isEmpty()) {
/* 431 */         MenuScreen menuScreen = MenuScreen.INSTANCE;
/* 432 */         if (menuScreen.getCategory() == ModuleCategory.SEARCH) {
/* 433 */           menuScreen.setCategory(this.previousCategory);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveCursor(int keyCode, int modifiers) {
/* 441 */     boolean shift = ((modifiers & 0x1) != 0);
/* 442 */     if (keyCode == 263 && this.cursorPosition > 0) {
/* 443 */       this.cursorPosition--;
/* 444 */     } else if (keyCode == 262 && this.cursorPosition < this.text.length()) {
/* 445 */       this.cursorPosition++;
/*     */     } 
/* 447 */     if (shift) {
/* 448 */       if (this.selectionStart == -1) this.selectionStart = this.cursorPosition; 
/* 449 */       this.selectionEnd = this.cursorPosition;
/*     */     } else {
/* 451 */       clearSelection();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getCursorIndexAt(double mouseX) {
/* 459 */     MsdfFont font = MsdfFonts.medium();
/* 460 */     float relativeX = (float)mouseX - this.x - 12.0F + this.xOffset;
/* 461 */     int position = 0;
/* 462 */     while (position < this.text.length()) {
/* 463 */       float textWidth = font.getWidth(this.text.substring(0, position + 1), 6.0F);
/* 464 */       if (textWidth > relativeX) {
/*     */         break;
/*     */       }
/* 467 */       position++;
/*     */     } 
/* 469 */     return position;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateXOffset(MsdfFont font, int cursorPosition) {
/* 474 */     float cursorX = font.getWidth(this.text.substring(0, cursorPosition), 6.0F);
/* 475 */     float availableWidth = this.width - 7.0F - 17.0F;
/* 476 */     if (cursorX < this.xOffset) {
/* 477 */       this.xOffset = cursorX;
/* 478 */     } else if (cursorX - this.xOffset > availableWidth) {
/* 479 */       this.xOffset = cursorX - availableWidth;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawMsdfText(class_332 context, String value, float drawX, float drawY, int color) {
/* 484 */     if (value == null || value.isEmpty()) {
/*     */       return;
/*     */     }
/* 487 */     MsdfRenderer.renderText(
/* 488 */         MsdfFonts.medium(), value, 6.0F, color, context
/*     */ 
/*     */ 
/*     */         
/* 492 */         .method_51448().method_23760().method_23761(), drawX, drawY, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleTabCompletion() {
/* 501 */     if (!this.text.isEmpty()) {
/* 502 */       String searchText = this.text.toLowerCase();
/*     */       
/* 504 */       Main.getInstance().getModuleProvider().getModules().stream()
/* 505 */         .filter(Module::isVisible)
/* 506 */         .filter(mod -> mod.getLocalizedName().toLowerCase().startsWith(searchText))
/* 507 */         .findFirst()
/* 508 */         .ifPresentOrElse(module -> {
/*     */             this.text = module.getLocalizedName();
/*     */             this.cursorPosition = this.text.length();
/*     */           }() -> findFirstMatchingSetting(searchText).ifPresent(()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<Setting> findFirstMatchingSetting(String searchText) {
/* 521 */     return Main.getInstance().getModuleProvider().getModules().stream()
/* 522 */       .filter(Module::isVisible)
/* 523 */       .flatMap(module -> findMatchingSettingInModule(module, searchText).stream())
/* 524 */       .findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   private Optional<Setting> findMatchingSettingInModule(Module module, String searchText) {
/* 529 */     return module.settings().stream()
/* 530 */       .flatMap(setting -> findMatchingSettingRecursive(setting, searchText).stream())
/* 531 */       .findFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<Setting> findMatchingSettingRecursive(Setting setting, String searchText) {
/* 537 */     if (setting.getLocalizedName().toLowerCase().startsWith(searchText)) {
/* 538 */       return Optional.of(setting);
/*     */     }
/*     */     
/* 541 */     if (setting instanceof GroupSetting) { GroupSetting groupSetting = (GroupSetting)setting;
/* 542 */       return groupSetting.getSubSettings().stream()
/* 543 */         .flatMap(subSetting -> findMatchingSettingRecursive(subSetting, searchText).stream())
/* 544 */         .findFirst(); }
/*     */ 
/*     */     
/* 547 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\SearchComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */