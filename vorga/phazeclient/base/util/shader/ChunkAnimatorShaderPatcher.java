/*     */ package vorga.phazeclient.base.util.shader;
/*     */ 
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class ChunkAnimatorShaderPatcher
/*     */ {
/*     */   private static final String PATCH_MARKER = "PhazeChunkAnimBlock";
/*     */   public static final int CHUNK_ANIM_UBO_BINDING = 0;
/*     */   private static final String FRAGMENT_PATCH_MARKER = "phaze_dither_threshold";
/* 138 */   private static final Pattern MESH_ID_ASSIGN = Pattern.compile("((?:_draw_id|_vert_mesh_id)\\s*=\\s*[^;]+;)");
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
/*     */   private static final String OFFSET_PATCH_TEMPLATE = "$1\n    vec4 phazeAnimData = phazeChunkData[int({mesh_id}) & 0xFF];\n    if (u_PhazeChunkAnimMode == 1) { _vert_position += phazeAnimData.xyz; }\n    v_PhazeChunkAnimFade = ((u_PhazeChunkAnimMode == 2 || u_PhazeChunkAnimMode == 3) ? phazeAnimData.w : 1.0);\n    if (u_PhazeChunkAnimMode == 3 && phazeAnimData.w < 1.0) { _vert_position = mix(vec3(8.0), _vert_position, phazeAnimData.w); }";
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
/*     */   private static final String FRAGMENT_DECL = "\nin float v_PhazeChunkAnimFade;\nuniform int u_PhazeFadeStyle;\nuniform vec3 u_PhazeFogMixColor;\nconst float phaze_kBayer4x4[16] = float[16](\n     0.0/16.0,  8.0/16.0,  2.0/16.0, 10.0/16.0,\n    12.0/16.0,  4.0/16.0, 14.0/16.0,  6.0/16.0,\n     3.0/16.0, 11.0/16.0,  1.0/16.0,  9.0/16.0,\n    15.0/16.0,  7.0/16.0, 13.0/16.0,  5.0/16.0\n);\nfloat phaze_dither_threshold(vec2 fragCoord) {\n    int x = int(mod(fragCoord.x, 4.0));\n    int y = int(mod(fragCoord.y, 4.0));\n    return phaze_kBayer4x4[y * 4 + x];\n}\n";
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
/* 247 */   private static final Pattern FRAG_MAIN_OPEN = Pattern.compile("(void\\s+main\\s*\\(\\s*\\)\\s*\\{)");
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
/*     */   private static final String FRAG_DISCARD_PATCH = "$1\n    if (u_PhazeFadeStyle == 0 && v_PhazeChunkAnimFade < 1.0 && v_PhazeChunkAnimFade <= phaze_dither_threshold(gl_FragCoord.xy)) {\n        discard;\n    }";
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
/* 273 */   private static final Pattern FRAG_COLOR_ASSIGN = Pattern.compile("((?:fragColor|out_FragColor)\\s*=\\s*[^;]+;)");
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
/*     */   private static final String FRAG_FOGMIX_PATCH_TEMPLATE = "$1\n    if (u_PhazeFadeStyle == 1 && v_PhazeChunkAnimFade < 1.0) {\n        {frag_color} = mix({frag_color}, vec4(u_PhazeFogMixColor, {frag_color}.a), 1.0 - v_PhazeChunkAnimFade);\n    }";
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
/*     */   public static String patch(String original) {
/* 299 */     if (original == null) {
/* 300 */       return null;
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
/* 331 */     String meshIdToken = detectMeshIdToken(original);
/* 332 */     boolean isVertex = (original.contains("_vert_position") && meshIdToken != null);
/*     */     
/* 334 */     if (isVertex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 340 */       if (original.contains("PhazeChunkAnimBlock")) {
/* 341 */         return original;
/*     */       }
/* 343 */       return patchVertex(original, meshIdToken);
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
/* 363 */     boolean isChunkFragment = (original.contains("u_BlockTex") && (original.contains("out vec4 fragColor") || original.contains("out vec4 out_FragColor")));
/* 364 */     if (isChunkFragment) {
/* 365 */       if (original.contains("phaze_dither_threshold")) {
/* 366 */         return original;
/*     */       }
/* 368 */       return patchFragment(original);
/*     */     } 
/* 370 */     return original;
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
/*     */   private static String detectMeshIdToken(String original) {
/* 384 */     if (original.contains("_draw_id")) {
/* 385 */       return "_draw_id";
/*     */     }
/* 387 */     if (original.contains("_vert_mesh_id")) {
/* 388 */       return "_vert_mesh_id";
/*     */     }
/* 390 */     return null;
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
/*     */   private static String detectFragColorToken(String original) {
/* 408 */     if (original.contains("out vec4 fragColor")) {
/* 409 */       return "fragColor";
/*     */     }
/* 411 */     if (original.contains("out vec4 out_FragColor")) {
/* 412 */       return "out_FragColor";
/*     */     }
/* 414 */     return null;
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
/*     */   private static String patchVertex(String original, String meshIdToken) {
/* 440 */     int insertAt = findUniformInsertionPoint(original);
/* 441 */     if (insertAt < 0) {
/* 442 */       return original;
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
/* 459 */     String withUniforms = original.substring(0, insertAt) + "\nlayout(std140) uniform PhazeChunkAnimBlock {\n    vec4 phazeChunkData[256];\n};\nuniform int u_PhazeChunkAnimMode;\nout float v_PhazeChunkAnimFade;\n" + original.substring(0, insertAt);
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
/* 477 */     String offsetPatch = "$1\n    vec4 phazeAnimData = phazeChunkData[int({mesh_id}) & 0xFF];\n    if (u_PhazeChunkAnimMode == 1) { _vert_position += phazeAnimData.xyz; }\n    v_PhazeChunkAnimFade = ((u_PhazeChunkAnimMode == 2 || u_PhazeChunkAnimMode == 3) ? phazeAnimData.w : 1.0);\n    if (u_PhazeChunkAnimMode == 3 && phazeAnimData.w < 1.0) { _vert_position = mix(vec3(8.0), _vert_position, phazeAnimData.w); }".replace("{mesh_id}", meshIdToken);
/*     */     
/* 479 */     String result = MESH_ID_ASSIGN.matcher(withUniforms).replaceFirst(offsetPatch);
/* 480 */     if (result.equals(withUniforms))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 487 */       return original;
/*     */     }
/* 489 */     return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String patchFragment(String original) {
/* 520 */     String fragColorToken = detectFragColorToken(original);
/* 521 */     if (fragColorToken == null) {
/* 522 */       return original;
/*     */     }
/* 524 */     int insertAt = findUniformInsertionPoint(original);
/* 525 */     if (insertAt < 0) {
/* 526 */       return original;
/*     */     }
/*     */ 
/*     */     
/* 530 */     String withDecl = original.substring(0, insertAt) + "\nin float v_PhazeChunkAnimFade;\nuniform int u_PhazeFadeStyle;\nuniform vec3 u_PhazeFogMixColor;\nconst float phaze_kBayer4x4[16] = float[16](\n     0.0/16.0,  8.0/16.0,  2.0/16.0, 10.0/16.0,\n    12.0/16.0,  4.0/16.0, 14.0/16.0,  6.0/16.0,\n     3.0/16.0, 11.0/16.0,  1.0/16.0,  9.0/16.0,\n    15.0/16.0,  7.0/16.0, 13.0/16.0,  5.0/16.0\n);\nfloat phaze_dither_threshold(vec2 fragCoord) {\n    int x = int(mod(fragCoord.x, 4.0));\n    int y = int(mod(fragCoord.y, 4.0));\n    return phaze_kBayer4x4[y * 4 + x];\n}\n" + original.substring(0, insertAt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 538 */     String afterDiscard = FRAG_MAIN_OPEN.matcher(withDecl).replaceFirst("$1\n    if (u_PhazeFadeStyle == 0 && v_PhazeChunkAnimFade < 1.0 && v_PhazeChunkAnimFade <= phaze_dither_threshold(gl_FragCoord.xy)) {\n        discard;\n    }");
/* 539 */     if (afterDiscard.equals(withDecl))
/*     */     {
/*     */ 
/*     */       
/* 543 */       return original;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 552 */     String fogMixPatch = "$1\n    if (u_PhazeFadeStyle == 1 && v_PhazeChunkAnimFade < 1.0) {\n        {frag_color} = mix({frag_color}, vec4(u_PhazeFogMixColor, {frag_color}.a), 1.0 - v_PhazeChunkAnimFade);\n    }".replace("{frag_color}", fragColorToken);
/*     */     
/* 554 */     String result = FRAG_COLOR_ASSIGN.matcher(afterDiscard).replaceFirst(fogMixPatch);
/* 555 */     if (result.equals(afterDiscard))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 563 */       return afterDiscard;
/*     */     }
/* 565 */     return result;
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
/*     */   private static int findUniformInsertionPoint(String source) {
/* 584 */     if (source.isEmpty()) {
/* 585 */       return -1;
/*     */     }
/* 587 */     int charPos = 0;
/* 588 */     int lastDirectiveEnd = -1;
/* 589 */     while (charPos < source.length()) {
/* 590 */       int lineEnd = source.indexOf('\n', charPos);
/* 591 */       if (lineEnd < 0) lineEnd = source.length(); 
/* 592 */       String line = source.substring(charPos, lineEnd).trim();
/* 593 */       if (line.isEmpty() || line.startsWith("//")) {
/*     */ 
/*     */         
/* 596 */         charPos = lineEnd + 1;
/*     */         continue;
/*     */       } 
/* 599 */       if (line.startsWith("#")) {
/* 600 */         lastDirectiveEnd = lineEnd;
/* 601 */         charPos = lineEnd + 1;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 607 */       return (lastDirectiveEnd >= 0) ? lastDirectiveEnd : charPos;
/*     */     } 
/* 609 */     return lastDirectiveEnd;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\shader\ChunkAnimatorShaderPatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */