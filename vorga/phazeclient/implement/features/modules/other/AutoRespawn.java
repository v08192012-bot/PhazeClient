/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.TextSetting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AutoRespawn
/*     */   extends Module
/*     */ {
/*  34 */   private static final AutoRespawn INSTANCE = new AutoRespawn();
/*     */   
/*  36 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  37 */   public final BooleanSetting commandToggle = (new BooleanSetting("Command", "Send a command after the auto-respawn completes"))
/*  38 */     .setValue(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TextSetting commandText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean commandPending;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean respawnRequested;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AutoRespawn() {
/*  63 */     super("auto_respawn", "Auto Respawn", ModuleCategory.UTILITIES); Objects.requireNonNull(this.commandToggle); this.commandText = (new TextSetting("Command", "Command to send after respawn (leading slash optional)")).setText("home").visible(this.commandToggle::isValue); this.commandPending = false; this.respawnRequested = false;
/*  64 */     this.commandToggle.setFullWidth(true);
/*  65 */     this.commandText.setFullWidth(true);
/*  66 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.commandToggle, (Setting)this.commandText });
/*     */     
/*  68 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*     */   }
/*     */   
/*     */   public static AutoRespawn getInstance() {
/*  72 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  77 */     return "Automatically respawns and optionally runs a command after death";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  82 */     return "auto_respawn.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  87 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*  96 */     this.commandPending = false;
/*  97 */     this.respawnRequested = false;
/*     */   }
/*     */   
/*     */   private void tick(class_310 mc) {
/* 101 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/* 104 */     if (mc == null || mc.field_1724 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     boolean onDeathScreen = mc.field_1755 instanceof net.minecraft.class_418;
/*     */     
/* 110 */     if (onDeathScreen) {
/* 111 */       if (!this.respawnRequested) {
/* 112 */         mc.field_1724.method_7331();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 117 */         mc.method_1507(null);
/* 118 */         this.respawnRequested = true;
/* 119 */         this.commandPending = this.commandToggle.isValue();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.respawnRequested = false;
/* 132 */     if (this.commandPending && mc.field_1724.method_5805() && mc.method_1562() != null) {
/* 133 */       String raw = this.commandText.getText();
/* 134 */       if (raw == null) {
/* 135 */         this.commandPending = false;
/*     */         return;
/*     */       } 
/* 138 */       String cleaned = raw.trim();
/* 139 */       if (cleaned.startsWith("/")) {
/* 140 */         cleaned = cleaned.substring(1).trim();
/*     */       }
/* 142 */       if (!cleaned.isEmpty()) {
/* 143 */         mc.method_1562().method_45730(cleaned);
/*     */       }
/* 145 */       this.commandPending = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\AutoRespawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */