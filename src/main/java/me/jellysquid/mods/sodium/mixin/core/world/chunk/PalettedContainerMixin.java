package me.jellysquid.mods.sodium.mixin.core.world.chunk;

import java.util.Objects;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import me.jellysquid.mods.sodium.client.world.PaletteStorageExtended;
import me.jellysquid.mods.sodium.client.world.ReadableContainerExtended;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;

@Mixin(PalettedContainer.class)
public abstract class PalettedContainerMixin<T> implements ReadableContainerExtended<T> {

    @Shadow
    private volatile PalettedContainer.Data<T> data;

    @Shadow
    @Final
    private PalettedContainer.Strategy strategy;

    @Shadow
    public abstract PalettedContainer<T> copy();

    @Override
    public void sodium$unpack(T[] values) {
        var indexer = Objects.requireNonNull(this.strategy);

        if (values.length != indexer.size()) {
            throw new IllegalArgumentException("Array is wrong size");
        }

        var data = Objects.requireNonNull(this.data, "PalettedContainer must have data");

        var storage = (PaletteStorageExtended) data.storage();
        storage.sodium$unpack(values, data.palette());
    }

    @Override
    public void sodium$unpack(T[] values, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        var indexer = Objects.requireNonNull(this.strategy);

        if (values.length != indexer.size()) {
            throw new IllegalArgumentException("Array is wrong size");
        }

        var data = Objects.requireNonNull(this.data, "PalettedContainer must have data");

        var storage = data.storage();
        var palette = data.palette();

        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int x = minX; x <= maxX; x++) {
                    int localBlockIndex = indexer.getIndex(x, y, z);

                    int paletteIndex = storage.get(localBlockIndex);
                    var paletteValue = palette.get(paletteIndex);

                    values[localBlockIndex] = paletteValue;
                }
            }
        }
    }

    @Override
    public PalettedContainerRO<T> sodium$copy() {
        return this.copy();
    }
}
