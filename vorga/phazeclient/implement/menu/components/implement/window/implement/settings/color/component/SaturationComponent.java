/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.class_10142;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ import vorga.phazeclient.api.system.render.TextureFilter;
/*     */ 
/*     */ public class SaturationComponent extends AbstractComponent {
/*     */   public SaturationComponent(ColorSetting setting) {
/*  24 */     this.setting = setting;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final float INDICATOR_SIZE = 6.0F;
/*     */   
/*     */   private final ColorSetting setting;
/*     */   
/*     */   private boolean saturationDragging;
/*     */   
/*     */   private float X;
/*     */   
/*     */   private float Y;
/*     */   private float W;
/*     */   private float H;
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  41 */     class_4587 matrix = context.method_51448();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     this.X = this.x + 148.0F;
/*  48 */     this.Y = this.y + 10.5F;
/*  49 */     this.W = 6.0F;
/*  50 */     this.H = 50.0F;
/*  51 */     boolean hoveredStrip = MathUtil.isHovered(mouseX, mouseY, this.X, this.Y, this.W, this.H);
/*  52 */     if (this.saturationDragging || hoveredStrip) {
/*  53 */       CursorManager.requestVerticalResize();
/*     */     }
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
/*  67 */     renderVerticalGradientStrip(matrix, "textures/color_picker/hue.png", this.X, this.Y, this.W, this.H, applyGlobalAlpha(-1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     float apexY = class_3532.method_15363(this.Y + this.H * this.setting.getHue(), this.Y, this.Y + this.H);
/*  78 */     float triX = this.X + this.W;
/*  79 */     float triY = apexY - 3.0F;
/*  80 */     renderLeftPointingTriangle(matrix, "textures/color_picker/triangle.png", triX, triY, 6.0F, 6.0F, applyGlobalAlpha(-1));
/*     */     
/*  82 */     if (this.saturationDragging) {
/*  83 */       this.setting.setHue(class_3532.method_15363((mouseY - this.Y) / this.H, 0.0F, 1.0F));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  90 */     this.saturationDragging = (button == 0 && MathUtil.isHovered(mouseX, mouseY, this.X, this.Y, this.W, this.H));
/*  91 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/*  96 */     this.saturationDragging = false;
/*  97 */     return super.mouseReleased(mouseX, mouseY, button);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderVerticalGradientStrip(class_4587 matrix, String texture, float x, float y, float w, float h, int color) {
/* 118 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 120 */     RenderSystem.enableBlend();
/* 121 */     RenderSystem.defaultBlendFunc();
/* 122 */     RenderSystem.setShaderTexture(0, class_2960.method_60654(texture));
/* 123 */     RenderSystem.setShader(class_10142.field_53880);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     TextureFilter.setMinMag(class_2960.method_60654(texture), 9729);
/*     */     
/* 131 */     Matrix4f mat = matrix.method_23760().method_23761();
/* 132 */     class_287 buf = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
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
/* 144 */     buf.method_22918(mat, x, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 145 */     buf.method_22918(mat, x, y + h, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 146 */     buf.method_22918(mat, x + w, y + h, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 147 */     buf.method_22918(mat, x + w, y, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/*     */     
/* 149 */     class_286.method_43433(buf.method_60800());
/* 150 */     RenderSystem.disableBlend();
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
/*     */   private static void renderLeftPointingTriangle(class_4587 matrix, String texture, float x, float y, float w, float h, int color) {
/* 175 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 177 */     RenderSystem.enableBlend();
/* 178 */     RenderSystem.defaultBlendFunc();
/* 179 */     RenderSystem.setShaderTexture(0, class_2960.method_60654(texture));
/* 180 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 182 */     TextureFilter.setMinMag(class_2960.method_60654(texture), 9729);
/*     */     
/* 185 */     Matrix4f mat = matrix.method_23760().method_23761();
/* 186 */     class_287 buf = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/*     */     
/* 188 */     buf.method_22918(mat, x, y, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 189 */     buf.method_22918(mat, x, y + h, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 190 */     buf.method_22918(mat, x + w, y + h, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 191 */     buf.method_22918(mat, x + w, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/*     */     
/* 193 */     class_286.method_43433(buf.method_60800());
/* 194 */     RenderSystem.disableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\component\SaturationComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */