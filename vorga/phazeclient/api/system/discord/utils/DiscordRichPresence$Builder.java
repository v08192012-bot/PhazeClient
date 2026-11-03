/*     */ package vorga.phazeclient.api.system.discord.utils;
/*     */ 
/*     */ import java.time.OffsetDateTime;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*  41 */   private final DiscordRichPresence richPresence = new DiscordRichPresence();
/*     */   
/*     */   public Builder setSmallImage(String var1) {
/*  44 */     return setSmallImage(var1, "");
/*     */   }
/*     */   
/*     */   public Builder setDetails(String var1) {
/*  48 */     if (var1 != null && !var1.isEmpty()) {
/*  49 */       this.richPresence.details = var1.substring(0, Math.min(var1.length(), 128));
/*     */     }
/*     */     
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setLargeImage(String var1, String var2) {
/*  56 */     this.richPresence.largeImageKey = var1;
/*  57 */     this.richPresence.largeImageText = var2;
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setState(String var1) {
/*  62 */     if (var1 != null && !var1.isEmpty()) {
/*  63 */       this.richPresence.state = var1.substring(0, Math.min(var1.length(), 128));
/*     */     }
/*     */     
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setInstance(boolean var1) {
/*  70 */     if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
/*  71 */       this.richPresence.instance = var1 ? 1 : 0;
/*     */     }
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setButtons(RPCButton var1) {
/*  77 */     return setButtons(Collections.singletonList(var1));
/*     */   }
/*     */   
/*     */   public Builder setSmallImage(String var1, String var2) {
/*  81 */     this.richPresence.smallImageKey = var1;
/*  82 */     this.richPresence.smallImageText = var2;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setButtons(List<RPCButton> buttons) {
/*  87 */     if (buttons != null && !buttons.isEmpty()) {
/*  88 */       int var2 = Math.min(buttons.size(), 2);
/*  89 */       this.richPresence.button_label_1 = ((RPCButton)buttons.get(0)).getLabel();
/*  90 */       this.richPresence.button_url_1 = ((RPCButton)buttons.get(0)).getUrl();
/*  91 */       if (var2 == 2) {
/*  92 */         this.richPresence.button_label_2 = ((RPCButton)buttons.get(1)).getLabel();
/*  93 */         this.richPresence.button_url_2 = ((RPCButton)buttons.get(1)).getUrl();
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setStartTimestamp(OffsetDateTime var1) {
/* 101 */     this.richPresence.startTimestamp = var1.toEpochSecond();
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setSecrets(String var1, String var2, String var3) {
/* 106 */     if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
/* 107 */       this.richPresence.matchSecret = var1;
/* 108 */       this.richPresence.joinSecret = var2;
/* 109 */       this.richPresence.spectateSecret = var3;
/*     */     } 
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setButtons(RPCButton var1, RPCButton var2) {
/* 115 */     setButtons(Arrays.asList(new RPCButton[] { var1, var2 }));
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setStartTimestamp(long var1) {
/* 120 */     this.richPresence.startTimestamp = var1;
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setSecrets(String var1, String var2) {
/* 125 */     if ((this.richPresence.button_label_1 == null || !this.richPresence.button_label_1.isEmpty()) && (this.richPresence.button_label_2 == null || !this.richPresence.button_label_2.isEmpty())) {
/* 126 */       this.richPresence.joinSecret = var1;
/* 127 */       this.richPresence.spectateSecret = var2;
/*     */     } 
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setEndTimestamp(long var1) {
/* 133 */     this.richPresence.endTimestamp = var1;
/* 134 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setEndTimestamp(OffsetDateTime var1) {
/* 138 */     this.richPresence.endTimestamp = var1.toEpochSecond();
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setLargeImage(String var1) {
/* 143 */     return setLargeImage(var1, "");
/*     */   }
/*     */   
/*     */   public DiscordRichPresence build() {
/* 147 */     return this.richPresence;
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\discor\\utils\DiscordRichPresence$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */