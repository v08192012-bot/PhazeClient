/*     */ package vorga.phazeclient.implement.menu.components.implement.settings;
/*     */ 
/*     */ import net.minecraft.class_4587;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.base.util.math.MathUtil;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ 
/*     */ public class ResetIconComponent
/*     */   implements QuickImports {
/*     */   private static final float BASE_ICON_SIZE = 8.0F;
/*     */   private static final float ICON_SIZE = 9.855073F;
/*     */   private static final float ICON_X_OFFSET = 7.0724635F;
/*     */   private static final float ICON_Y_OFFSET = 5.0724635F;
/*     */   private static final String ICON_TEXTURE = "textures/reset.png";
/*     */   private float x;
/*     */   private float y;
/*  21 */   private float alpha = 1.0F;
/*     */   private boolean isModified = false;
/*  23 */   private final Animation visibilityAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean visibilitySeeded = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResetIconComponent position(float parentX, float parentY) {
/*  35 */     this.x = parentX + 7.0724635F;
/*  36 */     this.y = parentY + 5.0724635F;
/*  37 */     return this;
/*     */   }
/*     */   
/*     */   public ResetIconComponent position(float parentX, float parentY, float parentHeight) {
/*  41 */     this.x = parentX + 7.0724635F;
/*  42 */     this.y = parentY + (parentHeight - 9.855073F) / 2.0F;
/*  43 */     return this;
/*     */   }
/*     */   
/*     */   public ResetIconComponent alpha(float alpha) {
/*  47 */     this.alpha = alpha;
/*  48 */     return this;
/*     */   }
/*     */   
/*     */   public ResetIconComponent modified(boolean isModified) {
/*  52 */     this.isModified = isModified;
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   public void render(class_4587 matrix) {
/*  57 */     Direction targetDir = this.isModified ? Direction.FORWARDS : Direction.BACKWARDS;
/*  58 */     if (!this.visibilitySeeded) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  65 */       this.visibilityAnimation.setDirectionAndFinish(targetDir);
/*  66 */       this.visibilitySeeded = true;
/*     */     } else {
/*  68 */       this.visibilityAnimation.setDirection(targetDir);
/*     */     } 
/*  70 */     float visible = this.visibilityAnimation.getOutputFloat();
/*  71 */     float iconAlpha = this.alpha * visible;
/*  72 */     if (iconAlpha <= 0.01F) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     int iconColor = MenuStyle.withAlpha(MenuStyle.TEXT_MUTED, iconAlpha);
/*     */     
/*  78 */     image.setTexture("textures/reset.png")
/*  79 */       .render(ShapeProperties.create(matrix, this.x, this.y, 9.855072975158691D, 9.855072975158691D)
/*  80 */         .color(iconColor)
/*  81 */         .build());
/*     */   }
/*     */   
/*     */   public boolean isHovered(double mouseX, double mouseY) {
/*  85 */     if (this.alpha <= 0.01F) {
/*  86 */       return false;
/*     */     }
/*  88 */     return MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 9.855072975158691D, 9.855072975158691D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getTextOffset() {
/*  94 */     return 10.0F;
/*     */   }
/*     */   
/*     */   public static float getXOffset() {
/*  98 */     return 7.0724635F;
/*     */   }
/*     */   
/*     */   public static float getYOffset() {
/* 102 */     return 5.0724635F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\settings\ResetIconComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */