/*    */ package vorga.phazeclient.api.system.discord.utils;
/*    */ 
/*    */ import com.sun.jna.Structure;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DiscordUser
/*    */   extends Structure {
/*    */   public String userId;
/*    */   public String username;
/*    */   public String discriminator;
/*    */   public String avatar;
/*    */   
/*    */   public DiscordUser() {
/* 15 */     setStringEncoding("UTF-8");
/*    */   }
/*    */   
/*    */   protected List<String> getFieldOrder() {
/* 19 */     return Arrays.asList(new String[] { "userId", "username", "discriminator", "avatar" });
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\api\system\discor\\utils\DiscordUser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */