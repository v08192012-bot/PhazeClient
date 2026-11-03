/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_10055;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_591;
/*     */ import net.minecraft.class_630;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_8685;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
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
/*     */ public final class ThreeDSkinsHelper
/*     */ {
/*     */   private static boolean activeForCurrentEntity = false;
/*  31 */   private static ThreeDSkinsVoxelizer.BakedSkinLayers currentBaked = null;
/*     */   private static boolean currentIsSlim = false;
/*  33 */   private static final IdentityHashMap<class_630, Boolean> originalVisibility = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static void preRender(class_591 model, class_10055 state) {
/*  38 */     activeForCurrentEntity = false;
/*  39 */     currentBaked = null;
/*  40 */     originalVisibility.clear();
/*     */     
/*  42 */     if (model == null || state == null)
/*     */       return; 
/*  44 */     ThreeDSkins module = ThreeDSkins.getInstance();
/*  45 */     if (module == null || !module.isEnabled())
/*     */       return; 
/*  47 */     class_310 client = class_310.method_1551();
/*  48 */     class_746 player = client.field_1724;
/*  49 */     boolean isLocal = (player != null && state.field_53528 == player.method_5628());
/*  50 */     if (!isLocal && !module.showOthers.isValue()) {
/*     */       return;
/*     */     }
/*  53 */     if (player != null && !isLocal) {
/*  54 */       double dx = player.method_23317() - state.field_53325;
/*  55 */       double dy = player.method_23318() - state.field_53326;
/*  56 */       double dz = player.method_23321() - state.field_53327;
/*  57 */       double maxDist = module.maxDistance.getValue();
/*  58 */       if (dx * dx + dy * dy + dz * dz > maxDist * maxDist)
/*     */         return; 
/*     */     } 
/*  61 */     class_2960 skinId = (state.field_53520 != null) ? state.field_53520.comp_1626() : null;
/*  62 */     if (skinId == null)
/*     */       return; 
/*  64 */     ThreeDSkinsVoxelizer.BakedSkinLayers baked = ThreeDSkinsVoxelizer.get(skinId);
/*  65 */     if (baked == null || !baked.hasAny())
/*     */       return; 
/*  67 */     activeForCurrentEntity = true;
/*  68 */     currentBaked = baked;
/*  69 */     currentIsSlim = (state.field_53520 != null && state.field_53520.comp_1629() == class_8685.class_7920.field_41122);
/*     */ 
/*     */     
/*  72 */     if (module.hat3D.isValue() && !baked.hat.isEmpty()) {
/*  73 */       hide(model.field_3394);
/*     */     }
/*  75 */     if (module.jacket3D.isValue() && !baked.jacket.isEmpty()) {
/*  76 */       hide(model.field_3483);
/*     */     }
/*  78 */     if (module.sleeves3D.isValue()) {
/*  79 */       List<ThreeDSkinsVoxelizer.VoxelQuad> leftSleeve = currentIsSlim ? baked.leftSleeveSlim : baked.leftSleeveWide;
/*  80 */       List<ThreeDSkinsVoxelizer.VoxelQuad> rightSleeve = currentIsSlim ? baked.rightSleeveSlim : baked.rightSleeveWide;
/*  81 */       if (leftSleeve != null && !leftSleeve.isEmpty()) {
/*  82 */         hide(model.field_3484);
/*     */       }
/*  84 */       if (rightSleeve != null && !rightSleeve.isEmpty()) {
/*  85 */         hide(model.field_3486);
/*     */       }
/*     */     } 
/*  88 */     if (module.pants3D.isValue()) {
/*  89 */       if (!baked.leftPants.isEmpty()) {
/*  90 */         hide(model.field_3482);
/*     */       }
/*  92 */       if (!baked.rightPants.isEmpty()) {
/*  93 */         hide(model.field_3479);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void postRender(class_591 model, class_10055 state, class_4587 matrices, class_4597 vertexConsumers, int light, int overlay) {
/* 106 */     if (!activeForCurrentEntity || currentBaked == null || model == null || state == null) {
/* 107 */       restoreVisibility();
/* 108 */       activeForCurrentEntity = false;
/* 109 */       currentBaked = null;
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     ThreeDSkins module = ThreeDSkins.getInstance();
/* 114 */     if (module == null || !module.isEnabled()) {
/* 115 */       restoreVisibility();
/* 116 */       activeForCurrentEntity = false;
/* 117 */       currentBaked = null;
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     class_2960 skinId = (state.field_53520 != null) ? state.field_53520.comp_1626() : null;
/* 122 */     if (skinId == null) {
/* 123 */       restoreVisibility();
/* 124 */       activeForCurrentEntity = false;
/* 125 */       currentBaked = null;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     class_4588 consumer = vertexConsumers.getBuffer(class_1921.method_23578(skinId));
/*     */ 
/*     */     
/* 135 */     if (module.hat3D.isValue() && !currentBaked.hat.isEmpty()) {
/* 136 */       matrices.method_22903();
/* 137 */       transformPart(matrices, model.field_3398);
/* 138 */       matrices.method_22905(0.0625F, 0.0625F, 0.0625F);
/* 139 */       renderQuads(consumer, matrices, currentBaked.hat, light, overlay);
/* 140 */       matrices.method_22909();
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (module.jacket3D.isValue() && !currentBaked.jacket.isEmpty()) {
/* 145 */       matrices.method_22903();
/* 146 */       transformPart(matrices, model.field_3391);
/* 147 */       matrices.method_22905(0.0625F, 0.0625F, 0.0625F);
/* 148 */       renderQuads(consumer, matrices, currentBaked.jacket, light, overlay);
/* 149 */       matrices.method_22909();
/*     */     } 
/*     */ 
/*     */     
/* 153 */     if (module.sleeves3D.isValue()) {
/* 154 */       List<ThreeDSkinsVoxelizer.VoxelQuad> rightSleeve = currentIsSlim ? currentBaked.rightSleeveSlim : currentBaked.rightSleeveWide;
/* 155 */       if (rightSleeve != null && !rightSleeve.isEmpty()) {
/* 156 */         matrices.method_22903();
/* 157 */         model.field_3401.method_22703(matrices);
/* 158 */         matrices.method_22905(0.0625F, 0.0625F, 0.0625F);
/* 159 */         renderQuads(consumer, matrices, rightSleeve, light, overlay);
/* 160 */         matrices.method_22909();
/*     */       } 
/*     */       
/* 163 */       List<ThreeDSkinsVoxelizer.VoxelQuad> leftSleeve = currentIsSlim ? currentBaked.leftSleeveSlim : currentBaked.leftSleeveWide;
/* 164 */       if (leftSleeve != null && !leftSleeve.isEmpty()) {
/* 165 */         matrices.method_22903();
/* 166 */         model.field_27433.method_22703(matrices);
/* 167 */         matrices.method_22905(0.0625F, 0.0625F, 0.0625F);
/* 168 */         renderQuads(consumer, matrices, leftSleeve, light, overlay);
/* 169 */         matrices.method_22909();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 174 */     if (module.pants3D.isValue()) {
/* 175 */       if (!currentBaked.rightPants.isEmpty()) {
/* 176 */         matrices.method_22903();
/* 177 */         model.field_3392.method_22703(matrices);
/* 178 */         matrices.method_22905(0.0625F, 0.0625F, 0.0625F);
/* 179 */         renderQuads(consumer, matrices, currentBaked.rightPants, light, overlay);
/* 180 */         matrices.method_22909();
/*     */       } 
/*     */       
/* 183 */       if (!currentBaked.leftPants.isEmpty()) {
/* 184 */         matrices.method_22903();
/* 185 */         model.field_3397.method_22703(matrices);
/* 186 */         matrices.method_22905(0.0625F, 0.0625F, 0.0625F);
/* 187 */         renderQuads(consumer, matrices, currentBaked.leftPants, light, overlay);
/* 188 */         matrices.method_22909();
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     restoreVisibility();
/* 193 */     activeForCurrentEntity = false;
/* 194 */     currentBaked = null;
/*     */   }
/*     */   
/*     */   private static void hide(class_630 part) {
/* 198 */     originalVisibility.putIfAbsent(part, Boolean.valueOf(part.field_3665));
/* 199 */     part.field_3665 = false;
/*     */   }
/*     */   
/*     */   private static void restoreVisibility() {
/* 203 */     originalVisibility.forEach((part, visible) -> part.field_3665 = visible.booleanValue());
/* 204 */     originalVisibility.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void transformPart(class_4587 matrices, class_630 part) {
/* 215 */     matrices.method_46416(part.field_3657 / 16.0F, part.field_3656 / 16.0F, part.field_3655 / 16.0F);
/* 216 */     part.method_22703(matrices);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderQuads(class_4588 consumer, class_4587 matrices, List<ThreeDSkinsVoxelizer.VoxelQuad> quads, int light, int overlay) {
/* 226 */     class_4587.class_4665 entry = matrices.method_23760();
/* 227 */     Matrix4f posMatrix = entry.method_23761();
/*     */     
/* 229 */     for (ThreeDSkinsVoxelizer.VoxelQuad quad : quads) {
/* 230 */       Vector3f normal = quad.normal();
/* 231 */       for (ThreeDSkinsVoxelizer.VoxelVertex v : quad.vertices())
/* 232 */         consumer.method_22918(posMatrix, v.x(), v.y(), v.z())
/* 233 */           .method_39415(-1)
/* 234 */           .method_22913(v.u(), v.v())
/* 235 */           .method_22922(overlay)
/* 236 */           .method_60803(light)
/* 237 */           .method_60831(entry, normal.x(), normal.y(), normal.z()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ThreeDSkinsHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */