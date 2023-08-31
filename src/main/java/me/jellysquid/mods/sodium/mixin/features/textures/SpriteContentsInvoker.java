package me.jellysquid.mods.sodium.mixin.features.textures;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.renderer.texture.SpriteContents;

@Mixin(SpriteContents.class)
public interface SpriteContentsInvoker {
    @Invoker
    void invokeUpload(int x, int y, int unpackSkipPixels, int unpackSkipRows, NativeImage[] images);
}