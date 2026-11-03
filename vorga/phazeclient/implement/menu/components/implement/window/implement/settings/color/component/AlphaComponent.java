/*     */ package vorga.phazeclient.implement.menu.components.implement.window.implement.settings.color.component;
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
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.api.system.shape.batched.BatchedRectangle;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ 
/*     */ public class AlphaComponent extends AbstractComponent {
/*     */   public AlphaComponent(ColorSetting setting) {
/*  24 */     this.setting = setting;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final float INDICATOR_SIZE = 6.0F;
/*     */   
/*     */   private final ColorSetting setting;
/*     */   
/*     */   private boolean alphaDragging;
/*     */   
/*     */   private float X;
/*     */   private float Y;
/*     */   private float W;
/*     */   private float H;
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  40 */     class_4587 matrix = context.method_51448();
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
/*  51 */     this.X = this.x + 162.0F;
/*  52 */     this.Y = this.y + 10.5F;
/*  53 */     this.W = 6.0F;
/*  54 */     this.H = 50.0F;
/*  55 */     boolean hoveredStrip = MathUtil.isHovered(mouseX, mouseY, this.X, this.Y, this.W, this.H);
/*  56 */     if (this.alphaDragging || hoveredStrip) {
/*  57 */       CursorManager.requestVerticalResize();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     renderVerticalGradientStrip(matrix, "textures/color_picker/alpha.png", this.X, this.Y, this.W, this.H, applyGlobalAlpha(-1));
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
/*  79 */     int gradColorWithAlpha = applyGlobalAlpha(this.setting.getColorWithAlpha());
/*  80 */     int transparent = applyGlobalAlpha(-2147483648);
/*  81 */     rectangle.render(ShapeProperties.create(matrix, this.X, this.Y - 0.2D, this.W, this.H + 0.5D)
/*  82 */         .round(1.5F)
/*  83 */         .color(new int[] { transparent, gradColorWithAlpha, transparent, gradColorWithAlpha
/*  84 */           }).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     float apexY = class_3532.method_15363(this.Y + this.H * (1.0F - this.setting.getAlpha()), this.Y, this.Y + this.H);
/*  93 */     float triX = this.X + this.W;
/*  94 */     float triY = apexY - 3.0F;
/*  95 */     renderLeftPointingTriangle(matrix, "textures/color_picker/triangle.png", triX, triY, 6.0F, 6.0F, applyGlobalAlpha(-1));
/*     */     
/*  97 */     if (this.alphaDragging)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       this.setting.setAlpha(class_3532.method_15363(1.0F - (mouseY - this.Y) / this.H, 0.0F, 1.0F));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 109 */     this.alphaDragging = (button == 0 && MathUtil.isHovered(mouseX, mouseY, this.X, this.Y, this.W, this.H));
/* 110 */     return super.mouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double mouseX, double mouseY, int button) {
/* 115 */     this.alphaDragging = false;
/* 116 */     return super.mouseReleased(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderVerticalGradientStrip(class_4587 matrix, String texture, float x, float y, float w, float h, int color) {
/* 127 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 129 */     RenderSystem.enableBlend();
/* 130 */     RenderSystem.defaultBlendFunc();
/* 131 */     RenderSystem.setShaderTexture(0, class_2960.method_60654(texture));
/* 132 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 134 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 135 */     GL11.glTexParameteri(3553, 10240, 9729);
/*     */     
/* 137 */     Matrix4f mat = matrix.method_23760().method_23761();
/* 138 */     class_287 buf = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/*     */     
/* 140 */     buf.method_22918(mat, x, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/* 141 */     buf.method_22918(mat, x, y + h, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 142 */     buf.method_22918(mat, x + w, y + h, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 143 */     buf.method_22918(mat, x + w, y, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/*     */     
/* 145 */     class_286.method_43433(buf.method_60800());
/* 146 */     RenderSystem.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderLeftPointingTriangle(class_4587 matrix, String texture, float x, float y, float w, float h, int color) {
/* 157 */     BatchedRectangle.flushIfBatching();
/*     */     
/* 159 */     RenderSystem.enableBlend();
/* 160 */     RenderSystem.defaultBlendFunc();
/* 161 */     RenderSystem.setShaderTexture(0, class_2960.method_60654(texture));
/* 162 */     RenderSystem.setShader(class_10142.field_53880);
/*     */     
/* 164 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 165 */     GL11.glTexParameteri(3553, 10240, 9729);
/*     */     
/* 167 */     Matrix4f mat = matrix.method_23760().method_23761();
/* 168 */     class_287 buf = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/*     */     
/* 170 */     buf.method_22918(mat, x, y, 0.0F).method_22913(0.0F, 1.0F).method_39415(color);
/* 171 */     buf.method_22918(mat, x, y + h, 0.0F).method_22913(1.0F, 1.0F).method_39415(color);
/* 172 */     buf.method_22918(mat, x + w, y + h, 0.0F).method_22913(1.0F, 0.0F).method_39415(color);
/* 173 */     buf.method_22918(mat, x + w, y, 0.0F).method_22913(0.0F, 0.0F).method_39415(color);
/*     */     
/* 175 */     class_286.method_43433(buf.method_60800());
/* 176 */     RenderSystem.disableBlend();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\window\implement\settings\color\component\AlphaComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */