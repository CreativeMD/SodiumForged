package me.jellysquid.mods.sodium.client.gui.console;

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import me.jellysquid.mods.sodium.client.gui.console.message.Message;
import me.jellysquid.mods.sodium.client.gui.console.message.MessageLevel;
import net.minecraft.network.chat.Component;

public class Console implements ConsoleSink {
    static final Console INSTANCE = new Console();

    private final ArrayDeque<Message> messages = new ArrayDeque<>();

    @Override
    public void logMessage(@NotNull MessageLevel level, @NotNull Component text, double duration) {
        Validate.notNull(level);
        Validate.notNull(text);

        this.messages.addLast(new Message(level, text.copy(), duration));
    }

    public Deque<Message> getMessageDrain() {
        return this.messages;
    }

    public static ConsoleSink instance() {
        return INSTANCE;
    }
}
