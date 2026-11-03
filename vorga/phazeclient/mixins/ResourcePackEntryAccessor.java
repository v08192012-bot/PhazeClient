package vorga.phazeclient.mixins;

import net.minecraft.class_5369;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = {"net.minecraft.client.gui.screen.pack.PackListWidget$ResourcePackEntry"})
public interface ResourcePackEntryAccessor {
  @Accessor("pack")
  class_5369.class_5371 phaze$getPack();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ResourcePackEntryAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */