/*    */ package vorga.phazeclient.api.feature.module.setting.implement;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BindSetting
/*    */   extends Setting
/*    */ {
/*    */   public BindSetting setDefaultKey(Integer defaultKey) {
/* 12 */     this.defaultKey = defaultKey; return this; } public BindSetting setType(int type) { this.type = type; return this; }
/*    */ 
/*    */   
/* 15 */   private int key = -1; private Integer defaultKey; public int getKey() { return this.key; } public Integer getDefaultKey() {
/* 16 */     return this.defaultKey;
/* 17 */   } private int type = 1; public int getType() { return this.type; }
/*    */   
/*    */   public BindSetting(String name, String description) {
/* 20 */     super(name, description);
/*    */   }
/*    */   
/*    */   public BindSetting visible(Supplier<Boolean> visible) {
/* 24 */     setVisible(visible);
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public BindSetting setKey(int key) {
/* 29 */     if (this.defaultKey == null) {
/* 30 */       this.defaultKey = Integer.valueOf(key);
/*    */     }
/* 32 */     this.key = key;
/* 33 */     notifyChange();
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 39 */     if (this.defaultKey == null) {
/* 40 */       return false;
/*    */     }
/* 42 */     return (this.key != this.defaultKey.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 47 */     if (this.defaultKey != null)
/*    */     {
/*    */ 
/*    */ 
/*    */       
/* 52 */       setKey(this.defaultKey.intValue());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\implement\BindSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */