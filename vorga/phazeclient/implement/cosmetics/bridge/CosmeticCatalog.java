/*     */ package vorga.phazeclient.implement.cosmetics.bridge;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class CosmeticCatalog {
/*   6 */   private static final List<CosmeticEntry> ALL = new ArrayList<>();
/*   7 */   private static final Map<Integer, CosmeticEntry> BY_ID = new HashMap<>();
/*   8 */   private static final Map<CosmeticCategory, List<CosmeticEntry>> BY_CATEGORY = new EnumMap<>(CosmeticCategory.class);
/*     */   
/*     */   static {
/*  11 */     add(new CosmeticEntry(25, CosmeticCategory.WINGS, "Крылья - Aurum", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/25/model.json", "pulsecosmetics:cosmetics/wings/25/texture.png", "pulsecosmetics:cosmetics/wings/25/preview.png", 64, 64, 1, 64, 64, 1, 149));
/*  12 */     add(new CosmeticEntry(61, CosmeticCategory.WINGS, "Крылья - Angel", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/61/model.json", "pulsecosmetics:cosmetics/wings/61/texture.png", "pulsecosmetics:cosmetics/wings/61/preview.png", 516, 774, 1, 516, 774, 1, 219));
/*  13 */     add(new CosmeticEntry(62, CosmeticCategory.WINGS, "Крылья - Dragon", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/62/model.json", "pulsecosmetics:cosmetics/wings/62/texture.png", "pulsecosmetics:cosmetics/wings/62/preview.png", 64, 64, 1, 64, 64, 1, 199));
/*  14 */     add(new CosmeticEntry(63, CosmeticCategory.WINGS, "Крылья - Diamond", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/63/model.json", "pulsecosmetics:cosmetics/wings/63/texture.png", "pulsecosmetics:cosmetics/wings/63/preview.png", 128, 128, 1, 128, 128, 1, 149));
/*  15 */     add(new CosmeticEntry(64, CosmeticCategory.WINGS, "Крылья - Spider", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/64/model.json", "pulsecosmetics:cosmetics/wings/64/texture.png", "pulsecosmetics:cosmetics/wings/64/preview.png", 256, 256, 1, 256, 256, 1, 249));
/*  16 */     add(new CosmeticEntry(65, CosmeticCategory.WINGS, "Крылья - Easter", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/65/model.json", "pulsecosmetics:cosmetics/wings/65/texture.png", "pulsecosmetics:cosmetics/wings/65/preview.png", 128, 128, 1, 128, 128, 1, 399));
/*  17 */     add(new CosmeticEntry(82, CosmeticCategory.WINGS, "Крылья - Fluger", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/82/model.json", "pulsecosmetics:cosmetics/wings/82/texture.png", "pulsecosmetics:cosmetics/wings/82/preview.png", 16, 16, 1, 16, 16, 1, 349));
/*  18 */     add(new CosmeticEntry(86, CosmeticCategory.WINGS, "Трезубец - Броя", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/86/model.json", "pulsecosmetics:cosmetics/wings/86/texture.png", "pulsecosmetics:cosmetics/wings/86/preview.png", 32, 32, 1, 32, 32, 1, 349));
/*  19 */     add(new CosmeticEntry(91, CosmeticCategory.WINGS, "Крылья - Аквыч", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/91/model.json", "pulsecosmetics:cosmetics/wings/91/texture.png", "pulsecosmetics:cosmetics/wings/91/preview.png", 64, 640, 1, 64, 640, 1, 349));
/*  20 */     add(new CosmeticEntry(99, CosmeticCategory.WINGS, "Крылья - Dark", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/99/model.json", "pulsecosmetics:cosmetics/wings/99/texture.png", "pulsecosmetics:cosmetics/wings/99/preview.png", 128, 128, 1, 128, 128, 1, 249));
/*  21 */     add(new CosmeticEntry(123, CosmeticCategory.WINGS, "Крылья - Phaze", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/123/model.json", "pulsecosmetics:cosmetics/wings/123/texture.png", "pulsecosmetics:cosmetics/wings/123/preview.png", 64, 64, 1, 64, 64, 1, 149));
/*  22 */     add(new CosmeticEntry(124, CosmeticCategory.WINGS, "Крылья - Ghoul", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/124/model.json", "pulsecosmetics:cosmetics/wings/124/texture.png", "pulsecosmetics:cosmetics/wings/124/preview.png", 64, 64, 1, 64, 64, 1, 249));
/*  23 */     add(new CosmeticEntry(136, CosmeticCategory.WINGS, "Крылья - Ягуар", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/wings/136/model.json", "pulsecosmetics:cosmetics/wings/136/texture.png", "pulsecosmetics:cosmetics/wings/136/preview.png", 32, 32, 1, 32, 32, 1, 349));
/*  24 */     add(new CosmeticEntry(48, CosmeticCategory.HAT, "Шапка - Armour", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/48/model.json", "pulsecosmetics:cosmetics/hat/48/texture.png", "pulsecosmetics:cosmetics/hat/48/preview.png", 64, 1920, 1, 64, 1920, 1, 29));
/*  25 */     add(new CosmeticEntry(49, CosmeticCategory.HAT, "Шапка - Capybara", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/49/model.json", "pulsecosmetics:cosmetics/hat/49/texture.png", "pulsecosmetics:cosmetics/hat/49/preview.png", 64, 960, 1, 64, 960, 1, 29));
/*  26 */     add(new CosmeticEntry(50, CosmeticCategory.HAT, "Шапка -  Chicken", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/50/model.json", "pulsecosmetics:cosmetics/hat/50/texture.png", "pulsecosmetics:cosmetics/hat/50/preview.png", 64, 64, 1, 64, 64, 1, 29));
/*  27 */     add(new CosmeticEntry(53, CosmeticCategory.HAT, "Шапка - Pepe", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/53/model.json", "pulsecosmetics:cosmetics/hat/53/texture.png", "pulsecosmetics:cosmetics/hat/53/preview.png", 64, 832, 1, 64, 832, 1, 79));
/*  28 */     add(new CosmeticEntry(57, CosmeticCategory.HAT, "Шапка - Sheep", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/57/model.json", "pulsecosmetics:cosmetics/hat/57/texture.png", "pulsecosmetics:cosmetics/hat/57/preview.png", 64, 64, 1, 64, 64, 1, 29));
/*  29 */     add(new CosmeticEntry(80, CosmeticCategory.HAT, "Шапка - Fluger", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/80/model.json", "pulsecosmetics:cosmetics/hat/80/texture.png", "pulsecosmetics:cosmetics/hat/80/preview.png", 64, 64, 1, 64, 64, 1, 249));
/*  30 */     add(new CosmeticEntry(85, CosmeticCategory.HAT, "Маска - Bro9I", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/85/model.json", "pulsecosmetics:cosmetics/hat/85/texture.png", "pulsecosmetics:cosmetics/hat/85/preview.png", 16, 16, 1, 16, 16, 1, 249));
/*  31 */     add(new CosmeticEntry(88, CosmeticCategory.HAT, "Шапка - Аквыч", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/88/model.json", "pulsecosmetics:cosmetics/hat/88/texture.png", "pulsecosmetics:cosmetics/hat/88/preview.png", 64, 64, 1, 64, 64, 1, 249));
/*  32 */     add(new CosmeticEntry(92, CosmeticCategory.HAT, "Шапка - Bear", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/92/model.json", "pulsecosmetics:cosmetics/hat/92/texture.png", "pulsecosmetics:cosmetics/hat/92/preview.png", 96, 96, 1, 96, 96, 1, 149));
/*  33 */     add(new CosmeticEntry(96, CosmeticCategory.HAT, "Шапка - ПеПе", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/96/model.json", "pulsecosmetics:cosmetics/hat/96/texture.png", "pulsecosmetics:cosmetics/hat/96/preview.png", 96, 96, 1, 96, 96, 1, 149));
/*  34 */     add(new CosmeticEntry(97, CosmeticCategory.HAT, "Шапка - Happy", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/97/model.json", "pulsecosmetics:cosmetics/hat/97/texture.png", "pulsecosmetics:cosmetics/hat/97/preview.png", 64, 64, 1, 64, 64, 1, 99));
/*  35 */     add(new CosmeticEntry(98, CosmeticCategory.HAT, "Нимб", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/98/model.json", "pulsecosmetics:cosmetics/hat/98/texture.png", "pulsecosmetics:cosmetics/hat/98/preview.png", 64, 64, 1, 64, 64, 1, 329));
/*  36 */     add(new CosmeticEntry(125, CosmeticCategory.HAT, "Шапка - Phaze", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/hat/125/model.json", "pulsecosmetics:cosmetics/hat/125/texture.png", "pulsecosmetics:cosmetics/hat/125/preview.png", 64, 64, 1, 64, 64, 1, 0));
/*  37 */     add(new CosmeticEntry(36, CosmeticCategory.BODYWEAR, "Рюкзак - Louis Vuitton", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/36/model.json", "pulsecosmetics:cosmetics/bodywear/36/texture.png", "pulsecosmetics:cosmetics/bodywear/36/preview.png", 256, 256, 1, 256, 256, 1, 169));
/*  38 */     add(new CosmeticEntry(37, CosmeticCategory.BODYWEAR, "Рюкзак - Gucci", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/37/model.json", "pulsecosmetics:cosmetics/bodywear/37/texture.png", "pulsecosmetics:cosmetics/bodywear/37/preview.png", 128, 128, 1, 128, 128, 1, 129));
/*  39 */     add(new CosmeticEntry(38, CosmeticCategory.BODYWEAR, "Рюкзак - Adidas", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/38/model.json", "pulsecosmetics:cosmetics/bodywear/38/texture.png", "pulsecosmetics:cosmetics/bodywear/38/preview.png", 128, 128, 1, 128, 128, 1, 59));
/*  40 */     add(new CosmeticEntry(39, CosmeticCategory.BODYWEAR, "Рюкзак - Nike", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/39/model.json", "pulsecosmetics:cosmetics/bodywear/39/texture.png", "pulsecosmetics:cosmetics/bodywear/39/preview.png", 128, 128, 1, 128, 128, 1, 99));
/*  41 */     add(new CosmeticEntry(40, CosmeticCategory.BODYWEAR, "Рюкзак - Supreme", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/40/model.json", "pulsecosmetics:cosmetics/bodywear/40/texture.png", "pulsecosmetics:cosmetics/bodywear/40/preview.png", 128, 128, 1, 128, 128, 1, 119));
/*  42 */     add(new CosmeticEntry(41, CosmeticCategory.BODYWEAR, "Рюкзак - Vlone", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/41/model.json", "pulsecosmetics:cosmetics/bodywear/41/texture.png", "pulsecosmetics:cosmetics/bodywear/41/preview.png", 128, 128, 1, 128, 128, 1, 149));
/*  43 */     add(new CosmeticEntry(42, CosmeticCategory.BODYWEAR, "Рюкзак - Bape", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/42/model.json", "pulsecosmetics:cosmetics/bodywear/42/texture.png", "pulsecosmetics:cosmetics/bodywear/42/preview.png", 128, 128, 1, 128, 128, 1, 129));
/*  44 */     add(new CosmeticEntry(43, CosmeticCategory.BODYWEAR, "Рюкзак - Balenciaga", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/43/model.json", "pulsecosmetics:cosmetics/bodywear/43/texture.png", "pulsecosmetics:cosmetics/bodywear/43/preview.png", 256, 256, 1, 256, 256, 1, 219));
/*  45 */     add(new CosmeticEntry(44, CosmeticCategory.BODYWEAR, "Рюкзак -  Chrome Hearts", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/44/model.json", "pulsecosmetics:cosmetics/bodywear/44/texture.png", "pulsecosmetics:cosmetics/bodywear/44/preview.png", 256, 256, 1, 256, 256, 1, 149));
/*  46 */     add(new CosmeticEntry(95, CosmeticCategory.BODYWEAR, "Рюкзак -  PePe", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/95/model.json", "pulsecosmetics:cosmetics/bodywear/95/texture.png", "pulsecosmetics:cosmetics/bodywear/95/preview.png", 80, 80, 1, 80, 80, 1, 99));
/*  47 */     add(new CosmeticEntry(137, CosmeticCategory.BODYWEAR, "Катана - ila_studio", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/bodywear/137/model.json", "pulsecosmetics:cosmetics/bodywear/137/texture.png", "pulsecosmetics:cosmetics/bodywear/137/preview.png", 32, 32, 1, 32, 32, 1, 299));
/*  48 */     add(new CosmeticEntry(45, CosmeticCategory.PET, "Питомец - Angel", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/45/model.json", "pulsecosmetics:cosmetics/pet/45/texture.png", "pulsecosmetics:cosmetics/pet/45/preview.png", 128, 128, 1, 128, 128, 1, 99));
/*  49 */     add(new CosmeticEntry(46, CosmeticCategory.PET, "Питомец - Demon", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/46/model.json", "pulsecosmetics:cosmetics/pet/46/texture.png", "pulsecosmetics:cosmetics/pet/46/preview.png", 64, 64, 1, 64, 64, 1, 99));
/*  50 */     add(new CosmeticEntry(47, CosmeticCategory.PET, "Питомец - Buddy", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/47/model.json", "pulsecosmetics:cosmetics/pet/47/texture.png", "pulsecosmetics:cosmetics/pet/47/preview.png", 32, 32, 1, 32, 32, 1, 69));
/*  51 */     add(new CosmeticEntry(81, CosmeticCategory.PET, "Питомец - Fluger Door", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/81/model.json", "pulsecosmetics:cosmetics/pet/81/texture.png", "pulsecosmetics:cosmetics/pet/81/preview.png", 32, 32, 1, 32, 32, 1, 299));
/*  52 */     add(new CosmeticEntry(84, CosmeticCategory.PET, "Питомец - Diamond", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/84/model.json", "pulsecosmetics:cosmetics/pet/84/texture.png", "pulsecosmetics:cosmetics/pet/84/preview.png", 32, 288, 1, 32, 288, 1, 299));
/*  53 */     add(new CosmeticEntry(90, CosmeticCategory.PET, "Питомец - Аквыч", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/90/model.json", "pulsecosmetics:cosmetics/pet/90/texture.png", "pulsecosmetics:cosmetics/pet/90/preview.png", 64, 64, 1, 64, 64, 1, 299));
/*  54 */     add(new CosmeticEntry(93, CosmeticCategory.PET, "Питомец - Bee", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/93/model.json", "pulsecosmetics:cosmetics/pet/93/texture.png", "pulsecosmetics:cosmetics/pet/93/preview.png", 64, 64, 1, 64, 64, 1, 199));
/*  55 */     add(new CosmeticEntry(126, CosmeticCategory.PET, "Питомец - 1 Год", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/126/model.json", "pulsecosmetics:cosmetics/pet/126/texture.png", "pulsecosmetics:cosmetics/pet/126/preview.png", 64, 64, 1, 64, 64, 1, 99));
/*  56 */     add(new CosmeticEntry(132, CosmeticCategory.PET, "Питомец - Ягуар", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/132/model.json", "pulsecosmetics:cosmetics/pet/132/texture.png", "pulsecosmetics:cosmetics/pet/132/preview.png", 64, 64, 1, 64, 64, 1, 299));
/*  57 */     add(new CosmeticEntry(133, CosmeticCategory.PET, "Питомец - Cergifff", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/133/model.json", "pulsecosmetics:cosmetics/pet/133/texture.png", "pulsecosmetics:cosmetics/pet/133/preview.png", 64, 64, 1, 64, 64, 1, 299));
/*  58 */     add(new CosmeticEntry(134, CosmeticCategory.PET, "Питомец - Ila_Studio", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/134/model.json", "pulsecosmetics:cosmetics/pet/134/texture.png", "pulsecosmetics:cosmetics/pet/134/preview.png", 64, 64, 1, 64, 64, 1, 299));
/*  59 */     add(new CosmeticEntry(135, CosmeticCategory.PET, "Питомец - Creeper", CosmeticKind.GEOMETRY, "assets/pulsecosmetics/cosmetics/pet/135/model.json", "pulsecosmetics:cosmetics/pet/135/texture.png", "pulsecosmetics:cosmetics/pet/135/preview.png", 64, 64, 1, 64, 64, 1, 299));
/*  60 */     add(new CosmeticEntry(127, CosmeticCategory.GRAFFITI, "Граффити Phaze #127", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/127/texture.png", null, 1327, 1186, 1, 1327, 1186, 1, 0));
/*  61 */     add(new CosmeticEntry(128, CosmeticCategory.GRAFFITI, "Граффити Phaze #128", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/128/texture.png", null, 319, 360, 1, 319, 360, 1, 0));
/*  62 */     add(new CosmeticEntry(129, CosmeticCategory.GRAFFITI, "Граффити Phaze #129", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/129/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  63 */     add(new CosmeticEntry(138, CosmeticCategory.GRAFFITI, "Граффити Phaze #138", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/138/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  64 */     add(new CosmeticEntry(139, CosmeticCategory.GRAFFITI, "Граффити Phaze #139", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/139/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  65 */     add(new CosmeticEntry(140, CosmeticCategory.GRAFFITI, "Граффити Phaze #140", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/140/texture.png", null, 1024, 1024, 1, 1024, 1024, 1, 0));
/*  66 */     add(new CosmeticEntry(141, CosmeticCategory.GRAFFITI, "Граффити Phaze #141", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/141/texture.png", null, 256, 192, 1, 256, 192, 1, 0));
/*  67 */     add(new CosmeticEntry(142, CosmeticCategory.GRAFFITI, "Граффити Phaze #142", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/142/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  68 */     add(new CosmeticEntry(143, CosmeticCategory.GRAFFITI, "Граффити Phaze #143", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/143/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  69 */     add(new CosmeticEntry(144, CosmeticCategory.GRAFFITI, "Граффити Phaze #144", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/144/texture.png", null, 512, 324, 1, 512, 324, 1, 0));
/*  70 */     add(new CosmeticEntry(145, CosmeticCategory.GRAFFITI, "Граффити Phaze #145", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/145/texture.png", null, 512, 458, 1, 512, 458, 1, 0));
/*  71 */     add(new CosmeticEntry(146, CosmeticCategory.GRAFFITI, "Граффити Phaze #146", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/146/texture.png", null, 128, 128, 1, 128, 128, 1, 0));
/*  72 */     add(new CosmeticEntry(148, CosmeticCategory.GRAFFITI, "Граффити Phaze #148", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/148/texture.png", null, 224, 224, 1, 224, 224, 1, 0));
/*  73 */     add(new CosmeticEntry(149, CosmeticCategory.GRAFFITI, "Граффити Phaze #149", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/149/texture.png", null, 1536, 1024, 1, 1536, 1024, 1, 0));
/*  74 */     add(new CosmeticEntry(150, CosmeticCategory.GRAFFITI, "Граффити Phaze #150", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/150/texture.png", null, 256, 256, 1, 256, 256, 1, 0));
/*  75 */     add(new CosmeticEntry(151, CosmeticCategory.GRAFFITI, "Граффити Phaze #151", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/151/texture.png", null, 256, 256, 1, 256, 256, 1, 0));
/*  76 */     add(new CosmeticEntry(152, CosmeticCategory.GRAFFITI, "Граффити Phaze #152", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/152/texture.png", null, 1024, 1024, 1, 1024, 1024, 1, 0));
/*  77 */     add(new CosmeticEntry(153, CosmeticCategory.GRAFFITI, "Граффити Phaze #153", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/153/texture.png", null, 1024, 1024, 1, 1024, 1024, 1, 0));
/*  78 */     add(new CosmeticEntry(154, CosmeticCategory.GRAFFITI, "Граффити Phaze #154", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/154/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  79 */     add(new CosmeticEntry(155, CosmeticCategory.GRAFFITI, "Граффити Phaze #155", CosmeticKind.GRAFFITI, null, "pulsecosmetics:cosmetics/graffiti/155/texture.png", null, 512, 512, 1, 512, 512, 1, 0));
/*  80 */     for (CosmeticCategory category : CosmeticCategory.values()) {
/*  81 */       ((List)BY_CATEGORY.computeIfAbsent(category, k -> new ArrayList())).sort(Comparator.comparingInt(CosmeticEntry::id));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void add(CosmeticEntry entry) {
/*  86 */     ALL.add(entry);
/*  87 */     BY_ID.put(Integer.valueOf(entry.id()), entry);
/*  88 */     ((List<CosmeticEntry>)BY_CATEGORY.computeIfAbsent(entry.category(), k -> new ArrayList())).add(entry);
/*     */   }
/*     */   
/*     */   public static List<CosmeticEntry> all() {
/*  92 */     return Collections.unmodifiableList(ALL);
/*     */   }
/*     */   
/*     */   public static List<CosmeticEntry> byCategory(CosmeticCategory category) {
/*  96 */     return Collections.unmodifiableList(BY_CATEGORY.getOrDefault(category, List.of()));
/*     */   }
/*     */   
/*     */   public static CosmeticEntry byId(int id) {
/* 100 */     return BY_ID.get(Integer.valueOf(id));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\cosmetics\bridge\CosmeticCatalog.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */