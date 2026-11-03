/*    */ package vorga.phazeclient.implement.cosmetics.bridge;
/*    */ 
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Bone
/*    */ {
/*    */   final String name;
/*    */   final String parent;
/*    */   final ParsedCosmeticModel.Vec3 pivot;
/*    */   final ParsedCosmeticModel.Vec3 rotation;
/*    */   final List<ParsedCosmeticModel.Cube> cubes;
/*    */   final ParsedCosmeticModel.BoneStyle style;
/*    */   
/*    */   Bone(String name, String parent, ParsedCosmeticModel.Vec3 pivot, ParsedCosmeticModel.Vec3 rotation, List<ParsedCosmeticModel.Cube> cubes, ParsedCosmeticModel.BoneStyle style) {
/* 86 */     this.name = name;
/* 87 */     this.parent = parent;
/* 88 */     this.pivot = pivot;
/* 89 */     this.rotation = rotation;
/* 90 */     this.cubes = cubes;
/* 91 */     this.style = style;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\ParsedCosmeticModel$Bone.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */