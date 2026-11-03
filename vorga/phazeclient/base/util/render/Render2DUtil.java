/*     */ package vorga.phazeclient.base.util.render;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import lombok.NonNull;
/*     */ import net.minecraft.class_10142;
/*     */ import net.minecraft.class_1058;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_293;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import org.lwjgl.opengl.GL40C;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.color.ColorUtil;
/*     */ 
/*     */ public final class Render2DUtil implements QuickImports {
/*     */   private Render2DUtil() {
/*  24 */     throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
/*  25 */   } private static final List<Quad> QUAD = new ArrayList<>();
/*     */   
/*     */   public static void onRender(class_332 context) {
/*  28 */     class_4587 matrix = context.method_51448();
/*  29 */     Matrix4f matrix4f = matrix.method_23760().method_23761();
/*  30 */     if (!QUAD.isEmpty()) {
/*  31 */       RenderSystem.enableBlend();
/*  32 */       RenderSystem.defaultBlendFunc();
/*  33 */       RenderSystem.setShader(class_10142.field_53876);
/*  34 */       class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1576);
/*  35 */       QUAD.forEach(quad -> drawEngine.quad(matrix4f, buffer, quad.x, quad.y, quad.width, quad.height, quad.color));
/*  36 */       class_286.method_43433(buffer.method_60800());
/*  37 */       RenderSystem.disableBlend();
/*  38 */       QUAD.clear();
/*     */     } 
/*     */   }
/*     */   public static void defaultDrawStack(@NonNull class_332 context, @NonNull class_1799 stack, float x, float y, boolean rect, boolean drawItemInSlot, float scale) {
/*  42 */     if (context == null) throw new NullPointerException("context is marked non-null but is null");  if (stack == null) throw new NullPointerException("stack is marked non-null but is null"); 
/*  43 */     class_4587 matrix = context.method_51448();
/*  44 */     if (rect) Blur.INSTANCE.render(ShapeProperties.create(matrix, x, y, (16.0F * scale + 2.0F), (16.0F * scale + 2.0F))
/*  45 */           .round(2.0F).color(ColorUtil.HALF_BLACK).build()); 
/*  46 */     matrix.method_22903();
/*  47 */     matrix.method_46416(x + 1.0F, y + 1.0F, 0.0F);
/*  48 */     matrix.method_22905(scale, scale, 1.0F);
/*  49 */     context.method_51427(stack, 0, 0);
/*  50 */     class_310 client = class_310.method_1551();
/*  51 */     if (drawItemInSlot && client != null) context.method_51431(client.field_1772, stack, 0, 0); 
/*  52 */     matrix.method_22909();
/*     */   }
/*     */   public static void drawTexture(@NonNull class_332 context, class_2960 id, float x, float y, float size, float round, int uvSize, int regionSize, int textureSize, int backgroundColor) {
/*  55 */     if (context == null) throw new NullPointerException("context is marked non-null but is null"); 
/*  56 */     drawTexture(context, id, x, y, size, round, uvSize, regionSize, textureSize, backgroundColor, -1);
/*     */   }
/*     */   public static void drawTexture(@NonNull class_332 context, class_2960 id, float x, float y, float size, float round, int uvSize, int regionSize, int textureSize, int backgroundColor, int color) {
/*  59 */     if (context == null) throw new NullPointerException("context is marked non-null but is null"); 
/*  60 */     class_4587 matrix = context.method_51448();
/*  61 */     rectangle.render(ShapeProperties.create(matrix, x, y, size, size).round(round).color(backgroundColor).build());
/*     */     
/*  63 */     if (id != null) {
/*  64 */       matrix.method_22903();
/*  65 */       matrix.method_46416(x, y, 0.0F);
/*  66 */       matrix.method_22905(size, size, 1.0F);
/*     */       
/*  68 */       RenderSystem.enableBlend();
/*  69 */       RenderSystem.blendFunc(772, 773);
/*  70 */       drawTexture(matrix, id, 0, 0, 1.0F, 1.0F, uvSize, uvSize, regionSize, regionSize, textureSize, textureSize, color);
/*  71 */       RenderSystem.disableBlend();
/*     */       
/*  73 */       matrix.method_46416(-x, -y, 0.0F);
/*  74 */       matrix.method_22909();
/*     */     } 
/*     */   }
/*     */   public static void drawHead(@NonNull class_332 context, class_2960 id, float x, float y, float size, float round, int backgroundColor, int color) {
/*  78 */     if (context == null) throw new NullPointerException("context is marked non-null but is null"); 
/*  79 */     class_4587 matrix = context.method_51448();
/*  80 */     rectangle.render(ShapeProperties.create(matrix, x, y, size, size).round(round).color(backgroundColor).build());
/*     */     
/*  82 */     if (id != null) {
/*  83 */       matrix.method_22903();
/*  84 */       matrix.method_46416(x, y, 0.0F);
/*  85 */       matrix.method_22905(size, size, 1.0F);
/*     */       
/*  87 */       RenderSystem.setShaderTexture(0, id);
/*  88 */       RenderSystem.setShader(class_10142.field_53880);
/*  89 */       RenderSystem.enableBlend();
/*  90 */       RenderSystem.blendFunc(772, 773);
/*     */       
/*  92 */       GL40C.glTexParameteri(3553, 10241, 9728);
/*  93 */       GL40C.glTexParameteri(3553, 10240, 9728);
/*     */       
/*  95 */       class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/*  96 */       Matrix4f matrix4f = matrix.method_23760().method_23761();
/*     */       
/*  98 */       float u1_base = 0.125F;
/*  99 */       float u2_base = 0.25F;
/* 100 */       float v1_base = 0.125F;
/* 101 */       float v2_base = 0.25F;
/*     */       
/* 103 */       float u1_overlay = 0.625F;
/* 104 */       float u2_overlay = 0.75F;
/* 105 */       float v1_overlay = 0.125F;
/* 106 */       float v2_overlay = 0.25F;
/*     */       
/* 108 */       buffer.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_22913(u1_base, v1_base).method_39415(color);
/* 109 */       buffer.method_22918(matrix4f, 0.0F, 1.0F, 0.0F).method_22913(u1_base, v2_base).method_39415(color);
/* 110 */       buffer.method_22918(matrix4f, 1.0F, 1.0F, 0.0F).method_22913(u2_base, v2_base).method_39415(color);
/* 111 */       buffer.method_22918(matrix4f, 1.0F, 0.0F, 0.0F).method_22913(u2_base, v1_base).method_39415(color);
/*     */       
/* 113 */       buffer.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_22913(u1_overlay, v1_overlay).method_39415(color);
/* 114 */       buffer.method_22918(matrix4f, 0.0F, 1.0F, 0.0F).method_22913(u1_overlay, v2_overlay).method_39415(color);
/* 115 */       buffer.method_22918(matrix4f, 1.0F, 1.0F, 0.0F).method_22913(u2_overlay, v2_overlay).method_39415(color);
/* 116 */       buffer.method_22918(matrix4f, 1.0F, 0.0F, 0.0F).method_22913(u2_overlay, v1_overlay).method_39415(color);
/*     */       
/* 118 */       class_286.method_43433(buffer.method_60800());
/*     */       
/* 120 */       RenderSystem.disableBlend();
/*     */       
/* 122 */       matrix.method_46416(-x, -y, 0.0F);
/* 123 */       matrix.method_22909();
/*     */     } 
/*     */   }
/*     */   public static void drawSprite(@NonNull class_4587 matrix, @NonNull class_1058 sprite, float x, float y, float width, int height) {
/* 127 */     if (matrix == null) throw new NullPointerException("matrix is marked non-null but is null");  if (sprite == null) throw new NullPointerException("sprite is marked non-null but is null"); 
/* 128 */     drawSprite(matrix, sprite, x, y, width, height, -1);
/*     */   }
/*     */   public static void drawSprite(@NonNull class_4587 matrix, @NonNull class_1058 sprite, float x, float y, float width, int height, int color) {
/* 131 */     if (matrix == null) throw new NullPointerException("matrix is marked non-null but is null");  if (sprite == null) throw new NullPointerException("sprite is marked non-null but is null"); 
/* 132 */     if (width != 0.0F && height != 0)
/* 133 */       drawTexturedQuad(matrix, sprite.method_45852(), x, x + width, y, y + height, sprite.method_4594(), sprite.method_4577(), sprite.method_4593(), sprite.method_4575(), color); 
/*     */   }
/*     */   
/*     */   public static void drawTexture(@NonNull class_4587 matrix, @NonNull class_2960 texture, int x, int y, float width, float height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color) {
/* 137 */     if (matrix == null) throw new NullPointerException("matrix is marked non-null but is null");  if (texture == null) throw new NullPointerException("texture is marked non-null but is null"); 
/* 138 */     drawTexture(matrix, texture, x, x + width, y, y + height, 0.0F, regionWidth, regionHeight, u, v, textureWidth, textureHeight, color);
/*     */   }
/*     */   public static void drawTexture(@NonNull class_4587 matrix, @NonNull class_2960 texture, float x1, float x2, float y1, float y2, float z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight, int color) {
/* 141 */     if (matrix == null) throw new NullPointerException("matrix is marked non-null but is null");  if (texture == null) throw new NullPointerException("texture is marked non-null but is null"); 
/* 142 */     drawTexturedQuad(matrix, texture, x1, x2, y1, y2, (u + 0.0F) / textureWidth, (u + regionWidth) / textureWidth, (v + 0.0F) / textureHeight, (v + regionHeight) / textureHeight, color);
/*     */   }
/*     */   public static void drawTexturedQuad(@NonNull class_4587 matrix, @NonNull class_2960 texture, float x1, float x2, float y1, float y2, float u1, float u2, float v1, float v2, int color) {
/* 145 */     if (matrix == null) throw new NullPointerException("matrix is marked non-null but is null");  if (texture == null) throw new NullPointerException("texture is marked non-null but is null"); 
/* 146 */     RenderSystem.setShaderTexture(0, texture);
/* 147 */     RenderSystem.setShader(class_10142.field_53880);
/* 148 */     class_287 buffer = class_289.method_1348().method_60827(class_293.class_5596.field_27382, class_290.field_1575);
/* 149 */     Matrix4f matrix4f = matrix.method_23760().method_23761();
/* 150 */     buffer.method_22918(matrix4f, x1, y1, 0.0F).method_22913(u1, v1).method_39415(color);
/* 151 */     buffer.method_22918(matrix4f, x1, y2, 0.0F).method_22913(u1, v2).method_39415(color);
/* 152 */     buffer.method_22918(matrix4f, x2, y2, 0.0F).method_22913(u2, v2).method_39415(color);
/* 153 */     buffer.method_22918(matrix4f, x2, y1, 0.0F).method_22913(u2, v1).method_39415(color);
/* 154 */     class_286.method_43433(buffer.method_60800());
/*     */   }
/*     */   
/*     */   public static void drawQuad(float x, float y, float width, float height, int color) {
/* 158 */     QUAD.add(new Quad(x, y, width, height, ColorUtil.multAlpha(color, RenderSystem.getShaderColor()[3])));
/*     */   }
/*     */   public static final class Quad extends Record { private final float x; private final float y; private final float width; private final float height; private final int color;
/* 161 */     public Quad(float x, float y, float width, float height, int color) { this.x = x; this.y = y; this.width = width; this.height = height; this.color = color; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/render/Render2DUtil$Quad;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #161	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 161 */       //   0	7	0	this	Lvorga/phazeclient/base/util/render/Render2DUtil$Quad; } public float x() { return this.x; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/render/Render2DUtil$Quad;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #161	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/base/util/render/Render2DUtil$Quad; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/render/Render2DUtil$Quad;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #161	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/base/util/render/Render2DUtil$Quad;
/* 161 */       //   0	8	1	o	Ljava/lang/Object; } public float y() { return this.y; } public float width() { return this.width; } public float height() { return this.height; } public int color() { return this.color; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\render\Render2DUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */