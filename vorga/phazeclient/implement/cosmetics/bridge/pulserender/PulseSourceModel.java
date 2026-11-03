/*     */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Objects;
/*     */ import java.util.TreeMap;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoBone;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoModel;
/*     */ 
/*     */ 
/*     */ final class PulseSourceModel
/*     */ {
/*     */   final int id;
/*     */   final String name;
/*     */   final int position;
/*     */   final float scale;
/*     */   final float x;
/*     */   final float y;
/*  21 */   final Map<String, float[]> initialBoneTransforms = (Map)new HashMap<>();
/*     */   
/*     */   final float z;
/*     */   
/*     */   final float yaw;
/*     */   
/*     */   final float pitch;
/*     */   
/*     */   final float roll;
/*     */   
/*     */   final GeoModel model;
/*     */   final Map<String, AnimationData> animations;
/*     */   
/*     */   PulseSourceModel(int id, String name, int position, float scale, float x, float y, float z, float yaw, float pitch, float roll, GeoModel model, Map<String, AnimationData> animations) {
/*  35 */     this.id = id;
/*  36 */     this.name = name;
/*  37 */     this.position = position;
/*  38 */     this.scale = scale;
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.z = z;
/*  42 */     this.yaw = yaw;
/*  43 */     this.pitch = pitch;
/*  44 */     this.roll = roll;
/*  45 */     this.model = model;
/*  46 */     this.animations = animations;
/*  47 */     for (GeoBone bone : model.topLevelBones) {
/*  48 */       saveBoneRecursive(bone);
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveBoneRecursive(GeoBone bone) {
/*  53 */     this.initialBoneTransforms.put(bone.name, new float[] { bone
/*  54 */           .getRotationX(), bone.getRotationY(), bone.getRotationZ(), bone
/*  55 */           .getPositionX(), bone.getPositionY(), bone.getPositionZ(), bone
/*  56 */           .getScaleX(), bone.getScaleY(), bone.getScaleZ() });
/*     */     
/*  58 */     for (GeoBone child : bone.childBones) saveBoneRecursive(child); 
/*     */   }
/*     */   
/*     */   void resetBones() {
/*  62 */     for (GeoBone bone : this.model.topLevelBones) resetBoneRecursive(bone); 
/*     */   }
/*     */   
/*     */   private void resetBoneRecursive(GeoBone bone) {
/*  66 */     float[] base = this.initialBoneTransforms.get(bone.name);
/*  67 */     if (base != null) {
/*  68 */       bone.setRotationX(base[0]);
/*  69 */       bone.setRotationY(base[1]);
/*  70 */       bone.setRotationZ(base[2]);
/*  71 */       bone.setPositionX(base[3]);
/*  72 */       bone.setPositionY(base[4]);
/*  73 */       bone.setPositionZ(base[5]);
/*  74 */       bone.setScaleX(base[6]);
/*  75 */       bone.setScaleY(base[7]);
/*  76 */       bone.setScaleZ(base[8]);
/*     */     } 
/*  78 */     for (GeoBone child : bone.childBones) resetBoneRecursive(child); 
/*     */   }
/*     */   
/*     */   GeoBone findBone(String name) {
/*  82 */     return this.model.getBone(name).orElse(null);
/*     */   }
/*     */   
/*     */   String chooseAnimation(boolean gliding, boolean swimming, boolean sneaking, boolean moving) {
/*  86 */     if (this.animations.isEmpty()) return null;
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (gliding) {
/*  91 */       String s = find(new String[] { "elytra", "flying", "fly" });
/*  92 */       if (s != null) return s;
/*     */     
/*     */     } 
/*  95 */     if (swimming) {
/*  96 */       if (moving) {
/*  97 */         String str = findAll(new String[] { "moving", "water" });
/*  98 */         if (str != null) return str; 
/*     */       } 
/* 100 */       String s = find(new String[] { "swimming", "swim" });
/* 101 */       if (s != null) return s; 
/* 102 */       s = findAll(new String[] { "idle", "water" });
/* 103 */       if (s != null) return s; 
/* 104 */       s = findAll(new String[] { "main", "water" });
/* 105 */       if (s != null) return s;
/*     */     
/*     */     } 
/* 108 */     if (sneaking) {
/* 109 */       if (moving) {
/* 110 */         String str = findAll(new String[] { "moving", "sneak" });
/* 111 */         if (str != null) return str; 
/*     */       } 
/* 113 */       String s = findAll(new String[] { "idle", "sneak" });
/* 114 */       if (s != null) return s; 
/* 115 */       s = findAll(new String[] { "main", "sneak" });
/* 116 */       if (s != null) return s; 
/* 117 */       s = find(new String[] { "sneak", "crouch" });
/* 118 */       if (s != null) return s;
/*     */     
/*     */     } 
/* 121 */     if (moving) {
/* 122 */       String s = find(new String[] { "walking", "walk", "moving", "move", "run" });
/* 123 */       if (s != null && !lower(s).contains("water") && !lower(s).contains("sneak")) return s;
/*     */     
/*     */     } 
/* 126 */     String idle = findExactOrContains("idle");
/* 127 */     if (idle != null && !lower(idle).contains("water") && !lower(idle).contains("sneak")) return idle; 
/* 128 */     String main = findExactOrContains("main");
/* 129 */     if (main != null && !lower(main).contains("water") && !lower(main).contains("sneak")) return main;
/*     */     
/* 131 */     for (String name : this.animations.keySet()) {
/* 132 */       String l = lower(name);
/* 133 */       if (!l.contains("gui") && !l.contains("preview")) return name; 
/*     */     } 
/* 135 */     return this.animations.keySet().iterator().next();
/*     */   }
/*     */   
/*     */   private String find(String... needles) {
/* 139 */     for (String name : this.animations.keySet()) {
/* 140 */       String l = lower(name);
/* 141 */       for (String needle : needles) {
/* 142 */         if (l.contains(needle)) return name; 
/*     */       } 
/*     */     } 
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   private String findAll(String... needles) {
/* 149 */     for (String name : this.animations.keySet()) {
/* 150 */       String l = lower(name);
/* 151 */       boolean ok = true;
/* 152 */       for (String needle : needles) ok &= l.contains(needle); 
/* 153 */       if (ok) return name; 
/*     */     } 
/* 155 */     return null;
/*     */   }
/*     */   
/*     */   private String findExactOrContains(String needle) {
/* 159 */     for (String name : this.animations.keySet()) {
/* 160 */       if (lower(name).equals(needle)) return name; 
/*     */     } 
/* 162 */     return find(new String[] { needle });
/*     */   }
/*     */   
/*     */   private static String lower(String s) {
/* 166 */     return s.toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   static final class AnimationData {
/*     */     final String name;
/*     */     final boolean loop;
/*     */     final float length;
/*     */     final Map<String, PulseSourceModel.BoneAnimationData> bones;
/*     */     
/*     */     AnimationData(String name, boolean loop, float length, Map<String, PulseSourceModel.BoneAnimationData> bones) {
/* 176 */       this.name = name;
/* 177 */       this.loop = loop;
/* 178 */       this.length = Math.max(0.001F, length);
/* 179 */       this.bones = bones;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class BoneAnimationData {
/*     */     final PulseSourceModel.Channel rotation;
/*     */     final PulseSourceModel.Channel position;
/*     */     final PulseSourceModel.Channel scale;
/*     */     
/*     */     BoneAnimationData(PulseSourceModel.Channel rotation, PulseSourceModel.Channel position, PulseSourceModel.Channel scale) {
/* 189 */       this.rotation = rotation;
/* 190 */       this.position = position;
/* 191 */       this.scale = scale;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Channel {
/* 196 */     final NavigableMap<Float, float[]> keys = (NavigableMap)new TreeMap<>();
/*     */     float[] constant;
/*     */     
/*     */     boolean isEmpty() {
/* 200 */       return (this.constant == null && this.keys.isEmpty());
/*     */     }
/*     */     
/*     */     float[] sample(float time, float[] fallback) {
/* 204 */       if (this.constant != null) return this.constant; 
/* 205 */       if (this.keys.isEmpty()) return fallback; 
/* 206 */       if (this.keys.size() == 1) return (float[])this.keys.firstEntry().getValue();
/*     */       
/* 208 */       Map.Entry<Float, float[]> first = (Map.Entry)this.keys.firstEntry();
/* 209 */       Map.Entry<Float, float[]> last = (Map.Entry)this.keys.lastEntry();
/* 210 */       if (time <= ((Float)first.getKey()).floatValue()) return first.getValue(); 
/* 211 */       if (time >= ((Float)last.getKey()).floatValue()) return last.getValue();
/*     */       
/* 213 */       Map.Entry<Float, float[]> a = (Map.Entry)this.keys.floorEntry(Float.valueOf(time));
/* 214 */       Map.Entry<Float, float[]> b = (Map.Entry)this.keys.ceilingEntry(Float.valueOf(time));
/* 215 */       if (a == null) return first.getValue(); 
/* 216 */       if (b == null) return last.getValue(); 
/* 217 */       if (Objects.equals(a.getKey(), b.getKey())) return a.getValue();
/*     */       
/* 219 */       float q = (time - ((Float)a.getKey()).floatValue()) / (((Float)b.getKey()).floatValue() - ((Float)a.getKey()).floatValue());
/* 220 */       q = Math.max(0.0F, Math.min(1.0F, q));
/* 221 */       float[] av = a.getValue();
/* 222 */       float[] bv = b.getValue();
/* 223 */       return new float[] { av[0] + q * (bv[0] - av[0]), av[1] + q * (bv[1] - av[1]), av[2] + q * (bv[2] - av[2]) };
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\PulseSourceModel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */