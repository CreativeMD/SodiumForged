package me.jellysquid.mods.sodium.mixin.features.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.GrassColor;

@Mixin(GrassColor.class)
public interface GrassColorsAccessor {
    @Accessor("pixels")
    static int[] getColorMap() {
        throw new AssertionError();
    }
}
