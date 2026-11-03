package vorga.phazeclient.mixins;

import net.minecraft.class_1011;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_1011.class})
public interface NativeImageGetColorInvoker {
  @Invoker("getColor")
  int phaze$invokeGetColor(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\NativeImageGetColorInvoker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */