package vorga.phazeclient.mixins;

import java.util.List;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_630.class})
public interface ModelPartAccessor {
  @Accessor("cuboids")
  List<class_630.class_628> phaze$getCuboids();
  
  @Mutable
  @Accessor("cuboids")
  void phaze$setCuboids(List<class_630.class_628> paramList);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ModelPartAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */