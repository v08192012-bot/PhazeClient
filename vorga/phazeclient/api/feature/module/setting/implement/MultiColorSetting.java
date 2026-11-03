/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ public class MultiColorSetting extends Setting {
/*    */   private final List<ColorSetting> colors;
/*    */   
/*    */   public void setSelectedColorIndex(int selectedColorIndex) {
/* 12 */     this.selectedColorIndex = selectedColorIndex;
/*    */   } public List<ColorSetting> getColors() {
/* 14 */     return this.colors;
/* 15 */   } private int selectedColorIndex = 0; public int getSelectedColorIndex() { return this.selectedColorIndex; }
/*    */   
/*    */   public MultiColorSetting(String name, String description) {
/* 18 */     super(name, description);
/* 19 */     this.colors = new ArrayList<>();
/*    */   }
/*    */   
/*    */   public MultiColorSetting colors(String... colorNames) {
/* 23 */     this.colors.clear();
/* 24 */     for (int i = 0; i < colorNames.length; i++) {
/* 25 */       this.colors.add(new ColorSetting(colorNames[i], "Color " + i + 1 + " for " + getName()));
/*    */     }
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   public MultiColorSetting defaultColors(int... defaultColors) {
/* 31 */     for (int i = 0; i < Math.min(this.colors.size(), defaultColors.length); i++) {
/* 32 */       ((ColorSetting)this.colors.get(i)).setColor(defaultColors[i]);
/*    */     }
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   public MultiColorSetting visible(Supplier<Boolean> visible) {
/* 38 */     setVisible(visible);
/* 39 */     for (ColorSetting color : this.colors) {
/* 40 */       color.setVisible(visible);
/*    */     }
/* 42 */     return this;
/*    */   }
/*    */   
/*    */   public ColorSetting getColor1() {
/* 46 */     return !this.colors.isEmpty() ? this.colors.getFirst() : null;
/*    */   }
/*    */   
/*    */   public ColorSetting getColor2() {
/* 50 */     return (this.colors.size() > 1) ? this.colors.get(1) : null;
/*    */   }
/*    */   
/*    */   public ColorSetting getColor3() {
/* 54 */     return (this.colors.size() > 2) ? this.colors.get(2) : null;
/*    */   }
/*    */   
/*    */   public ColorSetting getColor(int index) {
/* 58 */     if (index >= 0 && index < this.colors.size()) {
/* 59 */       return this.colors.get(index);
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public int[] getColorValues() {
/* 65 */     return this.colors.stream().mapToInt(ColorSetting::getColor).toArray();
/*    */   }
/*    */   
/*    */   public int getColorCount() {
/* 69 */     return this.colors.size();
/*    */   }
/*    */   
/*    */   public List<ColorSetting> getAllColors() {
/* 73 */     return new ArrayList<>(this.colors);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 78 */     for (ColorSetting color : this.colors) {
/* 79 */       if (color.isModified()) {
/* 80 */         return true;
/*    */       }
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 88 */     for (ColorSetting color : this.colors)
/* 89 */       color.reset(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\MultiColorSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */