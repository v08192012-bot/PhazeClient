/*   */ package vorga.phazeclient.implement.menu;
/*   */ public final class MenuPalette extends Record { private final String name; private final int panelBg; private final int panelBgSoft; private final int panelHeader; private final int panelSidebar; private final int panelContent; private final int panelChip; private final int panelRow; private final int panelRowActive; private final int cardBg;
/* 3 */   public MenuPalette(String name, int panelBg, int panelBgSoft, int panelHeader, int panelSidebar, int panelContent, int panelChip, int panelRow, int panelRowActive, int cardBg, int cardInner, int cardOptions, int cardDisabled, int border, int borderLight, int textPrimary, int textMuted, int chipActive, int chipNew, int accentGreen) { this.name = name; this.panelBg = panelBg; this.panelBgSoft = panelBgSoft; this.panelHeader = panelHeader; this.panelSidebar = panelSidebar; this.panelContent = panelContent; this.panelChip = panelChip; this.panelRow = panelRow; this.panelRowActive = panelRowActive; this.cardBg = cardBg; this.cardInner = cardInner; this.cardOptions = cardOptions; this.cardDisabled = cardDisabled; this.border = border; this.borderLight = borderLight; this.textPrimary = textPrimary; this.textMuted = textMuted; this.chipActive = chipActive; this.chipNew = chipNew; this.accentGreen = accentGreen; } private final int cardInner; private final int cardOptions; private final int cardDisabled; private final int border; private final int borderLight; private final int textPrimary; private final int textMuted; private final int chipActive; private final int chipNew; private final int accentGreen; public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/MenuPalette;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lvorga/phazeclient/implement/menu/MenuPalette; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/MenuPalette;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lvorga/phazeclient/implement/menu/MenuPalette; } public final boolean equals(Object o) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/MenuPalette;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lvorga/phazeclient/implement/menu/MenuPalette;
/* 3 */     //   0	8	1	o	Ljava/lang/Object; } public String name() { return this.name; } public int panelBg() { return this.panelBg; } public int panelBgSoft() { return this.panelBgSoft; } public int panelHeader() { return this.panelHeader; } public int panelSidebar() { return this.panelSidebar; } public int panelContent() { return this.panelContent; } public int panelChip() { return this.panelChip; } public int panelRow() { return this.panelRow; } public int panelRowActive() { return this.panelRowActive; } public int cardBg() { return this.cardBg; } public int cardInner() { return this.cardInner; } public int cardOptions() { return this.cardOptions; } public int cardDisabled() { return this.cardDisabled; } public int border() { return this.border; } public int borderLight() { return this.borderLight; } public int textPrimary() { return this.textPrimary; } public int textMuted() { return this.textMuted; } public int chipActive() { return this.chipActive; } public int chipNew() { return this.chipNew; } public int accentGreen() { return this.accentGreen; }
/*   */    }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\MenuPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */