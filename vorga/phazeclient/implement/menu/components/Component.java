package vorga.phazeclient.implement.menu.components;

import net.minecraft.class_332;

public interface Component {
  void render(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat);
  
  void tick();
  
  boolean mouseClicked(double paramDouble1, double paramDouble2, int paramInt);
  
  boolean mouseReleased(double paramDouble1, double paramDouble2, int paramInt);
  
  boolean mouseDragged(double paramDouble1, double paramDouble2, int paramInt, double paramDouble3, double paramDouble4);
  
  boolean mouseScrolled(double paramDouble1, double paramDouble2, double paramDouble3);
  
  boolean keyPressed(int paramInt1, int paramInt2, int paramInt3);
  
  boolean keyReleased(int paramInt1, int paramInt2, int paramInt3);
  
  boolean charTyped(char paramChar, int paramInt);
  
  boolean isHover(double paramDouble1, double paramDouble2);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\Component.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */