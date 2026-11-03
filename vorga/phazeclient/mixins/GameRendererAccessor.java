package vorga.phazeclient.mixins;

import net.minecraft.class_4184;
import net.minecraft.class_757;
import net.minecraft.class_9920;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_757.class})
public interface GameRendererAccessor {
  @Invoker("getFov")
  float invokeGetFov(class_4184 paramclass_4184, float paramFloat, boolean paramBoolean);
  
  @Accessor
  class_9920 getPool();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\GameRendererAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */