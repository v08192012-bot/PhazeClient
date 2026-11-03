/*    */ package vorga.phazeclient.implement.features.modules.other;
/*    */ 
/*    */ import vorga.phazeclient.api.feature.module.Module;
/*    */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.TextSetting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DiscordRpc
/*    */   extends Module
/*    */ {
/* 42 */   private static final DiscordRpc INSTANCE = new DiscordRpc();
/*    */   
/* 44 */   public final SectionSetting linesSection = new SectionSetting("Lines");
/* 45 */   public final TextSetting detailsTemplate = (new TextSetting("Details", "Top line. Tokens: {server} {dimension} {player} {gamemode} {health}"))
/*    */ 
/*    */     
/* 48 */     .setText("Playing on {server}").setMax(96);
/* 49 */   public final TextSetting stateTemplate = (new TextSetting("State", "Bottom line. Same tokens as Details."))
/*    */ 
/*    */     
/* 52 */     .setText("In {dimension} as {player}").setMax(96);
/*    */   
/* 54 */   public final SectionSetting privacySection = new SectionSetting("Privacy");
/* 55 */   public final BooleanSetting showServerName = (new BooleanSetting("Show Server Name", "When OFF, the {server} token is replaced with 'Singleplayer' for any non-singleplayer session too. Use this if you don't want your server hostname visible in your Discord status."))
/*    */ 
/*    */     
/* 58 */     .setValue(true);
/* 59 */   public final BooleanSetting hideInMenus = (new BooleanSetting("Hide In Menus", "Stop sending the rich presence when you're in the main menu / title screen so your status reads 'idle' instead of 'Playing'"))
/*    */ 
/*    */     
/* 62 */     .setValue(false);
/*    */   
/* 64 */   public final SectionSetting timestampSection = new SectionSetting("Timestamp");
/* 65 */   public final SelectSetting elapsedMode = (new SelectSetting("Elapsed Time", "Session: time since the client launched. World: resets when you join a world. Off: no elapsed badge."))
/*    */ 
/*    */     
/* 68 */     .value(new String[] { "Session", "World", "Off" }).selected("Session");
/*    */   
/*    */   private DiscordRpc() {
/* 71 */     super("discord_rpc", "Discord RPC", ModuleCategory.OTHER, true, false);
/* 72 */     this.detailsTemplate.setFullWidth(true);
/* 73 */     this.stateTemplate.setFullWidth(true);
/* 74 */     this.showServerName.setFullWidth(true);
/* 75 */     this.hideInMenus.setFullWidth(true);
/* 76 */     this.elapsedMode.setFullWidth(true);
/* 77 */     setup(new Setting[] { (Setting)this.linesSection, (Setting)this.detailsTemplate, (Setting)this.stateTemplate, (Setting)this.privacySection, (Setting)this.showServerName, (Setting)this.hideInMenus, (Setting)this.timestampSection, (Setting)this.elapsedMode });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DiscordRpc getInstance() {
/* 83 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 88 */     return "Discord Rich Presence: customisable Playing-on-server card with templated lines";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 93 */     return "discord_rpc.png";
/*    */   }
/*    */ 
/*    */   
/*    */   public float getIconSize() {
/* 98 */     return 21.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\DiscordRpc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */