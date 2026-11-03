package vorga.phazeclient.api.system.draw;

import net.minecraft.class_287;
import org.joml.Matrix4f;

public interface DrawEngine {
  void quad(Matrix4f paramMatrix4f, class_287 paramclass_287, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void quad(Matrix4f paramMatrix4f, class_287 paramclass_287, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt);
  
  void quad(Matrix4f paramMatrix4f, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\draw\DrawEngine.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */