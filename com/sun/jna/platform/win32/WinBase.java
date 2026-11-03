/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.Callback;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.Platform;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Structure.FieldOrder;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.ptr.ByteByReference;
/*      */ import com.sun.jna.win32.StdCallLibrary;
/*      */ import com.sun.jna.win32.W32APITypeMapper;
/*      */ import java.text.DateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface WinBase
/*      */   extends WinDef, BaseTSD
/*      */ {
/*   51 */   public static final WinNT.HANDLE INVALID_HANDLE_VALUE = new WinNT.HANDLE(
/*   52 */       Pointer.createConstant((Native.POINTER_SIZE == 8) ? 
/*   53 */         -1L : 4294967295L)); public static final int WAIT_FAILED = -1; public static final int WAIT_OBJECT_0 = 0; public static final int WAIT_ABANDONED = 128; public static final int WAIT_ABANDONED_0 = 128; public static final int LOGON32_LOGON_INTERACTIVE = 2; public static final int LOGON32_LOGON_NETWORK = 3; public static final int LOGON32_LOGON_BATCH = 4; public static final int LOGON32_LOGON_SERVICE = 5; public static final int LOGON32_LOGON_UNLOCK = 7; public static final int LOGON32_LOGON_NETWORK_CLEARTEXT = 8; public static final int LOGON32_LOGON_NEW_CREDENTIALS = 9; public static final int LOGON32_PROVIDER_DEFAULT = 0; public static final int LOGON32_PROVIDER_WINNT35 = 1; public static final int LOGON32_PROVIDER_WINNT40 = 2; public static final int LOGON32_PROVIDER_WINNT50 = 3; public static final int HANDLE_FLAG_INHERIT = 1; public static final int HANDLE_FLAG_PROTECT_FROM_CLOSE = 2; public static final int STARTF_USESHOWWINDOW = 1; public static final int STARTF_USESIZE = 2; public static final int STARTF_USEPOSITION = 4; public static final int STARTF_USECOUNTCHARS = 8; public static final int STARTF_USEFILLATTRIBUTE = 16; public static final int STARTF_RUNFULLSCREEN = 32; public static final int STARTF_FORCEONFEEDBACK = 64; public static final int STARTF_FORCEOFFFEEDBACK = 128; public static final int STARTF_USESTDHANDLES = 256; public static final int DEBUG_PROCESS = 1; public static final int DEBUG_ONLY_THIS_PROCESS = 2; public static final int CREATE_SUSPENDED = 4; public static final int DETACHED_PROCESS = 8; public static final int CREATE_NEW_CONSOLE = 16; public static final int CREATE_NEW_PROCESS_GROUP = 512; public static final int CREATE_UNICODE_ENVIRONMENT = 1024; public static final int CREATE_SEPARATE_WOW_VDM = 2048; public static final int CREATE_SHARED_WOW_VDM = 4096; public static final int CREATE_FORCEDOS = 8192; public static final int INHERIT_PARENT_AFFINITY = 65536; public static final int CREATE_PROTECTED_PROCESS = 262144; public static final int EXTENDED_STARTUPINFO_PRESENT = 524288; public static final int CREATE_BREAKAWAY_FROM_JOB = 16777216; public static final int CREATE_PRESERVE_CODE_AUTHZ_LEVEL = 33554432; public static final int CREATE_DEFAULT_ERROR_MODE = 67108864; public static final int CREATE_NO_WINDOW = 134217728; public static final int FILE_ENCRYPTABLE = 0; public static final int FILE_IS_ENCRYPTED = 1; public static final int FILE_SYSTEM_ATTR = 2; public static final int FILE_ROOT_DIR = 3; public static final int FILE_SYSTEM_DIR = 4; public static final int FILE_UNKNOWN = 5; public static final int FILE_SYSTEM_NOT_SUPPORT = 6; public static final int FILE_USER_DISALLOWED = 7; public static final int FILE_READ_ONLY = 8; public static final int FILE_DIR_DISALOWED = 9; public static final int CREATE_FOR_IMPORT = 1; public static final int CREATE_FOR_DIR = 2; public static final int OVERWRITE_HIDDEN = 4; public static final int INVALID_FILE_SIZE = -1; public static final int INVALID_SET_FILE_POINTER = -1; public static final int INVALID_FILE_ATTRIBUTES = -1; public static final int STILL_ACTIVE = 259; public static final int FileBasicInfo = 0; public static final int FileStandardInfo = 1; public static final int FileNameInfo = 2; public static final int FileRenameInfo = 3; public static final int FileDispositionInfo = 4; public static final int FileAllocationInfo = 5; public static final int FileEndOfFileInfo = 6; public static final int FileStreamInfo = 7; public static final int FileCompressionInfo = 8; public static final int FileAttributeTagInfo = 9; public static final int FileIdBothDirectoryInfo = 10; public static final int FileIdBothDirectoryRestartInfo = 11; public static final int FileIoPriorityHintInfo = 12; public static final int FileRemoteProtocolInfo = 13; public static final int FileFullDirectoryInfo = 14; public static final int FileFullDirectoryRestartInfo = 15; public static final int FileStorageInfo = 16; public static final int FileAlignmentInfo = 17; public static final int FileIdInfo = 18; public static final int FileIdExtdDirectoryInfo = 19; public static final int FileIdExtdDirectoryRestartInfo = 20;
/*      */   public static final int FILE_MAP_COPY = 1;
/*      */   public static final int FILE_MAP_WRITE = 2;
/*      */   public static final int FILE_MAP_READ = 4;
/*      */   public static final int FILE_MAP_ALL_ACCESS = 983071;
/*      */   public static final int FILE_MAP_EXECUTE = 32;
/*      */   public static final int FindExInfoStandard = 0;
/*      */   public static final int FindExInfoBasic = 1;
/*      */   public static final int FindExInfoMaxInfoLevel = 2;
/*      */   public static final int FindExSearchNameMatch = 0;
/*      */   public static final int FindExSearchLimitToDirectories = 1;
/*   64 */   public static final int MAX_COMPUTERNAME_LENGTH = Platform.isMac() ? 15 : 31;
/*      */   
/*      */   public static final int FindExSearchLimitToDevices = 2;
/*      */   
/*      */   public static final int LMEM_FIXED = 0;
/*      */   
/*      */   public static final int LMEM_MOVEABLE = 2;
/*      */   
/*      */   public static final int LMEM_NOCOMPACT = 16;
/*      */   
/*      */   public static final int LMEM_NODISCARD = 32;
/*      */   
/*      */   public static final int LMEM_ZEROINIT = 64;
/*      */   
/*      */   public static final int LMEM_MODIFY = 128;
/*      */   
/*      */   public static final int LMEM_DISCARDABLE = 3840;
/*      */   
/*      */   public static final int LMEM_VALID_FLAGS = 3954;
/*      */   
/*      */   public static final int LMEM_INVALID_HANDLE = 32768;
/*      */   
/*      */   public static final int LHND = 66;
/*      */   
/*      */   public static final int LPTR = 64;
/*      */   
/*      */   public static final int LMEM_DISCARDED = 16384;
/*      */   
/*      */   public static final int LMEM_LOCKCOUNT = 255;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_ALLOCATE_BUFFER = 256;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_IGNORE_INSERTS = 512;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_FROM_STRING = 1024;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_FROM_HMODULE = 2048;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_FROM_SYSTEM = 4096;
/*      */   
/*      */   public static final int FORMAT_MESSAGE_ARGUMENT_ARRAY = 8192;
/*      */   
/*      */   public static final int DRIVE_UNKNOWN = 0;
/*      */   
/*      */   public static final int DRIVE_NO_ROOT_DIR = 1;
/*      */   
/*      */   public static final int DRIVE_REMOVABLE = 2;
/*      */   
/*      */   public static final int DRIVE_FIXED = 3;
/*      */   
/*      */   public static final int DRIVE_REMOTE = 4;
/*      */   
/*      */   public static final int DRIVE_CDROM = 5;
/*      */   
/*      */   public static final int DRIVE_RAMDISK = 6;
/*      */   
/*      */   public static final int INFINITE = -1;
/*      */   
/*      */   public static final int MOVEFILE_COPY_ALLOWED = 2;
/*      */   
/*      */   public static final int MOVEFILE_CREATE_HARDLINK = 16;
/*      */   
/*      */   public static final int MOVEFILE_DELAY_UNTIL_REBOOT = 4;
/*      */   
/*      */   public static final int MOVEFILE_FAIL_IF_NOT_TRACKABLE = 32;
/*      */   
/*      */   public static final int MOVEFILE_REPLACE_EXISTING = 1;
/*      */   
/*      */   public static final int MOVEFILE_WRITE_THROUGH = 8;
/*      */   
/*      */   public static final int PIPE_CLIENT_END = 0;
/*      */   
/*      */   public static final int PIPE_SERVER_END = 1;
/*      */   
/*      */   public static final int PIPE_ACCESS_DUPLEX = 3;
/*      */   
/*      */   public static final int PIPE_ACCESS_INBOUND = 1;
/*      */   
/*      */   public static final int PIPE_ACCESS_OUTBOUND = 2;
/*      */   
/*      */   public static final int PIPE_TYPE_BYTE = 0;
/*      */   
/*      */   public static final int PIPE_TYPE_MESSAGE = 4;
/*      */   
/*      */   public static final int PIPE_READMODE_BYTE = 0;
/*      */   
/*      */   public static final int PIPE_READMODE_MESSAGE = 2;
/*      */   
/*      */   public static final int PIPE_WAIT = 0;
/*      */   
/*      */   public static final int PIPE_NOWAIT = 1;
/*      */   
/*      */   public static final int PIPE_ACCEPT_REMOTE_CLIENTS = 0;
/*      */   
/*      */   public static final int PIPE_REJECT_REMOTE_CLIENTS = 8;
/*      */   
/*      */   public static final int PIPE_UNLIMITED_INSTANCES = 255;
/*      */   
/*      */   public static final int NMPWAIT_USE_DEFAULT_WAIT = 0;
/*      */   
/*      */   public static final int NMPWAIT_NOWAIT = 1;
/*      */   
/*      */   public static final int NMPWAIT_WAIT_FOREVER = -1;
/*      */   
/*      */   public static final int NOPARITY = 0;
/*      */   
/*      */   public static final int ODDPARITY = 1;
/*      */   
/*      */   public static final int EVENPARITY = 2;
/*      */   
/*      */   public static final int MARKPARITY = 3;
/*      */   
/*      */   public static final int SPACEPARITY = 4;
/*      */   
/*      */   public static final int ONESTOPBIT = 0;
/*      */   
/*      */   public static final int ONE5STOPBITS = 1;
/*      */   
/*      */   public static final int TWOSTOPBITS = 2;
/*      */   
/*      */   public static final int CBR_110 = 110;
/*      */   
/*      */   public static final int CBR_300 = 300;
/*      */   
/*      */   public static final int CBR_600 = 600;
/*      */   
/*      */   public static final int CBR_1200 = 1200;
/*      */   
/*      */   public static final int CBR_2400 = 2400;
/*      */   
/*      */   public static final int CBR_4800 = 4800;
/*      */   
/*      */   public static final int CBR_9600 = 9600;
/*      */   
/*      */   public static final int CBR_14400 = 14400;
/*      */   
/*      */   public static final int CBR_19200 = 19200;
/*      */   
/*      */   public static final int CBR_38400 = 38400;
/*      */   
/*      */   public static final int CBR_56000 = 56000;
/*      */   
/*      */   public static final int CBR_128000 = 128000;
/*      */   public static final int CBR_256000 = 256000;
/*      */   public static final int DTR_CONTROL_DISABLE = 0;
/*      */   public static final int DTR_CONTROL_ENABLE = 1;
/*      */   public static final int DTR_CONTROL_HANDSHAKE = 2;
/*      */   public static final int RTS_CONTROL_DISABLE = 0;
/*      */   public static final int RTS_CONTROL_ENABLE = 1;
/*      */   public static final int RTS_CONTROL_HANDSHAKE = 2;
/*      */   public static final int RTS_CONTROL_TOGGLE = 3;
/*      */   public static final int ES_AWAYMODE_REQUIRED = 64;
/*      */   public static final int ES_CONTINUOUS = -2147483648;
/*      */   public static final int ES_DISPLAY_REQUIRED = 2;
/*      */   public static final int ES_SYSTEM_REQUIRED = 1;
/*      */   public static final int ES_USER_PRESENT = 4;
/*      */   public static final int MUTEX_MODIFY_STATE = 1;
/*      */   public static final int MUTEX_ALL_ACCESS = 2031617;
/*      */   
/*      */   @FieldOrder({"CreationTime", "LastAccessTime", "LastWriteTime", "ChangeTime", "FileAttributes"})
/*      */   public static class FILE_BASIC_INFO
/*      */     extends Structure
/*      */   {
/*      */     public WinNT.LARGE_INTEGER CreationTime;
/*      */     public WinNT.LARGE_INTEGER LastAccessTime;
/*      */     public WinNT.LARGE_INTEGER LastWriteTime;
/*      */     public WinNT.LARGE_INTEGER ChangeTime;
/*      */     public int FileAttributes;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_BASIC_INFO
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference(Pointer memory) {
/*  238 */         super(memory);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ByReference() {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  272 */       return Native.getNativeSize(FILE_BASIC_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO(Pointer memory) {
/*  280 */       super(memory);
/*  281 */       read();
/*      */       
/*  283 */       this.CreationTime = new WinNT.LARGE_INTEGER(this.CreationTime.getValue());
/*  284 */       this.LastAccessTime = new WinNT.LARGE_INTEGER(this.LastAccessTime.getValue());
/*  285 */       this.LastWriteTime = new WinNT.LARGE_INTEGER(this.LastWriteTime.getValue());
/*  286 */       this.ChangeTime = new WinNT.LARGE_INTEGER(this.ChangeTime.getValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO(WinBase.FILETIME CreationTime, WinBase.FILETIME LastAccessTime, WinBase.FILETIME LastWriteTime, WinBase.FILETIME ChangeTime, int FileAttributes) {
/*  294 */       this.CreationTime = new WinNT.LARGE_INTEGER(CreationTime.toTime());
/*  295 */       this.LastAccessTime = new WinNT.LARGE_INTEGER(LastAccessTime.toTime());
/*  296 */       this.LastWriteTime = new WinNT.LARGE_INTEGER(LastWriteTime.toTime());
/*  297 */       this.ChangeTime = new WinNT.LARGE_INTEGER(ChangeTime.toTime());
/*  298 */       this.FileAttributes = FileAttributes;
/*  299 */       write();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_BASIC_INFO(WinNT.LARGE_INTEGER CreationTime, WinNT.LARGE_INTEGER LastAccessTime, WinNT.LARGE_INTEGER LastWriteTime, WinNT.LARGE_INTEGER ChangeTime, int FileAttributes) {
/*  307 */       this.CreationTime = CreationTime;
/*  308 */       this.LastAccessTime = LastAccessTime;
/*  309 */       this.LastWriteTime = LastWriteTime;
/*  310 */       this.ChangeTime = ChangeTime;
/*  311 */       this.FileAttributes = FileAttributes;
/*  312 */       write();
/*      */     } }
/*      */   
/*      */   @FieldOrder({"AllocationSize", "EndOfFile", "NumberOfLinks", "DeletePending", "Directory"})
/*      */   public static class FILE_STANDARD_INFO extends Structure {
/*      */     public WinNT.LARGE_INTEGER AllocationSize;
/*      */     public WinNT.LARGE_INTEGER EndOfFile;
/*      */     public int NumberOfLinks;
/*      */     public boolean DeletePending;
/*      */     public boolean Directory;
/*      */     
/*      */     public static class ByReference extends FILE_STANDARD_INFO implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  327 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  358 */       return Native.getNativeSize(FILE_STANDARD_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_STANDARD_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_STANDARD_INFO(Pointer memory) {
/*  366 */       super(memory);
/*  367 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_STANDARD_INFO(WinNT.LARGE_INTEGER AllocationSize, WinNT.LARGE_INTEGER EndOfFile, int NumberOfLinks, boolean DeletePending, boolean Directory) {
/*  375 */       this.AllocationSize = AllocationSize;
/*  376 */       this.EndOfFile = EndOfFile;
/*  377 */       this.NumberOfLinks = NumberOfLinks;
/*  378 */       this.DeletePending = DeletePending;
/*  379 */       this.Directory = Directory;
/*  380 */       write();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"DeleteFile"})
/*      */   public static class FILE_DISPOSITION_INFO
/*      */     extends Structure {
/*      */     public boolean DeleteFile;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_DISPOSITION_INFO
/*      */       implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  395 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  407 */       return Native.getNativeSize(FILE_DISPOSITION_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_DISPOSITION_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_DISPOSITION_INFO(Pointer memory) {
/*  415 */       super(memory);
/*  416 */       read();
/*      */     }
/*      */     
/*      */     public FILE_DISPOSITION_INFO(boolean DeleteFile) {
/*  420 */       this.DeleteFile = DeleteFile;
/*  421 */       write();
/*      */     } }
/*      */   
/*      */   @FieldOrder({"CompressedFileSize", "CompressionFormat", "CompressionUnitShift", "ChunkShift", "ClusterShift", "Reserved"})
/*      */   public static class FILE_COMPRESSION_INFO extends Structure {
/*      */     public WinNT.LARGE_INTEGER CompressedFileSize;
/*      */     public short CompressionFormat;
/*      */     public byte CompressionUnitShift;
/*      */     public byte ChunkShift;
/*      */     public byte ClusterShift;
/*      */     
/*      */     public static class ByReference extends FILE_COMPRESSION_INFO implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  436 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  468 */     public byte[] Reserved = new byte[3];
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  472 */       return Native.getNativeSize(FILE_COMPRESSION_INFO.class, null);
/*      */     }
/*      */     
/*      */     public FILE_COMPRESSION_INFO() {
/*  476 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public FILE_COMPRESSION_INFO(Pointer memory) {
/*  480 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/*  481 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILE_COMPRESSION_INFO(WinNT.LARGE_INTEGER CompressedFileSize, short CompressionFormat, byte CompressionUnitShift, byte ChunkShift, byte ClusterShift) {
/*  489 */       this.CompressedFileSize = CompressedFileSize;
/*  490 */       this.CompressionFormat = CompressionFormat;
/*  491 */       this.CompressionUnitShift = CompressionUnitShift;
/*  492 */       this.ChunkShift = ChunkShift;
/*  493 */       this.ClusterShift = ClusterShift;
/*  494 */       this.Reserved = new byte[3];
/*  495 */       write();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"FileAttributes", "ReparseTag"})
/*      */   public static class FILE_ATTRIBUTE_TAG_INFO
/*      */     extends Structure {
/*      */     public int FileAttributes;
/*      */     public int ReparseTag;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_ATTRIBUTE_TAG_INFO implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  510 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  526 */       return Native.getNativeSize(FILE_ATTRIBUTE_TAG_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ATTRIBUTE_TAG_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_ATTRIBUTE_TAG_INFO(Pointer memory) {
/*  534 */       super(memory);
/*  535 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ATTRIBUTE_TAG_INFO(int FileAttributes, int ReparseTag) {
/*  540 */       this.FileAttributes = FileAttributes;
/*  541 */       this.ReparseTag = ReparseTag;
/*  542 */       write();
/*      */     }
/*      */   }
/*      */   
/*      */   @FieldOrder({"VolumeSerialNumber", "FileId"})
/*      */   public static class FILE_ID_INFO
/*      */     extends Structure
/*      */   {
/*      */     public long VolumeSerialNumber;
/*      */     public FILE_ID_128 FileId;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILE_ID_INFO
/*      */       implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  559 */         super(memory);
/*      */       }
/*      */     }
/*      */     
/*      */     @FieldOrder({"Identifier"})
/*      */     public static class FILE_ID_128 extends Structure {
/*  565 */       public WinDef.BYTE[] Identifier = new WinDef.BYTE[16];
/*      */ 
/*      */       
/*      */       public FILE_ID_128() {}
/*      */ 
/*      */       
/*      */       public FILE_ID_128(Pointer memory) {
/*  572 */         super(memory);
/*  573 */         read();
/*      */       }
/*      */       
/*      */       public FILE_ID_128(WinDef.BYTE[] Identifier) {
/*  577 */         this.Identifier = Identifier;
/*  578 */         write();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static int sizeOf() {
/*  594 */       return Native.getNativeSize(FILE_ID_INFO.class, null);
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ID_INFO() {}
/*      */ 
/*      */     
/*      */     public FILE_ID_INFO(Pointer memory) {
/*  602 */       super(memory);
/*  603 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public FILE_ID_INFO(long VolumeSerialNumber, FILE_ID_128 FileId) {
/*  608 */       this.VolumeSerialNumber = VolumeSerialNumber;
/*  609 */       this.FileId = FileId;
/*  610 */       write();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwFileAttributes", "ftCreationTime", "ftLastAccessTime", "ftLastWriteTime", "nFileSizeHigh", "nFileSizeLow", "dwReserved0", "dwReserved1", "cFileName", "cAlternateFileName"})
/*      */   public static class WIN32_FIND_DATA
/*      */     extends Structure
/*      */   {
/*      */     public int dwFileAttributes;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinBase.FILETIME ftCreationTime;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinBase.FILETIME ftLastAccessTime;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinBase.FILETIME ftLastWriteTime;
/*      */ 
/*      */ 
/*      */     
/*      */     public int nFileSizeHigh;
/*      */ 
/*      */ 
/*      */     
/*      */     public int nFileSizeLow;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwReserved0;
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwReserved1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends WIN32_FIND_DATA
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  667 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  744 */     public char[] cFileName = new char[260];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  752 */     public char[] cAlternateFileName = new char[14];
/*      */     
/*      */     public static int sizeOf() {
/*  755 */       return Native.getNativeSize(WIN32_FIND_DATA.class, null);
/*      */     }
/*      */     
/*      */     public WIN32_FIND_DATA() {
/*  759 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */     
/*      */     public WIN32_FIND_DATA(Pointer memory) {
/*  763 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/*  764 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WIN32_FIND_DATA(int dwFileAttributes, WinBase.FILETIME ftCreationTime, WinBase.FILETIME ftLastAccessTime, WinBase.FILETIME ftLastWriteTime, int nFileSizeHigh, int nFileSizeLow, int dwReserved0, int dwReserved1, char[] cFileName, char[] cAlternateFileName) {
/*  777 */       this.dwFileAttributes = dwFileAttributes;
/*  778 */       this.ftCreationTime = ftCreationTime;
/*  779 */       this.ftLastAccessTime = ftLastAccessTime;
/*  780 */       this.ftLastWriteTime = ftLastWriteTime;
/*  781 */       this.nFileSizeHigh = nFileSizeHigh;
/*  782 */       this.nFileSizeLow = nFileSizeLow;
/*  783 */       this.dwReserved0 = dwReserved0;
/*  784 */       this.cFileName = cFileName;
/*  785 */       this.cAlternateFileName = cAlternateFileName;
/*  786 */       write();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getFileName() {
/*  793 */       return Native.toString(this.cFileName);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAlternateFileName() {
/*  800 */       return Native.toString(this.cAlternateFileName);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwLowDateTime", "dwHighDateTime"})
/*      */   public static class FILETIME
/*      */     extends Structure
/*      */   {
/*      */     public int dwLowDateTime;
/*      */     public int dwHighDateTime;
/*      */     private static final long EPOCH_DIFF = 11644473600000L;
/*      */     
/*      */     public static class ByReference
/*      */       extends FILETIME
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer memory) {
/*  820 */         super(memory);
/*      */       }
/*      */     }
/*      */     
/*      */     public FILETIME(Date date) {
/*  825 */       long rawValue = dateToFileTime(date);
/*  826 */       this.dwHighDateTime = (int)(rawValue >> 32L & 0xFFFFFFFFL);
/*  827 */       this.dwLowDateTime = (int)(rawValue & 0xFFFFFFFFL);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FILETIME(WinNT.LARGE_INTEGER ft) {
/*  835 */       this.dwHighDateTime = ft.getHigh().intValue();
/*  836 */       this.dwLowDateTime = ft.getLow().intValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public FILETIME() {}
/*      */     
/*      */     public FILETIME(Pointer memory) {
/*  843 */       super(memory);
/*  844 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Date filetimeToDate(int high, int low) {
/*  870 */       long filetime = high << 32L | low & 0xFFFFFFFFL;
/*  871 */       long ms_since_16010101 = filetime / 10000L;
/*  872 */       long ms_since_19700101 = ms_since_16010101 - 11644473600000L;
/*  873 */       return new Date(ms_since_19700101);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static long dateToFileTime(Date date) {
/*  885 */       long ms_since_19700101 = date.getTime();
/*  886 */       long ms_since_16010101 = ms_since_19700101 + 11644473600000L;
/*  887 */       return ms_since_16010101 * 1000L * 10L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Date toDate() {
/*  895 */       return filetimeToDate(this.dwHighDateTime, this.dwLowDateTime);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long toTime() {
/*  905 */       return toDate().getTime();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG toDWordLong() {
/*  916 */       return new WinDef.DWORDLONG(this.dwHighDateTime << 32L | this.dwLowDateTime & 0xFFFFFFFFL);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  921 */       return super.toString() + ": " + toDate().toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"wYear", "wMonth", "wDayOfWeek", "wDay", "wHour", "wMinute", "wSecond", "wMilliseconds"})
/*      */   public static class SYSTEMTIME
/*      */     extends Structure
/*      */   {
/*      */     public short wYear;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short wMonth;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short wDayOfWeek;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wDay;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wHour;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wMinute;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wSecond;
/*      */ 
/*      */ 
/*      */     
/*      */     public short wMilliseconds;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SYSTEMTIME() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SYSTEMTIME(Date date) {
/*  974 */       this(date.getTime());
/*      */     }
/*      */     
/*      */     public SYSTEMTIME(long timestamp) {
/*  978 */       Calendar cal = Calendar.getInstance();
/*  979 */       cal.setTimeInMillis(timestamp);
/*  980 */       fromCalendar(cal);
/*      */     }
/*      */     
/*      */     public SYSTEMTIME(Calendar cal) {
/*  984 */       fromCalendar(cal);
/*      */     }
/*      */     
/*      */     public void fromCalendar(Calendar cal) {
/*  988 */       this.wYear = (short)cal.get(1);
/*  989 */       this.wMonth = (short)(1 + cal.get(2) - 0);
/*  990 */       this.wDay = (short)cal.get(5);
/*  991 */       this.wHour = (short)cal.get(11);
/*  992 */       this.wMinute = (short)cal.get(12);
/*  993 */       this.wSecond = (short)cal.get(13);
/*  994 */       this.wMilliseconds = (short)cal.get(14);
/*  995 */       this.wDayOfWeek = (short)(cal.get(7) - 1);
/*      */     }
/*      */     
/*      */     public Calendar toCalendar() {
/*  999 */       Calendar cal = Calendar.getInstance();
/* 1000 */       cal.set(1, this.wYear);
/* 1001 */       cal.set(2, 0 + this.wMonth - 1);
/* 1002 */       cal.set(5, this.wDay);
/* 1003 */       cal.set(11, this.wHour);
/* 1004 */       cal.set(12, this.wMinute);
/* 1005 */       cal.set(13, this.wSecond);
/* 1006 */       cal.set(14, this.wMilliseconds);
/* 1007 */       return cal;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1013 */       if (this.wYear == 0 && this.wMonth == 0 && this.wDay == 0 && this.wHour == 0 && this.wMinute == 0 && this.wSecond == 0 && this.wMilliseconds == 0)
/*      */       {
/*      */         
/* 1016 */         return super.toString();
/*      */       }
/*      */       
/* 1019 */       DateFormat dtf = DateFormat.getDateTimeInstance();
/* 1020 */       Calendar cal = toCalendar();
/* 1021 */       return dtf.format(cal.getTime());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"Bias", "StandardName", "StandardDate", "StandardBias", "DaylightName", "DaylightDate", "DaylightBias"})
/*      */   public static class TIME_ZONE_INFORMATION
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.LONG Bias;
/*      */     
/*      */     public String StandardName;
/*      */     public WinBase.SYSTEMTIME StandardDate;
/*      */     public WinDef.LONG StandardBias;
/*      */     public String DaylightName;
/*      */     public WinBase.SYSTEMTIME DaylightDate;
/*      */     public WinDef.LONG DaylightBias;
/*      */     
/*      */     public TIME_ZONE_INFORMATION() {
/* 1040 */       super(W32APITypeMapper.DEFAULT);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"Internal", "InternalHigh", "Offset", "OffsetHigh", "hEvent"})
/*      */   public static class OVERLAPPED
/*      */     extends Structure
/*      */   {
/*      */     public BaseTSD.ULONG_PTR Internal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BaseTSD.ULONG_PTR InternalHigh;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int Offset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int OffsetHigh;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"processorArchitecture", "dwPageSize", "lpMinimumApplicationAddress", "lpMaximumApplicationAddress", "dwActiveProcessorMask", "dwNumberOfProcessors", "dwProcessorType", "dwAllocationGranularity", "wProcessorLevel", "wProcessorRevision"})
/*      */   public static class SYSTEM_INFO
/*      */     extends Structure
/*      */   {
/*      */     public UNION processorArchitecture;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwPageSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer lpMinimumApplicationAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer lpMaximumApplicationAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BaseTSD.DWORD_PTR dwActiveProcessorMask;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwNumberOfProcessors;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwProcessorType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwAllocationGranularity;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wProcessorLevel;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wProcessorRevision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @FieldOrder({"wProcessorArchitecture", "wReserved"})
/*      */     public static class PI
/*      */       extends Structure
/*      */     {
/*      */       public WinDef.WORD wProcessorArchitecture;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public WinDef.WORD wReserved;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends PI
/*      */         implements Structure.ByReference {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class UNION
/*      */       extends Union
/*      */     {
/*      */       public WinDef.DWORD dwOemID;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public WinBase.SYSTEM_INFO.PI pi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends UNION
/*      */         implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void read() {
/* 1191 */         setType("dwOemID");
/* 1192 */         super.read();
/*      */         
/* 1194 */         setType("pi");
/* 1195 */         super.read();
/*      */       }
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
/*      */   @FieldOrder({"dwLength", "dwMemoryLoad", "ullTotalPhys", "ullAvailPhys", "ullTotalPageFile", "ullAvailPageFile", "ullTotalVirtual", "ullAvailVirtual", "ullAvailExtendedVirtual"})
/*      */   public static class MEMORYSTATUSEX
/*      */     extends Structure
/*      */   {
/* 1298 */     public WinDef.DWORD dwLength = new WinDef.DWORD(size());
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwMemoryLoad;
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullTotalPhys;
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailPhys;
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullTotalPageFile;
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailPageFile;
/*      */ 
/*      */     
/*      */     public WinDef.DWORDLONG ullTotalVirtual;
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailVirtual;
/*      */     
/*      */     public WinDef.DWORDLONG ullAvailExtendedVirtual;
/*      */   }
/*      */ 
/*      */   
/*      */   @FieldOrder({"dwLength", "lpSecurityDescriptor", "bInheritHandle"})
/*      */   public static class SECURITY_ATTRIBUTES
/*      */     extends Structure
/*      */   {
/* 1328 */     public WinDef.DWORD dwLength = new WinDef.DWORD(size());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer lpSecurityDescriptor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean bInheritHandle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"cb", "lpReserved", "lpDesktop", "lpTitle", "dwX", "dwY", "dwXSize", "dwYSize", "dwXCountChars", "dwYCountChars", "dwFillAttribute", "dwFlags", "wShowWindow", "cbReserved2", "lpReserved2", "hStdInput", "hStdOutput", "hStdError"})
/*      */   public static class STARTUPINFO
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.DWORD cb;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String lpReserved;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String lpDesktop;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String lpTitle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwXSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwYSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwXCountChars;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwYCountChars;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwFillAttribute;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int dwFlags;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wShowWindow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD cbReserved2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteByReference lpReserved2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hStdInput;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hStdOutput;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hStdError;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public STARTUPINFO() {
/* 1501 */       super(W32APITypeMapper.DEFAULT);
/* 1502 */       this.cb = new WinDef.DWORD(size());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"hProcess", "hThread", "dwProcessId", "dwThreadId"})
/*      */   public static class PROCESS_INFORMATION
/*      */     extends Structure
/*      */   {
/*      */     public WinNT.HANDLE hProcess;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinNT.HANDLE hThread;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwProcessId;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwThreadId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends PROCESS_INFORMATION
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ByReference(Pointer memory) {
/* 1549 */         super(memory);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public PROCESS_INFORMATION() {}
/*      */     
/*      */     public PROCESS_INFORMATION(Pointer memory) {
/* 1557 */       super(memory);
/* 1558 */       read();
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
/*      */   public static interface THREAD_START_ROUTINE
/*      */     extends StdCallLibrary.StdCallCallback
/*      */   {
/*      */     WinDef.DWORD apply(WinDef.LPVOID param1LPVOID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"foreignLocation"})
/*      */   public static class FOREIGN_THREAD_START_ROUTINE
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.LPVOID foreignLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface COMPUTER_NAME_FORMAT
/*      */   {
/*      */     public static final int ComputerNameNetBIOS = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNameDnsHostname = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNameDnsDomain = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNameDnsFullyQualified = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNamePhysicalNetBIOS = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNamePhysicalDnsHostname = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNamePhysicalDnsDomain = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNamePhysicalDnsFullyQualified = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int ComputerNameMax = 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface FE_EXPORT_FUNC
/*      */     extends StdCallLibrary.StdCallCallback
/*      */   {
/*      */     WinDef.DWORD callback(Pointer param1Pointer1, Pointer param1Pointer2, WinDef.ULONG param1ULONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface FE_IMPORT_FUNC
/*      */     extends StdCallLibrary.StdCallCallback
/*      */   {
/*      */     WinDef.DWORD callback(Pointer param1Pointer1, Pointer param1Pointer2, WinDef.ULONGByReference param1ULONGByReference);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"ReadIntervalTimeout", "ReadTotalTimeoutMultiplier", "ReadTotalTimeoutConstant", "WriteTotalTimeoutMultiplier", "WriteTotalTimeoutConstant"})
/*      */   public static class COMMTIMEOUTS
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.DWORD ReadIntervalTimeout;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD ReadTotalTimeoutMultiplier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD ReadTotalTimeoutConstant;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD WriteTotalTimeoutMultiplier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD WriteTotalTimeoutConstant;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FieldOrder({"DCBlength", "BaudRate", "controllBits", "wReserved", "XonLim", "XoffLim", "ByteSize", "Parity", "StopBits", "XonChar", "XoffChar", "ErrorChar", "EofChar", "EvtChar", "wReserved1"})
/*      */   public static class DCB
/*      */     extends Structure
/*      */   {
/*      */     public WinDef.DWORD DCBlength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD BaudRate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DCBControllBits controllBits;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wReserved;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD XonLim;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD XoffLim;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.BYTE ByteSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.BYTE Parity;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.BYTE StopBits;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char XonChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char XoffChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char ErrorChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char EofChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char EvtChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wReserved1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class DCBControllBits
/*      */       extends WinDef.DWORD
/*      */     {
/*      */       private static final long serialVersionUID = 8574966619718078579L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String toString() {
/* 1857 */         StringBuilder stringBuilder = new StringBuilder();
/* 1858 */         stringBuilder.append('<');
/* 1859 */         stringBuilder.append("fBinary:1=");
/* 1860 */         stringBuilder.append(getfBinary() ? 49 : 48);
/* 1861 */         stringBuilder.append(", fParity:1=");
/* 1862 */         stringBuilder.append(getfParity() ? 49 : 48);
/* 1863 */         stringBuilder.append(", fOutxCtsFlow:1=");
/* 1864 */         stringBuilder.append(getfOutxCtsFlow() ? 49 : 48);
/* 1865 */         stringBuilder.append(", fOutxDsrFlow:1=");
/* 1866 */         stringBuilder.append(getfOutxDsrFlow() ? 49 : 48);
/* 1867 */         stringBuilder.append(", fDtrControl:2=");
/* 1868 */         stringBuilder.append(getfDtrControl());
/* 1869 */         stringBuilder.append(", fDsrSensitivity:1=");
/* 1870 */         stringBuilder.append(getfDsrSensitivity() ? 49 : 48);
/* 1871 */         stringBuilder.append(", fTXContinueOnXoff:1=");
/* 1872 */         stringBuilder.append(getfTXContinueOnXoff() ? 49 : 48);
/* 1873 */         stringBuilder.append(", fOutX:1=");
/* 1874 */         stringBuilder.append(getfOutX() ? 49 : 48);
/* 1875 */         stringBuilder.append(", fInX:1=");
/* 1876 */         stringBuilder.append(getfInX() ? 49 : 48);
/* 1877 */         stringBuilder.append(", fErrorChar:1=");
/* 1878 */         stringBuilder.append(getfErrorChar() ? 49 : 48);
/* 1879 */         stringBuilder.append(", fNull:1=");
/* 1880 */         stringBuilder.append(getfNull() ? 49 : 48);
/* 1881 */         stringBuilder.append(", fRtsControl:2=");
/* 1882 */         stringBuilder.append(getfRtsControl());
/* 1883 */         stringBuilder.append(", fAbortOnError:1=");
/* 1884 */         stringBuilder.append(getfAbortOnError() ? 49 : 48);
/* 1885 */         stringBuilder.append(", fDummy2:17=");
/* 1886 */         stringBuilder.append(getfDummy2());
/* 1887 */         stringBuilder.append('>');
/* 1888 */         return stringBuilder.toString();
/*      */       }
/*      */       
/*      */       public boolean getfAbortOnError() {
/* 1892 */         return ((intValue() & 0x4000) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfBinary() {
/* 1896 */         return ((intValue() & 0x1) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfDsrSensitivity() {
/* 1900 */         return ((intValue() & 0x40) != 0);
/*      */       }
/*      */       
/*      */       public int getfDtrControl() {
/* 1904 */         return intValue() >>> 4 & 0x3;
/*      */       }
/*      */       
/*      */       public boolean getfErrorChar() {
/* 1908 */         return ((intValue() & 0x400) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfInX() {
/* 1912 */         return ((intValue() & 0x200) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfNull() {
/* 1916 */         return ((intValue() & 0x800) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfOutX() {
/* 1920 */         return ((intValue() & 0x100) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfOutxCtsFlow() {
/* 1924 */         return ((intValue() & 0x4) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfOutxDsrFlow() {
/* 1928 */         return ((intValue() & 0x8) != 0);
/*      */       }
/*      */       
/*      */       public boolean getfParity() {
/* 1932 */         return ((intValue() & 0x2) != 0);
/*      */       }
/*      */       
/*      */       public int getfRtsControl() {
/* 1936 */         return intValue() >>> 12 & 0x3;
/*      */       }
/*      */       
/*      */       public int getfDummy2() {
/* 1940 */         return intValue() >>> 15 & 0x1FFFF;
/*      */       }
/*      */       
/*      */       public boolean getfTXContinueOnXoff() {
/* 1944 */         return ((intValue() & 0x80) != 0);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfAbortOnError(boolean fAbortOnError) {
/* 1957 */         int tmp = leftShiftMask(fAbortOnError ? 1 : 0, (byte)14, 1, intValue());
/* 1958 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfBinary(boolean fBinary) {
/* 1969 */         int tmp = leftShiftMask(fBinary ? 1 : 0, (byte)0, 1, intValue());
/* 1970 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfDsrSensitivity(boolean fDsrSensitivity) {
/* 1982 */         int tmp = leftShiftMask(fDsrSensitivity ? 1 : 0, (byte)6, 1, intValue());
/* 1983 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfDtrControl(int fOutxDsrFlow) {
/* 1997 */         int tmp = leftShiftMask(fOutxDsrFlow, (byte)4, 3, intValue());
/* 1998 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfErrorChar(boolean fErrorChar) {
/* 2010 */         int tmp = leftShiftMask(fErrorChar ? 1 : 0, (byte)10, 1, intValue());
/* 2011 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfInX(boolean fInX) {
/* 2024 */         int tmp = leftShiftMask(fInX ? 1 : 0, (byte)9, 1, intValue());
/* 2025 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfNull(boolean fNull) {
/* 2034 */         int tmp = leftShiftMask(fNull ? 1 : 0, (byte)11, 1, intValue());
/* 2035 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfOutX(boolean fOutX) {
/* 2048 */         int tmp = leftShiftMask(fOutX ? 1 : 0, (byte)8, 1, intValue());
/* 2049 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfOutxCtsFlow(boolean fOutxCtsFlow) {
/* 2061 */         int tmp = leftShiftMask(fOutxCtsFlow ? 1 : 0, (byte)2, 1, intValue());
/* 2062 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfOutxDsrFlow(boolean fOutxDsrFlow) {
/* 2074 */         int tmp = leftShiftMask(fOutxDsrFlow ? 1 : 0, (byte)3, 1, intValue());
/* 2075 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfParity(boolean fParity) {
/* 2085 */         int tmp = leftShiftMask(fParity ? 1 : 0, (byte)1, 1, intValue());
/* 2086 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfRtsControl(int fRtsControl) {
/* 2101 */         int tmp = leftShiftMask(fRtsControl, (byte)12, 3, intValue());
/* 2102 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setfTXContinueOnXoff(boolean fTXContinueOnXoff) {
/* 2116 */         int tmp = leftShiftMask(fTXContinueOnXoff ? 1 : 0, (byte)7, 1, intValue());
/* 2117 */         setValue(tmp);
/*      */       }
/*      */ 
/*      */       
/*      */       private static int leftShiftMask(int valuetoset, byte shift, int mask, int storage) {
/* 2122 */         int tmp = storage;
/* 2123 */         tmp &= mask << shift ^ 0xFFFFFFFF;
/* 2124 */         tmp |= (valuetoset & mask) << shift;
/* 2125 */         return tmp;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DCB() {
/* 2244 */       this.DCBlength = new WinDef.DWORD(size());
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface EnumResTypeProc extends Callback {
/*      */     boolean invoke(WinDef.HMODULE param1HMODULE, Pointer param1Pointer1, Pointer param1Pointer2);
/*      */   }
/*      */   
/*      */   public static interface EnumResNameProc extends Callback {
/*      */     boolean invoke(WinDef.HMODULE param1HMODULE, Pointer param1Pointer1, Pointer param1Pointer2, Pointer param1Pointer3);
/*      */   }
/*      */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\com\sun\jna\platform\win32\WinBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */