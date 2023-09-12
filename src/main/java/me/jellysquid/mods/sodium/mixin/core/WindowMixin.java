package me.jellysquid.mods.sodium.mixin.core;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.platform.Window;

@Mixin(Window.class)
public class WindowMixin {
    /*@Inject(method = "<init>(Lcom/mojang/blaze3d/platform/WindowEventHandler;Lcom/mojang/blaze3d/platform/ScreenManager;Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)V", at = @At("TAIL"))
    private void setAdditionalWindowHints(WindowEventHandler eventHandler, ScreenManager monitorTracker, DisplayData settings, String videoMode, String title, CallbackInfo ci) {
        if (SodiumClientMod.options().performance.useNoErrorGLContext && !Workarounds.isWorkaroundEnabled(Workarounds.Reference.NO_ERROR_CONTEXT_UNSUPPORTED)) {
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_NO_ERROR, GLFW.GLFW_TRUE);
        }
    }*/
}
