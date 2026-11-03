/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_350;
/*     */ import net.minecraft.class_437;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import vorga.phazeclient.api.system.cursor.CursorManager;
/*     */ import vorga.phazeclient.implement.features.modules.other.Animations;
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
/*     */ @Mixin({class_350.class})
/*     */ public abstract class EntryListWidgetCursorMixin
/*     */ {
/*  62 */   private static Field phaze$hoveredField = null;
/*     */   private static boolean phaze$lookupDone = false;
/*     */   private static final int ICON_COLUMN_WIDTH = 32;
/*     */   
/*     */   @Inject(method = {"renderWidget"}, at = {@At("TAIL")})
/*     */   private void phaze$cursorRequestHand(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/*  68 */     if (!Animations.getInstance().isDynamicCursorEnabled()) {
/*     */       return;
/*     */     }
/*  71 */     class_350<?> self = (class_350)this;
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (!self.method_25405(mouseX, mouseY)) {
/*     */       return;
/*     */     }
/*  78 */     Field f = phaze$resolveField();
/*  79 */     if (f == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  83 */       Object entry = f.get(self);
/*  84 */       if (entry == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       if (phaze$isIconOnlyScreen() && !phaze$pointerOverIconColumn(self, mouseX, mouseY)) {
/*     */         return;
/*     */       }
/*  98 */       CursorManager.requestHand();
/*  99 */     } catch (Throwable throwable) {}
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
/*     */   private static boolean phaze$isIconOnlyScreen() {
/* 113 */     class_310 mc = class_310.method_1551();
/* 114 */     if (mc == null) {
/* 115 */       return false;
/*     */     }
/* 117 */     class_437 s = mc.field_1755;
/* 118 */     return (s instanceof net.minecraft.class_526 || s instanceof net.minecraft.class_500);
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
/*     */   private static boolean phaze$pointerOverIconColumn(class_350<?> self, double mouseX, double mouseY) {
/*     */     try {
/* 132 */       int left = self.method_25342();
/* 133 */       return (mouseX >= left && mouseX < (left + 32));
/* 134 */     } catch (Throwable t) {
/*     */ 
/*     */       
/* 137 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Field phaze$resolveField() {
/* 147 */     if (phaze$lookupDone) {
/* 148 */       return phaze$hoveredField;
/*     */     }
/* 150 */     phaze$lookupDone = true;
/* 151 */     Class<?> cls = class_350.class;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 157 */       Field f = cls.getDeclaredField("hoveredEntry");
/* 158 */       f.setAccessible(true);
/* 159 */       phaze$hoveredField = f;
/* 160 */       return f;
/* 161 */     } catch (NoSuchFieldException noSuchFieldException) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 174 */         for (Field f : cls.getDeclaredFields()) {
/* 175 */           int mods = f.getModifiers();
/* 176 */           if (!Modifier.isStatic(mods) && !Modifier.isFinal(mods))
/*     */           
/*     */           { 
/* 179 */             Class<?> t = f.getType();
/* 180 */             if (t.getDeclaringClass() == cls)
/*     */             {
/*     */               
/* 183 */               if (Modifier.isAbstract(t.getModifiers()))
/*     */               
/*     */               { 
/* 186 */                 f.setAccessible(true);
/* 187 */                 phaze$hoveredField = f;
/* 188 */                 return f; }  }  } 
/*     */         } 
/* 190 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */       
/* 194 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EntryListWidgetCursorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */