/*     */ package vorga.phazeclient.base.util.shader;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ public final class WorldColorChunkShaderPatcher
/*     */ {
/*     */   private static final String VERTEX_MARKER = "v_PhazeWorldColorFluid";
/*     */   private static final String FRAGMENT_MARKER = "phazeApplyWorldColorCorrection";
/*  10 */   private static final Pattern MESH_ID_ASSIGN = Pattern.compile("((?:_draw_id|_vert_mesh_id)\\s*=\\s*[^;]+;)");
/*     */ 
/*     */   
/*  13 */   private static final Pattern FRAG_COLOR_ASSIGN = Pattern.compile("((?:fragColor|out_FragColor)\\s*=\\s*[^;]+;)");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String patch(String original) {
/*  19 */     if (original == null) {
/*  20 */       return null;
/*     */     }
/*     */     
/*  23 */     String meshIdToken = detectMeshIdToken(original);
/*  24 */     boolean isChunkVertex = (meshIdToken != null && original.contains("_vert_position") && original.contains("_vert_color"));
/*  25 */     if (isChunkVertex) {
/*  26 */       if (original.contains("v_PhazeWorldColorFluid")) {
/*  27 */         return original;
/*     */       }
/*  29 */       return patchVertex(original);
/*     */     } 
/*     */     
/*  32 */     String fragColorToken = detectFragColorToken(original);
/*     */     
/*  34 */     boolean isChunkFragment = (fragColorToken != null && original.contains("u_BlockTex"));
/*  35 */     if (isChunkFragment) {
/*  36 */       if (original.contains("phazeApplyWorldColorCorrection")) {
/*  37 */         return original;
/*     */       }
/*  39 */       return patchFragment(original, fragColorToken);
/*     */     } 
/*     */     
/*  42 */     return original;
/*     */   }
/*     */   
/*     */   private static String patchVertex(String original) {
/*  46 */     int insertAt = findUniformInsertionPoint(original);
/*  47 */     if (insertAt < 0) {
/*  48 */       return original;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  53 */     String withDecl = original.substring(0, insertAt) + "\nout float v_PhazeWorldColorFluid;\n" + original.substring(0, insertAt);
/*     */     
/*  55 */     String result = MESH_ID_ASSIGN.matcher(withDecl).replaceFirst("$1\n    v_PhazeWorldColorFluid = (_vert_color.a < 0.999) ? 1.0 : 0.0;\n    if (v_PhazeWorldColorFluid > 0.5) { _vert_color.a = 1.0; }");
/*     */ 
/*     */ 
/*     */     
/*  59 */     return result.equals(withDecl) ? original : result;
/*     */   }
/*     */   
/*     */   private static String patchFragment(String original, String fragColorToken) {
/*  63 */     int insertAt = findUniformInsertionPoint(original);
/*  64 */     if (insertAt < 0) {
/*  65 */       return original;
/*     */     }
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
/* 100 */     String withDecl = original.substring(0, insertAt) + "\nin float v_PhazeWorldColorFluid;\nuniform vec4 PhazeBlockColor;\nuniform vec4 PhazeBlockParams;\nuniform vec4 PhazeFluidColor;\nuniform vec4 PhazeFluidParams;\n\nvec3 phazeApplyWorldColorBrightness(vec3 value, float brightness) {\n    if (brightness > 0.0) {\n        return mix(value, vec3(1.0), clamp(brightness, 0.0, 1.0));\n    }\n    if (brightness < 0.0) {\n        return value * (1.0 + clamp(brightness, -1.0, 0.0));\n    }\n    return value;\n}\n\nvec4 phazeApplyWorldColorCorrection(vec4 color, vec4 tint, vec4 params) {\n    if (params.z < 0.5) {\n        return color;\n    }\n\n    vec3 rgb = color.rgb * max(tint.rgb, vec3(0.0));\n    float gray = dot(clamp(rgb, 0.0, 1.0), vec3(0.2126, 0.7152, 0.0722));\n    rgb = mix(vec3(gray), rgb, clamp(params.x, 0.0, 2.0));\n    rgb = phazeApplyWorldColorBrightness(rgb, params.y);\n    color.rgb = max(rgb, vec3(0.0));\n    return color;\n}\n" + original.substring(0, insertAt);
/*     */     
/* 102 */     String result = FRAG_COLOR_ASSIGN.matcher(withDecl).replaceFirst("$1\n    " + fragColorToken + " = phazeApplyWorldColorCorrection(" + fragColorToken + ", v_PhazeWorldColorFluid > 0.5 ? PhazeFluidColor : PhazeBlockColor, v_PhazeWorldColorFluid > 0.5 ? PhazeFluidParams : PhazeBlockParams);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return result.equals(withDecl) ? original : result;
/*     */   }
/*     */   
/*     */   private static String detectMeshIdToken(String original) {
/* 112 */     if (original.contains("_draw_id")) {
/* 113 */       return "_draw_id";
/*     */     }
/* 115 */     if (original.contains("_vert_mesh_id")) {
/* 116 */       return "_vert_mesh_id";
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   private static String detectFragColorToken(String original) {
/* 122 */     if (original.contains("out vec4 fragColor")) {
/* 123 */       return "fragColor";
/*     */     }
/* 125 */     if (original.contains("out vec4 out_FragColor")) {
/* 126 */       return "out_FragColor";
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   private static int findUniformInsertionPoint(String source) {
/* 132 */     if (source.isEmpty()) {
/* 133 */       return -1;
/*     */     }
/* 135 */     int charPos = 0;
/* 136 */     int lastDirectiveEnd = -1;
/* 137 */     while (charPos < source.length()) {
/* 138 */       int lineEnd = source.indexOf('\n', charPos);
/* 139 */       if (lineEnd < 0) {
/* 140 */         lineEnd = source.length();
/*     */       }
/* 142 */       String line = source.substring(charPos, lineEnd).trim();
/* 143 */       if (line.isEmpty() || line.startsWith("//")) {
/* 144 */         charPos = lineEnd + 1;
/*     */         continue;
/*     */       } 
/* 147 */       if (line.startsWith("#")) {
/* 148 */         lastDirectiveEnd = lineEnd;
/* 149 */         charPos = lineEnd + 1;
/*     */         continue;
/*     */       } 
/* 152 */       return (lastDirectiveEnd >= 0) ? lastDirectiveEnd : charPos;
/*     */     } 
/* 154 */     return lastDirectiveEnd;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\shader\WorldColorChunkShaderPatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */