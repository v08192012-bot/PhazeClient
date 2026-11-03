/*    */ package vorga.phazeclient.base.util;
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
/*    */ public final class Message
/*    */   extends Record
/*    */ {
/*    */   private final long id;
/*    */   private final String senderId;
/*    */   private final String senderUsername;
/*    */   private final String text;
/*    */   private final long createdAt;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/base/util/MessengerService$Message;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #49	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$Message;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/base/util/MessengerService$Message;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #49	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lvorga/phazeclient/base/util/MessengerService$Message;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/base/util/MessengerService$Message;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #49	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lvorga/phazeclient/base/util/MessengerService$Message;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Message(long id, String senderId, String senderUsername, String text, long createdAt) {
/* 49 */     this.id = id; this.senderId = senderId; this.senderUsername = senderUsername; this.text = text; this.createdAt = createdAt; } public long id() { return this.id; } public String senderId() { return this.senderId; } public String senderUsername() { return this.senderUsername; } public String text() { return this.text; } public long createdAt() { return this.createdAt; }
/*    */ 
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\MessengerService$Message.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */