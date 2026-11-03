package vorga.phazeclient.api.system.discord.callbacks;

import com.sun.jna.Callback;
import vorga.phazeclient.api.system.discord.utils.DiscordUser;

public interface ReadyCallback extends Callback {
  void onReady(DiscordUser paramDiscordUser);
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\discord\callbacks\ReadyCallback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */