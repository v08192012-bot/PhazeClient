package vorga.phazeclient.mixins;

import java.util.Map;
import java.util.SequencedMap;
import net.minecraft.class_1921;
import net.minecraft.class_287;
import net.minecraft.class_4597;
import net.minecraft.class_9799;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_4597.class_4598.class})
public interface VertexConsumerProviderImmediateAccessor {
  @Accessor("layerBuffers")
  SequencedMap<class_1921, class_9799> phaze$getLayerBuffers();
  
  @Accessor("pending")
  Map<class_1921, class_287> phaze$getPendingLayers();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\VertexConsumerProviderImmediateAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */