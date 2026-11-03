/*     */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_4608;
/*     */ import net.minecraft.class_7833;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.CosmeticEntry;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoBone;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoCube;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoQuad;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoVertex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PulseGeoRenderer
/*     */ {
/*     */   public static boolean render(CosmeticEntry entry, class_4587 matrices, class_4597 providers, int light, boolean gliding, boolean swimming, boolean sneaking, boolean moving, float timeSeconds) {
/*  31 */     PulseSourceModel source = PulseSourceModelLoader.get(entry);
/*  32 */     if (source == null || entry.textureId() == null) return false;
/*     */     
/*  34 */     source.resetBones();
/*  35 */     String animationName = source.chooseAnimation(gliding, swimming, sneaking, moving);
/*  36 */     if (animationName != null) {
/*  37 */       PulseSourceModel.AnimationData animation = source.animations.get(animationName);
/*  38 */       if (animation != null) applyAnimation(source, animation, timeSeconds);
/*     */     
/*     */     } 
/*  41 */     matrices.method_22903();
/*     */ 
/*     */     
/*  44 */     matrices.method_22907(class_7833.field_40718.rotationDegrees(180.0F));
/*  45 */     matrices.method_46416(source.x, source.y, source.z);
/*  46 */     if (source.yaw != 0.0F) matrices.method_22907(class_7833.field_40716.rotationDegrees(source.yaw)); 
/*  47 */     if (source.pitch != 0.0F) matrices.method_22907(class_7833.field_40714.rotationDegrees(source.pitch)); 
/*  48 */     if (source.roll != 0.0F) matrices.method_22907(class_7833.field_40718.rotationDegrees(source.roll)); 
/*  49 */     matrices.method_22905(source.scale, source.scale, source.scale);
/*     */     
/*  51 */     class_4588 consumer = providers.getBuffer(class_1921.method_23578(entry.textureId()));
/*  52 */     RenderSystem.disableCull();
/*     */     try {
/*  54 */       for (GeoBone bone : source.model.topLevelBones) {
/*  55 */         renderBone(bone, matrices, consumer, light, class_4608.field_21444);
/*     */       }
/*     */     } finally {
/*  58 */       RenderSystem.enableCull();
/*  59 */       matrices.method_22909();
/*     */     } 
/*  61 */     return true;
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
/*     */   public static boolean renderPet(CosmeticEntry entry, class_4587 matrices, class_4597 providers, int light, boolean gliding, boolean swimming, boolean sneaking, boolean moving, float timeSeconds) {
/*  81 */     PulseSourceModel source = PulseSourceModelLoader.get(entry);
/*  82 */     if (source == null || entry.textureId() == null) return false;
/*     */     
/*  84 */     source.resetBones();
/*  85 */     String animationName = source.chooseAnimation(gliding, swimming, sneaking, moving);
/*  86 */     if (animationName != null) {
/*  87 */       PulseSourceModel.AnimationData animation = source.animations.get(animationName);
/*  88 */       if (animation != null) applyAnimation(source, animation, timeSeconds);
/*     */     
/*     */     } 
/*  91 */     matrices.method_22903();
/*  92 */     matrices.method_22907(class_7833.field_40718.rotationDegrees(180.0F));
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (source.yaw != 0.0F) matrices.method_22907(class_7833.field_40716.rotationDegrees(source.yaw)); 
/*  97 */     if (source.pitch != 0.0F) matrices.method_22907(class_7833.field_40714.rotationDegrees(source.pitch)); 
/*  98 */     if (source.roll != 0.0F) matrices.method_22907(class_7833.field_40718.rotationDegrees(source.roll)); 
/*  99 */     matrices.method_22905(source.scale, source.scale, source.scale);
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (!source.model.topLevelBones.isEmpty()) {
/* 104 */       GeoBone root = source.model.topLevelBones.get(0);
/* 105 */       float px = root.getPivotX() / 16.0F;
/* 106 */       float py = root.getPivotY() / 16.0F;
/* 107 */       float pz = root.getPivotZ() / 16.0F;
/* 108 */       matrices.method_46416(px, py, pz);
/* 109 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(180.0F));
/* 110 */       matrices.method_22905(1.0F, -1.0F, 1.0F);
/* 111 */       matrices.method_46416(-px, -py, -pz);
/*     */     } else {
/* 113 */       matrices.method_22907(class_7833.field_40716.rotationDegrees(180.0F));
/* 114 */       matrices.method_22905(1.0F, -1.0F, 1.0F);
/*     */     } 
/*     */     
/* 117 */     class_4588 consumer = providers.getBuffer(class_1921.method_23578(entry.textureId()));
/* 118 */     RenderSystem.disableCull();
/*     */     try {
/* 120 */       for (GeoBone bone : source.model.topLevelBones) {
/* 121 */         renderBone(bone, matrices, consumer, light, class_4608.field_21444);
/*     */       }
/*     */     } finally {
/* 124 */       RenderSystem.enableCull();
/* 125 */       matrices.method_22909();
/*     */     } 
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   private static void applyAnimation(PulseSourceModel source, PulseSourceModel.AnimationData animation, float timeSeconds) {
/* 131 */     float t = animation.loop ? (timeSeconds % animation.length) : Math.min(timeSeconds, animation.length);
/*     */     
/* 133 */     for (Map.Entry<String, PulseSourceModel.BoneAnimationData> entry : animation.bones.entrySet()) {
/* 134 */       GeoBone bone = source.findBone(entry.getKey());
/* 135 */       if (bone == null)
/* 136 */         continue;  float[] base = source.initialBoneTransforms.get(entry.getKey());
/* 137 */       if (base == null) base = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };
/*     */       
/* 139 */       PulseSourceModel.BoneAnimationData data = entry.getValue();
/* 140 */       if (!data.rotation.isEmpty()) {
/* 141 */         float[] v = data.rotation.sample(t, new float[] { 0.0F, 0.0F, 0.0F });
/* 142 */         bone.setRotationX(base[0] + (float)Math.toRadians(-v[0]));
/* 143 */         bone.setRotationY(base[1] + (float)Math.toRadians(-v[1]));
/* 144 */         bone.setRotationZ(base[2] + (float)Math.toRadians(v[2]));
/*     */       } 
/* 146 */       if (!data.position.isEmpty()) {
/* 147 */         float[] v = data.position.sample(t, new float[] { 0.0F, 0.0F, 0.0F });
/* 148 */         bone.setPositionX(base[3] + v[0]);
/* 149 */         bone.setPositionY(base[4] + v[1]);
/* 150 */         bone.setPositionZ(base[5] + v[2]);
/*     */       } 
/* 152 */       if (!data.scale.isEmpty()) {
/* 153 */         float[] v = data.scale.sample(t, new float[] { 1.0F, 1.0F, 1.0F });
/* 154 */         bone.setScaleX(base[6] * v[0]);
/* 155 */         bone.setScaleY(base[7] * v[1]);
/* 156 */         bone.setScaleZ(base[8] * v[2]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderBone(GeoBone bone, class_4587 matrices, class_4588 consumer, int light, int overlay) {
/* 166 */     if (bone.isHidden)
/*     */       return; 
/* 168 */     matrices.method_22903();
/* 169 */     GeckoRenderHelper.translate(bone, matrices);
/* 170 */     GeckoRenderHelper.moveToPivot(bone, matrices);
/* 171 */     GeckoRenderHelper.rotate(bone, matrices);
/* 172 */     GeckoRenderHelper.scale(bone, matrices);
/* 173 */     GeckoRenderHelper.moveBackFromPivot(bone, matrices);
/*     */     
/* 175 */     for (GeoCube cube : bone.childCubes) {
/* 176 */       renderCube(cube, matrices, consumer, light, overlay);
/*     */     }
/* 178 */     for (GeoBone child : bone.childBones) {
/* 179 */       renderBone(child, matrices, consumer, light, overlay);
/*     */     }
/* 181 */     matrices.method_22909();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderCube(GeoCube cube, class_4587 matrices, class_4588 consumer, int light, int overlay) {
/* 189 */     matrices.method_22903();
/* 190 */     GeckoRenderHelper.moveToPivot(cube, matrices);
/* 191 */     GeckoRenderHelper.rotate(cube, matrices);
/* 192 */     GeckoRenderHelper.moveBackFromPivot(cube, matrices);
/*     */     
/* 194 */     Matrix4f positionMatrix = matrices.method_23760().method_23761();
/* 195 */     Matrix3f normalMatrix = matrices.method_23760().method_23762();
/*     */     
/* 197 */     for (GeoQuad quad : cube.quads) {
/* 198 */       if (quad != null) {
/*     */         
/* 200 */         Vector3f normal = new Vector3f(quad.normal.getX(), quad.normal.getY(), quad.normal.getZ());
/* 201 */         normalMatrix.transform(normal);
/* 202 */         float nx = normal.x();
/* 203 */         float ny = normal.y();
/* 204 */         float nz = normal.z();
/*     */ 
/*     */         
/* 207 */         if ((cube.size.getY() == 0.0F || cube.size.getZ() == 0.0F) && nx < 0.0F) nx = -nx; 
/* 208 */         if ((cube.size.getX() == 0.0F || cube.size.getZ() == 0.0F) && ny < 0.0F) ny = -ny; 
/* 209 */         if ((cube.size.getX() == 0.0F || cube.size.getY() == 0.0F) && nz < 0.0F) nz = -nz;
/*     */         
/* 211 */         for (GeoVertex vertex : quad.vertices)
/* 212 */           consumer.method_22918(positionMatrix, vertex.position
/* 213 */               .getX(), vertex.position.getY(), vertex.position.getZ())
/* 214 */             .method_1336(255, 255, 255, 255)
/* 215 */             .method_22913(vertex.textureU, vertex.textureV)
/* 216 */             .method_22922(overlay)
/* 217 */             .method_60803(light)
/* 218 */             .method_22914(nx, ny, nz); 
/*     */       } 
/*     */     } 
/* 221 */     matrices.method_22909();
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\PulseGeoRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */