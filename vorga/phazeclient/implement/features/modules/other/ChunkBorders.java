/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_746;
/*     */ import org.joml.Matrix4f;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public class ChunkBorders extends Module {
/*  19 */   private static final ChunkBorders INSTANCE = new ChunkBorders();
/*     */ 
/*     */   
/*  22 */   public final ValueSetting gridSize = (new ValueSetting("Grid Size", "Size of grid cells in blocks (1x1 to 5x5)"))
/*  23 */     .range(1.0F, 5.0F).step(1.0F).setValue(1.0F);
/*  24 */   public final ValueSetting gridThickness = (new ValueSetting("Line Thickness", "Thickness of chunk grid lines"))
/*  25 */     .range(1.0F, 5.0F).step(1.0F).setValue(1.0F);
/*  26 */   public final ColorSetting gridColor = (new ColorSetting("Grid Color", "Color of the chunk grid lines"))
/*  27 */     .value(-256);
/*     */   
/*  29 */   public final SectionSetting gridGroup = new SectionSetting("Grid");
/*  30 */   public final BooleanSetting gridEnabled = (new BooleanSetting("Grid", "Enable chunk grid lines")).setValue(true);
/*     */ 
/*     */   
/*  33 */   public final ValueSetting innerThickness = (new ValueSetting("Line Thickness", "Thickness of inner chunk corner lines"))
/*  34 */     .range(1.0F, 5.0F).step(1.0F).setValue(1.0F);
/*  35 */   public final ColorSetting innerColor = (new ColorSetting("Inner Chunk Corner Color", "Color of inner chunk corners and sub-chunk frames"))
/*  36 */     .value(-16776961);
/*     */   
/*  38 */   public final SectionSetting innerCornersGroup = new SectionSetting("Inner Corners");
/*  39 */   public final BooleanSetting innerEnabled = (new BooleanSetting("Inner Corners", "Enable inner chunk corners")).setValue(true);
/*     */ 
/*     */   
/*  42 */   public final ValueSetting outerThickness = (new ValueSetting("Line Thickness", "Thickness of outer chunk corner lines"))
/*  43 */     .range(1.0F, 5.0F).step(1.0F).setValue(5.0F);
/*  44 */   public final ColorSetting outerColor = (new ColorSetting("Outer Chunk Corner Color", "Color of outer neighboring chunk corners"))
/*  45 */     .value(-65536);
/*     */   
/*  47 */   public final SectionSetting outerCornersGroup = new SectionSetting("Outer Corners");
/*  48 */   public final BooleanSetting outerEnabled = (new BooleanSetting("Outer Corners", "Enable outer chunk corners")).setValue(true);
/*     */   
/*     */   private ChunkBorders() {
/*  51 */     super("chunk_borders", "Toggle Chunk Borders", ModuleCategory.OTHER);
/*  52 */     this.innerColor.setFullWidth(true);
/*  53 */     this.outerColor.setFullWidth(true);
/*  54 */     setup(new Setting[] { (Setting)this.gridGroup, (Setting)this.gridEnabled, (Setting)this.gridSize, (Setting)this.gridThickness, (Setting)this.gridColor, (Setting)this.innerCornersGroup, (Setting)this.innerEnabled, (Setting)this.innerThickness, (Setting)this.innerColor, (Setting)this.outerCornersGroup, (Setting)this.outerEnabled, (Setting)this.outerThickness, (Setting)this.outerColor });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChunkBorders getInstance() {
/*  62 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  67 */     return "Customizable Lunar-style chunk boundaries with grid, inner corners, and outer corners";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  72 */     return "block_overlay.png";
/*     */   }
/*     */   
/*     */   public void renderCustomChunkBorders(class_4587 matrices, class_4597 vertexConsumers, double cameraX, double cameraY, double cameraZ) {
/*  76 */     class_310 client = class_310.method_1551();
/*  77 */     if (client.field_1687 == null || client.field_1724 == null)
/*     */       return;  try {
/*     */       class_746 class_746;
/*  80 */       class_1297 cameraEntity = client.method_1560();
/*  81 */       if (cameraEntity == null) class_746 = client.field_1724;
/*     */       
/*  83 */       float minY = (float)(client.field_1687.method_31607() - cameraY);
/*  84 */       float maxY = (float)((client.field_1687.method_31600() + 1) - cameraY);
/*     */       
/*  86 */       class_1923 chunkPos = class_746.method_31476();
/*  87 */       float minBlockX = (float)(chunkPos.method_8326() - cameraX);
/*  88 */       float minBlockZ = (float)(chunkPos.method_8328() - cameraZ);
/*     */       
/*  90 */       Matrix4f posMatrix = matrices.method_23760().method_23761();
/*     */ 
/*     */       
/*  93 */       if (this.outerEnabled.isValue()) {
/*  94 */         double thick = this.outerThickness.getValue();
/*  95 */         class_4588 buf = vertexConsumers.getBuffer(class_1921.method_49043(thick));
/*     */         
/*  97 */         int col = this.outerColor.getColor();
/*  98 */         float r = (col >> 16 & 0xFF) / 255.0F;
/*  99 */         float g = (col >> 8 & 0xFF) / 255.0F;
/* 100 */         float b = (col & 0xFF) / 255.0F;
/* 101 */         float a = (col >> 24 & 0xFF) / 255.0F;
/* 102 */         if (a <= 0.01F) a = 1.0F; 
/* 103 */         float halfA = a * 0.5F;
/*     */         
/* 105 */         for (int x = -16; x <= 32; x += 16) {
/* 106 */           for (int z = -16; z <= 32; z += 16) {
/* 107 */             if (!this.innerEnabled.isValue() || (x != 0 && x != 16) || (z != 0 && z != 16)) {
/*     */ 
/*     */               
/* 110 */               buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/* 111 */               buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ + z).method_22915(r, g, b, halfA);
/* 112 */               buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ + z).method_22915(r, g, b, halfA);
/* 113 */               buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 119 */       if (this.gridEnabled.isValue()) {
/* 120 */         double thick = this.gridThickness.getValue();
/* 121 */         class_4588 buf = vertexConsumers.getBuffer(class_1921.method_49043(thick));
/*     */         
/* 123 */         int col = this.gridColor.getColor();
/* 124 */         float r = (col >> 16 & 0xFF) / 255.0F;
/* 125 */         float g = (col >> 8 & 0xFF) / 255.0F;
/* 126 */         float b = (col & 0xFF) / 255.0F;
/* 127 */         float a = (col >> 24 & 0xFF) / 255.0F;
/* 128 */         if (a <= 0.01F) a = 1.0F;
/*     */         
/* 130 */         int step = Math.max(1, (int)this.gridSize.getValue());
/*     */         
/*     */         int x;
/* 133 */         for (x = step; x < 16; x += step) {
/* 134 */           buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ).method_22915(r, g, b, 0.0F);
/* 135 */           buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ).method_22915(r, g, b, a);
/* 136 */           buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ).method_22915(r, g, b, a);
/* 137 */           buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ).method_22915(r, g, b, 0.0F);
/*     */           
/* 139 */           buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ + 16.0F).method_22915(r, g, b, 0.0F);
/* 140 */           buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ + 16.0F).method_22915(r, g, b, a);
/* 141 */           buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ + 16.0F).method_22915(r, g, b, a);
/* 142 */           buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ + 16.0F).method_22915(r, g, b, 0.0F);
/*     */         } 
/*     */         
/*     */         int z;
/* 146 */         for (z = step; z < 16; z += step) {
/* 147 */           buf.method_22918(posMatrix, minBlockX, minY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/* 148 */           buf.method_22918(posMatrix, minBlockX, minY, minBlockZ + z).method_22915(r, g, b, a);
/* 149 */           buf.method_22918(posMatrix, minBlockX, maxY, minBlockZ + z).method_22915(r, g, b, a);
/* 150 */           buf.method_22918(posMatrix, minBlockX, maxY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/*     */           
/* 152 */           buf.method_22918(posMatrix, minBlockX + 16.0F, minY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/* 153 */           buf.method_22918(posMatrix, minBlockX + 16.0F, minY, minBlockZ + z).method_22915(r, g, b, a);
/* 154 */           buf.method_22918(posMatrix, minBlockX + 16.0F, maxY, minBlockZ + z).method_22915(r, g, b, a);
/* 155 */           buf.method_22918(posMatrix, minBlockX + 16.0F, maxY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/*     */         } 
/*     */ 
/*     */         
/* 159 */         int bottomY = client.field_1687.method_31607();
/* 160 */         int topY = client.field_1687.method_31600() + 1; int y;
/* 161 */         for (y = bottomY; y <= topY; y += step) {
/* 162 */           float relY = (float)(y - cameraY);
/* 163 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, 0.0F);
/* 164 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, a);
/* 165 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ + 16.0F).method_22915(r, g, b, a);
/* 166 */           buf.method_22918(posMatrix, minBlockX + 16.0F, relY, minBlockZ + 16.0F).method_22915(r, g, b, a);
/* 167 */           buf.method_22918(posMatrix, minBlockX + 16.0F, relY, minBlockZ).method_22915(r, g, b, a);
/* 168 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, a);
/* 169 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, 0.0F);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 174 */       if (this.innerEnabled.isValue()) {
/* 175 */         double thick = this.innerThickness.getValue();
/* 176 */         class_4588 buf = vertexConsumers.getBuffer(class_1921.method_49043(thick));
/*     */         
/* 178 */         int col = this.innerColor.getColor();
/* 179 */         float r = (col >> 16 & 0xFF) / 255.0F;
/* 180 */         float g = (col >> 8 & 0xFF) / 255.0F;
/* 181 */         float b = (col & 0xFF) / 255.0F;
/* 182 */         float a = (col >> 24 & 0xFF) / 255.0F;
/* 183 */         if (a <= 0.01F) a = 1.0F;
/*     */ 
/*     */         
/* 186 */         for (int x = 0; x <= 16; x += 16) {
/* 187 */           for (int z = 0; z <= 16; z += 16) {
/* 188 */             buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/* 189 */             buf.method_22918(posMatrix, minBlockX + x, minY, minBlockZ + z).method_22915(r, g, b, a);
/* 190 */             buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ + z).method_22915(r, g, b, a);
/* 191 */             buf.method_22918(posMatrix, minBlockX + x, maxY, minBlockZ + z).method_22915(r, g, b, 0.0F);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 196 */         int bottomY = client.field_1687.method_31607();
/* 197 */         int topY = client.field_1687.method_31600() + 1;
/* 198 */         for (int y = bottomY; y <= topY; y += 16) {
/* 199 */           float relY = (float)(y - cameraY);
/* 200 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, 0.0F);
/* 201 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, a);
/* 202 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ + 16.0F).method_22915(r, g, b, a);
/* 203 */           buf.method_22918(posMatrix, minBlockX + 16.0F, relY, minBlockZ + 16.0F).method_22915(r, g, b, a);
/* 204 */           buf.method_22918(posMatrix, minBlockX + 16.0F, relY, minBlockZ).method_22915(r, g, b, a);
/* 205 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, a);
/* 206 */           buf.method_22918(posMatrix, minBlockX, relY, minBlockZ).method_22915(r, g, b, 0.0F);
/*     */         } 
/*     */       } 
/* 209 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ChunkBorders.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */