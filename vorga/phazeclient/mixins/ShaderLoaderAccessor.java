package vorga.phazeclient.mixins;

import net.minecraft.class_10151;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_10151.class})
public interface ShaderLoaderAccessor {
  @Accessor
  class_10151.class_10170 getCache();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ShaderLoaderAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */