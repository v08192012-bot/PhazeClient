package vorga.phazeclient.mixins;

import java.util.List;
import net.minecraft.class_279;
import net.minecraft.class_283;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_279.class})
public interface PostEffectProcessorAccessor {
  @Accessor
  List<class_283> getPasses();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PostEffectProcessorAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */