/*     */ package vorga.phazeclient.implement.menu.components.implement.other;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfFonts;
/*     */ import vorga.phazeclient.api.system.font.msdf.MsdfRenderer;
/*     */ import vorga.phazeclient.api.system.shape.ShapeProperties;
/*     */ import vorga.phazeclient.base.util.MessengerService;
/*     */ import vorga.phazeclient.implement.menu.MenuStyle;
/*     */ import vorga.phazeclient.implement.menu.components.AbstractComponent;
/*     */ 
/*     */ 
/*     */ public final class MessengerViewComponent
/*     */   extends AbstractComponent
/*     */ {
/*     */   private static final float SIDE = 12.0F;
/*     */   private static final float TOP = 46.0F;
/*     */   private static final float TEXT = 6.4F;
/*     */   private static final float SMALL = 5.3F;
/*     */   private static final int ACCENT = -6587393;
/*     */   private static final int CONTENT = -15394525;
/*     */   private static final int INPUT = -15921129;
/*  24 */   private final MessengerService service = MessengerService.getInstance();
/*     */   private boolean open;
/*  26 */   private final StringBuilder input = new StringBuilder();
/*     */   private double scroll;
/*     */   private volatile boolean sendResultReady;
/*     */   private volatile boolean sendResultSuccess;
/*     */   private boolean sendPending;
/*     */   private String pendingText;
/*     */   
/*     */   public void open() {
/*  34 */     this.open = true;
/*  35 */     this.service.start();
/*     */   }
/*     */   
/*     */   public void close() {
/*  39 */     this.open = false;
/*  40 */     this.input.setLength(0);
/*  41 */     this.sendPending = false;
/*  42 */     this.pendingText = null;
/*  43 */     this.sendResultReady = false;
/*     */   }
/*     */   
/*  46 */   public boolean isOpen() { return this.open; } public boolean shouldRender() {
/*  47 */     return this.open;
/*     */   }
/*     */   
/*     */   public void render(class_332 context, int mouseX, int mouseY, float delta) {
/*  51 */     if (!this.open)
/*  52 */       return;  if (this.sendResultReady) {
/*  53 */       this.sendResultReady = false;
/*  54 */       this.sendPending = false;
/*  55 */       if (this.sendResultSuccess && this.pendingText != null && this.input.toString().equals(this.pendingText)) {
/*  56 */         this.input.setLength(0);
/*     */       }
/*  58 */       this.pendingText = null;
/*     */     } 
/*  60 */     float left = this.x + 12.0F;
/*  61 */     float right = this.x + this.width - 12.0F;
/*  62 */     float top = this.y + 46.0F;
/*  63 */     float bottom = this.y + this.height - 12.0F;
/*  64 */     rectangle.render(ShapeProperties.create(context.method_51448(), left, top, (right - left), (bottom - top))
/*  65 */         .round(5.0F).color(applyGlobalAlpha(-15394525)).build());
/*     */     
/*  67 */     drawText(context, "GLOBAL CHAT", left + 14.0F, top + 13.0F, 6.4F, MenuStyle.TEXT_PRIMARY);
/*  68 */     drawText(context, this.service.getStatus(), right - 62.0F, top + 14.0F, 5.3F, MenuStyle.TEXT_MUTED);
/*  69 */     rectangle.render(ShapeProperties.create(context.method_51448(), (left + 12.0F), (top + 31.0F), (right - left - 24.0F), 1.0D)
/*  70 */         .color(applyGlobalAlpha(MenuStyle.BORDER)).build());
/*     */     
/*  72 */     List<MessengerService.Message> messages = this.service.getGlobalMessages();
/*  73 */     float lineY = bottom - 58.0F + (float)this.scroll;
/*  74 */     int first = Math.max(0, messages.size() - 28);
/*  75 */     for (int index = messages.size() - 1; index >= first; index--) {
/*  76 */       MessengerService.Message message = messages.get(index);
/*  77 */       String line = clip(message.senderUsername() + ": " + message.senderUsername(), 112);
/*  78 */       drawText(context, line, left + 14.0F, lineY, 5.3F, MenuStyle.TEXT_PRIMARY);
/*  79 */       lineY -= 12.0F;
/*     */     } 
/*  81 */     if (messages.isEmpty()) {
/*  82 */       drawText(context, "No messages yet. Say hello to the Phaze community.", left + 14.0F, top + 62.0F, 6.4F, MenuStyle.TEXT_MUTED);
/*     */     }
/*     */ 
/*     */     
/*  86 */     float inputY = bottom - 35.0F;
/*  87 */     float sendX = right - 58.0F;
/*  88 */     rectangle.render(ShapeProperties.create(context.method_51448(), (left + 12.0F), inputY, (right - left - 82.0F), 22.0D)
/*  89 */         .round(3.0F).color(applyGlobalAlpha(-15921129)).build());
/*  90 */     drawText(context, this.input.isEmpty() ? "Write to global chat..." : this.input.toString(), left + 19.0F, inputY + 7.0F, 5.3F, 
/*  91 */         this.input.isEmpty() ? MenuStyle.TEXT_MUTED : MenuStyle.TEXT_PRIMARY);
/*  92 */     rectangle.render(ShapeProperties.create(context.method_51448(), sendX, inputY, 58.0D, 22.0D)
/*  93 */         .round(3.0F).color(applyGlobalAlpha(-6587393)).build());
/*  94 */     drawText(context, "SEND", sendX + 15.0F, inputY + 7.0F, 5.3F, -15658984);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double mouseX, double mouseY, int button) {
/*  99 */     if (!this.open || button != 0) return false; 
/* 100 */     float sendX = this.x + this.width - 12.0F - 58.0F;
/* 101 */     float sendY = this.y + this.height - 12.0F - 35.0F;
/* 102 */     if (mouseX >= sendX && mouseX <= (sendX + 58.0F) && mouseY >= sendY && mouseY <= (sendY + 22.0F)) {
/* 103 */       submit();
/* 104 */       return true;
/*     */     } 
/* 106 */     return (mouseX >= (this.x + 12.0F) && mouseX <= (this.x + this.width - 12.0F) && mouseY >= (this.y + 46.0F) && mouseY <= (this.y + this.height - 12.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
/* 112 */     if (!this.open) return false; 
/* 113 */     this.scroll = Math.max(-220.0D, Math.min(0.0D, this.scroll + amount * 12.0D));
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
/* 119 */     if (!this.open) return false; 
/* 120 */     if (keyCode == 257 || keyCode == 335) {
/* 121 */       submit();
/* 122 */       return true;
/*     */     } 
/* 124 */     if (keyCode == 259 && !this.input.isEmpty()) {
/* 125 */       if (this.sendPending) return true; 
/* 126 */       this.input.deleteCharAt(this.input.length() - 1);
/* 127 */       return true;
/*     */     } 
/* 129 */     if (keyCode == 86 && (modifiers & 0x2) != 0) {
/* 130 */       String clipboard = (class_310.method_1551()).field_1774.method_1460();
/* 131 */       if (clipboard != null && !clipboard.isEmpty() && !this.sendPending) {
/* 132 */         String value = String.valueOf(this.input) + String.valueOf(this.input);
/* 133 */         if (value.codePointCount(0, value.length()) <= 255) this.input.append(clipboard); 
/*     */       } 
/* 135 */       return true;
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char chr, int modifiers) {
/* 142 */     if (!this.open || this.sendPending || Character.isISOControl(chr)) return false; 
/* 143 */     if (this.input.codePointCount(0, this.input.length()) < 255) this.input.append(chr); 
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   private void submit() {
/* 148 */     if (this.input.isEmpty() || this.sendPending)
/* 149 */       return;  this.pendingText = this.input.toString();
/* 150 */     this.sendPending = true;
/* 151 */     this.service.sendGlobal(this.pendingText, success -> {
/*     */           this.sendResultSuccess = success.booleanValue();
/*     */           this.sendResultReady = true;
/*     */         });
/*     */   }
/*     */   
/*     */   private void drawText(class_332 context, String text, float textX, float textY, float size, int color) {
/* 158 */     MsdfRenderer.renderText(MsdfFonts.medium(), text, size, applyGlobalAlpha(color), context
/* 159 */         .method_51448().method_23760().method_23761(), textX, textY, 0.0F);
/*     */   }
/*     */   
/*     */   private static String clip(String value, int max) {
/* 163 */     if (value == null) return ""; 
/* 164 */     return (value.length() <= max) ? value : (value.substring(0, max - 1) + "…");
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\menu\components\implement\other\MessengerViewComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */