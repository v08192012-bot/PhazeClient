/*     */ package vorga.phazeclient.implement.menu;
/*     */ 
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.font.FontRenderer;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFont;
/*     */ 
/*     */ public final class MenuStyle {
/*     */   private static final float MSDF_CENTER_Y_OFFSET = 1.15F;
/*     */   private static final int TRANSITION_MS = 300;
/*  12 */   private static MenuPalette currentPalette = MenuPalettes.LUNAR_BLUE;
/*  13 */   private static MenuPalette previousPalette = MenuPalettes.LUNAR_BLUE;
/*  14 */   private static MenuPalette targetPalette = MenuPalettes.LUNAR_BLUE;
/*  15 */   private static final Animation themeAnimation = (new DecelerateAnimation()).setMs(300).setValue(1.0D);
/*  16 */   private static String appliedPaletteName = currentPalette.name();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean paletteStable = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   public static int PANEL_BG = currentPalette.panelBg();
/*  33 */   public static int PANEL_BG_SOFT = currentPalette.panelBgSoft();
/*  34 */   public static int PANEL_HEADER = currentPalette.panelHeader();
/*  35 */   public static int PANEL_SIDEBAR = currentPalette.panelSidebar();
/*  36 */   public static int PANEL_CONTENT = currentPalette.panelContent();
/*  37 */   public static int PANEL_CHIP = currentPalette.panelChip();
/*  38 */   public static int PANEL_ROW = currentPalette.panelRow();
/*  39 */   public static int PANEL_ROW_ACTIVE = currentPalette.panelRowActive();
/*     */   
/*  41 */   public static int CARD_BG = currentPalette.cardBg();
/*  42 */   public static int CARD_INNER = currentPalette.cardInner();
/*  43 */   public static int CARD_OPTIONS = currentPalette.cardOptions();
/*  44 */   public static int CARD_DISABLED = currentPalette.cardDisabled();
/*     */   
/*  46 */   public static int BORDER = currentPalette.border();
/*  47 */   public static int BORDER_LIGHT = currentPalette.borderLight();
/*     */   
/*  49 */   public static int TEXT_PRIMARY = currentPalette.textPrimary();
/*  50 */   public static int TEXT_MUTED = currentPalette.textMuted();
/*     */   
/*  52 */   public static int CHIP_ACTIVE = currentPalette.chipActive();
/*  53 */   public static int CHIP_NEW = currentPalette.chipNew();
/*  54 */   public static int ACCENT_GREEN = currentPalette.accentGreen();
/*     */   
/*     */   public static void apply(MenuPalette palette) {
/*  57 */     MenuPalette newPalette = (palette == null) ? MenuPalettes.LUNAR_BLUE : palette;
/*  58 */     String newName = newPalette.name();
/*     */     
/*  60 */     if (!newName.equals(appliedPaletteName)) {
/*  61 */       previousPalette = currentPalette;
/*  62 */       targetPalette = newPalette;
/*  63 */       appliedPaletteName = newName;
/*  64 */       themeAnimation.setDirection(Direction.FORWARDS);
/*  65 */       themeAnimation.reset();
/*  66 */       paletteStable = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     if (paletteStable) {
/*  77 */       currentPalette = newPalette;
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     float progress = themeAnimation.getOutputFloat();
/*  82 */     currentPalette = newPalette;
/*     */     
/*  84 */     if (progress < 1.0F) {
/*  85 */       PANEL_BG = mix(previousPalette.panelBg(), targetPalette.panelBg(), progress);
/*  86 */       PANEL_BG_SOFT = mix(previousPalette.panelBgSoft(), targetPalette.panelBgSoft(), progress);
/*  87 */       PANEL_HEADER = mix(previousPalette.panelHeader(), targetPalette.panelHeader(), progress);
/*  88 */       PANEL_SIDEBAR = mix(previousPalette.panelSidebar(), targetPalette.panelSidebar(), progress);
/*  89 */       PANEL_CONTENT = mix(previousPalette.panelContent(), targetPalette.panelContent(), progress);
/*  90 */       PANEL_CHIP = mix(previousPalette.panelChip(), targetPalette.panelChip(), progress);
/*  91 */       PANEL_ROW = mix(previousPalette.panelRow(), targetPalette.panelRow(), progress);
/*  92 */       PANEL_ROW_ACTIVE = mix(previousPalette.panelRowActive(), targetPalette.panelRowActive(), progress);
/*     */       
/*  94 */       CARD_BG = mix(previousPalette.cardBg(), targetPalette.cardBg(), progress);
/*  95 */       CARD_INNER = mix(previousPalette.cardInner(), targetPalette.cardInner(), progress);
/*  96 */       CARD_OPTIONS = mix(previousPalette.cardOptions(), targetPalette.cardOptions(), progress);
/*  97 */       CARD_DISABLED = mix(previousPalette.cardDisabled(), targetPalette.cardDisabled(), progress);
/*     */       
/*  99 */       BORDER = mix(previousPalette.border(), targetPalette.border(), progress);
/* 100 */       BORDER_LIGHT = mix(previousPalette.borderLight(), targetPalette.borderLight(), progress);
/*     */       
/* 102 */       TEXT_PRIMARY = mix(previousPalette.textPrimary(), targetPalette.textPrimary(), progress);
/* 103 */       TEXT_MUTED = mix(previousPalette.textMuted(), targetPalette.textMuted(), progress);
/*     */       
/* 105 */       CHIP_ACTIVE = mix(previousPalette.chipActive(), targetPalette.chipActive(), progress);
/* 106 */       CHIP_NEW = mix(previousPalette.chipNew(), targetPalette.chipNew(), progress);
/* 107 */       ACCENT_GREEN = mix(previousPalette.accentGreen(), targetPalette.accentGreen(), progress);
/*     */     } else {
/* 109 */       PANEL_BG = targetPalette.panelBg();
/* 110 */       PANEL_BG_SOFT = targetPalette.panelBgSoft();
/* 111 */       PANEL_HEADER = targetPalette.panelHeader();
/* 112 */       PANEL_SIDEBAR = targetPalette.panelSidebar();
/* 113 */       PANEL_CONTENT = targetPalette.panelContent();
/* 114 */       PANEL_CHIP = targetPalette.panelChip();
/* 115 */       PANEL_ROW = targetPalette.panelRow();
/* 116 */       PANEL_ROW_ACTIVE = targetPalette.panelRowActive();
/*     */       
/* 118 */       CARD_BG = targetPalette.cardBg();
/* 119 */       CARD_INNER = targetPalette.cardInner();
/* 120 */       CARD_OPTIONS = targetPalette.cardOptions();
/* 121 */       CARD_DISABLED = targetPalette.cardDisabled();
/*     */       
/* 123 */       BORDER = targetPalette.border();
/* 124 */       BORDER_LIGHT = targetPalette.borderLight();
/*     */       
/* 126 */       TEXT_PRIMARY = targetPalette.textPrimary();
/* 127 */       TEXT_MUTED = targetPalette.textMuted();
/*     */       
/* 129 */       CHIP_ACTIVE = targetPalette.chipActive();
/* 130 */       CHIP_NEW = targetPalette.chipNew();
/* 131 */       ACCENT_GREEN = targetPalette.accentGreen();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       paletteStable = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MenuPalette currentPalette() {
/* 141 */     return currentPalette;
/*     */   }
/*     */   
/*     */   public static int withAlpha(int color, float alpha) {
/* 145 */     int a = Math.max(0, Math.min(255, Math.round((color >>> 24 & 0xFF) * alpha)));
/* 146 */     return color & 0xFFFFFF | a << 24;
/*     */   }
/*     */   
/*     */   public static int detailScrim(float alpha) {
/* 150 */     return withAlpha(mix(PANEL_BG, -16777216, 0.55F), alpha);
/*     */   }
/*     */   
/*     */   public static int settingSurface(boolean active) {
/* 154 */     return active ? 
/* 155 */       mix(PANEL_CHIP, CARD_OPTIONS, 0.5F) : 
/* 156 */       mix(PANEL_CHIP, CARD_INNER, 0.38F);
/*     */   }
/*     */   
/*     */   public static int settingOutline(boolean active) {
/* 160 */     return active ? 
/* 161 */       mix(BORDER_LIGHT, CHIP_ACTIVE, 0.4F) : 
/* 162 */       BORDER;
/*     */   }
/*     */   
/*     */   public static int accent(boolean success) {
/* 166 */     return success ? ACCENT_GREEN : CHIP_ACTIVE;
/*     */   }
/*     */   
/*     */   public static int pill(boolean active) {
/* 170 */     return active ? 
/* 171 */       mix(CHIP_ACTIVE, ACCENT_GREEN, 0.12F) : 
/* 172 */       mix(PANEL_CHIP, CARD_OPTIONS, 0.2F);
/*     */   }
/*     */   
/*     */   public static int mix(int from, int to, float t) {
/* 176 */     t = Math.max(0.0F, Math.min(1.0F, t));
/*     */     
/* 178 */     int af = from >>> 24 & 0xFF;
/* 179 */     int rf = from >>> 16 & 0xFF;
/* 180 */     int gf = from >>> 8 & 0xFF;
/* 181 */     int bf = from & 0xFF;
/*     */     
/* 183 */     int at = to >>> 24 & 0xFF;
/* 184 */     int rt = to >>> 16 & 0xFF;
/* 185 */     int gt = to >>> 8 & 0xFF;
/* 186 */     int bt = to & 0xFF;
/*     */     
/* 188 */     int a = (int)(af + (at - af) * t);
/* 189 */     int r = (int)(rf + (rt - rf) * t);
/* 190 */     int g = (int)(gf + (gt - gf) * t);
/* 191 */     int b = (int)(bf + (bt - bf) * t);
/*     */     
/* 193 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   public static float renderedTextHeight(FontRenderer font, String text) {
/* 197 */     return font.getStringHeight((text == null || text.isEmpty()) ? "I" : text) / 2.0F;
/*     */   }
/*     */   
/*     */   public static float centerTextY(FontRenderer font, String text, float boxY, float boxHeight) {
/* 201 */     return boxY + (boxHeight - renderedTextHeight(font, text)) / 2.0F + 3.0F;
/*     */   }
/*     */   
/*     */   public static float centerTextX(FontRenderer font, String text, float boxX, float boxWidth) {
/* 205 */     return boxX + (boxWidth - font.getStringWidth(text)) / 2.0F;
/*     */   }
/*     */   
/*     */   public static float centerMsdfTextX(MsdfFont font, String text, float size, float boxX, float boxWidth) {
/* 209 */     return boxX + (boxWidth - font.getWidth(text, size)) / 2.0F;
/*     */   }
/*     */   
/*     */   public static float centerMsdfTextY(float size, float boxY, float boxHeight) {
/* 213 */     return boxY + (boxHeight - size) / 2.0F + 1.15F;
/*     */   }
/*     */   
/*     */   public static float centerMsdfTextY(float size, float boxY, float boxHeight, float opticalOffset) {
/* 217 */     return centerMsdfTextY(size, boxY, boxHeight) + opticalOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float alignIconToMsdfTextY(float textY, float textSize, float iconSize) {
/* 222 */     return textY + (textSize - iconSize) / 2.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\MenuStyle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */