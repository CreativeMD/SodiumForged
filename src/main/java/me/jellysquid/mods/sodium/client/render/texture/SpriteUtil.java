package me.jellysquid.mods.sodium.client.render.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SpriteUtil {
    public static void markSpriteActive(TextureAtlasSprite sprite) {
        ((SpriteContentsExtended) sprite.contents()).sodium$setActive(true);
    }

    public static boolean hasAnimation(TextureAtlasSprite sprite) {
        return ((SpriteContentsExtended) sprite.contents()).sodium$hasAnimation();
    }
}
