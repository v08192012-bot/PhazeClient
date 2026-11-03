/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import io.github.imurx.arboard.Clipboard;
/*     */ import io.github.imurx.arboard.ImageData;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_303;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_338;
/*     */ import net.minecraft.class_8623;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ import vorga.phazeclient.mixins.ChatHudAccessor;
/*     */ import vorga.phazeclient.mixins.NativeImageGetColorInvoker;
/*     */ 
/*     */ public final class ChatHelper extends Module {
/*  28 */   private static final ChatHelper INSTANCE = new ChatHelper();
/*  29 */   private static final Pattern COUNT_SUFFIX = Pattern.compile("\\s*\\((\\d+)x\\)\\s*$");
/*     */   
/*  31 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  32 */   public final BooleanSetting collapseRepeats = (new BooleanSetting("Collapse Repeats", "Collapse consecutive identical chat messages into one with a red (Nx) suffix"))
/*     */ 
/*     */     
/*  35 */     .setValue(true);
/*  36 */   public final SectionSetting screencopySection = new SectionSetting("Screencopy");
/*  37 */   public final BooleanSetting screencopy = (new BooleanSetting("Screencopy", "Copy taken screenshots to the system clipboard (F2)"))
/*     */ 
/*     */     
/*  40 */     .setValue(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public final SectionSetting historySection = new SectionSetting("Longer Chat History");
/*  55 */   public final BooleanSetting longerHistory = (new BooleanSetting("Longer Chat History", "Raise the chat scrollback / sent-message history cap above the vanilla 100-line limit"))
/*     */ 
/*     */     
/*  58 */     .setValue(false).onChange(v -> getInstance().phaze$applyHistoryLimit());
/*  59 */   public final ValueSetting historyLimit = (new ValueSetting("History Limit", "Maximum number of chat lines and recent messages kept in memory when Longer Chat History is on"))
/*     */ 
/*     */     
/*  62 */     .range(200, 32767).setValue(1000.0F)
/*  63 */     .visible(() -> Boolean.valueOf(this.longerHistory.isValue()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public final SectionSetting antiCapsSection = new SectionSetting("Anti Caps");
/*  76 */   public final BooleanSetting antiCaps = (new BooleanSetting("Anti Caps", "Auto-lowercase outgoing chat messages whose content is at least 75% uppercase"))
/*     */ 
/*     */     
/*  79 */     .setValue(false);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float ANTI_CAPS_THRESHOLD = 0.75F;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean bypass = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2561 lastBaseStyled;
/*     */ 
/*     */ 
/*     */   
/*     */   private String lastBaseRaw;
/*     */ 
/*     */ 
/*     */   
/*     */   private int lastCount;
/*     */ 
/*     */ 
/*     */   
/* 103 */   private final AtomicLong lastClipboardWriteMs = new AtomicLong(0L);
/* 104 */   private final AtomicLong lastClipboardErrorMs = new AtomicLong(0L);
/*     */   private static volatile Clipboard phaze$nativeClipboard;
/*     */   private static volatile boolean phaze$nativeClipboardBroken = false;
/*     */   
/*     */   private ChatHelper() {
/* 109 */     super("chat_helper", "Chat Helper", ModuleCategory.OTHER);
/* 110 */     this.collapseRepeats.setFullWidth(true);
/* 111 */     this.screencopy.setFullWidth(true);
/* 112 */     this.longerHistory.setFullWidth(true);
/* 113 */     this.historyLimit.setFullWidth(true);
/* 114 */     this.antiCaps.setFullWidth(true);
/* 115 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.collapseRepeats, (Setting)this.screencopySection, (Setting)this.screencopy, (Setting)this.historySection, (Setting)this.longerHistory, (Setting)this.historyLimit, (Setting)this.antiCapsSection, (Setting)this.antiCaps });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChatHelper getInstance() {
/* 122 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {
/* 127 */     super.activate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     phaze$applyHistoryLimit();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 138 */     super.deactivate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     phaze$applyHistoryLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void phaze$applyHistoryLimit() {
/* 162 */     class_310 client = class_310.method_1551();
/* 163 */     if (client == null || client.field_1705 == null) {
/*     */       return;
/*     */     }
/* 166 */     class_338 hud = client.field_1705.method_1743();
/* 167 */     if (hud == null) {
/*     */       return;
/*     */     }
/* 170 */     int cap = getChatHistoryLimit();
/* 171 */     ChatHudAccessor accessor = (ChatHudAccessor)hud;
/* 172 */     List<class_303> messages = accessor.phaze$getMessages();
/* 173 */     if (messages != null) {
/* 174 */       while (messages.size() > cap) {
/* 175 */         messages.remove(messages.size() - 1);
/*     */       }
/*     */     }
/* 178 */     List<class_303.class_7590> visibleMessages = accessor.phaze$getVisibleMessages();
/* 179 */     if (visibleMessages != null) {
/* 180 */       while (visibleMessages.size() > cap) {
/* 181 */         visibleMessages.remove(visibleMessages.size() - 1);
/*     */       }
/*     */     }
/* 184 */     class_8623<String> messageHistory = accessor.phaze$getMessageHistory();
/* 185 */     if (messageHistory != null) {
/* 186 */       while (messageHistory.size() > cap) {
/* 187 */         messageHistory.removeFirst();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 194 */     return "Tweaks for chat and screenshots: collapse repeated messages, copy F2 screenshots to clipboard";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 199 */     return "chat_helper.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 204 */     return 28.35F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconOffsetY() {
/* 209 */     return -2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBypassActive() {
/* 217 */     return this.bypass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2561 tryCollapse(class_338 hud, class_2561 incoming) {
/* 229 */     if (!isEnabled() || !this.collapseRepeats.isValue() || incoming == null) {
/* 230 */       return null;
/*     */     }
/*     */     
/* 233 */     String incomingRaw = stripSuffixRaw(incoming.getString());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     if (this.lastBaseRaw == null || !this.lastBaseRaw.equals(incomingRaw)) {
/* 239 */       this.lastBaseStyled = incoming;
/* 240 */       this.lastBaseRaw = incomingRaw;
/* 241 */       this.lastCount = 1;
/* 242 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     ChatHudAccessor accessor = (ChatHudAccessor)hud;
/* 249 */     List<class_303> messages = accessor.phaze$getMessages();
/* 250 */     if (messages == null || messages.isEmpty()) {
/* 251 */       return null;
/*     */     }
/* 253 */     class_303 removed = messages.remove(0);
/* 254 */     List<class_303.class_7590> visible = accessor.phaze$getVisibleMessages();
/* 255 */     if (visible != null && !visible.isEmpty() && removed != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 270 */       int removedTick = removed.comp_892();
/* 271 */       visible.removeIf(v -> (v != null && v.comp_895() == removedTick));
/*     */     } 
/*     */     
/* 274 */     this.lastCount++;
/*     */     
/* 276 */     class_5250 replacement = this.lastBaseStyled.method_27661().method_10852((class_2561)class_2561.method_43470(" (" + this.lastCount + "x)").method_27692(class_124.field_1061));
/* 277 */     return (class_2561)replacement;
/*     */   }
/*     */   
/*     */   public void runWithBypass(Runnable runnable) {
/* 281 */     this.bypass = true;
/*     */     try {
/* 283 */       runnable.run();
/*     */     } finally {
/* 285 */       this.bypass = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String stripSuffixRaw(String text) {
/* 295 */     if (text == null) {
/* 296 */       return "";
/*     */     }
/* 298 */     Matcher matcher = COUNT_SUFFIX.matcher(text);
/* 299 */     if (matcher.find()) {
/* 300 */       return text.substring(0, matcher.start());
/*     */     }
/* 302 */     return text;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCopyScreenshot() {
/* 307 */     return (isEnabled() && this.screencopy.isValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChatHistoryLimit() {
/* 321 */     if (!isEnabled() || !this.longerHistory.isValue()) {
/* 322 */       return 100;
/*     */     }
/* 324 */     return Math.max(100, Math.min(32767, this.historyLimit.getInt()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String maybeAntiCaps(String message) {
/* 347 */     if (!isEnabled() || !this.antiCaps.isValue() || message == null) {
/* 348 */       return message;
/*     */     }
/* 350 */     if (message.isEmpty() || message.startsWith("/")) {
/* 351 */       return message;
/*     */     }
/* 353 */     int upper = 0;
/* 354 */     int alpha = 0;
/* 355 */     for (int i = 0; i < message.length(); i++) {
/* 356 */       char c = message.charAt(i);
/* 357 */       if (Character.isLetter(c)) {
/* 358 */         alpha++;
/* 359 */         if (Character.isUpperCase(c)) {
/* 360 */           upper++;
/*     */         }
/*     */       } 
/*     */     } 
/* 364 */     if (alpha == 0) {
/* 365 */       return message;
/*     */     }
/* 367 */     if (upper / alpha >= 0.75F) {
/* 368 */       return message.toLowerCase(Locale.ROOT);
/*     */     }
/* 370 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyImageToClipboardAsync(class_1011 image, Consumer<class_2561> messageReceiver) {
/* 388 */     if (image == null) {
/*     */       return;
/*     */     }
/* 391 */     long now = System.currentTimeMillis();
/* 392 */     long previous = this.lastClipboardWriteMs.get();
/* 393 */     if (now - previous < 250L) {
/*     */       return;
/*     */     }
/* 396 */     this.lastClipboardWriteMs.set(now);
/*     */     
/* 398 */     int width = image.method_4307();
/* 399 */     int height = image.method_4323();
/* 400 */     if (width <= 0 || height <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 404 */     NativeImageGetColorInvoker invoker = (NativeImageGetColorInvoker)image;
/*     */     
/* 406 */     Thread t = new Thread(() -> {
/*     */           try {
/*     */             if (!phaze$nativeClipboardBroken) {
/*     */               try {
/*     */                 byte[] rgba = phaze$toNativeRgbaBytes(image, invoker, width, height);
/*     */                 phaze$copyNative(width, height, rgba);
/*     */                 return;
/* 413 */               } catch (UnsatisfiedLinkError e) {
/*     */                 phaze$nativeClipboardBroken = true;
/*     */               } 
/*     */             }
/*     */             if (!phaze$copyViaWindowsClipboardBridgeNativeImage(image)) {
/*     */               phaze$sendClipboardError(messageReceiver, "Screencopy Failed: native + bridge unavailable");
/*     */             }
/* 420 */           } catch (Throwable err) {
/*     */             phaze$sendClipboardError(messageReceiver, "Screencopy Failed: " + String.valueOf(err));
/*     */           } 
/*     */         }"Phaze-Screencopy");
/* 424 */     t.setDaemon(true);
/* 425 */     t.start();
/*     */   }
/*     */   
/*     */   private void phaze$sendClipboardError(Consumer<class_2561> messageReceiver, String message) {
/* 429 */     if (messageReceiver == null)
/* 430 */       return;  long now = System.currentTimeMillis();
/* 431 */     long last = this.lastClipboardErrorMs.get();
/* 432 */     if (now - last < 3000L)
/* 433 */       return;  this.lastClipboardErrorMs.set(now);
/* 434 */     messageReceiver.accept(class_2561.method_43470(message).method_27692(class_124.field_1061));
/*     */   }
/*     */   
/*     */   private static Clipboard phaze$getNativeClipboard() {
/* 438 */     Clipboard local = phaze$nativeClipboard;
/* 439 */     if (local != null) return local; 
/* 440 */     synchronized (ChatHelper.class) {
/* 441 */       if (phaze$nativeClipboard == null) {
/* 442 */         phaze$nativeClipboard = new Clipboard();
/*     */       }
/* 444 */       return phaze$nativeClipboard;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void phaze$copyNative(int width, int height, byte[] rgba) {
/* 449 */     ImageData data = new ImageData(width, height, rgba);
/*     */     try {
/* 451 */       phaze$getNativeClipboard().setImage(data);
/*     */     } finally {
/* 453 */       data.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] phaze$toNativeRgbaBytes(class_1011 image, NativeImageGetColorInvoker invoker, int width, int height) {
/* 460 */     ByteBuffer imageBytes = ByteBuffer.allocate(width * height * 4).order(ByteOrder.LITTLE_ENDIAN);
/* 461 */     for (int y = 0; y < height; y++) {
/* 462 */       for (int x = 0; x < width; x++) {
/* 463 */         imageBytes.putInt(invoker.phaze$invokeGetColor(x, y));
/*     */       }
/*     */     } 
/* 466 */     return imageBytes.array();
/*     */   }
/*     */   
/*     */   private static boolean phaze$copyViaWindowsClipboardBridgeNativeImage(class_1011 image) throws Exception {
/* 470 */     String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
/* 471 */     if (!os.contains("win")) return false; 
/* 472 */     Path temp = Files.createTempFile("phaze_screencopy_", ".png", (FileAttribute<?>[])new FileAttribute[0]);
/*     */     
/* 474 */     try { image.method_4314(temp);
/* 475 */       String p = temp.toAbsolutePath().toString().replace("'", "''");
/* 476 */       String script = "$path='" + p + "'; Add-Type -AssemblyName PresentationCore; $fs=[System.IO.File]::OpenRead($path); try { $dec=[System.Windows.Media.Imaging.PngBitmapDecoder]::new($fs,[System.Windows.Media.Imaging.BitmapCreateOptions]::PreservePixelFormat,[System.Windows.Media.Imaging.BitmapCacheOption]::OnLoad); [System.Windows.Clipboard]::SetImage($dec.Frames[0]); } finally { $fs.Close() }";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 486 */       Process proc = (new ProcessBuilder(new String[] { "powershell.exe", "-NoProfile", "-STA", "-Command", script })).redirectErrorStream(true).start();
/* 487 */       return (proc.waitFor() == 0); }
/*     */     finally { 
/* 489 */       try { Files.deleteIfExists(temp); } catch (Throwable throwable) {} }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\ChatHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */