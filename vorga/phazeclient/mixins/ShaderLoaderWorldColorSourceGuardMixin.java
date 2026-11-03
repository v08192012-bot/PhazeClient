/*    */ package vorga.phazeclient.mixins;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.minecraft.class_10141;
/*    */ import net.minecraft.class_10151;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_3298;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
/*    */ 
/*    */ 
/*    */ @Mixin({class_10151.class})
/*    */ public abstract class ShaderLoaderWorldColorSourceGuardMixin
/*    */ {
/*    */   @Inject(method = {"loadShaderSource"}, at = {@At("HEAD")}, cancellable = true, require = 0)
/*    */   private static void phaze$skipSodiumShaderSourcesWithoutSodium(class_2960 id, class_3298 resource, class_10141.class_282 type, Map<class_2960, class_3298> allResources, ImmutableMap.Builder<?, String> builder, CallbackInfo ci) {
/* 32 */     if ("sodium".equals(id.method_12836()) && !FabricLoader.getInstance().isModLoaded("sodium"))
/* 33 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\VLADIMIR\Downloads\Telegram Desktop\PhazeClient-1.2.jar!\vorga\phazeclient\mixins\ShaderLoaderWorldColorSourceGuardMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */