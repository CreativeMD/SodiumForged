package me.jellysquid.mods.sodium.mixin.features.gui.hooks.settings;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Dynamic
    @Inject(method = "Lnet/minecraft/client/gui/screens/OptionsScreen;lambda$init$2()Lnet/minecraft/client/gui/screens/Screen;", at = @At("HEAD"), cancellable = true)
    private void open(CallbackInfoReturnable<Screen> ci) {
        ci.setReturnValue(new SodiumOptionsGUI(this));
    }
}
