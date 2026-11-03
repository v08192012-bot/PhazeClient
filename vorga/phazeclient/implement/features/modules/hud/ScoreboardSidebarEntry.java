/*   */ package vorga.phazeclient.implement.features.modules.hud;public final class ScoreboardSidebarEntry extends Record { private final class_2561 name;
/*   */   private final class_2561 score;
/*   */   private final boolean hasScore;
/*   */   private final int scoreWidth;
/*   */   
/* 6 */   public ScoreboardSidebarEntry(class_2561 name, class_2561 score, boolean hasScore, int scoreWidth) { this.name = name; this.score = score; this.hasScore = hasScore; this.scoreWidth = scoreWidth; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/hud/ScoreboardSidebarEntry;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 6 */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/hud/ScoreboardSidebarEntry; } public class_2561 name() { return this.name; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/hud/ScoreboardSidebarEntry;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/hud/ScoreboardSidebarEntry; } public final boolean equals(Object o) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/hud/ScoreboardSidebarEntry;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/hud/ScoreboardSidebarEntry;
/* 6 */     //   0	8	1	o	Ljava/lang/Object; } public class_2561 score() { return this.score; } public boolean hasScore() { return this.hasScore; } public int scoreWidth() { return this.scoreWidth; }
/*   */    }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\ScoreboardSidebarEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */