/*     */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.CosmeticEntry;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.CosmeticKind;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoModel;
/*     */ import vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo.GeoModelParser;
/*     */ 
/*     */ public final class PulseSourceModelLoader
/*     */ {
/*  20 */   private static final Map<Integer, PulseSourceModel> CACHE = new ConcurrentHashMap<>();
/*     */   
/*     */   static PulseSourceModel get(CosmeticEntry entry) {
/*  23 */     if (entry == null || entry.kind() != CosmeticKind.GEOMETRY || entry.modelResource() == null) return null; 
/*  24 */     return CACHE.computeIfAbsent(Integer.valueOf(entry.id()), ignored -> load(entry));
/*     */   }
/*     */   private static PulseSourceModel load(CosmeticEntry entry) {
/*     */     
/*  28 */     try { InputStream in = PulseSourceModelLoader.class.getClassLoader().getResourceAsStream(entry.modelResource()); 
/*  29 */       try { if (in == null) { PulseSourceModel pulseSourceModel1 = null;
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
/*  51 */           if (in != null) in.close();  return pulseSourceModel1; }  JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject(); JsonObject modelObject = object(root, "model"); if (modelObject == null) { PulseSourceModel pulseSourceModel1 = null; if (in != null) in.close();  return pulseSourceModel1; }  GeoModel geo = GeoModelParser.parse(modelObject.toString()); if (geo == null) { PulseSourceModel pulseSourceModel1 = null; if (in != null) in.close();  return pulseSourceModel1; }  PulseSourceModel pulseSourceModel = new PulseSourceModel(entry.id(), string(root, "name", entry.name()), integer(root, "pos", 1), number(root, "scale", 1.0F), number(root, "x", 0.0F), number(root, "y", 0.0F), number(root, "z", 0.0F), number(root, "yaw", 0.0F), number(root, "pitch", 0.0F), number(root, "roll", 0.0F), geo, parseAnimations(root)); if (in != null) in.close();  return pulseSourceModel; } catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception e)
/*  52 */     { System.err.println("[PulseCosmetics] Pulse renderer failed to load " + entry.name() + ": " + String.valueOf(e));
/*  53 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   private static Map<String, PulseSourceModel.AnimationData> parseAnimations(JsonObject root) {
/*  58 */     Map<String, PulseSourceModel.AnimationData> result = new LinkedHashMap<>();
/*  59 */     JsonObject animationRoot = object(root, "animation");
/*  60 */     JsonObject animations = (animationRoot == null) ? null : object(animationRoot, "animations");
/*  61 */     if (animations == null) return result;
/*     */     
/*  63 */     for (Map.Entry<String, JsonElement> animEntry : (Iterable<Map.Entry<String, JsonElement>>)animations.entrySet()) {
/*  64 */       if (!((JsonElement)animEntry.getValue()).isJsonObject())
/*  65 */         continue;  JsonObject animation = ((JsonElement)animEntry.getValue()).getAsJsonObject();
/*  66 */       boolean loop = bool(animation, "loop", true);
/*  67 */       float length = number(animation, "animation_length", 1.0F);
/*  68 */       Map<String, PulseSourceModel.BoneAnimationData> bones = new LinkedHashMap<>();
/*     */       
/*  70 */       JsonObject boneObject = object(animation, "bones");
/*  71 */       if (boneObject != null) {
/*  72 */         for (Map.Entry<String, JsonElement> boneEntry : (Iterable<Map.Entry<String, JsonElement>>)boneObject.entrySet()) {
/*  73 */           if (!((JsonElement)boneEntry.getValue()).isJsonObject())
/*  74 */             continue;  JsonObject b = ((JsonElement)boneEntry.getValue()).getAsJsonObject();
/*  75 */           bones.put(boneEntry.getKey(), new PulseSourceModel.BoneAnimationData(
/*  76 */                 parseChannel(b.get("rotation"), new float[] { 0.0F, 0.0F, 0.0F
/*  77 */                   }), parseChannel(b.get("position"), new float[] { 0.0F, 0.0F, 0.0F
/*  78 */                   }), parseChannel(b.get("scale"), new float[] { 1.0F, 1.0F, 1.0F })));
/*     */         } 
/*     */       }
/*     */       
/*  82 */       result.put(animEntry.getKey(), new PulseSourceModel.AnimationData(animEntry.getKey(), loop, length, bones));
/*     */     } 
/*  84 */     return result;
/*     */   }
/*     */   
/*     */   private static PulseSourceModel.Channel parseChannel(JsonElement element, float[] fallback) {
/*  88 */     PulseSourceModel.Channel channel = new PulseSourceModel.Channel();
/*  89 */     if (element == null || element.isJsonNull()) return channel;
/*     */     
/*  91 */     if (element.isJsonArray() || element.isJsonPrimitive()) {
/*  92 */       channel.constant = vector(element, fallback);
/*  93 */       return channel;
/*     */     } 
/*  95 */     if (!element.isJsonObject()) return channel;
/*     */     
/*  97 */     JsonObject object = element.getAsJsonObject();
/*  98 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)object.entrySet()) {
/*     */       try {
/* 100 */         float t = Float.parseFloat(entry.getKey());
/* 101 */         channel.keys.put(Float.valueOf(t), keyVector(entry.getValue(), fallback));
/* 102 */       } catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */     
/* 105 */     return channel;
/*     */   }
/*     */   
/*     */   private static float[] keyVector(JsonElement value, float[] fallback) {
/* 109 */     if (value != null && value.isJsonObject()) {
/* 110 */       JsonObject o = value.getAsJsonObject();
/* 111 */       if (o.has("post")) return vector(o.get("post"), fallback); 
/* 112 */       if (o.has("pre")) return vector(o.get("pre"), fallback); 
/* 113 */       if (o.has("vector")) return vector(o.get("vector"), fallback); 
/*     */     } 
/* 115 */     return vector(value, fallback);
/*     */   }
/*     */   
/*     */   private static float[] vector(JsonElement element, float[] fallback) {
/* 119 */     if (element == null || element.isJsonNull()) return (float[])fallback.clone(); 
/* 120 */     if (element.isJsonPrimitive()) {
/*     */       float n; 
/* 122 */       try { n = element.getAsFloat(); } catch (Exception e) { return (float[])fallback.clone(); }
/* 123 */        return new float[] { n, n, n };
/*     */     } 
/* 125 */     if (!element.isJsonArray()) return (float[])fallback.clone(); 
/* 126 */     JsonArray a = element.getAsJsonArray();
/* 127 */     float[] result = (float[])fallback.clone();
/* 128 */     for (int i = 0; i < Math.min(3, a.size()); i++) { 
/* 129 */       try { result[i] = a.get(i).getAsFloat(); } catch (Exception exception) {} }
/*     */     
/* 131 */     return result;
/*     */   }
/*     */   
/*     */   private static JsonObject object(JsonObject root, String key) {
/* 135 */     return (root != null && root.has(key) && root.get(key).isJsonObject()) ? root.getAsJsonObject(key) : null;
/*     */   }
/*     */   private static float number(JsonObject root, String key, float fallback) {
/*     */     
/* 139 */     try { return root.has(key) ? root.get(key).getAsFloat() : fallback; } catch (Exception e) { return fallback; }
/*     */   
/*     */   } private static int integer(JsonObject root, String key, int fallback) {
/*     */     
/* 143 */     try { return root.has(key) ? root.get(key).getAsInt() : fallback; } catch (Exception e) { return fallback; }
/*     */   
/*     */   } private static boolean bool(JsonObject root, String key, boolean fallback) {
/*     */     
/* 147 */     try { return root.has(key) ? root.get(key).getAsBoolean() : fallback; } catch (Exception e) { return fallback; }
/*     */   
/*     */   } private static String string(JsonObject root, String key, String fallback) {
/*     */     
/* 151 */     try { return root.has(key) ? root.get(key).getAsString() : fallback; } catch (Exception e) { return fallback; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\PulseSourceModelLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */