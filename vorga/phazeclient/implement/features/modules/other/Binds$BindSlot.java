/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
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
/*     */ final class BindSlot
/*     */ {
/*     */   final SectionSetting section;
/*     */   final BindSetting bind;
/*     */   final TextSetting text;
/*     */   
/*     */   BindSlot(int index) {
/* 166 */     String label = "Bind " + index;
/* 167 */     this.section = new SectionSetting(label);
/* 168 */     this.bind = new BindSetting(label + " Key", "Key that triggers " + label);
/* 169 */     this
/* 170 */       .text = (new TextSetting(label + " Message", "Chat text or /command. Empty = slot is disabled.")).setText("");
/* 171 */     this.bind.setFullWidth(true);
/* 172 */     this.text.setFullWidth(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void fire(class_310 mc) {
/* 181 */     String payload = this.text.getText();
/* 182 */     if (payload == null) {
/*     */       return;
/*     */     }
/* 185 */     payload = payload.trim();
/* 186 */     if (payload.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     try {
/* 190 */       if (payload.startsWith("/")) {
/* 191 */         String command = payload.substring(1).trim();
/* 192 */         if (!command.isEmpty()) {
/* 193 */           mc.method_1562().method_45730(command);
/*     */         }
/*     */       } else {
/* 196 */         mc.method_1562().method_45729(payload);
/*     */       } 
/* 198 */     } catch (Throwable t) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       if (mc.field_1724 != null)
/* 204 */         mc.field_1724.method_7353(
/* 205 */             (class_2561)class_2561.method_43470("[Phaze Binds] failed: " + t.getMessage())
/* 206 */             .method_27692(class_124.field_1061), false); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\Binds$BindSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */