package me.jellysquid.mods.sodium.client.gui.console;

import org.jetbrains.annotations.NotNull;

import me.jellysquid.mods.sodium.client.gui.console.message.MessageLevel;
import net.minecraft.network.chat.Component;

public interface ConsoleSink {
    void logMessage(@NotNull MessageLevel level, @NotNull Component text, double duration);
}
