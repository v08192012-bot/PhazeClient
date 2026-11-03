/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.FromNativeContext;
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.NativeMapped;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.StringArray;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Structure.FieldOrder;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.win32.StdCallLibrary;
/*      */ import com.sun.jna.win32.W32APITypeMapper;
/*      */ 
/*      */ public interface WinCrypt
/*      */ {
/*      */   public static final int CRYPTPROTECT_PROMPT_ON_UNPROTECT = 1;
/*      */   public static final int CRYPTPROTECT_PROMPT_ON_PROTECT = 2;
/*      */   public static final int CRYPTPROTECT_PROMPT_RESERVED = 4;
/*      */   public static final int CRYPTPROTECT_PROMPT_STRONG = 8;
/*      */   public static final int CRYPTPROTECT_PROMPT_REQUIRE_STRONG = 16;
/*      */   public static final int CRYPTPROTECT_UI_FORBIDDEN = 1;
/*      */   public static final int CRYPTPROTECT_LOCAL_MACHINE = 4;
/*      */   public static final int CRYPTPROTECT_CRED_SYNC = 8;
/*      */   public static final int CRYPTPROTECT_AUDIT = 16;
/*      */   public static final int CRYPTPROTECT_NO_RECOVERY = 32;
/*      */   public static final int CRYPTPROTECT_VERIFY_PROTECTION = 64;
/*      */   public static final int CRYPTPROTECT_CRED_REGENERATE = 128;
/*      */   public static final int CRYPT_E_ASN1_ERROR = -2146881280;
/*      */   public static final int CRYPT_E_ASN1_INTERNAL = -2146881279;
/*      */   public static final int CRYPT_E_ASN1_EOD = -2146881278;
/*      */   public static final int CRYPT_E_ASN1_CORRUPT = -2146881277;
/*      */   public static final int CRYPT_E_ASN1_LARGE = -2146881276;
/*      */   public static final int CRYPT_E_ASN1_CONSTRAINT = -2146881275;
/*      */   public static final int CRYPT_E_ASN1_MEMORY = -2146881274;
/*      */   public static final int CRYPT_E_ASN1_OVERFLOW = -2146881273;
/*      */   public static final int CRYPT_E_ASN1_BADPDU = -2146881272;
/*      */   public static final int CRYPT_E_ASN1_BADARGS = -2146881271;
/*      */   public static final int CRYPT_E_ASN1_BADREAL = -2146881270;
/*      */   public static final int CRYPT_E_ASN1_BADTAG = -2146881269;
/*      */   public static final int CRYPT_E_ASN1_CHOICE = -2146881268;
/*      */   public static final int CRYPT_E_ASN1_RULE = -2146881267;
/*      */   public static final int CRYPT_E_ASN1_UTF8 = -2146881266;
/*      */   public static final int CRYPT_E_ASN1_PDU_TYPE = -2146881229;
/*      */   public static final int CRYPT_E_ASN1_NYI = -2146881228;
/*      */   public static final int CRYPT_E_ASN1_EXTENDED = -2146881023;
/*      */   public static final int CRYPT_E_ASN1_NOEOD = -2146881022;
/*      */   public static final int CRYPT_ASN_ENCODING = 1;
/*      */   public static final int CRYPT_NDR_ENCODING = 2;
/*      */   public static final int X509_ASN_ENCODING = 1;
/*      */   public static final int X509_NDR_ENCODING = 2;
/*      */   public static final int PKCS_7_ASN_ENCODING = 65536;
/*      */   public static final int PKCS_7_NDR_ENCODING = 131072;
/*      */   public static final int USAGE_MATCH_TYPE_AND = 0;
/*      */   public static final int USAGE_MATCH_TYPE_OR = 1;
/*      */   public static final int PP_CLIENT_HWND = 1;
/*      */   public static final int CERT_SIMPLE_NAME_STR = 1;
/*      */   public static final int CERT_OID_NAME_STR = 2;
/*      */   public static final int CERT_X500_NAME_STR = 3;
/*      */   public static final int CERT_XML_NAME_STR = 4;
/*      */   public static final int CERT_CHAIN_POLICY_BASE = 1;
/*      */   public static final String szOID_RSA_SHA1RSA = "1.2.840.113549.1.1.5";
/*      */   
/*      */   @FieldOrder({"cbData", "pbData"})
/*      */   public static class DATA_BLOB
/*      */     extends Structure
/*      */   {
/*      */     public int cbData;
/*      */     public Pointer pbData;
/*      */     
/*      */     public DATA_BLOB(Pointer memory) {
/*   72 */       super(memory);
/*   73 */       read();
/*      */     }
/*      */     public static class ByReference extends DATA_BLOB implements Structure.ByReference {}
/*      */     public DATA_BLOB() {}
/*      */     public DATA_BLOB(byte[] data) {
/*   78 */       if (data.length > 0) {
/*   79 */         this.pbData = (Pointer)new Memory(data.length);
/*   80 */         this.pbData.write(0L, data, 0, data.length);
/*   81 */         this.cbData = data.length;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*   87 */         this.pbData = (Pointer)new Memory(1L);
/*   88 */         this.cbData = 0;
/*      */       } 
/*      */     }
/*      */     
/*      */     public DATA_BLOB(String s) {
/*   93 */       this(Native.toByteArray(s));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] getData() {
/*  102 */       return (this.pbData == null) ? null : this.pbData.getByteArray(0L, this.cbData);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwErrorStatus", "dwInfoStatus"})
/*      */   public static class CERT_TRUST_STATUS
/*      */     extends Structure
/*      */   {
/*      */     public int dwErrorStatus;
/*      */     
/*      */     public int dwInfoStatus;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_TRUST_STATUS
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"SubjectIdentifier", "cAttribute", "rgAttribute"})
/*      */   public static class CTL_ENTRY
/*      */     extends Structure
/*      */   {
/*      */     public WinCrypt.DATA_BLOB SubjectIdentifier;
/*      */     
/*      */     public int cAttribute;
/*      */     
/*      */     public Pointer rgAttribute;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CTL_ENTRY
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public WinCrypt.CRYPT_ATTRIBUTE[] getRgAttribute() {
/*  140 */       if (this.cAttribute == 0) {
/*  141 */         return new WinCrypt.CRYPT_ATTRIBUTE[0];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  146 */       WinCrypt.CRYPT_ATTRIBUTE[] result = (WinCrypt.CRYPT_ATTRIBUTE[])((WinCrypt.CRYPT_ATTRIBUTE)Structure.newInstance(WinCrypt.CRYPT_ATTRIBUTE.class, this.rgAttribute)).toArray(this.cAttribute);
/*  147 */       result[0].read();
/*  148 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "pBaseCRLContext", "pDeltaCRLContext", "pCrlEntry", "fDeltaCrlEntry"})
/*      */   public static class CERT_REVOCATION_CRL_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public WinCrypt.CRL_CONTEXT.ByReference pBaseCRLContext;
/*      */     
/*      */     public WinCrypt.CRL_CONTEXT.ByReference pDeltaCRLContext;
/*      */     
/*      */     public WinCrypt.CRL_ENTRY.ByReference pCrlEntry;
/*      */     
/*      */     public boolean fDeltaCrlEntry;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_REVOCATION_CRL_INFO
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public CERT_REVOCATION_CRL_INFO() {
/*  174 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwRevocationResult", "pszRevocationOid", "pvOidSpecificInfo", "fHasFreshnessTime", "dwFreshnessTime", "pCrlInfo"})
/*      */   public static class CERT_REVOCATION_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public int dwRevocationResult;
/*      */     
/*      */     public String pszRevocationOid;
/*      */     
/*      */     public Pointer pvOidSpecificInfo;
/*      */     
/*      */     public boolean fHasFreshnessTime;
/*      */     public int dwFreshnessTime;
/*      */     public WinCrypt.CERT_REVOCATION_CRL_INFO.ByReference pCrlInfo;
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_REVOCATION_INFO
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CERT_REVOCATION_INFO() {
/*  200 */       super(W32APITypeMapper.ASCII);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "pCertContext", "TrustStatus", "pRevocationInfo", "pIssuanceUsage", "pApplicationUsage", "pwszExtendedErrorInfo"})
/*      */   public static class CERT_CHAIN_ELEMENT
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public WinCrypt.CERT_CONTEXT.ByReference pCertContext;
/*      */     
/*      */     public WinCrypt.CERT_TRUST_STATUS TrustStatus;
/*      */     
/*      */     public WinCrypt.CERT_REVOCATION_INFO.ByReference pRevocationInfo;
/*      */     
/*      */     public WinCrypt.CTL_USAGE.ByReference pIssuanceUsage;
/*      */     
/*      */     public WinCrypt.CTL_USAGE.ByReference pApplicationUsage;
/*      */     
/*      */     public String pwszExtendedErrorInfo;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_CHAIN_ELEMENT
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public CERT_CHAIN_ELEMENT() {
/*  230 */       super(W32APITypeMapper.UNICODE);
/*      */     }
/*      */     
/*      */     public CERT_CHAIN_ELEMENT(Pointer p) {
/*  234 */       super(p, 0, W32APITypeMapper.UNICODE);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwVersion", "SubjectUsage", "ListIdentifier", "SequenceNumber", "ThisUpdate", "NextUpdate", "SubjectAlgorithm", "cCTLEntry", "rgCTLEntry", "cExtension", "rgExtension"})
/*      */   public static class CTL_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int dwVersion;
/*      */     
/*      */     public WinCrypt.CTL_USAGE SubjectUsage;
/*      */     
/*      */     public WinCrypt.DATA_BLOB ListIdentifier;
/*      */     
/*      */     public WinCrypt.DATA_BLOB SequenceNumber;
/*      */     
/*      */     public WinBase.FILETIME ThisUpdate;
/*      */     
/*      */     public WinBase.FILETIME NextUpdate;
/*      */     public WinCrypt.CRYPT_ALGORITHM_IDENTIFIER SubjectAlgorithm;
/*      */     public int cCTLEntry;
/*      */     public Pointer rgCTLEntry;
/*      */     public int cExtension;
/*      */     public Pointer rgExtension;
/*      */     
/*      */     public static class ByReference
/*      */       extends CTL_INFO
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public WinCrypt.CTL_ENTRY[] getRgCTLEntry() {
/*  265 */       if (this.cCTLEntry == 0) {
/*  266 */         return new WinCrypt.CTL_ENTRY[0];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  271 */       WinCrypt.CTL_ENTRY[] result = (WinCrypt.CTL_ENTRY[])((WinCrypt.CTL_ENTRY)Structure.newInstance(WinCrypt.CTL_ENTRY.class, this.rgCTLEntry)).toArray(this.cCTLEntry);
/*  272 */       result[0].read();
/*  273 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     public WinCrypt.CERT_EXTENSION[] getRgExtension() {
/*  278 */       if (this.cExtension == 0) {
/*  279 */         return new WinCrypt.CERT_EXTENSION[0];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  284 */       WinCrypt.CERT_EXTENSION[] result = (WinCrypt.CERT_EXTENSION[])((WinCrypt.CERT_EXTENSION)Structure.newInstance(WinCrypt.CERT_EXTENSION.class, this.rgExtension)).toArray(this.cExtension);
/*  285 */       result[0].read();
/*  286 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwMsgAndCertEncodingType", "pbCtlEncoded", "cbCtlEncoded", "pCtlInfo", "hCertStore", "hCryptMsg", "pbCtlContent", "cbCtlContent"})
/*      */   public static class CTL_CONTEXT
/*      */     extends Structure
/*      */   {
/*      */     public int dwMsgAndCertEncodingType;
/*      */ 
/*      */     
/*      */     public Pointer pbCtlEncoded;
/*      */ 
/*      */     
/*      */     public int cbCtlEncoded;
/*      */ 
/*      */     
/*      */     public WinCrypt.CTL_INFO.ByReference pCtlInfo;
/*      */ 
/*      */     
/*      */     public WinCrypt.HCERTSTORE hCertStore;
/*      */     
/*      */     public WinCrypt.HCRYPTMSG hCryptMsg;
/*      */     
/*      */     public Pointer pbCtlContent;
/*      */     
/*      */     public int cbCtlContent;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CTL_CONTEXT
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "pCtlEntry", "pCtlContext"})
/*      */   public static class CERT_TRUST_LIST_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public WinCrypt.CTL_ENTRY.ByReference pCtlEntry;
/*      */     
/*      */     public WinCrypt.CTL_CONTEXT.ByReference pCtlContext;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_TRUST_LIST_INFO
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cUsageIdentifier", "rgpszUsageIdentifier"})
/*      */   public static class CTL_USAGE
/*      */     extends Structure
/*      */   {
/*      */     public int cUsageIdentifier;
/*      */     
/*      */     public Pointer rgpszUsageIdentifier;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CTL_USAGE
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public String[] getRgpszUsageIdentier() {
/*  355 */       if (this.cUsageIdentifier == 0) {
/*  356 */         return new String[0];
/*      */       }
/*  358 */       return this.rgpszUsageIdentifier.getStringArray(0L, this.cUsageIdentifier);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRgpszUsageIdentier(String[] array) {
/*  363 */       if (array == null || array.length == 0) {
/*  364 */         this.cUsageIdentifier = 0;
/*  365 */         this.rgpszUsageIdentifier = null;
/*      */       } else {
/*  367 */         this.cUsageIdentifier = array.length;
/*  368 */         this.rgpszUsageIdentifier = (Pointer)new StringArray(array);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwType", "Usage"})
/*      */   public static class CERT_USAGE_MATCH
/*      */     extends Structure
/*      */   {
/*      */     public int dwType;
/*      */     
/*      */     public WinCrypt.CTL_USAGE Usage;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_USAGE_MATCH
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "RequestedUsage", "RequestedIssuancePolicy", "dwUrlRetrievalTimeout", "fCheckRevocationFreshnessTime", "dwRevocationFreshnessTime", "pftCacheResync", "pStrongSignPara", "dwStrongSignFlags"})
/*      */   public static class CERT_CHAIN_PARA
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public WinCrypt.CERT_USAGE_MATCH RequestedUsage;
/*      */     
/*      */     public WinCrypt.CERT_USAGE_MATCH RequestedIssuancePolicy;
/*      */     
/*      */     public int dwUrlRetrievalTimeout;
/*      */     
/*      */     public boolean fCheckRevocationFreshnessTime;
/*      */     
/*      */     public int dwRevocationFreshnessTime;
/*      */     
/*      */     public WinBase.FILETIME.ByReference pftCacheResync;
/*      */     
/*      */     public WinCrypt.CERT_STRONG_SIGN_PARA.ByReference pStrongSignPara;
/*      */     public int dwStrongSignFlags;
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_CHAIN_PARA
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CERT_CHAIN_PARA() {
/*  415 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwInfoChoice", "DUMMYUNIONNAME"})
/*      */   public static class CERT_STRONG_SIGN_PARA
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public int dwInfoChoice;
/*      */     
/*      */     public DUMMYUNION DUMMYUNIONNAME;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends WinCrypt.CERT_CHAIN_PARA
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public class DUMMYUNION
/*      */       extends Union
/*      */     {
/*      */       Pointer pvInfo;
/*      */       
/*      */       WinCrypt.CERT_STRONG_SIGN_SERIALIZED_INFO.ByReference pSerializedInfo;
/*      */       
/*      */       WTypes.LPSTR pszOID;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwFlags", "pwszCNGSignHashAlgids", "pwszCNGPubKeyMinBitLengths"})
/*      */   public static class CERT_STRONG_SIGN_SERIALIZED_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int dwFlags;
/*      */     
/*      */     public String pwszCNGSignHashAlgids;
/*      */     public String pwszCNGPubKeyMinBitLengths;
/*      */     
/*      */     public static class ByReference
/*      */       extends WinCrypt.CERT_CHAIN_PARA
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CERT_STRONG_SIGN_SERIALIZED_INFO() {
/*  462 */       super(W32APITypeMapper.UNICODE);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwError", "lChainIndex", "lElementIndex", "pvExtraPolicyStatus"})
/*      */   public static class CERT_CHAIN_POLICY_STATUS
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public int dwError;
/*      */     
/*      */     public int lChainIndex;
/*      */     
/*      */     public int lElementIndex;
/*      */     
/*      */     public Pointer pvExtraPolicyStatus;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_CHAIN_POLICY_STATUS
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "TrustStatus", "cElement", "rgpElement", "pTrustListInfo", "fHasRevocationFreshnessTime", "dwRevocationFreshnessTime"})
/*      */   public static class CERT_SIMPLE_CHAIN
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public WinCrypt.CERT_TRUST_STATUS TrustStatus;
/*      */     
/*      */     public int cElement;
/*      */     
/*      */     public Pointer rgpElement;
/*      */     
/*      */     public WinCrypt.CERT_TRUST_LIST_INFO.ByReference pTrustListInfo;
/*      */     
/*      */     public boolean fHasRevocationFreshnessTime;
/*      */     public int dwRevocationFreshnessTime;
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_SIMPLE_CHAIN
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CERT_SIMPLE_CHAIN() {
/*  510 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public WinCrypt.CERT_CHAIN_ELEMENT[] getRgpElement() {
/*  514 */       WinCrypt.CERT_CHAIN_ELEMENT[] elements = new WinCrypt.CERT_CHAIN_ELEMENT[this.cElement];
/*  515 */       for (int i = 0; i < elements.length; i++) {
/*  516 */         elements[i] = (WinCrypt.CERT_CHAIN_ELEMENT)Structure.newInstance(WinCrypt.CERT_CHAIN_ELEMENT.class, this.rgpElement
/*      */             
/*  518 */             .getPointer((i * Native.POINTER_SIZE)));
/*  519 */         elements[i].read();
/*      */       } 
/*  521 */       return elements;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwFlags", "pvExtraPolicyPara"})
/*      */   public static class CERT_CHAIN_POLICY_PARA
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public int dwFlags;
/*      */     
/*      */     public Pointer pvExtraPolicyPara;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_CHAIN_POLICY_PARA
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "TrustStatus", "cChain", "rgpChain", "cLowerQualityChainContext", "rgpLowerQualityChainContext", "fHasRevocationFreshnessTime", "dwRevocationFreshnessTime", "dwCreateFlags", "ChainId"})
/*      */   public static class CERT_CHAIN_CONTEXT
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public WinCrypt.CERT_TRUST_STATUS TrustStatus;
/*      */     
/*      */     public int cChain;
/*      */     
/*      */     public Pointer rgpChain;
/*      */     
/*      */     public int cLowerQualityChainContext;
/*      */     
/*      */     public Pointer rgpLowerQualityChainContext;
/*      */     
/*      */     public boolean fHasRevocationFreshnessTime;
/*      */     
/*      */     public int dwRevocationFreshnessTime;
/*      */     
/*      */     public int dwCreateFlags;
/*      */     public Guid.GUID ChainId;
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_CHAIN_CONTEXT
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public WinCrypt.CERT_SIMPLE_CHAIN[] getRgpChain() {
/*  571 */       WinCrypt.CERT_SIMPLE_CHAIN[] elements = new WinCrypt.CERT_SIMPLE_CHAIN[this.cChain];
/*  572 */       for (int i = 0; i < elements.length; i++) {
/*  573 */         elements[i] = (WinCrypt.CERT_SIMPLE_CHAIN)Structure.newInstance(WinCrypt.CERT_SIMPLE_CHAIN.class, this.rgpChain
/*      */             
/*  575 */             .getPointer((i * Native.POINTER_SIZE)));
/*  576 */         elements[i].read();
/*      */       } 
/*  578 */       return elements;
/*      */     }
/*      */     
/*      */     public CERT_CHAIN_CONTEXT[] getRgpLowerQualityChainContext() {
/*  582 */       CERT_CHAIN_CONTEXT[] elements = new CERT_CHAIN_CONTEXT[this.cLowerQualityChainContext];
/*  583 */       for (int i = 0; i < elements.length; i++) {
/*  584 */         elements[i] = (CERT_CHAIN_CONTEXT)Structure.newInstance(CERT_CHAIN_CONTEXT.class, this.rgpLowerQualityChainContext
/*      */             
/*  586 */             .getPointer((i * Native.POINTER_SIZE)));
/*  587 */         elements[i].read();
/*      */       } 
/*  589 */       return elements;
/*      */     }
/*      */     
/*      */     public CERT_CHAIN_CONTEXT() {
/*  593 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwCertEncodingType", "pbCertEncoded", "cbCertEncoded", "pCertInfo", "hCertStore"})
/*      */   public static class CERT_CONTEXT
/*      */     extends Structure
/*      */   {
/*      */     public int dwCertEncodingType;
/*      */ 
/*      */     
/*      */     public Pointer pbCertEncoded;
/*      */ 
/*      */     
/*      */     public int cbCertEncoded;
/*      */     
/*      */     public WinCrypt.CERT_INFO.ByReference pCertInfo;
/*      */     
/*      */     public WinCrypt.HCERTSTORE hCertStore;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_CONTEXT
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"pszObjId", "fCritical", "Value"})
/*      */   public static class CERT_EXTENSION
/*      */     extends Structure
/*      */   {
/*      */     public String pszObjId;
/*      */     
/*      */     public boolean fCritical;
/*      */     
/*      */     public WinCrypt.DATA_BLOB Value;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_EXTENSION
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public CERT_EXTENSION() {
/*  639 */       super(W32APITypeMapper.ASCII);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cExtension", "rgExtension"})
/*      */   public static class CERT_EXTENSIONS
/*      */     extends Structure
/*      */   {
/*      */     public int cExtension;
/*      */     
/*      */     public Pointer rgExtension;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_EXTENSIONS
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public WinCrypt.CERT_EXTENSION[] getRgExtension() {
/*  658 */       if (this.cExtension == 0) {
/*  659 */         return new WinCrypt.CERT_EXTENSION[0];
/*      */       }
/*      */ 
/*      */       
/*  663 */       WinCrypt.CERT_EXTENSION[] ces = (WinCrypt.CERT_EXTENSION[])((WinCrypt.CERT_EXTENSION)Structure.newInstance(WinCrypt.CERT_EXTENSION.class, this.rgExtension)).toArray(this.cExtension);
/*  664 */       ces[0].read();
/*  665 */       return ces;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwVersion", "SerialNumber", "SignatureAlgorithm", "Issuer", "NotBefore", "NotAfter", "Subject", "SubjectPublicKeyInfo", "IssuerUniqueId", "SubjectUniqueId", "cExtension", "rgExtension"})
/*      */   public static class CERT_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int dwVersion;
/*      */     
/*      */     public WinCrypt.DATA_BLOB SerialNumber;
/*      */     
/*      */     public WinCrypt.CRYPT_ALGORITHM_IDENTIFIER SignatureAlgorithm;
/*      */     
/*      */     public WinCrypt.DATA_BLOB Issuer;
/*      */     
/*      */     public WinBase.FILETIME NotBefore;
/*      */     public WinBase.FILETIME NotAfter;
/*      */     public WinCrypt.DATA_BLOB Subject;
/*      */     public WinCrypt.CERT_PUBLIC_KEY_INFO SubjectPublicKeyInfo;
/*      */     public WinCrypt.CRYPT_BIT_BLOB IssuerUniqueId;
/*      */     public WinCrypt.CRYPT_BIT_BLOB SubjectUniqueId;
/*      */     public int cExtension;
/*      */     public Pointer rgExtension;
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_INFO
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public WinCrypt.CERT_EXTENSION[] getRgExtension() {
/*  696 */       if (this.cExtension == 0) {
/*  697 */         return new WinCrypt.CERT_EXTENSION[0];
/*      */       }
/*      */ 
/*      */       
/*  701 */       WinCrypt.CERT_EXTENSION[] ces = (WinCrypt.CERT_EXTENSION[])((WinCrypt.CERT_EXTENSION)Structure.newInstance(WinCrypt.CERT_EXTENSION.class, this.rgExtension)).toArray(this.cExtension);
/*  702 */       ces[0].read();
/*  703 */       return ces;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"Algorithm", "PublicKey"})
/*      */   public static class CERT_PUBLIC_KEY_INFO
/*      */     extends Structure
/*      */   {
/*      */     public WinCrypt.CRYPT_ALGORITHM_IDENTIFIER Algorithm;
/*      */     
/*      */     public WinCrypt.CRYPT_BIT_BLOB PublicKey;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CERT_PUBLIC_KEY_INFO
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwCertEncodingType", "pbCrlEncoded", "cbCrlEncoded", "pCrlInfo", "hCertStore"})
/*      */   public static class CRL_CONTEXT
/*      */     extends Structure
/*      */   {
/*      */     public int dwCertEncodingType;
/*      */     
/*      */     public Pointer pbCrlEncoded;
/*      */     
/*      */     public int cbCrlEncoded;
/*      */     
/*      */     public WinCrypt.CRL_INFO.ByReference pCrlInfo;
/*      */     
/*      */     public WinCrypt.HCERTSTORE hCertStore;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRL_CONTEXT
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"SerialNumber", "RevocationDate", "cExtension", "rgExtension"})
/*      */   public static class CRL_ENTRY
/*      */     extends Structure
/*      */   {
/*      */     public WinCrypt.DATA_BLOB SerialNumber;
/*      */     
/*      */     public WinBase.FILETIME RevocationDate;
/*      */     
/*      */     public int cExtension;
/*      */     
/*      */     public Pointer rgExtension;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRL_ENTRY
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public WinCrypt.CERT_EXTENSION[] getRgExtension() {
/*  763 */       if (this.cExtension == 0) {
/*  764 */         return new WinCrypt.CERT_EXTENSION[0];
/*      */       }
/*      */ 
/*      */       
/*  768 */       WinCrypt.CERT_EXTENSION[] result = (WinCrypt.CERT_EXTENSION[])((WinCrypt.CERT_EXTENSION)Structure.newInstance(WinCrypt.CERT_EXTENSION.class, this.rgExtension)).toArray(this.cExtension);
/*  769 */       result[0].read();
/*  770 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwVersion", "SignatureAlgorithm", "Issuer", "ThisUpdate", "NextUpdate", "cCRLEntry", "rgCRLEntry", "cExtension", "rgExtension"})
/*      */   public static class CRL_INFO
/*      */     extends Structure
/*      */   {
/*      */     public int dwVersion;
/*      */     
/*      */     public WinCrypt.CRYPT_ALGORITHM_IDENTIFIER SignatureAlgorithm;
/*      */     
/*      */     public WinCrypt.DATA_BLOB Issuer;
/*      */     
/*      */     public WinBase.FILETIME ThisUpdate;
/*      */     
/*      */     public WinBase.FILETIME NextUpdate;
/*      */     
/*      */     public int cCRLEntry;
/*      */     public Pointer rgCRLEntry;
/*      */     public int cExtension;
/*      */     public Pointer rgExtension;
/*      */     
/*      */     public static class ByReference
/*      */       extends CRL_INFO
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public WinCrypt.CRL_ENTRY[] getRgCRLEntry() {
/*  799 */       if (this.cCRLEntry == 0) {
/*  800 */         return new WinCrypt.CRL_ENTRY[0];
/*      */       }
/*      */ 
/*      */       
/*  804 */       WinCrypt.CRL_ENTRY[] result = (WinCrypt.CRL_ENTRY[])((WinCrypt.CRL_ENTRY)Structure.newInstance(WinCrypt.CRL_ENTRY.class, this.rgCRLEntry)).toArray(this.cCRLEntry);
/*  805 */       result[0].read();
/*  806 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     public WinCrypt.CERT_EXTENSION[] getRgExtension() {
/*  811 */       if (this.cExtension == 0) {
/*  812 */         return new WinCrypt.CERT_EXTENSION[0];
/*      */       }
/*      */ 
/*      */       
/*  816 */       WinCrypt.CERT_EXTENSION[] result = (WinCrypt.CERT_EXTENSION[])((WinCrypt.CERT_EXTENSION)Structure.newInstance(WinCrypt.CERT_EXTENSION.class, this.rgExtension)).toArray(this.cExtension);
/*  817 */       result[0].read();
/*  818 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"pszObjId", "Parameters"})
/*      */   public static class CRYPT_ALGORITHM_IDENTIFIER
/*      */     extends Structure
/*      */   {
/*      */     public String pszObjId;
/*      */ 
/*      */     
/*      */     public WinCrypt.DATA_BLOB Parameters;
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRYPT_ALGORITHM_IDENTIFIER
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public CRYPT_ALGORITHM_IDENTIFIER() {
/*  841 */       super(W32APITypeMapper.ASCII);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"pszObjId", "cValue", "rgValue"})
/*      */   public static class CRYPT_ATTRIBUTE
/*      */     extends Structure
/*      */   {
/*      */     public String pszObjId;
/*      */     
/*      */     public int cValue;
/*      */     
/*      */     public WinCrypt.DATA_BLOB.ByReference rgValue;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRYPT_ATTRIBUTE
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public WinCrypt.DATA_BLOB[] getRgValue() {
/*  862 */       return (WinCrypt.DATA_BLOB[])this.rgValue.toArray(this.cValue);
/*      */     }
/*      */     
/*      */     public CRYPT_ATTRIBUTE() {
/*  866 */       super(W32APITypeMapper.ASCII);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbData", "pbData", "cUnusedBits"})
/*      */   public static class CRYPT_BIT_BLOB
/*      */     extends Structure
/*      */   {
/*      */     public int cbData;
/*      */     
/*      */     public Pointer pbData;
/*      */     
/*      */     public int cUnusedBits;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRYPT_BIT_BLOB
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"pwszContainerName", "pwszProvName", "dwProvType", "dwFlags", "cProvParam", "rgProvParam", "dwKeySpec"})
/*      */   public static class CRYPT_KEY_PROV_INFO
/*      */     extends Structure
/*      */   {
/*      */     public String pwszContainerName;
/*      */     
/*      */     public String pwszProvName;
/*      */     
/*      */     public int dwProvType;
/*      */     
/*      */     public int dwFlags;
/*      */     
/*      */     public int cProvParam;
/*      */     public Pointer rgProvParam;
/*      */     public int dwKeySpec;
/*      */     
/*      */     public static class ByReference
/*      */       extends CRYPT_KEY_PROV_INFO
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CRYPT_KEY_PROV_INFO() {
/*  909 */       super(W32APITypeMapper.UNICODE);
/*      */     }
/*      */     
/*      */     public WinCrypt.CRYPT_KEY_PROV_PARAM[] getRgProvParam() {
/*  913 */       WinCrypt.CRYPT_KEY_PROV_PARAM[] elements = new WinCrypt.CRYPT_KEY_PROV_PARAM[this.cProvParam];
/*  914 */       for (int i = 0; i < elements.length; i++) {
/*  915 */         elements[i] = (WinCrypt.CRYPT_KEY_PROV_PARAM)Structure.newInstance(WinCrypt.CRYPT_KEY_PROV_PARAM.class, this.rgProvParam
/*      */             
/*  917 */             .getPointer((i * Native.POINTER_SIZE)));
/*  918 */         elements[i].read();
/*      */       } 
/*  920 */       return elements;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwParam", "pbData", "cbData", "dwFlags"})
/*      */   public static class CRYPT_KEY_PROV_PARAM
/*      */     extends Structure
/*      */   {
/*      */     public int dwParam;
/*      */     
/*      */     public Pointer pbData;
/*      */     
/*      */     public int cbData;
/*      */     
/*      */     public int dwFlags;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRYPT_KEY_PROV_PARAM
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwMsgEncodingType", "pSigningCert", "HashAlgorithm", "pvHashAuxInfo", "cMsgCert", "rgpMsgCert", "cMsgCrl", "rgpMsgCrl", "cAuthAttr", "rgAuthAttr", "cUnauthAttr", "rgUnauthAttr", "dwFlags", "dwInnerContentType", "HashEncryptionAlgorithm", "pvHashEncryptionAuxInfo"})
/*      */   public static class CRYPT_SIGN_MESSAGE_PARA
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */     
/*      */     public int dwMsgEncodingType;
/*      */     
/*      */     public WinCrypt.CERT_CONTEXT.ByReference pSigningCert;
/*      */     
/*      */     public WinCrypt.CRYPT_ALGORITHM_IDENTIFIER HashAlgorithm;
/*      */     
/*      */     public Pointer pvHashAuxInfo;
/*      */     
/*      */     public int cMsgCert;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends CRYPT_SIGN_MESSAGE_PARA
/*      */       implements Structure.ByReference {}
/*      */     
/*  965 */     public Pointer rgpMsgCert = null;
/*      */     public int cMsgCrl;
/*  967 */     public Pointer rgpMsgCrl = null;
/*      */     public int cAuthAttr;
/*  969 */     public Pointer rgAuthAttr = null;
/*      */     public int cUnauthAttr;
/*  971 */     public Pointer rgUnauthAttr = null;
/*      */     public int dwFlags;
/*      */     public int dwInnerContentType;
/*      */     public WinCrypt.CRYPT_ALGORITHM_IDENTIFIER HashEncryptionAlgorithm;
/*      */     public Pointer pvHashEncryptionAuxInfo;
/*      */     
/*      */     public WinCrypt.CERT_CONTEXT[] getRgpMsgCert() {
/*  978 */       WinCrypt.CERT_CONTEXT[] elements = new WinCrypt.CERT_CONTEXT[this.cMsgCrl];
/*  979 */       for (int i = 0; i < elements.length; i++) {
/*  980 */         elements[i] = (WinCrypt.CERT_CONTEXT)Structure.newInstance(WinCrypt.CERT_CONTEXT.class, this.rgpMsgCert
/*      */             
/*  982 */             .getPointer((i * Native.POINTER_SIZE)));
/*  983 */         elements[i].read();
/*      */       } 
/*  985 */       return elements;
/*      */     }
/*      */     
/*      */     public WinCrypt.CRL_CONTEXT[] getRgpMsgCrl() {
/*  989 */       WinCrypt.CRL_CONTEXT[] elements = new WinCrypt.CRL_CONTEXT[this.cMsgCrl];
/*  990 */       for (int i = 0; i < elements.length; i++) {
/*  991 */         elements[i] = (WinCrypt.CRL_CONTEXT)Structure.newInstance(WinCrypt.CRL_CONTEXT.class, this.rgpMsgCrl
/*      */             
/*  993 */             .getPointer((i * Native.POINTER_SIZE)));
/*  994 */         elements[i].read();
/*      */       } 
/*  996 */       return elements;
/*      */     }
/*      */     
/*      */     public WinCrypt.CRYPT_ATTRIBUTE[] getRgAuthAttr() {
/* 1000 */       if (this.cAuthAttr == 0) {
/* 1001 */         return new WinCrypt.CRYPT_ATTRIBUTE[0];
/*      */       }
/* 1003 */       return (WinCrypt.CRYPT_ATTRIBUTE[])((WinCrypt.CRYPT_ATTRIBUTE)Structure.newInstance(WinCrypt.CRYPT_ATTRIBUTE.class, this.rgAuthAttr))
/*      */ 
/*      */         
/* 1006 */         .toArray(this.cAuthAttr);
/*      */     }
/*      */ 
/*      */     
/*      */     public WinCrypt.CRYPT_ATTRIBUTE[] getRgUnauthAttr() {
/* 1011 */       if (this.cUnauthAttr == 0) {
/* 1012 */         return new WinCrypt.CRYPT_ATTRIBUTE[0];
/*      */       }
/* 1014 */       return (WinCrypt.CRYPT_ATTRIBUTE[])((WinCrypt.CRYPT_ATTRIBUTE)Structure.newInstance(WinCrypt.CRYPT_ATTRIBUTE.class, this.rgUnauthAttr))
/*      */ 
/*      */         
/* 1017 */         .toArray(this.cUnauthAttr);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwMsgAndCertEncodingType", "hCryptProv", "pfnGetSignerCertificate", "pvGetArg", "pStrongSignPara"})
/*      */   public static class CRYPT_VERIFY_MESSAGE_PARA
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwMsgAndCertEncodingType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinCrypt.HCRYPTPROV_LEGACY hCryptProv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinCrypt.CryptGetSignerCertificateCallback pfnGetSignerCertificate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer pvGetArg;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinCrypt.CERT_STRONG_SIGN_PARA.ByReference pStrongSignPara;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends WinCrypt.CRYPT_SIGN_MESSAGE_PARA
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write() {
/* 1082 */       this.cbSize = size();
/* 1083 */       super.write();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HCERTCHAINENGINE
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HCERTCHAINENGINE() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HCERTCHAINENGINE(Pointer p) {
/* 1105 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HCERTSTORE
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HCERTSTORE() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HCERTSTORE(Pointer p) {
/* 1128 */       super(p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class HCRYPTMSG
/*      */     extends WinNT.HANDLE
/*      */   {
/*      */     public HCRYPTMSG() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HCRYPTMSG(Pointer p) {
/* 1151 */       super(p);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class HCRYPTPROV_LEGACY
/*      */     extends BaseTSD.ULONG_PTR
/*      */   {
/*      */     public HCRYPTPROV_LEGACY() {}
/*      */     
/*      */     public HCRYPTPROV_LEGACY(long value) {
/* 1161 */       super(value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"cbSize", "dwPromptFlags", "hwndApp", "szPrompt"})
/*      */   public static class CRYPTPROTECT_PROMPTSTRUCT
/*      */     extends Structure
/*      */   {
/*      */     public int cbSize;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwPromptFlags;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.HWND hwndApp;
/*      */ 
/*      */ 
/*      */     
/*      */     public String szPrompt;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CRYPTPROTECT_PROMPTSTRUCT() {
/* 1190 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public CRYPTPROTECT_PROMPTSTRUCT(Pointer memory) {
/* 1194 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/* 1195 */       read();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class CertStoreProviderName
/*      */     implements NativeMapped
/*      */   {
/*      */     private final Pointer pointer;
/*      */     
/*      */     private CertStoreProviderName(Pointer fixedValue) {
/* 1206 */       this.pointer = fixedValue;
/*      */     }
/*      */     
/*      */     public CertStoreProviderName() {
/* 1210 */       this.pointer = Pointer.NULL;
/*      */     }
/*      */     
/*      */     public CertStoreProviderName(int fixedValue) {
/* 1214 */       this.pointer = Pointer.createConstant(fixedValue);
/*      */     }
/*      */     
/*      */     public CertStoreProviderName(String name) {
/* 1218 */       byte[] nameBytes = Native.toByteArray(name);
/* 1219 */       this.pointer = (Pointer)new Memory(nameBytes.length);
/* 1220 */       this.pointer.write(0L, nameBytes, 0, nameBytes.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object fromNative(Object nativeValue, FromNativeContext fnc) {
/* 1225 */       if (nativeValue == null) {
/* 1226 */         return null;
/*      */       }
/* 1228 */       return new CertStoreProviderName((Pointer)nativeValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object toNative() {
/* 1233 */       return this.pointer;
/*      */     }
/*      */ 
/*      */     
/*      */     public Class<?> nativeType() {
/* 1238 */       return Pointer.class;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1529 */   public static final HCERTCHAINENGINE HCCE_CURRENT_USER = new HCERTCHAINENGINE(Pointer.createConstant(0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1537 */   public static final HCERTCHAINENGINE HCCE_LOCAL_MACHINE = new HCERTCHAINENGINE(Pointer.createConstant(1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1545 */   public static final HCERTCHAINENGINE HCCE_SERIAL_LOCAL_MACHINE = new HCERTCHAINENGINE(Pointer.createConstant(2));
/*      */   public static final int CERT_COMPARE_SHIFT = 16;
/*      */   public static final int CERT_COMPARE_NAME_STR_W = 8;
/*      */   public static final int CERT_INFO_SUBJECT_FLAG = 7;
/*      */   public static final int CERT_FIND_SUBJECT_STR_W = 524295;
/*      */   public static final int CERT_FIND_SUBJECT_STR = 524295;
/*      */   public static final int CRYPT_EXPORTABLE = 1;
/*      */   public static final int CRYPT_USER_PROTECTED = 2;
/*      */   public static final int CRYPT_MACHINE_KEYSET = 32;
/*      */   public static final int CRYPT_USER_KEYSET = 4096;
/*      */   public static final int PKCS12_PREFER_CNG_KSP = 256;
/*      */   public static final int PKCS12_ALWAYS_CNG_KSP = 512;
/*      */   public static final int PKCS12_ALLOW_OVERWRITE_KEY = 16384;
/*      */   public static final int PKCS12_NO_PERSIST_KEY = 32768;
/*      */   public static final int PKCS12_INCLUDE_EXTENDED_PROPERTIES = 16;
/*      */   public static final int CERT_STORE_PROV_MSG = 1;
/*      */   public static final int CERT_STORE_PROV_MEMORY = 2;
/*      */   public static final int CERT_STORE_PROV_FILE = 3;
/*      */   public static final int CERT_STORE_PROV_REG = 4;
/*      */   public static final int CERT_STORE_PROV_PKCS7 = 5;
/*      */   public static final int CERT_STORE_PROV_SERIALIZED = 6;
/*      */   public static final int CERT_STORE_PROV_FILENAME_A = 7;
/*      */   public static final int CERT_STORE_PROV_FILENAME_W = 8;
/*      */   public static final int CERT_STORE_PROV_FILENAME = 8;
/*      */   public static final int CERT_STORE_PROV_SYSTEM_A = 9;
/*      */   public static final int CERT_STORE_PROV_SYSTEM_W = 10;
/*      */   public static final int CERT_STORE_PROV_SYSTEM = 10;
/*      */   public static final int CERT_STORE_PROV_COLLECTION = 11;
/*      */   public static final int CERT_STORE_PROV_SYSTEM_REGISTRY_A = 12;
/*      */   public static final int CERT_STORE_PROV_SYSTEM_REGISTRY_W = 13;
/*      */   public static final int CERT_STORE_PROV_SYSTEM_REGISTRY = 13;
/*      */   public static final int CERT_STORE_PROV_PHYSICAL_W = 14;
/*      */   public static final int CERT_STORE_PROV_PHYSICAL = 14;
/*      */   public static final int CERT_STORE_PROV_SMART_CARD_W = 15;
/*      */   public static final int CERT_STORE_PROV_SMART_CARD = 15;
/*      */   public static final int CERT_STORE_PROV_LDAP_W = 16;
/*      */   public static final int CERT_STORE_PROV_LDAP = 16;
/*      */   public static final int CERT_STORE_NO_CRYPT_RELEASE_FLAG = 1;
/*      */   public static final int CERT_STORE_SET_LOCALIZED_NAME_FLAG = 2;
/*      */   public static final int CERT_STORE_DEFER_CLOSE_UNTIL_LAST_FREE_FLAG = 4;
/*      */   public static final int CERT_STORE_DELETE_FLAG = 16;
/*      */   public static final int CERT_STORE_UNSAFE_PHYSICAL_FLAG = 32;
/*      */   public static final int CERT_STORE_SHARE_STORE_FLAG = 64;
/*      */   public static final int CERT_STORE_SHARE_CONTEXT_FLAG = 128;
/*      */   public static final int CERT_STORE_MANIFOLD_FLAG = 256;
/*      */   public static final int CERT_STORE_ENUM_ARCHIVED_FLAG = 512;
/*      */   public static final int CERT_STORE_UPDATE_KEYID_FLAG = 1024;
/*      */   public static final int CERT_STORE_BACKUP_RESTORE_FLAG = 2048;
/*      */   public static final int CERT_STORE_MAXIMUM_ALLOWED_FLAG = 4096;
/*      */   public static final int CERT_STORE_CREATE_NEW_FLAG = 8192;
/*      */   public static final int CERT_STORE_OPEN_EXISTING_FLAG = 16384;
/*      */   public static final int CERT_STORE_READONLY_FLAG = 32768;
/*      */   public static final int CERT_SYSTEM_STORE_CURRENT_USER = 65536;
/*      */   public static final int CERT_SYSTEM_STORE_LOCAL_MACHINE = 131072;
/*      */   public static final int CERT_SYSTEM_STORE_CURRENT_SERVICE = 262144;
/*      */   public static final int CERT_SYSTEM_STORE_SERVICES = 327680;
/*      */   public static final int CERT_SYSTEM_STORE_USERS = 393216;
/*      */   public static final int CERT_SYSTEM_STORE_CURRENT_USER_GROUP_POLICY = 458752;
/*      */   public static final int CERT_SYSTEM_STORE_LOCAL_MACHINE_GROUP_POLICY = 524288;
/*      */   public static final int CERT_SYSTEM_STORE_LOCAL_MACHINE_ENTERPRISE = 589824;
/*      */   public static final int CERT_SYSTEM_STORE_UNPROTECTED_FLAG = 1073741824;
/*      */   public static final int CERT_SYSTEM_STORE_RELOCATE_FLAG = -2147483648;
/*      */   public static final int CERT_CLOSE_STORE_FORCE_FLAG = 1;
/*      */   public static final int CERT_CLOSE_STORE_CHECK_FLAG = 2;
/*      */   public static final int CERT_QUERY_CONTENT_CERT = 1;
/*      */   public static final int CERT_QUERY_CONTENT_CTL = 2;
/*      */   public static final int CERT_QUERY_CONTENT_CRL = 3;
/*      */   public static final int CERT_QUERY_CONTENT_SERIALIZED_STORE = 4;
/*      */   public static final int CERT_QUERY_CONTENT_SERIALIZED_CERT = 5;
/*      */   public static final int CERT_QUERY_CONTENT_SERIALIZED_CTL = 6;
/*      */   public static final int CERT_QUERY_CONTENT_SERIALIZED_CRL = 7;
/*      */   public static final int CERT_QUERY_CONTENT_PKCS7_SIGNED = 8;
/*      */   public static final int CERT_QUERY_CONTENT_PKCS7_UNSIGNED = 9;
/*      */   public static final int CERT_QUERY_CONTENT_PKCS7_SIGNED_EMBED = 10;
/*      */   public static final int CERT_QUERY_CONTENT_PKCS10 = 11;
/*      */   public static final int CERT_QUERY_CONTENT_PFX = 12;
/*      */   public static final int CERT_QUERY_CONTENT_CERT_PAIR = 13;
/*      */   public static final int CERT_QUERY_CONTENT_PFX_AND_LOAD = 14;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_CERT = 2;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_CTL = 4;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_CRL = 8;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_SERIALIZED_STORE = 16;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_SERIALIZED_CERT = 32;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_SERIALIZED_CTL = 64;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_SERIALIZED_CRL = 128;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_PKCS7_SIGNED = 256;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_PKCS7_UNSIGNED = 512;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_PKCS7_SIGNED_EMBED = 1024;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_PKCS10 = 2048;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_PFX = 4096;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_CERT_PAIR = 8192;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_PFX_AND_LOAD = 16384;
/*      */   public static final int CERT_QUERY_CONTENT_FLAG_ALL = 16382;
/*      */   public static final int CERT_QUERY_FORMAT_BINARY = 1;
/*      */   public static final int CERT_QUERY_FORMAT_BASE64_ENCODED = 2;
/*      */   public static final int CERT_QUERY_FORMAT_ASN_ASCII_HEX_ENCODED = 3;
/*      */   public static final int CERT_QUERY_FORMAT_FLAG_BINARY = 2;
/*      */   public static final int CERT_QUERY_FORMAT_FLAG_BASE64_ENCODED = 4;
/*      */   public static final int CERT_QUERY_FORMAT_FLAG_ASN_ASCII_HEX_ENCODED = 8;
/*      */   public static final int CERT_QUERY_FORMAT_FLAG_ALL = 14;
/*      */   public static final int CERT_QUERY_OBJECT_FILE = 1;
/*      */   public static final int CERT_QUERY_OBJECT_BLOB = 2;
/*      */   
/*      */   public static interface CryptGetSignerCertificateCallback extends StdCallLibrary.StdCallCallback {
/*      */     WinCrypt.CERT_CONTEXT.ByReference callback(Pointer param1Pointer, int param1Int, WinCrypt.CERT_INFO param1CERT_INFO, WinCrypt.HCERTSTORE param1HCERTSTORE);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\WinCrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */