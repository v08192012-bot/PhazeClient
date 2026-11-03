/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3262;
/*     */ import net.minecraft.class_3288;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5348;
/*     */ import net.minecraft.class_7367;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ 
/*     */ public class PackDisplayHud extends RectHudModule {
/*  27 */   private static final PackDisplayHud INSTANCE = new PackDisplayHud();
/*     */   
/*     */   public static final int ICON_SIZE = 28;
/*     */   private static final float TEXT_PADDING = 4.0F;
/*  31 */   private String cachedPackName = "Default";
/*     */   private String cachedProfileId;
/*  33 */   private String cachedText = "Default";
/*     */   private int cachedTextWidth;
/*  35 */   private String cachedDescription = "";
/*  36 */   private String cachedTextWithDescription = "Default";
/*     */   private int cachedDescriptionWidth;
/*  38 */   private class_2561 cachedNameText = (class_2561)class_2561.method_43470("Default");
/*  39 */   private class_2561 cachedDescriptionText = (class_2561)class_2561.method_43473();
/*  40 */   private List<class_2561> cachedDescriptionLines = List.of();
/*     */   private class_2960 cachedIcon;
/*     */   private class_2960 ownedIcon;
/*  43 */   private int cachedIconWidth = 16;
/*  44 */   private int cachedIconHeight = 16;
/*  45 */   private int cachedDrawIconWidth = 28;
/*  46 */   private int cachedDrawIconHeight = 28;
/*  47 */   private long lastCheckTime = 0L;
/*  48 */   public final BooleanSetting displayPackIcon = (new BooleanSetting("Показывать иконку пака", "Показывать иконку resource pack слева")).setValue(true);
/*  49 */   public final BooleanSetting displayDescription = (new BooleanSetting("Показывать описание", "Показывать описание resource pack")).setValue(true);
/*  50 */   public final BooleanSetting ignoreServerPack = (new BooleanSetting("Игнорировать серверный пак", "Не учитывать серверный resource pack")).setValue(false);
/*  51 */   public final SelectSetting packOrder = (new SelectSetting("Порядок паков", "Выбирать первый или последний пак"))
/*  52 */     .value(new String[] { "Первый", "Последний" }).selected("Последний");
/*     */   
/*     */   private PackDisplayHud() {
/*  55 */     super("pack_display_hud", "Pack Display", 20.0F, 140.0F, 1.0F);
/*  56 */     this.cornerRounding.setValue(4.0F);
/*  57 */     setup(new Setting[] { (Setting)this.displayPackIcon, (Setting)this.displayDescription, (Setting)this.ignoreServerPack, (Setting)this.packOrder });
/*     */   }
/*     */   
/*     */   public static PackDisplayHud getInstance() {
/*  61 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  66 */     return "HUD widget showing name of current active resource pack";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  71 */     return "item_scroller.png";
/*     */   }
/*     */   private void updatePackName(class_310 client) {
/*     */     class_2561 class_25611, class_25612;
/*  75 */     long now = System.currentTimeMillis();
/*  76 */     if (now - this.lastCheckTime < 2000L)
/*  77 */       return;  this.lastCheckTime = now;
/*     */     
/*  79 */     if (client.method_1520() == null)
/*  80 */       return;  Collection<class_3288> profiles = client.method_1520().method_14444();
/*  81 */     String topCustomPack = "Default";
/*  82 */     String topDescription = "";
/*  83 */     class_5250 class_52501 = class_2561.method_43470("Default");
/*  84 */     class_5250 class_52502 = class_2561.method_43473();
/*  85 */     class_3288 selectedProfile = null;
/*  86 */     class_3288 defaultProfile = null;
/*  87 */     boolean first = "Первый".equals(this.packOrder.getSelected());
/*  88 */     for (class_3288 profile : profiles) {
/*  89 */       String name = profile.method_14463();
/*  90 */       if (name.equalsIgnoreCase("vanilla")) {
/*  91 */         defaultProfile = profile; continue;
/*     */       } 
/*  93 */       if (this.ignoreServerPack.isValue() && name.toLowerCase().contains("server"))
/*  94 */         continue;  topCustomPack = profile.method_14457().getString();
/*  95 */       topDescription = profile.method_14459().getString();
/*  96 */       class_25611 = profile.method_14457();
/*  97 */       class_25612 = profile.method_14459();
/*  98 */       selectedProfile = profile;
/*  99 */       if (first)
/*     */         break; 
/*     */     } 
/* 102 */     if (selectedProfile == null) selectedProfile = defaultProfile; 
/* 103 */     String profileId = (selectedProfile == null) ? null : selectedProfile.method_14463();
/* 104 */     if (!this.cachedPackName.equals(topCustomPack) || !Objects.equals(this.cachedProfileId, profileId) || this.cachedIcon == null) {
/* 105 */       this.cachedPackName = topCustomPack;
/* 106 */       this.cachedProfileId = profileId;
/* 107 */       this.cachedText = this.cachedPackName;
/* 108 */       this.cachedDescription = topDescription;
/* 109 */       this.cachedTextWithDescription = this.cachedText + "\n" + this.cachedText;
/* 110 */       this.cachedNameText = class_25611;
/* 111 */       this.cachedDescriptionText = class_25612;
/* 112 */       this.cachedDescriptionLines = splitDescription(class_25612);
/* 113 */       this.cachedTextWidth = client.field_1772.method_27525((class_5348)this.cachedNameText);
/* 114 */       this.cachedDescriptionWidth = 0;
/* 115 */       for (class_2561 line : this.cachedDescriptionLines) {
/* 116 */         this.cachedDescriptionWidth = Math.max(this.cachedDescriptionWidth, client.field_1772.method_27525((class_5348)line));
/*     */       }
/* 118 */       releaseOwnedIcon(client);
/* 119 */       this.cachedIcon = loadIcon(client, selectedProfile);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getDisplayText(class_310 client) {
/* 124 */     updatePackName(client);
/* 125 */     if (this.displayDescription.isValue() && !this.cachedDescription.isBlank()) {
/* 126 */       return this.cachedTextWithDescription;
/*     */     }
/* 128 */     return this.cachedText;
/*     */   }
/*     */   
/*     */   public int getDisplayTextWidth() {
/* 132 */     return this.cachedTextWidth;
/*     */   }
/*     */   
/*     */   public class_2561 getNameText(class_310 client) {
/* 136 */     updatePackName(client);
/* 137 */     return this.cachedNameText;
/*     */   }
/*     */   
/*     */   public class_2561 getDescriptionText(class_310 client) {
/* 141 */     updatePackName(client);
/* 142 */     return this.cachedDescriptionText;
/*     */   }
/*     */   
/*     */   public boolean hasDescription(class_310 client) {
/* 146 */     updatePackName(client);
/* 147 */     return (this.displayDescription.isValue() && !this.cachedDescription.isBlank());
/*     */   }
/*     */   
/*     */   public List<class_2561> getDescriptionLines(class_310 client) {
/* 151 */     updatePackName(client);
/* 152 */     return this.cachedDescriptionLines;
/*     */   }
/*     */   
/*     */   public float getTextStartX() {
/* 156 */     return ((this.cachedIcon != null && this.displayPackIcon.isValue()) ? 28 : false) + 4.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPanelBaseWidth(class_310 client) {
/* 161 */     updatePackName(client);
/* 162 */     int width = hasDescription(client) ? Math.max(this.cachedTextWidth, this.cachedDescriptionWidth) : this.cachedTextWidth;
/* 163 */     return getTextStartX() + width + 4.0F;
/*     */   }
/*     */   
/*     */   public class_2960 getPackIcon(class_310 client) {
/* 167 */     updatePackName(client);
/* 168 */     return this.displayPackIcon.isValue() ? this.cachedIcon : null;
/*     */   }
/*     */   
/*     */   public int getPackIconWidth() {
/* 172 */     return this.cachedIconWidth;
/*     */   }
/*     */   
/*     */   public int getPackIconHeight() {
/* 176 */     return this.cachedIconHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPackIcon(class_332 context) {
/* 181 */     if (this.cachedIcon == null || !this.displayPackIcon.isValue())
/* 182 */       return;  context.method_25302(class_1921::method_62277, this.cachedIcon, (28 - this.cachedDrawIconWidth) / 2, (28 - this.cachedDrawIconHeight) / 2, 0.0F, 0.0F, this.cachedDrawIconWidth, this.cachedDrawIconHeight, this.cachedIconWidth, this.cachedIconHeight, this.cachedIconWidth, this.cachedIconHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIconSize(int width, int height) {
/* 189 */     this.cachedIconWidth = Math.max(1, width);
/* 190 */     this.cachedIconHeight = Math.max(1, height);
/* 191 */     int longestSide = Math.max(this.cachedIconWidth, this.cachedIconHeight);
/* 192 */     this.cachedDrawIconWidth = Math.max(1, Math.round(28.0F * this.cachedIconWidth / longestSide));
/* 193 */     this.cachedDrawIconHeight = Math.max(1, Math.round(28.0F * this.cachedIconHeight / longestSide));
/*     */   }
/*     */   
/*     */   public void onResourcesReloaded(class_310 client) {
/* 197 */     releaseOwnedIcon(client);
/* 198 */     this.cachedIcon = null;
/* 199 */     this.cachedProfileId = null;
/* 200 */     this.lastCheckTime = 0L;
/* 201 */     getHudBuffer().invalidate();
/*     */   }
/*     */   
/*     */   private void releaseOwnedIcon(class_310 client) {
/* 205 */     if (this.ownedIcon != null) {
/* 206 */       client.method_1531().method_4615(this.ownedIcon);
/* 207 */       this.ownedIcon = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class_2960 loadIcon(class_310 client, class_3288 profile) {
/* 212 */     if (profile == null) return fallbackIcon(); 
/* 213 */     if (profile.method_14463().equalsIgnoreCase("vanilla")) {
/* 214 */       return fallbackIcon();
/*     */     }
/* 216 */     class_3262 pack = null;
/*     */     try {
/* 218 */       pack = profile.method_14458();
/* 219 */       class_7367<InputStream> supplier = pack.method_14410(new String[] { "pack.png" });
/* 220 */       if (supplier == null) return fallbackIcon(); 
/* 221 */       InputStream stream = (InputStream)supplier.get();
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
/*     */     }
/* 235 */     catch (IOException|RuntimeException ignored) {
/* 236 */       return fallbackIcon();
/*     */     } finally {
/* 238 */       if (pack != null) pack.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class_2960 fallbackIcon() {
/* 243 */     setIconSize(16, 16);
/* 244 */     return class_2960.method_60655("minecraft", "textures/item/grass_block.png");
/*     */   }
/*     */   
/*     */   private static boolean isMissingTextureImage(class_1011 image) {
/* 248 */     if (image.method_4307() <= 0 || image.method_4323() <= 0) return true; 
/* 249 */     int first = image.method_61940(0, 0) & 0xFFFFFF;
/* 250 */     if (first != 16711935) return false; 
/* 251 */     for (int y = 0; y < image.method_4323(); y++) {
/* 252 */       for (int x = 0; x < image.method_4307(); x++) {
/* 253 */         if ((image.method_61940(x, y) & 0xFFFFFF) != first) return false; 
/*     */       } 
/*     */     } 
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   private static List<class_2561> splitDescription(class_2561 description) {
/* 260 */     List<class_2561> lines = new ArrayList<>();
/* 261 */     class_5250[] current = { class_2561.method_43473() };
/* 262 */     description.method_27658((style, fragment) -> { class_2583 activeStyle = style; StringBuilder run = new StringBuilder(); for (int i = 0; i < fragment.length(); i++) { char character = fragment.charAt(i); if (character == '§' && i + 1 < fragment.length()) { appendRun(current[0], run, activeStyle); class_124 formatting = class_124.method_544(fragment.charAt(++i)); if (formatting == class_124.field_1070) { activeStyle = class_2583.field_24360; } else if (formatting != null) { activeStyle = formatting.method_543() ? class_2583.field_24360.method_10977(formatting) : activeStyle.method_27706(formatting); }  } else if (character == '\n' || character == '\r') { appendRun(current[0], run, activeStyle); if (character == '\r' && i + 1 < fragment.length() && fragment.charAt(i + 1) == '\n') i++;  lines.add(current[0]); current[0] = class_2561.method_43473(); } else { run.append(character); }  }  appendRun(current[0], run, activeStyle); return Optional.empty(); }description
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
/* 287 */         .method_10866());
/* 288 */     lines.add(current[0]);
/* 289 */     return List.copyOf(lines);
/*     */   }
/*     */   
/*     */   private static void appendRun(class_5250 line, StringBuilder run, class_2583 style) {
/* 293 */     if (!run.isEmpty()) {
/* 294 */       line.method_10852((class_2561)class_2561.method_43470(run.toString()).method_10862(style));
/* 295 */       run.setLength(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\PackDisplayHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */