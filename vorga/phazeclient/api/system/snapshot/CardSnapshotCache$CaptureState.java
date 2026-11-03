package vorga.phazeclient.api.system.snapshot;

import net.minecraft.class_10366;
import net.minecraft.class_276;
import org.joml.Matrix4f;

final class CaptureState {
  CardSnapshotCache.Snapshot snapshot;
  
  class_276 mainFb;
  
  Matrix4f savedProj;
  
  class_10366 savedProjType;
  
  int savedViewportX;
  
  int savedViewportY;
  
  int savedViewportW;
  
  int savedViewportH;
  
  boolean savedScissorEnabled;
  
  int savedScissorX;
  
  int savedScissorY;
  
  int savedScissorW;
  
  int savedScissorH;
  
  float[] savedShaderColor;
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\snapshot\CardSnapshotCache$CaptureState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */