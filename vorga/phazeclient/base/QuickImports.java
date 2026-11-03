/*    */ package vorga.phazeclient.base;
/*    */ import net.minecraft.class_1041;
/*    */ import net.minecraft.class_289;
/*    */ import net.minecraft.class_310;
/*    */ import vorga.phazeclient.api.system.draw.DrawEngineImpl;
/*    */ import vorga.phazeclient.api.system.shape.implement.Arc;
/*    */ import vorga.phazeclient.api.system.shape.implement.InvertedArc;
/*    */ import vorga.phazeclient.api.system.shape.implement.InvertedRectangle;
/*    */ import vorga.phazeclient.api.system.shape.implement.Rectangle;
/*    */ 
/*    */ public interface QuickImports {
/* 12 */   public static final DrawEngineImpl drawEngine = new DrawEngineImpl();
/* 13 */   public static final Rectangle rectangle = new Rectangle();
/* 14 */   public static final Arc arc = new Arc();
/* 15 */   public static final InvertedArc invertedArc = new InvertedArc();
/* 16 */   public static final InvertedRectangle invertedRectangle = new InvertedRectangle();
/* 17 */   public static final Image image = new Image();
/* 18 */   public static final WindowManager windowManager = WindowManager.INSTANCE;
/*    */   
/*    */   default class_310 client() {
/* 21 */     return class_310.method_1551();
/*    */   }
/*    */   
/*    */   default class_1041 getWindow() {
/* 25 */     class_310 client = client();
/* 26 */     return (client != null) ? client.method_22683() : null;
/*    */   }
/*    */   
/*    */   default class_1041 window() {
/* 30 */     return getWindow();
/*    */   }
/*    */   
/*    */   default class_9779 tickCounter() {
/* 34 */     class_310 client = client();
/* 35 */     return (client != null) ? client.method_61966() : null;
/*    */   }
/*    */   
/*    */   default class_289 tessellator() {
/* 39 */     return class_289.method_1348();
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\base\QuickImports.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */