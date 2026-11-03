> ### Важно
>
> `patch_jar.bat` **самодостаточный**: весь PowerShell-код и шейдер запакованы внутрь
> него в base64. Рядом с ним **не нужен** ни `patch_jar.ps1`, ни Python — достаточно
> одного файла.
>
> `patch_jar.ps1` и `patch_jar.py` лежат рядом просто как альтернатива, если хочешь
> запустить патч ручной командой.

# Патчер: настоящий джарник клиента с фиксом

Скомпилировать PhazeClient здесь нечем (см. ниже), зато готовый `PhazeClient-1.2.jar`
можно пропатчить за секунду: джар — это zip, и фикс требует заменить в нём ровно один файл
`assets/phaze/shaders/core/msdf_font.fsh`. Всё остальное (классы, миксины, атласы) копируется
побайтово, клиент остаётся тем же самым, только с исправленным шейдером.

## Windows — в один клик

1. Положить `patch_jar.bat`, `patch_jar.ps1` и `PhazeClient-1.2.jar` в одну папку.
2. Перетащить `PhazeClient-1.2.jar` на `patch_jar.bat`.
3. Рядом появится `PhazeClient-1.2-msdf-fix.jar` — его в `.minecraft/mods`
   (старый джар оттуда удалить или переименовать, иначе будет два мода с одним id).

Python не нужен — работает через встроенный PowerShell.

## Linux / macOS / Windows с Python

```bash
python3 patch_jar.py PhazeClient-1.2.jar
# или без аргумента — возьмёт единственный .jar рядом со скриптом
```

## Что проверяет скрипт

- внутри есть `fabric.mod.json` (иначе предупредит, что это может быть не мод);
- запись `assets/phaze/shaders/core/msdf_font.fsh` существовала — если нет, скажет
  `added (entry was missing!)`.

Вывод содержит список: исходник, результат, размер шейдера, что именно сделано.

## Проверка результата

```bash
unzip -p PhazeClient-1.2-msdf-fix.jar assets/phaze/shaders/core/msdf_font.fsh
```

Должно быть:

```glsl
vec2 unitRange     = vec2(Range) / vec2(textureSize(Sampler0, 0));
vec2 screenTexSize = vec2(1.0) / max(fwidth(TexCoord), vec2(1.0e-6));
float pixels = max(0.5 * dot(unitRange, screenTexSize), 1.0);
```

и **не** должно быть `inversesqrt(h.x * h.x + h.y * h.y)`.

## Почему нельзя просто собрать мод

- в репозитории декомпилированные исходники (JD-Core) без `build.gradle` / `gradlew`;
- JDK и Gradle в сборочной среде отсутствуют;
- `maven.fabricmc.net`, `repo1.maven.org`, `services.gradle.org`, `libraries.minecraft.net`
  недоступны — то есть Minecraft 1.21.4, Fabric Loom, Fabric API и зависимости мода
  (Sodium / Iris / Exordium) скачать неоткуда.

Полную сборку надо делать на своей машине с установленным JDK 21 и доступом в интернет:

```bash
./gradlew build          # в нормальном проекте с build.gradle от Fabric Loom
ls build/libs/*.jar
```

Альтернатива без сборки — `release/PhazeClient-msdf-fix-1.21.4.jar`: это ресурспак
(1.5 КБ, только шейдер), его достаточно, чтобы **проверить** фикс в игре. Но модом он
не является и клиента в себе не несёт.
