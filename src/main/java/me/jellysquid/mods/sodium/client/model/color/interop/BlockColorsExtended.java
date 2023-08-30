package me.jellysquid.mods.sodium.client.model.color.interop;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;

public interface BlockColorsExtended {
    static Reference2ReferenceMap<Block, BlockColor> getProviders(BlockColors blockColors) {
        return ((BlockColorsExtended) blockColors).sodium$getProviders();
    }

    Reference2ReferenceMap<Block, BlockColor> sodium$getProviders();
}
