/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.implement.menu.MenuScreen;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.implement.menu.font.GuiFonts;
/*     */ 
/*     */ public class ModuleDescriptionComponent extends AbstractComponent {
/*  16 */   public void setDescription(String description) { this.description = description; } public void setVisible(boolean visible) { this.visible = visible; }
/*     */ 
/*     */   
/*  19 */   private Module hoveredModule = null; public Module getHoveredModule() { return this.hoveredModule; }
/*  20 */    private Setting hoveredSetting = null; public Setting getHoveredSetting() { return this.hoveredSetting; }
/*  21 */    private String description = ""; public String getDescription() { return this.description; } public boolean isVisible() {
/*  22 */     return this.visible;
/*     */   }
/*     */   private boolean visible = false;
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  27 */     if (this.description.isEmpty() || !this.visible) {
/*     */       return;
/*     */     }
/*     */     
/*  31 */     int screenWidth = context.method_51421();
/*  32 */     int screenHeight = context.method_51443();
/*     */     
/*  34 */     float maxWidth = 300.0F;
/*  35 */     float lineHeight = 4.0F;
/*  36 */     int fontSize = 16;
/*     */     
/*  38 */     String[] lines = wrapText(this.description, maxWidth);
/*  39 */     float textWidth = 0.0F;
/*  40 */     for (String line : lines) {
/*  41 */       float lineWidth = GuiFonts.getSize(fontSize).getStringWidth(line);
/*  42 */       textWidth = Math.max(textWidth, lineWidth);
/*     */     } 
/*     */     
/*  45 */     float padding = 4.0F;
/*  46 */     float boxWidth = textWidth + padding * 2.0F;
/*  47 */     float boxHeight = lines.length * lineHeight + padding * 2.0F;
/*     */     
/*  49 */     int menuX = MenuScreen.INSTANCE.x;
/*  50 */     int menuY = MenuScreen.INSTANCE.y;
/*  51 */     int menuWidth = MenuScreen.INSTANCE.width;
/*     */     
/*  53 */     float boxX = menuX + menuWidth / 2.0F - boxWidth / 2.0F;
/*     */     
/*  55 */     float topY = menuY - boxHeight - 10.0F;
/*  56 */     float bottomY = (menuY + 238);
/*     */ 
/*     */     
/*  59 */     if (topY >= 10.0F) {
/*  60 */       boxY = topY;
/*     */     } else {
/*  62 */       boxY = bottomY;
/*     */     } 
/*     */     
/*  65 */     boxX = Math.max(10.0F, Math.min(boxX, screenWidth - boxWidth - 10.0F));
/*  66 */     float boxY = Math.max(10.0F, Math.min(boxY, screenHeight - boxHeight - 10.0F));
/*     */     
/*  68 */     class_4587 matrices = context.method_51448();
/*  69 */     rectangle.render(ShapeProperties.create(matrices, boxX, boxY, boxWidth, boxHeight)
/*  70 */         .round(4.0F).softness(1.0F).thickness(1.4F)
/*  71 */         .outlineColor(MenuStyle.BORDER_LIGHT)
/*  72 */         .color(MenuStyle.withAlpha(MenuStyle.mix(MenuStyle.PANEL_CHIP, MenuStyle.CARD_INNER, 0.55F), 0.95F))
/*  73 */         .build());
/*     */     
/*  75 */     int textColor = MenuStyle.TEXT_PRIMARY;
/*  76 */     float textX = boxX + padding;
/*  77 */     float textY = boxY + padding;
/*  78 */     for (String line : lines) {
/*  79 */       GuiFonts.getSize(fontSize).drawString(matrices, line, textX, (textY - 0.5F), textColor);
/*  80 */       textY += lineHeight;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHoveredModule(Module module) {
/*  85 */     if (module != null) {
/*  86 */       String moduleDescription = module.getDisplayDescription();
/*  87 */       if (!moduleDescription.isEmpty()) {
/*  88 */         this.hoveredModule = module;
/*  89 */         this.hoveredSetting = null;
/*  90 */         this.description = moduleDescription;
/*  91 */         this.visible = true;
/*     */       } else {
/*  93 */         this.hoveredModule = null;
/*  94 */         this.hoveredSetting = null;
/*  95 */         this.description = "";
/*  96 */         this.visible = false;
/*     */       } 
/*     */     } else {
/*  99 */       this.hoveredModule = null;
/* 100 */       this.hoveredSetting = null;
/* 101 */       this.description = "";
/* 102 */       this.visible = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHoveredSetting(Setting setting) {
/* 107 */     if (setting != null) {
/* 108 */       String settingDescription = setting.getLocalizedDescription();
/* 109 */       if (settingDescription != null && !settingDescription.isEmpty()) {
/* 110 */         this.hoveredModule = null;
/* 111 */         this.hoveredSetting = setting;
/* 112 */         this.description = settingDescription;
/* 113 */         this.visible = true;
/*     */       } else {
/* 115 */         this.hoveredModule = null;
/* 116 */         this.hoveredSetting = null;
/* 117 */         this.description = "";
/* 118 */         this.visible = false;
/*     */       } 
/*     */     } else {
/* 121 */       this.hoveredModule = null;
/* 122 */       this.hoveredSetting = null;
/* 123 */       this.description = "";
/* 124 */       this.visible = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHoveredSettingDescription(String descriptionKey) {
/* 129 */     if (descriptionKey != null && !descriptionKey.isEmpty()) {
/* 130 */       String settingDescription = descriptionKey;
/* 131 */       if (!settingDescription.isEmpty()) {
/* 132 */         this.hoveredModule = null;
/* 133 */         this.hoveredSetting = null;
/* 134 */         this.description = settingDescription;
/* 135 */         this.visible = true;
/*     */       } else {
/* 137 */         this.hoveredModule = null;
/* 138 */         this.hoveredSetting = null;
/* 139 */         this.description = "";
/* 140 */         this.visible = false;
/*     */       } 
/*     */     } else {
/* 143 */       this.hoveredModule = null;
/* 144 */       this.hoveredSetting = null;
/* 145 */       this.description = "";
/* 146 */       this.visible = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void hide() {
/* 151 */     this.visible = false;
/* 152 */     this.hoveredModule = null;
/* 153 */     this.hoveredSetting = null;
/* 154 */     this.description = "";
/*     */   }
/*     */   
/*     */   private String[] wrapText(String text, float maxWidth) {
/* 158 */     if (text.isEmpty()) {
/* 159 */       return new String[0];
/*     */     }
/*     */     
/* 162 */     String[] words = text.split(" ");
/* 163 */     StringBuilder currentLine = new StringBuilder();
/* 164 */     List<String> lines = new ArrayList<>();
/*     */     
/* 166 */     for (String word : words) {
/* 167 */       String testLine = !currentLine.isEmpty() ? (String.valueOf(currentLine) + " " + String.valueOf(currentLine)) : word;
/* 168 */       float lineWidth = GuiFonts.getSize(16).getStringWidth(testLine);
/*     */       
/* 170 */       if (lineWidth <= maxWidth) {
/* 171 */         currentLine = new StringBuilder(testLine);
/*     */       }
/* 173 */       else if (!currentLine.isEmpty()) {
/* 174 */         lines.add(currentLine.toString());
/* 175 */         currentLine = new StringBuilder(word);
/*     */       } else {
/* 177 */         lines.add(word);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 182 */     if (!currentLine.isEmpty()) {
/* 183 */       lines.add(currentLine.toString());
/*     */     }
/*     */     
/* 186 */     return lines.<String>toArray(new String[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\ModuleDescriptionComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */