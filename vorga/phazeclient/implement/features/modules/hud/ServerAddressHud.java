/*     */ package vorga.phazeclient.implement.features.modules.hud;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_642;
/*     */ import net.minecraft.class_8573;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ 
/*     */ public final class ServerAddressHud extends RectHudModule {
/*  16 */   private static final ServerAddressHud INSTANCE = new ServerAddressHud();
/*     */   
/*     */   public static ServerAddressHud getInstance() {
/*  19 */     return INSTANCE;
/*     */   }
/*     */   
/*  22 */   public final BooleanSetting displayServerIcon = (new BooleanSetting("Display Server Icon", "Show server icon next to address")).setValue(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final class_2960 UNKNOWN_SERVER_TEXTURE = class_2960.method_60656("textures/misc/unknown_server.png");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_8573 currentIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String currentIconServerAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] currentIconFaviconBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerAddressHud() {
/*  60 */     super("server_address_hud", "Server Address", 100.0F, 50.0F, 1.0F);
/*  61 */     this.displayServerIcon.setFullWidth(true);
/*  62 */     setup(new Setting[] { (Setting)this.displayServerIcon });
/*     */   }
/*     */   
/*     */   public String getServerAddress() {
/*  66 */     class_310 client = class_310.method_1551();
/*  67 */     if (client == null || client.method_1562() == null) {
/*  68 */       return "Local";
/*     */     }
/*     */     
/*  71 */     if (client.method_1496()) {
/*  72 */       return "Local";
/*     */     }
/*  74 */     class_642 serverInfo = getServerInfo();
/*  75 */     if (serverInfo != null && serverInfo.field_3761 != null && !serverInfo.field_3761.isEmpty()) {
/*  76 */       String str = normalizeServerAddress(serverInfo.field_3761);
/*  77 */       if (!isNumericAddress(str)) {
/*  78 */         return str;
/*     */       }
/*     */     } 
/*  81 */     if (serverInfo != null && serverInfo.field_3752 != null && !serverInfo.field_3752.isEmpty() && !isNumericAddress(serverInfo.field_3752)) {
/*  82 */       return serverInfo.field_3752;
/*     */     }
/*  84 */     String address = client.method_1562().method_48296().method_10755().toString();
/*  85 */     address = normalizeServerAddress(address);
/*  86 */     if (isNumericAddress(address)) {
/*  87 */       return "Unknown";
/*     */     }
/*     */     
/*  90 */     return address;
/*     */   }
/*     */   
/*     */   private String normalizeServerAddress(String address) {
/*  94 */     if (address.startsWith("/")) {
/*  95 */       address = address.substring(1);
/*     */     }
/*     */     
/*  98 */     if (address.contains("[")) {
/*  99 */       int bracketEnd = address.indexOf("]");
/* 100 */       if (bracketEnd != -1 && bracketEnd < address.length() - 1 && address.charAt(bracketEnd + 1) == ':') {
/* 101 */         address = address.substring(0, bracketEnd + 1);
/*     */       }
/* 103 */       address = address.replace("[", "").replace("]", "");
/*     */     } else {
/* 105 */       int lastColon = address.lastIndexOf(':');
/* 106 */       if (lastColon != -1 && address.indexOf(':') == lastColon) {
/* 107 */         address = address.substring(0, lastColon);
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return address;
/*     */   }
/*     */   
/*     */   private boolean isNumericAddress(String address) {
/* 115 */     String normalized = address.trim().toLowerCase();
/* 116 */     return (normalized.matches("\\d{1,3}(\\.\\d{1,3}){3}") || (normalized.matches("[0-9a-f:]+") && normalized.contains(":")));
/*     */   }
/*     */   
/*     */   public class_642 getServerInfo() {
/* 120 */     class_310 client = class_310.method_1551();
/* 121 */     if (client == null || client.method_1562() == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     return client.method_1558();
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
/*     */   public void renderServerIcon(class_332 context, float rectX, float rectY, float rectHeight, float inverseGuiScale) {
/* 159 */     if (!this.displayServerIcon.isValue()) {
/*     */       return;
/*     */     }
/* 162 */     class_310 client = class_310.method_1551();
/* 163 */     if (client == null) {
/*     */       return;
/*     */     }
/* 166 */     class_642 serverInfo = getServerInfo();
/* 167 */     if (serverInfo == null) {
/*     */ 
/*     */ 
/*     */       
/* 171 */       releaseIcon();
/*     */       return;
/*     */     } 
/* 174 */     updateIcon(client, serverInfo);
/*     */ 
/*     */ 
/*     */     
/* 178 */     class_2960 textureId = (this.currentIcon != null && this.currentIconFaviconBytes != null) ? this.currentIcon.method_52201() : UNKNOWN_SERVER_TEXTURE;
/*     */     
/* 180 */     int iconSize = Math.max(1, Math.round(rectHeight));
/* 181 */     int iconX = Math.round(rectX - rectHeight);
/* 182 */     int iconY = Math.round(rectY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     context.method_51448().method_22903();
/* 189 */     context.method_51448().method_22905(inverseGuiScale, inverseGuiScale, 1.0F);
/* 190 */     context.method_25290(class_1921::method_62277, textureId, iconX, iconY, 0.0F, 0.0F, iconSize, iconSize, iconSize, iconSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     context.method_51448().method_22909();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateIcon(class_310 client, class_642 info) {
/* 209 */     String address = info.field_3761;
/* 210 */     if (address == null || address.isEmpty()) {
/* 211 */       releaseIcon();
/*     */       return;
/*     */     } 
/* 214 */     if (this.currentIcon == null || !address.equals(this.currentIconServerAddress)) {
/*     */ 
/*     */ 
/*     */       
/* 218 */       releaseIcon();
/* 219 */       this.currentIcon = class_8573.method_52202(client.method_1531(), address);
/* 220 */       this.currentIconServerAddress = address;
/* 221 */       this.currentIconFaviconBytes = null;
/*     */     } 
/*     */     
/* 224 */     byte[] favicon = info.method_49306();
/* 225 */     if (favicon == null || favicon.length == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     if (Arrays.equals(favicon, this.currentIconFaviconBytes)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     class_1011 image = null;
/*     */     try {
/* 242 */       image = class_1011.method_4309(new ByteArrayInputStream(favicon));
/* 243 */       this.currentIcon.method_52199(image);
/*     */ 
/*     */ 
/*     */       
/* 247 */       image = null;
/* 248 */       this.currentIconFaviconBytes = favicon;
/* 249 */     } catch (Exception ignored) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       this.currentIconFaviconBytes = null;
/* 255 */       if (image != null) {
/*     */         try {
/* 257 */           image.close();
/* 258 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
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
/*     */   private void releaseIcon() {
/* 275 */     if (this.currentIcon != null) {
/*     */       try {
/* 277 */         this.currentIcon.close();
/* 278 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       this.currentIcon = null;
/*     */     } 
/* 286 */     this.currentIconServerAddress = null;
/* 287 */     this.currentIconFaviconBytes = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {
/* 292 */     super.activate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 297 */     super.deactivate();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     releaseIcon();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 307 */     return "Shows server IP address in HUD";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 312 */     return "server_address_hud.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 317 */     return 21.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\hud\ServerAddressHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */