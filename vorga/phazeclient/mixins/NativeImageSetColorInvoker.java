package vorga.phazeclient.mixins;

import net.minecraft.class_1011;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_1011.class})
public interface NativeImageSetColorInvoker {
  @Invoker("setColor")
  void phaze$invokeSetColor(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\NativeImageSetColorInvoker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */