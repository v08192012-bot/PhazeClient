/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3414;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5251;
/*     */ import net.minecraft.class_7417;
/*     */ import net.minecraft.class_8828;
/*     */ import vorga.phazeclient.api.feature.module.Module;
/*     */ import vorga.phazeclient.api.feature.module.ModuleCategory;
/*     */ import vorga.phazeclient.api.feature.module.setting.Setting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.BooleanSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ColorSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.SectionSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.TextSetting;
/*     */ import vorga.phazeclient.api.feature.module.setting.implement.ValueSetting;
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
/*     */ public final class MentionHighlight
/*     */   extends Module
/*     */ {
/*  67 */   private static final MentionHighlight INSTANCE = new MentionHighlight();
/*     */   
/*  69 */   public final SectionSetting matchSection = new SectionSetting("Match");
/*  70 */   public final BooleanSetting matchUsername = (new BooleanSetting("Match Your Username", "Highlight when someone says your in-game name"))
/*     */ 
/*     */     
/*  73 */     .setValue(true);
/*  74 */   public final TextSetting extraTriggers = (new TextSetting("Extra Triggers", "Comma-separated list of extra words that count as a mention (case-insensitive)"))
/*     */ 
/*     */     
/*  77 */     .setText("");
/*     */   
/*  79 */   public final SectionSetting visualSection = new SectionSetting("Visual");
/*  80 */   public final BooleanSetting recolor = (new BooleanSetting("Recolor Match", "Tint the matched word with the highlight color"))
/*     */ 
/*     */     
/*  83 */     .setValue(true);
/*  84 */   public final ColorSetting highlightColor = (new ColorSetting("Highlight Color", "Color applied to matched substrings"))
/*     */ 
/*     */     
/*  87 */     .setColor(-22016).popupRow();
/*  88 */   public final BooleanSetting bold = (new BooleanSetting("Bold", "Render the matched substring in bold"))
/*     */ 
/*     */     
/*  91 */     .setValue(true);
/*  92 */   public final BooleanSetting underline = (new BooleanSetting("Underline", "Underline the matched substring"))
/*     */ 
/*     */     
/*  95 */     .setValue(false);
/*     */   
/*  97 */   public final SectionSetting soundSection = new SectionSetting("Sound");
/*  98 */   public final BooleanSetting playSound = (new BooleanSetting("Play Sound", "Ping sound when a mention is received"))
/*     */ 
/*     */     
/* 101 */     .setValue(true);
/* 102 */   public final ValueSetting volume = (new ValueSetting("Volume", "Sound volume (0.0 = mute, 1.0 = full)"))
/*     */ 
/*     */     
/* 105 */     .range(0.0F, 1.0F).step(0.05F).setValue(0.6F);
/* 106 */   public final ValueSetting pitch = (new ValueSetting("Pitch", "Sound pitch multiplier"))
/*     */ 
/*     */     
/* 109 */     .range(0.5F, 2.0F).step(0.05F).setValue(1.4F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long SOUND_COOLDOWN_MS = 350L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long ECHO_WINDOW_MS = 2000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private volatile String lastOutgoing = null;
/* 137 */   private volatile long lastOutgoingAtMs = 0L;
/*     */   
/* 139 */   private long lastSoundAtMs = 0L;
/* 140 */   private List<Pattern> compiledTriggers = null;
/* 141 */   private String compiledTriggersFromText = null;
/*     */   
/*     */   private MentionHighlight() {
/* 144 */     super("mention_highlight", "Mention Highlight", ModuleCategory.UTILITIES);
/*     */     
/* 146 */     this.matchUsername.setFullWidth(true);
/* 147 */     this.extraTriggers.setFullWidth(true);
/* 148 */     this.recolor.setFullWidth(true);
/* 149 */     this.highlightColor.setFullWidth(true);
/* 150 */     this.bold.setFullWidth(true);
/* 151 */     this.underline.setFullWidth(true);
/* 152 */     this.playSound.setFullWidth(true);
/* 153 */     this.volume.setFullWidth(true);
/* 154 */     this.pitch.setFullWidth(true);
/*     */     
/* 156 */     setup(new Setting[] { (Setting)this.matchSection, (Setting)this.matchUsername, (Setting)this.extraTriggers, (Setting)this.visualSection, (Setting)this.recolor, (Setting)this.highlightColor, (Setting)this.bold, (Setting)this.underline, (Setting)this.soundSection, (Setting)this.playSound, (Setting)this.volume, (Setting)this.pitch });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MentionHighlight getInstance() {
/* 164 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 169 */     return "Highlights mentions of your name in chat and plays a ping sound";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcon() {
/* 174 */     return "mention_highlight.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIconSize() {
/* 179 */     return 21.0F;
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
/*     */   public void markOutgoing(String content) {
/* 194 */     if (!isEnabled() || content == null) {
/*     */       return;
/*     */     }
/* 197 */     this.lastOutgoing = normalizeForEcho(content);
/* 198 */     this.lastOutgoingAtMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markOutgoing() {
/* 207 */     markOutgoing("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2561 processIncoming(class_2561 original) {
/* 216 */     if (!isEnabled() || original == null) {
/* 217 */       return original;
/*     */     }
/*     */     
/* 220 */     String flat = original.getString();
/* 221 */     if (flat == null || flat.isEmpty()) {
/* 222 */       return original;
/*     */     }
/* 224 */     String normalizedFlat = normalizeForEcho(flat);
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
/* 235 */     class_310 mcc = class_310.method_1551();
/*     */ 
/*     */     
/* 238 */     String selfName = (mcc != null && mcc.field_1724 != null) ? mcc.field_1724.method_7334().getName() : null;
/* 239 */     if (selfName != null && !selfName.isEmpty() && isSelfAuthored(flat, selfName)) {
/* 240 */       return original;
/*     */     }
/*     */     
/* 243 */     List<Pattern> triggers = resolveTriggers();
/* 244 */     boolean hit = false;
/* 245 */     for (Pattern p : triggers) {
/* 246 */       if (p.matcher(flat).find()) {
/* 247 */         hit = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 251 */     if (!hit) {
/* 252 */       return original;
/*     */     }
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
/* 264 */     long now = System.currentTimeMillis();
/* 265 */     String pending = this.lastOutgoing;
/* 266 */     if (pending != null && !pending.isEmpty()) {
/* 267 */       long age = now - this.lastOutgoingAtMs;
/* 268 */       if (age > 2000L) {
/*     */ 
/*     */         
/* 271 */         this.lastOutgoing = null;
/* 272 */       } else if (matchesOutgoingEcho(normalizedFlat, pending)) {
/* 273 */         this.lastOutgoing = null;
/* 274 */         return original;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     if (this.playSound.isValue() && now - this.lastSoundAtMs >= 350L) {
/* 282 */       playPingSound();
/* 283 */       this.lastSoundAtMs = now;
/*     */     } 
/*     */     
/* 286 */     if (!this.recolor.isValue() && !this.bold.isValue() && !this.underline.isValue())
/*     */     {
/*     */ 
/*     */       
/* 290 */       return original;
/*     */     }
/*     */     
/* 293 */     return rebuildWithHighlights(original, triggers);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Pattern> resolveTriggers() {
/* 299 */     class_310 mc = class_310.method_1551();
/*     */ 
/*     */     
/* 302 */     String username = (mc != null && mc.field_1724 != null && this.matchUsername.isValue()) ? mc.field_1724.method_7334().getName() : null;
/* 303 */     String extras = this.extraTriggers.getText();
/* 304 */     String key = ((username == null) ? "" : username) + "\000" + ((username == null) ? "" : username);
/*     */     
/* 306 */     if (this.compiledTriggers != null && key.equals(this.compiledTriggersFromText)) {
/* 307 */       return this.compiledTriggers;
/*     */     }
/*     */     
/* 310 */     List<Pattern> built = new ArrayList<>();
/* 311 */     if (username != null && !username.isEmpty())
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 316 */       built.add(Pattern.compile(Pattern.quote(username), 2));
/*     */     }
/* 318 */     if (extras != null) {
/* 319 */       for (String raw : extras.split(",")) {
/* 320 */         String t = raw.trim();
/* 321 */         if (!t.isEmpty())
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 326 */           built.add(Pattern.compile("\\b" + Pattern.quote(t) + "\\b", 2));
/*     */         }
/*     */       } 
/*     */     }
/* 330 */     this.compiledTriggers = built;
/* 331 */     this.compiledTriggersFromText = key;
/* 332 */     return built;
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
/*     */   private class_2561 rebuildWithHighlights(class_2561 original, List<Pattern> triggers) {
/* 345 */     class_2583 highlightStyle = buildHighlightStyle();
/* 346 */     return walk(original, triggers, highlightStyle);
/*     */   }
/*     */   private class_2561 walk(class_2561 node, List<Pattern> triggers, class_2583 highlight) {
/*     */     class_5250 acc;
/* 350 */     class_7417 class_7417 = node.method_10851(); class_8828 ptc = (class_8828)class_7417;
/*     */     
/* 352 */     class_2561 rewritten = (class_7417 instanceof class_8828) ? rewriteSegment(ptc.comp_737(), node.method_10866(), triggers, highlight) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     if (rewritten != null) {
/* 360 */       acc = rewritten.method_27661();
/*     */     } else {
/* 362 */       acc = node.method_27662().method_10862(node.method_10866());
/*     */     } 
/* 364 */     for (class_2561 sibling : node.method_10855()) {
/* 365 */       acc.method_10852(walk(sibling, triggers, highlight));
/*     */     }
/* 367 */     return (class_2561)acc;
/*     */   }
/*     */   
/*     */   private class_2561 rewriteSegment(String segment, class_2583 baseStyle, List<Pattern> triggers, class_2583 highlight) {
/* 371 */     if (segment == null || segment.isEmpty()) {
/* 372 */       return (class_2561)class_2561.method_43473().method_10862(baseStyle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     Pattern combined = combine(triggers);
/* 380 */     Matcher m = combined.matcher(segment);
/* 381 */     if (!m.find()) {
/* 382 */       return (class_2561)class_2561.method_43470(segment).method_10862(baseStyle);
/*     */     }
/* 384 */     m.reset();
/*     */     
/* 386 */     class_5250 out = class_2561.method_43473().method_10862(baseStyle);
/* 387 */     int last = 0;
/* 388 */     while (m.find()) {
/* 389 */       int start = m.start();
/* 390 */       int end = m.end();
/* 391 */       if (start > last) {
/* 392 */         out.method_10852((class_2561)class_2561.method_43470(segment.substring(last, start)).method_10862(baseStyle));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 397 */       class_2583 merged = baseStyle.method_27702(highlight);
/* 398 */       out.method_10852((class_2561)class_2561.method_43470(segment.substring(start, end)).method_10862(merged));
/* 399 */       last = end;
/*     */     } 
/* 401 */     if (last < segment.length()) {
/* 402 */       out.method_10852((class_2561)class_2561.method_43470(segment.substring(last)).method_10862(baseStyle));
/*     */     }
/* 404 */     return (class_2561)out;
/*     */   }
/*     */   
/*     */   private Pattern combine(List<Pattern> triggers) {
/* 408 */     if (triggers.size() == 1) return triggers.get(0); 
/* 409 */     StringBuilder sb = new StringBuilder();
/* 410 */     for (int i = 0; i < triggers.size(); i++) {
/* 411 */       if (i > 0) sb.append('|'); 
/* 412 */       sb.append("(?:").append(((Pattern)triggers.get(i)).pattern()).append(')');
/*     */     } 
/* 414 */     return Pattern.compile(sb.toString(), 2);
/*     */   }
/*     */   
/*     */   private class_2583 buildHighlightStyle() {
/* 418 */     class_2583 s = class_2583.field_24360;
/* 419 */     if (this.recolor.isValue()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 424 */       int rgb = this.highlightColor.getColor() & 0xFFFFFF;
/* 425 */       s = s.method_27703(class_5251.method_27717(rgb));
/*     */     } 
/* 427 */     if (this.bold.isValue()) {
/* 428 */       s = s.method_27706(class_124.field_1067);
/*     */     }
/* 430 */     if (this.underline.isValue()) {
/* 431 */       s = s.method_27706(class_124.field_1073);
/*     */     }
/* 433 */     return s;
/*     */   }
/*     */   
/*     */   private void playPingSound() {
/* 437 */     class_310 mc = class_310.method_1551();
/* 438 */     if (mc == null)
/* 439 */       return;  class_3414 event = (class_3414)class_3417.field_14622.comp_349();
/* 440 */     float v = clamp(this.volume.getValue(), 0.0F, 1.0F);
/* 441 */     float p = clamp(this.pitch.getValue(), 0.5F, 2.0F);
/* 442 */     mc.method_1483().method_4873((class_1113)class_1109.method_4757(event, p, v));
/*     */   }
/*     */   
/*     */   private static float clamp(float v, float lo, float hi) {
/* 446 */     if (v < lo) return lo; 
/* 447 */     if (v > hi) return hi; 
/* 448 */     return v;
/*     */   }
/*     */   
/*     */   private static String normalizeForEcho(String s) {
/* 452 */     if (s == null || s.isEmpty()) return ""; 
/* 453 */     StringBuilder out = new StringBuilder(s.length());
/* 454 */     boolean prevSpace = false;
/* 455 */     for (int i = 0; i < s.length(); i++) {
/* 456 */       char c = Character.toLowerCase(s.charAt(i));
/* 457 */       if (Character.isLetterOrDigit(c)) {
/* 458 */         out.append(c);
/* 459 */         prevSpace = false;
/*     */       
/*     */       }
/* 462 */       else if (Character.isWhitespace(c) && 
/* 463 */         !prevSpace) {
/* 464 */         out.append(' ');
/* 465 */         prevSpace = true;
/*     */       } 
/*     */     } 
/*     */     
/* 469 */     int start = 0;
/* 470 */     int end = out.length();
/* 471 */     for (; start < end && out.charAt(start) == ' '; start++);
/* 472 */     for (; end > start && out.charAt(end - 1) == ' '; end--);
/* 473 */     return out.substring(start, end);
/*     */   }
/*     */   
/*     */   private static boolean matchesOutgoingEcho(String incoming, String pendingOutgoing) {
/* 477 */     if (incoming == null || pendingOutgoing == null || incoming
/* 478 */       .isEmpty() || pendingOutgoing.isEmpty()) {
/* 479 */       return false;
/*     */     }
/* 481 */     if (incoming.contains(pendingOutgoing) || pendingOutgoing.contains(incoming)) {
/* 482 */       return true;
/*     */     }
/*     */     
/* 485 */     if (pendingOutgoing.length() < 6) {
/* 486 */       return false;
/*     */     }
/* 488 */     String[] tokens = pendingOutgoing.split(" ");
/* 489 */     int matched = 0;
/* 490 */     for (String t : tokens) {
/* 491 */       if (t.length() >= 3 && 
/* 492 */         incoming.contains(t)) matched++; 
/*     */     } 
/* 494 */     return (matched >= 2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSelfAuthored(String flat, String selfName) {
/* 523 */     int max = Math.min(flat.length(), 80);
/* 524 */     int sep = -1;
/* 525 */     for (int i = 0; i < max; i++) {
/* 526 */       char c = flat.charAt(i);
/* 527 */       if (c == ':' || c == '»' || c == '>' || c == '›') {
/* 528 */         sep = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 532 */     if (sep < 0)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 537 */       return false;
/*     */     }
/* 539 */     String header = flat.substring(0, sep);
/* 540 */     String lowerHeader = header.toLowerCase();
/* 541 */     String lowerName = selfName.toLowerCase();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 546 */     int idx = lowerHeader.indexOf(lowerName);
/* 547 */     while (idx >= 0) {
/* 548 */       boolean leftOk = (idx == 0 || !Character.isLetterOrDigit(lowerHeader.charAt(idx - 1)));
/* 549 */       int after = idx + lowerName.length();
/*     */       
/* 551 */       boolean rightOk = (after >= lowerHeader.length() || !Character.isLetterOrDigit(lowerHeader.charAt(after)));
/* 552 */       if (leftOk && rightOk) {
/* 553 */         return true;
/*     */       }
/* 555 */       idx = lowerHeader.indexOf(lowerName, idx + 1);
/*     */     } 
/* 557 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\MentionHighlight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */