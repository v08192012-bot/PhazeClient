package vorga.phazeclient.mixins;

import java.util.List;
import net.minecraft.class_303;
import net.minecraft.class_338;
import net.minecraft.class_8623;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_338.class})
public interface ChatHudAccessor {
  @Accessor("messages")
  List<class_303> phaze$getMessages();
  
  @Accessor("visibleMessages")
  List<class_303.class_7590> phaze$getVisibleMessages();
  
  @Accessor("messageHistory")
  class_8623<String> phaze$getMessageHistory();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ChatHudAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */