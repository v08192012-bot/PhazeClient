/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_746;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DeathCoords
/*     */   extends Module
/*     */ {
/*  43 */   private static final DeathCoords INSTANCE = new DeathCoords();
/*     */   
/*  45 */   public final SectionSetting outputSection = new SectionSetting("Output");
/*  46 */   public final SelectSetting outputMode = (new SelectSetting("Output Mode", "Where to send the recorded coordinates"))
/*     */ 
/*     */     
/*  49 */     .value(new String[] { "Chat", "Clipboard", "Both" }).selected("Chat");
/*  50 */   public final SelectSetting format = (new SelectSetting("Format", "Long: full message with labels and dimension. Short: raw 'X Y Z' triplet."))
/*     */ 
/*     */     
/*  53 */     .value(new String[] { "Long", "Short" }).selected("Long");
/*  54 */   public final TextSetting customPrefix = (new TextSetting("Prefix", "Tag prepended to the chat message"))
/*     */ 
/*     */     
/*  57 */     .setText("[Death]").setMax(24);
/*     */   
/*  59 */   public final SectionSetting visualSection = new SectionSetting("Visual");
/*  60 */   public final BooleanSetting colorMessage = (new BooleanSetting("Color Message", "Tint the chat message red so it stands out from regular chat"))
/*     */ 
/*     */     
/*  63 */     .setValue(true);
/*  64 */   public final BooleanSetting playSound = (new BooleanSetting("Play Sound", "Play the experience-orb pickup sound when the death message fires"))
/*     */ 
/*     */     
/*  67 */     .setValue(false);
/*     */ 
/*     */   
/*     */   private boolean deathPending = false;
/*     */ 
/*     */   
/*     */   public static DeathCoords getInstance() {
/*  74 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private DeathCoords() {
/*  78 */     super("death_coords", "Death Coords", ModuleCategory.OTHER);
/*  79 */     this.outputMode.setFullWidth(true);
/*  80 */     this.format.setFullWidth(true);
/*  81 */     this.customPrefix.setFullWidth(true);
/*  82 */     this.colorMessage.setFullWidth(true);
/*  83 */     this.playSound.setFullWidth(true);
/*  84 */     setup(new Setting[] { (Setting)this.outputSection, (Setting)this.outputMode, (Setting)this.format, (Setting)this.customPrefix, (Setting)this.visualSection, (Setting)this.colorMessage, (Setting)this.playSound });
/*     */ 
/*     */     
/*  87 */     ClientTickEvents.END_CLIENT_TICK.register(this::tick);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  92 */     return "Records your coords on death so you can return for your dropped items";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  97 */     return "skull-outline.png";
/*     */   }
/*     */   
/*     */   private void tick(class_310 client) {
/* 101 */     if (!isEnabled() || client == null || client.field_1724 == null)
/* 102 */       return;  class_746 player = client.field_1724;
/* 103 */     if (player.method_6032() <= 0.0F && !this.deathPending) {
/* 104 */       this.deathPending = true;
/* 105 */       recordDeath(client, player);
/* 106 */     } else if (player.method_6032() > 0.0F && this.deathPending) {
/*     */ 
/*     */       
/* 109 */       this.deathPending = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void recordDeath(class_310 client, class_746 player) {
/* 115 */     class_2338 pos = player.method_24515();
/*     */ 
/*     */     
/* 118 */     String dim = (client.field_1687 != null) ? client.field_1687.method_27983().method_29177().method_12832() : "unknown";
/* 119 */     boolean shortFmt = "Short".equalsIgnoreCase(this.format.getSelected());
/*     */ 
/*     */     
/* 122 */     String line = shortFmt ? ("" + pos.method_10263() + " " + pos.method_10263() + " " + pos.method_10264()) : ("X: " + pos.method_10263() + " Y: " + pos.method_10264() + " Z: " + pos.method_10260() + ", " + dim);
/*     */     
/* 124 */     String mode = this.outputMode.getSelected();
/* 125 */     if (!"Clipboard".equalsIgnoreCase(mode)) {
/*     */       
/* 127 */       String prefix = (this.customPrefix.getText() == null || this.customPrefix.getText().isEmpty()) ? "[Death]" : this.customPrefix.getText();
/* 128 */       String chatLine = prefix + " " + prefix;
/*     */ 
/*     */ 
/*     */       
/* 132 */       String colored = this.colorMessage.isValue() ? ("§c" + chatLine) : chatLine;
/* 133 */       client.field_1705.method_1743().method_1812((class_2561)class_2561.method_43470(colored));
/*     */     } 
/* 135 */     if (!"Chat".equalsIgnoreCase(mode)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       String clip = shortFmt ? line : ("" + pos.method_10263() + " " + pos.method_10263() + " " + pos.method_10264());
/* 141 */       client.field_1774.method_1455(clip);
/*     */     } 
/* 143 */     if (this.playSound.isValue())
/* 144 */       client.field_1724.method_5783(class_3417.field_14627, 1.0F, 1.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\DeathCoords.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */