package vorga.phazeclient.mixins;

import net.minecraft.class_10197;
import net.minecraft.class_10201;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_10197.class})
public interface EquipmentRendererAccessor {
  @Accessor("equipmentModelLoader")
  class_10201 phaze$getEquipmentModelLoader();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\EquipmentRendererAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */