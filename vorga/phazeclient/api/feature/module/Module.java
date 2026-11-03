/*     */ package vorga.phazeclient.api.feature.module;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.SettingRepository;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*     */ import vorga.phazeclient.api.system.animation.Animation;
/*     */ import vorga.phazeclient.api.system.animation.Direction;
/*     */ import vorga.phazeclient.api.system.animation.implement.DecelerateAnimation;
/*     */ import vorga.phazeclient.base.QuickImports;
/*     */ import vorga.phazeclient.base.util.HolyWorldFeatureControlService;
/*     */ import vorga.phazeclient.base.util.Lang;
/*     */ import vorga.phazeclient.base.util.RemoteRulesService;
/*     */ import vorga.phazeclient.base.util.ServerUtil;
/*     */ 
/*     */ public class Module extends SettingRepository implements QuickImports {
/*     */   private final String name;
/*     */   private final String visibleName;
/*     */   private final ModuleCategory category;
/*     */   private ModuleCategory secondaryCategory;
/*     */   private final boolean showEnable;
/*     */   private final boolean canBind;
/*     */   
/*  25 */   public void setSecondaryCategory(ModuleCategory secondaryCategory) { this.secondaryCategory = secondaryCategory; } public void setType(int type) { this.type = type; } public void setExpanded(boolean expanded) { this.expanded = expanded; }
/*     */   
/*     */   public String getName() {
/*  28 */     return this.name;
/*     */   }
/*  30 */   public ModuleCategory getCategory() { return this.category; }
/*  31 */   public ModuleCategory getSecondaryCategory() { return this.secondaryCategory; }
/*  32 */   public boolean isShowEnable() { return this.showEnable; } public boolean isCanBind() {
/*  33 */     return this.canBind;
/*  34 */   } private final Animation animation = (new DecelerateAnimation()).setMs(150).setValue(1.0D); private int key; private int type; public boolean state; public boolean expanded; private static Consumer<Module> globalStateChangeListener; public Animation getAnimation() { return this.animation; }
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
/*     */   public Module(String name, ModuleCategory category)
/*     */   {
/*  96 */     this.key = -1; this.type = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.expanded = false; this.name = name; this.category = category; this.visibleName = name; this.secondaryCategory = null; this.showEnable = true; this.canBind = false; initializeModule(); } public Module(String name, ModuleCategory category, boolean showEnable) { this.key = -1; this.type = 1; this.expanded = false; this.name = name; this.category = category; this.visibleName = name; this.secondaryCategory = null; this.showEnable = showEnable; this.canBind = false; initializeModule(); } public Module(String name, ModuleCategory category, boolean showEnable, boolean canBind) { this.key = -1; this.type = 1; this.expanded = false; this.name = name; this.category = category; this.visibleName = name; this.secondaryCategory = null; this.showEnable = showEnable; this.canBind = canBind; initializeModule(); } public Module(String name, String visibleName, ModuleCategory category) { this.key = -1; this.type = 1; this.expanded = false; this.name = name; this.visibleName = visibleName; this.category = category; this.secondaryCategory = null; this.showEnable = true; this.canBind = false; initializeModule(); } public Module(String name, String visibleName, ModuleCategory category, boolean showEnable) { this.key = -1; this.type = 1; this.expanded = false; this.name = name; this.visibleName = visibleName; this.category = category; this.secondaryCategory = null; this.showEnable = showEnable; this.canBind = false; initializeModule(); } public Module(String name, String visibleName, ModuleCategory category, boolean showEnable, boolean canBind) { this.key = -1; this.type = 1; this.expanded = false; this.name = name; this.visibleName = visibleName; this.category = category; this.secondaryCategory = null; this.showEnable = showEnable; this.canBind = canBind; initializeModule(); }
/* 103 */   public int getKey() { return this.key; } public boolean isExpanded() { return this.expanded; }
/*     */   public int getType() { return this.type; }
/* 105 */   public boolean isState() { return this.state; } public static void setGlobalStateChangeListener(Consumer<Module> globalStateChangeListener) { Module.globalStateChangeListener = globalStateChangeListener; }
/*     */ 
/*     */   
/*     */   public void switchState() {
/* 109 */     setState(!this.state);
/*     */   }
/*     */   
/*     */   public void setState(boolean state) {
/* 113 */     if (state && isServerLocked()) {
/*     */       return;
/*     */     }
/* 116 */     if (state) {
/* 117 */       HolyWorldFeatureControlService.getInstance().requestFeatureStatus(getIdentifier());
/*     */     }
/* 119 */     this.animation.setDirection(state ? Direction.FORWARDS : Direction.BACKWARDS);
/* 120 */     if (state != this.state) {
/* 121 */       this.state = state;
/* 122 */       handleStateChange();
/* 123 */       notifyStateChange();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyStateChange() {
/* 128 */     if (globalStateChangeListener != null) {
/* 129 */       globalStateChangeListener.accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setKey(int key) {
/* 134 */     if (this.key != key) {
/* 135 */       this.key = key;
/* 136 */       notifyStateChange();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStateSilent(boolean state) {
/* 141 */     if (state && isServerLocked()) {
/*     */       return;
/*     */     }
/* 144 */     this.animation.setDirection(state ? Direction.FORWARDS : Direction.BACKWARDS);
/* 145 */     if (state != this.state) {
/* 146 */       this.state = state;
/* 147 */       toggleSilent(isEnabled());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleStateChange() {
/* 152 */     class_310 mc = class_310.method_1551();
/* 153 */     if (mc.field_1724 != null && mc.field_1687 != null) {
/* 154 */       if (this.state) {
/* 155 */         activate();
/*     */       } else {
/* 157 */         deactivate();
/*     */       } 
/*     */     }
/* 160 */     toggleSilent(isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   private void toggleSilent(boolean activate) {}
/*     */ 
/*     */   
/*     */   private void initializeModule() {
/* 168 */     if (!this.showEnable) {
/* 169 */       toggleSilent(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 174 */     if (isServerLocked()) {
/* 175 */       return false;
/*     */     }
/* 177 */     return (!this.showEnable || this.state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabledByDefault() {
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 186 */     if (RemoteRulesService.getInstance().shouldHideModuleWhenOffline(getIdentifier())) {
/* 187 */       return false;
/*     */     }
/* 189 */     return !isServerLocked();
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {}
/*     */ 
/*     */   
/*     */   public void deactivate() {}
/*     */ 
/*     */   
/*     */   public final void setup(Setting... settings) {
/* 200 */     String moduleContext = extractModuleContext(this.name);
/*     */     
/* 202 */     for (Setting setting : settings) {
/* 203 */       setModuleContextRecursive(setting, moduleContext);
/*     */     }
/* 205 */     super.setup(settings);
/*     */   }
/*     */   
/*     */   private String extractModuleContext(String moduleName) {
/* 209 */     if (moduleName != null && moduleName.startsWith("module.") && moduleName.endsWith(".name")) {
/* 210 */       String withoutPrefix = moduleName.substring(7);
/* 211 */       return withoutPrefix.substring(0, withoutPrefix.length() - 5);
/*     */     } 
/* 213 */     return moduleName;
/*     */   }
/*     */   
/*     */   private void setModuleContextRecursive(Setting setting, String moduleName) {
/* 217 */     setting.setModuleContext(moduleName);
/*     */     
/* 219 */     if (setting instanceof GroupSetting) { GroupSetting group = (GroupSetting)setting;
/* 220 */       for (Setting subSetting : group.getSubSettings()) {
/* 221 */         setModuleContextRecursive(subSetting, moduleName);
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   public String getVisibleName() {
/* 227 */     return this.visibleName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getLocalizedName() {
/* 234 */     return getVisibleName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayDescription() {
/* 239 */     return Lang.translate(getDescription());
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 243 */     return "No description";
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 247 */     return null;
/*     */   }
/*     */   
/*     */   public float getIconSize() {
/* 251 */     return 16.0F;
/*     */   }
/*     */   
/*     */   public float getIconOffsetY() {
/* 255 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean showIconInSettings() {
/* 259 */     return true;
/*     */   }
/*     */   
/*     */   public String getVisibleNameKey() {
/* 263 */     return this.visibleName;
/*     */   }
/*     */   
/*     */   public String getIdentifier() {
/* 267 */     return extractModuleContext(this.name);
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
/*     */   public boolean isServerAllowed() {
/* 280 */     return true;
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
/*     */   public boolean isServerLocked() {
/* 299 */     if (RemoteRulesService.getInstance().shouldHideModuleWhenOffline(getIdentifier())) {
/* 300 */       return true;
/*     */     }
/* 302 */     String host = ServerUtil.getCurrentServerHost();
/* 303 */     if (host == null || host.isEmpty()) {
/* 304 */       return false;
/*     */     }
/*     */     
/* 307 */     String identifier = getIdentifier();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (RemoteRulesService.getInstance().isModuleExplicitlyAllowed(identifier)) {
/* 317 */       return false;
/*     */     }
/*     */     
/* 320 */     if (!isServerAllowed()) {
/* 321 */       return true;
/*     */     }
/* 323 */     if (!ServerUtil.isModuleAllowedByMirroredRules(identifier)) {
/* 324 */       return true;
/*     */     }
/* 326 */     return (RemoteRulesService.getInstance().isModuleBlocked(identifier) || 
/* 327 */       HolyWorldFeatureControlService.getInstance().isFeatureDisabled(identifier));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\Module.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */