/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COMLateBindingObject
/*     */   extends COMBindingBaseObject
/*     */ {
/*     */   public COMLateBindingObject(IDispatch iDispatch) {
/*  47 */     super(iDispatch);
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
/*     */   public COMLateBindingObject(Guid.CLSID clsid, boolean useActiveInstance) {
/*  59 */     super(clsid, useActiveInstance);
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
/*     */   public COMLateBindingObject(String progId, boolean useActiveInstance) throws COMException {
/*  74 */     super(progId, useActiveInstance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IDispatch getAutomationProperty(String propertyName) {
/*  85 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/*  86 */     oleMethod(2, result, propertyName);
/*     */     
/*  88 */     return (IDispatch)result.getValue();
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
/*     */   protected IDispatch getAutomationProperty(String propertyName, Variant.VARIANT value) {
/* 101 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 102 */     oleMethod(2, result, propertyName, value);
/*     */     
/* 104 */     return (IDispatch)result.getValue();
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
/*     */   @Deprecated
/*     */   protected IDispatch getAutomationProperty(String propertyName, COMLateBindingObject comObject) {
/* 120 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 121 */     oleMethod(2, result, propertyName);
/*     */     
/* 123 */     return (IDispatch)result.getValue();
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
/*     */   @Deprecated
/*     */   protected IDispatch getAutomationProperty(String propertyName, COMLateBindingObject comObject, Variant.VARIANT value) {
/* 141 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 142 */     oleMethod(2, result, propertyName, value);
/*     */     
/* 144 */     return (IDispatch)result.getValue();
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
/*     */   @Deprecated
/*     */   protected IDispatch getAutomationProperty(String propertyName, IDispatch iDispatch) {
/* 160 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 161 */     oleMethod(2, result, propertyName);
/*     */     
/* 163 */     return (IDispatch)result.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean getBooleanProperty(String propertyName) {
/* 174 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 175 */     oleMethod(2, result, propertyName);
/*     */     
/* 177 */     return result.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Date getDateProperty(String propertyName) {
/* 188 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 189 */     oleMethod(2, result, propertyName);
/*     */     
/* 191 */     return result.dateValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIntProperty(String propertyName) {
/* 202 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 203 */     oleMethod(2, result, propertyName);
/*     */     
/* 205 */     return result.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getShortProperty(String propertyName) {
/* 216 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 217 */     oleMethod(2, result, propertyName);
/*     */     
/* 219 */     return result.shortValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStringProperty(String propertyName) {
/* 230 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 231 */     oleMethod(2, result, propertyName);
/*     */     
/* 233 */     String res = result.stringValue();
/*     */     
/* 235 */     OleAuto.INSTANCE.VariantClear((Variant.VARIANT)result);
/*     */     
/* 237 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Variant.VARIANT invoke(String methodName) {
/* 248 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 249 */     oleMethod(1, result, methodName);
/*     */     
/* 251 */     return (Variant.VARIANT)result;
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg) {
/* 264 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 265 */     oleMethod(1, result, methodName, arg);
/*     */     
/* 267 */     return (Variant.VARIANT)result;
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT[] args) {
/* 280 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 281 */     oleMethod(1, result, methodName, args);
/*     */     
/* 283 */     return (Variant.VARIANT)result;
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 298 */     return invoke(methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3) {
/* 316 */     return invoke(methodName, new Variant.VARIANT[] { arg1, arg2, arg3 });
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
/*     */   protected Variant.VARIANT invoke(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3, Variant.VARIANT arg4) {
/* 336 */     return invoke(methodName, new Variant.VARIANT[] { arg1, arg2, arg3, arg4 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch) {
/* 346 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, COMLateBindingObject comObject) {
/* 356 */     oleMethod(1, (Variant.VARIANT.ByReference)null, comObject.getIDispatch(), methodName);
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg) {
/* 369 */     oleMethod(1, (Variant.VARIANT.ByReference)null, methodName, arg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch, Variant.VARIANT arg) {
/* 381 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName, arg);
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
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 394 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, COMLateBindingObject comObject, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 408 */     oleMethod(1, (Variant.VARIANT.ByReference)null, comObject.getIDispatch(), methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, COMLateBindingObject comObject, Variant.VARIANT arg) {
/* 421 */     oleMethod(1, (Variant.VARIANT.ByReference)null, comObject.getIDispatch(), methodName, arg);
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
/*     */   @Deprecated
/*     */   protected void invokeNoReply(String methodName, IDispatch dispatch, Variant.VARIANT[] args) {
/* 435 */     oleMethod(1, (Variant.VARIANT.ByReference)null, dispatch, methodName, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invokeNoReply(String methodName) {
/* 446 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 447 */     oleMethod(1, result, methodName);
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT[] args) {
/* 459 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 460 */     oleMethod(1, result, methodName, args);
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2) {
/* 474 */     invokeNoReply(methodName, new Variant.VARIANT[] { arg1, arg2 });
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3) {
/* 491 */     invokeNoReply(methodName, new Variant.VARIANT[] { arg1, arg2, arg3 });
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
/*     */   protected void invokeNoReply(String methodName, Variant.VARIANT arg1, Variant.VARIANT arg2, Variant.VARIANT arg3, Variant.VARIANT arg4) {
/* 510 */     invokeNoReply(methodName, new Variant.VARIANT[] { arg1, arg2, arg3, arg4 });
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
/*     */   protected void setProperty(String propertyName, boolean value) {
/* 522 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, Date value) {
/* 534 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, Dispatch value) {
/* 546 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, new Variant.VARIANT(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setProperty(String propertyName, IDispatch value) {
/* 556 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, int value) {
/* 568 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, short value) {
/* 580 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, new Variant.VARIANT(value));
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
/*     */   protected void setProperty(String propertyName, String value) {
/* 592 */     Variant.VARIANT wrappedValue = new Variant.VARIANT(value);
/*     */     try {
/* 594 */       oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, wrappedValue);
/*     */     } finally {
/* 596 */       OleAuto.INSTANCE.VariantClear(wrappedValue);
/*     */     } 
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
/*     */   protected void setProperty(String propertyName, Variant.VARIANT value) {
/* 609 */     oleMethod(4, (Variant.VARIANT.ByReference)null, propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setProperty(String propertyName, IDispatch iDispatch, Variant.VARIANT value) {
/* 621 */     oleMethod(4, (Variant.VARIANT.ByReference)null, iDispatch, propertyName, value);
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
/*     */   @Deprecated
/*     */   protected void setProperty(String propertyName, COMLateBindingObject comObject, Variant.VARIANT value) {
/* 634 */     oleMethod(4, (Variant.VARIANT.ByReference)null, comObject
/* 635 */         .getIDispatch(), propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Variant.VARIANT toVariant() {
/* 645 */     return new Variant.VARIANT(getIDispatch());
/*     */   }
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\COM\COMLateBindingObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */