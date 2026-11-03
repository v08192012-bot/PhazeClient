/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1269;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3414;
/*     */ import net.minecraft.class_3417;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SelectSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
/*     */ 
/*     */ public class KillSounds extends Module {
/*  20 */   private static final KillSounds INSTANCE = new KillSounds();
/*     */   
/*     */   private static boolean registered = false;
/*  23 */   public final SectionSetting generalSection = new SectionSetting("General");
/*     */   
/*  25 */   public final SelectSetting soundType = (new SelectSetting("Sound", "Kill sound effect"))
/*  26 */     .value(new String[] { "Laser", "Ding", "Destroy", "Hit", "Pop", "Crunch", "Pling", "Bell", "Orb XP", "Level Up", 
/*  27 */         "Anvil" }).selected("Laser");
/*  28 */   public final ValueSetting volume = (new ValueSetting("Volume", "Sound volume")).range(10, 100).step(5.0F).setValue(85.0F);
/*  29 */   public final ValueSetting pitch = (new ValueSetting("Pitch", "Sound pitch")).range(0.5F, 2.0F).step(0.1F).setValue(1.0F);
/*  30 */   public final BooleanSetting playersOnly = (new BooleanSetting("Players Only", "Only play when eliminating players")).setValue(true);
/*     */   
/*  32 */   private class_1309 targetEntity = null;
/*  33 */   private long lastAttackTime = 0L;
/*     */   
/*     */   private KillSounds() {
/*  36 */     super("kill_sounds", "Kill Sounds", ModuleCategory.OTHER);
/*  37 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.soundType, (Setting)this.volume, (Setting)this.pitch, (Setting)this.playersOnly });
/*  38 */     registerEvents();
/*     */   }
/*     */   
/*     */   public static KillSounds getInstance() {
/*  42 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  47 */     return "Plays crisp open-source and retro sound effects on eliminating opponents";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  52 */     return "square-sound-outline.png";
/*     */   }
/*     */   
/*     */   private synchronized void registerEvents() {
/*  56 */     if (registered)
/*  57 */       return;  registered = true;
/*     */     
/*  59 */     AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
/*     */           if (world.method_8608() && isEnabled() && entity instanceof class_1309) {
/*     */             class_1309 living = (class_1309)entity;
/*     */             this.targetEntity = living;
/*     */             this.lastAttackTime = System.currentTimeMillis();
/*     */           } 
/*     */           return (class_1269)class_1269.field_5811;
/*     */         });
/*  67 */     ClientTickEvents.END_CLIENT_TICK.register(client -> {
/*     */           if (!isEnabled() || this.targetEntity == null) {
/*     */             return;
/*     */           }
/*     */           long now = System.currentTimeMillis();
/*     */           if (now - this.lastAttackTime > 2500L) {
/*     */             this.targetEntity = null;
/*     */             return;
/*     */           } 
/*     */           if (this.targetEntity.method_29504() || this.targetEntity.method_6032() <= 0.0F) {
/*     */             if (!this.playersOnly.isValue() || this.targetEntity instanceof class_1657) {
/*     */               playKillSound(client);
/*     */             }
/*     */             this.targetEntity = null;
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void playKillSound(class_310 client) {
/*     */     class_3414 sound;
/*  87 */     switch (this.soundType.getSelected()) { case "Laser":
/*  88 */         sound = PhazeSounds.KILL_LASER; break;
/*  89 */       case "Ding": sound = PhazeSounds.KILL_DING; break;
/*  90 */       case "Destroy": sound = PhazeSounds.KILL_DESTROY; break;
/*  91 */       case "Hit": sound = PhazeSounds.KILL_HIT; break;
/*  92 */       case "Pop": sound = PhazeSounds.KILL_POP; break;
/*  93 */       case "Crunch": sound = PhazeSounds.KILL_CRUNCH; break;
/*  94 */       case "Bell": sound = class_3417.field_17265; break;
/*  95 */       case "Orb XP": sound = class_3417.field_14627; break;
/*  96 */       case "Level Up": sound = class_3417.field_14709; break;
/*  97 */       case "Anvil": sound = class_3417.field_14833; break;
/*  98 */       default: sound = (class_3414)class_3417.field_14622.comp_349();
/*     */         break; }
/*     */     
/* 101 */     float vol = this.volume.getValue() / 100.0F;
/* 102 */     float p = this.pitch.getValue();
/* 103 */     client.method_1483().method_4873((class_1113)class_1109.method_4757(sound, p, vol));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\KillSounds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */