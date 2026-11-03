/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5250;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.TextSetting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NickHider
/*     */   extends Module
/*     */ {
/*  24 */   private static final NickHider INSTANCE = new NickHider();
/*     */   
/*  26 */   public final SectionSetting generalSection = new SectionSetting("General");
/*  27 */   public final TextSetting replacement = (new TextSetting("Replacement", "Text shown in place of your username"))
/*     */ 
/*     */     
/*  30 */     .setText("Phaze").setMax(16);
/*     */   
/*     */   private NickHider() {
/*  33 */     super("nick_hider", "Nick Hider", ModuleCategory.UTILITIES);
/*  34 */     this.replacement.setFullWidth(true);
/*  35 */     setup(new Setting[] { (Setting)this.generalSection, (Setting)this.replacement });
/*     */   }
/*     */   
/*     */   public static NickHider getInstance() {
/*  39 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  44 */     return "Replaces your own username in chat with a custom string";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/*  49 */     return "nick_hider.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/*  54 */     return 21.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String selfName() {
/*  62 */     class_310 mc = class_310.method_1551();
/*  63 */     if (mc == null || mc.method_1548() == null) {
/*  64 */       return null;
/*     */     }
/*  66 */     String name = mc.method_1548().method_1676();
/*  67 */     return (name == null || name.isEmpty()) ? null : name;
/*     */   }
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
/*     */   public class_2561 rewrite(class_2561 original) {
/*  89 */     if (!isEnabled() || original == null) {
/*  90 */       return original;
/*     */     }
/*  92 */     String name = selfName();
/*  93 */     if (name == null) {
/*  94 */       return original;
/*     */     }
/*  96 */     String full = original.getString();
/*  97 */     if (!full.contains(name)) {
/*  98 */       return original;
/*     */     }
/* 100 */     List<Integer> matches = findExactMatches(full, name);
/* 101 */     if (matches.isEmpty()) {
/* 102 */       return original;
/*     */     }
/*     */     
/* 105 */     String repl = this.replacement.getText();
/* 106 */     if (repl == null) {
/* 107 */       repl = "";
/*     */     }
/* 109 */     String replFinal = repl;
/* 110 */     int nameLen = name.length();
/* 111 */     HashSet<Integer> matchStarts = new HashSet<>(matches);
/* 112 */     class_5250 rebuilt = class_2561.method_43473();
/* 113 */     int[] globalPos = { 0 };
/* 114 */     int[] skipRemaining = { 0 };
/* 115 */     original.method_27658((style, fragment) -> { if (fragment == null || fragment.isEmpty()) return Optional.empty();  StringBuilder out = new StringBuilder(fragment.length()); for (int i = 0; i < fragment.length(); i++) { int pos = globalPos[0]; if (skipRemaining[0] > 0) { skipRemaining[0] = skipRemaining[0] - 1; globalPos[0] = globalPos[0] + 1; } else if (matchStarts.contains(Integer.valueOf(pos))) { out.append(replFinal); skipRemaining[0] = nameLen - 1; globalPos[0] = globalPos[0] + 1; } else { out.append(fragment.charAt(i)); globalPos[0] = globalPos[0] + 1; }  }  rebuilt.method_10852((class_2561)class_2561.method_43470(out.toString()).method_10862(style)); return Optional.empty(); }original
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
/* 138 */         .method_10866());
/* 139 */     return (class_2561)rebuilt;
/*     */   }
/*     */   
/*     */   private static List<Integer> findExactMatches(String text, String name) {
/* 143 */     ArrayList<Integer> out = new ArrayList<>();
/* 144 */     int from = 0;
/* 145 */     int len = name.length();
/*     */     while (true) {
/* 147 */       int idx = text.indexOf(name, from);
/* 148 */       if (idx < 0) {
/* 149 */         return out;
/*     */       }
/* 151 */       int end = idx + len;
/* 152 */       if (isNameBoundary(text, idx - 1) && isNameBoundary(text, end)) {
/* 153 */         out.add(Integer.valueOf(idx));
/*     */       }
/* 155 */       from = end;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isNameBoundary(String s, int index) {
/* 160 */     if (index < 0 || index >= s.length()) {
/* 161 */       return true;
/*     */     }
/* 163 */     char c = s.charAt(index);
/* 164 */     return (c != '_' && !Character.isLetterOrDigit(c));
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\NickHider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */