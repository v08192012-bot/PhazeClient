/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Binds
/*     */   extends Module
/*     */ {
/*  62 */   private static final Binds INSTANCE = new Binds();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SLOT_COUNT = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public final SectionSetting generalSection = new SectionSetting("Binds");
/*     */   private final BindSlot[] slots;
/*     */   
/*     */   private Binds() {
/*  76 */     super("binds", "Binds", ModuleCategory.UTILITIES);
/*     */     
/*  78 */     this.slots = new BindSlot[8];
/*  79 */     List<Setting> setupArgs = new ArrayList<>();
/*  80 */     for (int i = 0; i < 8; i++) {
/*  81 */       this.slots[i] = new BindSlot(i + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       setupArgs.add((this.slots[i]).section);
/*  90 */       setupArgs.add((this.slots[i]).bind);
/*  91 */       setupArgs.add((this.slots[i]).text);
/*     */     } 
/*  93 */     setup(setupArgs.<Setting>toArray(new Setting[0]));
/*     */   }
/*     */   
/*     */   public static Binds getInstance() {
/*  97 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 102 */     return "N keybinds that send a chat message or slash command";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 107 */     return "binds.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 112 */     return 27.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconOffsetY() {
/* 117 */     return -2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKey(int key, int action) {
/* 127 */     if (!isEnabled() || action != 1) {
/*     */       return;
/*     */     }
/* 130 */     if (key == -1) {
/*     */       return;
/*     */     }
/* 133 */     class_310 mc = class_310.method_1551();
/* 134 */     if (mc == null || mc.field_1724 == null || mc.method_1562() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (mc.field_1755 != null) {
/*     */       return;
/*     */     }
/* 145 */     for (BindSlot slot : this.slots) {
/* 146 */       if (slot.bind.getKey() == key) {
/* 147 */         slot.fire(mc);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class BindSlot
/*     */   {
/*     */     final SectionSetting section;
/*     */ 
/*     */     
/*     */     final BindSetting bind;
/*     */ 
/*     */     
/*     */     final TextSetting text;
/*     */ 
/*     */     
/*     */     BindSlot(int index) {
/* 166 */       String label = "Bind " + index;
/* 167 */       this.section = new SectionSetting(label);
/* 168 */       this.bind = new BindSetting(label + " Key", "Key that triggers " + label);
/* 169 */       this
/* 170 */         .text = (new TextSetting(label + " Message", "Chat text or /command. Empty = slot is disabled.")).setText("");
/* 171 */       this.bind.setFullWidth(true);
/* 172 */       this.text.setFullWidth(true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void fire(class_310 mc) {
/* 181 */       String payload = this.text.getText();
/* 182 */       if (payload == null) {
/*     */         return;
/*     */       }
/* 185 */       payload = payload.trim();
/* 186 */       if (payload.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       try {
/* 190 */         if (payload.startsWith("/")) {
/* 191 */           String command = payload.substring(1).trim();
/* 192 */           if (!command.isEmpty()) {
/* 193 */             mc.method_1562().method_45730(command);
/*     */           }
/*     */         } else {
/* 196 */           mc.method_1562().method_45729(payload);
/*     */         } 
/* 198 */       } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 203 */         if (mc.field_1724 != null)
/* 204 */           mc.field_1724.method_7353(
/* 205 */               (class_2561)class_2561.method_43470("[Phaze Binds] failed: " + t.getMessage())
/* 206 */               .method_27692(class_124.field_1061), false); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Binds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */