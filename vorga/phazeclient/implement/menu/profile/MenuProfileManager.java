/*     */ package vorga.phazeclient.implement.menu.profile;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.core.Main;
/*     */ import vorga.phazeclient.implement.features.modules.hud.ArmorHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*     */ import vorga.phazeclient.implement.features.modules.hud.RectHudModule;
/*     */ 
/*     */ public final class MenuProfileManager implements QuickImports {
/*     */   private static final String DEFAULT_PROFILE_NAME = "DEFAULT";
/*  31 */   private static final MenuProfileManager INSTANCE = new MenuProfileManager();
/*  32 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   
/*  34 */   private final List<String> profiles = new ArrayList<>();
/*     */   
/*  36 */   private String activeProfile = "DEFAULT"; public String getActiveProfile() { return this.activeProfile; }
/*     */ 
/*     */   
/*     */   private boolean initialized = false;
/*     */   private Path activeProfileFile;
/*     */   
/*     */   private MenuProfileManager() {
/*  43 */     class_310 client = class_310.method_1551();
/*  44 */     Path root = (client != null) ? client.field_1697.toPath() : Path.of(".", new String[0]);
/*  45 */     this.activeProfileFile = root.resolve("Phaze").resolve("files").resolve("current_config");
/*     */   }
/*     */   
/*     */   public static MenuProfileManager getInstance() {
/*  49 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public synchronized void ensureInitialized() {
/*  53 */     if (this.initialized) {
/*     */       return;
/*     */     }
/*     */     
/*  57 */     loadProfilesFromDisk();
/*  58 */     loadActiveProfile();
/*     */     
/*  60 */     System.out.println("[MenuProfileManager] Loaded profiles: " + String.valueOf(this.profiles));
/*  61 */     System.out.println("[MenuProfileManager] Active profile before check: " + this.activeProfile);
/*     */     
/*  63 */     if (this.profiles.isEmpty()) {
/*  64 */       this.profiles.add("DEFAULT");
/*  65 */       this.activeProfile = "DEFAULT";
/*  66 */       saveProfile("DEFAULT");
/*  67 */       System.out.println("[MenuProfileManager] No profiles found, created DEFAULT");
/*     */     } else {
/*  69 */       sortProfiles();
/*  70 */       this.activeProfile = findExistingProfile(this.activeProfile);
/*  71 */       System.out.println("[MenuProfileManager] Active profile after findExisting: " + this.activeProfile);
/*  72 */       if (this.activeProfile == null) {
/*  73 */         this.activeProfile = this.profiles.getFirst();
/*  74 */         System.out.println("[MenuProfileManager] Active profile not found, using first: " + this.activeProfile);
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     System.out.println("[MenuProfileManager] Loading profile settings: " + this.activeProfile);
/*  79 */     loadProfile(this.activeProfile);
/*  80 */     Main.getInstance().getConfigManager().setCurrentConfigName(this.activeProfile);
/*     */     
/*  82 */     this.initialized = true;
/*     */   }
/*     */   
/*     */   private void loadActiveProfile() {
/*     */     try {
/*  87 */       if (Files.exists(this.activeProfileFile, new java.nio.file.LinkOption[0])) {
/*  88 */         String content = Files.readString(this.activeProfileFile, StandardCharsets.UTF_8);
/*  89 */         System.out.println("[MenuProfileManager] Loaded active profile from file: " + content);
/*  90 */         if (content != null && !content.trim().isEmpty()) {
/*  91 */           this.activeProfile = sanitizeProfileName(content.trim());
/*  92 */           System.out.println("[MenuProfileManager] Sanitized active profile: " + this.activeProfile);
/*     */         } 
/*     */       } else {
/*  95 */         System.out.println("[MenuProfileManager] Active profile file does not exist: " + String.valueOf(this.activeProfileFile));
/*     */       } 
/*  97 */     } catch (IOException e) {
/*  98 */       System.out.println("[MenuProfileManager] Error loading active profile: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveActiveProfile() {
/*     */     try {
/* 104 */       Files.createDirectories(this.activeProfileFile.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 105 */       Files.writeString(this.activeProfileFile, this.activeProfile, StandardCharsets.UTF_8, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 113 */     catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized List<String> getProfiles() {
/* 118 */     ensureInitialized();
/* 119 */     return List.copyOf(this.profiles);
/*     */   }
/*     */   
/*     */   public synchronized String createNewProfile() {
/* 123 */     ensureInitialized();
/* 124 */     String name = nextNewProfileName();
/* 125 */     saveProfile(name);
/* 126 */     this.activeProfile = name;
/* 127 */     saveActiveProfile();
/* 128 */     return name;
/*     */   }
/*     */   
/*     */   public synchronized boolean selectProfile(String profileName) {
/* 132 */     ensureInitialized();
/* 133 */     String existing = findExistingProfile(profileName);
/* 134 */     if (existing == null) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     saveCurrentProfile();
/* 139 */     if (loadProfile(existing)) {
/* 140 */       this.activeProfile = existing;
/* 141 */       saveActiveProfile();
/* 142 */       Main.getInstance().getConfigManager().setCurrentConfigName(this.activeProfile);
/* 143 */       return true;
/*     */     } 
/* 145 */     return false;
/*     */   }
/*     */   
/*     */   public synchronized boolean renameProfile(String oldName, String newName) {
/* 149 */     ensureInitialized();
/*     */     
/* 151 */     String existingOld = findExistingProfile(oldName);
/* 152 */     if (existingOld == null) {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     String sanitizedNew = sanitizeProfileName(newName);
/* 157 */     if (sanitizedNew.isBlank()) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     String existingTarget = findExistingProfile(sanitizedNew);
/* 162 */     if (existingTarget != null && !existingTarget.equalsIgnoreCase(existingOld)) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     Path oldPath = profilePath(existingOld);
/* 167 */     Path newPath = profilePath(sanitizedNew);
/* 168 */     JsonObject root = readProfile(oldPath);
/* 169 */     if (root == null) {
/* 170 */       root = serializeCurrentState(existingOld);
/*     */     }
/*     */     
/* 173 */     root.addProperty("profileName", sanitizedNew);
/* 174 */     writeProfile(newPath, root);
/*     */     
/* 176 */     if (!oldPath.equals(newPath)) {
/*     */       try {
/* 178 */         Files.deleteIfExists(oldPath);
/* 179 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 183 */     this.profiles.removeIf(profile -> profile.equalsIgnoreCase(existingOld));
/* 184 */     this.profiles.add(sanitizedNew);
/* 185 */     sortProfiles();
/*     */     
/* 187 */     if (this.activeProfile != null && this.activeProfile.equalsIgnoreCase(existingOld)) {
/* 188 */       this.activeProfile = sanitizedNew;
/* 189 */       saveActiveProfile();
/*     */     } 
/* 191 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized boolean deleteProfile(String profileName) {
/* 195 */     ensureInitialized();
/*     */     
/* 197 */     String existing = findExistingProfile(profileName);
/* 198 */     if (existing == null) {
/* 199 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 203 */     if (existing.equalsIgnoreCase("DEFAULT")) {
/* 204 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 208 */     if (this.activeProfile != null && this.activeProfile.equalsIgnoreCase(existing)) {
/* 209 */       return false;
/*     */     }
/*     */     
/* 212 */     Path path = profilePath(existing);
/*     */     try {
/* 214 */       Files.deleteIfExists(path);
/* 215 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 218 */     this.profiles.removeIf(profile -> profile.equalsIgnoreCase(existing));
/* 219 */     sortProfiles();
/*     */     
/* 221 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void saveCurrentProfile() {
/* 225 */     ensureInitialized();
/* 226 */     String target = (this.activeProfile == null) ? "DEFAULT" : this.activeProfile;
/* 227 */     saveProfile(target);
/*     */   }
/*     */   
/*     */   public synchronized String getDefaultProfileName() {
/* 231 */     return "DEFAULT";
/*     */   }
/*     */   
/*     */   private void loadProfilesFromDisk() {
/* 235 */     this.profiles.clear();
/*     */     
/* 237 */     try { Files.createDirectories(profilesDirectory(), (FileAttribute<?>[])new FileAttribute[0]);
/* 238 */       Stream<Path> stream = Files.list(profilesDirectory()); 
/* 239 */       try { stream.filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".profile"))
/* 240 */           .sorted(Comparator.comparing(path -> path.getFileName().toString().toLowerCase(Locale.ROOT)))
/* 241 */           .forEach(path -> {
/*     */               JsonObject root = readProfile(path);
/* 243 */               String profileName = (root != null && root.has("profileName")) ? sanitizeProfileName(root.get("profileName").getAsString()) : stripExtension(path.getFileName().toString());
/*     */ 
/*     */               
/*     */               if (!profileName.isBlank() && findExistingProfile(profileName) == null) {
/*     */                 this.profiles.add(profileName);
/*     */               }
/*     */             });
/*     */         
/* 251 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/* 252 */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void saveProfile(String profileName) {
/* 257 */     String normalizedName = sanitizeProfileName(profileName);
/* 258 */     JsonObject root = serializeCurrentState(normalizedName);
/* 259 */     writeProfile(profilePath(normalizedName), root);
/*     */     
/* 261 */     if (findExistingProfile(normalizedName) == null) {
/* 262 */       this.profiles.add(normalizedName);
/* 263 */       sortProfiles();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean loadProfile(String profileName) {
/* 268 */     JsonObject root = readProfile(profilePath(profileName));
/* 269 */     if (root == null) {
/* 270 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 275 */     JsonObject modulesObject = (root.has("modules") && root.get("modules").isJsonObject()) ? root.getAsJsonObject("modules") : root;
/*     */     
/* 277 */     for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/* 278 */       boolean hasModuleData = (modulesObject.has(module.getIdentifier()) && modulesObject.get(module.getIdentifier()).isJsonObject());
/* 279 */       JsonObject moduleData = hasModuleData ? modulesObject.getAsJsonObject(module.getIdentifier()) : null;
/* 280 */       if (hasModuleData && module instanceof BossBarHud) {
/* 281 */         moduleData = BossBarHud.migrateLegacyData(moduleData);
/*     */       }
/*     */       
/* 284 */       if (module.isShowEnable()) {
/* 285 */         boolean enabled = (hasModuleData && moduleData.has("enabled") && moduleData.get("enabled").getAsBoolean());
/* 286 */         module.setStateSilent(enabled);
/*     */       } 
/*     */       
/* 289 */       if (!hasModuleData) {
/* 290 */         if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/* 291 */           rectHudModule.resetHudTransform(); continue; }
/* 292 */          if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/* 293 */           armorHud.resetHudTransform(); }
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/* 298 */       if (moduleData.has("bind")) {
/* 299 */         module.setKey(moduleData.get("bind").getAsInt());
/*     */       }
/*     */       
/* 302 */       for (Setting setting : module.settings()) {
/*     */         try {
/* 304 */           loadSetting(setting, moduleData);
/* 305 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/* 309 */       if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 314 */         if (moduleData.has("hud_x_ratio") && moduleData.has("hud_y_ratio")) {
/* 315 */           rectHudModule.setHudPositionRatio(moduleData.get("hud_x_ratio").getAsFloat(), moduleData.get("hud_y_ratio").getAsFloat());
/* 316 */         } else if (moduleData.has("hud_x") && moduleData.has("hud_y")) {
/* 317 */           rectHudModule.setHudPosition(moduleData
/* 318 */               .get("hud_x").getAsFloat(), moduleData
/* 319 */               .get("hud_y").getAsFloat(), 
/* 320 */               moduleData.has("hud_ref_w") ? moduleData.get("hud_ref_w").getAsInt() : -1, 
/* 321 */               moduleData.has("hud_ref_h") ? moduleData.get("hud_ref_h").getAsInt() : -1);
/*     */         } else {
/* 323 */           rectHudModule.resetHudTransform();
/*     */         } 
/* 325 */         if (moduleData.has("hud_scale")) {
/* 326 */           float savedScale = moduleData.get("hud_scale").getAsFloat();
/* 327 */           rectHudModule.setHudScale(savedScale);
/*     */         } 
/* 329 */         if (rectHudModule instanceof BossBarHud) { BossBarHud bossBar = (BossBarHud)rectHudModule; if (moduleData.has("hud_auto_position") && moduleData
/* 330 */             .get("hud_auto_position").getAsBoolean())
/* 331 */             bossBar.resetAutomaticPosition();  }
/*     */          continue; }
/* 333 */        if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 338 */         if (moduleData.has("hud_x_ratio") && moduleData.has("hud_y_ratio")) {
/* 339 */           armorHud.setHudPositionRatio(moduleData.get("hud_x_ratio").getAsFloat(), moduleData.get("hud_y_ratio").getAsFloat());
/* 340 */         } else if (moduleData.has("hud_x") && moduleData.has("hud_y")) {
/* 341 */           armorHud.setHudPosition(moduleData
/* 342 */               .get("hud_x").getAsFloat(), moduleData
/* 343 */               .get("hud_y").getAsFloat(), 
/* 344 */               moduleData.has("hud_ref_w") ? moduleData.get("hud_ref_w").getAsInt() : -1, 
/* 345 */               moduleData.has("hud_ref_h") ? moduleData.get("hud_ref_h").getAsInt() : -1);
/*     */         } else {
/* 347 */           armorHud.resetHudTransform();
/*     */         } 
/* 349 */         if (moduleData.has("hud_scale")) {
/* 350 */           float savedScale = moduleData.get("hud_scale").getAsFloat();
/* 351 */           armorHud.setHudScale(savedScale);
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 356 */     return true;
/*     */   }
/*     */   
/*     */   private JsonObject serializeCurrentState(String profileName) {
/* 360 */     JsonObject root = new JsonObject();
/* 361 */     root.addProperty("profileName", profileName);
/* 362 */     root.addProperty("savedAt", Instant.now().toString());
/*     */     
/* 364 */     JsonObject modulesObject = new JsonObject();
/* 365 */     for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/* 366 */       JsonObject moduleData = new JsonObject();
/* 367 */       moduleData.addProperty("enabled", Boolean.valueOf(module.isState()));
/* 368 */       moduleData.addProperty("bind", Integer.valueOf(module.getKey()));
/*     */       
/* 370 */       for (Setting setting : module.settings()) {
/* 371 */         saveSetting(setting, moduleData);
/*     */       }
/*     */       
/* 374 */       if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/* 375 */         moduleData.addProperty("hud_x_ratio", Float.valueOf(rectHudModule.getHudXRatio()));
/* 376 */         moduleData.addProperty("hud_y_ratio", Float.valueOf(rectHudModule.getHudYRatio()));
/* 377 */         moduleData.addProperty("hud_x", Float.valueOf(rectHudModule.getHudX()));
/* 378 */         moduleData.addProperty("hud_y", Float.valueOf(rectHudModule.getHudY()));
/*     */ 
/*     */ 
/*     */         
/* 382 */         moduleData.addProperty("hud_ref_w", Integer.valueOf(rectHudModule.getHudRefWidth()));
/* 383 */         moduleData.addProperty("hud_ref_h", Integer.valueOf(rectHudModule.getHudRefHeight()));
/* 384 */         moduleData.addProperty("hud_scale", Float.valueOf(rectHudModule.getHudScale()));
/* 385 */         if (rectHudModule instanceof BossBarHud) { BossBarHud bossBar = (BossBarHud)rectHudModule;
/* 386 */           moduleData.addProperty("hud_auto_position", Boolean.valueOf(bossBar.isHudPositionAutomatic())); }
/*     */          }
/* 388 */       else if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/* 389 */         moduleData.addProperty("hud_x_ratio", Float.valueOf(armorHud.getHudXRatio()));
/* 390 */         moduleData.addProperty("hud_y_ratio", Float.valueOf(armorHud.getHudYRatio()));
/* 391 */         moduleData.addProperty("hud_x", Float.valueOf(armorHud.getHudX()));
/* 392 */         moduleData.addProperty("hud_y", Float.valueOf(armorHud.getHudY()));
/*     */ 
/*     */ 
/*     */         
/* 396 */         moduleData.addProperty("hud_ref_w", Integer.valueOf(armorHud.getHudRefWidth()));
/* 397 */         moduleData.addProperty("hud_ref_h", Integer.valueOf(armorHud.getHudRefHeight()));
/* 398 */         moduleData.addProperty("hud_scale", Float.valueOf(armorHud.getHudScale())); }
/*     */ 
/*     */       
/* 401 */       modulesObject.add(module.getIdentifier(), (JsonElement)moduleData);
/*     */     } 
/*     */     
/* 404 */     root.add("modules", (JsonElement)modulesObject);
/* 405 */     return root;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveSetting(Setting setting, JsonObject moduleData) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual isSaveToConfig : ()Z
/*     */     //   4: ifne -> 8
/*     */     //   7: return
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual getNameKey : ()Ljava/lang/String;
/*     */     //   12: astore_3
/*     */     //   13: aload_1
/*     */     //   14: dup
/*     */     //   15: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   18: pop
/*     */     //   19: astore #4
/*     */     //   21: iconst_0
/*     */     //   22: istore #5
/*     */     //   24: aload #4
/*     */     //   26: iload #5
/*     */     //   28: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   33: tableswitch default -> 609, 0 -> 88, 1 -> 111, 2 -> 134, 3 -> 180, 4 -> 203, 5 -> 226, 6 -> 246, 7 -> 272, 8 -> 411, 9 -> 522
/*     */     //   88: aload #4
/*     */     //   90: checkcast vorga/phazeclient/api/feature/module/setting/implement/BooleanSetting
/*     */     //   93: astore #6
/*     */     //   95: aload_2
/*     */     //   96: aload_3
/*     */     //   97: aload #6
/*     */     //   99: invokevirtual isValue : ()Z
/*     */     //   102: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   105: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   108: goto -> 609
/*     */     //   111: aload #4
/*     */     //   113: checkcast vorga/phazeclient/api/feature/module/setting/implement/ValueSetting
/*     */     //   116: astore #7
/*     */     //   118: aload_2
/*     */     //   119: aload_3
/*     */     //   120: aload #7
/*     */     //   122: invokevirtual getValue : ()F
/*     */     //   125: invokestatic valueOf : (F)Ljava/lang/Float;
/*     */     //   128: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   131: goto -> 609
/*     */     //   134: aload #4
/*     */     //   136: checkcast vorga/phazeclient/api/feature/module/setting/implement/TextSetting
/*     */     //   139: astore #8
/*     */     //   141: aload #8
/*     */     //   143: invokevirtual getText : ()Ljava/lang/String;
/*     */     //   146: astore #9
/*     */     //   148: aload #9
/*     */     //   150: ldc_w ' '
/*     */     //   153: ldc_w '%%'
/*     */     //   156: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   159: ldc_w '/'
/*     */     //   162: ldc_w '++'
/*     */     //   165: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   168: astore #9
/*     */     //   170: aload_2
/*     */     //   171: aload_3
/*     */     //   172: aload #9
/*     */     //   174: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   177: goto -> 609
/*     */     //   180: aload #4
/*     */     //   182: checkcast vorga/phazeclient/api/feature/module/setting/implement/BindSetting
/*     */     //   185: astore #9
/*     */     //   187: aload_2
/*     */     //   188: aload_3
/*     */     //   189: aload #9
/*     */     //   191: invokevirtual getKey : ()I
/*     */     //   194: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   197: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   200: goto -> 609
/*     */     //   203: aload #4
/*     */     //   205: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   208: astore #10
/*     */     //   210: aload_2
/*     */     //   211: aload_3
/*     */     //   212: aload #10
/*     */     //   214: invokevirtual getColor : ()I
/*     */     //   217: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   220: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   223: goto -> 609
/*     */     //   226: aload #4
/*     */     //   228: checkcast vorga/phazeclient/api/feature/module/setting/implement/SelectSetting
/*     */     //   231: astore #11
/*     */     //   233: aload_2
/*     */     //   234: aload_3
/*     */     //   235: aload #11
/*     */     //   237: invokevirtual getSelected : ()Ljava/lang/String;
/*     */     //   240: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   243: goto -> 609
/*     */     //   246: aload #4
/*     */     //   248: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting
/*     */     //   251: astore #12
/*     */     //   253: aload_2
/*     */     //   254: aload_3
/*     */     //   255: ldc_w ','
/*     */     //   258: aload #12
/*     */     //   260: invokevirtual getSelected : ()Ljava/util/List;
/*     */     //   263: invokestatic join : (Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
/*     */     //   266: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   269: goto -> 609
/*     */     //   272: aload #4
/*     */     //   274: checkcast vorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting
/*     */     //   277: astore #13
/*     */     //   279: aload #13
/*     */     //   281: invokevirtual isCustomPicker : ()Z
/*     */     //   284: ifeq -> 305
/*     */     //   287: aload #13
/*     */     //   289: invokevirtual isActive : ()Z
/*     */     //   292: ifne -> 305
/*     */     //   295: aload_2
/*     */     //   296: aload_3
/*     */     //   297: iconst_0
/*     */     //   298: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   301: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   304: return
/*     */     //   305: new com/google/gson/JsonObject
/*     */     //   308: dup
/*     */     //   309: invokespecial <init> : ()V
/*     */     //   312: astore #14
/*     */     //   314: aload #14
/*     */     //   316: ldc_w 'active'
/*     */     //   319: aload #13
/*     */     //   321: invokevirtual isActive : ()Z
/*     */     //   324: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   327: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   330: aload #14
/*     */     //   332: ldc_w 'enabled'
/*     */     //   335: aload #13
/*     */     //   337: invokevirtual isEnabled : ()Z
/*     */     //   340: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   343: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   346: aload #14
/*     */     //   348: ldc_w 'highlightColor'
/*     */     //   351: aload #13
/*     */     //   353: invokevirtual getHighlightColor : ()I
/*     */     //   356: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   359: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   362: aload #14
/*     */     //   364: ldc_w 'itemId'
/*     */     //   367: aload #13
/*     */     //   369: invokevirtual getItemId : ()Ljava/lang/String;
/*     */     //   372: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   375: aload #14
/*     */     //   377: ldc_w 'displayName'
/*     */     //   380: aload #13
/*     */     //   382: invokevirtual getDisplayName : ()Ljava/lang/String;
/*     */     //   385: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   388: aload #14
/*     */     //   390: ldc_w 'matchName'
/*     */     //   393: aload #13
/*     */     //   395: invokevirtual getMatchName : ()Ljava/lang/String;
/*     */     //   398: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   401: aload_2
/*     */     //   402: aload_3
/*     */     //   403: aload #14
/*     */     //   405: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   408: goto -> 609
/*     */     //   411: aload #4
/*     */     //   413: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting
/*     */     //   416: astore #14
/*     */     //   418: new com/google/gson/JsonObject
/*     */     //   421: dup
/*     */     //   422: invokespecial <init> : ()V
/*     */     //   425: astore #15
/*     */     //   427: aload #15
/*     */     //   429: ldc_w 'selectedColorIndex'
/*     */     //   432: aload #14
/*     */     //   434: invokevirtual getSelectedColorIndex : ()I
/*     */     //   437: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   440: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*     */     //   443: new com/google/gson/JsonArray
/*     */     //   446: dup
/*     */     //   447: invokespecial <init> : ()V
/*     */     //   450: astore #16
/*     */     //   452: aload #14
/*     */     //   454: invokevirtual getAllColors : ()Ljava/util/List;
/*     */     //   457: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   462: astore #17
/*     */     //   464: aload #17
/*     */     //   466: invokeinterface hasNext : ()Z
/*     */     //   471: ifeq -> 502
/*     */     //   474: aload #17
/*     */     //   476: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   481: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   484: astore #18
/*     */     //   486: aload #16
/*     */     //   488: aload #18
/*     */     //   490: invokevirtual getColor : ()I
/*     */     //   493: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   496: invokevirtual add : (Ljava/lang/Number;)V
/*     */     //   499: goto -> 464
/*     */     //   502: aload #15
/*     */     //   504: ldc_w 'colors'
/*     */     //   507: aload #16
/*     */     //   509: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   512: aload_2
/*     */     //   513: aload_3
/*     */     //   514: aload #15
/*     */     //   516: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   519: goto -> 609
/*     */     //   522: aload #4
/*     */     //   524: checkcast vorga/phazeclient/api/feature/module/setting/implement/GroupSetting
/*     */     //   527: astore #15
/*     */     //   529: new com/google/gson/JsonObject
/*     */     //   532: dup
/*     */     //   533: invokespecial <init> : ()V
/*     */     //   536: astore #16
/*     */     //   538: aload #16
/*     */     //   540: ldc_w 'state'
/*     */     //   543: aload #15
/*     */     //   545: invokevirtual isValue : ()Z
/*     */     //   548: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   551: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*     */     //   554: aload #15
/*     */     //   556: invokevirtual getSubSettings : ()Ljava/util/List;
/*     */     //   559: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   564: astore #17
/*     */     //   566: aload #17
/*     */     //   568: invokeinterface hasNext : ()Z
/*     */     //   573: ifeq -> 599
/*     */     //   576: aload #17
/*     */     //   578: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   583: checkcast vorga/phazeclient/api/feature/module/setting/Setting
/*     */     //   586: astore #18
/*     */     //   588: aload_0
/*     */     //   589: aload #18
/*     */     //   591: aload #16
/*     */     //   593: invokevirtual saveSetting : (Lvorga/phazeclient/api/feature/module/setting/Setting;Lcom/google/gson/JsonObject;)V
/*     */     //   596: goto -> 566
/*     */     //   599: aload_2
/*     */     //   600: aload_3
/*     */     //   601: aload #16
/*     */     //   603: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*     */     //   606: goto -> 609
/*     */     //   609: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #409	-> 0
/*     */     //   #410	-> 7
/*     */     //   #413	-> 8
/*     */     //   #414	-> 13
/*     */     //   #415	-> 88
/*     */     //   #416	-> 111
/*     */     //   #417	-> 134
/*     */     //   #418	-> 141
/*     */     //   #419	-> 148
/*     */     //   #420	-> 170
/*     */     //   #421	-> 177
/*     */     //   #422	-> 180
/*     */     //   #423	-> 203
/*     */     //   #424	-> 226
/*     */     //   #425	-> 246
/*     */     //   #426	-> 272
/*     */     //   #427	-> 279
/*     */     //   #428	-> 295
/*     */     //   #429	-> 304
/*     */     //   #431	-> 305
/*     */     //   #432	-> 314
/*     */     //   #433	-> 330
/*     */     //   #434	-> 346
/*     */     //   #435	-> 362
/*     */     //   #436	-> 375
/*     */     //   #437	-> 388
/*     */     //   #438	-> 401
/*     */     //   #439	-> 408
/*     */     //   #440	-> 411
/*     */     //   #441	-> 418
/*     */     //   #442	-> 427
/*     */     //   #444	-> 443
/*     */     //   #445	-> 452
/*     */     //   #446	-> 486
/*     */     //   #447	-> 499
/*     */     //   #448	-> 502
/*     */     //   #449	-> 512
/*     */     //   #450	-> 519
/*     */     //   #451	-> 522
/*     */     //   #452	-> 529
/*     */     //   #453	-> 538
/*     */     //   #455	-> 554
/*     */     //   #456	-> 588
/*     */     //   #457	-> 596
/*     */     //   #459	-> 599
/*     */     //   #460	-> 606
/*     */     //   #464	-> 609
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   95	16	6	booleanSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   118	16	7	valueSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*     */     //   148	29	9	value	Ljava/lang/String;
/*     */     //   141	39	8	textSetting	Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*     */     //   187	16	9	bindSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*     */     //   210	16	10	colorSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   233	13	11	selectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/SelectSetting;
/*     */     //   253	19	12	multiSelectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;
/*     */     //   314	94	14	itemObject	Lcom/google/gson/JsonObject;
/*     */     //   279	132	13	itemPickerSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting;
/*     */     //   486	13	18	color	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   427	92	15	colorObject	Lcom/google/gson/JsonObject;
/*     */     //   452	67	16	colorsArray	Lcom/google/gson/JsonArray;
/*     */     //   418	104	14	multiColor	Lvorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting;
/*     */     //   588	8	18	subSetting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   538	68	16	groupObject	Lcom/google/gson/JsonObject;
/*     */     //   529	80	15	group	Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*     */     //   0	610	0	this	Lvorga/phazeclient/implement/menu/profile/MenuProfileManager;
/*     */     //   0	610	1	setting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   0	610	2	moduleData	Lcom/google/gson/JsonObject;
/*     */     //   13	597	3	key	Ljava/lang/String;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadSetting(Setting setting, JsonObject moduleData) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual isSaveToConfig : ()Z
/*     */     //   4: ifne -> 8
/*     */     //   7: return
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual getNameKey : ()Ljava/lang/String;
/*     */     //   12: astore_3
/*     */     //   13: aload_2
/*     */     //   14: aload_3
/*     */     //   15: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   18: ifne -> 22
/*     */     //   21: return
/*     */     //   22: aload_2
/*     */     //   23: aload_3
/*     */     //   24: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   27: astore #4
/*     */     //   29: aload_1
/*     */     //   30: dup
/*     */     //   31: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   34: pop
/*     */     //   35: astore #5
/*     */     //   37: iconst_0
/*     */     //   38: istore #6
/*     */     //   40: aload #5
/*     */     //   42: iload #6
/*     */     //   44: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   49: tableswitch default -> 844, 0 -> 104, 1 -> 125, 2 -> 146, 3 -> 193, 4 -> 214, 5 -> 235, 6 -> 255, 7 -> 314, 8 -> 581, 9 -> 754
/*     */     //   104: aload #5
/*     */     //   106: checkcast vorga/phazeclient/api/feature/module/setting/implement/BooleanSetting
/*     */     //   109: astore #7
/*     */     //   111: aload #7
/*     */     //   113: aload #4
/*     */     //   115: invokevirtual getAsBoolean : ()Z
/*     */     //   118: invokevirtual setValue : (Z)Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   121: pop
/*     */     //   122: goto -> 844
/*     */     //   125: aload #5
/*     */     //   127: checkcast vorga/phazeclient/api/feature/module/setting/implement/ValueSetting
/*     */     //   130: astore #8
/*     */     //   132: aload #8
/*     */     //   134: aload #4
/*     */     //   136: invokevirtual getAsFloat : ()F
/*     */     //   139: invokevirtual setValue : (F)Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*     */     //   142: pop
/*     */     //   143: goto -> 844
/*     */     //   146: aload #5
/*     */     //   148: checkcast vorga/phazeclient/api/feature/module/setting/implement/TextSetting
/*     */     //   151: astore #9
/*     */     //   153: aload #4
/*     */     //   155: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   158: astore #10
/*     */     //   160: aload #10
/*     */     //   162: ldc_w '%%'
/*     */     //   165: ldc_w ' '
/*     */     //   168: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   171: ldc_w '++'
/*     */     //   174: ldc_w '/'
/*     */     //   177: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*     */     //   180: astore #10
/*     */     //   182: aload #9
/*     */     //   184: aload #10
/*     */     //   186: invokevirtual setText : (Ljava/lang/String;)Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*     */     //   189: pop
/*     */     //   190: goto -> 844
/*     */     //   193: aload #5
/*     */     //   195: checkcast vorga/phazeclient/api/feature/module/setting/implement/BindSetting
/*     */     //   198: astore #10
/*     */     //   200: aload #10
/*     */     //   202: aload #4
/*     */     //   204: invokevirtual getAsInt : ()I
/*     */     //   207: invokevirtual setKey : (I)Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*     */     //   210: pop
/*     */     //   211: goto -> 844
/*     */     //   214: aload #5
/*     */     //   216: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   219: astore #11
/*     */     //   221: aload #11
/*     */     //   223: aload #4
/*     */     //   225: invokevirtual getAsInt : ()I
/*     */     //   228: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   231: pop
/*     */     //   232: goto -> 844
/*     */     //   235: aload #5
/*     */     //   237: checkcast vorga/phazeclient/api/feature/module/setting/implement/SelectSetting
/*     */     //   240: astore #12
/*     */     //   242: aload #12
/*     */     //   244: aload #4
/*     */     //   246: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   249: invokevirtual setSelected : (Ljava/lang/String;)V
/*     */     //   252: goto -> 844
/*     */     //   255: aload #5
/*     */     //   257: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting
/*     */     //   260: astore #13
/*     */     //   262: aload #4
/*     */     //   264: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   267: astore #14
/*     */     //   269: new java/util/ArrayList
/*     */     //   272: dup
/*     */     //   273: aload #14
/*     */     //   275: ldc_w ','
/*     */     //   278: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
/*     */     //   281: invokestatic of : ([Ljava/lang/Object;)Ljava/util/List;
/*     */     //   284: invokespecial <init> : (Ljava/util/Collection;)V
/*     */     //   287: astore #15
/*     */     //   289: aload #15
/*     */     //   291: aload #13
/*     */     //   293: <illegal opcode> test : (Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;)Ljava/util/function/Predicate;
/*     */     //   298: invokeinterface removeIf : (Ljava/util/function/Predicate;)Z
/*     */     //   303: pop
/*     */     //   304: aload #13
/*     */     //   306: aload #15
/*     */     //   308: invokevirtual setSelected : (Ljava/util/List;)V
/*     */     //   311: goto -> 844
/*     */     //   314: aload #5
/*     */     //   316: checkcast vorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting
/*     */     //   319: astore #14
/*     */     //   321: aload #4
/*     */     //   323: invokevirtual isJsonPrimitive : ()Z
/*     */     //   326: ifeq -> 351
/*     */     //   329: aload #4
/*     */     //   331: invokevirtual getAsJsonPrimitive : ()Lcom/google/gson/JsonPrimitive;
/*     */     //   334: invokevirtual isBoolean : ()Z
/*     */     //   337: ifeq -> 351
/*     */     //   340: aload #14
/*     */     //   342: aload #4
/*     */     //   344: invokevirtual getAsBoolean : ()Z
/*     */     //   347: invokevirtual setEnabled : (Z)V
/*     */     //   350: return
/*     */     //   351: aload #4
/*     */     //   353: invokevirtual isJsonObject : ()Z
/*     */     //   356: ifne -> 360
/*     */     //   359: return
/*     */     //   360: aload #4
/*     */     //   362: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*     */     //   365: astore #15
/*     */     //   367: aload #15
/*     */     //   369: ldc_w 'itemId'
/*     */     //   372: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   375: ifeq -> 392
/*     */     //   378: aload #15
/*     */     //   380: ldc_w 'itemId'
/*     */     //   383: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   386: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   389: goto -> 395
/*     */     //   392: ldc_w ''
/*     */     //   395: astore #16
/*     */     //   397: aload #15
/*     */     //   399: ldc_w 'displayName'
/*     */     //   402: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   405: ifeq -> 422
/*     */     //   408: aload #15
/*     */     //   410: ldc_w 'displayName'
/*     */     //   413: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   416: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   419: goto -> 425
/*     */     //   422: ldc_w ''
/*     */     //   425: astore #17
/*     */     //   427: aload #15
/*     */     //   429: ldc_w 'matchName'
/*     */     //   432: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   435: ifeq -> 452
/*     */     //   438: aload #15
/*     */     //   440: ldc_w 'matchName'
/*     */     //   443: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   446: invokevirtual getAsString : ()Ljava/lang/String;
/*     */     //   449: goto -> 455
/*     */     //   452: ldc_w ''
/*     */     //   455: astore #18
/*     */     //   457: aload #15
/*     */     //   459: ldc_w 'enabled'
/*     */     //   462: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   465: ifeq -> 482
/*     */     //   468: aload #15
/*     */     //   470: ldc_w 'enabled'
/*     */     //   473: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   476: invokevirtual getAsBoolean : ()Z
/*     */     //   479: goto -> 487
/*     */     //   482: aload #14
/*     */     //   484: invokevirtual isEnabled : ()Z
/*     */     //   487: istore #19
/*     */     //   489: aload #15
/*     */     //   491: ldc_w 'highlightColor'
/*     */     //   494: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   497: ifeq -> 514
/*     */     //   500: aload #15
/*     */     //   502: ldc_w 'highlightColor'
/*     */     //   505: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   508: invokevirtual getAsInt : ()I
/*     */     //   511: goto -> 519
/*     */     //   514: aload #14
/*     */     //   516: invokevirtual getHighlightColor : ()I
/*     */     //   519: istore #20
/*     */     //   521: aload #15
/*     */     //   523: ldc_w 'active'
/*     */     //   526: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   529: ifeq -> 546
/*     */     //   532: aload #15
/*     */     //   534: ldc_w 'active'
/*     */     //   537: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   540: invokevirtual getAsBoolean : ()Z
/*     */     //   543: goto -> 559
/*     */     //   546: aload #16
/*     */     //   548: invokevirtual isBlank : ()Z
/*     */     //   551: ifne -> 558
/*     */     //   554: iconst_1
/*     */     //   555: goto -> 559
/*     */     //   558: iconst_0
/*     */     //   559: istore #21
/*     */     //   561: aload #14
/*     */     //   563: iload #21
/*     */     //   565: aload #16
/*     */     //   567: aload #17
/*     */     //   569: aload #18
/*     */     //   571: iload #19
/*     */     //   573: iload #20
/*     */     //   575: invokevirtual setSerializedState : (ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZI)V
/*     */     //   578: goto -> 844
/*     */     //   581: aload #5
/*     */     //   583: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting
/*     */     //   586: astore #15
/*     */     //   588: aload #4
/*     */     //   590: invokevirtual isJsonPrimitive : ()Z
/*     */     //   593: ifeq -> 634
/*     */     //   596: aload #4
/*     */     //   598: invokevirtual getAsJsonPrimitive : ()Lcom/google/gson/JsonPrimitive;
/*     */     //   601: invokevirtual isNumber : ()Z
/*     */     //   604: ifeq -> 634
/*     */     //   607: aload #4
/*     */     //   609: invokevirtual getAsInt : ()I
/*     */     //   612: istore #16
/*     */     //   614: aload #15
/*     */     //   616: invokevirtual getColor1 : ()Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   619: ifnull -> 633
/*     */     //   622: aload #15
/*     */     //   624: invokevirtual getColor1 : ()Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   627: iload #16
/*     */     //   629: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   632: pop
/*     */     //   633: return
/*     */     //   634: aload #4
/*     */     //   636: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*     */     //   639: astore #16
/*     */     //   641: aload #16
/*     */     //   643: ldc_w 'selectedColorIndex'
/*     */     //   646: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   649: ifeq -> 668
/*     */     //   652: aload #15
/*     */     //   654: aload #16
/*     */     //   656: ldc_w 'selectedColorIndex'
/*     */     //   659: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   662: invokevirtual getAsInt : ()I
/*     */     //   665: invokevirtual setSelectedColorIndex : (I)V
/*     */     //   668: aload #16
/*     */     //   670: ldc_w 'colors'
/*     */     //   673: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   676: ifeq -> 751
/*     */     //   679: aload #16
/*     */     //   681: ldc_w 'colors'
/*     */     //   684: invokevirtual getAsJsonArray : (Ljava/lang/String;)Lcom/google/gson/JsonArray;
/*     */     //   687: astore #17
/*     */     //   689: aload #15
/*     */     //   691: invokevirtual getAllColors : ()Ljava/util/List;
/*     */     //   694: astore #18
/*     */     //   696: iconst_0
/*     */     //   697: istore #19
/*     */     //   699: iload #19
/*     */     //   701: aload #17
/*     */     //   703: invokevirtual size : ()I
/*     */     //   706: aload #18
/*     */     //   708: invokeinterface size : ()I
/*     */     //   713: invokestatic min : (II)I
/*     */     //   716: if_icmpge -> 751
/*     */     //   719: aload #18
/*     */     //   721: iload #19
/*     */     //   723: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   728: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*     */     //   731: aload #17
/*     */     //   733: iload #19
/*     */     //   735: invokevirtual get : (I)Lcom/google/gson/JsonElement;
/*     */     //   738: invokevirtual getAsInt : ()I
/*     */     //   741: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   744: pop
/*     */     //   745: iinc #19, 1
/*     */     //   748: goto -> 699
/*     */     //   751: goto -> 844
/*     */     //   754: aload #5
/*     */     //   756: checkcast vorga/phazeclient/api/feature/module/setting/implement/GroupSetting
/*     */     //   759: astore #16
/*     */     //   761: aload #4
/*     */     //   763: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*     */     //   766: astore #17
/*     */     //   768: aload #17
/*     */     //   770: ldc_w 'state'
/*     */     //   773: invokevirtual has : (Ljava/lang/String;)Z
/*     */     //   776: ifeq -> 796
/*     */     //   779: aload #16
/*     */     //   781: aload #17
/*     */     //   783: ldc_w 'state'
/*     */     //   786: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*     */     //   789: invokevirtual getAsBoolean : ()Z
/*     */     //   792: invokevirtual setValue : (Z)Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*     */     //   795: pop
/*     */     //   796: aload #16
/*     */     //   798: invokevirtual getSubSettings : ()Ljava/util/List;
/*     */     //   801: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   806: astore #18
/*     */     //   808: aload #18
/*     */     //   810: invokeinterface hasNext : ()Z
/*     */     //   815: ifeq -> 841
/*     */     //   818: aload #18
/*     */     //   820: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   825: checkcast vorga/phazeclient/api/feature/module/setting/Setting
/*     */     //   828: astore #19
/*     */     //   830: aload_0
/*     */     //   831: aload #19
/*     */     //   833: aload #17
/*     */     //   835: invokevirtual loadSetting : (Lvorga/phazeclient/api/feature/module/setting/Setting;Lcom/google/gson/JsonObject;)V
/*     */     //   838: goto -> 808
/*     */     //   841: goto -> 844
/*     */     //   844: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #467	-> 0
/*     */     //   #468	-> 7
/*     */     //   #471	-> 8
/*     */     //   #472	-> 13
/*     */     //   #473	-> 21
/*     */     //   #476	-> 22
/*     */     //   #477	-> 29
/*     */     //   #478	-> 104
/*     */     //   #479	-> 125
/*     */     //   #480	-> 146
/*     */     //   #481	-> 153
/*     */     //   #482	-> 160
/*     */     //   #483	-> 182
/*     */     //   #484	-> 190
/*     */     //   #485	-> 193
/*     */     //   #486	-> 214
/*     */     //   #487	-> 235
/*     */     //   #488	-> 255
/*     */     //   #489	-> 262
/*     */     //   #490	-> 269
/*     */     //   #491	-> 289
/*     */     //   #492	-> 304
/*     */     //   #493	-> 311
/*     */     //   #494	-> 314
/*     */     //   #495	-> 321
/*     */     //   #496	-> 340
/*     */     //   #497	-> 350
/*     */     //   #499	-> 351
/*     */     //   #500	-> 359
/*     */     //   #502	-> 360
/*     */     //   #503	-> 367
/*     */     //   #504	-> 397
/*     */     //   #505	-> 427
/*     */     //   #506	-> 457
/*     */     //   #507	-> 468
/*     */     //   #508	-> 482
/*     */     //   #509	-> 489
/*     */     //   #510	-> 500
/*     */     //   #511	-> 514
/*     */     //   #512	-> 521
/*     */     //   #513	-> 532
/*     */     //   #514	-> 546
/*     */     //   #515	-> 561
/*     */     //   #516	-> 578
/*     */     //   #517	-> 581
/*     */     //   #518	-> 588
/*     */     //   #519	-> 607
/*     */     //   #520	-> 614
/*     */     //   #521	-> 622
/*     */     //   #523	-> 633
/*     */     //   #526	-> 634
/*     */     //   #527	-> 641
/*     */     //   #528	-> 652
/*     */     //   #531	-> 668
/*     */     //   #532	-> 679
/*     */     //   #533	-> 689
/*     */     //   #534	-> 696
/*     */     //   #535	-> 719
/*     */     //   #534	-> 745
/*     */     //   #538	-> 751
/*     */     //   #539	-> 754
/*     */     //   #540	-> 761
/*     */     //   #541	-> 768
/*     */     //   #542	-> 779
/*     */     //   #545	-> 796
/*     */     //   #546	-> 830
/*     */     //   #547	-> 838
/*     */     //   #548	-> 841
/*     */     //   #552	-> 844
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   111	14	7	booleanSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   132	14	8	valueSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*     */     //   160	30	10	value	Ljava/lang/String;
/*     */     //   153	40	9	textSetting	Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*     */     //   200	14	10	bindSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*     */     //   221	14	11	colorSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*     */     //   242	13	12	selectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/SelectSetting;
/*     */     //   269	42	14	value	Ljava/lang/String;
/*     */     //   289	22	15	selected	Ljava/util/List;
/*     */     //   262	52	13	multiSelectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;
/*     */     //   367	211	15	itemObject	Lcom/google/gson/JsonObject;
/*     */     //   397	181	16	itemId	Ljava/lang/String;
/*     */     //   427	151	17	displayName	Ljava/lang/String;
/*     */     //   457	121	18	matchName	Ljava/lang/String;
/*     */     //   489	89	19	enabled	Z
/*     */     //   521	57	20	highlightColor	I
/*     */     //   561	17	21	active	Z
/*     */     //   321	260	14	itemPickerSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting;
/*     */     //   614	20	16	oldColor	I
/*     */     //   699	52	19	i	I
/*     */     //   689	62	17	colorsArray	Lcom/google/gson/JsonArray;
/*     */     //   696	55	18	colorSettings	Ljava/util/List;
/*     */     //   641	110	16	colorObject	Lcom/google/gson/JsonObject;
/*     */     //   588	166	15	multiColor	Lvorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting;
/*     */     //   830	8	19	subSetting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   768	73	17	groupObject	Lcom/google/gson/JsonObject;
/*     */     //   761	83	16	group	Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*     */     //   0	845	0	this	Lvorga/phazeclient/implement/menu/profile/MenuProfileManager;
/*     */     //   0	845	1	setting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*     */     //   0	845	2	moduleData	Lcom/google/gson/JsonObject;
/*     */     //   13	832	3	key	Ljava/lang/String;
/*     */     //   29	816	4	element	Lcom/google/gson/JsonElement;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   289	22	15	selected	Ljava/util/List<Ljava/lang/String;>;
/*     */     //   696	55	18	colorSettings	Ljava/util/List<Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonObject readProfile(Path path) {
/*     */     try {
/* 556 */       if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 557 */         return null;
/*     */       }
/* 559 */       String content = Files.readString(path, StandardCharsets.UTF_8);
/* 560 */       return (JsonObject)GSON.fromJson(content, JsonObject.class);
/* 561 */     } catch (Exception ignored) {
/* 562 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeProfile(Path path, JsonObject root) {
/*     */     try {
/* 568 */       Files.createDirectories(path.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 569 */       Files.writeString(path, GSON
/*     */           
/* 571 */           .toJson((JsonElement)root), StandardCharsets.UTF_8, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 577 */     catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private Path profilesDirectory() {
/* 582 */     class_310 client = class_310.method_1551();
/* 583 */     Path root = (client != null) ? client.field_1697.toPath() : Path.of(".", new String[0]);
/* 584 */     return root.resolve("Phaze").resolve("configs");
/*     */   }
/*     */   
/*     */   private Path profilePath(String profileName) {
/* 588 */     return profilesDirectory().resolve(sanitizeFileName(profileName) + ".profile");
/*     */   }
/*     */   
/*     */   private String nextNewProfileName() {
/* 592 */     int index = 1;
/*     */     while (true) {
/* 594 */       String name = "new-profile" + index;
/* 595 */       if (findExistingProfile(name) == null) {
/* 596 */         return name;
/*     */       }
/* 598 */       index++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String findExistingProfile(String name) {
/* 603 */     if (name == null) {
/* 604 */       return null;
/*     */     }
/*     */     
/* 607 */     for (String profile : this.profiles) {
/* 608 */       if (profile.equalsIgnoreCase(name)) {
/* 609 */         return profile;
/*     */       }
/*     */     } 
/* 612 */     return null;
/*     */   }
/*     */   
/*     */   private void sortProfiles() {
/* 616 */     this.profiles.sort((first, second) -> {
/*     */           boolean firstDefault = first.equalsIgnoreCase("DEFAULT");
/*     */           boolean secondDefault = second.equalsIgnoreCase("DEFAULT");
/* 619 */           return (firstDefault && !secondDefault) ? -1 : (
/*     */ 
/*     */             
/* 622 */             (!firstDefault && secondDefault) ? 1 : first.compareToIgnoreCase(second));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String sanitizeProfileName(String name) {
/* 630 */     if (name == null) {
/* 631 */       return "";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 636 */     String sanitized = name.trim().replaceAll("[\\\\/:*?\"<>|]", "-").replaceAll("\\s+", " ");
/* 637 */     if (sanitized.isBlank()) {
/* 638 */       return "";
/*     */     }
/* 640 */     return sanitized;
/*     */   }
/*     */   
/*     */   private String sanitizeFileName(String name) {
/* 644 */     return sanitizeProfileName(name).replace(' ', '_');
/*     */   }
/*     */   
/*     */   private String stripExtension(String fileName) {
/* 648 */     int dotIndex = fileName.lastIndexOf('.');
/* 649 */     return (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\profile\MenuProfileManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */