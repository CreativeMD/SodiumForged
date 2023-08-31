package me.jellysquid.mods.sodium.mixin.features.render.model;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@Mixin(ItemBlockRenderTypes.class)
public class RenderLayersMixin {
    @Mutable
    @Shadow
    @Final
    private static Map<Block, RenderType> TYPE_BY_BLOCK;

    @Mutable
    @Shadow
    @Final
    private static Map<Fluid, RenderLayer> TYPE_BY_FLUID;

    static {
        // Replace the backing collection types with something a bit faster, since this is a hot spot in chunk rendering.
        TYPE_BY_BLOCK = new Reference2ReferenceOpenHashMap<>(TYPE_BY_BLOCK);
        TYPE_BY_FLUID = new Reference2ReferenceOpenHashMap<>(TYPE_BY_FLUID);
    }
}
