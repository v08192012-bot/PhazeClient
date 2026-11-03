/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public final class BakedSkinLayers
/*    */ {
/* 23 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> hat = new ArrayList<>();
/* 24 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> jacket = new ArrayList<>();
/* 25 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> leftSleeveWide = new ArrayList<>();
/* 26 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> leftSleeveSlim = new ArrayList<>();
/* 27 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> rightSleeveWide = new ArrayList<>();
/* 28 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> rightSleeveSlim = new ArrayList<>();
/* 29 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> leftPants = new ArrayList<>();
/* 30 */   public final List<ThreeDSkinsVoxelizer.VoxelQuad> rightPants = new ArrayList<>();
/*    */   
/*    */   public boolean hasAny() {
/* 33 */     return (!this.hat.isEmpty() || !this.jacket.isEmpty() || !this.leftSleeveWide.isEmpty() || 
/* 34 */       !this.rightSleeveWide.isEmpty() || !this.leftPants.isEmpty() || !this.rightPants.isEmpty());
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ThreeDSkinsVoxelizer$BakedSkinLayers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */