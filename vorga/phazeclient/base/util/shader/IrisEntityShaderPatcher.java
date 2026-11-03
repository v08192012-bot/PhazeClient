/*    */ package vorga.phazeclient.base.util.shader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class IrisEntityShaderPatcher
/*    */ {
/*    */   private static final String MARKER = "v_PhazeEntityTarget";
/*    */   
/*    */   public static String patch(String name, String source) {
/* 10 */     if (name == null || source == null) {
/* 11 */       return source;
/*    */     }
/* 13 */     if (!name.toLowerCase().contains("gbuffers_entities") || source.contains("v_PhazeEntityTarget")) {
/* 14 */       return source;
/*    */     }
/*    */     
/* 17 */     String patched = patchFragment(source);
/* 18 */     patched = patchVertex(patched);
/* 19 */     return patched;
/*    */   }
/*    */   
/*    */   private static String patchFragment(String source) {
/* 23 */     if (!source.contains("#ifdef FRAGMENT_SHADER") || 
/* 24 */       !source.contains("in vec4 glColor;") || 
/* 25 */       !source.contains("gl_FragData[0] = color;")) {
/* 26 */       return source;
/*    */     }
/*    */     
/* 29 */     String injected = source.replace("in vec4 glColor;", "in vec4 glColor;\nflat in float v_PhazeEntityTarget;\n\nuniform vec4 PhazePlayerColor;\nuniform vec4 PhazePlayerParams;\nuniform vec4 PhazeEntityColor;\nuniform vec4 PhazeEntityParams;\n\nvec3 phazeEntityBrightness(vec3 value, float brightness) {\n    if (brightness > 0.0) {\n        return mix(value, vec3(1.0), clamp(brightness, 0.0, 1.0));\n    }\n    if (brightness < 0.0) {\n        return value * (1.0 + clamp(brightness, -1.0, 0.0));\n    }\n    return value;\n}\n\nvec4 phazeApplyEntityCorrection(vec4 color, vec4 tint, vec4 params) {\n    if (params.z < 0.5) {\n        return color;\n    }\n\n    vec3 rgb = color.rgb * max(tint.rgb, vec3(0.0));\n    float gray = dot(clamp(rgb, 0.0, 1.0), vec3(0.2126, 0.7152, 0.0722));\n    rgb = mix(vec3(gray), rgb, clamp(params.x, 0.0, 2.0));\n    rgb = phazeEntityBrightness(rgb, params.y);\n    return vec4(max(rgb, vec3(0.0)), color.a);\n}\n");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (injected.equals(source)) {
/* 63 */       return source;
/*    */     }
/*    */     
/* 66 */     return injected.replace("gl_FragData[0] = color;", "if (v_PhazeEntityTarget > 0.5) {\n    color = phazeApplyEntityCorrection(\n        color,\n        v_PhazeEntityTarget < 1.5 ? PhazePlayerColor : PhazeEntityColor,\n        v_PhazeEntityTarget < 1.5 ? PhazePlayerParams : PhazeEntityParams\n    );\n}\ngl_FragData[0] = color;\n");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String patchVertex(String source) {
/* 81 */     if (!source.contains("#ifdef VERTEX_SHADER") || 
/* 82 */       !source.contains("out vec4 glColor;") || 
/* 83 */       !source.contains("glColor = gl_Color;")) {
/* 84 */       return source;
/*    */     }
/*    */     
/* 87 */     String injected = source.replace("out vec4 glColor;", "out vec4 glColor;\nflat out float v_PhazeEntityTarget;\n");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 93 */     if (injected.equals(source)) {
/* 94 */       return source;
/*    */     }
/*    */     
/* 97 */     return injected.replace("glColor = gl_Color;", "glColor = gl_Color;\nv_PhazeEntityTarget = 0.0;\nif (glColor.a > 0.990 && glColor.a < 0.999) {\n    v_PhazeEntityTarget = glColor.a > 0.994 ? 1.0 : 2.0;\n    glColor.a = 1.0;\n}\n");
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\shader\IrisEntityShaderPatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */