/*    */ package vorga.phazeclient.api.feature.module.setting;
/*    */ import java.util.List;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BindSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.GroupSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.ItemPickerSetting;
/*    */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*    */ import vorga.phazeclient.implement.menu.components.implement.settings.CheckboxComponent;
/*    */ 
/*    */ public class SettingComponentAdder {
/*    */   public void addSettingComponent(List<Setting> settings, List<AbstractSettingComponent> components) {
/* 12 */     settings.forEach(setting -> {
/*    */           if (setting instanceof BooleanSetting) {
/*    */             BooleanSetting booleanSetting = (BooleanSetting)setting;
/*    */             components.add(new CheckboxComponent(booleanSetting));
/*    */           } 
/*    */           if (setting instanceof BindSetting) {
/*    */             BindSetting bindSetting = (BindSetting)setting;
/*    */             components.add(new BindComponent(bindSetting));
/*    */           } 
/*    */           if (setting instanceof ColorSetting) {
/*    */             ColorSetting colorSetting = (ColorSetting)setting;
/*    */             components.add(new ColorComponent(colorSetting));
/*    */           } 
/*    */           if (setting instanceof TextSetting) {
/*    */             TextSetting textSetting = (TextSetting)setting;
/*    */             components.add(new TextComponent(textSetting));
/*    */           } 
/*    */           if (setting instanceof ValueSetting) {
/*    */             ValueSetting valueSetting = (ValueSetting)setting;
/*    */             components.add(new ValueComponent(valueSetting));
/*    */           } 
/*    */           if (setting instanceof GroupSetting) {
/*    */             GroupSetting groupSetting = (GroupSetting)setting;
/*    */             components.add(new GroupComponent(groupSetting));
/*    */           } 
/*    */           if (setting instanceof ButtonSetting) {
/*    */             ButtonSetting buttonSetting = (ButtonSetting)setting;
/*    */             components.add(new SButtonComponent(buttonSetting));
/*    */           } 
/*    */           if (setting instanceof ItemPickerSetting) {
/*    */             ItemPickerSetting itemPickerSetting = (ItemPickerSetting)setting;
/*    */             components.add(new ItemPickerComponent(itemPickerSetting));
/*    */           } 
/*    */           if (setting instanceof SelectSetting) {
/*    */             SelectSetting selectSetting = (SelectSetting)setting;
/*    */             components.add(new SelectComponent(selectSetting));
/*    */           } 
/*    */           if (setting instanceof MultiSelectSetting) {
/*    */             MultiSelectSetting multiSelectSetting = (MultiSelectSetting)setting;
/*    */             components.add(new MultiSelectComponent(multiSelectSetting));
/*    */           } 
/*    */           if (setting instanceof MultiColorSetting) {
/*    */             MultiColorSetting multiColorSetting = (MultiColorSetting)setting;
/*    */             components.add(new MultiColorComponent(multiColorSetting));
/*    */           } 
/*    */           if (setting instanceof SectionSetting) {
/*    */             SectionSetting sectionSetting = (SectionSetting)setting;
/*    */             components.add(new SectionComponent(sectionSetting));
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\feature\module\setting\SettingComponentAdder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */