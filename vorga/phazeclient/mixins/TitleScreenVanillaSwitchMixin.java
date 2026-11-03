/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_364;
/*    */ import net.minecraft.class_4185;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_442;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import vorga.phazeclient.base.util.render.Render2DUtil;
/*    */ import vorga.phazeclient.implement.menu.MainMenuScreen;
/*    */ import vorga.phazeclient.implement.menu.UiMsdfIconAtlas;
/*    */ 
/*    */ @Mixin({class_442.class})
/*    */ public abstract class TitleScreenVanillaSwitchMixin extends class_437 {
/* 22 */   private static final class_2960 PHAZE_MENU_SWITCH_ICON = class_2960.method_60655("phaze", "textures/menu/vanilla_main_menu.png");
/*    */   @Unique
/*    */   private class_4185 phaze$switchBackButton;
/*    */   
/*    */   protected TitleScreenVanillaSwitchMixin(class_2561 title) {
/* 27 */     super(title);
/*    */   }
/*    */   
/*    */   @Inject(method = {"init"}, at = {@At("TAIL")})
/*    */   private void phaze$addSwitchBackButton(CallbackInfo ci) {
/* 32 */     if (MainMenuScreen.isCustomMainMenuEnabled()) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     this.phaze$switchBackButton = (class_4185)method_37063(
/* 37 */         (class_364)class_4185.method_46430(
/* 38 */           (class_2561)class_2561.method_43473(), button -> {
/*    */             MainMenuScreen.setCustomMainMenuEnabled(true);
/*    */ 
/*    */ 
/*    */             
/*    */             if (this.field_22787 != null) {
/*    */               this.field_22787.method_1507((class_437)new MainMenuScreen());
/*    */             }
/* 46 */           }).method_46434(this.field_22789 - 28, -3, 20, 20)
/* 47 */         .method_46431());
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*    */   private void phaze$renderSwitchButtonIcon(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 53 */     if (MainMenuScreen.isCustomMainMenuEnabled() || this.phaze$switchBackButton == null || !this.phaze$switchBackButton.field_22764) {
/*    */       return;
/*    */     }
/*    */     
/* 57 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 58 */     float iconHeight = 22.0F;
/* 59 */     float iconWidth = iconHeight * Math.max(1.0F, UiMsdfIconAtlas.resolveAspectRatio(PHAZE_MENU_SWITCH_ICON));
/* 60 */     float iconX = this.phaze$switchBackButton.method_46426() + (this.phaze$switchBackButton.method_25368() - iconWidth) / 2.0F;
/* 61 */     float iconY = this.phaze$switchBackButton.method_46427() + (this.phaze$switchBackButton.method_25364() - iconHeight) / 2.0F;
/* 62 */     int tint = -1;
/* 63 */     if (!UiMsdfIconAtlas.renderIcon(context, PHAZE_MENU_SWITCH_ICON, iconX, iconY, iconWidth, iconHeight, tint, true)) {
/* 64 */       Render2DUtil.drawTexture(context
/* 65 */           .method_51448(), PHAZE_MENU_SWITCH_ICON, iconX, iconX + iconWidth, iconY, iconY + iconHeight, 0.0F, 64, 64, 0.0F, 0.0F, 64, 64, tint);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\TitleScreenVanillaSwitchMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */