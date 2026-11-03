package vorga.phazeclient.mixins;

import net.minecraft.class_4597;
import net.minecraft.class_4618;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_4618.class})
public interface OutlineVertexConsumerProviderAccessor {
  @Accessor("parent")
  class_4597.class_4598 phaze$getParent();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\OutlineVertexConsumerProviderAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */