/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
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
/*     */ public final class StreamerMode
/*     */   extends Module
/*     */ {
/*  34 */   private static final StreamerMode INSTANCE = new StreamerMode();
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
/*  49 */   private static final String[] PASSWORD_COMMANDS = new String[] { "/l", "/login", "/log", "/r", "/reg", "/register", "/registration", "/changepass", "/changepassword", "/changepw", "/cp", "/auth", "/authme", "/pass", "/password", "/unreg", "/unregister", "/л", "/логин", "/р", "/рег", "/регистрация", "/смп", "/сменапароля" };
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
/*  80 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  81 */   public final BooleanSetting hideCoordinates = (new BooleanSetting("Hide Coordinates", "Hide your XYZ position in the F3 debug screen, the WAILA HUD, and the Coordinates HUD"))
/*     */ 
/*     */     
/*  84 */     .setValue(true);
/*  85 */   public final BooleanSetting hidePasswords = (new BooleanSetting("Hide Passwords", "Replace passwords with * (visually only) when typing /login, /reg, /register, /changepass, etc."))
/*     */ 
/*     */     
/*  88 */     .setValue(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StreamerMode() {
/*  95 */     super("streamer_mode", "Streamer Mode", ModuleCategory.OTHER);
/*  96 */     this.hideCoordinates.setFullWidth(true);
/*  97 */     this.hidePasswords.setFullWidth(true);
/*  98 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.hideCoordinates, (Setting)this.hidePasswords });
/*     */   }
/*     */   
/*     */   public static StreamerMode getInstance() {
/* 102 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 107 */     return "Hides sensitive info while streaming: coordinates and passwords";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 112 */     return "streamer_mode.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 117 */     return 21.0F;
/*     */   }
/*     */   
/*     */   public boolean isHideCoordinatesEnabled() {
/* 121 */     return (isEnabled() && this.hideCoordinates.isValue());
/*     */   }
/*     */   
/*     */   public boolean isHidePasswordsEnabled() {
/* 125 */     return (isEnabled() && this.hidePasswords.isValue());
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
/*     */   public static String maskPasswordIfMatching(String text) {
/* 144 */     if (text == null || text.length() < 2 || text.charAt(0) != '/') {
/* 145 */       return text;
/*     */     }
/* 147 */     String lower = text.toLowerCase();
/* 148 */     for (String cmd : PASSWORD_COMMANDS) {
/* 149 */       if (lower.startsWith(cmd))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 155 */         if (lower.length() == cmd.length() || lower.charAt(cmd.length()) == ' ') {
/*     */ 
/*     */           
/* 158 */           int spaceIdx = text.indexOf(' ');
/* 159 */           if (spaceIdx < 0)
/*     */           {
/* 161 */             return text;
/*     */           }
/* 163 */           StringBuilder sb = new StringBuilder(text.length());
/* 164 */           sb.append(text, 0, spaceIdx + 1);
/* 165 */           for (int i = spaceIdx + 1; i < text.length(); i++) {
/* 166 */             char c = text.charAt(i);
/* 167 */             sb.append((c == ' ') ? 32 : 42);
/*     */           } 
/* 169 */           return sb.toString();
/*     */         }  } 
/* 171 */     }  return text;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\StreamerMode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */