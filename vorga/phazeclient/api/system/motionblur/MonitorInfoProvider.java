/*    */ package vorga.phazeclient.api.system.motionblur;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import org.lwjgl.PointerBuffer;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ import org.lwjgl.glfw.GLFWVidMode;
/*    */ 
/*    */ public class MonitorInfoProvider {
/*  9 */   private static long lastMonitorHandle = 0L;
/* 10 */   private static int lastRefreshRate = 60;
/* 11 */   private static long lastCheckTime = 0L;
/*    */   private static final long CHECK_INTERVAL_NS = 1000000000L;
/*    */   
/*    */   public static void updateDisplayInfo() {
/* 15 */     long now = System.nanoTime();
/* 16 */     if (now - lastCheckTime < 1000000000L)
/* 17 */       return;  lastCheckTime = now;
/*    */     
/* 19 */     class_310 mc = class_310.method_1551();
/* 20 */     if (mc == null || mc.method_22683() == null)
/*    */       return; 
/* 22 */     long window = mc.method_22683().method_4490();
/* 23 */     long monitor = GLFW.glfwGetWindowMonitor(window);
/*    */     
/* 25 */     if (monitor == 0L) {
/* 26 */       monitor = getMonitorFromWindowPosition(window, mc
/* 27 */           .method_22683().method_4480(), mc
/* 28 */           .method_22683().method_4507());
/*    */     }
/*    */     
/* 31 */     if (monitor != lastMonitorHandle) {
/* 32 */       lastRefreshRate = detectRefreshRateFromMonitor(monitor);
/* 33 */       lastMonitorHandle = monitor;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getRefreshRate() {
/* 38 */     return lastRefreshRate;
/*    */   }
/*    */   
/*    */   private static long getMonitorFromWindowPosition(long window, int windowWidth, int windowHeight) {
/* 42 */     int[] winX = new int[1];
/* 43 */     int[] winY = new int[1];
/* 44 */     GLFW.glfwGetWindowPos(window, winX, winY);
/*    */     
/* 46 */     int windowCenterX = winX[0] + windowWidth / 2;
/* 47 */     int windowCenterY = winY[0] + windowHeight / 2;
/*    */     
/* 49 */     long monitorResult = GLFW.glfwGetPrimaryMonitor();
/* 50 */     PointerBuffer monitors = GLFW.glfwGetMonitors();
/* 51 */     if (monitors != null)
/* 52 */       for (int i = 0; i < monitors.limit(); i++) {
/* 53 */         long m = monitors.get(i);
/* 54 */         int[] mx = new int[1];
/* 55 */         int[] my = new int[1];
/* 56 */         GLFW.glfwGetMonitorPos(m, mx, my);
/* 57 */         GLFWVidMode mode = GLFW.glfwGetVideoMode(m);
/* 58 */         if (mode != null)
/*    */         {
/* 60 */           if (windowCenterX >= mx[0] && windowCenterX < mx[0] + mode.width() && windowCenterY >= my[0] && windowCenterY < my[0] + mode
/* 61 */             .height()) {
/* 62 */             monitorResult = m;
/*    */             break;
/*    */           } 
/*    */         }
/*    */       }  
/* 67 */     return monitorResult;
/*    */   }
/*    */   
/*    */   private static int detectRefreshRateFromMonitor(long monitor) {
/* 71 */     GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
/* 72 */     return (vidMode != null) ? vidMode.refreshRate() : 60;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\motionblur\MonitorInfoProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */