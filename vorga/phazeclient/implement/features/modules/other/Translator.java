/*      */ package vorga.phazeclient.implement.features.modules.other;
/*      */ 
/*      */ import com.google.gson.JsonArray;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.google.gson.JsonObject;
/*      */ import com.google.gson.JsonParser;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.URLEncoder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.ThreadFactory;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.class_124;
/*      */ import net.minecraft.class_2561;
/*      */ import net.minecraft.class_2568;
/*      */ import net.minecraft.class_2583;
/*      */ import net.minecraft.class_2588;
/*      */ import net.minecraft.class_303;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_338;
/*      */ import net.minecraft.class_5250;
/*      */ import net.minecraft.class_7417;
/*      */ import net.minecraft.class_7469;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import vorga.phazeclient.api.feature.module.Module;
/*      */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*      */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.TextSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*      */ import vorga.phazeclient.mixins.ChatHudAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Translator
/*      */   extends Module
/*      */ {
/*   97 */   private static final Logger LOG = LoggerFactory.getLogger("PhazeTranslator");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private static final Set<String> CLIENT_SYSTEM_KEY_PREFIXES = Set.of("debug.", "screenshot.", "narrator.", "commands.", "key.", "options.", "chat.cannotSend", "chat.disabled");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   private static final Map<String, String> LANGUAGE_NAME_TO_CODE = Map.ofEntries((Map.Entry<? extends String, ? extends String>[])new Map.Entry[] { 
/*  159 */         Map.entry("Russian", "ru"), 
/*  160 */         Map.entry("English", "en"), 
/*  161 */         Map.entry("Ukrainian", "uk"), 
/*  162 */         Map.entry("Belarusian", "be"), 
/*  163 */         Map.entry("Spanish", "es"), 
/*  164 */         Map.entry("French", "fr"), 
/*  165 */         Map.entry("German", "de"), 
/*  166 */         Map.entry("Italian", "it"), 
/*  167 */         Map.entry("Portuguese", "pt"), 
/*  168 */         Map.entry("Polish", "pl"), 
/*  169 */         Map.entry("Turkish", "tr"), 
/*  170 */         Map.entry("Chinese", "zh-CN"), 
/*  171 */         Map.entry("Japanese", "ja"), 
/*  172 */         Map.entry("Korean", "ko"), 
/*  173 */         Map.entry("Arabic", "ar"), 
/*  174 */         Map.entry("Hindi", "hi"), 
/*  175 */         Map.entry("Vietnamese", "vi"), 
/*  176 */         Map.entry("Indonesian", "id") });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   private static final String[] LANGUAGE_NAMES = new String[] { "Russian", "English", "Ukrainian", "Belarusian", "Spanish", "French", "German", "Italian", "Portuguese", "Polish", "Turkish", "Chinese", "Japanese", "Korean", "Arabic", "Hindi", "Vietnamese", "Indonesian" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  225 */   private static final Map<String, String> SLANG_EXPANSIONS = Map.ofEntries((Map.Entry<? extends String, ? extends String>[])new Map.Entry[] { 
/*  226 */         Map.entry("lol", "haha"), 
/*  227 */         Map.entry("lmao", "haha"), 
/*  228 */         Map.entry("lmfao", "haha"), 
/*  229 */         Map.entry("rofl", "haha"), 
/*  230 */         Map.entry("xd", "haha"), 
/*  231 */         Map.entry("kek", "haha"), 
/*  232 */         Map.entry("brb", "be right back"), 
/*  233 */         Map.entry("bbl", "be back later"), 
/*  234 */         Map.entry("afk", "away from keyboard"), 
/*  235 */         Map.entry("wtf", "what the heck"), 
/*  236 */         Map.entry("wth", "what the heck"), 
/*  237 */         Map.entry("omg", "oh my god"), 
/*  238 */         Map.entry("omw", "on my way"), 
/*  239 */         Map.entry("idk", "I don't know"), 
/*  240 */         Map.entry("dunno", "I don't know"), 
/*  241 */         Map.entry("idc", "I don't care"), 
/*  242 */         Map.entry("imo", "in my opinion"), 
/*  243 */         Map.entry("imho", "in my opinion"), 
/*  244 */         Map.entry("tbh", "to be honest"), 
/*  245 */         Map.entry("tbf", "to be fair"), 
/*  246 */         Map.entry("ngl", "not gonna lie"), 
/*  247 */         Map.entry("ikr", "I know right"), 
/*  248 */         Map.entry("afaik", "as far as I know"), 
/*  249 */         Map.entry("iirc", "if I recall correctly"), 
/*  250 */         Map.entry("smh", "shaking my head"), 
/*  251 */         Map.entry("fyi", "for your information"), 
/*  252 */         Map.entry("tldr", "too long didn't read"), 
/*  253 */         Map.entry("btw", "by the way"), 
/*  254 */         Map.entry("nvm", "never mind"), 
/*  255 */         Map.entry("asap", "as soon as possible"), 
/*  256 */         Map.entry("thx", "thanks"), 
/*  257 */         Map.entry("ty", "thank you"), 
/*  258 */         Map.entry("tysm", "thank you so much"), 
/*  259 */         Map.entry("np", "no problem"), 
/*  260 */         Map.entry("yw", "you're welcome"), 
/*  261 */         Map.entry("pls", "please"), 
/*  262 */         Map.entry("plz", "please"), 
/*  263 */         Map.entry("rly", "really"), 
/*  264 */         Map.entry("srsly", "seriously"), 
/*  265 */         Map.entry("ofc", "of course"), 
/*  266 */         Map.entry("bc", "because"), 
/*  267 */         Map.entry("cuz", "because"), 
/*  268 */         Map.entry("coz", "because"), 
/*  269 */         Map.entry("rn", "right now"), 
/*  270 */         Map.entry("fr", "for real"), 
/*  271 */         Map.entry("ig", "I guess"), 
/*  272 */         Map.entry("irl", "in real life"), 
/*  273 */         Map.entry("dm", "direct message"), 
/*  274 */         Map.entry("ily", "I love you"), 
/*  275 */         Map.entry("wbu", "what about you"), 
/*  276 */         Map.entry("hbu", "how about you"), 
/*  277 */         Map.entry("ur", "your"), 
/*  278 */         Map.entry("sup", "what's up"), 
/*  279 */         Map.entry("ppl", "people"), 
/*  280 */         Map.entry("smth", "something"), 
/*  281 */         Map.entry("smthn", "something"), 
/*  282 */         Map.entry("smb", "somebody"), 
/*  283 */         Map.entry("gonna", "going to"), 
/*  284 */         Map.entry("wanna", "want to"), 
/*  285 */         Map.entry("gotta", "got to"), 
/*  286 */         Map.entry("gimme", "give me"), 
/*  287 */         Map.entry("lemme", "let me"), 
/*  288 */         Map.entry("kinda", "kind of"), 
/*  289 */         Map.entry("sorta", "sort of"), 
/*  290 */         Map.entry("gg", "good game"), 
/*  291 */         Map.entry("ggs", "good games"), 
/*  292 */         Map.entry("ggwp", "good game well played"), 
/*  293 */         Map.entry("wp", "well played"), 
/*  294 */         Map.entry("ez", "easy"), 
/*  295 */         Map.entry("gl", "good luck"), 
/*  296 */         Map.entry("hf", "have fun"), 
/*  297 */         Map.entry("glhf", "good luck have fun"), 
/*  298 */         Map.entry("nt", "nice try"), 
/*  299 */         Map.entry("rip", "rest in peace"), 
/*  300 */         Map.entry("sus", "suspicious"), 
/*  301 */         Map.entry("bff", "best friend"), 
/*  302 */         Map.entry("bf", "boyfriend"), 
/*  303 */         Map.entry("gf", "girlfriend"), 
/*  304 */         Map.entry("op", "overpowered"), 
/*  305 */         Map.entry("noob", "newbie"), 
/*  306 */         Map.entry("tho", "though"), 
/*  307 */         Map.entry("thru", "through"), 
/*  308 */         Map.entry("yk", "you know"), 
/*  309 */         Map.entry("kk", "okay"), 
/*  310 */         Map.entry("tmrw", "tomorrow"), 
/*  311 */         Map.entry("jk", "just kidding"), 
/*  312 */         Map.entry("ffs", "for fucks sake"), 
/*  313 */         Map.entry("goat", "greatest of all time"), 
/*  314 */         Map.entry("hbd", "happy birthday"), 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  323 */         Map.entry("mend", "Mending enchantment") });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Pattern SLANG_PATTERN;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  334 */     SLANG_PATTERN = Pattern.compile("\\b(" + (String)SLANG_EXPANSIONS
/*  335 */         .keySet().stream()
/*  336 */         .sorted((a, b) -> Integer.compare(b.length(), a.length()))
/*  337 */         .map(Pattern::quote)
/*  338 */         .reduce((a, b) -> a + "|" + a)
/*  339 */         .orElse("") + ")\\b", 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  371 */   private static final Pattern PLAYER_CHAT_HEURISTIC = Pattern.compile("^.*?\\p{L}[\\p{L}\\p{N}_\\-]+\\s*[:»]\\s+.+$", 32);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  392 */   private static final Pattern HEADER_PATTERN = Pattern.compile("^(.*?[:>»])\\s+(.*)$", 32);
/*      */ 
/*      */   
/*      */   private static final int CACHE_CAPACITY = 256;
/*      */ 
/*      */   
/*      */   private static final int MAX_PENDING = 32;
/*      */   
/*  400 */   public final SectionSetting generalSection = new SectionSetting("General");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  408 */   public final SelectSetting targetLanguage = (new SelectSetting("Target Language", "Language to translate incoming chat into"))
/*      */ 
/*      */     
/*  411 */     .value(LANGUAGE_NAMES).selected("Russian");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  419 */   public final MultiSelectSetting translationOptions = (new MultiSelectSetting("Translation Options", "Choose which translator behaviors stay enabled"))
/*      */ 
/*      */     
/*  422 */     .value(new String[] {
/*      */ 
/*      */ 
/*      */         
/*      */         "Auto-Detect Source", "Show Direction", "Show Original On Hover", "Skip Nicknames", "Skip Same Language", "Replace Message", "Only Players", "Expand Slang"
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  431 */       }).selected(new String[] { "Auto-Detect Source", "Show Direction", "Show Original On Hover", "Skip Nicknames", "Skip Same Language", "Expand Slang" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike showSuffix = () -> this.translationOptions.isSelected("Show Direction");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike showOriginalOnHover = () -> this.translationOptions.isSelected("Show Original On Hover");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike skipNicknames = () -> this.translationOptions.isSelected("Skip Nicknames");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike skipSameLanguage = () -> this.translationOptions.isSelected("Skip Same Language");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike replaceOriginal = () -> this.translationOptions.isSelected("Replace Message");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike onlyPlayers = () -> this.translationOptions.isSelected("Only Players");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike autoDetectSource = () -> this.translationOptions.isSelected("Auto-Detect Source");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  554 */   public final SelectSetting sourceLanguage = (new SelectSetting("Source Language", "Source language (used only when Auto-Detect Source is off)"))
/*      */ 
/*      */     
/*  557 */     .value(LANGUAGE_NAMES).selected("English")
/*  558 */     .visible(() -> Boolean.valueOf(!this.autoDetectSource.isValue()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  565 */   public final ValueSetting minLength = (new ValueSetting("Min Length", "Skip messages whose translatable body has fewer characters than this"))
/*      */ 
/*      */     
/*  568 */     .range(1, 64).step(1.0F).setValue(3.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final BooleanLike expandSlang = () -> this.translationOptions.isSelected("Expand Slang");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  588 */   public final SectionSetting providerSection = new SectionSetting("Provider");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  597 */   public final SelectSetting provider = (new SelectSetting("Provider", "Translation backend. Google = direct mobile endpoint (fast, free). Apify = run actor through Apify API (slower, uses your token)."))
/*      */ 
/*      */     
/*  600 */     .value(new String[] { "Google", "Apify" }).selected("Google");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  606 */   public final TextSetting apifyToken = (new TextSetting("Apify Token", "Personal API token for api.apify.com (used only when Provider=Apify)"))
/*      */ 
/*      */     
/*  609 */     .setText("")
/*  610 */     .visible(() -> Boolean.valueOf(this.provider.isSelected("Apify")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  622 */   public final TextSetting apifyActor = (new TextSetting("Apify Actor", "Actor ID in username~actorname form"))
/*      */ 
/*      */     
/*  625 */     .setText("web.harvester~google-translator")
/*  626 */     .visible(() -> Boolean.valueOf(this.provider.isSelected("Apify")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  634 */   private final Map<String, String> cache = new LinkedHashMap<String, String>(64, 0.75F, true)
/*      */     {
/*      */       protected boolean removeEldestEntry(Map.Entry<String, String> eldest)
/*      */       {
/*  638 */         return (size() > 256);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ExecutorService executor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final AtomicInteger pending;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean bypass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface BooleanLike
/*      */   {
/*      */     boolean isValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  695 */   private static final Translator INSTANCE = new Translator();
/*      */   
/*      */   public static Translator getInstance() {
/*  698 */     return INSTANCE;
/*      */   }
/*      */   
/*      */   private Translator() {
/*  702 */     super("translator", "Translator", ModuleCategory.OTHER); this.executor = Executors.newFixedThreadPool(2, r -> { Thread t = new Thread(r, "phaze-translator"); t.setDaemon(true); return (ThreadFactory)t;
/*  703 */         }); this.pending = new AtomicInteger(0); this.bypass = false; this.targetLanguage.setFullWidth(true);
/*  704 */     this.translationOptions.setFullWidth(true);
/*  705 */     this.sourceLanguage.setFullWidth(true);
/*  706 */     this.minLength.setFullWidth(true);
/*  707 */     this.provider.setFullWidth(true);
/*  708 */     this.apifyToken.setFullWidth(true);
/*  709 */     this.apifyActor.setFullWidth(true);
/*  710 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.targetLanguage, (Setting)this.translationOptions, (Setting)this.sourceLanguage, (Setting)this.minLength, (Setting)this.providerSection, (Setting)this.provider, (Setting)this.apifyToken, (Setting)this.apifyActor });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  716 */     return "Auto-translate incoming chat messages and post the translation as a new row below the original";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getIcon() {
/*  721 */     return "translator.png";
/*      */   }
/*      */ 
/*      */   
/*      */   public float getIconSize() {
/*  726 */     return 21.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBypassActive() {
/*  731 */     return this.bypass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onIncomingChat(class_2561 message, class_7469 signature) {
/*      */     String header, cached;
/*  742 */     if (!isEnabled() || this.bypass || message == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  752 */     if (isClientSystemMessage(message)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  769 */     if (this.onlyPlayers.isValue() && !isPlayerChat(message, signature)) {
/*      */       return;
/*      */     }
/*      */     
/*  773 */     String raw = message.getString();
/*  774 */     if (raw == null || raw.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  782 */     if (this.skipNicknames.isValue()) {
/*  783 */       Matcher matcher = HEADER_PATTERN.matcher(raw);
/*  784 */       if (matcher.matches()) {
/*  785 */         header = matcher.group(1) + " ";
/*  786 */         body = matcher.group(2);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  791 */         header = "";
/*  792 */         body = raw;
/*      */       } 
/*      */     } else {
/*  795 */       header = "";
/*  796 */       body = raw;
/*      */     } 
/*      */     
/*  799 */     if (body == null) {
/*      */       return;
/*      */     }
/*  802 */     String body = body.trim();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  810 */     if (translationWeight(body) < (int)this.minLength.getValue()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  818 */     if (isMostlySymbolic(body)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  826 */     String target = languageNameToCode(this.targetLanguage.getSelected());
/*  827 */     if (target == null || target.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  835 */     String cacheKey = target + "|" + target;
/*      */     
/*  837 */     synchronized (this.cache) {
/*  838 */       cached = this.cache.get(cacheKey);
/*      */     } 
/*  840 */     if (cached != null) {
/*      */ 
/*      */ 
/*      */       
/*  844 */       int sep = cached.indexOf('|');
/*  845 */       if (sep >= 0) {
/*  846 */         String detected = cached.substring(0, sep);
/*  847 */         String translation = cached.substring(sep + 1);
/*  848 */         if (this.skipSameLanguage.isValue() && detected.equalsIgnoreCase(target)) {
/*      */           return;
/*      */         }
/*  851 */         postTranslation(message, raw, header, translation, detected, target);
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  860 */     if (this.pending.get() >= 32) {
/*      */       return;
/*      */     }
/*  863 */     this.pending.incrementAndGet();
/*  864 */     class_2561 messageF = message;
/*  865 */     String rawF = raw;
/*  866 */     String headerF = header;
/*  867 */     String bodyF = body;
/*  868 */     String targetF = target;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  879 */     String sourceF = this.autoDetectSource.isValue() ? "auto" : languageNameToCode(this.sourceLanguage.getSelected());
/*  880 */     this.executor.execute(() -> {
/*      */           try {
/*      */             translateAndPost(messageF, rawF, headerF, bodyF, sourceF, targetF, cacheKey);
/*  883 */           } catch (Throwable t) {
/*      */             LOG.warn("translation failed for body='{}': {}", bodyF, t.toString());
/*      */           } finally {
/*      */             this.pending.decrementAndGet();
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void translateAndPost(class_2561 original, String raw, String header, String body, String source, String target, String cacheKey) {
/*  898 */     String result[], apiInput = shouldExpandSlang(body, source) ? applySlangExpansion(body) : body;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  905 */     if (this.provider.isSelected("Apify")) {
/*  906 */       result = translateApify(apiInput, source, target);
/*      */     } else {
/*  908 */       result = translateGoogleDirect(apiInput, source, target);
/*      */     } 
/*  910 */     if (result == null) {
/*      */       return;
/*      */     }
/*  913 */     String detected = result[0];
/*  914 */     String translation = result[1];
/*  915 */     if (translation == null || translation.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  919 */     synchronized (this.cache) {
/*  920 */       this.cache.put(cacheKey, detected + "|" + detected);
/*      */     } 
/*      */     
/*  923 */     if (this.skipSameLanguage.isValue() && detected.equalsIgnoreCase(target)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  930 */     if (translation.trim().equalsIgnoreCase(body.trim())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  938 */     String displayDetected = this.autoDetectSource.isValue() ? detected : source;
/*  939 */     postTranslation(original, raw, header, translation, displayDetected, target);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postTranslation(class_2561 original, String originalRaw, String header, String translation, String detectedSrc, String target) {
/*  952 */     class_310 client = class_310.method_1551();
/*  953 */     if (client == null) {
/*      */       return;
/*      */     }
/*  956 */     client.execute(() -> {
/*      */           class_338 chat = (client.field_1705 != null) ? client.field_1705.method_1743() : null;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           if (chat == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  968 */           String suffix = this.showSuffix.isValue() ? (" (" + ((detectedSrc == null || detectedSrc.isEmpty()) ? "auto" : detectedSrc) + "2" + target + ")") : null;
/*      */           class_5250 row = buildStyledTranslationRow(original, header, translation, suffix);
/*      */           this.bypass = true;
/*      */           try {
/*      */             if (this.replaceOriginal.isValue() && originalRaw != null) {
/*      */               removeOriginal(chat, originalRaw);
/*      */             }
/*      */             chat.method_1812((class_2561)row);
/*      */           } finally {
/*      */             this.bypass = false;
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class_5250 buildStyledTranslationRow(class_2561 original, String header, String translation, String suffix) {
/* 1023 */     if (original == null) {
/*      */       
/* 1025 */       class_5250 row = class_2561.method_43473();
/* 1026 */       if (header != null && !header.isEmpty()) {
/* 1027 */         row.method_10852((class_2561)class_2561.method_43470(header));
/*      */       }
/* 1029 */       row.method_10852((class_2561)class_2561.method_43470(translation));
/* 1030 */       if (suffix != null) {
/* 1031 */         row.method_10852((class_2561)class_2561.method_43470(suffix).method_27695(new class_124[] { class_124.field_1080, class_124.field_1056 }));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1036 */       return row;
/*      */     } 
/*      */     
/* 1039 */     String raw = original.getString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1047 */     int bodyStart = (header == null) ? 0 : header.length();
/*      */ 
/*      */ 
/*      */     
/* 1051 */     if (bodyStart < 0 || bodyStart > raw.length()) {
/* 1052 */       bodyStart = 0;
/*      */     }
/* 1054 */     int bodyStartF = bodyStart;
/* 1055 */     int bodyEndF = raw.length();
/*      */     
/* 1057 */     class_5250 result = class_2561.method_43473();
/* 1058 */     int[] cursor = { 0 };
/* 1059 */     boolean[] translationInserted = { false };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1064 */     original.method_27658((style, str) -> {
/*      */           int segStart = cursor[0];
/*      */ 
/*      */           
/*      */           int segEnd = segStart + str.length();
/*      */ 
/*      */           
/*      */           cursor[0] = segEnd;
/*      */           
/*      */           int prefixLen = Math.max(0, Math.min(str.length(), bodyStartF - segStart));
/*      */           
/*      */           int suffixStart = Math.min(str.length(), Math.max(0, bodyEndF - segStart));
/*      */           
/*      */           if (prefixLen > 0) {
/*      */             String prefix = str.substring(0, prefixLen);
/*      */             
/*      */             result.method_10852((class_2561)class_2561.method_43470(prefix).method_10862(style));
/*      */           } 
/*      */           
/* 1083 */           boolean bodyOverlaps = (segStart < bodyEndF && segEnd > bodyStartF);
/*      */ 
/*      */           
/*      */           if (bodyOverlaps && !translationInserted[0]) {
/*      */             result.method_10852((class_2561)class_2561.method_43470(translation).method_10862(style));
/*      */ 
/*      */             
/*      */             translationInserted[0] = true;
/*      */           } 
/*      */ 
/*      */           
/*      */           if (suffixStart < str.length()) {
/*      */             String tail = str.substring(suffixStart);
/*      */             
/*      */             result.method_10852((class_2561)class_2561.method_43470(tail).method_10862(style));
/*      */           } 
/*      */           
/*      */           return Optional.empty();
/*      */         }class_2583.field_24360);
/*      */     
/* 1103 */     if (!translationInserted[0]) {
/* 1104 */       result.method_10852((class_2561)class_2561.method_43470(translation));
/*      */     }
/*      */     
/* 1107 */     if (suffix != null)
/*      */     {
/*      */ 
/*      */       
/* 1111 */       result.method_10852((class_2561)class_2561.method_43470(suffix).method_27695(new class_124[] { class_124.field_1080, class_124.field_1056 }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1124 */     if (this.showOriginalOnHover.isValue()) {
/* 1125 */       class_2568 hover = buildOriginalHover(original);
/* 1126 */       if (hover != null) {
/* 1127 */         class_5250 wrapped = class_2561.method_43473().method_10862(class_2583.field_24360.method_10949(hover));
/* 1128 */         wrapped.method_10852((class_2561)result);
/* 1129 */         return wrapped;
/*      */       } 
/*      */     } 
/* 1132 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class_2568 buildOriginalHover(class_2561 original) {
/* 1147 */     if (original == null) {
/* 1148 */       return null;
/*      */     }
/* 1150 */     String raw = original.getString();
/* 1151 */     if (raw == null || raw.isEmpty()) {
/* 1152 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1157 */     class_5250 tooltip = class_2561.method_43473().method_10852((class_2561)class_2561.method_43470("Original message:").method_27695(new class_124[] { class_124.field_1080, class_124.field_1056 })).method_10852((class_2561)class_2561.method_43470("\n")).method_10852(original);
/* 1158 */     return new class_2568(class_2568.class_5247.field_24342, tooltip);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String languageNameToCode(String name) {
/* 1171 */     if (name == null || name.isEmpty()) return "ru"; 
/* 1172 */     String code = LANGUAGE_NAME_TO_CODE.get(name);
/* 1173 */     if (code != null) return code;
/*      */ 
/*      */ 
/*      */     
/* 1177 */     return name.trim().toLowerCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isPlayerChat(class_2561 message, class_7469 signature) {
/* 1207 */     if (signature != null) return true; 
/* 1208 */     if (message == null) return false; 
/* 1209 */     String raw = message.getString();
/* 1210 */     if (raw == null || raw.isEmpty()) return false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1216 */     if (raw.startsWith("<")) {
/* 1217 */       int close = raw.indexOf("> ");
/* 1218 */       if (close > 1 && close < raw.length() - 2) {
/* 1219 */         String name = raw.substring(1, close);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1225 */         if (!name.isEmpty() && name.indexOf('<') < 0 && containsLetter(name)) {
/* 1226 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1233 */     return PLAYER_CHAT_HEURISTIC.matcher(raw).matches();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean containsLetter(String s) {
/* 1238 */     for (int i = 0; i < s.length(); i++) {
/* 1239 */       if (Character.isLetter(s.codePointAt(i))) return true; 
/*      */     } 
/* 1241 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldExpandSlang(String body, String source) {
/* 1263 */     if (!this.expandSlang.isValue()) return false; 
/* 1264 */     if (body == null || body.isEmpty()) return false; 
/* 1265 */     if (!isAsciiLettersOnly(body)) return false; 
/* 1266 */     return ("auto".equals(source) || "en".equalsIgnoreCase(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAsciiLettersOnly(String s) {
/* 1278 */     for (int i = 0; i < s.length(); ) {
/* 1279 */       int cp = s.codePointAt(i);
/* 1280 */       if (Character.isLetter(cp)) {
/* 1281 */         boolean asciiLetter = ((cp >= 65 && cp <= 90) || (cp >= 97 && cp <= 122));
/* 1282 */         if (!asciiLetter) return false; 
/*      */       } 
/* 1284 */       i += Character.charCount(cp);
/*      */     } 
/* 1286 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String applySlangExpansion(String input) {
/* 1304 */     Matcher m = SLANG_PATTERN.matcher(input);
/* 1305 */     StringBuilder out = new StringBuilder(input.length() + 16);
/* 1306 */     while (m.find()) {
/* 1307 */       String key = m.group(1).toLowerCase(Locale.ROOT);
/* 1308 */       String replacement = SLANG_EXPANSIONS.getOrDefault(key, m.group(1));
/* 1309 */       m.appendReplacement(out, Matcher.quoteReplacement(replacement));
/*      */     } 
/* 1311 */     m.appendTail(out);
/* 1312 */     return out.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isClientSystemMessage(class_2561 message) {
/*      */     class_2588 translatable;
/* 1333 */     if (message == null) {
/* 1334 */       return false;
/*      */     }
/* 1336 */     class_7417 content = message.method_10851();
/* 1337 */     if (content instanceof class_2588) { translatable = (class_2588)content; }
/* 1338 */     else { return false; }
/*      */     
/* 1340 */     String key = translatable.method_11022();
/* 1341 */     if (key == null || key.isEmpty()) {
/* 1342 */       return false;
/*      */     }
/* 1344 */     for (String prefix : CLIENT_SYSTEM_KEY_PREFIXES) {
/* 1345 */       if (key.startsWith(prefix)) {
/* 1346 */         return true;
/*      */       }
/*      */     } 
/* 1349 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeOriginal(class_338 chat, String originalRaw) {
/* 1371 */     ChatHudAccessor accessor = (ChatHudAccessor)chat;
/* 1372 */     List<class_303> messages = accessor.phaze$getMessages();
/* 1373 */     if (messages == null || messages.isEmpty()) {
/*      */       return;
/*      */     }
/* 1376 */     int targetIdx = -1;
/* 1377 */     class_303 targetLine = null;
/* 1378 */     for (int i = 0; i < messages.size(); i++) {
/* 1379 */       class_303 line = messages.get(i);
/* 1380 */       if (line != null && line.comp_893() != null && originalRaw.equals(line.comp_893().getString())) {
/* 1381 */         targetIdx = i;
/* 1382 */         targetLine = line;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1386 */     if (targetIdx < 0 || targetLine == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1390 */     messages.remove(targetIdx);
/* 1391 */     List<class_303.class_7590> visible = accessor.phaze$getVisibleMessages();
/* 1392 */     if (visible != null && !visible.isEmpty()) {
/* 1393 */       int removedTick = targetLine.comp_892();
/* 1394 */       visible.removeIf(v -> (v != null && v.comp_895() == removedTick));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] translateGoogleDirect(String text, String source, String target) {
/*      */     try {
/* 1423 */       String encoded = URLEncoder.encode(text, StandardCharsets.UTF_8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1429 */       String slParam = (source == null || source.isEmpty()) ? "auto" : source;
/*      */ 
/*      */ 
/*      */       
/* 1433 */       String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + URLEncoder.encode(slParam, StandardCharsets.UTF_8) + "&tl=" + URLEncoder.encode(target, StandardCharsets.UTF_8) + "&dt=t&q=" + encoded;
/*      */ 
/*      */       
/* 1436 */       URL url = URI.create(urlStr).toURL();
/* 1437 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*      */       try {
/* 1439 */         conn.setRequestMethod("GET");
/* 1440 */         conn.setConnectTimeout(5000);
/* 1441 */         conn.setReadTimeout(8000);
/* 1442 */         conn.setRequestProperty("Accept", "application/json");
/*      */ 
/*      */         
/* 1445 */         conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1450 */         conn.setInstanceFollowRedirects(true);
/*      */         
/* 1452 */         int status = conn.getResponseCode();
/* 1453 */         if (status != 200) {
/* 1454 */           LOG.warn("Google Translate returned HTTP {}", Integer.valueOf(status));
/* 1455 */           return null;
/*      */         } 
/* 1457 */         String body = readAll(conn.getInputStream());
/* 1458 */         JsonElement parsed = JsonParser.parseString(body);
/* 1459 */         if (!parsed.isJsonArray()) {
/* 1460 */           return null;
/*      */         }
/* 1462 */         JsonArray root = parsed.getAsJsonArray();
/*      */ 
/*      */ 
/*      */         
/* 1466 */         StringBuilder sb = new StringBuilder();
/* 1467 */         if (root.size() > 0 && root.get(0).isJsonArray()) {
/* 1468 */           JsonArray chunks = root.get(0).getAsJsonArray();
/* 1469 */           for (JsonElement chunkEl : chunks) {
/* 1470 */             if (!chunkEl.isJsonArray())
/* 1471 */               continue;  JsonArray chunk = chunkEl.getAsJsonArray();
/* 1472 */             if (chunk.size() == 0)
/* 1473 */               continue;  JsonElement piece = chunk.get(0);
/* 1474 */             if (piece != null && piece.isJsonPrimitive()) {
/* 1475 */               sb.append(piece.getAsString());
/*      */             }
/*      */           } 
/*      */         } 
/* 1479 */         String translated = sb.toString();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1484 */         String detected = "auto";
/* 1485 */         if (root.size() >= 3 && root.get(2).isJsonPrimitive()) {
/* 1486 */           detected = root.get(2).getAsString();
/*      */         }
/* 1488 */         return new String[] { detected, translated };
/*      */       } finally {
/* 1490 */         conn.disconnect();
/*      */       } 
/* 1492 */     } catch (Throwable t) {
/* 1493 */       LOG.warn("Google Translate request failed: {}", t.toString());
/* 1494 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] translateApify(String text, String source, String target) {
/*      */     try {
/* 1514 */       String token = this.apifyToken.getText();
/* 1515 */       String actor = this.apifyActor.getText();
/* 1516 */       if (token == null || token.isEmpty() || actor == null || actor.isEmpty()) {
/* 1517 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1521 */       String urlStr = "https://api.apify.com/v2/acts/" + actor + "/run-sync-get-dataset-items?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
/* 1522 */       URL url = URI.create(urlStr).toURL();
/* 1523 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*      */       try {
/* 1525 */         conn.setRequestMethod("POST");
/* 1526 */         conn.setDoOutput(true);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1531 */         conn.setConnectTimeout(15000);
/* 1532 */         conn.setReadTimeout(60000);
/* 1533 */         conn.setRequestProperty("Accept", "application/json");
/* 1534 */         conn.setRequestProperty("Content-Type", "application/json");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1541 */         String slField = (source == null || source.isEmpty()) ? "auto" : source;
/*      */ 
/*      */ 
/*      */         
/* 1545 */         String payload = "{\"text\":" + jsonString(text) + ",\"sourceLanguage\":" + jsonString(slField) + ",\"targetLanguage\":" + jsonString(target) + "}";
/*      */         
/* 1547 */         OutputStream os = conn.getOutputStream(); 
/* 1548 */         try { os.write(payload.getBytes(StandardCharsets.UTF_8));
/* 1549 */           if (os != null) os.close();  } catch (Throwable throwable) { if (os != null)
/*      */             try { os.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1551 */          int status = conn.getResponseCode();
/* 1552 */         if (status / 100 != 2) {
/* 1553 */           LOG.warn("Apify returned HTTP {}", Integer.valueOf(status));
/* 1554 */           return null;
/*      */         } 
/* 1556 */         String body = readAll(conn.getInputStream());
/* 1557 */         JsonElement parsed = JsonParser.parseString(body);
/* 1558 */         if (!parsed.isJsonArray()) {
/* 1559 */           return null;
/*      */         }
/* 1561 */         JsonArray items = parsed.getAsJsonArray();
/* 1562 */         if (items.size() == 0 || !items.get(0).isJsonObject()) {
/* 1563 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1573 */         JsonObject item = items.get(0).getAsJsonObject();
/*      */ 
/*      */         
/* 1576 */         String translated = (item.has("translatedText") && item.get("translatedText").isJsonPrimitive()) ? item.get("translatedText").getAsString() : null;
/*      */ 
/*      */         
/* 1579 */         String detectedFull = (item.has("sourceLanguage") && item.get("sourceLanguage").isJsonPrimitive()) ? item.get("sourceLanguage").getAsString() : "auto";
/* 1580 */         if (translated == null) {
/* 1581 */           return null;
/*      */         }
/* 1583 */         return new String[] { shortenLanguageName(detectedFull), translated };
/*      */       } finally {
/* 1585 */         conn.disconnect();
/*      */       } 
/* 1587 */     } catch (Throwable t) {
/* 1588 */       LOG.warn("Apify translation failed: {}", t.toString());
/* 1589 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String jsonString(String s) {
/* 1595 */     StringBuilder sb = new StringBuilder(s.length() + 2);
/* 1596 */     sb.append('"');
/* 1597 */     for (int i = 0; i < s.length(); i++) {
/* 1598 */       char c = s.charAt(i);
/* 1599 */       switch (c) { case '"':
/* 1600 */           sb.append("\\\""); break;
/* 1601 */         case '\\': sb.append("\\\\"); break;
/* 1602 */         case '\n': sb.append("\\n"); break;
/* 1603 */         case '\r': sb.append("\\r"); break;
/* 1604 */         case '\t': sb.append("\\t"); break;
/*      */         default:
/* 1606 */           if (c < ' ') {
/* 1607 */             sb.append(String.format("\\u%04x", new Object[] { Integer.valueOf(c) })); break;
/*      */           } 
/* 1609 */           sb.append(c);
/*      */           break; }
/*      */     
/*      */     } 
/* 1613 */     sb.append('"');
/* 1614 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String shortenLanguageName(String full) {
/* 1624 */     if (full == null) return "auto"; 
/* 1625 */     String f = full.trim().toLowerCase();
/* 1626 */     switch (f) { case "english":
/* 1627 */         return "en";
/* 1628 */       case "russian": return "ru";
/* 1629 */       case "ukrainian": return "uk";
/* 1630 */       case "belarusian": return "be";
/* 1631 */       case "spanish": return "es";
/* 1632 */       case "french": return "fr";
/* 1633 */       case "german": return "de";
/* 1634 */       case "italian": return "it";
/* 1635 */       case "portuguese": return "pt";
/* 1636 */       case "polish": return "pl";
/* 1637 */       case "turkish": return "tr";
/*      */ 
/*      */ 
/*      */       
/*      */       case "chinese":
/*      */       case "chinese (simplified)":
/*      */       case "chinese (traditional)":
/* 1644 */         return "zh-CN";
/* 1645 */       case "japanese": return "ja";
/* 1646 */       case "korean": return "ko";
/* 1647 */       case "arabic": return "ar";
/* 1648 */       case "hindi": return "hi";
/* 1649 */       case "vietnamese": return "vi";
/* 1650 */       case "indonesian": return "id";
/*      */       case "auto": case "":
/* 1652 */         return "auto"; }
/*      */     
/* 1654 */     return (f.length() <= 3) ? f : f.substring(0, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String readAll(InputStream in) throws Exception {
/* 1660 */     StringBuilder sb = new StringBuilder();
/* 1661 */     BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)); 
/* 1662 */     try { char[] buf = new char[2048];
/*      */       
/* 1664 */       int total = 0; int n;
/* 1665 */       while ((n = r.read(buf)) != -1) {
/* 1666 */         sb.append(buf, 0, n);
/* 1667 */         total += n;
/*      */ 
/*      */ 
/*      */         
/* 1671 */         if (total > 262144) {
/*      */           break;
/*      */         }
/*      */       } 
/* 1675 */       r.close(); } catch (Throwable throwable) { try { r.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 1676 */      return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isMostlySymbolic(String text) {
/* 1692 */     if (text == null || text.isEmpty()) return true; 
/* 1693 */     int letterWeight = 0;
/* 1694 */     for (int i = 0; i < text.length(); ) {
/* 1695 */       int cp = text.codePointAt(i);
/* 1696 */       if (Character.isLetter(cp)) {
/* 1697 */         letterWeight += isCjkLike(cp) ? 2 : 1;
/* 1698 */         if (letterWeight >= 2) {
/* 1699 */           return false;
/*      */         }
/*      */       } 
/* 1702 */       i += Character.charCount(cp);
/*      */     } 
/* 1704 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int translationWeight(String s) {
/* 1728 */     int total = 0;
/* 1729 */     for (int i = 0; i < s.length(); ) {
/* 1730 */       int cp = s.codePointAt(i);
/* 1731 */       total += isCjkLike(cp) ? 2 : 1;
/* 1732 */       i += Character.charCount(cp);
/*      */     } 
/* 1734 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isCjkLike(int cp) {
/* 1751 */     Character.UnicodeBlock b = Character.UnicodeBlock.of(cp);
/* 1752 */     return (b == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || b == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || b == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || b == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || b == Character.UnicodeBlock.HIRAGANA || b == Character.UnicodeBlock.KATAKANA || b == Character.UnicodeBlock.HANGUL_SYLLABLES);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Translator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */