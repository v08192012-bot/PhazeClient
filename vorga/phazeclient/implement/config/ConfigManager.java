/*      */ package vorga.phazeclient.implement.config;
/*      */ 
/*      */ import com.google.gson.Gson;
/*      */ import com.google.gson.GsonBuilder;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.google.gson.JsonObject;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.nio.file.CopyOption;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.StandardCopyOption;
/*      */ import java.util.Arrays;
/*      */ import java.util.Base64;
/*      */ import java.util.Comparator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ import net.minecraft.class_310;
/*      */ import vorga.phazeclient.api.feature.module.Module;
/*      */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*      */ import vorga.phazeclient.api.feature.module.setting.implement.MultiSelectSetting;
/*      */ import vorga.phazeclient.core.Main;
/*      */ import vorga.phazeclient.implement.features.modules.client.Theme;
/*      */ import vorga.phazeclient.implement.features.modules.hud.ArmorHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.BossBarHud;
/*      */ import vorga.phazeclient.implement.features.modules.hud.RectHudModule;
/*      */ import vorga.phazeclient.implement.features.modules.other.LowHealthWarning;
/*      */ import vorga.phazeclient.implement.menu.MenuUiSettings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ConfigManager
/*      */ {
/*   48 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*   49 */   private static final ConfigManager INSTANCE = new ConfigManager();
/*      */   
/*      */   private final File configDir;
/*      */   private final File configsDir;
/*      */   private final File filesDir;
/*      */   private final File currentConfigFile;
/*      */   private File currentConfig;
/*   56 */   private String currentConfigName = "default";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long AUTOSAVE_DEBOUNCE_MS = 250L;
/*      */ 
/*      */ 
/*      */   
/*   64 */   private volatile long dirtyAt = 0L;
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile boolean autoSaveEnabled = false;
/*      */ 
/*      */   
/*      */   private volatile boolean isLoadingConfig = false;
/*      */ 
/*      */   
/*      */   private int pendingScreenRefWidth;
/*      */ 
/*      */   
/*      */   private int pendingScreenRefHeight;
/*      */ 
/*      */   
/*      */   private static final Comparator<String> CONFIG_LIST_ORDER;
/*      */ 
/*      */ 
/*      */   
/*      */   public static ConfigManager getInstance() {
/*   85 */     return INSTANCE;
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
/*      */   private void initDirectories() {
/*  101 */     if (!this.configDir.exists()) {
/*  102 */       this.configDir.mkdirs();
/*      */     }
/*  104 */     if (!this.configsDir.exists()) {
/*  105 */       this.configsDir.mkdirs();
/*      */     }
/*  107 */     if (!this.filesDir.exists()) {
/*  108 */       this.filesDir.mkdirs();
/*      */     }
/*      */   }
/*      */   
/*      */   private void loadCurrentConfigName() {
/*  113 */     if (this.currentConfigFile.exists())
/*  114 */       try { BufferedReader reader = new BufferedReader(new FileReader(this.currentConfigFile)); 
/*  115 */         try { JsonObject json = (JsonObject)GSON.fromJson(reader, JsonObject.class);
/*  116 */           if (json != null && json.has("current_config")) {
/*  117 */             this.currentConfigName = json.get("current_config").getAsString();
/*      */           }
/*  119 */           if (this.currentConfigName == null || this.currentConfigName.isEmpty()) {
/*  120 */             this.currentConfigName = "default";
/*      */           }
/*  122 */           reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*  123 */       { this.currentConfigName = "default"; }
/*      */        
/*      */   }
/*      */   
/*      */   private void saveCurrentConfigName() {
/*      */     
/*  129 */     try { FileWriter writer = new FileWriter(this.currentConfigFile); 
/*  130 */       try { JsonObject json = new JsonObject();
/*  131 */         json.addProperty("current_config", this.currentConfigName);
/*  132 */         GSON.toJson((JsonElement)json, writer);
/*  133 */         writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*  134 */     { e.printStackTrace(); }
/*      */   
/*      */   }
/*      */   
/*      */   public String getCurrentConfigName() {
/*  139 */     return this.currentConfigName;
/*      */   }
/*      */   
/*      */   public void setCurrentConfigName(String name) {
/*  143 */     this.currentConfigName = name;
/*  144 */     saveCurrentConfigName();
/*      */   }
/*      */   
/*      */   public File getConfigsDir() {
/*  148 */     return this.configsDir;
/*      */   }
/*      */   
/*      */   public File getConfigFile(String configName) {
/*  152 */     return new File(this.configsDir, configName + ".Phaze");
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
/*      */   public void save(File configFile) {
/*  177 */     JsonObject config = new JsonObject();
/*  178 */     JsonObject modules = new JsonObject();
/*      */     
/*  180 */     for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/*  181 */       JsonObject moduleData = new JsonObject();
/*  182 */       moduleData.addProperty("enabled", Boolean.valueOf(module.isState()));
/*  183 */       moduleData.addProperty("key", Integer.valueOf(module.getKey()));
/*      */       
/*  185 */       if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/*  186 */         moduleData.addProperty("hud_x_ratio", Float.valueOf(rectHudModule.getHudXRatio()));
/*  187 */         moduleData.addProperty("hud_y_ratio", Float.valueOf(rectHudModule.getHudYRatio()));
/*  188 */         moduleData.addProperty("hud_x", Float.valueOf(rectHudModule.getHudX()));
/*  189 */         moduleData.addProperty("hud_y", Float.valueOf(rectHudModule.getHudY()));
/*      */ 
/*      */ 
/*      */         
/*  193 */         moduleData.addProperty("hud_ref_w", Integer.valueOf(rectHudModule.getHudRefWidth()));
/*  194 */         moduleData.addProperty("hud_ref_h", Integer.valueOf(rectHudModule.getHudRefHeight()));
/*  195 */         moduleData.addProperty("hud_scale", Float.valueOf(rectHudModule.getHudScale()));
/*  196 */         if (rectHudModule instanceof BossBarHud) { BossBarHud bossBar = (BossBarHud)rectHudModule;
/*  197 */           moduleData.addProperty("hud_auto_position", Boolean.valueOf(bossBar.isHudPositionAutomatic())); }
/*      */          }
/*  199 */       else if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/*  200 */         moduleData.addProperty("hud_x_ratio", Float.valueOf(armorHud.getHudXRatio()));
/*  201 */         moduleData.addProperty("hud_y_ratio", Float.valueOf(armorHud.getHudYRatio()));
/*  202 */         moduleData.addProperty("hud_x", Float.valueOf(armorHud.getHudX()));
/*  203 */         moduleData.addProperty("hud_y", Float.valueOf(armorHud.getHudY()));
/*      */ 
/*      */ 
/*      */         
/*  207 */         moduleData.addProperty("hud_ref_w", Integer.valueOf(armorHud.getHudRefWidth()));
/*  208 */         moduleData.addProperty("hud_ref_h", Integer.valueOf(armorHud.getHudRefHeight()));
/*  209 */         moduleData.addProperty("hud_scale", Float.valueOf(armorHud.getHudScale())); }
/*      */ 
/*      */       
/*  212 */       JsonObject settings = new JsonObject();
/*  213 */       for (Setting setting : module.settings()) {
/*      */         try {
/*  215 */           serializeSetting(setting, settings);
/*  216 */         } catch (Throwable t) {
/*      */ 
/*      */ 
/*      */           
/*  220 */           System.err.println("[Phaze] failed to serialize setting " + setting.getRawName() + " of " + module.getName() + ": " + String.valueOf(t));
/*      */         } 
/*      */       } 
/*  223 */       moduleData.add("settings", (JsonElement)settings);
/*      */       
/*  225 */       modules.add(module.getName(), (JsonElement)moduleData);
/*      */     } 
/*      */     
/*  228 */     config.add("modules", (JsonElement)modules);
/*  229 */     Theme theme = Theme.getInstance();
/*  230 */     config.addProperty("theme", theme.menuTheme.getSelected());
/*  231 */     config.addProperty("blurRadius", Float.valueOf(theme.blurRadius.getValue()));
/*  232 */     config.addProperty("menuPanoramaSpeed", Double.valueOf(MenuUiSettings.getInstance().getPanoramaSpeed()));
/*  233 */     config.addProperty("menuGuiFpsLimit", Integer.valueOf(MenuUiSettings.getInstance().getGuiFpsLimit()));
/*  234 */     config.addProperty("menuGuiScale", Float.valueOf(MenuUiSettings.getInstance().getGuiScale()));
/*  235 */     config.addProperty("menuPanoramaPreset", MenuUiSettings.getInstance().getSelectedPanoramaPresetId());
/*  236 */     config.addProperty("customMainMenuEnabled", Boolean.valueOf(MenuUiSettings.getInstance().isCustomMainMenuEnabled()));
/*  237 */     config.addProperty("menuPanoramaSpeedScaleVersion", Integer.valueOf(3));
/*      */ 
/*      */ 
/*      */     
/*  241 */     writeScreenReference(config);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     if (configFile.exists()) {
/*  249 */       try { Reader rdr = new FileReader(configFile); 
/*  250 */         try { JsonObject existing = (JsonObject)GSON.fromJson(rdr, JsonObject.class);
/*  251 */           if (existing != null && existing.has("imported") && existing
/*  252 */             .get("imported").getAsBoolean()) {
/*  253 */             config.addProperty("imported", Boolean.valueOf(true));
/*      */           }
/*  255 */           rdr.close(); } catch (Throwable throwable) { try { rdr.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  263 */     Path target = configFile.toPath();
/*  264 */     Path tmp = target.resolveSibling(configFile.getName() + ".tmp");
/*  265 */     Path bak = target.resolveSibling(configFile.getName() + ".bak");
/*      */ 
/*      */     
/*      */     try {
/*  269 */       FileWriter writer = new FileWriter(tmp.toFile()); 
/*  270 */       try { GSON.toJson((JsonElement)config, writer);
/*  271 */         writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */          throw throwable; }
/*  273 */        if (Files.exists(target, new java.nio.file.LinkOption[0])) {
/*      */         try {
/*  275 */           Files.move(target, bak, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*  276 */         } catch (IOException iOException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  283 */         Files.move(tmp, target, new CopyOption[] { StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING });
/*  284 */       } catch (IOException atomicFailed) {
/*      */ 
/*      */ 
/*      */         
/*  288 */         Files.move(tmp, target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*      */       } 
/*  290 */     } catch (IOException e) {
/*  291 */       e.printStackTrace();
/*      */       
/*  293 */       try { Files.deleteIfExists(tmp); } catch (IOException iOException) {}
/*      */     } 
/*      */   }
/*      */   
/*      */   public void saveConfig(String configName) {
/*  298 */     save(getConfigFile(configName));
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
/*      */   public String exportCurrentToString() {
/*      */     try {
/*  320 */       JsonObject config = buildCurrentConfigJson();
/*  321 */       String json = GSON.toJson((JsonElement)config);
/*  322 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  323 */       GZIPOutputStream gz = new GZIPOutputStream(out); 
/*  324 */       try { gz.write(json.getBytes(StandardCharsets.UTF_8));
/*  325 */         gz.close(); } catch (Throwable throwable) { try { gz.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*  326 */        String b64 = Base64.getEncoder().withoutPadding().encodeToString(out.toByteArray());
/*  327 */       return "PHAZE1:" + b64;
/*  328 */     } catch (Throwable t) {
/*  329 */       t.printStackTrace();
/*  330 */       return null;
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
/*      */   public String importFromString(String shareString) {
/*  345 */     return importFromString(shareString, null);
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
/*      */   public String importFromString(String shareString, String preferredName) {
/*  358 */     if (shareString == null) return null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     flushNow();
/*  367 */     String trimmed = shareString.trim();
/*      */     
/*  369 */     if (trimmed.startsWith("\"") && trimmed.endsWith("\"") && trimmed.length() > 1) {
/*  370 */       trimmed = trimmed.substring(1, trimmed.length() - 1);
/*      */     }
/*  372 */     if (!trimmed.startsWith("PHAZE1:"))
/*  373 */       return null; 
/*      */     try {
/*      */       byte[] raw;
/*  376 */       String b64 = trimmed.substring("PHAZE1:".length());
/*  377 */       byte[] gz = Base64.getDecoder().decode(b64);
/*      */       
/*  379 */       GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(gz)); 
/*  380 */       try { raw = in.readAllBytes();
/*  381 */         in.close(); } catch (Throwable throwable) { try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*  382 */        String json = new String(raw, StandardCharsets.UTF_8);
/*  383 */       JsonObject parsed = (JsonObject)GSON.fromJson(json, JsonObject.class);
/*  384 */       if (parsed == null) {
/*  385 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  392 */       String name = pickImportName(preferredName);
/*  393 */       File target = getConfigFile(name);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  400 */       parsed.addProperty("imported", Boolean.valueOf(true));
/*  401 */       FileWriter writer = new FileWriter(target); try {
/*  402 */         GSON.toJson((JsonElement)parsed, writer);
/*  403 */         writer.close();
/*      */       } catch (Throwable throwable) {
/*      */         try {
/*      */           writer.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         throw throwable;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  418 */       this.isLoadingConfig = true;
/*      */       try {
/*  420 */         this.currentConfig = target;
/*  421 */         setCurrentConfigName(name);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  426 */         applyInCodeDefaults();
/*      */ 
/*      */ 
/*      */         
/*  430 */         applyConfigJson(parsed);
/*      */       } finally {
/*  432 */         this.dirtyAt = 0L;
/*  433 */         this.isLoadingConfig = false;
/*      */       } 
/*  435 */       return name;
/*  436 */     } catch (Throwable t) {
/*  437 */       t.printStackTrace();
/*  438 */       return null;
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
/*      */   private String pickImportName(String preferredName) {
/*  452 */     if (preferredName != null) {
/*  453 */       String sanitised = preferredName.trim();
/*  454 */       if (!sanitised.isEmpty() && sanitised
/*  455 */         .matches("[A-Za-z0-9_\\-]{1,32}") && 
/*  456 */         !getConfigFile(sanitised).exists()) {
/*  457 */         return sanitised;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  462 */       if (sanitised.matches("[A-Za-z0-9_\\-]{1,30}")) {
/*  463 */         for (int i = 2; i < 1000; i++) {
/*  464 */           String candidate = sanitised + "_" + sanitised;
/*  465 */           if (!getConfigFile(candidate).exists()) {
/*  466 */             return candidate;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*  471 */     return nextImportedName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String nextImportedName() {
/*  481 */     for (int i = 1; i < 1000; i++) {
/*  482 */       String candidate = (i == 1) ? "imported" : ("imported_" + i);
/*  483 */       if (!getConfigFile(candidate).exists()) {
/*  484 */         return candidate;
/*      */       }
/*      */     } 
/*  487 */     return "imported_" + System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JsonObject buildCurrentConfigJson() {
/*  497 */     JsonObject config = new JsonObject();
/*  498 */     JsonObject modules = new JsonObject();
/*      */     
/*  500 */     for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/*  501 */       JsonObject moduleData = new JsonObject();
/*  502 */       moduleData.addProperty("enabled", Boolean.valueOf(module.isState()));
/*  503 */       moduleData.addProperty("key", Integer.valueOf(module.getKey()));
/*      */       
/*  505 */       if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/*  506 */         moduleData.addProperty("hud_x_ratio", Float.valueOf(rectHudModule.getHudXRatio()));
/*  507 */         moduleData.addProperty("hud_y_ratio", Float.valueOf(rectHudModule.getHudYRatio()));
/*  508 */         moduleData.addProperty("hud_x", Float.valueOf(rectHudModule.getHudX()));
/*  509 */         moduleData.addProperty("hud_y", Float.valueOf(rectHudModule.getHudY()));
/*  510 */         moduleData.addProperty("hud_ref_w", Integer.valueOf(rectHudModule.getHudRefWidth()));
/*  511 */         moduleData.addProperty("hud_ref_h", Integer.valueOf(rectHudModule.getHudRefHeight()));
/*  512 */         moduleData.addProperty("hud_scale", Float.valueOf(rectHudModule.getHudScale()));
/*  513 */         if (rectHudModule instanceof BossBarHud) { BossBarHud bossBar = (BossBarHud)rectHudModule;
/*  514 */           moduleData.addProperty("hud_auto_position", Boolean.valueOf(bossBar.isHudPositionAutomatic())); }
/*      */          }
/*  516 */       else if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/*  517 */         moduleData.addProperty("hud_x_ratio", Float.valueOf(armorHud.getHudXRatio()));
/*  518 */         moduleData.addProperty("hud_y_ratio", Float.valueOf(armorHud.getHudYRatio()));
/*  519 */         moduleData.addProperty("hud_x", Float.valueOf(armorHud.getHudX()));
/*  520 */         moduleData.addProperty("hud_y", Float.valueOf(armorHud.getHudY()));
/*  521 */         moduleData.addProperty("hud_ref_w", Integer.valueOf(armorHud.getHudRefWidth()));
/*  522 */         moduleData.addProperty("hud_ref_h", Integer.valueOf(armorHud.getHudRefHeight()));
/*  523 */         moduleData.addProperty("hud_scale", Float.valueOf(armorHud.getHudScale())); }
/*      */ 
/*      */       
/*  526 */       JsonObject settings = new JsonObject();
/*  527 */       for (Setting setting : module.settings()) {
/*      */         try {
/*  529 */           serializeSetting(setting, settings);
/*  530 */         } catch (Throwable throwable) {}
/*      */       } 
/*  532 */       moduleData.add("settings", (JsonElement)settings);
/*  533 */       modules.add(module.getName(), (JsonElement)moduleData);
/*      */     } 
/*      */     
/*  536 */     config.add("modules", (JsonElement)modules);
/*  537 */     Theme theme = Theme.getInstance();
/*  538 */     config.addProperty("theme", theme.menuTheme.getSelected());
/*  539 */     config.addProperty("blurRadius", Float.valueOf(theme.blurRadius.getValue()));
/*  540 */     config.addProperty("menuPanoramaSpeed", Double.valueOf(MenuUiSettings.getInstance().getPanoramaSpeed()));
/*  541 */     config.addProperty("menuGuiFpsLimit", Integer.valueOf(MenuUiSettings.getInstance().getGuiFpsLimit()));
/*  542 */     config.addProperty("menuGuiScale", Float.valueOf(MenuUiSettings.getInstance().getGuiScale()));
/*  543 */     config.addProperty("menuPanoramaPreset", MenuUiSettings.getInstance().getSelectedPanoramaPresetId());
/*  544 */     config.addProperty("customMainMenuEnabled", Boolean.valueOf(MenuUiSettings.getInstance().isCustomMainMenuEnabled()));
/*  545 */     config.addProperty("menuPanoramaSpeedScaleVersion", Integer.valueOf(3));
/*      */ 
/*      */ 
/*      */     
/*  549 */     writeScreenReference(config);
/*  550 */     return config;
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
/*      */   public void saveCurrentConfig() {
/*  581 */     if (this.isLoadingConfig) {
/*      */       return;
/*      */     }
/*  584 */     if (this.currentConfig != null) {
/*  585 */       save(this.currentConfig);
/*  586 */       saveCurrentConfigName();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableAutoSave() {
/*  596 */     this.autoSaveEnabled = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markDirty() {
/*  605 */     if (this.autoSaveEnabled && !this.isLoadingConfig) {
/*  606 */       this.dirtyAt = System.currentTimeMillis();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flushIfDirty() {
/*  617 */     if (this.isLoadingConfig) {
/*      */       return;
/*      */     }
/*  620 */     long ts = this.dirtyAt;
/*  621 */     if (ts == 0L) {
/*      */       return;
/*      */     }
/*  624 */     if (System.currentTimeMillis() - ts < 250L) {
/*      */       return;
/*      */     }
/*  627 */     this.dirtyAt = 0L;
/*  628 */     saveCurrentConfig();
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
/*      */   public void flushNow() {
/*  643 */     if (!this.autoSaveEnabled || this.isLoadingConfig) {
/*      */       return;
/*      */     }
/*  646 */     this.dirtyAt = 0L;
/*  647 */     saveCurrentConfig();
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
/*      */   public void loadConfig(String configName) {
/*  660 */     this.isLoadingConfig = true;
/*      */     try {
/*  662 */       loadConfigInternal(configName);
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  669 */       this.dirtyAt = 0L;
/*  670 */       this.isLoadingConfig = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadConfigInternal(String configName) {
/*  679 */     if (this.currentConfig != null && this.currentConfig.exists() && !this.currentConfigName.equals(configName)) {
/*  680 */       save(this.currentConfig);
/*      */     }
/*      */     
/*  683 */     File configFile = getConfigFile(configName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  690 */     applyInCodeDefaults();
/*      */ 
/*      */ 
/*      */     
/*  694 */     JsonObject config = readConfigFile(configFile);
/*  695 */     File backupFile = new File(configFile.getParentFile(), configFile.getName() + ".bak");
/*  696 */     if (config == null && backupFile.exists()) {
/*  697 */       System.err.println("[Phaze] primary config '" + configFile.getName() + "' unreadable, falling back to .bak");
/*      */       
/*  699 */       config = readConfigFile(backupFile);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  707 */     this.currentConfig = configFile;
/*  708 */     setCurrentConfigName(configName);
/*      */     
/*  710 */     if (config == null) {
/*      */       return;
/*      */     }
/*      */     
/*  714 */     applyConfigJson(config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyConfigJson(JsonObject config) {
/*      */     try {
/*  726 */       this.pendingScreenRefWidth = config.has("screen_w") ? config.get("screen_w").getAsInt() : -1;
/*  727 */       this.pendingScreenRefHeight = config.has("screen_h") ? config.get("screen_h").getAsInt() : -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  734 */       boolean needsScreenReference = (this.pendingScreenRefWidth <= 0 || this.pendingScreenRefHeight <= 0);
/*      */       
/*  736 */       if (config.has("modules")) {
/*  737 */         JsonObject modules = config.getAsJsonObject("modules");
/*  738 */         for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/*      */           try {
/*  740 */             loadModule(module, modules);
/*      */             
/*  742 */             if (needsScreenReference) {
/*  743 */               markDirty();
/*      */             }
/*  745 */           } catch (Throwable t) {
/*  746 */             System.err.println("[Phaze] failed to load module '" + module
/*  747 */                 .getName() + "' from config: " + String.valueOf(t));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  752 */       if (config.has("theme")) {
/*      */         try {
/*  754 */           String theme = config.get("theme").getAsString();
/*  755 */           (Theme.getInstance()).menuTheme.setSelected(theme);
/*  756 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  759 */       if (config.has("blurRadius")) {
/*      */         try {
/*  761 */           float blurRadius = config.get("blurRadius").getAsFloat();
/*  762 */           (Theme.getInstance()).blurRadius.setValue(blurRadius);
/*  763 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  766 */       double panoramaSpeed = 10.0D;
/*  767 */       if (config.has("menuPanoramaSpeed")) {
/*      */         try {
/*  769 */           panoramaSpeed = config.get("menuPanoramaSpeed").getAsDouble();
/*  770 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  773 */       int guiFpsLimit = 60;
/*  774 */       if (config.has("menuGuiFpsLimit")) {
/*      */         try {
/*  776 */           guiFpsLimit = config.get("menuGuiFpsLimit").getAsInt();
/*  777 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  780 */       float guiScale = 1.0F;
/*  781 */       if (config.has("menuGuiScale")) {
/*  782 */         try { guiScale = config.get("menuGuiScale").getAsFloat(); } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  785 */       String panoramaPreset = "vanilla";
/*  786 */       if (config.has("menuPanoramaPreset")) {
/*      */         try {
/*  788 */           panoramaPreset = config.get("menuPanoramaPreset").getAsString();
/*  789 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  792 */       boolean customMainMenuEnabled = true;
/*  793 */       if (config.has("customMainMenuEnabled")) {
/*      */         try {
/*  795 */           customMainMenuEnabled = config.get("customMainMenuEnabled").getAsBoolean();
/*  796 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  799 */       int panoramaSpeedScaleVersion = 1;
/*  800 */       if (config.has("menuPanoramaSpeedScaleVersion")) {
/*      */         try {
/*  802 */           panoramaSpeedScaleVersion = config.get("menuPanoramaSpeedScaleVersion").getAsInt();
/*  803 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  806 */       if (panoramaSpeedScaleVersion >= 3) {
/*  807 */         MenuUiSettings.getInstance().applyConfig(panoramaSpeed, guiFpsLimit, guiScale, panoramaPreset, customMainMenuEnabled);
/*  808 */       } else if (panoramaSpeedScaleVersion == 2) {
/*  809 */         MenuUiSettings.getInstance().applyLegacyScaleV2Config(panoramaSpeed, guiFpsLimit, panoramaPreset, customMainMenuEnabled);
/*      */       } else {
/*  811 */         MenuUiSettings.getInstance().applyLegacyScaleV1Config(panoramaSpeed, guiFpsLimit, panoramaPreset, customMainMenuEnabled);
/*      */       } 
/*  813 */     } catch (Throwable t) {
/*  814 */       t.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadConfigInternalLegacyApply(JsonObject config) {
/*      */     try {
/*  820 */       this.pendingScreenRefWidth = config.has("screen_w") ? config.get("screen_w").getAsInt() : -1;
/*  821 */       this.pendingScreenRefHeight = config.has("screen_h") ? config.get("screen_h").getAsInt() : -1;
/*      */       
/*  823 */       if (config.has("modules")) {
/*  824 */         JsonObject modules = config.getAsJsonObject("modules");
/*  825 */         for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/*  830 */             loadModule(module, modules);
/*  831 */           } catch (Throwable t) {
/*  832 */             System.err.println("[Phaze] failed to load module '" + module
/*  833 */                 .getName() + "' from config: " + String.valueOf(t));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  838 */       if (config.has("theme")) {
/*      */         try {
/*  840 */           String theme = config.get("theme").getAsString();
/*  841 */           (Theme.getInstance()).menuTheme.setSelected(theme);
/*  842 */         } catch (Throwable throwable) {}
/*      */       }
/*      */       
/*  845 */       if (config.has("blurRadius")) {
/*      */         try {
/*  847 */           float blurRadius = config.get("blurRadius").getAsFloat();
/*  848 */           (Theme.getInstance()).blurRadius.setValue(blurRadius);
/*  849 */         } catch (Throwable throwable) {}
/*      */       }
/*  851 */     } catch (Throwable t) {
/*      */ 
/*      */ 
/*      */       
/*  855 */       t.printStackTrace();
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
/*      */   private void applyInCodeDefaults() {
/*  876 */     for (Module module : Main.getInstance().getModuleProvider().getModules()) {
/*      */ 
/*      */       
/*  879 */       if (module.isState() != module.isEnabledByDefault()) {
/*  880 */         module.setState(module.isEnabledByDefault());
/*      */       }
/*  882 */       module.setKey(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  889 */       resetSettingsRecursive(module.settings());
/*  890 */       if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/*  891 */         rectHudModule.resetHudTransform(); continue; }
/*  892 */        if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/*  893 */         armorHud.resetHudTransform(); }
/*      */     
/*      */     } 
/*      */     
/*  897 */     (Theme.getInstance()).menuTheme.setSelected("Lunar Blue");
/*  898 */     (Theme.getInstance()).blurRadius.setValue(5.0F);
/*  899 */     MenuUiSettings.getInstance().resetToDefaults();
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
/*      */   private void resetSettingsRecursive(List<? extends Setting> settings) {
/*  911 */     if (settings == null)
/*  912 */       return;  for (Setting setting : settings) {
/*      */       try {
/*  914 */         setting.reset();
/*  915 */         if (setting instanceof GroupSetting) { GroupSetting group = (GroupSetting)setting;
/*  916 */           resetSettingsRecursive(group.getSubSettings()); }
/*      */       
/*  918 */       } catch (Throwable throwable) {}
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
/*      */   private static void writeScreenReference(JsonObject config) {
/*  941 */     class_310 client = class_310.method_1551();
/*  942 */     if (client == null || client.method_22683() == null) {
/*      */       return;
/*      */     }
/*  945 */     int width = client.method_22683().method_4486();
/*  946 */     int height = client.method_22683().method_4502();
/*  947 */     if (width > 0 && height > 0) {
/*  948 */       config.addProperty("screen_w", Integer.valueOf(width));
/*  949 */       config.addProperty("screen_h", Integer.valueOf(height));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ConfigManager() {
/*  960 */     this.pendingScreenRefWidth = -1;
/*  961 */     this.pendingScreenRefHeight = -1; File minecraftDir = (class_310.method_1551()).field_1697; this.configDir = new File(minecraftDir, "Phaze"); this.configsDir = new File(this.configDir, "configs"); this.filesDir = new File(this.configDir, "files"); this.currentConfigFile = new File(this.filesDir, "current_config.json");
/*      */     initDirectories();
/*      */     loadCurrentConfigName();
/*  964 */     this.currentConfig = getConfigFile(this.currentConfigName); } private JsonObject readConfigFile(File file) { if (file == null || !file.exists())
/*  965 */       return null; 
/*      */     
/*  967 */     try { Reader reader = new FileReader(file); 
/*  968 */       try { JsonObject parsed = (JsonObject)GSON.fromJson(reader, JsonObject.class);
/*  969 */         JsonObject jsonObject1 = parsed;
/*  970 */         reader.close(); return jsonObject1; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (JsonSyntaxException|IOException e)
/*      */     
/*      */     { 
/*      */       
/*  974 */       System.err.println("[Phaze] could not parse config file '" + file
/*  975 */           .getAbsolutePath() + "': " + String.valueOf(e));
/*  976 */       return null; }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadModule(Module module, JsonObject modules) {
/*  990 */     boolean hasModuleData = (modules.has(module.getName()) && modules.get(module.getName()).isJsonObject());
/*  991 */     JsonObject moduleData = hasModuleData ? modules.getAsJsonObject(module.getName()) : null;
/*  992 */     if (hasModuleData && module instanceof BossBarHud) {
/*  993 */       moduleData = BossBarHud.migrateLegacyData(moduleData);
/*      */     }
/*      */     
/*  996 */     if (module.isShowEnable()) {
/*      */ 
/*      */       
/*  999 */       boolean enabled = (hasModuleData && moduleData.has("enabled")) ? moduleData.get("enabled").getAsBoolean() : module.isEnabledByDefault();
/* 1000 */       module.setState(enabled);
/*      */     } 
/*      */     
/* 1003 */     if (hasModuleData && moduleData.has("key")) {
/* 1004 */       module.setKey(moduleData.get("key").getAsInt());
/*      */     }
/*      */     
/* 1007 */     if (hasModuleData && moduleData.has("settings") && moduleData.get("settings").isJsonObject()) {
/* 1008 */       JsonObject settings = moduleData.getAsJsonObject("settings");
/* 1009 */       if (module instanceof LowHealthWarning) {
/* 1010 */         settings = LowHealthWarning.migrateLegacySettings(settings);
/*      */       }
/* 1012 */       for (Setting setting : module.settings()) {
/*      */         try {
/* 1014 */           deserializeSetting(setting, settings);
/* 1015 */         } catch (Exception exception) {}
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     if (module instanceof RectHudModule) { RectHudModule rectHudModule = (RectHudModule)module;
/* 1023 */       if (!hasModuleData) {
/* 1024 */         rectHudModule.resetHudTransform();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1040 */         if (moduleData.has("hud_x_ratio") && moduleData.has("hud_y_ratio")) {
/* 1041 */           rectHudModule.setHudPositionRatio(moduleData
/* 1042 */               .get("hud_x_ratio").getAsFloat(), moduleData
/* 1043 */               .get("hud_y_ratio").getAsFloat());
/* 1044 */         } else if (moduleData.has("hud_x") && moduleData.has("hud_y") && moduleData
/* 1045 */           .has("hud_ref_w") && moduleData.has("hud_ref_h")) {
/* 1046 */           rectHudModule.setHudPosition(moduleData
/* 1047 */               .get("hud_x").getAsFloat(), moduleData
/* 1048 */               .get("hud_y").getAsFloat(), moduleData
/* 1049 */               .get("hud_ref_w").getAsInt(), moduleData
/* 1050 */               .get("hud_ref_h").getAsInt());
/* 1051 */         } else if (moduleData.has("hud_x") && moduleData.has("hud_y")) {
/*      */ 
/*      */ 
/*      */           
/* 1055 */           rectHudModule.setHudPosition(moduleData
/* 1056 */               .get("hud_x").getAsFloat(), moduleData
/* 1057 */               .get("hud_y").getAsFloat(), this.pendingScreenRefWidth, this.pendingScreenRefHeight);
/*      */         } else {
/*      */           
/* 1060 */           rectHudModule.resetHudTransform();
/*      */         } 
/* 1062 */         if (moduleData.has("hud_scale")) {
/* 1063 */           rectHudModule.setHudScale(moduleData.get("hud_scale").getAsFloat());
/*      */         }
/* 1065 */         if (rectHudModule instanceof BossBarHud) { BossBarHud bossBar = (BossBarHud)rectHudModule; if (moduleData.has("hud_auto_position") && moduleData
/* 1066 */             .get("hud_auto_position").getAsBoolean())
/* 1067 */             bossBar.resetAutomaticPosition();  }
/*      */       
/*      */       }  }
/* 1070 */     else if (module instanceof ArmorHud) { ArmorHud armorHud = (ArmorHud)module;
/* 1071 */       if (!hasModuleData) {
/* 1072 */         armorHud.resetHudTransform();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1088 */         if (moduleData.has("hud_x_ratio") && moduleData.has("hud_y_ratio")) {
/* 1089 */           armorHud.setHudPositionRatio(moduleData
/* 1090 */               .get("hud_x_ratio").getAsFloat(), moduleData
/* 1091 */               .get("hud_y_ratio").getAsFloat());
/* 1092 */         } else if (moduleData.has("hud_x") && moduleData.has("hud_y") && moduleData
/* 1093 */           .has("hud_ref_w") && moduleData.has("hud_ref_h")) {
/* 1094 */           armorHud.setHudPosition(moduleData
/* 1095 */               .get("hud_x").getAsFloat(), moduleData
/* 1096 */               .get("hud_y").getAsFloat(), moduleData
/* 1097 */               .get("hud_ref_w").getAsInt(), moduleData
/* 1098 */               .get("hud_ref_h").getAsInt());
/* 1099 */         } else if (moduleData.has("hud_x") && moduleData.has("hud_y")) {
/*      */ 
/*      */ 
/*      */           
/* 1103 */           armorHud.setHudPosition(moduleData
/* 1104 */               .get("hud_x").getAsFloat(), moduleData
/* 1105 */               .get("hud_y").getAsFloat(), this.pendingScreenRefWidth, this.pendingScreenRefHeight);
/*      */         } else {
/*      */           
/* 1108 */           armorHud.resetHudTransform();
/*      */         } 
/* 1110 */         if (moduleData.has("hud_scale")) {
/* 1111 */           armorHud.setHudScale(moduleData.get("hud_scale").getAsFloat());
/*      */         }
/*      */       }  }
/*      */   
/*      */   }
/*      */   
/*      */   public void loadCurrentConfig() {
/* 1118 */     loadConfig(this.currentConfigName);
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
/*      */   public String[] getConfigList() {
/* 1134 */     File[] files = this.configsDir.listFiles((dir, name) -> name.endsWith(".Phaze"));
/*      */     
/* 1136 */     Set<String> uniqueNames = new LinkedHashSet<>();
/*      */ 
/*      */ 
/*      */     
/* 1140 */     uniqueNames.add("default");
/* 1141 */     if (files != null) {
/* 1142 */       for (File file : files) {
/* 1143 */         uniqueNames.add(file.getName().replace(".Phaze", ""));
/*      */       }
/*      */     }
/*      */     
/* 1147 */     String[] configs = uniqueNames.<String>toArray(new String[0]);
/* 1148 */     Arrays.sort(configs, CONFIG_LIST_ORDER);
/* 1149 */     return configs;
/*      */   }
/*      */   
/*      */   static {
/* 1153 */     CONFIG_LIST_ORDER = ((a, b) -> {
/*      */         int aRank = pinnedRank(a);
/*      */         int bRank = pinnedRank(b);
/*      */         return (aRank != bRank) ? Integer.compare(aRank, bRank) : a.compareToIgnoreCase(b);
/*      */       });
/*      */   }
/*      */   
/*      */   private static int pinnedRank(String name) {
/* 1161 */     if ("default".equalsIgnoreCase(name)) return 0; 
/* 1162 */     if ("autosave".equalsIgnoreCase(name)) return 1; 
/* 1163 */     return 2;
/*      */   }
/*      */   
/*      */   public boolean configExists(String configName) {
/* 1167 */     return getConfigFile(configName).exists();
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
/*      */   public boolean isImportedConfig(String configName) {
/* 1179 */     File f = getConfigFile(configName);
/* 1180 */     if (!f.exists()) return false;  
/* 1181 */     try { Reader r = new FileReader(f); 
/* 1182 */       try { JsonObject obj = (JsonObject)GSON.fromJson(r, JsonObject.class);
/*      */ 
/*      */         
/* 1185 */         boolean bool = (obj != null && obj.has("imported") && obj.get("imported").getAsBoolean()) ? true : false;
/* 1186 */         r.close(); return bool; } catch (Throwable throwable) { try { r.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable ignored)
/* 1187 */     { return false; }
/*      */   
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
/*      */   public void deleteConfig(String configName) {
/* 1204 */     if (configName == null || configName.isEmpty())
/*      */       return; 
/* 1206 */     File configFile = getConfigFile(configName);
/* 1207 */     File backupFile = new File(configFile.getParentFile(), configFile.getName() + ".bak");
/* 1208 */     boolean wasActive = this.currentConfigName.equalsIgnoreCase(configName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1217 */     if (wasActive) {
/* 1218 */       this.currentConfig = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1228 */       Files.deleteIfExists(configFile.toPath());
/* 1229 */     } catch (IOException e) {
/* 1230 */       System.err.println("[Phaze] Failed to delete config '" + configFile
/* 1231 */           .getAbsolutePath() + "': " + e.getMessage());
/*      */     } 
/*      */     try {
/* 1234 */       Files.deleteIfExists(backupFile.toPath());
/* 1235 */     } catch (IOException e) {
/* 1236 */       System.err.println("[Phaze] Failed to delete .bak '" + backupFile
/* 1237 */           .getAbsolutePath() + "': " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1243 */     if (wasActive) {
/* 1244 */       String fallback = "default";
/*      */ 
/*      */ 
/*      */       
/* 1248 */       if (!getConfigFile(fallback).exists()) {
/* 1249 */         String[] remaining = getConfigList();
/* 1250 */         for (String name : remaining) {
/* 1251 */           if (!name.equalsIgnoreCase(configName)) {
/* 1252 */             fallback = name;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1257 */       loadConfig(fallback);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renameConfig(String oldName, String newName) {
/* 1262 */     File oldFile = getConfigFile(oldName);
/* 1263 */     File newFile = getConfigFile(newName);
/* 1264 */     if (oldFile.exists()) {
/* 1265 */       oldFile.renameTo(newFile);
/*      */     }
/*      */   }
/*      */   
/*      */   public String createNewConfig() {
/* 1270 */     String name = nextNewConfigName();
/* 1271 */     saveConfig(name);
/* 1272 */     loadConfig(name);
/* 1273 */     return name;
/*      */   }
/*      */   
/*      */   private String nextNewConfigName() {
/* 1277 */     int index = 1;
/*      */     while (true) {
/* 1279 */       String name = "new-config" + index;
/* 1280 */       if (!configExists(name)) {
/* 1281 */         return name;
/*      */       }
/* 1283 */       index++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void serializeSetting(Setting setting, JsonObject target) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ifnull -> 11
/*      */     //   4: aload_0
/*      */     //   5: invokevirtual isSaveToConfig : ()Z
/*      */     //   8: ifne -> 12
/*      */     //   11: return
/*      */     //   12: aload_0
/*      */     //   13: invokevirtual getRawName : ()Ljava/lang/String;
/*      */     //   16: astore_2
/*      */     //   17: aload_0
/*      */     //   18: dup
/*      */     //   19: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   22: pop
/*      */     //   23: astore_3
/*      */     //   24: iconst_0
/*      */     //   25: istore #4
/*      */     //   27: aload_3
/*      */     //   28: iload #4
/*      */     //   30: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*      */     //   35: tableswitch default -> 617, 0 -> 88, 1 -> 110, 2 -> 132, 3 -> 182, 4 -> 204, 5 -> 226, 6 -> 245, 7 -> 285, 8 -> 422, 9 -> 532
/*      */     //   88: aload_3
/*      */     //   89: checkcast vorga/phazeclient/api/feature/module/setting/implement/BooleanSetting
/*      */     //   92: astore #5
/*      */     //   94: aload_1
/*      */     //   95: aload_2
/*      */     //   96: aload #5
/*      */     //   98: invokevirtual isValue : ()Z
/*      */     //   101: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */     //   104: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*      */     //   107: goto -> 617
/*      */     //   110: aload_3
/*      */     //   111: checkcast vorga/phazeclient/api/feature/module/setting/implement/ValueSetting
/*      */     //   114: astore #6
/*      */     //   116: aload_1
/*      */     //   117: aload_2
/*      */     //   118: aload #6
/*      */     //   120: invokevirtual getValue : ()F
/*      */     //   123: invokestatic valueOf : (F)Ljava/lang/Float;
/*      */     //   126: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*      */     //   129: goto -> 617
/*      */     //   132: aload_3
/*      */     //   133: checkcast vorga/phazeclient/api/feature/module/setting/implement/TextSetting
/*      */     //   136: astore #7
/*      */     //   138: aload #7
/*      */     //   140: invokevirtual getText : ()Ljava/lang/String;
/*      */     //   143: astore #8
/*      */     //   145: aload #8
/*      */     //   147: ifnull -> 172
/*      */     //   150: aload #8
/*      */     //   152: ldc_w ' '
/*      */     //   155: ldc_w '%%'
/*      */     //   158: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*      */     //   161: ldc_w '/'
/*      */     //   164: ldc_w '++'
/*      */     //   167: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*      */     //   170: astore #8
/*      */     //   172: aload_1
/*      */     //   173: aload_2
/*      */     //   174: aload #8
/*      */     //   176: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   179: goto -> 617
/*      */     //   182: aload_3
/*      */     //   183: checkcast vorga/phazeclient/api/feature/module/setting/implement/BindSetting
/*      */     //   186: astore #8
/*      */     //   188: aload_1
/*      */     //   189: aload_2
/*      */     //   190: aload #8
/*      */     //   192: invokevirtual getKey : ()I
/*      */     //   195: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   198: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*      */     //   201: goto -> 617
/*      */     //   204: aload_3
/*      */     //   205: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*      */     //   208: astore #9
/*      */     //   210: aload_1
/*      */     //   211: aload_2
/*      */     //   212: aload #9
/*      */     //   214: invokevirtual getColor : ()I
/*      */     //   217: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   220: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*      */     //   223: goto -> 617
/*      */     //   226: aload_3
/*      */     //   227: checkcast vorga/phazeclient/api/feature/module/setting/implement/SelectSetting
/*      */     //   230: astore #10
/*      */     //   232: aload_1
/*      */     //   233: aload_2
/*      */     //   234: aload #10
/*      */     //   236: invokevirtual getSelected : ()Ljava/lang/String;
/*      */     //   239: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   242: goto -> 617
/*      */     //   245: aload_3
/*      */     //   246: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting
/*      */     //   249: astore #11
/*      */     //   251: aload #11
/*      */     //   253: invokevirtual getSelected : ()Ljava/util/List;
/*      */     //   256: astore #12
/*      */     //   258: aload_1
/*      */     //   259: aload_2
/*      */     //   260: aload #12
/*      */     //   262: ifnonnull -> 271
/*      */     //   265: ldc_w ''
/*      */     //   268: goto -> 279
/*      */     //   271: ldc_w ','
/*      */     //   274: aload #12
/*      */     //   276: invokestatic join : (Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
/*      */     //   279: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   282: goto -> 617
/*      */     //   285: aload_3
/*      */     //   286: checkcast vorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting
/*      */     //   289: astore #12
/*      */     //   291: aload #12
/*      */     //   293: invokevirtual isCustomPicker : ()Z
/*      */     //   296: ifeq -> 317
/*      */     //   299: aload #12
/*      */     //   301: invokevirtual isActive : ()Z
/*      */     //   304: ifne -> 317
/*      */     //   307: aload_1
/*      */     //   308: aload_2
/*      */     //   309: iconst_0
/*      */     //   310: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */     //   313: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*      */     //   316: return
/*      */     //   317: new com/google/gson/JsonObject
/*      */     //   320: dup
/*      */     //   321: invokespecial <init> : ()V
/*      */     //   324: astore #13
/*      */     //   326: aload #13
/*      */     //   328: ldc_w 'active'
/*      */     //   331: aload #12
/*      */     //   333: invokevirtual isActive : ()Z
/*      */     //   336: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */     //   339: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*      */     //   342: aload #13
/*      */     //   344: ldc 'enabled'
/*      */     //   346: aload #12
/*      */     //   348: invokevirtual isEnabled : ()Z
/*      */     //   351: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */     //   354: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*      */     //   357: aload #13
/*      */     //   359: ldc_w 'highlightColor'
/*      */     //   362: aload #12
/*      */     //   364: invokevirtual getHighlightColor : ()I
/*      */     //   367: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   370: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*      */     //   373: aload #13
/*      */     //   375: ldc_w 'itemId'
/*      */     //   378: aload #12
/*      */     //   380: invokevirtual getItemId : ()Ljava/lang/String;
/*      */     //   383: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   386: aload #13
/*      */     //   388: ldc_w 'displayName'
/*      */     //   391: aload #12
/*      */     //   393: invokevirtual getDisplayName : ()Ljava/lang/String;
/*      */     //   396: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   399: aload #13
/*      */     //   401: ldc_w 'matchName'
/*      */     //   404: aload #12
/*      */     //   406: invokevirtual getMatchName : ()Ljava/lang/String;
/*      */     //   409: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   412: aload_1
/*      */     //   413: aload_2
/*      */     //   414: aload #13
/*      */     //   416: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*      */     //   419: goto -> 617
/*      */     //   422: aload_3
/*      */     //   423: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting
/*      */     //   426: astore #13
/*      */     //   428: new com/google/gson/JsonObject
/*      */     //   431: dup
/*      */     //   432: invokespecial <init> : ()V
/*      */     //   435: astore #14
/*      */     //   437: aload #14
/*      */     //   439: ldc_w 'selectedColorIndex'
/*      */     //   442: aload #13
/*      */     //   444: invokevirtual getSelectedColorIndex : ()I
/*      */     //   447: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   450: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Number;)V
/*      */     //   453: new com/google/gson/JsonArray
/*      */     //   456: dup
/*      */     //   457: invokespecial <init> : ()V
/*      */     //   460: astore #15
/*      */     //   462: aload #13
/*      */     //   464: invokevirtual getAllColors : ()Ljava/util/List;
/*      */     //   467: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   472: astore #16
/*      */     //   474: aload #16
/*      */     //   476: invokeinterface hasNext : ()Z
/*      */     //   481: ifeq -> 512
/*      */     //   484: aload #16
/*      */     //   486: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   491: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*      */     //   494: astore #17
/*      */     //   496: aload #15
/*      */     //   498: aload #17
/*      */     //   500: invokevirtual getColor : ()I
/*      */     //   503: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   506: invokevirtual add : (Ljava/lang/Number;)V
/*      */     //   509: goto -> 474
/*      */     //   512: aload #14
/*      */     //   514: ldc_w 'colors'
/*      */     //   517: aload #15
/*      */     //   519: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*      */     //   522: aload_1
/*      */     //   523: aload_2
/*      */     //   524: aload #14
/*      */     //   526: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*      */     //   529: goto -> 617
/*      */     //   532: aload_3
/*      */     //   533: checkcast vorga/phazeclient/api/feature/module/setting/implement/GroupSetting
/*      */     //   536: astore #14
/*      */     //   538: new com/google/gson/JsonObject
/*      */     //   541: dup
/*      */     //   542: invokespecial <init> : ()V
/*      */     //   545: astore #15
/*      */     //   547: aload #15
/*      */     //   549: ldc_w 'state'
/*      */     //   552: aload #14
/*      */     //   554: invokevirtual isValue : ()Z
/*      */     //   557: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */     //   560: invokevirtual addProperty : (Ljava/lang/String;Ljava/lang/Boolean;)V
/*      */     //   563: aload #14
/*      */     //   565: invokevirtual getSubSettings : ()Ljava/util/List;
/*      */     //   568: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   573: astore #16
/*      */     //   575: aload #16
/*      */     //   577: invokeinterface hasNext : ()Z
/*      */     //   582: ifeq -> 607
/*      */     //   585: aload #16
/*      */     //   587: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   592: checkcast vorga/phazeclient/api/feature/module/setting/Setting
/*      */     //   595: astore #17
/*      */     //   597: aload #17
/*      */     //   599: aload #15
/*      */     //   601: invokestatic serializeSetting : (Lvorga/phazeclient/api/feature/module/setting/Setting;Lcom/google/gson/JsonObject;)V
/*      */     //   604: goto -> 575
/*      */     //   607: aload_1
/*      */     //   608: aload_2
/*      */     //   609: aload #15
/*      */     //   611: invokevirtual add : (Ljava/lang/String;Lcom/google/gson/JsonElement;)V
/*      */     //   614: goto -> 617
/*      */     //   617: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1303	-> 0
/*      */     //   #1304	-> 11
/*      */     //   #1311	-> 12
/*      */     //   #1313	-> 17
/*      */     //   #1314	-> 88
/*      */     //   #1315	-> 110
/*      */     //   #1316	-> 132
/*      */     //   #1317	-> 138
/*      */     //   #1318	-> 145
/*      */     //   #1322	-> 150
/*      */     //   #1324	-> 172
/*      */     //   #1325	-> 179
/*      */     //   #1326	-> 182
/*      */     //   #1327	-> 204
/*      */     //   #1328	-> 226
/*      */     //   #1329	-> 245
/*      */     //   #1330	-> 251
/*      */     //   #1331	-> 258
/*      */     //   #1332	-> 282
/*      */     //   #1333	-> 285
/*      */     //   #1334	-> 291
/*      */     //   #1335	-> 307
/*      */     //   #1336	-> 316
/*      */     //   #1338	-> 317
/*      */     //   #1339	-> 326
/*      */     //   #1340	-> 342
/*      */     //   #1341	-> 357
/*      */     //   #1342	-> 373
/*      */     //   #1343	-> 386
/*      */     //   #1344	-> 399
/*      */     //   #1345	-> 412
/*      */     //   #1346	-> 419
/*      */     //   #1347	-> 422
/*      */     //   #1348	-> 428
/*      */     //   #1349	-> 437
/*      */     //   #1351	-> 453
/*      */     //   #1352	-> 462
/*      */     //   #1353	-> 496
/*      */     //   #1354	-> 509
/*      */     //   #1355	-> 512
/*      */     //   #1356	-> 522
/*      */     //   #1357	-> 529
/*      */     //   #1358	-> 532
/*      */     //   #1359	-> 538
/*      */     //   #1360	-> 547
/*      */     //   #1361	-> 563
/*      */     //   #1362	-> 597
/*      */     //   #1363	-> 604
/*      */     //   #1364	-> 607
/*      */     //   #1365	-> 614
/*      */     //   #1371	-> 617
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   94	16	5	booleanSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*      */     //   116	16	6	valueSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*      */     //   145	34	8	value	Ljava/lang/String;
/*      */     //   138	44	7	textSetting	Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*      */     //   188	16	8	bindSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*      */     //   210	16	9	colorSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   232	13	10	selectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/SelectSetting;
/*      */     //   258	24	12	selected	Ljava/util/List;
/*      */     //   251	34	11	multiSelectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;
/*      */     //   326	93	13	itemObject	Lcom/google/gson/JsonObject;
/*      */     //   291	131	12	itemPickerSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting;
/*      */     //   496	13	17	color	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   437	92	14	colorObject	Lcom/google/gson/JsonObject;
/*      */     //   462	67	15	colorsArray	Lcom/google/gson/JsonArray;
/*      */     //   428	104	13	multiColor	Lvorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting;
/*      */     //   597	7	17	subSetting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*      */     //   547	67	15	groupObject	Lcom/google/gson/JsonObject;
/*      */     //   538	79	14	group	Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*      */     //   0	618	0	setting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*      */     //   0	618	1	target	Lcom/google/gson/JsonObject;
/*      */     //   17	601	2	key	Ljava/lang/String;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   258	24	12	selected	Ljava/util/List<Ljava/lang/String;>;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1385 */   private static final Map<String, String[]> LEGACY_KEY_ALIASES = (Map)Map.of("Message Animation", new String[] { "Chat Smooth Scroll" }, "Message Animation Speed", new String[] { "Chat Scroll Speed" });
/*      */   
/*      */   private static void deserializeSetting(Setting setting, JsonObject source) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ifnull -> 11
/*      */     //   4: aload_0
/*      */     //   5: invokevirtual isSaveToConfig : ()Z
/*      */     //   8: ifne -> 12
/*      */     //   11: return
/*      */     //   12: aload_0
/*      */     //   13: invokevirtual getRawName : ()Ljava/lang/String;
/*      */     //   16: astore_2
/*      */     //   17: aload_1
/*      */     //   18: aload_2
/*      */     //   19: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   22: ifne -> 97
/*      */     //   25: getstatic vorga/phazeclient/implement/config/ConfigManager.LEGACY_KEY_ALIASES : Ljava/util/Map;
/*      */     //   28: aload_2
/*      */     //   29: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   34: checkcast [Ljava/lang/String;
/*      */     //   37: astore_3
/*      */     //   38: aload_3
/*      */     //   39: ifnull -> 88
/*      */     //   42: aload_3
/*      */     //   43: astore #4
/*      */     //   45: aload #4
/*      */     //   47: arraylength
/*      */     //   48: istore #5
/*      */     //   50: iconst_0
/*      */     //   51: istore #6
/*      */     //   53: iload #6
/*      */     //   55: iload #5
/*      */     //   57: if_icmpge -> 88
/*      */     //   60: aload #4
/*      */     //   62: iload #6
/*      */     //   64: aaload
/*      */     //   65: astore #7
/*      */     //   67: aload_1
/*      */     //   68: aload #7
/*      */     //   70: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   73: ifeq -> 82
/*      */     //   76: aload #7
/*      */     //   78: astore_2
/*      */     //   79: goto -> 88
/*      */     //   82: iinc #6, 1
/*      */     //   85: goto -> 53
/*      */     //   88: aload_1
/*      */     //   89: aload_2
/*      */     //   90: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   93: ifne -> 97
/*      */     //   96: return
/*      */     //   97: aload_1
/*      */     //   98: aload_2
/*      */     //   99: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   102: astore_3
/*      */     //   103: aload_3
/*      */     //   104: ifnull -> 114
/*      */     //   107: aload_3
/*      */     //   108: invokevirtual isJsonNull : ()Z
/*      */     //   111: ifeq -> 115
/*      */     //   114: return
/*      */     //   115: aload_0
/*      */     //   116: dup
/*      */     //   117: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   120: pop
/*      */     //   121: astore #4
/*      */     //   123: iconst_0
/*      */     //   124: istore #5
/*      */     //   126: aload #4
/*      */     //   128: iload #5
/*      */     //   130: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*      */     //   135: tableswitch default -> 922, 0 -> 188, 1 -> 208, 2 -> 228, 3 -> 279, 4 -> 299, 5 -> 319, 6 -> 338, 7 -> 396, 8 -> 656, 9 -> 829
/*      */     //   188: aload #4
/*      */     //   190: checkcast vorga/phazeclient/api/feature/module/setting/implement/BooleanSetting
/*      */     //   193: astore #6
/*      */     //   195: aload #6
/*      */     //   197: aload_3
/*      */     //   198: invokevirtual getAsBoolean : ()Z
/*      */     //   201: invokevirtual setValue : (Z)Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*      */     //   204: pop
/*      */     //   205: goto -> 922
/*      */     //   208: aload #4
/*      */     //   210: checkcast vorga/phazeclient/api/feature/module/setting/implement/ValueSetting
/*      */     //   213: astore #7
/*      */     //   215: aload #7
/*      */     //   217: aload_3
/*      */     //   218: invokevirtual getAsFloat : ()F
/*      */     //   221: invokevirtual setValue : (F)Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*      */     //   224: pop
/*      */     //   225: goto -> 922
/*      */     //   228: aload #4
/*      */     //   230: checkcast vorga/phazeclient/api/feature/module/setting/implement/TextSetting
/*      */     //   233: astore #8
/*      */     //   235: aload_3
/*      */     //   236: invokevirtual getAsString : ()Ljava/lang/String;
/*      */     //   239: astore #9
/*      */     //   241: aload #9
/*      */     //   243: ifnull -> 268
/*      */     //   246: aload #9
/*      */     //   248: ldc_w '%%'
/*      */     //   251: ldc_w ' '
/*      */     //   254: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*      */     //   257: ldc_w '++'
/*      */     //   260: ldc_w '/'
/*      */     //   263: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*      */     //   266: astore #9
/*      */     //   268: aload #8
/*      */     //   270: aload #9
/*      */     //   272: invokevirtual setText : (Ljava/lang/String;)Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*      */     //   275: pop
/*      */     //   276: goto -> 922
/*      */     //   279: aload #4
/*      */     //   281: checkcast vorga/phazeclient/api/feature/module/setting/implement/BindSetting
/*      */     //   284: astore #9
/*      */     //   286: aload #9
/*      */     //   288: aload_3
/*      */     //   289: invokevirtual getAsInt : ()I
/*      */     //   292: invokevirtual setKey : (I)Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*      */     //   295: pop
/*      */     //   296: goto -> 922
/*      */     //   299: aload #4
/*      */     //   301: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*      */     //   304: astore #10
/*      */     //   306: aload #10
/*      */     //   308: aload_3
/*      */     //   309: invokevirtual getAsInt : ()I
/*      */     //   312: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   315: pop
/*      */     //   316: goto -> 922
/*      */     //   319: aload #4
/*      */     //   321: checkcast vorga/phazeclient/api/feature/module/setting/implement/SelectSetting
/*      */     //   324: astore #11
/*      */     //   326: aload #11
/*      */     //   328: aload_3
/*      */     //   329: invokevirtual getAsString : ()Ljava/lang/String;
/*      */     //   332: invokevirtual setSelected : (Ljava/lang/String;)V
/*      */     //   335: goto -> 922
/*      */     //   338: aload #4
/*      */     //   340: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting
/*      */     //   343: astore #12
/*      */     //   345: aload_3
/*      */     //   346: invokevirtual getAsString : ()Ljava/lang/String;
/*      */     //   349: astore #13
/*      */     //   351: new java/util/ArrayList
/*      */     //   354: dup
/*      */     //   355: aload #13
/*      */     //   357: ldc_w ','
/*      */     //   360: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
/*      */     //   363: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
/*      */     //   366: invokespecial <init> : (Ljava/util/Collection;)V
/*      */     //   369: astore #14
/*      */     //   371: aload #14
/*      */     //   373: aload #12
/*      */     //   375: <illegal opcode> test : (Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;)Ljava/util/function/Predicate;
/*      */     //   380: invokeinterface removeIf : (Ljava/util/function/Predicate;)Z
/*      */     //   385: pop
/*      */     //   386: aload #12
/*      */     //   388: aload #14
/*      */     //   390: invokevirtual setSelected : (Ljava/util/List;)V
/*      */     //   393: goto -> 922
/*      */     //   396: aload #4
/*      */     //   398: checkcast vorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting
/*      */     //   401: astore #13
/*      */     //   403: aload_3
/*      */     //   404: invokevirtual isJsonPrimitive : ()Z
/*      */     //   407: ifeq -> 430
/*      */     //   410: aload_3
/*      */     //   411: invokevirtual getAsJsonPrimitive : ()Lcom/google/gson/JsonPrimitive;
/*      */     //   414: invokevirtual isBoolean : ()Z
/*      */     //   417: ifeq -> 430
/*      */     //   420: aload #13
/*      */     //   422: aload_3
/*      */     //   423: invokevirtual getAsBoolean : ()Z
/*      */     //   426: invokevirtual setEnabled : (Z)V
/*      */     //   429: return
/*      */     //   430: aload_3
/*      */     //   431: invokevirtual isJsonObject : ()Z
/*      */     //   434: ifne -> 438
/*      */     //   437: return
/*      */     //   438: aload_3
/*      */     //   439: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*      */     //   442: astore #14
/*      */     //   444: aload #14
/*      */     //   446: ldc_w 'itemId'
/*      */     //   449: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   452: ifeq -> 469
/*      */     //   455: aload #14
/*      */     //   457: ldc_w 'itemId'
/*      */     //   460: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   463: invokevirtual getAsString : ()Ljava/lang/String;
/*      */     //   466: goto -> 472
/*      */     //   469: ldc_w ''
/*      */     //   472: astore #15
/*      */     //   474: aload #14
/*      */     //   476: ldc_w 'displayName'
/*      */     //   479: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   482: ifeq -> 499
/*      */     //   485: aload #14
/*      */     //   487: ldc_w 'displayName'
/*      */     //   490: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   493: invokevirtual getAsString : ()Ljava/lang/String;
/*      */     //   496: goto -> 502
/*      */     //   499: ldc_w ''
/*      */     //   502: astore #16
/*      */     //   504: aload #14
/*      */     //   506: ldc_w 'matchName'
/*      */     //   509: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   512: ifeq -> 529
/*      */     //   515: aload #14
/*      */     //   517: ldc_w 'matchName'
/*      */     //   520: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   523: invokevirtual getAsString : ()Ljava/lang/String;
/*      */     //   526: goto -> 532
/*      */     //   529: ldc_w ''
/*      */     //   532: astore #17
/*      */     //   534: aload #14
/*      */     //   536: ldc 'enabled'
/*      */     //   538: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   541: ifeq -> 557
/*      */     //   544: aload #14
/*      */     //   546: ldc 'enabled'
/*      */     //   548: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   551: invokevirtual getAsBoolean : ()Z
/*      */     //   554: goto -> 562
/*      */     //   557: aload #13
/*      */     //   559: invokevirtual isEnabled : ()Z
/*      */     //   562: istore #18
/*      */     //   564: aload #14
/*      */     //   566: ldc_w 'highlightColor'
/*      */     //   569: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   572: ifeq -> 589
/*      */     //   575: aload #14
/*      */     //   577: ldc_w 'highlightColor'
/*      */     //   580: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   583: invokevirtual getAsInt : ()I
/*      */     //   586: goto -> 594
/*      */     //   589: aload #13
/*      */     //   591: invokevirtual getHighlightColor : ()I
/*      */     //   594: istore #19
/*      */     //   596: aload #14
/*      */     //   598: ldc_w 'active'
/*      */     //   601: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   604: ifeq -> 621
/*      */     //   607: aload #14
/*      */     //   609: ldc_w 'active'
/*      */     //   612: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   615: invokevirtual getAsBoolean : ()Z
/*      */     //   618: goto -> 634
/*      */     //   621: aload #15
/*      */     //   623: invokevirtual isBlank : ()Z
/*      */     //   626: ifne -> 633
/*      */     //   629: iconst_1
/*      */     //   630: goto -> 634
/*      */     //   633: iconst_0
/*      */     //   634: istore #20
/*      */     //   636: aload #13
/*      */     //   638: iload #20
/*      */     //   640: aload #15
/*      */     //   642: aload #16
/*      */     //   644: aload #17
/*      */     //   646: iload #18
/*      */     //   648: iload #19
/*      */     //   650: invokevirtual setSerializedState : (ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZI)V
/*      */     //   653: goto -> 922
/*      */     //   656: aload #4
/*      */     //   658: checkcast vorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting
/*      */     //   661: astore #14
/*      */     //   663: aload_3
/*      */     //   664: invokevirtual isJsonPrimitive : ()Z
/*      */     //   667: ifeq -> 706
/*      */     //   670: aload_3
/*      */     //   671: invokevirtual getAsJsonPrimitive : ()Lcom/google/gson/JsonPrimitive;
/*      */     //   674: invokevirtual isNumber : ()Z
/*      */     //   677: ifeq -> 706
/*      */     //   680: aload_3
/*      */     //   681: invokevirtual getAsInt : ()I
/*      */     //   684: istore #15
/*      */     //   686: aload #14
/*      */     //   688: invokevirtual getColor1 : ()Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   691: ifnull -> 705
/*      */     //   694: aload #14
/*      */     //   696: invokevirtual getColor1 : ()Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   699: iload #15
/*      */     //   701: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   704: pop
/*      */     //   705: return
/*      */     //   706: aload_3
/*      */     //   707: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*      */     //   710: astore #15
/*      */     //   712: aload #15
/*      */     //   714: ldc_w 'selectedColorIndex'
/*      */     //   717: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   720: ifeq -> 739
/*      */     //   723: aload #14
/*      */     //   725: aload #15
/*      */     //   727: ldc_w 'selectedColorIndex'
/*      */     //   730: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   733: invokevirtual getAsInt : ()I
/*      */     //   736: invokevirtual setSelectedColorIndex : (I)V
/*      */     //   739: aload #15
/*      */     //   741: ldc_w 'colors'
/*      */     //   744: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   747: ifeq -> 826
/*      */     //   750: aload #15
/*      */     //   752: ldc_w 'colors'
/*      */     //   755: invokevirtual getAsJsonArray : (Ljava/lang/String;)Lcom/google/gson/JsonArray;
/*      */     //   758: astore #16
/*      */     //   760: aload #14
/*      */     //   762: invokevirtual getAllColors : ()Ljava/util/List;
/*      */     //   765: astore #17
/*      */     //   767: aload #16
/*      */     //   769: invokevirtual size : ()I
/*      */     //   772: aload #17
/*      */     //   774: invokeinterface size : ()I
/*      */     //   779: invokestatic min : (II)I
/*      */     //   782: istore #18
/*      */     //   784: iconst_0
/*      */     //   785: istore #19
/*      */     //   787: iload #19
/*      */     //   789: iload #18
/*      */     //   791: if_icmpge -> 826
/*      */     //   794: aload #17
/*      */     //   796: iload #19
/*      */     //   798: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   803: checkcast vorga/phazeclient/api/feature/module/setting/implement/ColorSetting
/*      */     //   806: aload #16
/*      */     //   808: iload #19
/*      */     //   810: invokevirtual get : (I)Lcom/google/gson/JsonElement;
/*      */     //   813: invokevirtual getAsInt : ()I
/*      */     //   816: invokevirtual setColor : (I)Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   819: pop
/*      */     //   820: iinc #19, 1
/*      */     //   823: goto -> 787
/*      */     //   826: goto -> 922
/*      */     //   829: aload #4
/*      */     //   831: checkcast vorga/phazeclient/api/feature/module/setting/implement/GroupSetting
/*      */     //   834: astore #15
/*      */     //   836: aload_3
/*      */     //   837: invokevirtual getAsJsonObject : ()Lcom/google/gson/JsonObject;
/*      */     //   840: astore #16
/*      */     //   842: aload #16
/*      */     //   844: ldc_w 'state'
/*      */     //   847: invokevirtual has : (Ljava/lang/String;)Z
/*      */     //   850: ifeq -> 870
/*      */     //   853: aload #15
/*      */     //   855: aload #16
/*      */     //   857: ldc_w 'state'
/*      */     //   860: invokevirtual get : (Ljava/lang/String;)Lcom/google/gson/JsonElement;
/*      */     //   863: invokevirtual getAsBoolean : ()Z
/*      */     //   866: invokevirtual setValue : (Z)Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*      */     //   869: pop
/*      */     //   870: aload #15
/*      */     //   872: invokevirtual getSubSettings : ()Ljava/util/List;
/*      */     //   875: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   880: astore #17
/*      */     //   882: aload #17
/*      */     //   884: invokeinterface hasNext : ()Z
/*      */     //   889: ifeq -> 919
/*      */     //   892: aload #17
/*      */     //   894: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   899: checkcast vorga/phazeclient/api/feature/module/setting/Setting
/*      */     //   902: astore #18
/*      */     //   904: aload #18
/*      */     //   906: aload #16
/*      */     //   908: invokestatic deserializeSetting : (Lvorga/phazeclient/api/feature/module/setting/Setting;Lcom/google/gson/JsonObject;)V
/*      */     //   911: goto -> 916
/*      */     //   914: astore #19
/*      */     //   916: goto -> 882
/*      */     //   919: goto -> 922
/*      */     //   922: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1408	-> 0
/*      */     //   #1409	-> 11
/*      */     //   #1412	-> 12
/*      */     //   #1413	-> 17
/*      */     //   #1414	-> 25
/*      */     //   #1415	-> 38
/*      */     //   #1416	-> 42
/*      */     //   #1417	-> 67
/*      */     //   #1418	-> 76
/*      */     //   #1419	-> 79
/*      */     //   #1416	-> 82
/*      */     //   #1423	-> 88
/*      */     //   #1424	-> 96
/*      */     //   #1427	-> 97
/*      */     //   #1428	-> 103
/*      */     //   #1429	-> 114
/*      */     //   #1432	-> 115
/*      */     //   #1433	-> 188
/*      */     //   #1434	-> 208
/*      */     //   #1435	-> 228
/*      */     //   #1436	-> 235
/*      */     //   #1437	-> 241
/*      */     //   #1438	-> 246
/*      */     //   #1440	-> 268
/*      */     //   #1441	-> 276
/*      */     //   #1442	-> 279
/*      */     //   #1443	-> 299
/*      */     //   #1444	-> 319
/*      */     //   #1445	-> 338
/*      */     //   #1446	-> 345
/*      */     //   #1447	-> 351
/*      */     //   #1452	-> 371
/*      */     //   #1453	-> 386
/*      */     //   #1454	-> 393
/*      */     //   #1455	-> 396
/*      */     //   #1456	-> 403
/*      */     //   #1457	-> 420
/*      */     //   #1458	-> 429
/*      */     //   #1460	-> 430
/*      */     //   #1461	-> 437
/*      */     //   #1463	-> 438
/*      */     //   #1464	-> 444
/*      */     //   #1465	-> 474
/*      */     //   #1466	-> 504
/*      */     //   #1467	-> 534
/*      */     //   #1468	-> 544
/*      */     //   #1469	-> 557
/*      */     //   #1470	-> 564
/*      */     //   #1471	-> 575
/*      */     //   #1472	-> 589
/*      */     //   #1473	-> 596
/*      */     //   #1474	-> 607
/*      */     //   #1475	-> 621
/*      */     //   #1476	-> 636
/*      */     //   #1477	-> 653
/*      */     //   #1478	-> 656
/*      */     //   #1479	-> 663
/*      */     //   #1480	-> 680
/*      */     //   #1481	-> 686
/*      */     //   #1482	-> 694
/*      */     //   #1484	-> 705
/*      */     //   #1486	-> 706
/*      */     //   #1487	-> 712
/*      */     //   #1488	-> 723
/*      */     //   #1490	-> 739
/*      */     //   #1491	-> 750
/*      */     //   #1492	-> 760
/*      */     //   #1493	-> 767
/*      */     //   #1494	-> 784
/*      */     //   #1495	-> 794
/*      */     //   #1494	-> 820
/*      */     //   #1498	-> 826
/*      */     //   #1499	-> 829
/*      */     //   #1500	-> 836
/*      */     //   #1501	-> 842
/*      */     //   #1502	-> 853
/*      */     //   #1504	-> 870
/*      */     //   #1506	-> 904
/*      */     //   #1508	-> 911
/*      */     //   #1507	-> 914
/*      */     //   #1509	-> 916
/*      */     //   #1510	-> 919
/*      */     //   #1515	-> 922
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   67	15	7	alias	Ljava/lang/String;
/*      */     //   38	59	3	aliases	[Ljava/lang/String;
/*      */     //   195	13	6	booleanSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*      */     //   215	13	7	valueSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ValueSetting;
/*      */     //   241	35	9	value	Ljava/lang/String;
/*      */     //   235	44	8	textSetting	Lvorga/phazeclient/api/feature/module/setting/implement/TextSetting;
/*      */     //   286	13	9	bindSetting	Lvorga/phazeclient/api/feature/module/setting/implement/BindSetting;
/*      */     //   306	13	10	colorSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;
/*      */     //   326	12	11	selectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/SelectSetting;
/*      */     //   351	42	13	value	Ljava/lang/String;
/*      */     //   371	22	14	selected	Ljava/util/List;
/*      */     //   345	51	12	multiSelectSetting	Lvorga/phazeclient/api/feature/module/setting/implement/MultiSelectSetting;
/*      */     //   444	209	14	itemObject	Lcom/google/gson/JsonObject;
/*      */     //   474	179	15	itemId	Ljava/lang/String;
/*      */     //   504	149	16	displayName	Ljava/lang/String;
/*      */     //   534	119	17	matchName	Ljava/lang/String;
/*      */     //   564	89	18	enabled	Z
/*      */     //   596	57	19	highlightColor	I
/*      */     //   636	17	20	active	Z
/*      */     //   403	253	13	itemPickerSetting	Lvorga/phazeclient/api/feature/module/setting/implement/ItemPickerSetting;
/*      */     //   686	20	15	legacyColor	I
/*      */     //   787	39	19	i	I
/*      */     //   760	66	16	colorsArray	Lcom/google/gson/JsonArray;
/*      */     //   767	59	17	colorSettings	Ljava/util/List;
/*      */     //   784	42	18	n	I
/*      */     //   712	114	15	colorObject	Lcom/google/gson/JsonObject;
/*      */     //   663	166	14	multiColor	Lvorga/phazeclient/api/feature/module/setting/implement/MultiColorSetting;
/*      */     //   904	12	18	subSetting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*      */     //   842	77	16	groupObject	Lcom/google/gson/JsonObject;
/*      */     //   836	86	15	group	Lvorga/phazeclient/api/feature/module/setting/implement/GroupSetting;
/*      */     //   0	923	0	setting	Lvorga/phazeclient/api/feature/module/setting/Setting;
/*      */     //   0	923	1	source	Lcom/google/gson/JsonObject;
/*      */     //   17	906	2	key	Ljava/lang/String;
/*      */     //   103	820	3	element	Lcom/google/gson/JsonElement;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   371	22	14	selected	Ljava/util/List<Ljava/lang/String;>;
/*      */     //   767	59	17	colorSettings	Ljava/util/List<Lvorga/phazeclient/api/feature/module/setting/implement/ColorSetting;>;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   904	911	914	java/lang/Exception
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\config\ConfigManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */