/*     */ package vorga.phazeclient.implement.features.modules.other;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_5321;
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
/*     */ public final class BetterF3Renderer
/*     */ {
/*     */   private static final int FPS_HISTORY_SIZE = 60;
/*  48 */   private static final List<Integer> fpsHistory = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private static final int LABEL_COLOR = -5589047;
/*     */ 
/*     */   
/*     */   private static final int VALUE_DEFAULT = -1;
/*     */   
/*  56 */   private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
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
/*     */   public static void render(class_332 context) {
/*     */     // Byte code:
/*     */     //   0: invokestatic getInstance : ()Lvorga/phazeclient/implement/features/modules/other/BetterF3;
/*     */     //   3: astore_1
/*     */     //   4: aload_1
/*     */     //   5: ifnull -> 15
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual isEnabled : ()Z
/*     */     //   12: ifne -> 16
/*     */     //   15: return
/*     */     //   16: invokestatic method_1551 : ()Lnet/minecraft/class_310;
/*     */     //   19: astore_2
/*     */     //   20: aload_2
/*     */     //   21: ifnull -> 38
/*     */     //   24: aload_2
/*     */     //   25: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   28: ifnull -> 38
/*     */     //   31: aload_2
/*     */     //   32: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   35: ifnonnull -> 39
/*     */     //   38: return
/*     */     //   39: aload_2
/*     */     //   40: getfield field_1772 : Lnet/minecraft/class_327;
/*     */     //   43: ifnonnull -> 47
/*     */     //   46: return
/*     */     //   47: aload_2
/*     */     //   48: invokevirtual method_47599 : ()I
/*     */     //   51: invokestatic sampleFps : (I)V
/*     */     //   54: aload_2
/*     */     //   55: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   58: astore_3
/*     */     //   59: aload_3
/*     */     //   60: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   63: astore #4
/*     */     //   65: aload_1
/*     */     //   66: getfield colorCoding : Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   69: invokevirtual isValue : ()Z
/*     */     //   72: istore #5
/*     */     //   74: aload_1
/*     */     //   75: getfield textShadow : Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   78: invokevirtual isValue : ()Z
/*     */     //   81: istore #6
/*     */     //   83: new java/util/ArrayList
/*     */     //   86: dup
/*     */     //   87: invokespecial <init> : ()V
/*     */     //   90: astore #7
/*     */     //   92: aload_1
/*     */     //   93: getfield showFps : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   96: invokeinterface isValue : ()Z
/*     */     //   101: ifeq -> 142
/*     */     //   104: aload_2
/*     */     //   105: invokevirtual method_47599 : ()I
/*     */     //   108: istore #8
/*     */     //   110: aload #7
/*     */     //   112: ldc 'FPS'
/*     */     //   114: iload #8
/*     */     //   116: invokestatic valueOf : (I)Ljava/lang/String;
/*     */     //   119: iload #5
/*     */     //   121: ifeq -> 132
/*     */     //   124: iload #8
/*     */     //   126: invokestatic colorForFps : (I)I
/*     */     //   129: goto -> 133
/*     */     //   132: iconst_m1
/*     */     //   133: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   136: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   141: pop
/*     */     //   142: aload_1
/*     */     //   143: getfield showCoords : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   146: invokeinterface isValue : ()Z
/*     */     //   151: ifeq -> 442
/*     */     //   154: invokestatic getInstance : ()Lvorga/phazeclient/implement/features/modules/other/StreamerMode;
/*     */     //   157: invokevirtual isHideCoordinatesEnabled : ()Z
/*     */     //   160: ifeq -> 198
/*     */     //   163: aload #7
/*     */     //   165: ldc 'Pos'
/*     */     //   167: ldc 'hidden'
/*     */     //   169: iconst_m1
/*     */     //   170: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   173: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   178: pop
/*     */     //   179: aload #7
/*     */     //   181: ldc 'Chunk'
/*     */     //   183: ldc 'hidden'
/*     */     //   185: iconst_m1
/*     */     //   186: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   189: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   194: pop
/*     */     //   195: goto -> 442
/*     */     //   198: new java/util/ArrayList
/*     */     //   201: dup
/*     */     //   202: invokespecial <init> : ()V
/*     */     //   205: astore #8
/*     */     //   207: aload #8
/*     */     //   209: ldc 'Pos: '
/*     */     //   211: ldc -5589047
/*     */     //   213: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   216: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   221: pop
/*     */     //   222: aload #8
/*     */     //   224: ldc '%.2f'
/*     */     //   226: iconst_1
/*     */     //   227: anewarray java/lang/Object
/*     */     //   230: dup
/*     */     //   231: iconst_0
/*     */     //   232: aload_3
/*     */     //   233: invokevirtual method_23317 : ()D
/*     */     //   236: invokestatic valueOf : (D)Ljava/lang/Double;
/*     */     //   239: aastore
/*     */     //   240: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   243: iload #5
/*     */     //   245: ifeq -> 253
/*     */     //   248: ldc -38037
/*     */     //   250: goto -> 254
/*     */     //   253: iconst_m1
/*     */     //   254: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   257: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   262: pop
/*     */     //   263: aload #8
/*     */     //   265: ldc ' / '
/*     */     //   267: ldc -5589047
/*     */     //   269: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   272: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   277: pop
/*     */     //   278: aload #8
/*     */     //   280: ldc '%.2f'
/*     */     //   282: iconst_1
/*     */     //   283: anewarray java/lang/Object
/*     */     //   286: dup
/*     */     //   287: iconst_0
/*     */     //   288: aload_3
/*     */     //   289: invokevirtual method_23318 : ()D
/*     */     //   292: invokestatic valueOf : (D)Ljava/lang/Double;
/*     */     //   295: aastore
/*     */     //   296: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   299: iload #5
/*     */     //   301: ifeq -> 309
/*     */     //   304: ldc -9699477
/*     */     //   306: goto -> 310
/*     */     //   309: iconst_m1
/*     */     //   310: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   313: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   318: pop
/*     */     //   319: aload #8
/*     */     //   321: ldc ' / '
/*     */     //   323: ldc -5589047
/*     */     //   325: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   328: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   333: pop
/*     */     //   334: aload #8
/*     */     //   336: ldc '%.2f'
/*     */     //   338: iconst_1
/*     */     //   339: anewarray java/lang/Object
/*     */     //   342: dup
/*     */     //   343: iconst_0
/*     */     //   344: aload_3
/*     */     //   345: invokevirtual method_23321 : ()D
/*     */     //   348: invokestatic valueOf : (D)Ljava/lang/Double;
/*     */     //   351: aastore
/*     */     //   352: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   355: iload #5
/*     */     //   357: ifeq -> 365
/*     */     //   360: ldc -9724417
/*     */     //   362: goto -> 366
/*     */     //   365: iconst_m1
/*     */     //   366: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   369: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   374: pop
/*     */     //   375: aload #7
/*     */     //   377: aload #8
/*     */     //   379: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   384: pop
/*     */     //   385: aload #7
/*     */     //   387: ldc 'Chunk'
/*     */     //   389: aload #4
/*     */     //   391: invokevirtual method_10263 : ()I
/*     */     //   394: bipush #15
/*     */     //   396: iand
/*     */     //   397: aload #4
/*     */     //   399: invokevirtual method_10264 : ()I
/*     */     //   402: bipush #15
/*     */     //   404: iand
/*     */     //   405: aload #4
/*     */     //   407: invokevirtual method_10260 : ()I
/*     */     //   410: bipush #15
/*     */     //   412: iand
/*     */     //   413: aload #4
/*     */     //   415: invokevirtual method_10263 : ()I
/*     */     //   418: iconst_4
/*     */     //   419: ishr
/*     */     //   420: aload #4
/*     */     //   422: invokevirtual method_10260 : ()I
/*     */     //   425: iconst_4
/*     */     //   426: ishr
/*     */     //   427: <illegal opcode> makeConcatWithConstants : (IIIII)Ljava/lang/String;
/*     */     //   432: iconst_m1
/*     */     //   433: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   436: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   441: pop
/*     */     //   442: aload_1
/*     */     //   443: getfield showFacing : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   446: invokeinterface isValue : ()Z
/*     */     //   451: ifeq -> 509
/*     */     //   454: aload #7
/*     */     //   456: ldc 'Facing'
/*     */     //   458: aload_3
/*     */     //   459: invokestatic facingFor : (Lnet/minecraft/class_1297;)Ljava/lang/String;
/*     */     //   462: ldc 'yaw=%.1f, pitch=%.1f'
/*     */     //   464: iconst_2
/*     */     //   465: anewarray java/lang/Object
/*     */     //   468: dup
/*     */     //   469: iconst_0
/*     */     //   470: aload_3
/*     */     //   471: invokevirtual method_36454 : ()F
/*     */     //   474: invokestatic normalizedYaw : (F)F
/*     */     //   477: invokestatic valueOf : (F)Ljava/lang/Float;
/*     */     //   480: aastore
/*     */     //   481: dup
/*     */     //   482: iconst_1
/*     */     //   483: aload_3
/*     */     //   484: invokevirtual method_36455 : ()F
/*     */     //   487: invokestatic valueOf : (F)Ljava/lang/Float;
/*     */     //   490: aastore
/*     */     //   491: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   494: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   499: iconst_m1
/*     */     //   500: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   503: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   508: pop
/*     */     //   509: aload_1
/*     */     //   510: getfield showBiome : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   513: invokeinterface isValue : ()Z
/*     */     //   518: ifeq -> 571
/*     */     //   521: aload_2
/*     */     //   522: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   525: aload #4
/*     */     //   527: invokevirtual method_23753 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_6880;
/*     */     //   530: invokeinterface method_40230 : ()Ljava/util/Optional;
/*     */     //   535: <illegal opcode> apply : ()Ljava/util/function/Function;
/*     */     //   540: invokevirtual map : (Ljava/util/function/Function;)Ljava/util/Optional;
/*     */     //   543: ldc_w 'unknown'
/*     */     //   546: invokevirtual orElse : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   549: checkcast java/lang/String
/*     */     //   552: astore #8
/*     */     //   554: aload #7
/*     */     //   556: ldc_w 'Biome'
/*     */     //   559: aload #8
/*     */     //   561: iconst_m1
/*     */     //   562: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   565: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   570: pop
/*     */     //   571: aload_1
/*     */     //   572: getfield showLight : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   575: invokeinterface isValue : ()Z
/*     */     //   580: ifeq -> 635
/*     */     //   583: aload_2
/*     */     //   584: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   587: getstatic net/minecraft/class_1944.field_9282 : Lnet/minecraft/class_1944;
/*     */     //   590: aload #4
/*     */     //   592: invokevirtual method_8314 : (Lnet/minecraft/class_1944;Lnet/minecraft/class_2338;)I
/*     */     //   595: istore #8
/*     */     //   597: aload_2
/*     */     //   598: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   601: getstatic net/minecraft/class_1944.field_9284 : Lnet/minecraft/class_1944;
/*     */     //   604: aload #4
/*     */     //   606: invokevirtual method_8314 : (Lnet/minecraft/class_1944;Lnet/minecraft/class_2338;)I
/*     */     //   609: istore #9
/*     */     //   611: aload #7
/*     */     //   613: ldc_w 'Light'
/*     */     //   616: iload #8
/*     */     //   618: iload #9
/*     */     //   620: <illegal opcode> makeConcatWithConstants : (II)Ljava/lang/String;
/*     */     //   625: iconst_m1
/*     */     //   626: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   629: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   634: pop
/*     */     //   635: aload_1
/*     */     //   636: getfield showDimension : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   639: invokeinterface isValue : ()Z
/*     */     //   644: ifeq -> 675
/*     */     //   647: aload #7
/*     */     //   649: ldc_w 'Dimension'
/*     */     //   652: aload_2
/*     */     //   653: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   656: invokevirtual method_27983 : ()Lnet/minecraft/class_5321;
/*     */     //   659: invokevirtual method_29177 : ()Lnet/minecraft/class_2960;
/*     */     //   662: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   665: iconst_m1
/*     */     //   666: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   669: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   674: pop
/*     */     //   675: aload_1
/*     */     //   676: getfield showServer : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   679: invokeinterface isValue : ()Z
/*     */     //   684: ifeq -> 887
/*     */     //   687: ldc_w 'Singleplayer'
/*     */     //   690: astore #8
/*     */     //   692: aload_2
/*     */     //   693: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
/*     */     //   696: ifnull -> 715
/*     */     //   699: aload_2
/*     */     //   700: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
/*     */     //   703: ifnull -> 715
/*     */     //   706: aload_2
/*     */     //   707: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
/*     */     //   710: getfield field_3761 : Ljava/lang/String;
/*     */     //   713: astore #8
/*     */     //   715: iconst_m1
/*     */     //   716: istore #9
/*     */     //   718: aload_2
/*     */     //   719: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
/*     */     //   722: ifnull -> 760
/*     */     //   725: aload_2
/*     */     //   726: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   729: ifnull -> 760
/*     */     //   732: aload_2
/*     */     //   733: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
/*     */     //   736: aload_2
/*     */     //   737: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   740: invokevirtual method_5667 : ()Ljava/util/UUID;
/*     */     //   743: invokevirtual method_2871 : (Ljava/util/UUID;)Lnet/minecraft/class_640;
/*     */     //   746: astore #10
/*     */     //   748: aload #10
/*     */     //   750: ifnull -> 760
/*     */     //   753: aload #10
/*     */     //   755: invokevirtual method_2959 : ()I
/*     */     //   758: istore #9
/*     */     //   760: iload #5
/*     */     //   762: ifeq -> 778
/*     */     //   765: iload #9
/*     */     //   767: iflt -> 778
/*     */     //   770: iload #9
/*     */     //   772: invokestatic colorForPing : (I)I
/*     */     //   775: goto -> 779
/*     */     //   778: iconst_m1
/*     */     //   779: istore #10
/*     */     //   781: new java/util/ArrayList
/*     */     //   784: dup
/*     */     //   785: invokespecial <init> : ()V
/*     */     //   788: astore #11
/*     */     //   790: aload #11
/*     */     //   792: ldc_w 'Server: '
/*     */     //   795: ldc -5589047
/*     */     //   797: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   800: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   805: pop
/*     */     //   806: aload #11
/*     */     //   808: aload #8
/*     */     //   810: iconst_m1
/*     */     //   811: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   814: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   819: pop
/*     */     //   820: iload #9
/*     */     //   822: iflt -> 877
/*     */     //   825: aload #11
/*     */     //   827: ldc_w ' ('
/*     */     //   830: ldc -5589047
/*     */     //   832: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   835: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   840: pop
/*     */     //   841: aload #11
/*     */     //   843: iload #9
/*     */     //   845: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
/*     */     //   850: iload #10
/*     */     //   852: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   855: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   860: pop
/*     */     //   861: aload #11
/*     */     //   863: ldc_w ')'
/*     */     //   866: ldc -5589047
/*     */     //   868: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   871: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   876: pop
/*     */     //   877: aload #7
/*     */     //   879: aload #11
/*     */     //   881: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   886: pop
/*     */     //   887: aload_1
/*     */     //   888: getfield showMemory : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   891: invokeinterface isValue : ()Z
/*     */     //   896: ifeq -> 1071
/*     */     //   899: invokestatic getRuntime : ()Ljava/lang/Runtime;
/*     */     //   902: invokevirtual maxMemory : ()J
/*     */     //   905: lstore #8
/*     */     //   907: invokestatic getRuntime : ()Ljava/lang/Runtime;
/*     */     //   910: invokevirtual totalMemory : ()J
/*     */     //   913: lstore #10
/*     */     //   915: invokestatic getRuntime : ()Ljava/lang/Runtime;
/*     */     //   918: invokevirtual freeMemory : ()J
/*     */     //   921: lstore #12
/*     */     //   923: lload #10
/*     */     //   925: lload #12
/*     */     //   927: lsub
/*     */     //   928: lstore #14
/*     */     //   930: lload #14
/*     */     //   932: ldc2_w 100
/*     */     //   935: lmul
/*     */     //   936: lconst_1
/*     */     //   937: lload #8
/*     */     //   939: invokestatic max : (JJ)J
/*     */     //   942: ldiv
/*     */     //   943: l2i
/*     */     //   944: istore #16
/*     */     //   946: iload #5
/*     */     //   948: ifeq -> 959
/*     */     //   951: iload #16
/*     */     //   953: invokestatic colorForMemory : (I)I
/*     */     //   956: goto -> 960
/*     */     //   959: iconst_m1
/*     */     //   960: istore #17
/*     */     //   962: new java/util/ArrayList
/*     */     //   965: dup
/*     */     //   966: invokespecial <init> : ()V
/*     */     //   969: astore #18
/*     */     //   971: aload #18
/*     */     //   973: ldc_w 'Mem: '
/*     */     //   976: ldc -5589047
/*     */     //   978: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   981: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   986: pop
/*     */     //   987: aload #18
/*     */     //   989: iload #16
/*     */     //   991: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
/*     */     //   996: iload #17
/*     */     //   998: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   1001: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1006: pop
/*     */     //   1007: aload #18
/*     */     //   1009: ldc_w ' (%d MB / %d MB)'
/*     */     //   1012: iconst_2
/*     */     //   1013: anewarray java/lang/Object
/*     */     //   1016: dup
/*     */     //   1017: iconst_0
/*     */     //   1018: lload #14
/*     */     //   1020: ldc2_w 1024
/*     */     //   1023: ldiv
/*     */     //   1024: ldc2_w 1024
/*     */     //   1027: ldiv
/*     */     //   1028: invokestatic valueOf : (J)Ljava/lang/Long;
/*     */     //   1031: aastore
/*     */     //   1032: dup
/*     */     //   1033: iconst_1
/*     */     //   1034: lload #8
/*     */     //   1036: ldc2_w 1024
/*     */     //   1039: ldiv
/*     */     //   1040: ldc2_w 1024
/*     */     //   1043: ldiv
/*     */     //   1044: invokestatic valueOf : (J)Ljava/lang/Long;
/*     */     //   1047: aastore
/*     */     //   1048: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   1051: iconst_m1
/*     */     //   1052: invokestatic seg : (Ljava/lang/String;I)Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   1055: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1060: pop
/*     */     //   1061: aload #7
/*     */     //   1063: aload #18
/*     */     //   1065: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1070: pop
/*     */     //   1071: aload_1
/*     */     //   1072: getfield showTime : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   1075: invokeinterface isValue : ()Z
/*     */     //   1080: ifeq -> 1111
/*     */     //   1083: aload #7
/*     */     //   1085: ldc_w 'Time'
/*     */     //   1088: getstatic vorga/phazeclient/implement/features/modules/other/BetterF3Renderer.TIME_FORMAT : Ljava/text/SimpleDateFormat;
/*     */     //   1091: new java/util/Date
/*     */     //   1094: dup
/*     */     //   1095: invokespecial <init> : ()V
/*     */     //   1098: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
/*     */     //   1101: iconst_m1
/*     */     //   1102: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   1105: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1110: pop
/*     */     //   1111: aload_1
/*     */     //   1112: getfield showSystem : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   1115: invokeinterface isValue : ()Z
/*     */     //   1120: ifeq -> 1187
/*     */     //   1123: aload #7
/*     */     //   1125: ldc_w 'Java'
/*     */     //   1128: ldc_w 'java.version'
/*     */     //   1131: invokestatic getProperty : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   1134: ldc_w 'java.vendor'
/*     */     //   1137: invokestatic getProperty : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   1140: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   1145: iconst_m1
/*     */     //   1146: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   1149: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1154: pop
/*     */     //   1155: aload #7
/*     */     //   1157: ldc_w 'OS'
/*     */     //   1160: ldc_w 'os.name'
/*     */     //   1163: invokestatic getProperty : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   1166: ldc_w 'os.arch'
/*     */     //   1169: invokestatic getProperty : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   1172: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   1177: iconst_m1
/*     */     //   1178: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   1181: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1186: pop
/*     */     //   1187: aload_1
/*     */     //   1188: getfield showTargeted : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   1191: invokeinterface isValue : ()Z
/*     */     //   1196: ifeq -> 1356
/*     */     //   1199: aload_2
/*     */     //   1200: getfield field_1765 : Lnet/minecraft/class_239;
/*     */     //   1203: astore #8
/*     */     //   1205: aload #8
/*     */     //   1207: instanceof net/minecraft/class_3965
/*     */     //   1210: ifeq -> 1298
/*     */     //   1213: aload #8
/*     */     //   1215: checkcast net/minecraft/class_3965
/*     */     //   1218: astore #9
/*     */     //   1220: aload #9
/*     */     //   1222: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
/*     */     //   1225: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
/*     */     //   1228: if_acmpne -> 1298
/*     */     //   1231: aload #9
/*     */     //   1233: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
/*     */     //   1236: astore #11
/*     */     //   1238: aload_2
/*     */     //   1239: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   1242: aload #11
/*     */     //   1244: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
/*     */     //   1247: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
/*     */     //   1250: invokevirtual method_9518 : ()Lnet/minecraft/class_5250;
/*     */     //   1253: invokevirtual getString : ()Ljava/lang/String;
/*     */     //   1256: astore #12
/*     */     //   1258: aload #7
/*     */     //   1260: ldc_w 'Block'
/*     */     //   1263: aload #12
/*     */     //   1265: aload #11
/*     */     //   1267: invokevirtual method_10263 : ()I
/*     */     //   1270: aload #11
/*     */     //   1272: invokevirtual method_10264 : ()I
/*     */     //   1275: aload #11
/*     */     //   1277: invokevirtual method_10260 : ()I
/*     */     //   1280: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;III)Ljava/lang/String;
/*     */     //   1285: iconst_m1
/*     */     //   1286: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   1289: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1294: pop
/*     */     //   1295: goto -> 1356
/*     */     //   1298: aload #8
/*     */     //   1300: instanceof net/minecraft/class_3966
/*     */     //   1303: ifeq -> 1356
/*     */     //   1306: aload #8
/*     */     //   1308: checkcast net/minecraft/class_3966
/*     */     //   1311: astore #10
/*     */     //   1313: aload #10
/*     */     //   1315: invokevirtual method_17782 : ()Lnet/minecraft/class_1297;
/*     */     //   1318: invokevirtual method_5476 : ()Lnet/minecraft/class_2561;
/*     */     //   1321: astore #11
/*     */     //   1323: aload #7
/*     */     //   1325: ldc_w 'Entity'
/*     */     //   1328: aload #11
/*     */     //   1330: ifnonnull -> 1339
/*     */     //   1333: ldc_w '?'
/*     */     //   1336: goto -> 1346
/*     */     //   1339: aload #11
/*     */     //   1341: invokeinterface getString : ()Ljava/lang/String;
/*     */     //   1346: iconst_m1
/*     */     //   1347: invokestatic line : (Ljava/lang/String;Ljava/lang/String;I)Ljava/util/List;
/*     */     //   1350: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   1355: pop
/*     */     //   1356: aload_1
/*     */     //   1357: getfield compactMode : Lvorga/phazeclient/api/feature/module/setting/implement/BooleanSetting;
/*     */     //   1360: invokevirtual isValue : ()Z
/*     */     //   1363: ifeq -> 1371
/*     */     //   1366: bipush #9
/*     */     //   1368: goto -> 1373
/*     */     //   1371: bipush #10
/*     */     //   1373: istore #8
/*     */     //   1375: iconst_4
/*     */     //   1376: istore #9
/*     */     //   1378: iconst_4
/*     */     //   1379: istore #10
/*     */     //   1381: aload_2
/*     */     //   1382: getfield field_1690 : Lnet/minecraft/class_315;
/*     */     //   1385: ldc_w 0.5
/*     */     //   1388: invokevirtual method_19345 : (F)I
/*     */     //   1391: istore #11
/*     */     //   1393: aload #7
/*     */     //   1395: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   1400: astore #12
/*     */     //   1402: aload #12
/*     */     //   1404: invokeinterface hasNext : ()Z
/*     */     //   1409: ifeq -> 1626
/*     */     //   1412: aload #12
/*     */     //   1414: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   1419: checkcast java/util/List
/*     */     //   1422: astore #13
/*     */     //   1424: iconst_0
/*     */     //   1425: istore #14
/*     */     //   1427: aload #13
/*     */     //   1429: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   1434: astore #15
/*     */     //   1436: aload #15
/*     */     //   1438: invokeinterface hasNext : ()Z
/*     */     //   1443: ifeq -> 1478
/*     */     //   1446: aload #15
/*     */     //   1448: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   1453: checkcast vorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment
/*     */     //   1456: astore #16
/*     */     //   1458: iload #14
/*     */     //   1460: aload_2
/*     */     //   1461: getfield field_1772 : Lnet/minecraft/class_327;
/*     */     //   1464: aload #16
/*     */     //   1466: getfield text : Ljava/lang/String;
/*     */     //   1469: invokevirtual method_1727 : (Ljava/lang/String;)I
/*     */     //   1472: iadd
/*     */     //   1473: istore #14
/*     */     //   1475: goto -> 1436
/*     */     //   1478: aload_0
/*     */     //   1479: iload #9
/*     */     //   1481: iconst_1
/*     */     //   1482: isub
/*     */     //   1483: iload #10
/*     */     //   1485: iconst_1
/*     */     //   1486: isub
/*     */     //   1487: iload #9
/*     */     //   1489: iload #14
/*     */     //   1491: iadd
/*     */     //   1492: iconst_1
/*     */     //   1493: iadd
/*     */     //   1494: iload #10
/*     */     //   1496: iload #8
/*     */     //   1498: iadd
/*     */     //   1499: iconst_1
/*     */     //   1500: isub
/*     */     //   1501: iload #11
/*     */     //   1503: invokevirtual method_25294 : (IIIII)V
/*     */     //   1506: iload #9
/*     */     //   1508: istore #15
/*     */     //   1510: aload #13
/*     */     //   1512: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   1517: astore #16
/*     */     //   1519: aload #16
/*     */     //   1521: invokeinterface hasNext : ()Z
/*     */     //   1526: ifeq -> 1616
/*     */     //   1529: aload #16
/*     */     //   1531: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   1536: checkcast vorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment
/*     */     //   1539: astore #17
/*     */     //   1541: iload #6
/*     */     //   1543: ifeq -> 1572
/*     */     //   1546: aload_0
/*     */     //   1547: aload_2
/*     */     //   1548: getfield field_1772 : Lnet/minecraft/class_327;
/*     */     //   1551: aload #17
/*     */     //   1553: getfield text : Ljava/lang/String;
/*     */     //   1556: iload #15
/*     */     //   1558: iload #10
/*     */     //   1560: aload #17
/*     */     //   1562: getfield color : I
/*     */     //   1565: invokevirtual method_25303 : (Lnet/minecraft/class_327;Ljava/lang/String;III)I
/*     */     //   1568: pop
/*     */     //   1569: goto -> 1596
/*     */     //   1572: aload_0
/*     */     //   1573: aload_2
/*     */     //   1574: getfield field_1772 : Lnet/minecraft/class_327;
/*     */     //   1577: aload #17
/*     */     //   1579: getfield text : Ljava/lang/String;
/*     */     //   1582: iload #15
/*     */     //   1584: iload #10
/*     */     //   1586: aload #17
/*     */     //   1588: getfield color : I
/*     */     //   1591: iconst_0
/*     */     //   1592: invokevirtual method_51433 : (Lnet/minecraft/class_327;Ljava/lang/String;IIIZ)I
/*     */     //   1595: pop
/*     */     //   1596: iload #15
/*     */     //   1598: aload_2
/*     */     //   1599: getfield field_1772 : Lnet/minecraft/class_327;
/*     */     //   1602: aload #17
/*     */     //   1604: getfield text : Ljava/lang/String;
/*     */     //   1607: invokevirtual method_1727 : (Ljava/lang/String;)I
/*     */     //   1610: iadd
/*     */     //   1611: istore #15
/*     */     //   1613: goto -> 1519
/*     */     //   1616: iload #10
/*     */     //   1618: iload #8
/*     */     //   1620: iadd
/*     */     //   1621: istore #10
/*     */     //   1623: goto -> 1402
/*     */     //   1626: aload_1
/*     */     //   1627: getfield showFps : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   1630: invokeinterface isValue : ()Z
/*     */     //   1635: ifeq -> 1862
/*     */     //   1638: aload_1
/*     */     //   1639: getfield showFpsBar : Lvorga/phazeclient/implement/features/modules/other/BetterF3$BooleanLike;
/*     */     //   1642: invokeinterface isValue : ()Z
/*     */     //   1647: ifeq -> 1862
/*     */     //   1650: getstatic vorga/phazeclient/implement/features/modules/other/BetterF3Renderer.fpsHistory : Ljava/util/List;
/*     */     //   1653: invokeinterface isEmpty : ()Z
/*     */     //   1658: ifne -> 1862
/*     */     //   1661: bipush #60
/*     */     //   1663: istore #12
/*     */     //   1665: bipush #24
/*     */     //   1667: istore #13
/*     */     //   1669: iload #9
/*     */     //   1671: istore #14
/*     */     //   1673: iload #10
/*     */     //   1675: iconst_2
/*     */     //   1676: iadd
/*     */     //   1677: istore #15
/*     */     //   1679: iconst_1
/*     */     //   1680: istore #16
/*     */     //   1682: getstatic vorga/phazeclient/implement/features/modules/other/BetterF3Renderer.fpsHistory : Ljava/util/List;
/*     */     //   1685: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   1690: astore #17
/*     */     //   1692: aload #17
/*     */     //   1694: invokeinterface hasNext : ()Z
/*     */     //   1699: ifeq -> 1731
/*     */     //   1702: aload #17
/*     */     //   1704: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   1709: checkcast java/lang/Integer
/*     */     //   1712: invokevirtual intValue : ()I
/*     */     //   1715: istore #18
/*     */     //   1717: iload #18
/*     */     //   1719: iload #16
/*     */     //   1721: if_icmple -> 1728
/*     */     //   1724: iload #18
/*     */     //   1726: istore #16
/*     */     //   1728: goto -> 1692
/*     */     //   1731: aload_0
/*     */     //   1732: iload #14
/*     */     //   1734: iconst_1
/*     */     //   1735: isub
/*     */     //   1736: iload #15
/*     */     //   1738: iconst_1
/*     */     //   1739: isub
/*     */     //   1740: iload #14
/*     */     //   1742: iload #12
/*     */     //   1744: iadd
/*     */     //   1745: iconst_1
/*     */     //   1746: iadd
/*     */     //   1747: iload #15
/*     */     //   1749: iload #13
/*     */     //   1751: iadd
/*     */     //   1752: iconst_1
/*     */     //   1753: iadd
/*     */     //   1754: iload #11
/*     */     //   1756: invokevirtual method_25294 : (IIIII)V
/*     */     //   1759: iconst_0
/*     */     //   1760: istore #17
/*     */     //   1762: iload #17
/*     */     //   1764: getstatic vorga/phazeclient/implement/features/modules/other/BetterF3Renderer.fpsHistory : Ljava/util/List;
/*     */     //   1767: invokeinterface size : ()I
/*     */     //   1772: if_icmpge -> 1862
/*     */     //   1775: getstatic vorga/phazeclient/implement/features/modules/other/BetterF3Renderer.fpsHistory : Ljava/util/List;
/*     */     //   1778: iload #17
/*     */     //   1780: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1785: checkcast java/lang/Integer
/*     */     //   1788: invokevirtual intValue : ()I
/*     */     //   1791: istore #18
/*     */     //   1793: iconst_1
/*     */     //   1794: iload #18
/*     */     //   1796: iload #13
/*     */     //   1798: imul
/*     */     //   1799: iload #16
/*     */     //   1801: idiv
/*     */     //   1802: invokestatic max : (II)I
/*     */     //   1805: istore #19
/*     */     //   1807: iload #5
/*     */     //   1809: ifeq -> 1820
/*     */     //   1812: iload #18
/*     */     //   1814: invokestatic colorForFps : (I)I
/*     */     //   1817: goto -> 1823
/*     */     //   1820: ldc_w -5592406
/*     */     //   1823: istore #20
/*     */     //   1825: aload_0
/*     */     //   1826: iload #14
/*     */     //   1828: iload #17
/*     */     //   1830: iadd
/*     */     //   1831: iload #15
/*     */     //   1833: iload #13
/*     */     //   1835: iadd
/*     */     //   1836: iload #19
/*     */     //   1838: isub
/*     */     //   1839: iload #14
/*     */     //   1841: iload #17
/*     */     //   1843: iadd
/*     */     //   1844: iconst_1
/*     */     //   1845: iadd
/*     */     //   1846: iload #15
/*     */     //   1848: iload #13
/*     */     //   1850: iadd
/*     */     //   1851: iload #20
/*     */     //   1853: invokevirtual method_25294 : (IIIII)V
/*     */     //   1856: iinc #17, 1
/*     */     //   1859: goto -> 1762
/*     */     //   1862: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #62	-> 0
/*     */     //   #63	-> 4
/*     */     //   #64	-> 16
/*     */     //   #65	-> 20
/*     */     //   #66	-> 39
/*     */     //   #71	-> 47
/*     */     //   #73	-> 54
/*     */     //   #74	-> 59
/*     */     //   #75	-> 65
/*     */     //   #76	-> 74
/*     */     //   #78	-> 83
/*     */     //   #80	-> 92
/*     */     //   #81	-> 104
/*     */     //   #82	-> 110
/*     */     //   #83	-> 119
/*     */     //   #82	-> 133
/*     */     //   #85	-> 142
/*     */     //   #86	-> 154
/*     */     //   #87	-> 163
/*     */     //   #88	-> 179
/*     */     //   #94	-> 198
/*     */     //   #95	-> 207
/*     */     //   #96	-> 222
/*     */     //   #97	-> 243
/*     */     //   #96	-> 254
/*     */     //   #98	-> 263
/*     */     //   #99	-> 278
/*     */     //   #100	-> 299
/*     */     //   #99	-> 310
/*     */     //   #101	-> 319
/*     */     //   #102	-> 334
/*     */     //   #103	-> 355
/*     */     //   #102	-> 366
/*     */     //   #104	-> 375
/*     */     //   #108	-> 385
/*     */     //   #109	-> 391
/*     */     //   #110	-> 415
/*     */     //   #108	-> 433
/*     */     //   #114	-> 442
/*     */     //   #115	-> 454
/*     */     //   #116	-> 459
/*     */     //   #118	-> 471
/*     */     //   #117	-> 491
/*     */     //   #115	-> 500
/*     */     //   #121	-> 509
/*     */     //   #122	-> 521
/*     */     //   #123	-> 540
/*     */     //   #124	-> 546
/*     */     //   #125	-> 554
/*     */     //   #127	-> 571
/*     */     //   #128	-> 583
/*     */     //   #129	-> 597
/*     */     //   #130	-> 611
/*     */     //   #132	-> 635
/*     */     //   #133	-> 647
/*     */     //   #134	-> 656
/*     */     //   #133	-> 666
/*     */     //   #137	-> 675
/*     */     //   #138	-> 687
/*     */     //   #139	-> 692
/*     */     //   #140	-> 706
/*     */     //   #142	-> 715
/*     */     //   #143	-> 718
/*     */     //   #144	-> 732
/*     */     //   #145	-> 748
/*     */     //   #149	-> 760
/*     */     //   #150	-> 781
/*     */     //   #151	-> 790
/*     */     //   #152	-> 806
/*     */     //   #153	-> 820
/*     */     //   #154	-> 825
/*     */     //   #155	-> 841
/*     */     //   #156	-> 861
/*     */     //   #158	-> 877
/*     */     //   #160	-> 887
/*     */     //   #161	-> 899
/*     */     //   #162	-> 907
/*     */     //   #163	-> 915
/*     */     //   #164	-> 923
/*     */     //   #165	-> 930
/*     */     //   #166	-> 946
/*     */     //   #167	-> 962
/*     */     //   #168	-> 971
/*     */     //   #169	-> 987
/*     */     //   #170	-> 1007
/*     */     //   #171	-> 1028
/*     */     //   #170	-> 1048
/*     */     //   #172	-> 1061
/*     */     //   #174	-> 1071
/*     */     //   #175	-> 1083
/*     */     //   #177	-> 1111
/*     */     //   #178	-> 1123
/*     */     //   #179	-> 1137
/*     */     //   #178	-> 1146
/*     */     //   #180	-> 1155
/*     */     //   #181	-> 1169
/*     */     //   #180	-> 1178
/*     */     //   #183	-> 1187
/*     */     //   #184	-> 1199
/*     */     //   #185	-> 1205
/*     */     //   #186	-> 1231
/*     */     //   #187	-> 1238
/*     */     //   #188	-> 1258
/*     */     //   #189	-> 1267
/*     */     //   #188	-> 1286
/*     */     //   #191	-> 1295
/*     */     //   #192	-> 1313
/*     */     //   #193	-> 1323
/*     */     //   #200	-> 1356
/*     */     //   #201	-> 1375
/*     */     //   #202	-> 1378
/*     */     //   #203	-> 1381
/*     */     //   #205	-> 1393
/*     */     //   #206	-> 1424
/*     */     //   #207	-> 1427
/*     */     //   #211	-> 1478
/*     */     //   #212	-> 1506
/*     */     //   #213	-> 1510
/*     */     //   #214	-> 1541
/*     */     //   #215	-> 1546
/*     */     //   #217	-> 1572
/*     */     //   #219	-> 1596
/*     */     //   #220	-> 1613
/*     */     //   #221	-> 1616
/*     */     //   #222	-> 1623
/*     */     //   #228	-> 1626
/*     */     //   #229	-> 1661
/*     */     //   #230	-> 1665
/*     */     //   #231	-> 1669
/*     */     //   #232	-> 1673
/*     */     //   #233	-> 1679
/*     */     //   #234	-> 1682
/*     */     //   #235	-> 1731
/*     */     //   #236	-> 1759
/*     */     //   #237	-> 1775
/*     */     //   #238	-> 1793
/*     */     //   #239	-> 1807
/*     */     //   #240	-> 1825
/*     */     //   #236	-> 1856
/*     */     //   #243	-> 1862
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   110	32	8	fps	I
/*     */     //   207	235	8	row	Ljava/util/List;
/*     */     //   554	17	8	biome	Ljava/lang/String;
/*     */     //   597	38	8	block	I
/*     */     //   611	24	9	sky	I
/*     */     //   748	12	10	entry	Lnet/minecraft/class_640;
/*     */     //   692	195	8	brand	Ljava/lang/String;
/*     */     //   718	169	9	ping	I
/*     */     //   781	106	10	pingColor	I
/*     */     //   790	97	11	row	Ljava/util/List;
/*     */     //   907	164	8	maxMem	J
/*     */     //   915	156	10	totalMem	J
/*     */     //   923	148	12	freeMem	J
/*     */     //   930	141	14	usedMem	J
/*     */     //   946	125	16	pct	I
/*     */     //   962	109	17	memColor	I
/*     */     //   971	100	18	row	Ljava/util/List;
/*     */     //   1238	57	11	bp	Lnet/minecraft/class_2338;
/*     */     //   1258	37	12	id	Ljava/lang/String;
/*     */     //   1220	78	9	bhr	Lnet/minecraft/class_3965;
/*     */     //   1323	33	11	n	Lnet/minecraft/class_2561;
/*     */     //   1313	43	10	ehr	Lnet/minecraft/class_3966;
/*     */     //   1205	151	8	target	Lnet/minecraft/class_239;
/*     */     //   1458	17	16	s	Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   1541	72	17	s	Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     //   1427	196	14	rowWidth	I
/*     */     //   1510	113	15	sx	I
/*     */     //   1424	199	13	row	Ljava/util/List;
/*     */     //   1717	11	18	s	I
/*     */     //   1793	63	18	sample	I
/*     */     //   1807	49	19	h	I
/*     */     //   1825	31	20	color	I
/*     */     //   1762	100	17	i	I
/*     */     //   1665	197	12	barW	I
/*     */     //   1669	193	13	barH	I
/*     */     //   1673	189	14	barX	I
/*     */     //   1679	183	15	barY	I
/*     */     //   1682	180	16	max	I
/*     */     //   0	1863	0	context	Lnet/minecraft/class_332;
/*     */     //   4	1859	1	module	Lvorga/phazeclient/implement/features/modules/other/BetterF3;
/*     */     //   20	1843	2	mc	Lnet/minecraft/class_310;
/*     */     //   59	1804	3	player	Lnet/minecraft/class_746;
/*     */     //   65	1798	4	pos	Lnet/minecraft/class_2338;
/*     */     //   74	1789	5	colorize	Z
/*     */     //   83	1780	6	shadow	Z
/*     */     //   92	1771	7	rows	Ljava/util/List;
/*     */     //   1375	488	8	lineHeight	I
/*     */     //   1378	485	9	x	I
/*     */     //   1381	482	10	y	I
/*     */     //   1393	470	11	bgColor	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   207	235	8	row	Ljava/util/List<Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;>;
/*     */     //   790	97	11	row	Ljava/util/List<Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;>;
/*     */     //   971	100	18	row	Ljava/util/List<Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;>;
/*     */     //   1424	199	13	row	Ljava/util/List<Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;>;
/*     */     //   92	1771	7	rows	Ljava/util/List<Ljava/util/List<Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;>;>;
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
/*     */   private static List<Segment> line(String name, String value, int valueColor) {
/* 249 */     List<Segment> row = new ArrayList<>(2);
/* 250 */     row.add(seg(name + ": ", -5589047));
/* 251 */     row.add(seg(value, valueColor));
/* 252 */     return row;
/*     */   }
/*     */   
/*     */   private static Segment seg(String text, int color) {
/* 256 */     return new Segment(text, color);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sampleFps(int fps) {
/* 261 */     fpsHistory.add(Integer.valueOf(fps));
/* 262 */     while (fpsHistory.size() > 60) {
/* 263 */       fpsHistory.remove(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String facingFor(class_1297 entity) {
/* 269 */     float yaw = normalizedYaw(entity.method_36454());
/* 270 */     if (yaw >= 315.0F || yaw < 45.0F) return "South (+Z)"; 
/* 271 */     if (yaw < 135.0F) return "West (-X)"; 
/* 272 */     if (yaw < 225.0F) return "North (-Z)"; 
/* 273 */     return "East (+X)";
/*     */   }
/*     */ 
/*     */   
/*     */   private static float normalizedYaw(float yaw) {
/* 278 */     return class_3532.method_15393(yaw);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int colorForFps(int fps) {
/* 283 */     if (fps < 30) return -43691; 
/* 284 */     if (fps < 60) return -171; 
/* 285 */     if (fps < 120) return -11141291; 
/* 286 */     return -11141121;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int colorForMemory(int pct) {
/* 291 */     if (pct >= 80) return -43691; 
/* 292 */     if (pct >= 50) return -171; 
/* 293 */     return -11141291;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int colorForPing(int ping) {
/* 298 */     if (ping > 200) return -43691; 
/* 299 */     if (ping > 80) return -171; 
/* 300 */     return -11141291;
/*     */   }
/*     */   private static final class Segment extends Record { private final String text; private final int color;
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #310	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #310	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/*     */     }
/*     */     
/* 310 */     private Segment(String text, int color) { this.text = text; this.color = color; } public String text() { return this.text; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #310	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lvorga/phazeclient/implement/features/modules/other/BetterF3Renderer$Segment;
/* 310 */       //   0	8	1	o	Ljava/lang/Object; } public int color() { return this.color; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\implement\features\modules\other\BetterF3Renderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */