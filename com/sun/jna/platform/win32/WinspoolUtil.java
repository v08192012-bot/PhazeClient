/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WinspoolUtil
/*     */ {
/*     */   public static Winspool.PRINTER_INFO_1[] getPrinterInfo1() {
/*  44 */     IntByReference pcbNeeded = new IntByReference();
/*  45 */     IntByReference pcReturned = new IntByReference();
/*  46 */     Winspool.INSTANCE.EnumPrinters(2, null, 1, null, 0, pcbNeeded, pcReturned);
/*     */     
/*  48 */     if (pcbNeeded.getValue() <= 0) {
/*  49 */       return new Winspool.PRINTER_INFO_1[0];
/*     */     }
/*     */     
/*  52 */     Winspool.PRINTER_INFO_1 pPrinterEnum = new Winspool.PRINTER_INFO_1(pcbNeeded.getValue());
/*  53 */     if (!Winspool.INSTANCE.EnumPrinters(2, null, 1, pPrinterEnum
/*  54 */         .getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/*  56 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/*  59 */     pPrinterEnum.read();
/*     */     
/*  61 */     return (Winspool.PRINTER_INFO_1[])pPrinterEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */   
/*     */   public static Winspool.PRINTER_INFO_2[] getPrinterInfo2() {
/*  65 */     return getPrinterInfo2(2);
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
/*     */   public static Winspool.PRINTER_INFO_2[] getAllPrinterInfo2() {
/*  78 */     return getPrinterInfo2(6);
/*     */   }
/*     */   
/*     */   private static Winspool.PRINTER_INFO_2[] getPrinterInfo2(int flags) {
/*  82 */     IntByReference pcbNeeded = new IntByReference();
/*  83 */     IntByReference pcReturned = new IntByReference();
/*  84 */     Winspool.INSTANCE.EnumPrinters(flags, null, 2, null, 0, pcbNeeded, pcReturned);
/*  85 */     if (pcbNeeded.getValue() <= 0) {
/*  86 */       return new Winspool.PRINTER_INFO_2[0];
/*     */     }
/*     */     
/*  89 */     Winspool.PRINTER_INFO_2 pPrinterEnum = new Winspool.PRINTER_INFO_2(pcbNeeded.getValue());
/*  90 */     if (!Winspool.INSTANCE.EnumPrinters(flags, null, 2, pPrinterEnum.getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/*  92 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/*  95 */     pPrinterEnum.read();
/*  96 */     return (Winspool.PRINTER_INFO_2[])pPrinterEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */   
/*     */   public static Winspool.PRINTER_INFO_2 getPrinterInfo2(String printerName) {
/* 100 */     IntByReference pcbNeeded = new IntByReference();
/* 101 */     IntByReference pcReturned = new IntByReference();
/* 102 */     WinNT.HANDLEByReference pHandle = new WinNT.HANDLEByReference();
/*     */     
/* 104 */     if (!Winspool.INSTANCE.OpenPrinter(printerName, pHandle, null)) {
/* 105 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 108 */     Win32Exception we = null;
/* 109 */     Winspool.PRINTER_INFO_2 pinfo2 = null;
/*     */     
/*     */     try {
/* 112 */       Winspool.INSTANCE.GetPrinter(pHandle.getValue(), 2, null, 0, pcbNeeded);
/* 113 */       if (pcbNeeded.getValue() <= 0) {
/* 114 */         return new Winspool.PRINTER_INFO_2();
/*     */       }
/*     */       
/* 117 */       pinfo2 = new Winspool.PRINTER_INFO_2(pcbNeeded.getValue());
/* 118 */       if (!Winspool.INSTANCE.GetPrinter(pHandle.getValue(), 2, pinfo2.getPointer(), pcbNeeded.getValue(), pcReturned)) {
/* 119 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       }
/*     */       
/* 122 */       pinfo2.read();
/* 123 */     } catch (Win32Exception e) {
/* 124 */       we = e;
/*     */     } finally {
/* 126 */       if (!Winspool.INSTANCE.ClosePrinter(pHandle.getValue())) {
/* 127 */         Win32Exception ex = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/* 128 */         if (we != null) {
/* 129 */           ex.addSuppressedReflected((Throwable)we);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     if (we != null) {
/* 135 */       throw we;
/*     */     }
/*     */     
/* 138 */     return pinfo2;
/*     */   }
/*     */   
/*     */   public static Winspool.PRINTER_INFO_4[] getPrinterInfo4() {
/* 142 */     IntByReference pcbNeeded = new IntByReference();
/* 143 */     IntByReference pcReturned = new IntByReference();
/* 144 */     Winspool.INSTANCE.EnumPrinters(2, null, 4, null, 0, pcbNeeded, pcReturned);
/*     */     
/* 146 */     if (pcbNeeded.getValue() <= 0) {
/* 147 */       return new Winspool.PRINTER_INFO_4[0];
/*     */     }
/*     */     
/* 150 */     Winspool.PRINTER_INFO_4 pPrinterEnum = new Winspool.PRINTER_INFO_4(pcbNeeded.getValue());
/* 151 */     if (!Winspool.INSTANCE.EnumPrinters(2, null, 4, pPrinterEnum
/* 152 */         .getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/* 154 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 157 */     pPrinterEnum.read();
/*     */     
/* 159 */     return (Winspool.PRINTER_INFO_4[])pPrinterEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */   
/*     */   public static Winspool.JOB_INFO_1[] getJobInfo1(WinNT.HANDLEByReference phPrinter) {
/* 163 */     IntByReference pcbNeeded = new IntByReference();
/* 164 */     IntByReference pcReturned = new IntByReference();
/* 165 */     Winspool.INSTANCE.EnumJobs(phPrinter.getValue(), 0, 255, 1, null, 0, pcbNeeded, pcReturned);
/*     */     
/* 167 */     if (pcbNeeded.getValue() <= 0) {
/* 168 */       return new Winspool.JOB_INFO_1[0];
/*     */     }
/*     */     
/* 171 */     int lastError = 0;
/*     */     
/*     */     while (true) {
/* 174 */       Winspool.JOB_INFO_1 pJobEnum = new Winspool.JOB_INFO_1(pcbNeeded.getValue());
/* 175 */       if (!Winspool.INSTANCE.EnumJobs(phPrinter.getValue(), 0, 255, 1, pJobEnum
/* 176 */           .getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */       {
/* 178 */         lastError = Kernel32.INSTANCE.GetLastError();
/*     */       }
/* 180 */       if (lastError != 122) {
/* 181 */         if (lastError != 0) {
/* 182 */           throw new Win32Exception(lastError);
/*     */         }
/* 184 */         if (pcReturned.getValue() <= 0) {
/* 185 */           return new Winspool.JOB_INFO_1[0];
/*     */         }
/*     */         
/* 188 */         pJobEnum.read();
/*     */         
/* 190 */         return (Winspool.JOB_INFO_1[])pJobEnum.toArray(pcReturned.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\WinspoolUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */