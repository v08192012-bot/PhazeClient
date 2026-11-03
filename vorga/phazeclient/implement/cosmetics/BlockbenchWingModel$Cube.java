/*      */ package vorga.phazeclient.implement.cosmetics;
/*      */ 
/*      */ import com.google.gson.JsonArray;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.google.gson.JsonObject;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import net.minecraft.class_1921;
/*      */ import net.minecraft.class_4587;
/*      */ import net.minecraft.class_4588;
/*      */ import net.minecraft.class_4597;
/*      */ import net.minecraft.class_4608;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Vector3f;
/*      */ import org.joml.Vector3fc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class Cube
/*      */ {
/*      */   private final String name;
/*      */   private final float x1;
/*      */   private final float y1;
/*      */   private final float z1;
/*      */   private final float x2;
/*      */   private final float y2;
/*      */   private final float z2;
/*      */   private final float ox;
/*      */   private final float oy;
/*      */   private final float oz;
/*      */   private final float rx;
/*      */   private final float ry;
/*      */   private final float rz;
/*      */   private final Map<String, BlockbenchWingModel.Face> faces;
/*      */   private final List<BlockbenchWingModel.MeshFace> meshFaces;
/*      */   private final boolean localMeshCoordinates;
/* 1018 */   private List<BlockbenchWingModel.Transform> parents = List.of();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Cube(String name, float x1, float y1, float z1, float x2, float y2, float z2, float ox, float oy, float oz, float rx, float ry, float rz, Map<String, BlockbenchWingModel.Face> faces, List<BlockbenchWingModel.MeshFace> meshFaces, boolean localMeshCoordinates) {
/* 1025 */     this.name = name;
/* 1026 */     this.x1 = x1; this.y1 = y1; this.z1 = z1;
/* 1027 */     this.x2 = x2; this.y2 = y2; this.z2 = z2;
/* 1028 */     this.ox = ox; this.oy = oy; this.oz = oz;
/* 1029 */     this.rx = rx; this.ry = ry; this.rz = rz;
/* 1030 */     this.faces = faces;
/* 1031 */     this.meshFaces = meshFaces;
/* 1032 */     this.localMeshCoordinates = localMeshCoordinates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Cube read(JsonObject object, Map<String, Integer> textureIndices, boolean localMeshCoordinates) {
/* 1040 */     String type = BlockbenchWingModel.string(object, "type");
/* 1041 */     if ("mesh".equals(type) || "beveled_cuboid".equals(type)) {
/* 1042 */       return readMesh(object, textureIndices, true);
/*      */     }
/*      */ 
/*      */     
/* 1046 */     if (!"cube".equals(type)) return null; 
/* 1047 */     float[] from = BlockbenchWingModel.numbers(object.getAsJsonArray("from"), 3);
/* 1048 */     float[] to = BlockbenchWingModel.numbers(object.getAsJsonArray("to"), 3);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1053 */     float[] origin = BlockbenchWingModel.numbersOrDefault(object.getAsJsonArray("origin"), 3, 0.0F);
/* 1054 */     float[] rotation = BlockbenchWingModel.numbersOrDefault(object.getAsJsonArray("rotation"), 3, 0.0F);
/* 1055 */     if (from == null || to == null) return null; 
/* 1056 */     Map<String, BlockbenchWingModel.Face> faces = new HashMap<>();
/* 1057 */     JsonObject rawFaces = object.getAsJsonObject("faces");
/* 1058 */     if (rawFaces != null)
/* 1059 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)rawFaces.entrySet()) {
/* 1060 */         if (!((JsonElement)entry.getValue()).isJsonObject())
/* 1061 */           continue;  JsonObject raw = ((JsonElement)entry.getValue()).getAsJsonObject();
/* 1062 */         float[] uv = BlockbenchWingModel.numbers(raw.getAsJsonArray("uv"), 4);
/* 1063 */         Integer texture = textureIndices.get(BlockbenchWingModel.string(raw, "texture"));
/* 1064 */         if (uv != null && texture != null) faces.put(entry.getKey(), new BlockbenchWingModel.Face(uv, texture.intValue()));
/*      */       
/*      */       }  
/* 1067 */     return faces.isEmpty() ? null : new Cube(
/* 1068 */         BlockbenchWingModel.string(object, "name"), from[0], from[1], from[2], to[0], to[1], to[2], origin[0], origin[1], origin[2], rotation[0], rotation[1], rotation[2], faces, 
/*      */ 
/*      */         
/* 1071 */         List.of(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Cube readMesh(JsonObject object, Map<String, Integer> textureIndices, boolean localMeshCoordinates) {
/* 1079 */     JsonObject rawVertices = object.getAsJsonObject("vertices");
/* 1080 */     JsonObject rawFaces = object.getAsJsonObject("faces");
/* 1081 */     if (rawVertices == null || rawFaces == null) return null;
/*      */     
/* 1083 */     Map<String, float[]> positions = (Map)new HashMap<>();
/* 1084 */     float minX = Float.POSITIVE_INFINITY;
/* 1085 */     float minY = Float.POSITIVE_INFINITY;
/* 1086 */     float minZ = Float.POSITIVE_INFINITY;
/* 1087 */     float maxX = Float.NEGATIVE_INFINITY;
/* 1088 */     float maxY = Float.NEGATIVE_INFINITY;
/* 1089 */     float maxZ = Float.NEGATIVE_INFINITY;
/* 1090 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)rawVertices.entrySet()) {
/* 1091 */       if (!((JsonElement)entry.getValue()).isJsonArray())
/* 1092 */         continue;  float[] point = BlockbenchWingModel.numbers(((JsonElement)entry.getValue()).getAsJsonArray(), 3);
/* 1093 */       if (point == null)
/* 1094 */         continue;  positions.put(entry.getKey(), point);
/* 1095 */       minX = Math.min(minX, point[0]);
/* 1096 */       minY = Math.min(minY, point[1]);
/* 1097 */       minZ = Math.min(minZ, point[2]);
/* 1098 */       maxX = Math.max(maxX, point[0]);
/* 1099 */       maxY = Math.max(maxY, point[1]);
/* 1100 */       maxZ = Math.max(maxZ, point[2]);
/*      */     } 
/*      */     
/* 1103 */     List<BlockbenchWingModel.MeshFace> faces = new ArrayList<>();
/* 1104 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)rawFaces.entrySet()) {
/* 1105 */       if (!((JsonElement)entry.getValue()).isJsonObject())
/* 1106 */         continue;  JsonObject face = ((JsonElement)entry.getValue()).getAsJsonObject();
/* 1107 */       Integer texture = textureIndices.get(BlockbenchWingModel.string(face, "texture"));
/* 1108 */       JsonArray faceVertices = face.getAsJsonArray("vertices");
/* 1109 */       JsonObject rawUv = face.getAsJsonObject("uv");
/* 1110 */       if (texture == null || faceVertices == null || rawUv == null || faceVertices
/* 1111 */         .size() < 3) {
/*      */         continue;
/*      */       }
/* 1114 */       List<BlockbenchWingModel.MeshVertex> vertices = new ArrayList<>(faceVertices.size());
/* 1115 */       for (JsonElement vertexElement : faceVertices) {
/* 1116 */         String vertexId = vertexElement.getAsString();
/* 1117 */         float[] point = positions.get(vertexId);
/* 1118 */         JsonElement uvElement = rawUv.get(vertexId);
/*      */ 
/*      */         
/* 1121 */         float[] uv = (uvElement != null && uvElement.isJsonArray()) ? BlockbenchWingModel.numbers(uvElement.getAsJsonArray(), 2) : null;
/* 1122 */         if (point != null && uv != null) {
/* 1123 */           vertices.add(new BlockbenchWingModel.MeshVertex(point[0], point[1], point[2], uv[0], uv[1]));
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1128 */       if (vertices.size() >= 3) {
/* 1129 */         faces.add(new BlockbenchWingModel.MeshFace(texture
/* 1130 */               .intValue(), 
/* 1131 */               List.copyOf(orderMeshFace(vertices))));
/*      */       }
/*      */     } 
/*      */     
/* 1135 */     if (faces.isEmpty() || !Float.isFinite(minX)) return null; 
/* 1136 */     float[] origin = BlockbenchWingModel.numbersOrDefault(object.getAsJsonArray("origin"), 3, 0.0F);
/* 1137 */     float[] rotation = BlockbenchWingModel.numbersOrDefault(object.getAsJsonArray("rotation"), 3, 0.0F);
/* 1138 */     return new Cube(
/* 1139 */         BlockbenchWingModel.string(object, "name"), minX, minY, minZ, maxX, maxY, maxZ, origin[0], origin[1], origin[2], rotation[0], rotation[1], rotation[2], 
/*      */ 
/*      */ 
/*      */         
/* 1143 */         Map.of(), List.copyOf(faces), localMeshCoordinates);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<BlockbenchWingModel.MeshVertex> orderMeshFace(List<BlockbenchWingModel.MeshVertex> vertices) {
/* 1157 */     if (vertices.size() != 4) return vertices; 
/* 1158 */     Vector3f center = new Vector3f();
/* 1159 */     for (BlockbenchWingModel.MeshVertex vertex : vertices) {
/* 1160 */       center.add(vertex.x, vertex.y, vertex.z);
/*      */     }
/* 1162 */     center.mul(0.25F);
/*      */     
/* 1164 */     Vector3f referenceNormal = null;
/*      */     int a;
/* 1166 */     label36: for (a = 0; a < vertices.size() - 2; a++) {
/* 1167 */       for (int i = a + 1; i < vertices.size() - 1; i++) {
/* 1168 */         for (int j = i + 1; j < vertices.size(); j++) {
/* 1169 */           BlockbenchWingModel.MeshVertex va = vertices.get(a);
/* 1170 */           BlockbenchWingModel.MeshVertex vb = vertices.get(i);
/* 1171 */           BlockbenchWingModel.MeshVertex vc = vertices.get(j);
/*      */ 
/*      */           
/* 1174 */           Vector3f candidate = (new Vector3f(vb.x - va.x, vb.y - va.y, vb.z - va.z)).cross((Vector3fc)new Vector3f(vc.x - va.x, vc.y - va.y, vc.z - va.z));
/*      */ 
/*      */           
/* 1177 */           if (candidate.lengthSquared() > 1.0E-8F) {
/* 1178 */             referenceNormal = candidate.normalize();
/*      */             break label36;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1184 */     if (referenceNormal == null) return vertices;
/*      */     
/* 1186 */     BlockbenchWingModel.MeshVertex first = vertices.getFirst();
/* 1187 */     Vector3f axisU = new Vector3f(first.x - center.x, first.y - center.y, first.z - center.z);
/*      */ 
/*      */     
/* 1190 */     if (axisU.lengthSquared() < 1.0E-8F) return vertices; 
/* 1191 */     axisU.normalize();
/* 1192 */     Vector3f axisV = (new Vector3f((Vector3fc)referenceNormal)).cross((Vector3fc)axisU).normalize();
/*      */     
/* 1194 */     List<BlockbenchWingModel.MeshVertex> ordered = new ArrayList<>(vertices);
/* 1195 */     ordered.sort((left, right) -> {
/*      */           float leftAngle = faceAngle(left, center, axisU, axisV);
/*      */           
/*      */           float rightAngle = faceAngle(right, center, axisU, axisV);
/*      */           return Float.compare(leftAngle, rightAngle);
/*      */         });
/* 1201 */     BlockbenchWingModel.MeshVertex meshVertex1 = ordered.get(0);
/* 1202 */     BlockbenchWingModel.MeshVertex b = ordered.get(1);
/* 1203 */     BlockbenchWingModel.MeshVertex c = ordered.get(2);
/*      */ 
/*      */     
/* 1206 */     Vector3f sortedNormal = (new Vector3f(b.x - meshVertex1.x, b.y - meshVertex1.y, b.z - meshVertex1.z)).cross((Vector3fc)new Vector3f(c.x - meshVertex1.x, c.y - meshVertex1.y, c.z - meshVertex1.z));
/*      */ 
/*      */     
/* 1209 */     if (sortedNormal.dot((Vector3fc)referenceNormal) < 0.0F) {
/* 1210 */       Collections.reverse(ordered);
/*      */     }
/* 1212 */     return ordered;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float faceAngle(BlockbenchWingModel.MeshVertex vertex, Vector3f center, Vector3f axisU, Vector3f axisV) {
/* 1221 */     Vector3f offset = new Vector3f(vertex.x - center.x, vertex.y - center.y, vertex.z - center.z);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1226 */     return (float)Math.atan2(offset.dot((Vector3fc)axisV), offset.dot((Vector3fc)axisU));
/*      */   }
/*      */   
/*      */   boolean isMembrane() {
/* 1230 */     return this.name.toLowerCase(Locale.ROOT).contains("membrane");
/*      */   }
/*      */   
/*      */   boolean usesTexture(int textureIndex) {
/* 1234 */     for (BlockbenchWingModel.Face face : this.faces.values()) {
/* 1235 */       if (face.textureIndex == textureIndex) return true; 
/*      */     } 
/* 1237 */     for (BlockbenchWingModel.MeshFace face : this.meshFaces) {
/* 1238 */       if (face.textureIndex == textureIndex) return true; 
/*      */     } 
/* 1240 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void renderMembrane(class_4587 matrices, class_4597 consumers, List<BlockbenchWingModel.Texture> textures, int light, boolean uniformLighting, class_4587.class_4665 uniformLightingEntry, Map<String, Vector3f> markers, String[] markerNames) {
/* 1253 */     Vector3f a = markers.get(markerNames[0]);
/* 1254 */     Vector3f b = markers.get(markerNames[1]);
/* 1255 */     Vector3f c = markers.get(markerNames[2]);
/* 1256 */     Vector3f d = markers.get(markerNames[3]);
/* 1257 */     if (a == null || b == null || c == null || d == null)
/* 1258 */       return;  Matrix4f matrix = matrices.method_23760().method_23761();
/*      */     
/* 1260 */     Vector3f normal = (new Vector3f((Vector3fc)b)).sub((Vector3fc)a).cross((Vector3fc)(new Vector3f((Vector3fc)d)).sub((Vector3fc)a));
/* 1261 */     if (normal.lengthSquared() < 1.0E-8F) {
/* 1262 */       normal.set(0.0F, 0.0F, 1.0F);
/*      */     } else {
/* 1264 */       normal.normalize();
/*      */     } 
/* 1266 */     drawMembraneFace("north", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, a, b, c, d, normal.x, normal.y, normal.z);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1271 */     drawMembraneFace("south", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, d, c, b, a, -normal.x, -normal.y, -normal.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawMembraneFace(String side, Matrix4f matrix, class_4587 matrices, class_4597 consumers, List<BlockbenchWingModel.Texture> textures, int light, boolean uniformLighting, class_4587.class_4665 uniformLightingEntry, Vector3f a, Vector3f b, Vector3f c, Vector3f d, float nx, float ny, float nz) {
/* 1290 */     BlockbenchWingModel.Face face = this.faces.get(side);
/* 1291 */     if (face == null || face.textureIndex >= textures.size())
/* 1292 */       return;  BlockbenchWingModel.Texture texture = textures.get(face.textureIndex);
/* 1293 */     if (texture.id == null)
/* 1294 */       return;  float u0 = face.uv[0] / texture.width;
/* 1295 */     float v0 = face.uv[1] / texture.height;
/* 1296 */     float u1 = face.uv[2] / texture.width;
/* 1297 */     float v1 = face.uv[3] / texture.height;
/* 1298 */     class_4588 out = consumers.getBuffer(class_1921.method_23580(texture.id));
/* 1299 */     vertexWorld(out, matrix, matrices, uniformLightingEntry, a, u0, v1, light, nx, ny, nz, uniformLighting);
/* 1300 */     vertexWorld(out, matrix, matrices, uniformLightingEntry, b, u1, v1, light, nx, ny, nz, uniformLighting);
/* 1301 */     vertexWorld(out, matrix, matrices, uniformLightingEntry, c, u1, v0, light, nx, ny, nz, uniformLighting);
/* 1302 */     vertexWorld(out, matrix, matrices, uniformLightingEntry, d, u0, v0, light, nx, ny, nz, uniformLighting);
/*      */   }
/*      */ 
/*      */   
/*      */   void render(class_4587 matrices, class_4597 consumers, List<BlockbenchWingModel.Texture> textures, int light, boolean uniformLighting, class_4587.class_4665 uniformLightingEntry) {
/* 1307 */     if (!this.meshFaces.isEmpty()) {
/* 1308 */       renderMesh(matrices, consumers, textures, light, uniformLighting, uniformLightingEntry);
/*      */       return;
/*      */     } 
/* 1311 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 1312 */     drawFace("north", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x2, this.y1, this.z1, this.x1, this.y1, this.z1, this.x1, this.y2, this.z1, this.x2, this.y2, this.z1, 0.0F, 0.0F, -1.0F);
/* 1313 */     drawFace("south", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x1, this.y1, this.z2, this.x2, this.y1, this.z2, this.x2, this.y2, this.z2, this.x1, this.y2, this.z2, 0.0F, 0.0F, 1.0F);
/* 1314 */     drawFace("west", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x1, this.y1, this.z1, this.x1, this.y1, this.z2, this.x1, this.y2, this.z2, this.x1, this.y2, this.z1, -1.0F, 0.0F, 0.0F);
/* 1315 */     drawFace("east", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x2, this.y1, this.z2, this.x2, this.y1, this.z1, this.x2, this.y2, this.z1, this.x2, this.y2, this.z2, 1.0F, 0.0F, 0.0F);
/* 1316 */     drawFace("up", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x1, this.y2, this.z1, this.x1, this.y2, this.z2, this.x2, this.y2, this.z2, this.x2, this.y2, this.z1, 0.0F, 1.0F, 0.0F);
/* 1317 */     drawFace("down", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x1, this.y1, this.z2, this.x1, this.y1, this.z1, this.x2, this.y1, this.z1, this.x2, this.y1, this.z2, 0.0F, -1.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void renderCatalog(class_4587 matrices, class_4597 consumers, List<BlockbenchWingModel.Texture> textures, int light, boolean uniformLighting, class_4587.class_4665 uniformLightingEntry, boolean frontIsXAxis) {
/* 1323 */     if (!this.meshFaces.isEmpty()) {
/* 1324 */       renderMesh(matrices, consumers, textures, light, uniformLighting, uniformLightingEntry);
/*      */       return;
/*      */     } 
/* 1327 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 1328 */     if (frontIsXAxis) {
/* 1329 */       drawFace("west", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x1, this.y1, this.z1, this.x1, this.y1, this.z2, this.x1, this.y2, this.z2, this.x1, this.y2, this.z1, -1.0F, 0.0F, 0.0F);
/* 1330 */       drawFace("east", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x2, this.y1, this.z2, this.x2, this.y1, this.z1, this.x2, this.y2, this.z1, this.x2, this.y2, this.z2, 1.0F, 0.0F, 0.0F);
/*      */     } else {
/* 1332 */       drawFace("north", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x2, this.y1, this.z1, this.x1, this.y1, this.z1, this.x1, this.y2, this.z1, this.x2, this.y2, this.z1, 0.0F, 0.0F, -1.0F);
/* 1333 */       drawFace("south", matrix, matrices, consumers, textures, light, uniformLighting, uniformLightingEntry, this.x1, this.y1, this.z2, this.x2, this.y1, this.z2, this.x2, this.y2, this.z2, this.x1, this.y2, this.z2, 0.0F, 0.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderMesh(class_4587 matrices, class_4597 consumers, List<BlockbenchWingModel.Texture> textures, int light, boolean uniformLighting, class_4587.class_4665 uniformLightingEntry) {
/* 1345 */     Matrix4f matrix = matrices.method_23760().method_23761();
/* 1346 */     for (BlockbenchWingModel.MeshFace face : this.meshFaces) {
/* 1347 */       if (face.textureIndex < 0 || face.textureIndex >= textures.size())
/* 1348 */         continue;  BlockbenchWingModel.Texture texture = textures.get(face.textureIndex);
/* 1349 */       if (texture.id == null || face.vertices.size() < 3)
/* 1350 */         continue;  BlockbenchWingModel.MeshVertex a = face.vertices.get(0);
/* 1351 */       BlockbenchWingModel.MeshVertex b = face.vertices.get(1);
/* 1352 */       BlockbenchWingModel.MeshVertex c = face.vertices.get(2);
/*      */ 
/*      */       
/* 1355 */       BlockbenchWingModel.MeshVertex d = (face.vertices.size() >= 4) ? face.vertices.get(3) : c;
/*      */ 
/*      */       
/* 1358 */       Vector3f normal = (new Vector3f(b.x - a.x, b.y - a.y, b.z - a.z)).cross((Vector3fc)new Vector3f(c.x - a.x, c.y - a.y, c.z - a.z));
/*      */ 
/*      */       
/* 1361 */       if (normal.lengthSquared() < 1.0E-8F) {
/* 1362 */         normal.set(0.0F, 0.0F, 1.0F);
/*      */       } else {
/* 1364 */         normal.normalize();
/*      */       } 
/* 1366 */       class_4588 out = consumers.getBuffer(
/* 1367 */           class_1921.method_23580(texture.id));
/*      */       
/* 1369 */       meshVertex(out, matrix, matrices, uniformLightingEntry, a, texture, light, normal, uniformLighting);
/*      */       
/* 1371 */       meshVertex(out, matrix, matrices, uniformLightingEntry, b, texture, light, normal, uniformLighting);
/*      */       
/* 1373 */       meshVertex(out, matrix, matrices, uniformLightingEntry, c, texture, light, normal, uniformLighting);
/*      */       
/* 1375 */       meshVertex(out, matrix, matrices, uniformLightingEntry, d, texture, light, normal, uniformLighting);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void meshVertex(class_4588 out, Matrix4f matrix, class_4587 matrices, class_4587.class_4665 uniformLightingEntry, BlockbenchWingModel.MeshVertex vertex, BlockbenchWingModel.Texture texture, int light, Vector3f normal, boolean uniformLighting) {
/* 1391 */     vertex(out, matrix, matrices, uniformLightingEntry, vertex.x, vertex.y, vertex.z, vertex.u / texture.width, vertex.v / texture.height, light, normal.x, normal.y, normal.z, uniformLighting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawFace(String side, Matrix4f matrix, class_4587 matrices, class_4597 consumers, List<BlockbenchWingModel.Texture> textures, int light, boolean uniformLighting, class_4587.class_4665 uniformLightingEntry, float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz, float dx, float dy, float dz, float nx, float ny, float nz) {
/* 1405 */     BlockbenchWingModel.Face face = this.faces.get(side);
/* 1406 */     if (face == null || face.textureIndex >= textures.size())
/* 1407 */       return;  BlockbenchWingModel.Texture texture = textures.get(face.textureIndex);
/* 1408 */     if (texture.id == null)
/* 1409 */       return;  float u0 = face.uv[0] / texture.width;
/* 1410 */     float v0 = face.uv[1] / texture.height;
/* 1411 */     float u1 = face.uv[2] / texture.width;
/* 1412 */     float v1 = face.uv[3] / texture.height;
/* 1413 */     class_4588 out = consumers.getBuffer(class_1921.method_23580(texture.id));
/* 1414 */     vertex(out, matrix, matrices, uniformLightingEntry, ax, ay, az, u0, v1, light, nx, ny, nz, uniformLighting);
/* 1415 */     vertex(out, matrix, matrices, uniformLightingEntry, bx, by, bz, u1, v1, light, nx, ny, nz, uniformLighting);
/* 1416 */     vertex(out, matrix, matrices, uniformLightingEntry, cx, cy, cz, u1, v0, light, nx, ny, nz, uniformLighting);
/* 1417 */     vertex(out, matrix, matrices, uniformLightingEntry, dx, dy, dz, u0, v0, light, nx, ny, nz, uniformLighting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void vertex(class_4588 out, Matrix4f matrix, class_4587 matrices, class_4587.class_4665 uniformLightingEntry, float x, float y, float z, float u, float v, int light, float nx, float ny, float nz, boolean uniformLighting) {
/* 1426 */     class_4588 vertex = out.method_22918(matrix, x * 0.0625F, y * 0.0625F, z * 0.0625F).method_39415(-1).method_22913(u, v).method_22922(class_4608.field_21444).method_60803(light);
/* 1427 */     if (uniformLighting) {
/*      */ 
/*      */ 
/*      */       
/* 1431 */       vertex.method_60831(uniformLightingEntry, 0.0F, 0.0F, 1.0F);
/*      */     } else {
/* 1433 */       vertex.method_60831(matrices.method_23760(), nx, ny, nz);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void vertexWorld(class_4588 out, Matrix4f matrix, class_4587 matrices, class_4587.class_4665 uniformLightingEntry, Vector3f point, float u, float v, int light, float nx, float ny, float nz, boolean uniformLighting) {
/* 1449 */     class_4588 vertex = out.method_22918(matrix, point.x, point.y, point.z).method_39415(-1).method_22913(u, v).method_22922(class_4608.field_21444).method_60803(light);
/* 1450 */     if (uniformLighting) {
/* 1451 */       vertex.method_60831(uniformLightingEntry, 0.0F, 0.0F, 1.0F);
/*      */     } else {
/* 1453 */       vertex.method_60831(matrices.method_23760(), nx, ny, nz);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\BlockbenchWingModel$Cube.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */