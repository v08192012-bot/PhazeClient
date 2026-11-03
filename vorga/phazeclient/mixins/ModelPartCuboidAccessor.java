package vorga.phazeclient.mixins;

import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_630.class_628.class})
public interface ModelPartCuboidAccessor {
  @Accessor("sides")
  class_630.class_593[] phaze$getSides();
  
  @Mutable
  @Accessor("sides")
  void phaze$setSides(class_630.class_593[] paramArrayOfclass_593);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ModelPartCuboidAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */