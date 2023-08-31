package me.jellysquid.mods.sodium.mixin.core.model.colors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import me.jellysquid.mods.sodium.client.model.color.interop.BlockColorsExtended;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;

@Mixin(BlockColors.class)
public class BlockColorsMixin implements BlockColorsExtended {
    // We're keeping a copy as we need to be able to iterate over the entry pairs, rather than just the values.
    @Unique
    private final Reference2ReferenceMap<Block, BlockColor> blocksToColor = new Reference2ReferenceOpenHashMap<>();

    @Inject(method = "register", at = @At("HEAD"))
    private void preRegisterColorProvider(BlockColor provider, Block[] blocks, CallbackInfo ci) {
        for (Block block : blocks) {
            this.blocksToColor.put(block, provider);
        }
    }

    @Override
    public Reference2ReferenceMap<Block, BlockColor> sodium$getProviders() {
        return Reference2ReferenceMaps.unmodifiable(this.blocksToColor);
    }
}
