/*     */ package vorga.phazeclient.api.system.colorcorrection;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Deque;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_4597;
/*     */ 
/*     */ 
/*     */ public final class WorldColorRenderHelper
/*     */ {
/*  13 */   private static final ThreadLocal<Deque<float[]>> ENTITY_SHADER_COLOR_STACK = ThreadLocal.withInitial(java.util.ArrayDeque::new);
/*     */   
/*  15 */   private static final ThreadLocal<Deque<float[]>> ENTITY_SHADER_COLOR_POOL = ThreadLocal.withInitial(java.util.ArrayDeque::new);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_4597 tintProvider(class_4597 original, WorldColorCorrectionController.Target target) {
/*  21 */     if (original == null || 
/*  22 */       !shouldWrapEntityProvider(target) || original instanceof TintingVertexConsumerProvider)
/*     */     {
/*  24 */       return original;
/*     */     }
/*  26 */     return new TintingVertexConsumerProvider(original, target);
/*     */   }
/*     */   
/*     */   public static boolean shouldWrapEntityProvider(WorldColorCorrectionController.Target target) {
/*  30 */     return (WorldColorCorrectionController.needsColorTransform(target) || 
/*  31 */       WorldColorCorrectionController.needsAlphaTransform(target));
/*     */   }
/*     */   
/*     */   public static boolean beginEntityShaderAlpha(WorldColorCorrectionController.Target target) {
/*  35 */     if (shouldWrapEntityProvider(target) || 
/*  36 */       !WorldColorCorrectionController.needsAlphaTransform(target) || 
/*  37 */       WorldColorCorrectionController.needsColorTransform(target)) {
/*  38 */       return false;
/*     */     }
/*     */     
/*  41 */     float[] current = RenderSystem.getShaderColor();
/*  42 */     Deque<float[]> pool = ENTITY_SHADER_COLOR_POOL.get();
/*  43 */     float[] state = pool.pollFirst();
/*  44 */     if (state == null) {
/*  45 */       state = new float[4];
/*     */     }
/*  47 */     state[0] = current[0];
/*  48 */     state[1] = current[1];
/*  49 */     state[2] = current[2];
/*  50 */     state[3] = current[3];
/*  51 */     ((Deque<float[]>)ENTITY_SHADER_COLOR_STACK.get()).push(state);
/*  52 */     RenderSystem.setShaderColor(current[0], current[1], current[2], current[3] * 
/*     */ 
/*     */ 
/*     */         
/*  56 */         WorldColorCorrectionController.getAlpha(target));
/*     */     
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   public static void endEntityShaderAlpha() {
/*  62 */     Deque<float[]> stack = ENTITY_SHADER_COLOR_STACK.get();
/*  63 */     if (stack.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     float[] previous = stack.pop();
/*  68 */     RenderSystem.setShaderColor(previous[0], previous[1], previous[2], previous[3]);
/*  69 */     ((Deque<float[]>)ENTITY_SHADER_COLOR_POOL.get()).push(previous);
/*     */   }
/*     */   
/*     */   public static boolean shouldForceBlendForLayer(class_1921 layer) {
/*  73 */     if (layer == null) {
/*  74 */       return false;
/*     */     }
/*     */     
/*  77 */     String name = layer.toString().toLowerCase(Locale.ROOT);
/*  78 */     WorldColorCorrectionController.Target target = WorldColorCorrectionController.current();
/*     */     
/*  80 */     if (target == WorldColorCorrectionController.Target.CLOUDS && name.contains("cloud")) {
/*  81 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.CLOUDS);
/*     */     }
/*     */     
/*  84 */     if (target == WorldColorCorrectionController.Target.PLAYERS && name.contains("entity")) {
/*  85 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.PLAYERS);
/*     */     }
/*     */     
/*  88 */     if (target == WorldColorCorrectionController.Target.ENTITIES && name.contains("entity")) {
/*  89 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.ENTITIES);
/*     */     }
/*     */     
/*  92 */     if (target == WorldColorCorrectionController.Target.BLOCKS && (name.contains("block_entity") || name.contains("blockentity"))) {
/*  93 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.BLOCKS);
/*     */     }
/*     */     
/*  96 */     if (target == WorldColorCorrectionController.Target.FLUIDS && name.contains("water")) {
/*  97 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.FLUIDS);
/*     */     }
/*     */     
/* 100 */     if (target == WorldColorCorrectionController.Target.FLUIDS && name.contains("lava")) {
/* 101 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.FLUIDS);
/*     */     }
/*     */     
/* 104 */     if (target == WorldColorCorrectionController.Target.FLUIDS && name.contains("fluid")) {
/* 105 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.FLUIDS);
/*     */     }
/*     */     
/* 108 */     if (target == WorldColorCorrectionController.Target.FLUIDS && name.contains("translucent")) {
/* 109 */       return WorldColorCorrectionController.needsBlend(WorldColorCorrectionController.Target.FLUIDS);
/*     */     }
/*     */     
/* 112 */     if (target == WorldColorCorrectionController.Target.CLOUDS && name.contains("translucent")) {
/* 113 */       return true;
/*     */     }
/*     */     
/* 116 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\colorcorrection\WorldColorRenderHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */