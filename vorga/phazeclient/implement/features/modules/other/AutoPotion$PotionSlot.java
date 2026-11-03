/*    */ package vorga.phazeclient.implement.features.modules.other;
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
/*    */ final class PotionSlot
/*    */   extends Record
/*    */ {
/*    */   private final int screenSlotId;
/*    */   private final int hotbarIndex;
/*    */   private final boolean hotbarSlot;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/AutoPotion$PotionSlot;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/AutoPotion$PotionSlot;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/AutoPotion$PotionSlot;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/AutoPotion$PotionSlot;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/AutoPotion$PotionSlot;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/AutoPotion$PotionSlot;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   private PotionSlot(int screenSlotId, int hotbarIndex, boolean hotbarSlot) {
/* 56 */     this.screenSlotId = screenSlotId; this.hotbarIndex = hotbarIndex; this.hotbarSlot = hotbarSlot; } public int screenSlotId() { return this.screenSlotId; } public int hotbarIndex() { return this.hotbarIndex; } public boolean hotbarSlot() { return this.hotbarSlot; }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoPotion$PotionSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */