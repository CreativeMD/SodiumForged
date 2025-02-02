package me.jellysquid.mods.sodium.client.util;

import java.util.Locale;

import com.mojang.blaze3d.platform.NativeImage;

import me.jellysquid.mods.sodium.mixin.features.textures.NativeImageAccessor;

public class NativeImageHelper {
    public static long getPointerRGBA(NativeImage nativeImage) {
        if (nativeImage.format() != NativeImage.Format.RGBA) {
            throw new IllegalArgumentException(
                    String.format(Locale.ROOT, "Tried to get pointer to RGBA pixel data on NativeImage of wrong format; have %s", nativeImage.format()));
        }

        return ((NativeImageAccessor) (Object) nativeImage) // duck type since NativeImage is final
                .getPointer();
    }
}
