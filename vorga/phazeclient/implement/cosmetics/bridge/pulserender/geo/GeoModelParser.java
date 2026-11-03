/*     */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class GeoModelParser {
/*     */   public static GeoModel parse(String var0) {
/*     */     try {
/*  12 */       JsonObject var1 = JsonParser.parseString(var0).getAsJsonObject();
/*  13 */       return parseModel(var1);
/*  14 */     } catch (Exception var2) {
/*  15 */       System.err.println("[PulseCosmetics] Failed to parse Bedrock model: " + String.valueOf(var2));
/*  16 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static GeoModel parseModel(JsonObject var0) {
/*  21 */     JsonArray var1 = var0.getAsJsonArray("minecraft:geometry");
/*  22 */     if (var1 != null && !var1.isEmpty()) {
/*  23 */       JsonObject var2 = var1.get(0).getAsJsonObject();
/*  24 */       JsonObject var3 = var2.getAsJsonObject("description");
/*  25 */       int var4 = var3.has("texture_width") ? var3.get("texture_width").getAsInt() : 64;
/*  26 */       int var5 = var3.has("texture_height") ? var3.get("texture_height").getAsInt() : 64;
/*  27 */       GeoModel var6 = new GeoModel();
/*  28 */       var6.textureWidth = var4;
/*  29 */       var6.textureHeight = var5;
/*  30 */       JsonArray var7 = var2.getAsJsonArray("bones");
/*  31 */       if (var7 != null) {
/*  32 */         HashMap<Object, Object> var8 = new HashMap<>();
/*     */         
/*  34 */         for (JsonElement var10 : var7) {
/*  35 */           JsonObject var11 = var10.getAsJsonObject();
/*  36 */           GeoBone var12 = parseBone(var11, var4, var5);
/*  37 */           var8.put(var12.name, var12);
/*     */         } 
/*     */         
/*  40 */         for (JsonElement var17 : var7) {
/*  41 */           JsonObject var18 = var17.getAsJsonObject();
/*  42 */           String var19 = var18.get("name").getAsString();
/*  43 */           GeoBone var13 = (GeoBone)var8.get(var19);
/*  44 */           if (var18.has("parent")) {
/*  45 */             String var14 = var18.get("parent").getAsString();
/*  46 */             GeoBone var15 = (GeoBone)var8.get(var14);
/*  47 */             if (var15 != null) {
/*  48 */               var15.childBones.add(var13);
/*  49 */               var13.parent = var15;
/*     */             }  continue;
/*     */           } 
/*  52 */           var6.topLevelBones.add(var13);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  57 */       return var6;
/*     */     } 
/*  59 */     System.err.println("[PulseCosmetics] No minecraft:geometry found in model");
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static GeoBone parseBone(JsonObject var0, int var1, int var2) {
/*  65 */     String var3 = var0.get("name").getAsString();
/*  66 */     GeoBone var4 = new GeoBone(var3);
/*  67 */     if (var0.has("pivot")) {
/*  68 */       JsonArray var5 = var0.getAsJsonArray("pivot");
/*  69 */       var4.rotationPointX = -var5.get(0).getAsFloat();
/*  70 */       var4.rotationPointY = var5.get(1).getAsFloat();
/*  71 */       var4.rotationPointZ = var5.get(2).getAsFloat();
/*     */     } 
/*     */     
/*  74 */     if (var0.has("rotation")) {
/*  75 */       JsonArray var10 = var0.getAsJsonArray("rotation");
/*  76 */       var4.setRotationX((float)Math.toRadians(-var10.get(0).getAsFloat()));
/*  77 */       var4.setRotationY((float)Math.toRadians(-var10.get(1).getAsFloat()));
/*  78 */       var4.setRotationZ((float)Math.toRadians(var10.get(2).getAsFloat()));
/*     */     } 
/*     */     
/*  81 */     if (var0.has("cubes")) {
/*  82 */       for (JsonElement var7 : var0.getAsJsonArray("cubes")) {
/*  83 */         JsonObject var8 = var7.getAsJsonObject();
/*  84 */         GeoCube var9 = parseCube(var8, var1, var2);
/*  85 */         var4.childCubes.add(var9);
/*     */       } 
/*     */     }
/*     */     
/*  89 */     return var4;
/*     */   }
/*     */   
/*     */   private static GeoCube parseCube(JsonObject var0, int var1, int var2) {
/*  93 */     float[] var3 = parseFloatArray(var0, "origin", new float[] { 0.0F, 0.0F, 0.0F });
/*  94 */     float[] var4 = parseFloatArray(var0, "size", new float[] { 1.0F, 1.0F, 1.0F });
/*  95 */     float[] var5 = parseFloatArray(var0, "pivot", (float[])var3.clone());
/*  96 */     float[] var6 = parseFloatArray(var0, "rotation", new float[] { 0.0F, 0.0F, 0.0F });
/*  97 */     float var7 = var0.has("inflate") ? var0.get("inflate").getAsFloat() : 0.0F;
/*  98 */     boolean var8 = (var0.has("mirror") && var0.get("mirror").getAsBoolean());
/*  99 */     GeoCube var9 = new GeoCube(var4[0], var4[1], var4[2]);
/* 100 */     var9.pivot = new Vec3F(-var5[0], var5[1], var5[2]);
/* 101 */     var9.rotation = new Vec3F((float)Math.toRadians(-var6[0]), (float)Math.toRadians(-var6[1]), (float)Math.toRadians(var6[2]));
/* 102 */     var9.inflate = var7;
/* 103 */     var9.mirror = var8;
/* 104 */     buildCubeQuads(var9, var3, var4, var7, var8, var0, var1, var2);
/* 105 */     return var9;
/*     */   }
/*     */   
/*     */   private static void buildCubeQuads(GeoCube var0, float[] var1, float[] var2, float var3, boolean var4, JsonObject var5, int var6, int var7) {
/* 109 */     float var8 = var1[0];
/* 110 */     float var9 = var1[1];
/* 111 */     float var10 = var1[2];
/* 112 */     float var11 = var2[0];
/* 113 */     float var12 = var2[1];
/* 114 */     float var13 = var2[2];
/* 115 */     var8 -= var3;
/* 116 */     var9 -= var3;
/* 117 */     var10 -= var3;
/* 118 */     var11 += var3 * 2.0F;
/* 119 */     var12 += var3 * 2.0F;
/* 120 */     var13 += var3 * 2.0F;
/* 121 */     float var14 = 0.0F;
/* 122 */     float var15 = 0.0F;
/* 123 */     boolean var16 = false;
/* 124 */     JsonObject var17 = null;
/* 125 */     if (var5.has("uv")) {
/* 126 */       JsonElement var18 = var5.get("uv");
/* 127 */       if (var18.isJsonArray()) {
/* 128 */         JsonArray var19 = var18.getAsJsonArray();
/* 129 */         var14 = var19.get(0).getAsFloat();
/* 130 */         var15 = var19.get(1).getAsFloat();
/* 131 */       } else if (var18.isJsonObject()) {
/* 132 */         var16 = true;
/* 133 */         var17 = var18.getAsJsonObject();
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     float var32 = -(var8 + var11) / 16.0F;
/* 138 */     float var33 = var9 / 16.0F;
/* 139 */     float var20 = var10 / 16.0F;
/* 140 */     float var21 = -var8 / 16.0F;
/* 141 */     float var22 = (var9 + var12) / 16.0F;
/* 142 */     float var23 = (var10 + var13) / 16.0F;
/* 143 */     if (var16 && var17 != null) {
/* 144 */       var0.quads[0] = buildQuadPerFace(var17, "west", var32, var33, var20, var32, var22, var23, -1.0F, 0.0F, 0.0F, var6, var7);
/* 145 */       var0.quads[1] = buildQuadPerFace(var17, "east", var21, var33, var20, var21, var22, var23, 1.0F, 0.0F, 0.0F, var6, var7);
/* 146 */       var0.quads[2] = buildQuadPerFace(var17, "down", var32, var33, var20, var21, var33, var23, 0.0F, -1.0F, 0.0F, var6, var7);
/* 147 */       var0.quads[3] = buildQuadPerFace(var17, "up", var32, var22, var20, var21, var22, var23, 0.0F, 1.0F, 0.0F, var6, var7);
/* 148 */       var0.quads[4] = buildQuadPerFace(var17, "north", var32, var33, var20, var21, var22, var20, 0.0F, 0.0F, -1.0F, var6, var7);
/* 149 */       var0.quads[5] = buildQuadPerFace(var17, "south", var32, var33, var23, var21, var22, var23, 0.0F, 0.0F, 1.0F, var6, var7);
/*     */     } else {
/* 151 */       float var24 = var6;
/* 152 */       float var25 = var7;
/* 153 */       var0.quads[0] = buildQuadBox(var32, var33, var20, var32, var22, var23, -1.0F, 0.0F, 0.0F, var14, var15, var13, var12, var11, var24, var25, "west");
/* 154 */       var0.quads[1] = buildQuadBox(var21, var33, var20, var21, var22, var23, 1.0F, 0.0F, 0.0F, var14, var15, var13, var12, var11, var24, var25, "east");
/* 155 */       var0.quads[2] = buildQuadBox(var32, var33, var20, var21, var33, var23, 0.0F, -1.0F, 0.0F, var14, var15, var13, var12, var11, var24, var25, "down");
/* 156 */       var0.quads[3] = buildQuadBox(var32, var22, var20, var21, var22, var23, 0.0F, 1.0F, 0.0F, var14, var15, var13, var12, var11, var24, var25, "up");
/* 157 */       var0.quads[4] = buildQuadBox(var32, var33, var20, var21, var22, var20, 0.0F, 0.0F, -1.0F, var14, var15, var13, var12, var11, var24, var25, "north");
/* 158 */       var0.quads[5] = buildQuadBox(var32, var33, var23, var21, var22, var23, 0.0F, 0.0F, 1.0F, var14, var15, var13, var12, var11, var24, var25, "south");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GeoQuad buildQuadPerFace(JsonObject var0, String var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11, int var12) {
/* 177 */     if (!var0.has(var1)) {
/* 178 */       return null;
/*     */     }
/* 180 */     JsonObject var13 = var0.getAsJsonObject(var1);
/* 181 */     JsonArray var14 = var13.getAsJsonArray("uv");
/* 182 */     JsonArray var15 = var13.getAsJsonArray("uv_size");
/* 183 */     float var16 = var14.get(0).getAsFloat() / var11;
/* 184 */     float var17 = var14.get(1).getAsFloat() / var12;
/* 185 */     float var18 = var15.get(0).getAsFloat() / var11;
/* 186 */     float var19 = var15.get(1).getAsFloat() / var12;
/* 187 */     GeoVertex[] var20 = buildFaceVertices(var1, var2, var3, var4, var5, var6, var7, var16, var17, var18, var19);
/* 188 */     return new GeoQuad(var20, var8, var9, var10);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GeoQuad buildQuadBox(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, String var16) {
/*     */     String str;
/* 215 */     switch (var16)
/*     */     { case "north":
/* 217 */         var17 = (var9 + var11 + var13) / var14;
/* 218 */         var18 = (var10 + var11) / var15;
/* 219 */         var19 = var13 / var14;
/* 220 */         var20 = var12 / var15;
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
/* 259 */         var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20);
/* 260 */         return new GeoQuad(var21, var6, var7, var8);case "south": var17 = (var9 + var11 + var13 + var11) / var14; var18 = (var10 + var11) / var15; var19 = var13 / var14; var20 = var12 / var15; var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20); return new GeoQuad(var21, var6, var7, var8);case "east": var17 = var9 / var14; var18 = (var10 + var11) / var15; var19 = var11 / var14; var20 = var12 / var15; var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20); return new GeoQuad(var21, var6, var7, var8);case "west": var17 = (var9 + var11 + var13) / var14; var18 = (var10 + var11) / var15; var19 = var11 / var14; var20 = var12 / var15; var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20); return new GeoQuad(var21, var6, var7, var8);case "up": var17 = (var9 + var11) / var14; var18 = var10 / var15; var19 = var13 / var14; var20 = var11 / var15; var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20); return new GeoQuad(var21, var6, var7, var8);case "down": var17 = (var9 + var11 + var13) / var14; var18 = var10 / var15; var19 = var13 / var14; var20 = var11 / var15; var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20); return new GeoQuad(var21, var6, var7, var8); }  float var17 = 0.0F; float var18 = 0.0F; float var19 = 0.0F; float var20 = 0.0F; GeoVertex[] var21 = buildFaceVertices(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20); return new GeoQuad(var21, var6, var7, var8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static GeoVertex[] buildFaceVertices(String var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10) {
/* 266 */     GeoVertex[] var11 = new GeoVertex[4];
/* 267 */     float var12 = var7 + var9;
/* 268 */     float var13 = var8 + var10;
/* 269 */     switch (var0) {
/*     */       case "north":
/* 271 */         var11[0] = new GeoVertex(var4, var5, var3, var7, var8);
/* 272 */         var11[1] = new GeoVertex(var1, var5, var3, var12, var8);
/* 273 */         var11[2] = new GeoVertex(var1, var2, var3, var12, var13);
/* 274 */         var11[3] = new GeoVertex(var4, var2, var3, var7, var13);
/*     */         break;
/*     */       case "south":
/* 277 */         var11[0] = new GeoVertex(var1, var5, var6, var7, var8);
/* 278 */         var11[1] = new GeoVertex(var4, var5, var6, var12, var8);
/* 279 */         var11[2] = new GeoVertex(var4, var2, var6, var12, var13);
/* 280 */         var11[3] = new GeoVertex(var1, var2, var6, var7, var13);
/*     */         break;
/*     */       case "east":
/* 283 */         var11[0] = new GeoVertex(var4, var5, var6, var7, var8);
/* 284 */         var11[1] = new GeoVertex(var4, var5, var3, var12, var8);
/* 285 */         var11[2] = new GeoVertex(var4, var2, var3, var12, var13);
/* 286 */         var11[3] = new GeoVertex(var4, var2, var6, var7, var13);
/*     */         break;
/*     */       case "west":
/* 289 */         var11[0] = new GeoVertex(var1, var5, var3, var7, var8);
/* 290 */         var11[1] = new GeoVertex(var1, var5, var6, var12, var8);
/* 291 */         var11[2] = new GeoVertex(var1, var2, var6, var12, var13);
/* 292 */         var11[3] = new GeoVertex(var1, var2, var3, var7, var13);
/*     */         break;
/*     */       case "up":
/* 295 */         var11[0] = new GeoVertex(var1, var5, var3, var7, var8);
/* 296 */         var11[1] = new GeoVertex(var1, var5, var6, var7, var13);
/* 297 */         var11[2] = new GeoVertex(var4, var5, var6, var12, var13);
/* 298 */         var11[3] = new GeoVertex(var4, var5, var3, var12, var8);
/*     */         break;
/*     */       case "down":
/* 301 */         var11[0] = new GeoVertex(var4, var2, var3, var7, var8);
/* 302 */         var11[1] = new GeoVertex(var4, var2, var6, var7, var13);
/* 303 */         var11[2] = new GeoVertex(var1, var2, var6, var12, var13);
/* 304 */         var11[3] = new GeoVertex(var1, var2, var3, var12, var8);
/*     */         break;
/*     */     } 
/* 307 */     return var11;
/*     */   }
/*     */   
/*     */   private static float[] parseFloatArray(JsonObject var0, String var1, float[] var2) {
/* 311 */     if (!var0.has(var1)) {
/* 312 */       return var2;
/*     */     }
/* 314 */     JsonArray var3 = var0.getAsJsonArray(var1);
/* 315 */     float[] var4 = new float[var3.size()];
/*     */     
/* 317 */     for (int var5 = 0; var5 < var3.size(); var5++) {
/* 318 */       var4[var5] = var3.get(var5).getAsFloat();
/*     */     }
/*     */     
/* 321 */     return var4;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\GeoModelParser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */