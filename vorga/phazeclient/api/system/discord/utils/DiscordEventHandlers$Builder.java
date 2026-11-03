/*    */ package vorga.phazeclient.api.system.discord.utils;
/*    */ 
/*    */ import com.sun.jna.Callback;
/*    */ import com.sun.jna.CallbackReference;
/*    */ import vorga.phazeclient.api.system.discord.callbacks.DisconnectedCallback;
/*    */ import vorga.phazeclient.api.system.discord.callbacks.ErroredCallback;
/*    */ import vorga.phazeclient.api.system.discord.callbacks.JoinGameCallback;
/*    */ import vorga.phazeclient.api.system.discord.callbacks.JoinRequestCallback;
/*    */ import vorga.phazeclient.api.system.discord.callbacks.ReadyCallback;
/*    */ import vorga.phazeclient.api.system.discord.callbacks.SpectateGameCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 24 */   private final DiscordEventHandlers handlers = new DiscordEventHandlers();
/*    */   
/*    */   public DiscordEventHandlers build() {
/* 27 */     return this.handlers;
/*    */   }
/*    */   
/*    */   public Builder disconnected(DisconnectedCallback var1) {
/* 31 */     this.handlers.disconnected = CallbackReference.getFunctionPointer((Callback)var1);
/* 32 */     return this;
/*    */   }
/*    */   
/*    */   public Builder errored(ErroredCallback var1) {
/* 36 */     this.handlers.errored = CallbackReference.getFunctionPointer((Callback)var1);
/* 37 */     return this;
/*    */   }
/*    */   
/*    */   public Builder ready(ReadyCallback var1) {
/* 41 */     this.handlers.ready = CallbackReference.getFunctionPointer((Callback)var1);
/* 42 */     return this;
/*    */   }
/*    */   
/*    */   public Builder joinRequest(JoinRequestCallback var1) {
/* 46 */     this.handlers.joinRequest = CallbackReference.getFunctionPointer((Callback)var1);
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   public Builder joinGame(JoinGameCallback var1) {
/* 51 */     this.handlers.joinGame = CallbackReference.getFunctionPointer((Callback)var1);
/* 52 */     return this;
/*    */   }
/*    */   
/*    */   public Builder spectateGame(SpectateGameCallback var1) {
/* 56 */     this.handlers.spectateGame = CallbackReference.getFunctionPointer((Callback)var1);
/* 57 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\discor\\utils\DiscordEventHandlers$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */