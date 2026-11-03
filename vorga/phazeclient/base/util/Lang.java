/*      */ package vorga.phazeclient.base.util;
/*      */ 
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Lang
/*      */ {
/*      */   public static final String EN = "English";
/*      */   public static final String RU = "Русский";
/*   30 */   private static volatile String active = "English";
/*      */   
/*      */   public static void setActive(String locale) {
/*   33 */     if ("Русский".equals(locale)) {
/*   34 */       active = "Русский";
/*      */     } else {
/*   36 */       active = "English";
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String getActive() {
/*   41 */     return active;
/*      */   }
/*      */   
/*      */   public static String t(String key) {
/*   45 */     Map<String, String> table = "Русский".equals(active) ? RU_TABLE : null;
/*   46 */     if (table != null) {
/*   47 */       String v = table.get(key);
/*   48 */       if (v != null) return v;
/*      */     
/*      */     } 
/*      */ 
/*      */     
/*   53 */     String fallback = EN_TABLE.get(key);
/*   54 */     return (fallback != null) ? fallback : key;
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
/*      */   public static String translate(String english) {
/*   68 */     if (english == null || english.isEmpty()) return english; 
/*   69 */     if (!"Русский".equals(active)) return english; 
/*   70 */     String v = SETTING_TRANSLATIONS.get(english);
/*   71 */     return (v != null) ? v : english;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   private static final Map<String, String> EN_TABLE = new HashMap<>();
/*   79 */   private static final Map<String, String> RU_TABLE = new HashMap<>();
/*      */ 
/*      */   
/*      */   static {
/*   83 */     en("modal.share.title", "Create Key");
/*   84 */     en("modal.share.subtitle.prefix", "Config");
/*   85 */     en("modal.share.placeholder", "Number of uses");
/*   86 */     en("modal.share.primary", "Create");
/*   87 */     en("modal.cloud.title", "Cloud Configs");
/*   88 */     en("modal.cloud.subtitle", "Share a config or load one by code");
/*      */ 
/*      */     
/*   91 */     en("modal.rename.title", "Rename Config");
/*   92 */     en("modal.rename.placeholder", "New name");
/*   93 */     en("modal.rename.primary", "Save");
/*      */ 
/*      */     
/*   96 */     en("modal.import.title", "Load Config from Key");
/*   97 */     en("modal.import.subtitle", "Enter the code from your friend");
/*   98 */     en("modal.import.placeholder", "nomad-9wxf-49k7");
/*   99 */     en("modal.import.primary", "Load");
/*      */ 
/*      */     
/*  102 */     en("button.cancel", "Cancel");
/*  103 */     en("status.loading", "Loading...");
/*  104 */     en("status.server_unreachable", "Server unreachable");
/*  105 */     en("status.error_prefix", "Error");
/*  106 */     en("status.copy_failed", "Failed to pack config");
/*  107 */     en("status.copied_prefix", "Code");
/*  108 */     en("status.copied_suffix", "copied");
/*  109 */     en("status.cloud_disabled_short", "Unavailable");
/*  110 */     en("status.cloud_disabled_detail", "Cloud sharing is turned off in this build.");
/*  111 */     en("status.cloud_quota_exceeded", "Limit reached: %d configs per player");
/*  112 */     en("status.cloud_rate_limited", "Too many requests, try again in a minute");
/*  113 */     en("status.cloud_network_error", "No connection to the server");
/*  114 */     en("status.cloud_server_error", "Server error (%d)");
/*  115 */     en("status.cloud_bad_payload", "Config could not be packed");
/*  116 */     en("status.cloud_bad_response", "Unexpected server response");
/*  117 */     en("status.cloud_bad_code", "Invalid code format");
/*  118 */     en("status.cloud_not_found", "Code not found");
/*  119 */     en("status.cloud_exhausted", "This code has run out of uses");
/*  120 */     en("status.kicked_by_admin", "You were kicked by admin");
/*  121 */     en("slider.snapped_to_default", "snapped to default");
/*  122 */     en("status.import_failed", "Import failed");
/*  123 */     en("status.imported_prefix", "Imported:");
/*  124 */     en("status.key_not_found", "Key not found");
/*  125 */     en("status.key_exhausted", "Key exhausted");
/*  126 */     en("status.empty_response", "Empty server response");
/*  127 */     en("status.enter_code", "Enter code");
/*  128 */     en("status.invalid_code_format", "Invalid code format");
/*  129 */     en("status.config_name_missing", "Config name not set");
/*  130 */     en("status.enter_new_name", "Enter new name");
/*  131 */     en("status.name_taken", "Name already in use");
/*  132 */     en("status.rename_failed", "Rename failed");
/*  133 */     en("status.rename_error", "Rename error");
/*      */ 
/*      */     
/*  136 */     en("category.all", "ALL");
/*  137 */     en("category.hud", "HUD");
/*  138 */     en("category.utilities", "UTILITIES");
/*  139 */     en("category.other", "OTHER");
/*  140 */     en("category.search", "SEARCH");
/*      */ 
/*      */     
/*  143 */     en("kebab.share", "Share");
/*  144 */     en("kebab.rename", "Rename");
/*  145 */     en("kebab.delete", "Delete");
/*  146 */     en("trap_timer.preview_item", "trapka");
/*  147 */     en("trap_timer.prefix", "Time until ");
/*  148 */     en("trap_timer.middle", " ends ");
/*  149 */     en("trap_timer.suffix", " seconds");
/*      */ 
/*      */     
/*  152 */     en("sidebar.new_config", "NEW CONFIG");
/*  153 */     en("sidebar.edit_hud", "EDIT HUD LAYOUT");
/*      */ 
/*      */     
/*  156 */     en("cosmetics.title", "COSMETICS");
/*  157 */     en("cosmetics.subtitle", "Phaze cosmetic collection");
/*  158 */     en("cosmetics.category.all", "ALL");
/*  159 */     en("cosmetics.category.wings", "WINGS");
/*  160 */     en("cosmetics.category.capes", "CAPES");
/*  161 */     en("cosmetics.category.hats", "HATS");
/*  162 */     en("cosmetics.category.pets", "PETS");
/*  163 */     en("cosmetics.wheel", "WHEEL");
/*  164 */     en("cosmetics.equipped", "EQUIPPED");
/*  165 */     en("cosmetics.locked", "LOCKED");
/*  166 */     en("cosmetics.missing_suffix", "missing file");
/*  167 */     en("cosmetics.preview", "PREVIEW");
/*  168 */     en("cosmetics.rotate", "ROTATING");
/*  169 */     en("cosmetics.drag_to_rotate", "DRAG TO ROTATE");
/*  170 */     en("cosmetics.hide_elytra", "HIDE ELYTRA");
/*  171 */     en("cosmetics.wing_motion", "WING MOTION");
/*  172 */     en("cosmetics.pet_follow", "PET FOLLOW");
/*  173 */     en("cosmetics.equip", "EQUIP");
/*  174 */     en("cosmetics.remove", "REMOVE");
/*  175 */     en("cosmetics.enter_world", "ENTER A WORLD TO PREVIEW");
/*  176 */     en("cosmetics.model_missing", "MODEL FILE NOT FOUND");
/*      */ 
/*      */     
/*  179 */     ru("modal.cloud.title", "Облачные конфиги");
/*  180 */     ru("modal.cloud.subtitle", "Поделись конфигом или загрузи по коду");
/*  181 */     ru("modal.share.title", "Создание ключа");
/*  182 */     ru("modal.share.subtitle.prefix", "Конфиг");
/*  183 */     ru("modal.share.placeholder", "Количество использований");
/*  184 */     ru("modal.share.primary", "Создать");
/*      */     
/*  186 */     ru("modal.rename.title", "Переименование конфига");
/*  187 */     ru("modal.rename.placeholder", "Новое имя");
/*  188 */     ru("modal.rename.primary", "Сохранить");
/*      */     
/*  190 */     ru("modal.import.title", "Загрузить конфиг из ключа");
/*  191 */     ru("modal.import.subtitle", "Введите код, полученный от друга");
/*  192 */     ru("modal.import.placeholder", "nomad-9wxf-49k7");
/*  193 */     ru("modal.import.primary", "Загрузить");
/*      */     
/*  195 */     ru("button.cancel", "Отмена");
/*  196 */     ru("status.loading", "Загрузка...");
/*  197 */     ru("status.server_unreachable", "Сервер недоступен");
/*  198 */     ru("status.error_prefix", "Ошибка");
/*  199 */     ru("status.copy_failed", "Не удалось упаковать конфиг");
/*  200 */     ru("status.copied_prefix", "Код");
/*  201 */     ru("status.copied_suffix", "скопирован");
/*  202 */     ru("status.cloud_disabled_short", "Недоступно");
/*  203 */     ru("status.cloud_disabled_detail", "Облачные конфиги отключены в этой сборке.");
/*  204 */     ru("status.cloud_quota_exceeded", "Достигнут лимит: %d конфигов на игрока");
/*  205 */     ru("status.cloud_rate_limited", "Слишком много запросов, попробуй через минуту");
/*  206 */     ru("status.cloud_network_error", "Нет связи с сервером");
/*  207 */     ru("status.cloud_server_error", "Ошибка сервера (%d)");
/*  208 */     ru("status.cloud_bad_payload", "Не удалось упаковать конфиг");
/*  209 */     ru("status.cloud_bad_response", "Неожиданный ответ сервера");
/*  210 */     ru("status.cloud_bad_code", "Неверный формат кода");
/*  211 */     ru("status.cloud_not_found", "Код не найден");
/*  212 */     ru("status.cloud_exhausted", "У этого кода закончились использования");
/*  213 */     ru("status.kicked_by_admin", "Вы были кикнуты админом");
/*  214 */     ru("slider.snapped_to_default", "залипло на дефолте");
/*  215 */     ru("status.import_failed", "Не удалось импортировать");
/*  216 */     ru("status.imported_prefix", "Импортирован:");
/*  217 */     ru("status.key_not_found", "Ключ не найден");
/*  218 */     ru("status.key_exhausted", "Ключ исчерпан");
/*  219 */     ru("status.empty_response", "Пустой ответ сервера");
/*  220 */     ru("status.enter_code", "Введите код");
/*  221 */     ru("status.invalid_code_format", "Неверный формат кода");
/*  222 */     ru("status.config_name_missing", "Имя конфига не задано");
/*  223 */     ru("status.enter_new_name", "Введите новое имя");
/*  224 */     ru("status.name_taken", "Имя уже занято");
/*  225 */     ru("status.rename_failed", "Не удалось переименовать");
/*  226 */     ru("status.rename_error", "Ошибка переименования");
/*      */     
/*  228 */     ru("category.all", "ВСЕ");
/*  229 */     ru("category.hud", "HUD");
/*  230 */     ru("category.utilities", "УТИЛИТЫ");
/*  231 */     ru("category.other", "ПРОЧЕЕ");
/*  232 */     ru("category.search", "ПОИСК");
/*      */     
/*  234 */     ru("kebab.share", "Поделиться");
/*  235 */     ru("kebab.rename", "Переименовать");
/*  236 */     ru("kebab.delete", "Удалить");
/*  237 */     ru("trap_timer.preview_item", "трапки");
/*  238 */     ru("trap_timer.prefix", "Время до окончания ");
/*  239 */     ru("trap_timer.middle", " ");
/*  240 */     ru("trap_timer.suffix", " секунд");
/*      */     
/*  242 */     ru("sidebar.new_config", "НОВЫЙ КОНФИГ");
/*  243 */     ru("sidebar.edit_hud", "РЕДАКТОР HUD");
/*      */     
/*  245 */     ru("cosmetics.title", "КОСМЕТИКА");
/*  246 */     ru("cosmetics.subtitle", "Коллекция косметики Phaze");
/*  247 */     ru("cosmetics.category.all", "ВСЁ");
/*  248 */     ru("cosmetics.category.wings", "КРЫЛЬЯ");
/*  249 */     ru("cosmetics.category.capes", "ПЛАЩИ");
/*  250 */     ru("cosmetics.category.hats", "ГОЛОВА");
/*  251 */     ru("cosmetics.category.pets", "ПИТОМЦЫ");
/*  252 */     ru("cosmetics.wheel", "КОЛЕСО");
/*  253 */     ru("cosmetics.equipped", "НАДЕТО");
/*  254 */     ru("cosmetics.locked", "ЗАКРЫТО");
/*  255 */     ru("cosmetics.missing_suffix", "нет файла");
/*  256 */     ru("cosmetics.preview", "ПРЕДПРОСМОТР");
/*  257 */     ru("cosmetics.rotate", "ПОВОРОТ");
/*  258 */     ru("cosmetics.drag_to_rotate", "ТЯНИТЕ ДЛЯ ПОВОРОТА");
/*  259 */     ru("cosmetics.hide_elytra", "СКРЫВАТЬ ЭЛИТРЫ");
/*  260 */     ru("cosmetics.wing_motion", "ДВИЖЕНИЕ КРЫЛЬЕВ");
/*  261 */     ru("cosmetics.pet_follow", "ПЛАВНОСТЬ ПИТОМЦА");
/*  262 */     ru("cosmetics.equip", "НАДЕТЬ");
/*  263 */     ru("cosmetics.remove", "СНЯТЬ");
/*  264 */     ru("cosmetics.enter_world", "ВОЙДИТЕ В МИР ДЛЯ ПРОСМОТРА");
/*  265 */     ru("cosmetics.model_missing", "ФАЙЛ МОДЕЛИ НЕ НАЙДЕН");
/*      */   }
/*      */   
/*  268 */   private static final Map<String, String> SETTING_TRANSLATIONS = new HashMap<>();
/*      */   static {
/*  270 */     SETTING_TRANSLATIONS.put("24 Hour Format", "24-часовой формат");
/*  271 */     SETTING_TRANSLATIONS.put("Abilities", "Способности");
/*  272 */     SETTING_TRANSLATIONS.put("Adds a stats panel to the death screen with killer / weapon / session counters", "Добавляет панель статистики на экран смерти: убийца, оружие, счётчик сессии");
/*  273 */     SETTING_TRANSLATIONS.put("Adds smooth UI animations: chat message fade-in, tab list slide-in/out", "Плавные анимации UI: затухание сообщений чата, выезд таб-листа");
/*  274 */     SETTING_TRANSLATIONS.put("Adds tier-coloured ping, version, and raw MOTD to the vanilla multiplayer server list", "Добавляет цветной пинг, версию и сырой MOTD в ванильный список серверов");
/*  275 */     SETTING_TRANSLATIONS.put("Advanced", "Расширенное");
/*  276 */     SETTING_TRANSLATIONS.put("Affect Clouds", "Влиять на облака");
/*  277 */     SETTING_TRANSLATIONS.put("All", "Все");
/*  278 */     SETTING_TRANSLATIONS.put("Always", "Всегда");
/*  279 */     SETTING_TRANSLATIONS.put("Always Show", "Всегда показывать");
/*  280 */     SETTING_TRANSLATIONS.put("Animals", "Животные");
/*  281 */     SETTING_TRANSLATIONS.put("Animates loaded chunks sliding smoothly into place from below or from a chosen direction", "Плавно выдвигает прогруженные чанки снизу или с выбранной стороны");
/*  282 */     SETTING_TRANSLATIONS.put("Animation", "Анимация");
/*  283 */     SETTING_TRANSLATIONS.put("Animation Speed", "Скорость анимации");
/*  284 */     SETTING_TRANSLATIONS.put("Animation Type", "Тип анимации");
/*  285 */     SETTING_TRANSLATIONS.put("Animations", "Анимации");
/*  286 */     SETTING_TRANSLATIONS.put("Anti AFK", "Анти-AFK");
/*  287 */     SETTING_TRANSLATIONS.put("Anti Caps", "Анти-капс");
/*  288 */     SETTING_TRANSLATIONS.put("Add", "Добавить");
/*  289 */     SETTING_TRANSLATIONS.put("Add Item", "Добавить предмет");
/*  290 */     SETTING_TRANSLATIONS.put("API Key", "Ключ API");
/*  291 */     SETTING_TRANSLATIONS.put("Apify Actor", "Apify Actor");
/*  292 */     SETTING_TRANSLATIONS.put("Apify Token", "Apify токен");
/*  293 */     SETTING_TRANSLATIONS.put("Appearance", "Внешний вид");
/*  294 */     SETTING_TRANSLATIONS.put("Apply", "Применить");
/*  295 */     SETTING_TRANSLATIONS.put("Armor", "Броня");
/*  296 */     SETTING_TRANSLATIONS.put("Aspect Factor", "Множитель пропорций");
/*  297 */     SETTING_TRANSLATIONS.put("Aspect Preset", "Пресет пропорций");
/*  298 */     SETTING_TRANSLATIONS.put("Aspect Ratio", "Соотношение сторон");
/*  299 */     SETTING_TRANSLATIONS.put("Audio", "Звук");
/*  300 */     SETTING_TRANSLATIONS.put("Auto", "Авто");
/*  301 */     SETTING_TRANSLATIONS.put("Auto Eat", "Авто-еда");
/*  302 */     SETTING_TRANSLATIONS.put("Auto Respawn", "Авто-возрождение");
/*  303 */     SETTING_TRANSLATIONS.put("Auto Sprint", "Авто-спринт");
/*  304 */     SETTING_TRANSLATIONS.put("Auto Swap", "Авто-смена");
/*  305 */     SETTING_TRANSLATIONS.put("Auto Switch", "Авто-смена");
/*  306 */     SETTING_TRANSLATIONS.put("Auto Walk", "Авто-ходьба");
/*  307 */     SETTING_TRANSLATIONS.put("Auto-Detect Source", "Авто-определение источника");
/*  308 */     SETTING_TRANSLATIONS.put("Auto-translate incoming chat messages and post the translation as a new row below the original", "Авто-перевод входящих сообщений: перевод появляется отдельной строкой под оригиналом");
/*  309 */     SETTING_TRANSLATIONS.put("Automatically drinks potions when effects expire", "Автоматически пьёт зелья при истечении эффектов");
/*  310 */     SETTING_TRANSLATIONS.put("Automatically eats food (or runs /feed) when hunger drops below the threshold", "Автоматически ест еду (или /feed) при падении голода ниже порога");
/*  311 */     SETTING_TRANSLATIONS.put("Automatically executes /near max command", "Автоматически выполняет команду /near max");
/*  312 */     SETTING_TRANSLATIONS.put("Automatically reissues your auction items on FunTime", "Автоматически перевыставляет ваши лоты на FunTime");
/*  313 */     SETTING_TRANSLATIONS.put("Basic", "Основные");
/*  314 */     SETTING_TRANSLATIONS.put("Delay before the first /ah click and between reissue cycles in seconds", "Задержка до первого клика в /ah и между циклами перевыставления в секундах");
/*  315 */     SETTING_TRANSLATIONS.put("Automatically respawns and optionally runs a command after death", "Автовозрождение и опциональная команда после смерти");
/*  316 */     SETTING_TRANSLATIONS.put("Automatically sprints while holding forward", "Автоматический спринт при движении вперёд");
/*  317 */     SETTING_TRANSLATIONS.put("Automatically taps shift for short duration", "Автоматически нажимает Shift на короткое время");
/*  318 */     SETTING_TRANSLATIONS.put("Background", "Фон");
/*  319 */     SETTING_TRANSLATIONS.put("Background Blur Radius", "Радиус размытия фона");
/*  320 */     SETTING_TRANSLATIONS.put("Background Opacity", "Прозрачность фона");
/*  321 */     SETTING_TRANSLATIONS.put("Background Preset", "Стиль фона");
/*  322 */     SETTING_TRANSLATIONS.put("Custom Background Color", "Свой цвет");
/*  323 */     SETTING_TRANSLATIONS.put("Gradient Start Color", "Первый цвет градиента");
/*  324 */     SETTING_TRANSLATIONS.put("Gradient End Color", "Второй цвет градиента");
/*  325 */     SETTING_TRANSLATIONS.put("Gradient Speed", "Скорость градиента");
/*  326 */     SETTING_TRANSLATIONS.put("Gradient Direction", "Направление градиента");
/*  327 */     SETTING_TRANSLATIONS.put("Base Color", "Базовый цвет");
/*  328 */     SETTING_TRANSLATIONS.put("Batches all 2D HUD elements into a single FBO and refreshes at a throttled rate", "Объединяет 2D-элементы HUD в один FBO и ограничивает частоту обновления");
/*  329 */     SETTING_TRANSLATIONS.put("Better Death", "Улучшенная смерть");
/*  330 */     SETTING_TRANSLATIONS.put("Better F3", "Улучшенный F3");
/*  331 */     SETTING_TRANSLATIONS.put("Bind", "Бинд");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  338 */     for (int i = 1; i <= 8; i++) {
/*  339 */       SETTING_TRANSLATIONS.put("Bind " + i, "Бинд " + i);
/*  340 */       SETTING_TRANSLATIONS.put("Bind " + i + " Key", "Бинд " + i + " - клавиша");
/*  341 */       SETTING_TRANSLATIONS.put("Bind " + i + " Message", "Бинд " + i + " - сообщение");
/*      */     } 
/*  343 */     SETTING_TRANSLATIONS.put("Binds", "Привязки");
/*  344 */     SETTING_TRANSLATIONS.put("Blacklist", "Чёрный список");
/*  345 */     SETTING_TRANSLATIONS.put("Block Reach", "Дальность блока");
/*  346 */     SETTING_TRANSLATIONS.put("Blur Radius", "Радиус размытия");
/*  347 */     SETTING_TRANSLATIONS.put("Bold", "Жирный");
/*  348 */     SETTING_TRANSLATIONS.put("Border", "Граница");
/*  349 */     SETTING_TRANSLATIONS.put("Border Color", "Цвет границы");
/*  350 */     SETTING_TRANSLATIONS.put("Box", "Куб");
/*  351 */     SETTING_TRANSLATIONS.put("Box Color", "Цвет рамки");
/*  352 */     SETTING_TRANSLATIONS.put("Bozhestvennaya Aura Name", "Имя «Божья аура»");
/*  353 */     SETTING_TRANSLATIONS.put("Break Block Particles", "Частицы ломки блоков");
/*  354 */     SETTING_TRANSLATIONS.put("Brightness", "Яркость");
/*  355 */     SETTING_TRANSLATIONS.put("Camera", "Камера");
/*  356 */     SETTING_TRANSLATIONS.put("Cancel", "Отмена");
/*  357 */     SETTING_TRANSLATIONS.put("Change Color When Looking on Entity", "Менять цвет при наведении на сущность");
/*  358 */     SETTING_TRANSLATIONS.put("Change the time of day in the world (client-side, works on any server)", "Меняет время суток (клиентски, работает на любом сервере)");
/*  359 */     SETTING_TRANSLATIONS.put("Change the weather in the world (client-side, works on any server)", "Меняет погоду в мире (клиентски, работает на любом сервере)");
/*  360 */     SETTING_TRANSLATIONS.put("Changes the visual properties of your hands - position, scale, and active side", "Меняет вид рук: позицию, масштаб и активную сторону");
/*  361 */     SETTING_TRANSLATIONS.put("Chat", "Чат");
/*  362 */     SETTING_TRANSLATIONS.put("Chat Fade", "Затухание чата");
/*  363 */     SETTING_TRANSLATIONS.put("Chat Height", "Высота чата");
/*  364 */     SETTING_TRANSLATIONS.put("Chat History", "История чата");
/*  365 */     SETTING_TRANSLATIONS.put("Chat Notify", "Уведомление в чат");
/*  366 */     SETTING_TRANSLATIONS.put("Chat Opacity", "Прозрачность чата");
/*  367 */     SETTING_TRANSLATIONS.put("Chat Width", "Ширина чата");
/*  368 */     SETTING_TRANSLATIONS.put("Cinematic Camera", "Кинематик-камера");
/*  369 */     SETTING_TRANSLATIONS.put("Cinematic-style camera smoothing for mouse movement", "Кинематографическое сглаживание камеры мыши");
/*  370 */     SETTING_TRANSLATIONS.put("Circle", "Круг");
/*  371 */     SETTING_TRANSLATIONS.put("Circle Color", "Цвет круга");
/*  372 */     SETTING_TRANSLATIONS.put("Circle Glow", "Свечение круга");
/*  373 */     SETTING_TRANSLATIONS.put("Circle Segments", "Сегменты круга");
/*  374 */     SETTING_TRANSLATIONS.put("Circle Thickness", "Толщина круга");
/*  375 */     SETTING_TRANSLATIONS.put("Classic", "Классический");
/*  376 */     SETTING_TRANSLATIONS.put("Click", "Клик");
/*  377 */     SETTING_TRANSLATIONS.put("Click Delay", "Задержка клика");
/*  378 */     SETTING_TRANSLATIONS.put("Click Type", "Тип клика");
/*  379 */     SETTING_TRANSLATIONS.put("Close", "Закрыть");
/*  380 */     SETTING_TRANSLATIONS.put("Cloud Brightness", "Яркость облаков");
/*  381 */     SETTING_TRANSLATIONS.put("Cloud Height", "Высота облаков");
/*  382 */     SETTING_TRANSLATIONS.put("Clouds", "Облака");
/*  383 */     SETTING_TRANSLATIONS.put("Collapse Repeats", "Сворачивать повторы");
/*  384 */     SETTING_TRANSLATIONS.put("Comfortable Text Selection", "Комфортное выделение текста");
/*  385 */     SETTING_TRANSLATIONS.put("Drag-select text in any input field with the mouse - chat, search bars, Sodium options, etc.", "Выделение текста мышью в любых полях ввода: чат, поиск, настройки Sodium и т.д.");
/*  386 */     SETTING_TRANSLATIONS.put("Color", "Цвет");
/*  387 */     SETTING_TRANSLATIONS.put("Color By Shulker", "Цвет по шалкеру");
/*  388 */     SETTING_TRANSLATIONS.put("Color Based On Usage", "Цвет от использований");
/*  389 */     SETTING_TRANSLATIONS.put("Color Brightness", "Яркость цвета");
/*  390 */     SETTING_TRANSLATIONS.put("Color By Cooldown", "Цвет по перезарядке");
/*  391 */     SETTING_TRANSLATIONS.put("Color By Durability", "Цвет по прочности");
/*  392 */     SETTING_TRANSLATIONS.put("Color By HP", "Цвет по HP");
/*  393 */     SETTING_TRANSLATIONS.put("Color By TPS", "Цвет по TPS");
/*  394 */     SETTING_TRANSLATIONS.put("Color By Type", "Цвет по типу");
/*  395 */     SETTING_TRANSLATIONS.put("Color Coding", "Цветовая разметка");
/*  396 */     SETTING_TRANSLATIONS.put("Color Correction", "Цветокоррекция");
/*  397 */     SETTING_TRANSLATIONS.put("Color correction post-process effect", "Пост-эффект цветокоррекции");
/*  398 */     SETTING_TRANSLATIONS.put("Color Message", "Цветное сообщение");
/*  399 */     SETTING_TRANSLATIONS.put("Color Phase", "Фаза цвета");
/*  400 */     SETTING_TRANSLATIONS.put("Color Ping", "Цветной пинг");
/*  401 */     SETTING_TRANSLATIONS.put("Color Preset", "Пресет цветов");
/*  402 */     SETTING_TRANSLATIONS.put("Color Settings", "Настройки цвета");
/*  403 */     SETTING_TRANSLATIONS.put("Color When In Range", "Цвет в радиусе");
/*  404 */     SETTING_TRANSLATIONS.put("Controls outline opacity in Filled mode without affecting fill opacity.", "Управляет прозрачностью обводки в режиме Filled, не затрагивая прозрачность заливки.");
/*  405 */     SETTING_TRANSLATIONS.put("Colors", "Цвета");
/*  406 */     SETTING_TRANSLATIONS.put("Command", "Команда");
/*  407 */     SETTING_TRANSLATIONS.put("Compact", "Компактный");
/*  408 */     SETTING_TRANSLATIONS.put("Compact custom F3 debug overlay with color-coded values and FPS bar", "Компактный F3-оверлей с цветными значениями и графиком FPS");
/*  409 */     SETTING_TRANSLATIONS.put("Compact Labels", "Компактные подписи");
/*  410 */     SETTING_TRANSLATIONS.put("Compact Mode", "Компактный режим");
/*  411 */     SETTING_TRANSLATIONS.put("Compact Rows", "Компактные строки");
/*  412 */     SETTING_TRANSLATIONS.put("Configuration", "Конфигурация");
/*  413 */     SETTING_TRANSLATIONS.put("Contrast", "Контраст");
/*  414 */     SETTING_TRANSLATIONS.put("Cooldown", "Перезарядка");
/*  415 */     SETTING_TRANSLATIONS.put("Cooldown HUD", "HUD перезарядки");
/*  416 */     SETTING_TRANSLATIONS.put("Coordinates HUD", "HUD координат");
/*  417 */     SETTING_TRANSLATIONS.put("Counts combo hits until enemy hits back", "Считает комбо-удары до удара противника");
/*  418 */     SETTING_TRANSLATIONS.put("Counts consumable items in your inventory and shows them as a draggable icon grid", "Считает расходники в инвентаре и показывает их сеткой иконок");
/*  419 */     SETTING_TRANSLATIONS.put("CPS HUD", "CPS HUD");
/*  420 */     SETTING_TRANSLATIONS.put("Crosshair", "Прицел");
/*  421 */     SETTING_TRANSLATIONS.put("Crosshair Color", "Цвет прицела");
/*  422 */     SETTING_TRANSLATIONS.put("Crosshair Size", "Размер прицела");
/*  423 */     SETTING_TRANSLATIONS.put("Crosshair Style", "Стиль прицела");
/*  424 */     SETTING_TRANSLATIONS.put("Crosshair Type", "Тип прицела");
/*  425 */     SETTING_TRANSLATIONS.put("Cursor", "Курсор");
/*  426 */     SETTING_TRANSLATIONS.put("Custom", "Кастомный");
/*  427 */     SETTING_TRANSLATIONS.put("Custom Hit Color", "Свой цвет удара");
/*  428 */     SETTING_TRANSLATIONS.put("Custom hit color for entities", "Свой цвет вспышки удара по сущностям");
/*  429 */     SETTING_TRANSLATIONS.put("Custom fog with adjustable distance, density and color", "Свой туман с настраиваемой дальностью, плотностью и цветом");
/*  430 */     SETTING_TRANSLATIONS.put("Density", "Плотность");
/*  431 */     SETTING_TRANSLATIONS.put("Color Mode", "Режим цвета");
/*  432 */     SETTING_TRANSLATIONS.put("Fog Color", "Цвет тумана");
/*  433 */     SETTING_TRANSLATIONS.put("Affect Sky Fog", "Влиять на туман неба");
/*  434 */     SETTING_TRANSLATIONS.put("Theme", "Тема");
/*  435 */     SETTING_TRANSLATIONS.put("Scope", "Область");
/*  436 */     SETTING_TRANSLATIONS.put("Distance in blocks at which the fog reaches full opacity", "Дальность в блоках, при которой туман достигает полной непрозрачности");
/*  437 */     SETTING_TRANSLATIONS.put("How much of the distance range is fogged. 0 = thin band at the far plane, 1 = solid wall starting at the camera", "Какая часть диапазона дальности туманит. 0 = тонкая полоса на дальнем плане, 1 = сплошная стена от камеры");
/*  438 */     SETTING_TRANSLATIONS.put("Custom uses the picker below, Theme follows the active GUI palette", "Custom использует пикер ниже, Theme следует активной палитре интерфейса");
/*  439 */     SETTING_TRANSLATIONS.put("Custom fog colour. Ignored when Color Mode is set to Theme", "Свой цвет тумана. Игнорируется, если Color Mode = Theme");
/*  440 */     SETTING_TRANSLATIONS.put("Final fog alpha multiplier. 1 = fully opaque, 0 = invisible", "Множитель альфы тумана. 1 = непрозрачный, 0 = невидимый");
/*  441 */     SETTING_TRANSLATIONS.put("Apply the override to the sky-dome fog pass too. Disable to keep the horizon clear and only fog terrain.", "Также применять к туману неба. Выключи, чтобы горизонт оставался чистым, а туман был только на ландшафте.");
/*  442 */     SETTING_TRANSLATIONS.put("Smooth Skies", "Гладкое небо");
/*  443 */     SETTING_TRANSLATIONS.put("Smooths the transition between fog and sky so lower fog opacity doesn't produce a hard horizon line. Active only when Custom Fog is on.", "Сглаживает переход между туманом и небом, чтобы при низкой прозрачности тумана не было резкой линии на горизонте. Работает только при включённом Custom Fog.");
/*  444 */     SETTING_TRANSLATIONS.put("Fix Skybox Clipping", "Исправить клиппинг скайбокса");
/*  445 */     SETTING_TRANSLATIONS.put("Pushes the far plane out so a low render-distance skybox doesn't clip into the fog band. Inspired by SmoothSkies (MIT).", "Отодвигает дальнюю плоскость, чтобы скайбокс на низкой дальности прорисовки не клиппился в туман. Вдохновлено SmoothSkies (MIT).");
/*  446 */     SETTING_TRANSLATIONS.put("Lower Void Darkness", "Снизить темноту бездны");
/*  447 */     SETTING_TRANSLATIONS.put("Stops the fog from going pitch-black when the camera is below world height. Inspired by SmoothSkies (MIT).", "Не даёт туману уходить в кромешную темноту, когда камера ниже мира. Вдохновлено SmoothSkies (MIT).");
/*  448 */     SETTING_TRANSLATIONS.put("Custom Mentions", "Кастомные упоминания");
/*  449 */     SETTING_TRANSLATIONS.put("Custom sky and cloud tint with day/night gradient and sunset boost", "Своя окраска неба и облаков с дневным/ночным градиентом и усилением заката");
/*  450 */     SETTING_TRANSLATIONS.put("Custom themes for GUI", "Свои темы для GUI");
/*  451 */     SETTING_TRANSLATIONS.put("Customize entity hitbox color", "Свой цвет хитбокса сущностей");
/*  452 */     SETTING_TRANSLATIONS.put("Damage", "Урон");
/*  453 */     SETTING_TRANSLATIONS.put("Day Phase", "Фаза дня");
/*  454 */     SETTING_TRANSLATIONS.put("Death Stats", "Статистика смертей");
/*  455 */     SETTING_TRANSLATIONS.put("Default", "По умолчанию");
/*  456 */     SETTING_TRANSLATIONS.put("Default Zoom", "Зум по умолчанию");
/*  457 */     SETTING_TRANSLATIONS.put("Delay", "Задержка");
/*  458 */     SETTING_TRANSLATIONS.put("Delay (ms)", "Задержка (мс)");
/*  459 */     SETTING_TRANSLATIONS.put("Description", "Описание");
/*  460 */     SETTING_TRANSLATIONS.put("Details", "Детали");
/*  461 */     SETTING_TRANSLATIONS.put("Dezorientation Name", "Имя «Дезориентация»");
/*  462 */     SETTING_TRANSLATIONS.put("Direction", "Направление");
/*  463 */     SETTING_TRANSLATIONS.put("Disabled", "Выключен");
/*  464 */     SETTING_TRANSLATIONS.put("Discord Rich Presence: customisable Playing-on-server card with templated lines", "Discord Rich Presence: настраиваемая карточка с шаблонными строками");
/*  465 */     SETTING_TRANSLATIONS.put("Display", "Отображение");
/*  466 */     SETTING_TRANSLATIONS.put("Display Mode", "Режим отображения");
/*  467 */     SETTING_TRANSLATIONS.put("Display Option", "Вариант отображения");
/*  468 */     SETTING_TRANSLATIONS.put("Display Ping Number", "Показывать число пинга");
/*  469 */     SETTING_TRANSLATIONS.put("Display Server Icon", "Показывать иконку сервера");
/*  470 */     SETTING_TRANSLATIONS.put("Distance", "Расстояние");
/*  471 */     SETTING_TRANSLATIONS.put("Drakon Trap Name", "Имя «Драконья трапка»");
/*  472 */     SETTING_TRANSLATIONS.put("Draws a configurable PvP-reach circle around entities (port of uku3lig/hitrange, MIT)", "Рисует настраиваемый круг pvp-реча вокруг сущностей (порт uku3lig/hitrange, MIT)");
/*  473 */     SETTING_TRANSLATIONS.put("Durability Mode", "Режим прочности");
/*  474 */     SETTING_TRANSLATIONS.put("Durability Threshold", "Порог прочности");
/*  475 */     SETTING_TRANSLATIONS.put("Duration", "Длительность");
/*  476 */     SETTING_TRANSLATIONS.put("Dynamic", "Динамический");
/*  477 */     SETTING_TRANSLATIONS.put("Dynamic Cursor", "Динамический курсор");
/*  478 */     SETTING_TRANSLATIONS.put("Dynamic Ping Color", "Динамический цвет пинга");
/*  479 */     SETTING_TRANSLATIONS.put("Easing", "Сглаживание");
/*  480 */     SETTING_TRANSLATIONS.put("Effect Duration", "Длительность эффекта");
/*  481 */     SETTING_TRANSLATIONS.put("Effects", "Эффекты");
/*  482 */     SETTING_TRANSLATIONS.put("Elapsed Time", "Прошедшее время");
/*  483 */     SETTING_TRANSLATIONS.put("Enable Limits", "Включить лимиты");
/*  484 */     SETTING_TRANSLATIONS.put("Enabled", "Включён");
/*  485 */     SETTING_TRANSLATIONS.put("Enchanted Gapple Cooldown", "Кд зачар. золотого яблока");
/*  486 */     SETTING_TRANSLATIONS.put("Estimates server TPS based on world-time advancement", "Оценивает серверный TPS по продвижению мирового времени");
/*  487 */     SETTING_TRANSLATIONS.put("Expand Slang", "Расшифровка сленга");
/*  488 */     SETTING_TRANSLATIONS.put("Extra Triggers", "Доп. триггеры");
/*  489 */     SETTING_TRANSLATIONS.put("F5 Animation Speed", "Скорость анимации F5");
/*  490 */     SETTING_TRANSLATIONS.put("F5 Interpolation", "Интерполяция F5");
/*  491 */     SETTING_TRANSLATIONS.put("Fade Distance", "Дистанция затухания");
/*  492 */     SETTING_TRANSLATIONS.put("Fade Style", "Стиль затухания");
/*  493 */     SETTING_TRANSLATIONS.put("Fake FPS", "Фейк FPS");
/*  494 */     SETTING_TRANSLATIONS.put("Fast Exp", "Быстрый опыт");
/*  495 */     SETTING_TRANSLATIONS.put("Fast Swap", "Быстрая смена");
/*  496 */     SETTING_TRANSLATIONS.put("Fill Color", "Цвет заливки");
/*  497 */     SETTING_TRANSLATIONS.put("Filled", "Заполнено");
/*  498 */     SETTING_TRANSLATIONS.put("Filter", "Фильтр");
/*  499 */     SETTING_TRANSLATIONS.put("Fire", "Огонь");
/*  500 */     SETTING_TRANSLATIONS.put("Flash On Expiry", "Мигание при истечении");
/*  501 */     SETTING_TRANSLATIONS.put("Food Particles", "Частицы еды");
/*  502 */     SETTING_TRANSLATIONS.put("Mace Particles", "Частицы булавы");
/*  503 */     SETTING_TRANSLATIONS.put("Forces maximum lightmap brightness so caves and night render at near-day levels", "Принудительно ставит макс яркость, чтобы пещеры и ночь рендерились почти как днём");
/*  504 */     SETTING_TRANSLATIONS.put("Format", "Формат");
/*  505 */     SETTING_TRANSLATIONS.put("FOV", "FOV (поле зрения)");
/*  506 */     SETTING_TRANSLATIONS.put("FPS HUD", "FPS HUD");
/*  507 */     SETTING_TRANSLATIONS.put("Free Look", "Свободный взгляд");
/*  508 */     SETTING_TRANSLATIONS.put("FunTime ability detector and visualizer toggles", "Детектор и визуализатор способностей FunTime");
/*  509 */     SETTING_TRANSLATIONS.put("Gamma", "Гамма");
/*  510 */     SETTING_TRANSLATIONS.put("Gapple Saturation", "Сытость от голд. яблока");
/*  511 */     SETTING_TRANSLATIONS.put("General", "Общее");
/*  512 */     SETTING_TRANSLATIONS.put("Glow", "Свечение");
/*  513 */     SETTING_TRANSLATIONS.put("Glow Pulsate", "Пульсация свечения");
/*  514 */     SETTING_TRANSLATIONS.put("Glow Strength", "Сила свечения");
/*  515 */     SETTING_TRANSLATIONS.put("Glowing", "Свечение");
/*  516 */     SETTING_TRANSLATIONS.put("Gradient", "Градиент");
/*  517 */     SETTING_TRANSLATIONS.put("Hand", "Рука");
/*  518 */     SETTING_TRANSLATIONS.put("Hand Depth Threshold", "Порог глубины руки");
/*  519 */     SETTING_TRANSLATIONS.put("Hand Sway", "Покачивание руки");
/*  520 */     SETTING_TRANSLATIONS.put("Hand Tweaks", "Настройки руки");
/*  521 */     SETTING_TRANSLATIONS.put("Heal", "Восстановление");
/*  522 */     SETTING_TRANSLATIONS.put("Healing Helper", "Помощник лечения");
/*  523 */     SETTING_TRANSLATIONS.put("Healing Potion HP", "HP для зелья лечения");
/*  524 */     SETTING_TRANSLATIONS.put("Health", "Здоровье");
/*  525 */     SETTING_TRANSLATIONS.put("Height", "Высота");
/*  526 */     SETTING_TRANSLATIONS.put("Held Item", "Предмет в руке");
/*  527 */     SETTING_TRANSLATIONS.put("Hidden", "Скрытый");
/*  528 */     SETTING_TRANSLATIONS.put("Hotkeys", "Хоткеи");
/*  529 */     SETTING_TRANSLATIONS.put("Hide Coordinates", "Скрыть координаты");
/*  530 */     SETTING_TRANSLATIONS.put("Hide HUD", "Скрыть HUD");
/*  531 */     SETTING_TRANSLATIONS.put("Hide in F1", "Скрывать в F1");
/*  532 */     SETTING_TRANSLATIONS.put("Hide In Menus", "Скрывать в меню");
/*  533 */     SETTING_TRANSLATIONS.put("Hide Nicknames", "Скрыть никнеймы");
/*  534 */     SETTING_TRANSLATIONS.put("Hide Passwords", "Скрыть пароли");
/*  535 */     SETTING_TRANSLATIONS.put("Hide Player Names", "Скрыть имена игроков");
/*  536 */     SETTING_TRANSLATIONS.put("Hide Tab List Names", "Скрыть имена в табе");
/*  537 */     SETTING_TRANSLATIONS.put("Hide When Looking At", "Скрывать при взгляде");
/*  538 */     SETTING_TRANSLATIONS.put("Hide While Sneaking", "Скрывать при приседании");
/*  539 */     SETTING_TRANSLATIONS.put("Hides sensitive info while streaming: coordinates and passwords", "Скрывает чувствительную инфу на стриме: координаты и пароли");
/*  540 */     SETTING_TRANSLATIONS.put("Highlight", "Подсветка");
/*  541 */     SETTING_TRANSLATIONS.put("Highlight Background", "Фон подсветки");
/*  542 */     SETTING_TRANSLATIONS.put("Highlight Color", "Цвет подсветки");
/*  543 */     SETTING_TRANSLATIONS.put("Highlight Own Name", "Подсвечивать свой ник");
/*  544 */     SETTING_TRANSLATIONS.put("Highlights mentions of your name in chat and plays a ping sound", "Подсвечивает упоминания вашего ника в чате и играет звук");
/*  545 */     SETTING_TRANSLATIONS.put("History Limit", "Лимит истории");
/*  546 */     SETTING_TRANSLATIONS.put("Hit Color", "Цвет удара");
/*  547 */     SETTING_TRANSLATIONS.put("Hit Particles", "Частицы удара");
/*  548 */     SETTING_TRANSLATIONS.put("Hit Range", "Зона удара");
/*  549 */     SETTING_TRANSLATIONS.put("Hit Sound", "Звук удара");
/*  550 */     SETTING_TRANSLATIONS.put("Hitbox Color", "Цвет хитбокса");
/*  551 */     SETTING_TRANSLATIONS.put("Fill", "Заполнение");
/*  552 */     SETTING_TRANSLATIONS.put("Fill the hitbox faces in addition to the outline", "Заполняет грани хитбокса помимо контура");
/*  553 */     SETTING_TRANSLATIONS.put("Opacity of the hitbox fill", "Прозрачность заливки хитбокса");
/*  554 */     SETTING_TRANSLATIONS.put("Hold", "Удерживать");
/*  555 */     SETTING_TRANSLATIONS.put("Hue", "Оттенок");
/*  556 */     SETTING_TRANSLATIONS.put("Hold Shift + drag the mouse over slots to shift-click them all without extra clicks", "Зажмите Shift и протащите мышь по слотам — все будут shift-кликнуты без лишних кликов");
/*  557 */     SETTING_TRANSLATIONS.put("Hotbar", "Хотбар");
/*  558 */     SETTING_TRANSLATIONS.put("Hotbar Flush", "Очистка хотбара");
/*  559 */     SETTING_TRANSLATIONS.put("Hotbar Rollover", "Перенос хотбара");
/*  560 */     SETTING_TRANSLATIONS.put("Hotbar Slide", "Анимация хотбара");
/*  561 */     SETTING_TRANSLATIONS.put("Hotbar Slide Speed", "Скорость прокрутки хотбара");
/*  562 */     SETTING_TRANSLATIONS.put("HP Prefix", "Префикс HP");
/*  563 */     SETTING_TRANSLATIONS.put("HUD Length", "Длина HUD");
/*  564 */     SETTING_TRANSLATIONS.put("Hunger", "Голод");
/*  565 */     SETTING_TRANSLATIONS.put("Hunger Threshold", "Порог голода");
/*  566 */     SETTING_TRANSLATIONS.put("Impact Marker", "Маркер попадания");
/*  567 */     SETTING_TRANSLATIONS.put("Imported from cloud", "Импортировано из облака");
/*  568 */     SETTING_TRANSLATIONS.put("In Range Color", "Цвет в радиусе");
/*  569 */     SETTING_TRANSLATIONS.put("Base color of the circle. In Filled mode, this alpha controls fill opacity.", "Базовый цвет круга. В режиме Filled его альфа управляет прозрачностью заливки.");
/*  570 */     SETTING_TRANSLATIONS.put("Info", "Инфо");
/*  571 */     SETTING_TRANSLATIONS.put("Info Display", "Информация");
/*  572 */     SETTING_TRANSLATIONS.put("Info Items", "Элементы информации");
/*  573 */     SETTING_TRANSLATIONS.put("Intensity", "Интенсивность");
/*  574 */     SETTING_TRANSLATIONS.put("Interval", "Интервал");
/*  575 */     SETTING_TRANSLATIONS.put("Inventory", "Инвентарь");
/*  576 */     SETTING_TRANSLATIONS.put("Inventory HUD", "HUD инвентаря");
/*  577 */     SETTING_TRANSLATIONS.put("Inverted", "Инвертированный");
/*  578 */     SETTING_TRANSLATIONS.put("Invisibility", "Невидимость");
/*  579 */     SETTING_TRANSLATIONS.put("Item Counts", "Счётчики предметов");
/*  580 */     SETTING_TRANSLATIONS.put("Item Highlight", "Подсветка предметов");
/*  581 */     SETTING_TRANSLATIONS.put("Item Physics", "Физика предметов");
/*  582 */     SETTING_TRANSLATIONS.put("Item Pickup", "Подбор предметов");
/*  583 */     SETTING_TRANSLATIONS.put("Item Scroller", "Прокрутка предметов");
/*  584 */     SETTING_TRANSLATIONS.put("Item Types", "Типы предметов");
/*  585 */     SETTING_TRANSLATIONS.put("Items", "Предметы");
/*  586 */     SETTING_TRANSLATIONS.put("Keep the vanilla crosshair visible in third-person (F5) view", "Оставляет ванильный прицел видимым в виде от 3-го лица (F5)");
/*  587 */     SETTING_TRANSLATIONS.put("Key", "Клавиша");
/*  588 */     SETTING_TRANSLATIONS.put("Keybind", "Бинд");
/*  589 */     SETTING_TRANSLATIONS.put("Language", "Язык");
/*  590 */     SETTING_TRANSLATIONS.put("Last modified", "Изменён");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  599 */     SETTING_TRANSLATIONS.put("Lunar Blue", "Синий Лунар");
/*  600 */     SETTING_TRANSLATIONS.put("Mocha Gold", "Мокка с золотом");
/*  601 */     SETTING_TRANSLATIONS.put("Custom Color", "Свой цвет");
/*  602 */     SETTING_TRANSLATIONS.put("Gradient", "Градиент");
/*  603 */     SETTING_TRANSLATIONS.put("Left to Right", "Слева направо");
/*  604 */     SETTING_TRANSLATIONS.put("Right to Left", "Справа налево");
/*  605 */     SETTING_TRANSLATIONS.put("Top to Bottom", "Сверху вниз");
/*  606 */     SETTING_TRANSLATIONS.put("Bottom to Top", "Снизу вверх");
/*  607 */     SETTING_TRANSLATIONS.put("Diagonal Down", "По диагонали вниз");
/*  608 */     SETTING_TRANSLATIONS.put("Diagonal Up", "По диагонали вверх");
/*  609 */     SETTING_TRANSLATIONS.put("Pulse", "Пульсация");
/*  610 */     SETTING_TRANSLATIONS.put("Rose Quartz", "Розовый кварц");
/*  611 */     SETTING_TRANSLATIONS.put("Emerald Frost", "Изумрудный иней");
/*  612 */     SETTING_TRANSLATIONS.put("Arctic Mint", "Арктическая мята");
/*  613 */     SETTING_TRANSLATIONS.put("Crimson Silk", "Алый шёлк");
/*  614 */     SETTING_TRANSLATIONS.put("Solar Ember", "Солнечный жар");
/*  615 */     SETTING_TRANSLATIONS.put("Midnight Bloom", "Полуночный цвет");
/*  616 */     SETTING_TRANSLATIONS.put("Desert Mirage", "Пустынный мираж");
/*  617 */     SETTING_TRANSLATIONS.put("Sapphire Steel", "Сапфировая сталь");
/*  618 */     SETTING_TRANSLATIONS.put("Velvet Plum", "Бархатная слива");
/*  619 */     SETTING_TRANSLATIONS.put("Frosted Peach", "Морозный персик");
/*  620 */     SETTING_TRANSLATIONS.put("Moss Smoke", "Мшистый дым");
/*  621 */     SETTING_TRANSLATIONS.put("Polar Night", "Полярная ночь");
/*  622 */     SETTING_TRANSLATIONS.put("Snow", "Снег");
/*  623 */     SETTING_TRANSLATIONS.put("Obsidian", "Обсидиан");
/*  624 */     SETTING_TRANSLATIONS.put("Nebula", "Туманность");
/*  625 */     SETTING_TRANSLATIONS.put("Coral", "Коралл");
/*  626 */     SETTING_TRANSLATIONS.put("Jade", "Нефрит");
/*  627 */     SETTING_TRANSLATIONS.put("Sunset", "Закат");
/*  628 */     SETTING_TRANSLATIONS.put("Violet", "Фиолетовый");
/*  629 */     SETTING_TRANSLATIONS.put("Ocean", "Океан");
/*  630 */     SETTING_TRANSLATIONS.put("Layout", "Расположение");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  639 */     SETTING_TRANSLATIONS.put("Linear", "Линейная");
/*  640 */     SETTING_TRANSLATIONS.put("Fast", "Быстрая");
/*  641 */     SETTING_TRANSLATIONS.put("Balanced", "Сбалансированная");
/*  642 */     SETTING_TRANSLATIONS.put("Back", "Возврат");
/*  643 */     SETTING_TRANSLATIONS.put("Overshoot", "Перелёт");
/*  644 */     SETTING_TRANSLATIONS.put("Elastic", "Упругая");
/*  645 */     SETTING_TRANSLATIONS.put("Bounce", "Отскок");
/*  646 */     SETTING_TRANSLATIONS.put("Ease Out", "Замедление");
/*  647 */     SETTING_TRANSLATIONS.put("Spring", "Пружина");
/*  648 */     SETTING_TRANSLATIONS.put("Decelerate", "Торможение");
/*  649 */     SETTING_TRANSLATIONS.put("Slide", "Сдвиг");
/*  650 */     SETTING_TRANSLATIONS.put("Slide+Scale", "Сдвиг+Масштаб");
/*  651 */     SETTING_TRANSLATIONS.put("Up", "Вверх");
/*  652 */     SETTING_TRANSLATIONS.put("Left", "Влево");
/*  653 */     SETTING_TRANSLATIONS.put("North", "Север");
/*  654 */     SETTING_TRANSLATIONS.put("South", "Юг");
/*  655 */     SETTING_TRANSLATIONS.put("East", "Восток");
/*  656 */     SETTING_TRANSLATIONS.put("West", "Запад");
/*  657 */     SETTING_TRANSLATIONS.put("Top", "Сверху");
/*  658 */     SETTING_TRANSLATIONS.put("Bottom", "Снизу");
/*  659 */     SETTING_TRANSLATIONS.put("Side", "Сторона");
/*  660 */     SETTING_TRANSLATIONS.put("Outline", "Контур");
/*  661 */     SETTING_TRANSLATIONS.put("Outline Opacity", "Прозрачность обводки");
/*  662 */     SETTING_TRANSLATIONS.put("Filled", "Заливка");
/*  663 */     SETTING_TRANSLATIONS.put("Chat", "Чат");
/*  664 */     SETTING_TRANSLATIONS.put("Clipboard", "Буфер обмена");
/*  665 */     SETTING_TRANSLATIONS.put("Both", "Оба");
/*  666 */     SETTING_TRANSLATIONS.put("Long", "Длинный");
/*  667 */     SETTING_TRANSLATIONS.put("Short", "Короткий");
/*  668 */     SETTING_TRANSLATIONS.put("Session", "Сессия");
/*  669 */     SETTING_TRANSLATIONS.put("World", "Мир");
/*  670 */     SETTING_TRANSLATIONS.put("Off", "Выкл");
/*  671 */     SETTING_TRANSLATIONS.put("Custom", "Свой");
/*  672 */     SETTING_TRANSLATIONS.put("Theme", "Тема");
/*  673 */     SETTING_TRANSLATIONS.put("Vanilla", "Ванильный");
/*  674 */     SETTING_TRANSLATIONS.put("Line", "Линия");
/*  675 */     SETTING_TRANSLATIONS.put("Thick", "Толстая");
/*  676 */     SETTING_TRANSLATIONS.put("Sphere", "Сфера");
/*  677 */     SETTING_TRANSLATIONS.put("Circle", "Круг");
/*  678 */     SETTING_TRANSLATIONS.put("Ukrainian", "Украинский");
/*  679 */     SETTING_TRANSLATIONS.put("Belarusian", "Белорусский");
/*  680 */     SETTING_TRANSLATIONS.put("Spanish", "Испанский");
/*  681 */     SETTING_TRANSLATIONS.put("French", "Французский");
/*  682 */     SETTING_TRANSLATIONS.put("German", "Немецкий");
/*  683 */     SETTING_TRANSLATIONS.put("Italian", "Итальянский");
/*  684 */     SETTING_TRANSLATIONS.put("Portuguese", "Португальский");
/*  685 */     SETTING_TRANSLATIONS.put("Polish", "Польский");
/*  686 */     SETTING_TRANSLATIONS.put("Turkish", "Турецкий");
/*  687 */     SETTING_TRANSLATIONS.put("Chinese", "Китайский");
/*  688 */     SETTING_TRANSLATIONS.put("Japanese", "Японский");
/*  689 */     SETTING_TRANSLATIONS.put("Korean", "Корейский");
/*  690 */     SETTING_TRANSLATIONS.put("Arabic", "Арабский");
/*  691 */     SETTING_TRANSLATIONS.put("Hindi", "Хинди");
/*  692 */     SETTING_TRANSLATIONS.put("Vietnamese", "Вьетнамский");
/*  693 */     SETTING_TRANSLATIONS.put("Indonesian", "Индонезийский");
/*      */     
/*  695 */     SETTING_TRANSLATIONS.put("Units", "Единицы");
/*  696 */     SETTING_TRANSLATIONS.put("Percent", "Проценты");
/*      */     
/*  698 */     SETTING_TRANSLATIONS.put("Column", "Столбец");
/*  699 */     SETTING_TRANSLATIONS.put("Table", "Таблица");
/*      */     
/*  701 */     SETTING_TRANSLATIONS.put("Main", "Основной");
/*  702 */     SETTING_TRANSLATIONS.put("Ender Chest", "Эндер-сундук");
/*      */     
/*  704 */     SETTING_TRANSLATIONS.put("Percentage", "Проценты");
/*  705 */     SETTING_TRANSLATIONS.put("Megabytes", "Мегабайты");
/*  706 */     SETTING_TRANSLATIONS.put("Gigabytes", "Гигабайты");
/*      */     
/*  708 */     SETTING_TRANSLATIONS.put("Nearest", "Ближайшее");
/*  709 */     SETTING_TRANSLATIONS.put("1 Decimal", "1 знак");
/*  710 */     SETTING_TRANSLATIONS.put("2 Decimals", "2 знака");
/*  711 */     SETTING_TRANSLATIONS.put("3 Decimals", "3 знака");
/*      */     
/*  713 */     SETTING_TRANSLATIONS.put("Follow Mouse", "Следовать за мышью");
/*  714 */     SETTING_TRANSLATIONS.put("Auto Rotate", "Авто-вращение");
/*  715 */     SETTING_TRANSLATIONS.put("Static", "Статично");
/*      */     
/*  717 */     SETTING_TRANSLATIONS.put("Yellow Bar", "Жёлтая полоса");
/*  718 */     SETTING_TRANSLATIONS.put("Second Hunger Bar", "Вторая полоса голода");
/*      */     
/*  720 */     SETTING_TRANSLATIONS.put("Normal", "Обычный");
/*  721 */     SETTING_TRANSLATIONS.put("Dragon", "Дракон");
/*      */     
/*  723 */     SETTING_TRANSLATIONS.put("Clear", "Ясно");
/*  724 */     SETTING_TRANSLATIONS.put("Rain", "Дождь");
/*  725 */     SETTING_TRANSLATIONS.put("Thunder", "Гроза");
/*      */     
/*  727 */     SETTING_TRANSLATIONS.put("Water", "Вода");
/*  728 */     SETTING_TRANSLATIONS.put("Lava", "Лава");
/*      */     
/*  730 */     SETTING_TRANSLATIONS.put("Right", "Правая");
/*      */     
/*  732 */     SETTING_TRANSLATIONS.put("Google", "Google");
/*  733 */     SETTING_TRANSLATIONS.put("Apify", "Apify");
/*      */     
/*  735 */     SETTING_TRANSLATIONS.put("Black", "Чёрный");
/*  736 */     SETTING_TRANSLATIONS.put("Quickly selects matching hotbar items on hotkey press", "Быстрый выбор предметов по нажатию клавиш");
/*  737 */     SETTING_TRANSLATIONS.put("Select the first matching item in your hotbar", "Выбирает первый подходящий предмет в хотбаре");
/*      */ 
/*      */ 
/*      */     
/*  741 */     SETTING_TRANSLATIONS.put("Snowball", "Снежок");
/*  742 */     SETTING_TRANSLATIONS.put("Egg", "Яйцо");
/*  743 */     SETTING_TRANSLATIONS.put("Wind Charge", "Заряд ветра");
/*  744 */     SETTING_TRANSLATIONS.put("Golden Apple", "Золотое яблоко");
/*  745 */     SETTING_TRANSLATIONS.put("Enchanted Golden Apple", "Зачарованное яблоко");
/*  746 */     SETTING_TRANSLATIONS.put("Arrow", "Стрела");
/*  747 */     SETTING_TRANSLATIONS.put("Spectral Arrow", "Призрачная стрела");
/*  748 */     SETTING_TRANSLATIONS.put("Tipped Arrow", "Стрела с эффектом");
/*  749 */     SETTING_TRANSLATIONS.put("Totem", "Тотем");
/*  750 */     SETTING_TRANSLATIONS.put("Chorus Fruit", "Плод хоруса");
/*  751 */     SETTING_TRANSLATIONS.put("Ender Pearl", "Жемчуг эндера");
/*  752 */     SETTING_TRANSLATIONS.put("Firework Rocket", "Фейерверк");
/*  753 */     SETTING_TRANSLATIONS.put("Experience Bottle", "Бутылочка опыта");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  758 */     SETTING_TRANSLATIONS.put("Coordinates", "Координаты");
/*  759 */     SETTING_TRANSLATIONS.put("Facing", "Направление");
/*  760 */     SETTING_TRANSLATIONS.put("Biome", "Биом");
/*  761 */     SETTING_TRANSLATIONS.put("Light", "Свет");
/*  762 */     SETTING_TRANSLATIONS.put("Dimension", "Измерение");
/*  763 */     SETTING_TRANSLATIONS.put("Server", "Сервер");
/*  764 */     SETTING_TRANSLATIONS.put("Memory", "Память");
/*  765 */     SETTING_TRANSLATIONS.put("System", "Система");
/*  766 */     SETTING_TRANSLATIONS.put("Left Slide Interpolation", "Интерполяция слайда влево");
/*  767 */     SETTING_TRANSLATIONS.put("Limits", "Лимиты");
/*  768 */     SETTING_TRANSLATIONS.put("Line", "Линия");
/*  769 */     SETTING_TRANSLATIONS.put("Line Width", "Ширина линии");
/*  770 */     SETTING_TRANSLATIONS.put("Lines", "Линии");
/*  771 */     SETTING_TRANSLATIONS.put("List Lines Per Scroll", "Строк за прокрутку");
/*  772 */     SETTING_TRANSLATIONS.put("List Scroll Speed", "Скорость прокрутки списка");
/*  773 */     SETTING_TRANSLATIONS.put("List Smooth Scroll", "Плавная прокрутка списка");
/*  774 */     SETTING_TRANSLATIONS.put("Lists", "Списки");
/*  775 */     SETTING_TRANSLATIONS.put("Local timer for the FunTime трапка / драконья трапка ability use cooldown", "Локальный таймер кд использования трапки / драконьей трапки FunTime");
/*  776 */     SETTING_TRANSLATIONS.put("Logs every picked-up item to the chat", "Пишет в чат каждый поднятый предмет");
/*  777 */     SETTING_TRANSLATIONS.put("Longer Chat History", "Расширенная история чата");
/*  778 */     SETTING_TRANSLATIONS.put("Look around without moving your player", "Свободный обзор без поворота персонажа");
/*  779 */     SETTING_TRANSLATIONS.put("Mace Indicator", "Индикатор булавы");
/*  780 */     SETTING_TRANSLATIONS.put("Main Hand", "Основная рука");
/*  781 */     SETTING_TRANSLATIONS.put("Main Scale", "Основной масштаб");
/*  782 */     SETTING_TRANSLATIONS.put("Main X", "Основная X");
/*  783 */     SETTING_TRANSLATIONS.put("Main Y", "Основная Y");
/*  784 */     SETTING_TRANSLATIONS.put("Main Z", "Основная Z");
/*  785 */     SETTING_TRANSLATIONS.put("Marker Size", "Размер маркера");
/*  786 */     SETTING_TRANSLATIONS.put("Marker Style", "Стиль маркера");
/*  787 */     SETTING_TRANSLATIONS.put("Match", "Совпадение");
/*  788 */     SETTING_TRANSLATIONS.put("Match Your Username", "Совпадение с вашим ником");
/*  789 */     SETTING_TRANSLATIONS.put("Max FPS", "Макс FPS");
/*  790 */     SETTING_TRANSLATIONS.put("Max Render Distance", "Макс дистанция прорисовки");
/*  791 */     SETTING_TRANSLATIONS.put("Max Search Distance", "Макс дистанция поиска");
/*  792 */     SETTING_TRANSLATIONS.put("Max Zoom", "Макс зум");
/*  793 */     SETTING_TRANSLATIONS.put("Maximum", "Максимум");
/*  794 */     SETTING_TRANSLATIONS.put("Mention Color", "Цвет упоминания");
/*  795 */     SETTING_TRANSLATIONS.put("Mention Highlight", "Подсветка упоминаний");
/*  796 */     SETTING_TRANSLATIONS.put("Mention Sound", "Звук упоминания");
/*  797 */     SETTING_TRANSLATIONS.put("Message", "Сообщение");
/*  798 */     SETTING_TRANSLATIONS.put("Message Animation", "Анимация сообщений");
/*  799 */     SETTING_TRANSLATIONS.put("Message Animation Speed", "Скорость анимации сообщений");
/*  800 */     SETTING_TRANSLATIONS.put("Message Animation Type", "Тип анимации сообщений");
/*  801 */     SETTING_TRANSLATIONS.put("Metrics", "Метрики");
/*  802 */     SETTING_TRANSLATIONS.put("Min FPS", "Мин FPS");
/*  803 */     SETTING_TRANSLATIONS.put("Min Length", "Мин длина");
/*  804 */     SETTING_TRANSLATIONS.put("Minimal", "Минимальный");
/*  805 */     SETTING_TRANSLATIONS.put("Minimum", "Минимум");
/*  806 */     SETTING_TRANSLATIONS.put("Misc", "Прочее");
/*  807 */     SETTING_TRANSLATIONS.put("Mobs", "Мобы");
/*  808 */     SETTING_TRANSLATIONS.put("Mode", "Режим");
/*  809 */     SETTING_TRANSLATIONS.put("Mode Type", "Тип режима");
/*  810 */     SETTING_TRANSLATIONS.put("Model Size", "Размер модели");
/*  811 */     SETTING_TRANSLATIONS.put("Modern", "Современный");
/*  812 */     SETTING_TRANSLATIONS.put("Modify vanilla scoreboard with blur, custom colors, position, and scale", "Меняет ваниль scoreboard: блюр, цвета, позиция и масштаб");
/*  813 */     SETTING_TRANSLATIONS.put("MOTD", "MOTD");
/*  814 */     SETTING_TRANSLATIONS.put("Motion Blur", "Размытие в движении");
/*  815 */     SETTING_TRANSLATIONS.put("Motion blur effect", "Эффект motion blur");
/*  816 */     SETTING_TRANSLATIONS.put("N keybinds that send a chat message or slash command", "N биндов, отправляющих сообщение в чат или команду");
/*  817 */     SETTING_TRANSLATIONS.put("Name Overrides", "Переопределение имён");
/*  818 */     SETTING_TRANSLATIONS.put("Names", "Имена");
/*  819 */     SETTING_TRANSLATIONS.put("Nametag", "Тег имени");
/*  820 */     SETTING_TRANSLATIONS.put("Nametag options HUD", "HUD настроек тегов имён");
/*  821 */     SETTING_TRANSLATIONS.put("Nametag Suffix", "Суффикс тега");
/*  822 */     SETTING_TRANSLATIONS.put("Nametag Text Shadow", "Тень текста тега");
/*  823 */     SETTING_TRANSLATIONS.put("Nearest Only", "Только ближайшее");
/*  824 */     SETTING_TRANSLATIONS.put("Never", "Никогда");
/*  825 */     SETTING_TRANSLATIONS.put("Night Color", "Цвет ночи");
/*  826 */     SETTING_TRANSLATIONS.put("No Bobbing", "Без покачивания при ходьбе");
/*  827 */     SETTING_TRANSLATIONS.put("No Glow", "Без свечения");
/*  828 */     SETTING_TRANSLATIONS.put("No Hand Sway", "Без покачивания руки");
/*  829 */     SETTING_TRANSLATIONS.put("No Sway", "Без покачивания");
/*  830 */     SETTING_TRANSLATIONS.put("No Swing", "Без замаха");
/*  831 */     SETTING_TRANSLATIONS.put("None", "Нет");
/*  832 */     SETTING_TRANSLATIONS.put("Notification", "Уведомление");
/*  833 */     SETTING_TRANSLATIONS.put("Notify", "Уведомлять");
/*  834 */     SETTING_TRANSLATIONS.put("Number of Repetitions", "Количество повторов");
/*  835 */     SETTING_TRANSLATIONS.put("Off Hand", "Вторая рука");
/*  836 */     SETTING_TRANSLATIONS.put("Off Scale", "Масштаб 2-й руки");
/*  837 */     SETTING_TRANSLATIONS.put("Off X", "X 2-й руки");
/*  838 */     SETTING_TRANSLATIONS.put("Off Y", "Y 2-й руки");
/*  839 */     SETTING_TRANSLATIONS.put("Off Z", "Z 2-й руки");
/*  840 */     SETTING_TRANSLATIONS.put("Offhand", "Вторая рука");
/*  841 */     SETTING_TRANSLATIONS.put("Ognennyi Smerch Name", "Имя «Огненный смерч»");
/*  842 */     SETTING_TRANSLATIONS.put("On Death", "При смерти");
/*  843 */     SETTING_TRANSLATIONS.put("On Hit", "При ударе");
/*  844 */     SETTING_TRANSLATIONS.put("Only Players", "Только игроки");
/*  845 */     SETTING_TRANSLATIONS.put("Only Rare", "Только редкие");
/*  846 */     SETTING_TRANSLATIONS.put("Only Use Ground Speed", "Только наземная скорость");
/*  847 */     SETTING_TRANSLATIONS.put("Opacity", "Прозрачность");
/*  848 */     SETTING_TRANSLATIONS.put("Open inventory picker", "Открыть выбор из инвентаря");
/*  849 */     SETTING_TRANSLATIONS.put("Open", "Открыть");
/*  850 */     SETTING_TRANSLATIONS.put("Other", "Другое");
/*  851 */     SETTING_TRANSLATIONS.put("Others", "Другие");
/*  852 */     SETTING_TRANSLATIONS.put("Outline", "Контур");
/*  853 */     SETTING_TRANSLATIONS.put("Outline Color", "Цвет обводки");
/*  854 */     SETTING_TRANSLATIONS.put("Outline Thickness", "Толщина обводки");
/*  855 */     SETTING_TRANSLATIONS.put("Outline Width", "Толщина контура");
/*  856 */     SETTING_TRANSLATIONS.put("Output", "Вывод");
/*  857 */     SETTING_TRANSLATIONS.put("Output Mode", "Режим вывода");
/*  858 */     SETTING_TRANSLATIONS.put("Override the world projection's aspect ratio with a preset or manual factor", "Переопределяет соотношение сторон проекции пресетом или вручную");
/*  859 */     SETTING_TRANSLATIONS.put("Padding", "Отступы");
/*  860 */     SETTING_TRANSLATIONS.put("Particle Color", "Цвет частиц");
/*  861 */     SETTING_TRANSLATIONS.put("Particle Density", "Плотность частиц");
/*  862 */     SETTING_TRANSLATIONS.put("Particle Size", "Размер частиц");
/*  863 */     SETTING_TRANSLATIONS.put("Particle Speed", "Скорость частиц");
/*  864 */     SETTING_TRANSLATIONS.put("Particle Types", "Типы частиц");
/*  865 */     SETTING_TRANSLATIONS.put("Scoreboard", "Скорборд");
/*  866 */     SETTING_TRANSLATIONS.put("Boss Bar", "Босс-бар");
/*  867 */     SETTING_TRANSLATIONS.put("Particles", "Частицы");
/*  868 */     SETTING_TRANSLATIONS.put("Periodically attacks or uses item based on selected hand", "Периодически атакует или использует предмет в выбранной руке");
/*  869 */     SETTING_TRANSLATIONS.put("Ping HUD", "Пинг HUD");
/*  870 */     SETTING_TRANSLATIONS.put("Ping Number Shadow", "Тень числа пинга");
/*  871 */     SETTING_TRANSLATIONS.put("Pitch", "Тон");
/*  872 */     SETTING_TRANSLATIONS.put("Plast Name", "Имя «Пласт»");
/*  873 */     SETTING_TRANSLATIONS.put("Play Sound", "Воспроизводить звук");
/*  874 */     SETTING_TRANSLATIONS.put("Player Model HUD", "HUD модели игрока");
/*  875 */     SETTING_TRANSLATIONS.put("Player Reach", "Дальность игрока");
/*  876 */     SETTING_TRANSLATIONS.put("Players", "Игроки");
/*  877 */     SETTING_TRANSLATIONS.put("Plays a sound when any armor piece drops below a durability threshold", "Играет звук, когда часть брони опускается ниже порога прочности");
/*  878 */     SETTING_TRANSLATIONS.put("Potion Particles", "Частицы зелий");
/*  879 */     SETTING_TRANSLATIONS.put("Power", "Мощность");
/*  880 */     SETTING_TRANSLATIONS.put("Predict Held", "Предсказывать в руке");
/*  881 */     SETTING_TRANSLATIONS.put("Predictions", "Предсказания");
/*  882 */     SETTING_TRANSLATIONS.put("Prefix", "Префикс");
/*  883 */     SETTING_TRANSLATIONS.put("Press", "Нажать");
/*  884 */     SETTING_TRANSLATIONS.put("Prevent dropping items from chosen hotbar/offhand slots", "Запрещает выкидывать предметы из выбранных слотов хотбара/второй руки");
/*  885 */     SETTING_TRANSLATIONS.put("Privacy", "Приватность");
/*  886 */     SETTING_TRANSLATIONS.put("Projectile Trail", "След снаряда");
/*  887 */     SETTING_TRANSLATIONS.put("Projects the predicted impact path of throwables in your hands and your own projectiles in flight", "Рисует прогноз траектории метаемых предметов в руках и ваших снарядов в полёте");
/*  888 */     SETTING_TRANSLATIONS.put("Provider", "Провайдер");
/*  889 */     SETTING_TRANSLATIONS.put("Pulses healing potions, golden apples, and enchanted gapples in your inventory based on HP, saturation, and a re-eat timer", "Пульсирует зельями лечения, голд. яблоками и зачар. яблоками в инвентаре по HP, сытости и таймеру");
/*  890 */     SETTING_TRANSLATIONS.put("Quality", "Качество");
/*  891 */     SETTING_TRANSLATIONS.put("Radius", "Радиус");
/*  892 */     SETTING_TRANSLATIONS.put("Rain Density", "Плотность дождя");
/*  893 */     SETTING_TRANSLATIONS.put("Rainbow", "Радуга");
/*  894 */     SETTING_TRANSLATIONS.put("Random Colors", "Случайные цвета");
/*  895 */     SETTING_TRANSLATIONS.put("Range", "Дальность");
/*  896 */     SETTING_TRANSLATIONS.put("Reach", "Дальность атаки");
/*  897 */     SETTING_TRANSLATIONS.put("Reach Color", "Цвет реча");
/*  898 */     SETTING_TRANSLATIONS.put("Reach Display", "Отображение дальности");
/*  899 */     SETTING_TRANSLATIONS.put("Reach in Blocks", "Дальность в блоках");
/*  900 */     SETTING_TRANSLATIONS.put("Realistic item physics", "Реалистичная физика предметов");
/*  901 */     SETTING_TRANSLATIONS.put("Recolor Match", "Перекрашивать совпадение");
/*  902 */     SETTING_TRANSLATIONS.put("Recolor the vanilla block outline at your crosshair, optionally fill the faces", "Перекрашивает ванильный контур блока под прицелом, опционально заливает грани");
/*  903 */     SETTING_TRANSLATIONS.put("Records your coords on death so you can return for your dropped items", "Запоминает координаты смерти, чтобы вернуться за выпавшим");
/*  904 */     SETTING_TRANSLATIONS.put("Refresh Rate", "Частота обновления");
/*  905 */     SETTING_TRANSLATIONS.put("Blur Background FPS", "FPS фона с блюром");
/*  906 */     SETTING_TRANSLATIONS.put("Refresh Rate Scaling", "Масштаб частоты обновления");
/*  907 */     SETTING_TRANSLATIONS.put("Removes the fog and screen overlay applied while the camera is submerged in water or lava", "Убирает туман и оверлей при погружении камеры в воду или лаву");
/*  908 */     SETTING_TRANSLATIONS.put("Removes the right-click delay when throwing experience bottles", "Убирает задержку ПКМ при бросании бутылок опыта");
/*  909 */     SETTING_TRANSLATIONS.put("Render Mode", "Режим рендера");
/*  910 */     SETTING_TRANSLATIONS.put("Render-pipeline tweaks: hide glowing outlines, fire overlay, particles", "Твики рендера: скрыть свечение, оверлей огня, частицы");
/*  911 */     SETTING_TRANSLATIONS.put("Renders a 3D mini-version of your skin in the corner", "Рендерит 3D-мини-версию вашего скина в углу");
/*  912 */     SETTING_TRANSLATIONS.put("Renders your 27 storage slots or last-seen ender chest on the HUD", "Рендерит 27 слотов инвентаря или последний эндер-сундук на HUD");
/*  913 */     SETTING_TRANSLATIONS.put("English", "Английский");
/*  914 */     SETTING_TRANSLATIONS.put("Russian", "Русский");
/*  915 */     SETTING_TRANSLATIONS.put("Repeat", "Повторять");
/*  916 */     SETTING_TRANSLATIONS.put("Repeat Delay", "Задержка повтора");
/*  917 */     SETTING_TRANSLATIONS.put("Replace", "Замена");
/*  918 */     SETTING_TRANSLATIONS.put("Replace Message", "Заменять сообщение");
/*  919 */     SETTING_TRANSLATIONS.put("Replace Own Name Color", "Менять цвет своего ника");
/*  920 */     SETTING_TRANSLATIONS.put("Replacement", "Замена");
/*  921 */     SETTING_TRANSLATIONS.put("Replaces the visible FPS counter with a randomised value drawn from your configured range", "Заменяет видимый счётчик FPS случайным значением из заданного диапазона");
/*  922 */     SETTING_TRANSLATIONS.put("Replaces your own username in chat with a custom string", "Заменяет ваш ник в чате на свою строку");
/*  923 */     SETTING_TRANSLATIONS.put("Reset", "Сбросить");
/*  924 */     SETTING_TRANSLATIONS.put("Reset Cooldown", "Сбросить перезарядку");
/*  925 */     SETTING_TRANSLATIONS.put("Reset On Death", "Сброс при смерти");
/*  926 */     SETTING_TRANSLATIONS.put("Resume Zoom", "Продолжить зум");
/*  927 */     SETTING_TRANSLATIONS.put("Reverse Order", "Обратный порядок");
/*  928 */     SETTING_TRANSLATIONS.put("Right Click", "Правый клик");
/*  929 */     SETTING_TRANSLATIONS.put("Right Click CPS", "ПКМ CPS");
/*  930 */     SETTING_TRANSLATIONS.put("Rolling averages of reach, combo, damage, ping, and saturation", "Скользящие средние реча, комбо, урона, пинга и сытости");
/*  931 */     SETTING_TRANSLATIONS.put("Rotation Speed", "Скорость вращения");
/*  932 */     SETTING_TRANSLATIONS.put("Round To", "Округлять до");
/*  933 */     SETTING_TRANSLATIONS.put("Sample Size", "Размер выборки");
/*  934 */     SETTING_TRANSLATIONS.put("Saturation", "Насыщенность");
/*  935 */     SETTING_TRANSLATIONS.put("Save", "Сохранить");
/*  936 */     SETTING_TRANSLATIONS.put("Scale", "Масштаб");
/*  937 */     SETTING_TRANSLATIONS.put("Screencopy", "Снимок экрана");
/*  938 */     SETTING_TRANSLATIONS.put("Scroll Multiplier", "Множитель прокрутки");
/*  939 */     SETTING_TRANSLATIONS.put("Scroll Sensitivity", "Чувствительность прокрутки");
/*  940 */     SETTING_TRANSLATIONS.put("Sections", "Секции");
/*  941 */     SETTING_TRANSLATIONS.put("Self", "Себе");
/*  942 */     SETTING_TRANSLATIONS.put("Sends 'gg' or 'GG' in chat automatically after killing another player", "Автоматически отправляет «gg» или «GG» в чат после убийства игрока");
/*  943 */     SETTING_TRANSLATIONS.put("Sends /ah search <item in main hand> when the bind is pressed", "По биду отправляет /ah search <предмет в основной руке>");
/*  944 */     SETTING_TRANSLATIONS.put("Sensitivity", "Чувствительность");
/*  945 */     SETTING_TRANSLATIONS.put("Server Customizer", "Настройщик сервера");
/*  946 */     SETTING_TRANSLATIONS.put("Server List", "Список серверов");
/*  947 */     SETTING_TRANSLATIONS.put("Session", "Сессия");
/*  948 */     SETTING_TRANSLATIONS.put("Settings", "Настройки");
/*  949 */     SETTING_TRANSLATIONS.put("Shadow", "Тень");
/*  950 */     SETTING_TRANSLATIONS.put("Shadow Color", "Цвет тени");
/*  951 */     SETTING_TRANSLATIONS.put("Shape", "Форма");
/*  952 */     SETTING_TRANSLATIONS.put("Sharpness", "Резкость");
/*  953 */     SETTING_TRANSLATIONS.put("Show AM/PM", "Показывать AM/PM");
/*  954 */     SETTING_TRANSLATIONS.put("Show Axis Signs", "Показывать знаки осей");
/*  955 */     SETTING_TRANSLATIONS.put("Show Bind", "Показывать бинд");
/*  956 */     SETTING_TRANSLATIONS.put("Show Biome", "Показывать биом");
/*  957 */     SETTING_TRANSLATIONS.put("Show Bow", "Показывать лук");
/*  958 */     SETTING_TRANSLATIONS.put("Show Brackets", "Показывать скобки");
/*  959 */     SETTING_TRANSLATIONS.put("Show Break Time", "Показывать время ломки");
/*  960 */     SETTING_TRANSLATIONS.put("Show Chunk", "Показывать чанк");
/*  961 */     SETTING_TRANSLATIONS.put("Show Combo", "Показывать комбо");
/*  962 */     SETTING_TRANSLATIONS.put("Show Coordinates", "Показывать координаты");
/*  963 */     SETTING_TRANSLATIONS.put("Show Correct Tool", "Показывать нужный инструмент");
/*  964 */     SETTING_TRANSLATIONS.put("Show Count", "Показывать количество");
/*  965 */     SETTING_TRANSLATIONS.put("Show CPS Text", "Показывать текст CPS");
/*  966 */     SETTING_TRANSLATIONS.put("Show Current Zoom", "Показывать текущий зум");
/*  967 */     SETTING_TRANSLATIONS.put("Show Damage", "Показывать урон");
/*  968 */     SETTING_TRANSLATIONS.put("Show Damage In Armor", "Показывать урон по броне");
/*  969 */     SETTING_TRANSLATIONS.put("Show Day Phase", "Показывать фазу дня");
/*  970 */     SETTING_TRANSLATIONS.put("Show Death History", "Показывать историю смертей");
/*  971 */     SETTING_TRANSLATIONS.put("Show Decimals", "Показывать десятые");
/*  972 */     SETTING_TRANSLATIONS.put("Show Degree Number", "Показывать градусы");
/*  973 */     SETTING_TRANSLATIONS.put("Show Direction", "Показывать направление");
/*  974 */     SETTING_TRANSLATIONS.put("Show Effect Amplifier", "Показывать усилитель эффекта");
/*  975 */     SETTING_TRANSLATIONS.put("Show Effect Duration", "Показывать длительность эффекта");
/*  976 */     SETTING_TRANSLATIONS.put("Show Effects", "Показывать эффекты");
/*  977 */     SETTING_TRANSLATIONS.put("Show Entities", "Показывать сущности");
/*  978 */     SETTING_TRANSLATIONS.put("Show Eye Line", "Показывать линию взгляда");
/*  979 */     SETTING_TRANSLATIONS.put("Show FPS", "Показывать FPS");
/*  980 */     SETTING_TRANSLATIONS.put("Show Hand", "Показывать руку");
/*  981 */     SETTING_TRANSLATIONS.put("Show Health Bars", "Показывать полоски здоровья");
/*  982 */     SETTING_TRANSLATIONS.put("Show Held Item", "Показывать предмет в руке");
/*  983 */     SETTING_TRANSLATIONS.put("Show Icon", "Показывать иконку");
/*  984 */     SETTING_TRANSLATIONS.put("Show in chat", "Показывать в чате");
/*  985 */     SETTING_TRANSLATIONS.put("Show in Chat", "Показывать в чате");
/*  986 */     SETTING_TRANSLATIONS.put("Show in sprint hud", "Показывать в HUD спринта");
/*  987 */     SETTING_TRANSLATIONS.put("Show In Third Person", "Показывать от 3-го лица");
/*  988 */     SETTING_TRANSLATIONS.put("Show Intermediate", "Показывать промежуточные");
/*  989 */     SETTING_TRANSLATIONS.put("Show Inventory", "Показывать инвентарь");
/*  990 */     SETTING_TRANSLATIONS.put("Show Item", "Показывать предмет");
/*  991 */     SETTING_TRANSLATIONS.put("Show Killer", "Показывать убийцу");
/*  992 */     SETTING_TRANSLATIONS.put("Show Map", "Показывать карту");
/*  993 */     SETTING_TRANSLATIONS.put("Show Marker", "Показывать маркер");
/*  994 */     SETTING_TRANSLATIONS.put("Show Names", "Показывать имена");
/*  995 */     SETTING_TRANSLATIONS.put("Show Numbers", "Показывать числа");
/*  996 */     SETTING_TRANSLATIONS.put("Show On", "Показывать на");
/*  997 */     SETTING_TRANSLATIONS.put("Show Original On Hover", "Оригинал при наведении");
/*  998 */     SETTING_TRANSLATIONS.put("Show Ping", "Показывать пинг");
/*  999 */     SETTING_TRANSLATIONS.put("Show Ping Number", "Показывать число пинга");
/* 1000 */     SETTING_TRANSLATIONS.put("Show Player Heads", "Показывать головы игроков");
/* 1001 */     SETTING_TRANSLATIONS.put("Show Player Model", "Показывать модель игрока");
/* 1002 */     SETTING_TRANSLATIONS.put("Show Raw MOTD", "Показывать сырой MOTD");
/* 1003 */     SETTING_TRANSLATIONS.put("Show Reach", "Показывать реч");
/* 1004 */     SETTING_TRANSLATIONS.put("Show Saturation", "Показывать насыщение");
/* 1005 */     SETTING_TRANSLATIONS.put("Show Seconds", "Показывать секунды");
/* 1006 */     SETTING_TRANSLATIONS.put("Show Self", "Показывать себя");
/* 1007 */     SETTING_TRANSLATIONS.put("Show Self On Top", "Показывать себя сверху");
/* 1008 */     SETTING_TRANSLATIONS.put("Show Server Name", "Показывать имя сервера");
/* 1009 */     SETTING_TRANSLATIONS.put("Show Session Deaths", "Показывать смерти за сессию");
/* 1010 */     SETTING_TRANSLATIONS.put("Show Title", "Показывать заголовок");
/* 1011 */     SETTING_TRANSLATIONS.put("Show TPS", "Показывать TPS");
/* 1012 */     SETTING_TRANSLATIONS.put("Show Trident", "Показывать трезубец");
/* 1013 */     SETTING_TRANSLATIONS.put("Show Version", "Показывать версию");
/* 1014 */     SETTING_TRANSLATIONS.put("Show X", "Показывать X");
/* 1015 */     SETTING_TRANSLATIONS.put("Show Y", "Показывать Y");
/* 1016 */     SETTING_TRANSLATIONS.put("Show Z", "Показывать Z");
/* 1017 */     SETTING_TRANSLATIONS.put("Shows active potion effects on HUD with optional buff/debuff color coding", "Показывает активные эффекты зелий с опциональной цветовой разметкой бафф/дебафф");
/* 1018 */     SETTING_TRANSLATIONS.put("Shows animated cardinal direction strip", "Показывает анимированную полосу сторон света");
/* 1019 */     SETTING_TRANSLATIONS.put("Shows armor and durability on HUD", "Показывает броню и прочность в HUD");
/* 1020 */     SETTING_TRANSLATIONS.put("Shows block/entity info when targeting", "Показывает инфо блока/сущности при наведении");
/* 1021 */     SETTING_TRANSLATIONS.put("Shows current clicks per second on HUD", "Показывает текущий CPS в HUD");
/* 1022 */     SETTING_TRANSLATIONS.put("Shows current FPS on HUD", "Показывает текущий FPS в HUD");
/* 1023 */     SETTING_TRANSLATIONS.put("Shows current movement speed", "Показывает текущую скорость передвижения");
/* 1024 */     SETTING_TRANSLATIONS.put("Shows current session duration", "Показывает длительность текущей сессии");
/* 1025 */     SETTING_TRANSLATIONS.put("Shows current sprint, sneak, and flying state", "Показывает текущий спринт, сжатие Shift и полёт");
/* 1026 */     SETTING_TRANSLATIONS.put("Shows current world day", "Показывает текущий день мира");
/* 1027 */     SETTING_TRANSLATIONS.put("Shows hit distance on HUD", "Показывает дистанцию удара в HUD");
/* 1028 */     SETTING_TRANSLATIONS.put("Shows movement and mouse buttons on HUD", "Показывает кнопки движения и мыши в HUD");
/* 1029 */     SETTING_TRANSLATIONS.put("Shows position, chunk, biome, and direction", "Показывает позицию, чанк, биом и направление");
/* 1030 */     SETTING_TRANSLATIONS.put("Shows RAM usage in HUD", "Показывает использование RAM в HUD");
/* 1031 */     SETTING_TRANSLATIONS.put("Shows real local time, optionally with seconds and the in-game day phase", "Показывает реальное локальное время, опционально с секундами и фазой игрового дня");
/* 1032 */     SETTING_TRANSLATIONS.put("Shows saturation bar above hunger bar", "Показывает полосу сытости над полосой голода");
/* 1033 */     SETTING_TRANSLATIONS.put("Shows server IP address in HUD", "Показывает IP сервера в HUD");
/* 1034 */     SETTING_TRANSLATIONS.put("Shows server ping on HUD", "Показывает пинг сервера в HUD");
/* 1035 */     SETTING_TRANSLATIONS.put("Shows shulker box contents in a tooltip while hovering it in any inventory", "Показывает содержимое шалкеров в подсказке при наведении в любом инвентаре");
/* 1036 */     SETTING_TRANSLATIONS.put("Shows the HP of the player you most recently hit (port of ZakoHealthIndicator)", "Показывает HP последнего ударенного игрока (порт ZakoHealthIndicator)");
/* 1037 */     SETTING_TRANSLATIONS.put("Shows the remaining cooldown time as a number above each hotbar slot", "Показывает оставшийся кд числом над каждым слотом хотбара");
/* 1038 */     SETTING_TRANSLATIONS.put("Side", "Сторона");
/* 1039 */     SETTING_TRANSLATIONS.put("Size", "Размер");
/* 1040 */     SETTING_TRANSLATIONS.put("Skip Nicknames", "Пропускать никнеймы");
/* 1041 */     SETTING_TRANSLATIONS.put("Skip Same Language", "Пропускать тот же язык");
/* 1042 */     SETTING_TRANSLATIONS.put("Sky", "Небо");
/* 1043 */     SETTING_TRANSLATIONS.put("Sky Color", "Цвет неба");
/* 1044 */     SETTING_TRANSLATIONS.put("Sky Customizer", "Настройщик неба");
/* 1045 */     SETTING_TRANSLATIONS.put("Slot", "Слот");
/* 1046 */     SETTING_TRANSLATIONS.put("Slot 1", "Слот 1");
/* 1047 */     SETTING_TRANSLATIONS.put("Slot 2", "Слот 2");
/* 1048 */     SETTING_TRANSLATIONS.put("Slot 3", "Слот 3");
/* 1049 */     SETTING_TRANSLATIONS.put("Slot 4", "Слот 4");
/* 1050 */     SETTING_TRANSLATIONS.put("Slot 5", "Слот 5");
/* 1051 */     SETTING_TRANSLATIONS.put("Slot 6", "Слот 6");
/* 1052 */     SETTING_TRANSLATIONS.put("Slot 7", "Слот 7");
/* 1053 */     SETTING_TRANSLATIONS.put("Slot 8", "Слот 8");
/* 1054 */     SETTING_TRANSLATIONS.put("Slot 9", "Слот 9");
/* 1055 */     SETTING_TRANSLATIONS.put("Smooth", "Сглаживание");
/* 1056 */     SETTING_TRANSLATIONS.put("Smooth Camera", "Плавная камера");
/* 1057 */     SETTING_TRANSLATIONS.put("Smooth Camera (no F5)", "Плавная камера (без F5)");
/* 1058 */     SETTING_TRANSLATIONS.put("Smooth Camera Speed", "Скорость плавной камеры");
/* 1059 */     SETTING_TRANSLATIONS.put("Smooth F5", "Плавный F5");
/* 1060 */     SETTING_TRANSLATIONS.put("Smooth Fade", "Плавное затухание");
/* 1061 */     SETTING_TRANSLATIONS.put("Smooth Input Field", "Плавное поле ввода");
/* 1062 */     SETTING_TRANSLATIONS.put("Smooth Speed", "Скорость сглаживания");
/* 1063 */     SETTING_TRANSLATIONS.put("Smoothness", "Плавность");
/* 1064 */     SETTING_TRANSLATIONS.put("Sneak Duration", "Длительность сжатия Shift");
/* 1065 */     SETTING_TRANSLATIONS.put("Snezhok Zamorozka Name", "Имя «Снежок заморозки»");
/* 1066 */     SETTING_TRANSLATIONS.put("Snow Density", "Плотность снега");
/* 1067 */     SETTING_TRANSLATIONS.put("Snowball Tracker", "Отслеживание снежков");
/* 1068 */     SETTING_TRANSLATIONS.put("Solid", "Сплошной");
/* 1069 */     SETTING_TRANSLATIONS.put("Sound", "Звук");
/* 1070 */     SETTING_TRANSLATIONS.put("Sound Volume", "Громкость");
/* 1071 */     SETTING_TRANSLATIONS.put("Source", "Источник");
/* 1072 */     SETTING_TRANSLATIONS.put("Source Language", "Исходный язык");
/* 1073 */     SETTING_TRANSLATIONS.put("Speed", "Скорость");
/* 1074 */     SETTING_TRANSLATIONS.put("Sphere Opacity", "Прозрачность сферы");
/* 1075 */     SETTING_TRANSLATIONS.put("Sphere Y Offset", "Смещение сферы по Y");
/* 1076 */     SETTING_TRANSLATIONS.put("Splash Potion Particles", "Частицы взрывных зелий");
/* 1077 */     SETTING_TRANSLATIONS.put("Square", "Квадрат");
/* 1078 */     SETTING_TRANSLATIONS.put("State", "Состояние");
/* 1079 */     SETTING_TRANSLATIONS.put("Static", "Статичный");
/* 1080 */     SETTING_TRANSLATIONS.put("Status Effects", "Эффекты состояния");
/* 1081 */     SETTING_TRANSLATIONS.put("Step", "Шаг");
/* 1082 */     SETTING_TRANSLATIONS.put("Streamer Mode", "Режим стримера");
/* 1083 */     SETTING_TRANSLATIONS.put("Strength", "Сила");
/* 1084 */     SETTING_TRANSLATIONS.put("Style", "Стиль");
/* 1085 */     SETTING_TRANSLATIONS.put("Sunrise/Sunset Boost", "Усиление рассвета/заката");
/* 1086 */     SETTING_TRANSLATIONS.put("Swap chestplate and elytra with a single keybind", "Меняет нагрудник и элитры одним бидом");
/* 1087 */     SETTING_TRANSLATIONS.put("Swap Slot", "Слот замены");
/* 1088 */     SETTING_TRANSLATIONS.put("Swap Type", "Тип замены");
/* 1089 */     SETTING_TRANSLATIONS.put("Sphere -> Totem", "Сфера -> Тотем");
/* 1090 */     SETTING_TRANSLATIONS.put("Sphere -> Talisman", "Сфера -> Талисман");
/* 1091 */     SETTING_TRANSLATIONS.put("Talisman -> Totem", "Талисман -> Тотем");
/* 1092 */     SETTING_TRANSLATIONS.put("Talisman -> Talisman", "Талисман -> Талисман");
/* 1093 */     SETTING_TRANSLATIONS.put("Sphere -> Sphere", "Сфера -> Сфера");
/* 1094 */     SETTING_TRANSLATIONS.put("Swaps items on button press", "Меняет предметы по нажатию кнопки");
/* 1095 */     SETTING_TRANSLATIONS.put("Switch Side", "Сменить сторону");
/* 1096 */     SETTING_TRANSLATIONS.put("Tab Animation", "Анимация таба");
/* 1097 */     SETTING_TRANSLATIONS.put("Tab Animation Speed", "Скорость анимации таба");
/* 1098 */     SETTING_TRANSLATIONS.put("Tab Close Interpolation", "Интерполяция закрытия таба");
/* 1099 */     SETTING_TRANSLATIONS.put("Tab Colors", "Цвета таба");
/* 1100 */     SETTING_TRANSLATIONS.put("Tab Fade", "Затухание таба");
/* 1101 */     SETTING_TRANSLATIONS.put("Tab List", "Tab список");
/* 1102 */     SETTING_TRANSLATIONS.put("Tab Open Interpolation", "Интерполяция открытия таба");
/* 1103 */     SETTING_TRANSLATIONS.put("Tab overlay settings HUD", "HUD настроек оверлея таба");
/* 1104 */     SETTING_TRANSLATIONS.put("Tab Slide", "Анимация таба");
/* 1105 */     SETTING_TRANSLATIONS.put("Target Delay", "Задержка цели");
/* 1106 */     SETTING_TRANSLATIONS.put("Target Language", "Целевой язык");
/* 1107 */     SETTING_TRANSLATIONS.put("Targeting", "Цель");
/* 1108 */     SETTING_TRANSLATIONS.put("Temperature", "Температура");
/* 1109 */     SETTING_TRANSLATIONS.put("Text Color", "Цвет текста");
/* 1110 */     SETTING_TRANSLATIONS.put("Text Shadow", "Тень текста");
/* 1111 */     SETTING_TRANSLATIONS.put("Theme", "Тема");
/* 1112 */     SETTING_TRANSLATIONS.put("Theme Color", "Цвет темы");
/* 1113 */     SETTING_TRANSLATIONS.put("Thickness", "Толщина");
/* 1114 */     SETTING_TRANSLATIONS.put("Third Person Nametag", "Тег от 3-го лица");
/* 1115 */     SETTING_TRANSLATIONS.put("Threshold (%)", "Порог (%)");
/* 1116 */     SETTING_TRANSLATIONS.put("Thunder Strength", "Сила грозы");
/* 1117 */     SETTING_TRANSLATIONS.put("Tier", "Уровень");
/* 1118 */     SETTING_TRANSLATIONS.put("Tint", "Окрашивание");
/* 1119 */     SETTING_TRANSLATIONS.put("Time", "Время");
/* 1120 */     SETTING_TRANSLATIONS.put("Time Changer", "Изменение времени");
/* 1121 */     SETTING_TRANSLATIONS.put("Timestamp", "Время");
/* 1122 */     SETTING_TRANSLATIONS.put("Tints inventory slots holding signature items so they're visible at a glance", "Подкрашивает слоты инвентаря с особыми предметами, чтобы их было видно сразу");
/* 1123 */     SETTING_TRANSLATIONS.put("Reveal another custom highlight row and pick an inventory item", "Показывает ещё одну строку для своего предмета и позволяет выбрать его из инвентаря");
/* 1124 */     SETTING_TRANSLATIONS.put("Tints maces in your inventory red/yellow/green based on attack cooldown progress", "Подкрашивает булавы красным/жёлтым/зелёным по прогрессу кд атаки");
/* 1125 */     SETTING_TRANSLATIONS.put("Toggle", "Переключатель");
/* 1126 */     SETTING_TRANSLATIONS.put("Toggle Message", "Сообщение о переключении");
/* 1127 */     SETTING_TRANSLATIONS.put("Total", "Всего");
/* 1128 */     SETTING_TRANSLATIONS.put("Totem Indicator", "Индикатор тотема");
/* 1129 */     SETTING_TRANSLATIONS.put("TPS HUD", "TPS HUD");
/* 1130 */     SETTING_TRANSLATIONS.put("Tracks who pops totems near you - prints to chat and adds \\", "Отслеживает, кто рядом съедает тотемы — пишет в чат и добавляет \\");
/* 1131 */     SETTING_TRANSLATIONS.put("Trajectory", "Траектория");
/* 1132 */     SETTING_TRANSLATIONS.put("Translate", "Перевести");
/* 1133 */     SETTING_TRANSLATIONS.put("Translator", "Переводчик");
/* 1134 */     SETTING_TRANSLATIONS.put("Trap", "Трапка");
/* 1135 */     SETTING_TRANSLATIONS.put("Trap Name", "Имя ловушки");
/* 1136 */     SETTING_TRANSLATIONS.put("Trap Type", "Тип ловушки");
/* 1137 */     SETTING_TRANSLATIONS.put("Trap Name", "Имя «Трапка»");
/* 1138 */     SETTING_TRANSLATIONS.put("Shows a draggable text timer for the FunTime trapka / dragon trapka cooldown", "Показывает перетаскиваемый текстовый таймер кулдауна FunTime трапки / драконьей трапки");
/* 1139 */     SETTING_TRANSLATIONS.put("Which trapka variant the timer measures (regular = 15s, dragon = 20s)", "Какой вариант трапки измерять: обычная 15с или драконья 20с");
/* 1140 */     SETTING_TRANSLATIONS.put("Substring matched against the held item's display name when deciding to start the timer", "Подстрока, которая ищется в названии предмета в руке для запуска таймера");
/* 1141 */     SETTING_TRANSLATIONS.put("Trigger", "Триггер");
/* 1142 */     SETTING_TRANSLATIONS.put("Tuning", "Настройка");
/* 1143 */     SETTING_TRANSLATIONS.put("Tweaks for chat and screenshots: collapse repeated messages, copy F2 screenshots to clipboard", "Твики чата и скриншотов: сворачивание повторов, копирование F2 в буфер");
/* 1144 */     SETTING_TRANSLATIONS.put("Twilight", "Сумерки");
/* 1145 */     SETTING_TRANSLATIONS.put("Underline", "Подчёркивание");
/* 1146 */     SETTING_TRANSLATIONS.put("Upon Impact", "При ударе");
/* 1147 */     SETTING_TRANSLATIONS.put("Use /feed Command", "Использовать /feed");
/* 1148 */     SETTING_TRANSLATIONS.put("Use Held Item", "Использовать предмет в руке");
/* 1149 */     SETTING_TRANSLATIONS.put("Use Preset", "Использовать пресет");
/* 1150 */     SETTING_TRANSLATIONS.put("Vanilla", "Ванилла");
/* 1151 */     SETTING_TRANSLATIONS.put("Vertical Layout", "Вертикальный макет");
/* 1152 */     SETTING_TRANSLATIONS.put("Vibrance", "Сочность");
/* 1153 */     SETTING_TRANSLATIONS.put("Visible", "Видимость");
/* 1154 */     SETTING_TRANSLATIONS.put("Visual", "Визуальное");
/* 1155 */     SETTING_TRANSLATIONS.put("Volume", "Громкость");
/* 1156 */     SETTING_TRANSLATIONS.put("Warns when the held pickaxe is low on durability and can auto-swap to a safe hotbar slot", "Предупреждает при низкой прочности кирки в руке и может авто-сменить на безопасный слот");
/* 1157 */     SETTING_TRANSLATIONS.put("Weather Changer", "Изменение погоды");
/* 1158 */     SETTING_TRANSLATIONS.put("Weather Type", "Тип погоды");
/* 1159 */     SETTING_TRANSLATIONS.put("Whitelist", "Белый список");
/* 1160 */     SETTING_TRANSLATIONS.put("Width", "Ширина");
/* 1161 */     SETTING_TRANSLATIONS.put("World Animator", "World Animator");
/* 1162 */     SETTING_TRANSLATIONS.put("X Offset", "Смещение X");
/* 1163 */     SETTING_TRANSLATIONS.put("Y Offset", "Смещение Y");
/* 1164 */     SETTING_TRANSLATIONS.put("Yavnaya Pyl Name", "Имя «Явная пыль»");
/* 1165 */     SETTING_TRANSLATIONS.put("Zoom", "Зум");
/* 1166 */     SETTING_TRANSLATIONS.put("Zoom functionality", "Функция зума");
/* 1167 */     SETTING_TRANSLATIONS.put("Zoom In Duration", "Длительность приближения");
/* 1168 */     SETTING_TRANSLATIONS.put("Zoom In Interpolation", "Интерполяция приближения");
/* 1169 */     SETTING_TRANSLATIONS.put("Zoom Out Duration", "Длительность отдаления");
/* 1170 */     SETTING_TRANSLATIONS.put("Zoom Out Interpolation", "Интерполяция отдаления");
/* 1171 */     SETTING_TRANSLATIONS.put("Zoom Sensitivity", "Чувствительность зума");
/* 1172 */     SETTING_TRANSLATIONS.put("Zoom Speed", "Скорость зума");
/* 1173 */     SETTING_TRANSLATIONS.put("Display Items", "Отображаемые элементы");
/* 1174 */     SETTING_TRANSLATIONS.put("Choose Highlight Item", "Выбор предмета для подсветки");
/* 1175 */     SETTING_TRANSLATIONS.put("Click any inventory item to add it to Item Highlighter", "Нажми на предмет в инвентаре, чтобы добавить его в Item Highlighter");
/* 1176 */     SETTING_TRANSLATIONS.put("Click outside or press Esc to go back", "Нажми вне окна или Esc, чтобы вернуться");
/* 1177 */     SETTING_TRANSLATIONS.put("Click to choose item", "Нажми, чтобы выбрать предмет");
/* 1178 */     SETTING_TRANSLATIONS.put("Custom Item Slot 1", "Кастомный слот 1");
/* 1179 */     SETTING_TRANSLATIONS.put("Custom Item Slot 2", "Кастомный слот 2");
/* 1180 */     SETTING_TRANSLATIONS.put("Custom Item Slot 3", "Кастомный слот 3");
/* 1181 */     SETTING_TRANSLATIONS.put("Custom Item Slot 4", "Кастомный слот 4");
/* 1182 */     SETTING_TRANSLATIONS.put("Custom Item Slot 5", "Кастомный слот 5");
/* 1183 */     SETTING_TRANSLATIONS.put("Custom Item Slot 6", "Кастомный слот 6");
/* 1184 */     SETTING_TRANSLATIONS.put("Custom Item Slot 7", "Кастомный слот 7");
/* 1185 */     SETTING_TRANSLATIONS.put("Custom Item Slot 8", "Кастомный слот 8");
/* 1186 */     SETTING_TRANSLATIONS.put("Current selection", "Текущий выбор");
/* 1187 */     SETTING_TRANSLATIONS.put("Custom", "Кастом");
/* 1188 */     SETTING_TRANSLATIONS.put("Disorientation", "Дезориентация");
/* 1189 */     SETTING_TRANSLATIONS.put("Disorientation Name", "Имя «Дезориентация»");
/* 1190 */     SETTING_TRANSLATIONS.put("Divine Aura", "Божья аура");
/* 1191 */     SETTING_TRANSLATIONS.put("Divine Aura Name", "Имя «Божья аура»");
/* 1192 */     SETTING_TRANSLATIONS.put("Dragon Trap", "Драконья трапка");
/* 1193 */     SETTING_TRANSLATIONS.put("Dragon Trap Name", "Имя «Драконья трапка»");
/* 1194 */     SETTING_TRANSLATIONS.put("Enabled Abilities", "Включённые способности");
/* 1195 */     SETTING_TRANSLATIONS.put("Entity Hit Color", "Цвет попадания по сущности");
/* 1196 */     SETTING_TRANSLATIONS.put("Entity Theme Color", "Тематический цвет сущности");
/* 1197 */     SETTING_TRANSLATIONS.put("Entity Color Preset", "Пресет цвета сущности");
/* 1198 */     SETTING_TRANSLATIONS.put("Experience Bottle", "Бутылка опыта");
/* 1199 */     SETTING_TRANSLATIONS.put("Fire Vortex", "Огненный смерч");
/* 1200 */     SETTING_TRANSLATIONS.put("Fire Vortex Name", "Имя «Огненный смерч»");
/* 1201 */     SETTING_TRANSLATIONS.put("Freeze Snowball", "Снежок заморозки");
/* 1202 */     SETTING_TRANSLATIONS.put("Freeze Snowball Name", "Имя «Снежок заморозки»");
/* 1203 */     SETTING_TRANSLATIONS.put("Fog Mix", "Смешивание с туманом");
/* 1204 */     SETTING_TRANSLATIONS.put("Golden Apple", "Золотое яблоко");
/* 1205 */     SETTING_TRANSLATIONS.put("Enchanted Golden Apple", "Зачарованное золотое яблоко");
/* 1206 */     SETTING_TRANSLATIONS.put("Ender Pearl", "Эндер перл");
/* 1207 */     SETTING_TRANSLATIONS.put("Chorus Fruit", "Хорус");
/* 1208 */     SETTING_TRANSLATIONS.put("Chorus Bind", "Бинд хоруса");
/* 1209 */     SETTING_TRANSLATIONS.put("Enderpearl Bind", "Бинд эндерпёрла");
/* 1210 */     SETTING_TRANSLATIONS.put("Healing Potion Bind", "Бинд зелья исцеления");
/* 1211 */     SETTING_TRANSLATIONS.put("Trap Bind", "Бинд трапки");
/* 1212 */     SETTING_TRANSLATIONS.put("Plate Bind", "Бинд пласта");
/* 1213 */     SETTING_TRANSLATIONS.put("Disorientation Bind", "Бинд дезориентации");
/* 1214 */     SETTING_TRANSLATIONS.put("Revealing Dust Bind", "Бинд явной пыли");
/* 1215 */     SETTING_TRANSLATIONS.put("Ball Lightning Bind", "Бинд шаровой молнии");
/* 1216 */     SETTING_TRANSLATIONS.put("Slime Lump Bind", "Бинд кома слизи");
/* 1217 */     SETTING_TRANSLATIONS.put("Turtle Grip Bind", "Бинд черепашьего захвата");
/* 1218 */     SETTING_TRANSLATIONS.put("Spider Fate Bind", "Бинд паучьей судьбы");
/* 1219 */     SETTING_TRANSLATIONS.put("Stun Bind", "Бинд стана");
/* 1220 */     SETTING_TRANSLATIONS.put("Magnetic Sphere Bind", "Бинд магнитного шара");
/* 1221 */     SETTING_TRANSLATIONS.put("Explosive Trap Bind", "Бинд взрывной трапки");
/* 1222 */     SETTING_TRANSLATIONS.put("Explosive Thing Bind", "Бинд взрывной штучки");
/* 1223 */     SETTING_TRANSLATIONS.put("Star Stun Bind", "Бинд стана звезды");
/* 1224 */     SETTING_TRANSLATIONS.put("Snow Lump Bind", "Бинд кома снега");
/* 1225 */     SETTING_TRANSLATIONS.put("Ball Lightning Name", "Имя «Шаровая молния»");
/* 1226 */     SETTING_TRANSLATIONS.put("Slime Lump Name", "Имя «Ком слизи»");
/* 1227 */     SETTING_TRANSLATIONS.put("Turtle Grip Name", "Имя «Черепаший захват»");
/* 1228 */     SETTING_TRANSLATIONS.put("Spider Fate Name", "Имя «Паучья судьба»");
/* 1229 */     SETTING_TRANSLATIONS.put("Stun Name", "Имя «Стан»");
/* 1230 */     SETTING_TRANSLATIONS.put("Magnetic Sphere Name", "Имя «Магнитный шар»");
/* 1231 */     SETTING_TRANSLATIONS.put("Explosive Trap Name", "Имя «Взрывная трапка»");
/* 1232 */     SETTING_TRANSLATIONS.put("Explosive Thing Name", "Имя «Взрывная штучка»");
/* 1233 */     SETTING_TRANSLATIONS.put("Star Stun Name", "Имя «Стан звезды»");
/* 1234 */     SETTING_TRANSLATIONS.put("Snow Lump Name", "Имя «Ком снега»");
/* 1235 */     SETTING_TRANSLATIONS.put("Locked Slots", "Заблокированные слоты");
/* 1236 */     SETTING_TRANSLATIONS.put("Join a world to choose an item", "Зайди в мир, чтобы выбрать предмет");
/* 1237 */     SETTING_TRANSLATIONS.put("Jack O'Lantern", "Светильник Джека");
/* 1238 */     SETTING_TRANSLATIONS.put("Plate", "Пласт");
/* 1239 */     SETTING_TRANSLATIONS.put("Plate Name", "Имя «Пласт»");
/* 1240 */     SETTING_TRANSLATIONS.put("Pulsate Speed", "Скорость пульсации");
/* 1241 */     SETTING_TRANSLATIONS.put("Revealing Dust", "Явная пыль");
/* 1242 */     SETTING_TRANSLATIONS.put("Revealing Dust Name", "Имя «Явная пыль»");
/* 1243 */     SETTING_TRANSLATIONS.put("Snowball", "Снежок");
/* 1244 */     SETTING_TRANSLATIONS.put("Stun", "Стан");
/* 1245 */     SETTING_TRANSLATIONS.put("Trapka 2", "Трапка 2");
/* 1246 */     SETTING_TRANSLATIONS.put("Explosive Trapka", "Взрывная трапка");
/* 1247 */     SETTING_TRANSLATIONS.put("Selected items are highlighted immediately", "Выбранные предметы подсвечиваются сразу");
/* 1248 */     SETTING_TRANSLATIONS.put("Totem of Undying", "Тотем бессмертия");
/* 1249 */     SETTING_TRANSLATIONS.put("Trap", "Трапка");
/* 1250 */     SETTING_TRANSLATIONS.put("Translation Options", "Параметры перевода");
/* 1251 */     SETTING_TRANSLATIONS.put("Hud Text Color", "Цвет текста HUD");
/* 1252 */     SETTING_TRANSLATIONS.put("Corner Rounding", "Закругление краёв");
/* 1253 */     SETTING_TRANSLATIONS.put("Default color for HUD text that does not use its own dynamic tint", "Цвет текста HUD по умолчанию для элементов без собственной динамической окраски");
/* 1254 */     SETTING_TRANSLATIONS.put("Render Other Player Cosmetics", "Показывать косметику других игроков");
/* 1255 */     SETTING_TRANSLATIONS.put("Render Phaze cosmetics equipped by other players", "Показывать косметику Phaze, надетую другими игроками");
/*      */ 
/*      */ 
/*      */     
/* 1259 */     SETTING_TRANSLATIONS.put("Active", "Активная");
/* 1260 */     SETTING_TRANSLATIONS.put("Applies to Main Menu, Mod Menu, Sodium and other screens", "Применяется к главному меню, Mod Menu, Sodium и другим экранам");
/* 1261 */     SETTING_TRANSLATIONS.put("Castle", "Замок");
/* 1262 */     SETTING_TRANSLATIONS.put("Chateau", "Шато");
/* 1263 */     SETTING_TRANSLATIONS.put("Drop .zip or use Phaze/Panoramas", "Перетащи .zip сюда или в Phaze/Panoramas");
/* 1264 */     SETTING_TRANSLATIONS.put("External", "Внешнее");
/* 1265 */     SETTING_TRANSLATIONS.put("Fabric Mod Menu", "Меню модов");
/* 1266 */     SETTING_TRANSLATIONS.put("Flashback Mod", "Flashback");
/* 1267 */     SETTING_TRANSLATIONS.put("Menu FPS Limit", "Лимит FPS меню");
/* 1268 */     SETTING_TRANSLATIONS.put("Minecraft Realms", "Realms");
/* 1269 */     SETTING_TRANSLATIONS.put("Minecraft Settings", "Настройки");
/* 1270 */     SETTING_TRANSLATIONS.put("Multiplayer", "Сетевая игра");
/* 1271 */     SETTING_TRANSLATIONS.put("No panoramas found", "Панорамы не найдены");
/* 1272 */     SETTING_TRANSLATIONS.put("Not affiliated with Mojang or Microsoft. Do not distribute!", "Не связано с Mojang или Microsoft. Не распространять!");
/* 1273 */     SETTING_TRANSLATIONS.put("Panorama Preset", "Пресет панорамы");
/* 1274 */     SETTING_TRANSLATIONS.put("Post-Soviet Night", "Постсоветская ночь");
/* 1275 */     SETTING_TRANSLATIONS.put("Quit", "Выход");
/* 1276 */     SETTING_TRANSLATIONS.put("Reset to default", "Сбросить на стандартную");
/* 1277 */     SETTING_TRANSLATIONS.put("Rotating Panorama Speed", "Скорость вращения панорамы");
/* 1278 */     SETTING_TRANSLATIONS.put("Search...", "Поиск...");
/* 1279 */     SETTING_TRANSLATIONS.put("Select Theme", "Выбор темы");
/* 1280 */     SETTING_TRANSLATIONS.put("Singleplayer", "Одиночная игра");
/* 1281 */     SETTING_TRANSLATIONS.put("Theme Selector", "Выбор темы");
/* 1282 */     SETTING_TRANSLATIONS.put("Themes", "Темы");
/* 1283 */     SETTING_TRANSLATIONS.put("just now", "только что");
/* 1284 */     SETTING_TRANSLATIONS.put("General", "Общие");
/* 1285 */     SETTING_TRANSLATIONS.put("Render Health", "Показывать здоровье");
/* 1286 */     SETTING_TRANSLATIONS.put("Render Text", "Показывать текст");
/* 1287 */     SETTING_TRANSLATIONS.put("Fill Color", "Цвет заполнения");
/* 1288 */     SETTING_TRANSLATIONS.put("Rainbow Speed", "Скорость радуги");
/* 1289 */     SETTING_TRANSLATIONS.put("Show the boss health bar", "Показывать полосу здоровья босса");
/* 1290 */     SETTING_TRANSLATIONS.put("Show the boss bar name", "Показывать название боссбара");
/* 1291 */     SETTING_TRANSLATIONS.put("Boss bar color mode", "Режим цвета боссбара");
/* 1292 */     SETTING_TRANSLATIONS.put("Boss bar fill color", "Цвет заполнения боссбара");
/* 1293 */     SETTING_TRANSLATIONS.put("Speed of the boss bar rainbow cycle", "Скорость полного радужного цикла боссбара");
/* 1294 */     SETTING_TRANSLATIONS.put("Customizable boss bar", "Настраиваемый боссбар");
/* 1295 */     SETTING_TRANSLATIONS.put("Advanced", "Расширенное");
/* 1296 */     SETTING_TRANSLATIONS.put("Blocks", "Блоки");
/* 1297 */     SETTING_TRANSLATIONS.put("Fluids", "Жидкости");
/* 1298 */     SETTING_TRANSLATIONS.put("Sky", "Небо");
/* 1299 */     SETTING_TRANSLATIONS.put("Clouds", "Облака");
/* 1300 */     SETTING_TRANSLATIONS.put("Players", "Игроки");
/* 1301 */     SETTING_TRANSLATIONS.put("Entities", "Сущности");
/* 1302 */     SETTING_TRANSLATIONS.put("Red", "Красный");
/* 1303 */     SETTING_TRANSLATIONS.put("Green", "Зелёный");
/* 1304 */     SETTING_TRANSLATIONS.put("Blue", "Синий");
/* 1305 */     SETTING_TRANSLATIONS.put("Alpha", "Прозрачность");
/* 1306 */     SETTING_TRANSLATIONS.put("Brightness", "Яркость");
/* 1307 */     SETTING_TRANSLATIONS.put("Saturation", "Насыщенность");
/* 1308 */     SETTING_TRANSLATIONS.put("Open", "Открыть");
/* 1309 */     SETTING_TRANSLATIONS.put("World color correction for blocks", "Цветокоррекция мира для блоков");
/* 1310 */     SETTING_TRANSLATIONS.put("World color correction for fluids", "Цветокоррекция мира для жидкостей");
/* 1311 */     SETTING_TRANSLATIONS.put("World color correction for empty sky", "Цветокоррекция пустого неба");
/* 1312 */     SETTING_TRANSLATIONS.put("World color correction for clouds", "Цветокоррекция облаков");
/* 1313 */     SETTING_TRANSLATIONS.put("World color correction for players", "Цветокоррекция игроков");
/* 1314 */     SETTING_TRANSLATIONS.put("World color correction for entities", "Цветокоррекция сущностей");
/* 1315 */     SETTING_TRANSLATIONS.put("Red multiplier", "Множитель красного");
/* 1316 */     SETTING_TRANSLATIONS.put("Green multiplier", "Множитель зелёного");
/* 1317 */     SETTING_TRANSLATIONS.put("Blue multiplier", "Множитель синего");
/* 1318 */     SETTING_TRANSLATIONS.put("Alpha multiplier", "Множитель прозрачности");
/* 1319 */     SETTING_TRANSLATIONS.put("Brightness offset", "Смещение яркости");
/* 1320 */     SETTING_TRANSLATIONS.put("Saturation multiplier", "Множитель насыщенности");
/*      */ 
/*      */ 
/*      */     
/* 1324 */     SETTING_TRANSLATIONS.put("Pack Organizer", "Органайзер ресурспаков");
/* 1325 */     SETTING_TRANSLATIONS.put("Pack Previews", "Превью ресурспаков");
/* 1326 */     SETTING_TRANSLATIONS.put("Show real pack textures and resource details when hovering a pack", "Показывать реальные текстуры и данные пака при наведении");
/* 1327 */     SETTING_TRANSLATIONS.put("Search Bar", "Поиск по пакам");
/* 1328 */     SETTING_TRANSLATIONS.put("Show quick search bar on resource pack screen", "Показывать быстрый поиск на экране ресурспаков");
/* 1329 */     SETTING_TRANSLATIONS.put("Adds a quick search bar and a real texture preview to the resource pack screen", "Добавляет быстрый поиск и настоящее превью текстур на экран ресурспаков");
/* 1330 */     SETTING_TRANSLATIONS.put("Low HP Warning", "Предупреждение о низком HP");
/* 1331 */     SETTING_TRANSLATIONS.put("Threshold (HP)", "Порог (HP)");
/* 1332 */     SETTING_TRANSLATIONS.put("HP threshold to activate warning (6.0 = 3 hearts)", "Порог HP для предупреждения (6.0 = 3 сердца)");
/* 1333 */     SETTING_TRANSLATIONS.put("Warning vignette tint color", "Цвет виньетки предупреждения");
/* 1334 */     SETTING_TRANSLATIONS.put("Warning vignette tint and intensity (alpha)", "Цвет и интенсивность виньетки предупреждения (альфа-канал)");
/* 1335 */     SETTING_TRANSLATIONS.put("Pulsing animation speed", "Скорость пульсации");
/* 1336 */     SETTING_TRANSLATIONS.put("Maximum opacity of the vignette", "Максимальная прозрачность виньетки");
/* 1337 */     SETTING_TRANSLATIONS.put("Play immersive heartbeat sound while in critical state", "Проигрывать сердцебиение в критическом состоянии");
/* 1338 */     SETTING_TRANSLATIONS.put("Heartbeat sound volume", "Громкость сердцебиения");
/* 1339 */     SETTING_TRANSLATIONS.put("Smooth cinematic screen vignette and pulsing heartbeat when health is low", "Плавная виньетка и пульсирующее сердцебиение при низком здоровье");
/* 1340 */     SETTING_TRANSLATIONS.put("Search", "Поиск");
/* 1341 */     SETTING_TRANSLATIONS.put("Search packs...", "Поиск ресурспаков...");
/*      */     
/* 1343 */     SETTING_TRANSLATIONS.put("Turquoise", "Бирюзовый");
/* 1344 */     SETTING_TRANSLATIONS.put("Golden", "Золотой");
/* 1345 */     SETTING_TRANSLATIONS.put("Ruby", "Рубиновый");
/* 1346 */     SETTING_TRANSLATIONS.put("Glint color mode", "Режим цвета зачарования");
/* 1347 */     SETTING_TRANSLATIONS.put("Custom glint color", "Свой цвет зачарования");
/* 1348 */     SETTING_TRANSLATIONS.put("Glint movement and color animation speed", "Скорость перелива и движения блеска зачарования");
/* 1349 */     SETTING_TRANSLATIONS.put("Custom color and animation speed for enchanted item and armor glint", "Настройка цвета и скорости блеска зачарованных предметов и брони");
/* 1350 */     SETTING_TRANSLATIONS.put("Pulse Speed", "Скорость пульсации");
/* 1351 */     SETTING_TRANSLATIONS.put("Heartbeat Sound", "Звук сердцебиения");
/*      */     
/* 1353 */     SETTING_TRANSLATIONS.put("Voxels settings", "Настройки вокселей");
/* 1354 */     SETTING_TRANSLATIONS.put("Voxel Depth", "Глубина вокселей");
/* 1355 */     SETTING_TRANSLATIONS.put("Depth of extruded outer skin voxels", "Глубина объёмных вокселей внешнего слоя скина");
/* 1356 */     SETTING_TRANSLATIONS.put("Max Distance", "Максимальная дальность");
/* 1357 */     SETTING_TRANSLATIONS.put("Maximum distance to render 3D voxels (blocks)", "Максимальная дальность отображения 3D-вокселей (в блоках)");
/* 1358 */     SETTING_TRANSLATIONS.put("Show Others", "Показывать других");
/* 1359 */     SETTING_TRANSLATIONS.put("Apply 3D skin layer to other players", "Показывать объёмный слой скина у других игроков");
/* 1360 */     SETTING_TRANSLATIONS.put("3D Hat", "3D-шапка");
/* 1361 */     SETTING_TRANSLATIONS.put("Extrude hat and hair layer", "Делать слой шапки и волос объёмным");
/* 1362 */     SETTING_TRANSLATIONS.put("3D Jacket", "3D-куртка");
/* 1363 */     SETTING_TRANSLATIONS.put("Extrude jacket and torso layer", "Делать слой куртки и туловища объёмным");
/* 1364 */     SETTING_TRANSLATIONS.put("3D Sleeves", "3D-рукава");
/* 1365 */     SETTING_TRANSLATIONS.put("Extrude sleeve layers", "Делать слои рукавов объёмными");
/* 1366 */     SETTING_TRANSLATIONS.put("3D Pants", "3D-штаны");
/* 1367 */     SETTING_TRANSLATIONS.put("Extrude pants layers", "Делать слои штанов объёмными");
/* 1368 */     SETTING_TRANSLATIONS.put("Clean-room 3D skin layer voxelization with optimized neighbor face culling", "Независимая 3D-вокселизация слоя скина с оптимизированным отсечением соседних граней");
/*      */     
/* 1370 */     SETTING_TRANSLATIONS.put("Kill sound effect", "Звуковой эффект при убийстве");
/* 1371 */     SETTING_TRANSLATIONS.put("Sound volume", "Громкость звука");
/* 1372 */     SETTING_TRANSLATIONS.put("Sound pitch", "Высота звука");
/* 1373 */     SETTING_TRANSLATIONS.put("Players Only", "Только игроки");
/* 1374 */     SETTING_TRANSLATIONS.put("Only play when eliminating players", "Воспроизводить звук только при убийстве игроков");
/* 1375 */     SETTING_TRANSLATIONS.put("Plays crisp open-source and retro sound effects on eliminating opponents", "Проигрывает чёткие звуковые эффекты с открытым исходным кодом и ретро-звуки при убийстве противников");
/* 1376 */     SETTING_TRANSLATIONS.put("Laser", "Лазер");
/* 1377 */     SETTING_TRANSLATIONS.put("Ding", "Динь");
/* 1378 */     SETTING_TRANSLATIONS.put("Destroy", "Разрушение");
/* 1379 */     SETTING_TRANSLATIONS.put("Hit", "Удар");
/* 1380 */     SETTING_TRANSLATIONS.put("Pop", "Хлопок");
/* 1381 */     SETTING_TRANSLATIONS.put("Crunch", "Хруст");
/* 1382 */     SETTING_TRANSLATIONS.put("Pling", "Звон");
/* 1383 */     SETTING_TRANSLATIONS.put("Bell", "Колокол");
/* 1384 */     SETTING_TRANSLATIONS.put("Orb XP", "Сфера опыта");
/* 1385 */     SETTING_TRANSLATIONS.put("Level Up", "Повышение уровня");
/* 1386 */     SETTING_TRANSLATIONS.put("Anvil", "Наковальня");
/*      */     
/* 1388 */     SETTING_TRANSLATIONS.put("Auto Sprint", "Авто-спринт");
/* 1389 */     SETTING_TRANSLATIONS.put("Auto Eat", "Авто-еда");
/* 1390 */     SETTING_TRANSLATIONS.put("Auto Respawn", "Авто-возрождение");
/* 1391 */     SETTING_TRANSLATIONS.put("Auto Swap", "Авто-смена");
/* 1392 */     SETTING_TRANSLATIONS.put("Better F3", "Улучшенный F3");
/* 1393 */     SETTING_TRANSLATIONS.put("Block Overlay", "Обводка блока");
/* 1394 */     SETTING_TRANSLATIONS.put("Full Bright", "Полная яркость");
/* 1395 */     SETTING_TRANSLATIONS.put("World Animator", "Анимация мира");
/* 1396 */     SETTING_TRANSLATIONS.put("Chat Helper", "Помощник чата");
/* 1397 */     SETTING_TRANSLATIONS.put("Color Correction", "Цветокоррекция");
/* 1398 */     SETTING_TRANSLATIONS.put("Custom Fog", "Своя дальность прорисовки");
/* 1399 */     SETTING_TRANSLATIONS.put("Death Coords", "Координаты смерти");
/* 1400 */     SETTING_TRANSLATIONS.put("Health Indicator", "Индикатор здоровья");
/* 1401 */     SETTING_TRANSLATIONS.put("Item Physics", "Физика предметов");
/* 1402 */     SETTING_TRANSLATIONS.put("Motion Blur", "Размытие движения");
/* 1403 */     SETTING_TRANSLATIONS.put("Old Animations", "Старые анимации");
/* 1404 */     SETTING_TRANSLATIONS.put("Render Tweaks", "Твики рендера");
/* 1405 */     SETTING_TRANSLATIONS.put("Shulker Preview", "Превью шалкера");
/* 1406 */     SETTING_TRANSLATIONS.put("Sky Customizer", "Настройка неба");
/* 1407 */     SETTING_TRANSLATIONS.put("Smooth Camera", "Плавная камера");
/* 1408 */     SETTING_TRANSLATIONS.put("Streamer Mode", "Режим стримера");
/* 1409 */     SETTING_TRANSLATIONS.put("Time Changer", "Смена времени");
/* 1410 */     SETTING_TRANSLATIONS.put("Waypoints", "Точки маршрута");
/* 1411 */     SETTING_TRANSLATIONS.put("Weather Changer", "Смена погоды");
/* 1412 */     SETTING_TRANSLATIONS.put("Zoom", "Зум");
/*      */   }
/*      */   
/* 1415 */   private static void en(String key, String value) { EN_TABLE.put(key, value); } private static void ru(String key, String value) {
/* 1416 */     RU_TABLE.put(key, value);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\bas\\util\Lang.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */