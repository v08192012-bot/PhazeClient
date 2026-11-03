package vorga.phazeclient.mixins;

import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_310.class})
public interface MinecraftClientMouseInvoker {
  @Invoker("doAttack")
  boolean phaze$invokeDoAttack();
  
  @Invoker("doItemUse")
  void phaze$invokeDoItemUse();
  
  @Invoker("handleBlockBreaking")
  void phaze$invokeHandleBlockBreaking(boolean paramBoolean);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\MinecraftClientMouseInvoker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */