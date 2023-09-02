package me.jellysquid.mods.sodium.mixin.features.render.world.clouds;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;

@Mixin(FogRenderer.class)
public interface BackgroundRendererInvoker {
    @Invoker
    static FogRenderer.MobEffectFogFunction invokeGetPriorityFogFunction(Entity entity, float tickDelta) {
        throw new AssertionError();
    }
}
