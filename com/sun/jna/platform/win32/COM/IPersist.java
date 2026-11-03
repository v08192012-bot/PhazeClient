package com.sun.jna.platform.win32.COM;

import com.sun.jna.platform.win32.Guid;

public interface IPersist extends IUnknown {
  Guid.CLSID GetClassID();
}


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\COM\IPersist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */