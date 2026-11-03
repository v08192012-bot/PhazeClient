/*    */ package vorga.phazeclient.base.util.shader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class IrisFluidShaderPatcher
/*    */ {
/*    */   private static final String MARKER = "phazeApplyFluidCorrection";
/*    */   
/*    */   public static String patch(String name, String source) {
/* 10 */     if (name == null || source == null) {
/* 11 */       return source;
/*    */     }
/* 13 */     String lowerName = name.toLowerCase();
/* 14 */     if ((!lowerName.contains("water") && !lowerName.contains("translucent")) || source.contains("phazeApplyFluidCorrection")) {
/* 15 */       return source;
/*    */     }
/* 17 */     if (!source.contains("#ifdef FRAGMENT_SHADER") || 
/* 18 */       !source.contains("in vec4 glColor;") || 
/* 19 */       !source.contains("vec4 color")) {
/* 20 */       return source;
/*    */     }
/*    */     
/* 23 */     String injected = source.replace("in vec4 glColor;", "in vec4 glColor;\nuniform vec4 PhazeFluidColor;\nuniform vec4 PhazeFluidParams;\n\nvec3 phazeApplyFluidBrightness(vec3 value, float brightness) {\n    if (brightness > 0.0) {\n        return mix(value, vec3(1.0), clamp(brightness, 0.0, 1.0));\n    }\n    if (brightness < 0.0) {\n        return value * (1.0 + clamp(brightness, -1.0, 0.0));\n    }\n    return value;\n}\n\nvec4 phazeApplyFluidCorrection(vec4 color, vec4 tint, vec4 params) {\n    if (params.z < 0.5) {\n        return color;\n    }\n\n    vec3 rgb = color.rgb * max(tint.rgb, vec3(0.0));\n    float gray = dot(clamp(rgb, 0.0, 1.0), vec3(0.2126, 0.7152, 0.0722));\n    rgb = mix(vec3(gray), rgb, clamp(params.x, 0.0, 2.0));\n    rgb = phazeApplyFluidBrightness(rgb, params.y);\n    color.rgb = max(rgb, vec3(0.0));\n    return color;\n}\n");
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
/* 53 */     if (injected.equals(source)) {
/* 54 */       return source;
/*    */     }
/*    */     
/* 57 */     if (injected.contains("float skyFade = 0.0;")) {
/* 58 */       return injected.replace("float skyFade = 0.0;", "color = phazeApplyFluidCorrection(color, PhazeFluidColor, PhazeFluidParams);\nfloat skyFade = 0.0;\n");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     if (injected.contains("fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);")) {
/* 67 */       return injected.replace("fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);", "color = phazeApplyFluidCorrection(color, PhazeFluidColor, PhazeFluidParams);\nfragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);\n");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     if (injected.contains("gl_FragData[0] = color;")) {
/* 76 */       return injected.replace("gl_FragData[0] = color;", "color = phazeApplyFluidCorrection(color, PhazeFluidColor, PhazeFluidParams);\ngl_FragData[0] = color;\n");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     return source;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\shader\IrisFluidShaderPatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */