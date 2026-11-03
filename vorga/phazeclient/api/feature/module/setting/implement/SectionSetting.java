/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ public class SectionSetting
/*    */   extends Setting {
/*    */   public SectionSetting(String name) {
/*  9 */     super(name);
/* 10 */     setSaveToConfig(false);
/* 11 */     setFullWidth(true);
/*    */   }
/*    */   
/*    */   public SectionSetting visible(Supplier<Boolean> visible) {
/* 15 */     setVisible(visible);
/* 16 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\SectionSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */