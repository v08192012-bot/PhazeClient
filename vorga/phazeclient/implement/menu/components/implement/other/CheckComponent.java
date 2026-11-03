/*    */ package vorga.phazeclient.implement.menu.components.implement.other;
/*    */ 
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_4587;
/*    */ import vorga.phazeclient.api.system.animation.Animation;
/*    */ import vorga.phazeclient.api.system.animation.Direction;
/*    */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*    */ import vorga.phazeclient.base.util.math.MathUtil;
/*    */ import vorga.phazeclient.implement.menu.MenuStyle;
/*    */ 
/*    */ public class CheckComponent extends AbstractComponent {
/*    */   private static final float TOGGLE_WIDTH = 18.0F;
/*    */   private static final float TOGGLE_HEIGHT = 10.0F;
/*    */   
/* 15 */   public CheckComponent setState(boolean state) { this.state = state; return this; } private boolean state; private Runnable runnable; public CheckComponent setRunnable(Runnable runnable) { this.runnable = runnable; return this; } public CheckComponent setInitialized(boolean initialized) { this.initialized = initialized; return this; } public CheckComponent setAlpha(float alpha) { this.alpha = alpha; return this; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean initialized = false;
/*    */ 
/*    */   
/* 24 */   private float alpha = 1.0F;
/*    */   
/* 26 */   private final Animation toggleAnimation = (new DecelerateAnimation()).setMs(200).setValue(1.0D);
/* 27 */   private final Animation hoverAnimation = (new DecelerateAnimation()).setMs(170).setValue(1.0D);
/*    */   
/*    */   public static float getToggleWidth() {
/* 30 */     return 18.0F;
/*    */   }
/*    */   
/*    */   public static float getToggleHeight() {
/* 34 */     return 10.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/* 39 */     if (!this.initialized) {
/* 40 */       this.initialized = true;
/* 41 */       this.toggleAnimation.setDirectionAndFinish(this.state ? Direction.FORWARDS : Direction.BACKWARDS);
/*    */     } 
/* 43 */     class_4587 matrix = context.method_51448();
/* 44 */     this.toggleAnimation.setDirection(this.state ? Direction.FORWARDS : Direction.BACKWARDS);
/*    */     
/* 46 */     float progress = this.toggleAnimation.getOutputFloat();
/* 47 */     boolean hovered = MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 18.0D, 10.0D);
/* 48 */     if (hovered) {
/* 49 */       CursorManager.requestHand();
/*    */     }
/* 51 */     this.hoverAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
/* 52 */     float hoverProgress = this.hoverAnimation.getOutputFloat();
/*    */     
/* 54 */     int inactiveBackground = MenuStyle.pill(false);
/* 55 */     int activeBackground = MenuStyle.mix(MenuStyle.CHIP_ACTIVE, MenuStyle.ACCENT_GREEN, 0.22F);
/* 56 */     int background = MenuStyle.mix(inactiveBackground, activeBackground, progress);
/* 57 */     background = MenuStyle.mix(background, MenuStyle.TEXT_PRIMARY, hoverProgress * 0.035F);
/* 58 */     int outline = MenuStyle.mix(
/* 59 */         MenuStyle.settingOutline(false), 
/* 60 */         MenuStyle.settingOutline(true), progress);
/*    */ 
/*    */     
/* 63 */     outline = MenuStyle.mix(outline, MenuStyle.BORDER_LIGHT, hoverProgress * 0.3F);
/* 64 */     float knobX = this.x + 1.2F + 7.0F * progress;
/* 65 */     float clampedAlpha = Math.max(0.0F, Math.min(1.0F, this.alpha));
/*    */     
/* 67 */     rectangle.render(ShapeProperties.create(matrix, this.x, this.y, 18.0D, 10.0D)
/* 68 */         .round(5.0F)
/* 69 */         .thickness(1.1F)
/* 70 */         .outlineColor(MenuStyle.withAlpha(outline, clampedAlpha))
/* 71 */         .color(MenuStyle.withAlpha(background, clampedAlpha))
/* 72 */         .build());
/*    */     
/* 74 */     rectangle.render(ShapeProperties.create(matrix, knobX, (this.y + 1.45F), 7.0D, 7.0D)
/* 75 */         .round(3.5F)
/* 76 */         .color(MenuStyle.withAlpha(MenuStyle.TEXT_PRIMARY, clampedAlpha))
/* 77 */         .build());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/* 82 */     if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 18.0D, 10.0D) && button == 0) {
/* 83 */       playButtonClickSound();
/* 84 */       this.runnable.run();
/* 85 */       return true;
/*    */     } 
/* 87 */     return super.mouseClicked(mouseX, mouseY, button);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\CheckComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */