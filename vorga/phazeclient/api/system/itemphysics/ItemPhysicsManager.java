/*    */ package vorga.phazeclient.api.system.itemphysics;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.class_1542;
/*    */ import net.minecraft.class_310;
/*    */ import vorga.phazeclient.implement.features.modules.other.ItemPhysics;
/*    */ 
/*    */ 
/*    */ public class ItemPhysicsManager
/*    */ {
/* 12 */   private static final ItemPhysicsManager INSTANCE = new ItemPhysicsManager();
/*    */   
/*    */   private static final float BASE_MULTIPLIER = 0.25F;
/* 15 */   private final Map<Integer, ItemPhysicsData> itemDataMap = new HashMap<>();
/*    */   
/*    */   public static ItemPhysicsManager getInstance() {
/* 18 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void updateRotation(class_1542 entity, boolean isBlock) {
/* 22 */     if (!ItemPhysics.getInstance().isEnabled())
/*    */       return; 
/* 24 */     class_310 mc = class_310.method_1551();
/* 25 */     float rotateBy = mc.method_61966().method_60637(true) * 0.25F * ItemPhysics.getInstance().getRotationSpeed();
/* 26 */     if (mc.method_1493()) rotateBy = 0.0F;
/*    */     
/* 28 */     int id = entity.method_5628();
/* 29 */     ItemPhysicsData data = this.itemDataMap.computeIfAbsent(Integer.valueOf(id), k -> new ItemPhysicsData());
/*    */     
/* 31 */     if (entity.method_24828()) {
/* 32 */       float target; data.onGround = true;
/* 33 */       float xRot = data.xRot;
/*    */       
/* 35 */       if (isBlock) {
/* 36 */         target = Math.round(xRot / 90.0F) * 90.0F;
/*    */       } else {
/* 38 */         target = Math.round(xRot / 180.0F) * 180.0F;
/*    */       } 
/*    */       
/* 41 */       data.xRot = target;
/*    */     } else {
/* 43 */       data.onGround = false;
/*    */       
/* 45 */       data.xRot += rotateBy * 2.0F;
/*    */     } 
/*    */     
/* 48 */     data.yRot = entity.method_36454();
/*    */   }
/*    */   
/*    */   public void removeItem(int id) {
/* 52 */     this.itemDataMap.remove(Integer.valueOf(id));
/*    */   }
/*    */   
/*    */   public ItemPhysicsData getItemData(int id) {
/* 56 */     return this.itemDataMap.get(Integer.valueOf(id));
/*    */   }
/*    */   
/*    */   public static class ItemPhysicsData {
/* 60 */     public float xRot = 0.0F;
/* 61 */     public float yRot = 0.0F;
/*    */     public boolean onGround = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\itemphysics\ItemPhysicsManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */