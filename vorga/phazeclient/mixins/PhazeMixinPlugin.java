/*     */ package vorga.phazeclient.mixins;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.fabricmc.loader.api.FabricLoader;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PhazeMixinPlugin
/*     */   implements IMixinConfigPlugin
/*     */ {
/*  52 */   private static final boolean SODIUM_LOADED = FabricLoader.getInstance().isModLoaded("sodium");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final boolean IRIS_LOADED = FabricLoader.getInstance().isModLoaded("iris");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final boolean EXORDIUM_LOADED = FabricLoader.getInstance().isModLoaded("exordium");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final boolean SCREENCOPY_MOD_LOADED = FabricLoader.getInstance().isModLoaded("screencopy");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private static final boolean REESES_LOADED = FabricLoader.getInstance().isModLoaded("reeses-sodium-options");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static final Set<String> VANILLA_ONLY = Set.of("vorga.phazeclient.mixins.WorldRendererChunkAnimatorMixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static final Set<String> SODIUM_ONLY = Set.of("vorga.phazeclient.mixins.sodium.DefaultFluidRendererWorldColorMixin", "vorga.phazeclient.mixins.sodium.SodiumGlProgramWorldColorMixin", "vorga.phazeclient.mixins.sodium.DefaultChunkRendererChunkAnimatorMixin", "vorga.phazeclient.mixins.sodium.ShaderParserChunkAnimatorMixin", "vorga.phazeclient.mixins.sodium.SodiumGlShaderChunkAnimatorMixin", "vorga.phazeclient.mixins.sodium.SodiumCloudRendererWorldColorMixin", "vorga.phazeclient.mixins.sodium.SodiumFlatButtonCursorMixin", "vorga.phazeclient.mixins.sodium.SodiumControlElementCursorMixin", "vorga.phazeclient.mixins.sodium.SodiumSliderDragCursorMixin", "vorga.phazeclient.mixins.sodium.ReesesSearchFieldCursorMixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private static final Set<String> REESES_ONLY = Set.of("vorga.phazeclient.mixins.sodium.ReesesSearchFieldCursorMixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   private static final Set<String> IRIS_ONLY = Set.of("vorga.phazeclient.mixins.iris.IrisGlShaderChunkAnimatorMixin", "vorga.phazeclient.mixins.iris.IrisElementWidgetCursorMixin", "vorga.phazeclient.mixins.iris.IrisSkyColorUniformMixin", "vorga.phazeclient.mixins.iris.IrisProgramWorldColorMixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   private static final Set<String> EXORDIUM_ONLY = Set.of("vorga.phazeclient.mixins.exordium.ExordiumBufferInstanceMixin", "vorga.phazeclient.mixins.exordium.ExordiumBufferedComponentMixin", "vorga.phazeclient.mixins.exordium.ExordiumDelayedRenderCallManagerMixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   private static final Set<String> SKIP_IF_SCREENCOPY_MOD = Set.of("vorga.phazeclient.mixins.ScreenshotRecorderScreencopyMixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoad(String mixinPackage) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefMapperConfig() {
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
/* 188 */     if (SODIUM_LOADED && VANILLA_ONLY.contains(mixinClassName)) {
/* 189 */       return false;
/*     */     }
/* 191 */     if (!SODIUM_LOADED && SODIUM_ONLY.contains(mixinClassName)) {
/* 192 */       return false;
/*     */     }
/* 194 */     if (!IRIS_LOADED && IRIS_ONLY.contains(mixinClassName)) {
/* 195 */       return false;
/*     */     }
/* 197 */     if (!EXORDIUM_LOADED && EXORDIUM_ONLY.contains(mixinClassName)) {
/* 198 */       return false;
/*     */     }
/* 200 */     if (!REESES_LOADED && REESES_ONLY.contains(mixinClassName)) {
/* 201 */       return false;
/*     */     }
/* 203 */     if (SCREENCOPY_MOD_LOADED && SKIP_IF_SCREENCOPY_MOD.contains(mixinClassName)) {
/* 204 */       return false;
/*     */     }
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getMixins() {
/* 218 */     return null;
/*     */   }
/*     */   
/*     */   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*     */   
/*     */   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*     */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\PhazeMixinPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */