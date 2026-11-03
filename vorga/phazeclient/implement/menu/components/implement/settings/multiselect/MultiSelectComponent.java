/*     */ package vorga.phazeclient.implement.menu.components.implement.settings.multiselect;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
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
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.implement.settings.AbstractSettingComponent;
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
/*     */ 
/*     */ 
/*     */ public class MultiSelectComponent
/*     */   extends AbstractSettingComponent
/*     */ {
/*     */   private static final float CHIP_TEXT_SIZE = 5.7F;
/*     */   private static final float CHIP_PADDING_X = 6.0F;
/*     */   private static final float CHIP_HEIGHT = 13.0F;
/*     */   private static final float CHIP_GAP_X = 4.0F;
/*     */   private static final float CHIP_GAP_Y = 4.0F;
/*     */   private static final float CHIPS_LEFT_PAD = 10.0F;
/*     */   private static final float CHIPS_RIGHT_PAD = 18.0F;
/*     */   private static final float LABEL_TO_CHIPS_GAP = 4.0F;
/*     */   private static final float LABEL_TOP_PAD = 7.0F;
/*     */   private static final float LABEL_TEXT_SIZE = 5.7F;
/*     */   private final MultiSelectSetting setting;
/*  97 */   private final Map<String, ChipAnimations> chipAnimations = new IdentityHashMap<>();
/*     */   
/*     */   private float expandedHeight;
/*     */ 
/*     */   
/*     */   public MultiSelectComponent(MultiSelectSetting setting) {
/* 103 */     super((Setting)setting);
/* 104 */     this.setting = setting;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void handleGlobalClick(double mouseX, double mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeAllDropdowns() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 128 */     updateVisibilityAnimation();
/*     */     
/* 130 */     class_4587 matrices = context.method_51448();
/* 131 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     ChipLayout layout = computeChipLayout();
/* 139 */     float labelBandHeight = 16.7F;
/* 140 */     float chipBlockHeight = layout.totalRows() * 13.0F + Math.max(0, layout.totalRows() - 1) * 4.0F;
/* 141 */     float computed = labelBandHeight + chipBlockHeight + 6.0F;
/* 142 */     this.height = (int)Math.ceil(computed);
/* 143 */     this.expandedHeight = this.height;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     renderSettingCard(context, 0.0F, 0.0F);
/*     */ 
/*     */     
/* 151 */     float labelX = this.x + 10.0F;
/* 152 */     float labelY = this.y + 7.0F;
/* 153 */     this.label.renderMsdf(matrices, this.setting.getLocalizedName(), 5.7F, labelX, labelY, this.x + this.width - 18.0F, this.y, labelBandHeight - 4.0F, 
/*     */         
/* 155 */         MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, this.currentAlpha));
/* 156 */     updateLabelHover(mouseX, mouseY);
/*     */ 
/*     */     
/* 159 */     renderChips(context, matrices, positionMatrix, layout, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderChips(class_332 context, class_4587 matrices, Matrix4f positionMatrix, ChipLayout layout, int mouseX, int mouseY) {
/* 169 */     for (int i = 0; i < layout.entries.size(); i++) {
/* 170 */       ChipEntry entry = layout.entries.get(i);
/* 171 */       renderChip(matrices, positionMatrix, entry, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderChip(class_4587 matrices, Matrix4f positionMatrix, ChipEntry entry, int mouseX, int mouseY) {
/* 176 */     boolean selected = this.setting.getSelected().contains(entry.name);
/* 177 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, entry.x, entry.y, entry.width, 13.0D);
/* 178 */     if (hovered) {
/* 179 */       CursorManager.requestHand();
/*     */     }
/*     */     
/* 182 */     ChipAnimations anim = this.chipAnimations.computeIfAbsent(entry.name, n -> new ChipAnimations());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (!anim.seeded) {
/* 192 */       anim.selected.setDirectionAndFinish(selected ? Direction.FORWARDS : Direction.BACKWARDS);
/* 193 */       anim.seeded = true;
/*     */     } else {
/* 195 */       anim.selected.setDirection(selected ? Direction.FORWARDS : Direction.BACKWARDS);
/*     */     } 
/* 197 */     float selP = anim.selected.getOutputFloat();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     int idle = MenuStyle.PANEL_CHIP;
/* 204 */     int activeFill = MenuStyle.mix(MenuStyle.CHIP_ACTIVE, MenuStyle.PANEL_CHIP, 0.35F);
/* 205 */     int fill = MenuStyle.mix(idle, activeFill, selP);
/* 206 */     int fillFinal = MenuStyle.withAlpha(fill, this.currentAlpha * (0.55F + 0.45F * selP));
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
/* 218 */     int outlineIdle = MenuStyle.mix(MenuStyle.BORDER, MenuStyle.BORDER_LIGHT, 0.4F);
/* 219 */     int outlineActive = MenuStyle.mix(MenuStyle.CHIP_ACTIVE, MenuStyle.TEXT_PRIMARY, 0.45F);
/* 220 */     int outline = MenuStyle.mix(outlineIdle, outlineActive, selP);
/* 221 */     int outlineFinal = MenuStyle.withAlpha(outline, this.currentAlpha);
/*     */     
/* 223 */     rectangle.render(ShapeProperties.create(matrices, entry.x, entry.y, entry.width, 13.0D)
/* 224 */         .round(2.5F)
/* 225 */         .thickness(1.5F)
/* 226 */         .softness(0.6F)
/* 227 */         .color(fillFinal)
/* 228 */         .outlineColor(outlineFinal)
/* 229 */         .build());
/*     */     
/* 231 */     int textColor = MenuStyle.mix(MenuStyle.TEXT_MUTED, MenuStyle.TEXT_PRIMARY, selP);
/* 232 */     int textFinal = MenuStyle.withAlpha(textColor, this.currentAlpha);
/*     */     
/* 234 */     MsdfFont font = MsdfFonts.bold();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     String chipLabel = Lang.translate(entry.name);
/* 243 */     float textW = font.getWidth(chipLabel, 5.7F);
/* 244 */     float textX = entry.x + (entry.width - textW) / 2.0F;
/* 245 */     float textY = MenuStyle.centerMsdfTextY(5.7F, entry.y, 13.0F);
/* 246 */     MsdfRenderer.renderText(font, chipLabel, 5.7F, textFinal, positionMatrix, textX, textY, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChipLayout computeChipLayout() {
/* 257 */     List<ChipEntry> out = new ArrayList<>();
/* 258 */     List<String> options = this.setting.getList();
/* 259 */     if (options == null || options.isEmpty()) {
/* 260 */       return new ChipLayout(out, 1);
/*     */     }
/*     */     
/* 263 */     MsdfFont font = MsdfFonts.bold();
/* 264 */     float availableWidth = Math.max(20.0F, this.width - 10.0F - 18.0F);
/* 265 */     float baseX = this.x + 10.0F;
/* 266 */     float baseY = this.y + 7.0F + 5.7F + 4.0F;
/*     */     
/* 268 */     float cursorX = 0.0F;
/* 269 */     float cursorY = 0.0F;
/* 270 */     int rows = 1;
/*     */     
/* 272 */     for (String option : options) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 277 */       float labelW = font.getWidth(Lang.translate(option), 5.7F);
/* 278 */       float chipW = Math.max(20.0F, labelW + 12.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       if (cursorX > 0.0F && cursorX + chipW > availableWidth) {
/* 286 */         cursorX = 0.0F;
/* 287 */         cursorY += 17.0F;
/* 288 */         rows++;
/*     */       } 
/*     */       
/* 291 */       out.add(new ChipEntry(option, baseX + cursorX, baseY + cursorY, chipW));
/* 292 */       cursorX += chipW + 4.0F;
/*     */     } 
/*     */     
/* 295 */     return new ChipLayout(out, rows);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 300 */     if (button != 0) {
/* 301 */       return super.mouseClicked(mouseX, mouseY, button);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 306 */     ChipLayout layout = computeChipLayout();
/* 307 */     for (ChipEntry entry : layout.entries) {
/* 308 */       if (MathUtil.isHovered(mouseX, mouseY, entry.x, entry.y, entry.width, 13.0D)) {
/* 309 */         playButtonClickSound();
/* 310 */         List<String> selected = new ArrayList<>(this.setting.getSelected());
/* 311 */         if (selected.contains(entry.name)) {
/* 312 */           selected.remove(entry.name);
/*     */         } else {
/* 314 */           selected.add(entry.name);
/* 315 */           Objects.requireNonNull(this.setting.getList()); selected.sort(Comparator.comparingInt(this.setting.getList()::indexOf));
/*     */         } 
/* 317 */         this.setting.setSelected(selected);
/* 318 */         return true;
/*     */       } 
/*     */     } 
/* 321 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHover(double mouseX, double mouseY) {
/* 326 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getExpandedHeight() {
/* 331 */     return (this.expandedHeight > 0.0F) ? this.expandedHeight : this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ChipAnimations
/*     */   {
/* 342 */     final Animation selected = (new DecelerateAnimation()).setMs(220).setValue(1.0D);
/*     */ 
/*     */     
/*     */     boolean seeded = false;
/*     */ 
/*     */ 
/*     */     
/*     */     ChipAnimations() {
/* 350 */       this.selected.setDirection(Direction.BACKWARDS);
/*     */     } }
/*     */   private static final class ChipEntry extends Record { private final String name; private final float x; private final float y;
/*     */     private final float width;
/*     */     
/* 355 */     private ChipEntry(String name, float x, float y, float width) { this.name = name; this.x = x; this.y = y; this.width = width; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #355	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 355 */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipEntry; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #355	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #355	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipEntry;
/* 355 */       //   0	8	1	o	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; } public float width() { return this.width; }
/*     */      }
/*     */   private static final class ChipLayout extends Record { private final List<MultiSelectComponent.ChipEntry> entries; private final int totalRows;
/* 358 */     private ChipLayout(List<MultiSelectComponent.ChipEntry> entries, int totalRows) { this.entries = entries; this.totalRows = totalRows; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipLayout;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #358	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipLayout; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipLayout;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #358	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipLayout; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipLayout;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #358	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/menu/components/implement/settings/multiselect/MultiSelectComponent$ChipLayout;
/* 358 */       //   0	8	1	o	Ljava/lang/Object; } public List<MultiSelectComponent.ChipEntry> entries() { return this.entries; } public int totalRows() { return this.totalRows; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\multiselect\MultiSelectComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */