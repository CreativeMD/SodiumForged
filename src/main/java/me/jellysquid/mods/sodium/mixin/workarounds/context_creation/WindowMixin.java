package me.jellysquid.mods.sodium.mixin.workarounds.context_creation;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;

import me.jellysquid.mods.sodium.client.util.workarounds.PostLaunchChecks;
import me.jellysquid.mods.sodium.client.util.workarounds.Workarounds;
import me.jellysquid.mods.sodium.client.util.workarounds.driver.nvidia.NvidiaWorkarounds;

@Mixin(Window.class)
public class WindowMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/loading/ImmediateWindowHandler;setupMinecraftWindow(Ljava/util/function/IntSupplier;Ljava/util/function/IntSupplier;Ljava/util/function/Supplier;Ljava/util/function/LongSupplier;)J", remap = false))
    private long wrapGlfwCreateWindow(int width, int height, CharSequence title, long monitor, long share) {
        if (Workarounds.isWorkaroundEnabled(Workarounds.Reference.NVIDIA_THREADED_OPTIMIZATIONS)) {
            NvidiaWorkarounds.install();
        }

        try {
            return GLFW.glfwCreateWindow(width, height, title, monitor, share);
        } finally {
            if (Workarounds.isWorkaroundEnabled(Workarounds.Reference.NVIDIA_THREADED_OPTIMIZATIONS)) {
                NvidiaWorkarounds.uninstall();
            }
        }
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void postWindowCreated(WindowEventHandler eventHandler, ScreenManager monitorTracker, DisplayData settings, String videoMode, String title, CallbackInfo ci) {
        PostLaunchChecks.checkContext();
    }
}
