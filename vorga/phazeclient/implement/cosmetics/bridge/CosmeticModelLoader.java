/*     */ package vorga.phazeclient.implement.cosmetics.bridge;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.io.InputStream;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class CosmeticModelLoader {
/*  10 */   private static final Map<Integer, ParsedCosmeticModel> CACHE = new HashMap<>();
/*     */   
/*     */   public static ParsedCosmeticModel get(CosmeticEntry entry) {
/*  13 */     if (entry == null || entry.kind() != CosmeticKind.GEOMETRY || entry.modelResource() == null) return null; 
/*  14 */     return CACHE.computeIfAbsent(Integer.valueOf(entry.id()), id -> load(entry));
/*     */   }
/*     */   private static ParsedCosmeticModel load(CosmeticEntry entry) {
/*     */     
/*  18 */     try { InputStream in = CosmeticModelLoader.class.getClassLoader().getResourceAsStream(entry.modelResource()); 
/*  19 */       try { if (in == null) { ParsedCosmeticModel parsedCosmeticModel1 = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  70 */           if (in != null) in.close();  return parsedCosmeticModel1; }  JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject(); float globalScale = f(root, "scale", 1.0F); ParsedCosmeticModel.Vec3 globalOffset = new ParsedCosmeticModel.Vec3(f(root, "x", 0.0F), f(root, "y", 0.0F), f(root, "z", 0.0F)); ParsedCosmeticModel.Vec3 globalRotation = new ParsedCosmeticModel.Vec3(f(root, "pitch", 0.0F), f(root, "yaw", 0.0F), f(root, "roll", 0.0F)); JsonObject modelObj = obj(root, "model"); JsonArray geoms = (modelObj == null) ? null : arr(modelObj, "minecraft:geometry"); if (geoms == null || geoms.size() == 0) { ParsedCosmeticModel parsedCosmeticModel1 = null; if (in != null) in.close();  return parsedCosmeticModel1; }  JsonObject geom = geoms.get(0).getAsJsonObject(); JsonObject description = obj(geom, "description"); float texW = (description == null) ? Math.max(1, entry.textureWidth()) : f(description, "texture_width", Math.max(1, entry.textureWidth())); float texH = (description == null) ? Math.max(1, entry.textureHeight()) : f(description, "texture_height", Math.max(1, entry.textureHeight())); Map<String, ParsedCosmeticModel.BoneStyle> styles = parseStyles(root); LinkedHashMap<String, ParsedCosmeticModel.Bone> bones = new LinkedHashMap<>(); JsonArray boneArray = arr(geom, "bones"); if (boneArray != null) for (JsonElement element : boneArray) { if (!element.isJsonObject()) continue;  JsonObject b = element.getAsJsonObject(); String name = s(b, "name", "bone_" + bones.size()); String parent = s(b, "parent", ""); ParsedCosmeticModel.Vec3 pivot = vec(b.get("pivot"), ParsedCosmeticModel.Vec3.ZERO); ParsedCosmeticModel.Vec3 rotation = vec(b.get("rotation"), ParsedCosmeticModel.Vec3.ZERO); List<ParsedCosmeticModel.Cube> cubes = new ArrayList<>(); JsonArray cubeArray = arr(b, "cubes"); if (cubeArray != null) for (JsonElement ce : cubeArray) { if (!ce.isJsonObject()) continue;  JsonObject c = ce.getAsJsonObject(); ParsedCosmeticModel.Vec3 origin = vec(c.get("origin"), ParsedCosmeticModel.Vec3.ZERO); ParsedCosmeticModel.Vec3 size = vec(c.get("size"), ParsedCosmeticModel.Vec3.ZERO); ParsedCosmeticModel.Vec3 cpivot = vec(c.get("pivot"), pivot); ParsedCosmeticModel.Vec3 crot = vec(c.get("rotation"), ParsedCosmeticModel.Vec3.ZERO); float inflate = f(c, "inflate", 0.0F); EnumMap<ParsedCosmeticModel.Side, ParsedCosmeticModel.Face> faces = parseFaces(c.get("uv"), size); cubes.add(new ParsedCosmeticModel.Cube(origin, size, cpivot, crot, inflate, faces)); }   bones.put(name, new ParsedCosmeticModel.Bone(name, parent, pivot, rotation, cubes, styles.getOrDefault(name, ParsedCosmeticModel.BoneStyle.DEFAULT))); }   Map<String, ParsedCosmeticModel.Animation> animations = parseAnimations(root); ParsedCosmeticModel parsedCosmeticModel = new ParsedCosmeticModel(texW, texH, globalScale, globalOffset, globalRotation, bones, animations); if (in != null) in.close();  return parsedCosmeticModel; } catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception e)
/*  71 */     { System.err.println("[PulseCosmetics] Failed loading " + entry.name() + ": " + String.valueOf(e));
/*  72 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   private static Map<String, ParsedCosmeticModel.BoneStyle> parseStyles(JsonObject root) {
/*  77 */     Map<String, ParsedCosmeticModel.BoneStyle> out = new HashMap<>();
/*  78 */     JsonArray models = arr(root, "models");
/*  79 */     if (models == null) return out;
/*     */     
/*  81 */     for (JsonElement e : models) {
/*  82 */       if (!e.isJsonObject())
/*  83 */         continue;  JsonObject o = e.getAsJsonObject();
/*  84 */       String name = s(o, "name", "");
/*  85 */       if (name.isBlank())
/*  86 */         continue;  boolean visible = (!o.has("visible") || o.get("visible").getAsBoolean());
/*  87 */       float scale = f(o, "scale", 1.0F);
/*  88 */       ParsedCosmeticModel.Vec3 offset = new ParsedCosmeticModel.Vec3(f(o, "x", 0.0F), f(o, "y", 0.0F), f(o, "z", 0.0F));
/*  89 */       ParsedCosmeticModel.Vec3 rotation = new ParsedCosmeticModel.Vec3(f(o, "pitch", 0.0F), f(o, "yaw", 0.0F), f(o, "roll", 0.0F));
/*  90 */       int color = o.has("color") ? o.get("color").getAsInt() : 16777215;
/*  91 */       out.put(name, new ParsedCosmeticModel.BoneStyle(visible, scale, offset, rotation, color));
/*     */     } 
/*  93 */     return out;
/*     */   }
/*     */   
/*     */   private static EnumMap<ParsedCosmeticModel.Side, ParsedCosmeticModel.Face> parseFaces(JsonElement uv, ParsedCosmeticModel.Vec3 size) {
/*  97 */     EnumMap<ParsedCosmeticModel.Side, ParsedCosmeticModel.Face> out = new EnumMap<>(ParsedCosmeticModel.Side.class);
/*  98 */     if (uv == null || uv.isJsonNull()) return out;
/*     */     
/* 100 */     if (uv.isJsonObject()) {
/* 101 */       JsonObject o = uv.getAsJsonObject();
/* 102 */       addFace(out, ParsedCosmeticModel.Side.NORTH, o.get("north"));
/* 103 */       addFace(out, ParsedCosmeticModel.Side.SOUTH, o.get("south"));
/* 104 */       addFace(out, ParsedCosmeticModel.Side.EAST, o.get("east"));
/* 105 */       addFace(out, ParsedCosmeticModel.Side.WEST, o.get("west"));
/* 106 */       addFace(out, ParsedCosmeticModel.Side.UP, o.get("up"));
/* 107 */       addFace(out, ParsedCosmeticModel.Side.DOWN, o.get("down"));
/* 108 */       return out;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     if (uv.isJsonArray() && uv.getAsJsonArray().size() >= 2) {
/* 113 */       JsonArray a = uv.getAsJsonArray();
/* 114 */       float u = a.get(0).getAsFloat();
/* 115 */       float v = a.get(1).getAsFloat();
/* 116 */       float sx = Math.abs(size.x());
/* 117 */       float sy = Math.abs(size.y());
/* 118 */       float sz = Math.abs(size.z());
/* 119 */       out.put(ParsedCosmeticModel.Side.NORTH, new ParsedCosmeticModel.Face(u + sz, v + sz, sx, sy));
/* 120 */       out.put(ParsedCosmeticModel.Side.SOUTH, new ParsedCosmeticModel.Face(u + sz + sx + sz, v + sz, sx, sy));
/* 121 */       out.put(ParsedCosmeticModel.Side.WEST, new ParsedCosmeticModel.Face(u, v + sz, sz, sy));
/* 122 */       out.put(ParsedCosmeticModel.Side.EAST, new ParsedCosmeticModel.Face(u + sz + sx, v + sz, sz, sy));
/* 123 */       out.put(ParsedCosmeticModel.Side.UP, new ParsedCosmeticModel.Face(u + sz, v, sx, sz));
/* 124 */       out.put(ParsedCosmeticModel.Side.DOWN, new ParsedCosmeticModel.Face(u + sz + sx, v, sx, sz));
/*     */     } 
/* 126 */     return out;
/*     */   }
/*     */   
/*     */   private static void addFace(EnumMap<ParsedCosmeticModel.Side, ParsedCosmeticModel.Face> out, ParsedCosmeticModel.Side side, JsonElement element) {
/* 130 */     if (element == null || !element.isJsonObject())
/* 131 */       return;  JsonObject o = element.getAsJsonObject();
/* 132 */     ParsedCosmeticModel.Vec3 uv = vec2(o.get("uv"));
/* 133 */     ParsedCosmeticModel.Vec3 sz = vec2(o.get("uv_size"));
/* 134 */     out.put(side, new ParsedCosmeticModel.Face(uv.x(), uv.y(), sz.x(), sz.y()));
/*     */   }
/*     */   
/*     */   private static Map<String, ParsedCosmeticModel.Animation> parseAnimations(JsonObject root) {
/* 138 */     Map<String, ParsedCosmeticModel.Animation> out = new LinkedHashMap<>();
/* 139 */     JsonObject animationRoot = obj(root, "animation");
/* 140 */     JsonObject animations = (animationRoot == null) ? null : obj(animationRoot, "animations");
/* 141 */     if (animations == null) return out;
/*     */     
/* 143 */     for (Map.Entry<String, JsonElement> ae : (Iterable<Map.Entry<String, JsonElement>>)animations.entrySet()) {
/* 144 */       if (!((JsonElement)ae.getValue()).isJsonObject())
/* 145 */         continue;  JsonObject a = ((JsonElement)ae.getValue()).getAsJsonObject();
/* 146 */       float length = f(a, "animation_length", 1.0F);
/* 147 */       Map<String, ParsedCosmeticModel.AnimatedBone> boneMap = new HashMap<>();
/* 148 */       JsonObject bones = obj(a, "bones");
/* 149 */       if (bones != null) {
/* 150 */         for (Map.Entry<String, JsonElement> be : (Iterable<Map.Entry<String, JsonElement>>)bones.entrySet()) {
/* 151 */           if (!((JsonElement)be.getValue()).isJsonObject())
/* 152 */             continue;  JsonObject bo = ((JsonElement)be.getValue()).getAsJsonObject();
/* 153 */           boneMap.put(be.getKey(), new ParsedCosmeticModel.AnimatedBone(
/* 154 */                 channel(bo.get("rotation"), ParsedCosmeticModel.Vec3.ZERO), 
/* 155 */                 channel(bo.get("position"), ParsedCosmeticModel.Vec3.ZERO), 
/* 156 */                 channel(bo.get("scale"), ParsedCosmeticModel.Vec3.ONE)));
/*     */         } 
/*     */       }
/*     */       
/* 160 */       out.put(ae.getKey(), new ParsedCosmeticModel.Animation(ae.getKey(), length, boneMap));
/*     */     } 
/* 162 */     return out;
/*     */   }
/*     */   
/*     */   private static ParsedCosmeticModel.Channel channel(JsonElement element, ParsedCosmeticModel.Vec3 defaultScalarBase) {
/* 166 */     ParsedCosmeticModel.Channel ch = new ParsedCosmeticModel.Channel();
/* 167 */     if (element == null || element.isJsonNull()) return ch;
/*     */     
/* 169 */     if (element.isJsonPrimitive()) {
/* 170 */       float v = number(element, defaultScalarBase.x());
/* 171 */       ch.constant = new ParsedCosmeticModel.Vec3(v, v, v);
/* 172 */       return ch;
/*     */     } 
/* 174 */     if (element.isJsonArray()) {
/* 175 */       ch.constant = vec(element, defaultScalarBase);
/* 176 */       return ch;
/*     */     } 
/* 178 */     if (!element.isJsonObject()) return ch;
/*     */     
/* 180 */     JsonObject o = element.getAsJsonObject();
/* 181 */     for (Map.Entry<String, JsonElement> e : (Iterable<Map.Entry<String, JsonElement>>)o.entrySet()) {
/*     */       try {
/* 183 */         float t = Float.parseFloat(e.getKey());
/* 184 */         ch.keys.put(Float.valueOf(t), vecKey(e.getValue(), defaultScalarBase));
/* 185 */       } catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */     
/* 188 */     return ch;
/*     */   }
/*     */   
/*     */   private static ParsedCosmeticModel.Vec3 vecKey(JsonElement e, ParsedCosmeticModel.Vec3 fallback) {
/* 192 */     if (e != null && e.isJsonObject()) {
/* 193 */       JsonObject o = e.getAsJsonObject();
/* 194 */       if (o.has("post")) return vec(o.get("post"), fallback); 
/* 195 */       if (o.has("pre")) return vec(o.get("pre"), fallback); 
/*     */     } 
/* 197 */     return vec(e, fallback);
/*     */   }
/*     */   
/*     */   private static ParsedCosmeticModel.Vec3 vec(JsonElement e, ParsedCosmeticModel.Vec3 fallback) {
/* 201 */     if (e == null || e.isJsonNull()) return fallback; 
/* 202 */     if (e.isJsonPrimitive()) {
/* 203 */       float n = number(e, fallback.x());
/* 204 */       return new ParsedCosmeticModel.Vec3(n, n, n);
/*     */     } 
/* 206 */     if (!e.isJsonArray()) return fallback; 
/* 207 */     JsonArray a = e.getAsJsonArray();
/* 208 */     float x = (a.size() > 0) ? number(a.get(0), fallback.x()) : fallback.x();
/* 209 */     float y = (a.size() > 1) ? number(a.get(1), fallback.y()) : fallback.y();
/* 210 */     float z = (a.size() > 2) ? number(a.get(2), fallback.z()) : fallback.z();
/* 211 */     return new ParsedCosmeticModel.Vec3(x, y, z);
/*     */   }
/*     */   
/*     */   private static ParsedCosmeticModel.Vec3 vec2(JsonElement e) {
/* 215 */     if (e == null || !e.isJsonArray()) return ParsedCosmeticModel.Vec3.ZERO; 
/* 216 */     JsonArray a = e.getAsJsonArray();
/* 217 */     return new ParsedCosmeticModel.Vec3(
/* 218 */         (a.size() > 0) ? number(a.get(0), 0.0F) : 0.0F, 
/* 219 */         (a.size() > 1) ? number(a.get(1), 0.0F) : 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float number(JsonElement e, float fallback) {
/*     */     try {
/* 226 */       if (e == null || e.isJsonNull()) return fallback; 
/* 227 */       if (e.isJsonPrimitive()) {
/* 228 */         JsonPrimitive p = e.getAsJsonPrimitive();
/* 229 */         if (p.isNumber()) return p.getAsFloat(); 
/* 230 */         if (p.isString()) return Float.parseFloat(p.getAsString()); 
/*     */       } 
/* 232 */     } catch (Exception exception) {}
/*     */     
/* 234 */     return fallback;
/*     */   }
/*     */   
/*     */   private static JsonObject obj(JsonObject o, String key) {
/* 238 */     return (o != null && o.has(key) && o.get(key).isJsonObject()) ? o.getAsJsonObject(key) : null;
/*     */   }
/*     */   
/*     */   private static JsonArray arr(JsonObject o, String key) {
/* 242 */     return (o != null && o.has(key) && o.get(key).isJsonArray()) ? o.getAsJsonArray(key) : null;
/*     */   }
/*     */   
/*     */   private static float f(JsonObject o, String key, float fallback) {
/* 246 */     return (o != null && o.has(key)) ? number(o.get(key), fallback) : fallback;
/*     */   }
/*     */   
/*     */   private static String s(JsonObject o, String key, String fallback) {
/*     */     try {
/* 251 */       return (o != null && o.has(key)) ? o.get(key).getAsString() : fallback;
/* 252 */     } catch (Exception e) {
/* 253 */       return fallback;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\CosmeticModelLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */