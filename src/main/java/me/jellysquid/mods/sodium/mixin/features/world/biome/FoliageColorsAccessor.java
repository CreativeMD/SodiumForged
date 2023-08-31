package me.jellysquid.mods.sodium.mixin.features.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.FoliageColor;

@Mixin(FoliageColor.class)
public interface FoliageColorsAccessor {
    @Accessor("pixels")
    static int[] getColorMap() {
        throw new AssertionError();
    }
}