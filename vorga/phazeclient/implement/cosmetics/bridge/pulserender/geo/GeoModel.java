/*    */ package vorga.phazeclient.implement.cosmetics.bridge.pulserender.geo;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class GeoModel {
/*  8 */   public List<GeoBone> topLevelBones = new ArrayList<>();
/*  9 */   public int textureWidth = 64;
/* 10 */   public int textureHeight = 64;
/*    */   
/*    */   public Optional<GeoBone> getBone(String var1) {
/* 13 */     for (GeoBone var3 : this.topLevelBones) {
/* 14 */       GeoBone var4 = getBoneRecursively(var1, var3);
/* 15 */       if (var4 != null) {
/* 16 */         return Optional.of(var4);
/*    */       }
/*    */     } 
/*    */     
/* 20 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   private GeoBone getBoneRecursively(String var1, GeoBone var2) {
/* 24 */     if (var2.name.equals(var1)) {
/* 25 */       return var2;
/*    */     }
/* 27 */     for (GeoBone var4 : var2.childBones) {
/* 28 */       GeoBone var5 = getBoneRecursively(var1, var4);
/* 29 */       if (var5 != null) {
/* 30 */         return var5;
/*    */       }
/*    */     } 
/*    */     
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\pulserender\geo\GeoModel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */