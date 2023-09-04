package me.jellysquid.mods.sodium.mixin.features.textures.animations.upload;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.texture.SpriteContents;

@Mixin(SpriteContents.AnimatedTexture.class)
public interface SpriteContentsAnimationAccessor {
    @Accessor
    List<SpriteContents.FrameInfo> getFrames();

    @Accessor("frameRowSize")
    int getFrameCount();
}
