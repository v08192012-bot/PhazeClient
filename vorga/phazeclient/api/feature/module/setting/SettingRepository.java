/*    */ package vorga.phazeclient.api.feature.module.setting;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import vorga.phazeclient.base.trait.Setupable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SettingRepository
/*    */   implements Setupable
/*    */ {
/* 13 */   private final List<Setting> settings = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public void setup(Setting... setting) {
/* 17 */     this.settings.addAll(Arrays.asList(setting));
/*    */   }
/*    */   
/*    */   public Setting get(String name) {
/* 21 */     return this.settings.stream()
/* 22 */       .filter(setting -> setting.getNameKey().equalsIgnoreCase(name))
/* 23 */       .findFirst()
/* 24 */       .orElse(null);
/*    */   }
/*    */   
/*    */   public List<Setting> settings() {
/* 28 */     return this.settings;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\SettingRepository.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */