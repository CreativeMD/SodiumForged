package me.jellysquid.mods.sodium.mixin.features.textures.animations.tracking;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import me.jellysquid.mods.sodium.client.render.texture.SpriteContentsExtended;
import net.minecraft.client.renderer.texture.SpriteContents;

@Mixin(SpriteContents.class)
public abstract class SpriteContentsMixin implements SpriteContentsExtended {
    @Shadow
    @Final
    @Nullable
    private SpriteContents.AnimatedTexture animatedTexture;
    
    @Unique
    private boolean active;
    
    @Override
    public void sodium$setActive(boolean value) {
        this.active = value;
    }
    
    @Override
    public boolean sodium$hasAnimation() {
        return this.animatedTexture != null;
    }
    
    @Override
    public boolean sodium$isActive() {
        return this.active;
    }
}
